/*    1:     */ package javassist;
/*    2:     */ 
/*    3:     */ import java.io.BufferedInputStream;
/*    4:     */ import java.io.File;
/*    5:     */ import java.io.IOException;
/*    6:     */ import java.io.InputStream;
/*    7:     */ import java.io.OutputStream;
/*    8:     */ import java.lang.reflect.InvocationTargetException;
/*    9:     */ import java.lang.reflect.Method;
/*   10:     */ import java.net.URL;
/*   11:     */ import java.security.AccessController;
/*   12:     */ import java.security.PrivilegedActionException;
/*   13:     */ import java.security.PrivilegedExceptionAction;
/*   14:     */ import java.security.ProtectionDomain;
/*   15:     */ import java.util.ArrayList;
/*   16:     */ import java.util.Enumeration;
/*   17:     */ import java.util.Hashtable;
/*   18:     */ import java.util.Iterator;
/*   19:     */ import javassist.bytecode.Descriptor;
/*   20:     */ 
/*   21:     */ public class ClassPool
/*   22:     */ {
/*   23:     */   private static Method defineClass1;
/*   24:     */   private static Method defineClass2;
/*   25:     */   
/*   26:     */   static
/*   27:     */   {
/*   28:     */     try
/*   29:     */     {
/*   30:  75 */       AccessController.doPrivileged(new PrivilegedExceptionAction()
/*   31:     */       {
/*   32:     */         public Object run()
/*   33:     */           throws Exception
/*   34:     */         {
/*   35:  77 */           Class cl = Class.forName("java.lang.ClassLoader");
/*   36:  78 */           ClassPool.access$002(cl.getDeclaredMethod("defineClass", new Class[] { String.class, new byte[0].getClass(), Integer.TYPE, Integer.TYPE }));
/*   37:     */           
/*   38:     */ 
/*   39:     */ 
/*   40:  82 */           ClassPool.access$102(cl.getDeclaredMethod("defineClass", new Class[] { String.class, new byte[0].getClass(), Integer.TYPE, Integer.TYPE, ProtectionDomain.class }));
/*   41:     */           
/*   42:     */ 
/*   43:  85 */           return null;
/*   44:     */         }
/*   45:     */       });
/*   46:     */     }
/*   47:     */     catch (PrivilegedActionException pae)
/*   48:     */     {
/*   49:  90 */       throw new RuntimeException("cannot initialize ClassPool", pae.getException());
/*   50:     */     }
/*   51:     */   }
/*   52:     */   
/*   53: 105 */   public boolean childFirstLookup = false;
/*   54: 121 */   public static boolean doPruning = false;
/*   55:     */   private int compressCount;
/*   56:     */   private static final int COMPRESS_THRESHOLD = 100;
/*   57: 136 */   public static boolean releaseUnmodifiedClassFile = true;
/*   58:     */   protected ClassPoolTail source;
/*   59:     */   protected ClassPool parent;
/*   60:     */   protected Hashtable classes;
/*   61: 145 */   private Hashtable cflow = null;
/*   62:     */   private static final int INIT_HASH_SIZE = 191;
/*   63:     */   private ArrayList importedPackages;
/*   64:     */   
/*   65:     */   public ClassPool()
/*   66:     */   {
/*   67: 155 */     this(null);
/*   68:     */   }
/*   69:     */   
/*   70:     */   public ClassPool(boolean useDefaultPath)
/*   71:     */   {
/*   72: 168 */     this(null);
/*   73: 169 */     if (useDefaultPath) {
/*   74: 170 */       appendSystemPath();
/*   75:     */     }
/*   76:     */   }
/*   77:     */   
/*   78:     */   public ClassPool(ClassPool parent)
/*   79:     */   {
/*   80: 181 */     this.classes = new Hashtable(191);
/*   81: 182 */     this.source = new ClassPoolTail();
/*   82: 183 */     this.parent = parent;
/*   83: 184 */     if (parent == null)
/*   84:     */     {
/*   85: 185 */       CtClass[] pt = CtClass.primitiveTypes;
/*   86: 186 */       for (int i = 0; i < pt.length; i++) {
/*   87: 187 */         this.classes.put(pt[i].getName(), pt[i]);
/*   88:     */       }
/*   89:     */     }
/*   90: 190 */     this.cflow = null;
/*   91: 191 */     this.compressCount = 0;
/*   92: 192 */     clearImportedPackages();
/*   93:     */   }
/*   94:     */   
/*   95:     */   public static synchronized ClassPool getDefault()
/*   96:     */   {
/*   97: 220 */     if (defaultPool == null)
/*   98:     */     {
/*   99: 221 */       defaultPool = new ClassPool(null);
/*  100: 222 */       defaultPool.appendSystemPath();
/*  101:     */     }
/*  102: 225 */     return defaultPool;
/*  103:     */   }
/*  104:     */   
/*  105: 228 */   private static ClassPool defaultPool = null;
/*  106:     */   
/*  107:     */   protected CtClass getCached(String classname)
/*  108:     */   {
/*  109: 238 */     return (CtClass)this.classes.get(classname);
/*  110:     */   }
/*  111:     */   
/*  112:     */   protected void cacheCtClass(String classname, CtClass c, boolean dynamic)
/*  113:     */   {
/*  114: 249 */     this.classes.put(classname, c);
/*  115:     */   }
/*  116:     */   
/*  117:     */   protected CtClass removeCached(String classname)
/*  118:     */   {
/*  119: 260 */     return (CtClass)this.classes.remove(classname);
/*  120:     */   }
/*  121:     */   
/*  122:     */   public String toString()
/*  123:     */   {
/*  124: 267 */     return this.source.toString();
/*  125:     */   }
/*  126:     */   
/*  127:     */   void compress()
/*  128:     */   {
/*  129: 275 */     if (this.compressCount++ > 100)
/*  130:     */     {
/*  131: 276 */       this.compressCount = 0;
/*  132: 277 */       Enumeration e = this.classes.elements();
/*  133: 278 */       while (e.hasMoreElements()) {
/*  134: 279 */         ((CtClass)e.nextElement()).compress();
/*  135:     */       }
/*  136:     */     }
/*  137:     */   }
/*  138:     */   
/*  139:     */   public void importPackage(String packageName)
/*  140:     */   {
/*  141: 298 */     this.importedPackages.add(packageName);
/*  142:     */   }
/*  143:     */   
/*  144:     */   public void clearImportedPackages()
/*  145:     */   {
/*  146: 309 */     this.importedPackages = new ArrayList();
/*  147: 310 */     this.importedPackages.add("java.lang");
/*  148:     */   }
/*  149:     */   
/*  150:     */   public Iterator getImportedPackages()
/*  151:     */   {
/*  152: 320 */     return this.importedPackages.iterator();
/*  153:     */   }
/*  154:     */   
/*  155:     */   public void recordInvalidClassName(String name)
/*  156:     */   {
/*  157: 334 */     this.source.recordInvalidClassName(name);
/*  158:     */   }
/*  159:     */   
/*  160:     */   void recordCflow(String name, String cname, String fname)
/*  161:     */   {
/*  162: 346 */     if (this.cflow == null) {
/*  163: 347 */       this.cflow = new Hashtable();
/*  164:     */     }
/*  165: 349 */     this.cflow.put(name, new Object[] { cname, fname });
/*  166:     */   }
/*  167:     */   
/*  168:     */   public Object[] lookupCflow(String name)
/*  169:     */   {
/*  170: 358 */     if (this.cflow == null) {
/*  171: 359 */       this.cflow = new Hashtable();
/*  172:     */     }
/*  173: 361 */     return (Object[])this.cflow.get(name);
/*  174:     */   }
/*  175:     */   
/*  176:     */   public CtClass getAndRename(String orgName, String newName)
/*  177:     */     throws NotFoundException
/*  178:     */   {
/*  179: 385 */     CtClass clazz = get0(orgName, false);
/*  180: 386 */     if (clazz == null) {
/*  181: 387 */       throw new NotFoundException(orgName);
/*  182:     */     }
/*  183: 389 */     if ((clazz instanceof CtClassType)) {
/*  184: 390 */       ((CtClassType)clazz).setClassPool(this);
/*  185:     */     }
/*  186: 392 */     clazz.setName(newName);
/*  187:     */     
/*  188: 394 */     return clazz;
/*  189:     */   }
/*  190:     */   
/*  191:     */   synchronized void classNameChanged(String oldname, CtClass clazz)
/*  192:     */   {
/*  193: 403 */     CtClass c = getCached(oldname);
/*  194: 404 */     if (c == clazz) {
/*  195: 405 */       removeCached(oldname);
/*  196:     */     }
/*  197: 407 */     String newName = clazz.getName();
/*  198: 408 */     checkNotFrozen(newName);
/*  199: 409 */     cacheCtClass(newName, clazz, false);
/*  200:     */   }
/*  201:     */   
/*  202:     */   public CtClass get(String classname)
/*  203:     */     throws NotFoundException
/*  204:     */   {
/*  205:     */     CtClass clazz;
/*  206:     */     CtClass clazz;
/*  207: 430 */     if (classname == null) {
/*  208: 431 */       clazz = null;
/*  209:     */     } else {
/*  210: 433 */       clazz = get0(classname, true);
/*  211:     */     }
/*  212: 435 */     if (clazz == null) {
/*  213: 436 */       throw new NotFoundException(classname);
/*  214:     */     }
/*  215: 438 */     clazz.incGetCounter();
/*  216: 439 */     return clazz;
/*  217:     */   }
/*  218:     */   
/*  219:     */   public CtClass getOrNull(String classname)
/*  220:     */   {
/*  221: 458 */     CtClass clazz = null;
/*  222: 459 */     if (classname == null) {
/*  223: 460 */       clazz = null;
/*  224:     */     } else {
/*  225:     */       try
/*  226:     */       {
/*  227: 467 */         clazz = get0(classname, true);
/*  228:     */       }
/*  229:     */       catch (NotFoundException e) {}
/*  230:     */     }
/*  231: 471 */     if (clazz != null) {
/*  232: 472 */       clazz.incGetCounter();
/*  233:     */     }
/*  234: 474 */     return clazz;
/*  235:     */   }
/*  236:     */   
/*  237:     */   public CtClass getCtClass(String classname)
/*  238:     */     throws NotFoundException
/*  239:     */   {
/*  240: 498 */     if (classname.charAt(0) == '[') {
/*  241: 499 */       return Descriptor.toCtClass(classname, this);
/*  242:     */     }
/*  243: 501 */     return get(classname);
/*  244:     */   }
/*  245:     */   
/*  246:     */   protected synchronized CtClass get0(String classname, boolean useCache)
/*  247:     */     throws NotFoundException
/*  248:     */   {
/*  249: 512 */     CtClass clazz = null;
/*  250: 513 */     if (useCache)
/*  251:     */     {
/*  252: 514 */       clazz = getCached(classname);
/*  253: 515 */       if (clazz != null) {
/*  254: 516 */         return clazz;
/*  255:     */       }
/*  256:     */     }
/*  257: 519 */     if ((!this.childFirstLookup) && (this.parent != null))
/*  258:     */     {
/*  259: 520 */       clazz = this.parent.get0(classname, useCache);
/*  260: 521 */       if (clazz != null) {
/*  261: 522 */         return clazz;
/*  262:     */       }
/*  263:     */     }
/*  264: 525 */     clazz = createCtClass(classname, useCache);
/*  265: 526 */     if (clazz != null)
/*  266:     */     {
/*  267: 528 */       if (useCache) {
/*  268: 529 */         cacheCtClass(clazz.getName(), clazz, false);
/*  269:     */       }
/*  270: 531 */       return clazz;
/*  271:     */     }
/*  272: 534 */     if ((this.childFirstLookup) && (this.parent != null)) {
/*  273: 535 */       clazz = this.parent.get0(classname, useCache);
/*  274:     */     }
/*  275: 537 */     return clazz;
/*  276:     */   }
/*  277:     */   
/*  278:     */   protected CtClass createCtClass(String classname, boolean useCache)
/*  279:     */   {
/*  280: 549 */     if (classname.charAt(0) == '[') {
/*  281: 550 */       classname = Descriptor.toClassName(classname);
/*  282:     */     }
/*  283: 552 */     if (classname.endsWith("[]"))
/*  284:     */     {
/*  285: 553 */       String base = classname.substring(0, classname.indexOf('['));
/*  286: 554 */       if (((!useCache) || (getCached(base) == null)) && (find(base) == null)) {
/*  287: 555 */         return null;
/*  288:     */       }
/*  289: 557 */       return new CtArray(classname, this);
/*  290:     */     }
/*  291: 560 */     if (find(classname) == null) {
/*  292: 561 */       return null;
/*  293:     */     }
/*  294: 563 */     return new CtClassType(classname, this);
/*  295:     */   }
/*  296:     */   
/*  297:     */   public URL find(String classname)
/*  298:     */   {
/*  299: 576 */     return this.source.find(classname);
/*  300:     */   }
/*  301:     */   
/*  302:     */   void checkNotFrozen(String classname)
/*  303:     */     throws RuntimeException
/*  304:     */   {
/*  305: 588 */     CtClass clazz = getCached(classname);
/*  306: 589 */     if (clazz == null)
/*  307:     */     {
/*  308: 590 */       if ((!this.childFirstLookup) && (this.parent != null))
/*  309:     */       {
/*  310:     */         try
/*  311:     */         {
/*  312: 592 */           clazz = this.parent.get0(classname, true);
/*  313:     */         }
/*  314:     */         catch (NotFoundException e) {}
/*  315: 595 */         if (clazz != null) {
/*  316: 596 */           throw new RuntimeException(classname + " is in a parent ClassPool.  Use the parent.");
/*  317:     */         }
/*  318:     */       }
/*  319:     */     }
/*  320: 601 */     else if (clazz.isFrozen()) {
/*  321: 602 */       throw new RuntimeException(classname + ": frozen class (cannot edit)");
/*  322:     */     }
/*  323:     */   }
/*  324:     */   
/*  325:     */   CtClass checkNotExists(String classname)
/*  326:     */   {
/*  327: 613 */     CtClass clazz = getCached(classname);
/*  328: 614 */     if ((clazz == null) && 
/*  329: 615 */       (!this.childFirstLookup) && (this.parent != null)) {
/*  330:     */       try
/*  331:     */       {
/*  332: 617 */         clazz = this.parent.get0(classname, true);
/*  333:     */       }
/*  334:     */       catch (NotFoundException e) {}
/*  335:     */     }
/*  336: 622 */     return clazz;
/*  337:     */   }
/*  338:     */   
/*  339:     */   InputStream openClassfile(String classname)
/*  340:     */     throws NotFoundException
/*  341:     */   {
/*  342: 628 */     return this.source.openClassfile(classname);
/*  343:     */   }
/*  344:     */   
/*  345:     */   void writeClassfile(String classname, OutputStream out)
/*  346:     */     throws NotFoundException, IOException, CannotCompileException
/*  347:     */   {
/*  348: 634 */     this.source.writeClassfile(classname, out);
/*  349:     */   }
/*  350:     */   
/*  351:     */   public CtClass[] get(String[] classnames)
/*  352:     */     throws NotFoundException
/*  353:     */   {
/*  354: 649 */     if (classnames == null) {
/*  355: 650 */       return new CtClass[0];
/*  356:     */     }
/*  357: 652 */     int num = classnames.length;
/*  358: 653 */     CtClass[] result = new CtClass[num];
/*  359: 654 */     for (int i = 0; i < num; i++) {
/*  360: 655 */       result[i] = get(classnames[i]);
/*  361:     */     }
/*  362: 657 */     return result;
/*  363:     */   }
/*  364:     */   
/*  365:     */   public CtMethod getMethod(String classname, String methodname)
/*  366:     */     throws NotFoundException
/*  367:     */   {
/*  368: 670 */     CtClass c = get(classname);
/*  369: 671 */     return c.getDeclaredMethod(methodname);
/*  370:     */   }
/*  371:     */   
/*  372:     */   public CtClass makeClass(InputStream classfile)
/*  373:     */     throws IOException, RuntimeException
/*  374:     */   {
/*  375: 692 */     return makeClass(classfile, true);
/*  376:     */   }
/*  377:     */   
/*  378:     */   public CtClass makeClass(InputStream classfile, boolean ifNotFrozen)
/*  379:     */     throws IOException, RuntimeException
/*  380:     */   {
/*  381: 712 */     compress();
/*  382: 713 */     classfile = new BufferedInputStream(classfile);
/*  383: 714 */     CtClass clazz = new CtClassType(classfile, this);
/*  384: 715 */     clazz.checkModify();
/*  385: 716 */     String classname = clazz.getName();
/*  386: 717 */     if (ifNotFrozen) {
/*  387: 718 */       checkNotFrozen(classname);
/*  388:     */     }
/*  389: 720 */     cacheCtClass(classname, clazz, true);
/*  390: 721 */     return clazz;
/*  391:     */   }
/*  392:     */   
/*  393:     */   public CtClass makeClassIfNew(InputStream classfile)
/*  394:     */     throws IOException, RuntimeException
/*  395:     */   {
/*  396: 742 */     compress();
/*  397: 743 */     classfile = new BufferedInputStream(classfile);
/*  398: 744 */     CtClass clazz = new CtClassType(classfile, this);
/*  399: 745 */     clazz.checkModify();
/*  400: 746 */     String classname = clazz.getName();
/*  401: 747 */     CtClass found = checkNotExists(classname);
/*  402: 748 */     if (found != null) {
/*  403: 749 */       return found;
/*  404:     */     }
/*  405: 751 */     cacheCtClass(classname, clazz, true);
/*  406: 752 */     return clazz;
/*  407:     */   }
/*  408:     */   
/*  409:     */   public CtClass makeClass(String classname)
/*  410:     */     throws RuntimeException
/*  411:     */   {
/*  412: 773 */     return makeClass(classname, null);
/*  413:     */   }
/*  414:     */   
/*  415:     */   public synchronized CtClass makeClass(String classname, CtClass superclass)
/*  416:     */     throws RuntimeException
/*  417:     */   {
/*  418: 796 */     checkNotFrozen(classname);
/*  419: 797 */     CtClass clazz = new CtNewClass(classname, this, false, superclass);
/*  420: 798 */     cacheCtClass(classname, clazz, true);
/*  421: 799 */     return clazz;
/*  422:     */   }
/*  423:     */   
/*  424:     */   synchronized CtClass makeNestedClass(String classname)
/*  425:     */   {
/*  426: 810 */     checkNotFrozen(classname);
/*  427: 811 */     CtClass clazz = new CtNewNestedClass(classname, this, false, null);
/*  428: 812 */     cacheCtClass(classname, clazz, true);
/*  429: 813 */     return clazz;
/*  430:     */   }
/*  431:     */   
/*  432:     */   public CtClass makeInterface(String name)
/*  433:     */     throws RuntimeException
/*  434:     */   {
/*  435: 825 */     return makeInterface(name, null);
/*  436:     */   }
/*  437:     */   
/*  438:     */   public synchronized CtClass makeInterface(String name, CtClass superclass)
/*  439:     */     throws RuntimeException
/*  440:     */   {
/*  441: 840 */     checkNotFrozen(name);
/*  442: 841 */     CtClass clazz = new CtNewClass(name, this, true, superclass);
/*  443: 842 */     cacheCtClass(name, clazz, true);
/*  444: 843 */     return clazz;
/*  445:     */   }
/*  446:     */   
/*  447:     */   public ClassPath appendSystemPath()
/*  448:     */   {
/*  449: 857 */     return this.source.appendSystemPath();
/*  450:     */   }
/*  451:     */   
/*  452:     */   public ClassPath insertClassPath(ClassPath cp)
/*  453:     */   {
/*  454: 870 */     return this.source.insertClassPath(cp);
/*  455:     */   }
/*  456:     */   
/*  457:     */   public ClassPath appendClassPath(ClassPath cp)
/*  458:     */   {
/*  459: 883 */     return this.source.appendClassPath(cp);
/*  460:     */   }
/*  461:     */   
/*  462:     */   public ClassPath insertClassPath(String pathname)
/*  463:     */     throws NotFoundException
/*  464:     */   {
/*  465: 901 */     return this.source.insertClassPath(pathname);
/*  466:     */   }
/*  467:     */   
/*  468:     */   public ClassPath appendClassPath(String pathname)
/*  469:     */     throws NotFoundException
/*  470:     */   {
/*  471: 919 */     return this.source.appendClassPath(pathname);
/*  472:     */   }
/*  473:     */   
/*  474:     */   public void removeClassPath(ClassPath cp)
/*  475:     */   {
/*  476: 928 */     this.source.removeClassPath(cp);
/*  477:     */   }
/*  478:     */   
/*  479:     */   public void appendPathList(String pathlist)
/*  480:     */     throws NotFoundException
/*  481:     */   {
/*  482: 944 */     char sep = File.pathSeparatorChar;
/*  483: 945 */     int i = 0;
/*  484:     */     for (;;)
/*  485:     */     {
/*  486: 947 */       int j = pathlist.indexOf(sep, i);
/*  487: 948 */       if (j < 0)
/*  488:     */       {
/*  489: 949 */         appendClassPath(pathlist.substring(i));
/*  490: 950 */         break;
/*  491:     */       }
/*  492: 953 */       appendClassPath(pathlist.substring(i, j));
/*  493: 954 */       i = j + 1;
/*  494:     */     }
/*  495:     */   }
/*  496:     */   
/*  497:     */   public Class toClass(CtClass clazz)
/*  498:     */     throws CannotCompileException
/*  499:     */   {
/*  500: 986 */     return toClass(clazz, getClassLoader());
/*  501:     */   }
/*  502:     */   
/*  503:     */   public ClassLoader getClassLoader()
/*  504:     */   {
/*  505:1000 */     return getContextClassLoader();
/*  506:     */   }
/*  507:     */   
/*  508:     */   static ClassLoader getContextClassLoader()
/*  509:     */   {
/*  510:1008 */     return Thread.currentThread().getContextClassLoader();
/*  511:     */   }
/*  512:     */   
/*  513:     */   /**
/*  514:     */    * @deprecated
/*  515:     */    */
/*  516:     */   public Class toClass(CtClass ct, ClassLoader loader)
/*  517:     */     throws CannotCompileException
/*  518:     */   {
/*  519:1028 */     return toClass(ct, loader, null);
/*  520:     */   }
/*  521:     */   
/*  522:     */   public Class toClass(CtClass ct, ClassLoader loader, ProtectionDomain domain)
/*  523:     */     throws CannotCompileException
/*  524:     */   {
/*  525:     */     try
/*  526:     */     {
/*  527:1065 */       byte[] b = ct.toBytecode();
/*  528:     */       Object[] args;
/*  529:     */       Method method;
/*  530:     */       Object[] args;
/*  531:1068 */       if (domain == null)
/*  532:     */       {
/*  533:1069 */         Method method = defineClass1;
/*  534:1070 */         args = new Object[] { ct.getName(), b, new Integer(0), new Integer(b.length) };
/*  535:     */       }
/*  536:     */       else
/*  537:     */       {
/*  538:1074 */         method = defineClass2;
/*  539:1075 */         args = new Object[] { ct.getName(), b, new Integer(0), new Integer(b.length), domain };
/*  540:     */       }
/*  541:1079 */       return toClass2(method, loader, args);
/*  542:     */     }
/*  543:     */     catch (RuntimeException e)
/*  544:     */     {
/*  545:1082 */       throw e;
/*  546:     */     }
/*  547:     */     catch (InvocationTargetException e)
/*  548:     */     {
/*  549:1085 */       throw new CannotCompileException(e.getTargetException());
/*  550:     */     }
/*  551:     */     catch (Exception e)
/*  552:     */     {
/*  553:1088 */       throw new CannotCompileException(e);
/*  554:     */     }
/*  555:     */   }
/*  556:     */   
/*  557:     */   private static synchronized Class toClass2(Method method, ClassLoader loader, Object[] args)
/*  558:     */     throws Exception
/*  559:     */   {
/*  560:1096 */     method.setAccessible(true);
/*  561:     */     try
/*  562:     */     {
/*  563:1098 */       return (Class)method.invoke(loader, args);
/*  564:     */     }
/*  565:     */     finally
/*  566:     */     {
/*  567:1101 */       method.setAccessible(false);
/*  568:     */     }
/*  569:     */   }
/*  570:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.ClassPool
 * JD-Core Version:    0.7.0.1
 */