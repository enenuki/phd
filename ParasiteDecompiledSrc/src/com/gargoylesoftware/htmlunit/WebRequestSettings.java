/*  1:   */ package com.gargoylesoftware.htmlunit;
/*  2:   */ 
/*  3:   */ import java.net.URL;
/*  4:   */ 
/*  5:   */ @Deprecated
/*  6:   */ public class WebRequestSettings
/*  7:   */   extends WebRequest
/*  8:   */ {
/*  9:   */   public WebRequestSettings(URL url)
/* 10:   */   {
/* 11:38 */     super(url);
/* 12:   */   }
/* 13:   */   
/* 14:   */   public WebRequestSettings(WebRequestSettings originalRequest, URL url)
/* 15:   */   {
/* 16:48 */     super(originalRequest, url);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public WebRequestSettings(URL url, HttpMethod submitMethod)
/* 20:   */   {
/* 21:57 */     super(url, submitMethod);
/* 22:   */   }
/* 23:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.WebRequestSettings
 * JD-Core Version:    0.7.0.1
 */