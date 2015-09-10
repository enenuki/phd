/*   1:    */ package org.apache.xpath.axes;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.util.Vector;
/*   6:    */ import javax.xml.transform.TransformerException;
/*   7:    */ import org.apache.xml.dtm.DTM;
/*   8:    */ import org.apache.xml.utils.PrefixResolver;
/*   9:    */ import org.apache.xml.utils.WrappedRuntimeException;
/*  10:    */ import org.apache.xpath.Expression;
/*  11:    */ import org.apache.xpath.ExpressionOwner;
/*  12:    */ import org.apache.xpath.XPathContext;
/*  13:    */ import org.apache.xpath.XPathVisitor;
/*  14:    */ import org.apache.xpath.compiler.Compiler;
/*  15:    */ import org.apache.xpath.compiler.OpMap;
/*  16:    */ import org.apache.xpath.objects.XObject;
/*  17:    */ import org.apache.xpath.patterns.NodeTest;
/*  18:    */ 
/*  19:    */ public abstract class PredicatedNodeTest
/*  20:    */   extends NodeTest
/*  21:    */   implements SubContextList
/*  22:    */ {
/*  23:    */   static final long serialVersionUID = -6193530757296377351L;
/*  24:    */   
/*  25:    */   PredicatedNodeTest(LocPathIterator locPathIterator)
/*  26:    */   {
/*  27: 45 */     this.m_lpi = locPathIterator;
/*  28:    */   }
/*  29:    */   
/*  30:    */   PredicatedNodeTest() {}
/*  31:    */   
/*  32:    */   private void readObject(ObjectInputStream stream)
/*  33:    */     throws IOException, TransformerException
/*  34:    */   {
/*  35:    */     try
/*  36:    */     {
/*  37: 69 */       stream.defaultReadObject();
/*  38: 70 */       this.m_predicateIndex = -1;
/*  39: 71 */       resetProximityPositions();
/*  40:    */     }
/*  41:    */     catch (ClassNotFoundException cnfe)
/*  42:    */     {
/*  43: 75 */       throw new TransformerException(cnfe);
/*  44:    */     }
/*  45:    */   }
/*  46:    */   
/*  47:    */   public Object clone()
/*  48:    */     throws CloneNotSupportedException
/*  49:    */   {
/*  50: 90 */     PredicatedNodeTest clone = (PredicatedNodeTest)super.clone();
/*  51: 92 */     if ((null != this.m_proximityPositions) && (this.m_proximityPositions == clone.m_proximityPositions))
/*  52:    */     {
/*  53: 95 */       clone.m_proximityPositions = new int[this.m_proximityPositions.length];
/*  54:    */       
/*  55: 97 */       System.arraycopy(this.m_proximityPositions, 0, clone.m_proximityPositions, 0, this.m_proximityPositions.length);
/*  56:    */     }
/*  57:102 */     if (clone.m_lpi == this) {
/*  58:103 */       clone.m_lpi = ((LocPathIterator)clone);
/*  59:    */     }
/*  60:105 */     return clone;
/*  61:    */   }
/*  62:    */   
/*  63:109 */   protected int m_predCount = -1;
/*  64:    */   
/*  65:    */   public int getPredicateCount()
/*  66:    */   {
/*  67:118 */     if (-1 == this.m_predCount) {
/*  68:119 */       return null == this.m_predicates ? 0 : this.m_predicates.length;
/*  69:    */     }
/*  70:121 */     return this.m_predCount;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setPredicateCount(int count)
/*  74:    */   {
/*  75:136 */     if (count > 0)
/*  76:    */     {
/*  77:138 */       Expression[] newPredicates = new Expression[count];
/*  78:139 */       for (int i = 0; i < count; i++) {
/*  79:141 */         newPredicates[i] = this.m_predicates[i];
/*  80:    */       }
/*  81:143 */       this.m_predicates = newPredicates;
/*  82:    */     }
/*  83:    */     else
/*  84:    */     {
/*  85:146 */       this.m_predicates = null;
/*  86:    */     }
/*  87:    */   }
/*  88:    */   
/*  89:    */   protected void initPredicateInfo(Compiler compiler, int opPos)
/*  90:    */     throws TransformerException
/*  91:    */   {
/*  92:163 */     int pos = compiler.getFirstPredicateOpPos(opPos);
/*  93:165 */     if (pos > 0)
/*  94:    */     {
/*  95:167 */       this.m_predicates = compiler.getCompiledPredicates(pos);
/*  96:168 */       if (null != this.m_predicates) {
/*  97:170 */         for (int i = 0; i < this.m_predicates.length; i++) {
/*  98:172 */           this.m_predicates[i].exprSetParent(this);
/*  99:    */         }
/* 100:    */       }
/* 101:    */     }
/* 102:    */   }
/* 103:    */   
/* 104:    */   public Expression getPredicate(int index)
/* 105:    */   {
/* 106:188 */     return this.m_predicates[index];
/* 107:    */   }
/* 108:    */   
/* 109:    */   public int getProximityPosition()
/* 110:    */   {
/* 111:200 */     return getProximityPosition(this.m_predicateIndex);
/* 112:    */   }
/* 113:    */   
/* 114:    */   public int getProximityPosition(XPathContext xctxt)
/* 115:    */   {
/* 116:212 */     return getProximityPosition();
/* 117:    */   }
/* 118:    */   
/* 119:    */   public abstract int getLastPos(XPathContext paramXPathContext);
/* 120:    */   
/* 121:    */   protected int getProximityPosition(int predicateIndex)
/* 122:    */   {
/* 123:235 */     return predicateIndex >= 0 ? this.m_proximityPositions[predicateIndex] : 0;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void resetProximityPositions()
/* 127:    */   {
/* 128:243 */     int nPredicates = getPredicateCount();
/* 129:244 */     if (nPredicates > 0)
/* 130:    */     {
/* 131:246 */       if (null == this.m_proximityPositions) {
/* 132:247 */         this.m_proximityPositions = new int[nPredicates];
/* 133:    */       }
/* 134:249 */       for (int i = 0; i < nPredicates; i++) {
/* 135:    */         try
/* 136:    */         {
/* 137:253 */           initProximityPosition(i);
/* 138:    */         }
/* 139:    */         catch (Exception e)
/* 140:    */         {
/* 141:258 */           throw new WrappedRuntimeException(e);
/* 142:    */         }
/* 143:    */       }
/* 144:    */     }
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void initProximityPosition(int i)
/* 148:    */     throws TransformerException
/* 149:    */   {
/* 150:273 */     this.m_proximityPositions[i] = 0;
/* 151:    */   }
/* 152:    */   
/* 153:    */   protected void countProximityPosition(int i)
/* 154:    */   {
/* 155:286 */     int[] pp = this.m_proximityPositions;
/* 156:287 */     if ((null != pp) && (i < pp.length)) {
/* 157:288 */       pp[i] += 1;
/* 158:    */     }
/* 159:    */   }
/* 160:    */   
/* 161:    */   public boolean isReverseAxes()
/* 162:    */   {
/* 163:298 */     return false;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public int getPredicateIndex()
/* 167:    */   {
/* 168:308 */     return this.m_predicateIndex;
/* 169:    */   }
/* 170:    */   
/* 171:    */   boolean executePredicates(int context, XPathContext xctxt)
/* 172:    */     throws TransformerException
/* 173:    */   {
/* 174:325 */     int nPredicates = getPredicateCount();
/* 175:327 */     if (nPredicates == 0) {
/* 176:328 */       return true;
/* 177:    */     }
/* 178:330 */     PrefixResolver savedResolver = xctxt.getNamespaceContext();
/* 179:    */     try
/* 180:    */     {
/* 181:334 */       this.m_predicateIndex = 0;
/* 182:335 */       xctxt.pushSubContextList(this);
/* 183:336 */       xctxt.pushNamespaceContext(this.m_lpi.getPrefixResolver());
/* 184:337 */       xctxt.pushCurrentNode(context);
/* 185:339 */       for (int i = 0; i < nPredicates; i++)
/* 186:    */       {
/* 187:342 */         XObject pred = this.m_predicates[i].execute(xctxt);
/* 188:    */         int proxPos;
/* 189:345 */         if (2 == pred.getType())
/* 190:    */         {
/* 191:357 */           proxPos = getProximityPosition(this.m_predicateIndex);
/* 192:358 */           int predIndex = (int)pred.num();
/* 193:359 */           if (proxPos != predIndex) {
/* 194:367 */             return false;
/* 195:    */           }
/* 196:385 */           if ((this.m_predicates[i].isStableNumber()) && (i == nPredicates - 1)) {
/* 197:387 */             this.m_foundLast = true;
/* 198:    */           }
/* 199:    */         }
/* 200:390 */         else if (!pred.bool())
/* 201:    */         {
/* 202:391 */           return 0;
/* 203:    */         }
/* 204:393 */         countProximityPosition(++this.m_predicateIndex);
/* 205:    */       }
/* 206:    */     }
/* 207:    */     finally
/* 208:    */     {
/* 209:398 */       xctxt.popCurrentNode();
/* 210:399 */       xctxt.popNamespaceContext();
/* 211:400 */       xctxt.popSubContextList();
/* 212:401 */       this.m_predicateIndex = -1;
/* 213:    */     }
/* 214:404 */     return true;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public void fixupVariables(Vector vars, int globalsSize)
/* 218:    */   {
/* 219:419 */     super.fixupVariables(vars, globalsSize);
/* 220:    */     
/* 221:421 */     int nPredicates = getPredicateCount();
/* 222:423 */     for (int i = 0; i < nPredicates; i++) {
/* 223:425 */       this.m_predicates[i].fixupVariables(vars, globalsSize);
/* 224:    */     }
/* 225:    */   }
/* 226:    */   
/* 227:    */   protected String nodeToString(int n)
/* 228:    */   {
/* 229:439 */     if (-1 != n)
/* 230:    */     {
/* 231:441 */       DTM dtm = this.m_lpi.getXPathContext().getDTM(n);
/* 232:442 */       return dtm.getNodeName(n) + "{" + (n + 1) + "}";
/* 233:    */     }
/* 234:446 */     return "null";
/* 235:    */   }
/* 236:    */   
/* 237:    */   public short acceptNode(int n)
/* 238:    */   {
/* 239:464 */     XPathContext xctxt = this.m_lpi.getXPathContext();
/* 240:    */     try
/* 241:    */     {
/* 242:468 */       xctxt.pushCurrentNode(n);
/* 243:    */       
/* 244:470 */       XObject score = execute(xctxt, n);
/* 245:473 */       if (score != NodeTest.SCORE_NONE)
/* 246:    */       {
/* 247:    */         short s;
/* 248:475 */         if (getPredicateCount() > 0)
/* 249:    */         {
/* 250:477 */           countProximityPosition(0);
/* 251:479 */           if (!executePredicates(n, xctxt)) {
/* 252:480 */             return 3;
/* 253:    */           }
/* 254:    */         }
/* 255:483 */         return 1;
/* 256:    */       }
/* 257:    */     }
/* 258:    */     catch (TransformerException se)
/* 259:    */     {
/* 260:490 */       throw new RuntimeException(se.getMessage());
/* 261:    */     }
/* 262:    */     finally
/* 263:    */     {
/* 264:494 */       xctxt.popCurrentNode();
/* 265:    */     }
/* 266:497 */     return 3;
/* 267:    */   }
/* 268:    */   
/* 269:    */   public LocPathIterator getLocPathIterator()
/* 270:    */   {
/* 271:508 */     return this.m_lpi;
/* 272:    */   }
/* 273:    */   
/* 274:    */   public void setLocPathIterator(LocPathIterator li)
/* 275:    */   {
/* 276:519 */     this.m_lpi = li;
/* 277:520 */     if (this != li) {
/* 278:521 */       li.exprSetParent(this);
/* 279:    */     }
/* 280:    */   }
/* 281:    */   
/* 282:    */   public boolean canTraverseOutsideSubtree()
/* 283:    */   {
/* 284:532 */     int n = getPredicateCount();
/* 285:533 */     for (int i = 0; i < n; i++) {
/* 286:535 */       if (getPredicate(i).canTraverseOutsideSubtree()) {
/* 287:536 */         return true;
/* 288:    */       }
/* 289:    */     }
/* 290:538 */     return false;
/* 291:    */   }
/* 292:    */   
/* 293:    */   public void callPredicateVisitors(XPathVisitor visitor)
/* 294:    */   {
/* 295:550 */     if (null != this.m_predicates)
/* 296:    */     {
/* 297:552 */       int n = this.m_predicates.length;
/* 298:553 */       for (int i = 0; i < n; i++)
/* 299:    */       {
/* 300:555 */         ExpressionOwner predOwner = new PredOwner(i);
/* 301:556 */         if (visitor.visitPredicate(predOwner, this.m_predicates[i])) {
/* 302:558 */           this.m_predicates[i].callVisitors(predOwner, visitor);
/* 303:    */         }
/* 304:    */       }
/* 305:    */     }
/* 306:    */   }
/* 307:    */   
/* 308:    */   public boolean deepEquals(Expression expr)
/* 309:    */   {
/* 310:570 */     if (!super.deepEquals(expr)) {
/* 311:571 */       return false;
/* 312:    */     }
/* 313:573 */     PredicatedNodeTest pnt = (PredicatedNodeTest)expr;
/* 314:574 */     if (null != this.m_predicates)
/* 315:    */     {
/* 316:577 */       int n = this.m_predicates.length;
/* 317:578 */       if ((null == pnt.m_predicates) || (pnt.m_predicates.length != n)) {
/* 318:579 */         return false;
/* 319:    */       }
/* 320:580 */       for (int i = 0; i < n; i++) {
/* 321:582 */         if (!this.m_predicates[i].deepEquals(pnt.m_predicates[i])) {
/* 322:583 */           return false;
/* 323:    */         }
/* 324:    */       }
/* 325:    */     }
/* 326:586 */     else if (null != pnt.m_predicates)
/* 327:    */     {
/* 328:587 */       return false;
/* 329:    */     }
/* 330:589 */     return true;
/* 331:    */   }
/* 332:    */   
/* 333:593 */   protected transient boolean m_foundLast = false;
/* 334:    */   protected LocPathIterator m_lpi;
/* 335:602 */   transient int m_predicateIndex = -1;
/* 336:    */   private Expression[] m_predicates;
/* 337:    */   protected transient int[] m_proximityPositions;
/* 338:    */   static final boolean DEBUG_PREDICATECOUNTING = false;
/* 339:    */   
/* 340:    */   class PredOwner
/* 341:    */     implements ExpressionOwner
/* 342:    */   {
/* 343:    */     int m_index;
/* 344:    */     
/* 345:    */     PredOwner(int index)
/* 346:    */     {
/* 347:625 */       this.m_index = index;
/* 348:    */     }
/* 349:    */     
/* 350:    */     public Expression getExpression()
/* 351:    */     {
/* 352:633 */       return PredicatedNodeTest.this.m_predicates[this.m_index];
/* 353:    */     }
/* 354:    */     
/* 355:    */     public void setExpression(Expression exp)
/* 356:    */     {
/* 357:642 */       exp.exprSetParent(PredicatedNodeTest.this);
/* 358:643 */       PredicatedNodeTest.this.m_predicates[this.m_index] = exp;
/* 359:    */     }
/* 360:    */   }
/* 361:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.axes.PredicatedNodeTest
 * JD-Core Version:    0.7.0.1
 */