/*   1:    */ package javassist.compiler;
/*   2:    */ 
/*   3:    */ import javassist.CannotCompileException;
/*   4:    */ import javassist.CtBehavior;
/*   5:    */ import javassist.CtClass;
/*   6:    */ import javassist.CtConstructor;
/*   7:    */ import javassist.CtField;
/*   8:    */ import javassist.CtMember;
/*   9:    */ import javassist.CtMethod;
/*  10:    */ import javassist.CtPrimitiveType;
/*  11:    */ import javassist.Modifier;
/*  12:    */ import javassist.NotFoundException;
/*  13:    */ import javassist.bytecode.BadBytecode;
/*  14:    */ import javassist.bytecode.Bytecode;
/*  15:    */ import javassist.bytecode.ClassFile;
/*  16:    */ import javassist.bytecode.CodeAttribute;
/*  17:    */ import javassist.bytecode.LocalVariableAttribute;
/*  18:    */ import javassist.bytecode.MethodInfo;
/*  19:    */ import javassist.compiler.ast.ASTList;
/*  20:    */ import javassist.compiler.ast.ASTree;
/*  21:    */ import javassist.compiler.ast.CallExpr;
/*  22:    */ import javassist.compiler.ast.Declarator;
/*  23:    */ import javassist.compiler.ast.Expr;
/*  24:    */ import javassist.compiler.ast.FieldDecl;
/*  25:    */ import javassist.compiler.ast.Member;
/*  26:    */ import javassist.compiler.ast.MethodDecl;
/*  27:    */ import javassist.compiler.ast.Stmnt;
/*  28:    */ import javassist.compiler.ast.Symbol;
/*  29:    */ 
/*  30:    */ public class Javac
/*  31:    */ {
/*  32:    */   JvstCodeGen gen;
/*  33:    */   SymbolTable stable;
/*  34:    */   private Bytecode bytecode;
/*  35:    */   public static final String param0Name = "$0";
/*  36:    */   public static final String resultVarName = "$_";
/*  37:    */   public static final String proceedName = "$proceed";
/*  38:    */   
/*  39:    */   public Javac(CtClass thisClass)
/*  40:    */   {
/*  41: 52 */     this(new Bytecode(thisClass.getClassFile2().getConstPool(), 0, 0), thisClass);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public Javac(Bytecode b, CtClass thisClass)
/*  45:    */   {
/*  46: 65 */     this.gen = new JvstCodeGen(b, thisClass, thisClass.getClassPool());
/*  47: 66 */     this.stable = new SymbolTable();
/*  48: 67 */     this.bytecode = b;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public Bytecode getBytecode()
/*  52:    */   {
/*  53: 73 */     return this.bytecode;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public CtMember compile(String src)
/*  57:    */     throws CompileError
/*  58:    */   {
/*  59: 88 */     Parser p = new Parser(new Lex(src));
/*  60: 89 */     ASTList mem = p.parseMember1(this.stable);
/*  61:    */     try
/*  62:    */     {
/*  63: 91 */       if ((mem instanceof FieldDecl)) {
/*  64: 92 */         return compileField((FieldDecl)mem);
/*  65:    */       }
/*  66: 94 */       CtBehavior cb = compileMethod(p, (MethodDecl)mem);
/*  67: 95 */       CtClass decl = cb.getDeclaringClass();
/*  68: 96 */       cb.getMethodInfo2().rebuildStackMapIf6(decl.getClassPool(), decl.getClassFile2());
/*  69:    */       
/*  70:    */ 
/*  71: 99 */       return cb;
/*  72:    */     }
/*  73:    */     catch (BadBytecode bb)
/*  74:    */     {
/*  75:103 */       throw new CompileError(bb.getMessage());
/*  76:    */     }
/*  77:    */     catch (CannotCompileException e)
/*  78:    */     {
/*  79:106 */       throw new CompileError(e.getMessage());
/*  80:    */     }
/*  81:    */   }
/*  82:    */   
/*  83:    */   public static class CtFieldWithInit
/*  84:    */     extends CtField
/*  85:    */   {
/*  86:    */     private ASTree init;
/*  87:    */     
/*  88:    */     CtFieldWithInit(CtClass type, String name, CtClass declaring)
/*  89:    */       throws CannotCompileException
/*  90:    */     {
/*  91:116 */       super(name, declaring);
/*  92:117 */       this.init = null;
/*  93:    */     }
/*  94:    */     
/*  95:    */     protected void setInit(ASTree i)
/*  96:    */     {
/*  97:120 */       this.init = i;
/*  98:    */     }
/*  99:    */     
/* 100:    */     protected ASTree getInitAST()
/* 101:    */     {
/* 102:123 */       return this.init;
/* 103:    */     }
/* 104:    */   }
/* 105:    */   
/* 106:    */   private CtField compileField(FieldDecl fd)
/* 107:    */     throws CompileError, CannotCompileException
/* 108:    */   {
/* 109:131 */     Declarator d = fd.getDeclarator();
/* 110:132 */     CtFieldWithInit f = new CtFieldWithInit(this.gen.resolver.lookupClass(d), d.getVariable().get(), this.gen.getThisClass());
/* 111:    */     
/* 112:134 */     f.setModifiers(MemberResolver.getModifiers(fd.getModifiers()));
/* 113:135 */     if (fd.getInit() != null) {
/* 114:136 */       f.setInit(fd.getInit());
/* 115:    */     }
/* 116:138 */     return f;
/* 117:    */   }
/* 118:    */   
/* 119:    */   private CtBehavior compileMethod(Parser p, MethodDecl md)
/* 120:    */     throws CompileError
/* 121:    */   {
/* 122:144 */     int mod = MemberResolver.getModifiers(md.getModifiers());
/* 123:145 */     CtClass[] plist = this.gen.makeParamList(md);
/* 124:146 */     CtClass[] tlist = this.gen.makeThrowsList(md);
/* 125:147 */     recordParams(plist, Modifier.isStatic(mod));
/* 126:148 */     md = p.parseMethod2(this.stable, md);
/* 127:    */     try
/* 128:    */     {
/* 129:150 */       if (md.isConstructor())
/* 130:    */       {
/* 131:151 */         CtConstructor cons = new CtConstructor(plist, this.gen.getThisClass());
/* 132:    */         
/* 133:153 */         cons.setModifiers(mod);
/* 134:154 */         md.accept(this.gen);
/* 135:155 */         cons.getMethodInfo().setCodeAttribute(this.bytecode.toCodeAttribute());
/* 136:    */         
/* 137:157 */         cons.setExceptionTypes(tlist);
/* 138:158 */         return cons;
/* 139:    */       }
/* 140:161 */       Declarator r = md.getReturn();
/* 141:162 */       CtClass rtype = this.gen.resolver.lookupClass(r);
/* 142:163 */       recordReturnType(rtype, false);
/* 143:164 */       CtMethod method = new CtMethod(rtype, r.getVariable().get(), plist, this.gen.getThisClass());
/* 144:    */       
/* 145:166 */       method.setModifiers(mod);
/* 146:167 */       this.gen.setThisMethod(method);
/* 147:168 */       md.accept(this.gen);
/* 148:169 */       if (md.getBody() != null) {
/* 149:170 */         method.getMethodInfo().setCodeAttribute(this.bytecode.toCodeAttribute());
/* 150:    */       } else {
/* 151:173 */         method.setModifiers(mod | 0x400);
/* 152:    */       }
/* 153:175 */       method.setExceptionTypes(tlist);
/* 154:176 */       return method;
/* 155:    */     }
/* 156:    */     catch (NotFoundException e)
/* 157:    */     {
/* 158:180 */       throw new CompileError(e.toString());
/* 159:    */     }
/* 160:    */   }
/* 161:    */   
/* 162:    */   public Bytecode compileBody(CtBehavior method, String src)
/* 163:    */     throws CompileError
/* 164:    */   {
/* 165:    */     try
/* 166:    */     {
/* 167:194 */       int mod = method.getModifiers();
/* 168:195 */       recordParams(method.getParameterTypes(), Modifier.isStatic(mod));
/* 169:    */       CtClass rtype;
/* 170:    */       CtClass rtype;
/* 171:198 */       if ((method instanceof CtMethod))
/* 172:    */       {
/* 173:199 */         this.gen.setThisMethod((CtMethod)method);
/* 174:200 */         rtype = ((CtMethod)method).getReturnType();
/* 175:    */       }
/* 176:    */       else
/* 177:    */       {
/* 178:203 */         rtype = CtClass.voidType;
/* 179:    */       }
/* 180:205 */       recordReturnType(rtype, false);
/* 181:206 */       boolean isVoid = rtype == CtClass.voidType;
/* 182:208 */       if (src == null)
/* 183:    */       {
/* 184:209 */         makeDefaultBody(this.bytecode, rtype);
/* 185:    */       }
/* 186:    */       else
/* 187:    */       {
/* 188:211 */         Parser p = new Parser(new Lex(src));
/* 189:212 */         SymbolTable stb = new SymbolTable(this.stable);
/* 190:213 */         Stmnt s = p.parseStatement(stb);
/* 191:214 */         if (p.hasMore()) {
/* 192:215 */           throw new CompileError("the method/constructor body must be surrounded by {}");
/* 193:    */         }
/* 194:218 */         boolean callSuper = false;
/* 195:219 */         if ((method instanceof CtConstructor)) {
/* 196:220 */           callSuper = !((CtConstructor)method).isClassInitializer();
/* 197:    */         }
/* 198:222 */         this.gen.atMethodBody(s, callSuper, isVoid);
/* 199:    */       }
/* 200:225 */       return this.bytecode;
/* 201:    */     }
/* 202:    */     catch (NotFoundException e)
/* 203:    */     {
/* 204:228 */       throw new CompileError(e.toString());
/* 205:    */     }
/* 206:    */   }
/* 207:    */   
/* 208:    */   private static void makeDefaultBody(Bytecode b, CtClass type)
/* 209:    */   {
/* 210:    */     int value;
/* 211:    */     int op;
/* 212:    */     int value;
/* 213:235 */     if ((type instanceof CtPrimitiveType))
/* 214:    */     {
/* 215:236 */       CtPrimitiveType pt = (CtPrimitiveType)type;
/* 216:237 */       int op = pt.getReturnOp();
/* 217:    */       int value;
/* 218:238 */       if (op == 175)
/* 219:    */       {
/* 220:239 */         value = 14;
/* 221:    */       }
/* 222:    */       else
/* 223:    */       {
/* 224:    */         int value;
/* 225:240 */         if (op == 174)
/* 226:    */         {
/* 227:241 */           value = 11;
/* 228:    */         }
/* 229:    */         else
/* 230:    */         {
/* 231:    */           int value;
/* 232:242 */           if (op == 173)
/* 233:    */           {
/* 234:243 */             value = 9;
/* 235:    */           }
/* 236:    */           else
/* 237:    */           {
/* 238:    */             int value;
/* 239:244 */             if (op == 177) {
/* 240:245 */               value = 0;
/* 241:    */             } else {
/* 242:247 */               value = 3;
/* 243:    */             }
/* 244:    */           }
/* 245:    */         }
/* 246:    */       }
/* 247:    */     }
/* 248:    */     else
/* 249:    */     {
/* 250:250 */       op = 176;
/* 251:251 */       value = 1;
/* 252:    */     }
/* 253:254 */     if (value != 0) {
/* 254:255 */       b.addOpcode(value);
/* 255:    */     }
/* 256:257 */     b.addOpcode(op);
/* 257:    */   }
/* 258:    */   
/* 259:    */   public boolean recordLocalVariables(CodeAttribute ca, int pc)
/* 260:    */     throws CompileError
/* 261:    */   {
/* 262:272 */     LocalVariableAttribute va = (LocalVariableAttribute)ca.getAttribute("LocalVariableTable");
/* 263:275 */     if (va == null) {
/* 264:276 */       return false;
/* 265:    */     }
/* 266:278 */     int n = va.tableLength();
/* 267:279 */     for (int i = 0; i < n; i++)
/* 268:    */     {
/* 269:280 */       int start = va.startPc(i);
/* 270:281 */       int len = va.codeLength(i);
/* 271:282 */       if ((start <= pc) && (pc < start + len)) {
/* 272:283 */         this.gen.recordVariable(va.descriptor(i), va.variableName(i), va.index(i), this.stable);
/* 273:    */       }
/* 274:    */     }
/* 275:287 */     return true;
/* 276:    */   }
/* 277:    */   
/* 278:    */   public boolean recordParamNames(CodeAttribute ca, int numOfLocalVars)
/* 279:    */     throws CompileError
/* 280:    */   {
/* 281:302 */     LocalVariableAttribute va = (LocalVariableAttribute)ca.getAttribute("LocalVariableTable");
/* 282:305 */     if (va == null) {
/* 283:306 */       return false;
/* 284:    */     }
/* 285:308 */     int n = va.tableLength();
/* 286:309 */     for (int i = 0; i < n; i++)
/* 287:    */     {
/* 288:310 */       int index = va.index(i);
/* 289:311 */       if (index < numOfLocalVars) {
/* 290:312 */         this.gen.recordVariable(va.descriptor(i), va.variableName(i), index, this.stable);
/* 291:    */       }
/* 292:    */     }
/* 293:316 */     return true;
/* 294:    */   }
/* 295:    */   
/* 296:    */   public int recordParams(CtClass[] params, boolean isStatic)
/* 297:    */     throws CompileError
/* 298:    */   {
/* 299:333 */     return this.gen.recordParams(params, isStatic, "$", "$args", "$$", this.stable);
/* 300:    */   }
/* 301:    */   
/* 302:    */   public int recordParams(String target, CtClass[] params, boolean use0, int varNo, boolean isStatic)
/* 303:    */     throws CompileError
/* 304:    */   {
/* 305:361 */     return this.gen.recordParams(params, isStatic, "$", "$args", "$$", use0, varNo, target, this.stable);
/* 306:    */   }
/* 307:    */   
/* 308:    */   public void setMaxLocals(int max)
/* 309:    */   {
/* 310:375 */     this.gen.setMaxLocals(max);
/* 311:    */   }
/* 312:    */   
/* 313:    */   public int recordReturnType(CtClass type, boolean useResultVar)
/* 314:    */     throws CompileError
/* 315:    */   {
/* 316:395 */     this.gen.recordType(type);
/* 317:396 */     return this.gen.recordReturnType(type, "$r", useResultVar ? "$_" : null, this.stable);
/* 318:    */   }
/* 319:    */   
/* 320:    */   public void recordType(CtClass t)
/* 321:    */   {
/* 322:407 */     this.gen.recordType(t);
/* 323:    */   }
/* 324:    */   
/* 325:    */   public int recordVariable(CtClass type, String name)
/* 326:    */     throws CompileError
/* 327:    */   {
/* 328:419 */     return this.gen.recordVariable(type, name, this.stable);
/* 329:    */   }
/* 330:    */   
/* 331:    */   public void recordProceed(String target, String method)
/* 332:    */     throws CompileError
/* 333:    */   {
/* 334:434 */     Parser p = new Parser(new Lex(target));
/* 335:435 */     final ASTree texpr = p.parseExpression(this.stable);
/* 336:436 */     final String m = method;
/* 337:    */     
/* 338:438 */     ProceedHandler h = new ProceedHandler()
/* 339:    */     {
/* 340:    */       private final String val$m;
/* 341:    */       private final ASTree val$texpr;
/* 342:    */       
/* 343:    */       public void doit(JvstCodeGen gen, Bytecode b, ASTList args)
/* 344:    */         throws CompileError
/* 345:    */       {
/* 346:442 */         ASTree expr = new Member(m);
/* 347:443 */         if (texpr != null) {
/* 348:444 */           expr = Expr.make(46, texpr, expr);
/* 349:    */         }
/* 350:446 */         expr = CallExpr.makeCall(expr, args);
/* 351:447 */         gen.compileExpr(expr);
/* 352:448 */         gen.addNullIfVoid();
/* 353:    */       }
/* 354:    */       
/* 355:    */       public void setReturnType(JvstTypeChecker check, ASTList args)
/* 356:    */         throws CompileError
/* 357:    */       {
/* 358:454 */         ASTree expr = new Member(m);
/* 359:455 */         if (texpr != null) {
/* 360:456 */           expr = Expr.make(46, texpr, expr);
/* 361:    */         }
/* 362:458 */         expr = CallExpr.makeCall(expr, args);
/* 363:459 */         expr.accept(check);
/* 364:460 */         check.addNullIfVoid();
/* 365:    */       }
/* 366:463 */     };
/* 367:464 */     this.gen.setProceedHandler(h, "$proceed");
/* 368:    */   }
/* 369:    */   
/* 370:    */   public void recordStaticProceed(String targetClass, String method)
/* 371:    */     throws CompileError
/* 372:    */   {
/* 373:479 */     final String c = targetClass;
/* 374:480 */     final String m = method;
/* 375:    */     
/* 376:482 */     ProceedHandler h = new ProceedHandler()
/* 377:    */     {
/* 378:    */       private final String val$c;
/* 379:    */       private final String val$m;
/* 380:    */       
/* 381:    */       public void doit(JvstCodeGen gen, Bytecode b, ASTList args)
/* 382:    */         throws CompileError
/* 383:    */       {
/* 384:486 */         Expr expr = Expr.make(35, new Symbol(c), new Member(m));
/* 385:    */         
/* 386:488 */         expr = CallExpr.makeCall(expr, args);
/* 387:489 */         gen.compileExpr(expr);
/* 388:490 */         gen.addNullIfVoid();
/* 389:    */       }
/* 390:    */       
/* 391:    */       public void setReturnType(JvstTypeChecker check, ASTList args)
/* 392:    */         throws CompileError
/* 393:    */       {
/* 394:496 */         Expr expr = Expr.make(35, new Symbol(c), new Member(m));
/* 395:    */         
/* 396:498 */         expr = CallExpr.makeCall(expr, args);
/* 397:499 */         expr.accept(check);
/* 398:500 */         check.addNullIfVoid();
/* 399:    */       }
/* 400:503 */     };
/* 401:504 */     this.gen.setProceedHandler(h, "$proceed");
/* 402:    */   }
/* 403:    */   
/* 404:    */   public void recordSpecialProceed(String target, String classname, String methodname, String descriptor)
/* 405:    */     throws CompileError
/* 406:    */   {
/* 407:522 */     Parser p = new Parser(new Lex(target));
/* 408:523 */     final ASTree texpr = p.parseExpression(this.stable);
/* 409:524 */     final String cname = classname;
/* 410:525 */     final String method = methodname;
/* 411:526 */     final String desc = descriptor;
/* 412:    */     
/* 413:528 */     ProceedHandler h = new ProceedHandler()
/* 414:    */     {
/* 415:    */       private final ASTree val$texpr;
/* 416:    */       private final String val$cname;
/* 417:    */       private final String val$method;
/* 418:    */       private final String val$desc;
/* 419:    */       
/* 420:    */       public void doit(JvstCodeGen gen, Bytecode b, ASTList args)
/* 421:    */         throws CompileError
/* 422:    */       {
/* 423:532 */         gen.compileInvokeSpecial(texpr, cname, method, desc, args);
/* 424:    */       }
/* 425:    */       
/* 426:    */       public void setReturnType(JvstTypeChecker c, ASTList args)
/* 427:    */         throws CompileError
/* 428:    */       {
/* 429:538 */         c.compileInvokeSpecial(texpr, cname, method, desc, args);
/* 430:    */       }
/* 431:542 */     };
/* 432:543 */     this.gen.setProceedHandler(h, "$proceed");
/* 433:    */   }
/* 434:    */   
/* 435:    */   public void recordProceed(ProceedHandler h)
/* 436:    */   {
/* 437:550 */     this.gen.setProceedHandler(h, "$proceed");
/* 438:    */   }
/* 439:    */   
/* 440:    */   public void compileStmnt(String src)
/* 441:    */     throws CompileError
/* 442:    */   {
/* 443:563 */     Parser p = new Parser(new Lex(src));
/* 444:564 */     SymbolTable stb = new SymbolTable(this.stable);
/* 445:565 */     while (p.hasMore())
/* 446:    */     {
/* 447:566 */       Stmnt s = p.parseStatement(stb);
/* 448:567 */       if (s != null) {
/* 449:568 */         s.accept(this.gen);
/* 450:    */       }
/* 451:    */     }
/* 452:    */   }
/* 453:    */   
/* 454:    */   public void compileExpr(String src)
/* 455:    */     throws CompileError
/* 456:    */   {
/* 457:582 */     ASTree e = parseExpr(src, this.stable);
/* 458:583 */     compileExpr(e);
/* 459:    */   }
/* 460:    */   
/* 461:    */   public static ASTree parseExpr(String src, SymbolTable st)
/* 462:    */     throws CompileError
/* 463:    */   {
/* 464:592 */     Parser p = new Parser(new Lex(src));
/* 465:593 */     return p.parseExpression(st);
/* 466:    */   }
/* 467:    */   
/* 468:    */   public void compileExpr(ASTree e)
/* 469:    */     throws CompileError
/* 470:    */   {
/* 471:606 */     if (e != null) {
/* 472:607 */       this.gen.compileExpr(e);
/* 473:    */     }
/* 474:    */   }
/* 475:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.compiler.Javac
 * JD-Core Version:    0.7.0.1
 */