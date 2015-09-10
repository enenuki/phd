/*    1:     */ package com.steadystate.css.parser;
/*    2:     */ 
/*    3:     */ import java.io.IOException;
/*    4:     */ import java.io.PrintStream;
/*    5:     */ import org.w3c.css.sac.ErrorHandler;
/*    6:     */ 
/*    7:     */ public class SACParserCSS21TokenManager
/*    8:     */   implements SACParserCSS21Constants
/*    9:     */ {
/*   10:  13 */   private boolean _quiet = true;
/*   11:     */   private ErrorHandler errorHandler;
/*   12:     */   
/*   13:     */   public String splitNumber(StringBuffer pattern)
/*   14:     */   {
/*   15:  17 */     String regexp = "[0-9]+|[0-9]*.[0-9]+";
/*   16:  18 */     int j = 1;
/*   17:  19 */     while (pattern.substring(0, j).matches(regexp)) {
/*   18:  21 */       j++;
/*   19:     */     }
/*   20:  23 */     if (pattern.substring(j - 1, j).equals("."))
/*   21:     */     {
/*   22:  25 */       j++;
/*   23:  26 */       while (pattern.substring(0, j).matches(regexp)) {
/*   24:  28 */         j++;
/*   25:     */       }
/*   26:     */     }
/*   27:  31 */     return pattern.substring(0, j - 1);
/*   28:     */   }
/*   29:     */   
/*   30:     */   private String trimBy(StringBuffer s, int left, int right)
/*   31:     */   {
/*   32:  35 */     int end = s.length();
/*   33:  36 */     return s.toString().substring(left, end - right);
/*   34:     */   }
/*   35:     */   
/*   36:     */   private String trimUrl(StringBuffer s)
/*   37:     */   {
/*   38:  40 */     StringBuffer s1 = new StringBuffer(trimBy(s, 4, 1).trim());
/*   39:  41 */     if (s1.length() == 0) {
/*   40:  43 */       return s1.toString();
/*   41:     */     }
/*   42:  45 */     int end = s1.length() - 1;
/*   43:  46 */     if (((s1.charAt(0) == '"') && (s1.charAt(end) == '"')) || ((s1.charAt(0) == '\'') && (s1.charAt(end) == '\''))) {
/*   44:  48 */       return trimBy(s1, 1, 1);
/*   45:     */     }
/*   46:  50 */     return s1.toString();
/*   47:     */   }
/*   48:     */   
/*   49:  52 */   public PrintStream debugStream = System.out;
/*   50:     */   
/*   51:     */   public void setDebugStream(PrintStream ds)
/*   52:     */   {
/*   53:  53 */     this.debugStream = ds;
/*   54:     */   }
/*   55:     */   
/*   56:     */   private final int jjStopStringLiteralDfa_0(int pos, long active0)
/*   57:     */   {
/*   58:  56 */     switch (pos)
/*   59:     */     {
/*   60:     */     case 0: 
/*   61:  59 */       if ((active0 & 0x0) != 0L)
/*   62:     */       {
/*   63:  61 */         this.jjmatchedKind = 56;
/*   64:  62 */         this.jjmatchedPos = 0;
/*   65:  63 */         return 837;
/*   66:     */       }
/*   67:  65 */       if ((active0 & 0x4008000) != 0L) {
/*   68:  66 */         return 710;
/*   69:     */       }
/*   70:  67 */       if ((active0 & 0x200) != 0L) {
/*   71:  68 */         return 838;
/*   72:     */       }
/*   73:  69 */       if ((active0 & 0xE0000000) != 0L) {
/*   74:  70 */         return 137;
/*   75:     */       }
/*   76:  71 */       return -1;
/*   77:     */     case 1: 
/*   78:  73 */       if ((active0 & 0xE0000000) != 0L)
/*   79:     */       {
/*   80:  75 */         this.jjmatchedKind = 33;
/*   81:  76 */         this.jjmatchedPos = 1;
/*   82:  77 */         return 839;
/*   83:     */       }
/*   84:  79 */       if ((active0 & 0x0) != 0L)
/*   85:     */       {
/*   86:  81 */         this.jjmatchedKind = 56;
/*   87:  82 */         this.jjmatchedPos = 1;
/*   88:  83 */         return 837;
/*   89:     */       }
/*   90:  85 */       return -1;
/*   91:     */     case 2: 
/*   92:  87 */       if ((active0 & 0xE0000000) != 0L)
/*   93:     */       {
/*   94:  89 */         this.jjmatchedKind = 33;
/*   95:  90 */         this.jjmatchedPos = 2;
/*   96:  91 */         return 839;
/*   97:     */       }
/*   98:  93 */       if ((active0 & 0x0) != 0L)
/*   99:     */       {
/*  100:  95 */         this.jjmatchedKind = 56;
/*  101:  96 */         this.jjmatchedPos = 2;
/*  102:  97 */         return 837;
/*  103:     */       }
/*  104:  99 */       return -1;
/*  105:     */     case 3: 
/*  106: 101 */       if ((active0 & 0x0) != 0L)
/*  107:     */       {
/*  108: 103 */         this.jjmatchedKind = 56;
/*  109: 104 */         this.jjmatchedPos = 3;
/*  110: 105 */         return 837;
/*  111:     */       }
/*  112: 107 */       if ((active0 & 0xE0000000) != 0L)
/*  113:     */       {
/*  114: 109 */         this.jjmatchedKind = 33;
/*  115: 110 */         this.jjmatchedPos = 3;
/*  116: 111 */         return 839;
/*  117:     */       }
/*  118: 113 */       return -1;
/*  119:     */     case 4: 
/*  120: 115 */       if ((active0 & 0x40000000) != 0L) {
/*  121: 116 */         return 839;
/*  122:     */       }
/*  123: 117 */       if ((active0 & 0xA0000000) != 0L)
/*  124:     */       {
/*  125: 119 */         this.jjmatchedKind = 33;
/*  126: 120 */         this.jjmatchedPos = 4;
/*  127: 121 */         return 839;
/*  128:     */       }
/*  129: 123 */       if ((active0 & 0x0) != 0L)
/*  130:     */       {
/*  131: 125 */         this.jjmatchedKind = 56;
/*  132: 126 */         this.jjmatchedPos = 4;
/*  133: 127 */         return 837;
/*  134:     */       }
/*  135: 129 */       return -1;
/*  136:     */     case 5: 
/*  137: 131 */       if ((active0 & 0x80000000) != 0L) {
/*  138: 132 */         return 839;
/*  139:     */       }
/*  140: 133 */       if ((active0 & 0x0) != 0L)
/*  141:     */       {
/*  142: 135 */         this.jjmatchedKind = 56;
/*  143: 136 */         this.jjmatchedPos = 5;
/*  144: 137 */         return 837;
/*  145:     */       }
/*  146: 139 */       if ((active0 & 0x20000000) != 0L)
/*  147:     */       {
/*  148: 141 */         this.jjmatchedKind = 33;
/*  149: 142 */         this.jjmatchedPos = 5;
/*  150: 143 */         return 839;
/*  151:     */       }
/*  152: 145 */       return -1;
/*  153:     */     case 6: 
/*  154: 147 */       if ((active0 & 0x0) != 0L)
/*  155:     */       {
/*  156: 149 */         this.jjmatchedKind = 56;
/*  157: 150 */         this.jjmatchedPos = 6;
/*  158: 151 */         return 837;
/*  159:     */       }
/*  160: 153 */       if ((active0 & 0x20000000) != 0L) {
/*  161: 154 */         return 839;
/*  162:     */       }
/*  163: 155 */       if ((active0 & 0x0) != 0L)
/*  164:     */       {
/*  165: 157 */         this.jjmatchedKind = 33;
/*  166: 158 */         this.jjmatchedPos = 6;
/*  167: 159 */         return 839;
/*  168:     */       }
/*  169: 161 */       if ((active0 & 0x0) != 0L) {
/*  170: 162 */         return 837;
/*  171:     */       }
/*  172: 163 */       return -1;
/*  173:     */     case 7: 
/*  174: 165 */       if ((active0 & 0x0) != 0L)
/*  175:     */       {
/*  176: 167 */         this.jjmatchedKind = 33;
/*  177: 168 */         this.jjmatchedPos = 7;
/*  178: 169 */         return 839;
/*  179:     */       }
/*  180: 171 */       if ((active0 & 0x0) != 0L)
/*  181:     */       {
/*  182: 173 */         this.jjmatchedKind = 56;
/*  183: 174 */         this.jjmatchedPos = 7;
/*  184: 175 */         return 837;
/*  185:     */       }
/*  186: 177 */       return -1;
/*  187:     */     }
/*  188: 179 */     return -1;
/*  189:     */   }
/*  190:     */   
/*  191:     */   private final int jjStartNfa_0(int pos, long active0)
/*  192:     */   {
/*  193: 184 */     return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0), pos + 1);
/*  194:     */   }
/*  195:     */   
/*  196:     */   private final int jjStopAtPos(int pos, int kind)
/*  197:     */   {
/*  198: 188 */     this.jjmatchedKind = kind;
/*  199: 189 */     this.jjmatchedPos = pos;
/*  200: 190 */     return pos + 1;
/*  201:     */   }
/*  202:     */   
/*  203:     */   private final int jjStartNfaWithStates_0(int pos, int kind, int state)
/*  204:     */   {
/*  205: 194 */     this.jjmatchedKind = kind;
/*  206: 195 */     this.jjmatchedPos = pos;
/*  207:     */     try
/*  208:     */     {
/*  209: 196 */       this.curChar = this.input_stream.readChar();
/*  210:     */     }
/*  211:     */     catch (IOException e)
/*  212:     */     {
/*  213: 197 */       return pos + 1;
/*  214:     */     }
/*  215: 198 */     return jjMoveNfa_0(state, pos + 1);
/*  216:     */   }
/*  217:     */   
/*  218:     */   private final int jjMoveStringLiteralDfa0_0()
/*  219:     */   {
/*  220: 202 */     switch (this.curChar)
/*  221:     */     {
/*  222:     */     case '!': 
/*  223: 205 */       return jjStopAtPos(0, 35);
/*  224:     */     case ')': 
/*  225: 207 */       return jjStopAtPos(0, 22);
/*  226:     */     case '*': 
/*  227: 209 */       return jjStopAtPos(0, 12);
/*  228:     */     case '-': 
/*  229: 212 */       this.jjmatchedKind = 15;
/*  230: 213 */       this.jjmatchedPos = 0;
/*  231:     */       
/*  232: 215 */       return jjMoveStringLiteralDfa1_0(67108864L);
/*  233:     */     case '.': 
/*  234: 217 */       return jjStartNfaWithStates_0(0, 9, 838);
/*  235:     */     case '/': 
/*  236: 220 */       this.jjmatchedKind = 13;
/*  237: 221 */       this.jjmatchedPos = 0;
/*  238:     */       
/*  239: 223 */       return jjMoveStringLiteralDfa1_0(8L);
/*  240:     */     case ':': 
/*  241: 225 */       return jjStopAtPos(0, 11);
/*  242:     */     case ';': 
/*  243: 227 */       return jjStopAtPos(0, 10);
/*  244:     */     case '<': 
/*  245: 229 */       return jjMoveStringLiteralDfa1_0(33554432L);
/*  246:     */     case '=': 
/*  247: 231 */       return jjStopAtPos(0, 16);
/*  248:     */     case '@': 
/*  249: 233 */       return jjMoveStringLiteralDfa1_0(8053063680L);
/*  250:     */     case '[': 
/*  251: 235 */       return jjStopAtPos(0, 17);
/*  252:     */     case ']': 
/*  253: 237 */       return jjStopAtPos(0, 18);
/*  254:     */     case 'I': 
/*  255:     */     case 'i': 
/*  256: 240 */       return jjMoveStringLiteralDfa1_0(85899345920L);
/*  257:     */     case '|': 
/*  258: 242 */       return jjMoveStringLiteralDfa1_0(268435456L);
/*  259:     */     case '}': 
/*  260: 244 */       return jjStopAtPos(0, 7);
/*  261:     */     case '~': 
/*  262: 246 */       return jjMoveStringLiteralDfa1_0(134217728L);
/*  263:     */     }
/*  264: 248 */     return jjMoveNfa_0(4, 0);
/*  265:     */   }
/*  266:     */   
/*  267:     */   private final int jjMoveStringLiteralDfa1_0(long active0)
/*  268:     */   {
/*  269:     */     try
/*  270:     */     {
/*  271: 253 */       this.curChar = this.input_stream.readChar();
/*  272:     */     }
/*  273:     */     catch (IOException e)
/*  274:     */     {
/*  275: 255 */       jjStopStringLiteralDfa_0(0, active0);
/*  276: 256 */       return 1;
/*  277:     */     }
/*  278: 258 */     switch (this.curChar)
/*  279:     */     {
/*  280:     */     case '!': 
/*  281: 261 */       return jjMoveStringLiteralDfa2_0(active0, 33554432L);
/*  282:     */     case '*': 
/*  283: 263 */       if ((active0 & 0x8) != 0L) {
/*  284: 264 */         return jjStopAtPos(1, 3);
/*  285:     */       }
/*  286:     */       break;
/*  287:     */     case '-': 
/*  288: 267 */       return jjMoveStringLiteralDfa2_0(active0, 67108864L);
/*  289:     */     case '=': 
/*  290: 269 */       if ((active0 & 0x8000000) != 0L) {
/*  291: 270 */         return jjStopAtPos(1, 27);
/*  292:     */       }
/*  293: 271 */       if ((active0 & 0x10000000) != 0L) {
/*  294: 272 */         return jjStopAtPos(1, 28);
/*  295:     */       }
/*  296:     */       break;
/*  297:     */     case 'C': 
/*  298:     */     case 'c': 
/*  299: 276 */       return jjMoveStringLiteralDfa2_0(active0, 4294967296L);
/*  300:     */     case 'I': 
/*  301:     */     case 'i': 
/*  302: 279 */       return jjMoveStringLiteralDfa2_0(active0, 536870912L);
/*  303:     */     case 'M': 
/*  304:     */     case 'm': 
/*  305: 282 */       return jjMoveStringLiteralDfa2_0(active0, 19327352832L);
/*  306:     */     case 'N': 
/*  307:     */     case 'n': 
/*  308: 285 */       return jjMoveStringLiteralDfa2_0(active0, 68719476736L);
/*  309:     */     case 'P': 
/*  310:     */     case 'p': 
/*  311: 288 */       return jjMoveStringLiteralDfa2_0(active0, 1073741824L);
/*  312:     */     }
/*  313: 292 */     return jjStartNfa_0(0, active0);
/*  314:     */   }
/*  315:     */   
/*  316:     */   private final int jjMoveStringLiteralDfa2_0(long old0, long active0)
/*  317:     */   {
/*  318: 296 */     if ((active0 &= old0) == 0L) {
/*  319: 297 */       return jjStartNfa_0(0, old0);
/*  320:     */     }
/*  321:     */     try
/*  322:     */     {
/*  323: 298 */       this.curChar = this.input_stream.readChar();
/*  324:     */     }
/*  325:     */     catch (IOException e)
/*  326:     */     {
/*  327: 300 */       jjStopStringLiteralDfa_0(1, active0);
/*  328: 301 */       return 2;
/*  329:     */     }
/*  330: 303 */     switch (this.curChar)
/*  331:     */     {
/*  332:     */     case '-': 
/*  333: 306 */       return jjMoveStringLiteralDfa3_0(active0, 33554432L);
/*  334:     */     case '>': 
/*  335: 308 */       if ((active0 & 0x4000000) != 0L) {
/*  336: 309 */         return jjStopAtPos(2, 26);
/*  337:     */       }
/*  338:     */       break;
/*  339:     */     case 'A': 
/*  340:     */     case 'a': 
/*  341: 313 */       return jjMoveStringLiteralDfa3_0(active0, 1073741824L);
/*  342:     */     case 'E': 
/*  343:     */     case 'e': 
/*  344: 316 */       return jjMoveStringLiteralDfa3_0(active0, 2147483648L);
/*  345:     */     case 'H': 
/*  346:     */     case 'h': 
/*  347: 319 */       return jjMoveStringLiteralDfa3_0(active0, 73014444032L);
/*  348:     */     case 'M': 
/*  349:     */     case 'm': 
/*  350: 322 */       return jjMoveStringLiteralDfa3_0(active0, 536870912L);
/*  351:     */     case 'P': 
/*  352:     */     case 'p': 
/*  353: 325 */       return jjMoveStringLiteralDfa3_0(active0, 17179869184L);
/*  354:     */     }
/*  355: 329 */     return jjStartNfa_0(1, active0);
/*  356:     */   }
/*  357:     */   
/*  358:     */   private final int jjMoveStringLiteralDfa3_0(long old0, long active0)
/*  359:     */   {
/*  360: 333 */     if ((active0 &= old0) == 0L) {
/*  361: 334 */       return jjStartNfa_0(1, old0);
/*  362:     */     }
/*  363:     */     try
/*  364:     */     {
/*  365: 335 */       this.curChar = this.input_stream.readChar();
/*  366:     */     }
/*  367:     */     catch (IOException e)
/*  368:     */     {
/*  369: 337 */       jjStopStringLiteralDfa_0(2, active0);
/*  370: 338 */       return 3;
/*  371:     */     }
/*  372: 340 */     switch (this.curChar)
/*  373:     */     {
/*  374:     */     case '-': 
/*  375: 343 */       if ((active0 & 0x2000000) != 0L) {
/*  376: 344 */         return jjStopAtPos(3, 25);
/*  377:     */       }
/*  378:     */       break;
/*  379:     */     case 'A': 
/*  380:     */     case 'a': 
/*  381: 348 */       return jjMoveStringLiteralDfa4_0(active0, 4294967296L);
/*  382:     */     case 'D': 
/*  383:     */     case 'd': 
/*  384: 351 */       return jjMoveStringLiteralDfa4_0(active0, 2147483648L);
/*  385:     */     case 'E': 
/*  386:     */     case 'e': 
/*  387: 354 */       return jjMoveStringLiteralDfa4_0(active0, 68719476736L);
/*  388:     */     case 'G': 
/*  389:     */     case 'g': 
/*  390: 357 */       return jjMoveStringLiteralDfa4_0(active0, 1073741824L);
/*  391:     */     case 'O': 
/*  392:     */     case 'o': 
/*  393: 360 */       return jjMoveStringLiteralDfa4_0(active0, 17179869184L);
/*  394:     */     case 'P': 
/*  395:     */     case 'p': 
/*  396: 363 */       return jjMoveStringLiteralDfa4_0(active0, 536870912L);
/*  397:     */     }
/*  398: 367 */     return jjStartNfa_0(2, active0);
/*  399:     */   }
/*  400:     */   
/*  401:     */   private final int jjMoveStringLiteralDfa4_0(long old0, long active0)
/*  402:     */   {
/*  403: 371 */     if ((active0 &= old0) == 0L) {
/*  404: 372 */       return jjStartNfa_0(2, old0);
/*  405:     */     }
/*  406:     */     try
/*  407:     */     {
/*  408: 373 */       this.curChar = this.input_stream.readChar();
/*  409:     */     }
/*  410:     */     catch (IOException e)
/*  411:     */     {
/*  412: 375 */       jjStopStringLiteralDfa_0(3, active0);
/*  413: 376 */       return 4;
/*  414:     */     }
/*  415: 378 */     switch (this.curChar)
/*  416:     */     {
/*  417:     */     case 'E': 
/*  418:     */     case 'e': 
/*  419: 382 */       if ((active0 & 0x40000000) != 0L) {
/*  420: 383 */         return jjStartNfaWithStates_0(4, 30, 839);
/*  421:     */       }
/*  422:     */       break;
/*  423:     */     case 'I': 
/*  424:     */     case 'i': 
/*  425: 387 */       return jjMoveStringLiteralDfa5_0(active0, 2147483648L);
/*  426:     */     case 'O': 
/*  427:     */     case 'o': 
/*  428: 390 */       return jjMoveStringLiteralDfa5_0(active0, 536870912L);
/*  429:     */     case 'R': 
/*  430:     */     case 'r': 
/*  431: 393 */       return jjMoveStringLiteralDfa5_0(active0, 90194313216L);
/*  432:     */     }
/*  433: 397 */     return jjStartNfa_0(3, active0);
/*  434:     */   }
/*  435:     */   
/*  436:     */   private final int jjMoveStringLiteralDfa5_0(long old0, long active0)
/*  437:     */   {
/*  438: 401 */     if ((active0 &= old0) == 0L) {
/*  439: 402 */       return jjStartNfa_0(3, old0);
/*  440:     */     }
/*  441:     */     try
/*  442:     */     {
/*  443: 403 */       this.curChar = this.input_stream.readChar();
/*  444:     */     }
/*  445:     */     catch (IOException e)
/*  446:     */     {
/*  447: 405 */       jjStopStringLiteralDfa_0(4, active0);
/*  448: 406 */       return 5;
/*  449:     */     }
/*  450: 408 */     switch (this.curChar)
/*  451:     */     {
/*  452:     */     case 'A': 
/*  453:     */     case 'a': 
/*  454: 412 */       if ((active0 & 0x80000000) != 0L) {
/*  455: 413 */         return jjStartNfaWithStates_0(5, 31, 839);
/*  456:     */       }
/*  457:     */       break;
/*  458:     */     case 'I': 
/*  459:     */     case 'i': 
/*  460: 417 */       return jjMoveStringLiteralDfa6_0(active0, 68719476736L);
/*  461:     */     case 'R': 
/*  462:     */     case 'r': 
/*  463: 420 */       return jjMoveStringLiteralDfa6_0(active0, 536870912L);
/*  464:     */     case 'S': 
/*  465:     */     case 's': 
/*  466: 423 */       return jjMoveStringLiteralDfa6_0(active0, 4294967296L);
/*  467:     */     case 'T': 
/*  468:     */     case 't': 
/*  469: 426 */       return jjMoveStringLiteralDfa6_0(active0, 17179869184L);
/*  470:     */     }
/*  471: 430 */     return jjStartNfa_0(4, active0);
/*  472:     */   }
/*  473:     */   
/*  474:     */   private final int jjMoveStringLiteralDfa6_0(long old0, long active0)
/*  475:     */   {
/*  476: 434 */     if ((active0 &= old0) == 0L) {
/*  477: 435 */       return jjStartNfa_0(4, old0);
/*  478:     */     }
/*  479:     */     try
/*  480:     */     {
/*  481: 436 */       this.curChar = this.input_stream.readChar();
/*  482:     */     }
/*  483:     */     catch (IOException e)
/*  484:     */     {
/*  485: 438 */       jjStopStringLiteralDfa_0(5, active0);
/*  486: 439 */       return 6;
/*  487:     */     }
/*  488: 441 */     switch (this.curChar)
/*  489:     */     {
/*  490:     */     case 'A': 
/*  491:     */     case 'a': 
/*  492: 445 */       return jjMoveStringLiteralDfa7_0(active0, 17179869184L);
/*  493:     */     case 'E': 
/*  494:     */     case 'e': 
/*  495: 448 */       return jjMoveStringLiteralDfa7_0(active0, 4294967296L);
/*  496:     */     case 'T': 
/*  497:     */     case 't': 
/*  498: 451 */       if ((active0 & 0x20000000) != 0L) {
/*  499: 452 */         return jjStartNfaWithStates_0(6, 29, 839);
/*  500:     */       }
/*  501: 453 */       if ((active0 & 0x0) != 0L) {
/*  502: 454 */         return jjStartNfaWithStates_0(6, 36, 837);
/*  503:     */       }
/*  504:     */       break;
/*  505:     */     }
/*  506: 459 */     return jjStartNfa_0(5, active0);
/*  507:     */   }
/*  508:     */   
/*  509:     */   private final int jjMoveStringLiteralDfa7_0(long old0, long active0)
/*  510:     */   {
/*  511: 463 */     if ((active0 &= old0) == 0L) {
/*  512: 464 */       return jjStartNfa_0(5, old0);
/*  513:     */     }
/*  514:     */     try
/*  515:     */     {
/*  516: 465 */       this.curChar = this.input_stream.readChar();
/*  517:     */     }
/*  518:     */     catch (IOException e)
/*  519:     */     {
/*  520: 467 */       jjStopStringLiteralDfa_0(6, active0);
/*  521: 468 */       return 7;
/*  522:     */     }
/*  523: 470 */     switch (this.curChar)
/*  524:     */     {
/*  525:     */     case 'N': 
/*  526:     */     case 'n': 
/*  527: 474 */       return jjMoveStringLiteralDfa8_0(active0, 17179869184L);
/*  528:     */     case 'T': 
/*  529:     */     case 't': 
/*  530: 477 */       return jjMoveStringLiteralDfa8_0(active0, 4294967296L);
/*  531:     */     }
/*  532: 481 */     return jjStartNfa_0(6, active0);
/*  533:     */   }
/*  534:     */   
/*  535:     */   private final int jjMoveStringLiteralDfa8_0(long old0, long active0)
/*  536:     */   {
/*  537: 485 */     if ((active0 &= old0) == 0L) {
/*  538: 486 */       return jjStartNfa_0(6, old0);
/*  539:     */     }
/*  540:     */     try
/*  541:     */     {
/*  542: 487 */       this.curChar = this.input_stream.readChar();
/*  543:     */     }
/*  544:     */     catch (IOException e)
/*  545:     */     {
/*  546: 489 */       jjStopStringLiteralDfa_0(7, active0);
/*  547: 490 */       return 8;
/*  548:     */     }
/*  549: 492 */     switch (this.curChar)
/*  550:     */     {
/*  551:     */     case ' ': 
/*  552: 495 */       if ((active0 & 0x0) != 0L) {
/*  553: 496 */         return jjStopAtPos(8, 32);
/*  554:     */       }
/*  555:     */       break;
/*  556:     */     case 'T': 
/*  557:     */     case 't': 
/*  558: 500 */       if ((active0 & 0x0) != 0L) {
/*  559: 501 */         return jjStartNfaWithStates_0(8, 34, 837);
/*  560:     */       }
/*  561:     */       break;
/*  562:     */     }
/*  563: 506 */     return jjStartNfa_0(7, active0);
/*  564:     */   }
/*  565:     */   
/*  566:     */   private final void jjCheckNAdd(int state)
/*  567:     */   {
/*  568: 510 */     if (this.jjrounds[state] != this.jjround)
/*  569:     */     {
/*  570: 512 */       this.jjstateSet[(this.jjnewStateCnt++)] = state;
/*  571: 513 */       this.jjrounds[state] = this.jjround;
/*  572:     */     }
/*  573:     */   }
/*  574:     */   
/*  575:     */   private final void jjAddStates(int start, int end)
/*  576:     */   {
/*  577:     */     do
/*  578:     */     {
/*  579: 519 */       this.jjstateSet[(this.jjnewStateCnt++)] = jjnextStates[start];
/*  580: 520 */     } while (start++ != end);
/*  581:     */   }
/*  582:     */   
/*  583:     */   private final void jjCheckNAddTwoStates(int state1, int state2)
/*  584:     */   {
/*  585: 524 */     jjCheckNAdd(state1);
/*  586: 525 */     jjCheckNAdd(state2);
/*  587:     */   }
/*  588:     */   
/*  589:     */   private final void jjCheckNAddStates(int start, int end)
/*  590:     */   {
/*  591:     */     do
/*  592:     */     {
/*  593: 530 */       jjCheckNAdd(jjnextStates[start]);
/*  594: 531 */     } while (start++ != end);
/*  595:     */   }
/*  596:     */   
/*  597:     */   private final void jjCheckNAddStates(int start)
/*  598:     */   {
/*  599: 535 */     jjCheckNAdd(jjnextStates[start]);
/*  600: 536 */     jjCheckNAdd(jjnextStates[(start + 1)]);
/*  601:     */   }
/*  602:     */   
/*  603: 538 */   static final long[] jjbitVec0 = { -2L, -1L, -1L, -1L };
/*  604: 541 */   static final long[] jjbitVec2 = { 0L, 0L, -1L, -1L };
/*  605:     */   
/*  606:     */   private final int jjMoveNfa_0(int startState, int curPos)
/*  607:     */   {
/*  608: 547 */     int startsAt = 0;
/*  609: 548 */     this.jjnewStateCnt = 837;
/*  610: 549 */     int i = 1;
/*  611: 550 */     this.jjstateSet[0] = startState;
/*  612: 551 */     int kind = 2147483647;
/*  613:     */     for (;;)
/*  614:     */     {
/*  615: 554 */       if (++this.jjround == 2147483647) {
/*  616: 555 */         ReInitRounds();
/*  617:     */       }
/*  618: 556 */       if (this.curChar < '@')
/*  619:     */       {
/*  620: 558 */         long l = 1L << this.curChar;
/*  621:     */         do
/*  622:     */         {
/*  623: 561 */           switch (this.jjstateSet[(--i)])
/*  624:     */           {
/*  625:     */           case 139: 
/*  626:     */           case 839: 
/*  627: 565 */             if ((0x0 & l) != 0L)
/*  628:     */             {
/*  629: 567 */               if (kind > 33) {
/*  630: 568 */                 kind = 33;
/*  631:     */               }
/*  632: 569 */               jjCheckNAddTwoStates(139, 140);
/*  633:     */             }
/*  634: 570 */             break;
/*  635:     */           case 137: 
/*  636: 572 */             if (this.curChar == '-') {
/*  637: 573 */               jjAddStates(0, 1);
/*  638:     */             }
/*  639:     */             break;
/*  640:     */           case 4: 
/*  641: 576 */             if ((0x0 & l) != 0L)
/*  642:     */             {
/*  643: 578 */               if (kind > 54) {
/*  644: 579 */                 kind = 54;
/*  645:     */               }
/*  646: 580 */               jjCheckNAddStates(2, 92);
/*  647:     */             }
/*  648: 582 */             else if ((0x3600 & l) != 0L)
/*  649:     */             {
/*  650: 584 */               if (kind > 1) {
/*  651: 585 */                 kind = 1;
/*  652:     */               }
/*  653: 586 */               jjCheckNAddStates(93, 102);
/*  654:     */             }
/*  655: 588 */             else if (this.curChar == '-')
/*  656:     */             {
/*  657: 589 */               jjAddStates(103, 106);
/*  658:     */             }
/*  659: 590 */             else if (this.curChar == '.')
/*  660:     */             {
/*  661: 591 */               jjCheckNAddStates(107, 125);
/*  662:     */             }
/*  663: 592 */             else if (this.curChar == '\'')
/*  664:     */             {
/*  665: 593 */               jjCheckNAddStates(126, 128);
/*  666:     */             }
/*  667: 594 */             else if (this.curChar == '"')
/*  668:     */             {
/*  669: 595 */               jjCheckNAddStates(129, 131);
/*  670:     */             }
/*  671: 596 */             else if (this.curChar == '#')
/*  672:     */             {
/*  673: 597 */               jjCheckNAddTwoStates(5, 6);
/*  674:     */             }
/*  675: 598 */             else if (this.curChar == '>')
/*  676:     */             {
/*  677: 600 */               if (kind > 19) {
/*  678: 601 */                 kind = 19;
/*  679:     */               }
/*  680:     */             }
/*  681: 603 */             else if (this.curChar == '+')
/*  682:     */             {
/*  683: 605 */               if (kind > 14) {
/*  684: 606 */                 kind = 14;
/*  685:     */               }
/*  686:     */             }
/*  687: 608 */             else if (this.curChar == ',')
/*  688:     */             {
/*  689: 610 */               if (kind > 8) {
/*  690: 611 */                 kind = 8;
/*  691:     */               }
/*  692:     */             }
/*  693:     */             break;
/*  694:     */           case 837: 
/*  695: 615 */             if ((0x0 & l) != 0L)
/*  696:     */             {
/*  697: 617 */               if (kind > 56) {
/*  698: 618 */                 kind = 56;
/*  699:     */               }
/*  700: 619 */               jjCheckNAddTwoStates(731, 732);
/*  701:     */             }
/*  702: 621 */             else if (this.curChar == '(')
/*  703:     */             {
/*  704: 623 */               if (kind > 55) {
/*  705: 624 */                 kind = 55;
/*  706:     */               }
/*  707:     */             }
/*  708: 626 */             if ((0x0 & l) != 0L) {
/*  709: 627 */               jjCheckNAddStates(132, 134);
/*  710:     */             }
/*  711:     */             break;
/*  712:     */           case 838: 
/*  713: 630 */             if ((0x0 & l) != 0L)
/*  714:     */             {
/*  715: 632 */               if (kind > 58) {
/*  716: 633 */                 kind = 58;
/*  717:     */               }
/*  718: 634 */               jjCheckNAdd(708);
/*  719:     */             }
/*  720: 636 */             if ((0x0 & l) != 0L)
/*  721:     */             {
/*  722: 638 */               if (kind > 54) {
/*  723: 639 */                 kind = 54;
/*  724:     */               }
/*  725: 640 */               jjCheckNAdd(707);
/*  726:     */             }
/*  727: 642 */             if ((0x0 & l) != 0L) {
/*  728: 643 */               jjCheckNAddTwoStates(705, 706);
/*  729:     */             }
/*  730: 644 */             if ((0x0 & l) != 0L) {
/*  731: 645 */               jjCheckNAddStates(135, 138);
/*  732:     */             }
/*  733: 646 */             if ((0x0 & l) != 0L) {
/*  734: 647 */               jjCheckNAddStates(139, 141);
/*  735:     */             }
/*  736: 648 */             if ((0x0 & l) != 0L) {
/*  737: 649 */               jjCheckNAddStates(142, 144);
/*  738:     */             }
/*  739: 650 */             if ((0x0 & l) != 0L) {
/*  740: 651 */               jjCheckNAddStates(145, 147);
/*  741:     */             }
/*  742: 652 */             if ((0x0 & l) != 0L) {
/*  743: 653 */               jjCheckNAddStates(148, 150);
/*  744:     */             }
/*  745: 654 */             if ((0x0 & l) != 0L) {
/*  746: 655 */               jjCheckNAddStates(151, 153);
/*  747:     */             }
/*  748: 656 */             if ((0x0 & l) != 0L) {
/*  749: 657 */               jjCheckNAddStates(154, 156);
/*  750:     */             }
/*  751: 658 */             if ((0x0 & l) != 0L) {
/*  752: 659 */               jjCheckNAddStates(157, 159);
/*  753:     */             }
/*  754: 660 */             if ((0x0 & l) != 0L) {
/*  755: 661 */               jjCheckNAddStates(160, 162);
/*  756:     */             }
/*  757: 662 */             if ((0x0 & l) != 0L) {
/*  758: 663 */               jjCheckNAddStates(163, 165);
/*  759:     */             }
/*  760: 664 */             if ((0x0 & l) != 0L) {
/*  761: 665 */               jjCheckNAddStates(166, 168);
/*  762:     */             }
/*  763: 666 */             if ((0x0 & l) != 0L) {
/*  764: 667 */               jjCheckNAddStates(169, 171);
/*  765:     */             }
/*  766: 668 */             if ((0x0 & l) != 0L) {
/*  767: 669 */               jjCheckNAddStates(172, 174);
/*  768:     */             }
/*  769: 670 */             if ((0x0 & l) != 0L) {
/*  770: 671 */               jjCheckNAddStates(175, 177);
/*  771:     */             }
/*  772: 672 */             if ((0x0 & l) != 0L) {
/*  773: 673 */               jjCheckNAddStates(178, 180);
/*  774:     */             }
/*  775: 674 */             if ((0x0 & l) != 0L) {
/*  776: 675 */               jjCheckNAddStates(181, 183);
/*  777:     */             }
/*  778:     */             break;
/*  779:     */           case 1: 
/*  780: 678 */             if ((this.curChar == ',') && (kind > 8)) {
/*  781: 679 */               kind = 8;
/*  782:     */             }
/*  783:     */             break;
/*  784:     */           case 2: 
/*  785: 682 */             if ((this.curChar == '+') && (kind > 14)) {
/*  786: 683 */               kind = 14;
/*  787:     */             }
/*  788:     */             break;
/*  789:     */           case 3: 
/*  790: 686 */             if ((this.curChar == '>') && (kind > 19)) {
/*  791: 687 */               kind = 19;
/*  792:     */             }
/*  793:     */             break;
/*  794:     */           case 5: 
/*  795: 690 */             if ((0x0 & l) != 0L)
/*  796:     */             {
/*  797: 692 */               if (kind > 20) {
/*  798: 693 */                 kind = 20;
/*  799:     */               }
/*  800: 694 */               jjCheckNAddTwoStates(5, 6);
/*  801:     */             }
/*  802: 695 */             break;
/*  803:     */           case 7: 
/*  804: 697 */             if ((0xFFFFCBFF & l) != 0L)
/*  805:     */             {
/*  806: 699 */               if (kind > 20) {
/*  807: 700 */                 kind = 20;
/*  808:     */               }
/*  809: 701 */               jjCheckNAddTwoStates(5, 6);
/*  810:     */             }
/*  811: 702 */             break;
/*  812:     */           case 8: 
/*  813: 704 */             if ((0x0 & l) != 0L)
/*  814:     */             {
/*  815: 706 */               if (kind > 20) {
/*  816: 707 */                 kind = 20;
/*  817:     */               }
/*  818: 708 */               jjCheckNAddStates(184, 192);
/*  819:     */             }
/*  820: 709 */             break;
/*  821:     */           case 9: 
/*  822: 711 */             if ((0x0 & l) != 0L)
/*  823:     */             {
/*  824: 713 */               if (kind > 20) {
/*  825: 714 */                 kind = 20;
/*  826:     */               }
/*  827: 715 */               jjCheckNAddStates(193, 196);
/*  828:     */             }
/*  829: 716 */             break;
/*  830:     */           case 10: 
/*  831: 718 */             if (this.curChar == '\n')
/*  832:     */             {
/*  833: 720 */               if (kind > 20) {
/*  834: 721 */                 kind = 20;
/*  835:     */               }
/*  836: 722 */               jjCheckNAddTwoStates(5, 6);
/*  837:     */             }
/*  838: 723 */             break;
/*  839:     */           case 11: 
/*  840: 725 */             if (this.curChar == '\r') {
/*  841: 726 */               this.jjstateSet[(this.jjnewStateCnt++)] = 10;
/*  842:     */             }
/*  843:     */             break;
/*  844:     */           case 12: 
/*  845: 729 */             if ((0x3600 & l) != 0L)
/*  846:     */             {
/*  847: 731 */               if (kind > 20) {
/*  848: 732 */                 kind = 20;
/*  849:     */               }
/*  850: 733 */               jjCheckNAddTwoStates(5, 6);
/*  851:     */             }
/*  852: 734 */             break;
/*  853:     */           case 13: 
/*  854:     */           case 15: 
/*  855:     */           case 18: 
/*  856:     */           case 22: 
/*  857: 739 */             if ((0x0 & l) != 0L) {
/*  858: 740 */               jjCheckNAdd(9);
/*  859:     */             }
/*  860:     */             break;
/*  861:     */           case 14: 
/*  862: 743 */             if ((0x0 & l) != 0L) {
/*  863: 744 */               this.jjstateSet[(this.jjnewStateCnt++)] = 15;
/*  864:     */             }
/*  865:     */             break;
/*  866:     */           case 16: 
/*  867: 747 */             if ((0x0 & l) != 0L) {
/*  868: 748 */               this.jjstateSet[(this.jjnewStateCnt++)] = 17;
/*  869:     */             }
/*  870:     */             break;
/*  871:     */           case 17: 
/*  872: 751 */             if ((0x0 & l) != 0L) {
/*  873: 752 */               this.jjstateSet[(this.jjnewStateCnt++)] = 18;
/*  874:     */             }
/*  875:     */             break;
/*  876:     */           case 19: 
/*  877: 755 */             if ((0x0 & l) != 0L) {
/*  878: 756 */               this.jjstateSet[(this.jjnewStateCnt++)] = 20;
/*  879:     */             }
/*  880:     */             break;
/*  881:     */           case 20: 
/*  882: 759 */             if ((0x0 & l) != 0L) {
/*  883: 760 */               this.jjstateSet[(this.jjnewStateCnt++)] = 21;
/*  884:     */             }
/*  885:     */             break;
/*  886:     */           case 21: 
/*  887: 763 */             if ((0x0 & l) != 0L) {
/*  888: 764 */               this.jjstateSet[(this.jjnewStateCnt++)] = 22;
/*  889:     */             }
/*  890:     */             break;
/*  891:     */           case 23: 
/*  892: 767 */             if (this.curChar == '"') {
/*  893: 768 */               jjCheckNAddStates(129, 131);
/*  894:     */             }
/*  895:     */             break;
/*  896:     */           case 24: 
/*  897: 771 */             if ((0xFFFFCBFF & l) != 0L) {
/*  898: 772 */               jjCheckNAddStates(129, 131);
/*  899:     */             }
/*  900:     */             break;
/*  901:     */           case 25: 
/*  902: 775 */             if ((this.curChar == '"') && (kind > 21)) {
/*  903: 776 */               kind = 21;
/*  904:     */             }
/*  905:     */             break;
/*  906:     */           case 27: 
/*  907: 779 */             if ((0x3400 & l) != 0L) {
/*  908: 780 */               jjCheckNAddStates(129, 131);
/*  909:     */             }
/*  910:     */             break;
/*  911:     */           case 28: 
/*  912: 783 */             if (this.curChar == '\n') {
/*  913: 784 */               jjCheckNAddStates(129, 131);
/*  914:     */             }
/*  915:     */             break;
/*  916:     */           case 29: 
/*  917:     */           case 33: 
/*  918: 788 */             if (this.curChar == '\r') {
/*  919: 789 */               jjCheckNAdd(28);
/*  920:     */             }
/*  921:     */             break;
/*  922:     */           case 30: 
/*  923: 792 */             if ((0xFFFFCBFF & l) != 0L) {
/*  924: 793 */               jjCheckNAddStates(129, 131);
/*  925:     */             }
/*  926:     */             break;
/*  927:     */           case 31: 
/*  928: 796 */             if ((0x0 & l) != 0L) {
/*  929: 797 */               jjCheckNAddStates(197, 206);
/*  930:     */             }
/*  931:     */             break;
/*  932:     */           case 32: 
/*  933: 800 */             if ((0x0 & l) != 0L) {
/*  934: 801 */               jjCheckNAddStates(207, 211);
/*  935:     */             }
/*  936:     */             break;
/*  937:     */           case 34: 
/*  938: 804 */             if ((0x3600 & l) != 0L) {
/*  939: 805 */               jjCheckNAddStates(129, 131);
/*  940:     */             }
/*  941:     */             break;
/*  942:     */           case 35: 
/*  943:     */           case 37: 
/*  944:     */           case 40: 
/*  945:     */           case 44: 
/*  946: 811 */             if ((0x0 & l) != 0L) {
/*  947: 812 */               jjCheckNAdd(32);
/*  948:     */             }
/*  949:     */             break;
/*  950:     */           case 36: 
/*  951: 815 */             if ((0x0 & l) != 0L) {
/*  952: 816 */               this.jjstateSet[(this.jjnewStateCnt++)] = 37;
/*  953:     */             }
/*  954:     */             break;
/*  955:     */           case 38: 
/*  956: 819 */             if ((0x0 & l) != 0L) {
/*  957: 820 */               this.jjstateSet[(this.jjnewStateCnt++)] = 39;
/*  958:     */             }
/*  959:     */             break;
/*  960:     */           case 39: 
/*  961: 823 */             if ((0x0 & l) != 0L) {
/*  962: 824 */               this.jjstateSet[(this.jjnewStateCnt++)] = 40;
/*  963:     */             }
/*  964:     */             break;
/*  965:     */           case 41: 
/*  966: 827 */             if ((0x0 & l) != 0L) {
/*  967: 828 */               this.jjstateSet[(this.jjnewStateCnt++)] = 42;
/*  968:     */             }
/*  969:     */             break;
/*  970:     */           case 42: 
/*  971: 831 */             if ((0x0 & l) != 0L) {
/*  972: 832 */               this.jjstateSet[(this.jjnewStateCnt++)] = 43;
/*  973:     */             }
/*  974:     */             break;
/*  975:     */           case 43: 
/*  976: 835 */             if ((0x0 & l) != 0L) {
/*  977: 836 */               this.jjstateSet[(this.jjnewStateCnt++)] = 44;
/*  978:     */             }
/*  979:     */             break;
/*  980:     */           case 45: 
/*  981: 839 */             if (this.curChar == '\'') {
/*  982: 840 */               jjCheckNAddStates(126, 128);
/*  983:     */             }
/*  984:     */             break;
/*  985:     */           case 46: 
/*  986: 843 */             if ((0xFFFFCBFF & l) != 0L) {
/*  987: 844 */               jjCheckNAddStates(126, 128);
/*  988:     */             }
/*  989:     */             break;
/*  990:     */           case 47: 
/*  991: 847 */             if ((this.curChar == '\'') && (kind > 21)) {
/*  992: 848 */               kind = 21;
/*  993:     */             }
/*  994:     */             break;
/*  995:     */           case 49: 
/*  996: 851 */             if ((0x3400 & l) != 0L) {
/*  997: 852 */               jjCheckNAddStates(126, 128);
/*  998:     */             }
/*  999:     */             break;
/* 1000:     */           case 50: 
/* 1001: 855 */             if (this.curChar == '\n') {
/* 1002: 856 */               jjCheckNAddStates(126, 128);
/* 1003:     */             }
/* 1004:     */             break;
/* 1005:     */           case 51: 
/* 1006:     */           case 55: 
/* 1007: 860 */             if (this.curChar == '\r') {
/* 1008: 861 */               jjCheckNAdd(50);
/* 1009:     */             }
/* 1010:     */             break;
/* 1011:     */           case 52: 
/* 1012: 864 */             if ((0xFFFFCBFF & l) != 0L) {
/* 1013: 865 */               jjCheckNAddStates(126, 128);
/* 1014:     */             }
/* 1015:     */             break;
/* 1016:     */           case 53: 
/* 1017: 868 */             if ((0x0 & l) != 0L) {
/* 1018: 869 */               jjCheckNAddStates(212, 221);
/* 1019:     */             }
/* 1020:     */             break;
/* 1021:     */           case 54: 
/* 1022: 872 */             if ((0x0 & l) != 0L) {
/* 1023: 873 */               jjCheckNAddStates(222, 226);
/* 1024:     */             }
/* 1025:     */             break;
/* 1026:     */           case 56: 
/* 1027: 876 */             if ((0x3600 & l) != 0L) {
/* 1028: 877 */               jjCheckNAddStates(126, 128);
/* 1029:     */             }
/* 1030:     */             break;
/* 1031:     */           case 57: 
/* 1032:     */           case 59: 
/* 1033:     */           case 62: 
/* 1034:     */           case 66: 
/* 1035: 883 */             if ((0x0 & l) != 0L) {
/* 1036: 884 */               jjCheckNAdd(54);
/* 1037:     */             }
/* 1038:     */             break;
/* 1039:     */           case 58: 
/* 1040: 887 */             if ((0x0 & l) != 0L) {
/* 1041: 888 */               this.jjstateSet[(this.jjnewStateCnt++)] = 59;
/* 1042:     */             }
/* 1043:     */             break;
/* 1044:     */           case 60: 
/* 1045: 891 */             if ((0x0 & l) != 0L) {
/* 1046: 892 */               this.jjstateSet[(this.jjnewStateCnt++)] = 61;
/* 1047:     */             }
/* 1048:     */             break;
/* 1049:     */           case 61: 
/* 1050: 895 */             if ((0x0 & l) != 0L) {
/* 1051: 896 */               this.jjstateSet[(this.jjnewStateCnt++)] = 62;
/* 1052:     */             }
/* 1053:     */             break;
/* 1054:     */           case 63: 
/* 1055: 899 */             if ((0x0 & l) != 0L) {
/* 1056: 900 */               this.jjstateSet[(this.jjnewStateCnt++)] = 64;
/* 1057:     */             }
/* 1058:     */             break;
/* 1059:     */           case 64: 
/* 1060: 903 */             if ((0x0 & l) != 0L) {
/* 1061: 904 */               this.jjstateSet[(this.jjnewStateCnt++)] = 65;
/* 1062:     */             }
/* 1063:     */             break;
/* 1064:     */           case 65: 
/* 1065: 907 */             if ((0x0 & l) != 0L) {
/* 1066: 908 */               this.jjstateSet[(this.jjnewStateCnt++)] = 66;
/* 1067:     */             }
/* 1068:     */             break;
/* 1069:     */           case 67: 
/* 1070: 911 */             if (this.curChar == '(') {
/* 1071: 912 */               jjCheckNAddStates(227, 232);
/* 1072:     */             }
/* 1073:     */             break;
/* 1074:     */           case 68: 
/* 1075: 915 */             if ((0x0 & l) != 0L) {
/* 1076: 916 */               jjCheckNAddStates(233, 236);
/* 1077:     */             }
/* 1078:     */             break;
/* 1079:     */           case 69: 
/* 1080: 919 */             if ((0x3600 & l) != 0L) {
/* 1081: 920 */               jjCheckNAddTwoStates(69, 70);
/* 1082:     */             }
/* 1083:     */             break;
/* 1084:     */           case 70: 
/* 1085: 923 */             if ((this.curChar == ')') && (kind > 24)) {
/* 1086: 924 */               kind = 24;
/* 1087:     */             }
/* 1088:     */             break;
/* 1089:     */           case 72: 
/* 1090: 927 */             if ((0xFFFFCBFF & l) != 0L) {
/* 1091: 928 */               jjCheckNAddStates(233, 236);
/* 1092:     */             }
/* 1093:     */             break;
/* 1094:     */           case 73: 
/* 1095: 931 */             if ((0x0 & l) != 0L) {
/* 1096: 932 */               jjCheckNAddStates(237, 246);
/* 1097:     */             }
/* 1098:     */             break;
/* 1099:     */           case 74: 
/* 1100: 935 */             if ((0x0 & l) != 0L) {
/* 1101: 936 */               jjCheckNAddStates(247, 251);
/* 1102:     */             }
/* 1103:     */             break;
/* 1104:     */           case 75: 
/* 1105: 939 */             if (this.curChar == '\n') {
/* 1106: 940 */               jjCheckNAddStates(233, 236);
/* 1107:     */             }
/* 1108:     */             break;
/* 1109:     */           case 76: 
/* 1110: 943 */             if (this.curChar == '\r') {
/* 1111: 944 */               this.jjstateSet[(this.jjnewStateCnt++)] = 75;
/* 1112:     */             }
/* 1113:     */             break;
/* 1114:     */           case 77: 
/* 1115: 947 */             if ((0x3600 & l) != 0L) {
/* 1116: 948 */               jjCheckNAddStates(233, 236);
/* 1117:     */             }
/* 1118:     */             break;
/* 1119:     */           case 78: 
/* 1120:     */           case 80: 
/* 1121:     */           case 83: 
/* 1122:     */           case 87: 
/* 1123: 954 */             if ((0x0 & l) != 0L) {
/* 1124: 955 */               jjCheckNAdd(74);
/* 1125:     */             }
/* 1126:     */             break;
/* 1127:     */           case 79: 
/* 1128: 958 */             if ((0x0 & l) != 0L) {
/* 1129: 959 */               this.jjstateSet[(this.jjnewStateCnt++)] = 80;
/* 1130:     */             }
/* 1131:     */             break;
/* 1132:     */           case 81: 
/* 1133: 962 */             if ((0x0 & l) != 0L) {
/* 1134: 963 */               this.jjstateSet[(this.jjnewStateCnt++)] = 82;
/* 1135:     */             }
/* 1136:     */             break;
/* 1137:     */           case 82: 
/* 1138: 966 */             if ((0x0 & l) != 0L) {
/* 1139: 967 */               this.jjstateSet[(this.jjnewStateCnt++)] = 83;
/* 1140:     */             }
/* 1141:     */             break;
/* 1142:     */           case 84: 
/* 1143: 970 */             if ((0x0 & l) != 0L) {
/* 1144: 971 */               this.jjstateSet[(this.jjnewStateCnt++)] = 85;
/* 1145:     */             }
/* 1146:     */             break;
/* 1147:     */           case 85: 
/* 1148: 974 */             if ((0x0 & l) != 0L) {
/* 1149: 975 */               this.jjstateSet[(this.jjnewStateCnt++)] = 86;
/* 1150:     */             }
/* 1151:     */             break;
/* 1152:     */           case 86: 
/* 1153: 978 */             if ((0x0 & l) != 0L) {
/* 1154: 979 */               this.jjstateSet[(this.jjnewStateCnt++)] = 87;
/* 1155:     */             }
/* 1156:     */             break;
/* 1157:     */           case 88: 
/* 1158: 982 */             if (this.curChar == '\'') {
/* 1159: 983 */               jjCheckNAddStates(252, 254);
/* 1160:     */             }
/* 1161:     */             break;
/* 1162:     */           case 89: 
/* 1163: 986 */             if ((0xFFFFCBFF & l) != 0L) {
/* 1164: 987 */               jjCheckNAddStates(252, 254);
/* 1165:     */             }
/* 1166:     */             break;
/* 1167:     */           case 90: 
/* 1168: 990 */             if (this.curChar == '\'') {
/* 1169: 991 */               jjCheckNAddTwoStates(69, 70);
/* 1170:     */             }
/* 1171:     */             break;
/* 1172:     */           case 92: 
/* 1173: 994 */             if ((0x3400 & l) != 0L) {
/* 1174: 995 */               jjCheckNAddStates(252, 254);
/* 1175:     */             }
/* 1176:     */             break;
/* 1177:     */           case 93: 
/* 1178: 998 */             if (this.curChar == '\n') {
/* 1179: 999 */               jjCheckNAddStates(252, 254);
/* 1180:     */             }
/* 1181:     */             break;
/* 1182:     */           case 94: 
/* 1183:     */           case 98: 
/* 1184:1003 */             if (this.curChar == '\r') {
/* 1185:1004 */               jjCheckNAdd(93);
/* 1186:     */             }
/* 1187:     */             break;
/* 1188:     */           case 95: 
/* 1189:1007 */             if ((0xFFFFCBFF & l) != 0L) {
/* 1190:1008 */               jjCheckNAddStates(252, 254);
/* 1191:     */             }
/* 1192:     */             break;
/* 1193:     */           case 96: 
/* 1194:1011 */             if ((0x0 & l) != 0L) {
/* 1195:1012 */               jjCheckNAddStates(255, 264);
/* 1196:     */             }
/* 1197:     */             break;
/* 1198:     */           case 97: 
/* 1199:1015 */             if ((0x0 & l) != 0L) {
/* 1200:1016 */               jjCheckNAddStates(265, 269);
/* 1201:     */             }
/* 1202:     */             break;
/* 1203:     */           case 99: 
/* 1204:1019 */             if ((0x3600 & l) != 0L) {
/* 1205:1020 */               jjCheckNAddStates(252, 254);
/* 1206:     */             }
/* 1207:     */             break;
/* 1208:     */           case 100: 
/* 1209:     */           case 102: 
/* 1210:     */           case 105: 
/* 1211:     */           case 109: 
/* 1212:1026 */             if ((0x0 & l) != 0L) {
/* 1213:1027 */               jjCheckNAdd(97);
/* 1214:     */             }
/* 1215:     */             break;
/* 1216:     */           case 101: 
/* 1217:1030 */             if ((0x0 & l) != 0L) {
/* 1218:1031 */               this.jjstateSet[(this.jjnewStateCnt++)] = 102;
/* 1219:     */             }
/* 1220:     */             break;
/* 1221:     */           case 103: 
/* 1222:1034 */             if ((0x0 & l) != 0L) {
/* 1223:1035 */               this.jjstateSet[(this.jjnewStateCnt++)] = 104;
/* 1224:     */             }
/* 1225:     */             break;
/* 1226:     */           case 104: 
/* 1227:1038 */             if ((0x0 & l) != 0L) {
/* 1228:1039 */               this.jjstateSet[(this.jjnewStateCnt++)] = 105;
/* 1229:     */             }
/* 1230:     */             break;
/* 1231:     */           case 106: 
/* 1232:1042 */             if ((0x0 & l) != 0L) {
/* 1233:1043 */               this.jjstateSet[(this.jjnewStateCnt++)] = 107;
/* 1234:     */             }
/* 1235:     */             break;
/* 1236:     */           case 107: 
/* 1237:1046 */             if ((0x0 & l) != 0L) {
/* 1238:1047 */               this.jjstateSet[(this.jjnewStateCnt++)] = 108;
/* 1239:     */             }
/* 1240:     */             break;
/* 1241:     */           case 108: 
/* 1242:1050 */             if ((0x0 & l) != 0L) {
/* 1243:1051 */               this.jjstateSet[(this.jjnewStateCnt++)] = 109;
/* 1244:     */             }
/* 1245:     */             break;
/* 1246:     */           case 110: 
/* 1247:1054 */             if (this.curChar == '"') {
/* 1248:1055 */               jjCheckNAddStates(270, 272);
/* 1249:     */             }
/* 1250:     */             break;
/* 1251:     */           case 111: 
/* 1252:1058 */             if ((0xFFFFCBFF & l) != 0L) {
/* 1253:1059 */               jjCheckNAddStates(270, 272);
/* 1254:     */             }
/* 1255:     */             break;
/* 1256:     */           case 112: 
/* 1257:1062 */             if (this.curChar == '"') {
/* 1258:1063 */               jjCheckNAddTwoStates(69, 70);
/* 1259:     */             }
/* 1260:     */             break;
/* 1261:     */           case 114: 
/* 1262:1066 */             if ((0x3400 & l) != 0L) {
/* 1263:1067 */               jjCheckNAddStates(270, 272);
/* 1264:     */             }
/* 1265:     */             break;
/* 1266:     */           case 115: 
/* 1267:1070 */             if (this.curChar == '\n') {
/* 1268:1071 */               jjCheckNAddStates(270, 272);
/* 1269:     */             }
/* 1270:     */             break;
/* 1271:     */           case 116: 
/* 1272:     */           case 120: 
/* 1273:1075 */             if (this.curChar == '\r') {
/* 1274:1076 */               jjCheckNAdd(115);
/* 1275:     */             }
/* 1276:     */             break;
/* 1277:     */           case 117: 
/* 1278:1079 */             if ((0xFFFFCBFF & l) != 0L) {
/* 1279:1080 */               jjCheckNAddStates(270, 272);
/* 1280:     */             }
/* 1281:     */             break;
/* 1282:     */           case 118: 
/* 1283:1083 */             if ((0x0 & l) != 0L) {
/* 1284:1084 */               jjCheckNAddStates(273, 282);
/* 1285:     */             }
/* 1286:     */             break;
/* 1287:     */           case 119: 
/* 1288:1087 */             if ((0x0 & l) != 0L) {
/* 1289:1088 */               jjCheckNAddStates(283, 287);
/* 1290:     */             }
/* 1291:     */             break;
/* 1292:     */           case 121: 
/* 1293:1091 */             if ((0x3600 & l) != 0L) {
/* 1294:1092 */               jjCheckNAddStates(270, 272);
/* 1295:     */             }
/* 1296:     */             break;
/* 1297:     */           case 122: 
/* 1298:     */           case 124: 
/* 1299:     */           case 127: 
/* 1300:     */           case 131: 
/* 1301:1098 */             if ((0x0 & l) != 0L) {
/* 1302:1099 */               jjCheckNAdd(119);
/* 1303:     */             }
/* 1304:     */             break;
/* 1305:     */           case 123: 
/* 1306:1102 */             if ((0x0 & l) != 0L) {
/* 1307:1103 */               this.jjstateSet[(this.jjnewStateCnt++)] = 124;
/* 1308:     */             }
/* 1309:     */             break;
/* 1310:     */           case 125: 
/* 1311:1106 */             if ((0x0 & l) != 0L) {
/* 1312:1107 */               this.jjstateSet[(this.jjnewStateCnt++)] = 126;
/* 1313:     */             }
/* 1314:     */             break;
/* 1315:     */           case 126: 
/* 1316:1110 */             if ((0x0 & l) != 0L) {
/* 1317:1111 */               this.jjstateSet[(this.jjnewStateCnt++)] = 127;
/* 1318:     */             }
/* 1319:     */             break;
/* 1320:     */           case 128: 
/* 1321:1114 */             if ((0x0 & l) != 0L) {
/* 1322:1115 */               this.jjstateSet[(this.jjnewStateCnt++)] = 129;
/* 1323:     */             }
/* 1324:     */             break;
/* 1325:     */           case 129: 
/* 1326:1118 */             if ((0x0 & l) != 0L) {
/* 1327:1119 */               this.jjstateSet[(this.jjnewStateCnt++)] = 130;
/* 1328:     */             }
/* 1329:     */             break;
/* 1330:     */           case 130: 
/* 1331:1122 */             if ((0x0 & l) != 0L) {
/* 1332:1123 */               this.jjstateSet[(this.jjnewStateCnt++)] = 131;
/* 1333:     */             }
/* 1334:     */             break;
/* 1335:     */           case 132: 
/* 1336:1126 */             if ((0x3600 & l) != 0L) {
/* 1337:1127 */               jjCheckNAddStates(288, 294);
/* 1338:     */             }
/* 1339:     */             break;
/* 1340:     */           case 141: 
/* 1341:1130 */             if ((0xFFFFCBFF & l) != 0L)
/* 1342:     */             {
/* 1343:1132 */               if (kind > 33) {
/* 1344:1133 */                 kind = 33;
/* 1345:     */               }
/* 1346:1134 */               jjCheckNAddTwoStates(139, 140);
/* 1347:     */             }
/* 1348:1135 */             break;
/* 1349:     */           case 142: 
/* 1350:1137 */             if ((0x0 & l) != 0L)
/* 1351:     */             {
/* 1352:1139 */               if (kind > 33) {
/* 1353:1140 */                 kind = 33;
/* 1354:     */               }
/* 1355:1141 */               jjCheckNAddStates(295, 303);
/* 1356:     */             }
/* 1357:1142 */             break;
/* 1358:     */           case 143: 
/* 1359:1144 */             if ((0x0 & l) != 0L)
/* 1360:     */             {
/* 1361:1146 */               if (kind > 33) {
/* 1362:1147 */                 kind = 33;
/* 1363:     */               }
/* 1364:1148 */               jjCheckNAddStates(304, 307);
/* 1365:     */             }
/* 1366:1149 */             break;
/* 1367:     */           case 144: 
/* 1368:1151 */             if (this.curChar == '\n')
/* 1369:     */             {
/* 1370:1153 */               if (kind > 33) {
/* 1371:1154 */                 kind = 33;
/* 1372:     */               }
/* 1373:1155 */               jjCheckNAddTwoStates(139, 140);
/* 1374:     */             }
/* 1375:1156 */             break;
/* 1376:     */           case 145: 
/* 1377:     */           case 160: 
/* 1378:1159 */             if (this.curChar == '\r') {
/* 1379:1160 */               jjCheckNAdd(144);
/* 1380:     */             }
/* 1381:     */             break;
/* 1382:     */           case 146: 
/* 1383:1163 */             if ((0x3600 & l) != 0L)
/* 1384:     */             {
/* 1385:1165 */               if (kind > 33) {
/* 1386:1166 */                 kind = 33;
/* 1387:     */               }
/* 1388:1167 */               jjCheckNAddTwoStates(139, 140);
/* 1389:     */             }
/* 1390:1168 */             break;
/* 1391:     */           case 147: 
/* 1392:     */           case 149: 
/* 1393:     */           case 152: 
/* 1394:     */           case 156: 
/* 1395:1173 */             if ((0x0 & l) != 0L) {
/* 1396:1174 */               jjCheckNAdd(143);
/* 1397:     */             }
/* 1398:     */             break;
/* 1399:     */           case 148: 
/* 1400:1177 */             if ((0x0 & l) != 0L) {
/* 1401:1178 */               this.jjstateSet[(this.jjnewStateCnt++)] = 149;
/* 1402:     */             }
/* 1403:     */             break;
/* 1404:     */           case 150: 
/* 1405:1181 */             if ((0x0 & l) != 0L) {
/* 1406:1182 */               this.jjstateSet[(this.jjnewStateCnt++)] = 151;
/* 1407:     */             }
/* 1408:     */             break;
/* 1409:     */           case 151: 
/* 1410:1185 */             if ((0x0 & l) != 0L) {
/* 1411:1186 */               this.jjstateSet[(this.jjnewStateCnt++)] = 152;
/* 1412:     */             }
/* 1413:     */             break;
/* 1414:     */           case 153: 
/* 1415:1189 */             if ((0x0 & l) != 0L) {
/* 1416:1190 */               this.jjstateSet[(this.jjnewStateCnt++)] = 154;
/* 1417:     */             }
/* 1418:     */             break;
/* 1419:     */           case 154: 
/* 1420:1193 */             if ((0x0 & l) != 0L) {
/* 1421:1194 */               this.jjstateSet[(this.jjnewStateCnt++)] = 155;
/* 1422:     */             }
/* 1423:     */             break;
/* 1424:     */           case 155: 
/* 1425:1197 */             if ((0x0 & l) != 0L) {
/* 1426:1198 */               this.jjstateSet[(this.jjnewStateCnt++)] = 156;
/* 1427:     */             }
/* 1428:     */             break;
/* 1429:     */           case 158: 
/* 1430:1201 */             if ((0x0 & l) != 0L)
/* 1431:     */             {
/* 1432:1203 */               if (kind > 33) {
/* 1433:1204 */                 kind = 33;
/* 1434:     */               }
/* 1435:1205 */               jjCheckNAddStates(308, 316);
/* 1436:     */             }
/* 1437:1206 */             break;
/* 1438:     */           case 159: 
/* 1439:1208 */             if ((0x0 & l) != 0L)
/* 1440:     */             {
/* 1441:1210 */               if (kind > 33) {
/* 1442:1211 */                 kind = 33;
/* 1443:     */               }
/* 1444:1212 */               jjCheckNAddStates(317, 320);
/* 1445:     */             }
/* 1446:1213 */             break;
/* 1447:     */           case 161: 
/* 1448:     */           case 163: 
/* 1449:     */           case 166: 
/* 1450:     */           case 170: 
/* 1451:1218 */             if ((0x0 & l) != 0L) {
/* 1452:1219 */               jjCheckNAdd(159);
/* 1453:     */             }
/* 1454:     */             break;
/* 1455:     */           case 162: 
/* 1456:1222 */             if ((0x0 & l) != 0L) {
/* 1457:1223 */               this.jjstateSet[(this.jjnewStateCnt++)] = 163;
/* 1458:     */             }
/* 1459:     */             break;
/* 1460:     */           case 164: 
/* 1461:1226 */             if ((0x0 & l) != 0L) {
/* 1462:1227 */               this.jjstateSet[(this.jjnewStateCnt++)] = 165;
/* 1463:     */             }
/* 1464:     */             break;
/* 1465:     */           case 165: 
/* 1466:1230 */             if ((0x0 & l) != 0L) {
/* 1467:1231 */               this.jjstateSet[(this.jjnewStateCnt++)] = 166;
/* 1468:     */             }
/* 1469:     */             break;
/* 1470:     */           case 167: 
/* 1471:1234 */             if ((0x0 & l) != 0L) {
/* 1472:1235 */               this.jjstateSet[(this.jjnewStateCnt++)] = 168;
/* 1473:     */             }
/* 1474:     */             break;
/* 1475:     */           case 168: 
/* 1476:1238 */             if ((0x0 & l) != 0L) {
/* 1477:1239 */               this.jjstateSet[(this.jjnewStateCnt++)] = 169;
/* 1478:     */             }
/* 1479:     */             break;
/* 1480:     */           case 169: 
/* 1481:1242 */             if ((0x0 & l) != 0L) {
/* 1482:1243 */               this.jjstateSet[(this.jjnewStateCnt++)] = 170;
/* 1483:     */             }
/* 1484:     */             break;
/* 1485:     */           case 171: 
/* 1486:1246 */             if ((0x3600 & l) != 0L)
/* 1487:     */             {
/* 1488:1248 */               if (kind > 1) {
/* 1489:1249 */                 kind = 1;
/* 1490:     */               }
/* 1491:1250 */               jjCheckNAddStates(93, 102);
/* 1492:     */             }
/* 1493:1251 */             break;
/* 1494:     */           case 172: 
/* 1495:1253 */             if ((0x3600 & l) != 0L)
/* 1496:     */             {
/* 1497:1255 */               if (kind > 1) {
/* 1498:1256 */                 kind = 1;
/* 1499:     */               }
/* 1500:1257 */               jjCheckNAdd(172);
/* 1501:     */             }
/* 1502:1258 */             break;
/* 1503:     */           case 173: 
/* 1504:1260 */             if ((0x3600 & l) != 0L)
/* 1505:     */             {
/* 1506:1262 */               if (kind > 2) {
/* 1507:1263 */                 kind = 2;
/* 1508:     */               }
/* 1509:1264 */               jjCheckNAdd(173);
/* 1510:     */             }
/* 1511:1265 */             break;
/* 1512:     */           case 174: 
/* 1513:1267 */             if ((0x3600 & l) != 0L) {
/* 1514:1268 */               jjCheckNAddTwoStates(174, 0);
/* 1515:     */             }
/* 1516:     */             break;
/* 1517:     */           case 175: 
/* 1518:1271 */             if ((0x3600 & l) != 0L) {
/* 1519:1272 */               jjCheckNAddTwoStates(175, 1);
/* 1520:     */             }
/* 1521:     */             break;
/* 1522:     */           case 176: 
/* 1523:1275 */             if ((0x3600 & l) != 0L) {
/* 1524:1276 */               jjCheckNAddTwoStates(176, 2);
/* 1525:     */             }
/* 1526:     */             break;
/* 1527:     */           case 177: 
/* 1528:1279 */             if ((0x3600 & l) != 0L) {
/* 1529:1280 */               jjCheckNAddTwoStates(177, 3);
/* 1530:     */             }
/* 1531:     */             break;
/* 1532:     */           case 178: 
/* 1533:1283 */             if (this.curChar == '.') {
/* 1534:1284 */               jjCheckNAddStates(107, 125);
/* 1535:     */             }
/* 1536:     */             break;
/* 1537:     */           case 179: 
/* 1538:1287 */             if ((0x0 & l) != 0L) {
/* 1539:1288 */               jjCheckNAddStates(181, 183);
/* 1540:     */             }
/* 1541:     */             break;
/* 1542:     */           case 183: 
/* 1543:     */           case 191: 
/* 1544:1292 */             if (this.curChar == '0') {
/* 1545:1293 */               jjCheckNAddTwoStates(188, 189);
/* 1546:     */             }
/* 1547:     */             break;
/* 1548:     */           case 185: 
/* 1549:1296 */             if ((this.curChar == '\n') && (kind > 37)) {
/* 1550:1297 */               kind = 37;
/* 1551:     */             }
/* 1552:     */             break;
/* 1553:     */           case 186: 
/* 1554:1300 */             if (this.curChar == '\r') {
/* 1555:1301 */               this.jjstateSet[(this.jjnewStateCnt++)] = 185;
/* 1556:     */             }
/* 1557:     */             break;
/* 1558:     */           case 187: 
/* 1559:1304 */             if (((0x3600 & l) != 0L) && (kind > 37)) {
/* 1560:1305 */               kind = 37;
/* 1561:     */             }
/* 1562:     */             break;
/* 1563:     */           case 188: 
/* 1564:1308 */             if (this.curChar == '4') {
/* 1565:1309 */               jjCheckNAdd(184);
/* 1566:     */             }
/* 1567:     */             break;
/* 1568:     */           case 189: 
/* 1569:1312 */             if (this.curChar == '6') {
/* 1570:1313 */               jjCheckNAdd(184);
/* 1571:     */             }
/* 1572:     */             break;
/* 1573:     */           case 190: 
/* 1574:1316 */             if (this.curChar == '0') {
/* 1575:1317 */               jjCheckNAddStates(321, 323);
/* 1576:     */             }
/* 1577:     */             break;
/* 1578:     */           case 192: 
/* 1579:     */           case 193: 
/* 1580:1321 */             if (this.curChar == '0') {
/* 1581:1322 */               jjCheckNAdd(191);
/* 1582:     */             }
/* 1583:     */             break;
/* 1584:     */           case 194: 
/* 1585:1325 */             if (this.curChar == '0') {
/* 1586:1326 */               this.jjstateSet[(this.jjnewStateCnt++)] = 193;
/* 1587:     */             }
/* 1588:     */             break;
/* 1589:     */           case 196: 
/* 1590:     */           case 204: 
/* 1591:1330 */             if (this.curChar == '0') {
/* 1592:1331 */               jjCheckNAddTwoStates(201, 202);
/* 1593:     */             }
/* 1594:     */             break;
/* 1595:     */           case 197: 
/* 1596:1334 */             if (this.curChar == '5') {
/* 1597:1335 */               jjCheckNAddStates(324, 327);
/* 1598:     */             }
/* 1599:     */             break;
/* 1600:     */           case 198: 
/* 1601:1338 */             if (this.curChar == '\n') {
/* 1602:1339 */               jjCheckNAddTwoStates(181, 182);
/* 1603:     */             }
/* 1604:     */             break;
/* 1605:     */           case 199: 
/* 1606:1342 */             if (this.curChar == '\r') {
/* 1607:1343 */               this.jjstateSet[(this.jjnewStateCnt++)] = 198;
/* 1608:     */             }
/* 1609:     */             break;
/* 1610:     */           case 200: 
/* 1611:1346 */             if ((0x3600 & l) != 0L) {
/* 1612:1347 */               jjCheckNAddTwoStates(181, 182);
/* 1613:     */             }
/* 1614:     */             break;
/* 1615:     */           case 201: 
/* 1616:1350 */             if (this.curChar == '4') {
/* 1617:1351 */               jjCheckNAdd(197);
/* 1618:     */             }
/* 1619:     */             break;
/* 1620:     */           case 202: 
/* 1621:1354 */             if (this.curChar == '6') {
/* 1622:1355 */               jjCheckNAdd(197);
/* 1623:     */             }
/* 1624:     */             break;
/* 1625:     */           case 203: 
/* 1626:1358 */             if (this.curChar == '0') {
/* 1627:1359 */               jjCheckNAddStates(328, 330);
/* 1628:     */             }
/* 1629:     */             break;
/* 1630:     */           case 205: 
/* 1631:     */           case 206: 
/* 1632:1363 */             if (this.curChar == '0') {
/* 1633:1364 */               jjCheckNAdd(204);
/* 1634:     */             }
/* 1635:     */             break;
/* 1636:     */           case 207: 
/* 1637:1367 */             if (this.curChar == '0') {
/* 1638:1368 */               this.jjstateSet[(this.jjnewStateCnt++)] = 206;
/* 1639:     */             }
/* 1640:     */             break;
/* 1641:     */           case 208: 
/* 1642:1371 */             if ((0x0 & l) != 0L) {
/* 1643:1372 */               jjCheckNAddStates(178, 180);
/* 1644:     */             }
/* 1645:     */             break;
/* 1646:     */           case 212: 
/* 1647:     */           case 220: 
/* 1648:1376 */             if (this.curChar == '0') {
/* 1649:1377 */               jjCheckNAddTwoStates(217, 218);
/* 1650:     */             }
/* 1651:     */             break;
/* 1652:     */           case 213: 
/* 1653:1380 */             if (this.curChar == '8')
/* 1654:     */             {
/* 1655:1382 */               if (kind > 38) {
/* 1656:1383 */                 kind = 38;
/* 1657:     */               }
/* 1658:1384 */               jjAddStates(331, 332);
/* 1659:     */             }
/* 1660:1385 */             break;
/* 1661:     */           case 214: 
/* 1662:1387 */             if ((this.curChar == '\n') && (kind > 38)) {
/* 1663:1388 */               kind = 38;
/* 1664:     */             }
/* 1665:     */             break;
/* 1666:     */           case 215: 
/* 1667:1391 */             if (this.curChar == '\r') {
/* 1668:1392 */               this.jjstateSet[(this.jjnewStateCnt++)] = 214;
/* 1669:     */             }
/* 1670:     */             break;
/* 1671:     */           case 216: 
/* 1672:1395 */             if (((0x3600 & l) != 0L) && (kind > 38)) {
/* 1673:1396 */               kind = 38;
/* 1674:     */             }
/* 1675:     */             break;
/* 1676:     */           case 217: 
/* 1677:1399 */             if (this.curChar == '5') {
/* 1678:1400 */               jjCheckNAdd(213);
/* 1679:     */             }
/* 1680:     */             break;
/* 1681:     */           case 218: 
/* 1682:1403 */             if (this.curChar == '7') {
/* 1683:1404 */               jjCheckNAdd(213);
/* 1684:     */             }
/* 1685:     */             break;
/* 1686:     */           case 219: 
/* 1687:1407 */             if (this.curChar == '0') {
/* 1688:1408 */               jjCheckNAddStates(333, 335);
/* 1689:     */             }
/* 1690:     */             break;
/* 1691:     */           case 221: 
/* 1692:     */           case 222: 
/* 1693:1412 */             if (this.curChar == '0') {
/* 1694:1413 */               jjCheckNAdd(220);
/* 1695:     */             }
/* 1696:     */             break;
/* 1697:     */           case 223: 
/* 1698:1416 */             if (this.curChar == '0') {
/* 1699:1417 */               this.jjstateSet[(this.jjnewStateCnt++)] = 222;
/* 1700:     */             }
/* 1701:     */             break;
/* 1702:     */           case 225: 
/* 1703:     */           case 233: 
/* 1704:1421 */             if (this.curChar == '0') {
/* 1705:1422 */               jjCheckNAddTwoStates(230, 231);
/* 1706:     */             }
/* 1707:     */             break;
/* 1708:     */           case 226: 
/* 1709:1425 */             if (this.curChar == '5') {
/* 1710:1426 */               jjCheckNAddStates(336, 339);
/* 1711:     */             }
/* 1712:     */             break;
/* 1713:     */           case 227: 
/* 1714:1429 */             if (this.curChar == '\n') {
/* 1715:1430 */               jjCheckNAddTwoStates(210, 211);
/* 1716:     */             }
/* 1717:     */             break;
/* 1718:     */           case 228: 
/* 1719:1433 */             if (this.curChar == '\r') {
/* 1720:1434 */               this.jjstateSet[(this.jjnewStateCnt++)] = 227;
/* 1721:     */             }
/* 1722:     */             break;
/* 1723:     */           case 229: 
/* 1724:1437 */             if ((0x3600 & l) != 0L) {
/* 1725:1438 */               jjCheckNAddTwoStates(210, 211);
/* 1726:     */             }
/* 1727:     */             break;
/* 1728:     */           case 230: 
/* 1729:1441 */             if (this.curChar == '4') {
/* 1730:1442 */               jjCheckNAdd(226);
/* 1731:     */             }
/* 1732:     */             break;
/* 1733:     */           case 231: 
/* 1734:1445 */             if (this.curChar == '6') {
/* 1735:1446 */               jjCheckNAdd(226);
/* 1736:     */             }
/* 1737:     */             break;
/* 1738:     */           case 232: 
/* 1739:1449 */             if (this.curChar == '0') {
/* 1740:1450 */               jjCheckNAddStates(340, 342);
/* 1741:     */             }
/* 1742:     */             break;
/* 1743:     */           case 234: 
/* 1744:     */           case 235: 
/* 1745:1454 */             if (this.curChar == '0') {
/* 1746:1455 */               jjCheckNAdd(233);
/* 1747:     */             }
/* 1748:     */             break;
/* 1749:     */           case 236: 
/* 1750:1458 */             if (this.curChar == '0') {
/* 1751:1459 */               this.jjstateSet[(this.jjnewStateCnt++)] = 235;
/* 1752:     */             }
/* 1753:     */             break;
/* 1754:     */           case 237: 
/* 1755:1462 */             if ((0x0 & l) != 0L) {
/* 1756:1463 */               jjCheckNAddStates(175, 177);
/* 1757:     */             }
/* 1758:     */             break;
/* 1759:     */           case 241: 
/* 1760:     */           case 249: 
/* 1761:1467 */             if (this.curChar == '0') {
/* 1762:1468 */               jjCheckNAddTwoStates(246, 247);
/* 1763:     */             }
/* 1764:     */             break;
/* 1765:     */           case 242: 
/* 1766:1471 */             if (this.curChar == '8')
/* 1767:     */             {
/* 1768:1473 */               if (kind > 39) {
/* 1769:1474 */                 kind = 39;
/* 1770:     */               }
/* 1771:1475 */               jjAddStates(343, 344);
/* 1772:     */             }
/* 1773:1476 */             break;
/* 1774:     */           case 243: 
/* 1775:1478 */             if ((this.curChar == '\n') && (kind > 39)) {
/* 1776:1479 */               kind = 39;
/* 1777:     */             }
/* 1778:     */             break;
/* 1779:     */           case 244: 
/* 1780:1482 */             if (this.curChar == '\r') {
/* 1781:1483 */               this.jjstateSet[(this.jjnewStateCnt++)] = 243;
/* 1782:     */             }
/* 1783:     */             break;
/* 1784:     */           case 245: 
/* 1785:1486 */             if (((0x3600 & l) != 0L) && (kind > 39)) {
/* 1786:1487 */               kind = 39;
/* 1787:     */             }
/* 1788:     */             break;
/* 1789:     */           case 246: 
/* 1790:1490 */             if (this.curChar == '5') {
/* 1791:1491 */               jjCheckNAdd(242);
/* 1792:     */             }
/* 1793:     */             break;
/* 1794:     */           case 247: 
/* 1795:1494 */             if (this.curChar == '7') {
/* 1796:1495 */               jjCheckNAdd(242);
/* 1797:     */             }
/* 1798:     */             break;
/* 1799:     */           case 248: 
/* 1800:1498 */             if (this.curChar == '0') {
/* 1801:1499 */               jjCheckNAddStates(345, 347);
/* 1802:     */             }
/* 1803:     */             break;
/* 1804:     */           case 250: 
/* 1805:     */           case 251: 
/* 1806:1503 */             if (this.curChar == '0') {
/* 1807:1504 */               jjCheckNAdd(249);
/* 1808:     */             }
/* 1809:     */             break;
/* 1810:     */           case 252: 
/* 1811:1507 */             if (this.curChar == '0') {
/* 1812:1508 */               this.jjstateSet[(this.jjnewStateCnt++)] = 251;
/* 1813:     */             }
/* 1814:     */             break;
/* 1815:     */           case 254: 
/* 1816:     */           case 262: 
/* 1817:1512 */             if (this.curChar == '0') {
/* 1818:1513 */               jjCheckNAddTwoStates(259, 260);
/* 1819:     */             }
/* 1820:     */             break;
/* 1821:     */           case 255: 
/* 1822:1516 */             if (this.curChar == '0') {
/* 1823:1517 */               jjCheckNAddStates(348, 351);
/* 1824:     */             }
/* 1825:     */             break;
/* 1826:     */           case 256: 
/* 1827:1520 */             if (this.curChar == '\n') {
/* 1828:1521 */               jjCheckNAddTwoStates(239, 240);
/* 1829:     */             }
/* 1830:     */             break;
/* 1831:     */           case 257: 
/* 1832:1524 */             if (this.curChar == '\r') {
/* 1833:1525 */               this.jjstateSet[(this.jjnewStateCnt++)] = 256;
/* 1834:     */             }
/* 1835:     */             break;
/* 1836:     */           case 258: 
/* 1837:1528 */             if ((0x3600 & l) != 0L) {
/* 1838:1529 */               jjCheckNAddTwoStates(239, 240);
/* 1839:     */             }
/* 1840:     */             break;
/* 1841:     */           case 259: 
/* 1842:1532 */             if (this.curChar == '5') {
/* 1843:1533 */               jjCheckNAdd(255);
/* 1844:     */             }
/* 1845:     */             break;
/* 1846:     */           case 260: 
/* 1847:1536 */             if (this.curChar == '7') {
/* 1848:1537 */               jjCheckNAdd(255);
/* 1849:     */             }
/* 1850:     */             break;
/* 1851:     */           case 261: 
/* 1852:1540 */             if (this.curChar == '0') {
/* 1853:1541 */               jjCheckNAddStates(352, 354);
/* 1854:     */             }
/* 1855:     */             break;
/* 1856:     */           case 263: 
/* 1857:     */           case 264: 
/* 1858:1545 */             if (this.curChar == '0') {
/* 1859:1546 */               jjCheckNAdd(262);
/* 1860:     */             }
/* 1861:     */             break;
/* 1862:     */           case 265: 
/* 1863:1549 */             if (this.curChar == '0') {
/* 1864:1550 */               this.jjstateSet[(this.jjnewStateCnt++)] = 264;
/* 1865:     */             }
/* 1866:     */             break;
/* 1867:     */           case 266: 
/* 1868:1553 */             if ((0x0 & l) != 0L) {
/* 1869:1554 */               jjCheckNAddStates(172, 174);
/* 1870:     */             }
/* 1871:     */             break;
/* 1872:     */           case 270: 
/* 1873:     */           case 278: 
/* 1874:1558 */             if (this.curChar == '0') {
/* 1875:1559 */               jjCheckNAddTwoStates(275, 276);
/* 1876:     */             }
/* 1877:     */             break;
/* 1878:     */           case 272: 
/* 1879:1562 */             if ((this.curChar == '\n') && (kind > 40)) {
/* 1880:1563 */               kind = 40;
/* 1881:     */             }
/* 1882:     */             break;
/* 1883:     */           case 273: 
/* 1884:1566 */             if (this.curChar == '\r') {
/* 1885:1567 */               this.jjstateSet[(this.jjnewStateCnt++)] = 272;
/* 1886:     */             }
/* 1887:     */             break;
/* 1888:     */           case 274: 
/* 1889:1570 */             if (((0x3600 & l) != 0L) && (kind > 40)) {
/* 1890:1571 */               kind = 40;
/* 1891:     */             }
/* 1892:     */             break;
/* 1893:     */           case 275: 
/* 1894:1574 */             if (this.curChar == '4') {
/* 1895:1575 */               jjCheckNAdd(271);
/* 1896:     */             }
/* 1897:     */             break;
/* 1898:     */           case 276: 
/* 1899:1578 */             if (this.curChar == '6') {
/* 1900:1579 */               jjCheckNAdd(271);
/* 1901:     */             }
/* 1902:     */             break;
/* 1903:     */           case 277: 
/* 1904:1582 */             if (this.curChar == '0') {
/* 1905:1583 */               jjCheckNAddStates(355, 357);
/* 1906:     */             }
/* 1907:     */             break;
/* 1908:     */           case 279: 
/* 1909:     */           case 280: 
/* 1910:1587 */             if (this.curChar == '0') {
/* 1911:1588 */               jjCheckNAdd(278);
/* 1912:     */             }
/* 1913:     */             break;
/* 1914:     */           case 281: 
/* 1915:1591 */             if (this.curChar == '0') {
/* 1916:1592 */               this.jjstateSet[(this.jjnewStateCnt++)] = 280;
/* 1917:     */             }
/* 1918:     */             break;
/* 1919:     */           case 283: 
/* 1920:     */           case 291: 
/* 1921:1596 */             if (this.curChar == '0') {
/* 1922:1597 */               jjCheckNAddTwoStates(288, 289);
/* 1923:     */             }
/* 1924:     */             break;
/* 1925:     */           case 284: 
/* 1926:1600 */             if (this.curChar == '3') {
/* 1927:1601 */               jjCheckNAddStates(358, 361);
/* 1928:     */             }
/* 1929:     */             break;
/* 1930:     */           case 285: 
/* 1931:1604 */             if (this.curChar == '\n') {
/* 1932:1605 */               jjCheckNAddTwoStates(268, 269);
/* 1933:     */             }
/* 1934:     */             break;
/* 1935:     */           case 286: 
/* 1936:1608 */             if (this.curChar == '\r') {
/* 1937:1609 */               this.jjstateSet[(this.jjnewStateCnt++)] = 285;
/* 1938:     */             }
/* 1939:     */             break;
/* 1940:     */           case 287: 
/* 1941:1612 */             if ((0x3600 & l) != 0L) {
/* 1942:1613 */               jjCheckNAddTwoStates(268, 269);
/* 1943:     */             }
/* 1944:     */             break;
/* 1945:     */           case 288: 
/* 1946:1616 */             if (this.curChar == '4') {
/* 1947:1617 */               jjCheckNAdd(284);
/* 1948:     */             }
/* 1949:     */             break;
/* 1950:     */           case 289: 
/* 1951:1620 */             if (this.curChar == '6') {
/* 1952:1621 */               jjCheckNAdd(284);
/* 1953:     */             }
/* 1954:     */             break;
/* 1955:     */           case 290: 
/* 1956:1624 */             if (this.curChar == '0') {
/* 1957:1625 */               jjCheckNAddStates(362, 364);
/* 1958:     */             }
/* 1959:     */             break;
/* 1960:     */           case 292: 
/* 1961:     */           case 293: 
/* 1962:1629 */             if (this.curChar == '0') {
/* 1963:1630 */               jjCheckNAdd(291);
/* 1964:     */             }
/* 1965:     */             break;
/* 1966:     */           case 294: 
/* 1967:1633 */             if (this.curChar == '0') {
/* 1968:1634 */               this.jjstateSet[(this.jjnewStateCnt++)] = 293;
/* 1969:     */             }
/* 1970:     */             break;
/* 1971:     */           case 295: 
/* 1972:1637 */             if ((0x0 & l) != 0L) {
/* 1973:1638 */               jjCheckNAddStates(169, 171);
/* 1974:     */             }
/* 1975:     */             break;
/* 1976:     */           case 299: 
/* 1977:     */           case 307: 
/* 1978:1642 */             if (this.curChar == '0') {
/* 1979:1643 */               jjCheckNAddTwoStates(304, 305);
/* 1980:     */             }
/* 1981:     */             break;
/* 1982:     */           case 301: 
/* 1983:1646 */             if ((this.curChar == '\n') && (kind > 41)) {
/* 1984:1647 */               kind = 41;
/* 1985:     */             }
/* 1986:     */             break;
/* 1987:     */           case 302: 
/* 1988:1650 */             if (this.curChar == '\r') {
/* 1989:1651 */               this.jjstateSet[(this.jjnewStateCnt++)] = 301;
/* 1990:     */             }
/* 1991:     */             break;
/* 1992:     */           case 303: 
/* 1993:1654 */             if (((0x3600 & l) != 0L) && (kind > 41)) {
/* 1994:1655 */               kind = 41;
/* 1995:     */             }
/* 1996:     */             break;
/* 1997:     */           case 304: 
/* 1998:1658 */             if (this.curChar == '4') {
/* 1999:1659 */               jjCheckNAdd(300);
/* 2000:     */             }
/* 2001:     */             break;
/* 2002:     */           case 305: 
/* 2003:1662 */             if (this.curChar == '6') {
/* 2004:1663 */               jjCheckNAdd(300);
/* 2005:     */             }
/* 2006:     */             break;
/* 2007:     */           case 306: 
/* 2008:1666 */             if (this.curChar == '0') {
/* 2009:1667 */               jjCheckNAddStates(365, 367);
/* 2010:     */             }
/* 2011:     */             break;
/* 2012:     */           case 308: 
/* 2013:     */           case 309: 
/* 2014:1671 */             if (this.curChar == '0') {
/* 2015:1672 */               jjCheckNAdd(307);
/* 2016:     */             }
/* 2017:     */             break;
/* 2018:     */           case 310: 
/* 2019:1675 */             if (this.curChar == '0') {
/* 2020:1676 */               this.jjstateSet[(this.jjnewStateCnt++)] = 309;
/* 2021:     */             }
/* 2022:     */             break;
/* 2023:     */           case 312: 
/* 2024:     */           case 320: 
/* 2025:1680 */             if (this.curChar == '0') {
/* 2026:1681 */               jjCheckNAddTwoStates(317, 318);
/* 2027:     */             }
/* 2028:     */             break;
/* 2029:     */           case 314: 
/* 2030:1684 */             if (this.curChar == '\n') {
/* 2031:1685 */               jjCheckNAddTwoStates(297, 298);
/* 2032:     */             }
/* 2033:     */             break;
/* 2034:     */           case 315: 
/* 2035:1688 */             if (this.curChar == '\r') {
/* 2036:1689 */               this.jjstateSet[(this.jjnewStateCnt++)] = 314;
/* 2037:     */             }
/* 2038:     */             break;
/* 2039:     */           case 316: 
/* 2040:1692 */             if ((0x3600 & l) != 0L) {
/* 2041:1693 */               jjCheckNAddTwoStates(297, 298);
/* 2042:     */             }
/* 2043:     */             break;
/* 2044:     */           case 317: 
/* 2045:1696 */             if (this.curChar == '4') {
/* 2046:1697 */               jjCheckNAdd(313);
/* 2047:     */             }
/* 2048:     */             break;
/* 2049:     */           case 318: 
/* 2050:1700 */             if (this.curChar == '6') {
/* 2051:1701 */               jjCheckNAdd(313);
/* 2052:     */             }
/* 2053:     */             break;
/* 2054:     */           case 319: 
/* 2055:1704 */             if (this.curChar == '0') {
/* 2056:1705 */               jjCheckNAddStates(368, 370);
/* 2057:     */             }
/* 2058:     */             break;
/* 2059:     */           case 321: 
/* 2060:     */           case 322: 
/* 2061:1709 */             if (this.curChar == '0') {
/* 2062:1710 */               jjCheckNAdd(320);
/* 2063:     */             }
/* 2064:     */             break;
/* 2065:     */           case 323: 
/* 2066:1713 */             if (this.curChar == '0') {
/* 2067:1714 */               this.jjstateSet[(this.jjnewStateCnt++)] = 322;
/* 2068:     */             }
/* 2069:     */             break;
/* 2070:     */           case 324: 
/* 2071:1717 */             if ((0x0 & l) != 0L) {
/* 2072:1718 */               jjCheckNAddStates(166, 168);
/* 2073:     */             }
/* 2074:     */             break;
/* 2075:     */           case 328: 
/* 2076:     */           case 336: 
/* 2077:1722 */             if (this.curChar == '0') {
/* 2078:1723 */               jjCheckNAddTwoStates(333, 334);
/* 2079:     */             }
/* 2080:     */             break;
/* 2081:     */           case 330: 
/* 2082:1726 */             if ((this.curChar == '\n') && (kind > 42)) {
/* 2083:1727 */               kind = 42;
/* 2084:     */             }
/* 2085:     */             break;
/* 2086:     */           case 331: 
/* 2087:1730 */             if (this.curChar == '\r') {
/* 2088:1731 */               this.jjstateSet[(this.jjnewStateCnt++)] = 330;
/* 2089:     */             }
/* 2090:     */             break;
/* 2091:     */           case 332: 
/* 2092:1734 */             if (((0x3600 & l) != 0L) && (kind > 42)) {
/* 2093:1735 */               kind = 42;
/* 2094:     */             }
/* 2095:     */             break;
/* 2096:     */           case 333: 
/* 2097:1738 */             if (this.curChar == '4') {
/* 2098:1739 */               jjCheckNAdd(329);
/* 2099:     */             }
/* 2100:     */             break;
/* 2101:     */           case 334: 
/* 2102:1742 */             if (this.curChar == '6') {
/* 2103:1743 */               jjCheckNAdd(329);
/* 2104:     */             }
/* 2105:     */             break;
/* 2106:     */           case 335: 
/* 2107:1746 */             if (this.curChar == '0') {
/* 2108:1747 */               jjCheckNAddStates(371, 373);
/* 2109:     */             }
/* 2110:     */             break;
/* 2111:     */           case 337: 
/* 2112:     */           case 338: 
/* 2113:1751 */             if (this.curChar == '0') {
/* 2114:1752 */               jjCheckNAdd(336);
/* 2115:     */             }
/* 2116:     */             break;
/* 2117:     */           case 339: 
/* 2118:1755 */             if (this.curChar == '0') {
/* 2119:1756 */               this.jjstateSet[(this.jjnewStateCnt++)] = 338;
/* 2120:     */             }
/* 2121:     */             break;
/* 2122:     */           case 341: 
/* 2123:     */           case 349: 
/* 2124:1760 */             if (this.curChar == '0') {
/* 2125:1761 */               jjCheckNAddTwoStates(346, 347);
/* 2126:     */             }
/* 2127:     */             break;
/* 2128:     */           case 342: 
/* 2129:1764 */             if (this.curChar == '9') {
/* 2130:1765 */               jjCheckNAddStates(374, 377);
/* 2131:     */             }
/* 2132:     */             break;
/* 2133:     */           case 343: 
/* 2134:1768 */             if (this.curChar == '\n') {
/* 2135:1769 */               jjCheckNAddTwoStates(326, 327);
/* 2136:     */             }
/* 2137:     */             break;
/* 2138:     */           case 344: 
/* 2139:1772 */             if (this.curChar == '\r') {
/* 2140:1773 */               this.jjstateSet[(this.jjnewStateCnt++)] = 343;
/* 2141:     */             }
/* 2142:     */             break;
/* 2143:     */           case 345: 
/* 2144:1776 */             if ((0x3600 & l) != 0L) {
/* 2145:1777 */               jjCheckNAddTwoStates(326, 327);
/* 2146:     */             }
/* 2147:     */             break;
/* 2148:     */           case 346: 
/* 2149:1780 */             if (this.curChar == '4') {
/* 2150:1781 */               jjCheckNAdd(342);
/* 2151:     */             }
/* 2152:     */             break;
/* 2153:     */           case 347: 
/* 2154:1784 */             if (this.curChar == '6') {
/* 2155:1785 */               jjCheckNAdd(342);
/* 2156:     */             }
/* 2157:     */             break;
/* 2158:     */           case 348: 
/* 2159:1788 */             if (this.curChar == '0') {
/* 2160:1789 */               jjCheckNAddStates(378, 380);
/* 2161:     */             }
/* 2162:     */             break;
/* 2163:     */           case 350: 
/* 2164:     */           case 351: 
/* 2165:1793 */             if (this.curChar == '0') {
/* 2166:1794 */               jjCheckNAdd(349);
/* 2167:     */             }
/* 2168:     */             break;
/* 2169:     */           case 352: 
/* 2170:1797 */             if (this.curChar == '0') {
/* 2171:1798 */               this.jjstateSet[(this.jjnewStateCnt++)] = 351;
/* 2172:     */             }
/* 2173:     */             break;
/* 2174:     */           case 353: 
/* 2175:1801 */             if ((0x0 & l) != 0L) {
/* 2176:1802 */               jjCheckNAddStates(163, 165);
/* 2177:     */             }
/* 2178:     */             break;
/* 2179:     */           case 357: 
/* 2180:     */           case 365: 
/* 2181:1806 */             if (this.curChar == '0') {
/* 2182:1807 */               jjCheckNAddTwoStates(362, 363);
/* 2183:     */             }
/* 2184:     */             break;
/* 2185:     */           case 358: 
/* 2186:1810 */             if (this.curChar == '4')
/* 2187:     */             {
/* 2188:1812 */               if (kind > 43) {
/* 2189:1813 */                 kind = 43;
/* 2190:     */               }
/* 2191:1814 */               jjAddStates(381, 382);
/* 2192:     */             }
/* 2193:1815 */             break;
/* 2194:     */           case 359: 
/* 2195:1817 */             if ((this.curChar == '\n') && (kind > 43)) {
/* 2196:1818 */               kind = 43;
/* 2197:     */             }
/* 2198:     */             break;
/* 2199:     */           case 360: 
/* 2200:1821 */             if (this.curChar == '\r') {
/* 2201:1822 */               this.jjstateSet[(this.jjnewStateCnt++)] = 359;
/* 2202:     */             }
/* 2203:     */             break;
/* 2204:     */           case 361: 
/* 2205:1825 */             if (((0x3600 & l) != 0L) && (kind > 43)) {
/* 2206:1826 */               kind = 43;
/* 2207:     */             }
/* 2208:     */             break;
/* 2209:     */           case 362: 
/* 2210:1829 */             if (this.curChar == '5') {
/* 2211:1830 */               jjCheckNAdd(358);
/* 2212:     */             }
/* 2213:     */             break;
/* 2214:     */           case 363: 
/* 2215:1833 */             if (this.curChar == '7') {
/* 2216:1834 */               jjCheckNAdd(358);
/* 2217:     */             }
/* 2218:     */             break;
/* 2219:     */           case 364: 
/* 2220:1837 */             if (this.curChar == '0') {
/* 2221:1838 */               jjCheckNAddStates(383, 385);
/* 2222:     */             }
/* 2223:     */             break;
/* 2224:     */           case 366: 
/* 2225:     */           case 367: 
/* 2226:1842 */             if (this.curChar == '0') {
/* 2227:1843 */               jjCheckNAdd(365);
/* 2228:     */             }
/* 2229:     */             break;
/* 2230:     */           case 368: 
/* 2231:1846 */             if (this.curChar == '0') {
/* 2232:1847 */               this.jjstateSet[(this.jjnewStateCnt++)] = 367;
/* 2233:     */             }
/* 2234:     */             break;
/* 2235:     */           case 370: 
/* 2236:     */           case 378: 
/* 2237:1851 */             if (this.curChar == '0') {
/* 2238:1852 */               jjCheckNAddTwoStates(375, 376);
/* 2239:     */             }
/* 2240:     */             break;
/* 2241:     */           case 371: 
/* 2242:1855 */             if (this.curChar == '0') {
/* 2243:1856 */               jjCheckNAddStates(386, 389);
/* 2244:     */             }
/* 2245:     */             break;
/* 2246:     */           case 372: 
/* 2247:1859 */             if (this.curChar == '\n') {
/* 2248:1860 */               jjCheckNAddTwoStates(355, 356);
/* 2249:     */             }
/* 2250:     */             break;
/* 2251:     */           case 373: 
/* 2252:1863 */             if (this.curChar == '\r') {
/* 2253:1864 */               this.jjstateSet[(this.jjnewStateCnt++)] = 372;
/* 2254:     */             }
/* 2255:     */             break;
/* 2256:     */           case 374: 
/* 2257:1867 */             if ((0x3600 & l) != 0L) {
/* 2258:1868 */               jjCheckNAddTwoStates(355, 356);
/* 2259:     */             }
/* 2260:     */             break;
/* 2261:     */           case 375: 
/* 2262:1871 */             if (this.curChar == '5') {
/* 2263:1872 */               jjCheckNAdd(371);
/* 2264:     */             }
/* 2265:     */             break;
/* 2266:     */           case 376: 
/* 2267:1875 */             if (this.curChar == '7') {
/* 2268:1876 */               jjCheckNAdd(371);
/* 2269:     */             }
/* 2270:     */             break;
/* 2271:     */           case 377: 
/* 2272:1879 */             if (this.curChar == '0') {
/* 2273:1880 */               jjCheckNAddStates(390, 392);
/* 2274:     */             }
/* 2275:     */             break;
/* 2276:     */           case 379: 
/* 2277:     */           case 380: 
/* 2278:1884 */             if (this.curChar == '0') {
/* 2279:1885 */               jjCheckNAdd(378);
/* 2280:     */             }
/* 2281:     */             break;
/* 2282:     */           case 381: 
/* 2283:1888 */             if (this.curChar == '0') {
/* 2284:1889 */               this.jjstateSet[(this.jjnewStateCnt++)] = 380;
/* 2285:     */             }
/* 2286:     */             break;
/* 2287:     */           case 382: 
/* 2288:1892 */             if ((0x0 & l) != 0L) {
/* 2289:1893 */               jjCheckNAddStates(160, 162);
/* 2290:     */             }
/* 2291:     */             break;
/* 2292:     */           case 386: 
/* 2293:     */           case 394: 
/* 2294:1897 */             if (this.curChar == '0') {
/* 2295:1898 */               jjCheckNAddTwoStates(391, 392);
/* 2296:     */             }
/* 2297:     */             break;
/* 2298:     */           case 387: 
/* 2299:1901 */             if (this.curChar == '3')
/* 2300:     */             {
/* 2301:1903 */               if (kind > 44) {
/* 2302:1904 */                 kind = 44;
/* 2303:     */               }
/* 2304:1905 */               jjAddStates(393, 394);
/* 2305:     */             }
/* 2306:1906 */             break;
/* 2307:     */           case 388: 
/* 2308:1908 */             if ((this.curChar == '\n') && (kind > 44)) {
/* 2309:1909 */               kind = 44;
/* 2310:     */             }
/* 2311:     */             break;
/* 2312:     */           case 389: 
/* 2313:1912 */             if (this.curChar == '\r') {
/* 2314:1913 */               this.jjstateSet[(this.jjnewStateCnt++)] = 388;
/* 2315:     */             }
/* 2316:     */             break;
/* 2317:     */           case 390: 
/* 2318:1916 */             if (((0x3600 & l) != 0L) && (kind > 44)) {
/* 2319:1917 */               kind = 44;
/* 2320:     */             }
/* 2321:     */             break;
/* 2322:     */           case 391: 
/* 2323:1920 */             if (this.curChar == '4') {
/* 2324:1921 */               jjCheckNAdd(387);
/* 2325:     */             }
/* 2326:     */             break;
/* 2327:     */           case 392: 
/* 2328:1924 */             if (this.curChar == '6') {
/* 2329:1925 */               jjCheckNAdd(387);
/* 2330:     */             }
/* 2331:     */             break;
/* 2332:     */           case 393: 
/* 2333:1928 */             if (this.curChar == '0') {
/* 2334:1929 */               jjCheckNAddStates(395, 397);
/* 2335:     */             }
/* 2336:     */             break;
/* 2337:     */           case 395: 
/* 2338:     */           case 396: 
/* 2339:1933 */             if (this.curChar == '0') {
/* 2340:1934 */               jjCheckNAdd(394);
/* 2341:     */             }
/* 2342:     */             break;
/* 2343:     */           case 397: 
/* 2344:1937 */             if (this.curChar == '0') {
/* 2345:1938 */               this.jjstateSet[(this.jjnewStateCnt++)] = 396;
/* 2346:     */             }
/* 2347:     */             break;
/* 2348:     */           case 399: 
/* 2349:     */           case 407: 
/* 2350:1942 */             if (this.curChar == '0') {
/* 2351:1943 */               jjCheckNAddTwoStates(404, 405);
/* 2352:     */             }
/* 2353:     */             break;
/* 2354:     */           case 400: 
/* 2355:1946 */             if (this.curChar == '0') {
/* 2356:1947 */               jjCheckNAddStates(398, 401);
/* 2357:     */             }
/* 2358:     */             break;
/* 2359:     */           case 401: 
/* 2360:1950 */             if (this.curChar == '\n') {
/* 2361:1951 */               jjCheckNAddTwoStates(384, 385);
/* 2362:     */             }
/* 2363:     */             break;
/* 2364:     */           case 402: 
/* 2365:1954 */             if (this.curChar == '\r') {
/* 2366:1955 */               this.jjstateSet[(this.jjnewStateCnt++)] = 401;
/* 2367:     */             }
/* 2368:     */             break;
/* 2369:     */           case 403: 
/* 2370:1958 */             if ((0x3600 & l) != 0L) {
/* 2371:1959 */               jjCheckNAddTwoStates(384, 385);
/* 2372:     */             }
/* 2373:     */             break;
/* 2374:     */           case 404: 
/* 2375:1962 */             if (this.curChar == '5') {
/* 2376:1963 */               jjCheckNAdd(400);
/* 2377:     */             }
/* 2378:     */             break;
/* 2379:     */           case 405: 
/* 2380:1966 */             if (this.curChar == '7') {
/* 2381:1967 */               jjCheckNAdd(400);
/* 2382:     */             }
/* 2383:     */             break;
/* 2384:     */           case 406: 
/* 2385:1970 */             if (this.curChar == '0') {
/* 2386:1971 */               jjCheckNAddStates(402, 404);
/* 2387:     */             }
/* 2388:     */             break;
/* 2389:     */           case 408: 
/* 2390:     */           case 409: 
/* 2391:1975 */             if (this.curChar == '0') {
/* 2392:1976 */               jjCheckNAdd(407);
/* 2393:     */             }
/* 2394:     */             break;
/* 2395:     */           case 410: 
/* 2396:1979 */             if (this.curChar == '0') {
/* 2397:1980 */               this.jjstateSet[(this.jjnewStateCnt++)] = 409;
/* 2398:     */             }
/* 2399:     */             break;
/* 2400:     */           case 411: 
/* 2401:1983 */             if ((0x0 & l) != 0L) {
/* 2402:1984 */               jjCheckNAddStates(157, 159);
/* 2403:     */             }
/* 2404:     */             break;
/* 2405:     */           case 416: 
/* 2406:     */           case 424: 
/* 2407:1988 */             if (this.curChar == '0') {
/* 2408:1989 */               jjCheckNAddTwoStates(421, 422);
/* 2409:     */             }
/* 2410:     */             break;
/* 2411:     */           case 417: 
/* 2412:1992 */             if (this.curChar == '7')
/* 2413:     */             {
/* 2414:1994 */               if (kind > 45) {
/* 2415:1995 */                 kind = 45;
/* 2416:     */               }
/* 2417:1996 */               jjAddStates(405, 406);
/* 2418:     */             }
/* 2419:1997 */             break;
/* 2420:     */           case 418: 
/* 2421:1999 */             if ((this.curChar == '\n') && (kind > 45)) {
/* 2422:2000 */               kind = 45;
/* 2423:     */             }
/* 2424:     */             break;
/* 2425:     */           case 419: 
/* 2426:2003 */             if (this.curChar == '\r') {
/* 2427:2004 */               this.jjstateSet[(this.jjnewStateCnt++)] = 418;
/* 2428:     */             }
/* 2429:     */             break;
/* 2430:     */           case 420: 
/* 2431:2007 */             if (((0x3600 & l) != 0L) && (kind > 45)) {
/* 2432:2008 */               kind = 45;
/* 2433:     */             }
/* 2434:     */             break;
/* 2435:     */           case 421: 
/* 2436:2011 */             if (this.curChar == '4') {
/* 2437:2012 */               jjCheckNAdd(417);
/* 2438:     */             }
/* 2439:     */             break;
/* 2440:     */           case 422: 
/* 2441:2015 */             if (this.curChar == '6') {
/* 2442:2016 */               jjCheckNAdd(417);
/* 2443:     */             }
/* 2444:     */             break;
/* 2445:     */           case 423: 
/* 2446:2019 */             if (this.curChar == '0') {
/* 2447:2020 */               jjCheckNAddStates(407, 409);
/* 2448:     */             }
/* 2449:     */             break;
/* 2450:     */           case 425: 
/* 2451:     */           case 426: 
/* 2452:2024 */             if (this.curChar == '0') {
/* 2453:2025 */               jjCheckNAdd(424);
/* 2454:     */             }
/* 2455:     */             break;
/* 2456:     */           case 427: 
/* 2457:2028 */             if (this.curChar == '0') {
/* 2458:2029 */               this.jjstateSet[(this.jjnewStateCnt++)] = 426;
/* 2459:     */             }
/* 2460:     */             break;
/* 2461:     */           case 429: 
/* 2462:     */           case 437: 
/* 2463:2033 */             if (this.curChar == '0') {
/* 2464:2034 */               jjCheckNAddTwoStates(434, 435);
/* 2465:     */             }
/* 2466:     */             break;
/* 2467:     */           case 430: 
/* 2468:2037 */             if (this.curChar == '5') {
/* 2469:2038 */               jjCheckNAddStates(410, 413);
/* 2470:     */             }
/* 2471:     */             break;
/* 2472:     */           case 431: 
/* 2473:2041 */             if (this.curChar == '\n') {
/* 2474:2042 */               jjCheckNAddTwoStates(414, 415);
/* 2475:     */             }
/* 2476:     */             break;
/* 2477:     */           case 432: 
/* 2478:2045 */             if (this.curChar == '\r') {
/* 2479:2046 */               this.jjstateSet[(this.jjnewStateCnt++)] = 431;
/* 2480:     */             }
/* 2481:     */             break;
/* 2482:     */           case 433: 
/* 2483:2049 */             if ((0x3600 & l) != 0L) {
/* 2484:2050 */               jjCheckNAddTwoStates(414, 415);
/* 2485:     */             }
/* 2486:     */             break;
/* 2487:     */           case 434: 
/* 2488:2053 */             if (this.curChar == '4') {
/* 2489:2054 */               jjCheckNAdd(430);
/* 2490:     */             }
/* 2491:     */             break;
/* 2492:     */           case 435: 
/* 2493:2057 */             if (this.curChar == '6') {
/* 2494:2058 */               jjCheckNAdd(430);
/* 2495:     */             }
/* 2496:     */             break;
/* 2497:     */           case 436: 
/* 2498:2061 */             if (this.curChar == '0') {
/* 2499:2062 */               jjCheckNAddStates(414, 416);
/* 2500:     */             }
/* 2501:     */             break;
/* 2502:     */           case 438: 
/* 2503:     */           case 439: 
/* 2504:2066 */             if (this.curChar == '0') {
/* 2505:2067 */               jjCheckNAdd(437);
/* 2506:     */             }
/* 2507:     */             break;
/* 2508:     */           case 440: 
/* 2509:2070 */             if (this.curChar == '0') {
/* 2510:2071 */               this.jjstateSet[(this.jjnewStateCnt++)] = 439;
/* 2511:     */             }
/* 2512:     */             break;
/* 2513:     */           case 442: 
/* 2514:     */           case 450: 
/* 2515:2075 */             if (this.curChar == '0') {
/* 2516:2076 */               jjCheckNAddTwoStates(447, 448);
/* 2517:     */             }
/* 2518:     */             break;
/* 2519:     */           case 443: 
/* 2520:2079 */             if (this.curChar == '4') {
/* 2521:2080 */               jjCheckNAddStates(417, 420);
/* 2522:     */             }
/* 2523:     */             break;
/* 2524:     */           case 444: 
/* 2525:2083 */             if (this.curChar == '\n') {
/* 2526:2084 */               jjCheckNAddTwoStates(413, 428);
/* 2527:     */             }
/* 2528:     */             break;
/* 2529:     */           case 445: 
/* 2530:2087 */             if (this.curChar == '\r') {
/* 2531:2088 */               this.jjstateSet[(this.jjnewStateCnt++)] = 444;
/* 2532:     */             }
/* 2533:     */             break;
/* 2534:     */           case 446: 
/* 2535:2091 */             if ((0x3600 & l) != 0L) {
/* 2536:2092 */               jjCheckNAddTwoStates(413, 428);
/* 2537:     */             }
/* 2538:     */             break;
/* 2539:     */           case 447: 
/* 2540:2095 */             if (this.curChar == '4') {
/* 2541:2096 */               jjCheckNAdd(443);
/* 2542:     */             }
/* 2543:     */             break;
/* 2544:     */           case 448: 
/* 2545:2099 */             if (this.curChar == '6') {
/* 2546:2100 */               jjCheckNAdd(443);
/* 2547:     */             }
/* 2548:     */             break;
/* 2549:     */           case 449: 
/* 2550:2103 */             if (this.curChar == '0') {
/* 2551:2104 */               jjCheckNAddStates(421, 423);
/* 2552:     */             }
/* 2553:     */             break;
/* 2554:     */           case 451: 
/* 2555:     */           case 452: 
/* 2556:2108 */             if (this.curChar == '0') {
/* 2557:2109 */               jjCheckNAdd(450);
/* 2558:     */             }
/* 2559:     */             break;
/* 2560:     */           case 453: 
/* 2561:2112 */             if (this.curChar == '0') {
/* 2562:2113 */               this.jjstateSet[(this.jjnewStateCnt++)] = 452;
/* 2563:     */             }
/* 2564:     */             break;
/* 2565:     */           case 454: 
/* 2566:2116 */             if ((0x0 & l) != 0L) {
/* 2567:2117 */               jjCheckNAddStates(154, 156);
/* 2568:     */             }
/* 2569:     */             break;
/* 2570:     */           case 459: 
/* 2571:     */           case 467: 
/* 2572:2121 */             if (this.curChar == '0') {
/* 2573:2122 */               jjCheckNAddTwoStates(464, 465);
/* 2574:     */             }
/* 2575:     */             break;
/* 2576:     */           case 460: 
/* 2577:2125 */             if (this.curChar == '4')
/* 2578:     */             {
/* 2579:2127 */               if (kind > 46) {
/* 2580:2128 */                 kind = 46;
/* 2581:     */               }
/* 2582:2129 */               jjAddStates(424, 425);
/* 2583:     */             }
/* 2584:2130 */             break;
/* 2585:     */           case 461: 
/* 2586:2132 */             if ((this.curChar == '\n') && (kind > 46)) {
/* 2587:2133 */               kind = 46;
/* 2588:     */             }
/* 2589:     */             break;
/* 2590:     */           case 462: 
/* 2591:2136 */             if (this.curChar == '\r') {
/* 2592:2137 */               this.jjstateSet[(this.jjnewStateCnt++)] = 461;
/* 2593:     */             }
/* 2594:     */             break;
/* 2595:     */           case 463: 
/* 2596:2140 */             if (((0x3600 & l) != 0L) && (kind > 46)) {
/* 2597:2141 */               kind = 46;
/* 2598:     */             }
/* 2599:     */             break;
/* 2600:     */           case 464: 
/* 2601:2144 */             if (this.curChar == '4') {
/* 2602:2145 */               jjCheckNAdd(460);
/* 2603:     */             }
/* 2604:     */             break;
/* 2605:     */           case 465: 
/* 2606:2148 */             if (this.curChar == '6') {
/* 2607:2149 */               jjCheckNAdd(460);
/* 2608:     */             }
/* 2609:     */             break;
/* 2610:     */           case 466: 
/* 2611:2152 */             if (this.curChar == '0') {
/* 2612:2153 */               jjCheckNAddStates(426, 428);
/* 2613:     */             }
/* 2614:     */             break;
/* 2615:     */           case 468: 
/* 2616:     */           case 469: 
/* 2617:2157 */             if (this.curChar == '0') {
/* 2618:2158 */               jjCheckNAdd(467);
/* 2619:     */             }
/* 2620:     */             break;
/* 2621:     */           case 470: 
/* 2622:2161 */             if (this.curChar == '0') {
/* 2623:2162 */               this.jjstateSet[(this.jjnewStateCnt++)] = 469;
/* 2624:     */             }
/* 2625:     */             break;
/* 2626:     */           case 472: 
/* 2627:     */           case 480: 
/* 2628:2166 */             if (this.curChar == '0') {
/* 2629:2167 */               jjCheckNAddTwoStates(477, 478);
/* 2630:     */             }
/* 2631:     */             break;
/* 2632:     */           case 473: 
/* 2633:2170 */             if (this.curChar == '1') {
/* 2634:2171 */               jjCheckNAddStates(429, 432);
/* 2635:     */             }
/* 2636:     */             break;
/* 2637:     */           case 474: 
/* 2638:2174 */             if (this.curChar == '\n') {
/* 2639:2175 */               jjCheckNAddTwoStates(457, 458);
/* 2640:     */             }
/* 2641:     */             break;
/* 2642:     */           case 475: 
/* 2643:2178 */             if (this.curChar == '\r') {
/* 2644:2179 */               this.jjstateSet[(this.jjnewStateCnt++)] = 474;
/* 2645:     */             }
/* 2646:     */             break;
/* 2647:     */           case 476: 
/* 2648:2182 */             if ((0x3600 & l) != 0L) {
/* 2649:2183 */               jjCheckNAddTwoStates(457, 458);
/* 2650:     */             }
/* 2651:     */             break;
/* 2652:     */           case 477: 
/* 2653:2186 */             if (this.curChar == '4') {
/* 2654:2187 */               jjCheckNAdd(473);
/* 2655:     */             }
/* 2656:     */             break;
/* 2657:     */           case 478: 
/* 2658:2190 */             if (this.curChar == '6') {
/* 2659:2191 */               jjCheckNAdd(473);
/* 2660:     */             }
/* 2661:     */             break;
/* 2662:     */           case 479: 
/* 2663:2194 */             if (this.curChar == '0') {
/* 2664:2195 */               jjCheckNAddStates(433, 435);
/* 2665:     */             }
/* 2666:     */             break;
/* 2667:     */           case 481: 
/* 2668:     */           case 482: 
/* 2669:2199 */             if (this.curChar == '0') {
/* 2670:2200 */               jjCheckNAdd(480);
/* 2671:     */             }
/* 2672:     */             break;
/* 2673:     */           case 483: 
/* 2674:2203 */             if (this.curChar == '0') {
/* 2675:2204 */               this.jjstateSet[(this.jjnewStateCnt++)] = 482;
/* 2676:     */             }
/* 2677:     */             break;
/* 2678:     */           case 485: 
/* 2679:     */           case 493: 
/* 2680:2208 */             if (this.curChar == '0') {
/* 2681:2209 */               jjCheckNAddTwoStates(490, 491);
/* 2682:     */             }
/* 2683:     */             break;
/* 2684:     */           case 486: 
/* 2685:2212 */             if (this.curChar == '2') {
/* 2686:2213 */               jjCheckNAddStates(436, 439);
/* 2687:     */             }
/* 2688:     */             break;
/* 2689:     */           case 487: 
/* 2690:2216 */             if (this.curChar == '\n') {
/* 2691:2217 */               jjCheckNAddTwoStates(456, 471);
/* 2692:     */             }
/* 2693:     */             break;
/* 2694:     */           case 488: 
/* 2695:2220 */             if (this.curChar == '\r') {
/* 2696:2221 */               this.jjstateSet[(this.jjnewStateCnt++)] = 487;
/* 2697:     */             }
/* 2698:     */             break;
/* 2699:     */           case 489: 
/* 2700:2224 */             if ((0x3600 & l) != 0L) {
/* 2701:2225 */               jjCheckNAddTwoStates(456, 471);
/* 2702:     */             }
/* 2703:     */             break;
/* 2704:     */           case 490: 
/* 2705:2228 */             if (this.curChar == '5') {
/* 2706:2229 */               jjCheckNAdd(486);
/* 2707:     */             }
/* 2708:     */             break;
/* 2709:     */           case 491: 
/* 2710:2232 */             if (this.curChar == '7') {
/* 2711:2233 */               jjCheckNAdd(486);
/* 2712:     */             }
/* 2713:     */             break;
/* 2714:     */           case 492: 
/* 2715:2236 */             if (this.curChar == '0') {
/* 2716:2237 */               jjCheckNAddStates(440, 442);
/* 2717:     */             }
/* 2718:     */             break;
/* 2719:     */           case 494: 
/* 2720:     */           case 495: 
/* 2721:2241 */             if (this.curChar == '0') {
/* 2722:2242 */               jjCheckNAdd(493);
/* 2723:     */             }
/* 2724:     */             break;
/* 2725:     */           case 496: 
/* 2726:2245 */             if (this.curChar == '0') {
/* 2727:2246 */               this.jjstateSet[(this.jjnewStateCnt++)] = 495;
/* 2728:     */             }
/* 2729:     */             break;
/* 2730:     */           case 497: 
/* 2731:2249 */             if ((0x0 & l) != 0L) {
/* 2732:2250 */               jjCheckNAddStates(151, 153);
/* 2733:     */             }
/* 2734:     */             break;
/* 2735:     */           case 503: 
/* 2736:     */           case 511: 
/* 2737:2254 */             if (this.curChar == '0') {
/* 2738:2255 */               jjCheckNAddTwoStates(508, 509);
/* 2739:     */             }
/* 2740:     */             break;
/* 2741:     */           case 504: 
/* 2742:2258 */             if (this.curChar == '4')
/* 2743:     */             {
/* 2744:2260 */               if (kind > 47) {
/* 2745:2261 */                 kind = 47;
/* 2746:     */               }
/* 2747:2262 */               jjAddStates(443, 444);
/* 2748:     */             }
/* 2749:2263 */             break;
/* 2750:     */           case 505: 
/* 2751:2265 */             if ((this.curChar == '\n') && (kind > 47)) {
/* 2752:2266 */               kind = 47;
/* 2753:     */             }
/* 2754:     */             break;
/* 2755:     */           case 506: 
/* 2756:2269 */             if (this.curChar == '\r') {
/* 2757:2270 */               this.jjstateSet[(this.jjnewStateCnt++)] = 505;
/* 2758:     */             }
/* 2759:     */             break;
/* 2760:     */           case 507: 
/* 2761:2273 */             if (((0x3600 & l) != 0L) && (kind > 47)) {
/* 2762:2274 */               kind = 47;
/* 2763:     */             }
/* 2764:     */             break;
/* 2765:     */           case 508: 
/* 2766:2277 */             if (this.curChar == '4') {
/* 2767:2278 */               jjCheckNAdd(504);
/* 2768:     */             }
/* 2769:     */             break;
/* 2770:     */           case 509: 
/* 2771:2281 */             if (this.curChar == '6') {
/* 2772:2282 */               jjCheckNAdd(504);
/* 2773:     */             }
/* 2774:     */             break;
/* 2775:     */           case 510: 
/* 2776:2285 */             if (this.curChar == '0') {
/* 2777:2286 */               jjCheckNAddStates(445, 447);
/* 2778:     */             }
/* 2779:     */             break;
/* 2780:     */           case 512: 
/* 2781:     */           case 513: 
/* 2782:2290 */             if (this.curChar == '0') {
/* 2783:2291 */               jjCheckNAdd(511);
/* 2784:     */             }
/* 2785:     */             break;
/* 2786:     */           case 514: 
/* 2787:2294 */             if (this.curChar == '0') {
/* 2788:2295 */               this.jjstateSet[(this.jjnewStateCnt++)] = 513;
/* 2789:     */             }
/* 2790:     */             break;
/* 2791:     */           case 516: 
/* 2792:     */           case 524: 
/* 2793:2299 */             if (this.curChar == '0') {
/* 2794:2300 */               jjCheckNAddTwoStates(521, 522);
/* 2795:     */             }
/* 2796:     */             break;
/* 2797:     */           case 517: 
/* 2798:2303 */             if (this.curChar == '1') {
/* 2799:2304 */               jjCheckNAddStates(448, 451);
/* 2800:     */             }
/* 2801:     */             break;
/* 2802:     */           case 518: 
/* 2803:2307 */             if (this.curChar == '\n') {
/* 2804:2308 */               jjCheckNAddTwoStates(501, 502);
/* 2805:     */             }
/* 2806:     */             break;
/* 2807:     */           case 519: 
/* 2808:2311 */             if (this.curChar == '\r') {
/* 2809:2312 */               this.jjstateSet[(this.jjnewStateCnt++)] = 518;
/* 2810:     */             }
/* 2811:     */             break;
/* 2812:     */           case 520: 
/* 2813:2315 */             if ((0x3600 & l) != 0L) {
/* 2814:2316 */               jjCheckNAddTwoStates(501, 502);
/* 2815:     */             }
/* 2816:     */             break;
/* 2817:     */           case 521: 
/* 2818:2319 */             if (this.curChar == '4') {
/* 2819:2320 */               jjCheckNAdd(517);
/* 2820:     */             }
/* 2821:     */             break;
/* 2822:     */           case 522: 
/* 2823:2323 */             if (this.curChar == '6') {
/* 2824:2324 */               jjCheckNAdd(517);
/* 2825:     */             }
/* 2826:     */             break;
/* 2827:     */           case 523: 
/* 2828:2327 */             if (this.curChar == '0') {
/* 2829:2328 */               jjCheckNAddStates(452, 454);
/* 2830:     */             }
/* 2831:     */             break;
/* 2832:     */           case 525: 
/* 2833:     */           case 526: 
/* 2834:2332 */             if (this.curChar == '0') {
/* 2835:2333 */               jjCheckNAdd(524);
/* 2836:     */             }
/* 2837:     */             break;
/* 2838:     */           case 527: 
/* 2839:2336 */             if (this.curChar == '0') {
/* 2840:2337 */               this.jjstateSet[(this.jjnewStateCnt++)] = 526;
/* 2841:     */             }
/* 2842:     */             break;
/* 2843:     */           case 529: 
/* 2844:     */           case 537: 
/* 2845:2341 */             if (this.curChar == '0') {
/* 2846:2342 */               jjCheckNAddTwoStates(534, 535);
/* 2847:     */             }
/* 2848:     */             break;
/* 2849:     */           case 530: 
/* 2850:2345 */             if (this.curChar == '2') {
/* 2851:2346 */               jjCheckNAddStates(455, 458);
/* 2852:     */             }
/* 2853:     */             break;
/* 2854:     */           case 531: 
/* 2855:2349 */             if (this.curChar == '\n') {
/* 2856:2350 */               jjCheckNAddTwoStates(500, 515);
/* 2857:     */             }
/* 2858:     */             break;
/* 2859:     */           case 532: 
/* 2860:2353 */             if (this.curChar == '\r') {
/* 2861:2354 */               this.jjstateSet[(this.jjnewStateCnt++)] = 531;
/* 2862:     */             }
/* 2863:     */             break;
/* 2864:     */           case 533: 
/* 2865:2357 */             if ((0x3600 & l) != 0L) {
/* 2866:2358 */               jjCheckNAddTwoStates(500, 515);
/* 2867:     */             }
/* 2868:     */             break;
/* 2869:     */           case 534: 
/* 2870:2361 */             if (this.curChar == '5') {
/* 2871:2362 */               jjCheckNAdd(530);
/* 2872:     */             }
/* 2873:     */             break;
/* 2874:     */           case 535: 
/* 2875:2365 */             if (this.curChar == '7') {
/* 2876:2366 */               jjCheckNAdd(530);
/* 2877:     */             }
/* 2878:     */             break;
/* 2879:     */           case 536: 
/* 2880:2369 */             if (this.curChar == '0') {
/* 2881:2370 */               jjCheckNAddStates(459, 461);
/* 2882:     */             }
/* 2883:     */             break;
/* 2884:     */           case 538: 
/* 2885:     */           case 539: 
/* 2886:2374 */             if (this.curChar == '0') {
/* 2887:2375 */               jjCheckNAdd(537);
/* 2888:     */             }
/* 2889:     */             break;
/* 2890:     */           case 540: 
/* 2891:2378 */             if (this.curChar == '0') {
/* 2892:2379 */               this.jjstateSet[(this.jjnewStateCnt++)] = 539;
/* 2893:     */             }
/* 2894:     */             break;
/* 2895:     */           case 542: 
/* 2896:     */           case 550: 
/* 2897:2383 */             if (this.curChar == '0') {
/* 2898:2384 */               jjCheckNAddTwoStates(547, 548);
/* 2899:     */             }
/* 2900:     */             break;
/* 2901:     */           case 543: 
/* 2902:2387 */             if (this.curChar == '7') {
/* 2903:2388 */               jjCheckNAddStates(462, 465);
/* 2904:     */             }
/* 2905:     */             break;
/* 2906:     */           case 544: 
/* 2907:2391 */             if (this.curChar == '\n') {
/* 2908:2392 */               jjCheckNAddTwoStates(499, 528);
/* 2909:     */             }
/* 2910:     */             break;
/* 2911:     */           case 545: 
/* 2912:2395 */             if (this.curChar == '\r') {
/* 2913:2396 */               this.jjstateSet[(this.jjnewStateCnt++)] = 544;
/* 2914:     */             }
/* 2915:     */             break;
/* 2916:     */           case 546: 
/* 2917:2399 */             if ((0x3600 & l) != 0L) {
/* 2918:2400 */               jjCheckNAddTwoStates(499, 528);
/* 2919:     */             }
/* 2920:     */             break;
/* 2921:     */           case 547: 
/* 2922:2403 */             if (this.curChar == '4') {
/* 2923:2404 */               jjCheckNAdd(543);
/* 2924:     */             }
/* 2925:     */             break;
/* 2926:     */           case 548: 
/* 2927:2407 */             if (this.curChar == '6') {
/* 2928:2408 */               jjCheckNAdd(543);
/* 2929:     */             }
/* 2930:     */             break;
/* 2931:     */           case 549: 
/* 2932:2411 */             if (this.curChar == '0') {
/* 2933:2412 */               jjCheckNAddStates(466, 468);
/* 2934:     */             }
/* 2935:     */             break;
/* 2936:     */           case 551: 
/* 2937:     */           case 552: 
/* 2938:2416 */             if (this.curChar == '0') {
/* 2939:2417 */               jjCheckNAdd(550);
/* 2940:     */             }
/* 2941:     */             break;
/* 2942:     */           case 553: 
/* 2943:2420 */             if (this.curChar == '0') {
/* 2944:2421 */               this.jjstateSet[(this.jjnewStateCnt++)] = 552;
/* 2945:     */             }
/* 2946:     */             break;
/* 2947:     */           case 554: 
/* 2948:2424 */             if ((0x0 & l) != 0L) {
/* 2949:2425 */               jjCheckNAddStates(148, 150);
/* 2950:     */             }
/* 2951:     */             break;
/* 2952:     */           case 558: 
/* 2953:     */           case 566: 
/* 2954:2429 */             if (this.curChar == '0') {
/* 2955:2430 */               jjCheckNAddTwoStates(563, 564);
/* 2956:     */             }
/* 2957:     */             break;
/* 2958:     */           case 559: 
/* 2959:2433 */             if (this.curChar == '3')
/* 2960:     */             {
/* 2961:2435 */               if (kind > 48) {
/* 2962:2436 */                 kind = 48;
/* 2963:     */               }
/* 2964:2437 */               jjAddStates(469, 470);
/* 2965:     */             }
/* 2966:2438 */             break;
/* 2967:     */           case 560: 
/* 2968:2440 */             if ((this.curChar == '\n') && (kind > 48)) {
/* 2969:2441 */               kind = 48;
/* 2970:     */             }
/* 2971:     */             break;
/* 2972:     */           case 561: 
/* 2973:2444 */             if (this.curChar == '\r') {
/* 2974:2445 */               this.jjstateSet[(this.jjnewStateCnt++)] = 560;
/* 2975:     */             }
/* 2976:     */             break;
/* 2977:     */           case 562: 
/* 2978:2448 */             if (((0x3600 & l) != 0L) && (kind > 48)) {
/* 2979:2449 */               kind = 48;
/* 2980:     */             }
/* 2981:     */             break;
/* 2982:     */           case 563: 
/* 2983:2452 */             if (this.curChar == '5') {
/* 2984:2453 */               jjCheckNAdd(559);
/* 2985:     */             }
/* 2986:     */             break;
/* 2987:     */           case 564: 
/* 2988:2456 */             if (this.curChar == '7') {
/* 2989:2457 */               jjCheckNAdd(559);
/* 2990:     */             }
/* 2991:     */             break;
/* 2992:     */           case 565: 
/* 2993:2460 */             if (this.curChar == '0') {
/* 2994:2461 */               jjCheckNAddStates(471, 473);
/* 2995:     */             }
/* 2996:     */             break;
/* 2997:     */           case 567: 
/* 2998:     */           case 568: 
/* 2999:2465 */             if (this.curChar == '0') {
/* 3000:2466 */               jjCheckNAdd(566);
/* 3001:     */             }
/* 3002:     */             break;
/* 3003:     */           case 569: 
/* 3004:2469 */             if (this.curChar == '0') {
/* 3005:2470 */               this.jjstateSet[(this.jjnewStateCnt++)] = 568;
/* 3006:     */             }
/* 3007:     */             break;
/* 3008:     */           case 571: 
/* 3009:     */           case 579: 
/* 3010:2474 */             if (this.curChar == '0') {
/* 3011:2475 */               jjCheckNAddTwoStates(576, 577);
/* 3012:     */             }
/* 3013:     */             break;
/* 3014:     */           case 573: 
/* 3015:2478 */             if (this.curChar == '\n') {
/* 3016:2479 */               jjCheckNAddTwoStates(556, 557);
/* 3017:     */             }
/* 3018:     */             break;
/* 3019:     */           case 574: 
/* 3020:2482 */             if (this.curChar == '\r') {
/* 3021:2483 */               this.jjstateSet[(this.jjnewStateCnt++)] = 573;
/* 3022:     */             }
/* 3023:     */             break;
/* 3024:     */           case 575: 
/* 3025:2486 */             if ((0x3600 & l) != 0L) {
/* 3026:2487 */               jjCheckNAddTwoStates(556, 557);
/* 3027:     */             }
/* 3028:     */             break;
/* 3029:     */           case 576: 
/* 3030:2490 */             if (this.curChar == '4') {
/* 3031:2491 */               jjCheckNAdd(572);
/* 3032:     */             }
/* 3033:     */             break;
/* 3034:     */           case 577: 
/* 3035:2494 */             if (this.curChar == '6') {
/* 3036:2495 */               jjCheckNAdd(572);
/* 3037:     */             }
/* 3038:     */             break;
/* 3039:     */           case 578: 
/* 3040:2498 */             if (this.curChar == '0') {
/* 3041:2499 */               jjCheckNAddStates(474, 476);
/* 3042:     */             }
/* 3043:     */             break;
/* 3044:     */           case 580: 
/* 3045:     */           case 581: 
/* 3046:2503 */             if (this.curChar == '0') {
/* 3047:2504 */               jjCheckNAdd(579);
/* 3048:     */             }
/* 3049:     */             break;
/* 3050:     */           case 582: 
/* 3051:2507 */             if (this.curChar == '0') {
/* 3052:2508 */               this.jjstateSet[(this.jjnewStateCnt++)] = 581;
/* 3053:     */             }
/* 3054:     */             break;
/* 3055:     */           case 583: 
/* 3056:2511 */             if ((0x0 & l) != 0L) {
/* 3057:2512 */               jjCheckNAddStates(145, 147);
/* 3058:     */             }
/* 3059:     */             break;
/* 3060:     */           case 586: 
/* 3061:     */           case 594: 
/* 3062:2516 */             if (this.curChar == '0') {
/* 3063:2517 */               jjCheckNAddTwoStates(591, 592);
/* 3064:     */             }
/* 3065:     */             break;
/* 3066:     */           case 587: 
/* 3067:2520 */             if (this.curChar == '3')
/* 3068:     */             {
/* 3069:2522 */               if (kind > 49) {
/* 3070:2523 */                 kind = 49;
/* 3071:     */               }
/* 3072:2524 */               jjAddStates(477, 478);
/* 3073:     */             }
/* 3074:2525 */             break;
/* 3075:     */           case 588: 
/* 3076:2527 */             if ((this.curChar == '\n') && (kind > 49)) {
/* 3077:2528 */               kind = 49;
/* 3078:     */             }
/* 3079:     */             break;
/* 3080:     */           case 589: 
/* 3081:2531 */             if (this.curChar == '\r') {
/* 3082:2532 */               this.jjstateSet[(this.jjnewStateCnt++)] = 588;
/* 3083:     */             }
/* 3084:     */             break;
/* 3085:     */           case 590: 
/* 3086:2535 */             if (((0x3600 & l) != 0L) && (kind > 49)) {
/* 3087:2536 */               kind = 49;
/* 3088:     */             }
/* 3089:     */             break;
/* 3090:     */           case 591: 
/* 3091:2539 */             if (this.curChar == '5') {
/* 3092:2540 */               jjCheckNAdd(587);
/* 3093:     */             }
/* 3094:     */             break;
/* 3095:     */           case 592: 
/* 3096:2543 */             if (this.curChar == '7') {
/* 3097:2544 */               jjCheckNAdd(587);
/* 3098:     */             }
/* 3099:     */             break;
/* 3100:     */           case 593: 
/* 3101:2547 */             if (this.curChar == '0') {
/* 3102:2548 */               jjCheckNAddStates(479, 481);
/* 3103:     */             }
/* 3104:     */             break;
/* 3105:     */           case 595: 
/* 3106:     */           case 596: 
/* 3107:2552 */             if (this.curChar == '0') {
/* 3108:2553 */               jjCheckNAdd(594);
/* 3109:     */             }
/* 3110:     */             break;
/* 3111:     */           case 597: 
/* 3112:2556 */             if (this.curChar == '0') {
/* 3113:2557 */               this.jjstateSet[(this.jjnewStateCnt++)] = 596;
/* 3114:     */             }
/* 3115:     */             break;
/* 3116:     */           case 598: 
/* 3117:2560 */             if ((0x0 & l) != 0L) {
/* 3118:2561 */               jjCheckNAddStates(142, 144);
/* 3119:     */             }
/* 3120:     */             break;
/* 3121:     */           case 602: 
/* 3122:     */           case 610: 
/* 3123:2565 */             if (this.curChar == '0') {
/* 3124:2566 */               jjCheckNAddTwoStates(607, 608);
/* 3125:     */             }
/* 3126:     */             break;
/* 3127:     */           case 604: 
/* 3128:2569 */             if ((this.curChar == '\n') && (kind > 50)) {
/* 3129:2570 */               kind = 50;
/* 3130:     */             }
/* 3131:     */             break;
/* 3132:     */           case 605: 
/* 3133:2573 */             if (this.curChar == '\r') {
/* 3134:2574 */               this.jjstateSet[(this.jjnewStateCnt++)] = 604;
/* 3135:     */             }
/* 3136:     */             break;
/* 3137:     */           case 606: 
/* 3138:2577 */             if (((0x3600 & l) != 0L) && (kind > 50)) {
/* 3139:2578 */               kind = 50;
/* 3140:     */             }
/* 3141:     */             break;
/* 3142:     */           case 607: 
/* 3143:2581 */             if (this.curChar == '5') {
/* 3144:2582 */               jjCheckNAdd(603);
/* 3145:     */             }
/* 3146:     */             break;
/* 3147:     */           case 608: 
/* 3148:2585 */             if (this.curChar == '7') {
/* 3149:2586 */               jjCheckNAdd(603);
/* 3150:     */             }
/* 3151:     */             break;
/* 3152:     */           case 609: 
/* 3153:2589 */             if (this.curChar == '0') {
/* 3154:2590 */               jjCheckNAddStates(482, 484);
/* 3155:     */             }
/* 3156:     */             break;
/* 3157:     */           case 611: 
/* 3158:     */           case 612: 
/* 3159:2594 */             if (this.curChar == '0') {
/* 3160:2595 */               jjCheckNAdd(610);
/* 3161:     */             }
/* 3162:     */             break;
/* 3163:     */           case 613: 
/* 3164:2598 */             if (this.curChar == '0') {
/* 3165:2599 */               this.jjstateSet[(this.jjnewStateCnt++)] = 612;
/* 3166:     */             }
/* 3167:     */             break;
/* 3168:     */           case 615: 
/* 3169:     */           case 623: 
/* 3170:2603 */             if (this.curChar == '0') {
/* 3171:2604 */               jjCheckNAddTwoStates(620, 621);
/* 3172:     */             }
/* 3173:     */             break;
/* 3174:     */           case 616: 
/* 3175:2607 */             if (this.curChar == '8') {
/* 3176:2608 */               jjCheckNAddStates(485, 488);
/* 3177:     */             }
/* 3178:     */             break;
/* 3179:     */           case 617: 
/* 3180:2611 */             if (this.curChar == '\n') {
/* 3181:2612 */               jjCheckNAddTwoStates(600, 601);
/* 3182:     */             }
/* 3183:     */             break;
/* 3184:     */           case 618: 
/* 3185:2615 */             if (this.curChar == '\r') {
/* 3186:2616 */               this.jjstateSet[(this.jjnewStateCnt++)] = 617;
/* 3187:     */             }
/* 3188:     */             break;
/* 3189:     */           case 619: 
/* 3190:2619 */             if ((0x3600 & l) != 0L) {
/* 3191:2620 */               jjCheckNAddTwoStates(600, 601);
/* 3192:     */             }
/* 3193:     */             break;
/* 3194:     */           case 620: 
/* 3195:2623 */             if (this.curChar == '4') {
/* 3196:2624 */               jjCheckNAdd(616);
/* 3197:     */             }
/* 3198:     */             break;
/* 3199:     */           case 621: 
/* 3200:2627 */             if (this.curChar == '6') {
/* 3201:2628 */               jjCheckNAdd(616);
/* 3202:     */             }
/* 3203:     */             break;
/* 3204:     */           case 622: 
/* 3205:2631 */             if (this.curChar == '0') {
/* 3206:2632 */               jjCheckNAddStates(489, 491);
/* 3207:     */             }
/* 3208:     */             break;
/* 3209:     */           case 624: 
/* 3210:     */           case 625: 
/* 3211:2636 */             if (this.curChar == '0') {
/* 3212:2637 */               jjCheckNAdd(623);
/* 3213:     */             }
/* 3214:     */             break;
/* 3215:     */           case 626: 
/* 3216:2640 */             if (this.curChar == '0') {
/* 3217:2641 */               this.jjstateSet[(this.jjnewStateCnt++)] = 625;
/* 3218:     */             }
/* 3219:     */             break;
/* 3220:     */           case 627: 
/* 3221:2644 */             if ((0x0 & l) != 0L) {
/* 3222:2645 */               jjCheckNAddStates(139, 141);
/* 3223:     */             }
/* 3224:     */             break;
/* 3225:     */           case 632: 
/* 3226:     */           case 640: 
/* 3227:2649 */             if (this.curChar == '0') {
/* 3228:2650 */               jjCheckNAddTwoStates(637, 638);
/* 3229:     */             }
/* 3230:     */             break;
/* 3231:     */           case 634: 
/* 3232:2653 */             if ((this.curChar == '\n') && (kind > 51)) {
/* 3233:2654 */               kind = 51;
/* 3234:     */             }
/* 3235:     */             break;
/* 3236:     */           case 635: 
/* 3237:2657 */             if (this.curChar == '\r') {
/* 3238:2658 */               this.jjstateSet[(this.jjnewStateCnt++)] = 634;
/* 3239:     */             }
/* 3240:     */             break;
/* 3241:     */           case 636: 
/* 3242:2661 */             if (((0x3600 & l) != 0L) && (kind > 51)) {
/* 3243:2662 */               kind = 51;
/* 3244:     */             }
/* 3245:     */             break;
/* 3246:     */           case 637: 
/* 3247:2665 */             if (this.curChar == '5') {
/* 3248:2666 */               jjCheckNAdd(633);
/* 3249:     */             }
/* 3250:     */             break;
/* 3251:     */           case 638: 
/* 3252:2669 */             if (this.curChar == '7') {
/* 3253:2670 */               jjCheckNAdd(633);
/* 3254:     */             }
/* 3255:     */             break;
/* 3256:     */           case 639: 
/* 3257:2673 */             if (this.curChar == '0') {
/* 3258:2674 */               jjCheckNAddStates(492, 494);
/* 3259:     */             }
/* 3260:     */             break;
/* 3261:     */           case 641: 
/* 3262:     */           case 642: 
/* 3263:2678 */             if (this.curChar == '0') {
/* 3264:2679 */               jjCheckNAdd(640);
/* 3265:     */             }
/* 3266:     */             break;
/* 3267:     */           case 643: 
/* 3268:2682 */             if (this.curChar == '0') {
/* 3269:2683 */               this.jjstateSet[(this.jjnewStateCnt++)] = 642;
/* 3270:     */             }
/* 3271:     */             break;
/* 3272:     */           case 645: 
/* 3273:     */           case 653: 
/* 3274:2687 */             if (this.curChar == '0') {
/* 3275:2688 */               jjCheckNAddTwoStates(650, 651);
/* 3276:     */             }
/* 3277:     */             break;
/* 3278:     */           case 646: 
/* 3279:2691 */             if (this.curChar == '8') {
/* 3280:2692 */               jjCheckNAddStates(495, 498);
/* 3281:     */             }
/* 3282:     */             break;
/* 3283:     */           case 647: 
/* 3284:2695 */             if (this.curChar == '\n') {
/* 3285:2696 */               jjCheckNAddTwoStates(630, 631);
/* 3286:     */             }
/* 3287:     */             break;
/* 3288:     */           case 648: 
/* 3289:2699 */             if (this.curChar == '\r') {
/* 3290:2700 */               this.jjstateSet[(this.jjnewStateCnt++)] = 647;
/* 3291:     */             }
/* 3292:     */             break;
/* 3293:     */           case 649: 
/* 3294:2703 */             if ((0x3600 & l) != 0L) {
/* 3295:2704 */               jjCheckNAddTwoStates(630, 631);
/* 3296:     */             }
/* 3297:     */             break;
/* 3298:     */           case 650: 
/* 3299:2707 */             if (this.curChar == '4') {
/* 3300:2708 */               jjCheckNAdd(646);
/* 3301:     */             }
/* 3302:     */             break;
/* 3303:     */           case 651: 
/* 3304:2711 */             if (this.curChar == '6') {
/* 3305:2712 */               jjCheckNAdd(646);
/* 3306:     */             }
/* 3307:     */             break;
/* 3308:     */           case 652: 
/* 3309:2715 */             if (this.curChar == '0') {
/* 3310:2716 */               jjCheckNAddStates(499, 501);
/* 3311:     */             }
/* 3312:     */             break;
/* 3313:     */           case 654: 
/* 3314:     */           case 655: 
/* 3315:2720 */             if (this.curChar == '0') {
/* 3316:2721 */               jjCheckNAdd(653);
/* 3317:     */             }
/* 3318:     */             break;
/* 3319:     */           case 656: 
/* 3320:2724 */             if (this.curChar == '0') {
/* 3321:2725 */               this.jjstateSet[(this.jjnewStateCnt++)] = 655;
/* 3322:     */             }
/* 3323:     */             break;
/* 3324:     */           case 658: 
/* 3325:     */           case 666: 
/* 3326:2729 */             if (this.curChar == '0') {
/* 3327:2730 */               jjCheckNAddTwoStates(663, 664);
/* 3328:     */             }
/* 3329:     */             break;
/* 3330:     */           case 660: 
/* 3331:2733 */             if (this.curChar == '\n') {
/* 3332:2734 */               jjCheckNAddTwoStates(629, 644);
/* 3333:     */             }
/* 3334:     */             break;
/* 3335:     */           case 661: 
/* 3336:2737 */             if (this.curChar == '\r') {
/* 3337:2738 */               this.jjstateSet[(this.jjnewStateCnt++)] = 660;
/* 3338:     */             }
/* 3339:     */             break;
/* 3340:     */           case 662: 
/* 3341:2741 */             if ((0x3600 & l) != 0L) {
/* 3342:2742 */               jjCheckNAddTwoStates(629, 644);
/* 3343:     */             }
/* 3344:     */             break;
/* 3345:     */           case 663: 
/* 3346:2745 */             if (this.curChar == '4') {
/* 3347:2746 */               jjCheckNAdd(659);
/* 3348:     */             }
/* 3349:     */             break;
/* 3350:     */           case 664: 
/* 3351:2749 */             if (this.curChar == '6') {
/* 3352:2750 */               jjCheckNAdd(659);
/* 3353:     */             }
/* 3354:     */             break;
/* 3355:     */           case 665: 
/* 3356:2753 */             if (this.curChar == '0') {
/* 3357:2754 */               jjCheckNAddStates(502, 504);
/* 3358:     */             }
/* 3359:     */             break;
/* 3360:     */           case 667: 
/* 3361:     */           case 668: 
/* 3362:2758 */             if (this.curChar == '0') {
/* 3363:2759 */               jjCheckNAdd(666);
/* 3364:     */             }
/* 3365:     */             break;
/* 3366:     */           case 669: 
/* 3367:2762 */             if (this.curChar == '0') {
/* 3368:2763 */               this.jjstateSet[(this.jjnewStateCnt++)] = 668;
/* 3369:     */             }
/* 3370:     */             break;
/* 3371:     */           case 670: 
/* 3372:2766 */             if ((0x0 & l) != 0L) {
/* 3373:2767 */               jjCheckNAddStates(135, 138);
/* 3374:     */             }
/* 3375:     */             break;
/* 3376:     */           case 671: 
/* 3377:2770 */             if (this.curChar == '-') {
/* 3378:2771 */               jjCheckNAddTwoStates(672, 691);
/* 3379:     */             }
/* 3380:     */             break;
/* 3381:     */           case 673: 
/* 3382:2774 */             if ((0x0 & l) != 0L)
/* 3383:     */             {
/* 3384:2776 */               if (kind > 52) {
/* 3385:2777 */                 kind = 52;
/* 3386:     */               }
/* 3387:2778 */               jjCheckNAddTwoStates(673, 674);
/* 3388:     */             }
/* 3389:2779 */             break;
/* 3390:     */           case 675: 
/* 3391:2781 */             if ((0xFFFFCBFF & l) != 0L)
/* 3392:     */             {
/* 3393:2783 */               if (kind > 52) {
/* 3394:2784 */                 kind = 52;
/* 3395:     */               }
/* 3396:2785 */               jjCheckNAddTwoStates(673, 674);
/* 3397:     */             }
/* 3398:2786 */             break;
/* 3399:     */           case 676: 
/* 3400:2788 */             if ((0x0 & l) != 0L)
/* 3401:     */             {
/* 3402:2790 */               if (kind > 52) {
/* 3403:2791 */                 kind = 52;
/* 3404:     */               }
/* 3405:2792 */               jjCheckNAddStates(505, 513);
/* 3406:     */             }
/* 3407:2793 */             break;
/* 3408:     */           case 677: 
/* 3409:2795 */             if ((0x0 & l) != 0L)
/* 3410:     */             {
/* 3411:2797 */               if (kind > 52) {
/* 3412:2798 */                 kind = 52;
/* 3413:     */               }
/* 3414:2799 */               jjCheckNAddStates(514, 517);
/* 3415:     */             }
/* 3416:2800 */             break;
/* 3417:     */           case 678: 
/* 3418:2802 */             if (this.curChar == '\n')
/* 3419:     */             {
/* 3420:2804 */               if (kind > 52) {
/* 3421:2805 */                 kind = 52;
/* 3422:     */               }
/* 3423:2806 */               jjCheckNAddTwoStates(673, 674);
/* 3424:     */             }
/* 3425:2807 */             break;
/* 3426:     */           case 679: 
/* 3427:     */           case 694: 
/* 3428:2810 */             if (this.curChar == '\r') {
/* 3429:2811 */               jjCheckNAdd(678);
/* 3430:     */             }
/* 3431:     */             break;
/* 3432:     */           case 680: 
/* 3433:2814 */             if ((0x3600 & l) != 0L)
/* 3434:     */             {
/* 3435:2816 */               if (kind > 52) {
/* 3436:2817 */                 kind = 52;
/* 3437:     */               }
/* 3438:2818 */               jjCheckNAddTwoStates(673, 674);
/* 3439:     */             }
/* 3440:2819 */             break;
/* 3441:     */           case 681: 
/* 3442:     */           case 683: 
/* 3443:     */           case 686: 
/* 3444:     */           case 690: 
/* 3445:2824 */             if ((0x0 & l) != 0L) {
/* 3446:2825 */               jjCheckNAdd(677);
/* 3447:     */             }
/* 3448:     */             break;
/* 3449:     */           case 682: 
/* 3450:2828 */             if ((0x0 & l) != 0L) {
/* 3451:2829 */               this.jjstateSet[(this.jjnewStateCnt++)] = 683;
/* 3452:     */             }
/* 3453:     */             break;
/* 3454:     */           case 684: 
/* 3455:2832 */             if ((0x0 & l) != 0L) {
/* 3456:2833 */               this.jjstateSet[(this.jjnewStateCnt++)] = 685;
/* 3457:     */             }
/* 3458:     */             break;
/* 3459:     */           case 685: 
/* 3460:2836 */             if ((0x0 & l) != 0L) {
/* 3461:2837 */               this.jjstateSet[(this.jjnewStateCnt++)] = 686;
/* 3462:     */             }
/* 3463:     */             break;
/* 3464:     */           case 687: 
/* 3465:2840 */             if ((0x0 & l) != 0L) {
/* 3466:2841 */               this.jjstateSet[(this.jjnewStateCnt++)] = 688;
/* 3467:     */             }
/* 3468:     */             break;
/* 3469:     */           case 688: 
/* 3470:2844 */             if ((0x0 & l) != 0L) {
/* 3471:2845 */               this.jjstateSet[(this.jjnewStateCnt++)] = 689;
/* 3472:     */             }
/* 3473:     */             break;
/* 3474:     */           case 689: 
/* 3475:2848 */             if ((0x0 & l) != 0L) {
/* 3476:2849 */               this.jjstateSet[(this.jjnewStateCnt++)] = 690;
/* 3477:     */             }
/* 3478:     */             break;
/* 3479:     */           case 692: 
/* 3480:2852 */             if ((0x0 & l) != 0L)
/* 3481:     */             {
/* 3482:2854 */               if (kind > 52) {
/* 3483:2855 */                 kind = 52;
/* 3484:     */               }
/* 3485:2856 */               jjCheckNAddStates(518, 526);
/* 3486:     */             }
/* 3487:2857 */             break;
/* 3488:     */           case 693: 
/* 3489:2859 */             if ((0x0 & l) != 0L)
/* 3490:     */             {
/* 3491:2861 */               if (kind > 52) {
/* 3492:2862 */                 kind = 52;
/* 3493:     */               }
/* 3494:2863 */               jjCheckNAddStates(527, 530);
/* 3495:     */             }
/* 3496:2864 */             break;
/* 3497:     */           case 695: 
/* 3498:     */           case 697: 
/* 3499:     */           case 700: 
/* 3500:     */           case 704: 
/* 3501:2869 */             if ((0x0 & l) != 0L) {
/* 3502:2870 */               jjCheckNAdd(693);
/* 3503:     */             }
/* 3504:     */             break;
/* 3505:     */           case 696: 
/* 3506:2873 */             if ((0x0 & l) != 0L) {
/* 3507:2874 */               this.jjstateSet[(this.jjnewStateCnt++)] = 697;
/* 3508:     */             }
/* 3509:     */             break;
/* 3510:     */           case 698: 
/* 3511:2877 */             if ((0x0 & l) != 0L) {
/* 3512:2878 */               this.jjstateSet[(this.jjnewStateCnt++)] = 699;
/* 3513:     */             }
/* 3514:     */             break;
/* 3515:     */           case 699: 
/* 3516:2881 */             if ((0x0 & l) != 0L) {
/* 3517:2882 */               this.jjstateSet[(this.jjnewStateCnt++)] = 700;
/* 3518:     */             }
/* 3519:     */             break;
/* 3520:     */           case 701: 
/* 3521:2885 */             if ((0x0 & l) != 0L) {
/* 3522:2886 */               this.jjstateSet[(this.jjnewStateCnt++)] = 702;
/* 3523:     */             }
/* 3524:     */             break;
/* 3525:     */           case 702: 
/* 3526:2889 */             if ((0x0 & l) != 0L) {
/* 3527:2890 */               this.jjstateSet[(this.jjnewStateCnt++)] = 703;
/* 3528:     */             }
/* 3529:     */             break;
/* 3530:     */           case 703: 
/* 3531:2893 */             if ((0x0 & l) != 0L) {
/* 3532:2894 */               this.jjstateSet[(this.jjnewStateCnt++)] = 704;
/* 3533:     */             }
/* 3534:     */             break;
/* 3535:     */           case 705: 
/* 3536:2897 */             if ((0x0 & l) != 0L) {
/* 3537:2898 */               jjCheckNAddTwoStates(705, 706);
/* 3538:     */             }
/* 3539:     */             break;
/* 3540:     */           case 706: 
/* 3541:2901 */             if ((this.curChar == '%') && (kind > 53)) {
/* 3542:2902 */               kind = 53;
/* 3543:     */             }
/* 3544:     */             break;
/* 3545:     */           case 707: 
/* 3546:2905 */             if ((0x0 & l) != 0L)
/* 3547:     */             {
/* 3548:2907 */               if (kind > 54) {
/* 3549:2908 */                 kind = 54;
/* 3550:     */               }
/* 3551:2909 */               jjCheckNAdd(707);
/* 3552:     */             }
/* 3553:2910 */             break;
/* 3554:     */           case 708: 
/* 3555:2912 */             if ((0x0 & l) != 0L)
/* 3556:     */             {
/* 3557:2914 */               if (kind > 58) {
/* 3558:2915 */                 kind = 58;
/* 3559:     */               }
/* 3560:2916 */               jjCheckNAdd(708);
/* 3561:     */             }
/* 3562:2917 */             break;
/* 3563:     */           case 709: 
/* 3564:2919 */             if (this.curChar == '-') {
/* 3565:2920 */               jjAddStates(103, 106);
/* 3566:     */             }
/* 3567:     */             break;
/* 3568:     */           case 711: 
/* 3569:2923 */             if ((0x0 & l) != 0L) {
/* 3570:2924 */               jjCheckNAddStates(132, 134);
/* 3571:     */             }
/* 3572:     */             break;
/* 3573:     */           case 712: 
/* 3574:2927 */             if ((this.curChar == '(') && (kind > 55)) {
/* 3575:2928 */               kind = 55;
/* 3576:     */             }
/* 3577:     */             break;
/* 3578:     */           case 714: 
/* 3579:2931 */             if ((0xFFFFCBFF & l) != 0L) {
/* 3580:2932 */               jjCheckNAddStates(132, 134);
/* 3581:     */             }
/* 3582:     */             break;
/* 3583:     */           case 715: 
/* 3584:2935 */             if ((0x0 & l) != 0L) {
/* 3585:2936 */               jjCheckNAddStates(531, 540);
/* 3586:     */             }
/* 3587:     */             break;
/* 3588:     */           case 716: 
/* 3589:2939 */             if ((0x0 & l) != 0L) {
/* 3590:2940 */               jjCheckNAddStates(541, 545);
/* 3591:     */             }
/* 3592:     */             break;
/* 3593:     */           case 717: 
/* 3594:2943 */             if (this.curChar == '\n') {
/* 3595:2944 */               jjCheckNAddStates(132, 134);
/* 3596:     */             }
/* 3597:     */             break;
/* 3598:     */           case 718: 
/* 3599:     */           case 766: 
/* 3600:2948 */             if (this.curChar == '\r') {
/* 3601:2949 */               jjCheckNAdd(717);
/* 3602:     */             }
/* 3603:     */             break;
/* 3604:     */           case 719: 
/* 3605:2952 */             if ((0x3600 & l) != 0L) {
/* 3606:2953 */               jjCheckNAddStates(132, 134);
/* 3607:     */             }
/* 3608:     */             break;
/* 3609:     */           case 720: 
/* 3610:     */           case 722: 
/* 3611:     */           case 725: 
/* 3612:     */           case 729: 
/* 3613:2959 */             if ((0x0 & l) != 0L) {
/* 3614:2960 */               jjCheckNAdd(716);
/* 3615:     */             }
/* 3616:     */             break;
/* 3617:     */           case 721: 
/* 3618:2963 */             if ((0x0 & l) != 0L) {
/* 3619:2964 */               this.jjstateSet[(this.jjnewStateCnt++)] = 722;
/* 3620:     */             }
/* 3621:     */             break;
/* 3622:     */           case 723: 
/* 3623:2967 */             if ((0x0 & l) != 0L) {
/* 3624:2968 */               this.jjstateSet[(this.jjnewStateCnt++)] = 724;
/* 3625:     */             }
/* 3626:     */             break;
/* 3627:     */           case 724: 
/* 3628:2971 */             if ((0x0 & l) != 0L) {
/* 3629:2972 */               this.jjstateSet[(this.jjnewStateCnt++)] = 725;
/* 3630:     */             }
/* 3631:     */             break;
/* 3632:     */           case 726: 
/* 3633:2975 */             if ((0x0 & l) != 0L) {
/* 3634:2976 */               this.jjstateSet[(this.jjnewStateCnt++)] = 727;
/* 3635:     */             }
/* 3636:     */             break;
/* 3637:     */           case 727: 
/* 3638:2979 */             if ((0x0 & l) != 0L) {
/* 3639:2980 */               this.jjstateSet[(this.jjnewStateCnt++)] = 728;
/* 3640:     */             }
/* 3641:     */             break;
/* 3642:     */           case 728: 
/* 3643:2983 */             if ((0x0 & l) != 0L) {
/* 3644:2984 */               this.jjstateSet[(this.jjnewStateCnt++)] = 729;
/* 3645:     */             }
/* 3646:     */             break;
/* 3647:     */           case 731: 
/* 3648:2987 */             if ((0x0 & l) != 0L)
/* 3649:     */             {
/* 3650:2989 */               if (kind > 56) {
/* 3651:2990 */                 kind = 56;
/* 3652:     */               }
/* 3653:2991 */               jjCheckNAddTwoStates(731, 732);
/* 3654:     */             }
/* 3655:2992 */             break;
/* 3656:     */           case 733: 
/* 3657:2994 */             if ((0xFFFFCBFF & l) != 0L)
/* 3658:     */             {
/* 3659:2996 */               if (kind > 56) {
/* 3660:2997 */                 kind = 56;
/* 3661:     */               }
/* 3662:2998 */               jjCheckNAddTwoStates(731, 732);
/* 3663:     */             }
/* 3664:2999 */             break;
/* 3665:     */           case 734: 
/* 3666:3001 */             if ((0x0 & l) != 0L)
/* 3667:     */             {
/* 3668:3003 */               if (kind > 56) {
/* 3669:3004 */                 kind = 56;
/* 3670:     */               }
/* 3671:3005 */               jjCheckNAddStates(546, 554);
/* 3672:     */             }
/* 3673:3006 */             break;
/* 3674:     */           case 735: 
/* 3675:3008 */             if ((0x0 & l) != 0L)
/* 3676:     */             {
/* 3677:3010 */               if (kind > 56) {
/* 3678:3011 */                 kind = 56;
/* 3679:     */               }
/* 3680:3012 */               jjCheckNAddStates(555, 558);
/* 3681:     */             }
/* 3682:3013 */             break;
/* 3683:     */           case 736: 
/* 3684:3015 */             if (this.curChar == '\n')
/* 3685:     */             {
/* 3686:3017 */               if (kind > 56) {
/* 3687:3018 */                 kind = 56;
/* 3688:     */               }
/* 3689:3019 */               jjCheckNAddTwoStates(731, 732);
/* 3690:     */             }
/* 3691:3020 */             break;
/* 3692:     */           case 737: 
/* 3693:     */           case 752: 
/* 3694:3023 */             if (this.curChar == '\r') {
/* 3695:3024 */               jjCheckNAdd(736);
/* 3696:     */             }
/* 3697:     */             break;
/* 3698:     */           case 738: 
/* 3699:3027 */             if ((0x3600 & l) != 0L)
/* 3700:     */             {
/* 3701:3029 */               if (kind > 56) {
/* 3702:3030 */                 kind = 56;
/* 3703:     */               }
/* 3704:3031 */               jjCheckNAddTwoStates(731, 732);
/* 3705:     */             }
/* 3706:3032 */             break;
/* 3707:     */           case 739: 
/* 3708:     */           case 741: 
/* 3709:     */           case 744: 
/* 3710:     */           case 748: 
/* 3711:3037 */             if ((0x0 & l) != 0L) {
/* 3712:3038 */               jjCheckNAdd(735);
/* 3713:     */             }
/* 3714:     */             break;
/* 3715:     */           case 740: 
/* 3716:3041 */             if ((0x0 & l) != 0L) {
/* 3717:3042 */               this.jjstateSet[(this.jjnewStateCnt++)] = 741;
/* 3718:     */             }
/* 3719:     */             break;
/* 3720:     */           case 742: 
/* 3721:3045 */             if ((0x0 & l) != 0L) {
/* 3722:3046 */               this.jjstateSet[(this.jjnewStateCnt++)] = 743;
/* 3723:     */             }
/* 3724:     */             break;
/* 3725:     */           case 743: 
/* 3726:3049 */             if ((0x0 & l) != 0L) {
/* 3727:3050 */               this.jjstateSet[(this.jjnewStateCnt++)] = 744;
/* 3728:     */             }
/* 3729:     */             break;
/* 3730:     */           case 745: 
/* 3731:3053 */             if ((0x0 & l) != 0L) {
/* 3732:3054 */               this.jjstateSet[(this.jjnewStateCnt++)] = 746;
/* 3733:     */             }
/* 3734:     */             break;
/* 3735:     */           case 746: 
/* 3736:3057 */             if ((0x0 & l) != 0L) {
/* 3737:3058 */               this.jjstateSet[(this.jjnewStateCnt++)] = 747;
/* 3738:     */             }
/* 3739:     */             break;
/* 3740:     */           case 747: 
/* 3741:3061 */             if ((0x0 & l) != 0L) {
/* 3742:3062 */               this.jjstateSet[(this.jjnewStateCnt++)] = 748;
/* 3743:     */             }
/* 3744:     */             break;
/* 3745:     */           case 750: 
/* 3746:3065 */             if ((0x0 & l) != 0L)
/* 3747:     */             {
/* 3748:3067 */               if (kind > 56) {
/* 3749:3068 */                 kind = 56;
/* 3750:     */               }
/* 3751:3069 */               jjCheckNAddStates(559, 567);
/* 3752:     */             }
/* 3753:3070 */             break;
/* 3754:     */           case 751: 
/* 3755:3072 */             if ((0x0 & l) != 0L)
/* 3756:     */             {
/* 3757:3074 */               if (kind > 56) {
/* 3758:3075 */                 kind = 56;
/* 3759:     */               }
/* 3760:3076 */               jjCheckNAddStates(568, 571);
/* 3761:     */             }
/* 3762:3077 */             break;
/* 3763:     */           case 753: 
/* 3764:     */           case 755: 
/* 3765:     */           case 758: 
/* 3766:     */           case 762: 
/* 3767:3082 */             if ((0x0 & l) != 0L) {
/* 3768:3083 */               jjCheckNAdd(751);
/* 3769:     */             }
/* 3770:     */             break;
/* 3771:     */           case 754: 
/* 3772:3086 */             if ((0x0 & l) != 0L) {
/* 3773:3087 */               this.jjstateSet[(this.jjnewStateCnt++)] = 755;
/* 3774:     */             }
/* 3775:     */             break;
/* 3776:     */           case 756: 
/* 3777:3090 */             if ((0x0 & l) != 0L) {
/* 3778:3091 */               this.jjstateSet[(this.jjnewStateCnt++)] = 757;
/* 3779:     */             }
/* 3780:     */             break;
/* 3781:     */           case 757: 
/* 3782:3094 */             if ((0x0 & l) != 0L) {
/* 3783:3095 */               this.jjstateSet[(this.jjnewStateCnt++)] = 758;
/* 3784:     */             }
/* 3785:     */             break;
/* 3786:     */           case 759: 
/* 3787:3098 */             if ((0x0 & l) != 0L) {
/* 3788:3099 */               this.jjstateSet[(this.jjnewStateCnt++)] = 760;
/* 3789:     */             }
/* 3790:     */             break;
/* 3791:     */           case 760: 
/* 3792:3102 */             if ((0x0 & l) != 0L) {
/* 3793:3103 */               this.jjstateSet[(this.jjnewStateCnt++)] = 761;
/* 3794:     */             }
/* 3795:     */             break;
/* 3796:     */           case 761: 
/* 3797:3106 */             if ((0x0 & l) != 0L) {
/* 3798:3107 */               this.jjstateSet[(this.jjnewStateCnt++)] = 762;
/* 3799:     */             }
/* 3800:     */             break;
/* 3801:     */           case 764: 
/* 3802:3110 */             if ((0x0 & l) != 0L) {
/* 3803:3111 */               jjCheckNAddStates(572, 581);
/* 3804:     */             }
/* 3805:     */             break;
/* 3806:     */           case 765: 
/* 3807:3114 */             if ((0x0 & l) != 0L) {
/* 3808:3115 */               jjCheckNAddStates(582, 586);
/* 3809:     */             }
/* 3810:     */             break;
/* 3811:     */           case 767: 
/* 3812:     */           case 769: 
/* 3813:     */           case 772: 
/* 3814:     */           case 776: 
/* 3815:3121 */             if ((0x0 & l) != 0L) {
/* 3816:3122 */               jjCheckNAdd(765);
/* 3817:     */             }
/* 3818:     */             break;
/* 3819:     */           case 768: 
/* 3820:3125 */             if ((0x0 & l) != 0L) {
/* 3821:3126 */               this.jjstateSet[(this.jjnewStateCnt++)] = 769;
/* 3822:     */             }
/* 3823:     */             break;
/* 3824:     */           case 770: 
/* 3825:3129 */             if ((0x0 & l) != 0L) {
/* 3826:3130 */               this.jjstateSet[(this.jjnewStateCnt++)] = 771;
/* 3827:     */             }
/* 3828:     */             break;
/* 3829:     */           case 771: 
/* 3830:3133 */             if ((0x0 & l) != 0L) {
/* 3831:3134 */               this.jjstateSet[(this.jjnewStateCnt++)] = 772;
/* 3832:     */             }
/* 3833:     */             break;
/* 3834:     */           case 773: 
/* 3835:3137 */             if ((0x0 & l) != 0L) {
/* 3836:3138 */               this.jjstateSet[(this.jjnewStateCnt++)] = 774;
/* 3837:     */             }
/* 3838:     */             break;
/* 3839:     */           case 774: 
/* 3840:3141 */             if ((0x0 & l) != 0L) {
/* 3841:3142 */               this.jjstateSet[(this.jjnewStateCnt++)] = 775;
/* 3842:     */             }
/* 3843:     */             break;
/* 3844:     */           case 775: 
/* 3845:3145 */             if ((0x0 & l) != 0L) {
/* 3846:3146 */               this.jjstateSet[(this.jjnewStateCnt++)] = 776;
/* 3847:     */             }
/* 3848:     */             break;
/* 3849:     */           case 778: 
/* 3850:3149 */             if ((0x0 & l) != 0L)
/* 3851:     */             {
/* 3852:3151 */               if (kind > 54) {
/* 3853:3152 */                 kind = 54;
/* 3854:     */               }
/* 3855:3153 */               jjCheckNAddStates(2, 92);
/* 3856:     */             }
/* 3857:3154 */             break;
/* 3858:     */           case 779: 
/* 3859:3156 */             if ((0x0 & l) != 0L) {
/* 3860:3157 */               jjCheckNAddStates(587, 589);
/* 3861:     */             }
/* 3862:     */             break;
/* 3863:     */           case 780: 
/* 3864:3160 */             if ((0x0 & l) != 0L) {
/* 3865:3161 */               jjCheckNAddTwoStates(780, 781);
/* 3866:     */             }
/* 3867:     */             break;
/* 3868:     */           case 781: 
/* 3869:3164 */             if (this.curChar == '.') {
/* 3870:3165 */               jjCheckNAdd(179);
/* 3871:     */             }
/* 3872:     */             break;
/* 3873:     */           case 782: 
/* 3874:3168 */             if ((0x0 & l) != 0L) {
/* 3875:3169 */               jjCheckNAddStates(590, 592);
/* 3876:     */             }
/* 3877:     */             break;
/* 3878:     */           case 783: 
/* 3879:3172 */             if ((0x0 & l) != 0L) {
/* 3880:3173 */               jjCheckNAddTwoStates(783, 784);
/* 3881:     */             }
/* 3882:     */             break;
/* 3883:     */           case 784: 
/* 3884:3176 */             if (this.curChar == '.') {
/* 3885:3177 */               jjCheckNAdd(208);
/* 3886:     */             }
/* 3887:     */             break;
/* 3888:     */           case 785: 
/* 3889:3180 */             if ((0x0 & l) != 0L) {
/* 3890:3181 */               jjCheckNAddStates(593, 595);
/* 3891:     */             }
/* 3892:     */             break;
/* 3893:     */           case 786: 
/* 3894:3184 */             if ((0x0 & l) != 0L) {
/* 3895:3185 */               jjCheckNAddTwoStates(786, 787);
/* 3896:     */             }
/* 3897:     */             break;
/* 3898:     */           case 787: 
/* 3899:3188 */             if (this.curChar == '.') {
/* 3900:3189 */               jjCheckNAdd(237);
/* 3901:     */             }
/* 3902:     */             break;
/* 3903:     */           case 788: 
/* 3904:3192 */             if ((0x0 & l) != 0L) {
/* 3905:3193 */               jjCheckNAddStates(596, 598);
/* 3906:     */             }
/* 3907:     */             break;
/* 3908:     */           case 789: 
/* 3909:3196 */             if ((0x0 & l) != 0L) {
/* 3910:3197 */               jjCheckNAddTwoStates(789, 790);
/* 3911:     */             }
/* 3912:     */             break;
/* 3913:     */           case 790: 
/* 3914:3200 */             if (this.curChar == '.') {
/* 3915:3201 */               jjCheckNAdd(266);
/* 3916:     */             }
/* 3917:     */             break;
/* 3918:     */           case 791: 
/* 3919:3204 */             if ((0x0 & l) != 0L) {
/* 3920:3205 */               jjCheckNAddStates(599, 601);
/* 3921:     */             }
/* 3922:     */             break;
/* 3923:     */           case 792: 
/* 3924:3208 */             if ((0x0 & l) != 0L) {
/* 3925:3209 */               jjCheckNAddTwoStates(792, 793);
/* 3926:     */             }
/* 3927:     */             break;
/* 3928:     */           case 793: 
/* 3929:3212 */             if (this.curChar == '.') {
/* 3930:3213 */               jjCheckNAdd(295);
/* 3931:     */             }
/* 3932:     */             break;
/* 3933:     */           case 794: 
/* 3934:3216 */             if ((0x0 & l) != 0L) {
/* 3935:3217 */               jjCheckNAddStates(602, 604);
/* 3936:     */             }
/* 3937:     */             break;
/* 3938:     */           case 795: 
/* 3939:3220 */             if ((0x0 & l) != 0L) {
/* 3940:3221 */               jjCheckNAddTwoStates(795, 796);
/* 3941:     */             }
/* 3942:     */             break;
/* 3943:     */           case 796: 
/* 3944:3224 */             if (this.curChar == '.') {
/* 3945:3225 */               jjCheckNAdd(324);
/* 3946:     */             }
/* 3947:     */             break;
/* 3948:     */           case 797: 
/* 3949:3228 */             if ((0x0 & l) != 0L) {
/* 3950:3229 */               jjCheckNAddStates(605, 607);
/* 3951:     */             }
/* 3952:     */             break;
/* 3953:     */           case 798: 
/* 3954:3232 */             if ((0x0 & l) != 0L) {
/* 3955:3233 */               jjCheckNAddTwoStates(798, 799);
/* 3956:     */             }
/* 3957:     */             break;
/* 3958:     */           case 799: 
/* 3959:3236 */             if (this.curChar == '.') {
/* 3960:3237 */               jjCheckNAdd(353);
/* 3961:     */             }
/* 3962:     */             break;
/* 3963:     */           case 800: 
/* 3964:3240 */             if ((0x0 & l) != 0L) {
/* 3965:3241 */               jjCheckNAddStates(608, 610);
/* 3966:     */             }
/* 3967:     */             break;
/* 3968:     */           case 801: 
/* 3969:3244 */             if ((0x0 & l) != 0L) {
/* 3970:3245 */               jjCheckNAddTwoStates(801, 802);
/* 3971:     */             }
/* 3972:     */             break;
/* 3973:     */           case 802: 
/* 3974:3248 */             if (this.curChar == '.') {
/* 3975:3249 */               jjCheckNAdd(382);
/* 3976:     */             }
/* 3977:     */             break;
/* 3978:     */           case 803: 
/* 3979:3252 */             if ((0x0 & l) != 0L) {
/* 3980:3253 */               jjCheckNAddStates(611, 613);
/* 3981:     */             }
/* 3982:     */             break;
/* 3983:     */           case 804: 
/* 3984:3256 */             if ((0x0 & l) != 0L) {
/* 3985:3257 */               jjCheckNAddTwoStates(804, 805);
/* 3986:     */             }
/* 3987:     */             break;
/* 3988:     */           case 805: 
/* 3989:3260 */             if (this.curChar == '.') {
/* 3990:3261 */               jjCheckNAdd(411);
/* 3991:     */             }
/* 3992:     */             break;
/* 3993:     */           case 806: 
/* 3994:3264 */             if ((0x0 & l) != 0L) {
/* 3995:3265 */               jjCheckNAddStates(614, 616);
/* 3996:     */             }
/* 3997:     */             break;
/* 3998:     */           case 807: 
/* 3999:3268 */             if ((0x0 & l) != 0L) {
/* 4000:3269 */               jjCheckNAddTwoStates(807, 808);
/* 4001:     */             }
/* 4002:     */             break;
/* 4003:     */           case 808: 
/* 4004:3272 */             if (this.curChar == '.') {
/* 4005:3273 */               jjCheckNAdd(454);
/* 4006:     */             }
/* 4007:     */             break;
/* 4008:     */           case 809: 
/* 4009:3276 */             if ((0x0 & l) != 0L) {
/* 4010:3277 */               jjCheckNAddStates(617, 619);
/* 4011:     */             }
/* 4012:     */             break;
/* 4013:     */           case 810: 
/* 4014:3280 */             if ((0x0 & l) != 0L) {
/* 4015:3281 */               jjCheckNAddTwoStates(810, 811);
/* 4016:     */             }
/* 4017:     */             break;
/* 4018:     */           case 811: 
/* 4019:3284 */             if (this.curChar == '.') {
/* 4020:3285 */               jjCheckNAdd(497);
/* 4021:     */             }
/* 4022:     */             break;
/* 4023:     */           case 812: 
/* 4024:3288 */             if ((0x0 & l) != 0L) {
/* 4025:3289 */               jjCheckNAddStates(620, 622);
/* 4026:     */             }
/* 4027:     */             break;
/* 4028:     */           case 813: 
/* 4029:3292 */             if ((0x0 & l) != 0L) {
/* 4030:3293 */               jjCheckNAddTwoStates(813, 814);
/* 4031:     */             }
/* 4032:     */             break;
/* 4033:     */           case 814: 
/* 4034:3296 */             if (this.curChar == '.') {
/* 4035:3297 */               jjCheckNAdd(554);
/* 4036:     */             }
/* 4037:     */             break;
/* 4038:     */           case 815: 
/* 4039:3300 */             if ((0x0 & l) != 0L) {
/* 4040:3301 */               jjCheckNAddStates(623, 625);
/* 4041:     */             }
/* 4042:     */             break;
/* 4043:     */           case 816: 
/* 4044:3304 */             if ((0x0 & l) != 0L) {
/* 4045:3305 */               jjCheckNAddTwoStates(816, 817);
/* 4046:     */             }
/* 4047:     */             break;
/* 4048:     */           case 817: 
/* 4049:3308 */             if (this.curChar == '.') {
/* 4050:3309 */               jjCheckNAdd(583);
/* 4051:     */             }
/* 4052:     */             break;
/* 4053:     */           case 818: 
/* 4054:3312 */             if ((0x0 & l) != 0L) {
/* 4055:3313 */               jjCheckNAddStates(626, 628);
/* 4056:     */             }
/* 4057:     */             break;
/* 4058:     */           case 819: 
/* 4059:3316 */             if ((0x0 & l) != 0L) {
/* 4060:3317 */               jjCheckNAddTwoStates(819, 820);
/* 4061:     */             }
/* 4062:     */             break;
/* 4063:     */           case 820: 
/* 4064:3320 */             if (this.curChar == '.') {
/* 4065:3321 */               jjCheckNAdd(598);
/* 4066:     */             }
/* 4067:     */             break;
/* 4068:     */           case 821: 
/* 4069:3324 */             if ((0x0 & l) != 0L) {
/* 4070:3325 */               jjCheckNAddStates(629, 631);
/* 4071:     */             }
/* 4072:     */             break;
/* 4073:     */           case 822: 
/* 4074:3328 */             if ((0x0 & l) != 0L) {
/* 4075:3329 */               jjCheckNAddTwoStates(822, 823);
/* 4076:     */             }
/* 4077:     */             break;
/* 4078:     */           case 823: 
/* 4079:3332 */             if (this.curChar == '.') {
/* 4080:3333 */               jjCheckNAdd(627);
/* 4081:     */             }
/* 4082:     */             break;
/* 4083:     */           case 824: 
/* 4084:3336 */             if ((0x0 & l) != 0L) {
/* 4085:3337 */               jjCheckNAddStates(632, 635);
/* 4086:     */             }
/* 4087:     */             break;
/* 4088:     */           case 825: 
/* 4089:3340 */             if ((0x0 & l) != 0L) {
/* 4090:3341 */               jjCheckNAddTwoStates(825, 826);
/* 4091:     */             }
/* 4092:     */             break;
/* 4093:     */           case 826: 
/* 4094:3344 */             if (this.curChar == '.') {
/* 4095:3345 */               jjCheckNAdd(670);
/* 4096:     */             }
/* 4097:     */             break;
/* 4098:     */           case 827: 
/* 4099:3348 */             if ((0x0 & l) != 0L) {
/* 4100:3349 */               jjCheckNAddTwoStates(827, 706);
/* 4101:     */             }
/* 4102:     */             break;
/* 4103:     */           case 828: 
/* 4104:3352 */             if ((0x0 & l) != 0L) {
/* 4105:3353 */               jjCheckNAddTwoStates(828, 829);
/* 4106:     */             }
/* 4107:     */             break;
/* 4108:     */           case 829: 
/* 4109:3356 */             if (this.curChar == '.') {
/* 4110:3357 */               jjCheckNAdd(705);
/* 4111:     */             }
/* 4112:     */             break;
/* 4113:     */           case 830: 
/* 4114:3360 */             if ((0x0 & l) != 0L)
/* 4115:     */             {
/* 4116:3362 */               if (kind > 54) {
/* 4117:3363 */                 kind = 54;
/* 4118:     */               }
/* 4119:3364 */               jjCheckNAdd(830);
/* 4120:     */             }
/* 4121:3365 */             break;
/* 4122:     */           case 831: 
/* 4123:3367 */             if ((0x0 & l) != 0L) {
/* 4124:3368 */               jjCheckNAddTwoStates(831, 832);
/* 4125:     */             }
/* 4126:     */             break;
/* 4127:     */           case 832: 
/* 4128:3371 */             if (this.curChar == '.') {
/* 4129:3372 */               jjCheckNAdd(707);
/* 4130:     */             }
/* 4131:     */             break;
/* 4132:     */           case 833: 
/* 4133:3375 */             if ((0x0 & l) != 0L)
/* 4134:     */             {
/* 4135:3377 */               if (kind > 58) {
/* 4136:3378 */                 kind = 58;
/* 4137:     */               }
/* 4138:3379 */               jjCheckNAdd(833);
/* 4139:     */             }
/* 4140:3380 */             break;
/* 4141:     */           case 834: 
/* 4142:3382 */             if ((0x0 & l) != 0L) {
/* 4143:3383 */               jjCheckNAddTwoStates(834, 835);
/* 4144:     */             }
/* 4145:     */             break;
/* 4146:     */           case 835: 
/* 4147:3386 */             if (this.curChar == '.') {
/* 4148:3387 */               jjCheckNAdd(708);
/* 4149:     */             }
/* 4150:     */             break;
/* 4151:     */           }
/* 4152:3391 */         } while (i != startsAt);
/* 4153:     */       }
/* 4154:3393 */       else if (this.curChar < '')
/* 4155:     */       {
/* 4156:3395 */         long l = 1L << (this.curChar & 0x3F);
/* 4157:     */         do
/* 4158:     */         {
/* 4159:3398 */           switch (this.jjstateSet[(--i)])
/* 4160:     */           {
/* 4161:     */           case 839: 
/* 4162:3401 */             if ((0x87FFFFFE & l) != 0L)
/* 4163:     */             {
/* 4164:3403 */               if (kind > 33) {
/* 4165:3404 */                 kind = 33;
/* 4166:     */               }
/* 4167:3405 */               jjCheckNAddTwoStates(139, 140);
/* 4168:     */             }
/* 4169:3407 */             else if (this.curChar == '\\')
/* 4170:     */             {
/* 4171:3408 */               jjCheckNAddTwoStates(141, 142);
/* 4172:     */             }
/* 4173:     */             break;
/* 4174:     */           case 137: 
/* 4175:3411 */             if ((0x87FFFFFE & l) != 0L)
/* 4176:     */             {
/* 4177:3413 */               if (kind > 33) {
/* 4178:3414 */                 kind = 33;
/* 4179:     */               }
/* 4180:3415 */               jjCheckNAddTwoStates(139, 140);
/* 4181:     */             }
/* 4182:3417 */             else if (this.curChar == '\\')
/* 4183:     */             {
/* 4184:3418 */               jjCheckNAddTwoStates(141, 158);
/* 4185:     */             }
/* 4186:     */             break;
/* 4187:     */           case 4: 
/* 4188:3421 */             if ((0x87FFFFFE & l) != 0L)
/* 4189:     */             {
/* 4190:3423 */               if (kind > 56) {
/* 4191:3424 */                 kind = 56;
/* 4192:     */               }
/* 4193:3425 */               jjCheckNAddStates(636, 640);
/* 4194:     */             }
/* 4195:3427 */             else if (this.curChar == '\\')
/* 4196:     */             {
/* 4197:3428 */               jjCheckNAddStates(641, 644);
/* 4198:     */             }
/* 4199:3429 */             else if (this.curChar == '@')
/* 4200:     */             {
/* 4201:3430 */               jjAddStates(645, 647);
/* 4202:     */             }
/* 4203:3431 */             else if (this.curChar == '{')
/* 4204:     */             {
/* 4205:3433 */               if (kind > 6) {
/* 4206:3434 */                 kind = 6;
/* 4207:     */               }
/* 4208:     */             }
/* 4209:3436 */             if ((0x200000 & l) != 0L) {
/* 4210:3437 */               this.jjstateSet[(this.jjnewStateCnt++)] = 134;
/* 4211:     */             }
/* 4212:     */             break;
/* 4213:     */           case 710: 
/* 4214:3440 */             if ((0x87FFFFFE & l) != 0L)
/* 4215:     */             {
/* 4216:3442 */               if (kind > 56) {
/* 4217:3443 */                 kind = 56;
/* 4218:     */               }
/* 4219:3444 */               jjCheckNAddTwoStates(731, 732);
/* 4220:     */             }
/* 4221:3446 */             else if (this.curChar == '\\')
/* 4222:     */             {
/* 4223:3447 */               jjCheckNAddTwoStates(714, 764);
/* 4224:     */             }
/* 4225:3448 */             if ((0x87FFFFFE & l) != 0L) {
/* 4226:3449 */               jjCheckNAddStates(132, 134);
/* 4227:3450 */             } else if (this.curChar == '\\') {
/* 4228:3451 */               jjCheckNAddTwoStates(733, 750);
/* 4229:     */             }
/* 4230:     */             break;
/* 4231:     */           case 837: 
/* 4232:3454 */             if ((0x87FFFFFE & l) != 0L)
/* 4233:     */             {
/* 4234:3456 */               if (kind > 56) {
/* 4235:3457 */                 kind = 56;
/* 4236:     */               }
/* 4237:3458 */               jjCheckNAddTwoStates(731, 732);
/* 4238:     */             }
/* 4239:3460 */             else if (this.curChar == '\\')
/* 4240:     */             {
/* 4241:3461 */               jjCheckNAddTwoStates(714, 715);
/* 4242:     */             }
/* 4243:3462 */             if ((0x87FFFFFE & l) != 0L) {
/* 4244:3463 */               jjCheckNAddStates(132, 134);
/* 4245:3464 */             } else if (this.curChar == '\\') {
/* 4246:3465 */               jjCheckNAddTwoStates(733, 734);
/* 4247:     */             }
/* 4248:     */             break;
/* 4249:     */           case 0: 
/* 4250:3468 */             if ((this.curChar == '{') && (kind > 6)) {
/* 4251:3469 */               kind = 6;
/* 4252:     */             }
/* 4253:     */             break;
/* 4254:     */           case 5: 
/* 4255:3472 */             if ((0x87FFFFFE & l) != 0L)
/* 4256:     */             {
/* 4257:3474 */               if (kind > 20) {
/* 4258:3475 */                 kind = 20;
/* 4259:     */               }
/* 4260:3476 */               jjCheckNAddTwoStates(5, 6);
/* 4261:     */             }
/* 4262:3477 */             break;
/* 4263:     */           case 6: 
/* 4264:3479 */             if (this.curChar == '\\') {
/* 4265:3480 */               jjAddStates(648, 649);
/* 4266:     */             }
/* 4267:     */             break;
/* 4268:     */           case 7: 
/* 4269:3483 */             if ((0xFFFFFF81 & l) != 0L)
/* 4270:     */             {
/* 4271:3485 */               if (kind > 20) {
/* 4272:3486 */                 kind = 20;
/* 4273:     */               }
/* 4274:3487 */               jjCheckNAddTwoStates(5, 6);
/* 4275:     */             }
/* 4276:3488 */             break;
/* 4277:     */           case 8: 
/* 4278:3490 */             if ((0x7E & l) != 0L)
/* 4279:     */             {
/* 4280:3492 */               if (kind > 20) {
/* 4281:3493 */                 kind = 20;
/* 4282:     */               }
/* 4283:3494 */               jjCheckNAddStates(184, 192);
/* 4284:     */             }
/* 4285:3495 */             break;
/* 4286:     */           case 9: 
/* 4287:3497 */             if ((0x7E & l) != 0L)
/* 4288:     */             {
/* 4289:3499 */               if (kind > 20) {
/* 4290:3500 */                 kind = 20;
/* 4291:     */               }
/* 4292:3501 */               jjCheckNAddStates(193, 196);
/* 4293:     */             }
/* 4294:3502 */             break;
/* 4295:     */           case 13: 
/* 4296:     */           case 15: 
/* 4297:     */           case 18: 
/* 4298:     */           case 22: 
/* 4299:3507 */             if ((0x7E & l) != 0L) {
/* 4300:3508 */               jjCheckNAdd(9);
/* 4301:     */             }
/* 4302:     */             break;
/* 4303:     */           case 14: 
/* 4304:3511 */             if ((0x7E & l) != 0L) {
/* 4305:3512 */               this.jjstateSet[(this.jjnewStateCnt++)] = 15;
/* 4306:     */             }
/* 4307:     */             break;
/* 4308:     */           case 16: 
/* 4309:3515 */             if ((0x7E & l) != 0L) {
/* 4310:3516 */               this.jjstateSet[(this.jjnewStateCnt++)] = 17;
/* 4311:     */             }
/* 4312:     */             break;
/* 4313:     */           case 17: 
/* 4314:3519 */             if ((0x7E & l) != 0L) {
/* 4315:3520 */               this.jjstateSet[(this.jjnewStateCnt++)] = 18;
/* 4316:     */             }
/* 4317:     */             break;
/* 4318:     */           case 19: 
/* 4319:3523 */             if ((0x7E & l) != 0L) {
/* 4320:3524 */               this.jjstateSet[(this.jjnewStateCnt++)] = 20;
/* 4321:     */             }
/* 4322:     */             break;
/* 4323:     */           case 20: 
/* 4324:3527 */             if ((0x7E & l) != 0L) {
/* 4325:3528 */               this.jjstateSet[(this.jjnewStateCnt++)] = 21;
/* 4326:     */             }
/* 4327:     */             break;
/* 4328:     */           case 21: 
/* 4329:3531 */             if ((0x7E & l) != 0L) {
/* 4330:3532 */               this.jjstateSet[(this.jjnewStateCnt++)] = 22;
/* 4331:     */             }
/* 4332:     */             break;
/* 4333:     */           case 24: 
/* 4334:3535 */             jjCheckNAddStates(129, 131);
/* 4335:3536 */             break;
/* 4336:     */           case 26: 
/* 4337:3538 */             if (this.curChar == '\\') {
/* 4338:3539 */               jjAddStates(650, 653);
/* 4339:     */             }
/* 4340:     */             break;
/* 4341:     */           case 30: 
/* 4342:3542 */             if ((0xFFFFFF81 & l) != 0L) {
/* 4343:3543 */               jjCheckNAddStates(129, 131);
/* 4344:     */             }
/* 4345:     */             break;
/* 4346:     */           case 31: 
/* 4347:3546 */             if ((0x7E & l) != 0L) {
/* 4348:3547 */               jjCheckNAddStates(197, 206);
/* 4349:     */             }
/* 4350:     */             break;
/* 4351:     */           case 32: 
/* 4352:3550 */             if ((0x7E & l) != 0L) {
/* 4353:3551 */               jjCheckNAddStates(207, 211);
/* 4354:     */             }
/* 4355:     */             break;
/* 4356:     */           case 35: 
/* 4357:     */           case 37: 
/* 4358:     */           case 40: 
/* 4359:     */           case 44: 
/* 4360:3557 */             if ((0x7E & l) != 0L) {
/* 4361:3558 */               jjCheckNAdd(32);
/* 4362:     */             }
/* 4363:     */             break;
/* 4364:     */           case 36: 
/* 4365:3561 */             if ((0x7E & l) != 0L) {
/* 4366:3562 */               this.jjstateSet[(this.jjnewStateCnt++)] = 37;
/* 4367:     */             }
/* 4368:     */             break;
/* 4369:     */           case 38: 
/* 4370:3565 */             if ((0x7E & l) != 0L) {
/* 4371:3566 */               this.jjstateSet[(this.jjnewStateCnt++)] = 39;
/* 4372:     */             }
/* 4373:     */             break;
/* 4374:     */           case 39: 
/* 4375:3569 */             if ((0x7E & l) != 0L) {
/* 4376:3570 */               this.jjstateSet[(this.jjnewStateCnt++)] = 40;
/* 4377:     */             }
/* 4378:     */             break;
/* 4379:     */           case 41: 
/* 4380:3573 */             if ((0x7E & l) != 0L) {
/* 4381:3574 */               this.jjstateSet[(this.jjnewStateCnt++)] = 42;
/* 4382:     */             }
/* 4383:     */             break;
/* 4384:     */           case 42: 
/* 4385:3577 */             if ((0x7E & l) != 0L) {
/* 4386:3578 */               this.jjstateSet[(this.jjnewStateCnt++)] = 43;
/* 4387:     */             }
/* 4388:     */             break;
/* 4389:     */           case 43: 
/* 4390:3581 */             if ((0x7E & l) != 0L) {
/* 4391:3582 */               this.jjstateSet[(this.jjnewStateCnt++)] = 44;
/* 4392:     */             }
/* 4393:     */             break;
/* 4394:     */           case 46: 
/* 4395:3585 */             jjCheckNAddStates(126, 128);
/* 4396:3586 */             break;
/* 4397:     */           case 48: 
/* 4398:3588 */             if (this.curChar == '\\') {
/* 4399:3589 */               jjAddStates(654, 657);
/* 4400:     */             }
/* 4401:     */             break;
/* 4402:     */           case 52: 
/* 4403:3592 */             if ((0xFFFFFF81 & l) != 0L) {
/* 4404:3593 */               jjCheckNAddStates(126, 128);
/* 4405:     */             }
/* 4406:     */             break;
/* 4407:     */           case 53: 
/* 4408:3596 */             if ((0x7E & l) != 0L) {
/* 4409:3597 */               jjCheckNAddStates(212, 221);
/* 4410:     */             }
/* 4411:     */             break;
/* 4412:     */           case 54: 
/* 4413:3600 */             if ((0x7E & l) != 0L) {
/* 4414:3601 */               jjCheckNAddStates(222, 226);
/* 4415:     */             }
/* 4416:     */             break;
/* 4417:     */           case 57: 
/* 4418:     */           case 59: 
/* 4419:     */           case 62: 
/* 4420:     */           case 66: 
/* 4421:3607 */             if ((0x7E & l) != 0L) {
/* 4422:3608 */               jjCheckNAdd(54);
/* 4423:     */             }
/* 4424:     */             break;
/* 4425:     */           case 58: 
/* 4426:3611 */             if ((0x7E & l) != 0L) {
/* 4427:3612 */               this.jjstateSet[(this.jjnewStateCnt++)] = 59;
/* 4428:     */             }
/* 4429:     */             break;
/* 4430:     */           case 60: 
/* 4431:3615 */             if ((0x7E & l) != 0L) {
/* 4432:3616 */               this.jjstateSet[(this.jjnewStateCnt++)] = 61;
/* 4433:     */             }
/* 4434:     */             break;
/* 4435:     */           case 61: 
/* 4436:3619 */             if ((0x7E & l) != 0L) {
/* 4437:3620 */               this.jjstateSet[(this.jjnewStateCnt++)] = 62;
/* 4438:     */             }
/* 4439:     */             break;
/* 4440:     */           case 63: 
/* 4441:3623 */             if ((0x7E & l) != 0L) {
/* 4442:3624 */               this.jjstateSet[(this.jjnewStateCnt++)] = 64;
/* 4443:     */             }
/* 4444:     */             break;
/* 4445:     */           case 64: 
/* 4446:3627 */             if ((0x7E & l) != 0L) {
/* 4447:3628 */               this.jjstateSet[(this.jjnewStateCnt++)] = 65;
/* 4448:     */             }
/* 4449:     */             break;
/* 4450:     */           case 65: 
/* 4451:3631 */             if ((0x7E & l) != 0L) {
/* 4452:3632 */               this.jjstateSet[(this.jjnewStateCnt++)] = 66;
/* 4453:     */             }
/* 4454:     */             break;
/* 4455:     */           case 68: 
/* 4456:3635 */             if ((0xEFFFFFFF & l) != 0L) {
/* 4457:3636 */               jjCheckNAddStates(233, 236);
/* 4458:     */             }
/* 4459:     */             break;
/* 4460:     */           case 71: 
/* 4461:3639 */             if (this.curChar == '\\') {
/* 4462:3640 */               jjAddStates(658, 659);
/* 4463:     */             }
/* 4464:     */             break;
/* 4465:     */           case 72: 
/* 4466:3643 */             if ((0xFFFFFF81 & l) != 0L) {
/* 4467:3644 */               jjCheckNAddStates(233, 236);
/* 4468:     */             }
/* 4469:     */             break;
/* 4470:     */           case 73: 
/* 4471:3647 */             if ((0x7E & l) != 0L) {
/* 4472:3648 */               jjCheckNAddStates(237, 246);
/* 4473:     */             }
/* 4474:     */             break;
/* 4475:     */           case 74: 
/* 4476:3651 */             if ((0x7E & l) != 0L) {
/* 4477:3652 */               jjCheckNAddStates(247, 251);
/* 4478:     */             }
/* 4479:     */             break;
/* 4480:     */           case 78: 
/* 4481:     */           case 80: 
/* 4482:     */           case 83: 
/* 4483:     */           case 87: 
/* 4484:3658 */             if ((0x7E & l) != 0L) {
/* 4485:3659 */               jjCheckNAdd(74);
/* 4486:     */             }
/* 4487:     */             break;
/* 4488:     */           case 79: 
/* 4489:3662 */             if ((0x7E & l) != 0L) {
/* 4490:3663 */               this.jjstateSet[(this.jjnewStateCnt++)] = 80;
/* 4491:     */             }
/* 4492:     */             break;
/* 4493:     */           case 81: 
/* 4494:3666 */             if ((0x7E & l) != 0L) {
/* 4495:3667 */               this.jjstateSet[(this.jjnewStateCnt++)] = 82;
/* 4496:     */             }
/* 4497:     */             break;
/* 4498:     */           case 82: 
/* 4499:3670 */             if ((0x7E & l) != 0L) {
/* 4500:3671 */               this.jjstateSet[(this.jjnewStateCnt++)] = 83;
/* 4501:     */             }
/* 4502:     */             break;
/* 4503:     */           case 84: 
/* 4504:3674 */             if ((0x7E & l) != 0L) {
/* 4505:3675 */               this.jjstateSet[(this.jjnewStateCnt++)] = 85;
/* 4506:     */             }
/* 4507:     */             break;
/* 4508:     */           case 85: 
/* 4509:3678 */             if ((0x7E & l) != 0L) {
/* 4510:3679 */               this.jjstateSet[(this.jjnewStateCnt++)] = 86;
/* 4511:     */             }
/* 4512:     */             break;
/* 4513:     */           case 86: 
/* 4514:3682 */             if ((0x7E & l) != 0L) {
/* 4515:3683 */               this.jjstateSet[(this.jjnewStateCnt++)] = 87;
/* 4516:     */             }
/* 4517:     */             break;
/* 4518:     */           case 89: 
/* 4519:3686 */             jjCheckNAddStates(252, 254);
/* 4520:3687 */             break;
/* 4521:     */           case 91: 
/* 4522:3689 */             if (this.curChar == '\\') {
/* 4523:3690 */               jjAddStates(660, 663);
/* 4524:     */             }
/* 4525:     */             break;
/* 4526:     */           case 95: 
/* 4527:3693 */             if ((0xFFFFFF81 & l) != 0L) {
/* 4528:3694 */               jjCheckNAddStates(252, 254);
/* 4529:     */             }
/* 4530:     */             break;
/* 4531:     */           case 96: 
/* 4532:3697 */             if ((0x7E & l) != 0L) {
/* 4533:3698 */               jjCheckNAddStates(255, 264);
/* 4534:     */             }
/* 4535:     */             break;
/* 4536:     */           case 97: 
/* 4537:3701 */             if ((0x7E & l) != 0L) {
/* 4538:3702 */               jjCheckNAddStates(265, 269);
/* 4539:     */             }
/* 4540:     */             break;
/* 4541:     */           case 100: 
/* 4542:     */           case 102: 
/* 4543:     */           case 105: 
/* 4544:     */           case 109: 
/* 4545:3708 */             if ((0x7E & l) != 0L) {
/* 4546:3709 */               jjCheckNAdd(97);
/* 4547:     */             }
/* 4548:     */             break;
/* 4549:     */           case 101: 
/* 4550:3712 */             if ((0x7E & l) != 0L) {
/* 4551:3713 */               this.jjstateSet[(this.jjnewStateCnt++)] = 102;
/* 4552:     */             }
/* 4553:     */             break;
/* 4554:     */           case 103: 
/* 4555:3716 */             if ((0x7E & l) != 0L) {
/* 4556:3717 */               this.jjstateSet[(this.jjnewStateCnt++)] = 104;
/* 4557:     */             }
/* 4558:     */             break;
/* 4559:     */           case 104: 
/* 4560:3720 */             if ((0x7E & l) != 0L) {
/* 4561:3721 */               this.jjstateSet[(this.jjnewStateCnt++)] = 105;
/* 4562:     */             }
/* 4563:     */             break;
/* 4564:     */           case 106: 
/* 4565:3724 */             if ((0x7E & l) != 0L) {
/* 4566:3725 */               this.jjstateSet[(this.jjnewStateCnt++)] = 107;
/* 4567:     */             }
/* 4568:     */             break;
/* 4569:     */           case 107: 
/* 4570:3728 */             if ((0x7E & l) != 0L) {
/* 4571:3729 */               this.jjstateSet[(this.jjnewStateCnt++)] = 108;
/* 4572:     */             }
/* 4573:     */             break;
/* 4574:     */           case 108: 
/* 4575:3732 */             if ((0x7E & l) != 0L) {
/* 4576:3733 */               this.jjstateSet[(this.jjnewStateCnt++)] = 109;
/* 4577:     */             }
/* 4578:     */             break;
/* 4579:     */           case 111: 
/* 4580:3736 */             jjCheckNAddStates(270, 272);
/* 4581:3737 */             break;
/* 4582:     */           case 113: 
/* 4583:3739 */             if (this.curChar == '\\') {
/* 4584:3740 */               jjAddStates(664, 667);
/* 4585:     */             }
/* 4586:     */             break;
/* 4587:     */           case 117: 
/* 4588:3743 */             if ((0xFFFFFF81 & l) != 0L) {
/* 4589:3744 */               jjCheckNAddStates(270, 272);
/* 4590:     */             }
/* 4591:     */             break;
/* 4592:     */           case 118: 
/* 4593:3747 */             if ((0x7E & l) != 0L) {
/* 4594:3748 */               jjCheckNAddStates(273, 282);
/* 4595:     */             }
/* 4596:     */             break;
/* 4597:     */           case 119: 
/* 4598:3751 */             if ((0x7E & l) != 0L) {
/* 4599:3752 */               jjCheckNAddStates(283, 287);
/* 4600:     */             }
/* 4601:     */             break;
/* 4602:     */           case 122: 
/* 4603:     */           case 124: 
/* 4604:     */           case 127: 
/* 4605:     */           case 131: 
/* 4606:3758 */             if ((0x7E & l) != 0L) {
/* 4607:3759 */               jjCheckNAdd(119);
/* 4608:     */             }
/* 4609:     */             break;
/* 4610:     */           case 123: 
/* 4611:3762 */             if ((0x7E & l) != 0L) {
/* 4612:3763 */               this.jjstateSet[(this.jjnewStateCnt++)] = 124;
/* 4613:     */             }
/* 4614:     */             break;
/* 4615:     */           case 125: 
/* 4616:3766 */             if ((0x7E & l) != 0L) {
/* 4617:3767 */               this.jjstateSet[(this.jjnewStateCnt++)] = 126;
/* 4618:     */             }
/* 4619:     */             break;
/* 4620:     */           case 126: 
/* 4621:3770 */             if ((0x7E & l) != 0L) {
/* 4622:3771 */               this.jjstateSet[(this.jjnewStateCnt++)] = 127;
/* 4623:     */             }
/* 4624:     */             break;
/* 4625:     */           case 128: 
/* 4626:3774 */             if ((0x7E & l) != 0L) {
/* 4627:3775 */               this.jjstateSet[(this.jjnewStateCnt++)] = 129;
/* 4628:     */             }
/* 4629:     */             break;
/* 4630:     */           case 129: 
/* 4631:3778 */             if ((0x7E & l) != 0L) {
/* 4632:3779 */               this.jjstateSet[(this.jjnewStateCnt++)] = 130;
/* 4633:     */             }
/* 4634:     */             break;
/* 4635:     */           case 130: 
/* 4636:3782 */             if ((0x7E & l) != 0L) {
/* 4637:3783 */               this.jjstateSet[(this.jjnewStateCnt++)] = 131;
/* 4638:     */             }
/* 4639:     */             break;
/* 4640:     */           case 133: 
/* 4641:3786 */             if ((0x1000 & l) != 0L) {
/* 4642:3787 */               this.jjstateSet[(this.jjnewStateCnt++)] = 67;
/* 4643:     */             }
/* 4644:     */             break;
/* 4645:     */           case 134: 
/* 4646:3790 */             if ((0x40000 & l) != 0L) {
/* 4647:3791 */               this.jjstateSet[(this.jjnewStateCnt++)] = 133;
/* 4648:     */             }
/* 4649:     */             break;
/* 4650:     */           case 135: 
/* 4651:3794 */             if ((0x200000 & l) != 0L) {
/* 4652:3795 */               this.jjstateSet[(this.jjnewStateCnt++)] = 134;
/* 4653:     */             }
/* 4654:     */             break;
/* 4655:     */           case 136: 
/* 4656:3798 */             if (this.curChar == '@') {
/* 4657:3799 */               jjAddStates(645, 647);
/* 4658:     */             }
/* 4659:     */             break;
/* 4660:     */           case 138: 
/* 4661:3802 */             if ((0x87FFFFFE & l) != 0L)
/* 4662:     */             {
/* 4663:3804 */               if (kind > 33) {
/* 4664:3805 */                 kind = 33;
/* 4665:     */               }
/* 4666:3806 */               jjCheckNAddTwoStates(139, 140);
/* 4667:     */             }
/* 4668:3807 */             break;
/* 4669:     */           case 139: 
/* 4670:3809 */             if ((0x87FFFFFE & l) != 0L)
/* 4671:     */             {
/* 4672:3811 */               if (kind > 33) {
/* 4673:3812 */                 kind = 33;
/* 4674:     */               }
/* 4675:3813 */               jjCheckNAddTwoStates(139, 140);
/* 4676:     */             }
/* 4677:3814 */             break;
/* 4678:     */           case 140: 
/* 4679:3816 */             if (this.curChar == '\\') {
/* 4680:3817 */               jjCheckNAddTwoStates(141, 142);
/* 4681:     */             }
/* 4682:     */             break;
/* 4683:     */           case 141: 
/* 4684:3820 */             if ((0xFFFFFF81 & l) != 0L)
/* 4685:     */             {
/* 4686:3822 */               if (kind > 33) {
/* 4687:3823 */                 kind = 33;
/* 4688:     */               }
/* 4689:3824 */               jjCheckNAddTwoStates(139, 140);
/* 4690:     */             }
/* 4691:3825 */             break;
/* 4692:     */           case 142: 
/* 4693:3827 */             if ((0x7E & l) != 0L)
/* 4694:     */             {
/* 4695:3829 */               if (kind > 33) {
/* 4696:3830 */                 kind = 33;
/* 4697:     */               }
/* 4698:3831 */               jjCheckNAddStates(295, 303);
/* 4699:     */             }
/* 4700:3832 */             break;
/* 4701:     */           case 143: 
/* 4702:3834 */             if ((0x7E & l) != 0L)
/* 4703:     */             {
/* 4704:3836 */               if (kind > 33) {
/* 4705:3837 */                 kind = 33;
/* 4706:     */               }
/* 4707:3838 */               jjCheckNAddStates(304, 307);
/* 4708:     */             }
/* 4709:3839 */             break;
/* 4710:     */           case 147: 
/* 4711:     */           case 149: 
/* 4712:     */           case 152: 
/* 4713:     */           case 156: 
/* 4714:3844 */             if ((0x7E & l) != 0L) {
/* 4715:3845 */               jjCheckNAdd(143);
/* 4716:     */             }
/* 4717:     */             break;
/* 4718:     */           case 148: 
/* 4719:3848 */             if ((0x7E & l) != 0L) {
/* 4720:3849 */               this.jjstateSet[(this.jjnewStateCnt++)] = 149;
/* 4721:     */             }
/* 4722:     */             break;
/* 4723:     */           case 150: 
/* 4724:3852 */             if ((0x7E & l) != 0L) {
/* 4725:3853 */               this.jjstateSet[(this.jjnewStateCnt++)] = 151;
/* 4726:     */             }
/* 4727:     */             break;
/* 4728:     */           case 151: 
/* 4729:3856 */             if ((0x7E & l) != 0L) {
/* 4730:3857 */               this.jjstateSet[(this.jjnewStateCnt++)] = 152;
/* 4731:     */             }
/* 4732:     */             break;
/* 4733:     */           case 153: 
/* 4734:3860 */             if ((0x7E & l) != 0L) {
/* 4735:3861 */               this.jjstateSet[(this.jjnewStateCnt++)] = 154;
/* 4736:     */             }
/* 4737:     */             break;
/* 4738:     */           case 154: 
/* 4739:3864 */             if ((0x7E & l) != 0L) {
/* 4740:3865 */               this.jjstateSet[(this.jjnewStateCnt++)] = 155;
/* 4741:     */             }
/* 4742:     */             break;
/* 4743:     */           case 155: 
/* 4744:3868 */             if ((0x7E & l) != 0L) {
/* 4745:3869 */               this.jjstateSet[(this.jjnewStateCnt++)] = 156;
/* 4746:     */             }
/* 4747:     */             break;
/* 4748:     */           case 157: 
/* 4749:3872 */             if (this.curChar == '\\') {
/* 4750:3873 */               jjCheckNAddTwoStates(141, 158);
/* 4751:     */             }
/* 4752:     */             break;
/* 4753:     */           case 158: 
/* 4754:3876 */             if ((0x7E & l) != 0L)
/* 4755:     */             {
/* 4756:3878 */               if (kind > 33) {
/* 4757:3879 */                 kind = 33;
/* 4758:     */               }
/* 4759:3880 */               jjCheckNAddStates(308, 316);
/* 4760:     */             }
/* 4761:3881 */             break;
/* 4762:     */           case 159: 
/* 4763:3883 */             if ((0x7E & l) != 0L)
/* 4764:     */             {
/* 4765:3885 */               if (kind > 33) {
/* 4766:3886 */                 kind = 33;
/* 4767:     */               }
/* 4768:3887 */               jjCheckNAddStates(317, 320);
/* 4769:     */             }
/* 4770:3888 */             break;
/* 4771:     */           case 161: 
/* 4772:     */           case 163: 
/* 4773:     */           case 166: 
/* 4774:     */           case 170: 
/* 4775:3893 */             if ((0x7E & l) != 0L) {
/* 4776:3894 */               jjCheckNAdd(159);
/* 4777:     */             }
/* 4778:     */             break;
/* 4779:     */           case 162: 
/* 4780:3897 */             if ((0x7E & l) != 0L) {
/* 4781:3898 */               this.jjstateSet[(this.jjnewStateCnt++)] = 163;
/* 4782:     */             }
/* 4783:     */             break;
/* 4784:     */           case 164: 
/* 4785:3901 */             if ((0x7E & l) != 0L) {
/* 4786:3902 */               this.jjstateSet[(this.jjnewStateCnt++)] = 165;
/* 4787:     */             }
/* 4788:     */             break;
/* 4789:     */           case 165: 
/* 4790:3905 */             if ((0x7E & l) != 0L) {
/* 4791:3906 */               this.jjstateSet[(this.jjnewStateCnt++)] = 166;
/* 4792:     */             }
/* 4793:     */             break;
/* 4794:     */           case 167: 
/* 4795:3909 */             if ((0x7E & l) != 0L) {
/* 4796:3910 */               this.jjstateSet[(this.jjnewStateCnt++)] = 168;
/* 4797:     */             }
/* 4798:     */             break;
/* 4799:     */           case 168: 
/* 4800:3913 */             if ((0x7E & l) != 0L) {
/* 4801:3914 */               this.jjstateSet[(this.jjnewStateCnt++)] = 169;
/* 4802:     */             }
/* 4803:     */             break;
/* 4804:     */           case 169: 
/* 4805:3917 */             if ((0x7E & l) != 0L) {
/* 4806:3918 */               this.jjstateSet[(this.jjnewStateCnt++)] = 170;
/* 4807:     */             }
/* 4808:     */             break;
/* 4809:     */           case 180: 
/* 4810:3921 */             if ((0x20 & l) != 0L) {
/* 4811:3922 */               jjCheckNAddTwoStates(181, 182);
/* 4812:     */             }
/* 4813:     */             break;
/* 4814:     */           case 181: 
/* 4815:3925 */             if (((0x2000 & l) != 0L) && (kind > 37)) {
/* 4816:3926 */               kind = 37;
/* 4817:     */             }
/* 4818:     */             break;
/* 4819:     */           case 182: 
/* 4820:3929 */             if (this.curChar == '\\') {
/* 4821:3930 */               jjCheckNAddStates(668, 670);
/* 4822:     */             }
/* 4823:     */             break;
/* 4824:     */           case 184: 
/* 4825:3933 */             if ((0x10 & l) != 0L)
/* 4826:     */             {
/* 4827:3935 */               if (kind > 37) {
/* 4828:3936 */                 kind = 37;
/* 4829:     */               }
/* 4830:3937 */               jjAddStates(671, 672);
/* 4831:     */             }
/* 4832:3938 */             break;
/* 4833:     */           case 195: 
/* 4834:3940 */             if (this.curChar == '\\') {
/* 4835:3941 */               jjAddStates(673, 674);
/* 4836:     */             }
/* 4837:     */             break;
/* 4838:     */           case 209: 
/* 4839:3944 */             if ((0x20 & l) != 0L) {
/* 4840:3945 */               jjCheckNAddTwoStates(210, 211);
/* 4841:     */             }
/* 4842:     */             break;
/* 4843:     */           case 210: 
/* 4844:3948 */             if (((0x1000000 & l) != 0L) && (kind > 38)) {
/* 4845:3949 */               kind = 38;
/* 4846:     */             }
/* 4847:     */             break;
/* 4848:     */           case 211: 
/* 4849:3952 */             if (this.curChar == '\\') {
/* 4850:3953 */               jjCheckNAddStates(675, 677);
/* 4851:     */             }
/* 4852:     */             break;
/* 4853:     */           case 224: 
/* 4854:3956 */             if (this.curChar == '\\') {
/* 4855:3957 */               jjAddStates(678, 679);
/* 4856:     */             }
/* 4857:     */             break;
/* 4858:     */           case 238: 
/* 4859:3960 */             if ((0x10000 & l) != 0L) {
/* 4860:3961 */               jjCheckNAddTwoStates(239, 240);
/* 4861:     */             }
/* 4862:     */             break;
/* 4863:     */           case 239: 
/* 4864:3964 */             if (((0x1000000 & l) != 0L) && (kind > 39)) {
/* 4865:3965 */               kind = 39;
/* 4866:     */             }
/* 4867:     */             break;
/* 4868:     */           case 240: 
/* 4869:3968 */             if (this.curChar == '\\') {
/* 4870:3969 */               jjCheckNAddStates(680, 682);
/* 4871:     */             }
/* 4872:     */             break;
/* 4873:     */           case 253: 
/* 4874:3972 */             if (this.curChar == '\\') {
/* 4875:3973 */               jjAddStates(683, 685);
/* 4876:     */             }
/* 4877:     */             break;
/* 4878:     */           case 267: 
/* 4879:3976 */             if ((0x8 & l) != 0L) {
/* 4880:3977 */               jjCheckNAddTwoStates(268, 269);
/* 4881:     */             }
/* 4882:     */             break;
/* 4883:     */           case 268: 
/* 4884:3980 */             if (((0x2000 & l) != 0L) && (kind > 40)) {
/* 4885:3981 */               kind = 40;
/* 4886:     */             }
/* 4887:     */             break;
/* 4888:     */           case 269: 
/* 4889:3984 */             if (this.curChar == '\\') {
/* 4890:3985 */               jjCheckNAddStates(686, 688);
/* 4891:     */             }
/* 4892:     */             break;
/* 4893:     */           case 271: 
/* 4894:3988 */             if ((0x10 & l) != 0L)
/* 4895:     */             {
/* 4896:3990 */               if (kind > 40) {
/* 4897:3991 */                 kind = 40;
/* 4898:     */               }
/* 4899:3992 */               jjAddStates(689, 690);
/* 4900:     */             }
/* 4901:3993 */             break;
/* 4902:     */           case 282: 
/* 4903:3995 */             if (this.curChar == '\\') {
/* 4904:3996 */               jjAddStates(691, 692);
/* 4905:     */             }
/* 4906:     */             break;
/* 4907:     */           case 296: 
/* 4908:3999 */             if ((0x2000 & l) != 0L) {
/* 4909:4000 */               jjCheckNAddTwoStates(297, 298);
/* 4910:     */             }
/* 4911:     */             break;
/* 4912:     */           case 297: 
/* 4913:4003 */             if (((0x2000 & l) != 0L) && (kind > 41)) {
/* 4914:4004 */               kind = 41;
/* 4915:     */             }
/* 4916:     */             break;
/* 4917:     */           case 298: 
/* 4918:4007 */             if (this.curChar == '\\') {
/* 4919:4008 */               jjCheckNAddStates(693, 695);
/* 4920:     */             }
/* 4921:     */             break;
/* 4922:     */           case 300: 
/* 4923:4011 */             if ((0x10 & l) != 0L)
/* 4924:     */             {
/* 4925:4013 */               if (kind > 41) {
/* 4926:4014 */                 kind = 41;
/* 4927:     */               }
/* 4928:4015 */               jjAddStates(696, 697);
/* 4929:     */             }
/* 4930:4016 */             break;
/* 4931:     */           case 311: 
/* 4932:4018 */             if (this.curChar == '\\') {
/* 4933:4019 */               jjAddStates(698, 700);
/* 4934:     */             }
/* 4935:     */             break;
/* 4936:     */           case 313: 
/* 4937:4022 */             if ((0x10 & l) != 0L) {
/* 4938:4023 */               jjCheckNAddStates(701, 704);
/* 4939:     */             }
/* 4940:     */             break;
/* 4941:     */           case 325: 
/* 4942:4026 */             if ((0x200 & l) != 0L) {
/* 4943:4027 */               jjCheckNAddTwoStates(326, 327);
/* 4944:     */             }
/* 4945:     */             break;
/* 4946:     */           case 326: 
/* 4947:4030 */             if (((0x4000 & l) != 0L) && (kind > 42)) {
/* 4948:4031 */               kind = 42;
/* 4949:     */             }
/* 4950:     */             break;
/* 4951:     */           case 327: 
/* 4952:4034 */             if (this.curChar == '\\') {
/* 4953:4035 */               jjCheckNAddStates(705, 707);
/* 4954:     */             }
/* 4955:     */             break;
/* 4956:     */           case 329: 
/* 4957:4038 */             if ((0x20 & l) != 0L)
/* 4958:     */             {
/* 4959:4040 */               if (kind > 42) {
/* 4960:4041 */                 kind = 42;
/* 4961:     */               }
/* 4962:4042 */               jjAddStates(708, 709);
/* 4963:     */             }
/* 4964:4043 */             break;
/* 4965:     */           case 340: 
/* 4966:4045 */             if (this.curChar == '\\') {
/* 4967:4046 */               jjAddStates(710, 712);
/* 4968:     */             }
/* 4969:     */             break;
/* 4970:     */           case 354: 
/* 4971:4049 */             if ((0x10000 & l) != 0L) {
/* 4972:4050 */               jjCheckNAddTwoStates(355, 356);
/* 4973:     */             }
/* 4974:     */             break;
/* 4975:     */           case 355: 
/* 4976:4053 */             if (((0x100000 & l) != 0L) && (kind > 43)) {
/* 4977:4054 */               kind = 43;
/* 4978:     */             }
/* 4979:     */             break;
/* 4980:     */           case 356: 
/* 4981:4057 */             if (this.curChar == '\\') {
/* 4982:4058 */               jjCheckNAddStates(713, 715);
/* 4983:     */             }
/* 4984:     */             break;
/* 4985:     */           case 369: 
/* 4986:4061 */             if (this.curChar == '\\') {
/* 4987:4062 */               jjAddStates(716, 718);
/* 4988:     */             }
/* 4989:     */             break;
/* 4990:     */           case 383: 
/* 4991:4065 */             if ((0x10000 & l) != 0L) {
/* 4992:4066 */               jjAddStates(719, 720);
/* 4993:     */             }
/* 4994:     */             break;
/* 4995:     */           case 384: 
/* 4996:4069 */             if (((0x8 & l) != 0L) && (kind > 44)) {
/* 4997:4070 */               kind = 44;
/* 4998:     */             }
/* 4999:     */             break;
/* 5000:     */           case 385: 
/* 5001:4073 */             if (this.curChar == '\\') {
/* 5002:4074 */               jjAddStates(721, 722);
/* 5003:     */             }
/* 5004:     */             break;
/* 5005:     */           case 398: 
/* 5006:4077 */             if (this.curChar == '\\') {
/* 5007:4078 */               jjAddStates(723, 725);
/* 5008:     */             }
/* 5009:     */             break;
/* 5010:     */           case 412: 
/* 5011:4081 */             if ((0x10 & l) != 0L) {
/* 5012:4082 */               jjAddStates(726, 727);
/* 5013:     */             }
/* 5014:     */             break;
/* 5015:     */           case 413: 
/* 5016:4085 */             if ((0x20 & l) != 0L) {
/* 5017:4086 */               jjCheckNAddTwoStates(414, 415);
/* 5018:     */             }
/* 5019:     */             break;
/* 5020:     */           case 414: 
/* 5021:4089 */             if (((0x80 & l) != 0L) && (kind > 45)) {
/* 5022:4090 */               kind = 45;
/* 5023:     */             }
/* 5024:     */             break;
/* 5025:     */           case 415: 
/* 5026:4093 */             if (this.curChar == '\\') {
/* 5027:4094 */               jjCheckNAddStates(728, 730);
/* 5028:     */             }
/* 5029:     */             break;
/* 5030:     */           case 428: 
/* 5031:4097 */             if (this.curChar == '\\') {
/* 5032:4098 */               jjAddStates(731, 732);
/* 5033:     */             }
/* 5034:     */             break;
/* 5035:     */           case 441: 
/* 5036:4101 */             if (this.curChar == '\\') {
/* 5037:4102 */               jjAddStates(733, 734);
/* 5038:     */             }
/* 5039:     */             break;
/* 5040:     */           case 455: 
/* 5041:4105 */             if ((0x40000 & l) != 0L) {
/* 5042:4106 */               jjAddStates(735, 736);
/* 5043:     */             }
/* 5044:     */             break;
/* 5045:     */           case 456: 
/* 5046:4109 */             if ((0x2 & l) != 0L) {
/* 5047:4110 */               jjAddStates(737, 738);
/* 5048:     */             }
/* 5049:     */             break;
/* 5050:     */           case 457: 
/* 5051:4113 */             if (((0x10 & l) != 0L) && (kind > 46)) {
/* 5052:4114 */               kind = 46;
/* 5053:     */             }
/* 5054:     */             break;
/* 5055:     */           case 458: 
/* 5056:4117 */             if (this.curChar == '\\') {
/* 5057:4118 */               jjAddStates(739, 740);
/* 5058:     */             }
/* 5059:     */             break;
/* 5060:     */           case 471: 
/* 5061:4121 */             if (this.curChar == '\\') {
/* 5062:4122 */               jjAddStates(741, 742);
/* 5063:     */             }
/* 5064:     */             break;
/* 5065:     */           case 484: 
/* 5066:4125 */             if (this.curChar == '\\') {
/* 5067:4126 */               jjAddStates(743, 745);
/* 5068:     */             }
/* 5069:     */             break;
/* 5070:     */           case 498: 
/* 5071:4129 */             if ((0x80 & l) != 0L) {
/* 5072:4130 */               jjCheckNAddTwoStates(499, 528);
/* 5073:     */             }
/* 5074:     */             break;
/* 5075:     */           case 499: 
/* 5076:4133 */             if ((0x40000 & l) != 0L) {
/* 5077:4134 */               jjAddStates(746, 747);
/* 5078:     */             }
/* 5079:     */             break;
/* 5080:     */           case 500: 
/* 5081:4137 */             if ((0x2 & l) != 0L) {
/* 5082:4138 */               jjAddStates(748, 749);
/* 5083:     */             }
/* 5084:     */             break;
/* 5085:     */           case 501: 
/* 5086:4141 */             if (((0x10 & l) != 0L) && (kind > 47)) {
/* 5087:4142 */               kind = 47;
/* 5088:     */             }
/* 5089:     */             break;
/* 5090:     */           case 502: 
/* 5091:4145 */             if (this.curChar == '\\') {
/* 5092:4146 */               jjAddStates(750, 751);
/* 5093:     */             }
/* 5094:     */             break;
/* 5095:     */           case 515: 
/* 5096:4149 */             if (this.curChar == '\\') {
/* 5097:4150 */               jjAddStates(752, 753);
/* 5098:     */             }
/* 5099:     */             break;
/* 5100:     */           case 528: 
/* 5101:4153 */             if (this.curChar == '\\') {
/* 5102:4154 */               jjCheckNAddStates(754, 756);
/* 5103:     */             }
/* 5104:     */             break;
/* 5105:     */           case 541: 
/* 5106:4157 */             if (this.curChar == '\\') {
/* 5107:4158 */               jjAddStates(757, 759);
/* 5108:     */             }
/* 5109:     */             break;
/* 5110:     */           case 555: 
/* 5111:4161 */             if ((0x2000 & l) != 0L) {
/* 5112:4162 */               jjCheckNAddTwoStates(556, 557);
/* 5113:     */             }
/* 5114:     */             break;
/* 5115:     */           case 556: 
/* 5116:4165 */             if (((0x80000 & l) != 0L) && (kind > 48)) {
/* 5117:4166 */               kind = 48;
/* 5118:     */             }
/* 5119:     */             break;
/* 5120:     */           case 557: 
/* 5121:4169 */             if (this.curChar == '\\') {
/* 5122:4170 */               jjCheckNAddStates(760, 762);
/* 5123:     */             }
/* 5124:     */             break;
/* 5125:     */           case 570: 
/* 5126:4173 */             if (this.curChar == '\\') {
/* 5127:4174 */               jjAddStates(763, 765);
/* 5128:     */             }
/* 5129:     */             break;
/* 5130:     */           case 572: 
/* 5131:4177 */             if ((0x10 & l) != 0L) {
/* 5132:4178 */               jjCheckNAddStates(766, 769);
/* 5133:     */             }
/* 5134:     */             break;
/* 5135:     */           case 584: 
/* 5136:4181 */             if (((0x80000 & l) != 0L) && (kind > 49)) {
/* 5137:4182 */               kind = 49;
/* 5138:     */             }
/* 5139:     */             break;
/* 5140:     */           case 585: 
/* 5141:4185 */             if (this.curChar == '\\') {
/* 5142:4186 */               jjAddStates(770, 772);
/* 5143:     */             }
/* 5144:     */             break;
/* 5145:     */           case 599: 
/* 5146:4189 */             if ((0x100 & l) != 0L) {
/* 5147:4190 */               jjCheckNAddTwoStates(600, 601);
/* 5148:     */             }
/* 5149:     */             break;
/* 5150:     */           case 600: 
/* 5151:4193 */             if (((0x4000000 & l) != 0L) && (kind > 50)) {
/* 5152:4194 */               kind = 50;
/* 5153:     */             }
/* 5154:     */             break;
/* 5155:     */           case 601: 
/* 5156:4197 */             if (this.curChar == '\\') {
/* 5157:4198 */               jjCheckNAddStates(773, 775);
/* 5158:     */             }
/* 5159:     */             break;
/* 5160:     */           case 603: 
/* 5161:4201 */             if ((0x2 & l) != 0L)
/* 5162:     */             {
/* 5163:4203 */               if (kind > 50) {
/* 5164:4204 */                 kind = 50;
/* 5165:     */               }
/* 5166:4205 */               jjAddStates(776, 777);
/* 5167:     */             }
/* 5168:4206 */             break;
/* 5169:     */           case 614: 
/* 5170:4208 */             if (this.curChar == '\\') {
/* 5171:4209 */               jjAddStates(778, 780);
/* 5172:     */             }
/* 5173:     */             break;
/* 5174:     */           case 628: 
/* 5175:4212 */             if ((0x800 & l) != 0L) {
/* 5176:4213 */               jjCheckNAddTwoStates(629, 644);
/* 5177:     */             }
/* 5178:     */             break;
/* 5179:     */           case 629: 
/* 5180:4216 */             if ((0x100 & l) != 0L) {
/* 5181:4217 */               jjCheckNAddTwoStates(630, 631);
/* 5182:     */             }
/* 5183:     */             break;
/* 5184:     */           case 630: 
/* 5185:4220 */             if (((0x4000000 & l) != 0L) && (kind > 51)) {
/* 5186:4221 */               kind = 51;
/* 5187:     */             }
/* 5188:     */             break;
/* 5189:     */           case 631: 
/* 5190:4224 */             if (this.curChar == '\\') {
/* 5191:4225 */               jjCheckNAddStates(781, 783);
/* 5192:     */             }
/* 5193:     */             break;
/* 5194:     */           case 633: 
/* 5195:4228 */             if ((0x2 & l) != 0L)
/* 5196:     */             {
/* 5197:4230 */               if (kind > 51) {
/* 5198:4231 */                 kind = 51;
/* 5199:     */               }
/* 5200:4232 */               jjAddStates(784, 785);
/* 5201:     */             }
/* 5202:4233 */             break;
/* 5203:     */           case 644: 
/* 5204:4235 */             if (this.curChar == '\\') {
/* 5205:4236 */               jjCheckNAddStates(786, 788);
/* 5206:     */             }
/* 5207:     */             break;
/* 5208:     */           case 657: 
/* 5209:4239 */             if (this.curChar == '\\') {
/* 5210:4240 */               jjAddStates(789, 791);
/* 5211:     */             }
/* 5212:     */             break;
/* 5213:     */           case 659: 
/* 5214:4243 */             if ((0x4 & l) != 0L) {
/* 5215:4244 */               jjCheckNAddStates(792, 795);
/* 5216:     */             }
/* 5217:     */             break;
/* 5218:     */           case 672: 
/* 5219:     */           case 673: 
/* 5220:4248 */             if ((0x87FFFFFE & l) != 0L)
/* 5221:     */             {
/* 5222:4250 */               if (kind > 52) {
/* 5223:4251 */                 kind = 52;
/* 5224:     */               }
/* 5225:4252 */               jjCheckNAddTwoStates(673, 674);
/* 5226:     */             }
/* 5227:4253 */             break;
/* 5228:     */           case 674: 
/* 5229:4255 */             if (this.curChar == '\\') {
/* 5230:4256 */               jjCheckNAddTwoStates(675, 676);
/* 5231:     */             }
/* 5232:     */             break;
/* 5233:     */           case 675: 
/* 5234:4259 */             if ((0xFFFFFF81 & l) != 0L)
/* 5235:     */             {
/* 5236:4261 */               if (kind > 52) {
/* 5237:4262 */                 kind = 52;
/* 5238:     */               }
/* 5239:4263 */               jjCheckNAddTwoStates(673, 674);
/* 5240:     */             }
/* 5241:4264 */             break;
/* 5242:     */           case 676: 
/* 5243:4266 */             if ((0x7E & l) != 0L)
/* 5244:     */             {
/* 5245:4268 */               if (kind > 52) {
/* 5246:4269 */                 kind = 52;
/* 5247:     */               }
/* 5248:4270 */               jjCheckNAddStates(505, 513);
/* 5249:     */             }
/* 5250:4271 */             break;
/* 5251:     */           case 677: 
/* 5252:4273 */             if ((0x7E & l) != 0L)
/* 5253:     */             {
/* 5254:4275 */               if (kind > 52) {
/* 5255:4276 */                 kind = 52;
/* 5256:     */               }
/* 5257:4277 */               jjCheckNAddStates(514, 517);
/* 5258:     */             }
/* 5259:4278 */             break;
/* 5260:     */           case 681: 
/* 5261:     */           case 683: 
/* 5262:     */           case 686: 
/* 5263:     */           case 690: 
/* 5264:4283 */             if ((0x7E & l) != 0L) {
/* 5265:4284 */               jjCheckNAdd(677);
/* 5266:     */             }
/* 5267:     */             break;
/* 5268:     */           case 682: 
/* 5269:4287 */             if ((0x7E & l) != 0L) {
/* 5270:4288 */               this.jjstateSet[(this.jjnewStateCnt++)] = 683;
/* 5271:     */             }
/* 5272:     */             break;
/* 5273:     */           case 684: 
/* 5274:4291 */             if ((0x7E & l) != 0L) {
/* 5275:4292 */               this.jjstateSet[(this.jjnewStateCnt++)] = 685;
/* 5276:     */             }
/* 5277:     */             break;
/* 5278:     */           case 685: 
/* 5279:4295 */             if ((0x7E & l) != 0L) {
/* 5280:4296 */               this.jjstateSet[(this.jjnewStateCnt++)] = 686;
/* 5281:     */             }
/* 5282:     */             break;
/* 5283:     */           case 687: 
/* 5284:4299 */             if ((0x7E & l) != 0L) {
/* 5285:4300 */               this.jjstateSet[(this.jjnewStateCnt++)] = 688;
/* 5286:     */             }
/* 5287:     */             break;
/* 5288:     */           case 688: 
/* 5289:4303 */             if ((0x7E & l) != 0L) {
/* 5290:4304 */               this.jjstateSet[(this.jjnewStateCnt++)] = 689;
/* 5291:     */             }
/* 5292:     */             break;
/* 5293:     */           case 689: 
/* 5294:4307 */             if ((0x7E & l) != 0L) {
/* 5295:4308 */               this.jjstateSet[(this.jjnewStateCnt++)] = 690;
/* 5296:     */             }
/* 5297:     */             break;
/* 5298:     */           case 691: 
/* 5299:4311 */             if (this.curChar == '\\') {
/* 5300:4312 */               jjCheckNAddTwoStates(675, 692);
/* 5301:     */             }
/* 5302:     */             break;
/* 5303:     */           case 692: 
/* 5304:4315 */             if ((0x7E & l) != 0L)
/* 5305:     */             {
/* 5306:4317 */               if (kind > 52) {
/* 5307:4318 */                 kind = 52;
/* 5308:     */               }
/* 5309:4319 */               jjCheckNAddStates(518, 526);
/* 5310:     */             }
/* 5311:4320 */             break;
/* 5312:     */           case 693: 
/* 5313:4322 */             if ((0x7E & l) != 0L)
/* 5314:     */             {
/* 5315:4324 */               if (kind > 52) {
/* 5316:4325 */                 kind = 52;
/* 5317:     */               }
/* 5318:4326 */               jjCheckNAddStates(527, 530);
/* 5319:     */             }
/* 5320:4327 */             break;
/* 5321:     */           case 695: 
/* 5322:     */           case 697: 
/* 5323:     */           case 700: 
/* 5324:     */           case 704: 
/* 5325:4332 */             if ((0x7E & l) != 0L) {
/* 5326:4333 */               jjCheckNAdd(693);
/* 5327:     */             }
/* 5328:     */             break;
/* 5329:     */           case 696: 
/* 5330:4336 */             if ((0x7E & l) != 0L) {
/* 5331:4337 */               this.jjstateSet[(this.jjnewStateCnt++)] = 697;
/* 5332:     */             }
/* 5333:     */             break;
/* 5334:     */           case 698: 
/* 5335:4340 */             if ((0x7E & l) != 0L) {
/* 5336:4341 */               this.jjstateSet[(this.jjnewStateCnt++)] = 699;
/* 5337:     */             }
/* 5338:     */             break;
/* 5339:     */           case 699: 
/* 5340:4344 */             if ((0x7E & l) != 0L) {
/* 5341:4345 */               this.jjstateSet[(this.jjnewStateCnt++)] = 700;
/* 5342:     */             }
/* 5343:     */             break;
/* 5344:     */           case 701: 
/* 5345:4348 */             if ((0x7E & l) != 0L) {
/* 5346:4349 */               this.jjstateSet[(this.jjnewStateCnt++)] = 702;
/* 5347:     */             }
/* 5348:     */             break;
/* 5349:     */           case 702: 
/* 5350:4352 */             if ((0x7E & l) != 0L) {
/* 5351:4353 */               this.jjstateSet[(this.jjnewStateCnt++)] = 703;
/* 5352:     */             }
/* 5353:     */             break;
/* 5354:     */           case 703: 
/* 5355:4356 */             if ((0x7E & l) != 0L) {
/* 5356:4357 */               this.jjstateSet[(this.jjnewStateCnt++)] = 704;
/* 5357:     */             }
/* 5358:     */             break;
/* 5359:     */           case 711: 
/* 5360:4360 */             if ((0x87FFFFFE & l) != 0L) {
/* 5361:4361 */               jjCheckNAddStates(132, 134);
/* 5362:     */             }
/* 5363:     */             break;
/* 5364:     */           case 713: 
/* 5365:4364 */             if (this.curChar == '\\') {
/* 5366:4365 */               jjCheckNAddTwoStates(714, 715);
/* 5367:     */             }
/* 5368:     */             break;
/* 5369:     */           case 714: 
/* 5370:4368 */             if ((0xFFFFFF81 & l) != 0L) {
/* 5371:4369 */               jjCheckNAddStates(132, 134);
/* 5372:     */             }
/* 5373:     */             break;
/* 5374:     */           case 715: 
/* 5375:4372 */             if ((0x7E & l) != 0L) {
/* 5376:4373 */               jjCheckNAddStates(531, 540);
/* 5377:     */             }
/* 5378:     */             break;
/* 5379:     */           case 716: 
/* 5380:4376 */             if ((0x7E & l) != 0L) {
/* 5381:4377 */               jjCheckNAddStates(541, 545);
/* 5382:     */             }
/* 5383:     */             break;
/* 5384:     */           case 720: 
/* 5385:     */           case 722: 
/* 5386:     */           case 725: 
/* 5387:     */           case 729: 
/* 5388:4383 */             if ((0x7E & l) != 0L) {
/* 5389:4384 */               jjCheckNAdd(716);
/* 5390:     */             }
/* 5391:     */             break;
/* 5392:     */           case 721: 
/* 5393:4387 */             if ((0x7E & l) != 0L) {
/* 5394:4388 */               this.jjstateSet[(this.jjnewStateCnt++)] = 722;
/* 5395:     */             }
/* 5396:     */             break;
/* 5397:     */           case 723: 
/* 5398:4391 */             if ((0x7E & l) != 0L) {
/* 5399:4392 */               this.jjstateSet[(this.jjnewStateCnt++)] = 724;
/* 5400:     */             }
/* 5401:     */             break;
/* 5402:     */           case 724: 
/* 5403:4395 */             if ((0x7E & l) != 0L) {
/* 5404:4396 */               this.jjstateSet[(this.jjnewStateCnt++)] = 725;
/* 5405:     */             }
/* 5406:     */             break;
/* 5407:     */           case 726: 
/* 5408:4399 */             if ((0x7E & l) != 0L) {
/* 5409:4400 */               this.jjstateSet[(this.jjnewStateCnt++)] = 727;
/* 5410:     */             }
/* 5411:     */             break;
/* 5412:     */           case 727: 
/* 5413:4403 */             if ((0x7E & l) != 0L) {
/* 5414:4404 */               this.jjstateSet[(this.jjnewStateCnt++)] = 728;
/* 5415:     */             }
/* 5416:     */             break;
/* 5417:     */           case 728: 
/* 5418:4407 */             if ((0x7E & l) != 0L) {
/* 5419:4408 */               this.jjstateSet[(this.jjnewStateCnt++)] = 729;
/* 5420:     */             }
/* 5421:     */             break;
/* 5422:     */           case 730: 
/* 5423:4411 */             if ((0x87FFFFFE & l) != 0L)
/* 5424:     */             {
/* 5425:4413 */               if (kind > 56) {
/* 5426:4414 */                 kind = 56;
/* 5427:     */               }
/* 5428:4415 */               jjCheckNAddTwoStates(731, 732);
/* 5429:     */             }
/* 5430:4416 */             break;
/* 5431:     */           case 731: 
/* 5432:4418 */             if ((0x87FFFFFE & l) != 0L)
/* 5433:     */             {
/* 5434:4420 */               if (kind > 56) {
/* 5435:4421 */                 kind = 56;
/* 5436:     */               }
/* 5437:4422 */               jjCheckNAddTwoStates(731, 732);
/* 5438:     */             }
/* 5439:4423 */             break;
/* 5440:     */           case 732: 
/* 5441:4425 */             if (this.curChar == '\\') {
/* 5442:4426 */               jjCheckNAddTwoStates(733, 734);
/* 5443:     */             }
/* 5444:     */             break;
/* 5445:     */           case 733: 
/* 5446:4429 */             if ((0xFFFFFF81 & l) != 0L)
/* 5447:     */             {
/* 5448:4431 */               if (kind > 56) {
/* 5449:4432 */                 kind = 56;
/* 5450:     */               }
/* 5451:4433 */               jjCheckNAddTwoStates(731, 732);
/* 5452:     */             }
/* 5453:4434 */             break;
/* 5454:     */           case 734: 
/* 5455:4436 */             if ((0x7E & l) != 0L)
/* 5456:     */             {
/* 5457:4438 */               if (kind > 56) {
/* 5458:4439 */                 kind = 56;
/* 5459:     */               }
/* 5460:4440 */               jjCheckNAddStates(546, 554);
/* 5461:     */             }
/* 5462:4441 */             break;
/* 5463:     */           case 735: 
/* 5464:4443 */             if ((0x7E & l) != 0L)
/* 5465:     */             {
/* 5466:4445 */               if (kind > 56) {
/* 5467:4446 */                 kind = 56;
/* 5468:     */               }
/* 5469:4447 */               jjCheckNAddStates(555, 558);
/* 5470:     */             }
/* 5471:4448 */             break;
/* 5472:     */           case 739: 
/* 5473:     */           case 741: 
/* 5474:     */           case 744: 
/* 5475:     */           case 748: 
/* 5476:4453 */             if ((0x7E & l) != 0L) {
/* 5477:4454 */               jjCheckNAdd(735);
/* 5478:     */             }
/* 5479:     */             break;
/* 5480:     */           case 740: 
/* 5481:4457 */             if ((0x7E & l) != 0L) {
/* 5482:4458 */               this.jjstateSet[(this.jjnewStateCnt++)] = 741;
/* 5483:     */             }
/* 5484:     */             break;
/* 5485:     */           case 742: 
/* 5486:4461 */             if ((0x7E & l) != 0L) {
/* 5487:4462 */               this.jjstateSet[(this.jjnewStateCnt++)] = 743;
/* 5488:     */             }
/* 5489:     */             break;
/* 5490:     */           case 743: 
/* 5491:4465 */             if ((0x7E & l) != 0L) {
/* 5492:4466 */               this.jjstateSet[(this.jjnewStateCnt++)] = 744;
/* 5493:     */             }
/* 5494:     */             break;
/* 5495:     */           case 745: 
/* 5496:4469 */             if ((0x7E & l) != 0L) {
/* 5497:4470 */               this.jjstateSet[(this.jjnewStateCnt++)] = 746;
/* 5498:     */             }
/* 5499:     */             break;
/* 5500:     */           case 746: 
/* 5501:4473 */             if ((0x7E & l) != 0L) {
/* 5502:4474 */               this.jjstateSet[(this.jjnewStateCnt++)] = 747;
/* 5503:     */             }
/* 5504:     */             break;
/* 5505:     */           case 747: 
/* 5506:4477 */             if ((0x7E & l) != 0L) {
/* 5507:4478 */               this.jjstateSet[(this.jjnewStateCnt++)] = 748;
/* 5508:     */             }
/* 5509:     */             break;
/* 5510:     */           case 749: 
/* 5511:4481 */             if (this.curChar == '\\') {
/* 5512:4482 */               jjCheckNAddTwoStates(733, 750);
/* 5513:     */             }
/* 5514:     */             break;
/* 5515:     */           case 750: 
/* 5516:4485 */             if ((0x7E & l) != 0L)
/* 5517:     */             {
/* 5518:4487 */               if (kind > 56) {
/* 5519:4488 */                 kind = 56;
/* 5520:     */               }
/* 5521:4489 */               jjCheckNAddStates(559, 567);
/* 5522:     */             }
/* 5523:4490 */             break;
/* 5524:     */           case 751: 
/* 5525:4492 */             if ((0x7E & l) != 0L)
/* 5526:     */             {
/* 5527:4494 */               if (kind > 56) {
/* 5528:4495 */                 kind = 56;
/* 5529:     */               }
/* 5530:4496 */               jjCheckNAddStates(568, 571);
/* 5531:     */             }
/* 5532:4497 */             break;
/* 5533:     */           case 753: 
/* 5534:     */           case 755: 
/* 5535:     */           case 758: 
/* 5536:     */           case 762: 
/* 5537:4502 */             if ((0x7E & l) != 0L) {
/* 5538:4503 */               jjCheckNAdd(751);
/* 5539:     */             }
/* 5540:     */             break;
/* 5541:     */           case 754: 
/* 5542:4506 */             if ((0x7E & l) != 0L) {
/* 5543:4507 */               this.jjstateSet[(this.jjnewStateCnt++)] = 755;
/* 5544:     */             }
/* 5545:     */             break;
/* 5546:     */           case 756: 
/* 5547:4510 */             if ((0x7E & l) != 0L) {
/* 5548:4511 */               this.jjstateSet[(this.jjnewStateCnt++)] = 757;
/* 5549:     */             }
/* 5550:     */             break;
/* 5551:     */           case 757: 
/* 5552:4514 */             if ((0x7E & l) != 0L) {
/* 5553:4515 */               this.jjstateSet[(this.jjnewStateCnt++)] = 758;
/* 5554:     */             }
/* 5555:     */             break;
/* 5556:     */           case 759: 
/* 5557:4518 */             if ((0x7E & l) != 0L) {
/* 5558:4519 */               this.jjstateSet[(this.jjnewStateCnt++)] = 760;
/* 5559:     */             }
/* 5560:     */             break;
/* 5561:     */           case 760: 
/* 5562:4522 */             if ((0x7E & l) != 0L) {
/* 5563:4523 */               this.jjstateSet[(this.jjnewStateCnt++)] = 761;
/* 5564:     */             }
/* 5565:     */             break;
/* 5566:     */           case 761: 
/* 5567:4526 */             if ((0x7E & l) != 0L) {
/* 5568:4527 */               this.jjstateSet[(this.jjnewStateCnt++)] = 762;
/* 5569:     */             }
/* 5570:     */             break;
/* 5571:     */           case 763: 
/* 5572:4530 */             if (this.curChar == '\\') {
/* 5573:4531 */               jjCheckNAddTwoStates(714, 764);
/* 5574:     */             }
/* 5575:     */             break;
/* 5576:     */           case 764: 
/* 5577:4534 */             if ((0x7E & l) != 0L) {
/* 5578:4535 */               jjCheckNAddStates(572, 581);
/* 5579:     */             }
/* 5580:     */             break;
/* 5581:     */           case 765: 
/* 5582:4538 */             if ((0x7E & l) != 0L) {
/* 5583:4539 */               jjCheckNAddStates(582, 586);
/* 5584:     */             }
/* 5585:     */             break;
/* 5586:     */           case 767: 
/* 5587:     */           case 769: 
/* 5588:     */           case 772: 
/* 5589:     */           case 776: 
/* 5590:4545 */             if ((0x7E & l) != 0L) {
/* 5591:4546 */               jjCheckNAdd(765);
/* 5592:     */             }
/* 5593:     */             break;
/* 5594:     */           case 768: 
/* 5595:4549 */             if ((0x7E & l) != 0L) {
/* 5596:4550 */               this.jjstateSet[(this.jjnewStateCnt++)] = 769;
/* 5597:     */             }
/* 5598:     */             break;
/* 5599:     */           case 770: 
/* 5600:4553 */             if ((0x7E & l) != 0L) {
/* 5601:4554 */               this.jjstateSet[(this.jjnewStateCnt++)] = 771;
/* 5602:     */             }
/* 5603:     */             break;
/* 5604:     */           case 771: 
/* 5605:4557 */             if ((0x7E & l) != 0L) {
/* 5606:4558 */               this.jjstateSet[(this.jjnewStateCnt++)] = 772;
/* 5607:     */             }
/* 5608:     */             break;
/* 5609:     */           case 773: 
/* 5610:4561 */             if ((0x7E & l) != 0L) {
/* 5611:4562 */               this.jjstateSet[(this.jjnewStateCnt++)] = 774;
/* 5612:     */             }
/* 5613:     */             break;
/* 5614:     */           case 774: 
/* 5615:4565 */             if ((0x7E & l) != 0L) {
/* 5616:4566 */               this.jjstateSet[(this.jjnewStateCnt++)] = 775;
/* 5617:     */             }
/* 5618:     */             break;
/* 5619:     */           case 775: 
/* 5620:4569 */             if ((0x7E & l) != 0L) {
/* 5621:4570 */               this.jjstateSet[(this.jjnewStateCnt++)] = 776;
/* 5622:     */             }
/* 5623:     */             break;
/* 5624:     */           case 777: 
/* 5625:4573 */             if ((0x87FFFFFE & l) != 0L)
/* 5626:     */             {
/* 5627:4575 */               if (kind > 56) {
/* 5628:4576 */                 kind = 56;
/* 5629:     */               }
/* 5630:4577 */               jjCheckNAddStates(636, 640);
/* 5631:     */             }
/* 5632:4578 */             break;
/* 5633:     */           case 836: 
/* 5634:4580 */             if (this.curChar == '\\') {
/* 5635:4581 */               jjCheckNAddStates(641, 644);
/* 5636:     */             }
/* 5637:     */             break;
/* 5638:     */           }
/* 5639:4585 */         } while (i != startsAt);
/* 5640:     */       }
/* 5641:     */       else
/* 5642:     */       {
/* 5643:4589 */         int i2 = (this.curChar & 0xFF) >> '\006';
/* 5644:4590 */         long l2 = 1L << (this.curChar & 0x3F);
/* 5645:     */         do
/* 5646:     */         {
/* 5647:4593 */           switch (this.jjstateSet[(--i)])
/* 5648:     */           {
/* 5649:     */           case 139: 
/* 5650:     */           case 839: 
/* 5651:4597 */             if ((jjbitVec2[i2] & l2) != 0L)
/* 5652:     */             {
/* 5653:4599 */               if (kind > 33) {
/* 5654:4600 */                 kind = 33;
/* 5655:     */               }
/* 5656:4601 */               jjCheckNAddTwoStates(139, 140);
/* 5657:     */             }
/* 5658:4602 */             break;
/* 5659:     */           case 137: 
/* 5660:     */           case 138: 
/* 5661:4605 */             if ((jjbitVec2[i2] & l2) != 0L)
/* 5662:     */             {
/* 5663:4607 */               if (kind > 33) {
/* 5664:4608 */                 kind = 33;
/* 5665:     */               }
/* 5666:4609 */               jjCheckNAddTwoStates(139, 140);
/* 5667:     */             }
/* 5668:4610 */             break;
/* 5669:     */           case 4: 
/* 5670:4612 */             if ((jjbitVec2[i2] & l2) != 0L)
/* 5671:     */             {
/* 5672:4614 */               if (kind > 56) {
/* 5673:4615 */                 kind = 56;
/* 5674:     */               }
/* 5675:4616 */               jjCheckNAddStates(636, 640);
/* 5676:     */             }
/* 5677:4617 */             break;
/* 5678:     */           case 710: 
/* 5679:4619 */             if ((jjbitVec2[i2] & l2) != 0L) {
/* 5680:4620 */               jjCheckNAddStates(132, 134);
/* 5681:     */             }
/* 5682:4621 */             if ((jjbitVec2[i2] & l2) != 0L)
/* 5683:     */             {
/* 5684:4623 */               if (kind > 56) {
/* 5685:4624 */                 kind = 56;
/* 5686:     */               }
/* 5687:4625 */               jjCheckNAddTwoStates(731, 732);
/* 5688:     */             }
/* 5689:     */             break;
/* 5690:     */           case 837: 
/* 5691:4629 */             if ((jjbitVec2[i2] & l2) != 0L) {
/* 5692:4630 */               jjCheckNAddStates(132, 134);
/* 5693:     */             }
/* 5694:4631 */             if ((jjbitVec2[i2] & l2) != 0L)
/* 5695:     */             {
/* 5696:4633 */               if (kind > 56) {
/* 5697:4634 */                 kind = 56;
/* 5698:     */               }
/* 5699:4635 */               jjCheckNAddTwoStates(731, 732);
/* 5700:     */             }
/* 5701:     */             break;
/* 5702:     */           case 5: 
/* 5703:4639 */             if ((jjbitVec2[i2] & l2) != 0L)
/* 5704:     */             {
/* 5705:4641 */               if (kind > 20) {
/* 5706:4642 */                 kind = 20;
/* 5707:     */               }
/* 5708:4643 */               jjCheckNAddTwoStates(5, 6);
/* 5709:     */             }
/* 5710:4644 */             break;
/* 5711:     */           case 7: 
/* 5712:4646 */             if ((jjbitVec2[i2] & l2) != 0L)
/* 5713:     */             {
/* 5714:4648 */               if (kind > 20) {
/* 5715:4649 */                 kind = 20;
/* 5716:     */               }
/* 5717:4650 */               jjCheckNAddTwoStates(5, 6);
/* 5718:     */             }
/* 5719:4651 */             break;
/* 5720:     */           case 24: 
/* 5721:     */           case 30: 
/* 5722:4654 */             if ((jjbitVec2[i2] & l2) != 0L) {
/* 5723:4655 */               jjCheckNAddStates(129, 131);
/* 5724:     */             }
/* 5725:     */             break;
/* 5726:     */           case 46: 
/* 5727:     */           case 52: 
/* 5728:4659 */             if ((jjbitVec2[i2] & l2) != 0L) {
/* 5729:4660 */               jjCheckNAddStates(126, 128);
/* 5730:     */             }
/* 5731:     */             break;
/* 5732:     */           case 68: 
/* 5733:4663 */             if ((jjbitVec2[i2] & l2) != 0L) {
/* 5734:4664 */               jjCheckNAddStates(233, 236);
/* 5735:     */             }
/* 5736:     */             break;
/* 5737:     */           case 72: 
/* 5738:4667 */             if ((jjbitVec2[i2] & l2) != 0L) {
/* 5739:4668 */               jjCheckNAddStates(233, 236);
/* 5740:     */             }
/* 5741:     */             break;
/* 5742:     */           case 89: 
/* 5743:     */           case 95: 
/* 5744:4672 */             if ((jjbitVec2[i2] & l2) != 0L) {
/* 5745:4673 */               jjCheckNAddStates(252, 254);
/* 5746:     */             }
/* 5747:     */             break;
/* 5748:     */           case 111: 
/* 5749:     */           case 117: 
/* 5750:4677 */             if ((jjbitVec2[i2] & l2) != 0L) {
/* 5751:4678 */               jjCheckNAddStates(270, 272);
/* 5752:     */             }
/* 5753:     */             break;
/* 5754:     */           case 141: 
/* 5755:4681 */             if ((jjbitVec2[i2] & l2) != 0L)
/* 5756:     */             {
/* 5757:4683 */               if (kind > 33) {
/* 5758:4684 */                 kind = 33;
/* 5759:     */               }
/* 5760:4685 */               jjCheckNAddTwoStates(139, 140);
/* 5761:     */             }
/* 5762:4686 */             break;
/* 5763:     */           case 672: 
/* 5764:     */           case 673: 
/* 5765:4689 */             if ((jjbitVec2[i2] & l2) != 0L)
/* 5766:     */             {
/* 5767:4691 */               if (kind > 52) {
/* 5768:4692 */                 kind = 52;
/* 5769:     */               }
/* 5770:4693 */               jjCheckNAddTwoStates(673, 674);
/* 5771:     */             }
/* 5772:4694 */             break;
/* 5773:     */           case 675: 
/* 5774:4696 */             if ((jjbitVec2[i2] & l2) != 0L)
/* 5775:     */             {
/* 5776:4698 */               if (kind > 52) {
/* 5777:4699 */                 kind = 52;
/* 5778:     */               }
/* 5779:4700 */               jjCheckNAddTwoStates(673, 674);
/* 5780:     */             }
/* 5781:4701 */             break;
/* 5782:     */           case 711: 
/* 5783:4703 */             if ((jjbitVec2[i2] & l2) != 0L) {
/* 5784:4704 */               jjCheckNAddStates(132, 134);
/* 5785:     */             }
/* 5786:     */             break;
/* 5787:     */           case 714: 
/* 5788:4707 */             if ((jjbitVec2[i2] & l2) != 0L) {
/* 5789:4708 */               jjCheckNAddStates(132, 134);
/* 5790:     */             }
/* 5791:     */             break;
/* 5792:     */           case 730: 
/* 5793:4711 */             if ((jjbitVec2[i2] & l2) != 0L)
/* 5794:     */             {
/* 5795:4713 */               if (kind > 56) {
/* 5796:4714 */                 kind = 56;
/* 5797:     */               }
/* 5798:4715 */               jjCheckNAddTwoStates(731, 732);
/* 5799:     */             }
/* 5800:4716 */             break;
/* 5801:     */           case 731: 
/* 5802:4718 */             if ((jjbitVec2[i2] & l2) != 0L)
/* 5803:     */             {
/* 5804:4720 */               if (kind > 56) {
/* 5805:4721 */                 kind = 56;
/* 5806:     */               }
/* 5807:4722 */               jjCheckNAddTwoStates(731, 732);
/* 5808:     */             }
/* 5809:4723 */             break;
/* 5810:     */           case 733: 
/* 5811:4725 */             if ((jjbitVec2[i2] & l2) != 0L)
/* 5812:     */             {
/* 5813:4727 */               if (kind > 56) {
/* 5814:4728 */                 kind = 56;
/* 5815:     */               }
/* 5816:4729 */               jjCheckNAddTwoStates(731, 732);
/* 5817:     */             }
/* 5818:     */             break;
/* 5819:     */           }
/* 5820:4733 */         } while (i != startsAt);
/* 5821:     */       }
/* 5822:4735 */       if (kind != 2147483647)
/* 5823:     */       {
/* 5824:4737 */         this.jjmatchedKind = kind;
/* 5825:4738 */         this.jjmatchedPos = curPos;
/* 5826:4739 */         kind = 2147483647;
/* 5827:     */       }
/* 5828:4741 */       curPos++;
/* 5829:4742 */       if ((i = this.jjnewStateCnt) == (startsAt = 837 - (this.jjnewStateCnt = startsAt))) {
/* 5830:4743 */         return curPos;
/* 5831:     */       }
/* 5832:     */       try
/* 5833:     */       {
/* 5834:4744 */         this.curChar = this.input_stream.readChar();
/* 5835:     */       }
/* 5836:     */       catch (IOException e) {}
/* 5837:     */     }
/* 5838:4745 */     return curPos;
/* 5839:     */   }
/* 5840:     */   
/* 5841:     */   private final int jjMoveStringLiteralDfa0_1()
/* 5842:     */   {
/* 5843:4750 */     switch (this.curChar)
/* 5844:     */     {
/* 5845:     */     case '*': 
/* 5846:4753 */       return jjMoveStringLiteralDfa1_1(16L);
/* 5847:     */     }
/* 5848:4755 */     return 1;
/* 5849:     */   }
/* 5850:     */   
/* 5851:     */   private final int jjMoveStringLiteralDfa1_1(long active0)
/* 5852:     */   {
/* 5853:     */     try
/* 5854:     */     {
/* 5855:4760 */       this.curChar = this.input_stream.readChar();
/* 5856:     */     }
/* 5857:     */     catch (IOException e)
/* 5858:     */     {
/* 5859:4762 */       return 1;
/* 5860:     */     }
/* 5861:4764 */     switch (this.curChar)
/* 5862:     */     {
/* 5863:     */     case '/': 
/* 5864:4767 */       if ((active0 & 0x10) != 0L) {
/* 5865:4768 */         return jjStopAtPos(1, 4);
/* 5866:     */       }
/* 5867:     */       break;
/* 5868:     */     default: 
/* 5869:4771 */       return 2;
/* 5870:     */     }
/* 5871:4773 */     return 2;
/* 5872:     */   }
/* 5873:     */   
/* 5874:4775 */   static final int[] jjnextStates = { 138, 157, 779, 780, 781, 180, 195, 782, 783, 784, 209, 224, 785, 786, 787, 238, 788, 789, 790, 267, 282, 791, 792, 793, 296, 794, 795, 796, 325, 797, 798, 799, 354, 800, 801, 802, 383, 803, 804, 805, 412, 441, 806, 807, 808, 455, 809, 810, 811, 498, 812, 813, 814, 555, 815, 816, 817, 584, 818, 819, 820, 599, 821, 822, 823, 628, 824, 825, 826, 671, 672, 827, 828, 829, 706, 830, 831, 832, 833, 834, 835, 691, 657, 614, 585, 570, 541, 484, 398, 369, 340, 311, 253, 172, 173, 174, 0, 175, 1, 176, 2, 177, 3, 710, 730, 749, 763, 179, 208, 237, 266, 295, 324, 353, 382, 411, 454, 497, 554, 583, 598, 627, 670, 705, 707, 708, 46, 47, 48, 24, 25, 26, 711, 712, 713, 670, 671, 672, 691, 627, 628, 657, 598, 599, 614, 583, 584, 585, 554, 555, 570, 497, 498, 541, 454, 455, 484, 411, 412, 441, 382, 383, 398, 353, 354, 369, 324, 325, 340, 295, 296, 311, 266, 267, 282, 237, 238, 253, 208, 209, 224, 179, 180, 195, 5, 9, 13, 14, 16, 19, 11, 12, 6, 5, 11, 12, 6, 24, 32, 35, 36, 38, 41, 33, 34, 25, 26, 24, 33, 34, 25, 26, 46, 54, 57, 58, 60, 63, 55, 56, 47, 48, 46, 55, 56, 47, 48, 68, 88, 110, 70, 71, 132, 68, 69, 70, 71, 68, 74, 78, 79, 81, 84, 76, 70, 71, 77, 68, 76, 70, 71, 77, 89, 90, 91, 89, 97, 100, 101, 103, 106, 98, 99, 90, 91, 89, 98, 99, 90, 91, 111, 112, 113, 111, 119, 122, 123, 125, 128, 120, 121, 112, 113, 111, 120, 121, 112, 113, 68, 88, 110, 69, 70, 71, 132, 139, 143, 147, 148, 150, 153, 145, 146, 140, 139, 145, 146, 140, 159, 161, 162, 164, 167, 160, 146, 139, 140, 160, 146, 139, 140, 191, 192, 194, 199, 200, 181, 182, 204, 205, 207, 215, 216, 220, 221, 223, 228, 229, 210, 211, 233, 234, 236, 244, 245, 249, 250, 252, 257, 258, 239, 240, 262, 263, 265, 278, 279, 281, 286, 287, 268, 269, 291, 292, 294, 307, 308, 310, 320, 321, 323, 336, 337, 339, 344, 345, 326, 327, 349, 350, 352, 360, 361, 365, 366, 368, 373, 374, 355, 356, 378, 379, 381, 389, 390, 394, 395, 397, 402, 403, 384, 385, 407, 408, 410, 419, 420, 424, 425, 427, 432, 433, 414, 415, 437, 438, 440, 445, 446, 413, 428, 450, 451, 453, 462, 463, 467, 468, 470, 475, 476, 457, 458, 480, 481, 483, 488, 489, 456, 471, 493, 494, 496, 506, 507, 511, 512, 514, 519, 520, 501, 502, 524, 525, 527, 532, 533, 500, 515, 537, 538, 540, 545, 546, 499, 528, 550, 551, 553, 561, 562, 566, 567, 569, 579, 580, 582, 589, 590, 594, 595, 597, 610, 611, 613, 618, 619, 600, 601, 623, 624, 626, 640, 641, 643, 648, 649, 630, 631, 653, 654, 656, 666, 667, 669, 673, 677, 681, 682, 684, 687, 679, 680, 674, 673, 679, 680, 674, 693, 695, 696, 698, 701, 694, 680, 673, 674, 694, 680, 673, 674, 711, 716, 720, 721, 723, 726, 718, 719, 712, 713, 711, 718, 719, 712, 713, 731, 735, 739, 740, 742, 745, 737, 738, 732, 731, 737, 738, 732, 751, 753, 754, 756, 759, 752, 738, 731, 732, 752, 738, 731, 732, 765, 767, 768, 770, 773, 766, 719, 711, 712, 713, 766, 719, 711, 712, 713, 779, 180, 195, 782, 209, 224, 785, 238, 253, 788, 267, 282, 791, 296, 311, 794, 325, 340, 797, 354, 369, 800, 383, 398, 803, 412, 441, 806, 455, 484, 809, 498, 541, 812, 555, 570, 815, 584, 585, 818, 599, 614, 821, 628, 657, 824, 671, 672, 691, 711, 712, 731, 732, 713, 714, 733, 750, 764, 137, 138, 157, 7, 8, 27, 29, 30, 31, 49, 51, 52, 53, 72, 73, 92, 94, 95, 96, 114, 116, 117, 118, 183, 181, 190, 186, 187, 196, 203, 212, 210, 219, 225, 232, 241, 239, 248, 254, 238, 261, 270, 268, 277, 273, 274, 283, 290, 299, 297, 306, 302, 303, 312, 296, 319, 315, 316, 297, 298, 328, 326, 335, 331, 332, 341, 325, 348, 357, 355, 364, 370, 354, 377, 384, 385, 386, 393, 399, 383, 406, 413, 428, 416, 414, 423, 429, 436, 442, 449, 456, 471, 457, 458, 459, 466, 472, 479, 485, 455, 492, 500, 515, 501, 502, 503, 510, 516, 523, 529, 499, 536, 542, 498, 549, 558, 556, 565, 571, 555, 578, 574, 575, 556, 557, 586, 584, 593, 602, 600, 609, 605, 606, 615, 599, 622, 632, 630, 639, 635, 636, 645, 629, 652, 658, 628, 665, 661, 662, 629, 644 };
/* 5875:4827 */   public static final String[] jjstrLiteralImages = { "", null, null, null, null, null, null, "}", null, ".", ";", ":", "*", "/", null, "-", "=", "[", "]", null, null, null, ")", null, null, "<!--", "-->", "~=", "|=", null, null, null, null, null, null, "!", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null };
/* 5876:4835 */   public static final String[] lexStateNames = { "DEFAULT", "COMMENT" };
/* 5877:4839 */   public static final int[] jjnewLexState = { -1, -1, -1, 1, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };
/* 5878:4845 */   static final long[] jjtoToken = { 432345564219178951L, 134217728L };
/* 5879:4848 */   static final long[] jjtoSkip = { 16L, 0L };
/* 5880:4851 */   static final long[] jjtoMore = { 40L, 0L };
/* 5881:     */   protected CharStream input_stream;
/* 5882:4855 */   private final int[] jjrounds = new int[837];
/* 5883:4856 */   private final int[] jjstateSet = new int[1674];
/* 5884:     */   StringBuffer image;
/* 5885:     */   int jjimageLen;
/* 5886:     */   int lengthOfMatch;
/* 5887:     */   protected char curChar;
/* 5888:     */   
/* 5889:     */   public SACParserCSS21TokenManager(CharStream stream)
/* 5890:     */   {
/* 5891:4862 */     this.input_stream = stream;
/* 5892:     */   }
/* 5893:     */   
/* 5894:     */   public SACParserCSS21TokenManager(CharStream stream, int lexState)
/* 5895:     */   {
/* 5896:4865 */     this(stream);
/* 5897:4866 */     SwitchTo(lexState);
/* 5898:     */   }
/* 5899:     */   
/* 5900:     */   public void ReInit(CharStream stream)
/* 5901:     */   {
/* 5902:4870 */     this.jjmatchedPos = (this.jjnewStateCnt = 0);
/* 5903:4871 */     this.curLexState = this.defaultLexState;
/* 5904:4872 */     this.input_stream = stream;
/* 5905:4873 */     ReInitRounds();
/* 5906:     */   }
/* 5907:     */   
/* 5908:     */   private final void ReInitRounds()
/* 5909:     */   {
/* 5910:4878 */     this.jjround = -2147483647;
/* 5911:4879 */     for (int i = 837; i-- > 0;) {
/* 5912:4880 */       this.jjrounds[i] = -2147483648;
/* 5913:     */     }
/* 5914:     */   }
/* 5915:     */   
/* 5916:     */   public void ReInit(CharStream stream, int lexState)
/* 5917:     */   {
/* 5918:4884 */     ReInit(stream);
/* 5919:4885 */     SwitchTo(lexState);
/* 5920:     */   }
/* 5921:     */   
/* 5922:     */   public void SwitchTo(int lexState)
/* 5923:     */   {
/* 5924:4889 */     if ((lexState >= 2) || (lexState < 0)) {
/* 5925:4890 */       throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", 2);
/* 5926:     */     }
/* 5927:4892 */     this.curLexState = lexState;
/* 5928:     */   }
/* 5929:     */   
/* 5930:     */   protected Token jjFillToken()
/* 5931:     */   {
/* 5932:4897 */     Token t = Token.newToken(this.jjmatchedKind);
/* 5933:4898 */     t.kind = this.jjmatchedKind;
/* 5934:4899 */     if (this.jjmatchedPos < 0)
/* 5935:     */     {
/* 5936:4901 */       if (this.image == null) {
/* 5937:4902 */         t.image = "";
/* 5938:     */       } else {
/* 5939:4904 */         t.image = this.image.toString();
/* 5940:     */       }
/* 5941:4905 */       t.beginLine = (t.endLine = this.input_stream.getBeginLine());
/* 5942:4906 */       t.beginColumn = (t.endColumn = this.input_stream.getBeginColumn());
/* 5943:     */     }
/* 5944:     */     else
/* 5945:     */     {
/* 5946:4910 */       String im = jjstrLiteralImages[this.jjmatchedKind];
/* 5947:4911 */       t.image = (im == null ? this.input_stream.GetImage() : im);
/* 5948:4912 */       t.beginLine = this.input_stream.getBeginLine();
/* 5949:4913 */       t.beginColumn = this.input_stream.getBeginColumn();
/* 5950:4914 */       t.endLine = this.input_stream.getEndLine();
/* 5951:4915 */       t.endColumn = this.input_stream.getEndColumn();
/* 5952:     */     }
/* 5953:4917 */     return t;
/* 5954:     */   }
/* 5955:     */   
/* 5956:4920 */   int curLexState = 0;
/* 5957:4921 */   int defaultLexState = 0;
/* 5958:     */   int jjnewStateCnt;
/* 5959:     */   int jjround;
/* 5960:     */   int jjmatchedPos;
/* 5961:     */   int jjmatchedKind;
/* 5962:     */   
/* 5963:     */   public Token getNextToken()
/* 5964:     */   {
/* 5965:4930 */     Token specialToken = null;
/* 5966:     */     
/* 5967:4932 */     int curPos = 0;
/* 5968:     */     try
/* 5969:     */     {
/* 5970:4939 */       this.curChar = this.input_stream.BeginToken();
/* 5971:     */     }
/* 5972:     */     catch (IOException e)
/* 5973:     */     {
/* 5974:4943 */       this.jjmatchedKind = 0;
/* 5975:4944 */       return jjFillToken();
/* 5976:     */     }
/* 5977:4947 */     this.image = null;
/* 5978:4948 */     this.jjimageLen = 0;
/* 5979:     */     for (;;)
/* 5980:     */     {
/* 5981:4952 */       switch (this.curLexState)
/* 5982:     */       {
/* 5983:     */       case 0: 
/* 5984:4955 */         this.jjmatchedKind = 2;
/* 5985:4956 */         this.jjmatchedPos = -1;
/* 5986:4957 */         curPos = 0;
/* 5987:4958 */         curPos = jjMoveStringLiteralDfa0_0();
/* 5988:4959 */         if ((this.jjmatchedPos < 0) || ((this.jjmatchedPos == 0) && (this.jjmatchedKind > 91)))
/* 5989:     */         {
/* 5990:4961 */           this.jjmatchedKind = 91;
/* 5991:4962 */           this.jjmatchedPos = 0;
/* 5992:     */         }
/* 5993:     */         break;
/* 5994:     */       case 1: 
/* 5995:4966 */         this.jjmatchedKind = 2147483647;
/* 5996:4967 */         this.jjmatchedPos = 0;
/* 5997:4968 */         curPos = jjMoveStringLiteralDfa0_1();
/* 5998:4969 */         if ((this.jjmatchedPos == 0) && (this.jjmatchedKind > 5)) {
/* 5999:4971 */           this.jjmatchedKind = 5;
/* 6000:     */         }
/* 6001:     */         break;
/* 6002:     */       }
/* 6003:4975 */       if (this.jjmatchedKind != 2147483647)
/* 6004:     */       {
/* 6005:4977 */         if (this.jjmatchedPos + 1 < curPos) {
/* 6006:4978 */           this.input_stream.backup(curPos - this.jjmatchedPos - 1);
/* 6007:     */         }
/* 6008:4979 */         if ((jjtoToken[(this.jjmatchedKind >> 6)] & 1L << (this.jjmatchedKind & 0x3F)) != 0L)
/* 6009:     */         {
/* 6010:4981 */           Token matchedToken = jjFillToken();
/* 6011:4982 */           TokenLexicalActions(matchedToken);
/* 6012:4983 */           if (jjnewLexState[this.jjmatchedKind] != -1) {
/* 6013:4984 */             this.curLexState = jjnewLexState[this.jjmatchedKind];
/* 6014:     */           }
/* 6015:4985 */           return matchedToken;
/* 6016:     */         }
/* 6017:4987 */         if ((jjtoSkip[(this.jjmatchedKind >> 6)] & 1L << (this.jjmatchedKind & 0x3F)) != 0L)
/* 6018:     */         {
/* 6019:4989 */           if (jjnewLexState[this.jjmatchedKind] == -1) {
/* 6020:     */             break;
/* 6021:     */           }
/* 6022:4990 */           this.curLexState = jjnewLexState[this.jjmatchedKind]; break;
/* 6023:     */         }
/* 6024:4993 */         this.jjimageLen += this.jjmatchedPos + 1;
/* 6025:4994 */         if (jjnewLexState[this.jjmatchedKind] != -1) {
/* 6026:4995 */           this.curLexState = jjnewLexState[this.jjmatchedKind];
/* 6027:     */         }
/* 6028:4996 */         curPos = 0;
/* 6029:4997 */         this.jjmatchedKind = 2147483647;
/* 6030:     */         try
/* 6031:     */         {
/* 6032:4999 */           this.curChar = this.input_stream.readChar();
/* 6033:     */         }
/* 6034:     */         catch (IOException e1) {}
/* 6035:     */       }
/* 6036:     */     }
/* 6037:5004 */     int error_line = this.input_stream.getEndLine();
/* 6038:5005 */     int error_column = this.input_stream.getEndColumn();
/* 6039:5006 */     String error_after = null;
/* 6040:5007 */     boolean EOFSeen = false;
/* 6041:     */     try
/* 6042:     */     {
/* 6043:5008 */       this.input_stream.readChar();this.input_stream.backup(1);
/* 6044:     */     }
/* 6045:     */     catch (IOException e1)
/* 6046:     */     {
/* 6047:5010 */       EOFSeen = true;
/* 6048:5011 */       error_after = curPos <= 1 ? "" : this.input_stream.GetImage();
/* 6049:5012 */       if ((this.curChar == '\n') || (this.curChar == '\r'))
/* 6050:     */       {
/* 6051:5013 */         error_line++;
/* 6052:5014 */         error_column = 0;
/* 6053:     */       }
/* 6054:     */       else
/* 6055:     */       {
/* 6056:5017 */         error_column++;
/* 6057:     */       }
/* 6058:     */     }
/* 6059:5019 */     if (!EOFSeen)
/* 6060:     */     {
/* 6061:5020 */       this.input_stream.backup(1);
/* 6062:5021 */       error_after = curPos <= 1 ? "" : this.input_stream.GetImage();
/* 6063:     */     }
/* 6064:5023 */     throw new TokenMgrError(EOFSeen, this.curLexState, error_line, error_column, error_after, this.curChar, 0);
/* 6065:     */   }
/* 6066:     */   
/* 6067:     */   void TokenLexicalActions(Token matchedToken)
/* 6068:     */   {
/* 6069:5030 */     switch (this.jjmatchedKind)
/* 6070:     */     {
/* 6071:     */     case 21: 
/* 6072:5033 */       if (this.image == null) {
/* 6073:5034 */         this.image = new StringBuffer();
/* 6074:     */       }
/* 6075:5035 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 6076:5036 */       matchedToken.image = trimBy(this.image, 1, 1);
/* 6077:5037 */       break;
/* 6078:     */     case 24: 
/* 6079:5039 */       if (this.image == null) {
/* 6080:5040 */         this.image = new StringBuffer();
/* 6081:     */       }
/* 6082:5041 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 6083:5042 */       matchedToken.image = trimUrl(this.image);
/* 6084:5043 */       break;
/* 6085:     */     case 37: 
/* 6086:5045 */       if (this.image == null) {
/* 6087:5046 */         this.image = new StringBuffer();
/* 6088:     */       }
/* 6089:5047 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 6090:5048 */       matchedToken.image = splitNumber(this.image);
/* 6091:5049 */       break;
/* 6092:     */     case 38: 
/* 6093:5051 */       if (this.image == null) {
/* 6094:5052 */         this.image = new StringBuffer();
/* 6095:     */       }
/* 6096:5053 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 6097:5054 */       matchedToken.image = splitNumber(this.image);
/* 6098:5055 */       break;
/* 6099:     */     case 39: 
/* 6100:5057 */       if (this.image == null) {
/* 6101:5058 */         this.image = new StringBuffer();
/* 6102:     */       }
/* 6103:5059 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 6104:5060 */       matchedToken.image = splitNumber(this.image);
/* 6105:5061 */       break;
/* 6106:     */     case 40: 
/* 6107:5063 */       if (this.image == null) {
/* 6108:5064 */         this.image = new StringBuffer();
/* 6109:     */       }
/* 6110:5065 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 6111:5066 */       matchedToken.image = splitNumber(this.image);
/* 6112:5067 */       break;
/* 6113:     */     case 41: 
/* 6114:5069 */       if (this.image == null) {
/* 6115:5070 */         this.image = new StringBuffer();
/* 6116:     */       }
/* 6117:5071 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 6118:5072 */       matchedToken.image = splitNumber(this.image);
/* 6119:5073 */       break;
/* 6120:     */     case 42: 
/* 6121:5075 */       if (this.image == null) {
/* 6122:5076 */         this.image = new StringBuffer();
/* 6123:     */       }
/* 6124:5077 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 6125:5078 */       matchedToken.image = splitNumber(this.image);
/* 6126:5079 */       break;
/* 6127:     */     case 43: 
/* 6128:5081 */       if (this.image == null) {
/* 6129:5082 */         this.image = new StringBuffer();
/* 6130:     */       }
/* 6131:5083 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 6132:5084 */       matchedToken.image = splitNumber(this.image);
/* 6133:5085 */       break;
/* 6134:     */     case 44: 
/* 6135:5087 */       if (this.image == null) {
/* 6136:5088 */         this.image = new StringBuffer();
/* 6137:     */       }
/* 6138:5089 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 6139:5090 */       matchedToken.image = splitNumber(this.image);
/* 6140:5091 */       break;
/* 6141:     */     case 45: 
/* 6142:5093 */       if (this.image == null) {
/* 6143:5094 */         this.image = new StringBuffer();
/* 6144:     */       }
/* 6145:5095 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 6146:5096 */       matchedToken.image = splitNumber(this.image);
/* 6147:5097 */       break;
/* 6148:     */     case 46: 
/* 6149:5099 */       if (this.image == null) {
/* 6150:5100 */         this.image = new StringBuffer();
/* 6151:     */       }
/* 6152:5101 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 6153:5102 */       matchedToken.image = splitNumber(this.image);
/* 6154:5103 */       break;
/* 6155:     */     case 47: 
/* 6156:5105 */       if (this.image == null) {
/* 6157:5106 */         this.image = new StringBuffer();
/* 6158:     */       }
/* 6159:5107 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 6160:5108 */       matchedToken.image = splitNumber(this.image);
/* 6161:5109 */       break;
/* 6162:     */     case 48: 
/* 6163:5111 */       if (this.image == null) {
/* 6164:5112 */         this.image = new StringBuffer();
/* 6165:     */       }
/* 6166:5113 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 6167:5114 */       matchedToken.image = splitNumber(this.image);
/* 6168:5115 */       break;
/* 6169:     */     case 49: 
/* 6170:5117 */       if (this.image == null) {
/* 6171:5118 */         this.image = new StringBuffer();
/* 6172:     */       }
/* 6173:5119 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 6174:5120 */       matchedToken.image = splitNumber(this.image);
/* 6175:5121 */       break;
/* 6176:     */     case 50: 
/* 6177:5123 */       if (this.image == null) {
/* 6178:5124 */         this.image = new StringBuffer();
/* 6179:     */       }
/* 6180:5125 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 6181:5126 */       matchedToken.image = splitNumber(this.image);
/* 6182:5127 */       break;
/* 6183:     */     case 51: 
/* 6184:5129 */       if (this.image == null) {
/* 6185:5130 */         this.image = new StringBuffer();
/* 6186:     */       }
/* 6187:5131 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 6188:5132 */       matchedToken.image = splitNumber(this.image);
/* 6189:5133 */       break;
/* 6190:     */     case 53: 
/* 6191:5135 */       if (this.image == null) {
/* 6192:5136 */         this.image = new StringBuffer();
/* 6193:     */       }
/* 6194:5137 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 6195:5138 */       matchedToken.image = splitNumber(this.image);
/* 6196:5139 */       break;
/* 6197:     */     case 91: 
/* 6198:5141 */       if (this.image == null) {
/* 6199:5142 */         this.image = new StringBuffer();
/* 6200:     */       }
/* 6201:5143 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 6202:5144 */       if (!this._quiet) {
/* 6203:5145 */         System.err.println("Illegal character : " + this.image.toString());
/* 6204:     */       }
/* 6205:     */       break;
/* 6206:     */     }
/* 6207:     */   }
/* 6208:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.steadystate.css.parser.SACParserCSS21TokenManager
 * JD-Core Version:    0.7.0.1
 */