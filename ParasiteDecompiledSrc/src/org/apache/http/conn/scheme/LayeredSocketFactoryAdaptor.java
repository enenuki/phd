/*  1:   */ package org.apache.http.conn.scheme;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.net.Socket;
/*  5:   */ import java.net.UnknownHostException;
/*  6:   */ 
/*  7:   */ @Deprecated
/*  8:   */ class LayeredSocketFactoryAdaptor
/*  9:   */   extends SocketFactoryAdaptor
/* 10:   */   implements LayeredSocketFactory
/* 11:   */ {
/* 12:   */   private final LayeredSchemeSocketFactory factory;
/* 13:   */   
/* 14:   */   LayeredSocketFactoryAdaptor(LayeredSchemeSocketFactory factory)
/* 15:   */   {
/* 16:40 */     super(factory);
/* 17:41 */     this.factory = factory;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public Socket createSocket(Socket socket, String host, int port, boolean autoClose)
/* 21:   */     throws IOException, UnknownHostException
/* 22:   */   {
/* 23:47 */     return this.factory.createLayeredSocket(socket, host, port, autoClose);
/* 24:   */   }
/* 25:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.conn.scheme.LayeredSocketFactoryAdaptor
 * JD-Core Version:    0.7.0.1
 */