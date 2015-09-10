/*  1:   */ package org.apache.http.cookie;
/*  2:   */ 
/*  3:   */ import org.apache.http.ProtocolException;
/*  4:   */ import org.apache.http.annotation.Immutable;
/*  5:   */ 
/*  6:   */ @Immutable
/*  7:   */ public class MalformedCookieException
/*  8:   */   extends ProtocolException
/*  9:   */ {
/* 10:   */   private static final long serialVersionUID = -6695462944287282185L;
/* 11:   */   
/* 12:   */   public MalformedCookieException() {}
/* 13:   */   
/* 14:   */   public MalformedCookieException(String message)
/* 15:   */   {
/* 16:59 */     super(message);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public MalformedCookieException(String message, Throwable cause)
/* 20:   */   {
/* 21:70 */     super(message, cause);
/* 22:   */   }
/* 23:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.cookie.MalformedCookieException
 * JD-Core Version:    0.7.0.1
 */