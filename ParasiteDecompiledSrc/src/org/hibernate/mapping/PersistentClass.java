/*   1:    */ package org.hibernate.mapping;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Collections;
/*   6:    */ import java.util.HashMap;
/*   7:    */ import java.util.HashSet;
/*   8:    */ import java.util.Iterator;
/*   9:    */ import java.util.Map;
/*  10:    */ import java.util.Set;
/*  11:    */ import java.util.StringTokenizer;
/*  12:    */ import org.hibernate.EntityMode;
/*  13:    */ import org.hibernate.MappingException;
/*  14:    */ import org.hibernate.dialect.Dialect;
/*  15:    */ import org.hibernate.engine.spi.ExecuteUpdateResultCheckStyle;
/*  16:    */ import org.hibernate.engine.spi.Mapping;
/*  17:    */ import org.hibernate.internal.util.ReflectHelper;
/*  18:    */ import org.hibernate.internal.util.StringHelper;
/*  19:    */ import org.hibernate.internal.util.collections.EmptyIterator;
/*  20:    */ import org.hibernate.internal.util.collections.JoinedIterator;
/*  21:    */ import org.hibernate.internal.util.collections.SingletonIterator;
/*  22:    */ import org.hibernate.sql.Alias;
/*  23:    */ import org.hibernate.type.Type;
/*  24:    */ 
/*  25:    */ public abstract class PersistentClass
/*  26:    */   implements Serializable, Filterable, MetaAttributable
/*  27:    */ {
/*  28: 52 */   private static final Alias PK_ALIAS = new Alias(15, "PK");
/*  29:    */   public static final String NULL_DISCRIMINATOR_MAPPING = "null";
/*  30:    */   public static final String NOT_NULL_DISCRIMINATOR_MAPPING = "not null";
/*  31:    */   private String entityName;
/*  32:    */   private String className;
/*  33:    */   private String proxyInterfaceName;
/*  34:    */   private String nodeName;
/*  35:    */   private String jpaEntityName;
/*  36:    */   private String discriminatorValue;
/*  37:    */   private boolean lazy;
/*  38: 67 */   private ArrayList properties = new ArrayList();
/*  39: 68 */   private ArrayList declaredProperties = new ArrayList();
/*  40: 69 */   private final ArrayList subclasses = new ArrayList();
/*  41: 70 */   private final ArrayList subclassProperties = new ArrayList();
/*  42: 71 */   private final ArrayList subclassTables = new ArrayList();
/*  43:    */   private boolean dynamicInsert;
/*  44:    */   private boolean dynamicUpdate;
/*  45: 74 */   private int batchSize = -1;
/*  46:    */   private boolean selectBeforeUpdate;
/*  47:    */   private Map metaAttributes;
/*  48: 77 */   private ArrayList joins = new ArrayList();
/*  49: 78 */   private final ArrayList subclassJoins = new ArrayList();
/*  50: 79 */   private final Map filters = new HashMap();
/*  51: 80 */   protected final Set synchronizedTables = new HashSet();
/*  52:    */   private String loaderName;
/*  53:    */   private Boolean isAbstract;
/*  54:    */   private boolean hasSubselectLoadableCollections;
/*  55:    */   private Component identifierMapper;
/*  56:    */   private String customSQLInsert;
/*  57:    */   private boolean customInsertCallable;
/*  58:    */   private ExecuteUpdateResultCheckStyle insertCheckStyle;
/*  59:    */   private String customSQLUpdate;
/*  60:    */   private boolean customUpdateCallable;
/*  61:    */   private ExecuteUpdateResultCheckStyle updateCheckStyle;
/*  62:    */   private String customSQLDelete;
/*  63:    */   private boolean customDeleteCallable;
/*  64:    */   private ExecuteUpdateResultCheckStyle deleteCheckStyle;
/*  65:    */   private String temporaryIdTableName;
/*  66:    */   private String temporaryIdTableDDL;
/*  67:    */   private Map tuplizerImpls;
/*  68:    */   protected int optimisticLockMode;
/*  69:    */   private MappedSuperclass superMappedSuperclass;
/*  70:    */   private Component declaredIdentifierMapper;
/*  71:    */   
/*  72:    */   public String getClassName()
/*  73:    */   {
/*  74:107 */     return this.className;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void setClassName(String className)
/*  78:    */   {
/*  79:111 */     this.className = (className == null ? null : className.intern());
/*  80:    */   }
/*  81:    */   
/*  82:    */   public String getProxyInterfaceName()
/*  83:    */   {
/*  84:115 */     return this.proxyInterfaceName;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void setProxyInterfaceName(String proxyInterfaceName)
/*  88:    */   {
/*  89:119 */     this.proxyInterfaceName = proxyInterfaceName;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public Class getMappedClass()
/*  93:    */     throws MappingException
/*  94:    */   {
/*  95:123 */     if (this.className == null) {
/*  96:123 */       return null;
/*  97:    */     }
/*  98:    */     try
/*  99:    */     {
/* 100:125 */       return ReflectHelper.classForName(this.className);
/* 101:    */     }
/* 102:    */     catch (ClassNotFoundException cnfe)
/* 103:    */     {
/* 104:128 */       throw new MappingException("entity class not found: " + this.className, cnfe);
/* 105:    */     }
/* 106:    */   }
/* 107:    */   
/* 108:    */   public Class getProxyInterface()
/* 109:    */   {
/* 110:133 */     if (this.proxyInterfaceName == null) {
/* 111:133 */       return null;
/* 112:    */     }
/* 113:    */     try
/* 114:    */     {
/* 115:135 */       return ReflectHelper.classForName(this.proxyInterfaceName);
/* 116:    */     }
/* 117:    */     catch (ClassNotFoundException cnfe)
/* 118:    */     {
/* 119:138 */       throw new MappingException("proxy class not found: " + this.proxyInterfaceName, cnfe);
/* 120:    */     }
/* 121:    */   }
/* 122:    */   
/* 123:    */   public boolean useDynamicInsert()
/* 124:    */   {
/* 125:142 */     return this.dynamicInsert;
/* 126:    */   }
/* 127:    */   
/* 128:    */   abstract int nextSubclassId();
/* 129:    */   
/* 130:    */   public abstract int getSubclassId();
/* 131:    */   
/* 132:    */   public boolean useDynamicUpdate()
/* 133:    */   {
/* 134:149 */     return this.dynamicUpdate;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setDynamicInsert(boolean dynamicInsert)
/* 138:    */   {
/* 139:153 */     this.dynamicInsert = dynamicInsert;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setDynamicUpdate(boolean dynamicUpdate)
/* 143:    */   {
/* 144:157 */     this.dynamicUpdate = dynamicUpdate;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public String getDiscriminatorValue()
/* 148:    */   {
/* 149:162 */     return this.discriminatorValue;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void addSubclass(Subclass subclass)
/* 153:    */     throws MappingException
/* 154:    */   {
/* 155:167 */     PersistentClass superclass = getSuperclass();
/* 156:168 */     while (superclass != null)
/* 157:    */     {
/* 158:169 */       if (subclass.getEntityName().equals(superclass.getEntityName())) {
/* 159:170 */         throw new MappingException("Circular inheritance mapping detected: " + subclass.getEntityName() + " will have it self as superclass when extending " + getEntityName());
/* 160:    */       }
/* 161:177 */       superclass = superclass.getSuperclass();
/* 162:    */     }
/* 163:179 */     this.subclasses.add(subclass);
/* 164:    */   }
/* 165:    */   
/* 166:    */   public boolean hasSubclasses()
/* 167:    */   {
/* 168:183 */     return this.subclasses.size() > 0;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public int getSubclassSpan()
/* 172:    */   {
/* 173:187 */     int n = this.subclasses.size();
/* 174:188 */     Iterator iter = this.subclasses.iterator();
/* 175:189 */     while (iter.hasNext()) {
/* 176:190 */       n += ((Subclass)iter.next()).getSubclassSpan();
/* 177:    */     }
/* 178:192 */     return n;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public Iterator getSubclassIterator()
/* 182:    */   {
/* 183:199 */     Iterator[] iters = new Iterator[this.subclasses.size() + 1];
/* 184:200 */     Iterator iter = this.subclasses.iterator();
/* 185:201 */     int i = 0;
/* 186:202 */     while (iter.hasNext()) {
/* 187:203 */       iters[(i++)] = ((Subclass)iter.next()).getSubclassIterator();
/* 188:    */     }
/* 189:205 */     iters[i] = this.subclasses.iterator();
/* 190:206 */     return new JoinedIterator(iters);
/* 191:    */   }
/* 192:    */   
/* 193:    */   public Iterator getSubclassClosureIterator()
/* 194:    */   {
/* 195:210 */     ArrayList iters = new ArrayList();
/* 196:211 */     iters.add(new SingletonIterator(this));
/* 197:212 */     Iterator iter = getSubclassIterator();
/* 198:213 */     while (iter.hasNext())
/* 199:    */     {
/* 200:214 */       PersistentClass clazz = (PersistentClass)iter.next();
/* 201:215 */       iters.add(clazz.getSubclassClosureIterator());
/* 202:    */     }
/* 203:217 */     return new JoinedIterator(iters);
/* 204:    */   }
/* 205:    */   
/* 206:    */   public Table getIdentityTable()
/* 207:    */   {
/* 208:221 */     return getRootTable();
/* 209:    */   }
/* 210:    */   
/* 211:    */   public Iterator getDirectSubclasses()
/* 212:    */   {
/* 213:225 */     return this.subclasses.iterator();
/* 214:    */   }
/* 215:    */   
/* 216:    */   public void addProperty(Property p)
/* 217:    */   {
/* 218:229 */     this.properties.add(p);
/* 219:230 */     this.declaredProperties.add(p);
/* 220:231 */     p.setPersistentClass(this);
/* 221:    */   }
/* 222:    */   
/* 223:    */   public abstract Table getTable();
/* 224:    */   
/* 225:    */   public String getEntityName()
/* 226:    */   {
/* 227:237 */     return this.entityName;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public abstract boolean isMutable();
/* 231:    */   
/* 232:    */   public abstract boolean hasIdentifierProperty();
/* 233:    */   
/* 234:    */   public abstract Property getIdentifierProperty();
/* 235:    */   
/* 236:    */   public abstract Property getDeclaredIdentifierProperty();
/* 237:    */   
/* 238:    */   public abstract KeyValue getIdentifier();
/* 239:    */   
/* 240:    */   public abstract Property getVersion();
/* 241:    */   
/* 242:    */   public abstract Property getDeclaredVersion();
/* 243:    */   
/* 244:    */   public abstract Value getDiscriminator();
/* 245:    */   
/* 246:    */   public abstract boolean isInherited();
/* 247:    */   
/* 248:    */   public abstract boolean isPolymorphic();
/* 249:    */   
/* 250:    */   public abstract boolean isVersioned();
/* 251:    */   
/* 252:    */   public abstract String getCacheConcurrencyStrategy();
/* 253:    */   
/* 254:    */   public abstract PersistentClass getSuperclass();
/* 255:    */   
/* 256:    */   public abstract boolean isExplicitPolymorphism();
/* 257:    */   
/* 258:    */   public abstract boolean isDiscriminatorInsertable();
/* 259:    */   
/* 260:    */   public abstract Iterator getPropertyClosureIterator();
/* 261:    */   
/* 262:    */   public abstract Iterator getTableClosureIterator();
/* 263:    */   
/* 264:    */   public abstract Iterator getKeyClosureIterator();
/* 265:    */   
/* 266:    */   protected void addSubclassProperty(Property prop)
/* 267:    */   {
/* 268:261 */     this.subclassProperties.add(prop);
/* 269:    */   }
/* 270:    */   
/* 271:    */   protected void addSubclassJoin(Join join)
/* 272:    */   {
/* 273:264 */     this.subclassJoins.add(join);
/* 274:    */   }
/* 275:    */   
/* 276:    */   protected void addSubclassTable(Table subclassTable)
/* 277:    */   {
/* 278:267 */     this.subclassTables.add(subclassTable);
/* 279:    */   }
/* 280:    */   
/* 281:    */   public Iterator getSubclassPropertyClosureIterator()
/* 282:    */   {
/* 283:270 */     ArrayList iters = new ArrayList();
/* 284:271 */     iters.add(getPropertyClosureIterator());
/* 285:272 */     iters.add(this.subclassProperties.iterator());
/* 286:273 */     for (int i = 0; i < this.subclassJoins.size(); i++)
/* 287:    */     {
/* 288:274 */       Join join = (Join)this.subclassJoins.get(i);
/* 289:275 */       iters.add(join.getPropertyIterator());
/* 290:    */     }
/* 291:277 */     return new JoinedIterator(iters);
/* 292:    */   }
/* 293:    */   
/* 294:    */   public Iterator getSubclassJoinClosureIterator()
/* 295:    */   {
/* 296:280 */     return new JoinedIterator(getJoinClosureIterator(), this.subclassJoins.iterator());
/* 297:    */   }
/* 298:    */   
/* 299:    */   public Iterator getSubclassTableClosureIterator()
/* 300:    */   {
/* 301:283 */     return new JoinedIterator(getTableClosureIterator(), this.subclassTables.iterator());
/* 302:    */   }
/* 303:    */   
/* 304:    */   public boolean isClassOrSuperclassJoin(Join join)
/* 305:    */   {
/* 306:287 */     return this.joins.contains(join);
/* 307:    */   }
/* 308:    */   
/* 309:    */   public boolean isClassOrSuperclassTable(Table closureTable)
/* 310:    */   {
/* 311:291 */     return getTable() == closureTable;
/* 312:    */   }
/* 313:    */   
/* 314:    */   public boolean isLazy()
/* 315:    */   {
/* 316:295 */     return this.lazy;
/* 317:    */   }
/* 318:    */   
/* 319:    */   public void setLazy(boolean lazy)
/* 320:    */   {
/* 321:299 */     this.lazy = lazy;
/* 322:    */   }
/* 323:    */   
/* 324:    */   public abstract boolean hasEmbeddedIdentifier();
/* 325:    */   
/* 326:    */   public abstract Class getEntityPersisterClass();
/* 327:    */   
/* 328:    */   public abstract void setEntityPersisterClass(Class paramClass);
/* 329:    */   
/* 330:    */   public abstract Table getRootTable();
/* 331:    */   
/* 332:    */   public abstract RootClass getRootClass();
/* 333:    */   
/* 334:    */   public abstract KeyValue getKey();
/* 335:    */   
/* 336:    */   public void setDiscriminatorValue(String discriminatorValue)
/* 337:    */   {
/* 338:310 */     this.discriminatorValue = discriminatorValue;
/* 339:    */   }
/* 340:    */   
/* 341:    */   public void setEntityName(String entityName)
/* 342:    */   {
/* 343:314 */     this.entityName = (entityName == null ? null : entityName.intern());
/* 344:    */   }
/* 345:    */   
/* 346:    */   public void createPrimaryKey()
/* 347:    */   {
/* 348:319 */     PrimaryKey pk = new PrimaryKey();
/* 349:320 */     Table table = getTable();
/* 350:321 */     pk.setTable(table);
/* 351:322 */     pk.setName(PK_ALIAS.toAliasString(table.getName()));
/* 352:323 */     table.setPrimaryKey(pk);
/* 353:    */     
/* 354:325 */     pk.addColumns(getKey().getColumnIterator());
/* 355:    */   }
/* 356:    */   
/* 357:    */   public abstract String getWhere();
/* 358:    */   
/* 359:    */   public int getBatchSize()
/* 360:    */   {
/* 361:331 */     return this.batchSize;
/* 362:    */   }
/* 363:    */   
/* 364:    */   public void setBatchSize(int batchSize)
/* 365:    */   {
/* 366:335 */     this.batchSize = batchSize;
/* 367:    */   }
/* 368:    */   
/* 369:    */   public boolean hasSelectBeforeUpdate()
/* 370:    */   {
/* 371:339 */     return this.selectBeforeUpdate;
/* 372:    */   }
/* 373:    */   
/* 374:    */   public void setSelectBeforeUpdate(boolean selectBeforeUpdate)
/* 375:    */   {
/* 376:343 */     this.selectBeforeUpdate = selectBeforeUpdate;
/* 377:    */   }
/* 378:    */   
/* 379:    */   public Iterator getReferenceablePropertyIterator()
/* 380:    */   {
/* 381:353 */     return getPropertyClosureIterator();
/* 382:    */   }
/* 383:    */   
/* 384:    */   public Property getReferencedProperty(String propertyPath)
/* 385:    */     throws MappingException
/* 386:    */   {
/* 387:    */     try
/* 388:    */     {
/* 389:368 */       return getRecursiveProperty(propertyPath, getReferenceablePropertyIterator());
/* 390:    */     }
/* 391:    */     catch (MappingException e)
/* 392:    */     {
/* 393:371 */       throw new MappingException("property-ref [" + propertyPath + "] not found on entity [" + getEntityName() + "]", e);
/* 394:    */     }
/* 395:    */   }
/* 396:    */   
/* 397:    */   public Property getRecursiveProperty(String propertyPath)
/* 398:    */     throws MappingException
/* 399:    */   {
/* 400:    */     try
/* 401:    */     {
/* 402:379 */       return getRecursiveProperty(propertyPath, getPropertyIterator());
/* 403:    */     }
/* 404:    */     catch (MappingException e)
/* 405:    */     {
/* 406:382 */       throw new MappingException("property [" + propertyPath + "] not found on entity [" + getEntityName() + "]", e);
/* 407:    */     }
/* 408:    */   }
/* 409:    */   
/* 410:    */   private Property getRecursiveProperty(String propertyPath, Iterator iter)
/* 411:    */     throws MappingException
/* 412:    */   {
/* 413:389 */     Property property = null;
/* 414:390 */     StringTokenizer st = new StringTokenizer(propertyPath, ".", false);
/* 415:    */     try
/* 416:    */     {
/* 417:392 */       while (st.hasMoreElements())
/* 418:    */       {
/* 419:393 */         String element = (String)st.nextElement();
/* 420:394 */         if (property == null)
/* 421:    */         {
/* 422:395 */           Property identifierProperty = getIdentifierProperty();
/* 423:396 */           if ((identifierProperty != null) && (identifierProperty.getName().equals(element))) {
/* 424:400 */             property = identifierProperty;
/* 425:402 */           } else if ((identifierProperty == null) && (getIdentifierMapper() != null)) {
/* 426:    */             try
/* 427:    */             {
/* 428:405 */               identifierProperty = getProperty(element, getIdentifierMapper().getPropertyIterator());
/* 429:406 */               if (identifierProperty != null) {
/* 430:409 */                 property = identifierProperty;
/* 431:    */               }
/* 432:    */             }
/* 433:    */             catch (MappingException ignore) {}
/* 434:    */           }
/* 435:417 */           if (property == null) {
/* 436:418 */             property = getProperty(element, iter);
/* 437:    */           }
/* 438:    */         }
/* 439:    */         else
/* 440:    */         {
/* 441:423 */           property = ((Component)property.getValue()).getProperty(element);
/* 442:    */         }
/* 443:    */       }
/* 444:    */     }
/* 445:    */     catch (MappingException e)
/* 446:    */     {
/* 447:428 */       throw new MappingException("property [" + propertyPath + "] not found on entity [" + getEntityName() + "]");
/* 448:    */     }
/* 449:431 */     return property;
/* 450:    */   }
/* 451:    */   
/* 452:    */   private Property getProperty(String propertyName, Iterator iterator)
/* 453:    */     throws MappingException
/* 454:    */   {
/* 455:435 */     while (iterator.hasNext())
/* 456:    */     {
/* 457:436 */       Property prop = (Property)iterator.next();
/* 458:437 */       if (prop.getName().equals(StringHelper.root(propertyName))) {
/* 459:438 */         return prop;
/* 460:    */       }
/* 461:    */     }
/* 462:441 */     throw new MappingException("property [" + propertyName + "] not found on entity [" + getEntityName() + "]");
/* 463:    */   }
/* 464:    */   
/* 465:    */   public Property getProperty(String propertyName)
/* 466:    */     throws MappingException
/* 467:    */   {
/* 468:445 */     Iterator iter = getPropertyClosureIterator();
/* 469:446 */     Property identifierProperty = getIdentifierProperty();
/* 470:447 */     if ((identifierProperty != null) && (identifierProperty.getName().equals(StringHelper.root(propertyName)))) {
/* 471:450 */       return identifierProperty;
/* 472:    */     }
/* 473:453 */     return getProperty(propertyName, iter);
/* 474:    */   }
/* 475:    */   
/* 476:    */   public abstract int getOptimisticLockMode();
/* 477:    */   
/* 478:    */   public void setOptimisticLockMode(int optimisticLockMode)
/* 479:    */   {
/* 480:460 */     this.optimisticLockMode = optimisticLockMode;
/* 481:    */   }
/* 482:    */   
/* 483:    */   public void validate(Mapping mapping)
/* 484:    */     throws MappingException
/* 485:    */   {
/* 486:464 */     Iterator iter = getPropertyIterator();
/* 487:465 */     while (iter.hasNext())
/* 488:    */     {
/* 489:466 */       Property prop = (Property)iter.next();
/* 490:467 */       if (!prop.isValid(mapping)) {
/* 491:468 */         throw new MappingException("property mapping has wrong number of columns: " + StringHelper.qualify(getEntityName(), prop.getName()) + " type: " + prop.getType().getName());
/* 492:    */       }
/* 493:    */     }
/* 494:476 */     checkPropertyDuplication();
/* 495:477 */     checkColumnDuplication();
/* 496:    */   }
/* 497:    */   
/* 498:    */   private void checkPropertyDuplication()
/* 499:    */     throws MappingException
/* 500:    */   {
/* 501:481 */     HashSet names = new HashSet();
/* 502:482 */     Iterator iter = getPropertyIterator();
/* 503:483 */     while (iter.hasNext())
/* 504:    */     {
/* 505:484 */       Property prop = (Property)iter.next();
/* 506:485 */       if (!names.add(prop.getName())) {
/* 507:486 */         throw new MappingException("Duplicate property mapping of " + prop.getName() + " found in " + getEntityName());
/* 508:    */       }
/* 509:    */     }
/* 510:    */   }
/* 511:    */   
/* 512:    */   public boolean isDiscriminatorValueNotNull()
/* 513:    */   {
/* 514:492 */     return "not null".equals(getDiscriminatorValue());
/* 515:    */   }
/* 516:    */   
/* 517:    */   public boolean isDiscriminatorValueNull()
/* 518:    */   {
/* 519:495 */     return "null".equals(getDiscriminatorValue());
/* 520:    */   }
/* 521:    */   
/* 522:    */   public Map getMetaAttributes()
/* 523:    */   {
/* 524:499 */     return this.metaAttributes;
/* 525:    */   }
/* 526:    */   
/* 527:    */   public void setMetaAttributes(Map metas)
/* 528:    */   {
/* 529:503 */     this.metaAttributes = metas;
/* 530:    */   }
/* 531:    */   
/* 532:    */   public MetaAttribute getMetaAttribute(String name)
/* 533:    */   {
/* 534:507 */     return this.metaAttributes == null ? null : (MetaAttribute)this.metaAttributes.get(name);
/* 535:    */   }
/* 536:    */   
/* 537:    */   public String toString()
/* 538:    */   {
/* 539:512 */     return getClass().getName() + '(' + getEntityName() + ')';
/* 540:    */   }
/* 541:    */   
/* 542:    */   public Iterator getJoinIterator()
/* 543:    */   {
/* 544:516 */     return this.joins.iterator();
/* 545:    */   }
/* 546:    */   
/* 547:    */   public Iterator getJoinClosureIterator()
/* 548:    */   {
/* 549:520 */     return this.joins.iterator();
/* 550:    */   }
/* 551:    */   
/* 552:    */   public void addJoin(Join join)
/* 553:    */   {
/* 554:524 */     this.joins.add(join);
/* 555:525 */     join.setPersistentClass(this);
/* 556:    */   }
/* 557:    */   
/* 558:    */   public int getJoinClosureSpan()
/* 559:    */   {
/* 560:529 */     return this.joins.size();
/* 561:    */   }
/* 562:    */   
/* 563:    */   public int getPropertyClosureSpan()
/* 564:    */   {
/* 565:533 */     int span = this.properties.size();
/* 566:534 */     for (int i = 0; i < this.joins.size(); i++)
/* 567:    */     {
/* 568:535 */       Join join = (Join)this.joins.get(i);
/* 569:536 */       span += join.getPropertySpan();
/* 570:    */     }
/* 571:538 */     return span;
/* 572:    */   }
/* 573:    */   
/* 574:    */   public int getJoinNumber(Property prop)
/* 575:    */   {
/* 576:542 */     int result = 1;
/* 577:543 */     Iterator iter = getSubclassJoinClosureIterator();
/* 578:544 */     while (iter.hasNext())
/* 579:    */     {
/* 580:545 */       Join join = (Join)iter.next();
/* 581:546 */       if (join.containsProperty(prop)) {
/* 582:546 */         return result;
/* 583:    */       }
/* 584:547 */       result++;
/* 585:    */     }
/* 586:549 */     return 0;
/* 587:    */   }
/* 588:    */   
/* 589:    */   public Iterator getPropertyIterator()
/* 590:    */   {
/* 591:563 */     ArrayList iterators = new ArrayList();
/* 592:564 */     iterators.add(this.properties.iterator());
/* 593:565 */     for (int i = 0; i < this.joins.size(); i++)
/* 594:    */     {
/* 595:566 */       Join join = (Join)this.joins.get(i);
/* 596:567 */       iterators.add(join.getPropertyIterator());
/* 597:    */     }
/* 598:569 */     return new JoinedIterator(iterators);
/* 599:    */   }
/* 600:    */   
/* 601:    */   public Iterator getUnjoinedPropertyIterator()
/* 602:    */   {
/* 603:580 */     return this.properties.iterator();
/* 604:    */   }
/* 605:    */   
/* 606:    */   public void setCustomSQLInsert(String customSQLInsert, boolean callable, ExecuteUpdateResultCheckStyle checkStyle)
/* 607:    */   {
/* 608:584 */     this.customSQLInsert = customSQLInsert;
/* 609:585 */     this.customInsertCallable = callable;
/* 610:586 */     this.insertCheckStyle = checkStyle;
/* 611:    */   }
/* 612:    */   
/* 613:    */   public String getCustomSQLInsert()
/* 614:    */   {
/* 615:590 */     return this.customSQLInsert;
/* 616:    */   }
/* 617:    */   
/* 618:    */   public boolean isCustomInsertCallable()
/* 619:    */   {
/* 620:594 */     return this.customInsertCallable;
/* 621:    */   }
/* 622:    */   
/* 623:    */   public ExecuteUpdateResultCheckStyle getCustomSQLInsertCheckStyle()
/* 624:    */   {
/* 625:598 */     return this.insertCheckStyle;
/* 626:    */   }
/* 627:    */   
/* 628:    */   public void setCustomSQLUpdate(String customSQLUpdate, boolean callable, ExecuteUpdateResultCheckStyle checkStyle)
/* 629:    */   {
/* 630:602 */     this.customSQLUpdate = customSQLUpdate;
/* 631:603 */     this.customUpdateCallable = callable;
/* 632:604 */     this.updateCheckStyle = checkStyle;
/* 633:    */   }
/* 634:    */   
/* 635:    */   public String getCustomSQLUpdate()
/* 636:    */   {
/* 637:608 */     return this.customSQLUpdate;
/* 638:    */   }
/* 639:    */   
/* 640:    */   public boolean isCustomUpdateCallable()
/* 641:    */   {
/* 642:612 */     return this.customUpdateCallable;
/* 643:    */   }
/* 644:    */   
/* 645:    */   public ExecuteUpdateResultCheckStyle getCustomSQLUpdateCheckStyle()
/* 646:    */   {
/* 647:616 */     return this.updateCheckStyle;
/* 648:    */   }
/* 649:    */   
/* 650:    */   public void setCustomSQLDelete(String customSQLDelete, boolean callable, ExecuteUpdateResultCheckStyle checkStyle)
/* 651:    */   {
/* 652:620 */     this.customSQLDelete = customSQLDelete;
/* 653:621 */     this.customDeleteCallable = callable;
/* 654:622 */     this.deleteCheckStyle = checkStyle;
/* 655:    */   }
/* 656:    */   
/* 657:    */   public String getCustomSQLDelete()
/* 658:    */   {
/* 659:626 */     return this.customSQLDelete;
/* 660:    */   }
/* 661:    */   
/* 662:    */   public boolean isCustomDeleteCallable()
/* 663:    */   {
/* 664:630 */     return this.customDeleteCallable;
/* 665:    */   }
/* 666:    */   
/* 667:    */   public ExecuteUpdateResultCheckStyle getCustomSQLDeleteCheckStyle()
/* 668:    */   {
/* 669:634 */     return this.deleteCheckStyle;
/* 670:    */   }
/* 671:    */   
/* 672:    */   public void addFilter(String name, String condition)
/* 673:    */   {
/* 674:638 */     this.filters.put(name, condition);
/* 675:    */   }
/* 676:    */   
/* 677:    */   public Map getFilterMap()
/* 678:    */   {
/* 679:642 */     return this.filters;
/* 680:    */   }
/* 681:    */   
/* 682:    */   public boolean isForceDiscriminator()
/* 683:    */   {
/* 684:646 */     return false;
/* 685:    */   }
/* 686:    */   
/* 687:    */   public abstract boolean isJoinedSubclass();
/* 688:    */   
/* 689:    */   public String getLoaderName()
/* 690:    */   {
/* 691:652 */     return this.loaderName;
/* 692:    */   }
/* 693:    */   
/* 694:    */   public void setLoaderName(String loaderName)
/* 695:    */   {
/* 696:656 */     this.loaderName = (loaderName == null ? null : loaderName.intern());
/* 697:    */   }
/* 698:    */   
/* 699:    */   public abstract Set getSynchronizedTables();
/* 700:    */   
/* 701:    */   public void addSynchronizedTable(String table)
/* 702:    */   {
/* 703:662 */     this.synchronizedTables.add(table);
/* 704:    */   }
/* 705:    */   
/* 706:    */   public Boolean isAbstract()
/* 707:    */   {
/* 708:666 */     return this.isAbstract;
/* 709:    */   }
/* 710:    */   
/* 711:    */   public void setAbstract(Boolean isAbstract)
/* 712:    */   {
/* 713:670 */     this.isAbstract = isAbstract;
/* 714:    */   }
/* 715:    */   
/* 716:    */   protected void checkColumnDuplication(Set distinctColumns, Iterator columns)
/* 717:    */     throws MappingException
/* 718:    */   {
/* 719:675 */     while (columns.hasNext())
/* 720:    */     {
/* 721:676 */       Selectable columnOrFormula = (Selectable)columns.next();
/* 722:677 */       if (!columnOrFormula.isFormula())
/* 723:    */       {
/* 724:678 */         Column col = (Column)columnOrFormula;
/* 725:679 */         if (!distinctColumns.add(col.getName())) {
/* 726:680 */           throw new MappingException("Repeated column in mapping for entity: " + getEntityName() + " column: " + col.getName() + " (should be mapped with insert=\"false\" update=\"false\")");
/* 727:    */         }
/* 728:    */       }
/* 729:    */     }
/* 730:    */   }
/* 731:    */   
/* 732:    */   protected void checkPropertyColumnDuplication(Set distinctColumns, Iterator properties)
/* 733:    */     throws MappingException
/* 734:    */   {
/* 735:694 */     while (properties.hasNext())
/* 736:    */     {
/* 737:695 */       Property prop = (Property)properties.next();
/* 738:696 */       if ((prop.getValue() instanceof Component))
/* 739:    */       {
/* 740:697 */         Component component = (Component)prop.getValue();
/* 741:698 */         checkPropertyColumnDuplication(distinctColumns, component.getPropertyIterator());
/* 742:    */       }
/* 743:701 */       else if ((prop.isUpdateable()) || (prop.isInsertable()))
/* 744:    */       {
/* 745:702 */         checkColumnDuplication(distinctColumns, prop.getColumnIterator());
/* 746:    */       }
/* 747:    */     }
/* 748:    */   }
/* 749:    */   
/* 750:    */   protected Iterator getNonDuplicatedPropertyIterator()
/* 751:    */   {
/* 752:709 */     return getUnjoinedPropertyIterator();
/* 753:    */   }
/* 754:    */   
/* 755:    */   protected Iterator getDiscriminatorColumnIterator()
/* 756:    */   {
/* 757:713 */     return EmptyIterator.INSTANCE;
/* 758:    */   }
/* 759:    */   
/* 760:    */   protected void checkColumnDuplication()
/* 761:    */   {
/* 762:717 */     HashSet cols = new HashSet();
/* 763:718 */     if (getIdentifierMapper() == null) {
/* 764:721 */       checkColumnDuplication(cols, getKey().getColumnIterator());
/* 765:    */     }
/* 766:723 */     checkColumnDuplication(cols, getDiscriminatorColumnIterator());
/* 767:724 */     checkPropertyColumnDuplication(cols, getNonDuplicatedPropertyIterator());
/* 768:725 */     Iterator iter = getJoinIterator();
/* 769:726 */     while (iter.hasNext())
/* 770:    */     {
/* 771:727 */       cols.clear();
/* 772:728 */       Join join = (Join)iter.next();
/* 773:729 */       checkColumnDuplication(cols, join.getKey().getColumnIterator());
/* 774:730 */       checkPropertyColumnDuplication(cols, join.getPropertyIterator());
/* 775:    */     }
/* 776:    */   }
/* 777:    */   
/* 778:    */   public abstract Object accept(PersistentClassVisitor paramPersistentClassVisitor);
/* 779:    */   
/* 780:    */   public String getNodeName()
/* 781:    */   {
/* 782:737 */     return this.nodeName;
/* 783:    */   }
/* 784:    */   
/* 785:    */   public void setNodeName(String nodeName)
/* 786:    */   {
/* 787:741 */     this.nodeName = nodeName;
/* 788:    */   }
/* 789:    */   
/* 790:    */   public String getJpaEntityName()
/* 791:    */   {
/* 792:745 */     return this.jpaEntityName;
/* 793:    */   }
/* 794:    */   
/* 795:    */   public void setJpaEntityName(String jpaEntityName)
/* 796:    */   {
/* 797:749 */     this.jpaEntityName = jpaEntityName;
/* 798:    */   }
/* 799:    */   
/* 800:    */   public boolean hasPojoRepresentation()
/* 801:    */   {
/* 802:753 */     return getClassName() != null;
/* 803:    */   }
/* 804:    */   
/* 805:    */   public boolean hasDom4jRepresentation()
/* 806:    */   {
/* 807:757 */     return getNodeName() != null;
/* 808:    */   }
/* 809:    */   
/* 810:    */   public boolean hasSubselectLoadableCollections()
/* 811:    */   {
/* 812:761 */     return this.hasSubselectLoadableCollections;
/* 813:    */   }
/* 814:    */   
/* 815:    */   public void setSubselectLoadableCollections(boolean hasSubselectCollections)
/* 816:    */   {
/* 817:765 */     this.hasSubselectLoadableCollections = hasSubselectCollections;
/* 818:    */   }
/* 819:    */   
/* 820:    */   public void prepareTemporaryTables(Mapping mapping, Dialect dialect)
/* 821:    */   {
/* 822:769 */     if (dialect.supportsTemporaryTables())
/* 823:    */     {
/* 824:770 */       this.temporaryIdTableName = dialect.generateTemporaryTableName(getTable().getName());
/* 825:771 */       Table table = new Table();
/* 826:772 */       table.setName(this.temporaryIdTableName);
/* 827:773 */       Iterator itr = getTable().getPrimaryKey().getColumnIterator();
/* 828:774 */       while (itr.hasNext())
/* 829:    */       {
/* 830:775 */         Column column = (Column)itr.next();
/* 831:776 */         table.addColumn((Column)column.clone());
/* 832:    */       }
/* 833:778 */       this.temporaryIdTableDDL = table.sqlTemporaryTableCreateString(dialect, mapping);
/* 834:    */     }
/* 835:    */   }
/* 836:    */   
/* 837:    */   public String getTemporaryIdTableName()
/* 838:    */   {
/* 839:783 */     return this.temporaryIdTableName;
/* 840:    */   }
/* 841:    */   
/* 842:    */   public String getTemporaryIdTableDDL()
/* 843:    */   {
/* 844:787 */     return this.temporaryIdTableDDL;
/* 845:    */   }
/* 846:    */   
/* 847:    */   public Component getIdentifierMapper()
/* 848:    */   {
/* 849:791 */     return this.identifierMapper;
/* 850:    */   }
/* 851:    */   
/* 852:    */   public Component getDeclaredIdentifierMapper()
/* 853:    */   {
/* 854:795 */     return this.declaredIdentifierMapper;
/* 855:    */   }
/* 856:    */   
/* 857:    */   public void setDeclaredIdentifierMapper(Component declaredIdentifierMapper)
/* 858:    */   {
/* 859:799 */     this.declaredIdentifierMapper = declaredIdentifierMapper;
/* 860:    */   }
/* 861:    */   
/* 862:    */   public boolean hasIdentifierMapper()
/* 863:    */   {
/* 864:803 */     return this.identifierMapper != null;
/* 865:    */   }
/* 866:    */   
/* 867:    */   public void setIdentifierMapper(Component handle)
/* 868:    */   {
/* 869:807 */     this.identifierMapper = handle;
/* 870:    */   }
/* 871:    */   
/* 872:    */   public void addTuplizer(EntityMode entityMode, String implClassName)
/* 873:    */   {
/* 874:811 */     if (this.tuplizerImpls == null) {
/* 875:812 */       this.tuplizerImpls = new HashMap();
/* 876:    */     }
/* 877:814 */     this.tuplizerImpls.put(entityMode, implClassName);
/* 878:    */   }
/* 879:    */   
/* 880:    */   public String getTuplizerImplClassName(EntityMode mode)
/* 881:    */   {
/* 882:818 */     if (this.tuplizerImpls == null) {
/* 883:818 */       return null;
/* 884:    */     }
/* 885:819 */     return (String)this.tuplizerImpls.get(mode);
/* 886:    */   }
/* 887:    */   
/* 888:    */   public Map getTuplizerMap()
/* 889:    */   {
/* 890:823 */     if (this.tuplizerImpls == null) {
/* 891:824 */       return null;
/* 892:    */     }
/* 893:826 */     return Collections.unmodifiableMap(this.tuplizerImpls);
/* 894:    */   }
/* 895:    */   
/* 896:    */   public boolean hasNaturalId()
/* 897:    */   {
/* 898:830 */     Iterator props = getRootClass().getPropertyIterator();
/* 899:831 */     while (props.hasNext()) {
/* 900:832 */       if (((Property)props.next()).isNaturalIdentifier()) {
/* 901:833 */         return true;
/* 902:    */       }
/* 903:    */     }
/* 904:836 */     return false;
/* 905:    */   }
/* 906:    */   
/* 907:    */   public abstract boolean isLazyPropertiesCacheable();
/* 908:    */   
/* 909:    */   public Iterator getDeclaredPropertyIterator()
/* 910:    */   {
/* 911:843 */     ArrayList iterators = new ArrayList();
/* 912:844 */     iterators.add(this.declaredProperties.iterator());
/* 913:845 */     for (int i = 0; i < this.joins.size(); i++)
/* 914:    */     {
/* 915:846 */       Join join = (Join)this.joins.get(i);
/* 916:847 */       iterators.add(join.getDeclaredPropertyIterator());
/* 917:    */     }
/* 918:849 */     return new JoinedIterator(iterators);
/* 919:    */   }
/* 920:    */   
/* 921:    */   public void addMappedsuperclassProperty(Property p)
/* 922:    */   {
/* 923:853 */     this.properties.add(p);
/* 924:854 */     p.setPersistentClass(this);
/* 925:    */   }
/* 926:    */   
/* 927:    */   public MappedSuperclass getSuperMappedSuperclass()
/* 928:    */   {
/* 929:858 */     return this.superMappedSuperclass;
/* 930:    */   }
/* 931:    */   
/* 932:    */   public void setSuperMappedSuperclass(MappedSuperclass superMappedSuperclass)
/* 933:    */   {
/* 934:862 */     this.superMappedSuperclass = superMappedSuperclass;
/* 935:    */   }
/* 936:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.mapping.PersistentClass
 * JD-Core Version:    0.7.0.1
 */