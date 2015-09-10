/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
/*   4:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*   5:    */ import org.w3c.dom.DOMException;
/*   6:    */ 
/*   7:    */ public class TreeWalker
/*   8:    */   extends SimpleScriptable
/*   9:    */ {
/*  10:    */   private Node root_;
/*  11:    */   private Node currentNode_;
/*  12:    */   private int whatToShow_;
/*  13:    */   private NodeFilter filter_;
/*  14:    */   private boolean expandEntityReferences_;
/*  15:    */   
/*  16:    */   public TreeWalker() {}
/*  17:    */   
/*  18:    */   public TreeWalker(Node root, int whatToShow, NodeFilter filter, Boolean expandEntityReferences)
/*  19:    */     throws DOMException
/*  20:    */   {
/*  21: 63 */     if (root == null) {
/*  22: 64 */       Context.throwAsScriptRuntimeEx(new DOMException((short)9, "root must not be null"));
/*  23:    */     }
/*  24: 67 */     this.root_ = root;
/*  25: 68 */     this.whatToShow_ = whatToShow;
/*  26: 69 */     this.filter_ = filter;
/*  27: 70 */     this.expandEntityReferences_ = expandEntityReferences.booleanValue();
/*  28: 71 */     this.currentNode_ = this.root_;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public Node jsxGet_root()
/*  32:    */   {
/*  33: 80 */     return this.root_;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public long jsxGet_whatToShow()
/*  37:    */   {
/*  38: 92 */     return this.whatToShow_ == -1 ? 4294967295L : this.whatToShow_;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public NodeFilter jsxGet_filter()
/*  42:    */   {
/*  43:101 */     return this.filter_;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public boolean jsxGet_expandEntityReferences()
/*  47:    */   {
/*  48:110 */     return this.expandEntityReferences_;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public Node jsxGet_currentNode()
/*  52:    */   {
/*  53:119 */     return this.currentNode_;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void jsxSet_currentNode(Node currentNode)
/*  57:    */     throws DOMException
/*  58:    */   {
/*  59:131 */     if (currentNode == null) {
/*  60:132 */       throw new DOMException((short)9, "currentNode cannot be set to null");
/*  61:    */     }
/*  62:135 */     this.currentNode_ = currentNode;
/*  63:    */   }
/*  64:    */   
/*  65:    */   private static int getFlagForNodeType(short type)
/*  66:    */   {
/*  67:146 */     switch (type)
/*  68:    */     {
/*  69:    */     case 1: 
/*  70:148 */       return 1;
/*  71:    */     case 2: 
/*  72:150 */       return 2;
/*  73:    */     case 3: 
/*  74:152 */       return 4;
/*  75:    */     case 4: 
/*  76:154 */       return 8;
/*  77:    */     case 5: 
/*  78:156 */       return 16;
/*  79:    */     case 6: 
/*  80:158 */       return 32;
/*  81:    */     case 7: 
/*  82:160 */       return 64;
/*  83:    */     case 8: 
/*  84:162 */       return 128;
/*  85:    */     case 9: 
/*  86:164 */       return 256;
/*  87:    */     case 10: 
/*  88:166 */       return 512;
/*  89:    */     case 11: 
/*  90:168 */       return 1024;
/*  91:    */     case 12: 
/*  92:170 */       return 2048;
/*  93:    */     }
/*  94:172 */     return 0;
/*  95:    */   }
/*  96:    */   
/*  97:    */   private short acceptNode(Node n)
/*  98:    */   {
/*  99:185 */     short type = n.jsxGet_nodeType();
/* 100:186 */     int flag = getFlagForNodeType(type);
/* 101:188 */     if ((this.whatToShow_ & flag) != 0) {
/* 102:189 */       return 1;
/* 103:    */     }
/* 104:192 */     return 3;
/* 105:    */   }
/* 106:    */   
/* 107:    */   private boolean isNodeVisible(Node n)
/* 108:    */   {
/* 109:197 */     if ((acceptNode(n) == 1) && (
/* 110:198 */       (this.filter_ == null) || (this.filter_.acceptNode(n) == 1)))
/* 111:    */     {
/* 112:199 */       if ((!this.expandEntityReferences_) && 
/* 113:200 */         (n.getParent() != null) && (n.getParent().jsxGet_nodeType() == 5)) {
/* 114:201 */         return false;
/* 115:    */       }
/* 116:204 */       return true;
/* 117:    */     }
/* 118:207 */     return false;
/* 119:    */   }
/* 120:    */   
/* 121:    */   private boolean isNodeRejected(Node n)
/* 122:    */   {
/* 123:212 */     if (acceptNode(n) == 2) {
/* 124:213 */       return true;
/* 125:    */     }
/* 126:215 */     if ((this.filter_ != null) && (this.filter_.acceptNode(n) == 2)) {
/* 127:216 */       return true;
/* 128:    */     }
/* 129:218 */     if ((!this.expandEntityReferences_) && 
/* 130:219 */       (n.getParent() != null) && (n.getParent().jsxGet_nodeType() == 5)) {
/* 131:220 */       return true;
/* 132:    */     }
/* 133:223 */     return false;
/* 134:    */   }
/* 135:    */   
/* 136:    */   private boolean isNodeSkipped(Node n)
/* 137:    */   {
/* 138:228 */     return (!isNodeVisible(n)) && (!isNodeRejected(n));
/* 139:    */   }
/* 140:    */   
/* 141:    */   public Node jsxFunction_parentNode()
/* 142:    */   {
/* 143:241 */     if (this.currentNode_ == this.root_) {
/* 144:242 */       return null;
/* 145:    */     }
/* 146:245 */     Node newNode = this.currentNode_;
/* 147:    */     do
/* 148:    */     {
/* 149:248 */       newNode = newNode.getParent();
/* 150:249 */     } while ((newNode != null) && (!isNodeVisible(newNode)) && (newNode != this.root_));
/* 151:251 */     if ((newNode == null) || (!isNodeVisible(newNode))) {
/* 152:252 */       return null;
/* 153:    */     }
/* 154:254 */     this.currentNode_ = newNode;
/* 155:255 */     return newNode;
/* 156:    */   }
/* 157:    */   
/* 158:    */   private Node getEquivalentLogical(Node n, boolean lookLeft)
/* 159:    */   {
/* 160:270 */     if (n == null) {
/* 161:271 */       return null;
/* 162:    */     }
/* 163:273 */     if (isNodeVisible(n)) {
/* 164:274 */       return n;
/* 165:    */     }
/* 166:278 */     if (isNodeSkipped(n))
/* 167:    */     {
/* 168:    */       Node child;
/* 169:    */       Node child;
/* 170:280 */       if (lookLeft) {
/* 171:281 */         child = getEquivalentLogical(n.jsxGet_lastChild(), lookLeft);
/* 172:    */       } else {
/* 173:284 */         child = getEquivalentLogical(n.jsxGet_firstChild(), lookLeft);
/* 174:    */       }
/* 175:287 */       if (child != null) {
/* 176:288 */         return child;
/* 177:    */       }
/* 178:    */     }
/* 179:294 */     return getSibling(n, lookLeft);
/* 180:    */   }
/* 181:    */   
/* 182:    */   private Node getSibling(Node n, boolean lookLeft)
/* 183:    */   {
/* 184:299 */     if (n == null) {
/* 185:300 */       return null;
/* 186:    */     }
/* 187:303 */     if (isNodeVisible(n)) {
/* 188:304 */       return null;
/* 189:    */     }
/* 190:    */     Node sibling;
/* 191:    */     Node sibling;
/* 192:308 */     if (lookLeft) {
/* 193:309 */       sibling = n.jsxGet_previousSibling();
/* 194:    */     } else {
/* 195:312 */       sibling = n.jsxGet_nextSibling();
/* 196:    */     }
/* 197:315 */     if (sibling == null)
/* 198:    */     {
/* 199:317 */       if (n == this.root_) {
/* 200:318 */         return null;
/* 201:    */       }
/* 202:320 */       return getSibling(n.getParent(), lookLeft);
/* 203:    */     }
/* 204:323 */     return getEquivalentLogical(sibling, lookLeft);
/* 205:    */   }
/* 206:    */   
/* 207:    */   public Node jsxFunction_firstChild()
/* 208:    */   {
/* 209:335 */     Node newNode = getEquivalentLogical(this.currentNode_.jsxGet_firstChild(), false);
/* 210:337 */     if (newNode != null) {
/* 211:338 */       this.currentNode_ = newNode;
/* 212:    */     }
/* 213:341 */     return newNode;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public Node jsxFunction_lastChild()
/* 217:    */   {
/* 218:353 */     Node newNode = getEquivalentLogical(this.currentNode_.jsxGet_lastChild(), true);
/* 219:355 */     if (newNode != null) {
/* 220:356 */       this.currentNode_ = newNode;
/* 221:    */     }
/* 222:359 */     return newNode;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public Node jsxFunction_previousSibling()
/* 226:    */   {
/* 227:371 */     if (this.currentNode_ == this.root_) {
/* 228:372 */       return null;
/* 229:    */     }
/* 230:375 */     Node newNode = getEquivalentLogical(this.currentNode_.jsxGet_previousSibling(), true);
/* 231:377 */     if (newNode != null) {
/* 232:378 */       this.currentNode_ = newNode;
/* 233:    */     }
/* 234:381 */     return newNode;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public Node jsxFunction_nextSibling()
/* 238:    */   {
/* 239:393 */     if (this.currentNode_ == this.root_) {
/* 240:394 */       return null;
/* 241:    */     }
/* 242:397 */     Node newNode = getEquivalentLogical(this.currentNode_.jsxGet_nextSibling(), false);
/* 243:399 */     if (newNode != null) {
/* 244:400 */       this.currentNode_ = newNode;
/* 245:    */     }
/* 246:403 */     return newNode;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public Node jsxFunction_previousNode()
/* 250:    */   {
/* 251:417 */     Node newNode = getPreviousNode(this.currentNode_);
/* 252:419 */     if (newNode != null) {
/* 253:420 */       this.currentNode_ = newNode;
/* 254:    */     }
/* 255:423 */     return newNode;
/* 256:    */   }
/* 257:    */   
/* 258:    */   private Node getPreviousNode(Node n)
/* 259:    */   {
/* 260:431 */     if (n == this.root_) {
/* 261:432 */       return null;
/* 262:    */     }
/* 263:434 */     Node left = getEquivalentLogical(n.jsxGet_previousSibling(), true);
/* 264:435 */     if (left == null)
/* 265:    */     {
/* 266:436 */       Node parent = n.getParent();
/* 267:437 */       if (parent == null) {
/* 268:438 */         return null;
/* 269:    */       }
/* 270:440 */       if (isNodeVisible(parent)) {
/* 271:441 */         return parent;
/* 272:    */       }
/* 273:    */     }
/* 274:445 */     Node follow = left;
/* 275:446 */     while (follow.jsxFunction_hasChildNodes())
/* 276:    */     {
/* 277:447 */       Node toFollow = getEquivalentLogical(follow.jsxGet_lastChild(), true);
/* 278:448 */       if (toFollow == null) {
/* 279:    */         break;
/* 280:    */       }
/* 281:451 */       follow = toFollow;
/* 282:    */     }
/* 283:453 */     return follow;
/* 284:    */   }
/* 285:    */   
/* 286:    */   public Node jsxFunction_nextNode()
/* 287:    */   {
/* 288:467 */     Node leftChild = getEquivalentLogical(this.currentNode_.jsxGet_firstChild(), false);
/* 289:468 */     if (leftChild != null)
/* 290:    */     {
/* 291:469 */       this.currentNode_ = leftChild;
/* 292:470 */       return leftChild;
/* 293:    */     }
/* 294:472 */     Node rightSibling = getEquivalentLogical(this.currentNode_.jsxGet_nextSibling(), false);
/* 295:473 */     if (rightSibling != null)
/* 296:    */     {
/* 297:474 */       this.currentNode_ = rightSibling;
/* 298:475 */       return rightSibling;
/* 299:    */     }
/* 300:478 */     Node uncle = getFirstUncleNode(this.currentNode_);
/* 301:479 */     if (uncle != null)
/* 302:    */     {
/* 303:480 */       this.currentNode_ = uncle;
/* 304:481 */       return uncle;
/* 305:    */     }
/* 306:484 */     return null;
/* 307:    */   }
/* 308:    */   
/* 309:    */   private Node getFirstUncleNode(Node n)
/* 310:    */   {
/* 311:492 */     if ((n == this.root_) || (n == null)) {
/* 312:493 */       return null;
/* 313:    */     }
/* 314:496 */     Node parent = n.getParent();
/* 315:497 */     if (parent == null) {
/* 316:498 */       return null;
/* 317:    */     }
/* 318:501 */     Node uncle = getEquivalentLogical(parent.jsxGet_nextSibling(), false);
/* 319:502 */     if (uncle != null) {
/* 320:503 */       return uncle;
/* 321:    */     }
/* 322:506 */     return getFirstUncleNode(parent);
/* 323:    */   }
/* 324:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.TreeWalker
 * JD-Core Version:    0.7.0.1
 */