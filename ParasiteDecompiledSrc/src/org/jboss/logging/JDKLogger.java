/*  1:   */ package org.jboss.logging;
/*  2:   */ 
/*  3:   */ import java.util.MissingResourceException;
/*  4:   */ import java.util.ResourceBundle;
/*  5:   */ import java.util.logging.Level;
/*  6:   */ 
/*  7:   */ final class JDKLogger
/*  8:   */   extends Logger
/*  9:   */ {
/* 10:   */   private static final long serialVersionUID = 2563174097983721393L;
/* 11:   */   private final transient java.util.logging.Logger logger;
/* 12:   */   
/* 13:   */   public JDKLogger(String name)
/* 14:   */   {
/* 15:36 */     super(name);
/* 16:37 */     this.logger = java.util.logging.Logger.getLogger(name);
/* 17:   */   }
/* 18:   */   
/* 19:   */   protected void doLog(Logger.Level level, String loggerClassName, Object message, Object[] parameters, Throwable thrown)
/* 20:   */   {
/* 21:41 */     if (isEnabled(level))
/* 22:   */     {
/* 23:42 */       JBossLogRecord rec = new JBossLogRecord(translate(level), String.valueOf(message), loggerClassName);
/* 24:43 */       if (thrown != null) {
/* 25:43 */         rec.setThrown(thrown);
/* 26:   */       }
/* 27:44 */       rec.setLoggerName(getName());
/* 28:45 */       rec.setParameters(parameters);
/* 29:46 */       rec.setResourceBundleName(this.logger.getResourceBundleName());
/* 30:47 */       rec.setResourceBundle(this.logger.getResourceBundle());
/* 31:48 */       this.logger.log(rec);
/* 32:   */     }
/* 33:   */   }
/* 34:   */   
/* 35:   */   protected void doLogf(Logger.Level level, String loggerClassName, String format, Object[] parameters, Throwable thrown)
/* 36:   */   {
/* 37:53 */     if (isEnabled(level))
/* 38:   */     {
/* 39:54 */       ResourceBundle resourceBundle = this.logger.getResourceBundle();
/* 40:55 */       if (resourceBundle != null) {
/* 41:   */         try
/* 42:   */         {
/* 43:56 */           format = resourceBundle.getString(format);
/* 44:   */         }
/* 45:   */         catch (MissingResourceException e) {}
/* 46:   */       }
/* 47:60 */       String msg = parameters == null ? String.format(format, new Object[0]) : String.format(format, parameters);
/* 48:61 */       JBossLogRecord rec = new JBossLogRecord(translate(level), msg, loggerClassName);
/* 49:62 */       if (thrown != null) {
/* 50:62 */         rec.setThrown(thrown);
/* 51:   */       }
/* 52:63 */       rec.setLoggerName(getName());
/* 53:64 */       rec.setResourceBundleName(this.logger.getResourceBundleName());
/* 54:   */       
/* 55:66 */       rec.setResourceBundle(null);
/* 56:67 */       rec.setParameters(null);
/* 57:68 */       this.logger.log(rec);
/* 58:   */     }
/* 59:   */   }
/* 60:   */   
/* 61:   */   private static Level translate(Logger.Level level)
/* 62:   */   {
/* 63:73 */     if (level != null) {
/* 64:73 */       switch (1.$SwitchMap$org$jboss$logging$Logger$Level[level.ordinal()])
/* 65:   */       {
/* 66:   */       case 1: 
/* 67:74 */         return JDKLevel.FATAL;
/* 68:   */       case 2: 
/* 69:75 */         return JDKLevel.ERROR;
/* 70:   */       case 3: 
/* 71:76 */         return JDKLevel.WARN;
/* 72:   */       case 4: 
/* 73:77 */         return JDKLevel.INFO;
/* 74:   */       case 5: 
/* 75:78 */         return JDKLevel.DEBUG;
/* 76:   */       case 6: 
/* 77:79 */         return JDKLevel.TRACE;
/* 78:   */       }
/* 79:   */     }
/* 80:81 */     return JDKLevel.ALL;
/* 81:   */   }
/* 82:   */   
/* 83:   */   public boolean isEnabled(Logger.Level level)
/* 84:   */   {
/* 85:85 */     return this.logger.isLoggable(translate(level));
/* 86:   */   }
/* 87:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.jboss.logging.JDKLogger
 * JD-Core Version:    0.7.0.1
 */