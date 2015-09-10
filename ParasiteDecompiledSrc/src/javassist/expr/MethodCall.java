/*   1:    */ package javassist.expr;
/*   2:    */ 
/*   3:    */ import javassist.CannotCompileException;
/*   4:    */ import javassist.ClassPool;
/*   5:    */ import javassist.CtBehavior;
/*   6:    */ import javassist.CtClass;
/*   7:    */ import javassist.CtMethod;
/*   8:    */ import javassist.NotFoundException;
/*   9:    */ import javassist.bytecode.BadBytecode;
/*  10:    */ import javassist.bytecode.Bytecode;
/*  11:    */ import javassist.bytecode.CodeAttribute;
/*  12:    */ import javassist.bytecode.CodeIterator;
/*  13:    */ import javassist.bytecode.ConstPool;
/*  14:    */ import javassist.bytecode.Descriptor;
/*  15:    */ import javassist.bytecode.MethodInfo;
/*  16:    */ import javassist.compiler.CompileError;
/*  17:    */ import javassist.compiler.Javac;
/*  18:    */ 
/*  19:    */ public class MethodCall
/*  20:    */   extends Expr
/*  21:    */ {
/*  22:    */   protected MethodCall(int pos, CodeIterator i, CtClass declaring, MethodInfo m)
/*  23:    */   {
/*  24: 31 */     super(pos, i, declaring, m);
/*  25:    */   }
/*  26:    */   
/*  27:    */   private int getNameAndType(ConstPool cp)
/*  28:    */   {
/*  29: 35 */     int pos = this.currentPos;
/*  30: 36 */     int c = this.iterator.byteAt(pos);
/*  31: 37 */     int index = this.iterator.u16bitAt(pos + 1);
/*  32: 39 */     if (c == 185) {
/*  33: 40 */       return cp.getInterfaceMethodrefNameAndType(index);
/*  34:    */     }
/*  35: 42 */     return cp.getMethodrefNameAndType(index);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public CtBehavior where()
/*  39:    */   {
/*  40: 49 */     return super.where();
/*  41:    */   }
/*  42:    */   
/*  43:    */   public int getLineNumber()
/*  44:    */   {
/*  45: 58 */     return super.getLineNumber();
/*  46:    */   }
/*  47:    */   
/*  48:    */   public String getFileName()
/*  49:    */   {
/*  50: 67 */     return super.getFileName();
/*  51:    */   }
/*  52:    */   
/*  53:    */   protected CtClass getCtClass()
/*  54:    */     throws NotFoundException
/*  55:    */   {
/*  56: 75 */     return this.thisClass.getClassPool().get(getClassName());
/*  57:    */   }
/*  58:    */   
/*  59:    */   public String getClassName()
/*  60:    */   {
/*  61: 85 */     ConstPool cp = getConstPool();
/*  62: 86 */     int pos = this.currentPos;
/*  63: 87 */     int c = this.iterator.byteAt(pos);
/*  64: 88 */     int index = this.iterator.u16bitAt(pos + 1);
/*  65:    */     String cname;
/*  66:    */     String cname;
/*  67: 90 */     if (c == 185) {
/*  68: 91 */       cname = cp.getInterfaceMethodrefClassName(index);
/*  69:    */     } else {
/*  70: 93 */       cname = cp.getMethodrefClassName(index);
/*  71:    */     }
/*  72: 95 */     if (cname.charAt(0) == '[') {
/*  73: 96 */       cname = Descriptor.toClassName(cname);
/*  74:    */     }
/*  75: 98 */     return cname;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public String getMethodName()
/*  79:    */   {
/*  80:105 */     ConstPool cp = getConstPool();
/*  81:106 */     int nt = getNameAndType(cp);
/*  82:107 */     return cp.getUtf8Info(cp.getNameAndTypeName(nt));
/*  83:    */   }
/*  84:    */   
/*  85:    */   public CtMethod getMethod()
/*  86:    */     throws NotFoundException
/*  87:    */   {
/*  88:114 */     return getCtClass().getMethod(getMethodName(), getSignature());
/*  89:    */   }
/*  90:    */   
/*  91:    */   public String getSignature()
/*  92:    */   {
/*  93:128 */     ConstPool cp = getConstPool();
/*  94:129 */     int nt = getNameAndType(cp);
/*  95:130 */     return cp.getUtf8Info(cp.getNameAndTypeDescriptor(nt));
/*  96:    */   }
/*  97:    */   
/*  98:    */   public CtClass[] mayThrow()
/*  99:    */   {
/* 100:140 */     return super.mayThrow();
/* 101:    */   }
/* 102:    */   
/* 103:    */   public boolean isSuper()
/* 104:    */   {
/* 105:148 */     return (this.iterator.byteAt(this.currentPos) == 183) && (!where().getDeclaringClass().getName().equals(getClassName()));
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void replace(String statement)
/* 109:    */     throws CannotCompileException
/* 110:    */   {
/* 111:179 */     this.thisClass.getClassFile();
/* 112:180 */     ConstPool constPool = getConstPool();
/* 113:181 */     int pos = this.currentPos;
/* 114:182 */     int index = this.iterator.u16bitAt(pos + 1);
/* 115:    */     
/* 116:    */ 
/* 117:    */ 
/* 118:186 */     int c = this.iterator.byteAt(pos);
/* 119:    */     String signature;
/* 120:187 */     if (c == 185)
/* 121:    */     {
/* 122:188 */       int opcodeSize = 5;
/* 123:189 */       String classname = constPool.getInterfaceMethodrefClassName(index);
/* 124:190 */       String methodname = constPool.getInterfaceMethodrefName(index);
/* 125:191 */       signature = constPool.getInterfaceMethodrefType(index);
/* 126:    */     }
/* 127:    */     else
/* 128:    */     {
/* 129:    */       String signature;
/* 130:193 */       if ((c == 184) || (c == 183) || (c == 182))
/* 131:    */       {
/* 132:195 */         int opcodeSize = 3;
/* 133:196 */         String classname = constPool.getMethodrefClassName(index);
/* 134:197 */         String methodname = constPool.getMethodrefName(index);
/* 135:198 */         signature = constPool.getMethodrefType(index);
/* 136:    */       }
/* 137:    */       else
/* 138:    */       {
/* 139:201 */         throw new CannotCompileException("not method invocation");
/* 140:    */       }
/* 141:    */     }
/* 142:    */     int opcodeSize;
/* 143:    */     String signature;
/* 144:    */     String methodname;
/* 145:    */     String classname;
/* 146:203 */     Javac jc = new Javac(this.thisClass);
/* 147:204 */     ClassPool cp = this.thisClass.getClassPool();
/* 148:205 */     CodeAttribute ca = this.iterator.get();
/* 149:    */     try
/* 150:    */     {
/* 151:207 */       CtClass[] params = Descriptor.getParameterTypes(signature, cp);
/* 152:208 */       CtClass retType = Descriptor.getReturnType(signature, cp);
/* 153:209 */       int paramVar = ca.getMaxLocals();
/* 154:210 */       jc.recordParams(classname, params, true, paramVar, withinStatic());
/* 155:    */       
/* 156:212 */       int retVar = jc.recordReturnType(retType, true);
/* 157:213 */       if (c == 184) {
/* 158:214 */         jc.recordStaticProceed(classname, methodname);
/* 159:215 */       } else if (c == 183) {
/* 160:216 */         jc.recordSpecialProceed("$0", classname, methodname, signature);
/* 161:    */       } else {
/* 162:219 */         jc.recordProceed("$0", methodname);
/* 163:    */       }
/* 164:223 */       checkResultValue(retType, statement);
/* 165:    */       
/* 166:225 */       Bytecode bytecode = jc.getBytecode();
/* 167:226 */       storeStack(params, c == 184, paramVar, bytecode);
/* 168:227 */       jc.recordLocalVariables(ca, pos);
/* 169:229 */       if (retType != CtClass.voidType)
/* 170:    */       {
/* 171:230 */         bytecode.addConstZero(retType);
/* 172:231 */         bytecode.addStore(retVar, retType);
/* 173:    */       }
/* 174:234 */       jc.compileStmnt(statement);
/* 175:235 */       if (retType != CtClass.voidType) {
/* 176:236 */         bytecode.addLoad(retVar, retType);
/* 177:    */       }
/* 178:238 */       replace0(pos, bytecode, opcodeSize);
/* 179:    */     }
/* 180:    */     catch (CompileError e)
/* 181:    */     {
/* 182:240 */       throw new CannotCompileException(e);
/* 183:    */     }
/* 184:    */     catch (NotFoundException e)
/* 185:    */     {
/* 186:241 */       throw new CannotCompileException(e);
/* 187:    */     }
/* 188:    */     catch (BadBytecode e)
/* 189:    */     {
/* 190:243 */       throw new CannotCompileException("broken method");
/* 191:    */     }
/* 192:    */   }
/* 193:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.expr.MethodCall
 * JD-Core Version:    0.7.0.1
 */