/*    1:     */ package org.apache.commons.lang.text;
/*    2:     */ 
/*    3:     */ import java.util.ArrayList;
/*    4:     */ import java.util.Collections;
/*    5:     */ import java.util.List;
/*    6:     */ import java.util.ListIterator;
/*    7:     */ import java.util.NoSuchElementException;
/*    8:     */ 
/*    9:     */ public class StrTokenizer
/*   10:     */   implements ListIterator, Cloneable
/*   11:     */ {
/*   12:  93 */   private static final StrTokenizer CSV_TOKENIZER_PROTOTYPE = new StrTokenizer();
/*   13:     */   private static final StrTokenizer TSV_TOKENIZER_PROTOTYPE;
/*   14:     */   private char[] chars;
/*   15:     */   private String[] tokens;
/*   16:     */   private int tokenPos;
/*   17:     */   
/*   18:     */   static
/*   19:     */   {
/*   20:  94 */     CSV_TOKENIZER_PROTOTYPE.setDelimiterMatcher(StrMatcher.commaMatcher());
/*   21:  95 */     CSV_TOKENIZER_PROTOTYPE.setQuoteMatcher(StrMatcher.doubleQuoteMatcher());
/*   22:  96 */     CSV_TOKENIZER_PROTOTYPE.setIgnoredMatcher(StrMatcher.noneMatcher());
/*   23:  97 */     CSV_TOKENIZER_PROTOTYPE.setTrimmerMatcher(StrMatcher.trimMatcher());
/*   24:  98 */     CSV_TOKENIZER_PROTOTYPE.setEmptyTokenAsNull(false);
/*   25:  99 */     CSV_TOKENIZER_PROTOTYPE.setIgnoreEmptyTokens(false);
/*   26:     */     
/*   27: 101 */     TSV_TOKENIZER_PROTOTYPE = new StrTokenizer();
/*   28: 102 */     TSV_TOKENIZER_PROTOTYPE.setDelimiterMatcher(StrMatcher.tabMatcher());
/*   29: 103 */     TSV_TOKENIZER_PROTOTYPE.setQuoteMatcher(StrMatcher.doubleQuoteMatcher());
/*   30: 104 */     TSV_TOKENIZER_PROTOTYPE.setIgnoredMatcher(StrMatcher.noneMatcher());
/*   31: 105 */     TSV_TOKENIZER_PROTOTYPE.setTrimmerMatcher(StrMatcher.trimMatcher());
/*   32: 106 */     TSV_TOKENIZER_PROTOTYPE.setEmptyTokenAsNull(false);
/*   33: 107 */     TSV_TOKENIZER_PROTOTYPE.setIgnoreEmptyTokens(false);
/*   34:     */   }
/*   35:     */   
/*   36: 118 */   private StrMatcher delimMatcher = StrMatcher.splitMatcher();
/*   37: 120 */   private StrMatcher quoteMatcher = StrMatcher.noneMatcher();
/*   38: 122 */   private StrMatcher ignoredMatcher = StrMatcher.noneMatcher();
/*   39: 124 */   private StrMatcher trimmerMatcher = StrMatcher.noneMatcher();
/*   40: 127 */   private boolean emptyAsNull = false;
/*   41: 129 */   private boolean ignoreEmptyTokens = true;
/*   42:     */   
/*   43:     */   private static StrTokenizer getCSVClone()
/*   44:     */   {
/*   45: 139 */     return (StrTokenizer)CSV_TOKENIZER_PROTOTYPE.clone();
/*   46:     */   }
/*   47:     */   
/*   48:     */   public static StrTokenizer getCSVInstance()
/*   49:     */   {
/*   50: 152 */     return getCSVClone();
/*   51:     */   }
/*   52:     */   
/*   53:     */   public static StrTokenizer getCSVInstance(String input)
/*   54:     */   {
/*   55: 165 */     StrTokenizer tok = getCSVClone();
/*   56: 166 */     tok.reset(input);
/*   57: 167 */     return tok;
/*   58:     */   }
/*   59:     */   
/*   60:     */   public static StrTokenizer getCSVInstance(char[] input)
/*   61:     */   {
/*   62: 180 */     StrTokenizer tok = getCSVClone();
/*   63: 181 */     tok.reset(input);
/*   64: 182 */     return tok;
/*   65:     */   }
/*   66:     */   
/*   67:     */   private static StrTokenizer getTSVClone()
/*   68:     */   {
/*   69: 191 */     return (StrTokenizer)TSV_TOKENIZER_PROTOTYPE.clone();
/*   70:     */   }
/*   71:     */   
/*   72:     */   public static StrTokenizer getTSVInstance()
/*   73:     */   {
/*   74: 204 */     return getTSVClone();
/*   75:     */   }
/*   76:     */   
/*   77:     */   public static StrTokenizer getTSVInstance(String input)
/*   78:     */   {
/*   79: 215 */     StrTokenizer tok = getTSVClone();
/*   80: 216 */     tok.reset(input);
/*   81: 217 */     return tok;
/*   82:     */   }
/*   83:     */   
/*   84:     */   public static StrTokenizer getTSVInstance(char[] input)
/*   85:     */   {
/*   86: 228 */     StrTokenizer tok = getTSVClone();
/*   87: 229 */     tok.reset(input);
/*   88: 230 */     return tok;
/*   89:     */   }
/*   90:     */   
/*   91:     */   public StrTokenizer()
/*   92:     */   {
/*   93: 242 */     this.chars = null;
/*   94:     */   }
/*   95:     */   
/*   96:     */   public StrTokenizer(String input)
/*   97:     */   {
/*   98: 253 */     if (input != null) {
/*   99: 254 */       this.chars = input.toCharArray();
/*  100:     */     } else {
/*  101: 256 */       this.chars = null;
/*  102:     */     }
/*  103:     */   }
/*  104:     */   
/*  105:     */   public StrTokenizer(String input, char delim)
/*  106:     */   {
/*  107: 267 */     this(input);
/*  108: 268 */     setDelimiterChar(delim);
/*  109:     */   }
/*  110:     */   
/*  111:     */   public StrTokenizer(String input, String delim)
/*  112:     */   {
/*  113: 278 */     this(input);
/*  114: 279 */     setDelimiterString(delim);
/*  115:     */   }
/*  116:     */   
/*  117:     */   public StrTokenizer(String input, StrMatcher delim)
/*  118:     */   {
/*  119: 289 */     this(input);
/*  120: 290 */     setDelimiterMatcher(delim);
/*  121:     */   }
/*  122:     */   
/*  123:     */   public StrTokenizer(String input, char delim, char quote)
/*  124:     */   {
/*  125: 302 */     this(input, delim);
/*  126: 303 */     setQuoteChar(quote);
/*  127:     */   }
/*  128:     */   
/*  129:     */   public StrTokenizer(String input, StrMatcher delim, StrMatcher quote)
/*  130:     */   {
/*  131: 315 */     this(input, delim);
/*  132: 316 */     setQuoteMatcher(quote);
/*  133:     */   }
/*  134:     */   
/*  135:     */   public StrTokenizer(char[] input)
/*  136:     */   {
/*  137: 330 */     this.chars = input;
/*  138:     */   }
/*  139:     */   
/*  140:     */   public StrTokenizer(char[] input, char delim)
/*  141:     */   {
/*  142: 343 */     this(input);
/*  143: 344 */     setDelimiterChar(delim);
/*  144:     */   }
/*  145:     */   
/*  146:     */   public StrTokenizer(char[] input, String delim)
/*  147:     */   {
/*  148: 357 */     this(input);
/*  149: 358 */     setDelimiterString(delim);
/*  150:     */   }
/*  151:     */   
/*  152:     */   public StrTokenizer(char[] input, StrMatcher delim)
/*  153:     */   {
/*  154: 371 */     this(input);
/*  155: 372 */     setDelimiterMatcher(delim);
/*  156:     */   }
/*  157:     */   
/*  158:     */   public StrTokenizer(char[] input, char delim, char quote)
/*  159:     */   {
/*  160: 387 */     this(input, delim);
/*  161: 388 */     setQuoteChar(quote);
/*  162:     */   }
/*  163:     */   
/*  164:     */   public StrTokenizer(char[] input, StrMatcher delim, StrMatcher quote)
/*  165:     */   {
/*  166: 403 */     this(input, delim);
/*  167: 404 */     setQuoteMatcher(quote);
/*  168:     */   }
/*  169:     */   
/*  170:     */   public int size()
/*  171:     */   {
/*  172: 415 */     checkTokenized();
/*  173: 416 */     return this.tokens.length;
/*  174:     */   }
/*  175:     */   
/*  176:     */   public String nextToken()
/*  177:     */   {
/*  178: 427 */     if (hasNext()) {
/*  179: 428 */       return this.tokens[(this.tokenPos++)];
/*  180:     */     }
/*  181: 430 */     return null;
/*  182:     */   }
/*  183:     */   
/*  184:     */   public String previousToken()
/*  185:     */   {
/*  186: 439 */     if (hasPrevious()) {
/*  187: 440 */       return this.tokens[(--this.tokenPos)];
/*  188:     */     }
/*  189: 442 */     return null;
/*  190:     */   }
/*  191:     */   
/*  192:     */   public String[] getTokenArray()
/*  193:     */   {
/*  194: 451 */     checkTokenized();
/*  195: 452 */     return (String[])this.tokens.clone();
/*  196:     */   }
/*  197:     */   
/*  198:     */   public List getTokenList()
/*  199:     */   {
/*  200: 461 */     checkTokenized();
/*  201: 462 */     List list = new ArrayList(this.tokens.length);
/*  202: 463 */     for (int i = 0; i < this.tokens.length; i++) {
/*  203: 464 */       list.add(this.tokens[i]);
/*  204:     */     }
/*  205: 466 */     return list;
/*  206:     */   }
/*  207:     */   
/*  208:     */   public StrTokenizer reset()
/*  209:     */   {
/*  210: 477 */     this.tokenPos = 0;
/*  211: 478 */     this.tokens = null;
/*  212: 479 */     return this;
/*  213:     */   }
/*  214:     */   
/*  215:     */   public StrTokenizer reset(String input)
/*  216:     */   {
/*  217: 491 */     reset();
/*  218: 492 */     if (input != null) {
/*  219: 493 */       this.chars = input.toCharArray();
/*  220:     */     } else {
/*  221: 495 */       this.chars = null;
/*  222:     */     }
/*  223: 497 */     return this;
/*  224:     */   }
/*  225:     */   
/*  226:     */   public StrTokenizer reset(char[] input)
/*  227:     */   {
/*  228: 512 */     reset();
/*  229: 513 */     this.chars = input;
/*  230: 514 */     return this;
/*  231:     */   }
/*  232:     */   
/*  233:     */   public boolean hasNext()
/*  234:     */   {
/*  235: 525 */     checkTokenized();
/*  236: 526 */     return this.tokenPos < this.tokens.length;
/*  237:     */   }
/*  238:     */   
/*  239:     */   public Object next()
/*  240:     */   {
/*  241: 536 */     if (hasNext()) {
/*  242: 537 */       return this.tokens[(this.tokenPos++)];
/*  243:     */     }
/*  244: 539 */     throw new NoSuchElementException();
/*  245:     */   }
/*  246:     */   
/*  247:     */   public int nextIndex()
/*  248:     */   {
/*  249: 548 */     return this.tokenPos;
/*  250:     */   }
/*  251:     */   
/*  252:     */   public boolean hasPrevious()
/*  253:     */   {
/*  254: 557 */     checkTokenized();
/*  255: 558 */     return this.tokenPos > 0;
/*  256:     */   }
/*  257:     */   
/*  258:     */   public Object previous()
/*  259:     */   {
/*  260: 567 */     if (hasPrevious()) {
/*  261: 568 */       return this.tokens[(--this.tokenPos)];
/*  262:     */     }
/*  263: 570 */     throw new NoSuchElementException();
/*  264:     */   }
/*  265:     */   
/*  266:     */   public int previousIndex()
/*  267:     */   {
/*  268: 579 */     return this.tokenPos - 1;
/*  269:     */   }
/*  270:     */   
/*  271:     */   public void remove()
/*  272:     */   {
/*  273: 588 */     throw new UnsupportedOperationException("remove() is unsupported");
/*  274:     */   }
/*  275:     */   
/*  276:     */   public void set(Object obj)
/*  277:     */   {
/*  278: 597 */     throw new UnsupportedOperationException("set() is unsupported");
/*  279:     */   }
/*  280:     */   
/*  281:     */   public void add(Object obj)
/*  282:     */   {
/*  283: 606 */     throw new UnsupportedOperationException("add() is unsupported");
/*  284:     */   }
/*  285:     */   
/*  286:     */   private void checkTokenized()
/*  287:     */   {
/*  288: 615 */     if (this.tokens == null) {
/*  289: 616 */       if (this.chars == null)
/*  290:     */       {
/*  291: 618 */         List split = tokenize(null, 0, 0);
/*  292: 619 */         this.tokens = ((String[])split.toArray(new String[split.size()]));
/*  293:     */       }
/*  294:     */       else
/*  295:     */       {
/*  296: 621 */         List split = tokenize(this.chars, 0, this.chars.length);
/*  297: 622 */         this.tokens = ((String[])split.toArray(new String[split.size()]));
/*  298:     */       }
/*  299:     */     }
/*  300:     */   }
/*  301:     */   
/*  302:     */   protected List tokenize(char[] chars, int offset, int count)
/*  303:     */   {
/*  304: 648 */     if ((chars == null) || (count == 0)) {
/*  305: 649 */       return Collections.EMPTY_LIST;
/*  306:     */     }
/*  307: 651 */     StrBuilder buf = new StrBuilder();
/*  308: 652 */     List tokens = new ArrayList();
/*  309: 653 */     int pos = offset;
/*  310: 656 */     while ((pos >= 0) && (pos < count))
/*  311:     */     {
/*  312: 658 */       pos = readNextToken(chars, pos, count, buf, tokens);
/*  313: 661 */       if (pos >= count) {
/*  314: 662 */         addToken(tokens, "");
/*  315:     */       }
/*  316:     */     }
/*  317: 665 */     return tokens;
/*  318:     */   }
/*  319:     */   
/*  320:     */   private void addToken(List list, String tok)
/*  321:     */   {
/*  322: 675 */     if ((tok == null) || (tok.length() == 0))
/*  323:     */     {
/*  324: 676 */       if (isIgnoreEmptyTokens()) {
/*  325: 677 */         return;
/*  326:     */       }
/*  327: 679 */       if (isEmptyTokenAsNull()) {
/*  328: 680 */         tok = null;
/*  329:     */       }
/*  330:     */     }
/*  331: 683 */     list.add(tok);
/*  332:     */   }
/*  333:     */   
/*  334:     */   private int readNextToken(char[] chars, int start, int len, StrBuilder workArea, List tokens)
/*  335:     */   {
/*  336: 700 */     while (start < len)
/*  337:     */     {
/*  338: 701 */       int removeLen = Math.max(getIgnoredMatcher().isMatch(chars, start, start, len), getTrimmerMatcher().isMatch(chars, start, start, len));
/*  339: 704 */       if ((removeLen == 0) || (getDelimiterMatcher().isMatch(chars, start, start, len) > 0) || (getQuoteMatcher().isMatch(chars, start, start, len) > 0)) {
/*  340:     */         break;
/*  341:     */       }
/*  342: 709 */       start += removeLen;
/*  343:     */     }
/*  344: 713 */     if (start >= len)
/*  345:     */     {
/*  346: 714 */       addToken(tokens, "");
/*  347: 715 */       return -1;
/*  348:     */     }
/*  349: 719 */     int delimLen = getDelimiterMatcher().isMatch(chars, start, start, len);
/*  350: 720 */     if (delimLen > 0)
/*  351:     */     {
/*  352: 721 */       addToken(tokens, "");
/*  353: 722 */       return start + delimLen;
/*  354:     */     }
/*  355: 726 */     int quoteLen = getQuoteMatcher().isMatch(chars, start, start, len);
/*  356: 727 */     if (quoteLen > 0) {
/*  357: 728 */       return readWithQuotes(chars, start + quoteLen, len, workArea, tokens, start, quoteLen);
/*  358:     */     }
/*  359: 730 */     return readWithQuotes(chars, start, len, workArea, tokens, 0, 0);
/*  360:     */   }
/*  361:     */   
/*  362:     */   private int readWithQuotes(char[] chars, int start, int len, StrBuilder workArea, List tokens, int quoteStart, int quoteLen)
/*  363:     */   {
/*  364: 752 */     workArea.clear();
/*  365: 753 */     int pos = start;
/*  366: 754 */     boolean quoting = quoteLen > 0;
/*  367: 755 */     int trimStart = 0;
/*  368: 757 */     while (pos < len) {
/*  369: 761 */       if (quoting)
/*  370:     */       {
/*  371: 768 */         if (isQuote(chars, pos, len, quoteStart, quoteLen))
/*  372:     */         {
/*  373: 769 */           if (isQuote(chars, pos + quoteLen, len, quoteStart, quoteLen))
/*  374:     */           {
/*  375: 771 */             workArea.append(chars, pos, quoteLen);
/*  376: 772 */             pos += quoteLen * 2;
/*  377: 773 */             trimStart = workArea.size();
/*  378:     */           }
/*  379:     */           else
/*  380:     */           {
/*  381: 778 */             quoting = false;
/*  382: 779 */             pos += quoteLen;
/*  383:     */           }
/*  384:     */         }
/*  385:     */         else
/*  386:     */         {
/*  387: 784 */           workArea.append(chars[(pos++)]);
/*  388: 785 */           trimStart = workArea.size();
/*  389:     */         }
/*  390:     */       }
/*  391:     */       else
/*  392:     */       {
/*  393: 791 */         int delimLen = getDelimiterMatcher().isMatch(chars, pos, start, len);
/*  394: 792 */         if (delimLen > 0)
/*  395:     */         {
/*  396: 794 */           addToken(tokens, workArea.substring(0, trimStart));
/*  397: 795 */           return pos + delimLen;
/*  398:     */         }
/*  399: 799 */         if ((quoteLen > 0) && 
/*  400: 800 */           (isQuote(chars, pos, len, quoteStart, quoteLen)))
/*  401:     */         {
/*  402: 801 */           quoting = true;
/*  403: 802 */           pos += quoteLen;
/*  404:     */         }
/*  405:     */         else
/*  406:     */         {
/*  407: 808 */           int ignoredLen = getIgnoredMatcher().isMatch(chars, pos, start, len);
/*  408: 809 */           if (ignoredLen > 0)
/*  409:     */           {
/*  410: 810 */             pos += ignoredLen;
/*  411:     */           }
/*  412:     */           else
/*  413:     */           {
/*  414: 817 */             int trimmedLen = getTrimmerMatcher().isMatch(chars, pos, start, len);
/*  415: 818 */             if (trimmedLen > 0)
/*  416:     */             {
/*  417: 819 */               workArea.append(chars, pos, trimmedLen);
/*  418: 820 */               pos += trimmedLen;
/*  419:     */             }
/*  420:     */             else
/*  421:     */             {
/*  422: 825 */               workArea.append(chars[(pos++)]);
/*  423: 826 */               trimStart = workArea.size();
/*  424:     */             }
/*  425:     */           }
/*  426:     */         }
/*  427:     */       }
/*  428:     */     }
/*  429: 831 */     addToken(tokens, workArea.substring(0, trimStart));
/*  430: 832 */     return -1;
/*  431:     */   }
/*  432:     */   
/*  433:     */   private boolean isQuote(char[] chars, int pos, int len, int quoteStart, int quoteLen)
/*  434:     */   {
/*  435: 847 */     for (int i = 0; i < quoteLen; i++) {
/*  436: 848 */       if ((pos + i >= len) || (chars[(pos + i)] != chars[(quoteStart + i)])) {
/*  437: 849 */         return false;
/*  438:     */       }
/*  439:     */     }
/*  440: 852 */     return true;
/*  441:     */   }
/*  442:     */   
/*  443:     */   public StrMatcher getDelimiterMatcher()
/*  444:     */   {
/*  445: 863 */     return this.delimMatcher;
/*  446:     */   }
/*  447:     */   
/*  448:     */   public StrTokenizer setDelimiterMatcher(StrMatcher delim)
/*  449:     */   {
/*  450: 875 */     if (delim == null) {
/*  451: 876 */       this.delimMatcher = StrMatcher.noneMatcher();
/*  452:     */     } else {
/*  453: 878 */       this.delimMatcher = delim;
/*  454:     */     }
/*  455: 880 */     return this;
/*  456:     */   }
/*  457:     */   
/*  458:     */   public StrTokenizer setDelimiterChar(char delim)
/*  459:     */   {
/*  460: 890 */     return setDelimiterMatcher(StrMatcher.charMatcher(delim));
/*  461:     */   }
/*  462:     */   
/*  463:     */   public StrTokenizer setDelimiterString(String delim)
/*  464:     */   {
/*  465: 900 */     return setDelimiterMatcher(StrMatcher.stringMatcher(delim));
/*  466:     */   }
/*  467:     */   
/*  468:     */   public StrMatcher getQuoteMatcher()
/*  469:     */   {
/*  470: 915 */     return this.quoteMatcher;
/*  471:     */   }
/*  472:     */   
/*  473:     */   public StrTokenizer setQuoteMatcher(StrMatcher quote)
/*  474:     */   {
/*  475: 928 */     if (quote != null) {
/*  476: 929 */       this.quoteMatcher = quote;
/*  477:     */     }
/*  478: 931 */     return this;
/*  479:     */   }
/*  480:     */   
/*  481:     */   public StrTokenizer setQuoteChar(char quote)
/*  482:     */   {
/*  483: 944 */     return setQuoteMatcher(StrMatcher.charMatcher(quote));
/*  484:     */   }
/*  485:     */   
/*  486:     */   public StrMatcher getIgnoredMatcher()
/*  487:     */   {
/*  488: 959 */     return this.ignoredMatcher;
/*  489:     */   }
/*  490:     */   
/*  491:     */   public StrTokenizer setIgnoredMatcher(StrMatcher ignored)
/*  492:     */   {
/*  493: 972 */     if (ignored != null) {
/*  494: 973 */       this.ignoredMatcher = ignored;
/*  495:     */     }
/*  496: 975 */     return this;
/*  497:     */   }
/*  498:     */   
/*  499:     */   public StrTokenizer setIgnoredChar(char ignored)
/*  500:     */   {
/*  501: 988 */     return setIgnoredMatcher(StrMatcher.charMatcher(ignored));
/*  502:     */   }
/*  503:     */   
/*  504:     */   public StrMatcher getTrimmerMatcher()
/*  505:     */   {
/*  506:1003 */     return this.trimmerMatcher;
/*  507:     */   }
/*  508:     */   
/*  509:     */   public StrTokenizer setTrimmerMatcher(StrMatcher trimmer)
/*  510:     */   {
/*  511:1016 */     if (trimmer != null) {
/*  512:1017 */       this.trimmerMatcher = trimmer;
/*  513:     */     }
/*  514:1019 */     return this;
/*  515:     */   }
/*  516:     */   
/*  517:     */   public boolean isEmptyTokenAsNull()
/*  518:     */   {
/*  519:1030 */     return this.emptyAsNull;
/*  520:     */   }
/*  521:     */   
/*  522:     */   public StrTokenizer setEmptyTokenAsNull(boolean emptyAsNull)
/*  523:     */   {
/*  524:1041 */     this.emptyAsNull = emptyAsNull;
/*  525:1042 */     return this;
/*  526:     */   }
/*  527:     */   
/*  528:     */   public boolean isIgnoreEmptyTokens()
/*  529:     */   {
/*  530:1053 */     return this.ignoreEmptyTokens;
/*  531:     */   }
/*  532:     */   
/*  533:     */   public StrTokenizer setIgnoreEmptyTokens(boolean ignoreEmptyTokens)
/*  534:     */   {
/*  535:1064 */     this.ignoreEmptyTokens = ignoreEmptyTokens;
/*  536:1065 */     return this;
/*  537:     */   }
/*  538:     */   
/*  539:     */   public String getContent()
/*  540:     */   {
/*  541:1075 */     if (this.chars == null) {
/*  542:1076 */       return null;
/*  543:     */     }
/*  544:1078 */     return new String(this.chars);
/*  545:     */   }
/*  546:     */   
/*  547:     */   public Object clone()
/*  548:     */   {
/*  549:     */     try
/*  550:     */     {
/*  551:1091 */       return cloneReset();
/*  552:     */     }
/*  553:     */     catch (CloneNotSupportedException ex) {}
/*  554:1093 */     return null;
/*  555:     */   }
/*  556:     */   
/*  557:     */   Object cloneReset()
/*  558:     */     throws CloneNotSupportedException
/*  559:     */   {
/*  560:1106 */     StrTokenizer cloned = (StrTokenizer)super.clone();
/*  561:1107 */     if (cloned.chars != null) {
/*  562:1108 */       cloned.chars = ((char[])cloned.chars.clone());
/*  563:     */     }
/*  564:1110 */     cloned.reset();
/*  565:1111 */     return cloned;
/*  566:     */   }
/*  567:     */   
/*  568:     */   public String toString()
/*  569:     */   {
/*  570:1121 */     if (this.tokens == null) {
/*  571:1122 */       return "StrTokenizer[not tokenized yet]";
/*  572:     */     }
/*  573:1124 */     return "StrTokenizer" + getTokenList();
/*  574:     */   }
/*  575:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.text.StrTokenizer
 * JD-Core Version:    0.7.0.1
 */