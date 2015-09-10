/*  1:   */ package com.gargoylesoftware.htmlunit;
/*  2:   */ 
/*  3:   */ import java.net.URL;
/*  4:   */ 
/*  5:   */ public class FailingHttpStatusCodeException
/*  6:   */   extends RuntimeException
/*  7:   */ {
/*  8:   */   private final WebResponse response_;
/*  9:   */   
/* 10:   */   public FailingHttpStatusCodeException(WebResponse failingResponse)
/* 11:   */   {
/* 12:35 */     this(buildMessage(failingResponse), failingResponse);
/* 13:   */   }
/* 14:   */   
/* 15:   */   FailingHttpStatusCodeException(String message, WebResponse failingResponse)
/* 16:   */   {
/* 17:44 */     super(message);
/* 18:45 */     this.response_ = failingResponse;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public int getStatusCode()
/* 22:   */   {
/* 23:53 */     return this.response_.getStatusCode();
/* 24:   */   }
/* 25:   */   
/* 26:   */   public String getStatusMessage()
/* 27:   */   {
/* 28:61 */     return this.response_.getStatusMessage();
/* 29:   */   }
/* 30:   */   
/* 31:   */   private static String buildMessage(WebResponse failingResponse)
/* 32:   */   {
/* 33:65 */     int code = failingResponse.getStatusCode();
/* 34:66 */     String msg = failingResponse.getStatusMessage();
/* 35:67 */     URL url = failingResponse.getWebRequest().getUrl();
/* 36:68 */     return code + " " + msg + " for " + url;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public WebResponse getResponse()
/* 40:   */   {
/* 41:76 */     return this.response_;
/* 42:   */   }
/* 43:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException
 * JD-Core Version:    0.7.0.1
 */