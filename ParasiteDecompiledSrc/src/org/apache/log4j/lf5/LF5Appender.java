/*   1:    */ package org.apache.log4j.lf5;
/*   2:    */ 
/*   3:    */ import java.awt.Dimension;
/*   4:    */ import java.awt.Toolkit;
/*   5:    */ import org.apache.log4j.AppenderSkeleton;
/*   6:    */ import org.apache.log4j.Priority;
/*   7:    */ import org.apache.log4j.lf5.viewer.LogBrokerMonitor;
/*   8:    */ import org.apache.log4j.spi.LocationInfo;
/*   9:    */ import org.apache.log4j.spi.LoggingEvent;
/*  10:    */ 
/*  11:    */ public class LF5Appender
/*  12:    */   extends AppenderSkeleton
/*  13:    */ {
/*  14:    */   protected LogBrokerMonitor _logMonitor;
/*  15:    */   protected static LogBrokerMonitor _defaultLogMonitor;
/*  16:    */   protected static AppenderFinalizer _finalizer;
/*  17:    */   
/*  18:    */   public LF5Appender()
/*  19:    */   {
/*  20: 68 */     this(getDefaultInstance());
/*  21:    */   }
/*  22:    */   
/*  23:    */   public LF5Appender(LogBrokerMonitor monitor)
/*  24:    */   {
/*  25: 82 */     if (monitor != null) {
/*  26: 83 */       this._logMonitor = monitor;
/*  27:    */     }
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void append(LoggingEvent event)
/*  31:    */   {
/*  32: 99 */     String category = event.getLoggerName();
/*  33:100 */     String logMessage = event.getRenderedMessage();
/*  34:101 */     String nestedDiagnosticContext = event.getNDC();
/*  35:102 */     String threadDescription = event.getThreadName();
/*  36:103 */     String level = event.getLevel().toString();
/*  37:104 */     long time = event.timeStamp;
/*  38:105 */     LocationInfo locationInfo = event.getLocationInformation();
/*  39:    */     
/*  40:    */ 
/*  41:108 */     Log4JLogRecord record = new Log4JLogRecord();
/*  42:    */     
/*  43:110 */     record.setCategory(category);
/*  44:111 */     record.setMessage(logMessage);
/*  45:112 */     record.setLocation(locationInfo.fullInfo);
/*  46:113 */     record.setMillis(time);
/*  47:114 */     record.setThreadDescription(threadDescription);
/*  48:116 */     if (nestedDiagnosticContext != null) {
/*  49:117 */       record.setNDC(nestedDiagnosticContext);
/*  50:    */     } else {
/*  51:119 */       record.setNDC("");
/*  52:    */     }
/*  53:122 */     if (event.getThrowableInformation() != null) {
/*  54:123 */       record.setThrownStackTrace(event.getThrowableInformation());
/*  55:    */     }
/*  56:    */     try
/*  57:    */     {
/*  58:127 */       record.setLevel(LogLevel.valueOf(level));
/*  59:    */     }
/*  60:    */     catch (LogLevelFormatException e)
/*  61:    */     {
/*  62:131 */       record.setLevel(LogLevel.WARN);
/*  63:    */     }
/*  64:134 */     if (this._logMonitor != null) {
/*  65:135 */       this._logMonitor.addMessage(record);
/*  66:    */     }
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void close() {}
/*  70:    */   
/*  71:    */   public boolean requiresLayout()
/*  72:    */   {
/*  73:152 */     return false;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void setCallSystemExitOnClose(boolean callSystemExitOnClose)
/*  77:    */   {
/*  78:169 */     this._logMonitor.setCallSystemExitOnClose(callSystemExitOnClose);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public boolean equals(LF5Appender compareTo)
/*  82:    */   {
/*  83:183 */     return this._logMonitor == compareTo.getLogBrokerMonitor();
/*  84:    */   }
/*  85:    */   
/*  86:    */   public LogBrokerMonitor getLogBrokerMonitor()
/*  87:    */   {
/*  88:187 */     return this._logMonitor;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public static void main(String[] args)
/*  92:    */   {
/*  93:191 */     new LF5Appender();
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void setMaxNumberOfRecords(int maxNumberOfRecords)
/*  97:    */   {
/*  98:195 */     _defaultLogMonitor.setMaxNumberOfLogRecords(maxNumberOfRecords);
/*  99:    */   }
/* 100:    */   
/* 101:    */   protected static synchronized LogBrokerMonitor getDefaultInstance()
/* 102:    */   {
/* 103:205 */     if (_defaultLogMonitor == null) {
/* 104:    */       try
/* 105:    */       {
/* 106:207 */         _defaultLogMonitor = new LogBrokerMonitor(LogLevel.getLog4JLevels());
/* 107:    */         
/* 108:209 */         _finalizer = new AppenderFinalizer(_defaultLogMonitor);
/* 109:    */         
/* 110:211 */         _defaultLogMonitor.setFrameSize(getDefaultMonitorWidth(), getDefaultMonitorHeight());
/* 111:    */         
/* 112:213 */         _defaultLogMonitor.setFontSize(12);
/* 113:214 */         _defaultLogMonitor.show();
/* 114:    */       }
/* 115:    */       catch (SecurityException e)
/* 116:    */       {
/* 117:217 */         _defaultLogMonitor = null;
/* 118:    */       }
/* 119:    */     }
/* 120:221 */     return _defaultLogMonitor;
/* 121:    */   }
/* 122:    */   
/* 123:    */   protected static int getScreenWidth()
/* 124:    */   {
/* 125:    */     try
/* 126:    */     {
/* 127:231 */       return Toolkit.getDefaultToolkit().getScreenSize().width;
/* 128:    */     }
/* 129:    */     catch (Throwable t) {}
/* 130:233 */     return 800;
/* 131:    */   }
/* 132:    */   
/* 133:    */   protected static int getScreenHeight()
/* 134:    */   {
/* 135:    */     try
/* 136:    */     {
/* 137:244 */       return Toolkit.getDefaultToolkit().getScreenSize().height;
/* 138:    */     }
/* 139:    */     catch (Throwable t) {}
/* 140:246 */     return 600;
/* 141:    */   }
/* 142:    */   
/* 143:    */   protected static int getDefaultMonitorWidth()
/* 144:    */   {
/* 145:251 */     return 3 * getScreenWidth() / 4;
/* 146:    */   }
/* 147:    */   
/* 148:    */   protected static int getDefaultMonitorHeight()
/* 149:    */   {
/* 150:255 */     return 3 * getScreenHeight() / 4;
/* 151:    */   }
/* 152:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.lf5.LF5Appender
 * JD-Core Version:    0.7.0.1
 */