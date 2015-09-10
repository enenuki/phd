/*  1:   */ package org.apache.log4j.xml;
/*  2:   */ 
/*  3:   */ import org.apache.log4j.helpers.LogLog;
/*  4:   */ import org.xml.sax.ErrorHandler;
/*  5:   */ import org.xml.sax.SAXException;
/*  6:   */ import org.xml.sax.SAXParseException;
/*  7:   */ 
/*  8:   */ public class SAXErrorHandler
/*  9:   */   implements ErrorHandler
/* 10:   */ {
/* 11:   */   public void error(SAXParseException ex)
/* 12:   */   {
/* 13:28 */     emitMessage("Continuable parsing error ", ex);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public void fatalError(SAXParseException ex)
/* 17:   */   {
/* 18:33 */     emitMessage("Fatal parsing error ", ex);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void warning(SAXParseException ex)
/* 22:   */   {
/* 23:38 */     emitMessage("Parsing warning ", ex);
/* 24:   */   }
/* 25:   */   
/* 26:   */   private static void emitMessage(String msg, SAXParseException ex)
/* 27:   */   {
/* 28:42 */     LogLog.warn(msg + ex.getLineNumber() + " and column " + ex.getColumnNumber());
/* 29:   */     
/* 30:44 */     LogLog.warn(ex.getMessage(), ex.getException());
/* 31:   */   }
/* 32:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.xml.SAXErrorHandler
 * JD-Core Version:    0.7.0.1
 */