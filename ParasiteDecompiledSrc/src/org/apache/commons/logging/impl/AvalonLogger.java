/*   1:    */ package org.apache.commons.logging.impl;
/*   2:    */ 
/*   3:    */ import org.apache.avalon.framework.logger.Logger;
/*   4:    */ import org.apache.commons.logging.Log;
/*   5:    */ 
/*   6:    */ public class AvalonLogger
/*   7:    */   implements Log
/*   8:    */ {
/*   9: 58 */   private static Logger defaultLogger = null;
/*  10: 60 */   private transient Logger logger = null;
/*  11:    */   
/*  12:    */   public AvalonLogger(Logger logger)
/*  13:    */   {
/*  14: 68 */     this.logger = logger;
/*  15:    */   }
/*  16:    */   
/*  17:    */   public AvalonLogger(String name)
/*  18:    */   {
/*  19: 77 */     if (defaultLogger == null) {
/*  20: 78 */       throw new NullPointerException("default logger has to be specified if this constructor is used!");
/*  21:    */     }
/*  22: 79 */     this.logger = defaultLogger.getChildLogger(name);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public Logger getLogger()
/*  26:    */   {
/*  27: 87 */     return this.logger;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public static void setDefaultLogger(Logger logger)
/*  31:    */   {
/*  32: 97 */     defaultLogger = logger;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void debug(Object message, Throwable t)
/*  36:    */   {
/*  37:109 */     if (getLogger().isDebugEnabled()) {
/*  38:109 */       getLogger().debug(String.valueOf(message), t);
/*  39:    */     }
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void debug(Object message)
/*  43:    */   {
/*  44:120 */     if (getLogger().isDebugEnabled()) {
/*  45:120 */       getLogger().debug(String.valueOf(message));
/*  46:    */     }
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void error(Object message, Throwable t)
/*  50:    */   {
/*  51:132 */     if (getLogger().isErrorEnabled()) {
/*  52:132 */       getLogger().error(String.valueOf(message), t);
/*  53:    */     }
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void error(Object message)
/*  57:    */   {
/*  58:143 */     if (getLogger().isErrorEnabled()) {
/*  59:143 */       getLogger().error(String.valueOf(message));
/*  60:    */     }
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void fatal(Object message, Throwable t)
/*  64:    */   {
/*  65:155 */     if (getLogger().isFatalErrorEnabled()) {
/*  66:155 */       getLogger().fatalError(String.valueOf(message), t);
/*  67:    */     }
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void fatal(Object message)
/*  71:    */   {
/*  72:166 */     if (getLogger().isFatalErrorEnabled()) {
/*  73:166 */       getLogger().fatalError(String.valueOf(message));
/*  74:    */     }
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void info(Object message, Throwable t)
/*  78:    */   {
/*  79:178 */     if (getLogger().isInfoEnabled()) {
/*  80:178 */       getLogger().info(String.valueOf(message), t);
/*  81:    */     }
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void info(Object message)
/*  85:    */   {
/*  86:189 */     if (getLogger().isInfoEnabled()) {
/*  87:189 */       getLogger().info(String.valueOf(message));
/*  88:    */     }
/*  89:    */   }
/*  90:    */   
/*  91:    */   public boolean isDebugEnabled()
/*  92:    */   {
/*  93:198 */     return getLogger().isDebugEnabled();
/*  94:    */   }
/*  95:    */   
/*  96:    */   public boolean isErrorEnabled()
/*  97:    */   {
/*  98:207 */     return getLogger().isErrorEnabled();
/*  99:    */   }
/* 100:    */   
/* 101:    */   public boolean isFatalEnabled()
/* 102:    */   {
/* 103:216 */     return getLogger().isFatalErrorEnabled();
/* 104:    */   }
/* 105:    */   
/* 106:    */   public boolean isInfoEnabled()
/* 107:    */   {
/* 108:225 */     return getLogger().isInfoEnabled();
/* 109:    */   }
/* 110:    */   
/* 111:    */   public boolean isTraceEnabled()
/* 112:    */   {
/* 113:234 */     return getLogger().isDebugEnabled();
/* 114:    */   }
/* 115:    */   
/* 116:    */   public boolean isWarnEnabled()
/* 117:    */   {
/* 118:243 */     return getLogger().isWarnEnabled();
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void trace(Object message, Throwable t)
/* 122:    */   {
/* 123:255 */     if (getLogger().isDebugEnabled()) {
/* 124:255 */       getLogger().debug(String.valueOf(message), t);
/* 125:    */     }
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void trace(Object message)
/* 129:    */   {
/* 130:266 */     if (getLogger().isDebugEnabled()) {
/* 131:266 */       getLogger().debug(String.valueOf(message));
/* 132:    */     }
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void warn(Object message, Throwable t)
/* 136:    */   {
/* 137:278 */     if (getLogger().isWarnEnabled()) {
/* 138:278 */       getLogger().warn(String.valueOf(message), t);
/* 139:    */     }
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void warn(Object message)
/* 143:    */   {
/* 144:289 */     if (getLogger().isWarnEnabled()) {
/* 145:289 */       getLogger().warn(String.valueOf(message));
/* 146:    */     }
/* 147:    */   }
/* 148:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.logging.impl.AvalonLogger
 * JD-Core Version:    0.7.0.1
 */