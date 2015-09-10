/*   1:    */ package org.hibernate.hql.internal.classic;
/*   2:    */ 
/*   3:    */ import java.util.LinkedList;
/*   4:    */ import java.util.Map;
/*   5:    */ import org.hibernate.MappingException;
/*   6:    */ import org.hibernate.QueryException;
/*   7:    */ import org.hibernate.engine.internal.JoinSequence;
/*   8:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*   9:    */ import org.hibernate.hql.internal.CollectionSubqueryFactory;
/*  10:    */ import org.hibernate.persister.collection.CollectionPropertyMapping;
/*  11:    */ import org.hibernate.persister.collection.QueryableCollection;
/*  12:    */ import org.hibernate.persister.entity.PropertyMapping;
/*  13:    */ import org.hibernate.persister.entity.Queryable;
/*  14:    */ import org.hibernate.sql.JoinFragment;
/*  15:    */ import org.hibernate.sql.JoinType;
/*  16:    */ import org.hibernate.type.AssociationType;
/*  17:    */ import org.hibernate.type.CollectionType;
/*  18:    */ import org.hibernate.type.EntityType;
/*  19:    */ import org.hibernate.type.Type;
/*  20:    */ import org.hibernate.type.TypeFactory;
/*  21:    */ import org.hibernate.type.TypeResolver;
/*  22:    */ 
/*  23:    */ public class PathExpressionParser
/*  24:    */   implements Parser
/*  25:    */ {
/*  26:    */   private int dotcount;
/*  27:    */   private String currentName;
/*  28:    */   private String currentProperty;
/*  29:    */   private String oneToOneOwnerName;
/*  30:    */   private AssociationType ownerAssociationType;
/*  31:    */   private String[] columns;
/*  32:    */   private String collectionName;
/*  33:    */   private String collectionOwnerName;
/*  34:    */   private String collectionRole;
/*  35:    */   private final StringBuffer componentPath;
/*  36:    */   private Type type;
/*  37:    */   private final StringBuffer path;
/*  38:    */   private boolean ignoreInitialJoin;
/*  39:    */   private boolean continuation;
/*  40:    */   private JoinType joinType;
/*  41:    */   private boolean useThetaStyleJoin;
/*  42:    */   private PropertyMapping currentPropertyMapping;
/*  43:    */   private JoinSequence joinSequence;
/*  44:    */   private boolean expectingCollectionIndex;
/*  45:    */   private LinkedList collectionElements;
/*  46:    */   
/*  47:    */   public PathExpressionParser()
/*  48:    */   {
/*  49: 68 */     this.componentPath = new StringBuffer();
/*  50:    */     
/*  51: 70 */     this.path = new StringBuffer();
/*  52:    */     
/*  53:    */ 
/*  54: 73 */     this.joinType = JoinType.INNER_JOIN;
/*  55: 74 */     this.useThetaStyleJoin = true;
/*  56:    */     
/*  57:    */ 
/*  58:    */ 
/*  59:    */ 
/*  60: 79 */     this.collectionElements = new LinkedList();
/*  61:    */   }
/*  62:    */   
/*  63:    */   void setJoinType(JoinType joinType)
/*  64:    */   {
/*  65: 82 */     this.joinType = joinType;
/*  66:    */   }
/*  67:    */   
/*  68:    */   void setUseThetaStyleJoin(boolean useThetaStyleJoin)
/*  69:    */   {
/*  70: 86 */     this.useThetaStyleJoin = useThetaStyleJoin;
/*  71:    */   }
/*  72:    */   
/*  73:    */   private void addJoin(String name, AssociationType joinableType)
/*  74:    */     throws QueryException
/*  75:    */   {
/*  76:    */     try
/*  77:    */     {
/*  78: 91 */       this.joinSequence.addJoin(joinableType, name, this.joinType, currentColumns());
/*  79:    */     }
/*  80:    */     catch (MappingException me)
/*  81:    */     {
/*  82: 94 */       throw new QueryException(me);
/*  83:    */     }
/*  84:    */   }
/*  85:    */   
/*  86:    */   private void addJoin(String name, AssociationType joinableType, String[] foreignKeyColumns)
/*  87:    */     throws QueryException
/*  88:    */   {
/*  89:    */     try
/*  90:    */     {
/*  91:100 */       this.joinSequence.addJoin(joinableType, name, this.joinType, foreignKeyColumns);
/*  92:    */     }
/*  93:    */     catch (MappingException me)
/*  94:    */     {
/*  95:103 */       throw new QueryException(me);
/*  96:    */     }
/*  97:    */   }
/*  98:    */   
/*  99:    */   String continueFromManyToMany(String entityName, String[] joinColumns, QueryTranslatorImpl q)
/* 100:    */     throws QueryException
/* 101:    */   {
/* 102:108 */     start(q);
/* 103:109 */     this.continuation = true;
/* 104:110 */     this.currentName = q.createNameFor(entityName);
/* 105:111 */     q.addType(this.currentName, entityName);
/* 106:112 */     Queryable classPersister = q.getEntityPersister(entityName);
/* 107:    */     
/* 108:114 */     addJoin(this.currentName, q.getFactory().getTypeResolver().getTypeFactory().manyToOne(entityName), joinColumns);
/* 109:115 */     this.currentPropertyMapping = classPersister;
/* 110:116 */     return this.currentName;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void ignoreInitialJoin()
/* 114:    */   {
/* 115:120 */     this.ignoreInitialJoin = true;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void token(String token, QueryTranslatorImpl q)
/* 119:    */     throws QueryException
/* 120:    */   {
/* 121:125 */     if (token != null) {
/* 122:125 */       this.path.append(token);
/* 123:    */     }
/* 124:127 */     String alias = q.getPathAlias(this.path.toString());
/* 125:128 */     if (alias != null)
/* 126:    */     {
/* 127:129 */       reset(q);
/* 128:130 */       this.currentName = alias;
/* 129:131 */       this.currentPropertyMapping = q.getPropertyMapping(this.currentName);
/* 130:132 */       if (!this.ignoreInitialJoin)
/* 131:    */       {
/* 132:133 */         JoinSequence ojf = q.getPathJoin(this.path.toString());
/* 133:    */         try
/* 134:    */         {
/* 135:135 */           this.joinSequence.addCondition(ojf.toJoinFragment(q.getEnabledFilters(), true).toWhereFragmentString());
/* 136:    */         }
/* 137:    */         catch (MappingException me)
/* 138:    */         {
/* 139:138 */           throw new QueryException(me);
/* 140:    */         }
/* 141:    */       }
/* 142:    */     }
/* 143:145 */     else if (".".equals(token))
/* 144:    */     {
/* 145:146 */       this.dotcount += 1;
/* 146:    */     }
/* 147:149 */     else if (this.dotcount == 0)
/* 148:    */     {
/* 149:150 */       if (!this.continuation)
/* 150:    */       {
/* 151:151 */         if (!q.isName(token)) {
/* 152:151 */           throw new QueryException("undefined alias: " + token);
/* 153:    */         }
/* 154:152 */         this.currentName = token;
/* 155:153 */         this.currentPropertyMapping = q.getPropertyMapping(this.currentName);
/* 156:    */       }
/* 157:    */     }
/* 158:156 */     else if (this.dotcount == 1)
/* 159:    */     {
/* 160:157 */       if (this.currentName != null) {
/* 161:158 */         this.currentProperty = token;
/* 162:160 */       } else if (this.collectionName != null) {
/* 163:162 */         this.continuation = false;
/* 164:    */       } else {
/* 165:165 */         throw new QueryException("unexpected");
/* 166:    */       }
/* 167:    */     }
/* 168:    */     else
/* 169:    */     {
/* 170:171 */       Type propertyType = getPropertyType();
/* 171:173 */       if (propertyType == null) {
/* 172:174 */         throw new QueryException("unresolved property: " + this.path);
/* 173:    */       }
/* 174:177 */       if (propertyType.isComponentType()) {
/* 175:178 */         dereferenceComponent(token);
/* 176:180 */       } else if (propertyType.isEntityType())
/* 177:    */       {
/* 178:181 */         if (!isCollectionValued()) {
/* 179:181 */           dereferenceEntity(token, (EntityType)propertyType, q);
/* 180:    */         }
/* 181:    */       }
/* 182:183 */       else if (propertyType.isCollectionType()) {
/* 183:184 */         dereferenceCollection(token, ((CollectionType)propertyType).getRole(), q);
/* 184:188 */       } else if (token != null) {
/* 185:188 */         throw new QueryException("dereferenced: " + this.path);
/* 186:    */       }
/* 187:    */     }
/* 188:    */   }
/* 189:    */   
/* 190:    */   private void dereferenceEntity(String propertyName, EntityType propertyType, QueryTranslatorImpl q)
/* 191:    */     throws QueryException
/* 192:    */   {
/* 193:200 */     boolean isIdShortcut = ("id".equals(propertyName)) && (propertyType.isReferenceToPrimaryKey());
/* 194:    */     String idPropertyName;
/* 195:    */     try
/* 196:    */     {
/* 197:206 */       idPropertyName = propertyType.getIdentifierOrUniqueKeyPropertyName(q.getFactory());
/* 198:    */     }
/* 199:    */     catch (MappingException me)
/* 200:    */     {
/* 201:209 */       throw new QueryException(me);
/* 202:    */     }
/* 203:211 */     boolean isNamedIdPropertyShortcut = (idPropertyName != null) && (idPropertyName.equals(propertyName)) && (propertyType.isReferenceToPrimaryKey());
/* 204:216 */     if ((isIdShortcut) || (isNamedIdPropertyShortcut))
/* 205:    */     {
/* 206:219 */       if (this.componentPath.length() > 0) {
/* 207:219 */         this.componentPath.append('.');
/* 208:    */       }
/* 209:220 */       this.componentPath.append(propertyName);
/* 210:    */     }
/* 211:    */     else
/* 212:    */     {
/* 213:223 */       String entityClass = propertyType.getAssociatedEntityName();
/* 214:224 */       String name = q.createNameFor(entityClass);
/* 215:225 */       q.addType(name, entityClass);
/* 216:226 */       addJoin(name, propertyType);
/* 217:227 */       if (propertyType.isOneToOne()) {
/* 218:227 */         this.oneToOneOwnerName = this.currentName;
/* 219:    */       }
/* 220:228 */       this.ownerAssociationType = propertyType;
/* 221:229 */       this.currentName = name;
/* 222:230 */       this.currentProperty = propertyName;
/* 223:231 */       q.addPathAliasAndJoin(this.path.substring(0, this.path.toString().lastIndexOf('.')), name, this.joinSequence.copy());
/* 224:232 */       this.componentPath.setLength(0);
/* 225:233 */       this.currentPropertyMapping = q.getEntityPersister(entityClass);
/* 226:    */     }
/* 227:    */   }
/* 228:    */   
/* 229:    */   private void dereferenceComponent(String propertyName)
/* 230:    */   {
/* 231:238 */     if (propertyName != null)
/* 232:    */     {
/* 233:239 */       if (this.componentPath.length() > 0) {
/* 234:239 */         this.componentPath.append('.');
/* 235:    */       }
/* 236:240 */       this.componentPath.append(propertyName);
/* 237:    */     }
/* 238:    */   }
/* 239:    */   
/* 240:    */   private void dereferenceCollection(String propertyName, String role, QueryTranslatorImpl q)
/* 241:    */     throws QueryException
/* 242:    */   {
/* 243:245 */     this.collectionRole = role;
/* 244:246 */     QueryableCollection collPersister = q.getCollectionPersister(role);
/* 245:247 */     String name = q.createNameForCollection(role);
/* 246:248 */     addJoin(name, collPersister.getCollectionType());
/* 247:    */     
/* 248:250 */     this.collectionName = name;
/* 249:251 */     this.collectionOwnerName = this.currentName;
/* 250:252 */     this.currentName = name;
/* 251:253 */     this.currentProperty = propertyName;
/* 252:254 */     this.componentPath.setLength(0);
/* 253:255 */     this.currentPropertyMapping = new CollectionPropertyMapping(collPersister);
/* 254:    */   }
/* 255:    */   
/* 256:    */   private String getPropertyPath()
/* 257:    */   {
/* 258:259 */     if (this.currentProperty == null) {
/* 259:260 */       return "id";
/* 260:    */     }
/* 261:263 */     if (this.componentPath.length() > 0) {
/* 262:264 */       return this.currentProperty + '.' + this.componentPath.toString();
/* 263:    */     }
/* 264:271 */     return this.currentProperty;
/* 265:    */   }
/* 266:    */   
/* 267:    */   private PropertyMapping getPropertyMapping()
/* 268:    */   {
/* 269:277 */     return this.currentPropertyMapping;
/* 270:    */   }
/* 271:    */   
/* 272:    */   private void setType()
/* 273:    */     throws QueryException
/* 274:    */   {
/* 275:281 */     if (this.currentProperty == null) {
/* 276:282 */       this.type = getPropertyMapping().getType();
/* 277:    */     } else {
/* 278:285 */       this.type = getPropertyType();
/* 279:    */     }
/* 280:    */   }
/* 281:    */   
/* 282:    */   protected Type getPropertyType()
/* 283:    */     throws QueryException
/* 284:    */   {
/* 285:290 */     String propertyPath = getPropertyPath();
/* 286:291 */     Type propertyType = getPropertyMapping().toType(propertyPath);
/* 287:292 */     if (propertyType == null) {
/* 288:293 */       throw new QueryException("could not resolve property type: " + propertyPath);
/* 289:    */     }
/* 290:295 */     return propertyType;
/* 291:    */   }
/* 292:    */   
/* 293:    */   protected String[] currentColumns()
/* 294:    */     throws QueryException
/* 295:    */   {
/* 296:299 */     String propertyPath = getPropertyPath();
/* 297:300 */     String[] propertyColumns = getPropertyMapping().toColumns(this.currentName, propertyPath);
/* 298:301 */     if (propertyColumns == null) {
/* 299:302 */       throw new QueryException("could not resolve property columns: " + propertyPath);
/* 300:    */     }
/* 301:304 */     return propertyColumns;
/* 302:    */   }
/* 303:    */   
/* 304:    */   private void reset(QueryTranslatorImpl q)
/* 305:    */   {
/* 306:309 */     this.dotcount = 0;
/* 307:310 */     this.currentName = null;
/* 308:311 */     this.currentProperty = null;
/* 309:312 */     this.collectionName = null;
/* 310:313 */     this.collectionRole = null;
/* 311:314 */     this.componentPath.setLength(0);
/* 312:315 */     this.type = null;
/* 313:316 */     this.collectionName = null;
/* 314:317 */     this.columns = null;
/* 315:318 */     this.expectingCollectionIndex = false;
/* 316:319 */     this.continuation = false;
/* 317:320 */     this.currentPropertyMapping = null;
/* 318:    */   }
/* 319:    */   
/* 320:    */   public void start(QueryTranslatorImpl q)
/* 321:    */   {
/* 322:324 */     if (!this.continuation)
/* 323:    */     {
/* 324:325 */       reset(q);
/* 325:326 */       this.path.setLength(0);
/* 326:327 */       this.joinSequence = new JoinSequence(q.getFactory()).setUseThetaStyle(this.useThetaStyleJoin);
/* 327:    */     }
/* 328:    */   }
/* 329:    */   
/* 330:    */   public void end(QueryTranslatorImpl q)
/* 331:    */     throws QueryException
/* 332:    */   {
/* 333:332 */     this.ignoreInitialJoin = false;
/* 334:    */     
/* 335:334 */     Type propertyType = getPropertyType();
/* 336:335 */     if ((propertyType != null) && (propertyType.isCollectionType()))
/* 337:    */     {
/* 338:336 */       this.collectionRole = ((CollectionType)propertyType).getRole();
/* 339:337 */       this.collectionName = q.createNameForCollection(this.collectionRole);
/* 340:338 */       prepareForIndex(q);
/* 341:    */     }
/* 342:    */     else
/* 343:    */     {
/* 344:341 */       this.columns = currentColumns();
/* 345:342 */       setType();
/* 346:    */     }
/* 347:346 */     this.continuation = false;
/* 348:    */   }
/* 349:    */   
/* 350:    */   private void prepareForIndex(QueryTranslatorImpl q)
/* 351:    */     throws QueryException
/* 352:    */   {
/* 353:352 */     QueryableCollection collPersister = q.getCollectionPersister(this.collectionRole);
/* 354:354 */     if (!collPersister.hasIndex()) {
/* 355:354 */       throw new QueryException("unindexed collection before []: " + this.path);
/* 356:    */     }
/* 357:355 */     String[] indexCols = collPersister.getIndexColumnNames();
/* 358:356 */     if (indexCols.length != 1) {
/* 359:356 */       throw new QueryException("composite-index appears in []: " + this.path);
/* 360:    */     }
/* 361:359 */     JoinSequence fromJoins = new JoinSequence(q.getFactory()).setUseThetaStyle(this.useThetaStyleJoin).setRoot(collPersister, this.collectionName).setNext(this.joinSequence.copy());
/* 362:364 */     if (!this.continuation) {
/* 363:364 */       addJoin(this.collectionName, collPersister.getCollectionType());
/* 364:    */     }
/* 365:366 */     this.joinSequence.addCondition(this.collectionName + '.' + indexCols[0] + " = ");
/* 366:    */     
/* 367:368 */     CollectionElement elem = new CollectionElement();
/* 368:369 */     elem.elementColumns = collPersister.getElementColumnNames(this.collectionName);
/* 369:370 */     elem.elementType = collPersister.getElementType();
/* 370:371 */     elem.isOneToMany = collPersister.isOneToMany();
/* 371:372 */     elem.alias = this.collectionName;
/* 372:373 */     elem.joinSequence = this.joinSequence;
/* 373:374 */     this.collectionElements.addLast(elem);
/* 374:375 */     setExpectingCollectionIndex();
/* 375:    */     
/* 376:377 */     q.addCollection(this.collectionName, this.collectionRole);
/* 377:378 */     q.addFromJoinOnly(this.collectionName, fromJoins);
/* 378:    */   }
/* 379:    */   
/* 380:    */   static final class CollectionElement
/* 381:    */   {
/* 382:    */     Type elementType;
/* 383:    */     boolean isOneToMany;
/* 384:    */     String alias;
/* 385:    */     String[] elementColumns;
/* 386:    */     JoinSequence joinSequence;
/* 387:387 */     StringBuffer indexValue = new StringBuffer();
/* 388:    */   }
/* 389:    */   
/* 390:    */   public CollectionElement lastCollectionElement()
/* 391:    */   {
/* 392:391 */     return (CollectionElement)this.collectionElements.removeLast();
/* 393:    */   }
/* 394:    */   
/* 395:    */   public void setLastCollectionElementIndexValue(String value)
/* 396:    */   {
/* 397:395 */     ((CollectionElement)this.collectionElements.getLast()).indexValue.append(value);
/* 398:    */   }
/* 399:    */   
/* 400:    */   public boolean isExpectingCollectionIndex()
/* 401:    */   {
/* 402:399 */     return this.expectingCollectionIndex;
/* 403:    */   }
/* 404:    */   
/* 405:    */   protected void setExpectingCollectionIndex()
/* 406:    */     throws QueryException
/* 407:    */   {
/* 408:403 */     this.expectingCollectionIndex = true;
/* 409:    */   }
/* 410:    */   
/* 411:    */   public JoinSequence getWhereJoin()
/* 412:    */   {
/* 413:407 */     return this.joinSequence;
/* 414:    */   }
/* 415:    */   
/* 416:    */   public String getWhereColumn()
/* 417:    */     throws QueryException
/* 418:    */   {
/* 419:411 */     if (this.columns.length != 1) {
/* 420:412 */       throw new QueryException("path expression ends in a composite value: " + this.path);
/* 421:    */     }
/* 422:414 */     return this.columns[0];
/* 423:    */   }
/* 424:    */   
/* 425:    */   public String[] getWhereColumns()
/* 426:    */   {
/* 427:418 */     return this.columns;
/* 428:    */   }
/* 429:    */   
/* 430:    */   public Type getWhereColumnType()
/* 431:    */   {
/* 432:422 */     return this.type;
/* 433:    */   }
/* 434:    */   
/* 435:    */   public String getName()
/* 436:    */   {
/* 437:426 */     return this.currentName == null ? this.collectionName : this.currentName;
/* 438:    */   }
/* 439:    */   
/* 440:    */   public String getCollectionSubquery(Map enabledFilters)
/* 441:    */     throws QueryException
/* 442:    */   {
/* 443:431 */     return CollectionSubqueryFactory.createCollectionSubquery(this.joinSequence, enabledFilters, currentColumns());
/* 444:    */   }
/* 445:    */   
/* 446:    */   public boolean isCollectionValued()
/* 447:    */     throws QueryException
/* 448:    */   {
/* 449:436 */     return (this.collectionName != null) && (!getPropertyType().isCollectionType());
/* 450:    */   }
/* 451:    */   
/* 452:    */   public void addAssociation(QueryTranslatorImpl q)
/* 453:    */     throws QueryException
/* 454:    */   {
/* 455:440 */     q.addJoin(getName(), this.joinSequence);
/* 456:    */   }
/* 457:    */   
/* 458:    */   public String addFromAssociation(QueryTranslatorImpl q)
/* 459:    */     throws QueryException
/* 460:    */   {
/* 461:444 */     if (isCollectionValued()) {
/* 462:445 */       return addFromCollection(q);
/* 463:    */     }
/* 464:448 */     q.addFrom(this.currentName, this.joinSequence);
/* 465:449 */     return this.currentName;
/* 466:    */   }
/* 467:    */   
/* 468:    */   public String addFromCollection(QueryTranslatorImpl q)
/* 469:    */     throws QueryException
/* 470:    */   {
/* 471:454 */     Type collectionElementType = getPropertyType();
/* 472:456 */     if (collectionElementType == null) {
/* 473:457 */       throw new QueryException("must specify 'elements' for collection valued property in from clause: " + this.path);
/* 474:    */     }
/* 475:460 */     if (collectionElementType.isEntityType())
/* 476:    */     {
/* 477:462 */       QueryableCollection collectionPersister = q.getCollectionPersister(this.collectionRole);
/* 478:463 */       Queryable entityPersister = (Queryable)collectionPersister.getElementPersister();
/* 479:464 */       String clazz = entityPersister.getEntityName();
/* 480:    */       String elementName;
/* 481:467 */       if (collectionPersister.isOneToMany())
/* 482:    */       {
/* 483:468 */         String elementName = this.collectionName;
/* 484:    */         
/* 485:470 */         q.decoratePropertyMapping(elementName, collectionPersister);
/* 486:    */       }
/* 487:    */       else
/* 488:    */       {
/* 489:473 */         q.addCollection(this.collectionName, this.collectionRole);
/* 490:474 */         elementName = q.createNameFor(clazz);
/* 491:475 */         addJoin(elementName, (AssociationType)collectionElementType);
/* 492:    */       }
/* 493:477 */       q.addFrom(elementName, clazz, this.joinSequence);
/* 494:478 */       this.currentPropertyMapping = new CollectionPropertyMapping(collectionPersister);
/* 495:479 */       return elementName;
/* 496:    */     }
/* 497:483 */     q.addFromCollection(this.collectionName, this.collectionRole, this.joinSequence);
/* 498:484 */     return this.collectionName;
/* 499:    */   }
/* 500:    */   
/* 501:    */   String getCollectionName()
/* 502:    */   {
/* 503:490 */     return this.collectionName;
/* 504:    */   }
/* 505:    */   
/* 506:    */   String getCollectionRole()
/* 507:    */   {
/* 508:494 */     return this.collectionRole;
/* 509:    */   }
/* 510:    */   
/* 511:    */   String getCollectionOwnerName()
/* 512:    */   {
/* 513:498 */     return this.collectionOwnerName;
/* 514:    */   }
/* 515:    */   
/* 516:    */   String getOneToOneOwnerName()
/* 517:    */   {
/* 518:502 */     return this.oneToOneOwnerName;
/* 519:    */   }
/* 520:    */   
/* 521:    */   AssociationType getOwnerAssociationType()
/* 522:    */   {
/* 523:506 */     return this.ownerAssociationType;
/* 524:    */   }
/* 525:    */   
/* 526:    */   String getCurrentProperty()
/* 527:    */   {
/* 528:510 */     return this.currentProperty;
/* 529:    */   }
/* 530:    */   
/* 531:    */   String getCurrentName()
/* 532:    */   {
/* 533:514 */     return this.currentName;
/* 534:    */   }
/* 535:    */   
/* 536:    */   public void fetch(QueryTranslatorImpl q, String entityName)
/* 537:    */     throws QueryException
/* 538:    */   {
/* 539:518 */     if (isCollectionValued()) {
/* 540:519 */       q.setCollectionToFetch(getCollectionRole(), getCollectionName(), getCollectionOwnerName(), entityName);
/* 541:    */     } else {
/* 542:522 */       q.addEntityToFetch(entityName, getOneToOneOwnerName(), getOwnerAssociationType());
/* 543:    */     }
/* 544:    */   }
/* 545:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.classic.PathExpressionParser
 * JD-Core Version:    0.7.0.1
 */