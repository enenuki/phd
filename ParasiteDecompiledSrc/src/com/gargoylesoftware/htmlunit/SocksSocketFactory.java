/*   1:    */ package com.gargoylesoftware.htmlunit;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.net.InetSocketAddress;
/*   5:    */ import java.net.Proxy;
/*   6:    */ import java.net.Proxy.Type;
/*   7:    */ import java.net.Socket;
/*   8:    */ import java.net.SocketTimeoutException;
/*   9:    */ import java.net.UnknownHostException;
/*  10:    */ import org.apache.http.HttpHost;
/*  11:    */ import org.apache.http.conn.ConnectTimeoutException;
/*  12:    */ import org.apache.http.conn.scheme.SchemeSocketFactory;
/*  13:    */ import org.apache.http.params.HttpConnectionParams;
/*  14:    */ import org.apache.http.params.HttpParams;
/*  15:    */ 
/*  16:    */ class SocksSocketFactory
/*  17:    */   implements SchemeSocketFactory
/*  18:    */ {
/*  19:    */   private HttpHost socksProxy_;
/*  20:    */   
/*  21:    */   void setSocksProxy(HttpHost socksProxy)
/*  22:    */   {
/*  23: 41 */     this.socksProxy_ = socksProxy;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public Socket createSocket(HttpParams params)
/*  27:    */   {
/*  28: 48 */     if (this.socksProxy_ != null)
/*  29:    */     {
/*  30: 49 */       InetSocketAddress address = new InetSocketAddress(this.socksProxy_.getHostName(), this.socksProxy_.getPort());
/*  31: 50 */       Proxy proxy = new Proxy(Proxy.Type.SOCKS, address);
/*  32: 51 */       return new Socket(proxy);
/*  33:    */     }
/*  34: 53 */     return new Socket();
/*  35:    */   }
/*  36:    */   
/*  37:    */   public Socket connectSocket(Socket sock, InetSocketAddress remoteAddress, InetSocketAddress localAddress, HttpParams params)
/*  38:    */     throws IOException, UnknownHostException, ConnectTimeoutException
/*  39:    */   {
/*  40: 65 */     String host = remoteAddress.getHostName();
/*  41: 66 */     if (host == null) {
/*  42: 67 */       throw new IllegalArgumentException("Target host may not be null.");
/*  43:    */     }
/*  44: 69 */     if (params == null) {
/*  45: 70 */       throw new IllegalArgumentException("Parameters may not be null.");
/*  46:    */     }
/*  47: 73 */     if (sock == null) {
/*  48: 74 */       sock = createSocket(params);
/*  49:    */     }
/*  50: 77 */     if (localAddress != null)
/*  51:    */     {
/*  52: 78 */       int localPort = localAddress.getPort();
/*  53: 79 */       if (localPort < 0) {
/*  54: 80 */         localPort = 0;
/*  55:    */       }
/*  56: 83 */       sock.bind(new InetSocketAddress(localAddress.getHostName(), localPort));
/*  57:    */     }
/*  58: 86 */     int timeout = HttpConnectionParams.getConnectionTimeout(params);
/*  59:    */     try
/*  60:    */     {
/*  61: 89 */       sock.connect(remoteAddress, timeout);
/*  62:    */     }
/*  63:    */     catch (SocketTimeoutException ex)
/*  64:    */     {
/*  65: 92 */       throw new ConnectTimeoutException("Connect to " + remoteAddress + " timed out");
/*  66:    */     }
/*  67: 94 */     return sock;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public final boolean isSecure(Socket sock)
/*  71:    */     throws IllegalArgumentException
/*  72:    */   {
/*  73:106 */     if (sock == null) {
/*  74:107 */       throw new IllegalArgumentException("Socket may not be null.");
/*  75:    */     }
/*  76:111 */     if (sock.isClosed()) {
/*  77:112 */       throw new IllegalArgumentException("Socket is closed.");
/*  78:    */     }
/*  79:114 */     return false;
/*  80:    */   }
/*  81:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.SocksSocketFactory
 * JD-Core Version:    0.7.0.1
 */