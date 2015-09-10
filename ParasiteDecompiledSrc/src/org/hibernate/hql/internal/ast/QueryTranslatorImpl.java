/*   1:    */ package org.hibernate.hql.internal.ast;
/*   2:    */ 
/*   3:    */ import antlr.ANTLRException;
/*   4:    */ import antlr.RecognitionException;
/*   5:    */ import antlr.TokenStreamException;
/*   6:    */ import antlr.collections.AST;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.HashMap;
/*   9:    */ import java.util.Iterator;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Map;
/*  12:    */ import java.util.Set;
/*  13:    */ import org.hibernate.HibernateException;
/*  14:    */ import org.hibernate.MappingException;
/*  15:    */ import org.hibernate.QueryException;
/*  16:    */ import org.hibernate.ScrollableResults;
/*  17:    */ import org.hibernate.engine.spi.QueryParameters;
/*  18:    */ import org.hibernate.engine.spi.RowSelection;
/*  19:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  20:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  21:    */ import org.hibernate.event.spi.EventSource;
/*  22:    */ import org.hibernate.hql.internal.QueryExecutionRequestException;
/*  23:    */ import org.hibernate.hql.internal.antlr.HqlTokenTypes;
/*  24:    */ import org.hibernate.hql.internal.antlr.SqlTokenTypes;
/*  25:    */ import org.hibernate.hql.internal.ast.exec.BasicExecutor;
/*  26:    */ import org.hibernate.hql.internal.ast.exec.MultiTableDeleteExecutor;
/*  27:    */ import org.hibernate.hql.internal.ast.exec.MultiTableUpdateExecutor;
/*  28:    */ import org.hibernate.hql.internal.ast.exec.StatementExecutor;
/*  29:    */ import org.hibernate.hql.internal.ast.tree.AggregatedSelectExpression;
/*  30:    */ import org.hibernate.hql.internal.ast.tree.FromClause;
/*  31:    */ import org.hibernate.hql.internal.ast.tree.FromElement;
/*  32:    */ import org.hibernate.hql.internal.ast.tree.InsertStatement;
/*  33:    */ import org.hibernate.hql.internal.ast.tree.IntoClause;
/*  34:    */ import org.hibernate.hql.internal.ast.tree.OrderByClause;
/*  35:    */ import org.hibernate.hql.internal.ast.tree.QueryNode;
/*  36:    */ import org.hibernate.hql.internal.ast.tree.SelectClause;
/*  37:    */ import org.hibernate.hql.internal.ast.tree.Statement;
/*  38:    */ import org.hibernate.hql.internal.ast.util.ASTPrinter;
/*  39:    */ import org.hibernate.hql.internal.ast.util.ASTUtil;
/*  40:    */ import org.hibernate.hql.internal.ast.util.NodeTraverser;
/*  41:    */ import org.hibernate.hql.internal.ast.util.NodeTraverser.VisitationStrategy;
/*  42:    */ import org.hibernate.hql.spi.FilterTranslator;
/*  43:    */ import org.hibernate.hql.spi.ParameterTranslations;
/*  44:    */ import org.hibernate.internal.CoreMessageLogger;
/*  45:    */ import org.hibernate.internal.util.ReflectHelper;
/*  46:    */ import org.hibernate.internal.util.StringHelper;
/*  47:    */ import org.hibernate.internal.util.collections.IdentitySet;
/*  48:    */ import org.hibernate.loader.hql.QueryLoader;
/*  49:    */ import org.hibernate.persister.entity.Queryable;
/*  50:    */ import org.hibernate.type.Type;
/*  51:    */ import org.jboss.logging.Logger;
/*  52:    */ 
/*  53:    */ public class QueryTranslatorImpl
/*  54:    */   implements FilterTranslator
/*  55:    */ {
/*  56: 81 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, QueryTranslatorImpl.class.getName());
/*  57:    */   private SessionFactoryImplementor factory;
/*  58:    */   private final String queryIdentifier;
/*  59:    */   private String hql;
/*  60:    */   private boolean shallowQuery;
/*  61:    */   private Map tokenReplacements;
/*  62:    */   private Map enabledFilters;
/*  63:    */   private boolean compiled;
/*  64:    */   private QueryLoader queryLoader;
/*  65:    */   private StatementExecutor statementExecutor;
/*  66:    */   private Statement sqlAst;
/*  67:    */   private String sql;
/*  68:    */   private ParameterTranslations paramTranslations;
/*  69:    */   private List collectedParameterSpecifications;
/*  70:    */   
/*  71:    */   public QueryTranslatorImpl(String queryIdentifier, String query, Map enabledFilters, SessionFactoryImplementor factory)
/*  72:    */   {
/*  73:116 */     this.queryIdentifier = queryIdentifier;
/*  74:117 */     this.hql = query;
/*  75:118 */     this.compiled = false;
/*  76:119 */     this.shallowQuery = false;
/*  77:120 */     this.enabledFilters = enabledFilters;
/*  78:121 */     this.factory = factory;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void compile(Map replacements, boolean shallow)
/*  82:    */     throws QueryException, MappingException
/*  83:    */   {
/*  84:136 */     doCompile(replacements, shallow, null);
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void compile(String collectionRole, Map replacements, boolean shallow)
/*  88:    */     throws QueryException, MappingException
/*  89:    */   {
/*  90:153 */     doCompile(replacements, shallow, collectionRole);
/*  91:    */   }
/*  92:    */   
/*  93:    */   private synchronized void doCompile(Map replacements, boolean shallow, String collectionRole)
/*  94:    */   {
/*  95:166 */     if (this.compiled)
/*  96:    */     {
/*  97:167 */       LOG.debug("compile() : The query is already compiled, skipping...");
/*  98:168 */       return;
/*  99:    */     }
/* 100:172 */     this.tokenReplacements = replacements;
/* 101:173 */     if (this.tokenReplacements == null) {
/* 102:174 */       this.tokenReplacements = new HashMap();
/* 103:    */     }
/* 104:176 */     this.shallowQuery = shallow;
/* 105:    */     try
/* 106:    */     {
/* 107:180 */       HqlParser parser = parse(true);
/* 108:    */       
/* 109:    */ 
/* 110:183 */       HqlSqlWalker w = analyze(parser, collectionRole);
/* 111:    */       
/* 112:185 */       this.sqlAst = ((Statement)w.getAST());
/* 113:198 */       if (this.sqlAst.needsExecutor())
/* 114:    */       {
/* 115:199 */         this.statementExecutor = buildAppropriateStatementExecutor(w);
/* 116:    */       }
/* 117:    */       else
/* 118:    */       {
/* 119:203 */         generate((QueryNode)this.sqlAst);
/* 120:204 */         this.queryLoader = new QueryLoader(this, this.factory, w.getSelectClause());
/* 121:    */       }
/* 122:207 */       this.compiled = true;
/* 123:    */     }
/* 124:    */     catch (QueryException qe)
/* 125:    */     {
/* 126:210 */       qe.setQueryString(this.hql);
/* 127:211 */       throw qe;
/* 128:    */     }
/* 129:    */     catch (RecognitionException e)
/* 130:    */     {
/* 131:216 */       LOG.trace("Converted antlr.RecognitionException", e);
/* 132:217 */       throw QuerySyntaxException.convert(e, this.hql);
/* 133:    */     }
/* 134:    */     catch (ANTLRException e)
/* 135:    */     {
/* 136:222 */       LOG.trace("Converted antlr.ANTLRException", e);
/* 137:223 */       throw new QueryException(e.getMessage(), this.hql);
/* 138:    */     }
/* 139:226 */     this.enabledFilters = null;
/* 140:    */   }
/* 141:    */   
/* 142:    */   private void generate(AST sqlAst)
/* 143:    */     throws QueryException, RecognitionException
/* 144:    */   {
/* 145:230 */     if (this.sql == null)
/* 146:    */     {
/* 147:231 */       SqlGenerator gen = new SqlGenerator(this.factory);
/* 148:232 */       gen.statement(sqlAst);
/* 149:233 */       this.sql = gen.getSQL();
/* 150:234 */       if (LOG.isDebugEnabled())
/* 151:    */       {
/* 152:235 */         LOG.debugf("HQL: %s", this.hql);
/* 153:236 */         LOG.debugf("SQL: %s", this.sql);
/* 154:    */       }
/* 155:238 */       gen.getParseErrorHandler().throwQueryException();
/* 156:239 */       this.collectedParameterSpecifications = gen.getCollectedParameters();
/* 157:    */     }
/* 158:    */   }
/* 159:    */   
/* 160:    */   private HqlSqlWalker analyze(HqlParser parser, String collectionRole)
/* 161:    */     throws QueryException, RecognitionException
/* 162:    */   {
/* 163:244 */     HqlSqlWalker w = new HqlSqlWalker(this, this.factory, parser, this.tokenReplacements, collectionRole);
/* 164:245 */     AST hqlAst = parser.getAST();
/* 165:    */     
/* 166:    */ 
/* 167:248 */     w.statement(hqlAst);
/* 168:250 */     if (LOG.isDebugEnabled())
/* 169:    */     {
/* 170:251 */       ASTPrinter printer = new ASTPrinter(SqlTokenTypes.class);
/* 171:252 */       LOG.debug(printer.showAsString(w.getAST(), "--- SQL AST ---"));
/* 172:    */     }
/* 173:255 */     w.getParseErrorHandler().throwQueryException();
/* 174:    */     
/* 175:257 */     return w;
/* 176:    */   }
/* 177:    */   
/* 178:    */   private HqlParser parse(boolean filter)
/* 179:    */     throws TokenStreamException, RecognitionException
/* 180:    */   {
/* 181:262 */     HqlParser parser = HqlParser.getInstance(this.hql);
/* 182:263 */     parser.setFilter(filter);
/* 183:    */     
/* 184:265 */     LOG.debugf("parse() - HQL: %s", this.hql);
/* 185:266 */     parser.statement();
/* 186:    */     
/* 187:268 */     AST hqlAst = parser.getAST();
/* 188:    */     
/* 189:270 */     JavaConstantConverter converter = new JavaConstantConverter();
/* 190:271 */     NodeTraverser walker = new NodeTraverser(converter);
/* 191:272 */     walker.traverseDepthFirst(hqlAst);
/* 192:    */     
/* 193:274 */     showHqlAst(hqlAst);
/* 194:    */     
/* 195:276 */     parser.getParseErrorHandler().throwQueryException();
/* 196:277 */     return parser;
/* 197:    */   }
/* 198:    */   
/* 199:    */   void showHqlAst(AST hqlAst)
/* 200:    */   {
/* 201:281 */     if (LOG.isDebugEnabled())
/* 202:    */     {
/* 203:282 */       ASTPrinter printer = new ASTPrinter(HqlTokenTypes.class);
/* 204:283 */       LOG.debug(printer.showAsString(hqlAst, "--- HQL AST ---"));
/* 205:    */     }
/* 206:    */   }
/* 207:    */   
/* 208:    */   private void errorIfDML()
/* 209:    */     throws HibernateException
/* 210:    */   {
/* 211:288 */     if (this.sqlAst.needsExecutor()) {
/* 212:289 */       throw new QueryExecutionRequestException("Not supported for DML operations", this.hql);
/* 213:    */     }
/* 214:    */   }
/* 215:    */   
/* 216:    */   private void errorIfSelect()
/* 217:    */     throws HibernateException
/* 218:    */   {
/* 219:294 */     if (!this.sqlAst.needsExecutor()) {
/* 220:295 */       throw new QueryExecutionRequestException("Not supported for select queries", this.hql);
/* 221:    */     }
/* 222:    */   }
/* 223:    */   
/* 224:    */   public String getQueryIdentifier()
/* 225:    */   {
/* 226:300 */     return this.queryIdentifier;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public Statement getSqlAST()
/* 230:    */   {
/* 231:304 */     return this.sqlAst;
/* 232:    */   }
/* 233:    */   
/* 234:    */   private HqlSqlWalker getWalker()
/* 235:    */   {
/* 236:308 */     return this.sqlAst.getWalker();
/* 237:    */   }
/* 238:    */   
/* 239:    */   public Type[] getReturnTypes()
/* 240:    */   {
/* 241:317 */     errorIfDML();
/* 242:318 */     return getWalker().getReturnTypes();
/* 243:    */   }
/* 244:    */   
/* 245:    */   public String[] getReturnAliases()
/* 246:    */   {
/* 247:322 */     errorIfDML();
/* 248:323 */     return getWalker().getReturnAliases();
/* 249:    */   }
/* 250:    */   
/* 251:    */   public String[][] getColumnNames()
/* 252:    */   {
/* 253:327 */     errorIfDML();
/* 254:328 */     return getWalker().getSelectClause().getColumnNames();
/* 255:    */   }
/* 256:    */   
/* 257:    */   public Set getQuerySpaces()
/* 258:    */   {
/* 259:332 */     return getWalker().getQuerySpaces();
/* 260:    */   }
/* 261:    */   
/* 262:    */   public List list(SessionImplementor session, QueryParameters queryParameters)
/* 263:    */     throws HibernateException
/* 264:    */   {
/* 265:338 */     errorIfDML();
/* 266:339 */     QueryNode query = (QueryNode)this.sqlAst;
/* 267:340 */     boolean hasLimit = (queryParameters.getRowSelection() != null) && (queryParameters.getRowSelection().definesLimits());
/* 268:341 */     boolean needsDistincting = ((query.getSelectClause().isDistinct()) || (hasLimit)) && (containsCollectionFetches());
/* 269:    */     QueryParameters queryParametersToUse;
/* 270:    */     QueryParameters queryParametersToUse;
/* 271:344 */     if ((hasLimit) && (containsCollectionFetches()))
/* 272:    */     {
/* 273:345 */       LOG.firstOrMaxResultsSpecifiedWithCollectionFetch();
/* 274:346 */       RowSelection selection = new RowSelection();
/* 275:347 */       selection.setFetchSize(queryParameters.getRowSelection().getFetchSize());
/* 276:348 */       selection.setTimeout(queryParameters.getRowSelection().getTimeout());
/* 277:349 */       queryParametersToUse = queryParameters.createCopyUsing(selection);
/* 278:    */     }
/* 279:    */     else
/* 280:    */     {
/* 281:352 */       queryParametersToUse = queryParameters;
/* 282:    */     }
/* 283:355 */     List results = this.queryLoader.list(session, queryParametersToUse);
/* 284:357 */     if (needsDistincting)
/* 285:    */     {
/* 286:358 */       int includedCount = -1;
/* 287:    */       
/* 288:360 */       int first = (!hasLimit) || (queryParameters.getRowSelection().getFirstRow() == null) ? 0 : queryParameters.getRowSelection().getFirstRow().intValue();
/* 289:    */       
/* 290:    */ 
/* 291:363 */       int max = (!hasLimit) || (queryParameters.getRowSelection().getMaxRows() == null) ? -1 : queryParameters.getRowSelection().getMaxRows().intValue();
/* 292:    */       
/* 293:    */ 
/* 294:366 */       int size = results.size();
/* 295:367 */       List tmp = new ArrayList();
/* 296:368 */       IdentitySet distinction = new IdentitySet();
/* 297:369 */       for (int i = 0; i < size; i++)
/* 298:    */       {
/* 299:370 */         Object result = results.get(i);
/* 300:371 */         if (distinction.add(result))
/* 301:    */         {
/* 302:374 */           includedCount++;
/* 303:375 */           if (includedCount >= first)
/* 304:    */           {
/* 305:378 */             tmp.add(result);
/* 306:380 */             if ((max >= 0) && (includedCount - first >= max - 1)) {
/* 307:    */               break;
/* 308:    */             }
/* 309:    */           }
/* 310:    */         }
/* 311:    */       }
/* 312:384 */       results = tmp;
/* 313:    */     }
/* 314:387 */     return results;
/* 315:    */   }
/* 316:    */   
/* 317:    */   public Iterator iterate(QueryParameters queryParameters, EventSource session)
/* 318:    */     throws HibernateException
/* 319:    */   {
/* 320:396 */     errorIfDML();
/* 321:397 */     return this.queryLoader.iterate(queryParameters, session);
/* 322:    */   }
/* 323:    */   
/* 324:    */   public ScrollableResults scroll(QueryParameters queryParameters, SessionImplementor session)
/* 325:    */     throws HibernateException
/* 326:    */   {
/* 327:406 */     errorIfDML();
/* 328:407 */     return this.queryLoader.scroll(queryParameters, session);
/* 329:    */   }
/* 330:    */   
/* 331:    */   public int executeUpdate(QueryParameters queryParameters, SessionImplementor session)
/* 332:    */     throws HibernateException
/* 333:    */   {
/* 334:412 */     errorIfSelect();
/* 335:413 */     return this.statementExecutor.execute(queryParameters, session);
/* 336:    */   }
/* 337:    */   
/* 338:    */   public String getSQLString()
/* 339:    */   {
/* 340:420 */     return this.sql;
/* 341:    */   }
/* 342:    */   
/* 343:    */   public List collectSqlStrings()
/* 344:    */   {
/* 345:424 */     ArrayList list = new ArrayList();
/* 346:425 */     if (isManipulationStatement())
/* 347:    */     {
/* 348:426 */       String[] sqlStatements = this.statementExecutor.getSqlStatements();
/* 349:427 */       for (int i = 0; i < sqlStatements.length; i++) {
/* 350:428 */         list.add(sqlStatements[i]);
/* 351:    */       }
/* 352:    */     }
/* 353:    */     else
/* 354:    */     {
/* 355:432 */       list.add(this.sql);
/* 356:    */     }
/* 357:434 */     return list;
/* 358:    */   }
/* 359:    */   
/* 360:    */   public boolean isShallowQuery()
/* 361:    */   {
/* 362:440 */     return this.shallowQuery;
/* 363:    */   }
/* 364:    */   
/* 365:    */   public String getQueryString()
/* 366:    */   {
/* 367:444 */     return this.hql;
/* 368:    */   }
/* 369:    */   
/* 370:    */   public Map getEnabledFilters()
/* 371:    */   {
/* 372:448 */     return this.enabledFilters;
/* 373:    */   }
/* 374:    */   
/* 375:    */   public int[] getNamedParameterLocs(String name)
/* 376:    */   {
/* 377:452 */     return getWalker().getNamedParameterLocations(name);
/* 378:    */   }
/* 379:    */   
/* 380:    */   public boolean containsCollectionFetches()
/* 381:    */   {
/* 382:456 */     errorIfDML();
/* 383:457 */     List collectionFetches = ((QueryNode)this.sqlAst).getFromClause().getCollectionFetches();
/* 384:458 */     return (collectionFetches != null) && (collectionFetches.size() > 0);
/* 385:    */   }
/* 386:    */   
/* 387:    */   public boolean isManipulationStatement()
/* 388:    */   {
/* 389:462 */     return this.sqlAst.needsExecutor();
/* 390:    */   }
/* 391:    */   
/* 392:    */   public void validateScrollability()
/* 393:    */     throws HibernateException
/* 394:    */   {
/* 395:470 */     errorIfDML();
/* 396:    */     
/* 397:472 */     QueryNode query = (QueryNode)this.sqlAst;
/* 398:    */     
/* 399:    */ 
/* 400:475 */     List collectionFetches = query.getFromClause().getCollectionFetches();
/* 401:476 */     if (collectionFetches.isEmpty()) {
/* 402:477 */       return;
/* 403:    */     }
/* 404:481 */     if (isShallowQuery()) {
/* 405:482 */       return;
/* 406:    */     }
/* 407:487 */     if (getReturnTypes().length > 1) {
/* 408:488 */       throw new HibernateException("cannot scroll with collection fetches and returned tuples");
/* 409:    */     }
/* 410:491 */     FromElement owner = null;
/* 411:492 */     Iterator itr = query.getSelectClause().getFromElementsForLoad().iterator();
/* 412:493 */     while (itr.hasNext())
/* 413:    */     {
/* 414:495 */       FromElement fromElement = (FromElement)itr.next();
/* 415:496 */       if (fromElement.getOrigin() == null)
/* 416:    */       {
/* 417:497 */         owner = fromElement;
/* 418:498 */         break;
/* 419:    */       }
/* 420:    */     }
/* 421:502 */     if (owner == null) {
/* 422:503 */       throw new HibernateException("unable to locate collection fetch(es) owner for scrollability checks");
/* 423:    */     }
/* 424:510 */     AST primaryOrdering = query.getOrderByClause().getFirstChild();
/* 425:511 */     if (primaryOrdering != null)
/* 426:    */     {
/* 427:513 */       String[] idColNames = owner.getQueryable().getIdentifierColumnNames();
/* 428:514 */       String expectedPrimaryOrderSeq = StringHelper.join(", ", StringHelper.qualify(owner.getTableAlias(), idColNames));
/* 429:518 */       if (!primaryOrdering.getText().startsWith(expectedPrimaryOrderSeq)) {
/* 430:519 */         throw new HibernateException("cannot scroll results with collection fetches which are not ordered primarily by the root entity's PK");
/* 431:    */       }
/* 432:    */     }
/* 433:    */   }
/* 434:    */   
/* 435:    */   private StatementExecutor buildAppropriateStatementExecutor(HqlSqlWalker walker)
/* 436:    */   {
/* 437:525 */     Statement statement = (Statement)walker.getAST();
/* 438:526 */     if (walker.getStatementType() == 13)
/* 439:    */     {
/* 440:527 */       FromElement fromElement = walker.getFinalFromClause().getFromElement();
/* 441:528 */       Queryable persister = fromElement.getQueryable();
/* 442:529 */       if (persister.isMultiTable()) {
/* 443:530 */         return new MultiTableDeleteExecutor(walker);
/* 444:    */       }
/* 445:533 */       return new BasicExecutor(walker, persister);
/* 446:    */     }
/* 447:536 */     if (walker.getStatementType() == 51)
/* 448:    */     {
/* 449:537 */       FromElement fromElement = walker.getFinalFromClause().getFromElement();
/* 450:538 */       Queryable persister = fromElement.getQueryable();
/* 451:539 */       if (persister.isMultiTable()) {
/* 452:543 */         return new MultiTableUpdateExecutor(walker);
/* 453:    */       }
/* 454:546 */       return new BasicExecutor(walker, persister);
/* 455:    */     }
/* 456:549 */     if (walker.getStatementType() == 29) {
/* 457:550 */       return new BasicExecutor(walker, ((InsertStatement)statement).getIntoClause().getQueryable());
/* 458:    */     }
/* 459:553 */     throw new QueryException("Unexpected statement type");
/* 460:    */   }
/* 461:    */   
/* 462:    */   public ParameterTranslations getParameterTranslations()
/* 463:    */   {
/* 464:558 */     if (this.paramTranslations == null) {
/* 465:559 */       this.paramTranslations = new ParameterTranslationsImpl(getWalker().getParameters());
/* 466:    */     }
/* 467:562 */     return this.paramTranslations;
/* 468:    */   }
/* 469:    */   
/* 470:    */   public List getCollectedParameterSpecifications()
/* 471:    */   {
/* 472:566 */     return this.collectedParameterSpecifications;
/* 473:    */   }
/* 474:    */   
/* 475:    */   public Class getDynamicInstantiationResultType()
/* 476:    */   {
/* 477:571 */     AggregatedSelectExpression aggregation = this.queryLoader.getAggregatedSelectExpression();
/* 478:572 */     return aggregation == null ? null : aggregation.getAggregationResultType();
/* 479:    */   }
/* 480:    */   
/* 481:    */   public static class JavaConstantConverter
/* 482:    */     implements NodeTraverser.VisitationStrategy
/* 483:    */   {
/* 484:    */     private AST dotRoot;
/* 485:    */     
/* 486:    */     public void visit(AST node)
/* 487:    */     {
/* 488:578 */       if (this.dotRoot != null)
/* 489:    */       {
/* 490:580 */         if (ASTUtil.isSubtreeChild(this.dotRoot, node)) {
/* 491:580 */           return;
/* 492:    */         }
/* 493:582 */         this.dotRoot = null;
/* 494:    */       }
/* 495:585 */       if ((this.dotRoot == null) && (node.getType() == 15))
/* 496:    */       {
/* 497:586 */         this.dotRoot = node;
/* 498:587 */         handleDotStructure(this.dotRoot);
/* 499:    */       }
/* 500:    */     }
/* 501:    */     
/* 502:    */     private void handleDotStructure(AST dotStructureRoot)
/* 503:    */     {
/* 504:591 */       String expression = ASTUtil.getPathText(dotStructureRoot);
/* 505:592 */       Object constant = ReflectHelper.getConstantValue(expression);
/* 506:593 */       if (constant != null)
/* 507:    */       {
/* 508:594 */         dotStructureRoot.setFirstChild(null);
/* 509:595 */         dotStructureRoot.setType(100);
/* 510:596 */         dotStructureRoot.setText(expression);
/* 511:    */       }
/* 512:    */     }
/* 513:    */   }
/* 514:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.QueryTranslatorImpl
 * JD-Core Version:    0.7.0.1
 */