/*   1:    */ package javassist.expr;
/*   2:    */ 
/*   3:    */ import javassist.CannotCompileException;
/*   4:    */ import javassist.CtBehavior;
/*   5:    */ import javassist.CtClass;
/*   6:    */ import javassist.CtPrimitiveType;
/*   7:    */ import javassist.NotFoundException;
/*   8:    */ import javassist.bytecode.BadBytecode;
/*   9:    */ import javassist.bytecode.Bytecode;
/*  10:    */ import javassist.bytecode.CodeAttribute;
/*  11:    */ import javassist.bytecode.CodeIterator;
/*  12:    */ import javassist.bytecode.ConstPool;
/*  13:    */ import javassist.bytecode.Descriptor;
/*  14:    */ import javassist.bytecode.MethodInfo;
/*  15:    */ import javassist.compiler.CompileError;
/*  16:    */ import javassist.compiler.Javac;
/*  17:    */ import javassist.compiler.JvstCodeGen;
/*  18:    */ import javassist.compiler.JvstTypeChecker;
/*  19:    */ import javassist.compiler.ProceedHandler;
/*  20:    */ import javassist.compiler.ast.ASTList;
/*  21:    */ 
/*  22:    */ public class NewArray
/*  23:    */   extends Expr
/*  24:    */ {
/*  25:    */   int opcode;
/*  26:    */   
/*  27:    */   protected NewArray(int pos, CodeIterator i, CtClass declaring, MethodInfo m, int op)
/*  28:    */   {
/*  29: 34 */     super(pos, i, declaring, m);
/*  30: 35 */     this.opcode = op;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public CtBehavior where()
/*  34:    */   {
/*  35: 42 */     return super.where();
/*  36:    */   }
/*  37:    */   
/*  38:    */   public int getLineNumber()
/*  39:    */   {
/*  40: 51 */     return super.getLineNumber();
/*  41:    */   }
/*  42:    */   
/*  43:    */   public String getFileName()
/*  44:    */   {
/*  45: 60 */     return super.getFileName();
/*  46:    */   }
/*  47:    */   
/*  48:    */   public CtClass[] mayThrow()
/*  49:    */   {
/*  50: 70 */     return super.mayThrow();
/*  51:    */   }
/*  52:    */   
/*  53:    */   public CtClass getComponentType()
/*  54:    */     throws NotFoundException
/*  55:    */   {
/*  56: 80 */     if (this.opcode == 188)
/*  57:    */     {
/*  58: 81 */       int atype = this.iterator.byteAt(this.currentPos + 1);
/*  59: 82 */       return getPrimitiveType(atype);
/*  60:    */     }
/*  61: 84 */     if ((this.opcode == 189) || (this.opcode == 197))
/*  62:    */     {
/*  63: 86 */       int index = this.iterator.u16bitAt(this.currentPos + 1);
/*  64: 87 */       String desc = getConstPool().getClassInfo(index);
/*  65: 88 */       int dim = Descriptor.arrayDimension(desc);
/*  66: 89 */       desc = Descriptor.toArrayComponent(desc, dim);
/*  67: 90 */       return Descriptor.toCtClass(desc, this.thisClass.getClassPool());
/*  68:    */     }
/*  69: 93 */     throw new RuntimeException("bad opcode: " + this.opcode);
/*  70:    */   }
/*  71:    */   
/*  72:    */   CtClass getPrimitiveType(int atype)
/*  73:    */   {
/*  74: 97 */     switch (atype)
/*  75:    */     {
/*  76:    */     case 4: 
/*  77: 99 */       return CtClass.booleanType;
/*  78:    */     case 5: 
/*  79:101 */       return CtClass.charType;
/*  80:    */     case 6: 
/*  81:103 */       return CtClass.floatType;
/*  82:    */     case 7: 
/*  83:105 */       return CtClass.doubleType;
/*  84:    */     case 8: 
/*  85:107 */       return CtClass.byteType;
/*  86:    */     case 9: 
/*  87:109 */       return CtClass.shortType;
/*  88:    */     case 10: 
/*  89:111 */       return CtClass.intType;
/*  90:    */     case 11: 
/*  91:113 */       return CtClass.longType;
/*  92:    */     }
/*  93:115 */     throw new RuntimeException("bad atype: " + atype);
/*  94:    */   }
/*  95:    */   
/*  96:    */   public int getDimension()
/*  97:    */   {
/*  98:123 */     if (this.opcode == 188) {
/*  99:124 */       return 1;
/* 100:    */     }
/* 101:125 */     if ((this.opcode == 189) || (this.opcode == 197))
/* 102:    */     {
/* 103:127 */       int index = this.iterator.u16bitAt(this.currentPos + 1);
/* 104:128 */       String desc = getConstPool().getClassInfo(index);
/* 105:129 */       return Descriptor.arrayDimension(desc) + (this.opcode == 189 ? 1 : 0);
/* 106:    */     }
/* 107:133 */     throw new RuntimeException("bad opcode: " + this.opcode);
/* 108:    */   }
/* 109:    */   
/* 110:    */   public int getCreatedDimensions()
/* 111:    */   {
/* 112:142 */     if (this.opcode == 197) {
/* 113:143 */       return this.iterator.byteAt(this.currentPos + 3);
/* 114:    */     }
/* 115:145 */     return 1;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void replace(String statement)
/* 119:    */     throws CannotCompileException
/* 120:    */   {
/* 121:    */     try
/* 122:    */     {
/* 123:160 */       replace2(statement);
/* 124:    */     }
/* 125:    */     catch (CompileError e)
/* 126:    */     {
/* 127:162 */       throw new CannotCompileException(e);
/* 128:    */     }
/* 129:    */     catch (NotFoundException e)
/* 130:    */     {
/* 131:163 */       throw new CannotCompileException(e);
/* 132:    */     }
/* 133:    */     catch (BadBytecode e)
/* 134:    */     {
/* 135:165 */       throw new CannotCompileException("broken method");
/* 136:    */     }
/* 137:    */   }
/* 138:    */   
/* 139:    */   private void replace2(String statement)
/* 140:    */     throws CompileError, NotFoundException, BadBytecode, CannotCompileException
/* 141:    */   {
/* 142:173 */     this.thisClass.getClassFile();
/* 143:174 */     ConstPool constPool = getConstPool();
/* 144:175 */     int pos = this.currentPos;
/* 145:    */     
/* 146:    */ 
/* 147:178 */     int index = 0;
/* 148:179 */     int dim = 1;
/* 149:    */     int codeLength;
/* 150:181 */     if (this.opcode == 188)
/* 151:    */     {
/* 152:182 */       index = this.iterator.byteAt(this.currentPos + 1);
/* 153:183 */       CtPrimitiveType cpt = (CtPrimitiveType)getPrimitiveType(index);
/* 154:184 */       String desc = "[" + cpt.getDescriptor();
/* 155:185 */       codeLength = 2;
/* 156:    */     }
/* 157:    */     else
/* 158:    */     {
/* 159:    */       int codeLength;
/* 160:187 */       if (this.opcode == 189)
/* 161:    */       {
/* 162:188 */         index = this.iterator.u16bitAt(pos + 1);
/* 163:189 */         String desc = constPool.getClassInfo(index);
/* 164:190 */         if (desc.startsWith("[")) {
/* 165:191 */           desc = "[" + desc;
/* 166:    */         } else {
/* 167:193 */           desc = "[L" + desc + ";";
/* 168:    */         }
/* 169:195 */         codeLength = 3;
/* 170:    */       }
/* 171:    */       else
/* 172:    */       {
/* 173:    */         int codeLength;
/* 174:197 */         if (this.opcode == 197)
/* 175:    */         {
/* 176:198 */           index = this.iterator.u16bitAt(this.currentPos + 1);
/* 177:199 */           String desc = constPool.getClassInfo(index);
/* 178:200 */           dim = this.iterator.byteAt(this.currentPos + 3);
/* 179:201 */           codeLength = 4;
/* 180:    */         }
/* 181:    */         else
/* 182:    */         {
/* 183:204 */           throw new RuntimeException("bad opcode: " + this.opcode);
/* 184:    */         }
/* 185:    */       }
/* 186:    */     }
/* 187:    */     String desc;
/* 188:    */     int codeLength;
/* 189:206 */     CtClass retType = Descriptor.toCtClass(desc, this.thisClass.getClassPool());
/* 190:    */     
/* 191:208 */     Javac jc = new Javac(this.thisClass);
/* 192:209 */     CodeAttribute ca = this.iterator.get();
/* 193:    */     
/* 194:211 */     CtClass[] params = new CtClass[dim];
/* 195:212 */     for (int i = 0; i < dim; i++) {
/* 196:213 */       params[i] = CtClass.intType;
/* 197:    */     }
/* 198:215 */     int paramVar = ca.getMaxLocals();
/* 199:216 */     jc.recordParams("java.lang.Object", params, true, paramVar, withinStatic());
/* 200:    */     
/* 201:    */ 
/* 202:    */ 
/* 203:    */ 
/* 204:221 */     checkResultValue(retType, statement);
/* 205:222 */     int retVar = jc.recordReturnType(retType, true);
/* 206:223 */     jc.recordProceed(new ProceedForArray(retType, this.opcode, index, dim));
/* 207:    */     
/* 208:225 */     Bytecode bytecode = jc.getBytecode();
/* 209:226 */     storeStack(params, true, paramVar, bytecode);
/* 210:227 */     jc.recordLocalVariables(ca, pos);
/* 211:    */     
/* 212:229 */     bytecode.addOpcode(1);
/* 213:230 */     bytecode.addAstore(retVar);
/* 214:    */     
/* 215:232 */     jc.compileStmnt(statement);
/* 216:233 */     bytecode.addAload(retVar);
/* 217:    */     
/* 218:235 */     replace0(pos, bytecode, codeLength);
/* 219:    */   }
/* 220:    */   
/* 221:    */   static class ProceedForArray
/* 222:    */     implements ProceedHandler
/* 223:    */   {
/* 224:    */     CtClass arrayType;
/* 225:    */     int opcode;
/* 226:    */     int index;
/* 227:    */     int dimension;
/* 228:    */     
/* 229:    */     ProceedForArray(CtClass type, int op, int i, int dim)
/* 230:    */     {
/* 231:246 */       this.arrayType = type;
/* 232:247 */       this.opcode = op;
/* 233:248 */       this.index = i;
/* 234:249 */       this.dimension = dim;
/* 235:    */     }
/* 236:    */     
/* 237:    */     public void doit(JvstCodeGen gen, Bytecode bytecode, ASTList args)
/* 238:    */       throws CompileError
/* 239:    */     {
/* 240:255 */       int num = gen.getMethodArgsLength(args);
/* 241:256 */       if (num != this.dimension) {
/* 242:257 */         throw new CompileError("$proceed() with a wrong number of parameters");
/* 243:    */       }
/* 244:260 */       gen.atMethodArgs(args, new int[num], new int[num], new String[num]);
/* 245:    */       
/* 246:262 */       bytecode.addOpcode(this.opcode);
/* 247:263 */       if (this.opcode == 189)
/* 248:    */       {
/* 249:264 */         bytecode.addIndex(this.index);
/* 250:    */       }
/* 251:265 */       else if (this.opcode == 188)
/* 252:    */       {
/* 253:266 */         bytecode.add(this.index);
/* 254:    */       }
/* 255:    */       else
/* 256:    */       {
/* 257:268 */         bytecode.addIndex(this.index);
/* 258:269 */         bytecode.add(this.dimension);
/* 259:270 */         bytecode.growStack(1 - this.dimension);
/* 260:    */       }
/* 261:273 */       gen.setType(this.arrayType);
/* 262:    */     }
/* 263:    */     
/* 264:    */     public void setReturnType(JvstTypeChecker c, ASTList args)
/* 265:    */       throws CompileError
/* 266:    */     {
/* 267:279 */       c.setType(this.arrayType);
/* 268:    */     }
/* 269:    */   }
/* 270:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.expr.NewArray
 * JD-Core Version:    0.7.0.1
 */