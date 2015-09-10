/*   1:    */ package org.hibernate.internal;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import java.math.BigInteger;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.Arrays;
/*   8:    */ import java.util.Calendar;
/*   9:    */ import java.util.Collection;
/*  10:    */ import java.util.Date;
/*  11:    */ import java.util.HashMap;
/*  12:    */ import java.util.HashSet;
/*  13:    */ import java.util.Iterator;
/*  14:    */ import java.util.List;
/*  15:    */ import java.util.Locale;
/*  16:    */ import java.util.Map;
/*  17:    */ import java.util.Map.Entry;
/*  18:    */ import java.util.Set;
/*  19:    */ import org.hibernate.CacheMode;
/*  20:    */ import org.hibernate.FlushMode;
/*  21:    */ import org.hibernate.HibernateException;
/*  22:    */ import org.hibernate.LockOptions;
/*  23:    */ import org.hibernate.MappingException;
/*  24:    */ import org.hibernate.NonUniqueResultException;
/*  25:    */ import org.hibernate.PropertyNotFoundException;
/*  26:    */ import org.hibernate.Query;
/*  27:    */ import org.hibernate.QueryException;
/*  28:    */ import org.hibernate.Session;
/*  29:    */ import org.hibernate.TypeHelper;
/*  30:    */ import org.hibernate.engine.query.spi.NamedParameterDescriptor;
/*  31:    */ import org.hibernate.engine.query.spi.ParameterMetadata;
/*  32:    */ import org.hibernate.engine.spi.PersistenceContext;
/*  33:    */ import org.hibernate.engine.spi.QueryParameters;
/*  34:    */ import org.hibernate.engine.spi.RowSelection;
/*  35:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  36:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  37:    */ import org.hibernate.engine.spi.TypedValue;
/*  38:    */ import org.hibernate.internal.util.MarkerObject;
/*  39:    */ import org.hibernate.internal.util.ReflectHelper;
/*  40:    */ import org.hibernate.internal.util.StringHelper;
/*  41:    */ import org.hibernate.internal.util.collections.ArrayHelper;
/*  42:    */ import org.hibernate.property.Getter;
/*  43:    */ import org.hibernate.proxy.HibernateProxyHelper;
/*  44:    */ import org.hibernate.transform.ResultTransformer;
/*  45:    */ import org.hibernate.type.SerializableType;
/*  46:    */ import org.hibernate.type.StandardBasicTypes;
/*  47:    */ import org.hibernate.type.Type;
/*  48:    */ import org.hibernate.type.TypeResolver;
/*  49:    */ 
/*  50:    */ public abstract class AbstractQueryImpl
/*  51:    */   implements Query
/*  52:    */ {
/*  53: 77 */   private static final Object UNSET_PARAMETER = new MarkerObject("<unset parameter>");
/*  54: 78 */   private static final Object UNSET_TYPE = new MarkerObject("<unset type>");
/*  55:    */   private final String queryString;
/*  56:    */   protected final SessionImplementor session;
/*  57:    */   protected final ParameterMetadata parameterMetadata;
/*  58: 85 */   private List values = new ArrayList(4);
/*  59: 86 */   private List types = new ArrayList(4);
/*  60: 87 */   private Map<String, TypedValue> namedParameters = new HashMap(4);
/*  61: 88 */   private Map namedParameterLists = new HashMap(4);
/*  62:    */   private Object optionalObject;
/*  63:    */   private Serializable optionalId;
/*  64:    */   private String optionalEntityName;
/*  65:    */   private RowSelection selection;
/*  66:    */   private boolean cacheable;
/*  67:    */   private String cacheRegion;
/*  68:    */   private String comment;
/*  69:    */   private FlushMode flushMode;
/*  70:    */   private CacheMode cacheMode;
/*  71:    */   private FlushMode sessionFlushMode;
/*  72:    */   private CacheMode sessionCacheMode;
/*  73:    */   private Serializable collectionKey;
/*  74:    */   private Boolean readOnly;
/*  75:    */   private ResultTransformer resultTransformer;
/*  76:    */   
/*  77:    */   public AbstractQueryImpl(String queryString, FlushMode flushMode, SessionImplementor session, ParameterMetadata parameterMetadata)
/*  78:    */   {
/*  79:111 */     this.session = session;
/*  80:112 */     this.queryString = queryString;
/*  81:113 */     this.selection = new RowSelection();
/*  82:114 */     this.flushMode = flushMode;
/*  83:115 */     this.cacheMode = null;
/*  84:116 */     this.parameterMetadata = parameterMetadata;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public ParameterMetadata getParameterMetadata()
/*  88:    */   {
/*  89:120 */     return this.parameterMetadata;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public String toString()
/*  93:    */   {
/*  94:124 */     return StringHelper.unqualify(getClass().getName()) + '(' + this.queryString + ')';
/*  95:    */   }
/*  96:    */   
/*  97:    */   public final String getQueryString()
/*  98:    */   {
/*  99:128 */     return this.queryString;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public RowSelection getSelection()
/* 103:    */   {
/* 104:133 */     return this.selection;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public Query setFlushMode(FlushMode flushMode)
/* 108:    */   {
/* 109:137 */     this.flushMode = flushMode;
/* 110:138 */     return this;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public Query setCacheMode(CacheMode cacheMode)
/* 114:    */   {
/* 115:142 */     this.cacheMode = cacheMode;
/* 116:143 */     return this;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public CacheMode getCacheMode()
/* 120:    */   {
/* 121:147 */     return this.cacheMode;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public Query setCacheable(boolean cacheable)
/* 125:    */   {
/* 126:151 */     this.cacheable = cacheable;
/* 127:152 */     return this;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public Query setCacheRegion(String cacheRegion)
/* 131:    */   {
/* 132:156 */     if (cacheRegion != null) {
/* 133:157 */       this.cacheRegion = cacheRegion.trim();
/* 134:    */     }
/* 135:158 */     return this;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public Query setComment(String comment)
/* 139:    */   {
/* 140:162 */     this.comment = comment;
/* 141:163 */     return this;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public Query setFirstResult(int firstResult)
/* 145:    */   {
/* 146:167 */     this.selection.setFirstRow(Integer.valueOf(firstResult));
/* 147:168 */     return this;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public Query setMaxResults(int maxResults)
/* 151:    */   {
/* 152:172 */     if (maxResults < 0) {
/* 153:174 */       this.selection.setMaxRows(null);
/* 154:    */     } else {
/* 155:177 */       this.selection.setMaxRows(Integer.valueOf(maxResults));
/* 156:    */     }
/* 157:179 */     return this;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public Query setTimeout(int timeout)
/* 161:    */   {
/* 162:183 */     this.selection.setTimeout(Integer.valueOf(timeout));
/* 163:184 */     return this;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public Query setFetchSize(int fetchSize)
/* 167:    */   {
/* 168:187 */     this.selection.setFetchSize(Integer.valueOf(fetchSize));
/* 169:188 */     return this;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public Type[] getReturnTypes()
/* 173:    */     throws HibernateException
/* 174:    */   {
/* 175:192 */     return this.session.getFactory().getReturnTypes(this.queryString);
/* 176:    */   }
/* 177:    */   
/* 178:    */   public String[] getReturnAliases()
/* 179:    */     throws HibernateException
/* 180:    */   {
/* 181:196 */     return this.session.getFactory().getReturnAliases(this.queryString);
/* 182:    */   }
/* 183:    */   
/* 184:    */   public Query setCollectionKey(Serializable collectionKey)
/* 185:    */   {
/* 186:200 */     this.collectionKey = collectionKey;
/* 187:201 */     return this;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public boolean isReadOnly()
/* 191:    */   {
/* 192:208 */     return this.readOnly == null ? getSession().getPersistenceContext().isDefaultReadOnly() : this.readOnly.booleanValue();
/* 193:    */   }
/* 194:    */   
/* 195:    */   public Query setReadOnly(boolean readOnly)
/* 196:    */   {
/* 197:218 */     this.readOnly = Boolean.valueOf(readOnly);
/* 198:219 */     return this;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public Query setResultTransformer(ResultTransformer transformer)
/* 202:    */   {
/* 203:223 */     this.resultTransformer = transformer;
/* 204:224 */     return this;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public void setOptionalEntityName(String optionalEntityName)
/* 208:    */   {
/* 209:228 */     this.optionalEntityName = optionalEntityName;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public void setOptionalId(Serializable optionalId)
/* 213:    */   {
/* 214:232 */     this.optionalId = optionalId;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public void setOptionalObject(Object optionalObject)
/* 218:    */   {
/* 219:236 */     this.optionalObject = optionalObject;
/* 220:    */   }
/* 221:    */   
/* 222:    */   SessionImplementor getSession()
/* 223:    */   {
/* 224:240 */     return this.session;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public abstract LockOptions getLockOptions();
/* 228:    */   
/* 229:    */   protected Map getNamedParams()
/* 230:    */   {
/* 231:254 */     return new HashMap(this.namedParameters);
/* 232:    */   }
/* 233:    */   
/* 234:    */   public String[] getNamedParameters()
/* 235:    */     throws HibernateException
/* 236:    */   {
/* 237:272 */     return ArrayHelper.toStringArray(this.parameterMetadata.getNamedParameterNames());
/* 238:    */   }
/* 239:    */   
/* 240:    */   public boolean hasNamedParameters()
/* 241:    */   {
/* 242:282 */     return this.parameterMetadata.getNamedParameterNames().size() > 0;
/* 243:    */   }
/* 244:    */   
/* 245:    */   protected Map getNamedParameterLists()
/* 246:    */   {
/* 247:292 */     return this.namedParameterLists;
/* 248:    */   }
/* 249:    */   
/* 250:    */   protected List getValues()
/* 251:    */   {
/* 252:302 */     return this.values;
/* 253:    */   }
/* 254:    */   
/* 255:    */   protected List getTypes()
/* 256:    */   {
/* 257:312 */     return this.types;
/* 258:    */   }
/* 259:    */   
/* 260:    */   protected void verifyParameters()
/* 261:    */     throws QueryException
/* 262:    */   {
/* 263:322 */     verifyParameters(false);
/* 264:    */   }
/* 265:    */   
/* 266:    */   protected void verifyParameters(boolean reserveFirstParameter)
/* 267:    */     throws HibernateException
/* 268:    */   {
/* 269:334 */     if (this.parameterMetadata.getNamedParameterNames().size() != this.namedParameters.size() + this.namedParameterLists.size())
/* 270:    */     {
/* 271:335 */       Set missingParams = new HashSet(this.parameterMetadata.getNamedParameterNames());
/* 272:336 */       missingParams.removeAll(this.namedParameterLists.keySet());
/* 273:337 */       missingParams.removeAll(this.namedParameters.keySet());
/* 274:338 */       throw new QueryException("Not all named parameters have been set: " + missingParams, getQueryString());
/* 275:    */     }
/* 276:341 */     int positionalValueSpan = 0;
/* 277:342 */     for (int i = 0; i < this.values.size(); i++)
/* 278:    */     {
/* 279:343 */       Object object = this.types.get(i);
/* 280:344 */       if ((this.values.get(i) == UNSET_PARAMETER) || (object == UNSET_TYPE))
/* 281:    */       {
/* 282:345 */         if ((!reserveFirstParameter) || (i != 0)) {
/* 283:349 */           throw new QueryException("Unset positional parameter at position: " + i, getQueryString());
/* 284:    */         }
/* 285:    */       }
/* 286:    */       else {
/* 287:352 */         positionalValueSpan += ((Type)object).getColumnSpan(this.session.getFactory());
/* 288:    */       }
/* 289:    */     }
/* 290:355 */     if (this.parameterMetadata.getOrdinalParameterCount() != positionalValueSpan)
/* 291:    */     {
/* 292:356 */       if ((reserveFirstParameter) && (this.parameterMetadata.getOrdinalParameterCount() - 1 != positionalValueSpan)) {
/* 293:357 */         throw new QueryException("Expected positional parameter count: " + (this.parameterMetadata.getOrdinalParameterCount() - 1) + ", actual parameters: " + this.values, getQueryString());
/* 294:    */       }
/* 295:365 */       if (!reserveFirstParameter) {
/* 296:366 */         throw new QueryException("Expected positional parameter count: " + this.parameterMetadata.getOrdinalParameterCount() + ", actual parameters: " + this.values, getQueryString());
/* 297:    */       }
/* 298:    */     }
/* 299:    */   }
/* 300:    */   
/* 301:    */   public Query setParameter(int position, Object val, Type type)
/* 302:    */   {
/* 303:378 */     if (this.parameterMetadata.getOrdinalParameterCount() == 0) {
/* 304:379 */       throw new IllegalArgumentException("No positional parameters in query: " + getQueryString());
/* 305:    */     }
/* 306:381 */     if ((position < 0) || (position > this.parameterMetadata.getOrdinalParameterCount() - 1)) {
/* 307:382 */       throw new IllegalArgumentException("Positional parameter does not exist: " + position + " in query: " + getQueryString());
/* 308:    */     }
/* 309:384 */     int size = this.values.size();
/* 310:385 */     if (position < size)
/* 311:    */     {
/* 312:386 */       this.values.set(position, val);
/* 313:387 */       this.types.set(position, type);
/* 314:    */     }
/* 315:    */     else
/* 316:    */     {
/* 317:391 */       for (int i = 0; i < position - size; i++)
/* 318:    */       {
/* 319:392 */         this.values.add(UNSET_PARAMETER);
/* 320:393 */         this.types.add(UNSET_TYPE);
/* 321:    */       }
/* 322:395 */       this.values.add(val);
/* 323:396 */       this.types.add(type);
/* 324:    */     }
/* 325:398 */     return this;
/* 326:    */   }
/* 327:    */   
/* 328:    */   public Query setParameter(String name, Object val, Type type)
/* 329:    */   {
/* 330:402 */     if (!this.parameterMetadata.getNamedParameterNames().contains(name)) {
/* 331:403 */       throw new IllegalArgumentException("Parameter " + name + " does not exist as a named parameter in [" + getQueryString() + "]");
/* 332:    */     }
/* 333:406 */     this.namedParameters.put(name, new TypedValue(type, val));
/* 334:407 */     return this;
/* 335:    */   }
/* 336:    */   
/* 337:    */   public Query setParameter(int position, Object val)
/* 338:    */     throws HibernateException
/* 339:    */   {
/* 340:412 */     if (val == null) {
/* 341:413 */       setParameter(position, val, StandardBasicTypes.SERIALIZABLE);
/* 342:    */     } else {
/* 343:416 */       setParameter(position, val, determineType(position, val));
/* 344:    */     }
/* 345:418 */     return this;
/* 346:    */   }
/* 347:    */   
/* 348:    */   public Query setParameter(String name, Object val)
/* 349:    */     throws HibernateException
/* 350:    */   {
/* 351:422 */     if (val == null)
/* 352:    */     {
/* 353:423 */       Type type = this.parameterMetadata.getNamedParameterExpectedType(name);
/* 354:424 */       if (type == null) {
/* 355:425 */         type = StandardBasicTypes.SERIALIZABLE;
/* 356:    */       }
/* 357:427 */       setParameter(name, val, type);
/* 358:    */     }
/* 359:    */     else
/* 360:    */     {
/* 361:430 */       setParameter(name, val, determineType(name, val));
/* 362:    */     }
/* 363:432 */     return this;
/* 364:    */   }
/* 365:    */   
/* 366:    */   protected Type determineType(int paramPosition, Object paramValue, Type defaultType)
/* 367:    */   {
/* 368:436 */     Type type = this.parameterMetadata.getOrdinalParameterExpectedType(paramPosition + 1);
/* 369:437 */     if (type == null) {
/* 370:438 */       type = defaultType;
/* 371:    */     }
/* 372:440 */     return type;
/* 373:    */   }
/* 374:    */   
/* 375:    */   protected Type determineType(int paramPosition, Object paramValue)
/* 376:    */     throws HibernateException
/* 377:    */   {
/* 378:444 */     Type type = this.parameterMetadata.getOrdinalParameterExpectedType(paramPosition + 1);
/* 379:445 */     if (type == null) {
/* 380:446 */       type = guessType(paramValue);
/* 381:    */     }
/* 382:448 */     return type;
/* 383:    */   }
/* 384:    */   
/* 385:    */   protected Type determineType(String paramName, Object paramValue, Type defaultType)
/* 386:    */   {
/* 387:452 */     Type type = this.parameterMetadata.getNamedParameterExpectedType(paramName);
/* 388:453 */     if (type == null) {
/* 389:454 */       type = defaultType;
/* 390:    */     }
/* 391:456 */     return type;
/* 392:    */   }
/* 393:    */   
/* 394:    */   protected Type determineType(String paramName, Object paramValue)
/* 395:    */     throws HibernateException
/* 396:    */   {
/* 397:460 */     Type type = this.parameterMetadata.getNamedParameterExpectedType(paramName);
/* 398:461 */     if (type == null) {
/* 399:462 */       type = guessType(paramValue);
/* 400:    */     }
/* 401:464 */     return type;
/* 402:    */   }
/* 403:    */   
/* 404:    */   protected Type determineType(String paramName, Class clazz)
/* 405:    */     throws HibernateException
/* 406:    */   {
/* 407:468 */     Type type = this.parameterMetadata.getNamedParameterExpectedType(paramName);
/* 408:469 */     if (type == null) {
/* 409:470 */       type = guessType(clazz);
/* 410:    */     }
/* 411:472 */     return type;
/* 412:    */   }
/* 413:    */   
/* 414:    */   private Type guessType(Object param)
/* 415:    */     throws HibernateException
/* 416:    */   {
/* 417:476 */     Class clazz = HibernateProxyHelper.getClassWithoutInitializingProxy(param);
/* 418:477 */     return guessType(clazz);
/* 419:    */   }
/* 420:    */   
/* 421:    */   private Type guessType(Class clazz)
/* 422:    */     throws HibernateException
/* 423:    */   {
/* 424:481 */     String typename = clazz.getName();
/* 425:482 */     Type type = this.session.getFactory().getTypeResolver().heuristicType(typename);
/* 426:483 */     boolean serializable = (type != null) && ((type instanceof SerializableType));
/* 427:484 */     if ((type == null) || (serializable))
/* 428:    */     {
/* 429:    */       try
/* 430:    */       {
/* 431:486 */         this.session.getFactory().getEntityPersister(clazz.getName());
/* 432:    */       }
/* 433:    */       catch (MappingException me)
/* 434:    */       {
/* 435:489 */         if (serializable) {
/* 436:490 */           return type;
/* 437:    */         }
/* 438:493 */         throw new HibernateException("Could not determine a type for class: " + typename);
/* 439:    */       }
/* 440:496 */       return ((Session)this.session).getTypeHelper().entity(clazz);
/* 441:    */     }
/* 442:499 */     return type;
/* 443:    */   }
/* 444:    */   
/* 445:    */   public Query setString(int position, String val)
/* 446:    */   {
/* 447:504 */     setParameter(position, val, StandardBasicTypes.STRING);
/* 448:505 */     return this;
/* 449:    */   }
/* 450:    */   
/* 451:    */   public Query setCharacter(int position, char val)
/* 452:    */   {
/* 453:509 */     setParameter(position, new Character(val), StandardBasicTypes.CHARACTER);
/* 454:510 */     return this;
/* 455:    */   }
/* 456:    */   
/* 457:    */   public Query setBoolean(int position, boolean val)
/* 458:    */   {
/* 459:514 */     Boolean valueToUse = Boolean.valueOf(val);
/* 460:515 */     Type typeToUse = determineType(position, valueToUse, StandardBasicTypes.BOOLEAN);
/* 461:516 */     setParameter(position, valueToUse, typeToUse);
/* 462:517 */     return this;
/* 463:    */   }
/* 464:    */   
/* 465:    */   public Query setByte(int position, byte val)
/* 466:    */   {
/* 467:521 */     setParameter(position, Byte.valueOf(val), StandardBasicTypes.BYTE);
/* 468:522 */     return this;
/* 469:    */   }
/* 470:    */   
/* 471:    */   public Query setShort(int position, short val)
/* 472:    */   {
/* 473:526 */     setParameter(position, Short.valueOf(val), StandardBasicTypes.SHORT);
/* 474:527 */     return this;
/* 475:    */   }
/* 476:    */   
/* 477:    */   public Query setInteger(int position, int val)
/* 478:    */   {
/* 479:531 */     setParameter(position, Integer.valueOf(val), StandardBasicTypes.INTEGER);
/* 480:532 */     return this;
/* 481:    */   }
/* 482:    */   
/* 483:    */   public Query setLong(int position, long val)
/* 484:    */   {
/* 485:536 */     setParameter(position, Long.valueOf(val), StandardBasicTypes.LONG);
/* 486:537 */     return this;
/* 487:    */   }
/* 488:    */   
/* 489:    */   public Query setFloat(int position, float val)
/* 490:    */   {
/* 491:541 */     setParameter(position, Float.valueOf(val), StandardBasicTypes.FLOAT);
/* 492:542 */     return this;
/* 493:    */   }
/* 494:    */   
/* 495:    */   public Query setDouble(int position, double val)
/* 496:    */   {
/* 497:546 */     setParameter(position, Double.valueOf(val), StandardBasicTypes.DOUBLE);
/* 498:547 */     return this;
/* 499:    */   }
/* 500:    */   
/* 501:    */   public Query setBinary(int position, byte[] val)
/* 502:    */   {
/* 503:551 */     setParameter(position, val, StandardBasicTypes.BINARY);
/* 504:552 */     return this;
/* 505:    */   }
/* 506:    */   
/* 507:    */   public Query setText(int position, String val)
/* 508:    */   {
/* 509:556 */     setParameter(position, val, StandardBasicTypes.TEXT);
/* 510:557 */     return this;
/* 511:    */   }
/* 512:    */   
/* 513:    */   public Query setSerializable(int position, Serializable val)
/* 514:    */   {
/* 515:561 */     setParameter(position, val, StandardBasicTypes.SERIALIZABLE);
/* 516:562 */     return this;
/* 517:    */   }
/* 518:    */   
/* 519:    */   public Query setDate(int position, Date date)
/* 520:    */   {
/* 521:566 */     setParameter(position, date, StandardBasicTypes.DATE);
/* 522:567 */     return this;
/* 523:    */   }
/* 524:    */   
/* 525:    */   public Query setTime(int position, Date date)
/* 526:    */   {
/* 527:571 */     setParameter(position, date, StandardBasicTypes.TIME);
/* 528:572 */     return this;
/* 529:    */   }
/* 530:    */   
/* 531:    */   public Query setTimestamp(int position, Date date)
/* 532:    */   {
/* 533:576 */     setParameter(position, date, StandardBasicTypes.TIMESTAMP);
/* 534:577 */     return this;
/* 535:    */   }
/* 536:    */   
/* 537:    */   public Query setEntity(int position, Object val)
/* 538:    */   {
/* 539:581 */     setParameter(position, val, ((Session)this.session).getTypeHelper().entity(resolveEntityName(val)));
/* 540:582 */     return this;
/* 541:    */   }
/* 542:    */   
/* 543:    */   private String resolveEntityName(Object val)
/* 544:    */   {
/* 545:586 */     if (val == null) {
/* 546:587 */       throw new IllegalArgumentException("entity for parameter binding cannot be null");
/* 547:    */     }
/* 548:589 */     return this.session.bestGuessEntityName(val);
/* 549:    */   }
/* 550:    */   
/* 551:    */   public Query setLocale(int position, Locale locale)
/* 552:    */   {
/* 553:593 */     setParameter(position, locale, StandardBasicTypes.LOCALE);
/* 554:594 */     return this;
/* 555:    */   }
/* 556:    */   
/* 557:    */   public Query setCalendar(int position, Calendar calendar)
/* 558:    */   {
/* 559:598 */     setParameter(position, calendar, StandardBasicTypes.CALENDAR);
/* 560:599 */     return this;
/* 561:    */   }
/* 562:    */   
/* 563:    */   public Query setCalendarDate(int position, Calendar calendar)
/* 564:    */   {
/* 565:603 */     setParameter(position, calendar, StandardBasicTypes.CALENDAR_DATE);
/* 566:604 */     return this;
/* 567:    */   }
/* 568:    */   
/* 569:    */   public Query setBinary(String name, byte[] val)
/* 570:    */   {
/* 571:608 */     setParameter(name, val, StandardBasicTypes.BINARY);
/* 572:609 */     return this;
/* 573:    */   }
/* 574:    */   
/* 575:    */   public Query setText(String name, String val)
/* 576:    */   {
/* 577:613 */     setParameter(name, val, StandardBasicTypes.TEXT);
/* 578:614 */     return this;
/* 579:    */   }
/* 580:    */   
/* 581:    */   public Query setBoolean(String name, boolean val)
/* 582:    */   {
/* 583:618 */     Boolean valueToUse = Boolean.valueOf(val);
/* 584:619 */     Type typeToUse = determineType(name, valueToUse, StandardBasicTypes.BOOLEAN);
/* 585:620 */     setParameter(name, valueToUse, typeToUse);
/* 586:621 */     return this;
/* 587:    */   }
/* 588:    */   
/* 589:    */   public Query setByte(String name, byte val)
/* 590:    */   {
/* 591:625 */     setParameter(name, Byte.valueOf(val), StandardBasicTypes.BYTE);
/* 592:626 */     return this;
/* 593:    */   }
/* 594:    */   
/* 595:    */   public Query setCharacter(String name, char val)
/* 596:    */   {
/* 597:630 */     setParameter(name, Character.valueOf(val), StandardBasicTypes.CHARACTER);
/* 598:631 */     return this;
/* 599:    */   }
/* 600:    */   
/* 601:    */   public Query setDate(String name, Date date)
/* 602:    */   {
/* 603:635 */     setParameter(name, date, StandardBasicTypes.DATE);
/* 604:636 */     return this;
/* 605:    */   }
/* 606:    */   
/* 607:    */   public Query setDouble(String name, double val)
/* 608:    */   {
/* 609:640 */     setParameter(name, Double.valueOf(val), StandardBasicTypes.DOUBLE);
/* 610:641 */     return this;
/* 611:    */   }
/* 612:    */   
/* 613:    */   public Query setEntity(String name, Object val)
/* 614:    */   {
/* 615:645 */     setParameter(name, val, ((Session)this.session).getTypeHelper().entity(resolveEntityName(val)));
/* 616:646 */     return this;
/* 617:    */   }
/* 618:    */   
/* 619:    */   public Query setFloat(String name, float val)
/* 620:    */   {
/* 621:650 */     setParameter(name, Float.valueOf(val), StandardBasicTypes.FLOAT);
/* 622:651 */     return this;
/* 623:    */   }
/* 624:    */   
/* 625:    */   public Query setInteger(String name, int val)
/* 626:    */   {
/* 627:655 */     setParameter(name, Integer.valueOf(val), StandardBasicTypes.INTEGER);
/* 628:656 */     return this;
/* 629:    */   }
/* 630:    */   
/* 631:    */   public Query setLocale(String name, Locale locale)
/* 632:    */   {
/* 633:660 */     setParameter(name, locale, StandardBasicTypes.LOCALE);
/* 634:661 */     return this;
/* 635:    */   }
/* 636:    */   
/* 637:    */   public Query setCalendar(String name, Calendar calendar)
/* 638:    */   {
/* 639:665 */     setParameter(name, calendar, StandardBasicTypes.CALENDAR);
/* 640:666 */     return this;
/* 641:    */   }
/* 642:    */   
/* 643:    */   public Query setCalendarDate(String name, Calendar calendar)
/* 644:    */   {
/* 645:670 */     setParameter(name, calendar, StandardBasicTypes.CALENDAR_DATE);
/* 646:671 */     return this;
/* 647:    */   }
/* 648:    */   
/* 649:    */   public Query setLong(String name, long val)
/* 650:    */   {
/* 651:675 */     setParameter(name, Long.valueOf(val), StandardBasicTypes.LONG);
/* 652:676 */     return this;
/* 653:    */   }
/* 654:    */   
/* 655:    */   public Query setSerializable(String name, Serializable val)
/* 656:    */   {
/* 657:680 */     setParameter(name, val, StandardBasicTypes.SERIALIZABLE);
/* 658:681 */     return this;
/* 659:    */   }
/* 660:    */   
/* 661:    */   public Query setShort(String name, short val)
/* 662:    */   {
/* 663:685 */     setParameter(name, Short.valueOf(val), StandardBasicTypes.SHORT);
/* 664:686 */     return this;
/* 665:    */   }
/* 666:    */   
/* 667:    */   public Query setString(String name, String val)
/* 668:    */   {
/* 669:690 */     setParameter(name, val, StandardBasicTypes.STRING);
/* 670:691 */     return this;
/* 671:    */   }
/* 672:    */   
/* 673:    */   public Query setTime(String name, Date date)
/* 674:    */   {
/* 675:695 */     setParameter(name, date, StandardBasicTypes.TIME);
/* 676:696 */     return this;
/* 677:    */   }
/* 678:    */   
/* 679:    */   public Query setTimestamp(String name, Date date)
/* 680:    */   {
/* 681:700 */     setParameter(name, date, StandardBasicTypes.TIMESTAMP);
/* 682:701 */     return this;
/* 683:    */   }
/* 684:    */   
/* 685:    */   public Query setBigDecimal(int position, BigDecimal number)
/* 686:    */   {
/* 687:705 */     setParameter(position, number, StandardBasicTypes.BIG_DECIMAL);
/* 688:706 */     return this;
/* 689:    */   }
/* 690:    */   
/* 691:    */   public Query setBigDecimal(String name, BigDecimal number)
/* 692:    */   {
/* 693:710 */     setParameter(name, number, StandardBasicTypes.BIG_DECIMAL);
/* 694:711 */     return this;
/* 695:    */   }
/* 696:    */   
/* 697:    */   public Query setBigInteger(int position, BigInteger number)
/* 698:    */   {
/* 699:715 */     setParameter(position, number, StandardBasicTypes.BIG_INTEGER);
/* 700:716 */     return this;
/* 701:    */   }
/* 702:    */   
/* 703:    */   public Query setBigInteger(String name, BigInteger number)
/* 704:    */   {
/* 705:720 */     setParameter(name, number, StandardBasicTypes.BIG_INTEGER);
/* 706:721 */     return this;
/* 707:    */   }
/* 708:    */   
/* 709:    */   public Query setParameterList(String name, Collection vals, Type type)
/* 710:    */     throws HibernateException
/* 711:    */   {
/* 712:725 */     if (!this.parameterMetadata.getNamedParameterNames().contains(name)) {
/* 713:726 */       throw new IllegalArgumentException("Parameter " + name + " does not exist as a named parameter in [" + getQueryString() + "]");
/* 714:    */     }
/* 715:728 */     this.namedParameterLists.put(name, new TypedValue(type, vals));
/* 716:729 */     return this;
/* 717:    */   }
/* 718:    */   
/* 719:    */   protected String expandParameterLists(Map namedParamsCopy)
/* 720:    */   {
/* 721:737 */     String query = this.queryString;
/* 722:738 */     Iterator iter = this.namedParameterLists.entrySet().iterator();
/* 723:739 */     while (iter.hasNext())
/* 724:    */     {
/* 725:740 */       Map.Entry me = (Map.Entry)iter.next();
/* 726:741 */       query = expandParameterList(query, (String)me.getKey(), (TypedValue)me.getValue(), namedParamsCopy);
/* 727:    */     }
/* 728:743 */     return query;
/* 729:    */   }
/* 730:    */   
/* 731:    */   private String expandParameterList(String query, String name, TypedValue typedList, Map namedParamsCopy)
/* 732:    */   {
/* 733:751 */     Collection vals = (Collection)typedList.getValue();
/* 734:752 */     Type type = typedList.getType();
/* 735:    */     
/* 736:754 */     boolean isJpaPositionalParam = this.parameterMetadata.getNamedParameterDescriptor(name).isJpaStyle();
/* 737:755 */     String paramPrefix = isJpaPositionalParam ? "?" : ":";
/* 738:756 */     String placeholder = paramPrefix.length() + name.length() + paramPrefix + name;
/* 739:761 */     if (query == null) {
/* 740:762 */       return query;
/* 741:    */     }
/* 742:764 */     int loc = query.indexOf(placeholder);
/* 743:766 */     if (loc < 0) {
/* 744:767 */       return query;
/* 745:    */     }
/* 746:770 */     String beforePlaceholder = query.substring(0, loc);
/* 747:771 */     String afterPlaceholder = query.substring(loc + placeholder.length());
/* 748:    */     
/* 749:    */ 
/* 750:    */ 
/* 751:775 */     boolean isEnclosedInParens = (StringHelper.getLastNonWhitespaceCharacter(beforePlaceholder) == '(') && (StringHelper.getFirstNonWhitespaceCharacter(afterPlaceholder) == ')');
/* 752:779 */     if ((vals.size() == 1) && (isEnclosedInParens))
/* 753:    */     {
/* 754:782 */       namedParamsCopy.put(name, new TypedValue(type, vals.iterator().next()));
/* 755:783 */       return query;
/* 756:    */     }
/* 757:786 */     StringBuffer list = new StringBuffer(16);
/* 758:787 */     Iterator iter = vals.iterator();
/* 759:788 */     int i = 0;
/* 760:789 */     while (iter.hasNext())
/* 761:    */     {
/* 762:790 */       String alias = (isJpaPositionalParam ? 'x' + name : name) + i++ + '_';
/* 763:791 */       namedParamsCopy.put(alias, new TypedValue(type, iter.next()));
/* 764:792 */       list.append(":").append(alias);
/* 765:793 */       if (iter.hasNext()) {
/* 766:794 */         list.append(", ");
/* 767:    */       }
/* 768:    */     }
/* 769:797 */     return StringHelper.replace(beforePlaceholder, afterPlaceholder, placeholder.toString(), list.toString(), true, true);
/* 770:    */   }
/* 771:    */   
/* 772:    */   public Query setParameterList(String name, Collection vals)
/* 773:    */     throws HibernateException
/* 774:    */   {
/* 775:808 */     if (vals == null) {
/* 776:809 */       throw new QueryException("Collection must be not null!");
/* 777:    */     }
/* 778:812 */     if (vals.size() == 0) {
/* 779:813 */       setParameterList(name, vals, null);
/* 780:    */     } else {
/* 781:816 */       setParameterList(name, vals, determineType(name, vals.iterator().next()));
/* 782:    */     }
/* 783:819 */     return this;
/* 784:    */   }
/* 785:    */   
/* 786:    */   public Query setParameterList(String name, Object[] vals, Type type)
/* 787:    */     throws HibernateException
/* 788:    */   {
/* 789:823 */     return setParameterList(name, Arrays.asList(vals), type);
/* 790:    */   }
/* 791:    */   
/* 792:    */   public Query setParameterList(String name, Object[] vals)
/* 793:    */     throws HibernateException
/* 794:    */   {
/* 795:827 */     return setParameterList(name, Arrays.asList(vals));
/* 796:    */   }
/* 797:    */   
/* 798:    */   public Query setProperties(Map map)
/* 799:    */     throws HibernateException
/* 800:    */   {
/* 801:831 */     String[] params = getNamedParameters();
/* 802:832 */     for (int i = 0; i < params.length; i++)
/* 803:    */     {
/* 804:833 */       String namedParam = params[i];
/* 805:834 */       Object object = map.get(namedParam);
/* 806:835 */       if (object != null)
/* 807:    */       {
/* 808:838 */         Class retType = object.getClass();
/* 809:839 */         if (Collection.class.isAssignableFrom(retType)) {
/* 810:840 */           setParameterList(namedParam, (Collection)object);
/* 811:842 */         } else if (retType.isArray()) {
/* 812:843 */           setParameterList(namedParam, (Object[])object);
/* 813:    */         } else {
/* 814:846 */           setParameter(namedParam, object, determineType(namedParam, retType));
/* 815:    */         }
/* 816:    */       }
/* 817:    */     }
/* 818:851 */     return this;
/* 819:    */   }
/* 820:    */   
/* 821:    */   public Query setProperties(Object bean)
/* 822:    */     throws HibernateException
/* 823:    */   {
/* 824:855 */     Class clazz = bean.getClass();
/* 825:856 */     String[] params = getNamedParameters();
/* 826:857 */     for (int i = 0; i < params.length; i++)
/* 827:    */     {
/* 828:858 */       String namedParam = params[i];
/* 829:    */       try
/* 830:    */       {
/* 831:860 */         Getter getter = ReflectHelper.getGetter(clazz, namedParam);
/* 832:861 */         Class retType = getter.getReturnType();
/* 833:862 */         Object object = getter.get(bean);
/* 834:863 */         if (Collection.class.isAssignableFrom(retType)) {
/* 835:864 */           setParameterList(namedParam, (Collection)object);
/* 836:866 */         } else if (retType.isArray()) {
/* 837:867 */           setParameterList(namedParam, (Object[])object);
/* 838:    */         } else {
/* 839:870 */           setParameter(namedParam, object, determineType(namedParam, retType));
/* 840:    */         }
/* 841:    */       }
/* 842:    */       catch (PropertyNotFoundException pnfe) {}
/* 843:    */     }
/* 844:877 */     return this;
/* 845:    */   }
/* 846:    */   
/* 847:    */   public Query setParameters(Object[] values, Type[] types)
/* 848:    */   {
/* 849:881 */     this.values = Arrays.asList(values);
/* 850:882 */     this.types = Arrays.asList(types);
/* 851:883 */     return this;
/* 852:    */   }
/* 853:    */   
/* 854:    */   public Object uniqueResult()
/* 855:    */     throws HibernateException
/* 856:    */   {
/* 857:890 */     return uniqueElement(list());
/* 858:    */   }
/* 859:    */   
/* 860:    */   static Object uniqueElement(List list)
/* 861:    */     throws NonUniqueResultException
/* 862:    */   {
/* 863:894 */     int size = list.size();
/* 864:895 */     if (size == 0) {
/* 865:895 */       return null;
/* 866:    */     }
/* 867:896 */     Object first = list.get(0);
/* 868:897 */     for (int i = 1; i < size; i++) {
/* 869:898 */       if (list.get(i) != first) {
/* 870:899 */         throw new NonUniqueResultException(list.size());
/* 871:    */       }
/* 872:    */     }
/* 873:902 */     return first;
/* 874:    */   }
/* 875:    */   
/* 876:    */   protected RowSelection getRowSelection()
/* 877:    */   {
/* 878:906 */     return this.selection;
/* 879:    */   }
/* 880:    */   
/* 881:    */   public Type[] typeArray()
/* 882:    */   {
/* 883:910 */     return ArrayHelper.toTypeArray(getTypes());
/* 884:    */   }
/* 885:    */   
/* 886:    */   public Object[] valueArray()
/* 887:    */   {
/* 888:914 */     return getValues().toArray();
/* 889:    */   }
/* 890:    */   
/* 891:    */   public QueryParameters getQueryParameters(Map namedParams)
/* 892:    */   {
/* 893:918 */     return new QueryParameters(typeArray(), valueArray(), namedParams, getLockOptions(), getSelection(), true, isReadOnly(), this.cacheable, this.cacheRegion, this.comment, new Serializable[] { this.collectionKey == null ? null : this.collectionKey }, this.optionalObject, this.optionalEntityName, this.optionalId, this.resultTransformer);
/* 894:    */   }
/* 895:    */   
/* 896:    */   protected void before()
/* 897:    */   {
/* 898:938 */     if (this.flushMode != null)
/* 899:    */     {
/* 900:939 */       this.sessionFlushMode = getSession().getFlushMode();
/* 901:940 */       getSession().setFlushMode(this.flushMode);
/* 902:    */     }
/* 903:942 */     if (this.cacheMode != null)
/* 904:    */     {
/* 905:943 */       this.sessionCacheMode = getSession().getCacheMode();
/* 906:944 */       getSession().setCacheMode(this.cacheMode);
/* 907:    */     }
/* 908:    */   }
/* 909:    */   
/* 910:    */   protected void after()
/* 911:    */   {
/* 912:949 */     if (this.sessionFlushMode != null)
/* 913:    */     {
/* 914:950 */       getSession().setFlushMode(this.sessionFlushMode);
/* 915:951 */       this.sessionFlushMode = null;
/* 916:    */     }
/* 917:953 */     if (this.sessionCacheMode != null)
/* 918:    */     {
/* 919:954 */       getSession().setCacheMode(this.sessionCacheMode);
/* 920:955 */       this.sessionCacheMode = null;
/* 921:    */     }
/* 922:    */   }
/* 923:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.AbstractQueryImpl
 * JD-Core Version:    0.7.0.1
 */