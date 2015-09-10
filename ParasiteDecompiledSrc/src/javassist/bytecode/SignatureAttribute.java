/*   1:    */ package javassist.bytecode;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.Map;
/*   7:    */ import javassist.CtClass;
/*   8:    */ 
/*   9:    */ public class SignatureAttribute
/*  10:    */   extends AttributeInfo
/*  11:    */ {
/*  12:    */   public static final String tag = "Signature";
/*  13:    */   
/*  14:    */   SignatureAttribute(ConstPool cp, int n, DataInputStream in)
/*  15:    */     throws IOException
/*  16:    */   {
/*  17: 36 */     super(cp, n, in);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public SignatureAttribute(ConstPool cp, String signature)
/*  21:    */   {
/*  22: 46 */     super(cp, "Signature");
/*  23: 47 */     int index = cp.addUtf8Info(signature);
/*  24: 48 */     byte[] bvalue = new byte[2];
/*  25: 49 */     bvalue[0] = ((byte)(index >>> 8));
/*  26: 50 */     bvalue[1] = ((byte)index);
/*  27: 51 */     set(bvalue);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public String getSignature()
/*  31:    */   {
/*  32: 61 */     return getConstPool().getUtf8Info(ByteArray.readU16bit(get(), 0));
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void setSignature(String sig)
/*  36:    */   {
/*  37: 72 */     int index = getConstPool().addUtf8Info(sig);
/*  38: 73 */     ByteArray.write16bit(index, this.info, 0);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public AttributeInfo copy(ConstPool newCp, Map classnames)
/*  42:    */   {
/*  43: 85 */     return new SignatureAttribute(newCp, getSignature());
/*  44:    */   }
/*  45:    */   
/*  46:    */   void renameClass(String oldname, String newname)
/*  47:    */   {
/*  48: 89 */     String sig = renameClass(getSignature(), oldname, newname);
/*  49: 90 */     setSignature(sig);
/*  50:    */   }
/*  51:    */   
/*  52:    */   void renameClass(Map classnames)
/*  53:    */   {
/*  54: 94 */     String sig = renameClass(getSignature(), classnames);
/*  55: 95 */     setSignature(sig);
/*  56:    */   }
/*  57:    */   
/*  58:    */   static String renameClass(String desc, String oldname, String newname)
/*  59:    */   {
/*  60: 99 */     if (desc.indexOf(oldname) < 0) {
/*  61:100 */       return desc;
/*  62:    */     }
/*  63:102 */     StringBuffer newdesc = new StringBuffer();
/*  64:103 */     int head = 0;
/*  65:104 */     int i = 0;
/*  66:    */     for (;;)
/*  67:    */     {
/*  68:106 */       int j = desc.indexOf('L', i);
/*  69:107 */       if (j < 0) {
/*  70:    */         break;
/*  71:    */       }
/*  72:110 */       int k = j;
/*  73:111 */       int p = 0;
/*  74:    */       
/*  75:113 */       boolean match = true;
/*  76:    */       char c;
/*  77:    */       try
/*  78:    */       {
/*  79:115 */         int len = oldname.length();
/*  80:116 */         while (isNamePart(c = desc.charAt(++k))) {
/*  81:117 */           if ((p >= len) || (c != oldname.charAt(p++))) {
/*  82:118 */             match = false;
/*  83:    */           }
/*  84:    */         }
/*  85:    */       }
/*  86:    */       catch (IndexOutOfBoundsException e)
/*  87:    */       {
/*  88:    */         break;
/*  89:    */       }
/*  90:121 */       i = k + 1;
/*  91:122 */       if ((match) && (p == oldname.length()))
/*  92:    */       {
/*  93:123 */         newdesc.append(desc.substring(head, j));
/*  94:124 */         newdesc.append('L');
/*  95:125 */         newdesc.append(newname);
/*  96:126 */         newdesc.append(c);
/*  97:127 */         head = i;
/*  98:    */       }
/*  99:    */     }
/* 100:131 */     if (head == 0) {
/* 101:132 */       return desc;
/* 102:    */     }
/* 103:134 */     int len = desc.length();
/* 104:135 */     if (head < len) {
/* 105:136 */       newdesc.append(desc.substring(head, len));
/* 106:    */     }
/* 107:138 */     return newdesc.toString();
/* 108:    */   }
/* 109:    */   
/* 110:    */   static String renameClass(String desc, Map map)
/* 111:    */   {
/* 112:143 */     if (map == null) {
/* 113:144 */       return desc;
/* 114:    */     }
/* 115:146 */     StringBuffer newdesc = new StringBuffer();
/* 116:147 */     int head = 0;
/* 117:148 */     int i = 0;
/* 118:    */     for (;;)
/* 119:    */     {
/* 120:150 */       int j = desc.indexOf('L', i);
/* 121:151 */       if (j < 0) {
/* 122:    */         break;
/* 123:    */       }
/* 124:154 */       StringBuffer nameBuf = new StringBuffer();
/* 125:155 */       int k = j;
/* 126:    */       char c;
/* 127:    */       try
/* 128:    */       {
/* 129:158 */         while (isNamePart(c = desc.charAt(++k))) {
/* 130:159 */           nameBuf.append(c);
/* 131:    */         }
/* 132:    */       }
/* 133:    */       catch (IndexOutOfBoundsException e)
/* 134:    */       {
/* 135:    */         break;
/* 136:    */       }
/* 137:162 */       i = k + 1;
/* 138:163 */       String name = nameBuf.toString();
/* 139:164 */       String name2 = (String)map.get(name);
/* 140:165 */       if (name2 != null)
/* 141:    */       {
/* 142:166 */         newdesc.append(desc.substring(head, j));
/* 143:167 */         newdesc.append('L');
/* 144:168 */         newdesc.append(name2);
/* 145:169 */         newdesc.append(c);
/* 146:170 */         head = i;
/* 147:    */       }
/* 148:    */     }
/* 149:174 */     if (head == 0) {
/* 150:175 */       return desc;
/* 151:    */     }
/* 152:177 */     int len = desc.length();
/* 153:178 */     if (head < len) {
/* 154:179 */       newdesc.append(desc.substring(head, len));
/* 155:    */     }
/* 156:181 */     return newdesc.toString();
/* 157:    */   }
/* 158:    */   
/* 159:    */   private static boolean isNamePart(int c)
/* 160:    */   {
/* 161:186 */     return (c != 59) && (c != 60);
/* 162:    */   }
/* 163:    */   
/* 164:    */   private static class Cursor
/* 165:    */   {
/* 166:    */     Cursor(SignatureAttribute.1 x0)
/* 167:    */     {
/* 168:189 */       this();
/* 169:    */     }
/* 170:    */     
/* 171:190 */     int position = 0;
/* 172:    */     
/* 173:    */     int indexOf(String s, int ch)
/* 174:    */       throws BadBytecode
/* 175:    */     {
/* 176:193 */       int i = s.indexOf(ch, this.position);
/* 177:194 */       if (i < 0) {
/* 178:195 */         throw SignatureAttribute.error(s);
/* 179:    */       }
/* 180:197 */       this.position = (i + 1);
/* 181:198 */       return i;
/* 182:    */     }
/* 183:    */     
/* 184:    */     private Cursor() {}
/* 185:    */   }
/* 186:    */   
/* 187:    */   public static class ClassSignature
/* 188:    */   {
/* 189:    */     SignatureAttribute.TypeParameter[] params;
/* 190:    */     SignatureAttribute.ClassType superClass;
/* 191:    */     SignatureAttribute.ClassType[] interfaces;
/* 192:    */     
/* 193:    */     ClassSignature(SignatureAttribute.TypeParameter[] p, SignatureAttribute.ClassType s, SignatureAttribute.ClassType[] i)
/* 194:    */     {
/* 195:211 */       this.params = p;
/* 196:212 */       this.superClass = s;
/* 197:213 */       this.interfaces = i;
/* 198:    */     }
/* 199:    */     
/* 200:    */     public SignatureAttribute.TypeParameter[] getParameters()
/* 201:    */     {
/* 202:222 */       return this.params;
/* 203:    */     }
/* 204:    */     
/* 205:    */     public SignatureAttribute.ClassType getSuperClass()
/* 206:    */     {
/* 207:228 */       return this.superClass;
/* 208:    */     }
/* 209:    */     
/* 210:    */     public SignatureAttribute.ClassType[] getInterfaces()
/* 211:    */     {
/* 212:235 */       return this.interfaces;
/* 213:    */     }
/* 214:    */     
/* 215:    */     public String toString()
/* 216:    */     {
/* 217:241 */       StringBuffer sbuf = new StringBuffer();
/* 218:    */       
/* 219:243 */       SignatureAttribute.TypeParameter.toString(sbuf, this.params);
/* 220:244 */       sbuf.append(" extends ").append(this.superClass);
/* 221:245 */       if (this.interfaces.length > 0)
/* 222:    */       {
/* 223:246 */         sbuf.append(" implements ");
/* 224:247 */         SignatureAttribute.Type.toString(sbuf, this.interfaces);
/* 225:    */       }
/* 226:250 */       return sbuf.toString();
/* 227:    */     }
/* 228:    */   }
/* 229:    */   
/* 230:    */   public static class MethodSignature
/* 231:    */   {
/* 232:    */     SignatureAttribute.TypeParameter[] typeParams;
/* 233:    */     SignatureAttribute.Type[] params;
/* 234:    */     SignatureAttribute.Type retType;
/* 235:    */     SignatureAttribute.ObjectType[] exceptions;
/* 236:    */     
/* 237:    */     MethodSignature(SignatureAttribute.TypeParameter[] tp, SignatureAttribute.Type[] p, SignatureAttribute.Type ret, SignatureAttribute.ObjectType[] ex)
/* 238:    */     {
/* 239:264 */       this.typeParams = tp;
/* 240:265 */       this.params = p;
/* 241:266 */       this.retType = ret;
/* 242:267 */       this.exceptions = ex;
/* 243:    */     }
/* 244:    */     
/* 245:    */     public SignatureAttribute.TypeParameter[] getTypeParameters()
/* 246:    */     {
/* 247:275 */       return this.typeParams;
/* 248:    */     }
/* 249:    */     
/* 250:    */     public SignatureAttribute.Type[] getParameterTypes()
/* 251:    */     {
/* 252:282 */       return this.params;
/* 253:    */     }
/* 254:    */     
/* 255:    */     public SignatureAttribute.Type getReturnType()
/* 256:    */     {
/* 257:287 */       return this.retType;
/* 258:    */     }
/* 259:    */     
/* 260:    */     public SignatureAttribute.ObjectType[] getExceptionTypes()
/* 261:    */     {
/* 262:295 */       return this.exceptions;
/* 263:    */     }
/* 264:    */     
/* 265:    */     public String toString()
/* 266:    */     {
/* 267:301 */       StringBuffer sbuf = new StringBuffer();
/* 268:    */       
/* 269:303 */       SignatureAttribute.TypeParameter.toString(sbuf, this.typeParams);
/* 270:304 */       sbuf.append(" (");
/* 271:305 */       SignatureAttribute.Type.toString(sbuf, this.params);
/* 272:306 */       sbuf.append(") ");
/* 273:307 */       sbuf.append(this.retType);
/* 274:308 */       if (this.exceptions.length > 0)
/* 275:    */       {
/* 276:309 */         sbuf.append(" throws ");
/* 277:310 */         SignatureAttribute.Type.toString(sbuf, this.exceptions);
/* 278:    */       }
/* 279:313 */       return sbuf.toString();
/* 280:    */     }
/* 281:    */   }
/* 282:    */   
/* 283:    */   public static class TypeParameter
/* 284:    */   {
/* 285:    */     String name;
/* 286:    */     SignatureAttribute.ObjectType superClass;
/* 287:    */     SignatureAttribute.ObjectType[] superInterfaces;
/* 288:    */     
/* 289:    */     TypeParameter(String sig, int nb, int ne, SignatureAttribute.ObjectType sc, SignatureAttribute.ObjectType[] si)
/* 290:    */     {
/* 291:326 */       this.name = sig.substring(nb, ne);
/* 292:327 */       this.superClass = sc;
/* 293:328 */       this.superInterfaces = si;
/* 294:    */     }
/* 295:    */     
/* 296:    */     public String getName()
/* 297:    */     {
/* 298:335 */       return this.name;
/* 299:    */     }
/* 300:    */     
/* 301:    */     public SignatureAttribute.ObjectType getClassBound()
/* 302:    */     {
/* 303:343 */       return this.superClass;
/* 304:    */     }
/* 305:    */     
/* 306:    */     public SignatureAttribute.ObjectType[] getInterfaceBound()
/* 307:    */     {
/* 308:350 */       return this.superInterfaces;
/* 309:    */     }
/* 310:    */     
/* 311:    */     public String toString()
/* 312:    */     {
/* 313:356 */       StringBuffer sbuf = new StringBuffer(getName());
/* 314:357 */       if (this.superClass != null) {
/* 315:358 */         sbuf.append(" extends ").append(this.superClass.toString());
/* 316:    */       }
/* 317:360 */       int len = this.superInterfaces.length;
/* 318:361 */       if (len > 0) {
/* 319:362 */         for (int i = 0; i < len; i++)
/* 320:    */         {
/* 321:363 */           if ((i > 0) || (this.superClass != null)) {
/* 322:364 */             sbuf.append(" & ");
/* 323:    */           } else {
/* 324:366 */             sbuf.append(" extends ");
/* 325:    */           }
/* 326:368 */           sbuf.append(this.superInterfaces[i].toString());
/* 327:    */         }
/* 328:    */       }
/* 329:372 */       return sbuf.toString();
/* 330:    */     }
/* 331:    */     
/* 332:    */     static void toString(StringBuffer sbuf, TypeParameter[] tp)
/* 333:    */     {
/* 334:376 */       sbuf.append('<');
/* 335:377 */       for (int i = 0; i < tp.length; i++)
/* 336:    */       {
/* 337:378 */         if (i > 0) {
/* 338:379 */           sbuf.append(", ");
/* 339:    */         }
/* 340:381 */         sbuf.append(tp[i]);
/* 341:    */       }
/* 342:384 */       sbuf.append('>');
/* 343:    */     }
/* 344:    */   }
/* 345:    */   
/* 346:    */   public static class TypeArgument
/* 347:    */   {
/* 348:    */     SignatureAttribute.ObjectType arg;
/* 349:    */     char wildcard;
/* 350:    */     
/* 351:    */     TypeArgument(SignatureAttribute.ObjectType a, char w)
/* 352:    */     {
/* 353:396 */       this.arg = a;
/* 354:397 */       this.wildcard = w;
/* 355:    */     }
/* 356:    */     
/* 357:    */     public char getKind()
/* 358:    */     {
/* 359:406 */       return this.wildcard;
/* 360:    */     }
/* 361:    */     
/* 362:    */     public boolean isWildcard()
/* 363:    */     {
/* 364:412 */       return this.wildcard != ' ';
/* 365:    */     }
/* 366:    */     
/* 367:    */     public SignatureAttribute.ObjectType getType()
/* 368:    */     {
/* 369:421 */       return this.arg;
/* 370:    */     }
/* 371:    */     
/* 372:    */     public String toString()
/* 373:    */     {
/* 374:427 */       if (this.wildcard == '*') {
/* 375:428 */         return "?";
/* 376:    */       }
/* 377:430 */       String type = this.arg.toString();
/* 378:431 */       if (this.wildcard == ' ') {
/* 379:432 */         return type;
/* 380:    */       }
/* 381:433 */       if (this.wildcard == '+') {
/* 382:434 */         return "? extends " + type;
/* 383:    */       }
/* 384:436 */       return "? super " + type;
/* 385:    */     }
/* 386:    */   }
/* 387:    */   
/* 388:    */   public static abstract class Type
/* 389:    */   {
/* 390:    */     static void toString(StringBuffer sbuf, Type[] ts)
/* 391:    */     {
/* 392:445 */       for (int i = 0; i < ts.length; i++)
/* 393:    */       {
/* 394:446 */         if (i > 0) {
/* 395:447 */           sbuf.append(", ");
/* 396:    */         }
/* 397:449 */         sbuf.append(ts[i]);
/* 398:    */       }
/* 399:    */     }
/* 400:    */   }
/* 401:    */   
/* 402:    */   public static class BaseType
/* 403:    */     extends SignatureAttribute.Type
/* 404:    */   {
/* 405:    */     char descriptor;
/* 406:    */     
/* 407:    */     BaseType(char c)
/* 408:    */     {
/* 409:459 */       this.descriptor = c;
/* 410:    */     }
/* 411:    */     
/* 412:    */     public char getDescriptor()
/* 413:    */     {
/* 414:466 */       return this.descriptor;
/* 415:    */     }
/* 416:    */     
/* 417:    */     public CtClass getCtlass()
/* 418:    */     {
/* 419:473 */       return Descriptor.toPrimitiveClass(this.descriptor);
/* 420:    */     }
/* 421:    */     
/* 422:    */     public String toString()
/* 423:    */     {
/* 424:480 */       return Descriptor.toClassName(Character.toString(this.descriptor));
/* 425:    */     }
/* 426:    */   }
/* 427:    */   
/* 428:    */   public static abstract class ObjectType
/* 429:    */     extends SignatureAttribute.Type
/* 430:    */   {}
/* 431:    */   
/* 432:    */   public static class ClassType
/* 433:    */     extends SignatureAttribute.ObjectType
/* 434:    */   {
/* 435:    */     String name;
/* 436:    */     SignatureAttribute.TypeArgument[] arguments;
/* 437:    */     
/* 438:    */     static ClassType make(String s, int b, int e, SignatureAttribute.TypeArgument[] targs, ClassType parent)
/* 439:    */     {
/* 440:498 */       if (parent == null) {
/* 441:499 */         return new ClassType(s, b, e, targs);
/* 442:    */       }
/* 443:501 */       return new SignatureAttribute.NestedClassType(s, b, e, targs, parent);
/* 444:    */     }
/* 445:    */     
/* 446:    */     ClassType(String signature, int begin, int end, SignatureAttribute.TypeArgument[] targs)
/* 447:    */     {
/* 448:505 */       this.name = signature.substring(begin, end).replace('/', '.');
/* 449:506 */       this.arguments = targs;
/* 450:    */     }
/* 451:    */     
/* 452:    */     public String getName()
/* 453:    */     {
/* 454:513 */       return this.name;
/* 455:    */     }
/* 456:    */     
/* 457:    */     public SignatureAttribute.TypeArgument[] getTypeArguments()
/* 458:    */     {
/* 459:521 */       return this.arguments;
/* 460:    */     }
/* 461:    */     
/* 462:    */     public ClassType getDeclaringClass()
/* 463:    */     {
/* 464:529 */       return null;
/* 465:    */     }
/* 466:    */     
/* 467:    */     public String toString()
/* 468:    */     {
/* 469:535 */       StringBuffer sbuf = new StringBuffer();
/* 470:536 */       ClassType parent = getDeclaringClass();
/* 471:537 */       if (parent != null) {
/* 472:538 */         sbuf.append(parent.toString()).append('.');
/* 473:    */       }
/* 474:540 */       sbuf.append(this.name);
/* 475:541 */       if (this.arguments != null)
/* 476:    */       {
/* 477:542 */         sbuf.append('<');
/* 478:543 */         int n = this.arguments.length;
/* 479:544 */         for (int i = 0; i < n; i++)
/* 480:    */         {
/* 481:545 */           if (i > 0) {
/* 482:546 */             sbuf.append(", ");
/* 483:    */           }
/* 484:548 */           sbuf.append(this.arguments[i].toString());
/* 485:    */         }
/* 486:551 */         sbuf.append('>');
/* 487:    */       }
/* 488:554 */       return sbuf.toString();
/* 489:    */     }
/* 490:    */   }
/* 491:    */   
/* 492:    */   public static class NestedClassType
/* 493:    */     extends SignatureAttribute.ClassType
/* 494:    */   {
/* 495:    */     SignatureAttribute.ClassType parent;
/* 496:    */     
/* 497:    */     NestedClassType(String s, int b, int e, SignatureAttribute.TypeArgument[] targs, SignatureAttribute.ClassType p)
/* 498:    */     {
/* 499:565 */       super(b, e, targs);
/* 500:566 */       this.parent = p;
/* 501:    */     }
/* 502:    */     
/* 503:    */     public SignatureAttribute.ClassType getDeclaringClass()
/* 504:    */     {
/* 505:573 */       return this.parent;
/* 506:    */     }
/* 507:    */   }
/* 508:    */   
/* 509:    */   public static class ArrayType
/* 510:    */     extends SignatureAttribute.ObjectType
/* 511:    */   {
/* 512:    */     int dim;
/* 513:    */     SignatureAttribute.Type componentType;
/* 514:    */     
/* 515:    */     public ArrayType(int d, SignatureAttribute.Type comp)
/* 516:    */     {
/* 517:584 */       this.dim = d;
/* 518:585 */       this.componentType = comp;
/* 519:    */     }
/* 520:    */     
/* 521:    */     public int getDimension()
/* 522:    */     {
/* 523:591 */       return this.dim;
/* 524:    */     }
/* 525:    */     
/* 526:    */     public SignatureAttribute.Type getComponentType()
/* 527:    */     {
/* 528:597 */       return this.componentType;
/* 529:    */     }
/* 530:    */     
/* 531:    */     public String toString()
/* 532:    */     {
/* 533:604 */       StringBuffer sbuf = new StringBuffer(this.componentType.toString());
/* 534:605 */       for (int i = 0; i < this.dim; i++) {
/* 535:606 */         sbuf.append("[]");
/* 536:    */       }
/* 537:608 */       return sbuf.toString();
/* 538:    */     }
/* 539:    */   }
/* 540:    */   
/* 541:    */   public static class TypeVariable
/* 542:    */     extends SignatureAttribute.ObjectType
/* 543:    */   {
/* 544:    */     String name;
/* 545:    */     
/* 546:    */     TypeVariable(String sig, int begin, int end)
/* 547:    */     {
/* 548:619 */       this.name = sig.substring(begin, end);
/* 549:    */     }
/* 550:    */     
/* 551:    */     public String getName()
/* 552:    */     {
/* 553:626 */       return this.name;
/* 554:    */     }
/* 555:    */     
/* 556:    */     public String toString()
/* 557:    */     {
/* 558:633 */       return this.name;
/* 559:    */     }
/* 560:    */   }
/* 561:    */   
/* 562:    */   public static ClassSignature toClassSignature(String sig)
/* 563:    */     throws BadBytecode
/* 564:    */   {
/* 565:    */     try
/* 566:    */     {
/* 567:646 */       return parseSig(sig);
/* 568:    */     }
/* 569:    */     catch (IndexOutOfBoundsException e)
/* 570:    */     {
/* 571:649 */       throw error(sig);
/* 572:    */     }
/* 573:    */   }
/* 574:    */   
/* 575:    */   public static MethodSignature toMethodSignature(String sig)
/* 576:    */     throws BadBytecode
/* 577:    */   {
/* 578:    */     try
/* 579:    */     {
/* 580:662 */       return parseMethodSig(sig);
/* 581:    */     }
/* 582:    */     catch (IndexOutOfBoundsException e)
/* 583:    */     {
/* 584:665 */       throw error(sig);
/* 585:    */     }
/* 586:    */   }
/* 587:    */   
/* 588:    */   public static ObjectType toFieldSignature(String sig)
/* 589:    */     throws BadBytecode
/* 590:    */   {
/* 591:    */     try
/* 592:    */     {
/* 593:679 */       return parseObjectType(sig, new Cursor(null), false);
/* 594:    */     }
/* 595:    */     catch (IndexOutOfBoundsException e)
/* 596:    */     {
/* 597:682 */       throw error(sig);
/* 598:    */     }
/* 599:    */   }
/* 600:    */   
/* 601:    */   private static ClassSignature parseSig(String sig)
/* 602:    */     throws BadBytecode, IndexOutOfBoundsException
/* 603:    */   {
/* 604:689 */     Cursor cur = new Cursor(null);
/* 605:690 */     TypeParameter[] tp = parseTypeParams(sig, cur);
/* 606:691 */     ClassType superClass = parseClassType(sig, cur);
/* 607:692 */     int sigLen = sig.length();
/* 608:693 */     ArrayList ifArray = new ArrayList();
/* 609:694 */     while ((cur.position < sigLen) && (sig.charAt(cur.position) == 'L')) {
/* 610:695 */       ifArray.add(parseClassType(sig, cur));
/* 611:    */     }
/* 612:697 */     ClassType[] ifs = (ClassType[])ifArray.toArray(new ClassType[ifArray.size()]);
/* 613:    */     
/* 614:699 */     return new ClassSignature(tp, superClass, ifs);
/* 615:    */   }
/* 616:    */   
/* 617:    */   private static MethodSignature parseMethodSig(String sig)
/* 618:    */     throws BadBytecode
/* 619:    */   {
/* 620:705 */     Cursor cur = new Cursor(null);
/* 621:706 */     TypeParameter[] tp = parseTypeParams(sig, cur);
/* 622:707 */     if (sig.charAt(cur.position++) != '(') {
/* 623:708 */       throw error(sig);
/* 624:    */     }
/* 625:710 */     ArrayList params = new ArrayList();
/* 626:711 */     while (sig.charAt(cur.position) != ')')
/* 627:    */     {
/* 628:712 */       Type t = parseType(sig, cur);
/* 629:713 */       params.add(t);
/* 630:    */     }
/* 631:716 */     cur.position += 1;
/* 632:717 */     Type ret = parseType(sig, cur);
/* 633:718 */     int sigLen = sig.length();
/* 634:719 */     ArrayList exceptions = new ArrayList();
/* 635:720 */     while ((cur.position < sigLen) && (sig.charAt(cur.position) == '^'))
/* 636:    */     {
/* 637:721 */       cur.position += 1;
/* 638:722 */       ObjectType t = parseObjectType(sig, cur, false);
/* 639:723 */       if ((t instanceof ArrayType)) {
/* 640:724 */         throw error(sig);
/* 641:    */       }
/* 642:726 */       exceptions.add(t);
/* 643:    */     }
/* 644:729 */     Type[] p = (Type[])params.toArray(new Type[params.size()]);
/* 645:730 */     ObjectType[] ex = (ObjectType[])exceptions.toArray(new ObjectType[exceptions.size()]);
/* 646:731 */     return new MethodSignature(tp, p, ret, ex);
/* 647:    */   }
/* 648:    */   
/* 649:    */   private static TypeParameter[] parseTypeParams(String sig, Cursor cur)
/* 650:    */     throws BadBytecode
/* 651:    */   {
/* 652:737 */     ArrayList typeParam = new ArrayList();
/* 653:738 */     if (sig.charAt(cur.position) == '<')
/* 654:    */     {
/* 655:739 */       cur.position += 1;
/* 656:740 */       while (sig.charAt(cur.position) != '>')
/* 657:    */       {
/* 658:741 */         int nameBegin = cur.position;
/* 659:742 */         int nameEnd = cur.indexOf(sig, 58);
/* 660:743 */         ObjectType classBound = parseObjectType(sig, cur, true);
/* 661:744 */         ArrayList ifBound = new ArrayList();
/* 662:745 */         while (sig.charAt(cur.position) == ':')
/* 663:    */         {
/* 664:746 */           cur.position += 1;
/* 665:747 */           ObjectType t = parseObjectType(sig, cur, false);
/* 666:748 */           ifBound.add(t);
/* 667:    */         }
/* 668:751 */         TypeParameter p = new TypeParameter(sig, nameBegin, nameEnd, classBound, (ObjectType[])ifBound.toArray(new ObjectType[ifBound.size()]));
/* 669:    */         
/* 670:753 */         typeParam.add(p);
/* 671:    */       }
/* 672:756 */       cur.position += 1;
/* 673:    */     }
/* 674:759 */     return (TypeParameter[])typeParam.toArray(new TypeParameter[typeParam.size()]);
/* 675:    */   }
/* 676:    */   
/* 677:    */   private static ObjectType parseObjectType(String sig, Cursor c, boolean dontThrow)
/* 678:    */     throws BadBytecode
/* 679:    */   {
/* 680:766 */     int begin = c.position;
/* 681:767 */     switch (sig.charAt(begin))
/* 682:    */     {
/* 683:    */     case 'L': 
/* 684:769 */       return parseClassType2(sig, c, null);
/* 685:    */     case 'T': 
/* 686:771 */       int i = c.indexOf(sig, 59);
/* 687:772 */       return new TypeVariable(sig, begin + 1, i);
/* 688:    */     case '[': 
/* 689:774 */       return parseArray(sig, c);
/* 690:    */     }
/* 691:776 */     if (dontThrow) {
/* 692:777 */       return null;
/* 693:    */     }
/* 694:779 */     throw error(sig);
/* 695:    */   }
/* 696:    */   
/* 697:    */   private static ClassType parseClassType(String sig, Cursor c)
/* 698:    */     throws BadBytecode
/* 699:    */   {
/* 700:786 */     if (sig.charAt(c.position) == 'L') {
/* 701:787 */       return parseClassType2(sig, c, null);
/* 702:    */     }
/* 703:789 */     throw error(sig);
/* 704:    */   }
/* 705:    */   
/* 706:    */   private static ClassType parseClassType2(String sig, Cursor c, ClassType parent)
/* 707:    */     throws BadBytecode
/* 708:    */   {
/* 709:795 */     int start = ++c.position;
/* 710:    */     char t;
/* 711:    */     do
/* 712:    */     {
/* 713:798 */       t = sig.charAt(c.position++);
/* 714:799 */     } while ((t != '$') && (t != '<') && (t != ';'));
/* 715:800 */     int end = c.position - 1;
/* 716:    */     TypeArgument[] targs;
/* 717:802 */     if (t == '<')
/* 718:    */     {
/* 719:803 */       TypeArgument[] targs = parseTypeArgs(sig, c);
/* 720:804 */       t = sig.charAt(c.position++);
/* 721:    */     }
/* 722:    */     else
/* 723:    */     {
/* 724:807 */       targs = null;
/* 725:    */     }
/* 726:809 */     ClassType thisClass = ClassType.make(sig, start, end, targs, parent);
/* 727:810 */     if (t == '$')
/* 728:    */     {
/* 729:811 */       c.position -= 1;
/* 730:812 */       return parseClassType2(sig, c, thisClass);
/* 731:    */     }
/* 732:815 */     return thisClass;
/* 733:    */   }
/* 734:    */   
/* 735:    */   private static TypeArgument[] parseTypeArgs(String sig, Cursor c)
/* 736:    */     throws BadBytecode
/* 737:    */   {
/* 738:819 */     ArrayList args = new ArrayList();
/* 739:    */     for (;;)
/* 740:    */     {
/* 741:    */       char t;
/* 742:821 */       if ((t = sig.charAt(c.position++)) == '>') {
/* 743:    */         break;
/* 744:    */       }
/* 745:    */       TypeArgument ta;
/* 746:    */       TypeArgument ta;
/* 747:823 */       if (t == '*')
/* 748:    */       {
/* 749:824 */         ta = new TypeArgument(null, '*');
/* 750:    */       }
/* 751:    */       else
/* 752:    */       {
/* 753:826 */         if ((t != '+') && (t != '-'))
/* 754:    */         {
/* 755:827 */           t = ' ';
/* 756:828 */           c.position -= 1;
/* 757:    */         }
/* 758:831 */         ta = new TypeArgument(parseObjectType(sig, c, false), t);
/* 759:    */       }
/* 760:834 */       args.add(ta);
/* 761:    */     }
/* 762:837 */     return (TypeArgument[])args.toArray(new TypeArgument[args.size()]);
/* 763:    */   }
/* 764:    */   
/* 765:    */   private static ObjectType parseArray(String sig, Cursor c)
/* 766:    */     throws BadBytecode
/* 767:    */   {
/* 768:841 */     int dim = 1;
/* 769:    */     for (;;)
/* 770:    */     {
/* 771:842 */       if (sig.charAt(++c.position) != '[') {
/* 772:    */         break;
/* 773:    */       }
/* 774:843 */       dim++;
/* 775:    */     }
/* 776:845 */     return new ArrayType(dim, parseType(sig, c));
/* 777:    */   }
/* 778:    */   
/* 779:    */   private static Type parseType(String sig, Cursor c)
/* 780:    */     throws BadBytecode
/* 781:    */   {
/* 782:849 */     Type t = parseObjectType(sig, c, true);
/* 783:850 */     if (t == null) {
/* 784:851 */       t = new BaseType(sig.charAt(c.position++));
/* 785:    */     }
/* 786:853 */     return t;
/* 787:    */   }
/* 788:    */   
/* 789:    */   private static BadBytecode error(String sig)
/* 790:    */   {
/* 791:857 */     return new BadBytecode("bad signature: " + sig);
/* 792:    */   }
/* 793:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.SignatureAttribute
 * JD-Core Version:    0.7.0.1
 */