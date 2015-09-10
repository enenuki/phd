/*  1:   */ package com.gargoylesoftware.htmlunit.util;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.WebClient;
/*  4:   */ import com.gargoylesoftware.htmlunit.WebConnection;
/*  5:   */ import com.gargoylesoftware.htmlunit.WebRequest;
/*  6:   */ import com.gargoylesoftware.htmlunit.WebResponse;
/*  7:   */ import java.io.IOException;
/*  8:   */ 
/*  9:   */ public class WebConnectionWrapper
/* 10:   */   implements WebConnection
/* 11:   */ {
/* 12:   */   private final WebConnection wrappedWebConnection_;
/* 13:   */   
/* 14:   */   public WebConnectionWrapper(WebConnection webConnection)
/* 15:   */     throws IllegalArgumentException
/* 16:   */   {
/* 17:43 */     if (webConnection == null) {
/* 18:44 */       throw new IllegalArgumentException("Wrapped connection can't be null");
/* 19:   */     }
/* 20:46 */     this.wrappedWebConnection_ = webConnection;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public WebConnectionWrapper(WebClient webClient)
/* 24:   */     throws IllegalArgumentException
/* 25:   */   {
/* 26:56 */     if (webClient == null) {
/* 27:57 */       throw new IllegalArgumentException("WebClient can't be null");
/* 28:   */     }
/* 29:59 */     this.wrappedWebConnection_ = webClient.getWebConnection();
/* 30:60 */     webClient.setWebConnection(this);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public WebResponse getResponse(WebRequest request)
/* 34:   */     throws IOException
/* 35:   */   {
/* 36:68 */     return this.wrappedWebConnection_.getResponse(request);
/* 37:   */   }
/* 38:   */   
/* 39:   */   public WebConnection getWrappedWebConnection()
/* 40:   */   {
/* 41:76 */     return this.wrappedWebConnection_;
/* 42:   */   }
/* 43:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.util.WebConnectionWrapper
 * JD-Core Version:    0.7.0.1
 */