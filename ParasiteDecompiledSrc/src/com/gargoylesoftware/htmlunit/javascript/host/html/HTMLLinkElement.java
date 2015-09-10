/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.html.HtmlElement;
/*   4:    */ import com.gargoylesoftware.htmlunit.html.HtmlLink;
/*   5:    */ import com.gargoylesoftware.htmlunit.html.HtmlPage;
/*   6:    */ import com.gargoylesoftware.htmlunit.javascript.host.css.CSSStyleSheet;
/*   7:    */ import java.net.MalformedURLException;
/*   8:    */ import java.net.URL;
/*   9:    */ 
/*  10:    */ public class HTMLLinkElement
/*  11:    */   extends HTMLElement
/*  12:    */ {
/*  13:    */   private CSSStyleSheet sheet_;
/*  14:    */   
/*  15:    */   public void jsxSet_href(String href)
/*  16:    */   {
/*  17: 49 */     getDomNodeOrDie().setAttribute("href", href);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public String jsxGet_href()
/*  21:    */     throws Exception
/*  22:    */   {
/*  23: 58 */     HtmlLink link = (HtmlLink)getDomNodeOrDie();
/*  24: 59 */     String href = link.getHrefAttribute();
/*  25: 60 */     if (href.length() == 0) {
/*  26: 61 */       return href;
/*  27:    */     }
/*  28:    */     try
/*  29:    */     {
/*  30: 64 */       return ((HtmlPage)link.getPage()).getFullyQualifiedUrl(href).toString();
/*  31:    */     }
/*  32:    */     catch (MalformedURLException e) {}
/*  33: 67 */     return href;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void jsxSet_rel(String rel)
/*  37:    */   {
/*  38: 76 */     getDomNodeOrDie().setAttribute("rel", rel);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public String jsxGet_rel()
/*  42:    */     throws Exception
/*  43:    */   {
/*  44: 85 */     return ((HtmlLink)getDomNodeOrDie()).getRelAttribute();
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void jsxSet_rev(String rel)
/*  48:    */   {
/*  49: 93 */     getDomNodeOrDie().setAttribute("rev", rel);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public String jsxGet_rev()
/*  53:    */     throws Exception
/*  54:    */   {
/*  55:102 */     return ((HtmlLink)getDomNodeOrDie()).getRevAttribute();
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void jsxSet_type(String type)
/*  59:    */   {
/*  60:110 */     getDomNodeOrDie().setAttribute("type", type);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String jsxGet_type()
/*  64:    */     throws Exception
/*  65:    */   {
/*  66:119 */     return ((HtmlLink)getDomNodeOrDie()).getTypeAttribute();
/*  67:    */   }
/*  68:    */   
/*  69:    */   public CSSStyleSheet getSheet()
/*  70:    */   {
/*  71:128 */     if (this.sheet_ == null) {
/*  72:129 */       this.sheet_ = CSSStyleSheet.loadStylesheet(getWindow(), this, (HtmlLink)getDomNodeOrDie(), null);
/*  73:    */     }
/*  74:131 */     return this.sheet_;
/*  75:    */   }
/*  76:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.html.HTMLLinkElement
 * JD-Core Version:    0.7.0.1
 */