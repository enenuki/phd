/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ import org.apache.bcel.generic.ALOAD;
/*   5:    */ import org.apache.bcel.generic.ASTORE;
/*   6:    */ import org.apache.bcel.generic.CHECKCAST;
/*   7:    */ import org.apache.bcel.generic.ClassGen;
/*   8:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*   9:    */ import org.apache.bcel.generic.ICONST;
/*  10:    */ import org.apache.bcel.generic.ILOAD;
/*  11:    */ import org.apache.bcel.generic.INVOKEINTERFACE;
/*  12:    */ import org.apache.bcel.generic.INVOKESPECIAL;
/*  13:    */ import org.apache.bcel.generic.ISTORE;
/*  14:    */ import org.apache.bcel.generic.InstructionConstants;
/*  15:    */ import org.apache.bcel.generic.InstructionList;
/*  16:    */ import org.apache.bcel.generic.LocalVariableGen;
/*  17:    */ import org.apache.bcel.generic.MethodGen;
/*  18:    */ import org.apache.bcel.generic.NEW;
/*  19:    */ import org.apache.bcel.generic.PUSH;
/*  20:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  21:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  22:    */ import org.apache.xalan.xsltc.compiler.util.Type;
/*  23:    */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*  24:    */ import org.apache.xalan.xsltc.compiler.util.Util;
/*  25:    */ import org.apache.xml.dtm.Axis;
/*  26:    */ 
/*  27:    */ final class Step
/*  28:    */   extends RelativeLocationPath
/*  29:    */ {
/*  30:    */   private int _axis;
/*  31:    */   private Vector _predicates;
/*  32: 70 */   private boolean _hadPredicates = false;
/*  33:    */   private int _nodeType;
/*  34:    */   
/*  35:    */   public Step(int axis, int nodeType, Vector predicates)
/*  36:    */   {
/*  37: 78 */     this._axis = axis;
/*  38: 79 */     this._nodeType = nodeType;
/*  39: 80 */     this._predicates = predicates;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void setParser(Parser parser)
/*  43:    */   {
/*  44: 87 */     super.setParser(parser);
/*  45: 88 */     if (this._predicates != null)
/*  46:    */     {
/*  47: 89 */       int n = this._predicates.size();
/*  48: 90 */       for (int i = 0; i < n; i++)
/*  49:    */       {
/*  50: 91 */         Predicate exp = (Predicate)this._predicates.elementAt(i);
/*  51: 92 */         exp.setParser(parser);
/*  52: 93 */         exp.setParent(this);
/*  53:    */       }
/*  54:    */     }
/*  55:    */   }
/*  56:    */   
/*  57:    */   public int getAxis()
/*  58:    */   {
/*  59:102 */     return this._axis;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void setAxis(int axis)
/*  63:    */   {
/*  64:109 */     this._axis = axis;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public int getNodeType()
/*  68:    */   {
/*  69:116 */     return this._nodeType;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public Vector getPredicates()
/*  73:    */   {
/*  74:123 */     return this._predicates;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void addPredicates(Vector predicates)
/*  78:    */   {
/*  79:130 */     if (this._predicates == null) {
/*  80:131 */       this._predicates = predicates;
/*  81:    */     } else {
/*  82:134 */       this._predicates.addAll(predicates);
/*  83:    */     }
/*  84:    */   }
/*  85:    */   
/*  86:    */   private boolean hasParentPattern()
/*  87:    */   {
/*  88:144 */     SyntaxTreeNode parent = getParent();
/*  89:145 */     return ((parent instanceof ParentPattern)) || ((parent instanceof ParentLocationPath)) || ((parent instanceof UnionPathExpr)) || ((parent instanceof FilterParentPath));
/*  90:    */   }
/*  91:    */   
/*  92:    */   private boolean hasPredicates()
/*  93:    */   {
/*  94:155 */     return (this._predicates != null) && (this._predicates.size() > 0);
/*  95:    */   }
/*  96:    */   
/*  97:    */   private boolean isPredicate()
/*  98:    */   {
/*  99:162 */     SyntaxTreeNode parent = this;
/* 100:163 */     while (parent != null)
/* 101:    */     {
/* 102:164 */       parent = parent.getParent();
/* 103:165 */       if ((parent instanceof Predicate)) {
/* 104:165 */         return true;
/* 105:    */       }
/* 106:    */     }
/* 107:167 */     return false;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public boolean isAbbreviatedDot()
/* 111:    */   {
/* 112:174 */     return (this._nodeType == -1) && (this._axis == 13);
/* 113:    */   }
/* 114:    */   
/* 115:    */   public boolean isAbbreviatedDDot()
/* 116:    */   {
/* 117:182 */     return (this._nodeType == -1) && (this._axis == 10);
/* 118:    */   }
/* 119:    */   
/* 120:    */   public Type typeCheck(SymbolTable stable)
/* 121:    */     throws TypeCheckError
/* 122:    */   {
/* 123:194 */     this._hadPredicates = hasPredicates();
/* 124:199 */     if (isAbbreviatedDot()) {
/* 125:200 */       this._type = ((hasParentPattern()) || (hasPredicates()) ? Type.NodeSet : Type.Node);
/* 126:    */     } else {
/* 127:204 */       this._type = Type.NodeSet;
/* 128:    */     }
/* 129:208 */     if (this._predicates != null)
/* 130:    */     {
/* 131:209 */       int n = this._predicates.size();
/* 132:210 */       for (int i = 0; i < n; i++)
/* 133:    */       {
/* 134:211 */         Expression pred = (Expression)this._predicates.elementAt(i);
/* 135:212 */         pred.typeCheck(stable);
/* 136:    */       }
/* 137:    */     }
/* 138:217 */     return this._type;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/* 142:    */   {
/* 143:228 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 144:229 */     InstructionList il = methodGen.getInstructionList();
/* 145:231 */     if (hasPredicates())
/* 146:    */     {
/* 147:232 */       translatePredicates(classGen, methodGen);
/* 148:    */     }
/* 149:    */     else
/* 150:    */     {
/* 151:234 */       int star = 0;
/* 152:235 */       String name = null;
/* 153:236 */       XSLTC xsltc = getParser().getXSLTC();
/* 154:238 */       if (this._nodeType >= 14)
/* 155:    */       {
/* 156:239 */         Vector ni = xsltc.getNamesIndex();
/* 157:    */         
/* 158:241 */         name = (String)ni.elementAt(this._nodeType - 14);
/* 159:242 */         star = name.lastIndexOf('*');
/* 160:    */       }
/* 161:247 */       if ((this._axis == 2) && (this._nodeType != 2) && (this._nodeType != -1) && (!hasParentPattern()) && (star == 0))
/* 162:    */       {
/* 163:251 */         int iter = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "getTypedAxisIterator", "(II)Lorg/apache/xml/dtm/DTMAxisIterator;");
/* 164:    */         
/* 165:    */ 
/* 166:254 */         il.append(methodGen.loadDOM());
/* 167:255 */         il.append(new PUSH(cpg, 2));
/* 168:256 */         il.append(new PUSH(cpg, this._nodeType));
/* 169:257 */         il.append(new INVOKEINTERFACE(iter, 3));
/* 170:258 */         return;
/* 171:    */       }
/* 172:261 */       SyntaxTreeNode parent = getParent();
/* 173:263 */       if (isAbbreviatedDot())
/* 174:    */       {
/* 175:264 */         if (this._type == Type.Node)
/* 176:    */         {
/* 177:266 */           il.append(methodGen.loadContextNode());
/* 178:    */         }
/* 179:269 */         else if ((parent instanceof ParentLocationPath))
/* 180:    */         {
/* 181:271 */           int init = cpg.addMethodref("org.apache.xalan.xsltc.dom.SingletonIterator", "<init>", "(I)V");
/* 182:    */           
/* 183:    */ 
/* 184:274 */           il.append(new NEW(cpg.addClass("org.apache.xalan.xsltc.dom.SingletonIterator")));
/* 185:275 */           il.append(InstructionConstants.DUP);
/* 186:276 */           il.append(methodGen.loadContextNode());
/* 187:277 */           il.append(new INVOKESPECIAL(init));
/* 188:    */         }
/* 189:    */         else
/* 190:    */         {
/* 191:280 */           int git = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "getAxisIterator", "(I)Lorg/apache/xml/dtm/DTMAxisIterator;");
/* 192:    */           
/* 193:    */ 
/* 194:283 */           il.append(methodGen.loadDOM());
/* 195:284 */           il.append(new PUSH(cpg, this._axis));
/* 196:285 */           il.append(new INVOKEINTERFACE(git, 2));
/* 197:    */         }
/* 198:288 */         return;
/* 199:    */       }
/* 200:292 */       if (((parent instanceof ParentLocationPath)) && ((parent.getParent() instanceof ParentLocationPath))) {
/* 201:294 */         if ((this._nodeType == 1) && (!this._hadPredicates)) {
/* 202:295 */           this._nodeType = -1;
/* 203:    */         }
/* 204:    */       }
/* 205:300 */       switch (this._nodeType)
/* 206:    */       {
/* 207:    */       case 2: 
/* 208:302 */         this._axis = 2;
/* 209:    */       case -1: 
/* 210:305 */         int git = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "getAxisIterator", "(I)Lorg/apache/xml/dtm/DTMAxisIterator;");
/* 211:    */         
/* 212:    */ 
/* 213:308 */         il.append(methodGen.loadDOM());
/* 214:309 */         il.append(new PUSH(cpg, this._axis));
/* 215:310 */         il.append(new INVOKEINTERFACE(git, 2));
/* 216:311 */         break;
/* 217:    */       case 0: 
/* 218:    */       default: 
/* 219:313 */         if (star > 1)
/* 220:    */         {
/* 221:    */           String namespace;
/* 222:315 */           if (this._axis == 2) {
/* 223:316 */             namespace = name.substring(0, star - 2);
/* 224:    */           } else {
/* 225:318 */             namespace = name.substring(0, star - 1);
/* 226:    */           }
/* 227:320 */           int nsType = xsltc.registerNamespace(namespace);
/* 228:321 */           int ns = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "getNamespaceAxisIterator", "(II)Lorg/apache/xml/dtm/DTMAxisIterator;");
/* 229:    */           
/* 230:    */ 
/* 231:324 */           il.append(methodGen.loadDOM());
/* 232:325 */           il.append(new PUSH(cpg, this._axis));
/* 233:326 */           il.append(new PUSH(cpg, nsType));
/* 234:327 */           il.append(new INVOKEINTERFACE(ns, 3));
/* 235:    */         }
/* 236:328 */         break;
/* 237:    */       }
/* 238:332 */       int ty = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "getTypedAxisIterator", "(II)Lorg/apache/xml/dtm/DTMAxisIterator;");
/* 239:    */       
/* 240:    */ 
/* 241:    */ 
/* 242:336 */       il.append(methodGen.loadDOM());
/* 243:337 */       il.append(new PUSH(cpg, this._axis));
/* 244:338 */       il.append(new PUSH(cpg, this._nodeType));
/* 245:339 */       il.append(new INVOKEINTERFACE(ty, 3));
/* 246:    */     }
/* 247:    */   }
/* 248:    */   
/* 249:    */   public void translatePredicates(ClassGenerator classGen, MethodGenerator methodGen)
/* 250:    */   {
/* 251:355 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 252:356 */     InstructionList il = methodGen.getInstructionList();
/* 253:    */     
/* 254:358 */     int idx = 0;
/* 255:360 */     if (this._predicates.size() == 0)
/* 256:    */     {
/* 257:361 */       translate(classGen, methodGen);
/* 258:    */     }
/* 259:    */     else
/* 260:    */     {
/* 261:364 */       Predicate predicate = (Predicate)this._predicates.lastElement();
/* 262:365 */       this._predicates.remove(predicate);
/* 263:374 */       if (predicate.isNodeValueTest())
/* 264:    */       {
/* 265:375 */         Step step = predicate.getStep();
/* 266:    */         
/* 267:377 */         il.append(methodGen.loadDOM());
/* 268:380 */         if (step.isAbbreviatedDot())
/* 269:    */         {
/* 270:381 */           translate(classGen, methodGen);
/* 271:382 */           il.append(new ICONST(0));
/* 272:    */         }
/* 273:    */         else
/* 274:    */         {
/* 275:387 */           ParentLocationPath path = new ParentLocationPath(this, step);
/* 276:    */           try
/* 277:    */           {
/* 278:389 */             path.typeCheck(getParser().getSymbolTable());
/* 279:    */           }
/* 280:    */           catch (TypeCheckError e) {}
/* 281:392 */           path.translate(classGen, methodGen);
/* 282:393 */           il.append(new ICONST(1));
/* 283:    */         }
/* 284:395 */         predicate.translate(classGen, methodGen);
/* 285:396 */         idx = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "getNodeValueIterator", "(Lorg/apache/xml/dtm/DTMAxisIterator;ILjava/lang/String;Z)Lorg/apache/xml/dtm/DTMAxisIterator;");
/* 286:    */         
/* 287:    */ 
/* 288:399 */         il.append(new INVOKEINTERFACE(idx, 5));
/* 289:    */       }
/* 290:402 */       else if (predicate.isNthDescendant())
/* 291:    */       {
/* 292:403 */         il.append(methodGen.loadDOM());
/* 293:    */         
/* 294:405 */         il.append(new ICONST(predicate.getPosType()));
/* 295:406 */         predicate.translate(classGen, methodGen);
/* 296:407 */         il.append(new ICONST(0));
/* 297:408 */         idx = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "getNthDescendant", "(IIZ)Lorg/apache/xml/dtm/DTMAxisIterator;");
/* 298:    */         
/* 299:    */ 
/* 300:411 */         il.append(new INVOKEINTERFACE(idx, 4));
/* 301:    */       }
/* 302:414 */       else if (predicate.isNthPositionFilter())
/* 303:    */       {
/* 304:415 */         idx = cpg.addMethodref("org.apache.xalan.xsltc.dom.NthIterator", "<init>", "(Lorg/apache/xml/dtm/DTMAxisIterator;I)V");
/* 305:    */         
/* 306:    */ 
/* 307:    */ 
/* 308:    */ 
/* 309:    */ 
/* 310:    */ 
/* 311:    */ 
/* 312:    */ 
/* 313:    */ 
/* 314:    */ 
/* 315:    */ 
/* 316:    */ 
/* 317:428 */         translatePredicates(classGen, methodGen);
/* 318:429 */         LocalVariableGen iteratorTemp = methodGen.addLocalVariable("step_tmp1", Util.getJCRefType("Lorg/apache/xml/dtm/DTMAxisIterator;"), null, null);
/* 319:    */         
/* 320:    */ 
/* 321:    */ 
/* 322:433 */         iteratorTemp.setStart(il.append(new ASTORE(iteratorTemp.getIndex())));
/* 323:    */         
/* 324:    */ 
/* 325:436 */         predicate.translate(classGen, methodGen);
/* 326:437 */         LocalVariableGen predicateValueTemp = methodGen.addLocalVariable("step_tmp2", Util.getJCRefType("I"), null, null);
/* 327:    */         
/* 328:    */ 
/* 329:    */ 
/* 330:441 */         predicateValueTemp.setStart(il.append(new ISTORE(predicateValueTemp.getIndex())));
/* 331:    */         
/* 332:    */ 
/* 333:444 */         il.append(new NEW(cpg.addClass("org.apache.xalan.xsltc.dom.NthIterator")));
/* 334:445 */         il.append(InstructionConstants.DUP);
/* 335:446 */         iteratorTemp.setEnd(il.append(new ALOAD(iteratorTemp.getIndex())));
/* 336:    */         
/* 337:448 */         predicateValueTemp.setEnd(il.append(new ILOAD(predicateValueTemp.getIndex())));
/* 338:    */         
/* 339:450 */         il.append(new INVOKESPECIAL(idx));
/* 340:    */       }
/* 341:    */       else
/* 342:    */       {
/* 343:453 */         idx = cpg.addMethodref("org.apache.xalan.xsltc.dom.CurrentNodeListIterator", "<init>", "(Lorg/apache/xml/dtm/DTMAxisIterator;Lorg/apache/xalan/xsltc/dom/CurrentNodeListFilter;ILorg/apache/xalan/xsltc/runtime/AbstractTranslet;)V");
/* 344:    */         
/* 345:    */ 
/* 346:    */ 
/* 347:    */ 
/* 348:    */ 
/* 349:    */ 
/* 350:    */ 
/* 351:    */ 
/* 352:    */ 
/* 353:    */ 
/* 354:    */ 
/* 355:    */ 
/* 356:    */ 
/* 357:    */ 
/* 358:    */ 
/* 359:    */ 
/* 360:    */ 
/* 361:471 */         translatePredicates(classGen, methodGen);
/* 362:472 */         LocalVariableGen iteratorTemp = methodGen.addLocalVariable("step_tmp1", Util.getJCRefType("Lorg/apache/xml/dtm/DTMAxisIterator;"), null, null);
/* 363:    */         
/* 364:    */ 
/* 365:    */ 
/* 366:476 */         iteratorTemp.setStart(il.append(new ASTORE(iteratorTemp.getIndex())));
/* 367:    */         
/* 368:    */ 
/* 369:479 */         predicate.translateFilter(classGen, methodGen);
/* 370:480 */         LocalVariableGen filterTemp = methodGen.addLocalVariable("step_tmp2", Util.getJCRefType("Lorg/apache/xalan/xsltc/dom/CurrentNodeListFilter;"), null, null);
/* 371:    */         
/* 372:    */ 
/* 373:    */ 
/* 374:484 */         filterTemp.setStart(il.append(new ASTORE(filterTemp.getIndex())));
/* 375:    */         
/* 376:    */ 
/* 377:    */ 
/* 378:488 */         il.append(new NEW(cpg.addClass("org.apache.xalan.xsltc.dom.CurrentNodeListIterator")));
/* 379:489 */         il.append(InstructionConstants.DUP);
/* 380:    */         
/* 381:491 */         iteratorTemp.setEnd(il.append(new ALOAD(iteratorTemp.getIndex())));
/* 382:    */         
/* 383:493 */         filterTemp.setEnd(il.append(new ALOAD(filterTemp.getIndex())));
/* 384:    */         
/* 385:495 */         il.append(methodGen.loadCurrentNode());
/* 386:496 */         il.append(classGen.loadTranslet());
/* 387:497 */         if (classGen.isExternal())
/* 388:    */         {
/* 389:498 */           String className = classGen.getClassName();
/* 390:499 */           il.append(new CHECKCAST(cpg.addClass(className)));
/* 391:    */         }
/* 392:501 */         il.append(new INVOKESPECIAL(idx));
/* 393:    */       }
/* 394:    */     }
/* 395:    */   }
/* 396:    */   
/* 397:    */   public String toString()
/* 398:    */   {
/* 399:510 */     StringBuffer buffer = new StringBuffer("step(\"");
/* 400:511 */     buffer.append(Axis.getNames(this._axis)).append("\", ").append(this._nodeType);
/* 401:512 */     if (this._predicates != null)
/* 402:    */     {
/* 403:513 */       int n = this._predicates.size();
/* 404:514 */       for (int i = 0; i < n; i++)
/* 405:    */       {
/* 406:515 */         Predicate pred = (Predicate)this._predicates.elementAt(i);
/* 407:516 */         buffer.append(", ").append(pred.toString());
/* 408:    */       }
/* 409:    */     }
/* 410:519 */     return ')';
/* 411:    */   }
/* 412:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.Step
 * JD-Core Version:    0.7.0.1
 */