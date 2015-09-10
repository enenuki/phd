/*   1:    */ package javassist;
/*   2:    */ 
/*   3:    */ import javassist.bytecode.BadBytecode;
/*   4:    */ import javassist.bytecode.Bytecode;
/*   5:    */ import javassist.bytecode.ClassFile;
/*   6:    */ import javassist.bytecode.CodeAttribute;
/*   7:    */ import javassist.bytecode.CodeIterator;
/*   8:    */ import javassist.bytecode.ConstPool;
/*   9:    */ import javassist.bytecode.Descriptor;
/*  10:    */ import javassist.bytecode.MethodInfo;
/*  11:    */ 
/*  12:    */ public final class CtMethod
/*  13:    */   extends CtBehavior
/*  14:    */ {
/*  15:    */   protected String cachedStringRep;
/*  16:    */   
/*  17:    */   CtMethod(MethodInfo minfo, CtClass declaring)
/*  18:    */   {
/*  19: 37 */     super(declaring, minfo);
/*  20: 38 */     this.cachedStringRep = null;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public CtMethod(CtClass returnType, String mname, CtClass[] parameters, CtClass declaring)
/*  24:    */   {
/*  25: 54 */     this(null, declaring);
/*  26: 55 */     ConstPool cp = declaring.getClassFile2().getConstPool();
/*  27: 56 */     String desc = Descriptor.ofMethod(returnType, parameters);
/*  28: 57 */     this.methodInfo = new MethodInfo(cp, mname, desc);
/*  29: 58 */     setModifiers(1025);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public CtMethod(CtMethod src, CtClass declaring, ClassMap map)
/*  33:    */     throws CannotCompileException
/*  34:    */   {
/*  35:112 */     this(null, declaring);
/*  36:113 */     copy(src, false, map);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public static CtMethod make(String src, CtClass declaring)
/*  40:    */     throws CannotCompileException
/*  41:    */   {
/*  42:129 */     return CtNewMethod.make(src, declaring);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public static CtMethod make(MethodInfo minfo, CtClass declaring)
/*  46:    */     throws CannotCompileException
/*  47:    */   {
/*  48:144 */     if (declaring.getClassFile2().getConstPool() != minfo.getConstPool()) {
/*  49:145 */       throw new CannotCompileException("bad declaring class");
/*  50:    */     }
/*  51:147 */     return new CtMethod(minfo, declaring);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public int hashCode()
/*  55:    */   {
/*  56:156 */     return getStringRep().hashCode();
/*  57:    */   }
/*  58:    */   
/*  59:    */   void nameReplaced()
/*  60:    */   {
/*  61:164 */     this.cachedStringRep = null;
/*  62:    */   }
/*  63:    */   
/*  64:    */   final String getStringRep()
/*  65:    */   {
/*  66:170 */     if (this.cachedStringRep == null) {
/*  67:171 */       this.cachedStringRep = (this.methodInfo.getName() + Descriptor.getParamDescriptor(this.methodInfo.getDescriptor()));
/*  68:    */     }
/*  69:174 */     return this.cachedStringRep;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public boolean equals(Object obj)
/*  73:    */   {
/*  74:182 */     return (obj != null) && ((obj instanceof CtMethod)) && (((CtMethod)obj).getStringRep().equals(getStringRep()));
/*  75:    */   }
/*  76:    */   
/*  77:    */   public String getLongName()
/*  78:    */   {
/*  79:193 */     return getDeclaringClass().getName() + "." + getName() + Descriptor.toString(getSignature());
/*  80:    */   }
/*  81:    */   
/*  82:    */   public String getName()
/*  83:    */   {
/*  84:201 */     return this.methodInfo.getName();
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void setName(String newname)
/*  88:    */   {
/*  89:208 */     this.declaringClass.checkModify();
/*  90:209 */     this.methodInfo.setName(newname);
/*  91:    */   }
/*  92:    */   
/*  93:    */   public CtClass getReturnType()
/*  94:    */     throws NotFoundException
/*  95:    */   {
/*  96:216 */     return getReturnType0();
/*  97:    */   }
/*  98:    */   
/*  99:    */   public boolean isEmpty()
/* 100:    */   {
/* 101:224 */     CodeAttribute ca = getMethodInfo2().getCodeAttribute();
/* 102:225 */     if (ca == null) {
/* 103:226 */       return (getModifiers() & 0x400) != 0;
/* 104:    */     }
/* 105:228 */     CodeIterator it = ca.iterator();
/* 106:    */     try
/* 107:    */     {
/* 108:230 */       return (it.hasNext()) && (it.byteAt(it.next()) == 177) && (!it.hasNext());
/* 109:    */     }
/* 110:    */     catch (BadBytecode e) {}
/* 111:234 */     return false;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void setBody(CtMethod src, ClassMap map)
/* 115:    */     throws CannotCompileException
/* 116:    */   {
/* 117:254 */     setBody0(src.declaringClass, src.methodInfo, this.declaringClass, this.methodInfo, map);
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setWrappedBody(CtMethod mbody, ConstParameter constParam)
/* 121:    */     throws CannotCompileException
/* 122:    */   {
/* 123:272 */     this.declaringClass.checkModify();
/* 124:    */     
/* 125:274 */     CtClass clazz = getDeclaringClass();
/* 126:    */     CtClass[] params;
/* 127:    */     CtClass retType;
/* 128:    */     try
/* 129:    */     {
/* 130:278 */       params = getParameterTypes();
/* 131:279 */       retType = getReturnType();
/* 132:    */     }
/* 133:    */     catch (NotFoundException e)
/* 134:    */     {
/* 135:282 */       throw new CannotCompileException(e);
/* 136:    */     }
/* 137:285 */     Bytecode code = CtNewWrappedMethod.makeBody(clazz, clazz.getClassFile2(), mbody, params, retType, constParam);
/* 138:    */     
/* 139:    */ 
/* 140:    */ 
/* 141:    */ 
/* 142:290 */     CodeAttribute cattr = code.toCodeAttribute();
/* 143:291 */     this.methodInfo.setCodeAttribute(cattr);
/* 144:292 */     this.methodInfo.setAccessFlags(this.methodInfo.getAccessFlags() & 0xFFFFFBFF);
/* 145:    */   }
/* 146:    */   
/* 147:    */   public static class ConstParameter
/* 148:    */   {
/* 149:    */     public static ConstParameter integer(int i)
/* 150:    */     {
/* 151:315 */       return new CtMethod.IntConstParameter(i);
/* 152:    */     }
/* 153:    */     
/* 154:    */     public static ConstParameter integer(long i)
/* 155:    */     {
/* 156:324 */       return new CtMethod.LongConstParameter(i);
/* 157:    */     }
/* 158:    */     
/* 159:    */     public static ConstParameter string(String s)
/* 160:    */     {
/* 161:333 */       return new CtMethod.StringConstParameter(s);
/* 162:    */     }
/* 163:    */     
/* 164:    */     int compile(Bytecode code)
/* 165:    */       throws CannotCompileException
/* 166:    */     {
/* 167:342 */       return 0;
/* 168:    */     }
/* 169:    */     
/* 170:    */     String descriptor()
/* 171:    */     {
/* 172:346 */       return defaultDescriptor();
/* 173:    */     }
/* 174:    */     
/* 175:    */     static String defaultDescriptor()
/* 176:    */     {
/* 177:353 */       return "([Ljava/lang/Object;)Ljava/lang/Object;";
/* 178:    */     }
/* 179:    */     
/* 180:    */     String constDescriptor()
/* 181:    */     {
/* 182:362 */       return defaultConstDescriptor();
/* 183:    */     }
/* 184:    */     
/* 185:    */     static String defaultConstDescriptor()
/* 186:    */     {
/* 187:369 */       return "([Ljava/lang/Object;)V";
/* 188:    */     }
/* 189:    */   }
/* 190:    */   
/* 191:    */   static class IntConstParameter
/* 192:    */     extends CtMethod.ConstParameter
/* 193:    */   {
/* 194:    */     int param;
/* 195:    */     
/* 196:    */     IntConstParameter(int i)
/* 197:    */     {
/* 198:377 */       this.param = i;
/* 199:    */     }
/* 200:    */     
/* 201:    */     int compile(Bytecode code)
/* 202:    */       throws CannotCompileException
/* 203:    */     {
/* 204:381 */       code.addIconst(this.param);
/* 205:382 */       return 1;
/* 206:    */     }
/* 207:    */     
/* 208:    */     String descriptor()
/* 209:    */     {
/* 210:386 */       return "([Ljava/lang/Object;I)Ljava/lang/Object;";
/* 211:    */     }
/* 212:    */     
/* 213:    */     String constDescriptor()
/* 214:    */     {
/* 215:390 */       return "([Ljava/lang/Object;I)V";
/* 216:    */     }
/* 217:    */   }
/* 218:    */   
/* 219:    */   static class LongConstParameter
/* 220:    */     extends CtMethod.ConstParameter
/* 221:    */   {
/* 222:    */     long param;
/* 223:    */     
/* 224:    */     LongConstParameter(long l)
/* 225:    */     {
/* 226:398 */       this.param = l;
/* 227:    */     }
/* 228:    */     
/* 229:    */     int compile(Bytecode code)
/* 230:    */       throws CannotCompileException
/* 231:    */     {
/* 232:402 */       code.addLconst(this.param);
/* 233:403 */       return 2;
/* 234:    */     }
/* 235:    */     
/* 236:    */     String descriptor()
/* 237:    */     {
/* 238:407 */       return "([Ljava/lang/Object;J)Ljava/lang/Object;";
/* 239:    */     }
/* 240:    */     
/* 241:    */     String constDescriptor()
/* 242:    */     {
/* 243:411 */       return "([Ljava/lang/Object;J)V";
/* 244:    */     }
/* 245:    */   }
/* 246:    */   
/* 247:    */   static class StringConstParameter
/* 248:    */     extends CtMethod.ConstParameter
/* 249:    */   {
/* 250:    */     String param;
/* 251:    */     
/* 252:    */     StringConstParameter(String s)
/* 253:    */     {
/* 254:419 */       this.param = s;
/* 255:    */     }
/* 256:    */     
/* 257:    */     int compile(Bytecode code)
/* 258:    */       throws CannotCompileException
/* 259:    */     {
/* 260:423 */       code.addLdc(this.param);
/* 261:424 */       return 1;
/* 262:    */     }
/* 263:    */     
/* 264:    */     String descriptor()
/* 265:    */     {
/* 266:428 */       return "([Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;";
/* 267:    */     }
/* 268:    */     
/* 269:    */     String constDescriptor()
/* 270:    */     {
/* 271:432 */       return "([Ljava/lang/Object;Ljava/lang/String;)V";
/* 272:    */     }
/* 273:    */   }
/* 274:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.CtMethod
 * JD-Core Version:    0.7.0.1
 */