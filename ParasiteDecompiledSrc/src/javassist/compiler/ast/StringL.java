/*  1:   */ package javassist.compiler.ast;
/*  2:   */ 
/*  3:   */ import javassist.compiler.CompileError;
/*  4:   */ 
/*  5:   */ public class StringL
/*  6:   */   extends ASTree
/*  7:   */ {
/*  8:   */   protected String text;
/*  9:   */   
/* 10:   */   public StringL(String t)
/* 11:   */   {
/* 12:27 */     this.text = t;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public String get()
/* 16:   */   {
/* 17:30 */     return this.text;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public String toString()
/* 21:   */   {
/* 22:32 */     return "\"" + this.text + "\"";
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void accept(Visitor v)
/* 26:   */     throws CompileError
/* 27:   */   {
/* 28:34 */     v.atStringL(this);
/* 29:   */   }
/* 30:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.compiler.ast.StringL
 * JD-Core Version:    0.7.0.1
 */