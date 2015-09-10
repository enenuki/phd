/*   1:    */ package org.apache.xpath.axes;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.io.Serializable;
/*   6:    */ import java.util.Vector;
/*   7:    */ import javax.xml.transform.TransformerException;
/*   8:    */ import org.apache.xml.dtm.DTM;
/*   9:    */ import org.apache.xml.dtm.DTMIterator;
/*  10:    */ import org.apache.xml.utils.WrappedRuntimeException;
/*  11:    */ import org.apache.xpath.Expression;
/*  12:    */ import org.apache.xpath.ExpressionOwner;
/*  13:    */ import org.apache.xpath.XPathVisitor;
/*  14:    */ import org.apache.xpath.compiler.Compiler;
/*  15:    */ import org.apache.xpath.compiler.OpMap;
/*  16:    */ 
/*  17:    */ public class UnionPathIterator
/*  18:    */   extends LocPathIterator
/*  19:    */   implements Cloneable, DTMIterator, Serializable, PathComponent
/*  20:    */ {
/*  21:    */   static final long serialVersionUID = -3910351546843826781L;
/*  22:    */   protected LocPathIterator[] m_exprs;
/*  23:    */   protected DTMIterator[] m_iterators;
/*  24:    */   
/*  25:    */   public UnionPathIterator()
/*  26:    */   {
/*  27: 56 */     this.m_iterators = null;
/*  28: 57 */     this.m_exprs = null;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void setRoot(int context, Object environment)
/*  32:    */   {
/*  33: 69 */     super.setRoot(context, environment);
/*  34:    */     try
/*  35:    */     {
/*  36: 73 */       if (null != this.m_exprs)
/*  37:    */       {
/*  38: 75 */         int n = this.m_exprs.length;
/*  39: 76 */         DTMIterator[] newIters = new DTMIterator[n];
/*  40: 78 */         for (int i = 0; i < n; i++)
/*  41:    */         {
/*  42: 80 */           DTMIterator iter = this.m_exprs[i].asIterator(this.m_execContext, context);
/*  43: 81 */           newIters[i] = iter;
/*  44: 82 */           iter.nextNode();
/*  45:    */         }
/*  46: 84 */         this.m_iterators = newIters;
/*  47:    */       }
/*  48:    */     }
/*  49:    */     catch (Exception e)
/*  50:    */     {
/*  51: 89 */       throw new WrappedRuntimeException(e);
/*  52:    */     }
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void addIterator(DTMIterator expr)
/*  56:    */   {
/*  57:103 */     if (null == this.m_iterators)
/*  58:    */     {
/*  59:105 */       this.m_iterators = new DTMIterator[1];
/*  60:106 */       this.m_iterators[0] = expr;
/*  61:    */     }
/*  62:    */     else
/*  63:    */     {
/*  64:110 */       DTMIterator[] exprs = this.m_iterators;
/*  65:111 */       int len = this.m_iterators.length;
/*  66:    */       
/*  67:113 */       this.m_iterators = new DTMIterator[len + 1];
/*  68:    */       
/*  69:115 */       System.arraycopy(exprs, 0, this.m_iterators, 0, len);
/*  70:    */       
/*  71:117 */       this.m_iterators[len] = expr;
/*  72:    */     }
/*  73:119 */     expr.nextNode();
/*  74:120 */     if ((expr instanceof Expression)) {
/*  75:121 */       ((Expression)expr).exprSetParent(this);
/*  76:    */     }
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void detach()
/*  80:    */   {
/*  81:133 */     if ((this.m_allowDetach) && (null != this.m_iterators))
/*  82:    */     {
/*  83:134 */       int n = this.m_iterators.length;
/*  84:135 */       for (int i = 0; i < n; i++) {
/*  85:137 */         this.m_iterators[i].detach();
/*  86:    */       }
/*  87:139 */       this.m_iterators = null;
/*  88:    */     }
/*  89:    */   }
/*  90:    */   
/*  91:    */   public UnionPathIterator(Compiler compiler, int opPos)
/*  92:    */     throws TransformerException
/*  93:    */   {
/*  94:162 */     opPos = OpMap.getFirstChildPos(opPos);
/*  95:    */     
/*  96:164 */     loadLocationPaths(compiler, opPos, 0);
/*  97:    */   }
/*  98:    */   
/*  99:    */   public static LocPathIterator createUnionIterator(Compiler compiler, int opPos)
/* 100:    */     throws TransformerException
/* 101:    */   {
/* 102:186 */     UnionPathIterator upi = new UnionPathIterator(compiler, opPos);
/* 103:187 */     int nPaths = upi.m_exprs.length;
/* 104:188 */     boolean isAllChildIterators = true;
/* 105:189 */     for (int i = 0; i < nPaths; i++)
/* 106:    */     {
/* 107:191 */       LocPathIterator lpi = upi.m_exprs[i];
/* 108:193 */       if (lpi.getAxis() != 3)
/* 109:    */       {
/* 110:195 */         isAllChildIterators = false;
/* 111:196 */         break;
/* 112:    */       }
/* 113:201 */       if (HasPositionalPredChecker.check(lpi))
/* 114:    */       {
/* 115:203 */         isAllChildIterators = false;
/* 116:204 */         break;
/* 117:    */       }
/* 118:    */     }
/* 119:208 */     if (isAllChildIterators)
/* 120:    */     {
/* 121:210 */       UnionChildIterator uci = new UnionChildIterator();
/* 122:212 */       for (int i = 0; i < nPaths; i++)
/* 123:    */       {
/* 124:214 */         PredicatedNodeTest lpi = upi.m_exprs[i];
/* 125:    */         
/* 126:    */ 
/* 127:    */ 
/* 128:218 */         uci.addNodeTest(lpi);
/* 129:    */       }
/* 130:220 */       return uci;
/* 131:    */     }
/* 132:224 */     return upi;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public int getAnalysisBits()
/* 136:    */   {
/* 137:233 */     int bits = 0;
/* 138:235 */     if (this.m_exprs != null)
/* 139:    */     {
/* 140:237 */       int n = this.m_exprs.length;
/* 141:239 */       for (int i = 0; i < n; i++)
/* 142:    */       {
/* 143:241 */         int bit = this.m_exprs[i].getAnalysisBits();
/* 144:242 */         bits |= bit;
/* 145:    */       }
/* 146:    */     }
/* 147:246 */     return bits;
/* 148:    */   }
/* 149:    */   
/* 150:    */   private void readObject(ObjectInputStream stream)
/* 151:    */     throws IOException, TransformerException
/* 152:    */   {
/* 153:    */     try
/* 154:    */     {
/* 155:262 */       stream.defaultReadObject();
/* 156:263 */       this.m_clones = new IteratorPool(this);
/* 157:    */     }
/* 158:    */     catch (ClassNotFoundException cnfe)
/* 159:    */     {
/* 160:267 */       throw new TransformerException(cnfe);
/* 161:    */     }
/* 162:    */   }
/* 163:    */   
/* 164:    */   public Object clone()
/* 165:    */     throws CloneNotSupportedException
/* 166:    */   {
/* 167:282 */     UnionPathIterator clone = (UnionPathIterator)super.clone();
/* 168:283 */     if (this.m_iterators != null)
/* 169:    */     {
/* 170:285 */       int n = this.m_iterators.length;
/* 171:    */       
/* 172:287 */       clone.m_iterators = new DTMIterator[n];
/* 173:289 */       for (int i = 0; i < n; i++) {
/* 174:291 */         clone.m_iterators[i] = ((DTMIterator)this.m_iterators[i].clone());
/* 175:    */       }
/* 176:    */     }
/* 177:295 */     return clone;
/* 178:    */   }
/* 179:    */   
/* 180:    */   protected LocPathIterator createDTMIterator(Compiler compiler, int opPos)
/* 181:    */     throws TransformerException
/* 182:    */   {
/* 183:313 */     LocPathIterator lpi = (LocPathIterator)WalkerFactory.newDTMIterator(compiler, opPos, compiler.getLocationPathDepth() <= 0);
/* 184:    */     
/* 185:315 */     return lpi;
/* 186:    */   }
/* 187:    */   
/* 188:    */   protected void loadLocationPaths(Compiler compiler, int opPos, int count)
/* 189:    */     throws TransformerException
/* 190:    */   {
/* 191:334 */     int steptype = compiler.getOp(opPos);
/* 192:336 */     if (steptype == 28)
/* 193:    */     {
/* 194:338 */       loadLocationPaths(compiler, compiler.getNextOpPos(opPos), count + 1);
/* 195:    */       
/* 196:340 */       this.m_exprs[count] = createDTMIterator(compiler, opPos);
/* 197:341 */       this.m_exprs[count].exprSetParent(this);
/* 198:    */     }
/* 199:    */     else
/* 200:    */     {
/* 201:348 */       switch (steptype)
/* 202:    */       {
/* 203:    */       case 22: 
/* 204:    */       case 23: 
/* 205:    */       case 24: 
/* 206:    */       case 25: 
/* 207:354 */         loadLocationPaths(compiler, compiler.getNextOpPos(opPos), count + 1);
/* 208:    */         
/* 209:356 */         WalkingIterator iter = new WalkingIterator(compiler.getNamespaceContext());
/* 210:    */         
/* 211:358 */         iter.exprSetParent(this);
/* 212:360 */         if (compiler.getLocationPathDepth() <= 0) {
/* 213:361 */           iter.setIsTopLevel(true);
/* 214:    */         }
/* 215:363 */         iter.m_firstWalker = new FilterExprWalker(iter);
/* 216:    */         
/* 217:365 */         iter.m_firstWalker.init(compiler, opPos, steptype);
/* 218:    */         
/* 219:367 */         this.m_exprs[count] = iter;
/* 220:368 */         break;
/* 221:    */       default: 
/* 222:370 */         this.m_exprs = new LocPathIterator[count];
/* 223:    */       }
/* 224:    */     }
/* 225:    */   }
/* 226:    */   
/* 227:    */   public int nextNode()
/* 228:    */   {
/* 229:384 */     if (this.m_foundLast) {
/* 230:385 */       return -1;
/* 231:    */     }
/* 232:389 */     int earliestNode = -1;
/* 233:391 */     if (null != this.m_iterators)
/* 234:    */     {
/* 235:393 */       int n = this.m_iterators.length;
/* 236:394 */       int iteratorUsed = -1;
/* 237:396 */       for (int i = 0; i < n; i++)
/* 238:    */       {
/* 239:398 */         int node = this.m_iterators[i].getCurrentNode();
/* 240:400 */         if (-1 != node) {
/* 241:402 */           if (-1 == earliestNode)
/* 242:    */           {
/* 243:404 */             iteratorUsed = i;
/* 244:405 */             earliestNode = node;
/* 245:    */           }
/* 246:409 */           else if (node == earliestNode)
/* 247:    */           {
/* 248:413 */             this.m_iterators[i].nextNode();
/* 249:    */           }
/* 250:    */           else
/* 251:    */           {
/* 252:417 */             DTM dtm = getDTM(node);
/* 253:419 */             if (dtm.isNodeAfter(node, earliestNode))
/* 254:    */             {
/* 255:421 */               iteratorUsed = i;
/* 256:422 */               earliestNode = node;
/* 257:    */             }
/* 258:    */           }
/* 259:    */         }
/* 260:    */       }
/* 261:428 */       if (-1 != earliestNode)
/* 262:    */       {
/* 263:430 */         this.m_iterators[iteratorUsed].nextNode();
/* 264:    */         
/* 265:432 */         incrementCurrentPos();
/* 266:    */       }
/* 267:    */       else
/* 268:    */       {
/* 269:435 */         this.m_foundLast = true;
/* 270:    */       }
/* 271:    */     }
/* 272:438 */     this.m_lastFetched = earliestNode;
/* 273:    */     
/* 274:440 */     return earliestNode;
/* 275:    */   }
/* 276:    */   
/* 277:    */   public void fixupVariables(Vector vars, int globalsSize)
/* 278:    */   {
/* 279:455 */     for (int i = 0; i < this.m_exprs.length; i++) {
/* 280:457 */       this.m_exprs[i].fixupVariables(vars, globalsSize);
/* 281:    */     }
/* 282:    */   }
/* 283:    */   
/* 284:    */   public int getAxis()
/* 285:    */   {
/* 286:488 */     return -1;
/* 287:    */   }
/* 288:    */   
/* 289:    */   class iterOwner
/* 290:    */     implements ExpressionOwner
/* 291:    */   {
/* 292:    */     int m_index;
/* 293:    */     
/* 294:    */     iterOwner(int index)
/* 295:    */     {
/* 296:497 */       this.m_index = index;
/* 297:    */     }
/* 298:    */     
/* 299:    */     public Expression getExpression()
/* 300:    */     {
/* 301:505 */       return UnionPathIterator.this.m_exprs[this.m_index];
/* 302:    */     }
/* 303:    */     
/* 304:    */     public void setExpression(Expression exp)
/* 305:    */     {
/* 306:514 */       if (!(exp instanceof LocPathIterator))
/* 307:    */       {
/* 308:518 */         WalkingIterator wi = new WalkingIterator(UnionPathIterator.this.getPrefixResolver());
/* 309:519 */         FilterExprWalker few = new FilterExprWalker(wi);
/* 310:520 */         wi.setFirstWalker(few);
/* 311:521 */         few.setInnerExpression(exp);
/* 312:522 */         wi.exprSetParent(UnionPathIterator.this);
/* 313:523 */         few.exprSetParent(wi);
/* 314:524 */         exp.exprSetParent(few);
/* 315:525 */         exp = wi;
/* 316:    */       }
/* 317:    */       else
/* 318:    */       {
/* 319:528 */         exp.exprSetParent(UnionPathIterator.this);
/* 320:    */       }
/* 321:529 */       UnionPathIterator.this.m_exprs[this.m_index] = ((LocPathIterator)exp);
/* 322:    */     }
/* 323:    */   }
/* 324:    */   
/* 325:    */   public void callVisitors(ExpressionOwner owner, XPathVisitor visitor)
/* 326:    */   {
/* 327:539 */     if (visitor.visitUnionPath(owner, this)) {
/* 328:541 */       if (null != this.m_exprs)
/* 329:    */       {
/* 330:543 */         int n = this.m_exprs.length;
/* 331:544 */         for (int i = 0; i < n; i++) {
/* 332:546 */           this.m_exprs[i].callVisitors(new iterOwner(i), visitor);
/* 333:    */         }
/* 334:    */       }
/* 335:    */     }
/* 336:    */   }
/* 337:    */   
/* 338:    */   public boolean deepEquals(Expression expr)
/* 339:    */   {
/* 340:557 */     if (!super.deepEquals(expr)) {
/* 341:558 */       return false;
/* 342:    */     }
/* 343:560 */     UnionPathIterator upi = (UnionPathIterator)expr;
/* 344:562 */     if (null != this.m_exprs)
/* 345:    */     {
/* 346:564 */       int n = this.m_exprs.length;
/* 347:566 */       if ((null == upi.m_exprs) || (upi.m_exprs.length != n)) {
/* 348:567 */         return false;
/* 349:    */       }
/* 350:569 */       for (int i = 0; i < n; i++) {
/* 351:571 */         if (!this.m_exprs[i].deepEquals(upi.m_exprs[i])) {
/* 352:572 */           return false;
/* 353:    */         }
/* 354:    */       }
/* 355:    */     }
/* 356:575 */     else if (null != upi.m_exprs)
/* 357:    */     {
/* 358:577 */       return false;
/* 359:    */     }
/* 360:580 */     return true;
/* 361:    */   }
/* 362:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.axes.UnionPathIterator
 * JD-Core Version:    0.7.0.1
 */