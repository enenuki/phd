/*   1:    */ package org.apache.log4j.spi;
/*   2:    */ 
/*   3:    */ import java.util.Enumeration;
/*   4:    */ import java.util.ResourceBundle;
/*   5:    */ import java.util.Vector;
/*   6:    */ import org.apache.log4j.Appender;
/*   7:    */ import org.apache.log4j.Level;
/*   8:    */ import org.apache.log4j.Logger;
/*   9:    */ import org.apache.log4j.Priority;
/*  10:    */ 
/*  11:    */ public final class NOPLogger
/*  12:    */   extends Logger
/*  13:    */ {
/*  14:    */   public NOPLogger(NOPLoggerRepository repo, String name)
/*  15:    */   {
/*  16: 39 */     super(name);
/*  17: 40 */     this.repository = repo;
/*  18: 41 */     this.level = Level.OFF;
/*  19: 42 */     this.parent = this;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public void addAppender(Appender newAppender) {}
/*  23:    */   
/*  24:    */   public void assertLog(boolean assertion, String msg) {}
/*  25:    */   
/*  26:    */   public void callAppenders(LoggingEvent event) {}
/*  27:    */   
/*  28:    */   void closeNestedAppenders() {}
/*  29:    */   
/*  30:    */   public void debug(Object message) {}
/*  31:    */   
/*  32:    */   public void debug(Object message, Throwable t) {}
/*  33:    */   
/*  34:    */   public void error(Object message) {}
/*  35:    */   
/*  36:    */   public void error(Object message, Throwable t) {}
/*  37:    */   
/*  38:    */   public void fatal(Object message) {}
/*  39:    */   
/*  40:    */   public void fatal(Object message, Throwable t) {}
/*  41:    */   
/*  42:    */   public Enumeration getAllAppenders()
/*  43:    */   {
/*  44: 91 */     return new Vector().elements();
/*  45:    */   }
/*  46:    */   
/*  47:    */   public Appender getAppender(String name)
/*  48:    */   {
/*  49: 96 */     return null;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public Level getEffectiveLevel()
/*  53:    */   {
/*  54:101 */     return Level.OFF;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public Priority getChainedPriority()
/*  58:    */   {
/*  59:106 */     return getEffectiveLevel();
/*  60:    */   }
/*  61:    */   
/*  62:    */   public ResourceBundle getResourceBundle()
/*  63:    */   {
/*  64:111 */     return null;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void info(Object message) {}
/*  68:    */   
/*  69:    */   public void info(Object message, Throwable t) {}
/*  70:    */   
/*  71:    */   public boolean isAttached(Appender appender)
/*  72:    */   {
/*  73:125 */     return false;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public boolean isDebugEnabled()
/*  77:    */   {
/*  78:130 */     return false;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public boolean isEnabledFor(Priority level)
/*  82:    */   {
/*  83:135 */     return false;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public boolean isInfoEnabled()
/*  87:    */   {
/*  88:140 */     return false;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void l7dlog(Priority priority, String key, Throwable t) {}
/*  92:    */   
/*  93:    */   public void l7dlog(Priority priority, String key, Object[] params, Throwable t) {}
/*  94:    */   
/*  95:    */   public void log(Priority priority, Object message, Throwable t) {}
/*  96:    */   
/*  97:    */   public void log(Priority priority, Object message) {}
/*  98:    */   
/*  99:    */   public void log(String callerFQCN, Priority level, Object message, Throwable t) {}
/* 100:    */   
/* 101:    */   public void removeAllAppenders() {}
/* 102:    */   
/* 103:    */   public void removeAppender(Appender appender) {}
/* 104:    */   
/* 105:    */   public void removeAppender(String name) {}
/* 106:    */   
/* 107:    */   public void setLevel(Level level) {}
/* 108:    */   
/* 109:    */   public void setPriority(Priority priority) {}
/* 110:    */   
/* 111:    */   public void setResourceBundle(ResourceBundle bundle) {}
/* 112:    */   
/* 113:    */   public void warn(Object message) {}
/* 114:    */   
/* 115:    */   public void warn(Object message, Throwable t) {}
/* 116:    */   
/* 117:    */   public void trace(Object message) {}
/* 118:    */   
/* 119:    */   public void trace(Object message, Throwable t) {}
/* 120:    */   
/* 121:    */   public boolean isTraceEnabled()
/* 122:    */   {
/* 123:208 */     return false;
/* 124:    */   }
/* 125:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.spi.NOPLogger
 * JD-Core Version:    0.7.0.1
 */