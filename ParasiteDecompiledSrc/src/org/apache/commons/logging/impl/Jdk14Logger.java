/*   1:    */ package org.apache.commons.logging.impl;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.logging.Level;
/*   5:    */ import java.util.logging.Logger;
/*   6:    */ import org.apache.commons.logging.Log;
/*   7:    */ 
/*   8:    */ public class Jdk14Logger
/*   9:    */   implements Log, Serializable
/*  10:    */ {
/*  11: 48 */   protected static final Level dummyLevel = Level.FINE;
/*  12:    */   
/*  13:    */   public Jdk14Logger(String name)
/*  14:    */   {
/*  15: 60 */     this.name = name;
/*  16: 61 */     this.logger = getLogger();
/*  17:    */   }
/*  18:    */   
/*  19: 72 */   protected transient Logger logger = null;
/*  20: 78 */   protected String name = null;
/*  21:    */   
/*  22:    */   private void log(Level level, String msg, Throwable ex)
/*  23:    */   {
/*  24: 85 */     Logger logger = getLogger();
/*  25: 86 */     if (logger.isLoggable(level))
/*  26:    */     {
/*  27: 88 */       Throwable dummyException = new Throwable();
/*  28: 89 */       StackTraceElement[] locations = dummyException.getStackTrace();
/*  29:    */       
/*  30: 91 */       String cname = "unknown";
/*  31: 92 */       String method = "unknown";
/*  32: 93 */       if ((locations != null) && (locations.length > 2))
/*  33:    */       {
/*  34: 94 */         StackTraceElement caller = locations[2];
/*  35: 95 */         cname = caller.getClassName();
/*  36: 96 */         method = caller.getMethodName();
/*  37:    */       }
/*  38: 98 */       if (ex == null) {
/*  39: 99 */         logger.logp(level, cname, method, msg);
/*  40:    */       } else {
/*  41:101 */         logger.logp(level, cname, method, msg, ex);
/*  42:    */       }
/*  43:    */     }
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void debug(Object message)
/*  47:    */   {
/*  48:114 */     log(Level.FINE, String.valueOf(message), null);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void debug(Object message, Throwable exception)
/*  52:    */   {
/*  53:126 */     log(Level.FINE, String.valueOf(message), exception);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void error(Object message)
/*  57:    */   {
/*  58:137 */     log(Level.SEVERE, String.valueOf(message), null);
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void error(Object message, Throwable exception)
/*  62:    */   {
/*  63:149 */     log(Level.SEVERE, String.valueOf(message), exception);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void fatal(Object message)
/*  67:    */   {
/*  68:160 */     log(Level.SEVERE, String.valueOf(message), null);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void fatal(Object message, Throwable exception)
/*  72:    */   {
/*  73:172 */     log(Level.SEVERE, String.valueOf(message), exception);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public Logger getLogger()
/*  77:    */   {
/*  78:180 */     if (this.logger == null) {
/*  79:181 */       this.logger = Logger.getLogger(this.name);
/*  80:    */     }
/*  81:183 */     return this.logger;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void info(Object message)
/*  85:    */   {
/*  86:194 */     log(Level.INFO, String.valueOf(message), null);
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void info(Object message, Throwable exception)
/*  90:    */   {
/*  91:206 */     log(Level.INFO, String.valueOf(message), exception);
/*  92:    */   }
/*  93:    */   
/*  94:    */   public boolean isDebugEnabled()
/*  95:    */   {
/*  96:214 */     return getLogger().isLoggable(Level.FINE);
/*  97:    */   }
/*  98:    */   
/*  99:    */   public boolean isErrorEnabled()
/* 100:    */   {
/* 101:222 */     return getLogger().isLoggable(Level.SEVERE);
/* 102:    */   }
/* 103:    */   
/* 104:    */   public boolean isFatalEnabled()
/* 105:    */   {
/* 106:230 */     return getLogger().isLoggable(Level.SEVERE);
/* 107:    */   }
/* 108:    */   
/* 109:    */   public boolean isInfoEnabled()
/* 110:    */   {
/* 111:238 */     return getLogger().isLoggable(Level.INFO);
/* 112:    */   }
/* 113:    */   
/* 114:    */   public boolean isTraceEnabled()
/* 115:    */   {
/* 116:246 */     return getLogger().isLoggable(Level.FINEST);
/* 117:    */   }
/* 118:    */   
/* 119:    */   public boolean isWarnEnabled()
/* 120:    */   {
/* 121:254 */     return getLogger().isLoggable(Level.WARNING);
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void trace(Object message)
/* 125:    */   {
/* 126:265 */     log(Level.FINEST, String.valueOf(message), null);
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void trace(Object message, Throwable exception)
/* 130:    */   {
/* 131:277 */     log(Level.FINEST, String.valueOf(message), exception);
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void warn(Object message)
/* 135:    */   {
/* 136:288 */     log(Level.WARNING, String.valueOf(message), null);
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void warn(Object message, Throwable exception)
/* 140:    */   {
/* 141:300 */     log(Level.WARNING, String.valueOf(message), exception);
/* 142:    */   }
/* 143:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.logging.impl.Jdk14Logger
 * JD-Core Version:    0.7.0.1
 */