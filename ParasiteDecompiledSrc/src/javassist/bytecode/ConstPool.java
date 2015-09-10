/*    1:     */ package javassist.bytecode;
/*    2:     */ 
/*    3:     */ import java.io.DataInputStream;
/*    4:     */ import java.io.DataOutputStream;
/*    5:     */ import java.io.IOException;
/*    6:     */ import java.io.PrintWriter;
/*    7:     */ import java.util.HashMap;
/*    8:     */ import java.util.HashSet;
/*    9:     */ import java.util.Map;
/*   10:     */ import java.util.Set;
/*   11:     */ import javassist.CtClass;
/*   12:     */ 
/*   13:     */ public final class ConstPool
/*   14:     */ {
/*   15:     */   LongVector items;
/*   16:     */   int numOfItems;
/*   17:     */   HashMap classes;
/*   18:     */   HashMap strings;
/*   19:     */   ConstInfo[] constInfoCache;
/*   20:     */   int[] constInfoIndexCache;
/*   21:     */   int thisClassInfo;
/*   22:     */   private static final int CACHE_SIZE = 32;
/*   23:     */   public static final int CONST_Class = 7;
/*   24:     */   public static final int CONST_Fieldref = 9;
/*   25:     */   public static final int CONST_Methodref = 10;
/*   26:     */   public static final int CONST_InterfaceMethodref = 11;
/*   27:     */   public static final int CONST_String = 8;
/*   28:     */   public static final int CONST_Integer = 3;
/*   29:     */   public static final int CONST_Float = 4;
/*   30:     */   public static final int CONST_Long = 5;
/*   31:     */   public static final int CONST_Double = 6;
/*   32:     */   public static final int CONST_NameAndType = 12;
/*   33:     */   public static final int CONST_Utf8 = 1;
/*   34:     */   
/*   35:     */   private static int hashFunc(int a, int b)
/*   36:     */   {
/*   37:  48 */     int h = -2128831035;
/*   38:  49 */     int prime = 16777619;
/*   39:  50 */     h = (h ^ a & 0xFF) * 16777619;
/*   40:  51 */     h = (h ^ b & 0xFF) * 16777619;
/*   41:     */     
/*   42:     */ 
/*   43:  54 */     h = h >> 5 ^ h & 0x1F;
/*   44:  55 */     return h & 0x1F;
/*   45:     */   }
/*   46:     */   
/*   47: 117 */   public static final CtClass THIS = null;
/*   48:     */   
/*   49:     */   public ConstPool(String thisclass)
/*   50:     */   {
/*   51: 126 */     this.items = new LongVector();
/*   52: 127 */     this.numOfItems = 0;
/*   53: 128 */     addItem(null);
/*   54: 129 */     this.classes = new HashMap();
/*   55: 130 */     this.strings = new HashMap();
/*   56: 131 */     this.constInfoCache = new ConstInfo[32];
/*   57: 132 */     this.constInfoIndexCache = new int[32];
/*   58: 133 */     this.thisClassInfo = addClassInfo(thisclass);
/*   59:     */   }
/*   60:     */   
/*   61:     */   public ConstPool(DataInputStream in)
/*   62:     */     throws IOException
/*   63:     */   {
/*   64: 142 */     this.classes = new HashMap();
/*   65: 143 */     this.strings = new HashMap();
/*   66: 144 */     this.constInfoCache = new ConstInfo[32];
/*   67: 145 */     this.constInfoIndexCache = new int[32];
/*   68: 146 */     this.thisClassInfo = 0;
/*   69:     */     
/*   70:     */ 
/*   71: 149 */     read(in);
/*   72:     */   }
/*   73:     */   
/*   74:     */   void prune()
/*   75:     */   {
/*   76: 153 */     this.classes = new HashMap();
/*   77: 154 */     this.strings = new HashMap();
/*   78: 155 */     this.constInfoCache = new ConstInfo[32];
/*   79: 156 */     this.constInfoIndexCache = new int[32];
/*   80:     */   }
/*   81:     */   
/*   82:     */   public int getSize()
/*   83:     */   {
/*   84: 163 */     return this.numOfItems;
/*   85:     */   }
/*   86:     */   
/*   87:     */   public String getClassName()
/*   88:     */   {
/*   89: 170 */     return getClassInfo(this.thisClassInfo);
/*   90:     */   }
/*   91:     */   
/*   92:     */   public int getThisClassInfo()
/*   93:     */   {
/*   94: 178 */     return this.thisClassInfo;
/*   95:     */   }
/*   96:     */   
/*   97:     */   void setThisClassInfo(int i)
/*   98:     */   {
/*   99: 182 */     this.thisClassInfo = i;
/*  100:     */   }
/*  101:     */   
/*  102:     */   ConstInfo getItem(int n)
/*  103:     */   {
/*  104: 186 */     return this.items.elementAt(n);
/*  105:     */   }
/*  106:     */   
/*  107:     */   public int getTag(int index)
/*  108:     */   {
/*  109: 194 */     return getItem(index).getTag();
/*  110:     */   }
/*  111:     */   
/*  112:     */   public String getClassInfo(int index)
/*  113:     */   {
/*  114: 209 */     ClassInfo c = (ClassInfo)getItem(index);
/*  115: 210 */     if (c == null) {
/*  116: 211 */       return null;
/*  117:     */     }
/*  118: 213 */     return Descriptor.toJavaName(getUtf8Info(c.name));
/*  119:     */   }
/*  120:     */   
/*  121:     */   public int getNameAndTypeName(int index)
/*  122:     */   {
/*  123: 222 */     NameAndTypeInfo ntinfo = (NameAndTypeInfo)getItem(index);
/*  124: 223 */     return ntinfo.memberName;
/*  125:     */   }
/*  126:     */   
/*  127:     */   public int getNameAndTypeDescriptor(int index)
/*  128:     */   {
/*  129: 232 */     NameAndTypeInfo ntinfo = (NameAndTypeInfo)getItem(index);
/*  130: 233 */     return ntinfo.typeDescriptor;
/*  131:     */   }
/*  132:     */   
/*  133:     */   public int getMemberClass(int index)
/*  134:     */   {
/*  135: 246 */     MemberrefInfo minfo = (MemberrefInfo)getItem(index);
/*  136: 247 */     return minfo.classIndex;
/*  137:     */   }
/*  138:     */   
/*  139:     */   public int getMemberNameAndType(int index)
/*  140:     */   {
/*  141: 260 */     MemberrefInfo minfo = (MemberrefInfo)getItem(index);
/*  142: 261 */     return minfo.nameAndTypeIndex;
/*  143:     */   }
/*  144:     */   
/*  145:     */   public int getFieldrefClass(int index)
/*  146:     */   {
/*  147: 270 */     FieldrefInfo finfo = (FieldrefInfo)getItem(index);
/*  148: 271 */     return finfo.classIndex;
/*  149:     */   }
/*  150:     */   
/*  151:     */   public String getFieldrefClassName(int index)
/*  152:     */   {
/*  153: 282 */     FieldrefInfo f = (FieldrefInfo)getItem(index);
/*  154: 283 */     if (f == null) {
/*  155: 284 */       return null;
/*  156:     */     }
/*  157: 286 */     return getClassInfo(f.classIndex);
/*  158:     */   }
/*  159:     */   
/*  160:     */   public int getFieldrefNameAndType(int index)
/*  161:     */   {
/*  162: 295 */     FieldrefInfo finfo = (FieldrefInfo)getItem(index);
/*  163: 296 */     return finfo.nameAndTypeIndex;
/*  164:     */   }
/*  165:     */   
/*  166:     */   public String getFieldrefName(int index)
/*  167:     */   {
/*  168: 308 */     FieldrefInfo f = (FieldrefInfo)getItem(index);
/*  169: 309 */     if (f == null) {
/*  170: 310 */       return null;
/*  171:     */     }
/*  172: 312 */     NameAndTypeInfo n = (NameAndTypeInfo)getItem(f.nameAndTypeIndex);
/*  173: 313 */     if (n == null) {
/*  174: 314 */       return null;
/*  175:     */     }
/*  176: 316 */     return getUtf8Info(n.memberName);
/*  177:     */   }
/*  178:     */   
/*  179:     */   public String getFieldrefType(int index)
/*  180:     */   {
/*  181: 329 */     FieldrefInfo f = (FieldrefInfo)getItem(index);
/*  182: 330 */     if (f == null) {
/*  183: 331 */       return null;
/*  184:     */     }
/*  185: 333 */     NameAndTypeInfo n = (NameAndTypeInfo)getItem(f.nameAndTypeIndex);
/*  186: 334 */     if (n == null) {
/*  187: 335 */       return null;
/*  188:     */     }
/*  189: 337 */     return getUtf8Info(n.typeDescriptor);
/*  190:     */   }
/*  191:     */   
/*  192:     */   public int getMethodrefClass(int index)
/*  193:     */   {
/*  194: 347 */     MethodrefInfo minfo = (MethodrefInfo)getItem(index);
/*  195: 348 */     return minfo.classIndex;
/*  196:     */   }
/*  197:     */   
/*  198:     */   public String getMethodrefClassName(int index)
/*  199:     */   {
/*  200: 359 */     MethodrefInfo minfo = (MethodrefInfo)getItem(index);
/*  201: 360 */     if (minfo == null) {
/*  202: 361 */       return null;
/*  203:     */     }
/*  204: 363 */     return getClassInfo(minfo.classIndex);
/*  205:     */   }
/*  206:     */   
/*  207:     */   public int getMethodrefNameAndType(int index)
/*  208:     */   {
/*  209: 372 */     MethodrefInfo minfo = (MethodrefInfo)getItem(index);
/*  210: 373 */     return minfo.nameAndTypeIndex;
/*  211:     */   }
/*  212:     */   
/*  213:     */   public String getMethodrefName(int index)
/*  214:     */   {
/*  215: 385 */     MethodrefInfo minfo = (MethodrefInfo)getItem(index);
/*  216: 386 */     if (minfo == null) {
/*  217: 387 */       return null;
/*  218:     */     }
/*  219: 389 */     NameAndTypeInfo n = (NameAndTypeInfo)getItem(minfo.nameAndTypeIndex);
/*  220: 391 */     if (n == null) {
/*  221: 392 */       return null;
/*  222:     */     }
/*  223: 394 */     return getUtf8Info(n.memberName);
/*  224:     */   }
/*  225:     */   
/*  226:     */   public String getMethodrefType(int index)
/*  227:     */   {
/*  228: 407 */     MethodrefInfo minfo = (MethodrefInfo)getItem(index);
/*  229: 408 */     if (minfo == null) {
/*  230: 409 */       return null;
/*  231:     */     }
/*  232: 411 */     NameAndTypeInfo n = (NameAndTypeInfo)getItem(minfo.nameAndTypeIndex);
/*  233: 413 */     if (n == null) {
/*  234: 414 */       return null;
/*  235:     */     }
/*  236: 416 */     return getUtf8Info(n.typeDescriptor);
/*  237:     */   }
/*  238:     */   
/*  239:     */   public int getInterfaceMethodrefClass(int index)
/*  240:     */   {
/*  241: 426 */     InterfaceMethodrefInfo minfo = (InterfaceMethodrefInfo)getItem(index);
/*  242:     */     
/*  243: 428 */     return minfo.classIndex;
/*  244:     */   }
/*  245:     */   
/*  246:     */   public String getInterfaceMethodrefClassName(int index)
/*  247:     */   {
/*  248: 439 */     InterfaceMethodrefInfo minfo = (InterfaceMethodrefInfo)getItem(index);
/*  249:     */     
/*  250: 441 */     return getClassInfo(minfo.classIndex);
/*  251:     */   }
/*  252:     */   
/*  253:     */   public int getInterfaceMethodrefNameAndType(int index)
/*  254:     */   {
/*  255: 450 */     InterfaceMethodrefInfo minfo = (InterfaceMethodrefInfo)getItem(index);
/*  256:     */     
/*  257: 452 */     return minfo.nameAndTypeIndex;
/*  258:     */   }
/*  259:     */   
/*  260:     */   public String getInterfaceMethodrefName(int index)
/*  261:     */   {
/*  262: 465 */     InterfaceMethodrefInfo minfo = (InterfaceMethodrefInfo)getItem(index);
/*  263: 467 */     if (minfo == null) {
/*  264: 468 */       return null;
/*  265:     */     }
/*  266: 470 */     NameAndTypeInfo n = (NameAndTypeInfo)getItem(minfo.nameAndTypeIndex);
/*  267: 472 */     if (n == null) {
/*  268: 473 */       return null;
/*  269:     */     }
/*  270: 475 */     return getUtf8Info(n.memberName);
/*  271:     */   }
/*  272:     */   
/*  273:     */   public String getInterfaceMethodrefType(int index)
/*  274:     */   {
/*  275: 489 */     InterfaceMethodrefInfo minfo = (InterfaceMethodrefInfo)getItem(index);
/*  276: 491 */     if (minfo == null) {
/*  277: 492 */       return null;
/*  278:     */     }
/*  279: 494 */     NameAndTypeInfo n = (NameAndTypeInfo)getItem(minfo.nameAndTypeIndex);
/*  280: 496 */     if (n == null) {
/*  281: 497 */       return null;
/*  282:     */     }
/*  283: 499 */     return getUtf8Info(n.typeDescriptor);
/*  284:     */   }
/*  285:     */   
/*  286:     */   public Object getLdcValue(int index)
/*  287:     */   {
/*  288: 512 */     ConstInfo constInfo = getItem(index);
/*  289: 513 */     Object value = null;
/*  290: 514 */     if ((constInfo instanceof StringInfo)) {
/*  291: 515 */       value = getStringInfo(index);
/*  292: 516 */     } else if ((constInfo instanceof FloatInfo)) {
/*  293: 517 */       value = new Float(getFloatInfo(index));
/*  294: 518 */     } else if ((constInfo instanceof IntegerInfo)) {
/*  295: 519 */       value = new Integer(getIntegerInfo(index));
/*  296: 520 */     } else if ((constInfo instanceof LongInfo)) {
/*  297: 521 */       value = new Long(getLongInfo(index));
/*  298: 522 */     } else if ((constInfo instanceof DoubleInfo)) {
/*  299: 523 */       value = new Double(getDoubleInfo(index));
/*  300:     */     } else {
/*  301: 525 */       value = null;
/*  302:     */     }
/*  303: 527 */     return value;
/*  304:     */   }
/*  305:     */   
/*  306:     */   public int getIntegerInfo(int index)
/*  307:     */   {
/*  308: 537 */     IntegerInfo i = (IntegerInfo)getItem(index);
/*  309: 538 */     return i.value;
/*  310:     */   }
/*  311:     */   
/*  312:     */   public float getFloatInfo(int index)
/*  313:     */   {
/*  314: 548 */     FloatInfo i = (FloatInfo)getItem(index);
/*  315: 549 */     return i.value;
/*  316:     */   }
/*  317:     */   
/*  318:     */   public long getLongInfo(int index)
/*  319:     */   {
/*  320: 559 */     LongInfo i = (LongInfo)getItem(index);
/*  321: 560 */     return i.value;
/*  322:     */   }
/*  323:     */   
/*  324:     */   public double getDoubleInfo(int index)
/*  325:     */   {
/*  326: 570 */     DoubleInfo i = (DoubleInfo)getItem(index);
/*  327: 571 */     return i.value;
/*  328:     */   }
/*  329:     */   
/*  330:     */   public String getStringInfo(int index)
/*  331:     */   {
/*  332: 581 */     StringInfo si = (StringInfo)getItem(index);
/*  333: 582 */     return getUtf8Info(si.string);
/*  334:     */   }
/*  335:     */   
/*  336:     */   public String getUtf8Info(int index)
/*  337:     */   {
/*  338: 592 */     Utf8Info utf = (Utf8Info)getItem(index);
/*  339: 593 */     return utf.string;
/*  340:     */   }
/*  341:     */   
/*  342:     */   public int isConstructor(String classname, int index)
/*  343:     */   {
/*  344: 607 */     return isMember(classname, "<init>", index);
/*  345:     */   }
/*  346:     */   
/*  347:     */   public int isMember(String classname, String membername, int index)
/*  348:     */   {
/*  349: 627 */     MemberrefInfo minfo = (MemberrefInfo)getItem(index);
/*  350: 628 */     if (getClassInfo(minfo.classIndex).equals(classname))
/*  351:     */     {
/*  352: 629 */       NameAndTypeInfo ntinfo = (NameAndTypeInfo)getItem(minfo.nameAndTypeIndex);
/*  353: 631 */       if (getUtf8Info(ntinfo.memberName).equals(membername)) {
/*  354: 632 */         return ntinfo.typeDescriptor;
/*  355:     */       }
/*  356:     */     }
/*  357: 635 */     return 0;
/*  358:     */   }
/*  359:     */   
/*  360:     */   public String eqMember(String membername, String desc, int index)
/*  361:     */   {
/*  362: 656 */     MemberrefInfo minfo = (MemberrefInfo)getItem(index);
/*  363: 657 */     NameAndTypeInfo ntinfo = (NameAndTypeInfo)getItem(minfo.nameAndTypeIndex);
/*  364: 659 */     if ((getUtf8Info(ntinfo.memberName).equals(membername)) && (getUtf8Info(ntinfo.typeDescriptor).equals(desc))) {
/*  365: 661 */       return getClassInfo(minfo.classIndex);
/*  366:     */     }
/*  367: 663 */     return null;
/*  368:     */   }
/*  369:     */   
/*  370:     */   private int addItem(ConstInfo info)
/*  371:     */   {
/*  372: 667 */     this.items.addElement(info);
/*  373: 668 */     return this.numOfItems++;
/*  374:     */   }
/*  375:     */   
/*  376:     */   public int copy(int n, ConstPool dest, Map classnames)
/*  377:     */   {
/*  378: 683 */     if (n == 0) {
/*  379: 684 */       return 0;
/*  380:     */     }
/*  381: 686 */     ConstInfo info = getItem(n);
/*  382: 687 */     return info.copy(this, dest, classnames);
/*  383:     */   }
/*  384:     */   
/*  385:     */   int addConstInfoPadding()
/*  386:     */   {
/*  387: 691 */     return addItem(new ConstInfoPadding());
/*  388:     */   }
/*  389:     */   
/*  390:     */   public int addClassInfo(CtClass c)
/*  391:     */   {
/*  392: 703 */     if (c == THIS) {
/*  393: 704 */       return this.thisClassInfo;
/*  394:     */     }
/*  395: 705 */     if (!c.isArray()) {
/*  396: 706 */       return addClassInfo(c.getName());
/*  397:     */     }
/*  398: 713 */     return addClassInfo(Descriptor.toJvmName(c));
/*  399:     */   }
/*  400:     */   
/*  401:     */   public int addClassInfo(String qname)
/*  402:     */   {
/*  403: 728 */     ClassInfo info = (ClassInfo)this.classes.get(qname);
/*  404: 729 */     if (info != null) {
/*  405: 730 */       return info.index;
/*  406:     */     }
/*  407: 732 */     int utf8 = addUtf8Info(Descriptor.toJvmName(qname));
/*  408: 733 */     info = new ClassInfo(utf8, this.numOfItems);
/*  409: 734 */     this.classes.put(qname, info);
/*  410: 735 */     return addItem(info);
/*  411:     */   }
/*  412:     */   
/*  413:     */   public int addNameAndTypeInfo(String name, String type)
/*  414:     */   {
/*  415: 749 */     return addNameAndTypeInfo(addUtf8Info(name), addUtf8Info(type));
/*  416:     */   }
/*  417:     */   
/*  418:     */   public int addNameAndTypeInfo(int name, int type)
/*  419:     */   {
/*  420: 760 */     int h = hashFunc(name, type);
/*  421: 761 */     ConstInfo ci = this.constInfoCache[h];
/*  422: 762 */     if ((ci != null) && ((ci instanceof NameAndTypeInfo)) && (ci.hashCheck(name, type))) {
/*  423: 763 */       return this.constInfoIndexCache[h];
/*  424:     */     }
/*  425: 765 */     NameAndTypeInfo item = new NameAndTypeInfo(name, type);
/*  426: 766 */     this.constInfoCache[h] = item;
/*  427: 767 */     int i = addItem(item);
/*  428: 768 */     this.constInfoIndexCache[h] = i;
/*  429: 769 */     return i;
/*  430:     */   }
/*  431:     */   
/*  432:     */   public int addFieldrefInfo(int classInfo, String name, String type)
/*  433:     */   {
/*  434: 787 */     int nt = addNameAndTypeInfo(name, type);
/*  435: 788 */     return addFieldrefInfo(classInfo, nt);
/*  436:     */   }
/*  437:     */   
/*  438:     */   public int addFieldrefInfo(int classInfo, int nameAndTypeInfo)
/*  439:     */   {
/*  440: 799 */     int h = hashFunc(classInfo, nameAndTypeInfo);
/*  441: 800 */     ConstInfo ci = this.constInfoCache[h];
/*  442: 801 */     if ((ci != null) && ((ci instanceof FieldrefInfo)) && (ci.hashCheck(classInfo, nameAndTypeInfo))) {
/*  443: 802 */       return this.constInfoIndexCache[h];
/*  444:     */     }
/*  445: 804 */     FieldrefInfo item = new FieldrefInfo(classInfo, nameAndTypeInfo);
/*  446: 805 */     this.constInfoCache[h] = item;
/*  447: 806 */     int i = addItem(item);
/*  448: 807 */     this.constInfoIndexCache[h] = i;
/*  449: 808 */     return i;
/*  450:     */   }
/*  451:     */   
/*  452:     */   public int addMethodrefInfo(int classInfo, String name, String type)
/*  453:     */   {
/*  454: 826 */     int nt = addNameAndTypeInfo(name, type);
/*  455: 827 */     return addMethodrefInfo(classInfo, nt);
/*  456:     */   }
/*  457:     */   
/*  458:     */   public int addMethodrefInfo(int classInfo, int nameAndTypeInfo)
/*  459:     */   {
/*  460: 838 */     int h = hashFunc(classInfo, nameAndTypeInfo);
/*  461: 839 */     ConstInfo ci = this.constInfoCache[h];
/*  462: 840 */     if ((ci != null) && ((ci instanceof MethodrefInfo)) && (ci.hashCheck(classInfo, nameAndTypeInfo))) {
/*  463: 841 */       return this.constInfoIndexCache[h];
/*  464:     */     }
/*  465: 843 */     MethodrefInfo item = new MethodrefInfo(classInfo, nameAndTypeInfo);
/*  466: 844 */     this.constInfoCache[h] = item;
/*  467: 845 */     int i = addItem(item);
/*  468: 846 */     this.constInfoIndexCache[h] = i;
/*  469: 847 */     return i;
/*  470:     */   }
/*  471:     */   
/*  472:     */   public int addInterfaceMethodrefInfo(int classInfo, String name, String type)
/*  473:     */   {
/*  474: 867 */     int nt = addNameAndTypeInfo(name, type);
/*  475: 868 */     return addInterfaceMethodrefInfo(classInfo, nt);
/*  476:     */   }
/*  477:     */   
/*  478:     */   public int addInterfaceMethodrefInfo(int classInfo, int nameAndTypeInfo)
/*  479:     */   {
/*  480: 881 */     int h = hashFunc(classInfo, nameAndTypeInfo);
/*  481: 882 */     ConstInfo ci = this.constInfoCache[h];
/*  482: 883 */     if ((ci != null) && ((ci instanceof InterfaceMethodrefInfo)) && (ci.hashCheck(classInfo, nameAndTypeInfo))) {
/*  483: 884 */       return this.constInfoIndexCache[h];
/*  484:     */     }
/*  485: 886 */     InterfaceMethodrefInfo item = new InterfaceMethodrefInfo(classInfo, nameAndTypeInfo);
/*  486: 887 */     this.constInfoCache[h] = item;
/*  487: 888 */     int i = addItem(item);
/*  488: 889 */     this.constInfoIndexCache[h] = i;
/*  489: 890 */     return i;
/*  490:     */   }
/*  491:     */   
/*  492:     */   public int addStringInfo(String str)
/*  493:     */   {
/*  494: 904 */     return addItem(new StringInfo(addUtf8Info(str)));
/*  495:     */   }
/*  496:     */   
/*  497:     */   public int addIntegerInfo(int i)
/*  498:     */   {
/*  499: 914 */     return addItem(new IntegerInfo(i));
/*  500:     */   }
/*  501:     */   
/*  502:     */   public int addFloatInfo(float f)
/*  503:     */   {
/*  504: 924 */     return addItem(new FloatInfo(f));
/*  505:     */   }
/*  506:     */   
/*  507:     */   public int addLongInfo(long l)
/*  508:     */   {
/*  509: 934 */     int i = addItem(new LongInfo(l));
/*  510: 935 */     addItem(new ConstInfoPadding());
/*  511: 936 */     return i;
/*  512:     */   }
/*  513:     */   
/*  514:     */   public int addDoubleInfo(double d)
/*  515:     */   {
/*  516: 946 */     int i = addItem(new DoubleInfo(d));
/*  517: 947 */     addItem(new ConstInfoPadding());
/*  518: 948 */     return i;
/*  519:     */   }
/*  520:     */   
/*  521:     */   public int addUtf8Info(String utf8)
/*  522:     */   {
/*  523: 963 */     Utf8Info info = (Utf8Info)this.strings.get(utf8);
/*  524: 964 */     if (info != null) {
/*  525: 965 */       return info.index;
/*  526:     */     }
/*  527: 967 */     info = new Utf8Info(utf8, this.numOfItems);
/*  528: 968 */     this.strings.put(utf8, info);
/*  529: 969 */     return addItem(info);
/*  530:     */   }
/*  531:     */   
/*  532:     */   public Set getClassNames()
/*  533:     */   {
/*  534: 980 */     HashSet result = new HashSet();
/*  535: 981 */     LongVector v = this.items;
/*  536: 982 */     int size = this.numOfItems;
/*  537: 983 */     for (int i = 1; i < size; i++)
/*  538:     */     {
/*  539: 984 */       String className = v.elementAt(i).getClassName(this);
/*  540: 985 */       if (className != null) {
/*  541: 986 */         result.add(className);
/*  542:     */       }
/*  543:     */     }
/*  544: 988 */     return result;
/*  545:     */   }
/*  546:     */   
/*  547:     */   public void renameClass(String oldName, String newName)
/*  548:     */   {
/*  549: 998 */     LongVector v = this.items;
/*  550: 999 */     int size = this.numOfItems;
/*  551:1000 */     this.classes = new HashMap(this.classes.size() * 2);
/*  552:1001 */     for (int i = 1; i < size; i++)
/*  553:     */     {
/*  554:1002 */       ConstInfo ci = v.elementAt(i);
/*  555:1003 */       ci.renameClass(this, oldName, newName);
/*  556:1004 */       ci.makeHashtable(this);
/*  557:     */     }
/*  558:     */   }
/*  559:     */   
/*  560:     */   public void renameClass(Map classnames)
/*  561:     */   {
/*  562:1015 */     LongVector v = this.items;
/*  563:1016 */     int size = this.numOfItems;
/*  564:1017 */     this.classes = new HashMap(this.classes.size() * 2);
/*  565:1018 */     for (int i = 1; i < size; i++)
/*  566:     */     {
/*  567:1019 */       ConstInfo ci = v.elementAt(i);
/*  568:1020 */       ci.renameClass(this, classnames);
/*  569:1021 */       ci.makeHashtable(this);
/*  570:     */     }
/*  571:     */   }
/*  572:     */   
/*  573:     */   private void read(DataInputStream in)
/*  574:     */     throws IOException
/*  575:     */   {
/*  576:1026 */     int n = in.readUnsignedShort();
/*  577:     */     
/*  578:1028 */     this.items = new LongVector(n);
/*  579:1029 */     this.numOfItems = 0;
/*  580:1030 */     addItem(null);
/*  581:     */     for (;;)
/*  582:     */     {
/*  583:1032 */       n--;
/*  584:1032 */       if (n <= 0) {
/*  585:     */         break;
/*  586:     */       }
/*  587:1033 */       int tag = readOne(in);
/*  588:1034 */       if ((tag == 5) || (tag == 6))
/*  589:     */       {
/*  590:1035 */         addItem(new ConstInfoPadding());
/*  591:1036 */         n--;
/*  592:     */       }
/*  593:     */     }
/*  594:1040 */     int i = 1;
/*  595:     */     for (;;)
/*  596:     */     {
/*  597:1042 */       ConstInfo info = this.items.elementAt(i++);
/*  598:1043 */       if (info == null) {
/*  599:     */         break;
/*  600:     */       }
/*  601:1046 */       info.makeHashtable(this);
/*  602:     */     }
/*  603:     */   }
/*  604:     */   
/*  605:     */   private int readOne(DataInputStream in)
/*  606:     */     throws IOException
/*  607:     */   {
/*  608:1052 */     int tag = in.readUnsignedByte();
/*  609:     */     ConstInfo info;
/*  610:1053 */     switch (tag)
/*  611:     */     {
/*  612:     */     case 1: 
/*  613:1055 */       info = new Utf8Info(in, this.numOfItems);
/*  614:1056 */       this.strings.put(((Utf8Info)info).string, info);
/*  615:1057 */       break;
/*  616:     */     case 3: 
/*  617:1059 */       info = new IntegerInfo(in);
/*  618:1060 */       break;
/*  619:     */     case 4: 
/*  620:1062 */       info = new FloatInfo(in);
/*  621:1063 */       break;
/*  622:     */     case 5: 
/*  623:1065 */       info = new LongInfo(in);
/*  624:1066 */       break;
/*  625:     */     case 6: 
/*  626:1068 */       info = new DoubleInfo(in);
/*  627:1069 */       break;
/*  628:     */     case 7: 
/*  629:1071 */       info = new ClassInfo(in, this.numOfItems);
/*  630:     */       
/*  631:1073 */       break;
/*  632:     */     case 8: 
/*  633:1075 */       info = new StringInfo(in);
/*  634:1076 */       break;
/*  635:     */     case 9: 
/*  636:1078 */       info = new FieldrefInfo(in);
/*  637:1079 */       break;
/*  638:     */     case 10: 
/*  639:1081 */       info = new MethodrefInfo(in);
/*  640:1082 */       break;
/*  641:     */     case 11: 
/*  642:1084 */       info = new InterfaceMethodrefInfo(in);
/*  643:1085 */       break;
/*  644:     */     case 12: 
/*  645:1087 */       info = new NameAndTypeInfo(in);
/*  646:1088 */       break;
/*  647:     */     case 2: 
/*  648:     */     default: 
/*  649:1090 */       throw new IOException("invalid constant type: " + tag);
/*  650:     */     }
/*  651:1093 */     addItem(info);
/*  652:1094 */     return tag;
/*  653:     */   }
/*  654:     */   
/*  655:     */   public void write(DataOutputStream out)
/*  656:     */     throws IOException
/*  657:     */   {
/*  658:1101 */     out.writeShort(this.numOfItems);
/*  659:1102 */     LongVector v = this.items;
/*  660:1103 */     int size = this.numOfItems;
/*  661:1104 */     for (int i = 1; i < size; i++) {
/*  662:1105 */       v.elementAt(i).write(out);
/*  663:     */     }
/*  664:     */   }
/*  665:     */   
/*  666:     */   public void print()
/*  667:     */   {
/*  668:1112 */     print(new PrintWriter(System.out, true));
/*  669:     */   }
/*  670:     */   
/*  671:     */   public void print(PrintWriter out)
/*  672:     */   {
/*  673:1119 */     int size = this.numOfItems;
/*  674:1120 */     for (int i = 1; i < size; i++)
/*  675:     */     {
/*  676:1121 */       out.print(i);
/*  677:1122 */       out.print(" ");
/*  678:1123 */       this.items.elementAt(i).print(out);
/*  679:     */     }
/*  680:     */   }
/*  681:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.ConstPool
 * JD-Core Version:    0.7.0.1
 */