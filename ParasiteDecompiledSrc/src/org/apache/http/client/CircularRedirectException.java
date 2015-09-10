/*  1:   */ package org.apache.http.client;
/*  2:   */ 
/*  3:   */ import org.apache.http.annotation.Immutable;
/*  4:   */ 
/*  5:   */ @Immutable
/*  6:   */ public class CircularRedirectException
/*  7:   */   extends RedirectException
/*  8:   */ {
/*  9:   */   private static final long serialVersionUID = 6830063487001091803L;
/* 10:   */   
/* 11:   */   public CircularRedirectException() {}
/* 12:   */   
/* 13:   */   public CircularRedirectException(String message)
/* 14:   */   {
/* 15:55 */     super(message);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public CircularRedirectException(String message, Throwable cause)
/* 19:   */   {
/* 20:66 */     super(message, cause);
/* 21:   */   }
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.client.CircularRedirectException
 * JD-Core Version:    0.7.0.1
 */