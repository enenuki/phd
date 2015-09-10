/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.tools.debugger.downloaded;
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
/*  28:    */   protected TreeTableCellRenderer tree;
/*  29:    */   
/*  30:    */   public JTreeTable(TreeTableModel treeTableModel)
/*  31:    */   {
/*  32: 50 */     this.tree = new TreeTableCellRenderer(treeTableModel);
/*  33:    */     
/*  34:    */ 
/*  35: 53 */     super.setModel(new TreeTableModelAdapter(treeTableModel, this.tree));
/*  36:    */     
/*  37:    */ 
/*  38: 56 */     ListToTreeSelectionModelWrapper selectionWrapper = new ListToTreeSelectionModelWrapper();
/*  39:    */     
/*  40: 58 */     this.tree.setSelectionModel(selectionWrapper);
/*  41: 59 */     setSelectionModel(selectionWrapper.getListSelectionModel());
/*  42:    */     
/*  43:    */ 
/*  44: 62 */     setDefaultRenderer(TreeTableModel.class, this.tree);
/*  45: 63 */     setDefaultEditor(TreeTableModel.class, new TreeTableCellEditor());
/*  46:    */     
/*  47:    */ 
/*  48: 66 */     setShowGrid(false);
/*  49:    */     
/*  50:    */ 
/*  51: 69 */     setIntercellSpacing(new Dimension(0, 0));
/*  52: 73 */     if (this.tree.getRowHeight() < 1) {
/*  53: 75 */       setRowHeight(18);
/*  54:    */     }
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void updateUI()
/*  58:    */   {
/*  59: 85 */     super.updateUI();
/*  60: 86 */     if (this.tree != null) {
/*  61: 87 */       this.tree.updateUI();
/*  62:    */     }
/*  63: 91 */     LookAndFeel.installColorsAndFont(this, "Tree.background", "Tree.foreground", "Tree.font");
/*  64:    */   }
/*  65:    */   
/*  66:    */   public int getEditingRow()
/*  67:    */   {
/*  68:102 */     return getColumnClass(this.editingColumn) == TreeTableModel.class ? -1 : this.editingRow;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setRowHeight(int rowHeight)
/*  72:    */   {
/*  73:110 */     super.setRowHeight(rowHeight);
/*  74:111 */     if ((this.tree != null) && (this.tree.getRowHeight() != rowHeight)) {
/*  75:112 */       this.tree.setRowHeight(getRowHeight());
/*  76:    */     }
/*  77:    */   }
/*  78:    */   
/*  79:    */   public JTree getTree()
/*  80:    */   {
/*  81:120 */     return this.tree;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public class TreeTableCellRenderer
/*  85:    */     extends JTree
/*  86:    */     implements TableCellRenderer
/*  87:    */   {
/*  88:    */     protected int visibleRow;
/*  89:    */     
/*  90:    */     public TreeTableCellRenderer(TreeModel model)
/*  91:    */     {
/*  92:132 */       super();
/*  93:    */     }
/*  94:    */     
/*  95:    */     public void updateUI()
/*  96:    */     {
/*  97:140 */       super.updateUI();
/*  98:    */       
/*  99:    */ 
/* 100:143 */       TreeCellRenderer tcr = getCellRenderer();
/* 101:144 */       if ((tcr instanceof DefaultTreeCellRenderer))
/* 102:    */       {
/* 103:145 */         DefaultTreeCellRenderer dtcr = (DefaultTreeCellRenderer)tcr;
/* 104:    */         
/* 105:    */ 
/* 106:    */ 
/* 107:    */ 
/* 108:150 */         dtcr.setTextSelectionColor(UIManager.getColor("Table.selectionForeground"));
/* 109:    */         
/* 110:152 */         dtcr.setBackgroundSelectionColor(UIManager.getColor("Table.selectionBackground"));
/* 111:    */       }
/* 112:    */     }
/* 113:    */     
/* 114:    */     public void setRowHeight(int rowHeight)
/* 115:    */     {
/* 116:162 */       if (rowHeight > 0)
/* 117:    */       {
/* 118:163 */         super.setRowHeight(rowHeight);
/* 119:164 */         if ((JTreeTable.this != null) && (JTreeTable.this.getRowHeight() != rowHeight)) {
/* 120:166 */           JTreeTable.this.setRowHeight(getRowHeight());
/* 121:    */         }
/* 122:    */       }
/* 123:    */     }
/* 124:    */     
/* 125:    */     public void setBounds(int x, int y, int w, int h)
/* 126:    */     {
/* 127:175 */       super.setBounds(x, 0, w, JTreeTable.this.getHeight());
/* 128:    */     }
/* 129:    */     
/* 130:    */     public void paint(Graphics g)
/* 131:    */     {
/* 132:183 */       g.translate(0, -this.visibleRow * getRowHeight());
/* 133:184 */       super.paint(g);
/* 134:    */     }
/* 135:    */     
/* 136:    */     public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
/* 137:    */     {
/* 138:195 */       if (isSelected) {
/* 139:196 */         setBackground(table.getSelectionBackground());
/* 140:    */       } else {
/* 141:198 */         setBackground(table.getBackground());
/* 142:    */       }
/* 143:200 */       this.visibleRow = row;
/* 144:201 */       return this;
/* 145:    */     }
/* 146:    */   }
/* 147:    */   
/* 148:    */   public class TreeTableCellEditor
/* 149:    */     extends AbstractCellEditor
/* 150:    */     implements TableCellEditor
/* 151:    */   {
/* 152:    */     public TreeTableCellEditor() {}
/* 153:    */     
/* 154:    */     public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int r, int c)
/* 155:    */     {
/* 156:216 */       return JTreeTable.this.tree;
/* 157:    */     }
/* 158:    */     
/* 159:    */     public boolean isCellEditable(EventObject e)
/* 160:    */     {
/* 161:238 */       if ((e instanceof MouseEvent)) {
/* 162:239 */         for (int counter = JTreeTable.this.getColumnCount() - 1; counter >= 0; counter--) {
/* 163:241 */           if (JTreeTable.this.getColumnClass(counter) == TreeTableModel.class)
/* 164:    */           {
/* 165:242 */             MouseEvent me = (MouseEvent)e;
/* 166:243 */             MouseEvent newME = new MouseEvent(JTreeTable.this.tree, me.getID(), me.getWhen(), me.getModifiers(), me.getX() - JTreeTable.this.getCellRect(0, counter, true).x, me.getY(), me.getClickCount(), me.isPopupTrigger());
/* 167:    */             
/* 168:    */ 
/* 169:    */ 
/* 170:    */ 
/* 171:248 */             JTreeTable.this.tree.dispatchEvent(newME);
/* 172:249 */             break;
/* 173:    */           }
/* 174:    */         }
/* 175:    */       }
/* 176:253 */       return false;
/* 177:    */     }
/* 178:    */   }
/* 179:    */   
/* 180:    */   public class ListToTreeSelectionModelWrapper
/* 181:    */     extends DefaultTreeSelectionModel
/* 182:    */   {
/* 183:    */     protected boolean updatingListSelectionModel;
/* 184:    */     
/* 185:    */     public ListToTreeSelectionModelWrapper()
/* 186:    */     {
/* 187:270 */       getListSelectionModel().addListSelectionListener(createListSelectionListener());
/* 188:    */     }
/* 189:    */     
/* 190:    */     public ListSelectionModel getListSelectionModel()
/* 191:    */     {
/* 192:280 */       return this.listSelectionModel;
/* 193:    */     }
/* 194:    */     
/* 195:    */     public void resetRowSelection()
/* 196:    */     {
/* 197:289 */       if (!this.updatingListSelectionModel)
/* 198:    */       {
/* 199:290 */         this.updatingListSelectionModel = true;
/* 200:    */         try
/* 201:    */         {
/* 202:292 */           super.resetRowSelection();
/* 203:    */         }
/* 204:    */         finally
/* 205:    */         {
/* 206:295 */           this.updatingListSelectionModel = false;
/* 207:    */         }
/* 208:    */       }
/* 209:    */     }
/* 210:    */     
/* 211:    */     protected ListSelectionListener createListSelectionListener()
/* 212:    */     {
/* 213:309 */       return new ListSelectionHandler();
/* 214:    */     }
/* 215:    */     
/* 216:    */     protected void updateSelectedPathsFromSelectedRows()
/* 217:    */     {
/* 218:318 */       if (!this.updatingListSelectionModel)
/* 219:    */       {
/* 220:319 */         this.updatingListSelectionModel = true;
/* 221:    */         try
/* 222:    */         {
/* 223:323 */           int min = this.listSelectionModel.getMinSelectionIndex();
/* 224:324 */           int max = this.listSelectionModel.getMaxSelectionIndex();
/* 225:    */           
/* 226:326 */           clearSelection();
/* 227:327 */           if ((min != -1) && (max != -1)) {
/* 228:328 */             for (int counter = min; counter <= max; counter++) {
/* 229:329 */               if (this.listSelectionModel.isSelectedIndex(counter))
/* 230:    */               {
/* 231:330 */                 TreePath selPath = JTreeTable.this.tree.getPathForRow(counter);
/* 232:333 */                 if (selPath != null) {
/* 233:334 */                   addSelectionPath(selPath);
/* 234:    */                 }
/* 235:    */               }
/* 236:    */             }
/* 237:    */           }
/* 238:    */         }
/* 239:    */         finally
/* 240:    */         {
/* 241:341 */           this.updatingListSelectionModel = false;
/* 242:    */         }
/* 243:    */       }
/* 244:    */     }
/* 245:    */     
/* 246:    */     class ListSelectionHandler
/* 247:    */       implements ListSelectionListener
/* 248:    */     {
/* 249:    */       ListSelectionHandler() {}
/* 250:    */       
/* 251:    */       public void valueChanged(ListSelectionEvent e)
/* 252:    */       {
/* 253:352 */         JTreeTable.ListToTreeSelectionModelWrapper.this.updateSelectedPathsFromSelectedRows();
/* 254:    */       }
/* 255:    */     }
/* 256:    */   }
/* 257:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.tools.debugger.downloaded.JTreeTable
 * JD-Core Version:    0.7.0.1
 */