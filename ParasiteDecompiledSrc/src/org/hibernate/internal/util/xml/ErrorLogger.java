/*  1:   */ package org.hibernate.internal.util.xml;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.hibernate.internal.CoreMessageLogger;
/*  5:   */ import org.jboss.logging.Logger;
/*  6:   */ import org.xml.sax.ErrorHandler;
/*  7:   */ import org.xml.sax.SAXParseException;
/*  8:   */ 
/*  9:   */ public class ErrorLogger
/* 10:   */   implements ErrorHandler, Serializable
/* 11:   */ {
/* 12:42 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, ErrorLogger.class.getName());
/* 13:   */   private SAXParseException error;
/* 14:   */   
/* 15:   */   public SAXParseException getError()
/* 16:   */   {
/* 17:52 */     return this.error;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void error(SAXParseException error)
/* 21:   */   {
/* 22:59 */     LOG.parsingXmlError(error.getLineNumber(), error.getMessage());
/* 23:60 */     if (this.error == null) {
/* 24:60 */       this.error = error;
/* 25:   */     }
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void fatalError(SAXParseException error)
/* 29:   */   {
/* 30:67 */     error(error);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void warning(SAXParseException warn)
/* 34:   */   {
/* 35:74 */     LOG.parsingXmlWarning(this.error.getLineNumber(), this.error.getMessage());
/* 36:   */   }
/* 37:   */   
/* 38:   */   public void reset()
/* 39:   */   {
/* 40:78 */     this.error = null;
/* 41:   */   }
/* 42:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.util.xml.ErrorLogger
 * JD-Core Version:    0.7.0.1
 */