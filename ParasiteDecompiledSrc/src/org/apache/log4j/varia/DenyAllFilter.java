/*  1:   */ package org.apache.log4j.varia;
/*  2:   */ 
/*  3:   */ import org.apache.log4j.spi.Filter;
/*  4:   */ import org.apache.log4j.spi.LoggingEvent;
/*  5:   */ 
/*  6:   */ public class DenyAllFilter
/*  7:   */   extends Filter
/*  8:   */ {
/*  9:   */   /**
/* 10:   */    * @deprecated
/* 11:   */    */
/* 12:   */   public String[] getOptionStrings()
/* 13:   */   {
/* 14:46 */     return null;
/* 15:   */   }
/* 16:   */   
/* 17:   */   /**
/* 18:   */    * @deprecated
/* 19:   */    */
/* 20:   */   public void setOption(String key, String value) {}
/* 21:   */   
/* 22:   */   public int decide(LoggingEvent event)
/* 23:   */   {
/* 24:69 */     return -1;
/* 25:   */   }
/* 26:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.varia.DenyAllFilter
 * JD-Core Version:    0.7.0.1
 */