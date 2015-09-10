/*   1:    */ package org.jboss.logging;
/*   2:    */ 
/*   3:    */ import java.util.logging.Level;
/*   4:    */ import java.util.logging.LogRecord;
/*   5:    */ 
/*   6:    */ class JBossLogRecord
/*   7:    */   extends LogRecord
/*   8:    */ {
/*   9:    */   private static final long serialVersionUID = 2492784413065296060L;
/*  10: 31 */   private static final String LOGGER_CLASS_NAME = Logger.class.getName();
/*  11:    */   private boolean resolved;
/*  12:    */   private final String loggerClassName;
/*  13:    */   
/*  14:    */   JBossLogRecord(Level level, String msg)
/*  15:    */   {
/*  16: 37 */     super(level, msg);
/*  17: 38 */     this.loggerClassName = LOGGER_CLASS_NAME;
/*  18:    */   }
/*  19:    */   
/*  20:    */   JBossLogRecord(Level level, String msg, String loggerClassName)
/*  21:    */   {
/*  22: 42 */     super(level, msg);
/*  23: 43 */     this.loggerClassName = loggerClassName;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public String getSourceClassName()
/*  27:    */   {
/*  28: 47 */     if (!this.resolved) {
/*  29: 48 */       resolve();
/*  30:    */     }
/*  31: 50 */     return super.getSourceClassName();
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void setSourceClassName(String sourceClassName)
/*  35:    */   {
/*  36: 54 */     this.resolved = true;
/*  37: 55 */     super.setSourceClassName(sourceClassName);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public String getSourceMethodName()
/*  41:    */   {
/*  42: 59 */     if (!this.resolved) {
/*  43: 60 */       resolve();
/*  44:    */     }
/*  45: 62 */     return super.getSourceMethodName();
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void setSourceMethodName(String sourceMethodName)
/*  49:    */   {
/*  50: 66 */     this.resolved = true;
/*  51: 67 */     super.setSourceMethodName(sourceMethodName);
/*  52:    */   }
/*  53:    */   
/*  54:    */   private void resolve()
/*  55:    */   {
/*  56: 71 */     this.resolved = true;
/*  57: 72 */     StackTraceElement[] stack = new Throwable().getStackTrace();
/*  58: 73 */     boolean found = false;
/*  59: 74 */     for (StackTraceElement element : stack)
/*  60:    */     {
/*  61: 75 */       String className = element.getClassName();
/*  62: 76 */       if (found)
/*  63:    */       {
/*  64: 77 */         if (!this.loggerClassName.equals(className))
/*  65:    */         {
/*  66: 78 */           setSourceClassName(className);
/*  67: 79 */           setSourceMethodName(element.getMethodName());
/*  68:    */         }
/*  69:    */       }
/*  70:    */       else {
/*  71: 83 */         found = this.loggerClassName.equals(className);
/*  72:    */       }
/*  73:    */     }
/*  74: 86 */     setSourceClassName("<unknown>");
/*  75: 87 */     setSourceMethodName("<unknown>");
/*  76:    */   }
/*  77:    */   
/*  78:    */   protected Object writeReplace()
/*  79:    */   {
/*  80: 91 */     LogRecord replacement = new LogRecord(getLevel(), getMessage());
/*  81: 92 */     replacement.setResourceBundle(getResourceBundle());
/*  82: 93 */     replacement.setLoggerName(getLoggerName());
/*  83: 94 */     replacement.setMillis(getMillis());
/*  84: 95 */     replacement.setParameters(getParameters());
/*  85: 96 */     replacement.setResourceBundleName(getResourceBundleName());
/*  86: 97 */     replacement.setSequenceNumber(getSequenceNumber());
/*  87: 98 */     replacement.setSourceClassName(getSourceClassName());
/*  88: 99 */     replacement.setSourceMethodName(getSourceMethodName());
/*  89:100 */     replacement.setThreadID(getThreadID());
/*  90:101 */     replacement.setThrown(getThrown());
/*  91:102 */     return replacement;
/*  92:    */   }
/*  93:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.jboss.logging.JBossLogRecord
 * JD-Core Version:    0.7.0.1
 */