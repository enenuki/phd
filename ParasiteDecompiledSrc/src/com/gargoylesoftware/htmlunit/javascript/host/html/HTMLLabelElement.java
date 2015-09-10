/*  1:   */ package com.gargoylesoftware.htmlunit.javascript.host.html;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.html.HtmlLabel;
/*  4:   */ import com.gargoylesoftware.htmlunit.javascript.host.FormChild;
/*  5:   */ 
/*  6:   */ public class HTMLLabelElement
/*  7:   */   extends FormChild
/*  8:   */ {
/*  9:   */   public String jsxGet_htmlFor()
/* 10:   */   {
/* 11:40 */     return ((HtmlLabel)getDomNodeOrDie()).getForAttribute();
/* 12:   */   }
/* 13:   */   
/* 14:   */   public void jsxSet_htmlFor(String id)
/* 15:   */   {
/* 16:49 */     ((HtmlLabel)getDomNodeOrDie()).setAttribute("for", id);
/* 17:   */   }
/* 18:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.html.HTMLLabelElement
 * JD-Core Version:    0.7.0.1
 */