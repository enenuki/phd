/*   1:    */ package org.apache.log4j.chainsaw;
/*   2:    */ 
/*   3:    */ import java.io.EOFException;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.ObjectInputStream;
/*   6:    */ import java.net.InetAddress;
/*   7:    */ import java.net.ServerSocket;
/*   8:    */ import java.net.Socket;
/*   9:    */ import java.net.SocketException;
/*  10:    */ import org.apache.log4j.Category;
/*  11:    */ import org.apache.log4j.Logger;
/*  12:    */ import org.apache.log4j.spi.LoggingEvent;
/*  13:    */ 
/*  14:    */ class LoggingReceiver
/*  15:    */   extends Thread
/*  16:    */ {
/*  17: 36 */   private static final Logger LOG = Logger.getLogger(LoggingReceiver.class);
/*  18:    */   private MyTableModel mModel;
/*  19:    */   private ServerSocket mSvrSock;
/*  20:    */   
/*  21:    */   private class Slurper
/*  22:    */     implements Runnable
/*  23:    */   {
/*  24:    */     private final Socket mClient;
/*  25:    */     
/*  26:    */     Slurper(Socket aClient)
/*  27:    */     {
/*  28: 54 */       this.mClient = aClient;
/*  29:    */     }
/*  30:    */     
/*  31:    */     public void run()
/*  32:    */     {
/*  33: 59 */       LoggingReceiver.LOG.debug("Starting to get data");
/*  34:    */       try
/*  35:    */       {
/*  36: 61 */         ObjectInputStream ois = new ObjectInputStream(this.mClient.getInputStream());
/*  37:    */         for (;;)
/*  38:    */         {
/*  39: 64 */           LoggingEvent event = (LoggingEvent)ois.readObject();
/*  40: 65 */           LoggingReceiver.this.mModel.addEvent(new EventDetails(event));
/*  41:    */         }
/*  42:    */       }
/*  43:    */       catch (EOFException e)
/*  44:    */       {
/*  45: 68 */         LoggingReceiver.LOG.info("Reached EOF, closing connection");
/*  46:    */       }
/*  47:    */       catch (SocketException e)
/*  48:    */       {
/*  49: 70 */         LoggingReceiver.LOG.info("Caught SocketException, closing connection");
/*  50:    */       }
/*  51:    */       catch (IOException e)
/*  52:    */       {
/*  53: 72 */         LoggingReceiver.LOG.warn("Got IOException, closing connection", e);
/*  54:    */       }
/*  55:    */       catch (ClassNotFoundException e)
/*  56:    */       {
/*  57: 74 */         LoggingReceiver.LOG.warn("Got ClassNotFoundException, closing connection", e);
/*  58:    */       }
/*  59:    */       try
/*  60:    */       {
/*  61: 78 */         this.mClient.close();
/*  62:    */       }
/*  63:    */       catch (IOException e)
/*  64:    */       {
/*  65: 80 */         LoggingReceiver.LOG.warn("Error closing connection", e);
/*  66:    */       }
/*  67:    */     }
/*  68:    */   }
/*  69:    */   
/*  70:    */   LoggingReceiver(MyTableModel aModel, int aPort)
/*  71:    */     throws IOException
/*  72:    */   {
/*  73: 99 */     setDaemon(true);
/*  74:100 */     this.mModel = aModel;
/*  75:101 */     this.mSvrSock = new ServerSocket(aPort);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void run()
/*  79:    */   {
/*  80:106 */     LOG.info("Thread started");
/*  81:    */     try
/*  82:    */     {
/*  83:    */       for (;;)
/*  84:    */       {
/*  85:109 */         LOG.debug("Waiting for a connection");
/*  86:110 */         Socket client = this.mSvrSock.accept();
/*  87:111 */         LOG.debug("Got a connection from " + client.getInetAddress().getHostName());
/*  88:    */         
/*  89:113 */         Thread t = new Thread(new Slurper(client));
/*  90:114 */         t.setDaemon(true);
/*  91:115 */         t.start();
/*  92:    */       }
/*  93:    */     }
/*  94:    */     catch (IOException e)
/*  95:    */     {
/*  96:118 */       LOG.error("Error in accepting connections, stopping.", e);
/*  97:    */     }
/*  98:    */   }
/*  99:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.chainsaw.LoggingReceiver
 * JD-Core Version:    0.7.0.1
 */