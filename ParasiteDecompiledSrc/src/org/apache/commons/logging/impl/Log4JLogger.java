/*   1:    */ package org.apache.commons.logging.impl;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.lang.reflect.Field;
/*   5:    */ import org.apache.commons.logging.Log;
/*   6:    */ import org.apache.log4j.Category;
/*   7:    */ import org.apache.log4j.Level;
/*   8:    */ import org.apache.log4j.Logger;
/*   9:    */ import org.apache.log4j.Priority;
/*  10:    */ 
/*  11:    */ public class Log4JLogger
/*  12:    */   implements Log, Serializable
/*  13:    */ {
/*  14: 55 */   private static final String FQCN = Log4JLogger.class.getName();
/*  15: 58 */   private transient Logger logger = null;
/*  16: 61 */   private String name = null;
/*  17:    */   private static Priority traceLevel;
/*  18:    */   
/*  19:    */   static
/*  20:    */   {
/*  21: 80 */     if (!Priority.class.isAssignableFrom(Level.class)) {
/*  22: 82 */       throw new InstantiationError("Log4J 1.2 not available");
/*  23:    */     }
/*  24:    */     try
/*  25:    */     {
/*  26: 90 */       traceLevel = (Priority)Level.class.getDeclaredField("TRACE").get(null);
/*  27:    */     }
/*  28:    */     catch (Exception ex)
/*  29:    */     {
/*  30: 93 */       traceLevel = Priority.DEBUG;
/*  31:    */     }
/*  32:    */   }
/*  33:    */   
/*  34:    */   public Log4JLogger(String name)
/*  35:    */   {
/*  36:108 */     this.name = name;
/*  37:109 */     this.logger = getLogger();
/*  38:    */   }
/*  39:    */   
/*  40:    */   public Log4JLogger(Logger logger)
/*  41:    */   {
/*  42:116 */     if (logger == null) {
/*  43:117 */       throw new IllegalArgumentException("Warning - null logger in constructor; possible log4j misconfiguration.");
/*  44:    */     }
/*  45:120 */     this.name = logger.getName();
/*  46:121 */     this.logger = logger;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void trace(Object message)
/*  50:    */   {
/*  51:152 */     getLogger().log(FQCN, traceLevel, message, null);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void trace(Object message, Throwable t)
/*  55:    */   {
/*  56:166 */     getLogger().log(FQCN, traceLevel, message, t);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void debug(Object message)
/*  60:    */   {
/*  61:177 */     getLogger().log(FQCN, Priority.DEBUG, message, null);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void debug(Object message, Throwable t)
/*  65:    */   {
/*  66:188 */     getLogger().log(FQCN, Priority.DEBUG, message, t);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void info(Object message)
/*  70:    */   {
/*  71:199 */     getLogger().log(FQCN, Priority.INFO, message, null);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void info(Object message, Throwable t)
/*  75:    */   {
/*  76:211 */     getLogger().log(FQCN, Priority.INFO, message, t);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void warn(Object message)
/*  80:    */   {
/*  81:222 */     getLogger().log(FQCN, Priority.WARN, message, null);
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void warn(Object message, Throwable t)
/*  85:    */   {
/*  86:234 */     getLogger().log(FQCN, Priority.WARN, message, t);
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void error(Object message)
/*  90:    */   {
/*  91:245 */     getLogger().log(FQCN, Priority.ERROR, message, null);
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void error(Object message, Throwable t)
/*  95:    */   {
/*  96:257 */     getLogger().log(FQCN, Priority.ERROR, message, t);
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void fatal(Object message)
/* 100:    */   {
/* 101:268 */     getLogger().log(FQCN, Priority.FATAL, message, null);
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void fatal(Object message, Throwable t)
/* 105:    */   {
/* 106:280 */     getLogger().log(FQCN, Priority.FATAL, message, t);
/* 107:    */   }
/* 108:    */   
/* 109:    */   public Logger getLogger()
/* 110:    */   {
/* 111:288 */     if (this.logger == null) {
/* 112:289 */       this.logger = Logger.getLogger(this.name);
/* 113:    */     }
/* 114:291 */     return this.logger;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public boolean isDebugEnabled()
/* 118:    */   {
/* 119:299 */     return getLogger().isDebugEnabled();
/* 120:    */   }
/* 121:    */   
/* 122:    */   public boolean isErrorEnabled()
/* 123:    */   {
/* 124:307 */     return getLogger().isEnabledFor(Priority.ERROR);
/* 125:    */   }
/* 126:    */   
/* 127:    */   public boolean isFatalEnabled()
/* 128:    */   {
/* 129:315 */     return getLogger().isEnabledFor(Priority.FATAL);
/* 130:    */   }
/* 131:    */   
/* 132:    */   public boolean isInfoEnabled()
/* 133:    */   {
/* 134:323 */     return getLogger().isInfoEnabled();
/* 135:    */   }
/* 136:    */   
/* 137:    */   public boolean isTraceEnabled()
/* 138:    */   {
/* 139:333 */     return getLogger().isEnabledFor(traceLevel);
/* 140:    */   }
/* 141:    */   
/* 142:    */   public boolean isWarnEnabled()
/* 143:    */   {
/* 144:340 */     return getLogger().isEnabledFor(Priority.WARN);
/* 145:    */   }
/* 146:    */   
/* 147:    */   public Log4JLogger() {}
/* 148:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.logging.impl.Log4JLogger
 * JD-Core Version:    0.7.0.1
 */