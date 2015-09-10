/*  1:   */ package com.gargoylesoftware.htmlunit.javascript.host.html;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.javascript.host.Window;
/*  4:   */ 
/*  5:   */ public class HTMLHtmlElement
/*  6:   */   extends HTMLElement
/*  7:   */ {
/*  8:   */   public Object jsxGet_parentNode()
/*  9:   */   {
/* 10:36 */     return getWindow().jsxGet_document();
/* 11:   */   }
/* 12:   */   
/* 13:   */   public int jsxGet_clientWidth()
/* 14:   */   {
/* 15:42 */     return getWindow().jsxGet_innerWidth();
/* 16:   */   }
/* 17:   */   
/* 18:   */   public int jsxGet_clientHeight()
/* 19:   */   {
/* 20:48 */     return getWindow().jsxGet_innerHeight();
/* 21:   */   }
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.html.HTMLHtmlElement
 * JD-Core Version:    0.7.0.1
 */