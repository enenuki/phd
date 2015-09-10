/*  1:   */ package org.jboss.logging;
/*  2:   */ 
/*  3:   */ import java.text.MessageFormat;
/*  4:   */ 
/*  5:   */ final class Slf4jLogger
/*  6:   */   extends Logger
/*  7:   */ {
/*  8:   */   private static final long serialVersionUID = 8685757928087758380L;
/*  9:   */   private final org.slf4j.Logger logger;
/* 10:   */   
/* 11:   */   Slf4jLogger(String name, org.slf4j.Logger logger)
/* 12:   */   {
/* 13:34 */     super(name);
/* 14:35 */     this.logger = logger;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public boolean isEnabled(Logger.Level level)
/* 18:   */   {
/* 19:39 */     if (level != null) {
/* 20:39 */       switch (1.$SwitchMap$org$jboss$logging$Logger$Level[level.ordinal()])
/* 21:   */       {
/* 22:   */       case 1: 
/* 23:40 */         return this.logger.isErrorEnabled();
/* 24:   */       case 2: 
/* 25:41 */         return this.logger.isErrorEnabled();
/* 26:   */       case 3: 
/* 27:42 */         return this.logger.isWarnEnabled();
/* 28:   */       case 4: 
/* 29:43 */         return this.logger.isInfoEnabled();
/* 30:   */       case 5: 
/* 31:44 */         return this.logger.isDebugEnabled();
/* 32:   */       case 6: 
/* 33:45 */         return this.logger.isTraceEnabled();
/* 34:   */       }
/* 35:   */     }
/* 36:47 */     return true;
/* 37:   */   }
/* 38:   */   
/* 39:   */   protected void doLog(Logger.Level level, String loggerClassName, Object message, Object[] parameters, Throwable thrown)
/* 40:   */   {
/* 41:51 */     if (isEnabled(level))
/* 42:   */     {
/* 43:52 */       String text = (parameters == null) || (parameters.length == 0) ? String.valueOf(message) : MessageFormat.format(String.valueOf(message), parameters);
/* 44:53 */       switch (1.$SwitchMap$org$jboss$logging$Logger$Level[level.ordinal()])
/* 45:   */       {
/* 46:   */       case 1: 
/* 47:   */       case 2: 
/* 48:56 */         this.logger.error(text, thrown);
/* 49:57 */         return;
/* 50:   */       case 3: 
/* 51:59 */         this.logger.warn(text, thrown);
/* 52:60 */         return;
/* 53:   */       case 4: 
/* 54:62 */         this.logger.info(text, thrown);
/* 55:63 */         return;
/* 56:   */       case 5: 
/* 57:65 */         this.logger.debug(text, thrown);
/* 58:66 */         return;
/* 59:   */       case 6: 
/* 60:68 */         this.logger.trace(text, thrown);
/* 61:69 */         return;
/* 62:   */       }
/* 63:   */     }
/* 64:   */   }
/* 65:   */   
/* 66:   */   protected void doLogf(Logger.Level level, String loggerClassName, String format, Object[] parameters, Throwable thrown)
/* 67:   */   {
/* 68:75 */     if (isEnabled(level))
/* 69:   */     {
/* 70:76 */       String text = parameters == null ? String.format(format, new Object[0]) : String.format(format, parameters);
/* 71:77 */       switch (1.$SwitchMap$org$jboss$logging$Logger$Level[level.ordinal()])
/* 72:   */       {
/* 73:   */       case 1: 
/* 74:   */       case 2: 
/* 75:80 */         this.logger.error(text, thrown);
/* 76:81 */         return;
/* 77:   */       case 3: 
/* 78:83 */         this.logger.warn(text, thrown);
/* 79:84 */         return;
/* 80:   */       case 4: 
/* 81:86 */         this.logger.info(text, thrown);
/* 82:87 */         return;
/* 83:   */       case 5: 
/* 84:89 */         this.logger.debug(text, thrown);
/* 85:90 */         return;
/* 86:   */       case 6: 
/* 87:92 */         this.logger.trace(text, thrown);
/* 88:93 */         return;
/* 89:   */       }
/* 90:   */     }
/* 91:   */   }
/* 92:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.jboss.logging.Slf4jLogger
 * JD-Core Version:    0.7.0.1
 */