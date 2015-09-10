/*   1:    */ package org.jboss.logging;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.InvocationTargetException;
/*   4:    */ import java.lang.reflect.Method;
/*   5:    */ import java.lang.reflect.UndeclaredThrowableException;
/*   6:    */ import java.text.MessageFormat;
/*   7:    */ import org.slf4j.spi.LocationAwareLogger;
/*   8:    */ 
/*   9:    */ final class Slf4jLocationAwareLogger
/*  10:    */   extends Logger
/*  11:    */ {
/*  12:    */   private static final long serialVersionUID = 8685757928087758380L;
/*  13: 35 */   private static final Object[] EMPTY = new Object[0];
/*  14:    */   private static final boolean POST_1_6;
/*  15:    */   private static final Method LOG_METHOD;
/*  16:    */   private final LocationAwareLogger logger;
/*  17:    */   
/*  18:    */   static
/*  19:    */   {
/*  20: 40 */     Method[] methods = LocationAwareLogger.class.getDeclaredMethods();
/*  21: 41 */     Method logMethod = null;
/*  22: 42 */     boolean post16 = false;
/*  23: 43 */     for (Method method : methods) {
/*  24: 44 */       if (method.getName().equals("log"))
/*  25:    */       {
/*  26: 45 */         logMethod = method;
/*  27: 46 */         Class<?>[] parameterTypes = method.getParameterTypes();
/*  28: 47 */         post16 = parameterTypes.length == 6;
/*  29:    */       }
/*  30:    */     }
/*  31: 50 */     if (logMethod == null) {
/*  32: 51 */       throw new NoSuchMethodError("Cannot find LocationAwareLogger.log() method");
/*  33:    */     }
/*  34: 53 */     POST_1_6 = post16;
/*  35: 54 */     LOG_METHOD = logMethod;
/*  36:    */   }
/*  37:    */   
/*  38:    */   Slf4jLocationAwareLogger(String name, LocationAwareLogger logger)
/*  39:    */   {
/*  40: 60 */     super(name);
/*  41: 61 */     this.logger = logger;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public boolean isEnabled(Logger.Level level)
/*  45:    */   {
/*  46: 65 */     if (level != null) {
/*  47: 65 */       switch (1.$SwitchMap$org$jboss$logging$Logger$Level[level.ordinal()])
/*  48:    */       {
/*  49:    */       case 1: 
/*  50: 66 */         return this.logger.isErrorEnabled();
/*  51:    */       case 2: 
/*  52: 67 */         return this.logger.isErrorEnabled();
/*  53:    */       case 3: 
/*  54: 68 */         return this.logger.isWarnEnabled();
/*  55:    */       case 4: 
/*  56: 69 */         return this.logger.isInfoEnabled();
/*  57:    */       case 5: 
/*  58: 70 */         return this.logger.isDebugEnabled();
/*  59:    */       case 6: 
/*  60: 71 */         return this.logger.isTraceEnabled();
/*  61:    */       }
/*  62:    */     }
/*  63: 73 */     return true;
/*  64:    */   }
/*  65:    */   
/*  66:    */   protected void doLog(Logger.Level level, String loggerClassName, Object message, Object[] parameters, Throwable thrown)
/*  67:    */   {
/*  68: 77 */     if (isEnabled(level))
/*  69:    */     {
/*  70: 78 */       String text = (parameters == null) || (parameters.length == 0) ? String.valueOf(message) : MessageFormat.format(String.valueOf(message), parameters);
/*  71: 79 */       doLog(this.logger, loggerClassName, translate(level), text, thrown);
/*  72:    */     }
/*  73:    */   }
/*  74:    */   
/*  75:    */   protected void doLogf(Logger.Level level, String loggerClassName, String format, Object[] parameters, Throwable thrown)
/*  76:    */   {
/*  77: 84 */     if (isEnabled(level))
/*  78:    */     {
/*  79: 85 */       String text = parameters == null ? String.format(format, new Object[0]) : String.format(format, parameters);
/*  80: 86 */       doLog(this.logger, loggerClassName, translate(level), text, thrown);
/*  81:    */     }
/*  82:    */   }
/*  83:    */   
/*  84:    */   private static void doLog(LocationAwareLogger logger, String className, int level, String text, Throwable thrown)
/*  85:    */   {
/*  86:    */     try
/*  87:    */     {
/*  88: 92 */       if (POST_1_6) {
/*  89: 93 */         LOG_METHOD.invoke(logger, new Object[] { null, className, Integer.valueOf(level), text, EMPTY, thrown });
/*  90:    */       } else {
/*  91: 95 */         LOG_METHOD.invoke(logger, new Object[] { null, className, Integer.valueOf(level), text, thrown });
/*  92:    */       }
/*  93:    */     }
/*  94:    */     catch (InvocationTargetException e)
/*  95:    */     {
/*  96:    */       try
/*  97:    */       {
/*  98: 99 */         throw e.getCause();
/*  99:    */       }
/* 100:    */       catch (RuntimeException ex)
/* 101:    */       {
/* 102:101 */         throw ex;
/* 103:    */       }
/* 104:    */       catch (Error er)
/* 105:    */       {
/* 106:103 */         throw er;
/* 107:    */       }
/* 108:    */       catch (Throwable throwable)
/* 109:    */       {
/* 110:105 */         throw new UndeclaredThrowableException(throwable);
/* 111:    */       }
/* 112:    */     }
/* 113:    */     catch (IllegalAccessException e)
/* 114:    */     {
/* 115:108 */       throw new IllegalAccessError(e.getMessage());
/* 116:    */     }
/* 117:    */   }
/* 118:    */   
/* 119:    */   private static int translate(Logger.Level level)
/* 120:    */   {
/* 121:113 */     if (level != null) {
/* 122:113 */       switch (1.$SwitchMap$org$jboss$logging$Logger$Level[level.ordinal()])
/* 123:    */       {
/* 124:    */       case 1: 
/* 125:    */       case 2: 
/* 126:115 */         return 40;
/* 127:    */       case 3: 
/* 128:116 */         return 30;
/* 129:    */       case 4: 
/* 130:117 */         return 20;
/* 131:    */       case 5: 
/* 132:118 */         return 10;
/* 133:    */       case 6: 
/* 134:119 */         return 0;
/* 135:    */       }
/* 136:    */     }
/* 137:121 */     return 0;
/* 138:    */   }
/* 139:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.jboss.logging.Slf4jLocationAwareLogger
 * JD-Core Version:    0.7.0.1
 */