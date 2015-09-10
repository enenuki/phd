/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.tools.debugger.treetable;
/*   2:    */ 
/*   3:    */ import java.awt.Component;
/*   4:    */ import java.awt.Dimension;
/*   5:    */ import java.awt.Graphics;
/*   6:    */ import java.awt.Rectangle;
/*   7:    */ import java.awt.event.MouseEvent;
/*   8:    */ import java.util.EventObject;
/*   9:    */ import javax.swing.DefaultListSelectionModel;
/*  10:    */ import javax.swing.JTable;
/*  11:    */ import javax.swing.JTree;
/*  12:    */ import javax.swing.ListSelectionModel;
/*  13:    */ import javax.swing.LookAndFeel;
/*  14:    */ import javax.swing.UIManager;
/*  15:    */ import javax.swing.event.ListSelectionEvent;
/*  16:    */ import javax.swing.event.ListSelectionListener;
/*  17:    */ import javax.swing.table.TableCellEditor;
/*  18:    */ import javax.swing.table.TableCellRenderer;
/*  19:    */ import javax.swing.tree.DefaultTreeCellRenderer;
/*  20:    */ import javax.swing.tree.DefaultTreeSelectionModel;
/*  21:    */ import javax.swing.tree.TreeCellRenderer;
/*  22:    */ import javax.swing.tree.TreeModel;
/*  23:    */ import javax.swing.tree.TreePath;
/*  24:    */ 
/*  25:    */ public class JTreeTable
/*  26:    */   extends JTable
/*  27:    */ {
/*  28:    */   private static final long serialVersionUID = -2103973006456695515L;
/*  29:    */   protected TreeTableCellRenderer tree;
/*  30:    */   
/*  31:    */   public JTreeTable(TreeTableModel treeTableModel)
/*  32:    */   {
/*  33: 71 */     this.tree = new TreeTableCellRenderer(treeTableModel);
/*  34:    */     
/*  35:    */ 
/*  36: 74 */     super.setModel(new TreeTableModelAdapter(treeTableModel, this.tree));
/*  37:    */     
/*  38:    */ 
/*  39: 77 */     ListToTreeSelectionModelWrapper selectionWrapper = new ListToTreeSelectionModelWrapper();
/*  40:    */     
/*  41: 79 */     this.tree.setSelectionModel(selectionWrapper);
/*  42: 80 */     setSelectionModel(selectionWrapper.getListSelectionModel());
/*  43:    */     
/*  44:    */ 
/*  45: 83 */     setDefaultRenderer(TreeTableModel.class, this.tree);
/*  46: 84 */     setDefaultEditor(TreeTableModel.class, new TreeTableCellEditor());
/*  47:    */     
/*  48:    */ 
/*  49: 87 */     setShowGrid(false);
/*  50:    */     
/*  51:    */ 
/*  52: 90 */     setIntercellSpacing(new Dimension(0, 0));
/*  53: 94 */     if (this.tree.getRowHeight() < 1) {
/*  54: 96 */       setRowHeight(18);
/*  55:    */     }
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void updateUI()
/*  59:    */   {
/*  60:107 */     super.updateUI();
/*  61:108 */     if (this.tree != null) {
/*  62:109 */       this.tree.updateUI();
/*  63:    */     }
/*  64:113 */     LookAndFeel.installColorsAndFont(this, "Tree.background", "Tree.foreground", "Tree.font");
/*  65:    */   }
/*  66:    */   
/*  67:    */   public int getEditingRow()
/*  68:    */   {
/*  69:125 */     return getColumnClass(this.editingColumn) == TreeTableModel.class ? -1 : this.editingRow;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void setRowHeight(int rowHeight)
/*  73:    */   {
/*  74:134 */     super.setRowHeight(rowHeight);
/*  75:135 */     if ((this.tree != null) && (this.tree.getRowHeight() != rowHeight)) {
/*  76:136 */       this.tree.setRowHeight(getRowHeight());
/*  77:    */     }
/*  78:    */   }
/*  79:    */   
/*  80:    */   public JTree getTree()
/*  81:    */   {
/*  82:144 */     return this.tree;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public class TreeTableCellRenderer
/*  86:    */     extends JTree
/*  87:    */     implements TableCellRenderer
/*  88:    */   {
/*  89:    */     private static final long serialVersionUID = -193867880014600717L;
/*  90:    */     protected int visibleRow;
/*  91:    */     
/*  92:    */     public TreeTableCellRenderer(TreeModel model)
/*  93:    */     {
/*  94:156 */       super();
/*  95:    */     }
/*  96:    */     
/*  97:    */     public void updateUI()
/*  98:    */     {
/*  99:165 */       super.updateUI();
/* 100:    */       
/* 101:    */ 
/* 102:168 */       TreeCellRenderer tcr = getCellRenderer();
/* 103:169 */       if ((tcr instanceof DefaultTreeCellRenderer))
/* 104:    */       {
/* 105:170 */         DefaultTreeCellRenderer dtcr = (DefaultTreeCellRenderer)tcr;
/* 106:    */         
/* 107:    */ 
/* 108:    */ 
/* 109:    */ 
/* 110:175 */         dtcr.setTextSelectionColor(UIManager.getColor("Table.selectionForeground"));
/* 111:    */         
/* 112:177 */         dtcr.setBackgroundSelectionColor(UIManager.getColor("Table.selectionBackground"));
/* 113:    */       }
/* 114:    */     }
/* 115:    */     
/* 116:    */     public void setRowHeight(int rowHeight)
/* 117:    */     {
/* 118:188 */       if (rowHeight > 0)
/* 119:    */       {
/* 120:189 */         super.setRowHeight(rowHeight);
/* 121:190 */         if ((JTreeTable.this != null) && (JTreeTable.this.getRowHeight() != rowHeight)) {
/* 122:192 */           JTreeTable.this.setRowHeight(getRowHeight());
/* 123:    */         }
/* 124:    */       }
/* 125:    */     }
/* 126:    */     
/* 127:    */     public void setBounds(int x, int y, int w, int h)
/* 128:    */     {
/* 129:202 */       super.setBounds(x, 0, w, JTreeTable.this.getHeight());
/* 130:    */     }
/* 131:    */     
/* 132:    */     public void paint(Graphics g)
/* 133:    */     {
/* 134:211 */       g.translate(0, -this.visibleRow * getRowHeight());
/* 135:212 */       super.paint(g);
/* 136:    */     }
/* 137:    */     
/* 138:    */     public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
/* 139:    */     {
/* 140:223 */       if (isSelected) {
/* 141:224 */         setBackground(table.getSelectionBackground());
/* 142:    */       } else {
/* 143:226 */         setBackground(table.getBackground());
/* 144:    */       }
/* 145:228 */       this.visibleRow = row;
/* 146:229 */       return this;
/* 147:    */     }
/* 148:    */   }
/* 149:    */   
/* 150:    */   public class TreeTableCellEditor
/* 151:    */     extends AbstractCellEditor
/* 152:    */     implements TableCellEditor
/* 153:    */   {
/* 154:    */     public TreeTableCellEditor() {}
/* 155:    */     
/* 156:    */     public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int r, int c)
/* 157:    */     {
/* 158:244 */       return JTreeTable.this.tree;
/* 159:    */     }
/* 160:    */     
/* 161:    */     public boolean isCellEditable(EventObject e)
/* 162:    */     {
/* 163:267 */       if ((e instanceof MouseEvent)) {
/* 164:268 */         for (int counter = JTreeTable.this.getColumnCount() - 1; counter >= 0; counter--) {
/* 165:270 */           if (JTreeTable.this.getColumnClass(counter) == TreeTableModel.class)
/* 166:    */           {
/* 167:271 */             MouseEvent me = (MouseEvent)e;
/* 168:272 */             MouseEvent newME = new MouseEvent(JTreeTable.this.tree, me.getID(), me.getWhen(), me.getModifiers(), me.getX() - JTreeTable.this.getCellRect(0, counter, true).x, me.getY(), me.getClickCount(), me.isPopupTrigger());
/* 169:    */             
/* 170:    */ 
/* 171:    */ 
/* 172:    */ 
/* 173:277 */             JTreeTable.this.tree.dispatchEvent(newME);
/* 174:278 */             break;
/* 175:    */           }
/* 176:    */         }
/* 177:    */       }
/* 178:282 */       return false;
/* 179:    */     }
/* 180:    */   }
/* 181:    */   
/* 182:    */   public class ListToTreeSelectionModelWrapper
/* 183:    */     extends DefaultTreeSelectionModel
/* 184:    */   {
/* 185:    */     private static final long serialVersionUID = 8168140829623071131L;
/* 186:    */     protected boolean updatingListSelectionModel;
/* 187:    */     
/* 188:    */     public ListToTreeSelectionModelWrapper()
/* 189:    */     {
/* 190:303 */       getListSelectionModel().addListSelectionListener(createListSelectionListener());
/* 191:    */     }
/* 192:    */     
/* 193:    */     public ListSelectionModel getListSelectionModel()
/* 194:    */     {
/* 195:313 */       return this.listSelectionModel;
/* 196:    */     }
/* 197:    */     
/* 198:    */     public void resetRowSelection()
/* 199:    */     {
/* 200:323 */       if (!this.updatingListSelectionModel)
/* 201:    */       {
/* 202:324 */         this.updatingListSelectionModel = true;
/* 203:    */         try
/* 204:    */         {
/* 205:326 */           super.resetRowSelection();
/* 206:    */         }
/* 207:    */         finally
/* 208:    */         {
/* 209:329 */           this.updatingListSelectionModel = false;
/* 210:    */         }
/* 211:    */       }
/* 212:    */     }
/* 213:    */     
/* 214:    */     protected ListSelectionListener createListSelectionListener()
/* 215:    */     {
/* 216:343 */       return new ListSelectionHandler();
/* 217:    */     }
/* 218:    */     
/* 219:    */     protected void updateSelectedPathsFromSelectedRows()
/* 220:    */     {
/* 221:352 */       if (!this.updatingListSelectionModel)
/* 222:    */       {
/* 223:353 */         this.updatingListSelectionModel = true;
/* 224:    */         try
/* 225:    */         {
/* 226:357 */           int min = this.listSelectionModel.getMinSelectionIndex();
/* 227:358 */           int max = this.listSelectionModel.getMaxSelectionIndex();
/* 228:    */           
/* 229:360 */           clearSelection();
/* 230:361 */           if ((min != -1) && (max != -1)) {
/* 231:362 */             for (int counter = min; counter <= max; counter++) {
/* 232:363 */               if (this.listSelectionModel.isSelectedIndex(counter))
/* 233:    */               {
/* 234:364 */                 TreePath selPath = JTreeTable.this.tree.getPathForRow(counter);
/* 235:367 */                 if (selPath != null) {
/* 236:368 */                   addSelectionPath(selPath);
/* 237:    */                 }
/* 238:    */               }
/* 239:    */             }
/* 240:    */           }
/* 241:    */         }
/* 242:    */         finally
/* 243:    */         {
/* 244:375 */           this.updatingListSelectionModel = false;
/* 245:    */         }
/* 246:    */       }
/* 247:    */     }
/* 248:    */     
/* 249:    */     class ListSelectionHandler
/* 250:    */       implements ListSelectionListener
/* 251:    */     {
/* 252:    */       ListSelectionHandler() {}
/* 253:    */       
/* 254:    */       public void valueChanged(ListSelectionEvent e)
/* 255:    */       {
/* 256:386 */         JTreeTable.ListToTreeSelectionModelWrapper.this.updateSelectedPathsFromSelectedRows();
/* 257:    */       }
/* 258:    */     }
/* 259:    */   }
/* 260:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.tools.debugger.treetable.JTreeTable
 * JD-Core Version:    0.7.0.1
 */