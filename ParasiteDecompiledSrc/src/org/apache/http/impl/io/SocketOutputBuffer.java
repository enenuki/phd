/*  1:   */ package org.apache.http.impl.io;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.net.Socket;
/*  5:   */ import org.apache.http.params.HttpParams;
/*  6:   */ 
/*  7:   */ public class SocketOutputBuffer
/*  8:   */   extends AbstractSessionOutputBuffer
/*  9:   */ {
/* 10:   */   public SocketOutputBuffer(Socket socket, int buffersize, HttpParams params)
/* 11:   */     throws IOException
/* 12:   */   {
/* 13:64 */     if (socket == null) {
/* 14:65 */       throw new IllegalArgumentException("Socket may not be null");
/* 15:   */     }
/* 16:67 */     if (buffersize < 0) {
/* 17:68 */       buffersize = socket.getSendBufferSize();
/* 18:   */     }
/* 19:70 */     if (buffersize < 1024) {
/* 20:71 */       buffersize = 1024;
/* 21:   */     }
/* 22:73 */     init(socket.getOutputStream(), buffersize, params);
/* 23:   */   }
/* 24:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.io.SocketOutputBuffer
 * JD-Core Version:    0.7.0.1
 */