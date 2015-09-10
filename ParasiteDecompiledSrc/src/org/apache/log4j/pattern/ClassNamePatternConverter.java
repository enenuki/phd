/*  1:   */ package org.apache.log4j.pattern;
/*  2:   */ 
/*  3:   */ import org.apache.log4j.spi.LocationInfo;
/*  4:   */ import org.apache.log4j.spi.LoggingEvent;
/*  5:   */ 
/*  6:   */ public final class ClassNamePatternConverter
/*  7:   */   extends NamePatternConverter
/*  8:   */ {
/*  9:   */   private ClassNamePatternConverter(String[] options)
/* 10:   */   {
/* 11:36 */     super("Class Name", "class name", options);
/* 12:   */   }
/* 13:   */   
/* 14:   */   public static ClassNamePatternConverter newInstance(String[] options)
/* 15:   */   {
/* 16:46 */     return new ClassNamePatternConverter(options);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void format(LoggingEvent event, StringBuffer toAppendTo)
/* 20:   */   {
/* 21:55 */     int initialLength = toAppendTo.length();
/* 22:56 */     LocationInfo li = event.getLocationInformation();
/* 23:58 */     if (li == null) {
/* 24:59 */       toAppendTo.append("?");
/* 25:   */     } else {
/* 26:61 */       toAppendTo.append(li.getClassName());
/* 27:   */     }
/* 28:64 */     abbreviate(initialLength, toAppendTo);
/* 29:   */   }
/* 30:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.pattern.ClassNamePatternConverter
 * JD-Core Version:    0.7.0.1
 */