/*  1:   */ package com.steadystate.css.sac;
/*  2:   */ 
/*  3:   */ import org.w3c.css.sac.CSSParseException;
/*  4:   */ import org.w3c.css.sac.Locator;
/*  5:   */ 
/*  6:   */ public class TestCSSParseException
/*  7:   */   extends CSSParseException
/*  8:   */ {
/*  9:   */   private static final long serialVersionUID = -4892920039949256795L;
/* 10:   */   private String testCaseUri;
/* 11:   */   
/* 12:   */   public void setTestCaseUri(String testCaseUri)
/* 13:   */   {
/* 14:19 */     this.testCaseUri = testCaseUri;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public TestCSSParseException(String message, Locator locator, String testCaseUri)
/* 18:   */   {
/* 19:26 */     super(message, locator);
/* 20:27 */     setTestCaseUri(testCaseUri);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public TestCSSParseException(String message, Locator locator, Exception e, String testCaseUri)
/* 24:   */   {
/* 25:33 */     super(message, locator, e);
/* 26:34 */     setTestCaseUri(testCaseUri);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public TestCSSParseException(String message, String uri, int lineNumber, int columnNumber, String testCaseUri)
/* 30:   */   {
/* 31:40 */     super(message, uri, lineNumber, columnNumber);
/* 32:41 */     setTestCaseUri(testCaseUri);
/* 33:   */   }
/* 34:   */   
/* 35:   */   public TestCSSParseException(String message, String uri, int lineNumber, int columnNumber, Exception e, String testCaseUri)
/* 36:   */   {
/* 37:47 */     super(message, uri, lineNumber, columnNumber, e);
/* 38:48 */     setTestCaseUri(testCaseUri);
/* 39:   */   }
/* 40:   */   
/* 41:   */   public String getTestCaseUri()
/* 42:   */   {
/* 43:54 */     return this.testCaseUri;
/* 44:   */   }
/* 45:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.steadystate.css.sac.TestCSSParseException
 * JD-Core Version:    0.7.0.1
 */