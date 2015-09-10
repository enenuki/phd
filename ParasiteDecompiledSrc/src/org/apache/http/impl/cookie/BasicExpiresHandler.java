/*  1:   */ package org.apache.http.impl.cookie;
/*  2:   */ 
/*  3:   */ import org.apache.http.annotation.Immutable;
/*  4:   */ import org.apache.http.cookie.MalformedCookieException;
/*  5:   */ import org.apache.http.cookie.SetCookie;
/*  6:   */ 
/*  7:   */ @Immutable
/*  8:   */ public class BasicExpiresHandler
/*  9:   */   extends AbstractCookieAttributeHandler
/* 10:   */ {
/* 11:   */   private final String[] datepatterns;
/* 12:   */   
/* 13:   */   public BasicExpiresHandler(String[] datepatterns)
/* 14:   */   {
/* 15:46 */     if (datepatterns == null) {
/* 16:47 */       throw new IllegalArgumentException("Array of date patterns may not be null");
/* 17:   */     }
/* 18:49 */     this.datepatterns = datepatterns;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void parse(SetCookie cookie, String value)
/* 22:   */     throws MalformedCookieException
/* 23:   */   {
/* 24:54 */     if (cookie == null) {
/* 25:55 */       throw new IllegalArgumentException("Cookie may not be null");
/* 26:   */     }
/* 27:57 */     if (value == null) {
/* 28:58 */       throw new MalformedCookieException("Missing value for expires attribute");
/* 29:   */     }
/* 30:   */     try
/* 31:   */     {
/* 32:61 */       cookie.setExpiryDate(DateUtils.parseDate(value, this.datepatterns));
/* 33:   */     }
/* 34:   */     catch (DateParseException dpe)
/* 35:   */     {
/* 36:63 */       throw new MalformedCookieException("Unable to parse expires attribute: " + value);
/* 37:   */     }
/* 38:   */   }
/* 39:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.cookie.BasicExpiresHandler
 * JD-Core Version:    0.7.0.1
 */