/*   1:    */ package org.hibernate.hql.internal.ast.tree;
/*   2:    */ 
/*   3:    */ import antlr.SemanticException;
/*   4:    */ import antlr.collections.AST;
/*   5:    */ import org.hibernate.QueryException;
/*   6:    */ import org.hibernate.engine.internal.JoinSequence;
/*   7:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*   8:    */ import org.hibernate.hql.internal.CollectionProperties;
/*   9:    */ import org.hibernate.hql.internal.ast.HqlSqlWalker;
/*  10:    */ import org.hibernate.hql.internal.ast.util.ASTPrinter;
/*  11:    */ import org.hibernate.hql.internal.ast.util.ASTUtil;
/*  12:    */ import org.hibernate.hql.internal.ast.util.AliasGenerator;
/*  13:    */ import org.hibernate.hql.internal.ast.util.ColumnHelper;
/*  14:    */ import org.hibernate.hql.internal.ast.util.LiteralProcessor;
/*  15:    */ import org.hibernate.hql.internal.ast.util.SessionFactoryHelper;
/*  16:    */ import org.hibernate.internal.CoreMessageLogger;
/*  17:    */ import org.hibernate.internal.util.StringHelper;
/*  18:    */ import org.hibernate.persister.collection.QueryableCollection;
/*  19:    */ import org.hibernate.persister.entity.EntityPersister;
/*  20:    */ import org.hibernate.persister.entity.Queryable;
/*  21:    */ import org.hibernate.sql.JoinType;
/*  22:    */ import org.hibernate.tuple.entity.EntityMetamodel;
/*  23:    */ import org.hibernate.type.CollectionType;
/*  24:    */ import org.hibernate.type.EntityType;
/*  25:    */ import org.hibernate.type.Type;
/*  26:    */ import org.jboss.logging.Logger;
/*  27:    */ 
/*  28:    */ public class DotNode
/*  29:    */   extends FromReferenceNode
/*  30:    */   implements DisplayableNode, SelectExpression
/*  31:    */ {
/*  32: 60 */   public static boolean useThetaStyleImplicitJoins = false;
/*  33: 61 */   public static boolean REGRESSION_STYLE_JOIN_SUPPRESSION = false;
/*  34: 65 */   public static final IllegalCollectionDereferenceExceptionBuilder DEF_ILLEGAL_COLL_DEREF_EXCP_BUILDER = new IllegalCollectionDereferenceExceptionBuilder()
/*  35:    */   {
/*  36:    */     public QueryException buildIllegalCollectionDereferenceException(String propertyName, FromReferenceNode lhs)
/*  37:    */     {
/*  38: 67 */       String lhsPath = ASTUtil.getPathText(lhs);
/*  39: 68 */       return new QueryException("illegal attempt to dereference collection [" + lhsPath + "] with element property reference [" + propertyName + "]");
/*  40:    */     }
/*  41:    */   };
/*  42: 71 */   public static IllegalCollectionDereferenceExceptionBuilder ILLEGAL_COLL_DEREF_EXCP_BUILDER = DEF_ILLEGAL_COLL_DEREF_EXCP_BUILDER;
/*  43: 74 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, DotNode.class.getName());
/*  44:    */   private static final int DEREF_UNKNOWN = 0;
/*  45:    */   private static final int DEREF_ENTITY = 1;
/*  46:    */   private static final int DEREF_COMPONENT = 2;
/*  47:    */   private static final int DEREF_COLLECTION = 3;
/*  48:    */   private static final int DEREF_PRIMITIVE = 4;
/*  49:    */   private static final int DEREF_IDENTIFIER = 5;
/*  50:    */   private static final int DEREF_JAVA_CONSTANT = 6;
/*  51:    */   private String propertyName;
/*  52:    */   private String path;
/*  53:    */   private String propertyPath;
/*  54:    */   private String[] columns;
/*  55:105 */   private JoinType joinType = JoinType.INNER_JOIN;
/*  56:110 */   private boolean fetch = false;
/*  57:115 */   private int dereferenceType = 0;
/*  58:    */   private FromElement impliedJoin;
/*  59:    */   
/*  60:    */   public void setJoinType(JoinType joinType)
/*  61:    */   {
/*  62:126 */     this.joinType = joinType;
/*  63:    */   }
/*  64:    */   
/*  65:    */   private String[] getColumns()
/*  66:    */     throws QueryException
/*  67:    */   {
/*  68:130 */     if (this.columns == null)
/*  69:    */     {
/*  70:132 */       String tableAlias = getLhs().getFromElement().getTableAlias();
/*  71:133 */       this.columns = getFromElement().toColumns(tableAlias, this.propertyPath, false);
/*  72:    */     }
/*  73:135 */     return this.columns;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public String getDisplayText()
/*  77:    */   {
/*  78:140 */     StringBuilder buf = new StringBuilder();
/*  79:141 */     FromElement fromElement = getFromElement();
/*  80:142 */     buf.append("{propertyName=").append(this.propertyName);
/*  81:143 */     buf.append(",dereferenceType=").append(getWalker().getASTPrinter().getTokenTypeName(this.dereferenceType));
/*  82:144 */     buf.append(",propertyPath=").append(this.propertyPath);
/*  83:145 */     buf.append(",path=").append(getPath());
/*  84:146 */     if (fromElement != null)
/*  85:    */     {
/*  86:147 */       buf.append(",tableAlias=").append(fromElement.getTableAlias());
/*  87:148 */       buf.append(",className=").append(fromElement.getClassName());
/*  88:149 */       buf.append(",classAlias=").append(fromElement.getClassAlias());
/*  89:    */     }
/*  90:    */     else
/*  91:    */     {
/*  92:152 */       buf.append(",no from element");
/*  93:    */     }
/*  94:154 */     buf.append('}');
/*  95:155 */     return buf.toString();
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void resolveFirstChild()
/*  99:    */     throws SemanticException
/* 100:    */   {
/* 101:165 */     FromReferenceNode lhs = (FromReferenceNode)getFirstChild();
/* 102:166 */     SqlNode property = (SqlNode)lhs.getNextSibling();
/* 103:    */     
/* 104:    */ 
/* 105:169 */     String propName = property.getText();
/* 106:170 */     this.propertyName = propName;
/* 107:172 */     if (this.propertyPath == null) {
/* 108:173 */       this.propertyPath = propName;
/* 109:    */     }
/* 110:177 */     lhs.resolve(true, true, null, this);
/* 111:178 */     setFromElement(lhs.getFromElement());
/* 112:    */     
/* 113:180 */     checkSubclassOrSuperclassPropertyReference(lhs, propName);
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void resolveInFunctionCall(boolean generateJoin, boolean implicitJoin)
/* 117:    */     throws SemanticException
/* 118:    */   {
/* 119:185 */     if (isResolved()) {
/* 120:186 */       return;
/* 121:    */     }
/* 122:188 */     Type propertyType = prepareLhs();
/* 123:189 */     if ((propertyType != null) && (propertyType.isCollectionType()))
/* 124:    */     {
/* 125:190 */       resolveIndex(null);
/* 126:    */     }
/* 127:    */     else
/* 128:    */     {
/* 129:193 */       resolveFirstChild();
/* 130:194 */       super.resolve(generateJoin, implicitJoin);
/* 131:    */     }
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void resolveIndex(AST parent)
/* 135:    */     throws SemanticException
/* 136:    */   {
/* 137:200 */     if (isResolved()) {
/* 138:201 */       return;
/* 139:    */     }
/* 140:203 */     Type propertyType = prepareLhs();
/* 141:204 */     dereferenceCollection((CollectionType)propertyType, true, true, null, parent);
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void resolve(boolean generateJoin, boolean implicitJoin, String classAlias, AST parent)
/* 145:    */     throws SemanticException
/* 146:    */   {
/* 147:210 */     if (isResolved()) {
/* 148:211 */       return;
/* 149:    */     }
/* 150:213 */     Type propertyType = prepareLhs();
/* 151:217 */     if (propertyType == null)
/* 152:    */     {
/* 153:218 */       if (parent == null) {
/* 154:219 */         getWalker().getLiteralProcessor().lookupConstant(this);
/* 155:    */       }
/* 156:223 */       return;
/* 157:    */     }
/* 158:226 */     if (propertyType.isComponentType())
/* 159:    */     {
/* 160:228 */       checkLhsIsNotCollection();
/* 161:229 */       dereferenceComponent(parent);
/* 162:230 */       initText();
/* 163:    */     }
/* 164:232 */     else if (propertyType.isEntityType())
/* 165:    */     {
/* 166:234 */       checkLhsIsNotCollection();
/* 167:235 */       dereferenceEntity((EntityType)propertyType, implicitJoin, classAlias, generateJoin, parent);
/* 168:236 */       initText();
/* 169:    */     }
/* 170:238 */     else if (propertyType.isCollectionType())
/* 171:    */     {
/* 172:240 */       checkLhsIsNotCollection();
/* 173:241 */       dereferenceCollection((CollectionType)propertyType, implicitJoin, false, classAlias, parent);
/* 174:    */     }
/* 175:    */     else
/* 176:    */     {
/* 177:245 */       if (!CollectionProperties.isAnyCollectionProperty(this.propertyName)) {
/* 178:246 */         checkLhsIsNotCollection();
/* 179:    */       }
/* 180:248 */       this.dereferenceType = 4;
/* 181:249 */       initText();
/* 182:    */     }
/* 183:251 */     setResolved();
/* 184:    */   }
/* 185:    */   
/* 186:    */   private void initText()
/* 187:    */   {
/* 188:255 */     String[] cols = getColumns();
/* 189:256 */     String text = StringHelper.join(", ", cols);
/* 190:257 */     if ((cols.length > 1) && (getWalker().isComparativeExpressionClause())) {
/* 191:258 */       text = "(" + text + ")";
/* 192:    */     }
/* 193:260 */     setText(text);
/* 194:    */   }
/* 195:    */   
/* 196:    */   private Type prepareLhs()
/* 197:    */     throws SemanticException
/* 198:    */   {
/* 199:264 */     FromReferenceNode lhs = getLhs();
/* 200:265 */     lhs.prepareForDot(this.propertyName);
/* 201:266 */     return getDataType();
/* 202:    */   }
/* 203:    */   
/* 204:    */   private void dereferenceCollection(CollectionType collectionType, boolean implicitJoin, boolean indexed, String classAlias, AST parent)
/* 205:    */     throws SemanticException
/* 206:    */   {
/* 207:272 */     this.dereferenceType = 3;
/* 208:273 */     String role = collectionType.getRole();
/* 209:    */     
/* 210:    */ 
/* 211:276 */     boolean isSizeProperty = (getNextSibling() != null) && (CollectionProperties.isAnyCollectionProperty(getNextSibling().getText()));
/* 212:279 */     if (isSizeProperty) {
/* 213:279 */       indexed = true;
/* 214:    */     }
/* 215:281 */     QueryableCollection queryableCollection = getSessionFactoryHelper().requireQueryableCollection(role);
/* 216:282 */     String propName = getPath();
/* 217:283 */     FromClause currentFromClause = getWalker().getCurrentFromClause();
/* 218:285 */     if ((getWalker().getStatementType() != 45) && (indexed) && (classAlias == null))
/* 219:    */     {
/* 220:292 */       String alias = getLhs().getFromElement().getQueryable().getTableName();
/* 221:293 */       this.columns = getFromElement().toColumns(alias, this.propertyPath, false, true);
/* 222:    */     }
/* 223:298 */     FromElementFactory factory = new FromElementFactory(currentFromClause, getLhs().getFromElement(), propName, classAlias, getColumns(), implicitJoin);
/* 224:    */     
/* 225:    */ 
/* 226:    */ 
/* 227:    */ 
/* 228:    */ 
/* 229:    */ 
/* 230:    */ 
/* 231:306 */     FromElement elem = factory.createCollection(queryableCollection, role, this.joinType, this.fetch, indexed);
/* 232:    */     
/* 233:308 */     LOG.debugf("dereferenceCollection() : Created new FROM element for %s : %s", propName, elem);
/* 234:    */     
/* 235:310 */     setImpliedJoin(elem);
/* 236:311 */     setFromElement(elem);
/* 237:313 */     if (isSizeProperty)
/* 238:    */     {
/* 239:314 */       elem.setText("");
/* 240:315 */       elem.setUseWhereFragment(false);
/* 241:    */     }
/* 242:318 */     if (!implicitJoin)
/* 243:    */     {
/* 244:319 */       EntityPersister entityPersister = elem.getEntityPersister();
/* 245:320 */       if (entityPersister != null) {
/* 246:321 */         getWalker().addQuerySpaces(entityPersister.getQuerySpaces());
/* 247:    */       }
/* 248:    */     }
/* 249:324 */     getWalker().addQuerySpaces(queryableCollection.getCollectionSpaces());
/* 250:    */   }
/* 251:    */   
/* 252:    */   private void dereferenceEntity(EntityType entityType, boolean implicitJoin, String classAlias, boolean generateJoin, AST parent)
/* 253:    */     throws SemanticException
/* 254:    */   {
/* 255:328 */     checkForCorrelatedSubquery("dereferenceEntity");
/* 256:    */     
/* 257:    */ 
/* 258:    */ 
/* 259:    */ 
/* 260:    */ 
/* 261:    */ 
/* 262:    */ 
/* 263:    */ 
/* 264:    */ 
/* 265:    */ 
/* 266:    */ 
/* 267:    */ 
/* 268:    */ 
/* 269:    */ 
/* 270:    */ 
/* 271:    */ 
/* 272:    */ 
/* 273:    */ 
/* 274:    */ 
/* 275:    */ 
/* 276:349 */     DotNode parentAsDotNode = null;
/* 277:350 */     String property = this.propertyName;
/* 278:    */     boolean joinIsNeeded;
/* 279:    */     boolean joinIsNeeded;
/* 280:353 */     if (isDotNode(parent))
/* 281:    */     {
/* 282:357 */       parentAsDotNode = (DotNode)parent;
/* 283:358 */       property = parentAsDotNode.propertyName;
/* 284:359 */       joinIsNeeded = (generateJoin) && (!isReferenceToPrimaryKey(parentAsDotNode.propertyName, entityType));
/* 285:    */     }
/* 286:    */     else
/* 287:    */     {
/* 288:    */       boolean joinIsNeeded;
/* 289:361 */       if (!getWalker().isSelectStatement())
/* 290:    */       {
/* 291:363 */         joinIsNeeded = (getWalker().getCurrentStatementType() == 45) && (getWalker().isInFrom());
/* 292:    */       }
/* 293:    */       else
/* 294:    */       {
/* 295:    */         boolean joinIsNeeded;
/* 296:365 */         if (REGRESSION_STYLE_JOIN_SUPPRESSION) {
/* 297:367 */           joinIsNeeded = (generateJoin) && ((!getWalker().isInSelect()) || (!getWalker().isShallowQuery()));
/* 298:    */         } else {
/* 299:370 */           joinIsNeeded = (generateJoin) || (getWalker().isInSelect()) || (getWalker().isInFrom());
/* 300:    */         }
/* 301:    */       }
/* 302:    */     }
/* 303:373 */     if (joinIsNeeded) {
/* 304:374 */       dereferenceEntityJoin(classAlias, entityType, implicitJoin, parent);
/* 305:    */     } else {
/* 306:377 */       dereferenceEntityIdentifier(property, parentAsDotNode);
/* 307:    */     }
/* 308:    */   }
/* 309:    */   
/* 310:    */   private boolean isDotNode(AST n)
/* 311:    */   {
/* 312:383 */     return (n != null) && (n.getType() == 15);
/* 313:    */   }
/* 314:    */   
/* 315:    */   private void dereferenceEntityJoin(String classAlias, EntityType propertyType, boolean impliedJoin, AST parent)
/* 316:    */     throws SemanticException
/* 317:    */   {
/* 318:388 */     this.dereferenceType = 1;
/* 319:389 */     if (LOG.isDebugEnabled()) {
/* 320:389 */       LOG.debugf("dereferenceEntityJoin() : generating join for %s in %s (%s) parent = %s", new Object[] { this.propertyName, getFromElement().getClassName(), classAlias == null ? "<no alias>" : classAlias, ASTUtil.getDebugString(parent) });
/* 321:    */     }
/* 322:395 */     String associatedEntityName = propertyType.getAssociatedEntityName();
/* 323:396 */     String tableAlias = getAliasGenerator().createName(associatedEntityName);
/* 324:    */     
/* 325:398 */     String[] joinColumns = getColumns();
/* 326:399 */     String joinPath = getPath();
/* 327:401 */     if ((impliedJoin) && (getWalker().isInFrom())) {
/* 328:402 */       this.joinType = getWalker().getImpliedJoinType();
/* 329:    */     }
/* 330:405 */     FromClause currentFromClause = getWalker().getCurrentFromClause();
/* 331:406 */     FromElement elem = currentFromClause.findJoinByPath(joinPath);
/* 332:    */     
/* 333:    */ 
/* 334:    */ 
/* 335:    */ 
/* 336:    */ 
/* 337:    */ 
/* 338:    */ 
/* 339:    */ 
/* 340:    */ 
/* 341:    */ 
/* 342:    */ 
/* 343:    */ 
/* 344:    */ 
/* 345:    */ 
/* 346:    */ 
/* 347:    */ 
/* 348:    */ 
/* 349:    */ 
/* 350:    */ 
/* 351:    */ 
/* 352:    */ 
/* 353:    */ 
/* 354:    */ 
/* 355:    */ 
/* 356:    */ 
/* 357:    */ 
/* 358:    */ 
/* 359:    */ 
/* 360:435 */     boolean found = elem != null;
/* 361:    */     
/* 362:    */ 
/* 363:    */ 
/* 364:439 */     boolean useFoundFromElement = (found) && ((elem.isImplied()) || (areSame(classAlias, elem.getClassAlias())));
/* 365:441 */     if (!useFoundFromElement)
/* 366:    */     {
/* 367:444 */       JoinSequence joinSequence = getSessionFactoryHelper().createJoinSequence(impliedJoin, propertyType, tableAlias, this.joinType, joinColumns);
/* 368:    */       
/* 369:    */ 
/* 370:    */ 
/* 371:    */ 
/* 372:    */ 
/* 373:450 */       FromElement lhsFromElement = getLhs().getFromElement();
/* 374:451 */       while ((lhsFromElement != null) && (ComponentJoin.class.isInstance(lhsFromElement))) {
/* 375:452 */         lhsFromElement = lhsFromElement.getOrigin();
/* 376:    */       }
/* 377:454 */       if (lhsFromElement == null) {
/* 378:455 */         throw new QueryException("Unable to locate appropriate lhs");
/* 379:    */       }
/* 380:458 */       FromElementFactory factory = new FromElementFactory(currentFromClause, lhsFromElement, joinPath, classAlias, joinColumns, impliedJoin);
/* 381:    */       
/* 382:    */ 
/* 383:    */ 
/* 384:    */ 
/* 385:    */ 
/* 386:    */ 
/* 387:    */ 
/* 388:466 */       elem = factory.createEntityJoin(associatedEntityName, tableAlias, joinSequence, this.fetch, getWalker().isInFrom(), propertyType);
/* 389:    */     }
/* 390:    */     else
/* 391:    */     {
/* 392:477 */       currentFromClause.addDuplicateAlias(classAlias, elem);
/* 393:    */     }
/* 394:479 */     setImpliedJoin(elem);
/* 395:480 */     getWalker().addQuerySpaces(elem.getEntityPersister().getQuerySpaces());
/* 396:481 */     setFromElement(elem);
/* 397:    */   }
/* 398:    */   
/* 399:    */   private boolean areSame(String alias1, String alias2)
/* 400:    */   {
/* 401:486 */     return (!StringHelper.isEmpty(alias1)) && (!StringHelper.isEmpty(alias2)) && (alias1.equals(alias2));
/* 402:    */   }
/* 403:    */   
/* 404:    */   private void setImpliedJoin(FromElement elem)
/* 405:    */   {
/* 406:490 */     this.impliedJoin = elem;
/* 407:491 */     if (getFirstChild().getType() == 15)
/* 408:    */     {
/* 409:492 */       DotNode dotLhs = (DotNode)getFirstChild();
/* 410:493 */       if (dotLhs.getImpliedJoin() != null) {
/* 411:494 */         this.impliedJoin = dotLhs.getImpliedJoin();
/* 412:    */       }
/* 413:    */     }
/* 414:    */   }
/* 415:    */   
/* 416:    */   public FromElement getImpliedJoin()
/* 417:    */   {
/* 418:501 */     return this.impliedJoin;
/* 419:    */   }
/* 420:    */   
/* 421:    */   private boolean isReferenceToPrimaryKey(String propertyName, EntityType owningType)
/* 422:    */   {
/* 423:521 */     EntityPersister persister = getSessionFactoryHelper().getFactory().getEntityPersister(owningType.getAssociatedEntityName());
/* 424:524 */     if (persister.getEntityMetamodel().hasNonIdentifierPropertyNamedId()) {
/* 425:526 */       return (propertyName.equals(persister.getIdentifierPropertyName())) && (owningType.isReferenceToPrimaryKey());
/* 426:    */     }
/* 427:532 */     if ("id".equals(propertyName)) {
/* 428:532 */       return owningType.isReferenceToPrimaryKey();
/* 429:    */     }
/* 430:533 */     String keyPropertyName = getSessionFactoryHelper().getIdentifierOrUniqueKeyPropertyName(owningType);
/* 431:534 */     return (keyPropertyName != null) && (keyPropertyName.equals(propertyName)) && (owningType.isReferenceToPrimaryKey());
/* 432:    */   }
/* 433:    */   
/* 434:    */   private void checkForCorrelatedSubquery(String methodName)
/* 435:    */   {
/* 436:538 */     if (isCorrelatedSubselect()) {
/* 437:539 */       LOG.debugf("%s() : correlated subquery", methodName);
/* 438:    */     }
/* 439:    */   }
/* 440:    */   
/* 441:    */   private boolean isCorrelatedSubselect()
/* 442:    */   {
/* 443:544 */     return (getWalker().isSubQuery()) && (getFromElement().getFromClause() != getWalker().getCurrentFromClause());
/* 444:    */   }
/* 445:    */   
/* 446:    */   private void checkLhsIsNotCollection()
/* 447:    */     throws SemanticException
/* 448:    */   {
/* 449:549 */     if ((getLhs().getDataType() != null) && (getLhs().getDataType().isCollectionType())) {
/* 450:550 */       throw ILLEGAL_COLL_DEREF_EXCP_BUILDER.buildIllegalCollectionDereferenceException(this.propertyName, getLhs());
/* 451:    */     }
/* 452:    */   }
/* 453:    */   
/* 454:    */   private void dereferenceComponent(AST parent)
/* 455:    */   {
/* 456:554 */     this.dereferenceType = 2;
/* 457:555 */     setPropertyNameAndPath(parent);
/* 458:    */   }
/* 459:    */   
/* 460:    */   private void dereferenceEntityIdentifier(String propertyName, DotNode dotParent)
/* 461:    */   {
/* 462:561 */     if (LOG.isDebugEnabled()) {
/* 463:562 */       LOG.debugf("dereferenceShortcut() : property %s in %s does not require a join.", propertyName, getFromElement().getClassName());
/* 464:    */     }
/* 465:567 */     initText();
/* 466:568 */     setPropertyNameAndPath(dotParent);
/* 467:570 */     if (dotParent != null)
/* 468:    */     {
/* 469:571 */       dotParent.dereferenceType = 5;
/* 470:572 */       dotParent.setText(getText());
/* 471:573 */       dotParent.columns = getColumns();
/* 472:    */     }
/* 473:    */   }
/* 474:    */   
/* 475:    */   private void setPropertyNameAndPath(AST parent)
/* 476:    */   {
/* 477:578 */     if (isDotNode(parent))
/* 478:    */     {
/* 479:579 */       DotNode dotNode = (DotNode)parent;
/* 480:580 */       AST lhs = dotNode.getFirstChild();
/* 481:581 */       AST rhs = lhs.getNextSibling();
/* 482:582 */       this.propertyName = rhs.getText();
/* 483:583 */       this.propertyPath = (this.propertyPath + "." + this.propertyName);
/* 484:584 */       dotNode.propertyPath = this.propertyPath;
/* 485:585 */       LOG.debugf("Unresolved property path is now '%s'", dotNode.propertyPath);
/* 486:    */     }
/* 487:    */     else
/* 488:    */     {
/* 489:588 */       LOG.debugf("Terminal propertyPath = [%s]", this.propertyPath);
/* 490:    */     }
/* 491:    */   }
/* 492:    */   
/* 493:    */   public Type getDataType()
/* 494:    */   {
/* 495:594 */     if (super.getDataType() == null)
/* 496:    */     {
/* 497:595 */       FromElement fromElement = getLhs().getFromElement();
/* 498:596 */       if (fromElement == null) {
/* 499:596 */         return null;
/* 500:    */       }
/* 501:598 */       Type propertyType = fromElement.getPropertyType(this.propertyName, this.propertyPath);
/* 502:599 */       LOG.debugf("getDataType() : %s -> %s", this.propertyPath, propertyType);
/* 503:600 */       super.setDataType(propertyType);
/* 504:    */     }
/* 505:602 */     return super.getDataType();
/* 506:    */   }
/* 507:    */   
/* 508:    */   public void setPropertyPath(String propertyPath)
/* 509:    */   {
/* 510:606 */     this.propertyPath = propertyPath;
/* 511:    */   }
/* 512:    */   
/* 513:    */   public String getPropertyPath()
/* 514:    */   {
/* 515:610 */     return this.propertyPath;
/* 516:    */   }
/* 517:    */   
/* 518:    */   public FromReferenceNode getLhs()
/* 519:    */   {
/* 520:614 */     FromReferenceNode lhs = (FromReferenceNode)getFirstChild();
/* 521:615 */     if (lhs == null) {
/* 522:616 */       throw new IllegalStateException("DOT node with no left-hand-side!");
/* 523:    */     }
/* 524:618 */     return lhs;
/* 525:    */   }
/* 526:    */   
/* 527:    */   public String getPath()
/* 528:    */   {
/* 529:628 */     if (this.path == null)
/* 530:    */     {
/* 531:629 */       FromReferenceNode lhs = getLhs();
/* 532:630 */       if (lhs == null)
/* 533:    */       {
/* 534:631 */         this.path = getText();
/* 535:    */       }
/* 536:    */       else
/* 537:    */       {
/* 538:634 */         SqlNode rhs = (SqlNode)lhs.getNextSibling();
/* 539:635 */         this.path = (lhs.getPath() + "." + rhs.getOriginalText());
/* 540:    */       }
/* 541:    */     }
/* 542:638 */     return this.path;
/* 543:    */   }
/* 544:    */   
/* 545:    */   public void setFetch(boolean fetch)
/* 546:    */   {
/* 547:642 */     this.fetch = fetch;
/* 548:    */   }
/* 549:    */   
/* 550:    */   public void setScalarColumnText(int i)
/* 551:    */     throws SemanticException
/* 552:    */   {
/* 553:646 */     String[] sqlColumns = getColumns();
/* 554:647 */     ColumnHelper.generateScalarColumns(this, sqlColumns, i);
/* 555:    */   }
/* 556:    */   
/* 557:    */   public void resolveSelectExpression()
/* 558:    */     throws SemanticException
/* 559:    */   {
/* 560:656 */     if ((getWalker().isShallowQuery()) || (getWalker().getCurrentFromClause().isSubQuery()))
/* 561:    */     {
/* 562:657 */       resolve(false, true);
/* 563:    */     }
/* 564:    */     else
/* 565:    */     {
/* 566:660 */       resolve(true, false);
/* 567:661 */       Type type = getDataType();
/* 568:662 */       if (type.isEntityType())
/* 569:    */       {
/* 570:663 */         FromElement fromElement = getFromElement();
/* 571:664 */         fromElement.setIncludeSubclasses(true);
/* 572:665 */         if (useThetaStyleImplicitJoins)
/* 573:    */         {
/* 574:666 */           fromElement.getJoinSequence().setUseThetaStyle(true);
/* 575:    */           
/* 576:668 */           FromElement origin = fromElement.getOrigin();
/* 577:669 */           if (origin != null) {
/* 578:670 */             ASTUtil.makeSiblingOfParent(origin, fromElement);
/* 579:    */           }
/* 580:    */         }
/* 581:    */       }
/* 582:    */     }
/* 583:676 */     FromReferenceNode lhs = getLhs();
/* 584:677 */     while (lhs != null)
/* 585:    */     {
/* 586:678 */       checkSubclassOrSuperclassPropertyReference(lhs, lhs.getNextSibling().getText());
/* 587:679 */       lhs = (FromReferenceNode)lhs.getFirstChild();
/* 588:    */     }
/* 589:    */   }
/* 590:    */   
/* 591:    */   public void setResolvedConstant(String text)
/* 592:    */   {
/* 593:684 */     this.path = text;
/* 594:685 */     this.dereferenceType = 6;
/* 595:686 */     setResolved();
/* 596:    */   }
/* 597:    */   
/* 598:    */   private boolean checkSubclassOrSuperclassPropertyReference(FromReferenceNode lhs, String propertyName)
/* 599:    */   {
/* 600:690 */     if ((lhs != null) && (!(lhs instanceof IndexNode)))
/* 601:    */     {
/* 602:691 */       FromElement source = lhs.getFromElement();
/* 603:692 */       if (source != null) {
/* 604:693 */         source.handlePropertyBeingDereferenced(lhs.getDataType(), propertyName);
/* 605:    */       }
/* 606:    */     }
/* 607:696 */     return false;
/* 608:    */   }
/* 609:    */   
/* 610:    */   public static abstract interface IllegalCollectionDereferenceExceptionBuilder
/* 611:    */   {
/* 612:    */     public abstract QueryException buildIllegalCollectionDereferenceException(String paramString, FromReferenceNode paramFromReferenceNode);
/* 613:    */   }
/* 614:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.tree.DotNode
 * JD-Core Version:    0.7.0.1
 */