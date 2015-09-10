/*    1:     */ package javassist.compiler;
/*    2:     */ 
/*    3:     */ import javassist.compiler.ast.ASTList;
/*    4:     */ import javassist.compiler.ast.ASTree;
/*    5:     */ import javassist.compiler.ast.ArrayInit;
/*    6:     */ import javassist.compiler.ast.AssignExpr;
/*    7:     */ import javassist.compiler.ast.BinExpr;
/*    8:     */ import javassist.compiler.ast.CallExpr;
/*    9:     */ import javassist.compiler.ast.CastExpr;
/*   10:     */ import javassist.compiler.ast.CondExpr;
/*   11:     */ import javassist.compiler.ast.Declarator;
/*   12:     */ import javassist.compiler.ast.DoubleConst;
/*   13:     */ import javassist.compiler.ast.Expr;
/*   14:     */ import javassist.compiler.ast.FieldDecl;
/*   15:     */ import javassist.compiler.ast.InstanceOfExpr;
/*   16:     */ import javassist.compiler.ast.IntConst;
/*   17:     */ import javassist.compiler.ast.Keyword;
/*   18:     */ import javassist.compiler.ast.Member;
/*   19:     */ import javassist.compiler.ast.MethodDecl;
/*   20:     */ import javassist.compiler.ast.NewExpr;
/*   21:     */ import javassist.compiler.ast.Pair;
/*   22:     */ import javassist.compiler.ast.Stmnt;
/*   23:     */ import javassist.compiler.ast.StringL;
/*   24:     */ import javassist.compiler.ast.Symbol;
/*   25:     */ import javassist.compiler.ast.Variable;
/*   26:     */ 
/*   27:     */ public final class Parser
/*   28:     */   implements TokenId
/*   29:     */ {
/*   30:     */   private Lex lex;
/*   31:     */   
/*   32:     */   public Parser(Lex lex)
/*   33:     */   {
/*   34:  24 */     this.lex = lex;
/*   35:     */   }
/*   36:     */   
/*   37:     */   public boolean hasMore()
/*   38:     */   {
/*   39:  27 */     return this.lex.lookAhead() >= 0;
/*   40:     */   }
/*   41:     */   
/*   42:     */   public ASTList parseMember(SymbolTable tbl)
/*   43:     */     throws CompileError
/*   44:     */   {
/*   45:  33 */     ASTList mem = parseMember1(tbl);
/*   46:  34 */     if ((mem instanceof MethodDecl)) {
/*   47:  35 */       return parseMethod2(tbl, (MethodDecl)mem);
/*   48:     */     }
/*   49:  37 */     return mem;
/*   50:     */   }
/*   51:     */   
/*   52:     */   public ASTList parseMember1(SymbolTable tbl)
/*   53:     */     throws CompileError
/*   54:     */   {
/*   55:  43 */     ASTList mods = parseMemberMods();
/*   56:     */     
/*   57:  45 */     boolean isConstructor = false;
/*   58:     */     Declarator d;
/*   59:  46 */     if ((this.lex.lookAhead() == 400) && (this.lex.lookAhead(1) == 40))
/*   60:     */     {
/*   61:  47 */       Declarator d = new Declarator(344, 0);
/*   62:  48 */       isConstructor = true;
/*   63:     */     }
/*   64:     */     else
/*   65:     */     {
/*   66:  51 */       d = parseFormalType(tbl);
/*   67:     */     }
/*   68:  53 */     if (this.lex.get() != 400) {
/*   69:  54 */       throw new SyntaxError(this.lex);
/*   70:     */     }
/*   71:     */     String name;
/*   72:     */     String name;
/*   73:  57 */     if (isConstructor) {
/*   74:  58 */       name = "<init>";
/*   75:     */     } else {
/*   76:  60 */       name = this.lex.getString();
/*   77:     */     }
/*   78:  62 */     d.setVariable(new Symbol(name));
/*   79:  63 */     if ((isConstructor) || (this.lex.lookAhead() == 40)) {
/*   80:  64 */       return parseMethod1(tbl, isConstructor, mods, d);
/*   81:     */     }
/*   82:  66 */     return parseField(tbl, mods, d);
/*   83:     */   }
/*   84:     */   
/*   85:     */   private FieldDecl parseField(SymbolTable tbl, ASTList mods, Declarator d)
/*   86:     */     throws CompileError
/*   87:     */   {
/*   88:  77 */     ASTree expr = null;
/*   89:  78 */     if (this.lex.lookAhead() == 61)
/*   90:     */     {
/*   91:  79 */       this.lex.get();
/*   92:  80 */       expr = parseExpression(tbl);
/*   93:     */     }
/*   94:  83 */     int c = this.lex.get();
/*   95:  84 */     if (c == 59) {
/*   96:  85 */       return new FieldDecl(mods, new ASTList(d, new ASTList(expr)));
/*   97:     */     }
/*   98:  86 */     if (c == 44) {
/*   99:  87 */       throw new CompileError("only one field can be declared in one declaration", this.lex);
/*  100:     */     }
/*  101:  90 */     throw new SyntaxError(this.lex);
/*  102:     */   }
/*  103:     */   
/*  104:     */   private MethodDecl parseMethod1(SymbolTable tbl, boolean isConstructor, ASTList mods, Declarator d)
/*  105:     */     throws CompileError
/*  106:     */   {
/*  107: 107 */     if (this.lex.get() != 40) {
/*  108: 108 */       throw new SyntaxError(this.lex);
/*  109:     */     }
/*  110: 110 */     ASTList parms = null;
/*  111: 111 */     if (this.lex.lookAhead() != 41) {
/*  112:     */       for (;;)
/*  113:     */       {
/*  114: 113 */         parms = ASTList.append(parms, parseFormalParam(tbl));
/*  115: 114 */         int t = this.lex.lookAhead();
/*  116: 115 */         if (t == 44) {
/*  117: 116 */           this.lex.get();
/*  118:     */         } else {
/*  119: 117 */           if (t == 41) {
/*  120:     */             break;
/*  121:     */           }
/*  122:     */         }
/*  123:     */       }
/*  124:     */     }
/*  125: 121 */     this.lex.get();
/*  126: 122 */     d.addArrayDim(parseArrayDimension());
/*  127: 123 */     if ((isConstructor) && (d.getArrayDim() > 0)) {
/*  128: 124 */       throw new SyntaxError(this.lex);
/*  129:     */     }
/*  130: 126 */     ASTList throwsList = null;
/*  131: 127 */     if (this.lex.lookAhead() == 341)
/*  132:     */     {
/*  133: 128 */       this.lex.get();
/*  134:     */       for (;;)
/*  135:     */       {
/*  136: 130 */         throwsList = ASTList.append(throwsList, parseClassType(tbl));
/*  137: 131 */         if (this.lex.lookAhead() != 44) {
/*  138:     */           break;
/*  139:     */         }
/*  140: 132 */         this.lex.get();
/*  141:     */       }
/*  142:     */     }
/*  143: 138 */     return new MethodDecl(mods, new ASTList(d, ASTList.make(parms, throwsList, null)));
/*  144:     */   }
/*  145:     */   
/*  146:     */   public MethodDecl parseMethod2(SymbolTable tbl, MethodDecl md)
/*  147:     */     throws CompileError
/*  148:     */   {
/*  149: 147 */     Stmnt body = null;
/*  150: 148 */     if (this.lex.lookAhead() == 59)
/*  151:     */     {
/*  152: 149 */       this.lex.get();
/*  153:     */     }
/*  154:     */     else
/*  155:     */     {
/*  156: 151 */       body = parseBlock(tbl);
/*  157: 152 */       if (body == null) {
/*  158: 153 */         body = new Stmnt(66);
/*  159:     */       }
/*  160:     */     }
/*  161: 156 */     md.sublist(4).setHead(body);
/*  162: 157 */     return md;
/*  163:     */   }
/*  164:     */   
/*  165:     */   private ASTList parseMemberMods()
/*  166:     */   {
/*  167: 167 */     ASTList list = null;
/*  168:     */     for (;;)
/*  169:     */     {
/*  170: 169 */       int t = this.lex.lookAhead();
/*  171: 170 */       if ((t != 300) && (t != 315) && (t != 332) && (t != 331) && (t != 330) && (t != 338) && (t != 335) && (t != 345) && (t != 342) && (t != 347)) {
/*  172:     */         break;
/*  173:     */       }
/*  174: 173 */       list = new ASTList(new Keyword(this.lex.get()), list);
/*  175:     */     }
/*  176: 178 */     return list;
/*  177:     */   }
/*  178:     */   
/*  179:     */   private Declarator parseFormalType(SymbolTable tbl)
/*  180:     */     throws CompileError
/*  181:     */   {
/*  182: 184 */     int t = this.lex.lookAhead();
/*  183: 185 */     if ((isBuiltinType(t)) || (t == 344))
/*  184:     */     {
/*  185: 186 */       this.lex.get();
/*  186: 187 */       int dim = parseArrayDimension();
/*  187: 188 */       return new Declarator(t, dim);
/*  188:     */     }
/*  189: 191 */     ASTList name = parseClassType(tbl);
/*  190: 192 */     int dim = parseArrayDimension();
/*  191: 193 */     return new Declarator(name, dim);
/*  192:     */   }
/*  193:     */   
/*  194:     */   private static boolean isBuiltinType(int t)
/*  195:     */   {
/*  196: 198 */     return (t == 301) || (t == 303) || (t == 306) || (t == 334) || (t == 324) || (t == 326) || (t == 317) || (t == 312);
/*  197:     */   }
/*  198:     */   
/*  199:     */   private Declarator parseFormalParam(SymbolTable tbl)
/*  200:     */     throws CompileError
/*  201:     */   {
/*  202: 207 */     Declarator d = parseFormalType(tbl);
/*  203: 208 */     if (this.lex.get() != 400) {
/*  204: 209 */       throw new SyntaxError(this.lex);
/*  205:     */     }
/*  206: 211 */     String name = this.lex.getString();
/*  207: 212 */     d.setVariable(new Symbol(name));
/*  208: 213 */     d.addArrayDim(parseArrayDimension());
/*  209: 214 */     tbl.append(name, d);
/*  210: 215 */     return d;
/*  211:     */   }
/*  212:     */   
/*  213:     */   public Stmnt parseStatement(SymbolTable tbl)
/*  214:     */     throws CompileError
/*  215:     */   {
/*  216: 240 */     int t = this.lex.lookAhead();
/*  217: 241 */     if (t == 123) {
/*  218: 242 */       return parseBlock(tbl);
/*  219:     */     }
/*  220: 243 */     if (t == 59)
/*  221:     */     {
/*  222: 244 */       this.lex.get();
/*  223: 245 */       return new Stmnt(66);
/*  224:     */     }
/*  225: 247 */     if ((t == 400) && (this.lex.lookAhead(1) == 58))
/*  226:     */     {
/*  227: 248 */       this.lex.get();
/*  228: 249 */       String label = this.lex.getString();
/*  229: 250 */       this.lex.get();
/*  230: 251 */       return Stmnt.make(76, new Symbol(label), parseStatement(tbl));
/*  231:     */     }
/*  232: 253 */     if (t == 320) {
/*  233: 254 */       return parseIf(tbl);
/*  234:     */     }
/*  235: 255 */     if (t == 346) {
/*  236: 256 */       return parseWhile(tbl);
/*  237:     */     }
/*  238: 257 */     if (t == 311) {
/*  239: 258 */       return parseDo(tbl);
/*  240:     */     }
/*  241: 259 */     if (t == 318) {
/*  242: 260 */       return parseFor(tbl);
/*  243:     */     }
/*  244: 261 */     if (t == 343) {
/*  245: 262 */       return parseTry(tbl);
/*  246:     */     }
/*  247: 263 */     if (t == 337) {
/*  248: 264 */       return parseSwitch(tbl);
/*  249:     */     }
/*  250: 265 */     if (t == 338) {
/*  251: 266 */       return parseSynchronized(tbl);
/*  252:     */     }
/*  253: 267 */     if (t == 333) {
/*  254: 268 */       return parseReturn(tbl);
/*  255:     */     }
/*  256: 269 */     if (t == 340) {
/*  257: 270 */       return parseThrow(tbl);
/*  258:     */     }
/*  259: 271 */     if (t == 302) {
/*  260: 272 */       return parseBreak(tbl);
/*  261:     */     }
/*  262: 273 */     if (t == 309) {
/*  263: 274 */       return parseContinue(tbl);
/*  264:     */     }
/*  265: 276 */     return parseDeclarationOrExpression(tbl, false);
/*  266:     */   }
/*  267:     */   
/*  268:     */   private Stmnt parseBlock(SymbolTable tbl)
/*  269:     */     throws CompileError
/*  270:     */   {
/*  271: 282 */     if (this.lex.get() != 123) {
/*  272: 283 */       throw new SyntaxError(this.lex);
/*  273:     */     }
/*  274: 285 */     Stmnt body = null;
/*  275: 286 */     SymbolTable tbl2 = new SymbolTable(tbl);
/*  276: 287 */     while (this.lex.lookAhead() != 125)
/*  277:     */     {
/*  278: 288 */       Stmnt s = parseStatement(tbl2);
/*  279: 289 */       if (s != null) {
/*  280: 290 */         body = (Stmnt)ASTList.concat(body, new Stmnt(66, s));
/*  281:     */       }
/*  282:     */     }
/*  283: 293 */     this.lex.get();
/*  284: 294 */     if (body == null) {
/*  285: 295 */       return new Stmnt(66);
/*  286:     */     }
/*  287: 297 */     return body;
/*  288:     */   }
/*  289:     */   
/*  290:     */   private Stmnt parseIf(SymbolTable tbl)
/*  291:     */     throws CompileError
/*  292:     */   {
/*  293: 304 */     int t = this.lex.get();
/*  294: 305 */     ASTree expr = parseParExpression(tbl);
/*  295: 306 */     Stmnt thenp = parseStatement(tbl);
/*  296:     */     Stmnt elsep;
/*  297:     */     Stmnt elsep;
/*  298: 308 */     if (this.lex.lookAhead() == 313)
/*  299:     */     {
/*  300: 309 */       this.lex.get();
/*  301: 310 */       elsep = parseStatement(tbl);
/*  302:     */     }
/*  303:     */     else
/*  304:     */     {
/*  305: 313 */       elsep = null;
/*  306:     */     }
/*  307: 315 */     return new Stmnt(t, expr, new ASTList(thenp, new ASTList(elsep)));
/*  308:     */   }
/*  309:     */   
/*  310:     */   private Stmnt parseWhile(SymbolTable tbl)
/*  311:     */     throws CompileError
/*  312:     */   {
/*  313: 323 */     int t = this.lex.get();
/*  314: 324 */     ASTree expr = parseParExpression(tbl);
/*  315: 325 */     Stmnt body = parseStatement(tbl);
/*  316: 326 */     return new Stmnt(t, expr, body);
/*  317:     */   }
/*  318:     */   
/*  319:     */   private Stmnt parseDo(SymbolTable tbl)
/*  320:     */     throws CompileError
/*  321:     */   {
/*  322: 332 */     int t = this.lex.get();
/*  323: 333 */     Stmnt body = parseStatement(tbl);
/*  324: 334 */     if ((this.lex.get() != 346) || (this.lex.get() != 40)) {
/*  325: 335 */       throw new SyntaxError(this.lex);
/*  326:     */     }
/*  327: 337 */     ASTree expr = parseExpression(tbl);
/*  328: 338 */     if ((this.lex.get() != 41) || (this.lex.get() != 59)) {
/*  329: 339 */       throw new SyntaxError(this.lex);
/*  330:     */     }
/*  331: 341 */     return new Stmnt(t, expr, body);
/*  332:     */   }
/*  333:     */   
/*  334:     */   private Stmnt parseFor(SymbolTable tbl)
/*  335:     */     throws CompileError
/*  336:     */   {
/*  337: 350 */     int t = this.lex.get();
/*  338:     */     
/*  339: 352 */     SymbolTable tbl2 = new SymbolTable(tbl);
/*  340: 354 */     if (this.lex.get() != 40) {
/*  341: 355 */       throw new SyntaxError(this.lex);
/*  342:     */     }
/*  343:     */     Stmnt expr1;
/*  344:     */     Stmnt expr1;
/*  345: 357 */     if (this.lex.lookAhead() == 59)
/*  346:     */     {
/*  347: 358 */       this.lex.get();
/*  348: 359 */       expr1 = null;
/*  349:     */     }
/*  350:     */     else
/*  351:     */     {
/*  352: 362 */       expr1 = parseDeclarationOrExpression(tbl2, true);
/*  353:     */     }
/*  354:     */     ASTree expr2;
/*  355:     */     ASTree expr2;
/*  356: 364 */     if (this.lex.lookAhead() == 59) {
/*  357: 365 */       expr2 = null;
/*  358:     */     } else {
/*  359: 367 */       expr2 = parseExpression(tbl2);
/*  360:     */     }
/*  361: 369 */     if (this.lex.get() != 59) {
/*  362: 370 */       throw new CompileError("; is missing", this.lex);
/*  363:     */     }
/*  364:     */     Stmnt expr3;
/*  365:     */     Stmnt expr3;
/*  366: 372 */     if (this.lex.lookAhead() == 41) {
/*  367: 373 */       expr3 = null;
/*  368:     */     } else {
/*  369: 375 */       expr3 = parseExprList(tbl2);
/*  370:     */     }
/*  371: 377 */     if (this.lex.get() != 41) {
/*  372: 378 */       throw new CompileError(") is missing", this.lex);
/*  373:     */     }
/*  374: 380 */     Stmnt body = parseStatement(tbl2);
/*  375: 381 */     return new Stmnt(t, expr1, new ASTList(expr2, new ASTList(expr3, body)));
/*  376:     */   }
/*  377:     */   
/*  378:     */   private Stmnt parseSwitch(SymbolTable tbl)
/*  379:     */     throws CompileError
/*  380:     */   {
/*  381: 393 */     int t = this.lex.get();
/*  382: 394 */     ASTree expr = parseParExpression(tbl);
/*  383: 395 */     Stmnt body = parseSwitchBlock(tbl);
/*  384: 396 */     return new Stmnt(t, expr, body);
/*  385:     */   }
/*  386:     */   
/*  387:     */   private Stmnt parseSwitchBlock(SymbolTable tbl)
/*  388:     */     throws CompileError
/*  389:     */   {
/*  390: 400 */     if (this.lex.get() != 123) {
/*  391: 401 */       throw new SyntaxError(this.lex);
/*  392:     */     }
/*  393: 403 */     SymbolTable tbl2 = new SymbolTable(tbl);
/*  394: 404 */     Stmnt s = parseStmntOrCase(tbl2);
/*  395: 405 */     if (s == null) {
/*  396: 406 */       throw new CompileError("empty switch block", this.lex);
/*  397:     */     }
/*  398: 408 */     int op = s.getOperator();
/*  399: 409 */     if ((op != 304) && (op != 310)) {
/*  400: 410 */       throw new CompileError("no case or default in a switch block", this.lex);
/*  401:     */     }
/*  402: 413 */     Stmnt body = new Stmnt(66, s);
/*  403: 414 */     while (this.lex.lookAhead() != 125)
/*  404:     */     {
/*  405: 415 */       Stmnt s2 = parseStmntOrCase(tbl2);
/*  406: 416 */       if (s2 != null)
/*  407:     */       {
/*  408: 417 */         int op2 = s2.getOperator();
/*  409: 418 */         if ((op2 == 304) || (op2 == 310))
/*  410:     */         {
/*  411: 419 */           body = (Stmnt)ASTList.concat(body, new Stmnt(66, s2));
/*  412: 420 */           s = s2;
/*  413:     */         }
/*  414:     */         else
/*  415:     */         {
/*  416: 423 */           s = (Stmnt)ASTList.concat(s, new Stmnt(66, s2));
/*  417:     */         }
/*  418:     */       }
/*  419:     */     }
/*  420: 427 */     this.lex.get();
/*  421: 428 */     return body;
/*  422:     */   }
/*  423:     */   
/*  424:     */   private Stmnt parseStmntOrCase(SymbolTable tbl)
/*  425:     */     throws CompileError
/*  426:     */   {
/*  427: 432 */     int t = this.lex.lookAhead();
/*  428: 433 */     if ((t != 304) && (t != 310)) {
/*  429: 434 */       return parseStatement(tbl);
/*  430:     */     }
/*  431: 436 */     this.lex.get();
/*  432:     */     Stmnt s;
/*  433:     */     Stmnt s;
/*  434: 438 */     if (t == 304) {
/*  435: 439 */       s = new Stmnt(t, parseExpression(tbl));
/*  436:     */     } else {
/*  437: 441 */       s = new Stmnt(310);
/*  438:     */     }
/*  439: 443 */     if (this.lex.get() != 58) {
/*  440: 444 */       throw new CompileError(": is missing", this.lex);
/*  441:     */     }
/*  442: 446 */     return s;
/*  443:     */   }
/*  444:     */   
/*  445:     */   private Stmnt parseSynchronized(SymbolTable tbl)
/*  446:     */     throws CompileError
/*  447:     */   {
/*  448: 453 */     int t = this.lex.get();
/*  449: 454 */     if (this.lex.get() != 40) {
/*  450: 455 */       throw new SyntaxError(this.lex);
/*  451:     */     }
/*  452: 457 */     ASTree expr = parseExpression(tbl);
/*  453: 458 */     if (this.lex.get() != 41) {
/*  454: 459 */       throw new SyntaxError(this.lex);
/*  455:     */     }
/*  456: 461 */     Stmnt body = parseBlock(tbl);
/*  457: 462 */     return new Stmnt(t, expr, body);
/*  458:     */   }
/*  459:     */   
/*  460:     */   private Stmnt parseTry(SymbolTable tbl)
/*  461:     */     throws CompileError
/*  462:     */   {
/*  463: 471 */     this.lex.get();
/*  464: 472 */     Stmnt block = parseBlock(tbl);
/*  465: 473 */     ASTList catchList = null;
/*  466: 474 */     while (this.lex.lookAhead() == 305)
/*  467:     */     {
/*  468: 475 */       this.lex.get();
/*  469: 476 */       if (this.lex.get() != 40) {
/*  470: 477 */         throw new SyntaxError(this.lex);
/*  471:     */       }
/*  472: 479 */       SymbolTable tbl2 = new SymbolTable(tbl);
/*  473: 480 */       Declarator d = parseFormalParam(tbl2);
/*  474: 481 */       if ((d.getArrayDim() > 0) || (d.getType() != 307)) {
/*  475: 482 */         throw new SyntaxError(this.lex);
/*  476:     */       }
/*  477: 484 */       if (this.lex.get() != 41) {
/*  478: 485 */         throw new SyntaxError(this.lex);
/*  479:     */       }
/*  480: 487 */       Stmnt b = parseBlock(tbl2);
/*  481: 488 */       catchList = ASTList.append(catchList, new Pair(d, b));
/*  482:     */     }
/*  483: 491 */     Stmnt finallyBlock = null;
/*  484: 492 */     if (this.lex.lookAhead() == 316)
/*  485:     */     {
/*  486: 493 */       this.lex.get();
/*  487: 494 */       finallyBlock = parseBlock(tbl);
/*  488:     */     }
/*  489: 497 */     return Stmnt.make(343, block, catchList, finallyBlock);
/*  490:     */   }
/*  491:     */   
/*  492:     */   private Stmnt parseReturn(SymbolTable tbl)
/*  493:     */     throws CompileError
/*  494:     */   {
/*  495: 503 */     int t = this.lex.get();
/*  496: 504 */     Stmnt s = new Stmnt(t);
/*  497: 505 */     if (this.lex.lookAhead() != 59) {
/*  498: 506 */       s.setLeft(parseExpression(tbl));
/*  499:     */     }
/*  500: 508 */     if (this.lex.get() != 59) {
/*  501: 509 */       throw new CompileError("; is missing", this.lex);
/*  502:     */     }
/*  503: 511 */     return s;
/*  504:     */   }
/*  505:     */   
/*  506:     */   private Stmnt parseThrow(SymbolTable tbl)
/*  507:     */     throws CompileError
/*  508:     */   {
/*  509: 517 */     int t = this.lex.get();
/*  510: 518 */     ASTree expr = parseExpression(tbl);
/*  511: 519 */     if (this.lex.get() != 59) {
/*  512: 520 */       throw new CompileError("; is missing", this.lex);
/*  513:     */     }
/*  514: 522 */     return new Stmnt(t, expr);
/*  515:     */   }
/*  516:     */   
/*  517:     */   private Stmnt parseBreak(SymbolTable tbl)
/*  518:     */     throws CompileError
/*  519:     */   {
/*  520: 530 */     return parseContinue(tbl);
/*  521:     */   }
/*  522:     */   
/*  523:     */   private Stmnt parseContinue(SymbolTable tbl)
/*  524:     */     throws CompileError
/*  525:     */   {
/*  526: 538 */     int t = this.lex.get();
/*  527: 539 */     Stmnt s = new Stmnt(t);
/*  528: 540 */     int t2 = this.lex.get();
/*  529: 541 */     if (t2 == 400)
/*  530:     */     {
/*  531: 542 */       s.setLeft(new Symbol(this.lex.getString()));
/*  532: 543 */       t2 = this.lex.get();
/*  533:     */     }
/*  534: 546 */     if (t2 != 59) {
/*  535: 547 */       throw new CompileError("; is missing", this.lex);
/*  536:     */     }
/*  537: 549 */     return s;
/*  538:     */   }
/*  539:     */   
/*  540:     */   private Stmnt parseDeclarationOrExpression(SymbolTable tbl, boolean exprList)
/*  541:     */     throws CompileError
/*  542:     */   {
/*  543: 565 */     int t = this.lex.lookAhead();
/*  544: 566 */     while (t == 315)
/*  545:     */     {
/*  546: 567 */       this.lex.get();
/*  547: 568 */       t = this.lex.lookAhead();
/*  548:     */     }
/*  549: 571 */     if (isBuiltinType(t))
/*  550:     */     {
/*  551: 572 */       t = this.lex.get();
/*  552: 573 */       int dim = parseArrayDimension();
/*  553: 574 */       return parseDeclarators(tbl, new Declarator(t, dim));
/*  554:     */     }
/*  555: 576 */     if (t == 400)
/*  556:     */     {
/*  557: 577 */       int i = nextIsClassType(0);
/*  558: 578 */       if ((i >= 0) && 
/*  559: 579 */         (this.lex.lookAhead(i) == 400))
/*  560:     */       {
/*  561: 580 */         ASTList name = parseClassType(tbl);
/*  562: 581 */         int dim = parseArrayDimension();
/*  563: 582 */         return parseDeclarators(tbl, new Declarator(name, dim));
/*  564:     */       }
/*  565:     */     }
/*  566:     */     Stmnt expr;
/*  567:     */     Stmnt expr;
/*  568: 587 */     if (exprList) {
/*  569: 588 */       expr = parseExprList(tbl);
/*  570:     */     } else {
/*  571: 590 */       expr = new Stmnt(69, parseExpression(tbl));
/*  572:     */     }
/*  573: 592 */     if (this.lex.get() != 59) {
/*  574: 593 */       throw new CompileError("; is missing", this.lex);
/*  575:     */     }
/*  576: 595 */     return expr;
/*  577:     */   }
/*  578:     */   
/*  579:     */   private Stmnt parseExprList(SymbolTable tbl)
/*  580:     */     throws CompileError
/*  581:     */   {
/*  582: 601 */     Stmnt expr = null;
/*  583:     */     for (;;)
/*  584:     */     {
/*  585: 603 */       Stmnt e = new Stmnt(69, parseExpression(tbl));
/*  586: 604 */       expr = (Stmnt)ASTList.concat(expr, new Stmnt(66, e));
/*  587: 605 */       if (this.lex.lookAhead() == 44) {
/*  588: 606 */         this.lex.get();
/*  589:     */       } else {
/*  590: 608 */         return expr;
/*  591:     */       }
/*  592:     */     }
/*  593:     */   }
/*  594:     */   
/*  595:     */   private Stmnt parseDeclarators(SymbolTable tbl, Declarator d)
/*  596:     */     throws CompileError
/*  597:     */   {
/*  598: 617 */     Stmnt decl = null;
/*  599:     */     for (;;)
/*  600:     */     {
/*  601: 619 */       decl = (Stmnt)ASTList.concat(decl, new Stmnt(68, parseDeclarator(tbl, d)));
/*  602:     */       
/*  603: 621 */       int t = this.lex.get();
/*  604: 622 */       if (t == 59) {
/*  605: 623 */         return decl;
/*  606:     */       }
/*  607: 624 */       if (t != 44) {
/*  608: 625 */         throw new CompileError("; is missing", this.lex);
/*  609:     */       }
/*  610:     */     }
/*  611:     */   }
/*  612:     */   
/*  613:     */   private Declarator parseDeclarator(SymbolTable tbl, Declarator d)
/*  614:     */     throws CompileError
/*  615:     */   {
/*  616: 634 */     if ((this.lex.get() != 400) || (d.getType() == 344)) {
/*  617: 635 */       throw new SyntaxError(this.lex);
/*  618:     */     }
/*  619: 637 */     String name = this.lex.getString();
/*  620: 638 */     Symbol symbol = new Symbol(name);
/*  621: 639 */     int dim = parseArrayDimension();
/*  622: 640 */     ASTree init = null;
/*  623: 641 */     if (this.lex.lookAhead() == 61)
/*  624:     */     {
/*  625: 642 */       this.lex.get();
/*  626: 643 */       init = parseInitializer(tbl);
/*  627:     */     }
/*  628: 646 */     Declarator decl = d.make(symbol, dim, init);
/*  629: 647 */     tbl.append(name, decl);
/*  630: 648 */     return decl;
/*  631:     */   }
/*  632:     */   
/*  633:     */   private ASTree parseInitializer(SymbolTable tbl)
/*  634:     */     throws CompileError
/*  635:     */   {
/*  636: 654 */     if (this.lex.lookAhead() == 123) {
/*  637: 655 */       return parseArrayInitializer(tbl);
/*  638:     */     }
/*  639: 657 */     return parseExpression(tbl);
/*  640:     */   }
/*  641:     */   
/*  642:     */   private ArrayInit parseArrayInitializer(SymbolTable tbl)
/*  643:     */     throws CompileError
/*  644:     */   {
/*  645: 666 */     this.lex.get();
/*  646: 667 */     ASTree expr = parseExpression(tbl);
/*  647: 668 */     ArrayInit init = new ArrayInit(expr);
/*  648: 669 */     while (this.lex.lookAhead() == 44)
/*  649:     */     {
/*  650: 670 */       this.lex.get();
/*  651: 671 */       expr = parseExpression(tbl);
/*  652: 672 */       ASTList.append(init, expr);
/*  653:     */     }
/*  654: 675 */     if (this.lex.get() != 125) {
/*  655: 676 */       throw new SyntaxError(this.lex);
/*  656:     */     }
/*  657: 678 */     return init;
/*  658:     */   }
/*  659:     */   
/*  660:     */   private ASTree parseParExpression(SymbolTable tbl)
/*  661:     */     throws CompileError
/*  662:     */   {
/*  663: 684 */     if (this.lex.get() != 40) {
/*  664: 685 */       throw new SyntaxError(this.lex);
/*  665:     */     }
/*  666: 687 */     ASTree expr = parseExpression(tbl);
/*  667: 688 */     if (this.lex.get() != 41) {
/*  668: 689 */       throw new SyntaxError(this.lex);
/*  669:     */     }
/*  670: 691 */     return expr;
/*  671:     */   }
/*  672:     */   
/*  673:     */   public ASTree parseExpression(SymbolTable tbl)
/*  674:     */     throws CompileError
/*  675:     */   {
/*  676: 698 */     ASTree left = parseConditionalExpr(tbl);
/*  677: 699 */     if (!isAssignOp(this.lex.lookAhead())) {
/*  678: 700 */       return left;
/*  679:     */     }
/*  680: 702 */     int t = this.lex.get();
/*  681: 703 */     ASTree right = parseExpression(tbl);
/*  682: 704 */     return AssignExpr.makeAssign(t, left, right);
/*  683:     */   }
/*  684:     */   
/*  685:     */   private static boolean isAssignOp(int t)
/*  686:     */   {
/*  687: 708 */     return (t == 61) || (t == 351) || (t == 352) || (t == 353) || (t == 354) || (t == 355) || (t == 356) || (t == 360) || (t == 361) || (t == 365) || (t == 367) || (t == 371);
/*  688:     */   }
/*  689:     */   
/*  690:     */   private ASTree parseConditionalExpr(SymbolTable tbl)
/*  691:     */     throws CompileError
/*  692:     */   {
/*  693: 718 */     ASTree cond = parseBinaryExpr(tbl);
/*  694: 719 */     if (this.lex.lookAhead() == 63)
/*  695:     */     {
/*  696: 720 */       this.lex.get();
/*  697: 721 */       ASTree thenExpr = parseExpression(tbl);
/*  698: 722 */       if (this.lex.get() != 58) {
/*  699: 723 */         throw new CompileError(": is missing", this.lex);
/*  700:     */       }
/*  701: 725 */       ASTree elseExpr = parseExpression(tbl);
/*  702: 726 */       return new CondExpr(cond, thenExpr, elseExpr);
/*  703:     */     }
/*  704: 729 */     return cond;
/*  705:     */   }
/*  706:     */   
/*  707:     */   private ASTree parseBinaryExpr(SymbolTable tbl)
/*  708:     */     throws CompileError
/*  709:     */   {
/*  710: 774 */     ASTree expr = parseUnaryExpr(tbl);
/*  711:     */     for (;;)
/*  712:     */     {
/*  713: 776 */       int t = this.lex.lookAhead();
/*  714: 777 */       int p = getOpPrecedence(t);
/*  715: 778 */       if (p == 0) {
/*  716: 779 */         return expr;
/*  717:     */       }
/*  718: 781 */       expr = binaryExpr2(tbl, expr, p);
/*  719:     */     }
/*  720:     */   }
/*  721:     */   
/*  722:     */   private ASTree parseInstanceOf(SymbolTable tbl, ASTree expr)
/*  723:     */     throws CompileError
/*  724:     */   {
/*  725: 788 */     int t = this.lex.lookAhead();
/*  726: 789 */     if (isBuiltinType(t))
/*  727:     */     {
/*  728: 790 */       this.lex.get();
/*  729: 791 */       int dim = parseArrayDimension();
/*  730: 792 */       return new InstanceOfExpr(t, dim, expr);
/*  731:     */     }
/*  732: 795 */     ASTList name = parseClassType(tbl);
/*  733: 796 */     int dim = parseArrayDimension();
/*  734: 797 */     return new InstanceOfExpr(name, dim, expr);
/*  735:     */   }
/*  736:     */   
/*  737:     */   private ASTree binaryExpr2(SymbolTable tbl, ASTree expr, int prec)
/*  738:     */     throws CompileError
/*  739:     */   {
/*  740: 804 */     int t = this.lex.get();
/*  741: 805 */     if (t == 323) {
/*  742: 806 */       return parseInstanceOf(tbl, expr);
/*  743:     */     }
/*  744: 808 */     ASTree expr2 = parseUnaryExpr(tbl);
/*  745:     */     for (;;)
/*  746:     */     {
/*  747: 810 */       int t2 = this.lex.lookAhead();
/*  748: 811 */       int p2 = getOpPrecedence(t2);
/*  749: 812 */       if ((p2 != 0) && (prec > p2)) {
/*  750: 813 */         expr2 = binaryExpr2(tbl, expr2, p2);
/*  751:     */       } else {
/*  752: 815 */         return BinExpr.makeBin(t, expr, expr2);
/*  753:     */       }
/*  754:     */     }
/*  755:     */   }
/*  756:     */   
/*  757: 820 */   private static final int[] binaryOpPrecedence = { 0, 0, 0, 0, 1, 6, 0, 0, 0, 1, 2, 0, 2, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 4, 0 };
/*  758:     */   
/*  759:     */   private int getOpPrecedence(int c)
/*  760:     */   {
/*  761: 827 */     if ((33 <= c) && (c <= 63)) {
/*  762: 828 */       return binaryOpPrecedence[(c - 33)];
/*  763:     */     }
/*  764: 829 */     if (c == 94) {
/*  765: 830 */       return 7;
/*  766:     */     }
/*  767: 831 */     if (c == 124) {
/*  768: 832 */       return 8;
/*  769:     */     }
/*  770: 833 */     if (c == 369) {
/*  771: 834 */       return 9;
/*  772:     */     }
/*  773: 835 */     if (c == 368) {
/*  774: 836 */       return 10;
/*  775:     */     }
/*  776: 837 */     if ((c == 358) || (c == 350)) {
/*  777: 838 */       return 5;
/*  778:     */     }
/*  779: 839 */     if ((c == 357) || (c == 359) || (c == 323)) {
/*  780: 840 */       return 4;
/*  781:     */     }
/*  782: 841 */     if ((c == 364) || (c == 366) || (c == 370)) {
/*  783: 842 */       return 3;
/*  784:     */     }
/*  785: 844 */     return 0;
/*  786:     */   }
/*  787:     */   
/*  788:     */   private ASTree parseUnaryExpr(SymbolTable tbl)
/*  789:     */     throws CompileError
/*  790:     */   {
/*  791: 858 */     switch (this.lex.lookAhead())
/*  792:     */     {
/*  793:     */     case 33: 
/*  794:     */     case 43: 
/*  795:     */     case 45: 
/*  796:     */     case 126: 
/*  797:     */     case 362: 
/*  798:     */     case 363: 
/*  799: 865 */       int t = this.lex.get();
/*  800: 866 */       if (t == 45)
/*  801:     */       {
/*  802: 867 */         int t2 = this.lex.lookAhead();
/*  803: 868 */         switch (t2)
/*  804:     */         {
/*  805:     */         case 401: 
/*  806:     */         case 402: 
/*  807:     */         case 403: 
/*  808: 872 */           this.lex.get();
/*  809: 873 */           return new IntConst(-this.lex.getLong(), t2);
/*  810:     */         case 404: 
/*  811:     */         case 405: 
/*  812: 876 */           this.lex.get();
/*  813: 877 */           return new DoubleConst(-this.lex.getDouble(), t2);
/*  814:     */         }
/*  815:     */       }
/*  816: 883 */       return Expr.make(t, parseUnaryExpr(tbl));
/*  817:     */     case 40: 
/*  818: 885 */       return parseCast(tbl);
/*  819:     */     }
/*  820: 887 */     return parsePostfix(tbl);
/*  821:     */   }
/*  822:     */   
/*  823:     */   private ASTree parseCast(SymbolTable tbl)
/*  824:     */     throws CompileError
/*  825:     */   {
/*  826: 900 */     int t = this.lex.lookAhead(1);
/*  827: 901 */     if ((isBuiltinType(t)) && (nextIsBuiltinCast()))
/*  828:     */     {
/*  829: 902 */       this.lex.get();
/*  830: 903 */       this.lex.get();
/*  831: 904 */       int dim = parseArrayDimension();
/*  832: 905 */       if (this.lex.get() != 41) {
/*  833: 906 */         throw new CompileError(") is missing", this.lex);
/*  834:     */       }
/*  835: 908 */       return new CastExpr(t, dim, parseUnaryExpr(tbl));
/*  836:     */     }
/*  837: 910 */     if ((t == 400) && (nextIsClassCast()))
/*  838:     */     {
/*  839: 911 */       this.lex.get();
/*  840: 912 */       ASTList name = parseClassType(tbl);
/*  841: 913 */       int dim = parseArrayDimension();
/*  842: 914 */       if (this.lex.get() != 41) {
/*  843: 915 */         throw new CompileError(") is missing", this.lex);
/*  844:     */       }
/*  845: 917 */       return new CastExpr(name, dim, parseUnaryExpr(tbl));
/*  846:     */     }
/*  847: 920 */     return parsePostfix(tbl);
/*  848:     */   }
/*  849:     */   
/*  850:     */   private boolean nextIsBuiltinCast()
/*  851:     */   {
/*  852: 925 */     int i = 2;
/*  853:     */     int t;
/*  854: 926 */     while ((t = this.lex.lookAhead(i++)) == 91) {
/*  855: 927 */       if (this.lex.lookAhead(i++) != 93) {
/*  856: 928 */         return false;
/*  857:     */       }
/*  858:     */     }
/*  859: 930 */     return this.lex.lookAhead(i - 1) == 41;
/*  860:     */   }
/*  861:     */   
/*  862:     */   private boolean nextIsClassCast()
/*  863:     */   {
/*  864: 934 */     int i = nextIsClassType(1);
/*  865: 935 */     if (i < 0) {
/*  866: 936 */       return false;
/*  867:     */     }
/*  868: 938 */     int t = this.lex.lookAhead(i);
/*  869: 939 */     if (t != 41) {
/*  870: 940 */       return false;
/*  871:     */     }
/*  872: 942 */     t = this.lex.lookAhead(i + 1);
/*  873: 943 */     return (t == 40) || (t == 412) || (t == 406) || (t == 400) || (t == 339) || (t == 336) || (t == 328) || (t == 410) || (t == 411) || (t == 403) || (t == 402) || (t == 401) || (t == 405) || (t == 404);
/*  874:     */   }
/*  875:     */   
/*  876:     */   private int nextIsClassType(int i)
/*  877:     */   {
/*  878: 952 */     while (this.lex.lookAhead(++i) == 46) {
/*  879: 953 */       if (this.lex.lookAhead(++i) != 400) {
/*  880: 954 */         return -1;
/*  881:     */       }
/*  882:     */     }
/*  883:     */     int t;
/*  884: 956 */     while ((t = this.lex.lookAhead(i++)) == 91) {
/*  885: 957 */       if (this.lex.lookAhead(i++) != 93) {
/*  886: 958 */         return -1;
/*  887:     */       }
/*  888:     */     }
/*  889: 960 */     return i - 1;
/*  890:     */   }
/*  891:     */   
/*  892:     */   private int parseArrayDimension()
/*  893:     */     throws CompileError
/*  894:     */   {
/*  895: 966 */     int arrayDim = 0;
/*  896: 967 */     while (this.lex.lookAhead() == 91)
/*  897:     */     {
/*  898: 968 */       arrayDim++;
/*  899: 969 */       this.lex.get();
/*  900: 970 */       if (this.lex.get() != 93) {
/*  901: 971 */         throw new CompileError("] is missing", this.lex);
/*  902:     */       }
/*  903:     */     }
/*  904: 974 */     return arrayDim;
/*  905:     */   }
/*  906:     */   
/*  907:     */   private ASTList parseClassType(SymbolTable tbl)
/*  908:     */     throws CompileError
/*  909:     */   {
/*  910: 980 */     ASTList list = null;
/*  911:     */     for (;;)
/*  912:     */     {
/*  913: 982 */       if (this.lex.get() != 400) {
/*  914: 983 */         throw new SyntaxError(this.lex);
/*  915:     */       }
/*  916: 985 */       list = ASTList.append(list, new Symbol(this.lex.getString()));
/*  917: 986 */       if (this.lex.lookAhead() != 46) {
/*  918:     */         break;
/*  919:     */       }
/*  920: 987 */       this.lex.get();
/*  921:     */     }
/*  922: 992 */     return list;
/*  923:     */   }
/*  924:     */   
/*  925:     */   private ASTree parsePostfix(SymbolTable tbl)
/*  926:     */     throws CompileError
/*  927:     */   {
/*  928:1012 */     int token = this.lex.lookAhead();
/*  929:1013 */     switch (token)
/*  930:     */     {
/*  931:     */     case 401: 
/*  932:     */     case 402: 
/*  933:     */     case 403: 
/*  934:1017 */       this.lex.get();
/*  935:1018 */       return new IntConst(this.lex.getLong(), token);
/*  936:     */     case 404: 
/*  937:     */     case 405: 
/*  938:1021 */       this.lex.get();
/*  939:1022 */       return new DoubleConst(this.lex.getDouble(), token);
/*  940:     */     }
/*  941:1029 */     ASTree expr = parsePrimaryExpr(tbl);
/*  942:     */     for (;;)
/*  943:     */     {
/*  944:     */       int t;
/*  945:1032 */       switch (this.lex.lookAhead())
/*  946:     */       {
/*  947:     */       case 40: 
/*  948:1034 */         expr = parseMethodCall(tbl, expr);
/*  949:1035 */         break;
/*  950:     */       case 91: 
/*  951:1037 */         if (this.lex.lookAhead(1) == 93)
/*  952:     */         {
/*  953:1038 */           int dim = parseArrayDimension();
/*  954:1039 */           if ((this.lex.get() != 46) || (this.lex.get() != 307)) {
/*  955:1040 */             throw new SyntaxError(this.lex);
/*  956:     */           }
/*  957:1042 */           expr = parseDotClass(expr, dim);
/*  958:     */         }
/*  959:     */         else
/*  960:     */         {
/*  961:1045 */           ASTree index = parseArrayIndex(tbl);
/*  962:1046 */           if (index == null) {
/*  963:1047 */             throw new SyntaxError(this.lex);
/*  964:     */           }
/*  965:1049 */           expr = Expr.make(65, expr, index);
/*  966:     */         }
/*  967:1051 */         break;
/*  968:     */       case 362: 
/*  969:     */       case 363: 
/*  970:1054 */         t = this.lex.get();
/*  971:1055 */         expr = Expr.make(t, null, expr);
/*  972:1056 */         break;
/*  973:     */       case 46: 
/*  974:1058 */         this.lex.get();
/*  975:1059 */         t = this.lex.get();
/*  976:1060 */         if (t == 307)
/*  977:     */         {
/*  978:1061 */           expr = parseDotClass(expr, 0);
/*  979:     */         }
/*  980:1063 */         else if (t == 400)
/*  981:     */         {
/*  982:1064 */           String str = this.lex.getString();
/*  983:1065 */           expr = Expr.make(46, expr, new Member(str));
/*  984:     */         }
/*  985:     */         else
/*  986:     */         {
/*  987:1068 */           throw new CompileError("missing member name", this.lex);
/*  988:     */         }
/*  989:     */         break;
/*  990:     */       case 35: 
/*  991:1071 */         this.lex.get();
/*  992:1072 */         t = this.lex.get();
/*  993:1073 */         if (t != 400) {
/*  994:1074 */           throw new CompileError("missing static member name", this.lex);
/*  995:     */         }
/*  996:1076 */         String str = this.lex.getString();
/*  997:1077 */         expr = Expr.make(35, new Symbol(toClassName(expr)), new Member(str));
/*  998:     */       }
/*  999:     */     }
/* 1000:1081 */     return expr;
/* 1001:     */   }
/* 1002:     */   
/* 1003:     */   private ASTree parseDotClass(ASTree className, int dim)
/* 1004:     */     throws CompileError
/* 1005:     */   {
/* 1006:1093 */     String cname = toClassName(className);
/* 1007:1094 */     if (dim > 0)
/* 1008:     */     {
/* 1009:1095 */       StringBuffer sbuf = new StringBuffer();
/* 1010:1096 */       while (dim-- > 0) {
/* 1011:1097 */         sbuf.append('[');
/* 1012:     */       }
/* 1013:1099 */       sbuf.append('L').append(cname.replace('.', '/')).append(';');
/* 1014:1100 */       cname = sbuf.toString();
/* 1015:     */     }
/* 1016:1103 */     return Expr.make(46, new Symbol(cname), new Member("class"));
/* 1017:     */   }
/* 1018:     */   
/* 1019:     */   private ASTree parseDotClass(int builtinType, int dim)
/* 1020:     */     throws CompileError
/* 1021:     */   {
/* 1022:1113 */     if (dim > 0)
/* 1023:     */     {
/* 1024:1114 */       String cname = CodeGen.toJvmTypeName(builtinType, dim);
/* 1025:1115 */       return Expr.make(46, new Symbol(cname), new Member("class"));
/* 1026:     */     }
/* 1027:     */     String cname;
/* 1028:1119 */     switch (builtinType)
/* 1029:     */     {
/* 1030:     */     case 301: 
/* 1031:1121 */       cname = "java.lang.Boolean";
/* 1032:1122 */       break;
/* 1033:     */     case 303: 
/* 1034:1124 */       cname = "java.lang.Byte";
/* 1035:1125 */       break;
/* 1036:     */     case 306: 
/* 1037:1127 */       cname = "java.lang.Character";
/* 1038:1128 */       break;
/* 1039:     */     case 334: 
/* 1040:1130 */       cname = "java.lang.Short";
/* 1041:1131 */       break;
/* 1042:     */     case 324: 
/* 1043:1133 */       cname = "java.lang.Integer";
/* 1044:1134 */       break;
/* 1045:     */     case 326: 
/* 1046:1136 */       cname = "java.lang.Long";
/* 1047:1137 */       break;
/* 1048:     */     case 317: 
/* 1049:1139 */       cname = "java.lang.Float";
/* 1050:1140 */       break;
/* 1051:     */     case 312: 
/* 1052:1142 */       cname = "java.lang.Double";
/* 1053:1143 */       break;
/* 1054:     */     case 344: 
/* 1055:1145 */       cname = "java.lang.Void";
/* 1056:1146 */       break;
/* 1057:     */     default: 
/* 1058:1148 */       throw new CompileError("invalid builtin type: " + builtinType);
/* 1059:     */     }
/* 1060:1152 */     return Expr.make(35, new Symbol(cname), new Member("TYPE"));
/* 1061:     */   }
/* 1062:     */   
/* 1063:     */   private ASTree parseMethodCall(SymbolTable tbl, ASTree expr)
/* 1064:     */     throws CompileError
/* 1065:     */   {
/* 1066:1164 */     if ((expr instanceof Keyword))
/* 1067:     */     {
/* 1068:1165 */       int token = ((Keyword)expr).get();
/* 1069:1166 */       if ((token != 339) && (token != 336)) {
/* 1070:1167 */         throw new SyntaxError(this.lex);
/* 1071:     */       }
/* 1072:     */     }
/* 1073:1169 */     else if (!(expr instanceof Symbol))
/* 1074:     */     {
/* 1075:1171 */       if ((expr instanceof Expr))
/* 1076:     */       {
/* 1077:1172 */         int op = ((Expr)expr).getOperator();
/* 1078:1173 */         if ((op != 46) && (op != 35)) {
/* 1079:1174 */           throw new SyntaxError(this.lex);
/* 1080:     */         }
/* 1081:     */       }
/* 1082:     */     }
/* 1083:1177 */     return CallExpr.makeCall(expr, parseArgumentList(tbl));
/* 1084:     */   }
/* 1085:     */   
/* 1086:     */   private String toClassName(ASTree name)
/* 1087:     */     throws CompileError
/* 1088:     */   {
/* 1089:1183 */     StringBuffer sbuf = new StringBuffer();
/* 1090:1184 */     toClassName(name, sbuf);
/* 1091:1185 */     return sbuf.toString();
/* 1092:     */   }
/* 1093:     */   
/* 1094:     */   private void toClassName(ASTree name, StringBuffer sbuf)
/* 1095:     */     throws CompileError
/* 1096:     */   {
/* 1097:1191 */     if ((name instanceof Symbol))
/* 1098:     */     {
/* 1099:1192 */       sbuf.append(((Symbol)name).get());
/* 1100:1193 */       return;
/* 1101:     */     }
/* 1102:1195 */     if ((name instanceof Expr))
/* 1103:     */     {
/* 1104:1196 */       Expr expr = (Expr)name;
/* 1105:1197 */       if (expr.getOperator() == 46)
/* 1106:     */       {
/* 1107:1198 */         toClassName(expr.oprand1(), sbuf);
/* 1108:1199 */         sbuf.append('.');
/* 1109:1200 */         toClassName(expr.oprand2(), sbuf);
/* 1110:1201 */         return;
/* 1111:     */       }
/* 1112:     */     }
/* 1113:1205 */     throw new CompileError("bad static member access", this.lex);
/* 1114:     */   }
/* 1115:     */   
/* 1116:     */   private ASTree parsePrimaryExpr(SymbolTable tbl)
/* 1117:     */     throws CompileError
/* 1118:     */   {
/* 1119:     */     int t;
/* 1120:1224 */     switch (t = this.lex.get())
/* 1121:     */     {
/* 1122:     */     case 336: 
/* 1123:     */     case 339: 
/* 1124:     */     case 410: 
/* 1125:     */     case 411: 
/* 1126:     */     case 412: 
/* 1127:1230 */       return new Keyword(t);
/* 1128:     */     case 400: 
/* 1129:1232 */       String name = this.lex.getString();
/* 1130:1233 */       Declarator decl = tbl.lookup(name);
/* 1131:1234 */       if (decl == null) {
/* 1132:1235 */         return new Member(name);
/* 1133:     */       }
/* 1134:1237 */       return new Variable(name, decl);
/* 1135:     */     case 406: 
/* 1136:1239 */       return new StringL(this.lex.getString());
/* 1137:     */     case 328: 
/* 1138:1241 */       return parseNew(tbl);
/* 1139:     */     case 40: 
/* 1140:1243 */       ASTree expr = parseExpression(tbl);
/* 1141:1244 */       if (this.lex.get() == 41) {
/* 1142:1245 */         return expr;
/* 1143:     */       }
/* 1144:1247 */       throw new CompileError(") is missing", this.lex);
/* 1145:     */     }
/* 1146:1249 */     if ((isBuiltinType(t)) || (t == 344))
/* 1147:     */     {
/* 1148:1250 */       int dim = parseArrayDimension();
/* 1149:1251 */       if ((this.lex.get() == 46) && (this.lex.get() == 307)) {
/* 1150:1252 */         return parseDotClass(t, dim);
/* 1151:     */       }
/* 1152:     */     }
/* 1153:1255 */     throw new SyntaxError(this.lex);
/* 1154:     */   }
/* 1155:     */   
/* 1156:     */   private NewExpr parseNew(SymbolTable tbl)
/* 1157:     */     throws CompileError
/* 1158:     */   {
/* 1159:1264 */     ArrayInit init = null;
/* 1160:1265 */     int t = this.lex.lookAhead();
/* 1161:1266 */     if (isBuiltinType(t))
/* 1162:     */     {
/* 1163:1267 */       this.lex.get();
/* 1164:1268 */       ASTList size = parseArraySize(tbl);
/* 1165:1269 */       if (this.lex.lookAhead() == 123) {
/* 1166:1270 */         init = parseArrayInitializer(tbl);
/* 1167:     */       }
/* 1168:1272 */       return new NewExpr(t, size, init);
/* 1169:     */     }
/* 1170:1274 */     if (t == 400)
/* 1171:     */     {
/* 1172:1275 */       ASTList name = parseClassType(tbl);
/* 1173:1276 */       t = this.lex.lookAhead();
/* 1174:1277 */       if (t == 40)
/* 1175:     */       {
/* 1176:1278 */         ASTList args = parseArgumentList(tbl);
/* 1177:1279 */         return new NewExpr(name, args);
/* 1178:     */       }
/* 1179:1281 */       if (t == 91)
/* 1180:     */       {
/* 1181:1282 */         ASTList size = parseArraySize(tbl);
/* 1182:1283 */         if (this.lex.lookAhead() == 123) {
/* 1183:1284 */           init = parseArrayInitializer(tbl);
/* 1184:     */         }
/* 1185:1286 */         return NewExpr.makeObjectArray(name, size, init);
/* 1186:     */       }
/* 1187:     */     }
/* 1188:1290 */     throw new SyntaxError(this.lex);
/* 1189:     */   }
/* 1190:     */   
/* 1191:     */   private ASTList parseArraySize(SymbolTable tbl)
/* 1192:     */     throws CompileError
/* 1193:     */   {
/* 1194:1296 */     ASTList list = null;
/* 1195:1297 */     while (this.lex.lookAhead() == 91) {
/* 1196:1298 */       list = ASTList.append(list, parseArrayIndex(tbl));
/* 1197:     */     }
/* 1198:1300 */     return list;
/* 1199:     */   }
/* 1200:     */   
/* 1201:     */   private ASTree parseArrayIndex(SymbolTable tbl)
/* 1202:     */     throws CompileError
/* 1203:     */   {
/* 1204:1306 */     this.lex.get();
/* 1205:1307 */     if (this.lex.lookAhead() == 93)
/* 1206:     */     {
/* 1207:1308 */       this.lex.get();
/* 1208:1309 */       return null;
/* 1209:     */     }
/* 1210:1312 */     ASTree index = parseExpression(tbl);
/* 1211:1313 */     if (this.lex.get() != 93) {
/* 1212:1314 */       throw new CompileError("] is missing", this.lex);
/* 1213:     */     }
/* 1214:1316 */     return index;
/* 1215:     */   }
/* 1216:     */   
/* 1217:     */   private ASTList parseArgumentList(SymbolTable tbl)
/* 1218:     */     throws CompileError
/* 1219:     */   {
/* 1220:1323 */     if (this.lex.get() != 40) {
/* 1221:1324 */       throw new CompileError("( is missing", this.lex);
/* 1222:     */     }
/* 1223:1326 */     ASTList list = null;
/* 1224:1327 */     if (this.lex.lookAhead() != 41) {
/* 1225:     */       for (;;)
/* 1226:     */       {
/* 1227:1329 */         list = ASTList.append(list, parseExpression(tbl));
/* 1228:1330 */         if (this.lex.lookAhead() != 44) {
/* 1229:     */           break;
/* 1230:     */         }
/* 1231:1331 */         this.lex.get();
/* 1232:     */       }
/* 1233:     */     }
/* 1234:1336 */     if (this.lex.get() != 41) {
/* 1235:1337 */       throw new CompileError(") is missing", this.lex);
/* 1236:     */     }
/* 1237:1339 */     return list;
/* 1238:     */   }
/* 1239:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.compiler.Parser
 * JD-Core Version:    0.7.0.1
 */