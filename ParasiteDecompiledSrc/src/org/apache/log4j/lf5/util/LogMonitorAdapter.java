/*   1:    */ package org.apache.log4j.lf5.util;
/*   2:    */ 
/*   3:    */ import java.awt.Dimension;
/*   4:    */ import java.awt.Toolkit;
/*   5:    */ import java.util.Arrays;
/*   6:    */ import java.util.List;
/*   7:    */ import org.apache.log4j.lf5.LogLevel;
/*   8:    */ import org.apache.log4j.lf5.LogRecord;
/*   9:    */ import org.apache.log4j.lf5.viewer.LogBrokerMonitor;
/*  10:    */ 
/*  11:    */ public class LogMonitorAdapter
/*  12:    */ {
/*  13:    */   public static final int LOG4J_LOG_LEVELS = 0;
/*  14:    */   public static final int JDK14_LOG_LEVELS = 1;
/*  15:    */   private LogBrokerMonitor _logMonitor;
/*  16: 49 */   private LogLevel _defaultLevel = null;
/*  17:    */   
/*  18:    */   private LogMonitorAdapter(List userDefinedLevels)
/*  19:    */   {
/*  20: 57 */     this._defaultLevel = ((LogLevel)userDefinedLevels.get(0));
/*  21: 58 */     this._logMonitor = new LogBrokerMonitor(userDefinedLevels);
/*  22:    */     
/*  23: 60 */     this._logMonitor.setFrameSize(getDefaultMonitorWidth(), getDefaultMonitorHeight());
/*  24:    */     
/*  25: 62 */     this._logMonitor.setFontSize(12);
/*  26: 63 */     this._logMonitor.show();
/*  27:    */   }
/*  28:    */   
/*  29:    */   public static LogMonitorAdapter newInstance(int loglevels)
/*  30:    */   {
/*  31:    */     LogMonitorAdapter adapter;
/*  32: 78 */     if (loglevels == 1)
/*  33:    */     {
/*  34: 79 */       LogMonitorAdapter adapter = newInstance(LogLevel.getJdk14Levels());
/*  35: 80 */       adapter.setDefaultLevel(LogLevel.FINEST);
/*  36: 81 */       adapter.setSevereLevel(LogLevel.SEVERE);
/*  37:    */     }
/*  38:    */     else
/*  39:    */     {
/*  40: 83 */       adapter = newInstance(LogLevel.getLog4JLevels());
/*  41: 84 */       adapter.setDefaultLevel(LogLevel.DEBUG);
/*  42: 85 */       adapter.setSevereLevel(LogLevel.FATAL);
/*  43:    */     }
/*  44: 87 */     return adapter;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public static LogMonitorAdapter newInstance(LogLevel[] userDefined)
/*  48:    */   {
/*  49: 99 */     if (userDefined == null) {
/*  50:100 */       return null;
/*  51:    */     }
/*  52:102 */     return newInstance(Arrays.asList(userDefined));
/*  53:    */   }
/*  54:    */   
/*  55:    */   public static LogMonitorAdapter newInstance(List userDefinedLevels)
/*  56:    */   {
/*  57:114 */     return new LogMonitorAdapter(userDefinedLevels);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void addMessage(LogRecord record)
/*  61:    */   {
/*  62:123 */     this._logMonitor.addMessage(record);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void setMaxNumberOfRecords(int maxNumberOfRecords)
/*  66:    */   {
/*  67:132 */     this._logMonitor.setMaxNumberOfLogRecords(maxNumberOfRecords);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void setDefaultLevel(LogLevel level)
/*  71:    */   {
/*  72:142 */     this._defaultLevel = level;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public LogLevel getDefaultLevel()
/*  76:    */   {
/*  77:151 */     return this._defaultLevel;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setSevereLevel(LogLevel level)
/*  81:    */   {
/*  82:160 */     AdapterLogRecord.setSevereLevel(level);
/*  83:    */   }
/*  84:    */   
/*  85:    */   public LogLevel getSevereLevel()
/*  86:    */   {
/*  87:169 */     return AdapterLogRecord.getSevereLevel();
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void log(String category, LogLevel level, String message, Throwable t, String NDC)
/*  91:    */   {
/*  92:184 */     AdapterLogRecord record = new AdapterLogRecord();
/*  93:185 */     record.setCategory(category);
/*  94:186 */     record.setMessage(message);
/*  95:187 */     record.setNDC(NDC);
/*  96:188 */     record.setThrown(t);
/*  97:190 */     if (level == null) {
/*  98:191 */       record.setLevel(getDefaultLevel());
/*  99:    */     } else {
/* 100:193 */       record.setLevel(level);
/* 101:    */     }
/* 102:196 */     addMessage(record);
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void log(String category, String message)
/* 106:    */   {
/* 107:206 */     log(category, null, message);
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void log(String category, LogLevel level, String message, String NDC)
/* 111:    */   {
/* 112:218 */     log(category, level, message, null, NDC);
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void log(String category, LogLevel level, String message, Throwable t)
/* 116:    */   {
/* 117:231 */     log(category, level, message, t, null);
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void log(String category, LogLevel level, String message)
/* 121:    */   {
/* 122:242 */     log(category, level, message, null, null);
/* 123:    */   }
/* 124:    */   
/* 125:    */   protected static int getScreenWidth()
/* 126:    */   {
/* 127:    */     try
/* 128:    */     {
/* 129:255 */       return Toolkit.getDefaultToolkit().getScreenSize().width;
/* 130:    */     }
/* 131:    */     catch (Throwable t) {}
/* 132:257 */     return 800;
/* 133:    */   }
/* 134:    */   
/* 135:    */   protected static int getScreenHeight()
/* 136:    */   {
/* 137:    */     try
/* 138:    */     {
/* 139:268 */       return Toolkit.getDefaultToolkit().getScreenSize().height;
/* 140:    */     }
/* 141:    */     catch (Throwable t) {}
/* 142:270 */     return 600;
/* 143:    */   }
/* 144:    */   
/* 145:    */   protected static int getDefaultMonitorWidth()
/* 146:    */   {
/* 147:275 */     return 3 * getScreenWidth() / 4;
/* 148:    */   }
/* 149:    */   
/* 150:    */   protected static int getDefaultMonitorHeight()
/* 151:    */   {
/* 152:279 */     return 3 * getScreenHeight() / 4;
/* 153:    */   }
/* 154:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.lf5.util.LogMonitorAdapter
 * JD-Core Version:    0.7.0.1
 */