/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.History;
/*   4:    */ import com.gargoylesoftware.htmlunit.Page;
/*   5:    */ import com.gargoylesoftware.htmlunit.WebClient;
/*   6:    */ import com.gargoylesoftware.htmlunit.WebWindow;
/*   7:    */ import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptJobManager;
/*   8:    */ 
/*   9:    */ class PopupPseudoWebWindow
/*  10:    */   implements WebWindow
/*  11:    */ {
/*  12:    */   private final WebClient webClient_;
/*  13:    */   private Object scriptObject_;
/*  14:    */   private Page enclosedPage_;
/*  15:    */   
/*  16:    */   PopupPseudoWebWindow(WebClient webClient)
/*  17:    */   {
/*  18:118 */     this.webClient_ = webClient;
/*  19:119 */     this.webClient_.initialize(this);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public Page getEnclosedPage()
/*  23:    */   {
/*  24:126 */     return this.enclosedPage_;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public String getName()
/*  28:    */   {
/*  29:133 */     throw new RuntimeException("Not supported");
/*  30:    */   }
/*  31:    */   
/*  32:    */   public WebWindow getParentWindow()
/*  33:    */   {
/*  34:140 */     throw new RuntimeException("Not supported");
/*  35:    */   }
/*  36:    */   
/*  37:    */   public Object getScriptObject()
/*  38:    */   {
/*  39:147 */     return this.scriptObject_;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public JavaScriptJobManager getJobManager()
/*  43:    */   {
/*  44:154 */     throw new RuntimeException("Not supported");
/*  45:    */   }
/*  46:    */   
/*  47:    */   public WebWindow getTopWindow()
/*  48:    */   {
/*  49:161 */     throw new RuntimeException("Not supported");
/*  50:    */   }
/*  51:    */   
/*  52:    */   public WebClient getWebClient()
/*  53:    */   {
/*  54:168 */     return this.webClient_;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public History getHistory()
/*  58:    */   {
/*  59:175 */     throw new RuntimeException("Not supported");
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void setEnclosedPage(Page page)
/*  63:    */   {
/*  64:182 */     this.enclosedPage_ = page;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void setName(String name)
/*  68:    */   {
/*  69:189 */     throw new RuntimeException("Not supported");
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void setScriptObject(Object scriptObject)
/*  73:    */   {
/*  74:196 */     this.scriptObject_ = scriptObject;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public boolean isClosed()
/*  78:    */   {
/*  79:203 */     return false;
/*  80:    */   }
/*  81:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.PopupPseudoWebWindow
 * JD-Core Version:    0.7.0.1
 */