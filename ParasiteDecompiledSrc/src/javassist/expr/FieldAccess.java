/*   1:    */ package javassist.expr;
/*   2:    */ 
/*   3:    */ import javassist.CannotCompileException;
/*   4:    */ import javassist.ClassPool;
/*   5:    */ import javassist.CtBehavior;
/*   6:    */ import javassist.CtClass;
/*   7:    */ import javassist.CtField;
/*   8:    */ import javassist.CtPrimitiveType;
/*   9:    */ import javassist.NotFoundException;
/*  10:    */ import javassist.bytecode.BadBytecode;
/*  11:    */ import javassist.bytecode.Bytecode;
/*  12:    */ import javassist.bytecode.CodeAttribute;
/*  13:    */ import javassist.bytecode.CodeIterator;
/*  14:    */ import javassist.bytecode.ConstPool;
/*  15:    */ import javassist.bytecode.Descriptor;
/*  16:    */ import javassist.bytecode.MethodInfo;
/*  17:    */ import javassist.compiler.CompileError;
/*  18:    */ import javassist.compiler.Javac;
/*  19:    */ import javassist.compiler.JvstCodeGen;
/*  20:    */ import javassist.compiler.JvstTypeChecker;
/*  21:    */ import javassist.compiler.ProceedHandler;
/*  22:    */ import javassist.compiler.ast.ASTList;
/*  23:    */ 
/*  24:    */ public class FieldAccess
/*  25:    */   extends Expr
/*  26:    */ {
/*  27:    */   int opcode;
/*  28:    */   
/*  29:    */   protected FieldAccess(int pos, CodeIterator i, CtClass declaring, MethodInfo m, int op)
/*  30:    */   {
/*  31: 31 */     super(pos, i, declaring, m);
/*  32: 32 */     this.opcode = op;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public CtBehavior where()
/*  36:    */   {
/*  37: 39 */     return super.where();
/*  38:    */   }
/*  39:    */   
/*  40:    */   public int getLineNumber()
/*  41:    */   {
/*  42: 48 */     return super.getLineNumber();
/*  43:    */   }
/*  44:    */   
/*  45:    */   public String getFileName()
/*  46:    */   {
/*  47: 57 */     return super.getFileName();
/*  48:    */   }
/*  49:    */   
/*  50:    */   public boolean isStatic()
/*  51:    */   {
/*  52: 64 */     return isStatic(this.opcode);
/*  53:    */   }
/*  54:    */   
/*  55:    */   static boolean isStatic(int c)
/*  56:    */   {
/*  57: 68 */     return (c == 178) || (c == 179);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public boolean isReader()
/*  61:    */   {
/*  62: 75 */     return (this.opcode == 180) || (this.opcode == 178);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public boolean isWriter()
/*  66:    */   {
/*  67: 82 */     return (this.opcode == 181) || (this.opcode == 179);
/*  68:    */   }
/*  69:    */   
/*  70:    */   private CtClass getCtClass()
/*  71:    */     throws NotFoundException
/*  72:    */   {
/*  73: 89 */     return this.thisClass.getClassPool().get(getClassName());
/*  74:    */   }
/*  75:    */   
/*  76:    */   public String getClassName()
/*  77:    */   {
/*  78: 96 */     int index = this.iterator.u16bitAt(this.currentPos + 1);
/*  79: 97 */     return getConstPool().getFieldrefClassName(index);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public String getFieldName()
/*  83:    */   {
/*  84:104 */     int index = this.iterator.u16bitAt(this.currentPos + 1);
/*  85:105 */     return getConstPool().getFieldrefName(index);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public CtField getField()
/*  89:    */     throws NotFoundException
/*  90:    */   {
/*  91:112 */     CtClass cc = getCtClass();
/*  92:113 */     return cc.getField(getFieldName());
/*  93:    */   }
/*  94:    */   
/*  95:    */   public CtClass[] mayThrow()
/*  96:    */   {
/*  97:123 */     return super.mayThrow();
/*  98:    */   }
/*  99:    */   
/* 100:    */   public String getSignature()
/* 101:    */   {
/* 102:135 */     int index = this.iterator.u16bitAt(this.currentPos + 1);
/* 103:136 */     return getConstPool().getFieldrefType(index);
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void replace(String statement)
/* 107:    */     throws CannotCompileException
/* 108:    */   {
/* 109:150 */     this.thisClass.getClassFile();
/* 110:151 */     ConstPool constPool = getConstPool();
/* 111:152 */     int pos = this.currentPos;
/* 112:153 */     int index = this.iterator.u16bitAt(pos + 1);
/* 113:    */     
/* 114:155 */     Javac jc = new Javac(this.thisClass);
/* 115:156 */     CodeAttribute ca = this.iterator.get();
/* 116:    */     try
/* 117:    */     {
/* 118:160 */       CtClass fieldType = Descriptor.toCtClass(constPool.getFieldrefType(index), this.thisClass.getClassPool());
/* 119:    */       
/* 120:    */ 
/* 121:163 */       boolean read = isReader();
/* 122:    */       CtClass retType;
/* 123:    */       CtClass[] params;
/* 124:    */       CtClass retType;
/* 125:164 */       if (read)
/* 126:    */       {
/* 127:165 */         CtClass[] params = new CtClass[0];
/* 128:166 */         retType = fieldType;
/* 129:    */       }
/* 130:    */       else
/* 131:    */       {
/* 132:169 */         params = new CtClass[1];
/* 133:170 */         params[0] = fieldType;
/* 134:171 */         retType = CtClass.voidType;
/* 135:    */       }
/* 136:174 */       int paramVar = ca.getMaxLocals();
/* 137:175 */       jc.recordParams(constPool.getFieldrefClassName(index), params, true, paramVar, withinStatic());
/* 138:    */       
/* 139:    */ 
/* 140:    */ 
/* 141:    */ 
/* 142:180 */       boolean included = checkResultValue(retType, statement);
/* 143:181 */       if (read) {
/* 144:182 */         included = true;
/* 145:    */       }
/* 146:184 */       int retVar = jc.recordReturnType(retType, included);
/* 147:185 */       if (read)
/* 148:    */       {
/* 149:186 */         jc.recordProceed(new ProceedForRead(retType, this.opcode, index, paramVar));
/* 150:    */       }
/* 151:    */       else
/* 152:    */       {
/* 153:190 */         jc.recordType(fieldType);
/* 154:191 */         jc.recordProceed(new ProceedForWrite(params[0], this.opcode, index, paramVar));
/* 155:    */       }
/* 156:195 */       Bytecode bytecode = jc.getBytecode();
/* 157:196 */       storeStack(params, isStatic(), paramVar, bytecode);
/* 158:197 */       jc.recordLocalVariables(ca, pos);
/* 159:199 */       if (included) {
/* 160:200 */         if (retType == CtClass.voidType)
/* 161:    */         {
/* 162:201 */           bytecode.addOpcode(1);
/* 163:202 */           bytecode.addAstore(retVar);
/* 164:    */         }
/* 165:    */         else
/* 166:    */         {
/* 167:205 */           bytecode.addConstZero(retType);
/* 168:206 */           bytecode.addStore(retVar, retType);
/* 169:    */         }
/* 170:    */       }
/* 171:209 */       jc.compileStmnt(statement);
/* 172:210 */       if (read) {
/* 173:211 */         bytecode.addLoad(retVar, retType);
/* 174:    */       }
/* 175:213 */       replace0(pos, bytecode, 3);
/* 176:    */     }
/* 177:    */     catch (CompileError e)
/* 178:    */     {
/* 179:215 */       throw new CannotCompileException(e);
/* 180:    */     }
/* 181:    */     catch (NotFoundException e)
/* 182:    */     {
/* 183:216 */       throw new CannotCompileException(e);
/* 184:    */     }
/* 185:    */     catch (BadBytecode e)
/* 186:    */     {
/* 187:218 */       throw new CannotCompileException("broken method");
/* 188:    */     }
/* 189:    */   }
/* 190:    */   
/* 191:    */   static class ProceedForRead
/* 192:    */     implements ProceedHandler
/* 193:    */   {
/* 194:    */     CtClass fieldType;
/* 195:    */     int opcode;
/* 196:    */     int targetVar;
/* 197:    */     int index;
/* 198:    */     
/* 199:    */     ProceedForRead(CtClass type, int op, int i, int var)
/* 200:    */     {
/* 201:230 */       this.fieldType = type;
/* 202:231 */       this.targetVar = var;
/* 203:232 */       this.opcode = op;
/* 204:233 */       this.index = i;
/* 205:    */     }
/* 206:    */     
/* 207:    */     public void doit(JvstCodeGen gen, Bytecode bytecode, ASTList args)
/* 208:    */       throws CompileError
/* 209:    */     {
/* 210:239 */       if ((args != null) && (!gen.isParamListName(args))) {
/* 211:240 */         throw new CompileError("$proceed() cannot take a parameter for field reading");
/* 212:    */       }
/* 213:    */       int stack;
/* 214:    */       int stack;
/* 215:244 */       if (FieldAccess.isStatic(this.opcode))
/* 216:    */       {
/* 217:245 */         stack = 0;
/* 218:    */       }
/* 219:    */       else
/* 220:    */       {
/* 221:247 */         stack = -1;
/* 222:248 */         bytecode.addAload(this.targetVar);
/* 223:    */       }
/* 224:251 */       if ((this.fieldType instanceof CtPrimitiveType)) {
/* 225:252 */         stack += ((CtPrimitiveType)this.fieldType).getDataSize();
/* 226:    */       } else {
/* 227:254 */         stack++;
/* 228:    */       }
/* 229:256 */       bytecode.add(this.opcode);
/* 230:257 */       bytecode.addIndex(this.index);
/* 231:258 */       bytecode.growStack(stack);
/* 232:259 */       gen.setType(this.fieldType);
/* 233:    */     }
/* 234:    */     
/* 235:    */     public void setReturnType(JvstTypeChecker c, ASTList args)
/* 236:    */       throws CompileError
/* 237:    */     {
/* 238:265 */       c.setType(this.fieldType);
/* 239:    */     }
/* 240:    */   }
/* 241:    */   
/* 242:    */   static class ProceedForWrite
/* 243:    */     implements ProceedHandler
/* 244:    */   {
/* 245:    */     CtClass fieldType;
/* 246:    */     int opcode;
/* 247:    */     int targetVar;
/* 248:    */     int index;
/* 249:    */     
/* 250:    */     ProceedForWrite(CtClass type, int op, int i, int var)
/* 251:    */     {
/* 252:278 */       this.fieldType = type;
/* 253:279 */       this.targetVar = var;
/* 254:280 */       this.opcode = op;
/* 255:281 */       this.index = i;
/* 256:    */     }
/* 257:    */     
/* 258:    */     public void doit(JvstCodeGen gen, Bytecode bytecode, ASTList args)
/* 259:    */       throws CompileError
/* 260:    */     {
/* 261:287 */       if (gen.getMethodArgsLength(args) != 1) {
/* 262:288 */         throw new CompileError("$proceed() cannot take more than one parameter for field writing");
/* 263:    */       }
/* 264:    */       int stack;
/* 265:    */       int stack;
/* 266:293 */       if (FieldAccess.isStatic(this.opcode))
/* 267:    */       {
/* 268:294 */         stack = 0;
/* 269:    */       }
/* 270:    */       else
/* 271:    */       {
/* 272:296 */         stack = -1;
/* 273:297 */         bytecode.addAload(this.targetVar);
/* 274:    */       }
/* 275:300 */       gen.atMethodArgs(args, new int[1], new int[1], new String[1]);
/* 276:301 */       gen.doNumCast(this.fieldType);
/* 277:302 */       if ((this.fieldType instanceof CtPrimitiveType)) {
/* 278:303 */         stack -= ((CtPrimitiveType)this.fieldType).getDataSize();
/* 279:    */       } else {
/* 280:305 */         stack--;
/* 281:    */       }
/* 282:307 */       bytecode.add(this.opcode);
/* 283:308 */       bytecode.addIndex(this.index);
/* 284:309 */       bytecode.growStack(stack);
/* 285:310 */       gen.setType(CtClass.voidType);
/* 286:311 */       gen.addNullIfVoid();
/* 287:    */     }
/* 288:    */     
/* 289:    */     public void setReturnType(JvstTypeChecker c, ASTList args)
/* 290:    */       throws CompileError
/* 291:    */     {
/* 292:317 */       c.atMethodArgs(args, new int[1], new int[1], new String[1]);
/* 293:318 */       c.setType(CtClass.voidType);
/* 294:319 */       c.addNullIfVoid();
/* 295:    */     }
/* 296:    */   }
/* 297:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.expr.FieldAccess
 * JD-Core Version:    0.7.0.1
 */