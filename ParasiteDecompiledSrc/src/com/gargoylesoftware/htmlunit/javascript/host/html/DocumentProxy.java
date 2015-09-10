/*  1:   */ package com.gargoylesoftware.htmlunit.javascript.host.html;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.WebWindow;
/*  4:   */ import com.gargoylesoftware.htmlunit.javascript.SimpleScriptableProxy;
/*  5:   */ import com.gargoylesoftware.htmlunit.javascript.host.Document;
/*  6:   */ import com.gargoylesoftware.htmlunit.javascript.host.Window;
/*  7:   */ 
/*  8:   */ public class DocumentProxy
/*  9:   */   extends SimpleScriptableProxy<Document>
/* 10:   */ {
/* 11:   */   private final WebWindow webWindow_;
/* 12:   */   
/* 13:   */   public DocumentProxy(WebWindow webWindow)
/* 14:   */   {
/* 15:41 */     this.webWindow_ = webWindow;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public Document getDelegee()
/* 19:   */   {
/* 20:49 */     Window w = (Window)this.webWindow_.getScriptObject();
/* 21:50 */     return w.getDocument();
/* 22:   */   }
/* 23:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.html.DocumentProxy
 * JD-Core Version:    0.7.0.1
 */