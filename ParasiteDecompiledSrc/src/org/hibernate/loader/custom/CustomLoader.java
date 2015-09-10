/*   1:    */ package org.hibernate.loader.custom;
/*   2:    */ 
/*   3:    */ import java.sql.ResultSet;
/*   4:    */ import java.sql.ResultSetMetaData;
/*   5:    */ import java.sql.SQLException;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.HashSet;
/*   8:    */ import java.util.Iterator;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;
/*  11:    */ import java.util.Set;
/*  12:    */ import org.hibernate.HibernateException;
/*  13:    */ import org.hibernate.LockMode;
/*  14:    */ import org.hibernate.LockOptions;
/*  15:    */ import org.hibernate.QueryException;
/*  16:    */ import org.hibernate.ScrollableResults;
/*  17:    */ import org.hibernate.dialect.ColumnAliasExtractor;
/*  18:    */ import org.hibernate.dialect.Dialect;
/*  19:    */ import org.hibernate.engine.spi.QueryParameters;
/*  20:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  21:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  22:    */ import org.hibernate.hql.internal.HolderInstantiator;
/*  23:    */ import org.hibernate.internal.util.StringHelper;
/*  24:    */ import org.hibernate.internal.util.collections.ArrayHelper;
/*  25:    */ import org.hibernate.loader.CollectionAliases;
/*  26:    */ import org.hibernate.loader.EntityAliases;
/*  27:    */ import org.hibernate.loader.Loader;
/*  28:    */ import org.hibernate.persister.collection.CollectionPersister;
/*  29:    */ import org.hibernate.persister.collection.QueryableCollection;
/*  30:    */ import org.hibernate.persister.entity.Loadable;
/*  31:    */ import org.hibernate.persister.entity.Queryable;
/*  32:    */ import org.hibernate.transform.ResultTransformer;
/*  33:    */ import org.hibernate.type.CollectionType;
/*  34:    */ import org.hibernate.type.EntityType;
/*  35:    */ import org.hibernate.type.Type;
/*  36:    */ import org.hibernate.type.TypeResolver;
/*  37:    */ 
/*  38:    */ public class CustomLoader
/*  39:    */   extends Loader
/*  40:    */ {
/*  41:    */   private final String sql;
/*  42: 71 */   private final Set querySpaces = new HashSet();
/*  43:    */   private final Map namedParameterBindPoints;
/*  44:    */   private final Queryable[] entityPersisters;
/*  45:    */   private final int[] entiytOwners;
/*  46:    */   private final EntityAliases[] entityAliases;
/*  47:    */   private final QueryableCollection[] collectionPersisters;
/*  48:    */   private final int[] collectionOwners;
/*  49:    */   private final CollectionAliases[] collectionAliases;
/*  50:    */   private final LockMode[] lockModes;
/*  51:    */   private boolean[] includeInResultRow;
/*  52:    */   private final ResultRowProcessor rowProcessor;
/*  53:    */   private Type[] resultTypes;
/*  54:    */   private String[] transformerAliases;
/*  55:    */   
/*  56:    */   public CustomLoader(CustomQuery customQuery, SessionFactoryImplementor factory)
/*  57:    */   {
/*  58: 98 */     super(factory);
/*  59:    */     
/*  60:100 */     this.sql = customQuery.getSQL();
/*  61:101 */     this.querySpaces.addAll(customQuery.getQuerySpaces());
/*  62:102 */     this.namedParameterBindPoints = customQuery.getNamedParameterBindPoints();
/*  63:    */     
/*  64:104 */     List entityPersisters = new ArrayList();
/*  65:105 */     List entityOwners = new ArrayList();
/*  66:106 */     List entityAliases = new ArrayList();
/*  67:    */     
/*  68:108 */     List collectionPersisters = new ArrayList();
/*  69:109 */     List collectionOwners = new ArrayList();
/*  70:110 */     List collectionAliases = new ArrayList();
/*  71:    */     
/*  72:112 */     List lockModes = new ArrayList();
/*  73:113 */     List resultColumnProcessors = new ArrayList();
/*  74:114 */     List nonScalarReturnList = new ArrayList();
/*  75:115 */     List resultTypes = new ArrayList();
/*  76:116 */     List specifiedAliases = new ArrayList();
/*  77:117 */     int returnableCounter = 0;
/*  78:118 */     boolean hasScalars = false;
/*  79:    */     
/*  80:120 */     List includeInResultRowList = new ArrayList();
/*  81:    */     
/*  82:122 */     Iterator itr = customQuery.getCustomQueryReturns().iterator();
/*  83:123 */     while (itr.hasNext())
/*  84:    */     {
/*  85:124 */       Return rtn = (Return)itr.next();
/*  86:125 */       if ((rtn instanceof ScalarReturn))
/*  87:    */       {
/*  88:126 */         ScalarReturn scalarRtn = (ScalarReturn)rtn;
/*  89:127 */         resultTypes.add(scalarRtn.getType());
/*  90:128 */         specifiedAliases.add(scalarRtn.getColumnAlias());
/*  91:129 */         resultColumnProcessors.add(new ScalarResultColumnProcessor(StringHelper.unquote(scalarRtn.getColumnAlias(), factory.getDialect()), scalarRtn.getType()));
/*  92:    */         
/*  93:    */ 
/*  94:    */ 
/*  95:    */ 
/*  96:    */ 
/*  97:135 */         includeInResultRowList.add(Boolean.valueOf(true));
/*  98:136 */         hasScalars = true;
/*  99:    */       }
/* 100:138 */       else if ((rtn instanceof RootReturn))
/* 101:    */       {
/* 102:139 */         RootReturn rootRtn = (RootReturn)rtn;
/* 103:140 */         Queryable persister = (Queryable)factory.getEntityPersister(rootRtn.getEntityName());
/* 104:141 */         entityPersisters.add(persister);
/* 105:142 */         lockModes.add(rootRtn.getLockMode());
/* 106:143 */         resultColumnProcessors.add(new NonScalarResultColumnProcessor(returnableCounter++));
/* 107:144 */         nonScalarReturnList.add(rtn);
/* 108:145 */         entityOwners.add(Integer.valueOf(-1));
/* 109:146 */         resultTypes.add(persister.getType());
/* 110:147 */         specifiedAliases.add(rootRtn.getAlias());
/* 111:148 */         entityAliases.add(rootRtn.getEntityAliases());
/* 112:149 */         ArrayHelper.addAll(this.querySpaces, persister.getQuerySpaces());
/* 113:150 */         includeInResultRowList.add(Boolean.valueOf(true));
/* 114:    */       }
/* 115:152 */       else if ((rtn instanceof CollectionReturn))
/* 116:    */       {
/* 117:153 */         CollectionReturn collRtn = (CollectionReturn)rtn;
/* 118:154 */         String role = collRtn.getOwnerEntityName() + "." + collRtn.getOwnerProperty();
/* 119:155 */         QueryableCollection persister = (QueryableCollection)factory.getCollectionPersister(role);
/* 120:156 */         collectionPersisters.add(persister);
/* 121:157 */         lockModes.add(collRtn.getLockMode());
/* 122:158 */         resultColumnProcessors.add(new NonScalarResultColumnProcessor(returnableCounter++));
/* 123:159 */         nonScalarReturnList.add(rtn);
/* 124:160 */         collectionOwners.add(Integer.valueOf(-1));
/* 125:161 */         resultTypes.add(persister.getType());
/* 126:162 */         specifiedAliases.add(collRtn.getAlias());
/* 127:163 */         collectionAliases.add(collRtn.getCollectionAliases());
/* 128:    */         
/* 129:165 */         Type elementType = persister.getElementType();
/* 130:166 */         if (elementType.isEntityType())
/* 131:    */         {
/* 132:167 */           Queryable elementPersister = (Queryable)((EntityType)elementType).getAssociatedJoinable(factory);
/* 133:168 */           entityPersisters.add(elementPersister);
/* 134:169 */           entityOwners.add(Integer.valueOf(-1));
/* 135:170 */           entityAliases.add(collRtn.getElementEntityAliases());
/* 136:171 */           ArrayHelper.addAll(this.querySpaces, elementPersister.getQuerySpaces());
/* 137:    */         }
/* 138:173 */         includeInResultRowList.add(Boolean.valueOf(true));
/* 139:    */       }
/* 140:175 */       else if ((rtn instanceof EntityFetchReturn))
/* 141:    */       {
/* 142:176 */         EntityFetchReturn fetchRtn = (EntityFetchReturn)rtn;
/* 143:177 */         NonScalarReturn ownerDescriptor = fetchRtn.getOwner();
/* 144:178 */         int ownerIndex = nonScalarReturnList.indexOf(ownerDescriptor);
/* 145:179 */         entityOwners.add(Integer.valueOf(ownerIndex));
/* 146:180 */         lockModes.add(fetchRtn.getLockMode());
/* 147:181 */         Queryable ownerPersister = determineAppropriateOwnerPersister(ownerDescriptor);
/* 148:182 */         EntityType fetchedType = (EntityType)ownerPersister.getPropertyType(fetchRtn.getOwnerProperty());
/* 149:183 */         String entityName = fetchedType.getAssociatedEntityName(getFactory());
/* 150:184 */         Queryable persister = (Queryable)factory.getEntityPersister(entityName);
/* 151:185 */         entityPersisters.add(persister);
/* 152:186 */         nonScalarReturnList.add(rtn);
/* 153:187 */         specifiedAliases.add(fetchRtn.getAlias());
/* 154:188 */         entityAliases.add(fetchRtn.getEntityAliases());
/* 155:189 */         ArrayHelper.addAll(this.querySpaces, persister.getQuerySpaces());
/* 156:190 */         includeInResultRowList.add(Boolean.valueOf(false));
/* 157:    */       }
/* 158:192 */       else if ((rtn instanceof CollectionFetchReturn))
/* 159:    */       {
/* 160:193 */         CollectionFetchReturn fetchRtn = (CollectionFetchReturn)rtn;
/* 161:194 */         NonScalarReturn ownerDescriptor = fetchRtn.getOwner();
/* 162:195 */         int ownerIndex = nonScalarReturnList.indexOf(ownerDescriptor);
/* 163:196 */         collectionOwners.add(Integer.valueOf(ownerIndex));
/* 164:197 */         lockModes.add(fetchRtn.getLockMode());
/* 165:198 */         Queryable ownerPersister = determineAppropriateOwnerPersister(ownerDescriptor);
/* 166:199 */         String role = ownerPersister.getEntityName() + '.' + fetchRtn.getOwnerProperty();
/* 167:200 */         QueryableCollection persister = (QueryableCollection)factory.getCollectionPersister(role);
/* 168:201 */         collectionPersisters.add(persister);
/* 169:202 */         nonScalarReturnList.add(rtn);
/* 170:203 */         specifiedAliases.add(fetchRtn.getAlias());
/* 171:204 */         collectionAliases.add(fetchRtn.getCollectionAliases());
/* 172:    */         
/* 173:206 */         Type elementType = persister.getElementType();
/* 174:207 */         if (elementType.isEntityType())
/* 175:    */         {
/* 176:208 */           Queryable elementPersister = (Queryable)((EntityType)elementType).getAssociatedJoinable(factory);
/* 177:209 */           entityPersisters.add(elementPersister);
/* 178:210 */           entityOwners.add(Integer.valueOf(ownerIndex));
/* 179:211 */           entityAliases.add(fetchRtn.getElementEntityAliases());
/* 180:212 */           ArrayHelper.addAll(this.querySpaces, elementPersister.getQuerySpaces());
/* 181:    */         }
/* 182:214 */         includeInResultRowList.add(Boolean.valueOf(false));
/* 183:    */       }
/* 184:    */       else
/* 185:    */       {
/* 186:217 */         throw new HibernateException("unexpected custom query return type : " + rtn.getClass().getName());
/* 187:    */       }
/* 188:    */     }
/* 189:221 */     this.entityPersisters = new Queryable[entityPersisters.size()];
/* 190:222 */     for (int i = 0; i < entityPersisters.size(); i++) {
/* 191:223 */       this.entityPersisters[i] = ((Queryable)entityPersisters.get(i));
/* 192:    */     }
/* 193:225 */     this.entiytOwners = ArrayHelper.toIntArray(entityOwners);
/* 194:226 */     this.entityAliases = new EntityAliases[entityAliases.size()];
/* 195:227 */     for (int i = 0; i < entityAliases.size(); i++) {
/* 196:228 */       this.entityAliases[i] = ((EntityAliases)entityAliases.get(i));
/* 197:    */     }
/* 198:231 */     this.collectionPersisters = new QueryableCollection[collectionPersisters.size()];
/* 199:232 */     for (int i = 0; i < collectionPersisters.size(); i++) {
/* 200:233 */       this.collectionPersisters[i] = ((QueryableCollection)collectionPersisters.get(i));
/* 201:    */     }
/* 202:235 */     this.collectionOwners = ArrayHelper.toIntArray(collectionOwners);
/* 203:236 */     this.collectionAliases = new CollectionAliases[collectionAliases.size()];
/* 204:237 */     for (int i = 0; i < collectionAliases.size(); i++) {
/* 205:238 */       this.collectionAliases[i] = ((CollectionAliases)collectionAliases.get(i));
/* 206:    */     }
/* 207:241 */     this.lockModes = new LockMode[lockModes.size()];
/* 208:242 */     for (int i = 0; i < lockModes.size(); i++) {
/* 209:243 */       this.lockModes[i] = ((LockMode)lockModes.get(i));
/* 210:    */     }
/* 211:246 */     this.resultTypes = ArrayHelper.toTypeArray(resultTypes);
/* 212:247 */     this.transformerAliases = ArrayHelper.toStringArray(specifiedAliases);
/* 213:    */     
/* 214:249 */     this.rowProcessor = new ResultRowProcessor(hasScalars, (ResultColumnProcessor[])resultColumnProcessors.toArray(new ResultColumnProcessor[resultColumnProcessors.size()]));
/* 215:    */     
/* 216:    */ 
/* 217:    */ 
/* 218:    */ 
/* 219:254 */     this.includeInResultRow = ArrayHelper.toBooleanArray(includeInResultRowList);
/* 220:    */   }
/* 221:    */   
/* 222:    */   private Queryable determineAppropriateOwnerPersister(NonScalarReturn ownerDescriptor)
/* 223:    */   {
/* 224:258 */     String entityName = null;
/* 225:259 */     if ((ownerDescriptor instanceof RootReturn))
/* 226:    */     {
/* 227:260 */       entityName = ((RootReturn)ownerDescriptor).getEntityName();
/* 228:    */     }
/* 229:262 */     else if ((ownerDescriptor instanceof CollectionReturn))
/* 230:    */     {
/* 231:263 */       CollectionReturn collRtn = (CollectionReturn)ownerDescriptor;
/* 232:264 */       String role = collRtn.getOwnerEntityName() + "." + collRtn.getOwnerProperty();
/* 233:265 */       CollectionPersister persister = getFactory().getCollectionPersister(role);
/* 234:266 */       EntityType ownerType = (EntityType)persister.getElementType();
/* 235:267 */       entityName = ownerType.getAssociatedEntityName(getFactory());
/* 236:    */     }
/* 237:269 */     else if ((ownerDescriptor instanceof FetchReturn))
/* 238:    */     {
/* 239:270 */       FetchReturn fetchRtn = (FetchReturn)ownerDescriptor;
/* 240:271 */       Queryable persister = determineAppropriateOwnerPersister(fetchRtn.getOwner());
/* 241:272 */       Type ownerType = persister.getPropertyType(fetchRtn.getOwnerProperty());
/* 242:273 */       if (ownerType.isEntityType())
/* 243:    */       {
/* 244:274 */         entityName = ((EntityType)ownerType).getAssociatedEntityName(getFactory());
/* 245:    */       }
/* 246:276 */       else if (ownerType.isCollectionType())
/* 247:    */       {
/* 248:277 */         Type ownerCollectionElementType = ((CollectionType)ownerType).getElementType(getFactory());
/* 249:278 */         if (ownerCollectionElementType.isEntityType()) {
/* 250:279 */           entityName = ((EntityType)ownerCollectionElementType).getAssociatedEntityName(getFactory());
/* 251:    */         }
/* 252:    */       }
/* 253:    */     }
/* 254:284 */     if (entityName == null) {
/* 255:285 */       throw new HibernateException("Could not determine fetch owner : " + ownerDescriptor);
/* 256:    */     }
/* 257:288 */     return (Queryable)getFactory().getEntityPersister(entityName);
/* 258:    */   }
/* 259:    */   
/* 260:    */   protected String getQueryIdentifier()
/* 261:    */   {
/* 262:293 */     return this.sql;
/* 263:    */   }
/* 264:    */   
/* 265:    */   protected String getSQLString()
/* 266:    */   {
/* 267:298 */     return this.sql;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public Set getQuerySpaces()
/* 271:    */   {
/* 272:302 */     return this.querySpaces;
/* 273:    */   }
/* 274:    */   
/* 275:    */   protected LockMode[] getLockModes(LockOptions lockOptions)
/* 276:    */   {
/* 277:307 */     return this.lockModes;
/* 278:    */   }
/* 279:    */   
/* 280:    */   protected Loadable[] getEntityPersisters()
/* 281:    */   {
/* 282:312 */     return this.entityPersisters;
/* 283:    */   }
/* 284:    */   
/* 285:    */   protected CollectionPersister[] getCollectionPersisters()
/* 286:    */   {
/* 287:317 */     return this.collectionPersisters;
/* 288:    */   }
/* 289:    */   
/* 290:    */   protected int[] getCollectionOwners()
/* 291:    */   {
/* 292:322 */     return this.collectionOwners;
/* 293:    */   }
/* 294:    */   
/* 295:    */   protected int[] getOwners()
/* 296:    */   {
/* 297:327 */     return this.entiytOwners;
/* 298:    */   }
/* 299:    */   
/* 300:    */   public List list(SessionImplementor session, QueryParameters queryParameters)
/* 301:    */     throws HibernateException
/* 302:    */   {
/* 303:331 */     return list(session, queryParameters, this.querySpaces, this.resultTypes);
/* 304:    */   }
/* 305:    */   
/* 306:    */   public ScrollableResults scroll(QueryParameters queryParameters, SessionImplementor session)
/* 307:    */     throws HibernateException
/* 308:    */   {
/* 309:337 */     return scroll(queryParameters, this.resultTypes, getHolderInstantiator(queryParameters.getResultTransformer(), getReturnAliasesForTransformer()), session);
/* 310:    */   }
/* 311:    */   
/* 312:    */   private static HolderInstantiator getHolderInstantiator(ResultTransformer resultTransformer, String[] queryReturnAliases)
/* 313:    */   {
/* 314:346 */     if (resultTransformer == null) {
/* 315:347 */       return HolderInstantiator.NOOP_INSTANTIATOR;
/* 316:    */     }
/* 317:350 */     return new HolderInstantiator(resultTransformer, queryReturnAliases);
/* 318:    */   }
/* 319:    */   
/* 320:    */   protected String[] getResultRowAliases()
/* 321:    */   {
/* 322:356 */     return this.transformerAliases;
/* 323:    */   }
/* 324:    */   
/* 325:    */   protected ResultTransformer resolveResultTransformer(ResultTransformer resultTransformer)
/* 326:    */   {
/* 327:361 */     return HolderInstantiator.resolveResultTransformer(null, resultTransformer);
/* 328:    */   }
/* 329:    */   
/* 330:    */   protected boolean[] includeInResultRow()
/* 331:    */   {
/* 332:366 */     return this.includeInResultRow;
/* 333:    */   }
/* 334:    */   
/* 335:    */   protected Object getResultColumnOrRow(Object[] row, ResultTransformer transformer, ResultSet rs, SessionImplementor session)
/* 336:    */     throws SQLException, HibernateException
/* 337:    */   {
/* 338:375 */     return this.rowProcessor.buildResultRow(row, rs, transformer != null, session);
/* 339:    */   }
/* 340:    */   
/* 341:    */   protected Object[] getResultRow(Object[] row, ResultSet rs, SessionImplementor session)
/* 342:    */     throws SQLException, HibernateException
/* 343:    */   {
/* 344:381 */     return this.rowProcessor.buildResultRow(row, rs, session);
/* 345:    */   }
/* 346:    */   
/* 347:    */   protected List getResultList(List results, ResultTransformer resultTransformer)
/* 348:    */     throws QueryException
/* 349:    */   {
/* 350:387 */     HolderInstantiator holderInstantiator = HolderInstantiator.getHolderInstantiator(null, resultTransformer, getReturnAliasesForTransformer());
/* 351:392 */     if (holderInstantiator.isRequired())
/* 352:    */     {
/* 353:393 */       for (int i = 0; i < results.size(); i++)
/* 354:    */       {
/* 355:394 */         Object[] row = (Object[])results.get(i);
/* 356:395 */         Object result = holderInstantiator.instantiate(row);
/* 357:396 */         results.set(i, result);
/* 358:    */       }
/* 359:399 */       return resultTransformer.transformList(results);
/* 360:    */     }
/* 361:402 */     return results;
/* 362:    */   }
/* 363:    */   
/* 364:    */   private String[] getReturnAliasesForTransformer()
/* 365:    */   {
/* 366:407 */     return this.transformerAliases;
/* 367:    */   }
/* 368:    */   
/* 369:    */   protected EntityAliases[] getEntityAliases()
/* 370:    */   {
/* 371:412 */     return this.entityAliases;
/* 372:    */   }
/* 373:    */   
/* 374:    */   protected CollectionAliases[] getCollectionAliases()
/* 375:    */   {
/* 376:417 */     return this.collectionAliases;
/* 377:    */   }
/* 378:    */   
/* 379:    */   public int[] getNamedParameterLocs(String name)
/* 380:    */     throws QueryException
/* 381:    */   {
/* 382:422 */     Object loc = this.namedParameterBindPoints.get(name);
/* 383:423 */     if (loc == null) {
/* 384:424 */       throw new QueryException("Named parameter does not appear in Query: " + name, this.sql);
/* 385:    */     }
/* 386:429 */     if ((loc instanceof Integer)) {
/* 387:430 */       return new int[] { ((Integer)loc).intValue() };
/* 388:    */     }
/* 389:433 */     return ArrayHelper.toIntArray((List)loc);
/* 390:    */   }
/* 391:    */   
/* 392:    */   public class ResultRowProcessor
/* 393:    */   {
/* 394:    */     private final boolean hasScalars;
/* 395:    */     private CustomLoader.ResultColumnProcessor[] columnProcessors;
/* 396:    */     
/* 397:    */     public ResultRowProcessor(boolean hasScalars, CustomLoader.ResultColumnProcessor[] columnProcessors)
/* 398:    */     {
/* 399:443 */       this.hasScalars = ((hasScalars) || (columnProcessors == null) || (columnProcessors.length == 0));
/* 400:444 */       this.columnProcessors = columnProcessors;
/* 401:    */     }
/* 402:    */     
/* 403:    */     public void prepareForAutoDiscovery(CustomLoader.Metadata metadata)
/* 404:    */       throws SQLException
/* 405:    */     {
/* 406:448 */       if ((this.columnProcessors == null) || (this.columnProcessors.length == 0))
/* 407:    */       {
/* 408:449 */         int columns = metadata.getColumnCount();
/* 409:450 */         this.columnProcessors = new CustomLoader.ResultColumnProcessor[columns];
/* 410:451 */         for (int i = 1; i <= columns; i++) {
/* 411:452 */           this.columnProcessors[(i - 1)] = new CustomLoader.ScalarResultColumnProcessor(CustomLoader.this, i);
/* 412:    */         }
/* 413:    */       }
/* 414:    */     }
/* 415:    */     
/* 416:    */     public Object buildResultRow(Object[] data, ResultSet resultSet, boolean hasTransformer, SessionImplementor session)
/* 417:    */       throws SQLException, HibernateException
/* 418:    */     {
/* 419:478 */       Object[] resultRow = buildResultRow(data, resultSet, session);
/* 420:479 */       return resultRow.length == 1 ? resultRow[0] : hasTransformer ? resultRow : resultRow;
/* 421:    */     }
/* 422:    */     
/* 423:    */     public Object[] buildResultRow(Object[] data, ResultSet resultSet, SessionImplementor session)
/* 424:    */       throws SQLException, HibernateException
/* 425:    */     {
/* 426:    */       Object[] resultRow;
/* 427:    */       Object[] resultRow;
/* 428:490 */       if (!this.hasScalars)
/* 429:    */       {
/* 430:491 */         resultRow = data;
/* 431:    */       }
/* 432:    */       else
/* 433:    */       {
/* 434:497 */         resultRow = new Object[this.columnProcessors.length];
/* 435:498 */         for (int i = 0; i < this.columnProcessors.length; i++) {
/* 436:499 */           resultRow[i] = this.columnProcessors[i].extract(data, resultSet, session);
/* 437:    */         }
/* 438:    */       }
/* 439:503 */       return resultRow;
/* 440:    */     }
/* 441:    */   }
/* 442:    */   
/* 443:    */   private static abstract interface ResultColumnProcessor
/* 444:    */   {
/* 445:    */     public abstract Object extract(Object[] paramArrayOfObject, ResultSet paramResultSet, SessionImplementor paramSessionImplementor)
/* 446:    */       throws SQLException, HibernateException;
/* 447:    */     
/* 448:    */     public abstract void performDiscovery(CustomLoader.Metadata paramMetadata, List<Type> paramList, List<String> paramList1)
/* 449:    */       throws SQLException, HibernateException;
/* 450:    */   }
/* 451:    */   
/* 452:    */   public class NonScalarResultColumnProcessor
/* 453:    */     implements CustomLoader.ResultColumnProcessor
/* 454:    */   {
/* 455:    */     private final int position;
/* 456:    */     
/* 457:    */     public NonScalarResultColumnProcessor(int position)
/* 458:    */     {
/* 459:516 */       this.position = position;
/* 460:    */     }
/* 461:    */     
/* 462:    */     public Object extract(Object[] data, ResultSet resultSet, SessionImplementor session)
/* 463:    */       throws SQLException, HibernateException
/* 464:    */     {
/* 465:524 */       return data[this.position];
/* 466:    */     }
/* 467:    */     
/* 468:    */     public void performDiscovery(CustomLoader.Metadata metadata, List<Type> types, List<String> aliases) {}
/* 469:    */   }
/* 470:    */   
/* 471:    */   public class ScalarResultColumnProcessor
/* 472:    */     implements CustomLoader.ResultColumnProcessor
/* 473:    */   {
/* 474:534 */     private int position = -1;
/* 475:    */     private String alias;
/* 476:    */     private Type type;
/* 477:    */     
/* 478:    */     public ScalarResultColumnProcessor(int position)
/* 479:    */     {
/* 480:539 */       this.position = position;
/* 481:    */     }
/* 482:    */     
/* 483:    */     public ScalarResultColumnProcessor(String alias, Type type)
/* 484:    */     {
/* 485:543 */       this.alias = alias;
/* 486:544 */       this.type = type;
/* 487:    */     }
/* 488:    */     
/* 489:    */     public Object extract(Object[] data, ResultSet resultSet, SessionImplementor session)
/* 490:    */       throws SQLException, HibernateException
/* 491:    */     {
/* 492:552 */       return this.type.nullSafeGet(resultSet, this.alias, session, null);
/* 493:    */     }
/* 494:    */     
/* 495:    */     public void performDiscovery(CustomLoader.Metadata metadata, List<Type> types, List<String> aliases)
/* 496:    */       throws SQLException
/* 497:    */     {
/* 498:557 */       if (this.alias == null) {
/* 499:558 */         this.alias = metadata.getColumnName(this.position);
/* 500:560 */       } else if (this.position < 0) {
/* 501:561 */         this.position = metadata.resolveColumnPosition(this.alias);
/* 502:    */       }
/* 503:563 */       if (this.type == null) {
/* 504:564 */         this.type = metadata.getHibernateType(this.position);
/* 505:    */       }
/* 506:566 */       types.add(this.type);
/* 507:567 */       aliases.add(this.alias);
/* 508:    */     }
/* 509:    */   }
/* 510:    */   
/* 511:    */   protected void autoDiscoverTypes(ResultSet rs)
/* 512:    */   {
/* 513:    */     try
/* 514:    */     {
/* 515:574 */       Metadata metadata = new Metadata(getFactory(), rs);
/* 516:575 */       this.rowProcessor.prepareForAutoDiscovery(metadata);
/* 517:    */       
/* 518:577 */       List<String> aliases = new ArrayList();
/* 519:578 */       List<Type> types = new ArrayList();
/* 520:579 */       for (int i = 0; i < this.rowProcessor.columnProcessors.length; i++) {
/* 521:580 */         this.rowProcessor.columnProcessors[i].performDiscovery(metadata, types, aliases);
/* 522:    */       }
/* 523:590 */       HashSet<String> aliasesSet = new HashSet();
/* 524:591 */       for (String alias : aliases)
/* 525:    */       {
/* 526:592 */         boolean alreadyExisted = !aliasesSet.add(alias);
/* 527:593 */         if (alreadyExisted) {
/* 528:594 */           throw new NonUniqueDiscoveredSqlAliasException("Encountered a duplicated sql alias [" + alias + "] during auto-discovery of a native-sql query");
/* 529:    */         }
/* 530:    */       }
/* 531:601 */       this.resultTypes = ArrayHelper.toTypeArray(types);
/* 532:602 */       this.transformerAliases = ArrayHelper.toStringArray(aliases);
/* 533:    */     }
/* 534:    */     catch (SQLException e)
/* 535:    */     {
/* 536:605 */       throw new HibernateException("Exception while trying to autodiscover types.", e);
/* 537:    */     }
/* 538:    */   }
/* 539:    */   
/* 540:    */   private static class Metadata
/* 541:    */   {
/* 542:    */     private final SessionFactoryImplementor factory;
/* 543:    */     private final ResultSet resultSet;
/* 544:    */     private final ResultSetMetaData resultSetMetaData;
/* 545:    */     
/* 546:    */     public Metadata(SessionFactoryImplementor factory, ResultSet resultSet)
/* 547:    */       throws HibernateException
/* 548:    */     {
/* 549:    */       try
/* 550:    */       {
/* 551:616 */         this.factory = factory;
/* 552:617 */         this.resultSet = resultSet;
/* 553:618 */         this.resultSetMetaData = resultSet.getMetaData();
/* 554:    */       }
/* 555:    */       catch (SQLException e)
/* 556:    */       {
/* 557:621 */         throw new HibernateException("Could not extract result set metadata", e);
/* 558:    */       }
/* 559:    */     }
/* 560:    */     
/* 561:    */     public int getColumnCount()
/* 562:    */       throws HibernateException
/* 563:    */     {
/* 564:    */       try
/* 565:    */       {
/* 566:627 */         return this.resultSetMetaData.getColumnCount();
/* 567:    */       }
/* 568:    */       catch (SQLException e)
/* 569:    */       {
/* 570:630 */         throw new HibernateException("Could not determine result set column count", e);
/* 571:    */       }
/* 572:    */     }
/* 573:    */     
/* 574:    */     public int resolveColumnPosition(String columnName)
/* 575:    */       throws HibernateException
/* 576:    */     {
/* 577:    */       try
/* 578:    */       {
/* 579:636 */         return this.resultSet.findColumn(columnName);
/* 580:    */       }
/* 581:    */       catch (SQLException e)
/* 582:    */       {
/* 583:639 */         throw new HibernateException("Could not resolve column name in result set [" + columnName + "]", e);
/* 584:    */       }
/* 585:    */     }
/* 586:    */     
/* 587:    */     public String getColumnName(int position)
/* 588:    */       throws HibernateException
/* 589:    */     {
/* 590:    */       try
/* 591:    */       {
/* 592:645 */         return this.factory.getDialect().getColumnAliasExtractor().extractColumnAlias(this.resultSetMetaData, position);
/* 593:    */       }
/* 594:    */       catch (SQLException e)
/* 595:    */       {
/* 596:648 */         throw new HibernateException("Could not resolve column name [" + position + "]", e);
/* 597:    */       }
/* 598:    */     }
/* 599:    */     
/* 600:    */     public Type getHibernateType(int columnPos)
/* 601:    */       throws SQLException
/* 602:    */     {
/* 603:653 */       int columnType = this.resultSetMetaData.getColumnType(columnPos);
/* 604:654 */       int scale = this.resultSetMetaData.getScale(columnPos);
/* 605:655 */       int precision = this.resultSetMetaData.getPrecision(columnPos);
/* 606:656 */       int length = precision;
/* 607:657 */       if ((columnType == 1) && (precision == 0)) {
/* 608:658 */         length = this.resultSetMetaData.getColumnDisplaySize(columnPos);
/* 609:    */       }
/* 610:660 */       return this.factory.getTypeResolver().heuristicType(this.factory.getDialect().getHibernateTypeName(columnType, length, precision, scale));
/* 611:    */     }
/* 612:    */   }
/* 613:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.loader.custom.CustomLoader
 * JD-Core Version:    0.7.0.1
 */