/*  1:   */ package org.apache.log4j.pattern;
/*  2:   */ 
/*  3:   */ public abstract class PatternConverter
/*  4:   */ {
/*  5:   */   private final String name;
/*  6:   */   private final String style;
/*  7:   */   
/*  8:   */   protected PatternConverter(String name, String style)
/*  9:   */   {
/* 10:53 */     this.name = name;
/* 11:54 */     this.style = style;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public abstract void format(Object paramObject, StringBuffer paramStringBuffer);
/* 15:   */   
/* 16:   */   public final String getName()
/* 17:   */   {
/* 18:72 */     return this.name;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public String getStyleClass(Object e)
/* 22:   */   {
/* 23:85 */     return this.style;
/* 24:   */   }
/* 25:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.pattern.PatternConverter
 * JD-Core Version:    0.7.0.1
 */