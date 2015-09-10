/*  1:   */ package org.apache.log4j;
/*  2:   */ 
/*  3:   */ import org.apache.log4j.spi.LoggerFactory;
/*  4:   */ 
/*  5:   */ class DefaultCategoryFactory
/*  6:   */   implements LoggerFactory
/*  7:   */ {
/*  8:   */   public Logger makeNewLoggerInstance(String name)
/*  9:   */   {
/* 10:29 */     return new Logger(name);
/* 11:   */   }
/* 12:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.DefaultCategoryFactory
 * JD-Core Version:    0.7.0.1
 */