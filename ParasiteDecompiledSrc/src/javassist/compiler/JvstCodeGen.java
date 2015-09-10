/*   1:    */ package javassist.compiler;
/*   2:    */ 
/*   3:    */ import javassist.ClassPool;
/*   4:    */ import javassist.CtClass;
/*   5:    */ import javassist.CtPrimitiveType;
/*   6:    */ import javassist.NotFoundException;
/*   7:    */ import javassist.bytecode.Bytecode;
/*   8:    */ import javassist.bytecode.Descriptor;
/*   9:    */ import javassist.compiler.ast.ASTList;
/*  10:    */ import javassist.compiler.ast.ASTree;
/*  11:    */ import javassist.compiler.ast.CallExpr;
/*  12:    */ import javassist.compiler.ast.CastExpr;
/*  13:    */ import javassist.compiler.ast.Declarator;
/*  14:    */ import javassist.compiler.ast.Expr;
/*  15:    */ import javassist.compiler.ast.Member;
/*  16:    */ import javassist.compiler.ast.Stmnt;
/*  17:    */ import javassist.compiler.ast.Symbol;
/*  18:    */ 
/*  19:    */ public class JvstCodeGen
/*  20:    */   extends MemberCodeGen
/*  21:    */ {
/*  22: 26 */   String paramArrayName = null;
/*  23: 27 */   String paramListName = null;
/*  24: 28 */   CtClass[] paramTypeList = null;
/*  25: 29 */   private int paramVarBase = 0;
/*  26: 30 */   private boolean useParam0 = false;
/*  27: 31 */   private String param0Type = null;
/*  28:    */   public static final String sigName = "$sig";
/*  29:    */   public static final String dollarTypeName = "$type";
/*  30:    */   public static final String clazzName = "$class";
/*  31: 35 */   private CtClass dollarType = null;
/*  32: 36 */   CtClass returnType = null;
/*  33: 37 */   String returnCastName = null;
/*  34: 38 */   private String returnVarName = null;
/*  35:    */   public static final String wrapperCastName = "$w";
/*  36: 40 */   String proceedName = null;
/*  37:    */   public static final String cflowName = "$cflow";
/*  38: 42 */   ProceedHandler procHandler = null;
/*  39:    */   
/*  40:    */   public JvstCodeGen(Bytecode b, CtClass cc, ClassPool cp)
/*  41:    */   {
/*  42: 45 */     super(b, cc, cp);
/*  43: 46 */     setTypeChecker(new JvstTypeChecker(cc, cp, this));
/*  44:    */   }
/*  45:    */   
/*  46:    */   private int indexOfParam1()
/*  47:    */   {
/*  48: 52 */     return this.paramVarBase + (this.useParam0 ? 1 : 0);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void setProceedHandler(ProceedHandler h, String name)
/*  52:    */   {
/*  53: 61 */     this.proceedName = name;
/*  54: 62 */     this.procHandler = h;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void addNullIfVoid()
/*  58:    */   {
/*  59: 69 */     if (this.exprType == 344)
/*  60:    */     {
/*  61: 70 */       this.bytecode.addOpcode(1);
/*  62: 71 */       this.exprType = 307;
/*  63: 72 */       this.arrayDim = 0;
/*  64: 73 */       this.className = "java/lang/Object";
/*  65:    */     }
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void atMember(Member mem)
/*  69:    */     throws CompileError
/*  70:    */   {
/*  71: 81 */     String name = mem.get();
/*  72: 82 */     if (name.equals(this.paramArrayName))
/*  73:    */     {
/*  74: 83 */       compileParameterList(this.bytecode, this.paramTypeList, indexOfParam1());
/*  75: 84 */       this.exprType = 307;
/*  76: 85 */       this.arrayDim = 1;
/*  77: 86 */       this.className = "java/lang/Object";
/*  78:    */     }
/*  79: 88 */     else if (name.equals("$sig"))
/*  80:    */     {
/*  81: 89 */       this.bytecode.addLdc(Descriptor.ofMethod(this.returnType, this.paramTypeList));
/*  82: 90 */       this.bytecode.addInvokestatic("javassist/runtime/Desc", "getParams", "(Ljava/lang/String;)[Ljava/lang/Class;");
/*  83:    */       
/*  84: 92 */       this.exprType = 307;
/*  85: 93 */       this.arrayDim = 1;
/*  86: 94 */       this.className = "java/lang/Class";
/*  87:    */     }
/*  88: 96 */     else if (name.equals("$type"))
/*  89:    */     {
/*  90: 97 */       if (this.dollarType == null) {
/*  91: 98 */         throw new CompileError("$type is not available");
/*  92:    */       }
/*  93:100 */       this.bytecode.addLdc(Descriptor.of(this.dollarType));
/*  94:101 */       callGetType("getType");
/*  95:    */     }
/*  96:103 */     else if (name.equals("$class"))
/*  97:    */     {
/*  98:104 */       if (this.param0Type == null) {
/*  99:105 */         throw new CompileError("$class is not available");
/* 100:    */       }
/* 101:107 */       this.bytecode.addLdc(this.param0Type);
/* 102:108 */       callGetType("getClazz");
/* 103:    */     }
/* 104:    */     else
/* 105:    */     {
/* 106:111 */       super.atMember(mem);
/* 107:    */     }
/* 108:    */   }
/* 109:    */   
/* 110:    */   private void callGetType(String method)
/* 111:    */   {
/* 112:115 */     this.bytecode.addInvokestatic("javassist/runtime/Desc", method, "(Ljava/lang/String;)Ljava/lang/Class;");
/* 113:    */     
/* 114:117 */     this.exprType = 307;
/* 115:118 */     this.arrayDim = 0;
/* 116:119 */     this.className = "java/lang/Class";
/* 117:    */   }
/* 118:    */   
/* 119:    */   protected void atFieldAssign(Expr expr, int op, ASTree left, ASTree right, boolean doDup)
/* 120:    */     throws CompileError
/* 121:    */   {
/* 122:125 */     if (((left instanceof Member)) && (((Member)left).get().equals(this.paramArrayName)))
/* 123:    */     {
/* 124:127 */       if (op != 61) {
/* 125:128 */         throw new CompileError("bad operator for " + this.paramArrayName);
/* 126:    */       }
/* 127:130 */       right.accept(this);
/* 128:131 */       if ((this.arrayDim != 1) || (this.exprType != 307)) {
/* 129:132 */         throw new CompileError("invalid type for " + this.paramArrayName);
/* 130:    */       }
/* 131:134 */       atAssignParamList(this.paramTypeList, this.bytecode);
/* 132:135 */       if (!doDup) {
/* 133:136 */         this.bytecode.addOpcode(87);
/* 134:    */       }
/* 135:    */     }
/* 136:    */     else
/* 137:    */     {
/* 138:139 */       super.atFieldAssign(expr, op, left, right, doDup);
/* 139:    */     }
/* 140:    */   }
/* 141:    */   
/* 142:    */   protected void atAssignParamList(CtClass[] params, Bytecode code)
/* 143:    */     throws CompileError
/* 144:    */   {
/* 145:145 */     if (params == null) {
/* 146:146 */       return;
/* 147:    */     }
/* 148:148 */     int varNo = indexOfParam1();
/* 149:149 */     int n = params.length;
/* 150:150 */     for (int i = 0; i < n; i++)
/* 151:    */     {
/* 152:151 */       code.addOpcode(89);
/* 153:152 */       code.addIconst(i);
/* 154:153 */       code.addOpcode(50);
/* 155:154 */       compileUnwrapValue(params[i], code);
/* 156:155 */       code.addStore(varNo, params[i]);
/* 157:156 */       varNo += (is2word(this.exprType, this.arrayDim) ? 2 : 1);
/* 158:    */     }
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void atCastExpr(CastExpr expr)
/* 162:    */     throws CompileError
/* 163:    */   {
/* 164:161 */     ASTList classname = expr.getClassName();
/* 165:162 */     if ((classname != null) && (expr.getArrayDim() == 0))
/* 166:    */     {
/* 167:163 */       ASTree p = classname.head();
/* 168:164 */       if (((p instanceof Symbol)) && (classname.tail() == null))
/* 169:    */       {
/* 170:165 */         String typename = ((Symbol)p).get();
/* 171:166 */         if (typename.equals(this.returnCastName))
/* 172:    */         {
/* 173:167 */           atCastToRtype(expr);
/* 174:168 */           return;
/* 175:    */         }
/* 176:170 */         if (typename.equals("$w"))
/* 177:    */         {
/* 178:171 */           atCastToWrapper(expr);
/* 179:172 */           return;
/* 180:    */         }
/* 181:    */       }
/* 182:    */     }
/* 183:177 */     super.atCastExpr(expr);
/* 184:    */   }
/* 185:    */   
/* 186:    */   protected void atCastToRtype(CastExpr expr)
/* 187:    */     throws CompileError
/* 188:    */   {
/* 189:185 */     expr.getOprand().accept(this);
/* 190:186 */     if ((this.exprType == 344) || (isRefType(this.exprType)) || (this.arrayDim > 0))
/* 191:    */     {
/* 192:187 */       compileUnwrapValue(this.returnType, this.bytecode);
/* 193:    */     }
/* 194:188 */     else if ((this.returnType instanceof CtPrimitiveType))
/* 195:    */     {
/* 196:189 */       CtPrimitiveType pt = (CtPrimitiveType)this.returnType;
/* 197:190 */       int destType = MemberResolver.descToType(pt.getDescriptor());
/* 198:191 */       atNumCastExpr(this.exprType, destType);
/* 199:192 */       this.exprType = destType;
/* 200:193 */       this.arrayDim = 0;
/* 201:194 */       this.className = null;
/* 202:    */     }
/* 203:    */     else
/* 204:    */     {
/* 205:197 */       throw new CompileError("invalid cast");
/* 206:    */     }
/* 207:    */   }
/* 208:    */   
/* 209:    */   protected void atCastToWrapper(CastExpr expr)
/* 210:    */     throws CompileError
/* 211:    */   {
/* 212:201 */     expr.getOprand().accept(this);
/* 213:202 */     if ((isRefType(this.exprType)) || (this.arrayDim > 0)) {
/* 214:203 */       return;
/* 215:    */     }
/* 216:205 */     CtClass clazz = this.resolver.lookupClass(this.exprType, this.arrayDim, this.className);
/* 217:206 */     if ((clazz instanceof CtPrimitiveType))
/* 218:    */     {
/* 219:207 */       CtPrimitiveType pt = (CtPrimitiveType)clazz;
/* 220:208 */       String wrapper = pt.getWrapperName();
/* 221:209 */       this.bytecode.addNew(wrapper);
/* 222:210 */       this.bytecode.addOpcode(89);
/* 223:211 */       if (pt.getDataSize() > 1) {
/* 224:212 */         this.bytecode.addOpcode(94);
/* 225:    */       } else {
/* 226:214 */         this.bytecode.addOpcode(93);
/* 227:    */       }
/* 228:216 */       this.bytecode.addOpcode(88);
/* 229:217 */       this.bytecode.addInvokespecial(wrapper, "<init>", "(" + pt.getDescriptor() + ")V");
/* 230:    */       
/* 231:    */ 
/* 232:220 */       this.exprType = 307;
/* 233:221 */       this.arrayDim = 0;
/* 234:222 */       this.className = "java/lang/Object";
/* 235:    */     }
/* 236:    */   }
/* 237:    */   
/* 238:    */   public void atCallExpr(CallExpr expr)
/* 239:    */     throws CompileError
/* 240:    */   {
/* 241:230 */     ASTree method = expr.oprand1();
/* 242:231 */     if ((method instanceof Member))
/* 243:    */     {
/* 244:232 */       String name = ((Member)method).get();
/* 245:233 */       if ((this.procHandler != null) && (name.equals(this.proceedName)))
/* 246:    */       {
/* 247:234 */         this.procHandler.doit(this, this.bytecode, (ASTList)expr.oprand2());
/* 248:235 */         return;
/* 249:    */       }
/* 250:237 */       if (name.equals("$cflow"))
/* 251:    */       {
/* 252:238 */         atCflow((ASTList)expr.oprand2());
/* 253:239 */         return;
/* 254:    */       }
/* 255:    */     }
/* 256:243 */     super.atCallExpr(expr);
/* 257:    */   }
/* 258:    */   
/* 259:    */   protected void atCflow(ASTList cname)
/* 260:    */     throws CompileError
/* 261:    */   {
/* 262:249 */     StringBuffer sbuf = new StringBuffer();
/* 263:250 */     if ((cname == null) || (cname.tail() != null)) {
/* 264:251 */       throw new CompileError("bad $cflow");
/* 265:    */     }
/* 266:253 */     makeCflowName(sbuf, cname.head());
/* 267:254 */     String name = sbuf.toString();
/* 268:255 */     Object[] names = this.resolver.getClassPool().lookupCflow(name);
/* 269:256 */     if (names == null) {
/* 270:257 */       throw new CompileError("no such a $cflow: " + name);
/* 271:    */     }
/* 272:259 */     this.bytecode.addGetstatic((String)names[0], (String)names[1], "Ljavassist/runtime/Cflow;");
/* 273:    */     
/* 274:261 */     this.bytecode.addInvokevirtual("javassist.runtime.Cflow", "value", "()I");
/* 275:    */     
/* 276:263 */     this.exprType = 324;
/* 277:264 */     this.arrayDim = 0;
/* 278:265 */     this.className = null;
/* 279:    */   }
/* 280:    */   
/* 281:    */   private static void makeCflowName(StringBuffer sbuf, ASTree name)
/* 282:    */     throws CompileError
/* 283:    */   {
/* 284:276 */     if ((name instanceof Symbol))
/* 285:    */     {
/* 286:277 */       sbuf.append(((Symbol)name).get());
/* 287:278 */       return;
/* 288:    */     }
/* 289:280 */     if ((name instanceof Expr))
/* 290:    */     {
/* 291:281 */       Expr expr = (Expr)name;
/* 292:282 */       if (expr.getOperator() == 46)
/* 293:    */       {
/* 294:283 */         makeCflowName(sbuf, expr.oprand1());
/* 295:284 */         sbuf.append('.');
/* 296:285 */         makeCflowName(sbuf, expr.oprand2());
/* 297:286 */         return;
/* 298:    */       }
/* 299:    */     }
/* 300:290 */     throw new CompileError("bad $cflow");
/* 301:    */   }
/* 302:    */   
/* 303:    */   public boolean isParamListName(ASTList args)
/* 304:    */   {
/* 305:297 */     if ((this.paramTypeList != null) && (args != null) && (args.tail() == null))
/* 306:    */     {
/* 307:299 */       ASTree left = args.head();
/* 308:300 */       return ((left instanceof Member)) && (((Member)left).get().equals(this.paramListName));
/* 309:    */     }
/* 310:304 */     return false;
/* 311:    */   }
/* 312:    */   
/* 313:    */   public int getMethodArgsLength(ASTList args)
/* 314:    */   {
/* 315:317 */     String pname = this.paramListName;
/* 316:318 */     int n = 0;
/* 317:319 */     while (args != null)
/* 318:    */     {
/* 319:320 */       ASTree a = args.head();
/* 320:321 */       if (((a instanceof Member)) && (((Member)a).get().equals(pname)))
/* 321:    */       {
/* 322:322 */         if (this.paramTypeList != null) {
/* 323:323 */           n += this.paramTypeList.length;
/* 324:    */         }
/* 325:    */       }
/* 326:    */       else {
/* 327:326 */         n++;
/* 328:    */       }
/* 329:328 */       args = args.tail();
/* 330:    */     }
/* 331:331 */     return n;
/* 332:    */   }
/* 333:    */   
/* 334:    */   public void atMethodArgs(ASTList args, int[] types, int[] dims, String[] cnames)
/* 335:    */     throws CompileError
/* 336:    */   {
/* 337:336 */     CtClass[] params = this.paramTypeList;
/* 338:337 */     String pname = this.paramListName;
/* 339:338 */     int i = 0;
/* 340:339 */     while (args != null)
/* 341:    */     {
/* 342:340 */       ASTree a = args.head();
/* 343:341 */       if (((a instanceof Member)) && (((Member)a).get().equals(pname)))
/* 344:    */       {
/* 345:342 */         if (params != null)
/* 346:    */         {
/* 347:343 */           int n = params.length;
/* 348:344 */           int regno = indexOfParam1();
/* 349:345 */           for (int k = 0; k < n; k++)
/* 350:    */           {
/* 351:346 */             CtClass p = params[k];
/* 352:347 */             regno += this.bytecode.addLoad(regno, p);
/* 353:348 */             setType(p);
/* 354:349 */             types[i] = this.exprType;
/* 355:350 */             dims[i] = this.arrayDim;
/* 356:351 */             cnames[i] = this.className;
/* 357:352 */             i++;
/* 358:    */           }
/* 359:    */         }
/* 360:    */       }
/* 361:    */       else
/* 362:    */       {
/* 363:357 */         a.accept(this);
/* 364:358 */         types[i] = this.exprType;
/* 365:359 */         dims[i] = this.arrayDim;
/* 366:360 */         cnames[i] = this.className;
/* 367:361 */         i++;
/* 368:    */       }
/* 369:364 */       args = args.tail();
/* 370:    */     }
/* 371:    */   }
/* 372:    */   
/* 373:    */   void compileInvokeSpecial(ASTree target, String classname, String methodname, String descriptor, ASTList args)
/* 374:    */     throws CompileError
/* 375:    */   {
/* 376:400 */     target.accept(this);
/* 377:401 */     int nargs = getMethodArgsLength(args);
/* 378:402 */     atMethodArgs(args, new int[nargs], new int[nargs], new String[nargs]);
/* 379:    */     
/* 380:404 */     this.bytecode.addInvokespecial(classname, methodname, descriptor);
/* 381:405 */     setReturnType(descriptor, false, false);
/* 382:406 */     addNullIfVoid();
/* 383:    */   }
/* 384:    */   
/* 385:    */   protected void atReturnStmnt(Stmnt st)
/* 386:    */     throws CompileError
/* 387:    */   {
/* 388:413 */     ASTree result = st.getLeft();
/* 389:414 */     if ((result != null) && (this.returnType == CtClass.voidType))
/* 390:    */     {
/* 391:415 */       compileExpr(result);
/* 392:416 */       if (is2word(this.exprType, this.arrayDim)) {
/* 393:417 */         this.bytecode.addOpcode(88);
/* 394:418 */       } else if (this.exprType != 344) {
/* 395:419 */         this.bytecode.addOpcode(87);
/* 396:    */       }
/* 397:421 */       result = null;
/* 398:    */     }
/* 399:424 */     atReturnStmnt2(result);
/* 400:    */   }
/* 401:    */   
/* 402:    */   public int recordReturnType(CtClass type, String castName, String resultName, SymbolTable tbl)
/* 403:    */     throws CompileError
/* 404:    */   {
/* 405:440 */     this.returnType = type;
/* 406:441 */     this.returnCastName = castName;
/* 407:442 */     this.returnVarName = resultName;
/* 408:443 */     if (resultName == null) {
/* 409:444 */       return -1;
/* 410:    */     }
/* 411:446 */     int varNo = getMaxLocals();
/* 412:447 */     int locals = varNo + recordVar(type, resultName, varNo, tbl);
/* 413:448 */     setMaxLocals(locals);
/* 414:449 */     return varNo;
/* 415:    */   }
/* 416:    */   
/* 417:    */   public void recordType(CtClass t)
/* 418:    */   {
/* 419:457 */     this.dollarType = t;
/* 420:    */   }
/* 421:    */   
/* 422:    */   public int recordParams(CtClass[] params, boolean isStatic, String prefix, String paramVarName, String paramsName, SymbolTable tbl)
/* 423:    */     throws CompileError
/* 424:    */   {
/* 425:470 */     return recordParams(params, isStatic, prefix, paramVarName, paramsName, !isStatic, 0, getThisName(), tbl);
/* 426:    */   }
/* 427:    */   
/* 428:    */   public int recordParams(CtClass[] params, boolean isStatic, String prefix, String paramVarName, String paramsName, boolean use0, int paramBase, String target, SymbolTable tbl)
/* 429:    */     throws CompileError
/* 430:    */   {
/* 431:501 */     this.paramTypeList = params;
/* 432:502 */     this.paramArrayName = paramVarName;
/* 433:503 */     this.paramListName = paramsName;
/* 434:504 */     this.paramVarBase = paramBase;
/* 435:505 */     this.useParam0 = use0;
/* 436:507 */     if (target != null) {
/* 437:508 */       this.param0Type = MemberResolver.jvmToJavaName(target);
/* 438:    */     }
/* 439:510 */     this.inStaticMethod = isStatic;
/* 440:511 */     int varNo = paramBase;
/* 441:512 */     if (use0)
/* 442:    */     {
/* 443:513 */       String varName = prefix + "0";
/* 444:514 */       Declarator decl = new Declarator(307, MemberResolver.javaToJvmName(target), 0, varNo++, new Symbol(varName));
/* 445:    */       
/* 446:    */ 
/* 447:517 */       tbl.append(varName, decl);
/* 448:    */     }
/* 449:520 */     for (int i = 0; i < params.length; i++) {
/* 450:521 */       varNo += recordVar(params[i], prefix + (i + 1), varNo, tbl);
/* 451:    */     }
/* 452:523 */     if (getMaxLocals() < varNo) {
/* 453:524 */       setMaxLocals(varNo);
/* 454:    */     }
/* 455:526 */     return varNo;
/* 456:    */   }
/* 457:    */   
/* 458:    */   public int recordVariable(CtClass type, String varName, SymbolTable tbl)
/* 459:    */     throws CompileError
/* 460:    */   {
/* 461:538 */     if (varName == null) {
/* 462:539 */       return -1;
/* 463:    */     }
/* 464:541 */     int varNo = getMaxLocals();
/* 465:542 */     int locals = varNo + recordVar(type, varName, varNo, tbl);
/* 466:543 */     setMaxLocals(locals);
/* 467:544 */     return varNo;
/* 468:    */   }
/* 469:    */   
/* 470:    */   private int recordVar(CtClass cc, String varName, int varNo, SymbolTable tbl)
/* 471:    */     throws CompileError
/* 472:    */   {
/* 473:551 */     if (cc == CtClass.voidType)
/* 474:    */     {
/* 475:552 */       this.exprType = 307;
/* 476:553 */       this.arrayDim = 0;
/* 477:554 */       this.className = "java/lang/Object";
/* 478:    */     }
/* 479:    */     else
/* 480:    */     {
/* 481:557 */       setType(cc);
/* 482:    */     }
/* 483:559 */     Declarator decl = new Declarator(this.exprType, this.className, this.arrayDim, varNo, new Symbol(varName));
/* 484:    */     
/* 485:    */ 
/* 486:562 */     tbl.append(varName, decl);
/* 487:563 */     return is2word(this.exprType, this.arrayDim) ? 2 : 1;
/* 488:    */   }
/* 489:    */   
/* 490:    */   public void recordVariable(String typeDesc, String varName, int varNo, SymbolTable tbl)
/* 491:    */     throws CompileError
/* 492:    */   {
/* 493:577 */     int dim = 0;
/* 494:    */     char c;
/* 495:578 */     while ((c = typeDesc.charAt(dim)) == '[') {
/* 496:579 */       dim++;
/* 497:    */     }
/* 498:581 */     int type = MemberResolver.descToType(c);
/* 499:582 */     String cname = null;
/* 500:583 */     if (type == 307) {
/* 501:584 */       if (dim == 0) {
/* 502:585 */         cname = typeDesc.substring(1, typeDesc.length() - 1);
/* 503:    */       } else {
/* 504:587 */         cname = typeDesc.substring(dim + 1, typeDesc.length() - 1);
/* 505:    */       }
/* 506:    */     }
/* 507:590 */     Declarator decl = new Declarator(type, cname, dim, varNo, new Symbol(varName));
/* 508:    */     
/* 509:592 */     tbl.append(varName, decl);
/* 510:    */   }
/* 511:    */   
/* 512:    */   public static int compileParameterList(Bytecode code, CtClass[] params, int regno)
/* 513:    */   {
/* 514:606 */     if (params == null)
/* 515:    */     {
/* 516:607 */       code.addIconst(0);
/* 517:608 */       code.addAnewarray("java.lang.Object");
/* 518:609 */       return 1;
/* 519:    */     }
/* 520:612 */     CtClass[] args = new CtClass[1];
/* 521:613 */     int n = params.length;
/* 522:614 */     code.addIconst(n);
/* 523:615 */     code.addAnewarray("java.lang.Object");
/* 524:616 */     for (int i = 0; i < n; i++)
/* 525:    */     {
/* 526:617 */       code.addOpcode(89);
/* 527:618 */       code.addIconst(i);
/* 528:619 */       if (params[i].isPrimitive())
/* 529:    */       {
/* 530:620 */         CtPrimitiveType pt = (CtPrimitiveType)params[i];
/* 531:621 */         String wrapper = pt.getWrapperName();
/* 532:622 */         code.addNew(wrapper);
/* 533:623 */         code.addOpcode(89);
/* 534:624 */         int s = code.addLoad(regno, pt);
/* 535:625 */         regno += s;
/* 536:626 */         args[0] = pt;
/* 537:627 */         code.addInvokespecial(wrapper, "<init>", Descriptor.ofMethod(CtClass.voidType, args));
/* 538:    */       }
/* 539:    */       else
/* 540:    */       {
/* 541:632 */         code.addAload(regno);
/* 542:633 */         regno++;
/* 543:    */       }
/* 544:636 */       code.addOpcode(83);
/* 545:    */     }
/* 546:639 */     return 8;
/* 547:    */   }
/* 548:    */   
/* 549:    */   protected void compileUnwrapValue(CtClass type, Bytecode code)
/* 550:    */     throws CompileError
/* 551:    */   {
/* 552:646 */     if (type == CtClass.voidType)
/* 553:    */     {
/* 554:647 */       addNullIfVoid();
/* 555:648 */       return;
/* 556:    */     }
/* 557:651 */     if (this.exprType == 344) {
/* 558:652 */       throw new CompileError("invalid type for " + this.returnCastName);
/* 559:    */     }
/* 560:654 */     if ((type instanceof CtPrimitiveType))
/* 561:    */     {
/* 562:655 */       CtPrimitiveType pt = (CtPrimitiveType)type;
/* 563:    */       
/* 564:657 */       String wrapper = pt.getWrapperName();
/* 565:658 */       code.addCheckcast(wrapper);
/* 566:659 */       code.addInvokevirtual(wrapper, pt.getGetMethodName(), pt.getGetMethodDescriptor());
/* 567:    */       
/* 568:661 */       setType(type);
/* 569:    */     }
/* 570:    */     else
/* 571:    */     {
/* 572:664 */       code.addCheckcast(type);
/* 573:665 */       setType(type);
/* 574:    */     }
/* 575:    */   }
/* 576:    */   
/* 577:    */   public void setType(CtClass type)
/* 578:    */     throws CompileError
/* 579:    */   {
/* 580:673 */     setType(type, 0);
/* 581:    */   }
/* 582:    */   
/* 583:    */   private void setType(CtClass type, int dim)
/* 584:    */     throws CompileError
/* 585:    */   {
/* 586:677 */     if (type.isPrimitive())
/* 587:    */     {
/* 588:678 */       CtPrimitiveType pt = (CtPrimitiveType)type;
/* 589:679 */       this.exprType = MemberResolver.descToType(pt.getDescriptor());
/* 590:680 */       this.arrayDim = dim;
/* 591:681 */       this.className = null;
/* 592:    */     }
/* 593:683 */     else if (type.isArray())
/* 594:    */     {
/* 595:    */       try
/* 596:    */       {
/* 597:685 */         setType(type.getComponentType(), dim + 1);
/* 598:    */       }
/* 599:    */       catch (NotFoundException e)
/* 600:    */       {
/* 601:688 */         throw new CompileError("undefined type: " + type.getName());
/* 602:    */       }
/* 603:    */     }
/* 604:    */     else
/* 605:    */     {
/* 606:691 */       this.exprType = 307;
/* 607:692 */       this.arrayDim = dim;
/* 608:693 */       this.className = MemberResolver.javaToJvmName(type.getName());
/* 609:    */     }
/* 610:    */   }
/* 611:    */   
/* 612:    */   public void doNumCast(CtClass type)
/* 613:    */     throws CompileError
/* 614:    */   {
/* 615:700 */     if ((this.arrayDim == 0) && (!isRefType(this.exprType))) {
/* 616:701 */       if ((type instanceof CtPrimitiveType))
/* 617:    */       {
/* 618:702 */         CtPrimitiveType pt = (CtPrimitiveType)type;
/* 619:703 */         atNumCastExpr(this.exprType, MemberResolver.descToType(pt.getDescriptor()));
/* 620:    */       }
/* 621:    */       else
/* 622:    */       {
/* 623:707 */         throw new CompileError("type mismatch");
/* 624:    */       }
/* 625:    */     }
/* 626:    */   }
/* 627:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.compiler.JvstCodeGen
 * JD-Core Version:    0.7.0.1
 */