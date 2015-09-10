/*  1:   */ package com.gargoylesoftware.htmlunit;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.util.WebResponseWrapper;
/*  4:   */ 
/*  5:   */ class WebResponseFromCache
/*  6:   */   extends WebResponseWrapper
/*  7:   */ {
/*  8:   */   private final WebRequest request_;
/*  9:   */   
/* 10:   */   WebResponseFromCache(WebResponse cachedResponse, WebRequest currentRequest)
/* 11:   */   {
/* 12:36 */     super(cachedResponse);
/* 13:37 */     this.request_ = currentRequest;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public WebRequest getRequestSettings()
/* 17:   */   {
/* 18:45 */     return this.request_;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public WebRequest getWebRequest()
/* 22:   */   {
/* 23:53 */     return this.request_;
/* 24:   */   }
/* 25:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.WebResponseFromCache
 * JD-Core Version:    0.7.0.1
 */