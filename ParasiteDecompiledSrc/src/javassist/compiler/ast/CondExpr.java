/*  1:   */ package javassist.compiler.ast;
/*  2:   */ 
/*  3:   */ import javassist.compiler.CompileError;
/*  4:   */ 
/*  5:   */ public class CondExpr
/*  6:   */   extends ASTList
/*  7:   */ {
/*  8:   */   public CondExpr(ASTree cond, ASTree thenp, ASTree elsep)
/*  9:   */   {
/* 10:25 */     super(cond, new ASTList(thenp, new ASTList(elsep)));
/* 11:   */   }
/* 12:   */   
/* 13:   */   public ASTree condExpr()
/* 14:   */   {
/* 15:28 */     return head();
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void setCond(ASTree t)
/* 19:   */   {
/* 20:30 */     setHead(t);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public ASTree thenExpr()
/* 24:   */   {
/* 25:32 */     return tail().head();
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void setThen(ASTree t)
/* 29:   */   {
/* 30:34 */     tail().setHead(t);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public ASTree elseExpr()
/* 34:   */   {
/* 35:36 */     return tail().tail().head();
/* 36:   */   }
/* 37:   */   
/* 38:   */   public void setElse(ASTree t)
/* 39:   */   {
/* 40:38 */     tail().tail().setHead(t);
/* 41:   */   }
/* 42:   */   
/* 43:   */   public String getTag()
/* 44:   */   {
/* 45:40 */     return "?:";
/* 46:   */   }
/* 47:   */   
/* 48:   */   public void accept(Visitor v)
/* 49:   */     throws CompileError
/* 50:   */   {
/* 51:42 */     v.atCondExpr(this);
/* 52:   */   }
/* 53:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.compiler.ast.CondExpr
 * JD-Core Version:    0.7.0.1
 */