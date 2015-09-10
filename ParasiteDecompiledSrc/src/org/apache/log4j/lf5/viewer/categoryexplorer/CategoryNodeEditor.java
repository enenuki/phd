/*   1:    */ package org.apache.log4j.lf5.viewer.categoryexplorer;
/*   2:    */ 
/*   3:    */ import java.awt.Component;
/*   4:    */ import java.awt.event.ActionEvent;
/*   5:    */ import java.awt.event.ActionListener;
/*   6:    */ import java.awt.event.InputEvent;
/*   7:    */ import java.awt.event.MouseAdapter;
/*   8:    */ import java.awt.event.MouseEvent;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.Enumeration;
/*  11:    */ import javax.swing.AbstractButton;
/*  12:    */ import javax.swing.JCheckBox;
/*  13:    */ import javax.swing.JMenuItem;
/*  14:    */ import javax.swing.JOptionPane;
/*  15:    */ import javax.swing.JPopupMenu;
/*  16:    */ import javax.swing.JTree;
/*  17:    */ import javax.swing.tree.DefaultMutableTreeNode;
/*  18:    */ import javax.swing.tree.DefaultTreeModel;
/*  19:    */ import javax.swing.tree.TreePath;
/*  20:    */ 
/*  21:    */ public class CategoryNodeEditor
/*  22:    */   extends CategoryAbstractCellEditor
/*  23:    */ {
/*  24:    */   protected CategoryNodeEditorRenderer _renderer;
/*  25:    */   protected CategoryNode _lastEditedNode;
/*  26:    */   protected JCheckBox _checkBox;
/*  27:    */   protected CategoryExplorerModel _categoryModel;
/*  28:    */   protected JTree _tree;
/*  29:    */   
/*  30:    */   public CategoryNodeEditor(CategoryExplorerModel model)
/*  31:    */   {
/*  32: 66 */     this._renderer = new CategoryNodeEditorRenderer();
/*  33: 67 */     this._checkBox = this._renderer.getCheckBox();
/*  34: 68 */     this._categoryModel = model;
/*  35:    */     
/*  36: 70 */     this._checkBox.addActionListener(new ActionListener()
/*  37:    */     {
/*  38:    */       public void actionPerformed(ActionEvent e)
/*  39:    */       {
/*  40: 72 */         CategoryNodeEditor.this._categoryModel.update(CategoryNodeEditor.this._lastEditedNode, CategoryNodeEditor.this._checkBox.isSelected());
/*  41: 73 */         CategoryNodeEditor.this.stopCellEditing();
/*  42:    */       }
/*  43: 76 */     });
/*  44: 77 */     this._renderer.addMouseListener(new MouseAdapter()
/*  45:    */     {
/*  46:    */       public void mousePressed(MouseEvent e)
/*  47:    */       {
/*  48: 79 */         if ((e.getModifiers() & 0x4) != 0) {
/*  49: 80 */           CategoryNodeEditor.this.showPopup(CategoryNodeEditor.this._lastEditedNode, e.getX(), e.getY());
/*  50:    */         }
/*  51: 82 */         CategoryNodeEditor.this.stopCellEditing();
/*  52:    */       }
/*  53:    */     });
/*  54:    */   }
/*  55:    */   
/*  56:    */   public Component getTreeCellEditorComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row)
/*  57:    */   {
/*  58: 94 */     this._lastEditedNode = ((CategoryNode)value);
/*  59: 95 */     this._tree = tree;
/*  60:    */     
/*  61: 97 */     return this._renderer.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, true);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public Object getCellEditorValue()
/*  65:    */   {
/*  66:104 */     return this._lastEditedNode.getUserObject();
/*  67:    */   }
/*  68:    */   
/*  69:    */   protected JMenuItem createPropertiesMenuItem(CategoryNode node)
/*  70:    */   {
/*  71:111 */     JMenuItem result = new JMenuItem("Properties");
/*  72:112 */     result.addActionListener(new ActionListener()
/*  73:    */     {
/*  74:    */       private final CategoryNode val$node;
/*  75:    */       
/*  76:    */       public void actionPerformed(ActionEvent e)
/*  77:    */       {
/*  78:114 */         CategoryNodeEditor.this.showPropertiesDialog(this.val$node);
/*  79:    */       }
/*  80:116 */     });
/*  81:117 */     return result;
/*  82:    */   }
/*  83:    */   
/*  84:    */   protected void showPropertiesDialog(CategoryNode node)
/*  85:    */   {
/*  86:121 */     JOptionPane.showMessageDialog(this._tree, getDisplayedProperties(node), "Category Properties: " + node.getTitle(), -1);
/*  87:    */   }
/*  88:    */   
/*  89:    */   protected Object getDisplayedProperties(CategoryNode node)
/*  90:    */   {
/*  91:130 */     ArrayList result = new ArrayList();
/*  92:131 */     result.add("Category: " + node.getTitle());
/*  93:132 */     if (node.hasFatalRecords()) {
/*  94:133 */       result.add("Contains at least one fatal LogRecord.");
/*  95:    */     }
/*  96:135 */     if (node.hasFatalChildren()) {
/*  97:136 */       result.add("Contains descendants with a fatal LogRecord.");
/*  98:    */     }
/*  99:138 */     result.add("LogRecords in this category alone: " + node.getNumberOfContainedRecords());
/* 100:    */     
/* 101:140 */     result.add("LogRecords in descendant categories: " + node.getNumberOfRecordsFromChildren());
/* 102:    */     
/* 103:142 */     result.add("LogRecords in this category including descendants: " + node.getTotalNumberOfRecords());
/* 104:    */     
/* 105:144 */     return result.toArray();
/* 106:    */   }
/* 107:    */   
/* 108:    */   protected void showPopup(CategoryNode node, int x, int y)
/* 109:    */   {
/* 110:148 */     JPopupMenu popup = new JPopupMenu();
/* 111:149 */     popup.setSize(150, 400);
/* 112:153 */     if (node.getParent() == null)
/* 113:    */     {
/* 114:154 */       popup.add(createRemoveMenuItem());
/* 115:155 */       popup.addSeparator();
/* 116:    */     }
/* 117:157 */     popup.add(createSelectDescendantsMenuItem(node));
/* 118:158 */     popup.add(createUnselectDescendantsMenuItem(node));
/* 119:159 */     popup.addSeparator();
/* 120:160 */     popup.add(createExpandMenuItem(node));
/* 121:161 */     popup.add(createCollapseMenuItem(node));
/* 122:162 */     popup.addSeparator();
/* 123:163 */     popup.add(createPropertiesMenuItem(node));
/* 124:164 */     popup.show(this._renderer, x, y);
/* 125:    */   }
/* 126:    */   
/* 127:    */   protected JMenuItem createSelectDescendantsMenuItem(CategoryNode node)
/* 128:    */   {
/* 129:168 */     JMenuItem selectDescendants = new JMenuItem("Select All Descendant Categories");
/* 130:    */     
/* 131:170 */     selectDescendants.addActionListener(new ActionListener()
/* 132:    */     {
/* 133:    */       private final CategoryNode val$node;
/* 134:    */       
/* 135:    */       public void actionPerformed(ActionEvent e)
/* 136:    */       {
/* 137:173 */         CategoryNodeEditor.this._categoryModel.setDescendantSelection(this.val$node, true);
/* 138:    */       }
/* 139:176 */     });
/* 140:177 */     return selectDescendants;
/* 141:    */   }
/* 142:    */   
/* 143:    */   protected JMenuItem createUnselectDescendantsMenuItem(CategoryNode node)
/* 144:    */   {
/* 145:181 */     JMenuItem unselectDescendants = new JMenuItem("Deselect All Descendant Categories");
/* 146:    */     
/* 147:183 */     unselectDescendants.addActionListener(new ActionListener()
/* 148:    */     {
/* 149:    */       private final CategoryNode val$node;
/* 150:    */       
/* 151:    */       public void actionPerformed(ActionEvent e)
/* 152:    */       {
/* 153:187 */         CategoryNodeEditor.this._categoryModel.setDescendantSelection(this.val$node, false);
/* 154:    */       }
/* 155:191 */     });
/* 156:192 */     return unselectDescendants;
/* 157:    */   }
/* 158:    */   
/* 159:    */   protected JMenuItem createExpandMenuItem(CategoryNode node)
/* 160:    */   {
/* 161:196 */     JMenuItem result = new JMenuItem("Expand All Descendant Categories");
/* 162:197 */     result.addActionListener(new ActionListener()
/* 163:    */     {
/* 164:    */       private final CategoryNode val$node;
/* 165:    */       
/* 166:    */       public void actionPerformed(ActionEvent e)
/* 167:    */       {
/* 168:199 */         CategoryNodeEditor.this.expandDescendants(this.val$node);
/* 169:    */       }
/* 170:201 */     });
/* 171:202 */     return result;
/* 172:    */   }
/* 173:    */   
/* 174:    */   protected JMenuItem createCollapseMenuItem(CategoryNode node)
/* 175:    */   {
/* 176:206 */     JMenuItem result = new JMenuItem("Collapse All Descendant Categories");
/* 177:207 */     result.addActionListener(new ActionListener()
/* 178:    */     {
/* 179:    */       private final CategoryNode val$node;
/* 180:    */       
/* 181:    */       public void actionPerformed(ActionEvent e)
/* 182:    */       {
/* 183:209 */         CategoryNodeEditor.this.collapseDescendants(this.val$node);
/* 184:    */       }
/* 185:211 */     });
/* 186:212 */     return result;
/* 187:    */   }
/* 188:    */   
/* 189:    */   protected JMenuItem createRemoveMenuItem()
/* 190:    */   {
/* 191:225 */     JMenuItem result = new JMenuItem("Remove All Empty Categories");
/* 192:226 */     result.addActionListener(new ActionListener()
/* 193:    */     {
/* 194:    */       public void actionPerformed(ActionEvent e)
/* 195:    */       {
/* 196:228 */         while (CategoryNodeEditor.this.removeUnusedNodes() > 0) {}
/* 197:    */       }
/* 198:230 */     });
/* 199:231 */     return result;
/* 200:    */   }
/* 201:    */   
/* 202:    */   protected void expandDescendants(CategoryNode node)
/* 203:    */   {
/* 204:235 */     Enumeration descendants = node.depthFirstEnumeration();
/* 205:237 */     while (descendants.hasMoreElements())
/* 206:    */     {
/* 207:238 */       CategoryNode current = (CategoryNode)descendants.nextElement();
/* 208:239 */       expand(current);
/* 209:    */     }
/* 210:    */   }
/* 211:    */   
/* 212:    */   protected void collapseDescendants(CategoryNode node)
/* 213:    */   {
/* 214:244 */     Enumeration descendants = node.depthFirstEnumeration();
/* 215:246 */     while (descendants.hasMoreElements())
/* 216:    */     {
/* 217:247 */       CategoryNode current = (CategoryNode)descendants.nextElement();
/* 218:248 */       collapse(current);
/* 219:    */     }
/* 220:    */   }
/* 221:    */   
/* 222:    */   protected int removeUnusedNodes()
/* 223:    */   {
/* 224:256 */     int count = 0;
/* 225:257 */     CategoryNode root = this._categoryModel.getRootCategoryNode();
/* 226:258 */     Enumeration enumeration = root.depthFirstEnumeration();
/* 227:259 */     while (enumeration.hasMoreElements())
/* 228:    */     {
/* 229:260 */       CategoryNode node = (CategoryNode)enumeration.nextElement();
/* 230:261 */       if ((node.isLeaf()) && (node.getNumberOfContainedRecords() == 0) && (node.getParent() != null))
/* 231:    */       {
/* 232:263 */         this._categoryModel.removeNodeFromParent(node);
/* 233:264 */         count++;
/* 234:    */       }
/* 235:    */     }
/* 236:268 */     return count;
/* 237:    */   }
/* 238:    */   
/* 239:    */   protected void expand(CategoryNode node)
/* 240:    */   {
/* 241:272 */     this._tree.expandPath(getTreePath(node));
/* 242:    */   }
/* 243:    */   
/* 244:    */   protected TreePath getTreePath(CategoryNode node)
/* 245:    */   {
/* 246:276 */     return new TreePath(node.getPath());
/* 247:    */   }
/* 248:    */   
/* 249:    */   protected void collapse(CategoryNode node)
/* 250:    */   {
/* 251:280 */     this._tree.collapsePath(getTreePath(node));
/* 252:    */   }
/* 253:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.lf5.viewer.categoryexplorer.CategoryNodeEditor
 * JD-Core Version:    0.7.0.1
 */