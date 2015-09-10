/*  1:   */ package com.steadystate.css.parser;
/*  2:   */ 
/*  3:   */ public class Token
/*  4:   */ {
/*  5:   */   public int kind;
/*  6:   */   public int beginLine;
/*  7:   */   public int beginColumn;
/*  8:   */   public int endLine;
/*  9:   */   public int endColumn;
/* 10:   */   public String image;
/* 11:   */   public Token next;
/* 12:   */   public Token specialToken;
/* 13:   */   
/* 14:   */   public String toString()
/* 15:   */   {
/* 16:58 */     return this.image;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public static final Token newToken(int ofKind)
/* 20:   */   {
/* 21:75 */     switch (ofKind)
/* 22:   */     {
/* 23:   */     }
/* 24:77 */     return new Token();
/* 25:   */   }
/* 26:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.steadystate.css.parser.Token
 * JD-Core Version:    0.7.0.1
 */