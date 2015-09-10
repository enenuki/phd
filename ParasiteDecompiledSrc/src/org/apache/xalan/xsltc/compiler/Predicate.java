/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import org.apache.bcel.classfile.Field;
/*   5:    */ import org.apache.bcel.generic.ASTORE;
/*   6:    */ import org.apache.bcel.generic.CHECKCAST;
/*   7:    */ import org.apache.bcel.generic.ClassGen;
/*   8:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*   9:    */ import org.apache.bcel.generic.GETFIELD;
/*  10:    */ import org.apache.bcel.generic.INVOKESPECIAL;
/*  11:    */ import org.apache.bcel.generic.InstructionConstants;
/*  12:    */ import org.apache.bcel.generic.InstructionList;
/*  13:    */ import org.apache.bcel.generic.LocalVariableGen;
/*  14:    */ import org.apache.bcel.generic.MethodGen;
/*  15:    */ import org.apache.bcel.generic.NEW;
/*  16:    */ import org.apache.bcel.generic.PUSH;
/*  17:    */ import org.apache.bcel.generic.PUTFIELD;
/*  18:    */ import org.apache.xalan.xsltc.compiler.util.BooleanType;
/*  19:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  20:    */ import org.apache.xalan.xsltc.compiler.util.FilterGenerator;
/*  21:    */ import org.apache.xalan.xsltc.compiler.util.IntType;
/*  22:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  23:    */ import org.apache.xalan.xsltc.compiler.util.NumberType;
/*  24:    */ import org.apache.xalan.xsltc.compiler.util.ReferenceType;
/*  25:    */ import org.apache.xalan.xsltc.compiler.util.ResultTreeType;
/*  26:    */ import org.apache.xalan.xsltc.compiler.util.TestGenerator;
/*  27:    */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*  28:    */ import org.apache.xalan.xsltc.compiler.util.Util;
/*  29:    */ 
/*  30:    */ final class Predicate
/*  31:    */   extends Expression
/*  32:    */   implements Closure
/*  33:    */ {
/*  34: 61 */   private Expression _exp = null;
/*  35: 68 */   private boolean _canOptimize = true;
/*  36: 74 */   private boolean _nthPositionFilter = false;
/*  37: 80 */   private boolean _nthDescendant = false;
/*  38: 85 */   int _ptype = -1;
/*  39: 90 */   private String _className = null;
/*  40: 95 */   private ArrayList _closureVars = null;
/*  41:100 */   private Closure _parentClosure = null;
/*  42:105 */   private Expression _value = null;
/*  43:110 */   private Step _step = null;
/*  44:    */   
/*  45:    */   public Predicate(Expression exp)
/*  46:    */   {
/*  47:116 */     this._exp = exp;
/*  48:117 */     this._exp.setParent(this);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void setParser(Parser parser)
/*  52:    */   {
/*  53:125 */     super.setParser(parser);
/*  54:126 */     this._exp.setParser(parser);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public boolean isNthPositionFilter()
/*  58:    */   {
/*  59:134 */     return this._nthPositionFilter;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public boolean isNthDescendant()
/*  63:    */   {
/*  64:142 */     return this._nthDescendant;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void dontOptimize()
/*  68:    */   {
/*  69:149 */     this._canOptimize = false;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public boolean hasPositionCall()
/*  73:    */   {
/*  74:157 */     return this._exp.hasPositionCall();
/*  75:    */   }
/*  76:    */   
/*  77:    */   public boolean hasLastCall()
/*  78:    */   {
/*  79:165 */     return this._exp.hasLastCall();
/*  80:    */   }
/*  81:    */   
/*  82:    */   public boolean inInnerClass()
/*  83:    */   {
/*  84:175 */     return this._className != null;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public Closure getParentClosure()
/*  88:    */   {
/*  89:182 */     if (this._parentClosure == null)
/*  90:    */     {
/*  91:183 */       SyntaxTreeNode node = getParent();
/*  92:    */       do
/*  93:    */       {
/*  94:185 */         if ((node instanceof Closure))
/*  95:    */         {
/*  96:186 */           this._parentClosure = ((Closure)node);
/*  97:187 */           break;
/*  98:    */         }
/*  99:189 */         if ((node instanceof TopLevelElement)) {
/* 100:    */           break;
/* 101:    */         }
/* 102:192 */         node = node.getParent();
/* 103:193 */       } while (node != null);
/* 104:    */     }
/* 105:195 */     return this._parentClosure;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public String getInnerClassName()
/* 109:    */   {
/* 110:203 */     return this._className;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void addVariable(VariableRefBase variableRef)
/* 114:    */   {
/* 115:210 */     if (this._closureVars == null) {
/* 116:211 */       this._closureVars = new ArrayList();
/* 117:    */     }
/* 118:215 */     if (!this._closureVars.contains(variableRef))
/* 119:    */     {
/* 120:216 */       this._closureVars.add(variableRef);
/* 121:    */       
/* 122:    */ 
/* 123:219 */       Closure parentClosure = getParentClosure();
/* 124:220 */       if (parentClosure != null) {
/* 125:221 */         parentClosure.addVariable(variableRef);
/* 126:    */       }
/* 127:    */     }
/* 128:    */   }
/* 129:    */   
/* 130:    */   public int getPosType()
/* 131:    */   {
/* 132:233 */     if (this._ptype == -1)
/* 133:    */     {
/* 134:234 */       SyntaxTreeNode parent = getParent();
/* 135:235 */       if ((parent instanceof StepPattern))
/* 136:    */       {
/* 137:236 */         this._ptype = ((StepPattern)parent).getNodeType();
/* 138:    */       }
/* 139:238 */       else if ((parent instanceof AbsoluteLocationPath))
/* 140:    */       {
/* 141:239 */         AbsoluteLocationPath path = (AbsoluteLocationPath)parent;
/* 142:240 */         Expression exp = path.getPath();
/* 143:241 */         if ((exp instanceof Step)) {
/* 144:242 */           this._ptype = ((Step)exp).getNodeType();
/* 145:    */         }
/* 146:    */       }
/* 147:245 */       else if ((parent instanceof VariableRefBase))
/* 148:    */       {
/* 149:246 */         VariableRefBase ref = (VariableRefBase)parent;
/* 150:247 */         VariableBase var = ref.getVariable();
/* 151:248 */         Expression exp = var.getExpression();
/* 152:249 */         if ((exp instanceof Step)) {
/* 153:250 */           this._ptype = ((Step)exp).getNodeType();
/* 154:    */         }
/* 155:    */       }
/* 156:253 */       else if ((parent instanceof Step))
/* 157:    */       {
/* 158:254 */         this._ptype = ((Step)parent).getNodeType();
/* 159:    */       }
/* 160:    */     }
/* 161:257 */     return this._ptype;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public boolean parentIsPattern()
/* 165:    */   {
/* 166:261 */     return getParent() instanceof Pattern;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public Expression getExpr()
/* 170:    */   {
/* 171:265 */     return this._exp;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public String toString()
/* 175:    */   {
/* 176:269 */     return "pred(" + this._exp + ')';
/* 177:    */   }
/* 178:    */   
/* 179:    */   public org.apache.xalan.xsltc.compiler.util.Type typeCheck(SymbolTable stable)
/* 180:    */     throws TypeCheckError
/* 181:    */   {
/* 182:284 */     org.apache.xalan.xsltc.compiler.util.Type texp = this._exp.typeCheck(stable);
/* 183:287 */     if ((texp instanceof ReferenceType)) {
/* 184:288 */       this._exp = new CastExpr(this._exp, texp = org.apache.xalan.xsltc.compiler.util.Type.Real);
/* 185:    */     }
/* 186:294 */     if ((texp instanceof ResultTreeType))
/* 187:    */     {
/* 188:295 */       this._exp = new CastExpr(this._exp, org.apache.xalan.xsltc.compiler.util.Type.Boolean);
/* 189:296 */       this._exp = new CastExpr(this._exp, org.apache.xalan.xsltc.compiler.util.Type.Real);
/* 190:297 */       texp = this._exp.typeCheck(stable);
/* 191:    */     }
/* 192:301 */     if ((texp instanceof NumberType))
/* 193:    */     {
/* 194:303 */       if (!(texp instanceof IntType)) {
/* 195:304 */         this._exp = new CastExpr(this._exp, org.apache.xalan.xsltc.compiler.util.Type.Int);
/* 196:    */       }
/* 197:307 */       if (this._canOptimize)
/* 198:    */       {
/* 199:309 */         this._nthPositionFilter = ((!this._exp.hasLastCall()) && (!this._exp.hasPositionCall()));
/* 200:313 */         if (this._nthPositionFilter)
/* 201:    */         {
/* 202:314 */           SyntaxTreeNode parent = getParent();
/* 203:315 */           this._nthDescendant = (((parent instanceof Step)) && ((parent.getParent() instanceof AbsoluteLocationPath)));
/* 204:    */           
/* 205:317 */           return this._type = org.apache.xalan.xsltc.compiler.util.Type.NodeSet;
/* 206:    */         }
/* 207:    */       }
/* 208:322 */       this._nthPositionFilter = (this._nthDescendant = 0);
/* 209:    */       
/* 210:    */ 
/* 211:325 */       QName position = getParser().getQNameIgnoreDefaultNs("position");
/* 212:    */       
/* 213:327 */       PositionCall positionCall = new PositionCall(position);
/* 214:    */       
/* 215:329 */       positionCall.setParser(getParser());
/* 216:330 */       positionCall.setParent(this);
/* 217:    */       
/* 218:332 */       this._exp = new EqualityExpr(0, positionCall, this._exp);
/* 219:334 */       if (this._exp.typeCheck(stable) != org.apache.xalan.xsltc.compiler.util.Type.Boolean) {
/* 220:335 */         this._exp = new CastExpr(this._exp, org.apache.xalan.xsltc.compiler.util.Type.Boolean);
/* 221:    */       }
/* 222:337 */       return this._type = org.apache.xalan.xsltc.compiler.util.Type.Boolean;
/* 223:    */     }
/* 224:341 */     if (!(texp instanceof BooleanType)) {
/* 225:342 */       this._exp = new CastExpr(this._exp, org.apache.xalan.xsltc.compiler.util.Type.Boolean);
/* 226:    */     }
/* 227:344 */     return this._type = org.apache.xalan.xsltc.compiler.util.Type.Boolean;
/* 228:    */   }
/* 229:    */   
/* 230:    */   private void compileFilter(ClassGenerator classGen, MethodGenerator methodGen)
/* 231:    */   {
/* 232:360 */     this._className = getXSLTC().getHelperClassName();
/* 233:361 */     FilterGenerator filterGen = new FilterGenerator(this._className, "java.lang.Object", toString(), 33, new String[] { "org.apache.xalan.xsltc.dom.CurrentNodeListFilter" }, classGen.getStylesheet());
/* 234:    */     
/* 235:    */ 
/* 236:    */ 
/* 237:    */ 
/* 238:    */ 
/* 239:    */ 
/* 240:    */ 
/* 241:    */ 
/* 242:370 */     ConstantPoolGen cpg = filterGen.getConstantPool();
/* 243:371 */     int length = this._closureVars == null ? 0 : this._closureVars.size();
/* 244:374 */     for (int i = 0; i < length; i++)
/* 245:    */     {
/* 246:375 */       VariableBase var = ((VariableRefBase)this._closureVars.get(i)).getVariable();
/* 247:    */       
/* 248:377 */       filterGen.addField(new Field(1, cpg.addUtf8(var.getEscapedName()), cpg.addUtf8(var.getType().toSignature()), null, cpg.getConstantPool()));
/* 249:    */     }
/* 250:383 */     InstructionList il = new InstructionList();
/* 251:384 */     TestGenerator testGen = new TestGenerator(17, org.apache.bcel.generic.Type.BOOLEAN, new org.apache.bcel.generic.Type[] { org.apache.bcel.generic.Type.INT, org.apache.bcel.generic.Type.INT, org.apache.bcel.generic.Type.INT, org.apache.bcel.generic.Type.INT, Util.getJCRefType("Lorg/apache/xalan/xsltc/runtime/AbstractTranslet;"), Util.getJCRefType("Lorg/apache/xml/dtm/DTMAxisIterator;") }, new String[] { "node", "position", "last", "current", "translet", "iterator" }, "test", this._className, il, cpg);
/* 252:    */     
/* 253:    */ 
/* 254:    */ 
/* 255:    */ 
/* 256:    */ 
/* 257:    */ 
/* 258:    */ 
/* 259:    */ 
/* 260:    */ 
/* 261:    */ 
/* 262:    */ 
/* 263:    */ 
/* 264:    */ 
/* 265:    */ 
/* 266:    */ 
/* 267:    */ 
/* 268:    */ 
/* 269:    */ 
/* 270:    */ 
/* 271:    */ 
/* 272:405 */     LocalVariableGen local = testGen.addLocalVariable("document", Util.getJCRefType("Lorg/apache/xalan/xsltc/DOM;"), null, null);
/* 273:    */     
/* 274:    */ 
/* 275:408 */     String className = classGen.getClassName();
/* 276:409 */     il.append(filterGen.loadTranslet());
/* 277:410 */     il.append(new CHECKCAST(cpg.addClass(className)));
/* 278:411 */     il.append(new GETFIELD(cpg.addFieldref(className, "_dom", "Lorg/apache/xalan/xsltc/DOM;")));
/* 279:    */     
/* 280:413 */     local.setStart(il.append(new ASTORE(local.getIndex())));
/* 281:    */     
/* 282:    */ 
/* 283:416 */     testGen.setDomIndex(local.getIndex());
/* 284:    */     
/* 285:418 */     this._exp.translate(filterGen, testGen);
/* 286:419 */     il.append(InstructionConstants.IRETURN);
/* 287:    */     
/* 288:421 */     filterGen.addEmptyConstructor(1);
/* 289:422 */     filterGen.addMethod(testGen);
/* 290:    */     
/* 291:424 */     getXSLTC().dumpClass(filterGen.getJavaClass());
/* 292:    */   }
/* 293:    */   
/* 294:    */   public boolean isBooleanTest()
/* 295:    */   {
/* 296:433 */     return this._exp instanceof BooleanExpr;
/* 297:    */   }
/* 298:    */   
/* 299:    */   public boolean isNodeValueTest()
/* 300:    */   {
/* 301:442 */     if (!this._canOptimize) {
/* 302:442 */       return false;
/* 303:    */     }
/* 304:443 */     return (getStep() != null) && (getCompareValue() != null);
/* 305:    */   }
/* 306:    */   
/* 307:    */   public Step getStep()
/* 308:    */   {
/* 309:453 */     if (this._step != null) {
/* 310:454 */       return this._step;
/* 311:    */     }
/* 312:458 */     if (this._exp == null) {
/* 313:459 */       return null;
/* 314:    */     }
/* 315:463 */     if ((this._exp instanceof EqualityExpr))
/* 316:    */     {
/* 317:464 */       EqualityExpr exp = (EqualityExpr)this._exp;
/* 318:465 */       Expression left = exp.getLeft();
/* 319:466 */       Expression right = exp.getRight();
/* 320:469 */       if ((left instanceof CastExpr)) {
/* 321:470 */         left = ((CastExpr)left).getExpr();
/* 322:    */       }
/* 323:472 */       if ((left instanceof Step)) {
/* 324:473 */         this._step = ((Step)left);
/* 325:    */       }
/* 326:477 */       if ((right instanceof CastExpr)) {
/* 327:478 */         right = ((CastExpr)right).getExpr();
/* 328:    */       }
/* 329:480 */       if ((right instanceof Step)) {
/* 330:481 */         this._step = ((Step)right);
/* 331:    */       }
/* 332:    */     }
/* 333:484 */     return this._step;
/* 334:    */   }
/* 335:    */   
/* 336:    */   public Expression getCompareValue()
/* 337:    */   {
/* 338:494 */     if (this._value != null) {
/* 339:495 */       return this._value;
/* 340:    */     }
/* 341:499 */     if (this._exp == null) {
/* 342:500 */       return null;
/* 343:    */     }
/* 344:504 */     if ((this._exp instanceof EqualityExpr))
/* 345:    */     {
/* 346:505 */       EqualityExpr exp = (EqualityExpr)this._exp;
/* 347:506 */       Expression left = exp.getLeft();
/* 348:507 */       Expression right = exp.getRight();
/* 349:510 */       if ((left instanceof LiteralExpr))
/* 350:    */       {
/* 351:511 */         this._value = left;
/* 352:512 */         return this._value;
/* 353:    */       }
/* 354:515 */       if (((left instanceof VariableRefBase)) && (left.getType() == org.apache.xalan.xsltc.compiler.util.Type.String))
/* 355:    */       {
/* 356:518 */         this._value = left;
/* 357:519 */         return this._value;
/* 358:    */       }
/* 359:523 */       if ((right instanceof LiteralExpr))
/* 360:    */       {
/* 361:524 */         this._value = right;
/* 362:525 */         return this._value;
/* 363:    */       }
/* 364:528 */       if (((right instanceof VariableRefBase)) && (right.getType() == org.apache.xalan.xsltc.compiler.util.Type.String))
/* 365:    */       {
/* 366:531 */         this._value = right;
/* 367:532 */         return this._value;
/* 368:    */       }
/* 369:    */     }
/* 370:535 */     return null;
/* 371:    */   }
/* 372:    */   
/* 373:    */   public void translateFilter(ClassGenerator classGen, MethodGenerator methodGen)
/* 374:    */   {
/* 375:546 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 376:547 */     InstructionList il = methodGen.getInstructionList();
/* 377:    */     
/* 378:    */ 
/* 379:550 */     compileFilter(classGen, methodGen);
/* 380:    */     
/* 381:    */ 
/* 382:553 */     il.append(new NEW(cpg.addClass(this._className)));
/* 383:554 */     il.append(InstructionConstants.DUP);
/* 384:555 */     il.append(new INVOKESPECIAL(cpg.addMethodref(this._className, "<init>", "()V")));
/* 385:    */     
/* 386:    */ 
/* 387:    */ 
/* 388:559 */     int length = this._closureVars == null ? 0 : this._closureVars.size();
/* 389:561 */     for (int i = 0; i < length; i++)
/* 390:    */     {
/* 391:562 */       VariableRefBase varRef = (VariableRefBase)this._closureVars.get(i);
/* 392:563 */       VariableBase var = varRef.getVariable();
/* 393:564 */       org.apache.xalan.xsltc.compiler.util.Type varType = var.getType();
/* 394:    */       
/* 395:566 */       il.append(InstructionConstants.DUP);
/* 396:    */       
/* 397:    */ 
/* 398:569 */       Closure variableClosure = this._parentClosure;
/* 399:570 */       while (variableClosure != null)
/* 400:    */       {
/* 401:571 */         if (variableClosure.inInnerClass()) {
/* 402:    */           break;
/* 403:    */         }
/* 404:572 */         variableClosure = variableClosure.getParentClosure();
/* 405:    */       }
/* 406:576 */       if (variableClosure != null)
/* 407:    */       {
/* 408:577 */         il.append(InstructionConstants.ALOAD_0);
/* 409:578 */         il.append(new GETFIELD(cpg.addFieldref(variableClosure.getInnerClassName(), var.getEscapedName(), varType.toSignature())));
/* 410:    */       }
/* 411:    */       else
/* 412:    */       {
/* 413:584 */         il.append(var.loadInstruction());
/* 414:    */       }
/* 415:588 */       il.append(new PUTFIELD(cpg.addFieldref(this._className, var.getEscapedName(), varType.toSignature())));
/* 416:    */     }
/* 417:    */   }
/* 418:    */   
/* 419:    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/* 420:    */   {
/* 421:602 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 422:603 */     InstructionList il = methodGen.getInstructionList();
/* 423:605 */     if ((this._nthPositionFilter) || (this._nthDescendant))
/* 424:    */     {
/* 425:606 */       this._exp.translate(classGen, methodGen);
/* 426:    */     }
/* 427:608 */     else if ((isNodeValueTest()) && ((getParent() instanceof Step)))
/* 428:    */     {
/* 429:609 */       this._value.translate(classGen, methodGen);
/* 430:610 */       il.append(new CHECKCAST(cpg.addClass("java.lang.String")));
/* 431:611 */       il.append(new PUSH(cpg, ((EqualityExpr)this._exp).getOp()));
/* 432:    */     }
/* 433:    */     else
/* 434:    */     {
/* 435:614 */       translateFilter(classGen, methodGen);
/* 436:    */     }
/* 437:    */   }
/* 438:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.Predicate
 * JD-Core Version:    0.7.0.1
 */