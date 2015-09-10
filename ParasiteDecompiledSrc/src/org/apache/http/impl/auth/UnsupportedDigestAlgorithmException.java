/*  1:   */ package org.apache.http.impl.auth;
/*  2:   */ 
/*  3:   */ import org.apache.http.annotation.Immutable;
/*  4:   */ 
/*  5:   */ @Immutable
/*  6:   */ public class UnsupportedDigestAlgorithmException
/*  7:   */   extends RuntimeException
/*  8:   */ {
/*  9:   */   private static final long serialVersionUID = 319558534317118022L;
/* 10:   */   
/* 11:   */   public UnsupportedDigestAlgorithmException() {}
/* 12:   */   
/* 13:   */   public UnsupportedDigestAlgorithmException(String message)
/* 14:   */   {
/* 15:56 */     super(message);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public UnsupportedDigestAlgorithmException(String message, Throwable cause)
/* 19:   */   {
/* 20:67 */     super(message, cause);
/* 21:   */   }
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.auth.UnsupportedDigestAlgorithmException
 * JD-Core Version:    0.7.0.1
 */