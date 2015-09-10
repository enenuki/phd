/*    1:     */ package org.apache.xml.utils;
/*    2:     */ 
/*    3:     */ import org.xml.sax.ContentHandler;
/*    4:     */ import org.xml.sax.SAXException;
/*    5:     */ import org.xml.sax.ext.LexicalHandler;
/*    6:     */ 
/*    7:     */ public class FastStringBuffer
/*    8:     */ {
/*    9:     */   static final int DEBUG_FORCE_INIT_BITS = 0;
/*   10:     */   static final boolean DEBUG_FORCE_FIXED_CHUNKSIZE = true;
/*   11:     */   public static final int SUPPRESS_LEADING_WS = 1;
/*   12:     */   public static final int SUPPRESS_TRAILING_WS = 2;
/*   13:     */   public static final int SUPPRESS_BOTH = 3;
/*   14:     */   private static final int CARRY_WS = 4;
/*   15: 101 */   int m_chunkBits = 15;
/*   16: 108 */   int m_maxChunkBits = 15;
/*   17: 118 */   int m_rebundleBits = 2;
/*   18:     */   int m_chunkSize;
/*   19:     */   int m_chunkMask;
/*   20:     */   char[][] m_array;
/*   21: 153 */   int m_lastChunk = 0;
/*   22: 162 */   int m_firstFree = 0;
/*   23: 171 */   FastStringBuffer m_innerFSB = null;
/*   24:     */   
/*   25:     */   public FastStringBuffer(int initChunkBits, int maxChunkBits, int rebundleBits)
/*   26:     */   {
/*   27: 207 */     maxChunkBits = initChunkBits;
/*   28:     */     
/*   29:     */ 
/*   30: 210 */     this.m_array = new char[16][];
/*   31: 213 */     if (initChunkBits > maxChunkBits) {
/*   32: 214 */       initChunkBits = maxChunkBits;
/*   33:     */     }
/*   34: 216 */     this.m_chunkBits = initChunkBits;
/*   35: 217 */     this.m_maxChunkBits = maxChunkBits;
/*   36: 218 */     this.m_rebundleBits = rebundleBits;
/*   37: 219 */     this.m_chunkSize = (1 << initChunkBits);
/*   38: 220 */     this.m_chunkMask = (this.m_chunkSize - 1);
/*   39: 221 */     this.m_array[0] = new char[this.m_chunkSize];
/*   40:     */   }
/*   41:     */   
/*   42:     */   public FastStringBuffer(int initChunkBits, int maxChunkBits)
/*   43:     */   {
/*   44: 232 */     this(initChunkBits, maxChunkBits, 2);
/*   45:     */   }
/*   46:     */   
/*   47:     */   public FastStringBuffer(int initChunkBits)
/*   48:     */   {
/*   49: 246 */     this(initChunkBits, 15, 2);
/*   50:     */   }
/*   51:     */   
/*   52:     */   public FastStringBuffer()
/*   53:     */   {
/*   54: 260 */     this(10, 15, 2);
/*   55:     */   }
/*   56:     */   
/*   57:     */   public final int size()
/*   58:     */   {
/*   59: 270 */     return (this.m_lastChunk << this.m_chunkBits) + this.m_firstFree;
/*   60:     */   }
/*   61:     */   
/*   62:     */   public final int length()
/*   63:     */   {
/*   64: 280 */     return (this.m_lastChunk << this.m_chunkBits) + this.m_firstFree;
/*   65:     */   }
/*   66:     */   
/*   67:     */   public final void reset()
/*   68:     */   {
/*   69: 291 */     this.m_lastChunk = 0;
/*   70: 292 */     this.m_firstFree = 0;
/*   71:     */     
/*   72:     */ 
/*   73: 295 */     FastStringBuffer innermost = this;
/*   74: 297 */     while (innermost.m_innerFSB != null) {
/*   75: 299 */       innermost = innermost.m_innerFSB;
/*   76:     */     }
/*   77: 302 */     this.m_chunkBits = innermost.m_chunkBits;
/*   78: 303 */     this.m_chunkSize = innermost.m_chunkSize;
/*   79: 304 */     this.m_chunkMask = innermost.m_chunkMask;
/*   80:     */     
/*   81:     */ 
/*   82: 307 */     this.m_innerFSB = null;
/*   83: 308 */     this.m_array = new char[16][0];
/*   84: 309 */     this.m_array[0] = new char[this.m_chunkSize];
/*   85:     */   }
/*   86:     */   
/*   87:     */   public final void setLength(int l)
/*   88:     */   {
/*   89: 326 */     this.m_lastChunk = (l >>> this.m_chunkBits);
/*   90: 328 */     if ((this.m_lastChunk == 0) && (this.m_innerFSB != null))
/*   91:     */     {
/*   92: 331 */       this.m_innerFSB.setLength(l, this);
/*   93:     */     }
/*   94:     */     else
/*   95:     */     {
/*   96: 335 */       this.m_firstFree = (l & this.m_chunkMask);
/*   97: 342 */       if ((this.m_firstFree == 0) && (this.m_lastChunk > 0))
/*   98:     */       {
/*   99: 344 */         this.m_lastChunk -= 1;
/*  100: 345 */         this.m_firstFree = this.m_chunkSize;
/*  101:     */       }
/*  102:     */     }
/*  103:     */   }
/*  104:     */   
/*  105:     */   private final void setLength(int l, FastStringBuffer rootFSB)
/*  106:     */   {
/*  107: 360 */     this.m_lastChunk = (l >>> this.m_chunkBits);
/*  108: 362 */     if ((this.m_lastChunk == 0) && (this.m_innerFSB != null))
/*  109:     */     {
/*  110: 364 */       this.m_innerFSB.setLength(l, rootFSB);
/*  111:     */     }
/*  112:     */     else
/*  113:     */     {
/*  114: 371 */       rootFSB.m_chunkBits = this.m_chunkBits;
/*  115: 372 */       rootFSB.m_maxChunkBits = this.m_maxChunkBits;
/*  116: 373 */       rootFSB.m_rebundleBits = this.m_rebundleBits;
/*  117: 374 */       rootFSB.m_chunkSize = this.m_chunkSize;
/*  118: 375 */       rootFSB.m_chunkMask = this.m_chunkMask;
/*  119: 376 */       rootFSB.m_array = this.m_array;
/*  120: 377 */       rootFSB.m_innerFSB = this.m_innerFSB;
/*  121: 378 */       rootFSB.m_lastChunk = this.m_lastChunk;
/*  122:     */       
/*  123:     */ 
/*  124: 381 */       rootFSB.m_firstFree = (l & this.m_chunkMask);
/*  125:     */     }
/*  126:     */   }
/*  127:     */   
/*  128:     */   public final String toString()
/*  129:     */   {
/*  130: 401 */     int length = (this.m_lastChunk << this.m_chunkBits) + this.m_firstFree;
/*  131:     */     
/*  132: 403 */     return getString(new StringBuffer(length), 0, 0, length).toString();
/*  133:     */   }
/*  134:     */   
/*  135:     */   public final void append(char value)
/*  136:     */   {
/*  137:     */     char[] chunk;
/*  138: 424 */     if (this.m_firstFree < this.m_chunkSize)
/*  139:     */     {
/*  140: 425 */       chunk = this.m_array[this.m_lastChunk];
/*  141:     */     }
/*  142:     */     else
/*  143:     */     {
/*  144: 430 */       int i = this.m_array.length;
/*  145: 432 */       if (this.m_lastChunk + 1 == i)
/*  146:     */       {
/*  147: 434 */         char[][] newarray = new char[i + 16][];
/*  148:     */         
/*  149: 436 */         System.arraycopy(this.m_array, 0, newarray, 0, i);
/*  150:     */         
/*  151: 438 */         this.m_array = newarray;
/*  152:     */       }
/*  153: 442 */       chunk = this.m_array[(++this.m_lastChunk)];
/*  154: 444 */       if (chunk == null)
/*  155:     */       {
/*  156: 448 */         if ((this.m_lastChunk == 1 << this.m_rebundleBits) && (this.m_chunkBits < this.m_maxChunkBits)) {
/*  157: 454 */           this.m_innerFSB = new FastStringBuffer(this);
/*  158:     */         }
/*  159: 458 */         chunk = this.m_array[this.m_lastChunk] =  = new char[this.m_chunkSize];
/*  160:     */       }
/*  161: 461 */       this.m_firstFree = 0;
/*  162:     */     }
/*  163: 465 */     chunk[(this.m_firstFree++)] = value;
/*  164:     */   }
/*  165:     */   
/*  166:     */   public final void append(String value)
/*  167:     */   {
/*  168: 480 */     if (value == null) {
/*  169: 481 */       return;
/*  170:     */     }
/*  171: 482 */     int strlen = value.length();
/*  172: 484 */     if (0 == strlen) {
/*  173: 485 */       return;
/*  174:     */     }
/*  175: 487 */     int copyfrom = 0;
/*  176: 488 */     char[] chunk = this.m_array[this.m_lastChunk];
/*  177: 489 */     int available = this.m_chunkSize - this.m_firstFree;
/*  178: 492 */     while (strlen > 0)
/*  179:     */     {
/*  180: 496 */       if (available > strlen) {
/*  181: 497 */         available = strlen;
/*  182:     */       }
/*  183: 499 */       value.getChars(copyfrom, copyfrom + available, this.m_array[this.m_lastChunk], this.m_firstFree);
/*  184:     */       
/*  185:     */ 
/*  186: 502 */       strlen -= available;
/*  187: 503 */       copyfrom += available;
/*  188: 506 */       if (strlen > 0)
/*  189:     */       {
/*  190: 510 */         int i = this.m_array.length;
/*  191: 512 */         if (this.m_lastChunk + 1 == i)
/*  192:     */         {
/*  193: 514 */           char[][] newarray = new char[i + 16][];
/*  194:     */           
/*  195: 516 */           System.arraycopy(this.m_array, 0, newarray, 0, i);
/*  196:     */           
/*  197: 518 */           this.m_array = newarray;
/*  198:     */         }
/*  199: 522 */         chunk = this.m_array[(++this.m_lastChunk)];
/*  200: 524 */         if (chunk == null)
/*  201:     */         {
/*  202: 528 */           if ((this.m_lastChunk == 1 << this.m_rebundleBits) && (this.m_chunkBits < this.m_maxChunkBits)) {
/*  203: 534 */             this.m_innerFSB = new FastStringBuffer(this);
/*  204:     */           }
/*  205: 538 */           chunk = this.m_array[this.m_lastChunk] =  = new char[this.m_chunkSize];
/*  206:     */         }
/*  207: 541 */         available = this.m_chunkSize;
/*  208: 542 */         this.m_firstFree = 0;
/*  209:     */       }
/*  210:     */     }
/*  211: 547 */     this.m_firstFree += available;
/*  212:     */   }
/*  213:     */   
/*  214:     */   public final void append(StringBuffer value)
/*  215:     */   {
/*  216: 562 */     if (value == null) {
/*  217: 563 */       return;
/*  218:     */     }
/*  219: 564 */     int strlen = value.length();
/*  220: 566 */     if (0 == strlen) {
/*  221: 567 */       return;
/*  222:     */     }
/*  223: 569 */     int copyfrom = 0;
/*  224: 570 */     char[] chunk = this.m_array[this.m_lastChunk];
/*  225: 571 */     int available = this.m_chunkSize - this.m_firstFree;
/*  226: 574 */     while (strlen > 0)
/*  227:     */     {
/*  228: 578 */       if (available > strlen) {
/*  229: 579 */         available = strlen;
/*  230:     */       }
/*  231: 581 */       value.getChars(copyfrom, copyfrom + available, this.m_array[this.m_lastChunk], this.m_firstFree);
/*  232:     */       
/*  233:     */ 
/*  234: 584 */       strlen -= available;
/*  235: 585 */       copyfrom += available;
/*  236: 588 */       if (strlen > 0)
/*  237:     */       {
/*  238: 592 */         int i = this.m_array.length;
/*  239: 594 */         if (this.m_lastChunk + 1 == i)
/*  240:     */         {
/*  241: 596 */           char[][] newarray = new char[i + 16][];
/*  242:     */           
/*  243: 598 */           System.arraycopy(this.m_array, 0, newarray, 0, i);
/*  244:     */           
/*  245: 600 */           this.m_array = newarray;
/*  246:     */         }
/*  247: 604 */         chunk = this.m_array[(++this.m_lastChunk)];
/*  248: 606 */         if (chunk == null)
/*  249:     */         {
/*  250: 610 */           if ((this.m_lastChunk == 1 << this.m_rebundleBits) && (this.m_chunkBits < this.m_maxChunkBits)) {
/*  251: 616 */             this.m_innerFSB = new FastStringBuffer(this);
/*  252:     */           }
/*  253: 620 */           chunk = this.m_array[this.m_lastChunk] =  = new char[this.m_chunkSize];
/*  254:     */         }
/*  255: 623 */         available = this.m_chunkSize;
/*  256: 624 */         this.m_firstFree = 0;
/*  257:     */       }
/*  258:     */     }
/*  259: 629 */     this.m_firstFree += available;
/*  260:     */   }
/*  261:     */   
/*  262:     */   public final void append(char[] chars, int start, int length)
/*  263:     */   {
/*  264: 647 */     int strlen = length;
/*  265: 649 */     if (0 == strlen) {
/*  266: 650 */       return;
/*  267:     */     }
/*  268: 652 */     int copyfrom = start;
/*  269: 653 */     char[] chunk = this.m_array[this.m_lastChunk];
/*  270: 654 */     int available = this.m_chunkSize - this.m_firstFree;
/*  271: 657 */     while (strlen > 0)
/*  272:     */     {
/*  273: 661 */       if (available > strlen) {
/*  274: 662 */         available = strlen;
/*  275:     */       }
/*  276: 664 */       System.arraycopy(chars, copyfrom, this.m_array[this.m_lastChunk], this.m_firstFree, available);
/*  277:     */       
/*  278:     */ 
/*  279: 667 */       strlen -= available;
/*  280: 668 */       copyfrom += available;
/*  281: 671 */       if (strlen > 0)
/*  282:     */       {
/*  283: 675 */         int i = this.m_array.length;
/*  284: 677 */         if (this.m_lastChunk + 1 == i)
/*  285:     */         {
/*  286: 679 */           char[][] newarray = new char[i + 16][];
/*  287:     */           
/*  288: 681 */           System.arraycopy(this.m_array, 0, newarray, 0, i);
/*  289:     */           
/*  290: 683 */           this.m_array = newarray;
/*  291:     */         }
/*  292: 687 */         chunk = this.m_array[(++this.m_lastChunk)];
/*  293: 689 */         if (chunk == null)
/*  294:     */         {
/*  295: 693 */           if ((this.m_lastChunk == 1 << this.m_rebundleBits) && (this.m_chunkBits < this.m_maxChunkBits)) {
/*  296: 699 */             this.m_innerFSB = new FastStringBuffer(this);
/*  297:     */           }
/*  298: 703 */           chunk = this.m_array[this.m_lastChunk] =  = new char[this.m_chunkSize];
/*  299:     */         }
/*  300: 706 */         available = this.m_chunkSize;
/*  301: 707 */         this.m_firstFree = 0;
/*  302:     */       }
/*  303:     */     }
/*  304: 712 */     this.m_firstFree += available;
/*  305:     */   }
/*  306:     */   
/*  307:     */   public final void append(FastStringBuffer value)
/*  308:     */   {
/*  309: 732 */     if (value == null) {
/*  310: 733 */       return;
/*  311:     */     }
/*  312: 734 */     int strlen = value.length();
/*  313: 736 */     if (0 == strlen) {
/*  314: 737 */       return;
/*  315:     */     }
/*  316: 739 */     int copyfrom = 0;
/*  317: 740 */     char[] chunk = this.m_array[this.m_lastChunk];
/*  318: 741 */     int available = this.m_chunkSize - this.m_firstFree;
/*  319: 744 */     while (strlen > 0)
/*  320:     */     {
/*  321: 748 */       if (available > strlen) {
/*  322: 749 */         available = strlen;
/*  323:     */       }
/*  324: 751 */       int sourcechunk = copyfrom + value.m_chunkSize - 1 >>> value.m_chunkBits;
/*  325:     */       
/*  326: 753 */       int sourcecolumn = copyfrom & value.m_chunkMask;
/*  327: 754 */       int runlength = value.m_chunkSize - sourcecolumn;
/*  328: 756 */       if (runlength > available) {
/*  329: 757 */         runlength = available;
/*  330:     */       }
/*  331: 759 */       System.arraycopy(value.m_array[sourcechunk], sourcecolumn, this.m_array[this.m_lastChunk], this.m_firstFree, runlength);
/*  332: 762 */       if (runlength != available) {
/*  333: 763 */         System.arraycopy(value.m_array[(sourcechunk + 1)], 0, this.m_array[this.m_lastChunk], this.m_firstFree + runlength, available - runlength);
/*  334:     */       }
/*  335: 767 */       strlen -= available;
/*  336: 768 */       copyfrom += available;
/*  337: 771 */       if (strlen > 0)
/*  338:     */       {
/*  339: 775 */         int i = this.m_array.length;
/*  340: 777 */         if (this.m_lastChunk + 1 == i)
/*  341:     */         {
/*  342: 779 */           char[][] newarray = new char[i + 16][];
/*  343:     */           
/*  344: 781 */           System.arraycopy(this.m_array, 0, newarray, 0, i);
/*  345:     */           
/*  346: 783 */           this.m_array = newarray;
/*  347:     */         }
/*  348: 787 */         chunk = this.m_array[(++this.m_lastChunk)];
/*  349: 789 */         if (chunk == null)
/*  350:     */         {
/*  351: 793 */           if ((this.m_lastChunk == 1 << this.m_rebundleBits) && (this.m_chunkBits < this.m_maxChunkBits)) {
/*  352: 799 */             this.m_innerFSB = new FastStringBuffer(this);
/*  353:     */           }
/*  354: 803 */           chunk = this.m_array[this.m_lastChunk] =  = new char[this.m_chunkSize];
/*  355:     */         }
/*  356: 806 */         available = this.m_chunkSize;
/*  357: 807 */         this.m_firstFree = 0;
/*  358:     */       }
/*  359:     */     }
/*  360: 812 */     this.m_firstFree += available;
/*  361:     */   }
/*  362:     */   
/*  363:     */   public boolean isWhitespace(int start, int length)
/*  364:     */   {
/*  365: 827 */     int sourcechunk = start >>> this.m_chunkBits;
/*  366: 828 */     int sourcecolumn = start & this.m_chunkMask;
/*  367: 829 */     int available = this.m_chunkSize - sourcecolumn;
/*  368: 832 */     while (length > 0)
/*  369:     */     {
/*  370: 834 */       int runlength = length <= available ? length : available;
/*  371:     */       boolean chunkOK;
/*  372: 836 */       if ((sourcechunk == 0) && (this.m_innerFSB != null)) {
/*  373: 837 */         chunkOK = this.m_innerFSB.isWhitespace(sourcecolumn, runlength);
/*  374:     */       } else {
/*  375: 839 */         chunkOK = XMLCharacterRecognizer.isWhiteSpace(this.m_array[sourcechunk], sourcecolumn, runlength);
/*  376:     */       }
/*  377: 842 */       if (!chunkOK) {
/*  378: 843 */         return false;
/*  379:     */       }
/*  380: 845 */       length -= runlength;
/*  381:     */       
/*  382: 847 */       sourcechunk++;
/*  383:     */       
/*  384: 849 */       sourcecolumn = 0;
/*  385: 850 */       available = this.m_chunkSize;
/*  386:     */     }
/*  387: 853 */     return true;
/*  388:     */   }
/*  389:     */   
/*  390:     */   public String getString(int start, int length)
/*  391:     */   {
/*  392: 864 */     int startColumn = start & this.m_chunkMask;
/*  393: 865 */     int startChunk = start >>> this.m_chunkBits;
/*  394: 866 */     if ((startColumn + length < this.m_chunkMask) && (this.m_innerFSB == null)) {
/*  395: 867 */       return getOneChunkString(startChunk, startColumn, length);
/*  396:     */     }
/*  397: 869 */     return getString(new StringBuffer(length), startChunk, startColumn, length).toString();
/*  398:     */   }
/*  399:     */   
/*  400:     */   protected String getOneChunkString(int startChunk, int startColumn, int length)
/*  401:     */   {
/*  402: 875 */     return new String(this.m_array[startChunk], startColumn, length);
/*  403:     */   }
/*  404:     */   
/*  405:     */   StringBuffer getString(StringBuffer sb, int start, int length)
/*  406:     */   {
/*  407: 886 */     return getString(sb, start >>> this.m_chunkBits, start & this.m_chunkMask, length);
/*  408:     */   }
/*  409:     */   
/*  410:     */   StringBuffer getString(StringBuffer sb, int startChunk, int startColumn, int length)
/*  411:     */   {
/*  412: 917 */     int stop = (startChunk << this.m_chunkBits) + startColumn + length;
/*  413: 918 */     int stopChunk = stop >>> this.m_chunkBits;
/*  414: 919 */     int stopColumn = stop & this.m_chunkMask;
/*  415: 923 */     for (int i = startChunk; i < stopChunk; i++)
/*  416:     */     {
/*  417: 925 */       if ((i == 0) && (this.m_innerFSB != null)) {
/*  418: 926 */         this.m_innerFSB.getString(sb, startColumn, this.m_chunkSize - startColumn);
/*  419:     */       } else {
/*  420: 928 */         sb.append(this.m_array[i], startColumn, this.m_chunkSize - startColumn);
/*  421:     */       }
/*  422: 930 */       startColumn = 0;
/*  423:     */     }
/*  424: 933 */     if ((stopChunk == 0) && (this.m_innerFSB != null)) {
/*  425: 934 */       this.m_innerFSB.getString(sb, startColumn, stopColumn - startColumn);
/*  426: 935 */     } else if (stopColumn > startColumn) {
/*  427: 936 */       sb.append(this.m_array[stopChunk], startColumn, stopColumn - startColumn);
/*  428:     */     }
/*  429: 938 */     return sb;
/*  430:     */   }
/*  431:     */   
/*  432:     */   public char charAt(int pos)
/*  433:     */   {
/*  434: 950 */     int startChunk = pos >>> this.m_chunkBits;
/*  435: 952 */     if ((startChunk == 0) && (this.m_innerFSB != null)) {
/*  436: 953 */       return this.m_innerFSB.charAt(pos & this.m_chunkMask);
/*  437:     */     }
/*  438: 955 */     return this.m_array[startChunk][(pos & this.m_chunkMask)];
/*  439:     */   }
/*  440:     */   
/*  441:     */   public void sendSAXcharacters(ContentHandler ch, int start, int length)
/*  442:     */     throws SAXException
/*  443:     */   {
/*  444: 982 */     int startChunk = start >>> this.m_chunkBits;
/*  445: 983 */     int startColumn = start & this.m_chunkMask;
/*  446: 984 */     if ((startColumn + length < this.m_chunkMask) && (this.m_innerFSB == null))
/*  447:     */     {
/*  448: 985 */       ch.characters(this.m_array[startChunk], startColumn, length);
/*  449: 986 */       return;
/*  450:     */     }
/*  451: 989 */     int stop = start + length;
/*  452: 990 */     int stopChunk = stop >>> this.m_chunkBits;
/*  453: 991 */     int stopColumn = stop & this.m_chunkMask;
/*  454: 993 */     for (int i = startChunk; i < stopChunk; i++)
/*  455:     */     {
/*  456: 995 */       if ((i == 0) && (this.m_innerFSB != null)) {
/*  457: 996 */         this.m_innerFSB.sendSAXcharacters(ch, startColumn, this.m_chunkSize - startColumn);
/*  458:     */       } else {
/*  459: 999 */         ch.characters(this.m_array[i], startColumn, this.m_chunkSize - startColumn);
/*  460:     */       }
/*  461:1001 */       startColumn = 0;
/*  462:     */     }
/*  463:1005 */     if ((stopChunk == 0) && (this.m_innerFSB != null)) {
/*  464:1006 */       this.m_innerFSB.sendSAXcharacters(ch, startColumn, stopColumn - startColumn);
/*  465:1007 */     } else if (stopColumn > startColumn) {
/*  466:1009 */       ch.characters(this.m_array[stopChunk], startColumn, stopColumn - startColumn);
/*  467:     */     }
/*  468:     */   }
/*  469:     */   
/*  470:     */   public int sendNormalizedSAXcharacters(ContentHandler ch, int start, int length)
/*  471:     */     throws SAXException
/*  472:     */   {
/*  473:1045 */     int stateForNextChunk = 1;
/*  474:     */     
/*  475:1047 */     int stop = start + length;
/*  476:1048 */     int startChunk = start >>> this.m_chunkBits;
/*  477:1049 */     int startColumn = start & this.m_chunkMask;
/*  478:1050 */     int stopChunk = stop >>> this.m_chunkBits;
/*  479:1051 */     int stopColumn = stop & this.m_chunkMask;
/*  480:1053 */     for (int i = startChunk; i < stopChunk; i++)
/*  481:     */     {
/*  482:1055 */       if ((i == 0) && (this.m_innerFSB != null)) {
/*  483:1056 */         stateForNextChunk = this.m_innerFSB.sendNormalizedSAXcharacters(ch, startColumn, this.m_chunkSize - startColumn);
/*  484:     */       } else {
/*  485:1060 */         stateForNextChunk = sendNormalizedSAXcharacters(this.m_array[i], startColumn, this.m_chunkSize - startColumn, ch, stateForNextChunk);
/*  486:     */       }
/*  487:1065 */       startColumn = 0;
/*  488:     */     }
/*  489:1069 */     if ((stopChunk == 0) && (this.m_innerFSB != null)) {
/*  490:1070 */       stateForNextChunk = this.m_innerFSB.sendNormalizedSAXcharacters(ch, startColumn, stopColumn - startColumn);
/*  491:1072 */     } else if (stopColumn > startColumn) {
/*  492:1074 */       stateForNextChunk = sendNormalizedSAXcharacters(this.m_array[stopChunk], startColumn, stopColumn - startColumn, ch, stateForNextChunk | 0x2);
/*  493:     */     }
/*  494:1079 */     return stateForNextChunk;
/*  495:     */   }
/*  496:     */   
/*  497:1082 */   static final char[] SINGLE_SPACE = { ' ' };
/*  498:     */   
/*  499:     */   static int sendNormalizedSAXcharacters(char[] ch, int start, int length, ContentHandler handler, int edgeTreatmentFlags)
/*  500:     */     throws SAXException
/*  501:     */   {
/*  502:1133 */     boolean processingLeadingWhitespace = (edgeTreatmentFlags & 0x1) != 0;
/*  503:     */     
/*  504:1135 */     boolean seenWhitespace = (edgeTreatmentFlags & 0x4) != 0;
/*  505:1136 */     int currPos = start;
/*  506:1137 */     int limit = start + length;
/*  507:1140 */     if (processingLeadingWhitespace)
/*  508:     */     {
/*  509:1142 */       while ((currPos < limit) && (XMLCharacterRecognizer.isWhiteSpace(ch[currPos]))) {
/*  510:1143 */         currPos++;
/*  511:     */       }
/*  512:1147 */       if (currPos == limit) {
/*  513:1148 */         return edgeTreatmentFlags;
/*  514:     */       }
/*  515:     */     }
/*  516:1153 */     while (currPos < limit)
/*  517:     */     {
/*  518:1154 */       int startNonWhitespace = currPos;
/*  519:1158 */       while ((currPos < limit) && (!XMLCharacterRecognizer.isWhiteSpace(ch[currPos]))) {
/*  520:1159 */         currPos++;
/*  521:     */       }
/*  522:1163 */       if (startNonWhitespace != currPos)
/*  523:     */       {
/*  524:1164 */         if (seenWhitespace)
/*  525:     */         {
/*  526:1165 */           handler.characters(SINGLE_SPACE, 0, 1);
/*  527:1166 */           seenWhitespace = false;
/*  528:     */         }
/*  529:1168 */         handler.characters(ch, startNonWhitespace, currPos - startNonWhitespace);
/*  530:     */       }
/*  531:1172 */       int startWhitespace = currPos;
/*  532:1176 */       while ((currPos < limit) && (XMLCharacterRecognizer.isWhiteSpace(ch[currPos]))) {
/*  533:1177 */         currPos++;
/*  534:     */       }
/*  535:1179 */       if (startWhitespace != currPos) {
/*  536:1180 */         seenWhitespace = true;
/*  537:     */       }
/*  538:     */     }
/*  539:1184 */     return (seenWhitespace ? 4 : 0) | edgeTreatmentFlags & 0x2;
/*  540:     */   }
/*  541:     */   
/*  542:     */   public static void sendNormalizedSAXcharacters(char[] ch, int start, int length, ContentHandler handler)
/*  543:     */     throws SAXException
/*  544:     */   {
/*  545:1203 */     sendNormalizedSAXcharacters(ch, start, length, handler, 3);
/*  546:     */   }
/*  547:     */   
/*  548:     */   public void sendSAXComment(LexicalHandler ch, int start, int length)
/*  549:     */     throws SAXException
/*  550:     */   {
/*  551:1225 */     String comment = getString(start, length);
/*  552:1226 */     ch.comment(comment.toCharArray(), 0, length);
/*  553:     */   }
/*  554:     */   
/*  555:     */   private void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin) {}
/*  556:     */   
/*  557:     */   private FastStringBuffer(FastStringBuffer source)
/*  558:     */   {
/*  559:1267 */     this.m_chunkBits = source.m_chunkBits;
/*  560:1268 */     this.m_maxChunkBits = source.m_maxChunkBits;
/*  561:1269 */     this.m_rebundleBits = source.m_rebundleBits;
/*  562:1270 */     this.m_chunkSize = source.m_chunkSize;
/*  563:1271 */     this.m_chunkMask = source.m_chunkMask;
/*  564:1272 */     this.m_array = source.m_array;
/*  565:1273 */     this.m_innerFSB = source.m_innerFSB;
/*  566:     */     
/*  567:     */ 
/*  568:     */ 
/*  569:1277 */     source.m_lastChunk -= 1;
/*  570:1278 */     this.m_firstFree = source.m_chunkSize;
/*  571:     */     
/*  572:     */ 
/*  573:1281 */     source.m_array = new char[16][];
/*  574:1282 */     source.m_innerFSB = this;
/*  575:     */     
/*  576:     */ 
/*  577:     */ 
/*  578:     */ 
/*  579:1287 */     source.m_lastChunk = 1;
/*  580:1288 */     source.m_firstFree = 0;
/*  581:1289 */     source.m_chunkBits += this.m_rebundleBits;
/*  582:1290 */     source.m_chunkSize = (1 << source.m_chunkBits);
/*  583:1291 */     source.m_chunkMask = (source.m_chunkSize - 1);
/*  584:     */   }
/*  585:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.utils.FastStringBuffer
 * JD-Core Version:    0.7.0.1
 */