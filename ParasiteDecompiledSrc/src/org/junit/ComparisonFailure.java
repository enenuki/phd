/*   1:    */ package org.junit;
/*   2:    */ 
/*   3:    */ public class ComparisonFailure
/*   4:    */   extends AssertionError
/*   5:    */ {
/*   6:    */   private static final int MAX_CONTEXT_LENGTH = 20;
/*   7:    */   private static final long serialVersionUID = 1L;
/*   8:    */   private String fExpected;
/*   9:    */   private String fActual;
/*  10:    */   
/*  11:    */   public ComparisonFailure(String message, String expected, String actual)
/*  12:    */   {
/*  13: 28 */     super(message);
/*  14: 29 */     this.fExpected = expected;
/*  15: 30 */     this.fActual = actual;
/*  16:    */   }
/*  17:    */   
/*  18:    */   public String getMessage()
/*  19:    */   {
/*  20: 41 */     return new ComparisonCompactor(20, this.fExpected, this.fActual).compact(super.getMessage());
/*  21:    */   }
/*  22:    */   
/*  23:    */   public String getActual()
/*  24:    */   {
/*  25: 49 */     return this.fActual;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public String getExpected()
/*  29:    */   {
/*  30: 56 */     return this.fExpected;
/*  31:    */   }
/*  32:    */   
/*  33:    */   private static class ComparisonCompactor
/*  34:    */   {
/*  35:    */     private static final String ELLIPSIS = "...";
/*  36:    */     private static final String DELTA_END = "]";
/*  37:    */     private static final String DELTA_START = "[";
/*  38:    */     private int fContextLength;
/*  39:    */     private String fExpected;
/*  40:    */     private String fActual;
/*  41:    */     private int fPrefix;
/*  42:    */     private int fSuffix;
/*  43:    */     
/*  44:    */     public ComparisonCompactor(int contextLength, String expected, String actual)
/*  45:    */     {
/*  46: 81 */       this.fContextLength = contextLength;
/*  47: 82 */       this.fExpected = expected;
/*  48: 83 */       this.fActual = actual;
/*  49:    */     }
/*  50:    */     
/*  51:    */     private String compact(String message)
/*  52:    */     {
/*  53: 87 */       if ((this.fExpected == null) || (this.fActual == null) || (areStringsEqual())) {
/*  54: 88 */         return Assert.format(message, this.fExpected, this.fActual);
/*  55:    */       }
/*  56: 90 */       findCommonPrefix();
/*  57: 91 */       findCommonSuffix();
/*  58: 92 */       String expected = compactString(this.fExpected);
/*  59: 93 */       String actual = compactString(this.fActual);
/*  60: 94 */       return Assert.format(message, expected, actual);
/*  61:    */     }
/*  62:    */     
/*  63:    */     private String compactString(String source)
/*  64:    */     {
/*  65: 98 */       String result = "[" + source.substring(this.fPrefix, source.length() - this.fSuffix + 1) + "]";
/*  66: 99 */       if (this.fPrefix > 0) {
/*  67:100 */         result = computeCommonPrefix() + result;
/*  68:    */       }
/*  69:101 */       if (this.fSuffix > 0) {
/*  70:102 */         result = result + computeCommonSuffix();
/*  71:    */       }
/*  72:103 */       return result;
/*  73:    */     }
/*  74:    */     
/*  75:    */     private void findCommonPrefix()
/*  76:    */     {
/*  77:107 */       this.fPrefix = 0;
/*  78:108 */       int end = Math.min(this.fExpected.length(), this.fActual.length());
/*  79:109 */       while ((this.fPrefix < end) && 
/*  80:110 */         (this.fExpected.charAt(this.fPrefix) == this.fActual.charAt(this.fPrefix))) {
/*  81:109 */         this.fPrefix += 1;
/*  82:    */       }
/*  83:    */     }
/*  84:    */     
/*  85:    */     private void findCommonSuffix()
/*  86:    */     {
/*  87:116 */       int expectedSuffix = this.fExpected.length() - 1;
/*  88:117 */       int actualSuffix = this.fActual.length() - 1;
/*  89:118 */       for (; (actualSuffix >= this.fPrefix) && (expectedSuffix >= this.fPrefix); expectedSuffix--)
/*  90:    */       {
/*  91:119 */         if (this.fExpected.charAt(expectedSuffix) != this.fActual.charAt(actualSuffix)) {
/*  92:    */           break;
/*  93:    */         }
/*  94:118 */         actualSuffix--;
/*  95:    */       }
/*  96:122 */       this.fSuffix = (this.fExpected.length() - expectedSuffix);
/*  97:    */     }
/*  98:    */     
/*  99:    */     private String computeCommonPrefix()
/* 100:    */     {
/* 101:126 */       return (this.fPrefix > this.fContextLength ? "..." : "") + this.fExpected.substring(Math.max(0, this.fPrefix - this.fContextLength), this.fPrefix);
/* 102:    */     }
/* 103:    */     
/* 104:    */     private String computeCommonSuffix()
/* 105:    */     {
/* 106:130 */       int end = Math.min(this.fExpected.length() - this.fSuffix + 1 + this.fContextLength, this.fExpected.length());
/* 107:131 */       return this.fExpected.substring(this.fExpected.length() - this.fSuffix + 1, end) + (this.fExpected.length() - this.fSuffix + 1 < this.fExpected.length() - this.fContextLength ? "..." : "");
/* 108:    */     }
/* 109:    */     
/* 110:    */     private boolean areStringsEqual()
/* 111:    */     {
/* 112:135 */       return this.fExpected.equals(this.fActual);
/* 113:    */     }
/* 114:    */   }
/* 115:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.ComparisonFailure
 * JD-Core Version:    0.7.0.1
 */