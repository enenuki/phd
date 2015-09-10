/*  1:   */ package com.gargoylesoftware.htmlunit;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.html.HtmlPage;
/*  4:   */ import java.io.IOException;
/*  5:   */ import java.io.ObjectInputStream;
/*  6:   */ import java.lang.ref.WeakReference;
/*  7:   */ import org.apache.commons.logging.Log;
/*  8:   */ import org.apache.commons.logging.LogFactory;
/*  9:   */ 
/* 10:   */ public class NicelyResynchronizingAjaxController
/* 11:   */   extends AjaxController
/* 12:   */ {
/* 13:38 */   private static final Log LOG = LogFactory.getLog(NicelyResynchronizingAjaxController.class);
/* 14:   */   private transient WeakReference<Thread> originatedThread_;
/* 15:   */   
/* 16:   */   public NicelyResynchronizingAjaxController()
/* 17:   */   {
/* 18:46 */     init();
/* 19:   */   }
/* 20:   */   
/* 21:   */   private void init()
/* 22:   */   {
/* 23:53 */     this.originatedThread_ = new WeakReference(Thread.currentThread());
/* 24:   */   }
/* 25:   */   
/* 26:   */   public boolean processSynchron(HtmlPage page, WebRequest settings, boolean async)
/* 27:   */   {
/* 28:63 */     if ((async) && (isInOriginalThread()))
/* 29:   */     {
/* 30:64 */       LOG.info("Re-synchronized call to " + settings.getUrl());
/* 31:65 */       return true;
/* 32:   */     }
/* 33:67 */     return !async;
/* 34:   */   }
/* 35:   */   
/* 36:   */   boolean isInOriginalThread()
/* 37:   */   {
/* 38:75 */     return Thread.currentThread() == this.originatedThread_.get();
/* 39:   */   }
/* 40:   */   
/* 41:   */   private void readObject(ObjectInputStream stream)
/* 42:   */     throws IOException, ClassNotFoundException
/* 43:   */   {
/* 44:85 */     stream.defaultReadObject();
/* 45:86 */     init();
/* 46:   */   }
/* 47:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController
 * JD-Core Version:    0.7.0.1
 */