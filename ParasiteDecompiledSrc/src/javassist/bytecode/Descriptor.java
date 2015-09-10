/*   1:    */ package javassist.bytecode;
/*   2:    */ 
/*   3:    */ import java.util.Map;
/*   4:    */ import javassist.ClassPool;
/*   5:    */ import javassist.CtClass;
/*   6:    */ import javassist.CtPrimitiveType;
/*   7:    */ import javassist.NotFoundException;
/*   8:    */ 
/*   9:    */ public class Descriptor
/*  10:    */ {
/*  11:    */   public static String toJvmName(String classname)
/*  12:    */   {
/*  13: 38 */     return classname.replace('.', '/');
/*  14:    */   }
/*  15:    */   
/*  16:    */   public static String toJavaName(String classname)
/*  17:    */   {
/*  18: 51 */     return classname.replace('/', '.');
/*  19:    */   }
/*  20:    */   
/*  21:    */   public static String toJvmName(CtClass clazz)
/*  22:    */   {
/*  23: 59 */     if (clazz.isArray()) {
/*  24: 60 */       return of(clazz);
/*  25:    */     }
/*  26: 62 */     return toJvmName(clazz.getName());
/*  27:    */   }
/*  28:    */   
/*  29:    */   public static String toClassName(String descriptor)
/*  30:    */   {
/*  31: 71 */     int arrayDim = 0;
/*  32: 72 */     int i = 0;
/*  33: 73 */     char c = descriptor.charAt(0);
/*  34: 74 */     while (c == '[')
/*  35:    */     {
/*  36: 75 */       arrayDim++;
/*  37: 76 */       c = descriptor.charAt(++i);
/*  38:    */     }
/*  39: 80 */     if (c == 'L')
/*  40:    */     {
/*  41: 81 */       int i2 = descriptor.indexOf(';', i++);
/*  42: 82 */       String name = descriptor.substring(i, i2).replace('/', '.');
/*  43: 83 */       i = i2;
/*  44:    */     }
/*  45:    */     else
/*  46:    */     {
/*  47:    */       String name;
/*  48: 85 */       if (c == 'V')
/*  49:    */       {
/*  50: 86 */         name = "void";
/*  51:    */       }
/*  52:    */       else
/*  53:    */       {
/*  54:    */         String name;
/*  55: 87 */         if (c == 'I')
/*  56:    */         {
/*  57: 88 */           name = "int";
/*  58:    */         }
/*  59:    */         else
/*  60:    */         {
/*  61:    */           String name;
/*  62: 89 */           if (c == 'B')
/*  63:    */           {
/*  64: 90 */             name = "byte";
/*  65:    */           }
/*  66:    */           else
/*  67:    */           {
/*  68:    */             String name;
/*  69: 91 */             if (c == 'J')
/*  70:    */             {
/*  71: 92 */               name = "long";
/*  72:    */             }
/*  73:    */             else
/*  74:    */             {
/*  75:    */               String name;
/*  76: 93 */               if (c == 'D')
/*  77:    */               {
/*  78: 94 */                 name = "double";
/*  79:    */               }
/*  80:    */               else
/*  81:    */               {
/*  82:    */                 String name;
/*  83: 95 */                 if (c == 'F')
/*  84:    */                 {
/*  85: 96 */                   name = "float";
/*  86:    */                 }
/*  87:    */                 else
/*  88:    */                 {
/*  89:    */                   String name;
/*  90: 97 */                   if (c == 'C')
/*  91:    */                   {
/*  92: 98 */                     name = "char";
/*  93:    */                   }
/*  94:    */                   else
/*  95:    */                   {
/*  96:    */                     String name;
/*  97: 99 */                     if (c == 'S')
/*  98:    */                     {
/*  99:100 */                       name = "short";
/* 100:    */                     }
/* 101:    */                     else
/* 102:    */                     {
/* 103:    */                       String name;
/* 104:101 */                       if (c == 'Z') {
/* 105:102 */                         name = "boolean";
/* 106:    */                       } else {
/* 107:104 */                         throw new RuntimeException("bad descriptor: " + descriptor);
/* 108:    */                       }
/* 109:    */                     }
/* 110:    */                   }
/* 111:    */                 }
/* 112:    */               }
/* 113:    */             }
/* 114:    */           }
/* 115:    */         }
/* 116:    */       }
/* 117:    */     }
/* 118:    */     String name;
/* 119:106 */     if (i + 1 != descriptor.length()) {
/* 120:107 */       throw new RuntimeException("multiple descriptors?: " + descriptor);
/* 121:    */     }
/* 122:109 */     if (arrayDim == 0) {
/* 123:110 */       return name;
/* 124:    */     }
/* 125:112 */     StringBuffer sbuf = new StringBuffer(name);
/* 126:    */     do
/* 127:    */     {
/* 128:114 */       sbuf.append("[]");
/* 129:115 */       arrayDim--;
/* 130:115 */     } while (arrayDim > 0);
/* 131:117 */     return sbuf.toString();
/* 132:    */   }
/* 133:    */   
/* 134:    */   public static String of(String classname)
/* 135:    */   {
/* 136:125 */     if (classname.equals("void")) {
/* 137:126 */       return "V";
/* 138:    */     }
/* 139:127 */     if (classname.equals("int")) {
/* 140:128 */       return "I";
/* 141:    */     }
/* 142:129 */     if (classname.equals("byte")) {
/* 143:130 */       return "B";
/* 144:    */     }
/* 145:131 */     if (classname.equals("long")) {
/* 146:132 */       return "J";
/* 147:    */     }
/* 148:133 */     if (classname.equals("double")) {
/* 149:134 */       return "D";
/* 150:    */     }
/* 151:135 */     if (classname.equals("float")) {
/* 152:136 */       return "F";
/* 153:    */     }
/* 154:137 */     if (classname.equals("char")) {
/* 155:138 */       return "C";
/* 156:    */     }
/* 157:139 */     if (classname.equals("short")) {
/* 158:140 */       return "S";
/* 159:    */     }
/* 160:141 */     if (classname.equals("boolean")) {
/* 161:142 */       return "Z";
/* 162:    */     }
/* 163:144 */     return "L" + toJvmName(classname) + ";";
/* 164:    */   }
/* 165:    */   
/* 166:    */   public static String rename(String desc, String oldname, String newname)
/* 167:    */   {
/* 168:158 */     if (desc.indexOf(oldname) < 0) {
/* 169:159 */       return desc;
/* 170:    */     }
/* 171:161 */     StringBuffer newdesc = new StringBuffer();
/* 172:162 */     int head = 0;
/* 173:163 */     int i = 0;
/* 174:    */     for (;;)
/* 175:    */     {
/* 176:165 */       int j = desc.indexOf('L', i);
/* 177:166 */       if (j < 0) {
/* 178:    */         break;
/* 179:    */       }
/* 180:168 */       if ((desc.startsWith(oldname, j + 1)) && (desc.charAt(j + oldname.length() + 1) == ';'))
/* 181:    */       {
/* 182:170 */         newdesc.append(desc.substring(head, j));
/* 183:171 */         newdesc.append('L');
/* 184:172 */         newdesc.append(newname);
/* 185:173 */         newdesc.append(';');
/* 186:174 */         head = i = j + oldname.length() + 2;
/* 187:    */       }
/* 188:    */       else
/* 189:    */       {
/* 190:177 */         i = desc.indexOf(';', j) + 1;
/* 191:178 */         if (i < 1) {
/* 192:    */           break;
/* 193:    */         }
/* 194:    */       }
/* 195:    */     }
/* 196:183 */     if (head == 0) {
/* 197:184 */       return desc;
/* 198:    */     }
/* 199:186 */     int len = desc.length();
/* 200:187 */     if (head < len) {
/* 201:188 */       newdesc.append(desc.substring(head, len));
/* 202:    */     }
/* 203:190 */     return newdesc.toString();
/* 204:    */   }
/* 205:    */   
/* 206:    */   public static String rename(String desc, Map map)
/* 207:    */   {
/* 208:203 */     if (map == null) {
/* 209:204 */       return desc;
/* 210:    */     }
/* 211:206 */     StringBuffer newdesc = new StringBuffer();
/* 212:207 */     int head = 0;
/* 213:208 */     int i = 0;
/* 214:    */     for (;;)
/* 215:    */     {
/* 216:210 */       int j = desc.indexOf('L', i);
/* 217:211 */       if (j < 0) {
/* 218:    */         break;
/* 219:    */       }
/* 220:214 */       int k = desc.indexOf(';', j);
/* 221:215 */       if (k < 0) {
/* 222:    */         break;
/* 223:    */       }
/* 224:218 */       i = k + 1;
/* 225:219 */       String name = desc.substring(j + 1, k);
/* 226:220 */       String name2 = (String)map.get(name);
/* 227:221 */       if (name2 != null)
/* 228:    */       {
/* 229:222 */         newdesc.append(desc.substring(head, j));
/* 230:223 */         newdesc.append('L');
/* 231:224 */         newdesc.append(name2);
/* 232:225 */         newdesc.append(';');
/* 233:226 */         head = i;
/* 234:    */       }
/* 235:    */     }
/* 236:230 */     if (head == 0) {
/* 237:231 */       return desc;
/* 238:    */     }
/* 239:233 */     int len = desc.length();
/* 240:234 */     if (head < len) {
/* 241:235 */       newdesc.append(desc.substring(head, len));
/* 242:    */     }
/* 243:237 */     return newdesc.toString();
/* 244:    */   }
/* 245:    */   
/* 246:    */   public static String of(CtClass type)
/* 247:    */   {
/* 248:245 */     StringBuffer sbuf = new StringBuffer();
/* 249:246 */     toDescriptor(sbuf, type);
/* 250:247 */     return sbuf.toString();
/* 251:    */   }
/* 252:    */   
/* 253:    */   private static void toDescriptor(StringBuffer desc, CtClass type)
/* 254:    */   {
/* 255:251 */     if (type.isArray())
/* 256:    */     {
/* 257:252 */       desc.append('[');
/* 258:    */       try
/* 259:    */       {
/* 260:254 */         toDescriptor(desc, type.getComponentType());
/* 261:    */       }
/* 262:    */       catch (NotFoundException e)
/* 263:    */       {
/* 264:257 */         desc.append('L');
/* 265:258 */         String name = type.getName();
/* 266:259 */         desc.append(toJvmName(name.substring(0, name.length() - 2)));
/* 267:260 */         desc.append(';');
/* 268:    */       }
/* 269:    */     }
/* 270:263 */     else if (type.isPrimitive())
/* 271:    */     {
/* 272:264 */       CtPrimitiveType pt = (CtPrimitiveType)type;
/* 273:265 */       desc.append(pt.getDescriptor());
/* 274:    */     }
/* 275:    */     else
/* 276:    */     {
/* 277:268 */       desc.append('L');
/* 278:269 */       desc.append(type.getName().replace('.', '/'));
/* 279:270 */       desc.append(';');
/* 280:    */     }
/* 281:    */   }
/* 282:    */   
/* 283:    */   public static String ofConstructor(CtClass[] paramTypes)
/* 284:    */   {
/* 285:281 */     return ofMethod(CtClass.voidType, paramTypes);
/* 286:    */   }
/* 287:    */   
/* 288:    */   public static String ofMethod(CtClass returnType, CtClass[] paramTypes)
/* 289:    */   {
/* 290:292 */     StringBuffer desc = new StringBuffer();
/* 291:293 */     desc.append('(');
/* 292:294 */     if (paramTypes != null)
/* 293:    */     {
/* 294:295 */       int n = paramTypes.length;
/* 295:296 */       for (int i = 0; i < n; i++) {
/* 296:297 */         toDescriptor(desc, paramTypes[i]);
/* 297:    */       }
/* 298:    */     }
/* 299:300 */     desc.append(')');
/* 300:301 */     if (returnType != null) {
/* 301:302 */       toDescriptor(desc, returnType);
/* 302:    */     }
/* 303:304 */     return desc.toString();
/* 304:    */   }
/* 305:    */   
/* 306:    */   public static String ofParameters(CtClass[] paramTypes)
/* 307:    */   {
/* 308:315 */     return ofMethod(null, paramTypes);
/* 309:    */   }
/* 310:    */   
/* 311:    */   public static String appendParameter(String classname, String desc)
/* 312:    */   {
/* 313:328 */     int i = desc.indexOf(')');
/* 314:329 */     if (i < 0) {
/* 315:330 */       return desc;
/* 316:    */     }
/* 317:332 */     StringBuffer newdesc = new StringBuffer();
/* 318:333 */     newdesc.append(desc.substring(0, i));
/* 319:334 */     newdesc.append('L');
/* 320:335 */     newdesc.append(classname.replace('.', '/'));
/* 321:336 */     newdesc.append(';');
/* 322:337 */     newdesc.append(desc.substring(i));
/* 323:338 */     return newdesc.toString();
/* 324:    */   }
/* 325:    */   
/* 326:    */   public static String insertParameter(String classname, String desc)
/* 327:    */   {
/* 328:353 */     if (desc.charAt(0) != '(') {
/* 329:354 */       return desc;
/* 330:    */     }
/* 331:356 */     return "(L" + classname.replace('.', '/') + ';' + desc.substring(1);
/* 332:    */   }
/* 333:    */   
/* 334:    */   public static String appendParameter(CtClass type, String descriptor)
/* 335:    */   {
/* 336:369 */     int i = descriptor.indexOf(')');
/* 337:370 */     if (i < 0) {
/* 338:371 */       return descriptor;
/* 339:    */     }
/* 340:373 */     StringBuffer newdesc = new StringBuffer();
/* 341:374 */     newdesc.append(descriptor.substring(0, i));
/* 342:375 */     toDescriptor(newdesc, type);
/* 343:376 */     newdesc.append(descriptor.substring(i));
/* 344:377 */     return newdesc.toString();
/* 345:    */   }
/* 346:    */   
/* 347:    */   public static String insertParameter(CtClass type, String descriptor)
/* 348:    */   {
/* 349:391 */     if (descriptor.charAt(0) != '(') {
/* 350:392 */       return descriptor;
/* 351:    */     }
/* 352:394 */     return "(" + of(type) + descriptor.substring(1);
/* 353:    */   }
/* 354:    */   
/* 355:    */   public static String changeReturnType(String classname, String desc)
/* 356:    */   {
/* 357:406 */     int i = desc.indexOf(')');
/* 358:407 */     if (i < 0) {
/* 359:408 */       return desc;
/* 360:    */     }
/* 361:410 */     StringBuffer newdesc = new StringBuffer();
/* 362:411 */     newdesc.append(desc.substring(0, i + 1));
/* 363:412 */     newdesc.append('L');
/* 364:413 */     newdesc.append(classname.replace('.', '/'));
/* 365:414 */     newdesc.append(';');
/* 366:415 */     return newdesc.toString();
/* 367:    */   }
/* 368:    */   
/* 369:    */   public static CtClass[] getParameterTypes(String desc, ClassPool cp)
/* 370:    */     throws NotFoundException
/* 371:    */   {
/* 372:430 */     if (desc.charAt(0) != '(') {
/* 373:431 */       return null;
/* 374:    */     }
/* 375:433 */     int num = numOfParameters(desc);
/* 376:434 */     CtClass[] args = new CtClass[num];
/* 377:435 */     int n = 0;
/* 378:436 */     int i = 1;
/* 379:    */     do
/* 380:    */     {
/* 381:438 */       i = toCtClass(cp, desc, i, args, n++);
/* 382:439 */     } while (i > 0);
/* 383:440 */     return args;
/* 384:    */   }
/* 385:    */   
/* 386:    */   public static boolean eqParamTypes(String desc1, String desc2)
/* 387:    */   {
/* 388:450 */     if (desc1.charAt(0) != '(') {
/* 389:451 */       return false;
/* 390:    */     }
/* 391:453 */     for (int i = 0;; i++)
/* 392:    */     {
/* 393:454 */       char c = desc1.charAt(i);
/* 394:455 */       if (c != desc2.charAt(i)) {
/* 395:456 */         return false;
/* 396:    */       }
/* 397:458 */       if (c == ')') {
/* 398:459 */         return true;
/* 399:    */       }
/* 400:    */     }
/* 401:    */   }
/* 402:    */   
/* 403:    */   public static String getParamDescriptor(String decl)
/* 404:    */   {
/* 405:469 */     return decl.substring(0, decl.indexOf(')') + 1);
/* 406:    */   }
/* 407:    */   
/* 408:    */   public static CtClass getReturnType(String desc, ClassPool cp)
/* 409:    */     throws NotFoundException
/* 410:    */   {
/* 411:483 */     int i = desc.indexOf(')');
/* 412:484 */     if (i < 0) {
/* 413:485 */       return null;
/* 414:    */     }
/* 415:487 */     CtClass[] type = new CtClass[1];
/* 416:488 */     toCtClass(cp, desc, i + 1, type, 0);
/* 417:489 */     return type[0];
/* 418:    */   }
/* 419:    */   
/* 420:    */   public static int numOfParameters(String desc)
/* 421:    */   {
/* 422:500 */     int n = 0;
/* 423:501 */     int i = 1;
/* 424:    */     for (;;)
/* 425:    */     {
/* 426:503 */       char c = desc.charAt(i);
/* 427:504 */       if (c == ')') {
/* 428:    */         break;
/* 429:    */       }
/* 430:507 */       while (c == '[') {
/* 431:508 */         c = desc.charAt(++i);
/* 432:    */       }
/* 433:510 */       if (c == 'L')
/* 434:    */       {
/* 435:511 */         i = desc.indexOf(';', i) + 1;
/* 436:512 */         if (i <= 0) {
/* 437:513 */           throw new IndexOutOfBoundsException("bad descriptor");
/* 438:    */         }
/* 439:    */       }
/* 440:    */       else
/* 441:    */       {
/* 442:516 */         i++;
/* 443:    */       }
/* 444:518 */       n++;
/* 445:    */     }
/* 446:521 */     return n;
/* 447:    */   }
/* 448:    */   
/* 449:    */   public static CtClass toCtClass(String desc, ClassPool cp)
/* 450:    */     throws NotFoundException
/* 451:    */   {
/* 452:540 */     CtClass[] clazz = new CtClass[1];
/* 453:541 */     int res = toCtClass(cp, desc, 0, clazz, 0);
/* 454:542 */     if (res >= 0) {
/* 455:543 */       return clazz[0];
/* 456:    */     }
/* 457:547 */     return cp.get(desc.replace('/', '.'));
/* 458:    */   }
/* 459:    */   
/* 460:    */   private static int toCtClass(ClassPool cp, String desc, int i, CtClass[] args, int n)
/* 461:    */     throws NotFoundException
/* 462:    */   {
/* 463:558 */     int arrayDim = 0;
/* 464:559 */     char c = desc.charAt(i);
/* 465:560 */     while (c == '[')
/* 466:    */     {
/* 467:561 */       arrayDim++;
/* 468:562 */       c = desc.charAt(++i);
/* 469:    */     }
/* 470:    */     String name;
/* 471:    */     int i2;
/* 472:    */     String name;
/* 473:565 */     if (c == 'L')
/* 474:    */     {
/* 475:566 */       int i2 = desc.indexOf(';', ++i);
/* 476:567 */       name = desc.substring(i, i2++).replace('/', '.');
/* 477:    */     }
/* 478:    */     else
/* 479:    */     {
/* 480:570 */       CtClass type = toPrimitiveClass(c);
/* 481:571 */       if (type == null) {
/* 482:572 */         return -1;
/* 483:    */       }
/* 484:574 */       i2 = i + 1;
/* 485:575 */       if (arrayDim == 0)
/* 486:    */       {
/* 487:576 */         args[n] = type;
/* 488:577 */         return i2;
/* 489:    */       }
/* 490:580 */       name = type.getName();
/* 491:    */     }
/* 492:583 */     if (arrayDim > 0)
/* 493:    */     {
/* 494:584 */       StringBuffer sbuf = new StringBuffer(name);
/* 495:585 */       while (arrayDim-- > 0) {
/* 496:586 */         sbuf.append("[]");
/* 497:    */       }
/* 498:588 */       name = sbuf.toString();
/* 499:    */     }
/* 500:591 */     args[n] = cp.get(name);
/* 501:592 */     return i2;
/* 502:    */   }
/* 503:    */   
/* 504:    */   static CtClass toPrimitiveClass(char c)
/* 505:    */   {
/* 506:596 */     CtClass type = null;
/* 507:597 */     switch (c)
/* 508:    */     {
/* 509:    */     case 'Z': 
/* 510:599 */       type = CtClass.booleanType;
/* 511:600 */       break;
/* 512:    */     case 'C': 
/* 513:602 */       type = CtClass.charType;
/* 514:603 */       break;
/* 515:    */     case 'B': 
/* 516:605 */       type = CtClass.byteType;
/* 517:606 */       break;
/* 518:    */     case 'S': 
/* 519:608 */       type = CtClass.shortType;
/* 520:609 */       break;
/* 521:    */     case 'I': 
/* 522:611 */       type = CtClass.intType;
/* 523:612 */       break;
/* 524:    */     case 'J': 
/* 525:614 */       type = CtClass.longType;
/* 526:615 */       break;
/* 527:    */     case 'F': 
/* 528:617 */       type = CtClass.floatType;
/* 529:618 */       break;
/* 530:    */     case 'D': 
/* 531:620 */       type = CtClass.doubleType;
/* 532:621 */       break;
/* 533:    */     case 'V': 
/* 534:623 */       type = CtClass.voidType;
/* 535:    */     }
/* 536:627 */     return type;
/* 537:    */   }
/* 538:    */   
/* 539:    */   public static int arrayDimension(String desc)
/* 540:    */   {
/* 541:639 */     int dim = 0;
/* 542:640 */     while (desc.charAt(dim) == '[') {
/* 543:641 */       dim++;
/* 544:    */     }
/* 545:643 */     return dim;
/* 546:    */   }
/* 547:    */   
/* 548:    */   public static String toArrayComponent(String desc, int dim)
/* 549:    */   {
/* 550:656 */     return desc.substring(dim);
/* 551:    */   }
/* 552:    */   
/* 553:    */   public static int dataSize(String desc)
/* 554:    */   {
/* 555:671 */     return dataSize(desc, true);
/* 556:    */   }
/* 557:    */   
/* 558:    */   public static int paramSize(String desc)
/* 559:    */   {
/* 560:684 */     return -dataSize(desc, false);
/* 561:    */   }
/* 562:    */   
/* 563:    */   private static int dataSize(String desc, boolean withRet)
/* 564:    */   {
/* 565:688 */     int n = 0;
/* 566:689 */     char c = desc.charAt(0);
/* 567:690 */     if (c == '(')
/* 568:    */     {
/* 569:691 */       int i = 1;
/* 570:    */       for (;;)
/* 571:    */       {
/* 572:693 */         c = desc.charAt(i);
/* 573:694 */         if (c == ')')
/* 574:    */         {
/* 575:695 */           c = desc.charAt(i + 1);
/* 576:696 */           break;
/* 577:    */         }
/* 578:699 */         boolean array = false;
/* 579:700 */         while (c == '[')
/* 580:    */         {
/* 581:701 */           array = true;
/* 582:702 */           c = desc.charAt(++i);
/* 583:    */         }
/* 584:705 */         if (c == 'L')
/* 585:    */         {
/* 586:706 */           i = desc.indexOf(';', i) + 1;
/* 587:707 */           if (i <= 0) {
/* 588:708 */             throw new IndexOutOfBoundsException("bad descriptor");
/* 589:    */           }
/* 590:    */         }
/* 591:    */         else
/* 592:    */         {
/* 593:711 */           i++;
/* 594:    */         }
/* 595:713 */         if ((!array) && ((c == 'J') || (c == 'D'))) {
/* 596:714 */           n -= 2;
/* 597:    */         } else {
/* 598:716 */           n--;
/* 599:    */         }
/* 600:    */       }
/* 601:    */     }
/* 602:720 */     if (withRet) {
/* 603:721 */       if ((c == 'J') || (c == 'D')) {
/* 604:722 */         n += 2;
/* 605:723 */       } else if (c != 'V') {
/* 606:724 */         n++;
/* 607:    */       }
/* 608:    */     }
/* 609:726 */     return n;
/* 610:    */   }
/* 611:    */   
/* 612:    */   public static String toString(String desc)
/* 613:    */   {
/* 614:737 */     return PrettyPrinter.toString(desc);
/* 615:    */   }
/* 616:    */   
/* 617:    */   static class PrettyPrinter
/* 618:    */   {
/* 619:    */     static String toString(String desc)
/* 620:    */     {
/* 621:742 */       StringBuffer sbuf = new StringBuffer();
/* 622:743 */       if (desc.charAt(0) == '(')
/* 623:    */       {
/* 624:744 */         int pos = 1;
/* 625:745 */         sbuf.append('(');
/* 626:746 */         while (desc.charAt(pos) != ')')
/* 627:    */         {
/* 628:747 */           if (pos > 1) {
/* 629:748 */             sbuf.append(',');
/* 630:    */           }
/* 631:750 */           pos = readType(sbuf, pos, desc);
/* 632:    */         }
/* 633:753 */         sbuf.append(')');
/* 634:    */       }
/* 635:    */       else
/* 636:    */       {
/* 637:756 */         readType(sbuf, 0, desc);
/* 638:    */       }
/* 639:758 */       return sbuf.toString();
/* 640:    */     }
/* 641:    */     
/* 642:    */     static int readType(StringBuffer sbuf, int pos, String desc)
/* 643:    */     {
/* 644:762 */       char c = desc.charAt(pos);
/* 645:763 */       int arrayDim = 0;
/* 646:764 */       while (c == '[')
/* 647:    */       {
/* 648:765 */         arrayDim++;
/* 649:766 */         c = desc.charAt(++pos);
/* 650:    */       }
/* 651:769 */       if (c == 'L') {
/* 652:    */         for (;;)
/* 653:    */         {
/* 654:771 */           c = desc.charAt(++pos);
/* 655:772 */           if (c == ';') {
/* 656:    */             break;
/* 657:    */           }
/* 658:775 */           if (c == '/') {
/* 659:776 */             c = '.';
/* 660:    */           }
/* 661:778 */           sbuf.append(c);
/* 662:    */         }
/* 663:    */       }
/* 664:781 */       CtClass t = Descriptor.toPrimitiveClass(c);
/* 665:782 */       sbuf.append(t.getName());
/* 666:785 */       while (arrayDim-- > 0) {
/* 667:786 */         sbuf.append("[]");
/* 668:    */       }
/* 669:788 */       return pos + 1;
/* 670:    */     }
/* 671:    */   }
/* 672:    */   
/* 673:    */   public static class Iterator
/* 674:    */   {
/* 675:    */     private String desc;
/* 676:    */     private int index;
/* 677:    */     private int curPos;
/* 678:    */     private boolean param;
/* 679:    */     
/* 680:    */     public Iterator(String s)
/* 681:    */     {
/* 682:806 */       this.desc = s;
/* 683:807 */       this.index = (this.curPos = 0);
/* 684:808 */       this.param = false;
/* 685:    */     }
/* 686:    */     
/* 687:    */     public boolean hasNext()
/* 688:    */     {
/* 689:815 */       return this.index < this.desc.length();
/* 690:    */     }
/* 691:    */     
/* 692:    */     public boolean isParameter()
/* 693:    */     {
/* 694:821 */       return this.param;
/* 695:    */     }
/* 696:    */     
/* 697:    */     public char currentChar()
/* 698:    */     {
/* 699:826 */       return this.desc.charAt(this.curPos);
/* 700:    */     }
/* 701:    */     
/* 702:    */     public boolean is2byte()
/* 703:    */     {
/* 704:832 */       char c = currentChar();
/* 705:833 */       return (c == 'D') || (c == 'J');
/* 706:    */     }
/* 707:    */     
/* 708:    */     public int next()
/* 709:    */     {
/* 710:841 */       int nextPos = this.index;
/* 711:842 */       char c = this.desc.charAt(nextPos);
/* 712:843 */       if (c == '(')
/* 713:    */       {
/* 714:844 */         this.index += 1;
/* 715:845 */         c = this.desc.charAt(++nextPos);
/* 716:846 */         this.param = true;
/* 717:    */       }
/* 718:849 */       if (c == ')')
/* 719:    */       {
/* 720:850 */         this.index += 1;
/* 721:851 */         c = this.desc.charAt(++nextPos);
/* 722:852 */         this.param = false;
/* 723:    */       }
/* 724:855 */       while (c == '[') {
/* 725:856 */         c = this.desc.charAt(++nextPos);
/* 726:    */       }
/* 727:858 */       if (c == 'L')
/* 728:    */       {
/* 729:859 */         nextPos = this.desc.indexOf(';', nextPos) + 1;
/* 730:860 */         if (nextPos <= 0) {
/* 731:861 */           throw new IndexOutOfBoundsException("bad descriptor");
/* 732:    */         }
/* 733:    */       }
/* 734:    */       else
/* 735:    */       {
/* 736:864 */         nextPos++;
/* 737:    */       }
/* 738:866 */       this.curPos = this.index;
/* 739:867 */       this.index = nextPos;
/* 740:868 */       return this.curPos;
/* 741:    */     }
/* 742:    */   }
/* 743:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.Descriptor
 * JD-Core Version:    0.7.0.1
 */