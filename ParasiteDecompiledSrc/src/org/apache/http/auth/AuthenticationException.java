/*  1:   */ package org.apache.http.auth;
/*  2:   */ 
/*  3:   */ import org.apache.http.ProtocolException;
/*  4:   */ import org.apache.http.annotation.Immutable;
/*  5:   */ 
/*  6:   */ @Immutable
/*  7:   */ public class AuthenticationException
/*  8:   */   extends ProtocolException
/*  9:   */ {
/* 10:   */   private static final long serialVersionUID = -6794031905674764776L;
/* 11:   */   
/* 12:   */   public AuthenticationException() {}
/* 13:   */   
/* 14:   */   public AuthenticationException(String message)
/* 15:   */   {
/* 16:57 */     super(message);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public AuthenticationException(String message, Throwable cause)
/* 20:   */   {
/* 21:68 */     super(message, cause);
/* 22:   */   }
/* 23:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.auth.AuthenticationException
 * JD-Core Version:    0.7.0.1
 */