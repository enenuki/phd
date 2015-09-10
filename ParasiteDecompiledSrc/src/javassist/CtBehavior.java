/*    1:     */ package javassist;
/*    2:     */ 
/*    3:     */ import java.util.List;
/*    4:     */ import javassist.bytecode.AccessFlag;
/*    5:     */ import javassist.bytecode.AnnotationsAttribute;
/*    6:     */ import javassist.bytecode.AttributeInfo;
/*    7:     */ import javassist.bytecode.BadBytecode;
/*    8:     */ import javassist.bytecode.Bytecode;
/*    9:     */ import javassist.bytecode.ClassFile;
/*   10:     */ import javassist.bytecode.CodeAttribute;
/*   11:     */ import javassist.bytecode.CodeAttribute.RuntimeCopyException;
/*   12:     */ import javassist.bytecode.CodeIterator;
/*   13:     */ import javassist.bytecode.CodeIterator.Gap;
/*   14:     */ import javassist.bytecode.ConstPool;
/*   15:     */ import javassist.bytecode.Descriptor;
/*   16:     */ import javassist.bytecode.ExceptionTable;
/*   17:     */ import javassist.bytecode.ExceptionsAttribute;
/*   18:     */ import javassist.bytecode.LineNumberAttribute;
/*   19:     */ import javassist.bytecode.LineNumberAttribute.Pc;
/*   20:     */ import javassist.bytecode.LocalVariableAttribute;
/*   21:     */ import javassist.bytecode.MethodInfo;
/*   22:     */ import javassist.bytecode.ParameterAnnotationsAttribute;
/*   23:     */ import javassist.bytecode.StackMap;
/*   24:     */ import javassist.bytecode.StackMapTable;
/*   25:     */ import javassist.compiler.CompileError;
/*   26:     */ import javassist.compiler.Javac;
/*   27:     */ import javassist.expr.ExprEditor;
/*   28:     */ 
/*   29:     */ public abstract class CtBehavior
/*   30:     */   extends CtMember
/*   31:     */ {
/*   32:     */   protected MethodInfo methodInfo;
/*   33:     */   
/*   34:     */   protected CtBehavior(CtClass clazz, MethodInfo minfo)
/*   35:     */   {
/*   36:  33 */     super(clazz);
/*   37:  34 */     this.methodInfo = minfo;
/*   38:     */   }
/*   39:     */   
/*   40:     */   void copy(CtBehavior src, boolean isCons, ClassMap map)
/*   41:     */     throws CannotCompileException
/*   42:     */   {
/*   43:  43 */     CtClass declaring = this.declaringClass;
/*   44:  44 */     MethodInfo srcInfo = src.methodInfo;
/*   45:  45 */     CtClass srcClass = src.getDeclaringClass();
/*   46:  46 */     ConstPool cp = declaring.getClassFile2().getConstPool();
/*   47:     */     
/*   48:  48 */     map = new ClassMap(map);
/*   49:  49 */     map.put(srcClass.getName(), declaring.getName());
/*   50:     */     try
/*   51:     */     {
/*   52:  51 */       boolean patch = false;
/*   53:  52 */       CtClass srcSuper = srcClass.getSuperclass();
/*   54:  53 */       CtClass destSuper = declaring.getSuperclass();
/*   55:  54 */       String destSuperName = null;
/*   56:  55 */       if ((srcSuper != null) && (destSuper != null))
/*   57:     */       {
/*   58:  56 */         String srcSuperName = srcSuper.getName();
/*   59:  57 */         destSuperName = destSuper.getName();
/*   60:  58 */         if (!srcSuperName.equals(destSuperName)) {
/*   61:  59 */           if (srcSuperName.equals("java.lang.Object")) {
/*   62:  60 */             patch = true;
/*   63:     */           } else {
/*   64:  62 */             map.putIfNone(srcSuperName, destSuperName);
/*   65:     */           }
/*   66:     */         }
/*   67:     */       }
/*   68:  66 */       this.methodInfo = new MethodInfo(cp, srcInfo.getName(), srcInfo, map);
/*   69:  67 */       if ((isCons) && (patch)) {
/*   70:  68 */         this.methodInfo.setSuperclass(destSuperName);
/*   71:     */       }
/*   72:     */     }
/*   73:     */     catch (NotFoundException e)
/*   74:     */     {
/*   75:  71 */       throw new CannotCompileException(e);
/*   76:     */     }
/*   77:     */     catch (BadBytecode e)
/*   78:     */     {
/*   79:  74 */       throw new CannotCompileException(e);
/*   80:     */     }
/*   81:     */   }
/*   82:     */   
/*   83:     */   protected void extendToString(StringBuffer buffer)
/*   84:     */   {
/*   85:  79 */     buffer.append(' ');
/*   86:  80 */     buffer.append(getName());
/*   87:  81 */     buffer.append(' ');
/*   88:  82 */     buffer.append(this.methodInfo.getDescriptor());
/*   89:     */   }
/*   90:     */   
/*   91:     */   public abstract String getLongName();
/*   92:     */   
/*   93:     */   public MethodInfo getMethodInfo()
/*   94:     */   {
/*   95:  98 */     this.declaringClass.checkModify();
/*   96:  99 */     return this.methodInfo;
/*   97:     */   }
/*   98:     */   
/*   99:     */   public MethodInfo getMethodInfo2()
/*  100:     */   {
/*  101: 121 */     return this.methodInfo;
/*  102:     */   }
/*  103:     */   
/*  104:     */   public int getModifiers()
/*  105:     */   {
/*  106: 131 */     return AccessFlag.toModifier(this.methodInfo.getAccessFlags());
/*  107:     */   }
/*  108:     */   
/*  109:     */   public void setModifiers(int mod)
/*  110:     */   {
/*  111: 144 */     this.declaringClass.checkModify();
/*  112: 145 */     this.methodInfo.setAccessFlags(AccessFlag.of(mod));
/*  113:     */   }
/*  114:     */   
/*  115:     */   public boolean hasAnnotation(Class clz)
/*  116:     */   {
/*  117: 157 */     MethodInfo mi = getMethodInfo2();
/*  118: 158 */     AnnotationsAttribute ainfo = (AnnotationsAttribute)mi.getAttribute("RuntimeInvisibleAnnotations");
/*  119:     */     
/*  120: 160 */     AnnotationsAttribute ainfo2 = (AnnotationsAttribute)mi.getAttribute("RuntimeVisibleAnnotations");
/*  121:     */     
/*  122: 162 */     return CtClassType.hasAnnotationType(clz, getDeclaringClass().getClassPool(), ainfo, ainfo2);
/*  123:     */   }
/*  124:     */   
/*  125:     */   public Object getAnnotation(Class clz)
/*  126:     */     throws ClassNotFoundException
/*  127:     */   {
/*  128: 179 */     MethodInfo mi = getMethodInfo2();
/*  129: 180 */     AnnotationsAttribute ainfo = (AnnotationsAttribute)mi.getAttribute("RuntimeInvisibleAnnotations");
/*  130:     */     
/*  131: 182 */     AnnotationsAttribute ainfo2 = (AnnotationsAttribute)mi.getAttribute("RuntimeVisibleAnnotations");
/*  132:     */     
/*  133: 184 */     return CtClassType.getAnnotationType(clz, getDeclaringClass().getClassPool(), ainfo, ainfo2);
/*  134:     */   }
/*  135:     */   
/*  136:     */   public Object[] getAnnotations()
/*  137:     */     throws ClassNotFoundException
/*  138:     */   {
/*  139: 197 */     return getAnnotations(false);
/*  140:     */   }
/*  141:     */   
/*  142:     */   public Object[] getAvailableAnnotations()
/*  143:     */   {
/*  144:     */     try
/*  145:     */     {
/*  146: 211 */       return getAnnotations(true);
/*  147:     */     }
/*  148:     */     catch (ClassNotFoundException e)
/*  149:     */     {
/*  150: 214 */       throw new RuntimeException("Unexpected exception", e);
/*  151:     */     }
/*  152:     */   }
/*  153:     */   
/*  154:     */   private Object[] getAnnotations(boolean ignoreNotFound)
/*  155:     */     throws ClassNotFoundException
/*  156:     */   {
/*  157: 221 */     MethodInfo mi = getMethodInfo2();
/*  158: 222 */     AnnotationsAttribute ainfo = (AnnotationsAttribute)mi.getAttribute("RuntimeInvisibleAnnotations");
/*  159:     */     
/*  160: 224 */     AnnotationsAttribute ainfo2 = (AnnotationsAttribute)mi.getAttribute("RuntimeVisibleAnnotations");
/*  161:     */     
/*  162: 226 */     return CtClassType.toAnnotationType(ignoreNotFound, getDeclaringClass().getClassPool(), ainfo, ainfo2);
/*  163:     */   }
/*  164:     */   
/*  165:     */   public Object[][] getParameterAnnotations()
/*  166:     */     throws ClassNotFoundException
/*  167:     */   {
/*  168: 243 */     return getParameterAnnotations(false);
/*  169:     */   }
/*  170:     */   
/*  171:     */   public Object[][] getAvailableParameterAnnotations()
/*  172:     */   {
/*  173:     */     try
/*  174:     */     {
/*  175: 261 */       return getParameterAnnotations(true);
/*  176:     */     }
/*  177:     */     catch (ClassNotFoundException e)
/*  178:     */     {
/*  179: 264 */       throw new RuntimeException("Unexpected exception", e);
/*  180:     */     }
/*  181:     */   }
/*  182:     */   
/*  183:     */   Object[][] getParameterAnnotations(boolean ignoreNotFound)
/*  184:     */     throws ClassNotFoundException
/*  185:     */   {
/*  186: 271 */     MethodInfo mi = getMethodInfo2();
/*  187: 272 */     ParameterAnnotationsAttribute ainfo = (ParameterAnnotationsAttribute)mi.getAttribute("RuntimeInvisibleParameterAnnotations");
/*  188:     */     
/*  189: 274 */     ParameterAnnotationsAttribute ainfo2 = (ParameterAnnotationsAttribute)mi.getAttribute("RuntimeVisibleParameterAnnotations");
/*  190:     */     
/*  191: 276 */     return CtClassType.toAnnotationType(ignoreNotFound, getDeclaringClass().getClassPool(), ainfo, ainfo2, mi);
/*  192:     */   }
/*  193:     */   
/*  194:     */   public CtClass[] getParameterTypes()
/*  195:     */     throws NotFoundException
/*  196:     */   {
/*  197: 285 */     return Descriptor.getParameterTypes(this.methodInfo.getDescriptor(), this.declaringClass.getClassPool());
/*  198:     */   }
/*  199:     */   
/*  200:     */   CtClass getReturnType0()
/*  201:     */     throws NotFoundException
/*  202:     */   {
/*  203: 293 */     return Descriptor.getReturnType(this.methodInfo.getDescriptor(), this.declaringClass.getClassPool());
/*  204:     */   }
/*  205:     */   
/*  206:     */   public String getSignature()
/*  207:     */   {
/*  208: 319 */     return this.methodInfo.getDescriptor();
/*  209:     */   }
/*  210:     */   
/*  211:     */   public CtClass[] getExceptionTypes()
/*  212:     */     throws NotFoundException
/*  213:     */   {
/*  214: 329 */     ExceptionsAttribute ea = this.methodInfo.getExceptionsAttribute();
/*  215:     */     String[] exceptions;
/*  216:     */     String[] exceptions;
/*  217: 330 */     if (ea == null) {
/*  218: 331 */       exceptions = null;
/*  219:     */     } else {
/*  220: 333 */       exceptions = ea.getExceptions();
/*  221:     */     }
/*  222: 335 */     return this.declaringClass.getClassPool().get(exceptions);
/*  223:     */   }
/*  224:     */   
/*  225:     */   public void setExceptionTypes(CtClass[] types)
/*  226:     */     throws NotFoundException
/*  227:     */   {
/*  228: 342 */     this.declaringClass.checkModify();
/*  229: 343 */     if ((types == null) || (types.length == 0))
/*  230:     */     {
/*  231: 344 */       this.methodInfo.removeExceptionsAttribute();
/*  232: 345 */       return;
/*  233:     */     }
/*  234: 348 */     String[] names = new String[types.length];
/*  235: 349 */     for (int i = 0; i < types.length; i++) {
/*  236: 350 */       names[i] = types[i].getName();
/*  237:     */     }
/*  238: 352 */     ExceptionsAttribute ea = this.methodInfo.getExceptionsAttribute();
/*  239: 353 */     if (ea == null)
/*  240:     */     {
/*  241: 354 */       ea = new ExceptionsAttribute(this.methodInfo.getConstPool());
/*  242: 355 */       this.methodInfo.setExceptionsAttribute(ea);
/*  243:     */     }
/*  244: 358 */     ea.setExceptions(names);
/*  245:     */   }
/*  246:     */   
/*  247:     */   public abstract boolean isEmpty();
/*  248:     */   
/*  249:     */   public void setBody(String src)
/*  250:     */     throws CannotCompileException
/*  251:     */   {
/*  252: 375 */     setBody(src, null, null);
/*  253:     */   }
/*  254:     */   
/*  255:     */   public void setBody(String src, String delegateObj, String delegateMethod)
/*  256:     */     throws CannotCompileException
/*  257:     */   {
/*  258: 394 */     CtClass cc = this.declaringClass;
/*  259: 395 */     cc.checkModify();
/*  260:     */     try
/*  261:     */     {
/*  262: 397 */       Javac jv = new Javac(cc);
/*  263: 398 */       if (delegateMethod != null) {
/*  264: 399 */         jv.recordProceed(delegateObj, delegateMethod);
/*  265:     */       }
/*  266: 401 */       Bytecode b = jv.compileBody(this, src);
/*  267: 402 */       this.methodInfo.setCodeAttribute(b.toCodeAttribute());
/*  268: 403 */       this.methodInfo.setAccessFlags(this.methodInfo.getAccessFlags() & 0xFFFFFBFF);
/*  269:     */       
/*  270: 405 */       this.methodInfo.rebuildStackMapIf6(cc.getClassPool(), cc.getClassFile2());
/*  271: 406 */       this.declaringClass.rebuildClassFile();
/*  272:     */     }
/*  273:     */     catch (CompileError e)
/*  274:     */     {
/*  275: 409 */       throw new CannotCompileException(e);
/*  276:     */     }
/*  277:     */     catch (BadBytecode e)
/*  278:     */     {
/*  279: 411 */       throw new CannotCompileException(e);
/*  280:     */     }
/*  281:     */   }
/*  282:     */   
/*  283:     */   static void setBody0(CtClass srcClass, MethodInfo srcInfo, CtClass destClass, MethodInfo destInfo, ClassMap map)
/*  284:     */     throws CannotCompileException
/*  285:     */   {
/*  286: 420 */     destClass.checkModify();
/*  287:     */     
/*  288: 422 */     map = new ClassMap(map);
/*  289: 423 */     map.put(srcClass.getName(), destClass.getName());
/*  290:     */     try
/*  291:     */     {
/*  292: 425 */       CodeAttribute cattr = srcInfo.getCodeAttribute();
/*  293: 426 */       if (cattr != null)
/*  294:     */       {
/*  295: 427 */         ConstPool cp = destInfo.getConstPool();
/*  296: 428 */         CodeAttribute ca = (CodeAttribute)cattr.copy(cp, map);
/*  297: 429 */         destInfo.setCodeAttribute(ca);
/*  298:     */       }
/*  299:     */     }
/*  300:     */     catch (CodeAttribute.RuntimeCopyException e)
/*  301:     */     {
/*  302: 436 */       throw new CannotCompileException(e);
/*  303:     */     }
/*  304: 439 */     destInfo.setAccessFlags(destInfo.getAccessFlags() & 0xFFFFFBFF);
/*  305:     */     
/*  306: 441 */     destClass.rebuildClassFile();
/*  307:     */   }
/*  308:     */   
/*  309:     */   public byte[] getAttribute(String name)
/*  310:     */   {
/*  311: 456 */     AttributeInfo ai = this.methodInfo.getAttribute(name);
/*  312: 457 */     if (ai == null) {
/*  313: 458 */       return null;
/*  314:     */     }
/*  315: 460 */     return ai.get();
/*  316:     */   }
/*  317:     */   
/*  318:     */   public void setAttribute(String name, byte[] data)
/*  319:     */   {
/*  320: 474 */     this.declaringClass.checkModify();
/*  321: 475 */     this.methodInfo.addAttribute(new AttributeInfo(this.methodInfo.getConstPool(), name, data));
/*  322:     */   }
/*  323:     */   
/*  324:     */   public void useCflow(String name)
/*  325:     */     throws CannotCompileException
/*  326:     */   {
/*  327: 497 */     CtClass cc = this.declaringClass;
/*  328: 498 */     cc.checkModify();
/*  329: 499 */     ClassPool pool = cc.getClassPool();
/*  330:     */     
/*  331: 501 */     int i = 0;
/*  332:     */     for (;;)
/*  333:     */     {
/*  334: 503 */       String fname = "_cflow$" + i++;
/*  335:     */       try
/*  336:     */       {
/*  337: 505 */         cc.getDeclaredField(fname);
/*  338:     */       }
/*  339:     */       catch (NotFoundException e)
/*  340:     */       {
/*  341: 512 */         pool.recordCflow(name, this.declaringClass.getName(), fname);
/*  342:     */         try
/*  343:     */         {
/*  344: 514 */           CtClass type = pool.get("javassist.runtime.Cflow");
/*  345: 515 */           CtField field = new CtField(type, fname, cc);
/*  346: 516 */           field.setModifiers(9);
/*  347: 517 */           cc.addField(field, CtField.Initializer.byNew(type));
/*  348: 518 */           insertBefore(fname + ".enter();", false);
/*  349: 519 */           String src = fname + ".exit();";
/*  350: 520 */           insertAfter(src, true);
/*  351:     */         }
/*  352:     */         catch (NotFoundException e)
/*  353:     */         {
/*  354: 523 */           throw new CannotCompileException(e);
/*  355:     */         }
/*  356:     */       }
/*  357:     */     }
/*  358:     */   }
/*  359:     */   
/*  360:     */   public void addLocalVariable(String name, CtClass type)
/*  361:     */     throws CannotCompileException
/*  362:     */   {
/*  363: 545 */     this.declaringClass.checkModify();
/*  364: 546 */     ConstPool cp = this.methodInfo.getConstPool();
/*  365: 547 */     CodeAttribute ca = this.methodInfo.getCodeAttribute();
/*  366: 548 */     if (ca == null) {
/*  367: 549 */       throw new CannotCompileException("no method body");
/*  368:     */     }
/*  369: 551 */     LocalVariableAttribute va = (LocalVariableAttribute)ca.getAttribute("LocalVariableTable");
/*  370: 553 */     if (va == null)
/*  371:     */     {
/*  372: 554 */       va = new LocalVariableAttribute(cp);
/*  373: 555 */       ca.getAttributes().add(va);
/*  374:     */     }
/*  375: 558 */     int maxLocals = ca.getMaxLocals();
/*  376: 559 */     String desc = Descriptor.of(type);
/*  377: 560 */     va.addEntry(0, ca.getCodeLength(), cp.addUtf8Info(name), cp.addUtf8Info(desc), maxLocals);
/*  378:     */     
/*  379: 562 */     ca.setMaxLocals(maxLocals + Descriptor.dataSize(desc));
/*  380:     */   }
/*  381:     */   
/*  382:     */   public void insertParameter(CtClass type)
/*  383:     */     throws CannotCompileException
/*  384:     */   {
/*  385: 571 */     this.declaringClass.checkModify();
/*  386: 572 */     String desc = this.methodInfo.getDescriptor();
/*  387: 573 */     String desc2 = Descriptor.insertParameter(type, desc);
/*  388:     */     try
/*  389:     */     {
/*  390: 575 */       addParameter2(Modifier.isStatic(getModifiers()) ? 0 : 1, type, desc);
/*  391:     */     }
/*  392:     */     catch (BadBytecode e)
/*  393:     */     {
/*  394: 578 */       throw new CannotCompileException(e);
/*  395:     */     }
/*  396: 581 */     this.methodInfo.setDescriptor(desc2);
/*  397:     */   }
/*  398:     */   
/*  399:     */   public void addParameter(CtClass type)
/*  400:     */     throws CannotCompileException
/*  401:     */   {
/*  402: 590 */     this.declaringClass.checkModify();
/*  403: 591 */     String desc = this.methodInfo.getDescriptor();
/*  404: 592 */     String desc2 = Descriptor.appendParameter(type, desc);
/*  405: 593 */     int offset = Modifier.isStatic(getModifiers()) ? 0 : 1;
/*  406:     */     try
/*  407:     */     {
/*  408: 595 */       addParameter2(offset + Descriptor.paramSize(desc), type, desc);
/*  409:     */     }
/*  410:     */     catch (BadBytecode e)
/*  411:     */     {
/*  412: 598 */       throw new CannotCompileException(e);
/*  413:     */     }
/*  414: 601 */     this.methodInfo.setDescriptor(desc2);
/*  415:     */   }
/*  416:     */   
/*  417:     */   private void addParameter2(int where, CtClass type, String desc)
/*  418:     */     throws BadBytecode
/*  419:     */   {
/*  420: 607 */     CodeAttribute ca = this.methodInfo.getCodeAttribute();
/*  421: 608 */     if (ca != null)
/*  422:     */     {
/*  423: 609 */       int size = 1;
/*  424: 610 */       char typeDesc = 'L';
/*  425: 611 */       int classInfo = 0;
/*  426: 612 */       if (type.isPrimitive())
/*  427:     */       {
/*  428: 613 */         CtPrimitiveType cpt = (CtPrimitiveType)type;
/*  429: 614 */         size = cpt.getDataSize();
/*  430: 615 */         typeDesc = cpt.getDescriptor();
/*  431:     */       }
/*  432:     */       else
/*  433:     */       {
/*  434: 618 */         classInfo = this.methodInfo.getConstPool().addClassInfo(type);
/*  435:     */       }
/*  436: 620 */       ca.insertLocalVar(where, size);
/*  437: 621 */       LocalVariableAttribute va = (LocalVariableAttribute)ca.getAttribute("LocalVariableTable");
/*  438: 624 */       if (va != null) {
/*  439: 625 */         va.shiftIndex(where, size);
/*  440:     */       }
/*  441: 627 */       StackMapTable smt = (StackMapTable)ca.getAttribute("StackMapTable");
/*  442: 628 */       if (smt != null) {
/*  443: 629 */         smt.insertLocal(where, StackMapTable.typeTagOf(typeDesc), classInfo);
/*  444:     */       }
/*  445: 631 */       StackMap sm = (StackMap)ca.getAttribute("StackMap");
/*  446: 632 */       if (sm != null) {
/*  447: 633 */         sm.insertLocal(where, StackMapTable.typeTagOf(typeDesc), classInfo);
/*  448:     */       }
/*  449:     */     }
/*  450:     */   }
/*  451:     */   
/*  452:     */   public void instrument(CodeConverter converter)
/*  453:     */     throws CannotCompileException
/*  454:     */   {
/*  455: 645 */     this.declaringClass.checkModify();
/*  456: 646 */     ConstPool cp = this.methodInfo.getConstPool();
/*  457: 647 */     converter.doit(getDeclaringClass(), this.methodInfo, cp);
/*  458:     */   }
/*  459:     */   
/*  460:     */   public void instrument(ExprEditor editor)
/*  461:     */     throws CannotCompileException
/*  462:     */   {
/*  463: 660 */     if (this.declaringClass.isFrozen()) {
/*  464: 661 */       this.declaringClass.checkModify();
/*  465:     */     }
/*  466: 663 */     if (editor.doit(this.declaringClass, this.methodInfo)) {
/*  467: 664 */       this.declaringClass.checkModify();
/*  468:     */     }
/*  469:     */   }
/*  470:     */   
/*  471:     */   public void insertBefore(String src)
/*  472:     */     throws CannotCompileException
/*  473:     */   {
/*  474: 685 */     insertBefore(src, true);
/*  475:     */   }
/*  476:     */   
/*  477:     */   private void insertBefore(String src, boolean rebuild)
/*  478:     */     throws CannotCompileException
/*  479:     */   {
/*  480: 691 */     CtClass cc = this.declaringClass;
/*  481: 692 */     cc.checkModify();
/*  482: 693 */     CodeAttribute ca = this.methodInfo.getCodeAttribute();
/*  483: 694 */     if (ca == null) {
/*  484: 695 */       throw new CannotCompileException("no method body");
/*  485:     */     }
/*  486: 697 */     CodeIterator iterator = ca.iterator();
/*  487: 698 */     Javac jv = new Javac(cc);
/*  488:     */     try
/*  489:     */     {
/*  490: 700 */       int nvars = jv.recordParams(getParameterTypes(), Modifier.isStatic(getModifiers()));
/*  491:     */       
/*  492: 702 */       jv.recordParamNames(ca, nvars);
/*  493: 703 */       jv.recordLocalVariables(ca, 0);
/*  494: 704 */       jv.recordType(getReturnType0());
/*  495: 705 */       jv.compileStmnt(src);
/*  496: 706 */       Bytecode b = jv.getBytecode();
/*  497: 707 */       int stack = b.getMaxStack();
/*  498: 708 */       int locals = b.getMaxLocals();
/*  499: 710 */       if (stack > ca.getMaxStack()) {
/*  500: 711 */         ca.setMaxStack(stack);
/*  501:     */       }
/*  502: 713 */       if (locals > ca.getMaxLocals()) {
/*  503: 714 */         ca.setMaxLocals(locals);
/*  504:     */       }
/*  505: 716 */       int pos = iterator.insertEx(b.get());
/*  506: 717 */       iterator.insert(b.getExceptionTable(), pos);
/*  507: 718 */       if (rebuild) {
/*  508: 719 */         this.methodInfo.rebuildStackMapIf6(cc.getClassPool(), cc.getClassFile2());
/*  509:     */       }
/*  510:     */     }
/*  511:     */     catch (NotFoundException e)
/*  512:     */     {
/*  513: 722 */       throw new CannotCompileException(e);
/*  514:     */     }
/*  515:     */     catch (CompileError e)
/*  516:     */     {
/*  517: 725 */       throw new CannotCompileException(e);
/*  518:     */     }
/*  519:     */     catch (BadBytecode e)
/*  520:     */     {
/*  521: 728 */       throw new CannotCompileException(e);
/*  522:     */     }
/*  523:     */   }
/*  524:     */   
/*  525:     */   public void insertAfter(String src)
/*  526:     */     throws CannotCompileException
/*  527:     */   {
/*  528: 743 */     insertAfter(src, false);
/*  529:     */   }
/*  530:     */   
/*  531:     */   public void insertAfter(String src, boolean asFinally)
/*  532:     */     throws CannotCompileException
/*  533:     */   {
/*  534: 761 */     CtClass cc = this.declaringClass;
/*  535: 762 */     cc.checkModify();
/*  536: 763 */     ConstPool pool = this.methodInfo.getConstPool();
/*  537: 764 */     CodeAttribute ca = this.methodInfo.getCodeAttribute();
/*  538: 765 */     if (ca == null) {
/*  539: 766 */       throw new CannotCompileException("no method body");
/*  540:     */     }
/*  541: 768 */     CodeIterator iterator = ca.iterator();
/*  542: 769 */     int retAddr = ca.getMaxLocals();
/*  543: 770 */     Bytecode b = new Bytecode(pool, 0, retAddr + 1);
/*  544: 771 */     b.setStackDepth(ca.getMaxStack() + 1);
/*  545: 772 */     Javac jv = new Javac(b, cc);
/*  546:     */     try
/*  547:     */     {
/*  548: 774 */       int nvars = jv.recordParams(getParameterTypes(), Modifier.isStatic(getModifiers()));
/*  549:     */       
/*  550: 776 */       jv.recordParamNames(ca, nvars);
/*  551: 777 */       CtClass rtype = getReturnType0();
/*  552: 778 */       int varNo = jv.recordReturnType(rtype, true);
/*  553: 779 */       jv.recordLocalVariables(ca, 0);
/*  554:     */       
/*  555:     */ 
/*  556: 782 */       int handlerLen = insertAfterHandler(asFinally, b, rtype, varNo, jv, src);
/*  557:     */       
/*  558:     */ 
/*  559: 785 */       insertAfterAdvice(b, jv, src, pool, rtype, varNo);
/*  560:     */       
/*  561: 787 */       ca.setMaxStack(b.getMaxStack());
/*  562: 788 */       ca.setMaxLocals(b.getMaxLocals());
/*  563:     */       
/*  564: 790 */       int gapPos = iterator.append(b.get());
/*  565: 791 */       iterator.append(b.getExceptionTable(), gapPos);
/*  566: 793 */       if (asFinally) {
/*  567: 794 */         ca.getExceptionTable().add(getStartPosOfBody(ca), gapPos, gapPos, 0);
/*  568:     */       }
/*  569: 796 */       int gapLen = iterator.getCodeLength() - gapPos - handlerLen;
/*  570: 797 */       int subr = iterator.getCodeLength() - gapLen;
/*  571: 799 */       while (iterator.hasNext())
/*  572:     */       {
/*  573: 800 */         int pos = iterator.next();
/*  574: 801 */         if (pos >= subr) {
/*  575:     */           break;
/*  576:     */         }
/*  577: 804 */         int c = iterator.byteAt(pos);
/*  578: 805 */         if ((c == 176) || (c == 172) || (c == 174) || (c == 173) || (c == 175) || (c == 177))
/*  579:     */         {
/*  580: 808 */           insertGoto(iterator, subr, pos);
/*  581: 809 */           subr = iterator.getCodeLength() - gapLen;
/*  582:     */         }
/*  583:     */       }
/*  584: 813 */       this.methodInfo.rebuildStackMapIf6(cc.getClassPool(), cc.getClassFile2());
/*  585:     */     }
/*  586:     */     catch (NotFoundException e)
/*  587:     */     {
/*  588: 816 */       throw new CannotCompileException(e);
/*  589:     */     }
/*  590:     */     catch (CompileError e)
/*  591:     */     {
/*  592: 819 */       throw new CannotCompileException(e);
/*  593:     */     }
/*  594:     */     catch (BadBytecode e)
/*  595:     */     {
/*  596: 822 */       throw new CannotCompileException(e);
/*  597:     */     }
/*  598:     */   }
/*  599:     */   
/*  600:     */   private void insertAfterAdvice(Bytecode code, Javac jv, String src, ConstPool cp, CtClass rtype, int varNo)
/*  601:     */     throws CompileError
/*  602:     */   {
/*  603: 830 */     if (rtype == CtClass.voidType)
/*  604:     */     {
/*  605: 831 */       code.addOpcode(1);
/*  606: 832 */       code.addAstore(varNo);
/*  607: 833 */       jv.compileStmnt(src);
/*  608: 834 */       code.addOpcode(177);
/*  609: 835 */       if (code.getMaxLocals() < 1) {
/*  610: 836 */         code.setMaxLocals(1);
/*  611:     */       }
/*  612:     */     }
/*  613:     */     else
/*  614:     */     {
/*  615: 839 */       code.addStore(varNo, rtype);
/*  616: 840 */       jv.compileStmnt(src);
/*  617: 841 */       code.addLoad(varNo, rtype);
/*  618: 842 */       if (rtype.isPrimitive()) {
/*  619: 843 */         code.addOpcode(((CtPrimitiveType)rtype).getReturnOp());
/*  620:     */       } else {
/*  621: 845 */         code.addOpcode(176);
/*  622:     */       }
/*  623:     */     }
/*  624:     */   }
/*  625:     */   
/*  626:     */   private void insertGoto(CodeIterator iterator, int subr, int pos)
/*  627:     */     throws BadBytecode
/*  628:     */   {
/*  629: 855 */     iterator.setMark(subr);
/*  630:     */     
/*  631: 857 */     iterator.writeByte(0, pos);
/*  632: 858 */     boolean wide = subr + 2 - pos > 32767;
/*  633: 859 */     pos = iterator.insertGapAt(pos, wide ? 4 : 2, false).position;
/*  634: 860 */     int offset = iterator.getMark() - pos;
/*  635: 861 */     if (wide)
/*  636:     */     {
/*  637: 862 */       iterator.writeByte(200, pos);
/*  638: 863 */       iterator.write32bit(offset, pos + 1);
/*  639:     */     }
/*  640: 865 */     else if (offset <= 32767)
/*  641:     */     {
/*  642: 866 */       iterator.writeByte(167, pos);
/*  643: 867 */       iterator.write16bit(offset, pos + 1);
/*  644:     */     }
/*  645:     */     else
/*  646:     */     {
/*  647: 870 */       pos = iterator.insertGapAt(pos, 2, false).position;
/*  648: 871 */       iterator.writeByte(200, pos);
/*  649: 872 */       iterator.write32bit(iterator.getMark() - pos, pos + 1);
/*  650:     */     }
/*  651:     */   }
/*  652:     */   
/*  653:     */   private int insertAfterHandler(boolean asFinally, Bytecode b, CtClass rtype, int returnVarNo, Javac javac, String src)
/*  654:     */     throws CompileError
/*  655:     */   {
/*  656: 883 */     if (!asFinally) {
/*  657: 884 */       return 0;
/*  658:     */     }
/*  659: 886 */     int var = b.getMaxLocals();
/*  660: 887 */     b.incMaxLocals(1);
/*  661: 888 */     int pc = b.currentPc();
/*  662: 889 */     b.addAstore(var);
/*  663: 890 */     if (rtype.isPrimitive())
/*  664:     */     {
/*  665: 891 */       char c = ((CtPrimitiveType)rtype).getDescriptor();
/*  666: 892 */       if (c == 'D')
/*  667:     */       {
/*  668: 893 */         b.addDconst(0.0D);
/*  669: 894 */         b.addDstore(returnVarNo);
/*  670:     */       }
/*  671: 896 */       else if (c == 'F')
/*  672:     */       {
/*  673: 897 */         b.addFconst(0.0F);
/*  674: 898 */         b.addFstore(returnVarNo);
/*  675:     */       }
/*  676: 900 */       else if (c == 'J')
/*  677:     */       {
/*  678: 901 */         b.addLconst(0L);
/*  679: 902 */         b.addLstore(returnVarNo);
/*  680:     */       }
/*  681: 904 */       else if (c == 'V')
/*  682:     */       {
/*  683: 905 */         b.addOpcode(1);
/*  684: 906 */         b.addAstore(returnVarNo);
/*  685:     */       }
/*  686:     */       else
/*  687:     */       {
/*  688: 909 */         b.addIconst(0);
/*  689: 910 */         b.addIstore(returnVarNo);
/*  690:     */       }
/*  691:     */     }
/*  692:     */     else
/*  693:     */     {
/*  694: 914 */       b.addOpcode(1);
/*  695: 915 */       b.addAstore(returnVarNo);
/*  696:     */     }
/*  697: 918 */     javac.compileStmnt(src);
/*  698: 919 */     b.addAload(var);
/*  699: 920 */     b.addOpcode(191);
/*  700: 921 */     return b.currentPc() - pc;
/*  701:     */   }
/*  702:     */   
/*  703:     */   public void addCatch(String src, CtClass exceptionType)
/*  704:     */     throws CannotCompileException
/*  705:     */   {
/*  706: 987 */     addCatch(src, exceptionType, "$e");
/*  707:     */   }
/*  708:     */   
/*  709:     */   public void addCatch(String src, CtClass exceptionType, String exceptionName)
/*  710:     */     throws CannotCompileException
/*  711:     */   {
/*  712:1006 */     CtClass cc = this.declaringClass;
/*  713:1007 */     cc.checkModify();
/*  714:1008 */     ConstPool cp = this.methodInfo.getConstPool();
/*  715:1009 */     CodeAttribute ca = this.methodInfo.getCodeAttribute();
/*  716:1010 */     CodeIterator iterator = ca.iterator();
/*  717:1011 */     Bytecode b = new Bytecode(cp, ca.getMaxStack(), ca.getMaxLocals());
/*  718:1012 */     b.setStackDepth(1);
/*  719:1013 */     Javac jv = new Javac(b, cc);
/*  720:     */     try
/*  721:     */     {
/*  722:1015 */       jv.recordParams(getParameterTypes(), Modifier.isStatic(getModifiers()));
/*  723:     */       
/*  724:1017 */       int var = jv.recordVariable(exceptionType, exceptionName);
/*  725:1018 */       b.addAstore(var);
/*  726:1019 */       jv.compileStmnt(src);
/*  727:     */       
/*  728:1021 */       int stack = b.getMaxStack();
/*  729:1022 */       int locals = b.getMaxLocals();
/*  730:1024 */       if (stack > ca.getMaxStack()) {
/*  731:1025 */         ca.setMaxStack(stack);
/*  732:     */       }
/*  733:1027 */       if (locals > ca.getMaxLocals()) {
/*  734:1028 */         ca.setMaxLocals(locals);
/*  735:     */       }
/*  736:1030 */       int len = iterator.getCodeLength();
/*  737:1031 */       int pos = iterator.append(b.get());
/*  738:1032 */       ca.getExceptionTable().add(getStartPosOfBody(ca), len, len, cp.addClassInfo(exceptionType));
/*  739:     */       
/*  740:1034 */       iterator.append(b.getExceptionTable(), pos);
/*  741:1035 */       this.methodInfo.rebuildStackMapIf6(cc.getClassPool(), cc.getClassFile2());
/*  742:     */     }
/*  743:     */     catch (NotFoundException e)
/*  744:     */     {
/*  745:1038 */       throw new CannotCompileException(e);
/*  746:     */     }
/*  747:     */     catch (CompileError e)
/*  748:     */     {
/*  749:1041 */       throw new CannotCompileException(e);
/*  750:     */     }
/*  751:     */     catch (BadBytecode e)
/*  752:     */     {
/*  753:1043 */       throw new CannotCompileException(e);
/*  754:     */     }
/*  755:     */   }
/*  756:     */   
/*  757:     */   int getStartPosOfBody(CodeAttribute ca)
/*  758:     */     throws CannotCompileException
/*  759:     */   {
/*  760:1050 */     return 0;
/*  761:     */   }
/*  762:     */   
/*  763:     */   public int insertAt(int lineNum, String src)
/*  764:     */     throws CannotCompileException
/*  765:     */   {
/*  766:1073 */     return insertAt(lineNum, true, src);
/*  767:     */   }
/*  768:     */   
/*  769:     */   public int insertAt(int lineNum, boolean modify, String src)
/*  770:     */     throws CannotCompileException
/*  771:     */   {
/*  772:1101 */     CodeAttribute ca = this.methodInfo.getCodeAttribute();
/*  773:1102 */     if (ca == null) {
/*  774:1103 */       throw new CannotCompileException("no method body");
/*  775:     */     }
/*  776:1105 */     LineNumberAttribute ainfo = (LineNumberAttribute)ca.getAttribute("LineNumberTable");
/*  777:1107 */     if (ainfo == null) {
/*  778:1108 */       throw new CannotCompileException("no line number info");
/*  779:     */     }
/*  780:1110 */     LineNumberAttribute.Pc pc = ainfo.toNearPc(lineNum);
/*  781:1111 */     lineNum = pc.line;
/*  782:1112 */     int index = pc.index;
/*  783:1113 */     if (!modify) {
/*  784:1114 */       return lineNum;
/*  785:     */     }
/*  786:1116 */     CtClass cc = this.declaringClass;
/*  787:1117 */     cc.checkModify();
/*  788:1118 */     CodeIterator iterator = ca.iterator();
/*  789:1119 */     Javac jv = new Javac(cc);
/*  790:     */     try
/*  791:     */     {
/*  792:1121 */       jv.recordLocalVariables(ca, index);
/*  793:1122 */       jv.recordParams(getParameterTypes(), Modifier.isStatic(getModifiers()));
/*  794:     */       
/*  795:1124 */       jv.setMaxLocals(ca.getMaxLocals());
/*  796:1125 */       jv.compileStmnt(src);
/*  797:1126 */       Bytecode b = jv.getBytecode();
/*  798:1127 */       int locals = b.getMaxLocals();
/*  799:1128 */       int stack = b.getMaxStack();
/*  800:1129 */       ca.setMaxLocals(locals);
/*  801:1134 */       if (stack > ca.getMaxStack()) {
/*  802:1135 */         ca.setMaxStack(stack);
/*  803:     */       }
/*  804:1137 */       index = iterator.insertAt(index, b.get());
/*  805:1138 */       iterator.insert(b.getExceptionTable(), index);
/*  806:1139 */       this.methodInfo.rebuildStackMapIf6(cc.getClassPool(), cc.getClassFile2());
/*  807:1140 */       return lineNum;
/*  808:     */     }
/*  809:     */     catch (NotFoundException e)
/*  810:     */     {
/*  811:1143 */       throw new CannotCompileException(e);
/*  812:     */     }
/*  813:     */     catch (CompileError e)
/*  814:     */     {
/*  815:1146 */       throw new CannotCompileException(e);
/*  816:     */     }
/*  817:     */     catch (BadBytecode e)
/*  818:     */     {
/*  819:1149 */       throw new CannotCompileException(e);
/*  820:     */     }
/*  821:     */   }
/*  822:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.CtBehavior
 * JD-Core Version:    0.7.0.1
 */