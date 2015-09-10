/*   1:    */ package javassist;
/*   2:    */ 
/*   3:    */ import javassist.bytecode.Bytecode;
/*   4:    */ import javassist.bytecode.ClassFile;
/*   5:    */ import javassist.bytecode.ConstPool;
/*   6:    */ import javassist.bytecode.ExceptionsAttribute;
/*   7:    */ import javassist.bytecode.FieldInfo;
/*   8:    */ import javassist.bytecode.MethodInfo;
/*   9:    */ import javassist.compiler.CompileError;
/*  10:    */ import javassist.compiler.Javac;
/*  11:    */ 
/*  12:    */ public class CtNewMethod
/*  13:    */ {
/*  14:    */   public static CtMethod make(String src, CtClass declaring)
/*  15:    */     throws CannotCompileException
/*  16:    */   {
/*  17: 44 */     return make(src, declaring, null, null);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public static CtMethod make(String src, CtClass declaring, String delegateObj, String delegateMethod)
/*  21:    */     throws CannotCompileException
/*  22:    */   {
/*  23: 68 */     Javac compiler = new Javac(declaring);
/*  24:    */     try
/*  25:    */     {
/*  26: 70 */       if (delegateMethod != null) {
/*  27: 71 */         compiler.recordProceed(delegateObj, delegateMethod);
/*  28:    */       }
/*  29: 73 */       CtMember obj = compiler.compile(src);
/*  30: 74 */       if ((obj instanceof CtMethod)) {
/*  31: 75 */         return (CtMethod)obj;
/*  32:    */       }
/*  33:    */     }
/*  34:    */     catch (CompileError e)
/*  35:    */     {
/*  36: 78 */       throw new CannotCompileException(e);
/*  37:    */     }
/*  38: 81 */     throw new CannotCompileException("not a method");
/*  39:    */   }
/*  40:    */   
/*  41:    */   public static CtMethod make(CtClass returnType, String mname, CtClass[] parameters, CtClass[] exceptions, String body, CtClass declaring)
/*  42:    */     throws CannotCompileException
/*  43:    */   {
/*  44:105 */     return make(1, returnType, mname, parameters, exceptions, body, declaring);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public static CtMethod make(int modifiers, CtClass returnType, String mname, CtClass[] parameters, CtClass[] exceptions, String body, CtClass declaring)
/*  48:    */     throws CannotCompileException
/*  49:    */   {
/*  50:    */     try
/*  51:    */     {
/*  52:133 */       CtMethod cm = new CtMethod(returnType, mname, parameters, declaring);
/*  53:    */       
/*  54:135 */       cm.setModifiers(modifiers);
/*  55:136 */       cm.setExceptionTypes(exceptions);
/*  56:137 */       cm.setBody(body);
/*  57:138 */       return cm;
/*  58:    */     }
/*  59:    */     catch (NotFoundException e)
/*  60:    */     {
/*  61:141 */       throw new CannotCompileException(e);
/*  62:    */     }
/*  63:    */   }
/*  64:    */   
/*  65:    */   public static CtMethod copy(CtMethod src, CtClass declaring, ClassMap map)
/*  66:    */     throws CannotCompileException
/*  67:    */   {
/*  68:162 */     return new CtMethod(src, declaring, map);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public static CtMethod copy(CtMethod src, String name, CtClass declaring, ClassMap map)
/*  72:    */     throws CannotCompileException
/*  73:    */   {
/*  74:184 */     CtMethod cm = new CtMethod(src, declaring, map);
/*  75:185 */     cm.setName(name);
/*  76:186 */     return cm;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public static CtMethod abstractMethod(CtClass returnType, String mname, CtClass[] parameters, CtClass[] exceptions, CtClass declaring)
/*  80:    */     throws NotFoundException
/*  81:    */   {
/*  82:207 */     CtMethod cm = new CtMethod(returnType, mname, parameters, declaring);
/*  83:208 */     cm.setExceptionTypes(exceptions);
/*  84:209 */     return cm;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public static CtMethod getter(String methodName, CtField field)
/*  88:    */     throws CannotCompileException
/*  89:    */   {
/*  90:224 */     FieldInfo finfo = field.getFieldInfo2();
/*  91:225 */     String fieldType = finfo.getDescriptor();
/*  92:226 */     String desc = "()" + fieldType;
/*  93:227 */     ConstPool cp = finfo.getConstPool();
/*  94:228 */     MethodInfo minfo = new MethodInfo(cp, methodName, desc);
/*  95:229 */     minfo.setAccessFlags(1);
/*  96:    */     
/*  97:231 */     Bytecode code = new Bytecode(cp, 2, 1);
/*  98:    */     try
/*  99:    */     {
/* 100:233 */       String fieldName = finfo.getName();
/* 101:234 */       if ((finfo.getAccessFlags() & 0x8) == 0)
/* 102:    */       {
/* 103:235 */         code.addAload(0);
/* 104:236 */         code.addGetfield(Bytecode.THIS, fieldName, fieldType);
/* 105:    */       }
/* 106:    */       else
/* 107:    */       {
/* 108:239 */         code.addGetstatic(Bytecode.THIS, fieldName, fieldType);
/* 109:    */       }
/* 110:241 */       code.addReturn(field.getType());
/* 111:    */     }
/* 112:    */     catch (NotFoundException e)
/* 113:    */     {
/* 114:244 */       throw new CannotCompileException(e);
/* 115:    */     }
/* 116:247 */     minfo.setCodeAttribute(code.toCodeAttribute());
/* 117:248 */     return new CtMethod(minfo, field.getDeclaringClass());
/* 118:    */   }
/* 119:    */   
/* 120:    */   public static CtMethod setter(String methodName, CtField field)
/* 121:    */     throws CannotCompileException
/* 122:    */   {
/* 123:265 */     FieldInfo finfo = field.getFieldInfo2();
/* 124:266 */     String fieldType = finfo.getDescriptor();
/* 125:267 */     String desc = "(" + fieldType + ")V";
/* 126:268 */     ConstPool cp = finfo.getConstPool();
/* 127:269 */     MethodInfo minfo = new MethodInfo(cp, methodName, desc);
/* 128:270 */     minfo.setAccessFlags(1);
/* 129:    */     
/* 130:272 */     Bytecode code = new Bytecode(cp, 3, 3);
/* 131:    */     try
/* 132:    */     {
/* 133:274 */       String fieldName = finfo.getName();
/* 134:275 */       if ((finfo.getAccessFlags() & 0x8) == 0)
/* 135:    */       {
/* 136:276 */         code.addAload(0);
/* 137:277 */         code.addLoad(1, field.getType());
/* 138:278 */         code.addPutfield(Bytecode.THIS, fieldName, fieldType);
/* 139:    */       }
/* 140:    */       else
/* 141:    */       {
/* 142:281 */         code.addLoad(1, field.getType());
/* 143:282 */         code.addPutstatic(Bytecode.THIS, fieldName, fieldType);
/* 144:    */       }
/* 145:285 */       code.addReturn(null);
/* 146:    */     }
/* 147:    */     catch (NotFoundException e)
/* 148:    */     {
/* 149:288 */       throw new CannotCompileException(e);
/* 150:    */     }
/* 151:291 */     minfo.setCodeAttribute(code.toCodeAttribute());
/* 152:292 */     return new CtMethod(minfo, field.getDeclaringClass());
/* 153:    */   }
/* 154:    */   
/* 155:    */   public static CtMethod delegator(CtMethod delegate, CtClass declaring)
/* 156:    */     throws CannotCompileException
/* 157:    */   {
/* 158:    */     try
/* 159:    */     {
/* 160:320 */       return delegator0(delegate, declaring);
/* 161:    */     }
/* 162:    */     catch (NotFoundException e)
/* 163:    */     {
/* 164:323 */       throw new CannotCompileException(e);
/* 165:    */     }
/* 166:    */   }
/* 167:    */   
/* 168:    */   private static CtMethod delegator0(CtMethod delegate, CtClass declaring)
/* 169:    */     throws CannotCompileException, NotFoundException
/* 170:    */   {
/* 171:330 */     MethodInfo deleInfo = delegate.getMethodInfo2();
/* 172:331 */     String methodName = deleInfo.getName();
/* 173:332 */     String desc = deleInfo.getDescriptor();
/* 174:333 */     ConstPool cp = declaring.getClassFile2().getConstPool();
/* 175:334 */     MethodInfo minfo = new MethodInfo(cp, methodName, desc);
/* 176:335 */     minfo.setAccessFlags(deleInfo.getAccessFlags());
/* 177:    */     
/* 178:337 */     ExceptionsAttribute eattr = deleInfo.getExceptionsAttribute();
/* 179:338 */     if (eattr != null) {
/* 180:339 */       minfo.setExceptionsAttribute((ExceptionsAttribute)eattr.copy(cp, null));
/* 181:    */     }
/* 182:342 */     Bytecode code = new Bytecode(cp, 0, 0);
/* 183:343 */     boolean isStatic = Modifier.isStatic(delegate.getModifiers());
/* 184:344 */     CtClass deleClass = delegate.getDeclaringClass();
/* 185:345 */     CtClass[] params = delegate.getParameterTypes();
/* 186:    */     int s;
/* 187:347 */     if (isStatic)
/* 188:    */     {
/* 189:348 */       int s = code.addLoadParameters(params, 0);
/* 190:349 */       code.addInvokestatic(deleClass, methodName, desc);
/* 191:    */     }
/* 192:    */     else
/* 193:    */     {
/* 194:352 */       code.addLoad(0, deleClass);
/* 195:353 */       s = code.addLoadParameters(params, 1);
/* 196:354 */       code.addInvokespecial(deleClass, methodName, desc);
/* 197:    */     }
/* 198:357 */     code.addReturn(delegate.getReturnType());
/* 199:358 */     code.setMaxLocals(++s);
/* 200:359 */     code.setMaxStack(s < 2 ? 2 : s);
/* 201:360 */     minfo.setCodeAttribute(code.toCodeAttribute());
/* 202:361 */     return new CtMethod(minfo, declaring);
/* 203:    */   }
/* 204:    */   
/* 205:    */   public static CtMethod wrapped(CtClass returnType, String mname, CtClass[] parameterTypes, CtClass[] exceptionTypes, CtMethod body, CtMethod.ConstParameter constParam, CtClass declaring)
/* 206:    */     throws CannotCompileException
/* 207:    */   {
/* 208:467 */     return CtNewWrappedMethod.wrapped(returnType, mname, parameterTypes, exceptionTypes, body, constParam, declaring);
/* 209:    */   }
/* 210:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.CtNewMethod
 * JD-Core Version:    0.7.0.1
 */