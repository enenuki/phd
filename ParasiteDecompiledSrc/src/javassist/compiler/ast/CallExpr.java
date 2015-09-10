/*  1:   */ package javassist.compiler.ast;
/*  2:   */ 
/*  3:   */ import javassist.compiler.CompileError;
/*  4:   */ import javassist.compiler.MemberResolver.Method;
/*  5:   */ 
/*  6:   */ public class CallExpr
/*  7:   */   extends Expr
/*  8:   */ {
/*  9:   */   private MemberResolver.Method method;
/* 10:   */   
/* 11:   */   private CallExpr(ASTree _head, ASTList _tail)
/* 12:   */   {
/* 13:29 */     super(67, _head, _tail);
/* 14:30 */     this.method = null;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void setMethod(MemberResolver.Method m)
/* 18:   */   {
/* 19:34 */     this.method = m;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public MemberResolver.Method getMethod()
/* 23:   */   {
/* 24:38 */     return this.method;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public static CallExpr makeCall(ASTree target, ASTree args)
/* 28:   */   {
/* 29:42 */     return new CallExpr(target, new ASTList(args));
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void accept(Visitor v)
/* 33:   */     throws CompileError
/* 34:   */   {
/* 35:45 */     v.atCallExpr(this);
/* 36:   */   }
/* 37:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.compiler.ast.CallExpr
 * JD-Core Version:    0.7.0.1
 */