/*   1:    */ package com.gargoylesoftware.htmlunit;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.util.NameValuePair;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.net.URL;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.List;
/*   8:    */ 
/*   9:    */ public class StringWebResponse
/*  10:    */   extends WebResponse
/*  11:    */ {
/*  12:    */   private boolean fromJavascript_;
/*  13:    */   
/*  14:    */   private static WebResponseData getWebResponseData(String contentString, String charset)
/*  15:    */   {
/*  16: 48 */     byte[] content = TextUtil.stringToByteArray(contentString, charset);
/*  17: 49 */     List<NameValuePair> compiledHeaders = new ArrayList();
/*  18: 50 */     compiledHeaders.add(new NameValuePair("Content-Type", "text/html"));
/*  19:    */     try
/*  20:    */     {
/*  21: 52 */       return new WebResponseData(content, 200, "OK", compiledHeaders);
/*  22:    */     }
/*  23:    */     catch (IOException e)
/*  24:    */     {
/*  25: 55 */       throw new RuntimeException(e);
/*  26:    */     }
/*  27:    */   }
/*  28:    */   
/*  29:    */   public StringWebResponse(String content, URL originatingURL)
/*  30:    */   {
/*  31: 66 */     this(content, "UTF-8", originatingURL);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public StringWebResponse(String content, String charset, URL originatingURL)
/*  35:    */   {
/*  36: 76 */     super(getWebResponseData(content, charset), buildWebRequest(originatingURL, charset), 0L);
/*  37:    */   }
/*  38:    */   
/*  39:    */   private static WebRequest buildWebRequest(URL originatingURL, String charset)
/*  40:    */   {
/*  41: 80 */     WebRequest webRequest = new WebRequest(originatingURL, HttpMethod.GET);
/*  42: 81 */     webRequest.setCharset(charset);
/*  43: 82 */     return webRequest;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public boolean isFromJavascript()
/*  47:    */   {
/*  48: 91 */     return this.fromJavascript_;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void setFromJavascript(boolean fromJavascript)
/*  52:    */   {
/*  53:100 */     this.fromJavascript_ = fromJavascript;
/*  54:    */   }
/*  55:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.StringWebResponse
 * JD-Core Version:    0.7.0.1
 */