/*   1:    */ package org.apache.http.impl.cookie;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import java.util.Locale;
/*   6:    */ import org.apache.http.HeaderElement;
/*   7:    */ import org.apache.http.NameValuePair;
/*   8:    */ import org.apache.http.annotation.NotThreadSafe;
/*   9:    */ import org.apache.http.cookie.Cookie;
/*  10:    */ import org.apache.http.cookie.CookieAttributeHandler;
/*  11:    */ import org.apache.http.cookie.CookieOrigin;
/*  12:    */ import org.apache.http.cookie.MalformedCookieException;
/*  13:    */ 
/*  14:    */ @NotThreadSafe
/*  15:    */ public abstract class CookieSpecBase
/*  16:    */   extends AbstractCookieSpec
/*  17:    */ {
/*  18:    */   protected static String getDefaultPath(CookieOrigin origin)
/*  19:    */   {
/*  20: 53 */     String defaultPath = origin.getPath();
/*  21: 54 */     int lastSlashIndex = defaultPath.lastIndexOf('/');
/*  22: 55 */     if (lastSlashIndex >= 0)
/*  23:    */     {
/*  24: 56 */       if (lastSlashIndex == 0) {
/*  25: 58 */         lastSlashIndex = 1;
/*  26:    */       }
/*  27: 60 */       defaultPath = defaultPath.substring(0, lastSlashIndex);
/*  28:    */     }
/*  29: 62 */     return defaultPath;
/*  30:    */   }
/*  31:    */   
/*  32:    */   protected static String getDefaultDomain(CookieOrigin origin)
/*  33:    */   {
/*  34: 66 */     return origin.getHost();
/*  35:    */   }
/*  36:    */   
/*  37:    */   protected List<Cookie> parse(HeaderElement[] elems, CookieOrigin origin)
/*  38:    */     throws MalformedCookieException
/*  39:    */   {
/*  40: 71 */     List<Cookie> cookies = new ArrayList(elems.length);
/*  41: 72 */     for (HeaderElement headerelement : elems)
/*  42:    */     {
/*  43: 73 */       String name = headerelement.getName();
/*  44: 74 */       String value = headerelement.getValue();
/*  45: 75 */       if ((name == null) || (name.length() == 0)) {
/*  46: 76 */         throw new MalformedCookieException("Cookie name may not be empty");
/*  47:    */       }
/*  48: 79 */       BasicClientCookie cookie = new BasicClientCookie(name, value);
/*  49: 80 */       cookie.setPath(getDefaultPath(origin));
/*  50: 81 */       cookie.setDomain(getDefaultDomain(origin));
/*  51:    */       
/*  52:    */ 
/*  53: 84 */       NameValuePair[] attribs = headerelement.getParameters();
/*  54: 85 */       for (int j = attribs.length - 1; j >= 0; j--)
/*  55:    */       {
/*  56: 86 */         NameValuePair attrib = attribs[j];
/*  57: 87 */         String s = attrib.getName().toLowerCase(Locale.ENGLISH);
/*  58:    */         
/*  59: 89 */         cookie.setAttribute(s, attrib.getValue());
/*  60:    */         
/*  61: 91 */         CookieAttributeHandler handler = findAttribHandler(s);
/*  62: 92 */         if (handler != null) {
/*  63: 93 */           handler.parse(cookie, attrib.getValue());
/*  64:    */         }
/*  65:    */       }
/*  66: 96 */       cookies.add(cookie);
/*  67:    */     }
/*  68: 98 */     return cookies;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void validate(Cookie cookie, CookieOrigin origin)
/*  72:    */     throws MalformedCookieException
/*  73:    */   {
/*  74:103 */     if (cookie == null) {
/*  75:104 */       throw new IllegalArgumentException("Cookie may not be null");
/*  76:    */     }
/*  77:106 */     if (origin == null) {
/*  78:107 */       throw new IllegalArgumentException("Cookie origin may not be null");
/*  79:    */     }
/*  80:109 */     for (CookieAttributeHandler handler : getAttribHandlers()) {
/*  81:110 */       handler.validate(cookie, origin);
/*  82:    */     }
/*  83:    */   }
/*  84:    */   
/*  85:    */   public boolean match(Cookie cookie, CookieOrigin origin)
/*  86:    */   {
/*  87:115 */     if (cookie == null) {
/*  88:116 */       throw new IllegalArgumentException("Cookie may not be null");
/*  89:    */     }
/*  90:118 */     if (origin == null) {
/*  91:119 */       throw new IllegalArgumentException("Cookie origin may not be null");
/*  92:    */     }
/*  93:121 */     for (CookieAttributeHandler handler : getAttribHandlers()) {
/*  94:122 */       if (!handler.match(cookie, origin)) {
/*  95:123 */         return false;
/*  96:    */       }
/*  97:    */     }
/*  98:126 */     return true;
/*  99:    */   }
/* 100:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.cookie.CookieSpecBase
 * JD-Core Version:    0.7.0.1
 */