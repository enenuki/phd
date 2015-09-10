/*  1:   */ package org.apache.log4j.pattern;
/*  2:   */ 
/*  3:   */ import org.apache.log4j.spi.LoggingEvent;
/*  4:   */ 
/*  5:   */ public final class LiteralPatternConverter
/*  6:   */   extends LoggingEventPatternConverter
/*  7:   */ {
/*  8:   */   private final String literal;
/*  9:   */   
/* 10:   */   public LiteralPatternConverter(String literal)
/* 11:   */   {
/* 12:40 */     super("Literal", "literal");
/* 13:41 */     this.literal = literal;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public void format(LoggingEvent event, StringBuffer toAppendTo)
/* 17:   */   {
/* 18:48 */     toAppendTo.append(this.literal);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void format(Object obj, StringBuffer toAppendTo)
/* 22:   */   {
/* 23:55 */     toAppendTo.append(this.literal);
/* 24:   */   }
/* 25:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.pattern.LiteralPatternConverter
 * JD-Core Version:    0.7.0.1
 */