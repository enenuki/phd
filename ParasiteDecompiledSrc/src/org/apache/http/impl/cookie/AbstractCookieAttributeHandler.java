/*  1:   */ package org.apache.http.impl.cookie;
/*  2:   */ 
/*  3:   */ import org.apache.http.annotation.Immutable;
/*  4:   */ import org.apache.http.cookie.Cookie;
/*  5:   */ import org.apache.http.cookie.CookieAttributeHandler;
/*  6:   */ import org.apache.http.cookie.CookieOrigin;
/*  7:   */ import org.apache.http.cookie.MalformedCookieException;
/*  8:   */ 
/*  9:   */ @Immutable
/* 10:   */ public abstract class AbstractCookieAttributeHandler
/* 11:   */   implements CookieAttributeHandler
/* 12:   */ {
/* 13:   */   public void validate(Cookie cookie, CookieOrigin origin)
/* 14:   */     throws MalformedCookieException
/* 15:   */   {}
/* 16:   */   
/* 17:   */   public boolean match(Cookie cookie, CookieOrigin origin)
/* 18:   */   {
/* 19:50 */     return true;
/* 20:   */   }
/* 21:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.cookie.AbstractCookieAttributeHandler
 * JD-Core Version:    0.7.0.1
 */