/*    1:     */ package org.apache.bcel.verifier.structurals;
/*    2:     */ 
/*    3:     */ import org.apache.bcel.Repository;
/*    4:     */ import org.apache.bcel.classfile.AccessFlags;
/*    5:     */ import org.apache.bcel.classfile.Constant;
/*    6:     */ import org.apache.bcel.classfile.ConstantClass;
/*    7:     */ import org.apache.bcel.classfile.ConstantDouble;
/*    8:     */ import org.apache.bcel.classfile.ConstantFieldref;
/*    9:     */ import org.apache.bcel.classfile.ConstantFloat;
/*   10:     */ import org.apache.bcel.classfile.ConstantInteger;
/*   11:     */ import org.apache.bcel.classfile.ConstantInterfaceMethodref;
/*   12:     */ import org.apache.bcel.classfile.ConstantLong;
/*   13:     */ import org.apache.bcel.classfile.ConstantString;
/*   14:     */ import org.apache.bcel.classfile.Field;
/*   15:     */ import org.apache.bcel.classfile.JavaClass;
/*   16:     */ import org.apache.bcel.generic.AALOAD;
/*   17:     */ import org.apache.bcel.generic.AASTORE;
/*   18:     */ import org.apache.bcel.generic.ACONST_NULL;
/*   19:     */ import org.apache.bcel.generic.ALOAD;
/*   20:     */ import org.apache.bcel.generic.ANEWARRAY;
/*   21:     */ import org.apache.bcel.generic.ARETURN;
/*   22:     */ import org.apache.bcel.generic.ARRAYLENGTH;
/*   23:     */ import org.apache.bcel.generic.ASTORE;
/*   24:     */ import org.apache.bcel.generic.ATHROW;
/*   25:     */ import org.apache.bcel.generic.ArrayType;
/*   26:     */ import org.apache.bcel.generic.BALOAD;
/*   27:     */ import org.apache.bcel.generic.BASTORE;
/*   28:     */ import org.apache.bcel.generic.BIPUSH;
/*   29:     */ import org.apache.bcel.generic.BREAKPOINT;
/*   30:     */ import org.apache.bcel.generic.CALOAD;
/*   31:     */ import org.apache.bcel.generic.CASTORE;
/*   32:     */ import org.apache.bcel.generic.CHECKCAST;
/*   33:     */ import org.apache.bcel.generic.CPInstruction;
/*   34:     */ import org.apache.bcel.generic.ConstantPoolGen;
/*   35:     */ import org.apache.bcel.generic.D2F;
/*   36:     */ import org.apache.bcel.generic.D2I;
/*   37:     */ import org.apache.bcel.generic.D2L;
/*   38:     */ import org.apache.bcel.generic.DADD;
/*   39:     */ import org.apache.bcel.generic.DALOAD;
/*   40:     */ import org.apache.bcel.generic.DASTORE;
/*   41:     */ import org.apache.bcel.generic.DCMPG;
/*   42:     */ import org.apache.bcel.generic.DCMPL;
/*   43:     */ import org.apache.bcel.generic.DCONST;
/*   44:     */ import org.apache.bcel.generic.DDIV;
/*   45:     */ import org.apache.bcel.generic.DLOAD;
/*   46:     */ import org.apache.bcel.generic.DMUL;
/*   47:     */ import org.apache.bcel.generic.DNEG;
/*   48:     */ import org.apache.bcel.generic.DREM;
/*   49:     */ import org.apache.bcel.generic.DRETURN;
/*   50:     */ import org.apache.bcel.generic.DSTORE;
/*   51:     */ import org.apache.bcel.generic.DSUB;
/*   52:     */ import org.apache.bcel.generic.DUP;
/*   53:     */ import org.apache.bcel.generic.DUP2;
/*   54:     */ import org.apache.bcel.generic.DUP2_X1;
/*   55:     */ import org.apache.bcel.generic.DUP2_X2;
/*   56:     */ import org.apache.bcel.generic.DUP_X1;
/*   57:     */ import org.apache.bcel.generic.DUP_X2;
/*   58:     */ import org.apache.bcel.generic.EmptyVisitor;
/*   59:     */ import org.apache.bcel.generic.F2D;
/*   60:     */ import org.apache.bcel.generic.F2I;
/*   61:     */ import org.apache.bcel.generic.F2L;
/*   62:     */ import org.apache.bcel.generic.FADD;
/*   63:     */ import org.apache.bcel.generic.FALOAD;
/*   64:     */ import org.apache.bcel.generic.FASTORE;
/*   65:     */ import org.apache.bcel.generic.FCMPG;
/*   66:     */ import org.apache.bcel.generic.FCMPL;
/*   67:     */ import org.apache.bcel.generic.FCONST;
/*   68:     */ import org.apache.bcel.generic.FDIV;
/*   69:     */ import org.apache.bcel.generic.FLOAD;
/*   70:     */ import org.apache.bcel.generic.FMUL;
/*   71:     */ import org.apache.bcel.generic.FNEG;
/*   72:     */ import org.apache.bcel.generic.FREM;
/*   73:     */ import org.apache.bcel.generic.FRETURN;
/*   74:     */ import org.apache.bcel.generic.FSTORE;
/*   75:     */ import org.apache.bcel.generic.FSUB;
/*   76:     */ import org.apache.bcel.generic.FieldGenOrMethodGen;
/*   77:     */ import org.apache.bcel.generic.FieldInstruction;
/*   78:     */ import org.apache.bcel.generic.GETFIELD;
/*   79:     */ import org.apache.bcel.generic.GETSTATIC;
/*   80:     */ import org.apache.bcel.generic.GOTO;
/*   81:     */ import org.apache.bcel.generic.GOTO_W;
/*   82:     */ import org.apache.bcel.generic.I2B;
/*   83:     */ import org.apache.bcel.generic.I2C;
/*   84:     */ import org.apache.bcel.generic.I2D;
/*   85:     */ import org.apache.bcel.generic.I2F;
/*   86:     */ import org.apache.bcel.generic.I2L;
/*   87:     */ import org.apache.bcel.generic.I2S;
/*   88:     */ import org.apache.bcel.generic.IADD;
/*   89:     */ import org.apache.bcel.generic.IALOAD;
/*   90:     */ import org.apache.bcel.generic.IAND;
/*   91:     */ import org.apache.bcel.generic.IASTORE;
/*   92:     */ import org.apache.bcel.generic.ICONST;
/*   93:     */ import org.apache.bcel.generic.IDIV;
/*   94:     */ import org.apache.bcel.generic.IFEQ;
/*   95:     */ import org.apache.bcel.generic.IFGE;
/*   96:     */ import org.apache.bcel.generic.IFGT;
/*   97:     */ import org.apache.bcel.generic.IFLE;
/*   98:     */ import org.apache.bcel.generic.IFLT;
/*   99:     */ import org.apache.bcel.generic.IFNE;
/*  100:     */ import org.apache.bcel.generic.IFNONNULL;
/*  101:     */ import org.apache.bcel.generic.IFNULL;
/*  102:     */ import org.apache.bcel.generic.IF_ACMPEQ;
/*  103:     */ import org.apache.bcel.generic.IF_ACMPNE;
/*  104:     */ import org.apache.bcel.generic.IF_ICMPEQ;
/*  105:     */ import org.apache.bcel.generic.IF_ICMPGE;
/*  106:     */ import org.apache.bcel.generic.IF_ICMPGT;
/*  107:     */ import org.apache.bcel.generic.IF_ICMPLE;
/*  108:     */ import org.apache.bcel.generic.IF_ICMPLT;
/*  109:     */ import org.apache.bcel.generic.IF_ICMPNE;
/*  110:     */ import org.apache.bcel.generic.IINC;
/*  111:     */ import org.apache.bcel.generic.ILOAD;
/*  112:     */ import org.apache.bcel.generic.IMPDEP1;
/*  113:     */ import org.apache.bcel.generic.IMPDEP2;
/*  114:     */ import org.apache.bcel.generic.IMUL;
/*  115:     */ import org.apache.bcel.generic.INEG;
/*  116:     */ import org.apache.bcel.generic.INSTANCEOF;
/*  117:     */ import org.apache.bcel.generic.INVOKEINTERFACE;
/*  118:     */ import org.apache.bcel.generic.INVOKESPECIAL;
/*  119:     */ import org.apache.bcel.generic.INVOKESTATIC;
/*  120:     */ import org.apache.bcel.generic.INVOKEVIRTUAL;
/*  121:     */ import org.apache.bcel.generic.IOR;
/*  122:     */ import org.apache.bcel.generic.IREM;
/*  123:     */ import org.apache.bcel.generic.IRETURN;
/*  124:     */ import org.apache.bcel.generic.ISHL;
/*  125:     */ import org.apache.bcel.generic.ISHR;
/*  126:     */ import org.apache.bcel.generic.ISTORE;
/*  127:     */ import org.apache.bcel.generic.ISUB;
/*  128:     */ import org.apache.bcel.generic.IUSHR;
/*  129:     */ import org.apache.bcel.generic.IXOR;
/*  130:     */ import org.apache.bcel.generic.Instruction;
/*  131:     */ import org.apache.bcel.generic.InvokeInstruction;
/*  132:     */ import org.apache.bcel.generic.JSR;
/*  133:     */ import org.apache.bcel.generic.JSR_W;
/*  134:     */ import org.apache.bcel.generic.L2D;
/*  135:     */ import org.apache.bcel.generic.L2F;
/*  136:     */ import org.apache.bcel.generic.L2I;
/*  137:     */ import org.apache.bcel.generic.LADD;
/*  138:     */ import org.apache.bcel.generic.LALOAD;
/*  139:     */ import org.apache.bcel.generic.LAND;
/*  140:     */ import org.apache.bcel.generic.LASTORE;
/*  141:     */ import org.apache.bcel.generic.LCMP;
/*  142:     */ import org.apache.bcel.generic.LCONST;
/*  143:     */ import org.apache.bcel.generic.LDC;
/*  144:     */ import org.apache.bcel.generic.LDC2_W;
/*  145:     */ import org.apache.bcel.generic.LDC_W;
/*  146:     */ import org.apache.bcel.generic.LDIV;
/*  147:     */ import org.apache.bcel.generic.LLOAD;
/*  148:     */ import org.apache.bcel.generic.LMUL;
/*  149:     */ import org.apache.bcel.generic.LNEG;
/*  150:     */ import org.apache.bcel.generic.LOOKUPSWITCH;
/*  151:     */ import org.apache.bcel.generic.LOR;
/*  152:     */ import org.apache.bcel.generic.LREM;
/*  153:     */ import org.apache.bcel.generic.LRETURN;
/*  154:     */ import org.apache.bcel.generic.LSHL;
/*  155:     */ import org.apache.bcel.generic.LSHR;
/*  156:     */ import org.apache.bcel.generic.LSTORE;
/*  157:     */ import org.apache.bcel.generic.LSUB;
/*  158:     */ import org.apache.bcel.generic.LUSHR;
/*  159:     */ import org.apache.bcel.generic.LXOR;
/*  160:     */ import org.apache.bcel.generic.LoadClass;
/*  161:     */ import org.apache.bcel.generic.LoadInstruction;
/*  162:     */ import org.apache.bcel.generic.LocalVariableInstruction;
/*  163:     */ import org.apache.bcel.generic.MONITORENTER;
/*  164:     */ import org.apache.bcel.generic.MONITOREXIT;
/*  165:     */ import org.apache.bcel.generic.MULTIANEWARRAY;
/*  166:     */ import org.apache.bcel.generic.MethodGen;
/*  167:     */ import org.apache.bcel.generic.NEW;
/*  168:     */ import org.apache.bcel.generic.NEWARRAY;
/*  169:     */ import org.apache.bcel.generic.NOP;
/*  170:     */ import org.apache.bcel.generic.ObjectType;
/*  171:     */ import org.apache.bcel.generic.POP;
/*  172:     */ import org.apache.bcel.generic.POP2;
/*  173:     */ import org.apache.bcel.generic.PUTFIELD;
/*  174:     */ import org.apache.bcel.generic.PUTSTATIC;
/*  175:     */ import org.apache.bcel.generic.RET;
/*  176:     */ import org.apache.bcel.generic.RETURN;
/*  177:     */ import org.apache.bcel.generic.ReferenceType;
/*  178:     */ import org.apache.bcel.generic.ReturnInstruction;
/*  179:     */ import org.apache.bcel.generic.ReturnaddressType;
/*  180:     */ import org.apache.bcel.generic.SALOAD;
/*  181:     */ import org.apache.bcel.generic.SASTORE;
/*  182:     */ import org.apache.bcel.generic.SIPUSH;
/*  183:     */ import org.apache.bcel.generic.SWAP;
/*  184:     */ import org.apache.bcel.generic.StackConsumer;
/*  185:     */ import org.apache.bcel.generic.StackInstruction;
/*  186:     */ import org.apache.bcel.generic.StackProducer;
/*  187:     */ import org.apache.bcel.generic.StoreInstruction;
/*  188:     */ import org.apache.bcel.generic.TABLESWITCH;
/*  189:     */ import org.apache.bcel.generic.Type;
/*  190:     */ import org.apache.bcel.generic.Visitor;
/*  191:     */ import org.apache.bcel.verifier.VerificationResult;
/*  192:     */ import org.apache.bcel.verifier.Verifier;
/*  193:     */ import org.apache.bcel.verifier.VerifierFactory;
/*  194:     */ import org.apache.bcel.verifier.exc.AssertionViolatedException;
/*  195:     */ import org.apache.bcel.verifier.exc.StructuralCodeConstraintException;
/*  196:     */ 
/*  197:     */ public class InstConstraintVisitor
/*  198:     */   extends EmptyVisitor
/*  199:     */   implements Visitor
/*  200:     */ {
/*  201:  94 */   private static ObjectType GENERIC_ARRAY = new ObjectType("org.apache.bcel.verifier.structurals.GenericArray");
/*  202: 108 */   private Frame frame = null;
/*  203: 115 */   private ConstantPoolGen cpg = null;
/*  204: 122 */   private MethodGen mg = null;
/*  205:     */   
/*  206:     */   private OperandStack stack()
/*  207:     */   {
/*  208: 130 */     return this.frame.getStack();
/*  209:     */   }
/*  210:     */   
/*  211:     */   private LocalVariables locals()
/*  212:     */   {
/*  213: 139 */     return this.frame.getLocals();
/*  214:     */   }
/*  215:     */   
/*  216:     */   private void constraintViolated(Instruction violator, String description)
/*  217:     */   {
/*  218: 149 */     String fq_classname = violator.getClass().getName();
/*  219: 150 */     throw new StructuralCodeConstraintException("Instruction " + fq_classname.substring(fq_classname.lastIndexOf('.') + 1) + " constraint violated: " + description);
/*  220:     */   }
/*  221:     */   
/*  222:     */   public void setFrame(Frame f)
/*  223:     */   {
/*  224: 163 */     this.frame = f;
/*  225:     */   }
/*  226:     */   
/*  227:     */   public void setConstantPoolGen(ConstantPoolGen cpg)
/*  228:     */   {
/*  229: 172 */     this.cpg = cpg;
/*  230:     */   }
/*  231:     */   
/*  232:     */   public void setMethodGen(MethodGen mg)
/*  233:     */   {
/*  234: 180 */     this.mg = mg;
/*  235:     */   }
/*  236:     */   
/*  237:     */   private void indexOfInt(Instruction o, Type index)
/*  238:     */   {
/*  239: 188 */     if (!index.equals(Type.INT)) {
/*  240: 189 */       constraintViolated(o, "The 'index' is not of type int but of type " + index + ".");
/*  241:     */     }
/*  242:     */   }
/*  243:     */   
/*  244:     */   private void referenceTypeIsInitialized(Instruction o, ReferenceType r)
/*  245:     */   {
/*  246: 199 */     if ((r instanceof UninitializedObjectType)) {
/*  247: 200 */       constraintViolated(o, "Working on an uninitialized object '" + r + "'.");
/*  248:     */     }
/*  249:     */   }
/*  250:     */   
/*  251:     */   private void valueOfInt(Instruction o, Type value)
/*  252:     */   {
/*  253: 206 */     if (!value.equals(Type.INT)) {
/*  254: 207 */       constraintViolated(o, "The 'value' is not of type int but of type " + value + ".");
/*  255:     */     }
/*  256:     */   }
/*  257:     */   
/*  258:     */   private boolean arrayrefOfArrayType(Instruction o, Type arrayref)
/*  259:     */   {
/*  260: 216 */     if ((!(arrayref instanceof ArrayType)) && (!arrayref.equals(Type.NULL))) {
/*  261: 217 */       constraintViolated(o, "The 'arrayref' does not refer to an array but is of type " + arrayref + ".");
/*  262:     */     }
/*  263: 218 */     return arrayref instanceof ArrayType;
/*  264:     */   }
/*  265:     */   
/*  266:     */   private void _visitStackAccessor(Instruction o)
/*  267:     */   {
/*  268: 240 */     int consume = o.consumeStack(this.cpg);
/*  269: 241 */     if (consume > stack().slotsUsed()) {
/*  270: 242 */       constraintViolated(o, "Cannot consume " + consume + " stack slots: only " + stack().slotsUsed() + " slot(s) left on stack!\nStack:\n" + stack());
/*  271:     */     }
/*  272: 245 */     int produce = o.produceStack(this.cpg) - o.consumeStack(this.cpg);
/*  273: 246 */     if (produce + stack().slotsUsed() > stack().maxStack()) {
/*  274: 247 */       constraintViolated(o, "Cannot produce " + produce + " stack slots: only " + (stack().maxStack() - stack().slotsUsed()) + " free stack slot(s) left.\nStack:\n" + stack());
/*  275:     */     }
/*  276:     */   }
/*  277:     */   
/*  278:     */   public void visitLoadClass(LoadClass o)
/*  279:     */   {
/*  280: 262 */     ObjectType t = o.getLoadClassType(this.cpg);
/*  281: 263 */     if (t != null)
/*  282:     */     {
/*  283: 264 */       Verifier v = VerifierFactory.getVerifier(t.getClassName());
/*  284: 265 */       VerificationResult vr = v.doPass2();
/*  285: 266 */       if (vr.getStatus() != 1) {
/*  286: 267 */         constraintViolated((Instruction)o, "Class '" + o.getLoadClassType(this.cpg).getClassName() + "' is referenced, but cannot be loaded and resolved: '" + vr + "'.");
/*  287:     */       }
/*  288:     */     }
/*  289:     */   }
/*  290:     */   
/*  291:     */   public void visitStackConsumer(StackConsumer o)
/*  292:     */   {
/*  293: 276 */     _visitStackAccessor((Instruction)o);
/*  294:     */   }
/*  295:     */   
/*  296:     */   public void visitStackProducer(StackProducer o)
/*  297:     */   {
/*  298: 283 */     _visitStackAccessor((Instruction)o);
/*  299:     */   }
/*  300:     */   
/*  301:     */   public void visitCPInstruction(CPInstruction o)
/*  302:     */   {
/*  303: 296 */     int idx = o.getIndex();
/*  304: 297 */     if ((idx < 0) || (idx >= this.cpg.getSize())) {
/*  305: 298 */       throw new AssertionViolatedException("Huh?! Constant pool index of instruction '" + o + "' illegal? Pass 3a should have checked this!");
/*  306:     */     }
/*  307:     */   }
/*  308:     */   
/*  309:     */   public void visitFieldInstruction(FieldInstruction o)
/*  310:     */   {
/*  311: 310 */     Constant c = this.cpg.getConstant(o.getIndex());
/*  312: 311 */     if (!(c instanceof ConstantFieldref)) {
/*  313: 312 */       constraintViolated(o, "Index '" + o.getIndex() + "' should refer to a CONSTANT_Fieldref_info structure, but refers to '" + c + "'.");
/*  314:     */     }
/*  315: 315 */     Type t = o.getType(this.cpg);
/*  316: 316 */     if ((t instanceof ObjectType))
/*  317:     */     {
/*  318: 317 */       String name = ((ObjectType)t).getClassName();
/*  319: 318 */       Verifier v = VerifierFactory.getVerifier(name);
/*  320: 319 */       VerificationResult vr = v.doPass2();
/*  321: 320 */       if (vr.getStatus() != 1) {
/*  322: 321 */         constraintViolated(o, "Class '" + name + "' is referenced, but cannot be loaded and resolved: '" + vr + "'.");
/*  323:     */       }
/*  324:     */     }
/*  325:     */   }
/*  326:     */   
/*  327:     */   public void visitInvokeInstruction(InvokeInstruction o) {}
/*  328:     */   
/*  329:     */   public void visitStackInstruction(StackInstruction o)
/*  330:     */   {
/*  331: 340 */     _visitStackAccessor(o);
/*  332:     */   }
/*  333:     */   
/*  334:     */   public void visitLocalVariableInstruction(LocalVariableInstruction o)
/*  335:     */   {
/*  336: 348 */     if (locals().maxLocals() <= (o.getType(this.cpg).getSize() == 1 ? o.getIndex() : o.getIndex() + 1)) {
/*  337: 349 */       constraintViolated(o, "The 'index' is not a valid index into the local variable array.");
/*  338:     */     }
/*  339:     */   }
/*  340:     */   
/*  341:     */   public void visitLoadInstruction(LoadInstruction o)
/*  342:     */   {
/*  343: 360 */     if (locals().get(o.getIndex()) == Type.UNKNOWN) {
/*  344: 361 */       constraintViolated(o, "Read-Access on local variable " + o.getIndex() + " with unknown content.");
/*  345:     */     }
/*  346: 367 */     if ((o.getType(this.cpg).getSize() == 2) && 
/*  347: 368 */       (locals().get(o.getIndex() + 1) != Type.UNKNOWN)) {
/*  348: 369 */       constraintViolated(o, "Reading a two-locals value from local variables " + o.getIndex() + " and " + (o.getIndex() + 1) + " where the latter one is destroyed.");
/*  349:     */     }
/*  350: 374 */     if (!(o instanceof ALOAD))
/*  351:     */     {
/*  352: 375 */       if (locals().get(o.getIndex()) != o.getType(this.cpg)) {
/*  353: 376 */         constraintViolated(o, "Local Variable type and LOADing Instruction type mismatch: Local Variable: '" + locals().get(o.getIndex()) + "'; Instruction type: '" + o.getType(this.cpg) + "'.");
/*  354:     */       }
/*  355:     */     }
/*  356: 380 */     else if (!(locals().get(o.getIndex()) instanceof ReferenceType)) {
/*  357: 381 */       constraintViolated(o, "Local Variable type and LOADing Instruction type mismatch: Local Variable: '" + locals().get(o.getIndex()) + "'; Instruction expects a ReferenceType.");
/*  358:     */     }
/*  359: 388 */     if (stack().maxStack() - stack().slotsUsed() < o.getType(this.cpg).getSize()) {
/*  360: 389 */       constraintViolated(o, "Not enough free stack slots to load a '" + o.getType(this.cpg) + "' onto the OperandStack.");
/*  361:     */     }
/*  362:     */   }
/*  363:     */   
/*  364:     */   public void visitStoreInstruction(StoreInstruction o)
/*  365:     */   {
/*  366: 399 */     if (stack().isEmpty()) {
/*  367: 400 */       constraintViolated(o, "Cannot STORE: Stack to read from is empty.");
/*  368:     */     }
/*  369: 403 */     if (!(o instanceof ASTORE))
/*  370:     */     {
/*  371: 404 */       if (stack().peek() != o.getType(this.cpg)) {
/*  372: 405 */         constraintViolated(o, "Stack top type and STOREing Instruction type mismatch: Stack top: '" + stack().peek() + "'; Instruction type: '" + o.getType(this.cpg) + "'.");
/*  373:     */       }
/*  374:     */     }
/*  375:     */     else
/*  376:     */     {
/*  377: 409 */       Type stacktop = stack().peek();
/*  378: 410 */       if ((!(stacktop instanceof ReferenceType)) && (!(stacktop instanceof ReturnaddressType))) {
/*  379: 411 */         constraintViolated(o, "Stack top type and STOREing Instruction type mismatch: Stack top: '" + stack().peek() + "'; Instruction expects a ReferenceType or a ReturnadressType.");
/*  380:     */       }
/*  381: 413 */       if ((stacktop instanceof ReferenceType)) {
/*  382: 414 */         referenceTypeIsInitialized(o, (ReferenceType)stacktop);
/*  383:     */       }
/*  384:     */     }
/*  385:     */   }
/*  386:     */   
/*  387:     */   public void visitReturnInstruction(ReturnInstruction o)
/*  388:     */   {
/*  389: 423 */     if ((o instanceof RETURN)) {
/*  390:     */       return;
/*  391:     */     }
/*  392:     */     ReferenceType objectref;
/*  393: 426 */     if ((o instanceof ARETURN))
/*  394:     */     {
/*  395: 427 */       if (stack().peek() == Type.NULL) {
/*  396: 428 */         return;
/*  397:     */       }
/*  398: 431 */       if (!(stack().peek() instanceof ReferenceType)) {
/*  399: 432 */         constraintViolated(o, "Reference type expected on top of stack, but is: '" + stack().peek() + "'.");
/*  400:     */       }
/*  401: 434 */       referenceTypeIsInitialized(o, (ReferenceType)stack().peek());
/*  402: 435 */       objectref = (ReferenceType)stack().peek();
/*  403:     */     }
/*  404:     */     else
/*  405:     */     {
/*  406: 444 */       Type method_type = this.mg.getType();
/*  407: 445 */       if ((method_type == Type.BOOLEAN) || (method_type == Type.BYTE) || (method_type == Type.SHORT) || (method_type == Type.CHAR)) {
/*  408: 449 */         method_type = Type.INT;
/*  409:     */       }
/*  410: 451 */       if (!method_type.equals(stack().peek())) {
/*  411: 452 */         constraintViolated(o, "Current method has return type of '" + this.mg.getType() + "' expecting a '" + method_type + "' on top of the stack. But stack top is a '" + stack().peek() + "'.");
/*  412:     */       }
/*  413:     */     }
/*  414:     */   }
/*  415:     */   
/*  416:     */   public void visitAALOAD(AALOAD o)
/*  417:     */   {
/*  418: 465 */     Type arrayref = stack().peek(1);
/*  419: 466 */     Type index = stack().peek(0);
/*  420:     */     
/*  421: 468 */     indexOfInt(o, index);
/*  422: 469 */     if (arrayrefOfArrayType(o, arrayref))
/*  423:     */     {
/*  424: 470 */       if (!(((ArrayType)arrayref).getElementType() instanceof ReferenceType)) {
/*  425: 471 */         constraintViolated(o, "The 'arrayref' does not refer to an array with elements of a ReferenceType but to an array of " + ((ArrayType)arrayref).getElementType() + ".");
/*  426:     */       }
/*  427: 473 */       referenceTypeIsInitialized(o, (ReferenceType)((ArrayType)arrayref).getElementType());
/*  428:     */     }
/*  429:     */   }
/*  430:     */   
/*  431:     */   public void visitAASTORE(AASTORE o)
/*  432:     */   {
/*  433: 481 */     Type arrayref = stack().peek(2);
/*  434: 482 */     Type index = stack().peek(1);
/*  435: 483 */     Type value = stack().peek(0);
/*  436:     */     
/*  437: 485 */     indexOfInt(o, index);
/*  438: 486 */     if (!(value instanceof ReferenceType)) {
/*  439: 487 */       constraintViolated(o, "The 'value' is not of a ReferenceType but of type " + value + ".");
/*  440:     */     } else {
/*  441: 489 */       referenceTypeIsInitialized(o, (ReferenceType)value);
/*  442:     */     }
/*  443: 493 */     if (arrayrefOfArrayType(o, arrayref))
/*  444:     */     {
/*  445: 494 */       if (!(((ArrayType)arrayref).getElementType() instanceof ReferenceType)) {
/*  446: 495 */         constraintViolated(o, "The 'arrayref' does not refer to an array with elements of a ReferenceType but to an array of " + ((ArrayType)arrayref).getElementType() + ".");
/*  447:     */       }
/*  448: 497 */       if (!((ReferenceType)value).isAssignmentCompatibleWith((ReferenceType)((ArrayType)arrayref).getElementType())) {
/*  449: 498 */         constraintViolated(o, "The type of 'value' ('" + value + "') is not assignment compatible to the components of the array 'arrayref' refers to. ('" + ((ArrayType)arrayref).getElementType() + "')");
/*  450:     */       }
/*  451:     */     }
/*  452:     */   }
/*  453:     */   
/*  454:     */   public void visitACONST_NULL(ACONST_NULL o) {}
/*  455:     */   
/*  456:     */   public void visitALOAD(ALOAD o) {}
/*  457:     */   
/*  458:     */   public void visitANEWARRAY(ANEWARRAY o)
/*  459:     */   {
/*  460: 523 */     if (!stack().peek().equals(Type.INT)) {
/*  461: 524 */       constraintViolated(o, "The 'count' at the stack top is not of type '" + Type.INT + "' but of type '" + stack().peek() + "'.");
/*  462:     */     }
/*  463:     */   }
/*  464:     */   
/*  465:     */   public void visitARETURN(ARETURN o)
/*  466:     */   {
/*  467: 533 */     if (!(stack().peek() instanceof ReferenceType)) {
/*  468: 534 */       constraintViolated(o, "The 'objectref' at the stack top is not of a ReferenceType but of type '" + stack().peek() + "'.");
/*  469:     */     }
/*  470: 536 */     ReferenceType objectref = (ReferenceType)stack().peek();
/*  471: 537 */     referenceTypeIsInitialized(o, objectref);
/*  472:     */   }
/*  473:     */   
/*  474:     */   public void visitARRAYLENGTH(ARRAYLENGTH o)
/*  475:     */   {
/*  476: 551 */     Type arrayref = stack().peek(0);
/*  477: 552 */     arrayrefOfArrayType(o, arrayref);
/*  478:     */   }
/*  479:     */   
/*  480:     */   public void visitASTORE(ASTORE o)
/*  481:     */   {
/*  482: 559 */     if ((!(stack().peek() instanceof ReferenceType)) && (!(stack().peek() instanceof ReturnaddressType))) {
/*  483: 560 */       constraintViolated(o, "The 'objectref' is not of a ReferenceType or of ReturnaddressType but of " + stack().peek() + ".");
/*  484:     */     }
/*  485: 562 */     if ((stack().peek() instanceof ReferenceType)) {
/*  486: 563 */       referenceTypeIsInitialized(o, (ReferenceType)stack().peek());
/*  487:     */     }
/*  488:     */   }
/*  489:     */   
/*  490:     */   public void visitATHROW(ATHROW o)
/*  491:     */   {
/*  492: 573 */     if ((!(stack().peek() instanceof ObjectType)) && (!stack().peek().equals(Type.NULL))) {
/*  493: 574 */       constraintViolated(o, "The 'objectref' is not of an (initialized) ObjectType but of type " + stack().peek() + ".");
/*  494:     */     }
/*  495: 578 */     if (stack().peek().equals(Type.NULL)) {
/*  496: 578 */       return;
/*  497:     */     }
/*  498: 580 */     ObjectType exc = (ObjectType)stack().peek();
/*  499: 581 */     ObjectType throwable = (ObjectType)Type.getType("Ljava/lang/Throwable;");
/*  500: 582 */     if ((!exc.subclassOf(throwable)) && (!exc.equals(throwable))) {
/*  501: 583 */       constraintViolated(o, "The 'objectref' is not of class Throwable or of a subclass of Throwable, but of '" + stack().peek() + "'.");
/*  502:     */     }
/*  503:     */   }
/*  504:     */   
/*  505:     */   public void visitBALOAD(BALOAD o)
/*  506:     */   {
/*  507: 591 */     Type arrayref = stack().peek(1);
/*  508: 592 */     Type index = stack().peek(0);
/*  509: 593 */     indexOfInt(o, index);
/*  510: 594 */     if ((arrayrefOfArrayType(o, arrayref)) && 
/*  511: 595 */       (!((ArrayType)arrayref).getElementType().equals(Type.BOOLEAN)) && (!((ArrayType)arrayref).getElementType().equals(Type.BYTE))) {
/*  512: 597 */       constraintViolated(o, "The 'arrayref' does not refer to an array with elements of a Type.BYTE or Type.BOOLEAN but to an array of '" + ((ArrayType)arrayref).getElementType() + "'.");
/*  513:     */     }
/*  514:     */   }
/*  515:     */   
/*  516:     */   public void visitBASTORE(BASTORE o)
/*  517:     */   {
/*  518: 606 */     Type arrayref = stack().peek(2);
/*  519: 607 */     Type index = stack().peek(1);
/*  520: 608 */     Type value = stack().peek(0);
/*  521:     */     
/*  522: 610 */     indexOfInt(o, index);
/*  523: 611 */     valueOfInt(o, index);
/*  524: 612 */     if ((arrayrefOfArrayType(o, arrayref)) && 
/*  525: 613 */       (!((ArrayType)arrayref).getElementType().equals(Type.BOOLEAN)) && (!((ArrayType)arrayref).getElementType().equals(Type.BYTE))) {
/*  526: 615 */       constraintViolated(o, "The 'arrayref' does not refer to an array with elements of a Type.BYTE or Type.BOOLEAN but to an array of '" + ((ArrayType)arrayref).getElementType() + "'.");
/*  527:     */     }
/*  528:     */   }
/*  529:     */   
/*  530:     */   public void visitBIPUSH(BIPUSH o) {}
/*  531:     */   
/*  532:     */   public void visitBREAKPOINT(BREAKPOINT o)
/*  533:     */   {
/*  534: 630 */     throw new AssertionViolatedException("In this JustIce verification pass there should not occur an illegal instruction such as BREAKPOINT.");
/*  535:     */   }
/*  536:     */   
/*  537:     */   public void visitCALOAD(CALOAD o)
/*  538:     */   {
/*  539: 637 */     Type arrayref = stack().peek(1);
/*  540: 638 */     Type index = stack().peek(0);
/*  541:     */     
/*  542: 640 */     indexOfInt(o, index);
/*  543: 641 */     arrayrefOfArrayType(o, arrayref);
/*  544:     */   }
/*  545:     */   
/*  546:     */   public void visitCASTORE(CASTORE o)
/*  547:     */   {
/*  548: 648 */     Type arrayref = stack().peek(2);
/*  549: 649 */     Type index = stack().peek(1);
/*  550: 650 */     Type value = stack().peek(0);
/*  551:     */     
/*  552: 652 */     indexOfInt(o, index);
/*  553: 653 */     valueOfInt(o, index);
/*  554: 654 */     if ((arrayrefOfArrayType(o, arrayref)) && 
/*  555: 655 */       (!((ArrayType)arrayref).getElementType().equals(Type.CHAR))) {
/*  556: 656 */       constraintViolated(o, "The 'arrayref' does not refer to an array with elements of type char but to an array of type " + ((ArrayType)arrayref).getElementType() + ".");
/*  557:     */     }
/*  558:     */   }
/*  559:     */   
/*  560:     */   public void visitCHECKCAST(CHECKCAST o)
/*  561:     */   {
/*  562: 666 */     Type objectref = stack().peek(0);
/*  563: 667 */     if (!(objectref instanceof ReferenceType)) {
/*  564: 668 */       constraintViolated(o, "The 'objectref' is not of a ReferenceType but of type " + objectref + ".");
/*  565:     */     } else {
/*  566: 671 */       referenceTypeIsInitialized(o, (ReferenceType)objectref);
/*  567:     */     }
/*  568: 676 */     Constant c = this.cpg.getConstant(o.getIndex());
/*  569: 677 */     if (!(c instanceof ConstantClass)) {
/*  570: 678 */       constraintViolated(o, "The Constant at 'index' is not a ConstantClass, but '" + c + "'.");
/*  571:     */     }
/*  572:     */   }
/*  573:     */   
/*  574:     */   public void visitD2F(D2F o)
/*  575:     */   {
/*  576: 686 */     if (stack().peek() != Type.DOUBLE) {
/*  577: 687 */       constraintViolated(o, "The value at the stack top is not of type 'double', but of type '" + stack().peek() + "'.");
/*  578:     */     }
/*  579:     */   }
/*  580:     */   
/*  581:     */   public void visitD2I(D2I o)
/*  582:     */   {
/*  583: 695 */     if (stack().peek() != Type.DOUBLE) {
/*  584: 696 */       constraintViolated(o, "The value at the stack top is not of type 'double', but of type '" + stack().peek() + "'.");
/*  585:     */     }
/*  586:     */   }
/*  587:     */   
/*  588:     */   public void visitD2L(D2L o)
/*  589:     */   {
/*  590: 704 */     if (stack().peek() != Type.DOUBLE) {
/*  591: 705 */       constraintViolated(o, "The value at the stack top is not of type 'double', but of type '" + stack().peek() + "'.");
/*  592:     */     }
/*  593:     */   }
/*  594:     */   
/*  595:     */   public void visitDADD(DADD o)
/*  596:     */   {
/*  597: 713 */     if (stack().peek() != Type.DOUBLE) {
/*  598: 714 */       constraintViolated(o, "The value at the stack top is not of type 'double', but of type '" + stack().peek() + "'.");
/*  599:     */     }
/*  600: 716 */     if (stack().peek(1) != Type.DOUBLE) {
/*  601: 717 */       constraintViolated(o, "The value at the stack next-to-top is not of type 'double', but of type '" + stack().peek(1) + "'.");
/*  602:     */     }
/*  603:     */   }
/*  604:     */   
/*  605:     */   public void visitDALOAD(DALOAD o)
/*  606:     */   {
/*  607: 725 */     indexOfInt(o, stack().peek());
/*  608: 726 */     if (stack().peek(1) == Type.NULL) {
/*  609: 727 */       return;
/*  610:     */     }
/*  611: 729 */     if (!(stack().peek(1) instanceof ArrayType)) {
/*  612: 730 */       constraintViolated(o, "Stack next-to-top must be of type double[] but is '" + stack().peek(1) + "'.");
/*  613:     */     }
/*  614: 732 */     Type t = ((ArrayType)stack().peek(1)).getBasicType();
/*  615: 733 */     if (t != Type.DOUBLE) {
/*  616: 734 */       constraintViolated(o, "Stack next-to-top must be of type double[] but is '" + stack().peek(1) + "'.");
/*  617:     */     }
/*  618:     */   }
/*  619:     */   
/*  620:     */   public void visitDASTORE(DASTORE o)
/*  621:     */   {
/*  622: 742 */     if (stack().peek() != Type.DOUBLE) {
/*  623: 743 */       constraintViolated(o, "The value at the stack top is not of type 'double', but of type '" + stack().peek() + "'.");
/*  624:     */     }
/*  625: 745 */     indexOfInt(o, stack().peek(1));
/*  626: 746 */     if (stack().peek(2) == Type.NULL) {
/*  627: 747 */       return;
/*  628:     */     }
/*  629: 749 */     if (!(stack().peek(2) instanceof ArrayType)) {
/*  630: 750 */       constraintViolated(o, "Stack next-to-next-to-top must be of type double[] but is '" + stack().peek(2) + "'.");
/*  631:     */     }
/*  632: 752 */     Type t = ((ArrayType)stack().peek(2)).getBasicType();
/*  633: 753 */     if (t != Type.DOUBLE) {
/*  634: 754 */       constraintViolated(o, "Stack next-to-next-to-top must be of type double[] but is '" + stack().peek(2) + "'.");
/*  635:     */     }
/*  636:     */   }
/*  637:     */   
/*  638:     */   public void visitDCMPG(DCMPG o)
/*  639:     */   {
/*  640: 762 */     if (stack().peek() != Type.DOUBLE) {
/*  641: 763 */       constraintViolated(o, "The value at the stack top is not of type 'double', but of type '" + stack().peek() + "'.");
/*  642:     */     }
/*  643: 765 */     if (stack().peek(1) != Type.DOUBLE) {
/*  644: 766 */       constraintViolated(o, "The value at the stack next-to-top is not of type 'double', but of type '" + stack().peek(1) + "'.");
/*  645:     */     }
/*  646:     */   }
/*  647:     */   
/*  648:     */   public void visitDCMPL(DCMPL o)
/*  649:     */   {
/*  650: 774 */     if (stack().peek() != Type.DOUBLE) {
/*  651: 775 */       constraintViolated(o, "The value at the stack top is not of type 'double', but of type '" + stack().peek() + "'.");
/*  652:     */     }
/*  653: 777 */     if (stack().peek(1) != Type.DOUBLE) {
/*  654: 778 */       constraintViolated(o, "The value at the stack next-to-top is not of type 'double', but of type '" + stack().peek(1) + "'.");
/*  655:     */     }
/*  656:     */   }
/*  657:     */   
/*  658:     */   public void visitDCONST(DCONST o) {}
/*  659:     */   
/*  660:     */   public void visitDDIV(DDIV o)
/*  661:     */   {
/*  662: 793 */     if (stack().peek() != Type.DOUBLE) {
/*  663: 794 */       constraintViolated(o, "The value at the stack top is not of type 'double', but of type '" + stack().peek() + "'.");
/*  664:     */     }
/*  665: 796 */     if (stack().peek(1) != Type.DOUBLE) {
/*  666: 797 */       constraintViolated(o, "The value at the stack next-to-top is not of type 'double', but of type '" + stack().peek(1) + "'.");
/*  667:     */     }
/*  668:     */   }
/*  669:     */   
/*  670:     */   public void visitDLOAD(DLOAD o) {}
/*  671:     */   
/*  672:     */   public void visitDMUL(DMUL o)
/*  673:     */   {
/*  674: 814 */     if (stack().peek() != Type.DOUBLE) {
/*  675: 815 */       constraintViolated(o, "The value at the stack top is not of type 'double', but of type '" + stack().peek() + "'.");
/*  676:     */     }
/*  677: 817 */     if (stack().peek(1) != Type.DOUBLE) {
/*  678: 818 */       constraintViolated(o, "The value at the stack next-to-top is not of type 'double', but of type '" + stack().peek(1) + "'.");
/*  679:     */     }
/*  680:     */   }
/*  681:     */   
/*  682:     */   public void visitDNEG(DNEG o)
/*  683:     */   {
/*  684: 826 */     if (stack().peek() != Type.DOUBLE) {
/*  685: 827 */       constraintViolated(o, "The value at the stack top is not of type 'double', but of type '" + stack().peek() + "'.");
/*  686:     */     }
/*  687:     */   }
/*  688:     */   
/*  689:     */   public void visitDREM(DREM o)
/*  690:     */   {
/*  691: 835 */     if (stack().peek() != Type.DOUBLE) {
/*  692: 836 */       constraintViolated(o, "The value at the stack top is not of type 'double', but of type '" + stack().peek() + "'.");
/*  693:     */     }
/*  694: 838 */     if (stack().peek(1) != Type.DOUBLE) {
/*  695: 839 */       constraintViolated(o, "The value at the stack next-to-top is not of type 'double', but of type '" + stack().peek(1) + "'.");
/*  696:     */     }
/*  697:     */   }
/*  698:     */   
/*  699:     */   public void visitDRETURN(DRETURN o)
/*  700:     */   {
/*  701: 847 */     if (stack().peek() != Type.DOUBLE) {
/*  702: 848 */       constraintViolated(o, "The value at the stack top is not of type 'double', but of type '" + stack().peek() + "'.");
/*  703:     */     }
/*  704:     */   }
/*  705:     */   
/*  706:     */   public void visitDSTORE(DSTORE o) {}
/*  707:     */   
/*  708:     */   public void visitDSUB(DSUB o)
/*  709:     */   {
/*  710: 865 */     if (stack().peek() != Type.DOUBLE) {
/*  711: 866 */       constraintViolated(o, "The value at the stack top is not of type 'double', but of type '" + stack().peek() + "'.");
/*  712:     */     }
/*  713: 868 */     if (stack().peek(1) != Type.DOUBLE) {
/*  714: 869 */       constraintViolated(o, "The value at the stack next-to-top is not of type 'double', but of type '" + stack().peek(1) + "'.");
/*  715:     */     }
/*  716:     */   }
/*  717:     */   
/*  718:     */   public void visitDUP(DUP o)
/*  719:     */   {
/*  720: 877 */     if (stack().peek().getSize() != 1) {
/*  721: 878 */       constraintViolated(o, "Won't DUP type on stack top '" + stack().peek() + "' because it must occupy exactly one slot, not '" + stack().peek().getSize() + "'.");
/*  722:     */     }
/*  723:     */   }
/*  724:     */   
/*  725:     */   public void visitDUP_X1(DUP_X1 o)
/*  726:     */   {
/*  727: 886 */     if (stack().peek().getSize() != 1) {
/*  728: 887 */       constraintViolated(o, "Type on stack top '" + stack().peek() + "' should occupy exactly one slot, not '" + stack().peek().getSize() + "'.");
/*  729:     */     }
/*  730: 889 */     if (stack().peek(1).getSize() != 1) {
/*  731: 890 */       constraintViolated(o, "Type on stack next-to-top '" + stack().peek(1) + "' should occupy exactly one slot, not '" + stack().peek(1).getSize() + "'.");
/*  732:     */     }
/*  733:     */   }
/*  734:     */   
/*  735:     */   public void visitDUP_X2(DUP_X2 o)
/*  736:     */   {
/*  737: 898 */     if (stack().peek().getSize() != 1) {
/*  738: 899 */       constraintViolated(o, "Stack top type must be of size 1, but is '" + stack().peek() + "' of size '" + stack().peek().getSize() + "'.");
/*  739:     */     }
/*  740: 901 */     if (stack().peek(1).getSize() == 2) {
/*  741: 902 */       return;
/*  742:     */     }
/*  743: 905 */     if (stack().peek(2).getSize() != 1) {
/*  744: 906 */       constraintViolated(o, "If stack top's size is 1 and stack next-to-top's size is 1, stack next-to-next-to-top's size must also be 1, but is: '" + stack().peek(2) + "' of size '" + stack().peek(2).getSize() + "'.");
/*  745:     */     }
/*  746:     */   }
/*  747:     */   
/*  748:     */   public void visitDUP2(DUP2 o)
/*  749:     */   {
/*  750: 915 */     if (stack().peek().getSize() == 2) {
/*  751: 916 */       return;
/*  752:     */     }
/*  753: 919 */     if (stack().peek(1).getSize() != 1) {
/*  754: 920 */       constraintViolated(o, "If stack top's size is 1, then stack next-to-top's size must also be 1. But it is '" + stack().peek(1) + "' of size '" + stack().peek(1).getSize() + "'.");
/*  755:     */     }
/*  756:     */   }
/*  757:     */   
/*  758:     */   public void visitDUP2_X1(DUP2_X1 o)
/*  759:     */   {
/*  760: 929 */     if (stack().peek().getSize() == 2)
/*  761:     */     {
/*  762: 930 */       if (stack().peek(1).getSize() != 1) {
/*  763: 931 */         constraintViolated(o, "If stack top's size is 2, then stack next-to-top's size must be 1. But it is '" + stack().peek(1) + "' of size '" + stack().peek(1).getSize() + "'.");
/*  764:     */       }
/*  765:     */     }
/*  766:     */     else
/*  767:     */     {
/*  768: 938 */       if (stack().peek(1).getSize() != 1) {
/*  769: 939 */         constraintViolated(o, "If stack top's size is 1, then stack next-to-top's size must also be 1. But it is '" + stack().peek(1) + "' of size '" + stack().peek(1).getSize() + "'.");
/*  770:     */       }
/*  771: 941 */       if (stack().peek(2).getSize() != 1) {
/*  772: 942 */         constraintViolated(o, "If stack top's size is 1, then stack next-to-next-to-top's size must also be 1. But it is '" + stack().peek(2) + "' of size '" + stack().peek(2).getSize() + "'.");
/*  773:     */       }
/*  774:     */     }
/*  775:     */   }
/*  776:     */   
/*  777:     */   public void visitDUP2_X2(DUP2_X2 o)
/*  778:     */   {
/*  779: 952 */     if (stack().peek(0).getSize() == 2)
/*  780:     */     {
/*  781: 953 */       if (stack().peek(1).getSize() == 2) {
/*  782: 954 */         return;
/*  783:     */       }
/*  784: 957 */       if (stack().peek(2).getSize() != 1) {
/*  785: 958 */         constraintViolated(o, "If stack top's size is 2 and stack-next-to-top's size is 1, then stack next-to-next-to-top's size must also be 1. But it is '" + stack().peek(2) + "' of size '" + stack().peek(2).getSize() + "'.");
/*  786:     */       }
/*  787:     */     }
/*  788: 966 */     else if (stack().peek(1).getSize() == 1)
/*  789:     */     {
/*  790: 967 */       if (stack().peek(2).getSize() == 2) {
/*  791: 968 */         return;
/*  792:     */       }
/*  793: 971 */       if (stack().peek(3).getSize() == 1) {
/*  794: 972 */         return;
/*  795:     */       }
/*  796:     */     }
/*  797: 977 */     constraintViolated(o, "The operand sizes on the stack do not match any of the four forms of usage of this instruction.");
/*  798:     */   }
/*  799:     */   
/*  800:     */   public void visitF2D(F2D o)
/*  801:     */   {
/*  802: 984 */     if (stack().peek() != Type.FLOAT) {
/*  803: 985 */       constraintViolated(o, "The value at the stack top is not of type 'float', but of type '" + stack().peek() + "'.");
/*  804:     */     }
/*  805:     */   }
/*  806:     */   
/*  807:     */   public void visitF2I(F2I o)
/*  808:     */   {
/*  809: 993 */     if (stack().peek() != Type.FLOAT) {
/*  810: 994 */       constraintViolated(o, "The value at the stack top is not of type 'float', but of type '" + stack().peek() + "'.");
/*  811:     */     }
/*  812:     */   }
/*  813:     */   
/*  814:     */   public void visitF2L(F2L o)
/*  815:     */   {
/*  816:1002 */     if (stack().peek() != Type.FLOAT) {
/*  817:1003 */       constraintViolated(o, "The value at the stack top is not of type 'float', but of type '" + stack().peek() + "'.");
/*  818:     */     }
/*  819:     */   }
/*  820:     */   
/*  821:     */   public void visitFADD(FADD o)
/*  822:     */   {
/*  823:1011 */     if (stack().peek() != Type.FLOAT) {
/*  824:1012 */       constraintViolated(o, "The value at the stack top is not of type 'float', but of type '" + stack().peek() + "'.");
/*  825:     */     }
/*  826:1014 */     if (stack().peek(1) != Type.FLOAT) {
/*  827:1015 */       constraintViolated(o, "The value at the stack next-to-top is not of type 'float', but of type '" + stack().peek(1) + "'.");
/*  828:     */     }
/*  829:     */   }
/*  830:     */   
/*  831:     */   public void visitFALOAD(FALOAD o)
/*  832:     */   {
/*  833:1023 */     indexOfInt(o, stack().peek());
/*  834:1024 */     if (stack().peek(1) == Type.NULL) {
/*  835:1025 */       return;
/*  836:     */     }
/*  837:1027 */     if (!(stack().peek(1) instanceof ArrayType)) {
/*  838:1028 */       constraintViolated(o, "Stack next-to-top must be of type float[] but is '" + stack().peek(1) + "'.");
/*  839:     */     }
/*  840:1030 */     Type t = ((ArrayType)stack().peek(1)).getBasicType();
/*  841:1031 */     if (t != Type.FLOAT) {
/*  842:1032 */       constraintViolated(o, "Stack next-to-top must be of type float[] but is '" + stack().peek(1) + "'.");
/*  843:     */     }
/*  844:     */   }
/*  845:     */   
/*  846:     */   public void visitFASTORE(FASTORE o)
/*  847:     */   {
/*  848:1040 */     if (stack().peek() != Type.FLOAT) {
/*  849:1041 */       constraintViolated(o, "The value at the stack top is not of type 'float', but of type '" + stack().peek() + "'.");
/*  850:     */     }
/*  851:1043 */     indexOfInt(o, stack().peek(1));
/*  852:1044 */     if (stack().peek(2) == Type.NULL) {
/*  853:1045 */       return;
/*  854:     */     }
/*  855:1047 */     if (!(stack().peek(2) instanceof ArrayType)) {
/*  856:1048 */       constraintViolated(o, "Stack next-to-next-to-top must be of type float[] but is '" + stack().peek(2) + "'.");
/*  857:     */     }
/*  858:1050 */     Type t = ((ArrayType)stack().peek(2)).getBasicType();
/*  859:1051 */     if (t != Type.FLOAT) {
/*  860:1052 */       constraintViolated(o, "Stack next-to-next-to-top must be of type float[] but is '" + stack().peek(2) + "'.");
/*  861:     */     }
/*  862:     */   }
/*  863:     */   
/*  864:     */   public void visitFCMPG(FCMPG o)
/*  865:     */   {
/*  866:1060 */     if (stack().peek() != Type.FLOAT) {
/*  867:1061 */       constraintViolated(o, "The value at the stack top is not of type 'float', but of type '" + stack().peek() + "'.");
/*  868:     */     }
/*  869:1063 */     if (stack().peek(1) != Type.FLOAT) {
/*  870:1064 */       constraintViolated(o, "The value at the stack next-to-top is not of type 'float', but of type '" + stack().peek(1) + "'.");
/*  871:     */     }
/*  872:     */   }
/*  873:     */   
/*  874:     */   public void visitFCMPL(FCMPL o)
/*  875:     */   {
/*  876:1072 */     if (stack().peek() != Type.FLOAT) {
/*  877:1073 */       constraintViolated(o, "The value at the stack top is not of type 'float', but of type '" + stack().peek() + "'.");
/*  878:     */     }
/*  879:1075 */     if (stack().peek(1) != Type.FLOAT) {
/*  880:1076 */       constraintViolated(o, "The value at the stack next-to-top is not of type 'float', but of type '" + stack().peek(1) + "'.");
/*  881:     */     }
/*  882:     */   }
/*  883:     */   
/*  884:     */   public void visitFCONST(FCONST o) {}
/*  885:     */   
/*  886:     */   public void visitFDIV(FDIV o)
/*  887:     */   {
/*  888:1091 */     if (stack().peek() != Type.FLOAT) {
/*  889:1092 */       constraintViolated(o, "The value at the stack top is not of type 'float', but of type '" + stack().peek() + "'.");
/*  890:     */     }
/*  891:1094 */     if (stack().peek(1) != Type.FLOAT) {
/*  892:1095 */       constraintViolated(o, "The value at the stack next-to-top is not of type 'float', but of type '" + stack().peek(1) + "'.");
/*  893:     */     }
/*  894:     */   }
/*  895:     */   
/*  896:     */   public void visitFLOAD(FLOAD o) {}
/*  897:     */   
/*  898:     */   public void visitFMUL(FMUL o)
/*  899:     */   {
/*  900:1112 */     if (stack().peek() != Type.FLOAT) {
/*  901:1113 */       constraintViolated(o, "The value at the stack top is not of type 'float', but of type '" + stack().peek() + "'.");
/*  902:     */     }
/*  903:1115 */     if (stack().peek(1) != Type.FLOAT) {
/*  904:1116 */       constraintViolated(o, "The value at the stack next-to-top is not of type 'float', but of type '" + stack().peek(1) + "'.");
/*  905:     */     }
/*  906:     */   }
/*  907:     */   
/*  908:     */   public void visitFNEG(FNEG o)
/*  909:     */   {
/*  910:1124 */     if (stack().peek() != Type.FLOAT) {
/*  911:1125 */       constraintViolated(o, "The value at the stack top is not of type 'float', but of type '" + stack().peek() + "'.");
/*  912:     */     }
/*  913:     */   }
/*  914:     */   
/*  915:     */   public void visitFREM(FREM o)
/*  916:     */   {
/*  917:1133 */     if (stack().peek() != Type.FLOAT) {
/*  918:1134 */       constraintViolated(o, "The value at the stack top is not of type 'float', but of type '" + stack().peek() + "'.");
/*  919:     */     }
/*  920:1136 */     if (stack().peek(1) != Type.FLOAT) {
/*  921:1137 */       constraintViolated(o, "The value at the stack next-to-top is not of type 'float', but of type '" + stack().peek(1) + "'.");
/*  922:     */     }
/*  923:     */   }
/*  924:     */   
/*  925:     */   public void visitFRETURN(FRETURN o)
/*  926:     */   {
/*  927:1145 */     if (stack().peek() != Type.FLOAT) {
/*  928:1146 */       constraintViolated(o, "The value at the stack top is not of type 'float', but of type '" + stack().peek() + "'.");
/*  929:     */     }
/*  930:     */   }
/*  931:     */   
/*  932:     */   public void visitFSTORE(FSTORE o) {}
/*  933:     */   
/*  934:     */   public void visitFSUB(FSUB o)
/*  935:     */   {
/*  936:1163 */     if (stack().peek() != Type.FLOAT) {
/*  937:1164 */       constraintViolated(o, "The value at the stack top is not of type 'float', but of type '" + stack().peek() + "'.");
/*  938:     */     }
/*  939:1166 */     if (stack().peek(1) != Type.FLOAT) {
/*  940:1167 */       constraintViolated(o, "The value at the stack next-to-top is not of type 'float', but of type '" + stack().peek(1) + "'.");
/*  941:     */     }
/*  942:     */   }
/*  943:     */   
/*  944:     */   public void visitGETFIELD(GETFIELD o)
/*  945:     */   {
/*  946:1175 */     Type objectref = stack().peek();
/*  947:1176 */     if ((!(objectref instanceof ObjectType)) && (objectref != Type.NULL)) {
/*  948:1177 */       constraintViolated(o, "Stack top should be an object reference that's not an array reference, but is '" + objectref + "'.");
/*  949:     */     }
/*  950:1180 */     String field_name = o.getFieldName(this.cpg);
/*  951:     */     
/*  952:1182 */     JavaClass jc = Repository.lookupClass(o.getClassType(this.cpg).getClassName());
/*  953:1183 */     Field[] fields = jc.getFields();
/*  954:1184 */     Field f = null;
/*  955:1185 */     for (int i = 0; i < fields.length; i++) {
/*  956:1186 */       if (fields[i].getName().equals(field_name))
/*  957:     */       {
/*  958:1187 */         f = fields[i];
/*  959:1188 */         break;
/*  960:     */       }
/*  961:     */     }
/*  962:1191 */     if (f == null) {
/*  963:1192 */       throw new AssertionViolatedException("Field not found?!?");
/*  964:     */     }
/*  965:1195 */     if (f.isProtected())
/*  966:     */     {
/*  967:1196 */       ObjectType classtype = o.getClassType(this.cpg);
/*  968:1197 */       ObjectType curr = new ObjectType(this.mg.getClassName());
/*  969:1199 */       if ((classtype.equals(curr)) || (curr.subclassOf(classtype)))
/*  970:     */       {
/*  971:1201 */         Type t = stack().peek();
/*  972:1202 */         if (t == Type.NULL) {
/*  973:1203 */           return;
/*  974:     */         }
/*  975:1205 */         if (!(t instanceof ObjectType)) {
/*  976:1206 */           constraintViolated(o, "The 'objectref' must refer to an object that's not an array. Found instead: '" + t + "'.");
/*  977:     */         }
/*  978:1208 */         ObjectType objreftype = (ObjectType)t;
/*  979:1209 */         if ((objreftype.equals(curr)) || (objreftype.subclassOf(curr))) {}
/*  980:     */       }
/*  981:     */     }
/*  982:1220 */     if (f.isStatic()) {
/*  983:1221 */       constraintViolated(o, "Referenced field '" + f + "' is static which it shouldn't be.");
/*  984:     */     }
/*  985:     */   }
/*  986:     */   
/*  987:     */   public void visitGETSTATIC(GETSTATIC o) {}
/*  988:     */   
/*  989:     */   public void visitGOTO(GOTO o) {}
/*  990:     */   
/*  991:     */   public void visitGOTO_W(GOTO_W o) {}
/*  992:     */   
/*  993:     */   public void visitI2B(I2B o)
/*  994:     */   {
/*  995:1250 */     if (stack().peek() != Type.INT) {
/*  996:1251 */       constraintViolated(o, "The value at the stack top is not of type 'int', but of type '" + stack().peek() + "'.");
/*  997:     */     }
/*  998:     */   }
/*  999:     */   
/* 1000:     */   public void visitI2C(I2C o)
/* 1001:     */   {
/* 1002:1259 */     if (stack().peek() != Type.INT) {
/* 1003:1260 */       constraintViolated(o, "The value at the stack top is not of type 'int', but of type '" + stack().peek() + "'.");
/* 1004:     */     }
/* 1005:     */   }
/* 1006:     */   
/* 1007:     */   public void visitI2D(I2D o)
/* 1008:     */   {
/* 1009:1268 */     if (stack().peek() != Type.INT) {
/* 1010:1269 */       constraintViolated(o, "The value at the stack top is not of type 'int', but of type '" + stack().peek() + "'.");
/* 1011:     */     }
/* 1012:     */   }
/* 1013:     */   
/* 1014:     */   public void visitI2F(I2F o)
/* 1015:     */   {
/* 1016:1277 */     if (stack().peek() != Type.INT) {
/* 1017:1278 */       constraintViolated(o, "The value at the stack top is not of type 'int', but of type '" + stack().peek() + "'.");
/* 1018:     */     }
/* 1019:     */   }
/* 1020:     */   
/* 1021:     */   public void visitI2L(I2L o)
/* 1022:     */   {
/* 1023:1286 */     if (stack().peek() != Type.INT) {
/* 1024:1287 */       constraintViolated(o, "The value at the stack top is not of type 'int', but of type '" + stack().peek() + "'.");
/* 1025:     */     }
/* 1026:     */   }
/* 1027:     */   
/* 1028:     */   public void visitI2S(I2S o)
/* 1029:     */   {
/* 1030:1295 */     if (stack().peek() != Type.INT) {
/* 1031:1296 */       constraintViolated(o, "The value at the stack top is not of type 'int', but of type '" + stack().peek() + "'.");
/* 1032:     */     }
/* 1033:     */   }
/* 1034:     */   
/* 1035:     */   public void visitIADD(IADD o)
/* 1036:     */   {
/* 1037:1304 */     if (stack().peek() != Type.INT) {
/* 1038:1305 */       constraintViolated(o, "The value at the stack top is not of type 'int', but of type '" + stack().peek() + "'.");
/* 1039:     */     }
/* 1040:1307 */     if (stack().peek(1) != Type.INT) {
/* 1041:1308 */       constraintViolated(o, "The value at the stack next-to-top is not of type 'int', but of type '" + stack().peek(1) + "'.");
/* 1042:     */     }
/* 1043:     */   }
/* 1044:     */   
/* 1045:     */   public void visitIALOAD(IALOAD o)
/* 1046:     */   {
/* 1047:1316 */     indexOfInt(o, stack().peek());
/* 1048:1317 */     if (stack().peek(1) == Type.NULL) {
/* 1049:1318 */       return;
/* 1050:     */     }
/* 1051:1320 */     if (!(stack().peek(1) instanceof ArrayType)) {
/* 1052:1321 */       constraintViolated(o, "Stack next-to-top must be of type int[] but is '" + stack().peek(1) + "'.");
/* 1053:     */     }
/* 1054:1323 */     Type t = ((ArrayType)stack().peek(1)).getBasicType();
/* 1055:1324 */     if (t != Type.INT) {
/* 1056:1325 */       constraintViolated(o, "Stack next-to-top must be of type int[] but is '" + stack().peek(1) + "'.");
/* 1057:     */     }
/* 1058:     */   }
/* 1059:     */   
/* 1060:     */   public void visitIAND(IAND o)
/* 1061:     */   {
/* 1062:1333 */     if (stack().peek() != Type.INT) {
/* 1063:1334 */       constraintViolated(o, "The value at the stack top is not of type 'int', but of type '" + stack().peek() + "'.");
/* 1064:     */     }
/* 1065:1336 */     if (stack().peek(1) != Type.INT) {
/* 1066:1337 */       constraintViolated(o, "The value at the stack next-to-top is not of type 'int', but of type '" + stack().peek(1) + "'.");
/* 1067:     */     }
/* 1068:     */   }
/* 1069:     */   
/* 1070:     */   public void visitIASTORE(IASTORE o)
/* 1071:     */   {
/* 1072:1345 */     if (stack().peek() != Type.INT) {
/* 1073:1346 */       constraintViolated(o, "The value at the stack top is not of type 'int', but of type '" + stack().peek() + "'.");
/* 1074:     */     }
/* 1075:1348 */     indexOfInt(o, stack().peek(1));
/* 1076:1349 */     if (stack().peek(2) == Type.NULL) {
/* 1077:1350 */       return;
/* 1078:     */     }
/* 1079:1352 */     if (!(stack().peek(2) instanceof ArrayType)) {
/* 1080:1353 */       constraintViolated(o, "Stack next-to-next-to-top must be of type int[] but is '" + stack().peek(2) + "'.");
/* 1081:     */     }
/* 1082:1355 */     Type t = ((ArrayType)stack().peek(2)).getBasicType();
/* 1083:1356 */     if (t != Type.INT) {
/* 1084:1357 */       constraintViolated(o, "Stack next-to-next-to-top must be of type int[] but is '" + stack().peek(2) + "'.");
/* 1085:     */     }
/* 1086:     */   }
/* 1087:     */   
/* 1088:     */   public void visitICONST(ICONST o) {}
/* 1089:     */   
/* 1090:     */   public void visitIDIV(IDIV o)
/* 1091:     */   {
/* 1092:1372 */     if (stack().peek() != Type.INT) {
/* 1093:1373 */       constraintViolated(o, "The value at the stack top is not of type 'int', but of type '" + stack().peek() + "'.");
/* 1094:     */     }
/* 1095:1375 */     if (stack().peek(1) != Type.INT) {
/* 1096:1376 */       constraintViolated(o, "The value at the stack next-to-top is not of type 'int', but of type '" + stack().peek(1) + "'.");
/* 1097:     */     }
/* 1098:     */   }
/* 1099:     */   
/* 1100:     */   public void visitIF_ACMPEQ(IF_ACMPEQ o)
/* 1101:     */   {
/* 1102:1384 */     if (!(stack().peek() instanceof ReferenceType)) {
/* 1103:1385 */       constraintViolated(o, "The value at the stack top is not of a ReferenceType, but of type '" + stack().peek() + "'.");
/* 1104:     */     }
/* 1105:1387 */     referenceTypeIsInitialized(o, (ReferenceType)stack().peek());
/* 1106:1389 */     if (!(stack().peek(1) instanceof ReferenceType)) {
/* 1107:1390 */       constraintViolated(o, "The value at the stack next-to-top is not of a ReferenceType, but of type '" + stack().peek(1) + "'.");
/* 1108:     */     }
/* 1109:1392 */     referenceTypeIsInitialized(o, (ReferenceType)stack().peek(1));
/* 1110:     */   }
/* 1111:     */   
/* 1112:     */   public void visitIF_ACMPNE(IF_ACMPNE o)
/* 1113:     */   {
/* 1114:1400 */     if (!(stack().peek() instanceof ReferenceType))
/* 1115:     */     {
/* 1116:1401 */       constraintViolated(o, "The value at the stack top is not of a ReferenceType, but of type '" + stack().peek() + "'.");
/* 1117:1402 */       referenceTypeIsInitialized(o, (ReferenceType)stack().peek());
/* 1118:     */     }
/* 1119:1404 */     if (!(stack().peek(1) instanceof ReferenceType))
/* 1120:     */     {
/* 1121:1405 */       constraintViolated(o, "The value at the stack next-to-top is not of a ReferenceType, but of type '" + stack().peek(1) + "'.");
/* 1122:1406 */       referenceTypeIsInitialized(o, (ReferenceType)stack().peek(1));
/* 1123:     */     }
/* 1124:     */   }
/* 1125:     */   
/* 1126:     */   public void visitIF_ICMPEQ(IF_ICMPEQ o)
/* 1127:     */   {
/* 1128:1414 */     if (stack().peek() != Type.INT) {
/* 1129:1415 */       constraintViolated(o, "The value at the stack top is not of type 'int', but of type '" + stack().peek() + "'.");
/* 1130:     */     }
/* 1131:1417 */     if (stack().peek(1) != Type.INT) {
/* 1132:1418 */       constraintViolated(o, "The value at the stack next-to-top is not of type 'int', but of type '" + stack().peek(1) + "'.");
/* 1133:     */     }
/* 1134:     */   }
/* 1135:     */   
/* 1136:     */   public void visitIF_ICMPGE(IF_ICMPGE o)
/* 1137:     */   {
/* 1138:1426 */     if (stack().peek() != Type.INT) {
/* 1139:1427 */       constraintViolated(o, "The value at the stack top is not of type 'int', but of type '" + stack().peek() + "'.");
/* 1140:     */     }
/* 1141:1429 */     if (stack().peek(1) != Type.INT) {
/* 1142:1430 */       constraintViolated(o, "The value at the stack next-to-top is not of type 'int', but of type '" + stack().peek(1) + "'.");
/* 1143:     */     }
/* 1144:     */   }
/* 1145:     */   
/* 1146:     */   public void visitIF_ICMPGT(IF_ICMPGT o)
/* 1147:     */   {
/* 1148:1438 */     if (stack().peek() != Type.INT) {
/* 1149:1439 */       constraintViolated(o, "The value at the stack top is not of type 'int', but of type '" + stack().peek() + "'.");
/* 1150:     */     }
/* 1151:1441 */     if (stack().peek(1) != Type.INT) {
/* 1152:1442 */       constraintViolated(o, "The value at the stack next-to-top is not of type 'int', but of type '" + stack().peek(1) + "'.");
/* 1153:     */     }
/* 1154:     */   }
/* 1155:     */   
/* 1156:     */   public void visitIF_ICMPLE(IF_ICMPLE o)
/* 1157:     */   {
/* 1158:1450 */     if (stack().peek() != Type.INT) {
/* 1159:1451 */       constraintViolated(o, "The value at the stack top is not of type 'int', but of type '" + stack().peek() + "'.");
/* 1160:     */     }
/* 1161:1453 */     if (stack().peek(1) != Type.INT) {
/* 1162:1454 */       constraintViolated(o, "The value at the stack next-to-top is not of type 'int', but of type '" + stack().peek(1) + "'.");
/* 1163:     */     }
/* 1164:     */   }
/* 1165:     */   
/* 1166:     */   public void visitIF_ICMPLT(IF_ICMPLT o)
/* 1167:     */   {
/* 1168:1462 */     if (stack().peek() != Type.INT) {
/* 1169:1463 */       constraintViolated(o, "The value at the stack top is not of type 'int', but of type '" + stack().peek() + "'.");
/* 1170:     */     }
/* 1171:1465 */     if (stack().peek(1) != Type.INT) {
/* 1172:1466 */       constraintViolated(o, "The value at the stack next-to-top is not of type 'int', but of type '" + stack().peek(1) + "'.");
/* 1173:     */     }
/* 1174:     */   }
/* 1175:     */   
/* 1176:     */   public void visitIF_ICMPNE(IF_ICMPNE o)
/* 1177:     */   {
/* 1178:1474 */     if (stack().peek() != Type.INT) {
/* 1179:1475 */       constraintViolated(o, "The value at the stack top is not of type 'int', but of type '" + stack().peek() + "'.");
/* 1180:     */     }
/* 1181:1477 */     if (stack().peek(1) != Type.INT) {
/* 1182:1478 */       constraintViolated(o, "The value at the stack next-to-top is not of type 'int', but of type '" + stack().peek(1) + "'.");
/* 1183:     */     }
/* 1184:     */   }
/* 1185:     */   
/* 1186:     */   public void visitIFEQ(IFEQ o)
/* 1187:     */   {
/* 1188:1486 */     if (stack().peek() != Type.INT) {
/* 1189:1487 */       constraintViolated(o, "The value at the stack top is not of type 'int', but of type '" + stack().peek() + "'.");
/* 1190:     */     }
/* 1191:     */   }
/* 1192:     */   
/* 1193:     */   public void visitIFGE(IFGE o)
/* 1194:     */   {
/* 1195:1495 */     if (stack().peek() != Type.INT) {
/* 1196:1496 */       constraintViolated(o, "The value at the stack top is not of type 'int', but of type '" + stack().peek() + "'.");
/* 1197:     */     }
/* 1198:     */   }
/* 1199:     */   
/* 1200:     */   public void visitIFGT(IFGT o)
/* 1201:     */   {
/* 1202:1504 */     if (stack().peek() != Type.INT) {
/* 1203:1505 */       constraintViolated(o, "The value at the stack top is not of type 'int', but of type '" + stack().peek() + "'.");
/* 1204:     */     }
/* 1205:     */   }
/* 1206:     */   
/* 1207:     */   public void visitIFLE(IFLE o)
/* 1208:     */   {
/* 1209:1513 */     if (stack().peek() != Type.INT) {
/* 1210:1514 */       constraintViolated(o, "The value at the stack top is not of type 'int', but of type '" + stack().peek() + "'.");
/* 1211:     */     }
/* 1212:     */   }
/* 1213:     */   
/* 1214:     */   public void visitIFLT(IFLT o)
/* 1215:     */   {
/* 1216:1522 */     if (stack().peek() != Type.INT) {
/* 1217:1523 */       constraintViolated(o, "The value at the stack top is not of type 'int', but of type '" + stack().peek() + "'.");
/* 1218:     */     }
/* 1219:     */   }
/* 1220:     */   
/* 1221:     */   public void visitIFNE(IFNE o)
/* 1222:     */   {
/* 1223:1531 */     if (stack().peek() != Type.INT) {
/* 1224:1532 */       constraintViolated(o, "The value at the stack top is not of type 'int', but of type '" + stack().peek() + "'.");
/* 1225:     */     }
/* 1226:     */   }
/* 1227:     */   
/* 1228:     */   public void visitIFNONNULL(IFNONNULL o)
/* 1229:     */   {
/* 1230:1540 */     if (!(stack().peek() instanceof ReferenceType)) {
/* 1231:1541 */       constraintViolated(o, "The value at the stack top is not of a ReferenceType, but of type '" + stack().peek() + "'.");
/* 1232:     */     }
/* 1233:1543 */     referenceTypeIsInitialized(o, (ReferenceType)stack().peek());
/* 1234:     */   }
/* 1235:     */   
/* 1236:     */   public void visitIFNULL(IFNULL o)
/* 1237:     */   {
/* 1238:1550 */     if (!(stack().peek() instanceof ReferenceType)) {
/* 1239:1551 */       constraintViolated(o, "The value at the stack top is not of a ReferenceType, but of type '" + stack().peek() + "'.");
/* 1240:     */     }
/* 1241:1553 */     referenceTypeIsInitialized(o, (ReferenceType)stack().peek());
/* 1242:     */   }
/* 1243:     */   
/* 1244:     */   public void visitIINC(IINC o)
/* 1245:     */   {
/* 1246:1561 */     if (locals().maxLocals() <= (o.getType(this.cpg).getSize() == 1 ? o.getIndex() : o.getIndex() + 1)) {
/* 1247:1562 */       constraintViolated(o, "The 'index' is not a valid index into the local variable array.");
/* 1248:     */     }
/* 1249:1565 */     indexOfInt(o, locals().get(o.getIndex()));
/* 1250:     */   }
/* 1251:     */   
/* 1252:     */   public void visitILOAD(ILOAD o) {}
/* 1253:     */   
/* 1254:     */   public void visitIMPDEP1(IMPDEP1 o)
/* 1255:     */   {
/* 1256:1579 */     throw new AssertionViolatedException("In this JustIce verification pass there should not occur an illegal instruction such as IMPDEP1.");
/* 1257:     */   }
/* 1258:     */   
/* 1259:     */   public void visitIMPDEP2(IMPDEP2 o)
/* 1260:     */   {
/* 1261:1586 */     throw new AssertionViolatedException("In this JustIce verification pass there should not occur an illegal instruction such as IMPDEP2.");
/* 1262:     */   }
/* 1263:     */   
/* 1264:     */   public void visitIMUL(IMUL o)
/* 1265:     */   {
/* 1266:1593 */     if (stack().peek() != Type.INT) {
/* 1267:1594 */       constraintViolated(o, "The value at the stack top is not of type 'int', but of type '" + stack().peek() + "'.");
/* 1268:     */     }
/* 1269:1596 */     if (stack().peek(1) != Type.INT) {
/* 1270:1597 */       constraintViolated(o, "The value at the stack next-to-top is not of type 'int', but of type '" + stack().peek(1) + "'.");
/* 1271:     */     }
/* 1272:     */   }
/* 1273:     */   
/* 1274:     */   public void visitINEG(INEG o)
/* 1275:     */   {
/* 1276:1605 */     if (stack().peek() != Type.INT) {
/* 1277:1606 */       constraintViolated(o, "The value at the stack top is not of type 'int', but of type '" + stack().peek() + "'.");
/* 1278:     */     }
/* 1279:     */   }
/* 1280:     */   
/* 1281:     */   public void visitINSTANCEOF(INSTANCEOF o)
/* 1282:     */   {
/* 1283:1615 */     Type objectref = stack().peek(0);
/* 1284:1616 */     if (!(objectref instanceof ReferenceType)) {
/* 1285:1617 */       constraintViolated(o, "The 'objectref' is not of a ReferenceType but of type " + objectref + ".");
/* 1286:     */     } else {
/* 1287:1620 */       referenceTypeIsInitialized(o, (ReferenceType)objectref);
/* 1288:     */     }
/* 1289:1625 */     Constant c = this.cpg.getConstant(o.getIndex());
/* 1290:1626 */     if (!(c instanceof ConstantClass)) {
/* 1291:1627 */       constraintViolated(o, "The Constant at 'index' is not a ConstantClass, but '" + c + "'.");
/* 1292:     */     }
/* 1293:     */   }
/* 1294:     */   
/* 1295:     */   public void visitINVOKEINTERFACE(INVOKEINTERFACE o)
/* 1296:     */   {
/* 1297:1637 */     int count = o.getCount();
/* 1298:1638 */     if (count == 0) {
/* 1299:1639 */       constraintViolated(o, "The 'count' argument must not be 0.");
/* 1300:     */     }
/* 1301:1642 */     ConstantInterfaceMethodref cimr = (ConstantInterfaceMethodref)this.cpg.getConstant(o.getIndex());
/* 1302:     */     
/* 1303:     */ 
/* 1304:     */ 
/* 1305:1646 */     Type t = o.getType(this.cpg);
/* 1306:1647 */     if ((t instanceof ObjectType))
/* 1307:     */     {
/* 1308:1648 */       String name = ((ObjectType)t).getClassName();
/* 1309:1649 */       Verifier v = VerifierFactory.getVerifier(name);
/* 1310:1650 */       VerificationResult vr = v.doPass2();
/* 1311:1651 */       if (vr.getStatus() != 1) {
/* 1312:1652 */         constraintViolated(o, "Class '" + name + "' is referenced, but cannot be loaded and resolved: '" + vr + "'.");
/* 1313:     */       }
/* 1314:     */     }
/* 1315:1657 */     Type[] argtypes = o.getArgumentTypes(this.cpg);
/* 1316:1658 */     int nargs = argtypes.length;
/* 1317:1660 */     for (int i = nargs - 1; i >= 0; i--)
/* 1318:     */     {
/* 1319:1661 */       Type fromStack = stack().peek(nargs - 1 - i);
/* 1320:1662 */       Type fromDesc = argtypes[i];
/* 1321:1663 */       if ((fromDesc == Type.BOOLEAN) || (fromDesc == Type.BYTE) || (fromDesc == Type.CHAR) || (fromDesc == Type.SHORT)) {
/* 1322:1667 */         fromDesc = Type.INT;
/* 1323:     */       }
/* 1324:1669 */       if (!fromStack.equals(fromDesc))
/* 1325:     */       {
/* 1326:     */         ReferenceType rFromDesc;
/* 1327:1670 */         if (((fromStack instanceof ReferenceType)) && ((fromDesc instanceof ReferenceType)))
/* 1328:     */         {
/* 1329:1671 */           ReferenceType rFromStack = (ReferenceType)fromStack;
/* 1330:1672 */           rFromDesc = (ReferenceType)fromDesc;
/* 1331:     */         }
/* 1332:     */         else
/* 1333:     */         {
/* 1334:1680 */           constraintViolated(o, "Expecting a '" + fromDesc + "' but found a '" + fromStack + "' on the stack.");
/* 1335:     */         }
/* 1336:     */       }
/* 1337:     */     }
/* 1338:1685 */     Type objref = stack().peek(nargs);
/* 1339:1686 */     if (objref == Type.NULL) {
/* 1340:1687 */       return;
/* 1341:     */     }
/* 1342:1689 */     if (!(objref instanceof ReferenceType)) {
/* 1343:1690 */       constraintViolated(o, "Expecting a reference type as 'objectref' on the stack, not a '" + objref + "'.");
/* 1344:     */     }
/* 1345:1692 */     referenceTypeIsInitialized(o, (ReferenceType)objref);
/* 1346:1693 */     if (!(objref instanceof ObjectType)) {
/* 1347:1694 */       if (!(objref instanceof ArrayType)) {
/* 1348:1695 */         constraintViolated(o, "Expecting an ObjectType as 'objectref' on the stack, not a '" + objref + "'.");
/* 1349:     */       } else {
/* 1350:1698 */         objref = GENERIC_ARRAY;
/* 1351:     */       }
/* 1352:     */     }
/* 1353:1702 */     String objref_classname = ((ObjectType)objref).getClassName();
/* 1354:     */     
/* 1355:1704 */     String theInterface = o.getClassName(this.cpg);
/* 1356:     */     
/* 1357:     */ 
/* 1358:     */ 
/* 1359:     */ 
/* 1360:     */ 
/* 1361:     */ 
/* 1362:     */ 
/* 1363:1712 */     int counted_count = 1;
/* 1364:1713 */     for (int i = 0; i < nargs; i++) {
/* 1365:1714 */       counted_count += argtypes[i].getSize();
/* 1366:     */     }
/* 1367:1716 */     if (count != counted_count) {
/* 1368:1717 */       constraintViolated(o, "The 'count' argument should probably read '" + counted_count + "' but is '" + count + "'.");
/* 1369:     */     }
/* 1370:     */   }
/* 1371:     */   
/* 1372:     */   public void visitINVOKESPECIAL(INVOKESPECIAL o)
/* 1373:     */   {
/* 1374:1726 */     if ((o.getMethodName(this.cpg).equals("<init>")) && (!(stack().peek(o.getArgumentTypes(this.cpg).length) instanceof UninitializedObjectType))) {
/* 1375:1727 */       constraintViolated(o, "Possibly initializing object twice. A valid instruction sequence must not have an uninitialized object on the operand stack or in a local variable during a backwards branch, or in a local variable in code protected by an exception handler. Please see The Java Virtual Machine Specification, Second Edition, 4.9.4 (pages 147 and 148) for details.");
/* 1376:     */     }
/* 1377:1732 */     Type t = o.getType(this.cpg);
/* 1378:1733 */     if ((t instanceof ObjectType))
/* 1379:     */     {
/* 1380:1734 */       String name = ((ObjectType)t).getClassName();
/* 1381:1735 */       Verifier v = VerifierFactory.getVerifier(name);
/* 1382:1736 */       VerificationResult vr = v.doPass2();
/* 1383:1737 */       if (vr.getStatus() != 1) {
/* 1384:1738 */         constraintViolated(o, "Class '" + name + "' is referenced, but cannot be loaded and resolved: '" + vr + "'.");
/* 1385:     */       }
/* 1386:     */     }
/* 1387:1743 */     Type[] argtypes = o.getArgumentTypes(this.cpg);
/* 1388:1744 */     int nargs = argtypes.length;
/* 1389:1746 */     for (int i = nargs - 1; i >= 0; i--)
/* 1390:     */     {
/* 1391:1747 */       Type fromStack = stack().peek(nargs - 1 - i);
/* 1392:1748 */       Type fromDesc = argtypes[i];
/* 1393:1749 */       if ((fromDesc == Type.BOOLEAN) || (fromDesc == Type.BYTE) || (fromDesc == Type.CHAR) || (fromDesc == Type.SHORT)) {
/* 1394:1753 */         fromDesc = Type.INT;
/* 1395:     */       }
/* 1396:1755 */       if (!fromStack.equals(fromDesc))
/* 1397:     */       {
/* 1398:     */         ReferenceType rFromDesc;
/* 1399:1756 */         if (((fromStack instanceof ReferenceType)) && ((fromDesc instanceof ReferenceType)))
/* 1400:     */         {
/* 1401:1757 */           ReferenceType rFromStack = (ReferenceType)fromStack;
/* 1402:1758 */           rFromDesc = (ReferenceType)fromDesc;
/* 1403:     */         }
/* 1404:     */         else
/* 1405:     */         {
/* 1406:1766 */           constraintViolated(o, "Expecting a '" + fromDesc + "' but found a '" + fromStack + "' on the stack.");
/* 1407:     */         }
/* 1408:     */       }
/* 1409:     */     }
/* 1410:1771 */     Type objref = stack().peek(nargs);
/* 1411:1772 */     if (objref == Type.NULL) {
/* 1412:1773 */       return;
/* 1413:     */     }
/* 1414:1775 */     if (!(objref instanceof ReferenceType)) {
/* 1415:1776 */       constraintViolated(o, "Expecting a reference type as 'objectref' on the stack, not a '" + objref + "'.");
/* 1416:     */     }
/* 1417:1778 */     String objref_classname = null;
/* 1418:1779 */     if (!o.getMethodName(this.cpg).equals("<init>"))
/* 1419:     */     {
/* 1420:1780 */       referenceTypeIsInitialized(o, (ReferenceType)objref);
/* 1421:1781 */       if (!(objref instanceof ObjectType)) {
/* 1422:1782 */         if (!(objref instanceof ArrayType)) {
/* 1423:1783 */           constraintViolated(o, "Expecting an ObjectType as 'objectref' on the stack, not a '" + objref + "'.");
/* 1424:     */         } else {
/* 1425:1786 */           objref = GENERIC_ARRAY;
/* 1426:     */         }
/* 1427:     */       }
/* 1428:1790 */       objref_classname = ((ObjectType)objref).getClassName();
/* 1429:     */     }
/* 1430:     */     else
/* 1431:     */     {
/* 1432:1793 */       if (!(objref instanceof UninitializedObjectType)) {
/* 1433:1794 */         constraintViolated(o, "Expecting an UninitializedObjectType as 'objectref' on the stack, not a '" + objref + "'. Otherwise, you couldn't invoke a method since an array has no methods (not to speak of a return address).");
/* 1434:     */       }
/* 1435:1796 */       objref_classname = ((UninitializedObjectType)objref).getInitialized().getClassName();
/* 1436:     */     }
/* 1437:1800 */     String theClass = o.getClassName(this.cpg);
/* 1438:1801 */     if (!Repository.instanceOf(objref_classname, theClass)) {
/* 1439:1802 */       constraintViolated(o, "The 'objref' item '" + objref + "' does not implement '" + theClass + "' as expected.");
/* 1440:     */     }
/* 1441:     */   }
/* 1442:     */   
/* 1443:     */   public void visitINVOKESTATIC(INVOKESTATIC o)
/* 1444:     */   {
/* 1445:1813 */     Type t = o.getType(this.cpg);
/* 1446:1814 */     if ((t instanceof ObjectType))
/* 1447:     */     {
/* 1448:1815 */       String name = ((ObjectType)t).getClassName();
/* 1449:1816 */       Verifier v = VerifierFactory.getVerifier(name);
/* 1450:1817 */       VerificationResult vr = v.doPass2();
/* 1451:1818 */       if (vr.getStatus() != 1) {
/* 1452:1819 */         constraintViolated(o, "Class '" + name + "' is referenced, but cannot be loaded and resolved: '" + vr + "'.");
/* 1453:     */       }
/* 1454:     */     }
/* 1455:1823 */     Type[] argtypes = o.getArgumentTypes(this.cpg);
/* 1456:1824 */     int nargs = argtypes.length;
/* 1457:1826 */     for (int i = nargs - 1; i >= 0; i--)
/* 1458:     */     {
/* 1459:1827 */       Type fromStack = stack().peek(nargs - 1 - i);
/* 1460:1828 */       Type fromDesc = argtypes[i];
/* 1461:1829 */       if ((fromDesc == Type.BOOLEAN) || (fromDesc == Type.BYTE) || (fromDesc == Type.CHAR) || (fromDesc == Type.SHORT)) {
/* 1462:1833 */         fromDesc = Type.INT;
/* 1463:     */       }
/* 1464:1835 */       if (!fromStack.equals(fromDesc))
/* 1465:     */       {
/* 1466:     */         ReferenceType rFromDesc;
/* 1467:1836 */         if (((fromStack instanceof ReferenceType)) && ((fromDesc instanceof ReferenceType)))
/* 1468:     */         {
/* 1469:1837 */           ReferenceType rFromStack = (ReferenceType)fromStack;
/* 1470:1838 */           rFromDesc = (ReferenceType)fromDesc;
/* 1471:     */         }
/* 1472:     */         else
/* 1473:     */         {
/* 1474:1846 */           constraintViolated(o, "Expecting a '" + fromDesc + "' but found a '" + fromStack + "' on the stack.");
/* 1475:     */         }
/* 1476:     */       }
/* 1477:     */     }
/* 1478:     */   }
/* 1479:     */   
/* 1480:     */   public void visitINVOKEVIRTUAL(INVOKEVIRTUAL o)
/* 1481:     */   {
/* 1482:1858 */     Type t = o.getType(this.cpg);
/* 1483:1859 */     if ((t instanceof ObjectType))
/* 1484:     */     {
/* 1485:1860 */       String name = ((ObjectType)t).getClassName();
/* 1486:1861 */       Verifier v = VerifierFactory.getVerifier(name);
/* 1487:1862 */       VerificationResult vr = v.doPass2();
/* 1488:1863 */       if (vr.getStatus() != 1) {
/* 1489:1864 */         constraintViolated(o, "Class '" + name + "' is referenced, but cannot be loaded and resolved: '" + vr + "'.");
/* 1490:     */       }
/* 1491:     */     }
/* 1492:1869 */     Type[] argtypes = o.getArgumentTypes(this.cpg);
/* 1493:1870 */     int nargs = argtypes.length;
/* 1494:1872 */     for (int i = nargs - 1; i >= 0; i--)
/* 1495:     */     {
/* 1496:1873 */       Type fromStack = stack().peek(nargs - 1 - i);
/* 1497:1874 */       Type fromDesc = argtypes[i];
/* 1498:1875 */       if ((fromDesc == Type.BOOLEAN) || (fromDesc == Type.BYTE) || (fromDesc == Type.CHAR) || (fromDesc == Type.SHORT)) {
/* 1499:1879 */         fromDesc = Type.INT;
/* 1500:     */       }
/* 1501:1881 */       if (!fromStack.equals(fromDesc))
/* 1502:     */       {
/* 1503:     */         ReferenceType rFromDesc;
/* 1504:1882 */         if (((fromStack instanceof ReferenceType)) && ((fromDesc instanceof ReferenceType)))
/* 1505:     */         {
/* 1506:1883 */           ReferenceType rFromStack = (ReferenceType)fromStack;
/* 1507:1884 */           rFromDesc = (ReferenceType)fromDesc;
/* 1508:     */         }
/* 1509:     */         else
/* 1510:     */         {
/* 1511:1892 */           constraintViolated(o, "Expecting a '" + fromDesc + "' but found a '" + fromStack + "' on the stack.");
/* 1512:     */         }
/* 1513:     */       }
/* 1514:     */     }
/* 1515:1897 */     Type objref = stack().peek(nargs);
/* 1516:1898 */     if (objref == Type.NULL) {
/* 1517:1899 */       return;
/* 1518:     */     }
/* 1519:1901 */     if (!(objref instanceof ReferenceType)) {
/* 1520:1902 */       constraintViolated(o, "Expecting a reference type as 'objectref' on the stack, not a '" + objref + "'.");
/* 1521:     */     }
/* 1522:1904 */     referenceTypeIsInitialized(o, (ReferenceType)objref);
/* 1523:1905 */     if (!(objref instanceof ObjectType)) {
/* 1524:1906 */       if (!(objref instanceof ArrayType)) {
/* 1525:1907 */         constraintViolated(o, "Expecting an ObjectType as 'objectref' on the stack, not a '" + objref + "'.");
/* 1526:     */       } else {
/* 1527:1910 */         objref = GENERIC_ARRAY;
/* 1528:     */       }
/* 1529:     */     }
/* 1530:1914 */     String objref_classname = ((ObjectType)objref).getClassName();
/* 1531:     */     
/* 1532:1916 */     String theClass = o.getClassName(this.cpg);
/* 1533:1918 */     if (!Repository.instanceOf(objref_classname, theClass)) {
/* 1534:1919 */       constraintViolated(o, "The 'objref' item '" + objref + "' does not implement '" + theClass + "' as expected.");
/* 1535:     */     }
/* 1536:     */   }
/* 1537:     */   
/* 1538:     */   public void visitIOR(IOR o)
/* 1539:     */   {
/* 1540:1927 */     if (stack().peek() != Type.INT) {
/* 1541:1928 */       constraintViolated(o, "The value at the stack top is not of type 'int', but of type '" + stack().peek() + "'.");
/* 1542:     */     }
/* 1543:1930 */     if (stack().peek(1) != Type.INT) {
/* 1544:1931 */       constraintViolated(o, "The value at the stack next-to-top is not of type 'int', but of type '" + stack().peek(1) + "'.");
/* 1545:     */     }
/* 1546:     */   }
/* 1547:     */   
/* 1548:     */   public void visitIREM(IREM o)
/* 1549:     */   {
/* 1550:1939 */     if (stack().peek() != Type.INT) {
/* 1551:1940 */       constraintViolated(o, "The value at the stack top is not of type 'int', but of type '" + stack().peek() + "'.");
/* 1552:     */     }
/* 1553:1942 */     if (stack().peek(1) != Type.INT) {
/* 1554:1943 */       constraintViolated(o, "The value at the stack next-to-top is not of type 'int', but of type '" + stack().peek(1) + "'.");
/* 1555:     */     }
/* 1556:     */   }
/* 1557:     */   
/* 1558:     */   public void visitIRETURN(IRETURN o)
/* 1559:     */   {
/* 1560:1951 */     if (stack().peek() != Type.INT) {
/* 1561:1952 */       constraintViolated(o, "The value at the stack top is not of type 'int', but of type '" + stack().peek() + "'.");
/* 1562:     */     }
/* 1563:     */   }
/* 1564:     */   
/* 1565:     */   public void visitISHL(ISHL o)
/* 1566:     */   {
/* 1567:1960 */     if (stack().peek() != Type.INT) {
/* 1568:1961 */       constraintViolated(o, "The value at the stack top is not of type 'int', but of type '" + stack().peek() + "'.");
/* 1569:     */     }
/* 1570:1963 */     if (stack().peek(1) != Type.INT) {
/* 1571:1964 */       constraintViolated(o, "The value at the stack next-to-top is not of type 'int', but of type '" + stack().peek(1) + "'.");
/* 1572:     */     }
/* 1573:     */   }
/* 1574:     */   
/* 1575:     */   public void visitISHR(ISHR o)
/* 1576:     */   {
/* 1577:1972 */     if (stack().peek() != Type.INT) {
/* 1578:1973 */       constraintViolated(o, "The value at the stack top is not of type 'int', but of type '" + stack().peek() + "'.");
/* 1579:     */     }
/* 1580:1975 */     if (stack().peek(1) != Type.INT) {
/* 1581:1976 */       constraintViolated(o, "The value at the stack next-to-top is not of type 'int', but of type '" + stack().peek(1) + "'.");
/* 1582:     */     }
/* 1583:     */   }
/* 1584:     */   
/* 1585:     */   public void visitISTORE(ISTORE o) {}
/* 1586:     */   
/* 1587:     */   public void visitISUB(ISUB o)
/* 1588:     */   {
/* 1589:1993 */     if (stack().peek() != Type.INT) {
/* 1590:1994 */       constraintViolated(o, "The value at the stack top is not of type 'int', but of type '" + stack().peek() + "'.");
/* 1591:     */     }
/* 1592:1996 */     if (stack().peek(1) != Type.INT) {
/* 1593:1997 */       constraintViolated(o, "The value at the stack next-to-top is not of type 'int', but of type '" + stack().peek(1) + "'.");
/* 1594:     */     }
/* 1595:     */   }
/* 1596:     */   
/* 1597:     */   public void visitIUSHR(IUSHR o)
/* 1598:     */   {
/* 1599:2005 */     if (stack().peek() != Type.INT) {
/* 1600:2006 */       constraintViolated(o, "The value at the stack top is not of type 'int', but of type '" + stack().peek() + "'.");
/* 1601:     */     }
/* 1602:2008 */     if (stack().peek(1) != Type.INT) {
/* 1603:2009 */       constraintViolated(o, "The value at the stack next-to-top is not of type 'int', but of type '" + stack().peek(1) + "'.");
/* 1604:     */     }
/* 1605:     */   }
/* 1606:     */   
/* 1607:     */   public void visitIXOR(IXOR o)
/* 1608:     */   {
/* 1609:2017 */     if (stack().peek() != Type.INT) {
/* 1610:2018 */       constraintViolated(o, "The value at the stack top is not of type 'int', but of type '" + stack().peek() + "'.");
/* 1611:     */     }
/* 1612:2020 */     if (stack().peek(1) != Type.INT) {
/* 1613:2021 */       constraintViolated(o, "The value at the stack next-to-top is not of type 'int', but of type '" + stack().peek(1) + "'.");
/* 1614:     */     }
/* 1615:     */   }
/* 1616:     */   
/* 1617:     */   public void visitJSR(JSR o) {}
/* 1618:     */   
/* 1619:     */   public void visitJSR_W(JSR_W o) {}
/* 1620:     */   
/* 1621:     */   public void visitL2D(L2D o)
/* 1622:     */   {
/* 1623:2043 */     if (stack().peek() != Type.LONG) {
/* 1624:2044 */       constraintViolated(o, "The value at the stack top is not of type 'long', but of type '" + stack().peek() + "'.");
/* 1625:     */     }
/* 1626:     */   }
/* 1627:     */   
/* 1628:     */   public void visitL2F(L2F o)
/* 1629:     */   {
/* 1630:2052 */     if (stack().peek() != Type.LONG) {
/* 1631:2053 */       constraintViolated(o, "The value at the stack top is not of type 'long', but of type '" + stack().peek() + "'.");
/* 1632:     */     }
/* 1633:     */   }
/* 1634:     */   
/* 1635:     */   public void visitL2I(L2I o)
/* 1636:     */   {
/* 1637:2061 */     if (stack().peek() != Type.LONG) {
/* 1638:2062 */       constraintViolated(o, "The value at the stack top is not of type 'long', but of type '" + stack().peek() + "'.");
/* 1639:     */     }
/* 1640:     */   }
/* 1641:     */   
/* 1642:     */   public void visitLADD(LADD o)
/* 1643:     */   {
/* 1644:2070 */     if (stack().peek() != Type.LONG) {
/* 1645:2071 */       constraintViolated(o, "The value at the stack top is not of type 'long', but of type '" + stack().peek() + "'.");
/* 1646:     */     }
/* 1647:2073 */     if (stack().peek(1) != Type.LONG) {
/* 1648:2074 */       constraintViolated(o, "The value at the stack next-to-top is not of type 'long', but of type '" + stack().peek(1) + "'.");
/* 1649:     */     }
/* 1650:     */   }
/* 1651:     */   
/* 1652:     */   public void visitLALOAD(LALOAD o)
/* 1653:     */   {
/* 1654:2082 */     indexOfInt(o, stack().peek());
/* 1655:2083 */     if (stack().peek(1) == Type.NULL) {
/* 1656:2084 */       return;
/* 1657:     */     }
/* 1658:2086 */     if (!(stack().peek(1) instanceof ArrayType)) {
/* 1659:2087 */       constraintViolated(o, "Stack next-to-top must be of type long[] but is '" + stack().peek(1) + "'.");
/* 1660:     */     }
/* 1661:2089 */     Type t = ((ArrayType)stack().peek(1)).getBasicType();
/* 1662:2090 */     if (t != Type.LONG) {
/* 1663:2091 */       constraintViolated(o, "Stack next-to-top must be of type long[] but is '" + stack().peek(1) + "'.");
/* 1664:     */     }
/* 1665:     */   }
/* 1666:     */   
/* 1667:     */   public void visitLAND(LAND o)
/* 1668:     */   {
/* 1669:2099 */     if (stack().peek() != Type.LONG) {
/* 1670:2100 */       constraintViolated(o, "The value at the stack top is not of type 'long', but of type '" + stack().peek() + "'.");
/* 1671:     */     }
/* 1672:2102 */     if (stack().peek(1) != Type.LONG) {
/* 1673:2103 */       constraintViolated(o, "The value at the stack next-to-top is not of type 'long', but of type '" + stack().peek(1) + "'.");
/* 1674:     */     }
/* 1675:     */   }
/* 1676:     */   
/* 1677:     */   public void visitLASTORE(LASTORE o)
/* 1678:     */   {
/* 1679:2111 */     if (stack().peek() != Type.LONG) {
/* 1680:2112 */       constraintViolated(o, "The value at the stack top is not of type 'long', but of type '" + stack().peek() + "'.");
/* 1681:     */     }
/* 1682:2114 */     indexOfInt(o, stack().peek(1));
/* 1683:2115 */     if (stack().peek(2) == Type.NULL) {
/* 1684:2116 */       return;
/* 1685:     */     }
/* 1686:2118 */     if (!(stack().peek(2) instanceof ArrayType)) {
/* 1687:2119 */       constraintViolated(o, "Stack next-to-next-to-top must be of type long[] but is '" + stack().peek(2) + "'.");
/* 1688:     */     }
/* 1689:2121 */     Type t = ((ArrayType)stack().peek(2)).getBasicType();
/* 1690:2122 */     if (t != Type.LONG) {
/* 1691:2123 */       constraintViolated(o, "Stack next-to-next-to-top must be of type long[] but is '" + stack().peek(2) + "'.");
/* 1692:     */     }
/* 1693:     */   }
/* 1694:     */   
/* 1695:     */   public void visitLCMP(LCMP o)
/* 1696:     */   {
/* 1697:2131 */     if (stack().peek() != Type.LONG) {
/* 1698:2132 */       constraintViolated(o, "The value at the stack top is not of type 'long', but of type '" + stack().peek() + "'.");
/* 1699:     */     }
/* 1700:2134 */     if (stack().peek(1) != Type.LONG) {
/* 1701:2135 */       constraintViolated(o, "The value at the stack next-to-top is not of type 'long', but of type '" + stack().peek(1) + "'.");
/* 1702:     */     }
/* 1703:     */   }
/* 1704:     */   
/* 1705:     */   public void visitLCONST(LCONST o) {}
/* 1706:     */   
/* 1707:     */   public void visitLDC(LDC o)
/* 1708:     */   {
/* 1709:2152 */     Constant c = this.cpg.getConstant(o.getIndex());
/* 1710:2153 */     if ((!(c instanceof ConstantInteger)) && (!(c instanceof ConstantFloat)) && (!(c instanceof ConstantString))) {
/* 1711:2156 */       constraintViolated(o, "Referenced constant should be a CONSTANT_Integer, a CONSTANT_Float or a CONSTANT_String, but is '" + c + "'.");
/* 1712:     */     }
/* 1713:     */   }
/* 1714:     */   
/* 1715:     */   public void visitLDC_W(LDC_W o)
/* 1716:     */   {
/* 1717:2166 */     Constant c = this.cpg.getConstant(o.getIndex());
/* 1718:2167 */     if ((!(c instanceof ConstantInteger)) && (!(c instanceof ConstantFloat)) && (!(c instanceof ConstantString))) {
/* 1719:2170 */       constraintViolated(o, "Referenced constant should be a CONSTANT_Integer, a CONSTANT_Float or a CONSTANT_String, but is '" + c + "'.");
/* 1720:     */     }
/* 1721:     */   }
/* 1722:     */   
/* 1723:     */   public void visitLDC2_W(LDC2_W o)
/* 1724:     */   {
/* 1725:2180 */     Constant c = this.cpg.getConstant(o.getIndex());
/* 1726:2181 */     if ((!(c instanceof ConstantLong)) && (!(c instanceof ConstantDouble))) {
/* 1727:2183 */       constraintViolated(o, "Referenced constant should be a CONSTANT_Integer, a CONSTANT_Float or a CONSTANT_String, but is '" + c + "'.");
/* 1728:     */     }
/* 1729:     */   }
/* 1730:     */   
/* 1731:     */   public void visitLDIV(LDIV o)
/* 1732:     */   {
/* 1733:2191 */     if (stack().peek() != Type.LONG) {
/* 1734:2192 */       constraintViolated(o, "The value at the stack top is not of type 'long', but of type '" + stack().peek() + "'.");
/* 1735:     */     }
/* 1736:2194 */     if (stack().peek(1) != Type.LONG) {
/* 1737:2195 */       constraintViolated(o, "The value at the stack next-to-top is not of type 'long', but of type '" + stack().peek(1) + "'.");
/* 1738:     */     }
/* 1739:     */   }
/* 1740:     */   
/* 1741:     */   public void visitLLOAD(LLOAD o) {}
/* 1742:     */   
/* 1743:     */   public void visitLMUL(LMUL o)
/* 1744:     */   {
/* 1745:2212 */     if (stack().peek() != Type.LONG) {
/* 1746:2213 */       constraintViolated(o, "The value at the stack top is not of type 'long', but of type '" + stack().peek() + "'.");
/* 1747:     */     }
/* 1748:2215 */     if (stack().peek(1) != Type.LONG) {
/* 1749:2216 */       constraintViolated(o, "The value at the stack next-to-top is not of type 'long', but of type '" + stack().peek(1) + "'.");
/* 1750:     */     }
/* 1751:     */   }
/* 1752:     */   
/* 1753:     */   public void visitLNEG(LNEG o)
/* 1754:     */   {
/* 1755:2224 */     if (stack().peek() != Type.LONG) {
/* 1756:2225 */       constraintViolated(o, "The value at the stack top is not of type 'long', but of type '" + stack().peek() + "'.");
/* 1757:     */     }
/* 1758:     */   }
/* 1759:     */   
/* 1760:     */   public void visitLOOKUPSWITCH(LOOKUPSWITCH o)
/* 1761:     */   {
/* 1762:2233 */     if (stack().peek() != Type.INT) {
/* 1763:2234 */       constraintViolated(o, "The value at the stack top is not of type 'int', but of type '" + stack().peek() + "'.");
/* 1764:     */     }
/* 1765:     */   }
/* 1766:     */   
/* 1767:     */   public void visitLOR(LOR o)
/* 1768:     */   {
/* 1769:2243 */     if (stack().peek() != Type.LONG) {
/* 1770:2244 */       constraintViolated(o, "The value at the stack top is not of type 'long', but of type '" + stack().peek() + "'.");
/* 1771:     */     }
/* 1772:2246 */     if (stack().peek(1) != Type.LONG) {
/* 1773:2247 */       constraintViolated(o, "The value at the stack next-to-top is not of type 'long', but of type '" + stack().peek(1) + "'.");
/* 1774:     */     }
/* 1775:     */   }
/* 1776:     */   
/* 1777:     */   public void visitLREM(LREM o)
/* 1778:     */   {
/* 1779:2255 */     if (stack().peek() != Type.LONG) {
/* 1780:2256 */       constraintViolated(o, "The value at the stack top is not of type 'long', but of type '" + stack().peek() + "'.");
/* 1781:     */     }
/* 1782:2258 */     if (stack().peek(1) != Type.LONG) {
/* 1783:2259 */       constraintViolated(o, "The value at the stack next-to-top is not of type 'long', but of type '" + stack().peek(1) + "'.");
/* 1784:     */     }
/* 1785:     */   }
/* 1786:     */   
/* 1787:     */   public void visitLRETURN(LRETURN o)
/* 1788:     */   {
/* 1789:2267 */     if (stack().peek() != Type.LONG) {
/* 1790:2268 */       constraintViolated(o, "The value at the stack top is not of type 'long', but of type '" + stack().peek() + "'.");
/* 1791:     */     }
/* 1792:     */   }
/* 1793:     */   
/* 1794:     */   public void visitLSHL(LSHL o)
/* 1795:     */   {
/* 1796:2276 */     if (stack().peek() != Type.INT) {
/* 1797:2277 */       constraintViolated(o, "The value at the stack top is not of type 'int', but of type '" + stack().peek() + "'.");
/* 1798:     */     }
/* 1799:2279 */     if (stack().peek(1) != Type.LONG) {
/* 1800:2280 */       constraintViolated(o, "The value at the stack next-to-top is not of type 'long', but of type '" + stack().peek(1) + "'.");
/* 1801:     */     }
/* 1802:     */   }
/* 1803:     */   
/* 1804:     */   public void visitLSHR(LSHR o)
/* 1805:     */   {
/* 1806:2288 */     if (stack().peek() != Type.INT) {
/* 1807:2289 */       constraintViolated(o, "The value at the stack top is not of type 'int', but of type '" + stack().peek() + "'.");
/* 1808:     */     }
/* 1809:2291 */     if (stack().peek(1) != Type.LONG) {
/* 1810:2292 */       constraintViolated(o, "The value at the stack next-to-top is not of type 'long', but of type '" + stack().peek(1) + "'.");
/* 1811:     */     }
/* 1812:     */   }
/* 1813:     */   
/* 1814:     */   public void visitLSTORE(LSTORE o) {}
/* 1815:     */   
/* 1816:     */   public void visitLSUB(LSUB o)
/* 1817:     */   {
/* 1818:2309 */     if (stack().peek() != Type.LONG) {
/* 1819:2310 */       constraintViolated(o, "The value at the stack top is not of type 'long', but of type '" + stack().peek() + "'.");
/* 1820:     */     }
/* 1821:2312 */     if (stack().peek(1) != Type.LONG) {
/* 1822:2313 */       constraintViolated(o, "The value at the stack next-to-top is not of type 'long', but of type '" + stack().peek(1) + "'.");
/* 1823:     */     }
/* 1824:     */   }
/* 1825:     */   
/* 1826:     */   public void visitLUSHR(LUSHR o)
/* 1827:     */   {
/* 1828:2321 */     if (stack().peek() != Type.INT) {
/* 1829:2322 */       constraintViolated(o, "The value at the stack top is not of type 'int', but of type '" + stack().peek() + "'.");
/* 1830:     */     }
/* 1831:2324 */     if (stack().peek(1) != Type.LONG) {
/* 1832:2325 */       constraintViolated(o, "The value at the stack next-to-top is not of type 'long', but of type '" + stack().peek(1) + "'.");
/* 1833:     */     }
/* 1834:     */   }
/* 1835:     */   
/* 1836:     */   public void visitLXOR(LXOR o)
/* 1837:     */   {
/* 1838:2333 */     if (stack().peek() != Type.LONG) {
/* 1839:2334 */       constraintViolated(o, "The value at the stack top is not of type 'long', but of type '" + stack().peek() + "'.");
/* 1840:     */     }
/* 1841:2336 */     if (stack().peek(1) != Type.LONG) {
/* 1842:2337 */       constraintViolated(o, "The value at the stack next-to-top is not of type 'long', but of type '" + stack().peek(1) + "'.");
/* 1843:     */     }
/* 1844:     */   }
/* 1845:     */   
/* 1846:     */   public void visitMONITORENTER(MONITORENTER o)
/* 1847:     */   {
/* 1848:2345 */     if (!(stack().peek() instanceof ReferenceType)) {
/* 1849:2346 */       constraintViolated(o, "The stack top should be of a ReferenceType, but is '" + stack().peek() + "'.");
/* 1850:     */     }
/* 1851:2348 */     referenceTypeIsInitialized(o, (ReferenceType)stack().peek());
/* 1852:     */   }
/* 1853:     */   
/* 1854:     */   public void visitMONITOREXIT(MONITOREXIT o)
/* 1855:     */   {
/* 1856:2355 */     if (!(stack().peek() instanceof ReferenceType)) {
/* 1857:2356 */       constraintViolated(o, "The stack top should be of a ReferenceType, but is '" + stack().peek() + "'.");
/* 1858:     */     }
/* 1859:2358 */     referenceTypeIsInitialized(o, (ReferenceType)stack().peek());
/* 1860:     */   }
/* 1861:     */   
/* 1862:     */   public void visitMULTIANEWARRAY(MULTIANEWARRAY o)
/* 1863:     */   {
/* 1864:2365 */     int dimensions = o.getDimensions();
/* 1865:2367 */     for (int i = 0; i < dimensions; i++) {
/* 1866:2368 */       if (stack().peek(i) != Type.INT) {
/* 1867:2369 */         constraintViolated(o, "The '" + dimensions + "' upper stack types should be 'int' but aren't.");
/* 1868:     */       }
/* 1869:     */     }
/* 1870:     */   }
/* 1871:     */   
/* 1872:     */   public void visitNEW(NEW o)
/* 1873:     */   {
/* 1874:2383 */     Type t = o.getType(this.cpg);
/* 1875:2384 */     if (!(t instanceof ReferenceType)) {
/* 1876:2385 */       throw new AssertionViolatedException("NEW.getType() returning a non-reference type?!");
/* 1877:     */     }
/* 1878:2387 */     if (!(t instanceof ObjectType)) {
/* 1879:2388 */       constraintViolated(o, "Expecting a class type (ObjectType) to work on. Found: '" + t + "'.");
/* 1880:     */     }
/* 1881:2390 */     ObjectType obj = (ObjectType)t;
/* 1882:2393 */     if (!obj.referencesClass()) {
/* 1883:2394 */       constraintViolated(o, "Expecting a class type (ObjectType) to work on. Found: '" + obj + "'.");
/* 1884:     */     }
/* 1885:     */   }
/* 1886:     */   
/* 1887:     */   public void visitNEWARRAY(NEWARRAY o)
/* 1888:     */   {
/* 1889:2402 */     if (stack().peek() != Type.INT) {
/* 1890:2403 */       constraintViolated(o, "The value at the stack top is not of type 'int', but of type '" + stack().peek() + "'.");
/* 1891:     */     }
/* 1892:     */   }
/* 1893:     */   
/* 1894:     */   public void visitNOP(NOP o) {}
/* 1895:     */   
/* 1896:     */   public void visitPOP(POP o)
/* 1897:     */   {
/* 1898:2418 */     if (stack().peek().getSize() != 1) {
/* 1899:2419 */       constraintViolated(o, "Stack top size should be 1 but stack top is '" + stack().peek() + "' of size '" + stack().peek().getSize() + "'.");
/* 1900:     */     }
/* 1901:     */   }
/* 1902:     */   
/* 1903:     */   public void visitPOP2(POP2 o)
/* 1904:     */   {
/* 1905:2427 */     if (stack().peek().getSize() != 2) {
/* 1906:2428 */       constraintViolated(o, "Stack top size should be 2 but stack top is '" + stack().peek() + "' of size '" + stack().peek().getSize() + "'.");
/* 1907:     */     }
/* 1908:     */   }
/* 1909:     */   
/* 1910:     */   public void visitPUTFIELD(PUTFIELD o)
/* 1911:     */   {
/* 1912:2437 */     Type objectref = stack().peek(1);
/* 1913:2438 */     if ((!(objectref instanceof ObjectType)) && (objectref != Type.NULL)) {
/* 1914:2439 */       constraintViolated(o, "Stack next-to-top should be an object reference that's not an array reference, but is '" + objectref + "'.");
/* 1915:     */     }
/* 1916:2442 */     String field_name = o.getFieldName(this.cpg);
/* 1917:     */     
/* 1918:2444 */     JavaClass jc = Repository.lookupClass(o.getClassType(this.cpg).getClassName());
/* 1919:2445 */     Field[] fields = jc.getFields();
/* 1920:2446 */     Field f = null;
/* 1921:2447 */     for (int i = 0; i < fields.length; i++) {
/* 1922:2448 */       if (fields[i].getName().equals(field_name))
/* 1923:     */       {
/* 1924:2449 */         f = fields[i];
/* 1925:2450 */         break;
/* 1926:     */       }
/* 1927:     */     }
/* 1928:2453 */     if (f == null) {
/* 1929:2454 */       throw new AssertionViolatedException("Field not found?!?");
/* 1930:     */     }
/* 1931:2457 */     Type value = stack().peek();
/* 1932:2458 */     Type t = Type.getType(f.getSignature());
/* 1933:2459 */     Type shouldbe = t;
/* 1934:2460 */     if ((shouldbe == Type.BOOLEAN) || (shouldbe == Type.BYTE) || (shouldbe == Type.CHAR) || (shouldbe == Type.SHORT)) {
/* 1935:2464 */       shouldbe = Type.INT;
/* 1936:     */     }
/* 1937:2466 */     if ((t instanceof ReferenceType))
/* 1938:     */     {
/* 1939:2467 */       ReferenceType rvalue = null;
/* 1940:2468 */       if ((value instanceof ReferenceType))
/* 1941:     */       {
/* 1942:2469 */         rvalue = (ReferenceType)value;
/* 1943:2470 */         referenceTypeIsInitialized(o, rvalue);
/* 1944:     */       }
/* 1945:     */       else
/* 1946:     */       {
/* 1947:2473 */         constraintViolated(o, "The stack top type '" + value + "' is not of a reference type as expected.");
/* 1948:     */       }
/* 1949:     */     }
/* 1950:2482 */     else if (shouldbe != value)
/* 1951:     */     {
/* 1952:2483 */       constraintViolated(o, "The stack top type '" + value + "' is not of type '" + shouldbe + "' as expected.");
/* 1953:     */     }
/* 1954:2487 */     if (f.isProtected())
/* 1955:     */     {
/* 1956:2488 */       ObjectType classtype = o.getClassType(this.cpg);
/* 1957:2489 */       ObjectType curr = new ObjectType(this.mg.getClassName());
/* 1958:2491 */       if ((classtype.equals(curr)) || (curr.subclassOf(classtype)))
/* 1959:     */       {
/* 1960:2493 */         Type tp = stack().peek(1);
/* 1961:2494 */         if (tp == Type.NULL) {
/* 1962:2495 */           return;
/* 1963:     */         }
/* 1964:2497 */         if (!(tp instanceof ObjectType)) {
/* 1965:2498 */           constraintViolated(o, "The 'objectref' must refer to an object that's not an array. Found instead: '" + tp + "'.");
/* 1966:     */         }
/* 1967:2500 */         ObjectType objreftype = (ObjectType)tp;
/* 1968:2501 */         if ((!objreftype.equals(curr)) && (!objreftype.subclassOf(curr))) {
/* 1969:2503 */           constraintViolated(o, "The referenced field has the ACC_PROTECTED modifier, and it's a member of the current class or a superclass of the current class. However, the referenced object type '" + stack().peek() + "' is not the current class or a subclass of the current class.");
/* 1970:     */         }
/* 1971:     */       }
/* 1972:     */     }
/* 1973:2509 */     if (f.isStatic()) {
/* 1974:2510 */       constraintViolated(o, "Referenced field '" + f + "' is static which it shouldn't be.");
/* 1975:     */     }
/* 1976:     */   }
/* 1977:     */   
/* 1978:     */   public void visitPUTSTATIC(PUTSTATIC o)
/* 1979:     */   {
/* 1980:2518 */     String field_name = o.getFieldName(this.cpg);
/* 1981:2519 */     JavaClass jc = Repository.lookupClass(o.getClassType(this.cpg).getClassName());
/* 1982:2520 */     Field[] fields = jc.getFields();
/* 1983:2521 */     Field f = null;
/* 1984:2522 */     for (int i = 0; i < fields.length; i++) {
/* 1985:2523 */       if (fields[i].getName().equals(field_name))
/* 1986:     */       {
/* 1987:2524 */         f = fields[i];
/* 1988:2525 */         break;
/* 1989:     */       }
/* 1990:     */     }
/* 1991:2528 */     if (f == null) {
/* 1992:2529 */       throw new AssertionViolatedException("Field not found?!?");
/* 1993:     */     }
/* 1994:2531 */     Type value = stack().peek();
/* 1995:2532 */     Type t = Type.getType(f.getSignature());
/* 1996:2533 */     Type shouldbe = t;
/* 1997:2534 */     if ((shouldbe == Type.BOOLEAN) || (shouldbe == Type.BYTE) || (shouldbe == Type.CHAR) || (shouldbe == Type.SHORT)) {
/* 1998:2538 */       shouldbe = Type.INT;
/* 1999:     */     }
/* 2000:2540 */     if ((t instanceof ReferenceType))
/* 2001:     */     {
/* 2002:2541 */       ReferenceType rvalue = null;
/* 2003:2542 */       if ((value instanceof ReferenceType))
/* 2004:     */       {
/* 2005:2543 */         rvalue = (ReferenceType)value;
/* 2006:2544 */         referenceTypeIsInitialized(o, rvalue);
/* 2007:     */       }
/* 2008:     */       else
/* 2009:     */       {
/* 2010:2547 */         constraintViolated(o, "The stack top type '" + value + "' is not of a reference type as expected.");
/* 2011:     */       }
/* 2012:2549 */       if (!rvalue.isAssignmentCompatibleWith(shouldbe)) {
/* 2013:2550 */         constraintViolated(o, "The stack top type '" + value + "' is not assignment compatible with '" + shouldbe + "'.");
/* 2014:     */       }
/* 2015:     */     }
/* 2016:2554 */     else if (shouldbe != value)
/* 2017:     */     {
/* 2018:2555 */       constraintViolated(o, "The stack top type '" + value + "' is not of type '" + shouldbe + "' as expected.");
/* 2019:     */     }
/* 2020:     */   }
/* 2021:     */   
/* 2022:     */   public void visitRET(RET o)
/* 2023:     */   {
/* 2024:2566 */     if (!(locals().get(o.getIndex()) instanceof ReturnaddressType)) {
/* 2025:2567 */       constraintViolated(o, "Expecting a ReturnaddressType in local variable " + o.getIndex() + ".");
/* 2026:     */     }
/* 2027:2569 */     if (locals().get(o.getIndex()) == ReturnaddressType.NO_TARGET) {
/* 2028:2570 */       throw new AssertionViolatedException("Oops: RET expecting a target!");
/* 2029:     */     }
/* 2030:     */   }
/* 2031:     */   
/* 2032:     */   public void visitRETURN(RETURN o)
/* 2033:     */   {
/* 2034:2580 */     if ((this.mg.getName().equals("<init>")) && 
/* 2035:2581 */       (Frame._this != null) && (!this.mg.getClassName().equals(Type.OBJECT.getClassName()))) {
/* 2036:2582 */       constraintViolated(o, "Leaving a constructor that itself did not call a constructor.");
/* 2037:     */     }
/* 2038:     */   }
/* 2039:     */   
/* 2040:     */   public void visitSALOAD(SALOAD o)
/* 2041:     */   {
/* 2042:2591 */     indexOfInt(o, stack().peek());
/* 2043:2592 */     if (stack().peek(1) == Type.NULL) {
/* 2044:2593 */       return;
/* 2045:     */     }
/* 2046:2595 */     if (!(stack().peek(1) instanceof ArrayType)) {
/* 2047:2596 */       constraintViolated(o, "Stack next-to-top must be of type short[] but is '" + stack().peek(1) + "'.");
/* 2048:     */     }
/* 2049:2598 */     Type t = ((ArrayType)stack().peek(1)).getBasicType();
/* 2050:2599 */     if (t != Type.SHORT) {
/* 2051:2600 */       constraintViolated(o, "Stack next-to-top must be of type short[] but is '" + stack().peek(1) + "'.");
/* 2052:     */     }
/* 2053:     */   }
/* 2054:     */   
/* 2055:     */   public void visitSASTORE(SASTORE o)
/* 2056:     */   {
/* 2057:2608 */     if (stack().peek() != Type.INT) {
/* 2058:2609 */       constraintViolated(o, "The value at the stack top is not of type 'int', but of type '" + stack().peek() + "'.");
/* 2059:     */     }
/* 2060:2611 */     indexOfInt(o, stack().peek(1));
/* 2061:2612 */     if (stack().peek(2) == Type.NULL) {
/* 2062:2613 */       return;
/* 2063:     */     }
/* 2064:2615 */     if (!(stack().peek(2) instanceof ArrayType)) {
/* 2065:2616 */       constraintViolated(o, "Stack next-to-next-to-top must be of type short[] but is '" + stack().peek(2) + "'.");
/* 2066:     */     }
/* 2067:2618 */     Type t = ((ArrayType)stack().peek(2)).getBasicType();
/* 2068:2619 */     if (t != Type.SHORT) {
/* 2069:2620 */       constraintViolated(o, "Stack next-to-next-to-top must be of type short[] but is '" + stack().peek(2) + "'.");
/* 2070:     */     }
/* 2071:     */   }
/* 2072:     */   
/* 2073:     */   public void visitSIPUSH(SIPUSH o) {}
/* 2074:     */   
/* 2075:     */   public void visitSWAP(SWAP o)
/* 2076:     */   {
/* 2077:2635 */     if (stack().peek().getSize() != 1) {
/* 2078:2636 */       constraintViolated(o, "The value at the stack top is not of size '1', but of size '" + stack().peek().getSize() + "'.");
/* 2079:     */     }
/* 2080:2638 */     if (stack().peek(1).getSize() != 1) {
/* 2081:2639 */       constraintViolated(o, "The value at the stack next-to-top is not of size '1', but of size '" + stack().peek(1).getSize() + "'.");
/* 2082:     */     }
/* 2083:     */   }
/* 2084:     */   
/* 2085:     */   public void visitTABLESWITCH(TABLESWITCH o)
/* 2086:     */   {
/* 2087:2647 */     indexOfInt(o, stack().peek());
/* 2088:     */   }
/* 2089:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.verifier.structurals.InstConstraintVisitor
 * JD-Core Version:    0.7.0.1
 */