/*    1:     */ package javassist.util.proxy;
/*    2:     */ 
/*    3:     */ import java.lang.ref.WeakReference;
/*    4:     */ import java.lang.reflect.Constructor;
/*    5:     */ import java.lang.reflect.Field;
/*    6:     */ import java.lang.reflect.InvocationTargetException;
/*    7:     */ import java.lang.reflect.Member;
/*    8:     */ import java.lang.reflect.Method;
/*    9:     */ import java.lang.reflect.Modifier;
/*   10:     */ import java.security.ProtectionDomain;
/*   11:     */ import java.util.ArrayList;
/*   12:     */ import java.util.Collections;
/*   13:     */ import java.util.Comparator;
/*   14:     */ import java.util.HashMap;
/*   15:     */ import java.util.Iterator;
/*   16:     */ import java.util.List;
/*   17:     */ import java.util.Map.Entry;
/*   18:     */ import java.util.WeakHashMap;
/*   19:     */ import javassist.CannotCompileException;
/*   20:     */ import javassist.bytecode.Bytecode;
/*   21:     */ import javassist.bytecode.ClassFile;
/*   22:     */ import javassist.bytecode.CodeAttribute;
/*   23:     */ import javassist.bytecode.ConstPool;
/*   24:     */ import javassist.bytecode.Descriptor;
/*   25:     */ import javassist.bytecode.DuplicateMemberException;
/*   26:     */ import javassist.bytecode.ExceptionsAttribute;
/*   27:     */ import javassist.bytecode.FieldInfo;
/*   28:     */ import javassist.bytecode.MethodInfo;
/*   29:     */ import javassist.bytecode.StackMapTable.Writer;
/*   30:     */ 
/*   31:     */ public class ProxyFactory
/*   32:     */ {
/*   33:     */   private Class superClass;
/*   34:     */   private Class[] interfaces;
/*   35:     */   private MethodFilter methodFilter;
/*   36:     */   private MethodHandler handler;
/*   37:     */   private List signatureMethods;
/*   38:     */   private byte[] signature;
/*   39:     */   private String classname;
/*   40:     */   private String basename;
/*   41:     */   private String superName;
/*   42:     */   private Class thisClass;
/*   43:     */   private boolean factoryUseCache;
/*   44:     */   private boolean factoryWriteReplace;
/*   45:     */   public String writeDirectory;
/*   46: 184 */   private static final Class OBJECT_TYPE = Object.class;
/*   47:     */   private static final String HOLDER = "_methods_";
/*   48:     */   private static final String HOLDER_TYPE = "[Ljava/lang/reflect/Method;";
/*   49:     */   private static final String FILTER_SIGNATURE_FIELD = "_filter_signature";
/*   50:     */   private static final String FILTER_SIGNATURE_TYPE = "[B";
/*   51:     */   private static final String HANDLER = "handler";
/*   52:     */   private static final String NULL_INTERCEPTOR_HOLDER = "javassist.util.proxy.RuntimeSupport";
/*   53:     */   private static final String DEFAULT_INTERCEPTOR = "default_interceptor";
/*   54: 193 */   private static final String HANDLER_TYPE = 'L' + MethodHandler.class.getName().replace('.', '/') + ';';
/*   55:     */   private static final String HANDLER_SETTER = "setHandler";
/*   56: 196 */   private static final String HANDLER_SETTER_TYPE = "(" + HANDLER_TYPE + ")V";
/*   57:     */   private static final String HANDLER_GETTER = "getHandler";
/*   58: 199 */   private static final String HANDLER_GETTER_TYPE = "()" + HANDLER_TYPE;
/*   59:     */   private static final String SERIAL_VERSION_UID_FIELD = "serialVersionUID";
/*   60:     */   private static final String SERIAL_VERSION_UID_TYPE = "J";
/*   61:     */   private static final int SERIAL_VERSION_UID_VALUE = -1;
/*   62: 216 */   public static volatile boolean useCache = true;
/*   63: 237 */   public static volatile boolean useWriteReplace = true;
/*   64:     */   
/*   65:     */   public boolean isUseCache()
/*   66:     */   {
/*   67: 249 */     return this.factoryUseCache;
/*   68:     */   }
/*   69:     */   
/*   70:     */   public void setUseCache(boolean useCache)
/*   71:     */   {
/*   72: 261 */     if ((this.handler != null) && (useCache)) {
/*   73: 262 */       throw new RuntimeException("caching cannot be enabled if the factory default interceptor has been set");
/*   74:     */     }
/*   75: 264 */     this.factoryUseCache = useCache;
/*   76:     */   }
/*   77:     */   
/*   78:     */   public boolean isUseWriteReplace()
/*   79:     */   {
/*   80: 273 */     return this.factoryWriteReplace;
/*   81:     */   }
/*   82:     */   
/*   83:     */   public void setUseWriteReplace(boolean useWriteReplace)
/*   84:     */   {
/*   85: 283 */     this.factoryWriteReplace = useWriteReplace;
/*   86:     */   }
/*   87:     */   
/*   88: 286 */   private static WeakHashMap proxyCache = new WeakHashMap();
/*   89:     */   
/*   90:     */   public static boolean isProxyClass(Class cl)
/*   91:     */   {
/*   92: 296 */     return ProxyObject.class.isAssignableFrom(cl);
/*   93:     */   }
/*   94:     */   
/*   95:     */   public static abstract interface ClassLoaderProvider
/*   96:     */   {
/*   97:     */     public abstract ClassLoader get(ProxyFactory paramProxyFactory);
/*   98:     */   }
/*   99:     */   
/*  100:     */   static class ProxyDetails
/*  101:     */   {
/*  102:     */     byte[] signature;
/*  103:     */     WeakReference proxyClass;
/*  104:     */     boolean isUseWriteReplace;
/*  105:     */     
/*  106:     */     ProxyDetails(byte[] signature, Class proxyClass, boolean isUseWriteReplace)
/*  107:     */     {
/*  108: 324 */       this.signature = signature;
/*  109: 325 */       this.proxyClass = new WeakReference(proxyClass);
/*  110: 326 */       this.isUseWriteReplace = isUseWriteReplace;
/*  111:     */     }
/*  112:     */   }
/*  113:     */   
/*  114:     */   public ProxyFactory()
/*  115:     */   {
/*  116: 334 */     this.superClass = null;
/*  117: 335 */     this.interfaces = null;
/*  118: 336 */     this.methodFilter = null;
/*  119: 337 */     this.handler = null;
/*  120: 338 */     this.signature = null;
/*  121: 339 */     this.signatureMethods = null;
/*  122: 340 */     this.thisClass = null;
/*  123: 341 */     this.writeDirectory = null;
/*  124: 342 */     this.factoryUseCache = useCache;
/*  125: 343 */     this.factoryWriteReplace = useWriteReplace;
/*  126:     */   }
/*  127:     */   
/*  128:     */   public void setSuperclass(Class clazz)
/*  129:     */   {
/*  130: 350 */     this.superClass = clazz;
/*  131:     */     
/*  132: 352 */     this.signature = null;
/*  133:     */   }
/*  134:     */   
/*  135:     */   public Class getSuperclass()
/*  136:     */   {
/*  137: 360 */     return this.superClass;
/*  138:     */   }
/*  139:     */   
/*  140:     */   public void setInterfaces(Class[] ifs)
/*  141:     */   {
/*  142: 366 */     this.interfaces = ifs;
/*  143:     */     
/*  144: 368 */     this.signature = null;
/*  145:     */   }
/*  146:     */   
/*  147:     */   public Class[] getInterfaces()
/*  148:     */   {
/*  149: 376 */     return this.interfaces;
/*  150:     */   }
/*  151:     */   
/*  152:     */   public void setFilter(MethodFilter mf)
/*  153:     */   {
/*  154: 382 */     this.methodFilter = mf;
/*  155:     */     
/*  156: 384 */     this.signature = null;
/*  157:     */   }
/*  158:     */   
/*  159:     */   public Class createClass()
/*  160:     */   {
/*  161: 391 */     if (this.signature == null) {
/*  162: 392 */       computeSignature(this.methodFilter);
/*  163:     */     }
/*  164: 394 */     return createClass1();
/*  165:     */   }
/*  166:     */   
/*  167:     */   public Class createClass(MethodFilter filter)
/*  168:     */   {
/*  169: 401 */     computeSignature(filter);
/*  170: 402 */     return createClass1();
/*  171:     */   }
/*  172:     */   
/*  173:     */   Class createClass(byte[] signature)
/*  174:     */   {
/*  175: 413 */     installSignature(signature);
/*  176: 414 */     return createClass1();
/*  177:     */   }
/*  178:     */   
/*  179:     */   private Class createClass1()
/*  180:     */   {
/*  181: 418 */     if (this.thisClass == null)
/*  182:     */     {
/*  183: 419 */       ClassLoader cl = getClassLoader();
/*  184: 420 */       synchronized (proxyCache)
/*  185:     */       {
/*  186: 421 */         if (this.factoryUseCache) {
/*  187: 422 */           createClass2(cl);
/*  188:     */         } else {
/*  189: 424 */           createClass3(cl);
/*  190:     */         }
/*  191:     */       }
/*  192:     */     }
/*  193: 429 */     Class result = this.thisClass;
/*  194: 430 */     this.thisClass = null;
/*  195:     */     
/*  196: 432 */     return result;
/*  197:     */   }
/*  198:     */   
/*  199: 435 */   private static char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
/*  200:     */   
/*  201:     */   public String getKey(Class superClass, Class[] interfaces, byte[] signature, boolean useWriteReplace)
/*  202:     */   {
/*  203: 441 */     StringBuffer sbuf = new StringBuffer();
/*  204: 442 */     if (superClass != null) {
/*  205: 443 */       sbuf.append(superClass.getName());
/*  206:     */     }
/*  207: 445 */     sbuf.append(":");
/*  208: 446 */     for (int i = 0; i < interfaces.length; i++)
/*  209:     */     {
/*  210: 447 */       sbuf.append(interfaces[i].getName());
/*  211: 448 */       sbuf.append(":");
/*  212:     */     }
/*  213: 450 */     for (int i = 0; i < signature.length; i++)
/*  214:     */     {
/*  215: 451 */       byte b = signature[i];
/*  216: 452 */       int lo = b & 0xF;
/*  217: 453 */       int hi = b >> 4 & 0xF;
/*  218: 454 */       sbuf.append(hexDigits[lo]);
/*  219: 455 */       sbuf.append(hexDigits[hi]);
/*  220:     */     }
/*  221: 457 */     if (useWriteReplace) {
/*  222: 458 */       sbuf.append(":w");
/*  223:     */     }
/*  224: 461 */     return sbuf.toString();
/*  225:     */   }
/*  226:     */   
/*  227:     */   private void createClass2(ClassLoader cl)
/*  228:     */   {
/*  229: 465 */     String key = getKey(this.superClass, this.interfaces, this.signature, this.factoryWriteReplace);
/*  230:     */     
/*  231:     */ 
/*  232:     */ 
/*  233:     */ 
/*  234:     */ 
/*  235:     */ 
/*  236: 472 */     HashMap cacheForTheLoader = (HashMap)proxyCache.get(cl);
/*  237: 474 */     if (cacheForTheLoader == null)
/*  238:     */     {
/*  239: 475 */       cacheForTheLoader = new HashMap();
/*  240: 476 */       proxyCache.put(cl, cacheForTheLoader);
/*  241:     */     }
/*  242: 478 */     ProxyDetails details = (ProxyDetails)cacheForTheLoader.get(key);
/*  243: 479 */     if (details != null)
/*  244:     */     {
/*  245: 480 */       WeakReference reference = details.proxyClass;
/*  246: 481 */       this.thisClass = ((Class)reference.get());
/*  247: 482 */       if (this.thisClass != null) {
/*  248: 483 */         return;
/*  249:     */       }
/*  250:     */     }
/*  251: 486 */     createClass3(cl);
/*  252: 487 */     details = new ProxyDetails(this.signature, this.thisClass, this.factoryWriteReplace);
/*  253: 488 */     cacheForTheLoader.put(key, details);
/*  254:     */   }
/*  255:     */   
/*  256:     */   private void createClass3(ClassLoader cl)
/*  257:     */   {
/*  258: 494 */     allocateClassName();
/*  259:     */     try
/*  260:     */     {
/*  261: 497 */       ClassFile cf = make();
/*  262: 498 */       if (this.writeDirectory != null) {
/*  263: 499 */         FactoryHelper.writeFile(cf, this.writeDirectory);
/*  264:     */       }
/*  265: 501 */       this.thisClass = FactoryHelper.toClass(cf, cl, getDomain());
/*  266: 502 */       setField("_filter_signature", this.signature);
/*  267: 504 */       if (!this.factoryUseCache) {
/*  268: 505 */         setField("default_interceptor", this.handler);
/*  269:     */       }
/*  270:     */     }
/*  271:     */     catch (CannotCompileException e)
/*  272:     */     {
/*  273: 509 */       throw new RuntimeException(e.getMessage(), e);
/*  274:     */     }
/*  275:     */   }
/*  276:     */   
/*  277:     */   private void setField(String fieldName, Object value)
/*  278:     */   {
/*  279: 515 */     if ((this.thisClass != null) && (value != null)) {
/*  280:     */       try
/*  281:     */       {
/*  282: 517 */         Field f = this.thisClass.getField(fieldName);
/*  283: 518 */         SecurityActions.setAccessible(f, true);
/*  284: 519 */         f.set(null, value);
/*  285: 520 */         SecurityActions.setAccessible(f, false);
/*  286:     */       }
/*  287:     */       catch (Exception e)
/*  288:     */       {
/*  289: 523 */         throw new RuntimeException(e);
/*  290:     */       }
/*  291:     */     }
/*  292:     */   }
/*  293:     */   
/*  294:     */   static byte[] getFilterSignature(Class clazz)
/*  295:     */   {
/*  296: 528 */     return (byte[])getField(clazz, "_filter_signature");
/*  297:     */   }
/*  298:     */   
/*  299:     */   private static Object getField(Class clazz, String fieldName)
/*  300:     */   {
/*  301:     */     try
/*  302:     */     {
/*  303: 533 */       Field f = clazz.getField(fieldName);
/*  304: 534 */       f.setAccessible(true);
/*  305: 535 */       Object value = f.get(null);
/*  306: 536 */       f.setAccessible(false);
/*  307: 537 */       return value;
/*  308:     */     }
/*  309:     */     catch (Exception e)
/*  310:     */     {
/*  311: 540 */       throw new RuntimeException(e);
/*  312:     */     }
/*  313:     */   }
/*  314:     */   
/*  315: 579 */   public static ClassLoaderProvider classLoaderProvider = new ClassLoaderProvider()
/*  316:     */   {
/*  317:     */     public ClassLoader get(ProxyFactory pf)
/*  318:     */     {
/*  319: 582 */       return pf.getClassLoader0();
/*  320:     */     }
/*  321:     */   };
/*  322:     */   
/*  323:     */   protected ClassLoader getClassLoader()
/*  324:     */   {
/*  325: 587 */     return classLoaderProvider.get(this);
/*  326:     */   }
/*  327:     */   
/*  328:     */   protected ClassLoader getClassLoader0()
/*  329:     */   {
/*  330: 591 */     ClassLoader loader = null;
/*  331: 592 */     if ((this.superClass != null) && (!this.superClass.getName().equals("java.lang.Object"))) {
/*  332: 593 */       loader = this.superClass.getClassLoader();
/*  333: 594 */     } else if ((this.interfaces != null) && (this.interfaces.length > 0)) {
/*  334: 595 */       loader = this.interfaces[0].getClassLoader();
/*  335:     */     }
/*  336: 597 */     if (loader == null)
/*  337:     */     {
/*  338: 598 */       loader = getClass().getClassLoader();
/*  339: 600 */       if (loader == null)
/*  340:     */       {
/*  341: 601 */         loader = Thread.currentThread().getContextClassLoader();
/*  342: 602 */         if (loader == null) {
/*  343: 603 */           loader = ClassLoader.getSystemClassLoader();
/*  344:     */         }
/*  345:     */       }
/*  346:     */     }
/*  347: 607 */     return loader;
/*  348:     */   }
/*  349:     */   
/*  350:     */   protected ProtectionDomain getDomain()
/*  351:     */   {
/*  352:     */     Class clazz;
/*  353:     */     Class clazz;
/*  354: 612 */     if ((this.superClass != null) && (!this.superClass.getName().equals("java.lang.Object")))
/*  355:     */     {
/*  356: 613 */       clazz = this.superClass;
/*  357:     */     }
/*  358:     */     else
/*  359:     */     {
/*  360:     */       Class clazz;
/*  361: 614 */       if ((this.interfaces != null) && (this.interfaces.length > 0)) {
/*  362: 615 */         clazz = this.interfaces[0];
/*  363:     */       } else {
/*  364: 617 */         clazz = getClass();
/*  365:     */       }
/*  366:     */     }
/*  367: 619 */     return clazz.getProtectionDomain();
/*  368:     */   }
/*  369:     */   
/*  370:     */   public Object create(Class[] paramTypes, Object[] args, MethodHandler mh)
/*  371:     */     throws NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException
/*  372:     */   {
/*  373: 634 */     Object obj = create(paramTypes, args);
/*  374: 635 */     ((ProxyObject)obj).setHandler(mh);
/*  375: 636 */     return obj;
/*  376:     */   }
/*  377:     */   
/*  378:     */   public Object create(Class[] paramTypes, Object[] args)
/*  379:     */     throws NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException
/*  380:     */   {
/*  381: 649 */     Class c = createClass();
/*  382: 650 */     Constructor cons = c.getConstructor(paramTypes);
/*  383: 651 */     return cons.newInstance(args);
/*  384:     */   }
/*  385:     */   
/*  386:     */   /**
/*  387:     */    * @deprecated
/*  388:     */    */
/*  389:     */   public void setHandler(MethodHandler mi)
/*  390:     */   {
/*  391: 666 */     if ((this.factoryUseCache) && (mi != null))
/*  392:     */     {
/*  393: 667 */       this.factoryUseCache = false;
/*  394:     */       
/*  395: 669 */       this.thisClass = null;
/*  396:     */     }
/*  397: 671 */     this.handler = mi;
/*  398:     */     
/*  399:     */ 
/*  400: 674 */     setField("default_interceptor", this.handler);
/*  401:     */   }
/*  402:     */   
/*  403: 677 */   private static int counter = 0;
/*  404:     */   
/*  405:     */   private static synchronized String makeProxyName(String classname)
/*  406:     */   {
/*  407: 680 */     return classname + "_$$_javassist_" + counter++;
/*  408:     */   }
/*  409:     */   
/*  410:     */   private ClassFile make()
/*  411:     */     throws CannotCompileException
/*  412:     */   {
/*  413: 684 */     ClassFile cf = new ClassFile(false, this.classname, this.superName);
/*  414: 685 */     cf.setAccessFlags(1);
/*  415: 686 */     setInterfaces(cf, this.interfaces);
/*  416: 687 */     ConstPool pool = cf.getConstPool();
/*  417: 690 */     if (!this.factoryUseCache)
/*  418:     */     {
/*  419: 691 */       FieldInfo finfo = new FieldInfo(pool, "default_interceptor", HANDLER_TYPE);
/*  420: 692 */       finfo.setAccessFlags(9);
/*  421: 693 */       cf.addField(finfo);
/*  422:     */     }
/*  423: 697 */     FieldInfo finfo2 = new FieldInfo(pool, "handler", HANDLER_TYPE);
/*  424: 698 */     finfo2.setAccessFlags(2);
/*  425: 699 */     cf.addField(finfo2);
/*  426:     */     
/*  427:     */ 
/*  428: 702 */     FieldInfo finfo3 = new FieldInfo(pool, "_filter_signature", "[B");
/*  429: 703 */     finfo3.setAccessFlags(9);
/*  430: 704 */     cf.addField(finfo3);
/*  431:     */     
/*  432:     */ 
/*  433: 707 */     FieldInfo finfo4 = new FieldInfo(pool, "serialVersionUID", "J");
/*  434: 708 */     finfo4.setAccessFlags(25);
/*  435: 709 */     cf.addField(finfo4);
/*  436:     */     
/*  437:     */ 
/*  438:     */ 
/*  439: 713 */     makeConstructors(this.classname, cf, pool, this.classname);
/*  440: 714 */     int s = overrideMethods(cf, pool, this.classname);
/*  441: 715 */     addMethodsHolder(cf, pool, this.classname, s);
/*  442: 716 */     addSetter(this.classname, cf, pool);
/*  443: 717 */     addGetter(this.classname, cf, pool);
/*  444: 719 */     if (this.factoryWriteReplace) {
/*  445:     */       try
/*  446:     */       {
/*  447: 721 */         cf.addMethod(makeWriteReplace(pool));
/*  448:     */       }
/*  449:     */       catch (DuplicateMemberException e) {}
/*  450:     */     }
/*  451: 728 */     this.thisClass = null;
/*  452: 729 */     return cf;
/*  453:     */   }
/*  454:     */   
/*  455:     */   private void checkClassAndSuperName()
/*  456:     */   {
/*  457: 734 */     if (this.interfaces == null) {
/*  458: 735 */       this.interfaces = new Class[0];
/*  459:     */     }
/*  460: 737 */     if (this.superClass == null)
/*  461:     */     {
/*  462: 738 */       this.superClass = OBJECT_TYPE;
/*  463: 739 */       this.superName = this.superClass.getName();
/*  464: 740 */       this.basename = (this.interfaces.length == 0 ? this.superName : this.interfaces[0].getName());
/*  465:     */     }
/*  466:     */     else
/*  467:     */     {
/*  468: 743 */       this.superName = this.superClass.getName();
/*  469: 744 */       this.basename = this.superName;
/*  470:     */     }
/*  471: 747 */     if (Modifier.isFinal(this.superClass.getModifiers())) {
/*  472: 748 */       throw new RuntimeException(this.superName + " is final");
/*  473:     */     }
/*  474: 750 */     if (this.basename.startsWith("java.")) {
/*  475: 751 */       this.basename = ("org.javassist.tmp." + this.basename);
/*  476:     */     }
/*  477:     */   }
/*  478:     */   
/*  479:     */   private void allocateClassName()
/*  480:     */   {
/*  481: 756 */     this.classname = makeProxyName(this.basename);
/*  482:     */   }
/*  483:     */   
/*  484: 759 */   private static Comparator sorter = new Comparator()
/*  485:     */   {
/*  486:     */     public int compare(Object o1, Object o2)
/*  487:     */     {
/*  488: 762 */       Map.Entry e1 = (Map.Entry)o1;
/*  489: 763 */       Map.Entry e2 = (Map.Entry)o2;
/*  490: 764 */       String key1 = (String)e1.getKey();
/*  491: 765 */       String key2 = (String)e2.getKey();
/*  492: 766 */       return key1.compareTo(key2);
/*  493:     */     }
/*  494:     */   };
/*  495:     */   
/*  496:     */   private void makeSortedMethodList()
/*  497:     */   {
/*  498: 772 */     checkClassAndSuperName();
/*  499:     */     
/*  500: 774 */     HashMap allMethods = getMethods(this.superClass, this.interfaces);
/*  501: 775 */     this.signatureMethods = new ArrayList(allMethods.entrySet());
/*  502: 776 */     Collections.sort(this.signatureMethods, sorter);
/*  503:     */   }
/*  504:     */   
/*  505:     */   private void computeSignature(MethodFilter filter)
/*  506:     */   {
/*  507: 781 */     makeSortedMethodList();
/*  508:     */     
/*  509: 783 */     int l = this.signatureMethods.size();
/*  510: 784 */     int maxBytes = l + 7 >> 3;
/*  511: 785 */     this.signature = new byte[maxBytes];
/*  512: 786 */     for (int idx = 0; idx < l; idx++)
/*  513:     */     {
/*  514: 788 */       Map.Entry e = (Map.Entry)this.signatureMethods.get(idx);
/*  515: 789 */       Method m = (Method)e.getValue();
/*  516: 790 */       int mod = m.getModifiers();
/*  517: 791 */       if ((!Modifier.isFinal(mod)) && (!Modifier.isStatic(mod)) && (isVisible(mod, this.basename, m)) && ((filter == null) || (filter.isHandled(m)))) {
/*  518: 793 */         setBit(this.signature, idx);
/*  519:     */       }
/*  520:     */     }
/*  521:     */   }
/*  522:     */   
/*  523:     */   private void installSignature(byte[] signature)
/*  524:     */   {
/*  525: 800 */     makeSortedMethodList();
/*  526:     */     
/*  527: 802 */     int l = this.signatureMethods.size();
/*  528: 803 */     int maxBytes = l + 7 >> 3;
/*  529: 804 */     if (signature.length != maxBytes) {
/*  530: 805 */       throw new RuntimeException("invalid filter signature length for deserialized proxy class");
/*  531:     */     }
/*  532: 808 */     this.signature = signature;
/*  533:     */   }
/*  534:     */   
/*  535:     */   private boolean testBit(byte[] signature, int idx)
/*  536:     */   {
/*  537: 813 */     int byteIdx = idx >> 3;
/*  538: 814 */     if (byteIdx > signature.length) {
/*  539: 815 */       return false;
/*  540:     */     }
/*  541: 817 */     int bitIdx = idx & 0x7;
/*  542: 818 */     int mask = 1 << bitIdx;
/*  543: 819 */     int sigByte = signature[byteIdx];
/*  544: 820 */     return (sigByte & mask) != 0;
/*  545:     */   }
/*  546:     */   
/*  547:     */   private void setBit(byte[] signature, int idx)
/*  548:     */   {
/*  549: 826 */     int byteIdx = idx >> 3;
/*  550: 827 */     if (byteIdx < signature.length)
/*  551:     */     {
/*  552: 828 */       int bitIdx = idx & 0x7;
/*  553: 829 */       int mask = 1 << bitIdx;
/*  554: 830 */       int sigByte = signature[byteIdx];
/*  555: 831 */       signature[byteIdx] = ((byte)(sigByte | mask));
/*  556:     */     }
/*  557:     */   }
/*  558:     */   
/*  559:     */   private static void setInterfaces(ClassFile cf, Class[] interfaces)
/*  560:     */   {
/*  561: 836 */     String setterIntf = ProxyObject.class.getName();
/*  562:     */     String[] list;
/*  563:     */     String[] list;
/*  564: 838 */     if ((interfaces == null) || (interfaces.length == 0))
/*  565:     */     {
/*  566: 839 */       list = new String[] { setterIntf };
/*  567:     */     }
/*  568:     */     else
/*  569:     */     {
/*  570: 841 */       list = new String[interfaces.length + 1];
/*  571: 842 */       for (int i = 0; i < interfaces.length; i++) {
/*  572: 843 */         list[i] = interfaces[i].getName();
/*  573:     */       }
/*  574: 845 */       list[interfaces.length] = setterIntf;
/*  575:     */     }
/*  576: 848 */     cf.setInterfaces(list);
/*  577:     */   }
/*  578:     */   
/*  579:     */   private static void addMethodsHolder(ClassFile cf, ConstPool cp, String classname, int size)
/*  580:     */     throws CannotCompileException
/*  581:     */   {
/*  582: 855 */     FieldInfo finfo = new FieldInfo(cp, "_methods_", "[Ljava/lang/reflect/Method;");
/*  583: 856 */     finfo.setAccessFlags(10);
/*  584: 857 */     cf.addField(finfo);
/*  585: 858 */     MethodInfo minfo = new MethodInfo(cp, "<clinit>", "()V");
/*  586: 859 */     minfo.setAccessFlags(8);
/*  587: 860 */     Bytecode code = new Bytecode(cp, 0, 0);
/*  588: 861 */     code.addIconst(size * 2);
/*  589: 862 */     code.addAnewarray("java.lang.reflect.Method");
/*  590: 863 */     code.addPutstatic(classname, "_methods_", "[Ljava/lang/reflect/Method;");
/*  591:     */     
/*  592: 865 */     code.addLconst(-1L);
/*  593: 866 */     code.addPutstatic(classname, "serialVersionUID", "J");
/*  594: 867 */     code.addOpcode(177);
/*  595: 868 */     minfo.setCodeAttribute(code.toCodeAttribute());
/*  596: 869 */     cf.addMethod(minfo);
/*  597:     */   }
/*  598:     */   
/*  599:     */   private static void addSetter(String classname, ClassFile cf, ConstPool cp)
/*  600:     */     throws CannotCompileException
/*  601:     */   {
/*  602: 875 */     MethodInfo minfo = new MethodInfo(cp, "setHandler", HANDLER_SETTER_TYPE);
/*  603:     */     
/*  604: 877 */     minfo.setAccessFlags(1);
/*  605: 878 */     Bytecode code = new Bytecode(cp, 2, 2);
/*  606: 879 */     code.addAload(0);
/*  607: 880 */     code.addAload(1);
/*  608: 881 */     code.addPutfield(classname, "handler", HANDLER_TYPE);
/*  609: 882 */     code.addOpcode(177);
/*  610: 883 */     minfo.setCodeAttribute(code.toCodeAttribute());
/*  611: 884 */     cf.addMethod(minfo);
/*  612:     */   }
/*  613:     */   
/*  614:     */   private static void addGetter(String classname, ClassFile cf, ConstPool cp)
/*  615:     */     throws CannotCompileException
/*  616:     */   {
/*  617: 890 */     MethodInfo minfo = new MethodInfo(cp, "getHandler", HANDLER_GETTER_TYPE);
/*  618:     */     
/*  619: 892 */     minfo.setAccessFlags(1);
/*  620: 893 */     Bytecode code = new Bytecode(cp, 1, 1);
/*  621: 894 */     code.addAload(0);
/*  622: 895 */     code.addGetfield(classname, "handler", HANDLER_TYPE);
/*  623: 896 */     code.addOpcode(176);
/*  624: 897 */     minfo.setCodeAttribute(code.toCodeAttribute());
/*  625: 898 */     cf.addMethod(minfo);
/*  626:     */   }
/*  627:     */   
/*  628:     */   private int overrideMethods(ClassFile cf, ConstPool cp, String className)
/*  629:     */     throws CannotCompileException
/*  630:     */   {
/*  631: 904 */     String prefix = makeUniqueName("_d", this.signatureMethods);
/*  632: 905 */     Iterator it = this.signatureMethods.iterator();
/*  633: 906 */     int index = 0;
/*  634: 907 */     while (it.hasNext())
/*  635:     */     {
/*  636: 908 */       Map.Entry e = (Map.Entry)it.next();
/*  637: 909 */       String key = (String)e.getKey();
/*  638: 910 */       Method meth = (Method)e.getValue();
/*  639: 911 */       int mod = meth.getModifiers();
/*  640: 912 */       if (testBit(this.signature, index)) {
/*  641: 913 */         override(className, meth, prefix, index, keyToDesc(key), cf, cp);
/*  642:     */       }
/*  643: 916 */       index++;
/*  644:     */     }
/*  645: 919 */     return index;
/*  646:     */   }
/*  647:     */   
/*  648:     */   private void override(String thisClassname, Method meth, String prefix, int index, String desc, ClassFile cf, ConstPool cp)
/*  649:     */     throws CannotCompileException
/*  650:     */   {
/*  651: 926 */     Class declClass = meth.getDeclaringClass();
/*  652: 927 */     String delegatorName = prefix + index + meth.getName();
/*  653: 928 */     if (Modifier.isAbstract(meth.getModifiers()))
/*  654:     */     {
/*  655: 929 */       delegatorName = null;
/*  656:     */     }
/*  657:     */     else
/*  658:     */     {
/*  659: 931 */       MethodInfo delegator = makeDelegator(meth, desc, cp, declClass, delegatorName);
/*  660:     */       
/*  661:     */ 
/*  662: 934 */       delegator.setAccessFlags(delegator.getAccessFlags() & 0xFFFFFFBF);
/*  663: 935 */       cf.addMethod(delegator);
/*  664:     */     }
/*  665: 938 */     MethodInfo forwarder = makeForwarder(thisClassname, meth, desc, cp, declClass, delegatorName, index);
/*  666:     */     
/*  667:     */ 
/*  668: 941 */     cf.addMethod(forwarder);
/*  669:     */   }
/*  670:     */   
/*  671:     */   private void makeConstructors(String thisClassName, ClassFile cf, ConstPool cp, String classname)
/*  672:     */     throws CannotCompileException
/*  673:     */   {
/*  674: 947 */     Constructor[] cons = SecurityActions.getDeclaredConstructors(this.superClass);
/*  675:     */     
/*  676: 949 */     boolean doHandlerInit = !this.factoryUseCache;
/*  677: 950 */     for (int i = 0; i < cons.length; i++)
/*  678:     */     {
/*  679: 951 */       Constructor c = cons[i];
/*  680: 952 */       int mod = c.getModifiers();
/*  681: 953 */       if ((!Modifier.isFinal(mod)) && (!Modifier.isPrivate(mod)) && (isVisible(mod, this.basename, c)))
/*  682:     */       {
/*  683: 955 */         MethodInfo m = makeConstructor(thisClassName, c, cp, this.superClass, doHandlerInit);
/*  684: 956 */         cf.addMethod(m);
/*  685:     */       }
/*  686:     */     }
/*  687:     */   }
/*  688:     */   
/*  689:     */   private static String makeUniqueName(String name, List sortedMethods)
/*  690:     */   {
/*  691: 962 */     if (makeUniqueName0(name, sortedMethods.iterator())) {
/*  692: 963 */       return name;
/*  693:     */     }
/*  694: 965 */     for (int i = 100; i < 999; i++)
/*  695:     */     {
/*  696: 966 */       String s = name + i;
/*  697: 967 */       if (makeUniqueName0(s, sortedMethods.iterator())) {
/*  698: 968 */         return s;
/*  699:     */       }
/*  700:     */     }
/*  701: 971 */     throw new RuntimeException("cannot make a unique method name");
/*  702:     */   }
/*  703:     */   
/*  704:     */   private static boolean makeUniqueName0(String name, Iterator it)
/*  705:     */   {
/*  706: 975 */     while (it.hasNext())
/*  707:     */     {
/*  708: 976 */       Map.Entry e = (Map.Entry)it.next();
/*  709: 977 */       String key = (String)e.getKey();
/*  710: 978 */       if (key.startsWith(name)) {
/*  711: 979 */         return false;
/*  712:     */       }
/*  713:     */     }
/*  714: 982 */     return true;
/*  715:     */   }
/*  716:     */   
/*  717:     */   private static boolean isVisible(int mod, String from, Member meth)
/*  718:     */   {
/*  719: 991 */     if ((mod & 0x2) != 0) {
/*  720: 992 */       return false;
/*  721:     */     }
/*  722: 993 */     if ((mod & 0x5) != 0) {
/*  723: 994 */       return true;
/*  724:     */     }
/*  725: 996 */     String p = getPackageName(from);
/*  726: 997 */     String q = getPackageName(meth.getDeclaringClass().getName());
/*  727: 998 */     if (p == null) {
/*  728: 999 */       return q == null;
/*  729:     */     }
/*  730:1001 */     return p.equals(q);
/*  731:     */   }
/*  732:     */   
/*  733:     */   private static String getPackageName(String name)
/*  734:     */   {
/*  735:1006 */     int i = name.lastIndexOf('.');
/*  736:1007 */     if (i < 0) {
/*  737:1008 */       return null;
/*  738:     */     }
/*  739:1010 */     return name.substring(0, i);
/*  740:     */   }
/*  741:     */   
/*  742:     */   private static HashMap getMethods(Class superClass, Class[] interfaceTypes)
/*  743:     */   {
/*  744:1014 */     HashMap hash = new HashMap();
/*  745:1015 */     for (int i = 0; i < interfaceTypes.length; i++) {
/*  746:1016 */       getMethods(hash, interfaceTypes[i]);
/*  747:     */     }
/*  748:1018 */     getMethods(hash, superClass);
/*  749:1019 */     return hash;
/*  750:     */   }
/*  751:     */   
/*  752:     */   private static void getMethods(HashMap hash, Class clazz)
/*  753:     */   {
/*  754:1023 */     Class[] ifs = clazz.getInterfaces();
/*  755:1024 */     for (int i = 0; i < ifs.length; i++) {
/*  756:1025 */       getMethods(hash, ifs[i]);
/*  757:     */     }
/*  758:1027 */     Class parent = clazz.getSuperclass();
/*  759:1028 */     if (parent != null) {
/*  760:1029 */       getMethods(hash, parent);
/*  761:     */     }
/*  762:1031 */     Method[] methods = SecurityActions.getDeclaredMethods(clazz);
/*  763:1032 */     for (int i = 0; i < methods.length; i++) {
/*  764:1033 */       if (!Modifier.isPrivate(methods[i].getModifiers()))
/*  765:     */       {
/*  766:1034 */         Method m = methods[i];
/*  767:1035 */         String key = m.getName() + ':' + RuntimeSupport.makeDescriptor(m);
/*  768:     */         
/*  769:     */ 
/*  770:1038 */         Method oldMethod = (Method)hash.put(key, methods[i]);
/*  771:1041 */         if ((null != oldMethod) && (Modifier.isPublic(oldMethod.getModifiers())) && (!Modifier.isPublic(methods[i].getModifiers()))) {
/*  772:1045 */           hash.put(key, oldMethod);
/*  773:     */         }
/*  774:     */       }
/*  775:     */     }
/*  776:     */   }
/*  777:     */   
/*  778:     */   private static String keyToDesc(String key)
/*  779:     */   {
/*  780:1051 */     return key.substring(key.indexOf(':') + 1);
/*  781:     */   }
/*  782:     */   
/*  783:     */   private static MethodInfo makeConstructor(String thisClassName, Constructor cons, ConstPool cp, Class superClass, boolean doHandlerInit)
/*  784:     */   {
/*  785:1056 */     String desc = RuntimeSupport.makeDescriptor(cons.getParameterTypes(), Void.TYPE);
/*  786:     */     
/*  787:1058 */     MethodInfo minfo = new MethodInfo(cp, "<init>", desc);
/*  788:1059 */     minfo.setAccessFlags(1);
/*  789:1060 */     setThrows(minfo, cp, cons.getExceptionTypes());
/*  790:1061 */     Bytecode code = new Bytecode(cp, 0, 0);
/*  791:1066 */     if (doHandlerInit)
/*  792:     */     {
/*  793:1067 */       code.addAload(0);
/*  794:1068 */       code.addGetstatic(thisClassName, "default_interceptor", HANDLER_TYPE);
/*  795:1069 */       code.addPutfield(thisClassName, "handler", HANDLER_TYPE);
/*  796:1070 */       code.addGetstatic(thisClassName, "default_interceptor", HANDLER_TYPE);
/*  797:1071 */       code.addOpcode(199);
/*  798:1072 */       code.addIndex(10);
/*  799:     */     }
/*  800:1076 */     code.addAload(0);
/*  801:1077 */     code.addGetstatic("javassist.util.proxy.RuntimeSupport", "default_interceptor", HANDLER_TYPE);
/*  802:1078 */     code.addPutfield(thisClassName, "handler", HANDLER_TYPE);
/*  803:1079 */     int pc = code.currentPc();
/*  804:     */     
/*  805:1081 */     code.addAload(0);
/*  806:1082 */     int s = addLoadParameters(code, cons.getParameterTypes(), 1);
/*  807:1083 */     code.addInvokespecial(superClass.getName(), "<init>", desc);
/*  808:1084 */     code.addOpcode(177);
/*  809:1085 */     code.setMaxLocals(s + 1);
/*  810:1086 */     CodeAttribute ca = code.toCodeAttribute();
/*  811:1087 */     minfo.setCodeAttribute(ca);
/*  812:     */     
/*  813:1089 */     StackMapTable.Writer writer = new StackMapTable.Writer(32);
/*  814:1090 */     writer.sameFrame(pc);
/*  815:1091 */     ca.setAttribute(writer.toStackMapTable(cp));
/*  816:1092 */     return minfo;
/*  817:     */   }
/*  818:     */   
/*  819:     */   private static MethodInfo makeDelegator(Method meth, String desc, ConstPool cp, Class declClass, String delegatorName)
/*  820:     */   {
/*  821:1097 */     MethodInfo delegator = new MethodInfo(cp, delegatorName, desc);
/*  822:1098 */     delegator.setAccessFlags(0x11 | meth.getModifiers() & 0xFFFFFAD9);
/*  823:     */     
/*  824:     */ 
/*  825:     */ 
/*  826:     */ 
/*  827:     */ 
/*  828:1104 */     setThrows(delegator, cp, meth);
/*  829:1105 */     Bytecode code = new Bytecode(cp, 0, 0);
/*  830:1106 */     code.addAload(0);
/*  831:1107 */     int s = addLoadParameters(code, meth.getParameterTypes(), 1);
/*  832:1108 */     code.addInvokespecial(declClass.getName(), meth.getName(), desc);
/*  833:1109 */     addReturn(code, meth.getReturnType());
/*  834:1110 */     code.setMaxLocals(++s);
/*  835:1111 */     delegator.setCodeAttribute(code.toCodeAttribute());
/*  836:1112 */     return delegator;
/*  837:     */   }
/*  838:     */   
/*  839:     */   private static MethodInfo makeForwarder(String thisClassName, Method meth, String desc, ConstPool cp, Class declClass, String delegatorName, int index)
/*  840:     */   {
/*  841:1121 */     MethodInfo forwarder = new MethodInfo(cp, meth.getName(), desc);
/*  842:1122 */     forwarder.setAccessFlags(0x10 | meth.getModifiers() & 0xFFFFFADF);
/*  843:     */     
/*  844:     */ 
/*  845:     */ 
/*  846:1126 */     setThrows(forwarder, cp, meth);
/*  847:1127 */     int args = Descriptor.paramSize(desc);
/*  848:1128 */     Bytecode code = new Bytecode(cp, 0, args + 2);
/*  849:     */     
/*  850:     */ 
/*  851:     */ 
/*  852:     */ 
/*  853:     */ 
/*  854:     */ 
/*  855:     */ 
/*  856:     */ 
/*  857:     */ 
/*  858:     */ 
/*  859:     */ 
/*  860:1140 */     int origIndex = index * 2;
/*  861:1141 */     int delIndex = index * 2 + 1;
/*  862:1142 */     int arrayVar = args + 1;
/*  863:1143 */     code.addGetstatic(thisClassName, "_methods_", "[Ljava/lang/reflect/Method;");
/*  864:1144 */     code.addAstore(arrayVar);
/*  865:     */     
/*  866:1146 */     callFind2Methods(code, meth.getName(), delegatorName, origIndex, desc, arrayVar);
/*  867:     */     
/*  868:1148 */     code.addAload(0);
/*  869:1149 */     code.addGetfield(thisClassName, "handler", HANDLER_TYPE);
/*  870:1150 */     code.addAload(0);
/*  871:     */     
/*  872:1152 */     code.addAload(arrayVar);
/*  873:1153 */     code.addIconst(origIndex);
/*  874:1154 */     code.addOpcode(50);
/*  875:     */     
/*  876:1156 */     code.addAload(arrayVar);
/*  877:1157 */     code.addIconst(delIndex);
/*  878:1158 */     code.addOpcode(50);
/*  879:     */     
/*  880:1160 */     makeParameterList(code, meth.getParameterTypes());
/*  881:1161 */     code.addInvokeinterface(MethodHandler.class.getName(), "invoke", "(Ljava/lang/Object;Ljava/lang/reflect/Method;Ljava/lang/reflect/Method;[Ljava/lang/Object;)Ljava/lang/Object;", 5);
/*  882:     */     
/*  883:     */ 
/*  884:1164 */     Class retType = meth.getReturnType();
/*  885:1165 */     addUnwrapper(code, retType);
/*  886:1166 */     addReturn(code, retType);
/*  887:     */     
/*  888:1168 */     CodeAttribute ca = code.toCodeAttribute();
/*  889:1169 */     forwarder.setCodeAttribute(ca);
/*  890:1170 */     return forwarder;
/*  891:     */   }
/*  892:     */   
/*  893:     */   private static void setThrows(MethodInfo minfo, ConstPool cp, Method orig)
/*  894:     */   {
/*  895:1174 */     Class[] exceptions = orig.getExceptionTypes();
/*  896:1175 */     setThrows(minfo, cp, exceptions);
/*  897:     */   }
/*  898:     */   
/*  899:     */   private static void setThrows(MethodInfo minfo, ConstPool cp, Class[] exceptions)
/*  900:     */   {
/*  901:1180 */     if (exceptions.length == 0) {
/*  902:1181 */       return;
/*  903:     */     }
/*  904:1183 */     String[] list = new String[exceptions.length];
/*  905:1184 */     for (int i = 0; i < exceptions.length; i++) {
/*  906:1185 */       list[i] = exceptions[i].getName();
/*  907:     */     }
/*  908:1187 */     ExceptionsAttribute ea = new ExceptionsAttribute(cp);
/*  909:1188 */     ea.setExceptions(list);
/*  910:1189 */     minfo.setExceptionsAttribute(ea);
/*  911:     */   }
/*  912:     */   
/*  913:     */   private static int addLoadParameters(Bytecode code, Class[] params, int offset)
/*  914:     */   {
/*  915:1194 */     int stacksize = 0;
/*  916:1195 */     int n = params.length;
/*  917:1196 */     for (int i = 0; i < n; i++) {
/*  918:1197 */       stacksize += addLoad(code, stacksize + offset, params[i]);
/*  919:     */     }
/*  920:1199 */     return stacksize;
/*  921:     */   }
/*  922:     */   
/*  923:     */   private static int addLoad(Bytecode code, int n, Class type)
/*  924:     */   {
/*  925:1203 */     if (type.isPrimitive())
/*  926:     */     {
/*  927:1204 */       if (type == Long.TYPE)
/*  928:     */       {
/*  929:1205 */         code.addLload(n);
/*  930:1206 */         return 2;
/*  931:     */       }
/*  932:1208 */       if (type == Float.TYPE)
/*  933:     */       {
/*  934:1209 */         code.addFload(n);
/*  935:     */       }
/*  936:     */       else
/*  937:     */       {
/*  938:1210 */         if (type == Double.TYPE)
/*  939:     */         {
/*  940:1211 */           code.addDload(n);
/*  941:1212 */           return 2;
/*  942:     */         }
/*  943:1215 */         code.addIload(n);
/*  944:     */       }
/*  945:     */     }
/*  946:     */     else
/*  947:     */     {
/*  948:1218 */       code.addAload(n);
/*  949:     */     }
/*  950:1220 */     return 1;
/*  951:     */   }
/*  952:     */   
/*  953:     */   private static int addReturn(Bytecode code, Class type)
/*  954:     */   {
/*  955:1224 */     if (type.isPrimitive())
/*  956:     */     {
/*  957:1225 */       if (type == Long.TYPE)
/*  958:     */       {
/*  959:1226 */         code.addOpcode(173);
/*  960:1227 */         return 2;
/*  961:     */       }
/*  962:1229 */       if (type == Float.TYPE)
/*  963:     */       {
/*  964:1230 */         code.addOpcode(174);
/*  965:     */       }
/*  966:     */       else
/*  967:     */       {
/*  968:1231 */         if (type == Double.TYPE)
/*  969:     */         {
/*  970:1232 */           code.addOpcode(175);
/*  971:1233 */           return 2;
/*  972:     */         }
/*  973:1235 */         if (type == Void.TYPE)
/*  974:     */         {
/*  975:1236 */           code.addOpcode(177);
/*  976:1237 */           return 0;
/*  977:     */         }
/*  978:1240 */         code.addOpcode(172);
/*  979:     */       }
/*  980:     */     }
/*  981:     */     else
/*  982:     */     {
/*  983:1243 */       code.addOpcode(176);
/*  984:     */     }
/*  985:1245 */     return 1;
/*  986:     */   }
/*  987:     */   
/*  988:     */   private static void makeParameterList(Bytecode code, Class[] params)
/*  989:     */   {
/*  990:1249 */     int regno = 1;
/*  991:1250 */     int n = params.length;
/*  992:1251 */     code.addIconst(n);
/*  993:1252 */     code.addAnewarray("java/lang/Object");
/*  994:1253 */     for (int i = 0; i < n; i++)
/*  995:     */     {
/*  996:1254 */       code.addOpcode(89);
/*  997:1255 */       code.addIconst(i);
/*  998:1256 */       Class type = params[i];
/*  999:1257 */       if (type.isPrimitive())
/* 1000:     */       {
/* 1001:1258 */         regno = makeWrapper(code, type, regno);
/* 1002:     */       }
/* 1003:     */       else
/* 1004:     */       {
/* 1005:1260 */         code.addAload(regno);
/* 1006:1261 */         regno++;
/* 1007:     */       }
/* 1008:1264 */       code.addOpcode(83);
/* 1009:     */     }
/* 1010:     */   }
/* 1011:     */   
/* 1012:     */   private static int makeWrapper(Bytecode code, Class type, int regno)
/* 1013:     */   {
/* 1014:1269 */     int index = FactoryHelper.typeIndex(type);
/* 1015:1270 */     String wrapper = FactoryHelper.wrapperTypes[index];
/* 1016:1271 */     code.addNew(wrapper);
/* 1017:1272 */     code.addOpcode(89);
/* 1018:1273 */     addLoad(code, regno, type);
/* 1019:1274 */     code.addInvokespecial(wrapper, "<init>", FactoryHelper.wrapperDesc[index]);
/* 1020:     */     
/* 1021:1276 */     return regno + FactoryHelper.dataSize[index];
/* 1022:     */   }
/* 1023:     */   
/* 1024:     */   private static void callFind2Methods(Bytecode code, String superMethod, String thisMethod, int index, String desc, int arrayVar)
/* 1025:     */   {
/* 1026:1284 */     String findClass = RuntimeSupport.class.getName();
/* 1027:1285 */     String findDesc = "(Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;[Ljava/lang/reflect/Method;)V";
/* 1028:     */     
/* 1029:     */ 
/* 1030:1288 */     code.addAload(0);
/* 1031:1289 */     code.addLdc(superMethod);
/* 1032:1290 */     if (thisMethod == null) {
/* 1033:1291 */       code.addOpcode(1);
/* 1034:     */     } else {
/* 1035:1293 */       code.addLdc(thisMethod);
/* 1036:     */     }
/* 1037:1295 */     code.addIconst(index);
/* 1038:1296 */     code.addLdc(desc);
/* 1039:1297 */     code.addAload(arrayVar);
/* 1040:1298 */     code.addInvokestatic(findClass, "find2Methods", findDesc);
/* 1041:     */   }
/* 1042:     */   
/* 1043:     */   private static void addUnwrapper(Bytecode code, Class type)
/* 1044:     */   {
/* 1045:1302 */     if (type.isPrimitive())
/* 1046:     */     {
/* 1047:1303 */       if (type == Void.TYPE)
/* 1048:     */       {
/* 1049:1304 */         code.addOpcode(87);
/* 1050:     */       }
/* 1051:     */       else
/* 1052:     */       {
/* 1053:1306 */         int index = FactoryHelper.typeIndex(type);
/* 1054:1307 */         String wrapper = FactoryHelper.wrapperTypes[index];
/* 1055:1308 */         code.addCheckcast(wrapper);
/* 1056:1309 */         code.addInvokevirtual(wrapper, FactoryHelper.unwarpMethods[index], FactoryHelper.unwrapDesc[index]);
/* 1057:     */       }
/* 1058:     */     }
/* 1059:     */     else {
/* 1060:1315 */       code.addCheckcast(type.getName());
/* 1061:     */     }
/* 1062:     */   }
/* 1063:     */   
/* 1064:     */   private static MethodInfo makeWriteReplace(ConstPool cp)
/* 1065:     */   {
/* 1066:1319 */     MethodInfo minfo = new MethodInfo(cp, "writeReplace", "()Ljava/lang/Object;");
/* 1067:1320 */     String[] list = new String[1];
/* 1068:1321 */     list[0] = "java.io.ObjectStreamException";
/* 1069:1322 */     ExceptionsAttribute ea = new ExceptionsAttribute(cp);
/* 1070:1323 */     ea.setExceptions(list);
/* 1071:1324 */     minfo.setExceptionsAttribute(ea);
/* 1072:1325 */     Bytecode code = new Bytecode(cp, 0, 1);
/* 1073:1326 */     code.addAload(0);
/* 1074:1327 */     code.addInvokestatic("javassist.util.proxy.RuntimeSupport", "makeSerializedProxy", "(Ljava/lang/Object;)Ljavassist/util/proxy/SerializedProxy;");
/* 1075:     */     
/* 1076:     */ 
/* 1077:1330 */     code.addOpcode(176);
/* 1078:1331 */     minfo.setCodeAttribute(code.toCodeAttribute());
/* 1079:1332 */     return minfo;
/* 1080:     */   }
/* 1081:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.util.proxy.ProxyFactory
 * JD-Core Version:    0.7.0.1
 */