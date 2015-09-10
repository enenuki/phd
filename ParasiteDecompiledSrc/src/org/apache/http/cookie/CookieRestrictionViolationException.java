/*  1:   */ package org.apache.http.cookie;
/*  2:   */ 
/*  3:   */ import org.apache.http.annotation.Immutable;
/*  4:   */ 
/*  5:   */ @Immutable
/*  6:   */ public class CookieRestrictionViolationException
/*  7:   */   extends MalformedCookieException
/*  8:   */ {
/*  9:   */   private static final long serialVersionUID = 7371235577078589013L;
/* 10:   */   
/* 11:   */   public CookieRestrictionViolationException() {}
/* 12:   */   
/* 13:   */   public CookieRestrictionViolationException(String message)
/* 14:   */   {
/* 15:58 */     super(message);
/* 16:   */   }
/* 17:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.cookie.CookieRestrictionViolationException
 * JD-Core Version:    0.7.0.1
 */