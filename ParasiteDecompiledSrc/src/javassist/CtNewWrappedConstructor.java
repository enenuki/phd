/*  1:   */ package javassist;
/*  2:   */ 
/*  3:   */ import javassist.bytecode.Bytecode;
/*  4:   */ import javassist.bytecode.ClassFile;
/*  5:   */ import javassist.bytecode.Descriptor;
/*  6:   */ import javassist.bytecode.MethodInfo;
/*  7:   */ 
/*  8:   */ class CtNewWrappedConstructor
/*  9:   */   extends CtNewWrappedMethod
/* 10:   */ {
/* 11:   */   private static final int PASS_NONE = 0;
/* 12:   */   private static final int PASS_PARAMS = 2;
/* 13:   */   
/* 14:   */   public static CtConstructor wrapped(CtClass[] parameterTypes, CtClass[] exceptionTypes, int howToCallSuper, CtMethod body, CtMethod.ConstParameter constParam, CtClass declaring)
/* 15:   */     throws CannotCompileException
/* 16:   */   {
/* 17:   */     try
/* 18:   */     {
/* 19:35 */       CtConstructor cons = new CtConstructor(parameterTypes, declaring);
/* 20:36 */       cons.setExceptionTypes(exceptionTypes);
/* 21:37 */       Bytecode code = makeBody(declaring, declaring.getClassFile2(), howToCallSuper, body, parameterTypes, constParam);
/* 22:   */       
/* 23:   */ 
/* 24:40 */       cons.getMethodInfo2().setCodeAttribute(code.toCodeAttribute());
/* 25:41 */       return cons;
/* 26:   */     }
/* 27:   */     catch (NotFoundException e)
/* 28:   */     {
/* 29:44 */       throw new CannotCompileException(e);
/* 30:   */     }
/* 31:   */   }
/* 32:   */   
/* 33:   */   protected static Bytecode makeBody(CtClass declaring, ClassFile classfile, int howToCallSuper, CtMethod wrappedBody, CtClass[] parameters, CtMethod.ConstParameter cparam)
/* 34:   */     throws CannotCompileException
/* 35:   */   {
/* 36:57 */     int superclazz = classfile.getSuperclassId();
/* 37:58 */     Bytecode code = new Bytecode(classfile.getConstPool(), 0, 0);
/* 38:59 */     code.setMaxLocals(false, parameters, 0);
/* 39:60 */     code.addAload(0);
/* 40:   */     int stacksize;
/* 41:61 */     if (howToCallSuper == 0)
/* 42:   */     {
/* 43:62 */       int stacksize = 1;
/* 44:63 */       code.addInvokespecial(superclazz, "<init>", "()V");
/* 45:   */     }
/* 46:65 */     else if (howToCallSuper == 2)
/* 47:   */     {
/* 48:66 */       int stacksize = code.addLoadParameters(parameters, 1) + 1;
/* 49:67 */       code.addInvokespecial(superclazz, "<init>", Descriptor.ofConstructor(parameters));
/* 50:   */     }
/* 51:   */     else
/* 52:   */     {
/* 53:71 */       stacksize = compileParameterList(code, parameters, 1);
/* 54:   */       String desc;
/* 55:   */       int stacksize2;
/* 56:   */       String desc;
/* 57:73 */       if (cparam == null)
/* 58:   */       {
/* 59:74 */         int stacksize2 = 2;
/* 60:75 */         desc = CtMethod.ConstParameter.defaultConstDescriptor();
/* 61:   */       }
/* 62:   */       else
/* 63:   */       {
/* 64:78 */         stacksize2 = cparam.compile(code) + 2;
/* 65:79 */         desc = cparam.constDescriptor();
/* 66:   */       }
/* 67:82 */       if (stacksize < stacksize2) {
/* 68:83 */         stacksize = stacksize2;
/* 69:   */       }
/* 70:85 */       code.addInvokespecial(superclazz, "<init>", desc);
/* 71:   */     }
/* 72:88 */     if (wrappedBody == null)
/* 73:   */     {
/* 74:89 */       code.add(177);
/* 75:   */     }
/* 76:   */     else
/* 77:   */     {
/* 78:91 */       int stacksize2 = makeBody0(declaring, classfile, wrappedBody, false, parameters, CtClass.voidType, cparam, code);
/* 79:94 */       if (stacksize < stacksize2) {
/* 80:95 */         stacksize = stacksize2;
/* 81:   */       }
/* 82:   */     }
/* 83:98 */     code.setMaxStack(stacksize);
/* 84:99 */     return code;
/* 85:   */   }
/* 86:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.CtNewWrappedConstructor
 * JD-Core Version:    0.7.0.1
 */