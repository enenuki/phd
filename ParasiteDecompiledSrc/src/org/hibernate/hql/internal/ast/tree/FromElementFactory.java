/*   1:    */ package org.hibernate.hql.internal.ast.tree;
/*   2:    */ 
/*   3:    */ import antlr.ASTFactory;
/*   4:    */ import antlr.SemanticException;
/*   5:    */ import antlr.collections.AST;
/*   6:    */ import org.hibernate.engine.internal.JoinSequence;
/*   7:    */ import org.hibernate.hql.internal.antlr.SqlTokenTypes;
/*   8:    */ import org.hibernate.hql.internal.ast.HqlSqlWalker;
/*   9:    */ import org.hibernate.hql.internal.ast.util.ASTUtil;
/*  10:    */ import org.hibernate.hql.internal.ast.util.AliasGenerator;
/*  11:    */ import org.hibernate.hql.internal.ast.util.PathHelper;
/*  12:    */ import org.hibernate.hql.internal.ast.util.SessionFactoryHelper;
/*  13:    */ import org.hibernate.internal.CoreMessageLogger;
/*  14:    */ import org.hibernate.internal.util.StringHelper;
/*  15:    */ import org.hibernate.persister.collection.QueryableCollection;
/*  16:    */ import org.hibernate.persister.entity.EntityPersister;
/*  17:    */ import org.hibernate.persister.entity.Joinable;
/*  18:    */ import org.hibernate.persister.entity.Queryable;
/*  19:    */ import org.hibernate.sql.JoinType;
/*  20:    */ import org.hibernate.type.AssociationType;
/*  21:    */ import org.hibernate.type.CollectionType;
/*  22:    */ import org.hibernate.type.ComponentType;
/*  23:    */ import org.hibernate.type.EntityType;
/*  24:    */ import org.hibernate.type.Type;
/*  25:    */ import org.jboss.logging.Logger;
/*  26:    */ 
/*  27:    */ public class FromElementFactory
/*  28:    */   implements SqlTokenTypes
/*  29:    */ {
/*  30: 58 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, FromElementFactory.class.getName());
/*  31:    */   private FromClause fromClause;
/*  32:    */   private FromElement origin;
/*  33:    */   private String path;
/*  34:    */   private String classAlias;
/*  35:    */   private String[] columns;
/*  36:    */   private boolean implied;
/*  37:    */   private boolean inElementsFunction;
/*  38:    */   private boolean collection;
/*  39:    */   private QueryableCollection queryableCollection;
/*  40:    */   private CollectionType collectionType;
/*  41:    */   
/*  42:    */   public FromElementFactory(FromClause fromClause, FromElement origin, String path)
/*  43:    */   {
/*  44: 76 */     this.fromClause = fromClause;
/*  45: 77 */     this.origin = origin;
/*  46: 78 */     this.path = path;
/*  47: 79 */     this.collection = false;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public FromElementFactory(FromClause fromClause, FromElement origin, String path, String classAlias, String[] columns, boolean implied)
/*  51:    */   {
/*  52: 92 */     this(fromClause, origin, path);
/*  53: 93 */     this.classAlias = classAlias;
/*  54: 94 */     this.columns = columns;
/*  55: 95 */     this.implied = implied;
/*  56: 96 */     this.collection = true;
/*  57:    */   }
/*  58:    */   
/*  59:    */   FromElement addFromElement()
/*  60:    */     throws SemanticException
/*  61:    */   {
/*  62:100 */     FromClause parentFromClause = this.fromClause.getParentFromClause();
/*  63:101 */     if (parentFromClause != null)
/*  64:    */     {
/*  65:103 */       String pathAlias = PathHelper.getAlias(this.path);
/*  66:104 */       FromElement parentFromElement = parentFromClause.getFromElement(pathAlias);
/*  67:105 */       if (parentFromElement != null) {
/*  68:106 */         return createFromElementInSubselect(this.path, pathAlias, parentFromElement, this.classAlias);
/*  69:    */       }
/*  70:    */     }
/*  71:110 */     EntityPersister entityPersister = this.fromClause.getSessionFactoryHelper().requireClassPersister(this.path);
/*  72:    */     
/*  73:112 */     FromElement elem = createAndAddFromElement(this.path, this.classAlias, entityPersister, (EntityType)((Queryable)entityPersister).getType(), null);
/*  74:    */     
/*  75:    */ 
/*  76:    */ 
/*  77:    */ 
/*  78:    */ 
/*  79:    */ 
/*  80:119 */     this.fromClause.getWalker().addQuerySpaces(entityPersister.getQuerySpaces());
/*  81:    */     
/*  82:121 */     return elem;
/*  83:    */   }
/*  84:    */   
/*  85:    */   private FromElement createFromElementInSubselect(String path, String pathAlias, FromElement parentFromElement, String classAlias)
/*  86:    */     throws SemanticException
/*  87:    */   {
/*  88:129 */     LOG.debugf("createFromElementInSubselect() : path = %s", path);
/*  89:    */     
/*  90:131 */     FromElement fromElement = evaluateFromElementPath(path, classAlias);
/*  91:132 */     EntityPersister entityPersister = fromElement.getEntityPersister();
/*  92:    */     
/*  93:    */ 
/*  94:    */ 
/*  95:    */ 
/*  96:137 */     String tableAlias = null;
/*  97:138 */     boolean correlatedSubselect = pathAlias.equals(parentFromElement.getClassAlias());
/*  98:139 */     if (correlatedSubselect) {
/*  99:140 */       tableAlias = fromElement.getTableAlias();
/* 100:    */     } else {
/* 101:143 */       tableAlias = null;
/* 102:    */     }
/* 103:147 */     if (fromElement.getFromClause() != this.fromClause)
/* 104:    */     {
/* 105:148 */       LOG.debug("createFromElementInSubselect() : creating a new FROM element...");
/* 106:149 */       fromElement = createFromElement(entityPersister);
/* 107:150 */       initializeAndAddFromElement(fromElement, path, classAlias, entityPersister, (EntityType)((Queryable)entityPersister).getType(), tableAlias);
/* 108:    */     }
/* 109:158 */     LOG.debugf("createFromElementInSubselect() : %s -> %s", path, fromElement);
/* 110:159 */     return fromElement;
/* 111:    */   }
/* 112:    */   
/* 113:    */   private FromElement evaluateFromElementPath(String path, String classAlias)
/* 114:    */     throws SemanticException
/* 115:    */   {
/* 116:163 */     ASTFactory factory = this.fromClause.getASTFactory();
/* 117:164 */     FromReferenceNode pathNode = (FromReferenceNode)PathHelper.parsePath(path, factory);
/* 118:165 */     pathNode.recursiveResolve(0, false, classAlias, null);
/* 119:170 */     if (pathNode.getImpliedJoin() != null) {
/* 120:170 */       return pathNode.getImpliedJoin();
/* 121:    */     }
/* 122:171 */     return pathNode.getFromElement();
/* 123:    */   }
/* 124:    */   
/* 125:    */   FromElement createCollectionElementsJoin(QueryableCollection queryableCollection, String collectionName)
/* 126:    */     throws SemanticException
/* 127:    */   {
/* 128:177 */     JoinSequence collectionJoinSequence = this.fromClause.getSessionFactoryHelper().createCollectionJoinSequence(queryableCollection, collectionName);
/* 129:    */     
/* 130:179 */     this.queryableCollection = queryableCollection;
/* 131:180 */     return createCollectionJoin(collectionJoinSequence, null);
/* 132:    */   }
/* 133:    */   
/* 134:    */   FromElement createCollection(QueryableCollection queryableCollection, String role, JoinType joinType, boolean fetchFlag, boolean indexed)
/* 135:    */     throws SemanticException
/* 136:    */   {
/* 137:190 */     if (!this.collection) {
/* 138:191 */       throw new IllegalStateException("FromElementFactory not initialized for collections!");
/* 139:    */     }
/* 140:193 */     this.inElementsFunction = indexed;
/* 141:    */     
/* 142:195 */     this.queryableCollection = queryableCollection;
/* 143:196 */     this.collectionType = queryableCollection.getCollectionType();
/* 144:197 */     String roleAlias = this.fromClause.getAliasGenerator().createName(role);
/* 145:    */     
/* 146:    */ 
/* 147:    */ 
/* 148:201 */     boolean explicitSubqueryFromElement = (this.fromClause.isSubQuery()) && (!this.implied);
/* 149:202 */     if (explicitSubqueryFromElement)
/* 150:    */     {
/* 151:203 */       String pathRoot = StringHelper.root(this.path);
/* 152:204 */       FromElement origin = this.fromClause.getFromElement(pathRoot);
/* 153:205 */       if ((origin == null) || (origin.getFromClause() != this.fromClause)) {
/* 154:206 */         this.implied = true;
/* 155:    */       }
/* 156:    */     }
/* 157:211 */     if ((explicitSubqueryFromElement) && (DotNode.useThetaStyleImplicitJoins)) {
/* 158:212 */       this.implied = true;
/* 159:    */     }
/* 160:215 */     Type elementType = queryableCollection.getElementType();
/* 161:    */     FromElement elem;
/* 162:    */     FromElement elem;
/* 163:216 */     if (elementType.isEntityType())
/* 164:    */     {
/* 165:217 */       elem = createEntityAssociation(role, roleAlias, joinType);
/* 166:    */     }
/* 167:    */     else
/* 168:    */     {
/* 169:    */       FromElement elem;
/* 170:219 */       if (elementType.isComponentType())
/* 171:    */       {
/* 172:220 */         JoinSequence joinSequence = createJoinSequence(roleAlias, joinType);
/* 173:221 */         elem = createCollectionJoin(joinSequence, roleAlias);
/* 174:    */       }
/* 175:    */       else
/* 176:    */       {
/* 177:224 */         JoinSequence joinSequence = createJoinSequence(roleAlias, joinType);
/* 178:225 */         elem = createCollectionJoin(joinSequence, roleAlias);
/* 179:    */       }
/* 180:    */     }
/* 181:228 */     elem.setRole(role);
/* 182:229 */     elem.setQueryableCollection(queryableCollection);
/* 183:231 */     if (this.implied) {
/* 184:232 */       elem.setIncludeSubclasses(false);
/* 185:    */     }
/* 186:235 */     if (explicitSubqueryFromElement) {
/* 187:236 */       elem.setInProjectionList(true);
/* 188:    */     }
/* 189:239 */     if (fetchFlag) {
/* 190:240 */       elem.setFetch(true);
/* 191:    */     }
/* 192:242 */     return elem;
/* 193:    */   }
/* 194:    */   
/* 195:    */   FromElement createEntityJoin(String entityClass, String tableAlias, JoinSequence joinSequence, boolean fetchFlag, boolean inFrom, EntityType type)
/* 196:    */     throws SemanticException
/* 197:    */   {
/* 198:252 */     FromElement elem = createJoin(entityClass, tableAlias, joinSequence, type, false);
/* 199:253 */     elem.setFetch(fetchFlag);
/* 200:254 */     EntityPersister entityPersister = elem.getEntityPersister();
/* 201:255 */     int numberOfTables = entityPersister.getQuerySpaces().length;
/* 202:256 */     if ((numberOfTables > 1) && (this.implied) && (!elem.useFromFragment()))
/* 203:    */     {
/* 204:257 */       LOG.debug("createEntityJoin() : Implied multi-table entity join");
/* 205:258 */       elem.setUseFromFragment(true);
/* 206:    */     }
/* 207:263 */     if ((this.implied) && (inFrom))
/* 208:    */     {
/* 209:264 */       joinSequence.setUseThetaStyle(false);
/* 210:265 */       elem.setUseFromFragment(true);
/* 211:266 */       elem.setImpliedInFromClause(true);
/* 212:    */     }
/* 213:268 */     if (elem.getWalker().isSubQuery()) {
/* 214:273 */       if ((elem.getFromClause() != elem.getOrigin().getFromClause()) || (DotNode.useThetaStyleImplicitJoins))
/* 215:    */       {
/* 216:277 */         elem.setType(134);
/* 217:278 */         joinSequence.setUseThetaStyle(true);
/* 218:279 */         elem.setUseFromFragment(false);
/* 219:    */       }
/* 220:    */     }
/* 221:283 */     return elem;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public FromElement createComponentJoin(ComponentType type)
/* 225:    */   {
/* 226:289 */     return new ComponentJoin(this.fromClause, this.origin, this.classAlias, this.path, type);
/* 227:    */   }
/* 228:    */   
/* 229:    */   FromElement createElementJoin(QueryableCollection queryableCollection)
/* 230:    */     throws SemanticException
/* 231:    */   {
/* 232:295 */     this.implied = true;
/* 233:296 */     this.inElementsFunction = true;
/* 234:297 */     Type elementType = queryableCollection.getElementType();
/* 235:298 */     if (!elementType.isEntityType()) {
/* 236:299 */       throw new IllegalArgumentException("Cannot create element join for a collection of non-entities!");
/* 237:    */     }
/* 238:301 */     this.queryableCollection = queryableCollection;
/* 239:302 */     SessionFactoryHelper sfh = this.fromClause.getSessionFactoryHelper();
/* 240:303 */     FromElement destination = null;
/* 241:304 */     String tableAlias = null;
/* 242:305 */     EntityPersister entityPersister = queryableCollection.getElementPersister();
/* 243:306 */     tableAlias = this.fromClause.getAliasGenerator().createName(entityPersister.getEntityName());
/* 244:307 */     String associatedEntityName = entityPersister.getEntityName();
/* 245:308 */     EntityPersister targetEntityPersister = sfh.requireClassPersister(associatedEntityName);
/* 246:    */     
/* 247:310 */     destination = createAndAddFromElement(associatedEntityName, this.classAlias, targetEntityPersister, (EntityType)queryableCollection.getElementType(), tableAlias);
/* 248:318 */     if (this.implied) {
/* 249:319 */       destination.setIncludeSubclasses(false);
/* 250:    */     }
/* 251:321 */     this.fromClause.addCollectionJoinFromElementByPath(this.path, destination);
/* 252:    */     
/* 253:    */ 
/* 254:324 */     this.fromClause.getWalker().addQuerySpaces(entityPersister.getQuerySpaces());
/* 255:    */     
/* 256:326 */     CollectionType type = queryableCollection.getCollectionType();
/* 257:327 */     String role = type.getRole();
/* 258:328 */     String roleAlias = this.origin.getTableAlias();
/* 259:    */     
/* 260:330 */     String[] targetColumns = sfh.getCollectionElementColumns(role, roleAlias);
/* 261:331 */     AssociationType elementAssociationType = sfh.getElementAssociationType(type);
/* 262:    */     
/* 263:    */ 
/* 264:334 */     JoinType joinType = JoinType.INNER_JOIN;
/* 265:335 */     JoinSequence joinSequence = sfh.createJoinSequence(this.implied, elementAssociationType, tableAlias, joinType, targetColumns);
/* 266:336 */     FromElement elem = initializeJoin(this.path, destination, joinSequence, targetColumns, this.origin, false);
/* 267:337 */     elem.setUseFromFragment(true);
/* 268:338 */     elem.setCollectionTableAlias(roleAlias);
/* 269:339 */     return elem;
/* 270:    */   }
/* 271:    */   
/* 272:    */   private FromElement createCollectionJoin(JoinSequence collectionJoinSequence, String tableAlias)
/* 273:    */     throws SemanticException
/* 274:    */   {
/* 275:343 */     String text = this.queryableCollection.getTableName();
/* 276:344 */     AST ast = createFromElement(text);
/* 277:345 */     FromElement destination = (FromElement)ast;
/* 278:346 */     Type elementType = this.queryableCollection.getElementType();
/* 279:347 */     if (elementType.isCollectionType()) {
/* 280:348 */       throw new SemanticException("Collections of collections are not supported!");
/* 281:    */     }
/* 282:350 */     destination.initializeCollection(this.fromClause, this.classAlias, tableAlias);
/* 283:351 */     destination.setType(136);
/* 284:352 */     destination.setIncludeSubclasses(false);
/* 285:353 */     destination.setCollectionJoin(true);
/* 286:354 */     destination.setJoinSequence(collectionJoinSequence);
/* 287:355 */     destination.setOrigin(this.origin, false);
/* 288:356 */     destination.setCollectionTableAlias(tableAlias);
/* 289:    */     
/* 290:    */ 
/* 291:    */ 
/* 292:360 */     this.origin.setText("");
/* 293:361 */     this.origin.setCollectionJoin(true);
/* 294:362 */     this.fromClause.addCollectionJoinFromElementByPath(this.path, destination);
/* 295:363 */     this.fromClause.getWalker().addQuerySpaces(this.queryableCollection.getCollectionSpaces());
/* 296:364 */     return destination;
/* 297:    */   }
/* 298:    */   
/* 299:    */   private FromElement createEntityAssociation(String role, String roleAlias, JoinType joinType)
/* 300:    */     throws SemanticException
/* 301:    */   {
/* 302:372 */     Queryable entityPersister = (Queryable)this.queryableCollection.getElementPersister();
/* 303:373 */     String associatedEntityName = entityPersister.getEntityName();
/* 304:    */     FromElement elem;
/* 305:    */     FromElement elem;
/* 306:375 */     if (this.queryableCollection.isOneToMany())
/* 307:    */     {
/* 308:376 */       LOG.debugf("createEntityAssociation() : One to many - path = %s role = %s associatedEntityName = %s", this.path, role, associatedEntityName);
/* 309:    */       
/* 310:    */ 
/* 311:    */ 
/* 312:380 */       JoinSequence joinSequence = createJoinSequence(roleAlias, joinType);
/* 313:    */       
/* 314:382 */       elem = createJoin(associatedEntityName, roleAlias, joinSequence, (EntityType)this.queryableCollection.getElementType(), false);
/* 315:    */     }
/* 316:    */     else
/* 317:    */     {
/* 318:385 */       LOG.debugf("createManyToMany() : path = %s role = %s associatedEntityName = %s", this.path, role, associatedEntityName);
/* 319:386 */       elem = createManyToMany(role, associatedEntityName, roleAlias, entityPersister, (EntityType)this.queryableCollection.getElementType(), joinType);
/* 320:    */       
/* 321:388 */       this.fromClause.getWalker().addQuerySpaces(this.queryableCollection.getCollectionSpaces());
/* 322:    */     }
/* 323:390 */     elem.setCollectionTableAlias(roleAlias);
/* 324:391 */     return elem;
/* 325:    */   }
/* 326:    */   
/* 327:    */   private FromElement createJoin(String entityClass, String tableAlias, JoinSequence joinSequence, EntityType type, boolean manyToMany)
/* 328:    */     throws SemanticException
/* 329:    */   {
/* 330:401 */     EntityPersister entityPersister = this.fromClause.getSessionFactoryHelper().requireClassPersister(entityClass);
/* 331:402 */     FromElement destination = createAndAddFromElement(entityClass, this.classAlias, entityPersister, type, tableAlias);
/* 332:    */     
/* 333:    */ 
/* 334:    */ 
/* 335:    */ 
/* 336:407 */     return initializeJoin(this.path, destination, joinSequence, getColumns(), this.origin, manyToMany);
/* 337:    */   }
/* 338:    */   
/* 339:    */   private FromElement createManyToMany(String role, String associatedEntityName, String roleAlias, Queryable entityPersister, EntityType type, JoinType joinType)
/* 340:    */     throws SemanticException
/* 341:    */   {
/* 342:418 */     SessionFactoryHelper sfh = this.fromClause.getSessionFactoryHelper();
/* 343:    */     FromElement elem;
/* 344:    */     FromElement elem;
/* 345:419 */     if (this.inElementsFunction)
/* 346:    */     {
/* 347:421 */       JoinSequence joinSequence = createJoinSequence(roleAlias, joinType);
/* 348:422 */       elem = createJoin(associatedEntityName, roleAlias, joinSequence, type, true);
/* 349:    */     }
/* 350:    */     else
/* 351:    */     {
/* 352:428 */       String tableAlias = this.fromClause.getAliasGenerator().createName(entityPersister.getEntityName());
/* 353:429 */       String[] secondJoinColumns = sfh.getCollectionElementColumns(role, roleAlias);
/* 354:    */       
/* 355:431 */       JoinSequence joinSequence = createJoinSequence(roleAlias, joinType);
/* 356:432 */       joinSequence.addJoin(sfh.getElementAssociationType(this.collectionType), tableAlias, joinType, secondJoinColumns);
/* 357:433 */       elem = createJoin(associatedEntityName, tableAlias, joinSequence, type, false);
/* 358:434 */       elem.setUseFromFragment(true);
/* 359:    */     }
/* 360:436 */     return elem;
/* 361:    */   }
/* 362:    */   
/* 363:    */   private JoinSequence createJoinSequence(String roleAlias, JoinType joinType)
/* 364:    */   {
/* 365:440 */     SessionFactoryHelper sessionFactoryHelper = this.fromClause.getSessionFactoryHelper();
/* 366:441 */     String[] joinColumns = getColumns();
/* 367:442 */     if (this.collectionType == null) {
/* 368:443 */       throw new IllegalStateException("collectionType is null!");
/* 369:    */     }
/* 370:445 */     return sessionFactoryHelper.createJoinSequence(this.implied, this.collectionType, roleAlias, joinType, joinColumns);
/* 371:    */   }
/* 372:    */   
/* 373:    */   private FromElement createAndAddFromElement(String className, String classAlias, EntityPersister entityPersister, EntityType type, String tableAlias)
/* 374:    */   {
/* 375:454 */     if (!(entityPersister instanceof Joinable)) {
/* 376:455 */       throw new IllegalArgumentException("EntityPersister " + entityPersister + " does not implement Joinable!");
/* 377:    */     }
/* 378:457 */     FromElement element = createFromElement(entityPersister);
/* 379:458 */     initializeAndAddFromElement(element, className, classAlias, entityPersister, type, tableAlias);
/* 380:459 */     return element;
/* 381:    */   }
/* 382:    */   
/* 383:    */   private void initializeAndAddFromElement(FromElement element, String className, String classAlias, EntityPersister entityPersister, EntityType type, String tableAlias)
/* 384:    */   {
/* 385:469 */     if (tableAlias == null)
/* 386:    */     {
/* 387:470 */       AliasGenerator aliasGenerator = this.fromClause.getAliasGenerator();
/* 388:471 */       tableAlias = aliasGenerator.createName(entityPersister.getEntityName());
/* 389:    */     }
/* 390:473 */     element.initializeEntity(this.fromClause, className, entityPersister, type, classAlias, tableAlias);
/* 391:    */   }
/* 392:    */   
/* 393:    */   private FromElement createFromElement(EntityPersister entityPersister)
/* 394:    */   {
/* 395:477 */     Joinable joinable = (Joinable)entityPersister;
/* 396:478 */     String text = joinable.getTableName();
/* 397:479 */     AST ast = createFromElement(text);
/* 398:480 */     FromElement element = (FromElement)ast;
/* 399:481 */     return element;
/* 400:    */   }
/* 401:    */   
/* 402:    */   private AST createFromElement(String text)
/* 403:    */   {
/* 404:485 */     AST ast = ASTUtil.create(this.fromClause.getASTFactory(), this.implied ? 135 : 134, text);
/* 405:    */     
/* 406:    */ 
/* 407:    */ 
/* 408:    */ 
/* 409:    */ 
/* 410:491 */     ast.setType(134);
/* 411:492 */     return ast;
/* 412:    */   }
/* 413:    */   
/* 414:    */   private FromElement initializeJoin(String path, FromElement destination, JoinSequence joinSequence, String[] columns, FromElement origin, boolean manyToMany)
/* 415:    */   {
/* 416:502 */     destination.setType(136);
/* 417:503 */     destination.setJoinSequence(joinSequence);
/* 418:504 */     destination.setColumns(columns);
/* 419:505 */     destination.setOrigin(origin, manyToMany);
/* 420:506 */     this.fromClause.addJoinByPathMap(path, destination);
/* 421:507 */     return destination;
/* 422:    */   }
/* 423:    */   
/* 424:    */   private String[] getColumns()
/* 425:    */   {
/* 426:511 */     if (this.columns == null) {
/* 427:512 */       throw new IllegalStateException("No foriegn key columns were supplied!");
/* 428:    */     }
/* 429:514 */     return this.columns;
/* 430:    */   }
/* 431:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.tree.FromElementFactory
 * JD-Core Version:    0.7.0.1
 */