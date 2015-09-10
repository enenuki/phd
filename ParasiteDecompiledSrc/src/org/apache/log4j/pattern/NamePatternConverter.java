/*  1:   */ package org.apache.log4j.pattern;
/*  2:   */ 
/*  3:   */ public abstract class NamePatternConverter
/*  4:   */   extends LoggingEventPatternConverter
/*  5:   */ {
/*  6:   */   private final NameAbbreviator abbreviator;
/*  7:   */   
/*  8:   */   protected NamePatternConverter(String name, String style, String[] options)
/*  9:   */   {
/* 10:44 */     super(name, style);
/* 11:46 */     if ((options != null) && (options.length > 0)) {
/* 12:47 */       this.abbreviator = NameAbbreviator.getAbbreviator(options[0]);
/* 13:   */     } else {
/* 14:49 */       this.abbreviator = NameAbbreviator.getDefaultAbbreviator();
/* 15:   */     }
/* 16:   */   }
/* 17:   */   
/* 18:   */   protected final void abbreviate(int nameStart, StringBuffer buf)
/* 19:   */   {
/* 20:59 */     this.abbreviator.abbreviate(nameStart, buf);
/* 21:   */   }
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.pattern.NamePatternConverter
 * JD-Core Version:    0.7.0.1
 */