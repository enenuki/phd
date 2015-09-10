/*  1:   */ package com.gargoylesoftware.htmlunit;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.net.URL;
/*  5:   */ import org.apache.commons.logging.Log;
/*  6:   */ import org.apache.commons.logging.LogFactory;
/*  7:   */ 
/*  8:   */ public class WaitingRefreshHandler
/*  9:   */   implements RefreshHandler
/* 10:   */ {
/* 11:38 */   private static final Log LOG = LogFactory.getLog(WaitingRefreshHandler.class);
/* 12:   */   private final int maxwait_;
/* 13:   */   
/* 14:   */   public WaitingRefreshHandler(int maxwait)
/* 15:   */   {
/* 16:50 */     this.maxwait_ = maxwait;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public WaitingRefreshHandler()
/* 20:   */   {
/* 21:57 */     this.maxwait_ = 0;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void handleRefresh(Page page, URL url, int requestedWait)
/* 25:   */     throws IOException
/* 26:   */   {
/* 27:69 */     int seconds = requestedWait;
/* 28:70 */     if ((seconds > this.maxwait_) && (this.maxwait_ > 0)) {
/* 29:71 */       seconds = this.maxwait_;
/* 30:   */     }
/* 31:   */     try
/* 32:   */     {
/* 33:74 */       Thread.sleep(seconds * 1000);
/* 34:   */     }
/* 35:   */     catch (InterruptedException e)
/* 36:   */     {
/* 37:83 */       if (LOG.isDebugEnabled()) {
/* 38:84 */         LOG.debug("Waiting thread was interrupted. Ignoring interruption to continue navigation.");
/* 39:   */       }
/* 40:   */     }
/* 41:87 */     WebWindow window = page.getEnclosingWindow();
/* 42:88 */     if (window == null) {
/* 43:89 */       return;
/* 44:   */     }
/* 45:91 */     WebClient client = window.getWebClient();
/* 46:92 */     client.getPage(window, new WebRequest(url));
/* 47:   */   }
/* 48:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.WaitingRefreshHandler
 * JD-Core Version:    0.7.0.1
 */