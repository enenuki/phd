/*   1:    */ package org.apache.log4j.net;
/*   2:    */ 
/*   3:    */ import java.io.BufferedInputStream;
/*   4:    */ import java.io.EOFException;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.io.InterruptedIOException;
/*   7:    */ import java.io.ObjectInputStream;
/*   8:    */ import java.net.Socket;
/*   9:    */ import java.net.SocketException;
/*  10:    */ import org.apache.log4j.Category;
/*  11:    */ import org.apache.log4j.Logger;
/*  12:    */ import org.apache.log4j.Priority;
/*  13:    */ import org.apache.log4j.spi.LoggerRepository;
/*  14:    */ import org.apache.log4j.spi.LoggingEvent;
/*  15:    */ 
/*  16:    */ public class SocketNode
/*  17:    */   implements Runnable
/*  18:    */ {
/*  19:    */   Socket socket;
/*  20:    */   LoggerRepository hierarchy;
/*  21:    */   ObjectInputStream ois;
/*  22: 50 */   static Logger logger = Logger.getLogger(SocketNode.class);
/*  23:    */   
/*  24:    */   public SocketNode(Socket socket, LoggerRepository hierarchy)
/*  25:    */   {
/*  26: 53 */     this.socket = socket;
/*  27: 54 */     this.hierarchy = hierarchy;
/*  28:    */     try
/*  29:    */     {
/*  30: 56 */       this.ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
/*  31:    */     }
/*  32:    */     catch (InterruptedIOException e)
/*  33:    */     {
/*  34: 59 */       Thread.currentThread().interrupt();
/*  35: 60 */       logger.error("Could not open ObjectInputStream to " + socket, e);
/*  36:    */     }
/*  37:    */     catch (IOException e)
/*  38:    */     {
/*  39: 62 */       logger.error("Could not open ObjectInputStream to " + socket, e);
/*  40:    */     }
/*  41:    */     catch (RuntimeException e)
/*  42:    */     {
/*  43: 64 */       logger.error("Could not open ObjectInputStream to " + socket, e);
/*  44:    */     }
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void run()
/*  48:    */   {
/*  49:    */     try
/*  50:    */     {
/*  51: 79 */       if (this.ois != null) {
/*  52:    */         for (;;)
/*  53:    */         {
/*  54: 82 */           LoggingEvent event = (LoggingEvent)this.ois.readObject();
/*  55:    */           
/*  56: 84 */           Logger remoteLogger = this.hierarchy.getLogger(event.getLoggerName());
/*  57: 87 */           if (event.getLevel().isGreaterOrEqual(remoteLogger.getEffectiveLevel())) {
/*  58: 89 */             remoteLogger.callAppenders(event);
/*  59:    */           }
/*  60:    */         }
/*  61:    */       }
/*  62:    */       return;
/*  63:    */     }
/*  64:    */     catch (EOFException e)
/*  65:    */     {
/*  66: 94 */       logger.info("Caught java.io.EOFException closing conneciton.");
/*  67:    */     }
/*  68:    */     catch (SocketException e)
/*  69:    */     {
/*  70: 96 */       logger.info("Caught java.net.SocketException closing conneciton.");
/*  71:    */     }
/*  72:    */     catch (InterruptedIOException e)
/*  73:    */     {
/*  74: 98 */       Thread.currentThread().interrupt();
/*  75: 99 */       logger.info("Caught java.io.InterruptedIOException: " + e);
/*  76:100 */       logger.info("Closing connection.");
/*  77:    */     }
/*  78:    */     catch (IOException e)
/*  79:    */     {
/*  80:102 */       logger.info("Caught java.io.IOException: " + e);
/*  81:103 */       logger.info("Closing connection.");
/*  82:    */     }
/*  83:    */     catch (Exception e)
/*  84:    */     {
/*  85:105 */       logger.error("Unexpected exception. Closing conneciton.", e);
/*  86:    */     }
/*  87:    */     finally
/*  88:    */     {
/*  89:107 */       if (this.ois != null) {
/*  90:    */         try
/*  91:    */         {
/*  92:109 */           this.ois.close();
/*  93:    */         }
/*  94:    */         catch (Exception e)
/*  95:    */         {
/*  96:111 */           logger.info("Could not close connection.", e);
/*  97:    */         }
/*  98:    */       }
/*  99:114 */       if (this.socket != null) {
/* 100:    */         try
/* 101:    */         {
/* 102:116 */           this.socket.close();
/* 103:    */         }
/* 104:    */         catch (InterruptedIOException e)
/* 105:    */         {
/* 106:118 */           Thread.currentThread().interrupt();
/* 107:    */         }
/* 108:    */         catch (IOException ex) {}
/* 109:    */       }
/* 110:    */     }
/* 111:    */   }
/* 112:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.net.SocketNode
 * JD-Core Version:    0.7.0.1
 */