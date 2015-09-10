/*   1:    */ package javassist;
/*   2:    */ 
/*   3:    */ import javassist.bytecode.BadBytecode;
/*   4:    */ import javassist.bytecode.Bytecode;
/*   5:    */ import javassist.bytecode.ClassFile;
/*   6:    */ import javassist.bytecode.CodeAttribute;
/*   7:    */ import javassist.bytecode.CodeIterator;
/*   8:    */ import javassist.bytecode.CodeIterator.Gap;
/*   9:    */ import javassist.bytecode.ConstPool;
/*  10:    */ import javassist.bytecode.Descriptor;
/*  11:    */ import javassist.bytecode.Descriptor.Iterator;
/*  12:    */ import javassist.bytecode.MethodInfo;
/*  13:    */ import javassist.compiler.CompileError;
/*  14:    */ import javassist.compiler.Javac;
/*  15:    */ 
/*  16:    */ public final class CtConstructor
/*  17:    */   extends CtBehavior
/*  18:    */ {
/*  19:    */   protected CtConstructor(MethodInfo minfo, CtClass declaring)
/*  20:    */   {
/*  21: 37 */     super(declaring, minfo);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public CtConstructor(CtClass[] parameters, CtClass declaring)
/*  25:    */   {
/*  26: 56 */     this((MethodInfo)null, declaring);
/*  27: 57 */     ConstPool cp = declaring.getClassFile2().getConstPool();
/*  28: 58 */     String desc = Descriptor.ofConstructor(parameters);
/*  29: 59 */     this.methodInfo = new MethodInfo(cp, "<init>", desc);
/*  30: 60 */     setModifiers(1);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public CtConstructor(CtConstructor src, CtClass declaring, ClassMap map)
/*  34:    */     throws CannotCompileException
/*  35:    */   {
/*  36:100 */     this((MethodInfo)null, declaring);
/*  37:101 */     copy(src, true, map);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public boolean isConstructor()
/*  41:    */   {
/*  42:108 */     return this.methodInfo.isConstructor();
/*  43:    */   }
/*  44:    */   
/*  45:    */   public boolean isClassInitializer()
/*  46:    */   {
/*  47:115 */     return this.methodInfo.isStaticInitializer();
/*  48:    */   }
/*  49:    */   
/*  50:    */   public String getLongName()
/*  51:    */   {
/*  52:125 */     return getDeclaringClass().getName() + (isConstructor() ? Descriptor.toString(getSignature()) : ".<clinit>()");
/*  53:    */   }
/*  54:    */   
/*  55:    */   public String getName()
/*  56:    */   {
/*  57:137 */     if (this.methodInfo.isStaticInitializer()) {
/*  58:138 */       return "<clinit>";
/*  59:    */     }
/*  60:140 */     return this.declaringClass.getSimpleName();
/*  61:    */   }
/*  62:    */   
/*  63:    */   public boolean isEmpty()
/*  64:    */   {
/*  65:151 */     CodeAttribute ca = getMethodInfo2().getCodeAttribute();
/*  66:152 */     if (ca == null) {
/*  67:153 */       return false;
/*  68:    */     }
/*  69:156 */     ConstPool cp = ca.getConstPool();
/*  70:157 */     CodeIterator it = ca.iterator();
/*  71:    */     try
/*  72:    */     {
/*  73:160 */       int op0 = it.byteAt(it.next());
/*  74:    */       int pos;
/*  75:    */       int desc;
/*  76:161 */       return (op0 == 177) || ((op0 == 42) && (it.byteAt(pos = it.next()) == 183) && ((desc = cp.isConstructor(getSuperclassName(), it.u16bitAt(pos + 1))) != 0) && ("()V".equals(cp.getUtf8Info(desc))) && (it.byteAt(it.next()) == 177) && (!it.hasNext()));
/*  77:    */     }
/*  78:    */     catch (BadBytecode e) {}
/*  79:171 */     return false;
/*  80:    */   }
/*  81:    */   
/*  82:    */   private String getSuperclassName()
/*  83:    */   {
/*  84:175 */     ClassFile cf = this.declaringClass.getClassFile2();
/*  85:176 */     return cf.getSuperclass();
/*  86:    */   }
/*  87:    */   
/*  88:    */   public boolean callsSuper()
/*  89:    */     throws CannotCompileException
/*  90:    */   {
/*  91:185 */     CodeAttribute codeAttr = this.methodInfo.getCodeAttribute();
/*  92:186 */     if (codeAttr != null)
/*  93:    */     {
/*  94:187 */       CodeIterator it = codeAttr.iterator();
/*  95:    */       try
/*  96:    */       {
/*  97:189 */         int index = it.skipSuperConstructor();
/*  98:190 */         return index >= 0;
/*  99:    */       }
/* 100:    */       catch (BadBytecode e)
/* 101:    */       {
/* 102:193 */         throw new CannotCompileException(e);
/* 103:    */       }
/* 104:    */     }
/* 105:197 */     return false;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setBody(String src)
/* 109:    */     throws CannotCompileException
/* 110:    */   {
/* 111:210 */     if (src == null) {
/* 112:211 */       if (isClassInitializer()) {
/* 113:212 */         src = ";";
/* 114:    */       } else {
/* 115:214 */         src = "super();";
/* 116:    */       }
/* 117:    */     }
/* 118:216 */     super.setBody(src);
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setBody(CtConstructor src, ClassMap map)
/* 122:    */     throws CannotCompileException
/* 123:    */   {
/* 124:234 */     setBody0(src.declaringClass, src.methodInfo, this.declaringClass, this.methodInfo, map);
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void insertBeforeBody(String src)
/* 128:    */     throws CannotCompileException
/* 129:    */   {
/* 130:247 */     CtClass cc = this.declaringClass;
/* 131:248 */     cc.checkModify();
/* 132:249 */     if (isClassInitializer()) {
/* 133:250 */       throw new CannotCompileException("class initializer");
/* 134:    */     }
/* 135:252 */     CodeAttribute ca = this.methodInfo.getCodeAttribute();
/* 136:253 */     CodeIterator iterator = ca.iterator();
/* 137:254 */     Bytecode b = new Bytecode(this.methodInfo.getConstPool(), ca.getMaxStack(), ca.getMaxLocals());
/* 138:    */     
/* 139:256 */     b.setStackDepth(ca.getMaxStack());
/* 140:257 */     Javac jv = new Javac(b, cc);
/* 141:    */     try
/* 142:    */     {
/* 143:259 */       jv.recordParams(getParameterTypes(), false);
/* 144:260 */       jv.compileStmnt(src);
/* 145:261 */       ca.setMaxStack(b.getMaxStack());
/* 146:262 */       ca.setMaxLocals(b.getMaxLocals());
/* 147:263 */       iterator.skipConstructor();
/* 148:264 */       int pos = iterator.insertEx(b.get());
/* 149:265 */       iterator.insert(b.getExceptionTable(), pos);
/* 150:266 */       this.methodInfo.rebuildStackMapIf6(cc.getClassPool(), cc.getClassFile2());
/* 151:    */     }
/* 152:    */     catch (NotFoundException e)
/* 153:    */     {
/* 154:269 */       throw new CannotCompileException(e);
/* 155:    */     }
/* 156:    */     catch (CompileError e)
/* 157:    */     {
/* 158:272 */       throw new CannotCompileException(e);
/* 159:    */     }
/* 160:    */     catch (BadBytecode e)
/* 161:    */     {
/* 162:275 */       throw new CannotCompileException(e);
/* 163:    */     }
/* 164:    */   }
/* 165:    */   
/* 166:    */   int getStartPosOfBody(CodeAttribute ca)
/* 167:    */     throws CannotCompileException
/* 168:    */   {
/* 169:283 */     CodeIterator ci = ca.iterator();
/* 170:    */     try
/* 171:    */     {
/* 172:285 */       ci.skipConstructor();
/* 173:286 */       return ci.next();
/* 174:    */     }
/* 175:    */     catch (BadBytecode e)
/* 176:    */     {
/* 177:289 */       throw new CannotCompileException(e);
/* 178:    */     }
/* 179:    */   }
/* 180:    */   
/* 181:    */   public CtMethod toMethod(String name, CtClass declaring)
/* 182:    */     throws CannotCompileException
/* 183:    */   {
/* 184:316 */     return toMethod(name, declaring, null);
/* 185:    */   }
/* 186:    */   
/* 187:    */   public CtMethod toMethod(String name, CtClass declaring, ClassMap map)
/* 188:    */     throws CannotCompileException
/* 189:    */   {
/* 190:350 */     CtMethod method = new CtMethod(null, declaring);
/* 191:351 */     method.copy(this, false, map);
/* 192:352 */     if (isConstructor())
/* 193:    */     {
/* 194:353 */       MethodInfo minfo = method.getMethodInfo2();
/* 195:354 */       CodeAttribute ca = minfo.getCodeAttribute();
/* 196:355 */       if (ca != null)
/* 197:    */       {
/* 198:356 */         removeConsCall(ca);
/* 199:    */         try
/* 200:    */         {
/* 201:358 */           this.methodInfo.rebuildStackMapIf6(declaring.getClassPool(), declaring.getClassFile2());
/* 202:    */         }
/* 203:    */         catch (BadBytecode e)
/* 204:    */         {
/* 205:362 */           throw new CannotCompileException(e);
/* 206:    */         }
/* 207:    */       }
/* 208:    */     }
/* 209:367 */     method.setName(name);
/* 210:368 */     return method;
/* 211:    */   }
/* 212:    */   
/* 213:    */   private static void removeConsCall(CodeAttribute ca)
/* 214:    */     throws CannotCompileException
/* 215:    */   {
/* 216:374 */     CodeIterator iterator = ca.iterator();
/* 217:    */     try
/* 218:    */     {
/* 219:376 */       int pos = iterator.skipConstructor();
/* 220:377 */       if (pos >= 0)
/* 221:    */       {
/* 222:378 */         int mref = iterator.u16bitAt(pos + 1);
/* 223:379 */         String desc = ca.getConstPool().getMethodrefType(mref);
/* 224:380 */         int num = Descriptor.numOfParameters(desc) + 1;
/* 225:381 */         if (num > 3) {
/* 226:382 */           pos = iterator.insertGapAt(pos, num - 3, false).position;
/* 227:    */         }
/* 228:384 */         iterator.writeByte(87, pos++);
/* 229:385 */         iterator.writeByte(0, pos);
/* 230:386 */         iterator.writeByte(0, pos + 1);
/* 231:387 */         Descriptor.Iterator it = new Descriptor.Iterator(desc);
/* 232:    */         for (;;)
/* 233:    */         {
/* 234:389 */           it.next();
/* 235:390 */           if (!it.isParameter()) {
/* 236:    */             break;
/* 237:    */           }
/* 238:391 */           iterator.writeByte(it.is2byte() ? 88 : 87, pos++);
/* 239:    */         }
/* 240:    */       }
/* 241:    */     }
/* 242:    */     catch (BadBytecode e)
/* 243:    */     {
/* 244:399 */       throw new CannotCompileException(e);
/* 245:    */     }
/* 246:    */   }
/* 247:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.CtConstructor
 * JD-Core Version:    0.7.0.1
 */