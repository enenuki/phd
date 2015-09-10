/*  1:   */ package com.gargoylesoftware.htmlunit.javascript.host.html;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.html.HtmlElement;
/*  4:   */ 
/*  5:   */ public class HTMLInsElement
/*  6:   */   extends HTMLElement
/*  7:   */ {
/*  8:   */   public String jsxGet_cite()
/*  9:   */   {
/* 10:37 */     String cite = getDomNodeOrDie().getAttribute("cite");
/* 11:38 */     return cite;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public void jsxSet_cite(String cite)
/* 15:   */   {
/* 16:46 */     getDomNodeOrDie().setAttribute("cite", cite);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public String jsxGet_dateTime()
/* 20:   */   {
/* 21:54 */     String cite = getDomNodeOrDie().getAttribute("datetime");
/* 22:55 */     return cite;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void jsxSet_dateTime(String dateTime)
/* 26:   */   {
/* 27:63 */     getDomNodeOrDie().setAttribute("datetime", dateTime);
/* 28:   */   }
/* 29:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.html.HTMLInsElement
 * JD-Core Version:    0.7.0.1
 */