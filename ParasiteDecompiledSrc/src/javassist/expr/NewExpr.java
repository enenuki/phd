/*   1:    */ package javassist.expr;
/*   2:    */ 
/*   3:    */ import javassist.CannotCompileException;
/*   4:    */ import javassist.ClassPool;
/*   5:    */ import javassist.CtBehavior;
/*   6:    */ import javassist.CtClass;
/*   7:    */ import javassist.CtConstructor;
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
/*  18:    */ import javassist.compiler.JvstCodeGen;
/*  19:    */ import javassist.compiler.JvstTypeChecker;
/*  20:    */ import javassist.compiler.ProceedHandler;
/*  21:    */ import javassist.compiler.ast.ASTList;
/*  22:    */ 
/*  23:    */ public class NewExpr
/*  24:    */   extends Expr
/*  25:    */ {
/*  26:    */   String newTypeName;
/*  27:    */   int newPos;
/*  28:    */   
/*  29:    */   protected NewExpr(int pos, CodeIterator i, CtClass declaring, MethodInfo m, String type, int np)
/*  30:    */   {
/*  31: 35 */     super(pos, i, declaring, m);
/*  32: 36 */     this.newTypeName = type;
/*  33: 37 */     this.newPos = np;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public CtBehavior where()
/*  37:    */   {
/*  38: 58 */     return super.where();
/*  39:    */   }
/*  40:    */   
/*  41:    */   public int getLineNumber()
/*  42:    */   {
/*  43: 67 */     return super.getLineNumber();
/*  44:    */   }
/*  45:    */   
/*  46:    */   public String getFileName()
/*  47:    */   {
/*  48: 76 */     return super.getFileName();
/*  49:    */   }
/*  50:    */   
/*  51:    */   private CtClass getCtClass()
/*  52:    */     throws NotFoundException
/*  53:    */   {
/*  54: 83 */     return this.thisClass.getClassPool().get(this.newTypeName);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public String getClassName()
/*  58:    */   {
/*  59: 90 */     return this.newTypeName;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public String getSignature()
/*  63:    */   {
/*  64:104 */     ConstPool constPool = getConstPool();
/*  65:105 */     int methodIndex = this.iterator.u16bitAt(this.currentPos + 1);
/*  66:106 */     return constPool.getMethodrefType(methodIndex);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public CtConstructor getConstructor()
/*  70:    */     throws NotFoundException
/*  71:    */   {
/*  72:113 */     ConstPool cp = getConstPool();
/*  73:114 */     int index = this.iterator.u16bitAt(this.currentPos + 1);
/*  74:115 */     String desc = cp.getMethodrefType(index);
/*  75:116 */     return getCtClass().getConstructor(desc);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public CtClass[] mayThrow()
/*  79:    */   {
/*  80:126 */     return super.mayThrow();
/*  81:    */   }
/*  82:    */   
/*  83:    */   private int canReplace()
/*  84:    */     throws CannotCompileException
/*  85:    */   {
/*  86:141 */     int op = this.iterator.byteAt(this.newPos + 3);
/*  87:142 */     if (op == 89) {
/*  88:143 */       return 4;
/*  89:    */     }
/*  90:144 */     if ((op == 90) && (this.iterator.byteAt(this.newPos + 4) == 95)) {
/*  91:146 */       return 5;
/*  92:    */     }
/*  93:148 */     return 3;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void replace(String statement)
/*  97:    */     throws CannotCompileException
/*  98:    */   {
/*  99:162 */     this.thisClass.getClassFile();
/* 100:    */     
/* 101:164 */     int bytecodeSize = 3;
/* 102:165 */     int pos = this.newPos;
/* 103:    */     
/* 104:167 */     int newIndex = this.iterator.u16bitAt(pos + 1);
/* 105:    */     
/* 106:    */ 
/* 107:    */ 
/* 108:171 */     int codeSize = canReplace();
/* 109:172 */     int end = pos + codeSize;
/* 110:173 */     for (int i = pos; i < end; i++) {
/* 111:174 */       this.iterator.writeByte(0, i);
/* 112:    */     }
/* 113:176 */     ConstPool constPool = getConstPool();
/* 114:177 */     pos = this.currentPos;
/* 115:178 */     int methodIndex = this.iterator.u16bitAt(pos + 1);
/* 116:    */     
/* 117:180 */     String signature = constPool.getMethodrefType(methodIndex);
/* 118:    */     
/* 119:182 */     Javac jc = new Javac(this.thisClass);
/* 120:183 */     ClassPool cp = this.thisClass.getClassPool();
/* 121:184 */     CodeAttribute ca = this.iterator.get();
/* 122:    */     try
/* 123:    */     {
/* 124:186 */       CtClass[] params = Descriptor.getParameterTypes(signature, cp);
/* 125:187 */       CtClass newType = cp.get(this.newTypeName);
/* 126:188 */       int paramVar = ca.getMaxLocals();
/* 127:189 */       jc.recordParams(this.newTypeName, params, true, paramVar, withinStatic());
/* 128:    */       
/* 129:191 */       int retVar = jc.recordReturnType(newType, true);
/* 130:192 */       jc.recordProceed(new ProceedForNew(newType, newIndex, methodIndex));
/* 131:    */       
/* 132:    */ 
/* 133:    */ 
/* 134:    */ 
/* 135:197 */       checkResultValue(newType, statement);
/* 136:    */       
/* 137:199 */       Bytecode bytecode = jc.getBytecode();
/* 138:200 */       storeStack(params, true, paramVar, bytecode);
/* 139:201 */       jc.recordLocalVariables(ca, pos);
/* 140:    */       
/* 141:203 */       bytecode.addConstZero(newType);
/* 142:204 */       bytecode.addStore(retVar, newType);
/* 143:    */       
/* 144:206 */       jc.compileStmnt(statement);
/* 145:207 */       if (codeSize > 3) {
/* 146:208 */         bytecode.addAload(retVar);
/* 147:    */       }
/* 148:210 */       replace0(pos, bytecode, 3);
/* 149:    */     }
/* 150:    */     catch (CompileError e)
/* 151:    */     {
/* 152:212 */       throw new CannotCompileException(e);
/* 153:    */     }
/* 154:    */     catch (NotFoundException e)
/* 155:    */     {
/* 156:213 */       throw new CannotCompileException(e);
/* 157:    */     }
/* 158:    */     catch (BadBytecode e)
/* 159:    */     {
/* 160:215 */       throw new CannotCompileException("broken method");
/* 161:    */     }
/* 162:    */   }
/* 163:    */   
/* 164:    */   static class ProceedForNew
/* 165:    */     implements ProceedHandler
/* 166:    */   {
/* 167:    */     CtClass newType;
/* 168:    */     int newIndex;
/* 169:    */     int methodIndex;
/* 170:    */     
/* 171:    */     ProceedForNew(CtClass nt, int ni, int mi)
/* 172:    */     {
/* 173:224 */       this.newType = nt;
/* 174:225 */       this.newIndex = ni;
/* 175:226 */       this.methodIndex = mi;
/* 176:    */     }
/* 177:    */     
/* 178:    */     public void doit(JvstCodeGen gen, Bytecode bytecode, ASTList args)
/* 179:    */       throws CompileError
/* 180:    */     {
/* 181:232 */       bytecode.addOpcode(187);
/* 182:233 */       bytecode.addIndex(this.newIndex);
/* 183:234 */       bytecode.addOpcode(89);
/* 184:235 */       gen.atMethodCallCore(this.newType, "<init>", args, false, true, -1, null);
/* 185:    */       
/* 186:237 */       gen.setType(this.newType);
/* 187:    */     }
/* 188:    */     
/* 189:    */     public void setReturnType(JvstTypeChecker c, ASTList args)
/* 190:    */       throws CompileError
/* 191:    */     {
/* 192:243 */       c.atMethodCallCore(this.newType, "<init>", args);
/* 193:244 */       c.setType(this.newType);
/* 194:    */     }
/* 195:    */   }
/* 196:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.expr.NewExpr
 * JD-Core Version:    0.7.0.1
 */