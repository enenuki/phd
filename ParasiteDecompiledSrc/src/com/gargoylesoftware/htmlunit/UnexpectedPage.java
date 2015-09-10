/*  1:   */ package com.gargoylesoftware.htmlunit;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.io.InputStream;
/*  5:   */ import java.net.URL;
/*  6:   */ 
/*  7:   */ public class UnexpectedPage
/*  8:   */   implements Page
/*  9:   */ {
/* 10:   */   private final WebResponse webResponse_;
/* 11:   */   private WebWindow enclosingWindow_;
/* 12:   */   
/* 13:   */   public UnexpectedPage(WebResponse webResponse, WebWindow enclosingWindow)
/* 14:   */   {
/* 15:41 */     this.webResponse_ = webResponse;
/* 16:42 */     this.enclosingWindow_ = enclosingWindow;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void initialize() {}
/* 20:   */   
/* 21:   */   public void cleanUp() {}
/* 22:   */   
/* 23:   */   public InputStream getInputStream()
/* 24:   */     throws IOException
/* 25:   */   {
/* 26:65 */     return this.webResponse_.getContentAsStream();
/* 27:   */   }
/* 28:   */   
/* 29:   */   public WebResponse getWebResponse()
/* 30:   */   {
/* 31:74 */     return this.webResponse_;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public WebWindow getEnclosingWindow()
/* 35:   */   {
/* 36:83 */     return this.enclosingWindow_;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public URL getUrl()
/* 40:   */   {
/* 41:91 */     return getWebResponse().getWebRequest().getUrl();
/* 42:   */   }
/* 43:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.UnexpectedPage
 * JD-Core Version:    0.7.0.1
 */