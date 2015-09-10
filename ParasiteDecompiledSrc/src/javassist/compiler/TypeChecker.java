/*    1:     */ package javassist.compiler;
/*    2:     */ 
/*    3:     */ import javassist.ClassPool;
/*    4:     */ import javassist.CtClass;
/*    5:     */ import javassist.CtField;
/*    6:     */ import javassist.Modifier;
/*    7:     */ import javassist.NotFoundException;
/*    8:     */ import javassist.bytecode.FieldInfo;
/*    9:     */ import javassist.bytecode.MethodInfo;
/*   10:     */ import javassist.bytecode.Opcode;
/*   11:     */ import javassist.compiler.ast.ASTList;
/*   12:     */ import javassist.compiler.ast.ASTree;
/*   13:     */ import javassist.compiler.ast.ArrayInit;
/*   14:     */ import javassist.compiler.ast.AssignExpr;
/*   15:     */ import javassist.compiler.ast.BinExpr;
/*   16:     */ import javassist.compiler.ast.CallExpr;
/*   17:     */ import javassist.compiler.ast.CastExpr;
/*   18:     */ import javassist.compiler.ast.CondExpr;
/*   19:     */ import javassist.compiler.ast.Declarator;
/*   20:     */ import javassist.compiler.ast.DoubleConst;
/*   21:     */ import javassist.compiler.ast.Expr;
/*   22:     */ import javassist.compiler.ast.InstanceOfExpr;
/*   23:     */ import javassist.compiler.ast.IntConst;
/*   24:     */ import javassist.compiler.ast.Keyword;
/*   25:     */ import javassist.compiler.ast.Member;
/*   26:     */ import javassist.compiler.ast.NewExpr;
/*   27:     */ import javassist.compiler.ast.StringL;
/*   28:     */ import javassist.compiler.ast.Symbol;
/*   29:     */ import javassist.compiler.ast.Variable;
/*   30:     */ import javassist.compiler.ast.Visitor;
/*   31:     */ 
/*   32:     */ public class TypeChecker
/*   33:     */   extends Visitor
/*   34:     */   implements Opcode, TokenId
/*   35:     */ {
/*   36:     */   static final String javaLangObject = "java.lang.Object";
/*   37:     */   static final String jvmJavaLangObject = "java/lang/Object";
/*   38:     */   static final String jvmJavaLangString = "java/lang/String";
/*   39:     */   static final String jvmJavaLangClass = "java/lang/Class";
/*   40:     */   protected int exprType;
/*   41:     */   protected int arrayDim;
/*   42:     */   protected String className;
/*   43:     */   protected MemberResolver resolver;
/*   44:     */   protected CtClass thisClass;
/*   45:     */   protected MethodInfo thisMethod;
/*   46:     */   
/*   47:     */   public TypeChecker(CtClass cc, ClassPool cp)
/*   48:     */   {
/*   49:  44 */     this.resolver = new MemberResolver(cp);
/*   50:  45 */     this.thisClass = cc;
/*   51:  46 */     this.thisMethod = null;
/*   52:     */   }
/*   53:     */   
/*   54:     */   protected static String argTypesToString(int[] types, int[] dims, String[] cnames)
/*   55:     */   {
/*   56:  55 */     StringBuffer sbuf = new StringBuffer();
/*   57:  56 */     sbuf.append('(');
/*   58:  57 */     int n = types.length;
/*   59:  58 */     if (n > 0)
/*   60:     */     {
/*   61:  59 */       int i = 0;
/*   62:     */       for (;;)
/*   63:     */       {
/*   64:  61 */         typeToString(sbuf, types[i], dims[i], cnames[i]);
/*   65:  62 */         i++;
/*   66:  62 */         if (i >= n) {
/*   67:     */           break;
/*   68:     */         }
/*   69:  63 */         sbuf.append(',');
/*   70:     */       }
/*   71:     */     }
/*   72:  69 */     sbuf.append(')');
/*   73:  70 */     return sbuf.toString();
/*   74:     */   }
/*   75:     */   
/*   76:     */   protected static StringBuffer typeToString(StringBuffer sbuf, int type, int dim, String cname)
/*   77:     */   {
/*   78:     */     String s;
/*   79:     */     String s;
/*   80:  80 */     if (type == 307)
/*   81:     */     {
/*   82:  81 */       s = MemberResolver.jvmToJavaName(cname);
/*   83:     */     }
/*   84:     */     else
/*   85:     */     {
/*   86:     */       String s;
/*   87:  82 */       if (type == 412) {
/*   88:  83 */         s = "Object";
/*   89:     */       } else {
/*   90:     */         try
/*   91:     */         {
/*   92:  86 */           s = MemberResolver.getTypeName(type);
/*   93:     */         }
/*   94:     */         catch (CompileError e)
/*   95:     */         {
/*   96:  89 */           s = "?";
/*   97:     */         }
/*   98:     */       }
/*   99:     */     }
/*  100:  92 */     sbuf.append(s);
/*  101:  93 */     while (dim-- > 0) {
/*  102:  94 */       sbuf.append("[]");
/*  103:     */     }
/*  104:  96 */     return sbuf;
/*  105:     */   }
/*  106:     */   
/*  107:     */   public void setThisMethod(MethodInfo m)
/*  108:     */   {
/*  109: 103 */     this.thisMethod = m;
/*  110:     */   }
/*  111:     */   
/*  112:     */   protected static void fatal()
/*  113:     */     throws CompileError
/*  114:     */   {
/*  115: 107 */     throw new CompileError("fatal");
/*  116:     */   }
/*  117:     */   
/*  118:     */   protected String getThisName()
/*  119:     */   {
/*  120: 114 */     return MemberResolver.javaToJvmName(this.thisClass.getName());
/*  121:     */   }
/*  122:     */   
/*  123:     */   protected String getSuperName()
/*  124:     */     throws CompileError
/*  125:     */   {
/*  126: 121 */     return MemberResolver.javaToJvmName(MemberResolver.getSuperclass(this.thisClass).getName());
/*  127:     */   }
/*  128:     */   
/*  129:     */   protected String resolveClassName(ASTList name)
/*  130:     */     throws CompileError
/*  131:     */   {
/*  132: 131 */     return this.resolver.resolveClassName(name);
/*  133:     */   }
/*  134:     */   
/*  135:     */   protected String resolveClassName(String jvmName)
/*  136:     */     throws CompileError
/*  137:     */   {
/*  138: 138 */     return this.resolver.resolveJvmClassName(jvmName);
/*  139:     */   }
/*  140:     */   
/*  141:     */   public void atNewExpr(NewExpr expr)
/*  142:     */     throws CompileError
/*  143:     */   {
/*  144: 142 */     if (expr.isArray())
/*  145:     */     {
/*  146: 143 */       atNewArrayExpr(expr);
/*  147:     */     }
/*  148:     */     else
/*  149:     */     {
/*  150: 145 */       CtClass clazz = this.resolver.lookupClassByName(expr.getClassName());
/*  151: 146 */       String cname = clazz.getName();
/*  152: 147 */       ASTList args = expr.getArguments();
/*  153: 148 */       atMethodCallCore(clazz, "<init>", args);
/*  154: 149 */       this.exprType = 307;
/*  155: 150 */       this.arrayDim = 0;
/*  156: 151 */       this.className = MemberResolver.javaToJvmName(cname);
/*  157:     */     }
/*  158:     */   }
/*  159:     */   
/*  160:     */   public void atNewArrayExpr(NewExpr expr)
/*  161:     */     throws CompileError
/*  162:     */   {
/*  163: 156 */     int type = expr.getArrayType();
/*  164: 157 */     ASTList size = expr.getArraySize();
/*  165: 158 */     ASTList classname = expr.getClassName();
/*  166: 159 */     ASTree init = expr.getInitializer();
/*  167: 160 */     if (init != null) {
/*  168: 161 */       init.accept(this);
/*  169:     */     }
/*  170: 163 */     if (size.length() > 1)
/*  171:     */     {
/*  172: 164 */       atMultiNewArray(type, classname, size);
/*  173:     */     }
/*  174:     */     else
/*  175:     */     {
/*  176: 166 */       ASTree sizeExpr = size.head();
/*  177: 167 */       if (sizeExpr != null) {
/*  178: 168 */         sizeExpr.accept(this);
/*  179:     */       }
/*  180: 170 */       this.exprType = type;
/*  181: 171 */       this.arrayDim = 1;
/*  182: 172 */       if (type == 307) {
/*  183: 173 */         this.className = resolveClassName(classname);
/*  184:     */       } else {
/*  185: 175 */         this.className = null;
/*  186:     */       }
/*  187:     */     }
/*  188:     */   }
/*  189:     */   
/*  190:     */   public void atArrayInit(ArrayInit init)
/*  191:     */     throws CompileError
/*  192:     */   {
/*  193: 180 */     ASTList list = init;
/*  194: 181 */     while (list != null)
/*  195:     */     {
/*  196: 182 */       ASTree h = list.head();
/*  197: 183 */       list = list.tail();
/*  198: 184 */       if (h != null) {
/*  199: 185 */         h.accept(this);
/*  200:     */       }
/*  201:     */     }
/*  202:     */   }
/*  203:     */   
/*  204:     */   protected void atMultiNewArray(int type, ASTList classname, ASTList size)
/*  205:     */     throws CompileError
/*  206:     */   {
/*  207: 193 */     int dim = size.length();
/*  208: 194 */     for (int count = 0; size != null; size = size.tail())
/*  209:     */     {
/*  210: 195 */       ASTree s = size.head();
/*  211: 196 */       if (s == null) {
/*  212:     */         break;
/*  213:     */       }
/*  214: 199 */       count++;
/*  215: 200 */       s.accept(this);
/*  216:     */     }
/*  217: 203 */     this.exprType = type;
/*  218: 204 */     this.arrayDim = dim;
/*  219: 205 */     if (type == 307) {
/*  220: 206 */       this.className = resolveClassName(classname);
/*  221:     */     } else {
/*  222: 208 */       this.className = null;
/*  223:     */     }
/*  224:     */   }
/*  225:     */   
/*  226:     */   public void atAssignExpr(AssignExpr expr)
/*  227:     */     throws CompileError
/*  228:     */   {
/*  229: 213 */     int op = expr.getOperator();
/*  230: 214 */     ASTree left = expr.oprand1();
/*  231: 215 */     ASTree right = expr.oprand2();
/*  232: 216 */     if ((left instanceof Variable))
/*  233:     */     {
/*  234: 217 */       atVariableAssign(expr, op, (Variable)left, ((Variable)left).getDeclarator(), right);
/*  235:     */     }
/*  236:     */     else
/*  237:     */     {
/*  238: 221 */       if ((left instanceof Expr))
/*  239:     */       {
/*  240: 222 */         Expr e = (Expr)left;
/*  241: 223 */         if (e.getOperator() == 65)
/*  242:     */         {
/*  243: 224 */           atArrayAssign(expr, op, (Expr)left, right);
/*  244: 225 */           return;
/*  245:     */         }
/*  246:     */       }
/*  247: 229 */       atFieldAssign(expr, op, left, right);
/*  248:     */     }
/*  249:     */   }
/*  250:     */   
/*  251:     */   private void atVariableAssign(Expr expr, int op, Variable var, Declarator d, ASTree right)
/*  252:     */     throws CompileError
/*  253:     */   {
/*  254: 241 */     int varType = d.getType();
/*  255: 242 */     int varArray = d.getArrayDim();
/*  256: 243 */     String varClass = d.getClassName();
/*  257: 245 */     if (op != 61) {
/*  258: 246 */       atVariable(var);
/*  259:     */     }
/*  260: 248 */     right.accept(this);
/*  261: 249 */     this.exprType = varType;
/*  262: 250 */     this.arrayDim = varArray;
/*  263: 251 */     this.className = varClass;
/*  264:     */   }
/*  265:     */   
/*  266:     */   private void atArrayAssign(Expr expr, int op, Expr array, ASTree right)
/*  267:     */     throws CompileError
/*  268:     */   {
/*  269: 257 */     atArrayRead(array.oprand1(), array.oprand2());
/*  270: 258 */     int aType = this.exprType;
/*  271: 259 */     int aDim = this.arrayDim;
/*  272: 260 */     String cname = this.className;
/*  273: 261 */     right.accept(this);
/*  274: 262 */     this.exprType = aType;
/*  275: 263 */     this.arrayDim = aDim;
/*  276: 264 */     this.className = cname;
/*  277:     */   }
/*  278:     */   
/*  279:     */   protected void atFieldAssign(Expr expr, int op, ASTree left, ASTree right)
/*  280:     */     throws CompileError
/*  281:     */   {
/*  282: 270 */     CtField f = fieldAccess(left);
/*  283: 271 */     atFieldRead(f);
/*  284: 272 */     int fType = this.exprType;
/*  285: 273 */     int fDim = this.arrayDim;
/*  286: 274 */     String cname = this.className;
/*  287: 275 */     right.accept(this);
/*  288: 276 */     this.exprType = fType;
/*  289: 277 */     this.arrayDim = fDim;
/*  290: 278 */     this.className = cname;
/*  291:     */   }
/*  292:     */   
/*  293:     */   public void atCondExpr(CondExpr expr)
/*  294:     */     throws CompileError
/*  295:     */   {
/*  296: 282 */     booleanExpr(expr.condExpr());
/*  297: 283 */     expr.thenExpr().accept(this);
/*  298: 284 */     int type1 = this.exprType;
/*  299: 285 */     int dim1 = this.arrayDim;
/*  300: 286 */     String cname1 = this.className;
/*  301: 287 */     expr.elseExpr().accept(this);
/*  302: 289 */     if ((dim1 == 0) && (dim1 == this.arrayDim)) {
/*  303: 290 */       if (CodeGen.rightIsStrong(type1, this.exprType))
/*  304:     */       {
/*  305: 291 */         expr.setThen(new CastExpr(this.exprType, 0, expr.thenExpr()));
/*  306:     */       }
/*  307: 292 */       else if (CodeGen.rightIsStrong(this.exprType, type1))
/*  308:     */       {
/*  309: 293 */         expr.setElse(new CastExpr(type1, 0, expr.elseExpr()));
/*  310: 294 */         this.exprType = type1;
/*  311:     */       }
/*  312:     */     }
/*  313:     */   }
/*  314:     */   
/*  315:     */   public void atBinExpr(BinExpr expr)
/*  316:     */     throws CompileError
/*  317:     */   {
/*  318: 305 */     int token = expr.getOperator();
/*  319: 306 */     int k = CodeGen.lookupBinOp(token);
/*  320: 307 */     if (k >= 0)
/*  321:     */     {
/*  322: 310 */       if (token == 43)
/*  323:     */       {
/*  324: 311 */         Expr e = atPlusExpr(expr);
/*  325: 312 */         if (e != null)
/*  326:     */         {
/*  327: 316 */           e = CallExpr.makeCall(Expr.make(46, e, new Member("toString")), null);
/*  328:     */           
/*  329: 318 */           expr.setOprand1(e);
/*  330: 319 */           expr.setOprand2(null);
/*  331: 320 */           this.className = "java/lang/String";
/*  332:     */         }
/*  333:     */       }
/*  334:     */       else
/*  335:     */       {
/*  336: 324 */         ASTree left = expr.oprand1();
/*  337: 325 */         ASTree right = expr.oprand2();
/*  338: 326 */         left.accept(this);
/*  339: 327 */         int type1 = this.exprType;
/*  340: 328 */         right.accept(this);
/*  341: 329 */         if (!isConstant(expr, token, left, right)) {
/*  342: 330 */           computeBinExprType(expr, token, type1);
/*  343:     */         }
/*  344:     */       }
/*  345:     */     }
/*  346:     */     else {
/*  347: 336 */       booleanExpr(expr);
/*  348:     */     }
/*  349:     */   }
/*  350:     */   
/*  351:     */   private Expr atPlusExpr(BinExpr expr)
/*  352:     */     throws CompileError
/*  353:     */   {
/*  354: 345 */     ASTree left = expr.oprand1();
/*  355: 346 */     ASTree right = expr.oprand2();
/*  356: 347 */     if (right == null)
/*  357:     */     {
/*  358: 350 */       left.accept(this);
/*  359: 351 */       return null;
/*  360:     */     }
/*  361: 354 */     if (isPlusExpr(left))
/*  362:     */     {
/*  363: 355 */       Expr newExpr = atPlusExpr((BinExpr)left);
/*  364: 356 */       if (newExpr != null)
/*  365:     */       {
/*  366: 357 */         right.accept(this);
/*  367: 358 */         this.exprType = 307;
/*  368: 359 */         this.arrayDim = 0;
/*  369: 360 */         this.className = "java/lang/StringBuffer";
/*  370: 361 */         return makeAppendCall(newExpr, right);
/*  371:     */       }
/*  372:     */     }
/*  373:     */     else
/*  374:     */     {
/*  375: 365 */       left.accept(this);
/*  376:     */     }
/*  377: 367 */     int type1 = this.exprType;
/*  378: 368 */     int dim1 = this.arrayDim;
/*  379: 369 */     String cname = this.className;
/*  380: 370 */     right.accept(this);
/*  381: 372 */     if (isConstant(expr, 43, left, right)) {
/*  382: 373 */       return null;
/*  383:     */     }
/*  384: 375 */     if (((type1 == 307) && (dim1 == 0) && ("java/lang/String".equals(cname))) || ((this.exprType == 307) && (this.arrayDim == 0) && ("java/lang/String".equals(this.className))))
/*  385:     */     {
/*  386: 378 */       ASTList sbufClass = ASTList.make(new Symbol("java"), new Symbol("lang"), new Symbol("StringBuffer"));
/*  387:     */       
/*  388: 380 */       ASTree e = new NewExpr(sbufClass, null);
/*  389: 381 */       this.exprType = 307;
/*  390: 382 */       this.arrayDim = 0;
/*  391: 383 */       this.className = "java/lang/StringBuffer";
/*  392: 384 */       return makeAppendCall(makeAppendCall(e, left), right);
/*  393:     */     }
/*  394: 387 */     computeBinExprType(expr, 43, type1);
/*  395: 388 */     return null;
/*  396:     */   }
/*  397:     */   
/*  398:     */   private boolean isConstant(BinExpr expr, int op, ASTree left, ASTree right)
/*  399:     */     throws CompileError
/*  400:     */   {
/*  401: 395 */     left = stripPlusExpr(left);
/*  402: 396 */     right = stripPlusExpr(right);
/*  403: 397 */     ASTree newExpr = null;
/*  404: 398 */     if (((left instanceof StringL)) && ((right instanceof StringL)) && (op == 43)) {
/*  405: 399 */       newExpr = new StringL(((StringL)left).get() + ((StringL)right).get());
/*  406: 401 */     } else if ((left instanceof IntConst)) {
/*  407: 402 */       newExpr = ((IntConst)left).compute(op, right);
/*  408: 403 */     } else if ((left instanceof DoubleConst)) {
/*  409: 404 */       newExpr = ((DoubleConst)left).compute(op, right);
/*  410:     */     }
/*  411: 406 */     if (newExpr == null) {
/*  412: 407 */       return false;
/*  413:     */     }
/*  414: 409 */     expr.setOperator(43);
/*  415: 410 */     expr.setOprand1(newExpr);
/*  416: 411 */     expr.setOprand2(null);
/*  417: 412 */     newExpr.accept(this);
/*  418: 413 */     return true;
/*  419:     */   }
/*  420:     */   
/*  421:     */   static ASTree stripPlusExpr(ASTree expr)
/*  422:     */   {
/*  423: 420 */     if ((expr instanceof BinExpr))
/*  424:     */     {
/*  425: 421 */       BinExpr e = (BinExpr)expr;
/*  426: 422 */       if ((e.getOperator() == 43) && (e.oprand2() == null)) {
/*  427: 423 */         return e.getLeft();
/*  428:     */       }
/*  429:     */     }
/*  430: 425 */     else if ((expr instanceof Expr))
/*  431:     */     {
/*  432: 426 */       Expr e = (Expr)expr;
/*  433: 427 */       int op = e.getOperator();
/*  434: 428 */       if (op == 35)
/*  435:     */       {
/*  436: 429 */         ASTree cexpr = getConstantFieldValue((Member)e.oprand2());
/*  437: 430 */         if (cexpr != null) {
/*  438: 431 */           return cexpr;
/*  439:     */         }
/*  440:     */       }
/*  441: 433 */       else if ((op == 43) && (e.getRight() == null))
/*  442:     */       {
/*  443: 434 */         return e.getLeft();
/*  444:     */       }
/*  445:     */     }
/*  446: 436 */     else if ((expr instanceof Member))
/*  447:     */     {
/*  448: 437 */       ASTree cexpr = getConstantFieldValue((Member)expr);
/*  449: 438 */       if (cexpr != null) {
/*  450: 439 */         return cexpr;
/*  451:     */       }
/*  452:     */     }
/*  453: 442 */     return expr;
/*  454:     */   }
/*  455:     */   
/*  456:     */   private static ASTree getConstantFieldValue(Member mem)
/*  457:     */   {
/*  458: 450 */     return getConstantFieldValue(mem.getField());
/*  459:     */   }
/*  460:     */   
/*  461:     */   public static ASTree getConstantFieldValue(CtField f)
/*  462:     */   {
/*  463: 454 */     if (f == null) {
/*  464: 455 */       return null;
/*  465:     */     }
/*  466: 457 */     Object value = f.getConstantValue();
/*  467: 458 */     if (value == null) {
/*  468: 459 */       return null;
/*  469:     */     }
/*  470: 461 */     if ((value instanceof String)) {
/*  471: 462 */       return new StringL((String)value);
/*  472:     */     }
/*  473: 463 */     if (((value instanceof Double)) || ((value instanceof Float)))
/*  474:     */     {
/*  475: 464 */       int token = (value instanceof Double) ? 405 : 404;
/*  476:     */       
/*  477: 466 */       return new DoubleConst(((Number)value).doubleValue(), token);
/*  478:     */     }
/*  479: 468 */     if ((value instanceof Number))
/*  480:     */     {
/*  481: 469 */       int token = (value instanceof Long) ? 403 : 402;
/*  482: 470 */       return new IntConst(((Number)value).longValue(), token);
/*  483:     */     }
/*  484: 472 */     if ((value instanceof Boolean)) {
/*  485: 473 */       return new Keyword(((Boolean)value).booleanValue() ? 410 : 411);
/*  486:     */     }
/*  487: 476 */     return null;
/*  488:     */   }
/*  489:     */   
/*  490:     */   private static boolean isPlusExpr(ASTree expr)
/*  491:     */   {
/*  492: 480 */     if ((expr instanceof BinExpr))
/*  493:     */     {
/*  494: 481 */       BinExpr bexpr = (BinExpr)expr;
/*  495: 482 */       int token = bexpr.getOperator();
/*  496: 483 */       return token == 43;
/*  497:     */     }
/*  498: 486 */     return false;
/*  499:     */   }
/*  500:     */   
/*  501:     */   private static Expr makeAppendCall(ASTree target, ASTree arg)
/*  502:     */   {
/*  503: 490 */     return CallExpr.makeCall(Expr.make(46, target, new Member("append")), new ASTList(arg));
/*  504:     */   }
/*  505:     */   
/*  506:     */   private void computeBinExprType(BinExpr expr, int token, int type1)
/*  507:     */     throws CompileError
/*  508:     */   {
/*  509: 498 */     int type2 = this.exprType;
/*  510: 499 */     if ((token == 364) || (token == 366) || (token == 370)) {
/*  511: 500 */       this.exprType = type1;
/*  512:     */     } else {
/*  513: 502 */       insertCast(expr, type1, type2);
/*  514:     */     }
/*  515: 504 */     if (CodeGen.isP_INT(this.exprType)) {
/*  516: 505 */       this.exprType = 324;
/*  517:     */     }
/*  518:     */   }
/*  519:     */   
/*  520:     */   private void booleanExpr(ASTree expr)
/*  521:     */     throws CompileError
/*  522:     */   {
/*  523: 511 */     int op = CodeGen.getCompOperator(expr);
/*  524: 512 */     if (op == 358)
/*  525:     */     {
/*  526: 513 */       BinExpr bexpr = (BinExpr)expr;
/*  527: 514 */       bexpr.oprand1().accept(this);
/*  528: 515 */       int type1 = this.exprType;
/*  529: 516 */       int dim1 = this.arrayDim;
/*  530: 517 */       bexpr.oprand2().accept(this);
/*  531: 518 */       if ((dim1 == 0) && (this.arrayDim == 0)) {
/*  532: 519 */         insertCast(bexpr, type1, this.exprType);
/*  533:     */       }
/*  534:     */     }
/*  535: 521 */     else if (op == 33)
/*  536:     */     {
/*  537: 522 */       ((Expr)expr).oprand1().accept(this);
/*  538:     */     }
/*  539: 523 */     else if ((op == 369) || (op == 368))
/*  540:     */     {
/*  541: 524 */       BinExpr bexpr = (BinExpr)expr;
/*  542: 525 */       bexpr.oprand1().accept(this);
/*  543: 526 */       bexpr.oprand2().accept(this);
/*  544:     */     }
/*  545:     */     else
/*  546:     */     {
/*  547: 529 */       expr.accept(this);
/*  548:     */     }
/*  549: 531 */     this.exprType = 301;
/*  550: 532 */     this.arrayDim = 0;
/*  551:     */   }
/*  552:     */   
/*  553:     */   private void insertCast(BinExpr expr, int type1, int type2)
/*  554:     */     throws CompileError
/*  555:     */   {
/*  556: 538 */     if (CodeGen.rightIsStrong(type1, type2)) {
/*  557: 539 */       expr.setLeft(new CastExpr(type2, 0, expr.oprand1()));
/*  558:     */     } else {
/*  559: 541 */       this.exprType = type1;
/*  560:     */     }
/*  561:     */   }
/*  562:     */   
/*  563:     */   public void atCastExpr(CastExpr expr)
/*  564:     */     throws CompileError
/*  565:     */   {
/*  566: 545 */     String cname = resolveClassName(expr.getClassName());
/*  567: 546 */     expr.getOprand().accept(this);
/*  568: 547 */     this.exprType = expr.getType();
/*  569: 548 */     this.arrayDim = expr.getArrayDim();
/*  570: 549 */     this.className = cname;
/*  571:     */   }
/*  572:     */   
/*  573:     */   public void atInstanceOfExpr(InstanceOfExpr expr)
/*  574:     */     throws CompileError
/*  575:     */   {
/*  576: 553 */     expr.getOprand().accept(this);
/*  577: 554 */     this.exprType = 301;
/*  578: 555 */     this.arrayDim = 0;
/*  579:     */   }
/*  580:     */   
/*  581:     */   public void atExpr(Expr expr)
/*  582:     */     throws CompileError
/*  583:     */   {
/*  584: 562 */     int token = expr.getOperator();
/*  585: 563 */     ASTree oprand = expr.oprand1();
/*  586: 564 */     if (token == 46)
/*  587:     */     {
/*  588: 565 */       String member = ((Symbol)expr.oprand2()).get();
/*  589: 566 */       if (member.equals("length")) {
/*  590: 567 */         atArrayLength(expr);
/*  591: 568 */       } else if (member.equals("class")) {
/*  592: 569 */         atClassObject(expr);
/*  593:     */       } else {
/*  594: 571 */         atFieldRead(expr);
/*  595:     */       }
/*  596:     */     }
/*  597: 573 */     else if (token == 35)
/*  598:     */     {
/*  599: 574 */       String member = ((Symbol)expr.oprand2()).get();
/*  600: 575 */       if (member.equals("class")) {
/*  601: 576 */         atClassObject(expr);
/*  602:     */       } else {
/*  603: 578 */         atFieldRead(expr);
/*  604:     */       }
/*  605:     */     }
/*  606: 580 */     else if (token == 65)
/*  607:     */     {
/*  608: 581 */       atArrayRead(oprand, expr.oprand2());
/*  609:     */     }
/*  610: 582 */     else if ((token == 362) || (token == 363))
/*  611:     */     {
/*  612: 583 */       atPlusPlus(token, oprand, expr);
/*  613:     */     }
/*  614: 584 */     else if (token == 33)
/*  615:     */     {
/*  616: 585 */       booleanExpr(expr);
/*  617:     */     }
/*  618: 586 */     else if (token == 67)
/*  619:     */     {
/*  620: 587 */       fatal();
/*  621:     */     }
/*  622:     */     else
/*  623:     */     {
/*  624: 589 */       oprand.accept(this);
/*  625: 590 */       if ((!isConstant(expr, token, oprand)) && 
/*  626: 591 */         ((token == 45) || (token == 126)) && 
/*  627: 592 */         (CodeGen.isP_INT(this.exprType))) {
/*  628: 593 */         this.exprType = 324;
/*  629:     */       }
/*  630:     */     }
/*  631:     */   }
/*  632:     */   
/*  633:     */   private boolean isConstant(Expr expr, int op, ASTree oprand)
/*  634:     */   {
/*  635: 598 */     oprand = stripPlusExpr(oprand);
/*  636: 599 */     if ((oprand instanceof IntConst))
/*  637:     */     {
/*  638: 600 */       IntConst c = (IntConst)oprand;
/*  639: 601 */       long v = c.get();
/*  640: 602 */       if (op == 45) {
/*  641: 603 */         v = -v;
/*  642: 604 */       } else if (op == 126) {
/*  643: 605 */         v ^= 0xFFFFFFFF;
/*  644:     */       } else {
/*  645: 607 */         return false;
/*  646:     */       }
/*  647: 609 */       c.set(v);
/*  648:     */     }
/*  649: 611 */     else if ((oprand instanceof DoubleConst))
/*  650:     */     {
/*  651: 612 */       DoubleConst c = (DoubleConst)oprand;
/*  652: 613 */       if (op == 45) {
/*  653: 614 */         c.set(-c.get());
/*  654:     */       } else {
/*  655: 616 */         return false;
/*  656:     */       }
/*  657:     */     }
/*  658:     */     else
/*  659:     */     {
/*  660: 619 */       return false;
/*  661:     */     }
/*  662: 621 */     expr.setOperator(43);
/*  663: 622 */     return true;
/*  664:     */   }
/*  665:     */   
/*  666:     */   public void atCallExpr(CallExpr expr)
/*  667:     */     throws CompileError
/*  668:     */   {
/*  669: 626 */     String mname = null;
/*  670: 627 */     CtClass targetClass = null;
/*  671: 628 */     ASTree method = expr.oprand1();
/*  672: 629 */     ASTList args = (ASTList)expr.oprand2();
/*  673: 631 */     if ((method instanceof Member))
/*  674:     */     {
/*  675: 632 */       mname = ((Member)method).get();
/*  676: 633 */       targetClass = this.thisClass;
/*  677:     */     }
/*  678: 635 */     else if ((method instanceof Keyword))
/*  679:     */     {
/*  680: 636 */       mname = "<init>";
/*  681: 637 */       if (((Keyword)method).get() == 336) {
/*  682: 638 */         targetClass = MemberResolver.getSuperclass(this.thisClass);
/*  683:     */       } else {
/*  684: 640 */         targetClass = this.thisClass;
/*  685:     */       }
/*  686:     */     }
/*  687: 642 */     else if ((method instanceof Expr))
/*  688:     */     {
/*  689: 643 */       Expr e = (Expr)method;
/*  690: 644 */       mname = ((Symbol)e.oprand2()).get();
/*  691: 645 */       int op = e.getOperator();
/*  692: 646 */       if (op == 35)
/*  693:     */       {
/*  694: 647 */         targetClass = this.resolver.lookupClass(((Symbol)e.oprand1()).get(), false);
/*  695:     */       }
/*  696: 650 */       else if (op == 46)
/*  697:     */       {
/*  698: 651 */         ASTree target = e.oprand1();
/*  699:     */         try
/*  700:     */         {
/*  701: 653 */           target.accept(this);
/*  702:     */         }
/*  703:     */         catch (NoFieldException nfe)
/*  704:     */         {
/*  705: 656 */           if (nfe.getExpr() != target) {
/*  706: 657 */             throw nfe;
/*  707:     */           }
/*  708: 660 */           this.exprType = 307;
/*  709: 661 */           this.arrayDim = 0;
/*  710: 662 */           this.className = nfe.getField();
/*  711: 663 */           e.setOperator(35);
/*  712: 664 */           e.setOprand1(new Symbol(MemberResolver.jvmToJavaName(this.className)));
/*  713:     */         }
/*  714: 668 */         if (this.arrayDim > 0) {
/*  715: 669 */           targetClass = this.resolver.lookupClass("java.lang.Object", true);
/*  716: 670 */         } else if (this.exprType == 307) {
/*  717: 671 */           targetClass = this.resolver.lookupClassByJvmName(this.className);
/*  718:     */         } else {
/*  719: 673 */           badMethod();
/*  720:     */         }
/*  721:     */       }
/*  722:     */       else
/*  723:     */       {
/*  724: 676 */         badMethod();
/*  725:     */       }
/*  726:     */     }
/*  727:     */     else
/*  728:     */     {
/*  729: 679 */       fatal();
/*  730:     */     }
/*  731: 681 */     MemberResolver.Method minfo = atMethodCallCore(targetClass, mname, args);
/*  732:     */     
/*  733: 683 */     expr.setMethod(minfo);
/*  734:     */   }
/*  735:     */   
/*  736:     */   private static void badMethod()
/*  737:     */     throws CompileError
/*  738:     */   {
/*  739: 687 */     throw new CompileError("bad method");
/*  740:     */   }
/*  741:     */   
/*  742:     */   public MemberResolver.Method atMethodCallCore(CtClass targetClass, String mname, ASTList args)
/*  743:     */     throws CompileError
/*  744:     */   {
/*  745: 698 */     int nargs = getMethodArgsLength(args);
/*  746: 699 */     int[] types = new int[nargs];
/*  747: 700 */     int[] dims = new int[nargs];
/*  748: 701 */     String[] cnames = new String[nargs];
/*  749: 702 */     atMethodArgs(args, types, dims, cnames);
/*  750:     */     
/*  751: 704 */     MemberResolver.Method found = this.resolver.lookupMethod(targetClass, this.thisClass, this.thisMethod, mname, types, dims, cnames);
/*  752: 707 */     if (found == null)
/*  753:     */     {
/*  754: 708 */       String clazz = targetClass.getName();
/*  755: 709 */       String signature = argTypesToString(types, dims, cnames);
/*  756:     */       String msg;
/*  757:     */       String msg;
/*  758: 711 */       if (mname.equals("<init>")) {
/*  759: 712 */         msg = "cannot find constructor " + clazz + signature;
/*  760:     */       } else {
/*  761: 714 */         msg = mname + signature + " not found in " + clazz;
/*  762:     */       }
/*  763: 716 */       throw new CompileError(msg);
/*  764:     */     }
/*  765: 719 */     String desc = found.info.getDescriptor();
/*  766: 720 */     setReturnType(desc);
/*  767: 721 */     return found;
/*  768:     */   }
/*  769:     */   
/*  770:     */   public int getMethodArgsLength(ASTList args)
/*  771:     */   {
/*  772: 725 */     return ASTList.length(args);
/*  773:     */   }
/*  774:     */   
/*  775:     */   public void atMethodArgs(ASTList args, int[] types, int[] dims, String[] cnames)
/*  776:     */     throws CompileError
/*  777:     */   {
/*  778: 730 */     int i = 0;
/*  779: 731 */     while (args != null)
/*  780:     */     {
/*  781: 732 */       ASTree a = args.head();
/*  782: 733 */       a.accept(this);
/*  783: 734 */       types[i] = this.exprType;
/*  784: 735 */       dims[i] = this.arrayDim;
/*  785: 736 */       cnames[i] = this.className;
/*  786: 737 */       i++;
/*  787: 738 */       args = args.tail();
/*  788:     */     }
/*  789:     */   }
/*  790:     */   
/*  791:     */   void setReturnType(String desc)
/*  792:     */     throws CompileError
/*  793:     */   {
/*  794: 743 */     int i = desc.indexOf(')');
/*  795: 744 */     if (i < 0) {
/*  796: 745 */       badMethod();
/*  797:     */     }
/*  798: 747 */     char c = desc.charAt(++i);
/*  799: 748 */     int dim = 0;
/*  800: 749 */     while (c == '[')
/*  801:     */     {
/*  802: 750 */       dim++;
/*  803: 751 */       c = desc.charAt(++i);
/*  804:     */     }
/*  805: 754 */     this.arrayDim = dim;
/*  806: 755 */     if (c == 'L')
/*  807:     */     {
/*  808: 756 */       int j = desc.indexOf(';', i + 1);
/*  809: 757 */       if (j < 0) {
/*  810: 758 */         badMethod();
/*  811:     */       }
/*  812: 760 */       this.exprType = 307;
/*  813: 761 */       this.className = desc.substring(i + 1, j);
/*  814:     */     }
/*  815:     */     else
/*  816:     */     {
/*  817: 764 */       this.exprType = MemberResolver.descToType(c);
/*  818: 765 */       this.className = null;
/*  819:     */     }
/*  820:     */   }
/*  821:     */   
/*  822:     */   private void atFieldRead(ASTree expr)
/*  823:     */     throws CompileError
/*  824:     */   {
/*  825: 770 */     atFieldRead(fieldAccess(expr));
/*  826:     */   }
/*  827:     */   
/*  828:     */   private void atFieldRead(CtField f)
/*  829:     */     throws CompileError
/*  830:     */   {
/*  831: 774 */     FieldInfo finfo = f.getFieldInfo2();
/*  832: 775 */     String type = finfo.getDescriptor();
/*  833:     */     
/*  834: 777 */     int i = 0;
/*  835: 778 */     int dim = 0;
/*  836: 779 */     char c = type.charAt(i);
/*  837: 780 */     while (c == '[')
/*  838:     */     {
/*  839: 781 */       dim++;
/*  840: 782 */       c = type.charAt(++i);
/*  841:     */     }
/*  842: 785 */     this.arrayDim = dim;
/*  843: 786 */     this.exprType = MemberResolver.descToType(c);
/*  844: 788 */     if (c == 'L') {
/*  845: 789 */       this.className = type.substring(i + 1, type.indexOf(';', i + 1));
/*  846:     */     } else {
/*  847: 791 */       this.className = null;
/*  848:     */     }
/*  849:     */   }
/*  850:     */   
/*  851:     */   protected CtField fieldAccess(ASTree expr)
/*  852:     */     throws CompileError
/*  853:     */   {
/*  854: 800 */     if ((expr instanceof Member))
/*  855:     */     {
/*  856: 801 */       Member mem = (Member)expr;
/*  857: 802 */       String name = mem.get();
/*  858:     */       try
/*  859:     */       {
/*  860: 804 */         CtField f = this.thisClass.getField(name);
/*  861: 805 */         if (Modifier.isStatic(f.getModifiers())) {
/*  862: 806 */           mem.setField(f);
/*  863:     */         }
/*  864: 808 */         return f;
/*  865:     */       }
/*  866:     */       catch (NotFoundException e)
/*  867:     */       {
/*  868: 812 */         throw new NoFieldException(name, expr);
/*  869:     */       }
/*  870:     */     }
/*  871: 815 */     if ((expr instanceof Expr))
/*  872:     */     {
/*  873: 816 */       Expr e = (Expr)expr;
/*  874: 817 */       int op = e.getOperator();
/*  875: 818 */       if (op == 35)
/*  876:     */       {
/*  877: 819 */         Member mem = (Member)e.oprand2();
/*  878: 820 */         CtField f = this.resolver.lookupField(((Symbol)e.oprand1()).get(), mem);
/*  879:     */         
/*  880: 822 */         mem.setField(f);
/*  881: 823 */         return f;
/*  882:     */       }
/*  883: 825 */       if (op == 46)
/*  884:     */       {
/*  885:     */         try
/*  886:     */         {
/*  887: 827 */           e.oprand1().accept(this);
/*  888:     */         }
/*  889:     */         catch (NoFieldException nfe)
/*  890:     */         {
/*  891: 830 */           if (nfe.getExpr() != e.oprand1()) {
/*  892: 831 */             throw nfe;
/*  893:     */           }
/*  894: 837 */           return fieldAccess2(e, nfe.getField());
/*  895:     */         }
/*  896: 840 */         CompileError err = null;
/*  897:     */         try
/*  898:     */         {
/*  899: 842 */           if ((this.exprType == 307) && (this.arrayDim == 0)) {
/*  900: 843 */             return this.resolver.lookupFieldByJvmName(this.className, (Symbol)e.oprand2());
/*  901:     */           }
/*  902:     */         }
/*  903:     */         catch (CompileError ce)
/*  904:     */         {
/*  905: 847 */           err = ce;
/*  906:     */         }
/*  907: 866 */         ASTree oprnd1 = e.oprand1();
/*  908: 867 */         if ((oprnd1 instanceof Symbol)) {
/*  909: 868 */           return fieldAccess2(e, ((Symbol)oprnd1).get());
/*  910:     */         }
/*  911: 870 */         if (err != null) {
/*  912: 871 */           throw err;
/*  913:     */         }
/*  914:     */       }
/*  915:     */     }
/*  916: 875 */     throw new CompileError("bad filed access");
/*  917:     */   }
/*  918:     */   
/*  919:     */   private CtField fieldAccess2(Expr e, String jvmClassName)
/*  920:     */     throws CompileError
/*  921:     */   {
/*  922: 879 */     Member fname = (Member)e.oprand2();
/*  923: 880 */     CtField f = this.resolver.lookupFieldByJvmName2(jvmClassName, fname, e);
/*  924: 881 */     e.setOperator(35);
/*  925: 882 */     e.setOprand1(new Symbol(MemberResolver.jvmToJavaName(jvmClassName)));
/*  926: 883 */     fname.setField(f);
/*  927: 884 */     return f;
/*  928:     */   }
/*  929:     */   
/*  930:     */   public void atClassObject(Expr expr)
/*  931:     */     throws CompileError
/*  932:     */   {
/*  933: 888 */     this.exprType = 307;
/*  934: 889 */     this.arrayDim = 0;
/*  935: 890 */     this.className = "java/lang/Class";
/*  936:     */   }
/*  937:     */   
/*  938:     */   public void atArrayLength(Expr expr)
/*  939:     */     throws CompileError
/*  940:     */   {
/*  941: 894 */     expr.oprand1().accept(this);
/*  942: 895 */     this.exprType = 324;
/*  943: 896 */     this.arrayDim = 0;
/*  944:     */   }
/*  945:     */   
/*  946:     */   public void atArrayRead(ASTree array, ASTree index)
/*  947:     */     throws CompileError
/*  948:     */   {
/*  949: 902 */     array.accept(this);
/*  950: 903 */     int type = this.exprType;
/*  951: 904 */     int dim = this.arrayDim;
/*  952: 905 */     String cname = this.className;
/*  953: 906 */     index.accept(this);
/*  954: 907 */     this.exprType = type;
/*  955: 908 */     this.arrayDim = (dim - 1);
/*  956: 909 */     this.className = cname;
/*  957:     */   }
/*  958:     */   
/*  959:     */   private void atPlusPlus(int token, ASTree oprand, Expr expr)
/*  960:     */     throws CompileError
/*  961:     */   {
/*  962: 915 */     boolean isPost = oprand == null;
/*  963: 916 */     if (isPost) {
/*  964: 917 */       oprand = expr.oprand2();
/*  965:     */     }
/*  966: 919 */     if ((oprand instanceof Variable))
/*  967:     */     {
/*  968: 920 */       Declarator d = ((Variable)oprand).getDeclarator();
/*  969: 921 */       this.exprType = d.getType();
/*  970: 922 */       this.arrayDim = d.getArrayDim();
/*  971:     */     }
/*  972:     */     else
/*  973:     */     {
/*  974: 925 */       if ((oprand instanceof Expr))
/*  975:     */       {
/*  976: 926 */         Expr e = (Expr)oprand;
/*  977: 927 */         if (e.getOperator() == 65)
/*  978:     */         {
/*  979: 928 */           atArrayRead(e.oprand1(), e.oprand2());
/*  980:     */           
/*  981: 930 */           int t = this.exprType;
/*  982: 931 */           if ((t == 324) || (t == 303) || (t == 306) || (t == 334)) {
/*  983: 932 */             this.exprType = 324;
/*  984:     */           }
/*  985: 934 */           return;
/*  986:     */         }
/*  987:     */       }
/*  988: 938 */       atFieldPlusPlus(oprand);
/*  989:     */     }
/*  990:     */   }
/*  991:     */   
/*  992:     */   protected void atFieldPlusPlus(ASTree oprand)
/*  993:     */     throws CompileError
/*  994:     */   {
/*  995: 944 */     CtField f = fieldAccess(oprand);
/*  996: 945 */     atFieldRead(f);
/*  997: 946 */     int t = this.exprType;
/*  998: 947 */     if ((t == 324) || (t == 303) || (t == 306) || (t == 334)) {
/*  999: 948 */       this.exprType = 324;
/* 1000:     */     }
/* 1001:     */   }
/* 1002:     */   
/* 1003:     */   public void atMember(Member mem)
/* 1004:     */     throws CompileError
/* 1005:     */   {
/* 1006: 952 */     atFieldRead(mem);
/* 1007:     */   }
/* 1008:     */   
/* 1009:     */   public void atVariable(Variable v)
/* 1010:     */     throws CompileError
/* 1011:     */   {
/* 1012: 956 */     Declarator d = v.getDeclarator();
/* 1013: 957 */     this.exprType = d.getType();
/* 1014: 958 */     this.arrayDim = d.getArrayDim();
/* 1015: 959 */     this.className = d.getClassName();
/* 1016:     */   }
/* 1017:     */   
/* 1018:     */   public void atKeyword(Keyword k)
/* 1019:     */     throws CompileError
/* 1020:     */   {
/* 1021: 963 */     this.arrayDim = 0;
/* 1022: 964 */     int token = k.get();
/* 1023: 965 */     switch (token)
/* 1024:     */     {
/* 1025:     */     case 410: 
/* 1026:     */     case 411: 
/* 1027: 968 */       this.exprType = 301;
/* 1028: 969 */       break;
/* 1029:     */     case 412: 
/* 1030: 971 */       this.exprType = 412;
/* 1031: 972 */       break;
/* 1032:     */     case 336: 
/* 1033:     */     case 339: 
/* 1034: 975 */       this.exprType = 307;
/* 1035: 976 */       if (token == 339) {
/* 1036: 977 */         this.className = getThisName();
/* 1037:     */       } else {
/* 1038: 979 */         this.className = getSuperName();
/* 1039:     */       }
/* 1040: 980 */       break;
/* 1041:     */     default: 
/* 1042: 982 */       fatal();
/* 1043:     */     }
/* 1044:     */   }
/* 1045:     */   
/* 1046:     */   public void atStringL(StringL s)
/* 1047:     */     throws CompileError
/* 1048:     */   {
/* 1049: 987 */     this.exprType = 307;
/* 1050: 988 */     this.arrayDim = 0;
/* 1051: 989 */     this.className = "java/lang/String";
/* 1052:     */   }
/* 1053:     */   
/* 1054:     */   public void atIntConst(IntConst i)
/* 1055:     */     throws CompileError
/* 1056:     */   {
/* 1057: 993 */     this.arrayDim = 0;
/* 1058: 994 */     int type = i.getType();
/* 1059: 995 */     if ((type == 402) || (type == 401)) {
/* 1060: 996 */       this.exprType = (type == 402 ? 324 : 306);
/* 1061:     */     } else {
/* 1062: 998 */       this.exprType = 326;
/* 1063:     */     }
/* 1064:     */   }
/* 1065:     */   
/* 1066:     */   public void atDoubleConst(DoubleConst d)
/* 1067:     */     throws CompileError
/* 1068:     */   {
/* 1069:1002 */     this.arrayDim = 0;
/* 1070:1003 */     if (d.getType() == 405) {
/* 1071:1004 */       this.exprType = 312;
/* 1072:     */     } else {
/* 1073:1006 */       this.exprType = 317;
/* 1074:     */     }
/* 1075:     */   }
/* 1076:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.compiler.TypeChecker
 * JD-Core Version:    0.7.0.1
 */