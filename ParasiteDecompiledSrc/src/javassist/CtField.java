/*    1:     */ package javassist;
/*    2:     */ 
/*    3:     */ import java.util.List;
/*    4:     */ import java.util.ListIterator;
/*    5:     */ import javassist.bytecode.AccessFlag;
/*    6:     */ import javassist.bytecode.AnnotationsAttribute;
/*    7:     */ import javassist.bytecode.AttributeInfo;
/*    8:     */ import javassist.bytecode.Bytecode;
/*    9:     */ import javassist.bytecode.ClassFile;
/*   10:     */ import javassist.bytecode.ConstPool;
/*   11:     */ import javassist.bytecode.Descriptor;
/*   12:     */ import javassist.bytecode.FieldInfo;
/*   13:     */ import javassist.compiler.CompileError;
/*   14:     */ import javassist.compiler.Javac;
/*   15:     */ import javassist.compiler.SymbolTable;
/*   16:     */ import javassist.compiler.ast.ASTree;
/*   17:     */ import javassist.compiler.ast.DoubleConst;
/*   18:     */ import javassist.compiler.ast.IntConst;
/*   19:     */ import javassist.compiler.ast.StringL;
/*   20:     */ 
/*   21:     */ public class CtField
/*   22:     */   extends CtMember
/*   23:     */ {
/*   24:     */   static final String javaLangString = "java.lang.String";
/*   25:     */   protected FieldInfo fieldInfo;
/*   26:     */   
/*   27:     */   public CtField(CtClass type, String name, CtClass declaring)
/*   28:     */     throws CannotCompileException
/*   29:     */   {
/*   30:  60 */     this(Descriptor.of(type), name, declaring);
/*   31:     */   }
/*   32:     */   
/*   33:     */   public CtField(CtField src, CtClass declaring)
/*   34:     */     throws CannotCompileException
/*   35:     */   {
/*   36:  83 */     this(src.fieldInfo.getDescriptor(), src.fieldInfo.getName(), declaring);
/*   37:     */     
/*   38:  85 */     ListIterator iterator = src.fieldInfo.getAttributes().listIterator();
/*   39:     */     
/*   40:  87 */     FieldInfo fi = this.fieldInfo;
/*   41:  88 */     fi.setAccessFlags(src.fieldInfo.getAccessFlags());
/*   42:  89 */     ConstPool cp = fi.getConstPool();
/*   43:  90 */     while (iterator.hasNext())
/*   44:     */     {
/*   45:  91 */       AttributeInfo ainfo = (AttributeInfo)iterator.next();
/*   46:  92 */       fi.addAttribute(ainfo.copy(cp, null));
/*   47:     */     }
/*   48:     */   }
/*   49:     */   
/*   50:     */   private CtField(String typeDesc, String name, CtClass clazz)
/*   51:     */     throws CannotCompileException
/*   52:     */   {
/*   53:  99 */     super(clazz);
/*   54: 100 */     ClassFile cf = clazz.getClassFile2();
/*   55: 101 */     if (cf == null) {
/*   56: 102 */       throw new CannotCompileException("bad declaring class: " + clazz.getName());
/*   57:     */     }
/*   58: 105 */     this.fieldInfo = new FieldInfo(cf.getConstPool(), name, typeDesc);
/*   59:     */   }
/*   60:     */   
/*   61:     */   CtField(FieldInfo fi, CtClass clazz)
/*   62:     */   {
/*   63: 109 */     super(clazz);
/*   64: 110 */     this.fieldInfo = fi;
/*   65:     */   }
/*   66:     */   
/*   67:     */   public String toString()
/*   68:     */   {
/*   69: 117 */     return getDeclaringClass().getName() + "." + getName() + ":" + this.fieldInfo.getDescriptor();
/*   70:     */   }
/*   71:     */   
/*   72:     */   protected void extendToString(StringBuffer buffer)
/*   73:     */   {
/*   74: 122 */     buffer.append(' ');
/*   75: 123 */     buffer.append(getName());
/*   76: 124 */     buffer.append(' ');
/*   77: 125 */     buffer.append(this.fieldInfo.getDescriptor());
/*   78:     */   }
/*   79:     */   
/*   80:     */   protected ASTree getInitAST()
/*   81:     */   {
/*   82: 130 */     return null;
/*   83:     */   }
/*   84:     */   
/*   85:     */   Initializer getInit()
/*   86:     */   {
/*   87: 135 */     ASTree tree = getInitAST();
/*   88: 136 */     if (tree == null) {
/*   89: 137 */       return null;
/*   90:     */     }
/*   91: 139 */     return Initializer.byExpr(tree);
/*   92:     */   }
/*   93:     */   
/*   94:     */   public static CtField make(String src, CtClass declaring)
/*   95:     */     throws CannotCompileException
/*   96:     */   {
/*   97: 159 */     Javac compiler = new Javac(declaring);
/*   98:     */     try
/*   99:     */     {
/*  100: 161 */       CtMember obj = compiler.compile(src);
/*  101: 162 */       if ((obj instanceof CtField)) {
/*  102: 163 */         return (CtField)obj;
/*  103:     */       }
/*  104:     */     }
/*  105:     */     catch (CompileError e)
/*  106:     */     {
/*  107: 166 */       throw new CannotCompileException(e);
/*  108:     */     }
/*  109: 169 */     throw new CannotCompileException("not a field");
/*  110:     */   }
/*  111:     */   
/*  112:     */   public FieldInfo getFieldInfo()
/*  113:     */   {
/*  114: 176 */     this.declaringClass.checkModify();
/*  115: 177 */     return this.fieldInfo;
/*  116:     */   }
/*  117:     */   
/*  118:     */   public FieldInfo getFieldInfo2()
/*  119:     */   {
/*  120: 199 */     return this.fieldInfo;
/*  121:     */   }
/*  122:     */   
/*  123:     */   public CtClass getDeclaringClass()
/*  124:     */   {
/*  125: 206 */     return super.getDeclaringClass();
/*  126:     */   }
/*  127:     */   
/*  128:     */   public String getName()
/*  129:     */   {
/*  130: 213 */     return this.fieldInfo.getName();
/*  131:     */   }
/*  132:     */   
/*  133:     */   public void setName(String newName)
/*  134:     */   {
/*  135: 220 */     this.declaringClass.checkModify();
/*  136: 221 */     this.fieldInfo.setName(newName);
/*  137:     */   }
/*  138:     */   
/*  139:     */   public int getModifiers()
/*  140:     */   {
/*  141: 230 */     return AccessFlag.toModifier(this.fieldInfo.getAccessFlags());
/*  142:     */   }
/*  143:     */   
/*  144:     */   public void setModifiers(int mod)
/*  145:     */   {
/*  146: 239 */     this.declaringClass.checkModify();
/*  147: 240 */     this.fieldInfo.setAccessFlags(AccessFlag.of(mod));
/*  148:     */   }
/*  149:     */   
/*  150:     */   public boolean hasAnnotation(Class clz)
/*  151:     */   {
/*  152: 251 */     FieldInfo fi = getFieldInfo2();
/*  153: 252 */     AnnotationsAttribute ainfo = (AnnotationsAttribute)fi.getAttribute("RuntimeInvisibleAnnotations");
/*  154:     */     
/*  155: 254 */     AnnotationsAttribute ainfo2 = (AnnotationsAttribute)fi.getAttribute("RuntimeVisibleAnnotations");
/*  156:     */     
/*  157: 256 */     return CtClassType.hasAnnotationType(clz, getDeclaringClass().getClassPool(), ainfo, ainfo2);
/*  158:     */   }
/*  159:     */   
/*  160:     */   public Object getAnnotation(Class clz)
/*  161:     */     throws ClassNotFoundException
/*  162:     */   {
/*  163: 272 */     FieldInfo fi = getFieldInfo2();
/*  164: 273 */     AnnotationsAttribute ainfo = (AnnotationsAttribute)fi.getAttribute("RuntimeInvisibleAnnotations");
/*  165:     */     
/*  166: 275 */     AnnotationsAttribute ainfo2 = (AnnotationsAttribute)fi.getAttribute("RuntimeVisibleAnnotations");
/*  167:     */     
/*  168: 277 */     return CtClassType.getAnnotationType(clz, getDeclaringClass().getClassPool(), ainfo, ainfo2);
/*  169:     */   }
/*  170:     */   
/*  171:     */   public Object[] getAnnotations()
/*  172:     */     throws ClassNotFoundException
/*  173:     */   {
/*  174: 289 */     return getAnnotations(false);
/*  175:     */   }
/*  176:     */   
/*  177:     */   public Object[] getAvailableAnnotations()
/*  178:     */   {
/*  179:     */     try
/*  180:     */     {
/*  181: 303 */       return getAnnotations(true);
/*  182:     */     }
/*  183:     */     catch (ClassNotFoundException e)
/*  184:     */     {
/*  185: 306 */       throw new RuntimeException("Unexpected exception", e);
/*  186:     */     }
/*  187:     */   }
/*  188:     */   
/*  189:     */   private Object[] getAnnotations(boolean ignoreNotFound)
/*  190:     */     throws ClassNotFoundException
/*  191:     */   {
/*  192: 311 */     FieldInfo fi = getFieldInfo2();
/*  193: 312 */     AnnotationsAttribute ainfo = (AnnotationsAttribute)fi.getAttribute("RuntimeInvisibleAnnotations");
/*  194:     */     
/*  195: 314 */     AnnotationsAttribute ainfo2 = (AnnotationsAttribute)fi.getAttribute("RuntimeVisibleAnnotations");
/*  196:     */     
/*  197: 316 */     return CtClassType.toAnnotationType(ignoreNotFound, getDeclaringClass().getClassPool(), ainfo, ainfo2);
/*  198:     */   }
/*  199:     */   
/*  200:     */   public String getSignature()
/*  201:     */   {
/*  202: 337 */     return this.fieldInfo.getDescriptor();
/*  203:     */   }
/*  204:     */   
/*  205:     */   public CtClass getType()
/*  206:     */     throws NotFoundException
/*  207:     */   {
/*  208: 344 */     return Descriptor.toCtClass(this.fieldInfo.getDescriptor(), this.declaringClass.getClassPool());
/*  209:     */   }
/*  210:     */   
/*  211:     */   public void setType(CtClass clazz)
/*  212:     */   {
/*  213: 352 */     this.declaringClass.checkModify();
/*  214: 353 */     this.fieldInfo.setDescriptor(Descriptor.of(clazz));
/*  215:     */   }
/*  216:     */   
/*  217:     */   public Object getConstantValue()
/*  218:     */   {
/*  219: 374 */     int index = this.fieldInfo.getConstantValue();
/*  220: 375 */     if (index == 0) {
/*  221: 376 */       return null;
/*  222:     */     }
/*  223: 378 */     ConstPool cp = this.fieldInfo.getConstPool();
/*  224: 379 */     switch (cp.getTag(index))
/*  225:     */     {
/*  226:     */     case 5: 
/*  227: 381 */       return new Long(cp.getLongInfo(index));
/*  228:     */     case 4: 
/*  229: 383 */       return new Float(cp.getFloatInfo(index));
/*  230:     */     case 6: 
/*  231: 385 */       return new Double(cp.getDoubleInfo(index));
/*  232:     */     case 3: 
/*  233: 387 */       int value = cp.getIntegerInfo(index);
/*  234: 389 */       if ("Z".equals(this.fieldInfo.getDescriptor())) {
/*  235: 390 */         return new Boolean(value != 0);
/*  236:     */       }
/*  237: 392 */       return new Integer(value);
/*  238:     */     case 8: 
/*  239: 394 */       return cp.getStringInfo(index);
/*  240:     */     }
/*  241: 396 */     throw new RuntimeException("bad tag: " + cp.getTag(index) + " at " + index);
/*  242:     */   }
/*  243:     */   
/*  244:     */   public byte[] getAttribute(String name)
/*  245:     */   {
/*  246: 413 */     AttributeInfo ai = this.fieldInfo.getAttribute(name);
/*  247: 414 */     if (ai == null) {
/*  248: 415 */       return null;
/*  249:     */     }
/*  250: 417 */     return ai.get();
/*  251:     */   }
/*  252:     */   
/*  253:     */   public void setAttribute(String name, byte[] data)
/*  254:     */   {
/*  255: 431 */     this.declaringClass.checkModify();
/*  256: 432 */     this.fieldInfo.addAttribute(new AttributeInfo(this.fieldInfo.getConstPool(), name, data));
/*  257:     */   }
/*  258:     */   
/*  259:     */   public static abstract class Initializer
/*  260:     */   {
/*  261:     */     public static Initializer constant(int i)
/*  262:     */     {
/*  263: 457 */       return new CtField.IntInitializer(i);
/*  264:     */     }
/*  265:     */     
/*  266:     */     public static Initializer constant(boolean b)
/*  267:     */     {
/*  268: 465 */       return new CtField.IntInitializer(b ? 1 : 0);
/*  269:     */     }
/*  270:     */     
/*  271:     */     public static Initializer constant(long l)
/*  272:     */     {
/*  273: 473 */       return new CtField.LongInitializer(l);
/*  274:     */     }
/*  275:     */     
/*  276:     */     public static Initializer constant(float l)
/*  277:     */     {
/*  278: 481 */       return new CtField.FloatInitializer(l);
/*  279:     */     }
/*  280:     */     
/*  281:     */     public static Initializer constant(double d)
/*  282:     */     {
/*  283: 489 */       return new CtField.DoubleInitializer(d);
/*  284:     */     }
/*  285:     */     
/*  286:     */     public static Initializer constant(String s)
/*  287:     */     {
/*  288: 497 */       return new CtField.StringInitializer(s);
/*  289:     */     }
/*  290:     */     
/*  291:     */     public static Initializer byParameter(int nth)
/*  292:     */     {
/*  293: 515 */       CtField.ParamInitializer i = new CtField.ParamInitializer();
/*  294: 516 */       i.nthParam = nth;
/*  295: 517 */       return i;
/*  296:     */     }
/*  297:     */     
/*  298:     */     public static Initializer byNew(CtClass objectType)
/*  299:     */     {
/*  300: 536 */       CtField.NewInitializer i = new CtField.NewInitializer();
/*  301: 537 */       i.objectType = objectType;
/*  302: 538 */       i.stringParams = null;
/*  303: 539 */       i.withConstructorParams = false;
/*  304: 540 */       return i;
/*  305:     */     }
/*  306:     */     
/*  307:     */     public static Initializer byNew(CtClass objectType, String[] stringParams)
/*  308:     */     {
/*  309: 564 */       CtField.NewInitializer i = new CtField.NewInitializer();
/*  310: 565 */       i.objectType = objectType;
/*  311: 566 */       i.stringParams = stringParams;
/*  312: 567 */       i.withConstructorParams = false;
/*  313: 568 */       return i;
/*  314:     */     }
/*  315:     */     
/*  316:     */     public static Initializer byNewWithParams(CtClass objectType)
/*  317:     */     {
/*  318: 593 */       CtField.NewInitializer i = new CtField.NewInitializer();
/*  319: 594 */       i.objectType = objectType;
/*  320: 595 */       i.stringParams = null;
/*  321: 596 */       i.withConstructorParams = true;
/*  322: 597 */       return i;
/*  323:     */     }
/*  324:     */     
/*  325:     */     public static Initializer byNewWithParams(CtClass objectType, String[] stringParams)
/*  326:     */     {
/*  327: 624 */       CtField.NewInitializer i = new CtField.NewInitializer();
/*  328: 625 */       i.objectType = objectType;
/*  329: 626 */       i.stringParams = stringParams;
/*  330: 627 */       i.withConstructorParams = true;
/*  331: 628 */       return i;
/*  332:     */     }
/*  333:     */     
/*  334:     */     public static Initializer byCall(CtClass methodClass, String methodName)
/*  335:     */     {
/*  336: 653 */       CtField.MethodInitializer i = new CtField.MethodInitializer();
/*  337: 654 */       i.objectType = methodClass;
/*  338: 655 */       i.methodName = methodName;
/*  339: 656 */       i.stringParams = null;
/*  340: 657 */       i.withConstructorParams = false;
/*  341: 658 */       return i;
/*  342:     */     }
/*  343:     */     
/*  344:     */     public static Initializer byCall(CtClass methodClass, String methodName, String[] stringParams)
/*  345:     */     {
/*  346: 688 */       CtField.MethodInitializer i = new CtField.MethodInitializer();
/*  347: 689 */       i.objectType = methodClass;
/*  348: 690 */       i.methodName = methodName;
/*  349: 691 */       i.stringParams = stringParams;
/*  350: 692 */       i.withConstructorParams = false;
/*  351: 693 */       return i;
/*  352:     */     }
/*  353:     */     
/*  354:     */     public static Initializer byCallWithParams(CtClass methodClass, String methodName)
/*  355:     */     {
/*  356: 721 */       CtField.MethodInitializer i = new CtField.MethodInitializer();
/*  357: 722 */       i.objectType = methodClass;
/*  358: 723 */       i.methodName = methodName;
/*  359: 724 */       i.stringParams = null;
/*  360: 725 */       i.withConstructorParams = true;
/*  361: 726 */       return i;
/*  362:     */     }
/*  363:     */     
/*  364:     */     public static Initializer byCallWithParams(CtClass methodClass, String methodName, String[] stringParams)
/*  365:     */     {
/*  366: 758 */       CtField.MethodInitializer i = new CtField.MethodInitializer();
/*  367: 759 */       i.objectType = methodClass;
/*  368: 760 */       i.methodName = methodName;
/*  369: 761 */       i.stringParams = stringParams;
/*  370: 762 */       i.withConstructorParams = true;
/*  371: 763 */       return i;
/*  372:     */     }
/*  373:     */     
/*  374:     */     public static Initializer byNewArray(CtClass type, int size)
/*  375:     */       throws NotFoundException
/*  376:     */     {
/*  377: 777 */       return new CtField.ArrayInitializer(type.getComponentType(), size);
/*  378:     */     }
/*  379:     */     
/*  380:     */     public static Initializer byNewArray(CtClass type, int[] sizes)
/*  381:     */     {
/*  382: 790 */       return new CtField.MultiArrayInitializer(type, sizes);
/*  383:     */     }
/*  384:     */     
/*  385:     */     public static Initializer byExpr(String source)
/*  386:     */     {
/*  387: 799 */       return new CtField.CodeInitializer(source);
/*  388:     */     }
/*  389:     */     
/*  390:     */     static Initializer byExpr(ASTree source)
/*  391:     */     {
/*  392: 803 */       return new CtField.PtreeInitializer(source);
/*  393:     */     }
/*  394:     */     
/*  395:     */     void check(String desc)
/*  396:     */       throws CannotCompileException
/*  397:     */     {}
/*  398:     */     
/*  399:     */     abstract int compile(CtClass paramCtClass, String paramString, Bytecode paramBytecode, CtClass[] paramArrayOfCtClass, Javac paramJavac)
/*  400:     */       throws CannotCompileException;
/*  401:     */     
/*  402:     */     abstract int compileIfStatic(CtClass paramCtClass, String paramString, Bytecode paramBytecode, Javac paramJavac)
/*  403:     */       throws CannotCompileException;
/*  404:     */     
/*  405:     */     int getConstantValue(ConstPool cp, CtClass type)
/*  406:     */     {
/*  407: 821 */       return 0;
/*  408:     */     }
/*  409:     */   }
/*  410:     */   
/*  411:     */   static abstract class CodeInitializer0
/*  412:     */     extends CtField.Initializer
/*  413:     */   {
/*  414:     */     abstract void compileExpr(Javac paramJavac)
/*  415:     */       throws CompileError;
/*  416:     */     
/*  417:     */     int compile(CtClass type, String name, Bytecode code, CtClass[] parameters, Javac drv)
/*  418:     */       throws CannotCompileException
/*  419:     */     {
/*  420:     */       try
/*  421:     */       {
/*  422: 832 */         code.addAload(0);
/*  423: 833 */         compileExpr(drv);
/*  424: 834 */         code.addPutfield(Bytecode.THIS, name, Descriptor.of(type));
/*  425: 835 */         return code.getMaxStack();
/*  426:     */       }
/*  427:     */       catch (CompileError e)
/*  428:     */       {
/*  429: 838 */         throw new CannotCompileException(e);
/*  430:     */       }
/*  431:     */     }
/*  432:     */     
/*  433:     */     int compileIfStatic(CtClass type, String name, Bytecode code, Javac drv)
/*  434:     */       throws CannotCompileException
/*  435:     */     {
/*  436:     */       try
/*  437:     */       {
/*  438: 846 */         compileExpr(drv);
/*  439: 847 */         code.addPutstatic(Bytecode.THIS, name, Descriptor.of(type));
/*  440: 848 */         return code.getMaxStack();
/*  441:     */       }
/*  442:     */       catch (CompileError e)
/*  443:     */       {
/*  444: 851 */         throw new CannotCompileException(e);
/*  445:     */       }
/*  446:     */     }
/*  447:     */     
/*  448:     */     int getConstantValue2(ConstPool cp, CtClass type, ASTree tree)
/*  449:     */     {
/*  450: 856 */       if (type.isPrimitive())
/*  451:     */       {
/*  452: 857 */         if ((tree instanceof IntConst))
/*  453:     */         {
/*  454: 858 */           long value = ((IntConst)tree).get();
/*  455: 859 */           if (type == CtClass.doubleType) {
/*  456: 860 */             return cp.addDoubleInfo(value);
/*  457:     */           }
/*  458: 861 */           if (type == CtClass.floatType) {
/*  459: 862 */             return cp.addFloatInfo((float)value);
/*  460:     */           }
/*  461: 863 */           if (type == CtClass.longType) {
/*  462: 864 */             return cp.addLongInfo(value);
/*  463:     */           }
/*  464: 865 */           if (type != CtClass.voidType) {
/*  465: 866 */             return cp.addIntegerInfo((int)value);
/*  466:     */           }
/*  467:     */         }
/*  468: 868 */         else if ((tree instanceof DoubleConst))
/*  469:     */         {
/*  470: 869 */           double value = ((DoubleConst)tree).get();
/*  471: 870 */           if (type == CtClass.floatType) {
/*  472: 871 */             return cp.addFloatInfo((float)value);
/*  473:     */           }
/*  474: 872 */           if (type == CtClass.doubleType) {
/*  475: 873 */             return cp.addDoubleInfo(value);
/*  476:     */           }
/*  477:     */         }
/*  478:     */       }
/*  479: 876 */       else if (((tree instanceof StringL)) && (type.getName().equals("java.lang.String"))) {
/*  480: 878 */         return cp.addStringInfo(((StringL)tree).get());
/*  481:     */       }
/*  482: 880 */       return 0;
/*  483:     */     }
/*  484:     */   }
/*  485:     */   
/*  486:     */   static class CodeInitializer
/*  487:     */     extends CtField.CodeInitializer0
/*  488:     */   {
/*  489:     */     private String expression;
/*  490:     */     
/*  491:     */     CodeInitializer(String expr)
/*  492:     */     {
/*  493: 887 */       this.expression = expr;
/*  494:     */     }
/*  495:     */     
/*  496:     */     void compileExpr(Javac drv)
/*  497:     */       throws CompileError
/*  498:     */     {
/*  499: 890 */       drv.compileExpr(this.expression);
/*  500:     */     }
/*  501:     */     
/*  502:     */     int getConstantValue(ConstPool cp, CtClass type)
/*  503:     */     {
/*  504:     */       try
/*  505:     */       {
/*  506: 895 */         ASTree t = Javac.parseExpr(this.expression, new SymbolTable());
/*  507: 896 */         return getConstantValue2(cp, type, t);
/*  508:     */       }
/*  509:     */       catch (CompileError e) {}
/*  510: 899 */       return 0;
/*  511:     */     }
/*  512:     */   }
/*  513:     */   
/*  514:     */   static class PtreeInitializer
/*  515:     */     extends CtField.CodeInitializer0
/*  516:     */   {
/*  517:     */     private ASTree expression;
/*  518:     */     
/*  519:     */     PtreeInitializer(ASTree expr)
/*  520:     */     {
/*  521: 907 */       this.expression = expr;
/*  522:     */     }
/*  523:     */     
/*  524:     */     void compileExpr(Javac drv)
/*  525:     */       throws CompileError
/*  526:     */     {
/*  527: 910 */       drv.compileExpr(this.expression);
/*  528:     */     }
/*  529:     */     
/*  530:     */     int getConstantValue(ConstPool cp, CtClass type)
/*  531:     */     {
/*  532: 914 */       return getConstantValue2(cp, type, this.expression);
/*  533:     */     }
/*  534:     */   }
/*  535:     */   
/*  536:     */   static class ParamInitializer
/*  537:     */     extends CtField.Initializer
/*  538:     */   {
/*  539:     */     int nthParam;
/*  540:     */     
/*  541:     */     int compile(CtClass type, String name, Bytecode code, CtClass[] parameters, Javac drv)
/*  542:     */       throws CannotCompileException
/*  543:     */     {
/*  544: 931 */       if ((parameters != null) && (this.nthParam < parameters.length))
/*  545:     */       {
/*  546: 932 */         code.addAload(0);
/*  547: 933 */         int nth = nthParamToLocal(this.nthParam, parameters, false);
/*  548: 934 */         int s = code.addLoad(nth, type) + 1;
/*  549: 935 */         code.addPutfield(Bytecode.THIS, name, Descriptor.of(type));
/*  550: 936 */         return s;
/*  551:     */       }
/*  552: 939 */       return 0;
/*  553:     */     }
/*  554:     */     
/*  555:     */     static int nthParamToLocal(int nth, CtClass[] params, boolean isStatic)
/*  556:     */     {
/*  557: 952 */       CtClass longType = CtClass.longType;
/*  558: 953 */       CtClass doubleType = CtClass.doubleType;
/*  559:     */       int k;
/*  560:     */       int k;
/*  561: 955 */       if (isStatic) {
/*  562: 956 */         k = 0;
/*  563:     */       } else {
/*  564: 958 */         k = 1;
/*  565:     */       }
/*  566: 960 */       for (int i = 0; i < nth; i++)
/*  567:     */       {
/*  568: 961 */         CtClass type = params[i];
/*  569: 962 */         if ((type == longType) || (type == doubleType)) {
/*  570: 963 */           k += 2;
/*  571:     */         } else {
/*  572: 965 */           k++;
/*  573:     */         }
/*  574:     */       }
/*  575: 968 */       return k;
/*  576:     */     }
/*  577:     */     
/*  578:     */     int compileIfStatic(CtClass type, String name, Bytecode code, Javac drv)
/*  579:     */       throws CannotCompileException
/*  580:     */     {
/*  581: 974 */       return 0;
/*  582:     */     }
/*  583:     */   }
/*  584:     */   
/*  585:     */   static class NewInitializer
/*  586:     */     extends CtField.Initializer
/*  587:     */   {
/*  588:     */     CtClass objectType;
/*  589:     */     String[] stringParams;
/*  590:     */     boolean withConstructorParams;
/*  591:     */     
/*  592:     */     int compile(CtClass type, String name, Bytecode code, CtClass[] parameters, Javac drv)
/*  593:     */       throws CannotCompileException
/*  594:     */     {
/*  595: 998 */       code.addAload(0);
/*  596: 999 */       code.addNew(this.objectType);
/*  597:1000 */       code.add(89);
/*  598:1001 */       code.addAload(0);
/*  599:     */       int stacksize;
/*  600:     */       int stacksize;
/*  601:1003 */       if (this.stringParams == null) {
/*  602:1004 */         stacksize = 4;
/*  603:     */       } else {
/*  604:1006 */         stacksize = compileStringParameter(code) + 4;
/*  605:     */       }
/*  606:1008 */       if (this.withConstructorParams) {
/*  607:1009 */         stacksize += CtNewWrappedMethod.compileParameterList(code, parameters, 1);
/*  608:     */       }
/*  609:1012 */       code.addInvokespecial(this.objectType, "<init>", getDescriptor());
/*  610:1013 */       code.addPutfield(Bytecode.THIS, name, Descriptor.of(type));
/*  611:1014 */       return stacksize;
/*  612:     */     }
/*  613:     */     
/*  614:     */     private String getDescriptor()
/*  615:     */     {
/*  616:1018 */       String desc3 = "(Ljava/lang/Object;[Ljava/lang/String;[Ljava/lang/Object;)V";
/*  617:1021 */       if (this.stringParams == null)
/*  618:     */       {
/*  619:1022 */         if (this.withConstructorParams) {
/*  620:1023 */           return "(Ljava/lang/Object;[Ljava/lang/Object;)V";
/*  621:     */         }
/*  622:1025 */         return "(Ljava/lang/Object;)V";
/*  623:     */       }
/*  624:1027 */       if (this.withConstructorParams) {
/*  625:1028 */         return "(Ljava/lang/Object;[Ljava/lang/String;[Ljava/lang/Object;)V";
/*  626:     */       }
/*  627:1030 */       return "(Ljava/lang/Object;[Ljava/lang/String;)V";
/*  628:     */     }
/*  629:     */     
/*  630:     */     int compileIfStatic(CtClass type, String name, Bytecode code, Javac drv)
/*  631:     */       throws CannotCompileException
/*  632:     */     {
/*  633:1041 */       code.addNew(this.objectType);
/*  634:1042 */       code.add(89);
/*  635:     */       
/*  636:1044 */       int stacksize = 2;
/*  637:     */       String desc;
/*  638:     */       String desc;
/*  639:1045 */       if (this.stringParams == null)
/*  640:     */       {
/*  641:1046 */         desc = "()V";
/*  642:     */       }
/*  643:     */       else
/*  644:     */       {
/*  645:1048 */         desc = "([Ljava/lang/String;)V";
/*  646:1049 */         stacksize += compileStringParameter(code);
/*  647:     */       }
/*  648:1052 */       code.addInvokespecial(this.objectType, "<init>", desc);
/*  649:1053 */       code.addPutstatic(Bytecode.THIS, name, Descriptor.of(type));
/*  650:1054 */       return stacksize;
/*  651:     */     }
/*  652:     */     
/*  653:     */     protected final int compileStringParameter(Bytecode code)
/*  654:     */       throws CannotCompileException
/*  655:     */     {
/*  656:1060 */       int nparam = this.stringParams.length;
/*  657:1061 */       code.addIconst(nparam);
/*  658:1062 */       code.addAnewarray("java.lang.String");
/*  659:1063 */       for (int j = 0; j < nparam; j++)
/*  660:     */       {
/*  661:1064 */         code.add(89);
/*  662:1065 */         code.addIconst(j);
/*  663:1066 */         code.addLdc(this.stringParams[j]);
/*  664:1067 */         code.add(83);
/*  665:     */       }
/*  666:1070 */       return 4;
/*  667:     */     }
/*  668:     */   }
/*  669:     */   
/*  670:     */   static class MethodInitializer
/*  671:     */     extends CtField.NewInitializer
/*  672:     */   {
/*  673:     */     String methodName;
/*  674:     */     
/*  675:     */     int compile(CtClass type, String name, Bytecode code, CtClass[] parameters, Javac drv)
/*  676:     */       throws CannotCompileException
/*  677:     */     {
/*  678:1094 */       code.addAload(0);
/*  679:1095 */       code.addAload(0);
/*  680:     */       int stacksize;
/*  681:     */       int stacksize;
/*  682:1097 */       if (this.stringParams == null) {
/*  683:1098 */         stacksize = 2;
/*  684:     */       } else {
/*  685:1100 */         stacksize = compileStringParameter(code) + 2;
/*  686:     */       }
/*  687:1102 */       if (this.withConstructorParams) {
/*  688:1103 */         stacksize += CtNewWrappedMethod.compileParameterList(code, parameters, 1);
/*  689:     */       }
/*  690:1106 */       String typeDesc = Descriptor.of(type);
/*  691:1107 */       String mDesc = getDescriptor() + typeDesc;
/*  692:1108 */       code.addInvokestatic(this.objectType, this.methodName, mDesc);
/*  693:1109 */       code.addPutfield(Bytecode.THIS, name, typeDesc);
/*  694:1110 */       return stacksize;
/*  695:     */     }
/*  696:     */     
/*  697:     */     private String getDescriptor()
/*  698:     */     {
/*  699:1114 */       String desc3 = "(Ljava/lang/Object;[Ljava/lang/String;[Ljava/lang/Object;)";
/*  700:1117 */       if (this.stringParams == null)
/*  701:     */       {
/*  702:1118 */         if (this.withConstructorParams) {
/*  703:1119 */           return "(Ljava/lang/Object;[Ljava/lang/Object;)";
/*  704:     */         }
/*  705:1121 */         return "(Ljava/lang/Object;)";
/*  706:     */       }
/*  707:1123 */       if (this.withConstructorParams) {
/*  708:1124 */         return "(Ljava/lang/Object;[Ljava/lang/String;[Ljava/lang/Object;)";
/*  709:     */       }
/*  710:1126 */       return "(Ljava/lang/Object;[Ljava/lang/String;)";
/*  711:     */     }
/*  712:     */     
/*  713:     */     int compileIfStatic(CtClass type, String name, Bytecode code, Javac drv)
/*  714:     */       throws CannotCompileException
/*  715:     */     {
/*  716:1137 */       int stacksize = 1;
/*  717:     */       String desc;
/*  718:     */       String desc;
/*  719:1138 */       if (this.stringParams == null)
/*  720:     */       {
/*  721:1139 */         desc = "()";
/*  722:     */       }
/*  723:     */       else
/*  724:     */       {
/*  725:1141 */         desc = "([Ljava/lang/String;)";
/*  726:1142 */         stacksize += compileStringParameter(code);
/*  727:     */       }
/*  728:1145 */       String typeDesc = Descriptor.of(type);
/*  729:1146 */       code.addInvokestatic(this.objectType, this.methodName, desc + typeDesc);
/*  730:1147 */       code.addPutstatic(Bytecode.THIS, name, typeDesc);
/*  731:1148 */       return stacksize;
/*  732:     */     }
/*  733:     */   }
/*  734:     */   
/*  735:     */   static class IntInitializer
/*  736:     */     extends CtField.Initializer
/*  737:     */   {
/*  738:     */     int value;
/*  739:     */     
/*  740:     */     IntInitializer(int v)
/*  741:     */     {
/*  742:1155 */       this.value = v;
/*  743:     */     }
/*  744:     */     
/*  745:     */     void check(String desc)
/*  746:     */       throws CannotCompileException
/*  747:     */     {
/*  748:1158 */       char c = desc.charAt(0);
/*  749:1159 */       if ((c != 'I') && (c != 'S') && (c != 'B') && (c != 'C') && (c != 'Z')) {
/*  750:1160 */         throw new CannotCompileException("type mismatch");
/*  751:     */       }
/*  752:     */     }
/*  753:     */     
/*  754:     */     int compile(CtClass type, String name, Bytecode code, CtClass[] parameters, Javac drv)
/*  755:     */       throws CannotCompileException
/*  756:     */     {
/*  757:1167 */       code.addAload(0);
/*  758:1168 */       code.addIconst(this.value);
/*  759:1169 */       code.addPutfield(Bytecode.THIS, name, Descriptor.of(type));
/*  760:1170 */       return 2;
/*  761:     */     }
/*  762:     */     
/*  763:     */     int compileIfStatic(CtClass type, String name, Bytecode code, Javac drv)
/*  764:     */       throws CannotCompileException
/*  765:     */     {
/*  766:1176 */       code.addIconst(this.value);
/*  767:1177 */       code.addPutstatic(Bytecode.THIS, name, Descriptor.of(type));
/*  768:1178 */       return 1;
/*  769:     */     }
/*  770:     */     
/*  771:     */     int getConstantValue(ConstPool cp, CtClass type)
/*  772:     */     {
/*  773:1182 */       return cp.addIntegerInfo(this.value);
/*  774:     */     }
/*  775:     */   }
/*  776:     */   
/*  777:     */   static class LongInitializer
/*  778:     */     extends CtField.Initializer
/*  779:     */   {
/*  780:     */     long value;
/*  781:     */     
/*  782:     */     LongInitializer(long v)
/*  783:     */     {
/*  784:1189 */       this.value = v;
/*  785:     */     }
/*  786:     */     
/*  787:     */     void check(String desc)
/*  788:     */       throws CannotCompileException
/*  789:     */     {
/*  790:1192 */       if (!desc.equals("J")) {
/*  791:1193 */         throw new CannotCompileException("type mismatch");
/*  792:     */       }
/*  793:     */     }
/*  794:     */     
/*  795:     */     int compile(CtClass type, String name, Bytecode code, CtClass[] parameters, Javac drv)
/*  796:     */       throws CannotCompileException
/*  797:     */     {
/*  798:1200 */       code.addAload(0);
/*  799:1201 */       code.addLdc2w(this.value);
/*  800:1202 */       code.addPutfield(Bytecode.THIS, name, Descriptor.of(type));
/*  801:1203 */       return 3;
/*  802:     */     }
/*  803:     */     
/*  804:     */     int compileIfStatic(CtClass type, String name, Bytecode code, Javac drv)
/*  805:     */       throws CannotCompileException
/*  806:     */     {
/*  807:1209 */       code.addLdc2w(this.value);
/*  808:1210 */       code.addPutstatic(Bytecode.THIS, name, Descriptor.of(type));
/*  809:1211 */       return 2;
/*  810:     */     }
/*  811:     */     
/*  812:     */     int getConstantValue(ConstPool cp, CtClass type)
/*  813:     */     {
/*  814:1215 */       if (type == CtClass.longType) {
/*  815:1216 */         return cp.addLongInfo(this.value);
/*  816:     */       }
/*  817:1218 */       return 0;
/*  818:     */     }
/*  819:     */   }
/*  820:     */   
/*  821:     */   static class FloatInitializer
/*  822:     */     extends CtField.Initializer
/*  823:     */   {
/*  824:     */     float value;
/*  825:     */     
/*  826:     */     FloatInitializer(float v)
/*  827:     */     {
/*  828:1225 */       this.value = v;
/*  829:     */     }
/*  830:     */     
/*  831:     */     void check(String desc)
/*  832:     */       throws CannotCompileException
/*  833:     */     {
/*  834:1228 */       if (!desc.equals("F")) {
/*  835:1229 */         throw new CannotCompileException("type mismatch");
/*  836:     */       }
/*  837:     */     }
/*  838:     */     
/*  839:     */     int compile(CtClass type, String name, Bytecode code, CtClass[] parameters, Javac drv)
/*  840:     */       throws CannotCompileException
/*  841:     */     {
/*  842:1236 */       code.addAload(0);
/*  843:1237 */       code.addFconst(this.value);
/*  844:1238 */       code.addPutfield(Bytecode.THIS, name, Descriptor.of(type));
/*  845:1239 */       return 3;
/*  846:     */     }
/*  847:     */     
/*  848:     */     int compileIfStatic(CtClass type, String name, Bytecode code, Javac drv)
/*  849:     */       throws CannotCompileException
/*  850:     */     {
/*  851:1245 */       code.addFconst(this.value);
/*  852:1246 */       code.addPutstatic(Bytecode.THIS, name, Descriptor.of(type));
/*  853:1247 */       return 2;
/*  854:     */     }
/*  855:     */     
/*  856:     */     int getConstantValue(ConstPool cp, CtClass type)
/*  857:     */     {
/*  858:1251 */       if (type == CtClass.floatType) {
/*  859:1252 */         return cp.addFloatInfo(this.value);
/*  860:     */       }
/*  861:1254 */       return 0;
/*  862:     */     }
/*  863:     */   }
/*  864:     */   
/*  865:     */   static class DoubleInitializer
/*  866:     */     extends CtField.Initializer
/*  867:     */   {
/*  868:     */     double value;
/*  869:     */     
/*  870:     */     DoubleInitializer(double v)
/*  871:     */     {
/*  872:1261 */       this.value = v;
/*  873:     */     }
/*  874:     */     
/*  875:     */     void check(String desc)
/*  876:     */       throws CannotCompileException
/*  877:     */     {
/*  878:1264 */       if (!desc.equals("D")) {
/*  879:1265 */         throw new CannotCompileException("type mismatch");
/*  880:     */       }
/*  881:     */     }
/*  882:     */     
/*  883:     */     int compile(CtClass type, String name, Bytecode code, CtClass[] parameters, Javac drv)
/*  884:     */       throws CannotCompileException
/*  885:     */     {
/*  886:1272 */       code.addAload(0);
/*  887:1273 */       code.addLdc2w(this.value);
/*  888:1274 */       code.addPutfield(Bytecode.THIS, name, Descriptor.of(type));
/*  889:1275 */       return 3;
/*  890:     */     }
/*  891:     */     
/*  892:     */     int compileIfStatic(CtClass type, String name, Bytecode code, Javac drv)
/*  893:     */       throws CannotCompileException
/*  894:     */     {
/*  895:1281 */       code.addLdc2w(this.value);
/*  896:1282 */       code.addPutstatic(Bytecode.THIS, name, Descriptor.of(type));
/*  897:1283 */       return 2;
/*  898:     */     }
/*  899:     */     
/*  900:     */     int getConstantValue(ConstPool cp, CtClass type)
/*  901:     */     {
/*  902:1287 */       if (type == CtClass.doubleType) {
/*  903:1288 */         return cp.addDoubleInfo(this.value);
/*  904:     */       }
/*  905:1290 */       return 0;
/*  906:     */     }
/*  907:     */   }
/*  908:     */   
/*  909:     */   static class StringInitializer
/*  910:     */     extends CtField.Initializer
/*  911:     */   {
/*  912:     */     String value;
/*  913:     */     
/*  914:     */     StringInitializer(String v)
/*  915:     */     {
/*  916:1297 */       this.value = v;
/*  917:     */     }
/*  918:     */     
/*  919:     */     int compile(CtClass type, String name, Bytecode code, CtClass[] parameters, Javac drv)
/*  920:     */       throws CannotCompileException
/*  921:     */     {
/*  922:1303 */       code.addAload(0);
/*  923:1304 */       code.addLdc(this.value);
/*  924:1305 */       code.addPutfield(Bytecode.THIS, name, Descriptor.of(type));
/*  925:1306 */       return 2;
/*  926:     */     }
/*  927:     */     
/*  928:     */     int compileIfStatic(CtClass type, String name, Bytecode code, Javac drv)
/*  929:     */       throws CannotCompileException
/*  930:     */     {
/*  931:1312 */       code.addLdc(this.value);
/*  932:1313 */       code.addPutstatic(Bytecode.THIS, name, Descriptor.of(type));
/*  933:1314 */       return 1;
/*  934:     */     }
/*  935:     */     
/*  936:     */     int getConstantValue(ConstPool cp, CtClass type)
/*  937:     */     {
/*  938:1318 */       if (type.getName().equals("java.lang.String")) {
/*  939:1319 */         return cp.addStringInfo(this.value);
/*  940:     */       }
/*  941:1321 */       return 0;
/*  942:     */     }
/*  943:     */   }
/*  944:     */   
/*  945:     */   static class ArrayInitializer
/*  946:     */     extends CtField.Initializer
/*  947:     */   {
/*  948:     */     CtClass type;
/*  949:     */     int size;
/*  950:     */     
/*  951:     */     ArrayInitializer(CtClass t, int s)
/*  952:     */     {
/*  953:1329 */       this.type = t;this.size = s;
/*  954:     */     }
/*  955:     */     
/*  956:     */     private void addNewarray(Bytecode code)
/*  957:     */     {
/*  958:1332 */       if (this.type.isPrimitive()) {
/*  959:1333 */         code.addNewarray(((CtPrimitiveType)this.type).getArrayType(), this.size);
/*  960:     */       } else {
/*  961:1336 */         code.addAnewarray(this.type, this.size);
/*  962:     */       }
/*  963:     */     }
/*  964:     */     
/*  965:     */     int compile(CtClass type, String name, Bytecode code, CtClass[] parameters, Javac drv)
/*  966:     */       throws CannotCompileException
/*  967:     */     {
/*  968:1343 */       code.addAload(0);
/*  969:1344 */       addNewarray(code);
/*  970:1345 */       code.addPutfield(Bytecode.THIS, name, Descriptor.of(type));
/*  971:1346 */       return 2;
/*  972:     */     }
/*  973:     */     
/*  974:     */     int compileIfStatic(CtClass type, String name, Bytecode code, Javac drv)
/*  975:     */       throws CannotCompileException
/*  976:     */     {
/*  977:1352 */       addNewarray(code);
/*  978:1353 */       code.addPutstatic(Bytecode.THIS, name, Descriptor.of(type));
/*  979:1354 */       return 1;
/*  980:     */     }
/*  981:     */   }
/*  982:     */   
/*  983:     */   static class MultiArrayInitializer
/*  984:     */     extends CtField.Initializer
/*  985:     */   {
/*  986:     */     CtClass type;
/*  987:     */     int[] dim;
/*  988:     */     
/*  989:     */     MultiArrayInitializer(CtClass t, int[] d)
/*  990:     */     {
/*  991:1362 */       this.type = t;this.dim = d;
/*  992:     */     }
/*  993:     */     
/*  994:     */     void check(String desc)
/*  995:     */       throws CannotCompileException
/*  996:     */     {
/*  997:1365 */       if (desc.charAt(0) != '[') {
/*  998:1366 */         throw new CannotCompileException("type mismatch");
/*  999:     */       }
/* 1000:     */     }
/* 1001:     */     
/* 1002:     */     int compile(CtClass type, String name, Bytecode code, CtClass[] parameters, Javac drv)
/* 1003:     */       throws CannotCompileException
/* 1004:     */     {
/* 1005:1373 */       code.addAload(0);
/* 1006:1374 */       int s = code.addMultiNewarray(type, this.dim);
/* 1007:1375 */       code.addPutfield(Bytecode.THIS, name, Descriptor.of(type));
/* 1008:1376 */       return s + 1;
/* 1009:     */     }
/* 1010:     */     
/* 1011:     */     int compileIfStatic(CtClass type, String name, Bytecode code, Javac drv)
/* 1012:     */       throws CannotCompileException
/* 1013:     */     {
/* 1014:1382 */       int s = code.addMultiNewarray(type, this.dim);
/* 1015:1383 */       code.addPutstatic(Bytecode.THIS, name, Descriptor.of(type));
/* 1016:1384 */       return s;
/* 1017:     */     }
/* 1018:     */   }
/* 1019:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.CtField
 * JD-Core Version:    0.7.0.1
 */