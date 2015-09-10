/*  1:   */ package com.gargoylesoftware.htmlunit.protocol.data;
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
/* 12:35 */     return new DataURLConnection(url);
/* 13:   */   }
/* 14:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.protocol.data.Handler
 * JD-Core Version:    0.7.0.1
 */