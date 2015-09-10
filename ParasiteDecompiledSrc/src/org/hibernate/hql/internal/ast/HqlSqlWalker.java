/*    1:     */ package org.hibernate.hql.internal.ast;
/*    2:     */ 
/*    3:     */ import antlr.ASTFactory;
/*    4:     */ import antlr.RecognitionException;
/*    5:     */ import antlr.SemanticException;
/*    6:     */ import antlr.TreeParserSharedInputState;
/*    7:     */ import antlr.collections.AST;
/*    8:     */ import java.io.Serializable;
/*    9:     */ import java.util.ArrayList;
/*   10:     */ import java.util.Arrays;
/*   11:     */ import java.util.Calendar;
/*   12:     */ import java.util.Date;
/*   13:     */ import java.util.HashMap;
/*   14:     */ import java.util.HashSet;
/*   15:     */ import java.util.Iterator;
/*   16:     */ import java.util.List;
/*   17:     */ import java.util.Map;
/*   18:     */ import java.util.Set;
/*   19:     */ import org.hibernate.HibernateException;
/*   20:     */ import org.hibernate.QueryException;
/*   21:     */ import org.hibernate.dialect.Dialect;
/*   22:     */ import org.hibernate.engine.internal.JoinSequence;
/*   23:     */ import org.hibernate.engine.internal.ParameterBinder.NamedParameterSource;
/*   24:     */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*   25:     */ import org.hibernate.hql.internal.antlr.HqlSqlBaseWalker;
/*   26:     */ import org.hibernate.hql.internal.antlr.SqlTokenTypes;
/*   27:     */ import org.hibernate.hql.internal.ast.tree.AggregateNode;
/*   28:     */ import org.hibernate.hql.internal.ast.tree.AssignmentSpecification;
/*   29:     */ import org.hibernate.hql.internal.ast.tree.CollectionFunction;
/*   30:     */ import org.hibernate.hql.internal.ast.tree.ConstructorNode;
/*   31:     */ import org.hibernate.hql.internal.ast.tree.DeleteStatement;
/*   32:     */ import org.hibernate.hql.internal.ast.tree.DotNode;
/*   33:     */ import org.hibernate.hql.internal.ast.tree.FromClause;
/*   34:     */ import org.hibernate.hql.internal.ast.tree.FromElement;
/*   35:     */ import org.hibernate.hql.internal.ast.tree.FromElementFactory;
/*   36:     */ import org.hibernate.hql.internal.ast.tree.FromReferenceNode;
/*   37:     */ import org.hibernate.hql.internal.ast.tree.IdentNode;
/*   38:     */ import org.hibernate.hql.internal.ast.tree.IndexNode;
/*   39:     */ import org.hibernate.hql.internal.ast.tree.InsertStatement;
/*   40:     */ import org.hibernate.hql.internal.ast.tree.IntoClause;
/*   41:     */ import org.hibernate.hql.internal.ast.tree.MethodNode;
/*   42:     */ import org.hibernate.hql.internal.ast.tree.OperatorNode;
/*   43:     */ import org.hibernate.hql.internal.ast.tree.OrderByClause;
/*   44:     */ import org.hibernate.hql.internal.ast.tree.ParameterContainer;
/*   45:     */ import org.hibernate.hql.internal.ast.tree.ParameterNode;
/*   46:     */ import org.hibernate.hql.internal.ast.tree.QueryNode;
/*   47:     */ import org.hibernate.hql.internal.ast.tree.ResolvableNode;
/*   48:     */ import org.hibernate.hql.internal.ast.tree.RestrictableStatement;
/*   49:     */ import org.hibernate.hql.internal.ast.tree.ResultVariableRefNode;
/*   50:     */ import org.hibernate.hql.internal.ast.tree.SelectClause;
/*   51:     */ import org.hibernate.hql.internal.ast.tree.SelectExpression;
/*   52:     */ import org.hibernate.hql.internal.ast.tree.UpdateStatement;
/*   53:     */ import org.hibernate.hql.internal.ast.util.ASTPrinter;
/*   54:     */ import org.hibernate.hql.internal.ast.util.ASTUtil;
/*   55:     */ import org.hibernate.hql.internal.ast.util.AliasGenerator;
/*   56:     */ import org.hibernate.hql.internal.ast.util.JoinProcessor;
/*   57:     */ import org.hibernate.hql.internal.ast.util.LiteralProcessor;
/*   58:     */ import org.hibernate.hql.internal.ast.util.NodeTraverser;
/*   59:     */ import org.hibernate.hql.internal.ast.util.NodeTraverser.VisitationStrategy;
/*   60:     */ import org.hibernate.hql.internal.ast.util.SessionFactoryHelper;
/*   61:     */ import org.hibernate.hql.internal.ast.util.SyntheticAndFactory;
/*   62:     */ import org.hibernate.id.IdentifierGenerator;
/*   63:     */ import org.hibernate.id.PostInsertIdentifierGenerator;
/*   64:     */ import org.hibernate.id.SequenceGenerator;
/*   65:     */ import org.hibernate.internal.CoreMessageLogger;
/*   66:     */ import org.hibernate.internal.util.StringHelper;
/*   67:     */ import org.hibernate.internal.util.collections.ArrayHelper;
/*   68:     */ import org.hibernate.param.CollectionFilterKeyParameterSpecification;
/*   69:     */ import org.hibernate.param.NamedParameterSpecification;
/*   70:     */ import org.hibernate.param.ParameterSpecification;
/*   71:     */ import org.hibernate.param.PositionalParameterSpecification;
/*   72:     */ import org.hibernate.param.VersionTypeSeedParameterSpecification;
/*   73:     */ import org.hibernate.persister.collection.QueryableCollection;
/*   74:     */ import org.hibernate.persister.entity.EntityPersister;
/*   75:     */ import org.hibernate.persister.entity.PropertyMapping;
/*   76:     */ import org.hibernate.persister.entity.Queryable;
/*   77:     */ import org.hibernate.sql.JoinType;
/*   78:     */ import org.hibernate.type.AssociationType;
/*   79:     */ import org.hibernate.type.CollectionType;
/*   80:     */ import org.hibernate.type.ComponentType;
/*   81:     */ import org.hibernate.type.DbTimestampType;
/*   82:     */ import org.hibernate.type.Type;
/*   83:     */ import org.hibernate.type.VersionType;
/*   84:     */ import org.hibernate.usertype.UserVersionType;
/*   85:     */ import org.jboss.logging.Logger;
/*   86:     */ 
/*   87:     */ public class HqlSqlWalker
/*   88:     */   extends HqlSqlBaseWalker
/*   89:     */   implements ErrorReporter, ParameterBinder.NamedParameterSource
/*   90:     */ {
/*   91: 122 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, HqlSqlWalker.class.getName());
/*   92:     */   private final QueryTranslatorImpl queryTranslatorImpl;
/*   93:     */   private final HqlParser hqlParser;
/*   94:     */   private final SessionFactoryHelper sessionFactoryHelper;
/*   95:     */   private final Map tokenReplacements;
/*   96: 128 */   private final AliasGenerator aliasGenerator = new AliasGenerator();
/*   97:     */   private final LiteralProcessor literalProcessor;
/*   98:     */   private final ParseErrorHandler parseErrorHandler;
/*   99:     */   private final ASTPrinter printer;
/*  100:     */   private final String collectionFilterRole;
/*  101: 134 */   private FromClause currentFromClause = null;
/*  102:     */   private SelectClause selectClause;
/*  103: 141 */   private Map<String, SelectExpression> selectExpressionsByResultVariable = new HashMap();
/*  104: 143 */   private Set querySpaces = new HashSet();
/*  105:     */   private int parameterCount;
/*  106: 146 */   private Map namedParameters = new HashMap();
/*  107: 147 */   private ArrayList parameters = new ArrayList();
/*  108:     */   private int numberOfParametersInSetClause;
/*  109:     */   private int positionalParameterCount;
/*  110: 151 */   private ArrayList assignmentSpecifications = new ArrayList();
/*  111: 153 */   private JoinType impliedJoinType = JoinType.INNER_JOIN;
/*  112:     */   
/*  113:     */   public HqlSqlWalker(QueryTranslatorImpl qti, SessionFactoryImplementor sfi, HqlParser parser, Map tokenReplacements, String collectionRole)
/*  114:     */   {
/*  115: 172 */     setASTFactory(new SqlASTFactory(this));
/*  116:     */     
/*  117: 174 */     this.parseErrorHandler = new ErrorCounter();
/*  118: 175 */     this.queryTranslatorImpl = qti;
/*  119: 176 */     this.sessionFactoryHelper = new SessionFactoryHelper(sfi);
/*  120: 177 */     this.literalProcessor = new LiteralProcessor(this);
/*  121: 178 */     this.tokenReplacements = tokenReplacements;
/*  122: 179 */     this.collectionFilterRole = collectionRole;
/*  123: 180 */     this.hqlParser = parser;
/*  124: 181 */     this.printer = new ASTPrinter(SqlTokenTypes.class);
/*  125:     */   }
/*  126:     */   
/*  127: 187 */   private int traceDepth = 0;
/*  128:     */   
/*  129:     */   public void traceIn(String ruleName, AST tree)
/*  130:     */   {
/*  131: 191 */     if (!LOG.isTraceEnabled()) {
/*  132: 191 */       return;
/*  133:     */     }
/*  134: 192 */     if (this.inputState.guessing > 0) {
/*  135: 192 */       return;
/*  136:     */     }
/*  137: 193 */     String prefix = StringHelper.repeat('-', this.traceDepth++ * 2) + "-> ";
/*  138: 194 */     String traceText = ruleName + " (" + buildTraceNodeName(tree) + ")";
/*  139: 195 */     LOG.trace(prefix + traceText);
/*  140:     */   }
/*  141:     */   
/*  142:     */   private String buildTraceNodeName(AST tree)
/*  143:     */   {
/*  144: 199 */     return tree.getText() + " [" + this.printer.getTokenTypeName(tree.getType()) + "]";
/*  145:     */   }
/*  146:     */   
/*  147:     */   public void traceOut(String ruleName, AST tree)
/*  148:     */   {
/*  149: 206 */     if (!LOG.isTraceEnabled()) {
/*  150: 206 */       return;
/*  151:     */     }
/*  152: 207 */     if (this.inputState.guessing > 0) {
/*  153: 207 */       return;
/*  154:     */     }
/*  155: 208 */     String prefix = "<-" + StringHelper.repeat('-', --this.traceDepth * 2) + " ";
/*  156: 209 */     LOG.trace(prefix + ruleName);
/*  157:     */   }
/*  158:     */   
/*  159:     */   protected void prepareFromClauseInputTree(AST fromClauseInput)
/*  160:     */   {
/*  161: 214 */     if (!isSubQuery()) {
/*  162: 240 */       if (isFilter())
/*  163:     */       {
/*  164: 243 */         QueryableCollection persister = this.sessionFactoryHelper.getCollectionPersister(this.collectionFilterRole);
/*  165: 244 */         Type collectionElementType = persister.getElementType();
/*  166: 245 */         if (!collectionElementType.isEntityType()) {
/*  167: 246 */           throw new QueryException("collection of values in filter: this");
/*  168:     */         }
/*  169: 249 */         String collectionElementEntityName = persister.getElementPersister().getEntityName();
/*  170: 250 */         ASTFactory inputAstFactory = this.hqlParser.getASTFactory();
/*  171: 251 */         AST fromElement = ASTUtil.create(inputAstFactory, 76, collectionElementEntityName);
/*  172: 252 */         ASTUtil.createSibling(inputAstFactory, 72, "this", fromElement);
/*  173: 253 */         fromClauseInput.addChild(fromElement);
/*  174:     */         
/*  175: 255 */         LOG.debug("prepareFromClauseInputTree() : Filter - Added 'this' as a from element...");
/*  176: 256 */         this.queryTranslatorImpl.showHqlAst(this.hqlParser.getAST());
/*  177:     */         
/*  178:     */ 
/*  179: 259 */         Type collectionFilterKeyType = this.sessionFactoryHelper.requireQueryableCollection(this.collectionFilterRole).getKeyType();
/*  180: 260 */         ParameterNode collectionFilterKeyParameter = (ParameterNode)this.astFactory.create(123, "?");
/*  181: 261 */         CollectionFilterKeyParameterSpecification collectionFilterKeyParameterSpec = new CollectionFilterKeyParameterSpecification(this.collectionFilterRole, collectionFilterKeyType, this.positionalParameterCount++);
/*  182:     */         
/*  183:     */ 
/*  184: 264 */         collectionFilterKeyParameter.setHqlParameterSpecification(collectionFilterKeyParameterSpec);
/*  185: 265 */         this.parameters.add(collectionFilterKeyParameterSpec);
/*  186:     */       }
/*  187:     */     }
/*  188:     */   }
/*  189:     */   
/*  190:     */   public boolean isFilter()
/*  191:     */   {
/*  192: 271 */     return this.collectionFilterRole != null;
/*  193:     */   }
/*  194:     */   
/*  195:     */   public String getCollectionFilterRole()
/*  196:     */   {
/*  197: 275 */     return this.collectionFilterRole;
/*  198:     */   }
/*  199:     */   
/*  200:     */   public SessionFactoryHelper getSessionFactoryHelper()
/*  201:     */   {
/*  202: 279 */     return this.sessionFactoryHelper;
/*  203:     */   }
/*  204:     */   
/*  205:     */   public Map getTokenReplacements()
/*  206:     */   {
/*  207: 283 */     return this.tokenReplacements;
/*  208:     */   }
/*  209:     */   
/*  210:     */   public AliasGenerator getAliasGenerator()
/*  211:     */   {
/*  212: 287 */     return this.aliasGenerator;
/*  213:     */   }
/*  214:     */   
/*  215:     */   public FromClause getCurrentFromClause()
/*  216:     */   {
/*  217: 291 */     return this.currentFromClause;
/*  218:     */   }
/*  219:     */   
/*  220:     */   public ParseErrorHandler getParseErrorHandler()
/*  221:     */   {
/*  222: 295 */     return this.parseErrorHandler;
/*  223:     */   }
/*  224:     */   
/*  225:     */   public void reportError(RecognitionException e)
/*  226:     */   {
/*  227: 300 */     this.parseErrorHandler.reportError(e);
/*  228:     */   }
/*  229:     */   
/*  230:     */   public void reportError(String s)
/*  231:     */   {
/*  232: 305 */     this.parseErrorHandler.reportError(s);
/*  233:     */   }
/*  234:     */   
/*  235:     */   public void reportWarning(String s)
/*  236:     */   {
/*  237: 310 */     this.parseErrorHandler.reportWarning(s);
/*  238:     */   }
/*  239:     */   
/*  240:     */   public Set getQuerySpaces()
/*  241:     */   {
/*  242: 320 */     return this.querySpaces;
/*  243:     */   }
/*  244:     */   
/*  245:     */   protected AST createFromElement(String path, AST alias, AST propertyFetch)
/*  246:     */     throws SemanticException
/*  247:     */   {
/*  248: 325 */     FromElement fromElement = this.currentFromClause.addFromElement(path, alias);
/*  249: 326 */     fromElement.setAllPropertyFetch(propertyFetch != null);
/*  250: 327 */     return fromElement;
/*  251:     */   }
/*  252:     */   
/*  253:     */   protected AST createFromFilterElement(AST filterEntity, AST alias)
/*  254:     */     throws SemanticException
/*  255:     */   {
/*  256: 332 */     FromElement fromElement = this.currentFromClause.addFromElement(filterEntity.getText(), alias);
/*  257: 333 */     FromClause fromClause = fromElement.getFromClause();
/*  258: 334 */     QueryableCollection persister = this.sessionFactoryHelper.getCollectionPersister(this.collectionFilterRole);
/*  259:     */     
/*  260:     */ 
/*  261: 337 */     String[] keyColumnNames = persister.getKeyColumnNames();
/*  262: 338 */     String fkTableAlias = persister.isOneToMany() ? fromElement.getTableAlias() : fromClause.getAliasGenerator().createName(this.collectionFilterRole);
/*  263:     */     
/*  264:     */ 
/*  265: 341 */     JoinSequence join = this.sessionFactoryHelper.createJoinSequence();
/*  266: 342 */     join.setRoot(persister, fkTableAlias);
/*  267: 343 */     if (!persister.isOneToMany()) {
/*  268: 344 */       join.addJoin((AssociationType)persister.getElementType(), fromElement.getTableAlias(), JoinType.INNER_JOIN, persister.getElementColumnNames(fkTableAlias));
/*  269:     */     }
/*  270: 349 */     join.addCondition(fkTableAlias, keyColumnNames, " = ?");
/*  271: 350 */     fromElement.setJoinSequence(join);
/*  272: 351 */     fromElement.setFilter(true);
/*  273: 352 */     LOG.debug("createFromFilterElement() : processed filter FROM element.");
/*  274: 353 */     return fromElement;
/*  275:     */   }
/*  276:     */   
/*  277:     */   protected void createFromJoinElement(AST path, AST alias, int joinType, AST fetchNode, AST propertyFetch, AST with)
/*  278:     */     throws SemanticException
/*  279:     */   {
/*  280: 364 */     boolean fetch = fetchNode != null;
/*  281: 365 */     if ((fetch) && (isSubQuery())) {
/*  282: 366 */       throw new QueryException("fetch not allowed in subquery from-elements");
/*  283:     */     }
/*  284: 369 */     if (path.getType() != 15) {
/*  285: 370 */       throw new SemanticException("Path expected for join!");
/*  286:     */     }
/*  287: 372 */     DotNode dot = (DotNode)path;
/*  288: 373 */     JoinType hibernateJoinType = JoinProcessor.toHibernateJoinType(joinType);
/*  289: 374 */     dot.setJoinType(hibernateJoinType);
/*  290: 375 */     dot.setFetch(fetch);
/*  291:     */     
/*  292:     */ 
/*  293: 378 */     dot.resolve(true, false, alias == null ? null : alias.getText());
/*  294:     */     FromElement fromElement;
/*  295:     */     FromElement fromElement;
/*  296: 381 */     if ((dot.getDataType() != null) && (dot.getDataType().isComponentType()))
/*  297:     */     {
/*  298: 382 */       FromElementFactory factory = new FromElementFactory(getCurrentFromClause(), dot.getLhs().getFromElement(), dot.getPropertyPath(), alias == null ? null : alias.getText(), null, false);
/*  299:     */       
/*  300:     */ 
/*  301:     */ 
/*  302:     */ 
/*  303:     */ 
/*  304:     */ 
/*  305:     */ 
/*  306: 390 */       fromElement = factory.createComponentJoin((ComponentType)dot.getDataType());
/*  307:     */     }
/*  308:     */     else
/*  309:     */     {
/*  310: 393 */       fromElement = dot.getImpliedJoin();
/*  311: 394 */       fromElement.setAllPropertyFetch(propertyFetch != null);
/*  312: 396 */       if (with != null)
/*  313:     */       {
/*  314: 397 */         if (fetch) {
/*  315: 398 */           throw new SemanticException("with-clause not allowed on fetched associations; use filters");
/*  316:     */         }
/*  317: 400 */         handleWithFragment(fromElement, with);
/*  318:     */       }
/*  319:     */     }
/*  320: 404 */     if (LOG.isDebugEnabled()) {
/*  321: 404 */       LOG.debugf("createFromJoinElement() : %s", getASTPrinter().showAsString(fromElement, "-- join tree --"));
/*  322:     */     }
/*  323:     */   }
/*  324:     */   
/*  325:     */   private void handleWithFragment(FromElement fromElement, AST hqlWithNode)
/*  326:     */     throws SemanticException
/*  327:     */   {
/*  328:     */     try
/*  329:     */     {
/*  330: 410 */       withClause(hqlWithNode);
/*  331: 411 */       AST hqlSqlWithNode = this.returnAST;
/*  332: 412 */       if (LOG.isDebugEnabled()) {
/*  333: 412 */         LOG.debugf("handleWithFragment() : %s", getASTPrinter().showAsString(hqlSqlWithNode, "-- with clause --"));
/*  334:     */       }
/*  335: 414 */       WithClauseVisitor visitor = new WithClauseVisitor(fromElement);
/*  336: 415 */       NodeTraverser traverser = new NodeTraverser(visitor);
/*  337: 416 */       traverser.traverseDepthFirst(hqlSqlWithNode);
/*  338:     */       
/*  339: 418 */       String withClauseJoinAlias = visitor.getJoinAlias();
/*  340: 419 */       if (withClauseJoinAlias == null)
/*  341:     */       {
/*  342: 420 */         withClauseJoinAlias = fromElement.getCollectionTableAlias();
/*  343:     */       }
/*  344:     */       else
/*  345:     */       {
/*  346: 423 */         FromElement referencedFromElement = visitor.getReferencedFromElement();
/*  347: 424 */         if (referencedFromElement != fromElement) {
/*  348: 425 */           throw new InvalidWithClauseException("with-clause expressions did not reference from-clause element to which the with-clause was associated");
/*  349:     */         }
/*  350:     */       }
/*  351: 429 */       SqlGenerator sql = new SqlGenerator(getSessionFactoryHelper().getFactory());
/*  352: 430 */       sql.whereExpr(hqlSqlWithNode.getFirstChild());
/*  353:     */       
/*  354: 432 */       fromElement.setWithClauseFragment(withClauseJoinAlias, "(" + sql.getSQL() + ")");
/*  355:     */     }
/*  356:     */     catch (SemanticException e)
/*  357:     */     {
/*  358: 435 */       throw e;
/*  359:     */     }
/*  360:     */     catch (InvalidWithClauseException e)
/*  361:     */     {
/*  362: 438 */       throw e;
/*  363:     */     }
/*  364:     */     catch (Exception e)
/*  365:     */     {
/*  366: 441 */       throw new SemanticException(e.getMessage());
/*  367:     */     }
/*  368:     */   }
/*  369:     */   
/*  370:     */   private static class WithClauseVisitor
/*  371:     */     implements NodeTraverser.VisitationStrategy
/*  372:     */   {
/*  373:     */     private final FromElement joinFragment;
/*  374:     */     private FromElement referencedFromElement;
/*  375:     */     private String joinAlias;
/*  376:     */     
/*  377:     */     public WithClauseVisitor(FromElement fromElement)
/*  378:     */     {
/*  379: 451 */       this.joinFragment = fromElement;
/*  380:     */     }
/*  381:     */     
/*  382:     */     public void visit(AST node)
/*  383:     */     {
/*  384: 465 */       if ((node instanceof DotNode))
/*  385:     */       {
/*  386: 466 */         DotNode dotNode = (DotNode)node;
/*  387: 467 */         FromElement fromElement = dotNode.getFromElement();
/*  388: 468 */         if (this.referencedFromElement != null)
/*  389:     */         {
/*  390: 469 */           if (fromElement != this.referencedFromElement) {
/*  391: 470 */             throw new HibernateException("with-clause referenced two different from-clause elements");
/*  392:     */           }
/*  393:     */         }
/*  394:     */         else
/*  395:     */         {
/*  396: 474 */           this.referencedFromElement = fromElement;
/*  397: 475 */           this.joinAlias = extractAppliedAlias(dotNode);
/*  398: 480 */           if (!this.joinAlias.equals(this.referencedFromElement.getTableAlias())) {
/*  399: 481 */             throw new InvalidWithClauseException("with clause can only reference columns in the driving table");
/*  400:     */           }
/*  401:     */         }
/*  402:     */       }
/*  403: 485 */       else if ((node instanceof ParameterNode))
/*  404:     */       {
/*  405: 486 */         applyParameterSpecification(((ParameterNode)node).getHqlParameterSpecification());
/*  406:     */       }
/*  407: 488 */       else if ((node instanceof ParameterContainer))
/*  408:     */       {
/*  409: 489 */         applyParameterSpecifications((ParameterContainer)node);
/*  410:     */       }
/*  411:     */     }
/*  412:     */     
/*  413:     */     private void applyParameterSpecifications(ParameterContainer parameterContainer)
/*  414:     */     {
/*  415: 494 */       if (parameterContainer.hasEmbeddedParameters())
/*  416:     */       {
/*  417: 495 */         ParameterSpecification[] specs = parameterContainer.getEmbeddedParameters();
/*  418: 496 */         for (int i = 0; i < specs.length; i++) {
/*  419: 497 */           applyParameterSpecification(specs[i]);
/*  420:     */         }
/*  421:     */       }
/*  422:     */     }
/*  423:     */     
/*  424:     */     private void applyParameterSpecification(ParameterSpecification paramSpec)
/*  425:     */     {
/*  426: 503 */       this.joinFragment.addEmbeddedParameter(paramSpec);
/*  427:     */     }
/*  428:     */     
/*  429:     */     private String extractAppliedAlias(DotNode dotNode)
/*  430:     */     {
/*  431: 507 */       return dotNode.getText().substring(0, dotNode.getText().indexOf('.'));
/*  432:     */     }
/*  433:     */     
/*  434:     */     public FromElement getReferencedFromElement()
/*  435:     */     {
/*  436: 511 */       return this.referencedFromElement;
/*  437:     */     }
/*  438:     */     
/*  439:     */     public String getJoinAlias()
/*  440:     */     {
/*  441: 515 */       return this.joinAlias;
/*  442:     */     }
/*  443:     */   }
/*  444:     */   
/*  445:     */   protected void pushFromClause(AST fromNode, AST inputFromNode)
/*  446:     */   {
/*  447: 527 */     FromClause newFromClause = (FromClause)fromNode;
/*  448: 528 */     newFromClause.setParentFromClause(this.currentFromClause);
/*  449: 529 */     this.currentFromClause = newFromClause;
/*  450:     */   }
/*  451:     */   
/*  452:     */   private void popFromClause()
/*  453:     */   {
/*  454: 536 */     this.currentFromClause = this.currentFromClause.getParentFromClause();
/*  455:     */   }
/*  456:     */   
/*  457:     */   protected void lookupAlias(AST aliasRef)
/*  458:     */     throws SemanticException
/*  459:     */   {
/*  460: 542 */     FromElement alias = this.currentFromClause.getFromElement(aliasRef.getText());
/*  461: 543 */     FromReferenceNode aliasRefNode = (FromReferenceNode)aliasRef;
/*  462: 544 */     aliasRefNode.setFromElement(alias);
/*  463:     */   }
/*  464:     */   
/*  465:     */   protected void setImpliedJoinType(int joinType)
/*  466:     */   {
/*  467: 549 */     this.impliedJoinType = JoinProcessor.toHibernateJoinType(joinType);
/*  468:     */   }
/*  469:     */   
/*  470:     */   public JoinType getImpliedJoinType()
/*  471:     */   {
/*  472: 553 */     return this.impliedJoinType;
/*  473:     */   }
/*  474:     */   
/*  475:     */   protected AST lookupProperty(AST dot, boolean root, boolean inSelect)
/*  476:     */     throws SemanticException
/*  477:     */   {
/*  478: 558 */     DotNode dotNode = (DotNode)dot;
/*  479: 559 */     FromReferenceNode lhs = dotNode.getLhs();
/*  480: 560 */     AST rhs = lhs.getNextSibling();
/*  481: 561 */     switch (rhs.getType())
/*  482:     */     {
/*  483:     */     case 17: 
/*  484:     */     case 27: 
/*  485: 564 */       if (LOG.isDebugEnabled()) {
/*  486: 564 */         LOG.debugf("lookupProperty() %s => %s(%s)", dotNode.getPath(), rhs.getText(), lhs.getPath());
/*  487:     */       }
/*  488: 568 */       CollectionFunction f = (CollectionFunction)rhs;
/*  489:     */       
/*  490: 570 */       f.setFirstChild(lhs);
/*  491: 571 */       lhs.setNextSibling(null);
/*  492: 572 */       dotNode.setFirstChild(f);
/*  493: 573 */       resolve(lhs);
/*  494: 574 */       f.resolve(inSelect);
/*  495: 575 */       return f;
/*  496:     */     }
/*  497: 578 */     dotNode.resolveFirstChild();
/*  498: 579 */     return dotNode;
/*  499:     */   }
/*  500:     */   
/*  501:     */   protected boolean isNonQualifiedPropertyRef(AST ident)
/*  502:     */   {
/*  503: 585 */     String identText = ident.getText();
/*  504: 586 */     if (this.currentFromClause.isFromElementAlias(identText)) {
/*  505: 587 */       return false;
/*  506:     */     }
/*  507: 590 */     List fromElements = this.currentFromClause.getExplicitFromElements();
/*  508: 591 */     if (fromElements.size() == 1)
/*  509:     */     {
/*  510: 592 */       FromElement fromElement = (FromElement)fromElements.get(0);
/*  511:     */       try
/*  512:     */       {
/*  513: 594 */         LOG.tracev("Attempting to resolve property [{0}] as a non-qualified ref", identText);
/*  514: 595 */         return fromElement.getPropertyMapping(identText).toType(identText) != null;
/*  515:     */       }
/*  516:     */       catch (QueryException e) {}
/*  517:     */     }
/*  518: 602 */     return false;
/*  519:     */   }
/*  520:     */   
/*  521:     */   protected AST lookupNonQualifiedProperty(AST property)
/*  522:     */     throws SemanticException
/*  523:     */   {
/*  524: 607 */     FromElement fromElement = (FromElement)this.currentFromClause.getExplicitFromElements().get(0);
/*  525: 608 */     AST syntheticDotNode = generateSyntheticDotNodeForNonQualifiedPropertyRef(property, fromElement);
/*  526: 609 */     return lookupProperty(syntheticDotNode, false, getCurrentClauseType() == 45);
/*  527:     */   }
/*  528:     */   
/*  529:     */   private AST generateSyntheticDotNodeForNonQualifiedPropertyRef(AST property, FromElement fromElement)
/*  530:     */   {
/*  531: 613 */     AST dot = getASTFactory().create(15, "{non-qualified-property-ref}");
/*  532:     */     
/*  533: 615 */     ((DotNode)dot).setPropertyPath(((FromReferenceNode)property).getPath());
/*  534:     */     
/*  535: 617 */     IdentNode syntheticAlias = (IdentNode)getASTFactory().create(126, "{synthetic-alias}");
/*  536: 618 */     syntheticAlias.setFromElement(fromElement);
/*  537: 619 */     syntheticAlias.setResolved();
/*  538:     */     
/*  539: 621 */     dot.setFirstChild(syntheticAlias);
/*  540: 622 */     dot.addChild(property);
/*  541:     */     
/*  542: 624 */     return dot;
/*  543:     */   }
/*  544:     */   
/*  545:     */   protected void processQuery(AST select, AST query)
/*  546:     */     throws SemanticException
/*  547:     */   {
/*  548: 629 */     if (LOG.isDebugEnabled()) {
/*  549: 630 */       LOG.debugf("processQuery() : %s", query.toStringTree());
/*  550:     */     }
/*  551:     */     try
/*  552:     */     {
/*  553: 634 */       QueryNode qn = (QueryNode)query;
/*  554:     */       
/*  555:     */ 
/*  556: 637 */       boolean explicitSelect = (select != null) && (select.getNumberOfChildren() > 0);
/*  557: 639 */       if (!explicitSelect) {
/*  558: 645 */         createSelectClauseFromFromClause(qn);
/*  559:     */       } else {
/*  560: 650 */         useSelectClause(select);
/*  561:     */       }
/*  562: 655 */       JoinProcessor joinProcessor = new JoinProcessor(this);
/*  563: 656 */       joinProcessor.processJoins(qn);
/*  564:     */       
/*  565:     */ 
/*  566: 659 */       Iterator itr = qn.getFromClause().getProjectionList().iterator();
/*  567: 660 */       while (itr.hasNext())
/*  568:     */       {
/*  569: 661 */         FromElement fromElement = (FromElement)itr.next();
/*  570: 663 */         if ((fromElement.isFetch()) && (fromElement.getQueryableCollection() != null))
/*  571:     */         {
/*  572: 667 */           if (fromElement.getQueryableCollection().hasOrdering())
/*  573:     */           {
/*  574: 668 */             String orderByFragment = fromElement.getQueryableCollection().getSQLOrderByString(fromElement.getCollectionTableAlias());
/*  575:     */             
/*  576:     */ 
/*  577: 671 */             qn.getOrderByClause().addOrderFragment(orderByFragment);
/*  578:     */           }
/*  579: 673 */           if (fromElement.getQueryableCollection().hasManyToManyOrdering())
/*  580:     */           {
/*  581: 674 */             String orderByFragment = fromElement.getQueryableCollection().getManyToManyOrderByString(fromElement.getTableAlias());
/*  582:     */             
/*  583: 676 */             qn.getOrderByClause().addOrderFragment(orderByFragment);
/*  584:     */           }
/*  585:     */         }
/*  586:     */       }
/*  587:     */     }
/*  588:     */     finally
/*  589:     */     {
/*  590: 682 */       popFromClause();
/*  591:     */     }
/*  592:     */   }
/*  593:     */   
/*  594:     */   protected void postProcessDML(RestrictableStatement statement)
/*  595:     */     throws SemanticException
/*  596:     */   {
/*  597: 687 */     statement.getFromClause().resolve();
/*  598:     */     
/*  599: 689 */     FromElement fromElement = (FromElement)statement.getFromClause().getFromElements().get(0);
/*  600: 690 */     Queryable persister = fromElement.getQueryable();
/*  601:     */     
/*  602: 692 */     fromElement.setText(persister.getTableName());
/*  603: 704 */     if ((persister.getDiscriminatorType() != null) || (!this.queryTranslatorImpl.getEnabledFilters().isEmpty())) {
/*  604: 705 */       new SyntheticAndFactory(this).addDiscriminatorWhereFragment(statement, persister, this.queryTranslatorImpl.getEnabledFilters(), fromElement.getTableAlias());
/*  605:     */     }
/*  606:     */   }
/*  607:     */   
/*  608:     */   protected void postProcessUpdate(AST update)
/*  609:     */     throws SemanticException
/*  610:     */   {
/*  611: 717 */     UpdateStatement updateStatement = (UpdateStatement)update;
/*  612:     */     
/*  613: 719 */     postProcessDML(updateStatement);
/*  614:     */   }
/*  615:     */   
/*  616:     */   protected void postProcessDelete(AST delete)
/*  617:     */     throws SemanticException
/*  618:     */   {
/*  619: 724 */     postProcessDML((DeleteStatement)delete);
/*  620:     */   }
/*  621:     */   
/*  622:     */   public static boolean supportsIdGenWithBulkInsertion(IdentifierGenerator generator)
/*  623:     */   {
/*  624: 728 */     return (SequenceGenerator.class.isAssignableFrom(generator.getClass())) || (PostInsertIdentifierGenerator.class.isAssignableFrom(generator.getClass()));
/*  625:     */   }
/*  626:     */   
/*  627:     */   protected void postProcessInsert(AST insert)
/*  628:     */     throws SemanticException, QueryException
/*  629:     */   {
/*  630: 734 */     InsertStatement insertStatement = (InsertStatement)insert;
/*  631: 735 */     insertStatement.validate();
/*  632:     */     
/*  633: 737 */     SelectClause selectClause = insertStatement.getSelectClause();
/*  634: 738 */     Queryable persister = insertStatement.getIntoClause().getQueryable();
/*  635: 740 */     if (!insertStatement.getIntoClause().isExplicitIdInsertion())
/*  636:     */     {
/*  637: 745 */       IdentifierGenerator generator = persister.getIdentifierGenerator();
/*  638: 746 */       if (!supportsIdGenWithBulkInsertion(generator)) {
/*  639: 747 */         throw new QueryException("can only generate ids as part of bulk insert with either sequence or post-insert style generators");
/*  640:     */       }
/*  641: 750 */       AST idSelectExprNode = null;
/*  642: 752 */       if (SequenceGenerator.class.isAssignableFrom(generator.getClass()))
/*  643:     */       {
/*  644: 753 */         String seqName = (String)((SequenceGenerator)generator).generatorKey();
/*  645: 754 */         String nextval = this.sessionFactoryHelper.getFactory().getDialect().getSelectSequenceNextValString(seqName);
/*  646: 755 */         idSelectExprNode = getASTFactory().create(142, nextval);
/*  647:     */       }
/*  648: 766 */       if (idSelectExprNode != null)
/*  649:     */       {
/*  650: 767 */         AST currentFirstSelectExprNode = selectClause.getFirstChild();
/*  651: 768 */         selectClause.setFirstChild(idSelectExprNode);
/*  652: 769 */         idSelectExprNode.setNextSibling(currentFirstSelectExprNode);
/*  653:     */         
/*  654: 771 */         insertStatement.getIntoClause().prependIdColumnSpec();
/*  655:     */       }
/*  656:     */     }
/*  657: 775 */     boolean includeVersionProperty = (persister.isVersioned()) && (!insertStatement.getIntoClause().isExplicitVersionInsertion()) && (persister.isVersionPropertyInsertable());
/*  658: 778 */     if (includeVersionProperty)
/*  659:     */     {
/*  660: 780 */       VersionType versionType = persister.getVersionType();
/*  661: 781 */       AST versionValueNode = null;
/*  662: 783 */       if (this.sessionFactoryHelper.getFactory().getDialect().supportsParametersInInsertSelect())
/*  663:     */       {
/*  664: 784 */         int[] sqlTypes = versionType.sqlTypes(this.sessionFactoryHelper.getFactory());
/*  665: 785 */         if ((sqlTypes == null) || (sqlTypes.length == 0)) {
/*  666: 786 */           throw new IllegalStateException(versionType.getClass() + ".sqlTypes() returns null or empty array");
/*  667:     */         }
/*  668: 788 */         if (sqlTypes.length > 1) {
/*  669: 789 */           throw new IllegalStateException(versionType.getClass() + ".sqlTypes() returns > 1 element; only single-valued versions are allowed.");
/*  670:     */         }
/*  671: 794 */         versionValueNode = getASTFactory().create(123, "?");
/*  672: 795 */         ParameterSpecification paramSpec = new VersionTypeSeedParameterSpecification(versionType);
/*  673: 796 */         ((ParameterNode)versionValueNode).setHqlParameterSpecification(paramSpec);
/*  674: 797 */         this.parameters.add(0, paramSpec);
/*  675: 799 */         if (this.sessionFactoryHelper.getFactory().getDialect().requiresCastingOfParametersInSelectClause())
/*  676:     */         {
/*  677: 801 */           MethodNode versionMethodNode = (MethodNode)getASTFactory().create(81, "(");
/*  678: 802 */           AST methodIdentNode = getASTFactory().create(126, "cast");
/*  679: 803 */           versionMethodNode.addChild(methodIdentNode);
/*  680: 804 */           versionMethodNode.initializeMethodNode(methodIdentNode, true);
/*  681: 805 */           AST castExprListNode = getASTFactory().create(75, "exprList");
/*  682: 806 */           methodIdentNode.setNextSibling(castExprListNode);
/*  683: 807 */           castExprListNode.addChild(versionValueNode);
/*  684: 808 */           versionValueNode.setNextSibling(getASTFactory().create(126, this.sessionFactoryHelper.getFactory().getDialect().getTypeName(sqlTypes[0])));
/*  685:     */           
/*  686:     */ 
/*  687:     */ 
/*  688:     */ 
/*  689: 813 */           processFunction(versionMethodNode, true);
/*  690: 814 */           versionValueNode = versionMethodNode;
/*  691:     */         }
/*  692:     */       }
/*  693: 818 */       else if (isIntegral(versionType))
/*  694:     */       {
/*  695:     */         try
/*  696:     */         {
/*  697: 820 */           Object seedValue = versionType.seed(null);
/*  698: 821 */           versionValueNode = getASTFactory().create(142, seedValue.toString());
/*  699:     */         }
/*  700:     */         catch (Throwable t)
/*  701:     */         {
/*  702: 824 */           throw new QueryException("could not determine seed value for version on bulk insert [" + versionType + "]");
/*  703:     */         }
/*  704:     */       }
/*  705: 827 */       else if (isDatabaseGeneratedTimestamp(versionType))
/*  706:     */       {
/*  707: 828 */         String functionName = this.sessionFactoryHelper.getFactory().getDialect().getCurrentTimestampSQLFunctionName();
/*  708: 829 */         versionValueNode = getASTFactory().create(142, functionName);
/*  709:     */       }
/*  710:     */       else
/*  711:     */       {
/*  712: 832 */         throw new QueryException("cannot handle version type [" + versionType + "] on bulk inserts with dialects not supporting parameters in insert-select statements");
/*  713:     */       }
/*  714: 836 */       AST currentFirstSelectExprNode = selectClause.getFirstChild();
/*  715: 837 */       selectClause.setFirstChild(versionValueNode);
/*  716: 838 */       versionValueNode.setNextSibling(currentFirstSelectExprNode);
/*  717:     */       
/*  718: 840 */       insertStatement.getIntoClause().prependVersionColumnSpec();
/*  719:     */     }
/*  720: 843 */     if (insertStatement.getIntoClause().isDiscriminated())
/*  721:     */     {
/*  722: 844 */       String sqlValue = insertStatement.getIntoClause().getQueryable().getDiscriminatorSQLValue();
/*  723: 845 */       AST discrimValue = getASTFactory().create(142, sqlValue);
/*  724: 846 */       insertStatement.getSelectClause().addChild(discrimValue);
/*  725:     */     }
/*  726:     */   }
/*  727:     */   
/*  728:     */   private boolean isDatabaseGeneratedTimestamp(Type type)
/*  729:     */   {
/*  730: 853 */     return DbTimestampType.class.isAssignableFrom(type.getClass());
/*  731:     */   }
/*  732:     */   
/*  733:     */   private boolean isIntegral(Type type)
/*  734:     */   {
/*  735: 857 */     return (Long.class.isAssignableFrom(type.getReturnedClass())) || (Integer.class.isAssignableFrom(type.getReturnedClass())) || (Long.TYPE.isAssignableFrom(type.getReturnedClass())) || (Integer.TYPE.isAssignableFrom(type.getReturnedClass()));
/*  736:     */   }
/*  737:     */   
/*  738:     */   private void useSelectClause(AST select)
/*  739:     */     throws SemanticException
/*  740:     */   {
/*  741: 864 */     this.selectClause = ((SelectClause)select);
/*  742: 865 */     this.selectClause.initializeExplicitSelectClause(this.currentFromClause);
/*  743:     */   }
/*  744:     */   
/*  745:     */   private void createSelectClauseFromFromClause(QueryNode qn)
/*  746:     */     throws SemanticException
/*  747:     */   {
/*  748: 869 */     AST select = this.astFactory.create(137, "{derived select clause}");
/*  749: 870 */     AST sibling = qn.getFromClause();
/*  750: 871 */     qn.setFirstChild(select);
/*  751: 872 */     select.setNextSibling(sibling);
/*  752: 873 */     this.selectClause = ((SelectClause)select);
/*  753: 874 */     this.selectClause.initializeDerivedSelectClause(this.currentFromClause);
/*  754: 875 */     LOG.debug("Derived SELECT clause created.");
/*  755:     */   }
/*  756:     */   
/*  757:     */   protected void resolve(AST node)
/*  758:     */     throws SemanticException
/*  759:     */   {
/*  760: 880 */     if (node != null)
/*  761:     */     {
/*  762: 882 */       ResolvableNode r = (ResolvableNode)node;
/*  763: 883 */       if (isInFunctionCall()) {
/*  764: 884 */         r.resolveInFunctionCall(false, true);
/*  765:     */       } else {
/*  766: 887 */         r.resolve(false, true);
/*  767:     */       }
/*  768:     */     }
/*  769:     */   }
/*  770:     */   
/*  771:     */   protected void resolveSelectExpression(AST node)
/*  772:     */     throws SemanticException
/*  773:     */   {
/*  774: 895 */     int type = node.getType();
/*  775: 896 */     switch (type)
/*  776:     */     {
/*  777:     */     case 15: 
/*  778: 898 */       DotNode dot = (DotNode)node;
/*  779: 899 */       dot.resolveSelectExpression();
/*  780: 900 */       break;
/*  781:     */     case 140: 
/*  782: 904 */       FromReferenceNode aliasRefNode = (FromReferenceNode)node;
/*  783:     */       
/*  784: 906 */       aliasRefNode.resolve(false, false);
/*  785: 907 */       FromElement fromElement = aliasRefNode.getFromElement();
/*  786: 908 */       if (fromElement != null) {
/*  787: 909 */         fromElement.setIncludeSubclasses(true);
/*  788:     */       }
/*  789:     */       break;
/*  790:     */     }
/*  791:     */   }
/*  792:     */   
/*  793:     */   protected void beforeSelectClause()
/*  794:     */     throws SemanticException
/*  795:     */   {
/*  796: 922 */     FromClause from = getCurrentFromClause();
/*  797: 923 */     List fromElements = from.getFromElements();
/*  798: 924 */     for (Iterator iterator = fromElements.iterator(); iterator.hasNext();)
/*  799:     */     {
/*  800: 925 */       FromElement fromElement = (FromElement)iterator.next();
/*  801: 926 */       fromElement.setIncludeSubclasses(false);
/*  802:     */     }
/*  803:     */   }
/*  804:     */   
/*  805:     */   protected AST generatePositionalParameter(AST inputNode)
/*  806:     */     throws SemanticException
/*  807:     */   {
/*  808: 932 */     if (this.namedParameters.size() > 0) {
/*  809: 933 */       throw new SemanticException("cannot define positional parameter after any named parameters have been defined");
/*  810:     */     }
/*  811: 935 */     ParameterNode parameter = (ParameterNode)this.astFactory.create(123, "?");
/*  812: 936 */     PositionalParameterSpecification paramSpec = new PositionalParameterSpecification(inputNode.getLine(), inputNode.getColumn(), this.positionalParameterCount++);
/*  813:     */     
/*  814:     */ 
/*  815:     */ 
/*  816:     */ 
/*  817: 941 */     parameter.setHqlParameterSpecification(paramSpec);
/*  818: 942 */     this.parameters.add(paramSpec);
/*  819: 943 */     return parameter;
/*  820:     */   }
/*  821:     */   
/*  822:     */   protected AST generateNamedParameter(AST delimiterNode, AST nameNode)
/*  823:     */     throws SemanticException
/*  824:     */   {
/*  825: 948 */     String name = nameNode.getText();
/*  826: 949 */     trackNamedParameterPositions(name);
/*  827:     */     
/*  828:     */ 
/*  829:     */ 
/*  830: 953 */     ParameterNode parameter = (ParameterNode)this.astFactory.create(148, name);
/*  831: 954 */     parameter.setText("?");
/*  832:     */     
/*  833: 956 */     NamedParameterSpecification paramSpec = new NamedParameterSpecification(delimiterNode.getLine(), delimiterNode.getColumn(), name);
/*  834:     */     
/*  835:     */ 
/*  836:     */ 
/*  837:     */ 
/*  838: 961 */     parameter.setHqlParameterSpecification(paramSpec);
/*  839: 962 */     this.parameters.add(paramSpec);
/*  840: 963 */     return parameter;
/*  841:     */   }
/*  842:     */   
/*  843:     */   private void trackNamedParameterPositions(String name)
/*  844:     */   {
/*  845: 967 */     Integer loc = Integer.valueOf(this.parameterCount++);
/*  846: 968 */     Object o = this.namedParameters.get(name);
/*  847: 969 */     if (o == null)
/*  848:     */     {
/*  849: 970 */       this.namedParameters.put(name, loc);
/*  850:     */     }
/*  851: 972 */     else if ((o instanceof Integer))
/*  852:     */     {
/*  853: 973 */       ArrayList list = new ArrayList(4);
/*  854: 974 */       list.add(o);
/*  855: 975 */       list.add(loc);
/*  856: 976 */       this.namedParameters.put(name, list);
/*  857:     */     }
/*  858:     */     else
/*  859:     */     {
/*  860: 979 */       ((ArrayList)o).add(loc);
/*  861:     */     }
/*  862:     */   }
/*  863:     */   
/*  864:     */   protected void processConstant(AST constant)
/*  865:     */     throws SemanticException
/*  866:     */   {
/*  867: 985 */     this.literalProcessor.processConstant(constant, true);
/*  868:     */   }
/*  869:     */   
/*  870:     */   protected void processBoolean(AST constant)
/*  871:     */     throws SemanticException
/*  872:     */   {
/*  873: 990 */     this.literalProcessor.processBoolean(constant);
/*  874:     */   }
/*  875:     */   
/*  876:     */   protected void processNumericLiteral(AST literal)
/*  877:     */   {
/*  878: 995 */     this.literalProcessor.processNumeric(literal);
/*  879:     */   }
/*  880:     */   
/*  881:     */   protected void processIndex(AST indexOp)
/*  882:     */     throws SemanticException
/*  883:     */   {
/*  884:1000 */     IndexNode indexNode = (IndexNode)indexOp;
/*  885:1001 */     indexNode.resolve(true, true);
/*  886:     */   }
/*  887:     */   
/*  888:     */   protected void processFunction(AST functionCall, boolean inSelect)
/*  889:     */     throws SemanticException
/*  890:     */   {
/*  891:1006 */     MethodNode methodNode = (MethodNode)functionCall;
/*  892:1007 */     methodNode.resolve(inSelect);
/*  893:     */   }
/*  894:     */   
/*  895:     */   protected void processAggregation(AST node, boolean inSelect)
/*  896:     */     throws SemanticException
/*  897:     */   {
/*  898:1012 */     AggregateNode aggregateNode = (AggregateNode)node;
/*  899:1013 */     aggregateNode.resolve();
/*  900:     */   }
/*  901:     */   
/*  902:     */   protected void processConstructor(AST constructor)
/*  903:     */     throws SemanticException
/*  904:     */   {
/*  905:1018 */     ConstructorNode constructorNode = (ConstructorNode)constructor;
/*  906:1019 */     constructorNode.prepare();
/*  907:     */   }
/*  908:     */   
/*  909:     */   protected void setAlias(AST selectExpr, AST ident)
/*  910:     */   {
/*  911:1024 */     ((SelectExpression)selectExpr).setAlias(ident.getText());
/*  912:1027 */     if (!isSubQuery()) {
/*  913:1028 */       this.selectExpressionsByResultVariable.put(ident.getText(), (SelectExpression)selectExpr);
/*  914:     */     }
/*  915:     */   }
/*  916:     */   
/*  917:     */   protected boolean isOrderExpressionResultVariableRef(AST orderExpressionNode)
/*  918:     */     throws SemanticException
/*  919:     */   {
/*  920:1036 */     if ((!isSubQuery()) && (orderExpressionNode.getType() == 126) && (this.selectExpressionsByResultVariable.containsKey(orderExpressionNode.getText()))) {
/*  921:1039 */       return true;
/*  922:     */     }
/*  923:1041 */     return false;
/*  924:     */   }
/*  925:     */   
/*  926:     */   protected void handleResultVariableRef(AST resultVariableRef)
/*  927:     */     throws SemanticException
/*  928:     */   {
/*  929:1046 */     if (isSubQuery()) {
/*  930:1047 */       throw new SemanticException("References to result variables in subqueries are not supported.");
/*  931:     */     }
/*  932:1051 */     ((ResultVariableRefNode)resultVariableRef).setSelectExpression((SelectExpression)this.selectExpressionsByResultVariable.get(resultVariableRef.getText()));
/*  933:     */   }
/*  934:     */   
/*  935:     */   public int[] getNamedParameterLocations(String name)
/*  936:     */     throws QueryException
/*  937:     */   {
/*  938:1060 */     Object o = this.namedParameters.get(name);
/*  939:1061 */     if (o == null)
/*  940:     */     {
/*  941:1062 */       QueryException qe = new QueryException("Named parameter does not appear in Query: " + name);
/*  942:1063 */       qe.setQueryString(this.queryTranslatorImpl.getQueryString());
/*  943:1064 */       throw qe;
/*  944:     */     }
/*  945:1066 */     if ((o instanceof Integer)) {
/*  946:1067 */       return new int[] { ((Integer)o).intValue() };
/*  947:     */     }
/*  948:1070 */     return ArrayHelper.toIntArray((ArrayList)o);
/*  949:     */   }
/*  950:     */   
/*  951:     */   public void addQuerySpaces(Serializable[] spaces)
/*  952:     */   {
/*  953:1075 */     this.querySpaces.addAll(Arrays.asList(spaces));
/*  954:     */   }
/*  955:     */   
/*  956:     */   public Type[] getReturnTypes()
/*  957:     */   {
/*  958:1079 */     return this.selectClause.getQueryReturnTypes();
/*  959:     */   }
/*  960:     */   
/*  961:     */   public String[] getReturnAliases()
/*  962:     */   {
/*  963:1083 */     return this.selectClause.getQueryReturnAliases();
/*  964:     */   }
/*  965:     */   
/*  966:     */   public SelectClause getSelectClause()
/*  967:     */   {
/*  968:1087 */     return this.selectClause;
/*  969:     */   }
/*  970:     */   
/*  971:     */   public FromClause getFinalFromClause()
/*  972:     */   {
/*  973:1091 */     FromClause top = this.currentFromClause;
/*  974:1092 */     while (top.getParentFromClause() != null) {
/*  975:1093 */       top = top.getParentFromClause();
/*  976:     */     }
/*  977:1095 */     return top;
/*  978:     */   }
/*  979:     */   
/*  980:     */   public boolean isShallowQuery()
/*  981:     */   {
/*  982:1100 */     return (getStatementType() == 29) || (this.queryTranslatorImpl.isShallowQuery());
/*  983:     */   }
/*  984:     */   
/*  985:     */   public Map getEnabledFilters()
/*  986:     */   {
/*  987:1104 */     return this.queryTranslatorImpl.getEnabledFilters();
/*  988:     */   }
/*  989:     */   
/*  990:     */   public LiteralProcessor getLiteralProcessor()
/*  991:     */   {
/*  992:1108 */     return this.literalProcessor;
/*  993:     */   }
/*  994:     */   
/*  995:     */   public ASTPrinter getASTPrinter()
/*  996:     */   {
/*  997:1112 */     return this.printer;
/*  998:     */   }
/*  999:     */   
/* 1000:     */   public ArrayList getParameters()
/* 1001:     */   {
/* 1002:1116 */     return this.parameters;
/* 1003:     */   }
/* 1004:     */   
/* 1005:     */   public int getNumberOfParametersInSetClause()
/* 1006:     */   {
/* 1007:1120 */     return this.numberOfParametersInSetClause;
/* 1008:     */   }
/* 1009:     */   
/* 1010:     */   protected void evaluateAssignment(AST eq)
/* 1011:     */     throws SemanticException
/* 1012:     */   {
/* 1013:1125 */     prepareLogicOperator(eq);
/* 1014:1126 */     Queryable persister = getCurrentFromClause().getFromElement().getQueryable();
/* 1015:1127 */     evaluateAssignment(eq, persister, -1);
/* 1016:     */   }
/* 1017:     */   
/* 1018:     */   private void evaluateAssignment(AST eq, Queryable persister, int targetIndex)
/* 1019:     */   {
/* 1020:1131 */     if (persister.isMultiTable())
/* 1021:     */     {
/* 1022:1133 */       AssignmentSpecification specification = new AssignmentSpecification(eq, persister);
/* 1023:1134 */       if (targetIndex >= 0) {
/* 1024:1135 */         this.assignmentSpecifications.add(targetIndex, specification);
/* 1025:     */       } else {
/* 1026:1138 */         this.assignmentSpecifications.add(specification);
/* 1027:     */       }
/* 1028:1140 */       this.numberOfParametersInSetClause += specification.getParameters().length;
/* 1029:     */     }
/* 1030:     */   }
/* 1031:     */   
/* 1032:     */   public ArrayList getAssignmentSpecifications()
/* 1033:     */   {
/* 1034:1145 */     return this.assignmentSpecifications;
/* 1035:     */   }
/* 1036:     */   
/* 1037:     */   protected AST createIntoClause(String path, AST propertySpec)
/* 1038:     */     throws SemanticException
/* 1039:     */   {
/* 1040:1150 */     Queryable persister = (Queryable)getSessionFactoryHelper().requireClassPersister(path);
/* 1041:     */     
/* 1042:1152 */     IntoClause intoClause = (IntoClause)getASTFactory().create(30, persister.getEntityName());
/* 1043:1153 */     intoClause.setFirstChild(propertySpec);
/* 1044:1154 */     intoClause.initialize(persister);
/* 1045:     */     
/* 1046:1156 */     addQuerySpaces(persister.getQuerySpaces());
/* 1047:     */     
/* 1048:1158 */     return intoClause;
/* 1049:     */   }
/* 1050:     */   
/* 1051:     */   protected void prepareVersioned(AST updateNode, AST versioned)
/* 1052:     */     throws SemanticException
/* 1053:     */   {
/* 1054:1163 */     UpdateStatement updateStatement = (UpdateStatement)updateNode;
/* 1055:1164 */     FromClause fromClause = updateStatement.getFromClause();
/* 1056:1165 */     if (versioned != null)
/* 1057:     */     {
/* 1058:1167 */       Queryable persister = fromClause.getFromElement().getQueryable();
/* 1059:1168 */       if (!persister.isVersioned()) {
/* 1060:1169 */         throw new SemanticException("increment option specified for update of non-versioned entity");
/* 1061:     */       }
/* 1062:1172 */       VersionType versionType = persister.getVersionType();
/* 1063:1173 */       if ((versionType instanceof UserVersionType)) {
/* 1064:1174 */         throw new SemanticException("user-defined version types not supported for increment option");
/* 1065:     */       }
/* 1066:1177 */       AST eq = getASTFactory().create(102, "=");
/* 1067:1178 */       AST versionPropertyNode = generateVersionPropertyNode(persister);
/* 1068:     */       
/* 1069:1180 */       eq.setFirstChild(versionPropertyNode);
/* 1070:     */       
/* 1071:1182 */       AST versionIncrementNode = null;
/* 1072:1183 */       if (isTimestampBasedVersion(versionType))
/* 1073:     */       {
/* 1074:1184 */         versionIncrementNode = getASTFactory().create(123, "?");
/* 1075:1185 */         ParameterSpecification paramSpec = new VersionTypeSeedParameterSpecification(versionType);
/* 1076:1186 */         ((ParameterNode)versionIncrementNode).setHqlParameterSpecification(paramSpec);
/* 1077:1187 */         this.parameters.add(0, paramSpec);
/* 1078:     */       }
/* 1079:     */       else
/* 1080:     */       {
/* 1081:1192 */         versionIncrementNode = getASTFactory().create(115, "+");
/* 1082:1193 */         versionIncrementNode.setFirstChild(generateVersionPropertyNode(persister));
/* 1083:1194 */         versionIncrementNode.addChild(getASTFactory().create(126, "1"));
/* 1084:     */       }
/* 1085:1197 */       eq.addChild(versionIncrementNode);
/* 1086:     */       
/* 1087:1199 */       evaluateAssignment(eq, persister, 0);
/* 1088:     */       
/* 1089:1201 */       AST setClause = updateStatement.getSetClause();
/* 1090:1202 */       AST currentFirstSetElement = setClause.getFirstChild();
/* 1091:1203 */       setClause.setFirstChild(eq);
/* 1092:1204 */       eq.setNextSibling(currentFirstSetElement);
/* 1093:     */     }
/* 1094:     */   }
/* 1095:     */   
/* 1096:     */   private boolean isTimestampBasedVersion(VersionType versionType)
/* 1097:     */   {
/* 1098:1209 */     Class javaType = versionType.getReturnedClass();
/* 1099:1210 */     return (Date.class.isAssignableFrom(javaType)) || (Calendar.class.isAssignableFrom(javaType));
/* 1100:     */   }
/* 1101:     */   
/* 1102:     */   private AST generateVersionPropertyNode(Queryable persister)
/* 1103:     */     throws SemanticException
/* 1104:     */   {
/* 1105:1215 */     String versionPropertyName = persister.getPropertyNames()[persister.getVersionProperty()];
/* 1106:1216 */     AST versionPropertyRef = getASTFactory().create(126, versionPropertyName);
/* 1107:1217 */     AST versionPropertyNode = lookupNonQualifiedProperty(versionPropertyRef);
/* 1108:1218 */     resolve(versionPropertyNode);
/* 1109:1219 */     return versionPropertyNode;
/* 1110:     */   }
/* 1111:     */   
/* 1112:     */   protected void prepareLogicOperator(AST operator)
/* 1113:     */     throws SemanticException
/* 1114:     */   {
/* 1115:1224 */     ((OperatorNode)operator).initialize();
/* 1116:     */   }
/* 1117:     */   
/* 1118:     */   protected void prepareArithmeticOperator(AST operator)
/* 1119:     */     throws SemanticException
/* 1120:     */   {
/* 1121:1229 */     ((OperatorNode)operator).initialize();
/* 1122:     */   }
/* 1123:     */   
/* 1124:     */   protected void validateMapPropertyExpression(AST node)
/* 1125:     */     throws SemanticException
/* 1126:     */   {
/* 1127:     */     try
/* 1128:     */     {
/* 1129:1235 */       FromReferenceNode fromReferenceNode = (FromReferenceNode)node;
/* 1130:1236 */       QueryableCollection collectionPersister = fromReferenceNode.getFromElement().getQueryableCollection();
/* 1131:1237 */       if (!Map.class.isAssignableFrom(collectionPersister.getCollectionType().getReturnedClass())) {
/* 1132:1238 */         throw new SemanticException("node did not reference a map");
/* 1133:     */       }
/* 1134:     */     }
/* 1135:     */     catch (SemanticException se)
/* 1136:     */     {
/* 1137:1242 */       throw se;
/* 1138:     */     }
/* 1139:     */     catch (Throwable t)
/* 1140:     */     {
/* 1141:1245 */       throw new SemanticException("node did not reference a map");
/* 1142:     */     }
/* 1143:     */   }
/* 1144:     */   
/* 1145:     */   public static void panic()
/* 1146:     */   {
/* 1147:1250 */     throw new QueryException("TreeWalker: panic");
/* 1148:     */   }
/* 1149:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.HqlSqlWalker
 * JD-Core Version:    0.7.0.1
 */