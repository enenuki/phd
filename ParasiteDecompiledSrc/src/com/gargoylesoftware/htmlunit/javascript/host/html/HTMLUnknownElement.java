/*  1:   */ package com.gargoylesoftware.htmlunit.javascript.host.html;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*  4:   */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*  5:   */ import com.gargoylesoftware.htmlunit.Page;
/*  6:   */ import com.gargoylesoftware.htmlunit.html.HtmlElement;
/*  7:   */ import com.gargoylesoftware.htmlunit.html.HtmlPage;
/*  8:   */ import com.gargoylesoftware.htmlunit.xml.XmlPage;
/*  9:   */ import java.util.Map;
/* 10:   */ 
/* 11:   */ public class HTMLUnknownElement
/* 12:   */   extends HTMLElement
/* 13:   */ {
/* 14:   */   public String jsxGet_nodeName()
/* 15:   */   {
/* 16:43 */     Page page = getDomNodeOrDie().getPage();
/* 17:44 */     if (((page instanceof XmlPage)) || ((getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_112)) && (((HtmlPage)page).getNamespaces().containsKey(getDomNodeOrDie().getPrefix())))) {
/* 18:46 */       return getDomNodeOrDie().getLocalName();
/* 19:   */     }
/* 20:48 */     return super.jsxGet_nodeName();
/* 21:   */   }
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.html.HTMLUnknownElement
 * JD-Core Version:    0.7.0.1
 */