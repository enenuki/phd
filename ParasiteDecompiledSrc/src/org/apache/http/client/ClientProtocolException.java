/*  1:   */ package org.apache.http.client;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.apache.http.annotation.Immutable;
/*  5:   */ 
/*  6:   */ @Immutable
/*  7:   */ public class ClientProtocolException
/*  8:   */   extends IOException
/*  9:   */ {
/* 10:   */   private static final long serialVersionUID = -5596590843227115865L;
/* 11:   */   
/* 12:   */   public ClientProtocolException() {}
/* 13:   */   
/* 14:   */   public ClientProtocolException(String s)
/* 15:   */   {
/* 16:48 */     super(s);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public ClientProtocolException(Throwable cause)
/* 20:   */   {
/* 21:52 */     initCause(cause);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public ClientProtocolException(String message, Throwable cause)
/* 25:   */   {
/* 26:56 */     super(message);
/* 27:57 */     initCause(cause);
/* 28:   */   }
/* 29:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.client.ClientProtocolException
 * JD-Core Version:    0.7.0.1
 */