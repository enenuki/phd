/*  1:   */ package org.apache.http.conn.scheme;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.net.InetAddress;
/*  5:   */ import java.net.InetSocketAddress;
/*  6:   */ import java.net.Socket;
/*  7:   */ import java.net.UnknownHostException;
/*  8:   */ import org.apache.http.conn.ConnectTimeoutException;
/*  9:   */ import org.apache.http.params.BasicHttpParams;
/* 10:   */ import org.apache.http.params.HttpParams;
/* 11:   */ 
/* 12:   */ @Deprecated
/* 13:   */ class SocketFactoryAdaptor
/* 14:   */   implements SocketFactory
/* 15:   */ {
/* 16:   */   private final SchemeSocketFactory factory;
/* 17:   */   
/* 18:   */   SocketFactoryAdaptor(SchemeSocketFactory factory)
/* 19:   */   {
/* 20:47 */     this.factory = factory;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public Socket createSocket()
/* 24:   */     throws IOException
/* 25:   */   {
/* 26:51 */     HttpParams params = new BasicHttpParams();
/* 27:52 */     return this.factory.createSocket(params);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public Socket connectSocket(Socket socket, String host, int port, InetAddress localAddress, int localPort, HttpParams params)
/* 31:   */     throws IOException, UnknownHostException, ConnectTimeoutException
/* 32:   */   {
/* 33:60 */     InetSocketAddress local = null;
/* 34:61 */     if ((localAddress != null) || (localPort > 0))
/* 35:   */     {
/* 36:63 */       if (localPort < 0) {
/* 37:64 */         localPort = 0;
/* 38:   */       }
/* 39:66 */       local = new InetSocketAddress(localAddress, localPort);
/* 40:   */     }
/* 41:68 */     InetAddress remoteAddress = InetAddress.getByName(host);
/* 42:69 */     InetSocketAddress remote = new InetSocketAddress(remoteAddress, port);
/* 43:70 */     return this.factory.connectSocket(socket, remote, local, params);
/* 44:   */   }
/* 45:   */   
/* 46:   */   public boolean isSecure(Socket socket)
/* 47:   */     throws IllegalArgumentException
/* 48:   */   {
/* 49:74 */     return this.factory.isSecure(socket);
/* 50:   */   }
/* 51:   */   
/* 52:   */   public SchemeSocketFactory getFactory()
/* 53:   */   {
/* 54:78 */     return this.factory;
/* 55:   */   }
/* 56:   */   
/* 57:   */   public boolean equals(Object obj)
/* 58:   */   {
/* 59:83 */     if (obj == null) {
/* 60:83 */       return false;
/* 61:   */     }
/* 62:84 */     if (this == obj) {
/* 63:84 */       return true;
/* 64:   */     }
/* 65:85 */     if ((obj instanceof SocketFactoryAdaptor)) {
/* 66:86 */       return this.factory.equals(((SocketFactoryAdaptor)obj).factory);
/* 67:   */     }
/* 68:88 */     return this.factory.equals(obj);
/* 69:   */   }
/* 70:   */   
/* 71:   */   public int hashCode()
/* 72:   */   {
/* 73:94 */     return this.factory.hashCode();
/* 74:   */   }
/* 75:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.conn.scheme.SocketFactoryAdaptor
 * JD-Core Version:    0.7.0.1
 */