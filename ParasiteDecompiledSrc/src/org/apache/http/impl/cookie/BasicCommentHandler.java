/*  1:   */ package org.apache.http.impl.cookie;
/*  2:   */ 
/*  3:   */ import org.apache.http.annotation.Immutable;
/*  4:   */ import org.apache.http.cookie.MalformedCookieException;
/*  5:   */ import org.apache.http.cookie.SetCookie;
/*  6:   */ 
/*  7:   */ @Immutable
/*  8:   */ public class BasicCommentHandler
/*  9:   */   extends AbstractCookieAttributeHandler
/* 10:   */ {
/* 11:   */   public void parse(SetCookie cookie, String value)
/* 12:   */     throws MalformedCookieException
/* 13:   */   {
/* 14:47 */     if (cookie == null) {
/* 15:48 */       throw new IllegalArgumentException("Cookie may not be null");
/* 16:   */     }
/* 17:50 */     cookie.setComment(value);
/* 18:   */   }
/* 19:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.cookie.BasicCommentHandler
 * JD-Core Version:    0.7.0.1
 */