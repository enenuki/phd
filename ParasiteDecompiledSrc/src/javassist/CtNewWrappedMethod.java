/*   1:    */ package javassist;
/*   2:    */ 
/*   3:    */ import java.util.Hashtable;
/*   4:    */ import javassist.bytecode.AccessFlag;
/*   5:    */ import javassist.bytecode.BadBytecode;
/*   6:    */ import javassist.bytecode.Bytecode;
/*   7:    */ import javassist.bytecode.ClassFile;
/*   8:    */ import javassist.bytecode.MethodInfo;
/*   9:    */ import javassist.bytecode.SyntheticAttribute;
/*  10:    */ import javassist.compiler.JvstCodeGen;
/*  11:    */ 
/*  12:    */ class CtNewWrappedMethod
/*  13:    */ {
/*  14:    */   private static final String addedWrappedMethod = "_added_m$";
/*  15:    */   
/*  16:    */   public static CtMethod wrapped(CtClass returnType, String mname, CtClass[] parameterTypes, CtClass[] exceptionTypes, CtMethod body, CtMethod.ConstParameter constParam, CtClass declaring)
/*  17:    */     throws CannotCompileException
/*  18:    */   {
/*  19: 34 */     CtMethod mt = new CtMethod(returnType, mname, parameterTypes, declaring);
/*  20:    */     
/*  21: 36 */     mt.setModifiers(body.getModifiers());
/*  22:    */     try
/*  23:    */     {
/*  24: 38 */       mt.setExceptionTypes(exceptionTypes);
/*  25:    */     }
/*  26:    */     catch (NotFoundException e)
/*  27:    */     {
/*  28: 41 */       throw new CannotCompileException(e);
/*  29:    */     }
/*  30: 44 */     Bytecode code = makeBody(declaring, declaring.getClassFile2(), body, parameterTypes, returnType, constParam);
/*  31:    */     
/*  32: 46 */     mt.getMethodInfo2().setCodeAttribute(code.toCodeAttribute());
/*  33: 47 */     return mt;
/*  34:    */   }
/*  35:    */   
/*  36:    */   static Bytecode makeBody(CtClass clazz, ClassFile classfile, CtMethod wrappedBody, CtClass[] parameters, CtClass returnType, CtMethod.ConstParameter cparam)
/*  37:    */     throws CannotCompileException
/*  38:    */   {
/*  39: 57 */     boolean isStatic = Modifier.isStatic(wrappedBody.getModifiers());
/*  40: 58 */     Bytecode code = new Bytecode(classfile.getConstPool(), 0, 0);
/*  41: 59 */     int stacksize = makeBody0(clazz, classfile, wrappedBody, isStatic, parameters, returnType, cparam, code);
/*  42:    */     
/*  43: 61 */     code.setMaxStack(stacksize);
/*  44: 62 */     code.setMaxLocals(isStatic, parameters, 0);
/*  45: 63 */     return code;
/*  46:    */   }
/*  47:    */   
/*  48:    */   protected static int makeBody0(CtClass clazz, ClassFile classfile, CtMethod wrappedBody, boolean isStatic, CtClass[] parameters, CtClass returnType, CtMethod.ConstParameter cparam, Bytecode code)
/*  49:    */     throws CannotCompileException
/*  50:    */   {
/*  51: 76 */     if (!(clazz instanceof CtClassType)) {
/*  52: 77 */       throw new CannotCompileException("bad declaring class" + clazz.getName());
/*  53:    */     }
/*  54: 80 */     if (!isStatic) {
/*  55: 81 */       code.addAload(0);
/*  56:    */     }
/*  57: 83 */     int stacksize = compileParameterList(code, parameters, isStatic ? 0 : 1);
/*  58:    */     String desc;
/*  59:    */     int stacksize2;
/*  60:    */     String desc;
/*  61: 87 */     if (cparam == null)
/*  62:    */     {
/*  63: 88 */       int stacksize2 = 0;
/*  64: 89 */       desc = CtMethod.ConstParameter.defaultDescriptor();
/*  65:    */     }
/*  66:    */     else
/*  67:    */     {
/*  68: 92 */       stacksize2 = cparam.compile(code);
/*  69: 93 */       desc = cparam.descriptor();
/*  70:    */     }
/*  71: 96 */     checkSignature(wrappedBody, desc);
/*  72:    */     String bodyname;
/*  73:    */     try
/*  74:    */     {
/*  75:100 */       bodyname = addBodyMethod((CtClassType)clazz, classfile, wrappedBody);
/*  76:    */     }
/*  77:    */     catch (BadBytecode e)
/*  78:    */     {
/*  79:107 */       throw new CannotCompileException(e);
/*  80:    */     }
/*  81:110 */     if (isStatic) {
/*  82:111 */       code.addInvokestatic(Bytecode.THIS, bodyname, desc);
/*  83:    */     } else {
/*  84:113 */       code.addInvokespecial(Bytecode.THIS, bodyname, desc);
/*  85:    */     }
/*  86:115 */     compileReturn(code, returnType);
/*  87:117 */     if (stacksize < stacksize2 + 2) {
/*  88:118 */       stacksize = stacksize2 + 2;
/*  89:    */     }
/*  90:120 */     return stacksize;
/*  91:    */   }
/*  92:    */   
/*  93:    */   private static void checkSignature(CtMethod wrappedBody, String descriptor)
/*  94:    */     throws CannotCompileException
/*  95:    */   {
/*  96:127 */     if (!descriptor.equals(wrappedBody.getMethodInfo2().getDescriptor())) {
/*  97:128 */       throw new CannotCompileException("wrapped method with a bad signature: " + wrappedBody.getDeclaringClass().getName() + '.' + wrappedBody.getName());
/*  98:    */     }
/*  99:    */   }
/* 100:    */   
/* 101:    */   private static String addBodyMethod(CtClassType clazz, ClassFile classfile, CtMethod src)
/* 102:    */     throws BadBytecode, CannotCompileException
/* 103:    */   {
/* 104:139 */     Hashtable bodies = clazz.getHiddenMethods();
/* 105:140 */     String bodyname = (String)bodies.get(src);
/* 106:141 */     if (bodyname == null)
/* 107:    */     {
/* 108:    */       do
/* 109:    */       {
/* 110:143 */         bodyname = "_added_m$" + clazz.getUniqueNumber();
/* 111:144 */       } while (classfile.getMethod(bodyname) != null);
/* 112:145 */       ClassMap map = new ClassMap();
/* 113:146 */       map.put(src.getDeclaringClass().getName(), clazz.getName());
/* 114:147 */       MethodInfo body = new MethodInfo(classfile.getConstPool(), bodyname, src.getMethodInfo2(), map);
/* 115:    */       
/* 116:    */ 
/* 117:150 */       int acc = body.getAccessFlags();
/* 118:151 */       body.setAccessFlags(AccessFlag.setPrivate(acc));
/* 119:152 */       body.addAttribute(new SyntheticAttribute(classfile.getConstPool()));
/* 120:    */       
/* 121:154 */       classfile.addMethod(body);
/* 122:155 */       bodies.put(src, bodyname);
/* 123:156 */       CtMember.Cache cache = clazz.hasMemberCache();
/* 124:157 */       if (cache != null) {
/* 125:158 */         cache.addMethod(new CtMethod(body, clazz));
/* 126:    */       }
/* 127:    */     }
/* 128:161 */     return bodyname;
/* 129:    */   }
/* 130:    */   
/* 131:    */   static int compileParameterList(Bytecode code, CtClass[] params, int regno)
/* 132:    */   {
/* 133:173 */     return JvstCodeGen.compileParameterList(code, params, regno);
/* 134:    */   }
/* 135:    */   
/* 136:    */   private static void compileReturn(Bytecode code, CtClass type)
/* 137:    */   {
/* 138:180 */     if (type.isPrimitive())
/* 139:    */     {
/* 140:181 */       CtPrimitiveType pt = (CtPrimitiveType)type;
/* 141:182 */       if (pt != CtClass.voidType)
/* 142:    */       {
/* 143:183 */         String wrapper = pt.getWrapperName();
/* 144:184 */         code.addCheckcast(wrapper);
/* 145:185 */         code.addInvokevirtual(wrapper, pt.getGetMethodName(), pt.getGetMethodDescriptor());
/* 146:    */       }
/* 147:189 */       code.addOpcode(pt.getReturnOp());
/* 148:    */     }
/* 149:    */     else
/* 150:    */     {
/* 151:192 */       code.addCheckcast(type);
/* 152:193 */       code.addOpcode(176);
/* 153:    */     }
/* 154:    */   }
/* 155:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.CtNewWrappedMethod
 * JD-Core Version:    0.7.0.1
 */