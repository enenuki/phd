/*   1:    */ package org.apache.log4j.net;
/*   2:    */ 
/*   3:    */ import org.apache.log4j.Level;
/*   4:    */ import org.apache.log4j.Priority;
/*   5:    */ import org.apache.log4j.spi.LoggingEvent;
/*   6:    */ import org.apache.log4j.spi.TriggeringEventEvaluator;
/*   7:    */ 
/*   8:    */ class DefaultEvaluator
/*   9:    */   implements TriggeringEventEvaluator
/*  10:    */ {
/*  11:    */   public boolean isTriggeringEvent(LoggingEvent event)
/*  12:    */   {
/*  13:785 */     return event.getLevel().isGreaterOrEqual(Level.ERROR);
/*  14:    */   }
/*  15:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.net.DefaultEvaluator
 * JD-Core Version:    0.7.0.1
 */