/*  1:   */ package com.gargoylesoftware.htmlunit;
/*  2:   */ 
/*  3:   */ import java.net.URL;
/*  4:   */ 
/*  5:   */ public class TextPage
/*  6:   */   implements Page
/*  7:   */ {
/*  8:   */   private final WebResponse webResponse_;
/*  9:   */   private final String content_;
/* 10:   */   private WebWindow enclosingWindow_;
/* 11:   */   
/* 12:   */   public TextPage(WebResponse webResponse, WebWindow enclosingWindow)
/* 13:   */   {
/* 14:40 */     this.webResponse_ = webResponse;
/* 15:41 */     this.content_ = webResponse.getContentAsString();
/* 16:42 */     this.enclosingWindow_ = enclosingWindow;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void initialize() {}
/* 20:   */   
/* 21:   */   public void cleanUp() {}
/* 22:   */   
/* 23:   */   public String getContent()
/* 24:   */   {
/* 25:63 */     return this.content_;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public WebResponse getWebResponse()
/* 29:   */   {
/* 30:72 */     return this.webResponse_;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public WebWindow getEnclosingWindow()
/* 34:   */   {
/* 35:81 */     return this.enclosingWindow_;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public URL getUrl()
/* 39:   */   {
/* 40:89 */     return getWebResponse().getWebRequest().getUrl();
/* 41:   */   }
/* 42:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.TextPage
 * JD-Core Version:    0.7.0.1
 */