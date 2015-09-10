/*   1:    */ package org.apache.log4j.lf5.viewer.categoryexplorer;
/*   2:    */ 
/*   3:    */ import java.awt.AWTEventMulticaster;
/*   4:    */ import java.awt.event.ActionEvent;
/*   5:    */ import java.awt.event.ActionListener;
/*   6:    */ import java.util.Enumeration;
/*   7:    */ import javax.swing.SwingUtilities;
/*   8:    */ import javax.swing.tree.DefaultMutableTreeNode;
/*   9:    */ import javax.swing.tree.DefaultTreeModel;
/*  10:    */ import javax.swing.tree.TreeNode;
/*  11:    */ import javax.swing.tree.TreePath;
/*  12:    */ import org.apache.log4j.lf5.LogRecord;
/*  13:    */ 
/*  14:    */ public class CategoryExplorerModel
/*  15:    */   extends DefaultTreeModel
/*  16:    */ {
/*  17:    */   private static final long serialVersionUID = -3413887384316015901L;
/*  18: 53 */   protected boolean _renderFatal = true;
/*  19: 54 */   protected ActionListener _listener = null;
/*  20: 55 */   protected ActionEvent _event = new ActionEvent(this, 1001, "Nodes Selection changed");
/*  21:    */   
/*  22:    */   public CategoryExplorerModel(CategoryNode node)
/*  23:    */   {
/*  24: 68 */     super(node);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void addLogRecord(LogRecord lr)
/*  28:    */   {
/*  29: 75 */     CategoryPath path = new CategoryPath(lr.getCategory());
/*  30: 76 */     addCategory(path);
/*  31: 77 */     CategoryNode node = getCategoryNode(path);
/*  32: 78 */     node.addRecord();
/*  33: 79 */     if ((this._renderFatal) && (lr.isFatal()))
/*  34:    */     {
/*  35: 80 */       TreeNode[] nodes = getPathToRoot(node);
/*  36: 81 */       int len = nodes.length;
/*  37: 86 */       for (int i = 1; i < len - 1; i++)
/*  38:    */       {
/*  39: 87 */         CategoryNode parent = (CategoryNode)nodes[i];
/*  40: 88 */         parent.setHasFatalChildren(true);
/*  41: 89 */         nodeChanged(parent);
/*  42:    */       }
/*  43: 91 */       node.setHasFatalRecords(true);
/*  44: 92 */       nodeChanged(node);
/*  45:    */     }
/*  46:    */   }
/*  47:    */   
/*  48:    */   public CategoryNode getRootCategoryNode()
/*  49:    */   {
/*  50: 97 */     return (CategoryNode)getRoot();
/*  51:    */   }
/*  52:    */   
/*  53:    */   public CategoryNode getCategoryNode(String category)
/*  54:    */   {
/*  55:101 */     CategoryPath path = new CategoryPath(category);
/*  56:102 */     return getCategoryNode(path);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public CategoryNode getCategoryNode(CategoryPath path)
/*  60:    */   {
/*  61:109 */     CategoryNode root = (CategoryNode)getRoot();
/*  62:110 */     CategoryNode parent = root;
/*  63:112 */     for (int i = 0; i < path.size(); i++)
/*  64:    */     {
/*  65:113 */       CategoryElement element = path.categoryElementAt(i);
/*  66:    */       
/*  67:    */ 
/*  68:116 */       Enumeration children = parent.children();
/*  69:    */       
/*  70:118 */       boolean categoryAlreadyExists = false;
/*  71:119 */       while (children.hasMoreElements())
/*  72:    */       {
/*  73:120 */         CategoryNode node = (CategoryNode)children.nextElement();
/*  74:121 */         String title = node.getTitle().toLowerCase();
/*  75:    */         
/*  76:123 */         String pathLC = element.getTitle().toLowerCase();
/*  77:124 */         if (title.equals(pathLC))
/*  78:    */         {
/*  79:125 */           categoryAlreadyExists = true;
/*  80:    */           
/*  81:127 */           parent = node;
/*  82:128 */           break;
/*  83:    */         }
/*  84:    */       }
/*  85:132 */       if (!categoryAlreadyExists) {
/*  86:133 */         return null;
/*  87:    */       }
/*  88:    */     }
/*  89:137 */     return parent;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public boolean isCategoryPathActive(CategoryPath path)
/*  93:    */   {
/*  94:145 */     CategoryNode root = (CategoryNode)getRoot();
/*  95:146 */     CategoryNode parent = root;
/*  96:147 */     boolean active = false;
/*  97:149 */     for (int i = 0; i < path.size(); i++)
/*  98:    */     {
/*  99:150 */       CategoryElement element = path.categoryElementAt(i);
/* 100:    */       
/* 101:    */ 
/* 102:153 */       Enumeration children = parent.children();
/* 103:    */       
/* 104:155 */       boolean categoryAlreadyExists = false;
/* 105:156 */       active = false;
/* 106:158 */       while (children.hasMoreElements())
/* 107:    */       {
/* 108:159 */         CategoryNode node = (CategoryNode)children.nextElement();
/* 109:160 */         String title = node.getTitle().toLowerCase();
/* 110:    */         
/* 111:162 */         String pathLC = element.getTitle().toLowerCase();
/* 112:163 */         if (title.equals(pathLC))
/* 113:    */         {
/* 114:164 */           categoryAlreadyExists = true;
/* 115:    */           
/* 116:166 */           parent = node;
/* 117:168 */           if (!parent.isSelected()) {
/* 118:    */             break;
/* 119:    */           }
/* 120:169 */           active = true; break;
/* 121:    */         }
/* 122:    */       }
/* 123:176 */       if ((!active) || (!categoryAlreadyExists)) {
/* 124:177 */         return false;
/* 125:    */       }
/* 126:    */     }
/* 127:181 */     return active;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public CategoryNode addCategory(CategoryPath path)
/* 131:    */   {
/* 132:193 */     CategoryNode root = (CategoryNode)getRoot();
/* 133:194 */     CategoryNode parent = root;
/* 134:196 */     for (int i = 0; i < path.size(); i++)
/* 135:    */     {
/* 136:197 */       CategoryElement element = path.categoryElementAt(i);
/* 137:    */       
/* 138:    */ 
/* 139:200 */       Enumeration children = parent.children();
/* 140:    */       
/* 141:202 */       boolean categoryAlreadyExists = false;
/* 142:203 */       while (children.hasMoreElements())
/* 143:    */       {
/* 144:204 */         CategoryNode node = (CategoryNode)children.nextElement();
/* 145:205 */         String title = node.getTitle().toLowerCase();
/* 146:    */         
/* 147:207 */         String pathLC = element.getTitle().toLowerCase();
/* 148:208 */         if (title.equals(pathLC))
/* 149:    */         {
/* 150:209 */           categoryAlreadyExists = true;
/* 151:    */           
/* 152:211 */           parent = node;
/* 153:212 */           break;
/* 154:    */         }
/* 155:    */       }
/* 156:216 */       if (!categoryAlreadyExists)
/* 157:    */       {
/* 158:218 */         CategoryNode newNode = new CategoryNode(element.getTitle());
/* 159:    */         
/* 160:    */ 
/* 161:    */ 
/* 162:    */ 
/* 163:    */ 
/* 164:    */ 
/* 165:    */ 
/* 166:226 */         insertNodeInto(newNode, parent, parent.getChildCount());
/* 167:227 */         refresh(newNode);
/* 168:    */         
/* 169:    */ 
/* 170:230 */         parent = newNode;
/* 171:    */       }
/* 172:    */     }
/* 173:235 */     return parent;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void update(CategoryNode node, boolean selected)
/* 177:    */   {
/* 178:239 */     if (node.isSelected() == selected) {
/* 179:240 */       return;
/* 180:    */     }
/* 181:243 */     if (selected) {
/* 182:244 */       setParentSelection(node, true);
/* 183:    */     } else {
/* 184:246 */       setDescendantSelection(node, false);
/* 185:    */     }
/* 186:    */   }
/* 187:    */   
/* 188:    */   public void setDescendantSelection(CategoryNode node, boolean selected)
/* 189:    */   {
/* 190:251 */     Enumeration descendants = node.depthFirstEnumeration();
/* 191:253 */     while (descendants.hasMoreElements())
/* 192:    */     {
/* 193:254 */       CategoryNode current = (CategoryNode)descendants.nextElement();
/* 194:256 */       if (current.isSelected() != selected)
/* 195:    */       {
/* 196:257 */         current.setSelected(selected);
/* 197:258 */         nodeChanged(current);
/* 198:    */       }
/* 199:    */     }
/* 200:261 */     notifyActionListeners();
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void setParentSelection(CategoryNode node, boolean selected)
/* 204:    */   {
/* 205:265 */     TreeNode[] nodes = getPathToRoot(node);
/* 206:266 */     int len = nodes.length;
/* 207:271 */     for (int i = 1; i < len; i++)
/* 208:    */     {
/* 209:272 */       CategoryNode parent = (CategoryNode)nodes[i];
/* 210:273 */       if (parent.isSelected() != selected)
/* 211:    */       {
/* 212:274 */         parent.setSelected(selected);
/* 213:275 */         nodeChanged(parent);
/* 214:    */       }
/* 215:    */     }
/* 216:278 */     notifyActionListeners();
/* 217:    */   }
/* 218:    */   
/* 219:    */   public synchronized void addActionListener(ActionListener l)
/* 220:    */   {
/* 221:283 */     this._listener = AWTEventMulticaster.add(this._listener, l);
/* 222:    */   }
/* 223:    */   
/* 224:    */   public synchronized void removeActionListener(ActionListener l)
/* 225:    */   {
/* 226:287 */     this._listener = AWTEventMulticaster.remove(this._listener, l);
/* 227:    */   }
/* 228:    */   
/* 229:    */   public void resetAllNodeCounts()
/* 230:    */   {
/* 231:291 */     Enumeration nodes = getRootCategoryNode().depthFirstEnumeration();
/* 232:293 */     while (nodes.hasMoreElements())
/* 233:    */     {
/* 234:294 */       CategoryNode current = (CategoryNode)nodes.nextElement();
/* 235:295 */       current.resetNumberOfContainedRecords();
/* 236:296 */       nodeChanged(current);
/* 237:    */     }
/* 238:    */   }
/* 239:    */   
/* 240:    */   public TreePath getTreePathToRoot(CategoryNode node)
/* 241:    */   {
/* 242:307 */     if (node == null) {
/* 243:308 */       return null;
/* 244:    */     }
/* 245:310 */     return new TreePath(getPathToRoot(node));
/* 246:    */   }
/* 247:    */   
/* 248:    */   protected void notifyActionListeners()
/* 249:    */   {
/* 250:317 */     if (this._listener != null) {
/* 251:318 */       this._listener.actionPerformed(this._event);
/* 252:    */     }
/* 253:    */   }
/* 254:    */   
/* 255:    */   protected void refresh(CategoryNode node)
/* 256:    */   {
/* 257:326 */     SwingUtilities.invokeLater(new Runnable()
/* 258:    */     {
/* 259:    */       private final CategoryNode val$node;
/* 260:    */       
/* 261:    */       public void run()
/* 262:    */       {
/* 263:328 */         CategoryExplorerModel.this.nodeChanged(this.val$node);
/* 264:    */       }
/* 265:    */     });
/* 266:    */   }
/* 267:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.lf5.viewer.categoryexplorer.CategoryExplorerModel
 * JD-Core Version:    0.7.0.1
 */