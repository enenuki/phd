/*  1:   */ package org.apache.http.conn.scheme;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.net.Socket;
/*  5:   */ import java.net.UnknownHostException;
/*  6:   */ 
/*  7:   */ @Deprecated
/*  8:   */ class LayeredSchemeSocketFactoryAdaptor
/*  9:   */   extends SchemeSocketFactoryAdaptor
/* 10:   */   implements LayeredSchemeSocketFactory
/* 11:   */ {
/* 12:   */   private final LayeredSocketFactory factory;
/* 13:   */   
/* 14:   */   LayeredSchemeSocketFactoryAdaptor(LayeredSocketFactory factory)
/* 15:   */   {
/* 16:41 */     super(factory);
/* 17:42 */     this.factory = factory;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public Socket createLayeredSocket(Socket socket, String target, int port, boolean autoClose)
/* 21:   */     throws IOException, UnknownHostException
/* 22:   */   {
/* 23:49 */     return this.factory.createSocket(socket, target, port, autoClose);
/* 24:   */   }
/* 25:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.conn.scheme.LayeredSchemeSocketFactoryAdaptor
 * JD-Core Version:    0.7.0.1
 */