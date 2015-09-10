/*   1:    */ package org.w3c.css.sac;
/*   2:    */ 
/*   3:    */ public class CSSParseException
/*   4:    */   extends CSSException
/*   5:    */ {
/*   6:    */   private String uri;
/*   7:    */   private int lineNumber;
/*   8:    */   private int columnNumber;
/*   9:    */   
/*  10:    */   public CSSParseException(String paramString, Locator paramLocator)
/*  11:    */   {
/*  12: 49 */     super(paramString);
/*  13: 50 */     this.code = 2;
/*  14: 51 */     this.uri = paramLocator.getURI();
/*  15: 52 */     this.lineNumber = paramLocator.getLineNumber();
/*  16: 53 */     this.columnNumber = paramLocator.getColumnNumber();
/*  17:    */   }
/*  18:    */   
/*  19:    */   public CSSParseException(String paramString, Locator paramLocator, Exception paramException)
/*  20:    */   {
/*  21: 75 */     super((short)2, paramString, paramException);
/*  22: 76 */     this.uri = paramLocator.getURI();
/*  23: 77 */     this.lineNumber = paramLocator.getLineNumber();
/*  24: 78 */     this.columnNumber = paramLocator.getColumnNumber();
/*  25:    */   }
/*  26:    */   
/*  27:    */   public CSSParseException(String paramString1, String paramString2, int paramInt1, int paramInt2)
/*  28:    */   {
/*  29: 99 */     super(paramString1);
/*  30:100 */     this.code = 2;
/*  31:101 */     this.uri = paramString2;
/*  32:102 */     this.lineNumber = paramInt1;
/*  33:103 */     this.columnNumber = paramInt2;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public CSSParseException(String paramString1, String paramString2, int paramInt1, int paramInt2, Exception paramException)
/*  37:    */   {
/*  38:129 */     super((short)2, paramString1, paramException);
/*  39:130 */     this.uri = paramString2;
/*  40:131 */     this.lineNumber = paramInt1;
/*  41:132 */     this.columnNumber = paramInt2;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public String getURI()
/*  45:    */   {
/*  46:145 */     return this.uri;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public int getLineNumber()
/*  50:    */   {
/*  51:157 */     return this.lineNumber;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public int getColumnNumber()
/*  55:    */   {
/*  56:171 */     return this.columnNumber;
/*  57:    */   }
/*  58:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.w3c.css.sac.CSSParseException
 * JD-Core Version:    0.7.0.1
 */