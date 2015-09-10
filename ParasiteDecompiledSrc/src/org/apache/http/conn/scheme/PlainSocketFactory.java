/*   1:    */ package org.apache.http.conn.scheme;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.net.InetAddress;
/*   5:    */ import java.net.InetSocketAddress;
/*   6:    */ import java.net.Socket;
/*   7:    */ import java.net.SocketTimeoutException;
/*   8:    */ import java.net.UnknownHostException;
/*   9:    */ import org.apache.http.annotation.Immutable;
/*  10:    */ import org.apache.http.conn.ConnectTimeoutException;
/*  11:    */ import org.apache.http.params.HttpConnectionParams;
/*  12:    */ import org.apache.http.params.HttpParams;
/*  13:    */ 
/*  14:    */ @Immutable
/*  15:    */ public class PlainSocketFactory
/*  16:    */   implements SocketFactory, SchemeSocketFactory
/*  17:    */ {
/*  18:    */   private final HostNameResolver nameResolver;
/*  19:    */   
/*  20:    */   public static PlainSocketFactory getSocketFactory()
/*  21:    */   {
/*  22: 67 */     return new PlainSocketFactory();
/*  23:    */   }
/*  24:    */   
/*  25:    */   @Deprecated
/*  26:    */   public PlainSocketFactory(HostNameResolver nameResolver)
/*  27:    */   {
/*  28: 73 */     this.nameResolver = nameResolver;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public PlainSocketFactory()
/*  32:    */   {
/*  33: 78 */     this.nameResolver = null;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public Socket createSocket(HttpParams params)
/*  37:    */   {
/*  38: 89 */     return new Socket();
/*  39:    */   }
/*  40:    */   
/*  41:    */   public Socket createSocket()
/*  42:    */   {
/*  43: 93 */     return new Socket();
/*  44:    */   }
/*  45:    */   
/*  46:    */   public Socket connectSocket(Socket socket, InetSocketAddress remoteAddress, InetSocketAddress localAddress, HttpParams params)
/*  47:    */     throws IOException, ConnectTimeoutException
/*  48:    */   {
/*  49:104 */     if (remoteAddress == null) {
/*  50:105 */       throw new IllegalArgumentException("Remote address may not be null");
/*  51:    */     }
/*  52:107 */     if (params == null) {
/*  53:108 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*  54:    */     }
/*  55:110 */     Socket sock = socket;
/*  56:111 */     if (sock == null) {
/*  57:112 */       sock = createSocket();
/*  58:    */     }
/*  59:114 */     if (localAddress != null)
/*  60:    */     {
/*  61:115 */       sock.setReuseAddress(HttpConnectionParams.getSoReuseaddr(params));
/*  62:116 */       sock.bind(localAddress);
/*  63:    */     }
/*  64:118 */     int connTimeout = HttpConnectionParams.getConnectionTimeout(params);
/*  65:119 */     int soTimeout = HttpConnectionParams.getSoTimeout(params);
/*  66:    */     try
/*  67:    */     {
/*  68:122 */       sock.setSoTimeout(soTimeout);
/*  69:123 */       sock.connect(remoteAddress, connTimeout);
/*  70:    */     }
/*  71:    */     catch (SocketTimeoutException ex)
/*  72:    */     {
/*  73:125 */       throw new ConnectTimeoutException("Connect to " + remoteAddress + " timed out");
/*  74:    */     }
/*  75:127 */     return sock;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public final boolean isSecure(Socket sock)
/*  79:    */     throws IllegalArgumentException
/*  80:    */   {
/*  81:144 */     if (sock == null) {
/*  82:145 */       throw new IllegalArgumentException("Socket may not be null.");
/*  83:    */     }
/*  84:149 */     if (sock.isClosed()) {
/*  85:150 */       throw new IllegalArgumentException("Socket is closed.");
/*  86:    */     }
/*  87:152 */     return false;
/*  88:    */   }
/*  89:    */   
/*  90:    */   @Deprecated
/*  91:    */   public Socket connectSocket(Socket socket, String host, int port, InetAddress localAddress, int localPort, HttpParams params)
/*  92:    */     throws IOException, UnknownHostException, ConnectTimeoutException
/*  93:    */   {
/*  94:164 */     InetSocketAddress local = null;
/*  95:165 */     if ((localAddress != null) || (localPort > 0))
/*  96:    */     {
/*  97:167 */       if (localPort < 0) {
/*  98:168 */         localPort = 0;
/*  99:    */       }
/* 100:170 */       local = new InetSocketAddress(localAddress, localPort);
/* 101:    */     }
/* 102:    */     InetAddress remoteAddress;
/* 103:    */     InetAddress remoteAddress;
/* 104:173 */     if (this.nameResolver != null) {
/* 105:174 */       remoteAddress = this.nameResolver.resolve(host);
/* 106:    */     } else {
/* 107:176 */       remoteAddress = InetAddress.getByName(host);
/* 108:    */     }
/* 109:178 */     InetSocketAddress remote = new InetSocketAddress(remoteAddress, port);
/* 110:179 */     return connectSocket(socket, remote, local, params);
/* 111:    */   }
/* 112:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.conn.scheme.PlainSocketFactory
 * JD-Core Version:    0.7.0.1
 */