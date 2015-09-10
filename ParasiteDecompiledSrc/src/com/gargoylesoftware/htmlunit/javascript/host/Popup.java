/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.Page;
/*   4:    */ import com.gargoylesoftware.htmlunit.WebResponse;
/*   5:    */ import com.gargoylesoftware.htmlunit.WebWindow;
/*   6:    */ import com.gargoylesoftware.htmlunit.html.HTMLParser;
/*   7:    */ import com.gargoylesoftware.htmlunit.html.HtmlBody;
/*   8:    */ import com.gargoylesoftware.htmlunit.html.HtmlHtml;
/*   9:    */ import com.gargoylesoftware.htmlunit.html.HtmlPage;
/*  10:    */ import com.gargoylesoftware.htmlunit.html.IElementFactory;
/*  11:    */ import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
/*  12:    */ import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLDocument;
/*  13:    */ 
/*  14:    */ public class Popup
/*  15:    */   extends SimpleScriptable
/*  16:    */ {
/*  17:    */   private boolean opened_;
/*  18:    */   private HTMLDocument document_;
/*  19:    */   
/*  20:    */   public Popup()
/*  21:    */   {
/*  22: 48 */     this.opened_ = false;
/*  23:    */   }
/*  24:    */   
/*  25:    */   void init(Window openerJSWindow)
/*  26:    */   {
/*  27: 54 */     this.document_ = new HTMLDocument();
/*  28: 55 */     this.document_.setPrototype(openerJSWindow.getPrototype(HTMLDocument.class));
/*  29: 56 */     this.document_.setParentScope(this);
/*  30:    */     
/*  31: 58 */     WebWindow openerWindow = openerJSWindow.getWebWindow();
/*  32:    */     
/*  33: 60 */     WebWindow popupPseudoWindow = new PopupPseudoWebWindow(openerWindow.getWebClient());
/*  34:    */     
/*  35: 62 */     WebResponse webResponse = openerWindow.getEnclosedPage().getWebResponse();
/*  36: 63 */     HtmlPage popupPage = new HtmlPage(null, webResponse, popupPseudoWindow);
/*  37: 64 */     setDomNode(popupPage);
/*  38: 65 */     popupPseudoWindow.setEnclosedPage(popupPage);
/*  39: 66 */     HtmlHtml html = (HtmlHtml)HTMLParser.getFactory("html").createElement(popupPage, "html", null);
/*  40:    */     
/*  41: 68 */     popupPage.appendChild(html);
/*  42: 69 */     HtmlBody body = (HtmlBody)HTMLParser.getFactory("body").createElement(popupPage, "body", null);
/*  43:    */     
/*  44: 71 */     html.appendChild(body);
/*  45:    */     
/*  46: 73 */     this.document_.setDomNode(popupPage);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public Object jsxGet_document()
/*  50:    */   {
/*  51: 81 */     return this.document_;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public boolean jsxGet_isOpen()
/*  55:    */   {
/*  56: 89 */     return this.opened_;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void jsxFunction_hide()
/*  60:    */   {
/*  61: 96 */     this.opened_ = false;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void jsxFunction_show()
/*  65:    */   {
/*  66:103 */     this.opened_ = true;
/*  67:    */   }
/*  68:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.Popup
 * JD-Core Version:    0.7.0.1
 */