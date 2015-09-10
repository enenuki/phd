/*  1:   */ package com.gargoylesoftware.htmlunit.javascript.host;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.WebWindow;
/*  4:   */ import com.gargoylesoftware.htmlunit.javascript.SimpleScriptableProxy;
/*  5:   */ 
/*  6:   */ public class WindowProxy
/*  7:   */   extends SimpleScriptableProxy<Window>
/*  8:   */ {
/*  9:   */   private final WebWindow webWindow_;
/* 10:   */   
/* 11:   */   public WindowProxy(WebWindow webWindow)
/* 12:   */   {
/* 13:35 */     this.webWindow_ = webWindow;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public Window getDelegee()
/* 17:   */   {
/* 18:43 */     return (Window)this.webWindow_.getScriptObject();
/* 19:   */   }
/* 20:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.WindowProxy
 * JD-Core Version:    0.7.0.1
 */