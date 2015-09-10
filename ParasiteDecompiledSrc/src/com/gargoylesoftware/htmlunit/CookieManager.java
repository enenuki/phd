/*   1:    */ package com.gargoylesoftware.htmlunit;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.net.URL;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.Collections;
/*   7:    */ import java.util.Date;
/*   8:    */ import java.util.Iterator;
/*   9:    */ import java.util.LinkedHashSet;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Set;
/*  12:    */ import org.apache.commons.collections.set.ListOrderedSet;
/*  13:    */ import org.apache.commons.lang.StringUtils;
/*  14:    */ import org.apache.http.client.CookieStore;
/*  15:    */ import org.apache.http.cookie.CookieOrigin;
/*  16:    */ import org.apache.http.cookie.CookieSpec;
/*  17:    */ import org.apache.http.cookie.CookieSpecRegistry;
/*  18:    */ import org.apache.http.impl.client.DefaultHttpClient;
/*  19:    */ 
/*  20:    */ public class CookieManager
/*  21:    */   implements Serializable
/*  22:    */ {
/*  23:    */   public static final String HTMLUNIT_COOKIE_POLICY = "compatibility";
/*  24:    */   private boolean cookiesEnabled_;
/*  25: 59 */   private final Set<com.gargoylesoftware.htmlunit.util.Cookie> cookies_ = new ListOrderedSet();
/*  26: 63 */   private final transient CookieSpecRegistry registry_ = new DefaultHttpClient().getCookieSpecs();
/*  27:    */   
/*  28:    */   public CookieManager()
/*  29:    */   {
/*  30: 69 */     this.cookiesEnabled_ = true;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public synchronized void setCookiesEnabled(boolean enabled)
/*  34:    */   {
/*  35: 77 */     this.cookiesEnabled_ = enabled;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public synchronized boolean isCookiesEnabled()
/*  39:    */   {
/*  40: 85 */     return this.cookiesEnabled_;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public synchronized Set<com.gargoylesoftware.htmlunit.util.Cookie> getCookies()
/*  44:    */   {
/*  45: 93 */     return Collections.unmodifiableSet(this.cookies_);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public synchronized Set<com.gargoylesoftware.htmlunit.util.Cookie> getCookies(URL url)
/*  49:    */   {
/*  50:102 */     String host = url.getHost();
/*  51:103 */     String path = url.getPath();
/*  52:104 */     String protocol = url.getProtocol();
/*  53:105 */     boolean secure = "https".equals(protocol);
/*  54:109 */     if (host.length() == 0) {
/*  55:110 */       return Collections.emptySet();
/*  56:    */     }
/*  57:113 */     int port = getPort(url);
/*  58:    */     
/*  59:    */ 
/*  60:116 */     Date now = new Date();
/*  61:117 */     for (Iterator<com.gargoylesoftware.htmlunit.util.Cookie> iter = this.cookies_.iterator(); iter.hasNext();)
/*  62:    */     {
/*  63:118 */       com.gargoylesoftware.htmlunit.util.Cookie cookie = (com.gargoylesoftware.htmlunit.util.Cookie)iter.next();
/*  64:119 */       if ((cookie.getExpires() != null) && (now.after(cookie.getExpires()))) {
/*  65:120 */         iter.remove();
/*  66:    */       }
/*  67:    */     }
/*  68:124 */     CookieSpec spec = this.registry_.getCookieSpec("compatibility");
/*  69:125 */     org.apache.http.cookie.Cookie[] all = com.gargoylesoftware.htmlunit.util.Cookie.toHttpClient(this.cookies_);
/*  70:126 */     CookieOrigin cookieOrigin = new CookieOrigin(host, port, path, secure);
/*  71:127 */     List<org.apache.http.cookie.Cookie> matches = new ArrayList();
/*  72:128 */     for (org.apache.http.cookie.Cookie cookie : all) {
/*  73:129 */       if (spec.match(cookie, cookieOrigin)) {
/*  74:130 */         matches.add(cookie);
/*  75:    */       }
/*  76:    */     }
/*  77:134 */     Set<com.gargoylesoftware.htmlunit.util.Cookie> cookies = new LinkedHashSet();
/*  78:135 */     cookies.addAll(com.gargoylesoftware.htmlunit.util.Cookie.fromHttpClient(matches));
/*  79:136 */     return Collections.unmodifiableSet(cookies);
/*  80:    */   }
/*  81:    */   
/*  82:    */   protected int getPort(URL url)
/*  83:    */   {
/*  84:148 */     if (url.getPort() != -1) {
/*  85:149 */       return url.getPort();
/*  86:    */     }
/*  87:151 */     return url.getDefaultPort();
/*  88:    */   }
/*  89:    */   
/*  90:    */   public synchronized com.gargoylesoftware.htmlunit.util.Cookie getCookie(String name)
/*  91:    */   {
/*  92:160 */     for (com.gargoylesoftware.htmlunit.util.Cookie cookie : this.cookies_) {
/*  93:161 */       if (StringUtils.equals(cookie.getName(), name)) {
/*  94:162 */         return cookie;
/*  95:    */       }
/*  96:    */     }
/*  97:165 */     return null;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public synchronized void addCookie(com.gargoylesoftware.htmlunit.util.Cookie cookie)
/* 101:    */   {
/* 102:173 */     this.cookies_.remove(cookie);
/* 103:176 */     if ((cookie.getExpires() == null) || (cookie.getExpires().after(new Date()))) {
/* 104:177 */       this.cookies_.add(cookie);
/* 105:    */     }
/* 106:    */   }
/* 107:    */   
/* 108:    */   public synchronized void removeCookie(com.gargoylesoftware.htmlunit.util.Cookie cookie)
/* 109:    */   {
/* 110:186 */     this.cookies_.remove(cookie);
/* 111:    */   }
/* 112:    */   
/* 113:    */   public synchronized void clearCookies()
/* 114:    */   {
/* 115:193 */     this.cookies_.clear();
/* 116:    */   }
/* 117:    */   
/* 118:    */   protected synchronized void updateState(CookieStore state)
/* 119:    */   {
/* 120:202 */     if (!this.cookiesEnabled_) {
/* 121:203 */       return;
/* 122:    */     }
/* 123:205 */     state.clear();
/* 124:206 */     for (com.gargoylesoftware.htmlunit.util.Cookie cookie : this.cookies_) {
/* 125:207 */       state.addCookie(cookie.toHttpClient());
/* 126:    */     }
/* 127:    */   }
/* 128:    */   
/* 129:    */   protected synchronized void updateFromState(CookieStore state)
/* 130:    */   {
/* 131:217 */     if (!this.cookiesEnabled_) {
/* 132:218 */       return;
/* 133:    */     }
/* 134:220 */     this.cookies_.clear();
/* 135:221 */     this.cookies_.addAll(com.gargoylesoftware.htmlunit.util.Cookie.fromHttpClient(state.getCookies()));
/* 136:    */   }
/* 137:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.CookieManager
 * JD-Core Version:    0.7.0.1
 */