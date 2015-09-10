/*   1:    */ package org.apache.log4j;
/*   2:    */ 
/*   3:    */ import org.apache.log4j.helpers.AppenderAttachableImpl;
/*   4:    */ import org.apache.log4j.helpers.BoundedFIFO;
/*   5:    */ import org.apache.log4j.spi.LoggingEvent;
/*   6:    */ 
/*   7:    */ /**
/*   8:    */  * @deprecated
/*   9:    */  */
/*  10:    */ class Dispatcher
/*  11:    */   extends Thread
/*  12:    */ {
/*  13:    */   /**
/*  14:    */    * @deprecated
/*  15:    */    */
/*  16:    */   private BoundedFIFO bf;
/*  17:    */   private AppenderAttachableImpl aai;
/*  18: 35 */   private boolean interrupted = false;
/*  19:    */   AsyncAppender container;
/*  20:    */   
/*  21:    */   /**
/*  22:    */    * @deprecated
/*  23:    */    */
/*  24:    */   Dispatcher(BoundedFIFO bf, AsyncAppender container)
/*  25:    */   {
/*  26: 45 */     this.bf = bf;
/*  27: 46 */     this.container = container;
/*  28: 47 */     this.aai = container.aai;
/*  29:    */     
/*  30:    */ 
/*  31:    */ 
/*  32: 51 */     setDaemon(true);
/*  33:    */     
/*  34:    */ 
/*  35: 54 */     setPriority(1);
/*  36: 55 */     setName("Dispatcher-" + getName());
/*  37:    */   }
/*  38:    */   
/*  39:    */   void close()
/*  40:    */   {
/*  41: 63 */     synchronized (this.bf)
/*  42:    */     {
/*  43: 64 */       this.interrupted = true;
/*  44: 68 */       if (this.bf.length() == 0) {
/*  45: 69 */         this.bf.notify();
/*  46:    */       }
/*  47:    */     }
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void run()
/*  51:    */   {
/*  52:    */     for (;;)
/*  53:    */     {
/*  54:    */       LoggingEvent event;
/*  55: 89 */       synchronized (this.bf)
/*  56:    */       {
/*  57: 90 */         if (this.bf.length() == 0)
/*  58:    */         {
/*  59: 92 */           if (this.interrupted) {
/*  60:    */             break;
/*  61:    */           }
/*  62:    */           try
/*  63:    */           {
/*  64: 99 */             this.bf.wait();
/*  65:    */           }
/*  66:    */           catch (InterruptedException e)
/*  67:    */           {
/*  68:    */             break;
/*  69:    */           }
/*  70:    */         }
/*  71:105 */         event = this.bf.get();
/*  72:107 */         if (this.bf.wasFull()) {
/*  73:109 */           this.bf.notify();
/*  74:    */         }
/*  75:    */       }
/*  76:114 */       synchronized (this.container.aai)
/*  77:    */       {
/*  78:115 */         if ((this.aai != null) && (event != null)) {
/*  79:116 */           this.aai.appendLoopOnAppenders(event);
/*  80:    */         }
/*  81:    */       }
/*  82:    */     }
/*  83:123 */     this.aai.removeAllAppenders();
/*  84:    */   }
/*  85:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.Dispatcher
 * JD-Core Version:    0.7.0.1
 */