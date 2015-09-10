/*  1:   */ package org.apache.http;
/*  2:   */ 
/*  3:   */ public class ProtocolException
/*  4:   */   extends HttpException
/*  5:   */ {
/*  6:   */   private static final long serialVersionUID = -2143571074341228994L;
/*  7:   */   
/*  8:   */   public ProtocolException() {}
/*  9:   */   
/* 10:   */   public ProtocolException(String message)
/* 11:   */   {
/* 12:54 */     super(message);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public ProtocolException(String message, Throwable cause)
/* 16:   */   {
/* 17:65 */     super(message, cause);
/* 18:   */   }
/* 19:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.ProtocolException
 * JD-Core Version:    0.7.0.1
 */