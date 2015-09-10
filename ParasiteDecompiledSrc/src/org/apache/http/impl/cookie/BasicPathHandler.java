/*  1:   */ package org.apache.http.impl.cookie;
/*  2:   */ 
/*  3:   */ import org.apache.http.annotation.Immutable;
/*  4:   */ import org.apache.http.cookie.Cookie;
/*  5:   */ import org.apache.http.cookie.CookieAttributeHandler;
/*  6:   */ import org.apache.http.cookie.CookieOrigin;
/*  7:   */ import org.apache.http.cookie.CookieRestrictionViolationException;
/*  8:   */ import org.apache.http.cookie.MalformedCookieException;
/*  9:   */ import org.apache.http.cookie.SetCookie;
/* 10:   */ 
/* 11:   */ @Immutable
/* 12:   */ public class BasicPathHandler
/* 13:   */   implements CookieAttributeHandler
/* 14:   */ {
/* 15:   */   public void parse(SetCookie cookie, String value)
/* 16:   */     throws MalformedCookieException
/* 17:   */   {
/* 18:51 */     if (cookie == null) {
/* 19:52 */       throw new IllegalArgumentException("Cookie may not be null");
/* 20:   */     }
/* 21:54 */     if ((value == null) || (value.trim().length() == 0)) {
/* 22:55 */       value = "/";
/* 23:   */     }
/* 24:57 */     cookie.setPath(value);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void validate(Cookie cookie, CookieOrigin origin)
/* 28:   */     throws MalformedCookieException
/* 29:   */   {
/* 30:62 */     if (!match(cookie, origin)) {
/* 31:63 */       throw new CookieRestrictionViolationException("Illegal path attribute \"" + cookie.getPath() + "\". Path of origin: \"" + origin.getPath() + "\"");
/* 32:   */     }
/* 33:   */   }
/* 34:   */   
/* 35:   */   public boolean match(Cookie cookie, CookieOrigin origin)
/* 36:   */   {
/* 37:70 */     if (cookie == null) {
/* 38:71 */       throw new IllegalArgumentException("Cookie may not be null");
/* 39:   */     }
/* 40:73 */     if (origin == null) {
/* 41:74 */       throw new IllegalArgumentException("Cookie origin may not be null");
/* 42:   */     }
/* 43:76 */     String targetpath = origin.getPath();
/* 44:77 */     String topmostPath = cookie.getPath();
/* 45:78 */     if (topmostPath == null) {
/* 46:79 */       topmostPath = "/";
/* 47:   */     }
/* 48:81 */     if ((topmostPath.length() > 1) && (topmostPath.endsWith("/"))) {
/* 49:82 */       topmostPath = topmostPath.substring(0, topmostPath.length() - 1);
/* 50:   */     }
/* 51:84 */     boolean match = targetpath.startsWith(topmostPath);
/* 52:87 */     if ((match) && (targetpath.length() != topmostPath.length()) && 
/* 53:88 */       (!topmostPath.endsWith("/"))) {
/* 54:89 */       match = targetpath.charAt(topmostPath.length()) == '/';
/* 55:   */     }
/* 56:92 */     return match;
/* 57:   */   }
/* 58:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.cookie.BasicPathHandler
 * JD-Core Version:    0.7.0.1
 */