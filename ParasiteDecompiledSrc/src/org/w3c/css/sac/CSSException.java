/*   1:    */ package org.w3c.css.sac;
/*   2:    */ 
/*   3:    */ public class CSSException
/*   4:    */   extends RuntimeException
/*   5:    */ {
/*   6:    */   protected String s;
/*   7:    */   public static final short SAC_UNSPECIFIED_ERR = 0;
/*   8:    */   public static final short SAC_NOT_SUPPORTED_ERR = 1;
/*   9:    */   public static final short SAC_SYNTAX_ERR = 2;
/*  10:    */   protected static final String S_SAC_UNSPECIFIED_ERR = "unknown error";
/*  11:    */   protected static final String S_SAC_NOT_SUPPORTED_ERR = "not supported";
/*  12:    */   protected static final String S_SAC_SYNTAX_ERR = "syntax error";
/*  13:    */   protected Exception e;
/*  14:    */   protected short code;
/*  15:    */   
/*  16:    */   public CSSException() {}
/*  17:    */   
/*  18:    */   public CSSException(String paramString)
/*  19:    */   {
/*  20: 70 */     this.code = 0;
/*  21: 71 */     this.s = paramString;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public CSSException(Exception paramException)
/*  25:    */   {
/*  26: 79 */     this.code = 0;
/*  27: 80 */     this.e = paramException;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public CSSException(short paramShort)
/*  31:    */   {
/*  32: 88 */     this.code = paramShort;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public CSSException(short paramShort, String paramString, Exception paramException)
/*  36:    */   {
/*  37: 98 */     this.code = paramShort;
/*  38: 99 */     this.s = paramString;
/*  39:100 */     this.e = paramException;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public String getMessage()
/*  43:    */   {
/*  44:110 */     if (this.s != null) {
/*  45:111 */       return this.s;
/*  46:    */     }
/*  47:112 */     if (this.e != null) {
/*  48:113 */       return this.e.getMessage();
/*  49:    */     }
/*  50:115 */     switch (this.code)
/*  51:    */     {
/*  52:    */     case 0: 
/*  53:117 */       return "unknown error";
/*  54:    */     case 1: 
/*  55:119 */       return "not supported";
/*  56:    */     case 2: 
/*  57:121 */       return "syntax error";
/*  58:    */     }
/*  59:123 */     return null;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public short getCode()
/*  63:    */   {
/*  64:132 */     return this.code;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public Exception getException()
/*  68:    */   {
/*  69:139 */     return this.e;
/*  70:    */   }
/*  71:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.w3c.css.sac.CSSException
 * JD-Core Version:    0.7.0.1
 */