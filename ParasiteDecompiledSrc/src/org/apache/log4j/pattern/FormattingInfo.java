/*   1:    */ package org.apache.log4j.pattern;
/*   2:    */ 
/*   3:    */ public final class FormattingInfo
/*   4:    */ {
/*   5: 35 */   private static final char[] SPACES = { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' };
/*   6: 41 */   private static final FormattingInfo DEFAULT = new FormattingInfo(false, 0, 2147483647);
/*   7:    */   private final int minLength;
/*   8:    */   private final int maxLength;
/*   9:    */   private final boolean leftAlign;
/*  10:    */   
/*  11:    */   public FormattingInfo(boolean leftAlign, int minLength, int maxLength)
/*  12:    */   {
/*  13: 67 */     this.leftAlign = leftAlign;
/*  14: 68 */     this.minLength = minLength;
/*  15: 69 */     this.maxLength = maxLength;
/*  16:    */   }
/*  17:    */   
/*  18:    */   public static FormattingInfo getDefault()
/*  19:    */   {
/*  20: 77 */     return DEFAULT;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public boolean isLeftAligned()
/*  24:    */   {
/*  25: 85 */     return this.leftAlign;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public int getMinLength()
/*  29:    */   {
/*  30: 93 */     return this.minLength;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public int getMaxLength()
/*  34:    */   {
/*  35:101 */     return this.maxLength;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void format(int fieldStart, StringBuffer buffer)
/*  39:    */   {
/*  40:111 */     int rawLength = buffer.length() - fieldStart;
/*  41:113 */     if (rawLength > this.maxLength) {
/*  42:114 */       buffer.delete(fieldStart, buffer.length() - this.maxLength);
/*  43:115 */     } else if (rawLength < this.minLength) {
/*  44:116 */       if (this.leftAlign)
/*  45:    */       {
/*  46:117 */         int fieldEnd = buffer.length();
/*  47:118 */         buffer.setLength(fieldStart + this.minLength);
/*  48:120 */         for (int i = fieldEnd; i < buffer.length(); i++) {
/*  49:121 */           buffer.setCharAt(i, ' ');
/*  50:    */         }
/*  51:    */       }
/*  52:    */       else
/*  53:    */       {
/*  54:124 */         for (int padLength = this.minLength - rawLength; padLength > 8; padLength -= 8) {
/*  55:127 */           buffer.insert(fieldStart, SPACES);
/*  56:    */         }
/*  57:130 */         buffer.insert(fieldStart, SPACES, 0, padLength);
/*  58:    */       }
/*  59:    */     }
/*  60:    */   }
/*  61:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.pattern.FormattingInfo
 * JD-Core Version:    0.7.0.1
 */