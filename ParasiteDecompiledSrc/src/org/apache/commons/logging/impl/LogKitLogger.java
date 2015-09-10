/*   1:    */ package org.apache.commons.logging.impl;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import org.apache.commons.logging.Log;
/*   5:    */ import org.apache.log.Hierarchy;
/*   6:    */ import org.apache.log.Logger;
/*   7:    */ 
/*   8:    */ public class LogKitLogger
/*   9:    */   implements Log, Serializable
/*  10:    */ {
/*  11: 48 */   protected transient Logger logger = null;
/*  12: 51 */   protected String name = null;
/*  13:    */   
/*  14:    */   public LogKitLogger(String name)
/*  15:    */   {
/*  16: 64 */     this.name = name;
/*  17: 65 */     this.logger = getLogger();
/*  18:    */   }
/*  19:    */   
/*  20:    */   public Logger getLogger()
/*  21:    */   {
/*  22: 77 */     if (this.logger == null) {
/*  23: 78 */       this.logger = Hierarchy.getDefaultHierarchy().getLoggerFor(this.name);
/*  24:    */     }
/*  25: 80 */     return this.logger;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void trace(Object message)
/*  29:    */   {
/*  30: 95 */     debug(message);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void trace(Object message, Throwable t)
/*  34:    */   {
/*  35:107 */     debug(message, t);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void debug(Object message)
/*  39:    */   {
/*  40:118 */     if (message != null) {
/*  41:119 */       getLogger().debug(String.valueOf(message));
/*  42:    */     }
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void debug(Object message, Throwable t)
/*  46:    */   {
/*  47:132 */     if (message != null) {
/*  48:133 */       getLogger().debug(String.valueOf(message), t);
/*  49:    */     }
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void info(Object message)
/*  53:    */   {
/*  54:145 */     if (message != null) {
/*  55:146 */       getLogger().info(String.valueOf(message));
/*  56:    */     }
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void info(Object message, Throwable t)
/*  60:    */   {
/*  61:159 */     if (message != null) {
/*  62:160 */       getLogger().info(String.valueOf(message), t);
/*  63:    */     }
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void warn(Object message)
/*  67:    */   {
/*  68:172 */     if (message != null) {
/*  69:173 */       getLogger().warn(String.valueOf(message));
/*  70:    */     }
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void warn(Object message, Throwable t)
/*  74:    */   {
/*  75:186 */     if (message != null) {
/*  76:187 */       getLogger().warn(String.valueOf(message), t);
/*  77:    */     }
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void error(Object message)
/*  81:    */   {
/*  82:199 */     if (message != null) {
/*  83:200 */       getLogger().error(String.valueOf(message));
/*  84:    */     }
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void error(Object message, Throwable t)
/*  88:    */   {
/*  89:213 */     if (message != null) {
/*  90:214 */       getLogger().error(String.valueOf(message), t);
/*  91:    */     }
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void fatal(Object message)
/*  95:    */   {
/*  96:226 */     if (message != null) {
/*  97:227 */       getLogger().fatalError(String.valueOf(message));
/*  98:    */     }
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void fatal(Object message, Throwable t)
/* 102:    */   {
/* 103:240 */     if (message != null) {
/* 104:241 */       getLogger().fatalError(String.valueOf(message), t);
/* 105:    */     }
/* 106:    */   }
/* 107:    */   
/* 108:    */   public boolean isDebugEnabled()
/* 109:    */   {
/* 110:250 */     return getLogger().isDebugEnabled();
/* 111:    */   }
/* 112:    */   
/* 113:    */   public boolean isErrorEnabled()
/* 114:    */   {
/* 115:258 */     return getLogger().isErrorEnabled();
/* 116:    */   }
/* 117:    */   
/* 118:    */   public boolean isFatalEnabled()
/* 119:    */   {
/* 120:266 */     return getLogger().isFatalErrorEnabled();
/* 121:    */   }
/* 122:    */   
/* 123:    */   public boolean isInfoEnabled()
/* 124:    */   {
/* 125:274 */     return getLogger().isInfoEnabled();
/* 126:    */   }
/* 127:    */   
/* 128:    */   public boolean isTraceEnabled()
/* 129:    */   {
/* 130:282 */     return getLogger().isDebugEnabled();
/* 131:    */   }
/* 132:    */   
/* 133:    */   public boolean isWarnEnabled()
/* 134:    */   {
/* 135:290 */     return getLogger().isWarnEnabled();
/* 136:    */   }
/* 137:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.logging.impl.LogKitLogger
 * JD-Core Version:    0.7.0.1
 */