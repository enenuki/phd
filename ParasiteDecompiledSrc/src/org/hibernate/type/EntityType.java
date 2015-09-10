/*   1:    */ package org.hibernate.type;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.sql.ResultSet;
/*   5:    */ import java.sql.SQLException;
/*   6:    */ import java.util.Map;
/*   7:    */ import org.dom4j.Element;
/*   8:    */ import org.dom4j.Node;
/*   9:    */ import org.hibernate.AssertionFailure;
/*  10:    */ import org.hibernate.EntityMode;
/*  11:    */ import org.hibernate.HibernateException;
/*  12:    */ import org.hibernate.MappingException;
/*  13:    */ import org.hibernate.engine.internal.ForeignKeys;
/*  14:    */ import org.hibernate.engine.spi.EntityUniqueKey;
/*  15:    */ import org.hibernate.engine.spi.Mapping;
/*  16:    */ import org.hibernate.engine.spi.PersistenceContext;
/*  17:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  18:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  19:    */ import org.hibernate.internal.util.ReflectHelper;
/*  20:    */ import org.hibernate.persister.entity.EntityPersister;
/*  21:    */ import org.hibernate.persister.entity.Joinable;
/*  22:    */ import org.hibernate.persister.entity.UniqueKeyLoadable;
/*  23:    */ import org.hibernate.proxy.HibernateProxy;
/*  24:    */ import org.hibernate.proxy.LazyInitializer;
/*  25:    */ import org.hibernate.tuple.ElementWrapper;
/*  26:    */ 
/*  27:    */ public abstract class EntityType
/*  28:    */   extends AbstractType
/*  29:    */   implements AssociationType
/*  30:    */ {
/*  31:    */   private final TypeFactory.TypeScope scope;
/*  32:    */   private final String associatedEntityName;
/*  33:    */   protected final String uniqueKeyPropertyName;
/*  34:    */   protected final boolean isEmbeddedInXML;
/*  35:    */   private final boolean eager;
/*  36:    */   private final boolean unwrapProxy;
/*  37:    */   private transient Class returnedClass;
/*  38:    */   
/*  39:    */   protected EntityType(TypeFactory.TypeScope scope, String entityName, String uniqueKeyPropertyName, boolean eager, boolean isEmbeddedInXML, boolean unwrapProxy)
/*  40:    */   {
/*  41: 87 */     this.scope = scope;
/*  42: 88 */     this.associatedEntityName = entityName;
/*  43: 89 */     this.uniqueKeyPropertyName = uniqueKeyPropertyName;
/*  44: 90 */     this.isEmbeddedInXML = isEmbeddedInXML;
/*  45: 91 */     this.eager = eager;
/*  46: 92 */     this.unwrapProxy = unwrapProxy;
/*  47:    */   }
/*  48:    */   
/*  49:    */   protected TypeFactory.TypeScope scope()
/*  50:    */   {
/*  51: 96 */     return this.scope;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public boolean isAssociationType()
/*  55:    */   {
/*  56:105 */     return true;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public final boolean isEntityType()
/*  60:    */   {
/*  61:114 */     return true;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public boolean isMutable()
/*  65:    */   {
/*  66:121 */     return false;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public String toString()
/*  70:    */   {
/*  71:130 */     return getClass().getName() + '(' + getAssociatedEntityName() + ')';
/*  72:    */   }
/*  73:    */   
/*  74:    */   public String getName()
/*  75:    */   {
/*  76:137 */     return this.associatedEntityName;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public boolean isReferenceToPrimaryKey()
/*  80:    */   {
/*  81:147 */     return this.uniqueKeyPropertyName == null;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public String getRHSUniqueKeyPropertyName()
/*  85:    */   {
/*  86:151 */     return this.uniqueKeyPropertyName;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public String getLHSPropertyName()
/*  90:    */   {
/*  91:155 */     return null;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public String getPropertyName()
/*  95:    */   {
/*  96:159 */     return null;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public final String getAssociatedEntityName()
/* 100:    */   {
/* 101:168 */     return this.associatedEntityName;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public String getAssociatedEntityName(SessionFactoryImplementor factory)
/* 105:    */   {
/* 106:178 */     return getAssociatedEntityName();
/* 107:    */   }
/* 108:    */   
/* 109:    */   public Joinable getAssociatedJoinable(SessionFactoryImplementor factory)
/* 110:    */     throws MappingException
/* 111:    */   {
/* 112:189 */     return (Joinable)factory.getEntityPersister(this.associatedEntityName);
/* 113:    */   }
/* 114:    */   
/* 115:    */   public final Class getReturnedClass()
/* 116:    */   {
/* 117:203 */     if (this.returnedClass == null) {
/* 118:204 */       this.returnedClass = determineAssociatedEntityClass();
/* 119:    */     }
/* 120:206 */     return this.returnedClass;
/* 121:    */   }
/* 122:    */   
/* 123:    */   private Class determineAssociatedEntityClass()
/* 124:    */   {
/* 125:    */     try
/* 126:    */     {
/* 127:211 */       return ReflectHelper.classForName(getAssociatedEntityName());
/* 128:    */     }
/* 129:    */     catch (ClassNotFoundException cnfe) {}
/* 130:214 */     return Map.class;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public Object nullSafeGet(ResultSet rs, String name, SessionImplementor session, Object owner)
/* 134:    */     throws HibernateException, SQLException
/* 135:    */   {
/* 136:223 */     return nullSafeGet(rs, new String[] { name }, session, owner);
/* 137:    */   }
/* 138:    */   
/* 139:    */   public final Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner)
/* 140:    */     throws HibernateException, SQLException
/* 141:    */   {
/* 142:234 */     return resolve(hydrate(rs, names, session, owner), session, owner);
/* 143:    */   }
/* 144:    */   
/* 145:    */   public final boolean isSame(Object x, Object y)
/* 146:    */   {
/* 147:246 */     return x == y;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public int compare(Object x, Object y)
/* 151:    */   {
/* 152:253 */     return 0;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public Object deepCopy(Object value, SessionFactoryImplementor factory)
/* 156:    */   {
/* 157:260 */     return value;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public Object replace(Object original, Object target, SessionImplementor session, Object owner, Map copyCache)
/* 161:    */     throws HibernateException
/* 162:    */   {
/* 163:272 */     if (original == null) {
/* 164:273 */       return null;
/* 165:    */     }
/* 166:275 */     Object cached = copyCache.get(original);
/* 167:276 */     if (cached != null) {
/* 168:277 */       return cached;
/* 169:    */     }
/* 170:280 */     if (original == target) {
/* 171:281 */       return target;
/* 172:    */     }
/* 173:283 */     if ((session.getContextEntityIdentifier(original) == null) && (ForeignKeys.isTransient(this.associatedEntityName, original, Boolean.FALSE, session)))
/* 174:    */     {
/* 175:285 */       Object copy = session.getFactory().getEntityPersister(this.associatedEntityName).instantiate(null, session);
/* 176:    */       
/* 177:    */ 
/* 178:288 */       copyCache.put(original, copy);
/* 179:289 */       return copy;
/* 180:    */     }
/* 181:292 */     Object id = getIdentifier(original, session);
/* 182:293 */     if (id == null) {
/* 183:294 */       throw new AssertionFailure("non-transient entity has a null id");
/* 184:    */     }
/* 185:296 */     id = getIdentifierOrUniqueKeyType(session.getFactory()).replace(id, null, session, owner, copyCache);
/* 186:    */     
/* 187:298 */     return resolve(id, session, owner);
/* 188:    */   }
/* 189:    */   
/* 190:    */   public int getHashCode(Object x, SessionFactoryImplementor factory)
/* 191:    */   {
/* 192:307 */     EntityPersister persister = factory.getEntityPersister(this.associatedEntityName);
/* 193:308 */     if (!persister.canExtractIdOutOfEntity()) {
/* 194:309 */       return super.getHashCode(x);
/* 195:    */     }
/* 196:    */     Serializable id;
/* 197:    */     Serializable id;
/* 198:313 */     if ((x instanceof HibernateProxy))
/* 199:    */     {
/* 200:314 */       id = ((HibernateProxy)x).getHibernateLazyInitializer().getIdentifier();
/* 201:    */     }
/* 202:    */     else
/* 203:    */     {
/* 204:317 */       Class mappedClass = persister.getMappedClass();
/* 205:    */       Serializable id;
/* 206:318 */       if (mappedClass.isAssignableFrom(x.getClass())) {
/* 207:319 */         id = persister.getIdentifier(x);
/* 208:    */       } else {
/* 209:322 */         id = (Serializable)x;
/* 210:    */       }
/* 211:    */     }
/* 212:325 */     return persister.getIdentifierType().getHashCode(id, factory);
/* 213:    */   }
/* 214:    */   
/* 215:    */   public boolean isEqual(Object x, Object y, SessionFactoryImplementor factory)
/* 216:    */   {
/* 217:332 */     EntityPersister persister = factory.getEntityPersister(this.associatedEntityName);
/* 218:333 */     if (!persister.canExtractIdOutOfEntity()) {
/* 219:334 */       return super.isEqual(x, y);
/* 220:    */     }
/* 221:337 */     Class mappedClass = persister.getMappedClass();
/* 222:    */     Serializable xid;
/* 223:    */     Serializable xid;
/* 224:339 */     if ((x instanceof HibernateProxy))
/* 225:    */     {
/* 226:340 */       xid = ((HibernateProxy)x).getHibernateLazyInitializer().getIdentifier();
/* 227:    */     }
/* 228:    */     else
/* 229:    */     {
/* 230:    */       Serializable xid;
/* 231:344 */       if (mappedClass.isAssignableFrom(x.getClass())) {
/* 232:345 */         xid = persister.getIdentifier(x);
/* 233:    */       } else {
/* 234:349 */         xid = (Serializable)x;
/* 235:    */       }
/* 236:    */     }
/* 237:    */     Serializable yid;
/* 238:    */     Serializable yid;
/* 239:354 */     if ((y instanceof HibernateProxy))
/* 240:    */     {
/* 241:355 */       yid = ((HibernateProxy)y).getHibernateLazyInitializer().getIdentifier();
/* 242:    */     }
/* 243:    */     else
/* 244:    */     {
/* 245:    */       Serializable yid;
/* 246:359 */       if (mappedClass.isAssignableFrom(y.getClass())) {
/* 247:360 */         yid = persister.getIdentifier(y);
/* 248:    */       } else {
/* 249:364 */         yid = (Serializable)y;
/* 250:    */       }
/* 251:    */     }
/* 252:368 */     return persister.getIdentifierType().isEqual(xid, yid, factory);
/* 253:    */   }
/* 254:    */   
/* 255:    */   public boolean isEmbeddedInXML()
/* 256:    */   {
/* 257:376 */     return this.isEmbeddedInXML;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public boolean isXMLElement()
/* 261:    */   {
/* 262:383 */     return this.isEmbeddedInXML;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public Object fromXMLNode(Node xml, Mapping factory)
/* 266:    */     throws HibernateException
/* 267:    */   {
/* 268:390 */     if (!this.isEmbeddedInXML) {
/* 269:391 */       return getIdentifierType(factory).fromXMLNode(xml, factory);
/* 270:    */     }
/* 271:394 */     return xml;
/* 272:    */   }
/* 273:    */   
/* 274:    */   public void setToXMLNode(Node node, Object value, SessionFactoryImplementor factory)
/* 275:    */     throws HibernateException
/* 276:    */   {
/* 277:402 */     if (!this.isEmbeddedInXML)
/* 278:    */     {
/* 279:403 */       getIdentifierType(factory).setToXMLNode(node, value, factory);
/* 280:    */     }
/* 281:    */     else
/* 282:    */     {
/* 283:406 */       Element elt = (Element)value;
/* 284:407 */       replaceNode(node, new ElementWrapper(elt));
/* 285:    */     }
/* 286:    */   }
/* 287:    */   
/* 288:    */   public String getOnCondition(String alias, SessionFactoryImplementor factory, Map enabledFilters)
/* 289:    */     throws MappingException
/* 290:    */   {
/* 291:413 */     if (isReferenceToPrimaryKey()) {
/* 292:414 */       return "";
/* 293:    */     }
/* 294:417 */     return getAssociatedJoinable(factory).filterFragment(alias, enabledFilters);
/* 295:    */   }
/* 296:    */   
/* 297:    */   public Object resolve(Object value, SessionImplementor session, Object owner)
/* 298:    */     throws HibernateException
/* 299:    */   {
/* 300:425 */     if (isNotEmbedded(session)) {
/* 301:426 */       return value;
/* 302:    */     }
/* 303:429 */     if (value == null) {
/* 304:430 */       return null;
/* 305:    */     }
/* 306:433 */     if (isNull(owner, session)) {
/* 307:434 */       return null;
/* 308:    */     }
/* 309:437 */     if (isReferenceToPrimaryKey()) {
/* 310:438 */       return resolveIdentifier((Serializable)value, session);
/* 311:    */     }
/* 312:441 */     return loadByUniqueKey(getAssociatedEntityName(), this.uniqueKeyPropertyName, value, session);
/* 313:    */   }
/* 314:    */   
/* 315:    */   public Type getSemiResolvedType(SessionFactoryImplementor factory)
/* 316:    */   {
/* 317:447 */     return factory.getEntityPersister(this.associatedEntityName).getIdentifierType();
/* 318:    */   }
/* 319:    */   
/* 320:    */   protected final Object getIdentifier(Object value, SessionImplementor session)
/* 321:    */     throws HibernateException
/* 322:    */   {
/* 323:451 */     if (isNotEmbedded(session)) {
/* 324:452 */       return value;
/* 325:    */     }
/* 326:455 */     if (isReferenceToPrimaryKey()) {
/* 327:456 */       return ForeignKeys.getEntityIdentifierIfNotUnsaved(getAssociatedEntityName(), value, session);
/* 328:    */     }
/* 329:458 */     if (value == null) {
/* 330:459 */       return null;
/* 331:    */     }
/* 332:462 */     EntityPersister entityPersister = session.getFactory().getEntityPersister(getAssociatedEntityName());
/* 333:463 */     Object propertyValue = entityPersister.getPropertyValue(value, this.uniqueKeyPropertyName);
/* 334:    */     
/* 335:    */ 
/* 336:    */ 
/* 337:467 */     Type type = entityPersister.getPropertyType(this.uniqueKeyPropertyName);
/* 338:468 */     if (type.isEntityType()) {
/* 339:469 */       propertyValue = ((EntityType)type).getIdentifier(propertyValue, session);
/* 340:    */     }
/* 341:472 */     return propertyValue;
/* 342:    */   }
/* 343:    */   
/* 344:    */   protected boolean isNotEmbedded(SessionImplementor session)
/* 345:    */   {
/* 346:478 */     return false;
/* 347:    */   }
/* 348:    */   
/* 349:    */   public String toLoggableString(Object value, SessionFactoryImplementor factory)
/* 350:    */   {
/* 351:490 */     if (value == null) {
/* 352:491 */       return "null";
/* 353:    */     }
/* 354:494 */     EntityPersister persister = factory.getEntityPersister(this.associatedEntityName);
/* 355:495 */     StringBuffer result = new StringBuffer().append(this.associatedEntityName);
/* 356:497 */     if (persister.hasIdentifierProperty())
/* 357:    */     {
/* 358:498 */       EntityMode entityMode = persister.getEntityMode();
/* 359:    */       Serializable id;
/* 360:    */       Serializable id;
/* 361:500 */       if (entityMode == null)
/* 362:    */       {
/* 363:501 */         if (this.isEmbeddedInXML) {
/* 364:502 */           throw new ClassCastException(value.getClass().getName());
/* 365:    */         }
/* 366:504 */         id = (Serializable)value;
/* 367:    */       }
/* 368:    */       else
/* 369:    */       {
/* 370:507 */         id = persister.getIdentifier(value);
/* 371:    */       }
/* 372:510 */       result.append('#').append(persister.getIdentifierType().toLoggableString(id, factory));
/* 373:    */     }
/* 374:514 */     return result.toString();
/* 375:    */   }
/* 376:    */   
/* 377:    */   public abstract boolean isOneToOne();
/* 378:    */   
/* 379:    */   public boolean isLogicalOneToOne()
/* 380:    */   {
/* 381:530 */     return isOneToOne();
/* 382:    */   }
/* 383:    */   
/* 384:    */   Type getIdentifierType(Mapping factory)
/* 385:    */   {
/* 386:540 */     return factory.getIdentifierType(getAssociatedEntityName());
/* 387:    */   }
/* 388:    */   
/* 389:    */   Type getIdentifierType(SessionImplementor session)
/* 390:    */   {
/* 391:550 */     return getIdentifierType(session.getFactory());
/* 392:    */   }
/* 393:    */   
/* 394:    */   public final Type getIdentifierOrUniqueKeyType(Mapping factory)
/* 395:    */     throws MappingException
/* 396:    */   {
/* 397:564 */     if (isReferenceToPrimaryKey()) {
/* 398:565 */       return getIdentifierType(factory);
/* 399:    */     }
/* 400:568 */     Type type = factory.getReferencedPropertyType(getAssociatedEntityName(), this.uniqueKeyPropertyName);
/* 401:569 */     if (type.isEntityType()) {
/* 402:570 */       type = ((EntityType)type).getIdentifierOrUniqueKeyType(factory);
/* 403:    */     }
/* 404:572 */     return type;
/* 405:    */   }
/* 406:    */   
/* 407:    */   public final String getIdentifierOrUniqueKeyPropertyName(Mapping factory)
/* 408:    */     throws MappingException
/* 409:    */   {
/* 410:586 */     if (isReferenceToPrimaryKey()) {
/* 411:587 */       return factory.getIdentifierPropertyName(getAssociatedEntityName());
/* 412:    */     }
/* 413:590 */     return this.uniqueKeyPropertyName;
/* 414:    */   }
/* 415:    */   
/* 416:    */   protected abstract boolean isNullable();
/* 417:    */   
/* 418:    */   protected final Object resolveIdentifier(Serializable id, SessionImplementor session)
/* 419:    */     throws HibernateException
/* 420:    */   {
/* 421:605 */     boolean isProxyUnwrapEnabled = (this.unwrapProxy) && (session.getFactory().getEntityPersister(getAssociatedEntityName()).isInstrumented());
/* 422:    */     
/* 423:    */ 
/* 424:    */ 
/* 425:    */ 
/* 426:610 */     Object proxyOrEntity = session.internalLoad(getAssociatedEntityName(), id, this.eager, (isNullable()) && (!isProxyUnwrapEnabled));
/* 427:617 */     if ((proxyOrEntity instanceof HibernateProxy)) {
/* 428:618 */       ((HibernateProxy)proxyOrEntity).getHibernateLazyInitializer().setUnwrap(isProxyUnwrapEnabled);
/* 429:    */     }
/* 430:622 */     return proxyOrEntity;
/* 431:    */   }
/* 432:    */   
/* 433:    */   protected boolean isNull(Object owner, SessionImplementor session)
/* 434:    */   {
/* 435:626 */     return false;
/* 436:    */   }
/* 437:    */   
/* 438:    */   public Object loadByUniqueKey(String entityName, String uniqueKeyPropertyName, Object key, SessionImplementor session)
/* 439:    */     throws HibernateException
/* 440:    */   {
/* 441:644 */     SessionFactoryImplementor factory = session.getFactory();
/* 442:645 */     UniqueKeyLoadable persister = (UniqueKeyLoadable)factory.getEntityPersister(entityName);
/* 443:    */     
/* 444:    */ 
/* 445:    */ 
/* 446:649 */     EntityUniqueKey euk = new EntityUniqueKey(entityName, uniqueKeyPropertyName, key, getIdentifierOrUniqueKeyType(factory), persister.getEntityMode(), session.getFactory());
/* 447:    */     
/* 448:    */ 
/* 449:    */ 
/* 450:    */ 
/* 451:    */ 
/* 452:    */ 
/* 453:    */ 
/* 454:    */ 
/* 455:658 */     PersistenceContext persistenceContext = session.getPersistenceContext();
/* 456:659 */     Object result = persistenceContext.getEntity(euk);
/* 457:660 */     if (result == null) {
/* 458:661 */       result = persister.loadByUniqueKey(uniqueKeyPropertyName, key, session);
/* 459:    */     }
/* 460:663 */     return result == null ? null : persistenceContext.proxyFor(result);
/* 461:    */   }
/* 462:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.EntityType
 * JD-Core Version:    0.7.0.1
 */