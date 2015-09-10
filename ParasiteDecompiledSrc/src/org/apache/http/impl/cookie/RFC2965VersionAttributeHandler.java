/*  1:   */ package org.apache.http.impl.cookie;
/*  2:   */ 
/*  3:   */ import org.apache.http.annotation.Immutable;
/*  4:   */ import org.apache.http.cookie.ClientCookie;
/*  5:   */ import org.apache.http.cookie.Cookie;
/*  6:   */ import org.apache.http.cookie.CookieAttributeHandler;
/*  7:   */ import org.apache.http.cookie.CookieOrigin;
/*  8:   */ import org.apache.http.cookie.CookieRestrictionViolationException;
/*  9:   */ import org.apache.http.cookie.MalformedCookieException;
/* 10:   */ import org.apache.http.cookie.SetCookie;
/* 11:   */ import org.apache.http.cookie.SetCookie2;
/* 12:   */ 
/* 13:   */ @Immutable
/* 14:   */ public class RFC2965VersionAttributeHandler
/* 15:   */   implements CookieAttributeHandler
/* 16:   */ {
/* 17:   */   public void parse(SetCookie cookie, String value)
/* 18:   */     throws MalformedCookieException
/* 19:   */   {
/* 20:58 */     if (cookie == null) {
/* 21:59 */       throw new IllegalArgumentException("Cookie may not be null");
/* 22:   */     }
/* 23:61 */     if (value == null) {
/* 24:62 */       throw new MalformedCookieException("Missing value for version attribute");
/* 25:   */     }
/* 26:65 */     int version = -1;
/* 27:   */     try
/* 28:   */     {
/* 29:67 */       version = Integer.parseInt(value);
/* 30:   */     }
/* 31:   */     catch (NumberFormatException e)
/* 32:   */     {
/* 33:69 */       version = -1;
/* 34:   */     }
/* 35:71 */     if (version < 0) {
/* 36:72 */       throw new MalformedCookieException("Invalid cookie version.");
/* 37:   */     }
/* 38:74 */     cookie.setVersion(version);
/* 39:   */   }
/* 40:   */   
/* 41:   */   public void validate(Cookie cookie, CookieOrigin origin)
/* 42:   */     throws MalformedCookieException
/* 43:   */   {
/* 44:82 */     if (cookie == null) {
/* 45:83 */       throw new IllegalArgumentException("Cookie may not be null");
/* 46:   */     }
/* 47:85 */     if (((cookie instanceof SetCookie2)) && 
/* 48:86 */       ((cookie instanceof ClientCookie)) && (!((ClientCookie)cookie).containsAttribute("version"))) {
/* 49:88 */       throw new CookieRestrictionViolationException("Violates RFC 2965. Version attribute is required.");
/* 50:   */     }
/* 51:   */   }
/* 52:   */   
/* 53:   */   public boolean match(Cookie cookie, CookieOrigin origin)
/* 54:   */   {
/* 55:95 */     return true;
/* 56:   */   }
/* 57:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.cookie.RFC2965VersionAttributeHandler
 * JD-Core Version:    0.7.0.1
 */