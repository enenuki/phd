/*  1:   */ package org.apache.log4j.varia;
/*  2:   */ 
/*  3:   */ import org.apache.log4j.AppenderSkeleton;
/*  4:   */ import org.apache.log4j.spi.LoggingEvent;
/*  5:   */ 
/*  6:   */ public class NullAppender
/*  7:   */   extends AppenderSkeleton
/*  8:   */ {
/*  9:30 */   private static NullAppender instance = new NullAppender();
/* 10:   */   
/* 11:   */   public void activateOptions() {}
/* 12:   */   
/* 13:   */   /**
/* 14:   */    * @deprecated
/* 15:   */    */
/* 16:   */   public NullAppender getInstance()
/* 17:   */   {
/* 18:47 */     return instance;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public static NullAppender getNullAppender()
/* 22:   */   {
/* 23:55 */     return instance;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void close() {}
/* 27:   */   
/* 28:   */   public void doAppend(LoggingEvent event) {}
/* 29:   */   
/* 30:   */   protected void append(LoggingEvent event) {}
/* 31:   */   
/* 32:   */   public boolean requiresLayout()
/* 33:   */   {
/* 34:77 */     return false;
/* 35:   */   }
/* 36:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.varia.NullAppender
 * JD-Core Version:    0.7.0.1
 */