/*  1:   */ package junit.framework;
/*  2:   */ 
/*  3:   */ public class ComparisonCompactor
/*  4:   */ {
/*  5:   */   private static final String ELLIPSIS = "...";
/*  6:   */   private static final String DELTA_END = "]";
/*  7:   */   private static final String DELTA_START = "[";
/*  8:   */   private int fContextLength;
/*  9:   */   private String fExpected;
/* 10:   */   private String fActual;
/* 11:   */   private int fPrefix;
/* 12:   */   private int fSuffix;
/* 13:   */   
/* 14:   */   public ComparisonCompactor(int contextLength, String expected, String actual)
/* 15:   */   {
/* 16:16 */     this.fContextLength = contextLength;
/* 17:17 */     this.fExpected = expected;
/* 18:18 */     this.fActual = actual;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public String compact(String message)
/* 22:   */   {
/* 23:22 */     if ((this.fExpected == null) || (this.fActual == null) || (areStringsEqual())) {
/* 24:23 */       return Assert.format(message, this.fExpected, this.fActual);
/* 25:   */     }
/* 26:25 */     findCommonPrefix();
/* 27:26 */     findCommonSuffix();
/* 28:27 */     String expected = compactString(this.fExpected);
/* 29:28 */     String actual = compactString(this.fActual);
/* 30:29 */     return Assert.format(message, expected, actual);
/* 31:   */   }
/* 32:   */   
/* 33:   */   private String compactString(String source)
/* 34:   */   {
/* 35:33 */     String result = "[" + source.substring(this.fPrefix, source.length() - this.fSuffix + 1) + "]";
/* 36:34 */     if (this.fPrefix > 0) {
/* 37:35 */       result = computeCommonPrefix() + result;
/* 38:   */     }
/* 39:36 */     if (this.fSuffix > 0) {
/* 40:37 */       result = result + computeCommonSuffix();
/* 41:   */     }
/* 42:38 */     return result;
/* 43:   */   }
/* 44:   */   
/* 45:   */   private void findCommonPrefix()
/* 46:   */   {
/* 47:42 */     this.fPrefix = 0;
/* 48:43 */     int end = Math.min(this.fExpected.length(), this.fActual.length());
/* 49:44 */     while ((this.fPrefix < end) && 
/* 50:45 */       (this.fExpected.charAt(this.fPrefix) == this.fActual.charAt(this.fPrefix))) {
/* 51:44 */       this.fPrefix += 1;
/* 52:   */     }
/* 53:   */   }
/* 54:   */   
/* 55:   */   private void findCommonSuffix()
/* 56:   */   {
/* 57:51 */     int expectedSuffix = this.fExpected.length() - 1;
/* 58:52 */     int actualSuffix = this.fActual.length() - 1;
/* 59:53 */     for (; (actualSuffix >= this.fPrefix) && (expectedSuffix >= this.fPrefix); expectedSuffix--)
/* 60:   */     {
/* 61:54 */       if (this.fExpected.charAt(expectedSuffix) != this.fActual.charAt(actualSuffix)) {
/* 62:   */         break;
/* 63:   */       }
/* 64:53 */       actualSuffix--;
/* 65:   */     }
/* 66:57 */     this.fSuffix = (this.fExpected.length() - expectedSuffix);
/* 67:   */   }
/* 68:   */   
/* 69:   */   private String computeCommonPrefix()
/* 70:   */   {
/* 71:61 */     return (this.fPrefix > this.fContextLength ? "..." : "") + this.fExpected.substring(Math.max(0, this.fPrefix - this.fContextLength), this.fPrefix);
/* 72:   */   }
/* 73:   */   
/* 74:   */   private String computeCommonSuffix()
/* 75:   */   {
/* 76:65 */     int end = Math.min(this.fExpected.length() - this.fSuffix + 1 + this.fContextLength, this.fExpected.length());
/* 77:66 */     return this.fExpected.substring(this.fExpected.length() - this.fSuffix + 1, end) + (this.fExpected.length() - this.fSuffix + 1 < this.fExpected.length() - this.fContextLength ? "..." : "");
/* 78:   */   }
/* 79:   */   
/* 80:   */   private boolean areStringsEqual()
/* 81:   */   {
/* 82:70 */     return this.fExpected.equals(this.fActual);
/* 83:   */   }
/* 84:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     junit.framework.ComparisonCompactor
 * JD-Core Version:    0.7.0.1
 */