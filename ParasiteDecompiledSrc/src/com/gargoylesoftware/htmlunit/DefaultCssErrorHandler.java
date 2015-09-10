/*  1:   */ package com.gargoylesoftware.htmlunit;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.apache.commons.logging.Log;
/*  5:   */ import org.apache.commons.logging.LogFactory;
/*  6:   */ import org.w3c.css.sac.CSSParseException;
/*  7:   */ import org.w3c.css.sac.ErrorHandler;
/*  8:   */ 
/*  9:   */ public class DefaultCssErrorHandler
/* 10:   */   implements ErrorHandler, Serializable
/* 11:   */ {
/* 12:33 */   private static final Log LOG = LogFactory.getLog(DefaultCssErrorHandler.class);
/* 13:   */   
/* 14:   */   public void error(CSSParseException exception)
/* 15:   */   {
/* 16:39 */     LOG.warn("CSS error: " + buildMessage(exception));
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void fatalError(CSSParseException exception)
/* 20:   */   {
/* 21:46 */     LOG.warn("CSS fatal error: " + buildMessage(exception));
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void warning(CSSParseException exception)
/* 25:   */   {
/* 26:53 */     LOG.warn("CSS warning: " + buildMessage(exception));
/* 27:   */   }
/* 28:   */   
/* 29:   */   private String buildMessage(CSSParseException exception)
/* 30:   */   {
/* 31:62 */     String uri = exception.getURI();
/* 32:63 */     int line = exception.getLineNumber();
/* 33:64 */     int col = exception.getColumnNumber();
/* 34:66 */     if (null == uri) {
/* 35:67 */       return "[" + line + ":" + col + "] " + exception.getMessage();
/* 36:   */     }
/* 37:69 */     return "'" + uri + "' [" + line + ":" + col + "] " + exception.getMessage();
/* 38:   */   }
/* 39:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.DefaultCssErrorHandler
 * JD-Core Version:    0.7.0.1
 */