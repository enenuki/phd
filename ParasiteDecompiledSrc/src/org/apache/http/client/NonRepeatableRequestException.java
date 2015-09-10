/*  1:   */ package org.apache.http.client;
/*  2:   */ 
/*  3:   */ import org.apache.http.ProtocolException;
/*  4:   */ import org.apache.http.annotation.Immutable;
/*  5:   */ 
/*  6:   */ @Immutable
/*  7:   */ public class NonRepeatableRequestException
/*  8:   */   extends ProtocolException
/*  9:   */ {
/* 10:   */   private static final long serialVersionUID = 82685265288806048L;
/* 11:   */   
/* 12:   */   public NonRepeatableRequestException() {}
/* 13:   */   
/* 14:   */   public NonRepeatableRequestException(String message)
/* 15:   */   {
/* 16:58 */     super(message);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public NonRepeatableRequestException(String message, Throwable cause)
/* 20:   */   {
/* 21:68 */     super(message, cause);
/* 22:   */   }
/* 23:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.client.NonRepeatableRequestException
 * JD-Core Version:    0.7.0.1
 */