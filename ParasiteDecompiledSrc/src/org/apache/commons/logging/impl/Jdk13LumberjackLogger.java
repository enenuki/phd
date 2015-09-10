/*   1:    */ package org.apache.commons.logging.impl;
/*   2:    */ 
/*   3:    */ import java.io.PrintWriter;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.io.StringWriter;
/*   6:    */ import java.util.StringTokenizer;
/*   7:    */ import java.util.logging.Level;
/*   8:    */ import java.util.logging.LogRecord;
/*   9:    */ import java.util.logging.Logger;
/*  10:    */ import org.apache.commons.logging.Log;
/*  11:    */ 
/*  12:    */ public class Jdk13LumberjackLogger
/*  13:    */   implements Log, Serializable
/*  14:    */ {
/*  15: 55 */   protected transient Logger logger = null;
/*  16: 56 */   protected String name = null;
/*  17: 57 */   private String sourceClassName = "unknown";
/*  18: 58 */   private String sourceMethodName = "unknown";
/*  19: 59 */   private boolean classAndMethodFound = false;
/*  20: 68 */   protected static final Level dummyLevel = Level.FINE;
/*  21:    */   
/*  22:    */   public Jdk13LumberjackLogger(String name)
/*  23:    */   {
/*  24: 80 */     this.name = name;
/*  25: 81 */     this.logger = getLogger();
/*  26:    */   }
/*  27:    */   
/*  28:    */   private void log(Level level, String msg, Throwable ex)
/*  29:    */   {
/*  30: 90 */     if (getLogger().isLoggable(level))
/*  31:    */     {
/*  32: 91 */       LogRecord record = new LogRecord(level, msg);
/*  33: 92 */       if (!this.classAndMethodFound) {
/*  34: 93 */         getClassAndMethod();
/*  35:    */       }
/*  36: 95 */       record.setSourceClassName(this.sourceClassName);
/*  37: 96 */       record.setSourceMethodName(this.sourceMethodName);
/*  38: 97 */       if (ex != null) {
/*  39: 98 */         record.setThrown(ex);
/*  40:    */       }
/*  41:100 */       getLogger().log(record);
/*  42:    */     }
/*  43:    */   }
/*  44:    */   
/*  45:    */   private void getClassAndMethod()
/*  46:    */   {
/*  47:    */     try
/*  48:    */     {
/*  49:110 */       Throwable throwable = new Throwable();
/*  50:111 */       throwable.fillInStackTrace();
/*  51:112 */       StringWriter stringWriter = new StringWriter();
/*  52:113 */       PrintWriter printWriter = new PrintWriter(stringWriter);
/*  53:114 */       throwable.printStackTrace(printWriter);
/*  54:115 */       String traceString = stringWriter.getBuffer().toString();
/*  55:116 */       StringTokenizer tokenizer = new StringTokenizer(traceString, "\n");
/*  56:    */       
/*  57:118 */       tokenizer.nextToken();
/*  58:119 */       String line = tokenizer.nextToken();
/*  59:120 */       while (line.indexOf(getClass().getName()) == -1) {
/*  60:121 */         line = tokenizer.nextToken();
/*  61:    */       }
/*  62:123 */       while (line.indexOf(getClass().getName()) >= 0) {
/*  63:124 */         line = tokenizer.nextToken();
/*  64:    */       }
/*  65:126 */       int start = line.indexOf("at ") + 3;
/*  66:127 */       int end = line.indexOf('(');
/*  67:128 */       String temp = line.substring(start, end);
/*  68:129 */       int lastPeriod = temp.lastIndexOf('.');
/*  69:130 */       this.sourceClassName = temp.substring(0, lastPeriod);
/*  70:131 */       this.sourceMethodName = temp.substring(lastPeriod + 1);
/*  71:    */     }
/*  72:    */     catch (Exception ex) {}
/*  73:135 */     this.classAndMethodFound = true;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void debug(Object message)
/*  77:    */   {
/*  78:145 */     log(Level.FINE, String.valueOf(message), null);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void debug(Object message, Throwable exception)
/*  82:    */   {
/*  83:157 */     log(Level.FINE, String.valueOf(message), exception);
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void error(Object message)
/*  87:    */   {
/*  88:168 */     log(Level.SEVERE, String.valueOf(message), null);
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void error(Object message, Throwable exception)
/*  92:    */   {
/*  93:180 */     log(Level.SEVERE, String.valueOf(message), exception);
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void fatal(Object message)
/*  97:    */   {
/*  98:191 */     log(Level.SEVERE, String.valueOf(message), null);
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void fatal(Object message, Throwable exception)
/* 102:    */   {
/* 103:203 */     log(Level.SEVERE, String.valueOf(message), exception);
/* 104:    */   }
/* 105:    */   
/* 106:    */   public Logger getLogger()
/* 107:    */   {
/* 108:211 */     if (this.logger == null) {
/* 109:212 */       this.logger = Logger.getLogger(this.name);
/* 110:    */     }
/* 111:214 */     return this.logger;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void info(Object message)
/* 115:    */   {
/* 116:225 */     log(Level.INFO, String.valueOf(message), null);
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void info(Object message, Throwable exception)
/* 120:    */   {
/* 121:237 */     log(Level.INFO, String.valueOf(message), exception);
/* 122:    */   }
/* 123:    */   
/* 124:    */   public boolean isDebugEnabled()
/* 125:    */   {
/* 126:245 */     return getLogger().isLoggable(Level.FINE);
/* 127:    */   }
/* 128:    */   
/* 129:    */   public boolean isErrorEnabled()
/* 130:    */   {
/* 131:253 */     return getLogger().isLoggable(Level.SEVERE);
/* 132:    */   }
/* 133:    */   
/* 134:    */   public boolean isFatalEnabled()
/* 135:    */   {
/* 136:261 */     return getLogger().isLoggable(Level.SEVERE);
/* 137:    */   }
/* 138:    */   
/* 139:    */   public boolean isInfoEnabled()
/* 140:    */   {
/* 141:269 */     return getLogger().isLoggable(Level.INFO);
/* 142:    */   }
/* 143:    */   
/* 144:    */   public boolean isTraceEnabled()
/* 145:    */   {
/* 146:277 */     return getLogger().isLoggable(Level.FINEST);
/* 147:    */   }
/* 148:    */   
/* 149:    */   public boolean isWarnEnabled()
/* 150:    */   {
/* 151:285 */     return getLogger().isLoggable(Level.WARNING);
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void trace(Object message)
/* 155:    */   {
/* 156:296 */     log(Level.FINEST, String.valueOf(message), null);
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void trace(Object message, Throwable exception)
/* 160:    */   {
/* 161:308 */     log(Level.FINEST, String.valueOf(message), exception);
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void warn(Object message)
/* 165:    */   {
/* 166:319 */     log(Level.WARNING, String.valueOf(message), null);
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void warn(Object message, Throwable exception)
/* 170:    */   {
/* 171:331 */     log(Level.WARNING, String.valueOf(message), exception);
/* 172:    */   }
/* 173:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.logging.impl.Jdk13LumberjackLogger
 * JD-Core Version:    0.7.0.1
 */