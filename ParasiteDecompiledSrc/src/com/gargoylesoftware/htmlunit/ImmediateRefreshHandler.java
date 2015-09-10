/*  1:   */ package com.gargoylesoftware.htmlunit;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.io.Serializable;
/*  5:   */ import java.net.URL;
/*  6:   */ 
/*  7:   */ public class ImmediateRefreshHandler
/*  8:   */   implements RefreshHandler, Serializable
/*  9:   */ {
/* 10:   */   public void handleRefresh(Page page, URL url, int seconds)
/* 11:   */     throws IOException
/* 12:   */   {
/* 13:45 */     WebWindow window = page.getEnclosingWindow();
/* 14:46 */     if (window == null) {
/* 15:47 */       return;
/* 16:   */     }
/* 17:49 */     WebClient client = window.getWebClient();
/* 18:50 */     if ((page.getWebResponse().getWebRequest().getUrl().toExternalForm().equals(url.toExternalForm())) && (HttpMethod.GET == page.getWebResponse().getWebRequest().getHttpMethod()))
/* 19:   */     {
/* 20:52 */       String msg = "Refresh to " + url + " (" + seconds + "s) aborted by HtmlUnit: " + "Attempted to refresh a page using an ImmediateRefreshHandler " + "which could have caused an OutOfMemoryError " + "Please use WaitingRefreshHandler or ThreadedRefreshHandler instead.";
/* 21:   */       
/* 22:   */ 
/* 23:   */ 
/* 24:56 */       throw new RuntimeException(msg);
/* 25:   */     }
/* 26:58 */     client.getPage(window, new WebRequest(url));
/* 27:   */   }
/* 28:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.ImmediateRefreshHandler
 * JD-Core Version:    0.7.0.1
 */