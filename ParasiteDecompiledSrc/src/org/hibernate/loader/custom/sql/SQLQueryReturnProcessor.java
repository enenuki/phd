/*   1:    */ package org.hibernate.loader.custom.sql;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import java.util.Map.Entry;
/*   9:    */ import java.util.Set;
/*  10:    */ import org.hibernate.HibernateException;
/*  11:    */ import org.hibernate.MappingException;
/*  12:    */ import org.hibernate.engine.query.spi.sql.NativeSQLQueryCollectionReturn;
/*  13:    */ import org.hibernate.engine.query.spi.sql.NativeSQLQueryJoinReturn;
/*  14:    */ import org.hibernate.engine.query.spi.sql.NativeSQLQueryNonScalarReturn;
/*  15:    */ import org.hibernate.engine.query.spi.sql.NativeSQLQueryReturn;
/*  16:    */ import org.hibernate.engine.query.spi.sql.NativeSQLQueryRootReturn;
/*  17:    */ import org.hibernate.engine.query.spi.sql.NativeSQLQueryScalarReturn;
/*  18:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  19:    */ import org.hibernate.internal.CoreMessageLogger;
/*  20:    */ import org.hibernate.loader.CollectionAliases;
/*  21:    */ import org.hibernate.loader.ColumnEntityAliases;
/*  22:    */ import org.hibernate.loader.DefaultEntityAliases;
/*  23:    */ import org.hibernate.loader.EntityAliases;
/*  24:    */ import org.hibernate.loader.GeneratedCollectionAliases;
/*  25:    */ import org.hibernate.loader.custom.CollectionFetchReturn;
/*  26:    */ import org.hibernate.loader.custom.CollectionReturn;
/*  27:    */ import org.hibernate.loader.custom.ColumnCollectionAliases;
/*  28:    */ import org.hibernate.loader.custom.EntityFetchReturn;
/*  29:    */ import org.hibernate.loader.custom.FetchReturn;
/*  30:    */ import org.hibernate.loader.custom.NonScalarReturn;
/*  31:    */ import org.hibernate.loader.custom.RootReturn;
/*  32:    */ import org.hibernate.loader.custom.ScalarReturn;
/*  33:    */ import org.hibernate.persister.collection.SQLLoadableCollection;
/*  34:    */ import org.hibernate.persister.entity.EntityPersister;
/*  35:    */ import org.hibernate.persister.entity.SQLLoadable;
/*  36:    */ import org.hibernate.type.EntityType;
/*  37:    */ import org.hibernate.type.Type;
/*  38:    */ import org.jboss.logging.Logger;
/*  39:    */ 
/*  40:    */ public class SQLQueryReturnProcessor
/*  41:    */ {
/*  42: 77 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, SQLQueryReturnProcessor.class.getName());
/*  43:    */   private NativeSQLQueryReturn[] queryReturns;
/*  44: 84 */   private final Map alias2Return = new HashMap();
/*  45: 85 */   private final Map alias2OwnerAlias = new HashMap();
/*  46: 87 */   private final Map alias2Persister = new HashMap();
/*  47: 88 */   private final Map alias2Suffix = new HashMap();
/*  48: 90 */   private final Map alias2CollectionPersister = new HashMap();
/*  49: 91 */   private final Map alias2CollectionSuffix = new HashMap();
/*  50: 93 */   private final Map entityPropertyResultMaps = new HashMap();
/*  51: 94 */   private final Map collectionPropertyResultMaps = new HashMap();
/*  52:    */   private final SessionFactoryImplementor factory;
/*  53:106 */   private int entitySuffixSeed = 0;
/*  54:107 */   private int collectionSuffixSeed = 0;
/*  55:    */   
/*  56:    */   public SQLQueryReturnProcessor(NativeSQLQueryReturn[] queryReturns, SessionFactoryImplementor factory)
/*  57:    */   {
/*  58:111 */     this.queryReturns = queryReturns;
/*  59:112 */     this.factory = factory;
/*  60:    */   }
/*  61:    */   
/*  62:    */   class ResultAliasContext
/*  63:    */   {
/*  64:    */     ResultAliasContext() {}
/*  65:    */     
/*  66:    */     public SQLLoadable getEntityPersister(String alias)
/*  67:    */     {
/*  68:117 */       return (SQLLoadable)SQLQueryReturnProcessor.this.alias2Persister.get(alias);
/*  69:    */     }
/*  70:    */     
/*  71:    */     public SQLLoadableCollection getCollectionPersister(String alias)
/*  72:    */     {
/*  73:121 */       return (SQLLoadableCollection)SQLQueryReturnProcessor.this.alias2CollectionPersister.get(alias);
/*  74:    */     }
/*  75:    */     
/*  76:    */     public String getEntitySuffix(String alias)
/*  77:    */     {
/*  78:125 */       return (String)SQLQueryReturnProcessor.this.alias2Suffix.get(alias);
/*  79:    */     }
/*  80:    */     
/*  81:    */     public String getCollectionSuffix(String alias)
/*  82:    */     {
/*  83:129 */       return (String)SQLQueryReturnProcessor.this.alias2CollectionSuffix.get(alias);
/*  84:    */     }
/*  85:    */     
/*  86:    */     public String getOwnerAlias(String alias)
/*  87:    */     {
/*  88:133 */       return (String)SQLQueryReturnProcessor.this.alias2OwnerAlias.get(alias);
/*  89:    */     }
/*  90:    */     
/*  91:    */     public Map getPropertyResultsMap(String alias)
/*  92:    */     {
/*  93:137 */       return SQLQueryReturnProcessor.this.internalGetPropertyResultsMap(alias);
/*  94:    */     }
/*  95:    */   }
/*  96:    */   
/*  97:    */   private Map internalGetPropertyResultsMap(String alias)
/*  98:    */   {
/*  99:142 */     NativeSQLQueryReturn rtn = (NativeSQLQueryReturn)this.alias2Return.get(alias);
/* 100:143 */     if ((rtn instanceof NativeSQLQueryNonScalarReturn)) {
/* 101:144 */       return ((NativeSQLQueryNonScalarReturn)rtn).getPropertyResultsMap();
/* 102:    */     }
/* 103:147 */     return null;
/* 104:    */   }
/* 105:    */   
/* 106:    */   private boolean hasPropertyResultMap(String alias)
/* 107:    */   {
/* 108:152 */     Map propertyMaps = internalGetPropertyResultsMap(alias);
/* 109:153 */     return (propertyMaps != null) && (!propertyMaps.isEmpty());
/* 110:    */   }
/* 111:    */   
/* 112:    */   public ResultAliasContext process()
/* 113:    */   {
/* 114:159 */     for (int i = 0; i < this.queryReturns.length; i++) {
/* 115:160 */       if ((this.queryReturns[i] instanceof NativeSQLQueryNonScalarReturn))
/* 116:    */       {
/* 117:161 */         NativeSQLQueryNonScalarReturn rtn = (NativeSQLQueryNonScalarReturn)this.queryReturns[i];
/* 118:162 */         this.alias2Return.put(rtn.getAlias(), rtn);
/* 119:163 */         if ((rtn instanceof NativeSQLQueryJoinReturn))
/* 120:    */         {
/* 121:164 */           NativeSQLQueryJoinReturn fetchReturn = (NativeSQLQueryJoinReturn)rtn;
/* 122:165 */           this.alias2OwnerAlias.put(fetchReturn.getAlias(), fetchReturn.getOwnerAlias());
/* 123:    */         }
/* 124:    */       }
/* 125:    */     }
/* 126:171 */     for (int i = 0; i < this.queryReturns.length; i++) {
/* 127:172 */       processReturn(this.queryReturns[i]);
/* 128:    */     }
/* 129:175 */     return new ResultAliasContext();
/* 130:    */   }
/* 131:    */   
/* 132:    */   public List generateCustomReturns(boolean queryHadAliases)
/* 133:    */   {
/* 134:179 */     List customReturns = new ArrayList();
/* 135:180 */     Map customReturnsByAlias = new HashMap();
/* 136:181 */     for (int i = 0; i < this.queryReturns.length; i++) {
/* 137:182 */       if ((this.queryReturns[i] instanceof NativeSQLQueryScalarReturn))
/* 138:    */       {
/* 139:183 */         NativeSQLQueryScalarReturn rtn = (NativeSQLQueryScalarReturn)this.queryReturns[i];
/* 140:184 */         customReturns.add(new ScalarReturn(rtn.getType(), rtn.getColumnAlias()));
/* 141:    */       }
/* 142:186 */       else if ((this.queryReturns[i] instanceof NativeSQLQueryRootReturn))
/* 143:    */       {
/* 144:187 */         NativeSQLQueryRootReturn rtn = (NativeSQLQueryRootReturn)this.queryReturns[i];
/* 145:188 */         String alias = rtn.getAlias();
/* 146:    */         EntityAliases entityAliases;
/* 147:    */         EntityAliases entityAliases;
/* 148:190 */         if ((queryHadAliases) || (hasPropertyResultMap(alias))) {
/* 149:191 */           entityAliases = new DefaultEntityAliases((Map)this.entityPropertyResultMaps.get(alias), (SQLLoadable)this.alias2Persister.get(alias), (String)this.alias2Suffix.get(alias));
/* 150:    */         } else {
/* 151:198 */           entityAliases = new ColumnEntityAliases((Map)this.entityPropertyResultMaps.get(alias), (SQLLoadable)this.alias2Persister.get(alias), (String)this.alias2Suffix.get(alias));
/* 152:    */         }
/* 153:204 */         RootReturn customReturn = new RootReturn(alias, rtn.getReturnEntityName(), entityAliases, rtn.getLockMode());
/* 154:    */         
/* 155:    */ 
/* 156:    */ 
/* 157:    */ 
/* 158:    */ 
/* 159:210 */         customReturns.add(customReturn);
/* 160:211 */         customReturnsByAlias.put(rtn.getAlias(), customReturn);
/* 161:    */       }
/* 162:213 */       else if ((this.queryReturns[i] instanceof NativeSQLQueryCollectionReturn))
/* 163:    */       {
/* 164:214 */         NativeSQLQueryCollectionReturn rtn = (NativeSQLQueryCollectionReturn)this.queryReturns[i];
/* 165:215 */         String alias = rtn.getAlias();
/* 166:216 */         SQLLoadableCollection persister = (SQLLoadableCollection)this.alias2CollectionPersister.get(alias);
/* 167:217 */         boolean isEntityElements = persister.getElementType().isEntityType();
/* 168:    */         
/* 169:219 */         EntityAliases elementEntityAliases = null;
/* 170:    */         CollectionAliases collectionAliases;
/* 171:220 */         if ((queryHadAliases) || (hasPropertyResultMap(alias)))
/* 172:    */         {
/* 173:221 */           CollectionAliases collectionAliases = new GeneratedCollectionAliases((Map)this.collectionPropertyResultMaps.get(alias), (SQLLoadableCollection)this.alias2CollectionPersister.get(alias), (String)this.alias2CollectionSuffix.get(alias));
/* 174:226 */           if (isEntityElements) {
/* 175:227 */             elementEntityAliases = new DefaultEntityAliases((Map)this.entityPropertyResultMaps.get(alias), (SQLLoadable)this.alias2Persister.get(alias), (String)this.alias2Suffix.get(alias));
/* 176:    */           }
/* 177:    */         }
/* 178:    */         else
/* 179:    */         {
/* 180:235 */           collectionAliases = new ColumnCollectionAliases((Map)this.collectionPropertyResultMaps.get(alias), (SQLLoadableCollection)this.alias2CollectionPersister.get(alias));
/* 181:239 */           if (isEntityElements) {
/* 182:240 */             elementEntityAliases = new ColumnEntityAliases((Map)this.entityPropertyResultMaps.get(alias), (SQLLoadable)this.alias2Persister.get(alias), (String)this.alias2Suffix.get(alias));
/* 183:    */           }
/* 184:    */         }
/* 185:247 */         CollectionReturn customReturn = new CollectionReturn(alias, rtn.getOwnerEntityName(), rtn.getOwnerProperty(), collectionAliases, elementEntityAliases, rtn.getLockMode());
/* 186:    */         
/* 187:    */ 
/* 188:    */ 
/* 189:    */ 
/* 190:    */ 
/* 191:    */ 
/* 192:    */ 
/* 193:255 */         customReturns.add(customReturn);
/* 194:256 */         customReturnsByAlias.put(rtn.getAlias(), customReturn);
/* 195:    */       }
/* 196:258 */       else if ((this.queryReturns[i] instanceof NativeSQLQueryJoinReturn))
/* 197:    */       {
/* 198:259 */         NativeSQLQueryJoinReturn rtn = (NativeSQLQueryJoinReturn)this.queryReturns[i];
/* 199:260 */         String alias = rtn.getAlias();
/* 200:    */         
/* 201:262 */         NonScalarReturn ownerCustomReturn = (NonScalarReturn)customReturnsByAlias.get(rtn.getOwnerAlias());
/* 202:    */         FetchReturn customReturn;
/* 203:    */         FetchReturn customReturn;
/* 204:263 */         if (this.alias2CollectionPersister.containsKey(alias))
/* 205:    */         {
/* 206:264 */           SQLLoadableCollection persister = (SQLLoadableCollection)this.alias2CollectionPersister.get(alias);
/* 207:265 */           boolean isEntityElements = persister.getElementType().isEntityType();
/* 208:    */           
/* 209:267 */           EntityAliases elementEntityAliases = null;
/* 210:    */           CollectionAliases collectionAliases;
/* 211:268 */           if ((queryHadAliases) || (hasPropertyResultMap(alias)))
/* 212:    */           {
/* 213:269 */             CollectionAliases collectionAliases = new GeneratedCollectionAliases((Map)this.collectionPropertyResultMaps.get(alias), persister, (String)this.alias2CollectionSuffix.get(alias));
/* 214:274 */             if (isEntityElements) {
/* 215:275 */               elementEntityAliases = new DefaultEntityAliases((Map)this.entityPropertyResultMaps.get(alias), (SQLLoadable)this.alias2Persister.get(alias), (String)this.alias2Suffix.get(alias));
/* 216:    */             }
/* 217:    */           }
/* 218:    */           else
/* 219:    */           {
/* 220:283 */             collectionAliases = new ColumnCollectionAliases((Map)this.collectionPropertyResultMaps.get(alias), persister);
/* 221:287 */             if (isEntityElements) {
/* 222:288 */               elementEntityAliases = new ColumnEntityAliases((Map)this.entityPropertyResultMaps.get(alias), (SQLLoadable)this.alias2Persister.get(alias), (String)this.alias2Suffix.get(alias));
/* 223:    */             }
/* 224:    */           }
/* 225:295 */           customReturn = new CollectionFetchReturn(alias, ownerCustomReturn, rtn.getOwnerProperty(), collectionAliases, elementEntityAliases, rtn.getLockMode());
/* 226:    */         }
/* 227:    */         else
/* 228:    */         {
/* 229:    */           EntityAliases entityAliases;
/* 230:    */           EntityAliases entityAliases;
/* 231:306 */           if ((queryHadAliases) || (hasPropertyResultMap(alias))) {
/* 232:307 */             entityAliases = new DefaultEntityAliases((Map)this.entityPropertyResultMaps.get(alias), (SQLLoadable)this.alias2Persister.get(alias), (String)this.alias2Suffix.get(alias));
/* 233:    */           } else {
/* 234:314 */             entityAliases = new ColumnEntityAliases((Map)this.entityPropertyResultMaps.get(alias), (SQLLoadable)this.alias2Persister.get(alias), (String)this.alias2Suffix.get(alias));
/* 235:    */           }
/* 236:320 */           customReturn = new EntityFetchReturn(alias, entityAliases, ownerCustomReturn, rtn.getOwnerProperty(), rtn.getLockMode());
/* 237:    */         }
/* 238:328 */         customReturns.add(customReturn);
/* 239:329 */         customReturnsByAlias.put(alias, customReturn);
/* 240:    */       }
/* 241:    */     }
/* 242:332 */     return customReturns;
/* 243:    */   }
/* 244:    */   
/* 245:    */   private SQLLoadable getSQLLoadable(String entityName)
/* 246:    */     throws MappingException
/* 247:    */   {
/* 248:336 */     EntityPersister persister = this.factory.getEntityPersister(entityName);
/* 249:337 */     if (!(persister instanceof SQLLoadable)) {
/* 250:338 */       throw new MappingException("class persister is not SQLLoadable: " + entityName);
/* 251:    */     }
/* 252:340 */     return (SQLLoadable)persister;
/* 253:    */   }
/* 254:    */   
/* 255:    */   private String generateEntitySuffix()
/* 256:    */   {
/* 257:344 */     return org.hibernate.loader.BasicLoader.generateSuffixes(this.entitySuffixSeed++, 1)[0];
/* 258:    */   }
/* 259:    */   
/* 260:    */   private String generateCollectionSuffix()
/* 261:    */   {
/* 262:348 */     return this.collectionSuffixSeed++ + "__";
/* 263:    */   }
/* 264:    */   
/* 265:    */   private void processReturn(NativeSQLQueryReturn rtn)
/* 266:    */   {
/* 267:352 */     if ((rtn instanceof NativeSQLQueryScalarReturn)) {
/* 268:353 */       processScalarReturn((NativeSQLQueryScalarReturn)rtn);
/* 269:355 */     } else if ((rtn instanceof NativeSQLQueryRootReturn)) {
/* 270:356 */       processRootReturn((NativeSQLQueryRootReturn)rtn);
/* 271:358 */     } else if ((rtn instanceof NativeSQLQueryCollectionReturn)) {
/* 272:359 */       processCollectionReturn((NativeSQLQueryCollectionReturn)rtn);
/* 273:    */     } else {
/* 274:362 */       processJoinReturn((NativeSQLQueryJoinReturn)rtn);
/* 275:    */     }
/* 276:    */   }
/* 277:    */   
/* 278:    */   private void processScalarReturn(NativeSQLQueryScalarReturn typeReturn) {}
/* 279:    */   
/* 280:    */   private void processRootReturn(NativeSQLQueryRootReturn rootReturn)
/* 281:    */   {
/* 282:372 */     if (this.alias2Persister.containsKey(rootReturn.getAlias())) {
/* 283:374 */       return;
/* 284:    */     }
/* 285:377 */     SQLLoadable persister = getSQLLoadable(rootReturn.getReturnEntityName());
/* 286:378 */     addPersister(rootReturn.getAlias(), rootReturn.getPropertyResultsMap(), persister);
/* 287:    */   }
/* 288:    */   
/* 289:    */   private void addPersister(String alias, Map propertyResult, SQLLoadable persister)
/* 290:    */   {
/* 291:386 */     this.alias2Persister.put(alias, persister);
/* 292:387 */     String suffix = generateEntitySuffix();
/* 293:388 */     LOG.tracev("Mapping alias [{0}] to entity-suffix [{1}]", alias, suffix);
/* 294:389 */     this.alias2Suffix.put(alias, suffix);
/* 295:390 */     this.entityPropertyResultMaps.put(alias, propertyResult);
/* 296:    */   }
/* 297:    */   
/* 298:    */   private void addCollection(String role, String alias, Map propertyResults)
/* 299:    */   {
/* 300:394 */     SQLLoadableCollection collectionPersister = (SQLLoadableCollection)this.factory.getCollectionPersister(role);
/* 301:395 */     this.alias2CollectionPersister.put(alias, collectionPersister);
/* 302:396 */     String suffix = generateCollectionSuffix();
/* 303:397 */     LOG.tracev("Mapping alias [{0}] to collection-suffix [{1}]", alias, suffix);
/* 304:398 */     this.alias2CollectionSuffix.put(alias, suffix);
/* 305:399 */     this.collectionPropertyResultMaps.put(alias, propertyResults);
/* 306:401 */     if ((collectionPersister.isOneToMany()) || (collectionPersister.isManyToMany()))
/* 307:    */     {
/* 308:402 */       SQLLoadable persister = (SQLLoadable)collectionPersister.getElementPersister();
/* 309:403 */       addPersister(alias, filter(propertyResults), persister);
/* 310:    */     }
/* 311:    */   }
/* 312:    */   
/* 313:    */   private Map filter(Map propertyResults)
/* 314:    */   {
/* 315:408 */     Map result = new HashMap(propertyResults.size());
/* 316:    */     
/* 317:410 */     String keyPrefix = "element.";
/* 318:    */     
/* 319:412 */     Iterator iter = propertyResults.entrySet().iterator();
/* 320:413 */     while (iter.hasNext())
/* 321:    */     {
/* 322:414 */       Map.Entry element = (Map.Entry)iter.next();
/* 323:415 */       String path = (String)element.getKey();
/* 324:416 */       if (path.startsWith(keyPrefix)) {
/* 325:417 */         result.put(path.substring(keyPrefix.length()), element.getValue());
/* 326:    */       }
/* 327:    */     }
/* 328:421 */     return result;
/* 329:    */   }
/* 330:    */   
/* 331:    */   private void processCollectionReturn(NativeSQLQueryCollectionReturn collectionReturn)
/* 332:    */   {
/* 333:428 */     String role = collectionReturn.getOwnerEntityName() + '.' + collectionReturn.getOwnerProperty();
/* 334:429 */     addCollection(role, collectionReturn.getAlias(), collectionReturn.getPropertyResultsMap());
/* 335:    */   }
/* 336:    */   
/* 337:    */   private void processJoinReturn(NativeSQLQueryJoinReturn fetchReturn)
/* 338:    */   {
/* 339:437 */     String alias = fetchReturn.getAlias();
/* 340:439 */     if ((this.alias2Persister.containsKey(alias)) || (this.alias2CollectionPersister.containsKey(alias))) {
/* 341:441 */       return;
/* 342:    */     }
/* 343:444 */     String ownerAlias = fetchReturn.getOwnerAlias();
/* 344:447 */     if (!this.alias2Return.containsKey(ownerAlias)) {
/* 345:448 */       throw new HibernateException("Owner alias [" + ownerAlias + "] is unknown for alias [" + alias + "]");
/* 346:    */     }
/* 347:452 */     if (!this.alias2Persister.containsKey(ownerAlias))
/* 348:    */     {
/* 349:453 */       NativeSQLQueryNonScalarReturn ownerReturn = (NativeSQLQueryNonScalarReturn)this.alias2Return.get(ownerAlias);
/* 350:454 */       processReturn(ownerReturn);
/* 351:    */     }
/* 352:457 */     SQLLoadable ownerPersister = (SQLLoadable)this.alias2Persister.get(ownerAlias);
/* 353:458 */     Type returnType = ownerPersister.getPropertyType(fetchReturn.getOwnerProperty());
/* 354:460 */     if (returnType.isCollectionType())
/* 355:    */     {
/* 356:461 */       String role = ownerPersister.getEntityName() + '.' + fetchReturn.getOwnerProperty();
/* 357:462 */       addCollection(role, alias, fetchReturn.getPropertyResultsMap());
/* 358:    */     }
/* 359:465 */     else if (returnType.isEntityType())
/* 360:    */     {
/* 361:466 */       EntityType eType = (EntityType)returnType;
/* 362:467 */       String returnEntityName = eType.getAssociatedEntityName();
/* 363:468 */       SQLLoadable persister = getSQLLoadable(returnEntityName);
/* 364:469 */       addPersister(alias, fetchReturn.getPropertyResultsMap(), persister);
/* 365:    */     }
/* 366:    */   }
/* 367:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.loader.custom.sql.SQLQueryReturnProcessor
 * JD-Core Version:    0.7.0.1
 */