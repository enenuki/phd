/*    1:     */ package org.hibernate.hql.internal.antlr;
/*    2:     */ 
/*    3:     */ import antlr.ASTFactory;
/*    4:     */ import antlr.ASTPair;
/*    5:     */ import antlr.NoViableAltException;
/*    6:     */ import antlr.RecognitionException;
/*    7:     */ import antlr.SemanticException;
/*    8:     */ import antlr.TreeParser;
/*    9:     */ import antlr.collections.AST;
/*   10:     */ import antlr.collections.impl.ASTArray;
/*   11:     */ import antlr.collections.impl.BitSet;
/*   12:     */ import org.hibernate.internal.CoreMessageLogger;
/*   13:     */ import org.jboss.logging.Logger;
/*   14:     */ 
/*   15:     */ public class HqlSqlBaseWalker
/*   16:     */   extends TreeParser
/*   17:     */   implements HqlSqlTokenTypes
/*   18:     */ {
/*   19:  36 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, HqlSqlBaseWalker.class.getName());
/*   20:  38 */   private int level = 0;
/*   21:  39 */   private boolean inSelect = false;
/*   22:  40 */   private boolean inFunctionCall = false;
/*   23:  41 */   private boolean inCase = false;
/*   24:  42 */   private boolean inFrom = false;
/*   25:     */   private int statementType;
/*   26:     */   private String statementTypeName;
/*   27:     */   private int currentClauseType;
/*   28:     */   private int currentTopLevelClauseType;
/*   29:     */   private int currentStatementType;
/*   30:     */   
/*   31:     */   public final boolean isSubQuery()
/*   32:     */   {
/*   33:  54 */     return this.level > 1;
/*   34:     */   }
/*   35:     */   
/*   36:     */   public final boolean isInFrom()
/*   37:     */   {
/*   38:  58 */     return this.inFrom;
/*   39:     */   }
/*   40:     */   
/*   41:     */   public final boolean isInFunctionCall()
/*   42:     */   {
/*   43:  62 */     return this.inFunctionCall;
/*   44:     */   }
/*   45:     */   
/*   46:     */   public final boolean isInSelect()
/*   47:     */   {
/*   48:  66 */     return this.inSelect;
/*   49:     */   }
/*   50:     */   
/*   51:     */   public final boolean isInCase()
/*   52:     */   {
/*   53:  70 */     return this.inCase;
/*   54:     */   }
/*   55:     */   
/*   56:     */   public final int getStatementType()
/*   57:     */   {
/*   58:  74 */     return this.statementType;
/*   59:     */   }
/*   60:     */   
/*   61:     */   public final int getCurrentClauseType()
/*   62:     */   {
/*   63:  78 */     return this.currentClauseType;
/*   64:     */   }
/*   65:     */   
/*   66:     */   public final int getCurrentTopLevelClauseType()
/*   67:     */   {
/*   68:  82 */     return this.currentTopLevelClauseType;
/*   69:     */   }
/*   70:     */   
/*   71:     */   public final int getCurrentStatementType()
/*   72:     */   {
/*   73:  86 */     return this.currentStatementType;
/*   74:     */   }
/*   75:     */   
/*   76:     */   public final boolean isComparativeExpressionClause()
/*   77:     */   {
/*   78:  92 */     return (getCurrentClauseType() == 53) || (getCurrentClauseType() == 60) || (isInCase());
/*   79:     */   }
/*   80:     */   
/*   81:     */   public final boolean isSelectStatement()
/*   82:     */   {
/*   83:  98 */     return this.statementType == 45;
/*   84:     */   }
/*   85:     */   
/*   86:     */   private void beforeStatement(String statementName, int statementType)
/*   87:     */   {
/*   88: 102 */     this.inFunctionCall = false;
/*   89: 103 */     this.level += 1;
/*   90: 104 */     if (this.level == 1)
/*   91:     */     {
/*   92: 105 */       this.statementTypeName = statementName;
/*   93: 106 */       this.statementType = statementType;
/*   94:     */     }
/*   95: 108 */     this.currentStatementType = statementType;
/*   96: 109 */     LOG.debugf("%s << begin [level=%s, statement=%s]", statementName, Integer.valueOf(this.level), this.statementTypeName);
/*   97:     */   }
/*   98:     */   
/*   99:     */   private void beforeStatementCompletion(String statementName)
/*  100:     */   {
/*  101: 113 */     LOG.debugf("%s : finishing up [level=%s, statement=%s]", statementName, Integer.valueOf(this.level), this.statementTypeName);
/*  102:     */   }
/*  103:     */   
/*  104:     */   private void afterStatementCompletion(String statementName)
/*  105:     */   {
/*  106: 117 */     LOG.debugf("%s >> end [level=%s, statement=%s]", statementName, Integer.valueOf(this.level), this.statementTypeName);
/*  107: 118 */     this.level -= 1;
/*  108:     */   }
/*  109:     */   
/*  110:     */   private void handleClauseStart(int clauseType)
/*  111:     */   {
/*  112: 122 */     this.currentClauseType = clauseType;
/*  113: 123 */     if (this.level == 1) {
/*  114: 124 */       this.currentTopLevelClauseType = clauseType;
/*  115:     */     }
/*  116:     */   }
/*  117:     */   
/*  118:     */   protected void evaluateAssignment(AST eq)
/*  119:     */     throws SemanticException
/*  120:     */   {}
/*  121:     */   
/*  122:     */   protected void prepareFromClauseInputTree(AST fromClauseInput) {}
/*  123:     */   
/*  124:     */   protected void pushFromClause(AST fromClause, AST inputFromNode) {}
/*  125:     */   
/*  126:     */   protected AST createFromElement(String path, AST alias, AST propertyFetch)
/*  127:     */     throws SemanticException
/*  128:     */   {
/*  129: 140 */     return null;
/*  130:     */   }
/*  131:     */   
/*  132:     */   protected void createFromJoinElement(AST path, AST alias, int joinType, AST fetch, AST propertyFetch, AST with)
/*  133:     */     throws SemanticException
/*  134:     */   {}
/*  135:     */   
/*  136:     */   protected AST createFromFilterElement(AST filterEntity, AST alias)
/*  137:     */     throws SemanticException
/*  138:     */   {
/*  139: 146 */     return null;
/*  140:     */   }
/*  141:     */   
/*  142:     */   protected void processQuery(AST select, AST query)
/*  143:     */     throws SemanticException
/*  144:     */   {}
/*  145:     */   
/*  146:     */   protected void postProcessUpdate(AST update)
/*  147:     */     throws SemanticException
/*  148:     */   {}
/*  149:     */   
/*  150:     */   protected void postProcessDelete(AST delete)
/*  151:     */     throws SemanticException
/*  152:     */   {}
/*  153:     */   
/*  154:     */   protected void postProcessInsert(AST insert)
/*  155:     */     throws SemanticException
/*  156:     */   {}
/*  157:     */   
/*  158:     */   protected void beforeSelectClause()
/*  159:     */     throws SemanticException
/*  160:     */   {}
/*  161:     */   
/*  162:     */   protected void processIndex(AST indexOp)
/*  163:     */     throws SemanticException
/*  164:     */   {}
/*  165:     */   
/*  166:     */   protected void processConstant(AST constant)
/*  167:     */     throws SemanticException
/*  168:     */   {}
/*  169:     */   
/*  170:     */   protected void processBoolean(AST constant)
/*  171:     */     throws SemanticException
/*  172:     */   {}
/*  173:     */   
/*  174:     */   protected void processNumericLiteral(AST literal)
/*  175:     */     throws SemanticException
/*  176:     */   {}
/*  177:     */   
/*  178:     */   protected void resolve(AST node)
/*  179:     */     throws SemanticException
/*  180:     */   {}
/*  181:     */   
/*  182:     */   protected void resolveSelectExpression(AST dotNode)
/*  183:     */     throws SemanticException
/*  184:     */   {}
/*  185:     */   
/*  186:     */   protected void processFunction(AST functionCall, boolean inSelect)
/*  187:     */     throws SemanticException
/*  188:     */   {}
/*  189:     */   
/*  190:     */   protected void processAggregation(AST node, boolean inSelect)
/*  191:     */     throws SemanticException
/*  192:     */   {}
/*  193:     */   
/*  194:     */   protected void processConstructor(AST constructor)
/*  195:     */     throws SemanticException
/*  196:     */   {}
/*  197:     */   
/*  198:     */   protected AST generateNamedParameter(AST delimiterNode, AST nameNode)
/*  199:     */     throws SemanticException
/*  200:     */   {
/*  201: 178 */     return this.astFactory.make(new ASTArray(1).add(this.astFactory.create(148, nameNode.getText())));
/*  202:     */   }
/*  203:     */   
/*  204:     */   protected AST generatePositionalParameter(AST inputNode)
/*  205:     */     throws SemanticException
/*  206:     */   {
/*  207: 182 */     return this.astFactory.make(new ASTArray(1).add(this.astFactory.create(123, "?")));
/*  208:     */   }
/*  209:     */   
/*  210:     */   protected void lookupAlias(AST ident)
/*  211:     */     throws SemanticException
/*  212:     */   {}
/*  213:     */   
/*  214:     */   protected void setAlias(AST selectExpr, AST ident) {}
/*  215:     */   
/*  216:     */   protected boolean isOrderExpressionResultVariableRef(AST ident)
/*  217:     */     throws SemanticException
/*  218:     */   {
/*  219: 190 */     return false;
/*  220:     */   }
/*  221:     */   
/*  222:     */   protected void handleResultVariableRef(AST resultVariableRef)
/*  223:     */     throws SemanticException
/*  224:     */   {}
/*  225:     */   
/*  226:     */   protected AST lookupProperty(AST dot, boolean root, boolean inSelect)
/*  227:     */     throws SemanticException
/*  228:     */   {
/*  229: 197 */     return dot;
/*  230:     */   }
/*  231:     */   
/*  232:     */   protected boolean isNonQualifiedPropertyRef(AST ident)
/*  233:     */   {
/*  234: 200 */     return false;
/*  235:     */   }
/*  236:     */   
/*  237:     */   protected AST lookupNonQualifiedProperty(AST property)
/*  238:     */     throws SemanticException
/*  239:     */   {
/*  240: 202 */     return property;
/*  241:     */   }
/*  242:     */   
/*  243:     */   protected void setImpliedJoinType(int joinType) {}
/*  244:     */   
/*  245:     */   protected AST createIntoClause(String path, AST propertySpec)
/*  246:     */     throws SemanticException
/*  247:     */   {
/*  248: 207 */     return null;
/*  249:     */   }
/*  250:     */   
/*  251:     */   protected void prepareVersioned(AST updateNode, AST versionedNode)
/*  252:     */     throws SemanticException
/*  253:     */   {}
/*  254:     */   
/*  255:     */   protected void prepareLogicOperator(AST operator)
/*  256:     */     throws SemanticException
/*  257:     */   {}
/*  258:     */   
/*  259:     */   protected void prepareArithmeticOperator(AST operator)
/*  260:     */     throws SemanticException
/*  261:     */   {}
/*  262:     */   
/*  263:     */   protected void processMapComponentReference(AST node)
/*  264:     */     throws SemanticException
/*  265:     */   {}
/*  266:     */   
/*  267:     */   protected void validateMapPropertyExpression(AST node)
/*  268:     */     throws SemanticException
/*  269:     */   {}
/*  270:     */   
/*  271:     */   public HqlSqlBaseWalker()
/*  272:     */   {
/*  273: 220 */     this.tokenNames = _tokenNames;
/*  274:     */   }
/*  275:     */   
/*  276:     */   public final void statement(AST _t)
/*  277:     */     throws RecognitionException
/*  278:     */   {
/*  279: 225 */     AST statement_AST_in = _t == ASTNULL ? null : _t;
/*  280: 226 */     this.returnAST = null;
/*  281: 227 */     ASTPair currentAST = new ASTPair();
/*  282: 228 */     AST statement_AST = null;
/*  283:     */     try
/*  284:     */     {
/*  285: 231 */       if (_t == null) {
/*  286: 231 */         _t = ASTNULL;
/*  287:     */       }
/*  288: 232 */       switch (_t.getType())
/*  289:     */       {
/*  290:     */       case 86: 
/*  291: 235 */         selectStatement(_t);
/*  292: 236 */         _t = this._retTree;
/*  293: 237 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*  294: 238 */         statement_AST = currentAST.root;
/*  295: 239 */         break;
/*  296:     */       case 51: 
/*  297: 243 */         updateStatement(_t);
/*  298: 244 */         _t = this._retTree;
/*  299: 245 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*  300: 246 */         statement_AST = currentAST.root;
/*  301: 247 */         break;
/*  302:     */       case 13: 
/*  303: 251 */         deleteStatement(_t);
/*  304: 252 */         _t = this._retTree;
/*  305: 253 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*  306: 254 */         statement_AST = currentAST.root;
/*  307: 255 */         break;
/*  308:     */       case 29: 
/*  309: 259 */         insertStatement(_t);
/*  310: 260 */         _t = this._retTree;
/*  311: 261 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*  312: 262 */         statement_AST = currentAST.root;
/*  313: 263 */         break;
/*  314:     */       default: 
/*  315: 267 */         throw new NoViableAltException(_t);
/*  316:     */       }
/*  317:     */     }
/*  318:     */     catch (RecognitionException ex)
/*  319:     */     {
/*  320: 272 */       reportError(ex);
/*  321: 273 */       if (_t != null) {
/*  322: 273 */         _t = _t.getNextSibling();
/*  323:     */       }
/*  324:     */     }
/*  325: 275 */     this.returnAST = statement_AST;
/*  326: 276 */     this._retTree = _t;
/*  327:     */   }
/*  328:     */   
/*  329:     */   public final void selectStatement(AST _t)
/*  330:     */     throws RecognitionException
/*  331:     */   {
/*  332: 281 */     AST selectStatement_AST_in = _t == ASTNULL ? null : _t;
/*  333: 282 */     this.returnAST = null;
/*  334: 283 */     ASTPair currentAST = new ASTPair();
/*  335: 284 */     AST selectStatement_AST = null;
/*  336:     */     try
/*  337:     */     {
/*  338: 287 */       query(_t);
/*  339: 288 */       _t = this._retTree;
/*  340: 289 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/*  341: 290 */       selectStatement_AST = currentAST.root;
/*  342:     */     }
/*  343:     */     catch (RecognitionException ex)
/*  344:     */     {
/*  345: 293 */       reportError(ex);
/*  346: 294 */       if (_t != null) {
/*  347: 294 */         _t = _t.getNextSibling();
/*  348:     */       }
/*  349:     */     }
/*  350: 296 */     this.returnAST = selectStatement_AST;
/*  351: 297 */     this._retTree = _t;
/*  352:     */   }
/*  353:     */   
/*  354:     */   public final void updateStatement(AST _t)
/*  355:     */     throws RecognitionException
/*  356:     */   {
/*  357: 302 */     AST updateStatement_AST_in = _t == ASTNULL ? null : _t;
/*  358: 303 */     this.returnAST = null;
/*  359: 304 */     ASTPair currentAST = new ASTPair();
/*  360: 305 */     AST updateStatement_AST = null;
/*  361: 306 */     AST u = null;
/*  362: 307 */     AST u_AST = null;
/*  363: 308 */     AST v = null;
/*  364: 309 */     AST v_AST = null;
/*  365: 310 */     AST f_AST = null;
/*  366: 311 */     AST f = null;
/*  367: 312 */     AST s_AST = null;
/*  368: 313 */     AST s = null;
/*  369: 314 */     AST w_AST = null;
/*  370: 315 */     AST w = null;
/*  371:     */     try
/*  372:     */     {
/*  373: 318 */       AST __t4 = _t;
/*  374: 319 */       u = _t == ASTNULL ? null : _t;
/*  375: 320 */       AST u_AST_in = null;
/*  376: 321 */       u_AST = this.astFactory.create(u);
/*  377: 322 */       ASTPair __currentAST4 = currentAST.copy();
/*  378: 323 */       currentAST.root = currentAST.child;
/*  379: 324 */       currentAST.child = null;
/*  380: 325 */       match(_t, 51);
/*  381: 326 */       _t = _t.getFirstChild();
/*  382: 327 */       beforeStatement("update", 51);
/*  383: 329 */       if (_t == null) {
/*  384: 329 */         _t = ASTNULL;
/*  385:     */       }
/*  386: 330 */       switch (_t.getType())
/*  387:     */       {
/*  388:     */       case 52: 
/*  389: 333 */         v = _t;
/*  390: 334 */         AST v_AST_in = null;
/*  391: 335 */         v_AST = this.astFactory.create(v);
/*  392: 336 */         match(_t, 52);
/*  393: 337 */         _t = _t.getNextSibling();
/*  394: 338 */         break;
/*  395:     */       case 22: 
/*  396:     */         break;
/*  397:     */       default: 
/*  398: 346 */         throw new NoViableAltException(_t);
/*  399:     */       }
/*  400: 350 */       f = _t == ASTNULL ? null : _t;
/*  401: 351 */       fromClause(_t);
/*  402: 352 */       _t = this._retTree;
/*  403: 353 */       f_AST = this.returnAST;
/*  404: 354 */       s = _t == ASTNULL ? null : _t;
/*  405: 355 */       setClause(_t);
/*  406: 356 */       _t = this._retTree;
/*  407: 357 */       s_AST = this.returnAST;
/*  408: 359 */       if (_t == null) {
/*  409: 359 */         _t = ASTNULL;
/*  410:     */       }
/*  411: 360 */       switch (_t.getType())
/*  412:     */       {
/*  413:     */       case 53: 
/*  414: 363 */         w = _t == ASTNULL ? null : _t;
/*  415: 364 */         whereClause(_t);
/*  416: 365 */         _t = this._retTree;
/*  417: 366 */         w_AST = this.returnAST;
/*  418: 367 */         break;
/*  419:     */       case 3: 
/*  420:     */         break;
/*  421:     */       default: 
/*  422: 375 */         throw new NoViableAltException(_t);
/*  423:     */       }
/*  424: 379 */       currentAST = __currentAST4;
/*  425: 380 */       _t = __t4;
/*  426: 381 */       _t = _t.getNextSibling();
/*  427: 382 */       updateStatement_AST = currentAST.root;
/*  428:     */       
/*  429: 384 */       updateStatement_AST = this.astFactory.make(new ASTArray(4).add(u_AST).add(f_AST).add(s_AST).add(w_AST));
/*  430: 385 */       beforeStatementCompletion("update");
/*  431: 386 */       prepareVersioned(updateStatement_AST, v_AST);
/*  432: 387 */       postProcessUpdate(updateStatement_AST);
/*  433: 388 */       afterStatementCompletion("update");
/*  434:     */       
/*  435: 390 */       currentAST.root = updateStatement_AST;
/*  436: 391 */       currentAST.child = ((updateStatement_AST != null) && (updateStatement_AST.getFirstChild() != null) ? updateStatement_AST.getFirstChild() : updateStatement_AST);
/*  437:     */       
/*  438: 393 */       currentAST.advanceChildToEnd();
/*  439:     */     }
/*  440:     */     catch (RecognitionException ex)
/*  441:     */     {
/*  442: 396 */       reportError(ex);
/*  443: 397 */       if (_t != null) {
/*  444: 397 */         _t = _t.getNextSibling();
/*  445:     */       }
/*  446:     */     }
/*  447: 399 */     this.returnAST = updateStatement_AST;
/*  448: 400 */     this._retTree = _t;
/*  449:     */   }
/*  450:     */   
/*  451:     */   public final void deleteStatement(AST _t)
/*  452:     */     throws RecognitionException
/*  453:     */   {
/*  454: 405 */     AST deleteStatement_AST_in = _t == ASTNULL ? null : _t;
/*  455: 406 */     this.returnAST = null;
/*  456: 407 */     ASTPair currentAST = new ASTPair();
/*  457: 408 */     AST deleteStatement_AST = null;
/*  458:     */     try
/*  459:     */     {
/*  460: 411 */       AST __t8 = _t;
/*  461: 412 */       AST tmp1_AST = null;
/*  462: 413 */       AST tmp1_AST_in = null;
/*  463: 414 */       tmp1_AST = this.astFactory.create(_t);
/*  464: 415 */       tmp1_AST_in = _t;
/*  465: 416 */       this.astFactory.addASTChild(currentAST, tmp1_AST);
/*  466: 417 */       ASTPair __currentAST8 = currentAST.copy();
/*  467: 418 */       currentAST.root = currentAST.child;
/*  468: 419 */       currentAST.child = null;
/*  469: 420 */       match(_t, 13);
/*  470: 421 */       _t = _t.getFirstChild();
/*  471: 422 */       beforeStatement("delete", 13);
/*  472: 423 */       fromClause(_t);
/*  473: 424 */       _t = this._retTree;
/*  474: 425 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/*  475: 427 */       if (_t == null) {
/*  476: 427 */         _t = ASTNULL;
/*  477:     */       }
/*  478: 428 */       switch (_t.getType())
/*  479:     */       {
/*  480:     */       case 53: 
/*  481: 431 */         whereClause(_t);
/*  482: 432 */         _t = this._retTree;
/*  483: 433 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*  484: 434 */         break;
/*  485:     */       case 3: 
/*  486:     */         break;
/*  487:     */       default: 
/*  488: 442 */         throw new NoViableAltException(_t);
/*  489:     */       }
/*  490: 446 */       currentAST = __currentAST8;
/*  491: 447 */       _t = __t8;
/*  492: 448 */       _t = _t.getNextSibling();
/*  493: 449 */       deleteStatement_AST = currentAST.root;
/*  494:     */       
/*  495: 451 */       beforeStatementCompletion("delete");
/*  496: 452 */       postProcessDelete(deleteStatement_AST);
/*  497: 453 */       afterStatementCompletion("delete");
/*  498:     */       
/*  499: 455 */       deleteStatement_AST = currentAST.root;
/*  500:     */     }
/*  501:     */     catch (RecognitionException ex)
/*  502:     */     {
/*  503: 458 */       reportError(ex);
/*  504: 459 */       if (_t != null) {
/*  505: 459 */         _t = _t.getNextSibling();
/*  506:     */       }
/*  507:     */     }
/*  508: 461 */     this.returnAST = deleteStatement_AST;
/*  509: 462 */     this._retTree = _t;
/*  510:     */   }
/*  511:     */   
/*  512:     */   public final void insertStatement(AST _t)
/*  513:     */     throws RecognitionException
/*  514:     */   {
/*  515: 467 */     AST insertStatement_AST_in = _t == ASTNULL ? null : _t;
/*  516: 468 */     this.returnAST = null;
/*  517: 469 */     ASTPair currentAST = new ASTPair();
/*  518: 470 */     AST insertStatement_AST = null;
/*  519:     */     try
/*  520:     */     {
/*  521: 473 */       AST __t11 = _t;
/*  522: 474 */       AST tmp2_AST = null;
/*  523: 475 */       AST tmp2_AST_in = null;
/*  524: 476 */       tmp2_AST = this.astFactory.create(_t);
/*  525: 477 */       tmp2_AST_in = _t;
/*  526: 478 */       this.astFactory.addASTChild(currentAST, tmp2_AST);
/*  527: 479 */       ASTPair __currentAST11 = currentAST.copy();
/*  528: 480 */       currentAST.root = currentAST.child;
/*  529: 481 */       currentAST.child = null;
/*  530: 482 */       match(_t, 29);
/*  531: 483 */       _t = _t.getFirstChild();
/*  532: 484 */       beforeStatement("insert", 29);
/*  533: 485 */       intoClause(_t);
/*  534: 486 */       _t = this._retTree;
/*  535: 487 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/*  536: 488 */       query(_t);
/*  537: 489 */       _t = this._retTree;
/*  538: 490 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/*  539: 491 */       currentAST = __currentAST11;
/*  540: 492 */       _t = __t11;
/*  541: 493 */       _t = _t.getNextSibling();
/*  542: 494 */       insertStatement_AST = currentAST.root;
/*  543:     */       
/*  544: 496 */       beforeStatementCompletion("insert");
/*  545: 497 */       postProcessInsert(insertStatement_AST);
/*  546: 498 */       afterStatementCompletion("insert");
/*  547:     */       
/*  548: 500 */       insertStatement_AST = currentAST.root;
/*  549:     */     }
/*  550:     */     catch (RecognitionException ex)
/*  551:     */     {
/*  552: 503 */       reportError(ex);
/*  553: 504 */       if (_t != null) {
/*  554: 504 */         _t = _t.getNextSibling();
/*  555:     */       }
/*  556:     */     }
/*  557: 506 */     this.returnAST = insertStatement_AST;
/*  558: 507 */     this._retTree = _t;
/*  559:     */   }
/*  560:     */   
/*  561:     */   public final void query(AST _t)
/*  562:     */     throws RecognitionException
/*  563:     */   {
/*  564: 512 */     AST query_AST_in = _t == ASTNULL ? null : _t;
/*  565: 513 */     this.returnAST = null;
/*  566: 514 */     ASTPair currentAST = new ASTPair();
/*  567: 515 */     AST query_AST = null;
/*  568: 516 */     AST f_AST = null;
/*  569: 517 */     AST f = null;
/*  570: 518 */     AST s_AST = null;
/*  571: 519 */     AST s = null;
/*  572: 520 */     AST w_AST = null;
/*  573: 521 */     AST w = null;
/*  574: 522 */     AST g_AST = null;
/*  575: 523 */     AST g = null;
/*  576: 524 */     AST o_AST = null;
/*  577: 525 */     AST o = null;
/*  578:     */     try
/*  579:     */     {
/*  580: 528 */       AST __t29 = _t;
/*  581: 529 */       AST tmp3_AST = null;
/*  582: 530 */       AST tmp3_AST_in = null;
/*  583: 531 */       tmp3_AST = this.astFactory.create(_t);
/*  584: 532 */       tmp3_AST_in = _t;
/*  585: 533 */       ASTPair __currentAST29 = currentAST.copy();
/*  586: 534 */       currentAST.root = currentAST.child;
/*  587: 535 */       currentAST.child = null;
/*  588: 536 */       match(_t, 86);
/*  589: 537 */       _t = _t.getFirstChild();
/*  590: 538 */       beforeStatement("select", 45);
/*  591: 539 */       AST __t30 = _t;
/*  592: 540 */       AST tmp4_AST = null;
/*  593: 541 */       AST tmp4_AST_in = null;
/*  594: 542 */       tmp4_AST = this.astFactory.create(_t);
/*  595: 543 */       tmp4_AST_in = _t;
/*  596: 544 */       ASTPair __currentAST30 = currentAST.copy();
/*  597: 545 */       currentAST.root = currentAST.child;
/*  598: 546 */       currentAST.child = null;
/*  599: 547 */       match(_t, 89);
/*  600: 548 */       _t = _t.getFirstChild();
/*  601: 549 */       f = _t == ASTNULL ? null : _t;
/*  602: 550 */       fromClause(_t);
/*  603: 551 */       _t = this._retTree;
/*  604: 552 */       f_AST = this.returnAST;
/*  605: 554 */       if (_t == null) {
/*  606: 554 */         _t = ASTNULL;
/*  607:     */       }
/*  608: 555 */       switch (_t.getType())
/*  609:     */       {
/*  610:     */       case 45: 
/*  611: 558 */         s = _t == ASTNULL ? null : _t;
/*  612: 559 */         selectClause(_t);
/*  613: 560 */         _t = this._retTree;
/*  614: 561 */         s_AST = this.returnAST;
/*  615: 562 */         break;
/*  616:     */       case 3: 
/*  617:     */         break;
/*  618:     */       default: 
/*  619: 570 */         throw new NoViableAltException(_t);
/*  620:     */       }
/*  621: 574 */       currentAST = __currentAST30;
/*  622: 575 */       _t = __t30;
/*  623: 576 */       _t = _t.getNextSibling();
/*  624: 578 */       if (_t == null) {
/*  625: 578 */         _t = ASTNULL;
/*  626:     */       }
/*  627: 579 */       switch (_t.getType())
/*  628:     */       {
/*  629:     */       case 53: 
/*  630: 582 */         w = _t == ASTNULL ? null : _t;
/*  631: 583 */         whereClause(_t);
/*  632: 584 */         _t = this._retTree;
/*  633: 585 */         w_AST = this.returnAST;
/*  634: 586 */         break;
/*  635:     */       case 3: 
/*  636:     */       case 24: 
/*  637:     */       case 41: 
/*  638:     */         break;
/*  639:     */       default: 
/*  640: 596 */         throw new NoViableAltException(_t);
/*  641:     */       }
/*  642: 601 */       if (_t == null) {
/*  643: 601 */         _t = ASTNULL;
/*  644:     */       }
/*  645: 602 */       switch (_t.getType())
/*  646:     */       {
/*  647:     */       case 24: 
/*  648: 605 */         g = _t == ASTNULL ? null : _t;
/*  649: 606 */         groupClause(_t);
/*  650: 607 */         _t = this._retTree;
/*  651: 608 */         g_AST = this.returnAST;
/*  652: 609 */         break;
/*  653:     */       case 3: 
/*  654:     */       case 41: 
/*  655:     */         break;
/*  656:     */       default: 
/*  657: 618 */         throw new NoViableAltException(_t);
/*  658:     */       }
/*  659: 623 */       if (_t == null) {
/*  660: 623 */         _t = ASTNULL;
/*  661:     */       }
/*  662: 624 */       switch (_t.getType())
/*  663:     */       {
/*  664:     */       case 41: 
/*  665: 627 */         o = _t == ASTNULL ? null : _t;
/*  666: 628 */         orderClause(_t);
/*  667: 629 */         _t = this._retTree;
/*  668: 630 */         o_AST = this.returnAST;
/*  669: 631 */         break;
/*  670:     */       case 3: 
/*  671:     */         break;
/*  672:     */       default: 
/*  673: 639 */         throw new NoViableAltException(_t);
/*  674:     */       }
/*  675: 643 */       currentAST = __currentAST29;
/*  676: 644 */       _t = __t29;
/*  677: 645 */       _t = _t.getNextSibling();
/*  678: 646 */       query_AST = currentAST.root;
/*  679:     */       
/*  680:     */ 
/*  681: 649 */       query_AST = this.astFactory.make(new ASTArray(6).add(this.astFactory.create(45, "SELECT")).add(s_AST).add(f_AST).add(w_AST).add(g_AST).add(o_AST));
/*  682: 650 */       beforeStatementCompletion("select");
/*  683: 651 */       processQuery(s_AST, query_AST);
/*  684: 652 */       afterStatementCompletion("select");
/*  685:     */       
/*  686: 654 */       currentAST.root = query_AST;
/*  687: 655 */       currentAST.child = ((query_AST != null) && (query_AST.getFirstChild() != null) ? query_AST.getFirstChild() : query_AST);
/*  688:     */       
/*  689: 657 */       currentAST.advanceChildToEnd();
/*  690:     */     }
/*  691:     */     catch (RecognitionException ex)
/*  692:     */     {
/*  693: 660 */       reportError(ex);
/*  694: 661 */       if (_t != null) {
/*  695: 661 */         _t = _t.getNextSibling();
/*  696:     */       }
/*  697:     */     }
/*  698: 663 */     this.returnAST = query_AST;
/*  699: 664 */     this._retTree = _t;
/*  700:     */   }
/*  701:     */   
/*  702:     */   public final void fromClause(AST _t)
/*  703:     */     throws RecognitionException
/*  704:     */   {
/*  705: 669 */     AST fromClause_AST_in = _t == ASTNULL ? null : _t;
/*  706: 670 */     this.returnAST = null;
/*  707: 671 */     ASTPair currentAST = new ASTPair();
/*  708: 672 */     AST fromClause_AST = null;
/*  709: 673 */     AST f = null;
/*  710: 674 */     AST f_AST = null;
/*  711:     */     
/*  712:     */ 
/*  713:     */ 
/*  714: 678 */     prepareFromClauseInputTree(fromClause_AST_in);
/*  715:     */     try
/*  716:     */     {
/*  717: 682 */       AST __t69 = _t;
/*  718: 683 */       f = _t == ASTNULL ? null : _t;
/*  719: 684 */       AST f_AST_in = null;
/*  720: 685 */       f_AST = this.astFactory.create(f);
/*  721: 686 */       this.astFactory.addASTChild(currentAST, f_AST);
/*  722: 687 */       ASTPair __currentAST69 = currentAST.copy();
/*  723: 688 */       currentAST.root = currentAST.child;
/*  724: 689 */       currentAST.child = null;
/*  725: 690 */       match(_t, 22);
/*  726: 691 */       _t = _t.getFirstChild();
/*  727: 692 */       fromClause_AST = currentAST.root;
/*  728: 693 */       pushFromClause(fromClause_AST, f);handleClauseStart(22);
/*  729: 694 */       fromElementList(_t);
/*  730: 695 */       _t = this._retTree;
/*  731: 696 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/*  732: 697 */       currentAST = __currentAST69;
/*  733: 698 */       _t = __t69;
/*  734: 699 */       _t = _t.getNextSibling();
/*  735: 700 */       fromClause_AST = currentAST.root;
/*  736:     */     }
/*  737:     */     catch (RecognitionException ex)
/*  738:     */     {
/*  739: 703 */       reportError(ex);
/*  740: 704 */       if (_t != null) {
/*  741: 704 */         _t = _t.getNextSibling();
/*  742:     */       }
/*  743:     */     }
/*  744: 706 */     this.returnAST = fromClause_AST;
/*  745: 707 */     this._retTree = _t;
/*  746:     */   }
/*  747:     */   
/*  748:     */   public final void setClause(AST _t)
/*  749:     */     throws RecognitionException
/*  750:     */   {
/*  751: 712 */     AST setClause_AST_in = _t == ASTNULL ? null : _t;
/*  752: 713 */     this.returnAST = null;
/*  753: 714 */     ASTPair currentAST = new ASTPair();
/*  754: 715 */     AST setClause_AST = null;
/*  755:     */     try
/*  756:     */     {
/*  757: 718 */       AST __t20 = _t;
/*  758: 719 */       AST tmp5_AST = null;
/*  759: 720 */       AST tmp5_AST_in = null;
/*  760: 721 */       tmp5_AST = this.astFactory.create(_t);
/*  761: 722 */       tmp5_AST_in = _t;
/*  762: 723 */       this.astFactory.addASTChild(currentAST, tmp5_AST);
/*  763: 724 */       ASTPair __currentAST20 = currentAST.copy();
/*  764: 725 */       currentAST.root = currentAST.child;
/*  765: 726 */       currentAST.child = null;
/*  766: 727 */       match(_t, 46);
/*  767: 728 */       _t = _t.getFirstChild();
/*  768: 729 */       handleClauseStart(46);
/*  769:     */       for (;;)
/*  770:     */       {
/*  771: 733 */         if (_t == null) {
/*  772: 733 */           _t = ASTNULL;
/*  773:     */         }
/*  774: 734 */         if (_t.getType() != 102) {
/*  775:     */           break;
/*  776:     */         }
/*  777: 735 */         assignment(_t);
/*  778: 736 */         _t = this._retTree;
/*  779: 737 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*  780:     */       }
/*  781: 745 */       currentAST = __currentAST20;
/*  782: 746 */       _t = __t20;
/*  783: 747 */       _t = _t.getNextSibling();
/*  784: 748 */       setClause_AST = currentAST.root;
/*  785:     */     }
/*  786:     */     catch (RecognitionException ex)
/*  787:     */     {
/*  788: 751 */       reportError(ex);
/*  789: 752 */       if (_t != null) {
/*  790: 752 */         _t = _t.getNextSibling();
/*  791:     */       }
/*  792:     */     }
/*  793: 754 */     this.returnAST = setClause_AST;
/*  794: 755 */     this._retTree = _t;
/*  795:     */   }
/*  796:     */   
/*  797:     */   public final void whereClause(AST _t)
/*  798:     */     throws RecognitionException
/*  799:     */   {
/*  800: 760 */     AST whereClause_AST_in = _t == ASTNULL ? null : _t;
/*  801: 761 */     this.returnAST = null;
/*  802: 762 */     ASTPair currentAST = new ASTPair();
/*  803: 763 */     AST whereClause_AST = null;
/*  804: 764 */     AST w = null;
/*  805: 765 */     AST w_AST = null;
/*  806: 766 */     AST b_AST = null;
/*  807: 767 */     AST b = null;
/*  808:     */     try
/*  809:     */     {
/*  810: 770 */       AST __t94 = _t;
/*  811: 771 */       w = _t == ASTNULL ? null : _t;
/*  812: 772 */       AST w_AST_in = null;
/*  813: 773 */       w_AST = this.astFactory.create(w);
/*  814: 774 */       this.astFactory.addASTChild(currentAST, w_AST);
/*  815: 775 */       ASTPair __currentAST94 = currentAST.copy();
/*  816: 776 */       currentAST.root = currentAST.child;
/*  817: 777 */       currentAST.child = null;
/*  818: 778 */       match(_t, 53);
/*  819: 779 */       _t = _t.getFirstChild();
/*  820: 780 */       handleClauseStart(53);
/*  821: 781 */       b = _t == ASTNULL ? null : _t;
/*  822: 782 */       logicalExpr(_t);
/*  823: 783 */       _t = this._retTree;
/*  824: 784 */       b_AST = this.returnAST;
/*  825: 785 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/*  826: 786 */       currentAST = __currentAST94;
/*  827: 787 */       _t = __t94;
/*  828: 788 */       _t = _t.getNextSibling();
/*  829: 789 */       whereClause_AST = currentAST.root;
/*  830:     */       
/*  831:     */ 
/*  832: 792 */       whereClause_AST = this.astFactory.make(new ASTArray(2).add(w_AST).add(b_AST));
/*  833:     */       
/*  834: 794 */       currentAST.root = whereClause_AST;
/*  835: 795 */       currentAST.child = ((whereClause_AST != null) && (whereClause_AST.getFirstChild() != null) ? whereClause_AST.getFirstChild() : whereClause_AST);
/*  836:     */       
/*  837: 797 */       currentAST.advanceChildToEnd();
/*  838: 798 */       whereClause_AST = currentAST.root;
/*  839:     */     }
/*  840:     */     catch (RecognitionException ex)
/*  841:     */     {
/*  842: 801 */       reportError(ex);
/*  843: 802 */       if (_t != null) {
/*  844: 802 */         _t = _t.getNextSibling();
/*  845:     */       }
/*  846:     */     }
/*  847: 804 */     this.returnAST = whereClause_AST;
/*  848: 805 */     this._retTree = _t;
/*  849:     */   }
/*  850:     */   
/*  851:     */   public final void intoClause(AST _t)
/*  852:     */     throws RecognitionException
/*  853:     */   {
/*  854: 810 */     AST intoClause_AST_in = _t == ASTNULL ? null : _t;
/*  855: 811 */     this.returnAST = null;
/*  856: 812 */     ASTPair currentAST = new ASTPair();
/*  857: 813 */     AST intoClause_AST = null;
/*  858: 814 */     AST ps_AST = null;
/*  859: 815 */     AST ps = null;
/*  860:     */     
/*  861: 817 */     String p = null;
/*  862:     */     try
/*  863:     */     {
/*  864: 821 */       AST __t13 = _t;
/*  865: 822 */       AST tmp6_AST = null;
/*  866: 823 */       AST tmp6_AST_in = null;
/*  867: 824 */       tmp6_AST = this.astFactory.create(_t);
/*  868: 825 */       tmp6_AST_in = _t;
/*  869: 826 */       ASTPair __currentAST13 = currentAST.copy();
/*  870: 827 */       currentAST.root = currentAST.child;
/*  871: 828 */       currentAST.child = null;
/*  872: 829 */       match(_t, 30);
/*  873: 830 */       _t = _t.getFirstChild();
/*  874: 831 */       handleClauseStart(30);
/*  875:     */       
/*  876: 833 */       p = path(_t);
/*  877: 834 */       _t = this._retTree;
/*  878:     */       
/*  879: 836 */       ps = _t == ASTNULL ? null : _t;
/*  880: 837 */       insertablePropertySpec(_t);
/*  881: 838 */       _t = this._retTree;
/*  882: 839 */       ps_AST = this.returnAST;
/*  883: 840 */       currentAST = __currentAST13;
/*  884: 841 */       _t = __t13;
/*  885: 842 */       _t = _t.getNextSibling();
/*  886: 843 */       intoClause_AST = currentAST.root;
/*  887:     */       
/*  888: 845 */       intoClause_AST = createIntoClause(p, ps);
/*  889:     */       
/*  890: 847 */       currentAST.root = intoClause_AST;
/*  891: 848 */       currentAST.child = ((intoClause_AST != null) && (intoClause_AST.getFirstChild() != null) ? intoClause_AST.getFirstChild() : intoClause_AST);
/*  892:     */       
/*  893: 850 */       currentAST.advanceChildToEnd();
/*  894:     */     }
/*  895:     */     catch (RecognitionException ex)
/*  896:     */     {
/*  897: 853 */       reportError(ex);
/*  898: 854 */       if (_t != null) {
/*  899: 854 */         _t = _t.getNextSibling();
/*  900:     */       }
/*  901:     */     }
/*  902: 856 */     this.returnAST = intoClause_AST;
/*  903: 857 */     this._retTree = _t;
/*  904:     */   }
/*  905:     */   
/*  906:     */   public final String path(AST _t)
/*  907:     */     throws RecognitionException
/*  908:     */   {
/*  909: 863 */     AST path_AST_in = _t == ASTNULL ? null : _t;
/*  910: 864 */     this.returnAST = null;
/*  911: 865 */     ASTPair currentAST = new ASTPair();
/*  912: 866 */     AST path_AST = null;
/*  913: 867 */     AST a_AST = null;
/*  914: 868 */     AST a = null;
/*  915: 869 */     AST y_AST = null;
/*  916: 870 */     AST y = null;
/*  917:     */     
/*  918: 872 */     String p = "???";
/*  919: 873 */     String x = "?x?";
/*  920:     */     try
/*  921:     */     {
/*  922: 877 */       if (_t == null) {
/*  923: 877 */         _t = ASTNULL;
/*  924:     */       }
/*  925: 878 */       switch (_t.getType())
/*  926:     */       {
/*  927:     */       case 93: 
/*  928:     */       case 126: 
/*  929: 882 */         a = _t == ASTNULL ? null : _t;
/*  930: 883 */         identifier(_t);
/*  931: 884 */         _t = this._retTree;
/*  932: 885 */         a_AST = this.returnAST;
/*  933: 886 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*  934: 887 */         p = a.getText();
/*  935: 888 */         path_AST = currentAST.root;
/*  936: 889 */         break;
/*  937:     */       case 15: 
/*  938: 893 */         AST __t89 = _t;
/*  939: 894 */         AST tmp7_AST = null;
/*  940: 895 */         AST tmp7_AST_in = null;
/*  941: 896 */         tmp7_AST = this.astFactory.create(_t);
/*  942: 897 */         tmp7_AST_in = _t;
/*  943: 898 */         this.astFactory.addASTChild(currentAST, tmp7_AST);
/*  944: 899 */         ASTPair __currentAST89 = currentAST.copy();
/*  945: 900 */         currentAST.root = currentAST.child;
/*  946: 901 */         currentAST.child = null;
/*  947: 902 */         match(_t, 15);
/*  948: 903 */         _t = _t.getFirstChild();
/*  949: 904 */         x = path(_t);
/*  950: 905 */         _t = this._retTree;
/*  951: 906 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*  952: 907 */         y = _t == ASTNULL ? null : _t;
/*  953: 908 */         identifier(_t);
/*  954: 909 */         _t = this._retTree;
/*  955: 910 */         y_AST = this.returnAST;
/*  956: 911 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*  957: 912 */         currentAST = __currentAST89;
/*  958: 913 */         _t = __t89;
/*  959: 914 */         _t = _t.getNextSibling();
/*  960:     */         
/*  961: 916 */         StringBuffer buf = new StringBuffer();
/*  962: 917 */         buf.append(x).append(".").append(y.getText());
/*  963: 918 */         p = buf.toString();
/*  964:     */         
/*  965: 920 */         path_AST = currentAST.root;
/*  966: 921 */         break;
/*  967:     */       default: 
/*  968: 925 */         throw new NoViableAltException(_t);
/*  969:     */       }
/*  970:     */     }
/*  971:     */     catch (RecognitionException ex)
/*  972:     */     {
/*  973: 930 */       reportError(ex);
/*  974: 931 */       if (_t != null) {
/*  975: 931 */         _t = _t.getNextSibling();
/*  976:     */       }
/*  977:     */     }
/*  978: 933 */     this.returnAST = path_AST;
/*  979: 934 */     this._retTree = _t;
/*  980: 935 */     return p;
/*  981:     */   }
/*  982:     */   
/*  983:     */   public final void insertablePropertySpec(AST _t)
/*  984:     */     throws RecognitionException
/*  985:     */   {
/*  986: 940 */     AST insertablePropertySpec_AST_in = _t == ASTNULL ? null : _t;
/*  987: 941 */     this.returnAST = null;
/*  988: 942 */     ASTPair currentAST = new ASTPair();
/*  989: 943 */     AST insertablePropertySpec_AST = null;
/*  990:     */     try
/*  991:     */     {
/*  992: 946 */       AST __t16 = _t;
/*  993: 947 */       AST tmp8_AST = null;
/*  994: 948 */       AST tmp8_AST_in = null;
/*  995: 949 */       tmp8_AST = this.astFactory.create(_t);
/*  996: 950 */       tmp8_AST_in = _t;
/*  997: 951 */       this.astFactory.addASTChild(currentAST, tmp8_AST);
/*  998: 952 */       ASTPair __currentAST16 = currentAST.copy();
/*  999: 953 */       currentAST.root = currentAST.child;
/* 1000: 954 */       currentAST.child = null;
/* 1001: 955 */       match(_t, 87);
/* 1002: 956 */       _t = _t.getFirstChild();
/* 1003:     */       
/* 1004: 958 */       int _cnt18 = 0;
/* 1005:     */       for (;;)
/* 1006:     */       {
/* 1007: 961 */         if (_t == null) {
/* 1008: 961 */           _t = ASTNULL;
/* 1009:     */         }
/* 1010: 962 */         if (_t.getType() == 126)
/* 1011:     */         {
/* 1012: 963 */           AST tmp9_AST = null;
/* 1013: 964 */           AST tmp9_AST_in = null;
/* 1014: 965 */           tmp9_AST = this.astFactory.create(_t);
/* 1015: 966 */           tmp9_AST_in = _t;
/* 1016: 967 */           this.astFactory.addASTChild(currentAST, tmp9_AST);
/* 1017: 968 */           match(_t, 126);
/* 1018: 969 */           _t = _t.getNextSibling();
/* 1019:     */         }
/* 1020:     */         else
/* 1021:     */         {
/* 1022: 972 */           if (_cnt18 >= 1) {
/* 1023:     */             break;
/* 1024:     */           }
/* 1025: 972 */           throw new NoViableAltException(_t);
/* 1026:     */         }
/* 1027: 975 */         _cnt18++;
/* 1028:     */       }
/* 1029: 978 */       currentAST = __currentAST16;
/* 1030: 979 */       _t = __t16;
/* 1031: 980 */       _t = _t.getNextSibling();
/* 1032: 981 */       insertablePropertySpec_AST = currentAST.root;
/* 1033:     */     }
/* 1034:     */     catch (RecognitionException ex)
/* 1035:     */     {
/* 1036: 984 */       reportError(ex);
/* 1037: 985 */       if (_t != null) {
/* 1038: 985 */         _t = _t.getNextSibling();
/* 1039:     */       }
/* 1040:     */     }
/* 1041: 987 */     this.returnAST = insertablePropertySpec_AST;
/* 1042: 988 */     this._retTree = _t;
/* 1043:     */   }
/* 1044:     */   
/* 1045:     */   public final void assignment(AST _t)
/* 1046:     */     throws RecognitionException
/* 1047:     */   {
/* 1048: 993 */     AST assignment_AST_in = _t == ASTNULL ? null : _t;
/* 1049: 994 */     this.returnAST = null;
/* 1050: 995 */     ASTPair currentAST = new ASTPair();
/* 1051: 996 */     AST assignment_AST = null;
/* 1052: 997 */     AST p_AST = null;
/* 1053: 998 */     AST p = null;
/* 1054:     */     try
/* 1055:     */     {
/* 1056:1001 */       AST __t24 = _t;
/* 1057:1002 */       AST tmp10_AST = null;
/* 1058:1003 */       AST tmp10_AST_in = null;
/* 1059:1004 */       tmp10_AST = this.astFactory.create(_t);
/* 1060:1005 */       tmp10_AST_in = _t;
/* 1061:1006 */       this.astFactory.addASTChild(currentAST, tmp10_AST);
/* 1062:1007 */       ASTPair __currentAST24 = currentAST.copy();
/* 1063:1008 */       currentAST.root = currentAST.child;
/* 1064:1009 */       currentAST.child = null;
/* 1065:1010 */       match(_t, 102);
/* 1066:1011 */       _t = _t.getFirstChild();
/* 1067:     */       
/* 1068:1013 */       p = _t == ASTNULL ? null : _t;
/* 1069:1014 */       propertyRef(_t);
/* 1070:1015 */       _t = this._retTree;
/* 1071:1016 */       p_AST = this.returnAST;
/* 1072:1017 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1073:     */       
/* 1074:1019 */       resolve(p_AST);
/* 1075:     */       
/* 1076:1021 */       newValue(_t);
/* 1077:1022 */       _t = this._retTree;
/* 1078:1023 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1079:     */       
/* 1080:1025 */       currentAST = __currentAST24;
/* 1081:1026 */       _t = __t24;
/* 1082:1027 */       _t = _t.getNextSibling();
/* 1083:1028 */       assignment_AST = currentAST.root;
/* 1084:     */       
/* 1085:1030 */       evaluateAssignment(assignment_AST);
/* 1086:     */       
/* 1087:1032 */       assignment_AST = currentAST.root;
/* 1088:     */     }
/* 1089:     */     catch (RecognitionException ex)
/* 1090:     */     {
/* 1091:1035 */       reportError(ex);
/* 1092:1036 */       if (_t != null) {
/* 1093:1036 */         _t = _t.getNextSibling();
/* 1094:     */       }
/* 1095:     */     }
/* 1096:1038 */     this.returnAST = assignment_AST;
/* 1097:1039 */     this._retTree = _t;
/* 1098:     */   }
/* 1099:     */   
/* 1100:     */   public final void propertyRef(AST _t)
/* 1101:     */     throws RecognitionException
/* 1102:     */   {
/* 1103:1044 */     AST propertyRef_AST_in = _t == ASTNULL ? null : _t;
/* 1104:1045 */     this.returnAST = null;
/* 1105:1046 */     ASTPair currentAST = new ASTPair();
/* 1106:1047 */     AST propertyRef_AST = null;
/* 1107:1048 */     AST mcr_AST = null;
/* 1108:1049 */     AST mcr = null;
/* 1109:1050 */     AST d = null;
/* 1110:1051 */     AST d_AST = null;
/* 1111:1052 */     AST lhs_AST = null;
/* 1112:1053 */     AST lhs = null;
/* 1113:1054 */     AST rhs_AST = null;
/* 1114:1055 */     AST rhs = null;
/* 1115:1056 */     AST p_AST = null;
/* 1116:1057 */     AST p = null;
/* 1117:     */     try
/* 1118:     */     {
/* 1119:1060 */       if (_t == null) {
/* 1120:1060 */         _t = ASTNULL;
/* 1121:     */       }
/* 1122:1061 */       switch (_t.getType())
/* 1123:     */       {
/* 1124:     */       case 68: 
/* 1125:     */       case 69: 
/* 1126:     */       case 70: 
/* 1127:1066 */         mcr = _t == ASTNULL ? null : _t;
/* 1128:1067 */         mapComponentReference(_t);
/* 1129:1068 */         _t = this._retTree;
/* 1130:1069 */         mcr_AST = this.returnAST;
/* 1131:1070 */         propertyRef_AST = currentAST.root;
/* 1132:     */         
/* 1133:1072 */         resolve(mcr_AST);
/* 1134:1073 */         propertyRef_AST = mcr_AST;
/* 1135:     */         
/* 1136:1075 */         currentAST.root = propertyRef_AST;
/* 1137:1076 */         currentAST.child = ((propertyRef_AST != null) && (propertyRef_AST.getFirstChild() != null) ? propertyRef_AST.getFirstChild() : propertyRef_AST);
/* 1138:     */         
/* 1139:1078 */         currentAST.advanceChildToEnd();
/* 1140:1079 */         break;
/* 1141:     */       case 15: 
/* 1142:1083 */         AST __t176 = _t;
/* 1143:1084 */         d = _t == ASTNULL ? null : _t;
/* 1144:1085 */         AST d_AST_in = null;
/* 1145:1086 */         d_AST = this.astFactory.create(d);
/* 1146:1087 */         ASTPair __currentAST176 = currentAST.copy();
/* 1147:1088 */         currentAST.root = currentAST.child;
/* 1148:1089 */         currentAST.child = null;
/* 1149:1090 */         match(_t, 15);
/* 1150:1091 */         _t = _t.getFirstChild();
/* 1151:1092 */         lhs = _t == ASTNULL ? null : _t;
/* 1152:1093 */         propertyRefLhs(_t);
/* 1153:1094 */         _t = this._retTree;
/* 1154:1095 */         lhs_AST = this.returnAST;
/* 1155:1096 */         rhs = _t == ASTNULL ? null : _t;
/* 1156:1097 */         propertyName(_t);
/* 1157:1098 */         _t = this._retTree;
/* 1158:1099 */         rhs_AST = this.returnAST;
/* 1159:1100 */         currentAST = __currentAST176;
/* 1160:1101 */         _t = __t176;
/* 1161:1102 */         _t = _t.getNextSibling();
/* 1162:1103 */         propertyRef_AST = currentAST.root;
/* 1163:     */         
/* 1164:     */ 
/* 1165:1106 */         propertyRef_AST = this.astFactory.make(new ASTArray(3).add(d_AST).add(lhs_AST).add(rhs_AST));
/* 1166:1107 */         propertyRef_AST = lookupProperty(propertyRef_AST, false, true);
/* 1167:     */         
/* 1168:1109 */         currentAST.root = propertyRef_AST;
/* 1169:1110 */         currentAST.child = ((propertyRef_AST != null) && (propertyRef_AST.getFirstChild() != null) ? propertyRef_AST.getFirstChild() : propertyRef_AST);
/* 1170:     */         
/* 1171:1112 */         currentAST.advanceChildToEnd();
/* 1172:1113 */         break;
/* 1173:     */       case 93: 
/* 1174:     */       case 126: 
/* 1175:1118 */         p = _t == ASTNULL ? null : _t;
/* 1176:1119 */         identifier(_t);
/* 1177:1120 */         _t = this._retTree;
/* 1178:1121 */         p_AST = this.returnAST;
/* 1179:1122 */         propertyRef_AST = currentAST.root;
/* 1180:1128 */         if (isNonQualifiedPropertyRef(p_AST))
/* 1181:     */         {
/* 1182:1129 */           propertyRef_AST = lookupNonQualifiedProperty(p_AST);
/* 1183:     */         }
/* 1184:     */         else
/* 1185:     */         {
/* 1186:1132 */           resolve(p_AST);
/* 1187:1133 */           propertyRef_AST = p_AST;
/* 1188:     */         }
/* 1189:1136 */         currentAST.root = propertyRef_AST;
/* 1190:1137 */         currentAST.child = ((propertyRef_AST != null) && (propertyRef_AST.getFirstChild() != null) ? propertyRef_AST.getFirstChild() : propertyRef_AST);
/* 1191:     */         
/* 1192:1139 */         currentAST.advanceChildToEnd();
/* 1193:1140 */         break;
/* 1194:     */       default: 
/* 1195:1144 */         throw new NoViableAltException(_t);
/* 1196:     */       }
/* 1197:     */     }
/* 1198:     */     catch (RecognitionException ex)
/* 1199:     */     {
/* 1200:1149 */       reportError(ex);
/* 1201:1150 */       if (_t != null) {
/* 1202:1150 */         _t = _t.getNextSibling();
/* 1203:     */       }
/* 1204:     */     }
/* 1205:1152 */     this.returnAST = propertyRef_AST;
/* 1206:1153 */     this._retTree = _t;
/* 1207:     */   }
/* 1208:     */   
/* 1209:     */   public final void newValue(AST _t)
/* 1210:     */     throws RecognitionException
/* 1211:     */   {
/* 1212:1158 */     AST newValue_AST_in = _t == ASTNULL ? null : _t;
/* 1213:1159 */     this.returnAST = null;
/* 1214:1160 */     ASTPair currentAST = new ASTPair();
/* 1215:1161 */     AST newValue_AST = null;
/* 1216:     */     try
/* 1217:     */     {
/* 1218:1164 */       if (_t == null) {
/* 1219:1164 */         _t = ASTNULL;
/* 1220:     */       }
/* 1221:1165 */       switch (_t.getType())
/* 1222:     */       {
/* 1223:     */       case 12: 
/* 1224:     */       case 15: 
/* 1225:     */       case 20: 
/* 1226:     */       case 39: 
/* 1227:     */       case 49: 
/* 1228:     */       case 54: 
/* 1229:     */       case 71: 
/* 1230:     */       case 74: 
/* 1231:     */       case 78: 
/* 1232:     */       case 81: 
/* 1233:     */       case 90: 
/* 1234:     */       case 92: 
/* 1235:     */       case 93: 
/* 1236:     */       case 95: 
/* 1237:     */       case 96: 
/* 1238:     */       case 97: 
/* 1239:     */       case 98: 
/* 1240:     */       case 99: 
/* 1241:     */       case 100: 
/* 1242:     */       case 115: 
/* 1243:     */       case 116: 
/* 1244:     */       case 117: 
/* 1245:     */       case 118: 
/* 1246:     */       case 119: 
/* 1247:     */       case 122: 
/* 1248:     */       case 123: 
/* 1249:     */       case 124: 
/* 1250:     */       case 125: 
/* 1251:     */       case 126: 
/* 1252:1196 */         expr(_t);
/* 1253:1197 */         _t = this._retTree;
/* 1254:1198 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1255:1199 */         newValue_AST = currentAST.root;
/* 1256:1200 */         break;
/* 1257:     */       case 86: 
/* 1258:1204 */         query(_t);
/* 1259:1205 */         _t = this._retTree;
/* 1260:1206 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1261:1207 */         newValue_AST = currentAST.root;
/* 1262:1208 */         break;
/* 1263:     */       case 13: 
/* 1264:     */       case 14: 
/* 1265:     */       case 16: 
/* 1266:     */       case 17: 
/* 1267:     */       case 18: 
/* 1268:     */       case 19: 
/* 1269:     */       case 21: 
/* 1270:     */       case 22: 
/* 1271:     */       case 23: 
/* 1272:     */       case 24: 
/* 1273:     */       case 25: 
/* 1274:     */       case 26: 
/* 1275:     */       case 27: 
/* 1276:     */       case 28: 
/* 1277:     */       case 29: 
/* 1278:     */       case 30: 
/* 1279:     */       case 31: 
/* 1280:     */       case 32: 
/* 1281:     */       case 33: 
/* 1282:     */       case 34: 
/* 1283:     */       case 35: 
/* 1284:     */       case 36: 
/* 1285:     */       case 37: 
/* 1286:     */       case 38: 
/* 1287:     */       case 40: 
/* 1288:     */       case 41: 
/* 1289:     */       case 42: 
/* 1290:     */       case 43: 
/* 1291:     */       case 44: 
/* 1292:     */       case 45: 
/* 1293:     */       case 46: 
/* 1294:     */       case 47: 
/* 1295:     */       case 48: 
/* 1296:     */       case 50: 
/* 1297:     */       case 51: 
/* 1298:     */       case 52: 
/* 1299:     */       case 53: 
/* 1300:     */       case 55: 
/* 1301:     */       case 56: 
/* 1302:     */       case 57: 
/* 1303:     */       case 58: 
/* 1304:     */       case 59: 
/* 1305:     */       case 60: 
/* 1306:     */       case 61: 
/* 1307:     */       case 62: 
/* 1308:     */       case 63: 
/* 1309:     */       case 64: 
/* 1310:     */       case 65: 
/* 1311:     */       case 66: 
/* 1312:     */       case 67: 
/* 1313:     */       case 68: 
/* 1314:     */       case 69: 
/* 1315:     */       case 70: 
/* 1316:     */       case 72: 
/* 1317:     */       case 73: 
/* 1318:     */       case 75: 
/* 1319:     */       case 76: 
/* 1320:     */       case 77: 
/* 1321:     */       case 79: 
/* 1322:     */       case 80: 
/* 1323:     */       case 82: 
/* 1324:     */       case 83: 
/* 1325:     */       case 84: 
/* 1326:     */       case 85: 
/* 1327:     */       case 87: 
/* 1328:     */       case 88: 
/* 1329:     */       case 89: 
/* 1330:     */       case 91: 
/* 1331:     */       case 94: 
/* 1332:     */       case 101: 
/* 1333:     */       case 102: 
/* 1334:     */       case 103: 
/* 1335:     */       case 104: 
/* 1336:     */       case 105: 
/* 1337:     */       case 106: 
/* 1338:     */       case 107: 
/* 1339:     */       case 108: 
/* 1340:     */       case 109: 
/* 1341:     */       case 110: 
/* 1342:     */       case 111: 
/* 1343:     */       case 112: 
/* 1344:     */       case 113: 
/* 1345:     */       case 114: 
/* 1346:     */       case 120: 
/* 1347:     */       case 121: 
/* 1348:     */       default: 
/* 1349:1212 */         throw new NoViableAltException(_t);
/* 1350:     */       }
/* 1351:     */     }
/* 1352:     */     catch (RecognitionException ex)
/* 1353:     */     {
/* 1354:1217 */       reportError(ex);
/* 1355:1218 */       if (_t != null) {
/* 1356:1218 */         _t = _t.getNextSibling();
/* 1357:     */       }
/* 1358:     */     }
/* 1359:1220 */     this.returnAST = newValue_AST;
/* 1360:1221 */     this._retTree = _t;
/* 1361:     */   }
/* 1362:     */   
/* 1363:     */   public final void expr(AST _t)
/* 1364:     */     throws RecognitionException
/* 1365:     */   {
/* 1366:1226 */     AST expr_AST_in = _t == ASTNULL ? null : _t;
/* 1367:1227 */     this.returnAST = null;
/* 1368:1228 */     ASTPair currentAST = new ASTPair();
/* 1369:1229 */     AST expr_AST = null;
/* 1370:1230 */     AST ae_AST = null;
/* 1371:1231 */     AST ae = null;
/* 1372:     */     try
/* 1373:     */     {
/* 1374:1234 */       if (_t == null) {
/* 1375:1234 */         _t = ASTNULL;
/* 1376:     */       }
/* 1377:1235 */       switch (_t.getType())
/* 1378:     */       {
/* 1379:     */       case 15: 
/* 1380:     */       case 78: 
/* 1381:     */       case 93: 
/* 1382:     */       case 126: 
/* 1383:1241 */         ae = _t == ASTNULL ? null : _t;
/* 1384:1242 */         addrExpr(_t, true);
/* 1385:1243 */         _t = this._retTree;
/* 1386:1244 */         ae_AST = this.returnAST;
/* 1387:1245 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1388:1246 */         resolve(ae_AST);
/* 1389:1247 */         expr_AST = currentAST.root;
/* 1390:1248 */         break;
/* 1391:     */       case 92: 
/* 1392:1252 */         AST __t133 = _t;
/* 1393:1253 */         AST tmp11_AST = null;
/* 1394:1254 */         AST tmp11_AST_in = null;
/* 1395:1255 */         tmp11_AST = this.astFactory.create(_t);
/* 1396:1256 */         tmp11_AST_in = _t;
/* 1397:1257 */         this.astFactory.addASTChild(currentAST, tmp11_AST);
/* 1398:1258 */         ASTPair __currentAST133 = currentAST.copy();
/* 1399:1259 */         currentAST.root = currentAST.child;
/* 1400:1260 */         currentAST.child = null;
/* 1401:1261 */         match(_t, 92);
/* 1402:1262 */         _t = _t.getFirstChild();
/* 1403:     */         for (;;)
/* 1404:     */         {
/* 1405:1266 */           if (_t == null) {
/* 1406:1266 */             _t = ASTNULL;
/* 1407:     */           }
/* 1408:1267 */           if (!_tokenSet_0.member(_t.getType())) {
/* 1409:     */             break;
/* 1410:     */           }
/* 1411:1268 */           expr(_t);
/* 1412:1269 */           _t = this._retTree;
/* 1413:1270 */           this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1414:     */         }
/* 1415:1278 */         currentAST = __currentAST133;
/* 1416:1279 */         _t = __t133;
/* 1417:1280 */         _t = _t.getNextSibling();
/* 1418:1281 */         expr_AST = currentAST.root;
/* 1419:1282 */         break;
/* 1420:     */       case 20: 
/* 1421:     */       case 39: 
/* 1422:     */       case 49: 
/* 1423:     */       case 95: 
/* 1424:     */       case 96: 
/* 1425:     */       case 97: 
/* 1426:     */       case 98: 
/* 1427:     */       case 99: 
/* 1428:     */       case 100: 
/* 1429:     */       case 124: 
/* 1430:     */       case 125: 
/* 1431:1296 */         constant(_t);
/* 1432:1297 */         _t = this._retTree;
/* 1433:1298 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1434:1299 */         expr_AST = currentAST.root;
/* 1435:1300 */         break;
/* 1436:     */       case 54: 
/* 1437:     */       case 74: 
/* 1438:     */       case 90: 
/* 1439:     */       case 115: 
/* 1440:     */       case 116: 
/* 1441:     */       case 117: 
/* 1442:     */       case 118: 
/* 1443:     */       case 119: 
/* 1444:1311 */         arithmeticExpr(_t);
/* 1445:1312 */         _t = this._retTree;
/* 1446:1313 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1447:1314 */         expr_AST = currentAST.root;
/* 1448:1315 */         break;
/* 1449:     */       case 71: 
/* 1450:     */       case 81: 
/* 1451:1320 */         functionCall(_t);
/* 1452:1321 */         _t = this._retTree;
/* 1453:1322 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1454:1323 */         expr_AST = currentAST.root;
/* 1455:1324 */         break;
/* 1456:     */       case 122: 
/* 1457:     */       case 123: 
/* 1458:1329 */         parameter(_t);
/* 1459:1330 */         _t = this._retTree;
/* 1460:1331 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1461:1332 */         expr_AST = currentAST.root;
/* 1462:1333 */         break;
/* 1463:     */       case 12: 
/* 1464:1337 */         count(_t);
/* 1465:1338 */         _t = this._retTree;
/* 1466:1339 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1467:1340 */         expr_AST = currentAST.root;
/* 1468:1341 */         break;
/* 1469:     */       case 13: 
/* 1470:     */       case 14: 
/* 1471:     */       case 16: 
/* 1472:     */       case 17: 
/* 1473:     */       case 18: 
/* 1474:     */       case 19: 
/* 1475:     */       case 21: 
/* 1476:     */       case 22: 
/* 1477:     */       case 23: 
/* 1478:     */       case 24: 
/* 1479:     */       case 25: 
/* 1480:     */       case 26: 
/* 1481:     */       case 27: 
/* 1482:     */       case 28: 
/* 1483:     */       case 29: 
/* 1484:     */       case 30: 
/* 1485:     */       case 31: 
/* 1486:     */       case 32: 
/* 1487:     */       case 33: 
/* 1488:     */       case 34: 
/* 1489:     */       case 35: 
/* 1490:     */       case 36: 
/* 1491:     */       case 37: 
/* 1492:     */       case 38: 
/* 1493:     */       case 40: 
/* 1494:     */       case 41: 
/* 1495:     */       case 42: 
/* 1496:     */       case 43: 
/* 1497:     */       case 44: 
/* 1498:     */       case 45: 
/* 1499:     */       case 46: 
/* 1500:     */       case 47: 
/* 1501:     */       case 48: 
/* 1502:     */       case 50: 
/* 1503:     */       case 51: 
/* 1504:     */       case 52: 
/* 1505:     */       case 53: 
/* 1506:     */       case 55: 
/* 1507:     */       case 56: 
/* 1508:     */       case 57: 
/* 1509:     */       case 58: 
/* 1510:     */       case 59: 
/* 1511:     */       case 60: 
/* 1512:     */       case 61: 
/* 1513:     */       case 62: 
/* 1514:     */       case 63: 
/* 1515:     */       case 64: 
/* 1516:     */       case 65: 
/* 1517:     */       case 66: 
/* 1518:     */       case 67: 
/* 1519:     */       case 68: 
/* 1520:     */       case 69: 
/* 1521:     */       case 70: 
/* 1522:     */       case 72: 
/* 1523:     */       case 73: 
/* 1524:     */       case 75: 
/* 1525:     */       case 76: 
/* 1526:     */       case 77: 
/* 1527:     */       case 79: 
/* 1528:     */       case 80: 
/* 1529:     */       case 82: 
/* 1530:     */       case 83: 
/* 1531:     */       case 84: 
/* 1532:     */       case 85: 
/* 1533:     */       case 86: 
/* 1534:     */       case 87: 
/* 1535:     */       case 88: 
/* 1536:     */       case 89: 
/* 1537:     */       case 91: 
/* 1538:     */       case 94: 
/* 1539:     */       case 101: 
/* 1540:     */       case 102: 
/* 1541:     */       case 103: 
/* 1542:     */       case 104: 
/* 1543:     */       case 105: 
/* 1544:     */       case 106: 
/* 1545:     */       case 107: 
/* 1546:     */       case 108: 
/* 1547:     */       case 109: 
/* 1548:     */       case 110: 
/* 1549:     */       case 111: 
/* 1550:     */       case 112: 
/* 1551:     */       case 113: 
/* 1552:     */       case 114: 
/* 1553:     */       case 120: 
/* 1554:     */       case 121: 
/* 1555:     */       default: 
/* 1556:1345 */         throw new NoViableAltException(_t);
/* 1557:     */       }
/* 1558:     */     }
/* 1559:     */     catch (RecognitionException ex)
/* 1560:     */     {
/* 1561:1350 */       reportError(ex);
/* 1562:1351 */       if (_t != null) {
/* 1563:1351 */         _t = _t.getNextSibling();
/* 1564:     */       }
/* 1565:     */     }
/* 1566:1353 */     this.returnAST = expr_AST;
/* 1567:1354 */     this._retTree = _t;
/* 1568:     */   }
/* 1569:     */   
/* 1570:     */   public final void selectClause(AST _t)
/* 1571:     */     throws RecognitionException
/* 1572:     */   {
/* 1573:1359 */     AST selectClause_AST_in = _t == ASTNULL ? null : _t;
/* 1574:1360 */     this.returnAST = null;
/* 1575:1361 */     ASTPair currentAST = new ASTPair();
/* 1576:1362 */     AST selectClause_AST = null;
/* 1577:1363 */     AST d = null;
/* 1578:1364 */     AST d_AST = null;
/* 1579:1365 */     AST x_AST = null;
/* 1580:1366 */     AST x = null;
/* 1581:     */     try
/* 1582:     */     {
/* 1583:1369 */       AST __t49 = _t;
/* 1584:1370 */       AST tmp12_AST = null;
/* 1585:1371 */       AST tmp12_AST_in = null;
/* 1586:1372 */       tmp12_AST = this.astFactory.create(_t);
/* 1587:1373 */       tmp12_AST_in = _t;
/* 1588:1374 */       ASTPair __currentAST49 = currentAST.copy();
/* 1589:1375 */       currentAST.root = currentAST.child;
/* 1590:1376 */       currentAST.child = null;
/* 1591:1377 */       match(_t, 45);
/* 1592:1378 */       _t = _t.getFirstChild();
/* 1593:1379 */       handleClauseStart(45);beforeSelectClause();
/* 1594:1381 */       if (_t == null) {
/* 1595:1381 */         _t = ASTNULL;
/* 1596:     */       }
/* 1597:1382 */       switch (_t.getType())
/* 1598:     */       {
/* 1599:     */       case 16: 
/* 1600:1385 */         d = _t;
/* 1601:1386 */         AST d_AST_in = null;
/* 1602:1387 */         d_AST = this.astFactory.create(d);
/* 1603:1388 */         match(_t, 16);
/* 1604:1389 */         _t = _t.getNextSibling();
/* 1605:1390 */         break;
/* 1606:     */       case 4: 
/* 1607:     */       case 7: 
/* 1608:     */       case 12: 
/* 1609:     */       case 15: 
/* 1610:     */       case 17: 
/* 1611:     */       case 27: 
/* 1612:     */       case 54: 
/* 1613:     */       case 65: 
/* 1614:     */       case 68: 
/* 1615:     */       case 69: 
/* 1616:     */       case 70: 
/* 1617:     */       case 71: 
/* 1618:     */       case 73: 
/* 1619:     */       case 74: 
/* 1620:     */       case 81: 
/* 1621:     */       case 86: 
/* 1622:     */       case 90: 
/* 1623:     */       case 93: 
/* 1624:     */       case 95: 
/* 1625:     */       case 96: 
/* 1626:     */       case 97: 
/* 1627:     */       case 98: 
/* 1628:     */       case 99: 
/* 1629:     */       case 115: 
/* 1630:     */       case 116: 
/* 1631:     */       case 117: 
/* 1632:     */       case 118: 
/* 1633:     */       case 119: 
/* 1634:     */       case 124: 
/* 1635:     */       case 125: 
/* 1636:     */       case 126: 
/* 1637:     */         break;
/* 1638:     */       case 5: 
/* 1639:     */       case 6: 
/* 1640:     */       case 8: 
/* 1641:     */       case 9: 
/* 1642:     */       case 10: 
/* 1643:     */       case 11: 
/* 1644:     */       case 13: 
/* 1645:     */       case 14: 
/* 1646:     */       case 18: 
/* 1647:     */       case 19: 
/* 1648:     */       case 20: 
/* 1649:     */       case 21: 
/* 1650:     */       case 22: 
/* 1651:     */       case 23: 
/* 1652:     */       case 24: 
/* 1653:     */       case 25: 
/* 1654:     */       case 26: 
/* 1655:     */       case 28: 
/* 1656:     */       case 29: 
/* 1657:     */       case 30: 
/* 1658:     */       case 31: 
/* 1659:     */       case 32: 
/* 1660:     */       case 33: 
/* 1661:     */       case 34: 
/* 1662:     */       case 35: 
/* 1663:     */       case 36: 
/* 1664:     */       case 37: 
/* 1665:     */       case 38: 
/* 1666:     */       case 39: 
/* 1667:     */       case 40: 
/* 1668:     */       case 41: 
/* 1669:     */       case 42: 
/* 1670:     */       case 43: 
/* 1671:     */       case 44: 
/* 1672:     */       case 45: 
/* 1673:     */       case 46: 
/* 1674:     */       case 47: 
/* 1675:     */       case 48: 
/* 1676:     */       case 49: 
/* 1677:     */       case 50: 
/* 1678:     */       case 51: 
/* 1679:     */       case 52: 
/* 1680:     */       case 53: 
/* 1681:     */       case 55: 
/* 1682:     */       case 56: 
/* 1683:     */       case 57: 
/* 1684:     */       case 58: 
/* 1685:     */       case 59: 
/* 1686:     */       case 60: 
/* 1687:     */       case 61: 
/* 1688:     */       case 62: 
/* 1689:     */       case 63: 
/* 1690:     */       case 64: 
/* 1691:     */       case 66: 
/* 1692:     */       case 67: 
/* 1693:     */       case 72: 
/* 1694:     */       case 75: 
/* 1695:     */       case 76: 
/* 1696:     */       case 77: 
/* 1697:     */       case 78: 
/* 1698:     */       case 79: 
/* 1699:     */       case 80: 
/* 1700:     */       case 82: 
/* 1701:     */       case 83: 
/* 1702:     */       case 84: 
/* 1703:     */       case 85: 
/* 1704:     */       case 87: 
/* 1705:     */       case 88: 
/* 1706:     */       case 89: 
/* 1707:     */       case 91: 
/* 1708:     */       case 92: 
/* 1709:     */       case 94: 
/* 1710:     */       case 100: 
/* 1711:     */       case 101: 
/* 1712:     */       case 102: 
/* 1713:     */       case 103: 
/* 1714:     */       case 104: 
/* 1715:     */       case 105: 
/* 1716:     */       case 106: 
/* 1717:     */       case 107: 
/* 1718:     */       case 108: 
/* 1719:     */       case 109: 
/* 1720:     */       case 110: 
/* 1721:     */       case 111: 
/* 1722:     */       case 112: 
/* 1723:     */       case 113: 
/* 1724:     */       case 114: 
/* 1725:     */       case 120: 
/* 1726:     */       case 121: 
/* 1727:     */       case 122: 
/* 1728:     */       case 123: 
/* 1729:     */       default: 
/* 1730:1428 */         throw new NoViableAltException(_t);
/* 1731:     */       }
/* 1732:1432 */       x = _t == ASTNULL ? null : _t;
/* 1733:1433 */       selectExprList(_t);
/* 1734:1434 */       _t = this._retTree;
/* 1735:1435 */       x_AST = this.returnAST;
/* 1736:1436 */       currentAST = __currentAST49;
/* 1737:1437 */       _t = __t49;
/* 1738:1438 */       _t = _t.getNextSibling();
/* 1739:1439 */       selectClause_AST = currentAST.root;
/* 1740:     */       
/* 1741:1441 */       selectClause_AST = this.astFactory.make(new ASTArray(3).add(this.astFactory.create(137, "{select clause}")).add(d_AST).add(x_AST));
/* 1742:     */       
/* 1743:1443 */       currentAST.root = selectClause_AST;
/* 1744:1444 */       currentAST.child = ((selectClause_AST != null) && (selectClause_AST.getFirstChild() != null) ? selectClause_AST.getFirstChild() : selectClause_AST);
/* 1745:     */       
/* 1746:1446 */       currentAST.advanceChildToEnd();
/* 1747:     */     }
/* 1748:     */     catch (RecognitionException ex)
/* 1749:     */     {
/* 1750:1449 */       reportError(ex);
/* 1751:1450 */       if (_t != null) {
/* 1752:1450 */         _t = _t.getNextSibling();
/* 1753:     */       }
/* 1754:     */     }
/* 1755:1452 */     this.returnAST = selectClause_AST;
/* 1756:1453 */     this._retTree = _t;
/* 1757:     */   }
/* 1758:     */   
/* 1759:     */   public final void groupClause(AST _t)
/* 1760:     */     throws RecognitionException
/* 1761:     */   {
/* 1762:1458 */     AST groupClause_AST_in = _t == ASTNULL ? null : _t;
/* 1763:1459 */     this.returnAST = null;
/* 1764:1460 */     ASTPair currentAST = new ASTPair();
/* 1765:1461 */     AST groupClause_AST = null;
/* 1766:     */     try
/* 1767:     */     {
/* 1768:1464 */       AST __t43 = _t;
/* 1769:1465 */       AST tmp13_AST = null;
/* 1770:1466 */       AST tmp13_AST_in = null;
/* 1771:1467 */       tmp13_AST = this.astFactory.create(_t);
/* 1772:1468 */       tmp13_AST_in = _t;
/* 1773:1469 */       this.astFactory.addASTChild(currentAST, tmp13_AST);
/* 1774:1470 */       ASTPair __currentAST43 = currentAST.copy();
/* 1775:1471 */       currentAST.root = currentAST.child;
/* 1776:1472 */       currentAST.child = null;
/* 1777:1473 */       match(_t, 24);
/* 1778:1474 */       _t = _t.getFirstChild();
/* 1779:1475 */       handleClauseStart(24);
/* 1780:     */       
/* 1781:1477 */       int _cnt45 = 0;
/* 1782:     */       for (;;)
/* 1783:     */       {
/* 1784:1480 */         if (_t == null) {
/* 1785:1480 */           _t = ASTNULL;
/* 1786:     */         }
/* 1787:1481 */         if (_tokenSet_0.member(_t.getType()))
/* 1788:     */         {
/* 1789:1482 */           expr(_t);
/* 1790:1483 */           _t = this._retTree;
/* 1791:1484 */           this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1792:     */         }
/* 1793:     */         else
/* 1794:     */         {
/* 1795:1487 */           if (_cnt45 >= 1) {
/* 1796:     */             break;
/* 1797:     */           }
/* 1798:1487 */           throw new NoViableAltException(_t);
/* 1799:     */         }
/* 1800:1490 */         _cnt45++;
/* 1801:     */       }
/* 1802:1494 */       if (_t == null) {
/* 1803:1494 */         _t = ASTNULL;
/* 1804:     */       }
/* 1805:1495 */       switch (_t.getType())
/* 1806:     */       {
/* 1807:     */       case 25: 
/* 1808:1498 */         AST __t47 = _t;
/* 1809:1499 */         AST tmp14_AST = null;
/* 1810:1500 */         AST tmp14_AST_in = null;
/* 1811:1501 */         tmp14_AST = this.astFactory.create(_t);
/* 1812:1502 */         tmp14_AST_in = _t;
/* 1813:1503 */         this.astFactory.addASTChild(currentAST, tmp14_AST);
/* 1814:1504 */         ASTPair __currentAST47 = currentAST.copy();
/* 1815:1505 */         currentAST.root = currentAST.child;
/* 1816:1506 */         currentAST.child = null;
/* 1817:1507 */         match(_t, 25);
/* 1818:1508 */         _t = _t.getFirstChild();
/* 1819:1509 */         logicalExpr(_t);
/* 1820:1510 */         _t = this._retTree;
/* 1821:1511 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1822:1512 */         currentAST = __currentAST47;
/* 1823:1513 */         _t = __t47;
/* 1824:1514 */         _t = _t.getNextSibling();
/* 1825:1515 */         break;
/* 1826:     */       case 3: 
/* 1827:     */         break;
/* 1828:     */       default: 
/* 1829:1523 */         throw new NoViableAltException(_t);
/* 1830:     */       }
/* 1831:1527 */       currentAST = __currentAST43;
/* 1832:1528 */       _t = __t43;
/* 1833:1529 */       _t = _t.getNextSibling();
/* 1834:1530 */       groupClause_AST = currentAST.root;
/* 1835:     */     }
/* 1836:     */     catch (RecognitionException ex)
/* 1837:     */     {
/* 1838:1533 */       reportError(ex);
/* 1839:1534 */       if (_t != null) {
/* 1840:1534 */         _t = _t.getNextSibling();
/* 1841:     */       }
/* 1842:     */     }
/* 1843:1536 */     this.returnAST = groupClause_AST;
/* 1844:1537 */     this._retTree = _t;
/* 1845:     */   }
/* 1846:     */   
/* 1847:     */   public final void orderClause(AST _t)
/* 1848:     */     throws RecognitionException
/* 1849:     */   {
/* 1850:1542 */     AST orderClause_AST_in = _t == ASTNULL ? null : _t;
/* 1851:1543 */     this.returnAST = null;
/* 1852:1544 */     ASTPair currentAST = new ASTPair();
/* 1853:1545 */     AST orderClause_AST = null;
/* 1854:     */     try
/* 1855:     */     {
/* 1856:1548 */       AST __t36 = _t;
/* 1857:1549 */       AST tmp15_AST = null;
/* 1858:1550 */       AST tmp15_AST_in = null;
/* 1859:1551 */       tmp15_AST = this.astFactory.create(_t);
/* 1860:1552 */       tmp15_AST_in = _t;
/* 1861:1553 */       this.astFactory.addASTChild(currentAST, tmp15_AST);
/* 1862:1554 */       ASTPair __currentAST36 = currentAST.copy();
/* 1863:1555 */       currentAST.root = currentAST.child;
/* 1864:1556 */       currentAST.child = null;
/* 1865:1557 */       match(_t, 41);
/* 1866:1558 */       _t = _t.getFirstChild();
/* 1867:1559 */       handleClauseStart(41);
/* 1868:1560 */       orderExprs(_t);
/* 1869:1561 */       _t = this._retTree;
/* 1870:1562 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1871:1563 */       currentAST = __currentAST36;
/* 1872:1564 */       _t = __t36;
/* 1873:1565 */       _t = _t.getNextSibling();
/* 1874:1566 */       orderClause_AST = currentAST.root;
/* 1875:     */     }
/* 1876:     */     catch (RecognitionException ex)
/* 1877:     */     {
/* 1878:1569 */       reportError(ex);
/* 1879:1570 */       if (_t != null) {
/* 1880:1570 */         _t = _t.getNextSibling();
/* 1881:     */       }
/* 1882:     */     }
/* 1883:1572 */     this.returnAST = orderClause_AST;
/* 1884:1573 */     this._retTree = _t;
/* 1885:     */   }
/* 1886:     */   
/* 1887:     */   public final void orderExprs(AST _t)
/* 1888:     */     throws RecognitionException
/* 1889:     */   {
/* 1890:1578 */     AST orderExprs_AST_in = _t == ASTNULL ? null : _t;
/* 1891:1579 */     this.returnAST = null;
/* 1892:1580 */     ASTPair currentAST = new ASTPair();
/* 1893:1581 */     AST orderExprs_AST = null;
/* 1894:     */     try
/* 1895:     */     {
/* 1896:1584 */       orderExpr(_t);
/* 1897:1585 */       _t = this._retTree;
/* 1898:1586 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1899:1588 */       if (_t == null) {
/* 1900:1588 */         _t = ASTNULL;
/* 1901:     */       }
/* 1902:1589 */       switch (_t.getType())
/* 1903:     */       {
/* 1904:     */       case 8: 
/* 1905:1592 */         AST tmp16_AST = null;
/* 1906:1593 */         AST tmp16_AST_in = null;
/* 1907:1594 */         tmp16_AST = this.astFactory.create(_t);
/* 1908:1595 */         tmp16_AST_in = _t;
/* 1909:1596 */         this.astFactory.addASTChild(currentAST, tmp16_AST);
/* 1910:1597 */         match(_t, 8);
/* 1911:1598 */         _t = _t.getNextSibling();
/* 1912:1599 */         break;
/* 1913:     */       case 14: 
/* 1914:1603 */         AST tmp17_AST = null;
/* 1915:1604 */         AST tmp17_AST_in = null;
/* 1916:1605 */         tmp17_AST = this.astFactory.create(_t);
/* 1917:1606 */         tmp17_AST_in = _t;
/* 1918:1607 */         this.astFactory.addASTChild(currentAST, tmp17_AST);
/* 1919:1608 */         match(_t, 14);
/* 1920:1609 */         _t = _t.getNextSibling();
/* 1921:1610 */         break;
/* 1922:     */       case 3: 
/* 1923:     */       case 12: 
/* 1924:     */       case 15: 
/* 1925:     */       case 20: 
/* 1926:     */       case 39: 
/* 1927:     */       case 49: 
/* 1928:     */       case 54: 
/* 1929:     */       case 71: 
/* 1930:     */       case 74: 
/* 1931:     */       case 78: 
/* 1932:     */       case 81: 
/* 1933:     */       case 90: 
/* 1934:     */       case 92: 
/* 1935:     */       case 93: 
/* 1936:     */       case 95: 
/* 1937:     */       case 96: 
/* 1938:     */       case 97: 
/* 1939:     */       case 98: 
/* 1940:     */       case 99: 
/* 1941:     */       case 100: 
/* 1942:     */       case 115: 
/* 1943:     */       case 116: 
/* 1944:     */       case 117: 
/* 1945:     */       case 118: 
/* 1946:     */       case 119: 
/* 1947:     */       case 122: 
/* 1948:     */       case 123: 
/* 1949:     */       case 124: 
/* 1950:     */       case 125: 
/* 1951:     */       case 126: 
/* 1952:     */         break;
/* 1953:     */       case 4: 
/* 1954:     */       case 5: 
/* 1955:     */       case 6: 
/* 1956:     */       case 7: 
/* 1957:     */       case 9: 
/* 1958:     */       case 10: 
/* 1959:     */       case 11: 
/* 1960:     */       case 13: 
/* 1961:     */       case 16: 
/* 1962:     */       case 17: 
/* 1963:     */       case 18: 
/* 1964:     */       case 19: 
/* 1965:     */       case 21: 
/* 1966:     */       case 22: 
/* 1967:     */       case 23: 
/* 1968:     */       case 24: 
/* 1969:     */       case 25: 
/* 1970:     */       case 26: 
/* 1971:     */       case 27: 
/* 1972:     */       case 28: 
/* 1973:     */       case 29: 
/* 1974:     */       case 30: 
/* 1975:     */       case 31: 
/* 1976:     */       case 32: 
/* 1977:     */       case 33: 
/* 1978:     */       case 34: 
/* 1979:     */       case 35: 
/* 1980:     */       case 36: 
/* 1981:     */       case 37: 
/* 1982:     */       case 38: 
/* 1983:     */       case 40: 
/* 1984:     */       case 41: 
/* 1985:     */       case 42: 
/* 1986:     */       case 43: 
/* 1987:     */       case 44: 
/* 1988:     */       case 45: 
/* 1989:     */       case 46: 
/* 1990:     */       case 47: 
/* 1991:     */       case 48: 
/* 1992:     */       case 50: 
/* 1993:     */       case 51: 
/* 1994:     */       case 52: 
/* 1995:     */       case 53: 
/* 1996:     */       case 55: 
/* 1997:     */       case 56: 
/* 1998:     */       case 57: 
/* 1999:     */       case 58: 
/* 2000:     */       case 59: 
/* 2001:     */       case 60: 
/* 2002:     */       case 61: 
/* 2003:     */       case 62: 
/* 2004:     */       case 63: 
/* 2005:     */       case 64: 
/* 2006:     */       case 65: 
/* 2007:     */       case 66: 
/* 2008:     */       case 67: 
/* 2009:     */       case 68: 
/* 2010:     */       case 69: 
/* 2011:     */       case 70: 
/* 2012:     */       case 72: 
/* 2013:     */       case 73: 
/* 2014:     */       case 75: 
/* 2015:     */       case 76: 
/* 2016:     */       case 77: 
/* 2017:     */       case 79: 
/* 2018:     */       case 80: 
/* 2019:     */       case 82: 
/* 2020:     */       case 83: 
/* 2021:     */       case 84: 
/* 2022:     */       case 85: 
/* 2023:     */       case 86: 
/* 2024:     */       case 87: 
/* 2025:     */       case 88: 
/* 2026:     */       case 89: 
/* 2027:     */       case 91: 
/* 2028:     */       case 94: 
/* 2029:     */       case 101: 
/* 2030:     */       case 102: 
/* 2031:     */       case 103: 
/* 2032:     */       case 104: 
/* 2033:     */       case 105: 
/* 2034:     */       case 106: 
/* 2035:     */       case 107: 
/* 2036:     */       case 108: 
/* 2037:     */       case 109: 
/* 2038:     */       case 110: 
/* 2039:     */       case 111: 
/* 2040:     */       case 112: 
/* 2041:     */       case 113: 
/* 2042:     */       case 114: 
/* 2043:     */       case 120: 
/* 2044:     */       case 121: 
/* 2045:     */       default: 
/* 2046:1647 */         throw new NoViableAltException(_t);
/* 2047:     */       }
/* 2048:1652 */       if (_t == null) {
/* 2049:1652 */         _t = ASTNULL;
/* 2050:     */       }
/* 2051:1653 */       switch (_t.getType())
/* 2052:     */       {
/* 2053:     */       case 12: 
/* 2054:     */       case 15: 
/* 2055:     */       case 20: 
/* 2056:     */       case 39: 
/* 2057:     */       case 49: 
/* 2058:     */       case 54: 
/* 2059:     */       case 71: 
/* 2060:     */       case 74: 
/* 2061:     */       case 78: 
/* 2062:     */       case 81: 
/* 2063:     */       case 90: 
/* 2064:     */       case 92: 
/* 2065:     */       case 93: 
/* 2066:     */       case 95: 
/* 2067:     */       case 96: 
/* 2068:     */       case 97: 
/* 2069:     */       case 98: 
/* 2070:     */       case 99: 
/* 2071:     */       case 100: 
/* 2072:     */       case 115: 
/* 2073:     */       case 116: 
/* 2074:     */       case 117: 
/* 2075:     */       case 118: 
/* 2076:     */       case 119: 
/* 2077:     */       case 122: 
/* 2078:     */       case 123: 
/* 2079:     */       case 124: 
/* 2080:     */       case 125: 
/* 2081:     */       case 126: 
/* 2082:1684 */         orderExprs(_t);
/* 2083:1685 */         _t = this._retTree;
/* 2084:1686 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 2085:1687 */         break;
/* 2086:     */       case 3: 
/* 2087:     */         break;
/* 2088:     */       case 4: 
/* 2089:     */       case 5: 
/* 2090:     */       case 6: 
/* 2091:     */       case 7: 
/* 2092:     */       case 8: 
/* 2093:     */       case 9: 
/* 2094:     */       case 10: 
/* 2095:     */       case 11: 
/* 2096:     */       case 13: 
/* 2097:     */       case 14: 
/* 2098:     */       case 16: 
/* 2099:     */       case 17: 
/* 2100:     */       case 18: 
/* 2101:     */       case 19: 
/* 2102:     */       case 21: 
/* 2103:     */       case 22: 
/* 2104:     */       case 23: 
/* 2105:     */       case 24: 
/* 2106:     */       case 25: 
/* 2107:     */       case 26: 
/* 2108:     */       case 27: 
/* 2109:     */       case 28: 
/* 2110:     */       case 29: 
/* 2111:     */       case 30: 
/* 2112:     */       case 31: 
/* 2113:     */       case 32: 
/* 2114:     */       case 33: 
/* 2115:     */       case 34: 
/* 2116:     */       case 35: 
/* 2117:     */       case 36: 
/* 2118:     */       case 37: 
/* 2119:     */       case 38: 
/* 2120:     */       case 40: 
/* 2121:     */       case 41: 
/* 2122:     */       case 42: 
/* 2123:     */       case 43: 
/* 2124:     */       case 44: 
/* 2125:     */       case 45: 
/* 2126:     */       case 46: 
/* 2127:     */       case 47: 
/* 2128:     */       case 48: 
/* 2129:     */       case 50: 
/* 2130:     */       case 51: 
/* 2131:     */       case 52: 
/* 2132:     */       case 53: 
/* 2133:     */       case 55: 
/* 2134:     */       case 56: 
/* 2135:     */       case 57: 
/* 2136:     */       case 58: 
/* 2137:     */       case 59: 
/* 2138:     */       case 60: 
/* 2139:     */       case 61: 
/* 2140:     */       case 62: 
/* 2141:     */       case 63: 
/* 2142:     */       case 64: 
/* 2143:     */       case 65: 
/* 2144:     */       case 66: 
/* 2145:     */       case 67: 
/* 2146:     */       case 68: 
/* 2147:     */       case 69: 
/* 2148:     */       case 70: 
/* 2149:     */       case 72: 
/* 2150:     */       case 73: 
/* 2151:     */       case 75: 
/* 2152:     */       case 76: 
/* 2153:     */       case 77: 
/* 2154:     */       case 79: 
/* 2155:     */       case 80: 
/* 2156:     */       case 82: 
/* 2157:     */       case 83: 
/* 2158:     */       case 84: 
/* 2159:     */       case 85: 
/* 2160:     */       case 86: 
/* 2161:     */       case 87: 
/* 2162:     */       case 88: 
/* 2163:     */       case 89: 
/* 2164:     */       case 91: 
/* 2165:     */       case 94: 
/* 2166:     */       case 101: 
/* 2167:     */       case 102: 
/* 2168:     */       case 103: 
/* 2169:     */       case 104: 
/* 2170:     */       case 105: 
/* 2171:     */       case 106: 
/* 2172:     */       case 107: 
/* 2173:     */       case 108: 
/* 2174:     */       case 109: 
/* 2175:     */       case 110: 
/* 2176:     */       case 111: 
/* 2177:     */       case 112: 
/* 2178:     */       case 113: 
/* 2179:     */       case 114: 
/* 2180:     */       case 120: 
/* 2181:     */       case 121: 
/* 2182:     */       default: 
/* 2183:1695 */         throw new NoViableAltException(_t);
/* 2184:     */       }
/* 2185:1699 */       orderExprs_AST = currentAST.root;
/* 2186:     */     }
/* 2187:     */     catch (RecognitionException ex)
/* 2188:     */     {
/* 2189:1702 */       reportError(ex);
/* 2190:1703 */       if (_t != null) {
/* 2191:1703 */         _t = _t.getNextSibling();
/* 2192:     */       }
/* 2193:     */     }
/* 2194:1705 */     this.returnAST = orderExprs_AST;
/* 2195:1706 */     this._retTree = _t;
/* 2196:     */   }
/* 2197:     */   
/* 2198:     */   public final void orderExpr(AST _t)
/* 2199:     */     throws RecognitionException
/* 2200:     */   {
/* 2201:1711 */     AST orderExpr_AST_in = _t == ASTNULL ? null : _t;
/* 2202:1712 */     this.returnAST = null;
/* 2203:1713 */     ASTPair currentAST = new ASTPair();
/* 2204:1714 */     AST orderExpr_AST = null;
/* 2205:     */     try
/* 2206:     */     {
/* 2207:1717 */       if (_t == null) {
/* 2208:1717 */         _t = ASTNULL;
/* 2209:     */       }
/* 2210:1718 */       if (((_t.getType() == 93) || (_t.getType() == 126)) && (isOrderExpressionResultVariableRef(_t)))
/* 2211:     */       {
/* 2212:1719 */         resultVariableRef(_t);
/* 2213:1720 */         _t = this._retTree;
/* 2214:1721 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 2215:1722 */         orderExpr_AST = currentAST.root;
/* 2216:     */       }
/* 2217:1724 */       else if (_tokenSet_0.member(_t.getType()))
/* 2218:     */       {
/* 2219:1725 */         expr(_t);
/* 2220:1726 */         _t = this._retTree;
/* 2221:1727 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 2222:1728 */         orderExpr_AST = currentAST.root;
/* 2223:     */       }
/* 2224:     */       else
/* 2225:     */       {
/* 2226:1731 */         throw new NoViableAltException(_t);
/* 2227:     */       }
/* 2228:     */     }
/* 2229:     */     catch (RecognitionException ex)
/* 2230:     */     {
/* 2231:1736 */       reportError(ex);
/* 2232:1737 */       if (_t != null) {
/* 2233:1737 */         _t = _t.getNextSibling();
/* 2234:     */       }
/* 2235:     */     }
/* 2236:1739 */     this.returnAST = orderExpr_AST;
/* 2237:1740 */     this._retTree = _t;
/* 2238:     */   }
/* 2239:     */   
/* 2240:     */   public final void resultVariableRef(AST _t)
/* 2241:     */     throws RecognitionException
/* 2242:     */   {
/* 2243:1745 */     AST resultVariableRef_AST_in = _t == ASTNULL ? null : _t;
/* 2244:1746 */     this.returnAST = null;
/* 2245:1747 */     ASTPair currentAST = new ASTPair();
/* 2246:1748 */     AST resultVariableRef_AST = null;
/* 2247:1749 */     AST i_AST = null;
/* 2248:1750 */     AST i = null;
/* 2249:     */     try
/* 2250:     */     {
/* 2251:1753 */       i = _t == ASTNULL ? null : _t;
/* 2252:1754 */       identifier(_t);
/* 2253:1755 */       _t = this._retTree;
/* 2254:1756 */       i_AST = this.returnAST;
/* 2255:1757 */       resultVariableRef_AST = currentAST.root;
/* 2256:     */       
/* 2257:     */ 
/* 2258:1760 */       resultVariableRef_AST = this.astFactory.make(new ASTArray(1).add(this.astFactory.create(150, i.getText())));
/* 2259:1761 */       handleResultVariableRef(resultVariableRef_AST);
/* 2260:     */       
/* 2261:1763 */       currentAST.root = resultVariableRef_AST;
/* 2262:1764 */       currentAST.child = ((resultVariableRef_AST != null) && (resultVariableRef_AST.getFirstChild() != null) ? resultVariableRef_AST.getFirstChild() : resultVariableRef_AST);
/* 2263:     */       
/* 2264:1766 */       currentAST.advanceChildToEnd();
/* 2265:     */     }
/* 2266:     */     catch (RecognitionException ex)
/* 2267:     */     {
/* 2268:1769 */       reportError(ex);
/* 2269:1770 */       if (_t != null) {
/* 2270:1770 */         _t = _t.getNextSibling();
/* 2271:     */       }
/* 2272:     */     }
/* 2273:1772 */     this.returnAST = resultVariableRef_AST;
/* 2274:1773 */     this._retTree = _t;
/* 2275:     */   }
/* 2276:     */   
/* 2277:     */   public final void identifier(AST _t)
/* 2278:     */     throws RecognitionException
/* 2279:     */   {
/* 2280:1778 */     AST identifier_AST_in = _t == ASTNULL ? null : _t;
/* 2281:1779 */     this.returnAST = null;
/* 2282:1780 */     ASTPair currentAST = new ASTPair();
/* 2283:1781 */     AST identifier_AST = null;
/* 2284:     */     try
/* 2285:     */     {
/* 2286:1785 */       if (_t == null) {
/* 2287:1785 */         _t = ASTNULL;
/* 2288:     */       }
/* 2289:1786 */       switch (_t.getType())
/* 2290:     */       {
/* 2291:     */       case 126: 
/* 2292:1789 */         AST tmp18_AST = null;
/* 2293:1790 */         AST tmp18_AST_in = null;
/* 2294:1791 */         tmp18_AST = this.astFactory.create(_t);
/* 2295:1792 */         tmp18_AST_in = _t;
/* 2296:1793 */         this.astFactory.addASTChild(currentAST, tmp18_AST);
/* 2297:1794 */         match(_t, 126);
/* 2298:1795 */         _t = _t.getNextSibling();
/* 2299:1796 */         break;
/* 2300:     */       case 93: 
/* 2301:1800 */         AST tmp19_AST = null;
/* 2302:1801 */         AST tmp19_AST_in = null;
/* 2303:1802 */         tmp19_AST = this.astFactory.create(_t);
/* 2304:1803 */         tmp19_AST_in = _t;
/* 2305:1804 */         this.astFactory.addASTChild(currentAST, tmp19_AST);
/* 2306:1805 */         match(_t, 93);
/* 2307:1806 */         _t = _t.getNextSibling();
/* 2308:1807 */         break;
/* 2309:     */       default: 
/* 2310:1811 */         throw new NoViableAltException(_t);
/* 2311:     */       }
/* 2312:1815 */       identifier_AST = currentAST.root;
/* 2313:     */     }
/* 2314:     */     catch (RecognitionException ex)
/* 2315:     */     {
/* 2316:1818 */       reportError(ex);
/* 2317:1819 */       if (_t != null) {
/* 2318:1819 */         _t = _t.getNextSibling();
/* 2319:     */       }
/* 2320:     */     }
/* 2321:1821 */     this.returnAST = identifier_AST;
/* 2322:1822 */     this._retTree = _t;
/* 2323:     */   }
/* 2324:     */   
/* 2325:     */   public final void logicalExpr(AST _t)
/* 2326:     */     throws RecognitionException
/* 2327:     */   {
/* 2328:1827 */     AST logicalExpr_AST_in = _t == ASTNULL ? null : _t;
/* 2329:1828 */     this.returnAST = null;
/* 2330:1829 */     ASTPair currentAST = new ASTPair();
/* 2331:1830 */     AST logicalExpr_AST = null;
/* 2332:     */     try
/* 2333:     */     {
/* 2334:1833 */       if (_t == null) {
/* 2335:1833 */         _t = ASTNULL;
/* 2336:     */       }
/* 2337:1834 */       switch (_t.getType())
/* 2338:     */       {
/* 2339:     */       case 6: 
/* 2340:1837 */         AST __t96 = _t;
/* 2341:1838 */         AST tmp20_AST = null;
/* 2342:1839 */         AST tmp20_AST_in = null;
/* 2343:1840 */         tmp20_AST = this.astFactory.create(_t);
/* 2344:1841 */         tmp20_AST_in = _t;
/* 2345:1842 */         this.astFactory.addASTChild(currentAST, tmp20_AST);
/* 2346:1843 */         ASTPair __currentAST96 = currentAST.copy();
/* 2347:1844 */         currentAST.root = currentAST.child;
/* 2348:1845 */         currentAST.child = null;
/* 2349:1846 */         match(_t, 6);
/* 2350:1847 */         _t = _t.getFirstChild();
/* 2351:1848 */         logicalExpr(_t);
/* 2352:1849 */         _t = this._retTree;
/* 2353:1850 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 2354:1851 */         logicalExpr(_t);
/* 2355:1852 */         _t = this._retTree;
/* 2356:1853 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 2357:1854 */         currentAST = __currentAST96;
/* 2358:1855 */         _t = __t96;
/* 2359:1856 */         _t = _t.getNextSibling();
/* 2360:1857 */         logicalExpr_AST = currentAST.root;
/* 2361:1858 */         break;
/* 2362:     */       case 40: 
/* 2363:1862 */         AST __t97 = _t;
/* 2364:1863 */         AST tmp21_AST = null;
/* 2365:1864 */         AST tmp21_AST_in = null;
/* 2366:1865 */         tmp21_AST = this.astFactory.create(_t);
/* 2367:1866 */         tmp21_AST_in = _t;
/* 2368:1867 */         this.astFactory.addASTChild(currentAST, tmp21_AST);
/* 2369:1868 */         ASTPair __currentAST97 = currentAST.copy();
/* 2370:1869 */         currentAST.root = currentAST.child;
/* 2371:1870 */         currentAST.child = null;
/* 2372:1871 */         match(_t, 40);
/* 2373:1872 */         _t = _t.getFirstChild();
/* 2374:1873 */         logicalExpr(_t);
/* 2375:1874 */         _t = this._retTree;
/* 2376:1875 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 2377:1876 */         logicalExpr(_t);
/* 2378:1877 */         _t = this._retTree;
/* 2379:1878 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 2380:1879 */         currentAST = __currentAST97;
/* 2381:1880 */         _t = __t97;
/* 2382:1881 */         _t = _t.getNextSibling();
/* 2383:1882 */         logicalExpr_AST = currentAST.root;
/* 2384:1883 */         break;
/* 2385:     */       case 38: 
/* 2386:1887 */         AST __t98 = _t;
/* 2387:1888 */         AST tmp22_AST = null;
/* 2388:1889 */         AST tmp22_AST_in = null;
/* 2389:1890 */         tmp22_AST = this.astFactory.create(_t);
/* 2390:1891 */         tmp22_AST_in = _t;
/* 2391:1892 */         this.astFactory.addASTChild(currentAST, tmp22_AST);
/* 2392:1893 */         ASTPair __currentAST98 = currentAST.copy();
/* 2393:1894 */         currentAST.root = currentAST.child;
/* 2394:1895 */         currentAST.child = null;
/* 2395:1896 */         match(_t, 38);
/* 2396:1897 */         _t = _t.getFirstChild();
/* 2397:1898 */         logicalExpr(_t);
/* 2398:1899 */         _t = this._retTree;
/* 2399:1900 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 2400:1901 */         currentAST = __currentAST98;
/* 2401:1902 */         _t = __t98;
/* 2402:1903 */         _t = _t.getNextSibling();
/* 2403:1904 */         logicalExpr_AST = currentAST.root;
/* 2404:1905 */         break;
/* 2405:     */       case 10: 
/* 2406:     */       case 19: 
/* 2407:     */       case 26: 
/* 2408:     */       case 34: 
/* 2409:     */       case 79: 
/* 2410:     */       case 80: 
/* 2411:     */       case 82: 
/* 2412:     */       case 83: 
/* 2413:     */       case 84: 
/* 2414:     */       case 102: 
/* 2415:     */       case 108: 
/* 2416:     */       case 110: 
/* 2417:     */       case 111: 
/* 2418:     */       case 112: 
/* 2419:     */       case 113: 
/* 2420:1923 */         comparisonExpr(_t);
/* 2421:1924 */         _t = this._retTree;
/* 2422:1925 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 2423:1926 */         logicalExpr_AST = currentAST.root;
/* 2424:1927 */         break;
/* 2425:     */       default: 
/* 2426:1931 */         throw new NoViableAltException(_t);
/* 2427:     */       }
/* 2428:     */     }
/* 2429:     */     catch (RecognitionException ex)
/* 2430:     */     {
/* 2431:1936 */       reportError(ex);
/* 2432:1937 */       if (_t != null) {
/* 2433:1937 */         _t = _t.getNextSibling();
/* 2434:     */       }
/* 2435:     */     }
/* 2436:1939 */     this.returnAST = logicalExpr_AST;
/* 2437:1940 */     this._retTree = _t;
/* 2438:     */   }
/* 2439:     */   
/* 2440:     */   public final void selectExprList(AST _t)
/* 2441:     */     throws RecognitionException
/* 2442:     */   {
/* 2443:1945 */     AST selectExprList_AST_in = _t == ASTNULL ? null : _t;
/* 2444:1946 */     this.returnAST = null;
/* 2445:1947 */     ASTPair currentAST = new ASTPair();
/* 2446:1948 */     AST selectExprList_AST = null;
/* 2447:     */     
/* 2448:1950 */     boolean oldInSelect = this.inSelect;
/* 2449:1951 */     this.inSelect = true;
/* 2450:     */     try
/* 2451:     */     {
/* 2452:1956 */       int _cnt53 = 0;
/* 2453:     */       for (;;)
/* 2454:     */       {
/* 2455:1959 */         if (_t == null) {
/* 2456:1959 */           _t = ASTNULL;
/* 2457:     */         }
/* 2458:1960 */         switch (_t.getType())
/* 2459:     */         {
/* 2460:     */         case 4: 
/* 2461:     */         case 12: 
/* 2462:     */         case 15: 
/* 2463:     */         case 17: 
/* 2464:     */         case 27: 
/* 2465:     */         case 54: 
/* 2466:     */         case 65: 
/* 2467:     */         case 68: 
/* 2468:     */         case 69: 
/* 2469:     */         case 70: 
/* 2470:     */         case 71: 
/* 2471:     */         case 73: 
/* 2472:     */         case 74: 
/* 2473:     */         case 81: 
/* 2474:     */         case 86: 
/* 2475:     */         case 90: 
/* 2476:     */         case 93: 
/* 2477:     */         case 95: 
/* 2478:     */         case 96: 
/* 2479:     */         case 97: 
/* 2480:     */         case 98: 
/* 2481:     */         case 99: 
/* 2482:     */         case 115: 
/* 2483:     */         case 116: 
/* 2484:     */         case 117: 
/* 2485:     */         case 118: 
/* 2486:     */         case 119: 
/* 2487:     */         case 124: 
/* 2488:     */         case 125: 
/* 2489:     */         case 126: 
/* 2490:1992 */           selectExpr(_t);
/* 2491:1993 */           _t = this._retTree;
/* 2492:1994 */           this.astFactory.addASTChild(currentAST, this.returnAST);
/* 2493:1995 */           break;
/* 2494:     */         case 7: 
/* 2495:1999 */           aliasedSelectExpr(_t);
/* 2496:2000 */           _t = this._retTree;
/* 2497:2001 */           this.astFactory.addASTChild(currentAST, this.returnAST);
/* 2498:2002 */           break;
/* 2499:     */         case 5: 
/* 2500:     */         case 6: 
/* 2501:     */         case 8: 
/* 2502:     */         case 9: 
/* 2503:     */         case 10: 
/* 2504:     */         case 11: 
/* 2505:     */         case 13: 
/* 2506:     */         case 14: 
/* 2507:     */         case 16: 
/* 2508:     */         case 18: 
/* 2509:     */         case 19: 
/* 2510:     */         case 20: 
/* 2511:     */         case 21: 
/* 2512:     */         case 22: 
/* 2513:     */         case 23: 
/* 2514:     */         case 24: 
/* 2515:     */         case 25: 
/* 2516:     */         case 26: 
/* 2517:     */         case 28: 
/* 2518:     */         case 29: 
/* 2519:     */         case 30: 
/* 2520:     */         case 31: 
/* 2521:     */         case 32: 
/* 2522:     */         case 33: 
/* 2523:     */         case 34: 
/* 2524:     */         case 35: 
/* 2525:     */         case 36: 
/* 2526:     */         case 37: 
/* 2527:     */         case 38: 
/* 2528:     */         case 39: 
/* 2529:     */         case 40: 
/* 2530:     */         case 41: 
/* 2531:     */         case 42: 
/* 2532:     */         case 43: 
/* 2533:     */         case 44: 
/* 2534:     */         case 45: 
/* 2535:     */         case 46: 
/* 2536:     */         case 47: 
/* 2537:     */         case 48: 
/* 2538:     */         case 49: 
/* 2539:     */         case 50: 
/* 2540:     */         case 51: 
/* 2541:     */         case 52: 
/* 2542:     */         case 53: 
/* 2543:     */         case 55: 
/* 2544:     */         case 56: 
/* 2545:     */         case 57: 
/* 2546:     */         case 58: 
/* 2547:     */         case 59: 
/* 2548:     */         case 60: 
/* 2549:     */         case 61: 
/* 2550:     */         case 62: 
/* 2551:     */         case 63: 
/* 2552:     */         case 64: 
/* 2553:     */         case 66: 
/* 2554:     */         case 67: 
/* 2555:     */         case 72: 
/* 2556:     */         case 75: 
/* 2557:     */         case 76: 
/* 2558:     */         case 77: 
/* 2559:     */         case 78: 
/* 2560:     */         case 79: 
/* 2561:     */         case 80: 
/* 2562:     */         case 82: 
/* 2563:     */         case 83: 
/* 2564:     */         case 84: 
/* 2565:     */         case 85: 
/* 2566:     */         case 87: 
/* 2567:     */         case 88: 
/* 2568:     */         case 89: 
/* 2569:     */         case 91: 
/* 2570:     */         case 92: 
/* 2571:     */         case 94: 
/* 2572:     */         case 100: 
/* 2573:     */         case 101: 
/* 2574:     */         case 102: 
/* 2575:     */         case 103: 
/* 2576:     */         case 104: 
/* 2577:     */         case 105: 
/* 2578:     */         case 106: 
/* 2579:     */         case 107: 
/* 2580:     */         case 108: 
/* 2581:     */         case 109: 
/* 2582:     */         case 110: 
/* 2583:     */         case 111: 
/* 2584:     */         case 112: 
/* 2585:     */         case 113: 
/* 2586:     */         case 114: 
/* 2587:     */         case 120: 
/* 2588:     */         case 121: 
/* 2589:     */         case 122: 
/* 2590:     */         case 123: 
/* 2591:     */         default: 
/* 2592:2006 */           if (_cnt53 >= 1) {
/* 2593:     */             break label638;
/* 2594:     */           }
/* 2595:2006 */           throw new NoViableAltException(_t);
/* 2596:     */         }
/* 2597:2009 */         _cnt53++;
/* 2598:     */       }
/* 2599:     */       label638:
/* 2600:2013 */       this.inSelect = oldInSelect;
/* 2601:2015 */       selectExprList_AST = currentAST.root;
/* 2602:     */     }
/* 2603:     */     catch (RecognitionException ex)
/* 2604:     */     {
/* 2605:2018 */       reportError(ex);
/* 2606:2019 */       if (_t != null) {
/* 2607:2019 */         _t = _t.getNextSibling();
/* 2608:     */       }
/* 2609:     */     }
/* 2610:2021 */     this.returnAST = selectExprList_AST;
/* 2611:2022 */     this._retTree = _t;
/* 2612:     */   }
/* 2613:     */   
/* 2614:     */   public final void selectExpr(AST _t)
/* 2615:     */     throws RecognitionException
/* 2616:     */   {
/* 2617:2027 */     AST selectExpr_AST_in = _t == ASTNULL ? null : _t;
/* 2618:2028 */     this.returnAST = null;
/* 2619:2029 */     ASTPair currentAST = new ASTPair();
/* 2620:2030 */     AST selectExpr_AST = null;
/* 2621:2031 */     AST p_AST = null;
/* 2622:2032 */     AST p = null;
/* 2623:2033 */     AST ar2_AST = null;
/* 2624:2034 */     AST ar2 = null;
/* 2625:2035 */     AST ar3_AST = null;
/* 2626:2036 */     AST ar3 = null;
/* 2627:2037 */     AST con_AST = null;
/* 2628:2038 */     AST con = null;
/* 2629:     */     try
/* 2630:     */     {
/* 2631:2041 */       if (_t == null) {
/* 2632:2041 */         _t = ASTNULL;
/* 2633:     */       }
/* 2634:2042 */       switch (_t.getType())
/* 2635:     */       {
/* 2636:     */       case 15: 
/* 2637:     */       case 68: 
/* 2638:     */       case 69: 
/* 2639:     */       case 70: 
/* 2640:     */       case 93: 
/* 2641:     */       case 126: 
/* 2642:2050 */         p = _t == ASTNULL ? null : _t;
/* 2643:2051 */         propertyRef(_t);
/* 2644:2052 */         _t = this._retTree;
/* 2645:2053 */         p_AST = this.returnAST;
/* 2646:2054 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 2647:2055 */         resolveSelectExpression(p_AST);
/* 2648:2056 */         selectExpr_AST = currentAST.root;
/* 2649:2057 */         break;
/* 2650:     */       case 4: 
/* 2651:2061 */         AST __t57 = _t;
/* 2652:2062 */         AST tmp23_AST = null;
/* 2653:2063 */         AST tmp23_AST_in = null;
/* 2654:2064 */         tmp23_AST = this.astFactory.create(_t);
/* 2655:2065 */         tmp23_AST_in = _t;
/* 2656:2066 */         this.astFactory.addASTChild(currentAST, tmp23_AST);
/* 2657:2067 */         ASTPair __currentAST57 = currentAST.copy();
/* 2658:2068 */         currentAST.root = currentAST.child;
/* 2659:2069 */         currentAST.child = null;
/* 2660:2070 */         match(_t, 4);
/* 2661:2071 */         _t = _t.getFirstChild();
/* 2662:2072 */         ar2 = _t == ASTNULL ? null : _t;
/* 2663:2073 */         aliasRef(_t);
/* 2664:2074 */         _t = this._retTree;
/* 2665:2075 */         ar2_AST = this.returnAST;
/* 2666:2076 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 2667:2077 */         currentAST = __currentAST57;
/* 2668:2078 */         _t = __t57;
/* 2669:2079 */         _t = _t.getNextSibling();
/* 2670:2080 */         selectExpr_AST = currentAST.root;
/* 2671:2081 */         resolveSelectExpression(ar2_AST);selectExpr_AST = ar2_AST;
/* 2672:2082 */         currentAST.root = selectExpr_AST;
/* 2673:2083 */         currentAST.child = ((selectExpr_AST != null) && (selectExpr_AST.getFirstChild() != null) ? selectExpr_AST.getFirstChild() : selectExpr_AST);
/* 2674:     */         
/* 2675:2085 */         currentAST.advanceChildToEnd();
/* 2676:2086 */         selectExpr_AST = currentAST.root;
/* 2677:2087 */         break;
/* 2678:     */       case 65: 
/* 2679:2091 */         AST __t58 = _t;
/* 2680:2092 */         AST tmp24_AST = null;
/* 2681:2093 */         AST tmp24_AST_in = null;
/* 2682:2094 */         tmp24_AST = this.astFactory.create(_t);
/* 2683:2095 */         tmp24_AST_in = _t;
/* 2684:2096 */         this.astFactory.addASTChild(currentAST, tmp24_AST);
/* 2685:2097 */         ASTPair __currentAST58 = currentAST.copy();
/* 2686:2098 */         currentAST.root = currentAST.child;
/* 2687:2099 */         currentAST.child = null;
/* 2688:2100 */         match(_t, 65);
/* 2689:2101 */         _t = _t.getFirstChild();
/* 2690:2102 */         ar3 = _t == ASTNULL ? null : _t;
/* 2691:2103 */         aliasRef(_t);
/* 2692:2104 */         _t = this._retTree;
/* 2693:2105 */         ar3_AST = this.returnAST;
/* 2694:2106 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 2695:2107 */         currentAST = __currentAST58;
/* 2696:2108 */         _t = __t58;
/* 2697:2109 */         _t = _t.getNextSibling();
/* 2698:2110 */         selectExpr_AST = currentAST.root;
/* 2699:2111 */         resolveSelectExpression(ar3_AST);selectExpr_AST = ar3_AST;
/* 2700:2112 */         currentAST.root = selectExpr_AST;
/* 2701:2113 */         currentAST.child = ((selectExpr_AST != null) && (selectExpr_AST.getFirstChild() != null) ? selectExpr_AST.getFirstChild() : selectExpr_AST);
/* 2702:     */         
/* 2703:2115 */         currentAST.advanceChildToEnd();
/* 2704:2116 */         selectExpr_AST = currentAST.root;
/* 2705:2117 */         break;
/* 2706:     */       case 73: 
/* 2707:2121 */         con = _t == ASTNULL ? null : _t;
/* 2708:2122 */         constructor(_t);
/* 2709:2123 */         _t = this._retTree;
/* 2710:2124 */         con_AST = this.returnAST;
/* 2711:2125 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 2712:2126 */         processConstructor(con_AST);
/* 2713:2127 */         selectExpr_AST = currentAST.root;
/* 2714:2128 */         break;
/* 2715:     */       case 71: 
/* 2716:     */       case 81: 
/* 2717:2133 */         functionCall(_t);
/* 2718:2134 */         _t = this._retTree;
/* 2719:2135 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 2720:2136 */         selectExpr_AST = currentAST.root;
/* 2721:2137 */         break;
/* 2722:     */       case 12: 
/* 2723:2141 */         count(_t);
/* 2724:2142 */         _t = this._retTree;
/* 2725:2143 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 2726:2144 */         selectExpr_AST = currentAST.root;
/* 2727:2145 */         break;
/* 2728:     */       case 17: 
/* 2729:     */       case 27: 
/* 2730:2150 */         collectionFunction(_t);
/* 2731:2151 */         _t = this._retTree;
/* 2732:2152 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 2733:2153 */         selectExpr_AST = currentAST.root;
/* 2734:2154 */         break;
/* 2735:     */       case 95: 
/* 2736:     */       case 96: 
/* 2737:     */       case 97: 
/* 2738:     */       case 98: 
/* 2739:     */       case 99: 
/* 2740:     */       case 124: 
/* 2741:     */       case 125: 
/* 2742:2164 */         literal(_t);
/* 2743:2165 */         _t = this._retTree;
/* 2744:2166 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 2745:2167 */         selectExpr_AST = currentAST.root;
/* 2746:2168 */         break;
/* 2747:     */       case 54: 
/* 2748:     */       case 74: 
/* 2749:     */       case 90: 
/* 2750:     */       case 115: 
/* 2751:     */       case 116: 
/* 2752:     */       case 117: 
/* 2753:     */       case 118: 
/* 2754:     */       case 119: 
/* 2755:2179 */         arithmeticExpr(_t);
/* 2756:2180 */         _t = this._retTree;
/* 2757:2181 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 2758:2182 */         selectExpr_AST = currentAST.root;
/* 2759:2183 */         break;
/* 2760:     */       case 86: 
/* 2761:2187 */         query(_t);
/* 2762:2188 */         _t = this._retTree;
/* 2763:2189 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 2764:2190 */         selectExpr_AST = currentAST.root;
/* 2765:2191 */         break;
/* 2766:     */       case 5: 
/* 2767:     */       case 6: 
/* 2768:     */       case 7: 
/* 2769:     */       case 8: 
/* 2770:     */       case 9: 
/* 2771:     */       case 10: 
/* 2772:     */       case 11: 
/* 2773:     */       case 13: 
/* 2774:     */       case 14: 
/* 2775:     */       case 16: 
/* 2776:     */       case 18: 
/* 2777:     */       case 19: 
/* 2778:     */       case 20: 
/* 2779:     */       case 21: 
/* 2780:     */       case 22: 
/* 2781:     */       case 23: 
/* 2782:     */       case 24: 
/* 2783:     */       case 25: 
/* 2784:     */       case 26: 
/* 2785:     */       case 28: 
/* 2786:     */       case 29: 
/* 2787:     */       case 30: 
/* 2788:     */       case 31: 
/* 2789:     */       case 32: 
/* 2790:     */       case 33: 
/* 2791:     */       case 34: 
/* 2792:     */       case 35: 
/* 2793:     */       case 36: 
/* 2794:     */       case 37: 
/* 2795:     */       case 38: 
/* 2796:     */       case 39: 
/* 2797:     */       case 40: 
/* 2798:     */       case 41: 
/* 2799:     */       case 42: 
/* 2800:     */       case 43: 
/* 2801:     */       case 44: 
/* 2802:     */       case 45: 
/* 2803:     */       case 46: 
/* 2804:     */       case 47: 
/* 2805:     */       case 48: 
/* 2806:     */       case 49: 
/* 2807:     */       case 50: 
/* 2808:     */       case 51: 
/* 2809:     */       case 52: 
/* 2810:     */       case 53: 
/* 2811:     */       case 55: 
/* 2812:     */       case 56: 
/* 2813:     */       case 57: 
/* 2814:     */       case 58: 
/* 2815:     */       case 59: 
/* 2816:     */       case 60: 
/* 2817:     */       case 61: 
/* 2818:     */       case 62: 
/* 2819:     */       case 63: 
/* 2820:     */       case 64: 
/* 2821:     */       case 66: 
/* 2822:     */       case 67: 
/* 2823:     */       case 72: 
/* 2824:     */       case 75: 
/* 2825:     */       case 76: 
/* 2826:     */       case 77: 
/* 2827:     */       case 78: 
/* 2828:     */       case 79: 
/* 2829:     */       case 80: 
/* 2830:     */       case 82: 
/* 2831:     */       case 83: 
/* 2832:     */       case 84: 
/* 2833:     */       case 85: 
/* 2834:     */       case 87: 
/* 2835:     */       case 88: 
/* 2836:     */       case 89: 
/* 2837:     */       case 91: 
/* 2838:     */       case 92: 
/* 2839:     */       case 94: 
/* 2840:     */       case 100: 
/* 2841:     */       case 101: 
/* 2842:     */       case 102: 
/* 2843:     */       case 103: 
/* 2844:     */       case 104: 
/* 2845:     */       case 105: 
/* 2846:     */       case 106: 
/* 2847:     */       case 107: 
/* 2848:     */       case 108: 
/* 2849:     */       case 109: 
/* 2850:     */       case 110: 
/* 2851:     */       case 111: 
/* 2852:     */       case 112: 
/* 2853:     */       case 113: 
/* 2854:     */       case 114: 
/* 2855:     */       case 120: 
/* 2856:     */       case 121: 
/* 2857:     */       case 122: 
/* 2858:     */       case 123: 
/* 2859:     */       default: 
/* 2860:2195 */         throw new NoViableAltException(_t);
/* 2861:     */       }
/* 2862:     */     }
/* 2863:     */     catch (RecognitionException ex)
/* 2864:     */     {
/* 2865:2200 */       reportError(ex);
/* 2866:2201 */       if (_t != null) {
/* 2867:2201 */         _t = _t.getNextSibling();
/* 2868:     */       }
/* 2869:     */     }
/* 2870:2203 */     this.returnAST = selectExpr_AST;
/* 2871:2204 */     this._retTree = _t;
/* 2872:     */   }
/* 2873:     */   
/* 2874:     */   public final void aliasedSelectExpr(AST _t)
/* 2875:     */     throws RecognitionException
/* 2876:     */   {
/* 2877:2209 */     AST aliasedSelectExpr_AST_in = _t == ASTNULL ? null : _t;
/* 2878:2210 */     this.returnAST = null;
/* 2879:2211 */     ASTPair currentAST = new ASTPair();
/* 2880:2212 */     AST aliasedSelectExpr_AST = null;
/* 2881:2213 */     AST se_AST = null;
/* 2882:2214 */     AST se = null;
/* 2883:2215 */     AST i_AST = null;
/* 2884:2216 */     AST i = null;
/* 2885:     */     try
/* 2886:     */     {
/* 2887:2219 */       AST __t55 = _t;
/* 2888:2220 */       AST tmp25_AST = null;
/* 2889:2221 */       AST tmp25_AST_in = null;
/* 2890:2222 */       tmp25_AST = this.astFactory.create(_t);
/* 2891:2223 */       tmp25_AST_in = _t;
/* 2892:2224 */       ASTPair __currentAST55 = currentAST.copy();
/* 2893:2225 */       currentAST.root = currentAST.child;
/* 2894:2226 */       currentAST.child = null;
/* 2895:2227 */       match(_t, 7);
/* 2896:2228 */       _t = _t.getFirstChild();
/* 2897:2229 */       se = _t == ASTNULL ? null : _t;
/* 2898:2230 */       selectExpr(_t);
/* 2899:2231 */       _t = this._retTree;
/* 2900:2232 */       se_AST = this.returnAST;
/* 2901:2233 */       i = _t == ASTNULL ? null : _t;
/* 2902:2234 */       identifier(_t);
/* 2903:2235 */       _t = this._retTree;
/* 2904:2236 */       i_AST = this.returnAST;
/* 2905:2237 */       currentAST = __currentAST55;
/* 2906:2238 */       _t = __t55;
/* 2907:2239 */       _t = _t.getNextSibling();
/* 2908:2240 */       aliasedSelectExpr_AST = currentAST.root;
/* 2909:     */       
/* 2910:2242 */       setAlias(se_AST, i_AST);
/* 2911:2243 */       aliasedSelectExpr_AST = se_AST;
/* 2912:     */       
/* 2913:2245 */       currentAST.root = aliasedSelectExpr_AST;
/* 2914:2246 */       currentAST.child = ((aliasedSelectExpr_AST != null) && (aliasedSelectExpr_AST.getFirstChild() != null) ? aliasedSelectExpr_AST.getFirstChild() : aliasedSelectExpr_AST);
/* 2915:     */       
/* 2916:2248 */       currentAST.advanceChildToEnd();
/* 2917:     */     }
/* 2918:     */     catch (RecognitionException ex)
/* 2919:     */     {
/* 2920:2251 */       reportError(ex);
/* 2921:2252 */       if (_t != null) {
/* 2922:2252 */         _t = _t.getNextSibling();
/* 2923:     */       }
/* 2924:     */     }
/* 2925:2254 */     this.returnAST = aliasedSelectExpr_AST;
/* 2926:2255 */     this._retTree = _t;
/* 2927:     */   }
/* 2928:     */   
/* 2929:     */   public final void aliasRef(AST _t)
/* 2930:     */     throws RecognitionException
/* 2931:     */   {
/* 2932:2260 */     AST aliasRef_AST_in = _t == ASTNULL ? null : _t;
/* 2933:2261 */     this.returnAST = null;
/* 2934:2262 */     ASTPair currentAST = new ASTPair();
/* 2935:2263 */     AST aliasRef_AST = null;
/* 2936:2264 */     AST i_AST = null;
/* 2937:2265 */     AST i = null;
/* 2938:     */     try
/* 2939:     */     {
/* 2940:2268 */       i = _t == ASTNULL ? null : _t;
/* 2941:2269 */       identifier(_t);
/* 2942:2270 */       _t = this._retTree;
/* 2943:2271 */       i_AST = this.returnAST;
/* 2944:2272 */       aliasRef_AST = currentAST.root;
/* 2945:     */       
/* 2946:2274 */       aliasRef_AST = this.astFactory.make(new ASTArray(1).add(this.astFactory.create(140, i.getText())));
/* 2947:2275 */       lookupAlias(aliasRef_AST);
/* 2948:     */       
/* 2949:2277 */       currentAST.root = aliasRef_AST;
/* 2950:2278 */       currentAST.child = ((aliasRef_AST != null) && (aliasRef_AST.getFirstChild() != null) ? aliasRef_AST.getFirstChild() : aliasRef_AST);
/* 2951:     */       
/* 2952:2280 */       currentAST.advanceChildToEnd();
/* 2953:     */     }
/* 2954:     */     catch (RecognitionException ex)
/* 2955:     */     {
/* 2956:2283 */       reportError(ex);
/* 2957:2284 */       if (_t != null) {
/* 2958:2284 */         _t = _t.getNextSibling();
/* 2959:     */       }
/* 2960:     */     }
/* 2961:2286 */     this.returnAST = aliasRef_AST;
/* 2962:2287 */     this._retTree = _t;
/* 2963:     */   }
/* 2964:     */   
/* 2965:     */   public final void constructor(AST _t)
/* 2966:     */     throws RecognitionException
/* 2967:     */   {
/* 2968:2292 */     AST constructor_AST_in = _t == ASTNULL ? null : _t;
/* 2969:2293 */     this.returnAST = null;
/* 2970:2294 */     ASTPair currentAST = new ASTPair();
/* 2971:2295 */     AST constructor_AST = null;
/* 2972:2296 */     String className = null;
/* 2973:     */     try
/* 2974:     */     {
/* 2975:2299 */       AST __t64 = _t;
/* 2976:2300 */       AST tmp26_AST = null;
/* 2977:2301 */       AST tmp26_AST_in = null;
/* 2978:2302 */       tmp26_AST = this.astFactory.create(_t);
/* 2979:2303 */       tmp26_AST_in = _t;
/* 2980:2304 */       this.astFactory.addASTChild(currentAST, tmp26_AST);
/* 2981:2305 */       ASTPair __currentAST64 = currentAST.copy();
/* 2982:2306 */       currentAST.root = currentAST.child;
/* 2983:2307 */       currentAST.child = null;
/* 2984:2308 */       match(_t, 73);
/* 2985:2309 */       _t = _t.getFirstChild();
/* 2986:2310 */       className = path(_t);
/* 2987:2311 */       _t = this._retTree;
/* 2988:2312 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/* 2989:     */       for (;;)
/* 2990:     */       {
/* 2991:2316 */         if (_t == null) {
/* 2992:2316 */           _t = ASTNULL;
/* 2993:     */         }
/* 2994:2317 */         switch (_t.getType())
/* 2995:     */         {
/* 2996:     */         case 4: 
/* 2997:     */         case 12: 
/* 2998:     */         case 15: 
/* 2999:     */         case 17: 
/* 3000:     */         case 27: 
/* 3001:     */         case 54: 
/* 3002:     */         case 65: 
/* 3003:     */         case 68: 
/* 3004:     */         case 69: 
/* 3005:     */         case 70: 
/* 3006:     */         case 71: 
/* 3007:     */         case 73: 
/* 3008:     */         case 74: 
/* 3009:     */         case 81: 
/* 3010:     */         case 86: 
/* 3011:     */         case 90: 
/* 3012:     */         case 93: 
/* 3013:     */         case 95: 
/* 3014:     */         case 96: 
/* 3015:     */         case 97: 
/* 3016:     */         case 98: 
/* 3017:     */         case 99: 
/* 3018:     */         case 115: 
/* 3019:     */         case 116: 
/* 3020:     */         case 117: 
/* 3021:     */         case 118: 
/* 3022:     */         case 119: 
/* 3023:     */         case 124: 
/* 3024:     */         case 125: 
/* 3025:     */         case 126: 
/* 3026:2349 */           selectExpr(_t);
/* 3027:2350 */           _t = this._retTree;
/* 3028:2351 */           this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3029:2352 */           break;
/* 3030:     */         case 7: 
/* 3031:2356 */           aliasedSelectExpr(_t);
/* 3032:2357 */           _t = this._retTree;
/* 3033:2358 */           this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3034:     */         }
/* 3035:     */       }
/* 3036:2368 */       currentAST = __currentAST64;
/* 3037:2369 */       _t = __t64;
/* 3038:2370 */       _t = _t.getNextSibling();
/* 3039:2371 */       constructor_AST = currentAST.root;
/* 3040:     */     }
/* 3041:     */     catch (RecognitionException ex)
/* 3042:     */     {
/* 3043:2374 */       reportError(ex);
/* 3044:2375 */       if (_t != null) {
/* 3045:2375 */         _t = _t.getNextSibling();
/* 3046:     */       }
/* 3047:     */     }
/* 3048:2377 */     this.returnAST = constructor_AST;
/* 3049:2378 */     this._retTree = _t;
/* 3050:     */   }
/* 3051:     */   
/* 3052:     */   public final void functionCall(AST _t)
/* 3053:     */     throws RecognitionException
/* 3054:     */   {
/* 3055:2383 */     AST functionCall_AST_in = _t == ASTNULL ? null : _t;
/* 3056:2384 */     this.returnAST = null;
/* 3057:2385 */     ASTPair currentAST = new ASTPair();
/* 3058:2386 */     AST functionCall_AST = null;
/* 3059:     */     try
/* 3060:     */     {
/* 3061:2389 */       if (_t == null) {
/* 3062:2389 */         _t = ASTNULL;
/* 3063:     */       }
/* 3064:2390 */       switch (_t.getType())
/* 3065:     */       {
/* 3066:     */       case 81: 
/* 3067:2393 */         AST __t160 = _t;
/* 3068:2394 */         AST tmp27_AST = null;
/* 3069:2395 */         AST tmp27_AST_in = null;
/* 3070:2396 */         tmp27_AST = this.astFactory.create(_t);
/* 3071:2397 */         tmp27_AST_in = _t;
/* 3072:2398 */         this.astFactory.addASTChild(currentAST, tmp27_AST);
/* 3073:2399 */         ASTPair __currentAST160 = currentAST.copy();
/* 3074:2400 */         currentAST.root = currentAST.child;
/* 3075:2401 */         currentAST.child = null;
/* 3076:2402 */         match(_t, 81);
/* 3077:2403 */         _t = _t.getFirstChild();
/* 3078:2404 */         this.inFunctionCall = true;
/* 3079:2405 */         pathAsIdent(_t);
/* 3080:2406 */         _t = this._retTree;
/* 3081:2407 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3082:2409 */         if (_t == null) {
/* 3083:2409 */           _t = ASTNULL;
/* 3084:     */         }
/* 3085:2410 */         switch (_t.getType())
/* 3086:     */         {
/* 3087:     */         case 75: 
/* 3088:2413 */           AST __t162 = _t;
/* 3089:2414 */           AST tmp28_AST = null;
/* 3090:2415 */           AST tmp28_AST_in = null;
/* 3091:2416 */           tmp28_AST = this.astFactory.create(_t);
/* 3092:2417 */           tmp28_AST_in = _t;
/* 3093:2418 */           this.astFactory.addASTChild(currentAST, tmp28_AST);
/* 3094:2419 */           ASTPair __currentAST162 = currentAST.copy();
/* 3095:2420 */           currentAST.root = currentAST.child;
/* 3096:2421 */           currentAST.child = null;
/* 3097:2422 */           match(_t, 75);
/* 3098:2423 */           _t = _t.getFirstChild();
/* 3099:     */           for (;;)
/* 3100:     */           {
/* 3101:2427 */             if (_t == null) {
/* 3102:2427 */               _t = ASTNULL;
/* 3103:     */             }
/* 3104:2428 */             if (!_tokenSet_1.member(_t.getType())) {
/* 3105:     */               break;
/* 3106:     */             }
/* 3107:2429 */             exprOrSubquery(_t);
/* 3108:2430 */             _t = this._retTree;
/* 3109:2431 */             this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3110:     */           }
/* 3111:2439 */           currentAST = __currentAST162;
/* 3112:2440 */           _t = __t162;
/* 3113:2441 */           _t = _t.getNextSibling();
/* 3114:2442 */           break;
/* 3115:     */         case 3: 
/* 3116:     */           break;
/* 3117:     */         default: 
/* 3118:2450 */           throw new NoViableAltException(_t);
/* 3119:     */         }
/* 3120:2454 */         currentAST = __currentAST160;
/* 3121:2455 */         _t = __t160;
/* 3122:2456 */         _t = _t.getNextSibling();
/* 3123:2457 */         functionCall_AST = currentAST.root;
/* 3124:     */         
/* 3125:2459 */         processFunction(functionCall_AST, this.inSelect);
/* 3126:2460 */         this.inFunctionCall = false;
/* 3127:     */         
/* 3128:2462 */         functionCall_AST = currentAST.root;
/* 3129:2463 */         break;
/* 3130:     */       case 71: 
/* 3131:2467 */         AST __t165 = _t;
/* 3132:2468 */         AST tmp29_AST = null;
/* 3133:2469 */         AST tmp29_AST_in = null;
/* 3134:2470 */         tmp29_AST = this.astFactory.create(_t);
/* 3135:2471 */         tmp29_AST_in = _t;
/* 3136:2472 */         this.astFactory.addASTChild(currentAST, tmp29_AST);
/* 3137:2473 */         ASTPair __currentAST165 = currentAST.copy();
/* 3138:2474 */         currentAST.root = currentAST.child;
/* 3139:2475 */         currentAST.child = null;
/* 3140:2476 */         match(_t, 71);
/* 3141:2477 */         _t = _t.getFirstChild();
/* 3142:2478 */         aggregateExpr(_t);
/* 3143:2479 */         _t = this._retTree;
/* 3144:2480 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3145:2481 */         currentAST = __currentAST165;
/* 3146:2482 */         _t = __t165;
/* 3147:2483 */         _t = _t.getNextSibling();
/* 3148:2484 */         functionCall_AST = currentAST.root;
/* 3149:2485 */         break;
/* 3150:     */       default: 
/* 3151:2489 */         throw new NoViableAltException(_t);
/* 3152:     */       }
/* 3153:     */     }
/* 3154:     */     catch (RecognitionException ex)
/* 3155:     */     {
/* 3156:2494 */       reportError(ex);
/* 3157:2495 */       if (_t != null) {
/* 3158:2495 */         _t = _t.getNextSibling();
/* 3159:     */       }
/* 3160:     */     }
/* 3161:2497 */     this.returnAST = functionCall_AST;
/* 3162:2498 */     this._retTree = _t;
/* 3163:     */   }
/* 3164:     */   
/* 3165:     */   public final void count(AST _t)
/* 3166:     */     throws RecognitionException
/* 3167:     */   {
/* 3168:2503 */     AST count_AST_in = _t == ASTNULL ? null : _t;
/* 3169:2504 */     this.returnAST = null;
/* 3170:2505 */     ASTPair currentAST = new ASTPair();
/* 3171:2506 */     AST count_AST = null;
/* 3172:     */     try
/* 3173:     */     {
/* 3174:2509 */       AST __t60 = _t;
/* 3175:2510 */       AST tmp30_AST = null;
/* 3176:2511 */       AST tmp30_AST_in = null;
/* 3177:2512 */       tmp30_AST = this.astFactory.create(_t);
/* 3178:2513 */       tmp30_AST_in = _t;
/* 3179:2514 */       this.astFactory.addASTChild(currentAST, tmp30_AST);
/* 3180:2515 */       ASTPair __currentAST60 = currentAST.copy();
/* 3181:2516 */       currentAST.root = currentAST.child;
/* 3182:2517 */       currentAST.child = null;
/* 3183:2518 */       match(_t, 12);
/* 3184:2519 */       _t = _t.getFirstChild();
/* 3185:2521 */       if (_t == null) {
/* 3186:2521 */         _t = ASTNULL;
/* 3187:     */       }
/* 3188:2522 */       switch (_t.getType())
/* 3189:     */       {
/* 3190:     */       case 16: 
/* 3191:2525 */         AST tmp31_AST = null;
/* 3192:2526 */         AST tmp31_AST_in = null;
/* 3193:2527 */         tmp31_AST = this.astFactory.create(_t);
/* 3194:2528 */         tmp31_AST_in = _t;
/* 3195:2529 */         this.astFactory.addASTChild(currentAST, tmp31_AST);
/* 3196:2530 */         match(_t, 16);
/* 3197:2531 */         _t = _t.getNextSibling();
/* 3198:2532 */         break;
/* 3199:     */       case 4: 
/* 3200:2536 */         AST tmp32_AST = null;
/* 3201:2537 */         AST tmp32_AST_in = null;
/* 3202:2538 */         tmp32_AST = this.astFactory.create(_t);
/* 3203:2539 */         tmp32_AST_in = _t;
/* 3204:2540 */         this.astFactory.addASTChild(currentAST, tmp32_AST);
/* 3205:2541 */         match(_t, 4);
/* 3206:2542 */         _t = _t.getNextSibling();
/* 3207:2543 */         break;
/* 3208:     */       case 12: 
/* 3209:     */       case 15: 
/* 3210:     */       case 17: 
/* 3211:     */       case 20: 
/* 3212:     */       case 27: 
/* 3213:     */       case 39: 
/* 3214:     */       case 49: 
/* 3215:     */       case 54: 
/* 3216:     */       case 71: 
/* 3217:     */       case 74: 
/* 3218:     */       case 78: 
/* 3219:     */       case 81: 
/* 3220:     */       case 88: 
/* 3221:     */       case 90: 
/* 3222:     */       case 92: 
/* 3223:     */       case 93: 
/* 3224:     */       case 95: 
/* 3225:     */       case 96: 
/* 3226:     */       case 97: 
/* 3227:     */       case 98: 
/* 3228:     */       case 99: 
/* 3229:     */       case 100: 
/* 3230:     */       case 115: 
/* 3231:     */       case 116: 
/* 3232:     */       case 117: 
/* 3233:     */       case 118: 
/* 3234:     */       case 119: 
/* 3235:     */       case 122: 
/* 3236:     */       case 123: 
/* 3237:     */       case 124: 
/* 3238:     */       case 125: 
/* 3239:     */       case 126: 
/* 3240:     */         break;
/* 3241:     */       case 5: 
/* 3242:     */       case 6: 
/* 3243:     */       case 7: 
/* 3244:     */       case 8: 
/* 3245:     */       case 9: 
/* 3246:     */       case 10: 
/* 3247:     */       case 11: 
/* 3248:     */       case 13: 
/* 3249:     */       case 14: 
/* 3250:     */       case 18: 
/* 3251:     */       case 19: 
/* 3252:     */       case 21: 
/* 3253:     */       case 22: 
/* 3254:     */       case 23: 
/* 3255:     */       case 24: 
/* 3256:     */       case 25: 
/* 3257:     */       case 26: 
/* 3258:     */       case 28: 
/* 3259:     */       case 29: 
/* 3260:     */       case 30: 
/* 3261:     */       case 31: 
/* 3262:     */       case 32: 
/* 3263:     */       case 33: 
/* 3264:     */       case 34: 
/* 3265:     */       case 35: 
/* 3266:     */       case 36: 
/* 3267:     */       case 37: 
/* 3268:     */       case 38: 
/* 3269:     */       case 40: 
/* 3270:     */       case 41: 
/* 3271:     */       case 42: 
/* 3272:     */       case 43: 
/* 3273:     */       case 44: 
/* 3274:     */       case 45: 
/* 3275:     */       case 46: 
/* 3276:     */       case 47: 
/* 3277:     */       case 48: 
/* 3278:     */       case 50: 
/* 3279:     */       case 51: 
/* 3280:     */       case 52: 
/* 3281:     */       case 53: 
/* 3282:     */       case 55: 
/* 3283:     */       case 56: 
/* 3284:     */       case 57: 
/* 3285:     */       case 58: 
/* 3286:     */       case 59: 
/* 3287:     */       case 60: 
/* 3288:     */       case 61: 
/* 3289:     */       case 62: 
/* 3290:     */       case 63: 
/* 3291:     */       case 64: 
/* 3292:     */       case 65: 
/* 3293:     */       case 66: 
/* 3294:     */       case 67: 
/* 3295:     */       case 68: 
/* 3296:     */       case 69: 
/* 3297:     */       case 70: 
/* 3298:     */       case 72: 
/* 3299:     */       case 73: 
/* 3300:     */       case 75: 
/* 3301:     */       case 76: 
/* 3302:     */       case 77: 
/* 3303:     */       case 79: 
/* 3304:     */       case 80: 
/* 3305:     */       case 82: 
/* 3306:     */       case 83: 
/* 3307:     */       case 84: 
/* 3308:     */       case 85: 
/* 3309:     */       case 86: 
/* 3310:     */       case 87: 
/* 3311:     */       case 89: 
/* 3312:     */       case 91: 
/* 3313:     */       case 94: 
/* 3314:     */       case 101: 
/* 3315:     */       case 102: 
/* 3316:     */       case 103: 
/* 3317:     */       case 104: 
/* 3318:     */       case 105: 
/* 3319:     */       case 106: 
/* 3320:     */       case 107: 
/* 3321:     */       case 108: 
/* 3322:     */       case 109: 
/* 3323:     */       case 110: 
/* 3324:     */       case 111: 
/* 3325:     */       case 112: 
/* 3326:     */       case 113: 
/* 3327:     */       case 114: 
/* 3328:     */       case 120: 
/* 3329:     */       case 121: 
/* 3330:     */       default: 
/* 3331:2582 */         throw new NoViableAltException(_t);
/* 3332:     */       }
/* 3333:2587 */       if (_t == null) {
/* 3334:2587 */         _t = ASTNULL;
/* 3335:     */       }
/* 3336:2588 */       switch (_t.getType())
/* 3337:     */       {
/* 3338:     */       case 12: 
/* 3339:     */       case 15: 
/* 3340:     */       case 17: 
/* 3341:     */       case 20: 
/* 3342:     */       case 27: 
/* 3343:     */       case 39: 
/* 3344:     */       case 49: 
/* 3345:     */       case 54: 
/* 3346:     */       case 71: 
/* 3347:     */       case 74: 
/* 3348:     */       case 78: 
/* 3349:     */       case 81: 
/* 3350:     */       case 90: 
/* 3351:     */       case 92: 
/* 3352:     */       case 93: 
/* 3353:     */       case 95: 
/* 3354:     */       case 96: 
/* 3355:     */       case 97: 
/* 3356:     */       case 98: 
/* 3357:     */       case 99: 
/* 3358:     */       case 100: 
/* 3359:     */       case 115: 
/* 3360:     */       case 116: 
/* 3361:     */       case 117: 
/* 3362:     */       case 118: 
/* 3363:     */       case 119: 
/* 3364:     */       case 122: 
/* 3365:     */       case 123: 
/* 3366:     */       case 124: 
/* 3367:     */       case 125: 
/* 3368:     */       case 126: 
/* 3369:2621 */         aggregateExpr(_t);
/* 3370:2622 */         _t = this._retTree;
/* 3371:2623 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3372:2624 */         break;
/* 3373:     */       case 88: 
/* 3374:2628 */         AST tmp33_AST = null;
/* 3375:2629 */         AST tmp33_AST_in = null;
/* 3376:2630 */         tmp33_AST = this.astFactory.create(_t);
/* 3377:2631 */         tmp33_AST_in = _t;
/* 3378:2632 */         this.astFactory.addASTChild(currentAST, tmp33_AST);
/* 3379:2633 */         match(_t, 88);
/* 3380:2634 */         _t = _t.getNextSibling();
/* 3381:2635 */         break;
/* 3382:     */       case 13: 
/* 3383:     */       case 14: 
/* 3384:     */       case 16: 
/* 3385:     */       case 18: 
/* 3386:     */       case 19: 
/* 3387:     */       case 21: 
/* 3388:     */       case 22: 
/* 3389:     */       case 23: 
/* 3390:     */       case 24: 
/* 3391:     */       case 25: 
/* 3392:     */       case 26: 
/* 3393:     */       case 28: 
/* 3394:     */       case 29: 
/* 3395:     */       case 30: 
/* 3396:     */       case 31: 
/* 3397:     */       case 32: 
/* 3398:     */       case 33: 
/* 3399:     */       case 34: 
/* 3400:     */       case 35: 
/* 3401:     */       case 36: 
/* 3402:     */       case 37: 
/* 3403:     */       case 38: 
/* 3404:     */       case 40: 
/* 3405:     */       case 41: 
/* 3406:     */       case 42: 
/* 3407:     */       case 43: 
/* 3408:     */       case 44: 
/* 3409:     */       case 45: 
/* 3410:     */       case 46: 
/* 3411:     */       case 47: 
/* 3412:     */       case 48: 
/* 3413:     */       case 50: 
/* 3414:     */       case 51: 
/* 3415:     */       case 52: 
/* 3416:     */       case 53: 
/* 3417:     */       case 55: 
/* 3418:     */       case 56: 
/* 3419:     */       case 57: 
/* 3420:     */       case 58: 
/* 3421:     */       case 59: 
/* 3422:     */       case 60: 
/* 3423:     */       case 61: 
/* 3424:     */       case 62: 
/* 3425:     */       case 63: 
/* 3426:     */       case 64: 
/* 3427:     */       case 65: 
/* 3428:     */       case 66: 
/* 3429:     */       case 67: 
/* 3430:     */       case 68: 
/* 3431:     */       case 69: 
/* 3432:     */       case 70: 
/* 3433:     */       case 72: 
/* 3434:     */       case 73: 
/* 3435:     */       case 75: 
/* 3436:     */       case 76: 
/* 3437:     */       case 77: 
/* 3438:     */       case 79: 
/* 3439:     */       case 80: 
/* 3440:     */       case 82: 
/* 3441:     */       case 83: 
/* 3442:     */       case 84: 
/* 3443:     */       case 85: 
/* 3444:     */       case 86: 
/* 3445:     */       case 87: 
/* 3446:     */       case 89: 
/* 3447:     */       case 91: 
/* 3448:     */       case 94: 
/* 3449:     */       case 101: 
/* 3450:     */       case 102: 
/* 3451:     */       case 103: 
/* 3452:     */       case 104: 
/* 3453:     */       case 105: 
/* 3454:     */       case 106: 
/* 3455:     */       case 107: 
/* 3456:     */       case 108: 
/* 3457:     */       case 109: 
/* 3458:     */       case 110: 
/* 3459:     */       case 111: 
/* 3460:     */       case 112: 
/* 3461:     */       case 113: 
/* 3462:     */       case 114: 
/* 3463:     */       case 120: 
/* 3464:     */       case 121: 
/* 3465:     */       default: 
/* 3466:2639 */         throw new NoViableAltException(_t);
/* 3467:     */       }
/* 3468:2643 */       currentAST = __currentAST60;
/* 3469:2644 */       _t = __t60;
/* 3470:2645 */       _t = _t.getNextSibling();
/* 3471:2646 */       count_AST = currentAST.root;
/* 3472:     */     }
/* 3473:     */     catch (RecognitionException ex)
/* 3474:     */     {
/* 3475:2649 */       reportError(ex);
/* 3476:2650 */       if (_t != null) {
/* 3477:2650 */         _t = _t.getNextSibling();
/* 3478:     */       }
/* 3479:     */     }
/* 3480:2652 */     this.returnAST = count_AST;
/* 3481:2653 */     this._retTree = _t;
/* 3482:     */   }
/* 3483:     */   
/* 3484:     */   public final void collectionFunction(AST _t)
/* 3485:     */     throws RecognitionException
/* 3486:     */   {
/* 3487:2658 */     AST collectionFunction_AST_in = _t == ASTNULL ? null : _t;
/* 3488:2659 */     this.returnAST = null;
/* 3489:2660 */     ASTPair currentAST = new ASTPair();
/* 3490:2661 */     AST collectionFunction_AST = null;
/* 3491:2662 */     AST e = null;
/* 3492:2663 */     AST e_AST = null;
/* 3493:2664 */     AST p1_AST = null;
/* 3494:2665 */     AST p1 = null;
/* 3495:2666 */     AST i = null;
/* 3496:2667 */     AST i_AST = null;
/* 3497:2668 */     AST p2_AST = null;
/* 3498:2669 */     AST p2 = null;
/* 3499:     */     try
/* 3500:     */     {
/* 3501:2672 */       if (_t == null) {
/* 3502:2672 */         _t = ASTNULL;
/* 3503:     */       }
/* 3504:2673 */       switch (_t.getType())
/* 3505:     */       {
/* 3506:     */       case 17: 
/* 3507:2676 */         AST __t157 = _t;
/* 3508:2677 */         e = _t == ASTNULL ? null : _t;
/* 3509:2678 */         AST e_AST_in = null;
/* 3510:2679 */         e_AST = this.astFactory.create(e);
/* 3511:2680 */         this.astFactory.addASTChild(currentAST, e_AST);
/* 3512:2681 */         ASTPair __currentAST157 = currentAST.copy();
/* 3513:2682 */         currentAST.root = currentAST.child;
/* 3514:2683 */         currentAST.child = null;
/* 3515:2684 */         match(_t, 17);
/* 3516:2685 */         _t = _t.getFirstChild();
/* 3517:2686 */         this.inFunctionCall = true;
/* 3518:2687 */         p1 = _t == ASTNULL ? null : _t;
/* 3519:2688 */         propertyRef(_t);
/* 3520:2689 */         _t = this._retTree;
/* 3521:2690 */         p1_AST = this.returnAST;
/* 3522:2691 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3523:2692 */         resolve(p1_AST);
/* 3524:2693 */         currentAST = __currentAST157;
/* 3525:2694 */         _t = __t157;
/* 3526:2695 */         _t = _t.getNextSibling();
/* 3527:2696 */         processFunction(e_AST, this.inSelect);
/* 3528:2697 */         this.inFunctionCall = false;
/* 3529:2698 */         collectionFunction_AST = currentAST.root;
/* 3530:2699 */         break;
/* 3531:     */       case 27: 
/* 3532:2703 */         AST __t158 = _t;
/* 3533:2704 */         i = _t == ASTNULL ? null : _t;
/* 3534:2705 */         AST i_AST_in = null;
/* 3535:2706 */         i_AST = this.astFactory.create(i);
/* 3536:2707 */         this.astFactory.addASTChild(currentAST, i_AST);
/* 3537:2708 */         ASTPair __currentAST158 = currentAST.copy();
/* 3538:2709 */         currentAST.root = currentAST.child;
/* 3539:2710 */         currentAST.child = null;
/* 3540:2711 */         match(_t, 27);
/* 3541:2712 */         _t = _t.getFirstChild();
/* 3542:2713 */         this.inFunctionCall = true;
/* 3543:2714 */         p2 = _t == ASTNULL ? null : _t;
/* 3544:2715 */         propertyRef(_t);
/* 3545:2716 */         _t = this._retTree;
/* 3546:2717 */         p2_AST = this.returnAST;
/* 3547:2718 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3548:2719 */         resolve(p2_AST);
/* 3549:2720 */         currentAST = __currentAST158;
/* 3550:2721 */         _t = __t158;
/* 3551:2722 */         _t = _t.getNextSibling();
/* 3552:2723 */         processFunction(i_AST, this.inSelect);
/* 3553:2724 */         this.inFunctionCall = false;
/* 3554:2725 */         collectionFunction_AST = currentAST.root;
/* 3555:2726 */         break;
/* 3556:     */       default: 
/* 3557:2730 */         throw new NoViableAltException(_t);
/* 3558:     */       }
/* 3559:     */     }
/* 3560:     */     catch (RecognitionException ex)
/* 3561:     */     {
/* 3562:2735 */       reportError(ex);
/* 3563:2736 */       if (_t != null) {
/* 3564:2736 */         _t = _t.getNextSibling();
/* 3565:     */       }
/* 3566:     */     }
/* 3567:2738 */     this.returnAST = collectionFunction_AST;
/* 3568:2739 */     this._retTree = _t;
/* 3569:     */   }
/* 3570:     */   
/* 3571:     */   public final void literal(AST _t)
/* 3572:     */     throws RecognitionException
/* 3573:     */   {
/* 3574:2744 */     AST literal_AST_in = _t == ASTNULL ? null : _t;
/* 3575:2745 */     this.returnAST = null;
/* 3576:2746 */     ASTPair currentAST = new ASTPair();
/* 3577:2747 */     AST literal_AST = null;
/* 3578:     */     try
/* 3579:     */     {
/* 3580:2750 */       if (_t == null) {
/* 3581:2750 */         _t = ASTNULL;
/* 3582:     */       }
/* 3583:2751 */       switch (_t.getType())
/* 3584:     */       {
/* 3585:     */       case 124: 
/* 3586:2754 */         AST tmp34_AST = null;
/* 3587:2755 */         AST tmp34_AST_in = null;
/* 3588:2756 */         tmp34_AST = this.astFactory.create(_t);
/* 3589:2757 */         tmp34_AST_in = _t;
/* 3590:2758 */         this.astFactory.addASTChild(currentAST, tmp34_AST);
/* 3591:2759 */         match(_t, 124);
/* 3592:2760 */         _t = _t.getNextSibling();
/* 3593:2761 */         literal_AST = currentAST.root;
/* 3594:2762 */         processNumericLiteral(literal_AST);
/* 3595:2763 */         literal_AST = currentAST.root;
/* 3596:2764 */         break;
/* 3597:     */       case 97: 
/* 3598:2768 */         AST tmp35_AST = null;
/* 3599:2769 */         AST tmp35_AST_in = null;
/* 3600:2770 */         tmp35_AST = this.astFactory.create(_t);
/* 3601:2771 */         tmp35_AST_in = _t;
/* 3602:2772 */         this.astFactory.addASTChild(currentAST, tmp35_AST);
/* 3603:2773 */         match(_t, 97);
/* 3604:2774 */         _t = _t.getNextSibling();
/* 3605:2775 */         literal_AST = currentAST.root;
/* 3606:2776 */         processNumericLiteral(literal_AST);
/* 3607:2777 */         literal_AST = currentAST.root;
/* 3608:2778 */         break;
/* 3609:     */       case 96: 
/* 3610:2782 */         AST tmp36_AST = null;
/* 3611:2783 */         AST tmp36_AST_in = null;
/* 3612:2784 */         tmp36_AST = this.astFactory.create(_t);
/* 3613:2785 */         tmp36_AST_in = _t;
/* 3614:2786 */         this.astFactory.addASTChild(currentAST, tmp36_AST);
/* 3615:2787 */         match(_t, 96);
/* 3616:2788 */         _t = _t.getNextSibling();
/* 3617:2789 */         literal_AST = currentAST.root;
/* 3618:2790 */         processNumericLiteral(literal_AST);
/* 3619:2791 */         literal_AST = currentAST.root;
/* 3620:2792 */         break;
/* 3621:     */       case 95: 
/* 3622:2796 */         AST tmp37_AST = null;
/* 3623:2797 */         AST tmp37_AST_in = null;
/* 3624:2798 */         tmp37_AST = this.astFactory.create(_t);
/* 3625:2799 */         tmp37_AST_in = _t;
/* 3626:2800 */         this.astFactory.addASTChild(currentAST, tmp37_AST);
/* 3627:2801 */         match(_t, 95);
/* 3628:2802 */         _t = _t.getNextSibling();
/* 3629:2803 */         literal_AST = currentAST.root;
/* 3630:2804 */         processNumericLiteral(literal_AST);
/* 3631:2805 */         literal_AST = currentAST.root;
/* 3632:2806 */         break;
/* 3633:     */       case 98: 
/* 3634:2810 */         AST tmp38_AST = null;
/* 3635:2811 */         AST tmp38_AST_in = null;
/* 3636:2812 */         tmp38_AST = this.astFactory.create(_t);
/* 3637:2813 */         tmp38_AST_in = _t;
/* 3638:2814 */         this.astFactory.addASTChild(currentAST, tmp38_AST);
/* 3639:2815 */         match(_t, 98);
/* 3640:2816 */         _t = _t.getNextSibling();
/* 3641:2817 */         literal_AST = currentAST.root;
/* 3642:2818 */         processNumericLiteral(literal_AST);
/* 3643:2819 */         literal_AST = currentAST.root;
/* 3644:2820 */         break;
/* 3645:     */       case 99: 
/* 3646:2824 */         AST tmp39_AST = null;
/* 3647:2825 */         AST tmp39_AST_in = null;
/* 3648:2826 */         tmp39_AST = this.astFactory.create(_t);
/* 3649:2827 */         tmp39_AST_in = _t;
/* 3650:2828 */         this.astFactory.addASTChild(currentAST, tmp39_AST);
/* 3651:2829 */         match(_t, 99);
/* 3652:2830 */         _t = _t.getNextSibling();
/* 3653:2831 */         literal_AST = currentAST.root;
/* 3654:2832 */         processNumericLiteral(literal_AST);
/* 3655:2833 */         literal_AST = currentAST.root;
/* 3656:2834 */         break;
/* 3657:     */       case 125: 
/* 3658:2838 */         AST tmp40_AST = null;
/* 3659:2839 */         AST tmp40_AST_in = null;
/* 3660:2840 */         tmp40_AST = this.astFactory.create(_t);
/* 3661:2841 */         tmp40_AST_in = _t;
/* 3662:2842 */         this.astFactory.addASTChild(currentAST, tmp40_AST);
/* 3663:2843 */         match(_t, 125);
/* 3664:2844 */         _t = _t.getNextSibling();
/* 3665:2845 */         literal_AST = currentAST.root;
/* 3666:2846 */         break;
/* 3667:     */       default: 
/* 3668:2850 */         throw new NoViableAltException(_t);
/* 3669:     */       }
/* 3670:     */     }
/* 3671:     */     catch (RecognitionException ex)
/* 3672:     */     {
/* 3673:2855 */       reportError(ex);
/* 3674:2856 */       if (_t != null) {
/* 3675:2856 */         _t = _t.getNextSibling();
/* 3676:     */       }
/* 3677:     */     }
/* 3678:2858 */     this.returnAST = literal_AST;
/* 3679:2859 */     this._retTree = _t;
/* 3680:     */   }
/* 3681:     */   
/* 3682:     */   public final void arithmeticExpr(AST _t)
/* 3683:     */     throws RecognitionException
/* 3684:     */   {
/* 3685:2864 */     AST arithmeticExpr_AST_in = _t == ASTNULL ? null : _t;
/* 3686:2865 */     this.returnAST = null;
/* 3687:2866 */     ASTPair currentAST = new ASTPair();
/* 3688:2867 */     AST arithmeticExpr_AST = null;
/* 3689:     */     try
/* 3690:     */     {
/* 3691:2870 */       if (_t == null) {
/* 3692:2870 */         _t = ASTNULL;
/* 3693:     */       }
/* 3694:2871 */       switch (_t.getType())
/* 3695:     */       {
/* 3696:     */       case 115: 
/* 3697:2874 */         AST __t137 = _t;
/* 3698:2875 */         AST tmp41_AST = null;
/* 3699:2876 */         AST tmp41_AST_in = null;
/* 3700:2877 */         tmp41_AST = this.astFactory.create(_t);
/* 3701:2878 */         tmp41_AST_in = _t;
/* 3702:2879 */         this.astFactory.addASTChild(currentAST, tmp41_AST);
/* 3703:2880 */         ASTPair __currentAST137 = currentAST.copy();
/* 3704:2881 */         currentAST.root = currentAST.child;
/* 3705:2882 */         currentAST.child = null;
/* 3706:2883 */         match(_t, 115);
/* 3707:2884 */         _t = _t.getFirstChild();
/* 3708:2885 */         exprOrSubquery(_t);
/* 3709:2886 */         _t = this._retTree;
/* 3710:2887 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3711:2888 */         exprOrSubquery(_t);
/* 3712:2889 */         _t = this._retTree;
/* 3713:2890 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3714:2891 */         currentAST = __currentAST137;
/* 3715:2892 */         _t = __t137;
/* 3716:2893 */         _t = _t.getNextSibling();
/* 3717:2894 */         arithmeticExpr_AST = currentAST.root;
/* 3718:2895 */         prepareArithmeticOperator(arithmeticExpr_AST);
/* 3719:2896 */         arithmeticExpr_AST = currentAST.root;
/* 3720:2897 */         break;
/* 3721:     */       case 116: 
/* 3722:2901 */         AST __t138 = _t;
/* 3723:2902 */         AST tmp42_AST = null;
/* 3724:2903 */         AST tmp42_AST_in = null;
/* 3725:2904 */         tmp42_AST = this.astFactory.create(_t);
/* 3726:2905 */         tmp42_AST_in = _t;
/* 3727:2906 */         this.astFactory.addASTChild(currentAST, tmp42_AST);
/* 3728:2907 */         ASTPair __currentAST138 = currentAST.copy();
/* 3729:2908 */         currentAST.root = currentAST.child;
/* 3730:2909 */         currentAST.child = null;
/* 3731:2910 */         match(_t, 116);
/* 3732:2911 */         _t = _t.getFirstChild();
/* 3733:2912 */         exprOrSubquery(_t);
/* 3734:2913 */         _t = this._retTree;
/* 3735:2914 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3736:2915 */         exprOrSubquery(_t);
/* 3737:2916 */         _t = this._retTree;
/* 3738:2917 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3739:2918 */         currentAST = __currentAST138;
/* 3740:2919 */         _t = __t138;
/* 3741:2920 */         _t = _t.getNextSibling();
/* 3742:2921 */         arithmeticExpr_AST = currentAST.root;
/* 3743:2922 */         prepareArithmeticOperator(arithmeticExpr_AST);
/* 3744:2923 */         arithmeticExpr_AST = currentAST.root;
/* 3745:2924 */         break;
/* 3746:     */       case 118: 
/* 3747:2928 */         AST __t139 = _t;
/* 3748:2929 */         AST tmp43_AST = null;
/* 3749:2930 */         AST tmp43_AST_in = null;
/* 3750:2931 */         tmp43_AST = this.astFactory.create(_t);
/* 3751:2932 */         tmp43_AST_in = _t;
/* 3752:2933 */         this.astFactory.addASTChild(currentAST, tmp43_AST);
/* 3753:2934 */         ASTPair __currentAST139 = currentAST.copy();
/* 3754:2935 */         currentAST.root = currentAST.child;
/* 3755:2936 */         currentAST.child = null;
/* 3756:2937 */         match(_t, 118);
/* 3757:2938 */         _t = _t.getFirstChild();
/* 3758:2939 */         exprOrSubquery(_t);
/* 3759:2940 */         _t = this._retTree;
/* 3760:2941 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3761:2942 */         exprOrSubquery(_t);
/* 3762:2943 */         _t = this._retTree;
/* 3763:2944 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3764:2945 */         currentAST = __currentAST139;
/* 3765:2946 */         _t = __t139;
/* 3766:2947 */         _t = _t.getNextSibling();
/* 3767:2948 */         arithmeticExpr_AST = currentAST.root;
/* 3768:2949 */         prepareArithmeticOperator(arithmeticExpr_AST);
/* 3769:2950 */         arithmeticExpr_AST = currentAST.root;
/* 3770:2951 */         break;
/* 3771:     */       case 119: 
/* 3772:2955 */         AST __t140 = _t;
/* 3773:2956 */         AST tmp44_AST = null;
/* 3774:2957 */         AST tmp44_AST_in = null;
/* 3775:2958 */         tmp44_AST = this.astFactory.create(_t);
/* 3776:2959 */         tmp44_AST_in = _t;
/* 3777:2960 */         this.astFactory.addASTChild(currentAST, tmp44_AST);
/* 3778:2961 */         ASTPair __currentAST140 = currentAST.copy();
/* 3779:2962 */         currentAST.root = currentAST.child;
/* 3780:2963 */         currentAST.child = null;
/* 3781:2964 */         match(_t, 119);
/* 3782:2965 */         _t = _t.getFirstChild();
/* 3783:2966 */         exprOrSubquery(_t);
/* 3784:2967 */         _t = this._retTree;
/* 3785:2968 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3786:2969 */         exprOrSubquery(_t);
/* 3787:2970 */         _t = this._retTree;
/* 3788:2971 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3789:2972 */         currentAST = __currentAST140;
/* 3790:2973 */         _t = __t140;
/* 3791:2974 */         _t = _t.getNextSibling();
/* 3792:2975 */         arithmeticExpr_AST = currentAST.root;
/* 3793:2976 */         prepareArithmeticOperator(arithmeticExpr_AST);
/* 3794:2977 */         arithmeticExpr_AST = currentAST.root;
/* 3795:2978 */         break;
/* 3796:     */       case 117: 
/* 3797:2982 */         AST __t141 = _t;
/* 3798:2983 */         AST tmp45_AST = null;
/* 3799:2984 */         AST tmp45_AST_in = null;
/* 3800:2985 */         tmp45_AST = this.astFactory.create(_t);
/* 3801:2986 */         tmp45_AST_in = _t;
/* 3802:2987 */         this.astFactory.addASTChild(currentAST, tmp45_AST);
/* 3803:2988 */         ASTPair __currentAST141 = currentAST.copy();
/* 3804:2989 */         currentAST.root = currentAST.child;
/* 3805:2990 */         currentAST.child = null;
/* 3806:2991 */         match(_t, 117);
/* 3807:2992 */         _t = _t.getFirstChild();
/* 3808:2993 */         exprOrSubquery(_t);
/* 3809:2994 */         _t = this._retTree;
/* 3810:2995 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3811:2996 */         exprOrSubquery(_t);
/* 3812:2997 */         _t = this._retTree;
/* 3813:2998 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3814:2999 */         currentAST = __currentAST141;
/* 3815:3000 */         _t = __t141;
/* 3816:3001 */         _t = _t.getNextSibling();
/* 3817:3002 */         arithmeticExpr_AST = currentAST.root;
/* 3818:3003 */         prepareArithmeticOperator(arithmeticExpr_AST);
/* 3819:3004 */         arithmeticExpr_AST = currentAST.root;
/* 3820:3005 */         break;
/* 3821:     */       case 90: 
/* 3822:3009 */         AST __t142 = _t;
/* 3823:3010 */         AST tmp46_AST = null;
/* 3824:3011 */         AST tmp46_AST_in = null;
/* 3825:3012 */         tmp46_AST = this.astFactory.create(_t);
/* 3826:3013 */         tmp46_AST_in = _t;
/* 3827:3014 */         this.astFactory.addASTChild(currentAST, tmp46_AST);
/* 3828:3015 */         ASTPair __currentAST142 = currentAST.copy();
/* 3829:3016 */         currentAST.root = currentAST.child;
/* 3830:3017 */         currentAST.child = null;
/* 3831:3018 */         match(_t, 90);
/* 3832:3019 */         _t = _t.getFirstChild();
/* 3833:3020 */         expr(_t);
/* 3834:3021 */         _t = this._retTree;
/* 3835:3022 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3836:3023 */         currentAST = __currentAST142;
/* 3837:3024 */         _t = __t142;
/* 3838:3025 */         _t = _t.getNextSibling();
/* 3839:3026 */         arithmeticExpr_AST = currentAST.root;
/* 3840:3027 */         prepareArithmeticOperator(arithmeticExpr_AST);
/* 3841:3028 */         arithmeticExpr_AST = currentAST.root;
/* 3842:3029 */         break;
/* 3843:     */       case 54: 
/* 3844:     */       case 74: 
/* 3845:3034 */         caseExpr(_t);
/* 3846:3035 */         _t = this._retTree;
/* 3847:3036 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3848:3037 */         arithmeticExpr_AST = currentAST.root;
/* 3849:3038 */         break;
/* 3850:     */       default: 
/* 3851:3042 */         throw new NoViableAltException(_t);
/* 3852:     */       }
/* 3853:     */     }
/* 3854:     */     catch (RecognitionException ex)
/* 3855:     */     {
/* 3856:3047 */       reportError(ex);
/* 3857:3048 */       if (_t != null) {
/* 3858:3048 */         _t = _t.getNextSibling();
/* 3859:     */       }
/* 3860:     */     }
/* 3861:3050 */     this.returnAST = arithmeticExpr_AST;
/* 3862:3051 */     this._retTree = _t;
/* 3863:     */   }
/* 3864:     */   
/* 3865:     */   public final void aggregateExpr(AST _t)
/* 3866:     */     throws RecognitionException
/* 3867:     */   {
/* 3868:3056 */     AST aggregateExpr_AST_in = _t == ASTNULL ? null : _t;
/* 3869:3057 */     this.returnAST = null;
/* 3870:3058 */     ASTPair currentAST = new ASTPair();
/* 3871:3059 */     AST aggregateExpr_AST = null;
/* 3872:     */     try
/* 3873:     */     {
/* 3874:3062 */       if (_t == null) {
/* 3875:3062 */         _t = ASTNULL;
/* 3876:     */       }
/* 3877:3063 */       switch (_t.getType())
/* 3878:     */       {
/* 3879:     */       case 12: 
/* 3880:     */       case 15: 
/* 3881:     */       case 20: 
/* 3882:     */       case 39: 
/* 3883:     */       case 49: 
/* 3884:     */       case 54: 
/* 3885:     */       case 71: 
/* 3886:     */       case 74: 
/* 3887:     */       case 78: 
/* 3888:     */       case 81: 
/* 3889:     */       case 90: 
/* 3890:     */       case 92: 
/* 3891:     */       case 93: 
/* 3892:     */       case 95: 
/* 3893:     */       case 96: 
/* 3894:     */       case 97: 
/* 3895:     */       case 98: 
/* 3896:     */       case 99: 
/* 3897:     */       case 100: 
/* 3898:     */       case 115: 
/* 3899:     */       case 116: 
/* 3900:     */       case 117: 
/* 3901:     */       case 118: 
/* 3902:     */       case 119: 
/* 3903:     */       case 122: 
/* 3904:     */       case 123: 
/* 3905:     */       case 124: 
/* 3906:     */       case 125: 
/* 3907:     */       case 126: 
/* 3908:3094 */         expr(_t);
/* 3909:3095 */         _t = this._retTree;
/* 3910:3096 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3911:3097 */         aggregateExpr_AST = currentAST.root;
/* 3912:3098 */         break;
/* 3913:     */       case 17: 
/* 3914:     */       case 27: 
/* 3915:3103 */         collectionFunction(_t);
/* 3916:3104 */         _t = this._retTree;
/* 3917:3105 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3918:3106 */         aggregateExpr_AST = currentAST.root;
/* 3919:3107 */         break;
/* 3920:     */       case 13: 
/* 3921:     */       case 14: 
/* 3922:     */       case 16: 
/* 3923:     */       case 18: 
/* 3924:     */       case 19: 
/* 3925:     */       case 21: 
/* 3926:     */       case 22: 
/* 3927:     */       case 23: 
/* 3928:     */       case 24: 
/* 3929:     */       case 25: 
/* 3930:     */       case 26: 
/* 3931:     */       case 28: 
/* 3932:     */       case 29: 
/* 3933:     */       case 30: 
/* 3934:     */       case 31: 
/* 3935:     */       case 32: 
/* 3936:     */       case 33: 
/* 3937:     */       case 34: 
/* 3938:     */       case 35: 
/* 3939:     */       case 36: 
/* 3940:     */       case 37: 
/* 3941:     */       case 38: 
/* 3942:     */       case 40: 
/* 3943:     */       case 41: 
/* 3944:     */       case 42: 
/* 3945:     */       case 43: 
/* 3946:     */       case 44: 
/* 3947:     */       case 45: 
/* 3948:     */       case 46: 
/* 3949:     */       case 47: 
/* 3950:     */       case 48: 
/* 3951:     */       case 50: 
/* 3952:     */       case 51: 
/* 3953:     */       case 52: 
/* 3954:     */       case 53: 
/* 3955:     */       case 55: 
/* 3956:     */       case 56: 
/* 3957:     */       case 57: 
/* 3958:     */       case 58: 
/* 3959:     */       case 59: 
/* 3960:     */       case 60: 
/* 3961:     */       case 61: 
/* 3962:     */       case 62: 
/* 3963:     */       case 63: 
/* 3964:     */       case 64: 
/* 3965:     */       case 65: 
/* 3966:     */       case 66: 
/* 3967:     */       case 67: 
/* 3968:     */       case 68: 
/* 3969:     */       case 69: 
/* 3970:     */       case 70: 
/* 3971:     */       case 72: 
/* 3972:     */       case 73: 
/* 3973:     */       case 75: 
/* 3974:     */       case 76: 
/* 3975:     */       case 77: 
/* 3976:     */       case 79: 
/* 3977:     */       case 80: 
/* 3978:     */       case 82: 
/* 3979:     */       case 83: 
/* 3980:     */       case 84: 
/* 3981:     */       case 85: 
/* 3982:     */       case 86: 
/* 3983:     */       case 87: 
/* 3984:     */       case 88: 
/* 3985:     */       case 89: 
/* 3986:     */       case 91: 
/* 3987:     */       case 94: 
/* 3988:     */       case 101: 
/* 3989:     */       case 102: 
/* 3990:     */       case 103: 
/* 3991:     */       case 104: 
/* 3992:     */       case 105: 
/* 3993:     */       case 106: 
/* 3994:     */       case 107: 
/* 3995:     */       case 108: 
/* 3996:     */       case 109: 
/* 3997:     */       case 110: 
/* 3998:     */       case 111: 
/* 3999:     */       case 112: 
/* 4000:     */       case 113: 
/* 4001:     */       case 114: 
/* 4002:     */       case 120: 
/* 4003:     */       case 121: 
/* 4004:     */       default: 
/* 4005:3111 */         throw new NoViableAltException(_t);
/* 4006:     */       }
/* 4007:     */     }
/* 4008:     */     catch (RecognitionException ex)
/* 4009:     */     {
/* 4010:3116 */       reportError(ex);
/* 4011:3117 */       if (_t != null) {
/* 4012:3117 */         _t = _t.getNextSibling();
/* 4013:     */       }
/* 4014:     */     }
/* 4015:3119 */     this.returnAST = aggregateExpr_AST;
/* 4016:3120 */     this._retTree = _t;
/* 4017:     */   }
/* 4018:     */   
/* 4019:     */   public final void fromElementList(AST _t)
/* 4020:     */     throws RecognitionException
/* 4021:     */   {
/* 4022:3125 */     AST fromElementList_AST_in = _t == ASTNULL ? null : _t;
/* 4023:3126 */     this.returnAST = null;
/* 4024:3127 */     ASTPair currentAST = new ASTPair();
/* 4025:3128 */     AST fromElementList_AST = null;
/* 4026:     */     
/* 4027:3130 */     boolean oldInFrom = this.inFrom;
/* 4028:3131 */     this.inFrom = true;
/* 4029:     */     try
/* 4030:     */     {
/* 4031:3136 */       int _cnt72 = 0;
/* 4032:     */       for (;;)
/* 4033:     */       {
/* 4034:3139 */         if (_t == null) {
/* 4035:3139 */           _t = ASTNULL;
/* 4036:     */         }
/* 4037:3140 */         if ((_t.getType() == 32) || (_t.getType() == 76) || (_t.getType() == 87))
/* 4038:     */         {
/* 4039:3141 */           fromElement(_t);
/* 4040:3142 */           _t = this._retTree;
/* 4041:3143 */           this.astFactory.addASTChild(currentAST, this.returnAST);
/* 4042:     */         }
/* 4043:     */         else
/* 4044:     */         {
/* 4045:3146 */           if (_cnt72 >= 1) {
/* 4046:     */             break;
/* 4047:     */           }
/* 4048:3146 */           throw new NoViableAltException(_t);
/* 4049:     */         }
/* 4050:3149 */         _cnt72++;
/* 4051:     */       }
/* 4052:3153 */       this.inFrom = oldInFrom;
/* 4053:     */       
/* 4054:3155 */       fromElementList_AST = currentAST.root;
/* 4055:     */     }
/* 4056:     */     catch (RecognitionException ex)
/* 4057:     */     {
/* 4058:3158 */       reportError(ex);
/* 4059:3159 */       if (_t != null) {
/* 4060:3159 */         _t = _t.getNextSibling();
/* 4061:     */       }
/* 4062:     */     }
/* 4063:3161 */     this.returnAST = fromElementList_AST;
/* 4064:3162 */     this._retTree = _t;
/* 4065:     */   }
/* 4066:     */   
/* 4067:     */   public final void fromElement(AST _t)
/* 4068:     */     throws RecognitionException
/* 4069:     */   {
/* 4070:3167 */     AST fromElement_AST_in = _t == ASTNULL ? null : _t;
/* 4071:3168 */     this.returnAST = null;
/* 4072:3169 */     ASTPair currentAST = new ASTPair();
/* 4073:3170 */     AST fromElement_AST = null;
/* 4074:3171 */     AST a = null;
/* 4075:3172 */     AST a_AST = null;
/* 4076:3173 */     AST pf = null;
/* 4077:3174 */     AST pf_AST = null;
/* 4078:3175 */     AST je_AST = null;
/* 4079:3176 */     AST je = null;
/* 4080:3177 */     AST fe = null;
/* 4081:3178 */     AST fe_AST = null;
/* 4082:3179 */     AST a3 = null;
/* 4083:3180 */     AST a3_AST = null;
/* 4084:     */     
/* 4085:3182 */     String p = null;
/* 4086:     */     try
/* 4087:     */     {
/* 4088:3186 */       if (_t == null) {
/* 4089:3186 */         _t = ASTNULL;
/* 4090:     */       }
/* 4091:3187 */       switch (_t.getType())
/* 4092:     */       {
/* 4093:     */       case 87: 
/* 4094:3190 */         AST __t74 = _t;
/* 4095:3191 */         AST tmp47_AST = null;
/* 4096:3192 */         AST tmp47_AST_in = null;
/* 4097:3193 */         tmp47_AST = this.astFactory.create(_t);
/* 4098:3194 */         tmp47_AST_in = _t;
/* 4099:3195 */         ASTPair __currentAST74 = currentAST.copy();
/* 4100:3196 */         currentAST.root = currentAST.child;
/* 4101:3197 */         currentAST.child = null;
/* 4102:3198 */         match(_t, 87);
/* 4103:3199 */         _t = _t.getFirstChild();
/* 4104:3200 */         p = path(_t);
/* 4105:3201 */         _t = this._retTree;
/* 4106:3203 */         if (_t == null) {
/* 4107:3203 */           _t = ASTNULL;
/* 4108:     */         }
/* 4109:3204 */         switch (_t.getType())
/* 4110:     */         {
/* 4111:     */         case 72: 
/* 4112:3207 */           a = _t;
/* 4113:3208 */           AST a_AST_in = null;
/* 4114:3209 */           a_AST = this.astFactory.create(a);
/* 4115:3210 */           match(_t, 72);
/* 4116:3211 */           _t = _t.getNextSibling();
/* 4117:3212 */           break;
/* 4118:     */         case 3: 
/* 4119:     */         case 21: 
/* 4120:     */           break;
/* 4121:     */         default: 
/* 4122:3221 */           throw new NoViableAltException(_t);
/* 4123:     */         }
/* 4124:3226 */         if (_t == null) {
/* 4125:3226 */           _t = ASTNULL;
/* 4126:     */         }
/* 4127:3227 */         switch (_t.getType())
/* 4128:     */         {
/* 4129:     */         case 21: 
/* 4130:3230 */           pf = _t;
/* 4131:3231 */           AST pf_AST_in = null;
/* 4132:3232 */           pf_AST = this.astFactory.create(pf);
/* 4133:3233 */           match(_t, 21);
/* 4134:3234 */           _t = _t.getNextSibling();
/* 4135:3235 */           break;
/* 4136:     */         case 3: 
/* 4137:     */           break;
/* 4138:     */         default: 
/* 4139:3243 */           throw new NoViableAltException(_t);
/* 4140:     */         }
/* 4141:3247 */         currentAST = __currentAST74;
/* 4142:3248 */         _t = __t74;
/* 4143:3249 */         _t = _t.getNextSibling();
/* 4144:3250 */         fromElement_AST = currentAST.root;
/* 4145:     */         
/* 4146:3252 */         fromElement_AST = createFromElement(p, a, pf);
/* 4147:     */         
/* 4148:3254 */         currentAST.root = fromElement_AST;
/* 4149:3255 */         currentAST.child = ((fromElement_AST != null) && (fromElement_AST.getFirstChild() != null) ? fromElement_AST.getFirstChild() : fromElement_AST);
/* 4150:     */         
/* 4151:3257 */         currentAST.advanceChildToEnd();
/* 4152:3258 */         break;
/* 4153:     */       case 32: 
/* 4154:3262 */         je = _t == ASTNULL ? null : _t;
/* 4155:3263 */         joinElement(_t);
/* 4156:3264 */         _t = this._retTree;
/* 4157:3265 */         je_AST = this.returnAST;
/* 4158:3266 */         fromElement_AST = currentAST.root;
/* 4159:     */         
/* 4160:3268 */         fromElement_AST = je_AST;
/* 4161:     */         
/* 4162:3270 */         currentAST.root = fromElement_AST;
/* 4163:3271 */         currentAST.child = ((fromElement_AST != null) && (fromElement_AST.getFirstChild() != null) ? fromElement_AST.getFirstChild() : fromElement_AST);
/* 4164:     */         
/* 4165:3273 */         currentAST.advanceChildToEnd();
/* 4166:3274 */         break;
/* 4167:     */       case 76: 
/* 4168:3278 */         fe = _t;
/* 4169:3279 */         AST fe_AST_in = null;
/* 4170:3280 */         fe_AST = this.astFactory.create(fe);
/* 4171:3281 */         match(_t, 76);
/* 4172:3282 */         _t = _t.getNextSibling();
/* 4173:3283 */         a3 = _t;
/* 4174:3284 */         AST a3_AST_in = null;
/* 4175:3285 */         a3_AST = this.astFactory.create(a3);
/* 4176:3286 */         match(_t, 72);
/* 4177:3287 */         _t = _t.getNextSibling();
/* 4178:3288 */         fromElement_AST = currentAST.root;
/* 4179:     */         
/* 4180:3290 */         fromElement_AST = createFromFilterElement(fe, a3);
/* 4181:     */         
/* 4182:3292 */         currentAST.root = fromElement_AST;
/* 4183:3293 */         currentAST.child = ((fromElement_AST != null) && (fromElement_AST.getFirstChild() != null) ? fromElement_AST.getFirstChild() : fromElement_AST);
/* 4184:     */         
/* 4185:3295 */         currentAST.advanceChildToEnd();
/* 4186:3296 */         break;
/* 4187:     */       default: 
/* 4188:3300 */         throw new NoViableAltException(_t);
/* 4189:     */       }
/* 4190:     */     }
/* 4191:     */     catch (RecognitionException ex)
/* 4192:     */     {
/* 4193:3305 */       reportError(ex);
/* 4194:3306 */       if (_t != null) {
/* 4195:3306 */         _t = _t.getNextSibling();
/* 4196:     */       }
/* 4197:     */     }
/* 4198:3308 */     this.returnAST = fromElement_AST;
/* 4199:3309 */     this._retTree = _t;
/* 4200:     */   }
/* 4201:     */   
/* 4202:     */   public final void joinElement(AST _t)
/* 4203:     */     throws RecognitionException
/* 4204:     */   {
/* 4205:3314 */     AST joinElement_AST_in = _t == ASTNULL ? null : _t;
/* 4206:3315 */     this.returnAST = null;
/* 4207:3316 */     ASTPair currentAST = new ASTPair();
/* 4208:3317 */     AST joinElement_AST = null;
/* 4209:3318 */     AST f = null;
/* 4210:3319 */     AST f_AST = null;
/* 4211:3320 */     AST ref_AST = null;
/* 4212:3321 */     AST ref = null;
/* 4213:3322 */     AST a = null;
/* 4214:3323 */     AST a_AST = null;
/* 4215:3324 */     AST pf = null;
/* 4216:3325 */     AST pf_AST = null;
/* 4217:3326 */     AST with = null;
/* 4218:3327 */     AST with_AST = null;
/* 4219:     */     
/* 4220:3329 */     int j = 28;
/* 4221:     */     try
/* 4222:     */     {
/* 4223:3333 */       AST __t78 = _t;
/* 4224:3334 */       AST tmp48_AST = null;
/* 4225:3335 */       AST tmp48_AST_in = null;
/* 4226:3336 */       tmp48_AST = this.astFactory.create(_t);
/* 4227:3337 */       tmp48_AST_in = _t;
/* 4228:3338 */       ASTPair __currentAST78 = currentAST.copy();
/* 4229:3339 */       currentAST.root = currentAST.child;
/* 4230:3340 */       currentAST.child = null;
/* 4231:3341 */       match(_t, 32);
/* 4232:3342 */       _t = _t.getFirstChild();
/* 4233:3344 */       if (_t == null) {
/* 4234:3344 */         _t = ASTNULL;
/* 4235:     */       }
/* 4236:3345 */       switch (_t.getType())
/* 4237:     */       {
/* 4238:     */       case 23: 
/* 4239:     */       case 28: 
/* 4240:     */       case 33: 
/* 4241:     */       case 44: 
/* 4242:3351 */         j = joinType(_t);
/* 4243:3352 */         _t = this._retTree;
/* 4244:3353 */         setImpliedJoinType(j);
/* 4245:3354 */         break;
/* 4246:     */       case 15: 
/* 4247:     */       case 21: 
/* 4248:     */       case 68: 
/* 4249:     */       case 69: 
/* 4250:     */       case 70: 
/* 4251:     */       case 93: 
/* 4252:     */       case 126: 
/* 4253:     */         break;
/* 4254:     */       default: 
/* 4255:3368 */         throw new NoViableAltException(_t);
/* 4256:     */       }
/* 4257:3373 */       if (_t == null) {
/* 4258:3373 */         _t = ASTNULL;
/* 4259:     */       }
/* 4260:3374 */       switch (_t.getType())
/* 4261:     */       {
/* 4262:     */       case 21: 
/* 4263:3377 */         f = _t;
/* 4264:3378 */         AST f_AST_in = null;
/* 4265:3379 */         f_AST = this.astFactory.create(f);
/* 4266:3380 */         match(_t, 21);
/* 4267:3381 */         _t = _t.getNextSibling();
/* 4268:3382 */         break;
/* 4269:     */       case 15: 
/* 4270:     */       case 68: 
/* 4271:     */       case 69: 
/* 4272:     */       case 70: 
/* 4273:     */       case 93: 
/* 4274:     */       case 126: 
/* 4275:     */         break;
/* 4276:     */       default: 
/* 4277:3395 */         throw new NoViableAltException(_t);
/* 4278:     */       }
/* 4279:3399 */       ref = _t == ASTNULL ? null : _t;
/* 4280:3400 */       propertyRef(_t);
/* 4281:3401 */       _t = this._retTree;
/* 4282:3402 */       ref_AST = this.returnAST;
/* 4283:3404 */       if (_t == null) {
/* 4284:3404 */         _t = ASTNULL;
/* 4285:     */       }
/* 4286:3405 */       switch (_t.getType())
/* 4287:     */       {
/* 4288:     */       case 72: 
/* 4289:3408 */         a = _t;
/* 4290:3409 */         AST a_AST_in = null;
/* 4291:3410 */         a_AST = this.astFactory.create(a);
/* 4292:3411 */         match(_t, 72);
/* 4293:3412 */         _t = _t.getNextSibling();
/* 4294:3413 */         break;
/* 4295:     */       case 3: 
/* 4296:     */       case 21: 
/* 4297:     */       case 60: 
/* 4298:     */         break;
/* 4299:     */       default: 
/* 4300:3423 */         throw new NoViableAltException(_t);
/* 4301:     */       }
/* 4302:3428 */       if (_t == null) {
/* 4303:3428 */         _t = ASTNULL;
/* 4304:     */       }
/* 4305:3429 */       switch (_t.getType())
/* 4306:     */       {
/* 4307:     */       case 21: 
/* 4308:3432 */         pf = _t;
/* 4309:3433 */         AST pf_AST_in = null;
/* 4310:3434 */         pf_AST = this.astFactory.create(pf);
/* 4311:3435 */         match(_t, 21);
/* 4312:3436 */         _t = _t.getNextSibling();
/* 4313:3437 */         break;
/* 4314:     */       case 3: 
/* 4315:     */       case 60: 
/* 4316:     */         break;
/* 4317:     */       default: 
/* 4318:3446 */         throw new NoViableAltException(_t);
/* 4319:     */       }
/* 4320:3451 */       if (_t == null) {
/* 4321:3451 */         _t = ASTNULL;
/* 4322:     */       }
/* 4323:3452 */       switch (_t.getType())
/* 4324:     */       {
/* 4325:     */       case 60: 
/* 4326:3455 */         with = _t;
/* 4327:3456 */         AST with_AST_in = null;
/* 4328:3457 */         with_AST = this.astFactory.create(with);
/* 4329:3458 */         match(_t, 60);
/* 4330:3459 */         _t = _t.getNextSibling();
/* 4331:3460 */         break;
/* 4332:     */       case 3: 
/* 4333:     */         break;
/* 4334:     */       default: 
/* 4335:3468 */         throw new NoViableAltException(_t);
/* 4336:     */       }
/* 4337:3472 */       currentAST = __currentAST78;
/* 4338:3473 */       _t = __t78;
/* 4339:3474 */       _t = _t.getNextSibling();
/* 4340:     */       
/* 4341:     */ 
/* 4342:3477 */       createFromJoinElement(ref_AST, a, j, f, pf, with);
/* 4343:3478 */       setImpliedJoinType(28);
/* 4344:     */     }
/* 4345:     */     catch (RecognitionException ex)
/* 4346:     */     {
/* 4347:3482 */       reportError(ex);
/* 4348:3483 */       if (_t != null) {
/* 4349:3483 */         _t = _t.getNextSibling();
/* 4350:     */       }
/* 4351:     */     }
/* 4352:3485 */     this.returnAST = joinElement_AST;
/* 4353:3486 */     this._retTree = _t;
/* 4354:     */   }
/* 4355:     */   
/* 4356:     */   public final int joinType(AST _t)
/* 4357:     */     throws RecognitionException
/* 4358:     */   {
/* 4359:3492 */     AST joinType_AST_in = _t == ASTNULL ? null : _t;
/* 4360:3493 */     this.returnAST = null;
/* 4361:3494 */     ASTPair currentAST = new ASTPair();
/* 4362:3495 */     AST joinType_AST = null;
/* 4363:3496 */     AST left = null;
/* 4364:3497 */     AST left_AST = null;
/* 4365:3498 */     AST right = null;
/* 4366:3499 */     AST right_AST = null;
/* 4367:3500 */     AST outer = null;
/* 4368:3501 */     AST outer_AST = null;
/* 4369:     */     
/* 4370:3503 */     int j = 28;
/* 4371:     */     try
/* 4372:     */     {
/* 4373:3507 */       if (_t == null) {
/* 4374:3507 */         _t = ASTNULL;
/* 4375:     */       }
/* 4376:3508 */       switch (_t.getType())
/* 4377:     */       {
/* 4378:     */       case 33: 
/* 4379:     */       case 44: 
/* 4380:3514 */         if (_t == null) {
/* 4381:3514 */           _t = ASTNULL;
/* 4382:     */         }
/* 4383:3515 */         switch (_t.getType())
/* 4384:     */         {
/* 4385:     */         case 33: 
/* 4386:3518 */           left = _t;
/* 4387:3519 */           AST left_AST_in = null;
/* 4388:3520 */           left_AST = this.astFactory.create(left);
/* 4389:3521 */           this.astFactory.addASTChild(currentAST, left_AST);
/* 4390:3522 */           match(_t, 33);
/* 4391:3523 */           _t = _t.getNextSibling();
/* 4392:3524 */           break;
/* 4393:     */         case 44: 
/* 4394:3528 */           right = _t;
/* 4395:3529 */           AST right_AST_in = null;
/* 4396:3530 */           right_AST = this.astFactory.create(right);
/* 4397:3531 */           this.astFactory.addASTChild(currentAST, right_AST);
/* 4398:3532 */           match(_t, 44);
/* 4399:3533 */           _t = _t.getNextSibling();
/* 4400:3534 */           break;
/* 4401:     */         default: 
/* 4402:3538 */           throw new NoViableAltException(_t);
/* 4403:     */         }
/* 4404:3543 */         if (_t == null) {
/* 4405:3543 */           _t = ASTNULL;
/* 4406:     */         }
/* 4407:3544 */         switch (_t.getType())
/* 4408:     */         {
/* 4409:     */         case 42: 
/* 4410:3547 */           outer = _t;
/* 4411:3548 */           AST outer_AST_in = null;
/* 4412:3549 */           outer_AST = this.astFactory.create(outer);
/* 4413:3550 */           this.astFactory.addASTChild(currentAST, outer_AST);
/* 4414:3551 */           match(_t, 42);
/* 4415:3552 */           _t = _t.getNextSibling();
/* 4416:3553 */           break;
/* 4417:     */         case 15: 
/* 4418:     */         case 21: 
/* 4419:     */         case 68: 
/* 4420:     */         case 69: 
/* 4421:     */         case 70: 
/* 4422:     */         case 93: 
/* 4423:     */         case 126: 
/* 4424:     */           break;
/* 4425:     */         default: 
/* 4426:3567 */           throw new NoViableAltException(_t);
/* 4427:     */         }
/* 4428:3573 */         if (left != null) {
/* 4429:3573 */           j = 138;
/* 4430:3574 */         } else if (right != null) {
/* 4431:3574 */           j = 139;
/* 4432:3575 */         } else if (outer != null) {
/* 4433:3575 */           j = 139;
/* 4434:     */         }
/* 4435:3577 */         joinType_AST = currentAST.root;
/* 4436:3578 */         break;
/* 4437:     */       case 23: 
/* 4438:3582 */         AST tmp49_AST = null;
/* 4439:3583 */         AST tmp49_AST_in = null;
/* 4440:3584 */         tmp49_AST = this.astFactory.create(_t);
/* 4441:3585 */         tmp49_AST_in = _t;
/* 4442:3586 */         this.astFactory.addASTChild(currentAST, tmp49_AST);
/* 4443:3587 */         match(_t, 23);
/* 4444:3588 */         _t = _t.getNextSibling();
/* 4445:     */         
/* 4446:3590 */         j = 23;
/* 4447:     */         
/* 4448:3592 */         joinType_AST = currentAST.root;
/* 4449:3593 */         break;
/* 4450:     */       case 28: 
/* 4451:3597 */         AST tmp50_AST = null;
/* 4452:3598 */         AST tmp50_AST_in = null;
/* 4453:3599 */         tmp50_AST = this.astFactory.create(_t);
/* 4454:3600 */         tmp50_AST_in = _t;
/* 4455:3601 */         this.astFactory.addASTChild(currentAST, tmp50_AST);
/* 4456:3602 */         match(_t, 28);
/* 4457:3603 */         _t = _t.getNextSibling();
/* 4458:     */         
/* 4459:3605 */         j = 28;
/* 4460:     */         
/* 4461:3607 */         joinType_AST = currentAST.root;
/* 4462:3608 */         break;
/* 4463:     */       default: 
/* 4464:3612 */         throw new NoViableAltException(_t);
/* 4465:     */       }
/* 4466:     */     }
/* 4467:     */     catch (RecognitionException ex)
/* 4468:     */     {
/* 4469:3617 */       reportError(ex);
/* 4470:3618 */       if (_t != null) {
/* 4471:3618 */         _t = _t.getNextSibling();
/* 4472:     */       }
/* 4473:     */     }
/* 4474:3620 */     this.returnAST = joinType_AST;
/* 4475:3621 */     this._retTree = _t;
/* 4476:3622 */     return j;
/* 4477:     */   }
/* 4478:     */   
/* 4479:     */   public final void pathAsIdent(AST _t)
/* 4480:     */     throws RecognitionException
/* 4481:     */   {
/* 4482:3627 */     AST pathAsIdent_AST_in = _t == ASTNULL ? null : _t;
/* 4483:3628 */     this.returnAST = null;
/* 4484:3629 */     ASTPair currentAST = new ASTPair();
/* 4485:3630 */     AST pathAsIdent_AST = null;
/* 4486:     */     
/* 4487:3632 */     String text = "?text?";
/* 4488:     */     try
/* 4489:     */     {
/* 4490:3636 */       text = path(_t);
/* 4491:3637 */       _t = this._retTree;
/* 4492:3638 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/* 4493:3639 */       pathAsIdent_AST = currentAST.root;
/* 4494:     */       
/* 4495:3641 */       pathAsIdent_AST = this.astFactory.make(new ASTArray(1).add(this.astFactory.create(126, text)));
/* 4496:     */       
/* 4497:3643 */       currentAST.root = pathAsIdent_AST;
/* 4498:3644 */       currentAST.child = ((pathAsIdent_AST != null) && (pathAsIdent_AST.getFirstChild() != null) ? pathAsIdent_AST.getFirstChild() : pathAsIdent_AST);
/* 4499:     */       
/* 4500:3646 */       currentAST.advanceChildToEnd();
/* 4501:3647 */       pathAsIdent_AST = currentAST.root;
/* 4502:     */     }
/* 4503:     */     catch (RecognitionException ex)
/* 4504:     */     {
/* 4505:3650 */       reportError(ex);
/* 4506:3651 */       if (_t != null) {
/* 4507:3651 */         _t = _t.getNextSibling();
/* 4508:     */       }
/* 4509:     */     }
/* 4510:3653 */     this.returnAST = pathAsIdent_AST;
/* 4511:3654 */     this._retTree = _t;
/* 4512:     */   }
/* 4513:     */   
/* 4514:     */   public final void withClause(AST _t)
/* 4515:     */     throws RecognitionException
/* 4516:     */   {
/* 4517:3659 */     AST withClause_AST_in = _t == ASTNULL ? null : _t;
/* 4518:3660 */     this.returnAST = null;
/* 4519:3661 */     ASTPair currentAST = new ASTPair();
/* 4520:3662 */     AST withClause_AST = null;
/* 4521:3663 */     AST w = null;
/* 4522:3664 */     AST w_AST = null;
/* 4523:3665 */     AST b_AST = null;
/* 4524:3666 */     AST b = null;
/* 4525:     */     try
/* 4526:     */     {
/* 4527:3669 */       AST __t92 = _t;
/* 4528:3670 */       w = _t == ASTNULL ? null : _t;
/* 4529:3671 */       AST w_AST_in = null;
/* 4530:3672 */       w_AST = this.astFactory.create(w);
/* 4531:3673 */       this.astFactory.addASTChild(currentAST, w_AST);
/* 4532:3674 */       ASTPair __currentAST92 = currentAST.copy();
/* 4533:3675 */       currentAST.root = currentAST.child;
/* 4534:3676 */       currentAST.child = null;
/* 4535:3677 */       match(_t, 60);
/* 4536:3678 */       _t = _t.getFirstChild();
/* 4537:3679 */       handleClauseStart(60);
/* 4538:3680 */       b = _t == ASTNULL ? null : _t;
/* 4539:3681 */       logicalExpr(_t);
/* 4540:3682 */       _t = this._retTree;
/* 4541:3683 */       b_AST = this.returnAST;
/* 4542:3684 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/* 4543:3685 */       currentAST = __currentAST92;
/* 4544:3686 */       _t = __t92;
/* 4545:3687 */       _t = _t.getNextSibling();
/* 4546:3688 */       withClause_AST = currentAST.root;
/* 4547:     */       
/* 4548:3690 */       withClause_AST = this.astFactory.make(new ASTArray(2).add(w_AST).add(b_AST));
/* 4549:     */       
/* 4550:3692 */       currentAST.root = withClause_AST;
/* 4551:3693 */       currentAST.child = ((withClause_AST != null) && (withClause_AST.getFirstChild() != null) ? withClause_AST.getFirstChild() : withClause_AST);
/* 4552:     */       
/* 4553:3695 */       currentAST.advanceChildToEnd();
/* 4554:3696 */       withClause_AST = currentAST.root;
/* 4555:     */     }
/* 4556:     */     catch (RecognitionException ex)
/* 4557:     */     {
/* 4558:3699 */       reportError(ex);
/* 4559:3700 */       if (_t != null) {
/* 4560:3700 */         _t = _t.getNextSibling();
/* 4561:     */       }
/* 4562:     */     }
/* 4563:3702 */     this.returnAST = withClause_AST;
/* 4564:3703 */     this._retTree = _t;
/* 4565:     */   }
/* 4566:     */   
/* 4567:     */   public final void comparisonExpr(AST _t)
/* 4568:     */     throws RecognitionException
/* 4569:     */   {
/* 4570:3708 */     AST comparisonExpr_AST_in = _t == ASTNULL ? null : _t;
/* 4571:3709 */     this.returnAST = null;
/* 4572:3710 */     ASTPair currentAST = new ASTPair();
/* 4573:3711 */     AST comparisonExpr_AST = null;
/* 4574:     */     try
/* 4575:     */     {
/* 4576:3715 */       if (_t == null) {
/* 4577:3715 */         _t = ASTNULL;
/* 4578:     */       }
/* 4579:3716 */       switch (_t.getType())
/* 4580:     */       {
/* 4581:     */       case 102: 
/* 4582:3719 */         AST __t101 = _t;
/* 4583:3720 */         AST tmp51_AST = null;
/* 4584:3721 */         AST tmp51_AST_in = null;
/* 4585:3722 */         tmp51_AST = this.astFactory.create(_t);
/* 4586:3723 */         tmp51_AST_in = _t;
/* 4587:3724 */         this.astFactory.addASTChild(currentAST, tmp51_AST);
/* 4588:3725 */         ASTPair __currentAST101 = currentAST.copy();
/* 4589:3726 */         currentAST.root = currentAST.child;
/* 4590:3727 */         currentAST.child = null;
/* 4591:3728 */         match(_t, 102);
/* 4592:3729 */         _t = _t.getFirstChild();
/* 4593:3730 */         exprOrSubquery(_t);
/* 4594:3731 */         _t = this._retTree;
/* 4595:3732 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 4596:3733 */         exprOrSubquery(_t);
/* 4597:3734 */         _t = this._retTree;
/* 4598:3735 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 4599:3736 */         currentAST = __currentAST101;
/* 4600:3737 */         _t = __t101;
/* 4601:3738 */         _t = _t.getNextSibling();
/* 4602:3739 */         break;
/* 4603:     */       case 108: 
/* 4604:3743 */         AST __t102 = _t;
/* 4605:3744 */         AST tmp52_AST = null;
/* 4606:3745 */         AST tmp52_AST_in = null;
/* 4607:3746 */         tmp52_AST = this.astFactory.create(_t);
/* 4608:3747 */         tmp52_AST_in = _t;
/* 4609:3748 */         this.astFactory.addASTChild(currentAST, tmp52_AST);
/* 4610:3749 */         ASTPair __currentAST102 = currentAST.copy();
/* 4611:3750 */         currentAST.root = currentAST.child;
/* 4612:3751 */         currentAST.child = null;
/* 4613:3752 */         match(_t, 108);
/* 4614:3753 */         _t = _t.getFirstChild();
/* 4615:3754 */         exprOrSubquery(_t);
/* 4616:3755 */         _t = this._retTree;
/* 4617:3756 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 4618:3757 */         exprOrSubquery(_t);
/* 4619:3758 */         _t = this._retTree;
/* 4620:3759 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 4621:3760 */         currentAST = __currentAST102;
/* 4622:3761 */         _t = __t102;
/* 4623:3762 */         _t = _t.getNextSibling();
/* 4624:3763 */         break;
/* 4625:     */       case 110: 
/* 4626:3767 */         AST __t103 = _t;
/* 4627:3768 */         AST tmp53_AST = null;
/* 4628:3769 */         AST tmp53_AST_in = null;
/* 4629:3770 */         tmp53_AST = this.astFactory.create(_t);
/* 4630:3771 */         tmp53_AST_in = _t;
/* 4631:3772 */         this.astFactory.addASTChild(currentAST, tmp53_AST);
/* 4632:3773 */         ASTPair __currentAST103 = currentAST.copy();
/* 4633:3774 */         currentAST.root = currentAST.child;
/* 4634:3775 */         currentAST.child = null;
/* 4635:3776 */         match(_t, 110);
/* 4636:3777 */         _t = _t.getFirstChild();
/* 4637:3778 */         exprOrSubquery(_t);
/* 4638:3779 */         _t = this._retTree;
/* 4639:3780 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 4640:3781 */         exprOrSubquery(_t);
/* 4641:3782 */         _t = this._retTree;
/* 4642:3783 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 4643:3784 */         currentAST = __currentAST103;
/* 4644:3785 */         _t = __t103;
/* 4645:3786 */         _t = _t.getNextSibling();
/* 4646:3787 */         break;
/* 4647:     */       case 111: 
/* 4648:3791 */         AST __t104 = _t;
/* 4649:3792 */         AST tmp54_AST = null;
/* 4650:3793 */         AST tmp54_AST_in = null;
/* 4651:3794 */         tmp54_AST = this.astFactory.create(_t);
/* 4652:3795 */         tmp54_AST_in = _t;
/* 4653:3796 */         this.astFactory.addASTChild(currentAST, tmp54_AST);
/* 4654:3797 */         ASTPair __currentAST104 = currentAST.copy();
/* 4655:3798 */         currentAST.root = currentAST.child;
/* 4656:3799 */         currentAST.child = null;
/* 4657:3800 */         match(_t, 111);
/* 4658:3801 */         _t = _t.getFirstChild();
/* 4659:3802 */         exprOrSubquery(_t);
/* 4660:3803 */         _t = this._retTree;
/* 4661:3804 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 4662:3805 */         exprOrSubquery(_t);
/* 4663:3806 */         _t = this._retTree;
/* 4664:3807 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 4665:3808 */         currentAST = __currentAST104;
/* 4666:3809 */         _t = __t104;
/* 4667:3810 */         _t = _t.getNextSibling();
/* 4668:3811 */         break;
/* 4669:     */       case 112: 
/* 4670:3815 */         AST __t105 = _t;
/* 4671:3816 */         AST tmp55_AST = null;
/* 4672:3817 */         AST tmp55_AST_in = null;
/* 4673:3818 */         tmp55_AST = this.astFactory.create(_t);
/* 4674:3819 */         tmp55_AST_in = _t;
/* 4675:3820 */         this.astFactory.addASTChild(currentAST, tmp55_AST);
/* 4676:3821 */         ASTPair __currentAST105 = currentAST.copy();
/* 4677:3822 */         currentAST.root = currentAST.child;
/* 4678:3823 */         currentAST.child = null;
/* 4679:3824 */         match(_t, 112);
/* 4680:3825 */         _t = _t.getFirstChild();
/* 4681:3826 */         exprOrSubquery(_t);
/* 4682:3827 */         _t = this._retTree;
/* 4683:3828 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 4684:3829 */         exprOrSubquery(_t);
/* 4685:3830 */         _t = this._retTree;
/* 4686:3831 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 4687:3832 */         currentAST = __currentAST105;
/* 4688:3833 */         _t = __t105;
/* 4689:3834 */         _t = _t.getNextSibling();
/* 4690:3835 */         break;
/* 4691:     */       case 113: 
/* 4692:3839 */         AST __t106 = _t;
/* 4693:3840 */         AST tmp56_AST = null;
/* 4694:3841 */         AST tmp56_AST_in = null;
/* 4695:3842 */         tmp56_AST = this.astFactory.create(_t);
/* 4696:3843 */         tmp56_AST_in = _t;
/* 4697:3844 */         this.astFactory.addASTChild(currentAST, tmp56_AST);
/* 4698:3845 */         ASTPair __currentAST106 = currentAST.copy();
/* 4699:3846 */         currentAST.root = currentAST.child;
/* 4700:3847 */         currentAST.child = null;
/* 4701:3848 */         match(_t, 113);
/* 4702:3849 */         _t = _t.getFirstChild();
/* 4703:3850 */         exprOrSubquery(_t);
/* 4704:3851 */         _t = this._retTree;
/* 4705:3852 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 4706:3853 */         exprOrSubquery(_t);
/* 4707:3854 */         _t = this._retTree;
/* 4708:3855 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 4709:3856 */         currentAST = __currentAST106;
/* 4710:3857 */         _t = __t106;
/* 4711:3858 */         _t = _t.getNextSibling();
/* 4712:3859 */         break;
/* 4713:     */       case 34: 
/* 4714:3863 */         AST __t107 = _t;
/* 4715:3864 */         AST tmp57_AST = null;
/* 4716:3865 */         AST tmp57_AST_in = null;
/* 4717:3866 */         tmp57_AST = this.astFactory.create(_t);
/* 4718:3867 */         tmp57_AST_in = _t;
/* 4719:3868 */         this.astFactory.addASTChild(currentAST, tmp57_AST);
/* 4720:3869 */         ASTPair __currentAST107 = currentAST.copy();
/* 4721:3870 */         currentAST.root = currentAST.child;
/* 4722:3871 */         currentAST.child = null;
/* 4723:3872 */         match(_t, 34);
/* 4724:3873 */         _t = _t.getFirstChild();
/* 4725:3874 */         exprOrSubquery(_t);
/* 4726:3875 */         _t = this._retTree;
/* 4727:3876 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 4728:3877 */         expr(_t);
/* 4729:3878 */         _t = this._retTree;
/* 4730:3879 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 4731:3881 */         if (_t == null) {
/* 4732:3881 */           _t = ASTNULL;
/* 4733:     */         }
/* 4734:3882 */         switch (_t.getType())
/* 4735:     */         {
/* 4736:     */         case 18: 
/* 4737:3885 */           AST __t109 = _t;
/* 4738:3886 */           AST tmp58_AST = null;
/* 4739:3887 */           AST tmp58_AST_in = null;
/* 4740:3888 */           tmp58_AST = this.astFactory.create(_t);
/* 4741:3889 */           tmp58_AST_in = _t;
/* 4742:3890 */           this.astFactory.addASTChild(currentAST, tmp58_AST);
/* 4743:3891 */           ASTPair __currentAST109 = currentAST.copy();
/* 4744:3892 */           currentAST.root = currentAST.child;
/* 4745:3893 */           currentAST.child = null;
/* 4746:3894 */           match(_t, 18);
/* 4747:3895 */           _t = _t.getFirstChild();
/* 4748:3896 */           expr(_t);
/* 4749:3897 */           _t = this._retTree;
/* 4750:3898 */           this.astFactory.addASTChild(currentAST, this.returnAST);
/* 4751:3899 */           currentAST = __currentAST109;
/* 4752:3900 */           _t = __t109;
/* 4753:3901 */           _t = _t.getNextSibling();
/* 4754:3902 */           break;
/* 4755:     */         case 3: 
/* 4756:     */           break;
/* 4757:     */         default: 
/* 4758:3910 */           throw new NoViableAltException(_t);
/* 4759:     */         }
/* 4760:3914 */         currentAST = __currentAST107;
/* 4761:3915 */         _t = __t107;
/* 4762:3916 */         _t = _t.getNextSibling();
/* 4763:3917 */         break;
/* 4764:     */       case 84: 
/* 4765:3921 */         AST __t110 = _t;
/* 4766:3922 */         AST tmp59_AST = null;
/* 4767:3923 */         AST tmp59_AST_in = null;
/* 4768:3924 */         tmp59_AST = this.astFactory.create(_t);
/* 4769:3925 */         tmp59_AST_in = _t;
/* 4770:3926 */         this.astFactory.addASTChild(currentAST, tmp59_AST);
/* 4771:3927 */         ASTPair __currentAST110 = currentAST.copy();
/* 4772:3928 */         currentAST.root = currentAST.child;
/* 4773:3929 */         currentAST.child = null;
/* 4774:3930 */         match(_t, 84);
/* 4775:3931 */         _t = _t.getFirstChild();
/* 4776:3932 */         exprOrSubquery(_t);
/* 4777:3933 */         _t = this._retTree;
/* 4778:3934 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 4779:3935 */         expr(_t);
/* 4780:3936 */         _t = this._retTree;
/* 4781:3937 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 4782:3939 */         if (_t == null) {
/* 4783:3939 */           _t = ASTNULL;
/* 4784:     */         }
/* 4785:3940 */         switch (_t.getType())
/* 4786:     */         {
/* 4787:     */         case 18: 
/* 4788:3943 */           AST __t112 = _t;
/* 4789:3944 */           AST tmp60_AST = null;
/* 4790:3945 */           AST tmp60_AST_in = null;
/* 4791:3946 */           tmp60_AST = this.astFactory.create(_t);
/* 4792:3947 */           tmp60_AST_in = _t;
/* 4793:3948 */           this.astFactory.addASTChild(currentAST, tmp60_AST);
/* 4794:3949 */           ASTPair __currentAST112 = currentAST.copy();
/* 4795:3950 */           currentAST.root = currentAST.child;
/* 4796:3951 */           currentAST.child = null;
/* 4797:3952 */           match(_t, 18);
/* 4798:3953 */           _t = _t.getFirstChild();
/* 4799:3954 */           expr(_t);
/* 4800:3955 */           _t = this._retTree;
/* 4801:3956 */           this.astFactory.addASTChild(currentAST, this.returnAST);
/* 4802:3957 */           currentAST = __currentAST112;
/* 4803:3958 */           _t = __t112;
/* 4804:3959 */           _t = _t.getNextSibling();
/* 4805:3960 */           break;
/* 4806:     */         case 3: 
/* 4807:     */           break;
/* 4808:     */         default: 
/* 4809:3968 */           throw new NoViableAltException(_t);
/* 4810:     */         }
/* 4811:3972 */         currentAST = __currentAST110;
/* 4812:3973 */         _t = __t110;
/* 4813:3974 */         _t = _t.getNextSibling();
/* 4814:3975 */         break;
/* 4815:     */       case 10: 
/* 4816:3979 */         AST __t113 = _t;
/* 4817:3980 */         AST tmp61_AST = null;
/* 4818:3981 */         AST tmp61_AST_in = null;
/* 4819:3982 */         tmp61_AST = this.astFactory.create(_t);
/* 4820:3983 */         tmp61_AST_in = _t;
/* 4821:3984 */         this.astFactory.addASTChild(currentAST, tmp61_AST);
/* 4822:3985 */         ASTPair __currentAST113 = currentAST.copy();
/* 4823:3986 */         currentAST.root = currentAST.child;
/* 4824:3987 */         currentAST.child = null;
/* 4825:3988 */         match(_t, 10);
/* 4826:3989 */         _t = _t.getFirstChild();
/* 4827:3990 */         exprOrSubquery(_t);
/* 4828:3991 */         _t = this._retTree;
/* 4829:3992 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 4830:3993 */         exprOrSubquery(_t);
/* 4831:3994 */         _t = this._retTree;
/* 4832:3995 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 4833:3996 */         exprOrSubquery(_t);
/* 4834:3997 */         _t = this._retTree;
/* 4835:3998 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 4836:3999 */         currentAST = __currentAST113;
/* 4837:4000 */         _t = __t113;
/* 4838:4001 */         _t = _t.getNextSibling();
/* 4839:4002 */         break;
/* 4840:     */       case 82: 
/* 4841:4006 */         AST __t114 = _t;
/* 4842:4007 */         AST tmp62_AST = null;
/* 4843:4008 */         AST tmp62_AST_in = null;
/* 4844:4009 */         tmp62_AST = this.astFactory.create(_t);
/* 4845:4010 */         tmp62_AST_in = _t;
/* 4846:4011 */         this.astFactory.addASTChild(currentAST, tmp62_AST);
/* 4847:4012 */         ASTPair __currentAST114 = currentAST.copy();
/* 4848:4013 */         currentAST.root = currentAST.child;
/* 4849:4014 */         currentAST.child = null;
/* 4850:4015 */         match(_t, 82);
/* 4851:4016 */         _t = _t.getFirstChild();
/* 4852:4017 */         exprOrSubquery(_t);
/* 4853:4018 */         _t = this._retTree;
/* 4854:4019 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 4855:4020 */         exprOrSubquery(_t);
/* 4856:4021 */         _t = this._retTree;
/* 4857:4022 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 4858:4023 */         exprOrSubquery(_t);
/* 4859:4024 */         _t = this._retTree;
/* 4860:4025 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 4861:4026 */         currentAST = __currentAST114;
/* 4862:4027 */         _t = __t114;
/* 4863:4028 */         _t = _t.getNextSibling();
/* 4864:4029 */         break;
/* 4865:     */       case 26: 
/* 4866:4033 */         AST __t115 = _t;
/* 4867:4034 */         AST tmp63_AST = null;
/* 4868:4035 */         AST tmp63_AST_in = null;
/* 4869:4036 */         tmp63_AST = this.astFactory.create(_t);
/* 4870:4037 */         tmp63_AST_in = _t;
/* 4871:4038 */         this.astFactory.addASTChild(currentAST, tmp63_AST);
/* 4872:4039 */         ASTPair __currentAST115 = currentAST.copy();
/* 4873:4040 */         currentAST.root = currentAST.child;
/* 4874:4041 */         currentAST.child = null;
/* 4875:4042 */         match(_t, 26);
/* 4876:4043 */         _t = _t.getFirstChild();
/* 4877:4044 */         exprOrSubquery(_t);
/* 4878:4045 */         _t = this._retTree;
/* 4879:4046 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 4880:4047 */         inRhs(_t);
/* 4881:4048 */         _t = this._retTree;
/* 4882:4049 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 4883:4050 */         currentAST = __currentAST115;
/* 4884:4051 */         _t = __t115;
/* 4885:4052 */         _t = _t.getNextSibling();
/* 4886:4053 */         break;
/* 4887:     */       case 83: 
/* 4888:4057 */         AST __t116 = _t;
/* 4889:4058 */         AST tmp64_AST = null;
/* 4890:4059 */         AST tmp64_AST_in = null;
/* 4891:4060 */         tmp64_AST = this.astFactory.create(_t);
/* 4892:4061 */         tmp64_AST_in = _t;
/* 4893:4062 */         this.astFactory.addASTChild(currentAST, tmp64_AST);
/* 4894:4063 */         ASTPair __currentAST116 = currentAST.copy();
/* 4895:4064 */         currentAST.root = currentAST.child;
/* 4896:4065 */         currentAST.child = null;
/* 4897:4066 */         match(_t, 83);
/* 4898:4067 */         _t = _t.getFirstChild();
/* 4899:4068 */         exprOrSubquery(_t);
/* 4900:4069 */         _t = this._retTree;
/* 4901:4070 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 4902:4071 */         inRhs(_t);
/* 4903:4072 */         _t = this._retTree;
/* 4904:4073 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 4905:4074 */         currentAST = __currentAST116;
/* 4906:4075 */         _t = __t116;
/* 4907:4076 */         _t = _t.getNextSibling();
/* 4908:4077 */         break;
/* 4909:     */       case 80: 
/* 4910:4081 */         AST __t117 = _t;
/* 4911:4082 */         AST tmp65_AST = null;
/* 4912:4083 */         AST tmp65_AST_in = null;
/* 4913:4084 */         tmp65_AST = this.astFactory.create(_t);
/* 4914:4085 */         tmp65_AST_in = _t;
/* 4915:4086 */         this.astFactory.addASTChild(currentAST, tmp65_AST);
/* 4916:4087 */         ASTPair __currentAST117 = currentAST.copy();
/* 4917:4088 */         currentAST.root = currentAST.child;
/* 4918:4089 */         currentAST.child = null;
/* 4919:4090 */         match(_t, 80);
/* 4920:4091 */         _t = _t.getFirstChild();
/* 4921:4092 */         exprOrSubquery(_t);
/* 4922:4093 */         _t = this._retTree;
/* 4923:4094 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 4924:4095 */         currentAST = __currentAST117;
/* 4925:4096 */         _t = __t117;
/* 4926:4097 */         _t = _t.getNextSibling();
/* 4927:4098 */         break;
/* 4928:     */       case 79: 
/* 4929:4102 */         AST __t118 = _t;
/* 4930:4103 */         AST tmp66_AST = null;
/* 4931:4104 */         AST tmp66_AST_in = null;
/* 4932:4105 */         tmp66_AST = this.astFactory.create(_t);
/* 4933:4106 */         tmp66_AST_in = _t;
/* 4934:4107 */         this.astFactory.addASTChild(currentAST, tmp66_AST);
/* 4935:4108 */         ASTPair __currentAST118 = currentAST.copy();
/* 4936:4109 */         currentAST.root = currentAST.child;
/* 4937:4110 */         currentAST.child = null;
/* 4938:4111 */         match(_t, 79);
/* 4939:4112 */         _t = _t.getFirstChild();
/* 4940:4113 */         exprOrSubquery(_t);
/* 4941:4114 */         _t = this._retTree;
/* 4942:4115 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 4943:4116 */         currentAST = __currentAST118;
/* 4944:4117 */         _t = __t118;
/* 4945:4118 */         _t = _t.getNextSibling();
/* 4946:4119 */         break;
/* 4947:     */       case 19: 
/* 4948:4123 */         AST __t119 = _t;
/* 4949:4124 */         AST tmp67_AST = null;
/* 4950:4125 */         AST tmp67_AST_in = null;
/* 4951:4126 */         tmp67_AST = this.astFactory.create(_t);
/* 4952:4127 */         tmp67_AST_in = _t;
/* 4953:4128 */         this.astFactory.addASTChild(currentAST, tmp67_AST);
/* 4954:4129 */         ASTPair __currentAST119 = currentAST.copy();
/* 4955:4130 */         currentAST.root = currentAST.child;
/* 4956:4131 */         currentAST.child = null;
/* 4957:4132 */         match(_t, 19);
/* 4958:4133 */         _t = _t.getFirstChild();
/* 4959:4135 */         if (_t == null) {
/* 4960:4135 */           _t = ASTNULL;
/* 4961:     */         }
/* 4962:4136 */         switch (_t.getType())
/* 4963:     */         {
/* 4964:     */         case 12: 
/* 4965:     */         case 15: 
/* 4966:     */         case 20: 
/* 4967:     */         case 39: 
/* 4968:     */         case 49: 
/* 4969:     */         case 54: 
/* 4970:     */         case 71: 
/* 4971:     */         case 74: 
/* 4972:     */         case 78: 
/* 4973:     */         case 81: 
/* 4974:     */         case 90: 
/* 4975:     */         case 92: 
/* 4976:     */         case 93: 
/* 4977:     */         case 95: 
/* 4978:     */         case 96: 
/* 4979:     */         case 97: 
/* 4980:     */         case 98: 
/* 4981:     */         case 99: 
/* 4982:     */         case 100: 
/* 4983:     */         case 115: 
/* 4984:     */         case 116: 
/* 4985:     */         case 117: 
/* 4986:     */         case 118: 
/* 4987:     */         case 119: 
/* 4988:     */         case 122: 
/* 4989:     */         case 123: 
/* 4990:     */         case 124: 
/* 4991:     */         case 125: 
/* 4992:     */         case 126: 
/* 4993:4167 */           expr(_t);
/* 4994:4168 */           _t = this._retTree;
/* 4995:4169 */           this.astFactory.addASTChild(currentAST, this.returnAST);
/* 4996:4170 */           break;
/* 4997:     */         case 17: 
/* 4998:     */         case 27: 
/* 4999:     */         case 86: 
/* 5000:4176 */           collectionFunctionOrSubselect(_t);
/* 5001:4177 */           _t = this._retTree;
/* 5002:4178 */           this.astFactory.addASTChild(currentAST, this.returnAST);
/* 5003:4179 */           break;
/* 5004:     */         case 13: 
/* 5005:     */         case 14: 
/* 5006:     */         case 16: 
/* 5007:     */         case 18: 
/* 5008:     */         case 19: 
/* 5009:     */         case 21: 
/* 5010:     */         case 22: 
/* 5011:     */         case 23: 
/* 5012:     */         case 24: 
/* 5013:     */         case 25: 
/* 5014:     */         case 26: 
/* 5015:     */         case 28: 
/* 5016:     */         case 29: 
/* 5017:     */         case 30: 
/* 5018:     */         case 31: 
/* 5019:     */         case 32: 
/* 5020:     */         case 33: 
/* 5021:     */         case 34: 
/* 5022:     */         case 35: 
/* 5023:     */         case 36: 
/* 5024:     */         case 37: 
/* 5025:     */         case 38: 
/* 5026:     */         case 40: 
/* 5027:     */         case 41: 
/* 5028:     */         case 42: 
/* 5029:     */         case 43: 
/* 5030:     */         case 44: 
/* 5031:     */         case 45: 
/* 5032:     */         case 46: 
/* 5033:     */         case 47: 
/* 5034:     */         case 48: 
/* 5035:     */         case 50: 
/* 5036:     */         case 51: 
/* 5037:     */         case 52: 
/* 5038:     */         case 53: 
/* 5039:     */         case 55: 
/* 5040:     */         case 56: 
/* 5041:     */         case 57: 
/* 5042:     */         case 58: 
/* 5043:     */         case 59: 
/* 5044:     */         case 60: 
/* 5045:     */         case 61: 
/* 5046:     */         case 62: 
/* 5047:     */         case 63: 
/* 5048:     */         case 64: 
/* 5049:     */         case 65: 
/* 5050:     */         case 66: 
/* 5051:     */         case 67: 
/* 5052:     */         case 68: 
/* 5053:     */         case 69: 
/* 5054:     */         case 70: 
/* 5055:     */         case 72: 
/* 5056:     */         case 73: 
/* 5057:     */         case 75: 
/* 5058:     */         case 76: 
/* 5059:     */         case 77: 
/* 5060:     */         case 79: 
/* 5061:     */         case 80: 
/* 5062:     */         case 82: 
/* 5063:     */         case 83: 
/* 5064:     */         case 84: 
/* 5065:     */         case 85: 
/* 5066:     */         case 87: 
/* 5067:     */         case 88: 
/* 5068:     */         case 89: 
/* 5069:     */         case 91: 
/* 5070:     */         case 94: 
/* 5071:     */         case 101: 
/* 5072:     */         case 102: 
/* 5073:     */         case 103: 
/* 5074:     */         case 104: 
/* 5075:     */         case 105: 
/* 5076:     */         case 106: 
/* 5077:     */         case 107: 
/* 5078:     */         case 108: 
/* 5079:     */         case 109: 
/* 5080:     */         case 110: 
/* 5081:     */         case 111: 
/* 5082:     */         case 112: 
/* 5083:     */         case 113: 
/* 5084:     */         case 114: 
/* 5085:     */         case 120: 
/* 5086:     */         case 121: 
/* 5087:     */         default: 
/* 5088:4183 */           throw new NoViableAltException(_t);
/* 5089:     */         }
/* 5090:4187 */         currentAST = __currentAST119;
/* 5091:4188 */         _t = __t119;
/* 5092:4189 */         _t = _t.getNextSibling();
/* 5093:4190 */         break;
/* 5094:     */       default: 
/* 5095:4194 */         throw new NoViableAltException(_t);
/* 5096:     */       }
/* 5097:4198 */       comparisonExpr_AST = currentAST.root;
/* 5098:     */       
/* 5099:4200 */       prepareLogicOperator(comparisonExpr_AST);
/* 5100:     */       
/* 5101:4202 */       comparisonExpr_AST = currentAST.root;
/* 5102:     */     }
/* 5103:     */     catch (RecognitionException ex)
/* 5104:     */     {
/* 5105:4205 */       reportError(ex);
/* 5106:4206 */       if (_t != null) {
/* 5107:4206 */         _t = _t.getNextSibling();
/* 5108:     */       }
/* 5109:     */     }
/* 5110:4208 */     this.returnAST = comparisonExpr_AST;
/* 5111:4209 */     this._retTree = _t;
/* 5112:     */   }
/* 5113:     */   
/* 5114:     */   public final void exprOrSubquery(AST _t)
/* 5115:     */     throws RecognitionException
/* 5116:     */   {
/* 5117:4214 */     AST exprOrSubquery_AST_in = _t == ASTNULL ? null : _t;
/* 5118:4215 */     this.returnAST = null;
/* 5119:4216 */     ASTPair currentAST = new ASTPair();
/* 5120:4217 */     AST exprOrSubquery_AST = null;
/* 5121:     */     try
/* 5122:     */     {
/* 5123:4220 */       if (_t == null) {
/* 5124:4220 */         _t = ASTNULL;
/* 5125:     */       }
/* 5126:4221 */       switch (_t.getType())
/* 5127:     */       {
/* 5128:     */       case 12: 
/* 5129:     */       case 15: 
/* 5130:     */       case 20: 
/* 5131:     */       case 39: 
/* 5132:     */       case 49: 
/* 5133:     */       case 54: 
/* 5134:     */       case 71: 
/* 5135:     */       case 74: 
/* 5136:     */       case 78: 
/* 5137:     */       case 81: 
/* 5138:     */       case 90: 
/* 5139:     */       case 92: 
/* 5140:     */       case 93: 
/* 5141:     */       case 95: 
/* 5142:     */       case 96: 
/* 5143:     */       case 97: 
/* 5144:     */       case 98: 
/* 5145:     */       case 99: 
/* 5146:     */       case 100: 
/* 5147:     */       case 115: 
/* 5148:     */       case 116: 
/* 5149:     */       case 117: 
/* 5150:     */       case 118: 
/* 5151:     */       case 119: 
/* 5152:     */       case 122: 
/* 5153:     */       case 123: 
/* 5154:     */       case 124: 
/* 5155:     */       case 125: 
/* 5156:     */       case 126: 
/* 5157:4252 */         expr(_t);
/* 5158:4253 */         _t = this._retTree;
/* 5159:4254 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 5160:4255 */         exprOrSubquery_AST = currentAST.root;
/* 5161:4256 */         break;
/* 5162:     */       case 86: 
/* 5163:4260 */         query(_t);
/* 5164:4261 */         _t = this._retTree;
/* 5165:4262 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 5166:4263 */         exprOrSubquery_AST = currentAST.root;
/* 5167:4264 */         break;
/* 5168:     */       case 5: 
/* 5169:4268 */         AST __t128 = _t;
/* 5170:4269 */         AST tmp68_AST = null;
/* 5171:4270 */         AST tmp68_AST_in = null;
/* 5172:4271 */         tmp68_AST = this.astFactory.create(_t);
/* 5173:4272 */         tmp68_AST_in = _t;
/* 5174:4273 */         this.astFactory.addASTChild(currentAST, tmp68_AST);
/* 5175:4274 */         ASTPair __currentAST128 = currentAST.copy();
/* 5176:4275 */         currentAST.root = currentAST.child;
/* 5177:4276 */         currentAST.child = null;
/* 5178:4277 */         match(_t, 5);
/* 5179:4278 */         _t = _t.getFirstChild();
/* 5180:4279 */         collectionFunctionOrSubselect(_t);
/* 5181:4280 */         _t = this._retTree;
/* 5182:4281 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 5183:4282 */         currentAST = __currentAST128;
/* 5184:4283 */         _t = __t128;
/* 5185:4284 */         _t = _t.getNextSibling();
/* 5186:4285 */         exprOrSubquery_AST = currentAST.root;
/* 5187:4286 */         break;
/* 5188:     */       case 4: 
/* 5189:4290 */         AST __t129 = _t;
/* 5190:4291 */         AST tmp69_AST = null;
/* 5191:4292 */         AST tmp69_AST_in = null;
/* 5192:4293 */         tmp69_AST = this.astFactory.create(_t);
/* 5193:4294 */         tmp69_AST_in = _t;
/* 5194:4295 */         this.astFactory.addASTChild(currentAST, tmp69_AST);
/* 5195:4296 */         ASTPair __currentAST129 = currentAST.copy();
/* 5196:4297 */         currentAST.root = currentAST.child;
/* 5197:4298 */         currentAST.child = null;
/* 5198:4299 */         match(_t, 4);
/* 5199:4300 */         _t = _t.getFirstChild();
/* 5200:4301 */         collectionFunctionOrSubselect(_t);
/* 5201:4302 */         _t = this._retTree;
/* 5202:4303 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 5203:4304 */         currentAST = __currentAST129;
/* 5204:4305 */         _t = __t129;
/* 5205:4306 */         _t = _t.getNextSibling();
/* 5206:4307 */         exprOrSubquery_AST = currentAST.root;
/* 5207:4308 */         break;
/* 5208:     */       case 47: 
/* 5209:4312 */         AST __t130 = _t;
/* 5210:4313 */         AST tmp70_AST = null;
/* 5211:4314 */         AST tmp70_AST_in = null;
/* 5212:4315 */         tmp70_AST = this.astFactory.create(_t);
/* 5213:4316 */         tmp70_AST_in = _t;
/* 5214:4317 */         this.astFactory.addASTChild(currentAST, tmp70_AST);
/* 5215:4318 */         ASTPair __currentAST130 = currentAST.copy();
/* 5216:4319 */         currentAST.root = currentAST.child;
/* 5217:4320 */         currentAST.child = null;
/* 5218:4321 */         match(_t, 47);
/* 5219:4322 */         _t = _t.getFirstChild();
/* 5220:4323 */         collectionFunctionOrSubselect(_t);
/* 5221:4324 */         _t = this._retTree;
/* 5222:4325 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 5223:4326 */         currentAST = __currentAST130;
/* 5224:4327 */         _t = __t130;
/* 5225:4328 */         _t = _t.getNextSibling();
/* 5226:4329 */         exprOrSubquery_AST = currentAST.root;
/* 5227:4330 */         break;
/* 5228:     */       case 6: 
/* 5229:     */       case 7: 
/* 5230:     */       case 8: 
/* 5231:     */       case 9: 
/* 5232:     */       case 10: 
/* 5233:     */       case 11: 
/* 5234:     */       case 13: 
/* 5235:     */       case 14: 
/* 5236:     */       case 16: 
/* 5237:     */       case 17: 
/* 5238:     */       case 18: 
/* 5239:     */       case 19: 
/* 5240:     */       case 21: 
/* 5241:     */       case 22: 
/* 5242:     */       case 23: 
/* 5243:     */       case 24: 
/* 5244:     */       case 25: 
/* 5245:     */       case 26: 
/* 5246:     */       case 27: 
/* 5247:     */       case 28: 
/* 5248:     */       case 29: 
/* 5249:     */       case 30: 
/* 5250:     */       case 31: 
/* 5251:     */       case 32: 
/* 5252:     */       case 33: 
/* 5253:     */       case 34: 
/* 5254:     */       case 35: 
/* 5255:     */       case 36: 
/* 5256:     */       case 37: 
/* 5257:     */       case 38: 
/* 5258:     */       case 40: 
/* 5259:     */       case 41: 
/* 5260:     */       case 42: 
/* 5261:     */       case 43: 
/* 5262:     */       case 44: 
/* 5263:     */       case 45: 
/* 5264:     */       case 46: 
/* 5265:     */       case 48: 
/* 5266:     */       case 50: 
/* 5267:     */       case 51: 
/* 5268:     */       case 52: 
/* 5269:     */       case 53: 
/* 5270:     */       case 55: 
/* 5271:     */       case 56: 
/* 5272:     */       case 57: 
/* 5273:     */       case 58: 
/* 5274:     */       case 59: 
/* 5275:     */       case 60: 
/* 5276:     */       case 61: 
/* 5277:     */       case 62: 
/* 5278:     */       case 63: 
/* 5279:     */       case 64: 
/* 5280:     */       case 65: 
/* 5281:     */       case 66: 
/* 5282:     */       case 67: 
/* 5283:     */       case 68: 
/* 5284:     */       case 69: 
/* 5285:     */       case 70: 
/* 5286:     */       case 72: 
/* 5287:     */       case 73: 
/* 5288:     */       case 75: 
/* 5289:     */       case 76: 
/* 5290:     */       case 77: 
/* 5291:     */       case 79: 
/* 5292:     */       case 80: 
/* 5293:     */       case 82: 
/* 5294:     */       case 83: 
/* 5295:     */       case 84: 
/* 5296:     */       case 85: 
/* 5297:     */       case 87: 
/* 5298:     */       case 88: 
/* 5299:     */       case 89: 
/* 5300:     */       case 91: 
/* 5301:     */       case 94: 
/* 5302:     */       case 101: 
/* 5303:     */       case 102: 
/* 5304:     */       case 103: 
/* 5305:     */       case 104: 
/* 5306:     */       case 105: 
/* 5307:     */       case 106: 
/* 5308:     */       case 107: 
/* 5309:     */       case 108: 
/* 5310:     */       case 109: 
/* 5311:     */       case 110: 
/* 5312:     */       case 111: 
/* 5313:     */       case 112: 
/* 5314:     */       case 113: 
/* 5315:     */       case 114: 
/* 5316:     */       case 120: 
/* 5317:     */       case 121: 
/* 5318:     */       default: 
/* 5319:4334 */         throw new NoViableAltException(_t);
/* 5320:     */       }
/* 5321:     */     }
/* 5322:     */     catch (RecognitionException ex)
/* 5323:     */     {
/* 5324:4339 */       reportError(ex);
/* 5325:4340 */       if (_t != null) {
/* 5326:4340 */         _t = _t.getNextSibling();
/* 5327:     */       }
/* 5328:     */     }
/* 5329:4342 */     this.returnAST = exprOrSubquery_AST;
/* 5330:4343 */     this._retTree = _t;
/* 5331:     */   }
/* 5332:     */   
/* 5333:     */   public final void inRhs(AST _t)
/* 5334:     */     throws RecognitionException
/* 5335:     */   {
/* 5336:4348 */     AST inRhs_AST_in = _t == ASTNULL ? null : _t;
/* 5337:4349 */     this.returnAST = null;
/* 5338:4350 */     ASTPair currentAST = new ASTPair();
/* 5339:4351 */     AST inRhs_AST = null;
/* 5340:     */     try
/* 5341:     */     {
/* 5342:4354 */       AST __t122 = _t;
/* 5343:4355 */       AST tmp71_AST = null;
/* 5344:4356 */       AST tmp71_AST_in = null;
/* 5345:4357 */       tmp71_AST = this.astFactory.create(_t);
/* 5346:4358 */       tmp71_AST_in = _t;
/* 5347:4359 */       this.astFactory.addASTChild(currentAST, tmp71_AST);
/* 5348:4360 */       ASTPair __currentAST122 = currentAST.copy();
/* 5349:4361 */       currentAST.root = currentAST.child;
/* 5350:4362 */       currentAST.child = null;
/* 5351:4363 */       match(_t, 77);
/* 5352:4364 */       _t = _t.getFirstChild();
/* 5353:4366 */       if (_t == null) {
/* 5354:4366 */         _t = ASTNULL;
/* 5355:     */       }
/* 5356:4367 */       switch (_t.getType())
/* 5357:     */       {
/* 5358:     */       case 17: 
/* 5359:     */       case 27: 
/* 5360:     */       case 86: 
/* 5361:4372 */         collectionFunctionOrSubselect(_t);
/* 5362:4373 */         _t = this._retTree;
/* 5363:4374 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 5364:4375 */         break;
/* 5365:     */       case 3: 
/* 5366:     */       case 12: 
/* 5367:     */       case 15: 
/* 5368:     */       case 20: 
/* 5369:     */       case 39: 
/* 5370:     */       case 49: 
/* 5371:     */       case 54: 
/* 5372:     */       case 71: 
/* 5373:     */       case 74: 
/* 5374:     */       case 78: 
/* 5375:     */       case 81: 
/* 5376:     */       case 90: 
/* 5377:     */       case 92: 
/* 5378:     */       case 93: 
/* 5379:     */       case 95: 
/* 5380:     */       case 96: 
/* 5381:     */       case 97: 
/* 5382:     */       case 98: 
/* 5383:     */       case 99: 
/* 5384:     */       case 100: 
/* 5385:     */       case 115: 
/* 5386:     */       case 116: 
/* 5387:     */       case 117: 
/* 5388:     */       case 118: 
/* 5389:     */       case 119: 
/* 5390:     */       case 122: 
/* 5391:     */       case 123: 
/* 5392:     */       case 124: 
/* 5393:     */       case 125: 
/* 5394:     */       case 126: 
/* 5395:     */         for (;;)
/* 5396:     */         {
/* 5397:4412 */           if (_t == null) {
/* 5398:4412 */             _t = ASTNULL;
/* 5399:     */           }
/* 5400:4413 */           if (!_tokenSet_0.member(_t.getType())) {
/* 5401:     */             break;
/* 5402:     */           }
/* 5403:4414 */           expr(_t);
/* 5404:4415 */           _t = this._retTree;
/* 5405:4416 */           this.astFactory.addASTChild(currentAST, this.returnAST);
/* 5406:     */         }
/* 5407:     */       case 4: 
/* 5408:     */       case 5: 
/* 5409:     */       case 6: 
/* 5410:     */       case 7: 
/* 5411:     */       case 8: 
/* 5412:     */       case 9: 
/* 5413:     */       case 10: 
/* 5414:     */       case 11: 
/* 5415:     */       case 13: 
/* 5416:     */       case 14: 
/* 5417:     */       case 16: 
/* 5418:     */       case 18: 
/* 5419:     */       case 19: 
/* 5420:     */       case 21: 
/* 5421:     */       case 22: 
/* 5422:     */       case 23: 
/* 5423:     */       case 24: 
/* 5424:     */       case 25: 
/* 5425:     */       case 26: 
/* 5426:     */       case 28: 
/* 5427:     */       case 29: 
/* 5428:     */       case 30: 
/* 5429:     */       case 31: 
/* 5430:     */       case 32: 
/* 5431:     */       case 33: 
/* 5432:     */       case 34: 
/* 5433:     */       case 35: 
/* 5434:     */       case 36: 
/* 5435:     */       case 37: 
/* 5436:     */       case 38: 
/* 5437:     */       case 40: 
/* 5438:     */       case 41: 
/* 5439:     */       case 42: 
/* 5440:     */       case 43: 
/* 5441:     */       case 44: 
/* 5442:     */       case 45: 
/* 5443:     */       case 46: 
/* 5444:     */       case 47: 
/* 5445:     */       case 48: 
/* 5446:     */       case 50: 
/* 5447:     */       case 51: 
/* 5448:     */       case 52: 
/* 5449:     */       case 53: 
/* 5450:     */       case 55: 
/* 5451:     */       case 56: 
/* 5452:     */       case 57: 
/* 5453:     */       case 58: 
/* 5454:     */       case 59: 
/* 5455:     */       case 60: 
/* 5456:     */       case 61: 
/* 5457:     */       case 62: 
/* 5458:     */       case 63: 
/* 5459:     */       case 64: 
/* 5460:     */       case 65: 
/* 5461:     */       case 66: 
/* 5462:     */       case 67: 
/* 5463:     */       case 68: 
/* 5464:     */       case 69: 
/* 5465:     */       case 70: 
/* 5466:     */       case 72: 
/* 5467:     */       case 73: 
/* 5468:     */       case 75: 
/* 5469:     */       case 76: 
/* 5470:     */       case 77: 
/* 5471:     */       case 79: 
/* 5472:     */       case 80: 
/* 5473:     */       case 82: 
/* 5474:     */       case 83: 
/* 5475:     */       case 84: 
/* 5476:     */       case 85: 
/* 5477:     */       case 87: 
/* 5478:     */       case 88: 
/* 5479:     */       case 89: 
/* 5480:     */       case 91: 
/* 5481:     */       case 94: 
/* 5482:     */       case 101: 
/* 5483:     */       case 102: 
/* 5484:     */       case 103: 
/* 5485:     */       case 104: 
/* 5486:     */       case 105: 
/* 5487:     */       case 106: 
/* 5488:     */       case 107: 
/* 5489:     */       case 108: 
/* 5490:     */       case 109: 
/* 5491:     */       case 110: 
/* 5492:     */       case 111: 
/* 5493:     */       case 112: 
/* 5494:     */       case 113: 
/* 5495:     */       case 114: 
/* 5496:     */       case 120: 
/* 5497:     */       case 121: 
/* 5498:     */       default: 
/* 5499:4429 */         throw new NoViableAltException(_t);
/* 5500:     */       }
/* 5501:4433 */       currentAST = __currentAST122;
/* 5502:4434 */       _t = __t122;
/* 5503:4435 */       _t = _t.getNextSibling();
/* 5504:4436 */       inRhs_AST = currentAST.root;
/* 5505:     */     }
/* 5506:     */     catch (RecognitionException ex)
/* 5507:     */     {
/* 5508:4439 */       reportError(ex);
/* 5509:4440 */       if (_t != null) {
/* 5510:4440 */         _t = _t.getNextSibling();
/* 5511:     */       }
/* 5512:     */     }
/* 5513:4442 */     this.returnAST = inRhs_AST;
/* 5514:4443 */     this._retTree = _t;
/* 5515:     */   }
/* 5516:     */   
/* 5517:     */   public final void collectionFunctionOrSubselect(AST _t)
/* 5518:     */     throws RecognitionException
/* 5519:     */   {
/* 5520:4448 */     AST collectionFunctionOrSubselect_AST_in = _t == ASTNULL ? null : _t;
/* 5521:4449 */     this.returnAST = null;
/* 5522:4450 */     ASTPair currentAST = new ASTPair();
/* 5523:4451 */     AST collectionFunctionOrSubselect_AST = null;
/* 5524:     */     try
/* 5525:     */     {
/* 5526:4454 */       if (_t == null) {
/* 5527:4454 */         _t = ASTNULL;
/* 5528:     */       }
/* 5529:4455 */       switch (_t.getType())
/* 5530:     */       {
/* 5531:     */       case 17: 
/* 5532:     */       case 27: 
/* 5533:4459 */         collectionFunction(_t);
/* 5534:4460 */         _t = this._retTree;
/* 5535:4461 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 5536:4462 */         collectionFunctionOrSubselect_AST = currentAST.root;
/* 5537:4463 */         break;
/* 5538:     */       case 86: 
/* 5539:4467 */         query(_t);
/* 5540:4468 */         _t = this._retTree;
/* 5541:4469 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 5542:4470 */         collectionFunctionOrSubselect_AST = currentAST.root;
/* 5543:4471 */         break;
/* 5544:     */       default: 
/* 5545:4475 */         throw new NoViableAltException(_t);
/* 5546:     */       }
/* 5547:     */     }
/* 5548:     */     catch (RecognitionException ex)
/* 5549:     */     {
/* 5550:4480 */       reportError(ex);
/* 5551:4481 */       if (_t != null) {
/* 5552:4481 */         _t = _t.getNextSibling();
/* 5553:     */       }
/* 5554:     */     }
/* 5555:4483 */     this.returnAST = collectionFunctionOrSubselect_AST;
/* 5556:4484 */     this._retTree = _t;
/* 5557:     */   }
/* 5558:     */   
/* 5559:     */   public final void addrExpr(AST _t, boolean root)
/* 5560:     */     throws RecognitionException
/* 5561:     */   {
/* 5562:4491 */     AST addrExpr_AST_in = _t == ASTNULL ? null : _t;
/* 5563:4492 */     this.returnAST = null;
/* 5564:4493 */     ASTPair currentAST = new ASTPair();
/* 5565:4494 */     AST addrExpr_AST = null;
/* 5566:4495 */     AST d = null;
/* 5567:4496 */     AST d_AST = null;
/* 5568:4497 */     AST lhs_AST = null;
/* 5569:4498 */     AST lhs = null;
/* 5570:4499 */     AST rhs_AST = null;
/* 5571:4500 */     AST rhs = null;
/* 5572:4501 */     AST i = null;
/* 5573:4502 */     AST i_AST = null;
/* 5574:4503 */     AST lhs2_AST = null;
/* 5575:4504 */     AST lhs2 = null;
/* 5576:4505 */     AST rhs2_AST = null;
/* 5577:4506 */     AST rhs2 = null;
/* 5578:4507 */     AST p_AST = null;
/* 5579:4508 */     AST p = null;
/* 5580:     */     try
/* 5581:     */     {
/* 5582:4511 */       if (_t == null) {
/* 5583:4511 */         _t = ASTNULL;
/* 5584:     */       }
/* 5585:4512 */       switch (_t.getType())
/* 5586:     */       {
/* 5587:     */       case 15: 
/* 5588:4515 */         AST __t171 = _t;
/* 5589:4516 */         d = _t == ASTNULL ? null : _t;
/* 5590:4517 */         AST d_AST_in = null;
/* 5591:4518 */         d_AST = this.astFactory.create(d);
/* 5592:4519 */         ASTPair __currentAST171 = currentAST.copy();
/* 5593:4520 */         currentAST.root = currentAST.child;
/* 5594:4521 */         currentAST.child = null;
/* 5595:4522 */         match(_t, 15);
/* 5596:4523 */         _t = _t.getFirstChild();
/* 5597:4524 */         lhs = _t == ASTNULL ? null : _t;
/* 5598:4525 */         addrExprLhs(_t);
/* 5599:4526 */         _t = this._retTree;
/* 5600:4527 */         lhs_AST = this.returnAST;
/* 5601:4528 */         rhs = _t == ASTNULL ? null : _t;
/* 5602:4529 */         propertyName(_t);
/* 5603:4530 */         _t = this._retTree;
/* 5604:4531 */         rhs_AST = this.returnAST;
/* 5605:4532 */         currentAST = __currentAST171;
/* 5606:4533 */         _t = __t171;
/* 5607:4534 */         _t = _t.getNextSibling();
/* 5608:4535 */         addrExpr_AST = currentAST.root;
/* 5609:     */         
/* 5610:     */ 
/* 5611:     */ 
/* 5612:4539 */         addrExpr_AST = this.astFactory.make(new ASTArray(3).add(d_AST).add(lhs_AST).add(rhs_AST));
/* 5613:4540 */         addrExpr_AST = lookupProperty(addrExpr_AST, root, false);
/* 5614:     */         
/* 5615:4542 */         currentAST.root = addrExpr_AST;
/* 5616:4543 */         currentAST.child = ((addrExpr_AST != null) && (addrExpr_AST.getFirstChild() != null) ? addrExpr_AST.getFirstChild() : addrExpr_AST);
/* 5617:     */         
/* 5618:4545 */         currentAST.advanceChildToEnd();
/* 5619:4546 */         break;
/* 5620:     */       case 78: 
/* 5621:4550 */         AST __t172 = _t;
/* 5622:4551 */         i = _t == ASTNULL ? null : _t;
/* 5623:4552 */         AST i_AST_in = null;
/* 5624:4553 */         i_AST = this.astFactory.create(i);
/* 5625:4554 */         ASTPair __currentAST172 = currentAST.copy();
/* 5626:4555 */         currentAST.root = currentAST.child;
/* 5627:4556 */         currentAST.child = null;
/* 5628:4557 */         match(_t, 78);
/* 5629:4558 */         _t = _t.getFirstChild();
/* 5630:4559 */         lhs2 = _t == ASTNULL ? null : _t;
/* 5631:4560 */         addrExprLhs(_t);
/* 5632:4561 */         _t = this._retTree;
/* 5633:4562 */         lhs2_AST = this.returnAST;
/* 5634:4563 */         rhs2 = _t == ASTNULL ? null : _t;
/* 5635:4564 */         expr(_t);
/* 5636:4565 */         _t = this._retTree;
/* 5637:4566 */         rhs2_AST = this.returnAST;
/* 5638:4567 */         currentAST = __currentAST172;
/* 5639:4568 */         _t = __t172;
/* 5640:4569 */         _t = _t.getNextSibling();
/* 5641:4570 */         addrExpr_AST = currentAST.root;
/* 5642:     */         
/* 5643:4572 */         addrExpr_AST = this.astFactory.make(new ASTArray(3).add(i_AST).add(lhs2_AST).add(rhs2_AST));
/* 5644:4573 */         processIndex(addrExpr_AST);
/* 5645:     */         
/* 5646:4575 */         currentAST.root = addrExpr_AST;
/* 5647:4576 */         currentAST.child = ((addrExpr_AST != null) && (addrExpr_AST.getFirstChild() != null) ? addrExpr_AST.getFirstChild() : addrExpr_AST);
/* 5648:     */         
/* 5649:4578 */         currentAST.advanceChildToEnd();
/* 5650:4579 */         break;
/* 5651:     */       case 93: 
/* 5652:     */       case 126: 
/* 5653:4584 */         p = _t == ASTNULL ? null : _t;
/* 5654:4585 */         identifier(_t);
/* 5655:4586 */         _t = this._retTree;
/* 5656:4587 */         p_AST = this.returnAST;
/* 5657:4588 */         addrExpr_AST = currentAST.root;
/* 5658:4596 */         if (isNonQualifiedPropertyRef(p_AST))
/* 5659:     */         {
/* 5660:4597 */           addrExpr_AST = lookupNonQualifiedProperty(p_AST);
/* 5661:     */         }
/* 5662:     */         else
/* 5663:     */         {
/* 5664:4600 */           resolve(p_AST);
/* 5665:4601 */           addrExpr_AST = p_AST;
/* 5666:     */         }
/* 5667:4604 */         currentAST.root = addrExpr_AST;
/* 5668:4605 */         currentAST.child = ((addrExpr_AST != null) && (addrExpr_AST.getFirstChild() != null) ? addrExpr_AST.getFirstChild() : addrExpr_AST);
/* 5669:     */         
/* 5670:4607 */         currentAST.advanceChildToEnd();
/* 5671:4608 */         break;
/* 5672:     */       default: 
/* 5673:4612 */         throw new NoViableAltException(_t);
/* 5674:     */       }
/* 5675:     */     }
/* 5676:     */     catch (RecognitionException ex)
/* 5677:     */     {
/* 5678:4617 */       reportError(ex);
/* 5679:4618 */       if (_t != null) {
/* 5680:4618 */         _t = _t.getNextSibling();
/* 5681:     */       }
/* 5682:     */     }
/* 5683:4620 */     this.returnAST = addrExpr_AST;
/* 5684:4621 */     this._retTree = _t;
/* 5685:     */   }
/* 5686:     */   
/* 5687:     */   public final void constant(AST _t)
/* 5688:     */     throws RecognitionException
/* 5689:     */   {
/* 5690:4626 */     AST constant_AST_in = _t == ASTNULL ? null : _t;
/* 5691:4627 */     this.returnAST = null;
/* 5692:4628 */     ASTPair currentAST = new ASTPair();
/* 5693:4629 */     AST constant_AST = null;
/* 5694:     */     try
/* 5695:     */     {
/* 5696:4632 */       if (_t == null) {
/* 5697:4632 */         _t = ASTNULL;
/* 5698:     */       }
/* 5699:4633 */       switch (_t.getType())
/* 5700:     */       {
/* 5701:     */       case 95: 
/* 5702:     */       case 96: 
/* 5703:     */       case 97: 
/* 5704:     */       case 98: 
/* 5705:     */       case 99: 
/* 5706:     */       case 124: 
/* 5707:     */       case 125: 
/* 5708:4642 */         literal(_t);
/* 5709:4643 */         _t = this._retTree;
/* 5710:4644 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 5711:4645 */         constant_AST = currentAST.root;
/* 5712:4646 */         break;
/* 5713:     */       case 39: 
/* 5714:4650 */         AST tmp72_AST = null;
/* 5715:4651 */         AST tmp72_AST_in = null;
/* 5716:4652 */         tmp72_AST = this.astFactory.create(_t);
/* 5717:4653 */         tmp72_AST_in = _t;
/* 5718:4654 */         this.astFactory.addASTChild(currentAST, tmp72_AST);
/* 5719:4655 */         match(_t, 39);
/* 5720:4656 */         _t = _t.getNextSibling();
/* 5721:4657 */         constant_AST = currentAST.root;
/* 5722:4658 */         break;
/* 5723:     */       case 49: 
/* 5724:4662 */         AST tmp73_AST = null;
/* 5725:4663 */         AST tmp73_AST_in = null;
/* 5726:4664 */         tmp73_AST = this.astFactory.create(_t);
/* 5727:4665 */         tmp73_AST_in = _t;
/* 5728:4666 */         this.astFactory.addASTChild(currentAST, tmp73_AST);
/* 5729:4667 */         match(_t, 49);
/* 5730:4668 */         _t = _t.getNextSibling();
/* 5731:4669 */         constant_AST = currentAST.root;
/* 5732:4670 */         processBoolean(constant_AST);
/* 5733:4671 */         constant_AST = currentAST.root;
/* 5734:4672 */         break;
/* 5735:     */       case 20: 
/* 5736:4676 */         AST tmp74_AST = null;
/* 5737:4677 */         AST tmp74_AST_in = null;
/* 5738:4678 */         tmp74_AST = this.astFactory.create(_t);
/* 5739:4679 */         tmp74_AST_in = _t;
/* 5740:4680 */         this.astFactory.addASTChild(currentAST, tmp74_AST);
/* 5741:4681 */         match(_t, 20);
/* 5742:4682 */         _t = _t.getNextSibling();
/* 5743:4683 */         constant_AST = currentAST.root;
/* 5744:4684 */         processBoolean(constant_AST);
/* 5745:4685 */         constant_AST = currentAST.root;
/* 5746:4686 */         break;
/* 5747:     */       case 100: 
/* 5748:4690 */         AST tmp75_AST = null;
/* 5749:4691 */         AST tmp75_AST_in = null;
/* 5750:4692 */         tmp75_AST = this.astFactory.create(_t);
/* 5751:4693 */         tmp75_AST_in = _t;
/* 5752:4694 */         this.astFactory.addASTChild(currentAST, tmp75_AST);
/* 5753:4695 */         match(_t, 100);
/* 5754:4696 */         _t = _t.getNextSibling();
/* 5755:4697 */         constant_AST = currentAST.root;
/* 5756:4698 */         break;
/* 5757:     */       default: 
/* 5758:4702 */         throw new NoViableAltException(_t);
/* 5759:     */       }
/* 5760:     */     }
/* 5761:     */     catch (RecognitionException ex)
/* 5762:     */     {
/* 5763:4707 */       reportError(ex);
/* 5764:4708 */       if (_t != null) {
/* 5765:4708 */         _t = _t.getNextSibling();
/* 5766:     */       }
/* 5767:     */     }
/* 5768:4710 */     this.returnAST = constant_AST;
/* 5769:4711 */     this._retTree = _t;
/* 5770:     */   }
/* 5771:     */   
/* 5772:     */   public final void parameter(AST _t)
/* 5773:     */     throws RecognitionException
/* 5774:     */   {
/* 5775:4716 */     AST parameter_AST_in = _t == ASTNULL ? null : _t;
/* 5776:4717 */     this.returnAST = null;
/* 5777:4718 */     ASTPair currentAST = new ASTPair();
/* 5778:4719 */     AST parameter_AST = null;
/* 5779:4720 */     AST c = null;
/* 5780:4721 */     AST c_AST = null;
/* 5781:4722 */     AST a_AST = null;
/* 5782:4723 */     AST a = null;
/* 5783:4724 */     AST p = null;
/* 5784:4725 */     AST p_AST = null;
/* 5785:4726 */     AST n = null;
/* 5786:4727 */     AST n_AST = null;
/* 5787:     */     try
/* 5788:     */     {
/* 5789:4730 */       if (_t == null) {
/* 5790:4730 */         _t = ASTNULL;
/* 5791:     */       }
/* 5792:4731 */       switch (_t.getType())
/* 5793:     */       {
/* 5794:     */       case 122: 
/* 5795:4734 */         AST __t185 = _t;
/* 5796:4735 */         c = _t == ASTNULL ? null : _t;
/* 5797:4736 */         AST c_AST_in = null;
/* 5798:4737 */         c_AST = this.astFactory.create(c);
/* 5799:4738 */         ASTPair __currentAST185 = currentAST.copy();
/* 5800:4739 */         currentAST.root = currentAST.child;
/* 5801:4740 */         currentAST.child = null;
/* 5802:4741 */         match(_t, 122);
/* 5803:4742 */         _t = _t.getFirstChild();
/* 5804:4743 */         a = _t == ASTNULL ? null : _t;
/* 5805:4744 */         identifier(_t);
/* 5806:4745 */         _t = this._retTree;
/* 5807:4746 */         a_AST = this.returnAST;
/* 5808:4747 */         currentAST = __currentAST185;
/* 5809:4748 */         _t = __t185;
/* 5810:4749 */         _t = _t.getNextSibling();
/* 5811:4750 */         parameter_AST = currentAST.root;
/* 5812:     */         
/* 5813:     */ 
/* 5814:4753 */         parameter_AST = generateNamedParameter(c, a);
/* 5815:     */         
/* 5816:     */ 
/* 5817:     */ 
/* 5818:4757 */         currentAST.root = parameter_AST;
/* 5819:4758 */         currentAST.child = ((parameter_AST != null) && (parameter_AST.getFirstChild() != null) ? parameter_AST.getFirstChild() : parameter_AST);
/* 5820:     */         
/* 5821:4760 */         currentAST.advanceChildToEnd();
/* 5822:4761 */         break;
/* 5823:     */       case 123: 
/* 5824:4765 */         AST __t186 = _t;
/* 5825:4766 */         p = _t == ASTNULL ? null : _t;
/* 5826:4767 */         AST p_AST_in = null;
/* 5827:4768 */         p_AST = this.astFactory.create(p);
/* 5828:4769 */         ASTPair __currentAST186 = currentAST.copy();
/* 5829:4770 */         currentAST.root = currentAST.child;
/* 5830:4771 */         currentAST.child = null;
/* 5831:4772 */         match(_t, 123);
/* 5832:4773 */         _t = _t.getFirstChild();
/* 5833:4775 */         if (_t == null) {
/* 5834:4775 */           _t = ASTNULL;
/* 5835:     */         }
/* 5836:4776 */         switch (_t.getType())
/* 5837:     */         {
/* 5838:     */         case 124: 
/* 5839:4779 */           n = _t;
/* 5840:4780 */           AST n_AST_in = null;
/* 5841:4781 */           n_AST = this.astFactory.create(n);
/* 5842:4782 */           match(_t, 124);
/* 5843:4783 */           _t = _t.getNextSibling();
/* 5844:4784 */           break;
/* 5845:     */         case 3: 
/* 5846:     */           break;
/* 5847:     */         default: 
/* 5848:4792 */           throw new NoViableAltException(_t);
/* 5849:     */         }
/* 5850:4796 */         currentAST = __currentAST186;
/* 5851:4797 */         _t = __t186;
/* 5852:4798 */         _t = _t.getNextSibling();
/* 5853:4799 */         parameter_AST = currentAST.root;
/* 5854:4801 */         if (n != null) {
/* 5855:4803 */           parameter_AST = generateNamedParameter(p, n);
/* 5856:     */         } else {
/* 5857:4808 */           parameter_AST = generatePositionalParameter(p);
/* 5858:     */         }
/* 5859:4813 */         currentAST.root = parameter_AST;
/* 5860:4814 */         currentAST.child = ((parameter_AST != null) && (parameter_AST.getFirstChild() != null) ? parameter_AST.getFirstChild() : parameter_AST);
/* 5861:     */         
/* 5862:4816 */         currentAST.advanceChildToEnd();
/* 5863:4817 */         break;
/* 5864:     */       default: 
/* 5865:4821 */         throw new NoViableAltException(_t);
/* 5866:     */       }
/* 5867:     */     }
/* 5868:     */     catch (RecognitionException ex)
/* 5869:     */     {
/* 5870:4826 */       reportError(ex);
/* 5871:4827 */       if (_t != null) {
/* 5872:4827 */         _t = _t.getNextSibling();
/* 5873:     */       }
/* 5874:     */     }
/* 5875:4829 */     this.returnAST = parameter_AST;
/* 5876:4830 */     this._retTree = _t;
/* 5877:     */   }
/* 5878:     */   
/* 5879:     */   public final void caseExpr(AST _t)
/* 5880:     */     throws RecognitionException
/* 5881:     */   {
/* 5882:4835 */     AST caseExpr_AST_in = _t == ASTNULL ? null : _t;
/* 5883:4836 */     this.returnAST = null;
/* 5884:4837 */     ASTPair currentAST = new ASTPair();
/* 5885:4838 */     AST caseExpr_AST = null;
/* 5886:     */     try
/* 5887:     */     {
/* 5888:4841 */       if (_t == null) {
/* 5889:4841 */         _t = ASTNULL;
/* 5890:     */       }
/* 5891:4842 */       switch (_t.getType())
/* 5892:     */       {
/* 5893:     */       case 54: 
/* 5894:4845 */         AST __t144 = _t;
/* 5895:4846 */         AST tmp76_AST = null;
/* 5896:4847 */         AST tmp76_AST_in = null;
/* 5897:4848 */         tmp76_AST = this.astFactory.create(_t);
/* 5898:4849 */         tmp76_AST_in = _t;
/* 5899:4850 */         this.astFactory.addASTChild(currentAST, tmp76_AST);
/* 5900:4851 */         ASTPair __currentAST144 = currentAST.copy();
/* 5901:4852 */         currentAST.root = currentAST.child;
/* 5902:4853 */         currentAST.child = null;
/* 5903:4854 */         match(_t, 54);
/* 5904:4855 */         _t = _t.getFirstChild();
/* 5905:4856 */         this.inCase = true;
/* 5906:     */         
/* 5907:4858 */         int _cnt147 = 0;
/* 5908:     */         for (;;)
/* 5909:     */         {
/* 5910:4861 */           if (_t == null) {
/* 5911:4861 */             _t = ASTNULL;
/* 5912:     */           }
/* 5913:4862 */           if (_t.getType() == 58)
/* 5914:     */           {
/* 5915:4863 */             AST __t146 = _t;
/* 5916:4864 */             AST tmp77_AST = null;
/* 5917:4865 */             AST tmp77_AST_in = null;
/* 5918:4866 */             tmp77_AST = this.astFactory.create(_t);
/* 5919:4867 */             tmp77_AST_in = _t;
/* 5920:4868 */             this.astFactory.addASTChild(currentAST, tmp77_AST);
/* 5921:4869 */             ASTPair __currentAST146 = currentAST.copy();
/* 5922:4870 */             currentAST.root = currentAST.child;
/* 5923:4871 */             currentAST.child = null;
/* 5924:4872 */             match(_t, 58);
/* 5925:4873 */             _t = _t.getFirstChild();
/* 5926:4874 */             logicalExpr(_t);
/* 5927:4875 */             _t = this._retTree;
/* 5928:4876 */             this.astFactory.addASTChild(currentAST, this.returnAST);
/* 5929:4877 */             expr(_t);
/* 5930:4878 */             _t = this._retTree;
/* 5931:4879 */             this.astFactory.addASTChild(currentAST, this.returnAST);
/* 5932:4880 */             currentAST = __currentAST146;
/* 5933:4881 */             _t = __t146;
/* 5934:4882 */             _t = _t.getNextSibling();
/* 5935:     */           }
/* 5936:     */           else
/* 5937:     */           {
/* 5938:4885 */             if (_cnt147 >= 1) {
/* 5939:     */               break;
/* 5940:     */             }
/* 5941:4885 */             throw new NoViableAltException(_t);
/* 5942:     */           }
/* 5943:4888 */           _cnt147++;
/* 5944:     */         }
/* 5945:4892 */         if (_t == null) {
/* 5946:4892 */           _t = ASTNULL;
/* 5947:     */         }
/* 5948:4893 */         switch (_t.getType())
/* 5949:     */         {
/* 5950:     */         case 56: 
/* 5951:4896 */           AST __t149 = _t;
/* 5952:4897 */           AST tmp78_AST = null;
/* 5953:4898 */           AST tmp78_AST_in = null;
/* 5954:4899 */           tmp78_AST = this.astFactory.create(_t);
/* 5955:4900 */           tmp78_AST_in = _t;
/* 5956:4901 */           this.astFactory.addASTChild(currentAST, tmp78_AST);
/* 5957:4902 */           ASTPair __currentAST149 = currentAST.copy();
/* 5958:4903 */           currentAST.root = currentAST.child;
/* 5959:4904 */           currentAST.child = null;
/* 5960:4905 */           match(_t, 56);
/* 5961:4906 */           _t = _t.getFirstChild();
/* 5962:4907 */           expr(_t);
/* 5963:4908 */           _t = this._retTree;
/* 5964:4909 */           this.astFactory.addASTChild(currentAST, this.returnAST);
/* 5965:4910 */           currentAST = __currentAST149;
/* 5966:4911 */           _t = __t149;
/* 5967:4912 */           _t = _t.getNextSibling();
/* 5968:4913 */           break;
/* 5969:     */         case 3: 
/* 5970:     */           break;
/* 5971:     */         default: 
/* 5972:4921 */           throw new NoViableAltException(_t);
/* 5973:     */         }
/* 5974:4925 */         currentAST = __currentAST144;
/* 5975:4926 */         _t = __t144;
/* 5976:4927 */         _t = _t.getNextSibling();
/* 5977:4928 */         this.inCase = false;
/* 5978:4929 */         caseExpr_AST = currentAST.root;
/* 5979:4930 */         break;
/* 5980:     */       case 74: 
/* 5981:4934 */         AST __t150 = _t;
/* 5982:4935 */         AST tmp79_AST = null;
/* 5983:4936 */         AST tmp79_AST_in = null;
/* 5984:4937 */         tmp79_AST = this.astFactory.create(_t);
/* 5985:4938 */         tmp79_AST_in = _t;
/* 5986:4939 */         this.astFactory.addASTChild(currentAST, tmp79_AST);
/* 5987:4940 */         ASTPair __currentAST150 = currentAST.copy();
/* 5988:4941 */         currentAST.root = currentAST.child;
/* 5989:4942 */         currentAST.child = null;
/* 5990:4943 */         match(_t, 74);
/* 5991:4944 */         _t = _t.getFirstChild();
/* 5992:4945 */         this.inCase = true;
/* 5993:4946 */         expr(_t);
/* 5994:4947 */         _t = this._retTree;
/* 5995:4948 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 5996:     */         
/* 5997:4950 */         int _cnt153 = 0;
/* 5998:     */         for (;;)
/* 5999:     */         {
/* 6000:4953 */           if (_t == null) {
/* 6001:4953 */             _t = ASTNULL;
/* 6002:     */           }
/* 6003:4954 */           if (_t.getType() == 58)
/* 6004:     */           {
/* 6005:4955 */             AST __t152 = _t;
/* 6006:4956 */             AST tmp80_AST = null;
/* 6007:4957 */             AST tmp80_AST_in = null;
/* 6008:4958 */             tmp80_AST = this.astFactory.create(_t);
/* 6009:4959 */             tmp80_AST_in = _t;
/* 6010:4960 */             this.astFactory.addASTChild(currentAST, tmp80_AST);
/* 6011:4961 */             ASTPair __currentAST152 = currentAST.copy();
/* 6012:4962 */             currentAST.root = currentAST.child;
/* 6013:4963 */             currentAST.child = null;
/* 6014:4964 */             match(_t, 58);
/* 6015:4965 */             _t = _t.getFirstChild();
/* 6016:4966 */             expr(_t);
/* 6017:4967 */             _t = this._retTree;
/* 6018:4968 */             this.astFactory.addASTChild(currentAST, this.returnAST);
/* 6019:4969 */             expr(_t);
/* 6020:4970 */             _t = this._retTree;
/* 6021:4971 */             this.astFactory.addASTChild(currentAST, this.returnAST);
/* 6022:4972 */             currentAST = __currentAST152;
/* 6023:4973 */             _t = __t152;
/* 6024:4974 */             _t = _t.getNextSibling();
/* 6025:     */           }
/* 6026:     */           else
/* 6027:     */           {
/* 6028:4977 */             if (_cnt153 >= 1) {
/* 6029:     */               break;
/* 6030:     */             }
/* 6031:4977 */             throw new NoViableAltException(_t);
/* 6032:     */           }
/* 6033:4980 */           _cnt153++;
/* 6034:     */         }
/* 6035:4984 */         if (_t == null) {
/* 6036:4984 */           _t = ASTNULL;
/* 6037:     */         }
/* 6038:4985 */         switch (_t.getType())
/* 6039:     */         {
/* 6040:     */         case 56: 
/* 6041:4988 */           AST __t155 = _t;
/* 6042:4989 */           AST tmp81_AST = null;
/* 6043:4990 */           AST tmp81_AST_in = null;
/* 6044:4991 */           tmp81_AST = this.astFactory.create(_t);
/* 6045:4992 */           tmp81_AST_in = _t;
/* 6046:4993 */           this.astFactory.addASTChild(currentAST, tmp81_AST);
/* 6047:4994 */           ASTPair __currentAST155 = currentAST.copy();
/* 6048:4995 */           currentAST.root = currentAST.child;
/* 6049:4996 */           currentAST.child = null;
/* 6050:4997 */           match(_t, 56);
/* 6051:4998 */           _t = _t.getFirstChild();
/* 6052:4999 */           expr(_t);
/* 6053:5000 */           _t = this._retTree;
/* 6054:5001 */           this.astFactory.addASTChild(currentAST, this.returnAST);
/* 6055:5002 */           currentAST = __currentAST155;
/* 6056:5003 */           _t = __t155;
/* 6057:5004 */           _t = _t.getNextSibling();
/* 6058:5005 */           break;
/* 6059:     */         case 3: 
/* 6060:     */           break;
/* 6061:     */         default: 
/* 6062:5013 */           throw new NoViableAltException(_t);
/* 6063:     */         }
/* 6064:5017 */         currentAST = __currentAST150;
/* 6065:5018 */         _t = __t150;
/* 6066:5019 */         _t = _t.getNextSibling();
/* 6067:5020 */         this.inCase = false;
/* 6068:5021 */         caseExpr_AST = currentAST.root;
/* 6069:5022 */         break;
/* 6070:     */       default: 
/* 6071:5026 */         throw new NoViableAltException(_t);
/* 6072:     */       }
/* 6073:     */     }
/* 6074:     */     catch (RecognitionException ex)
/* 6075:     */     {
/* 6076:5031 */       reportError(ex);
/* 6077:5032 */       if (_t != null) {
/* 6078:5032 */         _t = _t.getNextSibling();
/* 6079:     */       }
/* 6080:     */     }
/* 6081:5034 */     this.returnAST = caseExpr_AST;
/* 6082:5035 */     this._retTree = _t;
/* 6083:     */   }
/* 6084:     */   
/* 6085:     */   public final void addrExprLhs(AST _t)
/* 6086:     */     throws RecognitionException
/* 6087:     */   {
/* 6088:5040 */     AST addrExprLhs_AST_in = _t == ASTNULL ? null : _t;
/* 6089:5041 */     this.returnAST = null;
/* 6090:5042 */     ASTPair currentAST = new ASTPair();
/* 6091:5043 */     AST addrExprLhs_AST = null;
/* 6092:     */     try
/* 6093:     */     {
/* 6094:5046 */       addrExpr(_t, false);
/* 6095:5047 */       _t = this._retTree;
/* 6096:5048 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/* 6097:5049 */       addrExprLhs_AST = currentAST.root;
/* 6098:     */     }
/* 6099:     */     catch (RecognitionException ex)
/* 6100:     */     {
/* 6101:5052 */       reportError(ex);
/* 6102:5053 */       if (_t != null) {
/* 6103:5053 */         _t = _t.getNextSibling();
/* 6104:     */       }
/* 6105:     */     }
/* 6106:5055 */     this.returnAST = addrExprLhs_AST;
/* 6107:5056 */     this._retTree = _t;
/* 6108:     */   }
/* 6109:     */   
/* 6110:     */   public final void propertyName(AST _t)
/* 6111:     */     throws RecognitionException
/* 6112:     */   {
/* 6113:5061 */     AST propertyName_AST_in = _t == ASTNULL ? null : _t;
/* 6114:5062 */     this.returnAST = null;
/* 6115:5063 */     ASTPair currentAST = new ASTPair();
/* 6116:5064 */     AST propertyName_AST = null;
/* 6117:     */     try
/* 6118:     */     {
/* 6119:5067 */       if (_t == null) {
/* 6120:5067 */         _t = ASTNULL;
/* 6121:     */       }
/* 6122:5068 */       switch (_t.getType())
/* 6123:     */       {
/* 6124:     */       case 93: 
/* 6125:     */       case 126: 
/* 6126:5072 */         identifier(_t);
/* 6127:5073 */         _t = this._retTree;
/* 6128:5074 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 6129:5075 */         propertyName_AST = currentAST.root;
/* 6130:5076 */         break;
/* 6131:     */       case 11: 
/* 6132:5080 */         AST tmp82_AST = null;
/* 6133:5081 */         AST tmp82_AST_in = null;
/* 6134:5082 */         tmp82_AST = this.astFactory.create(_t);
/* 6135:5083 */         tmp82_AST_in = _t;
/* 6136:5084 */         this.astFactory.addASTChild(currentAST, tmp82_AST);
/* 6137:5085 */         match(_t, 11);
/* 6138:5086 */         _t = _t.getNextSibling();
/* 6139:5087 */         propertyName_AST = currentAST.root;
/* 6140:5088 */         break;
/* 6141:     */       case 17: 
/* 6142:5092 */         AST tmp83_AST = null;
/* 6143:5093 */         AST tmp83_AST_in = null;
/* 6144:5094 */         tmp83_AST = this.astFactory.create(_t);
/* 6145:5095 */         tmp83_AST_in = _t;
/* 6146:5096 */         this.astFactory.addASTChild(currentAST, tmp83_AST);
/* 6147:5097 */         match(_t, 17);
/* 6148:5098 */         _t = _t.getNextSibling();
/* 6149:5099 */         propertyName_AST = currentAST.root;
/* 6150:5100 */         break;
/* 6151:     */       case 27: 
/* 6152:5104 */         AST tmp84_AST = null;
/* 6153:5105 */         AST tmp84_AST_in = null;
/* 6154:5106 */         tmp84_AST = this.astFactory.create(_t);
/* 6155:5107 */         tmp84_AST_in = _t;
/* 6156:5108 */         this.astFactory.addASTChild(currentAST, tmp84_AST);
/* 6157:5109 */         match(_t, 27);
/* 6158:5110 */         _t = _t.getNextSibling();
/* 6159:5111 */         propertyName_AST = currentAST.root;
/* 6160:5112 */         break;
/* 6161:     */       default: 
/* 6162:5116 */         throw new NoViableAltException(_t);
/* 6163:     */       }
/* 6164:     */     }
/* 6165:     */     catch (RecognitionException ex)
/* 6166:     */     {
/* 6167:5121 */       reportError(ex);
/* 6168:5122 */       if (_t != null) {
/* 6169:5122 */         _t = _t.getNextSibling();
/* 6170:     */       }
/* 6171:     */     }
/* 6172:5124 */     this.returnAST = propertyName_AST;
/* 6173:5125 */     this._retTree = _t;
/* 6174:     */   }
/* 6175:     */   
/* 6176:     */   public final void mapComponentReference(AST _t)
/* 6177:     */     throws RecognitionException
/* 6178:     */   {
/* 6179:5130 */     AST mapComponentReference_AST_in = _t == ASTNULL ? null : _t;
/* 6180:5131 */     this.returnAST = null;
/* 6181:5132 */     ASTPair currentAST = new ASTPair();
/* 6182:5133 */     AST mapComponentReference_AST = null;
/* 6183:     */     try
/* 6184:     */     {
/* 6185:5136 */       if (_t == null) {
/* 6186:5136 */         _t = ASTNULL;
/* 6187:     */       }
/* 6188:5137 */       switch (_t.getType())
/* 6189:     */       {
/* 6190:     */       case 68: 
/* 6191:5140 */         AST __t180 = _t;
/* 6192:5141 */         AST tmp85_AST = null;
/* 6193:5142 */         AST tmp85_AST_in = null;
/* 6194:5143 */         tmp85_AST = this.astFactory.create(_t);
/* 6195:5144 */         tmp85_AST_in = _t;
/* 6196:5145 */         this.astFactory.addASTChild(currentAST, tmp85_AST);
/* 6197:5146 */         ASTPair __currentAST180 = currentAST.copy();
/* 6198:5147 */         currentAST.root = currentAST.child;
/* 6199:5148 */         currentAST.child = null;
/* 6200:5149 */         match(_t, 68);
/* 6201:5150 */         _t = _t.getFirstChild();
/* 6202:5151 */         mapPropertyExpression(_t);
/* 6203:5152 */         _t = this._retTree;
/* 6204:5153 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 6205:5154 */         currentAST = __currentAST180;
/* 6206:5155 */         _t = __t180;
/* 6207:5156 */         _t = _t.getNextSibling();
/* 6208:5157 */         mapComponentReference_AST = currentAST.root;
/* 6209:5158 */         break;
/* 6210:     */       case 69: 
/* 6211:5162 */         AST __t181 = _t;
/* 6212:5163 */         AST tmp86_AST = null;
/* 6213:5164 */         AST tmp86_AST_in = null;
/* 6214:5165 */         tmp86_AST = this.astFactory.create(_t);
/* 6215:5166 */         tmp86_AST_in = _t;
/* 6216:5167 */         this.astFactory.addASTChild(currentAST, tmp86_AST);
/* 6217:5168 */         ASTPair __currentAST181 = currentAST.copy();
/* 6218:5169 */         currentAST.root = currentAST.child;
/* 6219:5170 */         currentAST.child = null;
/* 6220:5171 */         match(_t, 69);
/* 6221:5172 */         _t = _t.getFirstChild();
/* 6222:5173 */         mapPropertyExpression(_t);
/* 6223:5174 */         _t = this._retTree;
/* 6224:5175 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 6225:5176 */         currentAST = __currentAST181;
/* 6226:5177 */         _t = __t181;
/* 6227:5178 */         _t = _t.getNextSibling();
/* 6228:5179 */         mapComponentReference_AST = currentAST.root;
/* 6229:5180 */         break;
/* 6230:     */       case 70: 
/* 6231:5184 */         AST __t182 = _t;
/* 6232:5185 */         AST tmp87_AST = null;
/* 6233:5186 */         AST tmp87_AST_in = null;
/* 6234:5187 */         tmp87_AST = this.astFactory.create(_t);
/* 6235:5188 */         tmp87_AST_in = _t;
/* 6236:5189 */         this.astFactory.addASTChild(currentAST, tmp87_AST);
/* 6237:5190 */         ASTPair __currentAST182 = currentAST.copy();
/* 6238:5191 */         currentAST.root = currentAST.child;
/* 6239:5192 */         currentAST.child = null;
/* 6240:5193 */         match(_t, 70);
/* 6241:5194 */         _t = _t.getFirstChild();
/* 6242:5195 */         mapPropertyExpression(_t);
/* 6243:5196 */         _t = this._retTree;
/* 6244:5197 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 6245:5198 */         currentAST = __currentAST182;
/* 6246:5199 */         _t = __t182;
/* 6247:5200 */         _t = _t.getNextSibling();
/* 6248:5201 */         mapComponentReference_AST = currentAST.root;
/* 6249:5202 */         break;
/* 6250:     */       default: 
/* 6251:5206 */         throw new NoViableAltException(_t);
/* 6252:     */       }
/* 6253:     */     }
/* 6254:     */     catch (RecognitionException ex)
/* 6255:     */     {
/* 6256:5211 */       reportError(ex);
/* 6257:5212 */       if (_t != null) {
/* 6258:5212 */         _t = _t.getNextSibling();
/* 6259:     */       }
/* 6260:     */     }
/* 6261:5214 */     this.returnAST = mapComponentReference_AST;
/* 6262:5215 */     this._retTree = _t;
/* 6263:     */   }
/* 6264:     */   
/* 6265:     */   public final void propertyRefLhs(AST _t)
/* 6266:     */     throws RecognitionException
/* 6267:     */   {
/* 6268:5220 */     AST propertyRefLhs_AST_in = _t == ASTNULL ? null : _t;
/* 6269:5221 */     this.returnAST = null;
/* 6270:5222 */     ASTPair currentAST = new ASTPair();
/* 6271:5223 */     AST propertyRefLhs_AST = null;
/* 6272:     */     try
/* 6273:     */     {
/* 6274:5226 */       propertyRef(_t);
/* 6275:5227 */       _t = this._retTree;
/* 6276:5228 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/* 6277:5229 */       propertyRefLhs_AST = currentAST.root;
/* 6278:     */     }
/* 6279:     */     catch (RecognitionException ex)
/* 6280:     */     {
/* 6281:5232 */       reportError(ex);
/* 6282:5233 */       if (_t != null) {
/* 6283:5233 */         _t = _t.getNextSibling();
/* 6284:     */       }
/* 6285:     */     }
/* 6286:5235 */     this.returnAST = propertyRefLhs_AST;
/* 6287:5236 */     this._retTree = _t;
/* 6288:     */   }
/* 6289:     */   
/* 6290:     */   public final void mapPropertyExpression(AST _t)
/* 6291:     */     throws RecognitionException
/* 6292:     */   {
/* 6293:5241 */     AST mapPropertyExpression_AST_in = _t == ASTNULL ? null : _t;
/* 6294:5242 */     this.returnAST = null;
/* 6295:5243 */     ASTPair currentAST = new ASTPair();
/* 6296:5244 */     AST mapPropertyExpression_AST = null;
/* 6297:5245 */     AST e_AST = null;
/* 6298:5246 */     AST e = null;
/* 6299:     */     try
/* 6300:     */     {
/* 6301:5249 */       e = _t == ASTNULL ? null : _t;
/* 6302:5250 */       expr(_t);
/* 6303:5251 */       _t = this._retTree;
/* 6304:5252 */       e_AST = this.returnAST;
/* 6305:5253 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/* 6306:     */       
/* 6307:5255 */       validateMapPropertyExpression(e_AST);
/* 6308:     */       
/* 6309:5257 */       mapPropertyExpression_AST = currentAST.root;
/* 6310:     */     }
/* 6311:     */     catch (RecognitionException ex)
/* 6312:     */     {
/* 6313:5260 */       reportError(ex);
/* 6314:5261 */       if (_t != null) {
/* 6315:5261 */         _t = _t.getNextSibling();
/* 6316:     */       }
/* 6317:     */     }
/* 6318:5263 */     this.returnAST = mapPropertyExpression_AST;
/* 6319:5264 */     this._retTree = _t;
/* 6320:     */   }
/* 6321:     */   
/* 6322:     */   public final void numericInteger(AST _t)
/* 6323:     */     throws RecognitionException
/* 6324:     */   {
/* 6325:5269 */     AST numericInteger_AST_in = _t == ASTNULL ? null : _t;
/* 6326:5270 */     this.returnAST = null;
/* 6327:5271 */     ASTPair currentAST = new ASTPair();
/* 6328:5272 */     AST numericInteger_AST = null;
/* 6329:     */     try
/* 6330:     */     {
/* 6331:5275 */       AST tmp88_AST = null;
/* 6332:5276 */       AST tmp88_AST_in = null;
/* 6333:5277 */       tmp88_AST = this.astFactory.create(_t);
/* 6334:5278 */       tmp88_AST_in = _t;
/* 6335:5279 */       this.astFactory.addASTChild(currentAST, tmp88_AST);
/* 6336:5280 */       match(_t, 124);
/* 6337:5281 */       _t = _t.getNextSibling();
/* 6338:5282 */       numericInteger_AST = currentAST.root;
/* 6339:     */     }
/* 6340:     */     catch (RecognitionException ex)
/* 6341:     */     {
/* 6342:5285 */       reportError(ex);
/* 6343:5286 */       if (_t != null) {
/* 6344:5286 */         _t = _t.getNextSibling();
/* 6345:     */       }
/* 6346:     */     }
/* 6347:5288 */     this.returnAST = numericInteger_AST;
/* 6348:5289 */     this._retTree = _t;
/* 6349:     */   }
/* 6350:     */   
/* 6351:5293 */   public static final String[] _tokenNames = { "<0>", "EOF", "<2>", "NULL_TREE_LOOKAHEAD", "\"all\"", "\"any\"", "\"and\"", "\"as\"", "\"asc\"", "\"avg\"", "\"between\"", "\"class\"", "\"count\"", "\"delete\"", "\"desc\"", "DOT", "\"distinct\"", "\"elements\"", "\"escape\"", "\"exists\"", "\"false\"", "\"fetch\"", "\"from\"", "\"full\"", "\"group\"", "\"having\"", "\"in\"", "\"indices\"", "\"inner\"", "\"insert\"", "\"into\"", "\"is\"", "\"join\"", "\"left\"", "\"like\"", "\"max\"", "\"min\"", "\"new\"", "\"not\"", "\"null\"", "\"or\"", "\"order\"", "\"outer\"", "\"properties\"", "\"right\"", "\"select\"", "\"set\"", "\"some\"", "\"sum\"", "\"true\"", "\"union\"", "\"update\"", "\"versioned\"", "\"where\"", "\"case\"", "\"end\"", "\"else\"", "\"then\"", "\"when\"", "\"on\"", "\"with\"", "\"both\"", "\"empty\"", "\"leading\"", "\"member\"", "\"object\"", "\"of\"", "\"trailing\"", "KEY", "VALUE", "ENTRY", "AGGREGATE", "ALIAS", "CONSTRUCTOR", "CASE2", "EXPR_LIST", "FILTER_ENTITY", "IN_LIST", "INDEX_OP", "IS_NOT_NULL", "IS_NULL", "METHOD_CALL", "NOT_BETWEEN", "NOT_IN", "NOT_LIKE", "ORDER_ELEMENT", "QUERY", "RANGE", "ROW_STAR", "SELECT_FROM", "UNARY_MINUS", "UNARY_PLUS", "VECTOR_EXPR", "WEIRD_IDENT", "CONSTANT", "NUM_DOUBLE", "NUM_FLOAT", "NUM_LONG", "NUM_BIG_INTEGER", "NUM_BIG_DECIMAL", "JAVA_CONSTANT", "COMMA", "EQ", "OPEN", "CLOSE", "\"by\"", "\"ascending\"", "\"descending\"", "NE", "SQL_NE", "LT", "GT", "LE", "GE", "CONCAT", "PLUS", "MINUS", "STAR", "DIV", "MOD", "OPEN_BRACKET", "CLOSE_BRACKET", "COLON", "PARAM", "NUM_INT", "QUOTED_STRING", "IDENT", "ID_START_LETTER", "ID_LETTER", "ESCqs", "WS", "HEX_DIGIT", "EXPONENT", "FLOAT_SUFFIX", "FROM_FRAGMENT", "IMPLIED_FROM", "JOIN_FRAGMENT", "SELECT_CLAUSE", "LEFT_OUTER", "RIGHT_OUTER", "ALIAS_REF", "PROPERTY_REF", "SQL_TOKEN", "SELECT_COLUMNS", "SELECT_EXPR", "THETA_JOINS", "FILTERS", "METHOD_NAME", "NAMED_PARAM", "BOGUS", "RESULT_VARIABLE_REF" };
/* 6352:     */   
/* 6353:     */   private static final long[] mk_tokenSet_0()
/* 6354:     */   {
/* 6355:5448 */     long[] data = { 18577898219802624L, 9004947591091340416L, 0L, 0L };
/* 6356:5449 */     return data;
/* 6357:     */   }
/* 6358:     */   
/* 6359:5451 */   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
/* 6360:     */   
/* 6361:     */   private static final long[] mk_tokenSet_1()
/* 6362:     */   {
/* 6363:5453 */     long[] data = { 18718635708158000L, 9004947591095534720L, 0L, 0L };
/* 6364:5454 */     return data;
/* 6365:     */   }
/* 6366:     */   
/* 6367:5456 */   public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
/* 6368:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.antlr.HqlSqlBaseWalker
 * JD-Core Version:    0.7.0.1
 */