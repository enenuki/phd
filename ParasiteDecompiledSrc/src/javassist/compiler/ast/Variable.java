/*  1:   */ package javassist.compiler.ast;
/*  2:   */ 
/*  3:   */ import javassist.compiler.CompileError;
/*  4:   */ 
/*  5:   */ public class Variable
/*  6:   */   extends Symbol
/*  7:   */ {
/*  8:   */   protected Declarator declarator;
/*  9:   */   
/* 10:   */   public Variable(String sym, Declarator d)
/* 11:   */   {
/* 12:27 */     super(sym);
/* 13:28 */     this.declarator = d;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public Declarator getDeclarator()
/* 17:   */   {
/* 18:31 */     return this.declarator;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public String toString()
/* 22:   */   {
/* 23:34 */     return this.identifier + ":" + this.declarator.getType();
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void accept(Visitor v)
/* 27:   */     throws CompileError
/* 28:   */   {
/* 29:37 */     v.atVariable(this);
/* 30:   */   }
/* 31:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.compiler.ast.Variable
 * JD-Core Version:    0.7.0.1
 */