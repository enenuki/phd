/*  1:   */ package com.gargoylesoftware.htmlunit.javascript.background;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.WebClient;
/*  4:   */ import com.gargoylesoftware.htmlunit.WebWindow;
/*  5:   */ import com.gargoylesoftware.htmlunit.html.HtmlPage;
/*  6:   */ import java.lang.ref.WeakReference;
/*  7:   */ import java.util.List;
/*  8:   */ import org.apache.commons.logging.Log;
/*  9:   */ import org.apache.commons.logging.LogFactory;
/* 10:   */ 
/* 11:   */ public abstract class JavaScriptExecutionJob
/* 12:   */   extends JavaScriptJob
/* 13:   */ {
/* 14:35 */   private static final Log LOG = LogFactory.getLog(JavaScriptExecutionJob.class);
/* 15:   */   private final String label_;
/* 16:   */   private transient WeakReference<WebWindow> window_;
/* 17:   */   
/* 18:   */   public JavaScriptExecutionJob(int initialDelay, Integer period, String label, WebWindow window)
/* 19:   */   {
/* 20:52 */     super(initialDelay, period);
/* 21:53 */     this.label_ = label;
/* 22:54 */     this.window_ = new WeakReference(window);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void run()
/* 26:   */   {
/* 27:59 */     WebWindow w = (WebWindow)this.window_.get();
/* 28:60 */     if (w == null) {
/* 29:62 */       return;
/* 30:   */     }
/* 31:65 */     if (LOG.isDebugEnabled()) {
/* 32:66 */       LOG.debug("Executing " + this + ".");
/* 33:   */     }
/* 34:   */     try
/* 35:   */     {
/* 36:71 */       HtmlPage page = (HtmlPage)w.getEnclosedPage();
/* 37:72 */       if ((w.getEnclosedPage() != page) || (!w.getWebClient().getWebWindows().contains(w)))
/* 38:   */       {
/* 39:73 */         if (LOG.isDebugEnabled()) {
/* 40:74 */           LOG.debug("The page that originated this job doesn't exist anymore. Execution cancelled.");
/* 41:   */         }
/* 42:   */       }
/* 43:78 */       else if (w.isClosed())
/* 44:   */       {
/* 45:79 */         if (LOG.isDebugEnabled()) {
/* 46:80 */           LOG.debug("Enclosing window is now closed. Execution cancelled.");
/* 47:   */         }
/* 48:   */       }
/* 49:   */       else {
/* 50:84 */         runJavaScript(page);
/* 51:   */       }
/* 52:   */     }
/* 53:   */     finally
/* 54:   */     {
/* 55:87 */       if (LOG.isDebugEnabled()) {
/* 56:88 */         LOG.debug("Finished executing " + this + ".");
/* 57:   */       }
/* 58:   */     }
/* 59:   */   }
/* 60:   */   
/* 61:   */   public String toString()
/* 62:   */   {
/* 63:96 */     return "JavaScript Execution Job " + getId() + ": " + this.label_;
/* 64:   */   }
/* 65:   */   
/* 66:   */   protected abstract void runJavaScript(HtmlPage paramHtmlPage);
/* 67:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.background.JavaScriptExecutionJob
 * JD-Core Version:    0.7.0.1
 */