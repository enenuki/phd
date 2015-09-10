/*    1:     */ package org.hibernate.loader;
/*    2:     */ 
/*    3:     */ import java.util.ArrayList;
/*    4:     */ import java.util.Arrays;
/*    5:     */ import java.util.HashSet;
/*    6:     */ import java.util.Iterator;
/*    7:     */ import java.util.List;
/*    8:     */ import java.util.Set;
/*    9:     */ import org.hibernate.FetchMode;
/*   10:     */ import org.hibernate.LockMode;
/*   11:     */ import org.hibernate.LockOptions;
/*   12:     */ import org.hibernate.MappingException;
/*   13:     */ import org.hibernate.cfg.Settings;
/*   14:     */ import org.hibernate.dialect.Dialect;
/*   15:     */ import org.hibernate.engine.internal.JoinHelper;
/*   16:     */ import org.hibernate.engine.spi.CascadeStyle;
/*   17:     */ import org.hibernate.engine.spi.LoadQueryInfluencers;
/*   18:     */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*   19:     */ import org.hibernate.internal.util.StringHelper;
/*   20:     */ import org.hibernate.internal.util.collections.ArrayHelper;
/*   21:     */ import org.hibernate.persister.collection.CollectionPersister;
/*   22:     */ import org.hibernate.persister.collection.QueryableCollection;
/*   23:     */ import org.hibernate.persister.entity.EntityPersister;
/*   24:     */ import org.hibernate.persister.entity.Joinable;
/*   25:     */ import org.hibernate.persister.entity.Loadable;
/*   26:     */ import org.hibernate.persister.entity.OuterJoinLoadable;
/*   27:     */ import org.hibernate.sql.ConditionFragment;
/*   28:     */ import org.hibernate.sql.DisjunctionFragment;
/*   29:     */ import org.hibernate.sql.InFragment;
/*   30:     */ import org.hibernate.sql.JoinFragment;
/*   31:     */ import org.hibernate.sql.JoinType;
/*   32:     */ import org.hibernate.type.AssociationType;
/*   33:     */ import org.hibernate.type.CompositeType;
/*   34:     */ import org.hibernate.type.EntityType;
/*   35:     */ import org.hibernate.type.ForeignKeyDirection;
/*   36:     */ import org.hibernate.type.Type;
/*   37:     */ 
/*   38:     */ public class JoinWalker
/*   39:     */ {
/*   40:     */   private final SessionFactoryImplementor factory;
/*   41:  70 */   protected final List associations = new ArrayList();
/*   42:  71 */   private final Set visitedAssociationKeys = new HashSet();
/*   43:     */   private final LoadQueryInfluencers loadQueryInfluencers;
/*   44:     */   protected String[] suffixes;
/*   45:     */   protected String[] collectionSuffixes;
/*   46:     */   protected Loadable[] persisters;
/*   47:     */   protected int[] owners;
/*   48:     */   protected EntityType[] ownerAssociationTypes;
/*   49:     */   protected CollectionPersister[] collectionPersisters;
/*   50:     */   protected int[] collectionOwners;
/*   51:     */   protected String[] aliases;
/*   52:     */   protected LockOptions lockOptions;
/*   53:     */   protected LockMode[] lockModeArray;
/*   54:     */   protected String sql;
/*   55:     */   
/*   56:     */   protected JoinWalker(SessionFactoryImplementor factory, LoadQueryInfluencers loadQueryInfluencers)
/*   57:     */   {
/*   58:  89 */     this.factory = factory;
/*   59:  90 */     this.loadQueryInfluencers = loadQueryInfluencers;
/*   60:     */   }
/*   61:     */   
/*   62:     */   public String[] getCollectionSuffixes()
/*   63:     */   {
/*   64:  96 */     return this.collectionSuffixes;
/*   65:     */   }
/*   66:     */   
/*   67:     */   public void setCollectionSuffixes(String[] collectionSuffixes)
/*   68:     */   {
/*   69: 100 */     this.collectionSuffixes = collectionSuffixes;
/*   70:     */   }
/*   71:     */   
/*   72:     */   public LockOptions getLockModeOptions()
/*   73:     */   {
/*   74: 104 */     return this.lockOptions;
/*   75:     */   }
/*   76:     */   
/*   77:     */   public LockMode[] getLockModeArray()
/*   78:     */   {
/*   79: 108 */     return this.lockModeArray;
/*   80:     */   }
/*   81:     */   
/*   82:     */   public String[] getSuffixes()
/*   83:     */   {
/*   84: 112 */     return this.suffixes;
/*   85:     */   }
/*   86:     */   
/*   87:     */   public void setSuffixes(String[] suffixes)
/*   88:     */   {
/*   89: 116 */     this.suffixes = suffixes;
/*   90:     */   }
/*   91:     */   
/*   92:     */   public String[] getAliases()
/*   93:     */   {
/*   94: 120 */     return this.aliases;
/*   95:     */   }
/*   96:     */   
/*   97:     */   public void setAliases(String[] aliases)
/*   98:     */   {
/*   99: 124 */     this.aliases = aliases;
/*  100:     */   }
/*  101:     */   
/*  102:     */   public int[] getCollectionOwners()
/*  103:     */   {
/*  104: 128 */     return this.collectionOwners;
/*  105:     */   }
/*  106:     */   
/*  107:     */   public void setCollectionOwners(int[] collectionOwners)
/*  108:     */   {
/*  109: 132 */     this.collectionOwners = collectionOwners;
/*  110:     */   }
/*  111:     */   
/*  112:     */   public CollectionPersister[] getCollectionPersisters()
/*  113:     */   {
/*  114: 136 */     return this.collectionPersisters;
/*  115:     */   }
/*  116:     */   
/*  117:     */   public void setCollectionPersisters(CollectionPersister[] collectionPersisters)
/*  118:     */   {
/*  119: 140 */     this.collectionPersisters = collectionPersisters;
/*  120:     */   }
/*  121:     */   
/*  122:     */   public EntityType[] getOwnerAssociationTypes()
/*  123:     */   {
/*  124: 144 */     return this.ownerAssociationTypes;
/*  125:     */   }
/*  126:     */   
/*  127:     */   public void setOwnerAssociationTypes(EntityType[] ownerAssociationType)
/*  128:     */   {
/*  129: 148 */     this.ownerAssociationTypes = ownerAssociationType;
/*  130:     */   }
/*  131:     */   
/*  132:     */   public int[] getOwners()
/*  133:     */   {
/*  134: 152 */     return this.owners;
/*  135:     */   }
/*  136:     */   
/*  137:     */   public void setOwners(int[] owners)
/*  138:     */   {
/*  139: 156 */     this.owners = owners;
/*  140:     */   }
/*  141:     */   
/*  142:     */   public Loadable[] getPersisters()
/*  143:     */   {
/*  144: 160 */     return this.persisters;
/*  145:     */   }
/*  146:     */   
/*  147:     */   public void setPersisters(Loadable[] persisters)
/*  148:     */   {
/*  149: 164 */     this.persisters = persisters;
/*  150:     */   }
/*  151:     */   
/*  152:     */   public String getSQLString()
/*  153:     */   {
/*  154: 168 */     return this.sql;
/*  155:     */   }
/*  156:     */   
/*  157:     */   public void setSql(String sql)
/*  158:     */   {
/*  159: 172 */     this.sql = sql;
/*  160:     */   }
/*  161:     */   
/*  162:     */   protected SessionFactoryImplementor getFactory()
/*  163:     */   {
/*  164: 176 */     return this.factory;
/*  165:     */   }
/*  166:     */   
/*  167:     */   protected Dialect getDialect()
/*  168:     */   {
/*  169: 180 */     return this.factory.getDialect();
/*  170:     */   }
/*  171:     */   
/*  172:     */   public LoadQueryInfluencers getLoadQueryInfluencers()
/*  173:     */   {
/*  174: 184 */     return this.loadQueryInfluencers;
/*  175:     */   }
/*  176:     */   
/*  177:     */   private void addAssociationToJoinTreeIfNecessary(AssociationType type, String[] aliasedLhsColumns, String alias, PropertyPath path, int currentDepth, JoinType joinType)
/*  178:     */     throws MappingException
/*  179:     */   {
/*  180: 198 */     if (joinType != JoinType.NONE) {
/*  181: 199 */       addAssociationToJoinTree(type, aliasedLhsColumns, alias, path, currentDepth, joinType);
/*  182:     */     }
/*  183:     */   }
/*  184:     */   
/*  185:     */   protected boolean hasRestriction(PropertyPath path)
/*  186:     */   {
/*  187: 211 */     return false;
/*  188:     */   }
/*  189:     */   
/*  190:     */   protected String getWithClause(PropertyPath path)
/*  191:     */   {
/*  192: 215 */     return "";
/*  193:     */   }
/*  194:     */   
/*  195:     */   private void addAssociationToJoinTree(AssociationType type, String[] aliasedLhsColumns, String alias, PropertyPath path, int currentDepth, JoinType joinType)
/*  196:     */     throws MappingException
/*  197:     */   {
/*  198: 230 */     Joinable joinable = type.getAssociatedJoinable(getFactory());
/*  199:     */     
/*  200:     */ 
/*  201:     */ 
/*  202: 234 */     String subalias = generateTableAlias(this.associations.size() + 1, path, joinable);
/*  203:     */     
/*  204:     */ 
/*  205:     */ 
/*  206:     */ 
/*  207:     */ 
/*  208: 240 */     OuterJoinableAssociation assoc = new OuterJoinableAssociation(path, type, alias, aliasedLhsColumns, subalias, joinType, getWithClause(path), hasRestriction(path), getFactory(), this.loadQueryInfluencers.getEnabledFilters());
/*  209:     */     
/*  210:     */ 
/*  211:     */ 
/*  212:     */ 
/*  213:     */ 
/*  214:     */ 
/*  215:     */ 
/*  216:     */ 
/*  217:     */ 
/*  218:     */ 
/*  219:     */ 
/*  220: 252 */     assoc.validateJoin(path.getFullPath());
/*  221: 253 */     this.associations.add(assoc);
/*  222:     */     
/*  223: 255 */     int nextDepth = currentDepth + 1;
/*  224: 257 */     if (!joinable.isCollection())
/*  225:     */     {
/*  226: 258 */       if ((joinable instanceof OuterJoinLoadable)) {
/*  227: 259 */         walkEntityTree((OuterJoinLoadable)joinable, subalias, path, nextDepth);
/*  228:     */       }
/*  229:     */     }
/*  230: 268 */     else if ((joinable instanceof QueryableCollection)) {
/*  231: 269 */       walkCollectionTree((QueryableCollection)joinable, subalias, path, nextDepth);
/*  232:     */     }
/*  233:     */   }
/*  234:     */   
/*  235:     */   protected final void walkEntityTree(OuterJoinLoadable persister, String alias)
/*  236:     */     throws MappingException
/*  237:     */   {
/*  238: 293 */     walkEntityTree(persister, alias, new PropertyPath(), 0);
/*  239:     */   }
/*  240:     */   
/*  241:     */   protected final void walkCollectionTree(QueryableCollection persister, String alias)
/*  242:     */     throws MappingException
/*  243:     */   {
/*  244: 300 */     walkCollectionTree(persister, alias, new PropertyPath(), 0);
/*  245:     */   }
/*  246:     */   
/*  247:     */   private void walkCollectionTree(QueryableCollection persister, String alias, PropertyPath path, int currentDepth)
/*  248:     */     throws MappingException
/*  249:     */   {
/*  250: 313 */     if (persister.isOneToMany())
/*  251:     */     {
/*  252: 314 */       walkEntityTree((OuterJoinLoadable)persister.getElementPersister(), alias, path, currentDepth);
/*  253:     */     }
/*  254:     */     else
/*  255:     */     {
/*  256: 322 */       Type type = persister.getElementType();
/*  257: 323 */       if (type.isAssociationType())
/*  258:     */       {
/*  259: 327 */         AssociationType associationType = (AssociationType)type;
/*  260: 328 */         String[] aliasedLhsColumns = persister.getElementColumnNames(alias);
/*  261: 329 */         String[] lhsColumns = persister.getElementColumnNames();
/*  262:     */         
/*  263:     */ 
/*  264:     */ 
/*  265: 333 */         boolean useInnerJoin = currentDepth == 0;
/*  266: 334 */         JoinType joinType = getJoinType(associationType, persister.getFetchMode(), path, persister.getTableName(), lhsColumns, !useInnerJoin, currentDepth - 1, null);
/*  267:     */         
/*  268:     */ 
/*  269:     */ 
/*  270:     */ 
/*  271:     */ 
/*  272:     */ 
/*  273:     */ 
/*  274:     */ 
/*  275:     */ 
/*  276: 344 */         addAssociationToJoinTreeIfNecessary(associationType, aliasedLhsColumns, alias, path, currentDepth - 1, joinType);
/*  277:     */       }
/*  278: 353 */       else if (type.isComponentType())
/*  279:     */       {
/*  280: 354 */         walkCompositeElementTree((CompositeType)type, persister.getElementColumnNames(), persister, alias, path, currentDepth);
/*  281:     */       }
/*  282:     */     }
/*  283:     */   }
/*  284:     */   
/*  285:     */   private void walkEntityAssociationTree(AssociationType associationType, OuterJoinLoadable persister, int propertyNumber, String alias, PropertyPath path, boolean nullable, int currentDepth)
/*  286:     */     throws MappingException
/*  287:     */   {
/*  288: 390 */     String[] aliasedLhsColumns = JoinHelper.getAliasedLHSColumnNames(associationType, alias, propertyNumber, persister, getFactory());
/*  289:     */     
/*  290:     */ 
/*  291: 393 */     String[] lhsColumns = JoinHelper.getLHSColumnNames(associationType, propertyNumber, persister, getFactory());
/*  292:     */     
/*  293:     */ 
/*  294: 396 */     String lhsTable = JoinHelper.getLHSTableName(associationType, propertyNumber, persister);
/*  295:     */     
/*  296: 398 */     PropertyPath subPath = path.append(persister.getSubclassPropertyName(propertyNumber));
/*  297: 399 */     JoinType joinType = getJoinType(persister, subPath, propertyNumber, associationType, persister.getFetchMode(propertyNumber), persister.getCascadeStyle(propertyNumber), lhsTable, lhsColumns, nullable, currentDepth);
/*  298:     */     
/*  299:     */ 
/*  300:     */ 
/*  301:     */ 
/*  302:     */ 
/*  303:     */ 
/*  304:     */ 
/*  305:     */ 
/*  306:     */ 
/*  307:     */ 
/*  308:     */ 
/*  309: 411 */     addAssociationToJoinTreeIfNecessary(associationType, aliasedLhsColumns, alias, subPath, currentDepth, joinType);
/*  310:     */   }
/*  311:     */   
/*  312:     */   protected JoinType getJoinType(OuterJoinLoadable persister, PropertyPath path, int propertyNumber, AssociationType associationType, FetchMode metadataFetchMode, CascadeStyle metadataCascadeStyle, String lhsTable, String[] lhsColumns, boolean nullable, int currentDepth)
/*  313:     */     throws MappingException
/*  314:     */   {
/*  315: 450 */     return getJoinType(associationType, metadataFetchMode, path, lhsTable, lhsColumns, nullable, currentDepth, metadataCascadeStyle);
/*  316:     */   }
/*  317:     */   
/*  318:     */   protected JoinType getJoinType(AssociationType associationType, FetchMode config, PropertyPath path, String lhsTable, String[] lhsColumns, boolean nullable, int currentDepth, CascadeStyle cascadeStyle)
/*  319:     */     throws MappingException
/*  320:     */   {
/*  321: 487 */     if (!isJoinedFetchEnabled(associationType, config, cascadeStyle)) {
/*  322: 488 */       return JoinType.NONE;
/*  323:     */     }
/*  324: 490 */     if ((isTooDeep(currentDepth)) || ((associationType.isCollectionType()) && (isTooManyCollections()))) {
/*  325: 491 */       return JoinType.NONE;
/*  326:     */     }
/*  327: 493 */     if (isDuplicateAssociation(lhsTable, lhsColumns, associationType)) {
/*  328: 494 */       return JoinType.NONE;
/*  329:     */     }
/*  330: 496 */     return getJoinType(nullable, currentDepth);
/*  331:     */   }
/*  332:     */   
/*  333:     */   private void walkEntityTree(OuterJoinLoadable persister, String alias, PropertyPath path, int currentDepth)
/*  334:     */     throws MappingException
/*  335:     */   {
/*  336: 516 */     int n = persister.countSubclassProperties();
/*  337: 517 */     for (int i = 0; i < n; i++)
/*  338:     */     {
/*  339: 518 */       Type type = persister.getSubclassPropertyType(i);
/*  340: 519 */       if (type.isAssociationType()) {
/*  341: 520 */         walkEntityAssociationTree((AssociationType)type, persister, i, alias, path, persister.isSubclassPropertyNullable(i), currentDepth);
/*  342: 530 */       } else if (type.isComponentType()) {
/*  343: 531 */         walkComponentTree((CompositeType)type, i, 0, persister, alias, path.append(persister.getSubclassPropertyName(i)), currentDepth);
/*  344:     */       }
/*  345:     */     }
/*  346:     */   }
/*  347:     */   
/*  348:     */   private void walkComponentTree(CompositeType componentType, int propertyNumber, int begin, OuterJoinLoadable persister, String alias, PropertyPath path, int currentDepth)
/*  349:     */     throws MappingException
/*  350:     */   {
/*  351: 566 */     Type[] types = componentType.getSubtypes();
/*  352: 567 */     String[] propertyNames = componentType.getPropertyNames();
/*  353: 568 */     for (int i = 0; i < types.length; i++)
/*  354:     */     {
/*  355: 569 */       if (types[i].isAssociationType())
/*  356:     */       {
/*  357: 570 */         AssociationType associationType = (AssociationType)types[i];
/*  358: 571 */         String[] aliasedLhsColumns = JoinHelper.getAliasedLHSColumnNames(associationType, alias, propertyNumber, begin, persister, getFactory());
/*  359:     */         
/*  360:     */ 
/*  361: 574 */         String[] lhsColumns = JoinHelper.getLHSColumnNames(associationType, propertyNumber, begin, persister, getFactory());
/*  362:     */         
/*  363:     */ 
/*  364: 577 */         String lhsTable = JoinHelper.getLHSTableName(associationType, propertyNumber, persister);
/*  365:     */         
/*  366: 579 */         PropertyPath subPath = path.append(propertyNames[i]);
/*  367: 580 */         boolean[] propertyNullability = componentType.getPropertyNullability();
/*  368: 581 */         JoinType joinType = getJoinType(persister, subPath, propertyNumber, associationType, componentType.getFetchMode(i), componentType.getCascadeStyle(i), lhsTable, lhsColumns, (propertyNullability == null) || (propertyNullability[i] != 0), currentDepth);
/*  369:     */         
/*  370:     */ 
/*  371:     */ 
/*  372:     */ 
/*  373:     */ 
/*  374:     */ 
/*  375:     */ 
/*  376:     */ 
/*  377:     */ 
/*  378:     */ 
/*  379:     */ 
/*  380: 593 */         addAssociationToJoinTreeIfNecessary(associationType, aliasedLhsColumns, alias, subPath, currentDepth, joinType);
/*  381:     */       }
/*  382: 603 */       else if (types[i].isComponentType())
/*  383:     */       {
/*  384: 604 */         PropertyPath subPath = path.append(propertyNames[i]);
/*  385: 605 */         walkComponentTree((CompositeType)types[i], propertyNumber, begin, persister, alias, subPath, currentDepth);
/*  386:     */       }
/*  387: 615 */       begin += types[i].getColumnSpan(getFactory());
/*  388:     */     }
/*  389:     */   }
/*  390:     */   
/*  391:     */   private void walkCompositeElementTree(CompositeType compositeType, String[] cols, QueryableCollection persister, String alias, PropertyPath path, int currentDepth)
/*  392:     */     throws MappingException
/*  393:     */   {
/*  394: 631 */     Type[] types = compositeType.getSubtypes();
/*  395: 632 */     String[] propertyNames = compositeType.getPropertyNames();
/*  396: 633 */     int begin = 0;
/*  397: 634 */     for (int i = 0; i < types.length; i++)
/*  398:     */     {
/*  399: 635 */       int length = types[i].getColumnSpan(getFactory());
/*  400: 636 */       String[] lhsColumns = ArrayHelper.slice(cols, begin, length);
/*  401: 638 */       if (types[i].isAssociationType())
/*  402:     */       {
/*  403: 639 */         AssociationType associationType = (AssociationType)types[i];
/*  404:     */         
/*  405:     */ 
/*  406:     */ 
/*  407: 643 */         String[] aliasedLhsColumns = StringHelper.qualify(alias, lhsColumns);
/*  408:     */         
/*  409: 645 */         PropertyPath subPath = path.append(propertyNames[i]);
/*  410: 646 */         boolean[] propertyNullability = compositeType.getPropertyNullability();
/*  411: 647 */         JoinType joinType = getJoinType(associationType, compositeType.getFetchMode(i), subPath, persister.getTableName(), lhsColumns, (propertyNullability == null) || (propertyNullability[i] != 0), currentDepth, compositeType.getCascadeStyle(i));
/*  412:     */         
/*  413:     */ 
/*  414:     */ 
/*  415:     */ 
/*  416:     */ 
/*  417:     */ 
/*  418:     */ 
/*  419:     */ 
/*  420:     */ 
/*  421: 657 */         addAssociationToJoinTreeIfNecessary(associationType, aliasedLhsColumns, alias, subPath, currentDepth, joinType);
/*  422:     */       }
/*  423: 666 */       else if (types[i].isComponentType())
/*  424:     */       {
/*  425: 667 */         PropertyPath subPath = path.append(propertyNames[i]);
/*  426: 668 */         walkCompositeElementTree((CompositeType)types[i], lhsColumns, persister, alias, subPath, currentDepth);
/*  427:     */       }
/*  428: 677 */       begin += length;
/*  429:     */     }
/*  430:     */   }
/*  431:     */   
/*  432:     */   protected JoinType getJoinType(boolean nullable, int currentDepth)
/*  433:     */   {
/*  434: 692 */     return (!nullable) && (currentDepth <= 0) ? JoinType.INNER_JOIN : JoinType.LEFT_OUTER_JOIN;
/*  435:     */   }
/*  436:     */   
/*  437:     */   protected boolean isTooDeep(int currentDepth)
/*  438:     */   {
/*  439: 698 */     Integer maxFetchDepth = getFactory().getSettings().getMaximumFetchDepth();
/*  440: 699 */     return (maxFetchDepth != null) && (currentDepth >= maxFetchDepth.intValue());
/*  441:     */   }
/*  442:     */   
/*  443:     */   protected boolean isTooManyCollections()
/*  444:     */   {
/*  445: 703 */     return false;
/*  446:     */   }
/*  447:     */   
/*  448:     */   protected boolean isJoinedFetchEnabledInMapping(FetchMode config, AssociationType type)
/*  449:     */     throws MappingException
/*  450:     */   {
/*  451: 712 */     if ((!type.isEntityType()) && (!type.isCollectionType())) {
/*  452: 713 */       return false;
/*  453:     */     }
/*  454: 716 */     if (config == FetchMode.JOIN) {
/*  455: 716 */       return true;
/*  456:     */     }
/*  457: 717 */     if (config == FetchMode.SELECT) {
/*  458: 717 */       return false;
/*  459:     */     }
/*  460: 718 */     if (type.isEntityType())
/*  461:     */     {
/*  462: 721 */       EntityType entityType = (EntityType)type;
/*  463: 722 */       EntityPersister persister = getFactory().getEntityPersister(entityType.getAssociatedEntityName());
/*  464: 723 */       return !persister.hasProxy();
/*  465:     */     }
/*  466: 726 */     return false;
/*  467:     */   }
/*  468:     */   
/*  469:     */   protected boolean isJoinedFetchEnabled(AssociationType type, FetchMode config, CascadeStyle cascadeStyle)
/*  470:     */   {
/*  471: 736 */     return (type.isEntityType()) && (isJoinedFetchEnabledInMapping(config, type));
/*  472:     */   }
/*  473:     */   
/*  474:     */   protected String generateTableAlias(int n, PropertyPath path, Joinable joinable)
/*  475:     */   {
/*  476: 740 */     return StringHelper.generateAlias(joinable.getName(), n);
/*  477:     */   }
/*  478:     */   
/*  479:     */   protected String generateRootAlias(String description)
/*  480:     */   {
/*  481: 744 */     return StringHelper.generateAlias(description, 0);
/*  482:     */   }
/*  483:     */   
/*  484:     */   protected boolean isDuplicateAssociation(String foreignKeyTable, String[] foreignKeyColumns)
/*  485:     */   {
/*  486: 752 */     AssociationKey associationKey = new AssociationKey(foreignKeyColumns, foreignKeyTable, null);
/*  487: 753 */     return !this.visitedAssociationKeys.add(associationKey);
/*  488:     */   }
/*  489:     */   
/*  490:     */   protected boolean isDuplicateAssociation(String lhsTable, String[] lhsColumnNames, AssociationType type)
/*  491:     */   {
/*  492:     */     String[] foreignKeyColumns;
/*  493:     */     String foreignKeyTable;
/*  494:     */     String[] foreignKeyColumns;
/*  495: 763 */     if (type.getForeignKeyDirection() == ForeignKeyDirection.FOREIGN_KEY_FROM_PARENT)
/*  496:     */     {
/*  497: 764 */       String foreignKeyTable = lhsTable;
/*  498: 765 */       foreignKeyColumns = lhsColumnNames;
/*  499:     */     }
/*  500:     */     else
/*  501:     */     {
/*  502: 768 */       foreignKeyTable = type.getAssociatedJoinable(getFactory()).getTableName();
/*  503: 769 */       foreignKeyColumns = JoinHelper.getRHSColumnNames(type, getFactory());
/*  504:     */     }
/*  505: 771 */     return isDuplicateAssociation(foreignKeyTable, foreignKeyColumns);
/*  506:     */   }
/*  507:     */   
/*  508:     */   private static final class AssociationKey
/*  509:     */   {
/*  510:     */     private String[] columns;
/*  511:     */     private String table;
/*  512:     */     
/*  513:     */     private AssociationKey(String[] columns, String table)
/*  514:     */     {
/*  515: 782 */       this.columns = columns;
/*  516: 783 */       this.table = table;
/*  517:     */     }
/*  518:     */     
/*  519:     */     public boolean equals(Object other)
/*  520:     */     {
/*  521: 787 */       AssociationKey that = (AssociationKey)other;
/*  522: 788 */       return (that.table.equals(this.table)) && (Arrays.equals(this.columns, that.columns));
/*  523:     */     }
/*  524:     */     
/*  525:     */     public int hashCode()
/*  526:     */     {
/*  527: 792 */       return this.table.hashCode();
/*  528:     */     }
/*  529:     */   }
/*  530:     */   
/*  531:     */   protected boolean isJoinable(JoinType joinType, Set visitedAssociationKeys, String lhsTable, String[] lhsColumnNames, AssociationType type, int depth)
/*  532:     */   {
/*  533: 807 */     if (joinType == JoinType.NONE) {
/*  534: 808 */       return false;
/*  535:     */     }
/*  536: 811 */     if (joinType == JoinType.INNER_JOIN) {
/*  537: 812 */       return true;
/*  538:     */     }
/*  539: 815 */     Integer maxFetchDepth = getFactory().getSettings().getMaximumFetchDepth();
/*  540: 816 */     boolean tooDeep = (maxFetchDepth != null) && (depth >= maxFetchDepth.intValue());
/*  541:     */     
/*  542: 818 */     return (!tooDeep) && (!isDuplicateAssociation(lhsTable, lhsColumnNames, type));
/*  543:     */   }
/*  544:     */   
/*  545:     */   protected String orderBy(List associations, String orderBy)
/*  546:     */   {
/*  547: 822 */     return mergeOrderings(orderBy(associations), orderBy);
/*  548:     */   }
/*  549:     */   
/*  550:     */   protected static String mergeOrderings(String ordering1, String ordering2)
/*  551:     */   {
/*  552: 826 */     if (ordering1.length() == 0) {
/*  553: 827 */       return ordering2;
/*  554:     */     }
/*  555: 829 */     if (ordering2.length() == 0) {
/*  556: 830 */       return ordering1;
/*  557:     */     }
/*  558: 833 */     return ordering1 + ", " + ordering2;
/*  559:     */   }
/*  560:     */   
/*  561:     */   protected final JoinFragment mergeOuterJoins(List associations)
/*  562:     */     throws MappingException
/*  563:     */   {
/*  564: 842 */     JoinFragment outerjoin = getDialect().createOuterJoinFragment();
/*  565: 843 */     Iterator iter = associations.iterator();
/*  566: 844 */     OuterJoinableAssociation last = null;
/*  567: 845 */     while (iter.hasNext())
/*  568:     */     {
/*  569: 846 */       OuterJoinableAssociation oj = (OuterJoinableAssociation)iter.next();
/*  570: 847 */       if ((last != null) && (last.isManyToManyWith(oj))) {
/*  571: 848 */         oj.addManyToManyJoin(outerjoin, (QueryableCollection)last.getJoinable());
/*  572:     */       } else {
/*  573: 851 */         oj.addJoins(outerjoin);
/*  574:     */       }
/*  575: 853 */       last = oj;
/*  576:     */     }
/*  577: 855 */     last = null;
/*  578: 856 */     return outerjoin;
/*  579:     */   }
/*  580:     */   
/*  581:     */   protected static final int countEntityPersisters(List associations)
/*  582:     */     throws MappingException
/*  583:     */   {
/*  584: 865 */     int result = 0;
/*  585: 866 */     Iterator iter = associations.iterator();
/*  586: 867 */     while (iter.hasNext())
/*  587:     */     {
/*  588: 868 */       OuterJoinableAssociation oj = (OuterJoinableAssociation)iter.next();
/*  589: 869 */       if (oj.getJoinable().consumesEntityAlias()) {
/*  590: 870 */         result++;
/*  591:     */       }
/*  592:     */     }
/*  593: 873 */     return result;
/*  594:     */   }
/*  595:     */   
/*  596:     */   protected static final int countCollectionPersisters(List associations)
/*  597:     */     throws MappingException
/*  598:     */   {
/*  599: 883 */     int result = 0;
/*  600: 884 */     Iterator iter = associations.iterator();
/*  601: 885 */     while (iter.hasNext())
/*  602:     */     {
/*  603: 886 */       OuterJoinableAssociation oj = (OuterJoinableAssociation)iter.next();
/*  604: 887 */       if ((oj.getJoinType() == JoinType.LEFT_OUTER_JOIN) && (oj.getJoinable().isCollection()) && (!oj.hasRestriction())) {
/*  605: 890 */         result++;
/*  606:     */       }
/*  607:     */     }
/*  608: 893 */     return result;
/*  609:     */   }
/*  610:     */   
/*  611:     */   protected static final String orderBy(List associations)
/*  612:     */     throws MappingException
/*  613:     */   {
/*  614: 901 */     StringBuffer buf = new StringBuffer();
/*  615: 902 */     Iterator iter = associations.iterator();
/*  616: 903 */     OuterJoinableAssociation last = null;
/*  617: 904 */     while (iter.hasNext())
/*  618:     */     {
/*  619: 905 */       OuterJoinableAssociation oj = (OuterJoinableAssociation)iter.next();
/*  620: 906 */       if (oj.getJoinType() == JoinType.LEFT_OUTER_JOIN) {
/*  621: 907 */         if (oj.getJoinable().isCollection())
/*  622:     */         {
/*  623: 908 */           QueryableCollection queryableCollection = (QueryableCollection)oj.getJoinable();
/*  624: 909 */           if (queryableCollection.hasOrdering())
/*  625:     */           {
/*  626: 910 */             String orderByString = queryableCollection.getSQLOrderByString(oj.getRHSAlias());
/*  627: 911 */             buf.append(orderByString).append(", ");
/*  628:     */           }
/*  629:     */         }
/*  630: 917 */         else if ((last != null) && (last.getJoinable().isCollection()))
/*  631:     */         {
/*  632: 918 */           QueryableCollection queryableCollection = (QueryableCollection)last.getJoinable();
/*  633: 919 */           if ((queryableCollection.isManyToMany()) && (last.isManyToManyWith(oj)) && 
/*  634: 920 */             (queryableCollection.hasManyToManyOrdering()))
/*  635:     */           {
/*  636: 921 */             String orderByString = queryableCollection.getManyToManyOrderByString(oj.getRHSAlias());
/*  637: 922 */             buf.append(orderByString).append(", ");
/*  638:     */           }
/*  639:     */         }
/*  640:     */       }
/*  641: 928 */       last = oj;
/*  642:     */     }
/*  643: 930 */     if (buf.length() > 0) {
/*  644: 930 */       buf.setLength(buf.length() - 2);
/*  645:     */     }
/*  646: 931 */     return buf.toString();
/*  647:     */   }
/*  648:     */   
/*  649:     */   protected StringBuffer whereString(String alias, String[] columnNames, int batchSize)
/*  650:     */   {
/*  651: 938 */     if (columnNames.length == 1)
/*  652:     */     {
/*  653: 941 */       InFragment in = new InFragment().setColumn(alias, columnNames[0]);
/*  654: 942 */       for (int i = 0; i < batchSize; i++) {
/*  655: 942 */         in.addValue("?");
/*  656:     */       }
/*  657: 943 */       return new StringBuffer(in.toFragmentString());
/*  658:     */     }
/*  659: 947 */     ConditionFragment byId = new ConditionFragment().setTableAlias(alias).setCondition(columnNames, "?");
/*  660:     */     
/*  661:     */ 
/*  662:     */ 
/*  663: 951 */     StringBuffer whereString = new StringBuffer();
/*  664: 952 */     if (batchSize == 1)
/*  665:     */     {
/*  666: 954 */       whereString.append(byId.toFragmentString());
/*  667:     */     }
/*  668:     */     else
/*  669:     */     {
/*  670: 958 */       whereString.append('(');
/*  671: 959 */       DisjunctionFragment df = new DisjunctionFragment();
/*  672: 960 */       for (int i = 0; i < batchSize; i++) {
/*  673: 961 */         df.addCondition(byId);
/*  674:     */       }
/*  675: 963 */       whereString.append(df.toFragmentString());
/*  676: 964 */       whereString.append(')');
/*  677:     */     }
/*  678: 966 */     return whereString;
/*  679:     */   }
/*  680:     */   
/*  681:     */   protected void initPersisters(List associations, LockMode lockMode)
/*  682:     */     throws MappingException
/*  683:     */   {
/*  684: 972 */     initPersisters(associations, new LockOptions(lockMode));
/*  685:     */   }
/*  686:     */   
/*  687:     */   protected static abstract interface AssociationInitCallback
/*  688:     */   {
/*  689: 976 */     public static final AssociationInitCallback NO_CALLBACK = new AssociationInitCallback()
/*  690:     */     {
/*  691:     */       public void associationProcessed(OuterJoinableAssociation oja, int position) {}
/*  692:     */     };
/*  693:     */     
/*  694:     */     public abstract void associationProcessed(OuterJoinableAssociation paramOuterJoinableAssociation, int paramInt);
/*  695:     */   }
/*  696:     */   
/*  697:     */   protected void initPersisters(List associations, LockOptions lockOptions)
/*  698:     */     throws MappingException
/*  699:     */   {
/*  700: 984 */     initPersisters(associations, lockOptions, AssociationInitCallback.NO_CALLBACK);
/*  701:     */   }
/*  702:     */   
/*  703:     */   protected void initPersisters(List associations, LockOptions lockOptions, AssociationInitCallback callback)
/*  704:     */     throws MappingException
/*  705:     */   {
/*  706: 991 */     int joins = countEntityPersisters(associations);
/*  707: 992 */     int collections = countCollectionPersisters(associations);
/*  708:     */     
/*  709: 994 */     this.collectionOwners = (collections == 0 ? null : new int[collections]);
/*  710: 995 */     this.collectionPersisters = (collections == 0 ? null : new CollectionPersister[collections]);
/*  711: 996 */     this.collectionSuffixes = BasicLoader.generateSuffixes(joins + 1, collections);
/*  712:     */     
/*  713: 998 */     this.lockOptions = lockOptions;
/*  714:     */     
/*  715:1000 */     this.persisters = new Loadable[joins];
/*  716:1001 */     this.aliases = new String[joins];
/*  717:1002 */     this.owners = new int[joins];
/*  718:1003 */     this.ownerAssociationTypes = new EntityType[joins];
/*  719:1004 */     this.lockModeArray = ArrayHelper.fillArray(lockOptions.getLockMode(), joins);
/*  720:     */     
/*  721:1006 */     int i = 0;
/*  722:1007 */     int j = 0;
/*  723:1008 */     Iterator iter = associations.iterator();
/*  724:1009 */     while (iter.hasNext())
/*  725:     */     {
/*  726:1010 */       OuterJoinableAssociation oj = (OuterJoinableAssociation)iter.next();
/*  727:1011 */       if (!oj.isCollection())
/*  728:     */       {
/*  729:1013 */         this.persisters[i] = ((Loadable)oj.getJoinable());
/*  730:1014 */         this.aliases[i] = oj.getRHSAlias();
/*  731:1015 */         this.owners[i] = oj.getOwner(associations);
/*  732:1016 */         this.ownerAssociationTypes[i] = ((EntityType)oj.getJoinableType());
/*  733:1017 */         callback.associationProcessed(oj, i);
/*  734:1018 */         i++;
/*  735:     */       }
/*  736:     */       else
/*  737:     */       {
/*  738:1023 */         QueryableCollection collPersister = (QueryableCollection)oj.getJoinable();
/*  739:1024 */         if ((oj.getJoinType() == JoinType.LEFT_OUTER_JOIN) && (!oj.hasRestriction()))
/*  740:     */         {
/*  741:1026 */           this.collectionPersisters[j] = collPersister;
/*  742:1027 */           this.collectionOwners[j] = oj.getOwner(associations);
/*  743:1028 */           j++;
/*  744:     */         }
/*  745:1031 */         if (collPersister.isOneToMany())
/*  746:     */         {
/*  747:1032 */           this.persisters[i] = ((Loadable)collPersister.getElementPersister());
/*  748:1033 */           this.aliases[i] = oj.getRHSAlias();
/*  749:1034 */           callback.associationProcessed(oj, i);
/*  750:1035 */           i++;
/*  751:     */         }
/*  752:     */       }
/*  753:     */     }
/*  754:1040 */     if (ArrayHelper.isAllNegative(this.owners)) {
/*  755:1040 */       this.owners = null;
/*  756:     */     }
/*  757:1041 */     if ((this.collectionOwners != null) && (ArrayHelper.isAllNegative(this.collectionOwners))) {
/*  758:1042 */       this.collectionOwners = null;
/*  759:     */     }
/*  760:     */   }
/*  761:     */   
/*  762:     */   protected final String selectString(List associations)
/*  763:     */     throws MappingException
/*  764:     */   {
/*  765:1052 */     if (associations.size() == 0) {
/*  766:1053 */       return "";
/*  767:     */     }
/*  768:1056 */     StringBuffer buf = new StringBuffer(associations.size() * 100);
/*  769:1057 */     int entityAliasCount = 0;
/*  770:1058 */     int collectionAliasCount = 0;
/*  771:1059 */     for (int i = 0; i < associations.size(); i++)
/*  772:     */     {
/*  773:1060 */       OuterJoinableAssociation join = (OuterJoinableAssociation)associations.get(i);
/*  774:1061 */       OuterJoinableAssociation next = i == associations.size() - 1 ? null : (OuterJoinableAssociation)associations.get(i + 1);
/*  775:     */       
/*  776:     */ 
/*  777:1064 */       Joinable joinable = join.getJoinable();
/*  778:1065 */       String entitySuffix = (this.suffixes == null) || (entityAliasCount >= this.suffixes.length) ? null : this.suffixes[entityAliasCount];
/*  779:     */       
/*  780:     */ 
/*  781:1068 */       String collectionSuffix = (this.collectionSuffixes == null) || (collectionAliasCount >= this.collectionSuffixes.length) ? null : this.collectionSuffixes[collectionAliasCount];
/*  782:     */       
/*  783:     */ 
/*  784:1071 */       String selectFragment = joinable.selectFragment(next == null ? null : next.getJoinable(), next == null ? null : next.getRHSAlias(), join.getRHSAlias(), entitySuffix, collectionSuffix, join.getJoinType() == JoinType.LEFT_OUTER_JOIN);
/*  785:1079 */       if (selectFragment.trim().length() > 0) {
/*  786:1080 */         buf.append(", ").append(selectFragment);
/*  787:     */       }
/*  788:1082 */       if (joinable.consumesEntityAlias()) {
/*  789:1082 */         entityAliasCount++;
/*  790:     */       }
/*  791:1083 */       if ((joinable.consumesCollectionAlias()) && (join.getJoinType() == JoinType.LEFT_OUTER_JOIN)) {
/*  792:1083 */         collectionAliasCount++;
/*  793:     */       }
/*  794:     */     }
/*  795:1085 */     return buf.toString();
/*  796:     */   }
/*  797:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.loader.JoinWalker
 * JD-Core Version:    0.7.0.1
 */