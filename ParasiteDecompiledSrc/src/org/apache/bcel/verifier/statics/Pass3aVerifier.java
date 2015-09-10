/*    1:     */ package org.apache.bcel.verifier.statics;
/*    2:     */ 
/*    3:     */ import org.apache.bcel.Repository;
/*    4:     */ import org.apache.bcel.classfile.AccessFlags;
/*    5:     */ import org.apache.bcel.classfile.Attribute;
/*    6:     */ import org.apache.bcel.classfile.Code;
/*    7:     */ import org.apache.bcel.classfile.CodeException;
/*    8:     */ import org.apache.bcel.classfile.Constant;
/*    9:     */ import org.apache.bcel.classfile.ConstantCP;
/*   10:     */ import org.apache.bcel.classfile.ConstantClass;
/*   11:     */ import org.apache.bcel.classfile.ConstantDouble;
/*   12:     */ import org.apache.bcel.classfile.ConstantFieldref;
/*   13:     */ import org.apache.bcel.classfile.ConstantFloat;
/*   14:     */ import org.apache.bcel.classfile.ConstantInteger;
/*   15:     */ import org.apache.bcel.classfile.ConstantInterfaceMethodref;
/*   16:     */ import org.apache.bcel.classfile.ConstantLong;
/*   17:     */ import org.apache.bcel.classfile.ConstantMethodref;
/*   18:     */ import org.apache.bcel.classfile.ConstantNameAndType;
/*   19:     */ import org.apache.bcel.classfile.ConstantString;
/*   20:     */ import org.apache.bcel.classfile.ConstantUtf8;
/*   21:     */ import org.apache.bcel.classfile.Field;
/*   22:     */ import org.apache.bcel.classfile.JavaClass;
/*   23:     */ import org.apache.bcel.classfile.LineNumber;
/*   24:     */ import org.apache.bcel.classfile.LineNumberTable;
/*   25:     */ import org.apache.bcel.classfile.LocalVariable;
/*   26:     */ import org.apache.bcel.classfile.LocalVariableTable;
/*   27:     */ import org.apache.bcel.classfile.Method;
/*   28:     */ import org.apache.bcel.generic.ALOAD;
/*   29:     */ import org.apache.bcel.generic.ANEWARRAY;
/*   30:     */ import org.apache.bcel.generic.ASTORE;
/*   31:     */ import org.apache.bcel.generic.ATHROW;
/*   32:     */ import org.apache.bcel.generic.ArrayType;
/*   33:     */ import org.apache.bcel.generic.BREAKPOINT;
/*   34:     */ import org.apache.bcel.generic.BranchInstruction;
/*   35:     */ import org.apache.bcel.generic.CHECKCAST;
/*   36:     */ import org.apache.bcel.generic.CPInstruction;
/*   37:     */ import org.apache.bcel.generic.ConstantPoolGen;
/*   38:     */ import org.apache.bcel.generic.DLOAD;
/*   39:     */ import org.apache.bcel.generic.DSTORE;
/*   40:     */ import org.apache.bcel.generic.EmptyVisitor;
/*   41:     */ import org.apache.bcel.generic.FLOAD;
/*   42:     */ import org.apache.bcel.generic.FSTORE;
/*   43:     */ import org.apache.bcel.generic.FieldInstruction;
/*   44:     */ import org.apache.bcel.generic.GETSTATIC;
/*   45:     */ import org.apache.bcel.generic.GotoInstruction;
/*   46:     */ import org.apache.bcel.generic.IINC;
/*   47:     */ import org.apache.bcel.generic.ILOAD;
/*   48:     */ import org.apache.bcel.generic.IMPDEP1;
/*   49:     */ import org.apache.bcel.generic.IMPDEP2;
/*   50:     */ import org.apache.bcel.generic.INSTANCEOF;
/*   51:     */ import org.apache.bcel.generic.INVOKEINTERFACE;
/*   52:     */ import org.apache.bcel.generic.INVOKESPECIAL;
/*   53:     */ import org.apache.bcel.generic.INVOKESTATIC;
/*   54:     */ import org.apache.bcel.generic.INVOKEVIRTUAL;
/*   55:     */ import org.apache.bcel.generic.ISTORE;
/*   56:     */ import org.apache.bcel.generic.Instruction;
/*   57:     */ import org.apache.bcel.generic.InstructionHandle;
/*   58:     */ import org.apache.bcel.generic.InstructionList;
/*   59:     */ import org.apache.bcel.generic.InvokeInstruction;
/*   60:     */ import org.apache.bcel.generic.JsrInstruction;
/*   61:     */ import org.apache.bcel.generic.LDC;
/*   62:     */ import org.apache.bcel.generic.LDC2_W;
/*   63:     */ import org.apache.bcel.generic.LLOAD;
/*   64:     */ import org.apache.bcel.generic.LOOKUPSWITCH;
/*   65:     */ import org.apache.bcel.generic.LSTORE;
/*   66:     */ import org.apache.bcel.generic.LoadClass;
/*   67:     */ import org.apache.bcel.generic.LocalVariableInstruction;
/*   68:     */ import org.apache.bcel.generic.MULTIANEWARRAY;
/*   69:     */ import org.apache.bcel.generic.NEW;
/*   70:     */ import org.apache.bcel.generic.NEWARRAY;
/*   71:     */ import org.apache.bcel.generic.ObjectType;
/*   72:     */ import org.apache.bcel.generic.PUTSTATIC;
/*   73:     */ import org.apache.bcel.generic.RET;
/*   74:     */ import org.apache.bcel.generic.ReturnInstruction;
/*   75:     */ import org.apache.bcel.generic.Select;
/*   76:     */ import org.apache.bcel.generic.TABLESWITCH;
/*   77:     */ import org.apache.bcel.generic.Type;
/*   78:     */ import org.apache.bcel.verifier.PassVerifier;
/*   79:     */ import org.apache.bcel.verifier.VerificationResult;
/*   80:     */ import org.apache.bcel.verifier.Verifier;
/*   81:     */ import org.apache.bcel.verifier.VerifierFactory;
/*   82:     */ import org.apache.bcel.verifier.exc.AssertionViolatedException;
/*   83:     */ import org.apache.bcel.verifier.exc.ClassConstraintException;
/*   84:     */ import org.apache.bcel.verifier.exc.InvalidMethodException;
/*   85:     */ import org.apache.bcel.verifier.exc.StaticCodeConstraintException;
/*   86:     */ import org.apache.bcel.verifier.exc.StaticCodeInstructionConstraintException;
/*   87:     */ import org.apache.bcel.verifier.exc.StaticCodeInstructionOperandConstraintException;
/*   88:     */ import org.apache.bcel.verifier.exc.VerifierConstraintViolatedException;
/*   89:     */ 
/*   90:     */ public final class Pass3aVerifier
/*   91:     */   extends PassVerifier
/*   92:     */ {
/*   93:     */   private Verifier myOwner;
/*   94:     */   private int method_no;
/*   95:     */   InstructionList instructionList;
/*   96:     */   Code code;
/*   97:     */   
/*   98:     */   public Pass3aVerifier(Verifier owner, int method_no)
/*   99:     */   {
/*  100:  95 */     this.myOwner = owner;
/*  101:  96 */     this.method_no = method_no;
/*  102:     */   }
/*  103:     */   
/*  104:     */   public VerificationResult do_verify()
/*  105:     */   {
/*  106: 117 */     if (this.myOwner.doPass2().equals(VerificationResult.VR_OK))
/*  107:     */     {
/*  108: 120 */       JavaClass jc = Repository.lookupClass(this.myOwner.getClassName());
/*  109: 121 */       Method[] methods = jc.getMethods();
/*  110: 122 */       if (this.method_no >= methods.length) {
/*  111: 123 */         throw new InvalidMethodException("METHOD DOES NOT EXIST!");
/*  112:     */       }
/*  113: 125 */       Method method = methods[this.method_no];
/*  114: 126 */       this.code = method.getCode();
/*  115: 129 */       if ((method.isAbstract()) || (method.isNative())) {
/*  116: 130 */         return VerificationResult.VR_OK;
/*  117:     */       }
/*  118:     */       try
/*  119:     */       {
/*  120: 143 */         this.instructionList = new InstructionList(method.getCode().getCode());
/*  121:     */       }
/*  122:     */       catch (RuntimeException re)
/*  123:     */       {
/*  124: 146 */         return new VerificationResult(2, "Bad bytecode in the code array of the Code attribute of method '" + method + "'.");
/*  125:     */       }
/*  126: 149 */       this.instructionList.setPositions(true);
/*  127:     */       
/*  128:     */ 
/*  129: 152 */       VerificationResult vr = VerificationResult.VR_OK;
/*  130:     */       try
/*  131:     */       {
/*  132: 154 */         delayedPass2Checks();
/*  133:     */       }
/*  134:     */       catch (ClassConstraintException cce)
/*  135:     */       {
/*  136: 157 */         return new VerificationResult(2, cce.getMessage());
/*  137:     */       }
/*  138:     */       try
/*  139:     */       {
/*  140: 161 */         pass3StaticInstructionChecks();
/*  141: 162 */         pass3StaticInstructionOperandsChecks();
/*  142:     */       }
/*  143:     */       catch (StaticCodeConstraintException scce)
/*  144:     */       {
/*  145: 165 */         vr = new VerificationResult(2, scce.getMessage());
/*  146:     */       }
/*  147: 167 */       return vr;
/*  148:     */     }
/*  149: 170 */     return VerificationResult.VR_NOTYET;
/*  150:     */   }
/*  151:     */   
/*  152:     */   private void delayedPass2Checks()
/*  153:     */   {
/*  154: 185 */     int[] instructionPositions = this.instructionList.getInstructionPositions();
/*  155: 186 */     int codeLength = this.code.getCode().length;
/*  156:     */     
/*  157:     */ 
/*  158:     */ 
/*  159:     */ 
/*  160: 191 */     LineNumberTable lnt = this.code.getLineNumberTable();
/*  161: 192 */     if (lnt != null)
/*  162:     */     {
/*  163: 193 */       LineNumber[] lineNumbers = lnt.getLineNumberTable();
/*  164: 194 */       IntList offsets = new IntList();
/*  165: 195 */       for (int i = 0; i < lineNumbers.length; i++)
/*  166:     */       {
/*  167: 196 */         for (int j = 0; j < instructionPositions.length; j++)
/*  168:     */         {
/*  169: 198 */           int offset = lineNumbers[i].getStartPC();
/*  170: 199 */           if (instructionPositions[j] == offset)
/*  171:     */           {
/*  172: 200 */             if (offsets.contains(offset))
/*  173:     */             {
/*  174: 201 */               addMessage("LineNumberTable attribute '" + this.code.getLineNumberTable() + "' refers to the same code offset ('" + offset + "') more than once which is violating the semantics [but is sometimes produced by IBM's 'jikes' compiler]."); break;
/*  175:     */             }
/*  176: 204 */             offsets.add(offset);
/*  177:     */             
/*  178: 206 */             break;
/*  179:     */           }
/*  180:     */         }
/*  181: 209 */         throw new ClassConstraintException("Code attribute '" + this.code + "' has a LineNumberTable attribute '" + this.code.getLineNumberTable() + "' referring to a code offset ('" + lineNumbers[i].getStartPC() + "') that does not exist.");
/*  182:     */       }
/*  183:     */     }
/*  184: 218 */     Attribute[] atts = this.code.getAttributes();
/*  185: 219 */     for (int a = 0; a < atts.length; a++) {
/*  186: 220 */       if ((atts[a] instanceof LocalVariableTable))
/*  187:     */       {
/*  188: 221 */         LocalVariableTable lvt = (LocalVariableTable)atts[a];
/*  189: 222 */         if (lvt != null)
/*  190:     */         {
/*  191: 223 */           LocalVariable[] localVariables = lvt.getLocalVariableTable();
/*  192: 224 */           for (int i = 0; i < localVariables.length; i++)
/*  193:     */           {
/*  194: 225 */             int startpc = localVariables[i].getStartPC();
/*  195: 226 */             int length = localVariables[i].getLength();
/*  196: 228 */             if (!contains(instructionPositions, startpc)) {
/*  197: 229 */               throw new ClassConstraintException("Code attribute '" + this.code + "' has a LocalVariableTable attribute '" + this.code.getLocalVariableTable() + "' referring to a code offset ('" + startpc + "') that does not exist.");
/*  198:     */             }
/*  199: 231 */             if ((!contains(instructionPositions, startpc + length)) && (startpc + length != codeLength)) {
/*  200: 232 */               throw new ClassConstraintException("Code attribute '" + this.code + "' has a LocalVariableTable attribute '" + this.code.getLocalVariableTable() + "' referring to a code offset start_pc+length ('" + (startpc + length) + "') that does not exist.");
/*  201:     */             }
/*  202:     */           }
/*  203:     */         }
/*  204:     */       }
/*  205:     */     }
/*  206: 245 */     CodeException[] exceptionTable = this.code.getExceptionTable();
/*  207: 246 */     for (int i = 0; i < exceptionTable.length; i++)
/*  208:     */     {
/*  209: 247 */       int startpc = exceptionTable[i].getStartPC();
/*  210: 248 */       int endpc = exceptionTable[i].getEndPC();
/*  211: 249 */       int handlerpc = exceptionTable[i].getHandlerPC();
/*  212: 250 */       if (startpc >= endpc) {
/*  213: 251 */         throw new ClassConstraintException("Code attribute '" + this.code + "' has an exception_table entry '" + exceptionTable[i] + "' that has its start_pc ('" + startpc + "') not smaller than its end_pc ('" + endpc + "').");
/*  214:     */       }
/*  215: 253 */       if (!contains(instructionPositions, startpc)) {
/*  216: 254 */         throw new ClassConstraintException("Code attribute '" + this.code + "' has an exception_table entry '" + exceptionTable[i] + "' that has a non-existant bytecode offset as its start_pc ('" + startpc + "').");
/*  217:     */       }
/*  218: 256 */       if ((!contains(instructionPositions, endpc)) && (endpc != codeLength)) {
/*  219: 257 */         throw new ClassConstraintException("Code attribute '" + this.code + "' has an exception_table entry '" + exceptionTable[i] + "' that has a non-existant bytecode offset as its end_pc ('" + startpc + "') [that is also not equal to code_length ('" + codeLength + "')].");
/*  220:     */       }
/*  221: 259 */       if (!contains(instructionPositions, handlerpc)) {
/*  222: 260 */         throw new ClassConstraintException("Code attribute '" + this.code + "' has an exception_table entry '" + exceptionTable[i] + "' that has a non-existant bytecode offset as its handler_pc ('" + handlerpc + "').");
/*  223:     */       }
/*  224:     */     }
/*  225:     */   }
/*  226:     */   
/*  227:     */   private void pass3StaticInstructionChecks()
/*  228:     */   {
/*  229: 279 */     if (this.code.getCode().length >= 65536) {
/*  230: 280 */       throw new StaticCodeInstructionConstraintException("Code array in code attribute '" + this.code + "' too big: must be smaller than 65536 bytes.");
/*  231:     */     }
/*  232: 298 */     InstructionHandle ih = this.instructionList.getStart();
/*  233: 299 */     while (ih != null)
/*  234:     */     {
/*  235: 300 */       Instruction i = ih.getInstruction();
/*  236: 301 */       if ((i instanceof IMPDEP1)) {
/*  237: 302 */         throw new StaticCodeInstructionConstraintException("IMPDEP1 must not be in the code, it is an illegal instruction for _internal_ JVM use!");
/*  238:     */       }
/*  239: 304 */       if ((i instanceof IMPDEP2)) {
/*  240: 305 */         throw new StaticCodeInstructionConstraintException("IMPDEP2 must not be in the code, it is an illegal instruction for _internal_ JVM use!");
/*  241:     */       }
/*  242: 307 */       if ((i instanceof BREAKPOINT)) {
/*  243: 308 */         throw new StaticCodeInstructionConstraintException("BREAKPOINT must not be in the code, it is an illegal instruction for _internal_ JVM use!");
/*  244:     */       }
/*  245: 310 */       ih = ih.getNext();
/*  246:     */     }
/*  247: 317 */     Instruction last = this.instructionList.getEnd().getInstruction();
/*  248: 318 */     if ((!(last instanceof ReturnInstruction)) && (!(last instanceof RET)) && (!(last instanceof GotoInstruction)) && (!(last instanceof ATHROW))) {
/*  249: 322 */       throw new StaticCodeInstructionConstraintException("Execution must not fall off the bottom of the code array. This constraint is enforced statically as some existing verifiers do - so it may be a false alarm if the last instruction is not reachable.");
/*  250:     */     }
/*  251:     */   }
/*  252:     */   
/*  253:     */   private void pass3StaticInstructionOperandsChecks()
/*  254:     */   {
/*  255: 346 */     ConstantPoolGen cpg = new ConstantPoolGen(Repository.lookupClass(this.myOwner.getClassName()).getConstantPool());
/*  256: 347 */     InstOperandConstraintVisitor v = new InstOperandConstraintVisitor(cpg);
/*  257:     */     
/*  258:     */ 
/*  259: 350 */     InstructionHandle ih = this.instructionList.getStart();
/*  260: 351 */     while (ih != null)
/*  261:     */     {
/*  262: 352 */       Instruction i = ih.getInstruction();
/*  263: 355 */       if ((i instanceof JsrInstruction))
/*  264:     */       {
/*  265: 356 */         InstructionHandle target = ((JsrInstruction)i).getTarget();
/*  266: 357 */         if (target == this.instructionList.getStart()) {
/*  267: 358 */           throw new StaticCodeInstructionOperandConstraintException("Due to JustIce's clear definition of subroutines, no JSR or JSR_W may have a top-level instruction (such as the very first instruction, which is targeted by instruction '" + ih + "' as its target.");
/*  268:     */         }
/*  269: 360 */         if (!(target.getInstruction() instanceof ASTORE)) {
/*  270: 361 */           throw new StaticCodeInstructionOperandConstraintException("Due to JustIce's clear definition of subroutines, no JSR or JSR_W may target anything else than an ASTORE instruction. Instruction '" + ih + "' targets '" + target + "'.");
/*  271:     */         }
/*  272:     */       }
/*  273: 366 */       ih.accept(v);
/*  274:     */       
/*  275: 368 */       ih = ih.getNext();
/*  276:     */     }
/*  277:     */   }
/*  278:     */   
/*  279:     */   private static boolean contains(int[] ints, int i)
/*  280:     */   {
/*  281: 375 */     for (int j = 0; j < ints.length; j++) {
/*  282: 376 */       if (ints[j] == i) {
/*  283: 376 */         return true;
/*  284:     */       }
/*  285:     */     }
/*  286: 378 */     return false;
/*  287:     */   }
/*  288:     */   
/*  289:     */   public int getMethodNo()
/*  290:     */   {
/*  291: 383 */     return this.method_no;
/*  292:     */   }
/*  293:     */   
/*  294:     */   private class InstOperandConstraintVisitor
/*  295:     */     extends EmptyVisitor
/*  296:     */   {
/*  297:     */     private ConstantPoolGen cpg;
/*  298:     */     
/*  299:     */     InstOperandConstraintVisitor(ConstantPoolGen cpg)
/*  300:     */     {
/*  301: 396 */       this.cpg = cpg;
/*  302:     */     }
/*  303:     */     
/*  304:     */     private int max_locals()
/*  305:     */     {
/*  306: 404 */       return Repository.lookupClass(Pass3aVerifier.this.myOwner.getClassName()).getMethods()[Pass3aVerifier.this.method_no].getCode().getMaxLocals();
/*  307:     */     }
/*  308:     */     
/*  309:     */     private void constraintViolated(Instruction i, String message)
/*  310:     */     {
/*  311: 411 */       throw new StaticCodeInstructionOperandConstraintException("Instruction " + i + " constraint violated: " + message);
/*  312:     */     }
/*  313:     */     
/*  314:     */     private void indexValid(Instruction i, int idx)
/*  315:     */     {
/*  316: 419 */       if ((idx < 0) || (idx >= this.cpg.getSize())) {
/*  317: 420 */         constraintViolated(i, "Illegal constant pool index '" + idx + "'.");
/*  318:     */       }
/*  319:     */     }
/*  320:     */     
/*  321:     */     public void visitLoadClass(LoadClass o)
/*  322:     */     {
/*  323: 432 */       ObjectType t = o.getLoadClassType(this.cpg);
/*  324: 433 */       if (t != null)
/*  325:     */       {
/*  326: 434 */         Verifier v = VerifierFactory.getVerifier(t.getClassName());
/*  327: 435 */         VerificationResult vr = v.doPass1();
/*  328: 436 */         if (vr.getStatus() != 1) {
/*  329: 437 */           constraintViolated((Instruction)o, "Class '" + o.getLoadClassType(this.cpg).getClassName() + "' is referenced, but cannot be loaded: '" + vr + "'.");
/*  330:     */         }
/*  331:     */       }
/*  332:     */     }
/*  333:     */     
/*  334:     */     public void visitLDC(LDC o)
/*  335:     */     {
/*  336: 452 */       indexValid(o, o.getIndex());
/*  337: 453 */       Constant c = this.cpg.getConstant(o.getIndex());
/*  338: 454 */       if ((!(c instanceof ConstantInteger)) && (!(c instanceof ConstantFloat)) && (!(c instanceof ConstantString))) {
/*  339: 457 */         constraintViolated(o, "Operand of LDC or LDC_W must be one of CONSTANT_Integer, CONSTANT_Float or CONSTANT_String, but is '" + c + "'.");
/*  340:     */       }
/*  341:     */     }
/*  342:     */     
/*  343:     */     public void visitLDC2_W(LDC2_W o)
/*  344:     */     {
/*  345: 464 */       indexValid(o, o.getIndex());
/*  346: 465 */       Constant c = this.cpg.getConstant(o.getIndex());
/*  347: 466 */       if ((!(c instanceof ConstantLong)) && (!(c instanceof ConstantDouble))) {
/*  348: 468 */         constraintViolated(o, "Operand of LDC2_W must be CONSTANT_Long or CONSTANT_Double, but is '" + c + "'.");
/*  349:     */       }
/*  350:     */       try
/*  351:     */       {
/*  352: 471 */         indexValid(o, o.getIndex() + 1);
/*  353:     */       }
/*  354:     */       catch (StaticCodeInstructionOperandConstraintException e)
/*  355:     */       {
/*  356: 474 */         throw new AssertionViolatedException("OOPS: Does not BCEL handle that? LDC2_W operand has a problem.");
/*  357:     */       }
/*  358:     */     }
/*  359:     */     
/*  360:     */     public void visitFieldInstruction(FieldInstruction o)
/*  361:     */     {
/*  362: 481 */       indexValid(o, o.getIndex());
/*  363: 482 */       Constant c = this.cpg.getConstant(o.getIndex());
/*  364: 483 */       if (!(c instanceof ConstantFieldref)) {
/*  365: 484 */         constraintViolated(o, "Indexing a constant that's not a CONSTANT_Fieldref but a '" + c + "'.");
/*  366:     */       }
/*  367:     */     }
/*  368:     */     
/*  369:     */     public void visitInvokeInstruction(InvokeInstruction o)
/*  370:     */     {
/*  371: 490 */       indexValid(o, o.getIndex());
/*  372: 491 */       if (((o instanceof INVOKEVIRTUAL)) || ((o instanceof INVOKESPECIAL)) || ((o instanceof INVOKESTATIC)))
/*  373:     */       {
/*  374: 494 */         Constant c = this.cpg.getConstant(o.getIndex());
/*  375: 495 */         if (!(c instanceof ConstantMethodref))
/*  376:     */         {
/*  377: 496 */           constraintViolated(o, "Indexing a constant that's not a CONSTANT_Methodref but a '" + c + "'.");
/*  378:     */         }
/*  379:     */         else
/*  380:     */         {
/*  381: 500 */           ConstantNameAndType cnat = (ConstantNameAndType)this.cpg.getConstant(((ConstantMethodref)c).getNameAndTypeIndex());
/*  382: 501 */           ConstantUtf8 cutf8 = (ConstantUtf8)this.cpg.getConstant(cnat.getNameIndex());
/*  383: 502 */           if ((cutf8.getBytes().equals("<init>")) && (!(o instanceof INVOKESPECIAL))) {
/*  384: 503 */             constraintViolated(o, "Only INVOKESPECIAL is allowed to invoke instance initialization methods.");
/*  385:     */           }
/*  386: 505 */           if ((!cutf8.getBytes().equals("<init>")) && (cutf8.getBytes().startsWith("<"))) {
/*  387: 506 */             constraintViolated(o, "No method with a name beginning with '<' other than the instance initialization methods may be called by the method invocation instructions.");
/*  388:     */           }
/*  389:     */         }
/*  390:     */       }
/*  391:     */       else
/*  392:     */       {
/*  393: 511 */         Constant c = this.cpg.getConstant(o.getIndex());
/*  394: 512 */         if (!(c instanceof ConstantInterfaceMethodref)) {
/*  395: 513 */           constraintViolated(o, "Indexing a constant that's not a CONSTANT_InterfaceMethodref but a '" + c + "'.");
/*  396:     */         }
/*  397: 521 */         ConstantNameAndType cnat = (ConstantNameAndType)this.cpg.getConstant(((ConstantInterfaceMethodref)c).getNameAndTypeIndex());
/*  398: 522 */         String name = ((ConstantUtf8)this.cpg.getConstant(cnat.getNameIndex())).getBytes();
/*  399: 523 */         if (name.equals("<init>")) {
/*  400: 524 */           constraintViolated(o, "Method to invoke must not be '<init>'.");
/*  401:     */         }
/*  402: 526 */         if (name.equals("<clinit>")) {
/*  403: 527 */           constraintViolated(o, "Method to invoke must not be '<clinit>'.");
/*  404:     */         }
/*  405:     */       }
/*  406: 533 */       Type t = o.getReturnType(this.cpg);
/*  407: 534 */       if ((t instanceof ArrayType)) {
/*  408: 535 */         t = ((ArrayType)t).getBasicType();
/*  409:     */       }
/*  410: 537 */       if ((t instanceof ObjectType))
/*  411:     */       {
/*  412: 538 */         Verifier v = VerifierFactory.getVerifier(((ObjectType)t).getClassName());
/*  413: 539 */         VerificationResult vr = v.doPass2();
/*  414: 540 */         if (vr.getStatus() != 1) {
/*  415: 541 */           constraintViolated(o, "Return type class/interface could not be verified successfully: '" + vr.getMessage() + "'.");
/*  416:     */         }
/*  417:     */       }
/*  418: 545 */       Type[] ts = o.getArgumentTypes(this.cpg);
/*  419: 546 */       for (int i = 0; i < ts.length; i++)
/*  420:     */       {
/*  421: 547 */         t = ts[i];
/*  422: 548 */         if ((t instanceof ArrayType)) {
/*  423: 549 */           t = ((ArrayType)t).getBasicType();
/*  424:     */         }
/*  425: 551 */         if ((t instanceof ObjectType))
/*  426:     */         {
/*  427: 552 */           Verifier v = VerifierFactory.getVerifier(((ObjectType)t).getClassName());
/*  428: 553 */           VerificationResult vr = v.doPass2();
/*  429: 554 */           if (vr.getStatus() != 1) {
/*  430: 555 */             constraintViolated(o, "Argument type class/interface could not be verified successfully: '" + vr.getMessage() + "'.");
/*  431:     */           }
/*  432:     */         }
/*  433:     */       }
/*  434:     */     }
/*  435:     */     
/*  436:     */     public void visitINSTANCEOF(INSTANCEOF o)
/*  437:     */     {
/*  438: 564 */       indexValid(o, o.getIndex());
/*  439: 565 */       Constant c = this.cpg.getConstant(o.getIndex());
/*  440: 566 */       if (!(c instanceof ConstantClass)) {
/*  441: 567 */         constraintViolated(o, "Expecting a CONSTANT_Class operand, but found a '" + c + "'.");
/*  442:     */       }
/*  443:     */     }
/*  444:     */     
/*  445:     */     public void visitCHECKCAST(CHECKCAST o)
/*  446:     */     {
/*  447: 573 */       indexValid(o, o.getIndex());
/*  448: 574 */       Constant c = this.cpg.getConstant(o.getIndex());
/*  449: 575 */       if (!(c instanceof ConstantClass)) {
/*  450: 576 */         constraintViolated(o, "Expecting a CONSTANT_Class operand, but found a '" + c + "'.");
/*  451:     */       }
/*  452:     */     }
/*  453:     */     
/*  454:     */     public void visitNEW(NEW o)
/*  455:     */     {
/*  456: 582 */       indexValid(o, o.getIndex());
/*  457: 583 */       Constant c = this.cpg.getConstant(o.getIndex());
/*  458: 584 */       if (!(c instanceof ConstantClass))
/*  459:     */       {
/*  460: 585 */         constraintViolated(o, "Expecting a CONSTANT_Class operand, but found a '" + c + "'.");
/*  461:     */       }
/*  462:     */       else
/*  463:     */       {
/*  464: 588 */         ConstantUtf8 cutf8 = (ConstantUtf8)this.cpg.getConstant(((ConstantClass)c).getNameIndex());
/*  465: 589 */         Type t = Type.getType("L" + cutf8.getBytes() + ";");
/*  466: 590 */         if ((t instanceof ArrayType)) {
/*  467: 591 */           constraintViolated(o, "NEW must not be used to create an array.");
/*  468:     */         }
/*  469:     */       }
/*  470:     */     }
/*  471:     */     
/*  472:     */     public void visitMULTIANEWARRAY(MULTIANEWARRAY o)
/*  473:     */     {
/*  474: 599 */       indexValid(o, o.getIndex());
/*  475: 600 */       Constant c = this.cpg.getConstant(o.getIndex());
/*  476: 601 */       if (!(c instanceof ConstantClass)) {
/*  477: 602 */         constraintViolated(o, "Expecting a CONSTANT_Class operand, but found a '" + c + "'.");
/*  478:     */       }
/*  479: 604 */       int dimensions2create = o.getDimensions();
/*  480: 605 */       if (dimensions2create < 1) {
/*  481: 606 */         constraintViolated(o, "Number of dimensions to create must be greater than zero.");
/*  482:     */       }
/*  483: 608 */       Type t = o.getType(this.cpg);
/*  484: 609 */       if ((t instanceof ArrayType))
/*  485:     */       {
/*  486: 610 */         int dimensions = ((ArrayType)t).getDimensions();
/*  487: 611 */         if (dimensions < dimensions2create) {
/*  488: 612 */           constraintViolated(o, "Not allowed to create array with more dimensions ('+dimensions2create+') than the one referenced by the CONSTANT_Class '" + t + "'.");
/*  489:     */         }
/*  490:     */       }
/*  491:     */       else
/*  492:     */       {
/*  493: 616 */         constraintViolated(o, "Expecting a CONSTANT_Class referencing an array type. [Constraint not found in The Java Virtual Machine Specification, Second Edition, 4.8.1]");
/*  494:     */       }
/*  495:     */     }
/*  496:     */     
/*  497:     */     public void visitANEWARRAY(ANEWARRAY o)
/*  498:     */     {
/*  499: 622 */       indexValid(o, o.getIndex());
/*  500: 623 */       Constant c = this.cpg.getConstant(o.getIndex());
/*  501: 624 */       if (!(c instanceof ConstantClass)) {
/*  502: 625 */         constraintViolated(o, "Expecting a CONSTANT_Class operand, but found a '" + c + "'.");
/*  503:     */       }
/*  504: 627 */       Type t = o.getType(this.cpg);
/*  505: 628 */       if ((t instanceof ArrayType))
/*  506:     */       {
/*  507: 629 */         int dimensions = ((ArrayType)t).getDimensions();
/*  508: 630 */         if (dimensions >= 255) {
/*  509: 631 */           constraintViolated(o, "Not allowed to create an array with more than 255 dimensions.");
/*  510:     */         }
/*  511:     */       }
/*  512:     */     }
/*  513:     */     
/*  514:     */     public void visitNEWARRAY(NEWARRAY o)
/*  515:     */     {
/*  516: 638 */       byte t = o.getTypecode();
/*  517: 639 */       if ((t != 4) && (t != 5) && (t != 6) && (t != 7) && (t != 8) && (t != 9) && (t != 10) && (t != 11)) {
/*  518: 647 */         constraintViolated(o, "Illegal type code '+t+' for 'atype' operand.");
/*  519:     */       }
/*  520:     */     }
/*  521:     */     
/*  522:     */     public void visitILOAD(ILOAD o)
/*  523:     */     {
/*  524: 653 */       int idx = o.getIndex();
/*  525: 654 */       if (idx < 0)
/*  526:     */       {
/*  527: 655 */         constraintViolated(o, "Index '" + idx + "' must be non-negative.");
/*  528:     */       }
/*  529:     */       else
/*  530:     */       {
/*  531: 658 */         int maxminus1 = max_locals() - 1;
/*  532: 659 */         if (idx > maxminus1) {
/*  533: 660 */           constraintViolated(o, "Index '" + idx + "' must not be greater than max_locals-1 '" + maxminus1 + "'.");
/*  534:     */         }
/*  535:     */       }
/*  536:     */     }
/*  537:     */     
/*  538:     */     public void visitFLOAD(FLOAD o)
/*  539:     */     {
/*  540: 667 */       int idx = o.getIndex();
/*  541: 668 */       if (idx < 0)
/*  542:     */       {
/*  543: 669 */         constraintViolated(o, "Index '" + idx + "' must be non-negative.");
/*  544:     */       }
/*  545:     */       else
/*  546:     */       {
/*  547: 672 */         int maxminus1 = max_locals() - 1;
/*  548: 673 */         if (idx > maxminus1) {
/*  549: 674 */           constraintViolated(o, "Index '" + idx + "' must not be greater than max_locals-1 '" + maxminus1 + "'.");
/*  550:     */         }
/*  551:     */       }
/*  552:     */     }
/*  553:     */     
/*  554:     */     public void visitALOAD(ALOAD o)
/*  555:     */     {
/*  556: 681 */       int idx = o.getIndex();
/*  557: 682 */       if (idx < 0)
/*  558:     */       {
/*  559: 683 */         constraintViolated(o, "Index '" + idx + "' must be non-negative.");
/*  560:     */       }
/*  561:     */       else
/*  562:     */       {
/*  563: 686 */         int maxminus1 = max_locals() - 1;
/*  564: 687 */         if (idx > maxminus1) {
/*  565: 688 */           constraintViolated(o, "Index '" + idx + "' must not be greater than max_locals-1 '" + maxminus1 + "'.");
/*  566:     */         }
/*  567:     */       }
/*  568:     */     }
/*  569:     */     
/*  570:     */     public void visitISTORE(ISTORE o)
/*  571:     */     {
/*  572: 695 */       int idx = o.getIndex();
/*  573: 696 */       if (idx < 0)
/*  574:     */       {
/*  575: 697 */         constraintViolated(o, "Index '" + idx + "' must be non-negative.");
/*  576:     */       }
/*  577:     */       else
/*  578:     */       {
/*  579: 700 */         int maxminus1 = max_locals() - 1;
/*  580: 701 */         if (idx > maxminus1) {
/*  581: 702 */           constraintViolated(o, "Index '" + idx + "' must not be greater than max_locals-1 '" + maxminus1 + "'.");
/*  582:     */         }
/*  583:     */       }
/*  584:     */     }
/*  585:     */     
/*  586:     */     public void visitFSTORE(FSTORE o)
/*  587:     */     {
/*  588: 709 */       int idx = o.getIndex();
/*  589: 710 */       if (idx < 0)
/*  590:     */       {
/*  591: 711 */         constraintViolated(o, "Index '" + idx + "' must be non-negative.");
/*  592:     */       }
/*  593:     */       else
/*  594:     */       {
/*  595: 714 */         int maxminus1 = max_locals() - 1;
/*  596: 715 */         if (idx > maxminus1) {
/*  597: 716 */           constraintViolated(o, "Index '" + idx + "' must not be greater than max_locals-1 '" + maxminus1 + "'.");
/*  598:     */         }
/*  599:     */       }
/*  600:     */     }
/*  601:     */     
/*  602:     */     public void visitASTORE(ASTORE o)
/*  603:     */     {
/*  604: 723 */       int idx = o.getIndex();
/*  605: 724 */       if (idx < 0)
/*  606:     */       {
/*  607: 725 */         constraintViolated(o, "Index '" + idx + "' must be non-negative.");
/*  608:     */       }
/*  609:     */       else
/*  610:     */       {
/*  611: 728 */         int maxminus1 = max_locals() - 1;
/*  612: 729 */         if (idx > maxminus1) {
/*  613: 730 */           constraintViolated(o, "Index '" + idx + "' must not be greater than max_locals-1 '" + maxminus1 + "'.");
/*  614:     */         }
/*  615:     */       }
/*  616:     */     }
/*  617:     */     
/*  618:     */     public void visitIINC(IINC o)
/*  619:     */     {
/*  620: 737 */       int idx = o.getIndex();
/*  621: 738 */       if (idx < 0)
/*  622:     */       {
/*  623: 739 */         constraintViolated(o, "Index '" + idx + "' must be non-negative.");
/*  624:     */       }
/*  625:     */       else
/*  626:     */       {
/*  627: 742 */         int maxminus1 = max_locals() - 1;
/*  628: 743 */         if (idx > maxminus1) {
/*  629: 744 */           constraintViolated(o, "Index '" + idx + "' must not be greater than max_locals-1 '" + maxminus1 + "'.");
/*  630:     */         }
/*  631:     */       }
/*  632:     */     }
/*  633:     */     
/*  634:     */     public void visitRET(RET o)
/*  635:     */     {
/*  636: 751 */       int idx = o.getIndex();
/*  637: 752 */       if (idx < 0)
/*  638:     */       {
/*  639: 753 */         constraintViolated(o, "Index '" + idx + "' must be non-negative.");
/*  640:     */       }
/*  641:     */       else
/*  642:     */       {
/*  643: 756 */         int maxminus1 = max_locals() - 1;
/*  644: 757 */         if (idx > maxminus1) {
/*  645: 758 */           constraintViolated(o, "Index '" + idx + "' must not be greater than max_locals-1 '" + maxminus1 + "'.");
/*  646:     */         }
/*  647:     */       }
/*  648:     */     }
/*  649:     */     
/*  650:     */     public void visitLLOAD(LLOAD o)
/*  651:     */     {
/*  652: 765 */       int idx = o.getIndex();
/*  653: 766 */       if (idx < 0)
/*  654:     */       {
/*  655: 767 */         constraintViolated(o, "Index '" + idx + "' must be non-negative. [Constraint by JustIce as an analogon to the single-slot xLOAD/xSTORE instructions; may not happen anyway.]");
/*  656:     */       }
/*  657:     */       else
/*  658:     */       {
/*  659: 770 */         int maxminus2 = max_locals() - 2;
/*  660: 771 */         if (idx > maxminus2) {
/*  661: 772 */           constraintViolated(o, "Index '" + idx + "' must not be greater than max_locals-2 '" + maxminus2 + "'.");
/*  662:     */         }
/*  663:     */       }
/*  664:     */     }
/*  665:     */     
/*  666:     */     public void visitDLOAD(DLOAD o)
/*  667:     */     {
/*  668: 779 */       int idx = o.getIndex();
/*  669: 780 */       if (idx < 0)
/*  670:     */       {
/*  671: 781 */         constraintViolated(o, "Index '" + idx + "' must be non-negative. [Constraint by JustIce as an analogon to the single-slot xLOAD/xSTORE instructions; may not happen anyway.]");
/*  672:     */       }
/*  673:     */       else
/*  674:     */       {
/*  675: 784 */         int maxminus2 = max_locals() - 2;
/*  676: 785 */         if (idx > maxminus2) {
/*  677: 786 */           constraintViolated(o, "Index '" + idx + "' must not be greater than max_locals-2 '" + maxminus2 + "'.");
/*  678:     */         }
/*  679:     */       }
/*  680:     */     }
/*  681:     */     
/*  682:     */     public void visitLSTORE(LSTORE o)
/*  683:     */     {
/*  684: 793 */       int idx = o.getIndex();
/*  685: 794 */       if (idx < 0)
/*  686:     */       {
/*  687: 795 */         constraintViolated(o, "Index '" + idx + "' must be non-negative. [Constraint by JustIce as an analogon to the single-slot xLOAD/xSTORE instructions; may not happen anyway.]");
/*  688:     */       }
/*  689:     */       else
/*  690:     */       {
/*  691: 798 */         int maxminus2 = max_locals() - 2;
/*  692: 799 */         if (idx > maxminus2) {
/*  693: 800 */           constraintViolated(o, "Index '" + idx + "' must not be greater than max_locals-2 '" + maxminus2 + "'.");
/*  694:     */         }
/*  695:     */       }
/*  696:     */     }
/*  697:     */     
/*  698:     */     public void visitDSTORE(DSTORE o)
/*  699:     */     {
/*  700: 807 */       int idx = o.getIndex();
/*  701: 808 */       if (idx < 0)
/*  702:     */       {
/*  703: 809 */         constraintViolated(o, "Index '" + idx + "' must be non-negative. [Constraint by JustIce as an analogon to the single-slot xLOAD/xSTORE instructions; may not happen anyway.]");
/*  704:     */       }
/*  705:     */       else
/*  706:     */       {
/*  707: 812 */         int maxminus2 = max_locals() - 2;
/*  708: 813 */         if (idx > maxminus2) {
/*  709: 814 */           constraintViolated(o, "Index '" + idx + "' must not be greater than max_locals-2 '" + maxminus2 + "'.");
/*  710:     */         }
/*  711:     */       }
/*  712:     */     }
/*  713:     */     
/*  714:     */     public void visitLOOKUPSWITCH(LOOKUPSWITCH o)
/*  715:     */     {
/*  716: 821 */       int[] matchs = o.getMatchs();
/*  717: 822 */       int max = -2147483648;
/*  718: 823 */       for (int i = 0; i < matchs.length; i++)
/*  719:     */       {
/*  720: 824 */         if ((matchs[i] == max) && (i != 0)) {
/*  721: 825 */           constraintViolated(o, "Match '" + matchs[i] + "' occurs more than once.");
/*  722:     */         }
/*  723: 827 */         if (matchs[i] < max) {
/*  724: 828 */           constraintViolated(o, "Lookup table must be sorted but isn't.");
/*  725:     */         } else {
/*  726: 831 */           max = matchs[i];
/*  727:     */         }
/*  728:     */       }
/*  729:     */     }
/*  730:     */     
/*  731:     */     public void visitTABLESWITCH(TABLESWITCH o) {}
/*  732:     */     
/*  733:     */     public void visitPUTSTATIC(PUTSTATIC o)
/*  734:     */     {
/*  735: 844 */       String field_name = o.getFieldName(this.cpg);
/*  736: 845 */       JavaClass jc = Repository.lookupClass(o.getClassType(this.cpg).getClassName());
/*  737: 846 */       Field[] fields = jc.getFields();
/*  738: 847 */       Field f = null;
/*  739: 848 */       for (int i = 0; i < fields.length; i++) {
/*  740: 849 */         if (fields[i].getName().equals(field_name))
/*  741:     */         {
/*  742: 850 */           f = fields[i];
/*  743: 851 */           break;
/*  744:     */         }
/*  745:     */       }
/*  746: 854 */       if (f == null) {
/*  747: 855 */         throw new AssertionViolatedException("Field not found?!?");
/*  748:     */       }
/*  749: 858 */       if ((f.isFinal()) && 
/*  750: 859 */         (!Pass3aVerifier.this.myOwner.getClassName().equals(o.getClassType(this.cpg).getClassName()))) {
/*  751: 860 */         constraintViolated(o, "Referenced field '" + f + "' is final and must therefore be declared in the current class '" + Pass3aVerifier.this.myOwner.getClassName() + "' which is not the case: it is declared in '" + o.getClassType(this.cpg).getClassName() + "'.");
/*  752:     */       }
/*  753: 864 */       if (!f.isStatic()) {
/*  754: 865 */         constraintViolated(o, "Referenced field '" + f + "' is not static which it should be.");
/*  755:     */       }
/*  756: 868 */       String meth_name = Repository.lookupClass(Pass3aVerifier.this.myOwner.getClassName()).getMethods()[Pass3aVerifier.this.method_no].getName();
/*  757: 871 */       if ((!jc.isClass()) && (!meth_name.equals("<clinit>"))) {
/*  758: 872 */         constraintViolated(o, "Interface field '" + f + "' must be set in a '" + "<clinit>" + "' method.");
/*  759:     */       }
/*  760:     */     }
/*  761:     */     
/*  762:     */     public void visitGETSTATIC(GETSTATIC o)
/*  763:     */     {
/*  764: 878 */       String field_name = o.getFieldName(this.cpg);
/*  765: 879 */       JavaClass jc = Repository.lookupClass(o.getClassType(this.cpg).getClassName());
/*  766: 880 */       Field[] fields = jc.getFields();
/*  767: 881 */       Field f = null;
/*  768: 882 */       for (int i = 0; i < fields.length; i++) {
/*  769: 883 */         if (fields[i].getName().equals(field_name))
/*  770:     */         {
/*  771: 884 */           f = fields[i];
/*  772: 885 */           break;
/*  773:     */         }
/*  774:     */       }
/*  775: 888 */       if (f == null) {
/*  776: 889 */         throw new AssertionViolatedException("Field not found?!?");
/*  777:     */       }
/*  778: 892 */       if (!f.isStatic()) {
/*  779: 893 */         constraintViolated(o, "Referenced field '" + f + "' is not static which it should be.");
/*  780:     */       }
/*  781:     */     }
/*  782:     */     
/*  783:     */     public void visitINVOKEINTERFACE(INVOKEINTERFACE o)
/*  784:     */     {
/*  785: 913 */       String classname = o.getClassName(this.cpg);
/*  786: 914 */       JavaClass jc = Repository.lookupClass(classname);
/*  787: 915 */       Method[] ms = jc.getMethods();
/*  788: 916 */       Method m = null;
/*  789: 917 */       for (int i = 0; i < ms.length; i++) {
/*  790: 918 */         if ((ms[i].getName().equals(o.getMethodName(this.cpg))) && (Type.getReturnType(ms[i].getSignature()).equals(o.getReturnType(this.cpg))) && (objarrayequals(Type.getArgumentTypes(ms[i].getSignature()), o.getArgumentTypes(this.cpg))))
/*  791:     */         {
/*  792: 921 */           m = ms[i];
/*  793: 922 */           break;
/*  794:     */         }
/*  795:     */       }
/*  796: 925 */       if (m == null) {
/*  797: 926 */         constraintViolated(o, "Referenced method '" + o.getMethodName(this.cpg) + "' with expected signature not found in class '" + jc.getClassName() + "'. The native verfier does allow the method to be declared in some superinterface, which the Java Virtual Machine Specification, Second Edition does not.");
/*  798:     */       }
/*  799: 928 */       if (jc.isClass()) {
/*  800: 929 */         constraintViolated(o, "Referenced class '" + jc.getClassName() + "' is a class, but not an interface as expected.");
/*  801:     */       }
/*  802:     */     }
/*  803:     */     
/*  804:     */     public void visitINVOKESPECIAL(INVOKESPECIAL o)
/*  805:     */     {
/*  806: 939 */       String classname = o.getClassName(this.cpg);
/*  807: 940 */       JavaClass jc = Repository.lookupClass(classname);
/*  808: 941 */       Method[] ms = jc.getMethods();
/*  809: 942 */       Method m = null;
/*  810: 943 */       for (int i = 0; i < ms.length; i++) {
/*  811: 944 */         if ((ms[i].getName().equals(o.getMethodName(this.cpg))) && (Type.getReturnType(ms[i].getSignature()).equals(o.getReturnType(this.cpg))) && (objarrayequals(Type.getArgumentTypes(ms[i].getSignature()), o.getArgumentTypes(this.cpg))))
/*  812:     */         {
/*  813: 947 */           m = ms[i];
/*  814: 948 */           break;
/*  815:     */         }
/*  816:     */       }
/*  817: 951 */       if (m == null) {
/*  818: 952 */         constraintViolated(o, "Referenced method '" + o.getMethodName(this.cpg) + "' with expected signature not found in class '" + jc.getClassName() + "'. The native verfier does allow the method to be declared in some superclass or implemented interface, which the Java Virtual Machine Specification, Second Edition does not.");
/*  819:     */       }
/*  820: 955 */       JavaClass current = Repository.lookupClass(Pass3aVerifier.this.myOwner.getClassName());
/*  821: 956 */       if (current.isSuper()) {
/*  822: 958 */         if ((Repository.instanceOf(current, jc)) && (!current.equals(jc))) {
/*  823: 960 */           if (!o.getMethodName(this.cpg).equals("<init>"))
/*  824:     */           {
/*  825: 963 */             int supidx = -1;
/*  826:     */             
/*  827: 965 */             Method meth = null;
/*  828: 966 */             while (supidx != 0)
/*  829:     */             {
/*  830: 967 */               supidx = current.getSuperclassNameIndex();
/*  831: 968 */               current = Repository.lookupClass(current.getSuperclassName());
/*  832:     */               
/*  833: 970 */               Method[] meths = current.getMethods();
/*  834: 971 */               for (int i = 0; i < meths.length; i++) {
/*  835: 972 */                 if ((meths[i].getName().equals(o.getMethodName(this.cpg))) && (Type.getReturnType(meths[i].getSignature()).equals(o.getReturnType(this.cpg))) && (objarrayequals(Type.getArgumentTypes(meths[i].getSignature()), o.getArgumentTypes(this.cpg))))
/*  836:     */                 {
/*  837: 975 */                   meth = meths[i];
/*  838: 976 */                   break;
/*  839:     */                 }
/*  840:     */               }
/*  841: 979 */               if (meth != null) {
/*  842:     */                 break;
/*  843:     */               }
/*  844:     */             }
/*  845: 981 */             if (meth == null) {
/*  846: 982 */               constraintViolated(o, "ACC_SUPER special lookup procedure not successful: method '" + o.getMethodName(this.cpg) + "' with proper signature not declared in superclass hierarchy.");
/*  847:     */             }
/*  848:     */           }
/*  849:     */         }
/*  850:     */       }
/*  851:     */     }
/*  852:     */     
/*  853:     */     public void visitINVOKESTATIC(INVOKESTATIC o)
/*  854:     */     {
/*  855: 997 */       String classname = o.getClassName(this.cpg);
/*  856: 998 */       JavaClass jc = Repository.lookupClass(classname);
/*  857: 999 */       Method[] ms = jc.getMethods();
/*  858:1000 */       Method m = null;
/*  859:1001 */       for (int i = 0; i < ms.length; i++) {
/*  860:1002 */         if ((ms[i].getName().equals(o.getMethodName(this.cpg))) && (Type.getReturnType(ms[i].getSignature()).equals(o.getReturnType(this.cpg))) && (objarrayequals(Type.getArgumentTypes(ms[i].getSignature()), o.getArgumentTypes(this.cpg))))
/*  861:     */         {
/*  862:1005 */           m = ms[i];
/*  863:1006 */           break;
/*  864:     */         }
/*  865:     */       }
/*  866:1009 */       if (m == null) {
/*  867:1010 */         constraintViolated(o, "Referenced method '" + o.getMethodName(this.cpg) + "' with expected signature not found in class '" + jc.getClassName() + "'. The native verifier possibly allows the method to be declared in some superclass or implemented interface, which the Java Virtual Machine Specification, Second Edition does not.");
/*  868:     */       }
/*  869:1013 */       if (!m.isStatic()) {
/*  870:1014 */         constraintViolated(o, "Referenced method '" + o.getMethodName(this.cpg) + "' has ACC_STATIC unset.");
/*  871:     */       }
/*  872:     */     }
/*  873:     */     
/*  874:     */     public void visitINVOKEVIRTUAL(INVOKEVIRTUAL o)
/*  875:     */     {
/*  876:1026 */       String classname = o.getClassName(this.cpg);
/*  877:1027 */       JavaClass jc = Repository.lookupClass(classname);
/*  878:1028 */       Method[] ms = jc.getMethods();
/*  879:1029 */       Method m = null;
/*  880:1030 */       for (int i = 0; i < ms.length; i++) {
/*  881:1031 */         if ((ms[i].getName().equals(o.getMethodName(this.cpg))) && (Type.getReturnType(ms[i].getSignature()).equals(o.getReturnType(this.cpg))) && (objarrayequals(Type.getArgumentTypes(ms[i].getSignature()), o.getArgumentTypes(this.cpg))))
/*  882:     */         {
/*  883:1034 */           m = ms[i];
/*  884:1035 */           break;
/*  885:     */         }
/*  886:     */       }
/*  887:1038 */       if (m == null) {
/*  888:1039 */         constraintViolated(o, "Referenced method '" + o.getMethodName(this.cpg) + "' with expected signature not found in class '" + jc.getClassName() + "'. The native verfier does allow the method to be declared in some superclass or implemented interface, which the Java Virtual Machine Specification, Second Edition does not.");
/*  889:     */       }
/*  890:1041 */       if (!jc.isClass()) {
/*  891:1042 */         constraintViolated(o, "Referenced class '" + jc.getClassName() + "' is an interface, but not a class as expected.");
/*  892:     */       }
/*  893:     */     }
/*  894:     */     
/*  895:     */     private boolean objarrayequals(Object[] o, Object[] p)
/*  896:     */     {
/*  897:1056 */       if (o.length != p.length) {
/*  898:1057 */         return false;
/*  899:     */       }
/*  900:1060 */       for (int i = 0; i < o.length; i++) {
/*  901:1061 */         if (!o[i].equals(p[i])) {
/*  902:1062 */           return false;
/*  903:     */         }
/*  904:     */       }
/*  905:1066 */       return true;
/*  906:     */     }
/*  907:     */   }
/*  908:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.verifier.statics.Pass3aVerifier
 * JD-Core Version:    0.7.0.1
 */