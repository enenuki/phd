/*  1:   */ package com.gargoylesoftware.htmlunit.html;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.WebResponse;
/*  4:   */ import com.gargoylesoftware.htmlunit.WebWindow;
/*  5:   */ import java.net.URL;
/*  6:   */ 
/*  7:   */ public class XHtmlPage
/*  8:   */   extends HtmlPage
/*  9:   */ {
/* 10:   */   public XHtmlPage(URL originatingUrl, WebResponse webResponse, WebWindow webWindow)
/* 11:   */   {
/* 12:39 */     super(originatingUrl, webResponse, webWindow);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public boolean hasCaseSensitiveTagNames()
/* 16:   */   {
/* 17:47 */     return true;
/* 18:   */   }
/* 19:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.XHtmlPage
 * JD-Core Version:    0.7.0.1
 */