/*  1:   */ package org.apache.log4j.pattern;
/*  2:   */ 
/*  3:   */ import org.apache.log4j.spi.LoggingEvent;
/*  4:   */ 
/*  5:   */ public abstract class LoggingEventPatternConverter
/*  6:   */   extends PatternConverter
/*  7:   */ {
/*  8:   */   protected LoggingEventPatternConverter(String name, String style)
/*  9:   */   {
/* 10:38 */     super(name, style);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public abstract void format(LoggingEvent paramLoggingEvent, StringBuffer paramStringBuffer);
/* 14:   */   
/* 15:   */   public void format(Object obj, StringBuffer output)
/* 16:   */   {
/* 17:53 */     if ((obj instanceof LoggingEvent)) {
/* 18:54 */       format((LoggingEvent)obj, output);
/* 19:   */     }
/* 20:   */   }
/* 21:   */   
/* 22:   */   public boolean handlesThrowable()
/* 23:   */   {
/* 24:68 */     return false;
/* 25:   */   }
/* 26:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.pattern.LoggingEventPatternConverter
 * JD-Core Version:    0.7.0.1
 */