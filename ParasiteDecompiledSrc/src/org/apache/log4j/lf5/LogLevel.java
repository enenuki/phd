/*   1:    */ package org.apache.log4j.lf5;
/*   2:    */ 
/*   3:    */ import java.awt.Color;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.util.Arrays;
/*   6:    */ import java.util.HashMap;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ 
/*  11:    */ public class LogLevel
/*  12:    */   implements Serializable
/*  13:    */ {
/*  14: 48 */   public static final LogLevel FATAL = new LogLevel("FATAL", 0);
/*  15: 49 */   public static final LogLevel ERROR = new LogLevel("ERROR", 1);
/*  16: 50 */   public static final LogLevel WARN = new LogLevel("WARN", 2);
/*  17: 51 */   public static final LogLevel INFO = new LogLevel("INFO", 3);
/*  18: 52 */   public static final LogLevel DEBUG = new LogLevel("DEBUG", 4);
/*  19: 55 */   public static final LogLevel SEVERE = new LogLevel("SEVERE", 1);
/*  20: 56 */   public static final LogLevel WARNING = new LogLevel("WARNING", 2);
/*  21: 57 */   public static final LogLevel CONFIG = new LogLevel("CONFIG", 4);
/*  22: 58 */   public static final LogLevel FINE = new LogLevel("FINE", 5);
/*  23: 59 */   public static final LogLevel FINER = new LogLevel("FINER", 6);
/*  24: 60 */   public static final LogLevel FINEST = new LogLevel("FINEST", 7);
/*  25:    */   protected String _label;
/*  26:    */   protected int _precedence;
/*  27:    */   private static LogLevel[] _log4JLevels;
/*  28:    */   private static LogLevel[] _jdk14Levels;
/*  29:    */   private static LogLevel[] _allDefaultLevels;
/*  30:    */   private static Map _logLevelMap;
/*  31:    */   private static Map _logLevelColorMap;
/*  32: 75 */   private static Map _registeredLogLevelMap = new HashMap();
/*  33:    */   
/*  34:    */   static
/*  35:    */   {
/*  36: 81 */     _log4JLevels = new LogLevel[] { FATAL, ERROR, WARN, INFO, DEBUG };
/*  37: 82 */     _jdk14Levels = new LogLevel[] { SEVERE, WARNING, INFO, CONFIG, FINE, FINER, FINEST };
/*  38:    */     
/*  39: 84 */     _allDefaultLevels = new LogLevel[] { FATAL, ERROR, WARN, INFO, DEBUG, SEVERE, WARNING, CONFIG, FINE, FINER, FINEST };
/*  40:    */     
/*  41:    */ 
/*  42: 87 */     _logLevelMap = new HashMap();
/*  43: 88 */     for (int i = 0; i < _allDefaultLevels.length; i++) {
/*  44: 89 */       _logLevelMap.put(_allDefaultLevels[i].getLabel(), _allDefaultLevels[i]);
/*  45:    */     }
/*  46: 93 */     _logLevelColorMap = new HashMap();
/*  47: 94 */     for (int i = 0; i < _allDefaultLevels.length; i++) {
/*  48: 95 */       _logLevelColorMap.put(_allDefaultLevels[i], Color.black);
/*  49:    */     }
/*  50:    */   }
/*  51:    */   
/*  52:    */   public LogLevel(String label, int precedence)
/*  53:    */   {
/*  54:100 */     this._label = label;
/*  55:101 */     this._precedence = precedence;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public String getLabel()
/*  59:    */   {
/*  60:112 */     return this._label;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public boolean encompasses(LogLevel level)
/*  64:    */   {
/*  65:122 */     if (level.getPrecedence() <= getPrecedence()) {
/*  66:123 */       return true;
/*  67:    */     }
/*  68:126 */     return false;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public static LogLevel valueOf(String level)
/*  72:    */     throws LogLevelFormatException
/*  73:    */   {
/*  74:139 */     LogLevel logLevel = null;
/*  75:140 */     if (level != null)
/*  76:    */     {
/*  77:141 */       level = level.trim().toUpperCase();
/*  78:142 */       logLevel = (LogLevel)_logLevelMap.get(level);
/*  79:    */     }
/*  80:146 */     if ((logLevel == null) && (_registeredLogLevelMap.size() > 0)) {
/*  81:147 */       logLevel = (LogLevel)_registeredLogLevelMap.get(level);
/*  82:    */     }
/*  83:150 */     if (logLevel == null)
/*  84:    */     {
/*  85:151 */       StringBuffer buf = new StringBuffer();
/*  86:152 */       buf.append("Error while trying to parse (" + level + ") into");
/*  87:153 */       buf.append(" a LogLevel.");
/*  88:154 */       throw new LogLevelFormatException(buf.toString());
/*  89:    */     }
/*  90:156 */     return logLevel;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public static LogLevel register(LogLevel logLevel)
/*  94:    */   {
/*  95:166 */     if (logLevel == null) {
/*  96:166 */       return null;
/*  97:    */     }
/*  98:169 */     if (_logLevelMap.get(logLevel.getLabel()) == null) {
/*  99:170 */       return (LogLevel)_registeredLogLevelMap.put(logLevel.getLabel(), logLevel);
/* 100:    */     }
/* 101:173 */     return null;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public static void register(LogLevel[] logLevels)
/* 105:    */   {
/* 106:177 */     if (logLevels != null) {
/* 107:178 */       for (int i = 0; i < logLevels.length; i++) {
/* 108:179 */         register(logLevels[i]);
/* 109:    */       }
/* 110:    */     }
/* 111:    */   }
/* 112:    */   
/* 113:    */   public static void register(List logLevels)
/* 114:    */   {
/* 115:185 */     if (logLevels != null)
/* 116:    */     {
/* 117:186 */       Iterator it = logLevels.iterator();
/* 118:187 */       while (it.hasNext()) {
/* 119:188 */         register((LogLevel)it.next());
/* 120:    */       }
/* 121:    */     }
/* 122:    */   }
/* 123:    */   
/* 124:    */   public boolean equals(Object o)
/* 125:    */   {
/* 126:194 */     boolean equals = false;
/* 127:196 */     if (((o instanceof LogLevel)) && 
/* 128:197 */       (getPrecedence() == ((LogLevel)o).getPrecedence())) {
/* 129:199 */       equals = true;
/* 130:    */     }
/* 131:204 */     return equals;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public int hashCode()
/* 135:    */   {
/* 136:208 */     return this._label.hashCode();
/* 137:    */   }
/* 138:    */   
/* 139:    */   public String toString()
/* 140:    */   {
/* 141:212 */     return this._label;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void setLogLevelColorMap(LogLevel level, Color color)
/* 145:    */   {
/* 146:218 */     _logLevelColorMap.remove(level);
/* 147:220 */     if (color == null) {
/* 148:221 */       color = Color.black;
/* 149:    */     }
/* 150:223 */     _logLevelColorMap.put(level, color);
/* 151:    */   }
/* 152:    */   
/* 153:    */   public static void resetLogLevelColorMap()
/* 154:    */   {
/* 155:228 */     _logLevelColorMap.clear();
/* 156:231 */     for (int i = 0; i < _allDefaultLevels.length; i++) {
/* 157:232 */       _logLevelColorMap.put(_allDefaultLevels[i], Color.black);
/* 158:    */     }
/* 159:    */   }
/* 160:    */   
/* 161:    */   public static List getLog4JLevels()
/* 162:    */   {
/* 163:241 */     return Arrays.asList(_log4JLevels);
/* 164:    */   }
/* 165:    */   
/* 166:    */   public static List getJdk14Levels()
/* 167:    */   {
/* 168:245 */     return Arrays.asList(_jdk14Levels);
/* 169:    */   }
/* 170:    */   
/* 171:    */   public static List getAllDefaultLevels()
/* 172:    */   {
/* 173:249 */     return Arrays.asList(_allDefaultLevels);
/* 174:    */   }
/* 175:    */   
/* 176:    */   public static Map getLogLevelColorMap()
/* 177:    */   {
/* 178:253 */     return _logLevelColorMap;
/* 179:    */   }
/* 180:    */   
/* 181:    */   protected int getPrecedence()
/* 182:    */   {
/* 183:261 */     return this._precedence;
/* 184:    */   }
/* 185:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.lf5.LogLevel
 * JD-Core Version:    0.7.0.1
 */