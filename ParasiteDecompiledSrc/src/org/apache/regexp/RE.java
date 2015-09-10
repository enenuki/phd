/*    1:     */ package org.apache.regexp;
/*    2:     */ 
/*    3:     */ import java.util.Vector;
/*    4:     */ 
/*    5:     */ public class RE
/*    6:     */ {
/*    7:     */   public static final int MATCH_NORMAL = 0;
/*    8:     */   public static final int MATCH_CASEINDEPENDENT = 1;
/*    9:     */   public static final int MATCH_MULTILINE = 2;
/*   10:     */   public static final int MATCH_SINGLELINE = 4;
/*   11:     */   static final char OP_END = 'E';
/*   12:     */   static final char OP_BOL = '^';
/*   13:     */   static final char OP_EOL = '$';
/*   14:     */   static final char OP_ANY = '.';
/*   15:     */   static final char OP_ANYOF = '[';
/*   16:     */   static final char OP_BRANCH = '|';
/*   17:     */   static final char OP_ATOM = 'A';
/*   18:     */   static final char OP_STAR = '*';
/*   19:     */   static final char OP_PLUS = '+';
/*   20:     */   static final char OP_MAYBE = '?';
/*   21:     */   static final char OP_ESCAPE = '\\';
/*   22:     */   static final char OP_OPEN = '(';
/*   23:     */   static final char OP_CLOSE = ')';
/*   24:     */   static final char OP_BACKREF = '#';
/*   25:     */   static final char OP_GOTO = 'G';
/*   26:     */   static final char OP_NOTHING = 'N';
/*   27:     */   static final char OP_RELUCTANTSTAR = '8';
/*   28:     */   static final char OP_RELUCTANTPLUS = '=';
/*   29:     */   static final char OP_RELUCTANTMAYBE = '/';
/*   30:     */   static final char OP_POSIXCLASS = 'P';
/*   31:     */   static final char E_ALNUM = 'w';
/*   32:     */   static final char E_NALNUM = 'W';
/*   33:     */   static final char E_BOUND = 'b';
/*   34:     */   static final char E_NBOUND = 'B';
/*   35:     */   static final char E_SPACE = 's';
/*   36:     */   static final char E_NSPACE = 'S';
/*   37:     */   static final char E_DIGIT = 'd';
/*   38:     */   static final char E_NDIGIT = 'D';
/*   39:     */   static final char POSIX_CLASS_ALNUM = 'w';
/*   40:     */   static final char POSIX_CLASS_ALPHA = 'a';
/*   41:     */   static final char POSIX_CLASS_BLANK = 'b';
/*   42:     */   static final char POSIX_CLASS_CNTRL = 'c';
/*   43:     */   static final char POSIX_CLASS_DIGIT = 'd';
/*   44:     */   static final char POSIX_CLASS_GRAPH = 'g';
/*   45:     */   static final char POSIX_CLASS_LOWER = 'l';
/*   46:     */   static final char POSIX_CLASS_PRINT = 'p';
/*   47:     */   static final char POSIX_CLASS_PUNCT = '!';
/*   48:     */   static final char POSIX_CLASS_SPACE = 's';
/*   49:     */   static final char POSIX_CLASS_UPPER = 'u';
/*   50:     */   static final char POSIX_CLASS_XDIGIT = 'x';
/*   51:     */   static final char POSIX_CLASS_JSTART = 'j';
/*   52:     */   static final char POSIX_CLASS_JPART = 'k';
/*   53:     */   static final int maxNode = 65536;
/*   54:     */   static final int maxParen = 16;
/*   55:     */   static final int offsetOpcode = 0;
/*   56:     */   static final int offsetOpdata = 1;
/*   57:     */   static final int offsetNext = 2;
/*   58:     */   static final int nodeSize = 3;
/*   59: 446 */   static final String NEWLINE = System.getProperty("line.separator");
/*   60:     */   REProgram program;
/*   61:     */   CharacterIterator search;
/*   62:     */   int idx;
/*   63:     */   int matchFlags;
/*   64:     */   int parenCount;
/*   65:     */   int start0;
/*   66:     */   int end0;
/*   67:     */   int start1;
/*   68:     */   int end1;
/*   69:     */   int start2;
/*   70:     */   int end2;
/*   71:     */   int[] startn;
/*   72:     */   int[] endn;
/*   73:     */   int[] startBackref;
/*   74:     */   int[] endBackref;
/*   75:     */   public static final int REPLACE_ALL = 0;
/*   76:     */   public static final int REPLACE_FIRSTONLY = 1;
/*   77:     */   
/*   78:     */   public RE(String paramString)
/*   79:     */     throws RESyntaxException
/*   80:     */   {
/*   81: 480 */     this(paramString, 0);
/*   82:     */   }
/*   83:     */   
/*   84:     */   public RE(String paramString, int paramInt)
/*   85:     */     throws RESyntaxException
/*   86:     */   {
/*   87: 495 */     this(new RECompiler().compile(paramString));
/*   88: 496 */     setMatchFlags(paramInt);
/*   89:     */   }
/*   90:     */   
/*   91:     */   public RE(REProgram paramREProgram, int paramInt)
/*   92:     */   {
/*   93: 520 */     setProgram(paramREProgram);
/*   94: 521 */     setMatchFlags(paramInt);
/*   95:     */   }
/*   96:     */   
/*   97:     */   public RE(REProgram paramREProgram)
/*   98:     */   {
/*   99: 533 */     this(paramREProgram, 0);
/*  100:     */   }
/*  101:     */   
/*  102:     */   public RE()
/*  103:     */   {
/*  104: 542 */     this(null, 0);
/*  105:     */   }
/*  106:     */   
/*  107:     */   public static String simplePatternToFullRegularExpression(String paramString)
/*  108:     */   {
/*  109: 552 */     StringBuffer localStringBuffer = new StringBuffer();
/*  110: 553 */     for (int i = 0; i < paramString.length(); i++)
/*  111:     */     {
/*  112: 555 */       char c = paramString.charAt(i);
/*  113: 556 */       switch (c)
/*  114:     */       {
/*  115:     */       case '*': 
/*  116: 559 */         localStringBuffer.append(".*");
/*  117: 560 */         break;
/*  118:     */       case '$': 
/*  119:     */       case '(': 
/*  120:     */       case ')': 
/*  121:     */       case '+': 
/*  122:     */       case '.': 
/*  123:     */       case '?': 
/*  124:     */       case '[': 
/*  125:     */       case '\\': 
/*  126:     */       case ']': 
/*  127:     */       case '^': 
/*  128:     */       case '{': 
/*  129:     */       case '|': 
/*  130:     */       case '}': 
/*  131: 575 */         localStringBuffer.append('\\');
/*  132:     */       default: 
/*  133: 577 */         localStringBuffer.append(c);
/*  134:     */       }
/*  135:     */     }
/*  136: 581 */     return localStringBuffer.toString();
/*  137:     */   }
/*  138:     */   
/*  139:     */   public void setMatchFlags(int paramInt)
/*  140:     */   {
/*  141: 599 */     this.matchFlags = paramInt;
/*  142:     */   }
/*  143:     */   
/*  144:     */   public int getMatchFlags()
/*  145:     */   {
/*  146: 619 */     return this.matchFlags;
/*  147:     */   }
/*  148:     */   
/*  149:     */   public void setProgram(REProgram paramREProgram)
/*  150:     */   {
/*  151: 631 */     this.program = paramREProgram;
/*  152:     */   }
/*  153:     */   
/*  154:     */   public REProgram getProgram()
/*  155:     */   {
/*  156: 641 */     return this.program;
/*  157:     */   }
/*  158:     */   
/*  159:     */   public int getParenCount()
/*  160:     */   {
/*  161: 650 */     return this.parenCount;
/*  162:     */   }
/*  163:     */   
/*  164:     */   public String getParen(int paramInt)
/*  165:     */   {
/*  166:     */     int i;
/*  167: 661 */     if ((paramInt < this.parenCount) && ((i = getParenStart(paramInt)) >= 0)) {
/*  168: 663 */       return this.search.substring(i, getParenEnd(paramInt));
/*  169:     */     }
/*  170: 665 */     return null;
/*  171:     */   }
/*  172:     */   
/*  173:     */   public final int getParenStart(int paramInt)
/*  174:     */   {
/*  175: 675 */     if (paramInt < this.parenCount)
/*  176:     */     {
/*  177: 677 */       switch (paramInt)
/*  178:     */       {
/*  179:     */       case 0: 
/*  180: 680 */         return this.start0;
/*  181:     */       case 1: 
/*  182: 683 */         return this.start1;
/*  183:     */       case 2: 
/*  184: 686 */         return this.start2;
/*  185:     */       }
/*  186: 689 */       if (this.startn == null) {
/*  187: 691 */         allocParens();
/*  188:     */       }
/*  189: 693 */       return this.startn[paramInt];
/*  190:     */     }
/*  191: 696 */     return -1;
/*  192:     */   }
/*  193:     */   
/*  194:     */   public final int getParenEnd(int paramInt)
/*  195:     */   {
/*  196: 706 */     if (paramInt < this.parenCount)
/*  197:     */     {
/*  198: 708 */       switch (paramInt)
/*  199:     */       {
/*  200:     */       case 0: 
/*  201: 711 */         return this.end0;
/*  202:     */       case 1: 
/*  203: 714 */         return this.end1;
/*  204:     */       case 2: 
/*  205: 717 */         return this.end2;
/*  206:     */       }
/*  207: 720 */       if (this.endn == null) {
/*  208: 722 */         allocParens();
/*  209:     */       }
/*  210: 724 */       return this.endn[paramInt];
/*  211:     */     }
/*  212: 727 */     return -1;
/*  213:     */   }
/*  214:     */   
/*  215:     */   public final int getParenLength(int paramInt)
/*  216:     */   {
/*  217: 737 */     if (paramInt < this.parenCount) {
/*  218: 739 */       return getParenEnd(paramInt) - getParenStart(paramInt);
/*  219:     */     }
/*  220: 741 */     return -1;
/*  221:     */   }
/*  222:     */   
/*  223:     */   protected final void setParenStart(int paramInt1, int paramInt2)
/*  224:     */   {
/*  225: 751 */     if (paramInt1 < this.parenCount) {
/*  226: 753 */       switch (paramInt1)
/*  227:     */       {
/*  228:     */       case 0: 
/*  229: 756 */         this.start0 = paramInt2;
/*  230: 757 */         break;
/*  231:     */       case 1: 
/*  232: 760 */         this.start1 = paramInt2;
/*  233: 761 */         break;
/*  234:     */       case 2: 
/*  235: 764 */         this.start2 = paramInt2;
/*  236: 765 */         break;
/*  237:     */       default: 
/*  238: 768 */         if (this.startn == null) {
/*  239: 770 */           allocParens();
/*  240:     */         }
/*  241: 772 */         this.startn[paramInt1] = paramInt2;
/*  242: 773 */         break;
/*  243:     */       }
/*  244:     */     }
/*  245:     */   }
/*  246:     */   
/*  247:     */   protected final void setParenEnd(int paramInt1, int paramInt2)
/*  248:     */   {
/*  249: 785 */     if (paramInt1 < this.parenCount) {
/*  250: 787 */       switch (paramInt1)
/*  251:     */       {
/*  252:     */       case 0: 
/*  253: 790 */         this.end0 = paramInt2;
/*  254: 791 */         break;
/*  255:     */       case 1: 
/*  256: 794 */         this.end1 = paramInt2;
/*  257: 795 */         break;
/*  258:     */       case 2: 
/*  259: 798 */         this.end2 = paramInt2;
/*  260: 799 */         break;
/*  261:     */       default: 
/*  262: 802 */         if (this.endn == null) {
/*  263: 804 */           allocParens();
/*  264:     */         }
/*  265: 806 */         this.endn[paramInt1] = paramInt2;
/*  266: 807 */         break;
/*  267:     */       }
/*  268:     */     }
/*  269:     */   }
/*  270:     */   
/*  271:     */   protected void internalError(String paramString)
/*  272:     */     throws Error
/*  273:     */   {
/*  274: 820 */     throw new Error("RE internal error: " + paramString);
/*  275:     */   }
/*  276:     */   
/*  277:     */   private final void allocParens()
/*  278:     */   {
/*  279: 829 */     this.startn = new int[16];
/*  280: 830 */     this.endn = new int[16];
/*  281: 833 */     for (int i = 0; i < 16; i++)
/*  282:     */     {
/*  283: 835 */       this.startn[i] = -1;
/*  284: 836 */       this.endn[i] = -1;
/*  285:     */     }
/*  286:     */   }
/*  287:     */   
/*  288:     */   protected int matchNodes(int paramInt1, int paramInt2, int paramInt3)
/*  289:     */   {
/*  290: 851 */     int i = paramInt3;
/*  291:     */     
/*  292:     */ 
/*  293:     */ 
/*  294:     */ 
/*  295: 856 */     char[] arrayOfChar = this.program.instruction;
/*  296: 857 */     for (int i1 = paramInt1; i1 < paramInt2;)
/*  297:     */     {
/*  298: 859 */       int k = arrayOfChar[i1];
/*  299: 860 */       int j = i1 + (short)arrayOfChar[(i1 + 2)];
/*  300: 861 */       int m = arrayOfChar[(i1 + 1)];
/*  301:     */       int i2;
/*  302:     */       int n;
/*  303:     */       int i6;
/*  304:     */       int i3;
/*  305:     */       int i4;
/*  306: 863 */       switch (k)
/*  307:     */       {
/*  308:     */       case 47: 
/*  309: 867 */         i2 = 0;
/*  310:     */         do
/*  311:     */         {
/*  312: 871 */           if ((n = matchNodes(j, 65536, i)) != -1) {
/*  313: 873 */             return n;
/*  314:     */           }
/*  315: 876 */         } while ((i2++ == 0) && ((i = matchNodes(i1 + 3, j, i)) != -1));
/*  316: 877 */         return -1;
/*  317:     */       case 61: 
/*  318:     */         do
/*  319:     */         {
/*  320: 884 */           if ((n = matchNodes(j, 65536, i)) != -1) {
/*  321: 886 */             return n;
/*  322:     */           }
/*  323: 881 */         } while ((i = matchNodes(i1 + 3, j, i)) != -1);
/*  324: 889 */         return -1;
/*  325:     */       case 56: 
/*  326:     */         do
/*  327:     */         {
/*  328: 895 */           if ((n = matchNodes(j, 65536, i)) != -1) {
/*  329: 897 */             return n;
/*  330:     */           }
/*  331: 900 */         } while ((i = matchNodes(i1 + 3, j, i)) != -1);
/*  332: 901 */         return -1;
/*  333:     */       case 40: 
/*  334: 906 */         if ((this.program.flags & 0x1) != 0) {
/*  335: 908 */           this.startBackref[m] = i;
/*  336:     */         }
/*  337: 910 */         if ((n = matchNodes(j, 65536, i)) != -1)
/*  338:     */         {
/*  339: 913 */           if (m + 1 > this.parenCount) {
/*  340: 915 */             this.parenCount = (m + 1);
/*  341:     */           }
/*  342: 919 */           if (getParenStart(m) == -1) {
/*  343: 921 */             setParenStart(m, i);
/*  344:     */           }
/*  345:     */         }
/*  346: 924 */         return n;
/*  347:     */       case 41: 
/*  348: 929 */         if ((this.program.flags & 0x1) != 0) {
/*  349: 931 */           this.endBackref[m] = i;
/*  350:     */         }
/*  351: 933 */         if ((n = matchNodes(j, 65536, i)) != -1)
/*  352:     */         {
/*  353: 936 */           if (m + 1 > this.parenCount) {
/*  354: 938 */             this.parenCount = (m + 1);
/*  355:     */           }
/*  356: 942 */           if (getParenEnd(m) == -1) {
/*  357: 944 */             setParenEnd(m, i);
/*  358:     */           }
/*  359:     */         }
/*  360: 947 */         return n;
/*  361:     */       case 35: 
/*  362: 952 */         i2 = this.startBackref[m];
/*  363: 953 */         int i5 = this.endBackref[m];
/*  364: 956 */         if ((i2 == -1) || (i5 == -1)) {
/*  365: 958 */           return -1;
/*  366:     */         }
/*  367: 962 */         if (i2 != i5)
/*  368:     */         {
/*  369: 968 */           int i7 = i5 - i2;
/*  370: 971 */           if (this.search.isEnd(i + i7 - 1)) {
/*  371: 973 */             return -1;
/*  372:     */           }
/*  373: 977 */           if ((this.matchFlags & 0x1) != 0) {
/*  374: 980 */             for (int i11 = 0; i11 < i7; i11++) {
/*  375: 982 */               if (Character.toLowerCase(this.search.charAt(i++)) != Character.toLowerCase(this.search.charAt(i2 + i11))) {
/*  376: 984 */                 return -1;
/*  377:     */               }
/*  378:     */             }
/*  379:     */           } else {
/*  380: 991 */             for (int i12 = 0; i12 < i7; i12++) {
/*  381: 993 */               if (this.search.charAt(i++) != this.search.charAt(i2 + i12)) {
/*  382: 995 */                 return -1;
/*  383:     */               }
/*  384:     */             }
/*  385:     */           }
/*  386:     */         }
/*  387:     */         break;
/*  388:     */       case 94: 
/*  389:1000 */         if ((goto 2199) && 
/*  390:     */         
/*  391:     */ 
/*  392:     */ 
/*  393:     */ 
/*  394:1005 */           (i != 0)) {
/*  395:1008 */           if ((this.matchFlags & 0x2) == 2)
/*  396:     */           {
/*  397:1011 */             if ((i <= 0) || (!isNewline(i - 1))) {
/*  398:1012 */               return -1;
/*  399:     */             }
/*  400:     */           }
/*  401:     */           else {
/*  402:1017 */             return -1;
/*  403:     */           }
/*  404:     */         }
/*  405:     */         break;
/*  406:     */       case 36: 
/*  407:1024 */         if ((!this.search.isEnd(0)) && (!this.search.isEnd(i))) {
/*  408:1027 */           if ((this.matchFlags & 0x2) == 2)
/*  409:     */           {
/*  410:1030 */             if (!isNewline(i)) {
/*  411:1031 */               return -1;
/*  412:     */             }
/*  413:     */           }
/*  414:     */           else {
/*  415:1036 */             return -1;
/*  416:     */           }
/*  417:     */         }
/*  418:     */         break;
/*  419:     */       case 92: 
/*  420:1043 */         switch (m)
/*  421:     */         {
/*  422:     */         case 66: 
/*  423:     */         case 98: 
/*  424:1049 */           i2 = i == getParenStart(0) ? '\n' : this.search.charAt(i - 1);
/*  425:1050 */           char c = this.search.isEnd(i) ? '\n' : this.search.charAt(i);
/*  426:1051 */           if ((Character.isLetterOrDigit(i2) != Character.isLetterOrDigit(c) ? 0 : 1) == (m != 98 ? 0 : 1)) {
/*  427:1053 */             return -1;
/*  428:     */           }
/*  429:     */           break;
/*  430:     */         case 68: 
/*  431:     */         case 83: 
/*  432:     */         case 87: 
/*  433:     */         case 100: 
/*  434:     */         case 115: 
/*  435:     */         case 119: 
/*  436:1067 */           if (this.search.isEnd(i)) {
/*  437:1069 */             return -1;
/*  438:     */           }
/*  439:1073 */           switch (m)
/*  440:     */           {
/*  441:     */           case 87: 
/*  442:     */           case 119: 
/*  443:1077 */             if (Character.isLetterOrDigit(this.search.charAt(i)) != (m == 119)) {
/*  444:1079 */               return -1;
/*  445:     */             }
/*  446:     */             break;
/*  447:     */           case 68: 
/*  448:     */           case 100: 
/*  449:1085 */             if (Character.isDigit(this.search.charAt(i)) != (m == 100)) {
/*  450:1087 */               return -1;
/*  451:     */             }
/*  452:     */             break;
/*  453:     */           case 83: 
/*  454:     */           case 115: 
/*  455:1093 */             if (Character.isWhitespace(this.search.charAt(i)) != (m == 115)) {
/*  456:1095 */               return -1;
/*  457:     */             }
/*  458:     */             break;
/*  459:     */           }
/*  460:1099 */           i++;
/*  461:1100 */           break;
/*  462:     */         default: 
/*  463:1103 */           internalError("Unrecognized escape '" + m + "'");
/*  464:     */         }
/*  465:1105 */         break;
/*  466:     */       case 46: 
/*  467:1109 */         if ((this.matchFlags & 0x4) == 4)
/*  468:     */         {
/*  469:1111 */           if (this.search.isEnd(i)) {
/*  470:1113 */             return -1;
/*  471:     */           }
/*  472:1115 */           i++;
/*  473:     */         }
/*  474:1121 */         else if ((this.search.isEnd(i)) || (this.search.charAt(i++) == '\n'))
/*  475:     */         {
/*  476:1123 */           return -1;
/*  477:     */         }
/*  478:     */         break;
/*  479:     */       case 65: 
/*  480:1131 */         if (this.search.isEnd(i)) {
/*  481:1133 */           return -1;
/*  482:     */         }
/*  483:1137 */         i2 = m;
/*  484:1138 */         i6 = i1 + 3;
/*  485:1141 */         if (this.search.isEnd(i2 + i - 1)) {
/*  486:1143 */           return -1;
/*  487:     */         }
/*  488:1147 */         if ((this.matchFlags & 0x1) != 0) {
/*  489:1149 */           for (int i8 = 0; i8 < i2; i8++) {
/*  490:1151 */             if (Character.toLowerCase(this.search.charAt(i++)) != Character.toLowerCase(arrayOfChar[(i6 + i8)])) {
/*  491:1153 */               return -1;
/*  492:     */             }
/*  493:     */           }
/*  494:     */         } else {
/*  495:1159 */           for (int i9 = 0; i9 < i2; i9++) {
/*  496:1161 */             if (this.search.charAt(i++) != arrayOfChar[(i6 + i9)]) {
/*  497:1163 */               return -1;
/*  498:     */             }
/*  499:     */           }
/*  500:     */         }
/*  501:1168 */         break;
/*  502:     */       case 80: 
/*  503:1173 */         if (this.search.isEnd(i)) {
/*  504:1175 */           return -1;
/*  505:     */         }
/*  506:1178 */         switch (m)
/*  507:     */         {
/*  508:     */         case 119: 
/*  509:1181 */           if (!Character.isLetterOrDigit(this.search.charAt(i))) {
/*  510:1183 */             return -1;
/*  511:     */           }
/*  512:     */           break;
/*  513:     */         case 97: 
/*  514:1188 */           if (!Character.isLetter(this.search.charAt(i))) {
/*  515:1190 */             return -1;
/*  516:     */           }
/*  517:     */           break;
/*  518:     */         case 100: 
/*  519:1195 */           if (!Character.isDigit(this.search.charAt(i))) {
/*  520:1197 */             return -1;
/*  521:     */           }
/*  522:     */           break;
/*  523:     */         case 98: 
/*  524:1202 */           if (!Character.isSpaceChar(this.search.charAt(i))) {
/*  525:1204 */             return -1;
/*  526:     */           }
/*  527:     */           break;
/*  528:     */         case 115: 
/*  529:1209 */           if (!Character.isWhitespace(this.search.charAt(i))) {
/*  530:1211 */             return -1;
/*  531:     */           }
/*  532:     */           break;
/*  533:     */         case 99: 
/*  534:1216 */           if (Character.getType(this.search.charAt(i)) != 15) {
/*  535:1218 */             return -1;
/*  536:     */           }
/*  537:     */           break;
/*  538:     */         case 103: 
/*  539:1223 */           switch (Character.getType(this.search.charAt(i)))
/*  540:     */           {
/*  541:     */           default: 
/*  542:1232 */             return -1;
/*  543:     */           }
/*  544:     */         case 108: 
/*  545:1237 */           if (Character.getType(this.search.charAt(i)) != 2) {
/*  546:1239 */             return -1;
/*  547:     */           }
/*  548:     */           break;
/*  549:     */         case 117: 
/*  550:1244 */           if (Character.getType(this.search.charAt(i)) != 1) {
/*  551:1246 */             return -1;
/*  552:     */           }
/*  553:     */           break;
/*  554:     */         case 112: 
/*  555:1251 */           if (Character.getType(this.search.charAt(i)) == 15) {
/*  556:1253 */             return -1;
/*  557:     */           }
/*  558:     */           break;
/*  559:     */         case 33: 
/*  560:1259 */           i3 = Character.getType(this.search.charAt(i));
/*  561:1260 */           switch (i3)
/*  562:     */           {
/*  563:     */           default: 
/*  564:1270 */             return -1;
/*  565:     */           }
/*  566:     */         case 120: 
/*  567:1277 */           i3 = ((this.search.charAt(i) < '0') || (this.search.charAt(i) > '9')) && 
/*  568:1278 */             ((this.search.charAt(i) < 'a') || (this.search.charAt(i) > 'f')) && (
/*  569:1279 */             (this.search.charAt(i) < 'A') || (this.search.charAt(i) > 'F')) ? 0 : 1;
/*  570:1280 */           if (i3 == 0) {
/*  571:1282 */             return -1;
/*  572:     */           }
/*  573:     */           break;
/*  574:     */         case 106: 
/*  575:1288 */           if (!Character.isJavaIdentifierStart(this.search.charAt(i))) {
/*  576:1290 */             return -1;
/*  577:     */           }
/*  578:     */           break;
/*  579:     */         case 107: 
/*  580:1295 */           if (!Character.isJavaIdentifierPart(this.search.charAt(i))) {
/*  581:1297 */             return -1;
/*  582:     */           }
/*  583:     */           break;
/*  584:     */         default: 
/*  585:1302 */           internalError("Bad posix class");
/*  586:     */         }
/*  587:1307 */         i++;
/*  588:     */         
/*  589:1309 */         break;
/*  590:     */       case 91: 
/*  591:1314 */         if (this.search.isEnd(i)) {
/*  592:1316 */           return -1;
/*  593:     */         }
/*  594:1320 */         i3 = this.search.charAt(i);
/*  595:1321 */         i6 = (this.matchFlags & 0x1) == 0 ? 0 : 1;
/*  596:1322 */         if (i6 != 0) {
/*  597:1324 */           i4 = Character.toLowerCase(i3);
/*  598:     */         }
/*  599:1328 */         int i10 = i1 + 3;
/*  600:1329 */         int i13 = i10 + m * 2;
/*  601:1330 */         int i14 = 0;
/*  602:1331 */         for (int i15 = i10; i15 < i13;)
/*  603:     */         {
/*  604:1334 */           int i16 = arrayOfChar[(i15++)];
/*  605:1335 */           int i17 = arrayOfChar[(i15++)];
/*  606:1338 */           if (i6 != 0)
/*  607:     */           {
/*  608:1340 */             i16 = Character.toLowerCase(i16);
/*  609:1341 */             i17 = Character.toLowerCase(i17);
/*  610:     */           }
/*  611:1345 */           if ((i4 >= i16) && (i4 <= i17))
/*  612:     */           {
/*  613:1347 */             i14 = 1;
/*  614:1348 */             break;
/*  615:     */           }
/*  616:     */         }
/*  617:1353 */         if (i14 == 0) {
/*  618:1355 */           return -1;
/*  619:     */         }
/*  620:1357 */         i++;
/*  621:     */         
/*  622:1359 */         break;
/*  623:     */       case 124: 
/*  624:1364 */         if (arrayOfChar[j] != '|')
/*  625:     */         {
/*  626:1367 */           i1 += 3;
/*  627:1368 */           continue;
/*  628:     */         }
/*  629:     */         do
/*  630:     */         {
/*  631:1376 */           if ((n = matchNodes(i1 + 3, 65536, i)) != -1) {
/*  632:1378 */             return n;
/*  633:     */           }
/*  634:1382 */           i4 = (short)arrayOfChar[(i1 + 2)];
/*  635:1383 */           i1 += i4;
/*  636:1385 */         } while ((i4 != 0) && (arrayOfChar[i1] == '|'));
/*  637:1388 */         return -1;
/*  638:     */       case 69: 
/*  639:1400 */         setParenEnd(0, i);
/*  640:1401 */         return i;
/*  641:     */       default: 
/*  642:1406 */         internalError("Invalid opcode '" + k + "'");
/*  643:     */       }
/*  644:1410 */       i1 = j;
/*  645:     */     }
/*  646:1414 */     internalError("Corrupt program");
/*  647:1415 */     return -1;
/*  648:     */   }
/*  649:     */   
/*  650:     */   protected boolean matchAt(int paramInt)
/*  651:     */   {
/*  652:1428 */     this.start0 = -1;
/*  653:1429 */     this.end0 = -1;
/*  654:1430 */     this.start1 = -1;
/*  655:1431 */     this.end1 = -1;
/*  656:1432 */     this.start2 = -1;
/*  657:1433 */     this.end2 = -1;
/*  658:1434 */     this.startn = null;
/*  659:1435 */     this.endn = null;
/*  660:1436 */     this.parenCount = 1;
/*  661:1437 */     setParenStart(0, paramInt);
/*  662:1440 */     if ((this.program.flags & 0x1) != 0)
/*  663:     */     {
/*  664:1442 */       this.startBackref = new int[16];
/*  665:1443 */       this.endBackref = new int[16];
/*  666:     */     }
/*  667:     */     int i;
/*  668:1448 */     if ((i = matchNodes(0, 65536, paramInt)) != -1)
/*  669:     */     {
/*  670:1450 */       setParenEnd(0, i);
/*  671:1451 */       return true;
/*  672:     */     }
/*  673:1455 */     this.parenCount = 0;
/*  674:1456 */     return false;
/*  675:     */   }
/*  676:     */   
/*  677:     */   public boolean match(String paramString, int paramInt)
/*  678:     */   {
/*  679:1468 */     return match(new StringCharacterIterator(paramString), paramInt);
/*  680:     */   }
/*  681:     */   
/*  682:     */   public boolean match(CharacterIterator paramCharacterIterator, int paramInt)
/*  683:     */   {
/*  684:1481 */     if (this.program == null) {
/*  685:1485 */       internalError("No RE program to run!");
/*  686:     */     }
/*  687:1489 */     this.search = paramCharacterIterator;
/*  688:1492 */     if (this.program.prefix == null)
/*  689:     */     {
/*  690:1495 */       for (; !paramCharacterIterator.isEnd(paramInt - 1); paramInt++) {
/*  691:1498 */         if (matchAt(paramInt)) {
/*  692:1500 */           return true;
/*  693:     */         }
/*  694:     */       }
/*  695:1503 */       return false;
/*  696:     */     }
/*  697:1508 */     int i = (this.matchFlags & 0x1) == 0 ? 0 : 1;
/*  698:1509 */     char[] arrayOfChar = this.program.prefix;
/*  699:1510 */     for (; !paramCharacterIterator.isEnd(paramInt + arrayOfChar.length - 1); paramInt++)
/*  700:     */     {
/*  701:1513 */       int j = 0;
/*  702:1514 */       if (i != 0) {
/*  703:1515 */         j = Character.toLowerCase(paramCharacterIterator.charAt(paramInt)) != Character.toLowerCase(arrayOfChar[0]) ? 0 : 1;
/*  704:     */       } else {
/*  705:1517 */         j = paramCharacterIterator.charAt(paramInt) != arrayOfChar[0] ? 0 : 1;
/*  706:     */       }
/*  707:1518 */       if (j != 0)
/*  708:     */       {
/*  709:1521 */         int k = paramInt++;
/*  710:1523 */         for (int m = 1; m < arrayOfChar.length;)
/*  711:     */         {
/*  712:1526 */           if (i != 0) {
/*  713:1527 */             j = Character.toLowerCase(paramCharacterIterator.charAt(paramInt++)) != Character.toLowerCase(arrayOfChar[(m++)]) ? 0 : 1;
/*  714:     */           } else {
/*  715:1529 */             j = paramCharacterIterator.charAt(paramInt++) != arrayOfChar[(m++)] ? 0 : 1;
/*  716:     */           }
/*  717:1530 */           if (j == 0) {
/*  718:     */             break;
/*  719:     */           }
/*  720:     */         }
/*  721:1537 */         if (m == arrayOfChar.length) {
/*  722:1540 */           if (matchAt(k)) {
/*  723:1542 */             return true;
/*  724:     */           }
/*  725:     */         }
/*  726:1547 */         paramInt = k;
/*  727:     */       }
/*  728:     */     }
/*  729:1550 */     return false;
/*  730:     */   }
/*  731:     */   
/*  732:     */   public boolean match(String paramString)
/*  733:     */   {
/*  734:1561 */     return match(paramString, 0);
/*  735:     */   }
/*  736:     */   
/*  737:     */   public String[] split(String paramString)
/*  738:     */   {
/*  739:1576 */     Vector localVector = new Vector();
/*  740:     */     
/*  741:     */ 
/*  742:1579 */     int i = 0;
/*  743:1580 */     int j = paramString.length();
/*  744:1583 */     while ((i < j) && (match(paramString, i)))
/*  745:     */     {
/*  746:1586 */       int k = getParenStart(0);
/*  747:     */       
/*  748:     */ 
/*  749:1589 */       int m = getParenEnd(0);
/*  750:1592 */       if (m == i)
/*  751:     */       {
/*  752:1594 */         localVector.addElement(paramString.substring(i, k + 1));
/*  753:1595 */         m++;
/*  754:     */       }
/*  755:     */       else
/*  756:     */       {
/*  757:1599 */         localVector.addElement(paramString.substring(i, k));
/*  758:     */       }
/*  759:1603 */       i = m;
/*  760:     */     }
/*  761:1607 */     String str = paramString.substring(i);
/*  762:1608 */     if (str.length() != 0) {
/*  763:1610 */       localVector.addElement(str);
/*  764:     */     }
/*  765:1614 */     String[] arrayOfString = new String[localVector.size()];
/*  766:1615 */     localVector.copyInto(arrayOfString);
/*  767:1616 */     return arrayOfString;
/*  768:     */   }
/*  769:     */   
/*  770:     */   public String subst(String paramString1, String paramString2)
/*  771:     */   {
/*  772:1646 */     return subst(paramString1, paramString2, 0);
/*  773:     */   }
/*  774:     */   
/*  775:     */   public String subst(String paramString1, String paramString2, int paramInt)
/*  776:     */   {
/*  777:1669 */     StringBuffer localStringBuffer = new StringBuffer();
/*  778:     */     
/*  779:     */ 
/*  780:1672 */     int i = 0;
/*  781:1673 */     int j = paramString1.length();
/*  782:1676 */     while ((i < j) && (match(paramString1, i)))
/*  783:     */     {
/*  784:1679 */       localStringBuffer.append(paramString1.substring(i, getParenStart(0)));
/*  785:     */       
/*  786:     */ 
/*  787:1682 */       localStringBuffer.append(paramString2);
/*  788:     */       
/*  789:     */ 
/*  790:1685 */       int k = getParenEnd(0);
/*  791:1688 */       if (k == i) {
/*  792:1690 */         k++;
/*  793:     */       }
/*  794:1694 */       i = k;
/*  795:1697 */       if ((paramInt & 0x1) != 0) {
/*  796:     */         break;
/*  797:     */       }
/*  798:     */     }
/*  799:1704 */     if (i < j) {
/*  800:1706 */       localStringBuffer.append(paramString1.substring(i));
/*  801:     */     }
/*  802:1710 */     return localStringBuffer.toString();
/*  803:     */   }
/*  804:     */   
/*  805:     */   public String[] grep(Object[] paramArrayOfObject)
/*  806:     */   {
/*  807:1724 */     Vector localVector = new Vector();
/*  808:1727 */     for (int i = 0; i < paramArrayOfObject.length; i++)
/*  809:     */     {
/*  810:1730 */       localObject = paramArrayOfObject[i].toString();
/*  811:1733 */       if (match((String)localObject)) {
/*  812:1735 */         localVector.addElement(localObject);
/*  813:     */       }
/*  814:     */     }
/*  815:1740 */     Object localObject = new String[localVector.size()];
/*  816:1741 */     localVector.copyInto((Object[])localObject);
/*  817:1742 */     return localObject;
/*  818:     */   }
/*  819:     */   
/*  820:     */   private boolean isNewline(int paramInt)
/*  821:     */   {
/*  822:1748 */     if (paramInt < NEWLINE.length() - 1) {
/*  823:1749 */       return false;
/*  824:     */     }
/*  825:1752 */     if (this.search.charAt(paramInt) == '\n') {
/*  826:1753 */       return true;
/*  827:     */     }
/*  828:1756 */     for (int i = NEWLINE.length() - 1; i >= 0; paramInt--)
/*  829:     */     {
/*  830:1757 */       if (NEWLINE.charAt(i) != this.search.charAt(paramInt)) {
/*  831:1758 */         return false;
/*  832:     */       }
/*  833:1756 */       i--;
/*  834:     */     }
/*  835:1761 */     return true;
/*  836:     */   }
/*  837:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.regexp.RE
 * JD-Core Version:    0.7.0.1
 */