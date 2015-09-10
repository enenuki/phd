/*  1:   */ package org.apache.http.impl.conn;
/*  2:   */ 
/*  3:   */ import java.net.InetAddress;
/*  4:   */ import java.net.InetSocketAddress;
/*  5:   */ import org.apache.http.HttpHost;
/*  6:   */ 
/*  7:   */ class HttpInetSocketAddress
/*  8:   */   extends InetSocketAddress
/*  9:   */ {
/* 10:   */   private static final long serialVersionUID = -6650701828361907957L;
/* 11:   */   private final HttpHost host;
/* 12:   */   
/* 13:   */   public HttpInetSocketAddress(HttpHost host, InetAddress addr, int port)
/* 14:   */   {
/* 15:45 */     super(addr, port);
/* 16:46 */     if (host == null) {
/* 17:47 */       throw new IllegalArgumentException("HTTP host may not be null");
/* 18:   */     }
/* 19:49 */     this.host = host;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public HttpHost getHost()
/* 23:   */   {
/* 24:53 */     return this.host;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public String toString()
/* 28:   */   {
/* 29:58 */     return this.host.getHostName() + ":" + getPort();
/* 30:   */   }
/* 31:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.conn.HttpInetSocketAddress
 * JD-Core Version:    0.7.0.1
 */