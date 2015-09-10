/*  1:   */ package jxl.common;
/*  2:   */ 
/*  3:   */ import java.security.AccessControlException;
/*  4:   */ import jxl.common.log.LoggerName;
/*  5:   */ import jxl.common.log.SimpleLogger;
/*  6:   */ 
/*  7:   */ public abstract class Logger
/*  8:   */ {
/*  9:33 */   private static Logger logger = null;
/* 10:   */   
/* 11:   */   public static final Logger getLogger(Class cl)
/* 12:   */   {
/* 13:40 */     if (logger == null) {
/* 14:42 */       initializeLogger();
/* 15:   */     }
/* 16:45 */     return logger.getLoggerImpl(cl);
/* 17:   */   }
/* 18:   */   
/* 19:   */   private static synchronized void initializeLogger()
/* 20:   */   {
/* 21:53 */     if (logger != null) {
/* 22:55 */       return;
/* 23:   */     }
/* 24:58 */     String loggerName = LoggerName.NAME;
/* 25:   */     try
/* 26:   */     {
/* 27:63 */       loggerName = System.getProperty("logger");
/* 28:65 */       if (loggerName == null) {
/* 29:68 */         loggerName = LoggerName.NAME;
/* 30:   */       }
/* 31:71 */       logger = (Logger)Class.forName(loggerName).newInstance();
/* 32:   */     }
/* 33:   */     catch (IllegalAccessException e)
/* 34:   */     {
/* 35:75 */       logger = new SimpleLogger();
/* 36:76 */       logger.warn("Could not instantiate logger " + loggerName + " using default");
/* 37:   */     }
/* 38:   */     catch (InstantiationException e)
/* 39:   */     {
/* 40:81 */       logger = new SimpleLogger();
/* 41:82 */       logger.warn("Could not instantiate logger " + loggerName + " using default");
/* 42:   */     }
/* 43:   */     catch (AccessControlException e)
/* 44:   */     {
/* 45:87 */       logger = new SimpleLogger();
/* 46:88 */       logger.warn("Could not instantiate logger " + loggerName + " using default");
/* 47:   */     }
/* 48:   */     catch (ClassNotFoundException e)
/* 49:   */     {
/* 50:93 */       logger = new SimpleLogger();
/* 51:94 */       logger.warn("Could not instantiate logger " + loggerName + " using default");
/* 52:   */     }
/* 53:   */   }
/* 54:   */   
/* 55:   */   public abstract void debug(Object paramObject);
/* 56:   */   
/* 57:   */   public abstract void debug(Object paramObject, Throwable paramThrowable);
/* 58:   */   
/* 59:   */   public abstract void error(Object paramObject);
/* 60:   */   
/* 61:   */   public abstract void error(Object paramObject, Throwable paramThrowable);
/* 62:   */   
/* 63:   */   public abstract void fatal(Object paramObject);
/* 64:   */   
/* 65:   */   public abstract void fatal(Object paramObject, Throwable paramThrowable);
/* 66:   */   
/* 67:   */   public abstract void info(Object paramObject);
/* 68:   */   
/* 69:   */   public abstract void info(Object paramObject, Throwable paramThrowable);
/* 70:   */   
/* 71:   */   public abstract void warn(Object paramObject);
/* 72:   */   
/* 73:   */   public abstract void warn(Object paramObject, Throwable paramThrowable);
/* 74:   */   
/* 75:   */   protected abstract Logger getLoggerImpl(Class paramClass);
/* 76:   */   
/* 77:   */   public void setSuppressWarnings(boolean w) {}
/* 78:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.common.Logger
 * JD-Core Version:    0.7.0.1
 */