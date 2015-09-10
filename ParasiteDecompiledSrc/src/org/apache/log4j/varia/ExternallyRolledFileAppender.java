/*   1:    */ package org.apache.log4j.varia;
/*   2:    */ 
/*   3:    */ import org.apache.log4j.FileAppender;
/*   4:    */ import org.apache.log4j.RollingFileAppender;
/*   5:    */ 
/*   6:    */ public class ExternallyRolledFileAppender
/*   7:    */   extends RollingFileAppender
/*   8:    */ {
/*   9:    */   public static final String ROLL_OVER = "RollOver";
/*  10:    */   public static final String OK = "OK";
/*  11: 64 */   int port = 0;
/*  12:    */   HUP hup;
/*  13:    */   
/*  14:    */   public void setPort(int port)
/*  15:    */   {
/*  16: 80 */     this.port = port;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public int getPort()
/*  20:    */   {
/*  21: 88 */     return this.port;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public void activateOptions()
/*  25:    */   {
/*  26: 96 */     super.activateOptions();
/*  27: 97 */     if (this.port != 0)
/*  28:    */     {
/*  29: 98 */       if (this.hup != null) {
/*  30: 99 */         this.hup.interrupt();
/*  31:    */       }
/*  32:101 */       this.hup = new HUP(this, this.port);
/*  33:102 */       this.hup.setDaemon(true);
/*  34:103 */       this.hup.start();
/*  35:    */     }
/*  36:    */   }
/*  37:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.varia.ExternallyRolledFileAppender
 * JD-Core Version:    0.7.0.1
 */