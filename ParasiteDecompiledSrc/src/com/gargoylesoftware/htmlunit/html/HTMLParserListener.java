/*  1:   */ package com.gargoylesoftware.htmlunit.html;
/*  2:   */ 
/*  3:   */ import java.net.URL;
/*  4:   */ 
/*  5:   */ public abstract interface HTMLParserListener
/*  6:   */ {
/*  7:39 */   public static final HTMLParserListener LOG_REPORTER = new SimpleHTMLParserListener();
/*  8:   */   
/*  9:   */   public abstract void error(String paramString1, URL paramURL, int paramInt1, int paramInt2, String paramString2);
/* 10:   */   
/* 11:   */   public abstract void warning(String paramString1, URL paramURL, int paramInt1, int paramInt2, String paramString2);
/* 12:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.HTMLParserListener
 * JD-Core Version:    0.7.0.1
 */