/*  1:   */ package org.apache.http.conn;
/*  2:   */ 
/*  3:   */ import java.net.ConnectException;
/*  4:   */ import org.apache.http.HttpHost;
/*  5:   */ import org.apache.http.annotation.Immutable;
/*  6:   */ 
/*  7:   */ @Immutable
/*  8:   */ public class HttpHostConnectException
/*  9:   */   extends ConnectException
/* 10:   */ {
/* 11:   */   private static final long serialVersionUID = -3194482710275220224L;
/* 12:   */   private final HttpHost host;
/* 13:   */   
/* 14:   */   public HttpHostConnectException(HttpHost host, ConnectException cause)
/* 15:   */   {
/* 16:49 */     super("Connection to " + host + " refused");
/* 17:50 */     this.host = host;
/* 18:51 */     initCause(cause);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public HttpHost getHost()
/* 22:   */   {
/* 23:55 */     return this.host;
/* 24:   */   }
/* 25:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.conn.HttpHostConnectException
 * JD-Core Version:    0.7.0.1
 */