/*   1:    */ package javassist.compiler;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import java.util.List;
/*   5:    */ import javassist.ClassPool;
/*   6:    */ import javassist.CtClass;
/*   7:    */ import javassist.CtField;
/*   8:    */ import javassist.Modifier;
/*   9:    */ import javassist.NotFoundException;
/*  10:    */ import javassist.bytecode.ClassFile;
/*  11:    */ import javassist.bytecode.Descriptor;
/*  12:    */ import javassist.bytecode.MethodInfo;
/*  13:    */ import javassist.compiler.ast.ASTList;
/*  14:    */ import javassist.compiler.ast.ASTree;
/*  15:    */ import javassist.compiler.ast.Declarator;
/*  16:    */ import javassist.compiler.ast.Keyword;
/*  17:    */ import javassist.compiler.ast.Symbol;
/*  18:    */ 
/*  19:    */ public class MemberResolver
/*  20:    */   implements TokenId
/*  21:    */ {
/*  22:    */   private ClassPool classPool;
/*  23:    */   private static final int YES = 0;
/*  24:    */   private static final int NO = -1;
/*  25:    */   
/*  26:    */   public MemberResolver(ClassPool cp)
/*  27:    */   {
/*  28: 30 */     this.classPool = cp;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public ClassPool getClassPool()
/*  32:    */   {
/*  33: 33 */     return this.classPool;
/*  34:    */   }
/*  35:    */   
/*  36:    */   private static void fatal()
/*  37:    */     throws CompileError
/*  38:    */   {
/*  39: 36 */     throw new CompileError("fatal");
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void recordPackage(String jvmClassName)
/*  43:    */   {
/*  44: 43 */     String classname = jvmToJavaName(jvmClassName);
/*  45:    */     for (;;)
/*  46:    */     {
/*  47: 45 */       int i = classname.lastIndexOf('.');
/*  48: 46 */       if (i <= 0) {
/*  49:    */         break;
/*  50:    */       }
/*  51: 47 */       classname = classname.substring(0, i);
/*  52: 48 */       this.classPool.recordInvalidClassName(classname);
/*  53:    */     }
/*  54:    */   }
/*  55:    */   
/*  56:    */   public static class Method
/*  57:    */   {
/*  58:    */     public CtClass declaring;
/*  59:    */     public MethodInfo info;
/*  60:    */     public int notmatch;
/*  61:    */     
/*  62:    */     public Method(CtClass c, MethodInfo i, int n)
/*  63:    */     {
/*  64: 61 */       this.declaring = c;
/*  65: 62 */       this.info = i;
/*  66: 63 */       this.notmatch = n;
/*  67:    */     }
/*  68:    */     
/*  69:    */     public boolean isStatic()
/*  70:    */     {
/*  71: 70 */       int acc = this.info.getAccessFlags();
/*  72: 71 */       return (acc & 0x8) != 0;
/*  73:    */     }
/*  74:    */   }
/*  75:    */   
/*  76:    */   public Method lookupMethod(CtClass clazz, CtClass currentClass, MethodInfo current, String methodName, int[] argTypes, int[] argDims, String[] argClassNames)
/*  77:    */     throws CompileError
/*  78:    */   {
/*  79: 81 */     Method maybe = null;
/*  80: 83 */     if ((current != null) && (clazz == currentClass) && 
/*  81: 84 */       (current.getName().equals(methodName)))
/*  82:    */     {
/*  83: 85 */       int res = compareSignature(current.getDescriptor(), argTypes, argDims, argClassNames);
/*  84: 87 */       if (res != -1)
/*  85:    */       {
/*  86: 88 */         Method r = new Method(clazz, current, res);
/*  87: 89 */         if (res == 0) {
/*  88: 90 */           return r;
/*  89:    */         }
/*  90: 92 */         maybe = r;
/*  91:    */       }
/*  92:    */     }
/*  93: 96 */     Method m = lookupMethod(clazz, methodName, argTypes, argDims, argClassNames, maybe != null);
/*  94: 98 */     if (m != null) {
/*  95: 99 */       return m;
/*  96:    */     }
/*  97:101 */     return maybe;
/*  98:    */   }
/*  99:    */   
/* 100:    */   private Method lookupMethod(CtClass clazz, String methodName, int[] argTypes, int[] argDims, String[] argClassNames, boolean onlyExact)
/* 101:    */     throws CompileError
/* 102:    */   {
/* 103:109 */     Method maybe = null;
/* 104:110 */     ClassFile cf = clazz.getClassFile2();
/* 105:113 */     if (cf != null)
/* 106:    */     {
/* 107:114 */       List list = cf.getMethods();
/* 108:115 */       int n = list.size();
/* 109:116 */       for (int i = 0; i < n; i++)
/* 110:    */       {
/* 111:117 */         MethodInfo minfo = (MethodInfo)list.get(i);
/* 112:118 */         if (minfo.getName().equals(methodName))
/* 113:    */         {
/* 114:119 */           int res = compareSignature(minfo.getDescriptor(), argTypes, argDims, argClassNames);
/* 115:121 */           if (res != -1)
/* 116:    */           {
/* 117:122 */             Method r = new Method(clazz, minfo, res);
/* 118:123 */             if (res == 0) {
/* 119:124 */               return r;
/* 120:    */             }
/* 121:125 */             if ((maybe == null) || (maybe.notmatch > res)) {
/* 122:126 */               maybe = r;
/* 123:    */             }
/* 124:    */           }
/* 125:    */         }
/* 126:    */       }
/* 127:    */     }
/* 128:132 */     if (onlyExact) {
/* 129:133 */       maybe = null;
/* 130:    */     } else {
/* 131:135 */       onlyExact = maybe != null;
/* 132:    */     }
/* 133:137 */     int mod = clazz.getModifiers();
/* 134:138 */     boolean isIntf = Modifier.isInterface(mod);
/* 135:    */     try
/* 136:    */     {
/* 137:141 */       if (!isIntf)
/* 138:    */       {
/* 139:142 */         CtClass pclazz = clazz.getSuperclass();
/* 140:143 */         if (pclazz != null)
/* 141:    */         {
/* 142:144 */           Method r = lookupMethod(pclazz, methodName, argTypes, argDims, argClassNames, onlyExact);
/* 143:146 */           if (r != null) {
/* 144:147 */             return r;
/* 145:    */           }
/* 146:    */         }
/* 147:    */       }
/* 148:    */     }
/* 149:    */     catch (NotFoundException e) {}
/* 150:153 */     if ((isIntf) || (Modifier.isAbstract(mod))) {
/* 151:    */       try
/* 152:    */       {
/* 153:155 */         CtClass[] ifs = clazz.getInterfaces();
/* 154:156 */         int size = ifs.length;
/* 155:157 */         for (int i = 0; i < size; i++)
/* 156:    */         {
/* 157:158 */           Method r = lookupMethod(ifs[i], methodName, argTypes, argDims, argClassNames, onlyExact);
/* 158:161 */           if (r != null) {
/* 159:162 */             return r;
/* 160:    */           }
/* 161:    */         }
/* 162:165 */         if (isIntf)
/* 163:    */         {
/* 164:167 */           CtClass pclazz = clazz.getSuperclass();
/* 165:168 */           if (pclazz != null)
/* 166:    */           {
/* 167:169 */             Method r = lookupMethod(pclazz, methodName, argTypes, argDims, argClassNames, onlyExact);
/* 168:171 */             if (r != null) {
/* 169:172 */               return r;
/* 170:    */             }
/* 171:    */           }
/* 172:    */         }
/* 173:    */       }
/* 174:    */       catch (NotFoundException e) {}
/* 175:    */     }
/* 176:178 */     return maybe;
/* 177:    */   }
/* 178:    */   
/* 179:    */   private int compareSignature(String desc, int[] argTypes, int[] argDims, String[] argClassNames)
/* 180:    */     throws CompileError
/* 181:    */   {
/* 182:200 */     int result = 0;
/* 183:201 */     int i = 1;
/* 184:202 */     int nArgs = argTypes.length;
/* 185:203 */     if (nArgs != Descriptor.numOfParameters(desc)) {
/* 186:204 */       return -1;
/* 187:    */     }
/* 188:206 */     int len = desc.length();
/* 189:207 */     for (int n = 0; i < len; n++)
/* 190:    */     {
/* 191:208 */       char c = desc.charAt(i++);
/* 192:209 */       if (c == ')') {
/* 193:210 */         return n == nArgs ? result : -1;
/* 194:    */       }
/* 195:211 */       if (n >= nArgs) {
/* 196:212 */         return -1;
/* 197:    */       }
/* 198:214 */       int dim = 0;
/* 199:215 */       while (c == '[')
/* 200:    */       {
/* 201:216 */         dim++;
/* 202:217 */         c = desc.charAt(i++);
/* 203:    */       }
/* 204:220 */       if (argTypes[n] == 412)
/* 205:    */       {
/* 206:221 */         if ((dim == 0) && (c != 'L')) {
/* 207:222 */           return -1;
/* 208:    */         }
/* 209:224 */         if (c == 'L') {
/* 210:225 */           i = desc.indexOf(';', i) + 1;
/* 211:    */         }
/* 212:    */       }
/* 213:227 */       else if (argDims[n] != dim)
/* 214:    */       {
/* 215:228 */         if ((dim != 0) || (c != 'L') || (!desc.startsWith("java/lang/Object;", i))) {
/* 216:230 */           return -1;
/* 217:    */         }
/* 218:233 */         i = desc.indexOf(';', i) + 1;
/* 219:234 */         result++;
/* 220:235 */         if (i <= 0) {
/* 221:236 */           return -1;
/* 222:    */         }
/* 223:    */       }
/* 224:238 */       else if (c == 'L')
/* 225:    */       {
/* 226:239 */         int j = desc.indexOf(';', i);
/* 227:240 */         if ((j < 0) || (argTypes[n] != 307)) {
/* 228:241 */           return -1;
/* 229:    */         }
/* 230:243 */         String cname = desc.substring(i, j);
/* 231:244 */         if (!cname.equals(argClassNames[n]))
/* 232:    */         {
/* 233:245 */           CtClass clazz = lookupClassByJvmName(argClassNames[n]);
/* 234:    */           try
/* 235:    */           {
/* 236:247 */             if (clazz.subtypeOf(lookupClassByJvmName(cname))) {
/* 237:248 */               result++;
/* 238:    */             } else {
/* 239:250 */               return -1;
/* 240:    */             }
/* 241:    */           }
/* 242:    */           catch (NotFoundException e)
/* 243:    */           {
/* 244:253 */             result++;
/* 245:    */           }
/* 246:    */         }
/* 247:257 */         i = j + 1;
/* 248:    */       }
/* 249:    */       else
/* 250:    */       {
/* 251:260 */         int t = descToType(c);
/* 252:261 */         int at = argTypes[n];
/* 253:262 */         if (t != at) {
/* 254:263 */           if ((t == 324) && ((at == 334) || (at == 303) || (at == 306))) {
/* 255:265 */             result++;
/* 256:    */           } else {
/* 257:267 */             return -1;
/* 258:    */           }
/* 259:    */         }
/* 260:    */       }
/* 261:    */     }
/* 262:271 */     return -1;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public CtField lookupFieldByJvmName2(String jvmClassName, Symbol fieldSym, ASTree expr)
/* 266:    */     throws NoFieldException
/* 267:    */   {
/* 268:282 */     String field = fieldSym.get();
/* 269:283 */     CtClass cc = null;
/* 270:    */     try
/* 271:    */     {
/* 272:285 */       cc = lookupClass(jvmToJavaName(jvmClassName), true);
/* 273:    */     }
/* 274:    */     catch (CompileError e)
/* 275:    */     {
/* 276:289 */       throw new NoFieldException(jvmClassName + "/" + field, expr);
/* 277:    */     }
/* 278:    */     try
/* 279:    */     {
/* 280:293 */       return cc.getField(field);
/* 281:    */     }
/* 282:    */     catch (NotFoundException e)
/* 283:    */     {
/* 284:297 */       jvmClassName = javaToJvmName(cc.getName());
/* 285:298 */       throw new NoFieldException(jvmClassName + "$" + field, expr);
/* 286:    */     }
/* 287:    */   }
/* 288:    */   
/* 289:    */   public CtField lookupFieldByJvmName(String jvmClassName, Symbol fieldName)
/* 290:    */     throws CompileError
/* 291:    */   {
/* 292:308 */     return lookupField(jvmToJavaName(jvmClassName), fieldName);
/* 293:    */   }
/* 294:    */   
/* 295:    */   public CtField lookupField(String className, Symbol fieldName)
/* 296:    */     throws CompileError
/* 297:    */   {
/* 298:317 */     CtClass cc = lookupClass(className, false);
/* 299:    */     try
/* 300:    */     {
/* 301:319 */       return cc.getField(fieldName.get());
/* 302:    */     }
/* 303:    */     catch (NotFoundException e)
/* 304:    */     {
/* 305:322 */       throw new CompileError("no such field: " + fieldName.get());
/* 306:    */     }
/* 307:    */   }
/* 308:    */   
/* 309:    */   public CtClass lookupClassByName(ASTList name)
/* 310:    */     throws CompileError
/* 311:    */   {
/* 312:326 */     return lookupClass(Declarator.astToClassName(name, '.'), false);
/* 313:    */   }
/* 314:    */   
/* 315:    */   public CtClass lookupClassByJvmName(String jvmName)
/* 316:    */     throws CompileError
/* 317:    */   {
/* 318:330 */     return lookupClass(jvmToJavaName(jvmName), false);
/* 319:    */   }
/* 320:    */   
/* 321:    */   public CtClass lookupClass(Declarator decl)
/* 322:    */     throws CompileError
/* 323:    */   {
/* 324:334 */     return lookupClass(decl.getType(), decl.getArrayDim(), decl.getClassName());
/* 325:    */   }
/* 326:    */   
/* 327:    */   public CtClass lookupClass(int type, int dim, String classname)
/* 328:    */     throws CompileError
/* 329:    */   {
/* 330:344 */     String cname = "";
/* 331:346 */     if (type == 307)
/* 332:    */     {
/* 333:347 */       CtClass clazz = lookupClassByJvmName(classname);
/* 334:348 */       if (dim > 0) {
/* 335:349 */         cname = clazz.getName();
/* 336:    */       } else {
/* 337:351 */         return clazz;
/* 338:    */       }
/* 339:    */     }
/* 340:    */     else
/* 341:    */     {
/* 342:354 */       cname = getTypeName(type);
/* 343:    */     }
/* 344:356 */     while (dim-- > 0) {
/* 345:357 */       cname = cname + "[]";
/* 346:    */     }
/* 347:359 */     return lookupClass(cname, false);
/* 348:    */   }
/* 349:    */   
/* 350:    */   static String getTypeName(int type)
/* 351:    */     throws CompileError
/* 352:    */   {
/* 353:366 */     String cname = "";
/* 354:367 */     switch (type)
/* 355:    */     {
/* 356:    */     case 301: 
/* 357:369 */       cname = "boolean";
/* 358:370 */       break;
/* 359:    */     case 306: 
/* 360:372 */       cname = "char";
/* 361:373 */       break;
/* 362:    */     case 303: 
/* 363:375 */       cname = "byte";
/* 364:376 */       break;
/* 365:    */     case 334: 
/* 366:378 */       cname = "short";
/* 367:379 */       break;
/* 368:    */     case 324: 
/* 369:381 */       cname = "int";
/* 370:382 */       break;
/* 371:    */     case 326: 
/* 372:384 */       cname = "long";
/* 373:385 */       break;
/* 374:    */     case 317: 
/* 375:387 */       cname = "float";
/* 376:388 */       break;
/* 377:    */     case 312: 
/* 378:390 */       cname = "double";
/* 379:391 */       break;
/* 380:    */     case 344: 
/* 381:393 */       cname = "void";
/* 382:394 */       break;
/* 383:    */     default: 
/* 384:396 */       fatal();
/* 385:    */     }
/* 386:399 */     return cname;
/* 387:    */   }
/* 388:    */   
/* 389:    */   public CtClass lookupClass(String name, boolean notCheckInner)
/* 390:    */     throws CompileError
/* 391:    */   {
/* 392:    */     try
/* 393:    */     {
/* 394:409 */       return lookupClass0(name, notCheckInner);
/* 395:    */     }
/* 396:    */     catch (NotFoundException e) {}
/* 397:412 */     return searchImports(name);
/* 398:    */   }
/* 399:    */   
/* 400:    */   private CtClass searchImports(String orgName)
/* 401:    */     throws CompileError
/* 402:    */   {
/* 403:419 */     if (orgName.indexOf('.') < 0)
/* 404:    */     {
/* 405:420 */       Iterator it = this.classPool.getImportedPackages();
/* 406:421 */       while (it.hasNext())
/* 407:    */       {
/* 408:422 */         String pac = (String)it.next();
/* 409:423 */         String fqName = pac + '.' + orgName;
/* 410:    */         try
/* 411:    */         {
/* 412:425 */           CtClass cc = this.classPool.get(fqName);
/* 413:    */           
/* 414:427 */           this.classPool.recordInvalidClassName(orgName);
/* 415:428 */           return cc;
/* 416:    */         }
/* 417:    */         catch (NotFoundException e)
/* 418:    */         {
/* 419:431 */           this.classPool.recordInvalidClassName(fqName);
/* 420:    */         }
/* 421:    */       }
/* 422:    */     }
/* 423:436 */     throw new CompileError("no such class: " + orgName);
/* 424:    */   }
/* 425:    */   
/* 426:    */   private CtClass lookupClass0(String classname, boolean notCheckInner)
/* 427:    */     throws NotFoundException
/* 428:    */   {
/* 429:442 */     CtClass cc = null;
/* 430:    */     do
/* 431:    */     {
/* 432:    */       try
/* 433:    */       {
/* 434:445 */         cc = this.classPool.get(classname);
/* 435:    */       }
/* 436:    */       catch (NotFoundException e)
/* 437:    */       {
/* 438:448 */         int i = classname.lastIndexOf('.');
/* 439:449 */         if ((notCheckInner) || (i < 0)) {
/* 440:450 */           throw e;
/* 441:    */         }
/* 442:452 */         StringBuffer sbuf = new StringBuffer(classname);
/* 443:453 */         sbuf.setCharAt(i, '$');
/* 444:454 */         classname = sbuf.toString();
/* 445:    */       }
/* 446:457 */     } while (cc == null);
/* 447:458 */     return cc;
/* 448:    */   }
/* 449:    */   
/* 450:    */   public String resolveClassName(ASTList name)
/* 451:    */     throws CompileError
/* 452:    */   {
/* 453:467 */     if (name == null) {
/* 454:468 */       return null;
/* 455:    */     }
/* 456:470 */     return javaToJvmName(lookupClassByName(name).getName());
/* 457:    */   }
/* 458:    */   
/* 459:    */   public String resolveJvmClassName(String jvmName)
/* 460:    */     throws CompileError
/* 461:    */   {
/* 462:477 */     if (jvmName == null) {
/* 463:478 */       return null;
/* 464:    */     }
/* 465:480 */     return javaToJvmName(lookupClassByJvmName(jvmName).getName());
/* 466:    */   }
/* 467:    */   
/* 468:    */   public static CtClass getSuperclass(CtClass c)
/* 469:    */     throws CompileError
/* 470:    */   {
/* 471:    */     try
/* 472:    */     {
/* 473:485 */       CtClass sc = c.getSuperclass();
/* 474:486 */       if (sc != null) {
/* 475:487 */         return sc;
/* 476:    */       }
/* 477:    */     }
/* 478:    */     catch (NotFoundException e) {}
/* 479:490 */     throw new CompileError("cannot find the super class of " + c.getName());
/* 480:    */   }
/* 481:    */   
/* 482:    */   public static String javaToJvmName(String classname)
/* 483:    */   {
/* 484:495 */     return classname.replace('.', '/');
/* 485:    */   }
/* 486:    */   
/* 487:    */   public static String jvmToJavaName(String classname)
/* 488:    */   {
/* 489:499 */     return classname.replace('/', '.');
/* 490:    */   }
/* 491:    */   
/* 492:    */   public static int descToType(char c)
/* 493:    */     throws CompileError
/* 494:    */   {
/* 495:503 */     switch (c)
/* 496:    */     {
/* 497:    */     case 'Z': 
/* 498:505 */       return 301;
/* 499:    */     case 'C': 
/* 500:507 */       return 306;
/* 501:    */     case 'B': 
/* 502:509 */       return 303;
/* 503:    */     case 'S': 
/* 504:511 */       return 334;
/* 505:    */     case 'I': 
/* 506:513 */       return 324;
/* 507:    */     case 'J': 
/* 508:515 */       return 326;
/* 509:    */     case 'F': 
/* 510:517 */       return 317;
/* 511:    */     case 'D': 
/* 512:519 */       return 312;
/* 513:    */     case 'V': 
/* 514:521 */       return 344;
/* 515:    */     case 'L': 
/* 516:    */     case '[': 
/* 517:524 */       return 307;
/* 518:    */     }
/* 519:526 */     fatal();
/* 520:527 */     return 344;
/* 521:    */   }
/* 522:    */   
/* 523:    */   public static int getModifiers(ASTList mods)
/* 524:    */   {
/* 525:532 */     int m = 0;
/* 526:533 */     while (mods != null)
/* 527:    */     {
/* 528:534 */       Keyword k = (Keyword)mods.head();
/* 529:535 */       mods = mods.tail();
/* 530:536 */       switch (k.get())
/* 531:    */       {
/* 532:    */       case 335: 
/* 533:538 */         m |= 0x8;
/* 534:539 */         break;
/* 535:    */       case 315: 
/* 536:541 */         m |= 0x10;
/* 537:542 */         break;
/* 538:    */       case 338: 
/* 539:544 */         m |= 0x20;
/* 540:545 */         break;
/* 541:    */       case 300: 
/* 542:547 */         m |= 0x400;
/* 543:548 */         break;
/* 544:    */       case 332: 
/* 545:550 */         m |= 0x1;
/* 546:551 */         break;
/* 547:    */       case 331: 
/* 548:553 */         m |= 0x4;
/* 549:554 */         break;
/* 550:    */       case 330: 
/* 551:556 */         m |= 0x2;
/* 552:557 */         break;
/* 553:    */       case 345: 
/* 554:559 */         m |= 0x40;
/* 555:560 */         break;
/* 556:    */       case 342: 
/* 557:562 */         m |= 0x80;
/* 558:563 */         break;
/* 559:    */       case 347: 
/* 560:565 */         m |= 0x800;
/* 561:    */       }
/* 562:    */     }
/* 563:570 */     return m;
/* 564:    */   }
/* 565:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.compiler.MemberResolver
 * JD-Core Version:    0.7.0.1
 */