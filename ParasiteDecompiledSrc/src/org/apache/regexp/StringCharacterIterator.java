/*  1:   */ package org.apache.regexp;
/*  2:   */ 
/*  3:   */ public final class StringCharacterIterator
/*  4:   */   implements CharacterIterator
/*  5:   */ {
/*  6:   */   private final String src;
/*  7:   */   
/*  8:   */   public StringCharacterIterator(String paramString)
/*  9:   */   {
/* 10:72 */     this.src = paramString;
/* 11:   */   }
/* 12:   */   
/* 13:   */   public String substring(int paramInt1, int paramInt2)
/* 14:   */   {
/* 15:78 */     return this.src.substring(paramInt1, paramInt2);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public String substring(int paramInt)
/* 19:   */   {
/* 20:84 */     return this.src.substring(paramInt);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public char charAt(int paramInt)
/* 24:   */   {
/* 25:90 */     return this.src.charAt(paramInt);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public boolean isEnd(int paramInt)
/* 29:   */   {
/* 30:96 */     return paramInt >= this.src.length();
/* 31:   */   }
/* 32:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.regexp.StringCharacterIterator
 * JD-Core Version:    0.7.0.1
 */