/*  1:   */ package javassist.compiler.ast;
/*  2:   */ 
/*  3:   */ import javassist.compiler.CompileError;
/*  4:   */ 
/*  5:   */ public class AssignExpr
/*  6:   */   extends Expr
/*  7:   */ {
/*  8:   */   private AssignExpr(int op, ASTree _head, ASTList _tail)
/*  9:   */   {
/* 10:29 */     super(op, _head, _tail);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public static AssignExpr makeAssign(int op, ASTree oprand1, ASTree oprand2)
/* 14:   */   {
/* 15:34 */     return new AssignExpr(op, oprand1, new ASTList(oprand2));
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void accept(Visitor v)
/* 19:   */     throws CompileError
/* 20:   */   {
/* 21:38 */     v.atAssignExpr(this);
/* 22:   */   }
/* 23:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.compiler.ast.AssignExpr
 * JD-Core Version:    0.7.0.1
 */