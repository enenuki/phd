/*   1:    */ package org.apache.http.client.protocol;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.List;
/*   5:    */ import org.apache.commons.logging.Log;
/*   6:    */ import org.apache.commons.logging.LogFactory;
/*   7:    */ import org.apache.http.Header;
/*   8:    */ import org.apache.http.HeaderIterator;
/*   9:    */ import org.apache.http.HttpException;
/*  10:    */ import org.apache.http.HttpResponse;
/*  11:    */ import org.apache.http.HttpResponseInterceptor;
/*  12:    */ import org.apache.http.annotation.Immutable;
/*  13:    */ import org.apache.http.client.CookieStore;
/*  14:    */ import org.apache.http.cookie.Cookie;
/*  15:    */ import org.apache.http.cookie.CookieOrigin;
/*  16:    */ import org.apache.http.cookie.CookieSpec;
/*  17:    */ import org.apache.http.cookie.MalformedCookieException;
/*  18:    */ import org.apache.http.protocol.HttpContext;
/*  19:    */ 
/*  20:    */ @Immutable
/*  21:    */ public class ResponseProcessCookies
/*  22:    */   implements HttpResponseInterceptor
/*  23:    */ {
/*  24: 59 */   private final Log log = LogFactory.getLog(getClass());
/*  25:    */   
/*  26:    */   public void process(HttpResponse response, HttpContext context)
/*  27:    */     throws HttpException, IOException
/*  28:    */   {
/*  29: 67 */     if (response == null) {
/*  30: 68 */       throw new IllegalArgumentException("HTTP request may not be null");
/*  31:    */     }
/*  32: 70 */     if (context == null) {
/*  33: 71 */       throw new IllegalArgumentException("HTTP context may not be null");
/*  34:    */     }
/*  35: 75 */     CookieSpec cookieSpec = (CookieSpec)context.getAttribute("http.cookie-spec");
/*  36: 77 */     if (cookieSpec == null)
/*  37:    */     {
/*  38: 78 */       this.log.debug("Cookie spec not specified in HTTP context");
/*  39: 79 */       return;
/*  40:    */     }
/*  41: 82 */     CookieStore cookieStore = (CookieStore)context.getAttribute("http.cookie-store");
/*  42: 84 */     if (cookieStore == null)
/*  43:    */     {
/*  44: 85 */       this.log.debug("Cookie store not specified in HTTP context");
/*  45: 86 */       return;
/*  46:    */     }
/*  47: 89 */     CookieOrigin cookieOrigin = (CookieOrigin)context.getAttribute("http.cookie-origin");
/*  48: 91 */     if (cookieOrigin == null)
/*  49:    */     {
/*  50: 92 */       this.log.debug("Cookie origin not specified in HTTP context");
/*  51: 93 */       return;
/*  52:    */     }
/*  53: 95 */     HeaderIterator it = response.headerIterator("Set-Cookie");
/*  54: 96 */     processCookies(it, cookieSpec, cookieOrigin, cookieStore);
/*  55: 99 */     if (cookieSpec.getVersion() > 0)
/*  56:    */     {
/*  57:102 */       it = response.headerIterator("Set-Cookie2");
/*  58:103 */       processCookies(it, cookieSpec, cookieOrigin, cookieStore);
/*  59:    */     }
/*  60:    */   }
/*  61:    */   
/*  62:    */   private void processCookies(HeaderIterator iterator, CookieSpec cookieSpec, CookieOrigin cookieOrigin, CookieStore cookieStore)
/*  63:    */   {
/*  64:112 */     while (iterator.hasNext())
/*  65:    */     {
/*  66:113 */       Header header = iterator.nextHeader();
/*  67:    */       try
/*  68:    */       {
/*  69:115 */         List<Cookie> cookies = cookieSpec.parse(header, cookieOrigin);
/*  70:116 */         for (Cookie cookie : cookies) {
/*  71:    */           try
/*  72:    */           {
/*  73:118 */             cookieSpec.validate(cookie, cookieOrigin);
/*  74:119 */             cookieStore.addCookie(cookie);
/*  75:121 */             if (this.log.isDebugEnabled()) {
/*  76:122 */               this.log.debug("Cookie accepted: \"" + cookie + "\". ");
/*  77:    */             }
/*  78:    */           }
/*  79:    */           catch (MalformedCookieException ex)
/*  80:    */           {
/*  81:126 */             if (this.log.isWarnEnabled()) {
/*  82:127 */               this.log.warn("Cookie rejected: \"" + cookie + "\". " + ex.getMessage());
/*  83:    */             }
/*  84:    */           }
/*  85:    */         }
/*  86:    */       }
/*  87:    */       catch (MalformedCookieException ex)
/*  88:    */       {
/*  89:133 */         if (this.log.isWarnEnabled()) {
/*  90:134 */           this.log.warn("Invalid cookie header: \"" + header + "\". " + ex.getMessage());
/*  91:    */         }
/*  92:    */       }
/*  93:    */     }
/*  94:    */   }
/*  95:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.client.protocol.ResponseProcessCookies
 * JD-Core Version:    0.7.0.1
 */