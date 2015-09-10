/*    1:     */ package javassist;
/*    2:     */ 
/*    3:     */ import java.io.BufferedOutputStream;
/*    4:     */ import java.io.ByteArrayOutputStream;
/*    5:     */ import java.io.DataOutputStream;
/*    6:     */ import java.io.File;
/*    7:     */ import java.io.FileOutputStream;
/*    8:     */ import java.io.IOException;
/*    9:     */ import java.io.OutputStream;
/*   10:     */ import java.io.PrintStream;
/*   11:     */ import java.net.URL;
/*   12:     */ import java.security.ProtectionDomain;
/*   13:     */ import java.util.Collection;
/*   14:     */ import javassist.bytecode.ClassFile;
/*   15:     */ import javassist.bytecode.Descriptor;
/*   16:     */ import javassist.compiler.AccessorMaker;
/*   17:     */ import javassist.expr.ExprEditor;
/*   18:     */ 
/*   19:     */ public abstract class CtClass
/*   20:     */ {
/*   21:     */   protected String qualifiedName;
/*   22:     */   public static final String version = "3.12.0.GA";
/*   23:     */   static final String javaLangObject = "java.lang.Object";
/*   24:     */   public static CtClass booleanType;
/*   25:     */   public static CtClass charType;
/*   26:     */   public static CtClass byteType;
/*   27:     */   public static CtClass shortType;
/*   28:     */   public static CtClass intType;
/*   29:     */   public static CtClass longType;
/*   30:     */   public static CtClass floatType;
/*   31:     */   public static CtClass doubleType;
/*   32:     */   public static CtClass voidType;
/*   33:     */   
/*   34:     */   public static void main(String[] args)
/*   35:     */   {
/*   36:  65 */     System.out.println("Javassist version 3.12.0.GA");
/*   37:  66 */     System.out.println("Copyright (C) 1999-2010 Shigeru Chiba. All Rights Reserved.");
/*   38:     */   }
/*   39:     */   
/*   40: 129 */   static CtClass[] primitiveTypes = new CtClass[9];
/*   41:     */   
/*   42:     */   static
/*   43:     */   {
/*   44: 131 */     booleanType = new CtPrimitiveType("boolean", 'Z', "java.lang.Boolean", "booleanValue", "()Z", 172, 4, 1);
/*   45:     */     
/*   46:     */ 
/*   47:     */ 
/*   48: 135 */     primitiveTypes[0] = booleanType;
/*   49:     */     
/*   50: 137 */     charType = new CtPrimitiveType("char", 'C', "java.lang.Character", "charValue", "()C", 172, 5, 1);
/*   51:     */     
/*   52:     */ 
/*   53: 140 */     primitiveTypes[1] = charType;
/*   54:     */     
/*   55: 142 */     byteType = new CtPrimitiveType("byte", 'B', "java.lang.Byte", "byteValue", "()B", 172, 8, 1);
/*   56:     */     
/*   57:     */ 
/*   58: 145 */     primitiveTypes[2] = byteType;
/*   59:     */     
/*   60: 147 */     shortType = new CtPrimitiveType("short", 'S', "java.lang.Short", "shortValue", "()S", 172, 9, 1);
/*   61:     */     
/*   62:     */ 
/*   63: 150 */     primitiveTypes[3] = shortType;
/*   64:     */     
/*   65: 152 */     intType = new CtPrimitiveType("int", 'I', "java.lang.Integer", "intValue", "()I", 172, 10, 1);
/*   66:     */     
/*   67:     */ 
/*   68: 155 */     primitiveTypes[4] = intType;
/*   69:     */     
/*   70: 157 */     longType = new CtPrimitiveType("long", 'J', "java.lang.Long", "longValue", "()J", 173, 11, 2);
/*   71:     */     
/*   72:     */ 
/*   73: 160 */     primitiveTypes[5] = longType;
/*   74:     */     
/*   75: 162 */     floatType = new CtPrimitiveType("float", 'F', "java.lang.Float", "floatValue", "()F", 174, 6, 1);
/*   76:     */     
/*   77:     */ 
/*   78: 165 */     primitiveTypes[6] = floatType;
/*   79:     */     
/*   80: 167 */     doubleType = new CtPrimitiveType("double", 'D', "java.lang.Double", "doubleValue", "()D", 175, 7, 2);
/*   81:     */     
/*   82:     */ 
/*   83: 170 */     primitiveTypes[7] = doubleType;
/*   84:     */     
/*   85: 172 */     voidType = new CtPrimitiveType("void", 'V', "java.lang.Void", null, null, 177, 0, 0);
/*   86:     */     
/*   87: 174 */     primitiveTypes[8] = voidType;
/*   88:     */   }
/*   89:     */   
/*   90:     */   protected CtClass(String name)
/*   91:     */   {
/*   92: 178 */     this.qualifiedName = name;
/*   93:     */   }
/*   94:     */   
/*   95:     */   public String toString()
/*   96:     */   {
/*   97: 185 */     StringBuffer buf = new StringBuffer(getClass().getName());
/*   98: 186 */     buf.append("@");
/*   99: 187 */     buf.append(Integer.toHexString(hashCode()));
/*  100: 188 */     buf.append("[");
/*  101: 189 */     extendToString(buf);
/*  102: 190 */     buf.append("]");
/*  103: 191 */     return buf.toString();
/*  104:     */   }
/*  105:     */   
/*  106:     */   protected void extendToString(StringBuffer buffer)
/*  107:     */   {
/*  108: 199 */     buffer.append(getName());
/*  109:     */   }
/*  110:     */   
/*  111:     */   public ClassPool getClassPool()
/*  112:     */   {
/*  113: 205 */     return null;
/*  114:     */   }
/*  115:     */   
/*  116:     */   public ClassFile getClassFile()
/*  117:     */   {
/*  118: 214 */     checkModify();
/*  119: 215 */     return getClassFile2();
/*  120:     */   }
/*  121:     */   
/*  122:     */   public ClassFile getClassFile2()
/*  123:     */   {
/*  124: 236 */     return null;
/*  125:     */   }
/*  126:     */   
/*  127:     */   public AccessorMaker getAccessorMaker()
/*  128:     */   {
/*  129: 242 */     return null;
/*  130:     */   }
/*  131:     */   
/*  132:     */   public URL getURL()
/*  133:     */     throws NotFoundException
/*  134:     */   {
/*  135: 249 */     throw new NotFoundException(getName());
/*  136:     */   }
/*  137:     */   
/*  138:     */   public boolean isModified()
/*  139:     */   {
/*  140: 255 */     return false;
/*  141:     */   }
/*  142:     */   
/*  143:     */   public boolean isFrozen()
/*  144:     */   {
/*  145: 264 */     return true;
/*  146:     */   }
/*  147:     */   
/*  148:     */   void checkModify()
/*  149:     */     throws RuntimeException
/*  150:     */   {
/*  151: 278 */     if (isFrozen()) {
/*  152: 279 */       throw new RuntimeException(getName() + " class is frozen");
/*  153:     */     }
/*  154:     */   }
/*  155:     */   
/*  156:     */   public void defrost()
/*  157:     */   {
/*  158: 300 */     throw new RuntimeException("cannot defrost " + getName());
/*  159:     */   }
/*  160:     */   
/*  161:     */   public boolean isPrimitive()
/*  162:     */   {
/*  163: 308 */     return false;
/*  164:     */   }
/*  165:     */   
/*  166:     */   public boolean isArray()
/*  167:     */   {
/*  168: 314 */     return false;
/*  169:     */   }
/*  170:     */   
/*  171:     */   public CtClass getComponentType()
/*  172:     */     throws NotFoundException
/*  173:     */   {
/*  174: 322 */     return null;
/*  175:     */   }
/*  176:     */   
/*  177:     */   public boolean subtypeOf(CtClass clazz)
/*  178:     */     throws NotFoundException
/*  179:     */   {
/*  180: 331 */     return (this == clazz) || (getName().equals(clazz.getName()));
/*  181:     */   }
/*  182:     */   
/*  183:     */   public String getName()
/*  184:     */   {
/*  185: 337 */     return this.qualifiedName;
/*  186:     */   }
/*  187:     */   
/*  188:     */   public final String getSimpleName()
/*  189:     */   {
/*  190: 343 */     String qname = this.qualifiedName;
/*  191: 344 */     int index = qname.lastIndexOf('.');
/*  192: 345 */     if (index < 0) {
/*  193: 346 */       return qname;
/*  194:     */     }
/*  195: 348 */     return qname.substring(index + 1);
/*  196:     */   }
/*  197:     */   
/*  198:     */   public final String getPackageName()
/*  199:     */   {
/*  200: 355 */     String qname = this.qualifiedName;
/*  201: 356 */     int index = qname.lastIndexOf('.');
/*  202: 357 */     if (index < 0) {
/*  203: 358 */       return null;
/*  204:     */     }
/*  205: 360 */     return qname.substring(0, index);
/*  206:     */   }
/*  207:     */   
/*  208:     */   public void setName(String name)
/*  209:     */   {
/*  210: 369 */     checkModify();
/*  211: 370 */     if (name != null) {
/*  212: 371 */       this.qualifiedName = name;
/*  213:     */     }
/*  214:     */   }
/*  215:     */   
/*  216:     */   public void replaceClassName(String oldName, String newName)
/*  217:     */   {
/*  218: 382 */     checkModify();
/*  219:     */   }
/*  220:     */   
/*  221:     */   public void replaceClassName(ClassMap map)
/*  222:     */   {
/*  223: 403 */     checkModify();
/*  224:     */   }
/*  225:     */   
/*  226:     */   public synchronized Collection getRefClasses()
/*  227:     */   {
/*  228: 414 */     ClassFile cf = getClassFile2();
/*  229: 415 */     if (cf != null)
/*  230:     */     {
/*  231: 416 */       ClassMap cm = new ClassMap()
/*  232:     */       {
/*  233:     */         public void put(String oldname, String newname)
/*  234:     */         {
/*  235: 418 */           put0(oldname, newname);
/*  236:     */         }
/*  237:     */         
/*  238:     */         public Object get(Object jvmClassName)
/*  239:     */         {
/*  240: 422 */           String n = toJavaName((String)jvmClassName);
/*  241: 423 */           put0(n, n);
/*  242: 424 */           return null;
/*  243:     */         }
/*  244:     */         
/*  245:     */         public void fix(String name) {}
/*  246: 428 */       };
/*  247: 429 */       cf.renameClass(cm);
/*  248: 430 */       return cm.values();
/*  249:     */     }
/*  250: 433 */     return null;
/*  251:     */   }
/*  252:     */   
/*  253:     */   public boolean isInterface()
/*  254:     */   {
/*  255: 441 */     return false;
/*  256:     */   }
/*  257:     */   
/*  258:     */   public boolean isAnnotation()
/*  259:     */   {
/*  260: 451 */     return false;
/*  261:     */   }
/*  262:     */   
/*  263:     */   public boolean isEnum()
/*  264:     */   {
/*  265: 461 */     return false;
/*  266:     */   }
/*  267:     */   
/*  268:     */   public int getModifiers()
/*  269:     */   {
/*  270: 474 */     return 0;
/*  271:     */   }
/*  272:     */   
/*  273:     */   public boolean hasAnnotation(Class clz)
/*  274:     */   {
/*  275: 485 */     return false;
/*  276:     */   }
/*  277:     */   
/*  278:     */   public Object getAnnotation(Class clz)
/*  279:     */     throws ClassNotFoundException
/*  280:     */   {
/*  281: 500 */     return null;
/*  282:     */   }
/*  283:     */   
/*  284:     */   public Object[] getAnnotations()
/*  285:     */     throws ClassNotFoundException
/*  286:     */   {
/*  287: 515 */     return new Object[0];
/*  288:     */   }
/*  289:     */   
/*  290:     */   public Object[] getAvailableAnnotations()
/*  291:     */   {
/*  292: 530 */     return new Object[0];
/*  293:     */   }
/*  294:     */   
/*  295:     */   public CtClass[] getNestedClasses()
/*  296:     */     throws NotFoundException
/*  297:     */   {
/*  298: 541 */     return new CtClass[0];
/*  299:     */   }
/*  300:     */   
/*  301:     */   public void setModifiers(int mod)
/*  302:     */   {
/*  303: 556 */     checkModify();
/*  304:     */   }
/*  305:     */   
/*  306:     */   public boolean subclassOf(CtClass superclass)
/*  307:     */   {
/*  308: 568 */     return false;
/*  309:     */   }
/*  310:     */   
/*  311:     */   public CtClass getSuperclass()
/*  312:     */     throws NotFoundException
/*  313:     */   {
/*  314: 584 */     return null;
/*  315:     */   }
/*  316:     */   
/*  317:     */   public void setSuperclass(CtClass clazz)
/*  318:     */     throws CannotCompileException
/*  319:     */   {
/*  320: 601 */     checkModify();
/*  321:     */   }
/*  322:     */   
/*  323:     */   public CtClass[] getInterfaces()
/*  324:     */     throws NotFoundException
/*  325:     */   {
/*  326: 610 */     return new CtClass[0];
/*  327:     */   }
/*  328:     */   
/*  329:     */   public void setInterfaces(CtClass[] list)
/*  330:     */   {
/*  331: 623 */     checkModify();
/*  332:     */   }
/*  333:     */   
/*  334:     */   public void addInterface(CtClass anInterface)
/*  335:     */   {
/*  336: 632 */     checkModify();
/*  337:     */   }
/*  338:     */   
/*  339:     */   public CtClass getDeclaringClass()
/*  340:     */     throws NotFoundException
/*  341:     */   {
/*  342: 642 */     return null;
/*  343:     */   }
/*  344:     */   
/*  345:     */   public CtMethod getEnclosingMethod()
/*  346:     */     throws NotFoundException
/*  347:     */   {
/*  348: 653 */     return null;
/*  349:     */   }
/*  350:     */   
/*  351:     */   public CtClass makeNestedClass(String name, boolean isStatic)
/*  352:     */   {
/*  353: 668 */     throw new RuntimeException(getName() + " is not a class");
/*  354:     */   }
/*  355:     */   
/*  356:     */   public CtField[] getFields()
/*  357:     */   {
/*  358: 677 */     return new CtField[0];
/*  359:     */   }
/*  360:     */   
/*  361:     */   public CtField getField(String name)
/*  362:     */     throws NotFoundException
/*  363:     */   {
/*  364: 684 */     throw new NotFoundException(name);
/*  365:     */   }
/*  366:     */   
/*  367:     */   CtField getField2(String name)
/*  368:     */   {
/*  369: 690 */     return null;
/*  370:     */   }
/*  371:     */   
/*  372:     */   public CtField[] getDeclaredFields()
/*  373:     */   {
/*  374: 698 */     return new CtField[0];
/*  375:     */   }
/*  376:     */   
/*  377:     */   public CtField getDeclaredField(String name)
/*  378:     */     throws NotFoundException
/*  379:     */   {
/*  380: 707 */     throw new NotFoundException(name);
/*  381:     */   }
/*  382:     */   
/*  383:     */   public CtBehavior[] getDeclaredBehaviors()
/*  384:     */   {
/*  385: 714 */     return new CtBehavior[0];
/*  386:     */   }
/*  387:     */   
/*  388:     */   public CtConstructor[] getConstructors()
/*  389:     */   {
/*  390: 722 */     return new CtConstructor[0];
/*  391:     */   }
/*  392:     */   
/*  393:     */   public CtConstructor getConstructor(String desc)
/*  394:     */     throws NotFoundException
/*  395:     */   {
/*  396: 738 */     throw new NotFoundException("no such a constructor");
/*  397:     */   }
/*  398:     */   
/*  399:     */   public CtConstructor[] getDeclaredConstructors()
/*  400:     */   {
/*  401: 747 */     return new CtConstructor[0];
/*  402:     */   }
/*  403:     */   
/*  404:     */   public CtConstructor getDeclaredConstructor(CtClass[] params)
/*  405:     */     throws NotFoundException
/*  406:     */   {
/*  407: 758 */     String desc = Descriptor.ofConstructor(params);
/*  408: 759 */     return getConstructor(desc);
/*  409:     */   }
/*  410:     */   
/*  411:     */   public CtConstructor getClassInitializer()
/*  412:     */   {
/*  413: 772 */     return null;
/*  414:     */   }
/*  415:     */   
/*  416:     */   public CtMethod[] getMethods()
/*  417:     */   {
/*  418: 782 */     return new CtMethod[0];
/*  419:     */   }
/*  420:     */   
/*  421:     */   public CtMethod getMethod(String name, String desc)
/*  422:     */     throws NotFoundException
/*  423:     */   {
/*  424: 800 */     throw new NotFoundException(name);
/*  425:     */   }
/*  426:     */   
/*  427:     */   public CtMethod[] getDeclaredMethods()
/*  428:     */   {
/*  429: 810 */     return new CtMethod[0];
/*  430:     */   }
/*  431:     */   
/*  432:     */   public CtMethod getDeclaredMethod(String name, CtClass[] params)
/*  433:     */     throws NotFoundException
/*  434:     */   {
/*  435: 826 */     throw new NotFoundException(name);
/*  436:     */   }
/*  437:     */   
/*  438:     */   public CtMethod getDeclaredMethod(String name)
/*  439:     */     throws NotFoundException
/*  440:     */   {
/*  441: 839 */     throw new NotFoundException(name);
/*  442:     */   }
/*  443:     */   
/*  444:     */   public CtConstructor makeClassInitializer()
/*  445:     */     throws CannotCompileException
/*  446:     */   {
/*  447: 852 */     throw new CannotCompileException("not a class");
/*  448:     */   }
/*  449:     */   
/*  450:     */   public void addConstructor(CtConstructor c)
/*  451:     */     throws CannotCompileException
/*  452:     */   {
/*  453: 864 */     checkModify();
/*  454:     */   }
/*  455:     */   
/*  456:     */   public void removeConstructor(CtConstructor c)
/*  457:     */     throws NotFoundException
/*  458:     */   {
/*  459: 874 */     checkModify();
/*  460:     */   }
/*  461:     */   
/*  462:     */   public void addMethod(CtMethod m)
/*  463:     */     throws CannotCompileException
/*  464:     */   {
/*  465: 881 */     checkModify();
/*  466:     */   }
/*  467:     */   
/*  468:     */   public void removeMethod(CtMethod m)
/*  469:     */     throws NotFoundException
/*  470:     */   {
/*  471: 891 */     checkModify();
/*  472:     */   }
/*  473:     */   
/*  474:     */   public void addField(CtField f)
/*  475:     */     throws CannotCompileException
/*  476:     */   {
/*  477: 904 */     addField(f, (CtField.Initializer)null);
/*  478:     */   }
/*  479:     */   
/*  480:     */   public void addField(CtField f, String init)
/*  481:     */     throws CannotCompileException
/*  482:     */   {
/*  483: 940 */     checkModify();
/*  484:     */   }
/*  485:     */   
/*  486:     */   public void addField(CtField f, CtField.Initializer init)
/*  487:     */     throws CannotCompileException
/*  488:     */   {
/*  489: 968 */     checkModify();
/*  490:     */   }
/*  491:     */   
/*  492:     */   public void removeField(CtField f)
/*  493:     */     throws NotFoundException
/*  494:     */   {
/*  495: 978 */     checkModify();
/*  496:     */   }
/*  497:     */   
/*  498:     */   public byte[] getAttribute(String name)
/*  499:     */   {
/*  500: 999 */     return null;
/*  501:     */   }
/*  502:     */   
/*  503:     */   public void setAttribute(String name, byte[] data)
/*  504:     */   {
/*  505:1025 */     checkModify();
/*  506:     */   }
/*  507:     */   
/*  508:     */   public void instrument(CodeConverter converter)
/*  509:     */     throws CannotCompileException
/*  510:     */   {
/*  511:1039 */     checkModify();
/*  512:     */   }
/*  513:     */   
/*  514:     */   public void instrument(ExprEditor editor)
/*  515:     */     throws CannotCompileException
/*  516:     */   {
/*  517:1053 */     checkModify();
/*  518:     */   }
/*  519:     */   
/*  520:     */   public Class toClass()
/*  521:     */     throws CannotCompileException
/*  522:     */   {
/*  523:1079 */     return getClassPool().toClass(this);
/*  524:     */   }
/*  525:     */   
/*  526:     */   public Class toClass(ClassLoader loader, ProtectionDomain domain)
/*  527:     */     throws CannotCompileException
/*  528:     */   {
/*  529:1116 */     ClassPool cp = getClassPool();
/*  530:1117 */     if (loader == null) {
/*  531:1118 */       loader = cp.getClassLoader();
/*  532:     */     }
/*  533:1120 */     return cp.toClass(this, loader, domain);
/*  534:     */   }
/*  535:     */   
/*  536:     */   /**
/*  537:     */    * @deprecated
/*  538:     */    */
/*  539:     */   public final Class toClass(ClassLoader loader)
/*  540:     */     throws CannotCompileException
/*  541:     */   {
/*  542:1135 */     return getClassPool().toClass(this, loader);
/*  543:     */   }
/*  544:     */   
/*  545:     */   public void detach()
/*  546:     */   {
/*  547:1151 */     ClassPool cp = getClassPool();
/*  548:1152 */     CtClass obj = cp.removeCached(getName());
/*  549:1153 */     if (obj != this) {
/*  550:1154 */       cp.cacheCtClass(getName(), obj, false);
/*  551:     */     }
/*  552:     */   }
/*  553:     */   
/*  554:     */   public boolean stopPruning(boolean stop)
/*  555:     */   {
/*  556:1181 */     return true;
/*  557:     */   }
/*  558:     */   
/*  559:     */   public byte[] toBytecode()
/*  560:     */     throws IOException, CannotCompileException
/*  561:     */   {
/*  562:1243 */     ByteArrayOutputStream barray = new ByteArrayOutputStream();
/*  563:1244 */     DataOutputStream out = new DataOutputStream(barray);
/*  564:     */     try
/*  565:     */     {
/*  566:1246 */       toBytecode(out);
/*  567:     */     }
/*  568:     */     finally
/*  569:     */     {
/*  570:1249 */       out.close();
/*  571:     */     }
/*  572:1252 */     return barray.toByteArray();
/*  573:     */   }
/*  574:     */   
/*  575:     */   public void writeFile()
/*  576:     */     throws NotFoundException, IOException, CannotCompileException
/*  577:     */   {
/*  578:1266 */     writeFile(".");
/*  579:     */   }
/*  580:     */   
/*  581:     */   public void writeFile(String directoryName)
/*  582:     */     throws CannotCompileException, IOException
/*  583:     */   {
/*  584:1281 */     String classname = getName();
/*  585:1282 */     String filename = directoryName + File.separatorChar + classname.replace('.', File.separatorChar) + ".class";
/*  586:     */     
/*  587:1284 */     int pos = filename.lastIndexOf(File.separatorChar);
/*  588:1285 */     if (pos > 0)
/*  589:     */     {
/*  590:1286 */       String dir = filename.substring(0, pos);
/*  591:1287 */       if (!dir.equals(".")) {
/*  592:1288 */         new File(dir).mkdirs();
/*  593:     */       }
/*  594:     */     }
/*  595:1291 */     DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new DelayedFileOutputStream(filename)));
/*  596:     */     try
/*  597:     */     {
/*  598:1295 */       toBytecode(out);
/*  599:     */     }
/*  600:     */     finally
/*  601:     */     {
/*  602:1298 */       out.close();
/*  603:     */     }
/*  604:     */   }
/*  605:     */   
/*  606:     */   public void debugWriteFile()
/*  607:     */   {
/*  608:1310 */     debugWriteFile(".");
/*  609:     */   }
/*  610:     */   
/*  611:     */   public void debugWriteFile(String directoryName)
/*  612:     */   {
/*  613:     */     try
/*  614:     */     {
/*  615:1324 */       boolean p = stopPruning(true);
/*  616:1325 */       writeFile(directoryName);
/*  617:1326 */       defrost();
/*  618:1327 */       stopPruning(p);
/*  619:     */     }
/*  620:     */     catch (Exception e)
/*  621:     */     {
/*  622:1330 */       throw new RuntimeException(e);
/*  623:     */     }
/*  624:     */   }
/*  625:     */   
/*  626:     */   static class DelayedFileOutputStream
/*  627:     */     extends OutputStream
/*  628:     */   {
/*  629:     */     private FileOutputStream file;
/*  630:     */     private String filename;
/*  631:     */     
/*  632:     */     DelayedFileOutputStream(String name)
/*  633:     */     {
/*  634:1339 */       this.file = null;
/*  635:1340 */       this.filename = name;
/*  636:     */     }
/*  637:     */     
/*  638:     */     private void init()
/*  639:     */       throws IOException
/*  640:     */     {
/*  641:1344 */       if (this.file == null) {
/*  642:1345 */         this.file = new FileOutputStream(this.filename);
/*  643:     */       }
/*  644:     */     }
/*  645:     */     
/*  646:     */     public void write(int b)
/*  647:     */       throws IOException
/*  648:     */     {
/*  649:1349 */       init();
/*  650:1350 */       this.file.write(b);
/*  651:     */     }
/*  652:     */     
/*  653:     */     public void write(byte[] b)
/*  654:     */       throws IOException
/*  655:     */     {
/*  656:1354 */       init();
/*  657:1355 */       this.file.write(b);
/*  658:     */     }
/*  659:     */     
/*  660:     */     public void write(byte[] b, int off, int len)
/*  661:     */       throws IOException
/*  662:     */     {
/*  663:1359 */       init();
/*  664:1360 */       this.file.write(b, off, len);
/*  665:     */     }
/*  666:     */     
/*  667:     */     public void flush()
/*  668:     */       throws IOException
/*  669:     */     {
/*  670:1365 */       init();
/*  671:1366 */       this.file.flush();
/*  672:     */     }
/*  673:     */     
/*  674:     */     public void close()
/*  675:     */       throws IOException
/*  676:     */     {
/*  677:1370 */       init();
/*  678:1371 */       this.file.close();
/*  679:     */     }
/*  680:     */   }
/*  681:     */   
/*  682:     */   public void toBytecode(DataOutputStream out)
/*  683:     */     throws CannotCompileException, IOException
/*  684:     */   {
/*  685:1387 */     throw new CannotCompileException("not a class");
/*  686:     */   }
/*  687:     */   
/*  688:     */   public String makeUniqueName(String prefix)
/*  689:     */   {
/*  690:1400 */     throw new RuntimeException("not available in " + getName());
/*  691:     */   }
/*  692:     */   
/*  693:     */   public void freeze() {}
/*  694:     */   
/*  695:     */   public void prune() {}
/*  696:     */   
/*  697:     */   void incGetCounter() {}
/*  698:     */   
/*  699:     */   public void rebuildClassFile() {}
/*  700:     */   
/*  701:     */   void compress() {}
/*  702:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.CtClass
 * JD-Core Version:    0.7.0.1
 */