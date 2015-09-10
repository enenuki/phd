/*   1:    */ package org.hibernate.type;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.sql.PreparedStatement;
/*   5:    */ import java.sql.ResultSet;
/*   6:    */ import java.sql.SQLException;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.Collection;
/*   9:    */ import java.util.Iterator;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Map;
/*  12:    */ import org.dom4j.Element;
/*  13:    */ import org.dom4j.Node;
/*  14:    */ import org.hibernate.EntityMode;
/*  15:    */ import org.hibernate.Hibernate;
/*  16:    */ import org.hibernate.HibernateException;
/*  17:    */ import org.hibernate.MappingException;
/*  18:    */ import org.hibernate.collection.spi.PersistentCollection;
/*  19:    */ import org.hibernate.engine.loading.internal.LoadContexts;
/*  20:    */ import org.hibernate.engine.spi.CollectionKey;
/*  21:    */ import org.hibernate.engine.spi.EntityEntry;
/*  22:    */ import org.hibernate.engine.spi.Mapping;
/*  23:    */ import org.hibernate.engine.spi.PersistenceContext;
/*  24:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  25:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  26:    */ import org.hibernate.internal.util.MarkerObject;
/*  27:    */ import org.hibernate.internal.util.collections.ArrayHelper;
/*  28:    */ import org.hibernate.metamodel.relational.Size;
/*  29:    */ import org.hibernate.persister.collection.CollectionPersister;
/*  30:    */ import org.hibernate.persister.collection.QueryableCollection;
/*  31:    */ import org.hibernate.persister.entity.EntityPersister;
/*  32:    */ import org.hibernate.persister.entity.Joinable;
/*  33:    */ import org.hibernate.proxy.HibernateProxy;
/*  34:    */ import org.hibernate.proxy.LazyInitializer;
/*  35:    */ 
/*  36:    */ public abstract class CollectionType
/*  37:    */   extends AbstractType
/*  38:    */   implements AssociationType
/*  39:    */ {
/*  40: 67 */   private static final Object NOT_NULL_COLLECTION = new MarkerObject("NOT NULL COLLECTION");
/*  41: 68 */   public static final Object UNFETCHED_COLLECTION = new MarkerObject("UNFETCHED COLLECTION");
/*  42:    */   private final TypeFactory.TypeScope typeScope;
/*  43:    */   private final String role;
/*  44:    */   private final String foreignKeyPropertyName;
/*  45:    */   private final boolean isEmbeddedInXML;
/*  46:    */   
/*  47:    */   public CollectionType(TypeFactory.TypeScope typeScope, String role, String foreignKeyPropertyName, boolean isEmbeddedInXML)
/*  48:    */   {
/*  49: 76 */     this.typeScope = typeScope;
/*  50: 77 */     this.role = role;
/*  51: 78 */     this.foreignKeyPropertyName = foreignKeyPropertyName;
/*  52: 79 */     this.isEmbeddedInXML = isEmbeddedInXML;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public boolean isEmbeddedInXML()
/*  56:    */   {
/*  57: 83 */     return this.isEmbeddedInXML;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public String getRole()
/*  61:    */   {
/*  62: 87 */     return this.role;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public Object indexOf(Object collection, Object element)
/*  66:    */   {
/*  67: 91 */     throw new UnsupportedOperationException("generic collections don't have indexes");
/*  68:    */   }
/*  69:    */   
/*  70:    */   public boolean contains(Object collection, Object childObject, SessionImplementor session)
/*  71:    */   {
/*  72: 97 */     Iterator elems = getElementsIterator(collection, session);
/*  73: 98 */     while (elems.hasNext())
/*  74:    */     {
/*  75: 99 */       Object element = elems.next();
/*  76:101 */       if ((element instanceof HibernateProxy))
/*  77:    */       {
/*  78:102 */         LazyInitializer li = ((HibernateProxy)element).getHibernateLazyInitializer();
/*  79:103 */         if (!li.isUninitialized()) {
/*  80:103 */           element = li.getImplementation();
/*  81:    */         }
/*  82:    */       }
/*  83:105 */       if (element == childObject) {
/*  84:105 */         return true;
/*  85:    */       }
/*  86:    */     }
/*  87:107 */     return false;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public boolean isCollectionType()
/*  91:    */   {
/*  92:111 */     return true;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public final boolean isEqual(Object x, Object y)
/*  96:    */   {
/*  97:115 */     return (x == y) || (((x instanceof PersistentCollection)) && (((PersistentCollection)x).isWrapper(y))) || (((y instanceof PersistentCollection)) && (((PersistentCollection)y).isWrapper(x)));
/*  98:    */   }
/*  99:    */   
/* 100:    */   public int compare(Object x, Object y)
/* 101:    */   {
/* 102:121 */     return 0;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public int getHashCode(Object x)
/* 106:    */   {
/* 107:125 */     throw new UnsupportedOperationException("cannot doAfterTransactionCompletion lookups on collections");
/* 108:    */   }
/* 109:    */   
/* 110:    */   public abstract PersistentCollection instantiate(SessionImplementor paramSessionImplementor, CollectionPersister paramCollectionPersister, Serializable paramSerializable);
/* 111:    */   
/* 112:    */   public Object nullSafeGet(ResultSet rs, String name, SessionImplementor session, Object owner)
/* 113:    */     throws SQLException
/* 114:    */   {
/* 115:140 */     return nullSafeGet(rs, new String[] { name }, session, owner);
/* 116:    */   }
/* 117:    */   
/* 118:    */   public Object nullSafeGet(ResultSet rs, String[] name, SessionImplementor session, Object owner)
/* 119:    */     throws HibernateException, SQLException
/* 120:    */   {
/* 121:145 */     return resolve(null, session, owner);
/* 122:    */   }
/* 123:    */   
/* 124:    */   public final void nullSafeSet(PreparedStatement st, Object value, int index, boolean[] settable, SessionImplementor session)
/* 125:    */     throws HibernateException, SQLException
/* 126:    */   {}
/* 127:    */   
/* 128:    */   public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session)
/* 129:    */     throws HibernateException, SQLException
/* 130:    */   {}
/* 131:    */   
/* 132:    */   public int[] sqlTypes(Mapping session)
/* 133:    */     throws MappingException
/* 134:    */   {
/* 135:158 */     return ArrayHelper.EMPTY_INT_ARRAY;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public Size[] dictatedSizes(Mapping mapping)
/* 139:    */     throws MappingException
/* 140:    */   {
/* 141:163 */     return new Size[] { LEGACY_DICTATED_SIZE };
/* 142:    */   }
/* 143:    */   
/* 144:    */   public Size[] defaultSizes(Mapping mapping)
/* 145:    */     throws MappingException
/* 146:    */   {
/* 147:168 */     return new Size[] { LEGACY_DEFAULT_SIZE };
/* 148:    */   }
/* 149:    */   
/* 150:    */   public int getColumnSpan(Mapping session)
/* 151:    */     throws MappingException
/* 152:    */   {
/* 153:172 */     return 0;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public String toLoggableString(Object value, SessionFactoryImplementor factory)
/* 157:    */     throws HibernateException
/* 158:    */   {
/* 159:177 */     if (value == null) {
/* 160:178 */       return "null";
/* 161:    */     }
/* 162:180 */     if (!Hibernate.isInitialized(value)) {
/* 163:181 */       return "<uninitialized>";
/* 164:    */     }
/* 165:184 */     return renderLoggableString(value, factory);
/* 166:    */   }
/* 167:    */   
/* 168:    */   protected String renderLoggableString(Object value, SessionFactoryImplementor factory)
/* 169:    */     throws HibernateException
/* 170:    */   {
/* 171:189 */     List<String> list = new ArrayList();
/* 172:190 */     Type elemType = getElementType(factory);
/* 173:191 */     Iterator itr = getElementsIterator(value);
/* 174:192 */     while (itr.hasNext()) {
/* 175:193 */       list.add(elemType.toLoggableString(itr.next(), factory));
/* 176:    */     }
/* 177:195 */     return list.toString();
/* 178:    */   }
/* 179:    */   
/* 180:    */   public Object deepCopy(Object value, SessionFactoryImplementor factory)
/* 181:    */     throws HibernateException
/* 182:    */   {
/* 183:200 */     return value;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public String getName()
/* 187:    */   {
/* 188:204 */     return getReturnedClass().getName() + '(' + getRole() + ')';
/* 189:    */   }
/* 190:    */   
/* 191:    */   public Iterator getElementsIterator(Object collection, SessionImplementor session)
/* 192:    */   {
/* 193:215 */     return getElementsIterator(collection);
/* 194:    */   }
/* 195:    */   
/* 196:    */   protected Iterator getElementsIterator(Object collection)
/* 197:    */   {
/* 198:225 */     return ((Collection)collection).iterator();
/* 199:    */   }
/* 200:    */   
/* 201:    */   public boolean isMutable()
/* 202:    */   {
/* 203:229 */     return false;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public Serializable disassemble(Object value, SessionImplementor session, Object owner)
/* 207:    */     throws HibernateException
/* 208:    */   {
/* 209:240 */     Serializable key = getKeyOfOwner(owner, session);
/* 210:241 */     if (key == null) {
/* 211:242 */       return null;
/* 212:    */     }
/* 213:245 */     return getPersister(session).getKeyType().disassemble(key, session, owner);
/* 214:    */   }
/* 215:    */   
/* 216:    */   public Object assemble(Serializable cached, SessionImplementor session, Object owner)
/* 217:    */     throws HibernateException
/* 218:    */   {
/* 219:255 */     if (cached == null) {
/* 220:256 */       return null;
/* 221:    */     }
/* 222:259 */     Serializable key = (Serializable)getPersister(session).getKeyType().assemble(cached, session, owner);
/* 223:    */     
/* 224:    */ 
/* 225:262 */     return resolveKey(key, session, owner);
/* 226:    */   }
/* 227:    */   
/* 228:    */   private boolean isOwnerVersioned(SessionImplementor session)
/* 229:    */     throws MappingException
/* 230:    */   {
/* 231:274 */     return getPersister(session).getOwnerEntityPersister().isVersioned();
/* 232:    */   }
/* 233:    */   
/* 234:    */   private CollectionPersister getPersister(SessionImplementor session)
/* 235:    */   {
/* 236:285 */     return session.getFactory().getCollectionPersister(this.role);
/* 237:    */   }
/* 238:    */   
/* 239:    */   public boolean isDirty(Object old, Object current, SessionImplementor session)
/* 240:    */     throws HibernateException
/* 241:    */   {
/* 242:295 */     return (isOwnerVersioned(session)) && (super.isDirty(old, current, session));
/* 243:    */   }
/* 244:    */   
/* 245:    */   public boolean isDirty(Object old, Object current, boolean[] checkable, SessionImplementor session)
/* 246:    */     throws HibernateException
/* 247:    */   {
/* 248:302 */     return isDirty(old, current, session);
/* 249:    */   }
/* 250:    */   
/* 251:    */   public abstract PersistentCollection wrap(SessionImplementor paramSessionImplementor, Object paramObject);
/* 252:    */   
/* 253:    */   public boolean isAssociationType()
/* 254:    */   {
/* 255:320 */     return true;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public ForeignKeyDirection getForeignKeyDirection()
/* 259:    */   {
/* 260:324 */     return ForeignKeyDirection.FOREIGN_KEY_TO_PARENT;
/* 261:    */   }
/* 262:    */   
/* 263:    */   public Serializable getKeyOfOwner(Object owner, SessionImplementor session)
/* 264:    */   {
/* 265:337 */     EntityEntry entityEntry = session.getPersistenceContext().getEntry(owner);
/* 266:338 */     if (entityEntry == null) {
/* 267:338 */       return null;
/* 268:    */     }
/* 269:341 */     if (this.foreignKeyPropertyName == null) {
/* 270:342 */       return entityEntry.getId();
/* 271:    */     }
/* 272:    */     Object id;
/* 273:    */     Object id;
/* 274:351 */     if (entityEntry.getLoadedState() != null) {
/* 275:352 */       id = entityEntry.getLoadedValue(this.foreignKeyPropertyName);
/* 276:    */     } else {
/* 277:355 */       id = entityEntry.getPersister().getPropertyValue(owner, this.foreignKeyPropertyName);
/* 278:    */     }
/* 279:360 */     Type keyType = getPersister(session).getKeyType();
/* 280:361 */     if (!keyType.getReturnedClass().isInstance(id)) {
/* 281:362 */       id = (Serializable)keyType.semiResolve(entityEntry.getLoadedValue(this.foreignKeyPropertyName), session, owner);
/* 282:    */     }
/* 283:369 */     return (Serializable)id;
/* 284:    */   }
/* 285:    */   
/* 286:    */   public Serializable getIdOfOwnerOrNull(Serializable key, SessionImplementor session)
/* 287:    */   {
/* 288:383 */     Serializable ownerId = null;
/* 289:384 */     if (this.foreignKeyPropertyName == null)
/* 290:    */     {
/* 291:385 */       ownerId = key;
/* 292:    */     }
/* 293:    */     else
/* 294:    */     {
/* 295:388 */       Type keyType = getPersister(session).getKeyType();
/* 296:389 */       EntityPersister ownerPersister = getPersister(session).getOwnerEntityPersister();
/* 297:    */       
/* 298:391 */       Class ownerMappedClass = ownerPersister.getMappedClass();
/* 299:392 */       if ((ownerMappedClass.isAssignableFrom(keyType.getReturnedClass())) && (keyType.getReturnedClass().isInstance(key))) {
/* 300:395 */         ownerId = ownerPersister.getIdentifier(key, session);
/* 301:    */       }
/* 302:    */     }
/* 303:401 */     return ownerId;
/* 304:    */   }
/* 305:    */   
/* 306:    */   public Object hydrate(ResultSet rs, String[] name, SessionImplementor session, Object owner)
/* 307:    */   {
/* 308:407 */     return NOT_NULL_COLLECTION;
/* 309:    */   }
/* 310:    */   
/* 311:    */   public Object resolve(Object value, SessionImplementor session, Object owner)
/* 312:    */     throws HibernateException
/* 313:    */   {
/* 314:413 */     return resolveKey(getKeyOfOwner(owner, session), session, owner);
/* 315:    */   }
/* 316:    */   
/* 317:    */   private Object resolveKey(Serializable key, SessionImplementor session, Object owner)
/* 318:    */   {
/* 319:419 */     return key == null ? null : getCollection(key, session, owner);
/* 320:    */   }
/* 321:    */   
/* 322:    */   public Object semiResolve(Object value, SessionImplementor session, Object owner)
/* 323:    */     throws HibernateException
/* 324:    */   {
/* 325:425 */     throw new UnsupportedOperationException("collection mappings may not form part of a property-ref");
/* 326:    */   }
/* 327:    */   
/* 328:    */   public boolean isArrayType()
/* 329:    */   {
/* 330:430 */     return false;
/* 331:    */   }
/* 332:    */   
/* 333:    */   public boolean useLHSPrimaryKey()
/* 334:    */   {
/* 335:434 */     return this.foreignKeyPropertyName == null;
/* 336:    */   }
/* 337:    */   
/* 338:    */   public String getRHSUniqueKeyPropertyName()
/* 339:    */   {
/* 340:438 */     return null;
/* 341:    */   }
/* 342:    */   
/* 343:    */   public Joinable getAssociatedJoinable(SessionFactoryImplementor factory)
/* 344:    */     throws MappingException
/* 345:    */   {
/* 346:443 */     return (Joinable)factory.getCollectionPersister(this.role);
/* 347:    */   }
/* 348:    */   
/* 349:    */   public boolean isModified(Object old, Object current, boolean[] checkable, SessionImplementor session)
/* 350:    */     throws HibernateException
/* 351:    */   {
/* 352:447 */     return false;
/* 353:    */   }
/* 354:    */   
/* 355:    */   public String getAssociatedEntityName(SessionFactoryImplementor factory)
/* 356:    */     throws MappingException
/* 357:    */   {
/* 358:    */     try
/* 359:    */     {
/* 360:454 */       QueryableCollection collectionPersister = (QueryableCollection)factory.getCollectionPersister(this.role);
/* 361:457 */       if (!collectionPersister.getElementType().isEntityType()) {
/* 362:458 */         throw new MappingException("collection was not an association: " + collectionPersister.getRole());
/* 363:    */       }
/* 364:464 */       return collectionPersister.getElementPersister().getEntityName();
/* 365:    */     }
/* 366:    */     catch (ClassCastException cce)
/* 367:    */     {
/* 368:468 */       throw new MappingException("collection role is not queryable " + this.role);
/* 369:    */     }
/* 370:    */   }
/* 371:    */   
/* 372:    */   public Object replaceElements(Object original, Object target, Object owner, Map copyCache, SessionImplementor session)
/* 373:    */   {
/* 374:489 */     Collection result = (Collection)target;
/* 375:490 */     result.clear();
/* 376:    */     
/* 377:    */ 
/* 378:493 */     Type elemType = getElementType(session.getFactory());
/* 379:494 */     Iterator iter = ((Collection)original).iterator();
/* 380:495 */     while (iter.hasNext()) {
/* 381:496 */       result.add(elemType.replace(iter.next(), null, session, owner, copyCache));
/* 382:    */     }
/* 383:506 */     if (((original instanceof PersistentCollection)) && 
/* 384:507 */       ((result instanceof PersistentCollection)) && 
/* 385:508 */       (!((PersistentCollection)original).isDirty())) {
/* 386:509 */       ((PersistentCollection)result).clearDirty();
/* 387:    */     }
/* 388:514 */     return result;
/* 389:    */   }
/* 390:    */   
/* 391:    */   protected Object instantiateResult(Object original)
/* 392:    */   {
/* 393:527 */     return instantiate(-1);
/* 394:    */   }
/* 395:    */   
/* 396:    */   public abstract Object instantiate(int paramInt);
/* 397:    */   
/* 398:    */   public Object replace(Object original, Object target, SessionImplementor session, Object owner, Map copyCache)
/* 399:    */     throws HibernateException
/* 400:    */   {
/* 401:550 */     if (original == null) {
/* 402:551 */       return null;
/* 403:    */     }
/* 404:553 */     if (!Hibernate.isInitialized(original)) {
/* 405:554 */       return target;
/* 406:    */     }
/* 407:559 */     Object result = (target == null) || (target == original) ? instantiateResult(original) : target;
/* 408:    */     
/* 409:    */ 
/* 410:    */ 
/* 411:563 */     result = replaceElements(original, result, owner, copyCache, session);
/* 412:565 */     if (original == target)
/* 413:    */     {
/* 414:567 */       boolean wasClean = (PersistentCollection.class.isInstance(target)) && (!((PersistentCollection)target).isDirty());
/* 415:    */       
/* 416:    */ 
/* 417:570 */       replaceElements(result, target, owner, copyCache, session);
/* 418:571 */       if (wasClean) {
/* 419:572 */         ((PersistentCollection)target).clearDirty();
/* 420:    */       }
/* 421:574 */       result = target;
/* 422:    */     }
/* 423:577 */     return result;
/* 424:    */   }
/* 425:    */   
/* 426:    */   public final Type getElementType(SessionFactoryImplementor factory)
/* 427:    */     throws MappingException
/* 428:    */   {
/* 429:588 */     return factory.getCollectionPersister(getRole()).getElementType();
/* 430:    */   }
/* 431:    */   
/* 432:    */   public String toString()
/* 433:    */   {
/* 434:592 */     return getClass().getName() + '(' + getRole() + ')';
/* 435:    */   }
/* 436:    */   
/* 437:    */   public String getOnCondition(String alias, SessionFactoryImplementor factory, Map enabledFilters)
/* 438:    */     throws MappingException
/* 439:    */   {
/* 440:597 */     return getAssociatedJoinable(factory).filterFragment(alias, enabledFilters);
/* 441:    */   }
/* 442:    */   
/* 443:    */   public Object getCollection(Serializable key, SessionImplementor session, Object owner)
/* 444:    */   {
/* 445:610 */     CollectionPersister persister = getPersister(session);
/* 446:611 */     PersistenceContext persistenceContext = session.getPersistenceContext();
/* 447:612 */     EntityMode entityMode = persister.getOwnerEntityPersister().getEntityMode();
/* 448:    */     
/* 449:    */ 
/* 450:615 */     PersistentCollection collection = persistenceContext.getLoadContexts().locateLoadingCollection(persister, key);
/* 451:617 */     if (collection == null)
/* 452:    */     {
/* 453:620 */       collection = persistenceContext.useUnownedCollection(new CollectionKey(persister, key, entityMode));
/* 454:622 */       if (collection == null)
/* 455:    */       {
/* 456:624 */         collection = instantiate(session, persister, key);
/* 457:625 */         collection.setOwner(owner);
/* 458:    */         
/* 459:627 */         persistenceContext.addUninitializedCollection(persister, collection, key);
/* 460:630 */         if (initializeImmediately()) {
/* 461:631 */           session.initializeCollection(collection, false);
/* 462:633 */         } else if (!persister.isLazy()) {
/* 463:634 */           persistenceContext.addNonLazyCollection(collection);
/* 464:    */         }
/* 465:637 */         if (hasHolder()) {
/* 466:638 */           session.getPersistenceContext().addCollectionHolder(collection);
/* 467:    */         }
/* 468:    */       }
/* 469:    */     }
/* 470:645 */     collection.setOwner(owner);
/* 471:    */     
/* 472:647 */     return collection.getValue();
/* 473:    */   }
/* 474:    */   
/* 475:    */   public boolean hasHolder()
/* 476:    */   {
/* 477:651 */     return false;
/* 478:    */   }
/* 479:    */   
/* 480:    */   protected boolean initializeImmediately()
/* 481:    */   {
/* 482:655 */     return false;
/* 483:    */   }
/* 484:    */   
/* 485:    */   public String getLHSPropertyName()
/* 486:    */   {
/* 487:659 */     return this.foreignKeyPropertyName;
/* 488:    */   }
/* 489:    */   
/* 490:    */   public boolean isXMLElement()
/* 491:    */   {
/* 492:663 */     return true;
/* 493:    */   }
/* 494:    */   
/* 495:    */   public Object fromXMLNode(Node xml, Mapping factory)
/* 496:    */     throws HibernateException
/* 497:    */   {
/* 498:667 */     return xml;
/* 499:    */   }
/* 500:    */   
/* 501:    */   public void setToXMLNode(Node node, Object value, SessionFactoryImplementor factory)
/* 502:    */     throws HibernateException
/* 503:    */   {
/* 504:672 */     if (!this.isEmbeddedInXML) {
/* 505:673 */       node.detach();
/* 506:    */     } else {
/* 507:676 */       replaceNode(node, (Element)value);
/* 508:    */     }
/* 509:    */   }
/* 510:    */   
/* 511:    */   public boolean isAlwaysDirtyChecked()
/* 512:    */   {
/* 513:686 */     return true;
/* 514:    */   }
/* 515:    */   
/* 516:    */   public boolean[] toColumnNullness(Object value, Mapping mapping)
/* 517:    */   {
/* 518:690 */     return ArrayHelper.EMPTY_BOOLEAN_ARRAY;
/* 519:    */   }
/* 520:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.CollectionType
 * JD-Core Version:    0.7.0.1
 */