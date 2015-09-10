/*  1:   */ package com.gargoylesoftware.htmlunit.protocol.about;
/*  2:   */ 
/*  3:   */ import java.net.URL;
/*  4:   */ import java.net.URLConnection;
/*  5:   */ import java.net.URLStreamHandler;
/*  6:   */ 
/*  7:   */ public class Handler
/*  8:   */   extends URLStreamHandler
/*  9:   */ {
/* 10:   */   protected URLConnection openConnection(URL url)
/* 11:   */   {
/* 12:36 */     return new AboutURLConnection(url);
/* 13:   */   }
/* 14:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.protocol.about.Handler
 * JD-Core Version:    0.7.0.1
 */