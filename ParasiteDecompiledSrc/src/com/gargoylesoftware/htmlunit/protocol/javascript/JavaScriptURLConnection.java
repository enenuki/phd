/*  1:   */ package com.gargoylesoftware.htmlunit.protocol.javascript;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.TextUtil;
/*  4:   */ import java.io.InputStream;
/*  5:   */ import java.net.URL;
/*  6:   */ import java.net.URLConnection;
/*  7:   */ 
/*  8:   */ public class JavaScriptURLConnection
/*  9:   */   extends URLConnection
/* 10:   */ {
/* 11:   */   public static final String JAVASCRIPT_PREFIX = "javascript:";
/* 12:   */   private final String content_;
/* 13:   */   
/* 14:   */   public JavaScriptURLConnection(URL newUrl)
/* 15:   */   {
/* 16:42 */     super(newUrl);
/* 17:43 */     this.content_ = newUrl.toExternalForm().substring("javascript:".length());
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void connect() {}
/* 21:   */   
/* 22:   */   public InputStream getInputStream()
/* 23:   */   {
/* 24:60 */     return TextUtil.toInputStream(this.content_);
/* 25:   */   }
/* 26:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.protocol.javascript.JavaScriptURLConnection
 * JD-Core Version:    0.7.0.1
 */