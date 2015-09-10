/*   1:    */ package org.apache.http.impl.io;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InterruptedIOException;
/*   5:    */ import java.net.Socket;
/*   6:    */ import org.apache.http.io.EofSensor;
/*   7:    */ import org.apache.http.params.HttpParams;
/*   8:    */ 
/*   9:    */ public class SocketInputBuffer
/*  10:    */   extends AbstractSessionInputBuffer
/*  11:    */   implements EofSensor
/*  12:    */ {
/*  13: 52 */   private static final Class SOCKET_TIMEOUT_CLASS = ;
/*  14:    */   private final Socket socket;
/*  15:    */   private boolean eof;
/*  16:    */   
/*  17:    */   private static Class SocketTimeoutExceptionClass()
/*  18:    */   {
/*  19:    */     try
/*  20:    */     {
/*  21: 62 */       return Class.forName("java.net.SocketTimeoutException");
/*  22:    */     }
/*  23:    */     catch (ClassNotFoundException e) {}
/*  24: 64 */     return null;
/*  25:    */   }
/*  26:    */   
/*  27:    */   private static boolean isSocketTimeoutException(InterruptedIOException e)
/*  28:    */   {
/*  29: 69 */     if (SOCKET_TIMEOUT_CLASS != null) {
/*  30: 70 */       return SOCKET_TIMEOUT_CLASS.isInstance(e);
/*  31:    */     }
/*  32: 72 */     return true;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public SocketInputBuffer(Socket socket, int buffersize, HttpParams params)
/*  36:    */     throws IOException
/*  37:    */   {
/*  38: 95 */     if (socket == null) {
/*  39: 96 */       throw new IllegalArgumentException("Socket may not be null");
/*  40:    */     }
/*  41: 98 */     this.socket = socket;
/*  42: 99 */     this.eof = false;
/*  43:100 */     if (buffersize < 0) {
/*  44:101 */       buffersize = socket.getReceiveBufferSize();
/*  45:    */     }
/*  46:103 */     if (buffersize < 1024) {
/*  47:104 */       buffersize = 1024;
/*  48:    */     }
/*  49:106 */     init(socket.getInputStream(), buffersize, params);
/*  50:    */   }
/*  51:    */   
/*  52:    */   protected int fillBuffer()
/*  53:    */     throws IOException
/*  54:    */   {
/*  55:110 */     int i = super.fillBuffer();
/*  56:111 */     this.eof = (i == -1);
/*  57:112 */     return i;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public boolean isDataAvailable(int timeout)
/*  61:    */     throws IOException
/*  62:    */   {
/*  63:116 */     boolean result = hasBufferedData();
/*  64:117 */     if (!result)
/*  65:    */     {
/*  66:118 */       int oldtimeout = this.socket.getSoTimeout();
/*  67:    */       try
/*  68:    */       {
/*  69:120 */         this.socket.setSoTimeout(timeout);
/*  70:121 */         fillBuffer();
/*  71:122 */         result = hasBufferedData();
/*  72:    */       }
/*  73:    */       catch (InterruptedIOException e)
/*  74:    */       {
/*  75:124 */         if (!isSocketTimeoutException(e)) {
/*  76:125 */           throw e;
/*  77:    */         }
/*  78:    */       }
/*  79:    */       finally
/*  80:    */       {
/*  81:128 */         this.socket.setSoTimeout(oldtimeout);
/*  82:    */       }
/*  83:    */     }
/*  84:131 */     return result;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public boolean isEof()
/*  88:    */   {
/*  89:135 */     return this.eof;
/*  90:    */   }
/*  91:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.io.SocketInputBuffer
 * JD-Core Version:    0.7.0.1
 */