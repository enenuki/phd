/*  1:   */ package org.apache.http.impl.cookie;
/*  2:   */ 
/*  3:   */ import org.apache.http.annotation.Immutable;
/*  4:   */ import org.apache.http.cookie.Cookie;
/*  5:   */ import org.apache.http.cookie.CookieOrigin;
/*  6:   */ import org.apache.http.cookie.MalformedCookieException;
/*  7:   */ import org.apache.http.cookie.SetCookie;
/*  8:   */ 
/*  9:   */ @Immutable
/* 10:   */ public class BasicSecureHandler
/* 11:   */   extends AbstractCookieAttributeHandler
/* 12:   */ {
/* 13:   */   public void parse(SetCookie cookie, String value)
/* 14:   */     throws MalformedCookieException
/* 15:   */   {
/* 16:49 */     if (cookie == null) {
/* 17:50 */       throw new IllegalArgumentException("Cookie may not be null");
/* 18:   */     }
/* 19:52 */     cookie.setSecure(true);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public boolean match(Cookie cookie, CookieOrigin origin)
/* 23:   */   {
/* 24:57 */     if (cookie == null) {
/* 25:58 */       throw new IllegalArgumentException("Cookie may not be null");
/* 26:   */     }
/* 27:60 */     if (origin == null) {
/* 28:61 */       throw new IllegalArgumentException("Cookie origin may not be null");
/* 29:   */     }
/* 30:63 */     return (!cookie.isSecure()) || (origin.isSecure());
/* 31:   */   }
/* 32:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.cookie.BasicSecureHandler
 * JD-Core Version:    0.7.0.1
 */