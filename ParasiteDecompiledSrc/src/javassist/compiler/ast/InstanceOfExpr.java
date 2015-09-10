/*  1:   */ package javassist.compiler.ast;
/*  2:   */ 
/*  3:   */ import javassist.compiler.CompileError;
/*  4:   */ 
/*  5:   */ public class InstanceOfExpr
/*  6:   */   extends CastExpr
/*  7:   */ {
/*  8:   */   public InstanceOfExpr(ASTList className, int dim, ASTree expr)
/*  9:   */   {
/* 10:25 */     super(className, dim, expr);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public InstanceOfExpr(int type, int dim, ASTree expr)
/* 14:   */   {
/* 15:29 */     super(type, dim, expr);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public String getTag()
/* 19:   */   {
/* 20:33 */     return "instanceof:" + this.castType + ":" + this.arrayDim;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void accept(Visitor v)
/* 24:   */     throws CompileError
/* 25:   */   {
/* 26:37 */     v.atInstanceOfExpr(this);
/* 27:   */   }
/* 28:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.compiler.ast.InstanceOfExpr
 * JD-Core Version:    0.7.0.1
 */