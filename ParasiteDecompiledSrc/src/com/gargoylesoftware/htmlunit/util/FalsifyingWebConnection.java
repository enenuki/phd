/*   1:    */ package com.gargoylesoftware.htmlunit.util;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.WebClient;
/*   4:    */ import com.gargoylesoftware.htmlunit.WebConnection;
/*   5:    */ import com.gargoylesoftware.htmlunit.WebRequest;
/*   6:    */ import com.gargoylesoftware.htmlunit.WebResponse;
/*   7:    */ import com.gargoylesoftware.htmlunit.WebResponseData;
/*   8:    */ import java.io.IOException;
/*   9:    */ import java.net.URL;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.List;
/*  12:    */ 
/*  13:    */ public abstract class FalsifyingWebConnection
/*  14:    */   extends WebConnectionWrapper
/*  15:    */ {
/*  16:    */   public FalsifyingWebConnection(WebConnection webConnection)
/*  17:    */     throws IllegalArgumentException
/*  18:    */   {
/*  19: 43 */     super(webConnection);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public FalsifyingWebConnection(WebClient webClient)
/*  23:    */     throws IllegalArgumentException
/*  24:    */   {
/*  25: 52 */     super(webClient);
/*  26:    */   }
/*  27:    */   
/*  28:    */   protected WebResponse deliverFromAlternateUrl(WebRequest webRequest, URL url)
/*  29:    */     throws IOException
/*  30:    */   {
/*  31: 64 */     URL originalUrl = webRequest.getUrl();
/*  32: 65 */     webRequest.setUrl(url);
/*  33: 66 */     WebResponse resp = super.getResponse(webRequest);
/*  34: 67 */     resp.getWebRequest().setUrl(originalUrl);
/*  35: 68 */     return resp;
/*  36:    */   }
/*  37:    */   
/*  38:    */   protected WebResponse replaceContent(WebResponse wr, String newContent)
/*  39:    */     throws IOException
/*  40:    */   {
/*  41: 79 */     byte[] body = newContent.getBytes(wr.getContentCharset());
/*  42: 80 */     WebResponseData wrd = new WebResponseData(body, wr.getStatusCode(), wr.getStatusMessage(), wr.getResponseHeaders());
/*  43:    */     
/*  44: 82 */     return new WebResponse(wrd, wr.getWebRequest().getUrl(), wr.getWebRequest().getHttpMethod(), wr.getLoadTime());
/*  45:    */   }
/*  46:    */   
/*  47:    */   protected WebResponse createWebResponse(WebRequest wr, String content, String contentType)
/*  48:    */     throws IOException
/*  49:    */   {
/*  50: 96 */     return createWebResponse(wr, content, contentType, 200, "OK");
/*  51:    */   }
/*  52:    */   
/*  53:    */   protected WebResponse createWebResponse(WebRequest wr, String content, String contentType, int responseCode, String responseMessage)
/*  54:    */     throws IOException
/*  55:    */   {
/*  56:111 */     List<NameValuePair> headers = new ArrayList();
/*  57:112 */     String encoding = "UTF-8";
/*  58:113 */     headers.add(new NameValuePair("content-type", contentType + "; charset=" + "UTF-8"));
/*  59:114 */     byte[] body = content.getBytes("UTF-8");
/*  60:115 */     WebResponseData wrd = new WebResponseData(body, responseCode, responseMessage, headers);
/*  61:116 */     return new WebResponse(wrd, wr.getUrl(), wr.getHttpMethod(), 0L);
/*  62:    */   }
/*  63:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.util.FalsifyingWebConnection
 * JD-Core Version:    0.7.0.1
 */