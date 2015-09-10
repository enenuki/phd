/*  1:   */ package com.gargoylesoftware.htmlunit.javascript.background;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.WebWindow;
/*  4:   */ import com.gargoylesoftware.htmlunit.html.HtmlElement;
/*  5:   */ import com.gargoylesoftware.htmlunit.html.HtmlPage;
/*  6:   */ import net.sourceforge.htmlunit.corejs.javascript.Function;
/*  7:   */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*  8:   */ 
/*  9:   */ public class JavaScriptFunctionJob
/* 10:   */   extends JavaScriptExecutionJob
/* 11:   */ {
/* 12:   */   private final Function function_;
/* 13:   */   
/* 14:   */   public JavaScriptFunctionJob(int initialDelay, Integer period, String label, WebWindow window, Function function)
/* 15:   */   {
/* 16:44 */     super(initialDelay, period, label, window);
/* 17:45 */     this.function_ = function;
/* 18:   */   }
/* 19:   */   
/* 20:   */   protected void runJavaScript(HtmlPage page)
/* 21:   */   {
/* 22:51 */     HtmlElement doc = page.getDocumentElement();
/* 23:52 */     Scriptable scriptable = (Scriptable)page.getEnclosingWindow().getScriptObject();
/* 24:53 */     page.executeJavaScriptFunctionIfPossible(this.function_, scriptable, new Object[0], doc);
/* 25:   */   }
/* 26:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.background.JavaScriptFunctionJob
 * JD-Core Version:    0.7.0.1
 */