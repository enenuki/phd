/*   1:    */ package org.apache.log4j.spi;
/*   2:    */ 
/*   3:    */ public abstract class Filter
/*   4:    */   implements OptionHandler
/*   5:    */ {
/*   6:    */   /**
/*   7:    */    * @deprecated
/*   8:    */    */
/*   9:    */   public Filter next;
/*  10:    */   public static final int DENY = -1;
/*  11:    */   public static final int NEUTRAL = 0;
/*  12:    */   public static final int ACCEPT = 1;
/*  13:    */   
/*  14:    */   public void activateOptions() {}
/*  15:    */   
/*  16:    */   public abstract int decide(LoggingEvent paramLoggingEvent);
/*  17:    */   
/*  18:    */   public void setNext(Filter next)
/*  19:    */   {
/*  20:114 */     this.next = next;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public Filter getNext()
/*  24:    */   {
/*  25:121 */     return this.next;
/*  26:    */   }
/*  27:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.spi.Filter
 * JD-Core Version:    0.7.0.1
 */