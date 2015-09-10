/*   1:    */ package org.apache.log4j;
/*   2:    */ 
/*   3:    */ import java.text.MessageFormat;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Collection;
/*   6:    */ import java.util.Enumeration;
/*   7:    */ import java.util.HashMap;
/*   8:    */ import java.util.Iterator;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;
/*  11:    */ import org.apache.log4j.helpers.AppenderAttachableImpl;
/*  12:    */ import org.apache.log4j.helpers.LogLog;
/*  13:    */ import org.apache.log4j.spi.AppenderAttachable;
/*  14:    */ import org.apache.log4j.spi.LoggingEvent;
/*  15:    */ 
/*  16:    */ public class AsyncAppender
/*  17:    */   extends AppenderSkeleton
/*  18:    */   implements AppenderAttachable
/*  19:    */ {
/*  20:    */   public static final int DEFAULT_BUFFER_SIZE = 128;
/*  21: 67 */   private final List buffer = new ArrayList();
/*  22: 72 */   private final Map discardMap = new HashMap();
/*  23: 77 */   private int bufferSize = 128;
/*  24:    */   AppenderAttachableImpl aai;
/*  25:    */   private final AppenderAttachableImpl appenders;
/*  26:    */   private final Thread dispatcher;
/*  27: 95 */   private boolean locationInfo = false;
/*  28:100 */   private boolean blocking = true;
/*  29:    */   
/*  30:    */   public AsyncAppender()
/*  31:    */   {
/*  32:106 */     this.appenders = new AppenderAttachableImpl();
/*  33:    */     
/*  34:    */ 
/*  35:    */ 
/*  36:110 */     this.aai = this.appenders;
/*  37:    */     
/*  38:112 */     this.dispatcher = new Thread(new Dispatcher(this, this.buffer, this.discardMap, this.appenders));
/*  39:    */     
/*  40:    */ 
/*  41:    */ 
/*  42:    */ 
/*  43:117 */     this.dispatcher.setDaemon(true);
/*  44:    */     
/*  45:    */ 
/*  46:    */ 
/*  47:121 */     this.dispatcher.setName("AsyncAppender-Dispatcher-" + this.dispatcher.getName());
/*  48:122 */     this.dispatcher.start();
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void addAppender(Appender newAppender)
/*  52:    */   {
/*  53:131 */     synchronized (this.appenders)
/*  54:    */     {
/*  55:132 */       this.appenders.addAppender(newAppender);
/*  56:    */     }
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void append(LoggingEvent event)
/*  60:    */   {
/*  61:144 */     if ((this.dispatcher == null) || (!this.dispatcher.isAlive()) || (this.bufferSize <= 0))
/*  62:    */     {
/*  63:145 */       synchronized (this.appenders)
/*  64:    */       {
/*  65:146 */         this.appenders.appendLoopOnAppenders(event);
/*  66:    */       }
/*  67:149 */       return;
/*  68:    */     }
/*  69:154 */     event.getNDC();
/*  70:155 */     event.getThreadName();
/*  71:    */     
/*  72:157 */     event.getMDCCopy();
/*  73:158 */     if (this.locationInfo) {
/*  74:159 */       event.getLocationInformation();
/*  75:    */     }
/*  76:161 */     event.getRenderedMessage();
/*  77:162 */     event.getThrowableStrRep();
/*  78:164 */     synchronized (this.buffer)
/*  79:    */     {
/*  80:    */       for (;;)
/*  81:    */       {
/*  82:166 */         int previousSize = this.buffer.size();
/*  83:168 */         if (previousSize < this.bufferSize)
/*  84:    */         {
/*  85:169 */           this.buffer.add(event);
/*  86:176 */           if (previousSize != 0) {
/*  87:    */             break;
/*  88:    */           }
/*  89:177 */           this.buffer.notifyAll(); break;
/*  90:    */         }
/*  91:190 */         boolean discard = true;
/*  92:191 */         if ((this.blocking) && (!Thread.interrupted()) && (Thread.currentThread() != this.dispatcher)) {
/*  93:    */           try
/*  94:    */           {
/*  95:195 */             this.buffer.wait();
/*  96:196 */             discard = false;
/*  97:    */           }
/*  98:    */           catch (InterruptedException e)
/*  99:    */           {
/* 100:202 */             Thread.currentThread().interrupt();
/* 101:    */           }
/* 102:    */         }
/* 103:210 */         if (discard)
/* 104:    */         {
/* 105:211 */           String loggerName = event.getLoggerName();
/* 106:212 */           DiscardSummary summary = (DiscardSummary)this.discardMap.get(loggerName);
/* 107:214 */           if (summary == null)
/* 108:    */           {
/* 109:215 */             summary = new DiscardSummary(event);
/* 110:216 */             this.discardMap.put(loggerName, summary); break;
/* 111:    */           }
/* 112:218 */           summary.add(event);
/* 113:    */           
/* 114:    */ 
/* 115:221 */           break;
/* 116:    */         }
/* 117:    */       }
/* 118:    */     }
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void close()
/* 122:    */   {
/* 123:236 */     synchronized (this.buffer)
/* 124:    */     {
/* 125:237 */       this.closed = true;
/* 126:238 */       this.buffer.notifyAll();
/* 127:    */     }
/* 128:    */     try
/* 129:    */     {
/* 130:242 */       this.dispatcher.join();
/* 131:    */     }
/* 132:    */     catch (InterruptedException e)
/* 133:    */     {
/* 134:244 */       Thread.currentThread().interrupt();
/* 135:245 */       LogLog.error("Got an InterruptedException while waiting for the dispatcher to finish.", e);
/* 136:    */     }
/* 137:253 */     synchronized (this.appenders)
/* 138:    */     {
/* 139:254 */       Enumeration iter = this.appenders.getAllAppenders();
/* 140:256 */       if (iter != null) {
/* 141:257 */         while (iter.hasMoreElements())
/* 142:    */         {
/* 143:258 */           Object next = iter.nextElement();
/* 144:260 */           if ((next instanceof Appender)) {
/* 145:261 */             ((Appender)next).close();
/* 146:    */           }
/* 147:    */         }
/* 148:    */       }
/* 149:    */     }
/* 150:    */   }
/* 151:    */   
/* 152:    */   public Enumeration getAllAppenders()
/* 153:    */   {
/* 154:273 */     synchronized (this.appenders)
/* 155:    */     {
/* 156:274 */       return this.appenders.getAllAppenders();
/* 157:    */     }
/* 158:    */   }
/* 159:    */   
/* 160:    */   public Appender getAppender(String name)
/* 161:    */   {
/* 162:285 */     synchronized (this.appenders)
/* 163:    */     {
/* 164:286 */       return this.appenders.getAppender(name);
/* 165:    */     }
/* 166:    */   }
/* 167:    */   
/* 168:    */   public boolean getLocationInfo()
/* 169:    */   {
/* 170:297 */     return this.locationInfo;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public boolean isAttached(Appender appender)
/* 174:    */   {
/* 175:306 */     synchronized (this.appenders)
/* 176:    */     {
/* 177:307 */       return this.appenders.isAttached(appender);
/* 178:    */     }
/* 179:    */   }
/* 180:    */   
/* 181:    */   public boolean requiresLayout()
/* 182:    */   {
/* 183:315 */     return false;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public void removeAllAppenders()
/* 187:    */   {
/* 188:322 */     synchronized (this.appenders)
/* 189:    */     {
/* 190:323 */       this.appenders.removeAllAppenders();
/* 191:    */     }
/* 192:    */   }
/* 193:    */   
/* 194:    */   public void removeAppender(Appender appender)
/* 195:    */   {
/* 196:332 */     synchronized (this.appenders)
/* 197:    */     {
/* 198:333 */       this.appenders.removeAppender(appender);
/* 199:    */     }
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void removeAppender(String name)
/* 203:    */   {
/* 204:342 */     synchronized (this.appenders)
/* 205:    */     {
/* 206:343 */       this.appenders.removeAppender(name);
/* 207:    */     }
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void setLocationInfo(boolean flag)
/* 211:    */   {
/* 212:361 */     this.locationInfo = flag;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public void setBufferSize(int size)
/* 216:    */   {
/* 217:377 */     if (size < 0) {
/* 218:378 */       throw new NegativeArraySizeException("size");
/* 219:    */     }
/* 220:381 */     synchronized (this.buffer)
/* 221:    */     {
/* 222:385 */       this.bufferSize = (size < 1 ? 1 : size);
/* 223:386 */       this.buffer.notifyAll();
/* 224:    */     }
/* 225:    */   }
/* 226:    */   
/* 227:    */   public int getBufferSize()
/* 228:    */   {
/* 229:395 */     return this.bufferSize;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public void setBlocking(boolean value)
/* 233:    */   {
/* 234:406 */     synchronized (this.buffer)
/* 235:    */     {
/* 236:407 */       this.blocking = value;
/* 237:408 */       this.buffer.notifyAll();
/* 238:    */     }
/* 239:    */   }
/* 240:    */   
/* 241:    */   public boolean getBlocking()
/* 242:    */   {
/* 243:421 */     return this.blocking;
/* 244:    */   }
/* 245:    */   
/* 246:    */   private static final class DiscardSummary
/* 247:    */   {
/* 248:    */     private LoggingEvent maxEvent;
/* 249:    */     private int count;
/* 250:    */     
/* 251:    */     public DiscardSummary(LoggingEvent event)
/* 252:    */     {
/* 253:444 */       this.maxEvent = event;
/* 254:445 */       this.count = 1;
/* 255:    */     }
/* 256:    */     
/* 257:    */     public void add(LoggingEvent event)
/* 258:    */     {
/* 259:454 */       if (event.getLevel().toInt() > this.maxEvent.getLevel().toInt()) {
/* 260:455 */         this.maxEvent = event;
/* 261:    */       }
/* 262:458 */       this.count += 1;
/* 263:    */     }
/* 264:    */     
/* 265:    */     public LoggingEvent createEvent()
/* 266:    */     {
/* 267:467 */       String msg = MessageFormat.format("Discarded {0} messages due to full event buffer including: {1}", new Object[] { new Integer(this.count), this.maxEvent.getMessage() });
/* 268:    */       
/* 269:    */ 
/* 270:    */ 
/* 271:    */ 
/* 272:472 */       return new LoggingEvent("org.apache.log4j.AsyncAppender.DONT_REPORT_LOCATION", Logger.getLogger(this.maxEvent.getLoggerName()), this.maxEvent.getLevel(), msg, null);
/* 273:    */     }
/* 274:    */   }
/* 275:    */   
/* 276:    */   private static class Dispatcher
/* 277:    */     implements Runnable
/* 278:    */   {
/* 279:    */     private final AsyncAppender parent;
/* 280:    */     private final List buffer;
/* 281:    */     private final Map discardMap;
/* 282:    */     private final AppenderAttachableImpl appenders;
/* 283:    */     
/* 284:    */     public Dispatcher(AsyncAppender parent, List buffer, Map discardMap, AppenderAttachableImpl appenders)
/* 285:    */     {
/* 286:517 */       this.parent = parent;
/* 287:518 */       this.buffer = buffer;
/* 288:519 */       this.appenders = appenders;
/* 289:520 */       this.discardMap = discardMap;
/* 290:    */     }
/* 291:    */     
/* 292:    */     public void run()
/* 293:    */     {
/* 294:527 */       boolean isActive = true;
/* 295:    */       try
/* 296:    */       {
/* 297:536 */         while (isActive)
/* 298:    */         {
/* 299:537 */           LoggingEvent[] events = null;
/* 300:543 */           synchronized (this.buffer)
/* 301:    */           {
/* 302:544 */             int bufferSize = this.buffer.size();
/* 303:545 */             isActive = !this.parent.closed;
/* 304:547 */             while ((bufferSize == 0) && (isActive))
/* 305:    */             {
/* 306:548 */               this.buffer.wait();
/* 307:549 */               bufferSize = this.buffer.size();
/* 308:550 */               isActive = !this.parent.closed;
/* 309:    */             }
/* 310:553 */             if (bufferSize > 0)
/* 311:    */             {
/* 312:554 */               events = new LoggingEvent[bufferSize + this.discardMap.size()];
/* 313:555 */               this.buffer.toArray(events);
/* 314:    */               
/* 315:    */ 
/* 316:    */ 
/* 317:    */ 
/* 318:560 */               int index = bufferSize;
/* 319:    */               
/* 320:    */ 
/* 321:563 */               Iterator iter = this.discardMap.values().iterator();
/* 322:564 */               while (iter.hasNext()) {
/* 323:565 */                 events[(index++)] = ((AsyncAppender.DiscardSummary)iter.next()).createEvent();
/* 324:    */               }
/* 325:571 */               this.buffer.clear();
/* 326:572 */               this.discardMap.clear();
/* 327:    */               
/* 328:    */ 
/* 329:    */ 
/* 330:576 */               this.buffer.notifyAll();
/* 331:    */             }
/* 332:    */           }
/* 333:583 */           if (events != null) {
/* 334:584 */             for (int i = 0; i < events.length; i++) {
/* 335:585 */               synchronized (this.appenders)
/* 336:    */               {
/* 337:586 */                 this.appenders.appendLoopOnAppenders(events[i]);
/* 338:    */               }
/* 339:    */             }
/* 340:    */           }
/* 341:    */         }
/* 342:    */       }
/* 343:    */       catch (InterruptedException ex)
/* 344:    */       {
/* 345:592 */         Thread.currentThread().interrupt();
/* 346:    */       }
/* 347:    */     }
/* 348:    */   }
/* 349:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.AsyncAppender
 * JD-Core Version:    0.7.0.1
 */