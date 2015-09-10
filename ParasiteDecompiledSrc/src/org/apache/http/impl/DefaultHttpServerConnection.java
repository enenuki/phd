/*  1:   */ package org.apache.http.impl;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.net.Socket;
/*  5:   */ import org.apache.http.params.HttpConnectionParams;
/*  6:   */ import org.apache.http.params.HttpParams;
/*  7:   */ 
/*  8:   */ public class DefaultHttpServerConnection
/*  9:   */   extends SocketHttpServerConnection
/* 10:   */ {
/* 11:   */   public void bind(Socket socket, HttpParams params)
/* 12:   */     throws IOException
/* 13:   */   {
/* 14:59 */     if (socket == null) {
/* 15:60 */       throw new IllegalArgumentException("Socket may not be null");
/* 16:   */     }
/* 17:62 */     if (params == null) {
/* 18:63 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/* 19:   */     }
/* 20:65 */     assertNotOpen();
/* 21:66 */     socket.setTcpNoDelay(HttpConnectionParams.getTcpNoDelay(params));
/* 22:67 */     socket.setSoTimeout(HttpConnectionParams.getSoTimeout(params));
/* 23:   */     
/* 24:69 */     int linger = HttpConnectionParams.getLinger(params);
/* 25:70 */     if (linger >= 0) {
/* 26:71 */       socket.setSoLinger(linger > 0, linger);
/* 27:   */     }
/* 28:73 */     super.bind(socket, params);
/* 29:   */   }
/* 30:   */   
/* 31:   */   public String toString()
/* 32:   */   {
/* 33:77 */     StringBuffer buffer = new StringBuffer();
/* 34:78 */     buffer.append("[");
/* 35:79 */     if (isOpen()) {
/* 36:80 */       buffer.append(getRemotePort());
/* 37:   */     } else {
/* 38:82 */       buffer.append("closed");
/* 39:   */     }
/* 40:84 */     buffer.append("]");
/* 41:85 */     return buffer.toString();
/* 42:   */   }
/* 43:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.DefaultHttpServerConnection
 * JD-Core Version:    0.7.0.1
 */