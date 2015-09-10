/*    1:     */ package net.sourceforge.htmlunit.corejs.classfile;
/*    2:     */ 
/*    3:     */ import java.io.IOException;
/*    4:     */ import java.io.InputStream;
/*    5:     */ import java.io.OutputStream;
/*    6:     */ import java.util.Arrays;
/*    7:     */ import net.sourceforge.htmlunit.corejs.javascript.ObjArray;
/*    8:     */ import net.sourceforge.htmlunit.corejs.javascript.UintMap;
/*    9:     */ 
/*   10:     */ public class ClassFileWriter
/*   11:     */ {
/*   12:     */   public static final short ACC_PUBLIC = 1;
/*   13:     */   public static final short ACC_PRIVATE = 2;
/*   14:     */   public static final short ACC_PROTECTED = 4;
/*   15:     */   public static final short ACC_STATIC = 8;
/*   16:     */   public static final short ACC_FINAL = 16;
/*   17:     */   public static final short ACC_SUPER = 32;
/*   18:     */   public static final short ACC_SYNCHRONIZED = 32;
/*   19:     */   public static final short ACC_VOLATILE = 64;
/*   20:     */   public static final short ACC_TRANSIENT = 128;
/*   21:     */   public static final short ACC_NATIVE = 256;
/*   22:     */   public static final short ACC_ABSTRACT = 1024;
/*   23:     */   
/*   24:     */   public static class ClassFileFormatException
/*   25:     */     extends RuntimeException
/*   26:     */   {
/*   27:     */     private static final long serialVersionUID = 1263998431033790599L;
/*   28:     */     
/*   29:     */     ClassFileFormatException(String message)
/*   30:     */     {
/*   31:  69 */       super();
/*   32:     */     }
/*   33:     */   }
/*   34:     */   
/*   35:     */   public ClassFileWriter(String className, String superClassName, String sourceFileName)
/*   36:     */   {
/*   37:  87 */     this.generatedClassName = className;
/*   38:  88 */     this.itsConstantPool = new ConstantPool(this);
/*   39:  89 */     this.itsThisClassIndex = this.itsConstantPool.addClass(className);
/*   40:  90 */     this.itsSuperClassIndex = this.itsConstantPool.addClass(superClassName);
/*   41:  91 */     if (sourceFileName != null) {
/*   42:  92 */       this.itsSourceFileNameIndex = this.itsConstantPool.addUtf8(sourceFileName);
/*   43:     */     }
/*   44:  96 */     this.itsFlags = 33;
/*   45:     */   }
/*   46:     */   
/*   47:     */   public final String getClassName()
/*   48:     */   {
/*   49: 101 */     return this.generatedClassName;
/*   50:     */   }
/*   51:     */   
/*   52:     */   public void addInterface(String interfaceName)
/*   53:     */   {
/*   54: 115 */     short interfaceIndex = this.itsConstantPool.addClass(interfaceName);
/*   55: 116 */     this.itsInterfaces.add(Short.valueOf(interfaceIndex));
/*   56:     */   }
/*   57:     */   
/*   58:     */   public void setFlags(short flags)
/*   59:     */   {
/*   60: 146 */     this.itsFlags = flags;
/*   61:     */   }
/*   62:     */   
/*   63:     */   static String getSlashedForm(String name)
/*   64:     */   {
/*   65: 151 */     return name.replace('.', '/');
/*   66:     */   }
/*   67:     */   
/*   68:     */   public static String classNameToSignature(String name)
/*   69:     */   {
/*   70: 161 */     int nameLength = name.length();
/*   71: 162 */     int colonPos = 1 + nameLength;
/*   72: 163 */     char[] buf = new char[colonPos + 1];
/*   73: 164 */     buf[0] = 'L';
/*   74: 165 */     buf[colonPos] = ';';
/*   75: 166 */     name.getChars(0, nameLength, buf, 1);
/*   76: 167 */     for (int i = 1; i != colonPos; i++) {
/*   77: 168 */       if (buf[i] == '.') {
/*   78: 169 */         buf[i] = '/';
/*   79:     */       }
/*   80:     */     }
/*   81: 172 */     return new String(buf, 0, colonPos + 1);
/*   82:     */   }
/*   83:     */   
/*   84:     */   public void addField(String fieldName, String type, short flags)
/*   85:     */   {
/*   86: 184 */     short fieldNameIndex = this.itsConstantPool.addUtf8(fieldName);
/*   87: 185 */     short typeIndex = this.itsConstantPool.addUtf8(type);
/*   88: 186 */     this.itsFields.add(new ClassFileField(fieldNameIndex, typeIndex, flags));
/*   89:     */   }
/*   90:     */   
/*   91:     */   public void addField(String fieldName, String type, short flags, int value)
/*   92:     */   {
/*   93: 201 */     short fieldNameIndex = this.itsConstantPool.addUtf8(fieldName);
/*   94: 202 */     short typeIndex = this.itsConstantPool.addUtf8(type);
/*   95: 203 */     ClassFileField field = new ClassFileField(fieldNameIndex, typeIndex, flags);
/*   96:     */     
/*   97: 205 */     field.setAttributes(this.itsConstantPool.addUtf8("ConstantValue"), (short)0, (short)0, this.itsConstantPool.addConstant(value));
/*   98:     */     
/*   99:     */ 
/*  100:     */ 
/*  101: 209 */     this.itsFields.add(field);
/*  102:     */   }
/*  103:     */   
/*  104:     */   public void addField(String fieldName, String type, short flags, long value)
/*  105:     */   {
/*  106: 224 */     short fieldNameIndex = this.itsConstantPool.addUtf8(fieldName);
/*  107: 225 */     short typeIndex = this.itsConstantPool.addUtf8(type);
/*  108: 226 */     ClassFileField field = new ClassFileField(fieldNameIndex, typeIndex, flags);
/*  109:     */     
/*  110: 228 */     field.setAttributes(this.itsConstantPool.addUtf8("ConstantValue"), (short)0, (short)2, this.itsConstantPool.addConstant(value));
/*  111:     */     
/*  112:     */ 
/*  113:     */ 
/*  114: 232 */     this.itsFields.add(field);
/*  115:     */   }
/*  116:     */   
/*  117:     */   public void addField(String fieldName, String type, short flags, double value)
/*  118:     */   {
/*  119: 247 */     short fieldNameIndex = this.itsConstantPool.addUtf8(fieldName);
/*  120: 248 */     short typeIndex = this.itsConstantPool.addUtf8(type);
/*  121: 249 */     ClassFileField field = new ClassFileField(fieldNameIndex, typeIndex, flags);
/*  122:     */     
/*  123: 251 */     field.setAttributes(this.itsConstantPool.addUtf8("ConstantValue"), (short)0, (short)2, this.itsConstantPool.addConstant(value));
/*  124:     */     
/*  125:     */ 
/*  126:     */ 
/*  127: 255 */     this.itsFields.add(field);
/*  128:     */   }
/*  129:     */   
/*  130:     */   public void addVariableDescriptor(String name, String type, int startPC, int register)
/*  131:     */   {
/*  132: 271 */     int nameIndex = this.itsConstantPool.addUtf8(name);
/*  133: 272 */     int descriptorIndex = this.itsConstantPool.addUtf8(type);
/*  134: 273 */     int[] chunk = { nameIndex, descriptorIndex, startPC, register };
/*  135: 274 */     if (this.itsVarDescriptors == null) {
/*  136: 275 */       this.itsVarDescriptors = new ObjArray();
/*  137:     */     }
/*  138: 277 */     this.itsVarDescriptors.add(chunk);
/*  139:     */   }
/*  140:     */   
/*  141:     */   public void startMethod(String methodName, String type, short flags)
/*  142:     */   {
/*  143: 292 */     short methodNameIndex = this.itsConstantPool.addUtf8(methodName);
/*  144: 293 */     short typeIndex = this.itsConstantPool.addUtf8(type);
/*  145: 294 */     this.itsCurrentMethod = new ClassFileMethod(methodName, methodNameIndex, type, typeIndex, flags);
/*  146:     */     
/*  147: 296 */     this.itsJumpFroms = new UintMap();
/*  148: 297 */     this.itsMethods.add(this.itsCurrentMethod);
/*  149: 298 */     addSuperBlockStart(0);
/*  150:     */   }
/*  151:     */   
/*  152:     */   public void stopMethod(short maxLocals)
/*  153:     */   {
/*  154: 311 */     if (this.itsCurrentMethod == null) {
/*  155: 312 */       throw new IllegalStateException("No method to stop");
/*  156:     */     }
/*  157: 314 */     fixLabelGotos();
/*  158:     */     
/*  159: 316 */     this.itsMaxLocals = maxLocals;
/*  160:     */     
/*  161: 318 */     StackMapTable stackMap = null;
/*  162: 319 */     if (GenerateStackMap)
/*  163:     */     {
/*  164: 320 */       finalizeSuperBlockStarts();
/*  165: 321 */       stackMap = new StackMapTable();
/*  166: 322 */       stackMap.generate();
/*  167:     */     }
/*  168: 325 */     int lineNumberTableLength = 0;
/*  169: 326 */     if (this.itsLineNumberTable != null) {
/*  170: 330 */       lineNumberTableLength = 8 + this.itsLineNumberTableTop * 4;
/*  171:     */     }
/*  172: 333 */     int variableTableLength = 0;
/*  173: 334 */     if (this.itsVarDescriptors != null) {
/*  174: 338 */       variableTableLength = 8 + this.itsVarDescriptors.size() * 10;
/*  175:     */     }
/*  176: 341 */     int stackMapTableLength = 0;
/*  177: 342 */     if (stackMap != null)
/*  178:     */     {
/*  179: 343 */       int stackMapWriteSize = stackMap.computeWriteSize();
/*  180: 344 */       if (stackMapWriteSize > 0) {
/*  181: 345 */         stackMapTableLength = 6 + stackMapWriteSize;
/*  182:     */       }
/*  183:     */     }
/*  184: 349 */     int attrLength = 14 + this.itsCodeBufferTop + 2 + this.itsExceptionTableTop * 8 + 2 + lineNumberTableLength + variableTableLength + stackMapTableLength;
/*  185: 362 */     if (attrLength > 65536) {
/*  186: 366 */       throw new ClassFileFormatException("generated bytecode for method exceeds 64K limit.");
/*  187:     */     }
/*  188: 369 */     byte[] codeAttribute = new byte[attrLength];
/*  189: 370 */     int index = 0;
/*  190: 371 */     int codeAttrIndex = this.itsConstantPool.addUtf8("Code");
/*  191: 372 */     index = putInt16(codeAttrIndex, codeAttribute, index);
/*  192: 373 */     attrLength -= 6;
/*  193: 374 */     index = putInt32(attrLength, codeAttribute, index);
/*  194: 375 */     index = putInt16(this.itsMaxStack, codeAttribute, index);
/*  195: 376 */     index = putInt16(this.itsMaxLocals, codeAttribute, index);
/*  196: 377 */     index = putInt32(this.itsCodeBufferTop, codeAttribute, index);
/*  197: 378 */     System.arraycopy(this.itsCodeBuffer, 0, codeAttribute, index, this.itsCodeBufferTop);
/*  198:     */     
/*  199: 380 */     index += this.itsCodeBufferTop;
/*  200: 382 */     if (this.itsExceptionTableTop > 0)
/*  201:     */     {
/*  202: 383 */       index = putInt16(this.itsExceptionTableTop, codeAttribute, index);
/*  203: 384 */       for (int i = 0; i < this.itsExceptionTableTop; i++)
/*  204:     */       {
/*  205: 385 */         ExceptionTableEntry ete = this.itsExceptionTable[i];
/*  206: 386 */         short startPC = (short)getLabelPC(ete.itsStartLabel);
/*  207: 387 */         short endPC = (short)getLabelPC(ete.itsEndLabel);
/*  208: 388 */         short handlerPC = (short)getLabelPC(ete.itsHandlerLabel);
/*  209: 389 */         short catchType = ete.itsCatchType;
/*  210: 390 */         if (startPC == -1) {
/*  211: 391 */           throw new IllegalStateException("start label not defined");
/*  212:     */         }
/*  213: 392 */         if (endPC == -1) {
/*  214: 393 */           throw new IllegalStateException("end label not defined");
/*  215:     */         }
/*  216: 394 */         if (handlerPC == -1) {
/*  217: 395 */           throw new IllegalStateException("handler label not defined");
/*  218:     */         }
/*  219: 398 */         index = putInt16(startPC, codeAttribute, index);
/*  220: 399 */         index = putInt16(endPC, codeAttribute, index);
/*  221: 400 */         index = putInt16(handlerPC, codeAttribute, index);
/*  222: 401 */         index = putInt16(catchType, codeAttribute, index);
/*  223:     */       }
/*  224:     */     }
/*  225:     */     else
/*  226:     */     {
/*  227: 406 */       index = putInt16(0, codeAttribute, index);
/*  228:     */     }
/*  229: 409 */     int attributeCount = 0;
/*  230: 410 */     if (this.itsLineNumberTable != null) {
/*  231: 411 */       attributeCount++;
/*  232:     */     }
/*  233: 412 */     if (this.itsVarDescriptors != null) {
/*  234: 413 */       attributeCount++;
/*  235:     */     }
/*  236: 414 */     if (stackMapTableLength > 0) {
/*  237: 415 */       attributeCount++;
/*  238:     */     }
/*  239: 417 */     index = putInt16(attributeCount, codeAttribute, index);
/*  240: 419 */     if (this.itsLineNumberTable != null)
/*  241:     */     {
/*  242: 420 */       int lineNumberTableAttrIndex = this.itsConstantPool.addUtf8("LineNumberTable");
/*  243:     */       
/*  244: 422 */       index = putInt16(lineNumberTableAttrIndex, codeAttribute, index);
/*  245: 423 */       int tableAttrLength = 2 + this.itsLineNumberTableTop * 4;
/*  246: 424 */       index = putInt32(tableAttrLength, codeAttribute, index);
/*  247: 425 */       index = putInt16(this.itsLineNumberTableTop, codeAttribute, index);
/*  248: 426 */       for (int i = 0; i < this.itsLineNumberTableTop; i++) {
/*  249: 427 */         index = putInt32(this.itsLineNumberTable[i], codeAttribute, index);
/*  250:     */       }
/*  251:     */     }
/*  252: 431 */     if (this.itsVarDescriptors != null)
/*  253:     */     {
/*  254: 432 */       int variableTableAttrIndex = this.itsConstantPool.addUtf8("LocalVariableTable");
/*  255:     */       
/*  256: 434 */       index = putInt16(variableTableAttrIndex, codeAttribute, index);
/*  257: 435 */       int varCount = this.itsVarDescriptors.size();
/*  258: 436 */       int tableAttrLength = 2 + varCount * 10;
/*  259: 437 */       index = putInt32(tableAttrLength, codeAttribute, index);
/*  260: 438 */       index = putInt16(varCount, codeAttribute, index);
/*  261: 439 */       for (int i = 0; i < varCount; i++)
/*  262:     */       {
/*  263: 440 */         int[] chunk = (int[])this.itsVarDescriptors.get(i);
/*  264: 441 */         int nameIndex = chunk[0];
/*  265: 442 */         int descriptorIndex = chunk[1];
/*  266: 443 */         int startPC = chunk[2];
/*  267: 444 */         int register = chunk[3];
/*  268: 445 */         int length = this.itsCodeBufferTop - startPC;
/*  269:     */         
/*  270: 447 */         index = putInt16(startPC, codeAttribute, index);
/*  271: 448 */         index = putInt16(length, codeAttribute, index);
/*  272: 449 */         index = putInt16(nameIndex, codeAttribute, index);
/*  273: 450 */         index = putInt16(descriptorIndex, codeAttribute, index);
/*  274: 451 */         index = putInt16(register, codeAttribute, index);
/*  275:     */       }
/*  276:     */     }
/*  277: 455 */     if (stackMapTableLength > 0)
/*  278:     */     {
/*  279: 456 */       int stackMapTableAttrIndex = this.itsConstantPool.addUtf8("StackMapTable");
/*  280:     */       
/*  281: 458 */       int start = index;
/*  282: 459 */       index = putInt16(stackMapTableAttrIndex, codeAttribute, index);
/*  283: 460 */       index = stackMap.write(codeAttribute, index);
/*  284:     */     }
/*  285: 463 */     this.itsCurrentMethod.setCodeAttribute(codeAttribute);
/*  286:     */     
/*  287: 465 */     this.itsExceptionTable = null;
/*  288: 466 */     this.itsExceptionTableTop = 0;
/*  289: 467 */     this.itsLineNumberTableTop = 0;
/*  290: 468 */     this.itsCodeBufferTop = 0;
/*  291: 469 */     this.itsCurrentMethod = null;
/*  292: 470 */     this.itsMaxStack = 0;
/*  293: 471 */     this.itsStackTop = 0;
/*  294: 472 */     this.itsLabelTableTop = 0;
/*  295: 473 */     this.itsFixupTableTop = 0;
/*  296: 474 */     this.itsVarDescriptors = null;
/*  297: 475 */     this.itsSuperBlockStarts = null;
/*  298: 476 */     this.itsSuperBlockStartsTop = 0;
/*  299: 477 */     this.itsJumpFroms = null;
/*  300:     */   }
/*  301:     */   
/*  302:     */   public void add(int theOpCode)
/*  303:     */   {
/*  304: 486 */     if (opcodeCount(theOpCode) != 0) {
/*  305: 487 */       throw new IllegalArgumentException("Unexpected operands");
/*  306:     */     }
/*  307: 488 */     int newStack = this.itsStackTop + stackChange(theOpCode);
/*  308: 489 */     if ((newStack < 0) || (32767 < newStack)) {
/*  309: 489 */       badStack(newStack);
/*  310:     */     }
/*  311: 492 */     addToCodeBuffer(theOpCode);
/*  312: 493 */     this.itsStackTop = ((short)newStack);
/*  313: 494 */     if (newStack > this.itsMaxStack) {
/*  314: 494 */       this.itsMaxStack = ((short)newStack);
/*  315:     */     }
/*  316: 499 */     if (theOpCode == 191) {
/*  317: 500 */       addSuperBlockStart(this.itsCodeBufferTop);
/*  318:     */     }
/*  319:     */   }
/*  320:     */   
/*  321:     */   public void add(int theOpCode, int theOperand)
/*  322:     */   {
/*  323: 515 */     int newStack = this.itsStackTop + stackChange(theOpCode);
/*  324: 516 */     if ((newStack < 0) || (32767 < newStack)) {
/*  325: 516 */       badStack(newStack);
/*  326:     */     }
/*  327: 518 */     switch (theOpCode)
/*  328:     */     {
/*  329:     */     case 167: 
/*  330: 523 */       addSuperBlockStart(this.itsCodeBufferTop + 3);
/*  331:     */     case 153: 
/*  332:     */     case 154: 
/*  333:     */     case 155: 
/*  334:     */     case 156: 
/*  335:     */     case 157: 
/*  336:     */     case 158: 
/*  337:     */     case 159: 
/*  338:     */     case 160: 
/*  339:     */     case 161: 
/*  340:     */     case 162: 
/*  341:     */     case 163: 
/*  342:     */     case 164: 
/*  343:     */     case 165: 
/*  344:     */     case 166: 
/*  345:     */     case 168: 
/*  346:     */     case 198: 
/*  347:     */     case 199: 
/*  348: 542 */       if (((theOperand & 0x80000000) != -2147483648) && (
/*  349: 543 */         (theOperand < 0) || (theOperand > 65535))) {
/*  350: 544 */         throw new IllegalArgumentException("Bad label for branch");
/*  351:     */       }
/*  352: 547 */       int branchPC = this.itsCodeBufferTop;
/*  353: 548 */       addToCodeBuffer(theOpCode);
/*  354: 549 */       if ((theOperand & 0x80000000) != -2147483648)
/*  355:     */       {
/*  356: 551 */         addToCodeInt16(theOperand);
/*  357: 552 */         int target = theOperand + branchPC;
/*  358: 553 */         addSuperBlockStart(target);
/*  359: 554 */         this.itsJumpFroms.put(target, branchPC);
/*  360:     */       }
/*  361:     */       else
/*  362:     */       {
/*  363: 557 */         int targetPC = getLabelPC(theOperand);
/*  364: 564 */         if (targetPC != -1)
/*  365:     */         {
/*  366: 565 */           int offset = targetPC - branchPC;
/*  367: 566 */           addToCodeInt16(offset);
/*  368: 567 */           addSuperBlockStart(targetPC);
/*  369: 568 */           this.itsJumpFroms.put(targetPC, branchPC);
/*  370:     */         }
/*  371:     */         else
/*  372:     */         {
/*  373: 571 */           addLabelFixup(theOperand, branchPC + 1);
/*  374: 572 */           addToCodeInt16(0);
/*  375:     */         }
/*  376:     */       }
/*  377: 576 */       break;
/*  378:     */     case 16: 
/*  379: 579 */       if ((byte)theOperand != theOperand) {
/*  380: 580 */         throw new IllegalArgumentException("out of range byte");
/*  381:     */       }
/*  382: 581 */       addToCodeBuffer(theOpCode);
/*  383: 582 */       addToCodeBuffer((byte)theOperand);
/*  384: 583 */       break;
/*  385:     */     case 17: 
/*  386: 586 */       if ((short)theOperand != theOperand) {
/*  387: 587 */         throw new IllegalArgumentException("out of range short");
/*  388:     */       }
/*  389: 588 */       addToCodeBuffer(theOpCode);
/*  390: 589 */       addToCodeInt16(theOperand);
/*  391: 590 */       break;
/*  392:     */     case 188: 
/*  393: 593 */       if ((0 > theOperand) || (theOperand >= 256)) {
/*  394: 594 */         throw new IllegalArgumentException("out of range index");
/*  395:     */       }
/*  396: 595 */       addToCodeBuffer(theOpCode);
/*  397: 596 */       addToCodeBuffer(theOperand);
/*  398: 597 */       break;
/*  399:     */     case 180: 
/*  400:     */     case 181: 
/*  401: 601 */       if ((0 > theOperand) || (theOperand >= 65536)) {
/*  402: 602 */         throw new IllegalArgumentException("out of range field");
/*  403:     */       }
/*  404: 603 */       addToCodeBuffer(theOpCode);
/*  405: 604 */       addToCodeInt16(theOperand);
/*  406: 605 */       break;
/*  407:     */     case 18: 
/*  408:     */     case 19: 
/*  409:     */     case 20: 
/*  410: 610 */       if ((0 > theOperand) || (theOperand >= 65536)) {
/*  411: 611 */         throw new IllegalArgumentException("out of range index");
/*  412:     */       }
/*  413: 612 */       if ((theOperand >= 256) || (theOpCode == 19) || (theOpCode == 20))
/*  414:     */       {
/*  415: 616 */         if (theOpCode == 18) {
/*  416: 617 */           addToCodeBuffer(19);
/*  417:     */         } else {
/*  418: 619 */           addToCodeBuffer(theOpCode);
/*  419:     */         }
/*  420: 621 */         addToCodeInt16(theOperand);
/*  421:     */       }
/*  422:     */       else
/*  423:     */       {
/*  424: 623 */         addToCodeBuffer(theOpCode);
/*  425: 624 */         addToCodeBuffer(theOperand);
/*  426:     */       }
/*  427: 626 */       break;
/*  428:     */     case 21: 
/*  429:     */     case 22: 
/*  430:     */     case 23: 
/*  431:     */     case 24: 
/*  432:     */     case 25: 
/*  433:     */     case 54: 
/*  434:     */     case 55: 
/*  435:     */     case 56: 
/*  436:     */     case 57: 
/*  437:     */     case 58: 
/*  438:     */     case 169: 
/*  439: 639 */       if ((0 > theOperand) || (theOperand >= 65536)) {
/*  440: 640 */         throw new ClassFileFormatException("out of range variable");
/*  441:     */       }
/*  442: 641 */       if (theOperand >= 256)
/*  443:     */       {
/*  444: 642 */         addToCodeBuffer(196);
/*  445: 643 */         addToCodeBuffer(theOpCode);
/*  446: 644 */         addToCodeInt16(theOperand);
/*  447:     */       }
/*  448:     */       else
/*  449:     */       {
/*  450: 647 */         addToCodeBuffer(theOpCode);
/*  451: 648 */         addToCodeBuffer(theOperand);
/*  452:     */       }
/*  453: 650 */       break;
/*  454:     */     default: 
/*  455: 653 */       throw new IllegalArgumentException("Unexpected opcode for 1 operand");
/*  456:     */     }
/*  457: 657 */     this.itsStackTop = ((short)newStack);
/*  458: 658 */     if (newStack > this.itsMaxStack) {
/*  459: 658 */       this.itsMaxStack = ((short)newStack);
/*  460:     */     }
/*  461:     */   }
/*  462:     */   
/*  463:     */   public void addLoadConstant(int k)
/*  464:     */   {
/*  465: 671 */     switch (k)
/*  466:     */     {
/*  467:     */     case 0: 
/*  468: 672 */       add(3); break;
/*  469:     */     case 1: 
/*  470: 673 */       add(4); break;
/*  471:     */     case 2: 
/*  472: 674 */       add(5); break;
/*  473:     */     case 3: 
/*  474: 675 */       add(6); break;
/*  475:     */     case 4: 
/*  476: 676 */       add(7); break;
/*  477:     */     case 5: 
/*  478: 677 */       add(8); break;
/*  479:     */     default: 
/*  480: 679 */       add(18, this.itsConstantPool.addConstant(k));
/*  481:     */     }
/*  482:     */   }
/*  483:     */   
/*  484:     */   public void addLoadConstant(long k)
/*  485:     */   {
/*  486: 690 */     add(20, this.itsConstantPool.addConstant(k));
/*  487:     */   }
/*  488:     */   
/*  489:     */   public void addLoadConstant(float k)
/*  490:     */   {
/*  491: 699 */     add(18, this.itsConstantPool.addConstant(k));
/*  492:     */   }
/*  493:     */   
/*  494:     */   public void addLoadConstant(double k)
/*  495:     */   {
/*  496: 708 */     add(20, this.itsConstantPool.addConstant(k));
/*  497:     */   }
/*  498:     */   
/*  499:     */   public void addLoadConstant(String k)
/*  500:     */   {
/*  501: 717 */     add(18, this.itsConstantPool.addConstant(k));
/*  502:     */   }
/*  503:     */   
/*  504:     */   public void add(int theOpCode, int theOperand1, int theOperand2)
/*  505:     */   {
/*  506: 733 */     int newStack = this.itsStackTop + stackChange(theOpCode);
/*  507: 734 */     if ((newStack < 0) || (32767 < newStack)) {
/*  508: 734 */       badStack(newStack);
/*  509:     */     }
/*  510: 736 */     if (theOpCode == 132)
/*  511:     */     {
/*  512: 737 */       if ((0 > theOperand1) || (theOperand1 >= 65536)) {
/*  513: 738 */         throw new ClassFileFormatException("out of range variable");
/*  514:     */       }
/*  515: 739 */       if ((0 > theOperand2) || (theOperand2 >= 65536)) {
/*  516: 740 */         throw new ClassFileFormatException("out of range increment");
/*  517:     */       }
/*  518: 742 */       if ((theOperand1 > 255) || (theOperand2 < -128) || (theOperand2 > 127))
/*  519:     */       {
/*  520: 743 */         addToCodeBuffer(196);
/*  521: 744 */         addToCodeBuffer(132);
/*  522: 745 */         addToCodeInt16(theOperand1);
/*  523: 746 */         addToCodeInt16(theOperand2);
/*  524:     */       }
/*  525:     */       else
/*  526:     */       {
/*  527: 749 */         addToCodeBuffer(132);
/*  528: 750 */         addToCodeBuffer(theOperand1);
/*  529: 751 */         addToCodeBuffer(theOperand2);
/*  530:     */       }
/*  531:     */     }
/*  532: 754 */     else if (theOpCode == 197)
/*  533:     */     {
/*  534: 755 */       if ((0 > theOperand1) || (theOperand1 >= 65536)) {
/*  535: 756 */         throw new IllegalArgumentException("out of range index");
/*  536:     */       }
/*  537: 757 */       if ((0 > theOperand2) || (theOperand2 >= 256)) {
/*  538: 758 */         throw new IllegalArgumentException("out of range dimensions");
/*  539:     */       }
/*  540: 760 */       addToCodeBuffer(197);
/*  541: 761 */       addToCodeInt16(theOperand1);
/*  542: 762 */       addToCodeBuffer(theOperand2);
/*  543:     */     }
/*  544:     */     else
/*  545:     */     {
/*  546: 765 */       throw new IllegalArgumentException("Unexpected opcode for 2 operands");
/*  547:     */     }
/*  548: 768 */     this.itsStackTop = ((short)newStack);
/*  549: 769 */     if (newStack > this.itsMaxStack) {
/*  550: 769 */       this.itsMaxStack = ((short)newStack);
/*  551:     */     }
/*  552:     */   }
/*  553:     */   
/*  554:     */   public void add(int theOpCode, String className)
/*  555:     */   {
/*  556: 782 */     int newStack = this.itsStackTop + stackChange(theOpCode);
/*  557: 783 */     if ((newStack < 0) || (32767 < newStack)) {
/*  558: 783 */       badStack(newStack);
/*  559:     */     }
/*  560: 784 */     switch (theOpCode)
/*  561:     */     {
/*  562:     */     case 187: 
/*  563:     */     case 189: 
/*  564:     */     case 192: 
/*  565:     */     case 193: 
/*  566: 789 */       short classIndex = this.itsConstantPool.addClass(className);
/*  567: 790 */       addToCodeBuffer(theOpCode);
/*  568: 791 */       addToCodeInt16(classIndex);
/*  569:     */       
/*  570: 793 */       break;
/*  571:     */     case 188: 
/*  572:     */     case 190: 
/*  573:     */     case 191: 
/*  574:     */     default: 
/*  575: 796 */       throw new IllegalArgumentException("bad opcode for class reference");
/*  576:     */     }
/*  577: 799 */     this.itsStackTop = ((short)newStack);
/*  578: 800 */     if (newStack > this.itsMaxStack) {
/*  579: 800 */       this.itsMaxStack = ((short)newStack);
/*  580:     */     }
/*  581:     */   }
/*  582:     */   
/*  583:     */   public void add(int theOpCode, String className, String fieldName, String fieldType)
/*  584:     */   {
/*  585: 815 */     int newStack = this.itsStackTop + stackChange(theOpCode);
/*  586: 816 */     char fieldTypeChar = fieldType.charAt(0);
/*  587: 817 */     int fieldSize = (fieldTypeChar == 'J') || (fieldTypeChar == 'D') ? 2 : 1;
/*  588: 819 */     switch (theOpCode)
/*  589:     */     {
/*  590:     */     case 178: 
/*  591:     */     case 180: 
/*  592: 822 */       newStack += fieldSize;
/*  593: 823 */       break;
/*  594:     */     case 179: 
/*  595:     */     case 181: 
/*  596: 826 */       newStack -= fieldSize;
/*  597: 827 */       break;
/*  598:     */     default: 
/*  599: 829 */       throw new IllegalArgumentException("bad opcode for field reference");
/*  600:     */     }
/*  601: 832 */     if ((newStack < 0) || (32767 < newStack)) {
/*  602: 832 */       badStack(newStack);
/*  603:     */     }
/*  604: 833 */     short fieldRefIndex = this.itsConstantPool.addFieldRef(className, fieldName, fieldType);
/*  605:     */     
/*  606: 835 */     addToCodeBuffer(theOpCode);
/*  607: 836 */     addToCodeInt16(fieldRefIndex);
/*  608:     */     
/*  609: 838 */     this.itsStackTop = ((short)newStack);
/*  610: 839 */     if (newStack > this.itsMaxStack) {
/*  611: 839 */       this.itsMaxStack = ((short)newStack);
/*  612:     */     }
/*  613:     */   }
/*  614:     */   
/*  615:     */   public void addInvoke(int theOpCode, String className, String methodName, String methodType)
/*  616:     */   {
/*  617: 854 */     int parameterInfo = sizeOfParameters(methodType);
/*  618: 855 */     int parameterCount = parameterInfo >>> 16;
/*  619: 856 */     int stackDiff = (short)parameterInfo;
/*  620:     */     
/*  621: 858 */     int newStack = this.itsStackTop + stackDiff;
/*  622: 859 */     newStack += stackChange(theOpCode);
/*  623: 860 */     if ((newStack < 0) || (32767 < newStack)) {
/*  624: 860 */       badStack(newStack);
/*  625:     */     }
/*  626: 862 */     switch (theOpCode)
/*  627:     */     {
/*  628:     */     case 182: 
/*  629:     */     case 183: 
/*  630:     */     case 184: 
/*  631:     */     case 185: 
/*  632: 867 */       addToCodeBuffer(theOpCode);
/*  633: 868 */       if (theOpCode == 185)
/*  634:     */       {
/*  635: 869 */         short ifMethodRefIndex = this.itsConstantPool.addInterfaceMethodRef(className, methodName, methodType);
/*  636:     */         
/*  637:     */ 
/*  638:     */ 
/*  639: 873 */         addToCodeInt16(ifMethodRefIndex);
/*  640: 874 */         addToCodeBuffer(parameterCount + 1);
/*  641: 875 */         addToCodeBuffer(0);
/*  642:     */       }
/*  643:     */       else
/*  644:     */       {
/*  645: 878 */         short methodRefIndex = this.itsConstantPool.addMethodRef(className, methodName, methodType);
/*  646:     */         
/*  647:     */ 
/*  648: 881 */         addToCodeInt16(methodRefIndex);
/*  649:     */       }
/*  650: 884 */       break;
/*  651:     */     default: 
/*  652: 887 */       throw new IllegalArgumentException("bad opcode for method reference");
/*  653:     */     }
/*  654: 890 */     this.itsStackTop = ((short)newStack);
/*  655: 891 */     if (newStack > this.itsMaxStack) {
/*  656: 891 */       this.itsMaxStack = ((short)newStack);
/*  657:     */     }
/*  658:     */   }
/*  659:     */   
/*  660:     */   public void addPush(int k)
/*  661:     */   {
/*  662: 905 */     if ((byte)k == k)
/*  663:     */     {
/*  664: 906 */       if (k == -1) {
/*  665: 907 */         add(2);
/*  666: 908 */       } else if ((0 <= k) && (k <= 5)) {
/*  667: 909 */         add((byte)(3 + k));
/*  668:     */       } else {
/*  669: 911 */         add(16, (byte)k);
/*  670:     */       }
/*  671:     */     }
/*  672: 913 */     else if ((short)k == k) {
/*  673: 914 */       add(17, (short)k);
/*  674:     */     } else {
/*  675: 916 */       addLoadConstant(k);
/*  676:     */     }
/*  677:     */   }
/*  678:     */   
/*  679:     */   public void addPush(boolean k)
/*  680:     */   {
/*  681: 922 */     add(k ? 4 : 3);
/*  682:     */   }
/*  683:     */   
/*  684:     */   public void addPush(long k)
/*  685:     */   {
/*  686: 932 */     int ik = (int)k;
/*  687: 933 */     if (ik == k)
/*  688:     */     {
/*  689: 934 */       addPush(ik);
/*  690: 935 */       add(133);
/*  691:     */     }
/*  692:     */     else
/*  693:     */     {
/*  694: 937 */       addLoadConstant(k);
/*  695:     */     }
/*  696:     */   }
/*  697:     */   
/*  698:     */   public void addPush(double k)
/*  699:     */   {
/*  700: 948 */     if (k == 0.0D)
/*  701:     */     {
/*  702: 950 */       add(14);
/*  703: 951 */       if (1.0D / k < 0.0D) {
/*  704: 953 */         add(119);
/*  705:     */       }
/*  706:     */     }
/*  707: 955 */     else if ((k == 1.0D) || (k == -1.0D))
/*  708:     */     {
/*  709: 956 */       add(15);
/*  710: 957 */       if (k < 0.0D) {
/*  711: 958 */         add(119);
/*  712:     */       }
/*  713:     */     }
/*  714:     */     else
/*  715:     */     {
/*  716: 961 */       addLoadConstant(k);
/*  717:     */     }
/*  718:     */   }
/*  719:     */   
/*  720:     */   public void addPush(String k)
/*  721:     */   {
/*  722: 972 */     int length = k.length();
/*  723: 973 */     int limit = this.itsConstantPool.getUtfEncodingLimit(k, 0, length);
/*  724: 974 */     if (limit == length)
/*  725:     */     {
/*  726: 975 */       addLoadConstant(k);
/*  727: 976 */       return;
/*  728:     */     }
/*  729: 984 */     String SB = "java/lang/StringBuffer";
/*  730: 985 */     add(187, "java/lang/StringBuffer");
/*  731: 986 */     add(89);
/*  732: 987 */     addPush(length);
/*  733: 988 */     addInvoke(183, "java/lang/StringBuffer", "<init>", "(I)V");
/*  734: 989 */     int cursor = 0;
/*  735:     */     for (;;)
/*  736:     */     {
/*  737: 991 */       add(89);
/*  738: 992 */       String s = k.substring(cursor, limit);
/*  739: 993 */       addLoadConstant(s);
/*  740: 994 */       addInvoke(182, "java/lang/StringBuffer", "append", "(Ljava/lang/String;)Ljava/lang/StringBuffer;");
/*  741:     */       
/*  742: 996 */       add(87);
/*  743: 997 */       if (limit == length) {
/*  744:     */         break;
/*  745:     */       }
/*  746:1000 */       cursor = limit;
/*  747:1001 */       limit = this.itsConstantPool.getUtfEncodingLimit(k, limit, length);
/*  748:     */     }
/*  749:1003 */     addInvoke(182, "java/lang/StringBuffer", "toString", "()Ljava/lang/String;");
/*  750:     */   }
/*  751:     */   
/*  752:     */   public boolean isUnderStringSizeLimit(String k)
/*  753:     */   {
/*  754:1015 */     return this.itsConstantPool.isUnderUtfEncodingLimit(k);
/*  755:     */   }
/*  756:     */   
/*  757:     */   public void addIStore(int local)
/*  758:     */   {
/*  759:1025 */     xop(59, 54, local);
/*  760:     */   }
/*  761:     */   
/*  762:     */   public void addLStore(int local)
/*  763:     */   {
/*  764:1035 */     xop(63, 55, local);
/*  765:     */   }
/*  766:     */   
/*  767:     */   public void addFStore(int local)
/*  768:     */   {
/*  769:1045 */     xop(67, 56, local);
/*  770:     */   }
/*  771:     */   
/*  772:     */   public void addDStore(int local)
/*  773:     */   {
/*  774:1055 */     xop(71, 57, local);
/*  775:     */   }
/*  776:     */   
/*  777:     */   public void addAStore(int local)
/*  778:     */   {
/*  779:1065 */     xop(75, 58, local);
/*  780:     */   }
/*  781:     */   
/*  782:     */   public void addILoad(int local)
/*  783:     */   {
/*  784:1075 */     xop(26, 21, local);
/*  785:     */   }
/*  786:     */   
/*  787:     */   public void addLLoad(int local)
/*  788:     */   {
/*  789:1085 */     xop(30, 22, local);
/*  790:     */   }
/*  791:     */   
/*  792:     */   public void addFLoad(int local)
/*  793:     */   {
/*  794:1095 */     xop(34, 23, local);
/*  795:     */   }
/*  796:     */   
/*  797:     */   public void addDLoad(int local)
/*  798:     */   {
/*  799:1105 */     xop(38, 24, local);
/*  800:     */   }
/*  801:     */   
/*  802:     */   public void addALoad(int local)
/*  803:     */   {
/*  804:1115 */     xop(42, 25, local);
/*  805:     */   }
/*  806:     */   
/*  807:     */   public void addLoadThis()
/*  808:     */   {
/*  809:1123 */     add(42);
/*  810:     */   }
/*  811:     */   
/*  812:     */   private void xop(int shortOp, int op, int local)
/*  813:     */   {
/*  814:1128 */     switch (local)
/*  815:     */     {
/*  816:     */     case 0: 
/*  817:1130 */       add(shortOp);
/*  818:1131 */       break;
/*  819:     */     case 1: 
/*  820:1133 */       add(shortOp + 1);
/*  821:1134 */       break;
/*  822:     */     case 2: 
/*  823:1136 */       add(shortOp + 2);
/*  824:1137 */       break;
/*  825:     */     case 3: 
/*  826:1139 */       add(shortOp + 3);
/*  827:1140 */       break;
/*  828:     */     default: 
/*  829:1142 */       add(op, local);
/*  830:     */     }
/*  831:     */   }
/*  832:     */   
/*  833:     */   public int addTableSwitch(int low, int high)
/*  834:     */   {
/*  835:1152 */     if (low > high) {
/*  836:1153 */       throw new ClassFileFormatException("Bad bounds: " + low + ' ' + high);
/*  837:     */     }
/*  838:1155 */     int newStack = this.itsStackTop + stackChange(170);
/*  839:1156 */     if ((newStack < 0) || (32767 < newStack)) {
/*  840:1156 */       badStack(newStack);
/*  841:     */     }
/*  842:1158 */     int entryCount = high - low + 1;
/*  843:1159 */     int padSize = 0x3 & (this.itsCodeBufferTop ^ 0xFFFFFFFF);
/*  844:     */     
/*  845:1161 */     int N = addReservedCodeSpace(1 + padSize + 4 * (3 + entryCount));
/*  846:1162 */     int switchStart = N;
/*  847:1163 */     this.itsCodeBuffer[(N++)] = -86;
/*  848:1164 */     while (padSize != 0)
/*  849:     */     {
/*  850:1165 */       this.itsCodeBuffer[(N++)] = 0;
/*  851:1166 */       padSize--;
/*  852:     */     }
/*  853:1168 */     N += 4;
/*  854:1169 */     N = putInt32(low, this.itsCodeBuffer, N);
/*  855:1170 */     putInt32(high, this.itsCodeBuffer, N);
/*  856:     */     
/*  857:1172 */     this.itsStackTop = ((short)newStack);
/*  858:1173 */     if (newStack > this.itsMaxStack) {
/*  859:1173 */       this.itsMaxStack = ((short)newStack);
/*  860:     */     }
/*  861:1179 */     return switchStart;
/*  862:     */   }
/*  863:     */   
/*  864:     */   public final void markTableSwitchDefault(int switchStart)
/*  865:     */   {
/*  866:1184 */     addSuperBlockStart(this.itsCodeBufferTop);
/*  867:1185 */     this.itsJumpFroms.put(this.itsCodeBufferTop, switchStart);
/*  868:1186 */     setTableSwitchJump(switchStart, -1, this.itsCodeBufferTop);
/*  869:     */   }
/*  870:     */   
/*  871:     */   public final void markTableSwitchCase(int switchStart, int caseIndex)
/*  872:     */   {
/*  873:1191 */     addSuperBlockStart(this.itsCodeBufferTop);
/*  874:1192 */     this.itsJumpFroms.put(this.itsCodeBufferTop, switchStart);
/*  875:1193 */     setTableSwitchJump(switchStart, caseIndex, this.itsCodeBufferTop);
/*  876:     */   }
/*  877:     */   
/*  878:     */   public final void markTableSwitchCase(int switchStart, int caseIndex, int stackTop)
/*  879:     */   {
/*  880:1199 */     if ((0 > stackTop) || (stackTop > this.itsMaxStack)) {
/*  881:1200 */       throw new IllegalArgumentException("Bad stack index: " + stackTop);
/*  882:     */     }
/*  883:1201 */     this.itsStackTop = ((short)stackTop);
/*  884:1202 */     addSuperBlockStart(this.itsCodeBufferTop);
/*  885:1203 */     this.itsJumpFroms.put(this.itsCodeBufferTop, switchStart);
/*  886:1204 */     setTableSwitchJump(switchStart, caseIndex, this.itsCodeBufferTop);
/*  887:     */   }
/*  888:     */   
/*  889:     */   public void setTableSwitchJump(int switchStart, int caseIndex, int jumpTarget)
/*  890:     */   {
/*  891:1214 */     if ((0 > jumpTarget) || (jumpTarget > this.itsCodeBufferTop)) {
/*  892:1215 */       throw new IllegalArgumentException("Bad jump target: " + jumpTarget);
/*  893:     */     }
/*  894:1216 */     if (caseIndex < -1) {
/*  895:1217 */       throw new IllegalArgumentException("Bad case index: " + caseIndex);
/*  896:     */     }
/*  897:1219 */     int padSize = 0x3 & (switchStart ^ 0xFFFFFFFF);
/*  898:     */     int caseOffset;
/*  899:     */     int caseOffset;
/*  900:1221 */     if (caseIndex < 0) {
/*  901:1223 */       caseOffset = switchStart + 1 + padSize;
/*  902:     */     } else {
/*  903:1225 */       caseOffset = switchStart + 1 + padSize + 4 * (3 + caseIndex);
/*  904:     */     }
/*  905:1227 */     if ((0 > switchStart) || (switchStart > this.itsCodeBufferTop - 16 - padSize - 1)) {
/*  906:1230 */       throw new IllegalArgumentException(switchStart + " is outside a possible range of tableswitch" + " in already generated code");
/*  907:     */     }
/*  908:1234 */     if ((0xFF & this.itsCodeBuffer[switchStart]) != 170) {
/*  909:1235 */       throw new IllegalArgumentException(switchStart + " is not offset of tableswitch statement");
/*  910:     */     }
/*  911:1238 */     if ((0 > caseOffset) || (caseOffset + 4 > this.itsCodeBufferTop)) {
/*  912:1241 */       throw new ClassFileFormatException("Too big case index: " + caseIndex);
/*  913:     */     }
/*  914:1245 */     putInt32(jumpTarget - switchStart, this.itsCodeBuffer, caseOffset);
/*  915:     */   }
/*  916:     */   
/*  917:     */   public int acquireLabel()
/*  918:     */   {
/*  919:1250 */     int top = this.itsLabelTableTop;
/*  920:1251 */     if ((this.itsLabelTable == null) || (top == this.itsLabelTable.length)) {
/*  921:1252 */       if (this.itsLabelTable == null)
/*  922:     */       {
/*  923:1253 */         this.itsLabelTable = new int[32];
/*  924:     */       }
/*  925:     */       else
/*  926:     */       {
/*  927:1255 */         int[] tmp = new int[this.itsLabelTable.length * 2];
/*  928:1256 */         System.arraycopy(this.itsLabelTable, 0, tmp, 0, top);
/*  929:1257 */         this.itsLabelTable = tmp;
/*  930:     */       }
/*  931:     */     }
/*  932:1260 */     this.itsLabelTableTop = (top + 1);
/*  933:1261 */     this.itsLabelTable[top] = -1;
/*  934:1262 */     return top | 0x80000000;
/*  935:     */   }
/*  936:     */   
/*  937:     */   public void markLabel(int label)
/*  938:     */   {
/*  939:1267 */     if (label >= 0) {
/*  940:1268 */       throw new IllegalArgumentException("Bad label, no biscuit");
/*  941:     */     }
/*  942:1270 */     label &= 0x7FFFFFFF;
/*  943:1271 */     if (label > this.itsLabelTableTop) {
/*  944:1272 */       throw new IllegalArgumentException("Bad label");
/*  945:     */     }
/*  946:1274 */     if (this.itsLabelTable[label] != -1) {
/*  947:1275 */       throw new IllegalStateException("Can only mark label once");
/*  948:     */     }
/*  949:1278 */     this.itsLabelTable[label] = this.itsCodeBufferTop;
/*  950:     */   }
/*  951:     */   
/*  952:     */   public void markLabel(int label, short stackTop)
/*  953:     */   {
/*  954:1283 */     markLabel(label);
/*  955:1284 */     this.itsStackTop = stackTop;
/*  956:     */   }
/*  957:     */   
/*  958:     */   public void markHandler(int theLabel)
/*  959:     */   {
/*  960:1288 */     this.itsStackTop = 1;
/*  961:1289 */     markLabel(theLabel);
/*  962:     */   }
/*  963:     */   
/*  964:     */   private int getLabelPC(int label)
/*  965:     */   {
/*  966:1294 */     if (label >= 0) {
/*  967:1295 */       throw new IllegalArgumentException("Bad label, no biscuit");
/*  968:     */     }
/*  969:1296 */     label &= 0x7FFFFFFF;
/*  970:1297 */     if (label >= this.itsLabelTableTop) {
/*  971:1298 */       throw new IllegalArgumentException("Bad label");
/*  972:     */     }
/*  973:1299 */     return this.itsLabelTable[label];
/*  974:     */   }
/*  975:     */   
/*  976:     */   private void addLabelFixup(int label, int fixupSite)
/*  977:     */   {
/*  978:1304 */     if (label >= 0) {
/*  979:1305 */       throw new IllegalArgumentException("Bad label, no biscuit");
/*  980:     */     }
/*  981:1306 */     label &= 0x7FFFFFFF;
/*  982:1307 */     if (label >= this.itsLabelTableTop) {
/*  983:1308 */       throw new IllegalArgumentException("Bad label");
/*  984:     */     }
/*  985:1309 */     int top = this.itsFixupTableTop;
/*  986:1310 */     if ((this.itsFixupTable == null) || (top == this.itsFixupTable.length)) {
/*  987:1311 */       if (this.itsFixupTable == null)
/*  988:     */       {
/*  989:1312 */         this.itsFixupTable = new long[40];
/*  990:     */       }
/*  991:     */       else
/*  992:     */       {
/*  993:1314 */         long[] tmp = new long[this.itsFixupTable.length * 2];
/*  994:1315 */         System.arraycopy(this.itsFixupTable, 0, tmp, 0, top);
/*  995:1316 */         this.itsFixupTable = tmp;
/*  996:     */       }
/*  997:     */     }
/*  998:1319 */     this.itsFixupTableTop = (top + 1);
/*  999:1320 */     this.itsFixupTable[top] = (label << 32 | fixupSite);
/* 1000:     */   }
/* 1001:     */   
/* 1002:     */   private void fixLabelGotos()
/* 1003:     */   {
/* 1004:1325 */     byte[] codeBuffer = this.itsCodeBuffer;
/* 1005:1326 */     for (int i = 0; i < this.itsFixupTableTop; i++)
/* 1006:     */     {
/* 1007:1327 */       long fixup = this.itsFixupTable[i];
/* 1008:1328 */       int label = (int)(fixup >> 32);
/* 1009:1329 */       int fixupSite = (int)fixup;
/* 1010:1330 */       int pc = this.itsLabelTable[label];
/* 1011:1331 */       if (pc == -1) {
/* 1012:1333 */         throw new RuntimeException();
/* 1013:     */       }
/* 1014:1336 */       addSuperBlockStart(pc);
/* 1015:1337 */       this.itsJumpFroms.put(pc, fixupSite - 1);
/* 1016:1338 */       int offset = pc - (fixupSite - 1);
/* 1017:1339 */       if ((short)offset != offset) {
/* 1018:1340 */         throw new ClassFileFormatException("Program too complex: too big jump offset");
/* 1019:     */       }
/* 1020:1343 */       codeBuffer[fixupSite] = ((byte)(offset >> 8));
/* 1021:1344 */       codeBuffer[(fixupSite + 1)] = ((byte)offset);
/* 1022:     */     }
/* 1023:1346 */     this.itsFixupTableTop = 0;
/* 1024:     */   }
/* 1025:     */   
/* 1026:     */   public int getCurrentCodeOffset()
/* 1027:     */   {
/* 1028:1355 */     return this.itsCodeBufferTop;
/* 1029:     */   }
/* 1030:     */   
/* 1031:     */   public short getStackTop()
/* 1032:     */   {
/* 1033:1359 */     return this.itsStackTop;
/* 1034:     */   }
/* 1035:     */   
/* 1036:     */   public void setStackTop(short n)
/* 1037:     */   {
/* 1038:1363 */     this.itsStackTop = n;
/* 1039:     */   }
/* 1040:     */   
/* 1041:     */   public void adjustStackTop(int delta)
/* 1042:     */   {
/* 1043:1367 */     int newStack = this.itsStackTop + delta;
/* 1044:1368 */     if ((newStack < 0) || (32767 < newStack)) {
/* 1045:1368 */       badStack(newStack);
/* 1046:     */     }
/* 1047:1369 */     this.itsStackTop = ((short)newStack);
/* 1048:1370 */     if (newStack > this.itsMaxStack) {
/* 1049:1370 */       this.itsMaxStack = ((short)newStack);
/* 1050:     */     }
/* 1051:     */   }
/* 1052:     */   
/* 1053:     */   private void addToCodeBuffer(int b)
/* 1054:     */   {
/* 1055:1379 */     int N = addReservedCodeSpace(1);
/* 1056:1380 */     this.itsCodeBuffer[N] = ((byte)b);
/* 1057:     */   }
/* 1058:     */   
/* 1059:     */   private void addToCodeInt16(int value)
/* 1060:     */   {
/* 1061:1385 */     int N = addReservedCodeSpace(2);
/* 1062:1386 */     putInt16(value, this.itsCodeBuffer, N);
/* 1063:     */   }
/* 1064:     */   
/* 1065:     */   private int addReservedCodeSpace(int size)
/* 1066:     */   {
/* 1067:1391 */     if (this.itsCurrentMethod == null) {
/* 1068:1392 */       throw new IllegalArgumentException("No method to add to");
/* 1069:     */     }
/* 1070:1393 */     int oldTop = this.itsCodeBufferTop;
/* 1071:1394 */     int newTop = oldTop + size;
/* 1072:1395 */     if (newTop > this.itsCodeBuffer.length)
/* 1073:     */     {
/* 1074:1396 */       int newSize = this.itsCodeBuffer.length * 2;
/* 1075:1397 */       if (newTop > newSize) {
/* 1076:1397 */         newSize = newTop;
/* 1077:     */       }
/* 1078:1398 */       byte[] tmp = new byte[newSize];
/* 1079:1399 */       System.arraycopy(this.itsCodeBuffer, 0, tmp, 0, oldTop);
/* 1080:1400 */       this.itsCodeBuffer = tmp;
/* 1081:     */     }
/* 1082:1402 */     this.itsCodeBufferTop = newTop;
/* 1083:1403 */     return oldTop;
/* 1084:     */   }
/* 1085:     */   
/* 1086:     */   public void addExceptionHandler(int startLabel, int endLabel, int handlerLabel, String catchClassName)
/* 1087:     */   {
/* 1088:1409 */     if ((startLabel & 0x80000000) != -2147483648) {
/* 1089:1410 */       throw new IllegalArgumentException("Bad startLabel");
/* 1090:     */     }
/* 1091:1411 */     if ((endLabel & 0x80000000) != -2147483648) {
/* 1092:1412 */       throw new IllegalArgumentException("Bad endLabel");
/* 1093:     */     }
/* 1094:1413 */     if ((handlerLabel & 0x80000000) != -2147483648) {
/* 1095:1414 */       throw new IllegalArgumentException("Bad handlerLabel");
/* 1096:     */     }
/* 1097:1421 */     short catch_type_index = catchClassName == null ? 0 : this.itsConstantPool.addClass(catchClassName);
/* 1098:     */     
/* 1099:     */ 
/* 1100:1424 */     ExceptionTableEntry newEntry = new ExceptionTableEntry(startLabel, endLabel, handlerLabel, catch_type_index);
/* 1101:     */     
/* 1102:     */ 
/* 1103:     */ 
/* 1104:     */ 
/* 1105:1429 */     int N = this.itsExceptionTableTop;
/* 1106:1430 */     if (N == 0)
/* 1107:     */     {
/* 1108:1431 */       this.itsExceptionTable = new ExceptionTableEntry[4];
/* 1109:     */     }
/* 1110:1432 */     else if (N == this.itsExceptionTable.length)
/* 1111:     */     {
/* 1112:1433 */       ExceptionTableEntry[] tmp = new ExceptionTableEntry[N * 2];
/* 1113:1434 */       System.arraycopy(this.itsExceptionTable, 0, tmp, 0, N);
/* 1114:1435 */       this.itsExceptionTable = tmp;
/* 1115:     */     }
/* 1116:1437 */     this.itsExceptionTable[N] = newEntry;
/* 1117:1438 */     this.itsExceptionTableTop = (N + 1);
/* 1118:     */   }
/* 1119:     */   
/* 1120:     */   public void addLineNumberEntry(short lineNumber)
/* 1121:     */   {
/* 1122:1443 */     if (this.itsCurrentMethod == null) {
/* 1123:1444 */       throw new IllegalArgumentException("No method to stop");
/* 1124:     */     }
/* 1125:1445 */     int N = this.itsLineNumberTableTop;
/* 1126:1446 */     if (N == 0)
/* 1127:     */     {
/* 1128:1447 */       this.itsLineNumberTable = new int[16];
/* 1129:     */     }
/* 1130:1448 */     else if (N == this.itsLineNumberTable.length)
/* 1131:     */     {
/* 1132:1449 */       int[] tmp = new int[N * 2];
/* 1133:1450 */       System.arraycopy(this.itsLineNumberTable, 0, tmp, 0, N);
/* 1134:1451 */       this.itsLineNumberTable = tmp;
/* 1135:     */     }
/* 1136:1453 */     this.itsLineNumberTable[N] = ((this.itsCodeBufferTop << 16) + lineNumber);
/* 1137:1454 */     this.itsLineNumberTableTop = (N + 1);
/* 1138:     */   }
/* 1139:     */   
/* 1140:     */   final class StackMapTable
/* 1141:     */   {
/* 1142:     */     private int[] locals;
/* 1143:     */     private int localsTop;
/* 1144:     */     private int[] stack;
/* 1145:     */     private int stackTop;
/* 1146:     */     private SuperBlock[] workList;
/* 1147:     */     private int workListTop;
/* 1148:     */     private SuperBlock[] superBlocks;
/* 1149:     */     private SuperBlock[] superBlockDeps;
/* 1150:     */     private byte[] rawStackMap;
/* 1151:     */     private int rawStackMapTop;
/* 1152:     */     static final boolean DEBUGSTACKMAP = false;
/* 1153:     */     
/* 1154:     */     StackMapTable()
/* 1155:     */     {
/* 1156:1466 */       this.superBlocks = null;
/* 1157:1467 */       this.locals = (this.stack = null);
/* 1158:1468 */       this.workList = null;
/* 1159:1469 */       this.rawStackMap = null;
/* 1160:1470 */       this.localsTop = 0;
/* 1161:1471 */       this.stackTop = 0;
/* 1162:1472 */       this.workListTop = 0;
/* 1163:1473 */       this.rawStackMapTop = 0;
/* 1164:     */     }
/* 1165:     */     
/* 1166:     */     void generate()
/* 1167:     */     {
/* 1168:1477 */       this.superBlocks = new SuperBlock[ClassFileWriter.this.itsSuperBlockStartsTop];
/* 1169:1478 */       int[] initialLocals = ClassFileWriter.this.createInitialLocals();
/* 1170:1480 */       for (int i = 0; i < ClassFileWriter.this.itsSuperBlockStartsTop; i++)
/* 1171:     */       {
/* 1172:1481 */         int start = ClassFileWriter.this.itsSuperBlockStarts[i];
/* 1173:     */         int end;
/* 1174:     */         int end;
/* 1175:1483 */         if (i == ClassFileWriter.this.itsSuperBlockStartsTop - 1) {
/* 1176:1484 */           end = ClassFileWriter.this.itsCodeBufferTop;
/* 1177:     */         } else {
/* 1178:1486 */           end = ClassFileWriter.this.itsSuperBlockStarts[(i + 1)];
/* 1179:     */         }
/* 1180:1488 */         this.superBlocks[i] = new SuperBlock(i, start, end, initialLocals);
/* 1181:     */       }
/* 1182:1501 */       this.superBlockDeps = getSuperBlockDependencies();
/* 1183:     */       
/* 1184:1503 */       verify();
/* 1185:     */     }
/* 1186:     */     
/* 1187:     */     private SuperBlock getSuperBlockFromOffset(int offset)
/* 1188:     */     {
/* 1189:1517 */       for (int i = 0; i < this.superBlocks.length; i++)
/* 1190:     */       {
/* 1191:1518 */         SuperBlock sb = this.superBlocks[i];
/* 1192:1519 */         if (sb == null) {
/* 1193:     */           break;
/* 1194:     */         }
/* 1195:1521 */         if ((offset >= sb.getStart()) && (offset < sb.getEnd())) {
/* 1196:1522 */           return sb;
/* 1197:     */         }
/* 1198:     */       }
/* 1199:1525 */       throw new IllegalArgumentException("bad offset: " + offset);
/* 1200:     */     }
/* 1201:     */     
/* 1202:     */     private boolean isSuperBlockEnd(int opcode)
/* 1203:     */     {
/* 1204:1533 */       switch (opcode)
/* 1205:     */       {
/* 1206:     */       case 167: 
/* 1207:     */       case 170: 
/* 1208:     */       case 171: 
/* 1209:     */       case 172: 
/* 1210:     */       case 173: 
/* 1211:     */       case 174: 
/* 1212:     */       case 176: 
/* 1213:     */       case 177: 
/* 1214:     */       case 191: 
/* 1215:     */       case 200: 
/* 1216:1544 */         return true;
/* 1217:     */       }
/* 1218:1546 */       return false;
/* 1219:     */     }
/* 1220:     */     
/* 1221:     */     private SuperBlock[] getSuperBlockDependencies()
/* 1222:     */     {
/* 1223:1557 */       SuperBlock[] deps = new SuperBlock[this.superBlocks.length];
/* 1224:1559 */       for (int i = 0; i < ClassFileWriter.this.itsExceptionTableTop; i++)
/* 1225:     */       {
/* 1226:1560 */         ExceptionTableEntry ete = ClassFileWriter.this.itsExceptionTable[i];
/* 1227:1561 */         short startPC = (short)ClassFileWriter.this.getLabelPC(ete.itsStartLabel);
/* 1228:1562 */         short handlerPC = (short)ClassFileWriter.this.getLabelPC(ete.itsHandlerLabel);
/* 1229:1563 */         SuperBlock handlerSB = getSuperBlockFromOffset(handlerPC);
/* 1230:1564 */         SuperBlock dep = getSuperBlockFromOffset(startPC);
/* 1231:1565 */         deps[handlerSB.getIndex()] = dep;
/* 1232:     */       }
/* 1233:1567 */       int[] targetPCs = ClassFileWriter.this.itsJumpFroms.getKeys();
/* 1234:1568 */       for (int i = 0; i < targetPCs.length; i++)
/* 1235:     */       {
/* 1236:1569 */         int targetPC = targetPCs[i];
/* 1237:1570 */         int branchPC = ClassFileWriter.this.itsJumpFroms.getInt(targetPC, -1);
/* 1238:1571 */         SuperBlock branchSB = getSuperBlockFromOffset(branchPC);
/* 1239:1572 */         SuperBlock targetSB = getSuperBlockFromOffset(targetPC);
/* 1240:1573 */         deps[targetSB.getIndex()] = branchSB;
/* 1241:     */       }
/* 1242:1576 */       return deps;
/* 1243:     */     }
/* 1244:     */     
/* 1245:     */     private SuperBlock getBranchTarget(int bci)
/* 1246:     */     {
/* 1247:     */       int target;
/* 1248:     */       int target;
/* 1249:1586 */       if ((ClassFileWriter.this.itsCodeBuffer[bci] & 0xFF) == 200) {
/* 1250:1587 */         target = bci + getOperand(bci + 1, 4);
/* 1251:     */       } else {
/* 1252:1589 */         target = bci + (short)getOperand(bci + 1, 2);
/* 1253:     */       }
/* 1254:1591 */       return getSuperBlockFromOffset(target);
/* 1255:     */     }
/* 1256:     */     
/* 1257:     */     private boolean isBranch(int opcode)
/* 1258:     */     {
/* 1259:1599 */       switch (opcode)
/* 1260:     */       {
/* 1261:     */       case 153: 
/* 1262:     */       case 154: 
/* 1263:     */       case 155: 
/* 1264:     */       case 156: 
/* 1265:     */       case 157: 
/* 1266:     */       case 158: 
/* 1267:     */       case 159: 
/* 1268:     */       case 160: 
/* 1269:     */       case 161: 
/* 1270:     */       case 162: 
/* 1271:     */       case 163: 
/* 1272:     */       case 164: 
/* 1273:     */       case 165: 
/* 1274:     */       case 166: 
/* 1275:     */       case 167: 
/* 1276:     */       case 198: 
/* 1277:     */       case 199: 
/* 1278:     */       case 200: 
/* 1279:1618 */         return true;
/* 1280:     */       }
/* 1281:1620 */       return false;
/* 1282:     */     }
/* 1283:     */     
/* 1284:     */     private int getOperand(int offset)
/* 1285:     */     {
/* 1286:1625 */       return getOperand(offset, 1);
/* 1287:     */     }
/* 1288:     */     
/* 1289:     */     private int getOperand(int start, int size)
/* 1290:     */     {
/* 1291:1634 */       int result = 0;
/* 1292:1635 */       if (size > 4) {
/* 1293:1636 */         throw new IllegalArgumentException("bad operand size");
/* 1294:     */       }
/* 1295:1638 */       for (int i = 0; i < size; i++) {
/* 1296:1639 */         result = result << 8 | ClassFileWriter.this.itsCodeBuffer[(start + i)] & 0xFF;
/* 1297:     */       }
/* 1298:1641 */       return result;
/* 1299:     */     }
/* 1300:     */     
/* 1301:     */     private void verify()
/* 1302:     */     {
/* 1303:1649 */       int[] initialLocals = ClassFileWriter.this.createInitialLocals();
/* 1304:1650 */       this.superBlocks[0].merge(initialLocals, initialLocals.length, new int[0], 0, ClassFileWriter.this.itsConstantPool);
/* 1305:     */       
/* 1306:     */ 
/* 1307:     */ 
/* 1308:     */ 
/* 1309:1655 */       this.workList = new SuperBlock[] { this.superBlocks[0] };
/* 1310:1656 */       this.workListTop = 1;
/* 1311:1657 */       executeWorkList();
/* 1312:1660 */       for (int i = 0; i < this.superBlocks.length; i++)
/* 1313:     */       {
/* 1314:1661 */         SuperBlock sb = this.superBlocks[i];
/* 1315:1662 */         if (!sb.isInitialized()) {
/* 1316:1663 */           killSuperBlock(sb);
/* 1317:     */         }
/* 1318:     */       }
/* 1319:1666 */       executeWorkList();
/* 1320:     */     }
/* 1321:     */     
/* 1322:     */     private void killSuperBlock(SuperBlock sb)
/* 1323:     */     {
/* 1324:1685 */       int[] locals = new int[0];
/* 1325:1686 */       int[] stack = { TypeInfo.OBJECT("java/lang/Throwable", ClassFileWriter.this.itsConstantPool) };
/* 1326:1688 */       for (int i = 0; i < ClassFileWriter.this.itsExceptionTableTop; i++)
/* 1327:     */       {
/* 1328:1689 */         ExceptionTableEntry ete = ClassFileWriter.this.itsExceptionTable[i];
/* 1329:1690 */         int eteStart = ClassFileWriter.this.getLabelPC(ete.itsStartLabel);
/* 1330:1691 */         int eteEnd = ClassFileWriter.this.getLabelPC(ete.itsEndLabel);
/* 1331:1692 */         if (((sb.getStart() >= eteStart) && (sb.getStart() < eteEnd)) || ((eteStart >= sb.getStart()) && (eteStart < sb.getEnd())))
/* 1332:     */         {
/* 1333:1694 */           int handlerPC = ClassFileWriter.this.getLabelPC(ete.itsHandlerLabel);
/* 1334:1695 */           SuperBlock handlerSB = getSuperBlockFromOffset(handlerPC);
/* 1335:1696 */           locals = handlerSB.getLocals();
/* 1336:1697 */           break;
/* 1337:     */         }
/* 1338:     */       }
/* 1339:1701 */       sb.merge(locals, locals.length, stack, stack.length, ClassFileWriter.this.itsConstantPool);
/* 1340:     */       
/* 1341:     */ 
/* 1342:1704 */       int end = sb.getEnd() - 1;
/* 1343:1705 */       ClassFileWriter.this.itsCodeBuffer[end] = -65;
/* 1344:1706 */       for (int bci = sb.getStart(); bci < end; bci++) {
/* 1345:1707 */         ClassFileWriter.this.itsCodeBuffer[bci] = 0;
/* 1346:     */       }
/* 1347:     */     }
/* 1348:     */     
/* 1349:     */     private void executeWorkList()
/* 1350:     */     {
/* 1351:1712 */       while (this.workListTop > 0)
/* 1352:     */       {
/* 1353:1713 */         SuperBlock work = this.workList[(--this.workListTop)];
/* 1354:1714 */         work.setInQueue(false);
/* 1355:1715 */         this.locals = work.getLocals();
/* 1356:1716 */         this.stack = work.getStack();
/* 1357:1717 */         this.localsTop = this.locals.length;
/* 1358:1718 */         this.stackTop = this.stack.length;
/* 1359:1719 */         executeBlock(work);
/* 1360:     */       }
/* 1361:     */     }
/* 1362:     */     
/* 1363:     */     private void executeBlock(SuperBlock work)
/* 1364:     */     {
/* 1365:1727 */       int bc = 0;
/* 1366:1728 */       int next = 0;
/* 1367:1737 */       for (int bci = work.getStart(); bci < work.getEnd(); bci += next)
/* 1368:     */       {
/* 1369:1738 */         bc = ClassFileWriter.this.itsCodeBuffer[bci] & 0xFF;
/* 1370:1739 */         next = execute(bci);
/* 1371:1746 */         if (isBranch(bc))
/* 1372:     */         {
/* 1373:1747 */           SuperBlock targetSB = getBranchTarget(bci);
/* 1374:     */           
/* 1375:     */ 
/* 1376:     */ 
/* 1377:     */ 
/* 1378:     */ 
/* 1379:     */ 
/* 1380:     */ 
/* 1381:     */ 
/* 1382:     */ 
/* 1383:     */ 
/* 1384:1758 */           flowInto(targetSB);
/* 1385:     */         }
/* 1386:1765 */         else if (bc == 170)
/* 1387:     */         {
/* 1388:1766 */           int switchStart = bci + 1 + (0x3 & (bci ^ 0xFFFFFFFF));
/* 1389:1767 */           int defaultOffset = getOperand(switchStart, 4);
/* 1390:1768 */           SuperBlock targetSB = getSuperBlockFromOffset(bci + defaultOffset);
/* 1391:     */           
/* 1392:     */ 
/* 1393:     */ 
/* 1394:     */ 
/* 1395:     */ 
/* 1396:1774 */           flowInto(targetSB);
/* 1397:1775 */           int low = getOperand(switchStart + 4, 4);
/* 1398:1776 */           int high = getOperand(switchStart + 8, 4);
/* 1399:1777 */           int numCases = high - low + 1;
/* 1400:1778 */           int caseBase = switchStart + 12;
/* 1401:1779 */           for (int i = 0; i < numCases; i++)
/* 1402:     */           {
/* 1403:1780 */             int label = bci + getOperand(caseBase + 4 * i, 4);
/* 1404:1781 */             targetSB = getSuperBlockFromOffset(label);
/* 1405:     */             
/* 1406:     */ 
/* 1407:     */ 
/* 1408:     */ 
/* 1409:     */ 
/* 1410:1787 */             flowInto(targetSB);
/* 1411:     */           }
/* 1412:     */         }
/* 1413:1791 */         for (int i = 0; i < ClassFileWriter.this.itsExceptionTableTop; i++)
/* 1414:     */         {
/* 1415:1792 */           ExceptionTableEntry ete = ClassFileWriter.this.itsExceptionTable[i];
/* 1416:1793 */           short startPC = (short)ClassFileWriter.this.getLabelPC(ete.itsStartLabel);
/* 1417:1794 */           short endPC = (short)ClassFileWriter.this.getLabelPC(ete.itsEndLabel);
/* 1418:1795 */           if ((bci >= startPC) && (bci < endPC))
/* 1419:     */           {
/* 1420:1798 */             short handlerPC = (short)ClassFileWriter.this.getLabelPC(ete.itsHandlerLabel);
/* 1421:     */             
/* 1422:1800 */             SuperBlock sb = getSuperBlockFromOffset(handlerPC);
/* 1423:     */             int exceptionType;
/* 1424:     */             int exceptionType;
/* 1425:1803 */             if (ete.itsCatchType == 0) {
/* 1426:1804 */               exceptionType = TypeInfo.OBJECT(ClassFileWriter.this.itsConstantPool.addClass("java/lang/Throwable"));
/* 1427:     */             } else {
/* 1428:1807 */               exceptionType = TypeInfo.OBJECT(ete.itsCatchType);
/* 1429:     */             }
/* 1430:1809 */             sb.merge(this.locals, this.localsTop, new int[] { exceptionType }, 1, ClassFileWriter.this.itsConstantPool);
/* 1431:     */             
/* 1432:1811 */             addToWorkList(sb);
/* 1433:     */           }
/* 1434:     */         }
/* 1435:     */       }
/* 1436:1824 */       if (!isSuperBlockEnd(bc))
/* 1437:     */       {
/* 1438:1825 */         int nextIndex = work.getIndex() + 1;
/* 1439:1826 */         if (nextIndex < this.superBlocks.length) {
/* 1440:1832 */           flowInto(this.superBlocks[nextIndex]);
/* 1441:     */         }
/* 1442:     */       }
/* 1443:     */     }
/* 1444:     */     
/* 1445:     */     private void flowInto(SuperBlock sb)
/* 1446:     */     {
/* 1447:1842 */       if (sb.merge(this.locals, this.localsTop, this.stack, this.stackTop, ClassFileWriter.this.itsConstantPool)) {
/* 1448:1843 */         addToWorkList(sb);
/* 1449:     */       }
/* 1450:     */     }
/* 1451:     */     
/* 1452:     */     private void addToWorkList(SuperBlock sb)
/* 1453:     */     {
/* 1454:1848 */       if (!sb.isInQueue())
/* 1455:     */       {
/* 1456:1849 */         sb.setInQueue(true);
/* 1457:1850 */         sb.setInitialized(true);
/* 1458:1851 */         if (this.workListTop == this.workList.length)
/* 1459:     */         {
/* 1460:1852 */           SuperBlock[] tmp = new SuperBlock[this.workListTop * 2];
/* 1461:1853 */           System.arraycopy(this.workList, 0, tmp, 0, this.workListTop);
/* 1462:1854 */           this.workList = tmp;
/* 1463:     */         }
/* 1464:1856 */         this.workList[(this.workListTop++)] = sb;
/* 1465:     */       }
/* 1466:     */     }
/* 1467:     */     
/* 1468:     */     private int execute(int bci)
/* 1469:     */     {
/* 1470:1867 */       int bc = ClassFileWriter.this.itsCodeBuffer[bci] & 0xFF;
/* 1471:     */       
/* 1472:1869 */       int length = 0;
/* 1473:     */       int type;
/* 1474:     */       int type2;
/* 1475:     */       int index;
/* 1476:     */       String className;
/* 1477:     */       long lType;
/* 1478:1873 */       switch (bc)
/* 1479:     */       {
/* 1480:     */       case 0: 
/* 1481:     */       case 132: 
/* 1482:     */       case 167: 
/* 1483:     */       case 200: 
/* 1484:     */         break;
/* 1485:     */       case 192: 
/* 1486:1881 */         pop();
/* 1487:1882 */         push(TypeInfo.OBJECT(getOperand(bci + 1, 2)));
/* 1488:1883 */         break;
/* 1489:     */       case 79: 
/* 1490:     */       case 80: 
/* 1491:     */       case 81: 
/* 1492:     */       case 82: 
/* 1493:     */       case 83: 
/* 1494:     */       case 84: 
/* 1495:     */       case 85: 
/* 1496:     */       case 86: 
/* 1497:1892 */         pop();
/* 1498:     */       case 159: 
/* 1499:     */       case 160: 
/* 1500:     */       case 161: 
/* 1501:     */       case 162: 
/* 1502:     */       case 163: 
/* 1503:     */       case 164: 
/* 1504:     */       case 165: 
/* 1505:     */       case 166: 
/* 1506:     */       case 181: 
/* 1507:1902 */         pop();
/* 1508:     */       case 87: 
/* 1509:     */       case 153: 
/* 1510:     */       case 154: 
/* 1511:     */       case 155: 
/* 1512:     */       case 156: 
/* 1513:     */       case 157: 
/* 1514:     */       case 158: 
/* 1515:     */       case 179: 
/* 1516:     */       case 194: 
/* 1517:     */       case 195: 
/* 1518:     */       case 198: 
/* 1519:     */       case 199: 
/* 1520:1915 */         pop();
/* 1521:1916 */         break;
/* 1522:     */       case 88: 
/* 1523:1918 */         pop2();
/* 1524:1919 */         break;
/* 1525:     */       case 1: 
/* 1526:1921 */         push(5);
/* 1527:1922 */         break;
/* 1528:     */       case 46: 
/* 1529:     */       case 51: 
/* 1530:     */       case 52: 
/* 1531:     */       case 53: 
/* 1532:     */       case 96: 
/* 1533:     */       case 100: 
/* 1534:     */       case 104: 
/* 1535:     */       case 108: 
/* 1536:     */       case 112: 
/* 1537:     */       case 120: 
/* 1538:     */       case 122: 
/* 1539:     */       case 124: 
/* 1540:     */       case 126: 
/* 1541:     */       case 128: 
/* 1542:     */       case 130: 
/* 1543:     */       case 148: 
/* 1544:     */       case 149: 
/* 1545:     */       case 150: 
/* 1546:     */       case 151: 
/* 1547:     */       case 152: 
/* 1548:1943 */         pop();
/* 1549:     */       case 116: 
/* 1550:     */       case 136: 
/* 1551:     */       case 139: 
/* 1552:     */       case 142: 
/* 1553:     */       case 145: 
/* 1554:     */       case 146: 
/* 1555:     */       case 147: 
/* 1556:     */       case 190: 
/* 1557:     */       case 193: 
/* 1558:1953 */         pop();
/* 1559:     */       case 2: 
/* 1560:     */       case 3: 
/* 1561:     */       case 4: 
/* 1562:     */       case 5: 
/* 1563:     */       case 6: 
/* 1564:     */       case 7: 
/* 1565:     */       case 8: 
/* 1566:     */       case 16: 
/* 1567:     */       case 17: 
/* 1568:     */       case 21: 
/* 1569:     */       case 26: 
/* 1570:     */       case 27: 
/* 1571:     */       case 28: 
/* 1572:     */       case 29: 
/* 1573:1968 */         push(1);
/* 1574:1969 */         break;
/* 1575:     */       case 47: 
/* 1576:     */       case 97: 
/* 1577:     */       case 101: 
/* 1578:     */       case 105: 
/* 1579:     */       case 109: 
/* 1580:     */       case 113: 
/* 1581:     */       case 121: 
/* 1582:     */       case 123: 
/* 1583:     */       case 125: 
/* 1584:     */       case 127: 
/* 1585:     */       case 129: 
/* 1586:     */       case 131: 
/* 1587:1982 */         pop();
/* 1588:     */       case 117: 
/* 1589:     */       case 133: 
/* 1590:     */       case 140: 
/* 1591:     */       case 143: 
/* 1592:1987 */         pop();
/* 1593:     */       case 9: 
/* 1594:     */       case 10: 
/* 1595:     */       case 22: 
/* 1596:     */       case 30: 
/* 1597:     */       case 31: 
/* 1598:     */       case 32: 
/* 1599:     */       case 33: 
/* 1600:1995 */         push(4);
/* 1601:1996 */         break;
/* 1602:     */       case 48: 
/* 1603:     */       case 98: 
/* 1604:     */       case 102: 
/* 1605:     */       case 106: 
/* 1606:     */       case 110: 
/* 1607:     */       case 114: 
/* 1608:2003 */         pop();
/* 1609:     */       case 118: 
/* 1610:     */       case 134: 
/* 1611:     */       case 137: 
/* 1612:     */       case 144: 
/* 1613:2008 */         pop();
/* 1614:     */       case 11: 
/* 1615:     */       case 12: 
/* 1616:     */       case 13: 
/* 1617:     */       case 23: 
/* 1618:     */       case 34: 
/* 1619:     */       case 35: 
/* 1620:     */       case 36: 
/* 1621:     */       case 37: 
/* 1622:2017 */         push(2);
/* 1623:2018 */         break;
/* 1624:     */       case 49: 
/* 1625:     */       case 99: 
/* 1626:     */       case 103: 
/* 1627:     */       case 107: 
/* 1628:     */       case 111: 
/* 1629:     */       case 115: 
/* 1630:2025 */         pop();
/* 1631:     */       case 119: 
/* 1632:     */       case 135: 
/* 1633:     */       case 138: 
/* 1634:     */       case 141: 
/* 1635:2030 */         pop();
/* 1636:     */       case 14: 
/* 1637:     */       case 15: 
/* 1638:     */       case 24: 
/* 1639:     */       case 38: 
/* 1640:     */       case 39: 
/* 1641:     */       case 40: 
/* 1642:     */       case 41: 
/* 1643:2038 */         push(3);
/* 1644:2039 */         break;
/* 1645:     */       case 54: 
/* 1646:2041 */         executeStore(getOperand(bci + 1), 1);
/* 1647:2042 */         break;
/* 1648:     */       case 59: 
/* 1649:     */       case 60: 
/* 1650:     */       case 61: 
/* 1651:     */       case 62: 
/* 1652:2047 */         executeStore(bc - 59, 1);
/* 1653:2048 */         break;
/* 1654:     */       case 55: 
/* 1655:2050 */         executeStore(getOperand(bci + 1), 4);
/* 1656:2051 */         break;
/* 1657:     */       case 63: 
/* 1658:     */       case 64: 
/* 1659:     */       case 65: 
/* 1660:     */       case 66: 
/* 1661:2056 */         executeStore(bc - 63, 4);
/* 1662:2057 */         break;
/* 1663:     */       case 56: 
/* 1664:2059 */         executeStore(getOperand(bci + 1), 2);
/* 1665:2060 */         break;
/* 1666:     */       case 67: 
/* 1667:     */       case 68: 
/* 1668:     */       case 69: 
/* 1669:     */       case 70: 
/* 1670:2065 */         executeStore(getOperand(bci + 1), 2);
/* 1671:2066 */         break;
/* 1672:     */       case 57: 
/* 1673:2068 */         executeStore(getOperand(bci + 1), 3);
/* 1674:2069 */         break;
/* 1675:     */       case 71: 
/* 1676:     */       case 72: 
/* 1677:     */       case 73: 
/* 1678:     */       case 74: 
/* 1679:2074 */         executeStore(bc - 71, 3);
/* 1680:2075 */         break;
/* 1681:     */       case 25: 
/* 1682:2077 */         executeALoad(getOperand(bci + 1));
/* 1683:2078 */         break;
/* 1684:     */       case 42: 
/* 1685:     */       case 43: 
/* 1686:     */       case 44: 
/* 1687:     */       case 45: 
/* 1688:2083 */         executeALoad(bc - 42);
/* 1689:2084 */         break;
/* 1690:     */       case 58: 
/* 1691:2086 */         executeAStore(getOperand(bci + 1));
/* 1692:2087 */         break;
/* 1693:     */       case 75: 
/* 1694:     */       case 76: 
/* 1695:     */       case 77: 
/* 1696:     */       case 78: 
/* 1697:2092 */         executeAStore(bc - 75);
/* 1698:2093 */         break;
/* 1699:     */       case 172: 
/* 1700:     */       case 173: 
/* 1701:     */       case 174: 
/* 1702:     */       case 175: 
/* 1703:     */       case 176: 
/* 1704:     */       case 177: 
/* 1705:2100 */         clearStack();
/* 1706:2101 */         break;
/* 1707:     */       case 191: 
/* 1708:2103 */         type = pop();
/* 1709:2104 */         clearStack();
/* 1710:2105 */         push(type);
/* 1711:2106 */         break;
/* 1712:     */       case 95: 
/* 1713:2108 */         type = pop();
/* 1714:2109 */         type2 = pop();
/* 1715:2110 */         push(type);
/* 1716:2111 */         push(type2);
/* 1717:2112 */         break;
/* 1718:     */       case 18: 
/* 1719:     */       case 19: 
/* 1720:     */       case 20: 
/* 1721:     */         int index;
/* 1722:2116 */         if (bc == 18) {
/* 1723:2117 */           index = getOperand(bci + 1);
/* 1724:     */         } else {
/* 1725:2119 */           index = getOperand(bci + 1, 2);
/* 1726:     */         }
/* 1727:2121 */         byte constType = ClassFileWriter.this.itsConstantPool.getConstantType(index);
/* 1728:2122 */         switch (constType)
/* 1729:     */         {
/* 1730:     */         case 6: 
/* 1731:2124 */           push(3);
/* 1732:2125 */           break;
/* 1733:     */         case 4: 
/* 1734:2127 */           push(2);
/* 1735:2128 */           break;
/* 1736:     */         case 5: 
/* 1737:2130 */           push(4);
/* 1738:2131 */           break;
/* 1739:     */         case 3: 
/* 1740:2133 */           push(1);
/* 1741:2134 */           break;
/* 1742:     */         case 8: 
/* 1743:2136 */           push(TypeInfo.OBJECT("java/lang/String", ClassFileWriter.this.itsConstantPool));
/* 1744:     */           
/* 1745:2138 */           break;
/* 1746:     */         case 7: 
/* 1747:     */         default: 
/* 1748:2140 */           throw new IllegalArgumentException("bad const type " + constType);
/* 1749:     */         }
/* 1750:     */         break;
/* 1751:     */       case 187: 
/* 1752:2145 */         push(TypeInfo.UNINITIALIZED_VARIABLE(bci));
/* 1753:2146 */         break;
/* 1754:     */       case 188: 
/* 1755:2148 */         pop();
/* 1756:2149 */         char componentType = ClassFileWriter.arrayTypeToName(ClassFileWriter.this.itsCodeBuffer[(bci + 1)]);
/* 1757:     */         
/* 1758:2151 */         index = ClassFileWriter.this.itsConstantPool.addClass("[" + componentType);
/* 1759:2152 */         push(TypeInfo.OBJECT((short)index));
/* 1760:2153 */         break;
/* 1761:     */       case 189: 
/* 1762:2155 */         index = getOperand(bci + 1, 2);
/* 1763:2156 */         className = (String)ClassFileWriter.this.itsConstantPool.getConstantData(index);
/* 1764:2157 */         pop();
/* 1765:2158 */         push(TypeInfo.OBJECT("[L" + className + ';', ClassFileWriter.this.itsConstantPool));
/* 1766:     */         
/* 1767:2160 */         break;
/* 1768:     */       case 182: 
/* 1769:     */       case 183: 
/* 1770:     */       case 184: 
/* 1771:     */       case 185: 
/* 1772:2165 */         index = getOperand(bci + 1, 2);
/* 1773:2166 */         FieldOrMethodRef m = (FieldOrMethodRef)ClassFileWriter.this.itsConstantPool.getConstantData(index);
/* 1774:     */         
/* 1775:2168 */         String methodType = m.getType();
/* 1776:2169 */         String methodName = m.getName();
/* 1777:2170 */         int parameterCount = ClassFileWriter.sizeOfParameters(methodType) >>> 16;
/* 1778:2171 */         for (int i = 0; i < parameterCount; i++) {
/* 1779:2172 */           pop();
/* 1780:     */         }
/* 1781:2174 */         if (bc != 184)
/* 1782:     */         {
/* 1783:2175 */           int instType = pop();
/* 1784:2176 */           int tag = TypeInfo.getTag(instType);
/* 1785:2177 */           if ((tag == TypeInfo.UNINITIALIZED_VARIABLE(0)) || (tag == 6)) {
/* 1786:2179 */             if ("<init>".equals(methodName))
/* 1787:     */             {
/* 1788:2180 */               int newType = TypeInfo.OBJECT(ClassFileWriter.this.itsThisClassIndex);
/* 1789:     */               
/* 1790:2182 */               initializeTypeInfo(instType, newType);
/* 1791:     */             }
/* 1792:     */             else
/* 1793:     */             {
/* 1794:2184 */               throw new IllegalStateException("bad instance");
/* 1795:     */             }
/* 1796:     */           }
/* 1797:     */         }
/* 1798:2188 */         int rParen = methodType.indexOf(')');
/* 1799:2189 */         String returnType = methodType.substring(rParen + 1);
/* 1800:2190 */         returnType = ClassFileWriter.descriptorToInternalName(returnType);
/* 1801:2191 */         if (!returnType.equals("V")) {
/* 1802:2192 */           push(TypeInfo.fromType(returnType, ClassFileWriter.this.itsConstantPool));
/* 1803:     */         }
/* 1804:     */         break;
/* 1805:     */       case 180: 
/* 1806:2196 */         pop();
/* 1807:     */       case 178: 
/* 1808:2198 */         index = getOperand(bci + 1, 2);
/* 1809:2199 */         FieldOrMethodRef f = (FieldOrMethodRef)ClassFileWriter.this.itsConstantPool.getConstantData(index);
/* 1810:     */         
/* 1811:2201 */         String fieldType = ClassFileWriter.descriptorToInternalName(f.getType());
/* 1812:2202 */         push(TypeInfo.fromType(fieldType, ClassFileWriter.this.itsConstantPool));
/* 1813:2203 */         break;
/* 1814:     */       case 89: 
/* 1815:2205 */         type = pop();
/* 1816:2206 */         push(type);
/* 1817:2207 */         push(type);
/* 1818:2208 */         break;
/* 1819:     */       case 90: 
/* 1820:2210 */         type = pop();
/* 1821:2211 */         type2 = pop();
/* 1822:2212 */         push(type);
/* 1823:2213 */         push(type2);
/* 1824:2214 */         push(type);
/* 1825:2215 */         break;
/* 1826:     */       case 91: 
/* 1827:2217 */         type = pop();
/* 1828:2218 */         lType = pop2();
/* 1829:2219 */         push(type);
/* 1830:2220 */         push2(lType);
/* 1831:2221 */         push(type);
/* 1832:2222 */         break;
/* 1833:     */       case 92: 
/* 1834:2224 */         lType = pop2();
/* 1835:2225 */         push2(lType);
/* 1836:2226 */         push2(lType);
/* 1837:2227 */         break;
/* 1838:     */       case 93: 
/* 1839:2229 */         lType = pop2();
/* 1840:2230 */         type = pop();
/* 1841:2231 */         push2(lType);
/* 1842:2232 */         push(type);
/* 1843:2233 */         push2(lType);
/* 1844:2234 */         break;
/* 1845:     */       case 94: 
/* 1846:2236 */         lType = pop2();
/* 1847:2237 */         long lType2 = pop2();
/* 1848:2238 */         push2(lType);
/* 1849:2239 */         push2(lType2);
/* 1850:2240 */         push2(lType);
/* 1851:2241 */         break;
/* 1852:     */       case 170: 
/* 1853:2243 */         int switchStart = bci + 1 + (0x3 & (bci ^ 0xFFFFFFFF));
/* 1854:2244 */         int low = getOperand(switchStart + 4, 4);
/* 1855:2245 */         int high = getOperand(switchStart + 8, 4);
/* 1856:2246 */         length = 4 * (high - low + 4) + switchStart - bci;
/* 1857:2247 */         pop();
/* 1858:2248 */         break;
/* 1859:     */       case 50: 
/* 1860:2250 */         pop();
/* 1861:2251 */         int typeIndex = pop() >>> 8;
/* 1862:2252 */         className = (String)ClassFileWriter.this.itsConstantPool.getConstantData(typeIndex);
/* 1863:     */         
/* 1864:2254 */         String arrayType = className;
/* 1865:2255 */         if (arrayType.charAt(0) != '[') {
/* 1866:2256 */           throw new IllegalStateException("bad array type");
/* 1867:     */         }
/* 1868:2258 */         String elementDesc = arrayType.substring(1);
/* 1869:2259 */         String elementType = ClassFileWriter.descriptorToInternalName(elementDesc);
/* 1870:2260 */         typeIndex = ClassFileWriter.this.itsConstantPool.addClass(elementType);
/* 1871:2261 */         push(TypeInfo.OBJECT(typeIndex));
/* 1872:2262 */         break;
/* 1873:     */       case 168: 
/* 1874:     */       case 169: 
/* 1875:     */       case 171: 
/* 1876:     */       case 186: 
/* 1877:     */       case 196: 
/* 1878:     */       case 197: 
/* 1879:     */       case 201: 
/* 1880:     */       default: 
/* 1881:2271 */         throw new IllegalArgumentException("bad opcode");
/* 1882:     */       }
/* 1883:2274 */       if (length == 0) {
/* 1884:2275 */         length = ClassFileWriter.opcodeLength(bc);
/* 1885:     */       }
/* 1886:2277 */       return length;
/* 1887:     */     }
/* 1888:     */     
/* 1889:     */     private void executeALoad(int localIndex)
/* 1890:     */     {
/* 1891:2281 */       int type = getLocal(localIndex);
/* 1892:2282 */       int tag = TypeInfo.getTag(type);
/* 1893:2283 */       if ((tag == 7) || (tag == 6) || (tag == 8) || (tag == 5)) {
/* 1894:2287 */         push(type);
/* 1895:     */       } else {
/* 1896:2289 */         throw new IllegalStateException("bad local variable type: " + type + " at index: " + localIndex);
/* 1897:     */       }
/* 1898:     */     }
/* 1899:     */     
/* 1900:     */     private void executeAStore(int localIndex)
/* 1901:     */     {
/* 1902:2296 */       setLocal(localIndex, pop());
/* 1903:     */     }
/* 1904:     */     
/* 1905:     */     private void executeStore(int localIndex, int typeInfo)
/* 1906:     */     {
/* 1907:2300 */       pop();
/* 1908:2301 */       setLocal(localIndex, typeInfo);
/* 1909:     */     }
/* 1910:     */     
/* 1911:     */     private void initializeTypeInfo(int prevType, int newType)
/* 1912:     */     {
/* 1913:2310 */       initializeTypeInfo(prevType, newType, this.locals, this.localsTop);
/* 1914:2311 */       initializeTypeInfo(prevType, newType, this.stack, this.stackTop);
/* 1915:     */     }
/* 1916:     */     
/* 1917:     */     private void initializeTypeInfo(int prevType, int newType, int[] data, int dataTop)
/* 1918:     */     {
/* 1919:2316 */       for (int i = 0; i < dataTop; i++) {
/* 1920:2317 */         if (data[i] == prevType) {
/* 1921:2318 */           data[i] = newType;
/* 1922:     */         }
/* 1923:     */       }
/* 1924:     */     }
/* 1925:     */     
/* 1926:     */     private int getLocal(int localIndex)
/* 1927:     */     {
/* 1928:2324 */       if (localIndex < this.localsTop) {
/* 1929:2325 */         return this.locals[localIndex];
/* 1930:     */       }
/* 1931:2327 */       return 0;
/* 1932:     */     }
/* 1933:     */     
/* 1934:     */     private void setLocal(int localIndex, int typeInfo)
/* 1935:     */     {
/* 1936:2332 */       if (localIndex >= this.localsTop)
/* 1937:     */       {
/* 1938:2333 */         int[] tmp = new int[localIndex + 1];
/* 1939:2334 */         System.arraycopy(this.locals, 0, tmp, 0, this.localsTop);
/* 1940:2335 */         this.locals = tmp;
/* 1941:2336 */         this.localsTop = (localIndex + 1);
/* 1942:     */       }
/* 1943:2338 */       this.locals[localIndex] = typeInfo;
/* 1944:     */     }
/* 1945:     */     
/* 1946:     */     private void push(int typeInfo)
/* 1947:     */     {
/* 1948:2342 */       if (this.stackTop == this.stack.length)
/* 1949:     */       {
/* 1950:2343 */         int[] tmp = new int[Math.max(this.stackTop * 2, 4)];
/* 1951:2344 */         System.arraycopy(this.stack, 0, tmp, 0, this.stackTop);
/* 1952:2345 */         this.stack = tmp;
/* 1953:     */       }
/* 1954:2347 */       this.stack[(this.stackTop++)] = typeInfo;
/* 1955:     */     }
/* 1956:     */     
/* 1957:     */     private int pop()
/* 1958:     */     {
/* 1959:2351 */       return this.stack[(--this.stackTop)];
/* 1960:     */     }
/* 1961:     */     
/* 1962:     */     private void push2(long typeInfo)
/* 1963:     */     {
/* 1964:2361 */       push((int)(typeInfo & 0xFFFFFF));
/* 1965:2362 */       typeInfo >>>= 32;
/* 1966:2363 */       if (typeInfo != 0L) {
/* 1967:2364 */         push((int)(typeInfo & 0xFFFFFF));
/* 1968:     */       }
/* 1969:     */     }
/* 1970:     */     
/* 1971:     */     private long pop2()
/* 1972:     */     {
/* 1973:2377 */       long type = pop();
/* 1974:2378 */       if (TypeInfo.isTwoWords((int)type)) {
/* 1975:2379 */         return type;
/* 1976:     */       }
/* 1977:2381 */       return type << 32 | pop() & 0xFFFFFF;
/* 1978:     */     }
/* 1979:     */     
/* 1980:     */     private void clearStack()
/* 1981:     */     {
/* 1982:2386 */       this.stackTop = 0;
/* 1983:     */     }
/* 1984:     */     
/* 1985:     */     int computeWriteSize()
/* 1986:     */     {
/* 1987:2400 */       int writeSize = getWorstCaseWriteSize();
/* 1988:2401 */       this.rawStackMap = new byte[writeSize];
/* 1989:2402 */       computeRawStackMap();
/* 1990:2403 */       return this.rawStackMapTop + 2;
/* 1991:     */     }
/* 1992:     */     
/* 1993:     */     int write(byte[] data, int offset)
/* 1994:     */     {
/* 1995:2407 */       offset = ClassFileWriter.putInt32(this.rawStackMapTop + 2, data, offset);
/* 1996:2408 */       offset = ClassFileWriter.putInt16(this.superBlocks.length - 1, data, offset);
/* 1997:2409 */       System.arraycopy(this.rawStackMap, 0, data, offset, this.rawStackMapTop);
/* 1998:2410 */       return offset + this.rawStackMapTop;
/* 1999:     */     }
/* 2000:     */     
/* 2001:     */     private void computeRawStackMap()
/* 2002:     */     {
/* 2003:2417 */       SuperBlock prev = this.superBlocks[0];
/* 2004:2418 */       int[] prevLocals = prev.getTrimmedLocals();
/* 2005:2419 */       int prevOffset = -1;
/* 2006:2420 */       for (int i = 1; i < this.superBlocks.length; i++)
/* 2007:     */       {
/* 2008:2421 */         SuperBlock current = this.superBlocks[i];
/* 2009:2422 */         int[] currentLocals = current.getTrimmedLocals();
/* 2010:2423 */         int[] currentStack = current.getStack();
/* 2011:2424 */         int offsetDelta = current.getStart() - prevOffset - 1;
/* 2012:2426 */         if (currentStack.length == 0)
/* 2013:     */         {
/* 2014:2427 */           int last = prevLocals.length > currentLocals.length ? currentLocals.length : prevLocals.length;
/* 2015:     */           
/* 2016:2429 */           int delta = Math.abs(prevLocals.length - currentLocals.length);
/* 2017:2434 */           for (int j = 0; j < last; j++) {
/* 2018:2435 */             if (prevLocals[j] != currentLocals[j]) {
/* 2019:     */               break;
/* 2020:     */             }
/* 2021:     */           }
/* 2022:2439 */           if ((j == currentLocals.length) && (delta == 0)) {
/* 2023:2442 */             writeSameFrame(currentLocals, offsetDelta);
/* 2024:2443 */           } else if ((j == currentLocals.length) && (delta <= 3)) {
/* 2025:2446 */             writeChopFrame(delta, offsetDelta);
/* 2026:2447 */           } else if ((j == prevLocals.length) && (delta <= 3)) {
/* 2027:2450 */             writeAppendFrame(currentLocals, delta, offsetDelta);
/* 2028:     */           } else {
/* 2029:2454 */             writeFullFrame(currentLocals, currentStack, offsetDelta);
/* 2030:     */           }
/* 2031:     */         }
/* 2032:2457 */         else if (currentStack.length == 1)
/* 2033:     */         {
/* 2034:2458 */           if (Arrays.equals(prevLocals, currentLocals)) {
/* 2035:2459 */             writeSameLocalsOneStackItemFrame(currentLocals, currentStack, offsetDelta);
/* 2036:     */           } else {
/* 2037:2465 */             writeFullFrame(currentLocals, currentStack, offsetDelta);
/* 2038:     */           }
/* 2039:     */         }
/* 2040:     */         else
/* 2041:     */         {
/* 2042:2472 */           writeFullFrame(currentLocals, currentStack, offsetDelta);
/* 2043:     */         }
/* 2044:2475 */         prev = current;
/* 2045:2476 */         prevLocals = currentLocals;
/* 2046:2477 */         prevOffset = current.getStart();
/* 2047:     */       }
/* 2048:     */     }
/* 2049:     */     
/* 2050:     */     private int getWorstCaseWriteSize()
/* 2051:     */     {
/* 2052:2489 */       return (this.superBlocks.length - 1) * (7 + ClassFileWriter.this.itsMaxLocals * 3 + ClassFileWriter.this.itsMaxStack * 3);
/* 2053:     */     }
/* 2054:     */     
/* 2055:     */     private void writeSameFrame(int[] locals, int offsetDelta)
/* 2056:     */     {
/* 2057:2494 */       if (offsetDelta <= 63)
/* 2058:     */       {
/* 2059:2498 */         this.rawStackMap[(this.rawStackMapTop++)] = ((byte)offsetDelta);
/* 2060:     */       }
/* 2061:     */       else
/* 2062:     */       {
/* 2063:2502 */         this.rawStackMap[(this.rawStackMapTop++)] = -5;
/* 2064:2503 */         this.rawStackMapTop = ClassFileWriter.putInt16(offsetDelta, this.rawStackMap, this.rawStackMapTop);
/* 2065:     */       }
/* 2066:     */     }
/* 2067:     */     
/* 2068:     */     private void writeSameLocalsOneStackItemFrame(int[] locals, int[] stack, int offsetDelta)
/* 2069:     */     {
/* 2070:2511 */       if (offsetDelta <= 63)
/* 2071:     */       {
/* 2072:2515 */         this.rawStackMap[(this.rawStackMapTop++)] = ((byte)(64 + offsetDelta));
/* 2073:     */       }
/* 2074:     */       else
/* 2075:     */       {
/* 2076:2520 */         this.rawStackMap[(this.rawStackMapTop++)] = -9;
/* 2077:2521 */         this.rawStackMapTop = ClassFileWriter.putInt16(offsetDelta, this.rawStackMap, this.rawStackMapTop);
/* 2078:     */       }
/* 2079:2524 */       writeType(stack[0]);
/* 2080:     */     }
/* 2081:     */     
/* 2082:     */     private void writeFullFrame(int[] locals, int[] stack, int offsetDelta)
/* 2083:     */     {
/* 2084:2529 */       this.rawStackMap[(this.rawStackMapTop++)] = -1;
/* 2085:2530 */       this.rawStackMapTop = ClassFileWriter.putInt16(offsetDelta, this.rawStackMap, this.rawStackMapTop);
/* 2086:2531 */       this.rawStackMapTop = ClassFileWriter.putInt16(locals.length, this.rawStackMap, this.rawStackMapTop);
/* 2087:     */       
/* 2088:2533 */       this.rawStackMapTop = writeTypes(locals);
/* 2089:2534 */       this.rawStackMapTop = ClassFileWriter.putInt16(stack.length, this.rawStackMap, this.rawStackMapTop);
/* 2090:     */       
/* 2091:2536 */       this.rawStackMapTop = writeTypes(stack);
/* 2092:     */     }
/* 2093:     */     
/* 2094:     */     private void writeAppendFrame(int[] locals, int localsDelta, int offsetDelta)
/* 2095:     */     {
/* 2096:2541 */       int start = locals.length - localsDelta;
/* 2097:2542 */       this.rawStackMap[(this.rawStackMapTop++)] = ((byte)(251 + localsDelta));
/* 2098:2543 */       this.rawStackMapTop = ClassFileWriter.putInt16(offsetDelta, this.rawStackMap, this.rawStackMapTop);
/* 2099:2544 */       this.rawStackMapTop = writeTypes(locals, start);
/* 2100:     */     }
/* 2101:     */     
/* 2102:     */     private void writeChopFrame(int localsDelta, int offsetDelta)
/* 2103:     */     {
/* 2104:2548 */       this.rawStackMap[(this.rawStackMapTop++)] = ((byte)(251 - localsDelta));
/* 2105:2549 */       this.rawStackMapTop = ClassFileWriter.putInt16(offsetDelta, this.rawStackMap, this.rawStackMapTop);
/* 2106:     */     }
/* 2107:     */     
/* 2108:     */     private int writeTypes(int[] types)
/* 2109:     */     {
/* 2110:2553 */       return writeTypes(types, 0);
/* 2111:     */     }
/* 2112:     */     
/* 2113:     */     private int writeTypes(int[] types, int start)
/* 2114:     */     {
/* 2115:2557 */       int startOffset = this.rawStackMapTop;
/* 2116:2558 */       for (int i = start; i < types.length; i++) {
/* 2117:2559 */         this.rawStackMapTop = writeType(types[i]);
/* 2118:     */       }
/* 2119:2561 */       return this.rawStackMapTop;
/* 2120:     */     }
/* 2121:     */     
/* 2122:     */     private int writeType(int type)
/* 2123:     */     {
/* 2124:2565 */       int tag = type & 0xFF;
/* 2125:2566 */       this.rawStackMap[(this.rawStackMapTop++)] = ((byte)tag);
/* 2126:2567 */       if ((tag == 7) || (tag == 8)) {
/* 2127:2569 */         this.rawStackMapTop = ClassFileWriter.putInt16(type >>> 8, this.rawStackMap, this.rawStackMapTop);
/* 2128:     */       }
/* 2129:2572 */       return this.rawStackMapTop;
/* 2130:     */     }
/* 2131:     */   }
/* 2132:     */   
/* 2133:     */   private static char arrayTypeToName(int type)
/* 2134:     */   {
/* 2135:2600 */     switch (type)
/* 2136:     */     {
/* 2137:     */     case 4: 
/* 2138:2602 */       return 'Z';
/* 2139:     */     case 5: 
/* 2140:2604 */       return 'C';
/* 2141:     */     case 6: 
/* 2142:2606 */       return 'F';
/* 2143:     */     case 7: 
/* 2144:2608 */       return 'D';
/* 2145:     */     case 8: 
/* 2146:2610 */       return 'B';
/* 2147:     */     case 9: 
/* 2148:2612 */       return 'S';
/* 2149:     */     case 10: 
/* 2150:2614 */       return 'I';
/* 2151:     */     case 11: 
/* 2152:2616 */       return 'J';
/* 2153:     */     }
/* 2154:2618 */     throw new IllegalArgumentException("bad operand");
/* 2155:     */   }
/* 2156:     */   
/* 2157:     */   private static String classDescriptorToInternalName(String descriptor)
/* 2158:     */   {
/* 2159:2628 */     return descriptor.substring(1, descriptor.length() - 1);
/* 2160:     */   }
/* 2161:     */   
/* 2162:     */   private static String descriptorToInternalName(String descriptor)
/* 2163:     */   {
/* 2164:2637 */     switch (descriptor.charAt(0))
/* 2165:     */     {
/* 2166:     */     case 'B': 
/* 2167:     */     case 'C': 
/* 2168:     */     case 'D': 
/* 2169:     */     case 'F': 
/* 2170:     */     case 'I': 
/* 2171:     */     case 'J': 
/* 2172:     */     case 'S': 
/* 2173:     */     case 'V': 
/* 2174:     */     case 'Z': 
/* 2175:     */     case '[': 
/* 2176:2648 */       return descriptor;
/* 2177:     */     case 'L': 
/* 2178:2650 */       return classDescriptorToInternalName(descriptor);
/* 2179:     */     }
/* 2180:2652 */     throw new IllegalArgumentException("bad descriptor:" + descriptor);
/* 2181:     */   }
/* 2182:     */   
/* 2183:     */   private int[] createInitialLocals()
/* 2184:     */   {
/* 2185:2664 */     int[] initialLocals = new int[this.itsMaxLocals];
/* 2186:2665 */     int localsTop = 0;
/* 2187:2670 */     if ((this.itsCurrentMethod.getFlags() & 0x8) == 0) {
/* 2188:2671 */       if ("<init>".equals(this.itsCurrentMethod.getName())) {
/* 2189:2672 */         initialLocals[(localsTop++)] = 6;
/* 2190:     */       } else {
/* 2191:2674 */         initialLocals[(localsTop++)] = TypeInfo.OBJECT(this.itsThisClassIndex);
/* 2192:     */       }
/* 2193:     */     }
/* 2194:2679 */     String type = this.itsCurrentMethod.getType();
/* 2195:2680 */     int lParenIndex = type.indexOf('(');
/* 2196:2681 */     int rParenIndex = type.indexOf(')');
/* 2197:2682 */     if ((lParenIndex != 0) || (rParenIndex < 0)) {
/* 2198:2683 */       throw new IllegalArgumentException("bad method type");
/* 2199:     */     }
/* 2200:2685 */     int start = lParenIndex + 1;
/* 2201:2686 */     StringBuilder paramType = new StringBuilder();
/* 2202:2687 */     while (start < rParenIndex) {
/* 2203:2688 */       switch (type.charAt(start))
/* 2204:     */       {
/* 2205:     */       case 'B': 
/* 2206:     */       case 'C': 
/* 2207:     */       case 'D': 
/* 2208:     */       case 'F': 
/* 2209:     */       case 'I': 
/* 2210:     */       case 'J': 
/* 2211:     */       case 'S': 
/* 2212:     */       case 'Z': 
/* 2213:2697 */         paramType.append(type.charAt(start));
/* 2214:2698 */         start++;
/* 2215:2699 */         break;
/* 2216:     */       case 'L': 
/* 2217:2701 */         int end = type.indexOf(';', start) + 1;
/* 2218:2702 */         String name = type.substring(start, end);
/* 2219:2703 */         paramType.append(name);
/* 2220:2704 */         start = end;
/* 2221:2705 */         break;
/* 2222:     */       case '[': 
/* 2223:2707 */         paramType.append('[');
/* 2224:2708 */         start++;
/* 2225:2709 */         break;
/* 2226:     */       case 'E': 
/* 2227:     */       case 'G': 
/* 2228:     */       case 'H': 
/* 2229:     */       case 'K': 
/* 2230:     */       case 'M': 
/* 2231:     */       case 'N': 
/* 2232:     */       case 'O': 
/* 2233:     */       case 'P': 
/* 2234:     */       case 'Q': 
/* 2235:     */       case 'R': 
/* 2236:     */       case 'T': 
/* 2237:     */       case 'U': 
/* 2238:     */       case 'V': 
/* 2239:     */       case 'W': 
/* 2240:     */       case 'X': 
/* 2241:     */       case 'Y': 
/* 2242:     */       default: 
/* 2243:2711 */         String internalType = descriptorToInternalName(paramType.toString());
/* 2244:     */         
/* 2245:2713 */         int typeInfo = TypeInfo.fromType(internalType, this.itsConstantPool);
/* 2246:2714 */         initialLocals[(localsTop++)] = typeInfo;
/* 2247:2715 */         if (TypeInfo.isTwoWords(typeInfo)) {
/* 2248:2716 */           localsTop++;
/* 2249:     */         }
/* 2250:2718 */         paramType.setLength(0);
/* 2251:     */       }
/* 2252:     */     }
/* 2253:2720 */     return initialLocals;
/* 2254:     */   }
/* 2255:     */   
/* 2256:     */   public void write(OutputStream oStream)
/* 2257:     */     throws IOException
/* 2258:     */   {
/* 2259:2732 */     byte[] array = toByteArray();
/* 2260:2733 */     oStream.write(array);
/* 2261:     */   }
/* 2262:     */   
/* 2263:     */   private int getWriteSize()
/* 2264:     */   {
/* 2265:2738 */     int size = 0;
/* 2266:2740 */     if (this.itsSourceFileNameIndex != 0) {
/* 2267:2741 */       this.itsConstantPool.addUtf8("SourceFile");
/* 2268:     */     }
/* 2269:2744 */     size += 8;
/* 2270:2745 */     size += this.itsConstantPool.getWriteSize();
/* 2271:2746 */     size += 2;
/* 2272:2747 */     size += 2;
/* 2273:2748 */     size += 2;
/* 2274:2749 */     size += 2;
/* 2275:2750 */     size += 2 * this.itsInterfaces.size();
/* 2276:     */     
/* 2277:2752 */     size += 2;
/* 2278:2753 */     for (int i = 0; i < this.itsFields.size(); i++) {
/* 2279:2754 */       size += ((ClassFileField)this.itsFields.get(i)).getWriteSize();
/* 2280:     */     }
/* 2281:2757 */     size += 2;
/* 2282:2758 */     for (int i = 0; i < this.itsMethods.size(); i++) {
/* 2283:2759 */       size += ((ClassFileMethod)this.itsMethods.get(i)).getWriteSize();
/* 2284:     */     }
/* 2285:2762 */     if (this.itsSourceFileNameIndex != 0)
/* 2286:     */     {
/* 2287:2763 */       size += 2;
/* 2288:2764 */       size += 2;
/* 2289:2765 */       size += 4;
/* 2290:2766 */       size += 2;
/* 2291:     */     }
/* 2292:     */     else
/* 2293:     */     {
/* 2294:2768 */       size += 2;
/* 2295:     */     }
/* 2296:2771 */     return size;
/* 2297:     */   }
/* 2298:     */   
/* 2299:     */   public byte[] toByteArray()
/* 2300:     */   {
/* 2301:2779 */     int dataSize = getWriteSize();
/* 2302:2780 */     byte[] data = new byte[dataSize];
/* 2303:2781 */     int offset = 0;
/* 2304:     */     
/* 2305:2783 */     short sourceFileAttributeNameIndex = 0;
/* 2306:2784 */     if (this.itsSourceFileNameIndex != 0) {
/* 2307:2785 */       sourceFileAttributeNameIndex = this.itsConstantPool.addUtf8("SourceFile");
/* 2308:     */     }
/* 2309:2789 */     offset = putInt32(-889275714, data, offset);
/* 2310:2790 */     offset = putInt16(MinorVersion, data, offset);
/* 2311:2791 */     offset = putInt16(MajorVersion, data, offset);
/* 2312:2792 */     offset = this.itsConstantPool.write(data, offset);
/* 2313:2793 */     offset = putInt16(this.itsFlags, data, offset);
/* 2314:2794 */     offset = putInt16(this.itsThisClassIndex, data, offset);
/* 2315:2795 */     offset = putInt16(this.itsSuperClassIndex, data, offset);
/* 2316:2796 */     offset = putInt16(this.itsInterfaces.size(), data, offset);
/* 2317:2797 */     for (int i = 0; i < this.itsInterfaces.size(); i++)
/* 2318:     */     {
/* 2319:2798 */       int interfaceIndex = ((Short)this.itsInterfaces.get(i)).shortValue();
/* 2320:2799 */       offset = putInt16(interfaceIndex, data, offset);
/* 2321:     */     }
/* 2322:2801 */     offset = putInt16(this.itsFields.size(), data, offset);
/* 2323:2802 */     for (int i = 0; i < this.itsFields.size(); i++)
/* 2324:     */     {
/* 2325:2803 */       ClassFileField field = (ClassFileField)this.itsFields.get(i);
/* 2326:2804 */       offset = field.write(data, offset);
/* 2327:     */     }
/* 2328:2806 */     offset = putInt16(this.itsMethods.size(), data, offset);
/* 2329:2807 */     for (int i = 0; i < this.itsMethods.size(); i++)
/* 2330:     */     {
/* 2331:2808 */       ClassFileMethod method = (ClassFileMethod)this.itsMethods.get(i);
/* 2332:2809 */       offset = method.write(data, offset);
/* 2333:     */     }
/* 2334:2811 */     if (this.itsSourceFileNameIndex != 0)
/* 2335:     */     {
/* 2336:2812 */       offset = putInt16(1, data, offset);
/* 2337:2813 */       offset = putInt16(sourceFileAttributeNameIndex, data, offset);
/* 2338:2814 */       offset = putInt32(2, data, offset);
/* 2339:2815 */       offset = putInt16(this.itsSourceFileNameIndex, data, offset);
/* 2340:     */     }
/* 2341:     */     else
/* 2342:     */     {
/* 2343:2817 */       offset = putInt16(0, data, offset);
/* 2344:     */     }
/* 2345:2820 */     if (offset != dataSize) {
/* 2346:2822 */       throw new RuntimeException();
/* 2347:     */     }
/* 2348:2825 */     return data;
/* 2349:     */   }
/* 2350:     */   
/* 2351:     */   static int putInt64(long value, byte[] array, int offset)
/* 2352:     */   {
/* 2353:2830 */     offset = putInt32((int)(value >>> 32), array, offset);
/* 2354:2831 */     return putInt32((int)value, array, offset);
/* 2355:     */   }
/* 2356:     */   
/* 2357:     */   private static void badStack(int value)
/* 2358:     */   {
/* 2359:     */     String s;
/* 2360:     */     String s;
/* 2361:2837 */     if (value < 0) {
/* 2362:2837 */       s = "Stack underflow: " + value;
/* 2363:     */     } else {
/* 2364:2838 */       s = "Too big stack: " + value;
/* 2365:     */     }
/* 2366:2839 */     throw new IllegalStateException(s);
/* 2367:     */   }
/* 2368:     */   
/* 2369:     */   private static int sizeOfParameters(String pString)
/* 2370:     */   {
/* 2371:2852 */     int length = pString.length();
/* 2372:2853 */     int rightParenthesis = pString.lastIndexOf(')');
/* 2373:2854 */     if ((3 <= length) && (pString.charAt(0) == '(') && (1 <= rightParenthesis) && (rightParenthesis + 1 < length))
/* 2374:     */     {
/* 2375:2858 */       boolean ok = true;
/* 2376:2859 */       int index = 1;
/* 2377:2860 */       int stackDiff = 0;
/* 2378:2861 */       int count = 0;
/* 2379:2863 */       while (index != rightParenthesis) {
/* 2380:2864 */         switch (pString.charAt(index))
/* 2381:     */         {
/* 2382:     */         case 'E': 
/* 2383:     */         case 'G': 
/* 2384:     */         case 'H': 
/* 2385:     */         case 'K': 
/* 2386:     */         case 'M': 
/* 2387:     */         case 'N': 
/* 2388:     */         case 'O': 
/* 2389:     */         case 'P': 
/* 2390:     */         case 'Q': 
/* 2391:     */         case 'R': 
/* 2392:     */         case 'T': 
/* 2393:     */         case 'U': 
/* 2394:     */         case 'V': 
/* 2395:     */         case 'W': 
/* 2396:     */         case 'X': 
/* 2397:     */         case 'Y': 
/* 2398:     */         default: 
/* 2399:2866 */           ok = false;
/* 2400:2867 */           break;
/* 2401:     */         case 'D': 
/* 2402:     */         case 'J': 
/* 2403:2870 */           stackDiff--;
/* 2404:     */         case 'B': 
/* 2405:     */         case 'C': 
/* 2406:     */         case 'F': 
/* 2407:     */         case 'I': 
/* 2408:     */         case 'S': 
/* 2409:     */         case 'Z': 
/* 2410:2878 */           stackDiff--;
/* 2411:2879 */           count++;
/* 2412:2880 */           index++;
/* 2413:2881 */           break;
/* 2414:     */         case '[': 
/* 2415:2883 */           index++;
/* 2416:2884 */           int c = pString.charAt(index);
/* 2417:2885 */           while (c == 91)
/* 2418:     */           {
/* 2419:2886 */             index++;
/* 2420:2887 */             c = pString.charAt(index);
/* 2421:     */           }
/* 2422:2889 */           switch (c)
/* 2423:     */           {
/* 2424:     */           case 69: 
/* 2425:     */           case 71: 
/* 2426:     */           case 72: 
/* 2427:     */           case 75: 
/* 2428:     */           case 77: 
/* 2429:     */           case 78: 
/* 2430:     */           case 79: 
/* 2431:     */           case 80: 
/* 2432:     */           case 81: 
/* 2433:     */           case 82: 
/* 2434:     */           case 84: 
/* 2435:     */           case 85: 
/* 2436:     */           case 86: 
/* 2437:     */           case 87: 
/* 2438:     */           case 88: 
/* 2439:     */           case 89: 
/* 2440:     */           default: 
/* 2441:2891 */             ok = false;
/* 2442:2892 */             break;
/* 2443:     */           case 66: 
/* 2444:     */           case 67: 
/* 2445:     */           case 68: 
/* 2446:     */           case 70: 
/* 2447:     */           case 73: 
/* 2448:     */           case 74: 
/* 2449:     */           case 83: 
/* 2450:     */           case 90: 
/* 2451:2901 */             stackDiff--;
/* 2452:2902 */             count++;
/* 2453:2903 */             index++;
/* 2454:     */           }
/* 2455:2904 */           break;
/* 2456:     */         case 'L': 
/* 2457:2910 */           stackDiff--;
/* 2458:2911 */           count++;
/* 2459:2912 */           index++;
/* 2460:2913 */           int semicolon = pString.indexOf(';', index);
/* 2461:2914 */           if ((index + 1 > semicolon) || (semicolon >= rightParenthesis))
/* 2462:     */           {
/* 2463:2917 */             ok = false;
/* 2464:     */             break label413;
/* 2465:     */           }
/* 2466:2920 */           index = semicolon + 1;
/* 2467:     */         }
/* 2468:     */       }
/* 2469:     */       label413:
/* 2470:2925 */       if (ok)
/* 2471:     */       {
/* 2472:2926 */         switch (pString.charAt(rightParenthesis + 1))
/* 2473:     */         {
/* 2474:     */         case 'E': 
/* 2475:     */         case 'G': 
/* 2476:     */         case 'H': 
/* 2477:     */         case 'K': 
/* 2478:     */         case 'M': 
/* 2479:     */         case 'N': 
/* 2480:     */         case 'O': 
/* 2481:     */         case 'P': 
/* 2482:     */         case 'Q': 
/* 2483:     */         case 'R': 
/* 2484:     */         case 'T': 
/* 2485:     */         case 'U': 
/* 2486:     */         case 'W': 
/* 2487:     */         case 'X': 
/* 2488:     */         case 'Y': 
/* 2489:     */         default: 
/* 2490:2928 */           ok = false;
/* 2491:2929 */           break;
/* 2492:     */         case 'D': 
/* 2493:     */         case 'J': 
/* 2494:2932 */           stackDiff++;
/* 2495:     */         case 'B': 
/* 2496:     */         case 'C': 
/* 2497:     */         case 'F': 
/* 2498:     */         case 'I': 
/* 2499:     */         case 'L': 
/* 2500:     */         case 'S': 
/* 2501:     */         case 'Z': 
/* 2502:     */         case '[': 
/* 2503:2942 */           stackDiff++;
/* 2504:     */         }
/* 2505:2947 */         if (ok) {
/* 2506:2948 */           return count << 16 | 0xFFFF & stackDiff;
/* 2507:     */         }
/* 2508:     */       }
/* 2509:     */     }
/* 2510:2952 */     throw new IllegalArgumentException("Bad parameter signature: " + pString);
/* 2511:     */   }
/* 2512:     */   
/* 2513:     */   static int putInt16(int value, byte[] array, int offset)
/* 2514:     */   {
/* 2515:2958 */     array[(offset + 0)] = ((byte)(value >>> 8));
/* 2516:2959 */     array[(offset + 1)] = ((byte)value);
/* 2517:2960 */     return offset + 2;
/* 2518:     */   }
/* 2519:     */   
/* 2520:     */   static int putInt32(int value, byte[] array, int offset)
/* 2521:     */   {
/* 2522:2965 */     array[(offset + 0)] = ((byte)(value >>> 24));
/* 2523:2966 */     array[(offset + 1)] = ((byte)(value >>> 16));
/* 2524:2967 */     array[(offset + 2)] = ((byte)(value >>> 8));
/* 2525:2968 */     array[(offset + 3)] = ((byte)value);
/* 2526:2969 */     return offset + 4;
/* 2527:     */   }
/* 2528:     */   
/* 2529:     */   static int opcodeLength(int opcode)
/* 2530:     */   {
/* 2531:2979 */     switch (opcode)
/* 2532:     */     {
/* 2533:     */     case 0: 
/* 2534:     */     case 1: 
/* 2535:     */     case 2: 
/* 2536:     */     case 3: 
/* 2537:     */     case 4: 
/* 2538:     */     case 5: 
/* 2539:     */     case 6: 
/* 2540:     */     case 7: 
/* 2541:     */     case 8: 
/* 2542:     */     case 9: 
/* 2543:     */     case 10: 
/* 2544:     */     case 11: 
/* 2545:     */     case 12: 
/* 2546:     */     case 13: 
/* 2547:     */     case 14: 
/* 2548:     */     case 15: 
/* 2549:     */     case 26: 
/* 2550:     */     case 27: 
/* 2551:     */     case 28: 
/* 2552:     */     case 29: 
/* 2553:     */     case 30: 
/* 2554:     */     case 31: 
/* 2555:     */     case 32: 
/* 2556:     */     case 33: 
/* 2557:     */     case 34: 
/* 2558:     */     case 35: 
/* 2559:     */     case 36: 
/* 2560:     */     case 37: 
/* 2561:     */     case 38: 
/* 2562:     */     case 39: 
/* 2563:     */     case 40: 
/* 2564:     */     case 41: 
/* 2565:     */     case 42: 
/* 2566:     */     case 43: 
/* 2567:     */     case 44: 
/* 2568:     */     case 45: 
/* 2569:     */     case 46: 
/* 2570:     */     case 47: 
/* 2571:     */     case 48: 
/* 2572:     */     case 49: 
/* 2573:     */     case 50: 
/* 2574:     */     case 51: 
/* 2575:     */     case 52: 
/* 2576:     */     case 53: 
/* 2577:     */     case 59: 
/* 2578:     */     case 60: 
/* 2579:     */     case 61: 
/* 2580:     */     case 62: 
/* 2581:     */     case 63: 
/* 2582:     */     case 64: 
/* 2583:     */     case 65: 
/* 2584:     */     case 66: 
/* 2585:     */     case 67: 
/* 2586:     */     case 68: 
/* 2587:     */     case 69: 
/* 2588:     */     case 70: 
/* 2589:     */     case 71: 
/* 2590:     */     case 72: 
/* 2591:     */     case 73: 
/* 2592:     */     case 74: 
/* 2593:     */     case 75: 
/* 2594:     */     case 76: 
/* 2595:     */     case 77: 
/* 2596:     */     case 78: 
/* 2597:     */     case 79: 
/* 2598:     */     case 80: 
/* 2599:     */     case 81: 
/* 2600:     */     case 82: 
/* 2601:     */     case 83: 
/* 2602:     */     case 84: 
/* 2603:     */     case 85: 
/* 2604:     */     case 86: 
/* 2605:     */     case 87: 
/* 2606:     */     case 88: 
/* 2607:     */     case 89: 
/* 2608:     */     case 90: 
/* 2609:     */     case 91: 
/* 2610:     */     case 92: 
/* 2611:     */     case 93: 
/* 2612:     */     case 94: 
/* 2613:     */     case 95: 
/* 2614:     */     case 96: 
/* 2615:     */     case 97: 
/* 2616:     */     case 98: 
/* 2617:     */     case 99: 
/* 2618:     */     case 100: 
/* 2619:     */     case 101: 
/* 2620:     */     case 102: 
/* 2621:     */     case 103: 
/* 2622:     */     case 104: 
/* 2623:     */     case 105: 
/* 2624:     */     case 106: 
/* 2625:     */     case 107: 
/* 2626:     */     case 108: 
/* 2627:     */     case 109: 
/* 2628:     */     case 110: 
/* 2629:     */     case 111: 
/* 2630:     */     case 112: 
/* 2631:     */     case 113: 
/* 2632:     */     case 114: 
/* 2633:     */     case 115: 
/* 2634:     */     case 116: 
/* 2635:     */     case 117: 
/* 2636:     */     case 118: 
/* 2637:     */     case 119: 
/* 2638:     */     case 120: 
/* 2639:     */     case 121: 
/* 2640:     */     case 122: 
/* 2641:     */     case 123: 
/* 2642:     */     case 124: 
/* 2643:     */     case 125: 
/* 2644:     */     case 126: 
/* 2645:     */     case 127: 
/* 2646:     */     case 128: 
/* 2647:     */     case 129: 
/* 2648:     */     case 130: 
/* 2649:     */     case 131: 
/* 2650:     */     case 133: 
/* 2651:     */     case 134: 
/* 2652:     */     case 135: 
/* 2653:     */     case 136: 
/* 2654:     */     case 137: 
/* 2655:     */     case 138: 
/* 2656:     */     case 139: 
/* 2657:     */     case 140: 
/* 2658:     */     case 141: 
/* 2659:     */     case 142: 
/* 2660:     */     case 143: 
/* 2661:     */     case 144: 
/* 2662:     */     case 145: 
/* 2663:     */     case 146: 
/* 2664:     */     case 147: 
/* 2665:     */     case 148: 
/* 2666:     */     case 149: 
/* 2667:     */     case 150: 
/* 2668:     */     case 151: 
/* 2669:     */     case 152: 
/* 2670:     */     case 172: 
/* 2671:     */     case 173: 
/* 2672:     */     case 174: 
/* 2673:     */     case 175: 
/* 2674:     */     case 176: 
/* 2675:     */     case 177: 
/* 2676:     */     case 190: 
/* 2677:     */     case 191: 
/* 2678:     */     case 194: 
/* 2679:     */     case 195: 
/* 2680:     */     case 196: 
/* 2681:     */     case 202: 
/* 2682:     */     case 254: 
/* 2683:     */     case 255: 
/* 2684:3131 */       return 1;
/* 2685:     */     case 16: 
/* 2686:     */     case 18: 
/* 2687:     */     case 21: 
/* 2688:     */     case 22: 
/* 2689:     */     case 23: 
/* 2690:     */     case 24: 
/* 2691:     */     case 25: 
/* 2692:     */     case 54: 
/* 2693:     */     case 55: 
/* 2694:     */     case 56: 
/* 2695:     */     case 57: 
/* 2696:     */     case 58: 
/* 2697:     */     case 169: 
/* 2698:     */     case 188: 
/* 2699:3146 */       return 2;
/* 2700:     */     case 17: 
/* 2701:     */     case 19: 
/* 2702:     */     case 20: 
/* 2703:     */     case 132: 
/* 2704:     */     case 153: 
/* 2705:     */     case 154: 
/* 2706:     */     case 155: 
/* 2707:     */     case 156: 
/* 2708:     */     case 157: 
/* 2709:     */     case 158: 
/* 2710:     */     case 159: 
/* 2711:     */     case 160: 
/* 2712:     */     case 161: 
/* 2713:     */     case 162: 
/* 2714:     */     case 163: 
/* 2715:     */     case 164: 
/* 2716:     */     case 165: 
/* 2717:     */     case 166: 
/* 2718:     */     case 167: 
/* 2719:     */     case 168: 
/* 2720:     */     case 178: 
/* 2721:     */     case 179: 
/* 2722:     */     case 180: 
/* 2723:     */     case 181: 
/* 2724:     */     case 182: 
/* 2725:     */     case 183: 
/* 2726:     */     case 184: 
/* 2727:     */     case 187: 
/* 2728:     */     case 189: 
/* 2729:     */     case 192: 
/* 2730:     */     case 193: 
/* 2731:     */     case 198: 
/* 2732:     */     case 199: 
/* 2733:3181 */       return 3;
/* 2734:     */     case 197: 
/* 2735:3184 */       return 4;
/* 2736:     */     case 185: 
/* 2737:     */     case 200: 
/* 2738:     */     case 201: 
/* 2739:3189 */       return 5;
/* 2740:     */     }
/* 2741:3197 */     throw new IllegalArgumentException("Bad opcode: " + opcode);
/* 2742:     */   }
/* 2743:     */   
/* 2744:     */   static int opcodeCount(int opcode)
/* 2745:     */   {
/* 2746:3205 */     switch (opcode)
/* 2747:     */     {
/* 2748:     */     case 0: 
/* 2749:     */     case 1: 
/* 2750:     */     case 2: 
/* 2751:     */     case 3: 
/* 2752:     */     case 4: 
/* 2753:     */     case 5: 
/* 2754:     */     case 6: 
/* 2755:     */     case 7: 
/* 2756:     */     case 8: 
/* 2757:     */     case 9: 
/* 2758:     */     case 10: 
/* 2759:     */     case 11: 
/* 2760:     */     case 12: 
/* 2761:     */     case 13: 
/* 2762:     */     case 14: 
/* 2763:     */     case 15: 
/* 2764:     */     case 26: 
/* 2765:     */     case 27: 
/* 2766:     */     case 28: 
/* 2767:     */     case 29: 
/* 2768:     */     case 30: 
/* 2769:     */     case 31: 
/* 2770:     */     case 32: 
/* 2771:     */     case 33: 
/* 2772:     */     case 34: 
/* 2773:     */     case 35: 
/* 2774:     */     case 36: 
/* 2775:     */     case 37: 
/* 2776:     */     case 38: 
/* 2777:     */     case 39: 
/* 2778:     */     case 40: 
/* 2779:     */     case 41: 
/* 2780:     */     case 42: 
/* 2781:     */     case 43: 
/* 2782:     */     case 44: 
/* 2783:     */     case 45: 
/* 2784:     */     case 46: 
/* 2785:     */     case 47: 
/* 2786:     */     case 48: 
/* 2787:     */     case 49: 
/* 2788:     */     case 50: 
/* 2789:     */     case 51: 
/* 2790:     */     case 52: 
/* 2791:     */     case 53: 
/* 2792:     */     case 59: 
/* 2793:     */     case 60: 
/* 2794:     */     case 61: 
/* 2795:     */     case 62: 
/* 2796:     */     case 63: 
/* 2797:     */     case 64: 
/* 2798:     */     case 65: 
/* 2799:     */     case 66: 
/* 2800:     */     case 67: 
/* 2801:     */     case 68: 
/* 2802:     */     case 69: 
/* 2803:     */     case 70: 
/* 2804:     */     case 71: 
/* 2805:     */     case 72: 
/* 2806:     */     case 73: 
/* 2807:     */     case 74: 
/* 2808:     */     case 75: 
/* 2809:     */     case 76: 
/* 2810:     */     case 77: 
/* 2811:     */     case 78: 
/* 2812:     */     case 79: 
/* 2813:     */     case 80: 
/* 2814:     */     case 81: 
/* 2815:     */     case 82: 
/* 2816:     */     case 83: 
/* 2817:     */     case 84: 
/* 2818:     */     case 85: 
/* 2819:     */     case 86: 
/* 2820:     */     case 87: 
/* 2821:     */     case 88: 
/* 2822:     */     case 89: 
/* 2823:     */     case 90: 
/* 2824:     */     case 91: 
/* 2825:     */     case 92: 
/* 2826:     */     case 93: 
/* 2827:     */     case 94: 
/* 2828:     */     case 95: 
/* 2829:     */     case 96: 
/* 2830:     */     case 97: 
/* 2831:     */     case 98: 
/* 2832:     */     case 99: 
/* 2833:     */     case 100: 
/* 2834:     */     case 101: 
/* 2835:     */     case 102: 
/* 2836:     */     case 103: 
/* 2837:     */     case 104: 
/* 2838:     */     case 105: 
/* 2839:     */     case 106: 
/* 2840:     */     case 107: 
/* 2841:     */     case 108: 
/* 2842:     */     case 109: 
/* 2843:     */     case 110: 
/* 2844:     */     case 111: 
/* 2845:     */     case 112: 
/* 2846:     */     case 113: 
/* 2847:     */     case 114: 
/* 2848:     */     case 115: 
/* 2849:     */     case 116: 
/* 2850:     */     case 117: 
/* 2851:     */     case 118: 
/* 2852:     */     case 119: 
/* 2853:     */     case 120: 
/* 2854:     */     case 121: 
/* 2855:     */     case 122: 
/* 2856:     */     case 123: 
/* 2857:     */     case 124: 
/* 2858:     */     case 125: 
/* 2859:     */     case 126: 
/* 2860:     */     case 127: 
/* 2861:     */     case 128: 
/* 2862:     */     case 129: 
/* 2863:     */     case 130: 
/* 2864:     */     case 131: 
/* 2865:     */     case 133: 
/* 2866:     */     case 134: 
/* 2867:     */     case 135: 
/* 2868:     */     case 136: 
/* 2869:     */     case 137: 
/* 2870:     */     case 138: 
/* 2871:     */     case 139: 
/* 2872:     */     case 140: 
/* 2873:     */     case 141: 
/* 2874:     */     case 142: 
/* 2875:     */     case 143: 
/* 2876:     */     case 144: 
/* 2877:     */     case 145: 
/* 2878:     */     case 146: 
/* 2879:     */     case 147: 
/* 2880:     */     case 148: 
/* 2881:     */     case 149: 
/* 2882:     */     case 150: 
/* 2883:     */     case 151: 
/* 2884:     */     case 152: 
/* 2885:     */     case 172: 
/* 2886:     */     case 173: 
/* 2887:     */     case 174: 
/* 2888:     */     case 175: 
/* 2889:     */     case 176: 
/* 2890:     */     case 177: 
/* 2891:     */     case 190: 
/* 2892:     */     case 191: 
/* 2893:     */     case 194: 
/* 2894:     */     case 195: 
/* 2895:     */     case 196: 
/* 2896:     */     case 202: 
/* 2897:     */     case 254: 
/* 2898:     */     case 255: 
/* 2899:3357 */       return 0;
/* 2900:     */     case 16: 
/* 2901:     */     case 17: 
/* 2902:     */     case 18: 
/* 2903:     */     case 19: 
/* 2904:     */     case 20: 
/* 2905:     */     case 21: 
/* 2906:     */     case 22: 
/* 2907:     */     case 23: 
/* 2908:     */     case 24: 
/* 2909:     */     case 25: 
/* 2910:     */     case 54: 
/* 2911:     */     case 55: 
/* 2912:     */     case 56: 
/* 2913:     */     case 57: 
/* 2914:     */     case 58: 
/* 2915:     */     case 153: 
/* 2916:     */     case 154: 
/* 2917:     */     case 155: 
/* 2918:     */     case 156: 
/* 2919:     */     case 157: 
/* 2920:     */     case 158: 
/* 2921:     */     case 159: 
/* 2922:     */     case 160: 
/* 2923:     */     case 161: 
/* 2924:     */     case 162: 
/* 2925:     */     case 163: 
/* 2926:     */     case 164: 
/* 2927:     */     case 165: 
/* 2928:     */     case 166: 
/* 2929:     */     case 167: 
/* 2930:     */     case 168: 
/* 2931:     */     case 169: 
/* 2932:     */     case 178: 
/* 2933:     */     case 179: 
/* 2934:     */     case 180: 
/* 2935:     */     case 181: 
/* 2936:     */     case 182: 
/* 2937:     */     case 183: 
/* 2938:     */     case 184: 
/* 2939:     */     case 185: 
/* 2940:     */     case 187: 
/* 2941:     */     case 188: 
/* 2942:     */     case 189: 
/* 2943:     */     case 192: 
/* 2944:     */     case 193: 
/* 2945:     */     case 198: 
/* 2946:     */     case 199: 
/* 2947:     */     case 200: 
/* 2948:     */     case 201: 
/* 2949:3407 */       return 1;
/* 2950:     */     case 132: 
/* 2951:     */     case 197: 
/* 2952:3411 */       return 2;
/* 2953:     */     case 170: 
/* 2954:     */     case 171: 
/* 2955:3415 */       return -1;
/* 2956:     */     }
/* 2957:3417 */     throw new IllegalArgumentException("Bad opcode: " + opcode);
/* 2958:     */   }
/* 2959:     */   
/* 2960:     */   static int stackChange(int opcode)
/* 2961:     */   {
/* 2962:3427 */     switch (opcode)
/* 2963:     */     {
/* 2964:     */     case 80: 
/* 2965:     */     case 82: 
/* 2966:3430 */       return -4;
/* 2967:     */     case 79: 
/* 2968:     */     case 81: 
/* 2969:     */     case 83: 
/* 2970:     */     case 84: 
/* 2971:     */     case 85: 
/* 2972:     */     case 86: 
/* 2973:     */     case 148: 
/* 2974:     */     case 151: 
/* 2975:     */     case 152: 
/* 2976:3441 */       return -3;
/* 2977:     */     case 55: 
/* 2978:     */     case 57: 
/* 2979:     */     case 63: 
/* 2980:     */     case 64: 
/* 2981:     */     case 65: 
/* 2982:     */     case 66: 
/* 2983:     */     case 71: 
/* 2984:     */     case 72: 
/* 2985:     */     case 73: 
/* 2986:     */     case 74: 
/* 2987:     */     case 88: 
/* 2988:     */     case 97: 
/* 2989:     */     case 99: 
/* 2990:     */     case 101: 
/* 2991:     */     case 103: 
/* 2992:     */     case 105: 
/* 2993:     */     case 107: 
/* 2994:     */     case 109: 
/* 2995:     */     case 111: 
/* 2996:     */     case 113: 
/* 2997:     */     case 115: 
/* 2998:     */     case 127: 
/* 2999:     */     case 129: 
/* 3000:     */     case 131: 
/* 3001:     */     case 159: 
/* 3002:     */     case 160: 
/* 3003:     */     case 161: 
/* 3004:     */     case 162: 
/* 3005:     */     case 163: 
/* 3006:     */     case 164: 
/* 3007:     */     case 165: 
/* 3008:     */     case 166: 
/* 3009:     */     case 173: 
/* 3010:     */     case 175: 
/* 3011:3477 */       return -2;
/* 3012:     */     case 46: 
/* 3013:     */     case 48: 
/* 3014:     */     case 50: 
/* 3015:     */     case 51: 
/* 3016:     */     case 52: 
/* 3017:     */     case 53: 
/* 3018:     */     case 54: 
/* 3019:     */     case 56: 
/* 3020:     */     case 58: 
/* 3021:     */     case 59: 
/* 3022:     */     case 60: 
/* 3023:     */     case 61: 
/* 3024:     */     case 62: 
/* 3025:     */     case 67: 
/* 3026:     */     case 68: 
/* 3027:     */     case 69: 
/* 3028:     */     case 70: 
/* 3029:     */     case 75: 
/* 3030:     */     case 76: 
/* 3031:     */     case 77: 
/* 3032:     */     case 78: 
/* 3033:     */     case 87: 
/* 3034:     */     case 96: 
/* 3035:     */     case 98: 
/* 3036:     */     case 100: 
/* 3037:     */     case 102: 
/* 3038:     */     case 104: 
/* 3039:     */     case 106: 
/* 3040:     */     case 108: 
/* 3041:     */     case 110: 
/* 3042:     */     case 112: 
/* 3043:     */     case 114: 
/* 3044:     */     case 120: 
/* 3045:     */     case 121: 
/* 3046:     */     case 122: 
/* 3047:     */     case 123: 
/* 3048:     */     case 124: 
/* 3049:     */     case 125: 
/* 3050:     */     case 126: 
/* 3051:     */     case 128: 
/* 3052:     */     case 130: 
/* 3053:     */     case 136: 
/* 3054:     */     case 137: 
/* 3055:     */     case 142: 
/* 3056:     */     case 144: 
/* 3057:     */     case 149: 
/* 3058:     */     case 150: 
/* 3059:     */     case 153: 
/* 3060:     */     case 154: 
/* 3061:     */     case 155: 
/* 3062:     */     case 156: 
/* 3063:     */     case 157: 
/* 3064:     */     case 158: 
/* 3065:     */     case 170: 
/* 3066:     */     case 171: 
/* 3067:     */     case 172: 
/* 3068:     */     case 174: 
/* 3069:     */     case 176: 
/* 3070:     */     case 180: 
/* 3071:     */     case 181: 
/* 3072:     */     case 182: 
/* 3073:     */     case 183: 
/* 3074:     */     case 185: 
/* 3075:     */     case 191: 
/* 3076:     */     case 194: 
/* 3077:     */     case 195: 
/* 3078:     */     case 198: 
/* 3079:     */     case 199: 
/* 3080:3547 */       return -1;
/* 3081:     */     case 0: 
/* 3082:     */     case 47: 
/* 3083:     */     case 49: 
/* 3084:     */     case 95: 
/* 3085:     */     case 116: 
/* 3086:     */     case 117: 
/* 3087:     */     case 118: 
/* 3088:     */     case 119: 
/* 3089:     */     case 132: 
/* 3090:     */     case 134: 
/* 3091:     */     case 138: 
/* 3092:     */     case 139: 
/* 3093:     */     case 143: 
/* 3094:     */     case 145: 
/* 3095:     */     case 146: 
/* 3096:     */     case 147: 
/* 3097:     */     case 167: 
/* 3098:     */     case 169: 
/* 3099:     */     case 177: 
/* 3100:     */     case 178: 
/* 3101:     */     case 179: 
/* 3102:     */     case 184: 
/* 3103:     */     case 188: 
/* 3104:     */     case 189: 
/* 3105:     */     case 190: 
/* 3106:     */     case 192: 
/* 3107:     */     case 193: 
/* 3108:     */     case 196: 
/* 3109:     */     case 200: 
/* 3110:     */     case 202: 
/* 3111:     */     case 254: 
/* 3112:     */     case 255: 
/* 3113:3581 */       return 0;
/* 3114:     */     case 1: 
/* 3115:     */     case 2: 
/* 3116:     */     case 3: 
/* 3117:     */     case 4: 
/* 3118:     */     case 5: 
/* 3119:     */     case 6: 
/* 3120:     */     case 7: 
/* 3121:     */     case 8: 
/* 3122:     */     case 11: 
/* 3123:     */     case 12: 
/* 3124:     */     case 13: 
/* 3125:     */     case 16: 
/* 3126:     */     case 17: 
/* 3127:     */     case 18: 
/* 3128:     */     case 19: 
/* 3129:     */     case 21: 
/* 3130:     */     case 23: 
/* 3131:     */     case 25: 
/* 3132:     */     case 26: 
/* 3133:     */     case 27: 
/* 3134:     */     case 28: 
/* 3135:     */     case 29: 
/* 3136:     */     case 34: 
/* 3137:     */     case 35: 
/* 3138:     */     case 36: 
/* 3139:     */     case 37: 
/* 3140:     */     case 42: 
/* 3141:     */     case 43: 
/* 3142:     */     case 44: 
/* 3143:     */     case 45: 
/* 3144:     */     case 89: 
/* 3145:     */     case 90: 
/* 3146:     */     case 91: 
/* 3147:     */     case 133: 
/* 3148:     */     case 135: 
/* 3149:     */     case 140: 
/* 3150:     */     case 141: 
/* 3151:     */     case 168: 
/* 3152:     */     case 187: 
/* 3153:     */     case 197: 
/* 3154:     */     case 201: 
/* 3155:3624 */       return 1;
/* 3156:     */     case 9: 
/* 3157:     */     case 10: 
/* 3158:     */     case 14: 
/* 3159:     */     case 15: 
/* 3160:     */     case 20: 
/* 3161:     */     case 22: 
/* 3162:     */     case 24: 
/* 3163:     */     case 30: 
/* 3164:     */     case 31: 
/* 3165:     */     case 32: 
/* 3166:     */     case 33: 
/* 3167:     */     case 38: 
/* 3168:     */     case 39: 
/* 3169:     */     case 40: 
/* 3170:     */     case 41: 
/* 3171:     */     case 92: 
/* 3172:     */     case 93: 
/* 3173:     */     case 94: 
/* 3174:3644 */       return 2;
/* 3175:     */     }
/* 3176:3646 */     throw new IllegalArgumentException("Bad opcode: " + opcode);
/* 3177:     */   }
/* 3178:     */   
/* 3179:     */   private static String bytecodeStr(int code)
/* 3180:     */   {
/* 3181:4087 */     return "";
/* 3182:     */   }
/* 3183:     */   
/* 3184:     */   final char[] getCharBuffer(int minimalSize)
/* 3185:     */   {
/* 3186:4092 */     if (minimalSize > this.tmpCharBuffer.length)
/* 3187:     */     {
/* 3188:4093 */       int newSize = this.tmpCharBuffer.length * 2;
/* 3189:4094 */       if (minimalSize > newSize) {
/* 3190:4094 */         newSize = minimalSize;
/* 3191:     */       }
/* 3192:4095 */       this.tmpCharBuffer = new char[newSize];
/* 3193:     */     }
/* 3194:4097 */     return this.tmpCharBuffer;
/* 3195:     */   }
/* 3196:     */   
/* 3197:     */   private void addSuperBlockStart(int pc)
/* 3198:     */   {
/* 3199:4110 */     if (GenerateStackMap)
/* 3200:     */     {
/* 3201:4111 */       if (this.itsSuperBlockStarts == null)
/* 3202:     */       {
/* 3203:4112 */         this.itsSuperBlockStarts = new int[4];
/* 3204:     */       }
/* 3205:4113 */       else if (this.itsSuperBlockStarts.length == this.itsSuperBlockStartsTop)
/* 3206:     */       {
/* 3207:4114 */         int[] tmp = new int[this.itsSuperBlockStartsTop * 2];
/* 3208:4115 */         System.arraycopy(this.itsSuperBlockStarts, 0, tmp, 0, this.itsSuperBlockStartsTop);
/* 3209:     */         
/* 3210:4117 */         this.itsSuperBlockStarts = tmp;
/* 3211:     */       }
/* 3212:4119 */       this.itsSuperBlockStarts[(this.itsSuperBlockStartsTop++)] = pc;
/* 3213:     */     }
/* 3214:     */   }
/* 3215:     */   
/* 3216:     */   private void finalizeSuperBlockStarts()
/* 3217:     */   {
/* 3218:4130 */     if (GenerateStackMap)
/* 3219:     */     {
/* 3220:4131 */       for (int i = 0; i < this.itsExceptionTableTop; i++)
/* 3221:     */       {
/* 3222:4132 */         ExceptionTableEntry ete = this.itsExceptionTable[i];
/* 3223:4133 */         short handlerPC = (short)getLabelPC(ete.itsHandlerLabel);
/* 3224:4134 */         addSuperBlockStart(handlerPC);
/* 3225:     */       }
/* 3226:4136 */       Arrays.sort(this.itsSuperBlockStarts, 0, this.itsSuperBlockStartsTop);
/* 3227:4137 */       int prev = this.itsSuperBlockStarts[0];
/* 3228:4138 */       int copyTo = 1;
/* 3229:4139 */       for (int i = 1; i < this.itsSuperBlockStartsTop; i++)
/* 3230:     */       {
/* 3231:4140 */         int curr = this.itsSuperBlockStarts[i];
/* 3232:4141 */         if (prev != curr)
/* 3233:     */         {
/* 3234:4142 */           if (copyTo != i) {
/* 3235:4143 */             this.itsSuperBlockStarts[copyTo] = curr;
/* 3236:     */           }
/* 3237:4145 */           copyTo++;
/* 3238:4146 */           prev = curr;
/* 3239:     */         }
/* 3240:     */       }
/* 3241:4149 */       this.itsSuperBlockStartsTop = copyTo;
/* 3242:4150 */       if (this.itsSuperBlockStarts[(copyTo - 1)] == this.itsCodeBufferTop) {
/* 3243:4151 */         this.itsSuperBlockStartsTop -= 1;
/* 3244:     */       }
/* 3245:     */     }
/* 3246:     */   }
/* 3247:     */   
/* 3248:4156 */   private int[] itsSuperBlockStarts = null;
/* 3249:4157 */   private int itsSuperBlockStartsTop = 0;
/* 3250:     */   private static final int SuperBlockStartsSize = 4;
/* 3251:4164 */   private UintMap itsJumpFroms = null;
/* 3252:     */   private static final int LineNumberTableSize = 16;
/* 3253:     */   private static final int ExceptionTableSize = 4;
/* 3254:     */   private static final int MajorVersion;
/* 3255:     */   private static final int MinorVersion;
/* 3256:     */   private static final boolean GenerateStackMap;
/* 3257:     */   private static final int FileHeaderConstant = -889275714;
/* 3258:     */   private static final boolean DEBUGSTACK = false;
/* 3259:     */   private static final boolean DEBUGLABELS = false;
/* 3260:     */   private static final boolean DEBUGCODE = false;
/* 3261:     */   private String generatedClassName;
/* 3262:     */   private ExceptionTableEntry[] itsExceptionTable;
/* 3263:     */   private int itsExceptionTableTop;
/* 3264:     */   private int[] itsLineNumberTable;
/* 3265:     */   private int itsLineNumberTableTop;
/* 3266:     */   
/* 3267:     */   static
/* 3268:     */   {
/* 3269:4184 */     InputStream is = null;
/* 3270:4185 */     int major = 48;int minor = 0;
/* 3271:     */     try
/* 3272:     */     {
/* 3273:4187 */       is = ClassFileWriter.class.getResourceAsStream("net/sourceforge/htmlunit/corejs/classfile/ClassFileWriter.class");
/* 3274:4189 */       if (is == null) {
/* 3275:4190 */         is = ClassLoader.getSystemResourceAsStream("net/sourceforge/htmlunit/corejs/classfile/ClassFileWriter.class");
/* 3276:     */       }
/* 3277:4193 */       byte[] header = new byte[8];
/* 3278:     */       
/* 3279:     */ 
/* 3280:4196 */       int read = 0;
/* 3281:4197 */       while (read < 8)
/* 3282:     */       {
/* 3283:4198 */         int c = is.read(header, read, 8 - read);
/* 3284:4199 */         if (c < 0) {
/* 3285:4199 */           throw new IOException();
/* 3286:     */         }
/* 3287:4200 */         read += c;
/* 3288:     */       }
/* 3289:4202 */       minor = header[4] << 8 | header[5];
/* 3290:4203 */       major = header[6] << 8 | header[7]; return;
/* 3291:     */     }
/* 3292:     */     catch (Exception e) {}finally
/* 3293:     */     {
/* 3294:4207 */       MinorVersion = minor;
/* 3295:4208 */       MajorVersion = major;
/* 3296:4209 */       GenerateStackMap = major >= 50;
/* 3297:4210 */       if (is != null) {
/* 3298:     */         try
/* 3299:     */         {
/* 3300:4212 */           is.close();
/* 3301:     */         }
/* 3302:     */         catch (IOException e) {}
/* 3303:     */       }
/* 3304:     */     }
/* 3305:     */   }
/* 3306:     */   
/* 3307:4233 */   private byte[] itsCodeBuffer = new byte[256];
/* 3308:     */   private int itsCodeBufferTop;
/* 3309:     */   private ConstantPool itsConstantPool;
/* 3310:     */   private ClassFileMethod itsCurrentMethod;
/* 3311:     */   private short itsStackTop;
/* 3312:     */   private short itsMaxStack;
/* 3313:     */   private short itsMaxLocals;
/* 3314:4244 */   private ObjArray itsMethods = new ObjArray();
/* 3315:4245 */   private ObjArray itsFields = new ObjArray();
/* 3316:4246 */   private ObjArray itsInterfaces = new ObjArray();
/* 3317:     */   private short itsFlags;
/* 3318:     */   private short itsThisClassIndex;
/* 3319:     */   private short itsSuperClassIndex;
/* 3320:     */   private short itsSourceFileNameIndex;
/* 3321:     */   private static final int MIN_LABEL_TABLE_SIZE = 32;
/* 3322:     */   private int[] itsLabelTable;
/* 3323:     */   private int itsLabelTableTop;
/* 3324:     */   private static final int MIN_FIXUP_TABLE_SIZE = 40;
/* 3325:     */   private long[] itsFixupTable;
/* 3326:     */   private int itsFixupTableTop;
/* 3327:     */   private ObjArray itsVarDescriptors;
/* 3328:4263 */   private char[] tmpCharBuffer = new char[64];
/* 3329:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.classfile.ClassFileWriter
 * JD-Core Version:    0.7.0.1
 */