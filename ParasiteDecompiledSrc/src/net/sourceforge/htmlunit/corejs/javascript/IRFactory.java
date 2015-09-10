/*    1:     */ package net.sourceforge.htmlunit.corejs.javascript;
/*    2:     */ 
/*    3:     */ import java.util.ArrayList;
/*    4:     */ import java.util.List;
/*    5:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.ArrayComprehension;
/*    6:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.ArrayComprehensionLoop;
/*    7:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.ArrayLiteral;
/*    8:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.Assignment;
/*    9:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.AstNode;
/*   10:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.AstRoot;
/*   11:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.Block;
/*   12:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.BreakStatement;
/*   13:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.CatchClause;
/*   14:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.ConditionalExpression;
/*   15:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.ContinueStatement;
/*   16:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.DestructuringForm;
/*   17:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.DoLoop;
/*   18:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.ElementGet;
/*   19:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.EmptyExpression;
/*   20:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.ExpressionStatement;
/*   21:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.ForInLoop;
/*   22:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.ForLoop;
/*   23:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.FunctionCall;
/*   24:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.FunctionNode;
/*   25:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.IfStatement;
/*   26:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.InfixExpression;
/*   27:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.Jump;
/*   28:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.Label;
/*   29:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.LabeledStatement;
/*   30:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.LetNode;
/*   31:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.Loop;
/*   32:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.Name;
/*   33:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.NewExpression;
/*   34:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.NumberLiteral;
/*   35:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.ObjectLiteral;
/*   36:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.ObjectProperty;
/*   37:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.ParenthesizedExpression;
/*   38:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.PropertyGet;
/*   39:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.RegExpLiteral;
/*   40:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.ReturnStatement;
/*   41:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.Scope;
/*   42:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.ScriptNode;
/*   43:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.StringLiteral;
/*   44:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.SwitchCase;
/*   45:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.SwitchStatement;
/*   46:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.Symbol;
/*   47:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.ThrowStatement;
/*   48:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.TryStatement;
/*   49:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.UnaryExpression;
/*   50:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.VariableDeclaration;
/*   51:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.VariableInitializer;
/*   52:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.WhileLoop;
/*   53:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.WithStatement;
/*   54:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.XmlDotQuery;
/*   55:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.XmlElemRef;
/*   56:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.XmlExpression;
/*   57:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.XmlFragment;
/*   58:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.XmlLiteral;
/*   59:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.XmlMemberGet;
/*   60:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.XmlPropRef;
/*   61:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.XmlRef;
/*   62:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.XmlString;
/*   63:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.Yield;
/*   64:     */ 
/*   65:     */ public final class IRFactory
/*   66:     */   extends Parser
/*   67:     */ {
/*   68:     */   private static final int LOOP_DO_WHILE = 0;
/*   69:     */   private static final int LOOP_WHILE = 1;
/*   70:     */   private static final int LOOP_FOR = 2;
/*   71:     */   private static final int ALWAYS_TRUE_BOOLEAN = 1;
/*   72:     */   private static final int ALWAYS_FALSE_BOOLEAN = -1;
/*   73:  68 */   private Decompiler decompiler = new Decompiler();
/*   74:     */   
/*   75:     */   public IRFactory() {}
/*   76:     */   
/*   77:     */   public IRFactory(CompilerEnvirons env)
/*   78:     */   {
/*   79:  75 */     this(env, env.getErrorReporter());
/*   80:     */   }
/*   81:     */   
/*   82:     */   public IRFactory(CompilerEnvirons env, ErrorReporter errorReporter)
/*   83:     */   {
/*   84:  79 */     super(env, errorReporter);
/*   85:     */   }
/*   86:     */   
/*   87:     */   public ScriptNode transformTree(AstRoot root)
/*   88:     */   {
/*   89:  87 */     this.currentScriptOrFn = root;
/*   90:  88 */     this.inUseStrictDirective = root.isInStrictMode();
/*   91:  89 */     int sourceStartOffset = this.decompiler.getCurrentOffset();
/*   92:     */     
/*   93:     */ 
/*   94:     */ 
/*   95:     */ 
/*   96:     */ 
/*   97:  95 */     ScriptNode script = (ScriptNode)transform(root);
/*   98:     */     
/*   99:  97 */     int sourceEndOffset = this.decompiler.getCurrentOffset();
/*  100:  98 */     script.setEncodedSourceBounds(sourceStartOffset, sourceEndOffset);
/*  101: 101 */     if (this.compilerEnv.isGeneratingSource()) {
/*  102: 102 */       script.setEncodedSource(this.decompiler.getEncodedSource());
/*  103:     */     }
/*  104: 105 */     this.decompiler = null;
/*  105: 106 */     return script;
/*  106:     */   }
/*  107:     */   
/*  108:     */   public Node transform(AstNode node)
/*  109:     */   {
/*  110: 114 */     switch (node.getType())
/*  111:     */     {
/*  112:     */     case 157: 
/*  113: 116 */       return transformArrayComp((ArrayComprehension)node);
/*  114:     */     case 65: 
/*  115: 118 */       return transformArrayLiteral((ArrayLiteral)node);
/*  116:     */     case 129: 
/*  117: 120 */       return transformBlock(node);
/*  118:     */     case 120: 
/*  119: 122 */       return transformBreak((BreakStatement)node);
/*  120:     */     case 38: 
/*  121: 124 */       return transformFunctionCall((FunctionCall)node);
/*  122:     */     case 121: 
/*  123: 126 */       return transformContinue((ContinueStatement)node);
/*  124:     */     case 118: 
/*  125: 128 */       return transformDoLoop((DoLoop)node);
/*  126:     */     case 128: 
/*  127: 130 */       return node;
/*  128:     */     case 119: 
/*  129: 132 */       if ((node instanceof ForInLoop)) {
/*  130: 133 */         return transformForInLoop((ForInLoop)node);
/*  131:     */       }
/*  132: 135 */       return transformForLoop((ForLoop)node);
/*  133:     */     case 109: 
/*  134: 138 */       return transformFunction((FunctionNode)node);
/*  135:     */     case 36: 
/*  136: 140 */       return transformElementGet((ElementGet)node);
/*  137:     */     case 33: 
/*  138: 142 */       return transformPropertyGet((PropertyGet)node);
/*  139:     */     case 102: 
/*  140: 144 */       return transformCondExpr((ConditionalExpression)node);
/*  141:     */     case 112: 
/*  142: 146 */       return transformIf((IfStatement)node);
/*  143:     */     case 42: 
/*  144:     */     case 43: 
/*  145:     */     case 44: 
/*  146:     */     case 45: 
/*  147:     */     case 160: 
/*  148: 153 */       return transformLiteral(node);
/*  149:     */     case 39: 
/*  150: 156 */       return transformName((Name)node);
/*  151:     */     case 40: 
/*  152: 158 */       return transformNumber((NumberLiteral)node);
/*  153:     */     case 30: 
/*  154: 160 */       return transformNewExpr((NewExpression)node);
/*  155:     */     case 66: 
/*  156: 162 */       return transformObjectLiteral((ObjectLiteral)node);
/*  157:     */     case 48: 
/*  158: 164 */       return transformRegExp((RegExpLiteral)node);
/*  159:     */     case 4: 
/*  160: 166 */       return transformReturn((ReturnStatement)node);
/*  161:     */     case 136: 
/*  162: 168 */       return transformScript((ScriptNode)node);
/*  163:     */     case 41: 
/*  164: 170 */       return transformString((StringLiteral)node);
/*  165:     */     case 114: 
/*  166: 172 */       return transformSwitch((SwitchStatement)node);
/*  167:     */     case 50: 
/*  168: 174 */       return transformThrow((ThrowStatement)node);
/*  169:     */     case 81: 
/*  170: 176 */       return transformTry((TryStatement)node);
/*  171:     */     case 117: 
/*  172: 178 */       return transformWhileLoop((WhileLoop)node);
/*  173:     */     case 123: 
/*  174: 180 */       return transformWith((WithStatement)node);
/*  175:     */     case 72: 
/*  176: 182 */       return transformYield((Yield)node);
/*  177:     */     }
/*  178: 184 */     if ((node instanceof ExpressionStatement)) {
/*  179: 185 */       return transformExprStmt((ExpressionStatement)node);
/*  180:     */     }
/*  181: 187 */     if ((node instanceof Assignment)) {
/*  182: 188 */       return transformAssignment((Assignment)node);
/*  183:     */     }
/*  184: 190 */     if ((node instanceof UnaryExpression)) {
/*  185: 191 */       return transformUnary((UnaryExpression)node);
/*  186:     */     }
/*  187: 193 */     if ((node instanceof XmlMemberGet)) {
/*  188: 194 */       return transformXmlMemberGet((XmlMemberGet)node);
/*  189:     */     }
/*  190: 196 */     if ((node instanceof InfixExpression)) {
/*  191: 197 */       return transformInfix((InfixExpression)node);
/*  192:     */     }
/*  193: 199 */     if ((node instanceof VariableDeclaration)) {
/*  194: 200 */       return transformVariables((VariableDeclaration)node);
/*  195:     */     }
/*  196: 202 */     if ((node instanceof ParenthesizedExpression)) {
/*  197: 203 */       return transformParenExpr((ParenthesizedExpression)node);
/*  198:     */     }
/*  199: 205 */     if ((node instanceof LabeledStatement)) {
/*  200: 206 */       return transformLabeledStatement((LabeledStatement)node);
/*  201:     */     }
/*  202: 208 */     if ((node instanceof LetNode)) {
/*  203: 209 */       return transformLetNode((LetNode)node);
/*  204:     */     }
/*  205: 211 */     if ((node instanceof XmlRef)) {
/*  206: 212 */       return transformXmlRef((XmlRef)node);
/*  207:     */     }
/*  208: 214 */     if ((node instanceof XmlLiteral)) {
/*  209: 215 */       return transformXmlLiteral((XmlLiteral)node);
/*  210:     */     }
/*  211: 217 */     throw new IllegalArgumentException("Can't transform: " + node);
/*  212:     */   }
/*  213:     */   
/*  214:     */   private Node transformArrayComp(ArrayComprehension node)
/*  215:     */   {
/*  216: 242 */     int lineno = node.getLineno();
/*  217: 243 */     Scope scopeNode = createScopeNode(157, lineno);
/*  218: 244 */     String arrayName = this.currentScriptOrFn.getNextTempName();
/*  219: 245 */     pushScope(scopeNode);
/*  220:     */     try
/*  221:     */     {
/*  222: 247 */       defineSymbol(153, arrayName, false);
/*  223: 248 */       Node block = new Node(129, lineno);
/*  224: 249 */       Node newArray = createCallOrNew(30, createName("Array"));
/*  225: 250 */       Node init = new Node(133, createAssignment(90, createName(arrayName), newArray), lineno);
/*  226:     */       
/*  227:     */ 
/*  228:     */ 
/*  229:     */ 
/*  230: 255 */       block.addChildToBack(init);
/*  231: 256 */       block.addChildToBack(arrayCompTransformHelper(node, arrayName));
/*  232: 257 */       scopeNode.addChildToBack(block);
/*  233: 258 */       scopeNode.addChildToBack(createName(arrayName));
/*  234: 259 */       return scopeNode;
/*  235:     */     }
/*  236:     */     finally
/*  237:     */     {
/*  238: 261 */       popScope();
/*  239:     */     }
/*  240:     */   }
/*  241:     */   
/*  242:     */   private Node arrayCompTransformHelper(ArrayComprehension node, String arrayName)
/*  243:     */   {
/*  244: 267 */     this.decompiler.addToken(83);
/*  245: 268 */     int lineno = node.getLineno();
/*  246: 269 */     Node expr = transform(node.getResult());
/*  247:     */     
/*  248: 271 */     List<ArrayComprehensionLoop> loops = node.getLoops();
/*  249: 272 */     int numLoops = loops.size();
/*  250:     */     
/*  251:     */ 
/*  252: 275 */     Node[] iterators = new Node[numLoops];
/*  253: 276 */     Node[] iteratedObjs = new Node[numLoops];
/*  254: 278 */     for (int i = 0; i < numLoops; i++)
/*  255:     */     {
/*  256: 279 */       ArrayComprehensionLoop acl = (ArrayComprehensionLoop)loops.get(i);
/*  257: 280 */       this.decompiler.addName(" ");
/*  258: 281 */       this.decompiler.addToken(119);
/*  259: 282 */       if (acl.isForEach()) {
/*  260: 283 */         this.decompiler.addName("each ");
/*  261:     */       }
/*  262: 285 */       this.decompiler.addToken(87);
/*  263:     */       
/*  264: 287 */       AstNode iter = acl.getIterator();
/*  265: 288 */       String name = null;
/*  266: 289 */       if (iter.getType() == 39)
/*  267:     */       {
/*  268: 290 */         name = iter.getString();
/*  269: 291 */         this.decompiler.addName(name);
/*  270:     */       }
/*  271:     */       else
/*  272:     */       {
/*  273: 294 */         decompile(iter);
/*  274: 295 */         name = this.currentScriptOrFn.getNextTempName();
/*  275: 296 */         defineSymbol(87, name, false);
/*  276: 297 */         expr = createBinary(89, createAssignment(90, iter, createName(name)), expr);
/*  277:     */       }
/*  278: 303 */       Node init = createName(name);
/*  279:     */       
/*  280:     */ 
/*  281: 306 */       defineSymbol(153, name, false);
/*  282: 307 */       iterators[i] = init;
/*  283:     */       
/*  284: 309 */       this.decompiler.addToken(52);
/*  285: 310 */       iteratedObjs[i] = transform(acl.getIteratedObject());
/*  286: 311 */       this.decompiler.addToken(88);
/*  287:     */     }
/*  288: 315 */     Node call = createCallOrNew(38, createPropertyGet(createName(arrayName), null, "push", 0));
/*  289:     */     
/*  290:     */ 
/*  291:     */ 
/*  292:     */ 
/*  293: 320 */     Node body = new Node(133, call, lineno);
/*  294: 322 */     if (node.getFilter() != null)
/*  295:     */     {
/*  296: 323 */       this.decompiler.addName(" ");
/*  297: 324 */       this.decompiler.addToken(112);
/*  298: 325 */       this.decompiler.addToken(87);
/*  299: 326 */       body = createIf(transform(node.getFilter()), body, null, lineno);
/*  300: 327 */       this.decompiler.addToken(88);
/*  301:     */     }
/*  302: 331 */     int pushed = 0;
/*  303:     */     try
/*  304:     */     {
/*  305: 333 */       for (int i = numLoops - 1; i >= 0; i--)
/*  306:     */       {
/*  307: 334 */         ArrayComprehensionLoop acl = (ArrayComprehensionLoop)loops.get(i);
/*  308: 335 */         Scope loop = createLoopNode(null, acl.getLineno());
/*  309:     */         
/*  310: 337 */         pushScope(loop);
/*  311: 338 */         pushed++;
/*  312: 339 */         body = createForIn(153, loop, iterators[i], iteratedObjs[i], body, acl.isForEach());
/*  313:     */       }
/*  314:     */     }
/*  315:     */     finally
/*  316:     */     {
/*  317:     */       int i;
/*  318: 347 */       for (int i = 0; i < pushed; i++) {
/*  319: 348 */         popScope();
/*  320:     */       }
/*  321:     */     }
/*  322: 352 */     this.decompiler.addToken(84);
/*  323:     */     
/*  324:     */ 
/*  325:     */ 
/*  326: 356 */     call.addChildToBack(expr);
/*  327: 357 */     return body;
/*  328:     */   }
/*  329:     */   
/*  330:     */   private Node transformArrayLiteral(ArrayLiteral node)
/*  331:     */   {
/*  332: 361 */     if (node.isDestructuring()) {
/*  333: 362 */       return node;
/*  334:     */     }
/*  335: 364 */     this.decompiler.addToken(83);
/*  336: 365 */     List<AstNode> elems = node.getElements();
/*  337: 366 */     Node array = new Node(65);
/*  338: 367 */     List<Integer> skipIndexes = null;
/*  339: 368 */     for (int i = 0; i < elems.size(); i++)
/*  340:     */     {
/*  341: 369 */       AstNode elem = (AstNode)elems.get(i);
/*  342: 370 */       if (elem.getType() != 128)
/*  343:     */       {
/*  344: 371 */         array.addChildToBack(transform(elem));
/*  345:     */       }
/*  346:     */       else
/*  347:     */       {
/*  348: 373 */         if (skipIndexes == null) {
/*  349: 374 */           skipIndexes = new ArrayList();
/*  350:     */         }
/*  351: 376 */         skipIndexes.add(Integer.valueOf(i));
/*  352:     */       }
/*  353: 378 */       if (i < elems.size() - 1) {
/*  354: 379 */         this.decompiler.addToken(89);
/*  355:     */       }
/*  356:     */     }
/*  357: 381 */     this.decompiler.addToken(84);
/*  358: 382 */     array.putIntProp(21, node.getDestructuringLength());
/*  359: 384 */     if (skipIndexes != null)
/*  360:     */     {
/*  361: 385 */       int[] skips = new int[skipIndexes.size()];
/*  362: 386 */       for (int i = 0; i < skipIndexes.size(); i++) {
/*  363: 387 */         skips[i] = ((Integer)skipIndexes.get(i)).intValue();
/*  364:     */       }
/*  365: 388 */       array.putProp(11, skips);
/*  366:     */     }
/*  367: 390 */     return array;
/*  368:     */   }
/*  369:     */   
/*  370:     */   private Node transformAssignment(Assignment node)
/*  371:     */   {
/*  372: 394 */     AstNode left = removeParens(node.getLeft());
/*  373: 395 */     Node target = null;
/*  374: 396 */     if (isDestructuring(left))
/*  375:     */     {
/*  376: 397 */       decompile(left);
/*  377: 398 */       target = left;
/*  378:     */     }
/*  379:     */     else
/*  380:     */     {
/*  381: 400 */       target = transform(left);
/*  382:     */     }
/*  383: 402 */     this.decompiler.addToken(node.getType());
/*  384: 403 */     return createAssignment(node.getType(), target, transform(node.getRight()));
/*  385:     */   }
/*  386:     */   
/*  387:     */   private Node transformBlock(AstNode node)
/*  388:     */   {
/*  389: 409 */     if ((node instanceof Scope)) {
/*  390: 410 */       pushScope((Scope)node);
/*  391:     */     }
/*  392:     */     try
/*  393:     */     {
/*  394: 413 */       List<Node> kids = new ArrayList();
/*  395: 414 */       for (Node kid : node) {
/*  396: 415 */         kids.add(transform((AstNode)kid));
/*  397:     */       }
/*  398: 417 */       node.removeChildren();
/*  399: 418 */       for (Node kid : kids) {
/*  400: 419 */         node.addChildToBack(kid);
/*  401:     */       }
/*  402: 421 */       return node;
/*  403:     */     }
/*  404:     */     finally
/*  405:     */     {
/*  406: 423 */       if ((node instanceof Scope)) {
/*  407: 424 */         popScope();
/*  408:     */       }
/*  409:     */     }
/*  410:     */   }
/*  411:     */   
/*  412:     */   private Node transformBreak(BreakStatement node)
/*  413:     */   {
/*  414: 430 */     this.decompiler.addToken(120);
/*  415: 431 */     if (node.getBreakLabel() != null) {
/*  416: 432 */       this.decompiler.addName(node.getBreakLabel().getIdentifier());
/*  417:     */     }
/*  418: 434 */     this.decompiler.addEOL(82);
/*  419: 435 */     return node;
/*  420:     */   }
/*  421:     */   
/*  422:     */   private Node transformCondExpr(ConditionalExpression node)
/*  423:     */   {
/*  424: 439 */     Node test = transform(node.getTestExpression());
/*  425: 440 */     this.decompiler.addToken(102);
/*  426: 441 */     Node ifTrue = transform(node.getTrueExpression());
/*  427: 442 */     this.decompiler.addToken(103);
/*  428: 443 */     Node ifFalse = transform(node.getFalseExpression());
/*  429: 444 */     return createCondExpr(test, ifTrue, ifFalse);
/*  430:     */   }
/*  431:     */   
/*  432:     */   private Node transformContinue(ContinueStatement node)
/*  433:     */   {
/*  434: 448 */     this.decompiler.addToken(121);
/*  435: 449 */     if (node.getLabel() != null) {
/*  436: 450 */       this.decompiler.addName(node.getLabel().getIdentifier());
/*  437:     */     }
/*  438: 452 */     this.decompiler.addEOL(82);
/*  439: 453 */     return node;
/*  440:     */   }
/*  441:     */   
/*  442:     */   private Node transformDoLoop(DoLoop loop)
/*  443:     */   {
/*  444: 457 */     loop.setType(132);
/*  445: 458 */     pushScope(loop);
/*  446:     */     try
/*  447:     */     {
/*  448: 460 */       this.decompiler.addToken(118);
/*  449: 461 */       this.decompiler.addEOL(85);
/*  450: 462 */       Node body = transform(loop.getBody());
/*  451: 463 */       this.decompiler.addToken(86);
/*  452: 464 */       this.decompiler.addToken(117);
/*  453: 465 */       this.decompiler.addToken(87);
/*  454: 466 */       Node cond = transform(loop.getCondition());
/*  455: 467 */       this.decompiler.addToken(88);
/*  456: 468 */       this.decompiler.addEOL(82);
/*  457: 469 */       return createLoop(loop, 0, body, cond, null, null);
/*  458:     */     }
/*  459:     */     finally
/*  460:     */     {
/*  461: 472 */       popScope();
/*  462:     */     }
/*  463:     */   }
/*  464:     */   
/*  465:     */   private Node transformElementGet(ElementGet node)
/*  466:     */   {
/*  467: 479 */     Node target = transform(node.getTarget());
/*  468: 480 */     this.decompiler.addToken(83);
/*  469: 481 */     Node element = transform(node.getElement());
/*  470: 482 */     this.decompiler.addToken(84);
/*  471: 483 */     return new Node(36, target, element);
/*  472:     */   }
/*  473:     */   
/*  474:     */   private Node transformExprStmt(ExpressionStatement node)
/*  475:     */   {
/*  476: 487 */     Node expr = transform(node.getExpression());
/*  477: 488 */     this.decompiler.addEOL(82);
/*  478: 489 */     return new Node(node.getType(), expr, node.getLineno());
/*  479:     */   }
/*  480:     */   
/*  481:     */   private Node transformForInLoop(ForInLoop loop)
/*  482:     */   {
/*  483: 493 */     this.decompiler.addToken(119);
/*  484: 494 */     if (loop.isForEach()) {
/*  485: 495 */       this.decompiler.addName("each ");
/*  486:     */     }
/*  487: 496 */     this.decompiler.addToken(87);
/*  488:     */     
/*  489: 498 */     loop.setType(132);
/*  490: 499 */     pushScope(loop);
/*  491:     */     try
/*  492:     */     {
/*  493: 501 */       int declType = -1;
/*  494: 502 */       AstNode iter = loop.getIterator();
/*  495: 503 */       if ((iter instanceof VariableDeclaration)) {
/*  496: 504 */         declType = ((VariableDeclaration)iter).getType();
/*  497:     */       }
/*  498: 506 */       Node lhs = transform(iter);
/*  499: 507 */       this.decompiler.addToken(52);
/*  500: 508 */       Node obj = transform(loop.getIteratedObject());
/*  501: 509 */       this.decompiler.addToken(88);
/*  502: 510 */       this.decompiler.addEOL(85);
/*  503: 511 */       Node body = transform(loop.getBody());
/*  504: 512 */       this.decompiler.addEOL(86);
/*  505: 513 */       return createForIn(declType, loop, lhs, obj, body, loop.isForEach());
/*  506:     */     }
/*  507:     */     finally
/*  508:     */     {
/*  509: 516 */       popScope();
/*  510:     */     }
/*  511:     */   }
/*  512:     */   
/*  513:     */   private Node transformForLoop(ForLoop loop)
/*  514:     */   {
/*  515: 521 */     this.decompiler.addToken(119);
/*  516: 522 */     this.decompiler.addToken(87);
/*  517: 523 */     loop.setType(132);
/*  518:     */     
/*  519:     */ 
/*  520: 526 */     Scope savedScope = this.currentScope;
/*  521: 527 */     this.currentScope = loop;
/*  522:     */     try
/*  523:     */     {
/*  524: 529 */       Node init = transform(loop.getInitializer());
/*  525: 530 */       this.decompiler.addToken(82);
/*  526: 531 */       Node test = transform(loop.getCondition());
/*  527: 532 */       this.decompiler.addToken(82);
/*  528: 533 */       Node incr = transform(loop.getIncrement());
/*  529: 534 */       this.decompiler.addToken(88);
/*  530: 535 */       this.decompiler.addEOL(85);
/*  531: 536 */       Node body = transform(loop.getBody());
/*  532: 537 */       this.decompiler.addEOL(86);
/*  533: 538 */       return createFor(loop, init, test, incr, body);
/*  534:     */     }
/*  535:     */     finally
/*  536:     */     {
/*  537: 540 */       this.currentScope = savedScope;
/*  538:     */     }
/*  539:     */   }
/*  540:     */   
/*  541:     */   private Node transformFunction(FunctionNode fn)
/*  542:     */   {
/*  543: 545 */     int functionType = fn.getFunctionType();
/*  544: 546 */     int start = this.decompiler.markFunctionStart(functionType);
/*  545: 547 */     Node mexpr = decompileFunctionHeader(fn);
/*  546: 548 */     int index = this.currentScriptOrFn.addFunction(fn);
/*  547:     */     
/*  548: 550 */     Parser.PerFunctionVariables savedVars = new Parser.PerFunctionVariables(this, fn);
/*  549:     */     try
/*  550:     */     {
/*  551: 554 */       Node destructuring = (Node)fn.getProp(23);
/*  552: 555 */       fn.removeProp(23);
/*  553:     */       
/*  554: 557 */       int lineno = fn.getBody().getLineno();
/*  555: 558 */       this.nestingOfFunction += 1;
/*  556: 559 */       Node body = transform(fn.getBody());
/*  557: 561 */       if (!fn.isExpressionClosure()) {
/*  558: 562 */         this.decompiler.addToken(86);
/*  559:     */       }
/*  560: 564 */       fn.setEncodedSourceBounds(start, this.decompiler.markFunctionEnd(start));
/*  561: 566 */       if ((functionType != 2) && (!fn.isExpressionClosure())) {
/*  562: 569 */         this.decompiler.addToken(1);
/*  563:     */       }
/*  564: 572 */       if (destructuring != null) {
/*  565: 573 */         body.addChildToFront(new Node(133, destructuring, lineno));
/*  566:     */       }
/*  567: 577 */       int syntheticType = fn.getFunctionType();
/*  568: 578 */       Node pn = initFunction(fn, index, body, syntheticType);
/*  569: 579 */       if (mexpr != null)
/*  570:     */       {
/*  571: 580 */         pn = createAssignment(90, mexpr, pn);
/*  572: 581 */         if (syntheticType != 2) {
/*  573: 582 */           pn = createExprStatementNoReturn(pn, fn.getLineno());
/*  574:     */         }
/*  575:     */       }
/*  576: 585 */       return pn;
/*  577:     */     }
/*  578:     */     finally
/*  579:     */     {
/*  580: 588 */       this.nestingOfFunction -= 1;
/*  581: 589 */       savedVars.restore();
/*  582:     */     }
/*  583:     */   }
/*  584:     */   
/*  585:     */   private Node transformFunctionCall(FunctionCall node)
/*  586:     */   {
/*  587: 594 */     Node call = createCallOrNew(38, transform(node.getTarget()));
/*  588: 595 */     call.setLineno(node.getLineno());
/*  589: 596 */     this.decompiler.addToken(87);
/*  590: 597 */     List<AstNode> args = node.getArguments();
/*  591: 598 */     for (int i = 0; i < args.size(); i++)
/*  592:     */     {
/*  593: 599 */       AstNode arg = (AstNode)args.get(i);
/*  594: 600 */       call.addChildToBack(transform(arg));
/*  595: 601 */       if (i < args.size() - 1) {
/*  596: 602 */         this.decompiler.addToken(89);
/*  597:     */       }
/*  598:     */     }
/*  599: 605 */     this.decompiler.addToken(88);
/*  600: 606 */     return call;
/*  601:     */   }
/*  602:     */   
/*  603:     */   private Node transformIf(IfStatement n)
/*  604:     */   {
/*  605: 610 */     this.decompiler.addToken(112);
/*  606: 611 */     this.decompiler.addToken(87);
/*  607: 612 */     Node cond = transform(n.getCondition());
/*  608: 613 */     this.decompiler.addToken(88);
/*  609: 614 */     this.decompiler.addEOL(85);
/*  610: 615 */     Node ifTrue = transform(n.getThenPart());
/*  611: 616 */     Node ifFalse = null;
/*  612: 617 */     if (n.getElsePart() != null)
/*  613:     */     {
/*  614: 618 */       this.decompiler.addToken(86);
/*  615: 619 */       this.decompiler.addToken(113);
/*  616: 620 */       this.decompiler.addEOL(85);
/*  617: 621 */       ifFalse = transform(n.getElsePart());
/*  618:     */     }
/*  619: 623 */     this.decompiler.addEOL(86);
/*  620: 624 */     return createIf(cond, ifTrue, ifFalse, n.getLineno());
/*  621:     */   }
/*  622:     */   
/*  623:     */   private Node transformInfix(InfixExpression node)
/*  624:     */   {
/*  625: 628 */     Node left = transform(node.getLeft());
/*  626: 629 */     this.decompiler.addToken(node.getType());
/*  627: 630 */     Node right = transform(node.getRight());
/*  628: 631 */     if ((node instanceof XmlDotQuery)) {
/*  629: 632 */       this.decompiler.addToken(88);
/*  630:     */     }
/*  631: 634 */     return createBinary(node.getType(), left, right);
/*  632:     */   }
/*  633:     */   
/*  634:     */   private Node transformLabeledStatement(LabeledStatement ls)
/*  635:     */   {
/*  636: 638 */     for (Label lb : ls.getLabels())
/*  637:     */     {
/*  638: 639 */       this.decompiler.addName(lb.getName());
/*  639: 640 */       this.decompiler.addEOL(103);
/*  640:     */     }
/*  641: 642 */     Label label = ls.getFirstLabel();
/*  642: 643 */     Node statement = transform(ls.getStatement());
/*  643:     */     
/*  644:     */ 
/*  645:     */ 
/*  646: 647 */     Node breakTarget = Node.newTarget();
/*  647: 648 */     Node block = new Node(129, label, statement, breakTarget);
/*  648: 649 */     label.target = breakTarget;
/*  649:     */     
/*  650: 651 */     return block;
/*  651:     */   }
/*  652:     */   
/*  653:     */   private Node transformLetNode(LetNode node)
/*  654:     */   {
/*  655: 655 */     pushScope(node);
/*  656:     */     try
/*  657:     */     {
/*  658: 657 */       this.decompiler.addToken(153);
/*  659: 658 */       this.decompiler.addToken(87);
/*  660: 659 */       Node vars = transformVariableInitializers(node.getVariables());
/*  661: 660 */       this.decompiler.addToken(88);
/*  662: 661 */       node.addChildToBack(vars);
/*  663: 662 */       boolean letExpr = node.getType() == 158;
/*  664: 663 */       if (node.getBody() != null)
/*  665:     */       {
/*  666: 664 */         if (letExpr) {
/*  667: 665 */           this.decompiler.addName(" ");
/*  668:     */         } else {
/*  669: 667 */           this.decompiler.addEOL(85);
/*  670:     */         }
/*  671: 669 */         node.addChildToBack(transform(node.getBody()));
/*  672: 670 */         if (!letExpr) {
/*  673: 671 */           this.decompiler.addEOL(86);
/*  674:     */         }
/*  675:     */       }
/*  676: 674 */       return node;
/*  677:     */     }
/*  678:     */     finally
/*  679:     */     {
/*  680: 676 */       popScope();
/*  681:     */     }
/*  682:     */   }
/*  683:     */   
/*  684:     */   private Node transformLiteral(AstNode node)
/*  685:     */   {
/*  686: 681 */     this.decompiler.addToken(node.getType());
/*  687: 682 */     return node;
/*  688:     */   }
/*  689:     */   
/*  690:     */   private Node transformName(Name node)
/*  691:     */   {
/*  692: 686 */     this.decompiler.addName(node.getIdentifier());
/*  693: 687 */     return node;
/*  694:     */   }
/*  695:     */   
/*  696:     */   private Node transformNewExpr(NewExpression node)
/*  697:     */   {
/*  698: 691 */     this.decompiler.addToken(30);
/*  699: 692 */     Node nx = createCallOrNew(30, transform(node.getTarget()));
/*  700: 693 */     nx.setLineno(node.getLineno());
/*  701: 694 */     List<AstNode> args = node.getArguments();
/*  702: 695 */     this.decompiler.addToken(87);
/*  703: 696 */     for (int i = 0; i < args.size(); i++)
/*  704:     */     {
/*  705: 697 */       AstNode arg = (AstNode)args.get(i);
/*  706: 698 */       nx.addChildToBack(transform(arg));
/*  707: 699 */       if (i < args.size() - 1) {
/*  708: 700 */         this.decompiler.addToken(89);
/*  709:     */       }
/*  710:     */     }
/*  711: 703 */     this.decompiler.addToken(88);
/*  712: 704 */     if (node.getInitializer() != null) {
/*  713: 705 */       nx.addChildToBack(transformObjectLiteral(node.getInitializer()));
/*  714:     */     }
/*  715: 707 */     return nx;
/*  716:     */   }
/*  717:     */   
/*  718:     */   private Node transformNumber(NumberLiteral node)
/*  719:     */   {
/*  720: 711 */     this.decompiler.addNumber(node.getNumber());
/*  721: 712 */     return node;
/*  722:     */   }
/*  723:     */   
/*  724:     */   private Node transformObjectLiteral(ObjectLiteral node)
/*  725:     */   {
/*  726: 716 */     if (node.isDestructuring()) {
/*  727: 717 */       return node;
/*  728:     */     }
/*  729: 722 */     this.decompiler.addToken(85);
/*  730: 723 */     List<ObjectProperty> elems = node.getElements();
/*  731: 724 */     Node object = new Node(66);
/*  732:     */     Object[] properties;
/*  733:     */     int size;
/*  734:     */     int i;
/*  735:     */     Object[] properties;
/*  736: 726 */     if (elems.isEmpty())
/*  737:     */     {
/*  738: 727 */       properties = ScriptRuntime.emptyArgs;
/*  739:     */     }
/*  740:     */     else
/*  741:     */     {
/*  742: 729 */       size = elems.size();i = 0;
/*  743: 730 */       properties = new Object[size];
/*  744: 731 */       for (ObjectProperty prop : elems)
/*  745:     */       {
/*  746: 732 */         if (prop.isGetter()) {
/*  747: 733 */           this.decompiler.addToken(151);
/*  748: 734 */         } else if (prop.isSetter()) {
/*  749: 735 */           this.decompiler.addToken(152);
/*  750:     */         }
/*  751: 738 */         properties[(i++)] = getPropKey(prop.getLeft());
/*  752: 742 */         if ((!prop.isGetter()) && (!prop.isSetter())) {
/*  753: 743 */           this.decompiler.addToken(66);
/*  754:     */         }
/*  755: 746 */         Node right = transform(prop.getRight());
/*  756: 747 */         if (prop.isGetter()) {
/*  757: 748 */           right = createUnary(151, right);
/*  758: 749 */         } else if (prop.isSetter()) {
/*  759: 750 */           right = createUnary(152, right);
/*  760:     */         }
/*  761: 752 */         object.addChildToBack(right);
/*  762: 754 */         if (i < size) {
/*  763: 755 */           this.decompiler.addToken(89);
/*  764:     */         }
/*  765:     */       }
/*  766:     */     }
/*  767: 759 */     this.decompiler.addToken(86);
/*  768: 760 */     object.putProp(12, properties);
/*  769: 761 */     return object;
/*  770:     */   }
/*  771:     */   
/*  772:     */   private Object getPropKey(Node id)
/*  773:     */   {
/*  774:     */     Object key;
/*  775: 766 */     if ((id instanceof Name))
/*  776:     */     {
/*  777: 767 */       String s = ((Name)id).getIdentifier();
/*  778: 768 */       this.decompiler.addName(s);
/*  779: 769 */       key = ScriptRuntime.getIndexObject(s);
/*  780:     */     }
/*  781:     */     else
/*  782:     */     {
/*  783:     */       Object key;
/*  784: 770 */       if ((id instanceof StringLiteral))
/*  785:     */       {
/*  786: 771 */         String s = ((StringLiteral)id).getValue();
/*  787: 772 */         this.decompiler.addString(s);
/*  788: 773 */         key = ScriptRuntime.getIndexObject(s);
/*  789:     */       }
/*  790:     */       else
/*  791:     */       {
/*  792:     */         Object key;
/*  793: 774 */         if ((id instanceof NumberLiteral))
/*  794:     */         {
/*  795: 775 */           double n = ((NumberLiteral)id).getNumber();
/*  796: 776 */           this.decompiler.addNumber(n);
/*  797: 777 */           key = ScriptRuntime.getIndexObject(n);
/*  798:     */         }
/*  799:     */         else
/*  800:     */         {
/*  801: 779 */           throw Kit.codeBug();
/*  802:     */         }
/*  803:     */       }
/*  804:     */     }
/*  805:     */     Object key;
/*  806: 781 */     return key;
/*  807:     */   }
/*  808:     */   
/*  809:     */   private Node transformParenExpr(ParenthesizedExpression node)
/*  810:     */   {
/*  811: 785 */     AstNode expr = node.getExpression();
/*  812: 786 */     this.decompiler.addToken(87);
/*  813: 787 */     int count = 1;
/*  814: 788 */     while ((expr instanceof ParenthesizedExpression))
/*  815:     */     {
/*  816: 789 */       this.decompiler.addToken(87);
/*  817: 790 */       count++;
/*  818: 791 */       expr = ((ParenthesizedExpression)expr).getExpression();
/*  819:     */     }
/*  820: 793 */     Node result = transform(expr);
/*  821: 794 */     for (int i = 0; i < count; i++) {
/*  822: 795 */       this.decompiler.addToken(88);
/*  823:     */     }
/*  824: 797 */     result.putProp(19, Boolean.TRUE);
/*  825: 798 */     return result;
/*  826:     */   }
/*  827:     */   
/*  828:     */   private Node transformPropertyGet(PropertyGet node)
/*  829:     */   {
/*  830: 802 */     Node target = transform(node.getTarget());
/*  831: 803 */     String name = node.getProperty().getIdentifier();
/*  832: 804 */     this.decompiler.addToken(108);
/*  833: 805 */     this.decompiler.addName(name);
/*  834: 806 */     return createPropertyGet(target, null, name, 0);
/*  835:     */   }
/*  836:     */   
/*  837:     */   private Node transformRegExp(RegExpLiteral node)
/*  838:     */   {
/*  839: 810 */     this.decompiler.addRegexp(node.getValue(), node.getFlags());
/*  840: 811 */     this.currentScriptOrFn.addRegExp(node);
/*  841: 812 */     return node;
/*  842:     */   }
/*  843:     */   
/*  844:     */   private Node transformReturn(ReturnStatement node)
/*  845:     */   {
/*  846: 816 */     if (Boolean.TRUE.equals(node.getProp(25))) {
/*  847: 817 */       this.decompiler.addName(" ");
/*  848:     */     } else {
/*  849: 819 */       this.decompiler.addToken(4);
/*  850:     */     }
/*  851: 821 */     AstNode rv = node.getReturnValue();
/*  852: 822 */     Node value = rv == null ? null : transform(rv);
/*  853: 823 */     this.decompiler.addEOL(82);
/*  854: 824 */     return rv == null ? new Node(4, node.getLineno()) : new Node(4, value, node.getLineno());
/*  855:     */   }
/*  856:     */   
/*  857:     */   private Node transformScript(ScriptNode node)
/*  858:     */   {
/*  859: 830 */     this.decompiler.addToken(136);
/*  860: 831 */     if (this.currentScope != null) {
/*  861: 831 */       Kit.codeBug();
/*  862:     */     }
/*  863: 832 */     this.currentScope = node;
/*  864: 833 */     Node body = new Node(129);
/*  865: 834 */     for (Node kid : node) {
/*  866: 835 */       body.addChildToBack(transform((AstNode)kid));
/*  867:     */     }
/*  868: 837 */     node.removeChildren();
/*  869: 838 */     Node children = body.getFirstChild();
/*  870: 839 */     if (children != null) {
/*  871: 840 */       node.addChildrenToBack(children);
/*  872:     */     }
/*  873: 842 */     return node;
/*  874:     */   }
/*  875:     */   
/*  876:     */   private Node transformString(StringLiteral node)
/*  877:     */   {
/*  878: 846 */     this.decompiler.addString(node.getValue());
/*  879: 847 */     return Node.newString(node.getValue());
/*  880:     */   }
/*  881:     */   
/*  882:     */   private Node transformSwitch(SwitchStatement node)
/*  883:     */   {
/*  884: 890 */     this.decompiler.addToken(114);
/*  885: 891 */     this.decompiler.addToken(87);
/*  886: 892 */     Node switchExpr = transform(node.getExpression());
/*  887: 893 */     this.decompiler.addToken(88);
/*  888: 894 */     node.addChildToBack(switchExpr);
/*  889:     */     
/*  890: 896 */     Node block = new Node(129, node, node.getLineno());
/*  891: 897 */     this.decompiler.addEOL(85);
/*  892: 899 */     for (SwitchCase sc : node.getCases())
/*  893:     */     {
/*  894: 900 */       AstNode expr = sc.getExpression();
/*  895: 901 */       Node caseExpr = null;
/*  896: 903 */       if (expr != null)
/*  897:     */       {
/*  898: 904 */         this.decompiler.addToken(115);
/*  899: 905 */         caseExpr = transform(expr);
/*  900:     */       }
/*  901:     */       else
/*  902:     */       {
/*  903: 907 */         this.decompiler.addToken(116);
/*  904:     */       }
/*  905: 909 */       this.decompiler.addEOL(103);
/*  906:     */       
/*  907: 911 */       List<AstNode> stmts = sc.getStatements();
/*  908: 912 */       Node body = new Block();
/*  909: 913 */       if (stmts != null) {
/*  910: 914 */         for (AstNode kid : stmts) {
/*  911: 915 */           body.addChildToBack(transform(kid));
/*  912:     */         }
/*  913:     */       }
/*  914: 918 */       addSwitchCase(block, caseExpr, body);
/*  915:     */     }
/*  916: 920 */     this.decompiler.addEOL(86);
/*  917: 921 */     closeSwitch(block);
/*  918: 922 */     return block;
/*  919:     */   }
/*  920:     */   
/*  921:     */   private Node transformThrow(ThrowStatement node)
/*  922:     */   {
/*  923: 926 */     this.decompiler.addToken(50);
/*  924: 927 */     Node value = transform(node.getExpression());
/*  925: 928 */     this.decompiler.addEOL(82);
/*  926: 929 */     return new Node(50, value, node.getLineno());
/*  927:     */   }
/*  928:     */   
/*  929:     */   private Node transformTry(TryStatement node)
/*  930:     */   {
/*  931: 933 */     this.decompiler.addToken(81);
/*  932: 934 */     this.decompiler.addEOL(85);
/*  933: 935 */     Node tryBlock = transform(node.getTryBlock());
/*  934: 936 */     this.decompiler.addEOL(86);
/*  935:     */     
/*  936: 938 */     Node catchBlocks = new Block();
/*  937: 939 */     for (CatchClause cc : node.getCatchClauses())
/*  938:     */     {
/*  939: 940 */       this.decompiler.addToken(124);
/*  940: 941 */       this.decompiler.addToken(87);
/*  941:     */       
/*  942: 943 */       String varName = cc.getVarName().getIdentifier();
/*  943: 944 */       this.decompiler.addName(varName);
/*  944:     */       
/*  945: 946 */       Node catchCond = null;
/*  946: 947 */       AstNode ccc = cc.getCatchCondition();
/*  947: 948 */       if (ccc != null)
/*  948:     */       {
/*  949: 949 */         this.decompiler.addName(" ");
/*  950: 950 */         this.decompiler.addToken(112);
/*  951: 951 */         catchCond = transform(ccc);
/*  952:     */       }
/*  953:     */       else
/*  954:     */       {
/*  955: 953 */         catchCond = new EmptyExpression();
/*  956:     */       }
/*  957: 955 */       this.decompiler.addToken(88);
/*  958: 956 */       this.decompiler.addEOL(85);
/*  959:     */       
/*  960: 958 */       Node body = transform(cc.getBody());
/*  961: 959 */       this.decompiler.addEOL(86);
/*  962:     */       
/*  963: 961 */       catchBlocks.addChildToBack(createCatch(varName, catchCond, body, cc.getLineno()));
/*  964:     */     }
/*  965: 964 */     Node finallyBlock = null;
/*  966: 965 */     if (node.getFinallyBlock() != null)
/*  967:     */     {
/*  968: 966 */       this.decompiler.addToken(125);
/*  969: 967 */       this.decompiler.addEOL(85);
/*  970: 968 */       finallyBlock = transform(node.getFinallyBlock());
/*  971: 969 */       this.decompiler.addEOL(86);
/*  972:     */     }
/*  973: 971 */     return createTryCatchFinally(tryBlock, catchBlocks, finallyBlock, node.getLineno());
/*  974:     */   }
/*  975:     */   
/*  976:     */   private Node transformUnary(UnaryExpression node)
/*  977:     */   {
/*  978: 976 */     int type = node.getType();
/*  979: 977 */     if (type == 74) {
/*  980: 978 */       return transformDefaultXmlNamepace(node);
/*  981:     */     }
/*  982: 980 */     if (node.isPrefix()) {
/*  983: 981 */       this.decompiler.addToken(type);
/*  984:     */     }
/*  985: 983 */     Node child = transform(node.getOperand());
/*  986: 984 */     if (node.isPostfix()) {
/*  987: 985 */       this.decompiler.addToken(type);
/*  988:     */     }
/*  989: 987 */     if ((type == 106) || (type == 107)) {
/*  990: 988 */       return createIncDec(type, node.isPostfix(), child);
/*  991:     */     }
/*  992: 990 */     return createUnary(type, child);
/*  993:     */   }
/*  994:     */   
/*  995:     */   private Node transformVariables(VariableDeclaration node)
/*  996:     */   {
/*  997: 994 */     this.decompiler.addToken(node.getType());
/*  998: 995 */     transformVariableInitializers(node);
/*  999:     */     
/* 1000:     */ 
/* 1001:     */ 
/* 1002: 999 */     AstNode parent = node.getParent();
/* 1003:1000 */     if ((!(parent instanceof Loop)) && (!(parent instanceof LetNode))) {
/* 1004:1002 */       this.decompiler.addEOL(82);
/* 1005:     */     }
/* 1006:1004 */     return node;
/* 1007:     */   }
/* 1008:     */   
/* 1009:     */   private Node transformVariableInitializers(VariableDeclaration node)
/* 1010:     */   {
/* 1011:1008 */     List<VariableInitializer> vars = node.getVariables();
/* 1012:1009 */     int size = vars.size();int i = 0;
/* 1013:1010 */     for (VariableInitializer var : vars)
/* 1014:     */     {
/* 1015:1011 */       AstNode target = var.getTarget();
/* 1016:1012 */       AstNode init = var.getInitializer();
/* 1017:     */       
/* 1018:1014 */       Node left = null;
/* 1019:1015 */       if (var.isDestructuring())
/* 1020:     */       {
/* 1021:1016 */         decompile(target);
/* 1022:1017 */         left = target;
/* 1023:     */       }
/* 1024:     */       else
/* 1025:     */       {
/* 1026:1019 */         left = transform(target);
/* 1027:     */       }
/* 1028:1022 */       Node right = null;
/* 1029:1023 */       if (init != null)
/* 1030:     */       {
/* 1031:1024 */         this.decompiler.addToken(90);
/* 1032:1025 */         right = transform(init);
/* 1033:     */       }
/* 1034:1028 */       if (var.isDestructuring())
/* 1035:     */       {
/* 1036:1029 */         if (right == null)
/* 1037:     */         {
/* 1038:1030 */           node.addChildToBack(left);
/* 1039:     */         }
/* 1040:     */         else
/* 1041:     */         {
/* 1042:1032 */           Node d = createDestructuringAssignment(node.getType(), left, right);
/* 1043:     */           
/* 1044:1034 */           node.addChildToBack(d);
/* 1045:     */         }
/* 1046:     */       }
/* 1047:     */       else
/* 1048:     */       {
/* 1049:1037 */         if (right != null) {
/* 1050:1038 */           left.addChildToBack(right);
/* 1051:     */         }
/* 1052:1040 */         node.addChildToBack(left);
/* 1053:     */       }
/* 1054:1042 */       if (i++ < size - 1) {
/* 1055:1043 */         this.decompiler.addToken(89);
/* 1056:     */       }
/* 1057:     */     }
/* 1058:1046 */     return node;
/* 1059:     */   }
/* 1060:     */   
/* 1061:     */   private Node transformWhileLoop(WhileLoop loop)
/* 1062:     */   {
/* 1063:1050 */     this.decompiler.addToken(117);
/* 1064:1051 */     loop.setType(132);
/* 1065:1052 */     pushScope(loop);
/* 1066:     */     try
/* 1067:     */     {
/* 1068:1054 */       this.decompiler.addToken(87);
/* 1069:1055 */       Node cond = transform(loop.getCondition());
/* 1070:1056 */       this.decompiler.addToken(88);
/* 1071:1057 */       this.decompiler.addEOL(85);
/* 1072:1058 */       Node body = transform(loop.getBody());
/* 1073:1059 */       this.decompiler.addEOL(86);
/* 1074:1060 */       return createLoop(loop, 1, body, cond, null, null);
/* 1075:     */     }
/* 1076:     */     finally
/* 1077:     */     {
/* 1078:1062 */       popScope();
/* 1079:     */     }
/* 1080:     */   }
/* 1081:     */   
/* 1082:     */   private Node transformWith(WithStatement node)
/* 1083:     */   {
/* 1084:1067 */     this.decompiler.addToken(123);
/* 1085:1068 */     this.decompiler.addToken(87);
/* 1086:1069 */     Node expr = transform(node.getExpression());
/* 1087:1070 */     this.decompiler.addToken(88);
/* 1088:1071 */     this.decompiler.addEOL(85);
/* 1089:1072 */     Node stmt = transform(node.getStatement());
/* 1090:1073 */     this.decompiler.addEOL(86);
/* 1091:1074 */     return createWith(expr, stmt, node.getLineno());
/* 1092:     */   }
/* 1093:     */   
/* 1094:     */   private Node transformYield(Yield node)
/* 1095:     */   {
/* 1096:1078 */     this.decompiler.addToken(72);
/* 1097:1079 */     Node kid = node.getValue() == null ? null : transform(node.getValue());
/* 1098:1080 */     if (kid != null) {
/* 1099:1081 */       return new Node(72, kid, node.getLineno());
/* 1100:     */     }
/* 1101:1083 */     return new Node(72, node.getLineno());
/* 1102:     */   }
/* 1103:     */   
/* 1104:     */   private Node transformXmlLiteral(XmlLiteral node)
/* 1105:     */   {
/* 1106:1090 */     Node pnXML = new Node(30, node.getLineno());
/* 1107:1091 */     List<XmlFragment> frags = node.getFragments();
/* 1108:     */     
/* 1109:1093 */     XmlString first = (XmlString)frags.get(0);
/* 1110:1094 */     boolean anon = first.getXml().trim().startsWith("<>");
/* 1111:1095 */     pnXML.addChildToBack(createName(anon ? "XMLList" : "XML"));
/* 1112:     */     
/* 1113:1097 */     Node pn = null;
/* 1114:1098 */     for (XmlFragment frag : frags) {
/* 1115:1099 */       if ((frag instanceof XmlString))
/* 1116:     */       {
/* 1117:1100 */         String xml = ((XmlString)frag).getXml();
/* 1118:1101 */         this.decompiler.addName(xml);
/* 1119:1102 */         if (pn == null) {
/* 1120:1103 */           pn = createString(xml);
/* 1121:     */         } else {
/* 1122:1105 */           pn = createBinary(21, pn, createString(xml));
/* 1123:     */         }
/* 1124:     */       }
/* 1125:     */       else
/* 1126:     */       {
/* 1127:1108 */         XmlExpression xexpr = (XmlExpression)frag;
/* 1128:1109 */         boolean isXmlAttr = xexpr.isXmlAttribute();
/* 1129:     */         
/* 1130:1111 */         this.decompiler.addToken(85);
/* 1131:     */         Node expr;
/* 1132:     */         Node expr;
/* 1133:1112 */         if ((xexpr.getExpression() instanceof EmptyExpression)) {
/* 1134:1113 */           expr = createString("");
/* 1135:     */         } else {
/* 1136:1115 */           expr = transform(xexpr.getExpression());
/* 1137:     */         }
/* 1138:1117 */         this.decompiler.addToken(86);
/* 1139:1118 */         if (isXmlAttr)
/* 1140:     */         {
/* 1141:1120 */           expr = createUnary(75, expr);
/* 1142:1121 */           Node prepend = createBinary(21, createString("\""), expr);
/* 1143:     */           
/* 1144:     */ 
/* 1145:1124 */           expr = createBinary(21, prepend, createString("\""));
/* 1146:     */         }
/* 1147:     */         else
/* 1148:     */         {
/* 1149:1128 */           expr = createUnary(76, expr);
/* 1150:     */         }
/* 1151:1130 */         pn = createBinary(21, pn, expr);
/* 1152:     */       }
/* 1153:     */     }
/* 1154:1134 */     pnXML.addChildToBack(pn);
/* 1155:1135 */     return pnXML;
/* 1156:     */   }
/* 1157:     */   
/* 1158:     */   private Node transformXmlMemberGet(XmlMemberGet node)
/* 1159:     */   {
/* 1160:1139 */     XmlRef ref = node.getMemberRef();
/* 1161:1140 */     Node pn = transform(node.getLeft());
/* 1162:1141 */     int flags = ref.isAttributeAccess() ? 2 : 0;
/* 1163:1142 */     if (node.getType() == 143)
/* 1164:     */     {
/* 1165:1143 */       flags |= 0x4;
/* 1166:1144 */       this.decompiler.addToken(143);
/* 1167:     */     }
/* 1168:     */     else
/* 1169:     */     {
/* 1170:1146 */       this.decompiler.addToken(108);
/* 1171:     */     }
/* 1172:1148 */     return transformXmlRef(pn, ref, flags);
/* 1173:     */   }
/* 1174:     */   
/* 1175:     */   private Node transformXmlRef(XmlRef node)
/* 1176:     */   {
/* 1177:1153 */     int memberTypeFlags = node.isAttributeAccess() ? 2 : 0;
/* 1178:     */     
/* 1179:1155 */     return transformXmlRef(null, node, memberTypeFlags);
/* 1180:     */   }
/* 1181:     */   
/* 1182:     */   private Node transformXmlRef(Node pn, XmlRef node, int memberTypeFlags)
/* 1183:     */   {
/* 1184:1159 */     if ((memberTypeFlags & 0x2) != 0) {
/* 1185:1160 */       this.decompiler.addToken(147);
/* 1186:     */     }
/* 1187:1161 */     Name namespace = node.getNamespace();
/* 1188:1162 */     String ns = namespace != null ? namespace.getIdentifier() : null;
/* 1189:1163 */     if (ns != null)
/* 1190:     */     {
/* 1191:1164 */       this.decompiler.addName(ns);
/* 1192:1165 */       this.decompiler.addToken(144);
/* 1193:     */     }
/* 1194:1167 */     if ((node instanceof XmlPropRef))
/* 1195:     */     {
/* 1196:1168 */       String name = ((XmlPropRef)node).getPropName().getIdentifier();
/* 1197:1169 */       this.decompiler.addName(name);
/* 1198:1170 */       return createPropertyGet(pn, ns, name, memberTypeFlags);
/* 1199:     */     }
/* 1200:1172 */     this.decompiler.addToken(83);
/* 1201:1173 */     Node expr = transform(((XmlElemRef)node).getExpression());
/* 1202:1174 */     this.decompiler.addToken(84);
/* 1203:1175 */     return createElementGet(pn, ns, expr, memberTypeFlags);
/* 1204:     */   }
/* 1205:     */   
/* 1206:     */   private Node transformDefaultXmlNamepace(UnaryExpression node)
/* 1207:     */   {
/* 1208:1180 */     this.decompiler.addToken(116);
/* 1209:1181 */     this.decompiler.addName(" xml");
/* 1210:1182 */     this.decompiler.addName(" namespace");
/* 1211:1183 */     this.decompiler.addToken(90);
/* 1212:1184 */     Node child = transform(node.getOperand());
/* 1213:1185 */     return createUnary(74, child);
/* 1214:     */   }
/* 1215:     */   
/* 1216:     */   private void addSwitchCase(Node switchBlock, Node caseExpression, Node statements)
/* 1217:     */   {
/* 1218:1194 */     if (switchBlock.getType() != 129) {
/* 1219:1194 */       throw Kit.codeBug();
/* 1220:     */     }
/* 1221:1195 */     Jump switchNode = (Jump)switchBlock.getFirstChild();
/* 1222:1196 */     if (switchNode.getType() != 114) {
/* 1223:1196 */       throw Kit.codeBug();
/* 1224:     */     }
/* 1225:1198 */     Node gotoTarget = Node.newTarget();
/* 1226:1199 */     if (caseExpression != null)
/* 1227:     */     {
/* 1228:1200 */       Jump caseNode = new Jump(115, caseExpression);
/* 1229:1201 */       caseNode.target = gotoTarget;
/* 1230:1202 */       switchNode.addChildToBack(caseNode);
/* 1231:     */     }
/* 1232:     */     else
/* 1233:     */     {
/* 1234:1204 */       switchNode.setDefault(gotoTarget);
/* 1235:     */     }
/* 1236:1206 */     switchBlock.addChildToBack(gotoTarget);
/* 1237:1207 */     switchBlock.addChildToBack(statements);
/* 1238:     */   }
/* 1239:     */   
/* 1240:     */   private void closeSwitch(Node switchBlock)
/* 1241:     */   {
/* 1242:1212 */     if (switchBlock.getType() != 129) {
/* 1243:1212 */       throw Kit.codeBug();
/* 1244:     */     }
/* 1245:1213 */     Jump switchNode = (Jump)switchBlock.getFirstChild();
/* 1246:1214 */     if (switchNode.getType() != 114) {
/* 1247:1214 */       throw Kit.codeBug();
/* 1248:     */     }
/* 1249:1216 */     Node switchBreakTarget = Node.newTarget();
/* 1250:     */     
/* 1251:     */ 
/* 1252:1219 */     switchNode.target = switchBreakTarget;
/* 1253:     */     
/* 1254:1221 */     Node defaultTarget = switchNode.getDefault();
/* 1255:1222 */     if (defaultTarget == null) {
/* 1256:1223 */       defaultTarget = switchBreakTarget;
/* 1257:     */     }
/* 1258:1226 */     switchBlock.addChildAfter(makeJump(5, defaultTarget), switchNode);
/* 1259:     */     
/* 1260:1228 */     switchBlock.addChildToBack(switchBreakTarget);
/* 1261:     */   }
/* 1262:     */   
/* 1263:     */   private Node createExprStatementNoReturn(Node expr, int lineno)
/* 1264:     */   {
/* 1265:1232 */     return new Node(133, expr, lineno);
/* 1266:     */   }
/* 1267:     */   
/* 1268:     */   private Node createString(String string)
/* 1269:     */   {
/* 1270:1236 */     return Node.newString(string);
/* 1271:     */   }
/* 1272:     */   
/* 1273:     */   private Node createCatch(String varName, Node catchCond, Node stmts, int lineno)
/* 1274:     */   {
/* 1275:1249 */     if (catchCond == null) {
/* 1276:1250 */       catchCond = new Node(128);
/* 1277:     */     }
/* 1278:1252 */     return new Node(124, createName(varName), catchCond, stmts, lineno);
/* 1279:     */   }
/* 1280:     */   
/* 1281:     */   private Node initFunction(FunctionNode fnNode, int functionIndex, Node statements, int functionType)
/* 1282:     */   {
/* 1283:1258 */     fnNode.setFunctionType(functionType);
/* 1284:1259 */     fnNode.addChildToBack(statements);
/* 1285:     */     
/* 1286:1261 */     int functionCount = fnNode.getFunctionCount();
/* 1287:1262 */     if (functionCount != 0) {
/* 1288:1264 */       fnNode.setRequiresActivation();
/* 1289:     */     }
/* 1290:1267 */     if (functionType == 2)
/* 1291:     */     {
/* 1292:1268 */       Name name = fnNode.getFunctionName();
/* 1293:1269 */       if ((name != null) && (name.length() != 0) && (fnNode.getSymbol(name.getIdentifier()) == null))
/* 1294:     */       {
/* 1295:1278 */         fnNode.putSymbol(new Symbol(109, name.getIdentifier()));
/* 1296:1279 */         Node setFn = new Node(133, new Node(8, Node.newString(49, name.getIdentifier()), new Node(63)));
/* 1297:     */         
/* 1298:     */ 
/* 1299:     */ 
/* 1300:     */ 
/* 1301:1284 */         statements.addChildrenToFront(setFn);
/* 1302:     */       }
/* 1303:     */     }
/* 1304:1289 */     Node lastStmt = statements.getLastChild();
/* 1305:1290 */     if ((lastStmt == null) || (lastStmt.getType() != 4)) {
/* 1306:1291 */       statements.addChildToBack(new Node(4));
/* 1307:     */     }
/* 1308:1294 */     Node result = Node.newString(109, fnNode.getName());
/* 1309:1295 */     result.putIntProp(1, functionIndex);
/* 1310:1296 */     return result;
/* 1311:     */   }
/* 1312:     */   
/* 1313:     */   private Scope createLoopNode(Node loopLabel, int lineno)
/* 1314:     */   {
/* 1315:1305 */     Scope result = createScopeNode(132, lineno);
/* 1316:1306 */     if (loopLabel != null) {
/* 1317:1307 */       ((Jump)loopLabel).setLoop(result);
/* 1318:     */     }
/* 1319:1309 */     return result;
/* 1320:     */   }
/* 1321:     */   
/* 1322:     */   private Node createFor(Scope loop, Node init, Node test, Node incr, Node body)
/* 1323:     */   {
/* 1324:1314 */     if (init.getType() == 153)
/* 1325:     */     {
/* 1326:1318 */       Scope let = Scope.splitScope(loop);
/* 1327:1319 */       let.setType(153);
/* 1328:1320 */       let.addChildrenToBack(init);
/* 1329:1321 */       let.addChildToBack(createLoop(loop, 2, body, test, new Node(128), incr));
/* 1330:     */       
/* 1331:1323 */       return let;
/* 1332:     */     }
/* 1333:1325 */     return createLoop(loop, 2, body, test, init, incr);
/* 1334:     */   }
/* 1335:     */   
/* 1336:     */   private Node createLoop(Jump loop, int loopType, Node body, Node cond, Node init, Node incr)
/* 1337:     */   {
/* 1338:1331 */     Node bodyTarget = Node.newTarget();
/* 1339:1332 */     Node condTarget = Node.newTarget();
/* 1340:1333 */     if ((loopType == 2) && (cond.getType() == 128)) {
/* 1341:1334 */       cond = new Node(45);
/* 1342:     */     }
/* 1343:1336 */     Jump IFEQ = new Jump(6, cond);
/* 1344:1337 */     IFEQ.target = bodyTarget;
/* 1345:1338 */     Node breakTarget = Node.newTarget();
/* 1346:     */     
/* 1347:1340 */     loop.addChildToBack(bodyTarget);
/* 1348:1341 */     loop.addChildrenToBack(body);
/* 1349:1342 */     if ((loopType == 1) || (loopType == 2)) {
/* 1350:1344 */       loop.addChildrenToBack(new Node(128, loop.getLineno()));
/* 1351:     */     }
/* 1352:1346 */     loop.addChildToBack(condTarget);
/* 1353:1347 */     loop.addChildToBack(IFEQ);
/* 1354:1348 */     loop.addChildToBack(breakTarget);
/* 1355:     */     
/* 1356:1350 */     loop.target = breakTarget;
/* 1357:1351 */     Node continueTarget = condTarget;
/* 1358:1353 */     if ((loopType == 1) || (loopType == 2))
/* 1359:     */     {
/* 1360:1355 */       loop.addChildToFront(makeJump(5, condTarget));
/* 1361:1357 */       if (loopType == 2)
/* 1362:     */       {
/* 1363:1358 */         int initType = init.getType();
/* 1364:1359 */         if (initType != 128)
/* 1365:     */         {
/* 1366:1360 */           if ((initType != 122) && (initType != 153)) {
/* 1367:1361 */             init = new Node(133, init);
/* 1368:     */           }
/* 1369:1363 */           loop.addChildToFront(init);
/* 1370:     */         }
/* 1371:1365 */         Node incrTarget = Node.newTarget();
/* 1372:1366 */         loop.addChildAfter(incrTarget, body);
/* 1373:1367 */         if (incr.getType() != 128)
/* 1374:     */         {
/* 1375:1368 */           incr = new Node(133, incr);
/* 1376:1369 */           loop.addChildAfter(incr, incrTarget);
/* 1377:     */         }
/* 1378:1371 */         continueTarget = incrTarget;
/* 1379:     */       }
/* 1380:     */     }
/* 1381:1375 */     loop.setContinue(continueTarget);
/* 1382:1376 */     return loop;
/* 1383:     */   }
/* 1384:     */   
/* 1385:     */   private Node createForIn(int declType, Node loop, Node lhs, Node obj, Node body, boolean isForEach)
/* 1386:     */   {
/* 1387:1385 */     int destructuring = -1;
/* 1388:1386 */     int destructuringLen = 0;
/* 1389:     */     
/* 1390:1388 */     int type = lhs.getType();
/* 1391:     */     Node lvalue;
/* 1392:     */     Node lvalue;
/* 1393:1389 */     if ((type == 122) || (type == 153))
/* 1394:     */     {
/* 1395:1390 */       Node kid = lhs.getLastChild();
/* 1396:1391 */       int kidType = kid.getType();
/* 1397:1392 */       if ((kidType == 65) || (kidType == 66))
/* 1398:     */       {
/* 1399:1394 */         type = destructuring = kidType;
/* 1400:1395 */         Node lvalue = kid;
/* 1401:1396 */         destructuringLen = 0;
/* 1402:1397 */         if ((kid instanceof ArrayLiteral)) {
/* 1403:1398 */           destructuringLen = ((ArrayLiteral)kid).getDestructuringLength();
/* 1404:     */         }
/* 1405:     */       }
/* 1406:     */       else
/* 1407:     */       {
/* 1408:     */         Node lvalue;
/* 1409:1399 */         if (kidType == 39)
/* 1410:     */         {
/* 1411:1400 */           lvalue = Node.newString(39, kid.getString());
/* 1412:     */         }
/* 1413:     */         else
/* 1414:     */         {
/* 1415:1402 */           reportError("msg.bad.for.in.lhs");
/* 1416:1403 */           return null;
/* 1417:     */         }
/* 1418:     */       }
/* 1419:     */     }
/* 1420:1405 */     else if ((type == 65) || (type == 66))
/* 1421:     */     {
/* 1422:1406 */       destructuring = type;
/* 1423:1407 */       Node lvalue = lhs;
/* 1424:1408 */       destructuringLen = 0;
/* 1425:1409 */       if ((lhs instanceof ArrayLiteral)) {
/* 1426:1410 */         destructuringLen = ((ArrayLiteral)lhs).getDestructuringLength();
/* 1427:     */       }
/* 1428:     */     }
/* 1429:     */     else
/* 1430:     */     {
/* 1431:1412 */       lvalue = makeReference(lhs);
/* 1432:1413 */       if (lvalue == null)
/* 1433:     */       {
/* 1434:1414 */         reportError("msg.bad.for.in.lhs");
/* 1435:1415 */         return null;
/* 1436:     */       }
/* 1437:     */     }
/* 1438:1419 */     Node localBlock = new Node(141);
/* 1439:1420 */     int initType = destructuring != -1 ? 60 : isForEach ? 59 : 58;
/* 1440:     */     
/* 1441:     */ 
/* 1442:     */ 
/* 1443:1424 */     Node init = new Node(initType, obj);
/* 1444:1425 */     init.putProp(3, localBlock);
/* 1445:1426 */     Node cond = new Node(61);
/* 1446:1427 */     cond.putProp(3, localBlock);
/* 1447:1428 */     Node id = new Node(62);
/* 1448:1429 */     id.putProp(3, localBlock);
/* 1449:     */     
/* 1450:1431 */     Node newBody = new Node(129);
/* 1451:     */     Node assign;
/* 1452:1433 */     if (destructuring != -1)
/* 1453:     */     {
/* 1454:1434 */       Node assign = createDestructuringAssignment(declType, lvalue, id);
/* 1455:1435 */       if ((!isForEach) && ((destructuring == 66) || (destructuringLen != 2))) {
/* 1456:1440 */         reportError("msg.bad.for.in.destruct");
/* 1457:     */       }
/* 1458:     */     }
/* 1459:     */     else
/* 1460:     */     {
/* 1461:1443 */       assign = simpleAssignment(lvalue, id);
/* 1462:     */     }
/* 1463:1445 */     newBody.addChildToBack(new Node(133, assign));
/* 1464:1446 */     newBody.addChildToBack(body);
/* 1465:     */     
/* 1466:1448 */     loop = createLoop((Jump)loop, 1, newBody, cond, null, null);
/* 1467:1449 */     loop.addChildToFront(init);
/* 1468:1450 */     if ((type == 122) || (type == 153)) {
/* 1469:1451 */       loop.addChildToFront(lhs);
/* 1470:     */     }
/* 1471:1452 */     localBlock.addChildToBack(loop);
/* 1472:     */     
/* 1473:1454 */     return localBlock;
/* 1474:     */   }
/* 1475:     */   
/* 1476:     */   private Node createTryCatchFinally(Node tryBlock, Node catchBlocks, Node finallyBlock, int lineno)
/* 1477:     */   {
/* 1478:1475 */     boolean hasFinally = (finallyBlock != null) && ((finallyBlock.getType() != 129) || (finallyBlock.hasChildren()));
/* 1479:1480 */     if ((tryBlock.getType() == 129) && (!tryBlock.hasChildren()) && (!hasFinally)) {
/* 1480:1483 */       return tryBlock;
/* 1481:     */     }
/* 1482:1486 */     boolean hasCatch = catchBlocks.hasChildren();
/* 1483:1489 */     if ((!hasFinally) && (!hasCatch)) {
/* 1484:1491 */       return tryBlock;
/* 1485:     */     }
/* 1486:1494 */     Node handlerBlock = new Node(141);
/* 1487:1495 */     Jump pn = new Jump(81, tryBlock, lineno);
/* 1488:1496 */     pn.putProp(3, handlerBlock);
/* 1489:1498 */     if (hasCatch)
/* 1490:     */     {
/* 1491:1500 */       Node endCatch = Node.newTarget();
/* 1492:1501 */       pn.addChildToBack(makeJump(5, endCatch));
/* 1493:     */       
/* 1494:     */ 
/* 1495:1504 */       Node catchTarget = Node.newTarget();
/* 1496:1505 */       pn.target = catchTarget;
/* 1497:     */       
/* 1498:1507 */       pn.addChildToBack(catchTarget);
/* 1499:     */       
/* 1500:     */ 
/* 1501:     */ 
/* 1502:     */ 
/* 1503:     */ 
/* 1504:     */ 
/* 1505:     */ 
/* 1506:     */ 
/* 1507:     */ 
/* 1508:     */ 
/* 1509:     */ 
/* 1510:     */ 
/* 1511:     */ 
/* 1512:     */ 
/* 1513:     */ 
/* 1514:     */ 
/* 1515:     */ 
/* 1516:     */ 
/* 1517:     */ 
/* 1518:     */ 
/* 1519:     */ 
/* 1520:     */ 
/* 1521:     */ 
/* 1522:     */ 
/* 1523:     */ 
/* 1524:     */ 
/* 1525:     */ 
/* 1526:     */ 
/* 1527:     */ 
/* 1528:     */ 
/* 1529:     */ 
/* 1530:     */ 
/* 1531:     */ 
/* 1532:     */ 
/* 1533:     */ 
/* 1534:     */ 
/* 1535:     */ 
/* 1536:     */ 
/* 1537:     */ 
/* 1538:     */ 
/* 1539:     */ 
/* 1540:     */ 
/* 1541:     */ 
/* 1542:     */ 
/* 1543:     */ 
/* 1544:     */ 
/* 1545:     */ 
/* 1546:     */ 
/* 1547:     */ 
/* 1548:1557 */       Node catchScopeBlock = new Node(141);
/* 1549:     */       
/* 1550:     */ 
/* 1551:1560 */       Node cb = catchBlocks.getFirstChild();
/* 1552:1561 */       boolean hasDefault = false;
/* 1553:1562 */       int scopeIndex = 0;
/* 1554:1563 */       while (cb != null)
/* 1555:     */       {
/* 1556:1564 */         int catchLineNo = cb.getLineno();
/* 1557:     */         
/* 1558:1566 */         Node name = cb.getFirstChild();
/* 1559:1567 */         Node cond = name.getNext();
/* 1560:1568 */         Node catchStatement = cond.getNext();
/* 1561:1569 */         cb.removeChild(name);
/* 1562:1570 */         cb.removeChild(cond);
/* 1563:1571 */         cb.removeChild(catchStatement);
/* 1564:     */         
/* 1565:     */ 
/* 1566:     */ 
/* 1567:     */ 
/* 1568:     */ 
/* 1569:1577 */         catchStatement.addChildToBack(new Node(3));
/* 1570:1578 */         catchStatement.addChildToBack(makeJump(5, endCatch));
/* 1571:     */         Node condStmt;
/* 1572:1582 */         if (cond.getType() == 128)
/* 1573:     */         {
/* 1574:1583 */           Node condStmt = catchStatement;
/* 1575:1584 */           hasDefault = true;
/* 1576:     */         }
/* 1577:     */         else
/* 1578:     */         {
/* 1579:1586 */           condStmt = createIf(cond, catchStatement, null, catchLineNo);
/* 1580:     */         }
/* 1581:1592 */         Node catchScope = new Node(57, name, createUseLocal(handlerBlock));
/* 1582:     */         
/* 1583:1594 */         catchScope.putProp(3, catchScopeBlock);
/* 1584:1595 */         catchScope.putIntProp(14, scopeIndex);
/* 1585:1596 */         catchScopeBlock.addChildToBack(catchScope);
/* 1586:     */         
/* 1587:     */ 
/* 1588:1599 */         catchScopeBlock.addChildToBack(createWith(createUseLocal(catchScopeBlock), condStmt, catchLineNo));
/* 1589:     */         
/* 1590:     */ 
/* 1591:     */ 
/* 1592:     */ 
/* 1593:1604 */         cb = cb.getNext();
/* 1594:1605 */         scopeIndex++;
/* 1595:     */       }
/* 1596:1607 */       pn.addChildToBack(catchScopeBlock);
/* 1597:1608 */       if (!hasDefault)
/* 1598:     */       {
/* 1599:1610 */         Node rethrow = new Node(51);
/* 1600:1611 */         rethrow.putProp(3, handlerBlock);
/* 1601:1612 */         pn.addChildToBack(rethrow);
/* 1602:     */       }
/* 1603:1615 */       pn.addChildToBack(endCatch);
/* 1604:     */     }
/* 1605:1618 */     if (hasFinally)
/* 1606:     */     {
/* 1607:1619 */       Node finallyTarget = Node.newTarget();
/* 1608:1620 */       pn.setFinally(finallyTarget);
/* 1609:     */       
/* 1610:     */ 
/* 1611:1623 */       pn.addChildToBack(makeJump(135, finallyTarget));
/* 1612:     */       
/* 1613:     */ 
/* 1614:1626 */       Node finallyEnd = Node.newTarget();
/* 1615:1627 */       pn.addChildToBack(makeJump(5, finallyEnd));
/* 1616:     */       
/* 1617:1629 */       pn.addChildToBack(finallyTarget);
/* 1618:1630 */       Node fBlock = new Node(125, finallyBlock);
/* 1619:1631 */       fBlock.putProp(3, handlerBlock);
/* 1620:1632 */       pn.addChildToBack(fBlock);
/* 1621:     */       
/* 1622:1634 */       pn.addChildToBack(finallyEnd);
/* 1623:     */     }
/* 1624:1636 */     handlerBlock.addChildToBack(pn);
/* 1625:1637 */     return handlerBlock;
/* 1626:     */   }
/* 1627:     */   
/* 1628:     */   private Node createWith(Node obj, Node body, int lineno)
/* 1629:     */   {
/* 1630:1641 */     setRequiresActivation();
/* 1631:1642 */     Node result = new Node(129, lineno);
/* 1632:1643 */     result.addChildToBack(new Node(2, obj));
/* 1633:1644 */     Node bodyNode = new Node(123, body, lineno);
/* 1634:1645 */     result.addChildrenToBack(bodyNode);
/* 1635:1646 */     result.addChildToBack(new Node(3));
/* 1636:1647 */     return result;
/* 1637:     */   }
/* 1638:     */   
/* 1639:     */   private Node createIf(Node cond, Node ifTrue, Node ifFalse, int lineno)
/* 1640:     */   {
/* 1641:1652 */     int condStatus = isAlwaysDefinedBoolean(cond);
/* 1642:1653 */     if (condStatus == 1) {
/* 1643:1654 */       return ifTrue;
/* 1644:     */     }
/* 1645:1655 */     if (condStatus == -1)
/* 1646:     */     {
/* 1647:1656 */       if (ifFalse != null) {
/* 1648:1657 */         return ifFalse;
/* 1649:     */       }
/* 1650:1660 */       return new Node(129, lineno);
/* 1651:     */     }
/* 1652:1663 */     Node result = new Node(129, lineno);
/* 1653:1664 */     Node ifNotTarget = Node.newTarget();
/* 1654:1665 */     Jump IFNE = new Jump(7, cond);
/* 1655:1666 */     IFNE.target = ifNotTarget;
/* 1656:     */     
/* 1657:1668 */     result.addChildToBack(IFNE);
/* 1658:1669 */     result.addChildrenToBack(ifTrue);
/* 1659:1671 */     if (ifFalse != null)
/* 1660:     */     {
/* 1661:1672 */       Node endTarget = Node.newTarget();
/* 1662:1673 */       result.addChildToBack(makeJump(5, endTarget));
/* 1663:1674 */       result.addChildToBack(ifNotTarget);
/* 1664:1675 */       result.addChildrenToBack(ifFalse);
/* 1665:1676 */       result.addChildToBack(endTarget);
/* 1666:     */     }
/* 1667:     */     else
/* 1668:     */     {
/* 1669:1678 */       result.addChildToBack(ifNotTarget);
/* 1670:     */     }
/* 1671:1681 */     return result;
/* 1672:     */   }
/* 1673:     */   
/* 1674:     */   private Node createCondExpr(Node cond, Node ifTrue, Node ifFalse)
/* 1675:     */   {
/* 1676:1685 */     int condStatus = isAlwaysDefinedBoolean(cond);
/* 1677:1686 */     if (condStatus == 1) {
/* 1678:1687 */       return ifTrue;
/* 1679:     */     }
/* 1680:1688 */     if (condStatus == -1) {
/* 1681:1689 */       return ifFalse;
/* 1682:     */     }
/* 1683:1691 */     return new Node(102, cond, ifTrue, ifFalse);
/* 1684:     */   }
/* 1685:     */   
/* 1686:     */   private Node createUnary(int nodeType, Node child)
/* 1687:     */   {
/* 1688:1696 */     int childType = child.getType();
/* 1689:1697 */     switch (nodeType)
/* 1690:     */     {
/* 1691:     */     case 31: 
/* 1692:     */       Node n;
/* 1693:     */       Node n;
/* 1694:1700 */       if (childType == 39)
/* 1695:     */       {
/* 1696:1703 */         child.setType(49);
/* 1697:1704 */         Node left = child;
/* 1698:1705 */         Node right = Node.newString(child.getString());
/* 1699:1706 */         n = new Node(nodeType, left, right);
/* 1700:     */       }
/* 1701:     */       else
/* 1702:     */       {
/* 1703:     */         Node n;
/* 1704:1707 */         if ((childType == 33) || (childType == 36))
/* 1705:     */         {
/* 1706:1710 */           Node left = child.getFirstChild();
/* 1707:1711 */           Node right = child.getLastChild();
/* 1708:1712 */           child.removeChild(left);
/* 1709:1713 */           child.removeChild(right);
/* 1710:1714 */           n = new Node(nodeType, left, right);
/* 1711:     */         }
/* 1712:     */         else
/* 1713:     */         {
/* 1714:     */           Node n;
/* 1715:1715 */           if (childType == 67)
/* 1716:     */           {
/* 1717:1716 */             Node ref = child.getFirstChild();
/* 1718:1717 */             child.removeChild(ref);
/* 1719:1718 */             n = new Node(69, ref);
/* 1720:     */           }
/* 1721:     */           else
/* 1722:     */           {
/* 1723:     */             Node n;
/* 1724:1719 */             if (childType == 38) {
/* 1725:1720 */               n = new Node(nodeType, new Node(45), child);
/* 1726:     */             } else {
/* 1727:1722 */               n = new Node(45);
/* 1728:     */             }
/* 1729:     */           }
/* 1730:     */         }
/* 1731:     */       }
/* 1732:1724 */       return n;
/* 1733:     */     case 32: 
/* 1734:1727 */       if (childType == 39)
/* 1735:     */       {
/* 1736:1728 */         child.setType(137);
/* 1737:1729 */         return child;
/* 1738:     */       }
/* 1739:     */       break;
/* 1740:     */     case 27: 
/* 1741:1733 */       if (childType == 40)
/* 1742:     */       {
/* 1743:1734 */         int value = ScriptRuntime.toInt32(child.getDouble());
/* 1744:1735 */         child.setDouble(value ^ 0xFFFFFFFF);
/* 1745:1736 */         return child;
/* 1746:     */       }
/* 1747:     */       break;
/* 1748:     */     case 29: 
/* 1749:1740 */       if (childType == 40)
/* 1750:     */       {
/* 1751:1741 */         child.setDouble(-child.getDouble());
/* 1752:1742 */         return child;
/* 1753:     */       }
/* 1754:     */       break;
/* 1755:     */     case 26: 
/* 1756:1746 */       int status = isAlwaysDefinedBoolean(child);
/* 1757:1747 */       if (status != 0)
/* 1758:     */       {
/* 1759:     */         int type;
/* 1760:     */         int type;
/* 1761:1749 */         if (status == 1) {
/* 1762:1750 */           type = 44;
/* 1763:     */         } else {
/* 1764:1752 */           type = 45;
/* 1765:     */         }
/* 1766:1754 */         if ((childType == 45) || (childType == 44))
/* 1767:     */         {
/* 1768:1755 */           child.setType(type);
/* 1769:1756 */           return child;
/* 1770:     */         }
/* 1771:1758 */         return new Node(type);
/* 1772:     */       }
/* 1773:     */       break;
/* 1774:     */     }
/* 1775:1763 */     return new Node(nodeType, child);
/* 1776:     */   }
/* 1777:     */   
/* 1778:     */   private Node createCallOrNew(int nodeType, Node child)
/* 1779:     */   {
/* 1780:1767 */     int type = 0;
/* 1781:1768 */     if (child.getType() == 39)
/* 1782:     */     {
/* 1783:1769 */       String name = child.getString();
/* 1784:1770 */       if (name.equals("eval")) {
/* 1785:1771 */         type = 1;
/* 1786:1772 */       } else if (name.equals("With")) {
/* 1787:1773 */         type = 2;
/* 1788:     */       }
/* 1789:     */     }
/* 1790:1775 */     else if (child.getType() == 33)
/* 1791:     */     {
/* 1792:1776 */       String name = child.getLastChild().getString();
/* 1793:1777 */       if (name.equals("eval")) {
/* 1794:1778 */         type = 1;
/* 1795:     */       }
/* 1796:     */     }
/* 1797:1781 */     Node node = new Node(nodeType, child);
/* 1798:1782 */     if (type != 0)
/* 1799:     */     {
/* 1800:1784 */       setRequiresActivation();
/* 1801:1785 */       node.putIntProp(10, type);
/* 1802:     */     }
/* 1803:1787 */     return node;
/* 1804:     */   }
/* 1805:     */   
/* 1806:     */   private Node createIncDec(int nodeType, boolean post, Node child)
/* 1807:     */   {
/* 1808:1792 */     child = makeReference(child);
/* 1809:1793 */     int childType = child.getType();
/* 1810:1795 */     switch (childType)
/* 1811:     */     {
/* 1812:     */     case 33: 
/* 1813:     */     case 36: 
/* 1814:     */     case 39: 
/* 1815:     */     case 67: 
/* 1816:1800 */       Node n = new Node(nodeType, child);
/* 1817:1801 */       int incrDecrMask = 0;
/* 1818:1802 */       if (nodeType == 107) {
/* 1819:1803 */         incrDecrMask |= 0x1;
/* 1820:     */       }
/* 1821:1805 */       if (post) {
/* 1822:1806 */         incrDecrMask |= 0x2;
/* 1823:     */       }
/* 1824:1808 */       n.putIntProp(13, incrDecrMask);
/* 1825:1809 */       return n;
/* 1826:     */     }
/* 1827:1812 */     throw Kit.codeBug();
/* 1828:     */   }
/* 1829:     */   
/* 1830:     */   private Node createPropertyGet(Node target, String namespace, String name, int memberTypeFlags)
/* 1831:     */   {
/* 1832:1818 */     if ((namespace == null) && (memberTypeFlags == 0))
/* 1833:     */     {
/* 1834:1819 */       if (target == null) {
/* 1835:1820 */         return createName(name);
/* 1836:     */       }
/* 1837:1822 */       checkActivationName(name, 33);
/* 1838:1823 */       if (ScriptRuntime.isSpecialProperty(name))
/* 1839:     */       {
/* 1840:1824 */         Node ref = new Node(71, target);
/* 1841:1825 */         ref.putProp(17, name);
/* 1842:1826 */         return new Node(67, ref);
/* 1843:     */       }
/* 1844:1828 */       return new Node(33, target, Node.newString(name));
/* 1845:     */     }
/* 1846:1830 */     Node elem = Node.newString(name);
/* 1847:1831 */     memberTypeFlags |= 0x1;
/* 1848:1832 */     return createMemberRefGet(target, namespace, elem, memberTypeFlags);
/* 1849:     */   }
/* 1850:     */   
/* 1851:     */   private Node createElementGet(Node target, String namespace, Node elem, int memberTypeFlags)
/* 1852:     */   {
/* 1853:1846 */     if ((namespace == null) && (memberTypeFlags == 0))
/* 1854:     */     {
/* 1855:1849 */       if (target == null) {
/* 1856:1849 */         throw Kit.codeBug();
/* 1857:     */       }
/* 1858:1850 */       return new Node(36, target, elem);
/* 1859:     */     }
/* 1860:1852 */     return createMemberRefGet(target, namespace, elem, memberTypeFlags);
/* 1861:     */   }
/* 1862:     */   
/* 1863:     */   private Node createMemberRefGet(Node target, String namespace, Node elem, int memberTypeFlags)
/* 1864:     */   {
/* 1865:1858 */     Node nsNode = null;
/* 1866:1859 */     if (namespace != null) {
/* 1867:1861 */       if (namespace.equals("*")) {
/* 1868:1862 */         nsNode = new Node(42);
/* 1869:     */       } else {
/* 1870:1864 */         nsNode = createName(namespace);
/* 1871:     */       }
/* 1872:     */     }
/* 1873:     */     Node ref;
/* 1874:     */     Node ref;
/* 1875:1868 */     if (target == null)
/* 1876:     */     {
/* 1877:     */       Node ref;
/* 1878:1869 */       if (namespace == null) {
/* 1879:1870 */         ref = new Node(79, elem);
/* 1880:     */       } else {
/* 1881:1872 */         ref = new Node(80, nsNode, elem);
/* 1882:     */       }
/* 1883:     */     }
/* 1884:     */     else
/* 1885:     */     {
/* 1886:     */       Node ref;
/* 1887:1875 */       if (namespace == null) {
/* 1888:1876 */         ref = new Node(77, target, elem);
/* 1889:     */       } else {
/* 1890:1878 */         ref = new Node(78, target, nsNode, elem);
/* 1891:     */       }
/* 1892:     */     }
/* 1893:1881 */     if (memberTypeFlags != 0) {
/* 1894:1882 */       ref.putIntProp(16, memberTypeFlags);
/* 1895:     */     }
/* 1896:1884 */     return new Node(67, ref);
/* 1897:     */   }
/* 1898:     */   
/* 1899:     */   private Node createBinary(int nodeType, Node left, Node right)
/* 1900:     */   {
/* 1901:1888 */     switch (nodeType)
/* 1902:     */     {
/* 1903:     */     case 21: 
/* 1904:1892 */       if (left.type == 41)
/* 1905:     */       {
/* 1906:     */         String s2;
/* 1907:     */         String s2;
/* 1908:1894 */         if (right.type == 41)
/* 1909:     */         {
/* 1910:1895 */           s2 = right.getString();
/* 1911:     */         }
/* 1912:     */         else
/* 1913:     */         {
/* 1914:1896 */           if (right.type != 40) {
/* 1915:     */             break;
/* 1916:     */           }
/* 1917:1897 */           s2 = ScriptRuntime.numberToString(right.getDouble(), 10);
/* 1918:     */         }
/* 1919:1901 */         String s1 = left.getString();
/* 1920:1902 */         left.setString(s1.concat(s2));
/* 1921:1903 */         return left;
/* 1922:     */       }
/* 1923:1904 */       else if (left.type == 40)
/* 1924:     */       {
/* 1925:1905 */         if (right.type == 40)
/* 1926:     */         {
/* 1927:1906 */           left.setDouble(left.getDouble() + right.getDouble());
/* 1928:1907 */           return left;
/* 1929:     */         }
/* 1930:1908 */         if (right.type == 41)
/* 1931:     */         {
/* 1932:1910 */           String s1 = ScriptRuntime.numberToString(left.getDouble(), 10);
/* 1933:1911 */           String s2 = right.getString();
/* 1934:1912 */           right.setString(s1.concat(s2));
/* 1935:1913 */           return right;
/* 1936:     */         }
/* 1937:     */       }
/* 1938:     */       break;
/* 1939:     */     case 22: 
/* 1940:1923 */       if (left.type == 40)
/* 1941:     */       {
/* 1942:1924 */         double ld = left.getDouble();
/* 1943:1925 */         if (right.type == 40)
/* 1944:     */         {
/* 1945:1927 */           left.setDouble(ld - right.getDouble());
/* 1946:1928 */           return left;
/* 1947:     */         }
/* 1948:1929 */         if (ld == 0.0D) {
/* 1949:1931 */           return new Node(29, right);
/* 1950:     */         }
/* 1951:     */       }
/* 1952:1933 */       else if ((right.type == 40) && 
/* 1953:1934 */         (right.getDouble() == 0.0D))
/* 1954:     */       {
/* 1955:1937 */         return new Node(28, left);
/* 1956:     */       }
/* 1957:     */       break;
/* 1958:     */     case 23: 
/* 1959:1944 */       if (left.type == 40)
/* 1960:     */       {
/* 1961:1945 */         double ld = left.getDouble();
/* 1962:1946 */         if (right.type == 40)
/* 1963:     */         {
/* 1964:1948 */           left.setDouble(ld * right.getDouble());
/* 1965:1949 */           return left;
/* 1966:     */         }
/* 1967:1950 */         if (ld == 1.0D) {
/* 1968:1952 */           return new Node(28, right);
/* 1969:     */         }
/* 1970:     */       }
/* 1971:1954 */       else if ((right.type == 40) && 
/* 1972:1955 */         (right.getDouble() == 1.0D))
/* 1973:     */       {
/* 1974:1958 */         return new Node(28, left);
/* 1975:     */       }
/* 1976:     */       break;
/* 1977:     */     case 24: 
/* 1978:1966 */       if (right.type == 40)
/* 1979:     */       {
/* 1980:1967 */         double rd = right.getDouble();
/* 1981:1968 */         if (left.type == 40)
/* 1982:     */         {
/* 1983:1970 */           left.setDouble(left.getDouble() / rd);
/* 1984:1971 */           return left;
/* 1985:     */         }
/* 1986:1972 */         if (rd == 1.0D) {
/* 1987:1975 */           return new Node(28, left);
/* 1988:     */         }
/* 1989:     */       }
/* 1990:1977 */       break;
/* 1991:     */     case 105: 
/* 1992:1985 */       int leftStatus = isAlwaysDefinedBoolean(left);
/* 1993:1986 */       if (leftStatus == -1) {
/* 1994:1988 */         return left;
/* 1995:     */       }
/* 1996:1989 */       if (leftStatus == 1) {
/* 1997:1991 */         return right;
/* 1998:     */       }
/* 1999:     */       break;
/* 2000:     */     case 104: 
/* 2001:2001 */       int leftStatus = isAlwaysDefinedBoolean(left);
/* 2002:2002 */       if (leftStatus == 1) {
/* 2003:2004 */         return left;
/* 2004:     */       }
/* 2005:2005 */       if (leftStatus == -1) {
/* 2006:2007 */         return right;
/* 2007:     */       }
/* 2008:     */       break;
/* 2009:     */     }
/* 2010:2013 */     return new Node(nodeType, left, right);
/* 2011:     */   }
/* 2012:     */   
/* 2013:     */   private Node createAssignment(int assignType, Node left, Node right)
/* 2014:     */   {
/* 2015:2018 */     Node ref = makeReference(left);
/* 2016:2019 */     if (ref == null)
/* 2017:     */     {
/* 2018:2020 */       if ((left.getType() == 65) || (left.getType() == 66))
/* 2019:     */       {
/* 2020:2023 */         if (assignType != 90)
/* 2021:     */         {
/* 2022:2024 */           reportError("msg.bad.destruct.op");
/* 2023:2025 */           return right;
/* 2024:     */         }
/* 2025:2027 */         return createDestructuringAssignment(-1, left, right);
/* 2026:     */       }
/* 2027:2029 */       reportError("msg.bad.assign.left");
/* 2028:2030 */       return right;
/* 2029:     */     }
/* 2030:2032 */     left = ref;
/* 2031:     */     int assignOp;
/* 2032:2035 */     switch (assignType)
/* 2033:     */     {
/* 2034:     */     case 90: 
/* 2035:2037 */       return simpleAssignment(left, right);
/* 2036:     */     case 91: 
/* 2037:2038 */       assignOp = 9; break;
/* 2038:     */     case 92: 
/* 2039:2039 */       assignOp = 10; break;
/* 2040:     */     case 93: 
/* 2041:2040 */       assignOp = 11; break;
/* 2042:     */     case 94: 
/* 2043:2041 */       assignOp = 18; break;
/* 2044:     */     case 95: 
/* 2045:2042 */       assignOp = 19; break;
/* 2046:     */     case 96: 
/* 2047:2043 */       assignOp = 20; break;
/* 2048:     */     case 97: 
/* 2049:2044 */       assignOp = 21; break;
/* 2050:     */     case 98: 
/* 2051:2045 */       assignOp = 22; break;
/* 2052:     */     case 99: 
/* 2053:2046 */       assignOp = 23; break;
/* 2054:     */     case 100: 
/* 2055:2047 */       assignOp = 24; break;
/* 2056:     */     case 101: 
/* 2057:2048 */       assignOp = 25; break;
/* 2058:     */     default: 
/* 2059:2049 */       throw Kit.codeBug();
/* 2060:     */     }
/* 2061:2052 */     int nodeType = left.getType();
/* 2062:2053 */     switch (nodeType)
/* 2063:     */     {
/* 2064:     */     case 39: 
/* 2065:2055 */       Node op = new Node(assignOp, left, right);
/* 2066:2056 */       Node lvalueLeft = Node.newString(49, left.getString());
/* 2067:2057 */       return new Node(8, lvalueLeft, op);
/* 2068:     */     case 33: 
/* 2069:     */     case 36: 
/* 2070:2061 */       Node obj = left.getFirstChild();
/* 2071:2062 */       Node id = left.getLastChild();
/* 2072:     */       
/* 2073:2064 */       int type = nodeType == 33 ? 139 : 140;
/* 2074:     */       
/* 2075:     */ 
/* 2076:     */ 
/* 2077:2068 */       Node opLeft = new Node(138);
/* 2078:2069 */       Node op = new Node(assignOp, opLeft, right);
/* 2079:2070 */       return new Node(type, obj, id, op);
/* 2080:     */     case 67: 
/* 2081:2073 */       ref = left.getFirstChild();
/* 2082:2074 */       checkMutableReference(ref);
/* 2083:2075 */       Node opLeft = new Node(138);
/* 2084:2076 */       Node op = new Node(assignOp, opLeft, right);
/* 2085:2077 */       return new Node(142, ref, op);
/* 2086:     */     }
/* 2087:2081 */     throw Kit.codeBug();
/* 2088:     */   }
/* 2089:     */   
/* 2090:     */   private Node createUseLocal(Node localBlock)
/* 2091:     */   {
/* 2092:2085 */     if (141 != localBlock.getType()) {
/* 2093:2085 */       throw Kit.codeBug();
/* 2094:     */     }
/* 2095:2086 */     Node result = new Node(54);
/* 2096:2087 */     result.putProp(3, localBlock);
/* 2097:2088 */     return result;
/* 2098:     */   }
/* 2099:     */   
/* 2100:     */   private Jump makeJump(int type, Node target)
/* 2101:     */   {
/* 2102:2092 */     Jump n = new Jump(type);
/* 2103:2093 */     n.target = target;
/* 2104:2094 */     return n;
/* 2105:     */   }
/* 2106:     */   
/* 2107:     */   private Node makeReference(Node node)
/* 2108:     */   {
/* 2109:2098 */     int type = node.getType();
/* 2110:2099 */     switch (type)
/* 2111:     */     {
/* 2112:     */     case 33: 
/* 2113:     */     case 36: 
/* 2114:     */     case 39: 
/* 2115:     */     case 67: 
/* 2116:2104 */       return node;
/* 2117:     */     case 38: 
/* 2118:2106 */       node.setType(70);
/* 2119:2107 */       return new Node(67, node);
/* 2120:     */     }
/* 2121:2110 */     return null;
/* 2122:     */   }
/* 2123:     */   
/* 2124:     */   private static int isAlwaysDefinedBoolean(Node node)
/* 2125:     */   {
/* 2126:2115 */     switch (node.getType())
/* 2127:     */     {
/* 2128:     */     case 42: 
/* 2129:     */     case 44: 
/* 2130:2118 */       return -1;
/* 2131:     */     case 45: 
/* 2132:2120 */       return 1;
/* 2133:     */     case 40: 
/* 2134:2122 */       double num = node.getDouble();
/* 2135:2123 */       if ((num == num) && (num != 0.0D)) {
/* 2136:2124 */         return 1;
/* 2137:     */       }
/* 2138:2126 */       return -1;
/* 2139:     */     }
/* 2140:2130 */     return 0;
/* 2141:     */   }
/* 2142:     */   
/* 2143:     */   boolean isDestructuring(Node n)
/* 2144:     */   {
/* 2145:2135 */     return ((n instanceof DestructuringForm)) && (((DestructuringForm)n).isDestructuring());
/* 2146:     */   }
/* 2147:     */   
/* 2148:     */   Node decompileFunctionHeader(FunctionNode fn)
/* 2149:     */   {
/* 2150:2140 */     Node mexpr = null;
/* 2151:2141 */     if (fn.getFunctionName() != null) {
/* 2152:2142 */       this.decompiler.addName(fn.getName());
/* 2153:2143 */     } else if (fn.getMemberExprNode() != null) {
/* 2154:2144 */       mexpr = transform(fn.getMemberExprNode());
/* 2155:     */     }
/* 2156:2146 */     this.decompiler.addToken(87);
/* 2157:2147 */     List<AstNode> params = fn.getParams();
/* 2158:2148 */     for (int i = 0; i < params.size(); i++)
/* 2159:     */     {
/* 2160:2149 */       decompile((AstNode)params.get(i));
/* 2161:2150 */       if (i < params.size() - 1) {
/* 2162:2151 */         this.decompiler.addToken(89);
/* 2163:     */       }
/* 2164:     */     }
/* 2165:2154 */     this.decompiler.addToken(88);
/* 2166:2155 */     if (!fn.isExpressionClosure()) {
/* 2167:2156 */       this.decompiler.addEOL(85);
/* 2168:     */     }
/* 2169:2158 */     return mexpr;
/* 2170:     */   }
/* 2171:     */   
/* 2172:     */   void decompile(AstNode node)
/* 2173:     */   {
/* 2174:2162 */     switch (node.getType())
/* 2175:     */     {
/* 2176:     */     case 65: 
/* 2177:2164 */       decompileArrayLiteral((ArrayLiteral)node);
/* 2178:2165 */       break;
/* 2179:     */     case 66: 
/* 2180:2167 */       decompileObjectLiteral((ObjectLiteral)node);
/* 2181:2168 */       break;
/* 2182:     */     case 41: 
/* 2183:2170 */       this.decompiler.addString(((StringLiteral)node).getValue());
/* 2184:2171 */       break;
/* 2185:     */     case 39: 
/* 2186:2173 */       this.decompiler.addName(((Name)node).getIdentifier());
/* 2187:2174 */       break;
/* 2188:     */     case 40: 
/* 2189:2176 */       this.decompiler.addNumber(((NumberLiteral)node).getNumber());
/* 2190:2177 */       break;
/* 2191:     */     case 33: 
/* 2192:2179 */       decompilePropertyGet((PropertyGet)node);
/* 2193:2180 */       break;
/* 2194:     */     case 128: 
/* 2195:     */       break;
/* 2196:     */     case 36: 
/* 2197:2184 */       decompileElementGet((ElementGet)node);
/* 2198:2185 */       break;
/* 2199:     */     default: 
/* 2200:2187 */       Kit.codeBug("unexpected token: " + Token.typeToName(node.getType()));
/* 2201:     */     }
/* 2202:     */   }
/* 2203:     */   
/* 2204:     */   void decompileArrayLiteral(ArrayLiteral node)
/* 2205:     */   {
/* 2206:2194 */     this.decompiler.addToken(83);
/* 2207:2195 */     List<AstNode> elems = node.getElements();
/* 2208:2196 */     int size = elems.size();
/* 2209:2197 */     for (int i = 0; i < size; i++)
/* 2210:     */     {
/* 2211:2198 */       AstNode elem = (AstNode)elems.get(i);
/* 2212:2199 */       decompile(elem);
/* 2213:2200 */       if (i < size - 1) {
/* 2214:2201 */         this.decompiler.addToken(89);
/* 2215:     */       }
/* 2216:     */     }
/* 2217:2204 */     this.decompiler.addToken(84);
/* 2218:     */   }
/* 2219:     */   
/* 2220:     */   void decompileObjectLiteral(ObjectLiteral node)
/* 2221:     */   {
/* 2222:2209 */     this.decompiler.addToken(85);
/* 2223:2210 */     List<ObjectProperty> props = node.getElements();
/* 2224:2211 */     int size = props.size();
/* 2225:2212 */     for (int i = 0; i < size; i++)
/* 2226:     */     {
/* 2227:2213 */       ObjectProperty prop = (ObjectProperty)props.get(i);
/* 2228:2214 */       boolean destructuringShorthand = Boolean.TRUE.equals(prop.getProp(26));
/* 2229:     */       
/* 2230:2216 */       decompile(prop.getLeft());
/* 2231:2217 */       if (!destructuringShorthand)
/* 2232:     */       {
/* 2233:2218 */         this.decompiler.addToken(103);
/* 2234:2219 */         decompile(prop.getRight());
/* 2235:     */       }
/* 2236:2221 */       if (i < size - 1) {
/* 2237:2222 */         this.decompiler.addToken(89);
/* 2238:     */       }
/* 2239:     */     }
/* 2240:2225 */     this.decompiler.addToken(86);
/* 2241:     */   }
/* 2242:     */   
/* 2243:     */   void decompilePropertyGet(PropertyGet node)
/* 2244:     */   {
/* 2245:2230 */     decompile(node.getTarget());
/* 2246:2231 */     this.decompiler.addToken(108);
/* 2247:2232 */     decompile(node.getProperty());
/* 2248:     */   }
/* 2249:     */   
/* 2250:     */   void decompileElementGet(ElementGet node)
/* 2251:     */   {
/* 2252:2237 */     decompile(node.getTarget());
/* 2253:2238 */     this.decompiler.addToken(83);
/* 2254:2239 */     decompile(node.getElement());
/* 2255:2240 */     this.decompiler.addToken(84);
/* 2256:     */   }
/* 2257:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.IRFactory
 * JD-Core Version:    0.7.0.1
 */