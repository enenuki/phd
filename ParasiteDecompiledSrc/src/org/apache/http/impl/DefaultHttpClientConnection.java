/*  1:   */ package org.apache.http.impl;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.net.Socket;
/*  5:   */ import org.apache.http.params.HttpConnectionParams;
/*  6:   */ import org.apache.http.params.HttpParams;
/*  7:   */ 
/*  8:   */ public class DefaultHttpClientConnection
/*  9:   */   extends SocketHttpClientConnection
/* 10:   */ {
/* 11:   */   public void bind(Socket socket, HttpParams params)
/* 12:   */     throws IOException
/* 13:   */   {
/* 14:61 */     if (socket == null) {
/* 15:62 */       throw new IllegalArgumentException("Socket may not be null");
/* 16:   */     }
/* 17:64 */     if (params == null) {
/* 18:65 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/* 19:   */     }
/* 20:67 */     assertNotOpen();
/* 21:68 */     socket.setTcpNoDelay(HttpConnectionParams.getTcpNoDelay(params));
/* 22:69 */     socket.setSoTimeout(HttpConnectionParams.getSoTimeout(params));
/* 23:   */     
/* 24:71 */     int linger = HttpConnectionParams.getLinger(params);
/* 25:72 */     if (linger >= 0) {
/* 26:73 */       socket.setSoLinger(linger > 0, linger);
/* 27:   */     }
/* 28:75 */     super.bind(socket, params);
/* 29:   */   }
/* 30:   */   
/* 31:   */   public String toString()
/* 32:   */   {
/* 33:79 */     StringBuffer buffer = new StringBuffer();
/* 34:80 */     buffer.append("[");
/* 35:81 */     if (isOpen()) {
/* 36:82 */       buffer.append(getRemotePort());
/* 37:   */     } else {
/* 38:84 */       buffer.append("closed");
/* 39:   */     }
/* 40:86 */     buffer.append("]");
/* 41:87 */     return buffer.toString();
/* 42:   */   }
/* 43:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.DefaultHttpClientConnection
 * JD-Core Version:    0.7.0.1
 */