/*  1:   */ package org.apache.log4j.pattern;
/*  2:   */ 
/*  3:   */ import org.apache.log4j.helpers.PatternConverter;
/*  4:   */ import org.apache.log4j.helpers.PatternParser;
/*  5:   */ 
/*  6:   */ public final class BridgePatternParser
/*  7:   */   extends PatternParser
/*  8:   */ {
/*  9:   */   public BridgePatternParser(String conversionPattern)
/* 10:   */   {
/* 11:38 */     super(conversionPattern);
/* 12:   */   }
/* 13:   */   
/* 14:   */   public PatternConverter parse()
/* 15:   */   {
/* 16:46 */     return new BridgePatternConverter(this.pattern);
/* 17:   */   }
/* 18:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.pattern.BridgePatternParser
 * JD-Core Version:    0.7.0.1
 */