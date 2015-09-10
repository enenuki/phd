/*  1:   */ package org.apache.log4j.pattern;
/*  2:   */ 
/*  3:   */ public final class FileDatePatternConverter
/*  4:   */ {
/*  5:   */   public static PatternConverter newInstance(String[] options)
/*  6:   */   {
/*  7:41 */     if ((options == null) || (options.length == 0)) {
/*  8:42 */       return DatePatternConverter.newInstance(new String[] { "yyyy-MM-dd" });
/*  9:   */     }
/* 10:48 */     return DatePatternConverter.newInstance(options);
/* 11:   */   }
/* 12:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.pattern.FileDatePatternConverter
 * JD-Core Version:    0.7.0.1
 */