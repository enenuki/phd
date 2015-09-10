/*  1:   */ package javassist.compiler.ast;
/*  2:   */ 
/*  3:   */ import javassist.compiler.CompileError;
/*  4:   */ import javassist.compiler.TokenId;
/*  5:   */ 
/*  6:   */ public class CastExpr
/*  7:   */   extends ASTList
/*  8:   */   implements TokenId
/*  9:   */ {
/* 10:   */   protected int castType;
/* 11:   */   protected int arrayDim;
/* 12:   */   
/* 13:   */   public CastExpr(ASTList className, int dim, ASTree expr)
/* 14:   */   {
/* 15:29 */     super(className, new ASTList(expr));
/* 16:30 */     this.castType = 307;
/* 17:31 */     this.arrayDim = dim;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public CastExpr(int type, int dim, ASTree expr)
/* 21:   */   {
/* 22:35 */     super(null, new ASTList(expr));
/* 23:36 */     this.castType = type;
/* 24:37 */     this.arrayDim = dim;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public int getType()
/* 28:   */   {
/* 29:42 */     return this.castType;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public int getArrayDim()
/* 33:   */   {
/* 34:44 */     return this.arrayDim;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public ASTList getClassName()
/* 38:   */   {
/* 39:46 */     return (ASTList)getLeft();
/* 40:   */   }
/* 41:   */   
/* 42:   */   public ASTree getOprand()
/* 43:   */   {
/* 44:48 */     return getRight().getLeft();
/* 45:   */   }
/* 46:   */   
/* 47:   */   public void setOprand(ASTree t)
/* 48:   */   {
/* 49:50 */     getRight().setLeft(t);
/* 50:   */   }
/* 51:   */   
/* 52:   */   public String getTag()
/* 53:   */   {
/* 54:52 */     return "cast:" + this.castType + ":" + this.arrayDim;
/* 55:   */   }
/* 56:   */   
/* 57:   */   public void accept(Visitor v)
/* 58:   */     throws CompileError
/* 59:   */   {
/* 60:54 */     v.atCastExpr(this);
/* 61:   */   }
/* 62:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.compiler.ast.CastExpr
 * JD-Core Version:    0.7.0.1
 */