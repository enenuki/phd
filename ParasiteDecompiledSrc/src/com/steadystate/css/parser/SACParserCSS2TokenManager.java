/*    1:     */ package com.steadystate.css.parser;
/*    2:     */ 
/*    3:     */ import java.io.IOException;
/*    4:     */ import java.io.PrintStream;
/*    5:     */ import org.w3c.css.sac.ErrorHandler;
/*    6:     */ 
/*    7:     */ public class SACParserCSS2TokenManager
/*    8:     */   implements SACParserCSS2Constants
/*    9:     */ {
/*   10:  13 */   private boolean _quiet = true;
/*   11:     */   private ErrorHandler errorHandler;
/*   12:     */   
/*   13:     */   private String trimBy(StringBuffer s, int left, int right)
/*   14:     */   {
/*   15:  17 */     int end = s.length();
/*   16:  18 */     return s.toString().substring(left, end - right);
/*   17:     */   }
/*   18:     */   
/*   19:     */   private String trimUrl(StringBuffer s)
/*   20:     */   {
/*   21:  22 */     StringBuffer s1 = new StringBuffer(trimBy(s, 4, 1).trim());
/*   22:  23 */     if (s1.length() == 0) {
/*   23:  25 */       return s1.toString();
/*   24:     */     }
/*   25:  27 */     int end = s1.length() - 1;
/*   26:  28 */     if (((s1.charAt(0) == '"') && (s1.charAt(end) == '"')) || ((s1.charAt(0) == '\'') && (s1.charAt(end) == '\''))) {
/*   27:  30 */       return trimBy(s1, 1, 1);
/*   28:     */     }
/*   29:  32 */     return s1.toString();
/*   30:     */   }
/*   31:     */   
/*   32:  34 */   public PrintStream debugStream = System.out;
/*   33:     */   
/*   34:     */   public void setDebugStream(PrintStream ds)
/*   35:     */   {
/*   36:  35 */     this.debugStream = ds;
/*   37:     */   }
/*   38:     */   
/*   39:     */   private final int jjStopStringLiteralDfa_0(int pos, long active0)
/*   40:     */   {
/*   41:  38 */     switch (pos)
/*   42:     */     {
/*   43:     */     case 0: 
/*   44:  41 */       if ((active0 & 0xF0000000) != 0L) {
/*   45:  42 */         return 61;
/*   46:     */       }
/*   47:  43 */       if ((active0 & 0x0) != 0L)
/*   48:     */       {
/*   49:  45 */         this.jjmatchedKind = 56;
/*   50:  46 */         return 426;
/*   51:     */       }
/*   52:  48 */       if ((active0 & 0x100) != 0L) {
/*   53:  49 */         return 427;
/*   54:     */       }
/*   55:  50 */       return -1;
/*   56:     */     case 1: 
/*   57:  52 */       if ((active0 & 0x0) != 0L)
/*   58:     */       {
/*   59:  54 */         this.jjmatchedKind = 56;
/*   60:  55 */         this.jjmatchedPos = 1;
/*   61:  56 */         return 426;
/*   62:     */       }
/*   63:  58 */       if ((active0 & 0xF0000000) != 0L)
/*   64:     */       {
/*   65:  60 */         this.jjmatchedKind = 33;
/*   66:  61 */         this.jjmatchedPos = 1;
/*   67:  62 */         return 428;
/*   68:     */       }
/*   69:  64 */       return -1;
/*   70:     */     case 2: 
/*   71:  66 */       if ((active0 & 0xF0000000) != 0L)
/*   72:     */       {
/*   73:  68 */         this.jjmatchedKind = 33;
/*   74:  69 */         this.jjmatchedPos = 2;
/*   75:  70 */         return 428;
/*   76:     */       }
/*   77:  72 */       if ((active0 & 0x0) != 0L)
/*   78:     */       {
/*   79:  74 */         this.jjmatchedKind = 56;
/*   80:  75 */         this.jjmatchedPos = 2;
/*   81:  76 */         return 426;
/*   82:     */       }
/*   83:  78 */       return -1;
/*   84:     */     case 3: 
/*   85:  80 */       if ((active0 & 0x0) != 0L)
/*   86:     */       {
/*   87:  82 */         this.jjmatchedKind = 56;
/*   88:  83 */         this.jjmatchedPos = 3;
/*   89:  84 */         return 426;
/*   90:     */       }
/*   91:  86 */       if ((active0 & 0xF0000000) != 0L)
/*   92:     */       {
/*   93:  88 */         this.jjmatchedKind = 33;
/*   94:  89 */         this.jjmatchedPos = 3;
/*   95:  90 */         return 428;
/*   96:     */       }
/*   97:  92 */       return -1;
/*   98:     */     case 4: 
/*   99:  94 */       if ((active0 & 0x0) != 0L)
/*  100:     */       {
/*  101:  96 */         this.jjmatchedKind = 56;
/*  102:  97 */         this.jjmatchedPos = 4;
/*  103:  98 */         return 426;
/*  104:     */       }
/*  105: 100 */       if ((active0 & 0x20000000) != 0L) {
/*  106: 101 */         return 428;
/*  107:     */       }
/*  108: 102 */       if ((active0 & 0xD0000000) != 0L)
/*  109:     */       {
/*  110: 104 */         this.jjmatchedKind = 33;
/*  111: 105 */         this.jjmatchedPos = 4;
/*  112: 106 */         return 428;
/*  113:     */       }
/*  114: 108 */       return -1;
/*  115:     */     case 5: 
/*  116: 110 */       if ((active0 & 0x90000000) != 0L)
/*  117:     */       {
/*  118: 112 */         this.jjmatchedKind = 33;
/*  119: 113 */         this.jjmatchedPos = 5;
/*  120: 114 */         return 428;
/*  121:     */       }
/*  122: 116 */       if ((active0 & 0x40000000) != 0L) {
/*  123: 117 */         return 428;
/*  124:     */       }
/*  125: 118 */       if ((active0 & 0x0) != 0L)
/*  126:     */       {
/*  127: 120 */         this.jjmatchedKind = 56;
/*  128: 121 */         this.jjmatchedPos = 5;
/*  129: 122 */         return 426;
/*  130:     */       }
/*  131: 124 */       return -1;
/*  132:     */     case 6: 
/*  133: 126 */       if ((active0 & 0x10000000) != 0L) {
/*  134: 127 */         return 428;
/*  135:     */       }
/*  136: 128 */       if ((active0 & 0x80000000) != 0L)
/*  137:     */       {
/*  138: 130 */         this.jjmatchedKind = 33;
/*  139: 131 */         this.jjmatchedPos = 6;
/*  140: 132 */         return 428;
/*  141:     */       }
/*  142: 134 */       if ((active0 & 0x0) != 0L) {
/*  143: 135 */         return 426;
/*  144:     */       }
/*  145: 136 */       return -1;
/*  146:     */     case 7: 
/*  147: 138 */       if ((active0 & 0x0) != 0L) {
/*  148: 139 */         return 428;
/*  149:     */       }
/*  150: 140 */       if ((active0 & 0x80000000) != 0L)
/*  151:     */       {
/*  152: 142 */         this.jjmatchedKind = 33;
/*  153: 143 */         this.jjmatchedPos = 7;
/*  154: 144 */         return 428;
/*  155:     */       }
/*  156: 146 */       return -1;
/*  157:     */     case 8: 
/*  158: 148 */       if ((active0 & 0x80000000) != 0L)
/*  159:     */       {
/*  160: 150 */         this.jjmatchedKind = 33;
/*  161: 151 */         this.jjmatchedPos = 8;
/*  162: 152 */         return 428;
/*  163:     */       }
/*  164: 154 */       return -1;
/*  165:     */     }
/*  166: 156 */     return -1;
/*  167:     */   }
/*  168:     */   
/*  169:     */   private final int jjStartNfa_0(int pos, long active0)
/*  170:     */   {
/*  171: 161 */     return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0), pos + 1);
/*  172:     */   }
/*  173:     */   
/*  174:     */   private final int jjStopAtPos(int pos, int kind)
/*  175:     */   {
/*  176: 165 */     this.jjmatchedKind = kind;
/*  177: 166 */     this.jjmatchedPos = pos;
/*  178: 167 */     return pos + 1;
/*  179:     */   }
/*  180:     */   
/*  181:     */   private final int jjStartNfaWithStates_0(int pos, int kind, int state)
/*  182:     */   {
/*  183: 171 */     this.jjmatchedKind = kind;
/*  184: 172 */     this.jjmatchedPos = pos;
/*  185:     */     try
/*  186:     */     {
/*  187: 173 */       this.curChar = this.input_stream.readChar();
/*  188:     */     }
/*  189:     */     catch (IOException e)
/*  190:     */     {
/*  191: 174 */       return pos + 1;
/*  192:     */     }
/*  193: 175 */     return jjMoveNfa_0(state, pos + 1);
/*  194:     */   }
/*  195:     */   
/*  196:     */   private final int jjMoveStringLiteralDfa0_0()
/*  197:     */   {
/*  198: 179 */     switch (this.curChar)
/*  199:     */     {
/*  200:     */     case ')': 
/*  201: 182 */       return jjStopAtPos(0, 21);
/*  202:     */     case '*': 
/*  203: 184 */       return jjStopAtPos(0, 11);
/*  204:     */     case '+': 
/*  205: 186 */       return jjStopAtPos(0, 13);
/*  206:     */     case ',': 
/*  207: 188 */       return jjStopAtPos(0, 7);
/*  208:     */     case '-': 
/*  209: 190 */       this.jjmatchedKind = 14;
/*  210: 191 */       return jjMoveStringLiteralDfa1_0(33554432L);
/*  211:     */     case '.': 
/*  212: 193 */       return jjStartNfaWithStates_0(0, 8, 427);
/*  213:     */     case '/': 
/*  214: 195 */       this.jjmatchedKind = 12;
/*  215: 196 */       return jjMoveStringLiteralDfa1_0(4L);
/*  216:     */     case ':': 
/*  217: 198 */       return jjStopAtPos(0, 10);
/*  218:     */     case ';': 
/*  219: 200 */       return jjStopAtPos(0, 9);
/*  220:     */     case '<': 
/*  221: 202 */       return jjMoveStringLiteralDfa1_0(16777216L);
/*  222:     */     case '=': 
/*  223: 204 */       return jjStopAtPos(0, 15);
/*  224:     */     case '>': 
/*  225: 206 */       return jjStopAtPos(0, 16);
/*  226:     */     case '@': 
/*  227: 208 */       return jjMoveStringLiteralDfa1_0(8321499136L);
/*  228:     */     case '[': 
/*  229: 210 */       return jjStopAtPos(0, 17);
/*  230:     */     case ']': 
/*  231: 212 */       return jjStopAtPos(0, 18);
/*  232:     */     case 'I': 
/*  233:     */     case 'i': 
/*  234: 215 */       return jjMoveStringLiteralDfa1_0(34359738368L);
/*  235:     */     case 'R': 
/*  236:     */     case 'r': 
/*  237: 218 */       return jjMoveStringLiteralDfa1_0(18014398509481984L);
/*  238:     */     case '{': 
/*  239: 220 */       return jjStopAtPos(0, 5);
/*  240:     */     case '|': 
/*  241: 222 */       return jjMoveStringLiteralDfa1_0(134217728L);
/*  242:     */     case '}': 
/*  243: 224 */       return jjStopAtPos(0, 6);
/*  244:     */     case '~': 
/*  245: 226 */       return jjMoveStringLiteralDfa1_0(67108864L);
/*  246:     */     }
/*  247: 228 */     return jjMoveNfa_0(1, 0);
/*  248:     */   }
/*  249:     */   
/*  250:     */   private final int jjMoveStringLiteralDfa1_0(long active0)
/*  251:     */   {
/*  252:     */     try
/*  253:     */     {
/*  254: 233 */       this.curChar = this.input_stream.readChar();
/*  255:     */     }
/*  256:     */     catch (IOException e)
/*  257:     */     {
/*  258: 235 */       jjStopStringLiteralDfa_0(0, active0);
/*  259: 236 */       return 1;
/*  260:     */     }
/*  261: 238 */     switch (this.curChar)
/*  262:     */     {
/*  263:     */     case '!': 
/*  264: 241 */       return jjMoveStringLiteralDfa2_0(active0, 16777216L);
/*  265:     */     case '*': 
/*  266: 243 */       if ((active0 & 0x4) != 0L) {
/*  267: 244 */         return jjStopAtPos(1, 2);
/*  268:     */       }
/*  269:     */       break;
/*  270:     */     case '-': 
/*  271: 247 */       return jjMoveStringLiteralDfa2_0(active0, 33554432L);
/*  272:     */     case '=': 
/*  273: 249 */       if ((active0 & 0x4000000) != 0L) {
/*  274: 250 */         return jjStopAtPos(1, 26);
/*  275:     */       }
/*  276: 251 */       if ((active0 & 0x8000000) != 0L) {
/*  277: 252 */         return jjStopAtPos(1, 27);
/*  278:     */       }
/*  279:     */       break;
/*  280:     */     case 'C': 
/*  281:     */     case 'c': 
/*  282: 256 */       return jjMoveStringLiteralDfa2_0(active0, 4294967296L);
/*  283:     */     case 'F': 
/*  284:     */     case 'f': 
/*  285: 259 */       return jjMoveStringLiteralDfa2_0(active0, 2147483648L);
/*  286:     */     case 'G': 
/*  287:     */     case 'g': 
/*  288: 262 */       return jjMoveStringLiteralDfa2_0(active0, 18014398509481984L);
/*  289:     */     case 'I': 
/*  290:     */     case 'i': 
/*  291: 265 */       return jjMoveStringLiteralDfa2_0(active0, 268435456L);
/*  292:     */     case 'M': 
/*  293:     */     case 'm': 
/*  294: 268 */       return jjMoveStringLiteralDfa2_0(active0, 1073741824L);
/*  295:     */     case 'N': 
/*  296:     */     case 'n': 
/*  297: 271 */       return jjMoveStringLiteralDfa2_0(active0, 34359738368L);
/*  298:     */     case 'P': 
/*  299:     */     case 'p': 
/*  300: 274 */       return jjMoveStringLiteralDfa2_0(active0, 536870912L);
/*  301:     */     }
/*  302: 278 */     return jjStartNfa_0(0, active0);
/*  303:     */   }
/*  304:     */   
/*  305:     */   private final int jjMoveStringLiteralDfa2_0(long old0, long active0)
/*  306:     */   {
/*  307: 282 */     if ((active0 &= old0) == 0L) {
/*  308: 283 */       return jjStartNfa_0(0, old0);
/*  309:     */     }
/*  310:     */     try
/*  311:     */     {
/*  312: 284 */       this.curChar = this.input_stream.readChar();
/*  313:     */     }
/*  314:     */     catch (IOException e)
/*  315:     */     {
/*  316: 286 */       jjStopStringLiteralDfa_0(1, active0);
/*  317: 287 */       return 2;
/*  318:     */     }
/*  319: 289 */     switch (this.curChar)
/*  320:     */     {
/*  321:     */     case '-': 
/*  322: 292 */       return jjMoveStringLiteralDfa3_0(active0, 16777216L);
/*  323:     */     case '>': 
/*  324: 294 */       if ((active0 & 0x2000000) != 0L) {
/*  325: 295 */         return jjStopAtPos(2, 25);
/*  326:     */       }
/*  327:     */       break;
/*  328:     */     case 'A': 
/*  329:     */     case 'a': 
/*  330: 299 */       return jjMoveStringLiteralDfa3_0(active0, 536870912L);
/*  331:     */     case 'B': 
/*  332:     */     case 'b': 
/*  333: 302 */       return jjMoveStringLiteralDfa3_0(active0, 18014398509481984L);
/*  334:     */     case 'E': 
/*  335:     */     case 'e': 
/*  336: 305 */       return jjMoveStringLiteralDfa3_0(active0, 1073741824L);
/*  337:     */     case 'H': 
/*  338:     */     case 'h': 
/*  339: 308 */       return jjMoveStringLiteralDfa3_0(active0, 38654705664L);
/*  340:     */     case 'M': 
/*  341:     */     case 'm': 
/*  342: 311 */       return jjMoveStringLiteralDfa3_0(active0, 268435456L);
/*  343:     */     case 'O': 
/*  344:     */     case 'o': 
/*  345: 314 */       return jjMoveStringLiteralDfa3_0(active0, 2147483648L);
/*  346:     */     }
/*  347: 318 */     return jjStartNfa_0(1, active0);
/*  348:     */   }
/*  349:     */   
/*  350:     */   private final int jjMoveStringLiteralDfa3_0(long old0, long active0)
/*  351:     */   {
/*  352: 322 */     if ((active0 &= old0) == 0L) {
/*  353: 323 */       return jjStartNfa_0(1, old0);
/*  354:     */     }
/*  355:     */     try
/*  356:     */     {
/*  357: 324 */       this.curChar = this.input_stream.readChar();
/*  358:     */     }
/*  359:     */     catch (IOException e)
/*  360:     */     {
/*  361: 326 */       jjStopStringLiteralDfa_0(2, active0);
/*  362: 327 */       return 3;
/*  363:     */     }
/*  364: 329 */     switch (this.curChar)
/*  365:     */     {
/*  366:     */     case '(': 
/*  367: 332 */       if ((active0 & 0x0) != 0L) {
/*  368: 333 */         return jjStopAtPos(3, 54);
/*  369:     */       }
/*  370:     */       break;
/*  371:     */     case '-': 
/*  372: 336 */       if ((active0 & 0x1000000) != 0L) {
/*  373: 337 */         return jjStopAtPos(3, 24);
/*  374:     */       }
/*  375:     */       break;
/*  376:     */     case 'A': 
/*  377:     */     case 'a': 
/*  378: 341 */       return jjMoveStringLiteralDfa4_0(active0, 4294967296L);
/*  379:     */     case 'D': 
/*  380:     */     case 'd': 
/*  381: 344 */       return jjMoveStringLiteralDfa4_0(active0, 1073741824L);
/*  382:     */     case 'E': 
/*  383:     */     case 'e': 
/*  384: 347 */       return jjMoveStringLiteralDfa4_0(active0, 34359738368L);
/*  385:     */     case 'G': 
/*  386:     */     case 'g': 
/*  387: 350 */       return jjMoveStringLiteralDfa4_0(active0, 536870912L);
/*  388:     */     case 'N': 
/*  389:     */     case 'n': 
/*  390: 353 */       return jjMoveStringLiteralDfa4_0(active0, 2147483648L);
/*  391:     */     case 'P': 
/*  392:     */     case 'p': 
/*  393: 356 */       return jjMoveStringLiteralDfa4_0(active0, 268435456L);
/*  394:     */     }
/*  395: 360 */     return jjStartNfa_0(2, active0);
/*  396:     */   }
/*  397:     */   
/*  398:     */   private final int jjMoveStringLiteralDfa4_0(long old0, long active0)
/*  399:     */   {
/*  400: 364 */     if ((active0 &= old0) == 0L) {
/*  401: 365 */       return jjStartNfa_0(2, old0);
/*  402:     */     }
/*  403:     */     try
/*  404:     */     {
/*  405: 366 */       this.curChar = this.input_stream.readChar();
/*  406:     */     }
/*  407:     */     catch (IOException e)
/*  408:     */     {
/*  409: 368 */       jjStopStringLiteralDfa_0(3, active0);
/*  410: 369 */       return 4;
/*  411:     */     }
/*  412: 371 */     switch (this.curChar)
/*  413:     */     {
/*  414:     */     case 'E': 
/*  415:     */     case 'e': 
/*  416: 375 */       if ((active0 & 0x20000000) != 0L) {
/*  417: 376 */         return jjStartNfaWithStates_0(4, 29, 428);
/*  418:     */       }
/*  419:     */       break;
/*  420:     */     case 'I': 
/*  421:     */     case 'i': 
/*  422: 380 */       return jjMoveStringLiteralDfa5_0(active0, 1073741824L);
/*  423:     */     case 'O': 
/*  424:     */     case 'o': 
/*  425: 383 */       return jjMoveStringLiteralDfa5_0(active0, 268435456L);
/*  426:     */     case 'R': 
/*  427:     */     case 'r': 
/*  428: 386 */       return jjMoveStringLiteralDfa5_0(active0, 38654705664L);
/*  429:     */     case 'T': 
/*  430:     */     case 't': 
/*  431: 389 */       return jjMoveStringLiteralDfa5_0(active0, 2147483648L);
/*  432:     */     }
/*  433: 393 */     return jjStartNfa_0(3, active0);
/*  434:     */   }
/*  435:     */   
/*  436:     */   private final int jjMoveStringLiteralDfa5_0(long old0, long active0)
/*  437:     */   {
/*  438: 397 */     if ((active0 &= old0) == 0L) {
/*  439: 398 */       return jjStartNfa_0(3, old0);
/*  440:     */     }
/*  441:     */     try
/*  442:     */     {
/*  443: 399 */       this.curChar = this.input_stream.readChar();
/*  444:     */     }
/*  445:     */     catch (IOException e)
/*  446:     */     {
/*  447: 401 */       jjStopStringLiteralDfa_0(4, active0);
/*  448: 402 */       return 5;
/*  449:     */     }
/*  450: 404 */     switch (this.curChar)
/*  451:     */     {
/*  452:     */     case '-': 
/*  453: 407 */       return jjMoveStringLiteralDfa6_0(active0, 2147483648L);
/*  454:     */     case 'A': 
/*  455:     */     case 'a': 
/*  456: 410 */       if ((active0 & 0x40000000) != 0L) {
/*  457: 411 */         return jjStartNfaWithStates_0(5, 30, 428);
/*  458:     */       }
/*  459:     */       break;
/*  460:     */     case 'I': 
/*  461:     */     case 'i': 
/*  462: 415 */       return jjMoveStringLiteralDfa6_0(active0, 34359738368L);
/*  463:     */     case 'R': 
/*  464:     */     case 'r': 
/*  465: 418 */       return jjMoveStringLiteralDfa6_0(active0, 268435456L);
/*  466:     */     case 'S': 
/*  467:     */     case 's': 
/*  468: 421 */       return jjMoveStringLiteralDfa6_0(active0, 4294967296L);
/*  469:     */     }
/*  470: 425 */     return jjStartNfa_0(4, active0);
/*  471:     */   }
/*  472:     */   
/*  473:     */   private final int jjMoveStringLiteralDfa6_0(long old0, long active0)
/*  474:     */   {
/*  475: 429 */     if ((active0 &= old0) == 0L) {
/*  476: 430 */       return jjStartNfa_0(4, old0);
/*  477:     */     }
/*  478:     */     try
/*  479:     */     {
/*  480: 431 */       this.curChar = this.input_stream.readChar();
/*  481:     */     }
/*  482:     */     catch (IOException e)
/*  483:     */     {
/*  484: 433 */       jjStopStringLiteralDfa_0(5, active0);
/*  485: 434 */       return 6;
/*  486:     */     }
/*  487: 436 */     switch (this.curChar)
/*  488:     */     {
/*  489:     */     case 'E': 
/*  490:     */     case 'e': 
/*  491: 440 */       return jjMoveStringLiteralDfa7_0(active0, 4294967296L);
/*  492:     */     case 'F': 
/*  493:     */     case 'f': 
/*  494: 443 */       return jjMoveStringLiteralDfa7_0(active0, 2147483648L);
/*  495:     */     case 'T': 
/*  496:     */     case 't': 
/*  497: 446 */       if ((active0 & 0x10000000) != 0L) {
/*  498: 447 */         return jjStartNfaWithStates_0(6, 28, 428);
/*  499:     */       }
/*  500: 448 */       if ((active0 & 0x0) != 0L) {
/*  501: 449 */         return jjStartNfaWithStates_0(6, 35, 426);
/*  502:     */       }
/*  503:     */       break;
/*  504:     */     }
/*  505: 454 */     return jjStartNfa_0(5, active0);
/*  506:     */   }
/*  507:     */   
/*  508:     */   private final int jjMoveStringLiteralDfa7_0(long old0, long active0)
/*  509:     */   {
/*  510: 458 */     if ((active0 &= old0) == 0L) {
/*  511: 459 */       return jjStartNfa_0(5, old0);
/*  512:     */     }
/*  513:     */     try
/*  514:     */     {
/*  515: 460 */       this.curChar = this.input_stream.readChar();
/*  516:     */     }
/*  517:     */     catch (IOException e)
/*  518:     */     {
/*  519: 462 */       jjStopStringLiteralDfa_0(6, active0);
/*  520: 463 */       return 7;
/*  521:     */     }
/*  522: 465 */     switch (this.curChar)
/*  523:     */     {
/*  524:     */     case 'A': 
/*  525:     */     case 'a': 
/*  526: 469 */       return jjMoveStringLiteralDfa8_0(active0, 2147483648L);
/*  527:     */     case 'T': 
/*  528:     */     case 't': 
/*  529: 472 */       if ((active0 & 0x0) != 0L) {
/*  530: 473 */         return jjStartNfaWithStates_0(7, 32, 428);
/*  531:     */       }
/*  532:     */       break;
/*  533:     */     }
/*  534: 478 */     return jjStartNfa_0(6, active0);
/*  535:     */   }
/*  536:     */   
/*  537:     */   private final int jjMoveStringLiteralDfa8_0(long old0, long active0)
/*  538:     */   {
/*  539: 482 */     if ((active0 &= old0) == 0L) {
/*  540: 483 */       return jjStartNfa_0(6, old0);
/*  541:     */     }
/*  542:     */     try
/*  543:     */     {
/*  544: 484 */       this.curChar = this.input_stream.readChar();
/*  545:     */     }
/*  546:     */     catch (IOException e)
/*  547:     */     {
/*  548: 486 */       jjStopStringLiteralDfa_0(7, active0);
/*  549: 487 */       return 8;
/*  550:     */     }
/*  551: 489 */     switch (this.curChar)
/*  552:     */     {
/*  553:     */     case 'C': 
/*  554:     */     case 'c': 
/*  555: 493 */       return jjMoveStringLiteralDfa9_0(active0, 2147483648L);
/*  556:     */     }
/*  557: 497 */     return jjStartNfa_0(7, active0);
/*  558:     */   }
/*  559:     */   
/*  560:     */   private final int jjMoveStringLiteralDfa9_0(long old0, long active0)
/*  561:     */   {
/*  562: 501 */     if ((active0 &= old0) == 0L) {
/*  563: 502 */       return jjStartNfa_0(7, old0);
/*  564:     */     }
/*  565:     */     try
/*  566:     */     {
/*  567: 503 */       this.curChar = this.input_stream.readChar();
/*  568:     */     }
/*  569:     */     catch (IOException e)
/*  570:     */     {
/*  571: 505 */       jjStopStringLiteralDfa_0(8, active0);
/*  572: 506 */       return 9;
/*  573:     */     }
/*  574: 508 */     switch (this.curChar)
/*  575:     */     {
/*  576:     */     case 'E': 
/*  577:     */     case 'e': 
/*  578: 512 */       if ((active0 & 0x80000000) != 0L) {
/*  579: 513 */         return jjStartNfaWithStates_0(9, 31, 428);
/*  580:     */       }
/*  581:     */       break;
/*  582:     */     }
/*  583: 518 */     return jjStartNfa_0(8, active0);
/*  584:     */   }
/*  585:     */   
/*  586:     */   private final void jjCheckNAdd(int state)
/*  587:     */   {
/*  588: 522 */     if (this.jjrounds[state] != this.jjround)
/*  589:     */     {
/*  590: 524 */       this.jjstateSet[(this.jjnewStateCnt++)] = state;
/*  591: 525 */       this.jjrounds[state] = this.jjround;
/*  592:     */     }
/*  593:     */   }
/*  594:     */   
/*  595:     */   private final void jjAddStates(int start, int end)
/*  596:     */   {
/*  597:     */     do
/*  598:     */     {
/*  599: 531 */       this.jjstateSet[(this.jjnewStateCnt++)] = jjnextStates[start];
/*  600: 532 */     } while (start++ != end);
/*  601:     */   }
/*  602:     */   
/*  603:     */   private final void jjCheckNAddTwoStates(int state1, int state2)
/*  604:     */   {
/*  605: 536 */     jjCheckNAdd(state1);
/*  606: 537 */     jjCheckNAdd(state2);
/*  607:     */   }
/*  608:     */   
/*  609:     */   private final void jjCheckNAddStates(int start, int end)
/*  610:     */   {
/*  611:     */     do
/*  612:     */     {
/*  613: 542 */       jjCheckNAdd(jjnextStates[start]);
/*  614: 543 */     } while (start++ != end);
/*  615:     */   }
/*  616:     */   
/*  617:     */   private final void jjCheckNAddStates(int start)
/*  618:     */   {
/*  619: 547 */     jjCheckNAdd(jjnextStates[start]);
/*  620: 548 */     jjCheckNAdd(jjnextStates[(start + 1)]);
/*  621:     */   }
/*  622:     */   
/*  623: 550 */   static final long[] jjbitVec0 = { -2L, -1L, -1L, -1L };
/*  624: 553 */   static final long[] jjbitVec2 = { 0L, 0L, -1L, -1L };
/*  625:     */   
/*  626:     */   private final int jjMoveNfa_0(int startState, int curPos)
/*  627:     */   {
/*  628: 559 */     int startsAt = 0;
/*  629: 560 */     this.jjnewStateCnt = 426;
/*  630: 561 */     int i = 1;
/*  631: 562 */     this.jjstateSet[0] = startState;
/*  632: 563 */     int kind = 2147483647;
/*  633:     */     for (;;)
/*  634:     */     {
/*  635: 566 */       if (++this.jjround == 2147483647) {
/*  636: 567 */         ReInitRounds();
/*  637:     */       }
/*  638: 568 */       if (this.curChar < '@')
/*  639:     */       {
/*  640: 570 */         long l = 1L << this.curChar;
/*  641:     */         do
/*  642:     */         {
/*  643: 573 */           switch (this.jjstateSet[(--i)])
/*  644:     */           {
/*  645:     */           case 62: 
/*  646:     */           case 428: 
/*  647: 577 */             if ((0x0 & l) != 0L)
/*  648:     */             {
/*  649: 579 */               if (kind > 33) {
/*  650: 580 */                 kind = 33;
/*  651:     */               }
/*  652: 581 */               jjCheckNAddTwoStates(62, 63);
/*  653:     */             }
/*  654: 582 */             break;
/*  655:     */           case 427: 
/*  656: 584 */             if ((0x0 & l) != 0L)
/*  657:     */             {
/*  658: 586 */               if (kind > 58) {
/*  659: 587 */                 kind = 58;
/*  660:     */               }
/*  661: 588 */               jjCheckNAdd(308);
/*  662:     */             }
/*  663: 590 */             if ((0x0 & l) != 0L)
/*  664:     */             {
/*  665: 592 */               if (kind > 53) {
/*  666: 593 */                 kind = 53;
/*  667:     */               }
/*  668: 594 */               jjCheckNAdd(307);
/*  669:     */             }
/*  670: 596 */             if ((0x0 & l) != 0L) {
/*  671: 597 */               jjCheckNAddTwoStates(305, 306);
/*  672:     */             }
/*  673: 598 */             if ((0x0 & l) != 0L) {
/*  674: 599 */               jjCheckNAddStates(0, 2);
/*  675:     */             }
/*  676: 600 */             if ((0x0 & l) != 0L) {
/*  677: 601 */               jjCheckNAddTwoStates(270, 273);
/*  678:     */             }
/*  679: 602 */             if ((0x0 & l) != 0L) {
/*  680: 603 */               jjCheckNAddTwoStates(267, 269);
/*  681:     */             }
/*  682: 604 */             if ((0x0 & l) != 0L) {
/*  683: 605 */               jjCheckNAddTwoStates(265, 266);
/*  684:     */             }
/*  685: 606 */             if ((0x0 & l) != 0L) {
/*  686: 607 */               jjCheckNAddTwoStates(262, 264);
/*  687:     */             }
/*  688: 608 */             if ((0x0 & l) != 0L) {
/*  689: 609 */               jjCheckNAddTwoStates(257, 261);
/*  690:     */             }
/*  691: 610 */             if ((0x0 & l) != 0L) {
/*  692: 611 */               jjCheckNAddTwoStates(253, 256);
/*  693:     */             }
/*  694: 612 */             if ((0x0 & l) != 0L) {
/*  695: 613 */               jjCheckNAddTwoStates(249, 252);
/*  696:     */             }
/*  697: 614 */             if ((0x0 & l) != 0L) {
/*  698: 615 */               jjCheckNAddTwoStates(246, 248);
/*  699:     */             }
/*  700: 616 */             if ((0x0 & l) != 0L) {
/*  701: 617 */               jjCheckNAddTwoStates(243, 245);
/*  702:     */             }
/*  703: 618 */             if ((0x0 & l) != 0L) {
/*  704: 619 */               jjCheckNAddTwoStates(240, 242);
/*  705:     */             }
/*  706: 620 */             if ((0x0 & l) != 0L) {
/*  707: 621 */               jjCheckNAddTwoStates(237, 239);
/*  708:     */             }
/*  709: 622 */             if ((0x0 & l) != 0L) {
/*  710: 623 */               jjCheckNAddTwoStates(234, 236);
/*  711:     */             }
/*  712: 624 */             if ((0x0 & l) != 0L) {
/*  713: 625 */               jjCheckNAddTwoStates(231, 233);
/*  714:     */             }
/*  715: 626 */             if ((0x0 & l) != 0L) {
/*  716: 627 */               jjCheckNAddTwoStates(228, 230);
/*  717:     */             }
/*  718: 628 */             if ((0x0 & l) != 0L) {
/*  719: 629 */               jjCheckNAddTwoStates(225, 227);
/*  720:     */             }
/*  721:     */             break;
/*  722:     */           case 1: 
/*  723: 632 */             if ((0x0 & l) != 0L)
/*  724:     */             {
/*  725: 634 */               if (kind > 53) {
/*  726: 635 */                 kind = 53;
/*  727:     */               }
/*  728: 636 */               jjCheckNAddStates(3, 77);
/*  729:     */             }
/*  730: 638 */             else if ((0x3600 & l) != 0L)
/*  731:     */             {
/*  732: 640 */               if (kind > 1) {
/*  733: 641 */                 kind = 1;
/*  734:     */               }
/*  735: 642 */               jjCheckNAdd(0);
/*  736:     */             }
/*  737: 644 */             else if (this.curChar == '.')
/*  738:     */             {
/*  739: 645 */               jjCheckNAddStates(78, 96);
/*  740:     */             }
/*  741: 646 */             else if (this.curChar == '!')
/*  742:     */             {
/*  743: 647 */               jjCheckNAddTwoStates(92, 101);
/*  744:     */             }
/*  745: 648 */             else if (this.curChar == '\'')
/*  746:     */             {
/*  747: 649 */               jjCheckNAddStates(97, 99);
/*  748:     */             }
/*  749: 650 */             else if (this.curChar == '"')
/*  750:     */             {
/*  751: 651 */               jjCheckNAddStates(100, 102);
/*  752:     */             }
/*  753: 652 */             else if (this.curChar == '#')
/*  754:     */             {
/*  755: 653 */               jjCheckNAddTwoStates(2, 3);
/*  756:     */             }
/*  757:     */             break;
/*  758:     */           case 426: 
/*  759: 656 */             if ((0x0 & l) != 0L)
/*  760:     */             {
/*  761: 658 */               if (kind > 56) {
/*  762: 659 */                 kind = 56;
/*  763:     */               }
/*  764: 660 */               jjCheckNAddTwoStates(327, 328);
/*  765:     */             }
/*  766: 662 */             else if (this.curChar == '(')
/*  767:     */             {
/*  768: 664 */               if (kind > 55) {
/*  769: 665 */                 kind = 55;
/*  770:     */               }
/*  771:     */             }
/*  772: 667 */             if ((0x0 & l) != 0L) {
/*  773: 668 */               jjCheckNAddStates(103, 105);
/*  774:     */             }
/*  775:     */             break;
/*  776:     */           case 0: 
/*  777: 671 */             if ((0x3600 & l) != 0L)
/*  778:     */             {
/*  779: 673 */               if (kind > 1) {
/*  780: 674 */                 kind = 1;
/*  781:     */               }
/*  782: 675 */               jjCheckNAdd(0);
/*  783:     */             }
/*  784: 676 */             break;
/*  785:     */           case 2: 
/*  786: 678 */             if ((0x0 & l) != 0L)
/*  787:     */             {
/*  788: 680 */               if (kind > 19) {
/*  789: 681 */                 kind = 19;
/*  790:     */               }
/*  791: 682 */               jjCheckNAddTwoStates(2, 3);
/*  792:     */             }
/*  793: 683 */             break;
/*  794:     */           case 4: 
/*  795: 685 */             if ((0x0 & l) != 0L)
/*  796:     */             {
/*  797: 687 */               if (kind > 19) {
/*  798: 688 */                 kind = 19;
/*  799:     */               }
/*  800: 689 */               jjCheckNAddTwoStates(2, 3);
/*  801:     */             }
/*  802: 690 */             break;
/*  803:     */           case 5: 
/*  804: 692 */             if ((0x0 & l) != 0L)
/*  805:     */             {
/*  806: 694 */               if (kind > 19) {
/*  807: 695 */                 kind = 19;
/*  808:     */               }
/*  809: 696 */               jjCheckNAddStates(106, 113);
/*  810:     */             }
/*  811: 697 */             break;
/*  812:     */           case 6: 
/*  813: 699 */             if ((0x0 & l) != 0L)
/*  814:     */             {
/*  815: 701 */               if (kind > 19) {
/*  816: 702 */                 kind = 19;
/*  817:     */               }
/*  818: 703 */               jjCheckNAddStates(114, 116);
/*  819:     */             }
/*  820: 704 */             break;
/*  821:     */           case 7: 
/*  822: 706 */             if ((0x3600 & l) != 0L)
/*  823:     */             {
/*  824: 708 */               if (kind > 19) {
/*  825: 709 */                 kind = 19;
/*  826:     */               }
/*  827: 710 */               jjCheckNAddTwoStates(2, 3);
/*  828:     */             }
/*  829: 711 */             break;
/*  830:     */           case 8: 
/*  831:     */           case 10: 
/*  832:     */           case 13: 
/*  833:     */           case 17: 
/*  834: 716 */             if ((0x0 & l) != 0L) {
/*  835: 717 */               jjCheckNAdd(6);
/*  836:     */             }
/*  837:     */             break;
/*  838:     */           case 9: 
/*  839: 720 */             if ((0x0 & l) != 0L) {
/*  840: 721 */               this.jjstateSet[(this.jjnewStateCnt++)] = 10;
/*  841:     */             }
/*  842:     */             break;
/*  843:     */           case 11: 
/*  844: 724 */             if ((0x0 & l) != 0L) {
/*  845: 725 */               this.jjstateSet[(this.jjnewStateCnt++)] = 12;
/*  846:     */             }
/*  847:     */             break;
/*  848:     */           case 12: 
/*  849: 728 */             if ((0x0 & l) != 0L) {
/*  850: 729 */               this.jjstateSet[(this.jjnewStateCnt++)] = 13;
/*  851:     */             }
/*  852:     */             break;
/*  853:     */           case 14: 
/*  854: 732 */             if ((0x0 & l) != 0L) {
/*  855: 733 */               this.jjstateSet[(this.jjnewStateCnt++)] = 15;
/*  856:     */             }
/*  857:     */             break;
/*  858:     */           case 15: 
/*  859: 736 */             if ((0x0 & l) != 0L) {
/*  860: 737 */               this.jjstateSet[(this.jjnewStateCnt++)] = 16;
/*  861:     */             }
/*  862:     */             break;
/*  863:     */           case 16: 
/*  864: 740 */             if ((0x0 & l) != 0L) {
/*  865: 741 */               this.jjstateSet[(this.jjnewStateCnt++)] = 17;
/*  866:     */             }
/*  867:     */             break;
/*  868:     */           case 18: 
/*  869: 744 */             if (this.curChar == '"') {
/*  870: 745 */               jjCheckNAddStates(100, 102);
/*  871:     */             }
/*  872:     */             break;
/*  873:     */           case 19: 
/*  874: 748 */             if ((0x200 & l) != 0L) {
/*  875: 749 */               jjCheckNAddStates(100, 102);
/*  876:     */             }
/*  877:     */             break;
/*  878:     */           case 20: 
/*  879: 752 */             if ((this.curChar == '"') && (kind > 20)) {
/*  880: 753 */               kind = 20;
/*  881:     */             }
/*  882:     */             break;
/*  883:     */           case 22: 
/*  884: 756 */             if ((0x3400 & l) != 0L) {
/*  885: 757 */               jjCheckNAddStates(100, 102);
/*  886:     */             }
/*  887:     */             break;
/*  888:     */           case 23: 
/*  889: 760 */             if (this.curChar == '\n') {
/*  890: 761 */               jjCheckNAddStates(100, 102);
/*  891:     */             }
/*  892:     */             break;
/*  893:     */           case 24: 
/*  894: 764 */             if (this.curChar == '\r') {
/*  895: 765 */               this.jjstateSet[(this.jjnewStateCnt++)] = 23;
/*  896:     */             }
/*  897:     */             break;
/*  898:     */           case 25: 
/*  899: 768 */             if ((0x0 & l) != 0L) {
/*  900: 769 */               jjCheckNAddStates(100, 102);
/*  901:     */             }
/*  902:     */             break;
/*  903:     */           case 26: 
/*  904: 772 */             if ((0x0 & l) != 0L) {
/*  905: 773 */               jjCheckNAddStates(117, 125);
/*  906:     */             }
/*  907:     */             break;
/*  908:     */           case 27: 
/*  909: 776 */             if ((0x0 & l) != 0L) {
/*  910: 777 */               jjCheckNAddStates(126, 129);
/*  911:     */             }
/*  912:     */             break;
/*  913:     */           case 28: 
/*  914: 780 */             if ((0x3600 & l) != 0L) {
/*  915: 781 */               jjCheckNAddStates(100, 102);
/*  916:     */             }
/*  917:     */             break;
/*  918:     */           case 29: 
/*  919:     */           case 31: 
/*  920:     */           case 34: 
/*  921:     */           case 38: 
/*  922: 787 */             if ((0x0 & l) != 0L) {
/*  923: 788 */               jjCheckNAdd(27);
/*  924:     */             }
/*  925:     */             break;
/*  926:     */           case 30: 
/*  927: 791 */             if ((0x0 & l) != 0L) {
/*  928: 792 */               this.jjstateSet[(this.jjnewStateCnt++)] = 31;
/*  929:     */             }
/*  930:     */             break;
/*  931:     */           case 32: 
/*  932: 795 */             if ((0x0 & l) != 0L) {
/*  933: 796 */               this.jjstateSet[(this.jjnewStateCnt++)] = 33;
/*  934:     */             }
/*  935:     */             break;
/*  936:     */           case 33: 
/*  937: 799 */             if ((0x0 & l) != 0L) {
/*  938: 800 */               this.jjstateSet[(this.jjnewStateCnt++)] = 34;
/*  939:     */             }
/*  940:     */             break;
/*  941:     */           case 35: 
/*  942: 803 */             if ((0x0 & l) != 0L) {
/*  943: 804 */               this.jjstateSet[(this.jjnewStateCnt++)] = 36;
/*  944:     */             }
/*  945:     */             break;
/*  946:     */           case 36: 
/*  947: 807 */             if ((0x0 & l) != 0L) {
/*  948: 808 */               this.jjstateSet[(this.jjnewStateCnt++)] = 37;
/*  949:     */             }
/*  950:     */             break;
/*  951:     */           case 37: 
/*  952: 811 */             if ((0x0 & l) != 0L) {
/*  953: 812 */               this.jjstateSet[(this.jjnewStateCnt++)] = 38;
/*  954:     */             }
/*  955:     */             break;
/*  956:     */           case 39: 
/*  957: 815 */             if (this.curChar == '\'') {
/*  958: 816 */               jjCheckNAddStates(97, 99);
/*  959:     */             }
/*  960:     */             break;
/*  961:     */           case 40: 
/*  962: 819 */             if ((0x200 & l) != 0L) {
/*  963: 820 */               jjCheckNAddStates(97, 99);
/*  964:     */             }
/*  965:     */             break;
/*  966:     */           case 41: 
/*  967: 823 */             if ((this.curChar == '\'') && (kind > 20)) {
/*  968: 824 */               kind = 20;
/*  969:     */             }
/*  970:     */             break;
/*  971:     */           case 43: 
/*  972: 827 */             if ((0x3400 & l) != 0L) {
/*  973: 828 */               jjCheckNAddStates(97, 99);
/*  974:     */             }
/*  975:     */             break;
/*  976:     */           case 44: 
/*  977: 831 */             if (this.curChar == '\n') {
/*  978: 832 */               jjCheckNAddStates(97, 99);
/*  979:     */             }
/*  980:     */             break;
/*  981:     */           case 45: 
/*  982: 835 */             if (this.curChar == '\r') {
/*  983: 836 */               this.jjstateSet[(this.jjnewStateCnt++)] = 44;
/*  984:     */             }
/*  985:     */             break;
/*  986:     */           case 46: 
/*  987: 839 */             if ((0x0 & l) != 0L) {
/*  988: 840 */               jjCheckNAddStates(97, 99);
/*  989:     */             }
/*  990:     */             break;
/*  991:     */           case 47: 
/*  992: 843 */             if ((0x0 & l) != 0L) {
/*  993: 844 */               jjCheckNAddStates(130, 138);
/*  994:     */             }
/*  995:     */             break;
/*  996:     */           case 48: 
/*  997: 847 */             if ((0x0 & l) != 0L) {
/*  998: 848 */               jjCheckNAddStates(139, 142);
/*  999:     */             }
/* 1000:     */             break;
/* 1001:     */           case 49: 
/* 1002: 851 */             if ((0x3600 & l) != 0L) {
/* 1003: 852 */               jjCheckNAddStates(97, 99);
/* 1004:     */             }
/* 1005:     */             break;
/* 1006:     */           case 50: 
/* 1007:     */           case 52: 
/* 1008:     */           case 55: 
/* 1009:     */           case 59: 
/* 1010: 858 */             if ((0x0 & l) != 0L) {
/* 1011: 859 */               jjCheckNAdd(48);
/* 1012:     */             }
/* 1013:     */             break;
/* 1014:     */           case 51: 
/* 1015: 862 */             if ((0x0 & l) != 0L) {
/* 1016: 863 */               this.jjstateSet[(this.jjnewStateCnt++)] = 52;
/* 1017:     */             }
/* 1018:     */             break;
/* 1019:     */           case 53: 
/* 1020: 866 */             if ((0x0 & l) != 0L) {
/* 1021: 867 */               this.jjstateSet[(this.jjnewStateCnt++)] = 54;
/* 1022:     */             }
/* 1023:     */             break;
/* 1024:     */           case 54: 
/* 1025: 870 */             if ((0x0 & l) != 0L) {
/* 1026: 871 */               this.jjstateSet[(this.jjnewStateCnt++)] = 55;
/* 1027:     */             }
/* 1028:     */             break;
/* 1029:     */           case 56: 
/* 1030: 874 */             if ((0x0 & l) != 0L) {
/* 1031: 875 */               this.jjstateSet[(this.jjnewStateCnt++)] = 57;
/* 1032:     */             }
/* 1033:     */             break;
/* 1034:     */           case 57: 
/* 1035: 878 */             if ((0x0 & l) != 0L) {
/* 1036: 879 */               this.jjstateSet[(this.jjnewStateCnt++)] = 58;
/* 1037:     */             }
/* 1038:     */             break;
/* 1039:     */           case 58: 
/* 1040: 882 */             if ((0x0 & l) != 0L) {
/* 1041: 883 */               this.jjstateSet[(this.jjnewStateCnt++)] = 59;
/* 1042:     */             }
/* 1043:     */             break;
/* 1044:     */           case 64: 
/* 1045: 886 */             if ((0x0 & l) != 0L)
/* 1046:     */             {
/* 1047: 888 */               if (kind > 33) {
/* 1048: 889 */                 kind = 33;
/* 1049:     */               }
/* 1050: 890 */               jjCheckNAddTwoStates(62, 63);
/* 1051:     */             }
/* 1052: 891 */             break;
/* 1053:     */           case 65: 
/* 1054: 893 */             if ((0x0 & l) != 0L)
/* 1055:     */             {
/* 1056: 895 */               if (kind > 33) {
/* 1057: 896 */                 kind = 33;
/* 1058:     */               }
/* 1059: 897 */               jjCheckNAddStates(143, 150);
/* 1060:     */             }
/* 1061: 898 */             break;
/* 1062:     */           case 66: 
/* 1063: 900 */             if ((0x0 & l) != 0L)
/* 1064:     */             {
/* 1065: 902 */               if (kind > 33) {
/* 1066: 903 */                 kind = 33;
/* 1067:     */               }
/* 1068: 904 */               jjCheckNAddStates(151, 153);
/* 1069:     */             }
/* 1070: 905 */             break;
/* 1071:     */           case 67: 
/* 1072: 907 */             if ((0x3600 & l) != 0L)
/* 1073:     */             {
/* 1074: 909 */               if (kind > 33) {
/* 1075: 910 */                 kind = 33;
/* 1076:     */               }
/* 1077: 911 */               jjCheckNAddTwoStates(62, 63);
/* 1078:     */             }
/* 1079: 912 */             break;
/* 1080:     */           case 68: 
/* 1081:     */           case 70: 
/* 1082:     */           case 73: 
/* 1083:     */           case 77: 
/* 1084: 917 */             if ((0x0 & l) != 0L) {
/* 1085: 918 */               jjCheckNAdd(66);
/* 1086:     */             }
/* 1087:     */             break;
/* 1088:     */           case 69: 
/* 1089: 921 */             if ((0x0 & l) != 0L) {
/* 1090: 922 */               this.jjstateSet[(this.jjnewStateCnt++)] = 70;
/* 1091:     */             }
/* 1092:     */             break;
/* 1093:     */           case 71: 
/* 1094: 925 */             if ((0x0 & l) != 0L) {
/* 1095: 926 */               this.jjstateSet[(this.jjnewStateCnt++)] = 72;
/* 1096:     */             }
/* 1097:     */             break;
/* 1098:     */           case 72: 
/* 1099: 929 */             if ((0x0 & l) != 0L) {
/* 1100: 930 */               this.jjstateSet[(this.jjnewStateCnt++)] = 73;
/* 1101:     */             }
/* 1102:     */             break;
/* 1103:     */           case 74: 
/* 1104: 933 */             if ((0x0 & l) != 0L) {
/* 1105: 934 */               this.jjstateSet[(this.jjnewStateCnt++)] = 75;
/* 1106:     */             }
/* 1107:     */             break;
/* 1108:     */           case 75: 
/* 1109: 937 */             if ((0x0 & l) != 0L) {
/* 1110: 938 */               this.jjstateSet[(this.jjnewStateCnt++)] = 76;
/* 1111:     */             }
/* 1112:     */             break;
/* 1113:     */           case 76: 
/* 1114: 941 */             if ((0x0 & l) != 0L) {
/* 1115: 942 */               this.jjstateSet[(this.jjnewStateCnt++)] = 77;
/* 1116:     */             }
/* 1117:     */             break;
/* 1118:     */           case 79: 
/* 1119: 945 */             if ((0x0 & l) != 0L)
/* 1120:     */             {
/* 1121: 947 */               if (kind > 33) {
/* 1122: 948 */                 kind = 33;
/* 1123:     */               }
/* 1124: 949 */               jjCheckNAddStates(154, 161);
/* 1125:     */             }
/* 1126: 950 */             break;
/* 1127:     */           case 80: 
/* 1128: 952 */             if ((0x0 & l) != 0L)
/* 1129:     */             {
/* 1130: 954 */               if (kind > 33) {
/* 1131: 955 */                 kind = 33;
/* 1132:     */               }
/* 1133: 956 */               jjCheckNAddStates(162, 164);
/* 1134:     */             }
/* 1135: 957 */             break;
/* 1136:     */           case 81: 
/* 1137:     */           case 83: 
/* 1138:     */           case 86: 
/* 1139:     */           case 90: 
/* 1140: 962 */             if ((0x0 & l) != 0L) {
/* 1141: 963 */               jjCheckNAdd(80);
/* 1142:     */             }
/* 1143:     */             break;
/* 1144:     */           case 82: 
/* 1145: 966 */             if ((0x0 & l) != 0L) {
/* 1146: 967 */               this.jjstateSet[(this.jjnewStateCnt++)] = 83;
/* 1147:     */             }
/* 1148:     */             break;
/* 1149:     */           case 84: 
/* 1150: 970 */             if ((0x0 & l) != 0L) {
/* 1151: 971 */               this.jjstateSet[(this.jjnewStateCnt++)] = 85;
/* 1152:     */             }
/* 1153:     */             break;
/* 1154:     */           case 85: 
/* 1155: 974 */             if ((0x0 & l) != 0L) {
/* 1156: 975 */               this.jjstateSet[(this.jjnewStateCnt++)] = 86;
/* 1157:     */             }
/* 1158:     */             break;
/* 1159:     */           case 87: 
/* 1160: 978 */             if ((0x0 & l) != 0L) {
/* 1161: 979 */               this.jjstateSet[(this.jjnewStateCnt++)] = 88;
/* 1162:     */             }
/* 1163:     */             break;
/* 1164:     */           case 88: 
/* 1165: 982 */             if ((0x0 & l) != 0L) {
/* 1166: 983 */               this.jjstateSet[(this.jjnewStateCnt++)] = 89;
/* 1167:     */             }
/* 1168:     */             break;
/* 1169:     */           case 89: 
/* 1170: 986 */             if ((0x0 & l) != 0L) {
/* 1171: 987 */               this.jjstateSet[(this.jjnewStateCnt++)] = 90;
/* 1172:     */             }
/* 1173:     */             break;
/* 1174:     */           case 91: 
/* 1175: 990 */             if (this.curChar == '!') {
/* 1176: 991 */               jjCheckNAddTwoStates(92, 101);
/* 1177:     */             }
/* 1178:     */             break;
/* 1179:     */           case 92: 
/* 1180: 994 */             if ((0x3600 & l) != 0L) {
/* 1181: 995 */               jjCheckNAddTwoStates(92, 101);
/* 1182:     */             }
/* 1183:     */             break;
/* 1184:     */           case 103: 
/* 1185: 998 */             if (this.curChar == '(') {
/* 1186: 999 */               jjCheckNAddStates(165, 170);
/* 1187:     */             }
/* 1188:     */             break;
/* 1189:     */           case 104: 
/* 1190:1002 */             if ((0x0 & l) != 0L) {
/* 1191:1003 */               jjCheckNAddStates(171, 174);
/* 1192:     */             }
/* 1193:     */             break;
/* 1194:     */           case 105: 
/* 1195:1006 */             if ((0x3600 & l) != 0L) {
/* 1196:1007 */               jjCheckNAddTwoStates(105, 106);
/* 1197:     */             }
/* 1198:     */             break;
/* 1199:     */           case 106: 
/* 1200:1010 */             if ((this.curChar == ')') && (kind > 23)) {
/* 1201:1011 */               kind = 23;
/* 1202:     */             }
/* 1203:     */             break;
/* 1204:     */           case 108: 
/* 1205:1014 */             if ((0x0 & l) != 0L) {
/* 1206:1015 */               jjCheckNAddStates(171, 174);
/* 1207:     */             }
/* 1208:     */             break;
/* 1209:     */           case 109: 
/* 1210:1018 */             if ((0x0 & l) != 0L) {
/* 1211:1019 */               jjCheckNAddStates(175, 183);
/* 1212:     */             }
/* 1213:     */             break;
/* 1214:     */           case 110: 
/* 1215:1022 */             if ((0x0 & l) != 0L) {
/* 1216:1023 */               jjCheckNAddStates(184, 187);
/* 1217:     */             }
/* 1218:     */             break;
/* 1219:     */           case 111: 
/* 1220:1026 */             if ((0x3600 & l) != 0L) {
/* 1221:1027 */               jjCheckNAddStates(171, 174);
/* 1222:     */             }
/* 1223:     */             break;
/* 1224:     */           case 112: 
/* 1225:     */           case 114: 
/* 1226:     */           case 117: 
/* 1227:     */           case 121: 
/* 1228:1033 */             if ((0x0 & l) != 0L) {
/* 1229:1034 */               jjCheckNAdd(110);
/* 1230:     */             }
/* 1231:     */             break;
/* 1232:     */           case 113: 
/* 1233:1037 */             if ((0x0 & l) != 0L) {
/* 1234:1038 */               this.jjstateSet[(this.jjnewStateCnt++)] = 114;
/* 1235:     */             }
/* 1236:     */             break;
/* 1237:     */           case 115: 
/* 1238:1041 */             if ((0x0 & l) != 0L) {
/* 1239:1042 */               this.jjstateSet[(this.jjnewStateCnt++)] = 116;
/* 1240:     */             }
/* 1241:     */             break;
/* 1242:     */           case 116: 
/* 1243:1045 */             if ((0x0 & l) != 0L) {
/* 1244:1046 */               this.jjstateSet[(this.jjnewStateCnt++)] = 117;
/* 1245:     */             }
/* 1246:     */             break;
/* 1247:     */           case 118: 
/* 1248:1049 */             if ((0x0 & l) != 0L) {
/* 1249:1050 */               this.jjstateSet[(this.jjnewStateCnt++)] = 119;
/* 1250:     */             }
/* 1251:     */             break;
/* 1252:     */           case 119: 
/* 1253:1053 */             if ((0x0 & l) != 0L) {
/* 1254:1054 */               this.jjstateSet[(this.jjnewStateCnt++)] = 120;
/* 1255:     */             }
/* 1256:     */             break;
/* 1257:     */           case 120: 
/* 1258:1057 */             if ((0x0 & l) != 0L) {
/* 1259:1058 */               this.jjstateSet[(this.jjnewStateCnt++)] = 121;
/* 1260:     */             }
/* 1261:     */             break;
/* 1262:     */           case 122: 
/* 1263:1061 */             if (this.curChar == '\'') {
/* 1264:1062 */               jjCheckNAddStates(188, 190);
/* 1265:     */             }
/* 1266:     */             break;
/* 1267:     */           case 123: 
/* 1268:1065 */             if ((0x200 & l) != 0L) {
/* 1269:1066 */               jjCheckNAddStates(188, 190);
/* 1270:     */             }
/* 1271:     */             break;
/* 1272:     */           case 124: 
/* 1273:1069 */             if (this.curChar == '\'') {
/* 1274:1070 */               jjCheckNAddTwoStates(105, 106);
/* 1275:     */             }
/* 1276:     */             break;
/* 1277:     */           case 126: 
/* 1278:1073 */             if ((0x3400 & l) != 0L) {
/* 1279:1074 */               jjCheckNAddStates(188, 190);
/* 1280:     */             }
/* 1281:     */             break;
/* 1282:     */           case 127: 
/* 1283:1077 */             if (this.curChar == '\n') {
/* 1284:1078 */               jjCheckNAddStates(188, 190);
/* 1285:     */             }
/* 1286:     */             break;
/* 1287:     */           case 128: 
/* 1288:1081 */             if (this.curChar == '\r') {
/* 1289:1082 */               this.jjstateSet[(this.jjnewStateCnt++)] = 127;
/* 1290:     */             }
/* 1291:     */             break;
/* 1292:     */           case 129: 
/* 1293:1085 */             if ((0x0 & l) != 0L) {
/* 1294:1086 */               jjCheckNAddStates(188, 190);
/* 1295:     */             }
/* 1296:     */             break;
/* 1297:     */           case 130: 
/* 1298:1089 */             if ((0x0 & l) != 0L) {
/* 1299:1090 */               jjCheckNAddStates(191, 199);
/* 1300:     */             }
/* 1301:     */             break;
/* 1302:     */           case 131: 
/* 1303:1093 */             if ((0x0 & l) != 0L) {
/* 1304:1094 */               jjCheckNAddStates(200, 203);
/* 1305:     */             }
/* 1306:     */             break;
/* 1307:     */           case 132: 
/* 1308:1097 */             if ((0x3600 & l) != 0L) {
/* 1309:1098 */               jjCheckNAddStates(188, 190);
/* 1310:     */             }
/* 1311:     */             break;
/* 1312:     */           case 133: 
/* 1313:     */           case 135: 
/* 1314:     */           case 138: 
/* 1315:     */           case 142: 
/* 1316:1104 */             if ((0x0 & l) != 0L) {
/* 1317:1105 */               jjCheckNAdd(131);
/* 1318:     */             }
/* 1319:     */             break;
/* 1320:     */           case 134: 
/* 1321:1108 */             if ((0x0 & l) != 0L) {
/* 1322:1109 */               this.jjstateSet[(this.jjnewStateCnt++)] = 135;
/* 1323:     */             }
/* 1324:     */             break;
/* 1325:     */           case 136: 
/* 1326:1112 */             if ((0x0 & l) != 0L) {
/* 1327:1113 */               this.jjstateSet[(this.jjnewStateCnt++)] = 137;
/* 1328:     */             }
/* 1329:     */             break;
/* 1330:     */           case 137: 
/* 1331:1116 */             if ((0x0 & l) != 0L) {
/* 1332:1117 */               this.jjstateSet[(this.jjnewStateCnt++)] = 138;
/* 1333:     */             }
/* 1334:     */             break;
/* 1335:     */           case 139: 
/* 1336:1120 */             if ((0x0 & l) != 0L) {
/* 1337:1121 */               this.jjstateSet[(this.jjnewStateCnt++)] = 140;
/* 1338:     */             }
/* 1339:     */             break;
/* 1340:     */           case 140: 
/* 1341:1124 */             if ((0x0 & l) != 0L) {
/* 1342:1125 */               this.jjstateSet[(this.jjnewStateCnt++)] = 141;
/* 1343:     */             }
/* 1344:     */             break;
/* 1345:     */           case 141: 
/* 1346:1128 */             if ((0x0 & l) != 0L) {
/* 1347:1129 */               this.jjstateSet[(this.jjnewStateCnt++)] = 142;
/* 1348:     */             }
/* 1349:     */             break;
/* 1350:     */           case 143: 
/* 1351:1132 */             if (this.curChar == '"') {
/* 1352:1133 */               jjCheckNAddStates(204, 206);
/* 1353:     */             }
/* 1354:     */             break;
/* 1355:     */           case 144: 
/* 1356:1136 */             if ((0x200 & l) != 0L) {
/* 1357:1137 */               jjCheckNAddStates(204, 206);
/* 1358:     */             }
/* 1359:     */             break;
/* 1360:     */           case 145: 
/* 1361:1140 */             if (this.curChar == '"') {
/* 1362:1141 */               jjCheckNAddTwoStates(105, 106);
/* 1363:     */             }
/* 1364:     */             break;
/* 1365:     */           case 147: 
/* 1366:1144 */             if ((0x3400 & l) != 0L) {
/* 1367:1145 */               jjCheckNAddStates(204, 206);
/* 1368:     */             }
/* 1369:     */             break;
/* 1370:     */           case 148: 
/* 1371:1148 */             if (this.curChar == '\n') {
/* 1372:1149 */               jjCheckNAddStates(204, 206);
/* 1373:     */             }
/* 1374:     */             break;
/* 1375:     */           case 149: 
/* 1376:1152 */             if (this.curChar == '\r') {
/* 1377:1153 */               this.jjstateSet[(this.jjnewStateCnt++)] = 148;
/* 1378:     */             }
/* 1379:     */             break;
/* 1380:     */           case 150: 
/* 1381:1156 */             if ((0x0 & l) != 0L) {
/* 1382:1157 */               jjCheckNAddStates(204, 206);
/* 1383:     */             }
/* 1384:     */             break;
/* 1385:     */           case 151: 
/* 1386:1160 */             if ((0x0 & l) != 0L) {
/* 1387:1161 */               jjCheckNAddStates(207, 215);
/* 1388:     */             }
/* 1389:     */             break;
/* 1390:     */           case 152: 
/* 1391:1164 */             if ((0x0 & l) != 0L) {
/* 1392:1165 */               jjCheckNAddStates(216, 219);
/* 1393:     */             }
/* 1394:     */             break;
/* 1395:     */           case 153: 
/* 1396:1168 */             if ((0x3600 & l) != 0L) {
/* 1397:1169 */               jjCheckNAddStates(204, 206);
/* 1398:     */             }
/* 1399:     */             break;
/* 1400:     */           case 154: 
/* 1401:     */           case 156: 
/* 1402:     */           case 159: 
/* 1403:     */           case 163: 
/* 1404:1175 */             if ((0x0 & l) != 0L) {
/* 1405:1176 */               jjCheckNAdd(152);
/* 1406:     */             }
/* 1407:     */             break;
/* 1408:     */           case 155: 
/* 1409:1179 */             if ((0x0 & l) != 0L) {
/* 1410:1180 */               this.jjstateSet[(this.jjnewStateCnt++)] = 156;
/* 1411:     */             }
/* 1412:     */             break;
/* 1413:     */           case 157: 
/* 1414:1183 */             if ((0x0 & l) != 0L) {
/* 1415:1184 */               this.jjstateSet[(this.jjnewStateCnt++)] = 158;
/* 1416:     */             }
/* 1417:     */             break;
/* 1418:     */           case 158: 
/* 1419:1187 */             if ((0x0 & l) != 0L) {
/* 1420:1188 */               this.jjstateSet[(this.jjnewStateCnt++)] = 159;
/* 1421:     */             }
/* 1422:     */             break;
/* 1423:     */           case 160: 
/* 1424:1191 */             if ((0x0 & l) != 0L) {
/* 1425:1192 */               this.jjstateSet[(this.jjnewStateCnt++)] = 161;
/* 1426:     */             }
/* 1427:     */             break;
/* 1428:     */           case 161: 
/* 1429:1195 */             if ((0x0 & l) != 0L) {
/* 1430:1196 */               this.jjstateSet[(this.jjnewStateCnt++)] = 162;
/* 1431:     */             }
/* 1432:     */             break;
/* 1433:     */           case 162: 
/* 1434:1199 */             if ((0x0 & l) != 0L) {
/* 1435:1200 */               this.jjstateSet[(this.jjnewStateCnt++)] = 163;
/* 1436:     */             }
/* 1437:     */             break;
/* 1438:     */           case 164: 
/* 1439:1203 */             if ((0x3600 & l) != 0L) {
/* 1440:1204 */               jjCheckNAddStates(220, 226);
/* 1441:     */             }
/* 1442:     */             break;
/* 1443:     */           case 167: 
/* 1444:1207 */             if (this.curChar == '+') {
/* 1445:1208 */               jjCheckNAddStates(227, 229);
/* 1446:     */             }
/* 1447:     */             break;
/* 1448:     */           case 168: 
/* 1449:     */           case 197: 
/* 1450:1212 */             if ((this.curChar == '?') && (kind > 59)) {
/* 1451:1213 */               kind = 59;
/* 1452:     */             }
/* 1453:     */             break;
/* 1454:     */           case 169: 
/* 1455:1216 */             if ((0x0 & l) != 0L)
/* 1456:     */             {
/* 1457:1218 */               if (kind > 59) {
/* 1458:1219 */                 kind = 59;
/* 1459:     */               }
/* 1460:1220 */               jjCheckNAddStates(230, 238);
/* 1461:     */             }
/* 1462:1221 */             break;
/* 1463:     */           case 170: 
/* 1464:1223 */             if ((0x0 & l) != 0L) {
/* 1465:1224 */               jjCheckNAdd(171);
/* 1466:     */             }
/* 1467:     */             break;
/* 1468:     */           case 171: 
/* 1469:1227 */             if (this.curChar == '-') {
/* 1470:1228 */               this.jjstateSet[(this.jjnewStateCnt++)] = 172;
/* 1471:     */             }
/* 1472:     */             break;
/* 1473:     */           case 172: 
/* 1474:1231 */             if ((0x0 & l) != 0L)
/* 1475:     */             {
/* 1476:1233 */               if (kind > 59) {
/* 1477:1234 */                 kind = 59;
/* 1478:     */               }
/* 1479:1235 */               jjCheckNAddStates(239, 243);
/* 1480:     */             }
/* 1481:1236 */             break;
/* 1482:     */           case 173: 
/* 1483:1238 */             if (((0x0 & l) != 0L) && (kind > 59)) {
/* 1484:1239 */               kind = 59;
/* 1485:     */             }
/* 1486:     */             break;
/* 1487:     */           case 174: 
/* 1488:     */           case 176: 
/* 1489:     */           case 179: 
/* 1490:     */           case 183: 
/* 1491:1245 */             if ((0x0 & l) != 0L) {
/* 1492:1246 */               jjCheckNAdd(173);
/* 1493:     */             }
/* 1494:     */             break;
/* 1495:     */           case 175: 
/* 1496:1249 */             if ((0x0 & l) != 0L) {
/* 1497:1250 */               this.jjstateSet[(this.jjnewStateCnt++)] = 176;
/* 1498:     */             }
/* 1499:     */             break;
/* 1500:     */           case 177: 
/* 1501:1253 */             if ((0x0 & l) != 0L) {
/* 1502:1254 */               this.jjstateSet[(this.jjnewStateCnt++)] = 178;
/* 1503:     */             }
/* 1504:     */             break;
/* 1505:     */           case 178: 
/* 1506:1257 */             if ((0x0 & l) != 0L) {
/* 1507:1258 */               this.jjstateSet[(this.jjnewStateCnt++)] = 179;
/* 1508:     */             }
/* 1509:     */             break;
/* 1510:     */           case 180: 
/* 1511:1261 */             if ((0x0 & l) != 0L) {
/* 1512:1262 */               this.jjstateSet[(this.jjnewStateCnt++)] = 181;
/* 1513:     */             }
/* 1514:     */             break;
/* 1515:     */           case 181: 
/* 1516:1265 */             if ((0x0 & l) != 0L) {
/* 1517:1266 */               this.jjstateSet[(this.jjnewStateCnt++)] = 182;
/* 1518:     */             }
/* 1519:     */             break;
/* 1520:     */           case 182: 
/* 1521:1269 */             if ((0x0 & l) != 0L) {
/* 1522:1270 */               this.jjstateSet[(this.jjnewStateCnt++)] = 183;
/* 1523:     */             }
/* 1524:     */             break;
/* 1525:     */           case 184: 
/* 1526:     */           case 186: 
/* 1527:     */           case 189: 
/* 1528:     */           case 193: 
/* 1529:1276 */             if ((0x0 & l) != 0L) {
/* 1530:1277 */               jjCheckNAdd(170);
/* 1531:     */             }
/* 1532:     */             break;
/* 1533:     */           case 185: 
/* 1534:1280 */             if ((0x0 & l) != 0L) {
/* 1535:1281 */               this.jjstateSet[(this.jjnewStateCnt++)] = 186;
/* 1536:     */             }
/* 1537:     */             break;
/* 1538:     */           case 187: 
/* 1539:1284 */             if ((0x0 & l) != 0L) {
/* 1540:1285 */               this.jjstateSet[(this.jjnewStateCnt++)] = 188;
/* 1541:     */             }
/* 1542:     */             break;
/* 1543:     */           case 188: 
/* 1544:1288 */             if ((0x0 & l) != 0L) {
/* 1545:1289 */               this.jjstateSet[(this.jjnewStateCnt++)] = 189;
/* 1546:     */             }
/* 1547:     */             break;
/* 1548:     */           case 190: 
/* 1549:1292 */             if ((0x0 & l) != 0L) {
/* 1550:1293 */               this.jjstateSet[(this.jjnewStateCnt++)] = 191;
/* 1551:     */             }
/* 1552:     */             break;
/* 1553:     */           case 191: 
/* 1554:1296 */             if ((0x0 & l) != 0L) {
/* 1555:1297 */               this.jjstateSet[(this.jjnewStateCnt++)] = 192;
/* 1556:     */             }
/* 1557:     */             break;
/* 1558:     */           case 192: 
/* 1559:1300 */             if ((0x0 & l) != 0L) {
/* 1560:1301 */               this.jjstateSet[(this.jjnewStateCnt++)] = 193;
/* 1561:     */             }
/* 1562:     */             break;
/* 1563:     */           case 194: 
/* 1564:1304 */             if ((0x0 & l) != 0L)
/* 1565:     */             {
/* 1566:1306 */               if (kind > 59) {
/* 1567:1307 */                 kind = 59;
/* 1568:     */               }
/* 1569:1308 */               jjCheckNAddStates(244, 246);
/* 1570:     */             }
/* 1571:1309 */             break;
/* 1572:     */           case 195: 
/* 1573:1311 */             if ((0x0 & l) != 0L)
/* 1574:     */             {
/* 1575:1313 */               if (kind > 59) {
/* 1576:1314 */                 kind = 59;
/* 1577:     */               }
/* 1578:1315 */               jjCheckNAddStates(247, 249);
/* 1579:     */             }
/* 1580:1316 */             break;
/* 1581:     */           case 196: 
/* 1582:1318 */             if ((0x0 & l) != 0L)
/* 1583:     */             {
/* 1584:1320 */               if (kind > 59) {
/* 1585:1321 */                 kind = 59;
/* 1586:     */               }
/* 1587:1322 */               jjCheckNAddStates(250, 252);
/* 1588:     */             }
/* 1589:1323 */             break;
/* 1590:     */           case 198: 
/* 1591:     */           case 201: 
/* 1592:     */           case 203: 
/* 1593:     */           case 204: 
/* 1594:     */           case 207: 
/* 1595:     */           case 208: 
/* 1596:     */           case 210: 
/* 1597:     */           case 214: 
/* 1598:     */           case 218: 
/* 1599:     */           case 221: 
/* 1600:     */           case 223: 
/* 1601:1335 */             if (this.curChar == '?') {
/* 1602:1336 */               jjCheckNAdd(197);
/* 1603:     */             }
/* 1604:     */             break;
/* 1605:     */           case 199: 
/* 1606:1339 */             if ((0x0 & l) != 0L)
/* 1607:     */             {
/* 1608:1341 */               if (kind > 59) {
/* 1609:1342 */                 kind = 59;
/* 1610:     */               }
/* 1611:1343 */               jjCheckNAddTwoStates(168, 173);
/* 1612:     */             }
/* 1613:1344 */             break;
/* 1614:     */           case 200: 
/* 1615:1346 */             if (this.curChar == '?') {
/* 1616:1347 */               jjCheckNAddTwoStates(197, 201);
/* 1617:     */             }
/* 1618:     */             break;
/* 1619:     */           case 202: 
/* 1620:1350 */             if (this.curChar == '?') {
/* 1621:1351 */               jjCheckNAddStates(253, 255);
/* 1622:     */             }
/* 1623:     */             break;
/* 1624:     */           case 205: 
/* 1625:1354 */             if (this.curChar == '?') {
/* 1626:1355 */               this.jjstateSet[(this.jjnewStateCnt++)] = 204;
/* 1627:     */             }
/* 1628:     */             break;
/* 1629:     */           case 206: 
/* 1630:1358 */             if (this.curChar == '?') {
/* 1631:1359 */               jjCheckNAddStates(256, 259);
/* 1632:     */             }
/* 1633:     */             break;
/* 1634:     */           case 209: 
/* 1635:1362 */             if (this.curChar == '?') {
/* 1636:1363 */               this.jjstateSet[(this.jjnewStateCnt++)] = 208;
/* 1637:     */             }
/* 1638:     */             break;
/* 1639:     */           case 211: 
/* 1640:1366 */             if (this.curChar == '?') {
/* 1641:1367 */               this.jjstateSet[(this.jjnewStateCnt++)] = 210;
/* 1642:     */             }
/* 1643:     */             break;
/* 1644:     */           case 212: 
/* 1645:1370 */             if (this.curChar == '?') {
/* 1646:1371 */               this.jjstateSet[(this.jjnewStateCnt++)] = 211;
/* 1647:     */             }
/* 1648:     */             break;
/* 1649:     */           case 213: 
/* 1650:1374 */             if (this.curChar == '?') {
/* 1651:1375 */               jjCheckNAddStates(260, 264);
/* 1652:     */             }
/* 1653:     */             break;
/* 1654:     */           case 215: 
/* 1655:1378 */             if (this.curChar == '?') {
/* 1656:1379 */               this.jjstateSet[(this.jjnewStateCnt++)] = 214;
/* 1657:     */             }
/* 1658:     */             break;
/* 1659:     */           case 216: 
/* 1660:1382 */             if (this.curChar == '?') {
/* 1661:1383 */               this.jjstateSet[(this.jjnewStateCnt++)] = 215;
/* 1662:     */             }
/* 1663:     */             break;
/* 1664:     */           case 217: 
/* 1665:1386 */             if (this.curChar == '?') {
/* 1666:1387 */               this.jjstateSet[(this.jjnewStateCnt++)] = 216;
/* 1667:     */             }
/* 1668:     */             break;
/* 1669:     */           case 219: 
/* 1670:1390 */             if (this.curChar == '?') {
/* 1671:1391 */               this.jjstateSet[(this.jjnewStateCnt++)] = 218;
/* 1672:     */             }
/* 1673:     */             break;
/* 1674:     */           case 220: 
/* 1675:1394 */             if (this.curChar == '?') {
/* 1676:1395 */               this.jjstateSet[(this.jjnewStateCnt++)] = 219;
/* 1677:     */             }
/* 1678:     */             break;
/* 1679:     */           case 222: 
/* 1680:1398 */             if (this.curChar == '?') {
/* 1681:1399 */               this.jjstateSet[(this.jjnewStateCnt++)] = 221;
/* 1682:     */             }
/* 1683:     */             break;
/* 1684:     */           case 224: 
/* 1685:1402 */             if (this.curChar == '.') {
/* 1686:1403 */               jjCheckNAddStates(78, 96);
/* 1687:     */             }
/* 1688:     */             break;
/* 1689:     */           case 225: 
/* 1690:1406 */             if ((0x0 & l) != 0L) {
/* 1691:1407 */               jjCheckNAddTwoStates(225, 227);
/* 1692:     */             }
/* 1693:     */             break;
/* 1694:     */           case 228: 
/* 1695:1410 */             if ((0x0 & l) != 0L) {
/* 1696:1411 */               jjCheckNAddTwoStates(228, 230);
/* 1697:     */             }
/* 1698:     */             break;
/* 1699:     */           case 231: 
/* 1700:1414 */             if ((0x0 & l) != 0L) {
/* 1701:1415 */               jjCheckNAddTwoStates(231, 233);
/* 1702:     */             }
/* 1703:     */             break;
/* 1704:     */           case 234: 
/* 1705:1418 */             if ((0x0 & l) != 0L) {
/* 1706:1419 */               jjCheckNAddTwoStates(234, 236);
/* 1707:     */             }
/* 1708:     */             break;
/* 1709:     */           case 237: 
/* 1710:1422 */             if ((0x0 & l) != 0L) {
/* 1711:1423 */               jjCheckNAddTwoStates(237, 239);
/* 1712:     */             }
/* 1713:     */             break;
/* 1714:     */           case 240: 
/* 1715:1426 */             if ((0x0 & l) != 0L) {
/* 1716:1427 */               jjCheckNAddTwoStates(240, 242);
/* 1717:     */             }
/* 1718:     */             break;
/* 1719:     */           case 243: 
/* 1720:1430 */             if ((0x0 & l) != 0L) {
/* 1721:1431 */               jjCheckNAddTwoStates(243, 245);
/* 1722:     */             }
/* 1723:     */             break;
/* 1724:     */           case 246: 
/* 1725:1434 */             if ((0x0 & l) != 0L) {
/* 1726:1435 */               jjCheckNAddTwoStates(246, 248);
/* 1727:     */             }
/* 1728:     */             break;
/* 1729:     */           case 249: 
/* 1730:1438 */             if ((0x0 & l) != 0L) {
/* 1731:1439 */               jjCheckNAddTwoStates(249, 252);
/* 1732:     */             }
/* 1733:     */             break;
/* 1734:     */           case 253: 
/* 1735:1442 */             if ((0x0 & l) != 0L) {
/* 1736:1443 */               jjCheckNAddTwoStates(253, 256);
/* 1737:     */             }
/* 1738:     */             break;
/* 1739:     */           case 257: 
/* 1740:1446 */             if ((0x0 & l) != 0L) {
/* 1741:1447 */               jjCheckNAddTwoStates(257, 261);
/* 1742:     */             }
/* 1743:     */             break;
/* 1744:     */           case 262: 
/* 1745:1450 */             if ((0x0 & l) != 0L) {
/* 1746:1451 */               jjCheckNAddTwoStates(262, 264);
/* 1747:     */             }
/* 1748:     */             break;
/* 1749:     */           case 265: 
/* 1750:1454 */             if ((0x0 & l) != 0L) {
/* 1751:1455 */               jjCheckNAddTwoStates(265, 266);
/* 1752:     */             }
/* 1753:     */             break;
/* 1754:     */           case 267: 
/* 1755:1458 */             if ((0x0 & l) != 0L) {
/* 1756:1459 */               jjCheckNAddTwoStates(267, 269);
/* 1757:     */             }
/* 1758:     */             break;
/* 1759:     */           case 270: 
/* 1760:1462 */             if ((0x0 & l) != 0L) {
/* 1761:1463 */               jjCheckNAddTwoStates(270, 273);
/* 1762:     */             }
/* 1763:     */             break;
/* 1764:     */           case 274: 
/* 1765:1466 */             if ((0x0 & l) != 0L) {
/* 1766:1467 */               jjCheckNAddStates(0, 2);
/* 1767:     */             }
/* 1768:     */             break;
/* 1769:     */           case 276: 
/* 1770:1470 */             if ((0x0 & l) != 0L)
/* 1771:     */             {
/* 1772:1472 */               if (kind > 51) {
/* 1773:1473 */                 kind = 51;
/* 1774:     */               }
/* 1775:1474 */               jjCheckNAddTwoStates(276, 277);
/* 1776:     */             }
/* 1777:1475 */             break;
/* 1778:     */           case 278: 
/* 1779:1477 */             if ((0x0 & l) != 0L)
/* 1780:     */             {
/* 1781:1479 */               if (kind > 51) {
/* 1782:1480 */                 kind = 51;
/* 1783:     */               }
/* 1784:1481 */               jjCheckNAddTwoStates(276, 277);
/* 1785:     */             }
/* 1786:1482 */             break;
/* 1787:     */           case 279: 
/* 1788:1484 */             if ((0x0 & l) != 0L)
/* 1789:     */             {
/* 1790:1486 */               if (kind > 51) {
/* 1791:1487 */                 kind = 51;
/* 1792:     */               }
/* 1793:1488 */               jjCheckNAddStates(265, 272);
/* 1794:     */             }
/* 1795:1489 */             break;
/* 1796:     */           case 280: 
/* 1797:1491 */             if ((0x0 & l) != 0L)
/* 1798:     */             {
/* 1799:1493 */               if (kind > 51) {
/* 1800:1494 */                 kind = 51;
/* 1801:     */               }
/* 1802:1495 */               jjCheckNAddStates(273, 275);
/* 1803:     */             }
/* 1804:1496 */             break;
/* 1805:     */           case 281: 
/* 1806:1498 */             if ((0x3600 & l) != 0L)
/* 1807:     */             {
/* 1808:1500 */               if (kind > 51) {
/* 1809:1501 */                 kind = 51;
/* 1810:     */               }
/* 1811:1502 */               jjCheckNAddTwoStates(276, 277);
/* 1812:     */             }
/* 1813:1503 */             break;
/* 1814:     */           case 282: 
/* 1815:     */           case 284: 
/* 1816:     */           case 287: 
/* 1817:     */           case 291: 
/* 1818:1508 */             if ((0x0 & l) != 0L) {
/* 1819:1509 */               jjCheckNAdd(280);
/* 1820:     */             }
/* 1821:     */             break;
/* 1822:     */           case 283: 
/* 1823:1512 */             if ((0x0 & l) != 0L) {
/* 1824:1513 */               this.jjstateSet[(this.jjnewStateCnt++)] = 284;
/* 1825:     */             }
/* 1826:     */             break;
/* 1827:     */           case 285: 
/* 1828:1516 */             if ((0x0 & l) != 0L) {
/* 1829:1517 */               this.jjstateSet[(this.jjnewStateCnt++)] = 286;
/* 1830:     */             }
/* 1831:     */             break;
/* 1832:     */           case 286: 
/* 1833:1520 */             if ((0x0 & l) != 0L) {
/* 1834:1521 */               this.jjstateSet[(this.jjnewStateCnt++)] = 287;
/* 1835:     */             }
/* 1836:     */             break;
/* 1837:     */           case 288: 
/* 1838:1524 */             if ((0x0 & l) != 0L) {
/* 1839:1525 */               this.jjstateSet[(this.jjnewStateCnt++)] = 289;
/* 1840:     */             }
/* 1841:     */             break;
/* 1842:     */           case 289: 
/* 1843:1528 */             if ((0x0 & l) != 0L) {
/* 1844:1529 */               this.jjstateSet[(this.jjnewStateCnt++)] = 290;
/* 1845:     */             }
/* 1846:     */             break;
/* 1847:     */           case 290: 
/* 1848:1532 */             if ((0x0 & l) != 0L) {
/* 1849:1533 */               this.jjstateSet[(this.jjnewStateCnt++)] = 291;
/* 1850:     */             }
/* 1851:     */             break;
/* 1852:     */           case 293: 
/* 1853:1536 */             if ((0x0 & l) != 0L)
/* 1854:     */             {
/* 1855:1538 */               if (kind > 51) {
/* 1856:1539 */                 kind = 51;
/* 1857:     */               }
/* 1858:1540 */               jjCheckNAddStates(276, 283);
/* 1859:     */             }
/* 1860:1541 */             break;
/* 1861:     */           case 294: 
/* 1862:1543 */             if ((0x0 & l) != 0L)
/* 1863:     */             {
/* 1864:1545 */               if (kind > 51) {
/* 1865:1546 */                 kind = 51;
/* 1866:     */               }
/* 1867:1547 */               jjCheckNAddStates(284, 286);
/* 1868:     */             }
/* 1869:1548 */             break;
/* 1870:     */           case 295: 
/* 1871:     */           case 297: 
/* 1872:     */           case 300: 
/* 1873:     */           case 304: 
/* 1874:1553 */             if ((0x0 & l) != 0L) {
/* 1875:1554 */               jjCheckNAdd(294);
/* 1876:     */             }
/* 1877:     */             break;
/* 1878:     */           case 296: 
/* 1879:1557 */             if ((0x0 & l) != 0L) {
/* 1880:1558 */               this.jjstateSet[(this.jjnewStateCnt++)] = 297;
/* 1881:     */             }
/* 1882:     */             break;
/* 1883:     */           case 298: 
/* 1884:1561 */             if ((0x0 & l) != 0L) {
/* 1885:1562 */               this.jjstateSet[(this.jjnewStateCnt++)] = 299;
/* 1886:     */             }
/* 1887:     */             break;
/* 1888:     */           case 299: 
/* 1889:1565 */             if ((0x0 & l) != 0L) {
/* 1890:1566 */               this.jjstateSet[(this.jjnewStateCnt++)] = 300;
/* 1891:     */             }
/* 1892:     */             break;
/* 1893:     */           case 301: 
/* 1894:1569 */             if ((0x0 & l) != 0L) {
/* 1895:1570 */               this.jjstateSet[(this.jjnewStateCnt++)] = 302;
/* 1896:     */             }
/* 1897:     */             break;
/* 1898:     */           case 302: 
/* 1899:1573 */             if ((0x0 & l) != 0L) {
/* 1900:1574 */               this.jjstateSet[(this.jjnewStateCnt++)] = 303;
/* 1901:     */             }
/* 1902:     */             break;
/* 1903:     */           case 303: 
/* 1904:1577 */             if ((0x0 & l) != 0L) {
/* 1905:1578 */               this.jjstateSet[(this.jjnewStateCnt++)] = 304;
/* 1906:     */             }
/* 1907:     */             break;
/* 1908:     */           case 305: 
/* 1909:1581 */             if ((0x0 & l) != 0L) {
/* 1910:1582 */               jjCheckNAddTwoStates(305, 306);
/* 1911:     */             }
/* 1912:     */             break;
/* 1913:     */           case 306: 
/* 1914:1585 */             if ((this.curChar == '%') && (kind > 52)) {
/* 1915:1586 */               kind = 52;
/* 1916:     */             }
/* 1917:     */             break;
/* 1918:     */           case 307: 
/* 1919:1589 */             if ((0x0 & l) != 0L)
/* 1920:     */             {
/* 1921:1591 */               if (kind > 53) {
/* 1922:1592 */                 kind = 53;
/* 1923:     */               }
/* 1924:1593 */               jjCheckNAdd(307);
/* 1925:     */             }
/* 1926:1594 */             break;
/* 1927:     */           case 308: 
/* 1928:1596 */             if ((0x0 & l) != 0L)
/* 1929:     */             {
/* 1930:1598 */               if (kind > 58) {
/* 1931:1599 */                 kind = 58;
/* 1932:     */               }
/* 1933:1600 */               jjCheckNAdd(308);
/* 1934:     */             }
/* 1935:1601 */             break;
/* 1936:     */           case 310: 
/* 1937:1603 */             if ((0x0 & l) != 0L) {
/* 1938:1604 */               jjCheckNAddStates(103, 105);
/* 1939:     */             }
/* 1940:     */             break;
/* 1941:     */           case 311: 
/* 1942:1607 */             if ((this.curChar == '(') && (kind > 55)) {
/* 1943:1608 */               kind = 55;
/* 1944:     */             }
/* 1945:     */             break;
/* 1946:     */           case 313: 
/* 1947:1611 */             if ((0x0 & l) != 0L) {
/* 1948:1612 */               jjCheckNAddStates(103, 105);
/* 1949:     */             }
/* 1950:     */             break;
/* 1951:     */           case 314: 
/* 1952:1615 */             if ((0x0 & l) != 0L) {
/* 1953:1616 */               jjCheckNAddStates(287, 295);
/* 1954:     */             }
/* 1955:     */             break;
/* 1956:     */           case 315: 
/* 1957:1619 */             if ((0x0 & l) != 0L) {
/* 1958:1620 */               jjCheckNAddStates(296, 299);
/* 1959:     */             }
/* 1960:     */             break;
/* 1961:     */           case 316: 
/* 1962:1623 */             if ((0x3600 & l) != 0L) {
/* 1963:1624 */               jjCheckNAddStates(103, 105);
/* 1964:     */             }
/* 1965:     */             break;
/* 1966:     */           case 317: 
/* 1967:     */           case 319: 
/* 1968:     */           case 322: 
/* 1969:     */           case 326: 
/* 1970:1630 */             if ((0x0 & l) != 0L) {
/* 1971:1631 */               jjCheckNAdd(315);
/* 1972:     */             }
/* 1973:     */             break;
/* 1974:     */           case 318: 
/* 1975:1634 */             if ((0x0 & l) != 0L) {
/* 1976:1635 */               this.jjstateSet[(this.jjnewStateCnt++)] = 319;
/* 1977:     */             }
/* 1978:     */             break;
/* 1979:     */           case 320: 
/* 1980:1638 */             if ((0x0 & l) != 0L) {
/* 1981:1639 */               this.jjstateSet[(this.jjnewStateCnt++)] = 321;
/* 1982:     */             }
/* 1983:     */             break;
/* 1984:     */           case 321: 
/* 1985:1642 */             if ((0x0 & l) != 0L) {
/* 1986:1643 */               this.jjstateSet[(this.jjnewStateCnt++)] = 322;
/* 1987:     */             }
/* 1988:     */             break;
/* 1989:     */           case 323: 
/* 1990:1646 */             if ((0x0 & l) != 0L) {
/* 1991:1647 */               this.jjstateSet[(this.jjnewStateCnt++)] = 324;
/* 1992:     */             }
/* 1993:     */             break;
/* 1994:     */           case 324: 
/* 1995:1650 */             if ((0x0 & l) != 0L) {
/* 1996:1651 */               this.jjstateSet[(this.jjnewStateCnt++)] = 325;
/* 1997:     */             }
/* 1998:     */             break;
/* 1999:     */           case 325: 
/* 2000:1654 */             if ((0x0 & l) != 0L) {
/* 2001:1655 */               this.jjstateSet[(this.jjnewStateCnt++)] = 326;
/* 2002:     */             }
/* 2003:     */             break;
/* 2004:     */           case 327: 
/* 2005:1658 */             if ((0x0 & l) != 0L)
/* 2006:     */             {
/* 2007:1660 */               if (kind > 56) {
/* 2008:1661 */                 kind = 56;
/* 2009:     */               }
/* 2010:1662 */               jjCheckNAddTwoStates(327, 328);
/* 2011:     */             }
/* 2012:1663 */             break;
/* 2013:     */           case 329: 
/* 2014:1665 */             if ((0x0 & l) != 0L)
/* 2015:     */             {
/* 2016:1667 */               if (kind > 56) {
/* 2017:1668 */                 kind = 56;
/* 2018:     */               }
/* 2019:1669 */               jjCheckNAddTwoStates(327, 328);
/* 2020:     */             }
/* 2021:1670 */             break;
/* 2022:     */           case 330: 
/* 2023:1672 */             if ((0x0 & l) != 0L)
/* 2024:     */             {
/* 2025:1674 */               if (kind > 56) {
/* 2026:1675 */                 kind = 56;
/* 2027:     */               }
/* 2028:1676 */               jjCheckNAddStates(300, 307);
/* 2029:     */             }
/* 2030:1677 */             break;
/* 2031:     */           case 331: 
/* 2032:1679 */             if ((0x0 & l) != 0L)
/* 2033:     */             {
/* 2034:1681 */               if (kind > 56) {
/* 2035:1682 */                 kind = 56;
/* 2036:     */               }
/* 2037:1683 */               jjCheckNAddStates(308, 310);
/* 2038:     */             }
/* 2039:1684 */             break;
/* 2040:     */           case 332: 
/* 2041:1686 */             if ((0x3600 & l) != 0L)
/* 2042:     */             {
/* 2043:1688 */               if (kind > 56) {
/* 2044:1689 */                 kind = 56;
/* 2045:     */               }
/* 2046:1690 */               jjCheckNAddTwoStates(327, 328);
/* 2047:     */             }
/* 2048:1691 */             break;
/* 2049:     */           case 333: 
/* 2050:     */           case 335: 
/* 2051:     */           case 338: 
/* 2052:     */           case 342: 
/* 2053:1696 */             if ((0x0 & l) != 0L) {
/* 2054:1697 */               jjCheckNAdd(331);
/* 2055:     */             }
/* 2056:     */             break;
/* 2057:     */           case 334: 
/* 2058:1700 */             if ((0x0 & l) != 0L) {
/* 2059:1701 */               this.jjstateSet[(this.jjnewStateCnt++)] = 335;
/* 2060:     */             }
/* 2061:     */             break;
/* 2062:     */           case 336: 
/* 2063:1704 */             if ((0x0 & l) != 0L) {
/* 2064:1705 */               this.jjstateSet[(this.jjnewStateCnt++)] = 337;
/* 2065:     */             }
/* 2066:     */             break;
/* 2067:     */           case 337: 
/* 2068:1708 */             if ((0x0 & l) != 0L) {
/* 2069:1709 */               this.jjstateSet[(this.jjnewStateCnt++)] = 338;
/* 2070:     */             }
/* 2071:     */             break;
/* 2072:     */           case 339: 
/* 2073:1712 */             if ((0x0 & l) != 0L) {
/* 2074:1713 */               this.jjstateSet[(this.jjnewStateCnt++)] = 340;
/* 2075:     */             }
/* 2076:     */             break;
/* 2077:     */           case 340: 
/* 2078:1716 */             if ((0x0 & l) != 0L) {
/* 2079:1717 */               this.jjstateSet[(this.jjnewStateCnt++)] = 341;
/* 2080:     */             }
/* 2081:     */             break;
/* 2082:     */           case 341: 
/* 2083:1720 */             if ((0x0 & l) != 0L) {
/* 2084:1721 */               this.jjstateSet[(this.jjnewStateCnt++)] = 342;
/* 2085:     */             }
/* 2086:     */             break;
/* 2087:     */           case 343: 
/* 2088:1724 */             if ((0x0 & l) != 0L)
/* 2089:     */             {
/* 2090:1726 */               if (kind > 53) {
/* 2091:1727 */                 kind = 53;
/* 2092:     */               }
/* 2093:1728 */               jjCheckNAddStates(3, 77);
/* 2094:     */             }
/* 2095:1729 */             break;
/* 2096:     */           case 344: 
/* 2097:1731 */             if ((0x0 & l) != 0L) {
/* 2098:1732 */               jjCheckNAddTwoStates(344, 227);
/* 2099:     */             }
/* 2100:     */             break;
/* 2101:     */           case 345: 
/* 2102:1735 */             if ((0x0 & l) != 0L) {
/* 2103:1736 */               jjCheckNAddTwoStates(345, 346);
/* 2104:     */             }
/* 2105:     */             break;
/* 2106:     */           case 346: 
/* 2107:1739 */             if (this.curChar == '.') {
/* 2108:1740 */               jjCheckNAdd(225);
/* 2109:     */             }
/* 2110:     */             break;
/* 2111:     */           case 347: 
/* 2112:1743 */             if ((0x0 & l) != 0L) {
/* 2113:1744 */               jjCheckNAddTwoStates(347, 230);
/* 2114:     */             }
/* 2115:     */             break;
/* 2116:     */           case 348: 
/* 2117:1747 */             if ((0x0 & l) != 0L) {
/* 2118:1748 */               jjCheckNAddTwoStates(348, 349);
/* 2119:     */             }
/* 2120:     */             break;
/* 2121:     */           case 349: 
/* 2122:1751 */             if (this.curChar == '.') {
/* 2123:1752 */               jjCheckNAdd(228);
/* 2124:     */             }
/* 2125:     */             break;
/* 2126:     */           case 350: 
/* 2127:1755 */             if ((0x0 & l) != 0L) {
/* 2128:1756 */               jjCheckNAddTwoStates(350, 233);
/* 2129:     */             }
/* 2130:     */             break;
/* 2131:     */           case 351: 
/* 2132:1759 */             if ((0x0 & l) != 0L) {
/* 2133:1760 */               jjCheckNAddTwoStates(351, 352);
/* 2134:     */             }
/* 2135:     */             break;
/* 2136:     */           case 352: 
/* 2137:1763 */             if (this.curChar == '.') {
/* 2138:1764 */               jjCheckNAdd(231);
/* 2139:     */             }
/* 2140:     */             break;
/* 2141:     */           case 353: 
/* 2142:1767 */             if ((0x0 & l) != 0L) {
/* 2143:1768 */               jjCheckNAddTwoStates(353, 236);
/* 2144:     */             }
/* 2145:     */             break;
/* 2146:     */           case 354: 
/* 2147:1771 */             if ((0x0 & l) != 0L) {
/* 2148:1772 */               jjCheckNAddTwoStates(354, 355);
/* 2149:     */             }
/* 2150:     */             break;
/* 2151:     */           case 355: 
/* 2152:1775 */             if (this.curChar == '.') {
/* 2153:1776 */               jjCheckNAdd(234);
/* 2154:     */             }
/* 2155:     */             break;
/* 2156:     */           case 356: 
/* 2157:1779 */             if ((0x0 & l) != 0L) {
/* 2158:1780 */               jjCheckNAddTwoStates(356, 239);
/* 2159:     */             }
/* 2160:     */             break;
/* 2161:     */           case 357: 
/* 2162:1783 */             if ((0x0 & l) != 0L) {
/* 2163:1784 */               jjCheckNAddTwoStates(357, 358);
/* 2164:     */             }
/* 2165:     */             break;
/* 2166:     */           case 358: 
/* 2167:1787 */             if (this.curChar == '.') {
/* 2168:1788 */               jjCheckNAdd(237);
/* 2169:     */             }
/* 2170:     */             break;
/* 2171:     */           case 359: 
/* 2172:1791 */             if ((0x0 & l) != 0L) {
/* 2173:1792 */               jjCheckNAddTwoStates(359, 242);
/* 2174:     */             }
/* 2175:     */             break;
/* 2176:     */           case 360: 
/* 2177:1795 */             if ((0x0 & l) != 0L) {
/* 2178:1796 */               jjCheckNAddTwoStates(360, 361);
/* 2179:     */             }
/* 2180:     */             break;
/* 2181:     */           case 361: 
/* 2182:1799 */             if (this.curChar == '.') {
/* 2183:1800 */               jjCheckNAdd(240);
/* 2184:     */             }
/* 2185:     */             break;
/* 2186:     */           case 362: 
/* 2187:1803 */             if ((0x0 & l) != 0L) {
/* 2188:1804 */               jjCheckNAddTwoStates(362, 245);
/* 2189:     */             }
/* 2190:     */             break;
/* 2191:     */           case 363: 
/* 2192:1807 */             if ((0x0 & l) != 0L) {
/* 2193:1808 */               jjCheckNAddTwoStates(363, 364);
/* 2194:     */             }
/* 2195:     */             break;
/* 2196:     */           case 364: 
/* 2197:1811 */             if (this.curChar == '.') {
/* 2198:1812 */               jjCheckNAdd(243);
/* 2199:     */             }
/* 2200:     */             break;
/* 2201:     */           case 365: 
/* 2202:1815 */             if ((0x0 & l) != 0L) {
/* 2203:1816 */               jjCheckNAddTwoStates(365, 248);
/* 2204:     */             }
/* 2205:     */             break;
/* 2206:     */           case 366: 
/* 2207:1819 */             if ((0x0 & l) != 0L) {
/* 2208:1820 */               jjCheckNAddTwoStates(366, 367);
/* 2209:     */             }
/* 2210:     */             break;
/* 2211:     */           case 367: 
/* 2212:1823 */             if (this.curChar == '.') {
/* 2213:1824 */               jjCheckNAdd(246);
/* 2214:     */             }
/* 2215:     */             break;
/* 2216:     */           case 368: 
/* 2217:1827 */             if ((0x0 & l) != 0L) {
/* 2218:1828 */               jjCheckNAddTwoStates(368, 252);
/* 2219:     */             }
/* 2220:     */             break;
/* 2221:     */           case 369: 
/* 2222:1831 */             if ((0x0 & l) != 0L) {
/* 2223:1832 */               jjCheckNAddTwoStates(369, 370);
/* 2224:     */             }
/* 2225:     */             break;
/* 2226:     */           case 370: 
/* 2227:1835 */             if (this.curChar == '.') {
/* 2228:1836 */               jjCheckNAdd(249);
/* 2229:     */             }
/* 2230:     */             break;
/* 2231:     */           case 371: 
/* 2232:1839 */             if ((0x0 & l) != 0L) {
/* 2233:1840 */               jjCheckNAddTwoStates(371, 256);
/* 2234:     */             }
/* 2235:     */             break;
/* 2236:     */           case 372: 
/* 2237:1843 */             if ((0x0 & l) != 0L) {
/* 2238:1844 */               jjCheckNAddTwoStates(372, 373);
/* 2239:     */             }
/* 2240:     */             break;
/* 2241:     */           case 373: 
/* 2242:1847 */             if (this.curChar == '.') {
/* 2243:1848 */               jjCheckNAdd(253);
/* 2244:     */             }
/* 2245:     */             break;
/* 2246:     */           case 374: 
/* 2247:1851 */             if ((0x0 & l) != 0L) {
/* 2248:1852 */               jjCheckNAddTwoStates(374, 261);
/* 2249:     */             }
/* 2250:     */             break;
/* 2251:     */           case 375: 
/* 2252:1855 */             if ((0x0 & l) != 0L) {
/* 2253:1856 */               jjCheckNAddTwoStates(375, 376);
/* 2254:     */             }
/* 2255:     */             break;
/* 2256:     */           case 376: 
/* 2257:1859 */             if (this.curChar == '.') {
/* 2258:1860 */               jjCheckNAdd(257);
/* 2259:     */             }
/* 2260:     */             break;
/* 2261:     */           case 377: 
/* 2262:1863 */             if ((0x0 & l) != 0L) {
/* 2263:1864 */               jjCheckNAddTwoStates(377, 264);
/* 2264:     */             }
/* 2265:     */             break;
/* 2266:     */           case 378: 
/* 2267:1867 */             if ((0x0 & l) != 0L) {
/* 2268:1868 */               jjCheckNAddTwoStates(378, 379);
/* 2269:     */             }
/* 2270:     */             break;
/* 2271:     */           case 379: 
/* 2272:1871 */             if (this.curChar == '.') {
/* 2273:1872 */               jjCheckNAdd(262);
/* 2274:     */             }
/* 2275:     */             break;
/* 2276:     */           case 380: 
/* 2277:1875 */             if ((0x0 & l) != 0L) {
/* 2278:1876 */               jjCheckNAddTwoStates(380, 266);
/* 2279:     */             }
/* 2280:     */             break;
/* 2281:     */           case 381: 
/* 2282:1879 */             if ((0x0 & l) != 0L) {
/* 2283:1880 */               jjCheckNAddTwoStates(381, 382);
/* 2284:     */             }
/* 2285:     */             break;
/* 2286:     */           case 382: 
/* 2287:1883 */             if (this.curChar == '.') {
/* 2288:1884 */               jjCheckNAdd(265);
/* 2289:     */             }
/* 2290:     */             break;
/* 2291:     */           case 383: 
/* 2292:1887 */             if ((0x0 & l) != 0L) {
/* 2293:1888 */               jjCheckNAddTwoStates(383, 269);
/* 2294:     */             }
/* 2295:     */             break;
/* 2296:     */           case 384: 
/* 2297:1891 */             if ((0x0 & l) != 0L) {
/* 2298:1892 */               jjCheckNAddTwoStates(384, 385);
/* 2299:     */             }
/* 2300:     */             break;
/* 2301:     */           case 385: 
/* 2302:1895 */             if (this.curChar == '.') {
/* 2303:1896 */               jjCheckNAdd(267);
/* 2304:     */             }
/* 2305:     */             break;
/* 2306:     */           case 386: 
/* 2307:1899 */             if ((0x0 & l) != 0L) {
/* 2308:1900 */               jjCheckNAddTwoStates(386, 273);
/* 2309:     */             }
/* 2310:     */             break;
/* 2311:     */           case 387: 
/* 2312:1903 */             if ((0x0 & l) != 0L) {
/* 2313:1904 */               jjCheckNAddTwoStates(387, 388);
/* 2314:     */             }
/* 2315:     */             break;
/* 2316:     */           case 388: 
/* 2317:1907 */             if (this.curChar == '.') {
/* 2318:1908 */               jjCheckNAdd(270);
/* 2319:     */             }
/* 2320:     */             break;
/* 2321:     */           case 389: 
/* 2322:1911 */             if ((0x0 & l) != 0L) {
/* 2323:1912 */               jjCheckNAddStates(311, 313);
/* 2324:     */             }
/* 2325:     */             break;
/* 2326:     */           case 390: 
/* 2327:1915 */             if ((0x0 & l) != 0L) {
/* 2328:1916 */               jjCheckNAddTwoStates(390, 391);
/* 2329:     */             }
/* 2330:     */             break;
/* 2331:     */           case 391: 
/* 2332:1919 */             if (this.curChar == '.') {
/* 2333:1920 */               jjCheckNAdd(274);
/* 2334:     */             }
/* 2335:     */             break;
/* 2336:     */           case 392: 
/* 2337:1923 */             if ((0x0 & l) != 0L) {
/* 2338:1924 */               jjCheckNAddTwoStates(392, 306);
/* 2339:     */             }
/* 2340:     */             break;
/* 2341:     */           case 393: 
/* 2342:1927 */             if ((0x0 & l) != 0L) {
/* 2343:1928 */               jjCheckNAddTwoStates(393, 394);
/* 2344:     */             }
/* 2345:     */             break;
/* 2346:     */           case 394: 
/* 2347:1931 */             if (this.curChar == '.') {
/* 2348:1932 */               jjCheckNAdd(305);
/* 2349:     */             }
/* 2350:     */             break;
/* 2351:     */           case 395: 
/* 2352:1935 */             if ((0x0 & l) != 0L)
/* 2353:     */             {
/* 2354:1937 */               if (kind > 53) {
/* 2355:1938 */                 kind = 53;
/* 2356:     */               }
/* 2357:1939 */               jjCheckNAdd(395);
/* 2358:     */             }
/* 2359:1940 */             break;
/* 2360:     */           case 396: 
/* 2361:1942 */             if ((0x0 & l) != 0L) {
/* 2362:1943 */               jjCheckNAddTwoStates(396, 397);
/* 2363:     */             }
/* 2364:     */             break;
/* 2365:     */           case 397: 
/* 2366:1946 */             if (this.curChar == '.') {
/* 2367:1947 */               jjCheckNAdd(307);
/* 2368:     */             }
/* 2369:     */             break;
/* 2370:     */           case 398: 
/* 2371:1950 */             if ((0x0 & l) != 0L)
/* 2372:     */             {
/* 2373:1952 */               if (kind > 58) {
/* 2374:1953 */                 kind = 58;
/* 2375:     */               }
/* 2376:1954 */               jjCheckNAdd(398);
/* 2377:     */             }
/* 2378:1955 */             break;
/* 2379:     */           case 399: 
/* 2380:1957 */             if ((0x0 & l) != 0L) {
/* 2381:1958 */               jjCheckNAddTwoStates(399, 400);
/* 2382:     */             }
/* 2383:     */             break;
/* 2384:     */           case 400: 
/* 2385:1961 */             if (this.curChar == '.') {
/* 2386:1962 */               jjCheckNAdd(308);
/* 2387:     */             }
/* 2388:     */             break;
/* 2389:     */           case 402: 
/* 2390:1965 */             if ((0x0 & l) != 0L)
/* 2391:     */             {
/* 2392:1967 */               if (kind > 56) {
/* 2393:1968 */                 kind = 56;
/* 2394:     */               }
/* 2395:1969 */               jjCheckNAddStates(314, 321);
/* 2396:     */             }
/* 2397:1970 */             break;
/* 2398:     */           case 403: 
/* 2399:1972 */             if ((0x0 & l) != 0L)
/* 2400:     */             {
/* 2401:1974 */               if (kind > 56) {
/* 2402:1975 */                 kind = 56;
/* 2403:     */               }
/* 2404:1976 */               jjCheckNAddStates(322, 324);
/* 2405:     */             }
/* 2406:1977 */             break;
/* 2407:     */           case 404: 
/* 2408:     */           case 406: 
/* 2409:     */           case 409: 
/* 2410:     */           case 413: 
/* 2411:1982 */             if ((0x0 & l) != 0L) {
/* 2412:1983 */               jjCheckNAdd(403);
/* 2413:     */             }
/* 2414:     */             break;
/* 2415:     */           case 405: 
/* 2416:1986 */             if ((0x0 & l) != 0L) {
/* 2417:1987 */               this.jjstateSet[(this.jjnewStateCnt++)] = 406;
/* 2418:     */             }
/* 2419:     */             break;
/* 2420:     */           case 407: 
/* 2421:1990 */             if ((0x0 & l) != 0L) {
/* 2422:1991 */               this.jjstateSet[(this.jjnewStateCnt++)] = 408;
/* 2423:     */             }
/* 2424:     */             break;
/* 2425:     */           case 408: 
/* 2426:1994 */             if ((0x0 & l) != 0L) {
/* 2427:1995 */               this.jjstateSet[(this.jjnewStateCnt++)] = 409;
/* 2428:     */             }
/* 2429:     */             break;
/* 2430:     */           case 410: 
/* 2431:1998 */             if ((0x0 & l) != 0L) {
/* 2432:1999 */               this.jjstateSet[(this.jjnewStateCnt++)] = 411;
/* 2433:     */             }
/* 2434:     */             break;
/* 2435:     */           case 411: 
/* 2436:2002 */             if ((0x0 & l) != 0L) {
/* 2437:2003 */               this.jjstateSet[(this.jjnewStateCnt++)] = 412;
/* 2438:     */             }
/* 2439:     */             break;
/* 2440:     */           case 412: 
/* 2441:2006 */             if ((0x0 & l) != 0L) {
/* 2442:2007 */               this.jjstateSet[(this.jjnewStateCnt++)] = 413;
/* 2443:     */             }
/* 2444:     */             break;
/* 2445:     */           case 414: 
/* 2446:2010 */             if ((0x0 & l) != 0L) {
/* 2447:2011 */               jjCheckNAddStates(325, 333);
/* 2448:     */             }
/* 2449:     */             break;
/* 2450:     */           case 415: 
/* 2451:2014 */             if ((0x0 & l) != 0L) {
/* 2452:2015 */               jjCheckNAddStates(334, 337);
/* 2453:     */             }
/* 2454:     */             break;
/* 2455:     */           case 416: 
/* 2456:     */           case 418: 
/* 2457:     */           case 421: 
/* 2458:     */           case 425: 
/* 2459:2021 */             if ((0x0 & l) != 0L) {
/* 2460:2022 */               jjCheckNAdd(415);
/* 2461:     */             }
/* 2462:     */             break;
/* 2463:     */           case 417: 
/* 2464:2025 */             if ((0x0 & l) != 0L) {
/* 2465:2026 */               this.jjstateSet[(this.jjnewStateCnt++)] = 418;
/* 2466:     */             }
/* 2467:     */             break;
/* 2468:     */           case 419: 
/* 2469:2029 */             if ((0x0 & l) != 0L) {
/* 2470:2030 */               this.jjstateSet[(this.jjnewStateCnt++)] = 420;
/* 2471:     */             }
/* 2472:     */             break;
/* 2473:     */           case 420: 
/* 2474:2033 */             if ((0x0 & l) != 0L) {
/* 2475:2034 */               this.jjstateSet[(this.jjnewStateCnt++)] = 421;
/* 2476:     */             }
/* 2477:     */             break;
/* 2478:     */           case 422: 
/* 2479:2037 */             if ((0x0 & l) != 0L) {
/* 2480:2038 */               this.jjstateSet[(this.jjnewStateCnt++)] = 423;
/* 2481:     */             }
/* 2482:     */             break;
/* 2483:     */           case 423: 
/* 2484:2041 */             if ((0x0 & l) != 0L) {
/* 2485:2042 */               this.jjstateSet[(this.jjnewStateCnt++)] = 424;
/* 2486:     */             }
/* 2487:     */             break;
/* 2488:     */           case 424: 
/* 2489:2045 */             if ((0x0 & l) != 0L) {
/* 2490:2046 */               this.jjstateSet[(this.jjnewStateCnt++)] = 425;
/* 2491:     */             }
/* 2492:     */             break;
/* 2493:     */           }
/* 2494:2050 */         } while (i != startsAt);
/* 2495:     */       }
/* 2496:2052 */       else if (this.curChar < '')
/* 2497:     */       {
/* 2498:2054 */         long l = 1L << (this.curChar & 0x3F);
/* 2499:     */         do
/* 2500:     */         {
/* 2501:2057 */           switch (this.jjstateSet[(--i)])
/* 2502:     */           {
/* 2503:     */           case 428: 
/* 2504:2060 */             if ((0x87FFFFFE & l) != 0L)
/* 2505:     */             {
/* 2506:2062 */               if (kind > 33) {
/* 2507:2063 */                 kind = 33;
/* 2508:     */               }
/* 2509:2064 */               jjCheckNAddTwoStates(62, 63);
/* 2510:     */             }
/* 2511:2066 */             else if (this.curChar == '\\')
/* 2512:     */             {
/* 2513:2067 */               jjCheckNAddTwoStates(64, 65);
/* 2514:     */             }
/* 2515:     */             break;
/* 2516:     */           case 61: 
/* 2517:2070 */             if ((0x87FFFFFE & l) != 0L)
/* 2518:     */             {
/* 2519:2072 */               if (kind > 33) {
/* 2520:2073 */                 kind = 33;
/* 2521:     */               }
/* 2522:2074 */               jjCheckNAddTwoStates(62, 63);
/* 2523:     */             }
/* 2524:2076 */             else if (this.curChar == '\\')
/* 2525:     */             {
/* 2526:2077 */               jjCheckNAddTwoStates(64, 79);
/* 2527:     */             }
/* 2528:     */             break;
/* 2529:     */           case 1: 
/* 2530:2080 */             if ((0x87FFFFFE & l) != 0L)
/* 2531:     */             {
/* 2532:2082 */               if (kind > 56) {
/* 2533:2083 */                 kind = 56;
/* 2534:     */               }
/* 2535:2084 */               jjCheckNAddStates(338, 342);
/* 2536:     */             }
/* 2537:2086 */             else if (this.curChar == '\\')
/* 2538:     */             {
/* 2539:2087 */               jjCheckNAddStates(343, 346);
/* 2540:     */             }
/* 2541:2088 */             else if (this.curChar == '@')
/* 2542:     */             {
/* 2543:2089 */               jjAddStates(347, 348);
/* 2544:     */             }
/* 2545:2090 */             if ((0x200000 & l) != 0L) {
/* 2546:2091 */               jjAddStates(349, 350);
/* 2547:     */             }
/* 2548:     */             break;
/* 2549:     */           case 426: 
/* 2550:2094 */             if ((0x87FFFFFE & l) != 0L)
/* 2551:     */             {
/* 2552:2096 */               if (kind > 56) {
/* 2553:2097 */                 kind = 56;
/* 2554:     */               }
/* 2555:2098 */               jjCheckNAddTwoStates(327, 328);
/* 2556:     */             }
/* 2557:2100 */             else if (this.curChar == '\\')
/* 2558:     */             {
/* 2559:2101 */               jjCheckNAddTwoStates(313, 314);
/* 2560:     */             }
/* 2561:2102 */             if ((0x87FFFFFE & l) != 0L) {
/* 2562:2103 */               jjCheckNAddStates(103, 105);
/* 2563:2104 */             } else if (this.curChar == '\\') {
/* 2564:2105 */               jjCheckNAddTwoStates(329, 330);
/* 2565:     */             }
/* 2566:     */             break;
/* 2567:     */           case 2: 
/* 2568:2108 */             if ((0x87FFFFFE & l) != 0L)
/* 2569:     */             {
/* 2570:2110 */               if (kind > 19) {
/* 2571:2111 */                 kind = 19;
/* 2572:     */               }
/* 2573:2112 */               jjCheckNAddTwoStates(2, 3);
/* 2574:     */             }
/* 2575:2113 */             break;
/* 2576:     */           case 3: 
/* 2577:2115 */             if (this.curChar == '\\') {
/* 2578:2116 */               jjAddStates(351, 352);
/* 2579:     */             }
/* 2580:     */             break;
/* 2581:     */           case 4: 
/* 2582:2119 */             if ((0xFFFFFFFF & l) != 0L)
/* 2583:     */             {
/* 2584:2121 */               if (kind > 19) {
/* 2585:2122 */                 kind = 19;
/* 2586:     */               }
/* 2587:2123 */               jjCheckNAddTwoStates(2, 3);
/* 2588:     */             }
/* 2589:2124 */             break;
/* 2590:     */           case 5: 
/* 2591:2126 */             if ((0x7E & l) != 0L)
/* 2592:     */             {
/* 2593:2128 */               if (kind > 19) {
/* 2594:2129 */                 kind = 19;
/* 2595:     */               }
/* 2596:2130 */               jjCheckNAddStates(106, 113);
/* 2597:     */             }
/* 2598:2131 */             break;
/* 2599:     */           case 6: 
/* 2600:2133 */             if ((0x7E & l) != 0L)
/* 2601:     */             {
/* 2602:2135 */               if (kind > 19) {
/* 2603:2136 */                 kind = 19;
/* 2604:     */               }
/* 2605:2137 */               jjCheckNAddStates(114, 116);
/* 2606:     */             }
/* 2607:2138 */             break;
/* 2608:     */           case 8: 
/* 2609:     */           case 10: 
/* 2610:     */           case 13: 
/* 2611:     */           case 17: 
/* 2612:2143 */             if ((0x7E & l) != 0L) {
/* 2613:2144 */               jjCheckNAdd(6);
/* 2614:     */             }
/* 2615:     */             break;
/* 2616:     */           case 9: 
/* 2617:2147 */             if ((0x7E & l) != 0L) {
/* 2618:2148 */               this.jjstateSet[(this.jjnewStateCnt++)] = 10;
/* 2619:     */             }
/* 2620:     */             break;
/* 2621:     */           case 11: 
/* 2622:2151 */             if ((0x7E & l) != 0L) {
/* 2623:2152 */               this.jjstateSet[(this.jjnewStateCnt++)] = 12;
/* 2624:     */             }
/* 2625:     */             break;
/* 2626:     */           case 12: 
/* 2627:2155 */             if ((0x7E & l) != 0L) {
/* 2628:2156 */               this.jjstateSet[(this.jjnewStateCnt++)] = 13;
/* 2629:     */             }
/* 2630:     */             break;
/* 2631:     */           case 14: 
/* 2632:2159 */             if ((0x7E & l) != 0L) {
/* 2633:2160 */               this.jjstateSet[(this.jjnewStateCnt++)] = 15;
/* 2634:     */             }
/* 2635:     */             break;
/* 2636:     */           case 15: 
/* 2637:2163 */             if ((0x7E & l) != 0L) {
/* 2638:2164 */               this.jjstateSet[(this.jjnewStateCnt++)] = 16;
/* 2639:     */             }
/* 2640:     */             break;
/* 2641:     */           case 16: 
/* 2642:2167 */             if ((0x7E & l) != 0L) {
/* 2643:2168 */               this.jjstateSet[(this.jjnewStateCnt++)] = 17;
/* 2644:     */             }
/* 2645:     */             break;
/* 2646:     */           case 19: 
/* 2647:2171 */             if ((0xEFFFFFFF & l) != 0L) {
/* 2648:2172 */               jjCheckNAddStates(100, 102);
/* 2649:     */             }
/* 2650:     */             break;
/* 2651:     */           case 21: 
/* 2652:2175 */             if (this.curChar == '\\') {
/* 2653:2176 */               jjAddStates(353, 356);
/* 2654:     */             }
/* 2655:     */             break;
/* 2656:     */           case 25: 
/* 2657:2179 */             if ((0xFFFFFFFF & l) != 0L) {
/* 2658:2180 */               jjCheckNAddStates(100, 102);
/* 2659:     */             }
/* 2660:     */             break;
/* 2661:     */           case 26: 
/* 2662:2183 */             if ((0x7E & l) != 0L) {
/* 2663:2184 */               jjCheckNAddStates(117, 125);
/* 2664:     */             }
/* 2665:     */             break;
/* 2666:     */           case 27: 
/* 2667:2187 */             if ((0x7E & l) != 0L) {
/* 2668:2188 */               jjCheckNAddStates(126, 129);
/* 2669:     */             }
/* 2670:     */             break;
/* 2671:     */           case 29: 
/* 2672:     */           case 31: 
/* 2673:     */           case 34: 
/* 2674:     */           case 38: 
/* 2675:2194 */             if ((0x7E & l) != 0L) {
/* 2676:2195 */               jjCheckNAdd(27);
/* 2677:     */             }
/* 2678:     */             break;
/* 2679:     */           case 30: 
/* 2680:2198 */             if ((0x7E & l) != 0L) {
/* 2681:2199 */               this.jjstateSet[(this.jjnewStateCnt++)] = 31;
/* 2682:     */             }
/* 2683:     */             break;
/* 2684:     */           case 32: 
/* 2685:2202 */             if ((0x7E & l) != 0L) {
/* 2686:2203 */               this.jjstateSet[(this.jjnewStateCnt++)] = 33;
/* 2687:     */             }
/* 2688:     */             break;
/* 2689:     */           case 33: 
/* 2690:2206 */             if ((0x7E & l) != 0L) {
/* 2691:2207 */               this.jjstateSet[(this.jjnewStateCnt++)] = 34;
/* 2692:     */             }
/* 2693:     */             break;
/* 2694:     */           case 35: 
/* 2695:2210 */             if ((0x7E & l) != 0L) {
/* 2696:2211 */               this.jjstateSet[(this.jjnewStateCnt++)] = 36;
/* 2697:     */             }
/* 2698:     */             break;
/* 2699:     */           case 36: 
/* 2700:2214 */             if ((0x7E & l) != 0L) {
/* 2701:2215 */               this.jjstateSet[(this.jjnewStateCnt++)] = 37;
/* 2702:     */             }
/* 2703:     */             break;
/* 2704:     */           case 37: 
/* 2705:2218 */             if ((0x7E & l) != 0L) {
/* 2706:2219 */               this.jjstateSet[(this.jjnewStateCnt++)] = 38;
/* 2707:     */             }
/* 2708:     */             break;
/* 2709:     */           case 40: 
/* 2710:2222 */             if ((0xEFFFFFFF & l) != 0L) {
/* 2711:2223 */               jjCheckNAddStates(97, 99);
/* 2712:     */             }
/* 2713:     */             break;
/* 2714:     */           case 42: 
/* 2715:2226 */             if (this.curChar == '\\') {
/* 2716:2227 */               jjAddStates(357, 360);
/* 2717:     */             }
/* 2718:     */             break;
/* 2719:     */           case 46: 
/* 2720:2230 */             if ((0xFFFFFFFF & l) != 0L) {
/* 2721:2231 */               jjCheckNAddStates(97, 99);
/* 2722:     */             }
/* 2723:     */             break;
/* 2724:     */           case 47: 
/* 2725:2234 */             if ((0x7E & l) != 0L) {
/* 2726:2235 */               jjCheckNAddStates(130, 138);
/* 2727:     */             }
/* 2728:     */             break;
/* 2729:     */           case 48: 
/* 2730:2238 */             if ((0x7E & l) != 0L) {
/* 2731:2239 */               jjCheckNAddStates(139, 142);
/* 2732:     */             }
/* 2733:     */             break;
/* 2734:     */           case 50: 
/* 2735:     */           case 52: 
/* 2736:     */           case 55: 
/* 2737:     */           case 59: 
/* 2738:2245 */             if ((0x7E & l) != 0L) {
/* 2739:2246 */               jjCheckNAdd(48);
/* 2740:     */             }
/* 2741:     */             break;
/* 2742:     */           case 51: 
/* 2743:2249 */             if ((0x7E & l) != 0L) {
/* 2744:2250 */               this.jjstateSet[(this.jjnewStateCnt++)] = 52;
/* 2745:     */             }
/* 2746:     */             break;
/* 2747:     */           case 53: 
/* 2748:2253 */             if ((0x7E & l) != 0L) {
/* 2749:2254 */               this.jjstateSet[(this.jjnewStateCnt++)] = 54;
/* 2750:     */             }
/* 2751:     */             break;
/* 2752:     */           case 54: 
/* 2753:2257 */             if ((0x7E & l) != 0L) {
/* 2754:2258 */               this.jjstateSet[(this.jjnewStateCnt++)] = 55;
/* 2755:     */             }
/* 2756:     */             break;
/* 2757:     */           case 56: 
/* 2758:2261 */             if ((0x7E & l) != 0L) {
/* 2759:2262 */               this.jjstateSet[(this.jjnewStateCnt++)] = 57;
/* 2760:     */             }
/* 2761:     */             break;
/* 2762:     */           case 57: 
/* 2763:2265 */             if ((0x7E & l) != 0L) {
/* 2764:2266 */               this.jjstateSet[(this.jjnewStateCnt++)] = 58;
/* 2765:     */             }
/* 2766:     */             break;
/* 2767:     */           case 58: 
/* 2768:2269 */             if ((0x7E & l) != 0L) {
/* 2769:2270 */               this.jjstateSet[(this.jjnewStateCnt++)] = 59;
/* 2770:     */             }
/* 2771:     */             break;
/* 2772:     */           case 60: 
/* 2773:2273 */             if (this.curChar == '@') {
/* 2774:2274 */               jjAddStates(347, 348);
/* 2775:     */             }
/* 2776:     */             break;
/* 2777:     */           case 62: 
/* 2778:2277 */             if ((0x87FFFFFE & l) != 0L)
/* 2779:     */             {
/* 2780:2279 */               if (kind > 33) {
/* 2781:2280 */                 kind = 33;
/* 2782:     */               }
/* 2783:2281 */               jjCheckNAddTwoStates(62, 63);
/* 2784:     */             }
/* 2785:2282 */             break;
/* 2786:     */           case 63: 
/* 2787:2284 */             if (this.curChar == '\\') {
/* 2788:2285 */               jjCheckNAddTwoStates(64, 65);
/* 2789:     */             }
/* 2790:     */             break;
/* 2791:     */           case 64: 
/* 2792:2288 */             if ((0xFFFFFFFF & l) != 0L)
/* 2793:     */             {
/* 2794:2290 */               if (kind > 33) {
/* 2795:2291 */                 kind = 33;
/* 2796:     */               }
/* 2797:2292 */               jjCheckNAddTwoStates(62, 63);
/* 2798:     */             }
/* 2799:2293 */             break;
/* 2800:     */           case 65: 
/* 2801:2295 */             if ((0x7E & l) != 0L)
/* 2802:     */             {
/* 2803:2297 */               if (kind > 33) {
/* 2804:2298 */                 kind = 33;
/* 2805:     */               }
/* 2806:2299 */               jjCheckNAddStates(143, 150);
/* 2807:     */             }
/* 2808:2300 */             break;
/* 2809:     */           case 66: 
/* 2810:2302 */             if ((0x7E & l) != 0L)
/* 2811:     */             {
/* 2812:2304 */               if (kind > 33) {
/* 2813:2305 */                 kind = 33;
/* 2814:     */               }
/* 2815:2306 */               jjCheckNAddStates(151, 153);
/* 2816:     */             }
/* 2817:2307 */             break;
/* 2818:     */           case 68: 
/* 2819:     */           case 70: 
/* 2820:     */           case 73: 
/* 2821:     */           case 77: 
/* 2822:2312 */             if ((0x7E & l) != 0L) {
/* 2823:2313 */               jjCheckNAdd(66);
/* 2824:     */             }
/* 2825:     */             break;
/* 2826:     */           case 69: 
/* 2827:2316 */             if ((0x7E & l) != 0L) {
/* 2828:2317 */               this.jjstateSet[(this.jjnewStateCnt++)] = 70;
/* 2829:     */             }
/* 2830:     */             break;
/* 2831:     */           case 71: 
/* 2832:2320 */             if ((0x7E & l) != 0L) {
/* 2833:2321 */               this.jjstateSet[(this.jjnewStateCnt++)] = 72;
/* 2834:     */             }
/* 2835:     */             break;
/* 2836:     */           case 72: 
/* 2837:2324 */             if ((0x7E & l) != 0L) {
/* 2838:2325 */               this.jjstateSet[(this.jjnewStateCnt++)] = 73;
/* 2839:     */             }
/* 2840:     */             break;
/* 2841:     */           case 74: 
/* 2842:2328 */             if ((0x7E & l) != 0L) {
/* 2843:2329 */               this.jjstateSet[(this.jjnewStateCnt++)] = 75;
/* 2844:     */             }
/* 2845:     */             break;
/* 2846:     */           case 75: 
/* 2847:2332 */             if ((0x7E & l) != 0L) {
/* 2848:2333 */               this.jjstateSet[(this.jjnewStateCnt++)] = 76;
/* 2849:     */             }
/* 2850:     */             break;
/* 2851:     */           case 76: 
/* 2852:2336 */             if ((0x7E & l) != 0L) {
/* 2853:2337 */               this.jjstateSet[(this.jjnewStateCnt++)] = 77;
/* 2854:     */             }
/* 2855:     */             break;
/* 2856:     */           case 78: 
/* 2857:2340 */             if (this.curChar == '\\') {
/* 2858:2341 */               jjCheckNAddTwoStates(64, 79);
/* 2859:     */             }
/* 2860:     */             break;
/* 2861:     */           case 79: 
/* 2862:2344 */             if ((0x7E & l) != 0L)
/* 2863:     */             {
/* 2864:2346 */               if (kind > 33) {
/* 2865:2347 */                 kind = 33;
/* 2866:     */               }
/* 2867:2348 */               jjCheckNAddStates(154, 161);
/* 2868:     */             }
/* 2869:2349 */             break;
/* 2870:     */           case 80: 
/* 2871:2351 */             if ((0x7E & l) != 0L)
/* 2872:     */             {
/* 2873:2353 */               if (kind > 33) {
/* 2874:2354 */                 kind = 33;
/* 2875:     */               }
/* 2876:2355 */               jjCheckNAddStates(162, 164);
/* 2877:     */             }
/* 2878:2356 */             break;
/* 2879:     */           case 81: 
/* 2880:     */           case 83: 
/* 2881:     */           case 86: 
/* 2882:     */           case 90: 
/* 2883:2361 */             if ((0x7E & l) != 0L) {
/* 2884:2362 */               jjCheckNAdd(80);
/* 2885:     */             }
/* 2886:     */             break;
/* 2887:     */           case 82: 
/* 2888:2365 */             if ((0x7E & l) != 0L) {
/* 2889:2366 */               this.jjstateSet[(this.jjnewStateCnt++)] = 83;
/* 2890:     */             }
/* 2891:     */             break;
/* 2892:     */           case 84: 
/* 2893:2369 */             if ((0x7E & l) != 0L) {
/* 2894:2370 */               this.jjstateSet[(this.jjnewStateCnt++)] = 85;
/* 2895:     */             }
/* 2896:     */             break;
/* 2897:     */           case 85: 
/* 2898:2373 */             if ((0x7E & l) != 0L) {
/* 2899:2374 */               this.jjstateSet[(this.jjnewStateCnt++)] = 86;
/* 2900:     */             }
/* 2901:     */             break;
/* 2902:     */           case 87: 
/* 2903:2377 */             if ((0x7E & l) != 0L) {
/* 2904:2378 */               this.jjstateSet[(this.jjnewStateCnt++)] = 88;
/* 2905:     */             }
/* 2906:     */             break;
/* 2907:     */           case 88: 
/* 2908:2381 */             if ((0x7E & l) != 0L) {
/* 2909:2382 */               this.jjstateSet[(this.jjnewStateCnt++)] = 89;
/* 2910:     */             }
/* 2911:     */             break;
/* 2912:     */           case 89: 
/* 2913:2385 */             if ((0x7E & l) != 0L) {
/* 2914:2386 */               this.jjstateSet[(this.jjnewStateCnt++)] = 90;
/* 2915:     */             }
/* 2916:     */             break;
/* 2917:     */           case 93: 
/* 2918:2389 */             if (((0x100000 & l) != 0L) && (kind > 34)) {
/* 2919:2390 */               kind = 34;
/* 2920:     */             }
/* 2921:     */             break;
/* 2922:     */           case 94: 
/* 2923:2393 */             if ((0x4000 & l) != 0L) {
/* 2924:2394 */               this.jjstateSet[(this.jjnewStateCnt++)] = 93;
/* 2925:     */             }
/* 2926:     */             break;
/* 2927:     */           case 95: 
/* 2928:2397 */             if ((0x2 & l) != 0L) {
/* 2929:2398 */               this.jjstateSet[(this.jjnewStateCnt++)] = 94;
/* 2930:     */             }
/* 2931:     */             break;
/* 2932:     */           case 96: 
/* 2933:2401 */             if ((0x100000 & l) != 0L) {
/* 2934:2402 */               this.jjstateSet[(this.jjnewStateCnt++)] = 95;
/* 2935:     */             }
/* 2936:     */             break;
/* 2937:     */           case 97: 
/* 2938:2405 */             if ((0x40000 & l) != 0L) {
/* 2939:2406 */               this.jjstateSet[(this.jjnewStateCnt++)] = 96;
/* 2940:     */             }
/* 2941:     */             break;
/* 2942:     */           case 98: 
/* 2943:2409 */             if ((0x8000 & l) != 0L) {
/* 2944:2410 */               this.jjstateSet[(this.jjnewStateCnt++)] = 97;
/* 2945:     */             }
/* 2946:     */             break;
/* 2947:     */           case 99: 
/* 2948:2413 */             if ((0x10000 & l) != 0L) {
/* 2949:2414 */               this.jjstateSet[(this.jjnewStateCnt++)] = 98;
/* 2950:     */             }
/* 2951:     */             break;
/* 2952:     */           case 100: 
/* 2953:2417 */             if ((0x2000 & l) != 0L) {
/* 2954:2418 */               this.jjstateSet[(this.jjnewStateCnt++)] = 99;
/* 2955:     */             }
/* 2956:     */             break;
/* 2957:     */           case 101: 
/* 2958:2421 */             if ((0x200 & l) != 0L) {
/* 2959:2422 */               this.jjstateSet[(this.jjnewStateCnt++)] = 100;
/* 2960:     */             }
/* 2961:     */             break;
/* 2962:     */           case 102: 
/* 2963:2425 */             if ((0x200000 & l) != 0L) {
/* 2964:2426 */               jjAddStates(349, 350);
/* 2965:     */             }
/* 2966:     */             break;
/* 2967:     */           case 104: 
/* 2968:2429 */             if ((0xEFFFFFFF & l) != 0L) {
/* 2969:2430 */               jjCheckNAddStates(171, 174);
/* 2970:     */             }
/* 2971:     */             break;
/* 2972:     */           case 107: 
/* 2973:2433 */             if (this.curChar == '\\') {
/* 2974:2434 */               jjAddStates(361, 362);
/* 2975:     */             }
/* 2976:     */             break;
/* 2977:     */           case 108: 
/* 2978:2437 */             if ((0xFFFFFFFF & l) != 0L) {
/* 2979:2438 */               jjCheckNAddStates(171, 174);
/* 2980:     */             }
/* 2981:     */             break;
/* 2982:     */           case 109: 
/* 2983:2441 */             if ((0x7E & l) != 0L) {
/* 2984:2442 */               jjCheckNAddStates(175, 183);
/* 2985:     */             }
/* 2986:     */             break;
/* 2987:     */           case 110: 
/* 2988:2445 */             if ((0x7E & l) != 0L) {
/* 2989:2446 */               jjCheckNAddStates(184, 187);
/* 2990:     */             }
/* 2991:     */             break;
/* 2992:     */           case 112: 
/* 2993:     */           case 114: 
/* 2994:     */           case 117: 
/* 2995:     */           case 121: 
/* 2996:2452 */             if ((0x7E & l) != 0L) {
/* 2997:2453 */               jjCheckNAdd(110);
/* 2998:     */             }
/* 2999:     */             break;
/* 3000:     */           case 113: 
/* 3001:2456 */             if ((0x7E & l) != 0L) {
/* 3002:2457 */               this.jjstateSet[(this.jjnewStateCnt++)] = 114;
/* 3003:     */             }
/* 3004:     */             break;
/* 3005:     */           case 115: 
/* 3006:2460 */             if ((0x7E & l) != 0L) {
/* 3007:2461 */               this.jjstateSet[(this.jjnewStateCnt++)] = 116;
/* 3008:     */             }
/* 3009:     */             break;
/* 3010:     */           case 116: 
/* 3011:2464 */             if ((0x7E & l) != 0L) {
/* 3012:2465 */               this.jjstateSet[(this.jjnewStateCnt++)] = 117;
/* 3013:     */             }
/* 3014:     */             break;
/* 3015:     */           case 118: 
/* 3016:2468 */             if ((0x7E & l) != 0L) {
/* 3017:2469 */               this.jjstateSet[(this.jjnewStateCnt++)] = 119;
/* 3018:     */             }
/* 3019:     */             break;
/* 3020:     */           case 119: 
/* 3021:2472 */             if ((0x7E & l) != 0L) {
/* 3022:2473 */               this.jjstateSet[(this.jjnewStateCnt++)] = 120;
/* 3023:     */             }
/* 3024:     */             break;
/* 3025:     */           case 120: 
/* 3026:2476 */             if ((0x7E & l) != 0L) {
/* 3027:2477 */               this.jjstateSet[(this.jjnewStateCnt++)] = 121;
/* 3028:     */             }
/* 3029:     */             break;
/* 3030:     */           case 123: 
/* 3031:2480 */             if ((0xEFFFFFFF & l) != 0L) {
/* 3032:2481 */               jjCheckNAddStates(188, 190);
/* 3033:     */             }
/* 3034:     */             break;
/* 3035:     */           case 125: 
/* 3036:2484 */             if (this.curChar == '\\') {
/* 3037:2485 */               jjAddStates(363, 366);
/* 3038:     */             }
/* 3039:     */             break;
/* 3040:     */           case 129: 
/* 3041:2488 */             if ((0xFFFFFFFF & l) != 0L) {
/* 3042:2489 */               jjCheckNAddStates(188, 190);
/* 3043:     */             }
/* 3044:     */             break;
/* 3045:     */           case 130: 
/* 3046:2492 */             if ((0x7E & l) != 0L) {
/* 3047:2493 */               jjCheckNAddStates(191, 199);
/* 3048:     */             }
/* 3049:     */             break;
/* 3050:     */           case 131: 
/* 3051:2496 */             if ((0x7E & l) != 0L) {
/* 3052:2497 */               jjCheckNAddStates(200, 203);
/* 3053:     */             }
/* 3054:     */             break;
/* 3055:     */           case 133: 
/* 3056:     */           case 135: 
/* 3057:     */           case 138: 
/* 3058:     */           case 142: 
/* 3059:2503 */             if ((0x7E & l) != 0L) {
/* 3060:2504 */               jjCheckNAdd(131);
/* 3061:     */             }
/* 3062:     */             break;
/* 3063:     */           case 134: 
/* 3064:2507 */             if ((0x7E & l) != 0L) {
/* 3065:2508 */               this.jjstateSet[(this.jjnewStateCnt++)] = 135;
/* 3066:     */             }
/* 3067:     */             break;
/* 3068:     */           case 136: 
/* 3069:2511 */             if ((0x7E & l) != 0L) {
/* 3070:2512 */               this.jjstateSet[(this.jjnewStateCnt++)] = 137;
/* 3071:     */             }
/* 3072:     */             break;
/* 3073:     */           case 137: 
/* 3074:2515 */             if ((0x7E & l) != 0L) {
/* 3075:2516 */               this.jjstateSet[(this.jjnewStateCnt++)] = 138;
/* 3076:     */             }
/* 3077:     */             break;
/* 3078:     */           case 139: 
/* 3079:2519 */             if ((0x7E & l) != 0L) {
/* 3080:2520 */               this.jjstateSet[(this.jjnewStateCnt++)] = 140;
/* 3081:     */             }
/* 3082:     */             break;
/* 3083:     */           case 140: 
/* 3084:2523 */             if ((0x7E & l) != 0L) {
/* 3085:2524 */               this.jjstateSet[(this.jjnewStateCnt++)] = 141;
/* 3086:     */             }
/* 3087:     */             break;
/* 3088:     */           case 141: 
/* 3089:2527 */             if ((0x7E & l) != 0L) {
/* 3090:2528 */               this.jjstateSet[(this.jjnewStateCnt++)] = 142;
/* 3091:     */             }
/* 3092:     */             break;
/* 3093:     */           case 144: 
/* 3094:2531 */             if ((0xEFFFFFFF & l) != 0L) {
/* 3095:2532 */               jjCheckNAddStates(204, 206);
/* 3096:     */             }
/* 3097:     */             break;
/* 3098:     */           case 146: 
/* 3099:2535 */             if (this.curChar == '\\') {
/* 3100:2536 */               jjAddStates(367, 370);
/* 3101:     */             }
/* 3102:     */             break;
/* 3103:     */           case 150: 
/* 3104:2539 */             if ((0xFFFFFFFF & l) != 0L) {
/* 3105:2540 */               jjCheckNAddStates(204, 206);
/* 3106:     */             }
/* 3107:     */             break;
/* 3108:     */           case 151: 
/* 3109:2543 */             if ((0x7E & l) != 0L) {
/* 3110:2544 */               jjCheckNAddStates(207, 215);
/* 3111:     */             }
/* 3112:     */             break;
/* 3113:     */           case 152: 
/* 3114:2547 */             if ((0x7E & l) != 0L) {
/* 3115:2548 */               jjCheckNAddStates(216, 219);
/* 3116:     */             }
/* 3117:     */             break;
/* 3118:     */           case 154: 
/* 3119:     */           case 156: 
/* 3120:     */           case 159: 
/* 3121:     */           case 163: 
/* 3122:2554 */             if ((0x7E & l) != 0L) {
/* 3123:2555 */               jjCheckNAdd(152);
/* 3124:     */             }
/* 3125:     */             break;
/* 3126:     */           case 155: 
/* 3127:2558 */             if ((0x7E & l) != 0L) {
/* 3128:2559 */               this.jjstateSet[(this.jjnewStateCnt++)] = 156;
/* 3129:     */             }
/* 3130:     */             break;
/* 3131:     */           case 157: 
/* 3132:2562 */             if ((0x7E & l) != 0L) {
/* 3133:2563 */               this.jjstateSet[(this.jjnewStateCnt++)] = 158;
/* 3134:     */             }
/* 3135:     */             break;
/* 3136:     */           case 158: 
/* 3137:2566 */             if ((0x7E & l) != 0L) {
/* 3138:2567 */               this.jjstateSet[(this.jjnewStateCnt++)] = 159;
/* 3139:     */             }
/* 3140:     */             break;
/* 3141:     */           case 160: 
/* 3142:2570 */             if ((0x7E & l) != 0L) {
/* 3143:2571 */               this.jjstateSet[(this.jjnewStateCnt++)] = 161;
/* 3144:     */             }
/* 3145:     */             break;
/* 3146:     */           case 161: 
/* 3147:2574 */             if ((0x7E & l) != 0L) {
/* 3148:2575 */               this.jjstateSet[(this.jjnewStateCnt++)] = 162;
/* 3149:     */             }
/* 3150:     */             break;
/* 3151:     */           case 162: 
/* 3152:2578 */             if ((0x7E & l) != 0L) {
/* 3153:2579 */               this.jjstateSet[(this.jjnewStateCnt++)] = 163;
/* 3154:     */             }
/* 3155:     */             break;
/* 3156:     */           case 165: 
/* 3157:2582 */             if ((0x1000 & l) != 0L) {
/* 3158:2583 */               this.jjstateSet[(this.jjnewStateCnt++)] = 103;
/* 3159:     */             }
/* 3160:     */             break;
/* 3161:     */           case 166: 
/* 3162:2586 */             if ((0x40000 & l) != 0L) {
/* 3163:2587 */               this.jjstateSet[(this.jjnewStateCnt++)] = 165;
/* 3164:     */             }
/* 3165:     */             break;
/* 3166:     */           case 169: 
/* 3167:2590 */             if ((0x7E & l) != 0L)
/* 3168:     */             {
/* 3169:2592 */               if (kind > 59) {
/* 3170:2593 */                 kind = 59;
/* 3171:     */               }
/* 3172:2594 */               jjCheckNAddStates(230, 238);
/* 3173:     */             }
/* 3174:2595 */             break;
/* 3175:     */           case 170: 
/* 3176:2597 */             if ((0x7E & l) != 0L) {
/* 3177:2598 */               jjCheckNAdd(171);
/* 3178:     */             }
/* 3179:     */             break;
/* 3180:     */           case 172: 
/* 3181:2601 */             if ((0x7E & l) != 0L)
/* 3182:     */             {
/* 3183:2603 */               if (kind > 59) {
/* 3184:2604 */                 kind = 59;
/* 3185:     */               }
/* 3186:2605 */               jjCheckNAddStates(239, 243);
/* 3187:     */             }
/* 3188:2606 */             break;
/* 3189:     */           case 173: 
/* 3190:2608 */             if (((0x7E & l) != 0L) && (kind > 59)) {
/* 3191:2609 */               kind = 59;
/* 3192:     */             }
/* 3193:     */             break;
/* 3194:     */           case 174: 
/* 3195:     */           case 176: 
/* 3196:     */           case 179: 
/* 3197:     */           case 183: 
/* 3198:2615 */             if ((0x7E & l) != 0L) {
/* 3199:2616 */               jjCheckNAdd(173);
/* 3200:     */             }
/* 3201:     */             break;
/* 3202:     */           case 175: 
/* 3203:2619 */             if ((0x7E & l) != 0L) {
/* 3204:2620 */               this.jjstateSet[(this.jjnewStateCnt++)] = 176;
/* 3205:     */             }
/* 3206:     */             break;
/* 3207:     */           case 177: 
/* 3208:2623 */             if ((0x7E & l) != 0L) {
/* 3209:2624 */               this.jjstateSet[(this.jjnewStateCnt++)] = 178;
/* 3210:     */             }
/* 3211:     */             break;
/* 3212:     */           case 178: 
/* 3213:2627 */             if ((0x7E & l) != 0L) {
/* 3214:2628 */               this.jjstateSet[(this.jjnewStateCnt++)] = 179;
/* 3215:     */             }
/* 3216:     */             break;
/* 3217:     */           case 180: 
/* 3218:2631 */             if ((0x7E & l) != 0L) {
/* 3219:2632 */               this.jjstateSet[(this.jjnewStateCnt++)] = 181;
/* 3220:     */             }
/* 3221:     */             break;
/* 3222:     */           case 181: 
/* 3223:2635 */             if ((0x7E & l) != 0L) {
/* 3224:2636 */               this.jjstateSet[(this.jjnewStateCnt++)] = 182;
/* 3225:     */             }
/* 3226:     */             break;
/* 3227:     */           case 182: 
/* 3228:2639 */             if ((0x7E & l) != 0L) {
/* 3229:2640 */               this.jjstateSet[(this.jjnewStateCnt++)] = 183;
/* 3230:     */             }
/* 3231:     */             break;
/* 3232:     */           case 184: 
/* 3233:     */           case 186: 
/* 3234:     */           case 189: 
/* 3235:     */           case 193: 
/* 3236:2646 */             if ((0x7E & l) != 0L) {
/* 3237:2647 */               jjCheckNAdd(170);
/* 3238:     */             }
/* 3239:     */             break;
/* 3240:     */           case 185: 
/* 3241:2650 */             if ((0x7E & l) != 0L) {
/* 3242:2651 */               this.jjstateSet[(this.jjnewStateCnt++)] = 186;
/* 3243:     */             }
/* 3244:     */             break;
/* 3245:     */           case 187: 
/* 3246:2654 */             if ((0x7E & l) != 0L) {
/* 3247:2655 */               this.jjstateSet[(this.jjnewStateCnt++)] = 188;
/* 3248:     */             }
/* 3249:     */             break;
/* 3250:     */           case 188: 
/* 3251:2658 */             if ((0x7E & l) != 0L) {
/* 3252:2659 */               this.jjstateSet[(this.jjnewStateCnt++)] = 189;
/* 3253:     */             }
/* 3254:     */             break;
/* 3255:     */           case 190: 
/* 3256:2662 */             if ((0x7E & l) != 0L) {
/* 3257:2663 */               this.jjstateSet[(this.jjnewStateCnt++)] = 191;
/* 3258:     */             }
/* 3259:     */             break;
/* 3260:     */           case 191: 
/* 3261:2666 */             if ((0x7E & l) != 0L) {
/* 3262:2667 */               this.jjstateSet[(this.jjnewStateCnt++)] = 192;
/* 3263:     */             }
/* 3264:     */             break;
/* 3265:     */           case 192: 
/* 3266:2670 */             if ((0x7E & l) != 0L) {
/* 3267:2671 */               this.jjstateSet[(this.jjnewStateCnt++)] = 193;
/* 3268:     */             }
/* 3269:     */             break;
/* 3270:     */           case 194: 
/* 3271:2674 */             if ((0x7E & l) != 0L)
/* 3272:     */             {
/* 3273:2676 */               if (kind > 59) {
/* 3274:2677 */                 kind = 59;
/* 3275:     */               }
/* 3276:2678 */               jjCheckNAddStates(244, 246);
/* 3277:     */             }
/* 3278:2679 */             break;
/* 3279:     */           case 195: 
/* 3280:2681 */             if ((0x7E & l) != 0L)
/* 3281:     */             {
/* 3282:2683 */               if (kind > 59) {
/* 3283:2684 */                 kind = 59;
/* 3284:     */               }
/* 3285:2685 */               jjCheckNAddStates(247, 249);
/* 3286:     */             }
/* 3287:2686 */             break;
/* 3288:     */           case 196: 
/* 3289:2688 */             if ((0x7E & l) != 0L)
/* 3290:     */             {
/* 3291:2690 */               if (kind > 59) {
/* 3292:2691 */                 kind = 59;
/* 3293:     */               }
/* 3294:2692 */               jjCheckNAddStates(250, 252);
/* 3295:     */             }
/* 3296:2693 */             break;
/* 3297:     */           case 199: 
/* 3298:2695 */             if ((0x7E & l) != 0L)
/* 3299:     */             {
/* 3300:2697 */               if (kind > 59) {
/* 3301:2698 */                 kind = 59;
/* 3302:     */               }
/* 3303:2699 */               jjCheckNAddTwoStates(168, 173);
/* 3304:     */             }
/* 3305:2700 */             break;
/* 3306:     */           case 226: 
/* 3307:2702 */             if (((0x2000 & l) != 0L) && (kind > 36)) {
/* 3308:2703 */               kind = 36;
/* 3309:     */             }
/* 3310:     */             break;
/* 3311:     */           case 227: 
/* 3312:2706 */             if ((0x20 & l) != 0L) {
/* 3313:2707 */               this.jjstateSet[(this.jjnewStateCnt++)] = 226;
/* 3314:     */             }
/* 3315:     */             break;
/* 3316:     */           case 229: 
/* 3317:2710 */             if (((0x1000000 & l) != 0L) && (kind > 37)) {
/* 3318:2711 */               kind = 37;
/* 3319:     */             }
/* 3320:     */             break;
/* 3321:     */           case 230: 
/* 3322:2714 */             if ((0x20 & l) != 0L) {
/* 3323:2715 */               this.jjstateSet[(this.jjnewStateCnt++)] = 229;
/* 3324:     */             }
/* 3325:     */             break;
/* 3326:     */           case 232: 
/* 3327:2718 */             if (((0x1000000 & l) != 0L) && (kind > 38)) {
/* 3328:2719 */               kind = 38;
/* 3329:     */             }
/* 3330:     */             break;
/* 3331:     */           case 233: 
/* 3332:2722 */             if ((0x10000 & l) != 0L) {
/* 3333:2723 */               this.jjstateSet[(this.jjnewStateCnt++)] = 232;
/* 3334:     */             }
/* 3335:     */             break;
/* 3336:     */           case 235: 
/* 3337:2726 */             if (((0x2000 & l) != 0L) && (kind > 39)) {
/* 3338:2727 */               kind = 39;
/* 3339:     */             }
/* 3340:     */             break;
/* 3341:     */           case 236: 
/* 3342:2730 */             if ((0x8 & l) != 0L) {
/* 3343:2731 */               this.jjstateSet[(this.jjnewStateCnt++)] = 235;
/* 3344:     */             }
/* 3345:     */             break;
/* 3346:     */           case 238: 
/* 3347:2734 */             if (((0x2000 & l) != 0L) && (kind > 40)) {
/* 3348:2735 */               kind = 40;
/* 3349:     */             }
/* 3350:     */             break;
/* 3351:     */           case 239: 
/* 3352:2738 */             if ((0x2000 & l) != 0L) {
/* 3353:2739 */               this.jjstateSet[(this.jjnewStateCnt++)] = 238;
/* 3354:     */             }
/* 3355:     */             break;
/* 3356:     */           case 241: 
/* 3357:2742 */             if (((0x4000 & l) != 0L) && (kind > 41)) {
/* 3358:2743 */               kind = 41;
/* 3359:     */             }
/* 3360:     */             break;
/* 3361:     */           case 242: 
/* 3362:2746 */             if ((0x200 & l) != 0L) {
/* 3363:2747 */               this.jjstateSet[(this.jjnewStateCnt++)] = 241;
/* 3364:     */             }
/* 3365:     */             break;
/* 3366:     */           case 244: 
/* 3367:2750 */             if (((0x100000 & l) != 0L) && (kind > 42)) {
/* 3368:2751 */               kind = 42;
/* 3369:     */             }
/* 3370:     */             break;
/* 3371:     */           case 245: 
/* 3372:2754 */             if ((0x10000 & l) != 0L) {
/* 3373:2755 */               this.jjstateSet[(this.jjnewStateCnt++)] = 244;
/* 3374:     */             }
/* 3375:     */             break;
/* 3376:     */           case 247: 
/* 3377:2758 */             if (((0x8 & l) != 0L) && (kind > 43)) {
/* 3378:2759 */               kind = 43;
/* 3379:     */             }
/* 3380:     */             break;
/* 3381:     */           case 248: 
/* 3382:2762 */             if ((0x10000 & l) != 0L) {
/* 3383:2763 */               this.jjstateSet[(this.jjnewStateCnt++)] = 247;
/* 3384:     */             }
/* 3385:     */             break;
/* 3386:     */           case 250: 
/* 3387:2766 */             if (((0x80 & l) != 0L) && (kind > 44)) {
/* 3388:2767 */               kind = 44;
/* 3389:     */             }
/* 3390:     */             break;
/* 3391:     */           case 251: 
/* 3392:2770 */             if ((0x20 & l) != 0L) {
/* 3393:2771 */               this.jjstateSet[(this.jjnewStateCnt++)] = 250;
/* 3394:     */             }
/* 3395:     */             break;
/* 3396:     */           case 252: 
/* 3397:2774 */             if ((0x10 & l) != 0L) {
/* 3398:2775 */               this.jjstateSet[(this.jjnewStateCnt++)] = 251;
/* 3399:     */             }
/* 3400:     */             break;
/* 3401:     */           case 254: 
/* 3402:2778 */             if (((0x10 & l) != 0L) && (kind > 45)) {
/* 3403:2779 */               kind = 45;
/* 3404:     */             }
/* 3405:     */             break;
/* 3406:     */           case 255: 
/* 3407:2782 */             if ((0x2 & l) != 0L) {
/* 3408:2783 */               this.jjstateSet[(this.jjnewStateCnt++)] = 254;
/* 3409:     */             }
/* 3410:     */             break;
/* 3411:     */           case 256: 
/* 3412:2786 */             if ((0x40000 & l) != 0L) {
/* 3413:2787 */               this.jjstateSet[(this.jjnewStateCnt++)] = 255;
/* 3414:     */             }
/* 3415:     */             break;
/* 3416:     */           case 258: 
/* 3417:2790 */             if (((0x10 & l) != 0L) && (kind > 46)) {
/* 3418:2791 */               kind = 46;
/* 3419:     */             }
/* 3420:     */             break;
/* 3421:     */           case 259: 
/* 3422:2794 */             if ((0x2 & l) != 0L) {
/* 3423:2795 */               this.jjstateSet[(this.jjnewStateCnt++)] = 258;
/* 3424:     */             }
/* 3425:     */             break;
/* 3426:     */           case 260: 
/* 3427:2798 */             if ((0x40000 & l) != 0L) {
/* 3428:2799 */               this.jjstateSet[(this.jjnewStateCnt++)] = 259;
/* 3429:     */             }
/* 3430:     */             break;
/* 3431:     */           case 261: 
/* 3432:2802 */             if ((0x80 & l) != 0L) {
/* 3433:2803 */               this.jjstateSet[(this.jjnewStateCnt++)] = 260;
/* 3434:     */             }
/* 3435:     */             break;
/* 3436:     */           case 263: 
/* 3437:2806 */             if (((0x80000 & l) != 0L) && (kind > 47)) {
/* 3438:2807 */               kind = 47;
/* 3439:     */             }
/* 3440:     */             break;
/* 3441:     */           case 264: 
/* 3442:2810 */             if ((0x2000 & l) != 0L) {
/* 3443:2811 */               this.jjstateSet[(this.jjnewStateCnt++)] = 263;
/* 3444:     */             }
/* 3445:     */             break;
/* 3446:     */           case 266: 
/* 3447:2814 */             if (((0x80000 & l) != 0L) && (kind > 48)) {
/* 3448:2815 */               kind = 48;
/* 3449:     */             }
/* 3450:     */             break;
/* 3451:     */           case 268: 
/* 3452:2818 */             if (((0x4000000 & l) != 0L) && (kind > 49)) {
/* 3453:2819 */               kind = 49;
/* 3454:     */             }
/* 3455:     */             break;
/* 3456:     */           case 269: 
/* 3457:2822 */             if ((0x100 & l) != 0L) {
/* 3458:2823 */               this.jjstateSet[(this.jjnewStateCnt++)] = 268;
/* 3459:     */             }
/* 3460:     */             break;
/* 3461:     */           case 271: 
/* 3462:2826 */             if (((0x4000000 & l) != 0L) && (kind > 50)) {
/* 3463:2827 */               kind = 50;
/* 3464:     */             }
/* 3465:     */             break;
/* 3466:     */           case 272: 
/* 3467:2830 */             if ((0x100 & l) != 0L) {
/* 3468:2831 */               this.jjstateSet[(this.jjnewStateCnt++)] = 271;
/* 3469:     */             }
/* 3470:     */             break;
/* 3471:     */           case 273: 
/* 3472:2834 */             if ((0x800 & l) != 0L) {
/* 3473:2835 */               this.jjstateSet[(this.jjnewStateCnt++)] = 272;
/* 3474:     */             }
/* 3475:     */             break;
/* 3476:     */           case 275: 
/* 3477:     */           case 276: 
/* 3478:2839 */             if ((0x87FFFFFE & l) != 0L)
/* 3479:     */             {
/* 3480:2841 */               if (kind > 51) {
/* 3481:2842 */                 kind = 51;
/* 3482:     */               }
/* 3483:2843 */               jjCheckNAddTwoStates(276, 277);
/* 3484:     */             }
/* 3485:2844 */             break;
/* 3486:     */           case 277: 
/* 3487:2846 */             if (this.curChar == '\\') {
/* 3488:2847 */               jjCheckNAddTwoStates(278, 279);
/* 3489:     */             }
/* 3490:     */             break;
/* 3491:     */           case 278: 
/* 3492:2850 */             if ((0xFFFFFFFF & l) != 0L)
/* 3493:     */             {
/* 3494:2852 */               if (kind > 51) {
/* 3495:2853 */                 kind = 51;
/* 3496:     */               }
/* 3497:2854 */               jjCheckNAddTwoStates(276, 277);
/* 3498:     */             }
/* 3499:2855 */             break;
/* 3500:     */           case 279: 
/* 3501:2857 */             if ((0x7E & l) != 0L)
/* 3502:     */             {
/* 3503:2859 */               if (kind > 51) {
/* 3504:2860 */                 kind = 51;
/* 3505:     */               }
/* 3506:2861 */               jjCheckNAddStates(265, 272);
/* 3507:     */             }
/* 3508:2862 */             break;
/* 3509:     */           case 280: 
/* 3510:2864 */             if ((0x7E & l) != 0L)
/* 3511:     */             {
/* 3512:2866 */               if (kind > 51) {
/* 3513:2867 */                 kind = 51;
/* 3514:     */               }
/* 3515:2868 */               jjCheckNAddStates(273, 275);
/* 3516:     */             }
/* 3517:2869 */             break;
/* 3518:     */           case 282: 
/* 3519:     */           case 284: 
/* 3520:     */           case 287: 
/* 3521:     */           case 291: 
/* 3522:2874 */             if ((0x7E & l) != 0L) {
/* 3523:2875 */               jjCheckNAdd(280);
/* 3524:     */             }
/* 3525:     */             break;
/* 3526:     */           case 283: 
/* 3527:2878 */             if ((0x7E & l) != 0L) {
/* 3528:2879 */               this.jjstateSet[(this.jjnewStateCnt++)] = 284;
/* 3529:     */             }
/* 3530:     */             break;
/* 3531:     */           case 285: 
/* 3532:2882 */             if ((0x7E & l) != 0L) {
/* 3533:2883 */               this.jjstateSet[(this.jjnewStateCnt++)] = 286;
/* 3534:     */             }
/* 3535:     */             break;
/* 3536:     */           case 286: 
/* 3537:2886 */             if ((0x7E & l) != 0L) {
/* 3538:2887 */               this.jjstateSet[(this.jjnewStateCnt++)] = 287;
/* 3539:     */             }
/* 3540:     */             break;
/* 3541:     */           case 288: 
/* 3542:2890 */             if ((0x7E & l) != 0L) {
/* 3543:2891 */               this.jjstateSet[(this.jjnewStateCnt++)] = 289;
/* 3544:     */             }
/* 3545:     */             break;
/* 3546:     */           case 289: 
/* 3547:2894 */             if ((0x7E & l) != 0L) {
/* 3548:2895 */               this.jjstateSet[(this.jjnewStateCnt++)] = 290;
/* 3549:     */             }
/* 3550:     */             break;
/* 3551:     */           case 290: 
/* 3552:2898 */             if ((0x7E & l) != 0L) {
/* 3553:2899 */               this.jjstateSet[(this.jjnewStateCnt++)] = 291;
/* 3554:     */             }
/* 3555:     */             break;
/* 3556:     */           case 292: 
/* 3557:2902 */             if (this.curChar == '\\') {
/* 3558:2903 */               jjCheckNAddTwoStates(278, 293);
/* 3559:     */             }
/* 3560:     */             break;
/* 3561:     */           case 293: 
/* 3562:2906 */             if ((0x7E & l) != 0L)
/* 3563:     */             {
/* 3564:2908 */               if (kind > 51) {
/* 3565:2909 */                 kind = 51;
/* 3566:     */               }
/* 3567:2910 */               jjCheckNAddStates(276, 283);
/* 3568:     */             }
/* 3569:2911 */             break;
/* 3570:     */           case 294: 
/* 3571:2913 */             if ((0x7E & l) != 0L)
/* 3572:     */             {
/* 3573:2915 */               if (kind > 51) {
/* 3574:2916 */                 kind = 51;
/* 3575:     */               }
/* 3576:2917 */               jjCheckNAddStates(284, 286);
/* 3577:     */             }
/* 3578:2918 */             break;
/* 3579:     */           case 295: 
/* 3580:     */           case 297: 
/* 3581:     */           case 300: 
/* 3582:     */           case 304: 
/* 3583:2923 */             if ((0x7E & l) != 0L) {
/* 3584:2924 */               jjCheckNAdd(294);
/* 3585:     */             }
/* 3586:     */             break;
/* 3587:     */           case 296: 
/* 3588:2927 */             if ((0x7E & l) != 0L) {
/* 3589:2928 */               this.jjstateSet[(this.jjnewStateCnt++)] = 297;
/* 3590:     */             }
/* 3591:     */             break;
/* 3592:     */           case 298: 
/* 3593:2931 */             if ((0x7E & l) != 0L) {
/* 3594:2932 */               this.jjstateSet[(this.jjnewStateCnt++)] = 299;
/* 3595:     */             }
/* 3596:     */             break;
/* 3597:     */           case 299: 
/* 3598:2935 */             if ((0x7E & l) != 0L) {
/* 3599:2936 */               this.jjstateSet[(this.jjnewStateCnt++)] = 300;
/* 3600:     */             }
/* 3601:     */             break;
/* 3602:     */           case 301: 
/* 3603:2939 */             if ((0x7E & l) != 0L) {
/* 3604:2940 */               this.jjstateSet[(this.jjnewStateCnt++)] = 302;
/* 3605:     */             }
/* 3606:     */             break;
/* 3607:     */           case 302: 
/* 3608:2943 */             if ((0x7E & l) != 0L) {
/* 3609:2944 */               this.jjstateSet[(this.jjnewStateCnt++)] = 303;
/* 3610:     */             }
/* 3611:     */             break;
/* 3612:     */           case 303: 
/* 3613:2947 */             if ((0x7E & l) != 0L) {
/* 3614:2948 */               this.jjstateSet[(this.jjnewStateCnt++)] = 304;
/* 3615:     */             }
/* 3616:     */             break;
/* 3617:     */           case 309: 
/* 3618:2951 */             if ((0x87FFFFFE & l) != 0L)
/* 3619:     */             {
/* 3620:2953 */               if (kind > 56) {
/* 3621:2954 */                 kind = 56;
/* 3622:     */               }
/* 3623:2955 */               jjCheckNAddStates(338, 342);
/* 3624:     */             }
/* 3625:2956 */             break;
/* 3626:     */           case 310: 
/* 3627:2958 */             if ((0x87FFFFFE & l) != 0L) {
/* 3628:2959 */               jjCheckNAddStates(103, 105);
/* 3629:     */             }
/* 3630:     */             break;
/* 3631:     */           case 312: 
/* 3632:2962 */             if (this.curChar == '\\') {
/* 3633:2963 */               jjCheckNAddTwoStates(313, 314);
/* 3634:     */             }
/* 3635:     */             break;
/* 3636:     */           case 313: 
/* 3637:2966 */             if ((0xFFFFFFFF & l) != 0L) {
/* 3638:2967 */               jjCheckNAddStates(103, 105);
/* 3639:     */             }
/* 3640:     */             break;
/* 3641:     */           case 314: 
/* 3642:2970 */             if ((0x7E & l) != 0L) {
/* 3643:2971 */               jjCheckNAddStates(287, 295);
/* 3644:     */             }
/* 3645:     */             break;
/* 3646:     */           case 315: 
/* 3647:2974 */             if ((0x7E & l) != 0L) {
/* 3648:2975 */               jjCheckNAddStates(296, 299);
/* 3649:     */             }
/* 3650:     */             break;
/* 3651:     */           case 317: 
/* 3652:     */           case 319: 
/* 3653:     */           case 322: 
/* 3654:     */           case 326: 
/* 3655:2981 */             if ((0x7E & l) != 0L) {
/* 3656:2982 */               jjCheckNAdd(315);
/* 3657:     */             }
/* 3658:     */             break;
/* 3659:     */           case 318: 
/* 3660:2985 */             if ((0x7E & l) != 0L) {
/* 3661:2986 */               this.jjstateSet[(this.jjnewStateCnt++)] = 319;
/* 3662:     */             }
/* 3663:     */             break;
/* 3664:     */           case 320: 
/* 3665:2989 */             if ((0x7E & l) != 0L) {
/* 3666:2990 */               this.jjstateSet[(this.jjnewStateCnt++)] = 321;
/* 3667:     */             }
/* 3668:     */             break;
/* 3669:     */           case 321: 
/* 3670:2993 */             if ((0x7E & l) != 0L) {
/* 3671:2994 */               this.jjstateSet[(this.jjnewStateCnt++)] = 322;
/* 3672:     */             }
/* 3673:     */             break;
/* 3674:     */           case 323: 
/* 3675:2997 */             if ((0x7E & l) != 0L) {
/* 3676:2998 */               this.jjstateSet[(this.jjnewStateCnt++)] = 324;
/* 3677:     */             }
/* 3678:     */             break;
/* 3679:     */           case 324: 
/* 3680:3001 */             if ((0x7E & l) != 0L) {
/* 3681:3002 */               this.jjstateSet[(this.jjnewStateCnt++)] = 325;
/* 3682:     */             }
/* 3683:     */             break;
/* 3684:     */           case 325: 
/* 3685:3005 */             if ((0x7E & l) != 0L) {
/* 3686:3006 */               this.jjstateSet[(this.jjnewStateCnt++)] = 326;
/* 3687:     */             }
/* 3688:     */             break;
/* 3689:     */           case 327: 
/* 3690:3009 */             if ((0x87FFFFFE & l) != 0L)
/* 3691:     */             {
/* 3692:3011 */               if (kind > 56) {
/* 3693:3012 */                 kind = 56;
/* 3694:     */               }
/* 3695:3013 */               jjCheckNAddTwoStates(327, 328);
/* 3696:     */             }
/* 3697:3014 */             break;
/* 3698:     */           case 328: 
/* 3699:3016 */             if (this.curChar == '\\') {
/* 3700:3017 */               jjCheckNAddTwoStates(329, 330);
/* 3701:     */             }
/* 3702:     */             break;
/* 3703:     */           case 329: 
/* 3704:3020 */             if ((0xFFFFFFFF & l) != 0L)
/* 3705:     */             {
/* 3706:3022 */               if (kind > 56) {
/* 3707:3023 */                 kind = 56;
/* 3708:     */               }
/* 3709:3024 */               jjCheckNAddTwoStates(327, 328);
/* 3710:     */             }
/* 3711:3025 */             break;
/* 3712:     */           case 330: 
/* 3713:3027 */             if ((0x7E & l) != 0L)
/* 3714:     */             {
/* 3715:3029 */               if (kind > 56) {
/* 3716:3030 */                 kind = 56;
/* 3717:     */               }
/* 3718:3031 */               jjCheckNAddStates(300, 307);
/* 3719:     */             }
/* 3720:3032 */             break;
/* 3721:     */           case 331: 
/* 3722:3034 */             if ((0x7E & l) != 0L)
/* 3723:     */             {
/* 3724:3036 */               if (kind > 56) {
/* 3725:3037 */                 kind = 56;
/* 3726:     */               }
/* 3727:3038 */               jjCheckNAddStates(308, 310);
/* 3728:     */             }
/* 3729:3039 */             break;
/* 3730:     */           case 333: 
/* 3731:     */           case 335: 
/* 3732:     */           case 338: 
/* 3733:     */           case 342: 
/* 3734:3044 */             if ((0x7E & l) != 0L) {
/* 3735:3045 */               jjCheckNAdd(331);
/* 3736:     */             }
/* 3737:     */             break;
/* 3738:     */           case 334: 
/* 3739:3048 */             if ((0x7E & l) != 0L) {
/* 3740:3049 */               this.jjstateSet[(this.jjnewStateCnt++)] = 335;
/* 3741:     */             }
/* 3742:     */             break;
/* 3743:     */           case 336: 
/* 3744:3052 */             if ((0x7E & l) != 0L) {
/* 3745:3053 */               this.jjstateSet[(this.jjnewStateCnt++)] = 337;
/* 3746:     */             }
/* 3747:     */             break;
/* 3748:     */           case 337: 
/* 3749:3056 */             if ((0x7E & l) != 0L) {
/* 3750:3057 */               this.jjstateSet[(this.jjnewStateCnt++)] = 338;
/* 3751:     */             }
/* 3752:     */             break;
/* 3753:     */           case 339: 
/* 3754:3060 */             if ((0x7E & l) != 0L) {
/* 3755:3061 */               this.jjstateSet[(this.jjnewStateCnt++)] = 340;
/* 3756:     */             }
/* 3757:     */             break;
/* 3758:     */           case 340: 
/* 3759:3064 */             if ((0x7E & l) != 0L) {
/* 3760:3065 */               this.jjstateSet[(this.jjnewStateCnt++)] = 341;
/* 3761:     */             }
/* 3762:     */             break;
/* 3763:     */           case 341: 
/* 3764:3068 */             if ((0x7E & l) != 0L) {
/* 3765:3069 */               this.jjstateSet[(this.jjnewStateCnt++)] = 342;
/* 3766:     */             }
/* 3767:     */             break;
/* 3768:     */           case 401: 
/* 3769:3072 */             if (this.curChar == '\\') {
/* 3770:3073 */               jjCheckNAddStates(343, 346);
/* 3771:     */             }
/* 3772:     */             break;
/* 3773:     */           case 402: 
/* 3774:3076 */             if ((0x7E & l) != 0L)
/* 3775:     */             {
/* 3776:3078 */               if (kind > 56) {
/* 3777:3079 */                 kind = 56;
/* 3778:     */               }
/* 3779:3080 */               jjCheckNAddStates(314, 321);
/* 3780:     */             }
/* 3781:3081 */             break;
/* 3782:     */           case 403: 
/* 3783:3083 */             if ((0x7E & l) != 0L)
/* 3784:     */             {
/* 3785:3085 */               if (kind > 56) {
/* 3786:3086 */                 kind = 56;
/* 3787:     */               }
/* 3788:3087 */               jjCheckNAddStates(322, 324);
/* 3789:     */             }
/* 3790:3088 */             break;
/* 3791:     */           case 404: 
/* 3792:     */           case 406: 
/* 3793:     */           case 409: 
/* 3794:     */           case 413: 
/* 3795:3093 */             if ((0x7E & l) != 0L) {
/* 3796:3094 */               jjCheckNAdd(403);
/* 3797:     */             }
/* 3798:     */             break;
/* 3799:     */           case 405: 
/* 3800:3097 */             if ((0x7E & l) != 0L) {
/* 3801:3098 */               this.jjstateSet[(this.jjnewStateCnt++)] = 406;
/* 3802:     */             }
/* 3803:     */             break;
/* 3804:     */           case 407: 
/* 3805:3101 */             if ((0x7E & l) != 0L) {
/* 3806:3102 */               this.jjstateSet[(this.jjnewStateCnt++)] = 408;
/* 3807:     */             }
/* 3808:     */             break;
/* 3809:     */           case 408: 
/* 3810:3105 */             if ((0x7E & l) != 0L) {
/* 3811:3106 */               this.jjstateSet[(this.jjnewStateCnt++)] = 409;
/* 3812:     */             }
/* 3813:     */             break;
/* 3814:     */           case 410: 
/* 3815:3109 */             if ((0x7E & l) != 0L) {
/* 3816:3110 */               this.jjstateSet[(this.jjnewStateCnt++)] = 411;
/* 3817:     */             }
/* 3818:     */             break;
/* 3819:     */           case 411: 
/* 3820:3113 */             if ((0x7E & l) != 0L) {
/* 3821:3114 */               this.jjstateSet[(this.jjnewStateCnt++)] = 412;
/* 3822:     */             }
/* 3823:     */             break;
/* 3824:     */           case 412: 
/* 3825:3117 */             if ((0x7E & l) != 0L) {
/* 3826:3118 */               this.jjstateSet[(this.jjnewStateCnt++)] = 413;
/* 3827:     */             }
/* 3828:     */             break;
/* 3829:     */           case 414: 
/* 3830:3121 */             if ((0x7E & l) != 0L) {
/* 3831:3122 */               jjCheckNAddStates(325, 333);
/* 3832:     */             }
/* 3833:     */             break;
/* 3834:     */           case 415: 
/* 3835:3125 */             if ((0x7E & l) != 0L) {
/* 3836:3126 */               jjCheckNAddStates(334, 337);
/* 3837:     */             }
/* 3838:     */             break;
/* 3839:     */           case 416: 
/* 3840:     */           case 418: 
/* 3841:     */           case 421: 
/* 3842:     */           case 425: 
/* 3843:3132 */             if ((0x7E & l) != 0L) {
/* 3844:3133 */               jjCheckNAdd(415);
/* 3845:     */             }
/* 3846:     */             break;
/* 3847:     */           case 417: 
/* 3848:3136 */             if ((0x7E & l) != 0L) {
/* 3849:3137 */               this.jjstateSet[(this.jjnewStateCnt++)] = 418;
/* 3850:     */             }
/* 3851:     */             break;
/* 3852:     */           case 419: 
/* 3853:3140 */             if ((0x7E & l) != 0L) {
/* 3854:3141 */               this.jjstateSet[(this.jjnewStateCnt++)] = 420;
/* 3855:     */             }
/* 3856:     */             break;
/* 3857:     */           case 420: 
/* 3858:3144 */             if ((0x7E & l) != 0L) {
/* 3859:3145 */               this.jjstateSet[(this.jjnewStateCnt++)] = 421;
/* 3860:     */             }
/* 3861:     */             break;
/* 3862:     */           case 422: 
/* 3863:3148 */             if ((0x7E & l) != 0L) {
/* 3864:3149 */               this.jjstateSet[(this.jjnewStateCnt++)] = 423;
/* 3865:     */             }
/* 3866:     */             break;
/* 3867:     */           case 423: 
/* 3868:3152 */             if ((0x7E & l) != 0L) {
/* 3869:3153 */               this.jjstateSet[(this.jjnewStateCnt++)] = 424;
/* 3870:     */             }
/* 3871:     */             break;
/* 3872:     */           case 424: 
/* 3873:3156 */             if ((0x7E & l) != 0L) {
/* 3874:3157 */               this.jjstateSet[(this.jjnewStateCnt++)] = 425;
/* 3875:     */             }
/* 3876:     */             break;
/* 3877:     */           }
/* 3878:3161 */         } while (i != startsAt);
/* 3879:     */       }
/* 3880:     */       else
/* 3881:     */       {
/* 3882:3165 */         int i2 = (this.curChar & 0xFF) >> '\006';
/* 3883:3166 */         long l2 = 1L << (this.curChar & 0x3F);
/* 3884:     */         do
/* 3885:     */         {
/* 3886:3169 */           switch (this.jjstateSet[(--i)])
/* 3887:     */           {
/* 3888:     */           case 62: 
/* 3889:     */           case 64: 
/* 3890:     */           case 428: 
/* 3891:3174 */             if ((jjbitVec2[i2] & l2) != 0L)
/* 3892:     */             {
/* 3893:3176 */               if (kind > 33) {
/* 3894:3177 */                 kind = 33;
/* 3895:     */               }
/* 3896:3178 */               jjCheckNAddTwoStates(62, 63);
/* 3897:     */             }
/* 3898:3179 */             break;
/* 3899:     */           case 61: 
/* 3900:3181 */             if ((jjbitVec2[i2] & l2) != 0L)
/* 3901:     */             {
/* 3902:3183 */               if (kind > 33) {
/* 3903:3184 */                 kind = 33;
/* 3904:     */               }
/* 3905:3185 */               jjCheckNAddTwoStates(62, 63);
/* 3906:     */             }
/* 3907:3186 */             break;
/* 3908:     */           case 1: 
/* 3909:3188 */             if ((jjbitVec2[i2] & l2) != 0L)
/* 3910:     */             {
/* 3911:3190 */               if (kind > 56) {
/* 3912:3191 */                 kind = 56;
/* 3913:     */               }
/* 3914:3192 */               jjCheckNAddStates(338, 342);
/* 3915:     */             }
/* 3916:3193 */             break;
/* 3917:     */           case 426: 
/* 3918:3195 */             if ((jjbitVec2[i2] & l2) != 0L) {
/* 3919:3196 */               jjCheckNAddStates(103, 105);
/* 3920:     */             }
/* 3921:3197 */             if ((jjbitVec2[i2] & l2) != 0L)
/* 3922:     */             {
/* 3923:3199 */               if (kind > 56) {
/* 3924:3200 */                 kind = 56;
/* 3925:     */               }
/* 3926:3201 */               jjCheckNAddTwoStates(327, 328);
/* 3927:     */             }
/* 3928:     */             break;
/* 3929:     */           case 2: 
/* 3930:     */           case 4: 
/* 3931:3206 */             if ((jjbitVec2[i2] & l2) != 0L)
/* 3932:     */             {
/* 3933:3208 */               if (kind > 19) {
/* 3934:3209 */                 kind = 19;
/* 3935:     */               }
/* 3936:3210 */               jjCheckNAddTwoStates(2, 3);
/* 3937:     */             }
/* 3938:3211 */             break;
/* 3939:     */           case 19: 
/* 3940:     */           case 25: 
/* 3941:3214 */             if ((jjbitVec2[i2] & l2) != 0L) {
/* 3942:3215 */               jjCheckNAddStates(100, 102);
/* 3943:     */             }
/* 3944:     */             break;
/* 3945:     */           case 40: 
/* 3946:     */           case 46: 
/* 3947:3219 */             if ((jjbitVec2[i2] & l2) != 0L) {
/* 3948:3220 */               jjCheckNAddStates(97, 99);
/* 3949:     */             }
/* 3950:     */             break;
/* 3951:     */           case 104: 
/* 3952:     */           case 108: 
/* 3953:3224 */             if ((jjbitVec2[i2] & l2) != 0L) {
/* 3954:3225 */               jjCheckNAddStates(171, 174);
/* 3955:     */             }
/* 3956:     */             break;
/* 3957:     */           case 123: 
/* 3958:     */           case 129: 
/* 3959:3229 */             if ((jjbitVec2[i2] & l2) != 0L) {
/* 3960:3230 */               jjCheckNAddStates(188, 190);
/* 3961:     */             }
/* 3962:     */             break;
/* 3963:     */           case 144: 
/* 3964:     */           case 150: 
/* 3965:3234 */             if ((jjbitVec2[i2] & l2) != 0L) {
/* 3966:3235 */               jjCheckNAddStates(204, 206);
/* 3967:     */             }
/* 3968:     */             break;
/* 3969:     */           case 275: 
/* 3970:     */           case 276: 
/* 3971:     */           case 278: 
/* 3972:3240 */             if ((jjbitVec2[i2] & l2) != 0L)
/* 3973:     */             {
/* 3974:3242 */               if (kind > 51) {
/* 3975:3243 */                 kind = 51;
/* 3976:     */               }
/* 3977:3244 */               jjCheckNAddTwoStates(276, 277);
/* 3978:     */             }
/* 3979:3245 */             break;
/* 3980:     */           case 310: 
/* 3981:     */           case 313: 
/* 3982:3248 */             if ((jjbitVec2[i2] & l2) != 0L) {
/* 3983:3249 */               jjCheckNAddStates(103, 105);
/* 3984:     */             }
/* 3985:     */             break;
/* 3986:     */           case 327: 
/* 3987:     */           case 329: 
/* 3988:3253 */             if ((jjbitVec2[i2] & l2) != 0L)
/* 3989:     */             {
/* 3990:3255 */               if (kind > 56) {
/* 3991:3256 */                 kind = 56;
/* 3992:     */               }
/* 3993:3257 */               jjCheckNAddTwoStates(327, 328);
/* 3994:     */             }
/* 3995:     */             break;
/* 3996:     */           }
/* 3997:3261 */         } while (i != startsAt);
/* 3998:     */       }
/* 3999:3263 */       if (kind != 2147483647)
/* 4000:     */       {
/* 4001:3265 */         this.jjmatchedKind = kind;
/* 4002:3266 */         this.jjmatchedPos = curPos;
/* 4003:3267 */         kind = 2147483647;
/* 4004:     */       }
/* 4005:3269 */       curPos++;
/* 4006:3270 */       if ((i = this.jjnewStateCnt) == (startsAt = 426 - (this.jjnewStateCnt = startsAt))) {
/* 4007:3271 */         return curPos;
/* 4008:     */       }
/* 4009:     */       try
/* 4010:     */       {
/* 4011:3272 */         this.curChar = this.input_stream.readChar();
/* 4012:     */       }
/* 4013:     */       catch (IOException e) {}
/* 4014:     */     }
/* 4015:3273 */     return curPos;
/* 4016:     */   }
/* 4017:     */   
/* 4018:     */   private final int jjMoveStringLiteralDfa0_1()
/* 4019:     */   {
/* 4020:3278 */     switch (this.curChar)
/* 4021:     */     {
/* 4022:     */     case '*': 
/* 4023:3281 */       return jjMoveStringLiteralDfa1_1(8L);
/* 4024:     */     }
/* 4025:3283 */     return 1;
/* 4026:     */   }
/* 4027:     */   
/* 4028:     */   private final int jjMoveStringLiteralDfa1_1(long active0)
/* 4029:     */   {
/* 4030:     */     try
/* 4031:     */     {
/* 4032:3288 */       this.curChar = this.input_stream.readChar();
/* 4033:     */     }
/* 4034:     */     catch (IOException e)
/* 4035:     */     {
/* 4036:3290 */       return 1;
/* 4037:     */     }
/* 4038:3292 */     switch (this.curChar)
/* 4039:     */     {
/* 4040:     */     case '/': 
/* 4041:3295 */       if ((active0 & 0x8) != 0L) {
/* 4042:3296 */         return jjStopAtPos(1, 3);
/* 4043:     */       }
/* 4044:     */       break;
/* 4045:     */     default: 
/* 4046:3299 */       return 2;
/* 4047:     */     }
/* 4048:3301 */     return 2;
/* 4049:     */   }
/* 4050:     */   
/* 4051:3303 */   static final int[] jjnextStates = { 274, 275, 292, 344, 345, 346, 227, 347, 348, 349, 230, 350, 351, 352, 233, 353, 354, 355, 236, 356, 357, 358, 239, 359, 360, 361, 242, 362, 363, 364, 245, 365, 366, 367, 248, 368, 369, 370, 252, 371, 372, 373, 256, 374, 375, 376, 261, 377, 378, 379, 264, 380, 381, 382, 266, 383, 384, 385, 269, 386, 387, 388, 273, 389, 390, 391, 275, 392, 393, 394, 306, 395, 396, 397, 398, 399, 400, 292, 225, 228, 231, 234, 237, 240, 243, 246, 249, 253, 257, 262, 265, 267, 270, 274, 305, 307, 308, 40, 41, 42, 19, 20, 21, 310, 311, 312, 2, 6, 8, 9, 11, 14, 7, 3, 2, 7, 3, 19, 27, 29, 30, 32, 35, 28, 20, 21, 19, 28, 20, 21, 40, 48, 50, 51, 53, 56, 49, 41, 42, 40, 49, 41, 42, 62, 66, 68, 69, 71, 74, 67, 63, 62, 67, 63, 80, 81, 82, 84, 87, 67, 62, 63, 67, 62, 63, 104, 122, 143, 106, 107, 164, 104, 105, 106, 107, 104, 110, 112, 113, 115, 118, 106, 107, 111, 104, 106, 107, 111, 123, 124, 125, 123, 131, 133, 134, 136, 139, 132, 124, 125, 123, 132, 124, 125, 144, 145, 146, 144, 152, 154, 155, 157, 160, 153, 145, 146, 144, 153, 145, 146, 104, 122, 143, 105, 106, 107, 164, 168, 169, 213, 170, 184, 185, 187, 190, 171, 168, 194, 206, 173, 174, 175, 177, 180, 168, 195, 202, 168, 196, 200, 168, 198, 199, 197, 203, 205, 197, 207, 209, 212, 217, 220, 222, 223, 197, 276, 280, 282, 283, 285, 288, 281, 277, 276, 281, 277, 294, 295, 296, 298, 301, 281, 276, 277, 281, 276, 277, 310, 315, 317, 318, 320, 323, 316, 311, 312, 310, 316, 311, 312, 327, 331, 333, 334, 336, 339, 332, 328, 327, 332, 328, 389, 275, 292, 403, 404, 405, 407, 410, 332, 327, 328, 332, 327, 328, 415, 416, 417, 419, 422, 316, 310, 311, 312, 316, 310, 311, 312, 310, 311, 327, 328, 312, 313, 329, 402, 414, 61, 78, 166, 167, 4, 5, 22, 24, 25, 26, 43, 45, 46, 47, 108, 109, 126, 128, 129, 130, 147, 149, 150, 151 };
/* 4052:3329 */   public static final String[] jjstrLiteralImages = { "", null, null, null, null, "{", "}", ",", ".", ";", ":", "*", "/", "+", "-", "=", ">", "[", "]", null, null, ")", null, null, "<!--", "-->", "~=", "|=", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null };
/* 4053:3337 */   public static final String[] lexStateNames = { "DEFAULT", "COMMENT" };
/* 4054:3341 */   public static final int[] jjnewLexState = { -1, -1, 1, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };
/* 4055:3347 */   static final long[] jjtoToken = { 1008806316526796771L, 8192L };
/* 4056:3350 */   static final long[] jjtoSkip = { 8L, 0L };
/* 4057:3353 */   static final long[] jjtoMore = { 20L, 0L };
/* 4058:     */   protected CharStream input_stream;
/* 4059:3357 */   private final int[] jjrounds = new int[426];
/* 4060:3358 */   private final int[] jjstateSet = new int[852];
/* 4061:     */   StringBuffer image;
/* 4062:     */   int jjimageLen;
/* 4063:     */   int lengthOfMatch;
/* 4064:     */   protected char curChar;
/* 4065:     */   
/* 4066:     */   public SACParserCSS2TokenManager(CharStream stream)
/* 4067:     */   {
/* 4068:3364 */     this.input_stream = stream;
/* 4069:     */   }
/* 4070:     */   
/* 4071:     */   public SACParserCSS2TokenManager(CharStream stream, int lexState)
/* 4072:     */   {
/* 4073:3367 */     this(stream);
/* 4074:3368 */     SwitchTo(lexState);
/* 4075:     */   }
/* 4076:     */   
/* 4077:     */   public void ReInit(CharStream stream)
/* 4078:     */   {
/* 4079:3372 */     this.jjmatchedPos = (this.jjnewStateCnt = 0);
/* 4080:3373 */     this.curLexState = this.defaultLexState;
/* 4081:3374 */     this.input_stream = stream;
/* 4082:3375 */     ReInitRounds();
/* 4083:     */   }
/* 4084:     */   
/* 4085:     */   private final void ReInitRounds()
/* 4086:     */   {
/* 4087:3380 */     this.jjround = -2147483647;
/* 4088:3381 */     for (int i = 426; i-- > 0;) {
/* 4089:3382 */       this.jjrounds[i] = -2147483648;
/* 4090:     */     }
/* 4091:     */   }
/* 4092:     */   
/* 4093:     */   public void ReInit(CharStream stream, int lexState)
/* 4094:     */   {
/* 4095:3386 */     ReInit(stream);
/* 4096:3387 */     SwitchTo(lexState);
/* 4097:     */   }
/* 4098:     */   
/* 4099:     */   public void SwitchTo(int lexState)
/* 4100:     */   {
/* 4101:3391 */     if ((lexState >= 2) || (lexState < 0)) {
/* 4102:3392 */       throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", 2);
/* 4103:     */     }
/* 4104:3394 */     this.curLexState = lexState;
/* 4105:     */   }
/* 4106:     */   
/* 4107:     */   protected Token jjFillToken()
/* 4108:     */   {
/* 4109:3399 */     Token t = Token.newToken(this.jjmatchedKind);
/* 4110:3400 */     t.kind = this.jjmatchedKind;
/* 4111:3401 */     String im = jjstrLiteralImages[this.jjmatchedKind];
/* 4112:3402 */     t.image = (im == null ? this.input_stream.GetImage() : im);
/* 4113:3403 */     t.beginLine = this.input_stream.getBeginLine();
/* 4114:3404 */     t.beginColumn = this.input_stream.getBeginColumn();
/* 4115:3405 */     t.endLine = this.input_stream.getEndLine();
/* 4116:3406 */     t.endColumn = this.input_stream.getEndColumn();
/* 4117:3407 */     return t;
/* 4118:     */   }
/* 4119:     */   
/* 4120:3410 */   int curLexState = 0;
/* 4121:3411 */   int defaultLexState = 0;
/* 4122:     */   int jjnewStateCnt;
/* 4123:     */   int jjround;
/* 4124:     */   int jjmatchedPos;
/* 4125:     */   int jjmatchedKind;
/* 4126:     */   
/* 4127:     */   public Token getNextToken()
/* 4128:     */   {
/* 4129:3420 */     Token specialToken = null;
/* 4130:     */     
/* 4131:3422 */     int curPos = 0;
/* 4132:     */     try
/* 4133:     */     {
/* 4134:3429 */       this.curChar = this.input_stream.BeginToken();
/* 4135:     */     }
/* 4136:     */     catch (IOException e)
/* 4137:     */     {
/* 4138:3433 */       this.jjmatchedKind = 0;
/* 4139:3434 */       return jjFillToken();
/* 4140:     */     }
/* 4141:3437 */     this.image = null;
/* 4142:3438 */     this.jjimageLen = 0;
/* 4143:     */     for (;;)
/* 4144:     */     {
/* 4145:3442 */       switch (this.curLexState)
/* 4146:     */       {
/* 4147:     */       case 0: 
/* 4148:3445 */         this.jjmatchedKind = 2147483647;
/* 4149:3446 */         this.jjmatchedPos = 0;
/* 4150:3447 */         curPos = jjMoveStringLiteralDfa0_0();
/* 4151:3448 */         if ((this.jjmatchedPos == 0) && (this.jjmatchedKind > 77)) {
/* 4152:3450 */           this.jjmatchedKind = 77;
/* 4153:     */         }
/* 4154:     */         break;
/* 4155:     */       case 1: 
/* 4156:3454 */         this.jjmatchedKind = 2147483647;
/* 4157:3455 */         this.jjmatchedPos = 0;
/* 4158:3456 */         curPos = jjMoveStringLiteralDfa0_1();
/* 4159:3457 */         if ((this.jjmatchedPos == 0) && (this.jjmatchedKind > 4)) {
/* 4160:3459 */           this.jjmatchedKind = 4;
/* 4161:     */         }
/* 4162:     */         break;
/* 4163:     */       }
/* 4164:3463 */       if (this.jjmatchedKind != 2147483647)
/* 4165:     */       {
/* 4166:3465 */         if (this.jjmatchedPos + 1 < curPos) {
/* 4167:3466 */           this.input_stream.backup(curPos - this.jjmatchedPos - 1);
/* 4168:     */         }
/* 4169:3467 */         if ((jjtoToken[(this.jjmatchedKind >> 6)] & 1L << (this.jjmatchedKind & 0x3F)) != 0L)
/* 4170:     */         {
/* 4171:3469 */           Token matchedToken = jjFillToken();
/* 4172:3470 */           TokenLexicalActions(matchedToken);
/* 4173:3471 */           if (jjnewLexState[this.jjmatchedKind] != -1) {
/* 4174:3472 */             this.curLexState = jjnewLexState[this.jjmatchedKind];
/* 4175:     */           }
/* 4176:3473 */           return matchedToken;
/* 4177:     */         }
/* 4178:3475 */         if ((jjtoSkip[(this.jjmatchedKind >> 6)] & 1L << (this.jjmatchedKind & 0x3F)) != 0L)
/* 4179:     */         {
/* 4180:3477 */           if (jjnewLexState[this.jjmatchedKind] == -1) {
/* 4181:     */             break;
/* 4182:     */           }
/* 4183:3478 */           this.curLexState = jjnewLexState[this.jjmatchedKind]; break;
/* 4184:     */         }
/* 4185:3481 */         this.jjimageLen += this.jjmatchedPos + 1;
/* 4186:3482 */         if (jjnewLexState[this.jjmatchedKind] != -1) {
/* 4187:3483 */           this.curLexState = jjnewLexState[this.jjmatchedKind];
/* 4188:     */         }
/* 4189:3484 */         curPos = 0;
/* 4190:3485 */         this.jjmatchedKind = 2147483647;
/* 4191:     */         try
/* 4192:     */         {
/* 4193:3487 */           this.curChar = this.input_stream.readChar();
/* 4194:     */         }
/* 4195:     */         catch (IOException e1) {}
/* 4196:     */       }
/* 4197:     */     }
/* 4198:3492 */     int error_line = this.input_stream.getEndLine();
/* 4199:3493 */     int error_column = this.input_stream.getEndColumn();
/* 4200:3494 */     String error_after = null;
/* 4201:3495 */     boolean EOFSeen = false;
/* 4202:     */     try
/* 4203:     */     {
/* 4204:3496 */       this.input_stream.readChar();this.input_stream.backup(1);
/* 4205:     */     }
/* 4206:     */     catch (IOException e1)
/* 4207:     */     {
/* 4208:3498 */       EOFSeen = true;
/* 4209:3499 */       error_after = curPos <= 1 ? "" : this.input_stream.GetImage();
/* 4210:3500 */       if ((this.curChar == '\n') || (this.curChar == '\r'))
/* 4211:     */       {
/* 4212:3501 */         error_line++;
/* 4213:3502 */         error_column = 0;
/* 4214:     */       }
/* 4215:     */       else
/* 4216:     */       {
/* 4217:3505 */         error_column++;
/* 4218:     */       }
/* 4219:     */     }
/* 4220:3507 */     if (!EOFSeen)
/* 4221:     */     {
/* 4222:3508 */       this.input_stream.backup(1);
/* 4223:3509 */       error_after = curPos <= 1 ? "" : this.input_stream.GetImage();
/* 4224:     */     }
/* 4225:3511 */     throw new TokenMgrError(EOFSeen, this.curLexState, error_line, error_column, error_after, this.curChar, 0);
/* 4226:     */   }
/* 4227:     */   
/* 4228:     */   void TokenLexicalActions(Token matchedToken)
/* 4229:     */   {
/* 4230:3518 */     switch (this.jjmatchedKind)
/* 4231:     */     {
/* 4232:     */     case 20: 
/* 4233:3521 */       if (this.image == null) {
/* 4234:3522 */         this.image = new StringBuffer();
/* 4235:     */       }
/* 4236:3523 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 4237:3524 */       matchedToken.image = trimBy(this.image, 1, 1);
/* 4238:3525 */       break;
/* 4239:     */     case 23: 
/* 4240:3527 */       if (this.image == null) {
/* 4241:3528 */         this.image = new StringBuffer();
/* 4242:     */       }
/* 4243:3529 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 4244:3530 */       matchedToken.image = trimUrl(this.image);
/* 4245:3531 */       break;
/* 4246:     */     case 36: 
/* 4247:3533 */       if (this.image == null) {
/* 4248:3534 */         this.image = new StringBuffer();
/* 4249:     */       }
/* 4250:3535 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 4251:3536 */       matchedToken.image = trimBy(this.image, 0, 2);
/* 4252:3537 */       break;
/* 4253:     */     case 37: 
/* 4254:3539 */       if (this.image == null) {
/* 4255:3540 */         this.image = new StringBuffer();
/* 4256:     */       }
/* 4257:3541 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 4258:3542 */       matchedToken.image = trimBy(this.image, 0, 2);
/* 4259:3543 */       break;
/* 4260:     */     case 38: 
/* 4261:3545 */       if (this.image == null) {
/* 4262:3546 */         this.image = new StringBuffer();
/* 4263:     */       }
/* 4264:3547 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 4265:3548 */       matchedToken.image = trimBy(this.image, 0, 2);
/* 4266:3549 */       break;
/* 4267:     */     case 39: 
/* 4268:3551 */       if (this.image == null) {
/* 4269:3552 */         this.image = new StringBuffer();
/* 4270:     */       }
/* 4271:3553 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 4272:3554 */       matchedToken.image = trimBy(this.image, 0, 2);
/* 4273:3555 */       break;
/* 4274:     */     case 40: 
/* 4275:3557 */       if (this.image == null) {
/* 4276:3558 */         this.image = new StringBuffer();
/* 4277:     */       }
/* 4278:3559 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 4279:3560 */       matchedToken.image = trimBy(this.image, 0, 2);
/* 4280:3561 */       break;
/* 4281:     */     case 41: 
/* 4282:3563 */       if (this.image == null) {
/* 4283:3564 */         this.image = new StringBuffer();
/* 4284:     */       }
/* 4285:3565 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 4286:3566 */       matchedToken.image = trimBy(this.image, 0, 2);
/* 4287:3567 */       break;
/* 4288:     */     case 42: 
/* 4289:3569 */       if (this.image == null) {
/* 4290:3570 */         this.image = new StringBuffer();
/* 4291:     */       }
/* 4292:3571 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 4293:3572 */       matchedToken.image = trimBy(this.image, 0, 2);
/* 4294:3573 */       break;
/* 4295:     */     case 43: 
/* 4296:3575 */       if (this.image == null) {
/* 4297:3576 */         this.image = new StringBuffer();
/* 4298:     */       }
/* 4299:3577 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 4300:3578 */       matchedToken.image = trimBy(this.image, 0, 2);
/* 4301:3579 */       break;
/* 4302:     */     case 44: 
/* 4303:3581 */       if (this.image == null) {
/* 4304:3582 */         this.image = new StringBuffer();
/* 4305:     */       }
/* 4306:3583 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 4307:3584 */       matchedToken.image = trimBy(this.image, 0, 3);
/* 4308:3585 */       break;
/* 4309:     */     case 45: 
/* 4310:3587 */       if (this.image == null) {
/* 4311:3588 */         this.image = new StringBuffer();
/* 4312:     */       }
/* 4313:3589 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 4314:3590 */       matchedToken.image = trimBy(this.image, 0, 3);
/* 4315:3591 */       break;
/* 4316:     */     case 46: 
/* 4317:3593 */       if (this.image == null) {
/* 4318:3594 */         this.image = new StringBuffer();
/* 4319:     */       }
/* 4320:3595 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 4321:3596 */       matchedToken.image = trimBy(this.image, 0, 4);
/* 4322:3597 */       break;
/* 4323:     */     case 47: 
/* 4324:3599 */       if (this.image == null) {
/* 4325:3600 */         this.image = new StringBuffer();
/* 4326:     */       }
/* 4327:3601 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 4328:3602 */       matchedToken.image = trimBy(this.image, 0, 2);
/* 4329:3603 */       break;
/* 4330:     */     case 48: 
/* 4331:3605 */       if (this.image == null) {
/* 4332:3606 */         this.image = new StringBuffer();
/* 4333:     */       }
/* 4334:3607 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 4335:3608 */       matchedToken.image = trimBy(this.image, 0, 1);
/* 4336:3609 */       break;
/* 4337:     */     case 49: 
/* 4338:3611 */       if (this.image == null) {
/* 4339:3612 */         this.image = new StringBuffer();
/* 4340:     */       }
/* 4341:3613 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 4342:3614 */       matchedToken.image = trimBy(this.image, 0, 2);
/* 4343:3615 */       break;
/* 4344:     */     case 50: 
/* 4345:3617 */       if (this.image == null) {
/* 4346:3618 */         this.image = new StringBuffer();
/* 4347:     */       }
/* 4348:3619 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 4349:3620 */       matchedToken.image = trimBy(this.image, 0, 3);
/* 4350:3621 */       break;
/* 4351:     */     case 52: 
/* 4352:3623 */       if (this.image == null) {
/* 4353:3624 */         this.image = new StringBuffer();
/* 4354:     */       }
/* 4355:3625 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 4356:3626 */       matchedToken.image = trimBy(this.image, 0, 1);
/* 4357:3627 */       break;
/* 4358:     */     case 77: 
/* 4359:3629 */       if (this.image == null) {
/* 4360:3630 */         this.image = new StringBuffer();
/* 4361:     */       }
/* 4362:3631 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 4363:3632 */       if (!this._quiet) {
/* 4364:3633 */         System.err.println("Illegal character : " + this.image.toString());
/* 4365:     */       }
/* 4366:     */       break;
/* 4367:     */     }
/* 4368:     */   }
/* 4369:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.steadystate.css.parser.SACParserCSS2TokenManager
 * JD-Core Version:    0.7.0.1
 */