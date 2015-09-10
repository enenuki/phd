/*   1:    */ package org.apache.log4j.pattern;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.io.ObjectOutputStream;
/*   6:    */ import java.io.Serializable;
/*   7:    */ import java.lang.reflect.Method;
/*   8:    */ import java.util.Collections;
/*   9:    */ import java.util.HashMap;
/*  10:    */ import java.util.Hashtable;
/*  11:    */ import java.util.Map;
/*  12:    */ import java.util.Set;
/*  13:    */ import org.apache.log4j.Category;
/*  14:    */ import org.apache.log4j.Level;
/*  15:    */ import org.apache.log4j.Logger;
/*  16:    */ import org.apache.log4j.MDC;
/*  17:    */ import org.apache.log4j.NDC;
/*  18:    */ import org.apache.log4j.Priority;
/*  19:    */ import org.apache.log4j.helpers.Loader;
/*  20:    */ import org.apache.log4j.helpers.LogLog;
/*  21:    */ import org.apache.log4j.or.RendererMap;
/*  22:    */ import org.apache.log4j.spi.LocationInfo;
/*  23:    */ import org.apache.log4j.spi.LoggerRepository;
/*  24:    */ import org.apache.log4j.spi.RendererSupport;
/*  25:    */ import org.apache.log4j.spi.ThrowableInformation;
/*  26:    */ 
/*  27:    */ public class LogEvent
/*  28:    */   implements Serializable
/*  29:    */ {
/*  30: 57 */   private static long startTime = ;
/*  31:    */   public final transient String fqnOfCategoryClass;
/*  32:    */   /**
/*  33:    */    * @deprecated
/*  34:    */    */
/*  35:    */   private transient Category logger;
/*  36:    */   /**
/*  37:    */    * @deprecated
/*  38:    */    */
/*  39:    */   public final String categoryName;
/*  40:    */   /**
/*  41:    */    * @deprecated
/*  42:    */    */
/*  43:    */   public transient Priority level;
/*  44:    */   private String ndc;
/*  45:    */   private Hashtable mdcCopy;
/*  46:109 */   private boolean ndcLookupRequired = true;
/*  47:115 */   private boolean mdcCopyLookupRequired = true;
/*  48:    */   private transient Object message;
/*  49:    */   private String renderedMessage;
/*  50:    */   private String threadName;
/*  51:    */   private ThrowableInformation throwableInfo;
/*  52:    */   public final long timeStamp;
/*  53:    */   private LocationInfo locationInfo;
/*  54:    */   static final long serialVersionUID = -868428216207166145L;
/*  55:142 */   static final Integer[] PARAM_ARRAY = new Integer[1];
/*  56:    */   static final String TO_LEVEL = "toLevel";
/*  57:144 */   static final Class[] TO_LEVEL_PARAMS = { Integer.TYPE };
/*  58:145 */   static final Hashtable methodCache = new Hashtable(3);
/*  59:    */   
/*  60:    */   public LogEvent(String fqnOfCategoryClass, Category logger, Priority level, Object message, Throwable throwable)
/*  61:    */   {
/*  62:159 */     this.fqnOfCategoryClass = fqnOfCategoryClass;
/*  63:160 */     this.logger = logger;
/*  64:161 */     this.categoryName = logger.getName();
/*  65:162 */     this.level = level;
/*  66:163 */     this.message = message;
/*  67:164 */     if (throwable != null) {
/*  68:165 */       this.throwableInfo = new ThrowableInformation(throwable);
/*  69:    */     }
/*  70:167 */     this.timeStamp = System.currentTimeMillis();
/*  71:    */   }
/*  72:    */   
/*  73:    */   public LogEvent(String fqnOfCategoryClass, Category logger, long timeStamp, Priority level, Object message, Throwable throwable)
/*  74:    */   {
/*  75:184 */     this.fqnOfCategoryClass = fqnOfCategoryClass;
/*  76:185 */     this.logger = logger;
/*  77:186 */     this.categoryName = logger.getName();
/*  78:187 */     this.level = level;
/*  79:188 */     this.message = message;
/*  80:189 */     if (throwable != null) {
/*  81:190 */       this.throwableInfo = new ThrowableInformation(throwable);
/*  82:    */     }
/*  83:193 */     this.timeStamp = timeStamp;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public LogEvent(String fqnOfCategoryClass, Logger logger, long timeStamp, Level level, Object message, String threadName, ThrowableInformation throwable, String ndc, LocationInfo info, Map properties)
/*  87:    */   {
/*  88:222 */     this.fqnOfCategoryClass = fqnOfCategoryClass;
/*  89:223 */     this.logger = logger;
/*  90:224 */     if (logger != null) {
/*  91:225 */       this.categoryName = logger.getName();
/*  92:    */     } else {
/*  93:227 */       this.categoryName = null;
/*  94:    */     }
/*  95:229 */     this.level = level;
/*  96:230 */     this.message = message;
/*  97:231 */     if (throwable != null) {
/*  98:232 */       this.throwableInfo = throwable;
/*  99:    */     }
/* 100:235 */     this.timeStamp = timeStamp;
/* 101:236 */     this.threadName = threadName;
/* 102:237 */     this.ndcLookupRequired = false;
/* 103:238 */     this.ndc = ndc;
/* 104:239 */     this.locationInfo = info;
/* 105:240 */     this.mdcCopyLookupRequired = false;
/* 106:241 */     if (properties != null) {
/* 107:242 */       this.mdcCopy = new Hashtable(properties);
/* 108:    */     }
/* 109:    */   }
/* 110:    */   
/* 111:    */   public LocationInfo getLocationInformation()
/* 112:    */   {
/* 113:251 */     if (this.locationInfo == null) {
/* 114:252 */       this.locationInfo = new LocationInfo(new Throwable(), this.fqnOfCategoryClass);
/* 115:    */     }
/* 116:254 */     return this.locationInfo;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public Level getLevel()
/* 120:    */   {
/* 121:261 */     return (Level)this.level;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public String getLoggerName()
/* 125:    */   {
/* 126:269 */     return this.categoryName;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public Object getMessage()
/* 130:    */   {
/* 131:283 */     if (this.message != null) {
/* 132:284 */       return this.message;
/* 133:    */     }
/* 134:286 */     return getRenderedMessage();
/* 135:    */   }
/* 136:    */   
/* 137:    */   public String getNDC()
/* 138:    */   {
/* 139:297 */     if (this.ndcLookupRequired)
/* 140:    */     {
/* 141:298 */       this.ndcLookupRequired = false;
/* 142:299 */       this.ndc = NDC.get();
/* 143:    */     }
/* 144:301 */     return this.ndc;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public Object getMDC(String key)
/* 148:    */   {
/* 149:322 */     if (this.mdcCopy != null)
/* 150:    */     {
/* 151:323 */       Object r = this.mdcCopy.get(key);
/* 152:324 */       if (r != null) {
/* 153:325 */         return r;
/* 154:    */       }
/* 155:    */     }
/* 156:328 */     return MDC.get(key);
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void getMDCCopy()
/* 160:    */   {
/* 161:337 */     if (this.mdcCopyLookupRequired)
/* 162:    */     {
/* 163:338 */       this.mdcCopyLookupRequired = false;
/* 164:    */       
/* 165:    */ 
/* 166:341 */       Hashtable t = MDC.getContext();
/* 167:342 */       if (t != null) {
/* 168:343 */         this.mdcCopy = ((Hashtable)t.clone());
/* 169:    */       }
/* 170:    */     }
/* 171:    */   }
/* 172:    */   
/* 173:    */   public String getRenderedMessage()
/* 174:    */   {
/* 175:350 */     if ((this.renderedMessage == null) && (this.message != null)) {
/* 176:351 */       if ((this.message instanceof String))
/* 177:    */       {
/* 178:352 */         this.renderedMessage = ((String)this.message);
/* 179:    */       }
/* 180:    */       else
/* 181:    */       {
/* 182:354 */         LoggerRepository repository = this.logger.getLoggerRepository();
/* 183:356 */         if ((repository instanceof RendererSupport))
/* 184:    */         {
/* 185:357 */           RendererSupport rs = (RendererSupport)repository;
/* 186:358 */           this.renderedMessage = rs.getRendererMap().findAndRender(this.message);
/* 187:    */         }
/* 188:    */         else
/* 189:    */         {
/* 190:360 */           this.renderedMessage = this.message.toString();
/* 191:    */         }
/* 192:    */       }
/* 193:    */     }
/* 194:364 */     return this.renderedMessage;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public static long getStartTime()
/* 198:    */   {
/* 199:371 */     return startTime;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public String getThreadName()
/* 203:    */   {
/* 204:376 */     if (this.threadName == null) {
/* 205:377 */       this.threadName = Thread.currentThread().getName();
/* 206:    */     }
/* 207:378 */     return this.threadName;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public ThrowableInformation getThrowableInformation()
/* 211:    */   {
/* 212:391 */     return this.throwableInfo;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public String[] getThrowableStrRep()
/* 216:    */   {
/* 217:400 */     if (this.throwableInfo == null) {
/* 218:401 */       return null;
/* 219:    */     }
/* 220:403 */     return this.throwableInfo.getThrowableStrRep();
/* 221:    */   }
/* 222:    */   
/* 223:    */   private void readLevel(ObjectInputStream ois)
/* 224:    */     throws IOException, ClassNotFoundException
/* 225:    */   {
/* 226:411 */     int p = ois.readInt();
/* 227:    */     try
/* 228:    */     {
/* 229:413 */       String className = (String)ois.readObject();
/* 230:414 */       if (className == null)
/* 231:    */       {
/* 232:415 */         this.level = Level.toLevel(p);
/* 233:    */       }
/* 234:    */       else
/* 235:    */       {
/* 236:417 */         Method m = (Method)methodCache.get(className);
/* 237:418 */         if (m == null)
/* 238:    */         {
/* 239:419 */           Class clazz = Loader.loadClass(className);
/* 240:    */           
/* 241:    */ 
/* 242:    */ 
/* 243:    */ 
/* 244:    */ 
/* 245:    */ 
/* 246:426 */           m = clazz.getDeclaredMethod("toLevel", TO_LEVEL_PARAMS);
/* 247:427 */           methodCache.put(className, m);
/* 248:    */         }
/* 249:429 */         PARAM_ARRAY[0] = new Integer(p);
/* 250:430 */         this.level = ((Level)m.invoke(null, PARAM_ARRAY));
/* 251:    */       }
/* 252:    */     }
/* 253:    */     catch (Exception e)
/* 254:    */     {
/* 255:433 */       LogLog.warn("Level deserialization failed, reverting to default.", e);
/* 256:434 */       this.level = Level.toLevel(p);
/* 257:    */     }
/* 258:    */   }
/* 259:    */   
/* 260:    */   private void readObject(ObjectInputStream ois)
/* 261:    */     throws IOException, ClassNotFoundException
/* 262:    */   {
/* 263:440 */     ois.defaultReadObject();
/* 264:441 */     readLevel(ois);
/* 265:444 */     if (this.locationInfo == null) {
/* 266:445 */       this.locationInfo = new LocationInfo(null, null);
/* 267:    */     }
/* 268:    */   }
/* 269:    */   
/* 270:    */   private void writeObject(ObjectOutputStream oos)
/* 271:    */     throws IOException
/* 272:    */   {
/* 273:452 */     getThreadName();
/* 274:    */     
/* 275:    */ 
/* 276:455 */     getRenderedMessage();
/* 277:    */     
/* 278:    */ 
/* 279:    */ 
/* 280:459 */     getNDC();
/* 281:    */     
/* 282:    */ 
/* 283:    */ 
/* 284:463 */     getMDCCopy();
/* 285:    */     
/* 286:    */ 
/* 287:466 */     getThrowableStrRep();
/* 288:    */     
/* 289:468 */     oos.defaultWriteObject();
/* 290:    */     
/* 291:    */ 
/* 292:471 */     writeLevel(oos);
/* 293:    */   }
/* 294:    */   
/* 295:    */   private void writeLevel(ObjectOutputStream oos)
/* 296:    */     throws IOException
/* 297:    */   {
/* 298:477 */     oos.writeInt(this.level.toInt());
/* 299:    */     
/* 300:479 */     Class clazz = this.level.getClass();
/* 301:480 */     if (clazz == Level.class) {
/* 302:481 */       oos.writeObject(null);
/* 303:    */     } else {
/* 304:486 */       oos.writeObject(clazz.getName());
/* 305:    */     }
/* 306:    */   }
/* 307:    */   
/* 308:    */   public final void setProperty(String propName, String propValue)
/* 309:    */   {
/* 310:502 */     if (this.mdcCopy == null) {
/* 311:503 */       getMDCCopy();
/* 312:    */     }
/* 313:505 */     if (this.mdcCopy == null) {
/* 314:506 */       this.mdcCopy = new Hashtable();
/* 315:    */     }
/* 316:508 */     this.mdcCopy.put(propName, propValue);
/* 317:    */   }
/* 318:    */   
/* 319:    */   public final String getProperty(String key)
/* 320:    */   {
/* 321:522 */     Object value = getMDC(key);
/* 322:523 */     String retval = null;
/* 323:524 */     if (value != null) {
/* 324:525 */       retval = value.toString();
/* 325:    */     }
/* 326:527 */     return retval;
/* 327:    */   }
/* 328:    */   
/* 329:    */   public final boolean locationInformationExists()
/* 330:    */   {
/* 331:537 */     return this.locationInfo != null;
/* 332:    */   }
/* 333:    */   
/* 334:    */   public final long getTimeStamp()
/* 335:    */   {
/* 336:548 */     return this.timeStamp;
/* 337:    */   }
/* 338:    */   
/* 339:    */   public Set getPropertyKeySet()
/* 340:    */   {
/* 341:563 */     return getProperties().keySet();
/* 342:    */   }
/* 343:    */   
/* 344:    */   public Map getProperties()
/* 345:    */   {
/* 346:578 */     getMDCCopy();
/* 347:    */     Map properties;
/* 348:    */     Map properties;
/* 349:580 */     if (this.mdcCopy == null) {
/* 350:581 */       properties = new HashMap();
/* 351:    */     } else {
/* 352:583 */       properties = this.mdcCopy;
/* 353:    */     }
/* 354:585 */     return Collections.unmodifiableMap(properties);
/* 355:    */   }
/* 356:    */   
/* 357:    */   public String getFQNOfLoggerClass()
/* 358:    */   {
/* 359:595 */     return this.fqnOfCategoryClass;
/* 360:    */   }
/* 361:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.pattern.LogEvent
 * JD-Core Version:    0.7.0.1
 */