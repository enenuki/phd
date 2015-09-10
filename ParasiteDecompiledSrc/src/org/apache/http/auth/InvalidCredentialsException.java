/*  1:   */ package org.apache.http.auth;
/*  2:   */ 
/*  3:   */ import org.apache.http.annotation.Immutable;
/*  4:   */ 
/*  5:   */ @Immutable
/*  6:   */ public class InvalidCredentialsException
/*  7:   */   extends AuthenticationException
/*  8:   */ {
/*  9:   */   private static final long serialVersionUID = -4834003835215460648L;
/* 10:   */   
/* 11:   */   public InvalidCredentialsException() {}
/* 12:   */   
/* 13:   */   public InvalidCredentialsException(String message)
/* 14:   */   {
/* 15:56 */     super(message);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public InvalidCredentialsException(String message, Throwable cause)
/* 19:   */   {
/* 20:67 */     super(message, cause);
/* 21:   */   }
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.auth.InvalidCredentialsException
 * JD-Core Version:    0.7.0.1
 */