/*  1:   */ package org.apache.log4j.pattern;
/*  2:   */ 
/*  3:   */ import org.apache.log4j.spi.LocationInfo;
/*  4:   */ import org.apache.log4j.spi.LoggingEvent;
/*  5:   */ 
/*  6:   */ public final class FullLocationPatternConverter
/*  7:   */   extends LoggingEventPatternConverter
/*  8:   */ {
/*  9:34 */   private static final FullLocationPatternConverter INSTANCE = new FullLocationPatternConverter();
/* 10:   */   
/* 11:   */   private FullLocationPatternConverter()
/* 12:   */   {
/* 13:41 */     super("Full Location", "fullLocation");
/* 14:   */   }
/* 15:   */   
/* 16:   */   public static FullLocationPatternConverter newInstance(String[] options)
/* 17:   */   {
/* 18:51 */     return INSTANCE;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void format(LoggingEvent event, StringBuffer output)
/* 22:   */   {
/* 23:58 */     LocationInfo locationInfo = event.getLocationInformation();
/* 24:60 */     if (locationInfo != null) {
/* 25:61 */       output.append(locationInfo.fullInfo);
/* 26:   */     }
/* 27:   */   }
/* 28:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.pattern.FullLocationPatternConverter
 * JD-Core Version:    0.7.0.1
 */