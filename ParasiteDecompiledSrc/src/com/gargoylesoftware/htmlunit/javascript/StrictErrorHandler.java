/*  1:   */ package com.gargoylesoftware.htmlunit.javascript;
/*  2:   */ 
/*  3:   */ import org.xml.sax.ErrorHandler;
/*  4:   */ import org.xml.sax.SAXParseException;
/*  5:   */ 
/*  6:   */ public class StrictErrorHandler
/*  7:   */   implements ErrorHandler
/*  8:   */ {
/*  9:   */   public void warning(SAXParseException rethrow)
/* 10:   */     throws SAXParseException
/* 11:   */   {
/* 12:34 */     throw rethrow;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public void error(SAXParseException rethrow)
/* 16:   */     throws SAXParseException
/* 17:   */   {
/* 18:44 */     throw rethrow;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void fatalError(SAXParseException rethrow)
/* 22:   */     throws SAXParseException
/* 23:   */   {
/* 24:54 */     throw rethrow;
/* 25:   */   }
/* 26:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.StrictErrorHandler
 * JD-Core Version:    0.7.0.1
 */