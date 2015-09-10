/*    1:     */ package net.sourceforge.htmlunit.corejs.javascript.tools.debugger;
/*    2:     */ 
/*    3:     */ import java.util.Arrays;
/*    4:     */ import java.util.Comparator;
/*    5:     */ import javax.swing.event.TreeModelListener;
/*    6:     */ import javax.swing.tree.TreePath;
/*    7:     */ import net.sourceforge.htmlunit.corejs.javascript.tools.debugger.treetable.TreeTableModel;
/*    8:     */ 
/*    9:     */ class VariableModel
/*   10:     */   implements TreeTableModel
/*   11:     */ {
/*   12:2472 */   private static final String[] cNames = { " Name", " Value" };
/*   13:2477 */   private static final Class<?>[] cTypes = { TreeTableModel.class, String.class };
/*   14:2483 */   private static final VariableNode[] CHILDLESS = new VariableNode[0];
/*   15:     */   private Dim debugger;
/*   16:     */   private VariableNode root;
/*   17:     */   
/*   18:     */   public VariableModel() {}
/*   19:     */   
/*   20:     */   public VariableModel(Dim debugger, Object scope)
/*   21:     */   {
/*   22:2505 */     this.debugger = debugger;
/*   23:2506 */     this.root = new VariableNode(scope, "this");
/*   24:     */   }
/*   25:     */   
/*   26:     */   public Object getRoot()
/*   27:     */   {
/*   28:2515 */     if (this.debugger == null) {
/*   29:2516 */       return null;
/*   30:     */     }
/*   31:2518 */     return this.root;
/*   32:     */   }
/*   33:     */   
/*   34:     */   public int getChildCount(Object nodeObj)
/*   35:     */   {
/*   36:2525 */     if (this.debugger == null) {
/*   37:2526 */       return 0;
/*   38:     */     }
/*   39:2528 */     VariableNode node = (VariableNode)nodeObj;
/*   40:2529 */     return children(node).length;
/*   41:     */   }
/*   42:     */   
/*   43:     */   public Object getChild(Object nodeObj, int i)
/*   44:     */   {
/*   45:2536 */     if (this.debugger == null) {
/*   46:2537 */       return null;
/*   47:     */     }
/*   48:2539 */     VariableNode node = (VariableNode)nodeObj;
/*   49:2540 */     return children(node)[i];
/*   50:     */   }
/*   51:     */   
/*   52:     */   public boolean isLeaf(Object nodeObj)
/*   53:     */   {
/*   54:2547 */     if (this.debugger == null) {
/*   55:2548 */       return true;
/*   56:     */     }
/*   57:2550 */     VariableNode node = (VariableNode)nodeObj;
/*   58:2551 */     return children(node).length == 0;
/*   59:     */   }
/*   60:     */   
/*   61:     */   public int getIndexOfChild(Object parentObj, Object childObj)
/*   62:     */   {
/*   63:2558 */     if (this.debugger == null) {
/*   64:2559 */       return -1;
/*   65:     */     }
/*   66:2561 */     VariableNode parent = (VariableNode)parentObj;
/*   67:2562 */     VariableNode child = (VariableNode)childObj;
/*   68:2563 */     VariableNode[] children = children(parent);
/*   69:2564 */     for (int i = 0; i != children.length; i++) {
/*   70:2565 */       if (children[i] == child) {
/*   71:2566 */         return i;
/*   72:     */       }
/*   73:     */     }
/*   74:2569 */     return -1;
/*   75:     */   }
/*   76:     */   
/*   77:     */   public boolean isCellEditable(Object node, int column)
/*   78:     */   {
/*   79:2576 */     return column == 0;
/*   80:     */   }
/*   81:     */   
/*   82:     */   public void setValueAt(Object value, Object node, int column) {}
/*   83:     */   
/*   84:     */   public void addTreeModelListener(TreeModelListener l) {}
/*   85:     */   
/*   86:     */   public void removeTreeModelListener(TreeModelListener l) {}
/*   87:     */   
/*   88:     */   public void valueForPathChanged(TreePath path, Object newValue) {}
/*   89:     */   
/*   90:     */   public int getColumnCount()
/*   91:     */   {
/*   92:2602 */     return cNames.length;
/*   93:     */   }
/*   94:     */   
/*   95:     */   public String getColumnName(int column)
/*   96:     */   {
/*   97:2609 */     return cNames[column];
/*   98:     */   }
/*   99:     */   
/*  100:     */   public Class<?> getColumnClass(int column)
/*  101:     */   {
/*  102:2616 */     return cTypes[column];
/*  103:     */   }
/*  104:     */   
/*  105:     */   public Object getValueAt(Object nodeObj, int column)
/*  106:     */   {
/*  107:2623 */     if (this.debugger == null) {
/*  108:2623 */       return null;
/*  109:     */     }
/*  110:2624 */     VariableNode node = (VariableNode)nodeObj;
/*  111:2625 */     switch (column)
/*  112:     */     {
/*  113:     */     case 0: 
/*  114:2627 */       return node.toString();
/*  115:     */     case 1: 
/*  116:     */       String result;
/*  117:     */       try
/*  118:     */       {
/*  119:2631 */         result = this.debugger.objectToString(getValue(node));
/*  120:     */       }
/*  121:     */       catch (RuntimeException exc)
/*  122:     */       {
/*  123:2633 */         result = exc.getMessage();
/*  124:     */       }
/*  125:2635 */       StringBuffer buf = new StringBuffer();
/*  126:2636 */       int len = result.length();
/*  127:2637 */       for (int i = 0; i < len; i++)
/*  128:     */       {
/*  129:2638 */         char ch = result.charAt(i);
/*  130:2639 */         if (Character.isISOControl(ch)) {
/*  131:2640 */           ch = ' ';
/*  132:     */         }
/*  133:2642 */         buf.append(ch);
/*  134:     */       }
/*  135:2644 */       return buf.toString();
/*  136:     */     }
/*  137:2646 */     return null;
/*  138:     */   }
/*  139:     */   
/*  140:     */   private VariableNode[] children(VariableNode node)
/*  141:     */   {
/*  142:2653 */     if (node.children != null) {
/*  143:2654 */       return node.children;
/*  144:     */     }
/*  145:2659 */     Object value = getValue(node);
/*  146:2660 */     Object[] ids = this.debugger.getObjectIds(value);
/*  147:     */     VariableNode[] children;
/*  148:     */     VariableNode[] children;
/*  149:2661 */     if ((ids == null) || (ids.length == 0))
/*  150:     */     {
/*  151:2662 */       children = CHILDLESS;
/*  152:     */     }
/*  153:     */     else
/*  154:     */     {
/*  155:2664 */       Arrays.sort(ids, new Comparator()
/*  156:     */       {
/*  157:     */         public int compare(Object l, Object r)
/*  158:     */         {
/*  159:2667 */           if ((l instanceof String))
/*  160:     */           {
/*  161:2668 */             if ((r instanceof Integer)) {
/*  162:2669 */               return -1;
/*  163:     */             }
/*  164:2671 */             return ((String)l).compareToIgnoreCase((String)r);
/*  165:     */           }
/*  166:2673 */           if ((r instanceof String)) {
/*  167:2674 */             return 1;
/*  168:     */           }
/*  169:2676 */           int lint = ((Integer)l).intValue();
/*  170:2677 */           int rint = ((Integer)r).intValue();
/*  171:2678 */           return lint - rint;
/*  172:     */         }
/*  173:2681 */       });
/*  174:2682 */       children = new VariableNode[ids.length];
/*  175:2683 */       for (int i = 0; i != ids.length; i++) {
/*  176:2684 */         children[i] = new VariableNode(value, ids[i]);
/*  177:     */       }
/*  178:     */     }
/*  179:2687 */     node.children = children;
/*  180:2688 */     return children;
/*  181:     */   }
/*  182:     */   
/*  183:     */   public Object getValue(VariableNode node)
/*  184:     */   {
/*  185:     */     try
/*  186:     */     {
/*  187:2696 */       return this.debugger.getObjectProperty(node.object, node.id);
/*  188:     */     }
/*  189:     */     catch (Exception exc) {}
/*  190:2698 */     return "undefined";
/*  191:     */   }
/*  192:     */   
/*  193:     */   private static class VariableNode
/*  194:     */   {
/*  195:     */     private Object object;
/*  196:     */     private Object id;
/*  197:     */     private VariableNode[] children;
/*  198:     */     
/*  199:     */     public VariableNode(Object object, Object id)
/*  200:     */     {
/*  201:2727 */       this.object = object;
/*  202:2728 */       this.id = id;
/*  203:     */     }
/*  204:     */     
/*  205:     */     public String toString()
/*  206:     */     {
/*  207:2736 */       return "[" + ((Integer)this.id).intValue() + "]";
/*  208:     */     }
/*  209:     */   }
/*  210:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.tools.debugger.VariableModel
 * JD-Core Version:    0.7.0.1
 */