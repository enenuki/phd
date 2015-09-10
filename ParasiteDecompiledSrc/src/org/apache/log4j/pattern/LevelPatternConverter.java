/*  1:   */ package org.apache.log4j.pattern;
/*  2:   */ 
/*  3:   */ import org.apache.log4j.Priority;
/*  4:   */ import org.apache.log4j.spi.LoggingEvent;
/*  5:   */ 
/*  6:   */ public final class LevelPatternConverter
/*  7:   */   extends LoggingEventPatternConverter
/*  8:   */ {
/*  9:   */   private static final int TRACE_INT = 5000;
/* 10:38 */   private static final LevelPatternConverter INSTANCE = new LevelPatternConverter();
/* 11:   */   
/* 12:   */   private LevelPatternConverter()
/* 13:   */   {
/* 14:45 */     super("Level", "level");
/* 15:   */   }
/* 16:   */   
/* 17:   */   public static LevelPatternConverter newInstance(String[] options)
/* 18:   */   {
/* 19:55 */     return INSTANCE;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void format(LoggingEvent event, StringBuffer output)
/* 23:   */   {
/* 24:62 */     output.append(event.getLevel().toString());
/* 25:   */   }
/* 26:   */   
/* 27:   */   public String getStyleClass(Object e)
/* 28:   */   {
/* 29:69 */     if ((e instanceof LoggingEvent))
/* 30:   */     {
/* 31:70 */       int lint = ((LoggingEvent)e).getLevel().toInt();
/* 32:72 */       switch (lint)
/* 33:   */       {
/* 34:   */       case 5000: 
/* 35:74 */         return "level trace";
/* 36:   */       case 10000: 
/* 37:77 */         return "level debug";
/* 38:   */       case 20000: 
/* 39:80 */         return "level info";
/* 40:   */       case 30000: 
/* 41:83 */         return "level warn";
/* 42:   */       case 40000: 
/* 43:86 */         return "level error";
/* 44:   */       case 50000: 
/* 45:89 */         return "level fatal";
/* 46:   */       }
/* 47:92 */       return "level " + ((LoggingEvent)e).getLevel().toString();
/* 48:   */     }
/* 49:96 */     return "level";
/* 50:   */   }
/* 51:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.pattern.LevelPatternConverter
 * JD-Core Version:    0.7.0.1
 */