/*  1:   */ package org.apache.http.conn.scheme;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.net.InetAddress;
/*  5:   */ import java.net.InetSocketAddress;
/*  6:   */ import java.net.Socket;
/*  7:   */ import java.net.UnknownHostException;
/*  8:   */ import org.apache.http.conn.ConnectTimeoutException;
/*  9:   */ import org.apache.http.params.HttpParams;
/* 10:   */ 
/* 11:   */ @Deprecated
/* 12:   */ class SchemeSocketFactoryAdaptor
/* 13:   */   implements SchemeSocketFactory
/* 14:   */ {
/* 15:   */   private final SocketFactory factory;
/* 16:   */   
/* 17:   */   SchemeSocketFactoryAdaptor(SocketFactory factory)
/* 18:   */   {
/* 19:46 */     this.factory = factory;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public Socket connectSocket(Socket sock, InetSocketAddress remoteAddress, InetSocketAddress localAddress, HttpParams params)
/* 23:   */     throws IOException, UnknownHostException, ConnectTimeoutException
/* 24:   */   {
/* 25:54 */     String host = remoteAddress.getHostName();
/* 26:55 */     int port = remoteAddress.getPort();
/* 27:56 */     InetAddress local = null;
/* 28:57 */     int localPort = 0;
/* 29:58 */     if (localAddress != null)
/* 30:   */     {
/* 31:59 */       local = localAddress.getAddress();
/* 32:60 */       localPort = localAddress.getPort();
/* 33:   */     }
/* 34:62 */     return this.factory.connectSocket(sock, host, port, local, localPort, params);
/* 35:   */   }
/* 36:   */   
/* 37:   */   public Socket createSocket(HttpParams params)
/* 38:   */     throws IOException
/* 39:   */   {
/* 40:66 */     return this.factory.createSocket();
/* 41:   */   }
/* 42:   */   
/* 43:   */   public boolean isSecure(Socket sock)
/* 44:   */     throws IllegalArgumentException
/* 45:   */   {
/* 46:70 */     return this.factory.isSecure(sock);
/* 47:   */   }
/* 48:   */   
/* 49:   */   public SocketFactory getFactory()
/* 50:   */   {
/* 51:74 */     return this.factory;
/* 52:   */   }
/* 53:   */   
/* 54:   */   public boolean equals(Object obj)
/* 55:   */   {
/* 56:79 */     if (obj == null) {
/* 57:79 */       return false;
/* 58:   */     }
/* 59:80 */     if (this == obj) {
/* 60:80 */       return true;
/* 61:   */     }
/* 62:81 */     if ((obj instanceof SchemeSocketFactoryAdaptor)) {
/* 63:82 */       return this.factory.equals(((SchemeSocketFactoryAdaptor)obj).factory);
/* 64:   */     }
/* 65:84 */     return this.factory.equals(obj);
/* 66:   */   }
/* 67:   */   
/* 68:   */   public int hashCode()
/* 69:   */   {
/* 70:90 */     return this.factory.hashCode();
/* 71:   */   }
/* 72:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.conn.scheme.SchemeSocketFactoryAdaptor
 * JD-Core Version:    0.7.0.1
 */