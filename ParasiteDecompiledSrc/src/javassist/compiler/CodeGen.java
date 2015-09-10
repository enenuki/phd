/*    1:     */ package javassist.compiler;
/*    2:     */ 
/*    3:     */ import java.util.ArrayList;
/*    4:     */ import java.util.Arrays;
/*    5:     */ import javassist.bytecode.Bytecode;
/*    6:     */ import javassist.bytecode.Opcode;
/*    7:     */ import javassist.compiler.ast.ASTList;
/*    8:     */ import javassist.compiler.ast.ASTree;
/*    9:     */ import javassist.compiler.ast.ArrayInit;
/*   10:     */ import javassist.compiler.ast.AssignExpr;
/*   11:     */ import javassist.compiler.ast.BinExpr;
/*   12:     */ import javassist.compiler.ast.CallExpr;
/*   13:     */ import javassist.compiler.ast.CastExpr;
/*   14:     */ import javassist.compiler.ast.CondExpr;
/*   15:     */ import javassist.compiler.ast.Declarator;
/*   16:     */ import javassist.compiler.ast.DoubleConst;
/*   17:     */ import javassist.compiler.ast.Expr;
/*   18:     */ import javassist.compiler.ast.FieldDecl;
/*   19:     */ import javassist.compiler.ast.InstanceOfExpr;
/*   20:     */ import javassist.compiler.ast.IntConst;
/*   21:     */ import javassist.compiler.ast.Keyword;
/*   22:     */ import javassist.compiler.ast.Member;
/*   23:     */ import javassist.compiler.ast.MethodDecl;
/*   24:     */ import javassist.compiler.ast.NewExpr;
/*   25:     */ import javassist.compiler.ast.Pair;
/*   26:     */ import javassist.compiler.ast.Stmnt;
/*   27:     */ import javassist.compiler.ast.StringL;
/*   28:     */ import javassist.compiler.ast.Symbol;
/*   29:     */ import javassist.compiler.ast.Variable;
/*   30:     */ import javassist.compiler.ast.Visitor;
/*   31:     */ 
/*   32:     */ public abstract class CodeGen
/*   33:     */   extends Visitor
/*   34:     */   implements Opcode, TokenId
/*   35:     */ {
/*   36:     */   static final String javaLangObject = "java.lang.Object";
/*   37:     */   static final String jvmJavaLangObject = "java/lang/Object";
/*   38:     */   static final String javaLangString = "java.lang.String";
/*   39:     */   static final String jvmJavaLangString = "java/lang/String";
/*   40:     */   protected Bytecode bytecode;
/*   41:     */   private int tempVar;
/*   42:     */   TypeChecker typeChecker;
/*   43:     */   protected boolean hasReturned;
/*   44:     */   public boolean inStaticMethod;
/*   45:     */   protected ArrayList breakList;
/*   46:     */   protected ArrayList continueList;
/*   47:     */   protected ReturnHook returnHooks;
/*   48:     */   protected int exprType;
/*   49:     */   protected int arrayDim;
/*   50:     */   protected String className;
/*   51:     */   
/*   52:     */   protected static abstract class ReturnHook
/*   53:     */   {
/*   54:     */     ReturnHook next;
/*   55:     */     
/*   56:     */     protected abstract boolean doit(Bytecode paramBytecode, int paramInt);
/*   57:     */     
/*   58:     */     protected ReturnHook(CodeGen gen)
/*   59:     */     {
/*   60:  64 */       this.next = gen.returnHooks;
/*   61:  65 */       gen.returnHooks = this;
/*   62:     */     }
/*   63:     */     
/*   64:     */     protected void remove(CodeGen gen)
/*   65:     */     {
/*   66:  69 */       gen.returnHooks = this.next;
/*   67:     */     }
/*   68:     */   }
/*   69:     */   
/*   70:     */   public CodeGen(Bytecode b)
/*   71:     */   {
/*   72:  83 */     this.bytecode = b;
/*   73:  84 */     this.tempVar = -1;
/*   74:  85 */     this.typeChecker = null;
/*   75:  86 */     this.hasReturned = false;
/*   76:  87 */     this.inStaticMethod = false;
/*   77:  88 */     this.breakList = null;
/*   78:  89 */     this.continueList = null;
/*   79:  90 */     this.returnHooks = null;
/*   80:     */   }
/*   81:     */   
/*   82:     */   public void setTypeChecker(TypeChecker checker)
/*   83:     */   {
/*   84:  94 */     this.typeChecker = checker;
/*   85:     */   }
/*   86:     */   
/*   87:     */   protected static void fatal()
/*   88:     */     throws CompileError
/*   89:     */   {
/*   90:  98 */     throw new CompileError("fatal");
/*   91:     */   }
/*   92:     */   
/*   93:     */   public static boolean is2word(int type, int dim)
/*   94:     */   {
/*   95: 102 */     return (dim == 0) && ((type == 312) || (type == 326));
/*   96:     */   }
/*   97:     */   
/*   98:     */   public int getMaxLocals()
/*   99:     */   {
/*  100: 105 */     return this.bytecode.getMaxLocals();
/*  101:     */   }
/*  102:     */   
/*  103:     */   public void setMaxLocals(int n)
/*  104:     */   {
/*  105: 108 */     this.bytecode.setMaxLocals(n);
/*  106:     */   }
/*  107:     */   
/*  108:     */   protected void incMaxLocals(int size)
/*  109:     */   {
/*  110: 112 */     this.bytecode.incMaxLocals(size);
/*  111:     */   }
/*  112:     */   
/*  113:     */   protected int getTempVar()
/*  114:     */   {
/*  115: 120 */     if (this.tempVar < 0)
/*  116:     */     {
/*  117: 121 */       this.tempVar = getMaxLocals();
/*  118: 122 */       incMaxLocals(2);
/*  119:     */     }
/*  120: 125 */     return this.tempVar;
/*  121:     */   }
/*  122:     */   
/*  123:     */   protected int getLocalVar(Declarator d)
/*  124:     */   {
/*  125: 129 */     int v = d.getLocalVar();
/*  126: 130 */     if (v < 0)
/*  127:     */     {
/*  128: 131 */       v = getMaxLocals();
/*  129: 132 */       d.setLocalVar(v);
/*  130: 133 */       incMaxLocals(1);
/*  131:     */     }
/*  132: 136 */     return v;
/*  133:     */   }
/*  134:     */   
/*  135:     */   protected abstract String getThisName();
/*  136:     */   
/*  137:     */   protected abstract String getSuperName()
/*  138:     */     throws CompileError;
/*  139:     */   
/*  140:     */   protected abstract String resolveClassName(ASTList paramASTList)
/*  141:     */     throws CompileError;
/*  142:     */   
/*  143:     */   protected abstract String resolveClassName(String paramString)
/*  144:     */     throws CompileError;
/*  145:     */   
/*  146:     */   protected static String toJvmArrayName(String name, int dim)
/*  147:     */   {
/*  148: 168 */     if (name == null) {
/*  149: 169 */       return null;
/*  150:     */     }
/*  151: 171 */     if (dim == 0) {
/*  152: 172 */       return name;
/*  153:     */     }
/*  154: 174 */     StringBuffer sbuf = new StringBuffer();
/*  155: 175 */     int d = dim;
/*  156: 176 */     while (d-- > 0) {
/*  157: 177 */       sbuf.append('[');
/*  158:     */     }
/*  159: 179 */     sbuf.append('L');
/*  160: 180 */     sbuf.append(name);
/*  161: 181 */     sbuf.append(';');
/*  162:     */     
/*  163: 183 */     return sbuf.toString();
/*  164:     */   }
/*  165:     */   
/*  166:     */   protected static String toJvmTypeName(int type, int dim)
/*  167:     */   {
/*  168: 188 */     char c = 'I';
/*  169: 189 */     switch (type)
/*  170:     */     {
/*  171:     */     case 301: 
/*  172: 191 */       c = 'Z';
/*  173: 192 */       break;
/*  174:     */     case 303: 
/*  175: 194 */       c = 'B';
/*  176: 195 */       break;
/*  177:     */     case 306: 
/*  178: 197 */       c = 'C';
/*  179: 198 */       break;
/*  180:     */     case 334: 
/*  181: 200 */       c = 'S';
/*  182: 201 */       break;
/*  183:     */     case 324: 
/*  184: 203 */       c = 'I';
/*  185: 204 */       break;
/*  186:     */     case 326: 
/*  187: 206 */       c = 'J';
/*  188: 207 */       break;
/*  189:     */     case 317: 
/*  190: 209 */       c = 'F';
/*  191: 210 */       break;
/*  192:     */     case 312: 
/*  193: 212 */       c = 'D';
/*  194: 213 */       break;
/*  195:     */     case 344: 
/*  196: 215 */       c = 'V';
/*  197:     */     }
/*  198: 219 */     StringBuffer sbuf = new StringBuffer();
/*  199: 220 */     while (dim-- > 0) {
/*  200: 221 */       sbuf.append('[');
/*  201:     */     }
/*  202: 223 */     sbuf.append(c);
/*  203: 224 */     return sbuf.toString();
/*  204:     */   }
/*  205:     */   
/*  206:     */   public void compileExpr(ASTree expr)
/*  207:     */     throws CompileError
/*  208:     */   {
/*  209: 228 */     doTypeCheck(expr);
/*  210: 229 */     expr.accept(this);
/*  211:     */   }
/*  212:     */   
/*  213:     */   public boolean compileBooleanExpr(boolean branchIf, ASTree expr)
/*  214:     */     throws CompileError
/*  215:     */   {
/*  216: 235 */     doTypeCheck(expr);
/*  217: 236 */     return booleanExpr(branchIf, expr);
/*  218:     */   }
/*  219:     */   
/*  220:     */   public void doTypeCheck(ASTree expr)
/*  221:     */     throws CompileError
/*  222:     */   {
/*  223: 240 */     if (this.typeChecker != null) {
/*  224: 241 */       expr.accept(this.typeChecker);
/*  225:     */     }
/*  226:     */   }
/*  227:     */   
/*  228:     */   public void atASTList(ASTList n)
/*  229:     */     throws CompileError
/*  230:     */   {}
/*  231:     */   
/*  232:     */   public void atPair(Pair n)
/*  233:     */     throws CompileError
/*  234:     */   {}
/*  235:     */   
/*  236:     */   public void atSymbol(Symbol n)
/*  237:     */     throws CompileError
/*  238:     */   {}
/*  239:     */   
/*  240:     */   public void atFieldDecl(FieldDecl field)
/*  241:     */     throws CompileError
/*  242:     */   {
/*  243: 251 */     field.getInit().accept(this);
/*  244:     */   }
/*  245:     */   
/*  246:     */   public void atMethodDecl(MethodDecl method)
/*  247:     */     throws CompileError
/*  248:     */   {
/*  249: 255 */     ASTList mods = method.getModifiers();
/*  250: 256 */     setMaxLocals(1);
/*  251: 257 */     while (mods != null)
/*  252:     */     {
/*  253: 258 */       Keyword k = (Keyword)mods.head();
/*  254: 259 */       mods = mods.tail();
/*  255: 260 */       if (k.get() == 335)
/*  256:     */       {
/*  257: 261 */         setMaxLocals(0);
/*  258: 262 */         this.inStaticMethod = true;
/*  259:     */       }
/*  260:     */     }
/*  261: 266 */     ASTList params = method.getParams();
/*  262: 267 */     while (params != null)
/*  263:     */     {
/*  264: 268 */       atDeclarator((Declarator)params.head());
/*  265: 269 */       params = params.tail();
/*  266:     */     }
/*  267: 272 */     Stmnt s = method.getBody();
/*  268: 273 */     atMethodBody(s, method.isConstructor(), method.getReturn().getType() == 344);
/*  269:     */   }
/*  270:     */   
/*  271:     */   public void atMethodBody(Stmnt s, boolean isCons, boolean isVoid)
/*  272:     */     throws CompileError
/*  273:     */   {
/*  274: 284 */     if (s == null) {
/*  275: 285 */       return;
/*  276:     */     }
/*  277: 287 */     if ((isCons) && (needsSuperCall(s))) {
/*  278: 288 */       insertDefaultSuperCall();
/*  279:     */     }
/*  280: 290 */     this.hasReturned = false;
/*  281: 291 */     s.accept(this);
/*  282: 292 */     if (!this.hasReturned) {
/*  283: 293 */       if (isVoid)
/*  284:     */       {
/*  285: 294 */         this.bytecode.addOpcode(177);
/*  286: 295 */         this.hasReturned = true;
/*  287:     */       }
/*  288:     */       else
/*  289:     */       {
/*  290: 298 */         throw new CompileError("no return statement");
/*  291:     */       }
/*  292:     */     }
/*  293:     */   }
/*  294:     */   
/*  295:     */   private boolean needsSuperCall(Stmnt body)
/*  296:     */     throws CompileError
/*  297:     */   {
/*  298: 302 */     if (body.getOperator() == 66) {
/*  299: 303 */       body = (Stmnt)body.head();
/*  300:     */     }
/*  301: 305 */     if ((body != null) && (body.getOperator() == 69))
/*  302:     */     {
/*  303: 306 */       ASTree expr = body.head();
/*  304: 307 */       if ((expr != null) && ((expr instanceof Expr)) && (((Expr)expr).getOperator() == 67))
/*  305:     */       {
/*  306: 309 */         ASTree target = ((Expr)expr).head();
/*  307: 310 */         if ((target instanceof Keyword))
/*  308:     */         {
/*  309: 311 */           int token = ((Keyword)target).get();
/*  310: 312 */           return (token != 339) && (token != 336);
/*  311:     */         }
/*  312:     */       }
/*  313:     */     }
/*  314: 317 */     return true;
/*  315:     */   }
/*  316:     */   
/*  317:     */   protected abstract void insertDefaultSuperCall()
/*  318:     */     throws CompileError;
/*  319:     */   
/*  320:     */   public void atStmnt(Stmnt st)
/*  321:     */     throws CompileError
/*  322:     */   {
/*  323: 323 */     if (st == null) {
/*  324: 324 */       return;
/*  325:     */     }
/*  326: 326 */     int op = st.getOperator();
/*  327: 327 */     if (op == 69)
/*  328:     */     {
/*  329: 328 */       ASTree expr = st.getLeft();
/*  330: 329 */       doTypeCheck(expr);
/*  331: 330 */       if ((expr instanceof AssignExpr))
/*  332:     */       {
/*  333: 331 */         atAssignExpr((AssignExpr)expr, false);
/*  334:     */       }
/*  335: 332 */       else if (isPlusPlusExpr(expr))
/*  336:     */       {
/*  337: 333 */         Expr e = (Expr)expr;
/*  338: 334 */         atPlusPlus(e.getOperator(), e.oprand1(), e, false);
/*  339:     */       }
/*  340:     */       else
/*  341:     */       {
/*  342: 337 */         expr.accept(this);
/*  343: 338 */         if (is2word(this.exprType, this.arrayDim)) {
/*  344: 339 */           this.bytecode.addOpcode(88);
/*  345: 340 */         } else if (this.exprType != 344) {
/*  346: 341 */           this.bytecode.addOpcode(87);
/*  347:     */         }
/*  348:     */       }
/*  349:     */     }
/*  350: 344 */     else if ((op == 68) || (op == 66))
/*  351:     */     {
/*  352: 345 */       ASTList list = st;
/*  353: 346 */       while (list != null)
/*  354:     */       {
/*  355: 347 */         ASTree h = list.head();
/*  356: 348 */         list = list.tail();
/*  357: 349 */         if (h != null) {
/*  358: 350 */           h.accept(this);
/*  359:     */         }
/*  360:     */       }
/*  361:     */     }
/*  362: 353 */     else if (op == 320)
/*  363:     */     {
/*  364: 354 */       atIfStmnt(st);
/*  365:     */     }
/*  366: 355 */     else if ((op == 346) || (op == 311))
/*  367:     */     {
/*  368: 356 */       atWhileStmnt(st, op == 346);
/*  369:     */     }
/*  370: 357 */     else if (op == 318)
/*  371:     */     {
/*  372: 358 */       atForStmnt(st);
/*  373:     */     }
/*  374: 359 */     else if ((op == 302) || (op == 309))
/*  375:     */     {
/*  376: 360 */       atBreakStmnt(st, op == 302);
/*  377:     */     }
/*  378: 361 */     else if (op == 333)
/*  379:     */     {
/*  380: 362 */       atReturnStmnt(st);
/*  381:     */     }
/*  382: 363 */     else if (op == 340)
/*  383:     */     {
/*  384: 364 */       atThrowStmnt(st);
/*  385:     */     }
/*  386: 365 */     else if (op == 343)
/*  387:     */     {
/*  388: 366 */       atTryStmnt(st);
/*  389:     */     }
/*  390: 367 */     else if (op == 337)
/*  391:     */     {
/*  392: 368 */       atSwitchStmnt(st);
/*  393:     */     }
/*  394: 369 */     else if (op == 338)
/*  395:     */     {
/*  396: 370 */       atSyncStmnt(st);
/*  397:     */     }
/*  398:     */     else
/*  399:     */     {
/*  400: 373 */       this.hasReturned = false;
/*  401: 374 */       throw new CompileError("sorry, not supported statement: TokenId " + op);
/*  402:     */     }
/*  403:     */   }
/*  404:     */   
/*  405:     */   private void atIfStmnt(Stmnt st)
/*  406:     */     throws CompileError
/*  407:     */   {
/*  408: 380 */     ASTree expr = st.head();
/*  409: 381 */     Stmnt thenp = (Stmnt)st.tail().head();
/*  410: 382 */     Stmnt elsep = (Stmnt)st.tail().tail().head();
/*  411: 383 */     compileBooleanExpr(false, expr);
/*  412: 384 */     int pc = this.bytecode.currentPc();
/*  413: 385 */     int pc2 = 0;
/*  414: 386 */     this.bytecode.addIndex(0);
/*  415:     */     
/*  416: 388 */     this.hasReturned = false;
/*  417: 389 */     if (thenp != null) {
/*  418: 390 */       thenp.accept(this);
/*  419:     */     }
/*  420: 392 */     boolean thenHasReturned = this.hasReturned;
/*  421: 393 */     this.hasReturned = false;
/*  422: 395 */     if ((elsep != null) && (!thenHasReturned))
/*  423:     */     {
/*  424: 396 */       this.bytecode.addOpcode(167);
/*  425: 397 */       pc2 = this.bytecode.currentPc();
/*  426: 398 */       this.bytecode.addIndex(0);
/*  427:     */     }
/*  428: 401 */     this.bytecode.write16bit(pc, this.bytecode.currentPc() - pc + 1);
/*  429: 403 */     if (elsep != null)
/*  430:     */     {
/*  431: 404 */       elsep.accept(this);
/*  432: 405 */       if (!thenHasReturned) {
/*  433: 406 */         this.bytecode.write16bit(pc2, this.bytecode.currentPc() - pc2 + 1);
/*  434:     */       }
/*  435: 408 */       this.hasReturned = ((thenHasReturned) && (this.hasReturned));
/*  436:     */     }
/*  437:     */   }
/*  438:     */   
/*  439:     */   private void atWhileStmnt(Stmnt st, boolean notDo)
/*  440:     */     throws CompileError
/*  441:     */   {
/*  442: 413 */     ArrayList prevBreakList = this.breakList;
/*  443: 414 */     ArrayList prevContList = this.continueList;
/*  444: 415 */     this.breakList = new ArrayList();
/*  445: 416 */     this.continueList = new ArrayList();
/*  446:     */     
/*  447: 418 */     ASTree expr = st.head();
/*  448: 419 */     Stmnt body = (Stmnt)st.tail();
/*  449:     */     
/*  450: 421 */     int pc = 0;
/*  451: 422 */     if (notDo)
/*  452:     */     {
/*  453: 423 */       this.bytecode.addOpcode(167);
/*  454: 424 */       pc = this.bytecode.currentPc();
/*  455: 425 */       this.bytecode.addIndex(0);
/*  456:     */     }
/*  457: 428 */     int pc2 = this.bytecode.currentPc();
/*  458: 429 */     if (body != null) {
/*  459: 430 */       body.accept(this);
/*  460:     */     }
/*  461: 432 */     int pc3 = this.bytecode.currentPc();
/*  462: 433 */     if (notDo) {
/*  463: 434 */       this.bytecode.write16bit(pc, pc3 - pc + 1);
/*  464:     */     }
/*  465: 436 */     boolean alwaysBranch = compileBooleanExpr(true, expr);
/*  466: 437 */     this.bytecode.addIndex(pc2 - this.bytecode.currentPc() + 1);
/*  467:     */     
/*  468: 439 */     patchGoto(this.breakList, this.bytecode.currentPc());
/*  469: 440 */     patchGoto(this.continueList, pc3);
/*  470: 441 */     this.continueList = prevContList;
/*  471: 442 */     this.breakList = prevBreakList;
/*  472: 443 */     this.hasReturned = alwaysBranch;
/*  473:     */   }
/*  474:     */   
/*  475:     */   protected void patchGoto(ArrayList list, int targetPc)
/*  476:     */   {
/*  477: 447 */     int n = list.size();
/*  478: 448 */     for (int i = 0; i < n; i++)
/*  479:     */     {
/*  480: 449 */       int pc = ((Integer)list.get(i)).intValue();
/*  481: 450 */       this.bytecode.write16bit(pc, targetPc - pc + 1);
/*  482:     */     }
/*  483:     */   }
/*  484:     */   
/*  485:     */   private void atForStmnt(Stmnt st)
/*  486:     */     throws CompileError
/*  487:     */   {
/*  488: 455 */     ArrayList prevBreakList = this.breakList;
/*  489: 456 */     ArrayList prevContList = this.continueList;
/*  490: 457 */     this.breakList = new ArrayList();
/*  491: 458 */     this.continueList = new ArrayList();
/*  492:     */     
/*  493: 460 */     Stmnt init = (Stmnt)st.head();
/*  494: 461 */     ASTList p = st.tail();
/*  495: 462 */     ASTree expr = p.head();
/*  496: 463 */     p = p.tail();
/*  497: 464 */     Stmnt update = (Stmnt)p.head();
/*  498: 465 */     Stmnt body = (Stmnt)p.tail();
/*  499: 467 */     if (init != null) {
/*  500: 468 */       init.accept(this);
/*  501:     */     }
/*  502: 470 */     int pc = this.bytecode.currentPc();
/*  503: 471 */     int pc2 = 0;
/*  504: 472 */     if (expr != null)
/*  505:     */     {
/*  506: 473 */       compileBooleanExpr(false, expr);
/*  507: 474 */       pc2 = this.bytecode.currentPc();
/*  508: 475 */       this.bytecode.addIndex(0);
/*  509:     */     }
/*  510: 478 */     if (body != null) {
/*  511: 479 */       body.accept(this);
/*  512:     */     }
/*  513: 481 */     int pc3 = this.bytecode.currentPc();
/*  514: 482 */     if (update != null) {
/*  515: 483 */       update.accept(this);
/*  516:     */     }
/*  517: 485 */     this.bytecode.addOpcode(167);
/*  518: 486 */     this.bytecode.addIndex(pc - this.bytecode.currentPc() + 1);
/*  519:     */     
/*  520: 488 */     int pc4 = this.bytecode.currentPc();
/*  521: 489 */     if (expr != null) {
/*  522: 490 */       this.bytecode.write16bit(pc2, pc4 - pc2 + 1);
/*  523:     */     }
/*  524: 492 */     patchGoto(this.breakList, pc4);
/*  525: 493 */     patchGoto(this.continueList, pc3);
/*  526: 494 */     this.continueList = prevContList;
/*  527: 495 */     this.breakList = prevBreakList;
/*  528: 496 */     this.hasReturned = false;
/*  529:     */   }
/*  530:     */   
/*  531:     */   private void atSwitchStmnt(Stmnt st)
/*  532:     */     throws CompileError
/*  533:     */   {
/*  534: 500 */     compileExpr(st.head());
/*  535:     */     
/*  536: 502 */     ArrayList prevBreakList = this.breakList;
/*  537: 503 */     this.breakList = new ArrayList();
/*  538: 504 */     int opcodePc = this.bytecode.currentPc();
/*  539: 505 */     this.bytecode.addOpcode(171);
/*  540: 506 */     int npads = 3 - (opcodePc & 0x3);
/*  541: 507 */     while (npads-- > 0) {
/*  542: 508 */       this.bytecode.add(0);
/*  543:     */     }
/*  544: 510 */     Stmnt body = (Stmnt)st.tail();
/*  545: 511 */     int npairs = 0;
/*  546: 512 */     for (ASTList list = body; list != null; list = list.tail()) {
/*  547: 513 */       if (((Stmnt)list.head()).getOperator() == 304) {
/*  548: 514 */         npairs++;
/*  549:     */       }
/*  550:     */     }
/*  551: 517 */     int opcodePc2 = this.bytecode.currentPc();
/*  552: 518 */     this.bytecode.addGap(4);
/*  553: 519 */     this.bytecode.add32bit(npairs);
/*  554: 520 */     this.bytecode.addGap(npairs * 8);
/*  555:     */     
/*  556: 522 */     long[] pairs = new long[npairs];
/*  557: 523 */     int ipairs = 0;
/*  558: 524 */     int defaultPc = -1;
/*  559: 525 */     for (ASTList list = body; list != null; list = list.tail())
/*  560:     */     {
/*  561: 526 */       Stmnt label = (Stmnt)list.head();
/*  562: 527 */       int op = label.getOperator();
/*  563: 528 */       if (op == 310) {
/*  564: 529 */         defaultPc = this.bytecode.currentPc();
/*  565: 530 */       } else if (op != 304) {
/*  566: 531 */         fatal();
/*  567:     */       } else {
/*  568: 533 */         pairs[(ipairs++)] = ((computeLabel(label.head()) << 32) + (this.bytecode.currentPc() - opcodePc & 0xFFFFFFFF));
/*  569:     */       }
/*  570: 538 */       this.hasReturned = false;
/*  571: 539 */       ((Stmnt)label.tail()).accept(this);
/*  572:     */     }
/*  573: 542 */     Arrays.sort(pairs);
/*  574: 543 */     int pc = opcodePc2 + 8;
/*  575: 544 */     for (int i = 0; i < npairs; i++)
/*  576:     */     {
/*  577: 545 */       this.bytecode.write32bit(pc, (int)(pairs[i] >>> 32));
/*  578: 546 */       this.bytecode.write32bit(pc + 4, (int)pairs[i]);
/*  579: 547 */       pc += 8;
/*  580:     */     }
/*  581: 550 */     if ((defaultPc < 0) || (this.breakList.size() > 0)) {
/*  582: 551 */       this.hasReturned = false;
/*  583:     */     }
/*  584: 553 */     int endPc = this.bytecode.currentPc();
/*  585: 554 */     if (defaultPc < 0) {
/*  586: 555 */       defaultPc = endPc;
/*  587:     */     }
/*  588: 557 */     this.bytecode.write32bit(opcodePc2, defaultPc - opcodePc);
/*  589:     */     
/*  590: 559 */     patchGoto(this.breakList, endPc);
/*  591: 560 */     this.breakList = prevBreakList;
/*  592:     */   }
/*  593:     */   
/*  594:     */   private int computeLabel(ASTree expr)
/*  595:     */     throws CompileError
/*  596:     */   {
/*  597: 564 */     doTypeCheck(expr);
/*  598: 565 */     expr = TypeChecker.stripPlusExpr(expr);
/*  599: 566 */     if ((expr instanceof IntConst)) {
/*  600: 567 */       return (int)((IntConst)expr).get();
/*  601:     */     }
/*  602: 569 */     throw new CompileError("bad case label");
/*  603:     */   }
/*  604:     */   
/*  605:     */   private void atBreakStmnt(Stmnt st, boolean notCont)
/*  606:     */     throws CompileError
/*  607:     */   {
/*  608: 575 */     if (st.head() != null) {
/*  609: 576 */       throw new CompileError("sorry, not support labeled break or continue");
/*  610:     */     }
/*  611: 579 */     this.bytecode.addOpcode(167);
/*  612: 580 */     Integer pc = new Integer(this.bytecode.currentPc());
/*  613: 581 */     this.bytecode.addIndex(0);
/*  614: 582 */     if (notCont) {
/*  615: 583 */       this.breakList.add(pc);
/*  616:     */     } else {
/*  617: 585 */       this.continueList.add(pc);
/*  618:     */     }
/*  619:     */   }
/*  620:     */   
/*  621:     */   protected void atReturnStmnt(Stmnt st)
/*  622:     */     throws CompileError
/*  623:     */   {
/*  624: 589 */     atReturnStmnt2(st.getLeft());
/*  625:     */   }
/*  626:     */   
/*  627:     */   protected final void atReturnStmnt2(ASTree result)
/*  628:     */     throws CompileError
/*  629:     */   {
/*  630:     */     int op;
/*  631:     */     int op;
/*  632: 594 */     if (result == null)
/*  633:     */     {
/*  634: 595 */       op = 177;
/*  635:     */     }
/*  636:     */     else
/*  637:     */     {
/*  638: 597 */       compileExpr(result);
/*  639:     */       int op;
/*  640: 598 */       if (this.arrayDim > 0)
/*  641:     */       {
/*  642: 599 */         op = 176;
/*  643:     */       }
/*  644:     */       else
/*  645:     */       {
/*  646: 601 */         int type = this.exprType;
/*  647:     */         int op;
/*  648: 602 */         if (type == 312)
/*  649:     */         {
/*  650: 603 */           op = 175;
/*  651:     */         }
/*  652:     */         else
/*  653:     */         {
/*  654:     */           int op;
/*  655: 604 */           if (type == 317)
/*  656:     */           {
/*  657: 605 */             op = 174;
/*  658:     */           }
/*  659:     */           else
/*  660:     */           {
/*  661:     */             int op;
/*  662: 606 */             if (type == 326)
/*  663:     */             {
/*  664: 607 */               op = 173;
/*  665:     */             }
/*  666:     */             else
/*  667:     */             {
/*  668:     */               int op;
/*  669: 608 */               if (isRefType(type)) {
/*  670: 609 */                 op = 176;
/*  671:     */               } else {
/*  672: 611 */                 op = 172;
/*  673:     */               }
/*  674:     */             }
/*  675:     */           }
/*  676:     */         }
/*  677:     */       }
/*  678:     */     }
/*  679: 615 */     for (ReturnHook har = this.returnHooks; har != null; har = har.next) {
/*  680: 616 */       if (har.doit(this.bytecode, op))
/*  681:     */       {
/*  682: 617 */         this.hasReturned = true;
/*  683: 618 */         return;
/*  684:     */       }
/*  685:     */     }
/*  686: 621 */     this.bytecode.addOpcode(op);
/*  687: 622 */     this.hasReturned = true;
/*  688:     */   }
/*  689:     */   
/*  690:     */   private void atThrowStmnt(Stmnt st)
/*  691:     */     throws CompileError
/*  692:     */   {
/*  693: 626 */     ASTree e = st.getLeft();
/*  694: 627 */     compileExpr(e);
/*  695: 628 */     if ((this.exprType != 307) || (this.arrayDim > 0)) {
/*  696: 629 */       throw new CompileError("bad throw statement");
/*  697:     */     }
/*  698: 631 */     this.bytecode.addOpcode(191);
/*  699: 632 */     this.hasReturned = true;
/*  700:     */   }
/*  701:     */   
/*  702:     */   protected void atTryStmnt(Stmnt st)
/*  703:     */     throws CompileError
/*  704:     */   {
/*  705: 638 */     this.hasReturned = false;
/*  706:     */   }
/*  707:     */   
/*  708:     */   private void atSyncStmnt(Stmnt st)
/*  709:     */     throws CompileError
/*  710:     */   {
/*  711: 642 */     int nbreaks = getListSize(this.breakList);
/*  712: 643 */     int ncontinues = getListSize(this.continueList);
/*  713:     */     
/*  714: 645 */     compileExpr(st.head());
/*  715: 646 */     if ((this.exprType != 307) && (this.arrayDim == 0)) {
/*  716: 647 */       throw new CompileError("bad type expr for synchronized block");
/*  717:     */     }
/*  718: 649 */     Bytecode bc = this.bytecode;
/*  719: 650 */     final int var = bc.getMaxLocals();
/*  720: 651 */     bc.incMaxLocals(1);
/*  721: 652 */     bc.addOpcode(89);
/*  722: 653 */     bc.addAstore(var);
/*  723: 654 */     bc.addOpcode(194);
/*  724:     */     
/*  725: 656 */     ReturnHook rh = new ReturnHook(this)
/*  726:     */     {
/*  727:     */       private final int val$var;
/*  728:     */       
/*  729:     */       protected boolean doit(Bytecode b, int opcode)
/*  730:     */       {
/*  731: 658 */         b.addAload(var);
/*  732: 659 */         b.addOpcode(195);
/*  733: 660 */         return false;
/*  734:     */       }
/*  735: 663 */     };
/*  736: 664 */     int pc = bc.currentPc();
/*  737: 665 */     Stmnt body = (Stmnt)st.tail();
/*  738: 666 */     if (body != null) {
/*  739: 667 */       body.accept(this);
/*  740:     */     }
/*  741: 669 */     int pc2 = bc.currentPc();
/*  742: 670 */     int pc3 = 0;
/*  743: 671 */     if (!this.hasReturned)
/*  744:     */     {
/*  745: 672 */       rh.doit(bc, 0);
/*  746: 673 */       bc.addOpcode(167);
/*  747: 674 */       pc3 = bc.currentPc();
/*  748: 675 */       bc.addIndex(0);
/*  749:     */     }
/*  750: 678 */     if (pc < pc2)
/*  751:     */     {
/*  752: 679 */       int pc4 = bc.currentPc();
/*  753: 680 */       rh.doit(bc, 0);
/*  754: 681 */       bc.addOpcode(191);
/*  755: 682 */       bc.addExceptionHandler(pc, pc2, pc4, 0);
/*  756:     */     }
/*  757: 685 */     if (!this.hasReturned) {
/*  758: 686 */       bc.write16bit(pc3, bc.currentPc() - pc3 + 1);
/*  759:     */     }
/*  760: 688 */     rh.remove(this);
/*  761: 690 */     if ((getListSize(this.breakList) != nbreaks) || (getListSize(this.continueList) != ncontinues)) {
/*  762: 692 */       throw new CompileError("sorry, cannot break/continue in synchronized block");
/*  763:     */     }
/*  764:     */   }
/*  765:     */   
/*  766:     */   private static int getListSize(ArrayList list)
/*  767:     */   {
/*  768: 697 */     return list == null ? 0 : list.size();
/*  769:     */   }
/*  770:     */   
/*  771:     */   private static boolean isPlusPlusExpr(ASTree expr)
/*  772:     */   {
/*  773: 701 */     if ((expr instanceof Expr))
/*  774:     */     {
/*  775: 702 */       int op = ((Expr)expr).getOperator();
/*  776: 703 */       return (op == 362) || (op == 363);
/*  777:     */     }
/*  778: 706 */     return false;
/*  779:     */   }
/*  780:     */   
/*  781:     */   public void atDeclarator(Declarator d)
/*  782:     */     throws CompileError
/*  783:     */   {
/*  784: 710 */     d.setLocalVar(getMaxLocals());
/*  785: 711 */     d.setClassName(resolveClassName(d.getClassName()));
/*  786:     */     int size;
/*  787:     */     int size;
/*  788: 714 */     if (is2word(d.getType(), d.getArrayDim())) {
/*  789: 715 */       size = 2;
/*  790:     */     } else {
/*  791: 717 */       size = 1;
/*  792:     */     }
/*  793: 719 */     incMaxLocals(size);
/*  794:     */     
/*  795:     */ 
/*  796:     */ 
/*  797: 723 */     ASTree init = d.getInitializer();
/*  798: 724 */     if (init != null)
/*  799:     */     {
/*  800: 725 */       doTypeCheck(init);
/*  801: 726 */       atVariableAssign(null, 61, null, d, init, false);
/*  802:     */     }
/*  803:     */   }
/*  804:     */   
/*  805:     */   public abstract void atNewExpr(NewExpr paramNewExpr)
/*  806:     */     throws CompileError;
/*  807:     */   
/*  808:     */   public abstract void atArrayInit(ArrayInit paramArrayInit)
/*  809:     */     throws CompileError;
/*  810:     */   
/*  811:     */   public void atAssignExpr(AssignExpr expr)
/*  812:     */     throws CompileError
/*  813:     */   {
/*  814: 735 */     atAssignExpr(expr, true);
/*  815:     */   }
/*  816:     */   
/*  817:     */   protected void atAssignExpr(AssignExpr expr, boolean doDup)
/*  818:     */     throws CompileError
/*  819:     */   {
/*  820: 742 */     int op = expr.getOperator();
/*  821: 743 */     ASTree left = expr.oprand1();
/*  822: 744 */     ASTree right = expr.oprand2();
/*  823: 745 */     if ((left instanceof Variable))
/*  824:     */     {
/*  825: 746 */       atVariableAssign(expr, op, (Variable)left, ((Variable)left).getDeclarator(), right, doDup);
/*  826:     */     }
/*  827:     */     else
/*  828:     */     {
/*  829: 750 */       if ((left instanceof Expr))
/*  830:     */       {
/*  831: 751 */         Expr e = (Expr)left;
/*  832: 752 */         if (e.getOperator() == 65)
/*  833:     */         {
/*  834: 753 */           atArrayAssign(expr, op, (Expr)left, right, doDup);
/*  835: 754 */           return;
/*  836:     */         }
/*  837:     */       }
/*  838: 758 */       atFieldAssign(expr, op, left, right, doDup);
/*  839:     */     }
/*  840:     */   }
/*  841:     */   
/*  842:     */   protected static void badAssign(Expr expr)
/*  843:     */     throws CompileError
/*  844:     */   {
/*  845:     */     String msg;
/*  846:     */     String msg;
/*  847: 764 */     if (expr == null) {
/*  848: 765 */       msg = "incompatible type for assignment";
/*  849:     */     } else {
/*  850: 767 */       msg = "incompatible type for " + expr.getName();
/*  851:     */     }
/*  852: 769 */     throw new CompileError(msg);
/*  853:     */   }
/*  854:     */   
/*  855:     */   private void atVariableAssign(Expr expr, int op, Variable var, Declarator d, ASTree right, boolean doDup)
/*  856:     */     throws CompileError
/*  857:     */   {
/*  858: 780 */     int varType = d.getType();
/*  859: 781 */     int varArray = d.getArrayDim();
/*  860: 782 */     String varClass = d.getClassName();
/*  861: 783 */     int varNo = getLocalVar(d);
/*  862: 785 */     if (op != 61) {
/*  863: 786 */       atVariable(var);
/*  864:     */     }
/*  865: 789 */     if ((expr == null) && ((right instanceof ArrayInit))) {
/*  866: 790 */       atArrayVariableAssign((ArrayInit)right, varType, varArray, varClass);
/*  867:     */     } else {
/*  868: 792 */       atAssignCore(expr, op, right, varType, varArray, varClass);
/*  869:     */     }
/*  870: 794 */     if (doDup) {
/*  871: 795 */       if (is2word(varType, varArray)) {
/*  872: 796 */         this.bytecode.addOpcode(92);
/*  873:     */       } else {
/*  874: 798 */         this.bytecode.addOpcode(89);
/*  875:     */       }
/*  876:     */     }
/*  877: 800 */     if (varArray > 0) {
/*  878: 801 */       this.bytecode.addAstore(varNo);
/*  879: 802 */     } else if (varType == 312) {
/*  880: 803 */       this.bytecode.addDstore(varNo);
/*  881: 804 */     } else if (varType == 317) {
/*  882: 805 */       this.bytecode.addFstore(varNo);
/*  883: 806 */     } else if (varType == 326) {
/*  884: 807 */       this.bytecode.addLstore(varNo);
/*  885: 808 */     } else if (isRefType(varType)) {
/*  886: 809 */       this.bytecode.addAstore(varNo);
/*  887:     */     } else {
/*  888: 811 */       this.bytecode.addIstore(varNo);
/*  889:     */     }
/*  890: 813 */     this.exprType = varType;
/*  891: 814 */     this.arrayDim = varArray;
/*  892: 815 */     this.className = varClass;
/*  893:     */   }
/*  894:     */   
/*  895:     */   protected abstract void atArrayVariableAssign(ArrayInit paramArrayInit, int paramInt1, int paramInt2, String paramString)
/*  896:     */     throws CompileError;
/*  897:     */   
/*  898:     */   private void atArrayAssign(Expr expr, int op, Expr array, ASTree right, boolean doDup)
/*  899:     */     throws CompileError
/*  900:     */   {
/*  901: 824 */     arrayAccess(array.oprand1(), array.oprand2());
/*  902: 826 */     if (op != 61)
/*  903:     */     {
/*  904: 827 */       this.bytecode.addOpcode(92);
/*  905: 828 */       this.bytecode.addOpcode(getArrayReadOp(this.exprType, this.arrayDim));
/*  906:     */     }
/*  907: 831 */     int aType = this.exprType;
/*  908: 832 */     int aDim = this.arrayDim;
/*  909: 833 */     String cname = this.className;
/*  910:     */     
/*  911: 835 */     atAssignCore(expr, op, right, aType, aDim, cname);
/*  912: 837 */     if (doDup) {
/*  913: 838 */       if (is2word(aType, aDim)) {
/*  914: 839 */         this.bytecode.addOpcode(94);
/*  915:     */       } else {
/*  916: 841 */         this.bytecode.addOpcode(91);
/*  917:     */       }
/*  918:     */     }
/*  919: 843 */     this.bytecode.addOpcode(getArrayWriteOp(aType, aDim));
/*  920: 844 */     this.exprType = aType;
/*  921: 845 */     this.arrayDim = aDim;
/*  922: 846 */     this.className = cname;
/*  923:     */   }
/*  924:     */   
/*  925:     */   protected abstract void atFieldAssign(Expr paramExpr, int paramInt, ASTree paramASTree1, ASTree paramASTree2, boolean paramBoolean)
/*  926:     */     throws CompileError;
/*  927:     */   
/*  928:     */   protected void atAssignCore(Expr expr, int op, ASTree right, int type, int dim, String cname)
/*  929:     */     throws CompileError
/*  930:     */   {
/*  931: 856 */     if ((op == 354) && (dim == 0) && (type == 307))
/*  932:     */     {
/*  933: 857 */       atStringPlusEq(expr, type, dim, cname, right);
/*  934:     */     }
/*  935:     */     else
/*  936:     */     {
/*  937: 859 */       right.accept(this);
/*  938: 860 */       if ((invalidDim(this.exprType, this.arrayDim, this.className, type, dim, cname, false)) || ((op != 61) && (dim > 0))) {
/*  939: 862 */         badAssign(expr);
/*  940:     */       }
/*  941: 864 */       if (op != 61)
/*  942:     */       {
/*  943: 865 */         int token = assignOps[(op - 351)];
/*  944: 866 */         int k = lookupBinOp(token);
/*  945: 867 */         if (k < 0) {
/*  946: 868 */           fatal();
/*  947:     */         }
/*  948: 870 */         atArithBinExpr(expr, token, k, type);
/*  949:     */       }
/*  950:     */     }
/*  951: 874 */     if ((op != 61) || ((dim == 0) && (!isRefType(type)))) {
/*  952: 875 */       atNumCastExpr(this.exprType, type);
/*  953:     */     }
/*  954:     */   }
/*  955:     */   
/*  956:     */   private void atStringPlusEq(Expr expr, int type, int dim, String cname, ASTree right)
/*  957:     */     throws CompileError
/*  958:     */   {
/*  959: 884 */     if (!"java/lang/String".equals(cname)) {
/*  960: 885 */       badAssign(expr);
/*  961:     */     }
/*  962: 887 */     convToString(type, dim);
/*  963: 888 */     right.accept(this);
/*  964: 889 */     convToString(this.exprType, this.arrayDim);
/*  965: 890 */     this.bytecode.addInvokevirtual("java.lang.String", "concat", "(Ljava/lang/String;)Ljava/lang/String;");
/*  966:     */     
/*  967: 892 */     this.exprType = 307;
/*  968: 893 */     this.arrayDim = 0;
/*  969: 894 */     this.className = "java/lang/String";
/*  970:     */   }
/*  971:     */   
/*  972:     */   private boolean invalidDim(int srcType, int srcDim, String srcClass, int destType, int destDim, String destClass, boolean isCast)
/*  973:     */   {
/*  974: 901 */     if (srcDim != destDim)
/*  975:     */     {
/*  976: 902 */       if (srcType == 412) {
/*  977: 903 */         return false;
/*  978:     */       }
/*  979: 904 */       if ((destDim == 0) && (destType == 307) && ("java/lang/Object".equals(destClass))) {
/*  980: 906 */         return false;
/*  981:     */       }
/*  982: 907 */       if ((isCast) && (srcDim == 0) && (srcType == 307) && ("java/lang/Object".equals(srcClass))) {
/*  983: 909 */         return false;
/*  984:     */       }
/*  985: 911 */       return true;
/*  986:     */     }
/*  987: 913 */     return false;
/*  988:     */   }
/*  989:     */   
/*  990:     */   public void atCondExpr(CondExpr expr)
/*  991:     */     throws CompileError
/*  992:     */   {
/*  993: 917 */     booleanExpr(false, expr.condExpr());
/*  994: 918 */     int pc = this.bytecode.currentPc();
/*  995: 919 */     this.bytecode.addIndex(0);
/*  996: 920 */     expr.thenExpr().accept(this);
/*  997: 921 */     int dim1 = this.arrayDim;
/*  998: 922 */     this.bytecode.addOpcode(167);
/*  999: 923 */     int pc2 = this.bytecode.currentPc();
/* 1000: 924 */     this.bytecode.addIndex(0);
/* 1001: 925 */     this.bytecode.write16bit(pc, this.bytecode.currentPc() - pc + 1);
/* 1002: 926 */     expr.elseExpr().accept(this);
/* 1003: 927 */     if (dim1 != this.arrayDim) {
/* 1004: 928 */       throw new CompileError("type mismatch in ?:");
/* 1005:     */     }
/* 1006: 930 */     this.bytecode.write16bit(pc2, this.bytecode.currentPc() - pc2 + 1);
/* 1007:     */   }
/* 1008:     */   
/* 1009: 933 */   static final int[] binOp = { 43, 99, 98, 97, 96, 45, 103, 102, 101, 100, 42, 107, 106, 105, 104, 47, 111, 110, 109, 108, 37, 115, 114, 113, 112, 124, 0, 0, 129, 128, 94, 0, 0, 131, 130, 38, 0, 0, 127, 126, 364, 0, 0, 121, 120, 366, 0, 0, 123, 122, 370, 0, 0, 125, 124 };
/* 1010:     */   
/* 1011:     */   static int lookupBinOp(int token)
/* 1012:     */   {
/* 1013: 947 */     int[] code = binOp;
/* 1014: 948 */     int s = code.length;
/* 1015: 949 */     for (int k = 0; k < s; k += 5) {
/* 1016: 950 */       if (code[k] == token) {
/* 1017: 951 */         return k;
/* 1018:     */       }
/* 1019:     */     }
/* 1020: 953 */     return -1;
/* 1021:     */   }
/* 1022:     */   
/* 1023:     */   public void atBinExpr(BinExpr expr)
/* 1024:     */     throws CompileError
/* 1025:     */   {
/* 1026: 957 */     int token = expr.getOperator();
/* 1027:     */     
/* 1028:     */ 
/* 1029:     */ 
/* 1030: 961 */     int k = lookupBinOp(token);
/* 1031: 962 */     if (k >= 0)
/* 1032:     */     {
/* 1033: 963 */       expr.oprand1().accept(this);
/* 1034: 964 */       ASTree right = expr.oprand2();
/* 1035: 965 */       if (right == null) {
/* 1036: 966 */         return;
/* 1037:     */       }
/* 1038: 968 */       int type1 = this.exprType;
/* 1039: 969 */       int dim1 = this.arrayDim;
/* 1040: 970 */       String cname1 = this.className;
/* 1041: 971 */       right.accept(this);
/* 1042: 972 */       if (dim1 != this.arrayDim) {
/* 1043: 973 */         throw new CompileError("incompatible array types");
/* 1044:     */       }
/* 1045: 975 */       if ((token == 43) && (dim1 == 0) && ((type1 == 307) || (this.exprType == 307))) {
/* 1046: 977 */         atStringConcatExpr(expr, type1, dim1, cname1);
/* 1047:     */       } else {
/* 1048: 979 */         atArithBinExpr(expr, token, k, type1);
/* 1049:     */       }
/* 1050:     */     }
/* 1051:     */     else
/* 1052:     */     {
/* 1053: 984 */       booleanExpr(true, expr);
/* 1054: 985 */       this.bytecode.addIndex(7);
/* 1055: 986 */       this.bytecode.addIconst(0);
/* 1056: 987 */       this.bytecode.addOpcode(167);
/* 1057: 988 */       this.bytecode.addIndex(4);
/* 1058: 989 */       this.bytecode.addIconst(1);
/* 1059:     */     }
/* 1060:     */   }
/* 1061:     */   
/* 1062:     */   private void atArithBinExpr(Expr expr, int token, int index, int type1)
/* 1063:     */     throws CompileError
/* 1064:     */   {
/* 1065:1000 */     if (this.arrayDim != 0) {
/* 1066:1001 */       badTypes(expr);
/* 1067:     */     }
/* 1068:1003 */     int type2 = this.exprType;
/* 1069:1004 */     if ((token == 364) || (token == 366) || (token == 370))
/* 1070:     */     {
/* 1071:1005 */       if ((type2 == 324) || (type2 == 334) || (type2 == 306) || (type2 == 303)) {
/* 1072:1007 */         this.exprType = type1;
/* 1073:     */       } else {
/* 1074:1009 */         badTypes(expr);
/* 1075:     */       }
/* 1076:     */     }
/* 1077:     */     else {
/* 1078:1011 */       convertOprandTypes(type1, type2, expr);
/* 1079:     */     }
/* 1080:1013 */     int p = typePrecedence(this.exprType);
/* 1081:1014 */     if (p >= 0)
/* 1082:     */     {
/* 1083:1015 */       int op = binOp[(index + p + 1)];
/* 1084:1016 */       if (op != 0)
/* 1085:     */       {
/* 1086:1017 */         if ((p == 3) && (this.exprType != 301)) {
/* 1087:1018 */           this.exprType = 324;
/* 1088:     */         }
/* 1089:1020 */         this.bytecode.addOpcode(op);
/* 1090:1021 */         return;
/* 1091:     */       }
/* 1092:     */     }
/* 1093:1025 */     badTypes(expr);
/* 1094:     */   }
/* 1095:     */   
/* 1096:     */   private void atStringConcatExpr(Expr expr, int type1, int dim1, String cname1)
/* 1097:     */     throws CompileError
/* 1098:     */   {
/* 1099:1031 */     int type2 = this.exprType;
/* 1100:1032 */     int dim2 = this.arrayDim;
/* 1101:1033 */     boolean type2Is2 = is2word(type2, dim2);
/* 1102:1034 */     boolean type2IsString = (type2 == 307) && ("java/lang/String".equals(this.className));
/* 1103:1037 */     if (type2Is2) {
/* 1104:1038 */       convToString(type2, dim2);
/* 1105:     */     }
/* 1106:1040 */     if (is2word(type1, dim1))
/* 1107:     */     {
/* 1108:1041 */       this.bytecode.addOpcode(91);
/* 1109:1042 */       this.bytecode.addOpcode(87);
/* 1110:     */     }
/* 1111:     */     else
/* 1112:     */     {
/* 1113:1045 */       this.bytecode.addOpcode(95);
/* 1114:     */     }
/* 1115:1048 */     convToString(type1, dim1);
/* 1116:1049 */     this.bytecode.addOpcode(95);
/* 1117:1051 */     if ((!type2Is2) && (!type2IsString)) {
/* 1118:1052 */       convToString(type2, dim2);
/* 1119:     */     }
/* 1120:1054 */     this.bytecode.addInvokevirtual("java.lang.String", "concat", "(Ljava/lang/String;)Ljava/lang/String;");
/* 1121:     */     
/* 1122:1056 */     this.exprType = 307;
/* 1123:1057 */     this.arrayDim = 0;
/* 1124:1058 */     this.className = "java/lang/String";
/* 1125:     */   }
/* 1126:     */   
/* 1127:     */   private void convToString(int type, int dim)
/* 1128:     */     throws CompileError
/* 1129:     */   {
/* 1130:1062 */     String method = "valueOf";
/* 1131:1064 */     if ((isRefType(type)) || (dim > 0))
/* 1132:     */     {
/* 1133:1065 */       this.bytecode.addInvokestatic("java.lang.String", "valueOf", "(Ljava/lang/Object;)Ljava/lang/String;");
/* 1134:     */     }
/* 1135:1067 */     else if (type == 312)
/* 1136:     */     {
/* 1137:1068 */       this.bytecode.addInvokestatic("java.lang.String", "valueOf", "(D)Ljava/lang/String;");
/* 1138:     */     }
/* 1139:1070 */     else if (type == 317)
/* 1140:     */     {
/* 1141:1071 */       this.bytecode.addInvokestatic("java.lang.String", "valueOf", "(F)Ljava/lang/String;");
/* 1142:     */     }
/* 1143:1073 */     else if (type == 326)
/* 1144:     */     {
/* 1145:1074 */       this.bytecode.addInvokestatic("java.lang.String", "valueOf", "(J)Ljava/lang/String;");
/* 1146:     */     }
/* 1147:1076 */     else if (type == 301)
/* 1148:     */     {
/* 1149:1077 */       this.bytecode.addInvokestatic("java.lang.String", "valueOf", "(Z)Ljava/lang/String;");
/* 1150:     */     }
/* 1151:1079 */     else if (type == 306)
/* 1152:     */     {
/* 1153:1080 */       this.bytecode.addInvokestatic("java.lang.String", "valueOf", "(C)Ljava/lang/String;");
/* 1154:     */     }
/* 1155:     */     else
/* 1156:     */     {
/* 1157:1082 */       if (type == 344) {
/* 1158:1083 */         throw new CompileError("void type expression");
/* 1159:     */       }
/* 1160:1085 */       this.bytecode.addInvokestatic("java.lang.String", "valueOf", "(I)Ljava/lang/String;");
/* 1161:     */     }
/* 1162:     */   }
/* 1163:     */   
/* 1164:     */   private boolean booleanExpr(boolean branchIf, ASTree expr)
/* 1165:     */     throws CompileError
/* 1166:     */   {
/* 1167:1098 */     int op = getCompOperator(expr);
/* 1168:1099 */     if (op == 358)
/* 1169:     */     {
/* 1170:1100 */       BinExpr bexpr = (BinExpr)expr;
/* 1171:1101 */       int type1 = compileOprands(bexpr);
/* 1172:     */       
/* 1173:     */ 
/* 1174:1104 */       compareExpr(branchIf, bexpr.getOperator(), type1, bexpr);
/* 1175:     */     }
/* 1176:1106 */     else if (op == 33)
/* 1177:     */     {
/* 1178:1107 */       booleanExpr(!branchIf, ((Expr)expr).oprand1());
/* 1179:     */     }
/* 1180:     */     else
/* 1181:     */     {
/* 1182:     */       boolean isAndAnd;
/* 1183:1108 */       if (((isAndAnd = op == 369 ? 1 : 0) != 0) || (op == 368))
/* 1184:     */       {
/* 1185:1109 */         BinExpr bexpr = (BinExpr)expr;
/* 1186:1110 */         booleanExpr(!isAndAnd, bexpr.oprand1());
/* 1187:1111 */         int pc = this.bytecode.currentPc();
/* 1188:1112 */         this.bytecode.addIndex(0);
/* 1189:     */         
/* 1190:1114 */         booleanExpr(isAndAnd, bexpr.oprand2());
/* 1191:1115 */         this.bytecode.write16bit(pc, this.bytecode.currentPc() - pc + 3);
/* 1192:1116 */         if (branchIf != isAndAnd)
/* 1193:     */         {
/* 1194:1117 */           this.bytecode.addIndex(6);
/* 1195:1118 */           this.bytecode.addOpcode(167);
/* 1196:     */         }
/* 1197:     */       }
/* 1198:     */       else
/* 1199:     */       {
/* 1200:1121 */         if (isAlwaysBranch(expr, branchIf))
/* 1201:     */         {
/* 1202:1122 */           this.bytecode.addOpcode(167);
/* 1203:1123 */           return true;
/* 1204:     */         }
/* 1205:1126 */         expr.accept(this);
/* 1206:1127 */         if ((this.exprType != 301) || (this.arrayDim != 0)) {
/* 1207:1128 */           throw new CompileError("boolean expr is required");
/* 1208:     */         }
/* 1209:1130 */         this.bytecode.addOpcode(branchIf ? 154 : 153);
/* 1210:     */       }
/* 1211:     */     }
/* 1212:1133 */     this.exprType = 301;
/* 1213:1134 */     this.arrayDim = 0;
/* 1214:1135 */     return false;
/* 1215:     */   }
/* 1216:     */   
/* 1217:     */   private static boolean isAlwaysBranch(ASTree expr, boolean branchIf)
/* 1218:     */   {
/* 1219:1140 */     if ((expr instanceof Keyword))
/* 1220:     */     {
/* 1221:1141 */       int t = ((Keyword)expr).get();
/* 1222:1142 */       return t == 410;
/* 1223:     */     }
/* 1224:1145 */     return false;
/* 1225:     */   }
/* 1226:     */   
/* 1227:     */   static int getCompOperator(ASTree expr)
/* 1228:     */     throws CompileError
/* 1229:     */   {
/* 1230:1149 */     if ((expr instanceof Expr))
/* 1231:     */     {
/* 1232:1150 */       Expr bexpr = (Expr)expr;
/* 1233:1151 */       int token = bexpr.getOperator();
/* 1234:1152 */       if (token == 33) {
/* 1235:1153 */         return 33;
/* 1236:     */       }
/* 1237:1154 */       if (((bexpr instanceof BinExpr)) && (token != 368) && (token != 369) && (token != 38) && (token != 124)) {
/* 1238:1157 */         return 358;
/* 1239:     */       }
/* 1240:1159 */       return token;
/* 1241:     */     }
/* 1242:1162 */     return 32;
/* 1243:     */   }
/* 1244:     */   
/* 1245:     */   private int compileOprands(BinExpr expr)
/* 1246:     */     throws CompileError
/* 1247:     */   {
/* 1248:1166 */     expr.oprand1().accept(this);
/* 1249:1167 */     int type1 = this.exprType;
/* 1250:1168 */     int dim1 = this.arrayDim;
/* 1251:1169 */     expr.oprand2().accept(this);
/* 1252:1170 */     if (dim1 != this.arrayDim)
/* 1253:     */     {
/* 1254:1171 */       if ((type1 != 412) && (this.exprType != 412)) {
/* 1255:1172 */         throw new CompileError("incompatible array types");
/* 1256:     */       }
/* 1257:1173 */       if (this.exprType == 412) {
/* 1258:1174 */         this.arrayDim = dim1;
/* 1259:     */       }
/* 1260:     */     }
/* 1261:1176 */     if (type1 == 412) {
/* 1262:1177 */       return this.exprType;
/* 1263:     */     }
/* 1264:1179 */     return type1;
/* 1265:     */   }
/* 1266:     */   
/* 1267:1182 */   private static final int[] ifOp = { 358, 159, 160, 350, 160, 159, 357, 164, 163, 359, 162, 161, 60, 161, 162, 62, 163, 164 };
/* 1268:1189 */   private static final int[] ifOp2 = { 358, 153, 154, 350, 154, 153, 357, 158, 157, 359, 156, 155, 60, 155, 156, 62, 157, 158 };
/* 1269:     */   private static final int P_DOUBLE = 0;
/* 1270:     */   private static final int P_FLOAT = 1;
/* 1271:     */   private static final int P_LONG = 2;
/* 1272:     */   private static final int P_INT = 3;
/* 1273:     */   private static final int P_OTHER = -1;
/* 1274:     */   
/* 1275:     */   private void compareExpr(boolean branchIf, int token, int type1, BinExpr expr)
/* 1276:     */     throws CompileError
/* 1277:     */   {
/* 1278:1205 */     if (this.arrayDim == 0) {
/* 1279:1206 */       convertOprandTypes(type1, this.exprType, expr);
/* 1280:     */     }
/* 1281:1208 */     int p = typePrecedence(this.exprType);
/* 1282:1209 */     if ((p == -1) || (this.arrayDim > 0))
/* 1283:     */     {
/* 1284:1210 */       if (token == 358) {
/* 1285:1211 */         this.bytecode.addOpcode(branchIf ? 165 : 166);
/* 1286:1212 */       } else if (token == 350) {
/* 1287:1213 */         this.bytecode.addOpcode(branchIf ? 166 : 165);
/* 1288:     */       } else {
/* 1289:1215 */         badTypes(expr);
/* 1290:     */       }
/* 1291:     */     }
/* 1292:1217 */     else if (p == 3)
/* 1293:     */     {
/* 1294:1218 */       int[] op = ifOp;
/* 1295:1219 */       for (int i = 0; i < op.length; i += 3) {
/* 1296:1220 */         if (op[i] == token)
/* 1297:     */         {
/* 1298:1221 */           this.bytecode.addOpcode(op[(i + 2)]);
/* 1299:1222 */           return;
/* 1300:     */         }
/* 1301:     */       }
/* 1302:1225 */       badTypes(expr);
/* 1303:     */     }
/* 1304:     */     else
/* 1305:     */     {
/* 1306:1228 */       if (p == 0)
/* 1307:     */       {
/* 1308:1229 */         if ((token == 60) || (token == 357)) {
/* 1309:1230 */           this.bytecode.addOpcode(152);
/* 1310:     */         } else {
/* 1311:1232 */           this.bytecode.addOpcode(151);
/* 1312:     */         }
/* 1313:     */       }
/* 1314:1233 */       else if (p == 1)
/* 1315:     */       {
/* 1316:1234 */         if ((token == 60) || (token == 357)) {
/* 1317:1235 */           this.bytecode.addOpcode(150);
/* 1318:     */         } else {
/* 1319:1237 */           this.bytecode.addOpcode(149);
/* 1320:     */         }
/* 1321:     */       }
/* 1322:1238 */       else if (p == 2) {
/* 1323:1239 */         this.bytecode.addOpcode(148);
/* 1324:     */       } else {
/* 1325:1241 */         fatal();
/* 1326:     */       }
/* 1327:1243 */       int[] op = ifOp2;
/* 1328:1244 */       for (int i = 0; i < op.length; i += 3) {
/* 1329:1245 */         if (op[i] == token)
/* 1330:     */         {
/* 1331:1246 */           this.bytecode.addOpcode(op[(i + 2)]);
/* 1332:1247 */           return;
/* 1333:     */         }
/* 1334:     */       }
/* 1335:1250 */       badTypes(expr);
/* 1336:     */     }
/* 1337:     */   }
/* 1338:     */   
/* 1339:     */   protected static void badTypes(Expr expr)
/* 1340:     */     throws CompileError
/* 1341:     */   {
/* 1342:1255 */     throw new CompileError("invalid types for " + expr.getName());
/* 1343:     */   }
/* 1344:     */   
/* 1345:     */   protected static boolean isRefType(int type)
/* 1346:     */   {
/* 1347:1265 */     return (type == 307) || (type == 412);
/* 1348:     */   }
/* 1349:     */   
/* 1350:     */   private static int typePrecedence(int type)
/* 1351:     */   {
/* 1352:1269 */     if (type == 312) {
/* 1353:1270 */       return 0;
/* 1354:     */     }
/* 1355:1271 */     if (type == 317) {
/* 1356:1272 */       return 1;
/* 1357:     */     }
/* 1358:1273 */     if (type == 326) {
/* 1359:1274 */       return 2;
/* 1360:     */     }
/* 1361:1275 */     if (isRefType(type)) {
/* 1362:1276 */       return -1;
/* 1363:     */     }
/* 1364:1277 */     if (type == 344) {
/* 1365:1278 */       return -1;
/* 1366:     */     }
/* 1367:1280 */     return 3;
/* 1368:     */   }
/* 1369:     */   
/* 1370:     */   static boolean isP_INT(int type)
/* 1371:     */   {
/* 1372:1285 */     return typePrecedence(type) == 3;
/* 1373:     */   }
/* 1374:     */   
/* 1375:     */   static boolean rightIsStrong(int type1, int type2)
/* 1376:     */   {
/* 1377:1290 */     int type1_p = typePrecedence(type1);
/* 1378:1291 */     int type2_p = typePrecedence(type2);
/* 1379:1292 */     return (type1_p >= 0) && (type2_p >= 0) && (type1_p > type2_p);
/* 1380:     */   }
/* 1381:     */   
/* 1382:1295 */   private static final int[] castOp = { 0, 144, 143, 142, 141, 0, 140, 139, 138, 137, 0, 136, 135, 134, 133, 0 };
/* 1383:     */   
/* 1384:     */   private void convertOprandTypes(int type1, int type2, Expr expr)
/* 1385:     */     throws CompileError
/* 1386:     */   {
/* 1387:1309 */     int type1_p = typePrecedence(type1);
/* 1388:1310 */     int type2_p = typePrecedence(type2);
/* 1389:1312 */     if ((type2_p < 0) && (type1_p < 0)) {
/* 1390:1313 */       return;
/* 1391:     */     }
/* 1392:1315 */     if ((type2_p < 0) || (type1_p < 0)) {
/* 1393:1316 */       badTypes(expr);
/* 1394:     */     }
/* 1395:     */     int result_type;
/* 1396:     */     boolean rightStrong;
/* 1397:     */     int op;
/* 1398:     */     int result_type;
/* 1399:1319 */     if (type1_p <= type2_p)
/* 1400:     */     {
/* 1401:1320 */       boolean rightStrong = false;
/* 1402:1321 */       this.exprType = type1;
/* 1403:1322 */       int op = castOp[(type2_p * 4 + type1_p)];
/* 1404:1323 */       result_type = type1_p;
/* 1405:     */     }
/* 1406:     */     else
/* 1407:     */     {
/* 1408:1326 */       rightStrong = true;
/* 1409:1327 */       op = castOp[(type1_p * 4 + type2_p)];
/* 1410:1328 */       result_type = type2_p;
/* 1411:     */     }
/* 1412:1331 */     if (rightStrong)
/* 1413:     */     {
/* 1414:1332 */       if ((result_type == 0) || (result_type == 2))
/* 1415:     */       {
/* 1416:1333 */         if ((type1_p == 0) || (type1_p == 2)) {
/* 1417:1334 */           this.bytecode.addOpcode(94);
/* 1418:     */         } else {
/* 1419:1336 */           this.bytecode.addOpcode(93);
/* 1420:     */         }
/* 1421:1338 */         this.bytecode.addOpcode(88);
/* 1422:1339 */         this.bytecode.addOpcode(op);
/* 1423:1340 */         this.bytecode.addOpcode(94);
/* 1424:1341 */         this.bytecode.addOpcode(88);
/* 1425:     */       }
/* 1426:1343 */       else if (result_type == 1)
/* 1427:     */       {
/* 1428:1344 */         if (type1_p == 2)
/* 1429:     */         {
/* 1430:1345 */           this.bytecode.addOpcode(91);
/* 1431:1346 */           this.bytecode.addOpcode(87);
/* 1432:     */         }
/* 1433:     */         else
/* 1434:     */         {
/* 1435:1349 */           this.bytecode.addOpcode(95);
/* 1436:     */         }
/* 1437:1351 */         this.bytecode.addOpcode(op);
/* 1438:1352 */         this.bytecode.addOpcode(95);
/* 1439:     */       }
/* 1440:     */       else
/* 1441:     */       {
/* 1442:1355 */         fatal();
/* 1443:     */       }
/* 1444:     */     }
/* 1445:1357 */     else if (op != 0) {
/* 1446:1358 */       this.bytecode.addOpcode(op);
/* 1447:     */     }
/* 1448:     */   }
/* 1449:     */   
/* 1450:     */   public void atCastExpr(CastExpr expr)
/* 1451:     */     throws CompileError
/* 1452:     */   {
/* 1453:1362 */     String cname = resolveClassName(expr.getClassName());
/* 1454:1363 */     String toClass = checkCastExpr(expr, cname);
/* 1455:1364 */     int srcType = this.exprType;
/* 1456:1365 */     this.exprType = expr.getType();
/* 1457:1366 */     this.arrayDim = expr.getArrayDim();
/* 1458:1367 */     this.className = cname;
/* 1459:1368 */     if (toClass == null) {
/* 1460:1369 */       atNumCastExpr(srcType, this.exprType);
/* 1461:     */     } else {
/* 1462:1371 */       this.bytecode.addCheckcast(toClass);
/* 1463:     */     }
/* 1464:     */   }
/* 1465:     */   
/* 1466:     */   public void atInstanceOfExpr(InstanceOfExpr expr)
/* 1467:     */     throws CompileError
/* 1468:     */   {
/* 1469:1375 */     String cname = resolveClassName(expr.getClassName());
/* 1470:1376 */     String toClass = checkCastExpr(expr, cname);
/* 1471:1377 */     this.bytecode.addInstanceof(toClass);
/* 1472:1378 */     this.exprType = 301;
/* 1473:1379 */     this.arrayDim = 0;
/* 1474:     */   }
/* 1475:     */   
/* 1476:     */   private String checkCastExpr(CastExpr expr, String name)
/* 1477:     */     throws CompileError
/* 1478:     */   {
/* 1479:1385 */     String msg = "invalid cast";
/* 1480:1386 */     ASTree oprand = expr.getOprand();
/* 1481:1387 */     int dim = expr.getArrayDim();
/* 1482:1388 */     int type = expr.getType();
/* 1483:1389 */     oprand.accept(this);
/* 1484:1390 */     int srcType = this.exprType;
/* 1485:1391 */     if ((invalidDim(srcType, this.arrayDim, this.className, type, dim, name, true)) || (srcType == 344) || (type == 344)) {
/* 1486:1393 */       throw new CompileError("invalid cast");
/* 1487:     */     }
/* 1488:1395 */     if (type == 307)
/* 1489:     */     {
/* 1490:1396 */       if (!isRefType(srcType)) {
/* 1491:1397 */         throw new CompileError("invalid cast");
/* 1492:     */       }
/* 1493:1399 */       return toJvmArrayName(name, dim);
/* 1494:     */     }
/* 1495:1402 */     if (dim > 0) {
/* 1496:1403 */       return toJvmTypeName(type, dim);
/* 1497:     */     }
/* 1498:1405 */     return null;
/* 1499:     */   }
/* 1500:     */   
/* 1501:     */   void atNumCastExpr(int srcType, int destType)
/* 1502:     */     throws CompileError
/* 1503:     */   {
/* 1504:1411 */     if (srcType == destType) {
/* 1505:1412 */       return;
/* 1506:     */     }
/* 1507:1415 */     int stype = typePrecedence(srcType);
/* 1508:1416 */     int dtype = typePrecedence(destType);
/* 1509:     */     int op;
/* 1510:     */     int op;
/* 1511:1417 */     if ((0 <= stype) && (stype < 3)) {
/* 1512:1418 */       op = castOp[(stype * 4 + dtype)];
/* 1513:     */     } else {
/* 1514:1420 */       op = 0;
/* 1515:     */     }
/* 1516:     */     int op2;
/* 1517:     */     int op2;
/* 1518:1422 */     if (destType == 312)
/* 1519:     */     {
/* 1520:1423 */       op2 = 135;
/* 1521:     */     }
/* 1522:     */     else
/* 1523:     */     {
/* 1524:     */       int op2;
/* 1525:1424 */       if (destType == 317)
/* 1526:     */       {
/* 1527:1425 */         op2 = 134;
/* 1528:     */       }
/* 1529:     */       else
/* 1530:     */       {
/* 1531:     */         int op2;
/* 1532:1426 */         if (destType == 326)
/* 1533:     */         {
/* 1534:1427 */           op2 = 133;
/* 1535:     */         }
/* 1536:     */         else
/* 1537:     */         {
/* 1538:     */           int op2;
/* 1539:1428 */           if (destType == 334)
/* 1540:     */           {
/* 1541:1429 */             op2 = 147;
/* 1542:     */           }
/* 1543:     */           else
/* 1544:     */           {
/* 1545:     */             int op2;
/* 1546:1430 */             if (destType == 306)
/* 1547:     */             {
/* 1548:1431 */               op2 = 146;
/* 1549:     */             }
/* 1550:     */             else
/* 1551:     */             {
/* 1552:     */               int op2;
/* 1553:1432 */               if (destType == 303) {
/* 1554:1433 */                 op2 = 145;
/* 1555:     */               } else {
/* 1556:1435 */                 op2 = 0;
/* 1557:     */               }
/* 1558:     */             }
/* 1559:     */           }
/* 1560:     */         }
/* 1561:     */       }
/* 1562:     */     }
/* 1563:1437 */     if (op != 0) {
/* 1564:1438 */       this.bytecode.addOpcode(op);
/* 1565:     */     }
/* 1566:1440 */     if (((op == 0) || (op == 136) || (op == 139) || (op == 142)) && 
/* 1567:1441 */       (op2 != 0)) {
/* 1568:1442 */       this.bytecode.addOpcode(op2);
/* 1569:     */     }
/* 1570:     */   }
/* 1571:     */   
/* 1572:     */   public void atExpr(Expr expr)
/* 1573:     */     throws CompileError
/* 1574:     */   {
/* 1575:1449 */     int token = expr.getOperator();
/* 1576:1450 */     ASTree oprand = expr.oprand1();
/* 1577:1451 */     if (token == 46)
/* 1578:     */     {
/* 1579:1452 */       String member = ((Symbol)expr.oprand2()).get();
/* 1580:1453 */       if (member.equals("class")) {
/* 1581:1454 */         atClassObject(expr);
/* 1582:     */       } else {
/* 1583:1456 */         atFieldRead(expr);
/* 1584:     */       }
/* 1585:     */     }
/* 1586:1458 */     else if (token == 35)
/* 1587:     */     {
/* 1588:1463 */       atFieldRead(expr);
/* 1589:     */     }
/* 1590:1465 */     else if (token == 65)
/* 1591:     */     {
/* 1592:1466 */       atArrayRead(oprand, expr.oprand2());
/* 1593:     */     }
/* 1594:1467 */     else if ((token == 362) || (token == 363))
/* 1595:     */     {
/* 1596:1468 */       atPlusPlus(token, oprand, expr, true);
/* 1597:     */     }
/* 1598:1469 */     else if (token == 33)
/* 1599:     */     {
/* 1600:1470 */       booleanExpr(false, expr);
/* 1601:1471 */       this.bytecode.addIndex(7);
/* 1602:1472 */       this.bytecode.addIconst(1);
/* 1603:1473 */       this.bytecode.addOpcode(167);
/* 1604:1474 */       this.bytecode.addIndex(4);
/* 1605:1475 */       this.bytecode.addIconst(0);
/* 1606:     */     }
/* 1607:1477 */     else if (token == 67)
/* 1608:     */     {
/* 1609:1478 */       fatal();
/* 1610:     */     }
/* 1611:     */     else
/* 1612:     */     {
/* 1613:1480 */       expr.oprand1().accept(this);
/* 1614:1481 */       int type = typePrecedence(this.exprType);
/* 1615:1482 */       if (this.arrayDim > 0) {
/* 1616:1483 */         badType(expr);
/* 1617:     */       }
/* 1618:1485 */       if (token == 45)
/* 1619:     */       {
/* 1620:1486 */         if (type == 0)
/* 1621:     */         {
/* 1622:1487 */           this.bytecode.addOpcode(119);
/* 1623:     */         }
/* 1624:1488 */         else if (type == 1)
/* 1625:     */         {
/* 1626:1489 */           this.bytecode.addOpcode(118);
/* 1627:     */         }
/* 1628:1490 */         else if (type == 2)
/* 1629:     */         {
/* 1630:1491 */           this.bytecode.addOpcode(117);
/* 1631:     */         }
/* 1632:1492 */         else if (type == 3)
/* 1633:     */         {
/* 1634:1493 */           this.bytecode.addOpcode(116);
/* 1635:1494 */           this.exprType = 324;
/* 1636:     */         }
/* 1637:     */         else
/* 1638:     */         {
/* 1639:1497 */           badType(expr);
/* 1640:     */         }
/* 1641:     */       }
/* 1642:1499 */       else if (token == 126)
/* 1643:     */       {
/* 1644:1500 */         if (type == 3)
/* 1645:     */         {
/* 1646:1501 */           this.bytecode.addIconst(-1);
/* 1647:1502 */           this.bytecode.addOpcode(130);
/* 1648:1503 */           this.exprType = 324;
/* 1649:     */         }
/* 1650:1505 */         else if (type == 2)
/* 1651:     */         {
/* 1652:1506 */           this.bytecode.addLconst(-1L);
/* 1653:1507 */           this.bytecode.addOpcode(131);
/* 1654:     */         }
/* 1655:     */         else
/* 1656:     */         {
/* 1657:1510 */           badType(expr);
/* 1658:     */         }
/* 1659:     */       }
/* 1660:1513 */       else if (token == 43)
/* 1661:     */       {
/* 1662:1514 */         if (type == -1) {
/* 1663:1515 */           badType(expr);
/* 1664:     */         }
/* 1665:     */       }
/* 1666:     */       else {
/* 1667:1520 */         fatal();
/* 1668:     */       }
/* 1669:     */     }
/* 1670:     */   }
/* 1671:     */   
/* 1672:     */   protected static void badType(Expr expr)
/* 1673:     */     throws CompileError
/* 1674:     */   {
/* 1675:1525 */     throw new CompileError("invalid type for " + expr.getName());
/* 1676:     */   }
/* 1677:     */   
/* 1678:     */   public abstract void atCallExpr(CallExpr paramCallExpr)
/* 1679:     */     throws CompileError;
/* 1680:     */   
/* 1681:     */   protected abstract void atFieldRead(ASTree paramASTree)
/* 1682:     */     throws CompileError;
/* 1683:     */   
/* 1684:     */   public void atClassObject(Expr expr)
/* 1685:     */     throws CompileError
/* 1686:     */   {
/* 1687:1533 */     ASTree op1 = expr.oprand1();
/* 1688:1534 */     if (!(op1 instanceof Symbol)) {
/* 1689:1535 */       throw new CompileError("fatal error: badly parsed .class expr");
/* 1690:     */     }
/* 1691:1537 */     String cname = ((Symbol)op1).get();
/* 1692:1538 */     if (cname.startsWith("["))
/* 1693:     */     {
/* 1694:1539 */       int i = cname.indexOf("[L");
/* 1695:1540 */       if (i >= 0)
/* 1696:     */       {
/* 1697:1541 */         String name = cname.substring(i + 2, cname.length() - 1);
/* 1698:1542 */         String name2 = resolveClassName(name);
/* 1699:1543 */         if (!name.equals(name2))
/* 1700:     */         {
/* 1701:1548 */           name2 = MemberResolver.jvmToJavaName(name2);
/* 1702:1549 */           StringBuffer sbuf = new StringBuffer();
/* 1703:1550 */           while (i-- >= 0) {
/* 1704:1551 */             sbuf.append('[');
/* 1705:     */           }
/* 1706:1553 */           sbuf.append('L').append(name2).append(';');
/* 1707:1554 */           cname = sbuf.toString();
/* 1708:     */         }
/* 1709:     */       }
/* 1710:     */     }
/* 1711:     */     else
/* 1712:     */     {
/* 1713:1559 */       cname = resolveClassName(MemberResolver.javaToJvmName(cname));
/* 1714:1560 */       cname = MemberResolver.jvmToJavaName(cname);
/* 1715:     */     }
/* 1716:1563 */     atClassObject2(cname);
/* 1717:1564 */     this.exprType = 307;
/* 1718:1565 */     this.arrayDim = 0;
/* 1719:1566 */     this.className = "java/lang/Class";
/* 1720:     */   }
/* 1721:     */   
/* 1722:     */   protected void atClassObject2(String cname)
/* 1723:     */     throws CompileError
/* 1724:     */   {
/* 1725:1572 */     int start = this.bytecode.currentPc();
/* 1726:1573 */     this.bytecode.addLdc(cname);
/* 1727:1574 */     this.bytecode.addInvokestatic("java.lang.Class", "forName", "(Ljava/lang/String;)Ljava/lang/Class;");
/* 1728:     */     
/* 1729:1576 */     int end = this.bytecode.currentPc();
/* 1730:1577 */     this.bytecode.addOpcode(167);
/* 1731:1578 */     int pc = this.bytecode.currentPc();
/* 1732:1579 */     this.bytecode.addIndex(0);
/* 1733:     */     
/* 1734:1581 */     this.bytecode.addExceptionHandler(start, end, this.bytecode.currentPc(), "java.lang.ClassNotFoundException");
/* 1735:     */     
/* 1736:     */ 
/* 1737:     */ 
/* 1738:     */ 
/* 1739:     */ 
/* 1740:     */ 
/* 1741:     */ 
/* 1742:     */ 
/* 1743:     */ 
/* 1744:     */ 
/* 1745:     */ 
/* 1746:     */ 
/* 1747:     */ 
/* 1748:     */ 
/* 1749:     */ 
/* 1750:     */ 
/* 1751:     */ 
/* 1752:     */ 
/* 1753:1600 */     this.bytecode.growStack(1);
/* 1754:1601 */     this.bytecode.addInvokestatic("javassist.runtime.DotClass", "fail", "(Ljava/lang/ClassNotFoundException;)Ljava/lang/NoClassDefFoundError;");
/* 1755:     */     
/* 1756:     */ 
/* 1757:1604 */     this.bytecode.addOpcode(191);
/* 1758:1605 */     this.bytecode.write16bit(pc, this.bytecode.currentPc() - pc + 1);
/* 1759:     */   }
/* 1760:     */   
/* 1761:     */   public void atArrayRead(ASTree array, ASTree index)
/* 1762:     */     throws CompileError
/* 1763:     */   {
/* 1764:1611 */     arrayAccess(array, index);
/* 1765:1612 */     this.bytecode.addOpcode(getArrayReadOp(this.exprType, this.arrayDim));
/* 1766:     */   }
/* 1767:     */   
/* 1768:     */   protected void arrayAccess(ASTree array, ASTree index)
/* 1769:     */     throws CompileError
/* 1770:     */   {
/* 1771:1618 */     array.accept(this);
/* 1772:1619 */     int type = this.exprType;
/* 1773:1620 */     int dim = this.arrayDim;
/* 1774:1621 */     if (dim == 0) {
/* 1775:1622 */       throw new CompileError("bad array access");
/* 1776:     */     }
/* 1777:1624 */     String cname = this.className;
/* 1778:     */     
/* 1779:1626 */     index.accept(this);
/* 1780:1627 */     if ((typePrecedence(this.exprType) != 3) || (this.arrayDim > 0)) {
/* 1781:1628 */       throw new CompileError("bad array index");
/* 1782:     */     }
/* 1783:1630 */     this.exprType = type;
/* 1784:1631 */     this.arrayDim = (dim - 1);
/* 1785:1632 */     this.className = cname;
/* 1786:     */   }
/* 1787:     */   
/* 1788:     */   protected static int getArrayReadOp(int type, int dim)
/* 1789:     */   {
/* 1790:1636 */     if (dim > 0) {
/* 1791:1637 */       return 50;
/* 1792:     */     }
/* 1793:1639 */     switch (type)
/* 1794:     */     {
/* 1795:     */     case 312: 
/* 1796:1641 */       return 49;
/* 1797:     */     case 317: 
/* 1798:1643 */       return 48;
/* 1799:     */     case 326: 
/* 1800:1645 */       return 47;
/* 1801:     */     case 324: 
/* 1802:1647 */       return 46;
/* 1803:     */     case 334: 
/* 1804:1649 */       return 53;
/* 1805:     */     case 306: 
/* 1806:1651 */       return 52;
/* 1807:     */     case 301: 
/* 1808:     */     case 303: 
/* 1809:1654 */       return 51;
/* 1810:     */     }
/* 1811:1656 */     return 50;
/* 1812:     */   }
/* 1813:     */   
/* 1814:     */   protected static int getArrayWriteOp(int type, int dim)
/* 1815:     */   {
/* 1816:1661 */     if (dim > 0) {
/* 1817:1662 */       return 83;
/* 1818:     */     }
/* 1819:1664 */     switch (type)
/* 1820:     */     {
/* 1821:     */     case 312: 
/* 1822:1666 */       return 82;
/* 1823:     */     case 317: 
/* 1824:1668 */       return 81;
/* 1825:     */     case 326: 
/* 1826:1670 */       return 80;
/* 1827:     */     case 324: 
/* 1828:1672 */       return 79;
/* 1829:     */     case 334: 
/* 1830:1674 */       return 86;
/* 1831:     */     case 306: 
/* 1832:1676 */       return 85;
/* 1833:     */     case 301: 
/* 1834:     */     case 303: 
/* 1835:1679 */       return 84;
/* 1836:     */     }
/* 1837:1681 */     return 83;
/* 1838:     */   }
/* 1839:     */   
/* 1840:     */   private void atPlusPlus(int token, ASTree oprand, Expr expr, boolean doDup)
/* 1841:     */     throws CompileError
/* 1842:     */   {
/* 1843:1688 */     boolean isPost = oprand == null;
/* 1844:1689 */     if (isPost) {
/* 1845:1690 */       oprand = expr.oprand2();
/* 1846:     */     }
/* 1847:1692 */     if ((oprand instanceof Variable))
/* 1848:     */     {
/* 1849:1693 */       Declarator d = ((Variable)oprand).getDeclarator();
/* 1850:1694 */       int t = this.exprType = d.getType();
/* 1851:1695 */       this.arrayDim = d.getArrayDim();
/* 1852:1696 */       int var = getLocalVar(d);
/* 1853:1697 */       if (this.arrayDim > 0) {
/* 1854:1698 */         badType(expr);
/* 1855:     */       }
/* 1856:1700 */       if (t == 312)
/* 1857:     */       {
/* 1858:1701 */         this.bytecode.addDload(var);
/* 1859:1702 */         if ((doDup) && (isPost)) {
/* 1860:1703 */           this.bytecode.addOpcode(92);
/* 1861:     */         }
/* 1862:1705 */         this.bytecode.addDconst(1.0D);
/* 1863:1706 */         this.bytecode.addOpcode(token == 362 ? 99 : 103);
/* 1864:1707 */         if ((doDup) && (!isPost)) {
/* 1865:1708 */           this.bytecode.addOpcode(92);
/* 1866:     */         }
/* 1867:1710 */         this.bytecode.addDstore(var);
/* 1868:     */       }
/* 1869:1712 */       else if (t == 326)
/* 1870:     */       {
/* 1871:1713 */         this.bytecode.addLload(var);
/* 1872:1714 */         if ((doDup) && (isPost)) {
/* 1873:1715 */           this.bytecode.addOpcode(92);
/* 1874:     */         }
/* 1875:1717 */         this.bytecode.addLconst(1L);
/* 1876:1718 */         this.bytecode.addOpcode(token == 362 ? 97 : 101);
/* 1877:1719 */         if ((doDup) && (!isPost)) {
/* 1878:1720 */           this.bytecode.addOpcode(92);
/* 1879:     */         }
/* 1880:1722 */         this.bytecode.addLstore(var);
/* 1881:     */       }
/* 1882:1724 */       else if (t == 317)
/* 1883:     */       {
/* 1884:1725 */         this.bytecode.addFload(var);
/* 1885:1726 */         if ((doDup) && (isPost)) {
/* 1886:1727 */           this.bytecode.addOpcode(89);
/* 1887:     */         }
/* 1888:1729 */         this.bytecode.addFconst(1.0F);
/* 1889:1730 */         this.bytecode.addOpcode(token == 362 ? 98 : 102);
/* 1890:1731 */         if ((doDup) && (!isPost)) {
/* 1891:1732 */           this.bytecode.addOpcode(89);
/* 1892:     */         }
/* 1893:1734 */         this.bytecode.addFstore(var);
/* 1894:     */       }
/* 1895:1736 */       else if ((t == 303) || (t == 306) || (t == 334) || (t == 324))
/* 1896:     */       {
/* 1897:1737 */         if ((doDup) && (isPost)) {
/* 1898:1738 */           this.bytecode.addIload(var);
/* 1899:     */         }
/* 1900:1740 */         int delta = token == 362 ? 1 : -1;
/* 1901:1741 */         if (var > 255)
/* 1902:     */         {
/* 1903:1742 */           this.bytecode.addOpcode(196);
/* 1904:1743 */           this.bytecode.addOpcode(132);
/* 1905:1744 */           this.bytecode.addIndex(var);
/* 1906:1745 */           this.bytecode.addIndex(delta);
/* 1907:     */         }
/* 1908:     */         else
/* 1909:     */         {
/* 1910:1748 */           this.bytecode.addOpcode(132);
/* 1911:1749 */           this.bytecode.add(var);
/* 1912:1750 */           this.bytecode.add(delta);
/* 1913:     */         }
/* 1914:1753 */         if ((doDup) && (!isPost)) {
/* 1915:1754 */           this.bytecode.addIload(var);
/* 1916:     */         }
/* 1917:     */       }
/* 1918:     */       else
/* 1919:     */       {
/* 1920:1757 */         badType(expr);
/* 1921:     */       }
/* 1922:     */     }
/* 1923:     */     else
/* 1924:     */     {
/* 1925:1760 */       if ((oprand instanceof Expr))
/* 1926:     */       {
/* 1927:1761 */         Expr e = (Expr)oprand;
/* 1928:1762 */         if (e.getOperator() == 65)
/* 1929:     */         {
/* 1930:1763 */           atArrayPlusPlus(token, isPost, e, doDup);
/* 1931:1764 */           return;
/* 1932:     */         }
/* 1933:     */       }
/* 1934:1768 */       atFieldPlusPlus(token, isPost, oprand, expr, doDup);
/* 1935:     */     }
/* 1936:     */   }
/* 1937:     */   
/* 1938:     */   public void atArrayPlusPlus(int token, boolean isPost, Expr expr, boolean doDup)
/* 1939:     */     throws CompileError
/* 1940:     */   {
/* 1941:1775 */     arrayAccess(expr.oprand1(), expr.oprand2());
/* 1942:1776 */     int t = this.exprType;
/* 1943:1777 */     int dim = this.arrayDim;
/* 1944:1778 */     if (dim > 0) {
/* 1945:1779 */       badType(expr);
/* 1946:     */     }
/* 1947:1781 */     this.bytecode.addOpcode(92);
/* 1948:1782 */     this.bytecode.addOpcode(getArrayReadOp(t, this.arrayDim));
/* 1949:1783 */     int dup_code = is2word(t, dim) ? 94 : 91;
/* 1950:1784 */     atPlusPlusCore(dup_code, doDup, token, isPost, expr);
/* 1951:1785 */     this.bytecode.addOpcode(getArrayWriteOp(t, dim));
/* 1952:     */   }
/* 1953:     */   
/* 1954:     */   protected void atPlusPlusCore(int dup_code, boolean doDup, int token, boolean isPost, Expr expr)
/* 1955:     */     throws CompileError
/* 1956:     */   {
/* 1957:1792 */     int t = this.exprType;
/* 1958:1794 */     if ((doDup) && (isPost)) {
/* 1959:1795 */       this.bytecode.addOpcode(dup_code);
/* 1960:     */     }
/* 1961:1797 */     if ((t == 324) || (t == 303) || (t == 306) || (t == 334))
/* 1962:     */     {
/* 1963:1798 */       this.bytecode.addIconst(1);
/* 1964:1799 */       this.bytecode.addOpcode(token == 362 ? 96 : 100);
/* 1965:1800 */       this.exprType = 324;
/* 1966:     */     }
/* 1967:1802 */     else if (t == 326)
/* 1968:     */     {
/* 1969:1803 */       this.bytecode.addLconst(1L);
/* 1970:1804 */       this.bytecode.addOpcode(token == 362 ? 97 : 101);
/* 1971:     */     }
/* 1972:1806 */     else if (t == 317)
/* 1973:     */     {
/* 1974:1807 */       this.bytecode.addFconst(1.0F);
/* 1975:1808 */       this.bytecode.addOpcode(token == 362 ? 98 : 102);
/* 1976:     */     }
/* 1977:1810 */     else if (t == 312)
/* 1978:     */     {
/* 1979:1811 */       this.bytecode.addDconst(1.0D);
/* 1980:1812 */       this.bytecode.addOpcode(token == 362 ? 99 : 103);
/* 1981:     */     }
/* 1982:     */     else
/* 1983:     */     {
/* 1984:1815 */       badType(expr);
/* 1985:     */     }
/* 1986:1817 */     if ((doDup) && (!isPost)) {
/* 1987:1818 */       this.bytecode.addOpcode(dup_code);
/* 1988:     */     }
/* 1989:     */   }
/* 1990:     */   
/* 1991:     */   protected abstract void atFieldPlusPlus(int paramInt, boolean paramBoolean1, ASTree paramASTree, Expr paramExpr, boolean paramBoolean2)
/* 1992:     */     throws CompileError;
/* 1993:     */   
/* 1994:     */   public abstract void atMember(Member paramMember)
/* 1995:     */     throws CompileError;
/* 1996:     */   
/* 1997:     */   public void atVariable(Variable v)
/* 1998:     */     throws CompileError
/* 1999:     */   {
/* 2000:1827 */     Declarator d = v.getDeclarator();
/* 2001:1828 */     this.exprType = d.getType();
/* 2002:1829 */     this.arrayDim = d.getArrayDim();
/* 2003:1830 */     this.className = d.getClassName();
/* 2004:1831 */     int var = getLocalVar(d);
/* 2005:1833 */     if (this.arrayDim > 0) {
/* 2006:1834 */       this.bytecode.addAload(var);
/* 2007:     */     } else {
/* 2008:1836 */       switch (this.exprType)
/* 2009:     */       {
/* 2010:     */       case 307: 
/* 2011:1838 */         this.bytecode.addAload(var);
/* 2012:1839 */         break;
/* 2013:     */       case 326: 
/* 2014:1841 */         this.bytecode.addLload(var);
/* 2015:1842 */         break;
/* 2016:     */       case 317: 
/* 2017:1844 */         this.bytecode.addFload(var);
/* 2018:1845 */         break;
/* 2019:     */       case 312: 
/* 2020:1847 */         this.bytecode.addDload(var);
/* 2021:1848 */         break;
/* 2022:     */       default: 
/* 2023:1850 */         this.bytecode.addIload(var);
/* 2024:     */       }
/* 2025:     */     }
/* 2026:     */   }
/* 2027:     */   
/* 2028:     */   public void atKeyword(Keyword k)
/* 2029:     */     throws CompileError
/* 2030:     */   {
/* 2031:1856 */     this.arrayDim = 0;
/* 2032:1857 */     int token = k.get();
/* 2033:1858 */     switch (token)
/* 2034:     */     {
/* 2035:     */     case 410: 
/* 2036:1860 */       this.bytecode.addIconst(1);
/* 2037:1861 */       this.exprType = 301;
/* 2038:1862 */       break;
/* 2039:     */     case 411: 
/* 2040:1864 */       this.bytecode.addIconst(0);
/* 2041:1865 */       this.exprType = 301;
/* 2042:1866 */       break;
/* 2043:     */     case 412: 
/* 2044:1868 */       this.bytecode.addOpcode(1);
/* 2045:1869 */       this.exprType = 412;
/* 2046:1870 */       break;
/* 2047:     */     case 336: 
/* 2048:     */     case 339: 
/* 2049:1873 */       if (this.inStaticMethod) {
/* 2050:1874 */         throw new CompileError("not-available: " + (token == 339 ? "this" : "super"));
/* 2051:     */       }
/* 2052:1877 */       this.bytecode.addAload(0);
/* 2053:1878 */       this.exprType = 307;
/* 2054:1879 */       if (token == 339) {
/* 2055:1880 */         this.className = getThisName();
/* 2056:     */       } else {
/* 2057:1882 */         this.className = getSuperName();
/* 2058:     */       }
/* 2059:1883 */       break;
/* 2060:     */     default: 
/* 2061:1885 */       fatal();
/* 2062:     */     }
/* 2063:     */   }
/* 2064:     */   
/* 2065:     */   public void atStringL(StringL s)
/* 2066:     */     throws CompileError
/* 2067:     */   {
/* 2068:1890 */     this.exprType = 307;
/* 2069:1891 */     this.arrayDim = 0;
/* 2070:1892 */     this.className = "java/lang/String";
/* 2071:1893 */     this.bytecode.addLdc(s.get());
/* 2072:     */   }
/* 2073:     */   
/* 2074:     */   public void atIntConst(IntConst i)
/* 2075:     */     throws CompileError
/* 2076:     */   {
/* 2077:1897 */     this.arrayDim = 0;
/* 2078:1898 */     long value = i.get();
/* 2079:1899 */     int type = i.getType();
/* 2080:1900 */     if ((type == 402) || (type == 401))
/* 2081:     */     {
/* 2082:1901 */       this.exprType = (type == 402 ? 324 : 306);
/* 2083:1902 */       this.bytecode.addIconst((int)value);
/* 2084:     */     }
/* 2085:     */     else
/* 2086:     */     {
/* 2087:1905 */       this.exprType = 326;
/* 2088:1906 */       this.bytecode.addLconst(value);
/* 2089:     */     }
/* 2090:     */   }
/* 2091:     */   
/* 2092:     */   public void atDoubleConst(DoubleConst d)
/* 2093:     */     throws CompileError
/* 2094:     */   {
/* 2095:1911 */     this.arrayDim = 0;
/* 2096:1912 */     if (d.getType() == 405)
/* 2097:     */     {
/* 2098:1913 */       this.exprType = 312;
/* 2099:1914 */       this.bytecode.addDconst(d.get());
/* 2100:     */     }
/* 2101:     */     else
/* 2102:     */     {
/* 2103:1917 */       this.exprType = 317;
/* 2104:1918 */       this.bytecode.addFconst((float)d.get());
/* 2105:     */     }
/* 2106:     */   }
/* 2107:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.compiler.CodeGen
 * JD-Core Version:    0.7.0.1
 */