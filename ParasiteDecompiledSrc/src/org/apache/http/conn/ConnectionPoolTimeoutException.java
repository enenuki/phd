/*  1:   */ package org.apache.http.conn;
/*  2:   */ 
/*  3:   */ import org.apache.http.annotation.Immutable;
/*  4:   */ 
/*  5:   */ @Immutable
/*  6:   */ public class ConnectionPoolTimeoutException
/*  7:   */   extends ConnectTimeoutException
/*  8:   */ {
/*  9:   */   private static final long serialVersionUID = -7898874842020245128L;
/* 10:   */   
/* 11:   */   public ConnectionPoolTimeoutException() {}
/* 12:   */   
/* 13:   */   public ConnectionPoolTimeoutException(String message)
/* 14:   */   {
/* 15:57 */     super(message);
/* 16:   */   }
/* 17:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.conn.ConnectionPoolTimeoutException
 * JD-Core Version:    0.7.0.1
 */