/*   1:    */ package javassist.bytecode;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.ListIterator;
/*   9:    */ import java.util.Map;
/*  10:    */ import javassist.CannotCompileException;
/*  11:    */ 
/*  12:    */ public final class ClassFile
/*  13:    */ {
/*  14:    */   int major;
/*  15:    */   int minor;
/*  16:    */   ConstPool constPool;
/*  17:    */   int thisClass;
/*  18:    */   int accessFlags;
/*  19:    */   int superClass;
/*  20:    */   int[] interfaces;
/*  21:    */   ArrayList fields;
/*  22:    */   ArrayList methods;
/*  23:    */   ArrayList attributes;
/*  24:    */   String thisclassname;
/*  25:    */   String[] cachedInterfaces;
/*  26:    */   String cachedSuperclass;
/*  27:    */   public static final int JAVA_1 = 45;
/*  28:    */   public static final int JAVA_2 = 46;
/*  29:    */   public static final int JAVA_3 = 47;
/*  30:    */   public static final int JAVA_4 = 48;
/*  31:    */   public static final int JAVA_5 = 49;
/*  32:    */   public static final int JAVA_6 = 50;
/*  33:    */   public static final int JAVA_7 = 51;
/*  34: 94 */   public static int MAJOR_VERSION = 47;
/*  35:    */   
/*  36:    */   static
/*  37:    */   {
/*  38:    */     try
/*  39:    */     {
/*  40: 98 */       Class.forName("java.lang.StringBuilder");
/*  41: 99 */       MAJOR_VERSION = 49;
/*  42:    */     }
/*  43:    */     catch (Throwable t) {}
/*  44:    */   }
/*  45:    */   
/*  46:    */   public ClassFile(DataInputStream in)
/*  47:    */     throws IOException
/*  48:    */   {
/*  49:108 */     read(in);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public ClassFile(boolean isInterface, String classname, String superclass)
/*  53:    */   {
/*  54:122 */     this.major = MAJOR_VERSION;
/*  55:123 */     this.minor = 0;
/*  56:124 */     this.constPool = new ConstPool(classname);
/*  57:125 */     this.thisClass = this.constPool.getThisClassInfo();
/*  58:126 */     if (isInterface) {
/*  59:127 */       this.accessFlags = 1536;
/*  60:    */     } else {
/*  61:129 */       this.accessFlags = 32;
/*  62:    */     }
/*  63:131 */     initSuperclass(superclass);
/*  64:132 */     this.interfaces = null;
/*  65:133 */     this.fields = new ArrayList();
/*  66:134 */     this.methods = new ArrayList();
/*  67:135 */     this.thisclassname = classname;
/*  68:    */     
/*  69:137 */     this.attributes = new ArrayList();
/*  70:138 */     this.attributes.add(new SourceFileAttribute(this.constPool, getSourcefileName(this.thisclassname)));
/*  71:    */   }
/*  72:    */   
/*  73:    */   private void initSuperclass(String superclass)
/*  74:    */   {
/*  75:143 */     if (superclass != null)
/*  76:    */     {
/*  77:144 */       this.superClass = this.constPool.addClassInfo(superclass);
/*  78:145 */       this.cachedSuperclass = superclass;
/*  79:    */     }
/*  80:    */     else
/*  81:    */     {
/*  82:148 */       this.superClass = this.constPool.addClassInfo("java.lang.Object");
/*  83:149 */       this.cachedSuperclass = "java.lang.Object";
/*  84:    */     }
/*  85:    */   }
/*  86:    */   
/*  87:    */   private static String getSourcefileName(String qname)
/*  88:    */   {
/*  89:154 */     int index = qname.lastIndexOf('.');
/*  90:155 */     if (index >= 0) {
/*  91:156 */       qname = qname.substring(index + 1);
/*  92:    */     }
/*  93:158 */     return qname + ".java";
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void compact()
/*  97:    */   {
/*  98:167 */     ConstPool cp = compact0();
/*  99:168 */     ArrayList list = this.methods;
/* 100:169 */     int n = list.size();
/* 101:170 */     for (int i = 0; i < n; i++)
/* 102:    */     {
/* 103:171 */       MethodInfo minfo = (MethodInfo)list.get(i);
/* 104:172 */       minfo.compact(cp);
/* 105:    */     }
/* 106:175 */     list = this.fields;
/* 107:176 */     n = list.size();
/* 108:177 */     for (int i = 0; i < n; i++)
/* 109:    */     {
/* 110:178 */       FieldInfo finfo = (FieldInfo)list.get(i);
/* 111:179 */       finfo.compact(cp);
/* 112:    */     }
/* 113:182 */     this.attributes = AttributeInfo.copyAll(this.attributes, cp);
/* 114:183 */     this.constPool = cp;
/* 115:    */   }
/* 116:    */   
/* 117:    */   private ConstPool compact0()
/* 118:    */   {
/* 119:187 */     ConstPool cp = new ConstPool(this.thisclassname);
/* 120:188 */     this.thisClass = cp.getThisClassInfo();
/* 121:189 */     String sc = getSuperclass();
/* 122:190 */     if (sc != null) {
/* 123:191 */       this.superClass = cp.addClassInfo(getSuperclass());
/* 124:    */     }
/* 125:193 */     if (this.interfaces != null)
/* 126:    */     {
/* 127:194 */       int n = this.interfaces.length;
/* 128:195 */       for (int i = 0; i < n; i++) {
/* 129:196 */         this.interfaces[i] = cp.addClassInfo(this.constPool.getClassInfo(this.interfaces[i]));
/* 130:    */       }
/* 131:    */     }
/* 132:200 */     return cp;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void prune()
/* 136:    */   {
/* 137:210 */     ConstPool cp = compact0();
/* 138:211 */     ArrayList newAttributes = new ArrayList();
/* 139:212 */     AttributeInfo invisibleAnnotations = getAttribute("RuntimeInvisibleAnnotations");
/* 140:214 */     if (invisibleAnnotations != null)
/* 141:    */     {
/* 142:215 */       invisibleAnnotations = invisibleAnnotations.copy(cp, null);
/* 143:216 */       newAttributes.add(invisibleAnnotations);
/* 144:    */     }
/* 145:219 */     AttributeInfo visibleAnnotations = getAttribute("RuntimeVisibleAnnotations");
/* 146:221 */     if (visibleAnnotations != null)
/* 147:    */     {
/* 148:222 */       visibleAnnotations = visibleAnnotations.copy(cp, null);
/* 149:223 */       newAttributes.add(visibleAnnotations);
/* 150:    */     }
/* 151:226 */     AttributeInfo signature = getAttribute("Signature");
/* 152:228 */     if (signature != null)
/* 153:    */     {
/* 154:229 */       signature = signature.copy(cp, null);
/* 155:230 */       newAttributes.add(signature);
/* 156:    */     }
/* 157:233 */     ArrayList list = this.methods;
/* 158:234 */     int n = list.size();
/* 159:235 */     for (int i = 0; i < n; i++)
/* 160:    */     {
/* 161:236 */       MethodInfo minfo = (MethodInfo)list.get(i);
/* 162:237 */       minfo.prune(cp);
/* 163:    */     }
/* 164:240 */     list = this.fields;
/* 165:241 */     n = list.size();
/* 166:242 */     for (int i = 0; i < n; i++)
/* 167:    */     {
/* 168:243 */       FieldInfo finfo = (FieldInfo)list.get(i);
/* 169:244 */       finfo.prune(cp);
/* 170:    */     }
/* 171:247 */     this.attributes = newAttributes;
/* 172:248 */     this.constPool = cp;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public ConstPool getConstPool()
/* 176:    */   {
/* 177:255 */     return this.constPool;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public boolean isInterface()
/* 181:    */   {
/* 182:262 */     return (this.accessFlags & 0x200) != 0;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public boolean isFinal()
/* 186:    */   {
/* 187:269 */     return (this.accessFlags & 0x10) != 0;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public boolean isAbstract()
/* 191:    */   {
/* 192:276 */     return (this.accessFlags & 0x400) != 0;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public int getAccessFlags()
/* 196:    */   {
/* 197:285 */     return this.accessFlags;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public void setAccessFlags(int acc)
/* 201:    */   {
/* 202:294 */     if ((acc & 0x200) == 0) {
/* 203:295 */       acc |= 0x20;
/* 204:    */     }
/* 205:297 */     this.accessFlags = acc;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public int getInnerAccessFlags()
/* 209:    */   {
/* 210:309 */     InnerClassesAttribute ica = (InnerClassesAttribute)getAttribute("InnerClasses");
/* 211:311 */     if (ica == null) {
/* 212:312 */       return -1;
/* 213:    */     }
/* 214:314 */     String name = getName();
/* 215:315 */     int n = ica.tableLength();
/* 216:316 */     for (int i = 0; i < n; i++) {
/* 217:317 */       if (name.equals(ica.innerClass(i))) {
/* 218:318 */         return ica.accessFlags(i);
/* 219:    */       }
/* 220:    */     }
/* 221:320 */     return -1;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public String getName()
/* 225:    */   {
/* 226:327 */     return this.thisclassname;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public void setName(String name)
/* 230:    */   {
/* 231:335 */     renameClass(this.thisclassname, name);
/* 232:    */   }
/* 233:    */   
/* 234:    */   public String getSuperclass()
/* 235:    */   {
/* 236:342 */     if (this.cachedSuperclass == null) {
/* 237:343 */       this.cachedSuperclass = this.constPool.getClassInfo(this.superClass);
/* 238:    */     }
/* 239:345 */     return this.cachedSuperclass;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public int getSuperclassId()
/* 243:    */   {
/* 244:353 */     return this.superClass;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public void setSuperclass(String superclass)
/* 248:    */     throws CannotCompileException
/* 249:    */   {
/* 250:365 */     if (superclass == null) {
/* 251:366 */       superclass = "java.lang.Object";
/* 252:    */     }
/* 253:    */     try
/* 254:    */     {
/* 255:369 */       this.superClass = this.constPool.addClassInfo(superclass);
/* 256:370 */       ArrayList list = this.methods;
/* 257:371 */       int n = list.size();
/* 258:372 */       for (int i = 0; i < n; i++)
/* 259:    */       {
/* 260:373 */         MethodInfo minfo = (MethodInfo)list.get(i);
/* 261:374 */         minfo.setSuperclass(superclass);
/* 262:    */       }
/* 263:    */     }
/* 264:    */     catch (BadBytecode e)
/* 265:    */     {
/* 266:378 */       throw new CannotCompileException(e);
/* 267:    */     }
/* 268:380 */     this.cachedSuperclass = superclass;
/* 269:    */   }
/* 270:    */   
/* 271:    */   public final void renameClass(String oldname, String newname)
/* 272:    */   {
/* 273:401 */     if (oldname.equals(newname)) {
/* 274:402 */       return;
/* 275:    */     }
/* 276:404 */     if (oldname.equals(this.thisclassname)) {
/* 277:405 */       this.thisclassname = newname;
/* 278:    */     }
/* 279:407 */     oldname = Descriptor.toJvmName(oldname);
/* 280:408 */     newname = Descriptor.toJvmName(newname);
/* 281:409 */     this.constPool.renameClass(oldname, newname);
/* 282:    */     
/* 283:411 */     AttributeInfo.renameClass(this.attributes, oldname, newname);
/* 284:412 */     ArrayList list = this.methods;
/* 285:413 */     int n = list.size();
/* 286:414 */     for (int i = 0; i < n; i++)
/* 287:    */     {
/* 288:415 */       MethodInfo minfo = (MethodInfo)list.get(i);
/* 289:416 */       String desc = minfo.getDescriptor();
/* 290:417 */       minfo.setDescriptor(Descriptor.rename(desc, oldname, newname));
/* 291:418 */       AttributeInfo.renameClass(minfo.getAttributes(), oldname, newname);
/* 292:    */     }
/* 293:421 */     list = this.fields;
/* 294:422 */     n = list.size();
/* 295:423 */     for (int i = 0; i < n; i++)
/* 296:    */     {
/* 297:424 */       FieldInfo finfo = (FieldInfo)list.get(i);
/* 298:425 */       String desc = finfo.getDescriptor();
/* 299:426 */       finfo.setDescriptor(Descriptor.rename(desc, oldname, newname));
/* 300:427 */       AttributeInfo.renameClass(finfo.getAttributes(), oldname, newname);
/* 301:    */     }
/* 302:    */   }
/* 303:    */   
/* 304:    */   public final void renameClass(Map classnames)
/* 305:    */   {
/* 306:441 */     String jvmNewThisName = (String)classnames.get(Descriptor.toJvmName(this.thisclassname));
/* 307:443 */     if (jvmNewThisName != null) {
/* 308:444 */       this.thisclassname = Descriptor.toJavaName(jvmNewThisName);
/* 309:    */     }
/* 310:446 */     this.constPool.renameClass(classnames);
/* 311:    */     
/* 312:448 */     AttributeInfo.renameClass(this.attributes, classnames);
/* 313:449 */     ArrayList list = this.methods;
/* 314:450 */     int n = list.size();
/* 315:451 */     for (int i = 0; i < n; i++)
/* 316:    */     {
/* 317:452 */       MethodInfo minfo = (MethodInfo)list.get(i);
/* 318:453 */       String desc = minfo.getDescriptor();
/* 319:454 */       minfo.setDescriptor(Descriptor.rename(desc, classnames));
/* 320:455 */       AttributeInfo.renameClass(minfo.getAttributes(), classnames);
/* 321:    */     }
/* 322:458 */     list = this.fields;
/* 323:459 */     n = list.size();
/* 324:460 */     for (int i = 0; i < n; i++)
/* 325:    */     {
/* 326:461 */       FieldInfo finfo = (FieldInfo)list.get(i);
/* 327:462 */       String desc = finfo.getDescriptor();
/* 328:463 */       finfo.setDescriptor(Descriptor.rename(desc, classnames));
/* 329:464 */       AttributeInfo.renameClass(finfo.getAttributes(), classnames);
/* 330:    */     }
/* 331:    */   }
/* 332:    */   
/* 333:    */   public String[] getInterfaces()
/* 334:    */   {
/* 335:473 */     if (this.cachedInterfaces != null) {
/* 336:474 */       return this.cachedInterfaces;
/* 337:    */     }
/* 338:476 */     String[] rtn = null;
/* 339:477 */     if (this.interfaces == null)
/* 340:    */     {
/* 341:478 */       rtn = new String[0];
/* 342:    */     }
/* 343:    */     else
/* 344:    */     {
/* 345:480 */       int n = this.interfaces.length;
/* 346:481 */       String[] list = new String[n];
/* 347:482 */       for (int i = 0; i < n; i++) {
/* 348:483 */         list[i] = this.constPool.getClassInfo(this.interfaces[i]);
/* 349:    */       }
/* 350:485 */       rtn = list;
/* 351:    */     }
/* 352:488 */     this.cachedInterfaces = rtn;
/* 353:489 */     return rtn;
/* 354:    */   }
/* 355:    */   
/* 356:    */   public void setInterfaces(String[] nameList)
/* 357:    */   {
/* 358:499 */     this.cachedInterfaces = null;
/* 359:500 */     if (nameList != null)
/* 360:    */     {
/* 361:501 */       int n = nameList.length;
/* 362:502 */       this.interfaces = new int[n];
/* 363:503 */       for (int i = 0; i < n; i++) {
/* 364:504 */         this.interfaces[i] = this.constPool.addClassInfo(nameList[i]);
/* 365:    */       }
/* 366:    */     }
/* 367:    */   }
/* 368:    */   
/* 369:    */   public void addInterface(String name)
/* 370:    */   {
/* 371:512 */     this.cachedInterfaces = null;
/* 372:513 */     int info = this.constPool.addClassInfo(name);
/* 373:514 */     if (this.interfaces == null)
/* 374:    */     {
/* 375:515 */       this.interfaces = new int[1];
/* 376:516 */       this.interfaces[0] = info;
/* 377:    */     }
/* 378:    */     else
/* 379:    */     {
/* 380:519 */       int n = this.interfaces.length;
/* 381:520 */       int[] newarray = new int[n + 1];
/* 382:521 */       System.arraycopy(this.interfaces, 0, newarray, 0, n);
/* 383:522 */       newarray[n] = info;
/* 384:523 */       this.interfaces = newarray;
/* 385:    */     }
/* 386:    */   }
/* 387:    */   
/* 388:    */   public List getFields()
/* 389:    */   {
/* 390:534 */     return this.fields;
/* 391:    */   }
/* 392:    */   
/* 393:    */   public void addField(FieldInfo finfo)
/* 394:    */     throws DuplicateMemberException
/* 395:    */   {
/* 396:543 */     testExistingField(finfo.getName(), finfo.getDescriptor());
/* 397:544 */     this.fields.add(finfo);
/* 398:    */   }
/* 399:    */   
/* 400:    */   public final void addField2(FieldInfo finfo)
/* 401:    */   {
/* 402:556 */     this.fields.add(finfo);
/* 403:    */   }
/* 404:    */   
/* 405:    */   private void testExistingField(String name, String descriptor)
/* 406:    */     throws DuplicateMemberException
/* 407:    */   {
/* 408:561 */     ListIterator it = this.fields.listIterator(0);
/* 409:562 */     while (it.hasNext())
/* 410:    */     {
/* 411:563 */       FieldInfo minfo = (FieldInfo)it.next();
/* 412:564 */       if (minfo.getName().equals(name)) {
/* 413:565 */         throw new DuplicateMemberException("duplicate field: " + name);
/* 414:    */       }
/* 415:    */     }
/* 416:    */   }
/* 417:    */   
/* 418:    */   public List getMethods()
/* 419:    */   {
/* 420:576 */     return this.methods;
/* 421:    */   }
/* 422:    */   
/* 423:    */   public MethodInfo getMethod(String name)
/* 424:    */   {
/* 425:586 */     ArrayList list = this.methods;
/* 426:587 */     int n = list.size();
/* 427:588 */     for (int i = 0; i < n; i++)
/* 428:    */     {
/* 429:589 */       MethodInfo minfo = (MethodInfo)list.get(i);
/* 430:590 */       if (minfo.getName().equals(name)) {
/* 431:591 */         return minfo;
/* 432:    */       }
/* 433:    */     }
/* 434:594 */     return null;
/* 435:    */   }
/* 436:    */   
/* 437:    */   public MethodInfo getStaticInitializer()
/* 438:    */   {
/* 439:602 */     return getMethod("<clinit>");
/* 440:    */   }
/* 441:    */   
/* 442:    */   public void addMethod(MethodInfo minfo)
/* 443:    */     throws DuplicateMemberException
/* 444:    */   {
/* 445:613 */     testExistingMethod(minfo);
/* 446:614 */     this.methods.add(minfo);
/* 447:    */   }
/* 448:    */   
/* 449:    */   public final void addMethod2(MethodInfo minfo)
/* 450:    */   {
/* 451:626 */     this.methods.add(minfo);
/* 452:    */   }
/* 453:    */   
/* 454:    */   private void testExistingMethod(MethodInfo newMinfo)
/* 455:    */     throws DuplicateMemberException
/* 456:    */   {
/* 457:632 */     String name = newMinfo.getName();
/* 458:633 */     String descriptor = newMinfo.getDescriptor();
/* 459:634 */     ListIterator it = this.methods.listIterator(0);
/* 460:635 */     while (it.hasNext()) {
/* 461:636 */       if (isDuplicated(newMinfo, name, descriptor, (MethodInfo)it.next(), it)) {
/* 462:637 */         throw new DuplicateMemberException("duplicate method: " + name + " in " + getName());
/* 463:    */       }
/* 464:    */     }
/* 465:    */   }
/* 466:    */   
/* 467:    */   private static boolean isDuplicated(MethodInfo newMethod, String newName, String newDesc, MethodInfo minfo, ListIterator it)
/* 468:    */   {
/* 469:645 */     if (!minfo.getName().equals(newName)) {
/* 470:646 */       return false;
/* 471:    */     }
/* 472:648 */     String desc = minfo.getDescriptor();
/* 473:649 */     if (!Descriptor.eqParamTypes(desc, newDesc)) {
/* 474:650 */       return false;
/* 475:    */     }
/* 476:652 */     if (desc.equals(newDesc))
/* 477:    */     {
/* 478:653 */       if (notBridgeMethod(minfo)) {
/* 479:654 */         return true;
/* 480:    */       }
/* 481:656 */       it.remove();
/* 482:657 */       return false;
/* 483:    */     }
/* 484:661 */     return (notBridgeMethod(minfo)) && (notBridgeMethod(newMethod));
/* 485:    */   }
/* 486:    */   
/* 487:    */   private static boolean notBridgeMethod(MethodInfo minfo)
/* 488:    */   {
/* 489:667 */     return (minfo.getAccessFlags() & 0x40) == 0;
/* 490:    */   }
/* 491:    */   
/* 492:    */   public List getAttributes()
/* 493:    */   {
/* 494:681 */     return this.attributes;
/* 495:    */   }
/* 496:    */   
/* 497:    */   public AttributeInfo getAttribute(String name)
/* 498:    */   {
/* 499:693 */     ArrayList list = this.attributes;
/* 500:694 */     int n = list.size();
/* 501:695 */     for (int i = 0; i < n; i++)
/* 502:    */     {
/* 503:696 */       AttributeInfo ai = (AttributeInfo)list.get(i);
/* 504:697 */       if (ai.getName().equals(name)) {
/* 505:698 */         return ai;
/* 506:    */       }
/* 507:    */     }
/* 508:701 */     return null;
/* 509:    */   }
/* 510:    */   
/* 511:    */   public void addAttribute(AttributeInfo info)
/* 512:    */   {
/* 513:711 */     AttributeInfo.remove(this.attributes, info.getName());
/* 514:712 */     this.attributes.add(info);
/* 515:    */   }
/* 516:    */   
/* 517:    */   public String getSourceFile()
/* 518:    */   {
/* 519:721 */     SourceFileAttribute sf = (SourceFileAttribute)getAttribute("SourceFile");
/* 520:723 */     if (sf == null) {
/* 521:724 */       return null;
/* 522:    */     }
/* 523:726 */     return sf.getFileName();
/* 524:    */   }
/* 525:    */   
/* 526:    */   private void read(DataInputStream in)
/* 527:    */     throws IOException
/* 528:    */   {
/* 529:731 */     int magic = in.readInt();
/* 530:732 */     if (magic != -889275714) {
/* 531:733 */       throw new IOException("bad magic number: " + Integer.toHexString(magic));
/* 532:    */     }
/* 533:735 */     this.minor = in.readUnsignedShort();
/* 534:736 */     this.major = in.readUnsignedShort();
/* 535:737 */     this.constPool = new ConstPool(in);
/* 536:738 */     this.accessFlags = in.readUnsignedShort();
/* 537:739 */     this.thisClass = in.readUnsignedShort();
/* 538:740 */     this.constPool.setThisClassInfo(this.thisClass);
/* 539:741 */     this.superClass = in.readUnsignedShort();
/* 540:742 */     int n = in.readUnsignedShort();
/* 541:743 */     if (n == 0)
/* 542:    */     {
/* 543:744 */       this.interfaces = null;
/* 544:    */     }
/* 545:    */     else
/* 546:    */     {
/* 547:746 */       this.interfaces = new int[n];
/* 548:747 */       for (int i = 0; i < n; i++) {
/* 549:748 */         this.interfaces[i] = in.readUnsignedShort();
/* 550:    */       }
/* 551:    */     }
/* 552:751 */     ConstPool cp = this.constPool;
/* 553:752 */     n = in.readUnsignedShort();
/* 554:753 */     this.fields = new ArrayList();
/* 555:754 */     for (int i = 0; i < n; i++) {
/* 556:755 */       addField2(new FieldInfo(cp, in));
/* 557:    */     }
/* 558:757 */     n = in.readUnsignedShort();
/* 559:758 */     this.methods = new ArrayList();
/* 560:759 */     for (i = 0; i < n; i++) {
/* 561:760 */       addMethod2(new MethodInfo(cp, in));
/* 562:    */     }
/* 563:762 */     this.attributes = new ArrayList();
/* 564:763 */     n = in.readUnsignedShort();
/* 565:764 */     for (i = 0; i < n; i++) {
/* 566:765 */       addAttribute(AttributeInfo.read(cp, in));
/* 567:    */     }
/* 568:767 */     this.thisclassname = this.constPool.getClassInfo(this.thisClass);
/* 569:    */   }
/* 570:    */   
/* 571:    */   public void write(DataOutputStream out)
/* 572:    */     throws IOException
/* 573:    */   {
/* 574:776 */     out.writeInt(-889275714);
/* 575:777 */     out.writeShort(this.minor);
/* 576:778 */     out.writeShort(this.major);
/* 577:779 */     this.constPool.write(out);
/* 578:780 */     out.writeShort(this.accessFlags);
/* 579:781 */     out.writeShort(this.thisClass);
/* 580:782 */     out.writeShort(this.superClass);
/* 581:    */     int n;
/* 582:784 */     if (this.interfaces == null) {
/* 583:785 */       n = 0;
/* 584:    */     } else {
/* 585:787 */       n = this.interfaces.length;
/* 586:    */     }
/* 587:789 */     out.writeShort(n);
/* 588:790 */     for (int i = 0; i < n; i++) {
/* 589:791 */       out.writeShort(this.interfaces[i]);
/* 590:    */     }
/* 591:793 */     ArrayList list = this.fields;
/* 592:794 */     int n = list.size();
/* 593:795 */     out.writeShort(n);
/* 594:796 */     for (i = 0; i < n; i++)
/* 595:    */     {
/* 596:797 */       FieldInfo finfo = (FieldInfo)list.get(i);
/* 597:798 */       finfo.write(out);
/* 598:    */     }
/* 599:801 */     list = this.methods;
/* 600:802 */     n = list.size();
/* 601:803 */     out.writeShort(n);
/* 602:804 */     for (i = 0; i < n; i++)
/* 603:    */     {
/* 604:805 */       MethodInfo minfo = (MethodInfo)list.get(i);
/* 605:806 */       minfo.write(out);
/* 606:    */     }
/* 607:809 */     out.writeShort(this.attributes.size());
/* 608:810 */     AttributeInfo.writeAll(this.attributes, out);
/* 609:    */   }
/* 610:    */   
/* 611:    */   public int getMajorVersion()
/* 612:    */   {
/* 613:819 */     return this.major;
/* 614:    */   }
/* 615:    */   
/* 616:    */   public void setMajorVersion(int major)
/* 617:    */   {
/* 618:829 */     this.major = major;
/* 619:    */   }
/* 620:    */   
/* 621:    */   public int getMinorVersion()
/* 622:    */   {
/* 623:838 */     return this.minor;
/* 624:    */   }
/* 625:    */   
/* 626:    */   public void setMinorVersion(int minor)
/* 627:    */   {
/* 628:848 */     this.minor = minor;
/* 629:    */   }
/* 630:    */   
/* 631:    */   public void setVersionToJava5()
/* 632:    */   {
/* 633:859 */     this.major = 49;
/* 634:860 */     this.minor = 0;
/* 635:    */   }
/* 636:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.ClassFile
 * JD-Core Version:    0.7.0.1
 */