/*   1:    */ package org.apache.log4j.varia;
/*   2:    */ 
/*   3:    */ import java.io.InterruptedIOException;
/*   4:    */ import java.util.Vector;
/*   5:    */ import org.apache.log4j.Appender;
/*   6:    */ import org.apache.log4j.Category;
/*   7:    */ import org.apache.log4j.Logger;
/*   8:    */ import org.apache.log4j.helpers.LogLog;
/*   9:    */ import org.apache.log4j.spi.ErrorHandler;
/*  10:    */ import org.apache.log4j.spi.LoggingEvent;
/*  11:    */ 
/*  12:    */ public class FallbackErrorHandler
/*  13:    */   implements ErrorHandler
/*  14:    */ {
/*  15:    */   Appender backup;
/*  16:    */   Appender primary;
/*  17:    */   Vector loggers;
/*  18:    */   
/*  19:    */   public void setLogger(Logger logger)
/*  20:    */   {
/*  21: 57 */     LogLog.debug("FB: Adding logger [" + logger.getName() + "].");
/*  22: 58 */     if (this.loggers == null) {
/*  23: 59 */       this.loggers = new Vector();
/*  24:    */     }
/*  25: 61 */     this.loggers.addElement(logger);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void activateOptions() {}
/*  29:    */   
/*  30:    */   public void error(String message, Exception e, int errorCode)
/*  31:    */   {
/*  32: 78 */     error(message, e, errorCode, null);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void error(String message, Exception e, int errorCode, LoggingEvent event)
/*  36:    */   {
/*  37: 87 */     if ((e instanceof InterruptedIOException)) {
/*  38: 88 */       Thread.currentThread().interrupt();
/*  39:    */     }
/*  40: 90 */     LogLog.debug("FB: The following error reported: " + message, e);
/*  41: 91 */     LogLog.debug("FB: INITIATING FALLBACK PROCEDURE.");
/*  42: 92 */     if (this.loggers != null) {
/*  43: 93 */       for (int i = 0; i < this.loggers.size(); i++)
/*  44:    */       {
/*  45: 94 */         Logger l = (Logger)this.loggers.elementAt(i);
/*  46: 95 */         LogLog.debug("FB: Searching for [" + this.primary.getName() + "] in logger [" + l.getName() + "].");
/*  47:    */         
/*  48: 97 */         LogLog.debug("FB: Replacing [" + this.primary.getName() + "] by [" + this.backup.getName() + "] in logger [" + l.getName() + "].");
/*  49:    */         
/*  50: 99 */         l.removeAppender(this.primary);
/*  51:100 */         LogLog.debug("FB: Adding appender [" + this.backup.getName() + "] to logger " + l.getName());
/*  52:    */         
/*  53:102 */         l.addAppender(this.backup);
/*  54:    */       }
/*  55:    */     }
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void error(String message) {}
/*  59:    */   
/*  60:    */   public void setAppender(Appender primary)
/*  61:    */   {
/*  62:125 */     LogLog.debug("FB: Setting primary appender to [" + primary.getName() + "].");
/*  63:126 */     this.primary = primary;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void setBackupAppender(Appender backup)
/*  67:    */   {
/*  68:134 */     LogLog.debug("FB: Setting backup appender to [" + backup.getName() + "].");
/*  69:135 */     this.backup = backup;
/*  70:    */   }
/*  71:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.varia.FallbackErrorHandler
 * JD-Core Version:    0.7.0.1
 */