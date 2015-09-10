/*   1:    */ package org.apache.regexp;
/*   2:    */ 
/*   3:    */ public final class CharacterArrayCharacterIterator
/*   4:    */   implements CharacterIterator
/*   5:    */ {
/*   6:    */   private final char[] src;
/*   7:    */   private final int off;
/*   8:    */   private final int len;
/*   9:    */   
/*  10:    */   public CharacterArrayCharacterIterator(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*  11:    */   {
/*  12: 76 */     this.src = paramArrayOfChar;
/*  13: 77 */     this.off = paramInt1;
/*  14: 78 */     this.len = paramInt2;
/*  15:    */   }
/*  16:    */   
/*  17:    */   public String substring(int paramInt1, int paramInt2)
/*  18:    */   {
/*  19: 84 */     return new String(this.src, this.off + paramInt1, paramInt2);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public String substring(int paramInt)
/*  23:    */   {
/*  24: 90 */     return new String(this.src, this.off + paramInt, this.len);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public char charAt(int paramInt)
/*  28:    */   {
/*  29: 96 */     return this.src[(this.off + paramInt)];
/*  30:    */   }
/*  31:    */   
/*  32:    */   public boolean isEnd(int paramInt)
/*  33:    */   {
/*  34:102 */     return paramInt >= this.len;
/*  35:    */   }
/*  36:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.regexp.CharacterArrayCharacterIterator
 * JD-Core Version:    0.7.0.1
 */