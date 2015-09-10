/*   1:    */ package com.gargoylesoftware.htmlunit.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.WebAssert;
/*   4:    */ import java.net.URL;
/*   5:    */ import org.apache.xerces.util.DefaultErrorHandler;
/*   6:    */ import org.apache.xerces.xni.XNIException;
/*   7:    */ import org.apache.xerces.xni.parser.XMLParseException;
/*   8:    */ 
/*   9:    */ class HTMLErrorHandler
/*  10:    */   extends DefaultErrorHandler
/*  11:    */ {
/*  12:    */   private final HTMLParserListener listener_;
/*  13:    */   private final URL url_;
/*  14:    */   
/*  15:    */   HTMLErrorHandler(HTMLParserListener listener, URL url)
/*  16:    */   {
/*  17:806 */     WebAssert.notNull("listener", listener);
/*  18:807 */     WebAssert.notNull("url", url);
/*  19:808 */     this.listener_ = listener;
/*  20:809 */     this.url_ = url;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public void error(String domain, String key, XMLParseException exception)
/*  24:    */     throws XNIException
/*  25:    */   {
/*  26:816 */     this.listener_.error(exception.getMessage(), this.url_, exception.getLineNumber(), exception.getColumnNumber(), key);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void warning(String domain, String key, XMLParseException exception)
/*  30:    */     throws XNIException
/*  31:    */   {
/*  32:827 */     this.listener_.warning(exception.getMessage(), this.url_, exception.getLineNumber(), exception.getColumnNumber(), key);
/*  33:    */   }
/*  34:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.HTMLErrorHandler
 * JD-Core Version:    0.7.0.1
 */