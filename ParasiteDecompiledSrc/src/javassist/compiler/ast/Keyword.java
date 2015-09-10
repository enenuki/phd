/*  1:   */ package javassist.compiler.ast;
/*  2:   */ 
/*  3:   */ import javassist.compiler.CompileError;
/*  4:   */ 
/*  5:   */ public class Keyword
/*  6:   */   extends ASTree
/*  7:   */ {
/*  8:   */   protected int tokenId;
/*  9:   */   
/* 10:   */   public Keyword(int token)
/* 11:   */   {
/* 12:27 */     this.tokenId = token;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public int get()
/* 16:   */   {
/* 17:30 */     return this.tokenId;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public String toString()
/* 21:   */   {
/* 22:32 */     return "id:" + this.tokenId;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void accept(Visitor v)
/* 26:   */     throws CompileError
/* 27:   */   {
/* 28:34 */     v.atKeyword(this);
/* 29:   */   }
/* 30:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.compiler.ast.Keyword
 * JD-Core Version:    0.7.0.1
 */