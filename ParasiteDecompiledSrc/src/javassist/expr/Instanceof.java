/*   1:    */ package javassist.expr;
/*   2:    */ 
/*   3:    */ import javassist.CannotCompileException;
/*   4:    */ import javassist.ClassPool;
/*   5:    */ import javassist.CtBehavior;
/*   6:    */ import javassist.CtClass;
/*   7:    */ import javassist.NotFoundException;
/*   8:    */ import javassist.bytecode.BadBytecode;
/*   9:    */ import javassist.bytecode.Bytecode;
/*  10:    */ import javassist.bytecode.CodeAttribute;
/*  11:    */ import javassist.bytecode.CodeIterator;
/*  12:    */ import javassist.bytecode.ConstPool;
/*  13:    */ import javassist.bytecode.MethodInfo;
/*  14:    */ import javassist.compiler.CompileError;
/*  15:    */ import javassist.compiler.Javac;
/*  16:    */ import javassist.compiler.JvstCodeGen;
/*  17:    */ import javassist.compiler.JvstTypeChecker;
/*  18:    */ import javassist.compiler.ProceedHandler;
/*  19:    */ import javassist.compiler.ast.ASTList;
/*  20:    */ 
/*  21:    */ public class Instanceof
/*  22:    */   extends Expr
/*  23:    */ {
/*  24:    */   protected Instanceof(int pos, CodeIterator i, CtClass declaring, MethodInfo m)
/*  25:    */   {
/*  26: 32 */     super(pos, i, declaring, m);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public CtBehavior where()
/*  30:    */   {
/*  31: 39 */     return super.where();
/*  32:    */   }
/*  33:    */   
/*  34:    */   public int getLineNumber()
/*  35:    */   {
/*  36: 48 */     return super.getLineNumber();
/*  37:    */   }
/*  38:    */   
/*  39:    */   public String getFileName()
/*  40:    */   {
/*  41: 58 */     return super.getFileName();
/*  42:    */   }
/*  43:    */   
/*  44:    */   public CtClass getType()
/*  45:    */     throws NotFoundException
/*  46:    */   {
/*  47: 67 */     ConstPool cp = getConstPool();
/*  48: 68 */     int pos = this.currentPos;
/*  49: 69 */     int index = this.iterator.u16bitAt(pos + 1);
/*  50: 70 */     String name = cp.getClassInfo(index);
/*  51: 71 */     return this.thisClass.getClassPool().getCtClass(name);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public CtClass[] mayThrow()
/*  55:    */   {
/*  56: 81 */     return super.mayThrow();
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void replace(String statement)
/*  60:    */     throws CannotCompileException
/*  61:    */   {
/*  62: 93 */     this.thisClass.getClassFile();
/*  63: 94 */     ConstPool constPool = getConstPool();
/*  64: 95 */     int pos = this.currentPos;
/*  65: 96 */     int index = this.iterator.u16bitAt(pos + 1);
/*  66:    */     
/*  67: 98 */     Javac jc = new Javac(this.thisClass);
/*  68: 99 */     ClassPool cp = this.thisClass.getClassPool();
/*  69:100 */     CodeAttribute ca = this.iterator.get();
/*  70:    */     try
/*  71:    */     {
/*  72:103 */       CtClass[] params = { cp.get("java.lang.Object") };
/*  73:    */       
/*  74:105 */       CtClass retType = CtClass.booleanType;
/*  75:    */       
/*  76:107 */       int paramVar = ca.getMaxLocals();
/*  77:108 */       jc.recordParams("java.lang.Object", params, true, paramVar, withinStatic());
/*  78:    */       
/*  79:110 */       int retVar = jc.recordReturnType(retType, true);
/*  80:111 */       jc.recordProceed(new ProceedForInstanceof(index));
/*  81:    */       
/*  82:    */ 
/*  83:114 */       jc.recordType(getType());
/*  84:    */       
/*  85:    */ 
/*  86:    */ 
/*  87:118 */       checkResultValue(retType, statement);
/*  88:    */       
/*  89:120 */       Bytecode bytecode = jc.getBytecode();
/*  90:121 */       storeStack(params, true, paramVar, bytecode);
/*  91:122 */       jc.recordLocalVariables(ca, pos);
/*  92:    */       
/*  93:124 */       bytecode.addConstZero(retType);
/*  94:125 */       bytecode.addStore(retVar, retType);
/*  95:    */       
/*  96:127 */       jc.compileStmnt(statement);
/*  97:128 */       bytecode.addLoad(retVar, retType);
/*  98:    */       
/*  99:130 */       replace0(pos, bytecode, 3);
/* 100:    */     }
/* 101:    */     catch (CompileError e)
/* 102:    */     {
/* 103:132 */       throw new CannotCompileException(e);
/* 104:    */     }
/* 105:    */     catch (NotFoundException e)
/* 106:    */     {
/* 107:133 */       throw new CannotCompileException(e);
/* 108:    */     }
/* 109:    */     catch (BadBytecode e)
/* 110:    */     {
/* 111:135 */       throw new CannotCompileException("broken method");
/* 112:    */     }
/* 113:    */   }
/* 114:    */   
/* 115:    */   static class ProceedForInstanceof
/* 116:    */     implements ProceedHandler
/* 117:    */   {
/* 118:    */     int index;
/* 119:    */     
/* 120:    */     ProceedForInstanceof(int i)
/* 121:    */     {
/* 122:145 */       this.index = i;
/* 123:    */     }
/* 124:    */     
/* 125:    */     public void doit(JvstCodeGen gen, Bytecode bytecode, ASTList args)
/* 126:    */       throws CompileError
/* 127:    */     {
/* 128:151 */       if (gen.getMethodArgsLength(args) != 1) {
/* 129:152 */         throw new CompileError("$proceed() cannot take more than one parameter for instanceof");
/* 130:    */       }
/* 131:156 */       gen.atMethodArgs(args, new int[1], new int[1], new String[1]);
/* 132:157 */       bytecode.addOpcode(193);
/* 133:158 */       bytecode.addIndex(this.index);
/* 134:159 */       gen.setType(CtClass.booleanType);
/* 135:    */     }
/* 136:    */     
/* 137:    */     public void setReturnType(JvstTypeChecker c, ASTList args)
/* 138:    */       throws CompileError
/* 139:    */     {
/* 140:165 */       c.atMethodArgs(args, new int[1], new int[1], new String[1]);
/* 141:166 */       c.setType(CtClass.booleanType);
/* 142:    */     }
/* 143:    */   }
/* 144:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.expr.Instanceof
 * JD-Core Version:    0.7.0.1
 */