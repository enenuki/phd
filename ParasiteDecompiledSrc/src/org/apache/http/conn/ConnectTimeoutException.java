/*  1:   */ package org.apache.http.conn;
/*  2:   */ 
/*  3:   */ import java.io.InterruptedIOException;
/*  4:   */ import org.apache.http.annotation.Immutable;
/*  5:   */ 
/*  6:   */ @Immutable
/*  7:   */ public class ConnectTimeoutException
/*  8:   */   extends InterruptedIOException
/*  9:   */ {
/* 10:   */   private static final long serialVersionUID = -4816682903149535989L;
/* 11:   */   
/* 12:   */   public ConnectTimeoutException() {}
/* 13:   */   
/* 14:   */   public ConnectTimeoutException(String message)
/* 15:   */   {
/* 16:59 */     super(message);
/* 17:   */   }
/* 18:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.conn.ConnectTimeoutException
 * JD-Core Version:    0.7.0.1
 */