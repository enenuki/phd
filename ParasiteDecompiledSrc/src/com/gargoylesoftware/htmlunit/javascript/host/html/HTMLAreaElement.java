/*  1:   */ package com.gargoylesoftware.htmlunit.javascript.host.html;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.html.HtmlElement;
/*  4:   */ 
/*  5:   */ public class HTMLAreaElement
/*  6:   */   extends HTMLElement
/*  7:   */ {
/*  8:   */   public Object getDefaultValue(Class<?> hint)
/*  9:   */   {
/* 10:42 */     HtmlElement element = getDomNodeOrNull();
/* 11:43 */     if (element == null) {
/* 12:44 */       return super.getDefaultValue(null);
/* 13:   */     }
/* 14:46 */     return HTMLAnchorElement.getDefaultValue(element);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public String jsxGet_alt()
/* 18:   */   {
/* 19:54 */     String alt = getDomNodeOrDie().getAttribute("alt");
/* 20:55 */     return alt;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void jsxSet_alt(String alt)
/* 24:   */   {
/* 25:63 */     getDomNodeOrDie().setAttribute("alt", alt);
/* 26:   */   }
/* 27:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.html.HTMLAreaElement
 * JD-Core Version:    0.7.0.1
 */