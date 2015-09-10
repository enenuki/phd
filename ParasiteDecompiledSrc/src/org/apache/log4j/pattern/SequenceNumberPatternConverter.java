/*  1:   */ package org.apache.log4j.pattern;
/*  2:   */ 
/*  3:   */ import org.apache.log4j.spi.LoggingEvent;
/*  4:   */ 
/*  5:   */ public class SequenceNumberPatternConverter
/*  6:   */   extends LoggingEventPatternConverter
/*  7:   */ {
/*  8:33 */   private static final SequenceNumberPatternConverter INSTANCE = new SequenceNumberPatternConverter();
/*  9:   */   
/* 10:   */   private SequenceNumberPatternConverter()
/* 11:   */   {
/* 12:40 */     super("Sequence Number", "sn");
/* 13:   */   }
/* 14:   */   
/* 15:   */   public static SequenceNumberPatternConverter newInstance(String[] options)
/* 16:   */   {
/* 17:50 */     return INSTANCE;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void format(LoggingEvent event, StringBuffer toAppendTo)
/* 21:   */   {
/* 22:57 */     toAppendTo.append("0");
/* 23:   */   }
/* 24:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.pattern.SequenceNumberPatternConverter
 * JD-Core Version:    0.7.0.1
 */