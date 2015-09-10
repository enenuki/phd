/*  1:   */ package com.gargoylesoftware.htmlunit;
/*  2:   */ 
/*  3:   */ import java.net.URL;
/*  4:   */ 
/*  5:   */ @Deprecated
/*  6:   */ public class WebResponseImpl
/*  7:   */   extends WebResponse
/*  8:   */ {
/*  9:   */   public WebResponseImpl(WebResponseData responseData, URL url, HttpMethod requestMethod, long loadTime)
/* 10:   */   {
/* 11:43 */     this(responseData, new WebRequest(url, requestMethod), loadTime);
/* 12:   */   }
/* 13:   */   
/* 14:   */   public WebResponseImpl(WebResponseData responseData, WebRequest request, long loadTime)
/* 15:   */   {
/* 16:55 */     super(responseData, request, loadTime);
/* 17:   */   }
/* 18:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.WebResponseImpl
 * JD-Core Version:    0.7.0.1
 */