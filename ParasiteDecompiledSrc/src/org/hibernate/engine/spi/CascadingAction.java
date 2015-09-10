/*   1:    */ package org.hibernate.engine.spi;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import java.util.Map;
/*   5:    */ import java.util.Set;
/*   6:    */ import org.hibernate.HibernateException;
/*   7:    */ import org.hibernate.LockMode;
/*   8:    */ import org.hibernate.LockOptions;
/*   9:    */ import org.hibernate.ReplicationMode;
/*  10:    */ import org.hibernate.Session.LockRequest;
/*  11:    */ import org.hibernate.TransientObjectException;
/*  12:    */ import org.hibernate.collection.spi.PersistentCollection;
/*  13:    */ import org.hibernate.engine.internal.ForeignKeys;
/*  14:    */ import org.hibernate.event.spi.EventSource;
/*  15:    */ import org.hibernate.internal.CoreMessageLogger;
/*  16:    */ import org.hibernate.persister.entity.EntityPersister;
/*  17:    */ import org.hibernate.proxy.HibernateProxy;
/*  18:    */ import org.hibernate.type.CollectionType;
/*  19:    */ import org.hibernate.type.EntityType;
/*  20:    */ import org.hibernate.type.Type;
/*  21:    */ import org.jboss.logging.Logger;
/*  22:    */ 
/*  23:    */ public abstract class CascadingAction
/*  24:    */ {
/*  25: 54 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, CascadingAction.class.getName());
/*  26:    */   
/*  27:    */   public abstract void cascade(EventSource paramEventSource, Object paramObject1, String paramString, Object paramObject2, boolean paramBoolean)
/*  28:    */     throws HibernateException;
/*  29:    */   
/*  30:    */   public abstract Iterator getCascadableChildrenIterator(EventSource paramEventSource, CollectionType paramCollectionType, Object paramObject);
/*  31:    */   
/*  32:    */   public abstract boolean deleteOrphans();
/*  33:    */   
/*  34:    */   public boolean requiresNoCascadeChecking()
/*  35:    */   {
/*  36:108 */     return false;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void noCascade(EventSource session, Object child, Object parent, EntityPersister persister, int propertyIndex) {}
/*  40:    */   
/*  41:    */   public boolean performOnLazyProperty()
/*  42:    */   {
/*  43:128 */     return true;
/*  44:    */   }
/*  45:    */   
/*  46:137 */   public static final CascadingAction DELETE = new CascadingAction()
/*  47:    */   {
/*  48:    */     public void cascade(EventSource session, Object child, String entityName, Object anything, boolean isCascadeDeleteEnabled)
/*  49:    */       throws HibernateException
/*  50:    */     {
/*  51:141 */       CascadingAction.LOG.tracev("Cascading to delete: {0}", entityName);
/*  52:142 */       session.delete(entityName, child, isCascadeDeleteEnabled, (Set)anything);
/*  53:    */     }
/*  54:    */     
/*  55:    */     public Iterator getCascadableChildrenIterator(EventSource session, CollectionType collectionType, Object collection)
/*  56:    */     {
/*  57:147 */       return CascadingAction.getAllElementsIterator(session, collectionType, collection);
/*  58:    */     }
/*  59:    */     
/*  60:    */     public boolean deleteOrphans()
/*  61:    */     {
/*  62:152 */       return true;
/*  63:    */     }
/*  64:    */     
/*  65:    */     public String toString()
/*  66:    */     {
/*  67:156 */       return "ACTION_DELETE";
/*  68:    */     }
/*  69:    */   };
/*  70:163 */   public static final CascadingAction LOCK = new CascadingAction()
/*  71:    */   {
/*  72:    */     public void cascade(EventSource session, Object child, String entityName, Object anything, boolean isCascadeDeleteEnabled)
/*  73:    */       throws HibernateException
/*  74:    */     {
/*  75:167 */       CascadingAction.LOG.tracev("Cascading to lock: {0}", entityName);
/*  76:168 */       LockMode lockMode = LockMode.NONE;
/*  77:169 */       LockOptions lr = new LockOptions();
/*  78:170 */       if ((anything instanceof LockOptions))
/*  79:    */       {
/*  80:171 */         LockOptions lockOptions = (LockOptions)anything;
/*  81:172 */         lr.setTimeOut(lockOptions.getTimeOut());
/*  82:173 */         lr.setScope(lockOptions.getScope());
/*  83:174 */         if (lockOptions.getScope() == true) {
/*  84:175 */           lockMode = lockOptions.getLockMode();
/*  85:    */         }
/*  86:    */       }
/*  87:177 */       lr.setLockMode(lockMode);
/*  88:178 */       session.buildLockRequest(lr).lock(entityName, child);
/*  89:    */     }
/*  90:    */     
/*  91:    */     public Iterator getCascadableChildrenIterator(EventSource session, CollectionType collectionType, Object collection)
/*  92:    */     {
/*  93:183 */       return getLoadedElementsIterator(session, collectionType, collection);
/*  94:    */     }
/*  95:    */     
/*  96:    */     public boolean deleteOrphans()
/*  97:    */     {
/*  98:188 */       return false;
/*  99:    */     }
/* 100:    */     
/* 101:    */     public String toString()
/* 102:    */     {
/* 103:192 */       return "ACTION_LOCK";
/* 104:    */     }
/* 105:    */   };
/* 106:199 */   public static final CascadingAction REFRESH = new CascadingAction()
/* 107:    */   {
/* 108:    */     public void cascade(EventSource session, Object child, String entityName, Object anything, boolean isCascadeDeleteEnabled)
/* 109:    */       throws HibernateException
/* 110:    */     {
/* 111:203 */       CascadingAction.LOG.tracev("Cascading to refresh: {0}", entityName);
/* 112:204 */       session.refresh(child, (Map)anything);
/* 113:    */     }
/* 114:    */     
/* 115:    */     public Iterator getCascadableChildrenIterator(EventSource session, CollectionType collectionType, Object collection)
/* 116:    */     {
/* 117:209 */       return getLoadedElementsIterator(session, collectionType, collection);
/* 118:    */     }
/* 119:    */     
/* 120:    */     public boolean deleteOrphans()
/* 121:    */     {
/* 122:213 */       return false;
/* 123:    */     }
/* 124:    */     
/* 125:    */     public String toString()
/* 126:    */     {
/* 127:217 */       return "ACTION_REFRESH";
/* 128:    */     }
/* 129:    */   };
/* 130:224 */   public static final CascadingAction EVICT = new CascadingAction()
/* 131:    */   {
/* 132:    */     public void cascade(EventSource session, Object child, String entityName, Object anything, boolean isCascadeDeleteEnabled)
/* 133:    */       throws HibernateException
/* 134:    */     {
/* 135:228 */       CascadingAction.LOG.tracev("Cascading to evict: {0}", entityName);
/* 136:229 */       session.evict(child);
/* 137:    */     }
/* 138:    */     
/* 139:    */     public Iterator getCascadableChildrenIterator(EventSource session, CollectionType collectionType, Object collection)
/* 140:    */     {
/* 141:234 */       return getLoadedElementsIterator(session, collectionType, collection);
/* 142:    */     }
/* 143:    */     
/* 144:    */     public boolean deleteOrphans()
/* 145:    */     {
/* 146:238 */       return false;
/* 147:    */     }
/* 148:    */     
/* 149:    */     public boolean performOnLazyProperty()
/* 150:    */     {
/* 151:242 */       return false;
/* 152:    */     }
/* 153:    */     
/* 154:    */     public String toString()
/* 155:    */     {
/* 156:246 */       return "ACTION_EVICT";
/* 157:    */     }
/* 158:    */   };
/* 159:253 */   public static final CascadingAction SAVE_UPDATE = new CascadingAction()
/* 160:    */   {
/* 161:    */     public void cascade(EventSource session, Object child, String entityName, Object anything, boolean isCascadeDeleteEnabled)
/* 162:    */       throws HibernateException
/* 163:    */     {
/* 164:257 */       CascadingAction.LOG.tracev("Cascading to save or update: {0}", entityName);
/* 165:258 */       session.saveOrUpdate(entityName, child);
/* 166:    */     }
/* 167:    */     
/* 168:    */     public Iterator getCascadableChildrenIterator(EventSource session, CollectionType collectionType, Object collection)
/* 169:    */     {
/* 170:263 */       return getLoadedElementsIterator(session, collectionType, collection);
/* 171:    */     }
/* 172:    */     
/* 173:    */     public boolean deleteOrphans()
/* 174:    */     {
/* 175:268 */       return true;
/* 176:    */     }
/* 177:    */     
/* 178:    */     public boolean performOnLazyProperty()
/* 179:    */     {
/* 180:272 */       return false;
/* 181:    */     }
/* 182:    */     
/* 183:    */     public String toString()
/* 184:    */     {
/* 185:276 */       return "ACTION_SAVE_UPDATE";
/* 186:    */     }
/* 187:    */   };
/* 188:283 */   public static final CascadingAction MERGE = new CascadingAction()
/* 189:    */   {
/* 190:    */     public void cascade(EventSource session, Object child, String entityName, Object anything, boolean isCascadeDeleteEnabled)
/* 191:    */       throws HibernateException
/* 192:    */     {
/* 193:287 */       CascadingAction.LOG.tracev("Cascading to merge: {0}", entityName);
/* 194:288 */       session.merge(entityName, child, (Map)anything);
/* 195:    */     }
/* 196:    */     
/* 197:    */     public Iterator getCascadableChildrenIterator(EventSource session, CollectionType collectionType, Object collection)
/* 198:    */     {
/* 199:294 */       return getLoadedElementsIterator(session, collectionType, collection);
/* 200:    */     }
/* 201:    */     
/* 202:    */     public boolean deleteOrphans()
/* 203:    */     {
/* 204:299 */       return false;
/* 205:    */     }
/* 206:    */     
/* 207:    */     public String toString()
/* 208:    */     {
/* 209:303 */       return "ACTION_MERGE";
/* 210:    */     }
/* 211:    */   };
/* 212:310 */   public static final CascadingAction PERSIST = new CascadingAction()
/* 213:    */   {
/* 214:    */     public void cascade(EventSource session, Object child, String entityName, Object anything, boolean isCascadeDeleteEnabled)
/* 215:    */       throws HibernateException
/* 216:    */     {
/* 217:314 */       CascadingAction.LOG.tracev("Cascading to persist: {0}" + entityName, new Object[0]);
/* 218:315 */       session.persist(entityName, child, (Map)anything);
/* 219:    */     }
/* 220:    */     
/* 221:    */     public Iterator getCascadableChildrenIterator(EventSource session, CollectionType collectionType, Object collection)
/* 222:    */     {
/* 223:320 */       return CascadingAction.getAllElementsIterator(session, collectionType, collection);
/* 224:    */     }
/* 225:    */     
/* 226:    */     public boolean deleteOrphans()
/* 227:    */     {
/* 228:324 */       return false;
/* 229:    */     }
/* 230:    */     
/* 231:    */     public boolean performOnLazyProperty()
/* 232:    */     {
/* 233:328 */       return false;
/* 234:    */     }
/* 235:    */     
/* 236:    */     public String toString()
/* 237:    */     {
/* 238:332 */       return "ACTION_PERSIST";
/* 239:    */     }
/* 240:    */   };
/* 241:341 */   public static final CascadingAction PERSIST_ON_FLUSH = new CascadingAction()
/* 242:    */   {
/* 243:    */     public void cascade(EventSource session, Object child, String entityName, Object anything, boolean isCascadeDeleteEnabled)
/* 244:    */       throws HibernateException
/* 245:    */     {
/* 246:345 */       CascadingAction.LOG.tracev("Cascading to persist on flush: {0}", entityName);
/* 247:346 */       session.persistOnFlush(entityName, child, (Map)anything);
/* 248:    */     }
/* 249:    */     
/* 250:    */     public Iterator getCascadableChildrenIterator(EventSource session, CollectionType collectionType, Object collection)
/* 251:    */     {
/* 252:351 */       return CascadingAction.getLoadedElementsIterator(session, collectionType, collection);
/* 253:    */     }
/* 254:    */     
/* 255:    */     public boolean deleteOrphans()
/* 256:    */     {
/* 257:355 */       return true;
/* 258:    */     }
/* 259:    */     
/* 260:    */     public boolean requiresNoCascadeChecking()
/* 261:    */     {
/* 262:359 */       return true;
/* 263:    */     }
/* 264:    */     
/* 265:    */     public void noCascade(EventSource session, Object child, Object parent, EntityPersister persister, int propertyIndex)
/* 266:    */     {
/* 267:368 */       if (child == null) {
/* 268:369 */         return;
/* 269:    */       }
/* 270:371 */       Type type = persister.getPropertyTypes()[propertyIndex];
/* 271:372 */       if (type.isEntityType())
/* 272:    */       {
/* 273:373 */         String childEntityName = ((EntityType)type).getAssociatedEntityName(session.getFactory());
/* 274:375 */         if ((!isInManagedState(child, session)) && (!(child instanceof HibernateProxy)) && (ForeignKeys.isTransient(childEntityName, child, null, session)))
/* 275:    */         {
/* 276:378 */           String parentEntiytName = persister.getEntityName();
/* 277:379 */           String propertyName = persister.getPropertyNames()[propertyIndex];
/* 278:380 */           throw new TransientObjectException("object references an unsaved transient instance - save the transient instance before flushing: " + parentEntiytName + "." + propertyName + " -> " + childEntityName);
/* 279:    */         }
/* 280:    */       }
/* 281:    */     }
/* 282:    */     
/* 283:    */     public boolean performOnLazyProperty()
/* 284:    */     {
/* 285:391 */       return false;
/* 286:    */     }
/* 287:    */     
/* 288:    */     private boolean isInManagedState(Object child, EventSource session)
/* 289:    */     {
/* 290:395 */       EntityEntry entry = session.getPersistenceContext().getEntry(child);
/* 291:396 */       return (entry != null) && ((entry.getStatus() == Status.MANAGED) || (entry.getStatus() == Status.READ_ONLY));
/* 292:    */     }
/* 293:    */     
/* 294:    */     public String toString()
/* 295:    */     {
/* 296:401 */       return "ACTION_PERSIST_ON_FLUSH";
/* 297:    */     }
/* 298:    */   };
/* 299:408 */   public static final CascadingAction REPLICATE = new CascadingAction()
/* 300:    */   {
/* 301:    */     public void cascade(EventSource session, Object child, String entityName, Object anything, boolean isCascadeDeleteEnabled)
/* 302:    */       throws HibernateException
/* 303:    */     {
/* 304:412 */       CascadingAction.LOG.tracev("Cascading to replicate: {0}", entityName);
/* 305:413 */       session.replicate(entityName, child, (ReplicationMode)anything);
/* 306:    */     }
/* 307:    */     
/* 308:    */     public Iterator getCascadableChildrenIterator(EventSource session, CollectionType collectionType, Object collection)
/* 309:    */     {
/* 310:418 */       return getLoadedElementsIterator(session, collectionType, collection);
/* 311:    */     }
/* 312:    */     
/* 313:    */     public boolean deleteOrphans()
/* 314:    */     {
/* 315:422 */       return false;
/* 316:    */     }
/* 317:    */     
/* 318:    */     public String toString()
/* 319:    */     {
/* 320:426 */       return "ACTION_REPLICATE";
/* 321:    */     }
/* 322:    */   };
/* 323:    */   
/* 324:    */   private static Iterator getAllElementsIterator(EventSource session, CollectionType collectionType, Object collection)
/* 325:    */   {
/* 326:446 */     return collectionType.getElementsIterator(collection, session);
/* 327:    */   }
/* 328:    */   
/* 329:    */   public static Iterator getLoadedElementsIterator(SessionImplementor session, CollectionType collectionType, Object collection)
/* 330:    */   {
/* 331:454 */     if (collectionIsInitialized(collection)) {
/* 332:456 */       return collectionType.getElementsIterator(collection, session);
/* 333:    */     }
/* 334:461 */     return ((PersistentCollection)collection).queuedAdditionIterator();
/* 335:    */   }
/* 336:    */   
/* 337:    */   private static boolean collectionIsInitialized(Object collection)
/* 338:    */   {
/* 339:466 */     return (!(collection instanceof PersistentCollection)) || (((PersistentCollection)collection).wasInitialized());
/* 340:    */   }
/* 341:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.spi.CascadingAction
 * JD-Core Version:    0.7.0.1
 */