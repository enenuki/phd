/*   1:    */ package nukic.parasite.utils;
/*   2:    */ 
/*   3:    */ import hr.nukic.parasite.core.PortfolioData;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.util.Date;
/*   6:    */ import org.apache.log4j.ConsoleAppender;
/*   7:    */ import org.apache.log4j.FileAppender;
/*   8:    */ import org.apache.log4j.Level;
/*   9:    */ import org.apache.log4j.Logger;
/*  10:    */ import org.apache.log4j.PatternLayout;
/*  11:    */ import org.apache.log4j.RollingFileAppender;
/*  12:    */ 
/*  13:    */ public class MainLogger
/*  14:    */ {
/*  15: 33 */   public static String logPath = ParasiteUtils.outputPath + "ParasiteTrade.log";
/*  16: 34 */   public static String rollLogPath = ParasiteUtils.outputPath + "ParasiteTradeRolling.log";
/*  17: 35 */   public static final Level DEFAULT_CONSOLE_LOG_LEVEL = Level.DEBUG;
/*  18: 36 */   public static final Level DEFAULT_FILE_LOG_LEVEL = Level.DEBUG;
/*  19: 37 */   public static final Level DEFAULT_ROLLING_FILE_LOG_LEVEL = Level.DEBUG;
/*  20: 40 */   public static Logger logger = Logger.getLogger(MainLogger.class);
/*  21: 41 */   private static Logger fileLogger = Logger.getLogger(ParasiteUtils.class);
/*  22: 42 */   private static Logger rollingFileLogger = Logger.getLogger(PortfolioData.class);
/*  23: 44 */   private static boolean fileLoggerEnabled = true;
/*  24: 45 */   private static RollingFileAppender rollingFileAppender = null;
/*  25: 46 */   private static boolean rolloverDone = false;
/*  26: 47 */   private static boolean rolloverEnabled = true;
/*  27: 49 */   public static int logFileRolloverHour = 16;
/*  28: 50 */   public static int logFileRolloverMinute = 10;
/*  29:    */   
/*  30:    */   static
/*  31:    */   {
/*  32: 56 */     logger.addAppender(new ConsoleAppender(new PatternLayout("%d{yyyy/MM/dd HH:mm:ss} [%t] %-5p: %m%n")));
/*  33: 57 */     logger.setLevel(DEFAULT_CONSOLE_LOG_LEVEL);
/*  34:    */     try
/*  35:    */     {
/*  36: 59 */       if (fileLoggerEnabled) {
/*  37: 60 */         fileLogger.addAppender(new FileAppender(new PatternLayout("%d{yyyy/MM/dd HH:mm:ss} [%t] %C{1}.%M %-5p: %m%n"), 
/*  38: 61 */           logPath));
/*  39:    */       }
/*  40: 64 */       if (rolloverEnabled)
/*  41:    */       {
/*  42: 65 */         rollingFileAppender = new RollingFileAppender(new PatternLayout("%d{yyyy/MM/dd HH:mm:ss} [%t] %C{1}.%M %-5p: %m%n"), 
/*  43: 66 */           rollLogPath, true);
/*  44: 67 */         rollingFileAppender.setMaxBackupIndex(1000);
/*  45: 68 */         rollingFileLogger.addAppender(rollingFileAppender);
/*  46:    */       }
/*  47:    */     }
/*  48:    */     catch (IOException e)
/*  49:    */     {
/*  50: 71 */       e.printStackTrace();
/*  51:    */     }
/*  52: 74 */     if (fileLoggerEnabled) {
/*  53: 75 */       fileLogger.setLevel(DEFAULT_FILE_LOG_LEVEL);
/*  54:    */     }
/*  55: 77 */     if (rolloverEnabled) {
/*  56: 78 */       rollingFileLogger.setLevel(DEFAULT_ROLLING_FILE_LOG_LEVEL);
/*  57:    */     }
/*  58:    */   }
/*  59:    */   
/*  60:    */   public static void setConsoleLoggerLevel(Level level)
/*  61:    */   {
/*  62: 83 */     logger.setLevel(level);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public static void setFileLoggerLevel(Level level)
/*  66:    */   {
/*  67: 87 */     fileLogger.setLevel(level);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public static void setRollingFileLoggerLevel(Level level)
/*  71:    */   {
/*  72: 91 */     rollingFileLogger.setLevel(level);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public static void enableRollingLogger()
/*  76:    */   {
/*  77: 95 */     rolloverEnabled = true;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public static void disableRollingLogger()
/*  81:    */   {
/*  82: 99 */     rolloverEnabled = false;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public static void setRolloverHour(int rolloverHour)
/*  86:    */   {
/*  87:103 */     logFileRolloverHour = rolloverHour;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public static void setRolloverMinute(int rolloverMin)
/*  91:    */   {
/*  92:107 */     logFileRolloverMinute = rolloverMin;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public static void info(String message)
/*  96:    */   {
/*  97:111 */     logger.info(message);
/*  98:113 */     if (fileLoggerEnabled) {
/*  99:114 */       fileLogger.info(message);
/* 100:    */     }
/* 101:117 */     if (rolloverEnabled)
/* 102:    */     {
/* 103:118 */       rollingFileLogger.info(message);
/* 104:119 */       Date now = new Date();
/* 105:120 */       if ((now.getHours() >= logFileRolloverHour) && 
/* 106:121 */         (now.getMinutes() >= logFileRolloverMinute) && (!rolloverDone))
/* 107:    */       {
/* 108:122 */         rolloverDone = true;
/* 109:123 */         rollingFileAppender.rollOver();
/* 110:    */       }
/* 111:    */     }
/* 112:    */   }
/* 113:    */   
/* 114:    */   public static void info(String message, Object o)
/* 115:    */   {
/* 116:130 */     logger.info(message + " " + o.toString());
/* 117:131 */     if (fileLoggerEnabled) {
/* 118:132 */       fileLogger.info(message + " " + o.toString());
/* 119:    */     }
/* 120:135 */     if (rolloverEnabled)
/* 121:    */     {
/* 122:136 */       rollingFileLogger.info(message + " " + o.toString());
/* 123:137 */       Date now = new Date();
/* 124:138 */       if ((now.getHours() >= logFileRolloverHour) && 
/* 125:139 */         (now.getMinutes() >= logFileRolloverMinute) && (!rolloverDone))
/* 126:    */       {
/* 127:140 */         rolloverDone = true;
/* 128:141 */         rollingFileAppender.rollOver();
/* 129:    */       }
/* 130:    */     }
/* 131:    */   }
/* 132:    */   
/* 133:    */   public static void info(String message, Throwable e)
/* 134:    */   {
/* 135:147 */     logger.info(message + " " + e.getMessage());
/* 136:148 */     if (fileLoggerEnabled) {
/* 137:149 */       fileLogger.info(message + " " + e.getMessage());
/* 138:    */     }
/* 139:152 */     if (rolloverEnabled)
/* 140:    */     {
/* 141:153 */       rollingFileLogger.info(message + " " + e.getMessage());
/* 142:    */       
/* 143:155 */       Date now = new Date();
/* 144:156 */       if ((now.getHours() >= logFileRolloverHour) && 
/* 145:157 */         (now.getMinutes() >= logFileRolloverMinute) && (!rolloverDone))
/* 146:    */       {
/* 147:158 */         rolloverDone = true;
/* 148:159 */         rollingFileAppender.rollOver();
/* 149:    */       }
/* 150:    */     }
/* 151:    */   }
/* 152:    */   
/* 153:    */   public static void debug(String message)
/* 154:    */   {
/* 155:165 */     logger.debug(message);
/* 156:166 */     if (fileLoggerEnabled) {
/* 157:167 */       fileLogger.debug(message);
/* 158:    */     }
/* 159:170 */     if (rolloverEnabled)
/* 160:    */     {
/* 161:171 */       rollingFileLogger.debug(message);
/* 162:172 */       Date now = new Date();
/* 163:173 */       if ((now.getHours() >= logFileRolloverHour) && 
/* 164:174 */         (now.getMinutes() >= logFileRolloverMinute) && (!rolloverDone))
/* 165:    */       {
/* 166:175 */         rolloverDone = true;
/* 167:176 */         rollingFileAppender.rollOver();
/* 168:    */       }
/* 169:    */     }
/* 170:    */   }
/* 171:    */   
/* 172:    */   public static void debug(String message, Object o)
/* 173:    */   {
/* 174:183 */     logger.debug(message + " " + o.toString());
/* 175:184 */     if (fileLoggerEnabled) {
/* 176:185 */       fileLogger.debug(message + " " + o.toString());
/* 177:    */     }
/* 178:188 */     if (rolloverEnabled)
/* 179:    */     {
/* 180:189 */       rollingFileLogger.debug(message + " " + o.toString());
/* 181:    */       
/* 182:191 */       Date now = new Date();
/* 183:192 */       if ((now.getHours() >= logFileRolloverHour) && 
/* 184:193 */         (now.getMinutes() >= logFileRolloverMinute) && (!rolloverDone))
/* 185:    */       {
/* 186:194 */         rolloverDone = true;
/* 187:195 */         rollingFileAppender.rollOver();
/* 188:    */       }
/* 189:    */     }
/* 190:    */   }
/* 191:    */   
/* 192:    */   public static void debug(String message, Throwable e)
/* 193:    */   {
/* 194:201 */     logger.debug(message + " " + e.getMessage());
/* 195:202 */     e.printStackTrace();
/* 196:203 */     if (fileLoggerEnabled) {
/* 197:204 */       fileLogger.debug(message + " " + e.getMessage());
/* 198:    */     }
/* 199:207 */     if (rolloverEnabled)
/* 200:    */     {
/* 201:208 */       rollingFileLogger.debug(message + " " + e.getMessage());
/* 202:209 */       Date now = new Date();
/* 203:210 */       if ((now.getHours() >= logFileRolloverHour) && 
/* 204:211 */         (now.getMinutes() >= logFileRolloverMinute) && (!rolloverDone))
/* 205:    */       {
/* 206:212 */         rolloverDone = true;
/* 207:213 */         rollingFileAppender.rollOver();
/* 208:    */       }
/* 209:    */     }
/* 210:    */   }
/* 211:    */   
/* 212:    */   public static void debug(Throwable e)
/* 213:    */   {
/* 214:219 */     logger.debug(e.getMessage());
/* 215:220 */     e.printStackTrace();
/* 216:221 */     if (fileLoggerEnabled) {
/* 217:222 */       fileLogger.debug(e.getMessage());
/* 218:    */     }
/* 219:225 */     if (rolloverEnabled)
/* 220:    */     {
/* 221:226 */       rollingFileLogger.debug(e.getMessage());
/* 222:227 */       Date now = new Date();
/* 223:228 */       if ((now.getHours() >= logFileRolloverHour) && 
/* 224:229 */         (now.getMinutes() >= logFileRolloverMinute) && (!rolloverDone))
/* 225:    */       {
/* 226:230 */         rolloverDone = true;
/* 227:231 */         rollingFileAppender.rollOver();
/* 228:    */       }
/* 229:    */     }
/* 230:    */   }
/* 231:    */   
/* 232:    */   public static void error(String message)
/* 233:    */   {
/* 234:237 */     logger.error(message);
/* 235:238 */     if (fileLoggerEnabled) {
/* 236:239 */       fileLogger.error(message);
/* 237:    */     }
/* 238:242 */     if (rolloverEnabled)
/* 239:    */     {
/* 240:243 */       rollingFileLogger.error(message);
/* 241:244 */       Date now = new Date();
/* 242:245 */       if ((now.getHours() >= logFileRolloverHour) && 
/* 243:246 */         (now.getMinutes() >= logFileRolloverMinute) && (!rolloverDone))
/* 244:    */       {
/* 245:247 */         rolloverDone = true;
/* 246:248 */         rollingFileAppender.rollOver();
/* 247:    */       }
/* 248:    */     }
/* 249:    */   }
/* 250:    */   
/* 251:    */   public static void error(String message, Throwable e)
/* 252:    */   {
/* 253:254 */     logger.error(message + " " + e.getMessage());
/* 254:255 */     e.printStackTrace();
/* 255:256 */     if (fileLoggerEnabled) {
/* 256:257 */       fileLogger.error(message + " " + e.getMessage());
/* 257:    */     }
/* 258:260 */     if (rolloverEnabled)
/* 259:    */     {
/* 260:261 */       rollingFileLogger.error(message + " " + e.getMessage());
/* 261:262 */       Date now = new Date();
/* 262:263 */       if ((now.getHours() >= logFileRolloverHour) && 
/* 263:264 */         (now.getMinutes() >= logFileRolloverMinute) && (!rolloverDone))
/* 264:    */       {
/* 265:265 */         rolloverDone = true;
/* 266:266 */         rollingFileAppender.rollOver();
/* 267:    */       }
/* 268:    */     }
/* 269:    */   }
/* 270:    */   
/* 271:    */   public static void error(Throwable e)
/* 272:    */   {
/* 273:272 */     logger.error(e.getMessage());
/* 274:273 */     e.printStackTrace();
/* 275:274 */     if (fileLoggerEnabled) {
/* 276:275 */       fileLogger.error(e.getMessage());
/* 277:    */     }
/* 278:278 */     if (rolloverEnabled)
/* 279:    */     {
/* 280:279 */       rollingFileLogger.error(e.getMessage());
/* 281:280 */       Date now = new Date();
/* 282:281 */       if ((now.getHours() >= logFileRolloverHour) && 
/* 283:282 */         (now.getMinutes() >= logFileRolloverMinute) && (!rolloverDone))
/* 284:    */       {
/* 285:283 */         rolloverDone = true;
/* 286:284 */         rollingFileAppender.rollOver();
/* 287:    */       }
/* 288:    */     }
/* 289:    */   }
/* 290:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     nukic.parasite.utils.MainLogger
 * JD-Core Version:    0.7.0.1
 */