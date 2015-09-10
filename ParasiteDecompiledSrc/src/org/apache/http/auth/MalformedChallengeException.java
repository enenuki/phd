/*  1:   */ package org.apache.http.auth;
/*  2:   */ 
/*  3:   */ import org.apache.http.ProtocolException;
/*  4:   */ import org.apache.http.annotation.Immutable;
/*  5:   */ 
/*  6:   */ @Immutable
/*  7:   */ public class MalformedChallengeException
/*  8:   */   extends ProtocolException
/*  9:   */ {
/* 10:   */   private static final long serialVersionUID = 814586927989932284L;
/* 11:   */   
/* 12:   */   public MalformedChallengeException() {}
/* 13:   */   
/* 14:   */   public MalformedChallengeException(String message)
/* 15:   */   {
/* 16:58 */     super(message);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public MalformedChallengeException(String message, Throwable cause)
/* 20:   */   {
/* 21:69 */     super(message, cause);
/* 22:   */   }
/* 23:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.auth.MalformedChallengeException
 * JD-Core Version:    0.7.0.1
 */