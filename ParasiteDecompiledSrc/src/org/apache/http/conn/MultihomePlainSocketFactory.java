/*   1:    */ package org.apache.http.conn;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.net.InetAddress;
/*   5:    */ import java.net.InetSocketAddress;
/*   6:    */ import java.net.Socket;
/*   7:    */ import java.net.SocketTimeoutException;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.Arrays;
/*  10:    */ import java.util.Collections;
/*  11:    */ import java.util.List;
/*  12:    */ import org.apache.http.annotation.Immutable;
/*  13:    */ import org.apache.http.conn.scheme.SocketFactory;
/*  14:    */ import org.apache.http.params.HttpConnectionParams;
/*  15:    */ import org.apache.http.params.HttpParams;
/*  16:    */ 
/*  17:    */ @Deprecated
/*  18:    */ @Immutable
/*  19:    */ public final class MultihomePlainSocketFactory
/*  20:    */   implements SocketFactory
/*  21:    */ {
/*  22: 67 */   private static final MultihomePlainSocketFactory DEFAULT_FACTORY = new MultihomePlainSocketFactory();
/*  23:    */   
/*  24:    */   public static MultihomePlainSocketFactory getSocketFactory()
/*  25:    */   {
/*  26: 74 */     return DEFAULT_FACTORY;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public Socket createSocket()
/*  30:    */   {
/*  31: 87 */     return new Socket();
/*  32:    */   }
/*  33:    */   
/*  34:    */   public Socket connectSocket(Socket sock, String host, int port, InetAddress localAddress, int localPort, HttpParams params)
/*  35:    */     throws IOException
/*  36:    */   {
/*  37:110 */     if (host == null) {
/*  38:111 */       throw new IllegalArgumentException("Target host may not be null.");
/*  39:    */     }
/*  40:113 */     if (params == null) {
/*  41:114 */       throw new IllegalArgumentException("Parameters may not be null.");
/*  42:    */     }
/*  43:117 */     if (sock == null) {
/*  44:118 */       sock = createSocket();
/*  45:    */     }
/*  46:120 */     if ((localAddress != null) || (localPort > 0))
/*  47:    */     {
/*  48:123 */       if (localPort < 0) {
/*  49:124 */         localPort = 0;
/*  50:    */       }
/*  51:126 */       InetSocketAddress isa = new InetSocketAddress(localAddress, localPort);
/*  52:    */       
/*  53:128 */       sock.bind(isa);
/*  54:    */     }
/*  55:131 */     int timeout = HttpConnectionParams.getConnectionTimeout(params);
/*  56:    */     
/*  57:133 */     InetAddress[] inetadrs = InetAddress.getAllByName(host);
/*  58:134 */     List<InetAddress> addresses = new ArrayList(inetadrs.length);
/*  59:135 */     addresses.addAll(Arrays.asList(inetadrs));
/*  60:136 */     Collections.shuffle(addresses);
/*  61:    */     
/*  62:138 */     IOException lastEx = null;
/*  63:139 */     for (InetAddress remoteAddress : addresses) {
/*  64:    */       try
/*  65:    */       {
/*  66:141 */         sock.connect(new InetSocketAddress(remoteAddress, port), timeout);
/*  67:    */       }
/*  68:    */       catch (SocketTimeoutException ex)
/*  69:    */       {
/*  70:144 */         throw new ConnectTimeoutException("Connect to " + remoteAddress + " timed out");
/*  71:    */       }
/*  72:    */       catch (IOException ex)
/*  73:    */       {
/*  74:147 */         sock = new Socket();
/*  75:    */         
/*  76:149 */         lastEx = ex;
/*  77:    */       }
/*  78:    */     }
/*  79:152 */     if (lastEx != null) {
/*  80:153 */       throw lastEx;
/*  81:    */     }
/*  82:155 */     return sock;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public final boolean isSecure(Socket sock)
/*  86:    */     throws IllegalArgumentException
/*  87:    */   {
/*  88:173 */     if (sock == null) {
/*  89:174 */       throw new IllegalArgumentException("Socket may not be null.");
/*  90:    */     }
/*  91:179 */     if (sock.getClass() != Socket.class) {
/*  92:180 */       throw new IllegalArgumentException("Socket not created by this factory.");
/*  93:    */     }
/*  94:185 */     if (sock.isClosed()) {
/*  95:186 */       throw new IllegalArgumentException("Socket is closed.");
/*  96:    */     }
/*  97:189 */     return false;
/*  98:    */   }
/*  99:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.conn.MultihomePlainSocketFactory
 * JD-Core Version:    0.7.0.1
 */