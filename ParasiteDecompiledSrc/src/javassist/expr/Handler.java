/*   1:    */ package javassist.expr;
/*   2:    */ 
/*   3:    */ import javassist.CannotCompileException;
/*   4:    */ import javassist.ClassPool;
/*   5:    */ import javassist.CtBehavior;
/*   6:    */ import javassist.CtClass;
/*   7:    */ import javassist.NotFoundException;
/*   8:    */ import javassist.bytecode.Bytecode;
/*   9:    */ import javassist.bytecode.CodeAttribute;
/*  10:    */ import javassist.bytecode.CodeIterator;
/*  11:    */ import javassist.bytecode.ConstPool;
/*  12:    */ import javassist.bytecode.ExceptionTable;
/*  13:    */ import javassist.bytecode.MethodInfo;
/*  14:    */ import javassist.compiler.CompileError;
/*  15:    */ import javassist.compiler.Javac;
/*  16:    */ 
/*  17:    */ public class Handler
/*  18:    */   extends Expr
/*  19:    */ {
/*  20: 26 */   private static String EXCEPTION_NAME = "$1";
/*  21:    */   private ExceptionTable etable;
/*  22:    */   private int index;
/*  23:    */   
/*  24:    */   protected Handler(ExceptionTable et, int nth, CodeIterator it, CtClass declaring, MethodInfo m)
/*  25:    */   {
/*  26: 35 */     super(et.handlerPc(nth), it, declaring, m);
/*  27: 36 */     this.etable = et;
/*  28: 37 */     this.index = nth;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public CtBehavior where()
/*  32:    */   {
/*  33: 43 */     return super.where();
/*  34:    */   }
/*  35:    */   
/*  36:    */   public int getLineNumber()
/*  37:    */   {
/*  38: 51 */     return super.getLineNumber();
/*  39:    */   }
/*  40:    */   
/*  41:    */   public String getFileName()
/*  42:    */   {
/*  43: 60 */     return super.getFileName();
/*  44:    */   }
/*  45:    */   
/*  46:    */   public CtClass[] mayThrow()
/*  47:    */   {
/*  48: 67 */     return super.mayThrow();
/*  49:    */   }
/*  50:    */   
/*  51:    */   public CtClass getType()
/*  52:    */     throws NotFoundException
/*  53:    */   {
/*  54: 75 */     int type = this.etable.catchType(this.index);
/*  55: 76 */     if (type == 0) {
/*  56: 77 */       return null;
/*  57:    */     }
/*  58: 79 */     ConstPool cp = getConstPool();
/*  59: 80 */     String name = cp.getClassInfo(type);
/*  60: 81 */     return this.thisClass.getClassPool().getCtClass(name);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public boolean isFinally()
/*  64:    */   {
/*  65: 89 */     return this.etable.catchType(this.index) == 0;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void replace(String statement)
/*  69:    */     throws CannotCompileException
/*  70:    */   {
/*  71: 98 */     throw new RuntimeException("not implemented yet");
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void insertBefore(String src)
/*  75:    */     throws CannotCompileException
/*  76:    */   {
/*  77:109 */     this.edited = true;
/*  78:    */     
/*  79:111 */     ConstPool cp = getConstPool();
/*  80:112 */     CodeAttribute ca = this.iterator.get();
/*  81:113 */     Javac jv = new Javac(this.thisClass);
/*  82:114 */     Bytecode b = jv.getBytecode();
/*  83:115 */     b.setStackDepth(1);
/*  84:116 */     b.setMaxLocals(ca.getMaxLocals());
/*  85:    */     try
/*  86:    */     {
/*  87:119 */       CtClass type = getType();
/*  88:120 */       int var = jv.recordVariable(type, EXCEPTION_NAME);
/*  89:121 */       jv.recordReturnType(type, false);
/*  90:122 */       b.addAstore(var);
/*  91:123 */       jv.compileStmnt(src);
/*  92:124 */       b.addAload(var);
/*  93:    */       
/*  94:126 */       int oldHandler = this.etable.handlerPc(this.index);
/*  95:127 */       b.addOpcode(167);
/*  96:128 */       b.addIndex(oldHandler - this.iterator.getCodeLength() - b.currentPc() + 1);
/*  97:    */       
/*  98:    */ 
/*  99:131 */       this.maxStack = b.getMaxStack();
/* 100:132 */       this.maxLocals = b.getMaxLocals();
/* 101:    */       
/* 102:134 */       int pos = this.iterator.append(b.get());
/* 103:135 */       this.iterator.append(b.getExceptionTable(), pos);
/* 104:136 */       this.etable.setHandlerPc(this.index, pos);
/* 105:    */     }
/* 106:    */     catch (NotFoundException e)
/* 107:    */     {
/* 108:139 */       throw new CannotCompileException(e);
/* 109:    */     }
/* 110:    */     catch (CompileError e)
/* 111:    */     {
/* 112:142 */       throw new CannotCompileException(e);
/* 113:    */     }
/* 114:    */   }
/* 115:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.expr.Handler
 * JD-Core Version:    0.7.0.1
 */