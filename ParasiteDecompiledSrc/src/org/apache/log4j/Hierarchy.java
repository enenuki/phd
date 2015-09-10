/*   1:    */ package org.apache.log4j;
/*   2:    */ 
/*   3:    */ import java.util.Enumeration;
/*   4:    */ import java.util.Hashtable;
/*   5:    */ import java.util.Vector;
/*   6:    */ import org.apache.log4j.helpers.LogLog;
/*   7:    */ import org.apache.log4j.or.ObjectRenderer;
/*   8:    */ import org.apache.log4j.or.RendererMap;
/*   9:    */ import org.apache.log4j.spi.HierarchyEventListener;
/*  10:    */ import org.apache.log4j.spi.LoggerFactory;
/*  11:    */ import org.apache.log4j.spi.LoggerRepository;
/*  12:    */ import org.apache.log4j.spi.RendererSupport;
/*  13:    */ import org.apache.log4j.spi.ThrowableRenderer;
/*  14:    */ import org.apache.log4j.spi.ThrowableRendererSupport;
/*  15:    */ 
/*  16:    */ public class Hierarchy
/*  17:    */   implements LoggerRepository, RendererSupport, ThrowableRendererSupport
/*  18:    */ {
/*  19:    */   private LoggerFactory defaultFactory;
/*  20:    */   private Vector listeners;
/*  21:    */   Hashtable ht;
/*  22:    */   Logger root;
/*  23:    */   RendererMap rendererMap;
/*  24:    */   int thresholdInt;
/*  25:    */   Level threshold;
/*  26: 78 */   boolean emittedNoAppenderWarning = false;
/*  27: 79 */   boolean emittedNoResourceBundleWarning = false;
/*  28: 81 */   private ThrowableRenderer throwableRenderer = null;
/*  29:    */   
/*  30:    */   public Hierarchy(Logger root)
/*  31:    */   {
/*  32: 91 */     this.ht = new Hashtable();
/*  33: 92 */     this.listeners = new Vector(1);
/*  34: 93 */     this.root = root;
/*  35:    */     
/*  36: 95 */     setThreshold(Level.ALL);
/*  37: 96 */     this.root.setHierarchy(this);
/*  38: 97 */     this.rendererMap = new RendererMap();
/*  39: 98 */     this.defaultFactory = new DefaultCategoryFactory();
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void addRenderer(Class classToRender, ObjectRenderer or)
/*  43:    */   {
/*  44:106 */     this.rendererMap.put(classToRender, or);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void addHierarchyEventListener(HierarchyEventListener listener)
/*  48:    */   {
/*  49:111 */     if (this.listeners.contains(listener)) {
/*  50:112 */       LogLog.warn("Ignoring attempt to add an existent listener.");
/*  51:    */     } else {
/*  52:114 */       this.listeners.addElement(listener);
/*  53:    */     }
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void clear()
/*  57:    */   {
/*  58:130 */     this.ht.clear();
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void emitNoAppenderWarning(Category cat)
/*  62:    */   {
/*  63:136 */     if (!this.emittedNoAppenderWarning)
/*  64:    */     {
/*  65:137 */       LogLog.warn("No appenders could be found for logger (" + cat.getName() + ").");
/*  66:    */       
/*  67:139 */       LogLog.warn("Please initialize the log4j system properly.");
/*  68:140 */       LogLog.warn("See http://logging.apache.org/log4j/1.2/faq.html#noconfig for more info.");
/*  69:141 */       this.emittedNoAppenderWarning = true;
/*  70:    */     }
/*  71:    */   }
/*  72:    */   
/*  73:    */   public Logger exists(String name)
/*  74:    */   {
/*  75:154 */     Object o = this.ht.get(new CategoryKey(name));
/*  76:155 */     if ((o instanceof Logger)) {
/*  77:156 */       return (Logger)o;
/*  78:    */     }
/*  79:158 */     return null;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void setThreshold(String levelStr)
/*  83:    */   {
/*  84:167 */     Level l = Level.toLevel(levelStr, null);
/*  85:168 */     if (l != null) {
/*  86:169 */       setThreshold(l);
/*  87:    */     } else {
/*  88:171 */       LogLog.warn("Could not convert [" + levelStr + "] to Level.");
/*  89:    */     }
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setThreshold(Level l)
/*  93:    */   {
/*  94:184 */     if (l != null)
/*  95:    */     {
/*  96:185 */       this.thresholdInt = l.level;
/*  97:186 */       this.threshold = l;
/*  98:    */     }
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void fireAddAppenderEvent(Category logger, Appender appender)
/* 102:    */   {
/* 103:192 */     if (this.listeners != null)
/* 104:    */     {
/* 105:193 */       int size = this.listeners.size();
/* 106:195 */       for (int i = 0; i < size; i++)
/* 107:    */       {
/* 108:196 */         HierarchyEventListener listener = (HierarchyEventListener)this.listeners.elementAt(i);
/* 109:197 */         listener.addAppenderEvent(logger, appender);
/* 110:    */       }
/* 111:    */     }
/* 112:    */   }
/* 113:    */   
/* 114:    */   void fireRemoveAppenderEvent(Category logger, Appender appender)
/* 115:    */   {
/* 116:203 */     if (this.listeners != null)
/* 117:    */     {
/* 118:204 */       int size = this.listeners.size();
/* 119:206 */       for (int i = 0; i < size; i++)
/* 120:    */       {
/* 121:207 */         HierarchyEventListener listener = (HierarchyEventListener)this.listeners.elementAt(i);
/* 122:208 */         listener.removeAppenderEvent(logger, appender);
/* 123:    */       }
/* 124:    */     }
/* 125:    */   }
/* 126:    */   
/* 127:    */   public Level getThreshold()
/* 128:    */   {
/* 129:220 */     return this.threshold;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public Logger getLogger(String name)
/* 133:    */   {
/* 134:247 */     return getLogger(name, this.defaultFactory);
/* 135:    */   }
/* 136:    */   
/* 137:    */   public Logger getLogger(String name, LoggerFactory factory)
/* 138:    */   {
/* 139:266 */     CategoryKey key = new CategoryKey(name);
/* 140:272 */     synchronized (this.ht)
/* 141:    */     {
/* 142:273 */       Object o = this.ht.get(key);
/* 143:274 */       if (o == null)
/* 144:    */       {
/* 145:275 */         Logger logger = factory.makeNewLoggerInstance(name);
/* 146:276 */         logger.setHierarchy(this);
/* 147:277 */         this.ht.put(key, logger);
/* 148:278 */         updateParents(logger);
/* 149:279 */         return logger;
/* 150:    */       }
/* 151:280 */       if ((o instanceof Logger)) {
/* 152:281 */         return (Logger)o;
/* 153:    */       }
/* 154:282 */       if ((o instanceof ProvisionNode))
/* 155:    */       {
/* 156:284 */         Logger logger = factory.makeNewLoggerInstance(name);
/* 157:285 */         logger.setHierarchy(this);
/* 158:286 */         this.ht.put(key, logger);
/* 159:287 */         updateChildren((ProvisionNode)o, logger);
/* 160:288 */         updateParents(logger);
/* 161:289 */         return logger;
/* 162:    */       }
/* 163:293 */       return null;
/* 164:    */     }
/* 165:    */   }
/* 166:    */   
/* 167:    */   public Enumeration getCurrentLoggers()
/* 168:    */   {
/* 169:309 */     Vector v = new Vector(this.ht.size());
/* 170:    */     
/* 171:311 */     Enumeration elems = this.ht.elements();
/* 172:312 */     while (elems.hasMoreElements())
/* 173:    */     {
/* 174:313 */       Object o = elems.nextElement();
/* 175:314 */       if ((o instanceof Logger)) {
/* 176:315 */         v.addElement(o);
/* 177:    */       }
/* 178:    */     }
/* 179:318 */     return v.elements();
/* 180:    */   }
/* 181:    */   
/* 182:    */   /**
/* 183:    */    * @deprecated
/* 184:    */    */
/* 185:    */   public Enumeration getCurrentCategories()
/* 186:    */   {
/* 187:326 */     return getCurrentLoggers();
/* 188:    */   }
/* 189:    */   
/* 190:    */   public RendererMap getRendererMap()
/* 191:    */   {
/* 192:335 */     return this.rendererMap;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public Logger getRootLogger()
/* 196:    */   {
/* 197:346 */     return this.root;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public boolean isDisabled(int level)
/* 201:    */   {
/* 202:356 */     return this.thresholdInt > level;
/* 203:    */   }
/* 204:    */   
/* 205:    */   /**
/* 206:    */    * @deprecated
/* 207:    */    */
/* 208:    */   public void overrideAsNeeded(String override)
/* 209:    */   {
/* 210:364 */     LogLog.warn("The Hiearchy.overrideAsNeeded method has been deprecated.");
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void resetConfiguration()
/* 214:    */   {
/* 215:384 */     getRootLogger().setLevel(Level.DEBUG);
/* 216:385 */     this.root.setResourceBundle(null);
/* 217:386 */     setThreshold(Level.ALL);
/* 218:390 */     synchronized (this.ht)
/* 219:    */     {
/* 220:391 */       shutdown();
/* 221:    */       
/* 222:393 */       Enumeration cats = getCurrentLoggers();
/* 223:394 */       while (cats.hasMoreElements())
/* 224:    */       {
/* 225:395 */         Logger c = (Logger)cats.nextElement();
/* 226:396 */         c.setLevel(null);
/* 227:397 */         c.setAdditivity(true);
/* 228:398 */         c.setResourceBundle(null);
/* 229:    */       }
/* 230:    */     }
/* 231:401 */     this.rendererMap.clear();
/* 232:402 */     this.throwableRenderer = null;
/* 233:    */   }
/* 234:    */   
/* 235:    */   /**
/* 236:    */    * @deprecated
/* 237:    */    */
/* 238:    */   public void setDisableOverride(String override)
/* 239:    */   {
/* 240:412 */     LogLog.warn("The Hiearchy.setDisableOverride method has been deprecated.");
/* 241:    */   }
/* 242:    */   
/* 243:    */   public void setRenderer(Class renderedClass, ObjectRenderer renderer)
/* 244:    */   {
/* 245:422 */     this.rendererMap.put(renderedClass, renderer);
/* 246:    */   }
/* 247:    */   
/* 248:    */   public void setThrowableRenderer(ThrowableRenderer renderer)
/* 249:    */   {
/* 250:429 */     this.throwableRenderer = renderer;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public ThrowableRenderer getThrowableRenderer()
/* 254:    */   {
/* 255:436 */     return this.throwableRenderer;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public void shutdown()
/* 259:    */   {
/* 260:458 */     Logger root = getRootLogger();
/* 261:    */     
/* 262:    */ 
/* 263:461 */     root.closeNestedAppenders();
/* 264:463 */     synchronized (this.ht)
/* 265:    */     {
/* 266:464 */       Enumeration cats = getCurrentLoggers();
/* 267:465 */       while (cats.hasMoreElements())
/* 268:    */       {
/* 269:466 */         Logger c = (Logger)cats.nextElement();
/* 270:467 */         c.closeNestedAppenders();
/* 271:    */       }
/* 272:471 */       root.removeAllAppenders();
/* 273:472 */       cats = getCurrentLoggers();
/* 274:473 */       while (cats.hasMoreElements())
/* 275:    */       {
/* 276:474 */         Logger c = (Logger)cats.nextElement();
/* 277:475 */         c.removeAllAppenders();
/* 278:    */       }
/* 279:    */     }
/* 280:    */   }
/* 281:    */   
/* 282:    */   private final void updateParents(Logger cat)
/* 283:    */   {
/* 284:504 */     String name = cat.name;
/* 285:505 */     int length = name.length();
/* 286:506 */     boolean parentFound = false;
/* 287:511 */     for (int i = name.lastIndexOf('.', length - 1); i >= 0; i = name.lastIndexOf('.', i - 1))
/* 288:    */     {
/* 289:513 */       String substr = name.substring(0, i);
/* 290:    */       
/* 291:    */ 
/* 292:516 */       CategoryKey key = new CategoryKey(substr);
/* 293:517 */       Object o = this.ht.get(key);
/* 294:519 */       if (o == null)
/* 295:    */       {
/* 296:521 */         ProvisionNode pn = new ProvisionNode(cat);
/* 297:522 */         this.ht.put(key, pn);
/* 298:    */       }
/* 299:    */       else
/* 300:    */       {
/* 301:523 */         if ((o instanceof Category))
/* 302:    */         {
/* 303:524 */           parentFound = true;
/* 304:525 */           cat.parent = ((Category)o);
/* 305:    */           
/* 306:527 */           break;
/* 307:    */         }
/* 308:528 */         if ((o instanceof ProvisionNode))
/* 309:    */         {
/* 310:529 */           ((ProvisionNode)o).addElement(cat);
/* 311:    */         }
/* 312:    */         else
/* 313:    */         {
/* 314:531 */           Exception e = new IllegalStateException("unexpected object type " + o.getClass() + " in ht.");
/* 315:    */           
/* 316:533 */           e.printStackTrace();
/* 317:    */         }
/* 318:    */       }
/* 319:    */     }
/* 320:537 */     if (!parentFound) {
/* 321:538 */       cat.parent = this.root;
/* 322:    */     }
/* 323:    */   }
/* 324:    */   
/* 325:    */   private final void updateChildren(ProvisionNode pn, Logger logger)
/* 326:    */   {
/* 327:560 */     int last = pn.size();
/* 328:562 */     for (int i = 0; i < last; i++)
/* 329:    */     {
/* 330:563 */       Logger l = (Logger)pn.elementAt(i);
/* 331:568 */       if (!l.parent.name.startsWith(logger.name))
/* 332:    */       {
/* 333:569 */         logger.parent = l.parent;
/* 334:570 */         l.parent = logger;
/* 335:    */       }
/* 336:    */     }
/* 337:    */   }
/* 338:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.Hierarchy
 * JD-Core Version:    0.7.0.1
 */