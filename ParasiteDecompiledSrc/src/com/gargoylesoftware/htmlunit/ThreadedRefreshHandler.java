/*  1:   */ package com.gargoylesoftware.htmlunit;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.net.URL;
/*  5:   */ import org.apache.commons.logging.Log;
/*  6:   */ import org.apache.commons.logging.LogFactory;
/*  7:   */ 
/*  8:   */ public class ThreadedRefreshHandler
/*  9:   */   implements RefreshHandler
/* 10:   */ {
/* 11:38 */   private static final Log LOG = LogFactory.getLog(ThreadedRefreshHandler.class);
/* 12:   */   
/* 13:   */   public void handleRefresh(final Page page, final URL url, final int seconds)
/* 14:   */   {
/* 15:48 */     Thread thread = new Thread("ThreadedRefreshHandler Thread")
/* 16:   */     {
/* 17:   */       public void run()
/* 18:   */       {
/* 19:   */         try
/* 20:   */         {
/* 21:52 */           new WaitingRefreshHandler().handleRefresh(page, url, seconds);
/* 22:   */         }
/* 23:   */         catch (IOException e)
/* 24:   */         {
/* 25:55 */           ThreadedRefreshHandler.LOG.error("Unable to refresh page!", e);
/* 26:56 */           throw new RuntimeException("Unable to refresh page!", e);
/* 27:   */         }
/* 28:   */       }
/* 29:59 */     };
/* 30:60 */     thread.setDaemon(true);
/* 31:61 */     thread.start();
/* 32:   */   }
/* 33:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.ThreadedRefreshHandler
 * JD-Core Version:    0.7.0.1
 */