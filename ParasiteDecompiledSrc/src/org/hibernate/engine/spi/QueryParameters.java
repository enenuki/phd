/*   1:    */ package org.hibernate.engine.spi;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Collection;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ import java.util.StringTokenizer;
/*  10:    */ import org.hibernate.HibernateException;
/*  11:    */ import org.hibernate.LockOptions;
/*  12:    */ import org.hibernate.QueryException;
/*  13:    */ import org.hibernate.ScrollMode;
/*  14:    */ import org.hibernate.dialect.Dialect;
/*  15:    */ import org.hibernate.internal.CoreMessageLogger;
/*  16:    */ import org.hibernate.internal.FilterImpl;
/*  17:    */ import org.hibernate.internal.util.EntityPrinter;
/*  18:    */ import org.hibernate.internal.util.collections.ArrayHelper;
/*  19:    */ import org.hibernate.transform.ResultTransformer;
/*  20:    */ import org.hibernate.type.Type;
/*  21:    */ import org.jboss.logging.Logger;
/*  22:    */ 
/*  23:    */ public final class QueryParameters
/*  24:    */ {
/*  25: 54 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, QueryParameters.class.getName());
/*  26:    */   private Type[] positionalParameterTypes;
/*  27:    */   private Object[] positionalParameterValues;
/*  28:    */   private Map<String, TypedValue> namedParameters;
/*  29:    */   private LockOptions lockOptions;
/*  30:    */   private RowSelection rowSelection;
/*  31:    */   private boolean cacheable;
/*  32:    */   private String cacheRegion;
/*  33:    */   private String comment;
/*  34:    */   private ScrollMode scrollMode;
/*  35:    */   private Serializable[] collectionKeys;
/*  36:    */   private Object optionalObject;
/*  37:    */   private String optionalEntityName;
/*  38:    */   private Serializable optionalId;
/*  39:    */   private boolean isReadOnlyInitialized;
/*  40:    */   private boolean readOnly;
/*  41: 71 */   private boolean callable = false;
/*  42: 72 */   private boolean autodiscovertypes = false;
/*  43:    */   private boolean isNaturalKeyLookup;
/*  44:    */   private final ResultTransformer resultTransformer;
/*  45:    */   private String processedSQL;
/*  46:    */   private Type[] processedPositionalParameterTypes;
/*  47:    */   private Object[] processedPositionalParameterValues;
/*  48:    */   
/*  49:    */   public QueryParameters()
/*  50:    */   {
/*  51: 82 */     this(ArrayHelper.EMPTY_TYPE_ARRAY, ArrayHelper.EMPTY_OBJECT_ARRAY);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public QueryParameters(Type type, Object value)
/*  55:    */   {
/*  56: 86 */     this(new Type[] { type }, new Object[] { value });
/*  57:    */   }
/*  58:    */   
/*  59:    */   public QueryParameters(Type[] positionalParameterTypes, Object[] positionalParameterValues, Object optionalObject, String optionalEntityName, Serializable optionalObjectId)
/*  60:    */   {
/*  61: 95 */     this(positionalParameterTypes, positionalParameterValues);
/*  62: 96 */     this.optionalObject = optionalObject;
/*  63: 97 */     this.optionalId = optionalObjectId;
/*  64: 98 */     this.optionalEntityName = optionalEntityName;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public QueryParameters(Type[] positionalParameterTypes, Object[] positionalParameterValues)
/*  68:    */   {
/*  69:105 */     this(positionalParameterTypes, positionalParameterValues, null, null, false, false, false, null, null, false, null);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public QueryParameters(Type[] positionalParameterTypes, Object[] positionalParameterValues, Serializable[] collectionKeys)
/*  73:    */   {
/*  74:112 */     this(positionalParameterTypes, positionalParameterValues, null, collectionKeys);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public QueryParameters(Type[] positionalParameterTypes, Object[] positionalParameterValues, Map<String, TypedValue> namedParameters, Serializable[] collectionKeys)
/*  78:    */   {
/*  79:120 */     this(positionalParameterTypes, positionalParameterValues, namedParameters, null, null, false, false, false, null, null, collectionKeys, null);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public QueryParameters(Type[] positionalParameterTypes, Object[] positionalParameterValues, LockOptions lockOptions, RowSelection rowSelection, boolean isReadOnlyInitialized, boolean readOnly, boolean cacheable, String cacheRegion, String comment, boolean isLookupByNaturalKey, ResultTransformer transformer)
/*  83:    */   {
/*  84:149 */     this(positionalParameterTypes, positionalParameterValues, null, lockOptions, rowSelection, isReadOnlyInitialized, readOnly, cacheable, cacheRegion, comment, null, transformer);
/*  85:    */     
/*  86:    */ 
/*  87:    */ 
/*  88:    */ 
/*  89:    */ 
/*  90:    */ 
/*  91:    */ 
/*  92:    */ 
/*  93:    */ 
/*  94:    */ 
/*  95:    */ 
/*  96:    */ 
/*  97:    */ 
/*  98:163 */     this.isNaturalKeyLookup = isLookupByNaturalKey;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public QueryParameters(Type[] positionalParameterTypes, Object[] positionalParameterValues, Map<String, TypedValue> namedParameters, LockOptions lockOptions, RowSelection rowSelection, boolean isReadOnlyInitialized, boolean readOnly, boolean cacheable, String cacheRegion, String comment, Serializable[] collectionKeys, ResultTransformer transformer)
/* 102:    */   {
/* 103:180 */     this.positionalParameterTypes = positionalParameterTypes;
/* 104:181 */     this.positionalParameterValues = positionalParameterValues;
/* 105:182 */     this.namedParameters = namedParameters;
/* 106:183 */     this.lockOptions = lockOptions;
/* 107:184 */     this.rowSelection = rowSelection;
/* 108:185 */     this.cacheable = cacheable;
/* 109:186 */     this.cacheRegion = cacheRegion;
/* 110:    */     
/* 111:188 */     this.comment = comment;
/* 112:189 */     this.collectionKeys = collectionKeys;
/* 113:190 */     this.isReadOnlyInitialized = isReadOnlyInitialized;
/* 114:191 */     this.readOnly = readOnly;
/* 115:192 */     this.resultTransformer = transformer;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public QueryParameters(Type[] positionalParameterTypes, Object[] positionalParameterValues, Map<String, TypedValue> namedParameters, LockOptions lockOptions, RowSelection rowSelection, boolean isReadOnlyInitialized, boolean readOnly, boolean cacheable, String cacheRegion, String comment, Serializable[] collectionKeys, Object optionalObject, String optionalEntityName, Serializable optionalId, ResultTransformer transformer)
/* 119:    */   {
/* 120:212 */     this(positionalParameterTypes, positionalParameterValues, namedParameters, lockOptions, rowSelection, isReadOnlyInitialized, readOnly, cacheable, cacheRegion, comment, collectionKeys, transformer);
/* 121:    */     
/* 122:    */ 
/* 123:    */ 
/* 124:    */ 
/* 125:    */ 
/* 126:    */ 
/* 127:    */ 
/* 128:    */ 
/* 129:    */ 
/* 130:    */ 
/* 131:    */ 
/* 132:    */ 
/* 133:    */ 
/* 134:226 */     this.optionalEntityName = optionalEntityName;
/* 135:227 */     this.optionalId = optionalId;
/* 136:228 */     this.optionalObject = optionalObject;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public boolean hasRowSelection()
/* 140:    */   {
/* 141:232 */     return this.rowSelection != null;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public Map<String, TypedValue> getNamedParameters()
/* 145:    */   {
/* 146:236 */     return this.namedParameters;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public Type[] getPositionalParameterTypes()
/* 150:    */   {
/* 151:240 */     return this.positionalParameterTypes;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public Object[] getPositionalParameterValues()
/* 155:    */   {
/* 156:244 */     return this.positionalParameterValues;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public RowSelection getRowSelection()
/* 160:    */   {
/* 161:248 */     return this.rowSelection;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public ResultTransformer getResultTransformer()
/* 165:    */   {
/* 166:252 */     return this.resultTransformer;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void setNamedParameters(Map<String, TypedValue> map)
/* 170:    */   {
/* 171:256 */     this.namedParameters = map;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void setPositionalParameterTypes(Type[] types)
/* 175:    */   {
/* 176:260 */     this.positionalParameterTypes = types;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public void setPositionalParameterValues(Object[] objects)
/* 180:    */   {
/* 181:264 */     this.positionalParameterValues = objects;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public void setRowSelection(RowSelection selection)
/* 185:    */   {
/* 186:268 */     this.rowSelection = selection;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public LockOptions getLockOptions()
/* 190:    */   {
/* 191:272 */     return this.lockOptions;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public void setLockOptions(LockOptions lockOptions)
/* 195:    */   {
/* 196:276 */     this.lockOptions = lockOptions;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void traceParameters(SessionFactoryImplementor factory)
/* 200:    */     throws HibernateException
/* 201:    */   {
/* 202:280 */     EntityPrinter print = new EntityPrinter(factory);
/* 203:281 */     if (this.positionalParameterValues.length != 0) {
/* 204:282 */       LOG.tracev("Parameters: {0}", print.toString(this.positionalParameterTypes, this.positionalParameterValues));
/* 205:    */     }
/* 206:284 */     if (this.namedParameters != null) {
/* 207:285 */       LOG.tracev("Named parameters: {0}", print.toString(this.namedParameters));
/* 208:    */     }
/* 209:    */   }
/* 210:    */   
/* 211:    */   public boolean isCacheable()
/* 212:    */   {
/* 213:290 */     return this.cacheable;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public void setCacheable(boolean b)
/* 217:    */   {
/* 218:294 */     this.cacheable = b;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public String getCacheRegion()
/* 222:    */   {
/* 223:298 */     return this.cacheRegion;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public void setCacheRegion(String cacheRegion)
/* 227:    */   {
/* 228:302 */     this.cacheRegion = cacheRegion;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public void validateParameters()
/* 232:    */     throws QueryException
/* 233:    */   {
/* 234:306 */     int types = this.positionalParameterTypes == null ? 0 : this.positionalParameterTypes.length;
/* 235:307 */     int values = this.positionalParameterValues == null ? 0 : this.positionalParameterValues.length;
/* 236:308 */     if (types != values) {
/* 237:309 */       throw new QueryException("Number of positional parameter types:" + types + " does not match number of positional parameters: " + values);
/* 238:    */     }
/* 239:    */   }
/* 240:    */   
/* 241:    */   public String getComment()
/* 242:    */   {
/* 243:317 */     return this.comment;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public void setComment(String comment)
/* 247:    */   {
/* 248:321 */     this.comment = comment;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public ScrollMode getScrollMode()
/* 252:    */   {
/* 253:325 */     return this.scrollMode;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public void setScrollMode(ScrollMode scrollMode)
/* 257:    */   {
/* 258:329 */     this.scrollMode = scrollMode;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public Serializable[] getCollectionKeys()
/* 262:    */   {
/* 263:333 */     return this.collectionKeys;
/* 264:    */   }
/* 265:    */   
/* 266:    */   public void setCollectionKeys(Serializable[] collectionKeys)
/* 267:    */   {
/* 268:337 */     this.collectionKeys = collectionKeys;
/* 269:    */   }
/* 270:    */   
/* 271:    */   public String getOptionalEntityName()
/* 272:    */   {
/* 273:341 */     return this.optionalEntityName;
/* 274:    */   }
/* 275:    */   
/* 276:    */   public void setOptionalEntityName(String optionalEntityName)
/* 277:    */   {
/* 278:345 */     this.optionalEntityName = optionalEntityName;
/* 279:    */   }
/* 280:    */   
/* 281:    */   public Serializable getOptionalId()
/* 282:    */   {
/* 283:349 */     return this.optionalId;
/* 284:    */   }
/* 285:    */   
/* 286:    */   public void setOptionalId(Serializable optionalId)
/* 287:    */   {
/* 288:353 */     this.optionalId = optionalId;
/* 289:    */   }
/* 290:    */   
/* 291:    */   public Object getOptionalObject()
/* 292:    */   {
/* 293:357 */     return this.optionalObject;
/* 294:    */   }
/* 295:    */   
/* 296:    */   public void setOptionalObject(Object optionalObject)
/* 297:    */   {
/* 298:361 */     this.optionalObject = optionalObject;
/* 299:    */   }
/* 300:    */   
/* 301:    */   public boolean isReadOnlyInitialized()
/* 302:    */   {
/* 303:373 */     return this.isReadOnlyInitialized;
/* 304:    */   }
/* 305:    */   
/* 306:    */   public boolean isReadOnly()
/* 307:    */   {
/* 308:394 */     if (!isReadOnlyInitialized()) {
/* 309:395 */       throw new IllegalStateException("cannot call isReadOnly() when isReadOnlyInitialized() returns false");
/* 310:    */     }
/* 311:397 */     return this.readOnly;
/* 312:    */   }
/* 313:    */   
/* 314:    */   public boolean isReadOnly(SessionImplementor session)
/* 315:    */   {
/* 316:417 */     return this.isReadOnlyInitialized ? isReadOnly() : session.getPersistenceContext().isDefaultReadOnly();
/* 317:    */   }
/* 318:    */   
/* 319:    */   public void setReadOnly(boolean readOnly)
/* 320:    */   {
/* 321:438 */     this.readOnly = readOnly;
/* 322:439 */     this.isReadOnlyInitialized = true;
/* 323:    */   }
/* 324:    */   
/* 325:    */   public void setCallable(boolean callable)
/* 326:    */   {
/* 327:443 */     this.callable = callable;
/* 328:    */   }
/* 329:    */   
/* 330:    */   public boolean isCallable()
/* 331:    */   {
/* 332:447 */     return this.callable;
/* 333:    */   }
/* 334:    */   
/* 335:    */   public boolean hasAutoDiscoverScalarTypes()
/* 336:    */   {
/* 337:451 */     return this.autodiscovertypes;
/* 338:    */   }
/* 339:    */   
/* 340:    */   public void processFilters(String sql, SessionImplementor session)
/* 341:    */   {
/* 342:455 */     processFilters(sql, session.getLoadQueryInfluencers().getEnabledFilters(), session.getFactory());
/* 343:    */   }
/* 344:    */   
/* 345:    */   public void processFilters(String sql, Map filters, SessionFactoryImplementor factory)
/* 346:    */   {
/* 347:459 */     if ((filters.size() == 0) || (sql.indexOf(":") < 0))
/* 348:    */     {
/* 349:461 */       this.processedPositionalParameterValues = getPositionalParameterValues();
/* 350:462 */       this.processedPositionalParameterTypes = getPositionalParameterTypes();
/* 351:463 */       this.processedSQL = sql;
/* 352:    */     }
/* 353:    */     else
/* 354:    */     {
/* 355:466 */       Dialect dialect = factory.getDialect();
/* 356:467 */       String symbols = " \n\r\f\t,()=<>&|+-=/*'^![]#~\\" + dialect.openQuote() + dialect.closeQuote();
/* 357:    */       
/* 358:    */ 
/* 359:    */ 
/* 360:471 */       StringTokenizer tokens = new StringTokenizer(sql, symbols, true);
/* 361:472 */       StringBuffer result = new StringBuffer();
/* 362:    */       
/* 363:474 */       List parameters = new ArrayList();
/* 364:475 */       List parameterTypes = new ArrayList();
/* 365:    */       
/* 366:477 */       int positionalIndex = 0;
/* 367:478 */       while (tokens.hasMoreTokens())
/* 368:    */       {
/* 369:479 */         String token = tokens.nextToken();
/* 370:480 */         if (token.startsWith(":"))
/* 371:    */         {
/* 372:481 */           String filterParameterName = token.substring(1);
/* 373:482 */           String[] parts = LoadQueryInfluencers.parseFilterParameterName(filterParameterName);
/* 374:483 */           FilterImpl filter = (FilterImpl)filters.get(parts[0]);
/* 375:484 */           Object value = filter.getParameter(parts[1]);
/* 376:485 */           Type type = filter.getFilterDefinition().getParameterType(parts[1]);
/* 377:486 */           if ((value != null) && (Collection.class.isAssignableFrom(value.getClass())))
/* 378:    */           {
/* 379:487 */             Iterator itr = ((Collection)value).iterator();
/* 380:488 */             while (itr.hasNext())
/* 381:    */             {
/* 382:489 */               Object elementValue = itr.next();
/* 383:490 */               result.append('?');
/* 384:491 */               parameters.add(elementValue);
/* 385:492 */               parameterTypes.add(type);
/* 386:493 */               if (itr.hasNext()) {
/* 387:494 */                 result.append(", ");
/* 388:    */               }
/* 389:    */             }
/* 390:    */           }
/* 391:    */           else
/* 392:    */           {
/* 393:499 */             result.append('?');
/* 394:500 */             parameters.add(value);
/* 395:501 */             parameterTypes.add(type);
/* 396:    */           }
/* 397:    */         }
/* 398:    */         else
/* 399:    */         {
/* 400:505 */           if (("?".equals(token)) && (positionalIndex < getPositionalParameterValues().length))
/* 401:    */           {
/* 402:506 */             parameters.add(getPositionalParameterValues()[positionalIndex]);
/* 403:507 */             parameterTypes.add(getPositionalParameterTypes()[positionalIndex]);
/* 404:508 */             positionalIndex++;
/* 405:    */           }
/* 406:510 */           result.append(token);
/* 407:    */         }
/* 408:    */       }
/* 409:513 */       this.processedPositionalParameterValues = parameters.toArray();
/* 410:514 */       this.processedPositionalParameterTypes = ((Type[])parameterTypes.toArray(new Type[parameterTypes.size()]));
/* 411:515 */       this.processedSQL = result.toString();
/* 412:    */     }
/* 413:    */   }
/* 414:    */   
/* 415:    */   public String getFilteredSQL()
/* 416:    */   {
/* 417:520 */     return this.processedSQL;
/* 418:    */   }
/* 419:    */   
/* 420:    */   public Object[] getFilteredPositionalParameterValues()
/* 421:    */   {
/* 422:524 */     return this.processedPositionalParameterValues;
/* 423:    */   }
/* 424:    */   
/* 425:    */   public Type[] getFilteredPositionalParameterTypes()
/* 426:    */   {
/* 427:528 */     return this.processedPositionalParameterTypes;
/* 428:    */   }
/* 429:    */   
/* 430:    */   public boolean isNaturalKeyLookup()
/* 431:    */   {
/* 432:532 */     return this.isNaturalKeyLookup;
/* 433:    */   }
/* 434:    */   
/* 435:    */   public void setNaturalKeyLookup(boolean isNaturalKeyLookup)
/* 436:    */   {
/* 437:536 */     this.isNaturalKeyLookup = isNaturalKeyLookup;
/* 438:    */   }
/* 439:    */   
/* 440:    */   public void setAutoDiscoverScalarTypes(boolean autodiscovertypes)
/* 441:    */   {
/* 442:540 */     this.autodiscovertypes = autodiscovertypes;
/* 443:    */   }
/* 444:    */   
/* 445:    */   public QueryParameters createCopyUsing(RowSelection selection)
/* 446:    */   {
/* 447:544 */     QueryParameters copy = new QueryParameters(this.positionalParameterTypes, this.positionalParameterValues, this.namedParameters, this.lockOptions, selection, this.isReadOnlyInitialized, this.readOnly, this.cacheable, this.cacheRegion, this.comment, this.collectionKeys, this.optionalObject, this.optionalEntityName, this.optionalId, this.resultTransformer);
/* 448:    */     
/* 449:    */ 
/* 450:    */ 
/* 451:    */ 
/* 452:    */ 
/* 453:    */ 
/* 454:    */ 
/* 455:    */ 
/* 456:    */ 
/* 457:    */ 
/* 458:    */ 
/* 459:    */ 
/* 460:    */ 
/* 461:    */ 
/* 462:    */ 
/* 463:    */ 
/* 464:561 */     copy.processedSQL = this.processedSQL;
/* 465:562 */     copy.processedPositionalParameterTypes = this.processedPositionalParameterTypes;
/* 466:563 */     copy.processedPositionalParameterValues = this.processedPositionalParameterValues;
/* 467:564 */     return copy;
/* 468:    */   }
/* 469:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.spi.QueryParameters
 * JD-Core Version:    0.7.0.1
 */