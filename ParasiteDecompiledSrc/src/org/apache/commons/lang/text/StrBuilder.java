/*    1:     */ package org.apache.commons.lang.text;
/*    2:     */ 
/*    3:     */ import java.io.Reader;
/*    4:     */ import java.io.Writer;
/*    5:     */ import java.util.Collection;
/*    6:     */ import java.util.Iterator;
/*    7:     */ import java.util.List;
/*    8:     */ import org.apache.commons.lang.ArrayUtils;
/*    9:     */ import org.apache.commons.lang.SystemUtils;
/*   10:     */ 
/*   11:     */ public class StrBuilder
/*   12:     */   implements Cloneable
/*   13:     */ {
/*   14:     */   static final int CAPACITY = 32;
/*   15:     */   private static final long serialVersionUID = 7628716375283629643L;
/*   16:     */   protected char[] buffer;
/*   17:     */   protected int size;
/*   18:     */   private String newLine;
/*   19:     */   private String nullText;
/*   20:     */   
/*   21:     */   public StrBuilder()
/*   22:     */   {
/*   23: 102 */     this(32);
/*   24:     */   }
/*   25:     */   
/*   26:     */   public StrBuilder(int initialCapacity)
/*   27:     */   {
/*   28: 112 */     if (initialCapacity <= 0) {
/*   29: 113 */       initialCapacity = 32;
/*   30:     */     }
/*   31: 115 */     this.buffer = new char[initialCapacity];
/*   32:     */   }
/*   33:     */   
/*   34:     */   public StrBuilder(String str)
/*   35:     */   {
/*   36: 126 */     if (str == null)
/*   37:     */     {
/*   38: 127 */       this.buffer = new char[32];
/*   39:     */     }
/*   40:     */     else
/*   41:     */     {
/*   42: 129 */       this.buffer = new char[str.length() + 32];
/*   43: 130 */       append(str);
/*   44:     */     }
/*   45:     */   }
/*   46:     */   
/*   47:     */   public String getNewLineText()
/*   48:     */   {
/*   49: 141 */     return this.newLine;
/*   50:     */   }
/*   51:     */   
/*   52:     */   public StrBuilder setNewLineText(String newLine)
/*   53:     */   {
/*   54: 151 */     this.newLine = newLine;
/*   55: 152 */     return this;
/*   56:     */   }
/*   57:     */   
/*   58:     */   public String getNullText()
/*   59:     */   {
/*   60: 162 */     return this.nullText;
/*   61:     */   }
/*   62:     */   
/*   63:     */   public StrBuilder setNullText(String nullText)
/*   64:     */   {
/*   65: 172 */     if ((nullText != null) && (nullText.length() == 0)) {
/*   66: 173 */       nullText = null;
/*   67:     */     }
/*   68: 175 */     this.nullText = nullText;
/*   69: 176 */     return this;
/*   70:     */   }
/*   71:     */   
/*   72:     */   public int length()
/*   73:     */   {
/*   74: 186 */     return this.size;
/*   75:     */   }
/*   76:     */   
/*   77:     */   public StrBuilder setLength(int length)
/*   78:     */   {
/*   79: 198 */     if (length < 0) {
/*   80: 199 */       throw new StringIndexOutOfBoundsException(length);
/*   81:     */     }
/*   82: 201 */     if (length < this.size)
/*   83:     */     {
/*   84: 202 */       this.size = length;
/*   85:     */     }
/*   86: 203 */     else if (length > this.size)
/*   87:     */     {
/*   88: 204 */       ensureCapacity(length);
/*   89: 205 */       int oldEnd = this.size;
/*   90: 206 */       int newEnd = length;
/*   91: 207 */       this.size = length;
/*   92: 208 */       for (int i = oldEnd; i < newEnd; i++) {
/*   93: 209 */         this.buffer[i] = '\000';
/*   94:     */       }
/*   95:     */     }
/*   96: 212 */     return this;
/*   97:     */   }
/*   98:     */   
/*   99:     */   public int capacity()
/*  100:     */   {
/*  101: 222 */     return this.buffer.length;
/*  102:     */   }
/*  103:     */   
/*  104:     */   public StrBuilder ensureCapacity(int capacity)
/*  105:     */   {
/*  106: 232 */     if (capacity > this.buffer.length)
/*  107:     */     {
/*  108: 233 */       char[] old = this.buffer;
/*  109: 234 */       this.buffer = new char[capacity * 2];
/*  110: 235 */       System.arraycopy(old, 0, this.buffer, 0, this.size);
/*  111:     */     }
/*  112: 237 */     return this;
/*  113:     */   }
/*  114:     */   
/*  115:     */   public StrBuilder minimizeCapacity()
/*  116:     */   {
/*  117: 246 */     if (this.buffer.length > length())
/*  118:     */     {
/*  119: 247 */       char[] old = this.buffer;
/*  120: 248 */       this.buffer = new char[length()];
/*  121: 249 */       System.arraycopy(old, 0, this.buffer, 0, this.size);
/*  122:     */     }
/*  123: 251 */     return this;
/*  124:     */   }
/*  125:     */   
/*  126:     */   public int size()
/*  127:     */   {
/*  128: 264 */     return this.size;
/*  129:     */   }
/*  130:     */   
/*  131:     */   public boolean isEmpty()
/*  132:     */   {
/*  133: 276 */     return this.size == 0;
/*  134:     */   }
/*  135:     */   
/*  136:     */   public StrBuilder clear()
/*  137:     */   {
/*  138: 291 */     this.size = 0;
/*  139: 292 */     return this;
/*  140:     */   }
/*  141:     */   
/*  142:     */   public char charAt(int index)
/*  143:     */   {
/*  144: 306 */     if ((index < 0) || (index >= length())) {
/*  145: 307 */       throw new StringIndexOutOfBoundsException(index);
/*  146:     */     }
/*  147: 309 */     return this.buffer[index];
/*  148:     */   }
/*  149:     */   
/*  150:     */   public StrBuilder setCharAt(int index, char ch)
/*  151:     */   {
/*  152: 323 */     if ((index < 0) || (index >= length())) {
/*  153: 324 */       throw new StringIndexOutOfBoundsException(index);
/*  154:     */     }
/*  155: 326 */     this.buffer[index] = ch;
/*  156: 327 */     return this;
/*  157:     */   }
/*  158:     */   
/*  159:     */   public StrBuilder deleteCharAt(int index)
/*  160:     */   {
/*  161: 340 */     if ((index < 0) || (index >= this.size)) {
/*  162: 341 */       throw new StringIndexOutOfBoundsException(index);
/*  163:     */     }
/*  164: 343 */     deleteImpl(index, index + 1, 1);
/*  165: 344 */     return this;
/*  166:     */   }
/*  167:     */   
/*  168:     */   public char[] toCharArray()
/*  169:     */   {
/*  170: 354 */     if (this.size == 0) {
/*  171: 355 */       return ArrayUtils.EMPTY_CHAR_ARRAY;
/*  172:     */     }
/*  173: 357 */     char[] chars = new char[this.size];
/*  174: 358 */     System.arraycopy(this.buffer, 0, chars, 0, this.size);
/*  175: 359 */     return chars;
/*  176:     */   }
/*  177:     */   
/*  178:     */   public char[] toCharArray(int startIndex, int endIndex)
/*  179:     */   {
/*  180: 373 */     endIndex = validateRange(startIndex, endIndex);
/*  181: 374 */     int len = endIndex - startIndex;
/*  182: 375 */     if (len == 0) {
/*  183: 376 */       return ArrayUtils.EMPTY_CHAR_ARRAY;
/*  184:     */     }
/*  185: 378 */     char[] chars = new char[len];
/*  186: 379 */     System.arraycopy(this.buffer, startIndex, chars, 0, len);
/*  187: 380 */     return chars;
/*  188:     */   }
/*  189:     */   
/*  190:     */   public char[] getChars(char[] destination)
/*  191:     */   {
/*  192: 390 */     int len = length();
/*  193: 391 */     if ((destination == null) || (destination.length < len)) {
/*  194: 392 */       destination = new char[len];
/*  195:     */     }
/*  196: 394 */     System.arraycopy(this.buffer, 0, destination, 0, len);
/*  197: 395 */     return destination;
/*  198:     */   }
/*  199:     */   
/*  200:     */   public void getChars(int startIndex, int endIndex, char[] destination, int destinationIndex)
/*  201:     */   {
/*  202: 409 */     if (startIndex < 0) {
/*  203: 410 */       throw new StringIndexOutOfBoundsException(startIndex);
/*  204:     */     }
/*  205: 412 */     if ((endIndex < 0) || (endIndex > length())) {
/*  206: 413 */       throw new StringIndexOutOfBoundsException(endIndex);
/*  207:     */     }
/*  208: 415 */     if (startIndex > endIndex) {
/*  209: 416 */       throw new StringIndexOutOfBoundsException("end < start");
/*  210:     */     }
/*  211: 418 */     System.arraycopy(this.buffer, startIndex, destination, destinationIndex, endIndex - startIndex);
/*  212:     */   }
/*  213:     */   
/*  214:     */   public StrBuilder appendNewLine()
/*  215:     */   {
/*  216: 432 */     if (this.newLine == null)
/*  217:     */     {
/*  218: 433 */       append(SystemUtils.LINE_SEPARATOR);
/*  219: 434 */       return this;
/*  220:     */     }
/*  221: 436 */     return append(this.newLine);
/*  222:     */   }
/*  223:     */   
/*  224:     */   public StrBuilder appendNull()
/*  225:     */   {
/*  226: 445 */     if (this.nullText == null) {
/*  227: 446 */       return this;
/*  228:     */     }
/*  229: 448 */     return append(this.nullText);
/*  230:     */   }
/*  231:     */   
/*  232:     */   public StrBuilder append(Object obj)
/*  233:     */   {
/*  234: 459 */     if (obj == null) {
/*  235: 460 */       return appendNull();
/*  236:     */     }
/*  237: 462 */     return append(obj.toString());
/*  238:     */   }
/*  239:     */   
/*  240:     */   public StrBuilder append(String str)
/*  241:     */   {
/*  242: 473 */     if (str == null) {
/*  243: 474 */       return appendNull();
/*  244:     */     }
/*  245: 476 */     int strLen = str.length();
/*  246: 477 */     if (strLen > 0)
/*  247:     */     {
/*  248: 478 */       int len = length();
/*  249: 479 */       ensureCapacity(len + strLen);
/*  250: 480 */       str.getChars(0, strLen, this.buffer, len);
/*  251: 481 */       this.size += strLen;
/*  252:     */     }
/*  253: 483 */     return this;
/*  254:     */   }
/*  255:     */   
/*  256:     */   public StrBuilder append(String str, int startIndex, int length)
/*  257:     */   {
/*  258: 496 */     if (str == null) {
/*  259: 497 */       return appendNull();
/*  260:     */     }
/*  261: 499 */     if ((startIndex < 0) || (startIndex > str.length())) {
/*  262: 500 */       throw new StringIndexOutOfBoundsException("startIndex must be valid");
/*  263:     */     }
/*  264: 502 */     if ((length < 0) || (startIndex + length > str.length())) {
/*  265: 503 */       throw new StringIndexOutOfBoundsException("length must be valid");
/*  266:     */     }
/*  267: 505 */     if (length > 0)
/*  268:     */     {
/*  269: 506 */       int len = length();
/*  270: 507 */       ensureCapacity(len + length);
/*  271: 508 */       str.getChars(startIndex, startIndex + length, this.buffer, len);
/*  272: 509 */       this.size += length;
/*  273:     */     }
/*  274: 511 */     return this;
/*  275:     */   }
/*  276:     */   
/*  277:     */   public StrBuilder append(StringBuffer str)
/*  278:     */   {
/*  279: 522 */     if (str == null) {
/*  280: 523 */       return appendNull();
/*  281:     */     }
/*  282: 525 */     int strLen = str.length();
/*  283: 526 */     if (strLen > 0)
/*  284:     */     {
/*  285: 527 */       int len = length();
/*  286: 528 */       ensureCapacity(len + strLen);
/*  287: 529 */       str.getChars(0, strLen, this.buffer, len);
/*  288: 530 */       this.size += strLen;
/*  289:     */     }
/*  290: 532 */     return this;
/*  291:     */   }
/*  292:     */   
/*  293:     */   public StrBuilder append(StringBuffer str, int startIndex, int length)
/*  294:     */   {
/*  295: 545 */     if (str == null) {
/*  296: 546 */       return appendNull();
/*  297:     */     }
/*  298: 548 */     if ((startIndex < 0) || (startIndex > str.length())) {
/*  299: 549 */       throw new StringIndexOutOfBoundsException("startIndex must be valid");
/*  300:     */     }
/*  301: 551 */     if ((length < 0) || (startIndex + length > str.length())) {
/*  302: 552 */       throw new StringIndexOutOfBoundsException("length must be valid");
/*  303:     */     }
/*  304: 554 */     if (length > 0)
/*  305:     */     {
/*  306: 555 */       int len = length();
/*  307: 556 */       ensureCapacity(len + length);
/*  308: 557 */       str.getChars(startIndex, startIndex + length, this.buffer, len);
/*  309: 558 */       this.size += length;
/*  310:     */     }
/*  311: 560 */     return this;
/*  312:     */   }
/*  313:     */   
/*  314:     */   public StrBuilder append(StrBuilder str)
/*  315:     */   {
/*  316: 571 */     if (str == null) {
/*  317: 572 */       return appendNull();
/*  318:     */     }
/*  319: 574 */     int strLen = str.length();
/*  320: 575 */     if (strLen > 0)
/*  321:     */     {
/*  322: 576 */       int len = length();
/*  323: 577 */       ensureCapacity(len + strLen);
/*  324: 578 */       System.arraycopy(str.buffer, 0, this.buffer, len, strLen);
/*  325: 579 */       this.size += strLen;
/*  326:     */     }
/*  327: 581 */     return this;
/*  328:     */   }
/*  329:     */   
/*  330:     */   public StrBuilder append(StrBuilder str, int startIndex, int length)
/*  331:     */   {
/*  332: 594 */     if (str == null) {
/*  333: 595 */       return appendNull();
/*  334:     */     }
/*  335: 597 */     if ((startIndex < 0) || (startIndex > str.length())) {
/*  336: 598 */       throw new StringIndexOutOfBoundsException("startIndex must be valid");
/*  337:     */     }
/*  338: 600 */     if ((length < 0) || (startIndex + length > str.length())) {
/*  339: 601 */       throw new StringIndexOutOfBoundsException("length must be valid");
/*  340:     */     }
/*  341: 603 */     if (length > 0)
/*  342:     */     {
/*  343: 604 */       int len = length();
/*  344: 605 */       ensureCapacity(len + length);
/*  345: 606 */       str.getChars(startIndex, startIndex + length, this.buffer, len);
/*  346: 607 */       this.size += length;
/*  347:     */     }
/*  348: 609 */     return this;
/*  349:     */   }
/*  350:     */   
/*  351:     */   public StrBuilder append(char[] chars)
/*  352:     */   {
/*  353: 620 */     if (chars == null) {
/*  354: 621 */       return appendNull();
/*  355:     */     }
/*  356: 623 */     int strLen = chars.length;
/*  357: 624 */     if (strLen > 0)
/*  358:     */     {
/*  359: 625 */       int len = length();
/*  360: 626 */       ensureCapacity(len + strLen);
/*  361: 627 */       System.arraycopy(chars, 0, this.buffer, len, strLen);
/*  362: 628 */       this.size += strLen;
/*  363:     */     }
/*  364: 630 */     return this;
/*  365:     */   }
/*  366:     */   
/*  367:     */   public StrBuilder append(char[] chars, int startIndex, int length)
/*  368:     */   {
/*  369: 643 */     if (chars == null) {
/*  370: 644 */       return appendNull();
/*  371:     */     }
/*  372: 646 */     if ((startIndex < 0) || (startIndex > chars.length)) {
/*  373: 647 */       throw new StringIndexOutOfBoundsException("Invalid startIndex: " + length);
/*  374:     */     }
/*  375: 649 */     if ((length < 0) || (startIndex + length > chars.length)) {
/*  376: 650 */       throw new StringIndexOutOfBoundsException("Invalid length: " + length);
/*  377:     */     }
/*  378: 652 */     if (length > 0)
/*  379:     */     {
/*  380: 653 */       int len = length();
/*  381: 654 */       ensureCapacity(len + length);
/*  382: 655 */       System.arraycopy(chars, startIndex, this.buffer, len, length);
/*  383: 656 */       this.size += length;
/*  384:     */     }
/*  385: 658 */     return this;
/*  386:     */   }
/*  387:     */   
/*  388:     */   public StrBuilder append(boolean value)
/*  389:     */   {
/*  390: 668 */     if (value)
/*  391:     */     {
/*  392: 669 */       ensureCapacity(this.size + 4);
/*  393: 670 */       this.buffer[(this.size++)] = 't';
/*  394: 671 */       this.buffer[(this.size++)] = 'r';
/*  395: 672 */       this.buffer[(this.size++)] = 'u';
/*  396: 673 */       this.buffer[(this.size++)] = 'e';
/*  397:     */     }
/*  398:     */     else
/*  399:     */     {
/*  400: 675 */       ensureCapacity(this.size + 5);
/*  401: 676 */       this.buffer[(this.size++)] = 'f';
/*  402: 677 */       this.buffer[(this.size++)] = 'a';
/*  403: 678 */       this.buffer[(this.size++)] = 'l';
/*  404: 679 */       this.buffer[(this.size++)] = 's';
/*  405: 680 */       this.buffer[(this.size++)] = 'e';
/*  406:     */     }
/*  407: 682 */     return this;
/*  408:     */   }
/*  409:     */   
/*  410:     */   public StrBuilder append(char ch)
/*  411:     */   {
/*  412: 692 */     int len = length();
/*  413: 693 */     ensureCapacity(len + 1);
/*  414: 694 */     this.buffer[(this.size++)] = ch;
/*  415: 695 */     return this;
/*  416:     */   }
/*  417:     */   
/*  418:     */   public StrBuilder append(int value)
/*  419:     */   {
/*  420: 705 */     return append(String.valueOf(value));
/*  421:     */   }
/*  422:     */   
/*  423:     */   public StrBuilder append(long value)
/*  424:     */   {
/*  425: 715 */     return append(String.valueOf(value));
/*  426:     */   }
/*  427:     */   
/*  428:     */   public StrBuilder append(float value)
/*  429:     */   {
/*  430: 725 */     return append(String.valueOf(value));
/*  431:     */   }
/*  432:     */   
/*  433:     */   public StrBuilder append(double value)
/*  434:     */   {
/*  435: 735 */     return append(String.valueOf(value));
/*  436:     */   }
/*  437:     */   
/*  438:     */   public StrBuilder appendln(Object obj)
/*  439:     */   {
/*  440: 748 */     return append(obj).appendNewLine();
/*  441:     */   }
/*  442:     */   
/*  443:     */   public StrBuilder appendln(String str)
/*  444:     */   {
/*  445: 760 */     return append(str).appendNewLine();
/*  446:     */   }
/*  447:     */   
/*  448:     */   public StrBuilder appendln(String str, int startIndex, int length)
/*  449:     */   {
/*  450: 774 */     return append(str, startIndex, length).appendNewLine();
/*  451:     */   }
/*  452:     */   
/*  453:     */   public StrBuilder appendln(StringBuffer str)
/*  454:     */   {
/*  455: 786 */     return append(str).appendNewLine();
/*  456:     */   }
/*  457:     */   
/*  458:     */   public StrBuilder appendln(StringBuffer str, int startIndex, int length)
/*  459:     */   {
/*  460: 800 */     return append(str, startIndex, length).appendNewLine();
/*  461:     */   }
/*  462:     */   
/*  463:     */   public StrBuilder appendln(StrBuilder str)
/*  464:     */   {
/*  465: 812 */     return append(str).appendNewLine();
/*  466:     */   }
/*  467:     */   
/*  468:     */   public StrBuilder appendln(StrBuilder str, int startIndex, int length)
/*  469:     */   {
/*  470: 826 */     return append(str, startIndex, length).appendNewLine();
/*  471:     */   }
/*  472:     */   
/*  473:     */   public StrBuilder appendln(char[] chars)
/*  474:     */   {
/*  475: 838 */     return append(chars).appendNewLine();
/*  476:     */   }
/*  477:     */   
/*  478:     */   public StrBuilder appendln(char[] chars, int startIndex, int length)
/*  479:     */   {
/*  480: 852 */     return append(chars, startIndex, length).appendNewLine();
/*  481:     */   }
/*  482:     */   
/*  483:     */   public StrBuilder appendln(boolean value)
/*  484:     */   {
/*  485: 863 */     return append(value).appendNewLine();
/*  486:     */   }
/*  487:     */   
/*  488:     */   public StrBuilder appendln(char ch)
/*  489:     */   {
/*  490: 874 */     return append(ch).appendNewLine();
/*  491:     */   }
/*  492:     */   
/*  493:     */   public StrBuilder appendln(int value)
/*  494:     */   {
/*  495: 885 */     return append(value).appendNewLine();
/*  496:     */   }
/*  497:     */   
/*  498:     */   public StrBuilder appendln(long value)
/*  499:     */   {
/*  500: 896 */     return append(value).appendNewLine();
/*  501:     */   }
/*  502:     */   
/*  503:     */   public StrBuilder appendln(float value)
/*  504:     */   {
/*  505: 907 */     return append(value).appendNewLine();
/*  506:     */   }
/*  507:     */   
/*  508:     */   public StrBuilder appendln(double value)
/*  509:     */   {
/*  510: 918 */     return append(value).appendNewLine();
/*  511:     */   }
/*  512:     */   
/*  513:     */   public StrBuilder appendAll(Object[] array)
/*  514:     */   {
/*  515: 932 */     if ((array != null) && (array.length > 0)) {
/*  516: 933 */       for (int i = 0; i < array.length; i++) {
/*  517: 934 */         append(array[i]);
/*  518:     */       }
/*  519:     */     }
/*  520: 937 */     return this;
/*  521:     */   }
/*  522:     */   
/*  523:     */   public StrBuilder appendAll(Collection coll)
/*  524:     */   {
/*  525: 950 */     if ((coll != null) && (coll.size() > 0))
/*  526:     */     {
/*  527: 951 */       Iterator it = coll.iterator();
/*  528: 952 */       while (it.hasNext()) {
/*  529: 953 */         append(it.next());
/*  530:     */       }
/*  531:     */     }
/*  532: 956 */     return this;
/*  533:     */   }
/*  534:     */   
/*  535:     */   public StrBuilder appendAll(Iterator it)
/*  536:     */   {
/*  537: 969 */     if (it != null) {
/*  538: 970 */       while (it.hasNext()) {
/*  539: 971 */         append(it.next());
/*  540:     */       }
/*  541:     */     }
/*  542: 974 */     return this;
/*  543:     */   }
/*  544:     */   
/*  545:     */   public StrBuilder appendWithSeparators(Object[] array, String separator)
/*  546:     */   {
/*  547: 989 */     if ((array != null) && (array.length > 0))
/*  548:     */     {
/*  549: 990 */       separator = separator == null ? "" : separator;
/*  550: 991 */       append(array[0]);
/*  551: 992 */       for (int i = 1; i < array.length; i++)
/*  552:     */       {
/*  553: 993 */         append(separator);
/*  554: 994 */         append(array[i]);
/*  555:     */       }
/*  556:     */     }
/*  557: 997 */     return this;
/*  558:     */   }
/*  559:     */   
/*  560:     */   public StrBuilder appendWithSeparators(Collection coll, String separator)
/*  561:     */   {
/*  562:1011 */     if ((coll != null) && (coll.size() > 0))
/*  563:     */     {
/*  564:1012 */       separator = separator == null ? "" : separator;
/*  565:1013 */       Iterator it = coll.iterator();
/*  566:1014 */       while (it.hasNext())
/*  567:     */       {
/*  568:1015 */         append(it.next());
/*  569:1016 */         if (it.hasNext()) {
/*  570:1017 */           append(separator);
/*  571:     */         }
/*  572:     */       }
/*  573:     */     }
/*  574:1021 */     return this;
/*  575:     */   }
/*  576:     */   
/*  577:     */   public StrBuilder appendWithSeparators(Iterator it, String separator)
/*  578:     */   {
/*  579:1035 */     if (it != null)
/*  580:     */     {
/*  581:1036 */       separator = separator == null ? "" : separator;
/*  582:1037 */       while (it.hasNext())
/*  583:     */       {
/*  584:1038 */         append(it.next());
/*  585:1039 */         if (it.hasNext()) {
/*  586:1040 */           append(separator);
/*  587:     */         }
/*  588:     */       }
/*  589:     */     }
/*  590:1044 */     return this;
/*  591:     */   }
/*  592:     */   
/*  593:     */   public StrBuilder appendSeparator(String separator)
/*  594:     */   {
/*  595:1069 */     return appendSeparator(separator, null);
/*  596:     */   }
/*  597:     */   
/*  598:     */   public StrBuilder appendSeparator(String standard, String defaultIfEmpty)
/*  599:     */   {
/*  600:1100 */     String str = isEmpty() ? defaultIfEmpty : standard;
/*  601:1101 */     if (str != null) {
/*  602:1102 */       append(str);
/*  603:     */     }
/*  604:1104 */     return this;
/*  605:     */   }
/*  606:     */   
/*  607:     */   public StrBuilder appendSeparator(char separator)
/*  608:     */   {
/*  609:1127 */     if (size() > 0) {
/*  610:1128 */       append(separator);
/*  611:     */     }
/*  612:1130 */     return this;
/*  613:     */   }
/*  614:     */   
/*  615:     */   public StrBuilder appendSeparator(char standard, char defaultIfEmpty)
/*  616:     */   {
/*  617:1145 */     if (size() > 0) {
/*  618:1146 */       append(standard);
/*  619:     */     } else {
/*  620:1149 */       append(defaultIfEmpty);
/*  621:     */     }
/*  622:1151 */     return this;
/*  623:     */   }
/*  624:     */   
/*  625:     */   public StrBuilder appendSeparator(String separator, int loopIndex)
/*  626:     */   {
/*  627:1175 */     if ((separator != null) && (loopIndex > 0)) {
/*  628:1176 */       append(separator);
/*  629:     */     }
/*  630:1178 */     return this;
/*  631:     */   }
/*  632:     */   
/*  633:     */   public StrBuilder appendSeparator(char separator, int loopIndex)
/*  634:     */   {
/*  635:1202 */     if (loopIndex > 0) {
/*  636:1203 */       append(separator);
/*  637:     */     }
/*  638:1205 */     return this;
/*  639:     */   }
/*  640:     */   
/*  641:     */   public StrBuilder appendPadding(int length, char padChar)
/*  642:     */   {
/*  643:1217 */     if (length >= 0)
/*  644:     */     {
/*  645:1218 */       ensureCapacity(this.size + length);
/*  646:1219 */       for (int i = 0; i < length; i++) {
/*  647:1220 */         this.buffer[(this.size++)] = padChar;
/*  648:     */       }
/*  649:     */     }
/*  650:1223 */     return this;
/*  651:     */   }
/*  652:     */   
/*  653:     */   public StrBuilder appendFixedWidthPadLeft(Object obj, int width, char padChar)
/*  654:     */   {
/*  655:1239 */     if (width > 0)
/*  656:     */     {
/*  657:1240 */       ensureCapacity(this.size + width);
/*  658:1241 */       String str = obj == null ? getNullText() : obj.toString();
/*  659:1242 */       if (str == null) {
/*  660:1243 */         str = "";
/*  661:     */       }
/*  662:1245 */       int strLen = str.length();
/*  663:1246 */       if (strLen >= width)
/*  664:     */       {
/*  665:1247 */         str.getChars(strLen - width, strLen, this.buffer, this.size);
/*  666:     */       }
/*  667:     */       else
/*  668:     */       {
/*  669:1249 */         int padLen = width - strLen;
/*  670:1250 */         for (int i = 0; i < padLen; i++) {
/*  671:1251 */           this.buffer[(this.size + i)] = padChar;
/*  672:     */         }
/*  673:1253 */         str.getChars(0, strLen, this.buffer, this.size + padLen);
/*  674:     */       }
/*  675:1255 */       this.size += width;
/*  676:     */     }
/*  677:1257 */     return this;
/*  678:     */   }
/*  679:     */   
/*  680:     */   public StrBuilder appendFixedWidthPadLeft(int value, int width, char padChar)
/*  681:     */   {
/*  682:1271 */     return appendFixedWidthPadLeft(String.valueOf(value), width, padChar);
/*  683:     */   }
/*  684:     */   
/*  685:     */   public StrBuilder appendFixedWidthPadRight(Object obj, int width, char padChar)
/*  686:     */   {
/*  687:1286 */     if (width > 0)
/*  688:     */     {
/*  689:1287 */       ensureCapacity(this.size + width);
/*  690:1288 */       String str = obj == null ? getNullText() : obj.toString();
/*  691:1289 */       if (str == null) {
/*  692:1290 */         str = "";
/*  693:     */       }
/*  694:1292 */       int strLen = str.length();
/*  695:1293 */       if (strLen >= width)
/*  696:     */       {
/*  697:1294 */         str.getChars(0, width, this.buffer, this.size);
/*  698:     */       }
/*  699:     */       else
/*  700:     */       {
/*  701:1296 */         int padLen = width - strLen;
/*  702:1297 */         str.getChars(0, strLen, this.buffer, this.size);
/*  703:1298 */         for (int i = 0; i < padLen; i++) {
/*  704:1299 */           this.buffer[(this.size + strLen + i)] = padChar;
/*  705:     */         }
/*  706:     */       }
/*  707:1302 */       this.size += width;
/*  708:     */     }
/*  709:1304 */     return this;
/*  710:     */   }
/*  711:     */   
/*  712:     */   public StrBuilder appendFixedWidthPadRight(int value, int width, char padChar)
/*  713:     */   {
/*  714:1318 */     return appendFixedWidthPadRight(String.valueOf(value), width, padChar);
/*  715:     */   }
/*  716:     */   
/*  717:     */   public StrBuilder insert(int index, Object obj)
/*  718:     */   {
/*  719:1332 */     if (obj == null) {
/*  720:1333 */       return insert(index, this.nullText);
/*  721:     */     }
/*  722:1335 */     return insert(index, obj.toString());
/*  723:     */   }
/*  724:     */   
/*  725:     */   public StrBuilder insert(int index, String str)
/*  726:     */   {
/*  727:1348 */     validateIndex(index);
/*  728:1349 */     if (str == null) {
/*  729:1350 */       str = this.nullText;
/*  730:     */     }
/*  731:1352 */     int strLen = str == null ? 0 : str.length();
/*  732:1353 */     if (strLen > 0)
/*  733:     */     {
/*  734:1354 */       int newSize = this.size + strLen;
/*  735:1355 */       ensureCapacity(newSize);
/*  736:1356 */       System.arraycopy(this.buffer, index, this.buffer, index + strLen, this.size - index);
/*  737:1357 */       this.size = newSize;
/*  738:1358 */       str.getChars(0, strLen, this.buffer, index);
/*  739:     */     }
/*  740:1360 */     return this;
/*  741:     */   }
/*  742:     */   
/*  743:     */   public StrBuilder insert(int index, char[] chars)
/*  744:     */   {
/*  745:1373 */     validateIndex(index);
/*  746:1374 */     if (chars == null) {
/*  747:1375 */       return insert(index, this.nullText);
/*  748:     */     }
/*  749:1377 */     int len = chars.length;
/*  750:1378 */     if (len > 0)
/*  751:     */     {
/*  752:1379 */       ensureCapacity(this.size + len);
/*  753:1380 */       System.arraycopy(this.buffer, index, this.buffer, index + len, this.size - index);
/*  754:1381 */       System.arraycopy(chars, 0, this.buffer, index, len);
/*  755:1382 */       this.size += len;
/*  756:     */     }
/*  757:1384 */     return this;
/*  758:     */   }
/*  759:     */   
/*  760:     */   public StrBuilder insert(int index, char[] chars, int offset, int length)
/*  761:     */   {
/*  762:1399 */     validateIndex(index);
/*  763:1400 */     if (chars == null) {
/*  764:1401 */       return insert(index, this.nullText);
/*  765:     */     }
/*  766:1403 */     if ((offset < 0) || (offset > chars.length)) {
/*  767:1404 */       throw new StringIndexOutOfBoundsException("Invalid offset: " + offset);
/*  768:     */     }
/*  769:1406 */     if ((length < 0) || (offset + length > chars.length)) {
/*  770:1407 */       throw new StringIndexOutOfBoundsException("Invalid length: " + length);
/*  771:     */     }
/*  772:1409 */     if (length > 0)
/*  773:     */     {
/*  774:1410 */       ensureCapacity(this.size + length);
/*  775:1411 */       System.arraycopy(this.buffer, index, this.buffer, index + length, this.size - index);
/*  776:1412 */       System.arraycopy(chars, offset, this.buffer, index, length);
/*  777:1413 */       this.size += length;
/*  778:     */     }
/*  779:1415 */     return this;
/*  780:     */   }
/*  781:     */   
/*  782:     */   public StrBuilder insert(int index, boolean value)
/*  783:     */   {
/*  784:1427 */     validateIndex(index);
/*  785:1428 */     if (value)
/*  786:     */     {
/*  787:1429 */       ensureCapacity(this.size + 4);
/*  788:1430 */       System.arraycopy(this.buffer, index, this.buffer, index + 4, this.size - index);
/*  789:1431 */       this.buffer[(index++)] = 't';
/*  790:1432 */       this.buffer[(index++)] = 'r';
/*  791:1433 */       this.buffer[(index++)] = 'u';
/*  792:1434 */       this.buffer[index] = 'e';
/*  793:1435 */       this.size += 4;
/*  794:     */     }
/*  795:     */     else
/*  796:     */     {
/*  797:1437 */       ensureCapacity(this.size + 5);
/*  798:1438 */       System.arraycopy(this.buffer, index, this.buffer, index + 5, this.size - index);
/*  799:1439 */       this.buffer[(index++)] = 'f';
/*  800:1440 */       this.buffer[(index++)] = 'a';
/*  801:1441 */       this.buffer[(index++)] = 'l';
/*  802:1442 */       this.buffer[(index++)] = 's';
/*  803:1443 */       this.buffer[index] = 'e';
/*  804:1444 */       this.size += 5;
/*  805:     */     }
/*  806:1446 */     return this;
/*  807:     */   }
/*  808:     */   
/*  809:     */   public StrBuilder insert(int index, char value)
/*  810:     */   {
/*  811:1458 */     validateIndex(index);
/*  812:1459 */     ensureCapacity(this.size + 1);
/*  813:1460 */     System.arraycopy(this.buffer, index, this.buffer, index + 1, this.size - index);
/*  814:1461 */     this.buffer[index] = value;
/*  815:1462 */     this.size += 1;
/*  816:1463 */     return this;
/*  817:     */   }
/*  818:     */   
/*  819:     */   public StrBuilder insert(int index, int value)
/*  820:     */   {
/*  821:1475 */     return insert(index, String.valueOf(value));
/*  822:     */   }
/*  823:     */   
/*  824:     */   public StrBuilder insert(int index, long value)
/*  825:     */   {
/*  826:1487 */     return insert(index, String.valueOf(value));
/*  827:     */   }
/*  828:     */   
/*  829:     */   public StrBuilder insert(int index, float value)
/*  830:     */   {
/*  831:1499 */     return insert(index, String.valueOf(value));
/*  832:     */   }
/*  833:     */   
/*  834:     */   public StrBuilder insert(int index, double value)
/*  835:     */   {
/*  836:1511 */     return insert(index, String.valueOf(value));
/*  837:     */   }
/*  838:     */   
/*  839:     */   private void deleteImpl(int startIndex, int endIndex, int len)
/*  840:     */   {
/*  841:1524 */     System.arraycopy(this.buffer, endIndex, this.buffer, startIndex, this.size - endIndex);
/*  842:1525 */     this.size -= len;
/*  843:     */   }
/*  844:     */   
/*  845:     */   public StrBuilder delete(int startIndex, int endIndex)
/*  846:     */   {
/*  847:1538 */     endIndex = validateRange(startIndex, endIndex);
/*  848:1539 */     int len = endIndex - startIndex;
/*  849:1540 */     if (len > 0) {
/*  850:1541 */       deleteImpl(startIndex, endIndex, len);
/*  851:     */     }
/*  852:1543 */     return this;
/*  853:     */   }
/*  854:     */   
/*  855:     */   public StrBuilder deleteAll(char ch)
/*  856:     */   {
/*  857:1554 */     for (int i = 0; i < this.size; i++) {
/*  858:1555 */       if (this.buffer[i] == ch)
/*  859:     */       {
/*  860:1556 */         int start = i;
/*  861:     */         for (;;)
/*  862:     */         {
/*  863:1557 */           i++;
/*  864:1557 */           if (i < this.size) {
/*  865:1558 */             if (this.buffer[i] != ch) {
/*  866:     */               break;
/*  867:     */             }
/*  868:     */           }
/*  869:     */         }
/*  870:1562 */         int len = i - start;
/*  871:1563 */         deleteImpl(start, i, len);
/*  872:1564 */         i -= len;
/*  873:     */       }
/*  874:     */     }
/*  875:1567 */     return this;
/*  876:     */   }
/*  877:     */   
/*  878:     */   public StrBuilder deleteFirst(char ch)
/*  879:     */   {
/*  880:1577 */     for (int i = 0; i < this.size; i++) {
/*  881:1578 */       if (this.buffer[i] == ch)
/*  882:     */       {
/*  883:1579 */         deleteImpl(i, i + 1, 1);
/*  884:1580 */         break;
/*  885:     */       }
/*  886:     */     }
/*  887:1583 */     return this;
/*  888:     */   }
/*  889:     */   
/*  890:     */   public StrBuilder deleteAll(String str)
/*  891:     */   {
/*  892:1594 */     int len = str == null ? 0 : str.length();
/*  893:1595 */     if (len > 0)
/*  894:     */     {
/*  895:1596 */       int index = indexOf(str, 0);
/*  896:1597 */       while (index >= 0)
/*  897:     */       {
/*  898:1598 */         deleteImpl(index, index + len, len);
/*  899:1599 */         index = indexOf(str, index);
/*  900:     */       }
/*  901:     */     }
/*  902:1602 */     return this;
/*  903:     */   }
/*  904:     */   
/*  905:     */   public StrBuilder deleteFirst(String str)
/*  906:     */   {
/*  907:1612 */     int len = str == null ? 0 : str.length();
/*  908:1613 */     if (len > 0)
/*  909:     */     {
/*  910:1614 */       int index = indexOf(str, 0);
/*  911:1615 */       if (index >= 0) {
/*  912:1616 */         deleteImpl(index, index + len, len);
/*  913:     */       }
/*  914:     */     }
/*  915:1619 */     return this;
/*  916:     */   }
/*  917:     */   
/*  918:     */   public StrBuilder deleteAll(StrMatcher matcher)
/*  919:     */   {
/*  920:1634 */     return replace(matcher, null, 0, this.size, -1);
/*  921:     */   }
/*  922:     */   
/*  923:     */   public StrBuilder deleteFirst(StrMatcher matcher)
/*  924:     */   {
/*  925:1648 */     return replace(matcher, null, 0, this.size, 1);
/*  926:     */   }
/*  927:     */   
/*  928:     */   private void replaceImpl(int startIndex, int endIndex, int removeLen, String insertStr, int insertLen)
/*  929:     */   {
/*  930:1663 */     int newSize = this.size - removeLen + insertLen;
/*  931:1664 */     if (insertLen != removeLen)
/*  932:     */     {
/*  933:1665 */       ensureCapacity(newSize);
/*  934:1666 */       System.arraycopy(this.buffer, endIndex, this.buffer, startIndex + insertLen, this.size - endIndex);
/*  935:1667 */       this.size = newSize;
/*  936:     */     }
/*  937:1669 */     if (insertLen > 0) {
/*  938:1670 */       insertStr.getChars(0, insertLen, this.buffer, startIndex);
/*  939:     */     }
/*  940:     */   }
/*  941:     */   
/*  942:     */   public StrBuilder replace(int startIndex, int endIndex, String replaceStr)
/*  943:     */   {
/*  944:1686 */     endIndex = validateRange(startIndex, endIndex);
/*  945:1687 */     int insertLen = replaceStr == null ? 0 : replaceStr.length();
/*  946:1688 */     replaceImpl(startIndex, endIndex, endIndex - startIndex, replaceStr, insertLen);
/*  947:1689 */     return this;
/*  948:     */   }
/*  949:     */   
/*  950:     */   public StrBuilder replaceAll(char search, char replace)
/*  951:     */   {
/*  952:1702 */     if (search != replace) {
/*  953:1703 */       for (int i = 0; i < this.size; i++) {
/*  954:1704 */         if (this.buffer[i] == search) {
/*  955:1705 */           this.buffer[i] = replace;
/*  956:     */         }
/*  957:     */       }
/*  958:     */     }
/*  959:1709 */     return this;
/*  960:     */   }
/*  961:     */   
/*  962:     */   public StrBuilder replaceFirst(char search, char replace)
/*  963:     */   {
/*  964:1721 */     if (search != replace) {
/*  965:1722 */       for (int i = 0; i < this.size; i++) {
/*  966:1723 */         if (this.buffer[i] == search)
/*  967:     */         {
/*  968:1724 */           this.buffer[i] = replace;
/*  969:1725 */           break;
/*  970:     */         }
/*  971:     */       }
/*  972:     */     }
/*  973:1729 */     return this;
/*  974:     */   }
/*  975:     */   
/*  976:     */   public StrBuilder replaceAll(String searchStr, String replaceStr)
/*  977:     */   {
/*  978:1741 */     int searchLen = searchStr == null ? 0 : searchStr.length();
/*  979:1742 */     if (searchLen > 0)
/*  980:     */     {
/*  981:1743 */       int replaceLen = replaceStr == null ? 0 : replaceStr.length();
/*  982:1744 */       int index = indexOf(searchStr, 0);
/*  983:1745 */       while (index >= 0)
/*  984:     */       {
/*  985:1746 */         replaceImpl(index, index + searchLen, searchLen, replaceStr, replaceLen);
/*  986:1747 */         index = indexOf(searchStr, index + replaceLen);
/*  987:     */       }
/*  988:     */     }
/*  989:1750 */     return this;
/*  990:     */   }
/*  991:     */   
/*  992:     */   public StrBuilder replaceFirst(String searchStr, String replaceStr)
/*  993:     */   {
/*  994:1761 */     int searchLen = searchStr == null ? 0 : searchStr.length();
/*  995:1762 */     if (searchLen > 0)
/*  996:     */     {
/*  997:1763 */       int index = indexOf(searchStr, 0);
/*  998:1764 */       if (index >= 0)
/*  999:     */       {
/* 1000:1765 */         int replaceLen = replaceStr == null ? 0 : replaceStr.length();
/* 1001:1766 */         replaceImpl(index, index + searchLen, searchLen, replaceStr, replaceLen);
/* 1002:     */       }
/* 1003:     */     }
/* 1004:1769 */     return this;
/* 1005:     */   }
/* 1006:     */   
/* 1007:     */   public StrBuilder replaceAll(StrMatcher matcher, String replaceStr)
/* 1008:     */   {
/* 1009:1785 */     return replace(matcher, replaceStr, 0, this.size, -1);
/* 1010:     */   }
/* 1011:     */   
/* 1012:     */   public StrBuilder replaceFirst(StrMatcher matcher, String replaceStr)
/* 1013:     */   {
/* 1014:1800 */     return replace(matcher, replaceStr, 0, this.size, 1);
/* 1015:     */   }
/* 1016:     */   
/* 1017:     */   public StrBuilder replace(StrMatcher matcher, String replaceStr, int startIndex, int endIndex, int replaceCount)
/* 1018:     */   {
/* 1019:1823 */     endIndex = validateRange(startIndex, endIndex);
/* 1020:1824 */     return replaceImpl(matcher, replaceStr, startIndex, endIndex, replaceCount);
/* 1021:     */   }
/* 1022:     */   
/* 1023:     */   private StrBuilder replaceImpl(StrMatcher matcher, String replaceStr, int from, int to, int replaceCount)
/* 1024:     */   {
/* 1025:1845 */     if ((matcher == null) || (this.size == 0)) {
/* 1026:1846 */       return this;
/* 1027:     */     }
/* 1028:1848 */     int replaceLen = replaceStr == null ? 0 : replaceStr.length();
/* 1029:1849 */     char[] buf = this.buffer;
/* 1030:1850 */     for (int i = from; (i < to) && (replaceCount != 0); i++)
/* 1031:     */     {
/* 1032:1851 */       int removeLen = matcher.isMatch(buf, i, from, to);
/* 1033:1852 */       if (removeLen > 0)
/* 1034:     */       {
/* 1035:1853 */         replaceImpl(i, i + removeLen, removeLen, replaceStr, replaceLen);
/* 1036:1854 */         to = to - removeLen + replaceLen;
/* 1037:1855 */         i = i + replaceLen - 1;
/* 1038:1856 */         if (replaceCount > 0) {
/* 1039:1857 */           replaceCount--;
/* 1040:     */         }
/* 1041:     */       }
/* 1042:     */     }
/* 1043:1861 */     return this;
/* 1044:     */   }
/* 1045:     */   
/* 1046:     */   public StrBuilder reverse()
/* 1047:     */   {
/* 1048:1871 */     if (this.size == 0) {
/* 1049:1872 */       return this;
/* 1050:     */     }
/* 1051:1875 */     int half = this.size / 2;
/* 1052:1876 */     char[] buf = this.buffer;
/* 1053:1877 */     int leftIdx = 0;
/* 1054:1877 */     for (int rightIdx = this.size - 1; leftIdx < half; rightIdx--)
/* 1055:     */     {
/* 1056:1878 */       char swap = buf[leftIdx];
/* 1057:1879 */       buf[leftIdx] = buf[rightIdx];
/* 1058:1880 */       buf[rightIdx] = swap;leftIdx++;
/* 1059:     */     }
/* 1060:1882 */     return this;
/* 1061:     */   }
/* 1062:     */   
/* 1063:     */   public StrBuilder trim()
/* 1064:     */   {
/* 1065:1893 */     if (this.size == 0) {
/* 1066:1894 */       return this;
/* 1067:     */     }
/* 1068:1896 */     int len = this.size;
/* 1069:1897 */     char[] buf = this.buffer;
/* 1070:1898 */     int pos = 0;
/* 1071:1899 */     while ((pos < len) && (buf[pos] <= ' ')) {
/* 1072:1900 */       pos++;
/* 1073:     */     }
/* 1074:1902 */     while ((pos < len) && (buf[(len - 1)] <= ' ')) {
/* 1075:1903 */       len--;
/* 1076:     */     }
/* 1077:1905 */     if (len < this.size) {
/* 1078:1906 */       delete(len, this.size);
/* 1079:     */     }
/* 1080:1908 */     if (pos > 0) {
/* 1081:1909 */       delete(0, pos);
/* 1082:     */     }
/* 1083:1911 */     return this;
/* 1084:     */   }
/* 1085:     */   
/* 1086:     */   public boolean startsWith(String str)
/* 1087:     */   {
/* 1088:1924 */     if (str == null) {
/* 1089:1925 */       return false;
/* 1090:     */     }
/* 1091:1927 */     int len = str.length();
/* 1092:1928 */     if (len == 0) {
/* 1093:1929 */       return true;
/* 1094:     */     }
/* 1095:1931 */     if (len > this.size) {
/* 1096:1932 */       return false;
/* 1097:     */     }
/* 1098:1934 */     for (int i = 0; i < len; i++) {
/* 1099:1935 */       if (this.buffer[i] != str.charAt(i)) {
/* 1100:1936 */         return false;
/* 1101:     */       }
/* 1102:     */     }
/* 1103:1939 */     return true;
/* 1104:     */   }
/* 1105:     */   
/* 1106:     */   public boolean endsWith(String str)
/* 1107:     */   {
/* 1108:1951 */     if (str == null) {
/* 1109:1952 */       return false;
/* 1110:     */     }
/* 1111:1954 */     int len = str.length();
/* 1112:1955 */     if (len == 0) {
/* 1113:1956 */       return true;
/* 1114:     */     }
/* 1115:1958 */     if (len > this.size) {
/* 1116:1959 */       return false;
/* 1117:     */     }
/* 1118:1961 */     int pos = this.size - len;
/* 1119:1962 */     for (int i = 0; i < len; pos++)
/* 1120:     */     {
/* 1121:1963 */       if (this.buffer[pos] != str.charAt(i)) {
/* 1122:1964 */         return false;
/* 1123:     */       }
/* 1124:1962 */       i++;
/* 1125:     */     }
/* 1126:1967 */     return true;
/* 1127:     */   }
/* 1128:     */   
/* 1129:     */   public String substring(int start)
/* 1130:     */   {
/* 1131:1979 */     return substring(start, this.size);
/* 1132:     */   }
/* 1133:     */   
/* 1134:     */   public String substring(int startIndex, int endIndex)
/* 1135:     */   {
/* 1136:1996 */     endIndex = validateRange(startIndex, endIndex);
/* 1137:1997 */     return new String(this.buffer, startIndex, endIndex - startIndex);
/* 1138:     */   }
/* 1139:     */   
/* 1140:     */   public String leftString(int length)
/* 1141:     */   {
/* 1142:2013 */     if (length <= 0) {
/* 1143:2014 */       return "";
/* 1144:     */     }
/* 1145:2015 */     if (length >= this.size) {
/* 1146:2016 */       return new String(this.buffer, 0, this.size);
/* 1147:     */     }
/* 1148:2018 */     return new String(this.buffer, 0, length);
/* 1149:     */   }
/* 1150:     */   
/* 1151:     */   public String rightString(int length)
/* 1152:     */   {
/* 1153:2035 */     if (length <= 0) {
/* 1154:2036 */       return "";
/* 1155:     */     }
/* 1156:2037 */     if (length >= this.size) {
/* 1157:2038 */       return new String(this.buffer, 0, this.size);
/* 1158:     */     }
/* 1159:2040 */     return new String(this.buffer, this.size - length, length);
/* 1160:     */   }
/* 1161:     */   
/* 1162:     */   public String midString(int index, int length)
/* 1163:     */   {
/* 1164:2061 */     if (index < 0) {
/* 1165:2062 */       index = 0;
/* 1166:     */     }
/* 1167:2064 */     if ((length <= 0) || (index >= this.size)) {
/* 1168:2065 */       return "";
/* 1169:     */     }
/* 1170:2067 */     if (this.size <= index + length) {
/* 1171:2068 */       return new String(this.buffer, index, this.size - index);
/* 1172:     */     }
/* 1173:2070 */     return new String(this.buffer, index, length);
/* 1174:     */   }
/* 1175:     */   
/* 1176:     */   public boolean contains(char ch)
/* 1177:     */   {
/* 1178:2082 */     char[] thisBuf = this.buffer;
/* 1179:2083 */     for (int i = 0; i < this.size; i++) {
/* 1180:2084 */       if (thisBuf[i] == ch) {
/* 1181:2085 */         return true;
/* 1182:     */       }
/* 1183:     */     }
/* 1184:2088 */     return false;
/* 1185:     */   }
/* 1186:     */   
/* 1187:     */   public boolean contains(String str)
/* 1188:     */   {
/* 1189:2098 */     return indexOf(str, 0) >= 0;
/* 1190:     */   }
/* 1191:     */   
/* 1192:     */   public boolean contains(StrMatcher matcher)
/* 1193:     */   {
/* 1194:2113 */     return indexOf(matcher, 0) >= 0;
/* 1195:     */   }
/* 1196:     */   
/* 1197:     */   public int indexOf(char ch)
/* 1198:     */   {
/* 1199:2124 */     return indexOf(ch, 0);
/* 1200:     */   }
/* 1201:     */   
/* 1202:     */   public int indexOf(char ch, int startIndex)
/* 1203:     */   {
/* 1204:2135 */     startIndex = startIndex < 0 ? 0 : startIndex;
/* 1205:2136 */     if (startIndex >= this.size) {
/* 1206:2137 */       return -1;
/* 1207:     */     }
/* 1208:2139 */     char[] thisBuf = this.buffer;
/* 1209:2140 */     for (int i = startIndex; i < this.size; i++) {
/* 1210:2141 */       if (thisBuf[i] == ch) {
/* 1211:2142 */         return i;
/* 1212:     */       }
/* 1213:     */     }
/* 1214:2145 */     return -1;
/* 1215:     */   }
/* 1216:     */   
/* 1217:     */   public int indexOf(String str)
/* 1218:     */   {
/* 1219:2157 */     return indexOf(str, 0);
/* 1220:     */   }
/* 1221:     */   
/* 1222:     */   public int indexOf(String str, int startIndex)
/* 1223:     */   {
/* 1224:2171 */     startIndex = startIndex < 0 ? 0 : startIndex;
/* 1225:2172 */     if ((str == null) || (startIndex >= this.size)) {
/* 1226:2173 */       return -1;
/* 1227:     */     }
/* 1228:2175 */     int strLen = str.length();
/* 1229:2176 */     if (strLen == 1) {
/* 1230:2177 */       return indexOf(str.charAt(0), startIndex);
/* 1231:     */     }
/* 1232:2179 */     if (strLen == 0) {
/* 1233:2180 */       return startIndex;
/* 1234:     */     }
/* 1235:2182 */     if (strLen > this.size) {
/* 1236:2183 */       return -1;
/* 1237:     */     }
/* 1238:2185 */     char[] thisBuf = this.buffer;
/* 1239:2186 */     int len = this.size - strLen + 1;
/* 1240:     */     label125:
/* 1241:2188 */     for (int i = startIndex; i < len; i++)
/* 1242:     */     {
/* 1243:2189 */       for (int j = 0; j < strLen; j++) {
/* 1244:2190 */         if (str.charAt(j) != thisBuf[(i + j)]) {
/* 1245:     */           break label125;
/* 1246:     */         }
/* 1247:     */       }
/* 1248:2194 */       return i;
/* 1249:     */     }
/* 1250:2196 */     return -1;
/* 1251:     */   }
/* 1252:     */   
/* 1253:     */   public int indexOf(StrMatcher matcher)
/* 1254:     */   {
/* 1255:2210 */     return indexOf(matcher, 0);
/* 1256:     */   }
/* 1257:     */   
/* 1258:     */   public int indexOf(StrMatcher matcher, int startIndex)
/* 1259:     */   {
/* 1260:2226 */     startIndex = startIndex < 0 ? 0 : startIndex;
/* 1261:2227 */     if ((matcher == null) || (startIndex >= this.size)) {
/* 1262:2228 */       return -1;
/* 1263:     */     }
/* 1264:2230 */     int len = this.size;
/* 1265:2231 */     char[] buf = this.buffer;
/* 1266:2232 */     for (int i = startIndex; i < len; i++) {
/* 1267:2233 */       if (matcher.isMatch(buf, i, startIndex, len) > 0) {
/* 1268:2234 */         return i;
/* 1269:     */       }
/* 1270:     */     }
/* 1271:2237 */     return -1;
/* 1272:     */   }
/* 1273:     */   
/* 1274:     */   public int lastIndexOf(char ch)
/* 1275:     */   {
/* 1276:2248 */     return lastIndexOf(ch, this.size - 1);
/* 1277:     */   }
/* 1278:     */   
/* 1279:     */   public int lastIndexOf(char ch, int startIndex)
/* 1280:     */   {
/* 1281:2259 */     startIndex = startIndex >= this.size ? this.size - 1 : startIndex;
/* 1282:2260 */     if (startIndex < 0) {
/* 1283:2261 */       return -1;
/* 1284:     */     }
/* 1285:2263 */     for (int i = startIndex; i >= 0; i--) {
/* 1286:2264 */       if (this.buffer[i] == ch) {
/* 1287:2265 */         return i;
/* 1288:     */       }
/* 1289:     */     }
/* 1290:2268 */     return -1;
/* 1291:     */   }
/* 1292:     */   
/* 1293:     */   public int lastIndexOf(String str)
/* 1294:     */   {
/* 1295:2280 */     return lastIndexOf(str, this.size - 1);
/* 1296:     */   }
/* 1297:     */   
/* 1298:     */   public int lastIndexOf(String str, int startIndex)
/* 1299:     */   {
/* 1300:2294 */     startIndex = startIndex >= this.size ? this.size - 1 : startIndex;
/* 1301:2295 */     if ((str == null) || (startIndex < 0)) {
/* 1302:2296 */       return -1;
/* 1303:     */     }
/* 1304:2298 */     int strLen = str.length();
/* 1305:2299 */     if ((strLen > 0) && (strLen <= this.size))
/* 1306:     */     {
/* 1307:2300 */       if (strLen == 1) {
/* 1308:2301 */         return lastIndexOf(str.charAt(0), startIndex);
/* 1309:     */       }
/* 1310:     */       label114:
/* 1311:2305 */       for (int i = startIndex - strLen + 1; i >= 0; i--)
/* 1312:     */       {
/* 1313:2306 */         for (int j = 0; j < strLen; j++) {
/* 1314:2307 */           if (str.charAt(j) != this.buffer[(i + j)]) {
/* 1315:     */             break label114;
/* 1316:     */           }
/* 1317:     */         }
/* 1318:2311 */         return i;
/* 1319:     */       }
/* 1320:     */     }
/* 1321:2314 */     else if (strLen == 0)
/* 1322:     */     {
/* 1323:2315 */       return startIndex;
/* 1324:     */     }
/* 1325:2317 */     return -1;
/* 1326:     */   }
/* 1327:     */   
/* 1328:     */   public int lastIndexOf(StrMatcher matcher)
/* 1329:     */   {
/* 1330:2331 */     return lastIndexOf(matcher, this.size);
/* 1331:     */   }
/* 1332:     */   
/* 1333:     */   public int lastIndexOf(StrMatcher matcher, int startIndex)
/* 1334:     */   {
/* 1335:2347 */     startIndex = startIndex >= this.size ? this.size - 1 : startIndex;
/* 1336:2348 */     if ((matcher == null) || (startIndex < 0)) {
/* 1337:2349 */       return -1;
/* 1338:     */     }
/* 1339:2351 */     char[] buf = this.buffer;
/* 1340:2352 */     int endIndex = startIndex + 1;
/* 1341:2353 */     for (int i = startIndex; i >= 0; i--) {
/* 1342:2354 */       if (matcher.isMatch(buf, i, 0, endIndex) > 0) {
/* 1343:2355 */         return i;
/* 1344:     */       }
/* 1345:     */     }
/* 1346:2358 */     return -1;
/* 1347:     */   }
/* 1348:     */   
/* 1349:     */   public StrTokenizer asTokenizer()
/* 1350:     */   {
/* 1351:2395 */     return new StrBuilderTokenizer();
/* 1352:     */   }
/* 1353:     */   
/* 1354:     */   public Reader asReader()
/* 1355:     */   {
/* 1356:2419 */     return new StrBuilderReader();
/* 1357:     */   }
/* 1358:     */   
/* 1359:     */   public Writer asWriter()
/* 1360:     */   {
/* 1361:2444 */     return new StrBuilderWriter();
/* 1362:     */   }
/* 1363:     */   
/* 1364:     */   public boolean equalsIgnoreCase(StrBuilder other)
/* 1365:     */   {
/* 1366:2486 */     if (this == other) {
/* 1367:2487 */       return true;
/* 1368:     */     }
/* 1369:2489 */     if (this.size != other.size) {
/* 1370:2490 */       return false;
/* 1371:     */     }
/* 1372:2492 */     char[] thisBuf = this.buffer;
/* 1373:2493 */     char[] otherBuf = other.buffer;
/* 1374:2494 */     for (int i = this.size - 1; i >= 0; i--)
/* 1375:     */     {
/* 1376:2495 */       char c1 = thisBuf[i];
/* 1377:2496 */       char c2 = otherBuf[i];
/* 1378:2497 */       if ((c1 != c2) && (Character.toUpperCase(c1) != Character.toUpperCase(c2))) {
/* 1379:2498 */         return false;
/* 1380:     */       }
/* 1381:     */     }
/* 1382:2501 */     return true;
/* 1383:     */   }
/* 1384:     */   
/* 1385:     */   public boolean equals(StrBuilder other)
/* 1386:     */   {
/* 1387:2512 */     if (this == other) {
/* 1388:2513 */       return true;
/* 1389:     */     }
/* 1390:2515 */     if (this.size != other.size) {
/* 1391:2516 */       return false;
/* 1392:     */     }
/* 1393:2518 */     char[] thisBuf = this.buffer;
/* 1394:2519 */     char[] otherBuf = other.buffer;
/* 1395:2520 */     for (int i = this.size - 1; i >= 0; i--) {
/* 1396:2521 */       if (thisBuf[i] != otherBuf[i]) {
/* 1397:2522 */         return false;
/* 1398:     */       }
/* 1399:     */     }
/* 1400:2525 */     return true;
/* 1401:     */   }
/* 1402:     */   
/* 1403:     */   public boolean equals(Object obj)
/* 1404:     */   {
/* 1405:2536 */     if ((obj instanceof StrBuilder)) {
/* 1406:2537 */       return equals((StrBuilder)obj);
/* 1407:     */     }
/* 1408:2539 */     return false;
/* 1409:     */   }
/* 1410:     */   
/* 1411:     */   public int hashCode()
/* 1412:     */   {
/* 1413:2548 */     char[] buf = this.buffer;
/* 1414:2549 */     int hash = 0;
/* 1415:2550 */     for (int i = this.size - 1; i >= 0; i--) {
/* 1416:2551 */       hash = 31 * hash + buf[i];
/* 1417:     */     }
/* 1418:2553 */     return hash;
/* 1419:     */   }
/* 1420:     */   
/* 1421:     */   public String toString()
/* 1422:     */   {
/* 1423:2567 */     return new String(this.buffer, 0, this.size);
/* 1424:     */   }
/* 1425:     */   
/* 1426:     */   public StringBuffer toStringBuffer()
/* 1427:     */   {
/* 1428:2577 */     return new StringBuffer(this.size).append(this.buffer, 0, this.size);
/* 1429:     */   }
/* 1430:     */   
/* 1431:     */   public Object clone()
/* 1432:     */     throws CloneNotSupportedException
/* 1433:     */   {
/* 1434:2588 */     StrBuilder clone = (StrBuilder)super.clone();
/* 1435:2589 */     clone.buffer = new char[this.buffer.length];
/* 1436:2590 */     System.arraycopy(this.buffer, 0, clone.buffer, 0, this.buffer.length);
/* 1437:2591 */     return clone;
/* 1438:     */   }
/* 1439:     */   
/* 1440:     */   protected int validateRange(int startIndex, int endIndex)
/* 1441:     */   {
/* 1442:2605 */     if (startIndex < 0) {
/* 1443:2606 */       throw new StringIndexOutOfBoundsException(startIndex);
/* 1444:     */     }
/* 1445:2608 */     if (endIndex > this.size) {
/* 1446:2609 */       endIndex = this.size;
/* 1447:     */     }
/* 1448:2611 */     if (startIndex > endIndex) {
/* 1449:2612 */       throw new StringIndexOutOfBoundsException("end < start");
/* 1450:     */     }
/* 1451:2614 */     return endIndex;
/* 1452:     */   }
/* 1453:     */   
/* 1454:     */   protected void validateIndex(int index)
/* 1455:     */   {
/* 1456:2624 */     if ((index < 0) || (index > this.size)) {
/* 1457:2625 */       throw new StringIndexOutOfBoundsException(index);
/* 1458:     */     }
/* 1459:     */   }
/* 1460:     */   
/* 1461:     */   class StrBuilderTokenizer
/* 1462:     */     extends StrTokenizer
/* 1463:     */   {
/* 1464:     */     StrBuilderTokenizer() {}
/* 1465:     */     
/* 1466:     */     protected List tokenize(char[] chars, int offset, int count)
/* 1467:     */     {
/* 1468:2644 */       if (chars == null) {
/* 1469:2645 */         return super.tokenize(StrBuilder.this.buffer, 0, StrBuilder.this.size());
/* 1470:     */       }
/* 1471:2647 */       return super.tokenize(chars, offset, count);
/* 1472:     */     }
/* 1473:     */     
/* 1474:     */     public String getContent()
/* 1475:     */     {
/* 1476:2653 */       String str = super.getContent();
/* 1477:2654 */       if (str == null) {
/* 1478:2655 */         return StrBuilder.this.toString();
/* 1479:     */       }
/* 1480:2657 */       return str;
/* 1481:     */     }
/* 1482:     */   }
/* 1483:     */   
/* 1484:     */   class StrBuilderReader
/* 1485:     */     extends Reader
/* 1486:     */   {
/* 1487:     */     private int pos;
/* 1488:     */     private int mark;
/* 1489:     */     
/* 1490:     */     StrBuilderReader() {}
/* 1491:     */     
/* 1492:     */     public void close() {}
/* 1493:     */     
/* 1494:     */     public int read()
/* 1495:     */     {
/* 1496:2686 */       if (!ready()) {
/* 1497:2687 */         return -1;
/* 1498:     */       }
/* 1499:2689 */       return StrBuilder.this.charAt(this.pos++);
/* 1500:     */     }
/* 1501:     */     
/* 1502:     */     public int read(char[] b, int off, int len)
/* 1503:     */     {
/* 1504:2694 */       if ((off < 0) || (len < 0) || (off > b.length) || (off + len > b.length) || (off + len < 0)) {
/* 1505:2696 */         throw new IndexOutOfBoundsException();
/* 1506:     */       }
/* 1507:2698 */       if (len == 0) {
/* 1508:2699 */         return 0;
/* 1509:     */       }
/* 1510:2701 */       if (this.pos >= StrBuilder.this.size()) {
/* 1511:2702 */         return -1;
/* 1512:     */       }
/* 1513:2704 */       if (this.pos + len > StrBuilder.this.size()) {
/* 1514:2705 */         len = StrBuilder.this.size() - this.pos;
/* 1515:     */       }
/* 1516:2707 */       StrBuilder.this.getChars(this.pos, this.pos + len, b, off);
/* 1517:2708 */       this.pos += len;
/* 1518:2709 */       return len;
/* 1519:     */     }
/* 1520:     */     
/* 1521:     */     public long skip(long n)
/* 1522:     */     {
/* 1523:2714 */       if (this.pos + n > StrBuilder.this.size()) {
/* 1524:2715 */         n = StrBuilder.this.size() - this.pos;
/* 1525:     */       }
/* 1526:2717 */       if (n < 0L) {
/* 1527:2718 */         return 0L;
/* 1528:     */       }
/* 1529:2720 */       this.pos = ((int)(this.pos + n));
/* 1530:2721 */       return n;
/* 1531:     */     }
/* 1532:     */     
/* 1533:     */     public boolean ready()
/* 1534:     */     {
/* 1535:2726 */       return this.pos < StrBuilder.this.size();
/* 1536:     */     }
/* 1537:     */     
/* 1538:     */     public boolean markSupported()
/* 1539:     */     {
/* 1540:2731 */       return true;
/* 1541:     */     }
/* 1542:     */     
/* 1543:     */     public void mark(int readAheadLimit)
/* 1544:     */     {
/* 1545:2736 */       this.mark = this.pos;
/* 1546:     */     }
/* 1547:     */     
/* 1548:     */     public void reset()
/* 1549:     */     {
/* 1550:2741 */       this.pos = this.mark;
/* 1551:     */     }
/* 1552:     */   }
/* 1553:     */   
/* 1554:     */   class StrBuilderWriter
/* 1555:     */     extends Writer
/* 1556:     */   {
/* 1557:     */     StrBuilderWriter() {}
/* 1558:     */     
/* 1559:     */     public void close() {}
/* 1560:     */     
/* 1561:     */     public void flush() {}
/* 1562:     */     
/* 1563:     */     public void write(int c)
/* 1564:     */     {
/* 1565:2770 */       StrBuilder.this.append((char)c);
/* 1566:     */     }
/* 1567:     */     
/* 1568:     */     public void write(char[] cbuf)
/* 1569:     */     {
/* 1570:2775 */       StrBuilder.this.append(cbuf);
/* 1571:     */     }
/* 1572:     */     
/* 1573:     */     public void write(char[] cbuf, int off, int len)
/* 1574:     */     {
/* 1575:2780 */       StrBuilder.this.append(cbuf, off, len);
/* 1576:     */     }
/* 1577:     */     
/* 1578:     */     public void write(String str)
/* 1579:     */     {
/* 1580:2785 */       StrBuilder.this.append(str);
/* 1581:     */     }
/* 1582:     */     
/* 1583:     */     public void write(String str, int off, int len)
/* 1584:     */     {
/* 1585:2790 */       StrBuilder.this.append(str, off, len);
/* 1586:     */     }
/* 1587:     */   }
/* 1588:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.text.StrBuilder
 * JD-Core Version:    0.7.0.1
 */