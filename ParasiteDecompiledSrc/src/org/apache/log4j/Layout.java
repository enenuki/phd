/*  1:   */ package org.apache.log4j;
/*  2:   */ 
/*  3:   */ import org.apache.log4j.spi.LoggingEvent;
/*  4:   */ import org.apache.log4j.spi.OptionHandler;
/*  5:   */ 
/*  6:   */ public abstract class Layout
/*  7:   */   implements OptionHandler
/*  8:   */ {
/*  9:34 */   public static final String LINE_SEP = System.getProperty("line.separator");
/* 10:35 */   public static final int LINE_SEP_LEN = LINE_SEP.length();
/* 11:   */   
/* 12:   */   public abstract String format(LoggingEvent paramLoggingEvent);
/* 13:   */   
/* 14:   */   public String getContentType()
/* 15:   */   {
/* 16:51 */     return "text/plain";
/* 17:   */   }
/* 18:   */   
/* 19:   */   public String getHeader()
/* 20:   */   {
/* 21:59 */     return null;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public String getFooter()
/* 25:   */   {
/* 26:67 */     return null;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public abstract boolean ignoresThrowable();
/* 30:   */   
/* 31:   */   public abstract void activateOptions();
/* 32:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.Layout
 * JD-Core Version:    0.7.0.1
 */