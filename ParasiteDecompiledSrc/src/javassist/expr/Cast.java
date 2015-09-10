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
/*  21:    */ public class Cast
/*  22:    */   extends Expr
/*  23:    */ {
/*  24:    */   protected Cast(int pos, CodeIterator i, CtClass declaring, MethodInfo m)
/*  25:    */   {
/*  26: 31 */     super(pos, i, declaring, m);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public CtBehavior where()
/*  30:    */   {
/*  31: 38 */     return super.where();
/*  32:    */   }
/*  33:    */   
/*  34:    */   public int getLineNumber()
/*  35:    */   {
/*  36: 47 */     return super.getLineNumber();
/*  37:    */   }
/*  38:    */   
/*  39:    */   public String getFileName()
/*  40:    */   {
/*  41: 56 */     return super.getFileName();
/*  42:    */   }
/*  43:    */   
/*  44:    */   public CtClass getType()
/*  45:    */     throws NotFoundException
/*  46:    */   {
/*  47: 64 */     ConstPool cp = getConstPool();
/*  48: 65 */     int pos = this.currentPos;
/*  49: 66 */     int index = this.iterator.u16bitAt(pos + 1);
/*  50: 67 */     String name = cp.getClassInfo(index);
/*  51: 68 */     return this.thisClass.getClassPool().getCtClass(name);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public CtClass[] mayThrow()
/*  55:    */   {
/*  56: 78 */     return super.mayThrow();
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void replace(String statement)
/*  60:    */     throws CannotCompileException
/*  61:    */   {
/*  62: 90 */     this.thisClass.getClassFile();
/*  63: 91 */     ConstPool constPool = getConstPool();
/*  64: 92 */     int pos = this.currentPos;
/*  65: 93 */     int index = this.iterator.u16bitAt(pos + 1);
/*  66:    */     
/*  67: 95 */     Javac jc = new Javac(this.thisClass);
/*  68: 96 */     ClassPool cp = this.thisClass.getClassPool();
/*  69: 97 */     CodeAttribute ca = this.iterator.get();
/*  70:    */     try
/*  71:    */     {
/*  72:100 */       CtClass[] params = { cp.get("java.lang.Object") };
/*  73:    */       
/*  74:102 */       CtClass retType = getType();
/*  75:    */       
/*  76:104 */       int paramVar = ca.getMaxLocals();
/*  77:105 */       jc.recordParams("java.lang.Object", params, true, paramVar, withinStatic());
/*  78:    */       
/*  79:107 */       int retVar = jc.recordReturnType(retType, true);
/*  80:108 */       jc.recordProceed(new ProceedForCast(index, retType));
/*  81:    */       
/*  82:    */ 
/*  83:    */ 
/*  84:112 */       checkResultValue(retType, statement);
/*  85:    */       
/*  86:114 */       Bytecode bytecode = jc.getBytecode();
/*  87:115 */       storeStack(params, true, paramVar, bytecode);
/*  88:116 */       jc.recordLocalVariables(ca, pos);
/*  89:    */       
/*  90:118 */       bytecode.addConstZero(retType);
/*  91:119 */       bytecode.addStore(retVar, retType);
/*  92:    */       
/*  93:121 */       jc.compileStmnt(statement);
/*  94:122 */       bytecode.addLoad(retVar, retType);
/*  95:    */       
/*  96:124 */       replace0(pos, bytecode, 3);
/*  97:    */     }
/*  98:    */     catch (CompileError e)
/*  99:    */     {
/* 100:126 */       throw new CannotCompileException(e);
/* 101:    */     }
/* 102:    */     catch (NotFoundException e)
/* 103:    */     {
/* 104:127 */       throw new CannotCompileException(e);
/* 105:    */     }
/* 106:    */     catch (BadBytecode e)
/* 107:    */     {
/* 108:129 */       throw new CannotCompileException("broken method");
/* 109:    */     }
/* 110:    */   }
/* 111:    */   
/* 112:    */   static class ProceedForCast
/* 113:    */     implements ProceedHandler
/* 114:    */   {
/* 115:    */     int index;
/* 116:    */     CtClass retType;
/* 117:    */     
/* 118:    */     ProceedForCast(int i, CtClass t)
/* 119:    */     {
/* 120:140 */       this.index = i;
/* 121:141 */       this.retType = t;
/* 122:    */     }
/* 123:    */     
/* 124:    */     public void doit(JvstCodeGen gen, Bytecode bytecode, ASTList args)
/* 125:    */       throws CompileError
/* 126:    */     {
/* 127:147 */       if (gen.getMethodArgsLength(args) != 1) {
/* 128:148 */         throw new CompileError("$proceed() cannot take more than one parameter for cast");
/* 129:    */       }
/* 130:152 */       gen.atMethodArgs(args, new int[1], new int[1], new String[1]);
/* 131:153 */       bytecode.addOpcode(192);
/* 132:154 */       bytecode.addIndex(this.index);
/* 133:155 */       gen.setType(this.retType);
/* 134:    */     }
/* 135:    */     
/* 136:    */     public void setReturnType(JvstTypeChecker c, ASTList args)
/* 137:    */       throws CompileError
/* 138:    */     {
/* 139:161 */       c.atMethodArgs(args, new int[1], new int[1], new String[1]);
/* 140:162 */       c.setType(this.retType);
/* 141:    */     }
/* 142:    */   }
/* 143:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.expr.Cast
 * JD-Core Version:    0.7.0.1
 */