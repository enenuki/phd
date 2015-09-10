/*  1:   */ package org.jboss.logging;
/*  2:   */ 
/*  3:   */ import org.jboss.logmanager.ExtLogRecord.FormatStyle;
/*  4:   */ 
/*  5:   */ final class JBossLogManagerLogger
/*  6:   */   extends Logger
/*  7:   */ {
/*  8:   */   private static final long serialVersionUID = 7429618317727584742L;
/*  9:   */   private final org.jboss.logmanager.Logger logger;
/* 10:   */   
/* 11:   */   JBossLogManagerLogger(String name, org.jboss.logmanager.Logger logger)
/* 12:   */   {
/* 13:34 */     super(name);
/* 14:35 */     this.logger = logger;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public boolean isEnabled(Logger.Level level)
/* 18:   */   {
/* 19:39 */     return this.logger.isLoggable(translate(level));
/* 20:   */   }
/* 21:   */   
/* 22:   */   protected void doLog(Logger.Level level, String loggerClassName, Object message, Object[] parameters, Throwable thrown)
/* 23:   */   {
/* 24:43 */     if (parameters == null) {
/* 25:44 */       this.logger.log(loggerClassName, translate(level), String.valueOf(message), thrown);
/* 26:   */     } else {
/* 27:46 */       this.logger.log(loggerClassName, translate(level), String.valueOf(message), ExtLogRecord.FormatStyle.MESSAGE_FORMAT, parameters, thrown);
/* 28:   */     }
/* 29:   */   }
/* 30:   */   
/* 31:   */   protected void doLogf(Logger.Level level, String loggerClassName, String format, Object[] parameters, Throwable thrown)
/* 32:   */   {
/* 33:51 */     if (parameters == null) {
/* 34:52 */       this.logger.log(loggerClassName, translate(level), format, thrown);
/* 35:   */     } else {
/* 36:54 */       this.logger.log(loggerClassName, translate(level), format, ExtLogRecord.FormatStyle.PRINTF, parameters, thrown);
/* 37:   */     }
/* 38:   */   }
/* 39:   */   
/* 40:   */   private static java.util.logging.Level translate(Logger.Level level)
/* 41:   */   {
/* 42:59 */     if (level != null) {
/* 43:59 */       switch (1.$SwitchMap$org$jboss$logging$Logger$Level[level.ordinal()])
/* 44:   */       {
/* 45:   */       case 1: 
/* 46:60 */         return org.jboss.logmanager.Level.FATAL;
/* 47:   */       case 2: 
/* 48:61 */         return org.jboss.logmanager.Level.ERROR;
/* 49:   */       case 3: 
/* 50:62 */         return org.jboss.logmanager.Level.WARN;
/* 51:   */       case 4: 
/* 52:63 */         return org.jboss.logmanager.Level.INFO;
/* 53:   */       case 5: 
/* 54:64 */         return org.jboss.logmanager.Level.DEBUG;
/* 55:   */       case 6: 
/* 56:65 */         return org.jboss.logmanager.Level.TRACE;
/* 57:   */       }
/* 58:   */     }
/* 59:67 */     return org.jboss.logmanager.Level.ALL;
/* 60:   */   }
/* 61:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.jboss.logging.JBossLogManagerLogger
 * JD-Core Version:    0.7.0.1
 */