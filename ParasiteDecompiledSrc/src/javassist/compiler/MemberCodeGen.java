/*    1:     */ package javassist.compiler;
/*    2:     */ 
/*    3:     */ import java.util.ArrayList;
/*    4:     */ import javassist.ClassPool;
/*    5:     */ import javassist.CtClass;
/*    6:     */ import javassist.CtField;
/*    7:     */ import javassist.CtMethod;
/*    8:     */ import javassist.Modifier;
/*    9:     */ import javassist.NotFoundException;
/*   10:     */ import javassist.bytecode.AccessFlag;
/*   11:     */ import javassist.bytecode.Bytecode;
/*   12:     */ import javassist.bytecode.ClassFile;
/*   13:     */ import javassist.bytecode.ConstPool;
/*   14:     */ import javassist.bytecode.Descriptor;
/*   15:     */ import javassist.bytecode.FieldInfo;
/*   16:     */ import javassist.bytecode.MethodInfo;
/*   17:     */ import javassist.compiler.ast.ASTList;
/*   18:     */ import javassist.compiler.ast.ASTree;
/*   19:     */ import javassist.compiler.ast.ArrayInit;
/*   20:     */ import javassist.compiler.ast.CallExpr;
/*   21:     */ import javassist.compiler.ast.Declarator;
/*   22:     */ import javassist.compiler.ast.Expr;
/*   23:     */ import javassist.compiler.ast.Keyword;
/*   24:     */ import javassist.compiler.ast.Member;
/*   25:     */ import javassist.compiler.ast.MethodDecl;
/*   26:     */ import javassist.compiler.ast.NewExpr;
/*   27:     */ import javassist.compiler.ast.Pair;
/*   28:     */ import javassist.compiler.ast.Stmnt;
/*   29:     */ import javassist.compiler.ast.Symbol;
/*   30:     */ 
/*   31:     */ public class MemberCodeGen
/*   32:     */   extends CodeGen
/*   33:     */ {
/*   34:     */   protected MemberResolver resolver;
/*   35:     */   protected CtClass thisClass;
/*   36:     */   protected MethodInfo thisMethod;
/*   37:     */   protected boolean resultStatic;
/*   38:     */   
/*   39:     */   public MemberCodeGen(Bytecode b, CtClass cc, ClassPool cp)
/*   40:     */   {
/*   41:  34 */     super(b);
/*   42:  35 */     this.resolver = new MemberResolver(cp);
/*   43:  36 */     this.thisClass = cc;
/*   44:  37 */     this.thisMethod = null;
/*   45:     */   }
/*   46:     */   
/*   47:     */   public int getMajorVersion()
/*   48:     */   {
/*   49:  45 */     ClassFile cf = this.thisClass.getClassFile2();
/*   50:  46 */     if (cf == null) {
/*   51:  47 */       return ClassFile.MAJOR_VERSION;
/*   52:     */     }
/*   53:  49 */     return cf.getMajorVersion();
/*   54:     */   }
/*   55:     */   
/*   56:     */   public void setThisMethod(CtMethod m)
/*   57:     */   {
/*   58:  56 */     this.thisMethod = m.getMethodInfo2();
/*   59:  57 */     if (this.typeChecker != null) {
/*   60:  58 */       this.typeChecker.setThisMethod(this.thisMethod);
/*   61:     */     }
/*   62:     */   }
/*   63:     */   
/*   64:     */   public CtClass getThisClass()
/*   65:     */   {
/*   66:  61 */     return this.thisClass;
/*   67:     */   }
/*   68:     */   
/*   69:     */   protected String getThisName()
/*   70:     */   {
/*   71:  67 */     return MemberResolver.javaToJvmName(this.thisClass.getName());
/*   72:     */   }
/*   73:     */   
/*   74:     */   protected String getSuperName()
/*   75:     */     throws CompileError
/*   76:     */   {
/*   77:  74 */     return MemberResolver.javaToJvmName(MemberResolver.getSuperclass(this.thisClass).getName());
/*   78:     */   }
/*   79:     */   
/*   80:     */   protected void insertDefaultSuperCall()
/*   81:     */     throws CompileError
/*   82:     */   {
/*   83:  79 */     this.bytecode.addAload(0);
/*   84:  80 */     this.bytecode.addInvokespecial(MemberResolver.getSuperclass(this.thisClass), "<init>", "()V");
/*   85:     */   }
/*   86:     */   
/*   87:     */   static class JsrHook
/*   88:     */     extends CodeGen.ReturnHook
/*   89:     */   {
/*   90:     */     ArrayList jsrList;
/*   91:     */     CodeGen cgen;
/*   92:     */     int var;
/*   93:     */     
/*   94:     */     JsrHook(CodeGen gen)
/*   95:     */     {
/*   96:  90 */       super();
/*   97:  91 */       this.jsrList = new ArrayList();
/*   98:  92 */       this.cgen = gen;
/*   99:  93 */       this.var = -1;
/*  100:     */     }
/*  101:     */     
/*  102:     */     private int getVar(int size)
/*  103:     */     {
/*  104:  97 */       if (this.var < 0)
/*  105:     */       {
/*  106:  98 */         this.var = this.cgen.getMaxLocals();
/*  107:  99 */         this.cgen.incMaxLocals(size);
/*  108:     */       }
/*  109: 102 */       return this.var;
/*  110:     */     }
/*  111:     */     
/*  112:     */     private void jsrJmp(Bytecode b)
/*  113:     */     {
/*  114: 106 */       b.addOpcode(167);
/*  115: 107 */       this.jsrList.add(new int[] { b.currentPc(), this.var });
/*  116: 108 */       b.addIndex(0);
/*  117:     */     }
/*  118:     */     
/*  119:     */     protected boolean doit(Bytecode b, int opcode)
/*  120:     */     {
/*  121: 112 */       switch (opcode)
/*  122:     */       {
/*  123:     */       case 177: 
/*  124: 114 */         jsrJmp(b);
/*  125: 115 */         break;
/*  126:     */       case 176: 
/*  127: 117 */         b.addAstore(getVar(1));
/*  128: 118 */         jsrJmp(b);
/*  129: 119 */         b.addAload(this.var);
/*  130: 120 */         break;
/*  131:     */       case 172: 
/*  132: 122 */         b.addIstore(getVar(1));
/*  133: 123 */         jsrJmp(b);
/*  134: 124 */         b.addIload(this.var);
/*  135: 125 */         break;
/*  136:     */       case 173: 
/*  137: 127 */         b.addLstore(getVar(2));
/*  138: 128 */         jsrJmp(b);
/*  139: 129 */         b.addLload(this.var);
/*  140: 130 */         break;
/*  141:     */       case 175: 
/*  142: 132 */         b.addDstore(getVar(2));
/*  143: 133 */         jsrJmp(b);
/*  144: 134 */         b.addDload(this.var);
/*  145: 135 */         break;
/*  146:     */       case 174: 
/*  147: 137 */         b.addFstore(getVar(1));
/*  148: 138 */         jsrJmp(b);
/*  149: 139 */         b.addFload(this.var);
/*  150: 140 */         break;
/*  151:     */       default: 
/*  152: 142 */         throw new RuntimeException("fatal");
/*  153:     */       }
/*  154: 145 */       return false;
/*  155:     */     }
/*  156:     */   }
/*  157:     */   
/*  158:     */   static class JsrHook2
/*  159:     */     extends CodeGen.ReturnHook
/*  160:     */   {
/*  161:     */     int var;
/*  162:     */     int target;
/*  163:     */     
/*  164:     */     JsrHook2(CodeGen gen, int[] retTarget)
/*  165:     */     {
/*  166: 154 */       super();
/*  167: 155 */       this.target = retTarget[0];
/*  168: 156 */       this.var = retTarget[1];
/*  169:     */     }
/*  170:     */     
/*  171:     */     protected boolean doit(Bytecode b, int opcode)
/*  172:     */     {
/*  173: 160 */       switch (opcode)
/*  174:     */       {
/*  175:     */       case 177: 
/*  176:     */         break;
/*  177:     */       case 176: 
/*  178: 164 */         b.addAstore(this.var);
/*  179: 165 */         break;
/*  180:     */       case 172: 
/*  181: 167 */         b.addIstore(this.var);
/*  182: 168 */         break;
/*  183:     */       case 173: 
/*  184: 170 */         b.addLstore(this.var);
/*  185: 171 */         break;
/*  186:     */       case 175: 
/*  187: 173 */         b.addDstore(this.var);
/*  188: 174 */         break;
/*  189:     */       case 174: 
/*  190: 176 */         b.addFstore(this.var);
/*  191: 177 */         break;
/*  192:     */       default: 
/*  193: 179 */         throw new RuntimeException("fatal");
/*  194:     */       }
/*  195: 182 */       b.addOpcode(167);
/*  196: 183 */       b.addIndex(this.target - b.currentPc() + 3);
/*  197: 184 */       return true;
/*  198:     */     }
/*  199:     */   }
/*  200:     */   
/*  201:     */   protected void atTryStmnt(Stmnt st)
/*  202:     */     throws CompileError
/*  203:     */   {
/*  204: 189 */     Bytecode bc = this.bytecode;
/*  205: 190 */     Stmnt body = (Stmnt)st.getLeft();
/*  206: 191 */     if (body == null) {
/*  207: 192 */       return;
/*  208:     */     }
/*  209: 194 */     ASTList catchList = (ASTList)st.getRight().getLeft();
/*  210: 195 */     Stmnt finallyBlock = (Stmnt)st.getRight().getRight().getLeft();
/*  211: 196 */     ArrayList gotoList = new ArrayList();
/*  212:     */     
/*  213: 198 */     JsrHook jsrHook = null;
/*  214: 199 */     if (finallyBlock != null) {
/*  215: 200 */       jsrHook = new JsrHook(this);
/*  216:     */     }
/*  217: 202 */     int start = bc.currentPc();
/*  218: 203 */     body.accept(this);
/*  219: 204 */     int end = bc.currentPc();
/*  220: 205 */     if (start == end) {
/*  221: 206 */       throw new CompileError("empty try block");
/*  222:     */     }
/*  223: 208 */     boolean tryNotReturn = !this.hasReturned;
/*  224: 209 */     if (tryNotReturn)
/*  225:     */     {
/*  226: 210 */       bc.addOpcode(167);
/*  227: 211 */       gotoList.add(new Integer(bc.currentPc()));
/*  228: 212 */       bc.addIndex(0);
/*  229:     */     }
/*  230: 215 */     int var = getMaxLocals();
/*  231: 216 */     incMaxLocals(1);
/*  232: 217 */     while (catchList != null)
/*  233:     */     {
/*  234: 219 */       Pair p = (Pair)catchList.head();
/*  235: 220 */       catchList = catchList.tail();
/*  236: 221 */       Declarator decl = (Declarator)p.getLeft();
/*  237: 222 */       Stmnt block = (Stmnt)p.getRight();
/*  238:     */       
/*  239: 224 */       decl.setLocalVar(var);
/*  240:     */       
/*  241: 226 */       CtClass type = this.resolver.lookupClassByJvmName(decl.getClassName());
/*  242: 227 */       decl.setClassName(MemberResolver.javaToJvmName(type.getName()));
/*  243: 228 */       bc.addExceptionHandler(start, end, bc.currentPc(), type);
/*  244: 229 */       bc.growStack(1);
/*  245: 230 */       bc.addAstore(var);
/*  246: 231 */       this.hasReturned = false;
/*  247: 232 */       if (block != null) {
/*  248: 233 */         block.accept(this);
/*  249:     */       }
/*  250: 235 */       if (!this.hasReturned)
/*  251:     */       {
/*  252: 236 */         bc.addOpcode(167);
/*  253: 237 */         gotoList.add(new Integer(bc.currentPc()));
/*  254: 238 */         bc.addIndex(0);
/*  255: 239 */         tryNotReturn = true;
/*  256:     */       }
/*  257:     */     }
/*  258: 243 */     if (finallyBlock != null)
/*  259:     */     {
/*  260: 244 */       jsrHook.remove(this);
/*  261:     */       
/*  262: 246 */       int pcAnyCatch = bc.currentPc();
/*  263: 247 */       bc.addExceptionHandler(start, pcAnyCatch, pcAnyCatch, 0);
/*  264: 248 */       bc.growStack(1);
/*  265: 249 */       bc.addAstore(var);
/*  266: 250 */       this.hasReturned = false;
/*  267: 251 */       finallyBlock.accept(this);
/*  268: 252 */       if (!this.hasReturned)
/*  269:     */       {
/*  270: 253 */         bc.addAload(var);
/*  271: 254 */         bc.addOpcode(191);
/*  272:     */       }
/*  273: 257 */       addFinally(jsrHook.jsrList, finallyBlock);
/*  274:     */     }
/*  275: 260 */     int pcEnd = bc.currentPc();
/*  276: 261 */     patchGoto(gotoList, pcEnd);
/*  277: 262 */     this.hasReturned = (!tryNotReturn);
/*  278: 263 */     if ((finallyBlock != null) && 
/*  279: 264 */       (tryNotReturn)) {
/*  280: 265 */       finallyBlock.accept(this);
/*  281:     */     }
/*  282:     */   }
/*  283:     */   
/*  284:     */   private void addFinally(ArrayList returnList, Stmnt finallyBlock)
/*  285:     */     throws CompileError
/*  286:     */   {
/*  287: 275 */     Bytecode bc = this.bytecode;
/*  288: 276 */     int n = returnList.size();
/*  289: 277 */     for (int i = 0; i < n; i++)
/*  290:     */     {
/*  291: 278 */       int[] ret = (int[])returnList.get(i);
/*  292: 279 */       int pc = ret[0];
/*  293: 280 */       bc.write16bit(pc, bc.currentPc() - pc + 1);
/*  294: 281 */       CodeGen.ReturnHook hook = new JsrHook2(this, ret);
/*  295: 282 */       finallyBlock.accept(this);
/*  296: 283 */       hook.remove(this);
/*  297: 284 */       if (!this.hasReturned)
/*  298:     */       {
/*  299: 285 */         bc.addOpcode(167);
/*  300: 286 */         bc.addIndex(pc + 3 - bc.currentPc());
/*  301:     */       }
/*  302:     */     }
/*  303:     */   }
/*  304:     */   
/*  305:     */   public void atNewExpr(NewExpr expr)
/*  306:     */     throws CompileError
/*  307:     */   {
/*  308: 292 */     if (expr.isArray())
/*  309:     */     {
/*  310: 293 */       atNewArrayExpr(expr);
/*  311:     */     }
/*  312:     */     else
/*  313:     */     {
/*  314: 295 */       CtClass clazz = this.resolver.lookupClassByName(expr.getClassName());
/*  315: 296 */       String cname = clazz.getName();
/*  316: 297 */       ASTList args = expr.getArguments();
/*  317: 298 */       this.bytecode.addNew(cname);
/*  318: 299 */       this.bytecode.addOpcode(89);
/*  319:     */       
/*  320: 301 */       atMethodCallCore(clazz, "<init>", args, false, true, -1, null);
/*  321:     */       
/*  322:     */ 
/*  323: 304 */       this.exprType = 307;
/*  324: 305 */       this.arrayDim = 0;
/*  325: 306 */       this.className = MemberResolver.javaToJvmName(cname);
/*  326:     */     }
/*  327:     */   }
/*  328:     */   
/*  329:     */   public void atNewArrayExpr(NewExpr expr)
/*  330:     */     throws CompileError
/*  331:     */   {
/*  332: 311 */     int type = expr.getArrayType();
/*  333: 312 */     ASTList size = expr.getArraySize();
/*  334: 313 */     ASTList classname = expr.getClassName();
/*  335: 314 */     ArrayInit init = expr.getInitializer();
/*  336: 315 */     if (size.length() > 1)
/*  337:     */     {
/*  338: 316 */       if (init != null) {
/*  339: 317 */         throw new CompileError("sorry, multi-dimensional array initializer for new is not supported");
/*  340:     */       }
/*  341: 321 */       atMultiNewArray(type, classname, size);
/*  342: 322 */       return;
/*  343:     */     }
/*  344: 325 */     ASTree sizeExpr = size.head();
/*  345: 326 */     atNewArrayExpr2(type, sizeExpr, Declarator.astToClassName(classname, '/'), init);
/*  346:     */   }
/*  347:     */   
/*  348:     */   private void atNewArrayExpr2(int type, ASTree sizeExpr, String jvmClassname, ArrayInit init)
/*  349:     */     throws CompileError
/*  350:     */   {
/*  351: 331 */     if (init == null)
/*  352:     */     {
/*  353: 332 */       if (sizeExpr == null) {
/*  354: 333 */         throw new CompileError("no array size");
/*  355:     */       }
/*  356: 335 */       sizeExpr.accept(this);
/*  357:     */     }
/*  358: 337 */     else if (sizeExpr == null)
/*  359:     */     {
/*  360: 338 */       int s = init.length();
/*  361: 339 */       this.bytecode.addIconst(s);
/*  362:     */     }
/*  363:     */     else
/*  364:     */     {
/*  365: 342 */       throw new CompileError("unnecessary array size specified for new");
/*  366:     */     }
/*  367:     */     String elementClass;
/*  368: 345 */     if (type == 307)
/*  369:     */     {
/*  370: 346 */       String elementClass = resolveClassName(jvmClassname);
/*  371: 347 */       this.bytecode.addAnewarray(MemberResolver.jvmToJavaName(elementClass));
/*  372:     */     }
/*  373:     */     else
/*  374:     */     {
/*  375: 350 */       elementClass = null;
/*  376: 351 */       int atype = 0;
/*  377: 352 */       switch (type)
/*  378:     */       {
/*  379:     */       case 301: 
/*  380: 354 */         atype = 4;
/*  381: 355 */         break;
/*  382:     */       case 306: 
/*  383: 357 */         atype = 5;
/*  384: 358 */         break;
/*  385:     */       case 317: 
/*  386: 360 */         atype = 6;
/*  387: 361 */         break;
/*  388:     */       case 312: 
/*  389: 363 */         atype = 7;
/*  390: 364 */         break;
/*  391:     */       case 303: 
/*  392: 366 */         atype = 8;
/*  393: 367 */         break;
/*  394:     */       case 334: 
/*  395: 369 */         atype = 9;
/*  396: 370 */         break;
/*  397:     */       case 324: 
/*  398: 372 */         atype = 10;
/*  399: 373 */         break;
/*  400:     */       case 326: 
/*  401: 375 */         atype = 11;
/*  402: 376 */         break;
/*  403:     */       default: 
/*  404: 378 */         badNewExpr();
/*  405:     */       }
/*  406: 382 */       this.bytecode.addOpcode(188);
/*  407: 383 */       this.bytecode.add(atype);
/*  408:     */     }
/*  409: 386 */     if (init != null)
/*  410:     */     {
/*  411: 387 */       int s = init.length();
/*  412: 388 */       ASTList list = init;
/*  413: 389 */       for (int i = 0; i < s; i++)
/*  414:     */       {
/*  415: 390 */         this.bytecode.addOpcode(89);
/*  416: 391 */         this.bytecode.addIconst(i);
/*  417: 392 */         list.head().accept(this);
/*  418: 393 */         if (!isRefType(type)) {
/*  419: 394 */           atNumCastExpr(this.exprType, type);
/*  420:     */         }
/*  421: 396 */         this.bytecode.addOpcode(getArrayWriteOp(type, 0));
/*  422: 397 */         list = list.tail();
/*  423:     */       }
/*  424:     */     }
/*  425: 401 */     this.exprType = type;
/*  426: 402 */     this.arrayDim = 1;
/*  427: 403 */     this.className = elementClass;
/*  428:     */   }
/*  429:     */   
/*  430:     */   private static void badNewExpr()
/*  431:     */     throws CompileError
/*  432:     */   {
/*  433: 407 */     throw new CompileError("bad new expression");
/*  434:     */   }
/*  435:     */   
/*  436:     */   protected void atArrayVariableAssign(ArrayInit init, int varType, int varArray, String varClass)
/*  437:     */     throws CompileError
/*  438:     */   {
/*  439: 412 */     atNewArrayExpr2(varType, null, varClass, init);
/*  440:     */   }
/*  441:     */   
/*  442:     */   public void atArrayInit(ArrayInit init)
/*  443:     */     throws CompileError
/*  444:     */   {
/*  445: 416 */     throw new CompileError("array initializer is not supported");
/*  446:     */   }
/*  447:     */   
/*  448:     */   protected void atMultiNewArray(int type, ASTList classname, ASTList size)
/*  449:     */     throws CompileError
/*  450:     */   {
/*  451: 423 */     int dim = size.length();
/*  452: 424 */     for (int count = 0; size != null; size = size.tail())
/*  453:     */     {
/*  454: 425 */       ASTree s = size.head();
/*  455: 426 */       if (s == null) {
/*  456:     */         break;
/*  457:     */       }
/*  458: 429 */       count++;
/*  459: 430 */       s.accept(this);
/*  460: 431 */       if (this.exprType != 324) {
/*  461: 432 */         throw new CompileError("bad type for array size");
/*  462:     */       }
/*  463:     */     }
/*  464: 436 */     this.exprType = type;
/*  465: 437 */     this.arrayDim = dim;
/*  466:     */     String desc;
/*  467:     */     String desc;
/*  468: 438 */     if (type == 307)
/*  469:     */     {
/*  470: 439 */       this.className = resolveClassName(classname);
/*  471: 440 */       desc = toJvmArrayName(this.className, dim);
/*  472:     */     }
/*  473:     */     else
/*  474:     */     {
/*  475: 443 */       desc = toJvmTypeName(type, dim);
/*  476:     */     }
/*  477: 445 */     this.bytecode.addMultiNewarray(desc, count);
/*  478:     */   }
/*  479:     */   
/*  480:     */   public void atCallExpr(CallExpr expr)
/*  481:     */     throws CompileError
/*  482:     */   {
/*  483: 449 */     String mname = null;
/*  484: 450 */     CtClass targetClass = null;
/*  485: 451 */     ASTree method = expr.oprand1();
/*  486: 452 */     ASTList args = (ASTList)expr.oprand2();
/*  487: 453 */     boolean isStatic = false;
/*  488: 454 */     boolean isSpecial = false;
/*  489: 455 */     int aload0pos = -1;
/*  490:     */     
/*  491: 457 */     MemberResolver.Method cached = expr.getMethod();
/*  492: 458 */     if ((method instanceof Member))
/*  493:     */     {
/*  494: 459 */       mname = ((Member)method).get();
/*  495: 460 */       targetClass = this.thisClass;
/*  496: 461 */       if ((this.inStaticMethod) || ((cached != null) && (cached.isStatic())))
/*  497:     */       {
/*  498: 462 */         isStatic = true;
/*  499:     */       }
/*  500:     */       else
/*  501:     */       {
/*  502: 464 */         aload0pos = this.bytecode.currentPc();
/*  503: 465 */         this.bytecode.addAload(0);
/*  504:     */       }
/*  505:     */     }
/*  506: 468 */     else if ((method instanceof Keyword))
/*  507:     */     {
/*  508: 469 */       isSpecial = true;
/*  509: 470 */       mname = "<init>";
/*  510: 471 */       targetClass = this.thisClass;
/*  511: 472 */       if (this.inStaticMethod) {
/*  512: 473 */         throw new CompileError("a constructor cannot be static");
/*  513:     */       }
/*  514: 475 */       this.bytecode.addAload(0);
/*  515: 477 */       if (((Keyword)method).get() == 336) {
/*  516: 478 */         targetClass = MemberResolver.getSuperclass(targetClass);
/*  517:     */       }
/*  518:     */     }
/*  519: 480 */     else if ((method instanceof Expr))
/*  520:     */     {
/*  521: 481 */       Expr e = (Expr)method;
/*  522: 482 */       mname = ((Symbol)e.oprand2()).get();
/*  523: 483 */       int op = e.getOperator();
/*  524: 484 */       if (op == 35)
/*  525:     */       {
/*  526: 485 */         targetClass = this.resolver.lookupClass(((Symbol)e.oprand1()).get(), false);
/*  527:     */         
/*  528: 487 */         isStatic = true;
/*  529:     */       }
/*  530: 489 */       else if (op == 46)
/*  531:     */       {
/*  532: 490 */         ASTree target = e.oprand1();
/*  533: 491 */         if (((target instanceof Keyword)) && 
/*  534: 492 */           (((Keyword)target).get() == 336)) {
/*  535: 493 */           isSpecial = true;
/*  536:     */         }
/*  537:     */         try
/*  538:     */         {
/*  539: 496 */           target.accept(this);
/*  540:     */         }
/*  541:     */         catch (NoFieldException nfe)
/*  542:     */         {
/*  543: 499 */           if (nfe.getExpr() != target) {
/*  544: 500 */             throw nfe;
/*  545:     */           }
/*  546: 503 */           this.exprType = 307;
/*  547: 504 */           this.arrayDim = 0;
/*  548: 505 */           this.className = nfe.getField();
/*  549: 506 */           this.resolver.recordPackage(this.className);
/*  550: 507 */           isStatic = true;
/*  551:     */         }
/*  552: 510 */         if (this.arrayDim > 0) {
/*  553: 511 */           targetClass = this.resolver.lookupClass("java.lang.Object", true);
/*  554: 512 */         } else if (this.exprType == 307) {
/*  555: 513 */           targetClass = this.resolver.lookupClassByJvmName(this.className);
/*  556:     */         } else {
/*  557: 515 */           badMethod();
/*  558:     */         }
/*  559:     */       }
/*  560:     */       else
/*  561:     */       {
/*  562: 518 */         badMethod();
/*  563:     */       }
/*  564:     */     }
/*  565:     */     else
/*  566:     */     {
/*  567: 521 */       fatal();
/*  568:     */     }
/*  569: 523 */     atMethodCallCore(targetClass, mname, args, isStatic, isSpecial, aload0pos, cached);
/*  570:     */   }
/*  571:     */   
/*  572:     */   private static void badMethod()
/*  573:     */     throws CompileError
/*  574:     */   {
/*  575: 528 */     throw new CompileError("bad method");
/*  576:     */   }
/*  577:     */   
/*  578:     */   public void atMethodCallCore(CtClass targetClass, String mname, ASTList args, boolean isStatic, boolean isSpecial, int aload0pos, MemberResolver.Method found)
/*  579:     */     throws CompileError
/*  580:     */   {
/*  581: 542 */     int nargs = getMethodArgsLength(args);
/*  582: 543 */     int[] types = new int[nargs];
/*  583: 544 */     int[] dims = new int[nargs];
/*  584: 545 */     String[] cnames = new String[nargs];
/*  585: 547 */     if ((!isStatic) && (found != null) && (found.isStatic()))
/*  586:     */     {
/*  587: 548 */       this.bytecode.addOpcode(87);
/*  588: 549 */       isStatic = true;
/*  589:     */     }
/*  590: 552 */     int stack = this.bytecode.getStackDepth();
/*  591:     */     
/*  592:     */ 
/*  593: 555 */     atMethodArgs(args, types, dims, cnames);
/*  594:     */     
/*  595:     */ 
/*  596: 558 */     int count = this.bytecode.getStackDepth() - stack + 1;
/*  597: 560 */     if (found == null) {
/*  598: 561 */       found = this.resolver.lookupMethod(targetClass, this.thisClass, this.thisMethod, mname, types, dims, cnames);
/*  599:     */     }
/*  600: 564 */     if (found == null)
/*  601:     */     {
/*  602:     */       String msg;
/*  603:     */       String msg;
/*  604: 566 */       if (mname.equals("<init>")) {
/*  605: 567 */         msg = "constructor not found";
/*  606:     */       } else {
/*  607: 569 */         msg = "Method " + mname + " not found in " + targetClass.getName();
/*  608:     */       }
/*  609: 572 */       throw new CompileError(msg);
/*  610:     */     }
/*  611: 575 */     atMethodCallCore2(targetClass, mname, isStatic, isSpecial, aload0pos, count, found);
/*  612:     */   }
/*  613:     */   
/*  614:     */   private void atMethodCallCore2(CtClass targetClass, String mname, boolean isStatic, boolean isSpecial, int aload0pos, int count, MemberResolver.Method found)
/*  615:     */     throws CompileError
/*  616:     */   {
/*  617: 585 */     CtClass declClass = found.declaring;
/*  618: 586 */     MethodInfo minfo = found.info;
/*  619: 587 */     String desc = minfo.getDescriptor();
/*  620: 588 */     int acc = minfo.getAccessFlags();
/*  621: 590 */     if (mname.equals("<init>"))
/*  622:     */     {
/*  623: 591 */       isSpecial = true;
/*  624: 592 */       if (declClass != targetClass) {
/*  625: 593 */         throw new CompileError("no such a constructor");
/*  626:     */       }
/*  627: 595 */       if ((declClass != this.thisClass) && (AccessFlag.isPrivate(acc)))
/*  628:     */       {
/*  629: 596 */         desc = getAccessibleConstructor(desc, declClass, minfo);
/*  630: 597 */         this.bytecode.addOpcode(1);
/*  631:     */       }
/*  632:     */     }
/*  633: 600 */     else if (AccessFlag.isPrivate(acc))
/*  634:     */     {
/*  635: 601 */       if (declClass == this.thisClass)
/*  636:     */       {
/*  637: 602 */         isSpecial = true;
/*  638:     */       }
/*  639:     */       else
/*  640:     */       {
/*  641: 604 */         isSpecial = false;
/*  642: 605 */         isStatic = true;
/*  643: 606 */         String origDesc = desc;
/*  644: 607 */         if ((acc & 0x8) == 0) {
/*  645: 608 */           desc = Descriptor.insertParameter(declClass.getName(), origDesc);
/*  646:     */         }
/*  647: 611 */         acc = AccessFlag.setPackage(acc) | 0x8;
/*  648: 612 */         mname = getAccessiblePrivate(mname, origDesc, desc, minfo, declClass);
/*  649:     */       }
/*  650:     */     }
/*  651: 616 */     boolean popTarget = false;
/*  652: 617 */     if ((acc & 0x8) != 0)
/*  653:     */     {
/*  654: 618 */       if (!isStatic)
/*  655:     */       {
/*  656: 624 */         isStatic = true;
/*  657: 625 */         if (aload0pos >= 0) {
/*  658: 626 */           this.bytecode.write(aload0pos, 0);
/*  659:     */         } else {
/*  660: 628 */           popTarget = true;
/*  661:     */         }
/*  662:     */       }
/*  663: 631 */       this.bytecode.addInvokestatic(declClass, mname, desc);
/*  664:     */     }
/*  665: 633 */     else if (isSpecial)
/*  666:     */     {
/*  667: 634 */       this.bytecode.addInvokespecial(declClass, mname, desc);
/*  668:     */     }
/*  669:     */     else
/*  670:     */     {
/*  671: 636 */       if ((!Modifier.isPublic(declClass.getModifiers())) || (declClass.isInterface() != targetClass.isInterface())) {
/*  672: 638 */         declClass = targetClass;
/*  673:     */       }
/*  674: 640 */       if (declClass.isInterface())
/*  675:     */       {
/*  676: 641 */         this.bytecode.addInvokeinterface(declClass, mname, desc, count);
/*  677:     */       }
/*  678:     */       else
/*  679:     */       {
/*  680: 643 */         if (isStatic) {
/*  681: 644 */           throw new CompileError(mname + " is not static");
/*  682:     */         }
/*  683: 646 */         this.bytecode.addInvokevirtual(declClass, mname, desc);
/*  684:     */       }
/*  685:     */     }
/*  686: 649 */     setReturnType(desc, isStatic, popTarget);
/*  687:     */   }
/*  688:     */   
/*  689:     */   protected String getAccessiblePrivate(String methodName, String desc, String newDesc, MethodInfo minfo, CtClass declClass)
/*  690:     */     throws CompileError
/*  691:     */   {
/*  692: 664 */     if (isEnclosing(declClass, this.thisClass))
/*  693:     */     {
/*  694: 665 */       AccessorMaker maker = declClass.getAccessorMaker();
/*  695: 666 */       if (maker != null) {
/*  696: 667 */         return maker.getMethodAccessor(methodName, desc, newDesc, minfo);
/*  697:     */       }
/*  698:     */     }
/*  699: 671 */     throw new CompileError("Method " + methodName + " is private");
/*  700:     */   }
/*  701:     */   
/*  702:     */   protected String getAccessibleConstructor(String desc, CtClass declClass, MethodInfo minfo)
/*  703:     */     throws CompileError
/*  704:     */   {
/*  705: 688 */     if (isEnclosing(declClass, this.thisClass))
/*  706:     */     {
/*  707: 689 */       AccessorMaker maker = declClass.getAccessorMaker();
/*  708: 690 */       if (maker != null) {
/*  709: 691 */         return maker.getConstructor(declClass, desc, minfo);
/*  710:     */       }
/*  711:     */     }
/*  712: 694 */     throw new CompileError("the called constructor is private in " + declClass.getName());
/*  713:     */   }
/*  714:     */   
/*  715:     */   private boolean isEnclosing(CtClass outer, CtClass inner)
/*  716:     */   {
/*  717:     */     try
/*  718:     */     {
/*  719: 700 */       while (inner != null)
/*  720:     */       {
/*  721: 701 */         inner = inner.getDeclaringClass();
/*  722: 702 */         if (inner == outer) {
/*  723: 703 */           return true;
/*  724:     */         }
/*  725:     */       }
/*  726:     */     }
/*  727:     */     catch (NotFoundException e) {}
/*  728: 707 */     return false;
/*  729:     */   }
/*  730:     */   
/*  731:     */   public int getMethodArgsLength(ASTList args)
/*  732:     */   {
/*  733: 711 */     return ASTList.length(args);
/*  734:     */   }
/*  735:     */   
/*  736:     */   public void atMethodArgs(ASTList args, int[] types, int[] dims, String[] cnames)
/*  737:     */     throws CompileError
/*  738:     */   {
/*  739: 716 */     int i = 0;
/*  740: 717 */     while (args != null)
/*  741:     */     {
/*  742: 718 */       ASTree a = args.head();
/*  743: 719 */       a.accept(this);
/*  744: 720 */       types[i] = this.exprType;
/*  745: 721 */       dims[i] = this.arrayDim;
/*  746: 722 */       cnames[i] = this.className;
/*  747: 723 */       i++;
/*  748: 724 */       args = args.tail();
/*  749:     */     }
/*  750:     */   }
/*  751:     */   
/*  752:     */   void setReturnType(String desc, boolean isStatic, boolean popTarget)
/*  753:     */     throws CompileError
/*  754:     */   {
/*  755: 731 */     int i = desc.indexOf(')');
/*  756: 732 */     if (i < 0) {
/*  757: 733 */       badMethod();
/*  758:     */     }
/*  759: 735 */     char c = desc.charAt(++i);
/*  760: 736 */     int dim = 0;
/*  761: 737 */     while (c == '[')
/*  762:     */     {
/*  763: 738 */       dim++;
/*  764: 739 */       c = desc.charAt(++i);
/*  765:     */     }
/*  766: 742 */     this.arrayDim = dim;
/*  767: 743 */     if (c == 'L')
/*  768:     */     {
/*  769: 744 */       int j = desc.indexOf(';', i + 1);
/*  770: 745 */       if (j < 0) {
/*  771: 746 */         badMethod();
/*  772:     */       }
/*  773: 748 */       this.exprType = 307;
/*  774: 749 */       this.className = desc.substring(i + 1, j);
/*  775:     */     }
/*  776:     */     else
/*  777:     */     {
/*  778: 752 */       this.exprType = MemberResolver.descToType(c);
/*  779: 753 */       this.className = null;
/*  780:     */     }
/*  781: 756 */     int etype = this.exprType;
/*  782: 757 */     if ((isStatic) && 
/*  783: 758 */       (popTarget)) {
/*  784: 759 */       if (is2word(etype, dim))
/*  785:     */       {
/*  786: 760 */         this.bytecode.addOpcode(93);
/*  787: 761 */         this.bytecode.addOpcode(88);
/*  788: 762 */         this.bytecode.addOpcode(87);
/*  789:     */       }
/*  790: 764 */       else if (etype == 344)
/*  791:     */       {
/*  792: 765 */         this.bytecode.addOpcode(87);
/*  793:     */       }
/*  794:     */       else
/*  795:     */       {
/*  796: 767 */         this.bytecode.addOpcode(95);
/*  797: 768 */         this.bytecode.addOpcode(87);
/*  798:     */       }
/*  799:     */     }
/*  800:     */   }
/*  801:     */   
/*  802:     */   protected void atFieldAssign(Expr expr, int op, ASTree left, ASTree right, boolean doDup)
/*  803:     */     throws CompileError
/*  804:     */   {
/*  805: 777 */     CtField f = fieldAccess(left, false);
/*  806: 778 */     boolean is_static = this.resultStatic;
/*  807: 779 */     if ((op != 61) && (!is_static)) {
/*  808: 780 */       this.bytecode.addOpcode(89);
/*  809:     */     }
/*  810:     */     int fi;
/*  811:     */     int fi;
/*  812: 783 */     if (op == 61)
/*  813:     */     {
/*  814: 784 */       FieldInfo finfo = f.getFieldInfo2();
/*  815: 785 */       setFieldType(finfo);
/*  816: 786 */       AccessorMaker maker = isAccessibleField(f, finfo);
/*  817:     */       int fi;
/*  818: 787 */       if (maker == null) {
/*  819: 788 */         fi = addFieldrefInfo(f, finfo);
/*  820:     */       } else {
/*  821: 790 */         fi = 0;
/*  822:     */       }
/*  823:     */     }
/*  824:     */     else
/*  825:     */     {
/*  826: 793 */       fi = atFieldRead(f, is_static);
/*  827:     */     }
/*  828: 795 */     int fType = this.exprType;
/*  829: 796 */     int fDim = this.arrayDim;
/*  830: 797 */     String cname = this.className;
/*  831:     */     
/*  832: 799 */     atAssignCore(expr, op, right, fType, fDim, cname);
/*  833:     */     
/*  834: 801 */     boolean is2w = is2word(fType, fDim);
/*  835: 802 */     if (doDup)
/*  836:     */     {
/*  837:     */       int dup_code;
/*  838:     */       int dup_code;
/*  839: 804 */       if (is_static) {
/*  840: 805 */         dup_code = is2w ? 92 : 89;
/*  841:     */       } else {
/*  842: 807 */         dup_code = is2w ? 93 : 90;
/*  843:     */       }
/*  844: 809 */       this.bytecode.addOpcode(dup_code);
/*  845:     */     }
/*  846: 812 */     atFieldAssignCore(f, is_static, fi, is2w);
/*  847:     */     
/*  848: 814 */     this.exprType = fType;
/*  849: 815 */     this.arrayDim = fDim;
/*  850: 816 */     this.className = cname;
/*  851:     */   }
/*  852:     */   
/*  853:     */   private void atFieldAssignCore(CtField f, boolean is_static, int fi, boolean is2byte)
/*  854:     */     throws CompileError
/*  855:     */   {
/*  856: 823 */     if (fi != 0)
/*  857:     */     {
/*  858: 824 */       if (is_static)
/*  859:     */       {
/*  860: 825 */         this.bytecode.add(179);
/*  861: 826 */         this.bytecode.growStack(is2byte ? -2 : -1);
/*  862:     */       }
/*  863:     */       else
/*  864:     */       {
/*  865: 829 */         this.bytecode.add(181);
/*  866: 830 */         this.bytecode.growStack(is2byte ? -3 : -2);
/*  867:     */       }
/*  868: 833 */       this.bytecode.addIndex(fi);
/*  869:     */     }
/*  870:     */     else
/*  871:     */     {
/*  872: 836 */       CtClass declClass = f.getDeclaringClass();
/*  873: 837 */       AccessorMaker maker = declClass.getAccessorMaker();
/*  874:     */       
/*  875: 839 */       FieldInfo finfo = f.getFieldInfo2();
/*  876: 840 */       MethodInfo minfo = maker.getFieldSetter(finfo, is_static);
/*  877: 841 */       this.bytecode.addInvokestatic(declClass, minfo.getName(), minfo.getDescriptor());
/*  878:     */     }
/*  879:     */   }
/*  880:     */   
/*  881:     */   public void atMember(Member mem)
/*  882:     */     throws CompileError
/*  883:     */   {
/*  884: 849 */     atFieldRead(mem);
/*  885:     */   }
/*  886:     */   
/*  887:     */   protected void atFieldRead(ASTree expr)
/*  888:     */     throws CompileError
/*  889:     */   {
/*  890: 854 */     CtField f = fieldAccess(expr, true);
/*  891: 855 */     if (f == null)
/*  892:     */     {
/*  893: 856 */       atArrayLength(expr);
/*  894: 857 */       return;
/*  895:     */     }
/*  896: 860 */     boolean is_static = this.resultStatic;
/*  897: 861 */     ASTree cexpr = TypeChecker.getConstantFieldValue(f);
/*  898: 862 */     if (cexpr == null)
/*  899:     */     {
/*  900: 863 */       atFieldRead(f, is_static);
/*  901:     */     }
/*  902:     */     else
/*  903:     */     {
/*  904: 865 */       cexpr.accept(this);
/*  905: 866 */       setFieldType(f.getFieldInfo2());
/*  906:     */     }
/*  907:     */   }
/*  908:     */   
/*  909:     */   private void atArrayLength(ASTree expr)
/*  910:     */     throws CompileError
/*  911:     */   {
/*  912: 871 */     if (this.arrayDim == 0) {
/*  913: 872 */       throw new CompileError(".length applied to a non array");
/*  914:     */     }
/*  915: 874 */     this.bytecode.addOpcode(190);
/*  916: 875 */     this.exprType = 324;
/*  917: 876 */     this.arrayDim = 0;
/*  918:     */   }
/*  919:     */   
/*  920:     */   private int atFieldRead(CtField f, boolean isStatic)
/*  921:     */     throws CompileError
/*  922:     */   {
/*  923: 885 */     FieldInfo finfo = f.getFieldInfo2();
/*  924: 886 */     boolean is2byte = setFieldType(finfo);
/*  925: 887 */     AccessorMaker maker = isAccessibleField(f, finfo);
/*  926: 888 */     if (maker != null)
/*  927:     */     {
/*  928: 889 */       MethodInfo minfo = maker.getFieldGetter(finfo, isStatic);
/*  929: 890 */       this.bytecode.addInvokestatic(f.getDeclaringClass(), minfo.getName(), minfo.getDescriptor());
/*  930:     */       
/*  931: 892 */       return 0;
/*  932:     */     }
/*  933: 895 */     int fi = addFieldrefInfo(f, finfo);
/*  934: 896 */     if (isStatic)
/*  935:     */     {
/*  936: 897 */       this.bytecode.add(178);
/*  937: 898 */       this.bytecode.growStack(is2byte ? 2 : 1);
/*  938:     */     }
/*  939:     */     else
/*  940:     */     {
/*  941: 901 */       this.bytecode.add(180);
/*  942: 902 */       this.bytecode.growStack(is2byte ? 1 : 0);
/*  943:     */     }
/*  944: 905 */     this.bytecode.addIndex(fi);
/*  945: 906 */     return fi;
/*  946:     */   }
/*  947:     */   
/*  948:     */   private AccessorMaker isAccessibleField(CtField f, FieldInfo finfo)
/*  949:     */     throws CompileError
/*  950:     */   {
/*  951: 918 */     if ((AccessFlag.isPrivate(finfo.getAccessFlags())) && (f.getDeclaringClass() != this.thisClass))
/*  952:     */     {
/*  953: 920 */       CtClass declClass = f.getDeclaringClass();
/*  954: 921 */       if (isEnclosing(declClass, this.thisClass))
/*  955:     */       {
/*  956: 922 */         AccessorMaker maker = declClass.getAccessorMaker();
/*  957: 923 */         if (maker != null) {
/*  958: 924 */           return maker;
/*  959:     */         }
/*  960: 926 */         throw new CompileError("fatal error.  bug?");
/*  961:     */       }
/*  962: 929 */       throw new CompileError("Field " + f.getName() + " in " + declClass.getName() + " is private.");
/*  963:     */     }
/*  964: 933 */     return null;
/*  965:     */   }
/*  966:     */   
/*  967:     */   private boolean setFieldType(FieldInfo finfo)
/*  968:     */     throws CompileError
/*  969:     */   {
/*  970: 942 */     String type = finfo.getDescriptor();
/*  971:     */     
/*  972: 944 */     int i = 0;
/*  973: 945 */     int dim = 0;
/*  974: 946 */     char c = type.charAt(i);
/*  975: 947 */     while (c == '[')
/*  976:     */     {
/*  977: 948 */       dim++;
/*  978: 949 */       c = type.charAt(++i);
/*  979:     */     }
/*  980: 952 */     this.arrayDim = dim;
/*  981: 953 */     this.exprType = MemberResolver.descToType(c);
/*  982: 955 */     if (c == 'L') {
/*  983: 956 */       this.className = type.substring(i + 1, type.indexOf(';', i + 1));
/*  984:     */     } else {
/*  985: 958 */       this.className = null;
/*  986:     */     }
/*  987: 960 */     boolean is2byte = (c == 'J') || (c == 'D');
/*  988: 961 */     return is2byte;
/*  989:     */   }
/*  990:     */   
/*  991:     */   private int addFieldrefInfo(CtField f, FieldInfo finfo)
/*  992:     */   {
/*  993: 965 */     ConstPool cp = this.bytecode.getConstPool();
/*  994: 966 */     String cname = f.getDeclaringClass().getName();
/*  995: 967 */     int ci = cp.addClassInfo(cname);
/*  996: 968 */     String name = finfo.getName();
/*  997: 969 */     String type = finfo.getDescriptor();
/*  998: 970 */     return cp.addFieldrefInfo(ci, name, type);
/*  999:     */   }
/* 1000:     */   
/* 1001:     */   protected void atClassObject2(String cname)
/* 1002:     */     throws CompileError
/* 1003:     */   {
/* 1004: 974 */     if (getMajorVersion() < 49) {
/* 1005: 975 */       super.atClassObject2(cname);
/* 1006:     */     } else {
/* 1007: 977 */       this.bytecode.addLdc(this.bytecode.getConstPool().addClassInfo(cname));
/* 1008:     */     }
/* 1009:     */   }
/* 1010:     */   
/* 1011:     */   protected void atFieldPlusPlus(int token, boolean isPost, ASTree oprand, Expr expr, boolean doDup)
/* 1012:     */     throws CompileError
/* 1013:     */   {
/* 1014: 984 */     CtField f = fieldAccess(oprand, false);
/* 1015: 985 */     boolean is_static = this.resultStatic;
/* 1016: 986 */     if (!is_static) {
/* 1017: 987 */       this.bytecode.addOpcode(89);
/* 1018:     */     }
/* 1019: 989 */     int fi = atFieldRead(f, is_static);
/* 1020: 990 */     int t = this.exprType;
/* 1021: 991 */     boolean is2w = is2word(t, this.arrayDim);
/* 1022:     */     int dup_code;
/* 1023:     */     int dup_code;
/* 1024: 994 */     if (is_static) {
/* 1025: 995 */       dup_code = is2w ? 92 : 89;
/* 1026:     */     } else {
/* 1027: 997 */       dup_code = is2w ? 93 : 90;
/* 1028:     */     }
/* 1029: 999 */     atPlusPlusCore(dup_code, doDup, token, isPost, expr);
/* 1030:1000 */     atFieldAssignCore(f, is_static, fi, is2w);
/* 1031:     */   }
/* 1032:     */   
/* 1033:     */   protected CtField fieldAccess(ASTree expr, boolean acceptLength)
/* 1034:     */     throws CompileError
/* 1035:     */   {
/* 1036:1010 */     if ((expr instanceof Member))
/* 1037:     */     {
/* 1038:1011 */       String name = ((Member)expr).get();
/* 1039:1012 */       CtField f = null;
/* 1040:     */       try
/* 1041:     */       {
/* 1042:1014 */         f = this.thisClass.getField(name);
/* 1043:     */       }
/* 1044:     */       catch (NotFoundException e)
/* 1045:     */       {
/* 1046:1018 */         throw new NoFieldException(name, expr);
/* 1047:     */       }
/* 1048:1021 */       boolean is_static = Modifier.isStatic(f.getModifiers());
/* 1049:1022 */       if (!is_static)
/* 1050:     */       {
/* 1051:1023 */         if (this.inStaticMethod) {
/* 1052:1024 */           throw new CompileError("not available in a static method: " + name);
/* 1053:     */         }
/* 1054:1027 */         this.bytecode.addAload(0);
/* 1055:     */       }
/* 1056:1029 */       this.resultStatic = is_static;
/* 1057:1030 */       return f;
/* 1058:     */     }
/* 1059:1032 */     if ((expr instanceof Expr))
/* 1060:     */     {
/* 1061:1033 */       Expr e = (Expr)expr;
/* 1062:1034 */       int op = e.getOperator();
/* 1063:1035 */       if (op == 35)
/* 1064:     */       {
/* 1065:1040 */         CtField f = this.resolver.lookupField(((Symbol)e.oprand1()).get(), (Symbol)e.oprand2());
/* 1066:     */         
/* 1067:1042 */         this.resultStatic = true;
/* 1068:1043 */         return f;
/* 1069:     */       }
/* 1070:1045 */       if (op == 46)
/* 1071:     */       {
/* 1072:1046 */         CtField f = null;
/* 1073:     */         try
/* 1074:     */         {
/* 1075:1048 */           e.oprand1().accept(this);
/* 1076:1053 */           if ((this.exprType == 307) && (this.arrayDim == 0))
/* 1077:     */           {
/* 1078:1054 */             f = this.resolver.lookupFieldByJvmName(this.className, (Symbol)e.oprand2());
/* 1079:     */           }
/* 1080:     */           else
/* 1081:     */           {
/* 1082:1056 */             if ((acceptLength) && (this.arrayDim > 0) && (((Symbol)e.oprand2()).get().equals("length"))) {
/* 1083:1058 */               return null;
/* 1084:     */             }
/* 1085:1060 */             badLvalue();
/* 1086:     */           }
/* 1087:1062 */           boolean is_static = Modifier.isStatic(f.getModifiers());
/* 1088:1063 */           if (is_static) {
/* 1089:1064 */             this.bytecode.addOpcode(87);
/* 1090:     */           }
/* 1091:1066 */           this.resultStatic = is_static;
/* 1092:1067 */           return f;
/* 1093:     */         }
/* 1094:     */         catch (NoFieldException nfe)
/* 1095:     */         {
/* 1096:1070 */           if (nfe.getExpr() != e.oprand1()) {
/* 1097:1071 */             throw nfe;
/* 1098:     */           }
/* 1099:1077 */           Symbol fname = (Symbol)e.oprand2();
/* 1100:1078 */           String cname = nfe.getField();
/* 1101:1079 */           f = this.resolver.lookupFieldByJvmName2(cname, fname, expr);
/* 1102:1080 */           this.resolver.recordPackage(cname);
/* 1103:1081 */           this.resultStatic = true;
/* 1104:1082 */           return f;
/* 1105:     */         }
/* 1106:     */       }
/* 1107:1086 */       badLvalue();
/* 1108:     */     }
/* 1109:     */     else
/* 1110:     */     {
/* 1111:1089 */       badLvalue();
/* 1112:     */     }
/* 1113:1091 */     this.resultStatic = false;
/* 1114:1092 */     return null;
/* 1115:     */   }
/* 1116:     */   
/* 1117:     */   private static void badLvalue()
/* 1118:     */     throws CompileError
/* 1119:     */   {
/* 1120:1096 */     throw new CompileError("bad l-value");
/* 1121:     */   }
/* 1122:     */   
/* 1123:     */   public CtClass[] makeParamList(MethodDecl md)
/* 1124:     */     throws CompileError
/* 1125:     */   {
/* 1126:1101 */     ASTList plist = md.getParams();
/* 1127:     */     CtClass[] params;
/* 1128:     */     CtClass[] params;
/* 1129:1102 */     if (plist == null)
/* 1130:     */     {
/* 1131:1103 */       params = new CtClass[0];
/* 1132:     */     }
/* 1133:     */     else
/* 1134:     */     {
/* 1135:1105 */       int i = 0;
/* 1136:1106 */       params = new CtClass[plist.length()];
/* 1137:1107 */       while (plist != null)
/* 1138:     */       {
/* 1139:1108 */         params[(i++)] = this.resolver.lookupClass((Declarator)plist.head());
/* 1140:1109 */         plist = plist.tail();
/* 1141:     */       }
/* 1142:     */     }
/* 1143:1113 */     return params;
/* 1144:     */   }
/* 1145:     */   
/* 1146:     */   public CtClass[] makeThrowsList(MethodDecl md)
/* 1147:     */     throws CompileError
/* 1148:     */   {
/* 1149:1118 */     ASTList list = md.getThrows();
/* 1150:1119 */     if (list == null) {
/* 1151:1120 */       return null;
/* 1152:     */     }
/* 1153:1122 */     int i = 0;
/* 1154:1123 */     CtClass[] clist = new CtClass[list.length()];
/* 1155:1124 */     while (list != null)
/* 1156:     */     {
/* 1157:1125 */       clist[(i++)] = this.resolver.lookupClassByName((ASTList)list.head());
/* 1158:1126 */       list = list.tail();
/* 1159:     */     }
/* 1160:1129 */     return clist;
/* 1161:     */   }
/* 1162:     */   
/* 1163:     */   protected String resolveClassName(ASTList name)
/* 1164:     */     throws CompileError
/* 1165:     */   {
/* 1166:1139 */     return this.resolver.resolveClassName(name);
/* 1167:     */   }
/* 1168:     */   
/* 1169:     */   protected String resolveClassName(String jvmName)
/* 1170:     */     throws CompileError
/* 1171:     */   {
/* 1172:1146 */     return this.resolver.resolveJvmClassName(jvmName);
/* 1173:     */   }
/* 1174:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.compiler.MemberCodeGen
 * JD-Core Version:    0.7.0.1
 */