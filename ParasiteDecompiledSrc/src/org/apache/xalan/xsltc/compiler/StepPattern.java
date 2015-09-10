/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ import org.apache.bcel.classfile.Field;
/*   5:    */ import org.apache.bcel.generic.ALOAD;
/*   6:    */ import org.apache.bcel.generic.ASTORE;
/*   7:    */ import org.apache.bcel.generic.BranchHandle;
/*   8:    */ import org.apache.bcel.generic.ClassGen;
/*   9:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*  10:    */ import org.apache.bcel.generic.GETFIELD;
/*  11:    */ import org.apache.bcel.generic.GOTO;
/*  12:    */ import org.apache.bcel.generic.GOTO_W;
/*  13:    */ import org.apache.bcel.generic.IFLT;
/*  14:    */ import org.apache.bcel.generic.IFNE;
/*  15:    */ import org.apache.bcel.generic.IFNONNULL;
/*  16:    */ import org.apache.bcel.generic.IF_ICMPEQ;
/*  17:    */ import org.apache.bcel.generic.IF_ICMPLT;
/*  18:    */ import org.apache.bcel.generic.IF_ICMPNE;
/*  19:    */ import org.apache.bcel.generic.ILOAD;
/*  20:    */ import org.apache.bcel.generic.INVOKEINTERFACE;
/*  21:    */ import org.apache.bcel.generic.INVOKESPECIAL;
/*  22:    */ import org.apache.bcel.generic.ISTORE;
/*  23:    */ import org.apache.bcel.generic.InstructionConstants;
/*  24:    */ import org.apache.bcel.generic.InstructionHandle;
/*  25:    */ import org.apache.bcel.generic.InstructionList;
/*  26:    */ import org.apache.bcel.generic.LocalVariableGen;
/*  27:    */ import org.apache.bcel.generic.MethodGen;
/*  28:    */ import org.apache.bcel.generic.NEW;
/*  29:    */ import org.apache.bcel.generic.PUSH;
/*  30:    */ import org.apache.bcel.generic.PUTFIELD;
/*  31:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  32:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  33:    */ import org.apache.xalan.xsltc.compiler.util.Type;
/*  34:    */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*  35:    */ import org.apache.xalan.xsltc.compiler.util.Util;
/*  36:    */ import org.apache.xml.dtm.Axis;
/*  37:    */ 
/*  38:    */ class StepPattern
/*  39:    */   extends RelativePathPattern
/*  40:    */ {
/*  41:    */   private static final int NO_CONTEXT = 0;
/*  42:    */   private static final int SIMPLE_CONTEXT = 1;
/*  43:    */   private static final int GENERAL_CONTEXT = 2;
/*  44:    */   protected final int _axis;
/*  45:    */   protected final int _nodeType;
/*  46:    */   protected Vector _predicates;
/*  47: 73 */   private Step _step = null;
/*  48: 74 */   private boolean _isEpsilon = false;
/*  49:    */   private int _contextCase;
/*  50: 77 */   private double _priority = 1.7976931348623157E+308D;
/*  51:    */   
/*  52:    */   public StepPattern(int axis, int nodeType, Vector predicates)
/*  53:    */   {
/*  54: 80 */     this._axis = axis;
/*  55: 81 */     this._nodeType = nodeType;
/*  56: 82 */     this._predicates = predicates;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void setParser(Parser parser)
/*  60:    */   {
/*  61: 86 */     super.setParser(parser);
/*  62: 87 */     if (this._predicates != null)
/*  63:    */     {
/*  64: 88 */       int n = this._predicates.size();
/*  65: 89 */       for (int i = 0; i < n; i++)
/*  66:    */       {
/*  67: 90 */         Predicate exp = (Predicate)this._predicates.elementAt(i);
/*  68: 91 */         exp.setParser(parser);
/*  69: 92 */         exp.setParent(this);
/*  70:    */       }
/*  71:    */     }
/*  72:    */   }
/*  73:    */   
/*  74:    */   public int getNodeType()
/*  75:    */   {
/*  76: 98 */     return this._nodeType;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void setPriority(double priority)
/*  80:    */   {
/*  81:102 */     this._priority = priority;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public StepPattern getKernelPattern()
/*  85:    */   {
/*  86:106 */     return this;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public boolean isWildcard()
/*  90:    */   {
/*  91:110 */     return (this._isEpsilon) && (!hasPredicates());
/*  92:    */   }
/*  93:    */   
/*  94:    */   public StepPattern setPredicates(Vector predicates)
/*  95:    */   {
/*  96:114 */     this._predicates = predicates;
/*  97:115 */     return this;
/*  98:    */   }
/*  99:    */   
/* 100:    */   protected boolean hasPredicates()
/* 101:    */   {
/* 102:119 */     return (this._predicates != null) && (this._predicates.size() > 0);
/* 103:    */   }
/* 104:    */   
/* 105:    */   public double getDefaultPriority()
/* 106:    */   {
/* 107:123 */     if (this._priority != 1.7976931348623157E+308D) {
/* 108:124 */       return this._priority;
/* 109:    */     }
/* 110:127 */     if (hasPredicates()) {
/* 111:128 */       return 0.5D;
/* 112:    */     }
/* 113:131 */     switch (this._nodeType)
/* 114:    */     {
/* 115:    */     case -1: 
/* 116:133 */       return -0.5D;
/* 117:    */     case 0: 
/* 118:135 */       return 0.0D;
/* 119:    */     }
/* 120:137 */     return this._nodeType >= 14 ? 0.0D : -0.5D;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public int getAxis()
/* 124:    */   {
/* 125:143 */     return this._axis;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void reduceKernelPattern()
/* 129:    */   {
/* 130:147 */     this._isEpsilon = true;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public String toString()
/* 134:    */   {
/* 135:151 */     StringBuffer buffer = new StringBuffer("stepPattern(\"");
/* 136:152 */     buffer.append(Axis.getNames(this._axis)).append("\", ").append(this._isEpsilon ? "epsilon{" + Integer.toString(this._nodeType) + "}" : Integer.toString(this._nodeType));
/* 137:157 */     if (this._predicates != null) {
/* 138:158 */       buffer.append(", ").append(this._predicates.toString());
/* 139:    */     }
/* 140:159 */     return ')';
/* 141:    */   }
/* 142:    */   
/* 143:    */   private int analyzeCases()
/* 144:    */   {
/* 145:163 */     boolean noContext = true;
/* 146:164 */     int n = this._predicates.size();
/* 147:166 */     for (int i = 0; (i < n) && (noContext); i++)
/* 148:    */     {
/* 149:167 */       Predicate pred = (Predicate)this._predicates.elementAt(i);
/* 150:168 */       if ((pred.isNthPositionFilter()) || (pred.hasPositionCall()) || (pred.hasLastCall())) {
/* 151:172 */         noContext = false;
/* 152:    */       }
/* 153:    */     }
/* 154:176 */     if (noContext) {
/* 155:177 */       return 0;
/* 156:    */     }
/* 157:179 */     if (n == 1) {
/* 158:180 */       return 1;
/* 159:    */     }
/* 160:182 */     return 2;
/* 161:    */   }
/* 162:    */   
/* 163:    */   private String getNextFieldName()
/* 164:    */   {
/* 165:186 */     return "__step_pattern_iter_" + getXSLTC().nextStepPatternSerial();
/* 166:    */   }
/* 167:    */   
/* 168:    */   public Type typeCheck(SymbolTable stable)
/* 169:    */     throws TypeCheckError
/* 170:    */   {
/* 171:190 */     if (hasPredicates())
/* 172:    */     {
/* 173:192 */       int n = this._predicates.size();
/* 174:193 */       for (int i = 0; i < n; i++)
/* 175:    */       {
/* 176:194 */         Predicate pred = (Predicate)this._predicates.elementAt(i);
/* 177:195 */         pred.typeCheck(stable);
/* 178:    */       }
/* 179:199 */       this._contextCase = analyzeCases();
/* 180:    */       
/* 181:201 */       Step step = null;
/* 182:204 */       if (this._contextCase == 1)
/* 183:    */       {
/* 184:205 */         Predicate pred = (Predicate)this._predicates.elementAt(0);
/* 185:206 */         if (pred.isNthPositionFilter())
/* 186:    */         {
/* 187:207 */           this._contextCase = 2;
/* 188:208 */           step = new Step(this._axis, this._nodeType, this._predicates);
/* 189:    */         }
/* 190:    */         else
/* 191:    */         {
/* 192:210 */           step = new Step(this._axis, this._nodeType, null);
/* 193:    */         }
/* 194:    */       }
/* 195:212 */       else if (this._contextCase == 2)
/* 196:    */       {
/* 197:213 */         int len = this._predicates.size();
/* 198:214 */         for (int i = 0; i < len; i++) {
/* 199:215 */           ((Predicate)this._predicates.elementAt(i)).dontOptimize();
/* 200:    */         }
/* 201:218 */         step = new Step(this._axis, this._nodeType, this._predicates);
/* 202:    */       }
/* 203:221 */       if (step != null)
/* 204:    */       {
/* 205:222 */         step.setParser(getParser());
/* 206:223 */         step.typeCheck(stable);
/* 207:224 */         this._step = step;
/* 208:    */       }
/* 209:    */     }
/* 210:227 */     return this._axis == 3 ? Type.Element : Type.Attribute;
/* 211:    */   }
/* 212:    */   
/* 213:    */   private void translateKernel(ClassGenerator classGen, MethodGenerator methodGen)
/* 214:    */   {
/* 215:232 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 216:233 */     InstructionList il = methodGen.getInstructionList();
/* 217:235 */     if (this._nodeType == 1)
/* 218:    */     {
/* 219:236 */       int check = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "isElement", "(I)Z");
/* 220:    */       
/* 221:238 */       il.append(methodGen.loadDOM());
/* 222:239 */       il.append(InstructionConstants.SWAP);
/* 223:240 */       il.append(new INVOKEINTERFACE(check, 2));
/* 224:    */       
/* 225:    */ 
/* 226:243 */       BranchHandle icmp = il.append(new IFNE(null));
/* 227:244 */       this._falseList.add(il.append(new GOTO_W(null)));
/* 228:245 */       icmp.setTarget(il.append(InstructionConstants.NOP));
/* 229:    */     }
/* 230:247 */     else if (this._nodeType == 2)
/* 231:    */     {
/* 232:248 */       int check = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "isAttribute", "(I)Z");
/* 233:    */       
/* 234:250 */       il.append(methodGen.loadDOM());
/* 235:251 */       il.append(InstructionConstants.SWAP);
/* 236:252 */       il.append(new INVOKEINTERFACE(check, 2));
/* 237:    */       
/* 238:    */ 
/* 239:255 */       BranchHandle icmp = il.append(new IFNE(null));
/* 240:256 */       this._falseList.add(il.append(new GOTO_W(null)));
/* 241:257 */       icmp.setTarget(il.append(InstructionConstants.NOP));
/* 242:    */     }
/* 243:    */     else
/* 244:    */     {
/* 245:261 */       int getEType = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "getExpandedTypeID", "(I)I");
/* 246:    */       
/* 247:    */ 
/* 248:264 */       il.append(methodGen.loadDOM());
/* 249:265 */       il.append(InstructionConstants.SWAP);
/* 250:266 */       il.append(new INVOKEINTERFACE(getEType, 2));
/* 251:267 */       il.append(new PUSH(cpg, this._nodeType));
/* 252:    */       
/* 253:    */ 
/* 254:270 */       BranchHandle icmp = il.append(new IF_ICMPEQ(null));
/* 255:271 */       this._falseList.add(il.append(new GOTO_W(null)));
/* 256:272 */       icmp.setTarget(il.append(InstructionConstants.NOP));
/* 257:    */     }
/* 258:    */   }
/* 259:    */   
/* 260:    */   private void translateNoContext(ClassGenerator classGen, MethodGenerator methodGen)
/* 261:    */   {
/* 262:278 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 263:279 */     InstructionList il = methodGen.getInstructionList();
/* 264:    */     
/* 265:    */ 
/* 266:282 */     il.append(methodGen.loadCurrentNode());
/* 267:283 */     il.append(InstructionConstants.SWAP);
/* 268:    */     
/* 269:    */ 
/* 270:286 */     il.append(methodGen.storeCurrentNode());
/* 271:289 */     if (!this._isEpsilon)
/* 272:    */     {
/* 273:290 */       il.append(methodGen.loadCurrentNode());
/* 274:291 */       translateKernel(classGen, methodGen);
/* 275:    */     }
/* 276:295 */     int n = this._predicates.size();
/* 277:296 */     for (int i = 0; i < n; i++)
/* 278:    */     {
/* 279:297 */       Predicate pred = (Predicate)this._predicates.elementAt(i);
/* 280:298 */       Expression exp = pred.getExpr();
/* 281:299 */       exp.translateDesynthesized(classGen, methodGen);
/* 282:300 */       this._trueList.append(exp._trueList);
/* 283:301 */       this._falseList.append(exp._falseList);
/* 284:    */     }
/* 285:306 */     InstructionHandle restore = il.append(methodGen.storeCurrentNode());
/* 286:307 */     backPatchTrueList(restore);
/* 287:308 */     BranchHandle skipFalse = il.append(new GOTO(null));
/* 288:    */     
/* 289:    */ 
/* 290:311 */     restore = il.append(methodGen.storeCurrentNode());
/* 291:312 */     backPatchFalseList(restore);
/* 292:313 */     this._falseList.add(il.append(new GOTO(null)));
/* 293:    */     
/* 294:    */ 
/* 295:316 */     skipFalse.setTarget(il.append(InstructionConstants.NOP));
/* 296:    */   }
/* 297:    */   
/* 298:    */   private void translateSimpleContext(ClassGenerator classGen, MethodGenerator methodGen)
/* 299:    */   {
/* 300:322 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 301:323 */     InstructionList il = methodGen.getInstructionList();
/* 302:    */     
/* 303:    */ 
/* 304:    */ 
/* 305:327 */     LocalVariableGen match = methodGen.addLocalVariable("step_pattern_tmp1", Util.getJCRefType("I"), null, null);
/* 306:    */     
/* 307:    */ 
/* 308:330 */     match.setStart(il.append(new ISTORE(match.getIndex())));
/* 309:333 */     if (!this._isEpsilon)
/* 310:    */     {
/* 311:334 */       il.append(new ILOAD(match.getIndex()));
/* 312:335 */       translateKernel(classGen, methodGen);
/* 313:    */     }
/* 314:339 */     il.append(methodGen.loadCurrentNode());
/* 315:340 */     il.append(methodGen.loadIterator());
/* 316:    */     
/* 317:    */ 
/* 318:343 */     int index = cpg.addMethodref("org.apache.xalan.xsltc.dom.MatchingIterator", "<init>", "(ILorg/apache/xml/dtm/DTMAxisIterator;)V");
/* 319:    */     
/* 320:    */ 
/* 321:    */ 
/* 322:    */ 
/* 323:    */ 
/* 324:    */ 
/* 325:    */ 
/* 326:    */ 
/* 327:    */ 
/* 328:    */ 
/* 329:    */ 
/* 330:355 */     this._step.translate(classGen, methodGen);
/* 331:356 */     LocalVariableGen stepIteratorTemp = methodGen.addLocalVariable("step_pattern_tmp2", Util.getJCRefType("Lorg/apache/xml/dtm/DTMAxisIterator;"), null, null);
/* 332:    */     
/* 333:    */ 
/* 334:    */ 
/* 335:360 */     stepIteratorTemp.setStart(il.append(new ASTORE(stepIteratorTemp.getIndex())));
/* 336:    */     
/* 337:    */ 
/* 338:363 */     il.append(new NEW(cpg.addClass("org.apache.xalan.xsltc.dom.MatchingIterator")));
/* 339:364 */     il.append(InstructionConstants.DUP);
/* 340:365 */     il.append(new ILOAD(match.getIndex()));
/* 341:366 */     stepIteratorTemp.setEnd(il.append(new ALOAD(stepIteratorTemp.getIndex())));
/* 342:    */     
/* 343:368 */     il.append(new INVOKESPECIAL(index));
/* 344:    */     
/* 345:    */ 
/* 346:371 */     il.append(methodGen.loadDOM());
/* 347:372 */     il.append(new ILOAD(match.getIndex()));
/* 348:373 */     index = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "getParent", "(I)I");
/* 349:374 */     il.append(new INVOKEINTERFACE(index, 2));
/* 350:    */     
/* 351:    */ 
/* 352:377 */     il.append(methodGen.setStartNode());
/* 353:    */     
/* 354:    */ 
/* 355:380 */     il.append(methodGen.storeIterator());
/* 356:381 */     match.setEnd(il.append(new ILOAD(match.getIndex())));
/* 357:382 */     il.append(methodGen.storeCurrentNode());
/* 358:    */     
/* 359:    */ 
/* 360:385 */     Predicate pred = (Predicate)this._predicates.elementAt(0);
/* 361:386 */     Expression exp = pred.getExpr();
/* 362:387 */     exp.translateDesynthesized(classGen, methodGen);
/* 363:    */     
/* 364:    */ 
/* 365:390 */     InstructionHandle restore = il.append(methodGen.storeIterator());
/* 366:391 */     il.append(methodGen.storeCurrentNode());
/* 367:392 */     exp.backPatchTrueList(restore);
/* 368:393 */     BranchHandle skipFalse = il.append(new GOTO(null));
/* 369:    */     
/* 370:    */ 
/* 371:396 */     restore = il.append(methodGen.storeIterator());
/* 372:397 */     il.append(methodGen.storeCurrentNode());
/* 373:398 */     exp.backPatchFalseList(restore);
/* 374:399 */     this._falseList.add(il.append(new GOTO(null)));
/* 375:    */     
/* 376:    */ 
/* 377:402 */     skipFalse.setTarget(il.append(InstructionConstants.NOP));
/* 378:    */   }
/* 379:    */   
/* 380:    */   private void translateGeneralContext(ClassGenerator classGen, MethodGenerator methodGen)
/* 381:    */   {
/* 382:407 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 383:408 */     InstructionList il = methodGen.getInstructionList();
/* 384:    */     
/* 385:410 */     int iteratorIndex = 0;
/* 386:411 */     BranchHandle ifBlock = null;
/* 387:    */     
/* 388:413 */     String iteratorName = getNextFieldName();
/* 389:    */     
/* 390:    */ 
/* 391:416 */     LocalVariableGen node = methodGen.addLocalVariable("step_pattern_tmp1", Util.getJCRefType("I"), null, null);
/* 392:    */     
/* 393:    */ 
/* 394:419 */     node.setStart(il.append(new ISTORE(node.getIndex())));
/* 395:    */     
/* 396:    */ 
/* 397:422 */     LocalVariableGen iter = methodGen.addLocalVariable("step_pattern_tmp2", Util.getJCRefType("Lorg/apache/xml/dtm/DTMAxisIterator;"), null, null);
/* 398:427 */     if (!classGen.isExternal())
/* 399:    */     {
/* 400:428 */       Field iterator = new Field(2, cpg.addUtf8(iteratorName), cpg.addUtf8("Lorg/apache/xml/dtm/DTMAxisIterator;"), null, cpg.getConstantPool());
/* 401:    */       
/* 402:    */ 
/* 403:    */ 
/* 404:    */ 
/* 405:433 */       classGen.addField(iterator);
/* 406:434 */       iteratorIndex = cpg.addFieldref(classGen.getClassName(), iteratorName, "Lorg/apache/xml/dtm/DTMAxisIterator;");
/* 407:    */       
/* 408:    */ 
/* 409:    */ 
/* 410:438 */       il.append(classGen.loadTranslet());
/* 411:439 */       il.append(new GETFIELD(iteratorIndex));
/* 412:440 */       il.append(InstructionConstants.DUP);
/* 413:441 */       iter.setStart(il.append(new ASTORE(iter.getIndex())));
/* 414:442 */       ifBlock = il.append(new IFNONNULL(null));
/* 415:443 */       il.append(classGen.loadTranslet());
/* 416:    */     }
/* 417:447 */     this._step.translate(classGen, methodGen);
/* 418:448 */     InstructionHandle iterStore = il.append(new ASTORE(iter.getIndex()));
/* 419:451 */     if (!classGen.isExternal())
/* 420:    */     {
/* 421:452 */       il.append(new ALOAD(iter.getIndex()));
/* 422:453 */       il.append(new PUTFIELD(iteratorIndex));
/* 423:454 */       ifBlock.setTarget(il.append(InstructionConstants.NOP));
/* 424:    */     }
/* 425:    */     else
/* 426:    */     {
/* 427:458 */       iter.setStart(iterStore);
/* 428:    */     }
/* 429:462 */     il.append(methodGen.loadDOM());
/* 430:463 */     il.append(new ILOAD(node.getIndex()));
/* 431:464 */     int index = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "getParent", "(I)I");
/* 432:    */     
/* 433:466 */     il.append(new INVOKEINTERFACE(index, 2));
/* 434:    */     
/* 435:    */ 
/* 436:469 */     il.append(new ALOAD(iter.getIndex()));
/* 437:470 */     il.append(InstructionConstants.SWAP);
/* 438:471 */     il.append(methodGen.setStartNode());
/* 439:    */     
/* 440:    */ 
/* 441:    */ 
/* 442:    */ 
/* 443:    */ 
/* 444:    */ 
/* 445:    */ 
/* 446:    */ 
/* 447:    */ 
/* 448:    */ 
/* 449:    */ 
/* 450:483 */     LocalVariableGen node2 = methodGen.addLocalVariable("step_pattern_tmp3", Util.getJCRefType("I"), null, null);
/* 451:    */     
/* 452:    */ 
/* 453:    */ 
/* 454:487 */     BranchHandle skipNext = il.append(new GOTO(null));
/* 455:488 */     InstructionHandle next = il.append(new ALOAD(iter.getIndex()));
/* 456:489 */     node2.setStart(next);
/* 457:490 */     InstructionHandle begin = il.append(methodGen.nextNode());
/* 458:491 */     il.append(InstructionConstants.DUP);
/* 459:492 */     il.append(new ISTORE(node2.getIndex()));
/* 460:493 */     this._falseList.add(il.append(new IFLT(null)));
/* 461:    */     
/* 462:495 */     il.append(new ILOAD(node2.getIndex()));
/* 463:496 */     il.append(new ILOAD(node.getIndex()));
/* 464:497 */     iter.setEnd(il.append(new IF_ICMPLT(next)));
/* 465:    */     
/* 466:499 */     node2.setEnd(il.append(new ILOAD(node2.getIndex())));
/* 467:500 */     node.setEnd(il.append(new ILOAD(node.getIndex())));
/* 468:501 */     this._falseList.add(il.append(new IF_ICMPNE(null)));
/* 469:    */     
/* 470:503 */     skipNext.setTarget(begin);
/* 471:    */   }
/* 472:    */   
/* 473:    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/* 474:    */   {
/* 475:507 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 476:508 */     InstructionList il = methodGen.getInstructionList();
/* 477:510 */     if (hasPredicates()) {
/* 478:511 */       switch (this._contextCase)
/* 479:    */       {
/* 480:    */       case 0: 
/* 481:513 */         translateNoContext(classGen, methodGen);
/* 482:514 */         break;
/* 483:    */       case 1: 
/* 484:517 */         translateSimpleContext(classGen, methodGen);
/* 485:518 */         break;
/* 486:    */       default: 
/* 487:521 */         translateGeneralContext(classGen, methodGen);
/* 488:522 */         break;
/* 489:    */       }
/* 490:525 */     } else if (isWildcard()) {
/* 491:526 */       il.append(InstructionConstants.POP);
/* 492:    */     } else {
/* 493:529 */       translateKernel(classGen, methodGen);
/* 494:    */     }
/* 495:    */   }
/* 496:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.StepPattern
 * JD-Core Version:    0.7.0.1
 */