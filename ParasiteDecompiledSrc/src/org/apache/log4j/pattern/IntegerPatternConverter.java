/*  1:   */ package org.apache.log4j.pattern;
/*  2:   */ 
/*  3:   */ import java.util.Date;
/*  4:   */ 
/*  5:   */ public final class IntegerPatternConverter
/*  6:   */   extends PatternConverter
/*  7:   */ {
/*  8:32 */   private static final IntegerPatternConverter INSTANCE = new IntegerPatternConverter();
/*  9:   */   
/* 10:   */   private IntegerPatternConverter()
/* 11:   */   {
/* 12:39 */     super("Integer", "integer");
/* 13:   */   }
/* 14:   */   
/* 15:   */   public static IntegerPatternConverter newInstance(String[] options)
/* 16:   */   {
/* 17:49 */     return INSTANCE;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void format(Object obj, StringBuffer toAppendTo)
/* 21:   */   {
/* 22:56 */     if ((obj instanceof Integer)) {
/* 23:57 */       toAppendTo.append(obj.toString());
/* 24:   */     }
/* 25:60 */     if ((obj instanceof Date)) {
/* 26:61 */       toAppendTo.append(Long.toString(((Date)obj).getTime()));
/* 27:   */     }
/* 28:   */   }
/* 29:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.pattern.IntegerPatternConverter
 * JD-Core Version:    0.7.0.1
 */