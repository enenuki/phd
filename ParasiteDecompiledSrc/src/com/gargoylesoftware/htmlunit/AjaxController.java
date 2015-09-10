/*  1:   */ package com.gargoylesoftware.htmlunit;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.html.HtmlPage;
/*  4:   */ import java.io.Serializable;
/*  5:   */ 
/*  6:   */ public class AjaxController
/*  7:   */   implements Serializable
/*  8:   */ {
/*  9:   */   public boolean processSynchron(HtmlPage page, WebRequest request, boolean async)
/* 10:   */   {
/* 11:39 */     return !async;
/* 12:   */   }
/* 13:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.AjaxController
 * JD-Core Version:    0.7.0.1
 */