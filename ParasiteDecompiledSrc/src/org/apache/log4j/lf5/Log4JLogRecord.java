/*  1:   */ package org.apache.log4j.lf5;
/*  2:   */ 
/*  3:   */ import org.apache.log4j.spi.ThrowableInformation;
/*  4:   */ 
/*  5:   */ public class Log4JLogRecord
/*  6:   */   extends LogRecord
/*  7:   */ {
/*  8:   */   public boolean isSevereLevel()
/*  9:   */   {
/* 10:67 */     boolean isSevere = false;
/* 11:69 */     if ((LogLevel.ERROR.equals(getLevel())) || (LogLevel.FATAL.equals(getLevel()))) {
/* 12:71 */       isSevere = true;
/* 13:   */     }
/* 14:74 */     return isSevere;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void setThrownStackTrace(ThrowableInformation throwableInfo)
/* 18:   */   {
/* 19:88 */     String[] stackTraceArray = throwableInfo.getThrowableStrRep();
/* 20:   */     
/* 21:90 */     StringBuffer stackTrace = new StringBuffer();
/* 22:93 */     for (int i = 0; i < stackTraceArray.length; i++)
/* 23:   */     {
/* 24:94 */       String nextLine = stackTraceArray[i] + "\n";
/* 25:95 */       stackTrace.append(nextLine);
/* 26:   */     }
/* 27:98 */     this._thrownStackTrace = stackTrace.toString();
/* 28:   */   }
/* 29:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.lf5.Log4JLogRecord
 * JD-Core Version:    0.7.0.1
 */