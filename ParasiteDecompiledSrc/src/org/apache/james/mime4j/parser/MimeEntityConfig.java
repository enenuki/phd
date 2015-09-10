/*   1:    */ package org.apache.james.mime4j.parser;
/*   2:    */ 
/*   3:    */ public final class MimeEntityConfig
/*   4:    */   implements Cloneable
/*   5:    */ {
/*   6:    */   private boolean maximalBodyDescriptor;
/*   7:    */   private boolean strictParsing;
/*   8:    */   private int maxLineLen;
/*   9:    */   private int maxHeaderCount;
/*  10:    */   private long maxContentLen;
/*  11:    */   private boolean countLineNumbers;
/*  12:    */   
/*  13:    */   public MimeEntityConfig()
/*  14:    */   {
/*  15: 37 */     this.maximalBodyDescriptor = false;
/*  16: 38 */     this.strictParsing = false;
/*  17: 39 */     this.maxLineLen = 1000;
/*  18: 40 */     this.maxHeaderCount = 1000;
/*  19: 41 */     this.maxContentLen = -1L;
/*  20: 42 */     this.countLineNumbers = false;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public boolean isMaximalBodyDescriptor()
/*  24:    */   {
/*  25: 46 */     return this.maximalBodyDescriptor;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void setMaximalBodyDescriptor(boolean maximalBodyDescriptor)
/*  29:    */   {
/*  30: 50 */     this.maximalBodyDescriptor = maximalBodyDescriptor;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void setStrictParsing(boolean strictParsing)
/*  34:    */   {
/*  35: 63 */     this.strictParsing = strictParsing;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public boolean isStrictParsing()
/*  39:    */   {
/*  40: 73 */     return this.strictParsing;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void setMaxLineLen(int maxLineLen)
/*  44:    */   {
/*  45: 85 */     this.maxLineLen = maxLineLen;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public int getMaxLineLen()
/*  49:    */   {
/*  50: 95 */     return this.maxLineLen;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void setMaxHeaderCount(int maxHeaderCount)
/*  54:    */   {
/*  55:107 */     this.maxHeaderCount = maxHeaderCount;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public int getMaxHeaderCount()
/*  59:    */   {
/*  60:117 */     return this.maxHeaderCount;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void setMaxContentLen(long maxContentLen)
/*  64:    */   {
/*  65:129 */     this.maxContentLen = maxContentLen;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public long getMaxContentLen()
/*  69:    */   {
/*  70:139 */     return this.maxContentLen;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setCountLineNumbers(boolean countLineNumbers)
/*  74:    */   {
/*  75:150 */     this.countLineNumbers = countLineNumbers;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public boolean isCountLineNumbers()
/*  79:    */   {
/*  80:159 */     return this.countLineNumbers;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public MimeEntityConfig clone()
/*  84:    */   {
/*  85:    */     try
/*  86:    */     {
/*  87:165 */       return (MimeEntityConfig)super.clone();
/*  88:    */     }
/*  89:    */     catch (CloneNotSupportedException e)
/*  90:    */     {
/*  91:168 */       throw new InternalError();
/*  92:    */     }
/*  93:    */   }
/*  94:    */   
/*  95:    */   public String toString()
/*  96:    */   {
/*  97:174 */     return "[max body descriptor: " + this.maximalBodyDescriptor + ", strict parsing: " + this.strictParsing + ", max line length: " + this.maxLineLen + ", max header count: " + this.maxHeaderCount + ", max content length: " + this.maxContentLen + ", count line numbers: " + this.countLineNumbers + "]";
/*  98:    */   }
/*  99:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.parser.MimeEntityConfig
 * JD-Core Version:    0.7.0.1
 */