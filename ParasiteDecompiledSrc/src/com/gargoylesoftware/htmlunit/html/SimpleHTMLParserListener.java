/*  1:   */ package com.gargoylesoftware.htmlunit.html;
/*  2:   */ 
/*  3:   */ import java.net.URL;
/*  4:   */ import org.apache.commons.logging.Log;
/*  5:   */ import org.apache.commons.logging.LogFactory;
/*  6:   */ 
/*  7:   */ class SimpleHTMLParserListener
/*  8:   */   implements HTMLParserListener
/*  9:   */ {
/* 10:69 */   private static final Log LOG = LogFactory.getLog(HTMLParserListener.class);
/* 11:   */   
/* 12:   */   public void error(String message, URL url, int line, int column, String key)
/* 13:   */   {
/* 14:72 */     LOG.error(format(message, url, line, column));
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void warning(String message, URL url, int line, int column, String key)
/* 18:   */   {
/* 19:76 */     LOG.warn(format(message, url, line, column));
/* 20:   */   }
/* 21:   */   
/* 22:   */   private String format(String message, URL url, int line, int column)
/* 23:   */   {
/* 24:80 */     StringBuilder buffer = new StringBuilder(message);
/* 25:81 */     buffer.append(" (");
/* 26:82 */     buffer.append(url.toExternalForm());
/* 27:83 */     buffer.append(" ");
/* 28:84 */     buffer.append(line);
/* 29:85 */     buffer.append(":");
/* 30:86 */     buffer.append(column);
/* 31:87 */     buffer.append(")");
/* 32:88 */     return buffer.toString();
/* 33:   */   }
/* 34:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.SimpleHTMLParserListener
 * JD-Core Version:    0.7.0.1
 */