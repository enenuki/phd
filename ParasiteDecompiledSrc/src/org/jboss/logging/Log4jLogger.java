/*  1:   */ package org.jboss.logging;
/*  2:   */ 
/*  3:   */ import java.text.MessageFormat;
/*  4:   */ import org.apache.log4j.Level;
/*  5:   */ 
/*  6:   */ final class Log4jLogger
/*  7:   */   extends Logger
/*  8:   */ {
/*  9:   */   private static final long serialVersionUID = -5446154366955151335L;
/* 10:   */   private final org.apache.log4j.Logger logger;
/* 11:   */   
/* 12:   */   Log4jLogger(String name)
/* 13:   */   {
/* 14:34 */     super(name);
/* 15:35 */     this.logger = org.apache.log4j.Logger.getLogger(name);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public boolean isEnabled(Logger.Level level)
/* 19:   */   {
/* 20:39 */     Level l = translate(level);
/* 21:40 */     return (this.logger.isEnabledFor(l)) && (l.isGreaterOrEqual(this.logger.getEffectiveLevel()));
/* 22:   */   }
/* 23:   */   
/* 24:   */   protected void doLog(Logger.Level level, String loggerClassName, Object message, Object[] parameters, Throwable thrown)
/* 25:   */   {
/* 26:44 */     Level translatedLevel = translate(level);
/* 27:45 */     if (this.logger.isEnabledFor(translatedLevel)) {
/* 28:46 */       this.logger.log(loggerClassName, translatedLevel, (parameters == null) || (parameters.length == 0) ? message : MessageFormat.format(String.valueOf(message), parameters), thrown);
/* 29:   */     }
/* 30:   */   }
/* 31:   */   
/* 32:   */   protected void doLogf(Logger.Level level, String loggerClassName, String format, Object[] parameters, Throwable thrown)
/* 33:   */   {
/* 34:51 */     Level translatedLevel = translate(level);
/* 35:52 */     if (this.logger.isEnabledFor(translatedLevel)) {
/* 36:53 */       this.logger.log(loggerClassName, translatedLevel, parameters == null ? String.format(format, new Object[0]) : String.format(format, parameters), thrown);
/* 37:   */     }
/* 38:   */   }
/* 39:   */   
/* 40:   */   private static Level translate(Logger.Level level)
/* 41:   */   {
/* 42:58 */     if (level != null) {
/* 43:58 */       switch (1.$SwitchMap$org$jboss$logging$Logger$Level[level.ordinal()])
/* 44:   */       {
/* 45:   */       case 1: 
/* 46:59 */         return Level.FATAL;
/* 47:   */       case 2: 
/* 48:60 */         return Level.ERROR;
/* 49:   */       case 3: 
/* 50:61 */         return Level.WARN;
/* 51:   */       case 4: 
/* 52:62 */         return Level.INFO;
/* 53:   */       case 5: 
/* 54:63 */         return Level.DEBUG;
/* 55:   */       case 6: 
/* 56:64 */         return Level.TRACE;
/* 57:   */       }
/* 58:   */     }
/* 59:66 */     return Level.ALL;
/* 60:   */   }
/* 61:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.jboss.logging.Log4jLogger
 * JD-Core Version:    0.7.0.1
 */