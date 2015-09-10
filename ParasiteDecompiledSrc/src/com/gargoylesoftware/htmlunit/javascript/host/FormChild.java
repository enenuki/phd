/*  1:   */ package com.gargoylesoftware.htmlunit.javascript.host;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.html.HtmlElement;
/*  4:   */ import com.gargoylesoftware.htmlunit.html.HtmlForm;
/*  5:   */ import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLElement;
/*  6:   */ import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLFormElement;
/*  7:   */ 
/*  8:   */ public class FormChild
/*  9:   */   extends HTMLElement
/* 10:   */ {
/* 11:   */   public HTMLFormElement jsxGet_form()
/* 12:   */   {
/* 13:42 */     HtmlForm form = getDomNodeOrDie().getEnclosingForm();
/* 14:43 */     if (form == null) {
/* 15:44 */       return null;
/* 16:   */     }
/* 17:46 */     return (HTMLFormElement)getScriptableFor(form);
/* 18:   */   }
/* 19:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.FormChild
 * JD-Core Version:    0.7.0.1
 */