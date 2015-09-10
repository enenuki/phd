/*   1:    */ package org.apache.log4j.spi;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InterruptedIOException;
/*   5:    */ import java.io.ObjectInputStream;
/*   6:    */ import java.io.ObjectOutputStream;
/*   7:    */ import java.io.Serializable;
/*   8:    */ import java.lang.reflect.InvocationTargetException;
/*   9:    */ import java.lang.reflect.Method;
/*  10:    */ import java.util.Collections;
/*  11:    */ import java.util.HashMap;
/*  12:    */ import java.util.Hashtable;
/*  13:    */ import java.util.Map;
/*  14:    */ import java.util.Set;
/*  15:    */ import org.apache.log4j.Category;
/*  16:    */ import org.apache.log4j.Level;
/*  17:    */ import org.apache.log4j.MDC;
/*  18:    */ import org.apache.log4j.NDC;
/*  19:    */ import org.apache.log4j.Priority;
/*  20:    */ import org.apache.log4j.helpers.Loader;
/*  21:    */ import org.apache.log4j.helpers.LogLog;
/*  22:    */ import org.apache.log4j.or.RendererMap;
/*  23:    */ 
/*  24:    */ public class LoggingEvent
/*  25:    */   implements Serializable
/*  26:    */ {
/*  27: 57 */   private static long startTime = ;
/*  28:    */   public final transient String fqnOfCategoryClass;
/*  29:    */   /**
/*  30:    */    * @deprecated
/*  31:    */    */
/*  32:    */   private transient Category logger;
/*  33:    */   /**
/*  34:    */    * @deprecated
/*  35:    */    */
/*  36:    */   public final String categoryName;
/*  37:    */   /**
/*  38:    */    * @deprecated
/*  39:    */    */
/*  40:    */   public transient Priority level;
/*  41:    */   private String ndc;
/*  42:    */   private Hashtable mdcCopy;
/*  43:109 */   private boolean ndcLookupRequired = true;
/*  44:115 */   private boolean mdcCopyLookupRequired = true;
/*  45:    */   private transient Object message;
/*  46:    */   private String renderedMessage;
/*  47:    */   private String threadName;
/*  48:    */   private ThrowableInformation throwableInfo;
/*  49:    */   public final long timeStamp;
/*  50:    */   private LocationInfo locationInfo;
/*  51:    */   static final long serialVersionUID = -868428216207166145L;
/*  52:142 */   static final Integer[] PARAM_ARRAY = new Integer[1];
/*  53:    */   static final String TO_LEVEL = "toLevel";
/*  54:144 */   static final Class[] TO_LEVEL_PARAMS = { Integer.TYPE };
/*  55:145 */   static final Hashtable methodCache = new Hashtable(3);
/*  56:    */   
/*  57:    */   public LoggingEvent(String fqnOfCategoryClass, Category logger, Priority level, Object message, Throwable throwable)
/*  58:    */   {
/*  59:159 */     this.fqnOfCategoryClass = fqnOfCategoryClass;
/*  60:160 */     this.logger = logger;
/*  61:161 */     this.categoryName = logger.getName();
/*  62:162 */     this.level = level;
/*  63:163 */     this.message = message;
/*  64:164 */     if (throwable != null) {
/*  65:165 */       this.throwableInfo = new ThrowableInformation(throwable, logger);
/*  66:    */     }
/*  67:167 */     this.timeStamp = System.currentTimeMillis();
/*  68:    */   }
/*  69:    */   
/*  70:    */   public LoggingEvent(String fqnOfCategoryClass, Category logger, long timeStamp, Priority level, Object message, Throwable throwable)
/*  71:    */   {
/*  72:184 */     this.fqnOfCategoryClass = fqnOfCategoryClass;
/*  73:185 */     this.logger = logger;
/*  74:186 */     this.categoryName = logger.getName();
/*  75:187 */     this.level = level;
/*  76:188 */     this.message = message;
/*  77:189 */     if (throwable != null) {
/*  78:190 */       this.throwableInfo = new ThrowableInformation(throwable, logger);
/*  79:    */     }
/*  80:193 */     this.timeStamp = timeStamp;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public LoggingEvent(String fqnOfCategoryClass, Category logger, long timeStamp, Level level, Object message, String threadName, ThrowableInformation throwable, String ndc, LocationInfo info, Map properties)
/*  84:    */   {
/*  85:222 */     this.fqnOfCategoryClass = fqnOfCategoryClass;
/*  86:223 */     this.logger = logger;
/*  87:224 */     if (logger != null) {
/*  88:225 */       this.categoryName = logger.getName();
/*  89:    */     } else {
/*  90:227 */       this.categoryName = null;
/*  91:    */     }
/*  92:229 */     this.level = level;
/*  93:230 */     this.message = message;
/*  94:231 */     if (throwable != null) {
/*  95:232 */       this.throwableInfo = throwable;
/*  96:    */     }
/*  97:235 */     this.timeStamp = timeStamp;
/*  98:236 */     this.threadName = threadName;
/*  99:237 */     this.ndcLookupRequired = false;
/* 100:238 */     this.ndc = ndc;
/* 101:239 */     this.locationInfo = info;
/* 102:240 */     this.mdcCopyLookupRequired = false;
/* 103:241 */     if (properties != null) {
/* 104:242 */       this.mdcCopy = new Hashtable(properties);
/* 105:    */     }
/* 106:    */   }
/* 107:    */   
/* 108:    */   public LocationInfo getLocationInformation()
/* 109:    */   {
/* 110:252 */     if (this.locationInfo == null) {
/* 111:253 */       this.locationInfo = new LocationInfo(new Throwable(), this.fqnOfCategoryClass);
/* 112:    */     }
/* 113:255 */     return this.locationInfo;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public Level getLevel()
/* 117:    */   {
/* 118:262 */     return (Level)this.level;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public String getLoggerName()
/* 122:    */   {
/* 123:270 */     return this.categoryName;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public Category getLogger()
/* 127:    */   {
/* 128:279 */     return this.logger;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public Object getMessage()
/* 132:    */   {
/* 133:293 */     if (this.message != null) {
/* 134:294 */       return this.message;
/* 135:    */     }
/* 136:296 */     return getRenderedMessage();
/* 137:    */   }
/* 138:    */   
/* 139:    */   public String getNDC()
/* 140:    */   {
/* 141:307 */     if (this.ndcLookupRequired)
/* 142:    */     {
/* 143:308 */       this.ndcLookupRequired = false;
/* 144:309 */       this.ndc = NDC.get();
/* 145:    */     }
/* 146:311 */     return this.ndc;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public Object getMDC(String key)
/* 150:    */   {
/* 151:332 */     if (this.mdcCopy != null)
/* 152:    */     {
/* 153:333 */       Object r = this.mdcCopy.get(key);
/* 154:334 */       if (r != null) {
/* 155:335 */         return r;
/* 156:    */       }
/* 157:    */     }
/* 158:338 */     return MDC.get(key);
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void getMDCCopy()
/* 162:    */   {
/* 163:347 */     if (this.mdcCopyLookupRequired)
/* 164:    */     {
/* 165:348 */       this.mdcCopyLookupRequired = false;
/* 166:    */       
/* 167:    */ 
/* 168:351 */       Hashtable t = MDC.getContext();
/* 169:352 */       if (t != null) {
/* 170:353 */         this.mdcCopy = ((Hashtable)t.clone());
/* 171:    */       }
/* 172:    */     }
/* 173:    */   }
/* 174:    */   
/* 175:    */   public String getRenderedMessage()
/* 176:    */   {
/* 177:360 */     if ((this.renderedMessage == null) && (this.message != null)) {
/* 178:361 */       if ((this.message instanceof String))
/* 179:    */       {
/* 180:362 */         this.renderedMessage = ((String)this.message);
/* 181:    */       }
/* 182:    */       else
/* 183:    */       {
/* 184:364 */         LoggerRepository repository = this.logger.getLoggerRepository();
/* 185:366 */         if ((repository instanceof RendererSupport))
/* 186:    */         {
/* 187:367 */           RendererSupport rs = (RendererSupport)repository;
/* 188:368 */           this.renderedMessage = rs.getRendererMap().findAndRender(this.message);
/* 189:    */         }
/* 190:    */         else
/* 191:    */         {
/* 192:370 */           this.renderedMessage = this.message.toString();
/* 193:    */         }
/* 194:    */       }
/* 195:    */     }
/* 196:374 */     return this.renderedMessage;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public static long getStartTime()
/* 200:    */   {
/* 201:381 */     return startTime;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public String getThreadName()
/* 205:    */   {
/* 206:386 */     if (this.threadName == null) {
/* 207:387 */       this.threadName = Thread.currentThread().getName();
/* 208:    */     }
/* 209:388 */     return this.threadName;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public ThrowableInformation getThrowableInformation()
/* 213:    */   {
/* 214:401 */     return this.throwableInfo;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public String[] getThrowableStrRep()
/* 218:    */   {
/* 219:410 */     if (this.throwableInfo == null) {
/* 220:411 */       return null;
/* 221:    */     }
/* 222:413 */     return this.throwableInfo.getThrowableStrRep();
/* 223:    */   }
/* 224:    */   
/* 225:    */   private void readLevel(ObjectInputStream ois)
/* 226:    */     throws IOException, ClassNotFoundException
/* 227:    */   {
/* 228:421 */     int p = ois.readInt();
/* 229:    */     try
/* 230:    */     {
/* 231:423 */       String className = (String)ois.readObject();
/* 232:424 */       if (className == null)
/* 233:    */       {
/* 234:425 */         this.level = Level.toLevel(p);
/* 235:    */       }
/* 236:    */       else
/* 237:    */       {
/* 238:427 */         Method m = (Method)methodCache.get(className);
/* 239:428 */         if (m == null)
/* 240:    */         {
/* 241:429 */           Class clazz = Loader.loadClass(className);
/* 242:    */           
/* 243:    */ 
/* 244:    */ 
/* 245:    */ 
/* 246:    */ 
/* 247:    */ 
/* 248:436 */           m = clazz.getDeclaredMethod("toLevel", TO_LEVEL_PARAMS);
/* 249:437 */           methodCache.put(className, m);
/* 250:    */         }
/* 251:439 */         PARAM_ARRAY[0] = new Integer(p);
/* 252:440 */         this.level = ((Level)m.invoke(null, PARAM_ARRAY));
/* 253:    */       }
/* 254:    */     }
/* 255:    */     catch (InvocationTargetException e)
/* 256:    */     {
/* 257:443 */       if (((e.getTargetException() instanceof InterruptedException)) || ((e.getTargetException() instanceof InterruptedIOException))) {
/* 258:445 */         Thread.currentThread().interrupt();
/* 259:    */       }
/* 260:447 */       LogLog.warn("Level deserialization failed, reverting to default.", e);
/* 261:448 */       this.level = Level.toLevel(p);
/* 262:    */     }
/* 263:    */     catch (NoSuchMethodException e)
/* 264:    */     {
/* 265:450 */       LogLog.warn("Level deserialization failed, reverting to default.", e);
/* 266:451 */       this.level = Level.toLevel(p);
/* 267:    */     }
/* 268:    */     catch (IllegalAccessException e)
/* 269:    */     {
/* 270:453 */       LogLog.warn("Level deserialization failed, reverting to default.", e);
/* 271:454 */       this.level = Level.toLevel(p);
/* 272:    */     }
/* 273:    */     catch (RuntimeException e)
/* 274:    */     {
/* 275:456 */       LogLog.warn("Level deserialization failed, reverting to default.", e);
/* 276:457 */       this.level = Level.toLevel(p);
/* 277:    */     }
/* 278:    */   }
/* 279:    */   
/* 280:    */   private void readObject(ObjectInputStream ois)
/* 281:    */     throws IOException, ClassNotFoundException
/* 282:    */   {
/* 283:463 */     ois.defaultReadObject();
/* 284:464 */     readLevel(ois);
/* 285:467 */     if (this.locationInfo == null) {
/* 286:468 */       this.locationInfo = new LocationInfo(null, null);
/* 287:    */     }
/* 288:    */   }
/* 289:    */   
/* 290:    */   private void writeObject(ObjectOutputStream oos)
/* 291:    */     throws IOException
/* 292:    */   {
/* 293:475 */     getThreadName();
/* 294:    */     
/* 295:    */ 
/* 296:478 */     getRenderedMessage();
/* 297:    */     
/* 298:    */ 
/* 299:    */ 
/* 300:482 */     getNDC();
/* 301:    */     
/* 302:    */ 
/* 303:    */ 
/* 304:486 */     getMDCCopy();
/* 305:    */     
/* 306:    */ 
/* 307:489 */     getThrowableStrRep();
/* 308:    */     
/* 309:491 */     oos.defaultWriteObject();
/* 310:    */     
/* 311:    */ 
/* 312:494 */     writeLevel(oos);
/* 313:    */   }
/* 314:    */   
/* 315:    */   private void writeLevel(ObjectOutputStream oos)
/* 316:    */     throws IOException
/* 317:    */   {
/* 318:500 */     oos.writeInt(this.level.toInt());
/* 319:    */     
/* 320:502 */     Class clazz = this.level.getClass();
/* 321:503 */     if (clazz == Level.class) {
/* 322:504 */       oos.writeObject(null);
/* 323:    */     } else {
/* 324:509 */       oos.writeObject(clazz.getName());
/* 325:    */     }
/* 326:    */   }
/* 327:    */   
/* 328:    */   public final void setProperty(String propName, String propValue)
/* 329:    */   {
/* 330:525 */     if (this.mdcCopy == null) {
/* 331:526 */       getMDCCopy();
/* 332:    */     }
/* 333:528 */     if (this.mdcCopy == null) {
/* 334:529 */       this.mdcCopy = new Hashtable();
/* 335:    */     }
/* 336:531 */     this.mdcCopy.put(propName, propValue);
/* 337:    */   }
/* 338:    */   
/* 339:    */   public final String getProperty(String key)
/* 340:    */   {
/* 341:545 */     Object value = getMDC(key);
/* 342:546 */     String retval = null;
/* 343:547 */     if (value != null) {
/* 344:548 */       retval = value.toString();
/* 345:    */     }
/* 346:550 */     return retval;
/* 347:    */   }
/* 348:    */   
/* 349:    */   public final boolean locationInformationExists()
/* 350:    */   {
/* 351:560 */     return this.locationInfo != null;
/* 352:    */   }
/* 353:    */   
/* 354:    */   public final long getTimeStamp()
/* 355:    */   {
/* 356:571 */     return this.timeStamp;
/* 357:    */   }
/* 358:    */   
/* 359:    */   public Set getPropertyKeySet()
/* 360:    */   {
/* 361:586 */     return getProperties().keySet();
/* 362:    */   }
/* 363:    */   
/* 364:    */   public Map getProperties()
/* 365:    */   {
/* 366:601 */     getMDCCopy();
/* 367:    */     Map properties;
/* 368:    */     Map properties;
/* 369:603 */     if (this.mdcCopy == null) {
/* 370:604 */       properties = new HashMap();
/* 371:    */     } else {
/* 372:606 */       properties = this.mdcCopy;
/* 373:    */     }
/* 374:608 */     return Collections.unmodifiableMap(properties);
/* 375:    */   }
/* 376:    */   
/* 377:    */   public String getFQNOfLoggerClass()
/* 378:    */   {
/* 379:618 */     return this.fqnOfCategoryClass;
/* 380:    */   }
/* 381:    */   
/* 382:    */   public Object removeProperty(String propName)
/* 383:    */   {
/* 384:631 */     if (this.mdcCopy == null) {
/* 385:632 */       getMDCCopy();
/* 386:    */     }
/* 387:634 */     if (this.mdcCopy == null) {
/* 388:635 */       this.mdcCopy = new Hashtable();
/* 389:    */     }
/* 390:637 */     return this.mdcCopy.remove(propName);
/* 391:    */   }
/* 392:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.spi.LoggingEvent
 * JD-Core Version:    0.7.0.1
 */