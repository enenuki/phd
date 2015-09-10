/*   1:    */ package org.hibernate.event.service.internal;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Array;
/*   4:    */ import java.util.Collections;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.Map;
/*   7:    */ import org.hibernate.HibernateException;
/*   8:    */ import org.hibernate.event.internal.DefaultAutoFlushEventListener;
/*   9:    */ import org.hibernate.event.internal.DefaultDeleteEventListener;
/*  10:    */ import org.hibernate.event.internal.DefaultDirtyCheckEventListener;
/*  11:    */ import org.hibernate.event.internal.DefaultEvictEventListener;
/*  12:    */ import org.hibernate.event.internal.DefaultFlushEntityEventListener;
/*  13:    */ import org.hibernate.event.internal.DefaultFlushEventListener;
/*  14:    */ import org.hibernate.event.internal.DefaultInitializeCollectionEventListener;
/*  15:    */ import org.hibernate.event.internal.DefaultLoadEventListener;
/*  16:    */ import org.hibernate.event.internal.DefaultLockEventListener;
/*  17:    */ import org.hibernate.event.internal.DefaultMergeEventListener;
/*  18:    */ import org.hibernate.event.internal.DefaultPersistEventListener;
/*  19:    */ import org.hibernate.event.internal.DefaultPersistOnFlushEventListener;
/*  20:    */ import org.hibernate.event.internal.DefaultPostLoadEventListener;
/*  21:    */ import org.hibernate.event.internal.DefaultPreLoadEventListener;
/*  22:    */ import org.hibernate.event.internal.DefaultRefreshEventListener;
/*  23:    */ import org.hibernate.event.internal.DefaultReplicateEventListener;
/*  24:    */ import org.hibernate.event.internal.DefaultSaveEventListener;
/*  25:    */ import org.hibernate.event.internal.DefaultSaveOrUpdateEventListener;
/*  26:    */ import org.hibernate.event.internal.DefaultUpdateEventListener;
/*  27:    */ import org.hibernate.event.service.spi.DuplicationStrategy;
/*  28:    */ import org.hibernate.event.service.spi.EventListenerRegistrationException;
/*  29:    */ import org.hibernate.event.service.spi.EventListenerRegistry;
/*  30:    */ import org.hibernate.event.spi.EventType;
/*  31:    */ 
/*  32:    */ public class EventListenerRegistryImpl
/*  33:    */   implements EventListenerRegistry
/*  34:    */ {
/*  35: 95 */   private Map<Class, Object> listenerClassToInstanceMap = new HashMap();
/*  36: 97 */   private Map<EventType, EventListenerGroupImpl> registeredEventListenersMap = prepareListenerMap();
/*  37:    */   
/*  38:    */   public <T> EventListenerGroupImpl<T> getEventListenerGroup(EventType<T> eventType)
/*  39:    */   {
/*  40:101 */     EventListenerGroupImpl<T> listeners = (EventListenerGroupImpl)this.registeredEventListenersMap.get(eventType);
/*  41:102 */     if (listeners == null) {
/*  42:103 */       throw new HibernateException("Unable to find listeners for type [" + eventType.eventName() + "]");
/*  43:    */     }
/*  44:105 */     return listeners;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void addDuplicationStrategy(DuplicationStrategy strategy)
/*  48:    */   {
/*  49:110 */     for (EventListenerGroupImpl group : this.registeredEventListenersMap.values()) {
/*  50:111 */       group.addDuplicationStrategy(strategy);
/*  51:    */     }
/*  52:    */   }
/*  53:    */   
/*  54:    */   public <T> void setListeners(EventType<T> type, Class<T>... listenerClasses)
/*  55:    */   {
/*  56:117 */     setListeners(type, resolveListenerInstances(type, listenerClasses));
/*  57:    */   }
/*  58:    */   
/*  59:    */   private <T> T[] resolveListenerInstances(EventType<T> type, Class<T>... listenerClasses)
/*  60:    */   {
/*  61:122 */     T[] listeners = (Object[])Array.newInstance(type.baseListenerInterface(), listenerClasses.length);
/*  62:123 */     for (int i = 0; i < listenerClasses.length; i++) {
/*  63:124 */       listeners[i] = resolveListenerInstance(listenerClasses[i]);
/*  64:    */     }
/*  65:126 */     return listeners;
/*  66:    */   }
/*  67:    */   
/*  68:    */   private <T> T resolveListenerInstance(Class<T> listenerClass)
/*  69:    */   {
/*  70:131 */     T listenerInstance = this.listenerClassToInstanceMap.get(listenerClass);
/*  71:132 */     if (listenerInstance == null)
/*  72:    */     {
/*  73:133 */       listenerInstance = instantiateListener(listenerClass);
/*  74:134 */       this.listenerClassToInstanceMap.put(listenerClass, listenerInstance);
/*  75:    */     }
/*  76:136 */     return listenerInstance;
/*  77:    */   }
/*  78:    */   
/*  79:    */   private <T> T instantiateListener(Class<T> listenerClass)
/*  80:    */   {
/*  81:    */     try
/*  82:    */     {
/*  83:141 */       return listenerClass.newInstance();
/*  84:    */     }
/*  85:    */     catch (Exception e)
/*  86:    */     {
/*  87:144 */       throw new EventListenerRegistrationException("Unable to instantiate specified event listener class: " + listenerClass.getName(), e);
/*  88:    */     }
/*  89:    */   }
/*  90:    */   
/*  91:    */   public <T> void setListeners(EventType<T> type, T... listeners)
/*  92:    */   {
/*  93:153 */     EventListenerGroupImpl<T> registeredListeners = getEventListenerGroup(type);
/*  94:154 */     registeredListeners.clear();
/*  95:155 */     if (listeners != null)
/*  96:    */     {
/*  97:156 */       int i = 0;
/*  98:156 */       for (int max = listeners.length; i < max; i++) {
/*  99:157 */         registeredListeners.appendListener(listeners[i]);
/* 100:    */       }
/* 101:    */     }
/* 102:    */   }
/* 103:    */   
/* 104:    */   public <T> void appendListeners(EventType<T> type, Class<T>... listenerClasses)
/* 105:    */   {
/* 106:164 */     appendListeners(type, resolveListenerInstances(type, listenerClasses));
/* 107:    */   }
/* 108:    */   
/* 109:    */   public <T> void appendListeners(EventType<T> type, T... listeners)
/* 110:    */   {
/* 111:169 */     getEventListenerGroup(type).appendListeners(listeners);
/* 112:    */   }
/* 113:    */   
/* 114:    */   public <T> void prependListeners(EventType<T> type, Class<T>... listenerClasses)
/* 115:    */   {
/* 116:174 */     prependListeners(type, resolveListenerInstances(type, listenerClasses));
/* 117:    */   }
/* 118:    */   
/* 119:    */   public <T> void prependListeners(EventType<T> type, T... listeners)
/* 120:    */   {
/* 121:179 */     getEventListenerGroup(type).prependListeners(listeners);
/* 122:    */   }
/* 123:    */   
/* 124:    */   private static Map<EventType, EventListenerGroupImpl> prepareListenerMap()
/* 125:    */   {
/* 126:183 */     Map<EventType, EventListenerGroupImpl> workMap = new HashMap();
/* 127:    */     
/* 128:    */ 
/* 129:186 */     prepareListeners(EventType.AUTO_FLUSH, new DefaultAutoFlushEventListener(), workMap);
/* 130:    */     
/* 131:    */ 
/* 132:    */ 
/* 133:    */ 
/* 134:    */ 
/* 135:    */ 
/* 136:193 */     prepareListeners(EventType.PERSIST, new DefaultPersistEventListener(), workMap);
/* 137:    */     
/* 138:    */ 
/* 139:    */ 
/* 140:    */ 
/* 141:    */ 
/* 142:    */ 
/* 143:200 */     prepareListeners(EventType.PERSIST_ONFLUSH, new DefaultPersistOnFlushEventListener(), workMap);
/* 144:    */     
/* 145:    */ 
/* 146:    */ 
/* 147:    */ 
/* 148:    */ 
/* 149:    */ 
/* 150:207 */     prepareListeners(EventType.DELETE, new DefaultDeleteEventListener(), workMap);
/* 151:    */     
/* 152:    */ 
/* 153:    */ 
/* 154:    */ 
/* 155:    */ 
/* 156:    */ 
/* 157:214 */     prepareListeners(EventType.DIRTY_CHECK, new DefaultDirtyCheckEventListener(), workMap);
/* 158:    */     
/* 159:    */ 
/* 160:    */ 
/* 161:    */ 
/* 162:    */ 
/* 163:    */ 
/* 164:221 */     prepareListeners(EventType.EVICT, new DefaultEvictEventListener(), workMap);
/* 165:    */     
/* 166:    */ 
/* 167:    */ 
/* 168:    */ 
/* 169:    */ 
/* 170:    */ 
/* 171:228 */     prepareListeners(EventType.FLUSH, new DefaultFlushEventListener(), workMap);
/* 172:    */     
/* 173:    */ 
/* 174:    */ 
/* 175:    */ 
/* 176:    */ 
/* 177:    */ 
/* 178:235 */     prepareListeners(EventType.FLUSH_ENTITY, new DefaultFlushEntityEventListener(), workMap);
/* 179:    */     
/* 180:    */ 
/* 181:    */ 
/* 182:    */ 
/* 183:    */ 
/* 184:    */ 
/* 185:242 */     prepareListeners(EventType.LOAD, new DefaultLoadEventListener(), workMap);
/* 186:    */     
/* 187:    */ 
/* 188:    */ 
/* 189:    */ 
/* 190:    */ 
/* 191:    */ 
/* 192:249 */     prepareListeners(EventType.INIT_COLLECTION, new DefaultInitializeCollectionEventListener(), workMap);
/* 193:    */     
/* 194:    */ 
/* 195:    */ 
/* 196:    */ 
/* 197:    */ 
/* 198:    */ 
/* 199:256 */     prepareListeners(EventType.LOCK, new DefaultLockEventListener(), workMap);
/* 200:    */     
/* 201:    */ 
/* 202:    */ 
/* 203:    */ 
/* 204:    */ 
/* 205:    */ 
/* 206:263 */     prepareListeners(EventType.MERGE, new DefaultMergeEventListener(), workMap);
/* 207:    */     
/* 208:    */ 
/* 209:    */ 
/* 210:    */ 
/* 211:    */ 
/* 212:    */ 
/* 213:270 */     prepareListeners(EventType.PRE_COLLECTION_RECREATE, workMap);
/* 214:    */     
/* 215:    */ 
/* 216:    */ 
/* 217:    */ 
/* 218:    */ 
/* 219:276 */     prepareListeners(EventType.PRE_COLLECTION_REMOVE, workMap);
/* 220:    */     
/* 221:    */ 
/* 222:    */ 
/* 223:    */ 
/* 224:    */ 
/* 225:282 */     prepareListeners(EventType.PRE_COLLECTION_UPDATE, workMap);
/* 226:    */     
/* 227:    */ 
/* 228:    */ 
/* 229:    */ 
/* 230:    */ 
/* 231:288 */     prepareListeners(EventType.PRE_DELETE, workMap);
/* 232:    */     
/* 233:    */ 
/* 234:    */ 
/* 235:    */ 
/* 236:    */ 
/* 237:294 */     prepareListeners(EventType.PRE_INSERT, workMap);
/* 238:    */     
/* 239:    */ 
/* 240:    */ 
/* 241:    */ 
/* 242:    */ 
/* 243:300 */     prepareListeners(EventType.PRE_LOAD, new DefaultPreLoadEventListener(), workMap);
/* 244:    */     
/* 245:    */ 
/* 246:    */ 
/* 247:    */ 
/* 248:    */ 
/* 249:    */ 
/* 250:307 */     prepareListeners(EventType.PRE_UPDATE, workMap);
/* 251:    */     
/* 252:    */ 
/* 253:    */ 
/* 254:    */ 
/* 255:    */ 
/* 256:313 */     prepareListeners(EventType.POST_COLLECTION_RECREATE, workMap);
/* 257:    */     
/* 258:    */ 
/* 259:    */ 
/* 260:    */ 
/* 261:    */ 
/* 262:319 */     prepareListeners(EventType.POST_COLLECTION_REMOVE, workMap);
/* 263:    */     
/* 264:    */ 
/* 265:    */ 
/* 266:    */ 
/* 267:    */ 
/* 268:325 */     prepareListeners(EventType.POST_COLLECTION_UPDATE, workMap);
/* 269:    */     
/* 270:    */ 
/* 271:    */ 
/* 272:    */ 
/* 273:    */ 
/* 274:331 */     prepareListeners(EventType.POST_COMMIT_DELETE, workMap);
/* 275:    */     
/* 276:    */ 
/* 277:    */ 
/* 278:    */ 
/* 279:    */ 
/* 280:337 */     prepareListeners(EventType.POST_COMMIT_INSERT, workMap);
/* 281:    */     
/* 282:    */ 
/* 283:    */ 
/* 284:    */ 
/* 285:    */ 
/* 286:343 */     prepareListeners(EventType.POST_COMMIT_UPDATE, workMap);
/* 287:    */     
/* 288:    */ 
/* 289:    */ 
/* 290:    */ 
/* 291:    */ 
/* 292:349 */     prepareListeners(EventType.POST_DELETE, workMap);
/* 293:    */     
/* 294:    */ 
/* 295:    */ 
/* 296:    */ 
/* 297:    */ 
/* 298:355 */     prepareListeners(EventType.POST_INSERT, workMap);
/* 299:    */     
/* 300:    */ 
/* 301:    */ 
/* 302:    */ 
/* 303:    */ 
/* 304:361 */     prepareListeners(EventType.POST_LOAD, new DefaultPostLoadEventListener(), workMap);
/* 305:    */     
/* 306:    */ 
/* 307:    */ 
/* 308:    */ 
/* 309:    */ 
/* 310:    */ 
/* 311:368 */     prepareListeners(EventType.POST_UPDATE, workMap);
/* 312:    */     
/* 313:    */ 
/* 314:    */ 
/* 315:    */ 
/* 316:    */ 
/* 317:374 */     prepareListeners(EventType.UPDATE, new DefaultUpdateEventListener(), workMap);
/* 318:    */     
/* 319:    */ 
/* 320:    */ 
/* 321:    */ 
/* 322:    */ 
/* 323:    */ 
/* 324:381 */     prepareListeners(EventType.REFRESH, new DefaultRefreshEventListener(), workMap);
/* 325:    */     
/* 326:    */ 
/* 327:    */ 
/* 328:    */ 
/* 329:    */ 
/* 330:    */ 
/* 331:388 */     prepareListeners(EventType.REPLICATE, new DefaultReplicateEventListener(), workMap);
/* 332:    */     
/* 333:    */ 
/* 334:    */ 
/* 335:    */ 
/* 336:    */ 
/* 337:    */ 
/* 338:395 */     prepareListeners(EventType.SAVE, new DefaultSaveEventListener(), workMap);
/* 339:    */     
/* 340:    */ 
/* 341:    */ 
/* 342:    */ 
/* 343:    */ 
/* 344:    */ 
/* 345:402 */     prepareListeners(EventType.SAVE_UPDATE, new DefaultSaveOrUpdateEventListener(), workMap);
/* 346:    */     
/* 347:    */ 
/* 348:    */ 
/* 349:    */ 
/* 350:    */ 
/* 351:408 */     return Collections.unmodifiableMap(workMap);
/* 352:    */   }
/* 353:    */   
/* 354:    */   private static <T> void prepareListeners(EventType<T> type, Map<EventType, EventListenerGroupImpl> map)
/* 355:    */   {
/* 356:412 */     prepareListeners(type, null, map);
/* 357:    */   }
/* 358:    */   
/* 359:    */   private static <T> void prepareListeners(EventType<T> type, T defaultListener, Map<EventType, EventListenerGroupImpl> map)
/* 360:    */   {
/* 361:416 */     EventListenerGroupImpl<T> listeners = new EventListenerGroupImpl(type);
/* 362:417 */     if (defaultListener != null) {
/* 363:418 */       listeners.appendListener(defaultListener);
/* 364:    */     }
/* 365:420 */     map.put(type, listeners);
/* 366:    */   }
/* 367:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.service.internal.EventListenerRegistryImpl
 * JD-Core Version:    0.7.0.1
 */