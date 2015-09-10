/*   1:    */ package com.gargoylesoftware.htmlunit.util;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.WebClient;
/*   4:    */ import com.gargoylesoftware.htmlunit.gae.GAEUtils;
/*   5:    */ import java.net.MalformedURLException;
/*   6:    */ import java.net.URL;
/*   7:    */ import java.net.URLStreamHandler;
/*   8:    */ import org.apache.commons.lang.StringUtils;
/*   9:    */ 
/*  10:    */ abstract class URLCreator
/*  11:    */ {
/*  12:    */   abstract URL toUrlUnsafeClassic(String paramString)
/*  13:    */     throws MalformedURLException;
/*  14:    */   
/*  15:    */   protected URL toNormalUrl(String url)
/*  16:    */     throws MalformedURLException
/*  17:    */   {
/*  18: 36 */     URL response = new URL(url);
/*  19: 37 */     if ((response.getProtocol().startsWith("http")) && (StringUtils.isEmpty(response.getHost()))) {
/*  20: 38 */       throw new MalformedURLException("Missing host name in url: " + url);
/*  21:    */     }
/*  22: 40 */     return response;
/*  23:    */   }
/*  24:    */   
/*  25:    */   static URLCreator getCreator()
/*  26:    */   {
/*  27: 48 */     if (!GAEUtils.isGaeMode()) {
/*  28: 49 */       return new URLCreatorStandard();
/*  29:    */     }
/*  30: 51 */     return new URLCreatorGAE();
/*  31:    */   }
/*  32:    */   
/*  33:    */   static class URLCreatorStandard
/*  34:    */     extends URLCreator
/*  35:    */   {
/*  36: 58 */     private static final URLStreamHandler JS_HANDLER = new com.gargoylesoftware.htmlunit.protocol.javascript.Handler();
/*  37: 60 */     private static final URLStreamHandler ABOUT_HANDLER = new com.gargoylesoftware.htmlunit.protocol.about.Handler();
/*  38: 62 */     private static final URLStreamHandler DATA_HANDLER = new com.gargoylesoftware.htmlunit.protocol.data.Handler();
/*  39:    */     
/*  40:    */     URL toUrlUnsafeClassic(String url)
/*  41:    */       throws MalformedURLException
/*  42:    */     {
/*  43: 66 */       if (StringUtils.startsWithIgnoreCase(url, "javascript:")) {
/*  44: 68 */         return new URL(null, url, JS_HANDLER);
/*  45:    */       }
/*  46: 70 */       if (StringUtils.startsWithIgnoreCase(url, "about:"))
/*  47:    */       {
/*  48: 71 */         if ((WebClient.URL_ABOUT_BLANK != null) && (StringUtils.equalsIgnoreCase(WebClient.URL_ABOUT_BLANK.toExternalForm(), url))) {
/*  49: 74 */           return WebClient.URL_ABOUT_BLANK;
/*  50:    */         }
/*  51: 76 */         return new URL(null, url, ABOUT_HANDLER);
/*  52:    */       }
/*  53: 78 */       if (StringUtils.startsWithIgnoreCase(url, "data:")) {
/*  54: 79 */         return new URL(null, url, DATA_HANDLER);
/*  55:    */       }
/*  56: 82 */       return toNormalUrl(url);
/*  57:    */     }
/*  58:    */   }
/*  59:    */   
/*  60:    */   static class URLCreatorGAE
/*  61:    */     extends URLCreator
/*  62:    */   {
/*  63:    */     URL toUrlUnsafeClassic(String url)
/*  64:    */       throws MalformedURLException
/*  65:    */     {
/*  66: 94 */       if (StringUtils.startsWithIgnoreCase(url, "javascript:")) {
/*  67: 96 */         return new URL("http://gaeHack_" + url.replaceFirst(":", "/"));
/*  68:    */       }
/*  69: 98 */       if (StringUtils.startsWithIgnoreCase(url, "about:"))
/*  70:    */       {
/*  71: 99 */         if ((WebClient.URL_ABOUT_BLANK != null) && (StringUtils.equalsIgnoreCase(WebClient.URL_ABOUT_BLANK.toExternalForm(), url))) {
/*  72:102 */           return WebClient.URL_ABOUT_BLANK;
/*  73:    */         }
/*  74:104 */         return new URL("http://gaeHack_" + url.replaceFirst(":", "/"));
/*  75:    */       }
/*  76:106 */       if (StringUtils.startsWithIgnoreCase(url, "data:")) {
/*  77:107 */         return new URL("http://gaeHack_" + url.replaceFirst(":", "/"));
/*  78:    */       }
/*  79:110 */       return toNormalUrl(url);
/*  80:    */     }
/*  81:    */   }
/*  82:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.util.URLCreator
 * JD-Core Version:    0.7.0.1
 */