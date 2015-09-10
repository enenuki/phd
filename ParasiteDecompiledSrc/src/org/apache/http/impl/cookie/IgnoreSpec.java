/*  1:   */ package org.apache.http.impl.cookie;
/*  2:   */ 
/*  3:   */ import java.util.Collections;
/*  4:   */ import java.util.List;
/*  5:   */ import org.apache.http.Header;
/*  6:   */ import org.apache.http.annotation.NotThreadSafe;
/*  7:   */ import org.apache.http.cookie.Cookie;
/*  8:   */ import org.apache.http.cookie.CookieOrigin;
/*  9:   */ import org.apache.http.cookie.MalformedCookieException;
/* 10:   */ 
/* 11:   */ @NotThreadSafe
/* 12:   */ public class IgnoreSpec
/* 13:   */   extends CookieSpecBase
/* 14:   */ {
/* 15:   */   public int getVersion()
/* 16:   */   {
/* 17:48 */     return 0;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public List<Cookie> parse(Header header, CookieOrigin origin)
/* 21:   */     throws MalformedCookieException
/* 22:   */   {
/* 23:53 */     return Collections.emptyList();
/* 24:   */   }
/* 25:   */   
/* 26:   */   public List<Header> formatCookies(List<Cookie> cookies)
/* 27:   */   {
/* 28:57 */     return Collections.emptyList();
/* 29:   */   }
/* 30:   */   
/* 31:   */   public Header getVersionHeader()
/* 32:   */   {
/* 33:61 */     return null;
/* 34:   */   }
/* 35:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.cookie.IgnoreSpec
 * JD-Core Version:    0.7.0.1
 */