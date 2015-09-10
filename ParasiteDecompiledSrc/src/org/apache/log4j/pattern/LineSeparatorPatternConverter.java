/*  1:   */ package org.apache.log4j.pattern;
/*  2:   */ 
/*  3:   */ import org.apache.log4j.Layout;
/*  4:   */ import org.apache.log4j.spi.LoggingEvent;
/*  5:   */ 
/*  6:   */ public final class LineSeparatorPatternConverter
/*  7:   */   extends LoggingEventPatternConverter
/*  8:   */ {
/*  9:34 */   private static final LineSeparatorPatternConverter INSTANCE = new LineSeparatorPatternConverter();
/* 10:   */   private final String lineSep;
/* 11:   */   
/* 12:   */   private LineSeparatorPatternConverter()
/* 13:   */   {
/* 14:46 */     super("Line Sep", "lineSep");
/* 15:47 */     this.lineSep = Layout.LINE_SEP;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public static LineSeparatorPatternConverter newInstance(String[] options)
/* 19:   */   {
/* 20:57 */     return INSTANCE;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void format(LoggingEvent event, StringBuffer toAppendTo)
/* 24:   */   {
/* 25:64 */     toAppendTo.append(this.lineSep);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void format(Object obj, StringBuffer toAppendTo)
/* 29:   */   {
/* 30:71 */     toAppendTo.append(this.lineSep);
/* 31:   */   }
/* 32:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.pattern.LineSeparatorPatternConverter
 * JD-Core Version:    0.7.0.1
 */