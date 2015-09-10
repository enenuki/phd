/*  1:   */ package org.apache.commons.logging;
/*  2:   */ 
/*  3:   */ public class LogConfigurationException
/*  4:   */   extends RuntimeException
/*  5:   */ {
/*  6:   */   public LogConfigurationException() {}
/*  7:   */   
/*  8:   */   public LogConfigurationException(String message)
/*  9:   */   {
/* 10:50 */     super(message);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public LogConfigurationException(Throwable cause)
/* 14:   */   {
/* 15:63 */     this(cause == null ? null : cause.toString(), cause);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public LogConfigurationException(String message, Throwable cause)
/* 19:   */   {
/* 20:76 */     super(message + " (Caused by " + cause + ")");
/* 21:77 */     this.cause = cause;
/* 22:   */   }
/* 23:   */   
/* 24:85 */   protected Throwable cause = null;
/* 25:   */   
/* 26:   */   public Throwable getCause()
/* 27:   */   {
/* 28:93 */     return this.cause;
/* 29:   */   }
/* 30:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.logging.LogConfigurationException
 * JD-Core Version:    0.7.0.1
 */