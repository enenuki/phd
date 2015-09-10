/*  1:   */ package org.apache.log4j.helpers;
/*  2:   */ 
/*  3:   */ import java.io.InterruptedIOException;
/*  4:   */ import org.apache.log4j.Appender;
/*  5:   */ import org.apache.log4j.Logger;
/*  6:   */ import org.apache.log4j.spi.ErrorHandler;
/*  7:   */ import org.apache.log4j.spi.LoggingEvent;
/*  8:   */ 
/*  9:   */ public class OnlyOnceErrorHandler
/* 10:   */   implements ErrorHandler
/* 11:   */ {
/* 12:43 */   final String WARN_PREFIX = "log4j warning: ";
/* 13:44 */   final String ERROR_PREFIX = "log4j error: ";
/* 14:46 */   boolean firstTime = true;
/* 15:   */   
/* 16:   */   public void setLogger(Logger logger) {}
/* 17:   */   
/* 18:   */   public void activateOptions() {}
/* 19:   */   
/* 20:   */   public void error(String message, Exception e, int errorCode)
/* 21:   */   {
/* 22:70 */     error(message, e, errorCode, null);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void error(String message, Exception e, int errorCode, LoggingEvent event)
/* 26:   */   {
/* 27:79 */     if (((e instanceof InterruptedIOException)) || ((e instanceof InterruptedException))) {
/* 28:80 */       Thread.currentThread().interrupt();
/* 29:   */     }
/* 30:82 */     if (this.firstTime)
/* 31:   */     {
/* 32:83 */       LogLog.error(message, e);
/* 33:84 */       this.firstTime = false;
/* 34:   */     }
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void error(String message)
/* 38:   */   {
/* 39:95 */     if (this.firstTime)
/* 40:   */     {
/* 41:96 */       LogLog.error(message);
/* 42:97 */       this.firstTime = false;
/* 43:   */     }
/* 44:   */   }
/* 45:   */   
/* 46:   */   public void setAppender(Appender appender) {}
/* 47:   */   
/* 48:   */   public void setBackupAppender(Appender appender) {}
/* 49:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.helpers.OnlyOnceErrorHandler
 * JD-Core Version:    0.7.0.1
 */