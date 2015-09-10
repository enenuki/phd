/*    1:     */ package com.steadystate.css.parser;
/*    2:     */ 
/*    3:     */ import java.io.IOException;
/*    4:     */ import java.io.PrintStream;
/*    5:     */ import org.w3c.css.sac.ErrorHandler;
/*    6:     */ 
/*    7:     */ public class SACParserCSSmobileOKBasic1TokenManager
/*    8:     */   implements SACParserCSSmobileOKBasic1Constants
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
/*   44:  41 */       if ((active0 & 0x2000) != 0L) {
/*   45:  42 */         return 317;
/*   46:     */       }
/*   47:  43 */       if ((active0 & 0x0) != 0L)
/*   48:     */       {
/*   49:  45 */         this.jjmatchedKind = 3;
/*   50:  46 */         return 318;
/*   51:     */       }
/*   52:  48 */       if ((active0 & 0x60000000) != 0L) {
/*   53:  49 */         return 89;
/*   54:     */       }
/*   55:  50 */       return -1;
/*   56:     */     case 1: 
/*   57:  52 */       if ((active0 & 0x60000000) != 0L)
/*   58:     */       {
/*   59:  54 */         this.jjmatchedKind = 32;
/*   60:  55 */         this.jjmatchedPos = 1;
/*   61:  56 */         return 319;
/*   62:     */       }
/*   63:  58 */       if ((active0 & 0x0) != 0L)
/*   64:     */       {
/*   65:  60 */         this.jjmatchedKind = 3;
/*   66:  61 */         this.jjmatchedPos = 1;
/*   67:  62 */         return 318;
/*   68:     */       }
/*   69:  64 */       return -1;
/*   70:     */     case 2: 
/*   71:  66 */       if ((active0 & 0x0) != 0L)
/*   72:     */       {
/*   73:  68 */         this.jjmatchedKind = 3;
/*   74:  69 */         this.jjmatchedPos = 2;
/*   75:  70 */         return 318;
/*   76:     */       }
/*   77:  72 */       if ((active0 & 0x60000000) != 0L)
/*   78:     */       {
/*   79:  74 */         this.jjmatchedKind = 32;
/*   80:  75 */         this.jjmatchedPos = 2;
/*   81:  76 */         return 319;
/*   82:     */       }
/*   83:  78 */       return -1;
/*   84:     */     case 3: 
/*   85:  80 */       if ((active0 & 0x60000000) != 0L)
/*   86:     */       {
/*   87:  82 */         this.jjmatchedKind = 32;
/*   88:  83 */         this.jjmatchedPos = 3;
/*   89:  84 */         return 319;
/*   90:     */       }
/*   91:  86 */       return -1;
/*   92:     */     case 4: 
/*   93:  88 */       if ((active0 & 0x60000000) != 0L)
/*   94:     */       {
/*   95:  90 */         this.jjmatchedKind = 32;
/*   96:  91 */         this.jjmatchedPos = 4;
/*   97:  92 */         return 319;
/*   98:     */       }
/*   99:  94 */       return -1;
/*  100:     */     case 5: 
/*  101:  96 */       if ((active0 & 0x20000000) != 0L)
/*  102:     */       {
/*  103:  98 */         this.jjmatchedKind = 32;
/*  104:  99 */         this.jjmatchedPos = 5;
/*  105: 100 */         return 319;
/*  106:     */       }
/*  107: 102 */       if ((active0 & 0x40000000) != 0L) {
/*  108: 103 */         return 319;
/*  109:     */       }
/*  110: 104 */       return -1;
/*  111:     */     case 6: 
/*  112: 106 */       if ((active0 & 0x20000000) != 0L) {
/*  113: 107 */         return 319;
/*  114:     */       }
/*  115: 108 */       return -1;
/*  116:     */     }
/*  117: 110 */     return -1;
/*  118:     */   }
/*  119:     */   
/*  120:     */   private final int jjStartNfa_0(int pos, long active0)
/*  121:     */   {
/*  122: 115 */     return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0), pos + 1);
/*  123:     */   }
/*  124:     */   
/*  125:     */   private final int jjStopAtPos(int pos, int kind)
/*  126:     */   {
/*  127: 119 */     this.jjmatchedKind = kind;
/*  128: 120 */     this.jjmatchedPos = pos;
/*  129: 121 */     return pos + 1;
/*  130:     */   }
/*  131:     */   
/*  132:     */   private final int jjStartNfaWithStates_0(int pos, int kind, int state)
/*  133:     */   {
/*  134: 125 */     this.jjmatchedKind = kind;
/*  135: 126 */     this.jjmatchedPos = pos;
/*  136:     */     try
/*  137:     */     {
/*  138: 127 */       this.curChar = this.input_stream.readChar();
/*  139:     */     }
/*  140:     */     catch (IOException e)
/*  141:     */     {
/*  142: 128 */       return pos + 1;
/*  143:     */     }
/*  144: 129 */     return jjMoveNfa_0(state, pos + 1);
/*  145:     */   }
/*  146:     */   
/*  147:     */   private final int jjMoveStringLiteralDfa0_0()
/*  148:     */   {
/*  149: 133 */     switch (this.curChar)
/*  150:     */     {
/*  151:     */     case ')': 
/*  152: 136 */       return jjStopAtPos(0, 25);
/*  153:     */     case '*': 
/*  154: 138 */       return jjStopAtPos(0, 16);
/*  155:     */     case '+': 
/*  156: 140 */       return jjStopAtPos(0, 18);
/*  157:     */     case ',': 
/*  158: 142 */       return jjStopAtPos(0, 12);
/*  159:     */     case '-': 
/*  160: 144 */       this.jjmatchedKind = 19;
/*  161: 145 */       return jjMoveStringLiteralDfa1_0(268435456L);
/*  162:     */     case '.': 
/*  163: 147 */       return jjStartNfaWithStates_0(0, 13, 317);
/*  164:     */     case '/': 
/*  165: 149 */       this.jjmatchedKind = 17;
/*  166: 150 */       return jjMoveStringLiteralDfa1_0(4L);
/*  167:     */     case ':': 
/*  168: 152 */       this.jjmatchedKind = 15;
/*  169: 153 */       return jjMoveStringLiteralDfa1_0(496L);
/*  170:     */     case ';': 
/*  171: 155 */       return jjStopAtPos(0, 14);
/*  172:     */     case '<': 
/*  173: 157 */       return jjMoveStringLiteralDfa1_0(134217728L);
/*  174:     */     case '=': 
/*  175: 159 */       return jjStopAtPos(0, 20);
/*  176:     */     case '>': 
/*  177: 161 */       return jjStopAtPos(0, 21);
/*  178:     */     case '@': 
/*  179: 163 */       return jjMoveStringLiteralDfa1_0(1610612736L);
/*  180:     */     case '[': 
/*  181: 165 */       return jjStopAtPos(0, 22);
/*  182:     */     case ']': 
/*  183: 167 */       return jjStopAtPos(0, 23);
/*  184:     */     case 'R': 
/*  185:     */     case 'r': 
/*  186: 170 */       return jjMoveStringLiteralDfa1_0(8796093022208L);
/*  187:     */     case '{': 
/*  188: 172 */       return jjStopAtPos(0, 10);
/*  189:     */     case '}': 
/*  190: 174 */       return jjStopAtPos(0, 11);
/*  191:     */     }
/*  192: 176 */     return jjMoveNfa_0(1, 0);
/*  193:     */   }
/*  194:     */   
/*  195:     */   private final int jjMoveStringLiteralDfa1_0(long active0)
/*  196:     */   {
/*  197:     */     try
/*  198:     */     {
/*  199: 181 */       this.curChar = this.input_stream.readChar();
/*  200:     */     }
/*  201:     */     catch (IOException e)
/*  202:     */     {
/*  203: 183 */       jjStopStringLiteralDfa_0(0, active0);
/*  204: 184 */       return 1;
/*  205:     */     }
/*  206: 186 */     switch (this.curChar)
/*  207:     */     {
/*  208:     */     case '!': 
/*  209: 189 */       return jjMoveStringLiteralDfa2_0(active0, 134217728L);
/*  210:     */     case '*': 
/*  211: 191 */       if ((active0 & 0x4) != 0L) {
/*  212: 192 */         return jjStopAtPos(1, 2);
/*  213:     */       }
/*  214:     */       break;
/*  215:     */     case '-': 
/*  216: 195 */       return jjMoveStringLiteralDfa2_0(active0, 268435456L);
/*  217:     */     case 'A': 
/*  218:     */     case 'a': 
/*  219: 198 */       return jjMoveStringLiteralDfa2_0(active0, 64L);
/*  220:     */     case 'F': 
/*  221:     */     case 'f': 
/*  222: 201 */       return jjMoveStringLiteralDfa2_0(active0, 384L);
/*  223:     */     case 'G': 
/*  224:     */     case 'g': 
/*  225: 204 */       return jjMoveStringLiteralDfa2_0(active0, 8796093022208L);
/*  226:     */     case 'I': 
/*  227:     */     case 'i': 
/*  228: 207 */       return jjMoveStringLiteralDfa2_0(active0, 536870912L);
/*  229:     */     case 'L': 
/*  230:     */     case 'l': 
/*  231: 210 */       return jjMoveStringLiteralDfa2_0(active0, 16L);
/*  232:     */     case 'M': 
/*  233:     */     case 'm': 
/*  234: 213 */       return jjMoveStringLiteralDfa2_0(active0, 1073741824L);
/*  235:     */     case 'V': 
/*  236:     */     case 'v': 
/*  237: 216 */       return jjMoveStringLiteralDfa2_0(active0, 32L);
/*  238:     */     }
/*  239: 220 */     return jjStartNfa_0(0, active0);
/*  240:     */   }
/*  241:     */   
/*  242:     */   private final int jjMoveStringLiteralDfa2_0(long old0, long active0)
/*  243:     */   {
/*  244: 224 */     if ((active0 &= old0) == 0L) {
/*  245: 225 */       return jjStartNfa_0(0, old0);
/*  246:     */     }
/*  247:     */     try
/*  248:     */     {
/*  249: 226 */       this.curChar = this.input_stream.readChar();
/*  250:     */     }
/*  251:     */     catch (IOException e)
/*  252:     */     {
/*  253: 228 */       jjStopStringLiteralDfa_0(1, active0);
/*  254: 229 */       return 2;
/*  255:     */     }
/*  256: 231 */     switch (this.curChar)
/*  257:     */     {
/*  258:     */     case '-': 
/*  259: 234 */       return jjMoveStringLiteralDfa3_0(active0, 134217728L);
/*  260:     */     case '>': 
/*  261: 236 */       if ((active0 & 0x10000000) != 0L) {
/*  262: 237 */         return jjStopAtPos(2, 28);
/*  263:     */       }
/*  264:     */       break;
/*  265:     */     case 'B': 
/*  266:     */     case 'b': 
/*  267: 241 */       return jjMoveStringLiteralDfa3_0(active0, 8796093022208L);
/*  268:     */     case 'C': 
/*  269:     */     case 'c': 
/*  270: 244 */       return jjMoveStringLiteralDfa3_0(active0, 64L);
/*  271:     */     case 'E': 
/*  272:     */     case 'e': 
/*  273: 247 */       return jjMoveStringLiteralDfa3_0(active0, 1073741824L);
/*  274:     */     case 'I': 
/*  275:     */     case 'i': 
/*  276: 250 */       return jjMoveStringLiteralDfa3_0(active0, 432L);
/*  277:     */     case 'M': 
/*  278:     */     case 'm': 
/*  279: 253 */       return jjMoveStringLiteralDfa3_0(active0, 536870912L);
/*  280:     */     }
/*  281: 257 */     return jjStartNfa_0(1, active0);
/*  282:     */   }
/*  283:     */   
/*  284:     */   private final int jjMoveStringLiteralDfa3_0(long old0, long active0)
/*  285:     */   {
/*  286: 261 */     if ((active0 &= old0) == 0L) {
/*  287: 262 */       return jjStartNfa_0(1, old0);
/*  288:     */     }
/*  289:     */     try
/*  290:     */     {
/*  291: 263 */       this.curChar = this.input_stream.readChar();
/*  292:     */     }
/*  293:     */     catch (IOException e)
/*  294:     */     {
/*  295: 265 */       jjStopStringLiteralDfa_0(2, active0);
/*  296: 266 */       return 3;
/*  297:     */     }
/*  298: 268 */     switch (this.curChar)
/*  299:     */     {
/*  300:     */     case '(': 
/*  301: 271 */       if ((active0 & 0x0) != 0L) {
/*  302: 272 */         return jjStopAtPos(3, 43);
/*  303:     */       }
/*  304:     */       break;
/*  305:     */     case '-': 
/*  306: 275 */       if ((active0 & 0x8000000) != 0L) {
/*  307: 276 */         return jjStopAtPos(3, 27);
/*  308:     */       }
/*  309:     */       break;
/*  310:     */     case 'D': 
/*  311:     */     case 'd': 
/*  312: 280 */       return jjMoveStringLiteralDfa4_0(active0, 1073741824L);
/*  313:     */     case 'N': 
/*  314:     */     case 'n': 
/*  315: 283 */       return jjMoveStringLiteralDfa4_0(active0, 16L);
/*  316:     */     case 'P': 
/*  317:     */     case 'p': 
/*  318: 286 */       return jjMoveStringLiteralDfa4_0(active0, 536870912L);
/*  319:     */     case 'R': 
/*  320:     */     case 'r': 
/*  321: 289 */       return jjMoveStringLiteralDfa4_0(active0, 384L);
/*  322:     */     case 'S': 
/*  323:     */     case 's': 
/*  324: 292 */       return jjMoveStringLiteralDfa4_0(active0, 32L);
/*  325:     */     case 'T': 
/*  326:     */     case 't': 
/*  327: 295 */       return jjMoveStringLiteralDfa4_0(active0, 64L);
/*  328:     */     }
/*  329: 299 */     return jjStartNfa_0(2, active0);
/*  330:     */   }
/*  331:     */   
/*  332:     */   private final int jjMoveStringLiteralDfa4_0(long old0, long active0)
/*  333:     */   {
/*  334: 303 */     if ((active0 &= old0) == 0L) {
/*  335: 304 */       return jjStartNfa_0(2, old0);
/*  336:     */     }
/*  337:     */     try
/*  338:     */     {
/*  339: 305 */       this.curChar = this.input_stream.readChar();
/*  340:     */     }
/*  341:     */     catch (IOException e)
/*  342:     */     {
/*  343: 307 */       jjStopStringLiteralDfa_0(3, active0);
/*  344: 308 */       return 4;
/*  345:     */     }
/*  346: 310 */     switch (this.curChar)
/*  347:     */     {
/*  348:     */     case 'I': 
/*  349:     */     case 'i': 
/*  350: 314 */       return jjMoveStringLiteralDfa5_0(active0, 1073741920L);
/*  351:     */     case 'K': 
/*  352:     */     case 'k': 
/*  353: 317 */       if ((active0 & 0x10) != 0L) {
/*  354: 318 */         return jjStopAtPos(4, 4);
/*  355:     */       }
/*  356:     */       break;
/*  357:     */     case 'O': 
/*  358:     */     case 'o': 
/*  359: 322 */       return jjMoveStringLiteralDfa5_0(active0, 536870912L);
/*  360:     */     case 'S': 
/*  361:     */     case 's': 
/*  362: 325 */       return jjMoveStringLiteralDfa5_0(active0, 384L);
/*  363:     */     }
/*  364: 329 */     return jjStartNfa_0(3, active0);
/*  365:     */   }
/*  366:     */   
/*  367:     */   private final int jjMoveStringLiteralDfa5_0(long old0, long active0)
/*  368:     */   {
/*  369: 333 */     if ((active0 &= old0) == 0L) {
/*  370: 334 */       return jjStartNfa_0(3, old0);
/*  371:     */     }
/*  372:     */     try
/*  373:     */     {
/*  374: 335 */       this.curChar = this.input_stream.readChar();
/*  375:     */     }
/*  376:     */     catch (IOException e)
/*  377:     */     {
/*  378: 337 */       jjStopStringLiteralDfa_0(4, active0);
/*  379: 338 */       return 5;
/*  380:     */     }
/*  381: 340 */     switch (this.curChar)
/*  382:     */     {
/*  383:     */     case 'A': 
/*  384:     */     case 'a': 
/*  385: 344 */       if ((active0 & 0x40000000) != 0L) {
/*  386: 345 */         return jjStartNfaWithStates_0(5, 30, 319);
/*  387:     */       }
/*  388:     */       break;
/*  389:     */     case 'R': 
/*  390:     */     case 'r': 
/*  391: 349 */       return jjMoveStringLiteralDfa6_0(active0, 536870912L);
/*  392:     */     case 'T': 
/*  393:     */     case 't': 
/*  394: 352 */       return jjMoveStringLiteralDfa6_0(active0, 416L);
/*  395:     */     case 'V': 
/*  396:     */     case 'v': 
/*  397: 355 */       return jjMoveStringLiteralDfa6_0(active0, 64L);
/*  398:     */     }
/*  399: 359 */     return jjStartNfa_0(4, active0);
/*  400:     */   }
/*  401:     */   
/*  402:     */   private final int jjMoveStringLiteralDfa6_0(long old0, long active0)
/*  403:     */   {
/*  404: 363 */     if ((active0 &= old0) == 0L) {
/*  405: 364 */       return jjStartNfa_0(4, old0);
/*  406:     */     }
/*  407:     */     try
/*  408:     */     {
/*  409: 365 */       this.curChar = this.input_stream.readChar();
/*  410:     */     }
/*  411:     */     catch (IOException e)
/*  412:     */     {
/*  413: 367 */       jjStopStringLiteralDfa_0(5, active0);
/*  414: 368 */       return 6;
/*  415:     */     }
/*  416: 370 */     switch (this.curChar)
/*  417:     */     {
/*  418:     */     case '-': 
/*  419: 373 */       return jjMoveStringLiteralDfa7_0(active0, 384L);
/*  420:     */     case 'E': 
/*  421:     */     case 'e': 
/*  422: 376 */       if ((active0 & 0x40) != 0L) {
/*  423: 377 */         return jjStopAtPos(6, 6);
/*  424:     */       }
/*  425: 378 */       return jjMoveStringLiteralDfa7_0(active0, 32L);
/*  426:     */     case 'T': 
/*  427:     */     case 't': 
/*  428: 381 */       if ((active0 & 0x20000000) != 0L) {
/*  429: 382 */         return jjStartNfaWithStates_0(6, 29, 319);
/*  430:     */       }
/*  431:     */       break;
/*  432:     */     }
/*  433: 387 */     return jjStartNfa_0(5, active0);
/*  434:     */   }
/*  435:     */   
/*  436:     */   private final int jjMoveStringLiteralDfa7_0(long old0, long active0)
/*  437:     */   {
/*  438: 391 */     if ((active0 &= old0) == 0L) {
/*  439: 392 */       return jjStartNfa_0(5, old0);
/*  440:     */     }
/*  441:     */     try
/*  442:     */     {
/*  443: 393 */       this.curChar = this.input_stream.readChar();
/*  444:     */     }
/*  445:     */     catch (IOException e)
/*  446:     */     {
/*  447: 395 */       jjStopStringLiteralDfa_0(6, active0);
/*  448: 396 */       return 7;
/*  449:     */     }
/*  450: 398 */     switch (this.curChar)
/*  451:     */     {
/*  452:     */     case 'D': 
/*  453:     */     case 'd': 
/*  454: 402 */       if ((active0 & 0x20) != 0L) {
/*  455: 403 */         return jjStopAtPos(7, 5);
/*  456:     */       }
/*  457:     */       break;
/*  458:     */     case 'L': 
/*  459:     */     case 'l': 
/*  460: 407 */       return jjMoveStringLiteralDfa8_0(active0, 384L);
/*  461:     */     }
/*  462: 411 */     return jjStartNfa_0(6, active0);
/*  463:     */   }
/*  464:     */   
/*  465:     */   private final int jjMoveStringLiteralDfa8_0(long old0, long active0)
/*  466:     */   {
/*  467: 415 */     if ((active0 &= old0) == 0L) {
/*  468: 416 */       return jjStartNfa_0(6, old0);
/*  469:     */     }
/*  470:     */     try
/*  471:     */     {
/*  472: 417 */       this.curChar = this.input_stream.readChar();
/*  473:     */     }
/*  474:     */     catch (IOException e)
/*  475:     */     {
/*  476: 419 */       jjStopStringLiteralDfa_0(7, active0);
/*  477: 420 */       return 8;
/*  478:     */     }
/*  479: 422 */     switch (this.curChar)
/*  480:     */     {
/*  481:     */     case 'E': 
/*  482:     */     case 'e': 
/*  483: 426 */       return jjMoveStringLiteralDfa9_0(active0, 256L);
/*  484:     */     case 'I': 
/*  485:     */     case 'i': 
/*  486: 429 */       return jjMoveStringLiteralDfa9_0(active0, 128L);
/*  487:     */     }
/*  488: 433 */     return jjStartNfa_0(7, active0);
/*  489:     */   }
/*  490:     */   
/*  491:     */   private final int jjMoveStringLiteralDfa9_0(long old0, long active0)
/*  492:     */   {
/*  493: 437 */     if ((active0 &= old0) == 0L) {
/*  494: 438 */       return jjStartNfa_0(7, old0);
/*  495:     */     }
/*  496:     */     try
/*  497:     */     {
/*  498: 439 */       this.curChar = this.input_stream.readChar();
/*  499:     */     }
/*  500:     */     catch (IOException e)
/*  501:     */     {
/*  502: 441 */       jjStopStringLiteralDfa_0(8, active0);
/*  503: 442 */       return 9;
/*  504:     */     }
/*  505: 444 */     switch (this.curChar)
/*  506:     */     {
/*  507:     */     case 'N': 
/*  508:     */     case 'n': 
/*  509: 448 */       return jjMoveStringLiteralDfa10_0(active0, 128L);
/*  510:     */     case 'T': 
/*  511:     */     case 't': 
/*  512: 451 */       return jjMoveStringLiteralDfa10_0(active0, 256L);
/*  513:     */     }
/*  514: 455 */     return jjStartNfa_0(8, active0);
/*  515:     */   }
/*  516:     */   
/*  517:     */   private final int jjMoveStringLiteralDfa10_0(long old0, long active0)
/*  518:     */   {
/*  519: 459 */     if ((active0 &= old0) == 0L) {
/*  520: 460 */       return jjStartNfa_0(8, old0);
/*  521:     */     }
/*  522:     */     try
/*  523:     */     {
/*  524: 461 */       this.curChar = this.input_stream.readChar();
/*  525:     */     }
/*  526:     */     catch (IOException e)
/*  527:     */     {
/*  528: 463 */       jjStopStringLiteralDfa_0(9, active0);
/*  529: 464 */       return 10;
/*  530:     */     }
/*  531: 466 */     switch (this.curChar)
/*  532:     */     {
/*  533:     */     case 'E': 
/*  534:     */     case 'e': 
/*  535: 470 */       if ((active0 & 0x80) != 0L) {
/*  536: 471 */         return jjStopAtPos(10, 7);
/*  537:     */       }
/*  538:     */       break;
/*  539:     */     case 'T': 
/*  540:     */     case 't': 
/*  541: 475 */       return jjMoveStringLiteralDfa11_0(active0, 256L);
/*  542:     */     }
/*  543: 479 */     return jjStartNfa_0(9, active0);
/*  544:     */   }
/*  545:     */   
/*  546:     */   private final int jjMoveStringLiteralDfa11_0(long old0, long active0)
/*  547:     */   {
/*  548: 483 */     if ((active0 &= old0) == 0L) {
/*  549: 484 */       return jjStartNfa_0(9, old0);
/*  550:     */     }
/*  551:     */     try
/*  552:     */     {
/*  553: 485 */       this.curChar = this.input_stream.readChar();
/*  554:     */     }
/*  555:     */     catch (IOException e)
/*  556:     */     {
/*  557: 487 */       jjStopStringLiteralDfa_0(10, active0);
/*  558: 488 */       return 11;
/*  559:     */     }
/*  560: 490 */     switch (this.curChar)
/*  561:     */     {
/*  562:     */     case 'E': 
/*  563:     */     case 'e': 
/*  564: 494 */       return jjMoveStringLiteralDfa12_0(active0, 256L);
/*  565:     */     }
/*  566: 498 */     return jjStartNfa_0(10, active0);
/*  567:     */   }
/*  568:     */   
/*  569:     */   private final int jjMoveStringLiteralDfa12_0(long old0, long active0)
/*  570:     */   {
/*  571: 502 */     if ((active0 &= old0) == 0L) {
/*  572: 503 */       return jjStartNfa_0(10, old0);
/*  573:     */     }
/*  574:     */     try
/*  575:     */     {
/*  576: 504 */       this.curChar = this.input_stream.readChar();
/*  577:     */     }
/*  578:     */     catch (IOException e)
/*  579:     */     {
/*  580: 506 */       jjStopStringLiteralDfa_0(11, active0);
/*  581: 507 */       return 12;
/*  582:     */     }
/*  583: 509 */     switch (this.curChar)
/*  584:     */     {
/*  585:     */     case 'R': 
/*  586:     */     case 'r': 
/*  587: 513 */       if ((active0 & 0x100) != 0L) {
/*  588: 514 */         return jjStopAtPos(12, 8);
/*  589:     */       }
/*  590:     */       break;
/*  591:     */     }
/*  592: 519 */     return jjStartNfa_0(11, active0);
/*  593:     */   }
/*  594:     */   
/*  595:     */   private final void jjCheckNAdd(int state)
/*  596:     */   {
/*  597: 523 */     if (this.jjrounds[state] != this.jjround)
/*  598:     */     {
/*  599: 525 */       this.jjstateSet[(this.jjnewStateCnt++)] = state;
/*  600: 526 */       this.jjrounds[state] = this.jjround;
/*  601:     */     }
/*  602:     */   }
/*  603:     */   
/*  604:     */   private final void jjAddStates(int start, int end)
/*  605:     */   {
/*  606:     */     do
/*  607:     */     {
/*  608: 532 */       this.jjstateSet[(this.jjnewStateCnt++)] = jjnextStates[start];
/*  609: 533 */     } while (start++ != end);
/*  610:     */   }
/*  611:     */   
/*  612:     */   private final void jjCheckNAddTwoStates(int state1, int state2)
/*  613:     */   {
/*  614: 537 */     jjCheckNAdd(state1);
/*  615: 538 */     jjCheckNAdd(state2);
/*  616:     */   }
/*  617:     */   
/*  618:     */   private final void jjCheckNAddStates(int start, int end)
/*  619:     */   {
/*  620:     */     do
/*  621:     */     {
/*  622: 543 */       jjCheckNAdd(jjnextStates[start]);
/*  623: 544 */     } while (start++ != end);
/*  624:     */   }
/*  625:     */   
/*  626:     */   private final void jjCheckNAddStates(int start)
/*  627:     */   {
/*  628: 548 */     jjCheckNAdd(jjnextStates[start]);
/*  629: 549 */     jjCheckNAdd(jjnextStates[(start + 1)]);
/*  630:     */   }
/*  631:     */   
/*  632: 551 */   static final long[] jjbitVec0 = { -2L, -1L, -1L, -1L };
/*  633: 554 */   static final long[] jjbitVec2 = { 0L, 0L, -1L, -1L };
/*  634:     */   
/*  635:     */   private final int jjMoveNfa_0(int startState, int curPos)
/*  636:     */   {
/*  637: 560 */     int startsAt = 0;
/*  638: 561 */     this.jjnewStateCnt = 317;
/*  639: 562 */     int i = 1;
/*  640: 563 */     this.jjstateSet[0] = startState;
/*  641: 564 */     int kind = 2147483647;
/*  642:     */     for (;;)
/*  643:     */     {
/*  644: 567 */       if (++this.jjround == 2147483647) {
/*  645: 568 */         ReInitRounds();
/*  646:     */       }
/*  647: 569 */       if (this.curChar < '@')
/*  648:     */       {
/*  649: 571 */         long l = 1L << this.curChar;
/*  650:     */         do
/*  651:     */         {
/*  652: 574 */           switch (this.jjstateSet[(--i)])
/*  653:     */           {
/*  654:     */           case 317: 
/*  655: 577 */             if ((0x0 & l) != 0L)
/*  656:     */             {
/*  657: 579 */               if (kind > 46) {
/*  658: 580 */                 kind = 46;
/*  659:     */               }
/*  660: 581 */               jjCheckNAdd(282);
/*  661:     */             }
/*  662: 583 */             if ((0x0 & l) != 0L)
/*  663:     */             {
/*  664: 585 */               if (kind > 42) {
/*  665: 586 */                 kind = 42;
/*  666:     */               }
/*  667: 587 */               jjCheckNAdd(281);
/*  668:     */             }
/*  669: 589 */             if ((0x0 & l) != 0L) {
/*  670: 590 */               jjCheckNAddTwoStates(279, 280);
/*  671:     */             }
/*  672: 591 */             if ((0x0 & l) != 0L) {
/*  673: 592 */               jjCheckNAddTwoStates(276, 278);
/*  674:     */             }
/*  675: 593 */             if ((0x0 & l) != 0L) {
/*  676: 594 */               jjCheckNAddTwoStates(273, 275);
/*  677:     */             }
/*  678: 595 */             if ((0x0 & l) != 0L) {
/*  679: 596 */               jjCheckNAddTwoStates(270, 272);
/*  680:     */             }
/*  681: 597 */             if ((0x0 & l) != 0L) {
/*  682: 598 */               jjCheckNAddTwoStates(267, 269);
/*  683:     */             }
/*  684: 599 */             if ((0x0 & l) != 0L) {
/*  685: 600 */               jjCheckNAddTwoStates(264, 266);
/*  686:     */             }
/*  687: 601 */             if ((0x0 & l) != 0L) {
/*  688: 602 */               jjCheckNAddTwoStates(261, 263);
/*  689:     */             }
/*  690: 603 */             if ((0x0 & l) != 0L) {
/*  691: 604 */               jjCheckNAddTwoStates(258, 260);
/*  692:     */             }
/*  693: 605 */             if ((0x0 & l) != 0L) {
/*  694: 606 */               jjCheckNAddTwoStates(255, 257);
/*  695:     */             }
/*  696:     */             break;
/*  697:     */           case 2: 
/*  698:     */           case 318: 
/*  699: 610 */             if ((0x0 & l) != 0L)
/*  700:     */             {
/*  701: 612 */               if (kind > 3) {
/*  702: 613 */                 kind = 3;
/*  703:     */               }
/*  704: 614 */               jjCheckNAddTwoStates(2, 3);
/*  705:     */             }
/*  706: 615 */             break;
/*  707:     */           case 1: 
/*  708: 617 */             if ((0x0 & l) != 0L)
/*  709:     */             {
/*  710: 619 */               if (kind > 42) {
/*  711: 620 */                 kind = 42;
/*  712:     */               }
/*  713: 621 */               jjCheckNAddStates(0, 41);
/*  714:     */             }
/*  715: 623 */             else if ((0x3600 & l) != 0L)
/*  716:     */             {
/*  717: 625 */               if (kind > 1) {
/*  718: 626 */                 kind = 1;
/*  719:     */               }
/*  720: 627 */               jjCheckNAdd(0);
/*  721:     */             }
/*  722: 629 */             else if (this.curChar == '.')
/*  723:     */             {
/*  724: 630 */               jjCheckNAddStates(42, 52);
/*  725:     */             }
/*  726: 631 */             else if (this.curChar == '!')
/*  727:     */             {
/*  728: 632 */               jjCheckNAddTwoStates(78, 87);
/*  729:     */             }
/*  730: 633 */             else if (this.curChar == '\'')
/*  731:     */             {
/*  732: 634 */               jjCheckNAddStates(53, 55);
/*  733:     */             }
/*  734: 635 */             else if (this.curChar == '"')
/*  735:     */             {
/*  736: 636 */               jjCheckNAddStates(56, 58);
/*  737:     */             }
/*  738: 637 */             else if (this.curChar == '#')
/*  739:     */             {
/*  740: 638 */               jjCheckNAddTwoStates(19, 20);
/*  741:     */             }
/*  742:     */             break;
/*  743:     */           case 90: 
/*  744:     */           case 319: 
/*  745: 642 */             if ((0x0 & l) != 0L)
/*  746:     */             {
/*  747: 644 */               if (kind > 32) {
/*  748: 645 */                 kind = 32;
/*  749:     */               }
/*  750: 646 */               jjCheckNAddTwoStates(90, 91);
/*  751:     */             }
/*  752: 647 */             break;
/*  753:     */           case 0: 
/*  754: 649 */             if ((0x3600 & l) != 0L)
/*  755:     */             {
/*  756: 651 */               if (kind > 1) {
/*  757: 652 */                 kind = 1;
/*  758:     */               }
/*  759: 653 */               jjCheckNAdd(0);
/*  760:     */             }
/*  761: 654 */             break;
/*  762:     */           case 4: 
/*  763: 656 */             if ((0x0 & l) != 0L)
/*  764:     */             {
/*  765: 658 */               if (kind > 3) {
/*  766: 659 */                 kind = 3;
/*  767:     */               }
/*  768: 660 */               jjCheckNAddTwoStates(2, 3);
/*  769:     */             }
/*  770: 661 */             break;
/*  771:     */           case 5: 
/*  772: 663 */             if ((0x0 & l) != 0L)
/*  773:     */             {
/*  774: 665 */               if (kind > 3) {
/*  775: 666 */                 kind = 3;
/*  776:     */               }
/*  777: 667 */               jjCheckNAddStates(59, 66);
/*  778:     */             }
/*  779: 668 */             break;
/*  780:     */           case 6: 
/*  781: 670 */             if ((0x0 & l) != 0L)
/*  782:     */             {
/*  783: 672 */               if (kind > 3) {
/*  784: 673 */                 kind = 3;
/*  785:     */               }
/*  786: 674 */               jjCheckNAddStates(67, 69);
/*  787:     */             }
/*  788: 675 */             break;
/*  789:     */           case 7: 
/*  790: 677 */             if ((0x3600 & l) != 0L)
/*  791:     */             {
/*  792: 679 */               if (kind > 3) {
/*  793: 680 */                 kind = 3;
/*  794:     */               }
/*  795: 681 */               jjCheckNAddTwoStates(2, 3);
/*  796:     */             }
/*  797: 682 */             break;
/*  798:     */           case 8: 
/*  799:     */           case 10: 
/*  800:     */           case 13: 
/*  801:     */           case 17: 
/*  802: 687 */             if ((0x0 & l) != 0L) {
/*  803: 688 */               jjCheckNAdd(6);
/*  804:     */             }
/*  805:     */             break;
/*  806:     */           case 9: 
/*  807: 691 */             if ((0x0 & l) != 0L) {
/*  808: 692 */               this.jjstateSet[(this.jjnewStateCnt++)] = 10;
/*  809:     */             }
/*  810:     */             break;
/*  811:     */           case 11: 
/*  812: 695 */             if ((0x0 & l) != 0L) {
/*  813: 696 */               this.jjstateSet[(this.jjnewStateCnt++)] = 12;
/*  814:     */             }
/*  815:     */             break;
/*  816:     */           case 12: 
/*  817: 699 */             if ((0x0 & l) != 0L) {
/*  818: 700 */               this.jjstateSet[(this.jjnewStateCnt++)] = 13;
/*  819:     */             }
/*  820:     */             break;
/*  821:     */           case 14: 
/*  822: 703 */             if ((0x0 & l) != 0L) {
/*  823: 704 */               this.jjstateSet[(this.jjnewStateCnt++)] = 15;
/*  824:     */             }
/*  825:     */             break;
/*  826:     */           case 15: 
/*  827: 707 */             if ((0x0 & l) != 0L) {
/*  828: 708 */               this.jjstateSet[(this.jjnewStateCnt++)] = 16;
/*  829:     */             }
/*  830:     */             break;
/*  831:     */           case 16: 
/*  832: 711 */             if ((0x0 & l) != 0L) {
/*  833: 712 */               this.jjstateSet[(this.jjnewStateCnt++)] = 17;
/*  834:     */             }
/*  835:     */             break;
/*  836:     */           case 18: 
/*  837: 715 */             if (this.curChar == '#') {
/*  838: 716 */               jjCheckNAddTwoStates(19, 20);
/*  839:     */             }
/*  840:     */             break;
/*  841:     */           case 19: 
/*  842: 719 */             if ((0x0 & l) != 0L)
/*  843:     */             {
/*  844: 721 */               if (kind > 9) {
/*  845: 722 */                 kind = 9;
/*  846:     */               }
/*  847: 723 */               jjCheckNAddTwoStates(19, 20);
/*  848:     */             }
/*  849: 724 */             break;
/*  850:     */           case 21: 
/*  851: 726 */             if ((0x0 & l) != 0L)
/*  852:     */             {
/*  853: 728 */               if (kind > 9) {
/*  854: 729 */                 kind = 9;
/*  855:     */               }
/*  856: 730 */               jjCheckNAddTwoStates(19, 20);
/*  857:     */             }
/*  858: 731 */             break;
/*  859:     */           case 22: 
/*  860: 733 */             if ((0x0 & l) != 0L)
/*  861:     */             {
/*  862: 735 */               if (kind > 9) {
/*  863: 736 */                 kind = 9;
/*  864:     */               }
/*  865: 737 */               jjCheckNAddStates(70, 77);
/*  866:     */             }
/*  867: 738 */             break;
/*  868:     */           case 23: 
/*  869: 740 */             if ((0x0 & l) != 0L)
/*  870:     */             {
/*  871: 742 */               if (kind > 9) {
/*  872: 743 */                 kind = 9;
/*  873:     */               }
/*  874: 744 */               jjCheckNAddStates(78, 80);
/*  875:     */             }
/*  876: 745 */             break;
/*  877:     */           case 24: 
/*  878: 747 */             if ((0x3600 & l) != 0L)
/*  879:     */             {
/*  880: 749 */               if (kind > 9) {
/*  881: 750 */                 kind = 9;
/*  882:     */               }
/*  883: 751 */               jjCheckNAddTwoStates(19, 20);
/*  884:     */             }
/*  885: 752 */             break;
/*  886:     */           case 25: 
/*  887:     */           case 27: 
/*  888:     */           case 30: 
/*  889:     */           case 34: 
/*  890: 757 */             if ((0x0 & l) != 0L) {
/*  891: 758 */               jjCheckNAdd(23);
/*  892:     */             }
/*  893:     */             break;
/*  894:     */           case 26: 
/*  895: 761 */             if ((0x0 & l) != 0L) {
/*  896: 762 */               this.jjstateSet[(this.jjnewStateCnt++)] = 27;
/*  897:     */             }
/*  898:     */             break;
/*  899:     */           case 28: 
/*  900: 765 */             if ((0x0 & l) != 0L) {
/*  901: 766 */               this.jjstateSet[(this.jjnewStateCnt++)] = 29;
/*  902:     */             }
/*  903:     */             break;
/*  904:     */           case 29: 
/*  905: 769 */             if ((0x0 & l) != 0L) {
/*  906: 770 */               this.jjstateSet[(this.jjnewStateCnt++)] = 30;
/*  907:     */             }
/*  908:     */             break;
/*  909:     */           case 31: 
/*  910: 773 */             if ((0x0 & l) != 0L) {
/*  911: 774 */               this.jjstateSet[(this.jjnewStateCnt++)] = 32;
/*  912:     */             }
/*  913:     */             break;
/*  914:     */           case 32: 
/*  915: 777 */             if ((0x0 & l) != 0L) {
/*  916: 778 */               this.jjstateSet[(this.jjnewStateCnt++)] = 33;
/*  917:     */             }
/*  918:     */             break;
/*  919:     */           case 33: 
/*  920: 781 */             if ((0x0 & l) != 0L) {
/*  921: 782 */               this.jjstateSet[(this.jjnewStateCnt++)] = 34;
/*  922:     */             }
/*  923:     */             break;
/*  924:     */           case 35: 
/*  925: 785 */             if (this.curChar == '"') {
/*  926: 786 */               jjCheckNAddStates(56, 58);
/*  927:     */             }
/*  928:     */             break;
/*  929:     */           case 36: 
/*  930: 789 */             if ((0x200 & l) != 0L) {
/*  931: 790 */               jjCheckNAddStates(56, 58);
/*  932:     */             }
/*  933:     */             break;
/*  934:     */           case 37: 
/*  935: 793 */             if ((this.curChar == '"') && (kind > 24)) {
/*  936: 794 */               kind = 24;
/*  937:     */             }
/*  938:     */             break;
/*  939:     */           case 39: 
/*  940: 797 */             if ((0x3400 & l) != 0L) {
/*  941: 798 */               jjCheckNAddStates(56, 58);
/*  942:     */             }
/*  943:     */             break;
/*  944:     */           case 40: 
/*  945: 801 */             if (this.curChar == '\n') {
/*  946: 802 */               jjCheckNAddStates(56, 58);
/*  947:     */             }
/*  948:     */             break;
/*  949:     */           case 41: 
/*  950: 805 */             if (this.curChar == '\r') {
/*  951: 806 */               this.jjstateSet[(this.jjnewStateCnt++)] = 40;
/*  952:     */             }
/*  953:     */             break;
/*  954:     */           case 42: 
/*  955: 809 */             if ((0x0 & l) != 0L) {
/*  956: 810 */               jjCheckNAddStates(56, 58);
/*  957:     */             }
/*  958:     */             break;
/*  959:     */           case 43: 
/*  960: 813 */             if ((0x0 & l) != 0L) {
/*  961: 814 */               jjCheckNAddStates(81, 89);
/*  962:     */             }
/*  963:     */             break;
/*  964:     */           case 44: 
/*  965: 817 */             if ((0x0 & l) != 0L) {
/*  966: 818 */               jjCheckNAddStates(90, 93);
/*  967:     */             }
/*  968:     */             break;
/*  969:     */           case 45: 
/*  970: 821 */             if ((0x3600 & l) != 0L) {
/*  971: 822 */               jjCheckNAddStates(56, 58);
/*  972:     */             }
/*  973:     */             break;
/*  974:     */           case 46: 
/*  975:     */           case 48: 
/*  976:     */           case 51: 
/*  977:     */           case 55: 
/*  978: 828 */             if ((0x0 & l) != 0L) {
/*  979: 829 */               jjCheckNAdd(44);
/*  980:     */             }
/*  981:     */             break;
/*  982:     */           case 47: 
/*  983: 832 */             if ((0x0 & l) != 0L) {
/*  984: 833 */               this.jjstateSet[(this.jjnewStateCnt++)] = 48;
/*  985:     */             }
/*  986:     */             break;
/*  987:     */           case 49: 
/*  988: 836 */             if ((0x0 & l) != 0L) {
/*  989: 837 */               this.jjstateSet[(this.jjnewStateCnt++)] = 50;
/*  990:     */             }
/*  991:     */             break;
/*  992:     */           case 50: 
/*  993: 840 */             if ((0x0 & l) != 0L) {
/*  994: 841 */               this.jjstateSet[(this.jjnewStateCnt++)] = 51;
/*  995:     */             }
/*  996:     */             break;
/*  997:     */           case 52: 
/*  998: 844 */             if ((0x0 & l) != 0L) {
/*  999: 845 */               this.jjstateSet[(this.jjnewStateCnt++)] = 53;
/* 1000:     */             }
/* 1001:     */             break;
/* 1002:     */           case 53: 
/* 1003: 848 */             if ((0x0 & l) != 0L) {
/* 1004: 849 */               this.jjstateSet[(this.jjnewStateCnt++)] = 54;
/* 1005:     */             }
/* 1006:     */             break;
/* 1007:     */           case 54: 
/* 1008: 852 */             if ((0x0 & l) != 0L) {
/* 1009: 853 */               this.jjstateSet[(this.jjnewStateCnt++)] = 55;
/* 1010:     */             }
/* 1011:     */             break;
/* 1012:     */           case 56: 
/* 1013: 856 */             if (this.curChar == '\'') {
/* 1014: 857 */               jjCheckNAddStates(53, 55);
/* 1015:     */             }
/* 1016:     */             break;
/* 1017:     */           case 57: 
/* 1018: 860 */             if ((0x200 & l) != 0L) {
/* 1019: 861 */               jjCheckNAddStates(53, 55);
/* 1020:     */             }
/* 1021:     */             break;
/* 1022:     */           case 58: 
/* 1023: 864 */             if ((this.curChar == '\'') && (kind > 24)) {
/* 1024: 865 */               kind = 24;
/* 1025:     */             }
/* 1026:     */             break;
/* 1027:     */           case 60: 
/* 1028: 868 */             if ((0x3400 & l) != 0L) {
/* 1029: 869 */               jjCheckNAddStates(53, 55);
/* 1030:     */             }
/* 1031:     */             break;
/* 1032:     */           case 61: 
/* 1033: 872 */             if (this.curChar == '\n') {
/* 1034: 873 */               jjCheckNAddStates(53, 55);
/* 1035:     */             }
/* 1036:     */             break;
/* 1037:     */           case 62: 
/* 1038: 876 */             if (this.curChar == '\r') {
/* 1039: 877 */               this.jjstateSet[(this.jjnewStateCnt++)] = 61;
/* 1040:     */             }
/* 1041:     */             break;
/* 1042:     */           case 63: 
/* 1043: 880 */             if ((0x0 & l) != 0L) {
/* 1044: 881 */               jjCheckNAddStates(53, 55);
/* 1045:     */             }
/* 1046:     */             break;
/* 1047:     */           case 64: 
/* 1048: 884 */             if ((0x0 & l) != 0L) {
/* 1049: 885 */               jjCheckNAddStates(94, 102);
/* 1050:     */             }
/* 1051:     */             break;
/* 1052:     */           case 65: 
/* 1053: 888 */             if ((0x0 & l) != 0L) {
/* 1054: 889 */               jjCheckNAddStates(103, 106);
/* 1055:     */             }
/* 1056:     */             break;
/* 1057:     */           case 66: 
/* 1058: 892 */             if ((0x3600 & l) != 0L) {
/* 1059: 893 */               jjCheckNAddStates(53, 55);
/* 1060:     */             }
/* 1061:     */             break;
/* 1062:     */           case 67: 
/* 1063:     */           case 69: 
/* 1064:     */           case 72: 
/* 1065:     */           case 76: 
/* 1066: 899 */             if ((0x0 & l) != 0L) {
/* 1067: 900 */               jjCheckNAdd(65);
/* 1068:     */             }
/* 1069:     */             break;
/* 1070:     */           case 68: 
/* 1071: 903 */             if ((0x0 & l) != 0L) {
/* 1072: 904 */               this.jjstateSet[(this.jjnewStateCnt++)] = 69;
/* 1073:     */             }
/* 1074:     */             break;
/* 1075:     */           case 70: 
/* 1076: 907 */             if ((0x0 & l) != 0L) {
/* 1077: 908 */               this.jjstateSet[(this.jjnewStateCnt++)] = 71;
/* 1078:     */             }
/* 1079:     */             break;
/* 1080:     */           case 71: 
/* 1081: 911 */             if ((0x0 & l) != 0L) {
/* 1082: 912 */               this.jjstateSet[(this.jjnewStateCnt++)] = 72;
/* 1083:     */             }
/* 1084:     */             break;
/* 1085:     */           case 73: 
/* 1086: 915 */             if ((0x0 & l) != 0L) {
/* 1087: 916 */               this.jjstateSet[(this.jjnewStateCnt++)] = 74;
/* 1088:     */             }
/* 1089:     */             break;
/* 1090:     */           case 74: 
/* 1091: 919 */             if ((0x0 & l) != 0L) {
/* 1092: 920 */               this.jjstateSet[(this.jjnewStateCnt++)] = 75;
/* 1093:     */             }
/* 1094:     */             break;
/* 1095:     */           case 75: 
/* 1096: 923 */             if ((0x0 & l) != 0L) {
/* 1097: 924 */               this.jjstateSet[(this.jjnewStateCnt++)] = 76;
/* 1098:     */             }
/* 1099:     */             break;
/* 1100:     */           case 77: 
/* 1101: 927 */             if (this.curChar == '!') {
/* 1102: 928 */               jjCheckNAddTwoStates(78, 87);
/* 1103:     */             }
/* 1104:     */             break;
/* 1105:     */           case 78: 
/* 1106: 931 */             if ((0x3600 & l) != 0L) {
/* 1107: 932 */               jjCheckNAddTwoStates(78, 87);
/* 1108:     */             }
/* 1109:     */             break;
/* 1110:     */           case 92: 
/* 1111: 935 */             if ((0x0 & l) != 0L)
/* 1112:     */             {
/* 1113: 937 */               if (kind > 32) {
/* 1114: 938 */                 kind = 32;
/* 1115:     */               }
/* 1116: 939 */               jjCheckNAddTwoStates(90, 91);
/* 1117:     */             }
/* 1118: 940 */             break;
/* 1119:     */           case 93: 
/* 1120: 942 */             if ((0x0 & l) != 0L)
/* 1121:     */             {
/* 1122: 944 */               if (kind > 32) {
/* 1123: 945 */                 kind = 32;
/* 1124:     */               }
/* 1125: 946 */               jjCheckNAddStates(107, 114);
/* 1126:     */             }
/* 1127: 947 */             break;
/* 1128:     */           case 94: 
/* 1129: 949 */             if ((0x0 & l) != 0L)
/* 1130:     */             {
/* 1131: 951 */               if (kind > 32) {
/* 1132: 952 */                 kind = 32;
/* 1133:     */               }
/* 1134: 953 */               jjCheckNAddStates(115, 117);
/* 1135:     */             }
/* 1136: 954 */             break;
/* 1137:     */           case 95: 
/* 1138: 956 */             if ((0x3600 & l) != 0L)
/* 1139:     */             {
/* 1140: 958 */               if (kind > 32) {
/* 1141: 959 */                 kind = 32;
/* 1142:     */               }
/* 1143: 960 */               jjCheckNAddTwoStates(90, 91);
/* 1144:     */             }
/* 1145: 961 */             break;
/* 1146:     */           case 96: 
/* 1147:     */           case 98: 
/* 1148:     */           case 101: 
/* 1149:     */           case 105: 
/* 1150: 966 */             if ((0x0 & l) != 0L) {
/* 1151: 967 */               jjCheckNAdd(94);
/* 1152:     */             }
/* 1153:     */             break;
/* 1154:     */           case 97: 
/* 1155: 970 */             if ((0x0 & l) != 0L) {
/* 1156: 971 */               this.jjstateSet[(this.jjnewStateCnt++)] = 98;
/* 1157:     */             }
/* 1158:     */             break;
/* 1159:     */           case 99: 
/* 1160: 974 */             if ((0x0 & l) != 0L) {
/* 1161: 975 */               this.jjstateSet[(this.jjnewStateCnt++)] = 100;
/* 1162:     */             }
/* 1163:     */             break;
/* 1164:     */           case 100: 
/* 1165: 978 */             if ((0x0 & l) != 0L) {
/* 1166: 979 */               this.jjstateSet[(this.jjnewStateCnt++)] = 101;
/* 1167:     */             }
/* 1168:     */             break;
/* 1169:     */           case 102: 
/* 1170: 982 */             if ((0x0 & l) != 0L) {
/* 1171: 983 */               this.jjstateSet[(this.jjnewStateCnt++)] = 103;
/* 1172:     */             }
/* 1173:     */             break;
/* 1174:     */           case 103: 
/* 1175: 986 */             if ((0x0 & l) != 0L) {
/* 1176: 987 */               this.jjstateSet[(this.jjnewStateCnt++)] = 104;
/* 1177:     */             }
/* 1178:     */             break;
/* 1179:     */           case 104: 
/* 1180: 990 */             if ((0x0 & l) != 0L) {
/* 1181: 991 */               this.jjstateSet[(this.jjnewStateCnt++)] = 105;
/* 1182:     */             }
/* 1183:     */             break;
/* 1184:     */           case 107: 
/* 1185: 994 */             if ((0x0 & l) != 0L)
/* 1186:     */             {
/* 1187: 996 */               if (kind > 32) {
/* 1188: 997 */                 kind = 32;
/* 1189:     */               }
/* 1190: 998 */               jjCheckNAddStates(118, 125);
/* 1191:     */             }
/* 1192: 999 */             break;
/* 1193:     */           case 108: 
/* 1194:1001 */             if ((0x0 & l) != 0L)
/* 1195:     */             {
/* 1196:1003 */               if (kind > 32) {
/* 1197:1004 */                 kind = 32;
/* 1198:     */               }
/* 1199:1005 */               jjCheckNAddStates(126, 128);
/* 1200:     */             }
/* 1201:1006 */             break;
/* 1202:     */           case 109: 
/* 1203:     */           case 111: 
/* 1204:     */           case 114: 
/* 1205:     */           case 118: 
/* 1206:1011 */             if ((0x0 & l) != 0L) {
/* 1207:1012 */               jjCheckNAdd(108);
/* 1208:     */             }
/* 1209:     */             break;
/* 1210:     */           case 110: 
/* 1211:1015 */             if ((0x0 & l) != 0L) {
/* 1212:1016 */               this.jjstateSet[(this.jjnewStateCnt++)] = 111;
/* 1213:     */             }
/* 1214:     */             break;
/* 1215:     */           case 112: 
/* 1216:1019 */             if ((0x0 & l) != 0L) {
/* 1217:1020 */               this.jjstateSet[(this.jjnewStateCnt++)] = 113;
/* 1218:     */             }
/* 1219:     */             break;
/* 1220:     */           case 113: 
/* 1221:1023 */             if ((0x0 & l) != 0L) {
/* 1222:1024 */               this.jjstateSet[(this.jjnewStateCnt++)] = 114;
/* 1223:     */             }
/* 1224:     */             break;
/* 1225:     */           case 115: 
/* 1226:1027 */             if ((0x0 & l) != 0L) {
/* 1227:1028 */               this.jjstateSet[(this.jjnewStateCnt++)] = 116;
/* 1228:     */             }
/* 1229:     */             break;
/* 1230:     */           case 116: 
/* 1231:1031 */             if ((0x0 & l) != 0L) {
/* 1232:1032 */               this.jjstateSet[(this.jjnewStateCnt++)] = 117;
/* 1233:     */             }
/* 1234:     */             break;
/* 1235:     */           case 117: 
/* 1236:1035 */             if ((0x0 & l) != 0L) {
/* 1237:1036 */               this.jjstateSet[(this.jjnewStateCnt++)] = 118;
/* 1238:     */             }
/* 1239:     */             break;
/* 1240:     */           case 120: 
/* 1241:1039 */             if ((0x0 & l) != 0L)
/* 1242:     */             {
/* 1243:1041 */               if (kind > 3) {
/* 1244:1042 */                 kind = 3;
/* 1245:     */               }
/* 1246:1043 */               jjCheckNAddStates(129, 136);
/* 1247:     */             }
/* 1248:1044 */             break;
/* 1249:     */           case 121: 
/* 1250:1046 */             if ((0x0 & l) != 0L)
/* 1251:     */             {
/* 1252:1048 */               if (kind > 3) {
/* 1253:1049 */                 kind = 3;
/* 1254:     */               }
/* 1255:1050 */               jjCheckNAddStates(137, 139);
/* 1256:     */             }
/* 1257:1051 */             break;
/* 1258:     */           case 122: 
/* 1259:     */           case 124: 
/* 1260:     */           case 127: 
/* 1261:     */           case 131: 
/* 1262:1056 */             if ((0x0 & l) != 0L) {
/* 1263:1057 */               jjCheckNAdd(121);
/* 1264:     */             }
/* 1265:     */             break;
/* 1266:     */           case 123: 
/* 1267:1060 */             if ((0x0 & l) != 0L) {
/* 1268:1061 */               this.jjstateSet[(this.jjnewStateCnt++)] = 124;
/* 1269:     */             }
/* 1270:     */             break;
/* 1271:     */           case 125: 
/* 1272:1064 */             if ((0x0 & l) != 0L) {
/* 1273:1065 */               this.jjstateSet[(this.jjnewStateCnt++)] = 126;
/* 1274:     */             }
/* 1275:     */             break;
/* 1276:     */           case 126: 
/* 1277:1068 */             if ((0x0 & l) != 0L) {
/* 1278:1069 */               this.jjstateSet[(this.jjnewStateCnt++)] = 127;
/* 1279:     */             }
/* 1280:     */             break;
/* 1281:     */           case 128: 
/* 1282:1072 */             if ((0x0 & l) != 0L) {
/* 1283:1073 */               this.jjstateSet[(this.jjnewStateCnt++)] = 129;
/* 1284:     */             }
/* 1285:     */             break;
/* 1286:     */           case 129: 
/* 1287:1076 */             if ((0x0 & l) != 0L) {
/* 1288:1077 */               this.jjstateSet[(this.jjnewStateCnt++)] = 130;
/* 1289:     */             }
/* 1290:     */             break;
/* 1291:     */           case 130: 
/* 1292:1080 */             if ((0x0 & l) != 0L) {
/* 1293:1081 */               this.jjstateSet[(this.jjnewStateCnt++)] = 131;
/* 1294:     */             }
/* 1295:     */             break;
/* 1296:     */           case 133: 
/* 1297:1084 */             if (this.curChar == '(') {
/* 1298:1085 */               jjCheckNAddStates(140, 145);
/* 1299:     */             }
/* 1300:     */             break;
/* 1301:     */           case 134: 
/* 1302:1088 */             if ((0x0 & l) != 0L) {
/* 1303:1089 */               jjCheckNAddStates(146, 149);
/* 1304:     */             }
/* 1305:     */             break;
/* 1306:     */           case 135: 
/* 1307:1092 */             if ((0x3600 & l) != 0L) {
/* 1308:1093 */               jjCheckNAddTwoStates(135, 136);
/* 1309:     */             }
/* 1310:     */             break;
/* 1311:     */           case 136: 
/* 1312:1096 */             if ((this.curChar == ')') && (kind > 26)) {
/* 1313:1097 */               kind = 26;
/* 1314:     */             }
/* 1315:     */             break;
/* 1316:     */           case 138: 
/* 1317:1100 */             if ((0x0 & l) != 0L) {
/* 1318:1101 */               jjCheckNAddStates(146, 149);
/* 1319:     */             }
/* 1320:     */             break;
/* 1321:     */           case 139: 
/* 1322:1104 */             if ((0x0 & l) != 0L) {
/* 1323:1105 */               jjCheckNAddStates(150, 158);
/* 1324:     */             }
/* 1325:     */             break;
/* 1326:     */           case 140: 
/* 1327:1108 */             if ((0x0 & l) != 0L) {
/* 1328:1109 */               jjCheckNAddStates(159, 162);
/* 1329:     */             }
/* 1330:     */             break;
/* 1331:     */           case 141: 
/* 1332:1112 */             if ((0x3600 & l) != 0L) {
/* 1333:1113 */               jjCheckNAddStates(146, 149);
/* 1334:     */             }
/* 1335:     */             break;
/* 1336:     */           case 142: 
/* 1337:     */           case 144: 
/* 1338:     */           case 147: 
/* 1339:     */           case 151: 
/* 1340:1119 */             if ((0x0 & l) != 0L) {
/* 1341:1120 */               jjCheckNAdd(140);
/* 1342:     */             }
/* 1343:     */             break;
/* 1344:     */           case 143: 
/* 1345:1123 */             if ((0x0 & l) != 0L) {
/* 1346:1124 */               this.jjstateSet[(this.jjnewStateCnt++)] = 144;
/* 1347:     */             }
/* 1348:     */             break;
/* 1349:     */           case 145: 
/* 1350:1127 */             if ((0x0 & l) != 0L) {
/* 1351:1128 */               this.jjstateSet[(this.jjnewStateCnt++)] = 146;
/* 1352:     */             }
/* 1353:     */             break;
/* 1354:     */           case 146: 
/* 1355:1131 */             if ((0x0 & l) != 0L) {
/* 1356:1132 */               this.jjstateSet[(this.jjnewStateCnt++)] = 147;
/* 1357:     */             }
/* 1358:     */             break;
/* 1359:     */           case 148: 
/* 1360:1135 */             if ((0x0 & l) != 0L) {
/* 1361:1136 */               this.jjstateSet[(this.jjnewStateCnt++)] = 149;
/* 1362:     */             }
/* 1363:     */             break;
/* 1364:     */           case 149: 
/* 1365:1139 */             if ((0x0 & l) != 0L) {
/* 1366:1140 */               this.jjstateSet[(this.jjnewStateCnt++)] = 150;
/* 1367:     */             }
/* 1368:     */             break;
/* 1369:     */           case 150: 
/* 1370:1143 */             if ((0x0 & l) != 0L) {
/* 1371:1144 */               this.jjstateSet[(this.jjnewStateCnt++)] = 151;
/* 1372:     */             }
/* 1373:     */             break;
/* 1374:     */           case 152: 
/* 1375:1147 */             if (this.curChar == '\'') {
/* 1376:1148 */               jjCheckNAddStates(163, 165);
/* 1377:     */             }
/* 1378:     */             break;
/* 1379:     */           case 153: 
/* 1380:1151 */             if ((0x200 & l) != 0L) {
/* 1381:1152 */               jjCheckNAddStates(163, 165);
/* 1382:     */             }
/* 1383:     */             break;
/* 1384:     */           case 154: 
/* 1385:1155 */             if (this.curChar == '\'') {
/* 1386:1156 */               jjCheckNAddTwoStates(135, 136);
/* 1387:     */             }
/* 1388:     */             break;
/* 1389:     */           case 156: 
/* 1390:1159 */             if ((0x3400 & l) != 0L) {
/* 1391:1160 */               jjCheckNAddStates(163, 165);
/* 1392:     */             }
/* 1393:     */             break;
/* 1394:     */           case 157: 
/* 1395:1163 */             if (this.curChar == '\n') {
/* 1396:1164 */               jjCheckNAddStates(163, 165);
/* 1397:     */             }
/* 1398:     */             break;
/* 1399:     */           case 158: 
/* 1400:1167 */             if (this.curChar == '\r') {
/* 1401:1168 */               this.jjstateSet[(this.jjnewStateCnt++)] = 157;
/* 1402:     */             }
/* 1403:     */             break;
/* 1404:     */           case 159: 
/* 1405:1171 */             if ((0x0 & l) != 0L) {
/* 1406:1172 */               jjCheckNAddStates(163, 165);
/* 1407:     */             }
/* 1408:     */             break;
/* 1409:     */           case 160: 
/* 1410:1175 */             if ((0x0 & l) != 0L) {
/* 1411:1176 */               jjCheckNAddStates(166, 174);
/* 1412:     */             }
/* 1413:     */             break;
/* 1414:     */           case 161: 
/* 1415:1179 */             if ((0x0 & l) != 0L) {
/* 1416:1180 */               jjCheckNAddStates(175, 178);
/* 1417:     */             }
/* 1418:     */             break;
/* 1419:     */           case 162: 
/* 1420:1183 */             if ((0x3600 & l) != 0L) {
/* 1421:1184 */               jjCheckNAddStates(163, 165);
/* 1422:     */             }
/* 1423:     */             break;
/* 1424:     */           case 163: 
/* 1425:     */           case 165: 
/* 1426:     */           case 168: 
/* 1427:     */           case 172: 
/* 1428:1190 */             if ((0x0 & l) != 0L) {
/* 1429:1191 */               jjCheckNAdd(161);
/* 1430:     */             }
/* 1431:     */             break;
/* 1432:     */           case 164: 
/* 1433:1194 */             if ((0x0 & l) != 0L) {
/* 1434:1195 */               this.jjstateSet[(this.jjnewStateCnt++)] = 165;
/* 1435:     */             }
/* 1436:     */             break;
/* 1437:     */           case 166: 
/* 1438:1198 */             if ((0x0 & l) != 0L) {
/* 1439:1199 */               this.jjstateSet[(this.jjnewStateCnt++)] = 167;
/* 1440:     */             }
/* 1441:     */             break;
/* 1442:     */           case 167: 
/* 1443:1202 */             if ((0x0 & l) != 0L) {
/* 1444:1203 */               this.jjstateSet[(this.jjnewStateCnt++)] = 168;
/* 1445:     */             }
/* 1446:     */             break;
/* 1447:     */           case 169: 
/* 1448:1206 */             if ((0x0 & l) != 0L) {
/* 1449:1207 */               this.jjstateSet[(this.jjnewStateCnt++)] = 170;
/* 1450:     */             }
/* 1451:     */             break;
/* 1452:     */           case 170: 
/* 1453:1210 */             if ((0x0 & l) != 0L) {
/* 1454:1211 */               this.jjstateSet[(this.jjnewStateCnt++)] = 171;
/* 1455:     */             }
/* 1456:     */             break;
/* 1457:     */           case 171: 
/* 1458:1214 */             if ((0x0 & l) != 0L) {
/* 1459:1215 */               this.jjstateSet[(this.jjnewStateCnt++)] = 172;
/* 1460:     */             }
/* 1461:     */             break;
/* 1462:     */           case 173: 
/* 1463:1218 */             if (this.curChar == '"') {
/* 1464:1219 */               jjCheckNAddStates(179, 181);
/* 1465:     */             }
/* 1466:     */             break;
/* 1467:     */           case 174: 
/* 1468:1222 */             if ((0x200 & l) != 0L) {
/* 1469:1223 */               jjCheckNAddStates(179, 181);
/* 1470:     */             }
/* 1471:     */             break;
/* 1472:     */           case 175: 
/* 1473:1226 */             if (this.curChar == '"') {
/* 1474:1227 */               jjCheckNAddTwoStates(135, 136);
/* 1475:     */             }
/* 1476:     */             break;
/* 1477:     */           case 177: 
/* 1478:1230 */             if ((0x3400 & l) != 0L) {
/* 1479:1231 */               jjCheckNAddStates(179, 181);
/* 1480:     */             }
/* 1481:     */             break;
/* 1482:     */           case 178: 
/* 1483:1234 */             if (this.curChar == '\n') {
/* 1484:1235 */               jjCheckNAddStates(179, 181);
/* 1485:     */             }
/* 1486:     */             break;
/* 1487:     */           case 179: 
/* 1488:1238 */             if (this.curChar == '\r') {
/* 1489:1239 */               this.jjstateSet[(this.jjnewStateCnt++)] = 178;
/* 1490:     */             }
/* 1491:     */             break;
/* 1492:     */           case 180: 
/* 1493:1242 */             if ((0x0 & l) != 0L) {
/* 1494:1243 */               jjCheckNAddStates(179, 181);
/* 1495:     */             }
/* 1496:     */             break;
/* 1497:     */           case 181: 
/* 1498:1246 */             if ((0x0 & l) != 0L) {
/* 1499:1247 */               jjCheckNAddStates(182, 190);
/* 1500:     */             }
/* 1501:     */             break;
/* 1502:     */           case 182: 
/* 1503:1250 */             if ((0x0 & l) != 0L) {
/* 1504:1251 */               jjCheckNAddStates(191, 194);
/* 1505:     */             }
/* 1506:     */             break;
/* 1507:     */           case 183: 
/* 1508:1254 */             if ((0x3600 & l) != 0L) {
/* 1509:1255 */               jjCheckNAddStates(179, 181);
/* 1510:     */             }
/* 1511:     */             break;
/* 1512:     */           case 184: 
/* 1513:     */           case 186: 
/* 1514:     */           case 189: 
/* 1515:     */           case 193: 
/* 1516:1261 */             if ((0x0 & l) != 0L) {
/* 1517:1262 */               jjCheckNAdd(182);
/* 1518:     */             }
/* 1519:     */             break;
/* 1520:     */           case 185: 
/* 1521:1265 */             if ((0x0 & l) != 0L) {
/* 1522:1266 */               this.jjstateSet[(this.jjnewStateCnt++)] = 186;
/* 1523:     */             }
/* 1524:     */             break;
/* 1525:     */           case 187: 
/* 1526:1269 */             if ((0x0 & l) != 0L) {
/* 1527:1270 */               this.jjstateSet[(this.jjnewStateCnt++)] = 188;
/* 1528:     */             }
/* 1529:     */             break;
/* 1530:     */           case 188: 
/* 1531:1273 */             if ((0x0 & l) != 0L) {
/* 1532:1274 */               this.jjstateSet[(this.jjnewStateCnt++)] = 189;
/* 1533:     */             }
/* 1534:     */             break;
/* 1535:     */           case 190: 
/* 1536:1277 */             if ((0x0 & l) != 0L) {
/* 1537:1278 */               this.jjstateSet[(this.jjnewStateCnt++)] = 191;
/* 1538:     */             }
/* 1539:     */             break;
/* 1540:     */           case 191: 
/* 1541:1281 */             if ((0x0 & l) != 0L) {
/* 1542:1282 */               this.jjstateSet[(this.jjnewStateCnt++)] = 192;
/* 1543:     */             }
/* 1544:     */             break;
/* 1545:     */           case 192: 
/* 1546:1285 */             if ((0x0 & l) != 0L) {
/* 1547:1286 */               this.jjstateSet[(this.jjnewStateCnt++)] = 193;
/* 1548:     */             }
/* 1549:     */             break;
/* 1550:     */           case 194: 
/* 1551:1289 */             if ((0x3600 & l) != 0L) {
/* 1552:1290 */               jjCheckNAddStates(195, 201);
/* 1553:     */             }
/* 1554:     */             break;
/* 1555:     */           case 197: 
/* 1556:1293 */             if (this.curChar == '+') {
/* 1557:1294 */               jjCheckNAddStates(202, 204);
/* 1558:     */             }
/* 1559:     */             break;
/* 1560:     */           case 198: 
/* 1561:     */           case 227: 
/* 1562:1298 */             if ((this.curChar == '?') && (kind > 47)) {
/* 1563:1299 */               kind = 47;
/* 1564:     */             }
/* 1565:     */             break;
/* 1566:     */           case 199: 
/* 1567:1302 */             if ((0x0 & l) != 0L)
/* 1568:     */             {
/* 1569:1304 */               if (kind > 47) {
/* 1570:1305 */                 kind = 47;
/* 1571:     */               }
/* 1572:1306 */               jjCheckNAddStates(205, 213);
/* 1573:     */             }
/* 1574:1307 */             break;
/* 1575:     */           case 200: 
/* 1576:1309 */             if ((0x0 & l) != 0L) {
/* 1577:1310 */               jjCheckNAdd(201);
/* 1578:     */             }
/* 1579:     */             break;
/* 1580:     */           case 201: 
/* 1581:1313 */             if (this.curChar == '-') {
/* 1582:1314 */               this.jjstateSet[(this.jjnewStateCnt++)] = 202;
/* 1583:     */             }
/* 1584:     */             break;
/* 1585:     */           case 202: 
/* 1586:1317 */             if ((0x0 & l) != 0L)
/* 1587:     */             {
/* 1588:1319 */               if (kind > 47) {
/* 1589:1320 */                 kind = 47;
/* 1590:     */               }
/* 1591:1321 */               jjCheckNAddStates(214, 218);
/* 1592:     */             }
/* 1593:1322 */             break;
/* 1594:     */           case 203: 
/* 1595:1324 */             if (((0x0 & l) != 0L) && (kind > 47)) {
/* 1596:1325 */               kind = 47;
/* 1597:     */             }
/* 1598:     */             break;
/* 1599:     */           case 204: 
/* 1600:     */           case 206: 
/* 1601:     */           case 209: 
/* 1602:     */           case 213: 
/* 1603:1331 */             if ((0x0 & l) != 0L) {
/* 1604:1332 */               jjCheckNAdd(203);
/* 1605:     */             }
/* 1606:     */             break;
/* 1607:     */           case 205: 
/* 1608:1335 */             if ((0x0 & l) != 0L) {
/* 1609:1336 */               this.jjstateSet[(this.jjnewStateCnt++)] = 206;
/* 1610:     */             }
/* 1611:     */             break;
/* 1612:     */           case 207: 
/* 1613:1339 */             if ((0x0 & l) != 0L) {
/* 1614:1340 */               this.jjstateSet[(this.jjnewStateCnt++)] = 208;
/* 1615:     */             }
/* 1616:     */             break;
/* 1617:     */           case 208: 
/* 1618:1343 */             if ((0x0 & l) != 0L) {
/* 1619:1344 */               this.jjstateSet[(this.jjnewStateCnt++)] = 209;
/* 1620:     */             }
/* 1621:     */             break;
/* 1622:     */           case 210: 
/* 1623:1347 */             if ((0x0 & l) != 0L) {
/* 1624:1348 */               this.jjstateSet[(this.jjnewStateCnt++)] = 211;
/* 1625:     */             }
/* 1626:     */             break;
/* 1627:     */           case 211: 
/* 1628:1351 */             if ((0x0 & l) != 0L) {
/* 1629:1352 */               this.jjstateSet[(this.jjnewStateCnt++)] = 212;
/* 1630:     */             }
/* 1631:     */             break;
/* 1632:     */           case 212: 
/* 1633:1355 */             if ((0x0 & l) != 0L) {
/* 1634:1356 */               this.jjstateSet[(this.jjnewStateCnt++)] = 213;
/* 1635:     */             }
/* 1636:     */             break;
/* 1637:     */           case 214: 
/* 1638:     */           case 216: 
/* 1639:     */           case 219: 
/* 1640:     */           case 223: 
/* 1641:1362 */             if ((0x0 & l) != 0L) {
/* 1642:1363 */               jjCheckNAdd(200);
/* 1643:     */             }
/* 1644:     */             break;
/* 1645:     */           case 215: 
/* 1646:1366 */             if ((0x0 & l) != 0L) {
/* 1647:1367 */               this.jjstateSet[(this.jjnewStateCnt++)] = 216;
/* 1648:     */             }
/* 1649:     */             break;
/* 1650:     */           case 217: 
/* 1651:1370 */             if ((0x0 & l) != 0L) {
/* 1652:1371 */               this.jjstateSet[(this.jjnewStateCnt++)] = 218;
/* 1653:     */             }
/* 1654:     */             break;
/* 1655:     */           case 218: 
/* 1656:1374 */             if ((0x0 & l) != 0L) {
/* 1657:1375 */               this.jjstateSet[(this.jjnewStateCnt++)] = 219;
/* 1658:     */             }
/* 1659:     */             break;
/* 1660:     */           case 220: 
/* 1661:1378 */             if ((0x0 & l) != 0L) {
/* 1662:1379 */               this.jjstateSet[(this.jjnewStateCnt++)] = 221;
/* 1663:     */             }
/* 1664:     */             break;
/* 1665:     */           case 221: 
/* 1666:1382 */             if ((0x0 & l) != 0L) {
/* 1667:1383 */               this.jjstateSet[(this.jjnewStateCnt++)] = 222;
/* 1668:     */             }
/* 1669:     */             break;
/* 1670:     */           case 222: 
/* 1671:1386 */             if ((0x0 & l) != 0L) {
/* 1672:1387 */               this.jjstateSet[(this.jjnewStateCnt++)] = 223;
/* 1673:     */             }
/* 1674:     */             break;
/* 1675:     */           case 224: 
/* 1676:1390 */             if ((0x0 & l) != 0L)
/* 1677:     */             {
/* 1678:1392 */               if (kind > 47) {
/* 1679:1393 */                 kind = 47;
/* 1680:     */               }
/* 1681:1394 */               jjCheckNAddStates(219, 221);
/* 1682:     */             }
/* 1683:1395 */             break;
/* 1684:     */           case 225: 
/* 1685:1397 */             if ((0x0 & l) != 0L)
/* 1686:     */             {
/* 1687:1399 */               if (kind > 47) {
/* 1688:1400 */                 kind = 47;
/* 1689:     */               }
/* 1690:1401 */               jjCheckNAddStates(222, 224);
/* 1691:     */             }
/* 1692:1402 */             break;
/* 1693:     */           case 226: 
/* 1694:1404 */             if ((0x0 & l) != 0L)
/* 1695:     */             {
/* 1696:1406 */               if (kind > 47) {
/* 1697:1407 */                 kind = 47;
/* 1698:     */               }
/* 1699:1408 */               jjCheckNAddStates(225, 227);
/* 1700:     */             }
/* 1701:1409 */             break;
/* 1702:     */           case 228: 
/* 1703:     */           case 231: 
/* 1704:     */           case 233: 
/* 1705:     */           case 234: 
/* 1706:     */           case 237: 
/* 1707:     */           case 238: 
/* 1708:     */           case 240: 
/* 1709:     */           case 244: 
/* 1710:     */           case 248: 
/* 1711:     */           case 251: 
/* 1712:     */           case 253: 
/* 1713:1421 */             if (this.curChar == '?') {
/* 1714:1422 */               jjCheckNAdd(227);
/* 1715:     */             }
/* 1716:     */             break;
/* 1717:     */           case 229: 
/* 1718:1425 */             if ((0x0 & l) != 0L)
/* 1719:     */             {
/* 1720:1427 */               if (kind > 47) {
/* 1721:1428 */                 kind = 47;
/* 1722:     */               }
/* 1723:1429 */               jjCheckNAddTwoStates(198, 203);
/* 1724:     */             }
/* 1725:1430 */             break;
/* 1726:     */           case 230: 
/* 1727:1432 */             if (this.curChar == '?') {
/* 1728:1433 */               jjCheckNAddTwoStates(227, 231);
/* 1729:     */             }
/* 1730:     */             break;
/* 1731:     */           case 232: 
/* 1732:1436 */             if (this.curChar == '?') {
/* 1733:1437 */               jjCheckNAddStates(228, 230);
/* 1734:     */             }
/* 1735:     */             break;
/* 1736:     */           case 235: 
/* 1737:1440 */             if (this.curChar == '?') {
/* 1738:1441 */               this.jjstateSet[(this.jjnewStateCnt++)] = 234;
/* 1739:     */             }
/* 1740:     */             break;
/* 1741:     */           case 236: 
/* 1742:1444 */             if (this.curChar == '?') {
/* 1743:1445 */               jjCheckNAddStates(231, 234);
/* 1744:     */             }
/* 1745:     */             break;
/* 1746:     */           case 239: 
/* 1747:1448 */             if (this.curChar == '?') {
/* 1748:1449 */               this.jjstateSet[(this.jjnewStateCnt++)] = 238;
/* 1749:     */             }
/* 1750:     */             break;
/* 1751:     */           case 241: 
/* 1752:1452 */             if (this.curChar == '?') {
/* 1753:1453 */               this.jjstateSet[(this.jjnewStateCnt++)] = 240;
/* 1754:     */             }
/* 1755:     */             break;
/* 1756:     */           case 242: 
/* 1757:1456 */             if (this.curChar == '?') {
/* 1758:1457 */               this.jjstateSet[(this.jjnewStateCnt++)] = 241;
/* 1759:     */             }
/* 1760:     */             break;
/* 1761:     */           case 243: 
/* 1762:1460 */             if (this.curChar == '?') {
/* 1763:1461 */               jjCheckNAddStates(235, 239);
/* 1764:     */             }
/* 1765:     */             break;
/* 1766:     */           case 245: 
/* 1767:1464 */             if (this.curChar == '?') {
/* 1768:1465 */               this.jjstateSet[(this.jjnewStateCnt++)] = 244;
/* 1769:     */             }
/* 1770:     */             break;
/* 1771:     */           case 246: 
/* 1772:1468 */             if (this.curChar == '?') {
/* 1773:1469 */               this.jjstateSet[(this.jjnewStateCnt++)] = 245;
/* 1774:     */             }
/* 1775:     */             break;
/* 1776:     */           case 247: 
/* 1777:1472 */             if (this.curChar == '?') {
/* 1778:1473 */               this.jjstateSet[(this.jjnewStateCnt++)] = 246;
/* 1779:     */             }
/* 1780:     */             break;
/* 1781:     */           case 249: 
/* 1782:1476 */             if (this.curChar == '?') {
/* 1783:1477 */               this.jjstateSet[(this.jjnewStateCnt++)] = 248;
/* 1784:     */             }
/* 1785:     */             break;
/* 1786:     */           case 250: 
/* 1787:1480 */             if (this.curChar == '?') {
/* 1788:1481 */               this.jjstateSet[(this.jjnewStateCnt++)] = 249;
/* 1789:     */             }
/* 1790:     */             break;
/* 1791:     */           case 252: 
/* 1792:1484 */             if (this.curChar == '?') {
/* 1793:1485 */               this.jjstateSet[(this.jjnewStateCnt++)] = 251;
/* 1794:     */             }
/* 1795:     */             break;
/* 1796:     */           case 254: 
/* 1797:1488 */             if (this.curChar == '.') {
/* 1798:1489 */               jjCheckNAddStates(42, 52);
/* 1799:     */             }
/* 1800:     */             break;
/* 1801:     */           case 255: 
/* 1802:1492 */             if ((0x0 & l) != 0L) {
/* 1803:1493 */               jjCheckNAddTwoStates(255, 257);
/* 1804:     */             }
/* 1805:     */             break;
/* 1806:     */           case 258: 
/* 1807:1496 */             if ((0x0 & l) != 0L) {
/* 1808:1497 */               jjCheckNAddTwoStates(258, 260);
/* 1809:     */             }
/* 1810:     */             break;
/* 1811:     */           case 261: 
/* 1812:1500 */             if ((0x0 & l) != 0L) {
/* 1813:1501 */               jjCheckNAddTwoStates(261, 263);
/* 1814:     */             }
/* 1815:     */             break;
/* 1816:     */           case 264: 
/* 1817:1504 */             if ((0x0 & l) != 0L) {
/* 1818:1505 */               jjCheckNAddTwoStates(264, 266);
/* 1819:     */             }
/* 1820:     */             break;
/* 1821:     */           case 267: 
/* 1822:1508 */             if ((0x0 & l) != 0L) {
/* 1823:1509 */               jjCheckNAddTwoStates(267, 269);
/* 1824:     */             }
/* 1825:     */             break;
/* 1826:     */           case 270: 
/* 1827:1512 */             if ((0x0 & l) != 0L) {
/* 1828:1513 */               jjCheckNAddTwoStates(270, 272);
/* 1829:     */             }
/* 1830:     */             break;
/* 1831:     */           case 273: 
/* 1832:1516 */             if ((0x0 & l) != 0L) {
/* 1833:1517 */               jjCheckNAddTwoStates(273, 275);
/* 1834:     */             }
/* 1835:     */             break;
/* 1836:     */           case 276: 
/* 1837:1520 */             if ((0x0 & l) != 0L) {
/* 1838:1521 */               jjCheckNAddTwoStates(276, 278);
/* 1839:     */             }
/* 1840:     */             break;
/* 1841:     */           case 279: 
/* 1842:1524 */             if ((0x0 & l) != 0L) {
/* 1843:1525 */               jjCheckNAddTwoStates(279, 280);
/* 1844:     */             }
/* 1845:     */             break;
/* 1846:     */           case 280: 
/* 1847:1528 */             if ((this.curChar == '%') && (kind > 41)) {
/* 1848:1529 */               kind = 41;
/* 1849:     */             }
/* 1850:     */             break;
/* 1851:     */           case 281: 
/* 1852:1532 */             if ((0x0 & l) != 0L)
/* 1853:     */             {
/* 1854:1534 */               if (kind > 42) {
/* 1855:1535 */                 kind = 42;
/* 1856:     */               }
/* 1857:1536 */               jjCheckNAdd(281);
/* 1858:     */             }
/* 1859:1537 */             break;
/* 1860:     */           case 282: 
/* 1861:1539 */             if ((0x0 & l) != 0L)
/* 1862:     */             {
/* 1863:1541 */               if (kind > 46) {
/* 1864:1542 */                 kind = 46;
/* 1865:     */               }
/* 1866:1543 */               jjCheckNAdd(282);
/* 1867:     */             }
/* 1868:1544 */             break;
/* 1869:     */           case 283: 
/* 1870:1546 */             if ((0x0 & l) != 0L)
/* 1871:     */             {
/* 1872:1548 */               if (kind > 42) {
/* 1873:1549 */                 kind = 42;
/* 1874:     */               }
/* 1875:1550 */               jjCheckNAddStates(0, 41);
/* 1876:     */             }
/* 1877:1551 */             break;
/* 1878:     */           case 284: 
/* 1879:1553 */             if ((0x0 & l) != 0L) {
/* 1880:1554 */               jjCheckNAddTwoStates(284, 257);
/* 1881:     */             }
/* 1882:     */             break;
/* 1883:     */           case 285: 
/* 1884:1557 */             if ((0x0 & l) != 0L) {
/* 1885:1558 */               jjCheckNAddTwoStates(285, 286);
/* 1886:     */             }
/* 1887:     */             break;
/* 1888:     */           case 286: 
/* 1889:1561 */             if (this.curChar == '.') {
/* 1890:1562 */               jjCheckNAdd(255);
/* 1891:     */             }
/* 1892:     */             break;
/* 1893:     */           case 287: 
/* 1894:1565 */             if ((0x0 & l) != 0L) {
/* 1895:1566 */               jjCheckNAddTwoStates(287, 260);
/* 1896:     */             }
/* 1897:     */             break;
/* 1898:     */           case 288: 
/* 1899:1569 */             if ((0x0 & l) != 0L) {
/* 1900:1570 */               jjCheckNAddTwoStates(288, 289);
/* 1901:     */             }
/* 1902:     */             break;
/* 1903:     */           case 289: 
/* 1904:1573 */             if (this.curChar == '.') {
/* 1905:1574 */               jjCheckNAdd(258);
/* 1906:     */             }
/* 1907:     */             break;
/* 1908:     */           case 290: 
/* 1909:1577 */             if ((0x0 & l) != 0L) {
/* 1910:1578 */               jjCheckNAddTwoStates(290, 263);
/* 1911:     */             }
/* 1912:     */             break;
/* 1913:     */           case 291: 
/* 1914:1581 */             if ((0x0 & l) != 0L) {
/* 1915:1582 */               jjCheckNAddTwoStates(291, 292);
/* 1916:     */             }
/* 1917:     */             break;
/* 1918:     */           case 292: 
/* 1919:1585 */             if (this.curChar == '.') {
/* 1920:1586 */               jjCheckNAdd(261);
/* 1921:     */             }
/* 1922:     */             break;
/* 1923:     */           case 293: 
/* 1924:1589 */             if ((0x0 & l) != 0L) {
/* 1925:1590 */               jjCheckNAddTwoStates(293, 266);
/* 1926:     */             }
/* 1927:     */             break;
/* 1928:     */           case 294: 
/* 1929:1593 */             if ((0x0 & l) != 0L) {
/* 1930:1594 */               jjCheckNAddTwoStates(294, 295);
/* 1931:     */             }
/* 1932:     */             break;
/* 1933:     */           case 295: 
/* 1934:1597 */             if (this.curChar == '.') {
/* 1935:1598 */               jjCheckNAdd(264);
/* 1936:     */             }
/* 1937:     */             break;
/* 1938:     */           case 296: 
/* 1939:1601 */             if ((0x0 & l) != 0L) {
/* 1940:1602 */               jjCheckNAddTwoStates(296, 269);
/* 1941:     */             }
/* 1942:     */             break;
/* 1943:     */           case 297: 
/* 1944:1605 */             if ((0x0 & l) != 0L) {
/* 1945:1606 */               jjCheckNAddTwoStates(297, 298);
/* 1946:     */             }
/* 1947:     */             break;
/* 1948:     */           case 298: 
/* 1949:1609 */             if (this.curChar == '.') {
/* 1950:1610 */               jjCheckNAdd(267);
/* 1951:     */             }
/* 1952:     */             break;
/* 1953:     */           case 299: 
/* 1954:1613 */             if ((0x0 & l) != 0L) {
/* 1955:1614 */               jjCheckNAddTwoStates(299, 272);
/* 1956:     */             }
/* 1957:     */             break;
/* 1958:     */           case 300: 
/* 1959:1617 */             if ((0x0 & l) != 0L) {
/* 1960:1618 */               jjCheckNAddTwoStates(300, 301);
/* 1961:     */             }
/* 1962:     */             break;
/* 1963:     */           case 301: 
/* 1964:1621 */             if (this.curChar == '.') {
/* 1965:1622 */               jjCheckNAdd(270);
/* 1966:     */             }
/* 1967:     */             break;
/* 1968:     */           case 302: 
/* 1969:1625 */             if ((0x0 & l) != 0L) {
/* 1970:1626 */               jjCheckNAddTwoStates(302, 275);
/* 1971:     */             }
/* 1972:     */             break;
/* 1973:     */           case 303: 
/* 1974:1629 */             if ((0x0 & l) != 0L) {
/* 1975:1630 */               jjCheckNAddTwoStates(303, 304);
/* 1976:     */             }
/* 1977:     */             break;
/* 1978:     */           case 304: 
/* 1979:1633 */             if (this.curChar == '.') {
/* 1980:1634 */               jjCheckNAdd(273);
/* 1981:     */             }
/* 1982:     */             break;
/* 1983:     */           case 305: 
/* 1984:1637 */             if ((0x0 & l) != 0L) {
/* 1985:1638 */               jjCheckNAddTwoStates(305, 278);
/* 1986:     */             }
/* 1987:     */             break;
/* 1988:     */           case 306: 
/* 1989:1641 */             if ((0x0 & l) != 0L) {
/* 1990:1642 */               jjCheckNAddTwoStates(306, 307);
/* 1991:     */             }
/* 1992:     */             break;
/* 1993:     */           case 307: 
/* 1994:1645 */             if (this.curChar == '.') {
/* 1995:1646 */               jjCheckNAdd(276);
/* 1996:     */             }
/* 1997:     */             break;
/* 1998:     */           case 308: 
/* 1999:1649 */             if ((0x0 & l) != 0L) {
/* 2000:1650 */               jjCheckNAddTwoStates(308, 280);
/* 2001:     */             }
/* 2002:     */             break;
/* 2003:     */           case 309: 
/* 2004:1653 */             if ((0x0 & l) != 0L) {
/* 2005:1654 */               jjCheckNAddTwoStates(309, 310);
/* 2006:     */             }
/* 2007:     */             break;
/* 2008:     */           case 310: 
/* 2009:1657 */             if (this.curChar == '.') {
/* 2010:1658 */               jjCheckNAdd(279);
/* 2011:     */             }
/* 2012:     */             break;
/* 2013:     */           case 311: 
/* 2014:1661 */             if ((0x0 & l) != 0L)
/* 2015:     */             {
/* 2016:1663 */               if (kind > 42) {
/* 2017:1664 */                 kind = 42;
/* 2018:     */               }
/* 2019:1665 */               jjCheckNAdd(311);
/* 2020:     */             }
/* 2021:1666 */             break;
/* 2022:     */           case 312: 
/* 2023:1668 */             if ((0x0 & l) != 0L) {
/* 2024:1669 */               jjCheckNAddTwoStates(312, 313);
/* 2025:     */             }
/* 2026:     */             break;
/* 2027:     */           case 313: 
/* 2028:1672 */             if (this.curChar == '.') {
/* 2029:1673 */               jjCheckNAdd(281);
/* 2030:     */             }
/* 2031:     */             break;
/* 2032:     */           case 314: 
/* 2033:1676 */             if ((0x0 & l) != 0L)
/* 2034:     */             {
/* 2035:1678 */               if (kind > 46) {
/* 2036:1679 */                 kind = 46;
/* 2037:     */               }
/* 2038:1680 */               jjCheckNAdd(314);
/* 2039:     */             }
/* 2040:1681 */             break;
/* 2041:     */           case 315: 
/* 2042:1683 */             if ((0x0 & l) != 0L) {
/* 2043:1684 */               jjCheckNAddTwoStates(315, 316);
/* 2044:     */             }
/* 2045:     */             break;
/* 2046:     */           case 316: 
/* 2047:1687 */             if (this.curChar == '.') {
/* 2048:1688 */               jjCheckNAdd(282);
/* 2049:     */             }
/* 2050:     */             break;
/* 2051:     */           }
/* 2052:1692 */         } while (i != startsAt);
/* 2053:     */       }
/* 2054:1694 */       else if (this.curChar < '')
/* 2055:     */       {
/* 2056:1696 */         long l = 1L << (this.curChar & 0x3F);
/* 2057:     */         do
/* 2058:     */         {
/* 2059:1699 */           switch (this.jjstateSet[(--i)])
/* 2060:     */           {
/* 2061:     */           case 318: 
/* 2062:1702 */             if ((0x7FFFFFE & l) != 0L)
/* 2063:     */             {
/* 2064:1704 */               if (kind > 3) {
/* 2065:1705 */                 kind = 3;
/* 2066:     */               }
/* 2067:1706 */               jjCheckNAddTwoStates(2, 3);
/* 2068:     */             }
/* 2069:1708 */             else if (this.curChar == '\\')
/* 2070:     */             {
/* 2071:1709 */               jjCheckNAddTwoStates(4, 5);
/* 2072:     */             }
/* 2073:     */             break;
/* 2074:     */           case 1: 
/* 2075:1712 */             if ((0x7FFFFFE & l) != 0L)
/* 2076:     */             {
/* 2077:1714 */               if (kind > 3) {
/* 2078:1715 */                 kind = 3;
/* 2079:     */               }
/* 2080:1716 */               jjCheckNAddTwoStates(2, 3);
/* 2081:     */             }
/* 2082:1718 */             else if (this.curChar == '\\')
/* 2083:     */             {
/* 2084:1719 */               jjCheckNAddTwoStates(4, 120);
/* 2085:     */             }
/* 2086:1720 */             else if (this.curChar == '@')
/* 2087:     */             {
/* 2088:1721 */               jjAddStates(240, 241);
/* 2089:     */             }
/* 2090:1722 */             if ((0x200000 & l) != 0L) {
/* 2091:1723 */               jjAddStates(242, 243);
/* 2092:     */             }
/* 2093:     */             break;
/* 2094:     */           case 89: 
/* 2095:1726 */             if ((0x7FFFFFE & l) != 0L)
/* 2096:     */             {
/* 2097:1728 */               if (kind > 32) {
/* 2098:1729 */                 kind = 32;
/* 2099:     */               }
/* 2100:1730 */               jjCheckNAddTwoStates(90, 91);
/* 2101:     */             }
/* 2102:1732 */             else if (this.curChar == '\\')
/* 2103:     */             {
/* 2104:1733 */               jjCheckNAddTwoStates(92, 107);
/* 2105:     */             }
/* 2106:     */             break;
/* 2107:     */           case 319: 
/* 2108:1736 */             if ((0x7FFFFFE & l) != 0L)
/* 2109:     */             {
/* 2110:1738 */               if (kind > 32) {
/* 2111:1739 */                 kind = 32;
/* 2112:     */               }
/* 2113:1740 */               jjCheckNAddTwoStates(90, 91);
/* 2114:     */             }
/* 2115:1742 */             else if (this.curChar == '\\')
/* 2116:     */             {
/* 2117:1743 */               jjCheckNAddTwoStates(92, 93);
/* 2118:     */             }
/* 2119:     */             break;
/* 2120:     */           case 2: 
/* 2121:1746 */             if ((0x7FFFFFE & l) != 0L)
/* 2122:     */             {
/* 2123:1748 */               if (kind > 3) {
/* 2124:1749 */                 kind = 3;
/* 2125:     */               }
/* 2126:1750 */               jjCheckNAddTwoStates(2, 3);
/* 2127:     */             }
/* 2128:1751 */             break;
/* 2129:     */           case 3: 
/* 2130:1753 */             if (this.curChar == '\\') {
/* 2131:1754 */               jjCheckNAddTwoStates(4, 5);
/* 2132:     */             }
/* 2133:     */             break;
/* 2134:     */           case 4: 
/* 2135:1757 */             if ((0xFFFFFFFF & l) != 0L)
/* 2136:     */             {
/* 2137:1759 */               if (kind > 3) {
/* 2138:1760 */                 kind = 3;
/* 2139:     */               }
/* 2140:1761 */               jjCheckNAddTwoStates(2, 3);
/* 2141:     */             }
/* 2142:1762 */             break;
/* 2143:     */           case 5: 
/* 2144:1764 */             if ((0x7E & l) != 0L)
/* 2145:     */             {
/* 2146:1766 */               if (kind > 3) {
/* 2147:1767 */                 kind = 3;
/* 2148:     */               }
/* 2149:1768 */               jjCheckNAddStates(59, 66);
/* 2150:     */             }
/* 2151:1769 */             break;
/* 2152:     */           case 6: 
/* 2153:1771 */             if ((0x7E & l) != 0L)
/* 2154:     */             {
/* 2155:1773 */               if (kind > 3) {
/* 2156:1774 */                 kind = 3;
/* 2157:     */               }
/* 2158:1775 */               jjCheckNAddStates(67, 69);
/* 2159:     */             }
/* 2160:1776 */             break;
/* 2161:     */           case 8: 
/* 2162:     */           case 10: 
/* 2163:     */           case 13: 
/* 2164:     */           case 17: 
/* 2165:1781 */             if ((0x7E & l) != 0L) {
/* 2166:1782 */               jjCheckNAdd(6);
/* 2167:     */             }
/* 2168:     */             break;
/* 2169:     */           case 9: 
/* 2170:1785 */             if ((0x7E & l) != 0L) {
/* 2171:1786 */               this.jjstateSet[(this.jjnewStateCnt++)] = 10;
/* 2172:     */             }
/* 2173:     */             break;
/* 2174:     */           case 11: 
/* 2175:1789 */             if ((0x7E & l) != 0L) {
/* 2176:1790 */               this.jjstateSet[(this.jjnewStateCnt++)] = 12;
/* 2177:     */             }
/* 2178:     */             break;
/* 2179:     */           case 12: 
/* 2180:1793 */             if ((0x7E & l) != 0L) {
/* 2181:1794 */               this.jjstateSet[(this.jjnewStateCnt++)] = 13;
/* 2182:     */             }
/* 2183:     */             break;
/* 2184:     */           case 14: 
/* 2185:1797 */             if ((0x7E & l) != 0L) {
/* 2186:1798 */               this.jjstateSet[(this.jjnewStateCnt++)] = 15;
/* 2187:     */             }
/* 2188:     */             break;
/* 2189:     */           case 15: 
/* 2190:1801 */             if ((0x7E & l) != 0L) {
/* 2191:1802 */               this.jjstateSet[(this.jjnewStateCnt++)] = 16;
/* 2192:     */             }
/* 2193:     */             break;
/* 2194:     */           case 16: 
/* 2195:1805 */             if ((0x7E & l) != 0L) {
/* 2196:1806 */               this.jjstateSet[(this.jjnewStateCnt++)] = 17;
/* 2197:     */             }
/* 2198:     */             break;
/* 2199:     */           case 19: 
/* 2200:1809 */             if ((0x7FFFFFE & l) != 0L)
/* 2201:     */             {
/* 2202:1811 */               if (kind > 9) {
/* 2203:1812 */                 kind = 9;
/* 2204:     */               }
/* 2205:1813 */               jjCheckNAddTwoStates(19, 20);
/* 2206:     */             }
/* 2207:1814 */             break;
/* 2208:     */           case 20: 
/* 2209:1816 */             if (this.curChar == '\\') {
/* 2210:1817 */               jjAddStates(244, 245);
/* 2211:     */             }
/* 2212:     */             break;
/* 2213:     */           case 21: 
/* 2214:1820 */             if ((0xFFFFFFFF & l) != 0L)
/* 2215:     */             {
/* 2216:1822 */               if (kind > 9) {
/* 2217:1823 */                 kind = 9;
/* 2218:     */               }
/* 2219:1824 */               jjCheckNAddTwoStates(19, 20);
/* 2220:     */             }
/* 2221:1825 */             break;
/* 2222:     */           case 22: 
/* 2223:1827 */             if ((0x7E & l) != 0L)
/* 2224:     */             {
/* 2225:1829 */               if (kind > 9) {
/* 2226:1830 */                 kind = 9;
/* 2227:     */               }
/* 2228:1831 */               jjCheckNAddStates(70, 77);
/* 2229:     */             }
/* 2230:1832 */             break;
/* 2231:     */           case 23: 
/* 2232:1834 */             if ((0x7E & l) != 0L)
/* 2233:     */             {
/* 2234:1836 */               if (kind > 9) {
/* 2235:1837 */                 kind = 9;
/* 2236:     */               }
/* 2237:1838 */               jjCheckNAddStates(78, 80);
/* 2238:     */             }
/* 2239:1839 */             break;
/* 2240:     */           case 25: 
/* 2241:     */           case 27: 
/* 2242:     */           case 30: 
/* 2243:     */           case 34: 
/* 2244:1844 */             if ((0x7E & l) != 0L) {
/* 2245:1845 */               jjCheckNAdd(23);
/* 2246:     */             }
/* 2247:     */             break;
/* 2248:     */           case 26: 
/* 2249:1848 */             if ((0x7E & l) != 0L) {
/* 2250:1849 */               this.jjstateSet[(this.jjnewStateCnt++)] = 27;
/* 2251:     */             }
/* 2252:     */             break;
/* 2253:     */           case 28: 
/* 2254:1852 */             if ((0x7E & l) != 0L) {
/* 2255:1853 */               this.jjstateSet[(this.jjnewStateCnt++)] = 29;
/* 2256:     */             }
/* 2257:     */             break;
/* 2258:     */           case 29: 
/* 2259:1856 */             if ((0x7E & l) != 0L) {
/* 2260:1857 */               this.jjstateSet[(this.jjnewStateCnt++)] = 30;
/* 2261:     */             }
/* 2262:     */             break;
/* 2263:     */           case 31: 
/* 2264:1860 */             if ((0x7E & l) != 0L) {
/* 2265:1861 */               this.jjstateSet[(this.jjnewStateCnt++)] = 32;
/* 2266:     */             }
/* 2267:     */             break;
/* 2268:     */           case 32: 
/* 2269:1864 */             if ((0x7E & l) != 0L) {
/* 2270:1865 */               this.jjstateSet[(this.jjnewStateCnt++)] = 33;
/* 2271:     */             }
/* 2272:     */             break;
/* 2273:     */           case 33: 
/* 2274:1868 */             if ((0x7E & l) != 0L) {
/* 2275:1869 */               this.jjstateSet[(this.jjnewStateCnt++)] = 34;
/* 2276:     */             }
/* 2277:     */             break;
/* 2278:     */           case 36: 
/* 2279:1872 */             if ((0xEFFFFFFF & l) != 0L) {
/* 2280:1873 */               jjCheckNAddStates(56, 58);
/* 2281:     */             }
/* 2282:     */             break;
/* 2283:     */           case 38: 
/* 2284:1876 */             if (this.curChar == '\\') {
/* 2285:1877 */               jjAddStates(246, 249);
/* 2286:     */             }
/* 2287:     */             break;
/* 2288:     */           case 42: 
/* 2289:1880 */             if ((0xFFFFFFFF & l) != 0L) {
/* 2290:1881 */               jjCheckNAddStates(56, 58);
/* 2291:     */             }
/* 2292:     */             break;
/* 2293:     */           case 43: 
/* 2294:1884 */             if ((0x7E & l) != 0L) {
/* 2295:1885 */               jjCheckNAddStates(81, 89);
/* 2296:     */             }
/* 2297:     */             break;
/* 2298:     */           case 44: 
/* 2299:1888 */             if ((0x7E & l) != 0L) {
/* 2300:1889 */               jjCheckNAddStates(90, 93);
/* 2301:     */             }
/* 2302:     */             break;
/* 2303:     */           case 46: 
/* 2304:     */           case 48: 
/* 2305:     */           case 51: 
/* 2306:     */           case 55: 
/* 2307:1895 */             if ((0x7E & l) != 0L) {
/* 2308:1896 */               jjCheckNAdd(44);
/* 2309:     */             }
/* 2310:     */             break;
/* 2311:     */           case 47: 
/* 2312:1899 */             if ((0x7E & l) != 0L) {
/* 2313:1900 */               this.jjstateSet[(this.jjnewStateCnt++)] = 48;
/* 2314:     */             }
/* 2315:     */             break;
/* 2316:     */           case 49: 
/* 2317:1903 */             if ((0x7E & l) != 0L) {
/* 2318:1904 */               this.jjstateSet[(this.jjnewStateCnt++)] = 50;
/* 2319:     */             }
/* 2320:     */             break;
/* 2321:     */           case 50: 
/* 2322:1907 */             if ((0x7E & l) != 0L) {
/* 2323:1908 */               this.jjstateSet[(this.jjnewStateCnt++)] = 51;
/* 2324:     */             }
/* 2325:     */             break;
/* 2326:     */           case 52: 
/* 2327:1911 */             if ((0x7E & l) != 0L) {
/* 2328:1912 */               this.jjstateSet[(this.jjnewStateCnt++)] = 53;
/* 2329:     */             }
/* 2330:     */             break;
/* 2331:     */           case 53: 
/* 2332:1915 */             if ((0x7E & l) != 0L) {
/* 2333:1916 */               this.jjstateSet[(this.jjnewStateCnt++)] = 54;
/* 2334:     */             }
/* 2335:     */             break;
/* 2336:     */           case 54: 
/* 2337:1919 */             if ((0x7E & l) != 0L) {
/* 2338:1920 */               this.jjstateSet[(this.jjnewStateCnt++)] = 55;
/* 2339:     */             }
/* 2340:     */             break;
/* 2341:     */           case 57: 
/* 2342:1923 */             if ((0xEFFFFFFF & l) != 0L) {
/* 2343:1924 */               jjCheckNAddStates(53, 55);
/* 2344:     */             }
/* 2345:     */             break;
/* 2346:     */           case 59: 
/* 2347:1927 */             if (this.curChar == '\\') {
/* 2348:1928 */               jjAddStates(250, 253);
/* 2349:     */             }
/* 2350:     */             break;
/* 2351:     */           case 63: 
/* 2352:1931 */             if ((0xFFFFFFFF & l) != 0L) {
/* 2353:1932 */               jjCheckNAddStates(53, 55);
/* 2354:     */             }
/* 2355:     */             break;
/* 2356:     */           case 64: 
/* 2357:1935 */             if ((0x7E & l) != 0L) {
/* 2358:1936 */               jjCheckNAddStates(94, 102);
/* 2359:     */             }
/* 2360:     */             break;
/* 2361:     */           case 65: 
/* 2362:1939 */             if ((0x7E & l) != 0L) {
/* 2363:1940 */               jjCheckNAddStates(103, 106);
/* 2364:     */             }
/* 2365:     */             break;
/* 2366:     */           case 67: 
/* 2367:     */           case 69: 
/* 2368:     */           case 72: 
/* 2369:     */           case 76: 
/* 2370:1946 */             if ((0x7E & l) != 0L) {
/* 2371:1947 */               jjCheckNAdd(65);
/* 2372:     */             }
/* 2373:     */             break;
/* 2374:     */           case 68: 
/* 2375:1950 */             if ((0x7E & l) != 0L) {
/* 2376:1951 */               this.jjstateSet[(this.jjnewStateCnt++)] = 69;
/* 2377:     */             }
/* 2378:     */             break;
/* 2379:     */           case 70: 
/* 2380:1954 */             if ((0x7E & l) != 0L) {
/* 2381:1955 */               this.jjstateSet[(this.jjnewStateCnt++)] = 71;
/* 2382:     */             }
/* 2383:     */             break;
/* 2384:     */           case 71: 
/* 2385:1958 */             if ((0x7E & l) != 0L) {
/* 2386:1959 */               this.jjstateSet[(this.jjnewStateCnt++)] = 72;
/* 2387:     */             }
/* 2388:     */             break;
/* 2389:     */           case 73: 
/* 2390:1962 */             if ((0x7E & l) != 0L) {
/* 2391:1963 */               this.jjstateSet[(this.jjnewStateCnt++)] = 74;
/* 2392:     */             }
/* 2393:     */             break;
/* 2394:     */           case 74: 
/* 2395:1966 */             if ((0x7E & l) != 0L) {
/* 2396:1967 */               this.jjstateSet[(this.jjnewStateCnt++)] = 75;
/* 2397:     */             }
/* 2398:     */             break;
/* 2399:     */           case 75: 
/* 2400:1970 */             if ((0x7E & l) != 0L) {
/* 2401:1971 */               this.jjstateSet[(this.jjnewStateCnt++)] = 76;
/* 2402:     */             }
/* 2403:     */             break;
/* 2404:     */           case 79: 
/* 2405:1974 */             if (((0x100000 & l) != 0L) && (kind > 31)) {
/* 2406:1975 */               kind = 31;
/* 2407:     */             }
/* 2408:     */             break;
/* 2409:     */           case 80: 
/* 2410:1978 */             if ((0x4000 & l) != 0L) {
/* 2411:1979 */               this.jjstateSet[(this.jjnewStateCnt++)] = 79;
/* 2412:     */             }
/* 2413:     */             break;
/* 2414:     */           case 81: 
/* 2415:1982 */             if ((0x2 & l) != 0L) {
/* 2416:1983 */               this.jjstateSet[(this.jjnewStateCnt++)] = 80;
/* 2417:     */             }
/* 2418:     */             break;
/* 2419:     */           case 82: 
/* 2420:1986 */             if ((0x100000 & l) != 0L) {
/* 2421:1987 */               this.jjstateSet[(this.jjnewStateCnt++)] = 81;
/* 2422:     */             }
/* 2423:     */             break;
/* 2424:     */           case 83: 
/* 2425:1990 */             if ((0x40000 & l) != 0L) {
/* 2426:1991 */               this.jjstateSet[(this.jjnewStateCnt++)] = 82;
/* 2427:     */             }
/* 2428:     */             break;
/* 2429:     */           case 84: 
/* 2430:1994 */             if ((0x8000 & l) != 0L) {
/* 2431:1995 */               this.jjstateSet[(this.jjnewStateCnt++)] = 83;
/* 2432:     */             }
/* 2433:     */             break;
/* 2434:     */           case 85: 
/* 2435:1998 */             if ((0x10000 & l) != 0L) {
/* 2436:1999 */               this.jjstateSet[(this.jjnewStateCnt++)] = 84;
/* 2437:     */             }
/* 2438:     */             break;
/* 2439:     */           case 86: 
/* 2440:2002 */             if ((0x2000 & l) != 0L) {
/* 2441:2003 */               this.jjstateSet[(this.jjnewStateCnt++)] = 85;
/* 2442:     */             }
/* 2443:     */             break;
/* 2444:     */           case 87: 
/* 2445:2006 */             if ((0x200 & l) != 0L) {
/* 2446:2007 */               this.jjstateSet[(this.jjnewStateCnt++)] = 86;
/* 2447:     */             }
/* 2448:     */             break;
/* 2449:     */           case 88: 
/* 2450:2010 */             if (this.curChar == '@') {
/* 2451:2011 */               jjAddStates(240, 241);
/* 2452:     */             }
/* 2453:     */             break;
/* 2454:     */           case 90: 
/* 2455:2014 */             if ((0x7FFFFFE & l) != 0L)
/* 2456:     */             {
/* 2457:2016 */               if (kind > 32) {
/* 2458:2017 */                 kind = 32;
/* 2459:     */               }
/* 2460:2018 */               jjCheckNAddTwoStates(90, 91);
/* 2461:     */             }
/* 2462:2019 */             break;
/* 2463:     */           case 91: 
/* 2464:2021 */             if (this.curChar == '\\') {
/* 2465:2022 */               jjCheckNAddTwoStates(92, 93);
/* 2466:     */             }
/* 2467:     */             break;
/* 2468:     */           case 92: 
/* 2469:2025 */             if ((0xFFFFFFFF & l) != 0L)
/* 2470:     */             {
/* 2471:2027 */               if (kind > 32) {
/* 2472:2028 */                 kind = 32;
/* 2473:     */               }
/* 2474:2029 */               jjCheckNAddTwoStates(90, 91);
/* 2475:     */             }
/* 2476:2030 */             break;
/* 2477:     */           case 93: 
/* 2478:2032 */             if ((0x7E & l) != 0L)
/* 2479:     */             {
/* 2480:2034 */               if (kind > 32) {
/* 2481:2035 */                 kind = 32;
/* 2482:     */               }
/* 2483:2036 */               jjCheckNAddStates(107, 114);
/* 2484:     */             }
/* 2485:2037 */             break;
/* 2486:     */           case 94: 
/* 2487:2039 */             if ((0x7E & l) != 0L)
/* 2488:     */             {
/* 2489:2041 */               if (kind > 32) {
/* 2490:2042 */                 kind = 32;
/* 2491:     */               }
/* 2492:2043 */               jjCheckNAddStates(115, 117);
/* 2493:     */             }
/* 2494:2044 */             break;
/* 2495:     */           case 96: 
/* 2496:     */           case 98: 
/* 2497:     */           case 101: 
/* 2498:     */           case 105: 
/* 2499:2049 */             if ((0x7E & l) != 0L) {
/* 2500:2050 */               jjCheckNAdd(94);
/* 2501:     */             }
/* 2502:     */             break;
/* 2503:     */           case 97: 
/* 2504:2053 */             if ((0x7E & l) != 0L) {
/* 2505:2054 */               this.jjstateSet[(this.jjnewStateCnt++)] = 98;
/* 2506:     */             }
/* 2507:     */             break;
/* 2508:     */           case 99: 
/* 2509:2057 */             if ((0x7E & l) != 0L) {
/* 2510:2058 */               this.jjstateSet[(this.jjnewStateCnt++)] = 100;
/* 2511:     */             }
/* 2512:     */             break;
/* 2513:     */           case 100: 
/* 2514:2061 */             if ((0x7E & l) != 0L) {
/* 2515:2062 */               this.jjstateSet[(this.jjnewStateCnt++)] = 101;
/* 2516:     */             }
/* 2517:     */             break;
/* 2518:     */           case 102: 
/* 2519:2065 */             if ((0x7E & l) != 0L) {
/* 2520:2066 */               this.jjstateSet[(this.jjnewStateCnt++)] = 103;
/* 2521:     */             }
/* 2522:     */             break;
/* 2523:     */           case 103: 
/* 2524:2069 */             if ((0x7E & l) != 0L) {
/* 2525:2070 */               this.jjstateSet[(this.jjnewStateCnt++)] = 104;
/* 2526:     */             }
/* 2527:     */             break;
/* 2528:     */           case 104: 
/* 2529:2073 */             if ((0x7E & l) != 0L) {
/* 2530:2074 */               this.jjstateSet[(this.jjnewStateCnt++)] = 105;
/* 2531:     */             }
/* 2532:     */             break;
/* 2533:     */           case 106: 
/* 2534:2077 */             if (this.curChar == '\\') {
/* 2535:2078 */               jjCheckNAddTwoStates(92, 107);
/* 2536:     */             }
/* 2537:     */             break;
/* 2538:     */           case 107: 
/* 2539:2081 */             if ((0x7E & l) != 0L)
/* 2540:     */             {
/* 2541:2083 */               if (kind > 32) {
/* 2542:2084 */                 kind = 32;
/* 2543:     */               }
/* 2544:2085 */               jjCheckNAddStates(118, 125);
/* 2545:     */             }
/* 2546:2086 */             break;
/* 2547:     */           case 108: 
/* 2548:2088 */             if ((0x7E & l) != 0L)
/* 2549:     */             {
/* 2550:2090 */               if (kind > 32) {
/* 2551:2091 */                 kind = 32;
/* 2552:     */               }
/* 2553:2092 */               jjCheckNAddStates(126, 128);
/* 2554:     */             }
/* 2555:2093 */             break;
/* 2556:     */           case 109: 
/* 2557:     */           case 111: 
/* 2558:     */           case 114: 
/* 2559:     */           case 118: 
/* 2560:2098 */             if ((0x7E & l) != 0L) {
/* 2561:2099 */               jjCheckNAdd(108);
/* 2562:     */             }
/* 2563:     */             break;
/* 2564:     */           case 110: 
/* 2565:2102 */             if ((0x7E & l) != 0L) {
/* 2566:2103 */               this.jjstateSet[(this.jjnewStateCnt++)] = 111;
/* 2567:     */             }
/* 2568:     */             break;
/* 2569:     */           case 112: 
/* 2570:2106 */             if ((0x7E & l) != 0L) {
/* 2571:2107 */               this.jjstateSet[(this.jjnewStateCnt++)] = 113;
/* 2572:     */             }
/* 2573:     */             break;
/* 2574:     */           case 113: 
/* 2575:2110 */             if ((0x7E & l) != 0L) {
/* 2576:2111 */               this.jjstateSet[(this.jjnewStateCnt++)] = 114;
/* 2577:     */             }
/* 2578:     */             break;
/* 2579:     */           case 115: 
/* 2580:2114 */             if ((0x7E & l) != 0L) {
/* 2581:2115 */               this.jjstateSet[(this.jjnewStateCnt++)] = 116;
/* 2582:     */             }
/* 2583:     */             break;
/* 2584:     */           case 116: 
/* 2585:2118 */             if ((0x7E & l) != 0L) {
/* 2586:2119 */               this.jjstateSet[(this.jjnewStateCnt++)] = 117;
/* 2587:     */             }
/* 2588:     */             break;
/* 2589:     */           case 117: 
/* 2590:2122 */             if ((0x7E & l) != 0L) {
/* 2591:2123 */               this.jjstateSet[(this.jjnewStateCnt++)] = 118;
/* 2592:     */             }
/* 2593:     */             break;
/* 2594:     */           case 119: 
/* 2595:2126 */             if (this.curChar == '\\') {
/* 2596:2127 */               jjCheckNAddTwoStates(4, 120);
/* 2597:     */             }
/* 2598:     */             break;
/* 2599:     */           case 120: 
/* 2600:2130 */             if ((0x7E & l) != 0L)
/* 2601:     */             {
/* 2602:2132 */               if (kind > 3) {
/* 2603:2133 */                 kind = 3;
/* 2604:     */               }
/* 2605:2134 */               jjCheckNAddStates(129, 136);
/* 2606:     */             }
/* 2607:2135 */             break;
/* 2608:     */           case 121: 
/* 2609:2137 */             if ((0x7E & l) != 0L)
/* 2610:     */             {
/* 2611:2139 */               if (kind > 3) {
/* 2612:2140 */                 kind = 3;
/* 2613:     */               }
/* 2614:2141 */               jjCheckNAddStates(137, 139);
/* 2615:     */             }
/* 2616:2142 */             break;
/* 2617:     */           case 122: 
/* 2618:     */           case 124: 
/* 2619:     */           case 127: 
/* 2620:     */           case 131: 
/* 2621:2147 */             if ((0x7E & l) != 0L) {
/* 2622:2148 */               jjCheckNAdd(121);
/* 2623:     */             }
/* 2624:     */             break;
/* 2625:     */           case 123: 
/* 2626:2151 */             if ((0x7E & l) != 0L) {
/* 2627:2152 */               this.jjstateSet[(this.jjnewStateCnt++)] = 124;
/* 2628:     */             }
/* 2629:     */             break;
/* 2630:     */           case 125: 
/* 2631:2155 */             if ((0x7E & l) != 0L) {
/* 2632:2156 */               this.jjstateSet[(this.jjnewStateCnt++)] = 126;
/* 2633:     */             }
/* 2634:     */             break;
/* 2635:     */           case 126: 
/* 2636:2159 */             if ((0x7E & l) != 0L) {
/* 2637:2160 */               this.jjstateSet[(this.jjnewStateCnt++)] = 127;
/* 2638:     */             }
/* 2639:     */             break;
/* 2640:     */           case 128: 
/* 2641:2163 */             if ((0x7E & l) != 0L) {
/* 2642:2164 */               this.jjstateSet[(this.jjnewStateCnt++)] = 129;
/* 2643:     */             }
/* 2644:     */             break;
/* 2645:     */           case 129: 
/* 2646:2167 */             if ((0x7E & l) != 0L) {
/* 2647:2168 */               this.jjstateSet[(this.jjnewStateCnt++)] = 130;
/* 2648:     */             }
/* 2649:     */             break;
/* 2650:     */           case 130: 
/* 2651:2171 */             if ((0x7E & l) != 0L) {
/* 2652:2172 */               this.jjstateSet[(this.jjnewStateCnt++)] = 131;
/* 2653:     */             }
/* 2654:     */             break;
/* 2655:     */           case 132: 
/* 2656:2175 */             if ((0x200000 & l) != 0L) {
/* 2657:2176 */               jjAddStates(242, 243);
/* 2658:     */             }
/* 2659:     */             break;
/* 2660:     */           case 134: 
/* 2661:2179 */             if ((0xEFFFFFFF & l) != 0L) {
/* 2662:2180 */               jjCheckNAddStates(146, 149);
/* 2663:     */             }
/* 2664:     */             break;
/* 2665:     */           case 137: 
/* 2666:2183 */             if (this.curChar == '\\') {
/* 2667:2184 */               jjAddStates(254, 255);
/* 2668:     */             }
/* 2669:     */             break;
/* 2670:     */           case 138: 
/* 2671:2187 */             if ((0xFFFFFFFF & l) != 0L) {
/* 2672:2188 */               jjCheckNAddStates(146, 149);
/* 2673:     */             }
/* 2674:     */             break;
/* 2675:     */           case 139: 
/* 2676:2191 */             if ((0x7E & l) != 0L) {
/* 2677:2192 */               jjCheckNAddStates(150, 158);
/* 2678:     */             }
/* 2679:     */             break;
/* 2680:     */           case 140: 
/* 2681:2195 */             if ((0x7E & l) != 0L) {
/* 2682:2196 */               jjCheckNAddStates(159, 162);
/* 2683:     */             }
/* 2684:     */             break;
/* 2685:     */           case 142: 
/* 2686:     */           case 144: 
/* 2687:     */           case 147: 
/* 2688:     */           case 151: 
/* 2689:2202 */             if ((0x7E & l) != 0L) {
/* 2690:2203 */               jjCheckNAdd(140);
/* 2691:     */             }
/* 2692:     */             break;
/* 2693:     */           case 143: 
/* 2694:2206 */             if ((0x7E & l) != 0L) {
/* 2695:2207 */               this.jjstateSet[(this.jjnewStateCnt++)] = 144;
/* 2696:     */             }
/* 2697:     */             break;
/* 2698:     */           case 145: 
/* 2699:2210 */             if ((0x7E & l) != 0L) {
/* 2700:2211 */               this.jjstateSet[(this.jjnewStateCnt++)] = 146;
/* 2701:     */             }
/* 2702:     */             break;
/* 2703:     */           case 146: 
/* 2704:2214 */             if ((0x7E & l) != 0L) {
/* 2705:2215 */               this.jjstateSet[(this.jjnewStateCnt++)] = 147;
/* 2706:     */             }
/* 2707:     */             break;
/* 2708:     */           case 148: 
/* 2709:2218 */             if ((0x7E & l) != 0L) {
/* 2710:2219 */               this.jjstateSet[(this.jjnewStateCnt++)] = 149;
/* 2711:     */             }
/* 2712:     */             break;
/* 2713:     */           case 149: 
/* 2714:2222 */             if ((0x7E & l) != 0L) {
/* 2715:2223 */               this.jjstateSet[(this.jjnewStateCnt++)] = 150;
/* 2716:     */             }
/* 2717:     */             break;
/* 2718:     */           case 150: 
/* 2719:2226 */             if ((0x7E & l) != 0L) {
/* 2720:2227 */               this.jjstateSet[(this.jjnewStateCnt++)] = 151;
/* 2721:     */             }
/* 2722:     */             break;
/* 2723:     */           case 153: 
/* 2724:2230 */             if ((0xEFFFFFFF & l) != 0L) {
/* 2725:2231 */               jjCheckNAddStates(163, 165);
/* 2726:     */             }
/* 2727:     */             break;
/* 2728:     */           case 155: 
/* 2729:2234 */             if (this.curChar == '\\') {
/* 2730:2235 */               jjAddStates(256, 259);
/* 2731:     */             }
/* 2732:     */             break;
/* 2733:     */           case 159: 
/* 2734:2238 */             if ((0xFFFFFFFF & l) != 0L) {
/* 2735:2239 */               jjCheckNAddStates(163, 165);
/* 2736:     */             }
/* 2737:     */             break;
/* 2738:     */           case 160: 
/* 2739:2242 */             if ((0x7E & l) != 0L) {
/* 2740:2243 */               jjCheckNAddStates(166, 174);
/* 2741:     */             }
/* 2742:     */             break;
/* 2743:     */           case 161: 
/* 2744:2246 */             if ((0x7E & l) != 0L) {
/* 2745:2247 */               jjCheckNAddStates(175, 178);
/* 2746:     */             }
/* 2747:     */             break;
/* 2748:     */           case 163: 
/* 2749:     */           case 165: 
/* 2750:     */           case 168: 
/* 2751:     */           case 172: 
/* 2752:2253 */             if ((0x7E & l) != 0L) {
/* 2753:2254 */               jjCheckNAdd(161);
/* 2754:     */             }
/* 2755:     */             break;
/* 2756:     */           case 164: 
/* 2757:2257 */             if ((0x7E & l) != 0L) {
/* 2758:2258 */               this.jjstateSet[(this.jjnewStateCnt++)] = 165;
/* 2759:     */             }
/* 2760:     */             break;
/* 2761:     */           case 166: 
/* 2762:2261 */             if ((0x7E & l) != 0L) {
/* 2763:2262 */               this.jjstateSet[(this.jjnewStateCnt++)] = 167;
/* 2764:     */             }
/* 2765:     */             break;
/* 2766:     */           case 167: 
/* 2767:2265 */             if ((0x7E & l) != 0L) {
/* 2768:2266 */               this.jjstateSet[(this.jjnewStateCnt++)] = 168;
/* 2769:     */             }
/* 2770:     */             break;
/* 2771:     */           case 169: 
/* 2772:2269 */             if ((0x7E & l) != 0L) {
/* 2773:2270 */               this.jjstateSet[(this.jjnewStateCnt++)] = 170;
/* 2774:     */             }
/* 2775:     */             break;
/* 2776:     */           case 170: 
/* 2777:2273 */             if ((0x7E & l) != 0L) {
/* 2778:2274 */               this.jjstateSet[(this.jjnewStateCnt++)] = 171;
/* 2779:     */             }
/* 2780:     */             break;
/* 2781:     */           case 171: 
/* 2782:2277 */             if ((0x7E & l) != 0L) {
/* 2783:2278 */               this.jjstateSet[(this.jjnewStateCnt++)] = 172;
/* 2784:     */             }
/* 2785:     */             break;
/* 2786:     */           case 174: 
/* 2787:2281 */             if ((0xEFFFFFFF & l) != 0L) {
/* 2788:2282 */               jjCheckNAddStates(179, 181);
/* 2789:     */             }
/* 2790:     */             break;
/* 2791:     */           case 176: 
/* 2792:2285 */             if (this.curChar == '\\') {
/* 2793:2286 */               jjAddStates(260, 263);
/* 2794:     */             }
/* 2795:     */             break;
/* 2796:     */           case 180: 
/* 2797:2289 */             if ((0xFFFFFFFF & l) != 0L) {
/* 2798:2290 */               jjCheckNAddStates(179, 181);
/* 2799:     */             }
/* 2800:     */             break;
/* 2801:     */           case 181: 
/* 2802:2293 */             if ((0x7E & l) != 0L) {
/* 2803:2294 */               jjCheckNAddStates(182, 190);
/* 2804:     */             }
/* 2805:     */             break;
/* 2806:     */           case 182: 
/* 2807:2297 */             if ((0x7E & l) != 0L) {
/* 2808:2298 */               jjCheckNAddStates(191, 194);
/* 2809:     */             }
/* 2810:     */             break;
/* 2811:     */           case 184: 
/* 2812:     */           case 186: 
/* 2813:     */           case 189: 
/* 2814:     */           case 193: 
/* 2815:2304 */             if ((0x7E & l) != 0L) {
/* 2816:2305 */               jjCheckNAdd(182);
/* 2817:     */             }
/* 2818:     */             break;
/* 2819:     */           case 185: 
/* 2820:2308 */             if ((0x7E & l) != 0L) {
/* 2821:2309 */               this.jjstateSet[(this.jjnewStateCnt++)] = 186;
/* 2822:     */             }
/* 2823:     */             break;
/* 2824:     */           case 187: 
/* 2825:2312 */             if ((0x7E & l) != 0L) {
/* 2826:2313 */               this.jjstateSet[(this.jjnewStateCnt++)] = 188;
/* 2827:     */             }
/* 2828:     */             break;
/* 2829:     */           case 188: 
/* 2830:2316 */             if ((0x7E & l) != 0L) {
/* 2831:2317 */               this.jjstateSet[(this.jjnewStateCnt++)] = 189;
/* 2832:     */             }
/* 2833:     */             break;
/* 2834:     */           case 190: 
/* 2835:2320 */             if ((0x7E & l) != 0L) {
/* 2836:2321 */               this.jjstateSet[(this.jjnewStateCnt++)] = 191;
/* 2837:     */             }
/* 2838:     */             break;
/* 2839:     */           case 191: 
/* 2840:2324 */             if ((0x7E & l) != 0L) {
/* 2841:2325 */               this.jjstateSet[(this.jjnewStateCnt++)] = 192;
/* 2842:     */             }
/* 2843:     */             break;
/* 2844:     */           case 192: 
/* 2845:2328 */             if ((0x7E & l) != 0L) {
/* 2846:2329 */               this.jjstateSet[(this.jjnewStateCnt++)] = 193;
/* 2847:     */             }
/* 2848:     */             break;
/* 2849:     */           case 195: 
/* 2850:2332 */             if ((0x1000 & l) != 0L) {
/* 2851:2333 */               this.jjstateSet[(this.jjnewStateCnt++)] = 133;
/* 2852:     */             }
/* 2853:     */             break;
/* 2854:     */           case 196: 
/* 2855:2336 */             if ((0x40000 & l) != 0L) {
/* 2856:2337 */               this.jjstateSet[(this.jjnewStateCnt++)] = 195;
/* 2857:     */             }
/* 2858:     */             break;
/* 2859:     */           case 199: 
/* 2860:2340 */             if ((0x7E & l) != 0L)
/* 2861:     */             {
/* 2862:2342 */               if (kind > 47) {
/* 2863:2343 */                 kind = 47;
/* 2864:     */               }
/* 2865:2344 */               jjCheckNAddStates(205, 213);
/* 2866:     */             }
/* 2867:2345 */             break;
/* 2868:     */           case 200: 
/* 2869:2347 */             if ((0x7E & l) != 0L) {
/* 2870:2348 */               jjCheckNAdd(201);
/* 2871:     */             }
/* 2872:     */             break;
/* 2873:     */           case 202: 
/* 2874:2351 */             if ((0x7E & l) != 0L)
/* 2875:     */             {
/* 2876:2353 */               if (kind > 47) {
/* 2877:2354 */                 kind = 47;
/* 2878:     */               }
/* 2879:2355 */               jjCheckNAddStates(214, 218);
/* 2880:     */             }
/* 2881:2356 */             break;
/* 2882:     */           case 203: 
/* 2883:2358 */             if (((0x7E & l) != 0L) && (kind > 47)) {
/* 2884:2359 */               kind = 47;
/* 2885:     */             }
/* 2886:     */             break;
/* 2887:     */           case 204: 
/* 2888:     */           case 206: 
/* 2889:     */           case 209: 
/* 2890:     */           case 213: 
/* 2891:2365 */             if ((0x7E & l) != 0L) {
/* 2892:2366 */               jjCheckNAdd(203);
/* 2893:     */             }
/* 2894:     */             break;
/* 2895:     */           case 205: 
/* 2896:2369 */             if ((0x7E & l) != 0L) {
/* 2897:2370 */               this.jjstateSet[(this.jjnewStateCnt++)] = 206;
/* 2898:     */             }
/* 2899:     */             break;
/* 2900:     */           case 207: 
/* 2901:2373 */             if ((0x7E & l) != 0L) {
/* 2902:2374 */               this.jjstateSet[(this.jjnewStateCnt++)] = 208;
/* 2903:     */             }
/* 2904:     */             break;
/* 2905:     */           case 208: 
/* 2906:2377 */             if ((0x7E & l) != 0L) {
/* 2907:2378 */               this.jjstateSet[(this.jjnewStateCnt++)] = 209;
/* 2908:     */             }
/* 2909:     */             break;
/* 2910:     */           case 210: 
/* 2911:2381 */             if ((0x7E & l) != 0L) {
/* 2912:2382 */               this.jjstateSet[(this.jjnewStateCnt++)] = 211;
/* 2913:     */             }
/* 2914:     */             break;
/* 2915:     */           case 211: 
/* 2916:2385 */             if ((0x7E & l) != 0L) {
/* 2917:2386 */               this.jjstateSet[(this.jjnewStateCnt++)] = 212;
/* 2918:     */             }
/* 2919:     */             break;
/* 2920:     */           case 212: 
/* 2921:2389 */             if ((0x7E & l) != 0L) {
/* 2922:2390 */               this.jjstateSet[(this.jjnewStateCnt++)] = 213;
/* 2923:     */             }
/* 2924:     */             break;
/* 2925:     */           case 214: 
/* 2926:     */           case 216: 
/* 2927:     */           case 219: 
/* 2928:     */           case 223: 
/* 2929:2396 */             if ((0x7E & l) != 0L) {
/* 2930:2397 */               jjCheckNAdd(200);
/* 2931:     */             }
/* 2932:     */             break;
/* 2933:     */           case 215: 
/* 2934:2400 */             if ((0x7E & l) != 0L) {
/* 2935:2401 */               this.jjstateSet[(this.jjnewStateCnt++)] = 216;
/* 2936:     */             }
/* 2937:     */             break;
/* 2938:     */           case 217: 
/* 2939:2404 */             if ((0x7E & l) != 0L) {
/* 2940:2405 */               this.jjstateSet[(this.jjnewStateCnt++)] = 218;
/* 2941:     */             }
/* 2942:     */             break;
/* 2943:     */           case 218: 
/* 2944:2408 */             if ((0x7E & l) != 0L) {
/* 2945:2409 */               this.jjstateSet[(this.jjnewStateCnt++)] = 219;
/* 2946:     */             }
/* 2947:     */             break;
/* 2948:     */           case 220: 
/* 2949:2412 */             if ((0x7E & l) != 0L) {
/* 2950:2413 */               this.jjstateSet[(this.jjnewStateCnt++)] = 221;
/* 2951:     */             }
/* 2952:     */             break;
/* 2953:     */           case 221: 
/* 2954:2416 */             if ((0x7E & l) != 0L) {
/* 2955:2417 */               this.jjstateSet[(this.jjnewStateCnt++)] = 222;
/* 2956:     */             }
/* 2957:     */             break;
/* 2958:     */           case 222: 
/* 2959:2420 */             if ((0x7E & l) != 0L) {
/* 2960:2421 */               this.jjstateSet[(this.jjnewStateCnt++)] = 223;
/* 2961:     */             }
/* 2962:     */             break;
/* 2963:     */           case 224: 
/* 2964:2424 */             if ((0x7E & l) != 0L)
/* 2965:     */             {
/* 2966:2426 */               if (kind > 47) {
/* 2967:2427 */                 kind = 47;
/* 2968:     */               }
/* 2969:2428 */               jjCheckNAddStates(219, 221);
/* 2970:     */             }
/* 2971:2429 */             break;
/* 2972:     */           case 225: 
/* 2973:2431 */             if ((0x7E & l) != 0L)
/* 2974:     */             {
/* 2975:2433 */               if (kind > 47) {
/* 2976:2434 */                 kind = 47;
/* 2977:     */               }
/* 2978:2435 */               jjCheckNAddStates(222, 224);
/* 2979:     */             }
/* 2980:2436 */             break;
/* 2981:     */           case 226: 
/* 2982:2438 */             if ((0x7E & l) != 0L)
/* 2983:     */             {
/* 2984:2440 */               if (kind > 47) {
/* 2985:2441 */                 kind = 47;
/* 2986:     */               }
/* 2987:2442 */               jjCheckNAddStates(225, 227);
/* 2988:     */             }
/* 2989:2443 */             break;
/* 2990:     */           case 229: 
/* 2991:2445 */             if ((0x7E & l) != 0L)
/* 2992:     */             {
/* 2993:2447 */               if (kind > 47) {
/* 2994:2448 */                 kind = 47;
/* 2995:     */               }
/* 2996:2449 */               jjCheckNAddTwoStates(198, 203);
/* 2997:     */             }
/* 2998:2450 */             break;
/* 2999:     */           case 256: 
/* 3000:2452 */             if (((0x2000 & l) != 0L) && (kind > 33)) {
/* 3001:2453 */               kind = 33;
/* 3002:     */             }
/* 3003:     */             break;
/* 3004:     */           case 257: 
/* 3005:2456 */             if ((0x20 & l) != 0L) {
/* 3006:2457 */               this.jjstateSet[(this.jjnewStateCnt++)] = 256;
/* 3007:     */             }
/* 3008:     */             break;
/* 3009:     */           case 259: 
/* 3010:2460 */             if (((0x1000000 & l) != 0L) && (kind > 34)) {
/* 3011:2461 */               kind = 34;
/* 3012:     */             }
/* 3013:     */             break;
/* 3014:     */           case 260: 
/* 3015:2464 */             if ((0x20 & l) != 0L) {
/* 3016:2465 */               this.jjstateSet[(this.jjnewStateCnt++)] = 259;
/* 3017:     */             }
/* 3018:     */             break;
/* 3019:     */           case 262: 
/* 3020:2468 */             if (((0x1000000 & l) != 0L) && (kind > 35)) {
/* 3021:2469 */               kind = 35;
/* 3022:     */             }
/* 3023:     */             break;
/* 3024:     */           case 263: 
/* 3025:2472 */             if ((0x10000 & l) != 0L) {
/* 3026:2473 */               this.jjstateSet[(this.jjnewStateCnt++)] = 262;
/* 3027:     */             }
/* 3028:     */             break;
/* 3029:     */           case 265: 
/* 3030:2476 */             if (((0x2000 & l) != 0L) && (kind > 36)) {
/* 3031:2477 */               kind = 36;
/* 3032:     */             }
/* 3033:     */             break;
/* 3034:     */           case 266: 
/* 3035:2480 */             if ((0x8 & l) != 0L) {
/* 3036:2481 */               this.jjstateSet[(this.jjnewStateCnt++)] = 265;
/* 3037:     */             }
/* 3038:     */             break;
/* 3039:     */           case 268: 
/* 3040:2484 */             if (((0x2000 & l) != 0L) && (kind > 37)) {
/* 3041:2485 */               kind = 37;
/* 3042:     */             }
/* 3043:     */             break;
/* 3044:     */           case 269: 
/* 3045:2488 */             if ((0x2000 & l) != 0L) {
/* 3046:2489 */               this.jjstateSet[(this.jjnewStateCnt++)] = 268;
/* 3047:     */             }
/* 3048:     */             break;
/* 3049:     */           case 271: 
/* 3050:2492 */             if (((0x4000 & l) != 0L) && (kind > 38)) {
/* 3051:2493 */               kind = 38;
/* 3052:     */             }
/* 3053:     */             break;
/* 3054:     */           case 272: 
/* 3055:2496 */             if ((0x200 & l) != 0L) {
/* 3056:2497 */               this.jjstateSet[(this.jjnewStateCnt++)] = 271;
/* 3057:     */             }
/* 3058:     */             break;
/* 3059:     */           case 274: 
/* 3060:2500 */             if (((0x100000 & l) != 0L) && (kind > 39)) {
/* 3061:2501 */               kind = 39;
/* 3062:     */             }
/* 3063:     */             break;
/* 3064:     */           case 275: 
/* 3065:2504 */             if ((0x10000 & l) != 0L) {
/* 3066:2505 */               this.jjstateSet[(this.jjnewStateCnt++)] = 274;
/* 3067:     */             }
/* 3068:     */             break;
/* 3069:     */           case 277: 
/* 3070:2508 */             if (((0x8 & l) != 0L) && (kind > 40)) {
/* 3071:2509 */               kind = 40;
/* 3072:     */             }
/* 3073:     */             break;
/* 3074:     */           case 278: 
/* 3075:2512 */             if ((0x10000 & l) != 0L) {
/* 3076:2513 */               this.jjstateSet[(this.jjnewStateCnt++)] = 277;
/* 3077:     */             }
/* 3078:     */             break;
/* 3079:     */           }
/* 3080:2517 */         } while (i != startsAt);
/* 3081:     */       }
/* 3082:     */       else
/* 3083:     */       {
/* 3084:2521 */         int i2 = (this.curChar & 0xFF) >> '\006';
/* 3085:2522 */         long l2 = 1L << (this.curChar & 0x3F);
/* 3086:     */         do
/* 3087:     */         {
/* 3088:2525 */           switch (this.jjstateSet[(--i)])
/* 3089:     */           {
/* 3090:     */           case 2: 
/* 3091:     */           case 4: 
/* 3092:     */           case 318: 
/* 3093:2530 */             if ((jjbitVec2[i2] & l2) != 0L)
/* 3094:     */             {
/* 3095:2532 */               if (kind > 3) {
/* 3096:2533 */                 kind = 3;
/* 3097:     */               }
/* 3098:2534 */               jjCheckNAddTwoStates(2, 3);
/* 3099:     */             }
/* 3100:2535 */             break;
/* 3101:     */           case 1: 
/* 3102:2537 */             if ((jjbitVec2[i2] & l2) != 0L)
/* 3103:     */             {
/* 3104:2539 */               if (kind > 3) {
/* 3105:2540 */                 kind = 3;
/* 3106:     */               }
/* 3107:2541 */               jjCheckNAddTwoStates(2, 3);
/* 3108:     */             }
/* 3109:2542 */             break;
/* 3110:     */           case 89: 
/* 3111:     */           case 92: 
/* 3112:2545 */             if ((jjbitVec2[i2] & l2) != 0L)
/* 3113:     */             {
/* 3114:2547 */               if (kind > 32) {
/* 3115:2548 */                 kind = 32;
/* 3116:     */               }
/* 3117:2549 */               jjCheckNAddTwoStates(90, 91);
/* 3118:     */             }
/* 3119:2550 */             break;
/* 3120:     */           case 90: 
/* 3121:     */           case 319: 
/* 3122:2553 */             if ((jjbitVec2[i2] & l2) != 0L)
/* 3123:     */             {
/* 3124:2555 */               if (kind > 32) {
/* 3125:2556 */                 kind = 32;
/* 3126:     */               }
/* 3127:2557 */               jjCheckNAddTwoStates(90, 91);
/* 3128:     */             }
/* 3129:2558 */             break;
/* 3130:     */           case 19: 
/* 3131:     */           case 21: 
/* 3132:2561 */             if ((jjbitVec2[i2] & l2) != 0L)
/* 3133:     */             {
/* 3134:2563 */               if (kind > 9) {
/* 3135:2564 */                 kind = 9;
/* 3136:     */               }
/* 3137:2565 */               jjCheckNAddTwoStates(19, 20);
/* 3138:     */             }
/* 3139:2566 */             break;
/* 3140:     */           case 36: 
/* 3141:     */           case 42: 
/* 3142:2569 */             if ((jjbitVec2[i2] & l2) != 0L) {
/* 3143:2570 */               jjCheckNAddStates(56, 58);
/* 3144:     */             }
/* 3145:     */             break;
/* 3146:     */           case 57: 
/* 3147:     */           case 63: 
/* 3148:2574 */             if ((jjbitVec2[i2] & l2) != 0L) {
/* 3149:2575 */               jjCheckNAddStates(53, 55);
/* 3150:     */             }
/* 3151:     */             break;
/* 3152:     */           case 134: 
/* 3153:     */           case 138: 
/* 3154:2579 */             if ((jjbitVec2[i2] & l2) != 0L) {
/* 3155:2580 */               jjCheckNAddStates(146, 149);
/* 3156:     */             }
/* 3157:     */             break;
/* 3158:     */           case 153: 
/* 3159:     */           case 159: 
/* 3160:2584 */             if ((jjbitVec2[i2] & l2) != 0L) {
/* 3161:2585 */               jjCheckNAddStates(163, 165);
/* 3162:     */             }
/* 3163:     */             break;
/* 3164:     */           case 174: 
/* 3165:     */           case 180: 
/* 3166:2589 */             if ((jjbitVec2[i2] & l2) != 0L) {
/* 3167:2590 */               jjCheckNAddStates(179, 181);
/* 3168:     */             }
/* 3169:     */             break;
/* 3170:     */           }
/* 3171:2594 */         } while (i != startsAt);
/* 3172:     */       }
/* 3173:2596 */       if (kind != 2147483647)
/* 3174:     */       {
/* 3175:2598 */         this.jjmatchedKind = kind;
/* 3176:2599 */         this.jjmatchedPos = curPos;
/* 3177:2600 */         kind = 2147483647;
/* 3178:     */       }
/* 3179:2602 */       curPos++;
/* 3180:2603 */       if ((i = this.jjnewStateCnt) == (startsAt = 317 - (this.jjnewStateCnt = startsAt))) {
/* 3181:2604 */         return curPos;
/* 3182:     */       }
/* 3183:     */       try
/* 3184:     */       {
/* 3185:2605 */         this.curChar = this.input_stream.readChar();
/* 3186:     */       }
/* 3187:     */       catch (IOException e) {}
/* 3188:     */     }
/* 3189:2606 */     return curPos;
/* 3190:     */   }
/* 3191:     */   
/* 3192:     */   private final int jjMoveStringLiteralDfa0_1()
/* 3193:     */   {
/* 3194:2611 */     switch (this.curChar)
/* 3195:     */     {
/* 3196:     */     case '*': 
/* 3197:2614 */       return jjMoveStringLiteralDfa1_1(2L);
/* 3198:     */     }
/* 3199:2616 */     return 1;
/* 3200:     */   }
/* 3201:     */   
/* 3202:     */   private final int jjMoveStringLiteralDfa1_1(long active1)
/* 3203:     */   {
/* 3204:     */     try
/* 3205:     */     {
/* 3206:2621 */       this.curChar = this.input_stream.readChar();
/* 3207:     */     }
/* 3208:     */     catch (IOException e)
/* 3209:     */     {
/* 3210:2623 */       return 1;
/* 3211:     */     }
/* 3212:2625 */     switch (this.curChar)
/* 3213:     */     {
/* 3214:     */     case '/': 
/* 3215:2628 */       if ((active1 & 0x2) != 0L) {
/* 3216:2629 */         return jjStopAtPos(1, 65);
/* 3217:     */       }
/* 3218:     */       break;
/* 3219:     */     default: 
/* 3220:2632 */       return 2;
/* 3221:     */     }
/* 3222:2634 */     return 2;
/* 3223:     */   }
/* 3224:     */   
/* 3225:2636 */   static final int[] jjnextStates = { 284, 285, 286, 257, 287, 288, 289, 260, 290, 291, 292, 263, 293, 294, 295, 266, 296, 297, 298, 269, 299, 300, 301, 272, 302, 303, 304, 275, 305, 306, 307, 278, 308, 309, 310, 280, 311, 312, 313, 314, 315, 316, 255, 258, 261, 264, 267, 270, 273, 276, 279, 281, 282, 57, 58, 59, 36, 37, 38, 2, 6, 8, 9, 11, 14, 7, 3, 2, 7, 3, 19, 23, 25, 26, 28, 31, 24, 20, 19, 24, 20, 36, 44, 46, 47, 49, 52, 45, 37, 38, 36, 45, 37, 38, 57, 65, 67, 68, 70, 73, 66, 58, 59, 57, 66, 58, 59, 90, 94, 96, 97, 99, 102, 95, 91, 90, 95, 91, 108, 109, 110, 112, 115, 95, 90, 91, 95, 90, 91, 121, 122, 123, 125, 128, 7, 2, 3, 7, 2, 3, 134, 152, 173, 136, 137, 194, 134, 135, 136, 137, 134, 140, 142, 143, 145, 148, 136, 137, 141, 134, 136, 137, 141, 153, 154, 155, 153, 161, 163, 164, 166, 169, 162, 154, 155, 153, 162, 154, 155, 174, 175, 176, 174, 182, 184, 185, 187, 190, 183, 175, 176, 174, 183, 175, 176, 134, 152, 173, 135, 136, 137, 194, 198, 199, 243, 200, 214, 215, 217, 220, 201, 198, 224, 236, 203, 204, 205, 207, 210, 198, 225, 232, 198, 226, 230, 198, 228, 229, 227, 233, 235, 227, 237, 239, 242, 247, 250, 252, 253, 227, 89, 106, 196, 197, 21, 22, 39, 41, 42, 43, 60, 62, 63, 64, 138, 139, 156, 158, 159, 160, 177, 179, 180, 181 };
/* 3226:2655 */   public static final String[] jjstrLiteralImages = { "", null, null, null, null, null, null, null, null, null, "{", "}", ",", ".", ";", ":", "*", "/", "+", "-", "=", ">", "[", "]", null, ")", null, "<!--", "-->", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null };
/* 3227:2662 */   public static final String[] lexStateNames = { "DEFAULT", "COMMENT" };
/* 3228:2666 */   public static final int[] jjnewLexState = { -1, -1, 1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, -1, -1 };
/* 3229:2671 */   static final long[] jjtoToken = { 228698418577403L, 8L };
/* 3230:2674 */   static final long[] jjtoSkip = { 0L, 2L };
/* 3231:2677 */   static final long[] jjtoMore = { 4L, 4L };
/* 3232:     */   protected CharStream input_stream;
/* 3233:2681 */   private final int[] jjrounds = new int[317];
/* 3234:2682 */   private final int[] jjstateSet = new int[634];
/* 3235:     */   StringBuffer image;
/* 3236:     */   int jjimageLen;
/* 3237:     */   int lengthOfMatch;
/* 3238:     */   protected char curChar;
/* 3239:     */   
/* 3240:     */   public SACParserCSSmobileOKBasic1TokenManager(CharStream stream)
/* 3241:     */   {
/* 3242:2688 */     this.input_stream = stream;
/* 3243:     */   }
/* 3244:     */   
/* 3245:     */   public SACParserCSSmobileOKBasic1TokenManager(CharStream stream, int lexState)
/* 3246:     */   {
/* 3247:2691 */     this(stream);
/* 3248:2692 */     SwitchTo(lexState);
/* 3249:     */   }
/* 3250:     */   
/* 3251:     */   public void ReInit(CharStream stream)
/* 3252:     */   {
/* 3253:2696 */     this.jjmatchedPos = (this.jjnewStateCnt = 0);
/* 3254:2697 */     this.curLexState = this.defaultLexState;
/* 3255:2698 */     this.input_stream = stream;
/* 3256:2699 */     ReInitRounds();
/* 3257:     */   }
/* 3258:     */   
/* 3259:     */   private final void ReInitRounds()
/* 3260:     */   {
/* 3261:2704 */     this.jjround = -2147483647;
/* 3262:2705 */     for (int i = 317; i-- > 0;) {
/* 3263:2706 */       this.jjrounds[i] = -2147483648;
/* 3264:     */     }
/* 3265:     */   }
/* 3266:     */   
/* 3267:     */   public void ReInit(CharStream stream, int lexState)
/* 3268:     */   {
/* 3269:2710 */     ReInit(stream);
/* 3270:2711 */     SwitchTo(lexState);
/* 3271:     */   }
/* 3272:     */   
/* 3273:     */   public void SwitchTo(int lexState)
/* 3274:     */   {
/* 3275:2715 */     if ((lexState >= 2) || (lexState < 0)) {
/* 3276:2716 */       throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", 2);
/* 3277:     */     }
/* 3278:2718 */     this.curLexState = lexState;
/* 3279:     */   }
/* 3280:     */   
/* 3281:     */   protected Token jjFillToken()
/* 3282:     */   {
/* 3283:2723 */     Token t = Token.newToken(this.jjmatchedKind);
/* 3284:2724 */     t.kind = this.jjmatchedKind;
/* 3285:2725 */     String im = jjstrLiteralImages[this.jjmatchedKind];
/* 3286:2726 */     t.image = (im == null ? this.input_stream.GetImage() : im);
/* 3287:2727 */     t.beginLine = this.input_stream.getBeginLine();
/* 3288:2728 */     t.beginColumn = this.input_stream.getBeginColumn();
/* 3289:2729 */     t.endLine = this.input_stream.getEndLine();
/* 3290:2730 */     t.endColumn = this.input_stream.getEndColumn();
/* 3291:2731 */     return t;
/* 3292:     */   }
/* 3293:     */   
/* 3294:2734 */   int curLexState = 0;
/* 3295:2735 */   int defaultLexState = 0;
/* 3296:     */   int jjnewStateCnt;
/* 3297:     */   int jjround;
/* 3298:     */   int jjmatchedPos;
/* 3299:     */   int jjmatchedKind;
/* 3300:     */   
/* 3301:     */   public Token getNextToken()
/* 3302:     */   {
/* 3303:2744 */     Token specialToken = null;
/* 3304:     */     
/* 3305:2746 */     int curPos = 0;
/* 3306:     */     try
/* 3307:     */     {
/* 3308:2753 */       this.curChar = this.input_stream.BeginToken();
/* 3309:     */     }
/* 3310:     */     catch (IOException e)
/* 3311:     */     {
/* 3312:2757 */       this.jjmatchedKind = 0;
/* 3313:2758 */       return jjFillToken();
/* 3314:     */     }
/* 3315:2761 */     this.image = null;
/* 3316:2762 */     this.jjimageLen = 0;
/* 3317:     */     for (;;)
/* 3318:     */     {
/* 3319:2766 */       switch (this.curLexState)
/* 3320:     */       {
/* 3321:     */       case 0: 
/* 3322:2769 */         this.jjmatchedKind = 2147483647;
/* 3323:2770 */         this.jjmatchedPos = 0;
/* 3324:2771 */         curPos = jjMoveStringLiteralDfa0_0();
/* 3325:2772 */         if ((this.jjmatchedPos == 0) && (this.jjmatchedKind > 67)) {
/* 3326:2774 */           this.jjmatchedKind = 67;
/* 3327:     */         }
/* 3328:     */         break;
/* 3329:     */       case 1: 
/* 3330:2778 */         this.jjmatchedKind = 2147483647;
/* 3331:2779 */         this.jjmatchedPos = 0;
/* 3332:2780 */         curPos = jjMoveStringLiteralDfa0_1();
/* 3333:2781 */         if ((this.jjmatchedPos == 0) && (this.jjmatchedKind > 66)) {
/* 3334:2783 */           this.jjmatchedKind = 66;
/* 3335:     */         }
/* 3336:     */         break;
/* 3337:     */       }
/* 3338:2787 */       if (this.jjmatchedKind != 2147483647)
/* 3339:     */       {
/* 3340:2789 */         if (this.jjmatchedPos + 1 < curPos) {
/* 3341:2790 */           this.input_stream.backup(curPos - this.jjmatchedPos - 1);
/* 3342:     */         }
/* 3343:2791 */         if ((jjtoToken[(this.jjmatchedKind >> 6)] & 1L << (this.jjmatchedKind & 0x3F)) != 0L)
/* 3344:     */         {
/* 3345:2793 */           Token matchedToken = jjFillToken();
/* 3346:2794 */           TokenLexicalActions(matchedToken);
/* 3347:2795 */           if (jjnewLexState[this.jjmatchedKind] != -1) {
/* 3348:2796 */             this.curLexState = jjnewLexState[this.jjmatchedKind];
/* 3349:     */           }
/* 3350:2797 */           return matchedToken;
/* 3351:     */         }
/* 3352:2799 */         if ((jjtoSkip[(this.jjmatchedKind >> 6)] & 1L << (this.jjmatchedKind & 0x3F)) != 0L)
/* 3353:     */         {
/* 3354:2801 */           if (jjnewLexState[this.jjmatchedKind] == -1) {
/* 3355:     */             break;
/* 3356:     */           }
/* 3357:2802 */           this.curLexState = jjnewLexState[this.jjmatchedKind]; break;
/* 3358:     */         }
/* 3359:2805 */         this.jjimageLen += this.jjmatchedPos + 1;
/* 3360:2806 */         if (jjnewLexState[this.jjmatchedKind] != -1) {
/* 3361:2807 */           this.curLexState = jjnewLexState[this.jjmatchedKind];
/* 3362:     */         }
/* 3363:2808 */         curPos = 0;
/* 3364:2809 */         this.jjmatchedKind = 2147483647;
/* 3365:     */         try
/* 3366:     */         {
/* 3367:2811 */           this.curChar = this.input_stream.readChar();
/* 3368:     */         }
/* 3369:     */         catch (IOException e1) {}
/* 3370:     */       }
/* 3371:     */     }
/* 3372:2816 */     int error_line = this.input_stream.getEndLine();
/* 3373:2817 */     int error_column = this.input_stream.getEndColumn();
/* 3374:2818 */     String error_after = null;
/* 3375:2819 */     boolean EOFSeen = false;
/* 3376:     */     try
/* 3377:     */     {
/* 3378:2820 */       this.input_stream.readChar();this.input_stream.backup(1);
/* 3379:     */     }
/* 3380:     */     catch (IOException e1)
/* 3381:     */     {
/* 3382:2822 */       EOFSeen = true;
/* 3383:2823 */       error_after = curPos <= 1 ? "" : this.input_stream.GetImage();
/* 3384:2824 */       if ((this.curChar == '\n') || (this.curChar == '\r'))
/* 3385:     */       {
/* 3386:2825 */         error_line++;
/* 3387:2826 */         error_column = 0;
/* 3388:     */       }
/* 3389:     */       else
/* 3390:     */       {
/* 3391:2829 */         error_column++;
/* 3392:     */       }
/* 3393:     */     }
/* 3394:2831 */     if (!EOFSeen)
/* 3395:     */     {
/* 3396:2832 */       this.input_stream.backup(1);
/* 3397:2833 */       error_after = curPos <= 1 ? "" : this.input_stream.GetImage();
/* 3398:     */     }
/* 3399:2835 */     throw new TokenMgrError(EOFSeen, this.curLexState, error_line, error_column, error_after, this.curChar, 0);
/* 3400:     */   }
/* 3401:     */   
/* 3402:     */   void TokenLexicalActions(Token matchedToken)
/* 3403:     */   {
/* 3404:2842 */     switch (this.jjmatchedKind)
/* 3405:     */     {
/* 3406:     */     case 4: 
/* 3407:2845 */       if (this.image == null) {
/* 3408:2846 */         this.image = new StringBuffer();
/* 3409:     */       }
/* 3410:2847 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 3411:2848 */       matchedToken.image = trimBy(this.image, 1, 0);
/* 3412:2849 */       break;
/* 3413:     */     case 5: 
/* 3414:2851 */       if (this.image == null) {
/* 3415:2852 */         this.image = new StringBuffer();
/* 3416:     */       }
/* 3417:2853 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 3418:2854 */       matchedToken.image = trimBy(this.image, 1, 0);
/* 3419:2855 */       break;
/* 3420:     */     case 6: 
/* 3421:2857 */       if (this.image == null) {
/* 3422:2858 */         this.image = new StringBuffer();
/* 3423:     */       }
/* 3424:2859 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 3425:2860 */       matchedToken.image = trimBy(this.image, 1, 0);
/* 3426:2861 */       break;
/* 3427:     */     case 7: 
/* 3428:2863 */       if (this.image == null) {
/* 3429:2864 */         this.image = new StringBuffer();
/* 3430:     */       }
/* 3431:2865 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 3432:2866 */       matchedToken.image = trimBy(this.image, 1, 0);
/* 3433:2867 */       break;
/* 3434:     */     case 8: 
/* 3435:2869 */       if (this.image == null) {
/* 3436:2870 */         this.image = new StringBuffer();
/* 3437:     */       }
/* 3438:2871 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 3439:2872 */       matchedToken.image = trimBy(this.image, 1, 0);
/* 3440:2873 */       break;
/* 3441:     */     case 24: 
/* 3442:2875 */       if (this.image == null) {
/* 3443:2876 */         this.image = new StringBuffer();
/* 3444:     */       }
/* 3445:2877 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 3446:2878 */       matchedToken.image = trimBy(this.image, 1, 1);
/* 3447:2879 */       break;
/* 3448:     */     case 26: 
/* 3449:2881 */       if (this.image == null) {
/* 3450:2882 */         this.image = new StringBuffer();
/* 3451:     */       }
/* 3452:2883 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 3453:2884 */       matchedToken.image = trimUrl(this.image);
/* 3454:2885 */       break;
/* 3455:     */     case 33: 
/* 3456:2887 */       if (this.image == null) {
/* 3457:2888 */         this.image = new StringBuffer();
/* 3458:     */       }
/* 3459:2889 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 3460:2890 */       matchedToken.image = trimBy(this.image, 0, 2);
/* 3461:2891 */       break;
/* 3462:     */     case 34: 
/* 3463:2893 */       if (this.image == null) {
/* 3464:2894 */         this.image = new StringBuffer();
/* 3465:     */       }
/* 3466:2895 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 3467:2896 */       matchedToken.image = trimBy(this.image, 0, 2);
/* 3468:2897 */       break;
/* 3469:     */     case 35: 
/* 3470:2899 */       if (this.image == null) {
/* 3471:2900 */         this.image = new StringBuffer();
/* 3472:     */       }
/* 3473:2901 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 3474:2902 */       matchedToken.image = trimBy(this.image, 0, 2);
/* 3475:2903 */       break;
/* 3476:     */     case 36: 
/* 3477:2905 */       if (this.image == null) {
/* 3478:2906 */         this.image = new StringBuffer();
/* 3479:     */       }
/* 3480:2907 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 3481:2908 */       matchedToken.image = trimBy(this.image, 0, 2);
/* 3482:2909 */       break;
/* 3483:     */     case 37: 
/* 3484:2911 */       if (this.image == null) {
/* 3485:2912 */         this.image = new StringBuffer();
/* 3486:     */       }
/* 3487:2913 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 3488:2914 */       matchedToken.image = trimBy(this.image, 0, 2);
/* 3489:2915 */       break;
/* 3490:     */     case 38: 
/* 3491:2917 */       if (this.image == null) {
/* 3492:2918 */         this.image = new StringBuffer();
/* 3493:     */       }
/* 3494:2919 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 3495:2920 */       matchedToken.image = trimBy(this.image, 0, 2);
/* 3496:2921 */       break;
/* 3497:     */     case 39: 
/* 3498:2923 */       if (this.image == null) {
/* 3499:2924 */         this.image = new StringBuffer();
/* 3500:     */       }
/* 3501:2925 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 3502:2926 */       matchedToken.image = trimBy(this.image, 0, 2);
/* 3503:2927 */       break;
/* 3504:     */     case 40: 
/* 3505:2929 */       if (this.image == null) {
/* 3506:2930 */         this.image = new StringBuffer();
/* 3507:     */       }
/* 3508:2931 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 3509:2932 */       matchedToken.image = trimBy(this.image, 0, 2);
/* 3510:2933 */       break;
/* 3511:     */     case 41: 
/* 3512:2935 */       if (this.image == null) {
/* 3513:2936 */         this.image = new StringBuffer();
/* 3514:     */       }
/* 3515:2937 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 3516:2938 */       matchedToken.image = trimBy(this.image, 0, 1);
/* 3517:2939 */       break;
/* 3518:     */     case 67: 
/* 3519:2941 */       if (this.image == null) {
/* 3520:2942 */         this.image = new StringBuffer();
/* 3521:     */       }
/* 3522:2943 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 3523:2944 */       if (!this._quiet) {
/* 3524:2945 */         System.err.println("Illegal character : " + this.image.toString());
/* 3525:     */       }
/* 3526:     */       break;
/* 3527:     */     }
/* 3528:     */   }
/* 3529:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.steadystate.css.parser.SACParserCSSmobileOKBasic1TokenManager
 * JD-Core Version:    0.7.0.1
 */