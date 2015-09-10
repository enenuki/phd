/*   1:    */ package com.gargoylesoftware.htmlunit;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.html.HtmlPage;
/*   4:    */ import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptJobManager;
/*   5:    */ import org.apache.commons.logging.Log;
/*   6:    */ import org.apache.commons.logging.LogFactory;
/*   7:    */ 
/*   8:    */ public class TopLevelWindow
/*   9:    */   extends WebWindowImpl
/*  10:    */ {
/*  11: 34 */   private static final Log LOG = LogFactory.getLog(TopLevelWindow.class);
/*  12:    */   private WebWindow opener_;
/*  13:    */   
/*  14:    */   protected TopLevelWindow(String name, WebClient webClient)
/*  15:    */   {
/*  16: 45 */     super(webClient);
/*  17: 46 */     WebAssert.notNull("name", name);
/*  18: 47 */     setName(name);
/*  19: 48 */     performRegistration();
/*  20:    */   }
/*  21:    */   
/*  22:    */   public WebWindow getParentWindow()
/*  23:    */   {
/*  24: 56 */     return this;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public WebWindow getTopWindow()
/*  28:    */   {
/*  29: 64 */     return this;
/*  30:    */   }
/*  31:    */   
/*  32:    */   protected boolean isJavaScriptInitializationNeeded()
/*  33:    */   {
/*  34: 72 */     Page enclosedPage = getEnclosedPage();
/*  35: 73 */     return (getScriptObject() == null) || (enclosedPage.getWebResponse().getWebRequest().getUrl() == WebClient.URL_ABOUT_BLANK) || (!(enclosedPage.getWebResponse() instanceof StringWebResponse));
/*  36:    */   }
/*  37:    */   
/*  38:    */   public String toString()
/*  39:    */   {
/*  40: 85 */     return "TopLevelWindow[name=\"" + getName() + "\"]";
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void setOpener(WebWindow opener)
/*  44:    */   {
/*  45: 93 */     this.opener_ = opener;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public WebWindow getOpener()
/*  49:    */   {
/*  50:101 */     return this.opener_;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void close()
/*  54:    */   {
/*  55:108 */     setClosed();
/*  56:109 */     Page page = getEnclosedPage();
/*  57:110 */     if ((page instanceof HtmlPage))
/*  58:    */     {
/*  59:111 */       HtmlPage htmlPage = (HtmlPage)page;
/*  60:112 */       if (!htmlPage.isOnbeforeunloadAccepted())
/*  61:    */       {
/*  62:113 */         if (LOG.isDebugEnabled()) {
/*  63:114 */           LOG.debug("The registered OnbeforeunloadHandler rejected the window close event.");
/*  64:    */         }
/*  65:116 */         return;
/*  66:    */       }
/*  67:118 */       htmlPage.cleanUp();
/*  68:    */     }
/*  69:120 */     getJobManager().shutdown();
/*  70:121 */     destroyChildren();
/*  71:122 */     getWebClient().deregisterWebWindow(this);
/*  72:    */   }
/*  73:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.TopLevelWindow
 * JD-Core Version:    0.7.0.1
 */