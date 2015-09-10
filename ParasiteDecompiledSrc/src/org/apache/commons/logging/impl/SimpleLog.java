/*   1:    */ package org.apache.commons.logging.impl;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.io.PrintStream;
/*   6:    */ import java.io.PrintWriter;
/*   7:    */ import java.io.Serializable;
/*   8:    */ import java.io.StringWriter;
/*   9:    */ import java.lang.reflect.InvocationTargetException;
/*  10:    */ import java.lang.reflect.Method;
/*  11:    */ import java.security.AccessController;
/*  12:    */ import java.security.PrivilegedAction;
/*  13:    */ import java.text.DateFormat;
/*  14:    */ import java.text.SimpleDateFormat;
/*  15:    */ import java.util.Date;
/*  16:    */ import java.util.Properties;
/*  17:    */ import org.apache.commons.logging.Log;
/*  18:    */ import org.apache.commons.logging.LogConfigurationException;
/*  19:    */ 
/*  20:    */ public class SimpleLog
/*  21:    */   implements Log, Serializable
/*  22:    */ {
/*  23:    */   protected static final String systemPrefix = "org.apache.commons.logging.simplelog.";
/*  24: 86 */   protected static final Properties simpleLogProps = new Properties();
/*  25:    */   protected static final String DEFAULT_DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm:ss:SSS zzz";
/*  26: 93 */   protected static boolean showLogName = false;
/*  27: 98 */   protected static boolean showShortName = true;
/*  28:100 */   protected static boolean showDateTime = false;
/*  29:102 */   protected static String dateTimeFormat = "yyyy/MM/dd HH:mm:ss:SSS zzz";
/*  30:112 */   protected static DateFormat dateFormatter = null;
/*  31:    */   public static final int LOG_LEVEL_TRACE = 1;
/*  32:    */   public static final int LOG_LEVEL_DEBUG = 2;
/*  33:    */   public static final int LOG_LEVEL_INFO = 3;
/*  34:    */   public static final int LOG_LEVEL_WARN = 4;
/*  35:    */   public static final int LOG_LEVEL_ERROR = 5;
/*  36:    */   public static final int LOG_LEVEL_FATAL = 6;
/*  37:    */   public static final int LOG_LEVEL_ALL = 0;
/*  38:    */   public static final int LOG_LEVEL_OFF = 7;
/*  39:    */   
/*  40:    */   private static String getStringProperty(String name)
/*  41:    */   {
/*  42:139 */     String prop = null;
/*  43:    */     try
/*  44:    */     {
/*  45:141 */       prop = System.getProperty(name);
/*  46:    */     }
/*  47:    */     catch (SecurityException e) {}
/*  48:145 */     return prop == null ? simpleLogProps.getProperty(name) : prop;
/*  49:    */   }
/*  50:    */   
/*  51:    */   private static String getStringProperty(String name, String dephault)
/*  52:    */   {
/*  53:149 */     String prop = getStringProperty(name);
/*  54:150 */     return prop == null ? dephault : prop;
/*  55:    */   }
/*  56:    */   
/*  57:    */   private static boolean getBooleanProperty(String name, boolean dephault)
/*  58:    */   {
/*  59:154 */     String prop = getStringProperty(name);
/*  60:155 */     return prop == null ? dephault : "true".equalsIgnoreCase(prop);
/*  61:    */   }
/*  62:    */   
/*  63:    */   static
/*  64:    */   {
/*  65:163 */     InputStream in = getResourceAsStream("simplelog.properties");
/*  66:164 */     if (null != in) {
/*  67:    */       try
/*  68:    */       {
/*  69:166 */         simpleLogProps.load(in);
/*  70:167 */         in.close();
/*  71:    */       }
/*  72:    */       catch (IOException e) {}
/*  73:    */     }
/*  74:173 */     showLogName = getBooleanProperty("org.apache.commons.logging.simplelog.showlogname", showLogName);
/*  75:174 */     showShortName = getBooleanProperty("org.apache.commons.logging.simplelog.showShortLogname", showShortName);
/*  76:175 */     showDateTime = getBooleanProperty("org.apache.commons.logging.simplelog.showdatetime", showDateTime);
/*  77:177 */     if (showDateTime)
/*  78:    */     {
/*  79:178 */       dateTimeFormat = getStringProperty("org.apache.commons.logging.simplelog.dateTimeFormat", dateTimeFormat);
/*  80:    */       try
/*  81:    */       {
/*  82:181 */         dateFormatter = new SimpleDateFormat(dateTimeFormat);
/*  83:    */       }
/*  84:    */       catch (IllegalArgumentException e)
/*  85:    */       {
/*  86:184 */         dateTimeFormat = "yyyy/MM/dd HH:mm:ss:SSS zzz";
/*  87:185 */         dateFormatter = new SimpleDateFormat(dateTimeFormat);
/*  88:    */       }
/*  89:    */     }
/*  90:    */   }
/*  91:    */   
/*  92:193 */   protected String logName = null;
/*  93:    */   protected int currentLogLevel;
/*  94:197 */   private String shortLogName = null;
/*  95:    */   
/*  96:    */   public SimpleLog(String name)
/*  97:    */   {
/*  98:209 */     this.logName = name;
/*  99:    */     
/* 100:    */ 
/* 101:    */ 
/* 102:    */ 
/* 103:214 */     setLevel(3);
/* 104:    */     
/* 105:    */ 
/* 106:217 */     String lvl = getStringProperty("org.apache.commons.logging.simplelog.log." + this.logName);
/* 107:218 */     int i = String.valueOf(name).lastIndexOf(".");
/* 108:219 */     while ((null == lvl) && (i > -1))
/* 109:    */     {
/* 110:220 */       name = name.substring(0, i);
/* 111:221 */       lvl = getStringProperty("org.apache.commons.logging.simplelog.log." + name);
/* 112:222 */       i = String.valueOf(name).lastIndexOf(".");
/* 113:    */     }
/* 114:225 */     if (null == lvl) {
/* 115:226 */       lvl = getStringProperty("org.apache.commons.logging.simplelog.defaultlog");
/* 116:    */     }
/* 117:229 */     if ("all".equalsIgnoreCase(lvl)) {
/* 118:230 */       setLevel(0);
/* 119:231 */     } else if ("trace".equalsIgnoreCase(lvl)) {
/* 120:232 */       setLevel(1);
/* 121:233 */     } else if ("debug".equalsIgnoreCase(lvl)) {
/* 122:234 */       setLevel(2);
/* 123:235 */     } else if ("info".equalsIgnoreCase(lvl)) {
/* 124:236 */       setLevel(3);
/* 125:237 */     } else if ("warn".equalsIgnoreCase(lvl)) {
/* 126:238 */       setLevel(4);
/* 127:239 */     } else if ("error".equalsIgnoreCase(lvl)) {
/* 128:240 */       setLevel(5);
/* 129:241 */     } else if ("fatal".equalsIgnoreCase(lvl)) {
/* 130:242 */       setLevel(6);
/* 131:243 */     } else if ("off".equalsIgnoreCase(lvl)) {
/* 132:244 */       setLevel(7);
/* 133:    */     }
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void setLevel(int currentLogLevel)
/* 137:    */   {
/* 138:259 */     this.currentLogLevel = currentLogLevel;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public int getLevel()
/* 142:    */   {
/* 143:269 */     return this.currentLogLevel;
/* 144:    */   }
/* 145:    */   
/* 146:    */   protected void log(int type, Object message, Throwable t)
/* 147:    */   {
/* 148:287 */     StringBuffer buf = new StringBuffer();
/* 149:290 */     if (showDateTime)
/* 150:    */     {
/* 151:291 */       Date now = new Date();
/* 152:    */       String dateText;
/* 153:293 */       synchronized (dateFormatter)
/* 154:    */       {
/* 155:294 */         dateText = dateFormatter.format(now);
/* 156:    */       }
/* 157:    */       String dateText;
/* 158:296 */       buf.append(dateText);
/* 159:297 */       buf.append(" ");
/* 160:    */     }
/* 161:301 */     switch (type)
/* 162:    */     {
/* 163:    */     case 1: 
/* 164:302 */       buf.append("[TRACE] "); break;
/* 165:    */     case 2: 
/* 166:303 */       buf.append("[DEBUG] "); break;
/* 167:    */     case 3: 
/* 168:304 */       buf.append("[INFO] "); break;
/* 169:    */     case 4: 
/* 170:305 */       buf.append("[WARN] "); break;
/* 171:    */     case 5: 
/* 172:306 */       buf.append("[ERROR] "); break;
/* 173:    */     case 6: 
/* 174:307 */       buf.append("[FATAL] ");
/* 175:    */     }
/* 176:311 */     if (showShortName)
/* 177:    */     {
/* 178:312 */       if (this.shortLogName == null)
/* 179:    */       {
/* 180:314 */         this.shortLogName = this.logName.substring(this.logName.lastIndexOf(".") + 1);
/* 181:315 */         this.shortLogName = this.shortLogName.substring(this.shortLogName.lastIndexOf("/") + 1);
/* 182:    */       }
/* 183:318 */       buf.append(String.valueOf(this.shortLogName)).append(" - ");
/* 184:    */     }
/* 185:319 */     else if (showLogName)
/* 186:    */     {
/* 187:320 */       buf.append(String.valueOf(this.logName)).append(" - ");
/* 188:    */     }
/* 189:324 */     buf.append(String.valueOf(message));
/* 190:327 */     if (t != null)
/* 191:    */     {
/* 192:328 */       buf.append(" <");
/* 193:329 */       buf.append(t.toString());
/* 194:330 */       buf.append(">");
/* 195:    */       
/* 196:332 */       StringWriter sw = new StringWriter(1024);
/* 197:333 */       PrintWriter pw = new PrintWriter(sw);
/* 198:334 */       t.printStackTrace(pw);
/* 199:335 */       pw.close();
/* 200:336 */       buf.append(sw.toString());
/* 201:    */     }
/* 202:340 */     write(buf);
/* 203:    */   }
/* 204:    */   
/* 205:    */   protected void write(StringBuffer buffer)
/* 206:    */   {
/* 207:355 */     System.err.println(buffer.toString());
/* 208:    */   }
/* 209:    */   
/* 210:    */   protected boolean isLevelEnabled(int logLevel)
/* 211:    */   {
/* 212:368 */     return logLevel >= this.currentLogLevel;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public final void debug(Object message)
/* 216:    */   {
/* 217:384 */     if (isLevelEnabled(2)) {
/* 218:385 */       log(2, message, null);
/* 219:    */     }
/* 220:    */   }
/* 221:    */   
/* 222:    */   public final void debug(Object message, Throwable t)
/* 223:    */   {
/* 224:400 */     if (isLevelEnabled(2)) {
/* 225:401 */       log(2, message, t);
/* 226:    */     }
/* 227:    */   }
/* 228:    */   
/* 229:    */   public final void trace(Object message)
/* 230:    */   {
/* 231:415 */     if (isLevelEnabled(1)) {
/* 232:416 */       log(1, message, null);
/* 233:    */     }
/* 234:    */   }
/* 235:    */   
/* 236:    */   public final void trace(Object message, Throwable t)
/* 237:    */   {
/* 238:431 */     if (isLevelEnabled(1)) {
/* 239:432 */       log(1, message, t);
/* 240:    */     }
/* 241:    */   }
/* 242:    */   
/* 243:    */   public final void info(Object message)
/* 244:    */   {
/* 245:446 */     if (isLevelEnabled(3)) {
/* 246:447 */       log(3, message, null);
/* 247:    */     }
/* 248:    */   }
/* 249:    */   
/* 250:    */   public final void info(Object message, Throwable t)
/* 251:    */   {
/* 252:462 */     if (isLevelEnabled(3)) {
/* 253:463 */       log(3, message, t);
/* 254:    */     }
/* 255:    */   }
/* 256:    */   
/* 257:    */   public final void warn(Object message)
/* 258:    */   {
/* 259:477 */     if (isLevelEnabled(4)) {
/* 260:478 */       log(4, message, null);
/* 261:    */     }
/* 262:    */   }
/* 263:    */   
/* 264:    */   public final void warn(Object message, Throwable t)
/* 265:    */   {
/* 266:493 */     if (isLevelEnabled(4)) {
/* 267:494 */       log(4, message, t);
/* 268:    */     }
/* 269:    */   }
/* 270:    */   
/* 271:    */   public final void error(Object message)
/* 272:    */   {
/* 273:508 */     if (isLevelEnabled(5)) {
/* 274:509 */       log(5, message, null);
/* 275:    */     }
/* 276:    */   }
/* 277:    */   
/* 278:    */   public final void error(Object message, Throwable t)
/* 279:    */   {
/* 280:524 */     if (isLevelEnabled(5)) {
/* 281:525 */       log(5, message, t);
/* 282:    */     }
/* 283:    */   }
/* 284:    */   
/* 285:    */   public final void fatal(Object message)
/* 286:    */   {
/* 287:539 */     if (isLevelEnabled(6)) {
/* 288:540 */       log(6, message, null);
/* 289:    */     }
/* 290:    */   }
/* 291:    */   
/* 292:    */   public final void fatal(Object message, Throwable t)
/* 293:    */   {
/* 294:555 */     if (isLevelEnabled(6)) {
/* 295:556 */       log(6, message, t);
/* 296:    */     }
/* 297:    */   }
/* 298:    */   
/* 299:    */   public final boolean isDebugEnabled()
/* 300:    */   {
/* 301:570 */     return isLevelEnabled(2);
/* 302:    */   }
/* 303:    */   
/* 304:    */   public final boolean isErrorEnabled()
/* 305:    */   {
/* 306:583 */     return isLevelEnabled(5);
/* 307:    */   }
/* 308:    */   
/* 309:    */   public final boolean isFatalEnabled()
/* 310:    */   {
/* 311:596 */     return isLevelEnabled(6);
/* 312:    */   }
/* 313:    */   
/* 314:    */   public final boolean isInfoEnabled()
/* 315:    */   {
/* 316:609 */     return isLevelEnabled(3);
/* 317:    */   }
/* 318:    */   
/* 319:    */   public final boolean isTraceEnabled()
/* 320:    */   {
/* 321:622 */     return isLevelEnabled(1);
/* 322:    */   }
/* 323:    */   
/* 324:    */   public final boolean isWarnEnabled()
/* 325:    */   {
/* 326:635 */     return isLevelEnabled(4);
/* 327:    */   }
/* 328:    */   
/* 329:    */   private static ClassLoader getContextClassLoader()
/* 330:    */   {
/* 331:651 */     ClassLoader classLoader = null;
/* 332:653 */     if (classLoader == null) {
/* 333:    */       try
/* 334:    */       {
/* 335:656 */         Method method = Thread.class.getMethod("getContextClassLoader", (Class[])null);
/* 336:    */         try
/* 337:    */         {
/* 338:661 */           classLoader = (ClassLoader)method.invoke(Thread.currentThread(), (Class[])null);
/* 339:    */         }
/* 340:    */         catch (IllegalAccessException e) {}catch (InvocationTargetException e)
/* 341:    */         {
/* 342:682 */           if (!(e.getTargetException() instanceof SecurityException)) {
/* 343:687 */             throw new LogConfigurationException("Unexpected InvocationTargetException", e.getTargetException());
/* 344:    */           }
/* 345:    */         }
/* 346:    */       }
/* 347:    */       catch (NoSuchMethodException e) {}
/* 348:    */     }
/* 349:697 */     if (classLoader == null) {
/* 350:698 */       classLoader = SimpleLog.class.getClassLoader();
/* 351:    */     }
/* 352:702 */     return classLoader;
/* 353:    */   }
/* 354:    */   
/* 355:    */   private static InputStream getResourceAsStream(String name)
/* 356:    */   {
/* 357:707 */     (InputStream)AccessController.doPrivileged(new PrivilegedAction()
/* 358:    */     {
/* 359:    */       private final String val$name;
/* 360:    */       
/* 361:    */       public Object run()
/* 362:    */       {
/* 363:710 */         ClassLoader threadCL = SimpleLog.access$000();
/* 364:712 */         if (threadCL != null) {
/* 365:713 */           return threadCL.getResourceAsStream(this.val$name);
/* 366:    */         }
/* 367:715 */         return ClassLoader.getSystemResourceAsStream(this.val$name);
/* 368:    */       }
/* 369:    */     });
/* 370:    */   }
/* 371:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.logging.impl.SimpleLog
 * JD-Core Version:    0.7.0.1
 */