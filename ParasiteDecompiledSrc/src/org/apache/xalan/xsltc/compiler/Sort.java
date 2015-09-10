/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Vector;
/*   5:    */ import org.apache.bcel.classfile.Field;
/*   6:    */ import org.apache.bcel.generic.ALOAD;
/*   7:    */ import org.apache.bcel.generic.ANEWARRAY;
/*   8:    */ import org.apache.bcel.generic.ASTORE;
/*   9:    */ import org.apache.bcel.generic.CHECKCAST;
/*  10:    */ import org.apache.bcel.generic.ClassGen;
/*  11:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*  12:    */ import org.apache.bcel.generic.GETFIELD;
/*  13:    */ import org.apache.bcel.generic.ILOAD;
/*  14:    */ import org.apache.bcel.generic.INVOKEINTERFACE;
/*  15:    */ import org.apache.bcel.generic.INVOKESPECIAL;
/*  16:    */ import org.apache.bcel.generic.InstructionConstants;
/*  17:    */ import org.apache.bcel.generic.InstructionHandle;
/*  18:    */ import org.apache.bcel.generic.InstructionList;
/*  19:    */ import org.apache.bcel.generic.LocalVariableGen;
/*  20:    */ import org.apache.bcel.generic.MethodGen;
/*  21:    */ import org.apache.bcel.generic.NEW;
/*  22:    */ import org.apache.bcel.generic.NOP;
/*  23:    */ import org.apache.bcel.generic.PUSH;
/*  24:    */ import org.apache.bcel.generic.PUTFIELD;
/*  25:    */ import org.apache.bcel.generic.TABLESWITCH;
/*  26:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  27:    */ import org.apache.xalan.xsltc.compiler.util.CompareGenerator;
/*  28:    */ import org.apache.xalan.xsltc.compiler.util.IntType;
/*  29:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  30:    */ import org.apache.xalan.xsltc.compiler.util.NodeSortRecordFactGenerator;
/*  31:    */ import org.apache.xalan.xsltc.compiler.util.NodeSortRecordGenerator;
/*  32:    */ import org.apache.xalan.xsltc.compiler.util.StringType;
/*  33:    */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*  34:    */ import org.apache.xalan.xsltc.compiler.util.Util;
/*  35:    */ 
/*  36:    */ final class Sort
/*  37:    */   extends Instruction
/*  38:    */   implements Closure
/*  39:    */ {
/*  40:    */   private Expression _select;
/*  41:    */   private AttributeValue _order;
/*  42:    */   private AttributeValue _caseOrder;
/*  43:    */   private AttributeValue _dataType;
/*  44:    */   private String _lang;
/*  45: 79 */   private String _data = null;
/*  46: 82 */   private String _className = null;
/*  47: 83 */   private ArrayList _closureVars = null;
/*  48: 84 */   private boolean _needsSortRecordFactory = false;
/*  49:    */   
/*  50:    */   public boolean inInnerClass()
/*  51:    */   {
/*  52: 93 */     return this._className != null;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public Closure getParentClosure()
/*  56:    */   {
/*  57:100 */     return null;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public String getInnerClassName()
/*  61:    */   {
/*  62:108 */     return this._className;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void addVariable(VariableRefBase variableRef)
/*  66:    */   {
/*  67:115 */     if (this._closureVars == null) {
/*  68:116 */       this._closureVars = new ArrayList();
/*  69:    */     }
/*  70:120 */     if (!this._closureVars.contains(variableRef))
/*  71:    */     {
/*  72:121 */       this._closureVars.add(variableRef);
/*  73:122 */       this._needsSortRecordFactory = true;
/*  74:    */     }
/*  75:    */   }
/*  76:    */   
/*  77:    */   private void setInnerClassName(String className)
/*  78:    */   {
/*  79:129 */     this._className = className;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void parseContents(Parser parser)
/*  83:    */   {
/*  84:137 */     SyntaxTreeNode parent = getParent();
/*  85:138 */     if ((!(parent instanceof ApplyTemplates)) && (!(parent instanceof ForEach)))
/*  86:    */     {
/*  87:140 */       reportError(this, parser, "STRAY_SORT_ERR", null);
/*  88:141 */       return;
/*  89:    */     }
/*  90:145 */     this._select = parser.parseExpression(this, "select", "string(.)");
/*  91:    */     
/*  92:    */ 
/*  93:148 */     String val = getAttribute("order");
/*  94:149 */     if (val.length() == 0) {
/*  95:149 */       val = "ascending";
/*  96:    */     }
/*  97:150 */     this._order = AttributeValue.create(this, val, parser);
/*  98:    */     
/*  99:    */ 
/* 100:153 */     val = getAttribute("data-type");
/* 101:154 */     if (val.length() == 0) {
/* 102:    */       try
/* 103:    */       {
/* 104:156 */         org.apache.xalan.xsltc.compiler.util.Type type = this._select.typeCheck(parser.getSymbolTable());
/* 105:157 */         if ((type instanceof IntType)) {
/* 106:158 */           val = "number";
/* 107:    */         } else {
/* 108:160 */           val = "text";
/* 109:    */         }
/* 110:    */       }
/* 111:    */       catch (TypeCheckError e)
/* 112:    */       {
/* 113:163 */         val = "text";
/* 114:    */       }
/* 115:    */     }
/* 116:166 */     this._dataType = AttributeValue.create(this, val, parser);
/* 117:    */     
/* 118:168 */     this._lang = getAttribute("lang");
/* 119:    */     
/* 120:    */ 
/* 121:    */ 
/* 122:172 */     val = getAttribute("case-order");
/* 123:173 */     this._caseOrder = AttributeValue.create(this, val, parser);
/* 124:    */   }
/* 125:    */   
/* 126:    */   public org.apache.xalan.xsltc.compiler.util.Type typeCheck(SymbolTable stable)
/* 127:    */     throws TypeCheckError
/* 128:    */   {
/* 129:182 */     org.apache.xalan.xsltc.compiler.util.Type tselect = this._select.typeCheck(stable);
/* 130:186 */     if (!(tselect instanceof StringType)) {
/* 131:187 */       this._select = new CastExpr(this._select, org.apache.xalan.xsltc.compiler.util.Type.String);
/* 132:    */     }
/* 133:190 */     this._order.typeCheck(stable);
/* 134:191 */     this._caseOrder.typeCheck(stable);
/* 135:192 */     this._dataType.typeCheck(stable);
/* 136:193 */     return org.apache.xalan.xsltc.compiler.util.Type.Void;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void translateSortType(ClassGenerator classGen, MethodGenerator methodGen)
/* 140:    */   {
/* 141:202 */     this._dataType.translate(classGen, methodGen);
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void translateSortOrder(ClassGenerator classGen, MethodGenerator methodGen)
/* 145:    */   {
/* 146:207 */     this._order.translate(classGen, methodGen);
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void translateCaseOrder(ClassGenerator classGen, MethodGenerator methodGen)
/* 150:    */   {
/* 151:212 */     this._caseOrder.translate(classGen, methodGen);
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void translateLang(ClassGenerator classGen, MethodGenerator methodGen)
/* 155:    */   {
/* 156:217 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 157:218 */     InstructionList il = methodGen.getInstructionList();
/* 158:219 */     il.append(new PUSH(cpg, this._lang));
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void translateSelect(ClassGenerator classGen, MethodGenerator methodGen)
/* 162:    */   {
/* 163:229 */     this._select.translate(classGen, methodGen);
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {}
/* 167:    */   
/* 168:    */   public static void translateSortIterator(ClassGenerator classGen, MethodGenerator methodGen, Expression nodeSet, Vector sortObjects)
/* 169:    */   {
/* 170:249 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 171:250 */     InstructionList il = methodGen.getInstructionList();
/* 172:    */     
/* 173:    */ 
/* 174:253 */     int init = cpg.addMethodref("org.apache.xalan.xsltc.dom.SortingIterator", "<init>", "(Lorg/apache/xml/dtm/DTMAxisIterator;Lorg/apache/xalan/xsltc/dom/NodeSortRecordFactory;)V");
/* 175:    */     
/* 176:    */ 
/* 177:    */ 
/* 178:    */ 
/* 179:    */ 
/* 180:    */ 
/* 181:    */ 
/* 182:    */ 
/* 183:    */ 
/* 184:    */ 
/* 185:    */ 
/* 186:    */ 
/* 187:    */ 
/* 188:    */ 
/* 189:268 */     LocalVariableGen nodesTemp = methodGen.addLocalVariable("sort_tmp1", Util.getJCRefType("Lorg/apache/xml/dtm/DTMAxisIterator;"), null, null);
/* 190:    */     
/* 191:    */ 
/* 192:    */ 
/* 193:    */ 
/* 194:273 */     LocalVariableGen sortRecordFactoryTemp = methodGen.addLocalVariable("sort_tmp2", Util.getJCRefType("Lorg/apache/xalan/xsltc/dom/NodeSortRecordFactory;"), null, null);
/* 195:279 */     if (nodeSet == null)
/* 196:    */     {
/* 197:280 */       int children = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "getAxisIterator", "(I)Lorg/apache/xml/dtm/DTMAxisIterator;");
/* 198:    */       
/* 199:    */ 
/* 200:    */ 
/* 201:284 */       il.append(methodGen.loadDOM());
/* 202:285 */       il.append(new PUSH(cpg, 3));
/* 203:286 */       il.append(new INVOKEINTERFACE(children, 2));
/* 204:    */     }
/* 205:    */     else
/* 206:    */     {
/* 207:289 */       nodeSet.translate(classGen, methodGen);
/* 208:    */     }
/* 209:292 */     nodesTemp.setStart(il.append(new ASTORE(nodesTemp.getIndex())));
/* 210:    */     
/* 211:    */ 
/* 212:    */ 
/* 213:296 */     compileSortRecordFactory(sortObjects, classGen, methodGen);
/* 214:297 */     sortRecordFactoryTemp.setStart(il.append(new ASTORE(sortRecordFactoryTemp.getIndex())));
/* 215:    */     
/* 216:    */ 
/* 217:300 */     il.append(new NEW(cpg.addClass("org.apache.xalan.xsltc.dom.SortingIterator")));
/* 218:301 */     il.append(InstructionConstants.DUP);
/* 219:302 */     nodesTemp.setEnd(il.append(new ALOAD(nodesTemp.getIndex())));
/* 220:303 */     sortRecordFactoryTemp.setEnd(il.append(new ALOAD(sortRecordFactoryTemp.getIndex())));
/* 221:    */     
/* 222:305 */     il.append(new INVOKESPECIAL(init));
/* 223:    */   }
/* 224:    */   
/* 225:    */   public static void compileSortRecordFactory(Vector sortObjects, ClassGenerator classGen, MethodGenerator methodGen)
/* 226:    */   {
/* 227:316 */     String sortRecordClass = compileSortRecord(sortObjects, classGen, methodGen);
/* 228:    */     
/* 229:    */ 
/* 230:319 */     boolean needsSortRecordFactory = false;
/* 231:320 */     int nsorts = sortObjects.size();
/* 232:321 */     for (int i = 0; i < nsorts; i++)
/* 233:    */     {
/* 234:322 */       Sort sort = (Sort)sortObjects.elementAt(i);
/* 235:323 */       needsSortRecordFactory |= sort._needsSortRecordFactory;
/* 236:    */     }
/* 237:326 */     String sortRecordFactoryClass = "org/apache/xalan/xsltc/dom/NodeSortRecordFactory";
/* 238:327 */     if (needsSortRecordFactory) {
/* 239:328 */       sortRecordFactoryClass = compileSortRecordFactory(sortObjects, classGen, methodGen, sortRecordClass);
/* 240:    */     }
/* 241:333 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 242:334 */     InstructionList il = methodGen.getInstructionList();
/* 243:    */     
/* 244:    */ 
/* 245:    */ 
/* 246:    */ 
/* 247:    */ 
/* 248:    */ 
/* 249:    */ 
/* 250:    */ 
/* 251:    */ 
/* 252:    */ 
/* 253:    */ 
/* 254:346 */     LocalVariableGen sortOrderTemp = methodGen.addLocalVariable("sort_order_tmp", Util.getJCRefType("[Ljava/lang/String;"), null, null);
/* 255:    */     
/* 256:    */ 
/* 257:    */ 
/* 258:350 */     il.append(new PUSH(cpg, nsorts));
/* 259:351 */     il.append(new ANEWARRAY(cpg.addClass("java.lang.String")));
/* 260:352 */     for (int level = 0; level < nsorts; level++)
/* 261:    */     {
/* 262:353 */       Sort sort = (Sort)sortObjects.elementAt(level);
/* 263:354 */       il.append(InstructionConstants.DUP);
/* 264:355 */       il.append(new PUSH(cpg, level));
/* 265:356 */       sort.translateSortOrder(classGen, methodGen);
/* 266:357 */       il.append(InstructionConstants.AASTORE);
/* 267:    */     }
/* 268:359 */     sortOrderTemp.setStart(il.append(new ASTORE(sortOrderTemp.getIndex())));
/* 269:    */     
/* 270:361 */     LocalVariableGen sortTypeTemp = methodGen.addLocalVariable("sort_type_tmp", Util.getJCRefType("[Ljava/lang/String;"), null, null);
/* 271:    */     
/* 272:    */ 
/* 273:    */ 
/* 274:365 */     il.append(new PUSH(cpg, nsorts));
/* 275:366 */     il.append(new ANEWARRAY(cpg.addClass("java.lang.String")));
/* 276:367 */     for (int level = 0; level < nsorts; level++)
/* 277:    */     {
/* 278:368 */       Sort sort = (Sort)sortObjects.elementAt(level);
/* 279:369 */       il.append(InstructionConstants.DUP);
/* 280:370 */       il.append(new PUSH(cpg, level));
/* 281:371 */       sort.translateSortType(classGen, methodGen);
/* 282:372 */       il.append(InstructionConstants.AASTORE);
/* 283:    */     }
/* 284:374 */     sortTypeTemp.setStart(il.append(new ASTORE(sortTypeTemp.getIndex())));
/* 285:    */     
/* 286:376 */     LocalVariableGen sortLangTemp = methodGen.addLocalVariable("sort_lang_tmp", Util.getJCRefType("[Ljava/lang/String;"), null, null);
/* 287:    */     
/* 288:    */ 
/* 289:    */ 
/* 290:380 */     il.append(new PUSH(cpg, nsorts));
/* 291:381 */     il.append(new ANEWARRAY(cpg.addClass("java.lang.String")));
/* 292:382 */     for (int level = 0; level < nsorts; level++)
/* 293:    */     {
/* 294:383 */       Sort sort = (Sort)sortObjects.elementAt(level);
/* 295:384 */       il.append(InstructionConstants.DUP);
/* 296:385 */       il.append(new PUSH(cpg, level));
/* 297:386 */       sort.translateLang(classGen, methodGen);
/* 298:387 */       il.append(InstructionConstants.AASTORE);
/* 299:    */     }
/* 300:389 */     sortLangTemp.setStart(il.append(new ASTORE(sortLangTemp.getIndex())));
/* 301:    */     
/* 302:391 */     LocalVariableGen sortCaseOrderTemp = methodGen.addLocalVariable("sort_case_order_tmp", Util.getJCRefType("[Ljava/lang/String;"), null, null);
/* 303:    */     
/* 304:    */ 
/* 305:    */ 
/* 306:395 */     il.append(new PUSH(cpg, nsorts));
/* 307:396 */     il.append(new ANEWARRAY(cpg.addClass("java.lang.String")));
/* 308:397 */     for (int level = 0; level < nsorts; level++)
/* 309:    */     {
/* 310:398 */       Sort sort = (Sort)sortObjects.elementAt(level);
/* 311:399 */       il.append(InstructionConstants.DUP);
/* 312:400 */       il.append(new PUSH(cpg, level));
/* 313:401 */       sort.translateCaseOrder(classGen, methodGen);
/* 314:402 */       il.append(InstructionConstants.AASTORE);
/* 315:    */     }
/* 316:404 */     sortCaseOrderTemp.setStart(il.append(new ASTORE(sortCaseOrderTemp.getIndex())));
/* 317:    */     
/* 318:    */ 
/* 319:407 */     il.append(new NEW(cpg.addClass(sortRecordFactoryClass)));
/* 320:408 */     il.append(InstructionConstants.DUP);
/* 321:409 */     il.append(methodGen.loadDOM());
/* 322:410 */     il.append(new PUSH(cpg, sortRecordClass));
/* 323:411 */     il.append(classGen.loadTranslet());
/* 324:    */     
/* 325:413 */     sortOrderTemp.setEnd(il.append(new ALOAD(sortOrderTemp.getIndex())));
/* 326:414 */     sortTypeTemp.setEnd(il.append(new ALOAD(sortTypeTemp.getIndex())));
/* 327:415 */     sortLangTemp.setEnd(il.append(new ALOAD(sortLangTemp.getIndex())));
/* 328:416 */     sortCaseOrderTemp.setEnd(il.append(new ALOAD(sortCaseOrderTemp.getIndex())));
/* 329:    */     
/* 330:    */ 
/* 331:419 */     il.append(new INVOKESPECIAL(cpg.addMethodref(sortRecordFactoryClass, "<init>", "(Lorg/apache/xalan/xsltc/DOM;Ljava/lang/String;Lorg/apache/xalan/xsltc/Translet;[Ljava/lang/String;[Ljava/lang/String;[Ljava/lang/String;[Ljava/lang/String;)V")));
/* 332:    */     
/* 333:    */ 
/* 334:    */ 
/* 335:    */ 
/* 336:    */ 
/* 337:    */ 
/* 338:    */ 
/* 339:    */ 
/* 340:    */ 
/* 341:    */ 
/* 342:430 */     ArrayList dups = new ArrayList();
/* 343:432 */     for (int j = 0; j < nsorts; j++)
/* 344:    */     {
/* 345:433 */       Sort sort = (Sort)sortObjects.get(j);
/* 346:434 */       int length = sort._closureVars == null ? 0 : sort._closureVars.size();
/* 347:437 */       for (int i = 0; i < length; i++)
/* 348:    */       {
/* 349:438 */         VariableRefBase varRef = (VariableRefBase)sort._closureVars.get(i);
/* 350:441 */         if (!dups.contains(varRef))
/* 351:    */         {
/* 352:443 */           VariableBase var = varRef.getVariable();
/* 353:    */           
/* 354:    */ 
/* 355:446 */           il.append(InstructionConstants.DUP);
/* 356:447 */           il.append(var.loadInstruction());
/* 357:448 */           il.append(new PUTFIELD(cpg.addFieldref(sortRecordFactoryClass, var.getEscapedName(), var.getType().toSignature())));
/* 358:    */           
/* 359:    */ 
/* 360:451 */           dups.add(varRef);
/* 361:    */         }
/* 362:    */       }
/* 363:    */     }
/* 364:    */   }
/* 365:    */   
/* 366:    */   public static String compileSortRecordFactory(Vector sortObjects, ClassGenerator classGen, MethodGenerator methodGen, String sortRecordClass)
/* 367:    */   {
/* 368:460 */     XSLTC xsltc = ((Sort)sortObjects.firstElement()).getXSLTC();
/* 369:461 */     String className = xsltc.getHelperClassName();
/* 370:    */     
/* 371:463 */     NodeSortRecordFactGenerator sortRecordFactory = new NodeSortRecordFactGenerator(className, "org/apache/xalan/xsltc/dom/NodeSortRecordFactory", className + ".java", 49, new String[0], classGen.getStylesheet());
/* 372:    */     
/* 373:    */ 
/* 374:    */ 
/* 375:    */ 
/* 376:    */ 
/* 377:    */ 
/* 378:    */ 
/* 379:471 */     ConstantPoolGen cpg = sortRecordFactory.getConstantPool();
/* 380:    */     
/* 381:    */ 
/* 382:474 */     int nsorts = sortObjects.size();
/* 383:475 */     ArrayList dups = new ArrayList();
/* 384:477 */     for (int j = 0; j < nsorts; j++)
/* 385:    */     {
/* 386:478 */       Sort sort = (Sort)sortObjects.get(j);
/* 387:479 */       int length = sort._closureVars == null ? 0 : sort._closureVars.size();
/* 388:482 */       for (int i = 0; i < length; i++)
/* 389:    */       {
/* 390:483 */         VariableRefBase varRef = (VariableRefBase)sort._closureVars.get(i);
/* 391:486 */         if (!dups.contains(varRef))
/* 392:    */         {
/* 393:488 */           VariableBase var = varRef.getVariable();
/* 394:489 */           sortRecordFactory.addField(new Field(1, cpg.addUtf8(var.getEscapedName()), cpg.addUtf8(var.getType().toSignature()), null, cpg.getConstantPool()));
/* 395:    */           
/* 396:    */ 
/* 397:    */ 
/* 398:493 */           dups.add(varRef);
/* 399:    */         }
/* 400:    */       }
/* 401:    */     }
/* 402:498 */     org.apache.bcel.generic.Type[] argTypes = new org.apache.bcel.generic.Type[7];
/* 403:    */     
/* 404:500 */     argTypes[0] = Util.getJCRefType("Lorg/apache/xalan/xsltc/DOM;");
/* 405:501 */     argTypes[1] = Util.getJCRefType("Ljava/lang/String;");
/* 406:502 */     argTypes[2] = Util.getJCRefType("Lorg/apache/xalan/xsltc/Translet;");
/* 407:503 */     argTypes[3] = Util.getJCRefType("[Ljava/lang/String;");
/* 408:504 */     argTypes[4] = Util.getJCRefType("[Ljava/lang/String;");
/* 409:505 */     argTypes[5] = Util.getJCRefType("[Ljava/lang/String;");
/* 410:506 */     argTypes[6] = Util.getJCRefType("[Ljava/lang/String;");
/* 411:    */     
/* 412:508 */     String[] argNames = new String[7];
/* 413:509 */     argNames[0] = "document";
/* 414:510 */     argNames[1] = "className";
/* 415:511 */     argNames[2] = "translet";
/* 416:512 */     argNames[3] = "order";
/* 417:513 */     argNames[4] = "type";
/* 418:514 */     argNames[5] = "lang";
/* 419:515 */     argNames[6] = "case_order";
/* 420:    */     
/* 421:    */ 
/* 422:518 */     InstructionList il = new InstructionList();
/* 423:519 */     MethodGenerator constructor = new MethodGenerator(1, org.apache.bcel.generic.Type.VOID, argTypes, argNames, "<init>", className, il, cpg);
/* 424:    */     
/* 425:    */ 
/* 426:    */ 
/* 427:    */ 
/* 428:    */ 
/* 429:    */ 
/* 430:526 */     il.append(InstructionConstants.ALOAD_0);
/* 431:527 */     il.append(InstructionConstants.ALOAD_1);
/* 432:528 */     il.append(InstructionConstants.ALOAD_2);
/* 433:529 */     il.append(new ALOAD(3));
/* 434:530 */     il.append(new ALOAD(4));
/* 435:531 */     il.append(new ALOAD(5));
/* 436:532 */     il.append(new ALOAD(6));
/* 437:533 */     il.append(new ALOAD(7));
/* 438:534 */     il.append(new INVOKESPECIAL(cpg.addMethodref("org/apache/xalan/xsltc/dom/NodeSortRecordFactory", "<init>", "(Lorg/apache/xalan/xsltc/DOM;Ljava/lang/String;Lorg/apache/xalan/xsltc/Translet;[Ljava/lang/String;[Ljava/lang/String;[Ljava/lang/String;[Ljava/lang/String;)V")));
/* 439:    */     
/* 440:    */ 
/* 441:    */ 
/* 442:    */ 
/* 443:    */ 
/* 444:    */ 
/* 445:    */ 
/* 446:    */ 
/* 447:543 */     il.append(InstructionConstants.RETURN);
/* 448:    */     
/* 449:    */ 
/* 450:546 */     il = new InstructionList();
/* 451:547 */     MethodGenerator makeNodeSortRecord = new MethodGenerator(1, Util.getJCRefType("Lorg/apache/xalan/xsltc/dom/NodeSortRecord;"), new org.apache.bcel.generic.Type[] { org.apache.bcel.generic.Type.INT, org.apache.bcel.generic.Type.INT }, new String[] { "node", "last" }, "makeNodeSortRecord", className, il, cpg);
/* 452:    */     
/* 453:    */ 
/* 454:    */ 
/* 455:    */ 
/* 456:    */ 
/* 457:    */ 
/* 458:    */ 
/* 459:    */ 
/* 460:556 */     il.append(InstructionConstants.ALOAD_0);
/* 461:557 */     il.append(InstructionConstants.ILOAD_1);
/* 462:558 */     il.append(InstructionConstants.ILOAD_2);
/* 463:559 */     il.append(new INVOKESPECIAL(cpg.addMethodref("org/apache/xalan/xsltc/dom/NodeSortRecordFactory", "makeNodeSortRecord", "(II)Lorg/apache/xalan/xsltc/dom/NodeSortRecord;")));
/* 464:    */     
/* 465:561 */     il.append(InstructionConstants.DUP);
/* 466:562 */     il.append(new CHECKCAST(cpg.addClass(sortRecordClass)));
/* 467:    */     
/* 468:    */ 
/* 469:565 */     int ndups = dups.size();
/* 470:566 */     for (int i = 0; i < ndups; i++)
/* 471:    */     {
/* 472:567 */       VariableRefBase varRef = (VariableRefBase)dups.get(i);
/* 473:568 */       VariableBase var = varRef.getVariable();
/* 474:569 */       org.apache.xalan.xsltc.compiler.util.Type varType = var.getType();
/* 475:    */       
/* 476:571 */       il.append(InstructionConstants.DUP);
/* 477:    */       
/* 478:    */ 
/* 479:574 */       il.append(InstructionConstants.ALOAD_0);
/* 480:575 */       il.append(new GETFIELD(cpg.addFieldref(className, var.getEscapedName(), varType.toSignature())));
/* 481:    */       
/* 482:    */ 
/* 483:    */ 
/* 484:    */ 
/* 485:580 */       il.append(new PUTFIELD(cpg.addFieldref(sortRecordClass, var.getEscapedName(), varType.toSignature())));
/* 486:    */     }
/* 487:584 */     il.append(InstructionConstants.POP);
/* 488:585 */     il.append(InstructionConstants.ARETURN);
/* 489:    */     
/* 490:587 */     constructor.setMaxLocals();
/* 491:588 */     constructor.setMaxStack();
/* 492:589 */     sortRecordFactory.addMethod(constructor);
/* 493:590 */     makeNodeSortRecord.setMaxLocals();
/* 494:591 */     makeNodeSortRecord.setMaxStack();
/* 495:592 */     sortRecordFactory.addMethod(makeNodeSortRecord);
/* 496:593 */     xsltc.dumpClass(sortRecordFactory.getJavaClass());
/* 497:    */     
/* 498:595 */     return className;
/* 499:    */   }
/* 500:    */   
/* 501:    */   private static String compileSortRecord(Vector sortObjects, ClassGenerator classGen, MethodGenerator methodGen)
/* 502:    */   {
/* 503:604 */     XSLTC xsltc = ((Sort)sortObjects.firstElement()).getXSLTC();
/* 504:605 */     String className = xsltc.getHelperClassName();
/* 505:    */     
/* 506:    */ 
/* 507:608 */     NodeSortRecordGenerator sortRecord = new NodeSortRecordGenerator(className, "org.apache.xalan.xsltc.dom.NodeSortRecord", "sort$0.java", 49, new String[0], classGen.getStylesheet());
/* 508:    */     
/* 509:    */ 
/* 510:    */ 
/* 511:    */ 
/* 512:    */ 
/* 513:    */ 
/* 514:    */ 
/* 515:616 */     ConstantPoolGen cpg = sortRecord.getConstantPool();
/* 516:    */     
/* 517:    */ 
/* 518:619 */     int nsorts = sortObjects.size();
/* 519:620 */     ArrayList dups = new ArrayList();
/* 520:622 */     for (int j = 0; j < nsorts; j++)
/* 521:    */     {
/* 522:623 */       Sort sort = (Sort)sortObjects.get(j);
/* 523:    */       
/* 524:    */ 
/* 525:626 */       sort.setInnerClassName(className);
/* 526:    */       
/* 527:628 */       int length = sort._closureVars == null ? 0 : sort._closureVars.size();
/* 528:630 */       for (int i = 0; i < length; i++)
/* 529:    */       {
/* 530:631 */         VariableRefBase varRef = (VariableRefBase)sort._closureVars.get(i);
/* 531:634 */         if (!dups.contains(varRef))
/* 532:    */         {
/* 533:636 */           VariableBase var = varRef.getVariable();
/* 534:637 */           sortRecord.addField(new Field(1, cpg.addUtf8(var.getEscapedName()), cpg.addUtf8(var.getType().toSignature()), null, cpg.getConstantPool()));
/* 535:    */           
/* 536:    */ 
/* 537:    */ 
/* 538:641 */           dups.add(varRef);
/* 539:    */         }
/* 540:    */       }
/* 541:    */     }
/* 542:645 */     MethodGenerator init = compileInit(sortObjects, sortRecord, cpg, className);
/* 543:    */     
/* 544:647 */     MethodGenerator extract = compileExtract(sortObjects, sortRecord, cpg, className);
/* 545:    */     
/* 546:649 */     sortRecord.addMethod(init);
/* 547:650 */     sortRecord.addMethod(extract);
/* 548:    */     
/* 549:652 */     xsltc.dumpClass(sortRecord.getJavaClass());
/* 550:653 */     return className;
/* 551:    */   }
/* 552:    */   
/* 553:    */   private static MethodGenerator compileInit(Vector sortObjects, NodeSortRecordGenerator sortRecord, ConstantPoolGen cpg, String className)
/* 554:    */   {
/* 555:666 */     InstructionList il = new InstructionList();
/* 556:667 */     MethodGenerator init = new MethodGenerator(1, org.apache.bcel.generic.Type.VOID, null, null, "<init>", className, il, cpg);
/* 557:    */     
/* 558:    */ 
/* 559:    */ 
/* 560:    */ 
/* 561:    */ 
/* 562:    */ 
/* 563:674 */     il.append(InstructionConstants.ALOAD_0);
/* 564:675 */     il.append(new INVOKESPECIAL(cpg.addMethodref("org.apache.xalan.xsltc.dom.NodeSortRecord", "<init>", "()V")));
/* 565:    */     
/* 566:    */ 
/* 567:    */ 
/* 568:    */ 
/* 569:680 */     il.append(InstructionConstants.RETURN);
/* 570:    */     
/* 571:682 */     return init;
/* 572:    */   }
/* 573:    */   
/* 574:    */   private static MethodGenerator compileExtract(Vector sortObjects, NodeSortRecordGenerator sortRecord, ConstantPoolGen cpg, String className)
/* 575:    */   {
/* 576:693 */     InstructionList il = new InstructionList();
/* 577:    */     
/* 578:    */ 
/* 579:696 */     CompareGenerator extractMethod = new CompareGenerator(17, org.apache.bcel.generic.Type.STRING, new org.apache.bcel.generic.Type[] { Util.getJCRefType("Lorg/apache/xalan/xsltc/DOM;"), org.apache.bcel.generic.Type.INT, org.apache.bcel.generic.Type.INT, Util.getJCRefType("Lorg/apache/xalan/xsltc/runtime/AbstractTranslet;"), org.apache.bcel.generic.Type.INT }, new String[] { "dom", "current", "level", "translet", "last" }, "extractValueFromDOM", className, il, cpg);
/* 580:    */     
/* 581:    */ 
/* 582:    */ 
/* 583:    */ 
/* 584:    */ 
/* 585:    */ 
/* 586:    */ 
/* 587:    */ 
/* 588:    */ 
/* 589:    */ 
/* 590:    */ 
/* 591:    */ 
/* 592:    */ 
/* 593:    */ 
/* 594:    */ 
/* 595:    */ 
/* 596:    */ 
/* 597:    */ 
/* 598:715 */     int levels = sortObjects.size();
/* 599:716 */     int[] match = new int[levels];
/* 600:717 */     InstructionHandle[] target = new InstructionHandle[levels];
/* 601:718 */     InstructionHandle tblswitch = null;
/* 602:721 */     if (levels > 1)
/* 603:    */     {
/* 604:723 */       il.append(new ILOAD(extractMethod.getLocalIndex("level")));
/* 605:    */       
/* 606:725 */       tblswitch = il.append(new NOP());
/* 607:    */     }
/* 608:729 */     for (int level = 0; level < levels; level++)
/* 609:    */     {
/* 610:730 */       match[level] = level;
/* 611:731 */       Sort sort = (Sort)sortObjects.elementAt(level);
/* 612:732 */       target[level] = il.append(InstructionConstants.NOP);
/* 613:733 */       sort.translateSelect(sortRecord, extractMethod);
/* 614:734 */       il.append(InstructionConstants.ARETURN);
/* 615:    */     }
/* 616:738 */     if (levels > 1)
/* 617:    */     {
/* 618:740 */       InstructionHandle defaultTarget = il.append(new PUSH(cpg, ""));
/* 619:    */       
/* 620:742 */       il.insert(tblswitch, new TABLESWITCH(match, target, defaultTarget));
/* 621:743 */       il.append(InstructionConstants.ARETURN);
/* 622:    */     }
/* 623:746 */     return extractMethod;
/* 624:    */   }
/* 625:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.Sort
 * JD-Core Version:    0.7.0.1
 */