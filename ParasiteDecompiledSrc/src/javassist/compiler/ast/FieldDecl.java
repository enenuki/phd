/*  1:   */ package javassist.compiler.ast;
/*  2:   */ 
/*  3:   */ import javassist.compiler.CompileError;
/*  4:   */ 
/*  5:   */ public class FieldDecl
/*  6:   */   extends ASTList
/*  7:   */ {
/*  8:   */   public FieldDecl(ASTree _head, ASTList _tail)
/*  9:   */   {
/* 10:22 */     super(_head, _tail);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public ASTList getModifiers()
/* 14:   */   {
/* 15:25 */     return (ASTList)getLeft();
/* 16:   */   }
/* 17:   */   
/* 18:   */   public Declarator getDeclarator()
/* 19:   */   {
/* 20:27 */     return (Declarator)tail().head();
/* 21:   */   }
/* 22:   */   
/* 23:   */   public ASTree getInit()
/* 24:   */   {
/* 25:29 */     return sublist(2).head();
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void accept(Visitor v)
/* 29:   */     throws CompileError
/* 30:   */   {
/* 31:32 */     v.atFieldDecl(this);
/* 32:   */   }
/* 33:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.compiler.ast.FieldDecl
 * JD-Core Version:    0.7.0.1
 */