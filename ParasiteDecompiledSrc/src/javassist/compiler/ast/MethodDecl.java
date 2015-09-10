/*  1:   */ package javassist.compiler.ast;
/*  2:   */ 
/*  3:   */ import javassist.compiler.CompileError;
/*  4:   */ 
/*  5:   */ public class MethodDecl
/*  6:   */   extends ASTList
/*  7:   */ {
/*  8:   */   public static final String initName = "<init>";
/*  9:   */   
/* 10:   */   public MethodDecl(ASTree _head, ASTList _tail)
/* 11:   */   {
/* 12:24 */     super(_head, _tail);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public boolean isConstructor()
/* 16:   */   {
/* 17:28 */     Symbol sym = getReturn().getVariable();
/* 18:29 */     return (sym != null) && ("<init>".equals(sym.get()));
/* 19:   */   }
/* 20:   */   
/* 21:   */   public ASTList getModifiers()
/* 22:   */   {
/* 23:32 */     return (ASTList)getLeft();
/* 24:   */   }
/* 25:   */   
/* 26:   */   public Declarator getReturn()
/* 27:   */   {
/* 28:34 */     return (Declarator)tail().head();
/* 29:   */   }
/* 30:   */   
/* 31:   */   public ASTList getParams()
/* 32:   */   {
/* 33:36 */     return (ASTList)sublist(2).head();
/* 34:   */   }
/* 35:   */   
/* 36:   */   public ASTList getThrows()
/* 37:   */   {
/* 38:38 */     return (ASTList)sublist(3).head();
/* 39:   */   }
/* 40:   */   
/* 41:   */   public Stmnt getBody()
/* 42:   */   {
/* 43:40 */     return (Stmnt)sublist(4).head();
/* 44:   */   }
/* 45:   */   
/* 46:   */   public void accept(Visitor v)
/* 47:   */     throws CompileError
/* 48:   */   {
/* 49:43 */     v.atMethodDecl(this);
/* 50:   */   }
/* 51:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.compiler.ast.MethodDecl
 * JD-Core Version:    0.7.0.1
 */