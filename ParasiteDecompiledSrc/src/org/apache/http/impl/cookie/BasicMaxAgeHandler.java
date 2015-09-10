/*  1:   */ package org.apache.http.impl.cookie;
/*  2:   */ 
/*  3:   */ import java.util.Date;
/*  4:   */ import org.apache.http.annotation.Immutable;
/*  5:   */ import org.apache.http.cookie.MalformedCookieException;
/*  6:   */ import org.apache.http.cookie.SetCookie;
/*  7:   */ 
/*  8:   */ @Immutable
/*  9:   */ public class BasicMaxAgeHandler
/* 10:   */   extends AbstractCookieAttributeHandler
/* 11:   */ {
/* 12:   */   public void parse(SetCookie cookie, String value)
/* 13:   */     throws MalformedCookieException
/* 14:   */   {
/* 15:49 */     if (cookie == null) {
/* 16:50 */       throw new IllegalArgumentException("Cookie may not be null");
/* 17:   */     }
/* 18:52 */     if (value == null) {
/* 19:53 */       throw new MalformedCookieException("Missing value for max-age attribute");
/* 20:   */     }
/* 21:   */     int age;
/* 22:   */     try
/* 23:   */     {
/* 24:57 */       age = Integer.parseInt(value);
/* 25:   */     }
/* 26:   */     catch (NumberFormatException e)
/* 27:   */     {
/* 28:59 */       throw new MalformedCookieException("Invalid max-age attribute: " + value);
/* 29:   */     }
/* 30:62 */     if (age < 0) {
/* 31:63 */       throw new MalformedCookieException("Negative max-age attribute: " + value);
/* 32:   */     }
/* 33:66 */     cookie.setExpiryDate(new Date(System.currentTimeMillis() + age * 1000L));
/* 34:   */   }
/* 35:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.cookie.BasicMaxAgeHandler
 * JD-Core Version:    0.7.0.1
 */