/*  1:   */ package org.apache.http.impl.cookie;
/*  2:   */ 
/*  3:   */ import org.apache.http.annotation.Immutable;
/*  4:   */ import org.apache.http.cookie.Cookie;
/*  5:   */ import org.apache.http.cookie.CookieOrigin;
/*  6:   */ import org.apache.http.cookie.CookieRestrictionViolationException;
/*  7:   */ import org.apache.http.cookie.MalformedCookieException;
/*  8:   */ import org.apache.http.cookie.SetCookie;
/*  9:   */ 
/* 10:   */ @Immutable
/* 11:   */ public class RFC2109VersionHandler
/* 12:   */   extends AbstractCookieAttributeHandler
/* 13:   */ {
/* 14:   */   public void parse(SetCookie cookie, String value)
/* 15:   */     throws MalformedCookieException
/* 16:   */   {
/* 17:50 */     if (cookie == null) {
/* 18:51 */       throw new IllegalArgumentException("Cookie may not be null");
/* 19:   */     }
/* 20:53 */     if (value == null) {
/* 21:54 */       throw new MalformedCookieException("Missing value for version attribute");
/* 22:   */     }
/* 23:56 */     if (value.trim().length() == 0) {
/* 24:57 */       throw new MalformedCookieException("Blank value for version attribute");
/* 25:   */     }
/* 26:   */     try
/* 27:   */     {
/* 28:60 */       cookie.setVersion(Integer.parseInt(value));
/* 29:   */     }
/* 30:   */     catch (NumberFormatException e)
/* 31:   */     {
/* 32:62 */       throw new MalformedCookieException("Invalid version: " + e.getMessage());
/* 33:   */     }
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void validate(Cookie cookie, CookieOrigin origin)
/* 37:   */     throws MalformedCookieException
/* 38:   */   {
/* 39:70 */     if (cookie == null) {
/* 40:71 */       throw new IllegalArgumentException("Cookie may not be null");
/* 41:   */     }
/* 42:73 */     if (cookie.getVersion() < 0) {
/* 43:74 */       throw new CookieRestrictionViolationException("Cookie version may not be negative");
/* 44:   */     }
/* 45:   */   }
/* 46:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.cookie.RFC2109VersionHandler
 * JD-Core Version:    0.7.0.1
 */