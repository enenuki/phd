/*   1:    */ package org.apache.log4j.varia;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InterruptedIOException;
/*   5:    */ import java.net.ServerSocket;
/*   6:    */ import java.net.Socket;
/*   7:    */ import org.apache.log4j.helpers.LogLog;
/*   8:    */ 
/*   9:    */ class HUP
/*  10:    */   extends Thread
/*  11:    */ {
/*  12:    */   int port;
/*  13:    */   ExternallyRolledFileAppender er;
/*  14:    */   
/*  15:    */   HUP(ExternallyRolledFileAppender er, int port)
/*  16:    */   {
/*  17:115 */     this.er = er;
/*  18:116 */     this.port = port;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public void run()
/*  22:    */   {
/*  23:121 */     while (!isInterrupted()) {
/*  24:    */       try
/*  25:    */       {
/*  26:123 */         ServerSocket serverSocket = new ServerSocket(this.port);
/*  27:    */         for (;;)
/*  28:    */         {
/*  29:125 */           Socket socket = serverSocket.accept();
/*  30:126 */           LogLog.debug("Connected to client at " + socket.getInetAddress());
/*  31:127 */           new Thread(new HUPNode(socket, this.er), "ExternallyRolledFileAppender-HUP").start();
/*  32:    */         }
/*  33:    */       }
/*  34:    */       catch (InterruptedIOException e)
/*  35:    */       {
/*  36:130 */         Thread.currentThread().interrupt();
/*  37:131 */         e.printStackTrace();
/*  38:    */       }
/*  39:    */       catch (IOException e)
/*  40:    */       {
/*  41:133 */         e.printStackTrace();
/*  42:    */       }
/*  43:    */       catch (RuntimeException e)
/*  44:    */       {
/*  45:135 */         e.printStackTrace();
/*  46:    */       }
/*  47:    */     }
/*  48:    */   }
/*  49:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.varia.HUP
 * JD-Core Version:    0.7.0.1
 */