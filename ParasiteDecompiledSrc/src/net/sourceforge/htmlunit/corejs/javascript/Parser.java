/*    1:     */ package net.sourceforge.htmlunit.corejs.javascript;
/*    2:     */ 
/*    3:     */ import java.io.BufferedReader;
/*    4:     */ import java.io.IOException;
/*    5:     */ import java.io.Reader;
/*    6:     */ import java.util.ArrayList;
/*    7:     */ import java.util.HashMap;
/*    8:     */ import java.util.HashSet;
/*    9:     */ import java.util.Iterator;
/*   10:     */ import java.util.List;
/*   11:     */ import java.util.Map;
/*   12:     */ import java.util.Map.Entry;
/*   13:     */ import java.util.Set;
/*   14:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.ArrayComprehension;
/*   15:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.ArrayComprehensionLoop;
/*   16:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.ArrayLiteral;
/*   17:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.Assignment;
/*   18:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.AstNode;
/*   19:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.AstRoot;
/*   20:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.Block;
/*   21:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.BreakStatement;
/*   22:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.CatchClause;
/*   23:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.Comment;
/*   24:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.ConditionalExpression;
/*   25:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.ContinueStatement;
/*   26:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.DestructuringForm;
/*   27:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.DoLoop;
/*   28:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.ElementGet;
/*   29:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.EmptyExpression;
/*   30:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.ErrorNode;
/*   31:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.ExpressionStatement;
/*   32:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.ForInLoop;
/*   33:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.ForLoop;
/*   34:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.FunctionCall;
/*   35:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.FunctionNode;
/*   36:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.IdeErrorReporter;
/*   37:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.IfStatement;
/*   38:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.InfixExpression;
/*   39:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.Jump;
/*   40:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.KeywordLiteral;
/*   41:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.Label;
/*   42:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.LabeledStatement;
/*   43:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.LetNode;
/*   44:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.Loop;
/*   45:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.Name;
/*   46:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.NewExpression;
/*   47:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.NumberLiteral;
/*   48:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.ObjectLiteral;
/*   49:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.ObjectProperty;
/*   50:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.ParenthesizedExpression;
/*   51:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.PropertyGet;
/*   52:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.RegExpLiteral;
/*   53:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.ReturnStatement;
/*   54:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.Scope;
/*   55:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.ScriptNode;
/*   56:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.StringLiteral;
/*   57:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.SwitchCase;
/*   58:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.SwitchStatement;
/*   59:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.Symbol;
/*   60:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.ThrowStatement;
/*   61:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.TryStatement;
/*   62:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.UnaryExpression;
/*   63:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.VariableDeclaration;
/*   64:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.VariableInitializer;
/*   65:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.WhileLoop;
/*   66:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.WithStatement;
/*   67:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.XmlDotQuery;
/*   68:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.XmlElemRef;
/*   69:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.XmlExpression;
/*   70:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.XmlLiteral;
/*   71:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.XmlMemberGet;
/*   72:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.XmlPropRef;
/*   73:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.XmlRef;
/*   74:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.XmlString;
/*   75:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.Yield;
/*   76:     */ 
/*   77:     */ public class Parser
/*   78:     */ {
/*   79:     */   public static final int ARGC_LIMIT = 65536;
/*   80:     */   static final int CLEAR_TI_MASK = 65535;
/*   81:     */   static final int TI_AFTER_EOL = 65536;
/*   82:     */   static final int TI_CHECK_LABEL = 131072;
/*   83:     */   CompilerEnvirons compilerEnv;
/*   84:     */   private ErrorReporter errorReporter;
/*   85:     */   private IdeErrorReporter errorCollector;
/*   86:     */   private String sourceURI;
/*   87:     */   private char[] sourceChars;
/*   88:     */   boolean calledByCompileFunction;
/*   89:     */   private boolean parseFinished;
/*   90:     */   private TokenStream ts;
/*   91: 107 */   private int currentFlaggedToken = 0;
/*   92:     */   private int currentToken;
/*   93:     */   private int syntaxErrorCount;
/*   94:     */   private List<Comment> scannedComments;
/*   95:     */   private String currentJsDocComment;
/*   96:     */   protected int nestingOfFunction;
/*   97:     */   private LabeledStatement currentLabel;
/*   98:     */   private boolean inDestructuringAssignment;
/*   99:     */   protected boolean inUseStrictDirective;
/*  100:     */   ScriptNode currentScriptOrFn;
/*  101:     */   Scope currentScope;
/*  102:     */   int nestingOfWith;
/*  103:     */   private int endFlags;
/*  104:     */   private boolean inForInit;
/*  105:     */   private Map<String, LabeledStatement> labelSet;
/*  106:     */   private List<Loop> loopSet;
/*  107:     */   private List<Jump> loopAndSwitchSet;
/*  108:     */   private int prevNameTokenStart;
/*  109: 135 */   private String prevNameTokenString = "";
/*  110:     */   private int prevNameTokenLineno;
/*  111:     */   
/*  112:     */   public Parser()
/*  113:     */   {
/*  114: 145 */     this(new CompilerEnvirons());
/*  115:     */   }
/*  116:     */   
/*  117:     */   public Parser(CompilerEnvirons compilerEnv)
/*  118:     */   {
/*  119: 149 */     this(compilerEnv, compilerEnv.getErrorReporter());
/*  120:     */   }
/*  121:     */   
/*  122:     */   public Parser(CompilerEnvirons compilerEnv, ErrorReporter errorReporter)
/*  123:     */   {
/*  124: 153 */     this.compilerEnv = compilerEnv;
/*  125: 154 */     this.errorReporter = errorReporter;
/*  126: 155 */     if ((errorReporter instanceof IdeErrorReporter)) {
/*  127: 156 */       this.errorCollector = ((IdeErrorReporter)errorReporter);
/*  128:     */     }
/*  129:     */   }
/*  130:     */   
/*  131:     */   void addStrictWarning(String messageId, String messageArg)
/*  132:     */   {
/*  133: 162 */     int beg = -1;int end = -1;
/*  134: 163 */     if (this.ts != null)
/*  135:     */     {
/*  136: 164 */       beg = this.ts.tokenBeg;
/*  137: 165 */       end = this.ts.tokenEnd - this.ts.tokenBeg;
/*  138:     */     }
/*  139: 167 */     addStrictWarning(messageId, messageArg, beg, end);
/*  140:     */   }
/*  141:     */   
/*  142:     */   void addStrictWarning(String messageId, String messageArg, int position, int length)
/*  143:     */   {
/*  144: 172 */     if (this.compilerEnv.isStrictMode()) {
/*  145: 173 */       addWarning(messageId, messageArg, position, length);
/*  146:     */     }
/*  147:     */   }
/*  148:     */   
/*  149:     */   void addWarning(String messageId, String messageArg)
/*  150:     */   {
/*  151: 177 */     int beg = -1;int end = -1;
/*  152: 178 */     if (this.ts != null)
/*  153:     */     {
/*  154: 179 */       beg = this.ts.tokenBeg;
/*  155: 180 */       end = this.ts.tokenEnd - this.ts.tokenBeg;
/*  156:     */     }
/*  157: 182 */     addWarning(messageId, messageArg, beg, end);
/*  158:     */   }
/*  159:     */   
/*  160:     */   void addWarning(String messageId, int position, int length)
/*  161:     */   {
/*  162: 186 */     addWarning(messageId, null, position, length);
/*  163:     */   }
/*  164:     */   
/*  165:     */   void addWarning(String messageId, String messageArg, int position, int length)
/*  166:     */   {
/*  167: 192 */     String message = lookupMessage(messageId, messageArg);
/*  168: 193 */     if (this.compilerEnv.reportWarningAsError()) {
/*  169: 194 */       addError(messageId, messageArg, position, length);
/*  170: 195 */     } else if (this.errorCollector != null) {
/*  171: 196 */       this.errorCollector.warning(message, this.sourceURI, position, length);
/*  172:     */     } else {
/*  173: 198 */       this.errorReporter.warning(message, this.sourceURI, this.ts.getLineno(), this.ts.getLine(), this.ts.getOffset());
/*  174:     */     }
/*  175:     */   }
/*  176:     */   
/*  177:     */   void addError(String messageId)
/*  178:     */   {
/*  179: 204 */     addError(messageId, this.ts.tokenBeg, this.ts.tokenEnd - this.ts.tokenBeg);
/*  180:     */   }
/*  181:     */   
/*  182:     */   void addError(String messageId, int position, int length)
/*  183:     */   {
/*  184: 208 */     addError(messageId, null, position, length);
/*  185:     */   }
/*  186:     */   
/*  187:     */   void addError(String messageId, String messageArg)
/*  188:     */   {
/*  189: 212 */     addError(messageId, messageArg, this.ts.tokenBeg, this.ts.tokenEnd - this.ts.tokenBeg);
/*  190:     */   }
/*  191:     */   
/*  192:     */   void addError(String messageId, String messageArg, int position, int length)
/*  193:     */   {
/*  194: 218 */     this.syntaxErrorCount += 1;
/*  195: 219 */     String message = lookupMessage(messageId, messageArg);
/*  196: 220 */     if (this.errorCollector != null)
/*  197:     */     {
/*  198: 221 */       this.errorCollector.error(message, this.sourceURI, position, length);
/*  199:     */     }
/*  200:     */     else
/*  201:     */     {
/*  202: 223 */       int lineno = 1;int offset = 1;
/*  203: 224 */       String line = "";
/*  204: 225 */       if (this.ts != null)
/*  205:     */       {
/*  206: 226 */         lineno = this.ts.getLineno();
/*  207: 227 */         line = this.ts.getLine();
/*  208: 228 */         offset = this.ts.getOffset();
/*  209:     */       }
/*  210: 230 */       this.errorReporter.error(message, this.sourceURI, lineno, line, offset);
/*  211:     */     }
/*  212:     */   }
/*  213:     */   
/*  214:     */   String lookupMessage(String messageId)
/*  215:     */   {
/*  216: 235 */     return lookupMessage(messageId, null);
/*  217:     */   }
/*  218:     */   
/*  219:     */   String lookupMessage(String messageId, String messageArg)
/*  220:     */   {
/*  221: 239 */     return messageArg == null ? ScriptRuntime.getMessage0(messageId) : ScriptRuntime.getMessage1(messageId, messageArg);
/*  222:     */   }
/*  223:     */   
/*  224:     */   void reportError(String messageId)
/*  225:     */   {
/*  226: 245 */     reportError(messageId, null);
/*  227:     */   }
/*  228:     */   
/*  229:     */   void reportError(String messageId, String messageArg)
/*  230:     */   {
/*  231: 249 */     if (this.ts == null) {
/*  232: 250 */       reportError(messageId, messageArg, 1, 1);
/*  233:     */     } else {
/*  234: 252 */       reportError(messageId, messageArg, this.ts.tokenBeg, this.ts.tokenEnd - this.ts.tokenBeg);
/*  235:     */     }
/*  236:     */   }
/*  237:     */   
/*  238:     */   void reportError(String messageId, int position, int length)
/*  239:     */   {
/*  240: 259 */     reportError(messageId, null, position, length);
/*  241:     */   }
/*  242:     */   
/*  243:     */   void reportError(String messageId, String messageArg, int position, int length)
/*  244:     */   {
/*  245: 265 */     addError(messageId, position, length);
/*  246: 267 */     if (!this.compilerEnv.recoverFromErrors()) {
/*  247: 268 */       throw new ParserException(null);
/*  248:     */     }
/*  249:     */   }
/*  250:     */   
/*  251:     */   private int getNodeEnd(AstNode n)
/*  252:     */   {
/*  253: 276 */     return n.getPosition() + n.getLength();
/*  254:     */   }
/*  255:     */   
/*  256:     */   private void recordComment(int lineno)
/*  257:     */   {
/*  258: 280 */     if (this.scannedComments == null) {
/*  259: 281 */       this.scannedComments = new ArrayList();
/*  260:     */     }
/*  261: 283 */     String comment = this.ts.getAndResetCurrentComment();
/*  262: 284 */     if ((this.ts.commentType == Token.CommentType.JSDOC) && (this.compilerEnv.isRecordingLocalJsDocComments())) {
/*  263: 286 */       this.currentJsDocComment = comment;
/*  264:     */     }
/*  265: 288 */     Comment commentNode = new Comment(this.ts.tokenBeg, this.ts.getTokenLength(), this.ts.commentType, comment);
/*  266:     */     
/*  267:     */ 
/*  268:     */ 
/*  269: 292 */     commentNode.setLineno(lineno);
/*  270: 293 */     this.scannedComments.add(commentNode);
/*  271:     */   }
/*  272:     */   
/*  273:     */   private String getAndResetJsDoc()
/*  274:     */   {
/*  275: 297 */     String saved = this.currentJsDocComment;
/*  276: 298 */     this.currentJsDocComment = null;
/*  277: 299 */     return saved;
/*  278:     */   }
/*  279:     */   
/*  280:     */   private int peekToken()
/*  281:     */     throws IOException
/*  282:     */   {
/*  283: 322 */     if (this.currentFlaggedToken != 0) {
/*  284: 323 */       return this.currentToken;
/*  285:     */     }
/*  286: 326 */     int lineno = this.ts.getLineno();
/*  287: 327 */     int tt = this.ts.getToken();
/*  288: 328 */     boolean sawEOL = false;
/*  289: 331 */     while ((tt == 1) || (tt == 161))
/*  290:     */     {
/*  291: 332 */       if (tt == 1)
/*  292:     */       {
/*  293: 333 */         lineno++;
/*  294: 334 */         sawEOL = true;
/*  295:     */       }
/*  296:     */       else
/*  297:     */       {
/*  298: 336 */         sawEOL = false;
/*  299: 337 */         if (this.compilerEnv.isRecordingComments()) {
/*  300: 338 */           recordComment(lineno);
/*  301:     */         }
/*  302:     */       }
/*  303: 341 */       tt = this.ts.getToken();
/*  304:     */     }
/*  305: 344 */     this.currentToken = tt;
/*  306: 345 */     this.currentFlaggedToken = (tt | (sawEOL ? 65536 : 0));
/*  307: 346 */     return this.currentToken;
/*  308:     */   }
/*  309:     */   
/*  310:     */   private int peekFlaggedToken()
/*  311:     */     throws IOException
/*  312:     */   {
/*  313: 352 */     peekToken();
/*  314: 353 */     return this.currentFlaggedToken;
/*  315:     */   }
/*  316:     */   
/*  317:     */   private void consumeToken()
/*  318:     */   {
/*  319: 357 */     this.currentFlaggedToken = 0;
/*  320:     */   }
/*  321:     */   
/*  322:     */   private int nextToken()
/*  323:     */     throws IOException
/*  324:     */   {
/*  325: 363 */     int tt = peekToken();
/*  326: 364 */     consumeToken();
/*  327: 365 */     return tt;
/*  328:     */   }
/*  329:     */   
/*  330:     */   private int nextFlaggedToken()
/*  331:     */     throws IOException
/*  332:     */   {
/*  333: 371 */     peekToken();
/*  334: 372 */     int ttFlagged = this.currentFlaggedToken;
/*  335: 373 */     consumeToken();
/*  336: 374 */     return ttFlagged;
/*  337:     */   }
/*  338:     */   
/*  339:     */   private boolean matchToken(int toMatch)
/*  340:     */     throws IOException
/*  341:     */   {
/*  342: 380 */     if (peekToken() != toMatch) {
/*  343: 381 */       return false;
/*  344:     */     }
/*  345: 383 */     consumeToken();
/*  346: 384 */     return true;
/*  347:     */   }
/*  348:     */   
/*  349:     */   private int peekTokenOrEOL()
/*  350:     */     throws IOException
/*  351:     */   {
/*  352: 395 */     int tt = peekToken();
/*  353: 397 */     if ((this.currentFlaggedToken & 0x10000) != 0) {
/*  354: 398 */       tt = 1;
/*  355:     */     }
/*  356: 400 */     return tt;
/*  357:     */   }
/*  358:     */   
/*  359:     */   private boolean mustMatchToken(int toMatch, String messageId)
/*  360:     */     throws IOException
/*  361:     */   {
/*  362: 406 */     return mustMatchToken(toMatch, messageId, this.ts.tokenBeg, this.ts.tokenEnd - this.ts.tokenBeg);
/*  363:     */   }
/*  364:     */   
/*  365:     */   private boolean mustMatchToken(int toMatch, String msgId, int pos, int len)
/*  366:     */     throws IOException
/*  367:     */   {
/*  368: 413 */     if (matchToken(toMatch)) {
/*  369: 414 */       return true;
/*  370:     */     }
/*  371: 416 */     reportError(msgId, pos, len);
/*  372: 417 */     return false;
/*  373:     */   }
/*  374:     */   
/*  375:     */   private void mustHaveXML()
/*  376:     */   {
/*  377: 421 */     if (!this.compilerEnv.isXmlAvailable()) {
/*  378: 422 */       reportError("msg.XML.not.available");
/*  379:     */     }
/*  380:     */   }
/*  381:     */   
/*  382:     */   public boolean eof()
/*  383:     */   {
/*  384: 427 */     return this.ts.eof();
/*  385:     */   }
/*  386:     */   
/*  387:     */   boolean insideFunction()
/*  388:     */   {
/*  389: 431 */     return this.nestingOfFunction != 0;
/*  390:     */   }
/*  391:     */   
/*  392:     */   void pushScope(Scope scope)
/*  393:     */   {
/*  394: 435 */     Scope parent = scope.getParentScope();
/*  395: 438 */     if (parent != null)
/*  396:     */     {
/*  397: 439 */       if (parent != this.currentScope) {
/*  398: 440 */         codeBug();
/*  399:     */       }
/*  400:     */     }
/*  401:     */     else {
/*  402: 442 */       this.currentScope.addChildScope(scope);
/*  403:     */     }
/*  404: 444 */     this.currentScope = scope;
/*  405:     */   }
/*  406:     */   
/*  407:     */   void popScope()
/*  408:     */   {
/*  409: 448 */     this.currentScope = this.currentScope.getParentScope();
/*  410:     */   }
/*  411:     */   
/*  412:     */   private void enterLoop(Loop loop)
/*  413:     */   {
/*  414: 452 */     if (this.loopSet == null) {
/*  415: 453 */       this.loopSet = new ArrayList();
/*  416:     */     }
/*  417: 454 */     this.loopSet.add(loop);
/*  418: 455 */     if (this.loopAndSwitchSet == null) {
/*  419: 456 */       this.loopAndSwitchSet = new ArrayList();
/*  420:     */     }
/*  421: 457 */     this.loopAndSwitchSet.add(loop);
/*  422: 458 */     pushScope(loop);
/*  423: 459 */     if (this.currentLabel != null)
/*  424:     */     {
/*  425: 460 */       this.currentLabel.setStatement(loop);
/*  426: 461 */       this.currentLabel.getFirstLabel().setLoop(loop);
/*  427:     */       
/*  428:     */ 
/*  429:     */ 
/*  430:     */ 
/*  431: 466 */       loop.setRelative(-this.currentLabel.getPosition());
/*  432:     */     }
/*  433:     */   }
/*  434:     */   
/*  435:     */   private void exitLoop()
/*  436:     */   {
/*  437: 471 */     Loop loop = (Loop)this.loopSet.remove(this.loopSet.size() - 1);
/*  438: 472 */     this.loopAndSwitchSet.remove(this.loopAndSwitchSet.size() - 1);
/*  439: 473 */     if (loop.getParent() != null) {
/*  440: 474 */       loop.setRelative(loop.getParent().getPosition());
/*  441:     */     }
/*  442: 476 */     popScope();
/*  443:     */   }
/*  444:     */   
/*  445:     */   private void enterSwitch(SwitchStatement node)
/*  446:     */   {
/*  447: 480 */     if (this.loopAndSwitchSet == null) {
/*  448: 481 */       this.loopAndSwitchSet = new ArrayList();
/*  449:     */     }
/*  450: 482 */     this.loopAndSwitchSet.add(node);
/*  451:     */   }
/*  452:     */   
/*  453:     */   private void exitSwitch()
/*  454:     */   {
/*  455: 486 */     this.loopAndSwitchSet.remove(this.loopAndSwitchSet.size() - 1);
/*  456:     */   }
/*  457:     */   
/*  458:     */   public AstRoot parse(String sourceString, String sourceURI, int lineno)
/*  459:     */   {
/*  460: 499 */     if (this.parseFinished) {
/*  461: 499 */       throw new IllegalStateException("parser reused");
/*  462:     */     }
/*  463: 500 */     this.sourceURI = sourceURI;
/*  464: 501 */     if (this.compilerEnv.isIdeMode()) {
/*  465: 502 */       this.sourceChars = sourceString.toCharArray();
/*  466:     */     }
/*  467: 504 */     this.ts = new TokenStream(this, null, sourceString, lineno);
/*  468:     */     try
/*  469:     */     {
/*  470: 506 */       return parse();
/*  471:     */     }
/*  472:     */     catch (IOException iox)
/*  473:     */     {
/*  474: 509 */       throw new IllegalStateException();
/*  475:     */     }
/*  476:     */     finally
/*  477:     */     {
/*  478: 511 */       this.parseFinished = true;
/*  479:     */     }
/*  480:     */   }
/*  481:     */   
/*  482:     */   public AstRoot parse(Reader sourceReader, String sourceURI, int lineno)
/*  483:     */     throws IOException
/*  484:     */   {
/*  485: 523 */     if (this.parseFinished) {
/*  486: 523 */       throw new IllegalStateException("parser reused");
/*  487:     */     }
/*  488: 524 */     if (this.compilerEnv.isIdeMode()) {
/*  489: 525 */       return parse(readFully(sourceReader), sourceURI, lineno);
/*  490:     */     }
/*  491:     */     try
/*  492:     */     {
/*  493: 528 */       this.sourceURI = sourceURI;
/*  494: 529 */       this.ts = new TokenStream(this, sourceReader, null, lineno);
/*  495: 530 */       return parse();
/*  496:     */     }
/*  497:     */     finally
/*  498:     */     {
/*  499: 532 */       this.parseFinished = true;
/*  500:     */     }
/*  501:     */   }
/*  502:     */   
/*  503:     */   private AstRoot parse()
/*  504:     */     throws IOException
/*  505:     */   {
/*  506: 538 */     int pos = 0;
/*  507: 539 */     AstRoot root = new AstRoot(pos);
/*  508: 540 */     this.currentScope = (this.currentScriptOrFn = root);
/*  509:     */     
/*  510: 542 */     int baseLineno = this.ts.lineno;
/*  511: 543 */     int end = pos;
/*  512:     */     
/*  513: 545 */     boolean inDirectivePrologue = true;
/*  514: 546 */     boolean savedStrictMode = this.inUseStrictDirective;
/*  515:     */     
/*  516: 548 */     this.inUseStrictDirective = false;
/*  517:     */     try
/*  518:     */     {
/*  519:     */       for (;;)
/*  520:     */       {
/*  521: 552 */         int tt = peekToken();
/*  522: 553 */         if (tt <= 0) {
/*  523:     */           break;
/*  524:     */         }
/*  525:     */         AstNode n;
/*  526: 558 */         if (tt == 109)
/*  527:     */         {
/*  528: 559 */           consumeToken();
/*  529:     */           try
/*  530:     */           {
/*  531: 561 */             n = function(this.calledByCompileFunction ? 2 : 1);
/*  532:     */           }
/*  533:     */           catch (ParserException e)
/*  534:     */           {
/*  535:     */             break;
/*  536:     */           }
/*  537:     */         }
/*  538:     */         else
/*  539:     */         {
/*  540: 568 */           n = statement();
/*  541: 569 */           if (inDirectivePrologue)
/*  542:     */           {
/*  543: 570 */             String directive = getDirective(n);
/*  544: 571 */             if (directive == null)
/*  545:     */             {
/*  546: 572 */               inDirectivePrologue = false;
/*  547:     */             }
/*  548: 573 */             else if (directive.equals("use strict"))
/*  549:     */             {
/*  550: 574 */               this.inUseStrictDirective = true;
/*  551: 575 */               root.setInStrictMode(true);
/*  552:     */             }
/*  553:     */           }
/*  554:     */         }
/*  555: 580 */         end = getNodeEnd(n);
/*  556: 581 */         root.addChildToBack(n);
/*  557: 582 */         n.setParent(root);
/*  558:     */       }
/*  559:     */     }
/*  560:     */     catch (StackOverflowError ex)
/*  561:     */     {
/*  562: 585 */       String msg = lookupMessage("msg.too.deep.parser.recursion");
/*  563: 586 */       if (!this.compilerEnv.isIdeMode()) {
/*  564: 587 */         throw Context.reportRuntimeError(msg, this.sourceURI, this.ts.lineno, null, 0);
/*  565:     */       }
/*  566:     */     }
/*  567:     */     finally
/*  568:     */     {
/*  569: 590 */       this.inUseStrictDirective = savedStrictMode;
/*  570:     */     }
/*  571: 593 */     if (this.syntaxErrorCount != 0)
/*  572:     */     {
/*  573: 594 */       String msg = String.valueOf(this.syntaxErrorCount);
/*  574: 595 */       msg = lookupMessage("msg.got.syntax.errors", msg);
/*  575: 596 */       if (!this.compilerEnv.isIdeMode()) {
/*  576: 597 */         throw this.errorReporter.runtimeError(msg, this.sourceURI, baseLineno, null, 0);
/*  577:     */       }
/*  578:     */     }
/*  579: 602 */     if (this.scannedComments != null)
/*  580:     */     {
/*  581: 605 */       int last = this.scannedComments.size() - 1;
/*  582: 606 */       end = Math.max(end, getNodeEnd((AstNode)this.scannedComments.get(last)));
/*  583: 607 */       for (Comment c : this.scannedComments) {
/*  584: 608 */         root.addComment(c);
/*  585:     */       }
/*  586:     */     }
/*  587: 612 */     root.setLength(end - pos);
/*  588: 613 */     root.setSourceName(this.sourceURI);
/*  589: 614 */     root.setBaseLineno(baseLineno);
/*  590: 615 */     root.setEndLineno(this.ts.lineno);
/*  591: 616 */     return root;
/*  592:     */   }
/*  593:     */   
/*  594:     */   private AstNode parseFunctionBody()
/*  595:     */     throws IOException
/*  596:     */   {
/*  597: 622 */     if (!matchToken(85))
/*  598:     */     {
/*  599: 623 */       if (this.compilerEnv.getLanguageVersion() < 180) {
/*  600: 624 */         reportError("msg.no.brace.body");
/*  601:     */       }
/*  602: 626 */       return parseFunctionBodyExpr();
/*  603:     */     }
/*  604: 628 */     this.nestingOfFunction += 1;
/*  605: 629 */     int pos = this.ts.tokenBeg;
/*  606: 630 */     Block pn = new Block(pos);
/*  607:     */     
/*  608: 632 */     boolean inDirectivePrologue = true;
/*  609: 633 */     boolean savedStrictMode = this.inUseStrictDirective;
/*  610:     */     
/*  611:     */ 
/*  612: 636 */     pn.setLineno(this.ts.lineno);
/*  613:     */     try
/*  614:     */     {
/*  615:     */       for (;;)
/*  616:     */       {
/*  617: 640 */         int tt = peekToken();
/*  618:     */         AstNode n;
/*  619: 641 */         switch (tt)
/*  620:     */         {
/*  621:     */         case -1: 
/*  622:     */         case 0: 
/*  623:     */         case 86: 
/*  624:     */           break;
/*  625:     */         case 109: 
/*  626: 648 */           consumeToken();
/*  627: 649 */           n = function(1);
/*  628: 650 */           break;
/*  629:     */         default: 
/*  630: 652 */           n = statement();
/*  631: 653 */           if (inDirectivePrologue)
/*  632:     */           {
/*  633: 654 */             String directive = getDirective(n);
/*  634: 655 */             if (directive == null) {
/*  635: 656 */               inDirectivePrologue = false;
/*  636: 657 */             } else if (directive.equals("use strict")) {
/*  637: 658 */               this.inUseStrictDirective = true;
/*  638:     */             }
/*  639:     */           }
/*  640:     */           break;
/*  641:     */         }
/*  642: 663 */         pn.addStatement(n);
/*  643:     */       }
/*  644:     */     }
/*  645:     */     catch (ParserException e) {}finally
/*  646:     */     {
/*  647: 668 */       this.nestingOfFunction -= 1;
/*  648: 669 */       this.inUseStrictDirective = savedStrictMode;
/*  649:     */     }
/*  650: 672 */     int end = this.ts.tokenEnd;
/*  651: 673 */     getAndResetJsDoc();
/*  652: 674 */     if (mustMatchToken(86, "msg.no.brace.after.body")) {
/*  653: 675 */       end = this.ts.tokenEnd;
/*  654:     */     }
/*  655: 676 */     pn.setLength(end - pos);
/*  656: 677 */     return pn;
/*  657:     */   }
/*  658:     */   
/*  659:     */   private String getDirective(AstNode n)
/*  660:     */   {
/*  661: 681 */     if ((n instanceof ExpressionStatement))
/*  662:     */     {
/*  663: 682 */       AstNode e = ((ExpressionStatement)n).getExpression();
/*  664: 683 */       if ((e instanceof StringLiteral)) {
/*  665: 684 */         return ((StringLiteral)e).getValue();
/*  666:     */       }
/*  667:     */     }
/*  668: 687 */     return null;
/*  669:     */   }
/*  670:     */   
/*  671:     */   private void parseFunctionParams(FunctionNode fnNode)
/*  672:     */     throws IOException
/*  673:     */   {
/*  674: 693 */     if (matchToken(88))
/*  675:     */     {
/*  676: 694 */       fnNode.setRp(this.ts.tokenBeg - fnNode.getPosition());
/*  677: 695 */       return;
/*  678:     */     }
/*  679: 699 */     Map<String, Node> destructuring = null;
/*  680: 700 */     Set<String> paramNames = new HashSet();
/*  681:     */     do
/*  682:     */     {
/*  683: 702 */       int tt = peekToken();
/*  684: 703 */       if ((tt == 83) || (tt == 85))
/*  685:     */       {
/*  686: 704 */         AstNode expr = destructuringPrimaryExpr();
/*  687: 705 */         markDestructuring(expr);
/*  688: 706 */         fnNode.addParam(expr);
/*  689: 710 */         if (destructuring == null) {
/*  690: 711 */           destructuring = new HashMap();
/*  691:     */         }
/*  692: 713 */         String pname = this.currentScriptOrFn.getNextTempName();
/*  693: 714 */         defineSymbol(87, pname, false);
/*  694: 715 */         destructuring.put(pname, expr);
/*  695:     */       }
/*  696: 717 */       else if (mustMatchToken(39, "msg.no.parm"))
/*  697:     */       {
/*  698: 718 */         fnNode.addParam(createNameNode());
/*  699: 719 */         String paramName = this.ts.getString();
/*  700: 720 */         defineSymbol(87, paramName);
/*  701: 721 */         if (this.inUseStrictDirective)
/*  702:     */         {
/*  703: 722 */           if (("eval".equals(paramName)) || ("arguments".equals(paramName))) {
/*  704: 725 */             reportError("msg.bad.id.strict", paramName);
/*  705:     */           }
/*  706: 727 */           if (paramNames.contains(paramName)) {
/*  707: 728 */             addError("msg.dup.param.strict", paramName);
/*  708:     */           }
/*  709: 729 */           paramNames.add(paramName);
/*  710:     */         }
/*  711:     */       }
/*  712:     */       else
/*  713:     */       {
/*  714: 732 */         fnNode.addParam(makeErrorNode());
/*  715:     */       }
/*  716: 735 */     } while (matchToken(89));
/*  717: 737 */     if (destructuring != null)
/*  718:     */     {
/*  719: 738 */       Node destructuringNode = new Node(89);
/*  720: 740 */       for (Map.Entry<String, Node> param : destructuring.entrySet())
/*  721:     */       {
/*  722: 741 */         Node assign = createDestructuringAssignment(122, (Node)param.getValue(), createName((String)param.getKey()));
/*  723:     */         
/*  724: 743 */         destructuringNode.addChildToBack(assign);
/*  725:     */       }
/*  726: 746 */       fnNode.putProp(23, destructuringNode);
/*  727:     */     }
/*  728: 749 */     if (mustMatchToken(88, "msg.no.paren.after.parms")) {
/*  729: 750 */       fnNode.setRp(this.ts.tokenBeg - fnNode.getPosition());
/*  730:     */     }
/*  731:     */   }
/*  732:     */   
/*  733:     */   private AstNode parseFunctionBodyExpr()
/*  734:     */     throws IOException
/*  735:     */   {
/*  736: 758 */     this.nestingOfFunction += 1;
/*  737: 759 */     int lineno = this.ts.getLineno();
/*  738: 760 */     ReturnStatement n = new ReturnStatement(lineno);
/*  739: 761 */     n.putProp(25, Boolean.TRUE);
/*  740:     */     try
/*  741:     */     {
/*  742: 763 */       n.setReturnValue(assignExpr());
/*  743:     */     }
/*  744:     */     finally
/*  745:     */     {
/*  746: 765 */       this.nestingOfFunction -= 1;
/*  747:     */     }
/*  748: 767 */     return n;
/*  749:     */   }
/*  750:     */   
/*  751:     */   private FunctionNode function(int type)
/*  752:     */     throws IOException
/*  753:     */   {
/*  754: 773 */     int syntheticType = type;
/*  755: 774 */     int baseLineno = this.ts.lineno;
/*  756: 775 */     int functionSourceStart = this.ts.tokenBeg;
/*  757: 776 */     Name name = null;
/*  758: 777 */     AstNode memberExprNode = null;
/*  759: 779 */     if (matchToken(39))
/*  760:     */     {
/*  761: 780 */       name = createNameNode(true, 39);
/*  762: 781 */       if (this.inUseStrictDirective)
/*  763:     */       {
/*  764: 782 */         String id = name.getIdentifier();
/*  765: 783 */         if (("eval".equals(id)) || ("arguments".equals(id))) {
/*  766: 784 */           reportError("msg.bad.id.strict", id);
/*  767:     */         }
/*  768:     */       }
/*  769: 787 */       if (!matchToken(87))
/*  770:     */       {
/*  771: 788 */         if (this.compilerEnv.isAllowMemberExprAsFunctionName())
/*  772:     */         {
/*  773: 789 */           AstNode memberExprHead = name;
/*  774: 790 */           name = null;
/*  775: 791 */           memberExprNode = memberExprTail(false, memberExprHead);
/*  776:     */         }
/*  777: 793 */         mustMatchToken(87, "msg.no.paren.parms");
/*  778:     */       }
/*  779:     */     }
/*  780: 795 */     else if (!matchToken(87))
/*  781:     */     {
/*  782: 798 */       if (this.compilerEnv.isAllowMemberExprAsFunctionName()) {
/*  783: 802 */         memberExprNode = memberExpr(false);
/*  784:     */       }
/*  785: 804 */       mustMatchToken(87, "msg.no.paren.parms");
/*  786:     */     }
/*  787: 806 */     int lpPos = this.currentToken == 87 ? this.ts.tokenBeg : -1;
/*  788: 808 */     if (memberExprNode != null) {
/*  789: 809 */       syntheticType = 2;
/*  790:     */     }
/*  791: 812 */     if ((syntheticType != 2) && (name != null) && (name.length() > 0)) {
/*  792: 815 */       defineSymbol(109, name.getIdentifier());
/*  793:     */     }
/*  794: 818 */     FunctionNode fnNode = new FunctionNode(functionSourceStart, name);
/*  795: 819 */     fnNode.setFunctionType(type);
/*  796: 820 */     if (lpPos != -1) {
/*  797: 821 */       fnNode.setLp(lpPos - functionSourceStart);
/*  798:     */     }
/*  799: 823 */     if ((insideFunction()) || (this.nestingOfWith > 0)) {
/*  800: 829 */       fnNode.setIgnoreDynamicScope();
/*  801:     */     }
/*  802: 832 */     fnNode.setJsDoc(getAndResetJsDoc());
/*  803:     */     
/*  804: 834 */     PerFunctionVariables savedVars = new PerFunctionVariables(fnNode);
/*  805:     */     try
/*  806:     */     {
/*  807: 836 */       parseFunctionParams(fnNode);
/*  808: 837 */       fnNode.setBody(parseFunctionBody());
/*  809: 838 */       fnNode.setEncodedSourceBounds(functionSourceStart, this.ts.tokenEnd);
/*  810: 839 */       fnNode.setLength(this.ts.tokenEnd - functionSourceStart);
/*  811: 841 */       if ((this.compilerEnv.isStrictMode()) && (!fnNode.getBody().hasConsistentReturnUsage()))
/*  812:     */       {
/*  813: 843 */         String msg = (name != null) && (name.length() > 0) ? "msg.no.return.value" : "msg.anon.no.return.value";
/*  814:     */         
/*  815:     */ 
/*  816: 846 */         addStrictWarning(msg, name == null ? "" : name.getIdentifier());
/*  817:     */       }
/*  818:     */     }
/*  819:     */     finally
/*  820:     */     {
/*  821: 849 */       savedVars.restore();
/*  822:     */     }
/*  823: 852 */     if (memberExprNode != null)
/*  824:     */     {
/*  825: 854 */       Kit.codeBug();
/*  826: 855 */       fnNode.setMemberExprNode(memberExprNode);
/*  827:     */     }
/*  828: 867 */     fnNode.setSourceName(this.sourceURI);
/*  829: 868 */     fnNode.setBaseLineno(baseLineno);
/*  830: 869 */     fnNode.setEndLineno(this.ts.lineno);
/*  831: 875 */     if (this.compilerEnv.isIdeMode()) {
/*  832: 876 */       fnNode.setParentScope(this.currentScope);
/*  833:     */     }
/*  834: 878 */     return fnNode;
/*  835:     */   }
/*  836:     */   
/*  837:     */   private AstNode statements(AstNode parent)
/*  838:     */     throws IOException
/*  839:     */   {
/*  840: 890 */     if ((this.currentToken != 85) && (!this.compilerEnv.isIdeMode())) {
/*  841: 891 */       codeBug();
/*  842:     */     }
/*  843: 892 */     int pos = this.ts.tokenBeg;
/*  844: 893 */     AstNode block = parent != null ? parent : new Block(pos);
/*  845: 894 */     block.setLineno(this.ts.lineno);
/*  846:     */     int tt;
/*  847: 897 */     while (((tt = peekToken()) > 0) && (tt != 86)) {
/*  848: 898 */       block.addChild(statement());
/*  849:     */     }
/*  850: 900 */     block.setLength(this.ts.tokenBeg - pos);
/*  851: 901 */     return block;
/*  852:     */   }
/*  853:     */   
/*  854:     */   private AstNode statements()
/*  855:     */     throws IOException
/*  856:     */   {
/*  857: 905 */     return statements(null);
/*  858:     */   }
/*  859:     */   
/*  860:     */   private static class ParserException
/*  861:     */     extends RuntimeException
/*  862:     */   {
/*  863:     */     static final long serialVersionUID = 5882582646773765630L;
/*  864:     */   }
/*  865:     */   
/*  866:     */   private static class ConditionData
/*  867:     */   {
/*  868:     */     AstNode condition;
/*  869: 910 */     int lp = -1;
/*  870: 911 */     int rp = -1;
/*  871:     */   }
/*  872:     */   
/*  873:     */   private ConditionData condition()
/*  874:     */     throws IOException
/*  875:     */   {
/*  876: 918 */     ConditionData data = new ConditionData(null);
/*  877: 920 */     if (mustMatchToken(87, "msg.no.paren.cond")) {
/*  878: 921 */       data.lp = this.ts.tokenBeg;
/*  879:     */     }
/*  880: 923 */     data.condition = expr();
/*  881: 925 */     if (mustMatchToken(88, "msg.no.paren.after.cond")) {
/*  882: 926 */       data.rp = this.ts.tokenBeg;
/*  883:     */     }
/*  884: 930 */     if ((data.condition instanceof Assignment)) {
/*  885: 931 */       addStrictWarning("msg.equal.as.assign", "", data.condition.getPosition(), data.condition.getLength());
/*  886:     */     }
/*  887: 935 */     return data;
/*  888:     */   }
/*  889:     */   
/*  890:     */   private AstNode statement()
/*  891:     */     throws IOException
/*  892:     */   {
/*  893: 941 */     int pos = this.ts.tokenBeg;
/*  894:     */     try
/*  895:     */     {
/*  896: 943 */       AstNode pn = statementHelper();
/*  897: 944 */       if (pn != null)
/*  898:     */       {
/*  899: 945 */         if ((this.compilerEnv.isStrictMode()) && (!pn.hasSideEffects()))
/*  900:     */         {
/*  901: 946 */           int beg = pn.getPosition();
/*  902: 947 */           beg = Math.max(beg, lineBeginningFor(beg));
/*  903: 948 */           addStrictWarning((pn instanceof EmptyExpression) ? "msg.extra.trailing.semi" : "msg.no.side.effects", "", beg, nodeEnd(pn) - beg);
/*  904:     */         }
/*  905: 953 */         return pn;
/*  906:     */       }
/*  907:     */     }
/*  908:     */     catch (ParserException e) {}
/*  909:     */     for (;;)
/*  910:     */     {
/*  911: 961 */       int tt = peekTokenOrEOL();
/*  912: 962 */       consumeToken();
/*  913: 963 */       switch (tt)
/*  914:     */       {
/*  915:     */       case -1: 
/*  916:     */       case 0: 
/*  917:     */       case 1: 
/*  918:     */       case 82: 
/*  919:     */         break label142;
/*  920:     */       }
/*  921:     */     }
/*  922:     */     label142:
/*  923: 974 */     return new EmptyExpression(pos, this.ts.tokenBeg - pos);
/*  924:     */   }
/*  925:     */   
/*  926:     */   private AstNode statementHelper()
/*  927:     */     throws IOException
/*  928:     */   {
/*  929: 981 */     if ((this.currentLabel != null) && (this.currentLabel.getStatement() != null)) {
/*  930: 982 */       this.currentLabel = null;
/*  931:     */     }
/*  932: 984 */     AstNode pn = null;
/*  933: 985 */     int tt = peekToken();int pos = this.ts.tokenBeg;
/*  934:     */     int lineno;
/*  935: 987 */     switch (tt)
/*  936:     */     {
/*  937:     */     case 112: 
/*  938: 989 */       return ifStatement();
/*  939:     */     case 114: 
/*  940: 992 */       return switchStatement();
/*  941:     */     case 117: 
/*  942: 995 */       return whileLoop();
/*  943:     */     case 118: 
/*  944: 998 */       return doLoop();
/*  945:     */     case 119: 
/*  946:1001 */       return forLoop();
/*  947:     */     case 81: 
/*  948:1004 */       return tryStatement();
/*  949:     */     case 50: 
/*  950:1007 */       pn = throwStatement();
/*  951:1008 */       break;
/*  952:     */     case 120: 
/*  953:1011 */       pn = breakStatement();
/*  954:1012 */       break;
/*  955:     */     case 121: 
/*  956:1015 */       pn = continueStatement();
/*  957:1016 */       break;
/*  958:     */     case 123: 
/*  959:1019 */       if (this.inUseStrictDirective) {
/*  960:1020 */         reportError("msg.no.with.strict");
/*  961:     */       }
/*  962:1022 */       return withStatement();
/*  963:     */     case 122: 
/*  964:     */     case 154: 
/*  965:1026 */       consumeToken();
/*  966:1027 */       lineno = this.ts.lineno;
/*  967:1028 */       pn = variables(this.currentToken, this.ts.tokenBeg);
/*  968:1029 */       pn.setLineno(lineno);
/*  969:1030 */       break;
/*  970:     */     case 153: 
/*  971:1033 */       pn = letStatement();
/*  972:1034 */       if ((!(pn instanceof VariableDeclaration)) || (peekToken() != 82)) {
/*  973:1037 */         return pn;
/*  974:     */       }
/*  975:     */       break;
/*  976:     */     case 4: 
/*  977:     */     case 72: 
/*  978:1041 */       pn = returnOrYield(tt, false);
/*  979:1042 */       break;
/*  980:     */     case 160: 
/*  981:1045 */       consumeToken();
/*  982:1046 */       pn = new KeywordLiteral(this.ts.tokenBeg, this.ts.tokenEnd - this.ts.tokenBeg, tt);
/*  983:     */       
/*  984:1048 */       pn.setLineno(this.ts.lineno);
/*  985:1049 */       break;
/*  986:     */     case 85: 
/*  987:1052 */       return block();
/*  988:     */     case -1: 
/*  989:1055 */       consumeToken();
/*  990:1056 */       return makeErrorNode();
/*  991:     */     case 82: 
/*  992:1059 */       consumeToken();
/*  993:1060 */       pos = this.ts.tokenBeg;
/*  994:1061 */       pn = new EmptyExpression(pos, this.ts.tokenEnd - pos);
/*  995:1062 */       pn.setLineno(this.ts.lineno);
/*  996:1063 */       return pn;
/*  997:     */     case 109: 
/*  998:1066 */       consumeToken();
/*  999:1067 */       return function(3);
/* 1000:     */     case 116: 
/* 1001:1070 */       pn = defaultXmlNamespace();
/* 1002:1071 */       break;
/* 1003:     */     case 39: 
/* 1004:1074 */       pn = nameOrLabel();
/* 1005:1075 */       if (!(pn instanceof ExpressionStatement)) {
/* 1006:1077 */         return pn;
/* 1007:     */       }
/* 1008:     */       break;
/* 1009:     */     default: 
/* 1010:1080 */       lineno = this.ts.lineno;
/* 1011:1081 */       pn = new ExpressionStatement(expr(), !insideFunction());
/* 1012:1082 */       pn.setLineno(lineno);
/* 1013:     */     }
/* 1014:1086 */     autoInsertSemicolon(pn);
/* 1015:1087 */     return pn;
/* 1016:     */   }
/* 1017:     */   
/* 1018:     */   private void autoInsertSemicolon(AstNode pn)
/* 1019:     */     throws IOException
/* 1020:     */   {
/* 1021:1091 */     int ttFlagged = peekFlaggedToken();
/* 1022:1092 */     int pos = pn.getPosition();
/* 1023:1093 */     switch (ttFlagged & 0xFFFF)
/* 1024:     */     {
/* 1025:     */     case 82: 
/* 1026:1096 */       consumeToken();
/* 1027:     */       
/* 1028:1098 */       pn.setLength(this.ts.tokenEnd - pos);
/* 1029:1099 */       break;
/* 1030:     */     case -1: 
/* 1031:     */     case 0: 
/* 1032:     */     case 86: 
/* 1033:1104 */       warnMissingSemi(pos, nodeEnd(pn));
/* 1034:1105 */       break;
/* 1035:     */     default: 
/* 1036:1107 */       if ((ttFlagged & 0x10000) == 0) {
/* 1037:1109 */         reportError("msg.no.semi.stmt");
/* 1038:     */       } else {
/* 1039:1111 */         warnMissingSemi(pos, nodeEnd(pn));
/* 1040:     */       }
/* 1041:     */       break;
/* 1042:     */     }
/* 1043:     */   }
/* 1044:     */   
/* 1045:     */   private IfStatement ifStatement()
/* 1046:     */     throws IOException
/* 1047:     */   {
/* 1048:1120 */     if (this.currentToken != 112) {
/* 1049:1120 */       codeBug();
/* 1050:     */     }
/* 1051:1121 */     consumeToken();
/* 1052:1122 */     int pos = this.ts.tokenBeg;int lineno = this.ts.lineno;int elsePos = -1;
/* 1053:1123 */     ConditionData data = condition();
/* 1054:1124 */     AstNode ifTrue = statement();AstNode ifFalse = null;
/* 1055:1125 */     if (matchToken(113))
/* 1056:     */     {
/* 1057:1126 */       elsePos = this.ts.tokenBeg - pos;
/* 1058:1127 */       ifFalse = statement();
/* 1059:     */     }
/* 1060:1129 */     int end = getNodeEnd(ifFalse != null ? ifFalse : ifTrue);
/* 1061:1130 */     IfStatement pn = new IfStatement(pos, end - pos);
/* 1062:1131 */     pn.setCondition(data.condition);
/* 1063:1132 */     pn.setParens(data.lp - pos, data.rp - pos);
/* 1064:1133 */     pn.setThenPart(ifTrue);
/* 1065:1134 */     pn.setElsePart(ifFalse);
/* 1066:1135 */     pn.setElsePosition(elsePos);
/* 1067:1136 */     pn.setLineno(lineno);
/* 1068:1137 */     return pn;
/* 1069:     */   }
/* 1070:     */   
/* 1071:     */   private SwitchStatement switchStatement()
/* 1072:     */     throws IOException
/* 1073:     */   {
/* 1074:1143 */     if (this.currentToken != 114) {
/* 1075:1143 */       codeBug();
/* 1076:     */     }
/* 1077:1144 */     consumeToken();
/* 1078:1145 */     int pos = this.ts.tokenBeg;
/* 1079:     */     
/* 1080:1147 */     SwitchStatement pn = new SwitchStatement(pos);
/* 1081:1148 */     if (mustMatchToken(87, "msg.no.paren.switch")) {
/* 1082:1149 */       pn.setLp(this.ts.tokenBeg - pos);
/* 1083:     */     }
/* 1084:1150 */     pn.setLineno(this.ts.lineno);
/* 1085:     */     
/* 1086:1152 */     AstNode discriminant = expr();
/* 1087:1153 */     pn.setExpression(discriminant);
/* 1088:1154 */     enterSwitch(pn);
/* 1089:     */     try
/* 1090:     */     {
/* 1091:1157 */       if (mustMatchToken(88, "msg.no.paren.after.switch")) {
/* 1092:1158 */         pn.setRp(this.ts.tokenBeg - pos);
/* 1093:     */       }
/* 1094:1160 */       mustMatchToken(85, "msg.no.brace.switch");
/* 1095:     */       
/* 1096:1162 */       boolean hasDefault = false;
/* 1097:     */       for (;;)
/* 1098:     */       {
/* 1099:1165 */         int tt = nextToken();
/* 1100:1166 */         int casePos = this.ts.tokenBeg;
/* 1101:1167 */         int caseLineno = this.ts.lineno;
/* 1102:1168 */         AstNode caseExpression = null;
/* 1103:1169 */         switch (tt)
/* 1104:     */         {
/* 1105:     */         case 86: 
/* 1106:1171 */           pn.setLength(this.ts.tokenEnd - pos);
/* 1107:1172 */           break;
/* 1108:     */         case 115: 
/* 1109:1175 */           caseExpression = expr();
/* 1110:1176 */           mustMatchToken(103, "msg.no.colon.case");
/* 1111:1177 */           break;
/* 1112:     */         case 116: 
/* 1113:1180 */           if (hasDefault) {
/* 1114:1181 */             reportError("msg.double.switch.default");
/* 1115:     */           }
/* 1116:1183 */           hasDefault = true;
/* 1117:1184 */           caseExpression = null;
/* 1118:1185 */           mustMatchToken(103, "msg.no.colon.case");
/* 1119:1186 */           break;
/* 1120:     */         default: 
/* 1121:1189 */           reportError("msg.bad.switch");
/* 1122:1190 */           break;
/* 1123:     */         }
/* 1124:1193 */         SwitchCase caseNode = new SwitchCase(casePos);
/* 1125:1194 */         caseNode.setExpression(caseExpression);
/* 1126:1195 */         caseNode.setLength(this.ts.tokenEnd - pos);
/* 1127:1196 */         caseNode.setLineno(caseLineno);
/* 1128:1201 */         while (((tt = peekToken()) != 86) && (tt != 115) && (tt != 116) && (tt != 0)) {
/* 1129:1203 */           caseNode.addStatement(statement());
/* 1130:     */         }
/* 1131:1205 */         pn.addCase(caseNode);
/* 1132:     */       }
/* 1133:     */     }
/* 1134:     */     finally
/* 1135:     */     {
/* 1136:1208 */       exitSwitch();
/* 1137:     */     }
/* 1138:1210 */     return pn;
/* 1139:     */   }
/* 1140:     */   
/* 1141:     */   private WhileLoop whileLoop()
/* 1142:     */     throws IOException
/* 1143:     */   {
/* 1144:1216 */     if (this.currentToken != 117) {
/* 1145:1216 */       codeBug();
/* 1146:     */     }
/* 1147:1217 */     consumeToken();
/* 1148:1218 */     int pos = this.ts.tokenBeg;
/* 1149:1219 */     WhileLoop pn = new WhileLoop(pos);
/* 1150:1220 */     pn.setLineno(this.ts.lineno);
/* 1151:1221 */     enterLoop(pn);
/* 1152:     */     try
/* 1153:     */     {
/* 1154:1223 */       ConditionData data = condition();
/* 1155:1224 */       pn.setCondition(data.condition);
/* 1156:1225 */       pn.setParens(data.lp - pos, data.rp - pos);
/* 1157:1226 */       AstNode body = statement();
/* 1158:1227 */       pn.setLength(getNodeEnd(body) - pos);
/* 1159:1228 */       pn.setBody(body);
/* 1160:     */     }
/* 1161:     */     finally
/* 1162:     */     {
/* 1163:1230 */       exitLoop();
/* 1164:     */     }
/* 1165:1232 */     return pn;
/* 1166:     */   }
/* 1167:     */   
/* 1168:     */   private DoLoop doLoop()
/* 1169:     */     throws IOException
/* 1170:     */   {
/* 1171:1238 */     if (this.currentToken != 118) {
/* 1172:1238 */       codeBug();
/* 1173:     */     }
/* 1174:1239 */     consumeToken();
/* 1175:1240 */     int pos = this.ts.tokenBeg;
/* 1176:1241 */     DoLoop pn = new DoLoop(pos);
/* 1177:1242 */     pn.setLineno(this.ts.lineno);
/* 1178:1243 */     enterLoop(pn);
/* 1179:     */     int end;
/* 1180:     */     try
/* 1181:     */     {
/* 1182:1245 */       AstNode body = statement();
/* 1183:1246 */       mustMatchToken(117, "msg.no.while.do");
/* 1184:1247 */       pn.setWhilePosition(this.ts.tokenBeg - pos);
/* 1185:1248 */       ConditionData data = condition();
/* 1186:1249 */       pn.setCondition(data.condition);
/* 1187:1250 */       pn.setParens(data.lp - pos, data.rp - pos);
/* 1188:1251 */       end = getNodeEnd(body);
/* 1189:1252 */       pn.setBody(body);
/* 1190:     */     }
/* 1191:     */     finally
/* 1192:     */     {
/* 1193:1254 */       exitLoop();
/* 1194:     */     }
/* 1195:1259 */     if (matchToken(82)) {
/* 1196:1260 */       end = this.ts.tokenEnd;
/* 1197:     */     }
/* 1198:1262 */     pn.setLength(end - pos);
/* 1199:1263 */     return pn;
/* 1200:     */   }
/* 1201:     */   
/* 1202:     */   private Loop forLoop()
/* 1203:     */     throws IOException
/* 1204:     */   {
/* 1205:1269 */     if (this.currentToken != 119) {
/* 1206:1269 */       codeBug();
/* 1207:     */     }
/* 1208:1270 */     consumeToken();
/* 1209:1271 */     int forPos = this.ts.tokenBeg;int lineno = this.ts.lineno;
/* 1210:1272 */     boolean isForEach = false;boolean isForIn = false;
/* 1211:1273 */     int eachPos = -1;int inPos = -1;int lp = -1;int rp = -1;
/* 1212:1274 */     AstNode init = null;
/* 1213:1275 */     AstNode cond = null;
/* 1214:1276 */     AstNode incr = null;
/* 1215:1277 */     Loop pn = null;
/* 1216:     */     
/* 1217:1279 */     Scope tempScope = new Scope();
/* 1218:1280 */     pushScope(tempScope);
/* 1219:     */     try
/* 1220:     */     {
/* 1221:1283 */       if (matchToken(39)) {
/* 1222:1284 */         if ("each".equals(this.ts.getString()))
/* 1223:     */         {
/* 1224:1285 */           isForEach = true;
/* 1225:1286 */           eachPos = this.ts.tokenBeg - forPos;
/* 1226:     */         }
/* 1227:     */         else
/* 1228:     */         {
/* 1229:1288 */           reportError("msg.no.paren.for");
/* 1230:     */         }
/* 1231:     */       }
/* 1232:1292 */       if (mustMatchToken(87, "msg.no.paren.for")) {
/* 1233:1293 */         lp = this.ts.tokenBeg - forPos;
/* 1234:     */       }
/* 1235:1294 */       int tt = peekToken();
/* 1236:     */       
/* 1237:1296 */       init = forLoopInit(tt);
/* 1238:1298 */       if (matchToken(52))
/* 1239:     */       {
/* 1240:1299 */         isForIn = true;
/* 1241:1300 */         inPos = this.ts.tokenBeg - forPos;
/* 1242:1301 */         cond = expr();
/* 1243:     */       }
/* 1244:     */       else
/* 1245:     */       {
/* 1246:1303 */         mustMatchToken(82, "msg.no.semi.for");
/* 1247:1304 */         if (peekToken() == 82)
/* 1248:     */         {
/* 1249:1306 */           cond = new EmptyExpression(this.ts.tokenBeg, 1);
/* 1250:1307 */           cond.setLineno(this.ts.lineno);
/* 1251:     */         }
/* 1252:     */         else
/* 1253:     */         {
/* 1254:1309 */           cond = expr();
/* 1255:     */         }
/* 1256:1312 */         mustMatchToken(82, "msg.no.semi.for.cond");
/* 1257:1313 */         int tmpPos = this.ts.tokenEnd;
/* 1258:1314 */         if (peekToken() == 88)
/* 1259:     */         {
/* 1260:1315 */           incr = new EmptyExpression(tmpPos, 1);
/* 1261:1316 */           incr.setLineno(this.ts.lineno);
/* 1262:     */         }
/* 1263:     */         else
/* 1264:     */         {
/* 1265:1318 */           incr = expr();
/* 1266:     */         }
/* 1267:     */       }
/* 1268:1322 */       if (mustMatchToken(88, "msg.no.paren.for.ctrl")) {
/* 1269:1323 */         rp = this.ts.tokenBeg - forPos;
/* 1270:     */       }
/* 1271:1325 */       if (isForIn)
/* 1272:     */       {
/* 1273:1326 */         ForInLoop fis = new ForInLoop(forPos);
/* 1274:1327 */         if ((init instanceof VariableDeclaration)) {
/* 1275:1329 */           if (((VariableDeclaration)init).getVariables().size() > 1) {
/* 1276:1330 */             reportError("msg.mult.index");
/* 1277:     */           }
/* 1278:     */         }
/* 1279:1333 */         fis.setIterator(init);
/* 1280:1334 */         fis.setIteratedObject(cond);
/* 1281:1335 */         fis.setInPosition(inPos);
/* 1282:1336 */         fis.setIsForEach(isForEach);
/* 1283:1337 */         fis.setEachPosition(eachPos);
/* 1284:1338 */         pn = fis;
/* 1285:     */       }
/* 1286:     */       else
/* 1287:     */       {
/* 1288:1340 */         ForLoop fl = new ForLoop(forPos);
/* 1289:1341 */         fl.setInitializer(init);
/* 1290:1342 */         fl.setCondition(cond);
/* 1291:1343 */         fl.setIncrement(incr);
/* 1292:1344 */         pn = fl;
/* 1293:     */       }
/* 1294:1348 */       this.currentScope.replaceWith(pn);
/* 1295:1349 */       popScope();
/* 1296:     */       
/* 1297:     */ 
/* 1298:     */ 
/* 1299:     */ 
/* 1300:1354 */       enterLoop(pn);
/* 1301:     */       try
/* 1302:     */       {
/* 1303:1356 */         AstNode body = statement();
/* 1304:1357 */         pn.setLength(getNodeEnd(body) - forPos);
/* 1305:1358 */         pn.setBody(body);
/* 1306:     */       }
/* 1307:     */       finally
/* 1308:     */       {
/* 1309:1360 */         exitLoop();
/* 1310:     */       }
/* 1311:1364 */       if (this.currentScope == tempScope) {
/* 1312:1365 */         popScope();
/* 1313:     */       }
/* 1314:     */     }
/* 1315:     */     finally
/* 1316:     */     {
/* 1317:1364 */       if (this.currentScope == tempScope) {
/* 1318:1365 */         popScope();
/* 1319:     */       }
/* 1320:     */     }
/* 1321:1368 */     pn.setParens(lp, rp);
/* 1322:1369 */     pn.setLineno(lineno);
/* 1323:1370 */     return pn;
/* 1324:     */   }
/* 1325:     */   
/* 1326:     */   private AstNode forLoopInit(int tt)
/* 1327:     */     throws IOException
/* 1328:     */   {
/* 1329:     */     try
/* 1330:     */     {
/* 1331:1375 */       this.inForInit = true;
/* 1332:1376 */       AstNode init = null;
/* 1333:1377 */       if (tt == 82)
/* 1334:     */       {
/* 1335:1378 */         init = new EmptyExpression(this.ts.tokenBeg, 1);
/* 1336:1379 */         init.setLineno(this.ts.lineno);
/* 1337:     */       }
/* 1338:1380 */       else if ((tt == 122) || (tt == 153))
/* 1339:     */       {
/* 1340:1381 */         consumeToken();
/* 1341:1382 */         init = variables(tt, this.ts.tokenBeg);
/* 1342:     */       }
/* 1343:     */       else
/* 1344:     */       {
/* 1345:1384 */         init = expr();
/* 1346:1385 */         markDestructuring(init);
/* 1347:     */       }
/* 1348:1387 */       return init;
/* 1349:     */     }
/* 1350:     */     finally
/* 1351:     */     {
/* 1352:1389 */       this.inForInit = false;
/* 1353:     */     }
/* 1354:     */   }
/* 1355:     */   
/* 1356:     */   private TryStatement tryStatement()
/* 1357:     */     throws IOException
/* 1358:     */   {
/* 1359:1396 */     if (this.currentToken != 81) {
/* 1360:1396 */       codeBug();
/* 1361:     */     }
/* 1362:1397 */     consumeToken();
/* 1363:     */     
/* 1364:     */ 
/* 1365:1400 */     String jsdoc = getAndResetJsDoc();
/* 1366:     */     
/* 1367:1402 */     int tryPos = this.ts.tokenBeg;int lineno = this.ts.lineno;int finallyPos = -1;
/* 1368:1403 */     if (peekToken() != 85) {
/* 1369:1404 */       reportError("msg.no.brace.try");
/* 1370:     */     }
/* 1371:1406 */     AstNode tryBlock = statement();
/* 1372:1407 */     int tryEnd = getNodeEnd(tryBlock);
/* 1373:     */     
/* 1374:1409 */     List<CatchClause> clauses = null;
/* 1375:     */     
/* 1376:1411 */     boolean sawDefaultCatch = false;
/* 1377:1412 */     int peek = peekToken();
/* 1378:1413 */     if (peek == 124) {
/* 1379:1414 */       while (matchToken(124))
/* 1380:     */       {
/* 1381:1415 */         int catchLineNum = this.ts.lineno;
/* 1382:1416 */         if (sawDefaultCatch) {
/* 1383:1417 */           reportError("msg.catch.unreachable");
/* 1384:     */         }
/* 1385:1419 */         int catchPos = this.ts.tokenBeg;int lp = -1;int rp = -1;int guardPos = -1;
/* 1386:1420 */         if (mustMatchToken(87, "msg.no.paren.catch")) {
/* 1387:1421 */           lp = this.ts.tokenBeg;
/* 1388:     */         }
/* 1389:1423 */         mustMatchToken(39, "msg.bad.catchcond");
/* 1390:1424 */         Name varName = createNameNode();
/* 1391:1425 */         String varNameString = varName.getIdentifier();
/* 1392:1426 */         if ((this.inUseStrictDirective) && (
/* 1393:1427 */           ("eval".equals(varNameString)) || ("arguments".equals(varNameString)))) {
/* 1394:1430 */           reportError("msg.bad.id.strict", varNameString);
/* 1395:     */         }
/* 1396:1434 */         AstNode catchCond = null;
/* 1397:1435 */         if (matchToken(112))
/* 1398:     */         {
/* 1399:1436 */           guardPos = this.ts.tokenBeg;
/* 1400:1437 */           catchCond = expr();
/* 1401:     */         }
/* 1402:     */         else
/* 1403:     */         {
/* 1404:1439 */           sawDefaultCatch = true;
/* 1405:     */         }
/* 1406:1442 */         if (mustMatchToken(88, "msg.bad.catchcond")) {
/* 1407:1443 */           rp = this.ts.tokenBeg;
/* 1408:     */         }
/* 1409:1444 */         mustMatchToken(85, "msg.no.brace.catchblock");
/* 1410:     */         
/* 1411:1446 */         Block catchBlock = (Block)statements();
/* 1412:1447 */         tryEnd = getNodeEnd(catchBlock);
/* 1413:1448 */         CatchClause catchNode = new CatchClause(catchPos);
/* 1414:1449 */         catchNode.setVarName(varName);
/* 1415:1450 */         catchNode.setCatchCondition(catchCond);
/* 1416:1451 */         catchNode.setBody(catchBlock);
/* 1417:1452 */         if (guardPos != -1) {
/* 1418:1453 */           catchNode.setIfPosition(guardPos - catchPos);
/* 1419:     */         }
/* 1420:1455 */         catchNode.setParens(lp, rp);
/* 1421:1456 */         catchNode.setLineno(catchLineNum);
/* 1422:1458 */         if (mustMatchToken(86, "msg.no.brace.after.body")) {
/* 1423:1459 */           tryEnd = this.ts.tokenEnd;
/* 1424:     */         }
/* 1425:1460 */         catchNode.setLength(tryEnd - catchPos);
/* 1426:1461 */         if (clauses == null) {
/* 1427:1462 */           clauses = new ArrayList();
/* 1428:     */         }
/* 1429:1463 */         clauses.add(catchNode);
/* 1430:     */       }
/* 1431:     */     }
/* 1432:1465 */     if (peek != 125) {
/* 1433:1466 */       mustMatchToken(125, "msg.try.no.catchfinally");
/* 1434:     */     }
/* 1435:1469 */     AstNode finallyBlock = null;
/* 1436:1470 */     if (matchToken(125))
/* 1437:     */     {
/* 1438:1471 */       finallyPos = this.ts.tokenBeg;
/* 1439:1472 */       finallyBlock = statement();
/* 1440:1473 */       tryEnd = getNodeEnd(finallyBlock);
/* 1441:     */     }
/* 1442:1476 */     TryStatement pn = new TryStatement(tryPos, tryEnd - tryPos);
/* 1443:1477 */     pn.setTryBlock(tryBlock);
/* 1444:1478 */     pn.setCatchClauses(clauses);
/* 1445:1479 */     pn.setFinallyBlock(finallyBlock);
/* 1446:1480 */     if (finallyPos != -1) {
/* 1447:1481 */       pn.setFinallyPosition(finallyPos - tryPos);
/* 1448:     */     }
/* 1449:1483 */     pn.setLineno(lineno);
/* 1450:1485 */     if (jsdoc != null) {
/* 1451:1486 */       pn.setJsDoc(jsdoc);
/* 1452:     */     }
/* 1453:1489 */     return pn;
/* 1454:     */   }
/* 1455:     */   
/* 1456:     */   private ThrowStatement throwStatement()
/* 1457:     */     throws IOException
/* 1458:     */   {
/* 1459:1495 */     if (this.currentToken != 50) {
/* 1460:1495 */       codeBug();
/* 1461:     */     }
/* 1462:1496 */     consumeToken();
/* 1463:1497 */     int pos = this.ts.tokenBeg;int lineno = this.ts.lineno;
/* 1464:1498 */     if (peekTokenOrEOL() == 1) {
/* 1465:1501 */       reportError("msg.bad.throw.eol");
/* 1466:     */     }
/* 1467:1503 */     AstNode expr = expr();
/* 1468:1504 */     ThrowStatement pn = new ThrowStatement(pos, getNodeEnd(expr), expr);
/* 1469:1505 */     pn.setLineno(lineno);
/* 1470:1506 */     return pn;
/* 1471:     */   }
/* 1472:     */   
/* 1473:     */   private LabeledStatement matchJumpLabelName()
/* 1474:     */     throws IOException
/* 1475:     */   {
/* 1476:1518 */     LabeledStatement label = null;
/* 1477:1520 */     if (peekTokenOrEOL() == 39)
/* 1478:     */     {
/* 1479:1521 */       consumeToken();
/* 1480:1522 */       if (this.labelSet != null) {
/* 1481:1523 */         label = (LabeledStatement)this.labelSet.get(this.ts.getString());
/* 1482:     */       }
/* 1483:1525 */       if (label == null) {
/* 1484:1526 */         reportError("msg.undef.label");
/* 1485:     */       }
/* 1486:     */     }
/* 1487:1530 */     return label;
/* 1488:     */   }
/* 1489:     */   
/* 1490:     */   private BreakStatement breakStatement()
/* 1491:     */     throws IOException
/* 1492:     */   {
/* 1493:1536 */     if (this.currentToken != 120) {
/* 1494:1536 */       codeBug();
/* 1495:     */     }
/* 1496:1537 */     consumeToken();
/* 1497:1538 */     int lineno = this.ts.lineno;int pos = this.ts.tokenBeg;int end = this.ts.tokenEnd;
/* 1498:1539 */     Name breakLabel = null;
/* 1499:1540 */     if (peekTokenOrEOL() == 39)
/* 1500:     */     {
/* 1501:1541 */       breakLabel = createNameNode();
/* 1502:1542 */       end = getNodeEnd(breakLabel);
/* 1503:     */     }
/* 1504:1546 */     LabeledStatement labels = matchJumpLabelName();
/* 1505:     */     
/* 1506:1548 */     Jump breakTarget = labels == null ? null : labels.getFirstLabel();
/* 1507:1550 */     if ((breakTarget == null) && (breakLabel == null)) {
/* 1508:1551 */       if ((this.loopAndSwitchSet == null) || (this.loopAndSwitchSet.size() == 0))
/* 1509:     */       {
/* 1510:1552 */         if (breakLabel == null) {
/* 1511:1553 */           reportError("msg.bad.break", pos, end - pos);
/* 1512:     */         }
/* 1513:     */       }
/* 1514:     */       else {
/* 1515:1556 */         breakTarget = (Jump)this.loopAndSwitchSet.get(this.loopAndSwitchSet.size() - 1);
/* 1516:     */       }
/* 1517:     */     }
/* 1518:1560 */     BreakStatement pn = new BreakStatement(pos, end - pos);
/* 1519:1561 */     pn.setBreakLabel(breakLabel);
/* 1520:1563 */     if (breakTarget != null) {
/* 1521:1564 */       pn.setBreakTarget(breakTarget);
/* 1522:     */     }
/* 1523:1565 */     pn.setLineno(lineno);
/* 1524:1566 */     return pn;
/* 1525:     */   }
/* 1526:     */   
/* 1527:     */   private ContinueStatement continueStatement()
/* 1528:     */     throws IOException
/* 1529:     */   {
/* 1530:1572 */     if (this.currentToken != 121) {
/* 1531:1572 */       codeBug();
/* 1532:     */     }
/* 1533:1573 */     consumeToken();
/* 1534:1574 */     int lineno = this.ts.lineno;int pos = this.ts.tokenBeg;int end = this.ts.tokenEnd;
/* 1535:1575 */     Name label = null;
/* 1536:1576 */     if (peekTokenOrEOL() == 39)
/* 1537:     */     {
/* 1538:1577 */       label = createNameNode();
/* 1539:1578 */       end = getNodeEnd(label);
/* 1540:     */     }
/* 1541:1582 */     LabeledStatement labels = matchJumpLabelName();
/* 1542:1583 */     Loop target = null;
/* 1543:1584 */     if ((labels == null) && (label == null))
/* 1544:     */     {
/* 1545:1585 */       if ((this.loopSet == null) || (this.loopSet.size() == 0)) {
/* 1546:1586 */         reportError("msg.continue.outside");
/* 1547:     */       } else {
/* 1548:1588 */         target = (Loop)this.loopSet.get(this.loopSet.size() - 1);
/* 1549:     */       }
/* 1550:     */     }
/* 1551:     */     else
/* 1552:     */     {
/* 1553:1591 */       if ((labels == null) || (!(labels.getStatement() instanceof Loop))) {
/* 1554:1592 */         reportError("msg.continue.nonloop", pos, end - pos);
/* 1555:     */       }
/* 1556:1594 */       target = labels == null ? null : (Loop)labels.getStatement();
/* 1557:     */     }
/* 1558:1597 */     ContinueStatement pn = new ContinueStatement(pos, end - pos);
/* 1559:1598 */     if (target != null) {
/* 1560:1599 */       pn.setTarget(target);
/* 1561:     */     }
/* 1562:1600 */     pn.setLabel(label);
/* 1563:1601 */     pn.setLineno(lineno);
/* 1564:1602 */     return pn;
/* 1565:     */   }
/* 1566:     */   
/* 1567:     */   private WithStatement withStatement()
/* 1568:     */     throws IOException
/* 1569:     */   {
/* 1570:1608 */     if (this.currentToken != 123) {
/* 1571:1608 */       codeBug();
/* 1572:     */     }
/* 1573:1609 */     consumeToken();
/* 1574:1610 */     int lineno = this.ts.lineno;int pos = this.ts.tokenBeg;int lp = -1;int rp = -1;
/* 1575:1611 */     if (mustMatchToken(87, "msg.no.paren.with")) {
/* 1576:1612 */       lp = this.ts.tokenBeg;
/* 1577:     */     }
/* 1578:1614 */     AstNode obj = expr();
/* 1579:1616 */     if (mustMatchToken(88, "msg.no.paren.after.with")) {
/* 1580:1617 */       rp = this.ts.tokenBeg;
/* 1581:     */     }
/* 1582:1619 */     this.nestingOfWith += 1;
/* 1583:     */     AstNode body;
/* 1584:     */     try
/* 1585:     */     {
/* 1586:1622 */       body = statement();
/* 1587:     */     }
/* 1588:     */     finally
/* 1589:     */     {
/* 1590:1624 */       this.nestingOfWith -= 1;
/* 1591:     */     }
/* 1592:1627 */     WithStatement pn = new WithStatement(pos, getNodeEnd(body) - pos);
/* 1593:1628 */     pn.setJsDoc(getAndResetJsDoc());
/* 1594:1629 */     pn.setExpression(obj);
/* 1595:1630 */     pn.setStatement(body);
/* 1596:1631 */     pn.setParens(lp, rp);
/* 1597:1632 */     pn.setLineno(lineno);
/* 1598:1633 */     return pn;
/* 1599:     */   }
/* 1600:     */   
/* 1601:     */   private AstNode letStatement()
/* 1602:     */     throws IOException
/* 1603:     */   {
/* 1604:1639 */     if (this.currentToken != 153) {
/* 1605:1639 */       codeBug();
/* 1606:     */     }
/* 1607:1640 */     consumeToken();
/* 1608:1641 */     int lineno = this.ts.lineno;int pos = this.ts.tokenBeg;
/* 1609:     */     AstNode pn;
/* 1610:     */     AstNode pn;
/* 1611:1643 */     if (peekToken() == 87) {
/* 1612:1644 */       pn = let(true, pos);
/* 1613:     */     } else {
/* 1614:1646 */       pn = variables(153, pos);
/* 1615:     */     }
/* 1616:1648 */     pn.setLineno(lineno);
/* 1617:1649 */     return pn;
/* 1618:     */   }
/* 1619:     */   
/* 1620:     */   private static final boolean nowAllSet(int before, int after, int mask)
/* 1621:     */   {
/* 1622:1661 */     return ((before & mask) != mask) && ((after & mask) == mask);
/* 1623:     */   }
/* 1624:     */   
/* 1625:     */   private AstNode returnOrYield(int tt, boolean exprContext)
/* 1626:     */     throws IOException
/* 1627:     */   {
/* 1628:1667 */     if (!insideFunction()) {
/* 1629:1668 */       reportError(tt == 4 ? "msg.bad.return" : "msg.bad.yield");
/* 1630:     */     }
/* 1631:1671 */     consumeToken();
/* 1632:1672 */     int lineno = this.ts.lineno;int pos = this.ts.tokenBeg;int end = this.ts.tokenEnd;
/* 1633:     */     
/* 1634:1674 */     AstNode e = null;
/* 1635:1676 */     switch (peekTokenOrEOL())
/* 1636:     */     {
/* 1637:     */     case -1: 
/* 1638:     */     case 0: 
/* 1639:     */     case 1: 
/* 1640:     */     case 72: 
/* 1641:     */     case 82: 
/* 1642:     */     case 84: 
/* 1643:     */     case 86: 
/* 1644:     */     case 88: 
/* 1645:     */       break;
/* 1646:     */     default: 
/* 1647:1681 */       e = expr();
/* 1648:1682 */       end = getNodeEnd(e);
/* 1649:     */     }
/* 1650:1685 */     int before = this.endFlags;
/* 1651:     */     AstNode ret;
/* 1652:1688 */     if (tt == 4)
/* 1653:     */     {
/* 1654:1689 */       this.endFlags |= (e == null ? 2 : 4);
/* 1655:1690 */       AstNode ret = new ReturnStatement(pos, end - pos, e);
/* 1656:1693 */       if (nowAllSet(before, this.endFlags, 6)) {
/* 1657:1695 */         addStrictWarning("msg.return.inconsistent", "", pos, end - pos);
/* 1658:     */       }
/* 1659:     */     }
/* 1660:     */     else
/* 1661:     */     {
/* 1662:1697 */       if (!insideFunction()) {
/* 1663:1698 */         reportError("msg.bad.yield");
/* 1664:     */       }
/* 1665:1699 */       this.endFlags |= 0x8;
/* 1666:1700 */       ret = new Yield(pos, end - pos, e);
/* 1667:1701 */       setRequiresActivation();
/* 1668:1702 */       setIsGenerator();
/* 1669:1703 */       if (!exprContext) {
/* 1670:1704 */         ret = new ExpressionStatement(ret);
/* 1671:     */       }
/* 1672:     */     }
/* 1673:1709 */     if ((insideFunction()) && (nowAllSet(before, this.endFlags, 12)))
/* 1674:     */     {
/* 1675:1712 */       Name name = ((FunctionNode)this.currentScriptOrFn).getFunctionName();
/* 1676:1713 */       if ((name == null) || (name.length() == 0)) {
/* 1677:1714 */         addError("msg.anon.generator.returns", "");
/* 1678:     */       } else {
/* 1679:1716 */         addError("msg.generator.returns", name.getIdentifier());
/* 1680:     */       }
/* 1681:     */     }
/* 1682:1719 */     ret.setLineno(lineno);
/* 1683:1720 */     return ret;
/* 1684:     */   }
/* 1685:     */   
/* 1686:     */   private AstNode block()
/* 1687:     */     throws IOException
/* 1688:     */   {
/* 1689:1726 */     if (this.currentToken != 85) {
/* 1690:1726 */       codeBug();
/* 1691:     */     }
/* 1692:1727 */     consumeToken();
/* 1693:1728 */     int pos = this.ts.tokenBeg;
/* 1694:1729 */     Scope block = new Scope(pos);
/* 1695:1730 */     block.setLineno(this.ts.lineno);
/* 1696:1731 */     pushScope(block);
/* 1697:     */     try
/* 1698:     */     {
/* 1699:1733 */       statements(block);
/* 1700:1734 */       mustMatchToken(86, "msg.no.brace.block");
/* 1701:1735 */       block.setLength(this.ts.tokenEnd - pos);
/* 1702:1736 */       return block;
/* 1703:     */     }
/* 1704:     */     finally
/* 1705:     */     {
/* 1706:1738 */       popScope();
/* 1707:     */     }
/* 1708:     */   }
/* 1709:     */   
/* 1710:     */   private AstNode defaultXmlNamespace()
/* 1711:     */     throws IOException
/* 1712:     */   {
/* 1713:1745 */     if (this.currentToken != 116) {
/* 1714:1745 */       codeBug();
/* 1715:     */     }
/* 1716:1746 */     consumeToken();
/* 1717:1747 */     mustHaveXML();
/* 1718:1748 */     setRequiresActivation();
/* 1719:1749 */     int lineno = this.ts.lineno;int pos = this.ts.tokenBeg;
/* 1720:1751 */     if ((!matchToken(39)) || (!"xml".equals(this.ts.getString()))) {
/* 1721:1752 */       reportError("msg.bad.namespace");
/* 1722:     */     }
/* 1723:1754 */     if ((!matchToken(39)) || (!"namespace".equals(this.ts.getString()))) {
/* 1724:1755 */       reportError("msg.bad.namespace");
/* 1725:     */     }
/* 1726:1757 */     if (!matchToken(90)) {
/* 1727:1758 */       reportError("msg.bad.namespace");
/* 1728:     */     }
/* 1729:1761 */     AstNode e = expr();
/* 1730:1762 */     UnaryExpression dxmln = new UnaryExpression(pos, getNodeEnd(e) - pos);
/* 1731:1763 */     dxmln.setOperator(74);
/* 1732:1764 */     dxmln.setOperand(e);
/* 1733:1765 */     dxmln.setLineno(lineno);
/* 1734:     */     
/* 1735:1767 */     ExpressionStatement es = new ExpressionStatement(dxmln, true);
/* 1736:1768 */     return es;
/* 1737:     */   }
/* 1738:     */   
/* 1739:     */   private void recordLabel(Label label, LabeledStatement bundle)
/* 1740:     */     throws IOException
/* 1741:     */   {
/* 1742:1775 */     if (peekToken() != 103) {
/* 1743:1775 */       codeBug();
/* 1744:     */     }
/* 1745:1776 */     consumeToken();
/* 1746:1777 */     String name = label.getName();
/* 1747:1778 */     if (this.labelSet == null)
/* 1748:     */     {
/* 1749:1779 */       this.labelSet = new HashMap();
/* 1750:     */     }
/* 1751:     */     else
/* 1752:     */     {
/* 1753:1781 */       LabeledStatement ls = (LabeledStatement)this.labelSet.get(name);
/* 1754:1782 */       if (ls != null)
/* 1755:     */       {
/* 1756:1783 */         if (this.compilerEnv.isIdeMode())
/* 1757:     */         {
/* 1758:1784 */           Label dup = ls.getLabelByName(name);
/* 1759:1785 */           reportError("msg.dup.label", dup.getAbsolutePosition(), dup.getLength());
/* 1760:     */         }
/* 1761:1788 */         reportError("msg.dup.label", label.getPosition(), label.getLength());
/* 1762:     */       }
/* 1763:     */     }
/* 1764:1792 */     bundle.addLabel(label);
/* 1765:1793 */     this.labelSet.put(name, bundle);
/* 1766:     */   }
/* 1767:     */   
/* 1768:     */   private AstNode nameOrLabel()
/* 1769:     */     throws IOException
/* 1770:     */   {
/* 1771:1805 */     if (this.currentToken != 39) {
/* 1772:1805 */       throw codeBug();
/* 1773:     */     }
/* 1774:1806 */     int pos = this.ts.tokenBeg;
/* 1775:     */     
/* 1776:     */ 
/* 1777:1809 */     this.currentFlaggedToken |= 0x20000;
/* 1778:1810 */     AstNode expr = expr();
/* 1779:1812 */     if (expr.getType() != 130)
/* 1780:     */     {
/* 1781:1813 */       AstNode n = new ExpressionStatement(expr, !insideFunction());
/* 1782:1814 */       n.lineno = expr.lineno;
/* 1783:1815 */       return n;
/* 1784:     */     }
/* 1785:1818 */     LabeledStatement bundle = new LabeledStatement(pos);
/* 1786:1819 */     recordLabel((Label)expr, bundle);
/* 1787:1820 */     bundle.setLineno(this.ts.lineno);
/* 1788:     */     
/* 1789:1822 */     AstNode stmt = null;
/* 1790:1823 */     while (peekToken() == 39)
/* 1791:     */     {
/* 1792:1824 */       this.currentFlaggedToken |= 0x20000;
/* 1793:1825 */       expr = expr();
/* 1794:1826 */       if (expr.getType() != 130)
/* 1795:     */       {
/* 1796:1827 */         stmt = new ExpressionStatement(expr, !insideFunction());
/* 1797:1828 */         autoInsertSemicolon(stmt);
/* 1798:1829 */         break;
/* 1799:     */       }
/* 1800:1831 */       recordLabel((Label)expr, bundle);
/* 1801:     */     }
/* 1802:     */     try
/* 1803:     */     {
/* 1804:1836 */       this.currentLabel = bundle;
/* 1805:1837 */       if (stmt == null) {
/* 1806:1838 */         stmt = statementHelper();
/* 1807:     */       }
/* 1808:     */     }
/* 1809:     */     finally
/* 1810:     */     {
/* 1811:     */       Iterator i$;
/* 1812:     */       Label lb;
/* 1813:1841 */       this.currentLabel = null;
/* 1814:1843 */       for (Label lb : bundle.getLabels()) {
/* 1815:1844 */         this.labelSet.remove(lb.getName());
/* 1816:     */       }
/* 1817:     */     }
/* 1818:1848 */     bundle.setLength(getNodeEnd(stmt) - pos);
/* 1819:1849 */     bundle.setStatement(stmt);
/* 1820:1850 */     return bundle;
/* 1821:     */   }
/* 1822:     */   
/* 1823:     */   private VariableDeclaration variables(int declType, int pos)
/* 1824:     */     throws IOException
/* 1825:     */   {
/* 1826:1867 */     VariableDeclaration pn = new VariableDeclaration(pos);
/* 1827:1868 */     pn.setType(declType);
/* 1828:1869 */     pn.setLineno(this.ts.lineno);
/* 1829:1870 */     String varjsdoc = getAndResetJsDoc();
/* 1830:1871 */     if (varjsdoc != null) {
/* 1831:1872 */       pn.setJsDoc(varjsdoc);
/* 1832:     */     }
/* 1833:     */     int end;
/* 1834:     */     for (;;)
/* 1835:     */     {
/* 1836:1878 */       AstNode destructuring = null;
/* 1837:1879 */       Name name = null;
/* 1838:1880 */       int tt = peekToken();int kidPos = this.ts.tokenBeg;
/* 1839:1881 */       end = this.ts.tokenEnd;
/* 1840:1883 */       if ((tt == 83) || (tt == 85))
/* 1841:     */       {
/* 1842:1885 */         destructuring = destructuringPrimaryExpr();
/* 1843:1886 */         end = getNodeEnd(destructuring);
/* 1844:1887 */         if (!(destructuring instanceof DestructuringForm)) {
/* 1845:1888 */           reportError("msg.bad.assign.left", kidPos, end - kidPos);
/* 1846:     */         }
/* 1847:1889 */         markDestructuring(destructuring);
/* 1848:     */       }
/* 1849:     */       else
/* 1850:     */       {
/* 1851:1892 */         mustMatchToken(39, "msg.bad.var");
/* 1852:1893 */         name = createNameNode();
/* 1853:1894 */         name.setLineno(this.ts.getLineno());
/* 1854:1895 */         if (this.inUseStrictDirective)
/* 1855:     */         {
/* 1856:1896 */           String id = this.ts.getString();
/* 1857:1897 */           if (("eval".equals(id)) || ("arguments".equals(this.ts.getString()))) {
/* 1858:1899 */             reportError("msg.bad.id.strict", id);
/* 1859:     */           }
/* 1860:     */         }
/* 1861:1902 */         defineSymbol(declType, this.ts.getString(), this.inForInit);
/* 1862:     */       }
/* 1863:1905 */       int lineno = this.ts.lineno;
/* 1864:     */       
/* 1865:1907 */       String jsdoc = getAndResetJsDoc();
/* 1866:     */       
/* 1867:1909 */       AstNode init = null;
/* 1868:1910 */       if (matchToken(90))
/* 1869:     */       {
/* 1870:1911 */         init = assignExpr();
/* 1871:1912 */         end = getNodeEnd(init);
/* 1872:     */       }
/* 1873:1915 */       VariableInitializer vi = new VariableInitializer(kidPos, end - kidPos);
/* 1874:1916 */       if (destructuring != null)
/* 1875:     */       {
/* 1876:1917 */         if ((init == null) && (!this.inForInit)) {
/* 1877:1918 */           reportError("msg.destruct.assign.no.init");
/* 1878:     */         }
/* 1879:1920 */         vi.setTarget(destructuring);
/* 1880:     */       }
/* 1881:     */       else
/* 1882:     */       {
/* 1883:1922 */         vi.setTarget(name);
/* 1884:     */       }
/* 1885:1924 */       vi.setInitializer(init);
/* 1886:1925 */       vi.setType(declType);
/* 1887:1926 */       vi.setJsDoc(jsdoc);
/* 1888:1927 */       vi.setLineno(lineno);
/* 1889:1928 */       pn.addVariable(vi);
/* 1890:1930 */       if (!matchToken(89)) {
/* 1891:     */         break;
/* 1892:     */       }
/* 1893:     */     }
/* 1894:1933 */     pn.setLength(end - pos);
/* 1895:1934 */     return pn;
/* 1896:     */   }
/* 1897:     */   
/* 1898:     */   private AstNode let(boolean isStatement, int pos)
/* 1899:     */     throws IOException
/* 1900:     */   {
/* 1901:1941 */     LetNode pn = new LetNode(pos);
/* 1902:1942 */     pn.setLineno(this.ts.lineno);
/* 1903:1943 */     if (mustMatchToken(87, "msg.no.paren.after.let")) {
/* 1904:1944 */       pn.setLp(this.ts.tokenBeg - pos);
/* 1905:     */     }
/* 1906:1945 */     pushScope(pn);
/* 1907:     */     try
/* 1908:     */     {
/* 1909:1947 */       VariableDeclaration vars = variables(153, this.ts.tokenBeg);
/* 1910:1948 */       pn.setVariables(vars);
/* 1911:1949 */       if (mustMatchToken(88, "msg.no.paren.let")) {
/* 1912:1950 */         pn.setRp(this.ts.tokenBeg - pos);
/* 1913:     */       }
/* 1914:1952 */       if ((isStatement) && (peekToken() == 85))
/* 1915:     */       {
/* 1916:1954 */         consumeToken();
/* 1917:1955 */         int beg = this.ts.tokenBeg;
/* 1918:1956 */         AstNode stmt = statements();
/* 1919:1957 */         mustMatchToken(86, "msg.no.curly.let");
/* 1920:1958 */         stmt.setLength(this.ts.tokenEnd - beg);
/* 1921:1959 */         pn.setLength(this.ts.tokenEnd - pos);
/* 1922:1960 */         pn.setBody(stmt);
/* 1923:1961 */         pn.setType(153);
/* 1924:     */       }
/* 1925:     */       else
/* 1926:     */       {
/* 1927:1964 */         AstNode expr = expr();
/* 1928:1965 */         pn.setLength(getNodeEnd(expr) - pos);
/* 1929:1966 */         pn.setBody(expr);
/* 1930:1967 */         if (isStatement)
/* 1931:     */         {
/* 1932:1969 */           ExpressionStatement es = new ExpressionStatement(pn, !insideFunction());
/* 1933:     */           
/* 1934:1971 */           es.setLineno(pn.getLineno());
/* 1935:1972 */           return es;
/* 1936:     */         }
/* 1937:     */       }
/* 1938:     */     }
/* 1939:     */     finally
/* 1940:     */     {
/* 1941:1976 */       popScope();
/* 1942:     */     }
/* 1943:1978 */     return pn;
/* 1944:     */   }
/* 1945:     */   
/* 1946:     */   void defineSymbol(int declType, String name)
/* 1947:     */   {
/* 1948:1982 */     defineSymbol(declType, name, false);
/* 1949:     */   }
/* 1950:     */   
/* 1951:     */   void defineSymbol(int declType, String name, boolean ignoreNotInBlock)
/* 1952:     */   {
/* 1953:1986 */     if (name == null)
/* 1954:     */     {
/* 1955:1987 */       if (this.compilerEnv.isIdeMode()) {
/* 1956:1988 */         return;
/* 1957:     */       }
/* 1958:1990 */       codeBug();
/* 1959:     */     }
/* 1960:1993 */     Scope definingScope = this.currentScope.getDefiningScope(name);
/* 1961:1994 */     Symbol symbol = definingScope != null ? definingScope.getSymbol(name) : null;
/* 1962:     */     
/* 1963:     */ 
/* 1964:1997 */     int symDeclType = symbol != null ? symbol.getDeclType() : -1;
/* 1965:1998 */     if ((symbol != null) && ((symDeclType == 154) || (declType == 154) || ((definingScope == this.currentScope) && (symDeclType == 153))))
/* 1966:     */     {
/* 1967:2003 */       addError(symDeclType == 109 ? "msg.fn.redecl" : symDeclType == 122 ? "msg.var.redecl" : symDeclType == 153 ? "msg.let.redecl" : symDeclType == 154 ? "msg.const.redecl" : "msg.parm.redecl", name);
/* 1968:     */       
/* 1969:     */ 
/* 1970:     */ 
/* 1971:     */ 
/* 1972:2008 */       return;
/* 1973:     */     }
/* 1974:2010 */     switch (declType)
/* 1975:     */     {
/* 1976:     */     case 153: 
/* 1977:2012 */       if ((!ignoreNotInBlock) && ((this.currentScope.getType() == 112) || ((this.currentScope instanceof Loop))))
/* 1978:     */       {
/* 1979:2015 */         addError("msg.let.decl.not.in.block");
/* 1980:2016 */         return;
/* 1981:     */       }
/* 1982:2018 */       this.currentScope.putSymbol(new Symbol(declType, name));
/* 1983:2019 */       return;
/* 1984:     */     case 109: 
/* 1985:     */     case 122: 
/* 1986:     */     case 154: 
/* 1987:2024 */       if (symbol != null)
/* 1988:     */       {
/* 1989:2025 */         if (symDeclType == 122) {
/* 1990:2026 */           addStrictWarning("msg.var.redecl", name);
/* 1991:2027 */         } else if (symDeclType == 87) {
/* 1992:2028 */           addStrictWarning("msg.var.hides.arg", name);
/* 1993:     */         }
/* 1994:     */       }
/* 1995:     */       else {
/* 1996:2031 */         this.currentScriptOrFn.putSymbol(new Symbol(declType, name));
/* 1997:     */       }
/* 1998:2033 */       return;
/* 1999:     */     case 87: 
/* 2000:2036 */       if (symbol != null) {
/* 2001:2039 */         addWarning("msg.dup.parms", name);
/* 2002:     */       }
/* 2003:2041 */       this.currentScriptOrFn.putSymbol(new Symbol(declType, name));
/* 2004:2042 */       return;
/* 2005:     */     }
/* 2006:2045 */     throw codeBug();
/* 2007:     */   }
/* 2008:     */   
/* 2009:     */   private AstNode expr()
/* 2010:     */     throws IOException
/* 2011:     */   {
/* 2012:2052 */     AstNode pn = assignExpr();
/* 2013:2053 */     int pos = pn.getPosition();
/* 2014:2054 */     while (matchToken(89))
/* 2015:     */     {
/* 2016:2055 */       int lineno = this.ts.lineno;
/* 2017:2056 */       int opPos = this.ts.tokenBeg;
/* 2018:2057 */       if ((this.compilerEnv.isStrictMode()) && (!pn.hasSideEffects())) {
/* 2019:2058 */         addStrictWarning("msg.no.side.effects", "", pos, nodeEnd(pn) - pos);
/* 2020:     */       }
/* 2021:2060 */       if (peekToken() == 72) {
/* 2022:2061 */         reportError("msg.yield.parenthesized");
/* 2023:     */       }
/* 2024:2062 */       pn = new InfixExpression(89, pn, assignExpr(), opPos);
/* 2025:2063 */       pn.setLineno(lineno);
/* 2026:     */     }
/* 2027:2065 */     return pn;
/* 2028:     */   }
/* 2029:     */   
/* 2030:     */   private AstNode assignExpr()
/* 2031:     */     throws IOException
/* 2032:     */   {
/* 2033:2071 */     int tt = peekToken();
/* 2034:2072 */     if (tt == 72) {
/* 2035:2073 */       return returnOrYield(tt, true);
/* 2036:     */     }
/* 2037:2075 */     AstNode pn = condExpr();
/* 2038:2076 */     tt = peekToken();
/* 2039:2077 */     if ((90 <= tt) && (tt <= 101))
/* 2040:     */     {
/* 2041:2078 */       consumeToken();
/* 2042:     */       
/* 2043:     */ 
/* 2044:2081 */       String jsdoc = getAndResetJsDoc();
/* 2045:     */       
/* 2046:2083 */       markDestructuring(pn);
/* 2047:2084 */       int opPos = this.ts.tokenBeg;
/* 2048:2085 */       int opLineno = this.ts.getLineno();
/* 2049:     */       
/* 2050:2087 */       pn = new Assignment(tt, pn, assignExpr(), opPos);
/* 2051:     */       
/* 2052:2089 */       pn.setLineno(opLineno);
/* 2053:2090 */       if (jsdoc != null) {
/* 2054:2091 */         pn.setJsDoc(jsdoc);
/* 2055:     */       }
/* 2056:     */     }
/* 2057:2093 */     else if ((tt == 82) && (pn.getType() == 33))
/* 2058:     */     {
/* 2059:2096 */       if (this.currentJsDocComment != null) {
/* 2060:2097 */         pn.setJsDoc(getAndResetJsDoc());
/* 2061:     */       }
/* 2062:     */     }
/* 2063:2100 */     return pn;
/* 2064:     */   }
/* 2065:     */   
/* 2066:     */   private AstNode condExpr()
/* 2067:     */     throws IOException
/* 2068:     */   {
/* 2069:2106 */     AstNode pn = orExpr();
/* 2070:2107 */     if (matchToken(102))
/* 2071:     */     {
/* 2072:2108 */       int line = this.ts.lineno;
/* 2073:2109 */       int qmarkPos = this.ts.tokenBeg;int colonPos = -1;
/* 2074:2110 */       AstNode ifTrue = assignExpr();
/* 2075:2111 */       if (mustMatchToken(103, "msg.no.colon.cond")) {
/* 2076:2112 */         colonPos = this.ts.tokenBeg;
/* 2077:     */       }
/* 2078:2113 */       AstNode ifFalse = assignExpr();
/* 2079:2114 */       int beg = pn.getPosition();int len = getNodeEnd(ifFalse) - beg;
/* 2080:2115 */       ConditionalExpression ce = new ConditionalExpression(beg, len);
/* 2081:2116 */       ce.setLineno(line);
/* 2082:2117 */       ce.setTestExpression(pn);
/* 2083:2118 */       ce.setTrueExpression(ifTrue);
/* 2084:2119 */       ce.setFalseExpression(ifFalse);
/* 2085:2120 */       ce.setQuestionMarkPosition(qmarkPos - beg);
/* 2086:2121 */       ce.setColonPosition(colonPos - beg);
/* 2087:2122 */       pn = ce;
/* 2088:     */     }
/* 2089:2124 */     return pn;
/* 2090:     */   }
/* 2091:     */   
/* 2092:     */   private AstNode orExpr()
/* 2093:     */     throws IOException
/* 2094:     */   {
/* 2095:2130 */     AstNode pn = andExpr();
/* 2096:2131 */     if (matchToken(104))
/* 2097:     */     {
/* 2098:2132 */       int opPos = this.ts.tokenBeg;
/* 2099:2133 */       int lineno = this.ts.lineno;
/* 2100:2134 */       pn = new InfixExpression(104, pn, orExpr(), opPos);
/* 2101:2135 */       pn.setLineno(lineno);
/* 2102:     */     }
/* 2103:2137 */     return pn;
/* 2104:     */   }
/* 2105:     */   
/* 2106:     */   private AstNode andExpr()
/* 2107:     */     throws IOException
/* 2108:     */   {
/* 2109:2143 */     AstNode pn = bitOrExpr();
/* 2110:2144 */     if (matchToken(105))
/* 2111:     */     {
/* 2112:2145 */       int opPos = this.ts.tokenBeg;
/* 2113:2146 */       int lineno = this.ts.lineno;
/* 2114:2147 */       pn = new InfixExpression(105, pn, andExpr(), opPos);
/* 2115:2148 */       pn.setLineno(lineno);
/* 2116:     */     }
/* 2117:2150 */     return pn;
/* 2118:     */   }
/* 2119:     */   
/* 2120:     */   private AstNode bitOrExpr()
/* 2121:     */     throws IOException
/* 2122:     */   {
/* 2123:2156 */     AstNode pn = bitXorExpr();
/* 2124:2157 */     while (matchToken(9))
/* 2125:     */     {
/* 2126:2158 */       int opPos = this.ts.tokenBeg;
/* 2127:2159 */       int lineno = this.ts.lineno;
/* 2128:2160 */       pn = new InfixExpression(9, pn, bitXorExpr(), opPos);
/* 2129:2161 */       pn.setLineno(lineno);
/* 2130:     */     }
/* 2131:2163 */     return pn;
/* 2132:     */   }
/* 2133:     */   
/* 2134:     */   private AstNode bitXorExpr()
/* 2135:     */     throws IOException
/* 2136:     */   {
/* 2137:2169 */     AstNode pn = bitAndExpr();
/* 2138:2170 */     while (matchToken(10))
/* 2139:     */     {
/* 2140:2171 */       int opPos = this.ts.tokenBeg;
/* 2141:2172 */       int lineno = this.ts.lineno;
/* 2142:2173 */       pn = new InfixExpression(10, pn, bitAndExpr(), opPos);
/* 2143:2174 */       pn.setLineno(lineno);
/* 2144:     */     }
/* 2145:2176 */     return pn;
/* 2146:     */   }
/* 2147:     */   
/* 2148:     */   private AstNode bitAndExpr()
/* 2149:     */     throws IOException
/* 2150:     */   {
/* 2151:2182 */     AstNode pn = eqExpr();
/* 2152:2183 */     while (matchToken(11))
/* 2153:     */     {
/* 2154:2184 */       int opPos = this.ts.tokenBeg;
/* 2155:2185 */       int lineno = this.ts.lineno;
/* 2156:2186 */       pn = new InfixExpression(11, pn, eqExpr(), opPos);
/* 2157:2187 */       pn.setLineno(lineno);
/* 2158:     */     }
/* 2159:2189 */     return pn;
/* 2160:     */   }
/* 2161:     */   
/* 2162:     */   private AstNode eqExpr()
/* 2163:     */     throws IOException
/* 2164:     */   {
/* 2165:2195 */     AstNode pn = relExpr();
/* 2166:     */     for (;;)
/* 2167:     */     {
/* 2168:2197 */       int tt = peekToken();int opPos = this.ts.tokenBeg;
/* 2169:2198 */       int lineno = this.ts.lineno;
/* 2170:2199 */       switch (tt)
/* 2171:     */       {
/* 2172:     */       case 12: 
/* 2173:     */       case 13: 
/* 2174:     */       case 46: 
/* 2175:     */       case 47: 
/* 2176:2204 */         consumeToken();
/* 2177:2205 */         int parseToken = tt;
/* 2178:2206 */         if (this.compilerEnv.getLanguageVersion() == 120) {
/* 2179:2208 */           if (tt == 12) {
/* 2180:2209 */             parseToken = 46;
/* 2181:2210 */           } else if (tt == 13) {
/* 2182:2211 */             parseToken = 47;
/* 2183:     */           }
/* 2184:     */         }
/* 2185:2213 */         pn = new InfixExpression(parseToken, pn, relExpr(), opPos);
/* 2186:2214 */         pn.setLineno(lineno);
/* 2187:     */       }
/* 2188:     */     }
/* 2189:2219 */     return pn;
/* 2190:     */   }
/* 2191:     */   
/* 2192:     */   private AstNode relExpr()
/* 2193:     */     throws IOException
/* 2194:     */   {
/* 2195:2225 */     AstNode pn = shiftExpr();
/* 2196:     */     for (;;)
/* 2197:     */     {
/* 2198:2227 */       int tt = peekToken();int opPos = this.ts.tokenBeg;
/* 2199:2228 */       int line = this.ts.lineno;
/* 2200:2229 */       switch (tt)
/* 2201:     */       {
/* 2202:     */       case 52: 
/* 2203:2231 */         if (this.inForInit) {
/* 2204:     */           break;
/* 2205:     */         }
/* 2206:     */       case 14: 
/* 2207:     */       case 15: 
/* 2208:     */       case 16: 
/* 2209:     */       case 17: 
/* 2210:     */       case 53: 
/* 2211:2239 */         consumeToken();
/* 2212:2240 */         pn = new InfixExpression(tt, pn, shiftExpr(), opPos);
/* 2213:2241 */         pn.setLineno(line);
/* 2214:     */       }
/* 2215:     */     }
/* 2216:2246 */     return pn;
/* 2217:     */   }
/* 2218:     */   
/* 2219:     */   private AstNode shiftExpr()
/* 2220:     */     throws IOException
/* 2221:     */   {
/* 2222:2252 */     AstNode pn = addExpr();
/* 2223:     */     for (;;)
/* 2224:     */     {
/* 2225:2254 */       int tt = peekToken();int opPos = this.ts.tokenBeg;
/* 2226:2255 */       int lineno = this.ts.lineno;
/* 2227:2256 */       switch (tt)
/* 2228:     */       {
/* 2229:     */       case 18: 
/* 2230:     */       case 19: 
/* 2231:     */       case 20: 
/* 2232:2260 */         consumeToken();
/* 2233:2261 */         pn = new InfixExpression(tt, pn, addExpr(), opPos);
/* 2234:2262 */         pn.setLineno(lineno);
/* 2235:     */       }
/* 2236:     */     }
/* 2237:2267 */     return pn;
/* 2238:     */   }
/* 2239:     */   
/* 2240:     */   private AstNode addExpr()
/* 2241:     */     throws IOException
/* 2242:     */   {
/* 2243:2273 */     AstNode pn = mulExpr();
/* 2244:     */     for (;;)
/* 2245:     */     {
/* 2246:2275 */       int tt = peekToken();int opPos = this.ts.tokenBeg;
/* 2247:2276 */       if ((tt != 21) && (tt != 22)) {
/* 2248:     */         break;
/* 2249:     */       }
/* 2250:2277 */       consumeToken();
/* 2251:2278 */       int lineno = this.ts.lineno;
/* 2252:2279 */       pn = new InfixExpression(tt, pn, mulExpr(), opPos);
/* 2253:2280 */       pn.setLineno(lineno);
/* 2254:     */     }
/* 2255:2285 */     return pn;
/* 2256:     */   }
/* 2257:     */   
/* 2258:     */   private AstNode mulExpr()
/* 2259:     */     throws IOException
/* 2260:     */   {
/* 2261:2291 */     AstNode pn = unaryExpr();
/* 2262:     */     for (;;)
/* 2263:     */     {
/* 2264:2293 */       int tt = peekToken();int opPos = this.ts.tokenBeg;
/* 2265:2294 */       switch (tt)
/* 2266:     */       {
/* 2267:     */       case 23: 
/* 2268:     */       case 24: 
/* 2269:     */       case 25: 
/* 2270:2298 */         consumeToken();
/* 2271:2299 */         int line = this.ts.lineno;
/* 2272:2300 */         pn = new InfixExpression(tt, pn, unaryExpr(), opPos);
/* 2273:2301 */         pn.setLineno(line);
/* 2274:     */       }
/* 2275:     */     }
/* 2276:2306 */     return pn;
/* 2277:     */   }
/* 2278:     */   
/* 2279:     */   private AstNode unaryExpr()
/* 2280:     */     throws IOException
/* 2281:     */   {
/* 2282:2313 */     int tt = peekToken();
/* 2283:2314 */     int line = this.ts.lineno;
/* 2284:     */     AstNode node;
/* 2285:2316 */     switch (tt)
/* 2286:     */     {
/* 2287:     */     case 26: 
/* 2288:     */     case 27: 
/* 2289:     */     case 32: 
/* 2290:     */     case 126: 
/* 2291:2321 */       consumeToken();
/* 2292:2322 */       node = new UnaryExpression(tt, this.ts.tokenBeg, unaryExpr());
/* 2293:2323 */       node.setLineno(line);
/* 2294:2324 */       return node;
/* 2295:     */     case 21: 
/* 2296:2327 */       consumeToken();
/* 2297:     */       
/* 2298:2329 */       node = new UnaryExpression(28, this.ts.tokenBeg, unaryExpr());
/* 2299:2330 */       node.setLineno(line);
/* 2300:2331 */       return node;
/* 2301:     */     case 22: 
/* 2302:2334 */       consumeToken();
/* 2303:     */       
/* 2304:2336 */       node = new UnaryExpression(29, this.ts.tokenBeg, unaryExpr());
/* 2305:2337 */       node.setLineno(line);
/* 2306:2338 */       return node;
/* 2307:     */     case 106: 
/* 2308:     */     case 107: 
/* 2309:2342 */       consumeToken();
/* 2310:2343 */       UnaryExpression expr = new UnaryExpression(tt, this.ts.tokenBeg, memberExpr(true));
/* 2311:     */       
/* 2312:2345 */       expr.setLineno(line);
/* 2313:2346 */       checkBadIncDec(expr);
/* 2314:2347 */       return expr;
/* 2315:     */     case 31: 
/* 2316:2350 */       consumeToken();
/* 2317:2351 */       node = new UnaryExpression(tt, this.ts.tokenBeg, unaryExpr());
/* 2318:2352 */       node.setLineno(line);
/* 2319:2353 */       return node;
/* 2320:     */     case -1: 
/* 2321:2356 */       consumeToken();
/* 2322:2357 */       return makeErrorNode();
/* 2323:     */     case 14: 
/* 2324:2361 */       if (this.compilerEnv.isXmlAvailable())
/* 2325:     */       {
/* 2326:2362 */         consumeToken();
/* 2327:2363 */         return memberExprTail(true, xmlInitializer());
/* 2328:     */       }
/* 2329:     */       break;
/* 2330:     */     }
/* 2331:2368 */     AstNode pn = memberExpr(true);
/* 2332:     */     
/* 2333:2370 */     tt = peekTokenOrEOL();
/* 2334:2371 */     if ((tt != 106) && (tt != 107)) {
/* 2335:2372 */       return pn;
/* 2336:     */     }
/* 2337:2374 */     consumeToken();
/* 2338:2375 */     UnaryExpression uexpr = new UnaryExpression(tt, this.ts.tokenBeg, pn, true);
/* 2339:     */     
/* 2340:2377 */     uexpr.setLineno(line);
/* 2341:2378 */     checkBadIncDec(uexpr);
/* 2342:2379 */     return uexpr;
/* 2343:     */   }
/* 2344:     */   
/* 2345:     */   private AstNode xmlInitializer()
/* 2346:     */     throws IOException
/* 2347:     */   {
/* 2348:2386 */     if (this.currentToken != 14) {
/* 2349:2386 */       codeBug();
/* 2350:     */     }
/* 2351:2387 */     int pos = this.ts.tokenBeg;int tt = this.ts.getFirstXMLToken();
/* 2352:2388 */     if ((tt != 145) && (tt != 148))
/* 2353:     */     {
/* 2354:2389 */       reportError("msg.syntax");
/* 2355:2390 */       return makeErrorNode();
/* 2356:     */     }
/* 2357:2393 */     XmlLiteral pn = new XmlLiteral(pos);
/* 2358:2394 */     pn.setLineno(this.ts.lineno);
/* 2359:2396 */     for (;; tt = this.ts.getNextXMLToken()) {
/* 2360:2397 */       switch (tt)
/* 2361:     */       {
/* 2362:     */       case 145: 
/* 2363:2399 */         pn.addFragment(new XmlString(this.ts.tokenBeg, this.ts.getString()));
/* 2364:2400 */         mustMatchToken(85, "msg.syntax");
/* 2365:2401 */         int beg = this.ts.tokenBeg;
/* 2366:2402 */         AstNode expr = peekToken() == 86 ? new EmptyExpression(beg, this.ts.tokenEnd - beg) : expr();
/* 2367:     */         
/* 2368:     */ 
/* 2369:2405 */         mustMatchToken(86, "msg.syntax");
/* 2370:2406 */         XmlExpression xexpr = new XmlExpression(beg, expr);
/* 2371:2407 */         xexpr.setIsXmlAttribute(this.ts.isXMLAttribute());
/* 2372:2408 */         xexpr.setLength(this.ts.tokenEnd - beg);
/* 2373:2409 */         pn.addFragment(xexpr);
/* 2374:2410 */         break;
/* 2375:     */       case 148: 
/* 2376:2413 */         pn.addFragment(new XmlString(this.ts.tokenBeg, this.ts.getString()));
/* 2377:2414 */         return pn;
/* 2378:     */       default: 
/* 2379:2417 */         reportError("msg.syntax");
/* 2380:2418 */         return makeErrorNode();
/* 2381:     */       }
/* 2382:     */     }
/* 2383:     */   }
/* 2384:     */   
/* 2385:     */   private List<AstNode> argumentList()
/* 2386:     */     throws IOException
/* 2387:     */   {
/* 2388:2426 */     if (matchToken(88)) {
/* 2389:2427 */       return null;
/* 2390:     */     }
/* 2391:2429 */     List<AstNode> result = new ArrayList();
/* 2392:2430 */     boolean wasInForInit = this.inForInit;
/* 2393:2431 */     this.inForInit = false;
/* 2394:     */     try
/* 2395:     */     {
/* 2396:     */       do
/* 2397:     */       {
/* 2398:2434 */         if (peekToken() == 72) {
/* 2399:2435 */           reportError("msg.yield.parenthesized");
/* 2400:     */         }
/* 2401:2436 */         result.add(assignExpr());
/* 2402:2437 */       } while (matchToken(89));
/* 2403:     */     }
/* 2404:     */     finally
/* 2405:     */     {
/* 2406:2439 */       this.inForInit = wasInForInit;
/* 2407:     */     }
/* 2408:2442 */     mustMatchToken(88, "msg.no.paren.arg");
/* 2409:2443 */     return result;
/* 2410:     */   }
/* 2411:     */   
/* 2412:     */   private AstNode memberExpr(boolean allowCallSyntax)
/* 2413:     */     throws IOException
/* 2414:     */   {
/* 2415:2454 */     int tt = peekToken();int lineno = this.ts.lineno;
/* 2416:     */     AstNode pn;
/* 2417:     */     AstNode pn;
/* 2418:2457 */     if (tt != 30)
/* 2419:     */     {
/* 2420:2458 */       pn = primaryExpr();
/* 2421:     */     }
/* 2422:     */     else
/* 2423:     */     {
/* 2424:2460 */       consumeToken();
/* 2425:2461 */       int pos = this.ts.tokenBeg;
/* 2426:2462 */       NewExpression nx = new NewExpression(pos);
/* 2427:     */       
/* 2428:2464 */       AstNode target = memberExpr(false);
/* 2429:2465 */       int end = getNodeEnd(target);
/* 2430:2466 */       nx.setTarget(target);
/* 2431:     */       
/* 2432:2468 */       int lp = -1;
/* 2433:2469 */       if (matchToken(87))
/* 2434:     */       {
/* 2435:2470 */         lp = this.ts.tokenBeg;
/* 2436:2471 */         List<AstNode> args = argumentList();
/* 2437:2472 */         if ((args != null) && (args.size() > 65536)) {
/* 2438:2473 */           reportError("msg.too.many.constructor.args");
/* 2439:     */         }
/* 2440:2474 */         int rp = this.ts.tokenBeg;
/* 2441:2475 */         end = this.ts.tokenEnd;
/* 2442:2476 */         if (args != null) {
/* 2443:2477 */           nx.setArguments(args);
/* 2444:     */         }
/* 2445:2478 */         nx.setParens(lp - pos, rp - pos);
/* 2446:     */       }
/* 2447:2485 */       if (matchToken(85))
/* 2448:     */       {
/* 2449:2486 */         ObjectLiteral initializer = objectLiteral();
/* 2450:2487 */         end = getNodeEnd(initializer);
/* 2451:2488 */         nx.setInitializer(initializer);
/* 2452:     */       }
/* 2453:2490 */       nx.setLength(end - pos);
/* 2454:2491 */       pn = nx;
/* 2455:     */     }
/* 2456:2493 */     pn.setLineno(lineno);
/* 2457:2494 */     AstNode tail = memberExprTail(allowCallSyntax, pn);
/* 2458:2495 */     return tail;
/* 2459:     */   }
/* 2460:     */   
/* 2461:     */   private AstNode memberExprTail(boolean allowCallSyntax, AstNode pn)
/* 2462:     */     throws IOException
/* 2463:     */   {
/* 2464:2509 */     if (pn == null) {
/* 2465:2509 */       codeBug();
/* 2466:     */     }
/* 2467:2510 */     int pos = pn.getPosition();
/* 2468:     */     for (;;)
/* 2469:     */     {
/* 2470:2514 */       int tt = peekToken();
/* 2471:     */       int lineno;
/* 2472:     */       int end;
/* 2473:2515 */       switch (tt)
/* 2474:     */       {
/* 2475:     */       case 108: 
/* 2476:     */       case 143: 
/* 2477:2518 */         lineno = this.ts.lineno;
/* 2478:2519 */         pn = propertyAccess(tt, pn);
/* 2479:2520 */         pn.setLineno(lineno);
/* 2480:2521 */         break;
/* 2481:     */       case 146: 
/* 2482:2524 */         consumeToken();
/* 2483:2525 */         int opPos = this.ts.tokenBeg;int rp = -1;
/* 2484:2526 */         lineno = this.ts.lineno;
/* 2485:2527 */         mustHaveXML();
/* 2486:2528 */         setRequiresActivation();
/* 2487:2529 */         AstNode filter = expr();
/* 2488:2530 */         end = getNodeEnd(filter);
/* 2489:2531 */         if (mustMatchToken(88, "msg.no.paren"))
/* 2490:     */         {
/* 2491:2532 */           rp = this.ts.tokenBeg;
/* 2492:2533 */           end = this.ts.tokenEnd;
/* 2493:     */         }
/* 2494:2535 */         XmlDotQuery q = new XmlDotQuery(pos, end - pos);
/* 2495:2536 */         q.setLeft(pn);
/* 2496:2537 */         q.setRight(filter);
/* 2497:2538 */         q.setOperatorPosition(opPos);
/* 2498:2539 */         q.setRp(rp - pos);
/* 2499:2540 */         q.setLineno(lineno);
/* 2500:2541 */         pn = q;
/* 2501:2542 */         break;
/* 2502:     */       case 83: 
/* 2503:2545 */         consumeToken();
/* 2504:2546 */         int lb = this.ts.tokenBeg;int rb = -1;
/* 2505:2547 */         lineno = this.ts.lineno;
/* 2506:2548 */         AstNode expr = expr();
/* 2507:2549 */         end = getNodeEnd(expr);
/* 2508:2550 */         if (mustMatchToken(84, "msg.no.bracket.index"))
/* 2509:     */         {
/* 2510:2551 */           rb = this.ts.tokenBeg;
/* 2511:2552 */           end = this.ts.tokenEnd;
/* 2512:     */         }
/* 2513:2554 */         ElementGet g = new ElementGet(pos, end - pos);
/* 2514:2555 */         g.setTarget(pn);
/* 2515:2556 */         g.setElement(expr);
/* 2516:2557 */         g.setParens(lb, rb);
/* 2517:2558 */         g.setLineno(lineno);
/* 2518:2559 */         pn = g;
/* 2519:2560 */         break;
/* 2520:     */       case 87: 
/* 2521:2563 */         if (!allowCallSyntax) {
/* 2522:     */           return pn;
/* 2523:     */         }
/* 2524:2566 */         lineno = this.ts.lineno;
/* 2525:2567 */         consumeToken();
/* 2526:2568 */         checkCallRequiresActivation(pn);
/* 2527:2569 */         FunctionCall f = new FunctionCall(pos);
/* 2528:2570 */         f.setTarget(pn);
/* 2529:     */         
/* 2530:     */ 
/* 2531:2573 */         f.setLineno(lineno);
/* 2532:2574 */         f.setLp(this.ts.tokenBeg - pos);
/* 2533:2575 */         List<AstNode> args = argumentList();
/* 2534:2576 */         if ((args != null) && (args.size() > 65536)) {
/* 2535:2577 */           reportError("msg.too.many.function.args");
/* 2536:     */         }
/* 2537:2578 */         f.setArguments(args);
/* 2538:2579 */         f.setRp(this.ts.tokenBeg - pos);
/* 2539:2580 */         f.setLength(this.ts.tokenEnd - pos);
/* 2540:2581 */         pn = f;
/* 2541:2582 */         break;
/* 2542:     */       default: 
/* 2543:     */         return pn;
/* 2544:     */       }
/* 2545:     */     }
/* 2546:2588 */     return pn;
/* 2547:     */   }
/* 2548:     */   
/* 2549:     */   private AstNode propertyAccess(int tt, AstNode pn)
/* 2550:     */     throws IOException
/* 2551:     */   {
/* 2552:2599 */     if (pn == null) {
/* 2553:2599 */       codeBug();
/* 2554:     */     }
/* 2555:2600 */     int memberTypeFlags = 0;int lineno = this.ts.lineno;int dotPos = this.ts.tokenBeg;
/* 2556:2601 */     consumeToken();
/* 2557:2603 */     if (tt == 143)
/* 2558:     */     {
/* 2559:2604 */       mustHaveXML();
/* 2560:2605 */       memberTypeFlags = 4;
/* 2561:     */     }
/* 2562:2608 */     if (!this.compilerEnv.isXmlAvailable())
/* 2563:     */     {
/* 2564:2609 */       mustMatchToken(39, "msg.no.name.after.dot");
/* 2565:2610 */       Name name = createNameNode(true, 33);
/* 2566:2611 */       PropertyGet pg = new PropertyGet(pn, name, dotPos);
/* 2567:2612 */       pg.setLineno(lineno);
/* 2568:2613 */       return pg;
/* 2569:     */     }
/* 2570:2616 */     AstNode ref = null;
/* 2571:     */     
/* 2572:2618 */     int token = nextToken();
/* 2573:2619 */     switch (token)
/* 2574:     */     {
/* 2575:     */     case 50: 
/* 2576:2622 */       saveNameTokenData(this.ts.tokenBeg, "throw", this.ts.lineno);
/* 2577:2623 */       ref = propertyName(-1, "throw", memberTypeFlags);
/* 2578:2624 */       break;
/* 2579:     */     case 39: 
/* 2580:2628 */       ref = propertyName(-1, this.ts.getString(), memberTypeFlags);
/* 2581:2629 */       break;
/* 2582:     */     case 23: 
/* 2583:2633 */       saveNameTokenData(this.ts.tokenBeg, "*", this.ts.lineno);
/* 2584:2634 */       ref = propertyName(-1, "*", memberTypeFlags);
/* 2585:2635 */       break;
/* 2586:     */     case 147: 
/* 2587:2640 */       ref = attributeAccess();
/* 2588:2641 */       break;
/* 2589:     */     default: 
/* 2590:2644 */       if (this.compilerEnv.isReservedKeywordAsIdentifier())
/* 2591:     */       {
/* 2592:2646 */         String name = Token.keywordToName(token);
/* 2593:2647 */         if (name != null)
/* 2594:     */         {
/* 2595:2648 */           saveNameTokenData(this.ts.tokenBeg, name, this.ts.lineno);
/* 2596:2649 */           ref = propertyName(-1, name, memberTypeFlags);
/* 2597:2650 */           break;
/* 2598:     */         }
/* 2599:     */       }
/* 2600:2653 */       reportError("msg.no.name.after.dot");
/* 2601:2654 */       return makeErrorNode();
/* 2602:     */     }
/* 2603:2657 */     boolean xml = ref instanceof XmlRef;
/* 2604:2658 */     InfixExpression result = xml ? new XmlMemberGet() : new PropertyGet();
/* 2605:2659 */     if ((xml) && (tt == 108)) {
/* 2606:2660 */       result.setType(108);
/* 2607:     */     }
/* 2608:2661 */     int pos = pn.getPosition();
/* 2609:2662 */     result.setPosition(pos);
/* 2610:2663 */     result.setLength(getNodeEnd(ref) - pos);
/* 2611:2664 */     result.setOperatorPosition(dotPos - pos);
/* 2612:2665 */     result.setLineno(lineno);
/* 2613:2666 */     result.setLeft(pn);
/* 2614:2667 */     result.setRight(ref);
/* 2615:2668 */     return result;
/* 2616:     */   }
/* 2617:     */   
/* 2618:     */   private AstNode attributeAccess()
/* 2619:     */     throws IOException
/* 2620:     */   {
/* 2621:2681 */     int tt = nextToken();int atPos = this.ts.tokenBeg;
/* 2622:2683 */     switch (tt)
/* 2623:     */     {
/* 2624:     */     case 39: 
/* 2625:2686 */       return propertyName(atPos, this.ts.getString(), 0);
/* 2626:     */     case 23: 
/* 2627:2690 */       saveNameTokenData(this.ts.tokenBeg, "*", this.ts.lineno);
/* 2628:2691 */       return propertyName(atPos, "*", 0);
/* 2629:     */     case 83: 
/* 2630:2695 */       return xmlElemRef(atPos, null, -1);
/* 2631:     */     }
/* 2632:2698 */     reportError("msg.no.name.after.xmlAttr");
/* 2633:2699 */     return makeErrorNode();
/* 2634:     */   }
/* 2635:     */   
/* 2636:     */   private AstNode propertyName(int atPos, String s, int memberTypeFlags)
/* 2637:     */     throws IOException
/* 2638:     */   {
/* 2639:2721 */     int pos = atPos != -1 ? atPos : this.ts.tokenBeg;int lineno = this.ts.lineno;
/* 2640:2722 */     int colonPos = -1;
/* 2641:2723 */     Name name = createNameNode(true, this.currentToken);
/* 2642:2724 */     Name ns = null;
/* 2643:2726 */     if (matchToken(144))
/* 2644:     */     {
/* 2645:2727 */       ns = name;
/* 2646:2728 */       colonPos = this.ts.tokenBeg;
/* 2647:2730 */       switch (nextToken())
/* 2648:     */       {
/* 2649:     */       case 39: 
/* 2650:2733 */         name = createNameNode();
/* 2651:2734 */         break;
/* 2652:     */       case 23: 
/* 2653:2738 */         saveNameTokenData(this.ts.tokenBeg, "*", this.ts.lineno);
/* 2654:2739 */         name = createNameNode(false, -1);
/* 2655:2740 */         break;
/* 2656:     */       case 83: 
/* 2657:2744 */         return xmlElemRef(atPos, ns, colonPos);
/* 2658:     */       default: 
/* 2659:2747 */         reportError("msg.no.name.after.coloncolon");
/* 2660:2748 */         return makeErrorNode();
/* 2661:     */       }
/* 2662:     */     }
/* 2663:2752 */     if ((ns == null) && (memberTypeFlags == 0) && (atPos == -1)) {
/* 2664:2753 */       return name;
/* 2665:     */     }
/* 2666:2756 */     XmlPropRef ref = new XmlPropRef(pos, getNodeEnd(name) - pos);
/* 2667:2757 */     ref.setAtPos(atPos);
/* 2668:2758 */     ref.setNamespace(ns);
/* 2669:2759 */     ref.setColonPos(colonPos);
/* 2670:2760 */     ref.setPropName(name);
/* 2671:2761 */     ref.setLineno(lineno);
/* 2672:2762 */     return ref;
/* 2673:     */   }
/* 2674:     */   
/* 2675:     */   private XmlElemRef xmlElemRef(int atPos, Name namespace, int colonPos)
/* 2676:     */     throws IOException
/* 2677:     */   {
/* 2678:2772 */     int lb = this.ts.tokenBeg;int rb = -1;int pos = atPos != -1 ? atPos : lb;
/* 2679:2773 */     AstNode expr = expr();
/* 2680:2774 */     int end = getNodeEnd(expr);
/* 2681:2775 */     if (mustMatchToken(84, "msg.no.bracket.index"))
/* 2682:     */     {
/* 2683:2776 */       rb = this.ts.tokenBeg;
/* 2684:2777 */       end = this.ts.tokenEnd;
/* 2685:     */     }
/* 2686:2779 */     XmlElemRef ref = new XmlElemRef(pos, end - pos);
/* 2687:2780 */     ref.setNamespace(namespace);
/* 2688:2781 */     ref.setColonPos(colonPos);
/* 2689:2782 */     ref.setAtPos(atPos);
/* 2690:2783 */     ref.setExpression(expr);
/* 2691:2784 */     ref.setBrackets(lb, rb);
/* 2692:2785 */     return ref;
/* 2693:     */   }
/* 2694:     */   
/* 2695:     */   private AstNode destructuringPrimaryExpr()
/* 2696:     */     throws IOException, Parser.ParserException
/* 2697:     */   {
/* 2698:     */     try
/* 2699:     */     {
/* 2700:2792 */       this.inDestructuringAssignment = true;
/* 2701:2793 */       return primaryExpr();
/* 2702:     */     }
/* 2703:     */     finally
/* 2704:     */     {
/* 2705:2795 */       this.inDestructuringAssignment = false;
/* 2706:     */     }
/* 2707:     */   }
/* 2708:     */   
/* 2709:     */   private AstNode primaryExpr()
/* 2710:     */     throws IOException
/* 2711:     */   {
/* 2712:2802 */     int ttFlagged = nextFlaggedToken();
/* 2713:2803 */     int tt = ttFlagged & 0xFFFF;
/* 2714:     */     int pos;
/* 2715:     */     int end;
/* 2716:2805 */     switch (tt)
/* 2717:     */     {
/* 2718:     */     case 109: 
/* 2719:2807 */       return function(2);
/* 2720:     */     case 83: 
/* 2721:2810 */       return arrayLiteral();
/* 2722:     */     case 85: 
/* 2723:2813 */       return objectLiteral();
/* 2724:     */     case 153: 
/* 2725:2816 */       return let(false, this.ts.tokenBeg);
/* 2726:     */     case 87: 
/* 2727:2819 */       return parenExpr();
/* 2728:     */     case 147: 
/* 2729:2822 */       mustHaveXML();
/* 2730:2823 */       return attributeAccess();
/* 2731:     */     case 39: 
/* 2732:2826 */       return name(ttFlagged, tt);
/* 2733:     */     case 40: 
/* 2734:2829 */       String s = this.ts.getString();
/* 2735:2830 */       if ((this.inUseStrictDirective) && (this.ts.isNumberOctal())) {
/* 2736:2831 */         reportError("msg.no.octal.strict");
/* 2737:     */       }
/* 2738:2833 */       return new NumberLiteral(this.ts.tokenBeg, s, this.ts.getNumber());
/* 2739:     */     case 41: 
/* 2740:2839 */       return createStringLiteral();
/* 2741:     */     case 24: 
/* 2742:     */     case 100: 
/* 2743:2844 */       this.ts.readRegExp(tt);
/* 2744:2845 */       pos = this.ts.tokenBeg;end = this.ts.tokenEnd;
/* 2745:2846 */       RegExpLiteral re = new RegExpLiteral(pos, end - pos);
/* 2746:2847 */       re.setValue(this.ts.getString());
/* 2747:2848 */       re.setFlags(this.ts.readAndClearRegExpFlags());
/* 2748:2849 */       return re;
/* 2749:     */     case 42: 
/* 2750:     */     case 43: 
/* 2751:     */     case 44: 
/* 2752:     */     case 45: 
/* 2753:2855 */       pos = this.ts.tokenBeg;end = this.ts.tokenEnd;
/* 2754:2856 */       return new KeywordLiteral(pos, end - pos, tt);
/* 2755:     */     case 127: 
/* 2756:2859 */       reportError("msg.reserved.id");
/* 2757:2860 */       break;
/* 2758:     */     case -1: 
/* 2759:     */       break;
/* 2760:     */     case 0: 
/* 2761:2867 */       reportError("msg.unexpected.eof");
/* 2762:2868 */       break;
/* 2763:     */     default: 
/* 2764:2871 */       reportError("msg.syntax");
/* 2765:     */     }
/* 2766:2875 */     return makeErrorNode();
/* 2767:     */   }
/* 2768:     */   
/* 2769:     */   private AstNode parenExpr()
/* 2770:     */     throws IOException
/* 2771:     */   {
/* 2772:2879 */     boolean wasInForInit = this.inForInit;
/* 2773:2880 */     this.inForInit = false;
/* 2774:     */     try
/* 2775:     */     {
/* 2776:2882 */       String jsdoc = getAndResetJsDoc();
/* 2777:2883 */       int lineno = this.ts.lineno;
/* 2778:2884 */       AstNode e = expr();
/* 2779:2885 */       ParenthesizedExpression pn = new ParenthesizedExpression(e);
/* 2780:2886 */       if (jsdoc == null) {
/* 2781:2887 */         jsdoc = getAndResetJsDoc();
/* 2782:     */       }
/* 2783:2889 */       if (jsdoc != null) {
/* 2784:2890 */         pn.setJsDoc(jsdoc);
/* 2785:     */       }
/* 2786:2892 */       mustMatchToken(88, "msg.no.paren");
/* 2787:2893 */       pn.setLength(this.ts.tokenEnd - pn.getPosition());
/* 2788:2894 */       pn.setLineno(lineno);
/* 2789:2895 */       return pn;
/* 2790:     */     }
/* 2791:     */     finally
/* 2792:     */     {
/* 2793:2897 */       this.inForInit = wasInForInit;
/* 2794:     */     }
/* 2795:     */   }
/* 2796:     */   
/* 2797:     */   private AstNode name(int ttFlagged, int tt)
/* 2798:     */     throws IOException
/* 2799:     */   {
/* 2800:2902 */     String nameString = this.ts.getString();
/* 2801:2903 */     int namePos = this.ts.tokenBeg;int nameLineno = this.ts.lineno;
/* 2802:2904 */     if ((0 != (ttFlagged & 0x20000)) && (peekToken() == 103))
/* 2803:     */     {
/* 2804:2907 */       Label label = new Label(namePos, this.ts.tokenEnd - namePos);
/* 2805:2908 */       label.setName(nameString);
/* 2806:2909 */       label.setLineno(this.ts.lineno);
/* 2807:2910 */       return label;
/* 2808:     */     }
/* 2809:2915 */     saveNameTokenData(namePos, nameString, nameLineno);
/* 2810:2917 */     if (this.compilerEnv.isXmlAvailable()) {
/* 2811:2918 */       return propertyName(-1, nameString, 0);
/* 2812:     */     }
/* 2813:2920 */     return createNameNode(true, 39);
/* 2814:     */   }
/* 2815:     */   
/* 2816:     */   private AstNode arrayLiteral()
/* 2817:     */     throws IOException
/* 2818:     */   {
/* 2819:2930 */     if (this.currentToken != 83) {
/* 2820:2930 */       codeBug();
/* 2821:     */     }
/* 2822:2931 */     int pos = this.ts.tokenBeg;int end = this.ts.tokenEnd;
/* 2823:2932 */     List<AstNode> elements = new ArrayList();
/* 2824:2933 */     ArrayLiteral pn = new ArrayLiteral(pos);
/* 2825:2934 */     boolean after_lb_or_comma = true;
/* 2826:2935 */     int afterComma = -1;
/* 2827:2936 */     int skipCount = 0;
/* 2828:     */     for (;;)
/* 2829:     */     {
/* 2830:2938 */       int tt = peekToken();
/* 2831:2939 */       if (tt == 89)
/* 2832:     */       {
/* 2833:2940 */         consumeToken();
/* 2834:2941 */         afterComma = this.ts.tokenEnd;
/* 2835:2942 */         if (!after_lb_or_comma)
/* 2836:     */         {
/* 2837:2943 */           after_lb_or_comma = true;
/* 2838:     */         }
/* 2839:     */         else
/* 2840:     */         {
/* 2841:2945 */           elements.add(new EmptyExpression(this.ts.tokenBeg, 1));
/* 2842:2946 */           skipCount++;
/* 2843:     */         }
/* 2844:     */       }
/* 2845:     */       else
/* 2846:     */       {
/* 2847:2948 */         if (tt == 84)
/* 2848:     */         {
/* 2849:2949 */           consumeToken();
/* 2850:     */           
/* 2851:     */ 
/* 2852:     */ 
/* 2853:     */ 
/* 2854:     */ 
/* 2855:2955 */           end = this.ts.tokenEnd;
/* 2856:2956 */           pn.setDestructuringLength(elements.size() + (after_lb_or_comma ? 1 : 0));
/* 2857:     */           
/* 2858:2958 */           pn.setSkipCount(skipCount);
/* 2859:2959 */           if (afterComma == -1) {
/* 2860:     */             break;
/* 2861:     */           }
/* 2862:2960 */           warnTrailingComma("msg.array.trailing.comma", pos, elements, afterComma); break;
/* 2863:     */         }
/* 2864:2963 */         if ((tt == 119) && (!after_lb_or_comma) && (elements.size() == 1)) {
/* 2865:2965 */           return arrayComprehension((AstNode)elements.get(0), pos);
/* 2866:     */         }
/* 2867:2966 */         if (tt == 0)
/* 2868:     */         {
/* 2869:2967 */           reportError("msg.no.bracket.arg");
/* 2870:     */         }
/* 2871:     */         else
/* 2872:     */         {
/* 2873:2969 */           if (!after_lb_or_comma) {
/* 2874:2970 */             reportError("msg.no.bracket.arg");
/* 2875:     */           }
/* 2876:2972 */           elements.add(assignExpr());
/* 2877:2973 */           after_lb_or_comma = false;
/* 2878:2974 */           afterComma = -1;
/* 2879:     */         }
/* 2880:     */       }
/* 2881:     */     }
/* 2882:2977 */     for (AstNode e : elements) {
/* 2883:2978 */       pn.addElement(e);
/* 2884:     */     }
/* 2885:2980 */     pn.setLength(end - pos);
/* 2886:2981 */     return pn;
/* 2887:     */   }
/* 2888:     */   
/* 2889:     */   private AstNode arrayComprehension(AstNode result, int pos)
/* 2890:     */     throws IOException
/* 2891:     */   {
/* 2892:2993 */     List<ArrayComprehensionLoop> loops = new ArrayList();
/* 2893:2995 */     while (peekToken() == 119) {
/* 2894:2996 */       loops.add(arrayComprehensionLoop());
/* 2895:     */     }
/* 2896:2998 */     int ifPos = -1;
/* 2897:2999 */     ConditionData data = null;
/* 2898:3000 */     if (peekToken() == 112)
/* 2899:     */     {
/* 2900:3001 */       consumeToken();
/* 2901:3002 */       ifPos = this.ts.tokenBeg - pos;
/* 2902:3003 */       data = condition();
/* 2903:     */     }
/* 2904:3005 */     mustMatchToken(84, "msg.no.bracket.arg");
/* 2905:3006 */     ArrayComprehension pn = new ArrayComprehension(pos, this.ts.tokenEnd - pos);
/* 2906:3007 */     pn.setResult(result);
/* 2907:3008 */     pn.setLoops(loops);
/* 2908:3009 */     if (data != null)
/* 2909:     */     {
/* 2910:3010 */       pn.setIfPosition(ifPos);
/* 2911:3011 */       pn.setFilter(data.condition);
/* 2912:3012 */       pn.setFilterLp(data.lp - pos);
/* 2913:3013 */       pn.setFilterRp(data.rp - pos);
/* 2914:     */     }
/* 2915:3015 */     return pn;
/* 2916:     */   }
/* 2917:     */   
/* 2918:     */   private ArrayComprehensionLoop arrayComprehensionLoop()
/* 2919:     */     throws IOException
/* 2920:     */   {
/* 2921:3021 */     if (nextToken() != 119) {
/* 2922:3021 */       codeBug();
/* 2923:     */     }
/* 2924:3022 */     int pos = this.ts.tokenBeg;
/* 2925:3023 */     int eachPos = -1;int lp = -1;int rp = -1;int inPos = -1;
/* 2926:3024 */     ArrayComprehensionLoop pn = new ArrayComprehensionLoop(pos);
/* 2927:     */     
/* 2928:3026 */     pushScope(pn);
/* 2929:     */     try
/* 2930:     */     {
/* 2931:3028 */       if (matchToken(39)) {
/* 2932:3029 */         if (this.ts.getString().equals("each")) {
/* 2933:3030 */           eachPos = this.ts.tokenBeg - pos;
/* 2934:     */         } else {
/* 2935:3032 */           reportError("msg.no.paren.for");
/* 2936:     */         }
/* 2937:     */       }
/* 2938:3035 */       if (mustMatchToken(87, "msg.no.paren.for")) {
/* 2939:3036 */         lp = this.ts.tokenBeg - pos;
/* 2940:     */       }
/* 2941:3039 */       AstNode iter = null;
/* 2942:3040 */       switch (peekToken())
/* 2943:     */       {
/* 2944:     */       case 83: 
/* 2945:     */       case 85: 
/* 2946:3044 */         iter = destructuringPrimaryExpr();
/* 2947:3045 */         markDestructuring(iter);
/* 2948:3046 */         break;
/* 2949:     */       case 39: 
/* 2950:3048 */         consumeToken();
/* 2951:3049 */         iter = createNameNode();
/* 2952:3050 */         break;
/* 2953:     */       default: 
/* 2954:3052 */         reportError("msg.bad.var");
/* 2955:     */       }
/* 2956:3057 */       if (iter.getType() == 39) {
/* 2957:3058 */         defineSymbol(153, this.ts.getString(), true);
/* 2958:     */       }
/* 2959:3061 */       if (mustMatchToken(52, "msg.in.after.for.name")) {
/* 2960:3062 */         inPos = this.ts.tokenBeg - pos;
/* 2961:     */       }
/* 2962:3063 */       AstNode obj = expr();
/* 2963:3064 */       if (mustMatchToken(88, "msg.no.paren.for.ctrl")) {
/* 2964:3065 */         rp = this.ts.tokenBeg - pos;
/* 2965:     */       }
/* 2966:3067 */       pn.setLength(this.ts.tokenEnd - pos);
/* 2967:3068 */       pn.setIterator(iter);
/* 2968:3069 */       pn.setIteratedObject(obj);
/* 2969:3070 */       pn.setInPosition(inPos);
/* 2970:3071 */       pn.setEachPosition(eachPos);
/* 2971:3072 */       pn.setIsForEach(eachPos != -1);
/* 2972:3073 */       pn.setParens(lp, rp);
/* 2973:3074 */       return pn;
/* 2974:     */     }
/* 2975:     */     finally
/* 2976:     */     {
/* 2977:3076 */       popScope();
/* 2978:     */     }
/* 2979:     */   }
/* 2980:     */   
/* 2981:     */   private ObjectLiteral objectLiteral()
/* 2982:     */     throws IOException
/* 2983:     */   {
/* 2984:3083 */     int pos = this.ts.tokenBeg;int lineno = this.ts.lineno;
/* 2985:3084 */     int afterComma = -1;
/* 2986:3085 */     List<ObjectProperty> elems = new ArrayList();
/* 2987:3086 */     Set<String> propertyNames = new HashSet();
/* 2988:     */     for (;;)
/* 2989:     */     {
/* 2990:3090 */       String propertyName = null;
/* 2991:3091 */       int tt = peekToken();
/* 2992:3092 */       String jsdoc = getAndResetJsDoc();
/* 2993:3093 */       switch (tt)
/* 2994:     */       {
/* 2995:     */       case 39: 
/* 2996:     */       case 41: 
/* 2997:3096 */         afterComma = -1;
/* 2998:3097 */         saveNameTokenData(this.ts.tokenBeg, this.ts.getString(), this.ts.lineno);
/* 2999:3098 */         consumeToken();
/* 3000:3099 */         StringLiteral stringProp = null;
/* 3001:3100 */         if (tt == 41) {
/* 3002:3101 */           stringProp = createStringLiteral();
/* 3003:     */         }
/* 3004:3103 */         Name name = createNameNode();
/* 3005:3104 */         propertyName = this.ts.getString();
/* 3006:3105 */         int ppos = this.ts.tokenBeg;
/* 3007:3107 */         if ((tt == 39) && (peekToken() == 39) && (("get".equals(propertyName)) || ("set".equals(propertyName))))
/* 3008:     */         {
/* 3009:3111 */           consumeToken();
/* 3010:3112 */           name = createNameNode();
/* 3011:3113 */           name.setJsDoc(jsdoc);
/* 3012:3114 */           ObjectProperty objectProp = getterSetterProperty(ppos, name, "get".equals(propertyName));
/* 3013:     */           
/* 3014:3116 */           elems.add(objectProp);
/* 3015:3117 */           propertyName = objectProp.getLeft().getString();
/* 3016:     */         }
/* 3017:     */         else
/* 3018:     */         {
/* 3019:3119 */           AstNode pname = stringProp != null ? stringProp : name;
/* 3020:3120 */           pname.setJsDoc(jsdoc);
/* 3021:3121 */           elems.add(plainProperty(pname, tt));
/* 3022:     */         }
/* 3023:3123 */         break;
/* 3024:     */       case 40: 
/* 3025:3126 */         consumeToken();
/* 3026:3127 */         afterComma = -1;
/* 3027:3128 */         AstNode nl = new NumberLiteral(this.ts.tokenBeg, this.ts.getString(), this.ts.getNumber());
/* 3028:     */         
/* 3029:     */ 
/* 3030:3131 */         nl.setJsDoc(jsdoc);
/* 3031:3132 */         propertyName = this.ts.getString();
/* 3032:3133 */         elems.add(plainProperty(nl, tt));
/* 3033:3134 */         break;
/* 3034:     */       case 86: 
/* 3035:3137 */         if ((afterComma == -1) || (!this.compilerEnv.getWarnTrailingComma())) {
/* 3036:     */           break label556;
/* 3037:     */         }
/* 3038:3138 */         warnTrailingComma("msg.extra.trailing.comma", pos, elems, afterComma); break;
/* 3039:     */       default: 
/* 3040:3143 */         if (this.compilerEnv.isReservedKeywordAsIdentifier())
/* 3041:     */         {
/* 3042:3145 */           propertyName = Token.keywordToName(tt);
/* 3043:3146 */           if (propertyName != null)
/* 3044:     */           {
/* 3045:3147 */             afterComma = -1;
/* 3046:3148 */             saveNameTokenData(this.ts.tokenBeg, propertyName, this.ts.lineno);
/* 3047:3149 */             consumeToken();
/* 3048:3150 */             AstNode pname = createNameNode();
/* 3049:3151 */             pname.setJsDoc(jsdoc);
/* 3050:3152 */             elems.add(plainProperty(pname, tt));
/* 3051:3153 */             break;
/* 3052:     */           }
/* 3053:     */         }
/* 3054:3156 */         reportError("msg.bad.prop");
/* 3055:     */       }
/* 3056:3160 */       if (this.inUseStrictDirective)
/* 3057:     */       {
/* 3058:3161 */         if (propertyNames.contains(propertyName)) {
/* 3059:3162 */           addError("msg.dup.obj.lit.prop.strict", propertyName);
/* 3060:     */         }
/* 3061:3164 */         propertyNames.add(propertyName);
/* 3062:     */       }
/* 3063:3168 */       getAndResetJsDoc();
/* 3064:3169 */       jsdoc = null;
/* 3065:3171 */       if (!matchToken(89)) {
/* 3066:     */         break;
/* 3067:     */       }
/* 3068:3172 */       afterComma = this.ts.tokenEnd;
/* 3069:     */     }
/* 3070:     */     label556:
/* 3071:3178 */     mustMatchToken(86, "msg.no.brace.prop");
/* 3072:3179 */     ObjectLiteral pn = new ObjectLiteral(pos, this.ts.tokenEnd - pos);
/* 3073:3180 */     pn.setElements(elems);
/* 3074:3181 */     pn.setLineno(lineno);
/* 3075:3182 */     return pn;
/* 3076:     */   }
/* 3077:     */   
/* 3078:     */   private ObjectProperty plainProperty(AstNode property, int ptt)
/* 3079:     */     throws IOException
/* 3080:     */   {
/* 3081:3190 */     int tt = peekToken();
/* 3082:3191 */     if (((tt == 89) || (tt == 86)) && (ptt == 39) && (this.compilerEnv.getLanguageVersion() >= 180))
/* 3083:     */     {
/* 3084:3193 */       if (!this.inDestructuringAssignment) {
/* 3085:3194 */         reportError("msg.bad.object.init");
/* 3086:     */       }
/* 3087:3196 */       AstNode nn = new Name(property.getPosition(), property.getString());
/* 3088:3197 */       ObjectProperty pn = new ObjectProperty();
/* 3089:3198 */       pn.putProp(26, Boolean.TRUE);
/* 3090:3199 */       pn.setLeftAndRight(property, nn);
/* 3091:3200 */       return pn;
/* 3092:     */     }
/* 3093:3202 */     mustMatchToken(103, "msg.no.colon.prop");
/* 3094:3203 */     ObjectProperty pn = new ObjectProperty();
/* 3095:3204 */     pn.setOperatorPosition(this.ts.tokenBeg);
/* 3096:3205 */     pn.setLeftAndRight(property, assignExpr());
/* 3097:3206 */     return pn;
/* 3098:     */   }
/* 3099:     */   
/* 3100:     */   private ObjectProperty getterSetterProperty(int pos, AstNode propName, boolean isGetter)
/* 3101:     */     throws IOException
/* 3102:     */   {
/* 3103:3213 */     FunctionNode fn = function(2);
/* 3104:     */     
/* 3105:3215 */     Name name = fn.getFunctionName();
/* 3106:3216 */     if ((name != null) && (name.length() != 0)) {
/* 3107:3217 */       reportError("msg.bad.prop");
/* 3108:     */     }
/* 3109:3219 */     ObjectProperty pn = new ObjectProperty(pos);
/* 3110:3220 */     if (isGetter) {
/* 3111:3221 */       pn.setIsGetter();
/* 3112:     */     } else {
/* 3113:3223 */       pn.setIsSetter();
/* 3114:     */     }
/* 3115:3225 */     int end = getNodeEnd(fn);
/* 3116:3226 */     pn.setLeft(propName);
/* 3117:3227 */     pn.setRight(fn);
/* 3118:3228 */     pn.setLength(end - pos);
/* 3119:3229 */     return pn;
/* 3120:     */   }
/* 3121:     */   
/* 3122:     */   private Name createNameNode()
/* 3123:     */   {
/* 3124:3233 */     return createNameNode(false, 39);
/* 3125:     */   }
/* 3126:     */   
/* 3127:     */   private Name createNameNode(boolean checkActivation, int token)
/* 3128:     */   {
/* 3129:3244 */     int beg = this.ts.tokenBeg;
/* 3130:3245 */     String s = this.ts.getString();
/* 3131:3246 */     int lineno = this.ts.lineno;
/* 3132:3247 */     if (!"".equals(this.prevNameTokenString))
/* 3133:     */     {
/* 3134:3248 */       beg = this.prevNameTokenStart;
/* 3135:3249 */       s = this.prevNameTokenString;
/* 3136:3250 */       lineno = this.prevNameTokenLineno;
/* 3137:3251 */       this.prevNameTokenStart = 0;
/* 3138:3252 */       this.prevNameTokenString = "";
/* 3139:3253 */       this.prevNameTokenLineno = 0;
/* 3140:     */     }
/* 3141:3255 */     if (s == null) {
/* 3142:3256 */       if (this.compilerEnv.isIdeMode()) {
/* 3143:3257 */         s = "";
/* 3144:     */       } else {
/* 3145:3259 */         codeBug();
/* 3146:     */       }
/* 3147:     */     }
/* 3148:3262 */     Name name = new Name(beg, s);
/* 3149:3263 */     name.setLineno(lineno);
/* 3150:3264 */     if (checkActivation) {
/* 3151:3265 */       checkActivationName(s, token);
/* 3152:     */     }
/* 3153:3267 */     return name;
/* 3154:     */   }
/* 3155:     */   
/* 3156:     */   private StringLiteral createStringLiteral()
/* 3157:     */   {
/* 3158:3271 */     int pos = this.ts.tokenBeg;int end = this.ts.tokenEnd;
/* 3159:3272 */     StringLiteral s = new StringLiteral(pos, end - pos);
/* 3160:3273 */     s.setLineno(this.ts.lineno);
/* 3161:3274 */     s.setValue(this.ts.getString());
/* 3162:3275 */     s.setQuoteCharacter(this.ts.getQuoteChar());
/* 3163:3276 */     return s;
/* 3164:     */   }
/* 3165:     */   
/* 3166:     */   protected void checkActivationName(String name, int token)
/* 3167:     */   {
/* 3168:3280 */     if (!insideFunction()) {
/* 3169:3281 */       return;
/* 3170:     */     }
/* 3171:3283 */     boolean activation = false;
/* 3172:3284 */     if (("arguments".equals(name)) || ((this.compilerEnv.getActivationNames() != null) && (this.compilerEnv.getActivationNames().contains(name)))) {
/* 3173:3288 */       activation = true;
/* 3174:3289 */     } else if (("length".equals(name)) && 
/* 3175:3290 */       (token == 33) && (this.compilerEnv.getLanguageVersion() == 120)) {
/* 3176:3294 */       activation = true;
/* 3177:     */     }
/* 3178:3297 */     if (activation) {
/* 3179:3298 */       setRequiresActivation();
/* 3180:     */     }
/* 3181:     */   }
/* 3182:     */   
/* 3183:     */   protected void setRequiresActivation()
/* 3184:     */   {
/* 3185:3303 */     if (insideFunction()) {
/* 3186:3304 */       ((FunctionNode)this.currentScriptOrFn).setRequiresActivation();
/* 3187:     */     }
/* 3188:     */   }
/* 3189:     */   
/* 3190:     */   private void checkCallRequiresActivation(AstNode pn)
/* 3191:     */   {
/* 3192:3309 */     if (((pn.getType() == 39) && ("eval".equals(((Name)pn).getIdentifier()))) || ((pn.getType() == 33) && ("eval".equals(((PropertyGet)pn).getProperty().getIdentifier())))) {
/* 3193:3313 */       setRequiresActivation();
/* 3194:     */     }
/* 3195:     */   }
/* 3196:     */   
/* 3197:     */   protected void setIsGenerator()
/* 3198:     */   {
/* 3199:3317 */     if (insideFunction()) {
/* 3200:3318 */       ((FunctionNode)this.currentScriptOrFn).setIsGenerator();
/* 3201:     */     }
/* 3202:     */   }
/* 3203:     */   
/* 3204:     */   private void checkBadIncDec(UnaryExpression expr)
/* 3205:     */   {
/* 3206:3323 */     AstNode op = removeParens(expr.getOperand());
/* 3207:3324 */     int tt = op.getType();
/* 3208:3325 */     if ((tt != 39) && (tt != 33) && (tt != 36) && (tt != 67) && (tt != 38)) {
/* 3209:3330 */       reportError(expr.getType() == 106 ? "msg.bad.incr" : "msg.bad.decr");
/* 3210:     */     }
/* 3211:     */   }
/* 3212:     */   
/* 3213:     */   private ErrorNode makeErrorNode()
/* 3214:     */   {
/* 3215:3336 */     ErrorNode pn = new ErrorNode(this.ts.tokenBeg, this.ts.tokenEnd - this.ts.tokenBeg);
/* 3216:3337 */     pn.setLineno(this.ts.lineno);
/* 3217:3338 */     return pn;
/* 3218:     */   }
/* 3219:     */   
/* 3220:     */   private int nodeEnd(AstNode node)
/* 3221:     */   {
/* 3222:3343 */     return node.getPosition() + node.getLength();
/* 3223:     */   }
/* 3224:     */   
/* 3225:     */   private void saveNameTokenData(int pos, String name, int lineno)
/* 3226:     */   {
/* 3227:3347 */     this.prevNameTokenStart = pos;
/* 3228:3348 */     this.prevNameTokenString = name;
/* 3229:3349 */     this.prevNameTokenLineno = lineno;
/* 3230:     */   }
/* 3231:     */   
/* 3232:     */   private int lineBeginningFor(int pos)
/* 3233:     */   {
/* 3234:3366 */     if (this.sourceChars == null) {
/* 3235:3367 */       return -1;
/* 3236:     */     }
/* 3237:3369 */     if (pos <= 0) {
/* 3238:3370 */       return 0;
/* 3239:     */     }
/* 3240:3372 */     char[] buf = this.sourceChars;
/* 3241:3373 */     if (pos >= buf.length) {
/* 3242:3374 */       pos = buf.length - 1;
/* 3243:     */     }
/* 3244:     */     for (;;)
/* 3245:     */     {
/* 3246:3376 */       pos--;
/* 3247:3376 */       if (pos < 0) {
/* 3248:     */         break;
/* 3249:     */       }
/* 3250:3377 */       char c = buf[pos];
/* 3251:3378 */       if ((c == '\n') || (c == '\r')) {
/* 3252:3379 */         return pos + 1;
/* 3253:     */       }
/* 3254:     */     }
/* 3255:3382 */     return 0;
/* 3256:     */   }
/* 3257:     */   
/* 3258:     */   private void warnMissingSemi(int pos, int end)
/* 3259:     */   {
/* 3260:3389 */     if (this.compilerEnv.isStrictMode())
/* 3261:     */     {
/* 3262:3390 */       int beg = Math.max(pos, lineBeginningFor(end));
/* 3263:3391 */       if (end == -1) {
/* 3264:3392 */         end = this.ts.cursor;
/* 3265:     */       }
/* 3266:3393 */       addStrictWarning("msg.missing.semi", "", beg, end - beg);
/* 3267:     */     }
/* 3268:     */   }
/* 3269:     */   
/* 3270:     */   private void warnTrailingComma(String messageId, int pos, List<?> elems, int commaPos)
/* 3271:     */   {
/* 3272:3400 */     if (this.compilerEnv.getWarnTrailingComma())
/* 3273:     */     {
/* 3274:3402 */       if (!elems.isEmpty()) {
/* 3275:3403 */         pos = ((AstNode)elems.get(0)).getPosition();
/* 3276:     */       }
/* 3277:3405 */       pos = Math.max(pos, lineBeginningFor(commaPos));
/* 3278:3406 */       addWarning("msg.extra.trailing.comma", pos, commaPos - pos);
/* 3279:     */     }
/* 3280:     */   }
/* 3281:     */   
/* 3282:     */   private String readFully(Reader reader)
/* 3283:     */     throws IOException
/* 3284:     */   {
/* 3285:3412 */     BufferedReader in = new BufferedReader(reader);
/* 3286:     */     try
/* 3287:     */     {
/* 3288:3414 */       char[] cbuf = new char[1024];
/* 3289:3415 */       StringBuilder sb = new StringBuilder(1024);
/* 3290:     */       int bytes_read;
/* 3291:3417 */       while ((bytes_read = in.read(cbuf, 0, 1024)) != -1) {
/* 3292:3418 */         sb.append(cbuf, 0, bytes_read);
/* 3293:     */       }
/* 3294:3420 */       return sb.toString();
/* 3295:     */     }
/* 3296:     */     finally
/* 3297:     */     {
/* 3298:3422 */       in.close();
/* 3299:     */     }
/* 3300:     */   }
/* 3301:     */   
/* 3302:     */   protected class PerFunctionVariables
/* 3303:     */   {
/* 3304:     */     private ScriptNode savedCurrentScriptOrFn;
/* 3305:     */     private Scope savedCurrentScope;
/* 3306:     */     private int savedNestingOfWith;
/* 3307:     */     private int savedEndFlags;
/* 3308:     */     private boolean savedInForInit;
/* 3309:     */     private Map<String, LabeledStatement> savedLabelSet;
/* 3310:     */     private List<Loop> savedLoopSet;
/* 3311:     */     private List<Jump> savedLoopAndSwitchSet;
/* 3312:     */     
/* 3313:     */     PerFunctionVariables(FunctionNode fnNode)
/* 3314:     */     {
/* 3315:3439 */       this.savedCurrentScriptOrFn = Parser.this.currentScriptOrFn;
/* 3316:3440 */       Parser.this.currentScriptOrFn = fnNode;
/* 3317:     */       
/* 3318:3442 */       this.savedCurrentScope = Parser.this.currentScope;
/* 3319:3443 */       Parser.this.currentScope = fnNode;
/* 3320:     */       
/* 3321:3445 */       this.savedNestingOfWith = Parser.this.nestingOfWith;
/* 3322:3446 */       Parser.this.nestingOfWith = 0;
/* 3323:     */       
/* 3324:3448 */       this.savedLabelSet = Parser.this.labelSet;
/* 3325:3449 */       Parser.this.labelSet = null;
/* 3326:     */       
/* 3327:3451 */       this.savedLoopSet = Parser.this.loopSet;
/* 3328:3452 */       Parser.this.loopSet = null;
/* 3329:     */       
/* 3330:3454 */       this.savedLoopAndSwitchSet = Parser.this.loopAndSwitchSet;
/* 3331:3455 */       Parser.this.loopAndSwitchSet = null;
/* 3332:     */       
/* 3333:3457 */       this.savedEndFlags = Parser.this.endFlags;
/* 3334:3458 */       Parser.this.endFlags = 0;
/* 3335:     */       
/* 3336:3460 */       this.savedInForInit = Parser.this.inForInit;
/* 3337:3461 */       Parser.this.inForInit = false;
/* 3338:     */     }
/* 3339:     */     
/* 3340:     */     void restore()
/* 3341:     */     {
/* 3342:3465 */       Parser.this.currentScriptOrFn = this.savedCurrentScriptOrFn;
/* 3343:3466 */       Parser.this.currentScope = this.savedCurrentScope;
/* 3344:3467 */       Parser.this.nestingOfWith = this.savedNestingOfWith;
/* 3345:3468 */       Parser.this.labelSet = this.savedLabelSet;
/* 3346:3469 */       Parser.this.loopSet = this.savedLoopSet;
/* 3347:3470 */       Parser.this.loopAndSwitchSet = this.savedLoopAndSwitchSet;
/* 3348:3471 */       Parser.this.endFlags = this.savedEndFlags;
/* 3349:3472 */       Parser.this.inForInit = this.savedInForInit;
/* 3350:     */     }
/* 3351:     */   }
/* 3352:     */   
/* 3353:     */   Node createDestructuringAssignment(int type, Node left, Node right)
/* 3354:     */   {
/* 3355:3490 */     String tempName = this.currentScriptOrFn.getNextTempName();
/* 3356:3491 */     Node result = destructuringAssignmentHelper(type, left, right, tempName);
/* 3357:     */     
/* 3358:3493 */     Node comma = result.getLastChild();
/* 3359:3494 */     comma.addChildToBack(createName(tempName));
/* 3360:3495 */     return result;
/* 3361:     */   }
/* 3362:     */   
/* 3363:     */   Node destructuringAssignmentHelper(int variableType, Node left, Node right, String tempName)
/* 3364:     */   {
/* 3365:3501 */     Scope result = createScopeNode(158, left.getLineno());
/* 3366:3502 */     result.addChildToFront(new Node(153, createName(39, tempName, right)));
/* 3367:     */     try
/* 3368:     */     {
/* 3369:3505 */       pushScope(result);
/* 3370:3506 */       defineSymbol(153, tempName, true);
/* 3371:     */     }
/* 3372:     */     finally
/* 3373:     */     {
/* 3374:3508 */       popScope();
/* 3375:     */     }
/* 3376:3510 */     Node comma = new Node(89);
/* 3377:3511 */     result.addChildToBack(comma);
/* 3378:3512 */     List<String> destructuringNames = new ArrayList();
/* 3379:3513 */     boolean empty = true;
/* 3380:3514 */     switch (left.getType())
/* 3381:     */     {
/* 3382:     */     case 65: 
/* 3383:3516 */       empty = destructuringArray((ArrayLiteral)left, variableType, tempName, comma, destructuringNames);
/* 3384:     */       
/* 3385:     */ 
/* 3386:3519 */       break;
/* 3387:     */     case 66: 
/* 3388:3521 */       empty = destructuringObject((ObjectLiteral)left, variableType, tempName, comma, destructuringNames);
/* 3389:     */       
/* 3390:     */ 
/* 3391:3524 */       break;
/* 3392:     */     case 33: 
/* 3393:     */     case 36: 
/* 3394:3527 */       comma.addChildToBack(simpleAssignment(left, createName(tempName)));
/* 3395:3528 */       break;
/* 3396:     */     default: 
/* 3397:3530 */       reportError("msg.bad.assign.left");
/* 3398:     */     }
/* 3399:3532 */     if (empty) {
/* 3400:3534 */       comma.addChildToBack(createNumber(0.0D));
/* 3401:     */     }
/* 3402:3536 */     result.putProp(22, destructuringNames);
/* 3403:3537 */     return result;
/* 3404:     */   }
/* 3405:     */   
/* 3406:     */   boolean destructuringArray(ArrayLiteral array, int variableType, String tempName, Node parent, List<String> destructuringNames)
/* 3407:     */   {
/* 3408:3546 */     boolean empty = true;
/* 3409:3547 */     int setOp = variableType == 154 ? 155 : 8;
/* 3410:     */     
/* 3411:3549 */     int index = 0;
/* 3412:3550 */     for (AstNode n : array.getElements()) {
/* 3413:3551 */       if (n.getType() == 128)
/* 3414:     */       {
/* 3415:3552 */         index++;
/* 3416:     */       }
/* 3417:     */       else
/* 3418:     */       {
/* 3419:3555 */         Node rightElem = new Node(36, createName(tempName), createNumber(index));
/* 3420:3558 */         if (n.getType() == 39)
/* 3421:     */         {
/* 3422:3559 */           String name = n.getString();
/* 3423:3560 */           parent.addChildToBack(new Node(setOp, createName(49, name, null), rightElem));
/* 3424:3564 */           if (variableType != -1)
/* 3425:     */           {
/* 3426:3565 */             defineSymbol(variableType, name, true);
/* 3427:3566 */             destructuringNames.add(name);
/* 3428:     */           }
/* 3429:     */         }
/* 3430:     */         else
/* 3431:     */         {
/* 3432:3569 */           parent.addChildToBack(destructuringAssignmentHelper(variableType, n, rightElem, this.currentScriptOrFn.getNextTempName()));
/* 3433:     */         }
/* 3434:3575 */         index++;
/* 3435:3576 */         empty = false;
/* 3436:     */       }
/* 3437:     */     }
/* 3438:3578 */     return empty;
/* 3439:     */   }
/* 3440:     */   
/* 3441:     */   boolean destructuringObject(ObjectLiteral node, int variableType, String tempName, Node parent, List<String> destructuringNames)
/* 3442:     */   {
/* 3443:3587 */     boolean empty = true;
/* 3444:3588 */     int setOp = variableType == 154 ? 155 : 8;
/* 3445:3591 */     for (ObjectProperty prop : node.getElements())
/* 3446:     */     {
/* 3447:3592 */       int lineno = 0;
/* 3448:3596 */       if (this.ts != null) {
/* 3449:3597 */         lineno = this.ts.lineno;
/* 3450:     */       }
/* 3451:3599 */       AstNode id = prop.getLeft();
/* 3452:3600 */       Node rightElem = null;
/* 3453:3601 */       if ((id instanceof Name))
/* 3454:     */       {
/* 3455:3602 */         Node s = Node.newString(((Name)id).getIdentifier());
/* 3456:3603 */         rightElem = new Node(33, createName(tempName), s);
/* 3457:     */       }
/* 3458:3604 */       else if ((id instanceof StringLiteral))
/* 3459:     */       {
/* 3460:3605 */         Node s = Node.newString(((StringLiteral)id).getValue());
/* 3461:3606 */         rightElem = new Node(33, createName(tempName), s);
/* 3462:     */       }
/* 3463:3607 */       else if ((id instanceof NumberLiteral))
/* 3464:     */       {
/* 3465:3608 */         Node s = createNumber((int)((NumberLiteral)id).getNumber());
/* 3466:3609 */         rightElem = new Node(36, createName(tempName), s);
/* 3467:     */       }
/* 3468:     */       else
/* 3469:     */       {
/* 3470:3611 */         throw codeBug();
/* 3471:     */       }
/* 3472:3613 */       rightElem.setLineno(lineno);
/* 3473:3614 */       AstNode value = prop.getRight();
/* 3474:3615 */       if (value.getType() == 39)
/* 3475:     */       {
/* 3476:3616 */         String name = ((Name)value).getIdentifier();
/* 3477:3617 */         parent.addChildToBack(new Node(setOp, createName(49, name, null), rightElem));
/* 3478:3621 */         if (variableType != -1)
/* 3479:     */         {
/* 3480:3622 */           defineSymbol(variableType, name, true);
/* 3481:3623 */           destructuringNames.add(name);
/* 3482:     */         }
/* 3483:     */       }
/* 3484:     */       else
/* 3485:     */       {
/* 3486:3626 */         parent.addChildToBack(destructuringAssignmentHelper(variableType, value, rightElem, this.currentScriptOrFn.getNextTempName()));
/* 3487:     */       }
/* 3488:3631 */       empty = false;
/* 3489:     */     }
/* 3490:3633 */     return empty;
/* 3491:     */   }
/* 3492:     */   
/* 3493:     */   protected Node createName(String name)
/* 3494:     */   {
/* 3495:3637 */     checkActivationName(name, 39);
/* 3496:3638 */     return Node.newString(39, name);
/* 3497:     */   }
/* 3498:     */   
/* 3499:     */   protected Node createName(int type, String name, Node child)
/* 3500:     */   {
/* 3501:3642 */     Node result = createName(name);
/* 3502:3643 */     result.setType(type);
/* 3503:3644 */     if (child != null) {
/* 3504:3645 */       result.addChildToBack(child);
/* 3505:     */     }
/* 3506:3646 */     return result;
/* 3507:     */   }
/* 3508:     */   
/* 3509:     */   protected Node createNumber(double number)
/* 3510:     */   {
/* 3511:3650 */     return Node.newNumber(number);
/* 3512:     */   }
/* 3513:     */   
/* 3514:     */   protected Scope createScopeNode(int token, int lineno)
/* 3515:     */   {
/* 3516:3662 */     Scope scope = new Scope();
/* 3517:3663 */     scope.setType(token);
/* 3518:3664 */     scope.setLineno(lineno);
/* 3519:3665 */     return scope;
/* 3520:     */   }
/* 3521:     */   
/* 3522:     */   protected Node simpleAssignment(Node left, Node right)
/* 3523:     */   {
/* 3524:3691 */     int nodeType = left.getType();
/* 3525:3692 */     switch (nodeType)
/* 3526:     */     {
/* 3527:     */     case 39: 
/* 3528:3694 */       if ((this.inUseStrictDirective) && ("eval".equals(((Name)left).getIdentifier()))) {
/* 3529:3697 */         reportError("msg.bad.id.strict", ((Name)left).getIdentifier());
/* 3530:     */       }
/* 3531:3700 */       left.setType(49);
/* 3532:3701 */       return new Node(8, left, right);
/* 3533:     */     case 33: 
/* 3534:     */     case 36: 
/* 3535:     */       Node id;
/* 3536:     */       Node obj;
/* 3537:     */       Node id;
/* 3538:3710 */       if ((left instanceof PropertyGet))
/* 3539:     */       {
/* 3540:3711 */         Node obj = ((PropertyGet)left).getTarget();
/* 3541:3712 */         id = ((PropertyGet)left).getProperty();
/* 3542:     */       }
/* 3543:     */       else
/* 3544:     */       {
/* 3545:     */         Node id;
/* 3546:3713 */         if ((left instanceof ElementGet))
/* 3547:     */         {
/* 3548:3714 */           Node obj = ((ElementGet)left).getTarget();
/* 3549:3715 */           id = ((ElementGet)left).getElement();
/* 3550:     */         }
/* 3551:     */         else
/* 3552:     */         {
/* 3553:3718 */           obj = left.getFirstChild();
/* 3554:3719 */           id = left.getLastChild();
/* 3555:     */         }
/* 3556:     */       }
/* 3557:     */       int type;
/* 3558:3722 */       if (nodeType == 33)
/* 3559:     */       {
/* 3560:3723 */         int type = 35;
/* 3561:     */         
/* 3562:     */ 
/* 3563:     */ 
/* 3564:     */ 
/* 3565:     */ 
/* 3566:3729 */         id.setType(41);
/* 3567:     */       }
/* 3568:     */       else
/* 3569:     */       {
/* 3570:3731 */         type = 37;
/* 3571:     */       }
/* 3572:3733 */       return new Node(type, obj, id, right);
/* 3573:     */     case 67: 
/* 3574:3736 */       Node ref = left.getFirstChild();
/* 3575:3737 */       checkMutableReference(ref);
/* 3576:3738 */       return new Node(68, ref, right);
/* 3577:     */     }
/* 3578:3742 */     throw codeBug();
/* 3579:     */   }
/* 3580:     */   
/* 3581:     */   protected void checkMutableReference(Node n)
/* 3582:     */   {
/* 3583:3746 */     int memberTypeFlags = n.getIntProp(16, 0);
/* 3584:3747 */     if ((memberTypeFlags & 0x4) != 0) {
/* 3585:3748 */       reportError("msg.bad.assign.left");
/* 3586:     */     }
/* 3587:     */   }
/* 3588:     */   
/* 3589:     */   protected AstNode removeParens(AstNode node)
/* 3590:     */   {
/* 3591:3754 */     while ((node instanceof ParenthesizedExpression)) {
/* 3592:3755 */       node = ((ParenthesizedExpression)node).getExpression();
/* 3593:     */     }
/* 3594:3757 */     return node;
/* 3595:     */   }
/* 3596:     */   
/* 3597:     */   void markDestructuring(AstNode node)
/* 3598:     */   {
/* 3599:3761 */     if ((node instanceof DestructuringForm)) {
/* 3600:3762 */       ((DestructuringForm)node).setIsDestructuring(true);
/* 3601:3763 */     } else if ((node instanceof ParenthesizedExpression)) {
/* 3602:3764 */       markDestructuring(((ParenthesizedExpression)node).getExpression());
/* 3603:     */     }
/* 3604:     */   }
/* 3605:     */   
/* 3606:     */   private RuntimeException codeBug()
/* 3607:     */     throws RuntimeException
/* 3608:     */   {
/* 3609:3772 */     throw Kit.codeBug("ts.cursor=" + this.ts.cursor + ", ts.tokenBeg=" + this.ts.tokenBeg + ", currentToken=" + this.currentToken);
/* 3610:     */   }
/* 3611:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.Parser
 * JD-Core Version:    0.7.0.1
 */