/*  1:   */ package org.apache.http.impl.auth;
/*  2:   */ 
/*  3:   */ import org.apache.http.annotation.Immutable;
/*  4:   */ import org.apache.http.auth.AuthenticationException;
/*  5:   */ 
/*  6:   */ @Immutable
/*  7:   */ public class NTLMEngineException
/*  8:   */   extends AuthenticationException
/*  9:   */ {
/* 10:   */   private static final long serialVersionUID = 6027981323731768824L;
/* 11:   */   
/* 12:   */   public NTLMEngineException() {}
/* 13:   */   
/* 14:   */   public NTLMEngineException(String message)
/* 15:   */   {
/* 16:54 */     super(message);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public NTLMEngineException(String message, Throwable cause)
/* 20:   */   {
/* 21:65 */     super(message, cause);
/* 22:   */   }
/* 23:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.auth.NTLMEngineException
 * JD-Core Version:    0.7.0.1
 */