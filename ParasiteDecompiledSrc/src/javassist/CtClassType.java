/*    1:     */ package javassist;
/*    2:     */ 
/*    3:     */ import java.io.BufferedInputStream;
/*    4:     */ import java.io.ByteArrayInputStream;
/*    5:     */ import java.io.ByteArrayOutputStream;
/*    6:     */ import java.io.DataInputStream;
/*    7:     */ import java.io.DataOutputStream;
/*    8:     */ import java.io.IOException;
/*    9:     */ import java.io.InputStream;
/*   10:     */ import java.lang.ref.WeakReference;
/*   11:     */ import java.net.URL;
/*   12:     */ import java.util.ArrayList;
/*   13:     */ import java.util.Collection;
/*   14:     */ import java.util.HashMap;
/*   15:     */ import java.util.Hashtable;
/*   16:     */ import java.util.List;
/*   17:     */ import java.util.Set;
/*   18:     */ import javassist.bytecode.AccessFlag;
/*   19:     */ import javassist.bytecode.AnnotationsAttribute;
/*   20:     */ import javassist.bytecode.AttributeInfo;
/*   21:     */ import javassist.bytecode.BadBytecode;
/*   22:     */ import javassist.bytecode.Bytecode;
/*   23:     */ import javassist.bytecode.ClassFile;
/*   24:     */ import javassist.bytecode.CodeAttribute;
/*   25:     */ import javassist.bytecode.CodeIterator;
/*   26:     */ import javassist.bytecode.ConstPool;
/*   27:     */ import javassist.bytecode.ConstantAttribute;
/*   28:     */ import javassist.bytecode.Descriptor;
/*   29:     */ import javassist.bytecode.EnclosingMethodAttribute;
/*   30:     */ import javassist.bytecode.FieldInfo;
/*   31:     */ import javassist.bytecode.InnerClassesAttribute;
/*   32:     */ import javassist.bytecode.MethodInfo;
/*   33:     */ import javassist.bytecode.ParameterAnnotationsAttribute;
/*   34:     */ import javassist.bytecode.annotation.Annotation;
/*   35:     */ import javassist.compiler.AccessorMaker;
/*   36:     */ import javassist.compiler.CompileError;
/*   37:     */ import javassist.compiler.Javac;
/*   38:     */ import javassist.expr.ExprEditor;
/*   39:     */ 
/*   40:     */ class CtClassType
/*   41:     */   extends CtClass
/*   42:     */ {
/*   43:     */   ClassPool classPool;
/*   44:     */   boolean wasChanged;
/*   45:     */   private boolean wasFrozen;
/*   46:     */   boolean wasPruned;
/*   47:     */   boolean gcConstPool;
/*   48:     */   ClassFile classfile;
/*   49:     */   byte[] rawClassfile;
/*   50:     */   private WeakReference memberCache;
/*   51:     */   private AccessorMaker accessors;
/*   52:     */   private FieldInitLink fieldInitializers;
/*   53:     */   private Hashtable hiddenMethods;
/*   54:     */   private int uniqueNumberSeed;
/*   55:  74 */   private boolean doPruning = ClassPool.doPruning;
/*   56:     */   private int getCount;
/*   57:     */   private static final int GET_THRESHOLD = 2;
/*   58:     */   
/*   59:     */   CtClassType(String name, ClassPool cp)
/*   60:     */   {
/*   61:  79 */     super(name);
/*   62:  80 */     this.classPool = cp;
/*   63:  81 */     this.wasChanged = (this.wasFrozen = this.wasPruned = this.gcConstPool = 0);
/*   64:  82 */     this.classfile = null;
/*   65:  83 */     this.rawClassfile = null;
/*   66:  84 */     this.memberCache = null;
/*   67:  85 */     this.accessors = null;
/*   68:  86 */     this.fieldInitializers = null;
/*   69:  87 */     this.hiddenMethods = null;
/*   70:  88 */     this.uniqueNumberSeed = 0;
/*   71:  89 */     this.getCount = 0;
/*   72:     */   }
/*   73:     */   
/*   74:     */   CtClassType(InputStream ins, ClassPool cp)
/*   75:     */     throws IOException
/*   76:     */   {
/*   77:  93 */     this((String)null, cp);
/*   78:  94 */     this.classfile = new ClassFile(new DataInputStream(ins));
/*   79:  95 */     this.qualifiedName = this.classfile.getName();
/*   80:     */   }
/*   81:     */   
/*   82:     */   protected void extendToString(StringBuffer buffer)
/*   83:     */   {
/*   84:  99 */     if (this.wasChanged) {
/*   85: 100 */       buffer.append("changed ");
/*   86:     */     }
/*   87: 102 */     if (this.wasFrozen) {
/*   88: 103 */       buffer.append("frozen ");
/*   89:     */     }
/*   90: 105 */     if (this.wasPruned) {
/*   91: 106 */       buffer.append("pruned ");
/*   92:     */     }
/*   93: 108 */     buffer.append(Modifier.toString(getModifiers()));
/*   94: 109 */     buffer.append(" class ");
/*   95: 110 */     buffer.append(getName());
/*   96:     */     try
/*   97:     */     {
/*   98: 113 */       CtClass ext = getSuperclass();
/*   99: 114 */       if (ext != null)
/*  100:     */       {
/*  101: 115 */         String name = ext.getName();
/*  102: 116 */         if (!name.equals("java.lang.Object")) {
/*  103: 117 */           buffer.append(" extends " + ext.getName());
/*  104:     */         }
/*  105:     */       }
/*  106:     */     }
/*  107:     */     catch (NotFoundException e)
/*  108:     */     {
/*  109: 121 */       buffer.append(" extends ??");
/*  110:     */     }
/*  111:     */     try
/*  112:     */     {
/*  113: 125 */       CtClass[] intf = getInterfaces();
/*  114: 126 */       if (intf.length > 0) {
/*  115: 127 */         buffer.append(" implements ");
/*  116:     */       }
/*  117: 129 */       for (int i = 0; i < intf.length; i++)
/*  118:     */       {
/*  119: 130 */         buffer.append(intf[i].getName());
/*  120: 131 */         buffer.append(", ");
/*  121:     */       }
/*  122:     */     }
/*  123:     */     catch (NotFoundException e)
/*  124:     */     {
/*  125: 135 */       buffer.append(" extends ??");
/*  126:     */     }
/*  127: 138 */     CtMember.Cache memCache = getMembers();
/*  128: 139 */     exToString(buffer, " fields=", memCache.fieldHead(), memCache.lastField());
/*  129:     */     
/*  130: 141 */     exToString(buffer, " constructors=", memCache.consHead(), memCache.lastCons());
/*  131:     */     
/*  132: 143 */     exToString(buffer, " methods=", memCache.methodHead(), memCache.lastMethod());
/*  133:     */   }
/*  134:     */   
/*  135:     */   private void exToString(StringBuffer buffer, String msg, CtMember head, CtMember tail)
/*  136:     */   {
/*  137: 149 */     buffer.append(msg);
/*  138: 150 */     while (head != tail)
/*  139:     */     {
/*  140: 151 */       head = head.next();
/*  141: 152 */       buffer.append(head);
/*  142: 153 */       buffer.append(", ");
/*  143:     */     }
/*  144:     */   }
/*  145:     */   
/*  146:     */   public AccessorMaker getAccessorMaker()
/*  147:     */   {
/*  148: 158 */     if (this.accessors == null) {
/*  149: 159 */       this.accessors = new AccessorMaker(this);
/*  150:     */     }
/*  151: 161 */     return this.accessors;
/*  152:     */   }
/*  153:     */   
/*  154:     */   public ClassFile getClassFile2()
/*  155:     */   {
/*  156: 165 */     ClassFile cfile = this.classfile;
/*  157: 166 */     if (cfile != null) {
/*  158: 167 */       return cfile;
/*  159:     */     }
/*  160: 169 */     this.classPool.compress();
/*  161: 170 */     if (this.rawClassfile != null) {
/*  162:     */       try
/*  163:     */       {
/*  164: 172 */         this.classfile = new ClassFile(new DataInputStream(new ByteArrayInputStream(this.rawClassfile)));
/*  165:     */         
/*  166: 174 */         this.rawClassfile = null;
/*  167: 175 */         this.getCount = 2;
/*  168: 176 */         return this.classfile;
/*  169:     */       }
/*  170:     */       catch (IOException e)
/*  171:     */       {
/*  172: 179 */         throw new RuntimeException(e.toString(), e);
/*  173:     */       }
/*  174:     */     }
/*  175: 183 */     InputStream fin = null;
/*  176:     */     try
/*  177:     */     {
/*  178: 185 */       fin = this.classPool.openClassfile(getName());
/*  179: 186 */       if (fin == null) {
/*  180: 187 */         throw new NotFoundException(getName());
/*  181:     */       }
/*  182: 189 */       fin = new BufferedInputStream(fin);
/*  183: 190 */       ClassFile cf = new ClassFile(new DataInputStream(fin));
/*  184: 191 */       if (!cf.getName().equals(this.qualifiedName)) {
/*  185: 192 */         throw new RuntimeException("cannot find " + this.qualifiedName + ": " + cf.getName() + " found in " + this.qualifiedName.replace('.', '/') + ".class");
/*  186:     */       }
/*  187: 196 */       this.classfile = cf;
/*  188: 197 */       return cf;
/*  189:     */     }
/*  190:     */     catch (NotFoundException e)
/*  191:     */     {
/*  192: 200 */       throw new RuntimeException(e.toString(), e);
/*  193:     */     }
/*  194:     */     catch (IOException e)
/*  195:     */     {
/*  196: 203 */       throw new RuntimeException(e.toString(), e);
/*  197:     */     }
/*  198:     */     finally
/*  199:     */     {
/*  200: 206 */       if (fin != null) {
/*  201:     */         try
/*  202:     */         {
/*  203: 208 */           fin.close();
/*  204:     */         }
/*  205:     */         catch (IOException e) {}
/*  206:     */       }
/*  207:     */     }
/*  208:     */   }
/*  209:     */   
/*  210:     */   final void incGetCounter()
/*  211:     */   {
/*  212: 219 */     this.getCount += 1;
/*  213:     */   }
/*  214:     */   
/*  215:     */   void compress()
/*  216:     */   {
/*  217: 227 */     if (this.getCount < 2) {
/*  218: 228 */       if ((!isModified()) && (ClassPool.releaseUnmodifiedClassFile)) {
/*  219: 229 */         removeClassFile();
/*  220: 230 */       } else if ((isFrozen()) && (!this.wasPruned)) {
/*  221: 231 */         saveClassFile();
/*  222:     */       }
/*  223:     */     }
/*  224: 233 */     this.getCount = 0;
/*  225:     */   }
/*  226:     */   
/*  227:     */   private synchronized void saveClassFile()
/*  228:     */   {
/*  229: 243 */     if ((this.classfile == null) || (hasMemberCache() != null)) {
/*  230: 244 */       return;
/*  231:     */     }
/*  232: 246 */     ByteArrayOutputStream barray = new ByteArrayOutputStream();
/*  233: 247 */     DataOutputStream out = new DataOutputStream(barray);
/*  234:     */     try
/*  235:     */     {
/*  236: 249 */       this.classfile.write(out);
/*  237: 250 */       barray.close();
/*  238: 251 */       this.rawClassfile = barray.toByteArray();
/*  239: 252 */       this.classfile = null;
/*  240:     */     }
/*  241:     */     catch (IOException e) {}
/*  242:     */   }
/*  243:     */   
/*  244:     */   private synchronized void removeClassFile()
/*  245:     */   {
/*  246: 258 */     if ((this.classfile != null) && (!isModified()) && (hasMemberCache() == null)) {
/*  247: 259 */       this.classfile = null;
/*  248:     */     }
/*  249:     */   }
/*  250:     */   
/*  251:     */   public ClassPool getClassPool()
/*  252:     */   {
/*  253: 262 */     return this.classPool;
/*  254:     */   }
/*  255:     */   
/*  256:     */   void setClassPool(ClassPool cp)
/*  257:     */   {
/*  258: 264 */     this.classPool = cp;
/*  259:     */   }
/*  260:     */   
/*  261:     */   public URL getURL()
/*  262:     */     throws NotFoundException
/*  263:     */   {
/*  264: 267 */     URL url = this.classPool.find(getName());
/*  265: 268 */     if (url == null) {
/*  266: 269 */       throw new NotFoundException(getName());
/*  267:     */     }
/*  268: 271 */     return url;
/*  269:     */   }
/*  270:     */   
/*  271:     */   public boolean isModified()
/*  272:     */   {
/*  273: 274 */     return this.wasChanged;
/*  274:     */   }
/*  275:     */   
/*  276:     */   public boolean isFrozen()
/*  277:     */   {
/*  278: 276 */     return this.wasFrozen;
/*  279:     */   }
/*  280:     */   
/*  281:     */   public void freeze()
/*  282:     */   {
/*  283: 278 */     this.wasFrozen = true;
/*  284:     */   }
/*  285:     */   
/*  286:     */   void checkModify()
/*  287:     */     throws RuntimeException
/*  288:     */   {
/*  289: 281 */     if (isFrozen())
/*  290:     */     {
/*  291: 282 */       String msg = getName() + " class is frozen";
/*  292: 283 */       if (this.wasPruned) {
/*  293: 284 */         msg = msg + " and pruned";
/*  294:     */       }
/*  295: 286 */       throw new RuntimeException(msg);
/*  296:     */     }
/*  297: 289 */     this.wasChanged = true;
/*  298:     */   }
/*  299:     */   
/*  300:     */   public void defrost()
/*  301:     */   {
/*  302: 293 */     checkPruned("defrost");
/*  303: 294 */     this.wasFrozen = false;
/*  304:     */   }
/*  305:     */   
/*  306:     */   public boolean subtypeOf(CtClass clazz)
/*  307:     */     throws NotFoundException
/*  308:     */   {
/*  309: 299 */     String cname = clazz.getName();
/*  310: 300 */     if ((this == clazz) || (getName().equals(cname))) {
/*  311: 301 */       return true;
/*  312:     */     }
/*  313: 303 */     ClassFile file = getClassFile2();
/*  314: 304 */     String supername = file.getSuperclass();
/*  315: 305 */     if ((supername != null) && (supername.equals(cname))) {
/*  316: 306 */       return true;
/*  317:     */     }
/*  318: 308 */     String[] ifs = file.getInterfaces();
/*  319: 309 */     int num = ifs.length;
/*  320: 310 */     for (int i = 0; i < num; i++) {
/*  321: 311 */       if (ifs[i].equals(cname)) {
/*  322: 312 */         return true;
/*  323:     */       }
/*  324:     */     }
/*  325: 314 */     if ((supername != null) && (this.classPool.get(supername).subtypeOf(clazz))) {
/*  326: 315 */       return true;
/*  327:     */     }
/*  328: 317 */     for (i = 0; i < num; i++) {
/*  329: 318 */       if (this.classPool.get(ifs[i]).subtypeOf(clazz)) {
/*  330: 319 */         return true;
/*  331:     */       }
/*  332:     */     }
/*  333: 321 */     return false;
/*  334:     */   }
/*  335:     */   
/*  336:     */   public void setName(String name)
/*  337:     */     throws RuntimeException
/*  338:     */   {
/*  339: 325 */     String oldname = getName();
/*  340: 326 */     if (name.equals(oldname)) {
/*  341: 327 */       return;
/*  342:     */     }
/*  343: 330 */     this.classPool.checkNotFrozen(name);
/*  344: 331 */     ClassFile cf = getClassFile2();
/*  345: 332 */     super.setName(name);
/*  346: 333 */     cf.setName(name);
/*  347: 334 */     nameReplaced();
/*  348: 335 */     this.classPool.classNameChanged(oldname, this);
/*  349:     */   }
/*  350:     */   
/*  351:     */   public void replaceClassName(ClassMap classnames)
/*  352:     */     throws RuntimeException
/*  353:     */   {
/*  354: 341 */     String oldClassName = getName();
/*  355: 342 */     String newClassName = (String)classnames.get(Descriptor.toJvmName(oldClassName));
/*  356: 344 */     if (newClassName != null)
/*  357:     */     {
/*  358: 345 */       newClassName = Descriptor.toJavaName(newClassName);
/*  359:     */       
/*  360: 347 */       this.classPool.checkNotFrozen(newClassName);
/*  361:     */     }
/*  362: 350 */     super.replaceClassName(classnames);
/*  363: 351 */     ClassFile cf = getClassFile2();
/*  364: 352 */     cf.renameClass(classnames);
/*  365: 353 */     nameReplaced();
/*  366: 355 */     if (newClassName != null)
/*  367:     */     {
/*  368: 356 */       super.setName(newClassName);
/*  369: 357 */       this.classPool.classNameChanged(oldClassName, this);
/*  370:     */     }
/*  371:     */   }
/*  372:     */   
/*  373:     */   public void replaceClassName(String oldname, String newname)
/*  374:     */     throws RuntimeException
/*  375:     */   {
/*  376: 364 */     String thisname = getName();
/*  377: 365 */     if (thisname.equals(oldname))
/*  378:     */     {
/*  379: 366 */       setName(newname);
/*  380:     */     }
/*  381:     */     else
/*  382:     */     {
/*  383: 368 */       super.replaceClassName(oldname, newname);
/*  384: 369 */       getClassFile2().renameClass(oldname, newname);
/*  385: 370 */       nameReplaced();
/*  386:     */     }
/*  387:     */   }
/*  388:     */   
/*  389:     */   public boolean isInterface()
/*  390:     */   {
/*  391: 375 */     return Modifier.isInterface(getModifiers());
/*  392:     */   }
/*  393:     */   
/*  394:     */   public boolean isAnnotation()
/*  395:     */   {
/*  396: 379 */     return Modifier.isAnnotation(getModifiers());
/*  397:     */   }
/*  398:     */   
/*  399:     */   public boolean isEnum()
/*  400:     */   {
/*  401: 383 */     return Modifier.isEnum(getModifiers());
/*  402:     */   }
/*  403:     */   
/*  404:     */   public int getModifiers()
/*  405:     */   {
/*  406: 387 */     ClassFile cf = getClassFile2();
/*  407: 388 */     int acc = cf.getAccessFlags();
/*  408: 389 */     acc = AccessFlag.clear(acc, 32);
/*  409: 390 */     int inner = cf.getInnerAccessFlags();
/*  410: 391 */     if ((inner != -1) && ((inner & 0x8) != 0)) {
/*  411: 392 */       acc |= 0x8;
/*  412:     */     }
/*  413: 394 */     return AccessFlag.toModifier(acc);
/*  414:     */   }
/*  415:     */   
/*  416:     */   public CtClass[] getNestedClasses()
/*  417:     */     throws NotFoundException
/*  418:     */   {
/*  419: 398 */     ClassFile cf = getClassFile2();
/*  420: 399 */     InnerClassesAttribute ica = (InnerClassesAttribute)cf.getAttribute("InnerClasses");
/*  421: 401 */     if (ica == null) {
/*  422: 402 */       return new CtClass[0];
/*  423:     */     }
/*  424: 404 */     String thisName = cf.getName();
/*  425: 405 */     int n = ica.tableLength();
/*  426: 406 */     ArrayList list = new ArrayList(n);
/*  427: 407 */     for (int i = 0; i < n; i++)
/*  428:     */     {
/*  429: 408 */       String outer = ica.outerClass(i);
/*  430: 413 */       if ((outer == null) || (outer.equals(thisName)))
/*  431:     */       {
/*  432: 414 */         String inner = ica.innerClass(i);
/*  433: 415 */         if (inner != null) {
/*  434: 416 */           list.add(this.classPool.get(inner));
/*  435:     */         }
/*  436:     */       }
/*  437:     */     }
/*  438: 420 */     return (CtClass[])list.toArray(new CtClass[list.size()]);
/*  439:     */   }
/*  440:     */   
/*  441:     */   public void setModifiers(int mod)
/*  442:     */   {
/*  443: 424 */     ClassFile cf = getClassFile2();
/*  444: 425 */     if (Modifier.isStatic(mod))
/*  445:     */     {
/*  446: 426 */       int flags = cf.getInnerAccessFlags();
/*  447: 427 */       if ((flags != -1) && ((flags & 0x8) != 0)) {
/*  448: 428 */         mod &= 0xFFFFFFF7;
/*  449:     */       } else {
/*  450: 430 */         throw new RuntimeException("cannot change " + getName() + " into a static class");
/*  451:     */       }
/*  452:     */     }
/*  453: 433 */     checkModify();
/*  454: 434 */     cf.setAccessFlags(AccessFlag.of(mod));
/*  455:     */   }
/*  456:     */   
/*  457:     */   public boolean hasAnnotation(Class clz)
/*  458:     */   {
/*  459: 438 */     ClassFile cf = getClassFile2();
/*  460: 439 */     AnnotationsAttribute ainfo = (AnnotationsAttribute)cf.getAttribute("RuntimeInvisibleAnnotations");
/*  461:     */     
/*  462: 441 */     AnnotationsAttribute ainfo2 = (AnnotationsAttribute)cf.getAttribute("RuntimeVisibleAnnotations");
/*  463:     */     
/*  464: 443 */     return hasAnnotationType(clz, getClassPool(), ainfo, ainfo2);
/*  465:     */   }
/*  466:     */   
/*  467:     */   static boolean hasAnnotationType(Class clz, ClassPool cp, AnnotationsAttribute a1, AnnotationsAttribute a2)
/*  468:     */   {
/*  469:     */     Annotation[] anno1;
/*  470:     */     Annotation[] anno1;
/*  471: 451 */     if (a1 == null) {
/*  472: 452 */       anno1 = null;
/*  473:     */     } else {
/*  474: 454 */       anno1 = a1.getAnnotations();
/*  475:     */     }
/*  476:     */     Annotation[] anno2;
/*  477:     */     Annotation[] anno2;
/*  478: 456 */     if (a2 == null) {
/*  479: 457 */       anno2 = null;
/*  480:     */     } else {
/*  481: 459 */       anno2 = a2.getAnnotations();
/*  482:     */     }
/*  483: 461 */     String typeName = clz.getName();
/*  484: 462 */     if (anno1 != null) {
/*  485: 463 */       for (int i = 0; i < anno1.length; i++) {
/*  486: 464 */         if (anno1[i].getTypeName().equals(typeName)) {
/*  487: 465 */           return true;
/*  488:     */         }
/*  489:     */       }
/*  490:     */     }
/*  491: 467 */     if (anno2 != null) {
/*  492: 468 */       for (int i = 0; i < anno2.length; i++) {
/*  493: 469 */         if (anno2[i].getTypeName().equals(typeName)) {
/*  494: 470 */           return true;
/*  495:     */         }
/*  496:     */       }
/*  497:     */     }
/*  498: 472 */     return false;
/*  499:     */   }
/*  500:     */   
/*  501:     */   public Object getAnnotation(Class clz)
/*  502:     */     throws ClassNotFoundException
/*  503:     */   {
/*  504: 476 */     ClassFile cf = getClassFile2();
/*  505: 477 */     AnnotationsAttribute ainfo = (AnnotationsAttribute)cf.getAttribute("RuntimeInvisibleAnnotations");
/*  506:     */     
/*  507: 479 */     AnnotationsAttribute ainfo2 = (AnnotationsAttribute)cf.getAttribute("RuntimeVisibleAnnotations");
/*  508:     */     
/*  509: 481 */     return getAnnotationType(clz, getClassPool(), ainfo, ainfo2);
/*  510:     */   }
/*  511:     */   
/*  512:     */   static Object getAnnotationType(Class clz, ClassPool cp, AnnotationsAttribute a1, AnnotationsAttribute a2)
/*  513:     */     throws ClassNotFoundException
/*  514:     */   {
/*  515:     */     Annotation[] anno1;
/*  516:     */     Annotation[] anno1;
/*  517: 490 */     if (a1 == null) {
/*  518: 491 */       anno1 = null;
/*  519:     */     } else {
/*  520: 493 */       anno1 = a1.getAnnotations();
/*  521:     */     }
/*  522:     */     Annotation[] anno2;
/*  523:     */     Annotation[] anno2;
/*  524: 495 */     if (a2 == null) {
/*  525: 496 */       anno2 = null;
/*  526:     */     } else {
/*  527: 498 */       anno2 = a2.getAnnotations();
/*  528:     */     }
/*  529: 500 */     String typeName = clz.getName();
/*  530: 501 */     if (anno1 != null) {
/*  531: 502 */       for (int i = 0; i < anno1.length; i++) {
/*  532: 503 */         if (anno1[i].getTypeName().equals(typeName)) {
/*  533: 504 */           return toAnnoType(anno1[i], cp);
/*  534:     */         }
/*  535:     */       }
/*  536:     */     }
/*  537: 506 */     if (anno2 != null) {
/*  538: 507 */       for (int i = 0; i < anno2.length; i++) {
/*  539: 508 */         if (anno2[i].getTypeName().equals(typeName)) {
/*  540: 509 */           return toAnnoType(anno2[i], cp);
/*  541:     */         }
/*  542:     */       }
/*  543:     */     }
/*  544: 511 */     return null;
/*  545:     */   }
/*  546:     */   
/*  547:     */   public Object[] getAnnotations()
/*  548:     */     throws ClassNotFoundException
/*  549:     */   {
/*  550: 515 */     return getAnnotations(false);
/*  551:     */   }
/*  552:     */   
/*  553:     */   public Object[] getAvailableAnnotations()
/*  554:     */   {
/*  555:     */     try
/*  556:     */     {
/*  557: 520 */       return getAnnotations(true);
/*  558:     */     }
/*  559:     */     catch (ClassNotFoundException e)
/*  560:     */     {
/*  561: 523 */       throw new RuntimeException("Unexpected exception ", e);
/*  562:     */     }
/*  563:     */   }
/*  564:     */   
/*  565:     */   private Object[] getAnnotations(boolean ignoreNotFound)
/*  566:     */     throws ClassNotFoundException
/*  567:     */   {
/*  568: 530 */     ClassFile cf = getClassFile2();
/*  569: 531 */     AnnotationsAttribute ainfo = (AnnotationsAttribute)cf.getAttribute("RuntimeInvisibleAnnotations");
/*  570:     */     
/*  571: 533 */     AnnotationsAttribute ainfo2 = (AnnotationsAttribute)cf.getAttribute("RuntimeVisibleAnnotations");
/*  572:     */     
/*  573: 535 */     return toAnnotationType(ignoreNotFound, getClassPool(), ainfo, ainfo2);
/*  574:     */   }
/*  575:     */   
/*  576:     */   static Object[] toAnnotationType(boolean ignoreNotFound, ClassPool cp, AnnotationsAttribute a1, AnnotationsAttribute a2)
/*  577:     */     throws ClassNotFoundException
/*  578:     */   {
/*  579:     */     int size1;
/*  580:     */     Annotation[] anno1;
/*  581:     */     int size1;
/*  582: 545 */     if (a1 == null)
/*  583:     */     {
/*  584: 546 */       Annotation[] anno1 = null;
/*  585: 547 */       size1 = 0;
/*  586:     */     }
/*  587:     */     else
/*  588:     */     {
/*  589: 550 */       anno1 = a1.getAnnotations();
/*  590: 551 */       size1 = anno1.length;
/*  591:     */     }
/*  592:     */     int size2;
/*  593:     */     Annotation[] anno2;
/*  594:     */     int size2;
/*  595: 554 */     if (a2 == null)
/*  596:     */     {
/*  597: 555 */       Annotation[] anno2 = null;
/*  598: 556 */       size2 = 0;
/*  599:     */     }
/*  600:     */     else
/*  601:     */     {
/*  602: 559 */       anno2 = a2.getAnnotations();
/*  603: 560 */       size2 = anno2.length;
/*  604:     */     }
/*  605: 563 */     if (!ignoreNotFound)
/*  606:     */     {
/*  607: 564 */       Object[] result = new Object[size1 + size2];
/*  608: 565 */       for (int i = 0; i < size1; i++) {
/*  609: 566 */         result[i] = toAnnoType(anno1[i], cp);
/*  610:     */       }
/*  611: 568 */       for (int j = 0; j < size2; j++) {
/*  612: 569 */         result[(j + size1)] = toAnnoType(anno2[j], cp);
/*  613:     */       }
/*  614: 571 */       return result;
/*  615:     */     }
/*  616: 574 */     ArrayList annotations = new ArrayList();
/*  617: 575 */     for (int i = 0; i < size1; i++) {
/*  618:     */       try
/*  619:     */       {
/*  620: 577 */         annotations.add(toAnnoType(anno1[i], cp));
/*  621:     */       }
/*  622:     */       catch (ClassNotFoundException e) {}
/*  623:     */     }
/*  624: 581 */     for (int j = 0; j < size2; j++) {
/*  625:     */       try
/*  626:     */       {
/*  627: 583 */         annotations.add(toAnnoType(anno2[j], cp));
/*  628:     */       }
/*  629:     */       catch (ClassNotFoundException e) {}
/*  630:     */     }
/*  631: 588 */     return annotations.toArray();
/*  632:     */   }
/*  633:     */   
/*  634:     */   static Object[][] toAnnotationType(boolean ignoreNotFound, ClassPool cp, ParameterAnnotationsAttribute a1, ParameterAnnotationsAttribute a2, MethodInfo minfo)
/*  635:     */     throws ClassNotFoundException
/*  636:     */   {
/*  637: 598 */     int numParameters = 0;
/*  638: 599 */     if (a1 != null) {
/*  639: 600 */       numParameters = a1.numParameters();
/*  640: 601 */     } else if (a2 != null) {
/*  641: 602 */       numParameters = a2.numParameters();
/*  642:     */     } else {
/*  643: 604 */       numParameters = Descriptor.numOfParameters(minfo.getDescriptor());
/*  644:     */     }
/*  645: 606 */     Object[][] result = new Object[numParameters][];
/*  646: 607 */     for (int i = 0; i < numParameters; i++)
/*  647:     */     {
/*  648:     */       int size1;
/*  649:     */       Annotation[] anno1;
/*  650:     */       int size1;
/*  651: 611 */       if (a1 == null)
/*  652:     */       {
/*  653: 612 */         Annotation[] anno1 = null;
/*  654: 613 */         size1 = 0;
/*  655:     */       }
/*  656:     */       else
/*  657:     */       {
/*  658: 616 */         anno1 = a1.getAnnotations()[i];
/*  659: 617 */         size1 = anno1.length;
/*  660:     */       }
/*  661:     */       int size2;
/*  662:     */       Annotation[] anno2;
/*  663:     */       int size2;
/*  664: 620 */       if (a2 == null)
/*  665:     */       {
/*  666: 621 */         Annotation[] anno2 = null;
/*  667: 622 */         size2 = 0;
/*  668:     */       }
/*  669:     */       else
/*  670:     */       {
/*  671: 625 */         anno2 = a2.getAnnotations()[i];
/*  672: 626 */         size2 = anno2.length;
/*  673:     */       }
/*  674: 629 */       if (!ignoreNotFound)
/*  675:     */       {
/*  676: 630 */         result[i] = new Object[size1 + size2];
/*  677: 631 */         for (int j = 0; j < size1; j++) {
/*  678: 632 */           result[i][j] = toAnnoType(anno1[j], cp);
/*  679:     */         }
/*  680: 634 */         for (int j = 0; j < size2; j++) {
/*  681: 635 */           result[i][(j + size1)] = toAnnoType(anno2[j], cp);
/*  682:     */         }
/*  683:     */       }
/*  684:     */       else
/*  685:     */       {
/*  686: 638 */         ArrayList annotations = new ArrayList();
/*  687: 639 */         for (int j = 0; j < size1; j++) {
/*  688:     */           try
/*  689:     */           {
/*  690: 641 */             annotations.add(toAnnoType(anno1[j], cp));
/*  691:     */           }
/*  692:     */           catch (ClassNotFoundException e) {}
/*  693:     */         }
/*  694: 645 */         for (int j = 0; j < size2; j++) {
/*  695:     */           try
/*  696:     */           {
/*  697: 647 */             annotations.add(toAnnoType(anno2[j], cp));
/*  698:     */           }
/*  699:     */           catch (ClassNotFoundException e) {}
/*  700:     */         }
/*  701: 652 */         result[i] = annotations.toArray();
/*  702:     */       }
/*  703:     */     }
/*  704: 656 */     return result;
/*  705:     */   }
/*  706:     */   
/*  707:     */   private static Object toAnnoType(Annotation anno, ClassPool cp)
/*  708:     */     throws ClassNotFoundException
/*  709:     */   {
/*  710:     */     try
/*  711:     */     {
/*  712: 663 */       ClassLoader cl = cp.getClassLoader();
/*  713: 664 */       return anno.toAnnotationType(cl, cp);
/*  714:     */     }
/*  715:     */     catch (ClassNotFoundException e)
/*  716:     */     {
/*  717: 667 */       ClassLoader cl2 = cp.getClass().getClassLoader();
/*  718: 668 */       return anno.toAnnotationType(cl2, cp);
/*  719:     */     }
/*  720:     */   }
/*  721:     */   
/*  722:     */   public boolean subclassOf(CtClass superclass)
/*  723:     */   {
/*  724: 673 */     if (superclass == null) {
/*  725: 674 */       return false;
/*  726:     */     }
/*  727: 676 */     String superName = superclass.getName();
/*  728: 677 */     CtClass curr = this;
/*  729:     */     try
/*  730:     */     {
/*  731: 679 */       while (curr != null)
/*  732:     */       {
/*  733: 680 */         if (curr.getName().equals(superName)) {
/*  734: 681 */           return true;
/*  735:     */         }
/*  736: 683 */         curr = curr.getSuperclass();
/*  737:     */       }
/*  738:     */     }
/*  739:     */     catch (Exception ignored) {}
/*  740: 687 */     return false;
/*  741:     */   }
/*  742:     */   
/*  743:     */   public CtClass getSuperclass()
/*  744:     */     throws NotFoundException
/*  745:     */   {
/*  746: 691 */     String supername = getClassFile2().getSuperclass();
/*  747: 692 */     if (supername == null) {
/*  748: 693 */       return null;
/*  749:     */     }
/*  750: 695 */     return this.classPool.get(supername);
/*  751:     */   }
/*  752:     */   
/*  753:     */   public void setSuperclass(CtClass clazz)
/*  754:     */     throws CannotCompileException
/*  755:     */   {
/*  756: 699 */     checkModify();
/*  757: 700 */     if (isInterface()) {
/*  758: 701 */       addInterface(clazz);
/*  759:     */     } else {
/*  760: 703 */       getClassFile2().setSuperclass(clazz.getName());
/*  761:     */     }
/*  762:     */   }
/*  763:     */   
/*  764:     */   public CtClass[] getInterfaces()
/*  765:     */     throws NotFoundException
/*  766:     */   {
/*  767: 707 */     String[] ifs = getClassFile2().getInterfaces();
/*  768: 708 */     int num = ifs.length;
/*  769: 709 */     CtClass[] ifc = new CtClass[num];
/*  770: 710 */     for (int i = 0; i < num; i++) {
/*  771: 711 */       ifc[i] = this.classPool.get(ifs[i]);
/*  772:     */     }
/*  773: 713 */     return ifc;
/*  774:     */   }
/*  775:     */   
/*  776:     */   public void setInterfaces(CtClass[] list)
/*  777:     */   {
/*  778: 717 */     checkModify();
/*  779:     */     String[] ifs;
/*  780:     */     String[] ifs;
/*  781: 719 */     if (list == null)
/*  782:     */     {
/*  783: 720 */       ifs = new String[0];
/*  784:     */     }
/*  785:     */     else
/*  786:     */     {
/*  787: 722 */       int num = list.length;
/*  788: 723 */       ifs = new String[num];
/*  789: 724 */       for (int i = 0; i < num; i++) {
/*  790: 725 */         ifs[i] = list[i].getName();
/*  791:     */       }
/*  792:     */     }
/*  793: 728 */     getClassFile2().setInterfaces(ifs);
/*  794:     */   }
/*  795:     */   
/*  796:     */   public void addInterface(CtClass anInterface)
/*  797:     */   {
/*  798: 732 */     checkModify();
/*  799: 733 */     if (anInterface != null) {
/*  800: 734 */       getClassFile2().addInterface(anInterface.getName());
/*  801:     */     }
/*  802:     */   }
/*  803:     */   
/*  804:     */   public CtClass getDeclaringClass()
/*  805:     */     throws NotFoundException
/*  806:     */   {
/*  807: 738 */     ClassFile cf = getClassFile2();
/*  808: 739 */     InnerClassesAttribute ica = (InnerClassesAttribute)cf.getAttribute("InnerClasses");
/*  809: 741 */     if (ica == null) {
/*  810: 742 */       return null;
/*  811:     */     }
/*  812: 744 */     String name = getName();
/*  813: 745 */     int n = ica.tableLength();
/*  814: 746 */     for (int i = 0; i < n; i++) {
/*  815: 747 */       if (name.equals(ica.innerClass(i)))
/*  816:     */       {
/*  817: 748 */         String outName = ica.outerClass(i);
/*  818: 749 */         if (outName != null) {
/*  819: 750 */           return this.classPool.get(outName);
/*  820:     */         }
/*  821: 753 */         EnclosingMethodAttribute ema = (EnclosingMethodAttribute)cf.getAttribute("EnclosingMethod");
/*  822: 756 */         if (ema != null) {
/*  823: 757 */           return this.classPool.get(ema.className());
/*  824:     */         }
/*  825:     */       }
/*  826:     */     }
/*  827: 761 */     return null;
/*  828:     */   }
/*  829:     */   
/*  830:     */   public CtMethod getEnclosingMethod()
/*  831:     */     throws NotFoundException
/*  832:     */   {
/*  833: 765 */     ClassFile cf = getClassFile2();
/*  834: 766 */     EnclosingMethodAttribute ema = (EnclosingMethodAttribute)cf.getAttribute("EnclosingMethod");
/*  835: 769 */     if (ema != null)
/*  836:     */     {
/*  837: 770 */       CtClass enc = this.classPool.get(ema.className());
/*  838: 771 */       return enc.getMethod(ema.methodName(), ema.methodDescriptor());
/*  839:     */     }
/*  840: 774 */     return null;
/*  841:     */   }
/*  842:     */   
/*  843:     */   public CtClass makeNestedClass(String name, boolean isStatic)
/*  844:     */   {
/*  845: 778 */     if (!isStatic) {
/*  846: 779 */       throw new RuntimeException("sorry, only nested static class is supported");
/*  847:     */     }
/*  848: 782 */     checkModify();
/*  849: 783 */     CtClass c = this.classPool.makeNestedClass(getName() + "$" + name);
/*  850: 784 */     ClassFile cf = getClassFile2();
/*  851: 785 */     ClassFile cf2 = c.getClassFile2();
/*  852: 786 */     InnerClassesAttribute ica = (InnerClassesAttribute)cf.getAttribute("InnerClasses");
/*  853: 788 */     if (ica == null)
/*  854:     */     {
/*  855: 789 */       ica = new InnerClassesAttribute(cf.getConstPool());
/*  856: 790 */       cf.addAttribute(ica);
/*  857:     */     }
/*  858: 793 */     ica.append(c.getName(), getName(), name, cf2.getAccessFlags() & 0xFFFFFFDF | 0x8);
/*  859:     */     
/*  860: 795 */     cf2.addAttribute(ica.copy(cf2.getConstPool(), null));
/*  861: 796 */     return c;
/*  862:     */   }
/*  863:     */   
/*  864:     */   private void nameReplaced()
/*  865:     */   {
/*  866: 802 */     CtMember.Cache cache = hasMemberCache();
/*  867: 803 */     if (cache != null)
/*  868:     */     {
/*  869: 804 */       CtMember mth = cache.methodHead();
/*  870: 805 */       CtMember tail = cache.lastMethod();
/*  871: 806 */       while (mth != tail)
/*  872:     */       {
/*  873: 807 */         mth = mth.next();
/*  874: 808 */         mth.nameReplaced();
/*  875:     */       }
/*  876:     */     }
/*  877:     */   }
/*  878:     */   
/*  879:     */   protected CtMember.Cache hasMemberCache()
/*  880:     */   {
/*  881: 817 */     if (this.memberCache != null) {
/*  882: 818 */       return (CtMember.Cache)this.memberCache.get();
/*  883:     */     }
/*  884: 820 */     return null;
/*  885:     */   }
/*  886:     */   
/*  887:     */   protected synchronized CtMember.Cache getMembers()
/*  888:     */   {
/*  889: 824 */     CtMember.Cache cache = null;
/*  890: 825 */     if ((this.memberCache == null) || ((cache = (CtMember.Cache)this.memberCache.get()) == null))
/*  891:     */     {
/*  892: 827 */       cache = new CtMember.Cache(this);
/*  893: 828 */       makeFieldCache(cache);
/*  894: 829 */       makeBehaviorCache(cache);
/*  895: 830 */       this.memberCache = new WeakReference(cache);
/*  896:     */     }
/*  897: 833 */     return cache;
/*  898:     */   }
/*  899:     */   
/*  900:     */   private void makeFieldCache(CtMember.Cache cache)
/*  901:     */   {
/*  902: 837 */     List list = getClassFile2().getFields();
/*  903: 838 */     int n = list.size();
/*  904: 839 */     for (int i = 0; i < n; i++)
/*  905:     */     {
/*  906: 840 */       FieldInfo finfo = (FieldInfo)list.get(i);
/*  907: 841 */       CtField newField = new CtField(finfo, this);
/*  908: 842 */       cache.addField(newField);
/*  909:     */     }
/*  910:     */   }
/*  911:     */   
/*  912:     */   private void makeBehaviorCache(CtMember.Cache cache)
/*  913:     */   {
/*  914: 847 */     List list = getClassFile2().getMethods();
/*  915: 848 */     int n = list.size();
/*  916: 849 */     for (int i = 0; i < n; i++)
/*  917:     */     {
/*  918: 850 */       MethodInfo minfo = (MethodInfo)list.get(i);
/*  919: 851 */       if (minfo.isMethod())
/*  920:     */       {
/*  921: 852 */         CtMethod newMethod = new CtMethod(minfo, this);
/*  922: 853 */         cache.addMethod(newMethod);
/*  923:     */       }
/*  924:     */       else
/*  925:     */       {
/*  926: 856 */         CtConstructor newCons = new CtConstructor(minfo, this);
/*  927: 857 */         cache.addConstructor(newCons);
/*  928:     */       }
/*  929:     */     }
/*  930:     */   }
/*  931:     */   
/*  932:     */   public CtField[] getFields()
/*  933:     */   {
/*  934: 863 */     ArrayList alist = new ArrayList();
/*  935: 864 */     getFields(alist, this);
/*  936: 865 */     return (CtField[])alist.toArray(new CtField[alist.size()]);
/*  937:     */   }
/*  938:     */   
/*  939:     */   private static void getFields(ArrayList alist, CtClass cc)
/*  940:     */   {
/*  941: 870 */     if (cc == null) {
/*  942: 871 */       return;
/*  943:     */     }
/*  944:     */     try
/*  945:     */     {
/*  946: 874 */       getFields(alist, cc.getSuperclass());
/*  947:     */     }
/*  948:     */     catch (NotFoundException e) {}
/*  949:     */     try
/*  950:     */     {
/*  951: 879 */       CtClass[] ifs = cc.getInterfaces();
/*  952: 880 */       int num = ifs.length;
/*  953: 881 */       for (int i = 0; i < num; i++) {
/*  954: 882 */         getFields(alist, ifs[i]);
/*  955:     */       }
/*  956:     */     }
/*  957:     */     catch (NotFoundException e) {}
/*  958: 886 */     CtMember.Cache memCache = ((CtClassType)cc).getMembers();
/*  959: 887 */     CtMember field = memCache.fieldHead();
/*  960: 888 */     CtMember tail = memCache.lastField();
/*  961: 889 */     while (field != tail)
/*  962:     */     {
/*  963: 890 */       field = field.next();
/*  964: 891 */       if (!Modifier.isPrivate(field.getModifiers())) {
/*  965: 892 */         alist.add(field);
/*  966:     */       }
/*  967:     */     }
/*  968:     */   }
/*  969:     */   
/*  970:     */   public CtField getField(String name)
/*  971:     */     throws NotFoundException
/*  972:     */   {
/*  973: 897 */     CtField f = getField2(name);
/*  974: 898 */     if (f == null) {
/*  975: 899 */       throw new NotFoundException("field: " + name + " in " + getName());
/*  976:     */     }
/*  977: 901 */     return f;
/*  978:     */   }
/*  979:     */   
/*  980:     */   CtField getField2(String name)
/*  981:     */   {
/*  982: 905 */     CtField df = getDeclaredField2(name);
/*  983: 906 */     if (df != null) {
/*  984: 907 */       return df;
/*  985:     */     }
/*  986:     */     try
/*  987:     */     {
/*  988: 910 */       CtClass[] ifs = getInterfaces();
/*  989: 911 */       int num = ifs.length;
/*  990: 912 */       for (int i = 0; i < num; i++)
/*  991:     */       {
/*  992: 913 */         CtField f = ifs[i].getField2(name);
/*  993: 914 */         if (f != null) {
/*  994: 915 */           return f;
/*  995:     */         }
/*  996:     */       }
/*  997: 918 */       CtClass s = getSuperclass();
/*  998: 919 */       if (s != null) {
/*  999: 920 */         return s.getField2(name);
/* 1000:     */       }
/* 1001:     */     }
/* 1002:     */     catch (NotFoundException e) {}
/* 1003: 923 */     return null;
/* 1004:     */   }
/* 1005:     */   
/* 1006:     */   public CtField[] getDeclaredFields()
/* 1007:     */   {
/* 1008: 927 */     CtMember.Cache memCache = getMembers();
/* 1009: 928 */     CtMember field = memCache.fieldHead();
/* 1010: 929 */     CtMember tail = memCache.lastField();
/* 1011: 930 */     int num = CtMember.Cache.count(field, tail);
/* 1012: 931 */     CtField[] cfs = new CtField[num];
/* 1013: 932 */     int i = 0;
/* 1014: 933 */     while (field != tail)
/* 1015:     */     {
/* 1016: 934 */       field = field.next();
/* 1017: 935 */       cfs[(i++)] = ((CtField)field);
/* 1018:     */     }
/* 1019: 938 */     return cfs;
/* 1020:     */   }
/* 1021:     */   
/* 1022:     */   public CtField getDeclaredField(String name)
/* 1023:     */     throws NotFoundException
/* 1024:     */   {
/* 1025: 942 */     CtField f = getDeclaredField2(name);
/* 1026: 943 */     if (f == null) {
/* 1027: 944 */       throw new NotFoundException("field: " + name + " in " + getName());
/* 1028:     */     }
/* 1029: 946 */     return f;
/* 1030:     */   }
/* 1031:     */   
/* 1032:     */   private CtField getDeclaredField2(String name)
/* 1033:     */   {
/* 1034: 950 */     CtMember.Cache memCache = getMembers();
/* 1035: 951 */     CtMember field = memCache.fieldHead();
/* 1036: 952 */     CtMember tail = memCache.lastField();
/* 1037: 953 */     while (field != tail)
/* 1038:     */     {
/* 1039: 954 */       field = field.next();
/* 1040: 955 */       if (field.getName().equals(name)) {
/* 1041: 956 */         return (CtField)field;
/* 1042:     */       }
/* 1043:     */     }
/* 1044: 959 */     return null;
/* 1045:     */   }
/* 1046:     */   
/* 1047:     */   public CtBehavior[] getDeclaredBehaviors()
/* 1048:     */   {
/* 1049: 963 */     CtMember.Cache memCache = getMembers();
/* 1050: 964 */     CtMember cons = memCache.consHead();
/* 1051: 965 */     CtMember consTail = memCache.lastCons();
/* 1052: 966 */     int cnum = CtMember.Cache.count(cons, consTail);
/* 1053: 967 */     CtMember mth = memCache.methodHead();
/* 1054: 968 */     CtMember mthTail = memCache.lastMethod();
/* 1055: 969 */     int mnum = CtMember.Cache.count(mth, mthTail);
/* 1056:     */     
/* 1057: 971 */     CtBehavior[] cb = new CtBehavior[cnum + mnum];
/* 1058: 972 */     int i = 0;
/* 1059: 973 */     while (cons != consTail)
/* 1060:     */     {
/* 1061: 974 */       cons = cons.next();
/* 1062: 975 */       cb[(i++)] = ((CtBehavior)cons);
/* 1063:     */     }
/* 1064: 978 */     while (mth != mthTail)
/* 1065:     */     {
/* 1066: 979 */       mth = mth.next();
/* 1067: 980 */       cb[(i++)] = ((CtBehavior)mth);
/* 1068:     */     }
/* 1069: 983 */     return cb;
/* 1070:     */   }
/* 1071:     */   
/* 1072:     */   public CtConstructor[] getConstructors()
/* 1073:     */   {
/* 1074: 987 */     CtMember.Cache memCache = getMembers();
/* 1075: 988 */     CtMember cons = memCache.consHead();
/* 1076: 989 */     CtMember consTail = memCache.lastCons();
/* 1077:     */     
/* 1078: 991 */     int n = 0;
/* 1079: 992 */     CtMember mem = cons;
/* 1080: 993 */     while (mem != consTail)
/* 1081:     */     {
/* 1082: 994 */       mem = mem.next();
/* 1083: 995 */       if (isPubCons((CtConstructor)mem)) {
/* 1084: 996 */         n++;
/* 1085:     */       }
/* 1086:     */     }
/* 1087: 999 */     CtConstructor[] result = new CtConstructor[n];
/* 1088:1000 */     int i = 0;
/* 1089:1001 */     mem = cons;
/* 1090:1002 */     while (mem != consTail)
/* 1091:     */     {
/* 1092:1003 */       mem = mem.next();
/* 1093:1004 */       CtConstructor cc = (CtConstructor)mem;
/* 1094:1005 */       if (isPubCons(cc)) {
/* 1095:1006 */         result[(i++)] = cc;
/* 1096:     */       }
/* 1097:     */     }
/* 1098:1009 */     return result;
/* 1099:     */   }
/* 1100:     */   
/* 1101:     */   private static boolean isPubCons(CtConstructor cons)
/* 1102:     */   {
/* 1103:1013 */     return (!Modifier.isPrivate(cons.getModifiers())) && (cons.isConstructor());
/* 1104:     */   }
/* 1105:     */   
/* 1106:     */   public CtConstructor getConstructor(String desc)
/* 1107:     */     throws NotFoundException
/* 1108:     */   {
/* 1109:1020 */     CtMember.Cache memCache = getMembers();
/* 1110:1021 */     CtMember cons = memCache.consHead();
/* 1111:1022 */     CtMember consTail = memCache.lastCons();
/* 1112:1024 */     while (cons != consTail)
/* 1113:     */     {
/* 1114:1025 */       cons = cons.next();
/* 1115:1026 */       CtConstructor cc = (CtConstructor)cons;
/* 1116:1027 */       if ((cc.getMethodInfo2().getDescriptor().equals(desc)) && (cc.isConstructor())) {
/* 1117:1029 */         return cc;
/* 1118:     */       }
/* 1119:     */     }
/* 1120:1032 */     return super.getConstructor(desc);
/* 1121:     */   }
/* 1122:     */   
/* 1123:     */   public CtConstructor[] getDeclaredConstructors()
/* 1124:     */   {
/* 1125:1036 */     CtMember.Cache memCache = getMembers();
/* 1126:1037 */     CtMember cons = memCache.consHead();
/* 1127:1038 */     CtMember consTail = memCache.lastCons();
/* 1128:     */     
/* 1129:1040 */     int n = 0;
/* 1130:1041 */     CtMember mem = cons;
/* 1131:1042 */     while (mem != consTail)
/* 1132:     */     {
/* 1133:1043 */       mem = mem.next();
/* 1134:1044 */       CtConstructor cc = (CtConstructor)mem;
/* 1135:1045 */       if (cc.isConstructor()) {
/* 1136:1046 */         n++;
/* 1137:     */       }
/* 1138:     */     }
/* 1139:1049 */     CtConstructor[] result = new CtConstructor[n];
/* 1140:1050 */     int i = 0;
/* 1141:1051 */     mem = cons;
/* 1142:1052 */     while (mem != consTail)
/* 1143:     */     {
/* 1144:1053 */       mem = mem.next();
/* 1145:1054 */       CtConstructor cc = (CtConstructor)mem;
/* 1146:1055 */       if (cc.isConstructor()) {
/* 1147:1056 */         result[(i++)] = cc;
/* 1148:     */       }
/* 1149:     */     }
/* 1150:1059 */     return result;
/* 1151:     */   }
/* 1152:     */   
/* 1153:     */   public CtConstructor getClassInitializer()
/* 1154:     */   {
/* 1155:1063 */     CtMember.Cache memCache = getMembers();
/* 1156:1064 */     CtMember cons = memCache.consHead();
/* 1157:1065 */     CtMember consTail = memCache.lastCons();
/* 1158:1067 */     while (cons != consTail)
/* 1159:     */     {
/* 1160:1068 */       cons = cons.next();
/* 1161:1069 */       CtConstructor cc = (CtConstructor)cons;
/* 1162:1070 */       if (cc.isClassInitializer()) {
/* 1163:1071 */         return cc;
/* 1164:     */       }
/* 1165:     */     }
/* 1166:1074 */     return null;
/* 1167:     */   }
/* 1168:     */   
/* 1169:     */   public CtMethod[] getMethods()
/* 1170:     */   {
/* 1171:1078 */     HashMap h = new HashMap();
/* 1172:1079 */     getMethods0(h, this);
/* 1173:1080 */     return (CtMethod[])h.values().toArray(new CtMethod[h.size()]);
/* 1174:     */   }
/* 1175:     */   
/* 1176:     */   private static void getMethods0(HashMap h, CtClass cc)
/* 1177:     */   {
/* 1178:     */     try
/* 1179:     */     {
/* 1180:1085 */       CtClass[] ifs = cc.getInterfaces();
/* 1181:1086 */       int size = ifs.length;
/* 1182:1087 */       for (int i = 0; i < size; i++) {
/* 1183:1088 */         getMethods0(h, ifs[i]);
/* 1184:     */       }
/* 1185:     */     }
/* 1186:     */     catch (NotFoundException e) {}
/* 1187:     */     try
/* 1188:     */     {
/* 1189:1093 */       CtClass s = cc.getSuperclass();
/* 1190:1094 */       if (s != null) {
/* 1191:1095 */         getMethods0(h, s);
/* 1192:     */       }
/* 1193:     */     }
/* 1194:     */     catch (NotFoundException e) {}
/* 1195:1099 */     if ((cc instanceof CtClassType))
/* 1196:     */     {
/* 1197:1100 */       CtMember.Cache memCache = ((CtClassType)cc).getMembers();
/* 1198:1101 */       CtMember mth = memCache.methodHead();
/* 1199:1102 */       CtMember mthTail = memCache.lastMethod();
/* 1200:1104 */       while (mth != mthTail)
/* 1201:     */       {
/* 1202:1105 */         mth = mth.next();
/* 1203:1106 */         if (!Modifier.isPrivate(mth.getModifiers())) {
/* 1204:1107 */           h.put(((CtMethod)mth).getStringRep(), mth);
/* 1205:     */         }
/* 1206:     */       }
/* 1207:     */     }
/* 1208:     */   }
/* 1209:     */   
/* 1210:     */   public CtMethod getMethod(String name, String desc)
/* 1211:     */     throws NotFoundException
/* 1212:     */   {
/* 1213:1115 */     CtMethod m = getMethod0(this, name, desc);
/* 1214:1116 */     if (m != null) {
/* 1215:1117 */       return m;
/* 1216:     */     }
/* 1217:1119 */     throw new NotFoundException(name + "(..) is not found in " + getName());
/* 1218:     */   }
/* 1219:     */   
/* 1220:     */   private static CtMethod getMethod0(CtClass cc, String name, String desc)
/* 1221:     */   {
/* 1222:1125 */     if ((cc instanceof CtClassType))
/* 1223:     */     {
/* 1224:1126 */       CtMember.Cache memCache = ((CtClassType)cc).getMembers();
/* 1225:1127 */       CtMember mth = memCache.methodHead();
/* 1226:1128 */       CtMember mthTail = memCache.lastMethod();
/* 1227:1130 */       while (mth != mthTail)
/* 1228:     */       {
/* 1229:1131 */         mth = mth.next();
/* 1230:1132 */         if ((mth.getName().equals(name)) && (((CtMethod)mth).getMethodInfo2().getDescriptor().equals(desc))) {
/* 1231:1134 */           return (CtMethod)mth;
/* 1232:     */         }
/* 1233:     */       }
/* 1234:     */     }
/* 1235:     */     try
/* 1236:     */     {
/* 1237:1139 */       CtClass s = cc.getSuperclass();
/* 1238:1140 */       if (s != null)
/* 1239:     */       {
/* 1240:1141 */         CtMethod m = getMethod0(s, name, desc);
/* 1241:1142 */         if (m != null) {
/* 1242:1143 */           return m;
/* 1243:     */         }
/* 1244:     */       }
/* 1245:     */     }
/* 1246:     */     catch (NotFoundException e) {}
/* 1247:     */     try
/* 1248:     */     {
/* 1249:1149 */       CtClass[] ifs = cc.getInterfaces();
/* 1250:1150 */       int size = ifs.length;
/* 1251:1151 */       for (int i = 0; i < size; i++)
/* 1252:     */       {
/* 1253:1152 */         CtMethod m = getMethod0(ifs[i], name, desc);
/* 1254:1153 */         if (m != null) {
/* 1255:1154 */           return m;
/* 1256:     */         }
/* 1257:     */       }
/* 1258:     */     }
/* 1259:     */     catch (NotFoundException e) {}
/* 1260:1158 */     return null;
/* 1261:     */   }
/* 1262:     */   
/* 1263:     */   public CtMethod[] getDeclaredMethods()
/* 1264:     */   {
/* 1265:1162 */     CtMember.Cache memCache = getMembers();
/* 1266:1163 */     CtMember mth = memCache.methodHead();
/* 1267:1164 */     CtMember mthTail = memCache.lastMethod();
/* 1268:1165 */     int num = CtMember.Cache.count(mth, mthTail);
/* 1269:1166 */     CtMethod[] cms = new CtMethod[num];
/* 1270:1167 */     int i = 0;
/* 1271:1168 */     while (mth != mthTail)
/* 1272:     */     {
/* 1273:1169 */       mth = mth.next();
/* 1274:1170 */       cms[(i++)] = ((CtMethod)mth);
/* 1275:     */     }
/* 1276:1173 */     return cms;
/* 1277:     */   }
/* 1278:     */   
/* 1279:     */   public CtMethod getDeclaredMethod(String name)
/* 1280:     */     throws NotFoundException
/* 1281:     */   {
/* 1282:1177 */     CtMember.Cache memCache = getMembers();
/* 1283:1178 */     CtMember mth = memCache.methodHead();
/* 1284:1179 */     CtMember mthTail = memCache.lastMethod();
/* 1285:1180 */     while (mth != mthTail)
/* 1286:     */     {
/* 1287:1181 */       mth = mth.next();
/* 1288:1182 */       if (mth.getName().equals(name)) {
/* 1289:1183 */         return (CtMethod)mth;
/* 1290:     */       }
/* 1291:     */     }
/* 1292:1186 */     throw new NotFoundException(name + "(..) is not found in " + getName());
/* 1293:     */   }
/* 1294:     */   
/* 1295:     */   public CtMethod getDeclaredMethod(String name, CtClass[] params)
/* 1296:     */     throws NotFoundException
/* 1297:     */   {
/* 1298:1193 */     String desc = Descriptor.ofParameters(params);
/* 1299:1194 */     CtMember.Cache memCache = getMembers();
/* 1300:1195 */     CtMember mth = memCache.methodHead();
/* 1301:1196 */     CtMember mthTail = memCache.lastMethod();
/* 1302:1198 */     while (mth != mthTail)
/* 1303:     */     {
/* 1304:1199 */       mth = mth.next();
/* 1305:1200 */       if ((mth.getName().equals(name)) && (((CtMethod)mth).getMethodInfo2().getDescriptor().startsWith(desc))) {
/* 1306:1202 */         return (CtMethod)mth;
/* 1307:     */       }
/* 1308:     */     }
/* 1309:1205 */     throw new NotFoundException(name + "(..) is not found in " + getName());
/* 1310:     */   }
/* 1311:     */   
/* 1312:     */   public void addField(CtField f, String init)
/* 1313:     */     throws CannotCompileException
/* 1314:     */   {
/* 1315:1212 */     addField(f, CtField.Initializer.byExpr(init));
/* 1316:     */   }
/* 1317:     */   
/* 1318:     */   public void addField(CtField f, CtField.Initializer init)
/* 1319:     */     throws CannotCompileException
/* 1320:     */   {
/* 1321:1218 */     checkModify();
/* 1322:1219 */     if (f.getDeclaringClass() != this) {
/* 1323:1220 */       throw new CannotCompileException("cannot add");
/* 1324:     */     }
/* 1325:1222 */     if (init == null) {
/* 1326:1223 */       init = f.getInit();
/* 1327:     */     }
/* 1328:1225 */     if (init != null)
/* 1329:     */     {
/* 1330:1226 */       init.check(f.getSignature());
/* 1331:1227 */       int mod = f.getModifiers();
/* 1332:1228 */       if ((Modifier.isStatic(mod)) && (Modifier.isFinal(mod))) {
/* 1333:     */         try
/* 1334:     */         {
/* 1335:1230 */           ConstPool cp = getClassFile2().getConstPool();
/* 1336:1231 */           int index = init.getConstantValue(cp, f.getType());
/* 1337:1232 */           if (index != 0)
/* 1338:     */           {
/* 1339:1233 */             f.getFieldInfo2().addAttribute(new ConstantAttribute(cp, index));
/* 1340:1234 */             init = null;
/* 1341:     */           }
/* 1342:     */         }
/* 1343:     */         catch (NotFoundException e) {}
/* 1344:     */       }
/* 1345:     */     }
/* 1346:1240 */     getMembers().addField(f);
/* 1347:1241 */     getClassFile2().addField(f.getFieldInfo2());
/* 1348:1243 */     if (init != null)
/* 1349:     */     {
/* 1350:1244 */       FieldInitLink fil = new FieldInitLink(f, init);
/* 1351:1245 */       FieldInitLink link = this.fieldInitializers;
/* 1352:1246 */       if (link == null)
/* 1353:     */       {
/* 1354:1247 */         this.fieldInitializers = fil;
/* 1355:     */       }
/* 1356:     */       else
/* 1357:     */       {
/* 1358:1249 */         while (link.next != null) {
/* 1359:1250 */           link = link.next;
/* 1360:     */         }
/* 1361:1252 */         link.next = fil;
/* 1362:     */       }
/* 1363:     */     }
/* 1364:     */   }
/* 1365:     */   
/* 1366:     */   public void removeField(CtField f)
/* 1367:     */     throws NotFoundException
/* 1368:     */   {
/* 1369:1258 */     checkModify();
/* 1370:1259 */     FieldInfo fi = f.getFieldInfo2();
/* 1371:1260 */     ClassFile cf = getClassFile2();
/* 1372:1261 */     if (cf.getFields().remove(fi))
/* 1373:     */     {
/* 1374:1262 */       getMembers().remove(f);
/* 1375:1263 */       this.gcConstPool = true;
/* 1376:     */     }
/* 1377:     */     else
/* 1378:     */     {
/* 1379:1266 */       throw new NotFoundException(f.toString());
/* 1380:     */     }
/* 1381:     */   }
/* 1382:     */   
/* 1383:     */   public CtConstructor makeClassInitializer()
/* 1384:     */     throws CannotCompileException
/* 1385:     */   {
/* 1386:1272 */     CtConstructor clinit = getClassInitializer();
/* 1387:1273 */     if (clinit != null) {
/* 1388:1274 */       return clinit;
/* 1389:     */     }
/* 1390:1276 */     checkModify();
/* 1391:1277 */     ClassFile cf = getClassFile2();
/* 1392:1278 */     Bytecode code = new Bytecode(cf.getConstPool(), 0, 0);
/* 1393:1279 */     modifyClassConstructor(cf, code, 0, 0);
/* 1394:1280 */     return getClassInitializer();
/* 1395:     */   }
/* 1396:     */   
/* 1397:     */   public void addConstructor(CtConstructor c)
/* 1398:     */     throws CannotCompileException
/* 1399:     */   {
/* 1400:1286 */     checkModify();
/* 1401:1287 */     if (c.getDeclaringClass() != this) {
/* 1402:1288 */       throw new CannotCompileException("cannot add");
/* 1403:     */     }
/* 1404:1290 */     getMembers().addConstructor(c);
/* 1405:1291 */     getClassFile2().addMethod(c.getMethodInfo2());
/* 1406:     */   }
/* 1407:     */   
/* 1408:     */   public void removeConstructor(CtConstructor m)
/* 1409:     */     throws NotFoundException
/* 1410:     */   {
/* 1411:1295 */     checkModify();
/* 1412:1296 */     MethodInfo mi = m.getMethodInfo2();
/* 1413:1297 */     ClassFile cf = getClassFile2();
/* 1414:1298 */     if (cf.getMethods().remove(mi))
/* 1415:     */     {
/* 1416:1299 */       getMembers().remove(m);
/* 1417:1300 */       this.gcConstPool = true;
/* 1418:     */     }
/* 1419:     */     else
/* 1420:     */     {
/* 1421:1303 */       throw new NotFoundException(m.toString());
/* 1422:     */     }
/* 1423:     */   }
/* 1424:     */   
/* 1425:     */   public void addMethod(CtMethod m)
/* 1426:     */     throws CannotCompileException
/* 1427:     */   {
/* 1428:1307 */     checkModify();
/* 1429:1308 */     if (m.getDeclaringClass() != this) {
/* 1430:1309 */       throw new CannotCompileException("bad declaring class");
/* 1431:     */     }
/* 1432:1311 */     int mod = m.getModifiers();
/* 1433:1312 */     if ((getModifiers() & 0x200) != 0)
/* 1434:     */     {
/* 1435:1313 */       m.setModifiers(mod | 0x1);
/* 1436:1314 */       if ((mod & 0x400) == 0) {
/* 1437:1315 */         throw new CannotCompileException("an interface method must be abstract: " + m.toString());
/* 1438:     */       }
/* 1439:     */     }
/* 1440:1319 */     getMembers().addMethod(m);
/* 1441:1320 */     getClassFile2().addMethod(m.getMethodInfo2());
/* 1442:1321 */     if ((mod & 0x400) != 0) {
/* 1443:1322 */       setModifiers(getModifiers() | 0x400);
/* 1444:     */     }
/* 1445:     */   }
/* 1446:     */   
/* 1447:     */   public void removeMethod(CtMethod m)
/* 1448:     */     throws NotFoundException
/* 1449:     */   {
/* 1450:1326 */     checkModify();
/* 1451:1327 */     MethodInfo mi = m.getMethodInfo2();
/* 1452:1328 */     ClassFile cf = getClassFile2();
/* 1453:1329 */     if (cf.getMethods().remove(mi))
/* 1454:     */     {
/* 1455:1330 */       getMembers().remove(m);
/* 1456:1331 */       this.gcConstPool = true;
/* 1457:     */     }
/* 1458:     */     else
/* 1459:     */     {
/* 1460:1334 */       throw new NotFoundException(m.toString());
/* 1461:     */     }
/* 1462:     */   }
/* 1463:     */   
/* 1464:     */   public byte[] getAttribute(String name)
/* 1465:     */   {
/* 1466:1338 */     AttributeInfo ai = getClassFile2().getAttribute(name);
/* 1467:1339 */     if (ai == null) {
/* 1468:1340 */       return null;
/* 1469:     */     }
/* 1470:1342 */     return ai.get();
/* 1471:     */   }
/* 1472:     */   
/* 1473:     */   public void setAttribute(String name, byte[] data)
/* 1474:     */   {
/* 1475:1346 */     checkModify();
/* 1476:1347 */     ClassFile cf = getClassFile2();
/* 1477:1348 */     cf.addAttribute(new AttributeInfo(cf.getConstPool(), name, data));
/* 1478:     */   }
/* 1479:     */   
/* 1480:     */   public void instrument(CodeConverter converter)
/* 1481:     */     throws CannotCompileException
/* 1482:     */   {
/* 1483:1354 */     checkModify();
/* 1484:1355 */     ClassFile cf = getClassFile2();
/* 1485:1356 */     ConstPool cp = cf.getConstPool();
/* 1486:1357 */     List list = cf.getMethods();
/* 1487:1358 */     int n = list.size();
/* 1488:1359 */     for (int i = 0; i < n; i++)
/* 1489:     */     {
/* 1490:1360 */       MethodInfo minfo = (MethodInfo)list.get(i);
/* 1491:1361 */       converter.doit(this, minfo, cp);
/* 1492:     */     }
/* 1493:     */   }
/* 1494:     */   
/* 1495:     */   public void instrument(ExprEditor editor)
/* 1496:     */     throws CannotCompileException
/* 1497:     */   {
/* 1498:1368 */     checkModify();
/* 1499:1369 */     ClassFile cf = getClassFile2();
/* 1500:1370 */     List list = cf.getMethods();
/* 1501:1371 */     int n = list.size();
/* 1502:1372 */     for (int i = 0; i < n; i++)
/* 1503:     */     {
/* 1504:1373 */       MethodInfo minfo = (MethodInfo)list.get(i);
/* 1505:1374 */       editor.doit(this, minfo);
/* 1506:     */     }
/* 1507:     */   }
/* 1508:     */   
/* 1509:     */   public void prune()
/* 1510:     */   {
/* 1511:1383 */     if (this.wasPruned) {
/* 1512:1384 */       return;
/* 1513:     */     }
/* 1514:1386 */     this.wasPruned = (this.wasFrozen = 1);
/* 1515:1387 */     getClassFile2().prune();
/* 1516:     */   }
/* 1517:     */   
/* 1518:     */   public void rebuildClassFile()
/* 1519:     */   {
/* 1520:1390 */     this.gcConstPool = true;
/* 1521:     */   }
/* 1522:     */   
/* 1523:     */   public void toBytecode(DataOutputStream out)
/* 1524:     */     throws CannotCompileException, IOException
/* 1525:     */   {
/* 1526:     */     try
/* 1527:     */     {
/* 1528:1396 */       if (isModified())
/* 1529:     */       {
/* 1530:1397 */         checkPruned("toBytecode");
/* 1531:1398 */         ClassFile cf = getClassFile2();
/* 1532:1399 */         if (this.gcConstPool)
/* 1533:     */         {
/* 1534:1400 */           cf.compact();
/* 1535:1401 */           this.gcConstPool = false;
/* 1536:     */         }
/* 1537:1404 */         modifyClassConstructor(cf);
/* 1538:1405 */         modifyConstructors(cf);
/* 1539:1406 */         cf.write(out);
/* 1540:1407 */         out.flush();
/* 1541:1408 */         this.fieldInitializers = null;
/* 1542:1409 */         if (this.doPruning)
/* 1543:     */         {
/* 1544:1411 */           cf.prune();
/* 1545:1412 */           this.wasPruned = true;
/* 1546:     */         }
/* 1547:     */       }
/* 1548:     */       else
/* 1549:     */       {
/* 1550:1416 */         this.classPool.writeClassfile(getName(), out);
/* 1551:     */       }
/* 1552:1421 */       this.getCount = 0;
/* 1553:1422 */       this.wasFrozen = true;
/* 1554:     */     }
/* 1555:     */     catch (NotFoundException e)
/* 1556:     */     {
/* 1557:1425 */       throw new CannotCompileException(e);
/* 1558:     */     }
/* 1559:     */     catch (IOException e)
/* 1560:     */     {
/* 1561:1428 */       throw new CannotCompileException(e);
/* 1562:     */     }
/* 1563:     */   }
/* 1564:     */   
/* 1565:     */   private void checkPruned(String method)
/* 1566:     */   {
/* 1567:1435 */     if (this.wasPruned) {
/* 1568:1436 */       throw new RuntimeException(method + "(): " + getName() + " was pruned.");
/* 1569:     */     }
/* 1570:     */   }
/* 1571:     */   
/* 1572:     */   public boolean stopPruning(boolean stop)
/* 1573:     */   {
/* 1574:1441 */     boolean prev = !this.doPruning;
/* 1575:1442 */     this.doPruning = (!stop);
/* 1576:1443 */     return prev;
/* 1577:     */   }
/* 1578:     */   
/* 1579:     */   private void modifyClassConstructor(ClassFile cf)
/* 1580:     */     throws CannotCompileException, NotFoundException
/* 1581:     */   {
/* 1582:1449 */     if (this.fieldInitializers == null) {
/* 1583:1450 */       return;
/* 1584:     */     }
/* 1585:1452 */     Bytecode code = new Bytecode(cf.getConstPool(), 0, 0);
/* 1586:1453 */     Javac jv = new Javac(code, this);
/* 1587:1454 */     int stacksize = 0;
/* 1588:1455 */     boolean doInit = false;
/* 1589:1456 */     for (FieldInitLink fi = this.fieldInitializers; fi != null; fi = fi.next)
/* 1590:     */     {
/* 1591:1457 */       CtField f = fi.field;
/* 1592:1458 */       if (Modifier.isStatic(f.getModifiers()))
/* 1593:     */       {
/* 1594:1459 */         doInit = true;
/* 1595:1460 */         int s = fi.init.compileIfStatic(f.getType(), f.getName(), code, jv);
/* 1596:1462 */         if (stacksize < s) {
/* 1597:1463 */           stacksize = s;
/* 1598:     */         }
/* 1599:     */       }
/* 1600:     */     }
/* 1601:1467 */     if (doInit) {
/* 1602:1468 */       modifyClassConstructor(cf, code, stacksize, 0);
/* 1603:     */     }
/* 1604:     */   }
/* 1605:     */   
/* 1606:     */   private void modifyClassConstructor(ClassFile cf, Bytecode code, int stacksize, int localsize)
/* 1607:     */     throws CannotCompileException
/* 1608:     */   {
/* 1609:1475 */     MethodInfo m = cf.getStaticInitializer();
/* 1610:1476 */     if (m == null)
/* 1611:     */     {
/* 1612:1477 */       code.add(177);
/* 1613:1478 */       code.setMaxStack(stacksize);
/* 1614:1479 */       code.setMaxLocals(localsize);
/* 1615:1480 */       m = new MethodInfo(cf.getConstPool(), "<clinit>", "()V");
/* 1616:1481 */       m.setAccessFlags(8);
/* 1617:1482 */       m.setCodeAttribute(code.toCodeAttribute());
/* 1618:1483 */       cf.addMethod(m);
/* 1619:1484 */       CtMember.Cache cache = hasMemberCache();
/* 1620:1485 */       if (cache != null) {
/* 1621:1486 */         cache.addConstructor(new CtConstructor(m, this));
/* 1622:     */       }
/* 1623:     */     }
/* 1624:     */     else
/* 1625:     */     {
/* 1626:1489 */       CodeAttribute codeAttr = m.getCodeAttribute();
/* 1627:1490 */       if (codeAttr == null) {
/* 1628:1491 */         throw new CannotCompileException("empty <clinit>");
/* 1629:     */       }
/* 1630:     */       try
/* 1631:     */       {
/* 1632:1494 */         CodeIterator it = codeAttr.iterator();
/* 1633:1495 */         int pos = it.insertEx(code.get());
/* 1634:1496 */         it.insert(code.getExceptionTable(), pos);
/* 1635:1497 */         int maxstack = codeAttr.getMaxStack();
/* 1636:1498 */         if (maxstack < stacksize) {
/* 1637:1499 */           codeAttr.setMaxStack(stacksize);
/* 1638:     */         }
/* 1639:1501 */         int maxlocals = codeAttr.getMaxLocals();
/* 1640:1502 */         if (maxlocals < localsize) {
/* 1641:1503 */           codeAttr.setMaxLocals(localsize);
/* 1642:     */         }
/* 1643:     */       }
/* 1644:     */       catch (BadBytecode e)
/* 1645:     */       {
/* 1646:1506 */         throw new CannotCompileException(e);
/* 1647:     */       }
/* 1648:     */     }
/* 1649:     */     try
/* 1650:     */     {
/* 1651:1511 */       m.rebuildStackMapIf6(this.classPool, cf);
/* 1652:     */     }
/* 1653:     */     catch (BadBytecode e)
/* 1654:     */     {
/* 1655:1514 */       throw new CannotCompileException(e);
/* 1656:     */     }
/* 1657:     */   }
/* 1658:     */   
/* 1659:     */   private void modifyConstructors(ClassFile cf)
/* 1660:     */     throws CannotCompileException, NotFoundException
/* 1661:     */   {
/* 1662:1521 */     if (this.fieldInitializers == null) {
/* 1663:1522 */       return;
/* 1664:     */     }
/* 1665:1524 */     ConstPool cp = cf.getConstPool();
/* 1666:1525 */     List list = cf.getMethods();
/* 1667:1526 */     int n = list.size();
/* 1668:1527 */     for (int i = 0; i < n; i++)
/* 1669:     */     {
/* 1670:1528 */       MethodInfo minfo = (MethodInfo)list.get(i);
/* 1671:1529 */       if (minfo.isConstructor())
/* 1672:     */       {
/* 1673:1530 */         CodeAttribute codeAttr = minfo.getCodeAttribute();
/* 1674:1531 */         if (codeAttr != null) {
/* 1675:     */           try
/* 1676:     */           {
/* 1677:1533 */             Bytecode init = new Bytecode(cp, 0, codeAttr.getMaxLocals());
/* 1678:     */             
/* 1679:1535 */             CtClass[] params = Descriptor.getParameterTypes(minfo.getDescriptor(), this.classPool);
/* 1680:     */             
/* 1681:     */ 
/* 1682:     */ 
/* 1683:1539 */             int stacksize = makeFieldInitializer(init, params);
/* 1684:1540 */             insertAuxInitializer(codeAttr, init, stacksize);
/* 1685:1541 */             minfo.rebuildStackMapIf6(this.classPool, cf);
/* 1686:     */           }
/* 1687:     */           catch (BadBytecode e)
/* 1688:     */           {
/* 1689:1544 */             throw new CannotCompileException(e);
/* 1690:     */           }
/* 1691:     */         }
/* 1692:     */       }
/* 1693:     */     }
/* 1694:     */   }
/* 1695:     */   
/* 1696:     */   private static void insertAuxInitializer(CodeAttribute codeAttr, Bytecode initializer, int stacksize)
/* 1697:     */     throws BadBytecode
/* 1698:     */   {
/* 1699:1555 */     CodeIterator it = codeAttr.iterator();
/* 1700:1556 */     int index = it.skipSuperConstructor();
/* 1701:1557 */     if (index < 0)
/* 1702:     */     {
/* 1703:1558 */       index = it.skipThisConstructor();
/* 1704:1559 */       if (index >= 0) {
/* 1705:1560 */         return;
/* 1706:     */       }
/* 1707:     */     }
/* 1708:1565 */     int pos = it.insertEx(initializer.get());
/* 1709:1566 */     it.insert(initializer.getExceptionTable(), pos);
/* 1710:1567 */     int maxstack = codeAttr.getMaxStack();
/* 1711:1568 */     if (maxstack < stacksize) {
/* 1712:1569 */       codeAttr.setMaxStack(stacksize);
/* 1713:     */     }
/* 1714:     */   }
/* 1715:     */   
/* 1716:     */   private int makeFieldInitializer(Bytecode code, CtClass[] parameters)
/* 1717:     */     throws CannotCompileException, NotFoundException
/* 1718:     */   {
/* 1719:1575 */     int stacksize = 0;
/* 1720:1576 */     Javac jv = new Javac(code, this);
/* 1721:     */     try
/* 1722:     */     {
/* 1723:1578 */       jv.recordParams(parameters, false);
/* 1724:     */     }
/* 1725:     */     catch (CompileError e)
/* 1726:     */     {
/* 1727:1581 */       throw new CannotCompileException(e);
/* 1728:     */     }
/* 1729:1584 */     for (FieldInitLink fi = this.fieldInitializers; fi != null; fi = fi.next)
/* 1730:     */     {
/* 1731:1585 */       CtField f = fi.field;
/* 1732:1586 */       if (!Modifier.isStatic(f.getModifiers()))
/* 1733:     */       {
/* 1734:1587 */         int s = fi.init.compile(f.getType(), f.getName(), code, parameters, jv);
/* 1735:1589 */         if (stacksize < s) {
/* 1736:1590 */           stacksize = s;
/* 1737:     */         }
/* 1738:     */       }
/* 1739:     */     }
/* 1740:1594 */     return stacksize;
/* 1741:     */   }
/* 1742:     */   
/* 1743:     */   Hashtable getHiddenMethods()
/* 1744:     */   {
/* 1745:1600 */     if (this.hiddenMethods == null) {
/* 1746:1601 */       this.hiddenMethods = new Hashtable();
/* 1747:     */     }
/* 1748:1603 */     return this.hiddenMethods;
/* 1749:     */   }
/* 1750:     */   
/* 1751:     */   int getUniqueNumber()
/* 1752:     */   {
/* 1753:1606 */     return this.uniqueNumberSeed++;
/* 1754:     */   }
/* 1755:     */   
/* 1756:     */   public String makeUniqueName(String prefix)
/* 1757:     */   {
/* 1758:1609 */     HashMap table = new HashMap();
/* 1759:1610 */     makeMemberList(table);
/* 1760:1611 */     Set keys = table.keySet();
/* 1761:1612 */     String[] methods = new String[keys.size()];
/* 1762:1613 */     keys.toArray(methods);
/* 1763:1615 */     if (notFindInArray(prefix, methods)) {
/* 1764:1616 */       return prefix;
/* 1765:     */     }
/* 1766:1618 */     int i = 100;
/* 1767:     */     String name;
/* 1768:     */     do
/* 1769:     */     {
/* 1770:1621 */       if (i > 999) {
/* 1771:1622 */         throw new RuntimeException("too many unique name");
/* 1772:     */       }
/* 1773:1624 */       name = prefix + i++;
/* 1774:1625 */     } while (!notFindInArray(name, methods));
/* 1775:1626 */     return name;
/* 1776:     */   }
/* 1777:     */   
/* 1778:     */   private static boolean notFindInArray(String prefix, String[] values)
/* 1779:     */   {
/* 1780:1630 */     int len = values.length;
/* 1781:1631 */     for (int i = 0; i < len; i++) {
/* 1782:1632 */       if (values[i].startsWith(prefix)) {
/* 1783:1633 */         return false;
/* 1784:     */       }
/* 1785:     */     }
/* 1786:1635 */     return true;
/* 1787:     */   }
/* 1788:     */   
/* 1789:     */   private void makeMemberList(HashMap table)
/* 1790:     */   {
/* 1791:1639 */     int mod = getModifiers();
/* 1792:1640 */     if ((Modifier.isAbstract(mod)) || (Modifier.isInterface(mod))) {
/* 1793:     */       try
/* 1794:     */       {
/* 1795:1642 */         CtClass[] ifs = getInterfaces();
/* 1796:1643 */         int size = ifs.length;
/* 1797:1644 */         for (int i = 0; i < size; i++)
/* 1798:     */         {
/* 1799:1645 */           CtClass ic = ifs[i];
/* 1800:1646 */           if ((ic != null) && ((ic instanceof CtClassType))) {
/* 1801:1647 */             ((CtClassType)ic).makeMemberList(table);
/* 1802:     */           }
/* 1803:     */         }
/* 1804:     */       }
/* 1805:     */       catch (NotFoundException e) {}
/* 1806:     */     }
/* 1807:     */     try
/* 1808:     */     {
/* 1809:1653 */       CtClass s = getSuperclass();
/* 1810:1654 */       if ((s != null) && ((s instanceof CtClassType))) {
/* 1811:1655 */         ((CtClassType)s).makeMemberList(table);
/* 1812:     */       }
/* 1813:     */     }
/* 1814:     */     catch (NotFoundException e) {}
/* 1815:1659 */     List list = getClassFile2().getMethods();
/* 1816:1660 */     int n = list.size();
/* 1817:1661 */     for (int i = 0; i < n; i++)
/* 1818:     */     {
/* 1819:1662 */       MethodInfo minfo = (MethodInfo)list.get(i);
/* 1820:1663 */       table.put(minfo.getName(), this);
/* 1821:     */     }
/* 1822:1666 */     list = getClassFile2().getFields();
/* 1823:1667 */     n = list.size();
/* 1824:1668 */     for (int i = 0; i < n; i++)
/* 1825:     */     {
/* 1826:1669 */       FieldInfo finfo = (FieldInfo)list.get(i);
/* 1827:1670 */       table.put(finfo.getName(), this);
/* 1828:     */     }
/* 1829:     */   }
/* 1830:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.CtClassType
 * JD-Core Version:    0.7.0.1
 */