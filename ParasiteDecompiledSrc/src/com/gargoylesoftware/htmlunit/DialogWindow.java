/*  1:   */ package com.gargoylesoftware.htmlunit;
/*  2:   */ 
/*  3:   */ import net.sourceforge.htmlunit.corejs.javascript.ScriptableObject;
/*  4:   */ 
/*  5:   */ public class DialogWindow
/*  6:   */   extends WebWindowImpl
/*  7:   */ {
/*  8:   */   private Object arguments_;
/*  9:   */   
/* 10:   */   protected DialogWindow(WebClient webClient, Object arguments)
/* 11:   */   {
/* 12:35 */     super(webClient);
/* 13:36 */     this.arguments_ = arguments;
/* 14:37 */     performRegistration();
/* 15:   */   }
/* 16:   */   
/* 17:   */   protected boolean isJavaScriptInitializationNeeded()
/* 18:   */   {
/* 19:45 */     return getScriptObject() == null;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public WebWindow getParentWindow()
/* 23:   */   {
/* 24:52 */     return this;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public WebWindow getTopWindow()
/* 28:   */   {
/* 29:59 */     return this;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void setScriptObject(Object scriptObject)
/* 33:   */   {
/* 34:67 */     ScriptableObject so = (ScriptableObject)scriptObject;
/* 35:68 */     if (so != null) {
/* 36:69 */       so.put("dialogArguments", so, this.arguments_);
/* 37:   */     }
/* 38:71 */     super.setScriptObject(scriptObject);
/* 39:   */   }
/* 40:   */   
/* 41:   */   public void close()
/* 42:   */   {
/* 43:78 */     destroyChildren();
/* 44:79 */     getWebClient().deregisterWebWindow(this);
/* 45:   */   }
/* 46:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.DialogWindow
 * JD-Core Version:    0.7.0.1
 */