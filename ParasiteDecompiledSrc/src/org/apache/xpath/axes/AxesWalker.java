/*   1:    */ package org.apache.xpath.axes;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ import javax.xml.transform.TransformerException;
/*   5:    */ import org.apache.xml.dtm.DTM;
/*   6:    */ import org.apache.xml.dtm.DTMAxisTraverser;
/*   7:    */ import org.apache.xpath.Expression;
/*   8:    */ import org.apache.xpath.ExpressionOwner;
/*   9:    */ import org.apache.xpath.XPathContext;
/*  10:    */ import org.apache.xpath.XPathVisitor;
/*  11:    */ import org.apache.xpath.compiler.Compiler;
/*  12:    */ import org.apache.xpath.res.XPATHMessages;
/*  13:    */ 
/*  14:    */ public class AxesWalker
/*  15:    */   extends PredicatedNodeTest
/*  16:    */   implements Cloneable, PathComponent, ExpressionOwner
/*  17:    */ {
/*  18:    */   static final long serialVersionUID = -2966031951306601247L;
/*  19:    */   private DTM m_dtm;
/*  20:    */   
/*  21:    */   public AxesWalker(LocPathIterator locPathIterator, int axis)
/*  22:    */   {
/*  23: 52 */     super(locPathIterator);
/*  24: 53 */     this.m_axis = axis;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public final WalkingIterator wi()
/*  28:    */   {
/*  29: 58 */     return (WalkingIterator)this.m_lpi;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void init(Compiler compiler, int opPos, int stepType)
/*  33:    */     throws TransformerException
/*  34:    */   {
/*  35: 75 */     initPredicateInfo(compiler, opPos);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public Object clone()
/*  39:    */     throws CloneNotSupportedException
/*  40:    */   {
/*  41: 91 */     AxesWalker clone = (AxesWalker)super.clone();
/*  42:    */     
/*  43:    */ 
/*  44:    */ 
/*  45:    */ 
/*  46:    */ 
/*  47: 97 */     return clone;
/*  48:    */   }
/*  49:    */   
/*  50:    */   AxesWalker cloneDeep(WalkingIterator cloneOwner, Vector cloneList)
/*  51:    */     throws CloneNotSupportedException
/*  52:    */   {
/*  53:116 */     AxesWalker clone = findClone(this, cloneList);
/*  54:117 */     if (null != clone) {
/*  55:118 */       return clone;
/*  56:    */     }
/*  57:119 */     clone = (AxesWalker)clone();
/*  58:120 */     clone.setLocPathIterator(cloneOwner);
/*  59:121 */     if (null != cloneList)
/*  60:    */     {
/*  61:123 */       cloneList.addElement(this);
/*  62:124 */       cloneList.addElement(clone);
/*  63:    */     }
/*  64:127 */     if (wi().m_lastUsedWalker == this) {
/*  65:128 */       cloneOwner.m_lastUsedWalker = clone;
/*  66:    */     }
/*  67:130 */     if (null != this.m_nextWalker) {
/*  68:131 */       clone.m_nextWalker = this.m_nextWalker.cloneDeep(cloneOwner, cloneList);
/*  69:    */     }
/*  70:135 */     if (null != cloneList)
/*  71:    */     {
/*  72:137 */       if (null != this.m_prevWalker) {
/*  73:138 */         clone.m_prevWalker = this.m_prevWalker.cloneDeep(cloneOwner, cloneList);
/*  74:    */       }
/*  75:    */     }
/*  76:142 */     else if (null != this.m_nextWalker) {
/*  77:143 */       clone.m_nextWalker.m_prevWalker = clone;
/*  78:    */     }
/*  79:145 */     return clone;
/*  80:    */   }
/*  81:    */   
/*  82:    */   static AxesWalker findClone(AxesWalker key, Vector cloneList)
/*  83:    */   {
/*  84:159 */     if (null != cloneList)
/*  85:    */     {
/*  86:162 */       int n = cloneList.size();
/*  87:163 */       for (int i = 0; i < n; i += 2) {
/*  88:165 */         if (key == cloneList.elementAt(i)) {
/*  89:166 */           return (AxesWalker)cloneList.elementAt(i + 1);
/*  90:    */         }
/*  91:    */       }
/*  92:    */     }
/*  93:169 */     return null;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void detach()
/*  97:    */   {
/*  98:179 */     this.m_currentNode = -1;
/*  99:180 */     this.m_dtm = null;
/* 100:181 */     this.m_traverser = null;
/* 101:182 */     this.m_isFresh = true;
/* 102:183 */     this.m_root = -1;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public int getRoot()
/* 106:    */   {
/* 107:196 */     return this.m_root;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public int getAnalysisBits()
/* 111:    */   {
/* 112:205 */     int axis = getAxis();
/* 113:206 */     int bit = WalkerFactory.getAnalysisBitFromAxes(axis);
/* 114:207 */     return bit;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void setRoot(int root)
/* 118:    */   {
/* 119:219 */     XPathContext xctxt = wi().getXPathContext();
/* 120:220 */     this.m_dtm = xctxt.getDTM(root);
/* 121:221 */     this.m_traverser = this.m_dtm.getAxisTraverser(this.m_axis);
/* 122:222 */     this.m_isFresh = true;
/* 123:223 */     this.m_foundLast = false;
/* 124:224 */     this.m_root = root;
/* 125:225 */     this.m_currentNode = root;
/* 126:227 */     if (-1 == root) {
/* 127:229 */       throw new RuntimeException(XPATHMessages.createXPATHMessage("ER_SETTING_WALKER_ROOT_TO_NULL", null));
/* 128:    */     }
/* 129:233 */     resetProximityPositions();
/* 130:    */   }
/* 131:    */   
/* 132:    */   public final int getCurrentNode()
/* 133:    */   {
/* 134:252 */     return this.m_currentNode;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setNextWalker(AxesWalker walker)
/* 138:    */   {
/* 139:263 */     this.m_nextWalker = walker;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public AxesWalker getNextWalker()
/* 143:    */   {
/* 144:274 */     return this.m_nextWalker;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setPrevWalker(AxesWalker walker)
/* 148:    */   {
/* 149:286 */     this.m_prevWalker = walker;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public AxesWalker getPrevWalker()
/* 153:    */   {
/* 154:298 */     return this.m_prevWalker;
/* 155:    */   }
/* 156:    */   
/* 157:    */   private int returnNextNode(int n)
/* 158:    */   {
/* 159:312 */     return n;
/* 160:    */   }
/* 161:    */   
/* 162:    */   protected int getNextNode()
/* 163:    */   {
/* 164:322 */     if (this.m_foundLast) {
/* 165:323 */       return -1;
/* 166:    */     }
/* 167:325 */     if (this.m_isFresh)
/* 168:    */     {
/* 169:327 */       this.m_currentNode = this.m_traverser.first(this.m_root);
/* 170:328 */       this.m_isFresh = false;
/* 171:    */     }
/* 172:333 */     else if (-1 != this.m_currentNode)
/* 173:    */     {
/* 174:335 */       this.m_currentNode = this.m_traverser.next(this.m_root, this.m_currentNode);
/* 175:    */     }
/* 176:338 */     if (-1 == this.m_currentNode) {
/* 177:339 */       this.m_foundLast = true;
/* 178:    */     }
/* 179:341 */     return this.m_currentNode;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public int nextNode()
/* 183:    */   {
/* 184:355 */     int nextNode = -1;
/* 185:356 */     AxesWalker walker = wi().getLastUsedWalker();
/* 186:360 */     while (null != walker)
/* 187:    */     {
/* 188:363 */       nextNode = walker.getNextNode();
/* 189:365 */       if (-1 == nextNode)
/* 190:    */       {
/* 191:368 */         walker = walker.m_prevWalker;
/* 192:    */       }
/* 193:372 */       else if (walker.acceptNode(nextNode) == 1)
/* 194:    */       {
/* 195:377 */         if (null == walker.m_nextWalker)
/* 196:    */         {
/* 197:379 */           wi().setLastUsedWalker(walker);
/* 198:    */           
/* 199:    */ 
/* 200:382 */           break;
/* 201:    */         }
/* 202:386 */         AxesWalker prev = walker;
/* 203:    */         
/* 204:388 */         walker = walker.m_nextWalker;
/* 205:    */         
/* 206:390 */         walker.setRoot(nextNode);
/* 207:    */         
/* 208:392 */         walker.m_prevWalker = prev;
/* 209:    */       }
/* 210:    */     }
/* 211:399 */     return nextNode;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public int getLastPos(XPathContext xctxt)
/* 215:    */   {
/* 216:415 */     int pos = getProximityPosition();
/* 217:    */     AxesWalker walker;
/* 218:    */     try
/* 219:    */     {
/* 220:421 */       walker = (AxesWalker)clone();
/* 221:    */     }
/* 222:    */     catch (CloneNotSupportedException cnse)
/* 223:    */     {
/* 224:425 */       return -1;
/* 225:    */     }
/* 226:428 */     walker.setPredicateCount(this.m_predicateIndex);
/* 227:429 */     walker.setNextWalker(null);
/* 228:430 */     walker.setPrevWalker(null);
/* 229:    */     
/* 230:432 */     WalkingIterator lpi = wi();
/* 231:433 */     AxesWalker savedWalker = lpi.getLastUsedWalker();
/* 232:    */     try
/* 233:    */     {
/* 234:437 */       lpi.setLastUsedWalker(walker);
/* 235:    */       int next;
/* 236:441 */       while (-1 != (next = walker.nextNode())) {
/* 237:443 */         pos++;
/* 238:    */       }
/* 239:    */     }
/* 240:    */     finally
/* 241:    */     {
/* 242:450 */       lpi.setLastUsedWalker(savedWalker);
/* 243:    */     }
/* 244:454 */     return pos;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public void setDefaultDTM(DTM dtm)
/* 248:    */   {
/* 249:474 */     this.m_dtm = dtm;
/* 250:    */   }
/* 251:    */   
/* 252:    */   public DTM getDTM(int node)
/* 253:    */   {
/* 254:485 */     return wi().getXPathContext().getDTM(node);
/* 255:    */   }
/* 256:    */   
/* 257:    */   public boolean isDocOrdered()
/* 258:    */   {
/* 259:497 */     return true;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public int getAxis()
/* 263:    */   {
/* 264:508 */     return this.m_axis;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public void callVisitors(ExpressionOwner owner, XPathVisitor visitor)
/* 268:    */   {
/* 269:522 */     if (visitor.visitStep(owner, this))
/* 270:    */     {
/* 271:524 */       callPredicateVisitors(visitor);
/* 272:525 */       if (null != this.m_nextWalker) {
/* 273:527 */         this.m_nextWalker.callVisitors(this, visitor);
/* 274:    */       }
/* 275:    */     }
/* 276:    */   }
/* 277:    */   
/* 278:    */   public Expression getExpression()
/* 279:    */   {
/* 280:537 */     return this.m_nextWalker;
/* 281:    */   }
/* 282:    */   
/* 283:    */   public void setExpression(Expression exp)
/* 284:    */   {
/* 285:545 */     exp.exprSetParent(this);
/* 286:546 */     this.m_nextWalker = ((AxesWalker)exp);
/* 287:    */   }
/* 288:    */   
/* 289:    */   public boolean deepEquals(Expression expr)
/* 290:    */   {
/* 291:554 */     if (!super.deepEquals(expr)) {
/* 292:555 */       return false;
/* 293:    */     }
/* 294:557 */     AxesWalker walker = (AxesWalker)expr;
/* 295:558 */     if (this.m_axis != walker.m_axis) {
/* 296:559 */       return false;
/* 297:    */     }
/* 298:561 */     return true;
/* 299:    */   }
/* 300:    */   
/* 301:567 */   transient int m_root = -1;
/* 302:572 */   private transient int m_currentNode = -1;
/* 303:    */   transient boolean m_isFresh;
/* 304:    */   protected AxesWalker m_nextWalker;
/* 305:    */   AxesWalker m_prevWalker;
/* 306:586 */   protected int m_axis = -1;
/* 307:    */   protected DTMAxisTraverser m_traverser;
/* 308:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.axes.AxesWalker
 * JD-Core Version:    0.7.0.1
 */