/*    1:     */ package javassist.bytecode;
/*    2:     */ 
/*    3:     */ import javassist.CtClass;
/*    4:     */ import javassist.CtPrimitiveType;
/*    5:     */ 
/*    6:     */ public class Bytecode
/*    7:     */   extends ByteVector
/*    8:     */   implements Cloneable, Opcode
/*    9:     */ {
/*   10: 118 */   public static final CtClass THIS = ConstPool.THIS;
/*   11:     */   ConstPool constPool;
/*   12:     */   int maxStack;
/*   13:     */   int maxLocals;
/*   14:     */   ExceptionTable tryblocks;
/*   15:     */   private int stackDepth;
/*   16:     */   
/*   17:     */   public Bytecode(ConstPool cp, int stacksize, int localvars)
/*   18:     */   {
/*   19: 139 */     this.constPool = cp;
/*   20: 140 */     this.maxStack = stacksize;
/*   21: 141 */     this.maxLocals = localvars;
/*   22: 142 */     this.tryblocks = new ExceptionTable(cp);
/*   23: 143 */     this.stackDepth = 0;
/*   24:     */   }
/*   25:     */   
/*   26:     */   public Bytecode(ConstPool cp)
/*   27:     */   {
/*   28: 156 */     this(cp, 0, 0);
/*   29:     */   }
/*   30:     */   
/*   31:     */   public Object clone()
/*   32:     */   {
/*   33:     */     try
/*   34:     */     {
/*   35: 166 */       Bytecode bc = (Bytecode)super.clone();
/*   36: 167 */       bc.tryblocks = ((ExceptionTable)this.tryblocks.clone());
/*   37: 168 */       return bc;
/*   38:     */     }
/*   39:     */     catch (CloneNotSupportedException cnse)
/*   40:     */     {
/*   41: 171 */       throw new RuntimeException(cnse);
/*   42:     */     }
/*   43:     */   }
/*   44:     */   
/*   45:     */   public ConstPool getConstPool()
/*   46:     */   {
/*   47: 178 */     return this.constPool;
/*   48:     */   }
/*   49:     */   
/*   50:     */   public ExceptionTable getExceptionTable()
/*   51:     */   {
/*   52: 183 */     return this.tryblocks;
/*   53:     */   }
/*   54:     */   
/*   55:     */   public CodeAttribute toCodeAttribute()
/*   56:     */   {
/*   57: 189 */     return new CodeAttribute(this.constPool, this.maxStack, this.maxLocals, get(), this.tryblocks);
/*   58:     */   }
/*   59:     */   
/*   60:     */   public int length()
/*   61:     */   {
/*   62: 197 */     return getSize();
/*   63:     */   }
/*   64:     */   
/*   65:     */   public byte[] get()
/*   66:     */   {
/*   67: 204 */     return copy();
/*   68:     */   }
/*   69:     */   
/*   70:     */   public int getMaxStack()
/*   71:     */   {
/*   72: 210 */     return this.maxStack;
/*   73:     */   }
/*   74:     */   
/*   75:     */   public void setMaxStack(int size)
/*   76:     */   {
/*   77: 227 */     this.maxStack = size;
/*   78:     */   }
/*   79:     */   
/*   80:     */   public int getMaxLocals()
/*   81:     */   {
/*   82: 233 */     return this.maxLocals;
/*   83:     */   }
/*   84:     */   
/*   85:     */   public void setMaxLocals(int size)
/*   86:     */   {
/*   87: 239 */     this.maxLocals = size;
/*   88:     */   }
/*   89:     */   
/*   90:     */   public void setMaxLocals(boolean isStatic, CtClass[] params, int locals)
/*   91:     */   {
/*   92: 257 */     if (!isStatic) {
/*   93: 258 */       locals++;
/*   94:     */     }
/*   95: 260 */     if (params != null)
/*   96:     */     {
/*   97: 261 */       CtClass doubleType = CtClass.doubleType;
/*   98: 262 */       CtClass longType = CtClass.longType;
/*   99: 263 */       int n = params.length;
/*  100: 264 */       for (int i = 0; i < n; i++)
/*  101:     */       {
/*  102: 265 */         CtClass type = params[i];
/*  103: 266 */         if ((type == doubleType) || (type == longType)) {
/*  104: 267 */           locals += 2;
/*  105:     */         } else {
/*  106: 269 */           locals++;
/*  107:     */         }
/*  108:     */       }
/*  109:     */     }
/*  110: 273 */     this.maxLocals = locals;
/*  111:     */   }
/*  112:     */   
/*  113:     */   public void incMaxLocals(int diff)
/*  114:     */   {
/*  115: 280 */     this.maxLocals += diff;
/*  116:     */   }
/*  117:     */   
/*  118:     */   public void addExceptionHandler(int start, int end, int handler, CtClass type)
/*  119:     */   {
/*  120: 288 */     addExceptionHandler(start, end, handler, this.constPool.addClassInfo(type));
/*  121:     */   }
/*  122:     */   
/*  123:     */   public void addExceptionHandler(int start, int end, int handler, String type)
/*  124:     */   {
/*  125: 299 */     addExceptionHandler(start, end, handler, this.constPool.addClassInfo(type));
/*  126:     */   }
/*  127:     */   
/*  128:     */   public void addExceptionHandler(int start, int end, int handler, int type)
/*  129:     */   {
/*  130: 308 */     this.tryblocks.add(start, end, handler, type);
/*  131:     */   }
/*  132:     */   
/*  133:     */   public int currentPc()
/*  134:     */   {
/*  135: 316 */     return getSize();
/*  136:     */   }
/*  137:     */   
/*  138:     */   public int read(int offset)
/*  139:     */   {
/*  140: 326 */     return super.read(offset);
/*  141:     */   }
/*  142:     */   
/*  143:     */   public int read16bit(int offset)
/*  144:     */   {
/*  145: 334 */     int v1 = read(offset);
/*  146: 335 */     int v2 = read(offset + 1);
/*  147: 336 */     return (v1 << 8) + (v2 & 0xFF);
/*  148:     */   }
/*  149:     */   
/*  150:     */   public int read32bit(int offset)
/*  151:     */   {
/*  152: 344 */     int v1 = read16bit(offset);
/*  153: 345 */     int v2 = read16bit(offset + 2);
/*  154: 346 */     return (v1 << 16) + (v2 & 0xFFFF);
/*  155:     */   }
/*  156:     */   
/*  157:     */   public void write(int offset, int value)
/*  158:     */   {
/*  159: 356 */     super.write(offset, value);
/*  160:     */   }
/*  161:     */   
/*  162:     */   public void write16bit(int offset, int value)
/*  163:     */   {
/*  164: 364 */     write(offset, value >> 8);
/*  165: 365 */     write(offset + 1, value);
/*  166:     */   }
/*  167:     */   
/*  168:     */   public void write32bit(int offset, int value)
/*  169:     */   {
/*  170: 373 */     write16bit(offset, value >> 16);
/*  171: 374 */     write16bit(offset + 2, value);
/*  172:     */   }
/*  173:     */   
/*  174:     */   public void add(int code)
/*  175:     */   {
/*  176: 381 */     super.add(code);
/*  177:     */   }
/*  178:     */   
/*  179:     */   public void add32bit(int value)
/*  180:     */   {
/*  181: 388 */     add(value >> 24, value >> 16, value >> 8, value);
/*  182:     */   }
/*  183:     */   
/*  184:     */   public void addGap(int length)
/*  185:     */   {
/*  186: 397 */     super.addGap(length);
/*  187:     */   }
/*  188:     */   
/*  189:     */   public void addOpcode(int code)
/*  190:     */   {
/*  191: 412 */     add(code);
/*  192: 413 */     growStack(STACK_GROW[code]);
/*  193:     */   }
/*  194:     */   
/*  195:     */   public void growStack(int diff)
/*  196:     */   {
/*  197: 424 */     setStackDepth(this.stackDepth + diff);
/*  198:     */   }
/*  199:     */   
/*  200:     */   public int getStackDepth()
/*  201:     */   {
/*  202: 430 */     return this.stackDepth;
/*  203:     */   }
/*  204:     */   
/*  205:     */   public void setStackDepth(int depth)
/*  206:     */   {
/*  207: 440 */     this.stackDepth = depth;
/*  208: 441 */     if (this.stackDepth > this.maxStack) {
/*  209: 442 */       this.maxStack = this.stackDepth;
/*  210:     */     }
/*  211:     */   }
/*  212:     */   
/*  213:     */   public void addIndex(int index)
/*  214:     */   {
/*  215: 450 */     add(index >> 8, index);
/*  216:     */   }
/*  217:     */   
/*  218:     */   public void addAload(int n)
/*  219:     */   {
/*  220: 459 */     if (n < 4)
/*  221:     */     {
/*  222: 460 */       addOpcode(42 + n);
/*  223:     */     }
/*  224: 461 */     else if (n < 256)
/*  225:     */     {
/*  226: 462 */       addOpcode(25);
/*  227: 463 */       add(n);
/*  228:     */     }
/*  229:     */     else
/*  230:     */     {
/*  231: 466 */       addOpcode(196);
/*  232: 467 */       addOpcode(25);
/*  233: 468 */       addIndex(n);
/*  234:     */     }
/*  235:     */   }
/*  236:     */   
/*  237:     */   public void addAstore(int n)
/*  238:     */   {
/*  239: 478 */     if (n < 4)
/*  240:     */     {
/*  241: 479 */       addOpcode(75 + n);
/*  242:     */     }
/*  243: 480 */     else if (n < 256)
/*  244:     */     {
/*  245: 481 */       addOpcode(58);
/*  246: 482 */       add(n);
/*  247:     */     }
/*  248:     */     else
/*  249:     */     {
/*  250: 485 */       addOpcode(196);
/*  251: 486 */       addOpcode(58);
/*  252: 487 */       addIndex(n);
/*  253:     */     }
/*  254:     */   }
/*  255:     */   
/*  256:     */   public void addIconst(int n)
/*  257:     */   {
/*  258: 497 */     if ((n < 6) && (-2 < n))
/*  259:     */     {
/*  260: 498 */       addOpcode(3 + n);
/*  261:     */     }
/*  262: 499 */     else if ((n <= 127) && (-128 <= n))
/*  263:     */     {
/*  264: 500 */       addOpcode(16);
/*  265: 501 */       add(n);
/*  266:     */     }
/*  267: 503 */     else if ((n <= 32767) && (-32768 <= n))
/*  268:     */     {
/*  269: 504 */       addOpcode(17);
/*  270: 505 */       add(n >> 8);
/*  271: 506 */       add(n);
/*  272:     */     }
/*  273:     */     else
/*  274:     */     {
/*  275: 509 */       addLdc(this.constPool.addIntegerInfo(n));
/*  276:     */     }
/*  277:     */   }
/*  278:     */   
/*  279:     */   public void addConstZero(CtClass type)
/*  280:     */   {
/*  281: 519 */     if (type.isPrimitive())
/*  282:     */     {
/*  283: 520 */       if (type == CtClass.longType)
/*  284:     */       {
/*  285: 521 */         addOpcode(9);
/*  286:     */       }
/*  287: 522 */       else if (type == CtClass.floatType)
/*  288:     */       {
/*  289: 523 */         addOpcode(11);
/*  290:     */       }
/*  291: 524 */       else if (type == CtClass.doubleType)
/*  292:     */       {
/*  293: 525 */         addOpcode(14);
/*  294:     */       }
/*  295:     */       else
/*  296:     */       {
/*  297: 526 */         if (type == CtClass.voidType) {
/*  298: 527 */           throw new RuntimeException("void type?");
/*  299:     */         }
/*  300: 529 */         addOpcode(3);
/*  301:     */       }
/*  302:     */     }
/*  303:     */     else {
/*  304: 532 */       addOpcode(1);
/*  305:     */     }
/*  306:     */   }
/*  307:     */   
/*  308:     */   public void addIload(int n)
/*  309:     */   {
/*  310: 541 */     if (n < 4)
/*  311:     */     {
/*  312: 542 */       addOpcode(26 + n);
/*  313:     */     }
/*  314: 543 */     else if (n < 256)
/*  315:     */     {
/*  316: 544 */       addOpcode(21);
/*  317: 545 */       add(n);
/*  318:     */     }
/*  319:     */     else
/*  320:     */     {
/*  321: 548 */       addOpcode(196);
/*  322: 549 */       addOpcode(21);
/*  323: 550 */       addIndex(n);
/*  324:     */     }
/*  325:     */   }
/*  326:     */   
/*  327:     */   public void addIstore(int n)
/*  328:     */   {
/*  329: 560 */     if (n < 4)
/*  330:     */     {
/*  331: 561 */       addOpcode(59 + n);
/*  332:     */     }
/*  333: 562 */     else if (n < 256)
/*  334:     */     {
/*  335: 563 */       addOpcode(54);
/*  336: 564 */       add(n);
/*  337:     */     }
/*  338:     */     else
/*  339:     */     {
/*  340: 567 */       addOpcode(196);
/*  341: 568 */       addOpcode(54);
/*  342: 569 */       addIndex(n);
/*  343:     */     }
/*  344:     */   }
/*  345:     */   
/*  346:     */   public void addLconst(long n)
/*  347:     */   {
/*  348: 579 */     if ((n == 0L) || (n == 1L)) {
/*  349: 580 */       addOpcode(9 + (int)n);
/*  350:     */     } else {
/*  351: 582 */       addLdc2w(n);
/*  352:     */     }
/*  353:     */   }
/*  354:     */   
/*  355:     */   public void addLload(int n)
/*  356:     */   {
/*  357: 591 */     if (n < 4)
/*  358:     */     {
/*  359: 592 */       addOpcode(30 + n);
/*  360:     */     }
/*  361: 593 */     else if (n < 256)
/*  362:     */     {
/*  363: 594 */       addOpcode(22);
/*  364: 595 */       add(n);
/*  365:     */     }
/*  366:     */     else
/*  367:     */     {
/*  368: 598 */       addOpcode(196);
/*  369: 599 */       addOpcode(22);
/*  370: 600 */       addIndex(n);
/*  371:     */     }
/*  372:     */   }
/*  373:     */   
/*  374:     */   public void addLstore(int n)
/*  375:     */   {
/*  376: 610 */     if (n < 4)
/*  377:     */     {
/*  378: 611 */       addOpcode(63 + n);
/*  379:     */     }
/*  380: 612 */     else if (n < 256)
/*  381:     */     {
/*  382: 613 */       addOpcode(55);
/*  383: 614 */       add(n);
/*  384:     */     }
/*  385:     */     else
/*  386:     */     {
/*  387: 617 */       addOpcode(196);
/*  388: 618 */       addOpcode(55);
/*  389: 619 */       addIndex(n);
/*  390:     */     }
/*  391:     */   }
/*  392:     */   
/*  393:     */   public void addDconst(double d)
/*  394:     */   {
/*  395: 629 */     if ((d == 0.0D) || (d == 1.0D)) {
/*  396: 630 */       addOpcode(14 + (int)d);
/*  397:     */     } else {
/*  398: 632 */       addLdc2w(d);
/*  399:     */     }
/*  400:     */   }
/*  401:     */   
/*  402:     */   public void addDload(int n)
/*  403:     */   {
/*  404: 641 */     if (n < 4)
/*  405:     */     {
/*  406: 642 */       addOpcode(38 + n);
/*  407:     */     }
/*  408: 643 */     else if (n < 256)
/*  409:     */     {
/*  410: 644 */       addOpcode(24);
/*  411: 645 */       add(n);
/*  412:     */     }
/*  413:     */     else
/*  414:     */     {
/*  415: 648 */       addOpcode(196);
/*  416: 649 */       addOpcode(24);
/*  417: 650 */       addIndex(n);
/*  418:     */     }
/*  419:     */   }
/*  420:     */   
/*  421:     */   public void addDstore(int n)
/*  422:     */   {
/*  423: 660 */     if (n < 4)
/*  424:     */     {
/*  425: 661 */       addOpcode(71 + n);
/*  426:     */     }
/*  427: 662 */     else if (n < 256)
/*  428:     */     {
/*  429: 663 */       addOpcode(57);
/*  430: 664 */       add(n);
/*  431:     */     }
/*  432:     */     else
/*  433:     */     {
/*  434: 667 */       addOpcode(196);
/*  435: 668 */       addOpcode(57);
/*  436: 669 */       addIndex(n);
/*  437:     */     }
/*  438:     */   }
/*  439:     */   
/*  440:     */   public void addFconst(float f)
/*  441:     */   {
/*  442: 679 */     if ((f == 0.0F) || (f == 1.0F) || (f == 2.0F)) {
/*  443: 680 */       addOpcode(11 + (int)f);
/*  444:     */     } else {
/*  445: 682 */       addLdc(this.constPool.addFloatInfo(f));
/*  446:     */     }
/*  447:     */   }
/*  448:     */   
/*  449:     */   public void addFload(int n)
/*  450:     */   {
/*  451: 691 */     if (n < 4)
/*  452:     */     {
/*  453: 692 */       addOpcode(34 + n);
/*  454:     */     }
/*  455: 693 */     else if (n < 256)
/*  456:     */     {
/*  457: 694 */       addOpcode(23);
/*  458: 695 */       add(n);
/*  459:     */     }
/*  460:     */     else
/*  461:     */     {
/*  462: 698 */       addOpcode(196);
/*  463: 699 */       addOpcode(23);
/*  464: 700 */       addIndex(n);
/*  465:     */     }
/*  466:     */   }
/*  467:     */   
/*  468:     */   public void addFstore(int n)
/*  469:     */   {
/*  470: 710 */     if (n < 4)
/*  471:     */     {
/*  472: 711 */       addOpcode(67 + n);
/*  473:     */     }
/*  474: 712 */     else if (n < 256)
/*  475:     */     {
/*  476: 713 */       addOpcode(56);
/*  477: 714 */       add(n);
/*  478:     */     }
/*  479:     */     else
/*  480:     */     {
/*  481: 717 */       addOpcode(196);
/*  482: 718 */       addOpcode(56);
/*  483: 719 */       addIndex(n);
/*  484:     */     }
/*  485:     */   }
/*  486:     */   
/*  487:     */   public int addLoad(int n, CtClass type)
/*  488:     */   {
/*  489: 732 */     if (type.isPrimitive())
/*  490:     */     {
/*  491: 733 */       if ((type == CtClass.booleanType) || (type == CtClass.charType) || (type == CtClass.byteType) || (type == CtClass.shortType) || (type == CtClass.intType))
/*  492:     */       {
/*  493: 736 */         addIload(n);
/*  494:     */       }
/*  495:     */       else
/*  496:     */       {
/*  497: 737 */         if (type == CtClass.longType)
/*  498:     */         {
/*  499: 738 */           addLload(n);
/*  500: 739 */           return 2;
/*  501:     */         }
/*  502: 741 */         if (type == CtClass.floatType)
/*  503:     */         {
/*  504: 742 */           addFload(n);
/*  505:     */         }
/*  506:     */         else
/*  507:     */         {
/*  508: 743 */           if (type == CtClass.doubleType)
/*  509:     */           {
/*  510: 744 */             addDload(n);
/*  511: 745 */             return 2;
/*  512:     */           }
/*  513: 748 */           throw new RuntimeException("void type?");
/*  514:     */         }
/*  515:     */       }
/*  516:     */     }
/*  517:     */     else {
/*  518: 751 */       addAload(n);
/*  519:     */     }
/*  520: 753 */     return 1;
/*  521:     */   }
/*  522:     */   
/*  523:     */   public int addStore(int n, CtClass type)
/*  524:     */   {
/*  525: 765 */     if (type.isPrimitive())
/*  526:     */     {
/*  527: 766 */       if ((type == CtClass.booleanType) || (type == CtClass.charType) || (type == CtClass.byteType) || (type == CtClass.shortType) || (type == CtClass.intType))
/*  528:     */       {
/*  529: 769 */         addIstore(n);
/*  530:     */       }
/*  531:     */       else
/*  532:     */       {
/*  533: 770 */         if (type == CtClass.longType)
/*  534:     */         {
/*  535: 771 */           addLstore(n);
/*  536: 772 */           return 2;
/*  537:     */         }
/*  538: 774 */         if (type == CtClass.floatType)
/*  539:     */         {
/*  540: 775 */           addFstore(n);
/*  541:     */         }
/*  542:     */         else
/*  543:     */         {
/*  544: 776 */           if (type == CtClass.doubleType)
/*  545:     */           {
/*  546: 777 */             addDstore(n);
/*  547: 778 */             return 2;
/*  548:     */           }
/*  549: 781 */           throw new RuntimeException("void type?");
/*  550:     */         }
/*  551:     */       }
/*  552:     */     }
/*  553:     */     else {
/*  554: 784 */       addAstore(n);
/*  555:     */     }
/*  556: 786 */     return 1;
/*  557:     */   }
/*  558:     */   
/*  559:     */   public int addLoadParameters(CtClass[] params, int offset)
/*  560:     */   {
/*  561: 797 */     int stacksize = 0;
/*  562: 798 */     if (params != null)
/*  563:     */     {
/*  564: 799 */       int n = params.length;
/*  565: 800 */       for (int i = 0; i < n; i++) {
/*  566: 801 */         stacksize += addLoad(stacksize + offset, params[i]);
/*  567:     */       }
/*  568:     */     }
/*  569: 804 */     return stacksize;
/*  570:     */   }
/*  571:     */   
/*  572:     */   public void addCheckcast(CtClass c)
/*  573:     */   {
/*  574: 813 */     addOpcode(192);
/*  575: 814 */     addIndex(this.constPool.addClassInfo(c));
/*  576:     */   }
/*  577:     */   
/*  578:     */   public void addCheckcast(String classname)
/*  579:     */   {
/*  580: 823 */     addOpcode(192);
/*  581: 824 */     addIndex(this.constPool.addClassInfo(classname));
/*  582:     */   }
/*  583:     */   
/*  584:     */   public void addInstanceof(String classname)
/*  585:     */   {
/*  586: 833 */     addOpcode(193);
/*  587: 834 */     addIndex(this.constPool.addClassInfo(classname));
/*  588:     */   }
/*  589:     */   
/*  590:     */   public void addGetfield(CtClass c, String name, String type)
/*  591:     */   {
/*  592: 847 */     add(180);
/*  593: 848 */     int ci = this.constPool.addClassInfo(c);
/*  594: 849 */     addIndex(this.constPool.addFieldrefInfo(ci, name, type));
/*  595: 850 */     growStack(Descriptor.dataSize(type) - 1);
/*  596:     */   }
/*  597:     */   
/*  598:     */   public void addGetfield(String c, String name, String type)
/*  599:     */   {
/*  600: 863 */     add(180);
/*  601: 864 */     int ci = this.constPool.addClassInfo(c);
/*  602: 865 */     addIndex(this.constPool.addFieldrefInfo(ci, name, type));
/*  603: 866 */     growStack(Descriptor.dataSize(type) - 1);
/*  604:     */   }
/*  605:     */   
/*  606:     */   public void addGetstatic(CtClass c, String name, String type)
/*  607:     */   {
/*  608: 879 */     add(178);
/*  609: 880 */     int ci = this.constPool.addClassInfo(c);
/*  610: 881 */     addIndex(this.constPool.addFieldrefInfo(ci, name, type));
/*  611: 882 */     growStack(Descriptor.dataSize(type));
/*  612:     */   }
/*  613:     */   
/*  614:     */   public void addGetstatic(String c, String name, String type)
/*  615:     */   {
/*  616: 895 */     add(178);
/*  617: 896 */     int ci = this.constPool.addClassInfo(c);
/*  618: 897 */     addIndex(this.constPool.addFieldrefInfo(ci, name, type));
/*  619: 898 */     growStack(Descriptor.dataSize(type));
/*  620:     */   }
/*  621:     */   
/*  622:     */   public void addInvokespecial(CtClass clazz, String name, CtClass returnType, CtClass[] paramTypes)
/*  623:     */   {
/*  624: 911 */     String desc = Descriptor.ofMethod(returnType, paramTypes);
/*  625: 912 */     addInvokespecial(clazz, name, desc);
/*  626:     */   }
/*  627:     */   
/*  628:     */   public void addInvokespecial(CtClass clazz, String name, String desc)
/*  629:     */   {
/*  630: 926 */     addInvokespecial(this.constPool.addClassInfo(clazz), name, desc);
/*  631:     */   }
/*  632:     */   
/*  633:     */   public void addInvokespecial(String clazz, String name, String desc)
/*  634:     */   {
/*  635: 940 */     addInvokespecial(this.constPool.addClassInfo(clazz), name, desc);
/*  636:     */   }
/*  637:     */   
/*  638:     */   public void addInvokespecial(int clazz, String name, String desc)
/*  639:     */   {
/*  640: 955 */     add(183);
/*  641: 956 */     addIndex(this.constPool.addMethodrefInfo(clazz, name, desc));
/*  642: 957 */     growStack(Descriptor.dataSize(desc) - 1);
/*  643:     */   }
/*  644:     */   
/*  645:     */   public void addInvokestatic(CtClass clazz, String name, CtClass returnType, CtClass[] paramTypes)
/*  646:     */   {
/*  647: 970 */     String desc = Descriptor.ofMethod(returnType, paramTypes);
/*  648: 971 */     addInvokestatic(clazz, name, desc);
/*  649:     */   }
/*  650:     */   
/*  651:     */   public void addInvokestatic(CtClass clazz, String name, String desc)
/*  652:     */   {
/*  653: 984 */     addInvokestatic(this.constPool.addClassInfo(clazz), name, desc);
/*  654:     */   }
/*  655:     */   
/*  656:     */   public void addInvokestatic(String classname, String name, String desc)
/*  657:     */   {
/*  658: 997 */     addInvokestatic(this.constPool.addClassInfo(classname), name, desc);
/*  659:     */   }
/*  660:     */   
/*  661:     */   public void addInvokestatic(int clazz, String name, String desc)
/*  662:     */   {
/*  663:1011 */     add(184);
/*  664:1012 */     addIndex(this.constPool.addMethodrefInfo(clazz, name, desc));
/*  665:1013 */     growStack(Descriptor.dataSize(desc));
/*  666:     */   }
/*  667:     */   
/*  668:     */   public void addInvokevirtual(CtClass clazz, String name, CtClass returnType, CtClass[] paramTypes)
/*  669:     */   {
/*  670:1030 */     String desc = Descriptor.ofMethod(returnType, paramTypes);
/*  671:1031 */     addInvokevirtual(clazz, name, desc);
/*  672:     */   }
/*  673:     */   
/*  674:     */   public void addInvokevirtual(CtClass clazz, String name, String desc)
/*  675:     */   {
/*  676:1048 */     addInvokevirtual(this.constPool.addClassInfo(clazz), name, desc);
/*  677:     */   }
/*  678:     */   
/*  679:     */   public void addInvokevirtual(String classname, String name, String desc)
/*  680:     */   {
/*  681:1065 */     addInvokevirtual(this.constPool.addClassInfo(classname), name, desc);
/*  682:     */   }
/*  683:     */   
/*  684:     */   public void addInvokevirtual(int clazz, String name, String desc)
/*  685:     */   {
/*  686:1083 */     add(182);
/*  687:1084 */     addIndex(this.constPool.addMethodrefInfo(clazz, name, desc));
/*  688:1085 */     growStack(Descriptor.dataSize(desc) - 1);
/*  689:     */   }
/*  690:     */   
/*  691:     */   public void addInvokeinterface(CtClass clazz, String name, CtClass returnType, CtClass[] paramTypes, int count)
/*  692:     */   {
/*  693:1100 */     String desc = Descriptor.ofMethod(returnType, paramTypes);
/*  694:1101 */     addInvokeinterface(clazz, name, desc, count);
/*  695:     */   }
/*  696:     */   
/*  697:     */   public void addInvokeinterface(CtClass clazz, String name, String desc, int count)
/*  698:     */   {
/*  699:1116 */     addInvokeinterface(this.constPool.addClassInfo(clazz), name, desc, count);
/*  700:     */   }
/*  701:     */   
/*  702:     */   public void addInvokeinterface(String classname, String name, String desc, int count)
/*  703:     */   {
/*  704:1132 */     addInvokeinterface(this.constPool.addClassInfo(classname), name, desc, count);
/*  705:     */   }
/*  706:     */   
/*  707:     */   public void addInvokeinterface(int clazz, String name, String desc, int count)
/*  708:     */   {
/*  709:1149 */     add(185);
/*  710:1150 */     addIndex(this.constPool.addInterfaceMethodrefInfo(clazz, name, desc));
/*  711:1151 */     add(count);
/*  712:1152 */     add(0);
/*  713:1153 */     growStack(Descriptor.dataSize(desc) - 1);
/*  714:     */   }
/*  715:     */   
/*  716:     */   public void addLdc(String s)
/*  717:     */   {
/*  718:1163 */     addLdc(this.constPool.addStringInfo(s));
/*  719:     */   }
/*  720:     */   
/*  721:     */   public void addLdc(int i)
/*  722:     */   {
/*  723:1172 */     if (i > 255)
/*  724:     */     {
/*  725:1173 */       addOpcode(19);
/*  726:1174 */       addIndex(i);
/*  727:     */     }
/*  728:     */     else
/*  729:     */     {
/*  730:1177 */       addOpcode(18);
/*  731:1178 */       add(i);
/*  732:     */     }
/*  733:     */   }
/*  734:     */   
/*  735:     */   public void addLdc2w(long l)
/*  736:     */   {
/*  737:1186 */     addOpcode(20);
/*  738:1187 */     addIndex(this.constPool.addLongInfo(l));
/*  739:     */   }
/*  740:     */   
/*  741:     */   public void addLdc2w(double d)
/*  742:     */   {
/*  743:1194 */     addOpcode(20);
/*  744:1195 */     addIndex(this.constPool.addDoubleInfo(d));
/*  745:     */   }
/*  746:     */   
/*  747:     */   public void addNew(CtClass clazz)
/*  748:     */   {
/*  749:1204 */     addOpcode(187);
/*  750:1205 */     addIndex(this.constPool.addClassInfo(clazz));
/*  751:     */   }
/*  752:     */   
/*  753:     */   public void addNew(String classname)
/*  754:     */   {
/*  755:1214 */     addOpcode(187);
/*  756:1215 */     addIndex(this.constPool.addClassInfo(classname));
/*  757:     */   }
/*  758:     */   
/*  759:     */   public void addAnewarray(String classname)
/*  760:     */   {
/*  761:1224 */     addOpcode(189);
/*  762:1225 */     addIndex(this.constPool.addClassInfo(classname));
/*  763:     */   }
/*  764:     */   
/*  765:     */   public void addAnewarray(CtClass clazz, int length)
/*  766:     */   {
/*  767:1235 */     addIconst(length);
/*  768:1236 */     addOpcode(189);
/*  769:1237 */     addIndex(this.constPool.addClassInfo(clazz));
/*  770:     */   }
/*  771:     */   
/*  772:     */   public void addNewarray(int atype, int length)
/*  773:     */   {
/*  774:1247 */     addIconst(length);
/*  775:1248 */     addOpcode(188);
/*  776:1249 */     add(atype);
/*  777:     */   }
/*  778:     */   
/*  779:     */   public int addMultiNewarray(CtClass clazz, int[] dimensions)
/*  780:     */   {
/*  781:1260 */     int len = dimensions.length;
/*  782:1261 */     for (int i = 0; i < len; i++) {
/*  783:1262 */       addIconst(dimensions[i]);
/*  784:     */     }
/*  785:1264 */     growStack(len);
/*  786:1265 */     return addMultiNewarray(clazz, len);
/*  787:     */   }
/*  788:     */   
/*  789:     */   public int addMultiNewarray(CtClass clazz, int dim)
/*  790:     */   {
/*  791:1277 */     add(197);
/*  792:1278 */     addIndex(this.constPool.addClassInfo(clazz));
/*  793:1279 */     add(dim);
/*  794:1280 */     growStack(1 - dim);
/*  795:1281 */     return dim;
/*  796:     */   }
/*  797:     */   
/*  798:     */   public int addMultiNewarray(String desc, int dim)
/*  799:     */   {
/*  800:1292 */     add(197);
/*  801:1293 */     addIndex(this.constPool.addClassInfo(desc));
/*  802:1294 */     add(dim);
/*  803:1295 */     growStack(1 - dim);
/*  804:1296 */     return dim;
/*  805:     */   }
/*  806:     */   
/*  807:     */   public void addPutfield(CtClass c, String name, String desc)
/*  808:     */   {
/*  809:1307 */     addPutfield0(c, null, name, desc);
/*  810:     */   }
/*  811:     */   
/*  812:     */   public void addPutfield(String classname, String name, String desc)
/*  813:     */   {
/*  814:1319 */     addPutfield0(null, classname, name, desc);
/*  815:     */   }
/*  816:     */   
/*  817:     */   private void addPutfield0(CtClass target, String classname, String name, String desc)
/*  818:     */   {
/*  819:1324 */     add(181);
/*  820:     */     
/*  821:1326 */     int ci = classname == null ? this.constPool.addClassInfo(target) : this.constPool.addClassInfo(classname);
/*  822:     */     
/*  823:1328 */     addIndex(this.constPool.addFieldrefInfo(ci, name, desc));
/*  824:1329 */     growStack(-1 - Descriptor.dataSize(desc));
/*  825:     */   }
/*  826:     */   
/*  827:     */   public void addPutstatic(CtClass c, String name, String desc)
/*  828:     */   {
/*  829:1340 */     addPutstatic0(c, null, name, desc);
/*  830:     */   }
/*  831:     */   
/*  832:     */   public void addPutstatic(String classname, String fieldName, String desc)
/*  833:     */   {
/*  834:1352 */     addPutstatic0(null, classname, fieldName, desc);
/*  835:     */   }
/*  836:     */   
/*  837:     */   private void addPutstatic0(CtClass target, String classname, String fieldName, String desc)
/*  838:     */   {
/*  839:1357 */     add(179);
/*  840:     */     
/*  841:1359 */     int ci = classname == null ? this.constPool.addClassInfo(target) : this.constPool.addClassInfo(classname);
/*  842:     */     
/*  843:1361 */     addIndex(this.constPool.addFieldrefInfo(ci, fieldName, desc));
/*  844:1362 */     growStack(-Descriptor.dataSize(desc));
/*  845:     */   }
/*  846:     */   
/*  847:     */   public void addReturn(CtClass type)
/*  848:     */   {
/*  849:1371 */     if (type == null)
/*  850:     */     {
/*  851:1372 */       addOpcode(177);
/*  852:     */     }
/*  853:1373 */     else if (type.isPrimitive())
/*  854:     */     {
/*  855:1374 */       CtPrimitiveType ptype = (CtPrimitiveType)type;
/*  856:1375 */       addOpcode(ptype.getReturnOp());
/*  857:     */     }
/*  858:     */     else
/*  859:     */     {
/*  860:1378 */       addOpcode(176);
/*  861:     */     }
/*  862:     */   }
/*  863:     */   
/*  864:     */   public void addRet(int var)
/*  865:     */   {
/*  866:1387 */     if (var < 256)
/*  867:     */     {
/*  868:1388 */       addOpcode(169);
/*  869:1389 */       add(var);
/*  870:     */     }
/*  871:     */     else
/*  872:     */     {
/*  873:1392 */       addOpcode(196);
/*  874:1393 */       addOpcode(169);
/*  875:1394 */       addIndex(var);
/*  876:     */     }
/*  877:     */   }
/*  878:     */   
/*  879:     */   public void addPrintln(String message)
/*  880:     */   {
/*  881:1405 */     addGetstatic("java.lang.System", "err", "Ljava/io/PrintStream;");
/*  882:1406 */     addLdc(message);
/*  883:1407 */     addInvokevirtual("java.io.PrintStream", "println", "(Ljava/lang/String;)V");
/*  884:     */   }
/*  885:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.Bytecode
 * JD-Core Version:    0.7.0.1
 */