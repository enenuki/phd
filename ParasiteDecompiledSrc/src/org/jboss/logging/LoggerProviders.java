/*   1:    */ package org.jboss.logging;
/*   2:    */ 
/*   3:    */ import java.security.AccessController;
/*   4:    */ import java.security.PrivilegedAction;
/*   5:    */ import java.util.logging.LogManager;
/*   6:    */ 
/*   7:    */ final class LoggerProviders
/*   8:    */ {
/*   9:    */   static final String LOGGING_PROVIDER_KEY = "org.jboss.logging.provider";
/*  10: 32 */   static final LoggerProvider PROVIDER = ;
/*  11:    */   
/*  12:    */   private static LoggerProvider find()
/*  13:    */   {
/*  14: 35 */     LoggerProvider result = findProvider();
/*  15:    */     
/*  16: 37 */     result.getLogger("org.jboss.logging").debugf("Logging Provider: %s", result.getClass().getName());
/*  17: 38 */     return result;
/*  18:    */   }
/*  19:    */   
/*  20:    */   private static LoggerProvider findProvider()
/*  21:    */   {
/*  22: 45 */     ClassLoader cl = LoggerProviders.class.getClassLoader();
/*  23:    */     try
/*  24:    */     {
/*  25: 48 */       String loggerProvider = (String)AccessController.doPrivileged(new PrivilegedAction()
/*  26:    */       {
/*  27:    */         public String run()
/*  28:    */         {
/*  29: 50 */           return System.getProperty("org.jboss.logging.provider");
/*  30:    */         }
/*  31:    */       });
/*  32: 53 */       if (loggerProvider != null)
/*  33:    */       {
/*  34: 54 */         if ("jboss".equalsIgnoreCase(loggerProvider)) {
/*  35: 55 */           return tryJBossLogManager(cl);
/*  36:    */         }
/*  37: 56 */         if ("jdk".equalsIgnoreCase(loggerProvider)) {
/*  38: 57 */           return tryJDK();
/*  39:    */         }
/*  40: 58 */         if ("log4j".equalsIgnoreCase(loggerProvider)) {
/*  41: 59 */           return tryLog4j(cl);
/*  42:    */         }
/*  43: 60 */         if ("slf4j".equalsIgnoreCase(loggerProvider)) {
/*  44: 61 */           return trySlf4j();
/*  45:    */         }
/*  46:    */       }
/*  47:    */     }
/*  48:    */     catch (Throwable t) {}
/*  49:    */     try
/*  50:    */     {
/*  51: 67 */       return tryJBossLogManager(cl);
/*  52:    */     }
/*  53:    */     catch (Throwable t)
/*  54:    */     {
/*  55:    */       try
/*  56:    */       {
/*  57: 72 */         return tryLog4j(cl);
/*  58:    */       }
/*  59:    */       catch (Throwable t)
/*  60:    */       {
/*  61:    */         try
/*  62:    */         {
/*  63: 78 */           Class.forName("ch.qos.logback.classic.Logger", false, cl);
/*  64: 79 */           return trySlf4j();
/*  65:    */         }
/*  66:    */         catch (Throwable t) {}
/*  67:    */       }
/*  68:    */     }
/*  69: 83 */     return tryJDK();
/*  70:    */   }
/*  71:    */   
/*  72:    */   private static JDKLoggerProvider tryJDK()
/*  73:    */   {
/*  74: 87 */     return new JDKLoggerProvider();
/*  75:    */   }
/*  76:    */   
/*  77:    */   private static LoggerProvider trySlf4j()
/*  78:    */   {
/*  79: 91 */     return new Slf4jLoggerProvider();
/*  80:    */   }
/*  81:    */   
/*  82:    */   private static LoggerProvider tryLog4j(ClassLoader cl)
/*  83:    */     throws ClassNotFoundException
/*  84:    */   {
/*  85: 95 */     Class.forName("org.apache.log4j.LogManager", true, cl);
/*  86:    */     
/*  87: 97 */     Class.forName("org.apache.log4j.Hierarchy", true, cl);
/*  88: 98 */     return new Log4jLoggerProvider();
/*  89:    */   }
/*  90:    */   
/*  91:    */   private static LoggerProvider tryJBossLogManager(ClassLoader cl)
/*  92:    */     throws ClassNotFoundException
/*  93:    */   {
/*  94:102 */     Class<? extends LogManager> logManagerClass = LogManager.getLogManager().getClass();
/*  95:103 */     if ((logManagerClass == Class.forName("org.jboss.logmanager.LogManager", false, cl)) && (Class.forName("org.jboss.logmanager.Logger$AttachmentKey", true, cl).getClassLoader() == logManagerClass.getClassLoader())) {
/*  96:105 */       return new JBossLogManagerProvider();
/*  97:    */     }
/*  98:107 */     throw new IllegalStateException();
/*  99:    */   }
/* 100:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.jboss.logging.LoggerProviders
 * JD-Core Version:    0.7.0.1
 */