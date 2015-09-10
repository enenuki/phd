/*  1:   */ package org.jboss.logging;
/*  2:   */ 
/*  3:   */ final class JDKLoggerProvider
/*  4:   */   extends AbstractMdcLoggerProvider
/*  5:   */   implements LoggerProvider
/*  6:   */ {
/*  7:   */   public Logger getLogger(String name)
/*  8:   */   {
/*  9:28 */     return new JDKLogger(name);
/* 10:   */   }
/* 11:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.jboss.logging.JDKLoggerProvider
 * JD-Core Version:    0.7.0.1
 */