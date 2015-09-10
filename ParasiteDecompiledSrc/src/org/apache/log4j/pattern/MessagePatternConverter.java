/*  1:   */ package org.apache.log4j.pattern;
/*  2:   */ 
/*  3:   */ import org.apache.log4j.spi.LoggingEvent;
/*  4:   */ 
/*  5:   */ public final class MessagePatternConverter
/*  6:   */   extends LoggingEventPatternConverter
/*  7:   */ {
/*  8:32 */   private static final MessagePatternConverter INSTANCE = new MessagePatternConverter();
/*  9:   */   
/* 10:   */   private MessagePatternConverter()
/* 11:   */   {
/* 12:39 */     super("Message", "message");
/* 13:   */   }
/* 14:   */   
/* 15:   */   public static MessagePatternConverter newInstance(String[] options)
/* 16:   */   {
/* 17:49 */     return INSTANCE;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void format(LoggingEvent event, StringBuffer toAppendTo)
/* 21:   */   {
/* 22:56 */     toAppendTo.append(event.getRenderedMessage());
/* 23:   */   }
/* 24:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.pattern.MessagePatternConverter
 * JD-Core Version:    0.7.0.1
 */