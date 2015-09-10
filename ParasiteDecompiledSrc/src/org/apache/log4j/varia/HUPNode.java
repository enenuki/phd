/*   1:    */ package org.apache.log4j.varia;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.FilterOutputStream;
/*   6:    */ import java.io.IOException;
/*   7:    */ import java.io.InterruptedIOException;
/*   8:    */ import java.net.Socket;
/*   9:    */ import org.apache.log4j.RollingFileAppender;
/*  10:    */ import org.apache.log4j.helpers.LogLog;
/*  11:    */ 
/*  12:    */ class HUPNode
/*  13:    */   implements Runnable
/*  14:    */ {
/*  15:    */   Socket socket;
/*  16:    */   DataInputStream dis;
/*  17:    */   DataOutputStream dos;
/*  18:    */   ExternallyRolledFileAppender er;
/*  19:    */   
/*  20:    */   public HUPNode(Socket socket, ExternallyRolledFileAppender er)
/*  21:    */   {
/*  22:150 */     this.socket = socket;
/*  23:151 */     this.er = er;
/*  24:    */     try
/*  25:    */     {
/*  26:153 */       this.dis = new DataInputStream(socket.getInputStream());
/*  27:154 */       this.dos = new DataOutputStream(socket.getOutputStream());
/*  28:    */     }
/*  29:    */     catch (InterruptedIOException e)
/*  30:    */     {
/*  31:156 */       Thread.currentThread().interrupt();
/*  32:157 */       e.printStackTrace();
/*  33:    */     }
/*  34:    */     catch (IOException e)
/*  35:    */     {
/*  36:159 */       e.printStackTrace();
/*  37:    */     }
/*  38:    */     catch (RuntimeException e)
/*  39:    */     {
/*  40:161 */       e.printStackTrace();
/*  41:    */     }
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void run()
/*  45:    */   {
/*  46:    */     try
/*  47:    */     {
/*  48:167 */       String line = this.dis.readUTF();
/*  49:168 */       LogLog.debug("Got external roll over signal.");
/*  50:169 */       if ("RollOver".equals(line))
/*  51:    */       {
/*  52:170 */         synchronized (this.er)
/*  53:    */         {
/*  54:171 */           this.er.rollOver();
/*  55:    */         }
/*  56:173 */         this.dos.writeUTF("OK");
/*  57:    */       }
/*  58:    */       else
/*  59:    */       {
/*  60:176 */         this.dos.writeUTF("Expecting [RollOver] string.");
/*  61:    */       }
/*  62:178 */       this.dos.close();
/*  63:    */     }
/*  64:    */     catch (InterruptedIOException e)
/*  65:    */     {
/*  66:180 */       Thread.currentThread().interrupt();
/*  67:181 */       LogLog.error("Unexpected exception. Exiting HUPNode.", e);
/*  68:    */     }
/*  69:    */     catch (IOException e)
/*  70:    */     {
/*  71:183 */       LogLog.error("Unexpected exception. Exiting HUPNode.", e);
/*  72:    */     }
/*  73:    */     catch (RuntimeException e)
/*  74:    */     {
/*  75:185 */       LogLog.error("Unexpected exception. Exiting HUPNode.", e);
/*  76:    */     }
/*  77:    */   }
/*  78:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.varia.HUPNode
 * JD-Core Version:    0.7.0.1
 */