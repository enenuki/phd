/*  1:   */ package org.apache.http.impl.cookie;
/*  2:   */ 
/*  3:   */ import org.apache.http.annotation.Immutable;
/*  4:   */ import org.apache.http.cookie.Cookie;
/*  5:   */ import org.apache.http.cookie.CookieAttributeHandler;
/*  6:   */ import org.apache.http.cookie.CookieOrigin;
/*  7:   */ import org.apache.http.cookie.MalformedCookieException;
/*  8:   */ import org.apache.http.cookie.SetCookie;
/*  9:   */ import org.apache.http.cookie.SetCookie2;
/* 10:   */ 
/* 11:   */ @Immutable
/* 12:   */ public class RFC2965DiscardAttributeHandler
/* 13:   */   implements CookieAttributeHandler
/* 14:   */ {
/* 15:   */   public void parse(SetCookie cookie, String commenturl)
/* 16:   */     throws MalformedCookieException
/* 17:   */   {
/* 18:53 */     if ((cookie instanceof SetCookie2))
/* 19:   */     {
/* 20:54 */       SetCookie2 cookie2 = (SetCookie2)cookie;
/* 21:55 */       cookie2.setDiscard(true);
/* 22:   */     }
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void validate(Cookie cookie, CookieOrigin origin)
/* 26:   */     throws MalformedCookieException
/* 27:   */   {}
/* 28:   */   
/* 29:   */   public boolean match(Cookie cookie, CookieOrigin origin)
/* 30:   */   {
/* 31:64 */     return true;
/* 32:   */   }
/* 33:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.cookie.RFC2965DiscardAttributeHandler
 * JD-Core Version:    0.7.0.1
 */