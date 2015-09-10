/*  1:   */ package org.apache.log4j;
/*  2:   */ 
/*  3:   */ public class BasicConfigurator
/*  4:   */ {
/*  5:   */   public static void configure()
/*  6:   */   {
/*  7:46 */     Logger root = Logger.getRootLogger();
/*  8:47 */     root.addAppender(new ConsoleAppender(new PatternLayout("%r [%t] %p %c %x - %m%n")));
/*  9:   */   }
/* 10:   */   
/* 11:   */   public static void configure(Appender appender)
/* 12:   */   {
/* 13:58 */     Logger root = Logger.getRootLogger();
/* 14:59 */     root.addAppender(appender);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public static void resetConfiguration() {}
/* 18:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.BasicConfigurator
 * JD-Core Version:    0.7.0.1
 */