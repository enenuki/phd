/*  1:   */ package org.apache.log4j.pattern;
/*  2:   */ 
/*  3:   */ import org.apache.log4j.spi.LoggingEvent;
/*  4:   */ 
/*  5:   */ public final class LoggerPatternConverter
/*  6:   */   extends NamePatternConverter
/*  7:   */ {
/*  8:33 */   private static final LoggerPatternConverter INSTANCE = new LoggerPatternConverter(null);
/*  9:   */   
/* 10:   */   private LoggerPatternConverter(String[] options)
/* 11:   */   {
/* 12:41 */     super("Logger", "logger", options);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public static LoggerPatternConverter newInstance(String[] options)
/* 16:   */   {
/* 17:51 */     if ((options == null) || (options.length == 0)) {
/* 18:52 */       return INSTANCE;
/* 19:   */     }
/* 20:55 */     return new LoggerPatternConverter(options);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void format(LoggingEvent event, StringBuffer toAppendTo)
/* 24:   */   {
/* 25:62 */     int initialLength = toAppendTo.length();
/* 26:63 */     toAppendTo.append(event.getLoggerName());
/* 27:64 */     abbreviate(initialLength, toAppendTo);
/* 28:   */   }
/* 29:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.pattern.LoggerPatternConverter
 * JD-Core Version:    0.7.0.1
 */