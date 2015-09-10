/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import org.apache.bcel.classfile.Field;
/*   5:    */ import org.apache.bcel.generic.ALOAD;
/*   6:    */ import org.apache.bcel.generic.ASTORE;
/*   7:    */ import org.apache.bcel.generic.BranchHandle;
/*   8:    */ import org.apache.bcel.generic.CHECKCAST;
/*   9:    */ import org.apache.bcel.generic.ClassGen;
/*  10:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*  11:    */ import org.apache.bcel.generic.GETFIELD;
/*  12:    */ import org.apache.bcel.generic.GOTO;
/*  13:    */ import org.apache.bcel.generic.IFNONNULL;
/*  14:    */ import org.apache.bcel.generic.INVOKESPECIAL;
/*  15:    */ import org.apache.bcel.generic.INVOKESTATIC;
/*  16:    */ import org.apache.bcel.generic.INVOKEVIRTUAL;
/*  17:    */ import org.apache.bcel.generic.InstructionConstants;
/*  18:    */ import org.apache.bcel.generic.InstructionList;
/*  19:    */ import org.apache.bcel.generic.LocalVariableGen;
/*  20:    */ import org.apache.bcel.generic.MethodGen;
/*  21:    */ import org.apache.bcel.generic.NEW;
/*  22:    */ import org.apache.bcel.generic.PUSH;
/*  23:    */ import org.apache.bcel.generic.PUTFIELD;
/*  24:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  25:    */ import org.apache.xalan.xsltc.compiler.util.MatchGenerator;
/*  26:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  27:    */ import org.apache.xalan.xsltc.compiler.util.NodeCounterGenerator;
/*  28:    */ import org.apache.xalan.xsltc.compiler.util.RealType;
/*  29:    */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*  30:    */ import org.apache.xalan.xsltc.compiler.util.Util;
/*  31:    */ import org.apache.xalan.xsltc.runtime.AttributeList;
/*  32:    */ 
/*  33:    */ final class Number
/*  34:    */   extends Instruction
/*  35:    */   implements Closure
/*  36:    */ {
/*  37:    */   private static final int LEVEL_SINGLE = 0;
/*  38:    */   private static final int LEVEL_MULTIPLE = 1;
/*  39:    */   private static final int LEVEL_ANY = 2;
/*  40: 62 */   private static final String[] ClassNames = { "org.apache.xalan.xsltc.dom.SingleNodeCounter", "org.apache.xalan.xsltc.dom.MultipleNodeCounter", "org.apache.xalan.xsltc.dom.AnyNodeCounter" };
/*  41: 68 */   private static final String[] FieldNames = { "___single_node_counter", "___multiple_node_counter", "___any_node_counter" };
/*  42: 74 */   private Pattern _from = null;
/*  43: 75 */   private Pattern _count = null;
/*  44: 76 */   private Expression _value = null;
/*  45: 78 */   private AttributeValueTemplate _lang = null;
/*  46: 79 */   private AttributeValueTemplate _format = null;
/*  47: 80 */   private AttributeValueTemplate _letterValue = null;
/*  48: 81 */   private AttributeValueTemplate _groupingSeparator = null;
/*  49: 82 */   private AttributeValueTemplate _groupingSize = null;
/*  50: 84 */   private int _level = 0;
/*  51: 85 */   private boolean _formatNeeded = false;
/*  52: 87 */   private String _className = null;
/*  53: 88 */   private ArrayList _closureVars = null;
/*  54:    */   
/*  55:    */   public boolean inInnerClass()
/*  56:    */   {
/*  57: 97 */     return this._className != null;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public Closure getParentClosure()
/*  61:    */   {
/*  62:104 */     return null;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public String getInnerClassName()
/*  66:    */   {
/*  67:112 */     return this._className;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void addVariable(VariableRefBase variableRef)
/*  71:    */   {
/*  72:119 */     if (this._closureVars == null) {
/*  73:120 */       this._closureVars = new ArrayList();
/*  74:    */     }
/*  75:124 */     if (!this._closureVars.contains(variableRef)) {
/*  76:125 */       this._closureVars.add(variableRef);
/*  77:    */     }
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void parseContents(Parser parser)
/*  81:    */   {
/*  82:132 */     int count = this._attributes.getLength();
/*  83:134 */     for (int i = 0; i < count; i++)
/*  84:    */     {
/*  85:135 */       String name = this._attributes.getQName(i);
/*  86:136 */       String value = this._attributes.getValue(i);
/*  87:138 */       if (name.equals("value"))
/*  88:    */       {
/*  89:139 */         this._value = parser.parseExpression(this, name, null);
/*  90:    */       }
/*  91:141 */       else if (name.equals("count"))
/*  92:    */       {
/*  93:142 */         this._count = parser.parsePattern(this, name, null);
/*  94:    */       }
/*  95:144 */       else if (name.equals("from"))
/*  96:    */       {
/*  97:145 */         this._from = parser.parsePattern(this, name, null);
/*  98:    */       }
/*  99:147 */       else if (name.equals("level"))
/* 100:    */       {
/* 101:148 */         if (value.equals("single")) {
/* 102:149 */           this._level = 0;
/* 103:151 */         } else if (value.equals("multiple")) {
/* 104:152 */           this._level = 1;
/* 105:154 */         } else if (value.equals("any")) {
/* 106:155 */           this._level = 2;
/* 107:    */         }
/* 108:    */       }
/* 109:158 */       else if (name.equals("format"))
/* 110:    */       {
/* 111:159 */         this._format = new AttributeValueTemplate(value, parser, this);
/* 112:160 */         this._formatNeeded = true;
/* 113:    */       }
/* 114:162 */       else if (name.equals("lang"))
/* 115:    */       {
/* 116:163 */         this._lang = new AttributeValueTemplate(value, parser, this);
/* 117:164 */         this._formatNeeded = true;
/* 118:    */       }
/* 119:166 */       else if (name.equals("letter-value"))
/* 120:    */       {
/* 121:167 */         this._letterValue = new AttributeValueTemplate(value, parser, this);
/* 122:168 */         this._formatNeeded = true;
/* 123:    */       }
/* 124:170 */       else if (name.equals("grouping-separator"))
/* 125:    */       {
/* 126:171 */         this._groupingSeparator = new AttributeValueTemplate(value, parser, this);
/* 127:172 */         this._formatNeeded = true;
/* 128:    */       }
/* 129:174 */       else if (name.equals("grouping-size"))
/* 130:    */       {
/* 131:175 */         this._groupingSize = new AttributeValueTemplate(value, parser, this);
/* 132:176 */         this._formatNeeded = true;
/* 133:    */       }
/* 134:    */     }
/* 135:    */   }
/* 136:    */   
/* 137:    */   public org.apache.xalan.xsltc.compiler.util.Type typeCheck(SymbolTable stable)
/* 138:    */     throws TypeCheckError
/* 139:    */   {
/* 140:182 */     if (this._value != null)
/* 141:    */     {
/* 142:183 */       org.apache.xalan.xsltc.compiler.util.Type tvalue = this._value.typeCheck(stable);
/* 143:184 */       if (!(tvalue instanceof RealType)) {
/* 144:185 */         this._value = new CastExpr(this._value, org.apache.xalan.xsltc.compiler.util.Type.Real);
/* 145:    */       }
/* 146:    */     }
/* 147:188 */     if (this._count != null) {
/* 148:189 */       this._count.typeCheck(stable);
/* 149:    */     }
/* 150:191 */     if (this._from != null) {
/* 151:192 */       this._from.typeCheck(stable);
/* 152:    */     }
/* 153:194 */     if (this._format != null) {
/* 154:195 */       this._format.typeCheck(stable);
/* 155:    */     }
/* 156:197 */     if (this._lang != null) {
/* 157:198 */       this._lang.typeCheck(stable);
/* 158:    */     }
/* 159:200 */     if (this._letterValue != null) {
/* 160:201 */       this._letterValue.typeCheck(stable);
/* 161:    */     }
/* 162:203 */     if (this._groupingSeparator != null) {
/* 163:204 */       this._groupingSeparator.typeCheck(stable);
/* 164:    */     }
/* 165:206 */     if (this._groupingSize != null) {
/* 166:207 */       this._groupingSize.typeCheck(stable);
/* 167:    */     }
/* 168:209 */     return org.apache.xalan.xsltc.compiler.util.Type.Void;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public boolean hasValue()
/* 172:    */   {
/* 173:216 */     return this._value != null;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public boolean isDefault()
/* 177:    */   {
/* 178:224 */     return (this._from == null) && (this._count == null);
/* 179:    */   }
/* 180:    */   
/* 181:    */   private void compileDefault(ClassGenerator classGen, MethodGenerator methodGen)
/* 182:    */   {
/* 183:230 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 184:231 */     InstructionList il = methodGen.getInstructionList();
/* 185:    */     
/* 186:233 */     int[] fieldIndexes = getXSLTC().getNumberFieldIndexes();
/* 187:235 */     if (fieldIndexes[this._level] == -1)
/* 188:    */     {
/* 189:236 */       Field defaultNode = new Field(2, cpg.addUtf8(FieldNames[this._level]), cpg.addUtf8("Lorg/apache/xalan/xsltc/dom/NodeCounter;"), null, cpg.getConstantPool());
/* 190:    */       
/* 191:    */ 
/* 192:    */ 
/* 193:    */ 
/* 194:    */ 
/* 195:    */ 
/* 196:243 */       classGen.addField(defaultNode);
/* 197:    */       
/* 198:    */ 
/* 199:246 */       fieldIndexes[this._level] = cpg.addFieldref(classGen.getClassName(), FieldNames[this._level], "Lorg/apache/xalan/xsltc/dom/NodeCounter;");
/* 200:    */     }
/* 201:252 */     il.append(classGen.loadTranslet());
/* 202:253 */     il.append(new GETFIELD(fieldIndexes[this._level]));
/* 203:254 */     BranchHandle ifBlock1 = il.append(new IFNONNULL(null));
/* 204:    */     
/* 205:    */ 
/* 206:257 */     int index = cpg.addMethodref(ClassNames[this._level], "getDefaultNodeCounter", "(Lorg/apache/xalan/xsltc/Translet;Lorg/apache/xalan/xsltc/DOM;Lorg/apache/xml/dtm/DTMAxisIterator;)Lorg/apache/xalan/xsltc/dom/NodeCounter;");
/* 207:    */     
/* 208:    */ 
/* 209:    */ 
/* 210:    */ 
/* 211:    */ 
/* 212:263 */     il.append(classGen.loadTranslet());
/* 213:264 */     il.append(methodGen.loadDOM());
/* 214:265 */     il.append(methodGen.loadIterator());
/* 215:266 */     il.append(new INVOKESTATIC(index));
/* 216:267 */     il.append(InstructionConstants.DUP);
/* 217:    */     
/* 218:    */ 
/* 219:270 */     il.append(classGen.loadTranslet());
/* 220:271 */     il.append(InstructionConstants.SWAP);
/* 221:272 */     il.append(new PUTFIELD(fieldIndexes[this._level]));
/* 222:273 */     BranchHandle ifBlock2 = il.append(new GOTO(null));
/* 223:    */     
/* 224:    */ 
/* 225:276 */     ifBlock1.setTarget(il.append(classGen.loadTranslet()));
/* 226:277 */     il.append(new GETFIELD(fieldIndexes[this._level]));
/* 227:    */     
/* 228:279 */     ifBlock2.setTarget(il.append(InstructionConstants.NOP));
/* 229:    */   }
/* 230:    */   
/* 231:    */   private void compileConstructor(ClassGenerator classGen)
/* 232:    */   {
/* 233:289 */     InstructionList il = new InstructionList();
/* 234:290 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 235:    */     
/* 236:292 */     MethodGenerator cons = new MethodGenerator(1, org.apache.bcel.generic.Type.VOID, new org.apache.bcel.generic.Type[] { Util.getJCRefType("Lorg/apache/xalan/xsltc/Translet;"), Util.getJCRefType("Lorg/apache/xalan/xsltc/DOM;"), Util.getJCRefType("Lorg/apache/xml/dtm/DTMAxisIterator;") }, new String[] { "dom", "translet", "iterator" }, "<init>", this._className, il, cpg);
/* 237:    */     
/* 238:    */ 
/* 239:    */ 
/* 240:    */ 
/* 241:    */ 
/* 242:    */ 
/* 243:    */ 
/* 244:    */ 
/* 245:    */ 
/* 246:    */ 
/* 247:    */ 
/* 248:    */ 
/* 249:    */ 
/* 250:306 */     il.append(InstructionConstants.ALOAD_0);
/* 251:307 */     il.append(InstructionConstants.ALOAD_1);
/* 252:308 */     il.append(InstructionConstants.ALOAD_2);
/* 253:309 */     il.append(new ALOAD(3));
/* 254:    */     
/* 255:311 */     int index = cpg.addMethodref(ClassNames[this._level], "<init>", "(Lorg/apache/xalan/xsltc/Translet;Lorg/apache/xalan/xsltc/DOM;Lorg/apache/xml/dtm/DTMAxisIterator;)V");
/* 256:    */     
/* 257:    */ 
/* 258:    */ 
/* 259:    */ 
/* 260:    */ 
/* 261:317 */     il.append(new INVOKESPECIAL(index));
/* 262:318 */     il.append(InstructionConstants.RETURN);
/* 263:    */     
/* 264:320 */     classGen.addMethod(cons);
/* 265:    */   }
/* 266:    */   
/* 267:    */   private void compileLocals(NodeCounterGenerator nodeCounterGen, MatchGenerator matchGen, InstructionList il)
/* 268:    */   {
/* 269:333 */     ConstantPoolGen cpg = nodeCounterGen.getConstantPool();
/* 270:    */     
/* 271:    */ 
/* 272:336 */     LocalVariableGen local = matchGen.addLocalVariable("iterator", Util.getJCRefType("Lorg/apache/xml/dtm/DTMAxisIterator;"), null, null);
/* 273:    */     
/* 274:    */ 
/* 275:339 */     int field = cpg.addFieldref("org.apache.xalan.xsltc.dom.NodeCounter", "_iterator", "Lorg/apache/xml/dtm/DTMAxisIterator;");
/* 276:    */     
/* 277:341 */     il.append(InstructionConstants.ALOAD_0);
/* 278:342 */     il.append(new GETFIELD(field));
/* 279:343 */     local.setStart(il.append(new ASTORE(local.getIndex())));
/* 280:344 */     matchGen.setIteratorIndex(local.getIndex());
/* 281:    */     
/* 282:    */ 
/* 283:347 */     local = matchGen.addLocalVariable("translet", Util.getJCRefType("Lorg/apache/xalan/xsltc/runtime/AbstractTranslet;"), null, null);
/* 284:    */     
/* 285:    */ 
/* 286:350 */     field = cpg.addFieldref("org.apache.xalan.xsltc.dom.NodeCounter", "_translet", "Lorg/apache/xalan/xsltc/Translet;");
/* 287:    */     
/* 288:352 */     il.append(InstructionConstants.ALOAD_0);
/* 289:353 */     il.append(new GETFIELD(field));
/* 290:354 */     il.append(new CHECKCAST(cpg.addClass("org.apache.xalan.xsltc.runtime.AbstractTranslet")));
/* 291:355 */     local.setStart(il.append(new ASTORE(local.getIndex())));
/* 292:356 */     nodeCounterGen.setTransletIndex(local.getIndex());
/* 293:    */     
/* 294:    */ 
/* 295:359 */     local = matchGen.addLocalVariable("document", Util.getJCRefType("Lorg/apache/xalan/xsltc/DOM;"), null, null);
/* 296:    */     
/* 297:    */ 
/* 298:362 */     field = cpg.addFieldref(this._className, "_document", "Lorg/apache/xalan/xsltc/DOM;");
/* 299:363 */     il.append(InstructionConstants.ALOAD_0);
/* 300:364 */     il.append(new GETFIELD(field));
/* 301:    */     
/* 302:366 */     local.setStart(il.append(new ASTORE(local.getIndex())));
/* 303:367 */     matchGen.setDomIndex(local.getIndex());
/* 304:    */   }
/* 305:    */   
/* 306:    */   private void compilePatterns(ClassGenerator classGen, MethodGenerator methodGen)
/* 307:    */   {
/* 308:379 */     this._className = getXSLTC().getHelperClassName();
/* 309:380 */     NodeCounterGenerator nodeCounterGen = new NodeCounterGenerator(this._className, ClassNames[this._level], toString(), 33, null, classGen.getStylesheet());
/* 310:    */     
/* 311:    */ 
/* 312:    */ 
/* 313:    */ 
/* 314:    */ 
/* 315:386 */     InstructionList il = null;
/* 316:387 */     ConstantPoolGen cpg = nodeCounterGen.getConstantPool();
/* 317:    */     
/* 318:    */ 
/* 319:390 */     int closureLen = this._closureVars == null ? 0 : this._closureVars.size();
/* 320:393 */     for (int i = 0; i < closureLen; i++)
/* 321:    */     {
/* 322:394 */       VariableBase var = ((VariableRefBase)this._closureVars.get(i)).getVariable();
/* 323:    */       
/* 324:    */ 
/* 325:397 */       nodeCounterGen.addField(new Field(1, cpg.addUtf8(var.getEscapedName()), cpg.addUtf8(var.getType().toSignature()), null, cpg.getConstantPool()));
/* 326:    */     }
/* 327:404 */     compileConstructor(nodeCounterGen);
/* 328:    */     MatchGenerator matchGen;
/* 329:409 */     if (this._from != null)
/* 330:    */     {
/* 331:410 */       il = new InstructionList();
/* 332:411 */       matchGen = new MatchGenerator(17, org.apache.bcel.generic.Type.BOOLEAN, new org.apache.bcel.generic.Type[] { org.apache.bcel.generic.Type.INT }, new String[] { "node" }, "matchesFrom", this._className, il, cpg);
/* 333:    */       
/* 334:    */ 
/* 335:    */ 
/* 336:    */ 
/* 337:    */ 
/* 338:    */ 
/* 339:    */ 
/* 340:    */ 
/* 341:    */ 
/* 342:    */ 
/* 343:422 */       compileLocals(nodeCounterGen, matchGen, il);
/* 344:    */       
/* 345:    */ 
/* 346:425 */       il.append(matchGen.loadContextNode());
/* 347:426 */       this._from.translate(nodeCounterGen, matchGen);
/* 348:427 */       this._from.synthesize(nodeCounterGen, matchGen);
/* 349:428 */       il.append(InstructionConstants.IRETURN);
/* 350:    */       
/* 351:430 */       nodeCounterGen.addMethod(matchGen);
/* 352:    */     }
/* 353:436 */     if (this._count != null)
/* 354:    */     {
/* 355:437 */       il = new InstructionList();
/* 356:438 */       matchGen = new MatchGenerator(17, org.apache.bcel.generic.Type.BOOLEAN, new org.apache.bcel.generic.Type[] { org.apache.bcel.generic.Type.INT }, new String[] { "node" }, "matchesCount", this._className, il, cpg);
/* 357:    */       
/* 358:    */ 
/* 359:    */ 
/* 360:    */ 
/* 361:    */ 
/* 362:    */ 
/* 363:    */ 
/* 364:    */ 
/* 365:    */ 
/* 366:448 */       compileLocals(nodeCounterGen, matchGen, il);
/* 367:    */       
/* 368:    */ 
/* 369:451 */       il.append(matchGen.loadContextNode());
/* 370:452 */       this._count.translate(nodeCounterGen, matchGen);
/* 371:453 */       this._count.synthesize(nodeCounterGen, matchGen);
/* 372:    */       
/* 373:455 */       il.append(InstructionConstants.IRETURN);
/* 374:    */       
/* 375:457 */       nodeCounterGen.addMethod(matchGen);
/* 376:    */     }
/* 377:460 */     getXSLTC().dumpClass(nodeCounterGen.getJavaClass());
/* 378:    */     
/* 379:    */ 
/* 380:463 */     cpg = classGen.getConstantPool();
/* 381:464 */     il = methodGen.getInstructionList();
/* 382:    */     
/* 383:466 */     int index = cpg.addMethodref(this._className, "<init>", "(Lorg/apache/xalan/xsltc/Translet;Lorg/apache/xalan/xsltc/DOM;Lorg/apache/xml/dtm/DTMAxisIterator;)V");
/* 384:    */     
/* 385:    */ 
/* 386:    */ 
/* 387:    */ 
/* 388:471 */     il.append(new NEW(cpg.addClass(this._className)));
/* 389:472 */     il.append(InstructionConstants.DUP);
/* 390:473 */     il.append(classGen.loadTranslet());
/* 391:474 */     il.append(methodGen.loadDOM());
/* 392:475 */     il.append(methodGen.loadIterator());
/* 393:476 */     il.append(new INVOKESPECIAL(index));
/* 394:479 */     for (int i = 0; i < closureLen; i++)
/* 395:    */     {
/* 396:480 */       VariableRefBase varRef = (VariableRefBase)this._closureVars.get(i);
/* 397:481 */       VariableBase var = varRef.getVariable();
/* 398:482 */       org.apache.xalan.xsltc.compiler.util.Type varType = var.getType();
/* 399:    */       
/* 400:    */ 
/* 401:485 */       il.append(InstructionConstants.DUP);
/* 402:486 */       il.append(var.loadInstruction());
/* 403:487 */       il.append(new PUTFIELD(cpg.addFieldref(this._className, var.getEscapedName(), varType.toSignature())));
/* 404:    */     }
/* 405:    */   }
/* 406:    */   
/* 407:    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/* 408:    */   {
/* 409:495 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 410:496 */     InstructionList il = methodGen.getInstructionList();
/* 411:    */     
/* 412:    */ 
/* 413:499 */     il.append(classGen.loadTranslet());
/* 414:501 */     if (hasValue())
/* 415:    */     {
/* 416:502 */       compileDefault(classGen, methodGen);
/* 417:503 */       this._value.translate(classGen, methodGen);
/* 418:    */       
/* 419:    */ 
/* 420:506 */       il.append(new PUSH(cpg, 0.5D));
/* 421:507 */       il.append(InstructionConstants.DADD);
/* 422:508 */       index = cpg.addMethodref("java.lang.Math", "floor", "(D)D");
/* 423:509 */       il.append(new INVOKESTATIC(index));
/* 424:    */       
/* 425:    */ 
/* 426:512 */       index = cpg.addMethodref("org.apache.xalan.xsltc.dom.NodeCounter", "setValue", "(D)Lorg/apache/xalan/xsltc/dom/NodeCounter;");
/* 427:    */       
/* 428:    */ 
/* 429:515 */       il.append(new INVOKEVIRTUAL(index));
/* 430:    */     }
/* 431:517 */     else if (isDefault())
/* 432:    */     {
/* 433:518 */       compileDefault(classGen, methodGen);
/* 434:    */     }
/* 435:    */     else
/* 436:    */     {
/* 437:521 */       compilePatterns(classGen, methodGen);
/* 438:    */     }
/* 439:525 */     if (!hasValue())
/* 440:    */     {
/* 441:526 */       il.append(methodGen.loadContextNode());
/* 442:527 */       index = cpg.addMethodref("org.apache.xalan.xsltc.dom.NodeCounter", "setStartNode", "(I)Lorg/apache/xalan/xsltc/dom/NodeCounter;");
/* 443:    */       
/* 444:    */ 
/* 445:530 */       il.append(new INVOKEVIRTUAL(index));
/* 446:    */     }
/* 447:534 */     if (this._formatNeeded)
/* 448:    */     {
/* 449:535 */       if (this._format != null) {
/* 450:536 */         this._format.translate(classGen, methodGen);
/* 451:    */       } else {
/* 452:539 */         il.append(new PUSH(cpg, "1"));
/* 453:    */       }
/* 454:542 */       if (this._lang != null) {
/* 455:543 */         this._lang.translate(classGen, methodGen);
/* 456:    */       } else {
/* 457:546 */         il.append(new PUSH(cpg, "en"));
/* 458:    */       }
/* 459:549 */       if (this._letterValue != null) {
/* 460:550 */         this._letterValue.translate(classGen, methodGen);
/* 461:    */       } else {
/* 462:553 */         il.append(new PUSH(cpg, ""));
/* 463:    */       }
/* 464:556 */       if (this._groupingSeparator != null) {
/* 465:557 */         this._groupingSeparator.translate(classGen, methodGen);
/* 466:    */       } else {
/* 467:560 */         il.append(new PUSH(cpg, ""));
/* 468:    */       }
/* 469:563 */       if (this._groupingSize != null) {
/* 470:564 */         this._groupingSize.translate(classGen, methodGen);
/* 471:    */       } else {
/* 472:567 */         il.append(new PUSH(cpg, "0"));
/* 473:    */       }
/* 474:570 */       index = cpg.addMethodref("org.apache.xalan.xsltc.dom.NodeCounter", "getCounter", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;");
/* 475:    */       
/* 476:    */ 
/* 477:    */ 
/* 478:574 */       il.append(new INVOKEVIRTUAL(index));
/* 479:    */     }
/* 480:    */     else
/* 481:    */     {
/* 482:577 */       index = cpg.addMethodref("org.apache.xalan.xsltc.dom.NodeCounter", "setDefaultFormatting", "()Lorg/apache/xalan/xsltc/dom/NodeCounter;");
/* 483:    */       
/* 484:579 */       il.append(new INVOKEVIRTUAL(index));
/* 485:    */       
/* 486:581 */       index = cpg.addMethodref("org.apache.xalan.xsltc.dom.NodeCounter", "getCounter", "()Ljava/lang/String;");
/* 487:    */       
/* 488:583 */       il.append(new INVOKEVIRTUAL(index));
/* 489:    */     }
/* 490:587 */     il.append(methodGen.loadHandler());
/* 491:588 */     int index = cpg.addMethodref("org.apache.xalan.xsltc.runtime.AbstractTranslet", "characters", Constants.CHARACTERSW_SIG);
/* 492:    */     
/* 493:    */ 
/* 494:591 */     il.append(new INVOKEVIRTUAL(index));
/* 495:    */   }
/* 496:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.Number
 * JD-Core Version:    0.7.0.1
 */