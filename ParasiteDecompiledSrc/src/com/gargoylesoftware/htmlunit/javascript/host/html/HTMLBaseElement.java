/*  1:   */ package com.gargoylesoftware.htmlunit.javascript.host.html;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.html.HtmlElement;
/*  4:   */ 
/*  5:   */ public class HTMLBaseElement
/*  6:   */   extends HTMLElement
/*  7:   */ {
/*  8:   */   public String jsxGet_href()
/*  9:   */   {
/* 10:37 */     return getDomNodeOrDie().getAttribute("href");
/* 11:   */   }
/* 12:   */   
/* 13:   */   public void jsxSet_href(String href)
/* 14:   */   {
/* 15:45 */     getDomNodeOrDie().setAttribute("href", href);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public String jsxGet_target()
/* 19:   */   {
/* 20:53 */     return getDomNodeOrDie().getAttribute("target");
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void jsxSet_target(String target)
/* 24:   */   {
/* 25:61 */     getDomNodeOrDie().setAttribute("target", target);
/* 26:   */   }
/* 27:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.html.HTMLBaseElement
 * JD-Core Version:    0.7.0.1
 */