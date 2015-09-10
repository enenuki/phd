/*   1:    */ package com.gargoylesoftware.htmlunit.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.Page;
/*   4:    */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*   5:    */ import com.gargoylesoftware.htmlunit.StringWebResponse;
/*   6:    */ import com.gargoylesoftware.htmlunit.WebResponse;
/*   7:    */ import com.gargoylesoftware.htmlunit.WebWindow;
/*   8:    */ import com.gargoylesoftware.htmlunit.WebWindowImpl;
/*   9:    */ 
/*  10:    */ public class FrameWindow
/*  11:    */   extends WebWindowImpl
/*  12:    */ {
/*  13:    */   private final BaseFrame frame_;
/*  14:    */   
/*  15:    */   FrameWindow(BaseFrame frame)
/*  16:    */   {
/*  17: 39 */     super(frame.getPage().getWebClient());
/*  18: 40 */     this.frame_ = frame;
/*  19: 41 */     WebWindowImpl parent = (WebWindowImpl)getParentWindow();
/*  20: 42 */     performRegistration();
/*  21: 43 */     parent.addChildWindow(this);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public String getName()
/*  25:    */   {
/*  26: 52 */     return this.frame_.getNameAttribute();
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void setName(String name)
/*  30:    */   {
/*  31: 61 */     this.frame_.setNameAttribute(name);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public WebWindow getParentWindow()
/*  35:    */   {
/*  36: 68 */     return this.frame_.getPage().getEnclosingWindow();
/*  37:    */   }
/*  38:    */   
/*  39:    */   public WebWindow getTopWindow()
/*  40:    */   {
/*  41: 75 */     return getParentWindow().getTopWindow();
/*  42:    */   }
/*  43:    */   
/*  44:    */   protected boolean isJavaScriptInitializationNeeded()
/*  45:    */   {
/*  46: 83 */     return (getScriptObject() == null) || (!(getEnclosedPage().getWebResponse() instanceof StringWebResponse));
/*  47:    */   }
/*  48:    */   
/*  49:    */   public HtmlPage getEnclosingPage()
/*  50:    */   {
/*  51: 95 */     return (HtmlPage)this.frame_.getPage();
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void setEnclosedPage(Page page)
/*  55:    */   {
/*  56:102 */     super.setEnclosedPage(page);
/*  57:    */     
/*  58:    */ 
/*  59:    */ 
/*  60:    */ 
/*  61:107 */     WebResponse webResponse = page.getWebResponse();
/*  62:108 */     if ((webResponse instanceof StringWebResponse))
/*  63:    */     {
/*  64:109 */       StringWebResponse response = (StringWebResponse)webResponse;
/*  65:110 */       if (response.isFromJavascript())
/*  66:    */       {
/*  67:111 */         BaseFrame frame = getFrameElement();
/*  68:112 */         frame.setContentLoaded();
/*  69:    */       }
/*  70:    */     }
/*  71:    */   }
/*  72:    */   
/*  73:    */   public BaseFrame getFrameElement()
/*  74:    */   {
/*  75:122 */     return this.frame_;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public String toString()
/*  79:    */   {
/*  80:131 */     return "FrameWindow[name=\"" + getName() + "\"]";
/*  81:    */   }
/*  82:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.FrameWindow
 * JD-Core Version:    0.7.0.1
 */