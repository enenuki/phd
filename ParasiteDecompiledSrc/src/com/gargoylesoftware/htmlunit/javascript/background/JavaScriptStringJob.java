/*  1:   */ package com.gargoylesoftware.htmlunit.javascript.background;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.WebWindow;
/*  4:   */ import com.gargoylesoftware.htmlunit.html.HtmlPage;
/*  5:   */ 
/*  6:   */ public class JavaScriptStringJob
/*  7:   */   extends JavaScriptExecutionJob
/*  8:   */ {
/*  9:   */   private final String script_;
/* 10:   */   
/* 11:   */   public JavaScriptStringJob(int initialDelay, Integer period, String label, WebWindow window, String script)
/* 12:   */   {
/* 13:40 */     super(initialDelay, period, label, window);
/* 14:41 */     this.script_ = script;
/* 15:   */   }
/* 16:   */   
/* 17:   */   protected void runJavaScript(HtmlPage page)
/* 18:   */   {
/* 19:47 */     if (this.script_ == null) {
/* 20:48 */       return;
/* 21:   */     }
/* 22:50 */     page.executeJavaScriptIfPossible(this.script_, "JavaScriptStringJob", 1);
/* 23:   */   }
/* 24:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.background.JavaScriptStringJob
 * JD-Core Version:    0.7.0.1
 */