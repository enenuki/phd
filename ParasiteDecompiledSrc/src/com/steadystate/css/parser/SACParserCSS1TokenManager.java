/*    1:     */ package com.steadystate.css.parser;
/*    2:     */ 
/*    3:     */ import java.io.IOException;
/*    4:     */ import java.io.PrintStream;
/*    5:     */ import org.w3c.css.sac.ErrorHandler;
/*    6:     */ 
/*    7:     */ public class SACParserCSS1TokenManager
/*    8:     */   implements SACParserCSS1Constants
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
/*   52:  48 */       if ((active0 & 0x20000000) != 0L) {
/*   53:  49 */         return 89;
/*   54:     */       }
/*   55:  50 */       return -1;
/*   56:     */     case 1: 
/*   57:  52 */       if ((active0 & 0x20000000) != 0L)
/*   58:     */       {
/*   59:  54 */         this.jjmatchedKind = 31;
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
/*   77:  72 */       if ((active0 & 0x20000000) != 0L)
/*   78:     */       {
/*   79:  74 */         this.jjmatchedKind = 31;
/*   80:  75 */         this.jjmatchedPos = 2;
/*   81:  76 */         return 319;
/*   82:     */       }
/*   83:  78 */       return -1;
/*   84:     */     case 3: 
/*   85:  80 */       if ((active0 & 0x20000000) != 0L)
/*   86:     */       {
/*   87:  82 */         this.jjmatchedKind = 31;
/*   88:  83 */         this.jjmatchedPos = 3;
/*   89:  84 */         return 319;
/*   90:     */       }
/*   91:  86 */       return -1;
/*   92:     */     case 4: 
/*   93:  88 */       if ((active0 & 0x20000000) != 0L)
/*   94:     */       {
/*   95:  90 */         this.jjmatchedKind = 31;
/*   96:  91 */         this.jjmatchedPos = 4;
/*   97:  92 */         return 319;
/*   98:     */       }
/*   99:  94 */       return -1;
/*  100:     */     case 5: 
/*  101:  96 */       if ((active0 & 0x20000000) != 0L)
/*  102:     */       {
/*  103:  98 */         this.jjmatchedKind = 31;
/*  104:  99 */         this.jjmatchedPos = 5;
/*  105: 100 */         return 319;
/*  106:     */       }
/*  107: 102 */       return -1;
/*  108:     */     case 6: 
/*  109: 104 */       if ((active0 & 0x20000000) != 0L) {
/*  110: 105 */         return 319;
/*  111:     */       }
/*  112: 106 */       return -1;
/*  113:     */     }
/*  114: 108 */     return -1;
/*  115:     */   }
/*  116:     */   
/*  117:     */   private final int jjStartNfa_0(int pos, long active0)
/*  118:     */   {
/*  119: 113 */     return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0), pos + 1);
/*  120:     */   }
/*  121:     */   
/*  122:     */   private final int jjStopAtPos(int pos, int kind)
/*  123:     */   {
/*  124: 117 */     this.jjmatchedKind = kind;
/*  125: 118 */     this.jjmatchedPos = pos;
/*  126: 119 */     return pos + 1;
/*  127:     */   }
/*  128:     */   
/*  129:     */   private final int jjStartNfaWithStates_0(int pos, int kind, int state)
/*  130:     */   {
/*  131: 123 */     this.jjmatchedKind = kind;
/*  132: 124 */     this.jjmatchedPos = pos;
/*  133:     */     try
/*  134:     */     {
/*  135: 125 */       this.curChar = this.input_stream.readChar();
/*  136:     */     }
/*  137:     */     catch (IOException e)
/*  138:     */     {
/*  139: 126 */       return pos + 1;
/*  140:     */     }
/*  141: 127 */     return jjMoveNfa_0(state, pos + 1);
/*  142:     */   }
/*  143:     */   
/*  144:     */   private final int jjMoveStringLiteralDfa0_0()
/*  145:     */   {
/*  146: 131 */     switch (this.curChar)
/*  147:     */     {
/*  148:     */     case ')': 
/*  149: 134 */       return jjStopAtPos(0, 25);
/*  150:     */     case '*': 
/*  151: 136 */       return jjStopAtPos(0, 16);
/*  152:     */     case '+': 
/*  153: 138 */       return jjStopAtPos(0, 18);
/*  154:     */     case ',': 
/*  155: 140 */       return jjStopAtPos(0, 12);
/*  156:     */     case '-': 
/*  157: 142 */       this.jjmatchedKind = 19;
/*  158: 143 */       return jjMoveStringLiteralDfa1_0(268435456L);
/*  159:     */     case '.': 
/*  160: 145 */       return jjStartNfaWithStates_0(0, 13, 317);
/*  161:     */     case '/': 
/*  162: 147 */       this.jjmatchedKind = 17;
/*  163: 148 */       return jjMoveStringLiteralDfa1_0(4L);
/*  164:     */     case ':': 
/*  165: 150 */       this.jjmatchedKind = 15;
/*  166: 151 */       return jjMoveStringLiteralDfa1_0(496L);
/*  167:     */     case ';': 
/*  168: 153 */       return jjStopAtPos(0, 14);
/*  169:     */     case '<': 
/*  170: 155 */       return jjMoveStringLiteralDfa1_0(134217728L);
/*  171:     */     case '=': 
/*  172: 157 */       return jjStopAtPos(0, 20);
/*  173:     */     case '>': 
/*  174: 159 */       return jjStopAtPos(0, 21);
/*  175:     */     case '@': 
/*  176: 161 */       return jjMoveStringLiteralDfa1_0(536870912L);
/*  177:     */     case '[': 
/*  178: 163 */       return jjStopAtPos(0, 22);
/*  179:     */     case ']': 
/*  180: 165 */       return jjStopAtPos(0, 23);
/*  181:     */     case 'R': 
/*  182:     */     case 'r': 
/*  183: 168 */       return jjMoveStringLiteralDfa1_0(4398046511104L);
/*  184:     */     case '{': 
/*  185: 170 */       return jjStopAtPos(0, 10);
/*  186:     */     case '}': 
/*  187: 172 */       return jjStopAtPos(0, 11);
/*  188:     */     }
/*  189: 174 */     return jjMoveNfa_0(1, 0);
/*  190:     */   }
/*  191:     */   
/*  192:     */   private final int jjMoveStringLiteralDfa1_0(long active0)
/*  193:     */   {
/*  194:     */     try
/*  195:     */     {
/*  196: 179 */       this.curChar = this.input_stream.readChar();
/*  197:     */     }
/*  198:     */     catch (IOException e)
/*  199:     */     {
/*  200: 181 */       jjStopStringLiteralDfa_0(0, active0);
/*  201: 182 */       return 1;
/*  202:     */     }
/*  203: 184 */     switch (this.curChar)
/*  204:     */     {
/*  205:     */     case '!': 
/*  206: 187 */       return jjMoveStringLiteralDfa2_0(active0, 134217728L);
/*  207:     */     case '*': 
/*  208: 189 */       if ((active0 & 0x4) != 0L) {
/*  209: 190 */         return jjStopAtPos(1, 2);
/*  210:     */       }
/*  211:     */       break;
/*  212:     */     case '-': 
/*  213: 193 */       return jjMoveStringLiteralDfa2_0(active0, 268435456L);
/*  214:     */     case 'A': 
/*  215:     */     case 'a': 
/*  216: 196 */       return jjMoveStringLiteralDfa2_0(active0, 64L);
/*  217:     */     case 'F': 
/*  218:     */     case 'f': 
/*  219: 199 */       return jjMoveStringLiteralDfa2_0(active0, 384L);
/*  220:     */     case 'G': 
/*  221:     */     case 'g': 
/*  222: 202 */       return jjMoveStringLiteralDfa2_0(active0, 4398046511104L);
/*  223:     */     case 'I': 
/*  224:     */     case 'i': 
/*  225: 205 */       return jjMoveStringLiteralDfa2_0(active0, 536870912L);
/*  226:     */     case 'L': 
/*  227:     */     case 'l': 
/*  228: 208 */       return jjMoveStringLiteralDfa2_0(active0, 16L);
/*  229:     */     case 'V': 
/*  230:     */     case 'v': 
/*  231: 211 */       return jjMoveStringLiteralDfa2_0(active0, 32L);
/*  232:     */     }
/*  233: 215 */     return jjStartNfa_0(0, active0);
/*  234:     */   }
/*  235:     */   
/*  236:     */   private final int jjMoveStringLiteralDfa2_0(long old0, long active0)
/*  237:     */   {
/*  238: 219 */     if ((active0 &= old0) == 0L) {
/*  239: 220 */       return jjStartNfa_0(0, old0);
/*  240:     */     }
/*  241:     */     try
/*  242:     */     {
/*  243: 221 */       this.curChar = this.input_stream.readChar();
/*  244:     */     }
/*  245:     */     catch (IOException e)
/*  246:     */     {
/*  247: 223 */       jjStopStringLiteralDfa_0(1, active0);
/*  248: 224 */       return 2;
/*  249:     */     }
/*  250: 226 */     switch (this.curChar)
/*  251:     */     {
/*  252:     */     case '-': 
/*  253: 229 */       return jjMoveStringLiteralDfa3_0(active0, 134217728L);
/*  254:     */     case '>': 
/*  255: 231 */       if ((active0 & 0x10000000) != 0L) {
/*  256: 232 */         return jjStopAtPos(2, 28);
/*  257:     */       }
/*  258:     */       break;
/*  259:     */     case 'B': 
/*  260:     */     case 'b': 
/*  261: 236 */       return jjMoveStringLiteralDfa3_0(active0, 4398046511104L);
/*  262:     */     case 'C': 
/*  263:     */     case 'c': 
/*  264: 239 */       return jjMoveStringLiteralDfa3_0(active0, 64L);
/*  265:     */     case 'I': 
/*  266:     */     case 'i': 
/*  267: 242 */       return jjMoveStringLiteralDfa3_0(active0, 432L);
/*  268:     */     case 'M': 
/*  269:     */     case 'm': 
/*  270: 245 */       return jjMoveStringLiteralDfa3_0(active0, 536870912L);
/*  271:     */     }
/*  272: 249 */     return jjStartNfa_0(1, active0);
/*  273:     */   }
/*  274:     */   
/*  275:     */   private final int jjMoveStringLiteralDfa3_0(long old0, long active0)
/*  276:     */   {
/*  277: 253 */     if ((active0 &= old0) == 0L) {
/*  278: 254 */       return jjStartNfa_0(1, old0);
/*  279:     */     }
/*  280:     */     try
/*  281:     */     {
/*  282: 255 */       this.curChar = this.input_stream.readChar();
/*  283:     */     }
/*  284:     */     catch (IOException e)
/*  285:     */     {
/*  286: 257 */       jjStopStringLiteralDfa_0(2, active0);
/*  287: 258 */       return 3;
/*  288:     */     }
/*  289: 260 */     switch (this.curChar)
/*  290:     */     {
/*  291:     */     case '(': 
/*  292: 263 */       if ((active0 & 0x0) != 0L) {
/*  293: 264 */         return jjStopAtPos(3, 42);
/*  294:     */       }
/*  295:     */       break;
/*  296:     */     case '-': 
/*  297: 267 */       if ((active0 & 0x8000000) != 0L) {
/*  298: 268 */         return jjStopAtPos(3, 27);
/*  299:     */       }
/*  300:     */       break;
/*  301:     */     case 'N': 
/*  302:     */     case 'n': 
/*  303: 272 */       return jjMoveStringLiteralDfa4_0(active0, 16L);
/*  304:     */     case 'P': 
/*  305:     */     case 'p': 
/*  306: 275 */       return jjMoveStringLiteralDfa4_0(active0, 536870912L);
/*  307:     */     case 'R': 
/*  308:     */     case 'r': 
/*  309: 278 */       return jjMoveStringLiteralDfa4_0(active0, 384L);
/*  310:     */     case 'S': 
/*  311:     */     case 's': 
/*  312: 281 */       return jjMoveStringLiteralDfa4_0(active0, 32L);
/*  313:     */     case 'T': 
/*  314:     */     case 't': 
/*  315: 284 */       return jjMoveStringLiteralDfa4_0(active0, 64L);
/*  316:     */     }
/*  317: 288 */     return jjStartNfa_0(2, active0);
/*  318:     */   }
/*  319:     */   
/*  320:     */   private final int jjMoveStringLiteralDfa4_0(long old0, long active0)
/*  321:     */   {
/*  322: 292 */     if ((active0 &= old0) == 0L) {
/*  323: 293 */       return jjStartNfa_0(2, old0);
/*  324:     */     }
/*  325:     */     try
/*  326:     */     {
/*  327: 294 */       this.curChar = this.input_stream.readChar();
/*  328:     */     }
/*  329:     */     catch (IOException e)
/*  330:     */     {
/*  331: 296 */       jjStopStringLiteralDfa_0(3, active0);
/*  332: 297 */       return 4;
/*  333:     */     }
/*  334: 299 */     switch (this.curChar)
/*  335:     */     {
/*  336:     */     case 'I': 
/*  337:     */     case 'i': 
/*  338: 303 */       return jjMoveStringLiteralDfa5_0(active0, 96L);
/*  339:     */     case 'K': 
/*  340:     */     case 'k': 
/*  341: 306 */       if ((active0 & 0x10) != 0L) {
/*  342: 307 */         return jjStopAtPos(4, 4);
/*  343:     */       }
/*  344:     */       break;
/*  345:     */     case 'O': 
/*  346:     */     case 'o': 
/*  347: 311 */       return jjMoveStringLiteralDfa5_0(active0, 536870912L);
/*  348:     */     case 'S': 
/*  349:     */     case 's': 
/*  350: 314 */       return jjMoveStringLiteralDfa5_0(active0, 384L);
/*  351:     */     }
/*  352: 318 */     return jjStartNfa_0(3, active0);
/*  353:     */   }
/*  354:     */   
/*  355:     */   private final int jjMoveStringLiteralDfa5_0(long old0, long active0)
/*  356:     */   {
/*  357: 322 */     if ((active0 &= old0) == 0L) {
/*  358: 323 */       return jjStartNfa_0(3, old0);
/*  359:     */     }
/*  360:     */     try
/*  361:     */     {
/*  362: 324 */       this.curChar = this.input_stream.readChar();
/*  363:     */     }
/*  364:     */     catch (IOException e)
/*  365:     */     {
/*  366: 326 */       jjStopStringLiteralDfa_0(4, active0);
/*  367: 327 */       return 5;
/*  368:     */     }
/*  369: 329 */     switch (this.curChar)
/*  370:     */     {
/*  371:     */     case 'R': 
/*  372:     */     case 'r': 
/*  373: 333 */       return jjMoveStringLiteralDfa6_0(active0, 536870912L);
/*  374:     */     case 'T': 
/*  375:     */     case 't': 
/*  376: 336 */       return jjMoveStringLiteralDfa6_0(active0, 416L);
/*  377:     */     case 'V': 
/*  378:     */     case 'v': 
/*  379: 339 */       return jjMoveStringLiteralDfa6_0(active0, 64L);
/*  380:     */     }
/*  381: 343 */     return jjStartNfa_0(4, active0);
/*  382:     */   }
/*  383:     */   
/*  384:     */   private final int jjMoveStringLiteralDfa6_0(long old0, long active0)
/*  385:     */   {
/*  386: 347 */     if ((active0 &= old0) == 0L) {
/*  387: 348 */       return jjStartNfa_0(4, old0);
/*  388:     */     }
/*  389:     */     try
/*  390:     */     {
/*  391: 349 */       this.curChar = this.input_stream.readChar();
/*  392:     */     }
/*  393:     */     catch (IOException e)
/*  394:     */     {
/*  395: 351 */       jjStopStringLiteralDfa_0(5, active0);
/*  396: 352 */       return 6;
/*  397:     */     }
/*  398: 354 */     switch (this.curChar)
/*  399:     */     {
/*  400:     */     case '-': 
/*  401: 357 */       return jjMoveStringLiteralDfa7_0(active0, 384L);
/*  402:     */     case 'E': 
/*  403:     */     case 'e': 
/*  404: 360 */       if ((active0 & 0x40) != 0L) {
/*  405: 361 */         return jjStopAtPos(6, 6);
/*  406:     */       }
/*  407: 362 */       return jjMoveStringLiteralDfa7_0(active0, 32L);
/*  408:     */     case 'T': 
/*  409:     */     case 't': 
/*  410: 365 */       if ((active0 & 0x20000000) != 0L) {
/*  411: 366 */         return jjStartNfaWithStates_0(6, 29, 319);
/*  412:     */       }
/*  413:     */       break;
/*  414:     */     }
/*  415: 371 */     return jjStartNfa_0(5, active0);
/*  416:     */   }
/*  417:     */   
/*  418:     */   private final int jjMoveStringLiteralDfa7_0(long old0, long active0)
/*  419:     */   {
/*  420: 375 */     if ((active0 &= old0) == 0L) {
/*  421: 376 */       return jjStartNfa_0(5, old0);
/*  422:     */     }
/*  423:     */     try
/*  424:     */     {
/*  425: 377 */       this.curChar = this.input_stream.readChar();
/*  426:     */     }
/*  427:     */     catch (IOException e)
/*  428:     */     {
/*  429: 379 */       jjStopStringLiteralDfa_0(6, active0);
/*  430: 380 */       return 7;
/*  431:     */     }
/*  432: 382 */     switch (this.curChar)
/*  433:     */     {
/*  434:     */     case 'D': 
/*  435:     */     case 'd': 
/*  436: 386 */       if ((active0 & 0x20) != 0L) {
/*  437: 387 */         return jjStopAtPos(7, 5);
/*  438:     */       }
/*  439:     */       break;
/*  440:     */     case 'L': 
/*  441:     */     case 'l': 
/*  442: 391 */       return jjMoveStringLiteralDfa8_0(active0, 384L);
/*  443:     */     }
/*  444: 395 */     return jjStartNfa_0(6, active0);
/*  445:     */   }
/*  446:     */   
/*  447:     */   private final int jjMoveStringLiteralDfa8_0(long old0, long active0)
/*  448:     */   {
/*  449: 399 */     if ((active0 &= old0) == 0L) {
/*  450: 400 */       return jjStartNfa_0(6, old0);
/*  451:     */     }
/*  452:     */     try
/*  453:     */     {
/*  454: 401 */       this.curChar = this.input_stream.readChar();
/*  455:     */     }
/*  456:     */     catch (IOException e)
/*  457:     */     {
/*  458: 403 */       jjStopStringLiteralDfa_0(7, active0);
/*  459: 404 */       return 8;
/*  460:     */     }
/*  461: 406 */     switch (this.curChar)
/*  462:     */     {
/*  463:     */     case 'E': 
/*  464:     */     case 'e': 
/*  465: 410 */       return jjMoveStringLiteralDfa9_0(active0, 256L);
/*  466:     */     case 'I': 
/*  467:     */     case 'i': 
/*  468: 413 */       return jjMoveStringLiteralDfa9_0(active0, 128L);
/*  469:     */     }
/*  470: 417 */     return jjStartNfa_0(7, active0);
/*  471:     */   }
/*  472:     */   
/*  473:     */   private final int jjMoveStringLiteralDfa9_0(long old0, long active0)
/*  474:     */   {
/*  475: 421 */     if ((active0 &= old0) == 0L) {
/*  476: 422 */       return jjStartNfa_0(7, old0);
/*  477:     */     }
/*  478:     */     try
/*  479:     */     {
/*  480: 423 */       this.curChar = this.input_stream.readChar();
/*  481:     */     }
/*  482:     */     catch (IOException e)
/*  483:     */     {
/*  484: 425 */       jjStopStringLiteralDfa_0(8, active0);
/*  485: 426 */       return 9;
/*  486:     */     }
/*  487: 428 */     switch (this.curChar)
/*  488:     */     {
/*  489:     */     case 'N': 
/*  490:     */     case 'n': 
/*  491: 432 */       return jjMoveStringLiteralDfa10_0(active0, 128L);
/*  492:     */     case 'T': 
/*  493:     */     case 't': 
/*  494: 435 */       return jjMoveStringLiteralDfa10_0(active0, 256L);
/*  495:     */     }
/*  496: 439 */     return jjStartNfa_0(8, active0);
/*  497:     */   }
/*  498:     */   
/*  499:     */   private final int jjMoveStringLiteralDfa10_0(long old0, long active0)
/*  500:     */   {
/*  501: 443 */     if ((active0 &= old0) == 0L) {
/*  502: 444 */       return jjStartNfa_0(8, old0);
/*  503:     */     }
/*  504:     */     try
/*  505:     */     {
/*  506: 445 */       this.curChar = this.input_stream.readChar();
/*  507:     */     }
/*  508:     */     catch (IOException e)
/*  509:     */     {
/*  510: 447 */       jjStopStringLiteralDfa_0(9, active0);
/*  511: 448 */       return 10;
/*  512:     */     }
/*  513: 450 */     switch (this.curChar)
/*  514:     */     {
/*  515:     */     case 'E': 
/*  516:     */     case 'e': 
/*  517: 454 */       if ((active0 & 0x80) != 0L) {
/*  518: 455 */         return jjStopAtPos(10, 7);
/*  519:     */       }
/*  520:     */       break;
/*  521:     */     case 'T': 
/*  522:     */     case 't': 
/*  523: 459 */       return jjMoveStringLiteralDfa11_0(active0, 256L);
/*  524:     */     }
/*  525: 463 */     return jjStartNfa_0(9, active0);
/*  526:     */   }
/*  527:     */   
/*  528:     */   private final int jjMoveStringLiteralDfa11_0(long old0, long active0)
/*  529:     */   {
/*  530: 467 */     if ((active0 &= old0) == 0L) {
/*  531: 468 */       return jjStartNfa_0(9, old0);
/*  532:     */     }
/*  533:     */     try
/*  534:     */     {
/*  535: 469 */       this.curChar = this.input_stream.readChar();
/*  536:     */     }
/*  537:     */     catch (IOException e)
/*  538:     */     {
/*  539: 471 */       jjStopStringLiteralDfa_0(10, active0);
/*  540: 472 */       return 11;
/*  541:     */     }
/*  542: 474 */     switch (this.curChar)
/*  543:     */     {
/*  544:     */     case 'E': 
/*  545:     */     case 'e': 
/*  546: 478 */       return jjMoveStringLiteralDfa12_0(active0, 256L);
/*  547:     */     }
/*  548: 482 */     return jjStartNfa_0(10, active0);
/*  549:     */   }
/*  550:     */   
/*  551:     */   private final int jjMoveStringLiteralDfa12_0(long old0, long active0)
/*  552:     */   {
/*  553: 486 */     if ((active0 &= old0) == 0L) {
/*  554: 487 */       return jjStartNfa_0(10, old0);
/*  555:     */     }
/*  556:     */     try
/*  557:     */     {
/*  558: 488 */       this.curChar = this.input_stream.readChar();
/*  559:     */     }
/*  560:     */     catch (IOException e)
/*  561:     */     {
/*  562: 490 */       jjStopStringLiteralDfa_0(11, active0);
/*  563: 491 */       return 12;
/*  564:     */     }
/*  565: 493 */     switch (this.curChar)
/*  566:     */     {
/*  567:     */     case 'R': 
/*  568:     */     case 'r': 
/*  569: 497 */       if ((active0 & 0x100) != 0L) {
/*  570: 498 */         return jjStopAtPos(12, 8);
/*  571:     */       }
/*  572:     */       break;
/*  573:     */     }
/*  574: 503 */     return jjStartNfa_0(11, active0);
/*  575:     */   }
/*  576:     */   
/*  577:     */   private final void jjCheckNAdd(int state)
/*  578:     */   {
/*  579: 507 */     if (this.jjrounds[state] != this.jjround)
/*  580:     */     {
/*  581: 509 */       this.jjstateSet[(this.jjnewStateCnt++)] = state;
/*  582: 510 */       this.jjrounds[state] = this.jjround;
/*  583:     */     }
/*  584:     */   }
/*  585:     */   
/*  586:     */   private final void jjAddStates(int start, int end)
/*  587:     */   {
/*  588:     */     do
/*  589:     */     {
/*  590: 516 */       this.jjstateSet[(this.jjnewStateCnt++)] = jjnextStates[start];
/*  591: 517 */     } while (start++ != end);
/*  592:     */   }
/*  593:     */   
/*  594:     */   private final void jjCheckNAddTwoStates(int state1, int state2)
/*  595:     */   {
/*  596: 521 */     jjCheckNAdd(state1);
/*  597: 522 */     jjCheckNAdd(state2);
/*  598:     */   }
/*  599:     */   
/*  600:     */   private final void jjCheckNAddStates(int start, int end)
/*  601:     */   {
/*  602:     */     do
/*  603:     */     {
/*  604: 527 */       jjCheckNAdd(jjnextStates[start]);
/*  605: 528 */     } while (start++ != end);
/*  606:     */   }
/*  607:     */   
/*  608:     */   private final void jjCheckNAddStates(int start)
/*  609:     */   {
/*  610: 532 */     jjCheckNAdd(jjnextStates[start]);
/*  611: 533 */     jjCheckNAdd(jjnextStates[(start + 1)]);
/*  612:     */   }
/*  613:     */   
/*  614: 535 */   static final long[] jjbitVec0 = { -2L, -1L, -1L, -1L };
/*  615: 538 */   static final long[] jjbitVec2 = { 0L, 0L, -1L, -1L };
/*  616:     */   
/*  617:     */   private final int jjMoveNfa_0(int startState, int curPos)
/*  618:     */   {
/*  619: 544 */     int startsAt = 0;
/*  620: 545 */     this.jjnewStateCnt = 317;
/*  621: 546 */     int i = 1;
/*  622: 547 */     this.jjstateSet[0] = startState;
/*  623: 548 */     int kind = 2147483647;
/*  624:     */     for (;;)
/*  625:     */     {
/*  626: 551 */       if (++this.jjround == 2147483647) {
/*  627: 552 */         ReInitRounds();
/*  628:     */       }
/*  629: 553 */       if (this.curChar < '@')
/*  630:     */       {
/*  631: 555 */         long l = 1L << this.curChar;
/*  632:     */         do
/*  633:     */         {
/*  634: 558 */           switch (this.jjstateSet[(--i)])
/*  635:     */           {
/*  636:     */           case 317: 
/*  637: 561 */             if ((0x0 & l) != 0L)
/*  638:     */             {
/*  639: 563 */               if (kind > 45) {
/*  640: 564 */                 kind = 45;
/*  641:     */               }
/*  642: 565 */               jjCheckNAdd(282);
/*  643:     */             }
/*  644: 567 */             if ((0x0 & l) != 0L)
/*  645:     */             {
/*  646: 569 */               if (kind > 41) {
/*  647: 570 */                 kind = 41;
/*  648:     */               }
/*  649: 571 */               jjCheckNAdd(281);
/*  650:     */             }
/*  651: 573 */             if ((0x0 & l) != 0L) {
/*  652: 574 */               jjCheckNAddTwoStates(279, 280);
/*  653:     */             }
/*  654: 575 */             if ((0x0 & l) != 0L) {
/*  655: 576 */               jjCheckNAddTwoStates(276, 278);
/*  656:     */             }
/*  657: 577 */             if ((0x0 & l) != 0L) {
/*  658: 578 */               jjCheckNAddTwoStates(273, 275);
/*  659:     */             }
/*  660: 579 */             if ((0x0 & l) != 0L) {
/*  661: 580 */               jjCheckNAddTwoStates(270, 272);
/*  662:     */             }
/*  663: 581 */             if ((0x0 & l) != 0L) {
/*  664: 582 */               jjCheckNAddTwoStates(267, 269);
/*  665:     */             }
/*  666: 583 */             if ((0x0 & l) != 0L) {
/*  667: 584 */               jjCheckNAddTwoStates(264, 266);
/*  668:     */             }
/*  669: 585 */             if ((0x0 & l) != 0L) {
/*  670: 586 */               jjCheckNAddTwoStates(261, 263);
/*  671:     */             }
/*  672: 587 */             if ((0x0 & l) != 0L) {
/*  673: 588 */               jjCheckNAddTwoStates(258, 260);
/*  674:     */             }
/*  675: 589 */             if ((0x0 & l) != 0L) {
/*  676: 590 */               jjCheckNAddTwoStates(255, 257);
/*  677:     */             }
/*  678:     */             break;
/*  679:     */           case 2: 
/*  680:     */           case 318: 
/*  681: 594 */             if ((0x0 & l) != 0L)
/*  682:     */             {
/*  683: 596 */               if (kind > 3) {
/*  684: 597 */                 kind = 3;
/*  685:     */               }
/*  686: 598 */               jjCheckNAddTwoStates(2, 3);
/*  687:     */             }
/*  688: 599 */             break;
/*  689:     */           case 1: 
/*  690: 601 */             if ((0x0 & l) != 0L)
/*  691:     */             {
/*  692: 603 */               if (kind > 41) {
/*  693: 604 */                 kind = 41;
/*  694:     */               }
/*  695: 605 */               jjCheckNAddStates(0, 41);
/*  696:     */             }
/*  697: 607 */             else if ((0x3600 & l) != 0L)
/*  698:     */             {
/*  699: 609 */               if (kind > 1) {
/*  700: 610 */                 kind = 1;
/*  701:     */               }
/*  702: 611 */               jjCheckNAdd(0);
/*  703:     */             }
/*  704: 613 */             else if (this.curChar == '.')
/*  705:     */             {
/*  706: 614 */               jjCheckNAddStates(42, 52);
/*  707:     */             }
/*  708: 615 */             else if (this.curChar == '!')
/*  709:     */             {
/*  710: 616 */               jjCheckNAddTwoStates(78, 87);
/*  711:     */             }
/*  712: 617 */             else if (this.curChar == '\'')
/*  713:     */             {
/*  714: 618 */               jjCheckNAddStates(53, 55);
/*  715:     */             }
/*  716: 619 */             else if (this.curChar == '"')
/*  717:     */             {
/*  718: 620 */               jjCheckNAddStates(56, 58);
/*  719:     */             }
/*  720: 621 */             else if (this.curChar == '#')
/*  721:     */             {
/*  722: 622 */               jjCheckNAddTwoStates(19, 20);
/*  723:     */             }
/*  724:     */             break;
/*  725:     */           case 90: 
/*  726:     */           case 319: 
/*  727: 626 */             if ((0x0 & l) != 0L)
/*  728:     */             {
/*  729: 628 */               if (kind > 31) {
/*  730: 629 */                 kind = 31;
/*  731:     */               }
/*  732: 630 */               jjCheckNAddTwoStates(90, 91);
/*  733:     */             }
/*  734: 631 */             break;
/*  735:     */           case 0: 
/*  736: 633 */             if ((0x3600 & l) != 0L)
/*  737:     */             {
/*  738: 635 */               if (kind > 1) {
/*  739: 636 */                 kind = 1;
/*  740:     */               }
/*  741: 637 */               jjCheckNAdd(0);
/*  742:     */             }
/*  743: 638 */             break;
/*  744:     */           case 4: 
/*  745: 640 */             if ((0x0 & l) != 0L)
/*  746:     */             {
/*  747: 642 */               if (kind > 3) {
/*  748: 643 */                 kind = 3;
/*  749:     */               }
/*  750: 644 */               jjCheckNAddTwoStates(2, 3);
/*  751:     */             }
/*  752: 645 */             break;
/*  753:     */           case 5: 
/*  754: 647 */             if ((0x0 & l) != 0L)
/*  755:     */             {
/*  756: 649 */               if (kind > 3) {
/*  757: 650 */                 kind = 3;
/*  758:     */               }
/*  759: 651 */               jjCheckNAddStates(59, 66);
/*  760:     */             }
/*  761: 652 */             break;
/*  762:     */           case 6: 
/*  763: 654 */             if ((0x0 & l) != 0L)
/*  764:     */             {
/*  765: 656 */               if (kind > 3) {
/*  766: 657 */                 kind = 3;
/*  767:     */               }
/*  768: 658 */               jjCheckNAddStates(67, 69);
/*  769:     */             }
/*  770: 659 */             break;
/*  771:     */           case 7: 
/*  772: 661 */             if ((0x3600 & l) != 0L)
/*  773:     */             {
/*  774: 663 */               if (kind > 3) {
/*  775: 664 */                 kind = 3;
/*  776:     */               }
/*  777: 665 */               jjCheckNAddTwoStates(2, 3);
/*  778:     */             }
/*  779: 666 */             break;
/*  780:     */           case 8: 
/*  781:     */           case 10: 
/*  782:     */           case 13: 
/*  783:     */           case 17: 
/*  784: 671 */             if ((0x0 & l) != 0L) {
/*  785: 672 */               jjCheckNAdd(6);
/*  786:     */             }
/*  787:     */             break;
/*  788:     */           case 9: 
/*  789: 675 */             if ((0x0 & l) != 0L) {
/*  790: 676 */               this.jjstateSet[(this.jjnewStateCnt++)] = 10;
/*  791:     */             }
/*  792:     */             break;
/*  793:     */           case 11: 
/*  794: 679 */             if ((0x0 & l) != 0L) {
/*  795: 680 */               this.jjstateSet[(this.jjnewStateCnt++)] = 12;
/*  796:     */             }
/*  797:     */             break;
/*  798:     */           case 12: 
/*  799: 683 */             if ((0x0 & l) != 0L) {
/*  800: 684 */               this.jjstateSet[(this.jjnewStateCnt++)] = 13;
/*  801:     */             }
/*  802:     */             break;
/*  803:     */           case 14: 
/*  804: 687 */             if ((0x0 & l) != 0L) {
/*  805: 688 */               this.jjstateSet[(this.jjnewStateCnt++)] = 15;
/*  806:     */             }
/*  807:     */             break;
/*  808:     */           case 15: 
/*  809: 691 */             if ((0x0 & l) != 0L) {
/*  810: 692 */               this.jjstateSet[(this.jjnewStateCnt++)] = 16;
/*  811:     */             }
/*  812:     */             break;
/*  813:     */           case 16: 
/*  814: 695 */             if ((0x0 & l) != 0L) {
/*  815: 696 */               this.jjstateSet[(this.jjnewStateCnt++)] = 17;
/*  816:     */             }
/*  817:     */             break;
/*  818:     */           case 18: 
/*  819: 699 */             if (this.curChar == '#') {
/*  820: 700 */               jjCheckNAddTwoStates(19, 20);
/*  821:     */             }
/*  822:     */             break;
/*  823:     */           case 19: 
/*  824: 703 */             if ((0x0 & l) != 0L)
/*  825:     */             {
/*  826: 705 */               if (kind > 9) {
/*  827: 706 */                 kind = 9;
/*  828:     */               }
/*  829: 707 */               jjCheckNAddTwoStates(19, 20);
/*  830:     */             }
/*  831: 708 */             break;
/*  832:     */           case 21: 
/*  833: 710 */             if ((0x0 & l) != 0L)
/*  834:     */             {
/*  835: 712 */               if (kind > 9) {
/*  836: 713 */                 kind = 9;
/*  837:     */               }
/*  838: 714 */               jjCheckNAddTwoStates(19, 20);
/*  839:     */             }
/*  840: 715 */             break;
/*  841:     */           case 22: 
/*  842: 717 */             if ((0x0 & l) != 0L)
/*  843:     */             {
/*  844: 719 */               if (kind > 9) {
/*  845: 720 */                 kind = 9;
/*  846:     */               }
/*  847: 721 */               jjCheckNAddStates(70, 77);
/*  848:     */             }
/*  849: 722 */             break;
/*  850:     */           case 23: 
/*  851: 724 */             if ((0x0 & l) != 0L)
/*  852:     */             {
/*  853: 726 */               if (kind > 9) {
/*  854: 727 */                 kind = 9;
/*  855:     */               }
/*  856: 728 */               jjCheckNAddStates(78, 80);
/*  857:     */             }
/*  858: 729 */             break;
/*  859:     */           case 24: 
/*  860: 731 */             if ((0x3600 & l) != 0L)
/*  861:     */             {
/*  862: 733 */               if (kind > 9) {
/*  863: 734 */                 kind = 9;
/*  864:     */               }
/*  865: 735 */               jjCheckNAddTwoStates(19, 20);
/*  866:     */             }
/*  867: 736 */             break;
/*  868:     */           case 25: 
/*  869:     */           case 27: 
/*  870:     */           case 30: 
/*  871:     */           case 34: 
/*  872: 741 */             if ((0x0 & l) != 0L) {
/*  873: 742 */               jjCheckNAdd(23);
/*  874:     */             }
/*  875:     */             break;
/*  876:     */           case 26: 
/*  877: 745 */             if ((0x0 & l) != 0L) {
/*  878: 746 */               this.jjstateSet[(this.jjnewStateCnt++)] = 27;
/*  879:     */             }
/*  880:     */             break;
/*  881:     */           case 28: 
/*  882: 749 */             if ((0x0 & l) != 0L) {
/*  883: 750 */               this.jjstateSet[(this.jjnewStateCnt++)] = 29;
/*  884:     */             }
/*  885:     */             break;
/*  886:     */           case 29: 
/*  887: 753 */             if ((0x0 & l) != 0L) {
/*  888: 754 */               this.jjstateSet[(this.jjnewStateCnt++)] = 30;
/*  889:     */             }
/*  890:     */             break;
/*  891:     */           case 31: 
/*  892: 757 */             if ((0x0 & l) != 0L) {
/*  893: 758 */               this.jjstateSet[(this.jjnewStateCnt++)] = 32;
/*  894:     */             }
/*  895:     */             break;
/*  896:     */           case 32: 
/*  897: 761 */             if ((0x0 & l) != 0L) {
/*  898: 762 */               this.jjstateSet[(this.jjnewStateCnt++)] = 33;
/*  899:     */             }
/*  900:     */             break;
/*  901:     */           case 33: 
/*  902: 765 */             if ((0x0 & l) != 0L) {
/*  903: 766 */               this.jjstateSet[(this.jjnewStateCnt++)] = 34;
/*  904:     */             }
/*  905:     */             break;
/*  906:     */           case 35: 
/*  907: 769 */             if (this.curChar == '"') {
/*  908: 770 */               jjCheckNAddStates(56, 58);
/*  909:     */             }
/*  910:     */             break;
/*  911:     */           case 36: 
/*  912: 773 */             if ((0x200 & l) != 0L) {
/*  913: 774 */               jjCheckNAddStates(56, 58);
/*  914:     */             }
/*  915:     */             break;
/*  916:     */           case 37: 
/*  917: 777 */             if ((this.curChar == '"') && (kind > 24)) {
/*  918: 778 */               kind = 24;
/*  919:     */             }
/*  920:     */             break;
/*  921:     */           case 39: 
/*  922: 781 */             if ((0x3400 & l) != 0L) {
/*  923: 782 */               jjCheckNAddStates(56, 58);
/*  924:     */             }
/*  925:     */             break;
/*  926:     */           case 40: 
/*  927: 785 */             if (this.curChar == '\n') {
/*  928: 786 */               jjCheckNAddStates(56, 58);
/*  929:     */             }
/*  930:     */             break;
/*  931:     */           case 41: 
/*  932: 789 */             if (this.curChar == '\r') {
/*  933: 790 */               this.jjstateSet[(this.jjnewStateCnt++)] = 40;
/*  934:     */             }
/*  935:     */             break;
/*  936:     */           case 42: 
/*  937: 793 */             if ((0x0 & l) != 0L) {
/*  938: 794 */               jjCheckNAddStates(56, 58);
/*  939:     */             }
/*  940:     */             break;
/*  941:     */           case 43: 
/*  942: 797 */             if ((0x0 & l) != 0L) {
/*  943: 798 */               jjCheckNAddStates(81, 89);
/*  944:     */             }
/*  945:     */             break;
/*  946:     */           case 44: 
/*  947: 801 */             if ((0x0 & l) != 0L) {
/*  948: 802 */               jjCheckNAddStates(90, 93);
/*  949:     */             }
/*  950:     */             break;
/*  951:     */           case 45: 
/*  952: 805 */             if ((0x3600 & l) != 0L) {
/*  953: 806 */               jjCheckNAddStates(56, 58);
/*  954:     */             }
/*  955:     */             break;
/*  956:     */           case 46: 
/*  957:     */           case 48: 
/*  958:     */           case 51: 
/*  959:     */           case 55: 
/*  960: 812 */             if ((0x0 & l) != 0L) {
/*  961: 813 */               jjCheckNAdd(44);
/*  962:     */             }
/*  963:     */             break;
/*  964:     */           case 47: 
/*  965: 816 */             if ((0x0 & l) != 0L) {
/*  966: 817 */               this.jjstateSet[(this.jjnewStateCnt++)] = 48;
/*  967:     */             }
/*  968:     */             break;
/*  969:     */           case 49: 
/*  970: 820 */             if ((0x0 & l) != 0L) {
/*  971: 821 */               this.jjstateSet[(this.jjnewStateCnt++)] = 50;
/*  972:     */             }
/*  973:     */             break;
/*  974:     */           case 50: 
/*  975: 824 */             if ((0x0 & l) != 0L) {
/*  976: 825 */               this.jjstateSet[(this.jjnewStateCnt++)] = 51;
/*  977:     */             }
/*  978:     */             break;
/*  979:     */           case 52: 
/*  980: 828 */             if ((0x0 & l) != 0L) {
/*  981: 829 */               this.jjstateSet[(this.jjnewStateCnt++)] = 53;
/*  982:     */             }
/*  983:     */             break;
/*  984:     */           case 53: 
/*  985: 832 */             if ((0x0 & l) != 0L) {
/*  986: 833 */               this.jjstateSet[(this.jjnewStateCnt++)] = 54;
/*  987:     */             }
/*  988:     */             break;
/*  989:     */           case 54: 
/*  990: 836 */             if ((0x0 & l) != 0L) {
/*  991: 837 */               this.jjstateSet[(this.jjnewStateCnt++)] = 55;
/*  992:     */             }
/*  993:     */             break;
/*  994:     */           case 56: 
/*  995: 840 */             if (this.curChar == '\'') {
/*  996: 841 */               jjCheckNAddStates(53, 55);
/*  997:     */             }
/*  998:     */             break;
/*  999:     */           case 57: 
/* 1000: 844 */             if ((0x200 & l) != 0L) {
/* 1001: 845 */               jjCheckNAddStates(53, 55);
/* 1002:     */             }
/* 1003:     */             break;
/* 1004:     */           case 58: 
/* 1005: 848 */             if ((this.curChar == '\'') && (kind > 24)) {
/* 1006: 849 */               kind = 24;
/* 1007:     */             }
/* 1008:     */             break;
/* 1009:     */           case 60: 
/* 1010: 852 */             if ((0x3400 & l) != 0L) {
/* 1011: 853 */               jjCheckNAddStates(53, 55);
/* 1012:     */             }
/* 1013:     */             break;
/* 1014:     */           case 61: 
/* 1015: 856 */             if (this.curChar == '\n') {
/* 1016: 857 */               jjCheckNAddStates(53, 55);
/* 1017:     */             }
/* 1018:     */             break;
/* 1019:     */           case 62: 
/* 1020: 860 */             if (this.curChar == '\r') {
/* 1021: 861 */               this.jjstateSet[(this.jjnewStateCnt++)] = 61;
/* 1022:     */             }
/* 1023:     */             break;
/* 1024:     */           case 63: 
/* 1025: 864 */             if ((0x0 & l) != 0L) {
/* 1026: 865 */               jjCheckNAddStates(53, 55);
/* 1027:     */             }
/* 1028:     */             break;
/* 1029:     */           case 64: 
/* 1030: 868 */             if ((0x0 & l) != 0L) {
/* 1031: 869 */               jjCheckNAddStates(94, 102);
/* 1032:     */             }
/* 1033:     */             break;
/* 1034:     */           case 65: 
/* 1035: 872 */             if ((0x0 & l) != 0L) {
/* 1036: 873 */               jjCheckNAddStates(103, 106);
/* 1037:     */             }
/* 1038:     */             break;
/* 1039:     */           case 66: 
/* 1040: 876 */             if ((0x3600 & l) != 0L) {
/* 1041: 877 */               jjCheckNAddStates(53, 55);
/* 1042:     */             }
/* 1043:     */             break;
/* 1044:     */           case 67: 
/* 1045:     */           case 69: 
/* 1046:     */           case 72: 
/* 1047:     */           case 76: 
/* 1048: 883 */             if ((0x0 & l) != 0L) {
/* 1049: 884 */               jjCheckNAdd(65);
/* 1050:     */             }
/* 1051:     */             break;
/* 1052:     */           case 68: 
/* 1053: 887 */             if ((0x0 & l) != 0L) {
/* 1054: 888 */               this.jjstateSet[(this.jjnewStateCnt++)] = 69;
/* 1055:     */             }
/* 1056:     */             break;
/* 1057:     */           case 70: 
/* 1058: 891 */             if ((0x0 & l) != 0L) {
/* 1059: 892 */               this.jjstateSet[(this.jjnewStateCnt++)] = 71;
/* 1060:     */             }
/* 1061:     */             break;
/* 1062:     */           case 71: 
/* 1063: 895 */             if ((0x0 & l) != 0L) {
/* 1064: 896 */               this.jjstateSet[(this.jjnewStateCnt++)] = 72;
/* 1065:     */             }
/* 1066:     */             break;
/* 1067:     */           case 73: 
/* 1068: 899 */             if ((0x0 & l) != 0L) {
/* 1069: 900 */               this.jjstateSet[(this.jjnewStateCnt++)] = 74;
/* 1070:     */             }
/* 1071:     */             break;
/* 1072:     */           case 74: 
/* 1073: 903 */             if ((0x0 & l) != 0L) {
/* 1074: 904 */               this.jjstateSet[(this.jjnewStateCnt++)] = 75;
/* 1075:     */             }
/* 1076:     */             break;
/* 1077:     */           case 75: 
/* 1078: 907 */             if ((0x0 & l) != 0L) {
/* 1079: 908 */               this.jjstateSet[(this.jjnewStateCnt++)] = 76;
/* 1080:     */             }
/* 1081:     */             break;
/* 1082:     */           case 77: 
/* 1083: 911 */             if (this.curChar == '!') {
/* 1084: 912 */               jjCheckNAddTwoStates(78, 87);
/* 1085:     */             }
/* 1086:     */             break;
/* 1087:     */           case 78: 
/* 1088: 915 */             if ((0x3600 & l) != 0L) {
/* 1089: 916 */               jjCheckNAddTwoStates(78, 87);
/* 1090:     */             }
/* 1091:     */             break;
/* 1092:     */           case 92: 
/* 1093: 919 */             if ((0x0 & l) != 0L)
/* 1094:     */             {
/* 1095: 921 */               if (kind > 31) {
/* 1096: 922 */                 kind = 31;
/* 1097:     */               }
/* 1098: 923 */               jjCheckNAddTwoStates(90, 91);
/* 1099:     */             }
/* 1100: 924 */             break;
/* 1101:     */           case 93: 
/* 1102: 926 */             if ((0x0 & l) != 0L)
/* 1103:     */             {
/* 1104: 928 */               if (kind > 31) {
/* 1105: 929 */                 kind = 31;
/* 1106:     */               }
/* 1107: 930 */               jjCheckNAddStates(107, 114);
/* 1108:     */             }
/* 1109: 931 */             break;
/* 1110:     */           case 94: 
/* 1111: 933 */             if ((0x0 & l) != 0L)
/* 1112:     */             {
/* 1113: 935 */               if (kind > 31) {
/* 1114: 936 */                 kind = 31;
/* 1115:     */               }
/* 1116: 937 */               jjCheckNAddStates(115, 117);
/* 1117:     */             }
/* 1118: 938 */             break;
/* 1119:     */           case 95: 
/* 1120: 940 */             if ((0x3600 & l) != 0L)
/* 1121:     */             {
/* 1122: 942 */               if (kind > 31) {
/* 1123: 943 */                 kind = 31;
/* 1124:     */               }
/* 1125: 944 */               jjCheckNAddTwoStates(90, 91);
/* 1126:     */             }
/* 1127: 945 */             break;
/* 1128:     */           case 96: 
/* 1129:     */           case 98: 
/* 1130:     */           case 101: 
/* 1131:     */           case 105: 
/* 1132: 950 */             if ((0x0 & l) != 0L) {
/* 1133: 951 */               jjCheckNAdd(94);
/* 1134:     */             }
/* 1135:     */             break;
/* 1136:     */           case 97: 
/* 1137: 954 */             if ((0x0 & l) != 0L) {
/* 1138: 955 */               this.jjstateSet[(this.jjnewStateCnt++)] = 98;
/* 1139:     */             }
/* 1140:     */             break;
/* 1141:     */           case 99: 
/* 1142: 958 */             if ((0x0 & l) != 0L) {
/* 1143: 959 */               this.jjstateSet[(this.jjnewStateCnt++)] = 100;
/* 1144:     */             }
/* 1145:     */             break;
/* 1146:     */           case 100: 
/* 1147: 962 */             if ((0x0 & l) != 0L) {
/* 1148: 963 */               this.jjstateSet[(this.jjnewStateCnt++)] = 101;
/* 1149:     */             }
/* 1150:     */             break;
/* 1151:     */           case 102: 
/* 1152: 966 */             if ((0x0 & l) != 0L) {
/* 1153: 967 */               this.jjstateSet[(this.jjnewStateCnt++)] = 103;
/* 1154:     */             }
/* 1155:     */             break;
/* 1156:     */           case 103: 
/* 1157: 970 */             if ((0x0 & l) != 0L) {
/* 1158: 971 */               this.jjstateSet[(this.jjnewStateCnt++)] = 104;
/* 1159:     */             }
/* 1160:     */             break;
/* 1161:     */           case 104: 
/* 1162: 974 */             if ((0x0 & l) != 0L) {
/* 1163: 975 */               this.jjstateSet[(this.jjnewStateCnt++)] = 105;
/* 1164:     */             }
/* 1165:     */             break;
/* 1166:     */           case 107: 
/* 1167: 978 */             if ((0x0 & l) != 0L)
/* 1168:     */             {
/* 1169: 980 */               if (kind > 31) {
/* 1170: 981 */                 kind = 31;
/* 1171:     */               }
/* 1172: 982 */               jjCheckNAddStates(118, 125);
/* 1173:     */             }
/* 1174: 983 */             break;
/* 1175:     */           case 108: 
/* 1176: 985 */             if ((0x0 & l) != 0L)
/* 1177:     */             {
/* 1178: 987 */               if (kind > 31) {
/* 1179: 988 */                 kind = 31;
/* 1180:     */               }
/* 1181: 989 */               jjCheckNAddStates(126, 128);
/* 1182:     */             }
/* 1183: 990 */             break;
/* 1184:     */           case 109: 
/* 1185:     */           case 111: 
/* 1186:     */           case 114: 
/* 1187:     */           case 118: 
/* 1188: 995 */             if ((0x0 & l) != 0L) {
/* 1189: 996 */               jjCheckNAdd(108);
/* 1190:     */             }
/* 1191:     */             break;
/* 1192:     */           case 110: 
/* 1193: 999 */             if ((0x0 & l) != 0L) {
/* 1194:1000 */               this.jjstateSet[(this.jjnewStateCnt++)] = 111;
/* 1195:     */             }
/* 1196:     */             break;
/* 1197:     */           case 112: 
/* 1198:1003 */             if ((0x0 & l) != 0L) {
/* 1199:1004 */               this.jjstateSet[(this.jjnewStateCnt++)] = 113;
/* 1200:     */             }
/* 1201:     */             break;
/* 1202:     */           case 113: 
/* 1203:1007 */             if ((0x0 & l) != 0L) {
/* 1204:1008 */               this.jjstateSet[(this.jjnewStateCnt++)] = 114;
/* 1205:     */             }
/* 1206:     */             break;
/* 1207:     */           case 115: 
/* 1208:1011 */             if ((0x0 & l) != 0L) {
/* 1209:1012 */               this.jjstateSet[(this.jjnewStateCnt++)] = 116;
/* 1210:     */             }
/* 1211:     */             break;
/* 1212:     */           case 116: 
/* 1213:1015 */             if ((0x0 & l) != 0L) {
/* 1214:1016 */               this.jjstateSet[(this.jjnewStateCnt++)] = 117;
/* 1215:     */             }
/* 1216:     */             break;
/* 1217:     */           case 117: 
/* 1218:1019 */             if ((0x0 & l) != 0L) {
/* 1219:1020 */               this.jjstateSet[(this.jjnewStateCnt++)] = 118;
/* 1220:     */             }
/* 1221:     */             break;
/* 1222:     */           case 120: 
/* 1223:1023 */             if ((0x0 & l) != 0L)
/* 1224:     */             {
/* 1225:1025 */               if (kind > 3) {
/* 1226:1026 */                 kind = 3;
/* 1227:     */               }
/* 1228:1027 */               jjCheckNAddStates(129, 136);
/* 1229:     */             }
/* 1230:1028 */             break;
/* 1231:     */           case 121: 
/* 1232:1030 */             if ((0x0 & l) != 0L)
/* 1233:     */             {
/* 1234:1032 */               if (kind > 3) {
/* 1235:1033 */                 kind = 3;
/* 1236:     */               }
/* 1237:1034 */               jjCheckNAddStates(137, 139);
/* 1238:     */             }
/* 1239:1035 */             break;
/* 1240:     */           case 122: 
/* 1241:     */           case 124: 
/* 1242:     */           case 127: 
/* 1243:     */           case 131: 
/* 1244:1040 */             if ((0x0 & l) != 0L) {
/* 1245:1041 */               jjCheckNAdd(121);
/* 1246:     */             }
/* 1247:     */             break;
/* 1248:     */           case 123: 
/* 1249:1044 */             if ((0x0 & l) != 0L) {
/* 1250:1045 */               this.jjstateSet[(this.jjnewStateCnt++)] = 124;
/* 1251:     */             }
/* 1252:     */             break;
/* 1253:     */           case 125: 
/* 1254:1048 */             if ((0x0 & l) != 0L) {
/* 1255:1049 */               this.jjstateSet[(this.jjnewStateCnt++)] = 126;
/* 1256:     */             }
/* 1257:     */             break;
/* 1258:     */           case 126: 
/* 1259:1052 */             if ((0x0 & l) != 0L) {
/* 1260:1053 */               this.jjstateSet[(this.jjnewStateCnt++)] = 127;
/* 1261:     */             }
/* 1262:     */             break;
/* 1263:     */           case 128: 
/* 1264:1056 */             if ((0x0 & l) != 0L) {
/* 1265:1057 */               this.jjstateSet[(this.jjnewStateCnt++)] = 129;
/* 1266:     */             }
/* 1267:     */             break;
/* 1268:     */           case 129: 
/* 1269:1060 */             if ((0x0 & l) != 0L) {
/* 1270:1061 */               this.jjstateSet[(this.jjnewStateCnt++)] = 130;
/* 1271:     */             }
/* 1272:     */             break;
/* 1273:     */           case 130: 
/* 1274:1064 */             if ((0x0 & l) != 0L) {
/* 1275:1065 */               this.jjstateSet[(this.jjnewStateCnt++)] = 131;
/* 1276:     */             }
/* 1277:     */             break;
/* 1278:     */           case 133: 
/* 1279:1068 */             if (this.curChar == '(') {
/* 1280:1069 */               jjCheckNAddStates(140, 145);
/* 1281:     */             }
/* 1282:     */             break;
/* 1283:     */           case 134: 
/* 1284:1072 */             if ((0x0 & l) != 0L) {
/* 1285:1073 */               jjCheckNAddStates(146, 149);
/* 1286:     */             }
/* 1287:     */             break;
/* 1288:     */           case 135: 
/* 1289:1076 */             if ((0x3600 & l) != 0L) {
/* 1290:1077 */               jjCheckNAddTwoStates(135, 136);
/* 1291:     */             }
/* 1292:     */             break;
/* 1293:     */           case 136: 
/* 1294:1080 */             if ((this.curChar == ')') && (kind > 26)) {
/* 1295:1081 */               kind = 26;
/* 1296:     */             }
/* 1297:     */             break;
/* 1298:     */           case 138: 
/* 1299:1084 */             if ((0x0 & l) != 0L) {
/* 1300:1085 */               jjCheckNAddStates(146, 149);
/* 1301:     */             }
/* 1302:     */             break;
/* 1303:     */           case 139: 
/* 1304:1088 */             if ((0x0 & l) != 0L) {
/* 1305:1089 */               jjCheckNAddStates(150, 158);
/* 1306:     */             }
/* 1307:     */             break;
/* 1308:     */           case 140: 
/* 1309:1092 */             if ((0x0 & l) != 0L) {
/* 1310:1093 */               jjCheckNAddStates(159, 162);
/* 1311:     */             }
/* 1312:     */             break;
/* 1313:     */           case 141: 
/* 1314:1096 */             if ((0x3600 & l) != 0L) {
/* 1315:1097 */               jjCheckNAddStates(146, 149);
/* 1316:     */             }
/* 1317:     */             break;
/* 1318:     */           case 142: 
/* 1319:     */           case 144: 
/* 1320:     */           case 147: 
/* 1321:     */           case 151: 
/* 1322:1103 */             if ((0x0 & l) != 0L) {
/* 1323:1104 */               jjCheckNAdd(140);
/* 1324:     */             }
/* 1325:     */             break;
/* 1326:     */           case 143: 
/* 1327:1107 */             if ((0x0 & l) != 0L) {
/* 1328:1108 */               this.jjstateSet[(this.jjnewStateCnt++)] = 144;
/* 1329:     */             }
/* 1330:     */             break;
/* 1331:     */           case 145: 
/* 1332:1111 */             if ((0x0 & l) != 0L) {
/* 1333:1112 */               this.jjstateSet[(this.jjnewStateCnt++)] = 146;
/* 1334:     */             }
/* 1335:     */             break;
/* 1336:     */           case 146: 
/* 1337:1115 */             if ((0x0 & l) != 0L) {
/* 1338:1116 */               this.jjstateSet[(this.jjnewStateCnt++)] = 147;
/* 1339:     */             }
/* 1340:     */             break;
/* 1341:     */           case 148: 
/* 1342:1119 */             if ((0x0 & l) != 0L) {
/* 1343:1120 */               this.jjstateSet[(this.jjnewStateCnt++)] = 149;
/* 1344:     */             }
/* 1345:     */             break;
/* 1346:     */           case 149: 
/* 1347:1123 */             if ((0x0 & l) != 0L) {
/* 1348:1124 */               this.jjstateSet[(this.jjnewStateCnt++)] = 150;
/* 1349:     */             }
/* 1350:     */             break;
/* 1351:     */           case 150: 
/* 1352:1127 */             if ((0x0 & l) != 0L) {
/* 1353:1128 */               this.jjstateSet[(this.jjnewStateCnt++)] = 151;
/* 1354:     */             }
/* 1355:     */             break;
/* 1356:     */           case 152: 
/* 1357:1131 */             if (this.curChar == '\'') {
/* 1358:1132 */               jjCheckNAddStates(163, 165);
/* 1359:     */             }
/* 1360:     */             break;
/* 1361:     */           case 153: 
/* 1362:1135 */             if ((0x200 & l) != 0L) {
/* 1363:1136 */               jjCheckNAddStates(163, 165);
/* 1364:     */             }
/* 1365:     */             break;
/* 1366:     */           case 154: 
/* 1367:1139 */             if (this.curChar == '\'') {
/* 1368:1140 */               jjCheckNAddTwoStates(135, 136);
/* 1369:     */             }
/* 1370:     */             break;
/* 1371:     */           case 156: 
/* 1372:1143 */             if ((0x3400 & l) != 0L) {
/* 1373:1144 */               jjCheckNAddStates(163, 165);
/* 1374:     */             }
/* 1375:     */             break;
/* 1376:     */           case 157: 
/* 1377:1147 */             if (this.curChar == '\n') {
/* 1378:1148 */               jjCheckNAddStates(163, 165);
/* 1379:     */             }
/* 1380:     */             break;
/* 1381:     */           case 158: 
/* 1382:1151 */             if (this.curChar == '\r') {
/* 1383:1152 */               this.jjstateSet[(this.jjnewStateCnt++)] = 157;
/* 1384:     */             }
/* 1385:     */             break;
/* 1386:     */           case 159: 
/* 1387:1155 */             if ((0x0 & l) != 0L) {
/* 1388:1156 */               jjCheckNAddStates(163, 165);
/* 1389:     */             }
/* 1390:     */             break;
/* 1391:     */           case 160: 
/* 1392:1159 */             if ((0x0 & l) != 0L) {
/* 1393:1160 */               jjCheckNAddStates(166, 174);
/* 1394:     */             }
/* 1395:     */             break;
/* 1396:     */           case 161: 
/* 1397:1163 */             if ((0x0 & l) != 0L) {
/* 1398:1164 */               jjCheckNAddStates(175, 178);
/* 1399:     */             }
/* 1400:     */             break;
/* 1401:     */           case 162: 
/* 1402:1167 */             if ((0x3600 & l) != 0L) {
/* 1403:1168 */               jjCheckNAddStates(163, 165);
/* 1404:     */             }
/* 1405:     */             break;
/* 1406:     */           case 163: 
/* 1407:     */           case 165: 
/* 1408:     */           case 168: 
/* 1409:     */           case 172: 
/* 1410:1174 */             if ((0x0 & l) != 0L) {
/* 1411:1175 */               jjCheckNAdd(161);
/* 1412:     */             }
/* 1413:     */             break;
/* 1414:     */           case 164: 
/* 1415:1178 */             if ((0x0 & l) != 0L) {
/* 1416:1179 */               this.jjstateSet[(this.jjnewStateCnt++)] = 165;
/* 1417:     */             }
/* 1418:     */             break;
/* 1419:     */           case 166: 
/* 1420:1182 */             if ((0x0 & l) != 0L) {
/* 1421:1183 */               this.jjstateSet[(this.jjnewStateCnt++)] = 167;
/* 1422:     */             }
/* 1423:     */             break;
/* 1424:     */           case 167: 
/* 1425:1186 */             if ((0x0 & l) != 0L) {
/* 1426:1187 */               this.jjstateSet[(this.jjnewStateCnt++)] = 168;
/* 1427:     */             }
/* 1428:     */             break;
/* 1429:     */           case 169: 
/* 1430:1190 */             if ((0x0 & l) != 0L) {
/* 1431:1191 */               this.jjstateSet[(this.jjnewStateCnt++)] = 170;
/* 1432:     */             }
/* 1433:     */             break;
/* 1434:     */           case 170: 
/* 1435:1194 */             if ((0x0 & l) != 0L) {
/* 1436:1195 */               this.jjstateSet[(this.jjnewStateCnt++)] = 171;
/* 1437:     */             }
/* 1438:     */             break;
/* 1439:     */           case 171: 
/* 1440:1198 */             if ((0x0 & l) != 0L) {
/* 1441:1199 */               this.jjstateSet[(this.jjnewStateCnt++)] = 172;
/* 1442:     */             }
/* 1443:     */             break;
/* 1444:     */           case 173: 
/* 1445:1202 */             if (this.curChar == '"') {
/* 1446:1203 */               jjCheckNAddStates(179, 181);
/* 1447:     */             }
/* 1448:     */             break;
/* 1449:     */           case 174: 
/* 1450:1206 */             if ((0x200 & l) != 0L) {
/* 1451:1207 */               jjCheckNAddStates(179, 181);
/* 1452:     */             }
/* 1453:     */             break;
/* 1454:     */           case 175: 
/* 1455:1210 */             if (this.curChar == '"') {
/* 1456:1211 */               jjCheckNAddTwoStates(135, 136);
/* 1457:     */             }
/* 1458:     */             break;
/* 1459:     */           case 177: 
/* 1460:1214 */             if ((0x3400 & l) != 0L) {
/* 1461:1215 */               jjCheckNAddStates(179, 181);
/* 1462:     */             }
/* 1463:     */             break;
/* 1464:     */           case 178: 
/* 1465:1218 */             if (this.curChar == '\n') {
/* 1466:1219 */               jjCheckNAddStates(179, 181);
/* 1467:     */             }
/* 1468:     */             break;
/* 1469:     */           case 179: 
/* 1470:1222 */             if (this.curChar == '\r') {
/* 1471:1223 */               this.jjstateSet[(this.jjnewStateCnt++)] = 178;
/* 1472:     */             }
/* 1473:     */             break;
/* 1474:     */           case 180: 
/* 1475:1226 */             if ((0x0 & l) != 0L) {
/* 1476:1227 */               jjCheckNAddStates(179, 181);
/* 1477:     */             }
/* 1478:     */             break;
/* 1479:     */           case 181: 
/* 1480:1230 */             if ((0x0 & l) != 0L) {
/* 1481:1231 */               jjCheckNAddStates(182, 190);
/* 1482:     */             }
/* 1483:     */             break;
/* 1484:     */           case 182: 
/* 1485:1234 */             if ((0x0 & l) != 0L) {
/* 1486:1235 */               jjCheckNAddStates(191, 194);
/* 1487:     */             }
/* 1488:     */             break;
/* 1489:     */           case 183: 
/* 1490:1238 */             if ((0x3600 & l) != 0L) {
/* 1491:1239 */               jjCheckNAddStates(179, 181);
/* 1492:     */             }
/* 1493:     */             break;
/* 1494:     */           case 184: 
/* 1495:     */           case 186: 
/* 1496:     */           case 189: 
/* 1497:     */           case 193: 
/* 1498:1245 */             if ((0x0 & l) != 0L) {
/* 1499:1246 */               jjCheckNAdd(182);
/* 1500:     */             }
/* 1501:     */             break;
/* 1502:     */           case 185: 
/* 1503:1249 */             if ((0x0 & l) != 0L) {
/* 1504:1250 */               this.jjstateSet[(this.jjnewStateCnt++)] = 186;
/* 1505:     */             }
/* 1506:     */             break;
/* 1507:     */           case 187: 
/* 1508:1253 */             if ((0x0 & l) != 0L) {
/* 1509:1254 */               this.jjstateSet[(this.jjnewStateCnt++)] = 188;
/* 1510:     */             }
/* 1511:     */             break;
/* 1512:     */           case 188: 
/* 1513:1257 */             if ((0x0 & l) != 0L) {
/* 1514:1258 */               this.jjstateSet[(this.jjnewStateCnt++)] = 189;
/* 1515:     */             }
/* 1516:     */             break;
/* 1517:     */           case 190: 
/* 1518:1261 */             if ((0x0 & l) != 0L) {
/* 1519:1262 */               this.jjstateSet[(this.jjnewStateCnt++)] = 191;
/* 1520:     */             }
/* 1521:     */             break;
/* 1522:     */           case 191: 
/* 1523:1265 */             if ((0x0 & l) != 0L) {
/* 1524:1266 */               this.jjstateSet[(this.jjnewStateCnt++)] = 192;
/* 1525:     */             }
/* 1526:     */             break;
/* 1527:     */           case 192: 
/* 1528:1269 */             if ((0x0 & l) != 0L) {
/* 1529:1270 */               this.jjstateSet[(this.jjnewStateCnt++)] = 193;
/* 1530:     */             }
/* 1531:     */             break;
/* 1532:     */           case 194: 
/* 1533:1273 */             if ((0x3600 & l) != 0L) {
/* 1534:1274 */               jjCheckNAddStates(195, 201);
/* 1535:     */             }
/* 1536:     */             break;
/* 1537:     */           case 197: 
/* 1538:1277 */             if (this.curChar == '+') {
/* 1539:1278 */               jjCheckNAddStates(202, 204);
/* 1540:     */             }
/* 1541:     */             break;
/* 1542:     */           case 198: 
/* 1543:     */           case 227: 
/* 1544:1282 */             if ((this.curChar == '?') && (kind > 46)) {
/* 1545:1283 */               kind = 46;
/* 1546:     */             }
/* 1547:     */             break;
/* 1548:     */           case 199: 
/* 1549:1286 */             if ((0x0 & l) != 0L)
/* 1550:     */             {
/* 1551:1288 */               if (kind > 46) {
/* 1552:1289 */                 kind = 46;
/* 1553:     */               }
/* 1554:1290 */               jjCheckNAddStates(205, 213);
/* 1555:     */             }
/* 1556:1291 */             break;
/* 1557:     */           case 200: 
/* 1558:1293 */             if ((0x0 & l) != 0L) {
/* 1559:1294 */               jjCheckNAdd(201);
/* 1560:     */             }
/* 1561:     */             break;
/* 1562:     */           case 201: 
/* 1563:1297 */             if (this.curChar == '-') {
/* 1564:1298 */               this.jjstateSet[(this.jjnewStateCnt++)] = 202;
/* 1565:     */             }
/* 1566:     */             break;
/* 1567:     */           case 202: 
/* 1568:1301 */             if ((0x0 & l) != 0L)
/* 1569:     */             {
/* 1570:1303 */               if (kind > 46) {
/* 1571:1304 */                 kind = 46;
/* 1572:     */               }
/* 1573:1305 */               jjCheckNAddStates(214, 218);
/* 1574:     */             }
/* 1575:1306 */             break;
/* 1576:     */           case 203: 
/* 1577:1308 */             if (((0x0 & l) != 0L) && (kind > 46)) {
/* 1578:1309 */               kind = 46;
/* 1579:     */             }
/* 1580:     */             break;
/* 1581:     */           case 204: 
/* 1582:     */           case 206: 
/* 1583:     */           case 209: 
/* 1584:     */           case 213: 
/* 1585:1315 */             if ((0x0 & l) != 0L) {
/* 1586:1316 */               jjCheckNAdd(203);
/* 1587:     */             }
/* 1588:     */             break;
/* 1589:     */           case 205: 
/* 1590:1319 */             if ((0x0 & l) != 0L) {
/* 1591:1320 */               this.jjstateSet[(this.jjnewStateCnt++)] = 206;
/* 1592:     */             }
/* 1593:     */             break;
/* 1594:     */           case 207: 
/* 1595:1323 */             if ((0x0 & l) != 0L) {
/* 1596:1324 */               this.jjstateSet[(this.jjnewStateCnt++)] = 208;
/* 1597:     */             }
/* 1598:     */             break;
/* 1599:     */           case 208: 
/* 1600:1327 */             if ((0x0 & l) != 0L) {
/* 1601:1328 */               this.jjstateSet[(this.jjnewStateCnt++)] = 209;
/* 1602:     */             }
/* 1603:     */             break;
/* 1604:     */           case 210: 
/* 1605:1331 */             if ((0x0 & l) != 0L) {
/* 1606:1332 */               this.jjstateSet[(this.jjnewStateCnt++)] = 211;
/* 1607:     */             }
/* 1608:     */             break;
/* 1609:     */           case 211: 
/* 1610:1335 */             if ((0x0 & l) != 0L) {
/* 1611:1336 */               this.jjstateSet[(this.jjnewStateCnt++)] = 212;
/* 1612:     */             }
/* 1613:     */             break;
/* 1614:     */           case 212: 
/* 1615:1339 */             if ((0x0 & l) != 0L) {
/* 1616:1340 */               this.jjstateSet[(this.jjnewStateCnt++)] = 213;
/* 1617:     */             }
/* 1618:     */             break;
/* 1619:     */           case 214: 
/* 1620:     */           case 216: 
/* 1621:     */           case 219: 
/* 1622:     */           case 223: 
/* 1623:1346 */             if ((0x0 & l) != 0L) {
/* 1624:1347 */               jjCheckNAdd(200);
/* 1625:     */             }
/* 1626:     */             break;
/* 1627:     */           case 215: 
/* 1628:1350 */             if ((0x0 & l) != 0L) {
/* 1629:1351 */               this.jjstateSet[(this.jjnewStateCnt++)] = 216;
/* 1630:     */             }
/* 1631:     */             break;
/* 1632:     */           case 217: 
/* 1633:1354 */             if ((0x0 & l) != 0L) {
/* 1634:1355 */               this.jjstateSet[(this.jjnewStateCnt++)] = 218;
/* 1635:     */             }
/* 1636:     */             break;
/* 1637:     */           case 218: 
/* 1638:1358 */             if ((0x0 & l) != 0L) {
/* 1639:1359 */               this.jjstateSet[(this.jjnewStateCnt++)] = 219;
/* 1640:     */             }
/* 1641:     */             break;
/* 1642:     */           case 220: 
/* 1643:1362 */             if ((0x0 & l) != 0L) {
/* 1644:1363 */               this.jjstateSet[(this.jjnewStateCnt++)] = 221;
/* 1645:     */             }
/* 1646:     */             break;
/* 1647:     */           case 221: 
/* 1648:1366 */             if ((0x0 & l) != 0L) {
/* 1649:1367 */               this.jjstateSet[(this.jjnewStateCnt++)] = 222;
/* 1650:     */             }
/* 1651:     */             break;
/* 1652:     */           case 222: 
/* 1653:1370 */             if ((0x0 & l) != 0L) {
/* 1654:1371 */               this.jjstateSet[(this.jjnewStateCnt++)] = 223;
/* 1655:     */             }
/* 1656:     */             break;
/* 1657:     */           case 224: 
/* 1658:1374 */             if ((0x0 & l) != 0L)
/* 1659:     */             {
/* 1660:1376 */               if (kind > 46) {
/* 1661:1377 */                 kind = 46;
/* 1662:     */               }
/* 1663:1378 */               jjCheckNAddStates(219, 221);
/* 1664:     */             }
/* 1665:1379 */             break;
/* 1666:     */           case 225: 
/* 1667:1381 */             if ((0x0 & l) != 0L)
/* 1668:     */             {
/* 1669:1383 */               if (kind > 46) {
/* 1670:1384 */                 kind = 46;
/* 1671:     */               }
/* 1672:1385 */               jjCheckNAddStates(222, 224);
/* 1673:     */             }
/* 1674:1386 */             break;
/* 1675:     */           case 226: 
/* 1676:1388 */             if ((0x0 & l) != 0L)
/* 1677:     */             {
/* 1678:1390 */               if (kind > 46) {
/* 1679:1391 */                 kind = 46;
/* 1680:     */               }
/* 1681:1392 */               jjCheckNAddStates(225, 227);
/* 1682:     */             }
/* 1683:1393 */             break;
/* 1684:     */           case 228: 
/* 1685:     */           case 231: 
/* 1686:     */           case 233: 
/* 1687:     */           case 234: 
/* 1688:     */           case 237: 
/* 1689:     */           case 238: 
/* 1690:     */           case 240: 
/* 1691:     */           case 244: 
/* 1692:     */           case 248: 
/* 1693:     */           case 251: 
/* 1694:     */           case 253: 
/* 1695:1405 */             if (this.curChar == '?') {
/* 1696:1406 */               jjCheckNAdd(227);
/* 1697:     */             }
/* 1698:     */             break;
/* 1699:     */           case 229: 
/* 1700:1409 */             if ((0x0 & l) != 0L)
/* 1701:     */             {
/* 1702:1411 */               if (kind > 46) {
/* 1703:1412 */                 kind = 46;
/* 1704:     */               }
/* 1705:1413 */               jjCheckNAddTwoStates(198, 203);
/* 1706:     */             }
/* 1707:1414 */             break;
/* 1708:     */           case 230: 
/* 1709:1416 */             if (this.curChar == '?') {
/* 1710:1417 */               jjCheckNAddTwoStates(227, 231);
/* 1711:     */             }
/* 1712:     */             break;
/* 1713:     */           case 232: 
/* 1714:1420 */             if (this.curChar == '?') {
/* 1715:1421 */               jjCheckNAddStates(228, 230);
/* 1716:     */             }
/* 1717:     */             break;
/* 1718:     */           case 235: 
/* 1719:1424 */             if (this.curChar == '?') {
/* 1720:1425 */               this.jjstateSet[(this.jjnewStateCnt++)] = 234;
/* 1721:     */             }
/* 1722:     */             break;
/* 1723:     */           case 236: 
/* 1724:1428 */             if (this.curChar == '?') {
/* 1725:1429 */               jjCheckNAddStates(231, 234);
/* 1726:     */             }
/* 1727:     */             break;
/* 1728:     */           case 239: 
/* 1729:1432 */             if (this.curChar == '?') {
/* 1730:1433 */               this.jjstateSet[(this.jjnewStateCnt++)] = 238;
/* 1731:     */             }
/* 1732:     */             break;
/* 1733:     */           case 241: 
/* 1734:1436 */             if (this.curChar == '?') {
/* 1735:1437 */               this.jjstateSet[(this.jjnewStateCnt++)] = 240;
/* 1736:     */             }
/* 1737:     */             break;
/* 1738:     */           case 242: 
/* 1739:1440 */             if (this.curChar == '?') {
/* 1740:1441 */               this.jjstateSet[(this.jjnewStateCnt++)] = 241;
/* 1741:     */             }
/* 1742:     */             break;
/* 1743:     */           case 243: 
/* 1744:1444 */             if (this.curChar == '?') {
/* 1745:1445 */               jjCheckNAddStates(235, 239);
/* 1746:     */             }
/* 1747:     */             break;
/* 1748:     */           case 245: 
/* 1749:1448 */             if (this.curChar == '?') {
/* 1750:1449 */               this.jjstateSet[(this.jjnewStateCnt++)] = 244;
/* 1751:     */             }
/* 1752:     */             break;
/* 1753:     */           case 246: 
/* 1754:1452 */             if (this.curChar == '?') {
/* 1755:1453 */               this.jjstateSet[(this.jjnewStateCnt++)] = 245;
/* 1756:     */             }
/* 1757:     */             break;
/* 1758:     */           case 247: 
/* 1759:1456 */             if (this.curChar == '?') {
/* 1760:1457 */               this.jjstateSet[(this.jjnewStateCnt++)] = 246;
/* 1761:     */             }
/* 1762:     */             break;
/* 1763:     */           case 249: 
/* 1764:1460 */             if (this.curChar == '?') {
/* 1765:1461 */               this.jjstateSet[(this.jjnewStateCnt++)] = 248;
/* 1766:     */             }
/* 1767:     */             break;
/* 1768:     */           case 250: 
/* 1769:1464 */             if (this.curChar == '?') {
/* 1770:1465 */               this.jjstateSet[(this.jjnewStateCnt++)] = 249;
/* 1771:     */             }
/* 1772:     */             break;
/* 1773:     */           case 252: 
/* 1774:1468 */             if (this.curChar == '?') {
/* 1775:1469 */               this.jjstateSet[(this.jjnewStateCnt++)] = 251;
/* 1776:     */             }
/* 1777:     */             break;
/* 1778:     */           case 254: 
/* 1779:1472 */             if (this.curChar == '.') {
/* 1780:1473 */               jjCheckNAddStates(42, 52);
/* 1781:     */             }
/* 1782:     */             break;
/* 1783:     */           case 255: 
/* 1784:1476 */             if ((0x0 & l) != 0L) {
/* 1785:1477 */               jjCheckNAddTwoStates(255, 257);
/* 1786:     */             }
/* 1787:     */             break;
/* 1788:     */           case 258: 
/* 1789:1480 */             if ((0x0 & l) != 0L) {
/* 1790:1481 */               jjCheckNAddTwoStates(258, 260);
/* 1791:     */             }
/* 1792:     */             break;
/* 1793:     */           case 261: 
/* 1794:1484 */             if ((0x0 & l) != 0L) {
/* 1795:1485 */               jjCheckNAddTwoStates(261, 263);
/* 1796:     */             }
/* 1797:     */             break;
/* 1798:     */           case 264: 
/* 1799:1488 */             if ((0x0 & l) != 0L) {
/* 1800:1489 */               jjCheckNAddTwoStates(264, 266);
/* 1801:     */             }
/* 1802:     */             break;
/* 1803:     */           case 267: 
/* 1804:1492 */             if ((0x0 & l) != 0L) {
/* 1805:1493 */               jjCheckNAddTwoStates(267, 269);
/* 1806:     */             }
/* 1807:     */             break;
/* 1808:     */           case 270: 
/* 1809:1496 */             if ((0x0 & l) != 0L) {
/* 1810:1497 */               jjCheckNAddTwoStates(270, 272);
/* 1811:     */             }
/* 1812:     */             break;
/* 1813:     */           case 273: 
/* 1814:1500 */             if ((0x0 & l) != 0L) {
/* 1815:1501 */               jjCheckNAddTwoStates(273, 275);
/* 1816:     */             }
/* 1817:     */             break;
/* 1818:     */           case 276: 
/* 1819:1504 */             if ((0x0 & l) != 0L) {
/* 1820:1505 */               jjCheckNAddTwoStates(276, 278);
/* 1821:     */             }
/* 1822:     */             break;
/* 1823:     */           case 279: 
/* 1824:1508 */             if ((0x0 & l) != 0L) {
/* 1825:1509 */               jjCheckNAddTwoStates(279, 280);
/* 1826:     */             }
/* 1827:     */             break;
/* 1828:     */           case 280: 
/* 1829:1512 */             if ((this.curChar == '%') && (kind > 40)) {
/* 1830:1513 */               kind = 40;
/* 1831:     */             }
/* 1832:     */             break;
/* 1833:     */           case 281: 
/* 1834:1516 */             if ((0x0 & l) != 0L)
/* 1835:     */             {
/* 1836:1518 */               if (kind > 41) {
/* 1837:1519 */                 kind = 41;
/* 1838:     */               }
/* 1839:1520 */               jjCheckNAdd(281);
/* 1840:     */             }
/* 1841:1521 */             break;
/* 1842:     */           case 282: 
/* 1843:1523 */             if ((0x0 & l) != 0L)
/* 1844:     */             {
/* 1845:1525 */               if (kind > 45) {
/* 1846:1526 */                 kind = 45;
/* 1847:     */               }
/* 1848:1527 */               jjCheckNAdd(282);
/* 1849:     */             }
/* 1850:1528 */             break;
/* 1851:     */           case 283: 
/* 1852:1530 */             if ((0x0 & l) != 0L)
/* 1853:     */             {
/* 1854:1532 */               if (kind > 41) {
/* 1855:1533 */                 kind = 41;
/* 1856:     */               }
/* 1857:1534 */               jjCheckNAddStates(0, 41);
/* 1858:     */             }
/* 1859:1535 */             break;
/* 1860:     */           case 284: 
/* 1861:1537 */             if ((0x0 & l) != 0L) {
/* 1862:1538 */               jjCheckNAddTwoStates(284, 257);
/* 1863:     */             }
/* 1864:     */             break;
/* 1865:     */           case 285: 
/* 1866:1541 */             if ((0x0 & l) != 0L) {
/* 1867:1542 */               jjCheckNAddTwoStates(285, 286);
/* 1868:     */             }
/* 1869:     */             break;
/* 1870:     */           case 286: 
/* 1871:1545 */             if (this.curChar == '.') {
/* 1872:1546 */               jjCheckNAdd(255);
/* 1873:     */             }
/* 1874:     */             break;
/* 1875:     */           case 287: 
/* 1876:1549 */             if ((0x0 & l) != 0L) {
/* 1877:1550 */               jjCheckNAddTwoStates(287, 260);
/* 1878:     */             }
/* 1879:     */             break;
/* 1880:     */           case 288: 
/* 1881:1553 */             if ((0x0 & l) != 0L) {
/* 1882:1554 */               jjCheckNAddTwoStates(288, 289);
/* 1883:     */             }
/* 1884:     */             break;
/* 1885:     */           case 289: 
/* 1886:1557 */             if (this.curChar == '.') {
/* 1887:1558 */               jjCheckNAdd(258);
/* 1888:     */             }
/* 1889:     */             break;
/* 1890:     */           case 290: 
/* 1891:1561 */             if ((0x0 & l) != 0L) {
/* 1892:1562 */               jjCheckNAddTwoStates(290, 263);
/* 1893:     */             }
/* 1894:     */             break;
/* 1895:     */           case 291: 
/* 1896:1565 */             if ((0x0 & l) != 0L) {
/* 1897:1566 */               jjCheckNAddTwoStates(291, 292);
/* 1898:     */             }
/* 1899:     */             break;
/* 1900:     */           case 292: 
/* 1901:1569 */             if (this.curChar == '.') {
/* 1902:1570 */               jjCheckNAdd(261);
/* 1903:     */             }
/* 1904:     */             break;
/* 1905:     */           case 293: 
/* 1906:1573 */             if ((0x0 & l) != 0L) {
/* 1907:1574 */               jjCheckNAddTwoStates(293, 266);
/* 1908:     */             }
/* 1909:     */             break;
/* 1910:     */           case 294: 
/* 1911:1577 */             if ((0x0 & l) != 0L) {
/* 1912:1578 */               jjCheckNAddTwoStates(294, 295);
/* 1913:     */             }
/* 1914:     */             break;
/* 1915:     */           case 295: 
/* 1916:1581 */             if (this.curChar == '.') {
/* 1917:1582 */               jjCheckNAdd(264);
/* 1918:     */             }
/* 1919:     */             break;
/* 1920:     */           case 296: 
/* 1921:1585 */             if ((0x0 & l) != 0L) {
/* 1922:1586 */               jjCheckNAddTwoStates(296, 269);
/* 1923:     */             }
/* 1924:     */             break;
/* 1925:     */           case 297: 
/* 1926:1589 */             if ((0x0 & l) != 0L) {
/* 1927:1590 */               jjCheckNAddTwoStates(297, 298);
/* 1928:     */             }
/* 1929:     */             break;
/* 1930:     */           case 298: 
/* 1931:1593 */             if (this.curChar == '.') {
/* 1932:1594 */               jjCheckNAdd(267);
/* 1933:     */             }
/* 1934:     */             break;
/* 1935:     */           case 299: 
/* 1936:1597 */             if ((0x0 & l) != 0L) {
/* 1937:1598 */               jjCheckNAddTwoStates(299, 272);
/* 1938:     */             }
/* 1939:     */             break;
/* 1940:     */           case 300: 
/* 1941:1601 */             if ((0x0 & l) != 0L) {
/* 1942:1602 */               jjCheckNAddTwoStates(300, 301);
/* 1943:     */             }
/* 1944:     */             break;
/* 1945:     */           case 301: 
/* 1946:1605 */             if (this.curChar == '.') {
/* 1947:1606 */               jjCheckNAdd(270);
/* 1948:     */             }
/* 1949:     */             break;
/* 1950:     */           case 302: 
/* 1951:1609 */             if ((0x0 & l) != 0L) {
/* 1952:1610 */               jjCheckNAddTwoStates(302, 275);
/* 1953:     */             }
/* 1954:     */             break;
/* 1955:     */           case 303: 
/* 1956:1613 */             if ((0x0 & l) != 0L) {
/* 1957:1614 */               jjCheckNAddTwoStates(303, 304);
/* 1958:     */             }
/* 1959:     */             break;
/* 1960:     */           case 304: 
/* 1961:1617 */             if (this.curChar == '.') {
/* 1962:1618 */               jjCheckNAdd(273);
/* 1963:     */             }
/* 1964:     */             break;
/* 1965:     */           case 305: 
/* 1966:1621 */             if ((0x0 & l) != 0L) {
/* 1967:1622 */               jjCheckNAddTwoStates(305, 278);
/* 1968:     */             }
/* 1969:     */             break;
/* 1970:     */           case 306: 
/* 1971:1625 */             if ((0x0 & l) != 0L) {
/* 1972:1626 */               jjCheckNAddTwoStates(306, 307);
/* 1973:     */             }
/* 1974:     */             break;
/* 1975:     */           case 307: 
/* 1976:1629 */             if (this.curChar == '.') {
/* 1977:1630 */               jjCheckNAdd(276);
/* 1978:     */             }
/* 1979:     */             break;
/* 1980:     */           case 308: 
/* 1981:1633 */             if ((0x0 & l) != 0L) {
/* 1982:1634 */               jjCheckNAddTwoStates(308, 280);
/* 1983:     */             }
/* 1984:     */             break;
/* 1985:     */           case 309: 
/* 1986:1637 */             if ((0x0 & l) != 0L) {
/* 1987:1638 */               jjCheckNAddTwoStates(309, 310);
/* 1988:     */             }
/* 1989:     */             break;
/* 1990:     */           case 310: 
/* 1991:1641 */             if (this.curChar == '.') {
/* 1992:1642 */               jjCheckNAdd(279);
/* 1993:     */             }
/* 1994:     */             break;
/* 1995:     */           case 311: 
/* 1996:1645 */             if ((0x0 & l) != 0L)
/* 1997:     */             {
/* 1998:1647 */               if (kind > 41) {
/* 1999:1648 */                 kind = 41;
/* 2000:     */               }
/* 2001:1649 */               jjCheckNAdd(311);
/* 2002:     */             }
/* 2003:1650 */             break;
/* 2004:     */           case 312: 
/* 2005:1652 */             if ((0x0 & l) != 0L) {
/* 2006:1653 */               jjCheckNAddTwoStates(312, 313);
/* 2007:     */             }
/* 2008:     */             break;
/* 2009:     */           case 313: 
/* 2010:1656 */             if (this.curChar == '.') {
/* 2011:1657 */               jjCheckNAdd(281);
/* 2012:     */             }
/* 2013:     */             break;
/* 2014:     */           case 314: 
/* 2015:1660 */             if ((0x0 & l) != 0L)
/* 2016:     */             {
/* 2017:1662 */               if (kind > 45) {
/* 2018:1663 */                 kind = 45;
/* 2019:     */               }
/* 2020:1664 */               jjCheckNAdd(314);
/* 2021:     */             }
/* 2022:1665 */             break;
/* 2023:     */           case 315: 
/* 2024:1667 */             if ((0x0 & l) != 0L) {
/* 2025:1668 */               jjCheckNAddTwoStates(315, 316);
/* 2026:     */             }
/* 2027:     */             break;
/* 2028:     */           case 316: 
/* 2029:1671 */             if (this.curChar == '.') {
/* 2030:1672 */               jjCheckNAdd(282);
/* 2031:     */             }
/* 2032:     */             break;
/* 2033:     */           }
/* 2034:1676 */         } while (i != startsAt);
/* 2035:     */       }
/* 2036:1678 */       else if (this.curChar < '')
/* 2037:     */       {
/* 2038:1680 */         long l = 1L << (this.curChar & 0x3F);
/* 2039:     */         do
/* 2040:     */         {
/* 2041:1683 */           switch (this.jjstateSet[(--i)])
/* 2042:     */           {
/* 2043:     */           case 318: 
/* 2044:1686 */             if ((0x7FFFFFE & l) != 0L)
/* 2045:     */             {
/* 2046:1688 */               if (kind > 3) {
/* 2047:1689 */                 kind = 3;
/* 2048:     */               }
/* 2049:1690 */               jjCheckNAddTwoStates(2, 3);
/* 2050:     */             }
/* 2051:1692 */             else if (this.curChar == '\\')
/* 2052:     */             {
/* 2053:1693 */               jjCheckNAddTwoStates(4, 5);
/* 2054:     */             }
/* 2055:     */             break;
/* 2056:     */           case 1: 
/* 2057:1696 */             if ((0x7FFFFFE & l) != 0L)
/* 2058:     */             {
/* 2059:1698 */               if (kind > 3) {
/* 2060:1699 */                 kind = 3;
/* 2061:     */               }
/* 2062:1700 */               jjCheckNAddTwoStates(2, 3);
/* 2063:     */             }
/* 2064:1702 */             else if (this.curChar == '\\')
/* 2065:     */             {
/* 2066:1703 */               jjCheckNAddTwoStates(4, 120);
/* 2067:     */             }
/* 2068:1704 */             else if (this.curChar == '@')
/* 2069:     */             {
/* 2070:1705 */               jjAddStates(240, 241);
/* 2071:     */             }
/* 2072:1706 */             if ((0x200000 & l) != 0L) {
/* 2073:1707 */               jjAddStates(242, 243);
/* 2074:     */             }
/* 2075:     */             break;
/* 2076:     */           case 89: 
/* 2077:1710 */             if ((0x7FFFFFE & l) != 0L)
/* 2078:     */             {
/* 2079:1712 */               if (kind > 31) {
/* 2080:1713 */                 kind = 31;
/* 2081:     */               }
/* 2082:1714 */               jjCheckNAddTwoStates(90, 91);
/* 2083:     */             }
/* 2084:1716 */             else if (this.curChar == '\\')
/* 2085:     */             {
/* 2086:1717 */               jjCheckNAddTwoStates(92, 107);
/* 2087:     */             }
/* 2088:     */             break;
/* 2089:     */           case 319: 
/* 2090:1720 */             if ((0x7FFFFFE & l) != 0L)
/* 2091:     */             {
/* 2092:1722 */               if (kind > 31) {
/* 2093:1723 */                 kind = 31;
/* 2094:     */               }
/* 2095:1724 */               jjCheckNAddTwoStates(90, 91);
/* 2096:     */             }
/* 2097:1726 */             else if (this.curChar == '\\')
/* 2098:     */             {
/* 2099:1727 */               jjCheckNAddTwoStates(92, 93);
/* 2100:     */             }
/* 2101:     */             break;
/* 2102:     */           case 2: 
/* 2103:1730 */             if ((0x7FFFFFE & l) != 0L)
/* 2104:     */             {
/* 2105:1732 */               if (kind > 3) {
/* 2106:1733 */                 kind = 3;
/* 2107:     */               }
/* 2108:1734 */               jjCheckNAddTwoStates(2, 3);
/* 2109:     */             }
/* 2110:1735 */             break;
/* 2111:     */           case 3: 
/* 2112:1737 */             if (this.curChar == '\\') {
/* 2113:1738 */               jjCheckNAddTwoStates(4, 5);
/* 2114:     */             }
/* 2115:     */             break;
/* 2116:     */           case 4: 
/* 2117:1741 */             if ((0xFFFFFFFF & l) != 0L)
/* 2118:     */             {
/* 2119:1743 */               if (kind > 3) {
/* 2120:1744 */                 kind = 3;
/* 2121:     */               }
/* 2122:1745 */               jjCheckNAddTwoStates(2, 3);
/* 2123:     */             }
/* 2124:1746 */             break;
/* 2125:     */           case 5: 
/* 2126:1748 */             if ((0x7E & l) != 0L)
/* 2127:     */             {
/* 2128:1750 */               if (kind > 3) {
/* 2129:1751 */                 kind = 3;
/* 2130:     */               }
/* 2131:1752 */               jjCheckNAddStates(59, 66);
/* 2132:     */             }
/* 2133:1753 */             break;
/* 2134:     */           case 6: 
/* 2135:1755 */             if ((0x7E & l) != 0L)
/* 2136:     */             {
/* 2137:1757 */               if (kind > 3) {
/* 2138:1758 */                 kind = 3;
/* 2139:     */               }
/* 2140:1759 */               jjCheckNAddStates(67, 69);
/* 2141:     */             }
/* 2142:1760 */             break;
/* 2143:     */           case 8: 
/* 2144:     */           case 10: 
/* 2145:     */           case 13: 
/* 2146:     */           case 17: 
/* 2147:1765 */             if ((0x7E & l) != 0L) {
/* 2148:1766 */               jjCheckNAdd(6);
/* 2149:     */             }
/* 2150:     */             break;
/* 2151:     */           case 9: 
/* 2152:1769 */             if ((0x7E & l) != 0L) {
/* 2153:1770 */               this.jjstateSet[(this.jjnewStateCnt++)] = 10;
/* 2154:     */             }
/* 2155:     */             break;
/* 2156:     */           case 11: 
/* 2157:1773 */             if ((0x7E & l) != 0L) {
/* 2158:1774 */               this.jjstateSet[(this.jjnewStateCnt++)] = 12;
/* 2159:     */             }
/* 2160:     */             break;
/* 2161:     */           case 12: 
/* 2162:1777 */             if ((0x7E & l) != 0L) {
/* 2163:1778 */               this.jjstateSet[(this.jjnewStateCnt++)] = 13;
/* 2164:     */             }
/* 2165:     */             break;
/* 2166:     */           case 14: 
/* 2167:1781 */             if ((0x7E & l) != 0L) {
/* 2168:1782 */               this.jjstateSet[(this.jjnewStateCnt++)] = 15;
/* 2169:     */             }
/* 2170:     */             break;
/* 2171:     */           case 15: 
/* 2172:1785 */             if ((0x7E & l) != 0L) {
/* 2173:1786 */               this.jjstateSet[(this.jjnewStateCnt++)] = 16;
/* 2174:     */             }
/* 2175:     */             break;
/* 2176:     */           case 16: 
/* 2177:1789 */             if ((0x7E & l) != 0L) {
/* 2178:1790 */               this.jjstateSet[(this.jjnewStateCnt++)] = 17;
/* 2179:     */             }
/* 2180:     */             break;
/* 2181:     */           case 19: 
/* 2182:1793 */             if ((0x7FFFFFE & l) != 0L)
/* 2183:     */             {
/* 2184:1795 */               if (kind > 9) {
/* 2185:1796 */                 kind = 9;
/* 2186:     */               }
/* 2187:1797 */               jjCheckNAddTwoStates(19, 20);
/* 2188:     */             }
/* 2189:1798 */             break;
/* 2190:     */           case 20: 
/* 2191:1800 */             if (this.curChar == '\\') {
/* 2192:1801 */               jjAddStates(244, 245);
/* 2193:     */             }
/* 2194:     */             break;
/* 2195:     */           case 21: 
/* 2196:1804 */             if ((0xFFFFFFFF & l) != 0L)
/* 2197:     */             {
/* 2198:1806 */               if (kind > 9) {
/* 2199:1807 */                 kind = 9;
/* 2200:     */               }
/* 2201:1808 */               jjCheckNAddTwoStates(19, 20);
/* 2202:     */             }
/* 2203:1809 */             break;
/* 2204:     */           case 22: 
/* 2205:1811 */             if ((0x7E & l) != 0L)
/* 2206:     */             {
/* 2207:1813 */               if (kind > 9) {
/* 2208:1814 */                 kind = 9;
/* 2209:     */               }
/* 2210:1815 */               jjCheckNAddStates(70, 77);
/* 2211:     */             }
/* 2212:1816 */             break;
/* 2213:     */           case 23: 
/* 2214:1818 */             if ((0x7E & l) != 0L)
/* 2215:     */             {
/* 2216:1820 */               if (kind > 9) {
/* 2217:1821 */                 kind = 9;
/* 2218:     */               }
/* 2219:1822 */               jjCheckNAddStates(78, 80);
/* 2220:     */             }
/* 2221:1823 */             break;
/* 2222:     */           case 25: 
/* 2223:     */           case 27: 
/* 2224:     */           case 30: 
/* 2225:     */           case 34: 
/* 2226:1828 */             if ((0x7E & l) != 0L) {
/* 2227:1829 */               jjCheckNAdd(23);
/* 2228:     */             }
/* 2229:     */             break;
/* 2230:     */           case 26: 
/* 2231:1832 */             if ((0x7E & l) != 0L) {
/* 2232:1833 */               this.jjstateSet[(this.jjnewStateCnt++)] = 27;
/* 2233:     */             }
/* 2234:     */             break;
/* 2235:     */           case 28: 
/* 2236:1836 */             if ((0x7E & l) != 0L) {
/* 2237:1837 */               this.jjstateSet[(this.jjnewStateCnt++)] = 29;
/* 2238:     */             }
/* 2239:     */             break;
/* 2240:     */           case 29: 
/* 2241:1840 */             if ((0x7E & l) != 0L) {
/* 2242:1841 */               this.jjstateSet[(this.jjnewStateCnt++)] = 30;
/* 2243:     */             }
/* 2244:     */             break;
/* 2245:     */           case 31: 
/* 2246:1844 */             if ((0x7E & l) != 0L) {
/* 2247:1845 */               this.jjstateSet[(this.jjnewStateCnt++)] = 32;
/* 2248:     */             }
/* 2249:     */             break;
/* 2250:     */           case 32: 
/* 2251:1848 */             if ((0x7E & l) != 0L) {
/* 2252:1849 */               this.jjstateSet[(this.jjnewStateCnt++)] = 33;
/* 2253:     */             }
/* 2254:     */             break;
/* 2255:     */           case 33: 
/* 2256:1852 */             if ((0x7E & l) != 0L) {
/* 2257:1853 */               this.jjstateSet[(this.jjnewStateCnt++)] = 34;
/* 2258:     */             }
/* 2259:     */             break;
/* 2260:     */           case 36: 
/* 2261:1856 */             if ((0xEFFFFFFF & l) != 0L) {
/* 2262:1857 */               jjCheckNAddStates(56, 58);
/* 2263:     */             }
/* 2264:     */             break;
/* 2265:     */           case 38: 
/* 2266:1860 */             if (this.curChar == '\\') {
/* 2267:1861 */               jjAddStates(246, 249);
/* 2268:     */             }
/* 2269:     */             break;
/* 2270:     */           case 42: 
/* 2271:1864 */             if ((0xFFFFFFFF & l) != 0L) {
/* 2272:1865 */               jjCheckNAddStates(56, 58);
/* 2273:     */             }
/* 2274:     */             break;
/* 2275:     */           case 43: 
/* 2276:1868 */             if ((0x7E & l) != 0L) {
/* 2277:1869 */               jjCheckNAddStates(81, 89);
/* 2278:     */             }
/* 2279:     */             break;
/* 2280:     */           case 44: 
/* 2281:1872 */             if ((0x7E & l) != 0L) {
/* 2282:1873 */               jjCheckNAddStates(90, 93);
/* 2283:     */             }
/* 2284:     */             break;
/* 2285:     */           case 46: 
/* 2286:     */           case 48: 
/* 2287:     */           case 51: 
/* 2288:     */           case 55: 
/* 2289:1879 */             if ((0x7E & l) != 0L) {
/* 2290:1880 */               jjCheckNAdd(44);
/* 2291:     */             }
/* 2292:     */             break;
/* 2293:     */           case 47: 
/* 2294:1883 */             if ((0x7E & l) != 0L) {
/* 2295:1884 */               this.jjstateSet[(this.jjnewStateCnt++)] = 48;
/* 2296:     */             }
/* 2297:     */             break;
/* 2298:     */           case 49: 
/* 2299:1887 */             if ((0x7E & l) != 0L) {
/* 2300:1888 */               this.jjstateSet[(this.jjnewStateCnt++)] = 50;
/* 2301:     */             }
/* 2302:     */             break;
/* 2303:     */           case 50: 
/* 2304:1891 */             if ((0x7E & l) != 0L) {
/* 2305:1892 */               this.jjstateSet[(this.jjnewStateCnt++)] = 51;
/* 2306:     */             }
/* 2307:     */             break;
/* 2308:     */           case 52: 
/* 2309:1895 */             if ((0x7E & l) != 0L) {
/* 2310:1896 */               this.jjstateSet[(this.jjnewStateCnt++)] = 53;
/* 2311:     */             }
/* 2312:     */             break;
/* 2313:     */           case 53: 
/* 2314:1899 */             if ((0x7E & l) != 0L) {
/* 2315:1900 */               this.jjstateSet[(this.jjnewStateCnt++)] = 54;
/* 2316:     */             }
/* 2317:     */             break;
/* 2318:     */           case 54: 
/* 2319:1903 */             if ((0x7E & l) != 0L) {
/* 2320:1904 */               this.jjstateSet[(this.jjnewStateCnt++)] = 55;
/* 2321:     */             }
/* 2322:     */             break;
/* 2323:     */           case 57: 
/* 2324:1907 */             if ((0xEFFFFFFF & l) != 0L) {
/* 2325:1908 */               jjCheckNAddStates(53, 55);
/* 2326:     */             }
/* 2327:     */             break;
/* 2328:     */           case 59: 
/* 2329:1911 */             if (this.curChar == '\\') {
/* 2330:1912 */               jjAddStates(250, 253);
/* 2331:     */             }
/* 2332:     */             break;
/* 2333:     */           case 63: 
/* 2334:1915 */             if ((0xFFFFFFFF & l) != 0L) {
/* 2335:1916 */               jjCheckNAddStates(53, 55);
/* 2336:     */             }
/* 2337:     */             break;
/* 2338:     */           case 64: 
/* 2339:1919 */             if ((0x7E & l) != 0L) {
/* 2340:1920 */               jjCheckNAddStates(94, 102);
/* 2341:     */             }
/* 2342:     */             break;
/* 2343:     */           case 65: 
/* 2344:1923 */             if ((0x7E & l) != 0L) {
/* 2345:1924 */               jjCheckNAddStates(103, 106);
/* 2346:     */             }
/* 2347:     */             break;
/* 2348:     */           case 67: 
/* 2349:     */           case 69: 
/* 2350:     */           case 72: 
/* 2351:     */           case 76: 
/* 2352:1930 */             if ((0x7E & l) != 0L) {
/* 2353:1931 */               jjCheckNAdd(65);
/* 2354:     */             }
/* 2355:     */             break;
/* 2356:     */           case 68: 
/* 2357:1934 */             if ((0x7E & l) != 0L) {
/* 2358:1935 */               this.jjstateSet[(this.jjnewStateCnt++)] = 69;
/* 2359:     */             }
/* 2360:     */             break;
/* 2361:     */           case 70: 
/* 2362:1938 */             if ((0x7E & l) != 0L) {
/* 2363:1939 */               this.jjstateSet[(this.jjnewStateCnt++)] = 71;
/* 2364:     */             }
/* 2365:     */             break;
/* 2366:     */           case 71: 
/* 2367:1942 */             if ((0x7E & l) != 0L) {
/* 2368:1943 */               this.jjstateSet[(this.jjnewStateCnt++)] = 72;
/* 2369:     */             }
/* 2370:     */             break;
/* 2371:     */           case 73: 
/* 2372:1946 */             if ((0x7E & l) != 0L) {
/* 2373:1947 */               this.jjstateSet[(this.jjnewStateCnt++)] = 74;
/* 2374:     */             }
/* 2375:     */             break;
/* 2376:     */           case 74: 
/* 2377:1950 */             if ((0x7E & l) != 0L) {
/* 2378:1951 */               this.jjstateSet[(this.jjnewStateCnt++)] = 75;
/* 2379:     */             }
/* 2380:     */             break;
/* 2381:     */           case 75: 
/* 2382:1954 */             if ((0x7E & l) != 0L) {
/* 2383:1955 */               this.jjstateSet[(this.jjnewStateCnt++)] = 76;
/* 2384:     */             }
/* 2385:     */             break;
/* 2386:     */           case 79: 
/* 2387:1958 */             if (((0x100000 & l) != 0L) && (kind > 30)) {
/* 2388:1959 */               kind = 30;
/* 2389:     */             }
/* 2390:     */             break;
/* 2391:     */           case 80: 
/* 2392:1962 */             if ((0x4000 & l) != 0L) {
/* 2393:1963 */               this.jjstateSet[(this.jjnewStateCnt++)] = 79;
/* 2394:     */             }
/* 2395:     */             break;
/* 2396:     */           case 81: 
/* 2397:1966 */             if ((0x2 & l) != 0L) {
/* 2398:1967 */               this.jjstateSet[(this.jjnewStateCnt++)] = 80;
/* 2399:     */             }
/* 2400:     */             break;
/* 2401:     */           case 82: 
/* 2402:1970 */             if ((0x100000 & l) != 0L) {
/* 2403:1971 */               this.jjstateSet[(this.jjnewStateCnt++)] = 81;
/* 2404:     */             }
/* 2405:     */             break;
/* 2406:     */           case 83: 
/* 2407:1974 */             if ((0x40000 & l) != 0L) {
/* 2408:1975 */               this.jjstateSet[(this.jjnewStateCnt++)] = 82;
/* 2409:     */             }
/* 2410:     */             break;
/* 2411:     */           case 84: 
/* 2412:1978 */             if ((0x8000 & l) != 0L) {
/* 2413:1979 */               this.jjstateSet[(this.jjnewStateCnt++)] = 83;
/* 2414:     */             }
/* 2415:     */             break;
/* 2416:     */           case 85: 
/* 2417:1982 */             if ((0x10000 & l) != 0L) {
/* 2418:1983 */               this.jjstateSet[(this.jjnewStateCnt++)] = 84;
/* 2419:     */             }
/* 2420:     */             break;
/* 2421:     */           case 86: 
/* 2422:1986 */             if ((0x2000 & l) != 0L) {
/* 2423:1987 */               this.jjstateSet[(this.jjnewStateCnt++)] = 85;
/* 2424:     */             }
/* 2425:     */             break;
/* 2426:     */           case 87: 
/* 2427:1990 */             if ((0x200 & l) != 0L) {
/* 2428:1991 */               this.jjstateSet[(this.jjnewStateCnt++)] = 86;
/* 2429:     */             }
/* 2430:     */             break;
/* 2431:     */           case 88: 
/* 2432:1994 */             if (this.curChar == '@') {
/* 2433:1995 */               jjAddStates(240, 241);
/* 2434:     */             }
/* 2435:     */             break;
/* 2436:     */           case 90: 
/* 2437:1998 */             if ((0x7FFFFFE & l) != 0L)
/* 2438:     */             {
/* 2439:2000 */               if (kind > 31) {
/* 2440:2001 */                 kind = 31;
/* 2441:     */               }
/* 2442:2002 */               jjCheckNAddTwoStates(90, 91);
/* 2443:     */             }
/* 2444:2003 */             break;
/* 2445:     */           case 91: 
/* 2446:2005 */             if (this.curChar == '\\') {
/* 2447:2006 */               jjCheckNAddTwoStates(92, 93);
/* 2448:     */             }
/* 2449:     */             break;
/* 2450:     */           case 92: 
/* 2451:2009 */             if ((0xFFFFFFFF & l) != 0L)
/* 2452:     */             {
/* 2453:2011 */               if (kind > 31) {
/* 2454:2012 */                 kind = 31;
/* 2455:     */               }
/* 2456:2013 */               jjCheckNAddTwoStates(90, 91);
/* 2457:     */             }
/* 2458:2014 */             break;
/* 2459:     */           case 93: 
/* 2460:2016 */             if ((0x7E & l) != 0L)
/* 2461:     */             {
/* 2462:2018 */               if (kind > 31) {
/* 2463:2019 */                 kind = 31;
/* 2464:     */               }
/* 2465:2020 */               jjCheckNAddStates(107, 114);
/* 2466:     */             }
/* 2467:2021 */             break;
/* 2468:     */           case 94: 
/* 2469:2023 */             if ((0x7E & l) != 0L)
/* 2470:     */             {
/* 2471:2025 */               if (kind > 31) {
/* 2472:2026 */                 kind = 31;
/* 2473:     */               }
/* 2474:2027 */               jjCheckNAddStates(115, 117);
/* 2475:     */             }
/* 2476:2028 */             break;
/* 2477:     */           case 96: 
/* 2478:     */           case 98: 
/* 2479:     */           case 101: 
/* 2480:     */           case 105: 
/* 2481:2033 */             if ((0x7E & l) != 0L) {
/* 2482:2034 */               jjCheckNAdd(94);
/* 2483:     */             }
/* 2484:     */             break;
/* 2485:     */           case 97: 
/* 2486:2037 */             if ((0x7E & l) != 0L) {
/* 2487:2038 */               this.jjstateSet[(this.jjnewStateCnt++)] = 98;
/* 2488:     */             }
/* 2489:     */             break;
/* 2490:     */           case 99: 
/* 2491:2041 */             if ((0x7E & l) != 0L) {
/* 2492:2042 */               this.jjstateSet[(this.jjnewStateCnt++)] = 100;
/* 2493:     */             }
/* 2494:     */             break;
/* 2495:     */           case 100: 
/* 2496:2045 */             if ((0x7E & l) != 0L) {
/* 2497:2046 */               this.jjstateSet[(this.jjnewStateCnt++)] = 101;
/* 2498:     */             }
/* 2499:     */             break;
/* 2500:     */           case 102: 
/* 2501:2049 */             if ((0x7E & l) != 0L) {
/* 2502:2050 */               this.jjstateSet[(this.jjnewStateCnt++)] = 103;
/* 2503:     */             }
/* 2504:     */             break;
/* 2505:     */           case 103: 
/* 2506:2053 */             if ((0x7E & l) != 0L) {
/* 2507:2054 */               this.jjstateSet[(this.jjnewStateCnt++)] = 104;
/* 2508:     */             }
/* 2509:     */             break;
/* 2510:     */           case 104: 
/* 2511:2057 */             if ((0x7E & l) != 0L) {
/* 2512:2058 */               this.jjstateSet[(this.jjnewStateCnt++)] = 105;
/* 2513:     */             }
/* 2514:     */             break;
/* 2515:     */           case 106: 
/* 2516:2061 */             if (this.curChar == '\\') {
/* 2517:2062 */               jjCheckNAddTwoStates(92, 107);
/* 2518:     */             }
/* 2519:     */             break;
/* 2520:     */           case 107: 
/* 2521:2065 */             if ((0x7E & l) != 0L)
/* 2522:     */             {
/* 2523:2067 */               if (kind > 31) {
/* 2524:2068 */                 kind = 31;
/* 2525:     */               }
/* 2526:2069 */               jjCheckNAddStates(118, 125);
/* 2527:     */             }
/* 2528:2070 */             break;
/* 2529:     */           case 108: 
/* 2530:2072 */             if ((0x7E & l) != 0L)
/* 2531:     */             {
/* 2532:2074 */               if (kind > 31) {
/* 2533:2075 */                 kind = 31;
/* 2534:     */               }
/* 2535:2076 */               jjCheckNAddStates(126, 128);
/* 2536:     */             }
/* 2537:2077 */             break;
/* 2538:     */           case 109: 
/* 2539:     */           case 111: 
/* 2540:     */           case 114: 
/* 2541:     */           case 118: 
/* 2542:2082 */             if ((0x7E & l) != 0L) {
/* 2543:2083 */               jjCheckNAdd(108);
/* 2544:     */             }
/* 2545:     */             break;
/* 2546:     */           case 110: 
/* 2547:2086 */             if ((0x7E & l) != 0L) {
/* 2548:2087 */               this.jjstateSet[(this.jjnewStateCnt++)] = 111;
/* 2549:     */             }
/* 2550:     */             break;
/* 2551:     */           case 112: 
/* 2552:2090 */             if ((0x7E & l) != 0L) {
/* 2553:2091 */               this.jjstateSet[(this.jjnewStateCnt++)] = 113;
/* 2554:     */             }
/* 2555:     */             break;
/* 2556:     */           case 113: 
/* 2557:2094 */             if ((0x7E & l) != 0L) {
/* 2558:2095 */               this.jjstateSet[(this.jjnewStateCnt++)] = 114;
/* 2559:     */             }
/* 2560:     */             break;
/* 2561:     */           case 115: 
/* 2562:2098 */             if ((0x7E & l) != 0L) {
/* 2563:2099 */               this.jjstateSet[(this.jjnewStateCnt++)] = 116;
/* 2564:     */             }
/* 2565:     */             break;
/* 2566:     */           case 116: 
/* 2567:2102 */             if ((0x7E & l) != 0L) {
/* 2568:2103 */               this.jjstateSet[(this.jjnewStateCnt++)] = 117;
/* 2569:     */             }
/* 2570:     */             break;
/* 2571:     */           case 117: 
/* 2572:2106 */             if ((0x7E & l) != 0L) {
/* 2573:2107 */               this.jjstateSet[(this.jjnewStateCnt++)] = 118;
/* 2574:     */             }
/* 2575:     */             break;
/* 2576:     */           case 119: 
/* 2577:2110 */             if (this.curChar == '\\') {
/* 2578:2111 */               jjCheckNAddTwoStates(4, 120);
/* 2579:     */             }
/* 2580:     */             break;
/* 2581:     */           case 120: 
/* 2582:2114 */             if ((0x7E & l) != 0L)
/* 2583:     */             {
/* 2584:2116 */               if (kind > 3) {
/* 2585:2117 */                 kind = 3;
/* 2586:     */               }
/* 2587:2118 */               jjCheckNAddStates(129, 136);
/* 2588:     */             }
/* 2589:2119 */             break;
/* 2590:     */           case 121: 
/* 2591:2121 */             if ((0x7E & l) != 0L)
/* 2592:     */             {
/* 2593:2123 */               if (kind > 3) {
/* 2594:2124 */                 kind = 3;
/* 2595:     */               }
/* 2596:2125 */               jjCheckNAddStates(137, 139);
/* 2597:     */             }
/* 2598:2126 */             break;
/* 2599:     */           case 122: 
/* 2600:     */           case 124: 
/* 2601:     */           case 127: 
/* 2602:     */           case 131: 
/* 2603:2131 */             if ((0x7E & l) != 0L) {
/* 2604:2132 */               jjCheckNAdd(121);
/* 2605:     */             }
/* 2606:     */             break;
/* 2607:     */           case 123: 
/* 2608:2135 */             if ((0x7E & l) != 0L) {
/* 2609:2136 */               this.jjstateSet[(this.jjnewStateCnt++)] = 124;
/* 2610:     */             }
/* 2611:     */             break;
/* 2612:     */           case 125: 
/* 2613:2139 */             if ((0x7E & l) != 0L) {
/* 2614:2140 */               this.jjstateSet[(this.jjnewStateCnt++)] = 126;
/* 2615:     */             }
/* 2616:     */             break;
/* 2617:     */           case 126: 
/* 2618:2143 */             if ((0x7E & l) != 0L) {
/* 2619:2144 */               this.jjstateSet[(this.jjnewStateCnt++)] = 127;
/* 2620:     */             }
/* 2621:     */             break;
/* 2622:     */           case 128: 
/* 2623:2147 */             if ((0x7E & l) != 0L) {
/* 2624:2148 */               this.jjstateSet[(this.jjnewStateCnt++)] = 129;
/* 2625:     */             }
/* 2626:     */             break;
/* 2627:     */           case 129: 
/* 2628:2151 */             if ((0x7E & l) != 0L) {
/* 2629:2152 */               this.jjstateSet[(this.jjnewStateCnt++)] = 130;
/* 2630:     */             }
/* 2631:     */             break;
/* 2632:     */           case 130: 
/* 2633:2155 */             if ((0x7E & l) != 0L) {
/* 2634:2156 */               this.jjstateSet[(this.jjnewStateCnt++)] = 131;
/* 2635:     */             }
/* 2636:     */             break;
/* 2637:     */           case 132: 
/* 2638:2159 */             if ((0x200000 & l) != 0L) {
/* 2639:2160 */               jjAddStates(242, 243);
/* 2640:     */             }
/* 2641:     */             break;
/* 2642:     */           case 134: 
/* 2643:2163 */             if ((0xEFFFFFFF & l) != 0L) {
/* 2644:2164 */               jjCheckNAddStates(146, 149);
/* 2645:     */             }
/* 2646:     */             break;
/* 2647:     */           case 137: 
/* 2648:2167 */             if (this.curChar == '\\') {
/* 2649:2168 */               jjAddStates(254, 255);
/* 2650:     */             }
/* 2651:     */             break;
/* 2652:     */           case 138: 
/* 2653:2171 */             if ((0xFFFFFFFF & l) != 0L) {
/* 2654:2172 */               jjCheckNAddStates(146, 149);
/* 2655:     */             }
/* 2656:     */             break;
/* 2657:     */           case 139: 
/* 2658:2175 */             if ((0x7E & l) != 0L) {
/* 2659:2176 */               jjCheckNAddStates(150, 158);
/* 2660:     */             }
/* 2661:     */             break;
/* 2662:     */           case 140: 
/* 2663:2179 */             if ((0x7E & l) != 0L) {
/* 2664:2180 */               jjCheckNAddStates(159, 162);
/* 2665:     */             }
/* 2666:     */             break;
/* 2667:     */           case 142: 
/* 2668:     */           case 144: 
/* 2669:     */           case 147: 
/* 2670:     */           case 151: 
/* 2671:2186 */             if ((0x7E & l) != 0L) {
/* 2672:2187 */               jjCheckNAdd(140);
/* 2673:     */             }
/* 2674:     */             break;
/* 2675:     */           case 143: 
/* 2676:2190 */             if ((0x7E & l) != 0L) {
/* 2677:2191 */               this.jjstateSet[(this.jjnewStateCnt++)] = 144;
/* 2678:     */             }
/* 2679:     */             break;
/* 2680:     */           case 145: 
/* 2681:2194 */             if ((0x7E & l) != 0L) {
/* 2682:2195 */               this.jjstateSet[(this.jjnewStateCnt++)] = 146;
/* 2683:     */             }
/* 2684:     */             break;
/* 2685:     */           case 146: 
/* 2686:2198 */             if ((0x7E & l) != 0L) {
/* 2687:2199 */               this.jjstateSet[(this.jjnewStateCnt++)] = 147;
/* 2688:     */             }
/* 2689:     */             break;
/* 2690:     */           case 148: 
/* 2691:2202 */             if ((0x7E & l) != 0L) {
/* 2692:2203 */               this.jjstateSet[(this.jjnewStateCnt++)] = 149;
/* 2693:     */             }
/* 2694:     */             break;
/* 2695:     */           case 149: 
/* 2696:2206 */             if ((0x7E & l) != 0L) {
/* 2697:2207 */               this.jjstateSet[(this.jjnewStateCnt++)] = 150;
/* 2698:     */             }
/* 2699:     */             break;
/* 2700:     */           case 150: 
/* 2701:2210 */             if ((0x7E & l) != 0L) {
/* 2702:2211 */               this.jjstateSet[(this.jjnewStateCnt++)] = 151;
/* 2703:     */             }
/* 2704:     */             break;
/* 2705:     */           case 153: 
/* 2706:2214 */             if ((0xEFFFFFFF & l) != 0L) {
/* 2707:2215 */               jjCheckNAddStates(163, 165);
/* 2708:     */             }
/* 2709:     */             break;
/* 2710:     */           case 155: 
/* 2711:2218 */             if (this.curChar == '\\') {
/* 2712:2219 */               jjAddStates(256, 259);
/* 2713:     */             }
/* 2714:     */             break;
/* 2715:     */           case 159: 
/* 2716:2222 */             if ((0xFFFFFFFF & l) != 0L) {
/* 2717:2223 */               jjCheckNAddStates(163, 165);
/* 2718:     */             }
/* 2719:     */             break;
/* 2720:     */           case 160: 
/* 2721:2226 */             if ((0x7E & l) != 0L) {
/* 2722:2227 */               jjCheckNAddStates(166, 174);
/* 2723:     */             }
/* 2724:     */             break;
/* 2725:     */           case 161: 
/* 2726:2230 */             if ((0x7E & l) != 0L) {
/* 2727:2231 */               jjCheckNAddStates(175, 178);
/* 2728:     */             }
/* 2729:     */             break;
/* 2730:     */           case 163: 
/* 2731:     */           case 165: 
/* 2732:     */           case 168: 
/* 2733:     */           case 172: 
/* 2734:2237 */             if ((0x7E & l) != 0L) {
/* 2735:2238 */               jjCheckNAdd(161);
/* 2736:     */             }
/* 2737:     */             break;
/* 2738:     */           case 164: 
/* 2739:2241 */             if ((0x7E & l) != 0L) {
/* 2740:2242 */               this.jjstateSet[(this.jjnewStateCnt++)] = 165;
/* 2741:     */             }
/* 2742:     */             break;
/* 2743:     */           case 166: 
/* 2744:2245 */             if ((0x7E & l) != 0L) {
/* 2745:2246 */               this.jjstateSet[(this.jjnewStateCnt++)] = 167;
/* 2746:     */             }
/* 2747:     */             break;
/* 2748:     */           case 167: 
/* 2749:2249 */             if ((0x7E & l) != 0L) {
/* 2750:2250 */               this.jjstateSet[(this.jjnewStateCnt++)] = 168;
/* 2751:     */             }
/* 2752:     */             break;
/* 2753:     */           case 169: 
/* 2754:2253 */             if ((0x7E & l) != 0L) {
/* 2755:2254 */               this.jjstateSet[(this.jjnewStateCnt++)] = 170;
/* 2756:     */             }
/* 2757:     */             break;
/* 2758:     */           case 170: 
/* 2759:2257 */             if ((0x7E & l) != 0L) {
/* 2760:2258 */               this.jjstateSet[(this.jjnewStateCnt++)] = 171;
/* 2761:     */             }
/* 2762:     */             break;
/* 2763:     */           case 171: 
/* 2764:2261 */             if ((0x7E & l) != 0L) {
/* 2765:2262 */               this.jjstateSet[(this.jjnewStateCnt++)] = 172;
/* 2766:     */             }
/* 2767:     */             break;
/* 2768:     */           case 174: 
/* 2769:2265 */             if ((0xEFFFFFFF & l) != 0L) {
/* 2770:2266 */               jjCheckNAddStates(179, 181);
/* 2771:     */             }
/* 2772:     */             break;
/* 2773:     */           case 176: 
/* 2774:2269 */             if (this.curChar == '\\') {
/* 2775:2270 */               jjAddStates(260, 263);
/* 2776:     */             }
/* 2777:     */             break;
/* 2778:     */           case 180: 
/* 2779:2273 */             if ((0xFFFFFFFF & l) != 0L) {
/* 2780:2274 */               jjCheckNAddStates(179, 181);
/* 2781:     */             }
/* 2782:     */             break;
/* 2783:     */           case 181: 
/* 2784:2277 */             if ((0x7E & l) != 0L) {
/* 2785:2278 */               jjCheckNAddStates(182, 190);
/* 2786:     */             }
/* 2787:     */             break;
/* 2788:     */           case 182: 
/* 2789:2281 */             if ((0x7E & l) != 0L) {
/* 2790:2282 */               jjCheckNAddStates(191, 194);
/* 2791:     */             }
/* 2792:     */             break;
/* 2793:     */           case 184: 
/* 2794:     */           case 186: 
/* 2795:     */           case 189: 
/* 2796:     */           case 193: 
/* 2797:2288 */             if ((0x7E & l) != 0L) {
/* 2798:2289 */               jjCheckNAdd(182);
/* 2799:     */             }
/* 2800:     */             break;
/* 2801:     */           case 185: 
/* 2802:2292 */             if ((0x7E & l) != 0L) {
/* 2803:2293 */               this.jjstateSet[(this.jjnewStateCnt++)] = 186;
/* 2804:     */             }
/* 2805:     */             break;
/* 2806:     */           case 187: 
/* 2807:2296 */             if ((0x7E & l) != 0L) {
/* 2808:2297 */               this.jjstateSet[(this.jjnewStateCnt++)] = 188;
/* 2809:     */             }
/* 2810:     */             break;
/* 2811:     */           case 188: 
/* 2812:2300 */             if ((0x7E & l) != 0L) {
/* 2813:2301 */               this.jjstateSet[(this.jjnewStateCnt++)] = 189;
/* 2814:     */             }
/* 2815:     */             break;
/* 2816:     */           case 190: 
/* 2817:2304 */             if ((0x7E & l) != 0L) {
/* 2818:2305 */               this.jjstateSet[(this.jjnewStateCnt++)] = 191;
/* 2819:     */             }
/* 2820:     */             break;
/* 2821:     */           case 191: 
/* 2822:2308 */             if ((0x7E & l) != 0L) {
/* 2823:2309 */               this.jjstateSet[(this.jjnewStateCnt++)] = 192;
/* 2824:     */             }
/* 2825:     */             break;
/* 2826:     */           case 192: 
/* 2827:2312 */             if ((0x7E & l) != 0L) {
/* 2828:2313 */               this.jjstateSet[(this.jjnewStateCnt++)] = 193;
/* 2829:     */             }
/* 2830:     */             break;
/* 2831:     */           case 195: 
/* 2832:2316 */             if ((0x1000 & l) != 0L) {
/* 2833:2317 */               this.jjstateSet[(this.jjnewStateCnt++)] = 133;
/* 2834:     */             }
/* 2835:     */             break;
/* 2836:     */           case 196: 
/* 2837:2320 */             if ((0x40000 & l) != 0L) {
/* 2838:2321 */               this.jjstateSet[(this.jjnewStateCnt++)] = 195;
/* 2839:     */             }
/* 2840:     */             break;
/* 2841:     */           case 199: 
/* 2842:2324 */             if ((0x7E & l) != 0L)
/* 2843:     */             {
/* 2844:2326 */               if (kind > 46) {
/* 2845:2327 */                 kind = 46;
/* 2846:     */               }
/* 2847:2328 */               jjCheckNAddStates(205, 213);
/* 2848:     */             }
/* 2849:2329 */             break;
/* 2850:     */           case 200: 
/* 2851:2331 */             if ((0x7E & l) != 0L) {
/* 2852:2332 */               jjCheckNAdd(201);
/* 2853:     */             }
/* 2854:     */             break;
/* 2855:     */           case 202: 
/* 2856:2335 */             if ((0x7E & l) != 0L)
/* 2857:     */             {
/* 2858:2337 */               if (kind > 46) {
/* 2859:2338 */                 kind = 46;
/* 2860:     */               }
/* 2861:2339 */               jjCheckNAddStates(214, 218);
/* 2862:     */             }
/* 2863:2340 */             break;
/* 2864:     */           case 203: 
/* 2865:2342 */             if (((0x7E & l) != 0L) && (kind > 46)) {
/* 2866:2343 */               kind = 46;
/* 2867:     */             }
/* 2868:     */             break;
/* 2869:     */           case 204: 
/* 2870:     */           case 206: 
/* 2871:     */           case 209: 
/* 2872:     */           case 213: 
/* 2873:2349 */             if ((0x7E & l) != 0L) {
/* 2874:2350 */               jjCheckNAdd(203);
/* 2875:     */             }
/* 2876:     */             break;
/* 2877:     */           case 205: 
/* 2878:2353 */             if ((0x7E & l) != 0L) {
/* 2879:2354 */               this.jjstateSet[(this.jjnewStateCnt++)] = 206;
/* 2880:     */             }
/* 2881:     */             break;
/* 2882:     */           case 207: 
/* 2883:2357 */             if ((0x7E & l) != 0L) {
/* 2884:2358 */               this.jjstateSet[(this.jjnewStateCnt++)] = 208;
/* 2885:     */             }
/* 2886:     */             break;
/* 2887:     */           case 208: 
/* 2888:2361 */             if ((0x7E & l) != 0L) {
/* 2889:2362 */               this.jjstateSet[(this.jjnewStateCnt++)] = 209;
/* 2890:     */             }
/* 2891:     */             break;
/* 2892:     */           case 210: 
/* 2893:2365 */             if ((0x7E & l) != 0L) {
/* 2894:2366 */               this.jjstateSet[(this.jjnewStateCnt++)] = 211;
/* 2895:     */             }
/* 2896:     */             break;
/* 2897:     */           case 211: 
/* 2898:2369 */             if ((0x7E & l) != 0L) {
/* 2899:2370 */               this.jjstateSet[(this.jjnewStateCnt++)] = 212;
/* 2900:     */             }
/* 2901:     */             break;
/* 2902:     */           case 212: 
/* 2903:2373 */             if ((0x7E & l) != 0L) {
/* 2904:2374 */               this.jjstateSet[(this.jjnewStateCnt++)] = 213;
/* 2905:     */             }
/* 2906:     */             break;
/* 2907:     */           case 214: 
/* 2908:     */           case 216: 
/* 2909:     */           case 219: 
/* 2910:     */           case 223: 
/* 2911:2380 */             if ((0x7E & l) != 0L) {
/* 2912:2381 */               jjCheckNAdd(200);
/* 2913:     */             }
/* 2914:     */             break;
/* 2915:     */           case 215: 
/* 2916:2384 */             if ((0x7E & l) != 0L) {
/* 2917:2385 */               this.jjstateSet[(this.jjnewStateCnt++)] = 216;
/* 2918:     */             }
/* 2919:     */             break;
/* 2920:     */           case 217: 
/* 2921:2388 */             if ((0x7E & l) != 0L) {
/* 2922:2389 */               this.jjstateSet[(this.jjnewStateCnt++)] = 218;
/* 2923:     */             }
/* 2924:     */             break;
/* 2925:     */           case 218: 
/* 2926:2392 */             if ((0x7E & l) != 0L) {
/* 2927:2393 */               this.jjstateSet[(this.jjnewStateCnt++)] = 219;
/* 2928:     */             }
/* 2929:     */             break;
/* 2930:     */           case 220: 
/* 2931:2396 */             if ((0x7E & l) != 0L) {
/* 2932:2397 */               this.jjstateSet[(this.jjnewStateCnt++)] = 221;
/* 2933:     */             }
/* 2934:     */             break;
/* 2935:     */           case 221: 
/* 2936:2400 */             if ((0x7E & l) != 0L) {
/* 2937:2401 */               this.jjstateSet[(this.jjnewStateCnt++)] = 222;
/* 2938:     */             }
/* 2939:     */             break;
/* 2940:     */           case 222: 
/* 2941:2404 */             if ((0x7E & l) != 0L) {
/* 2942:2405 */               this.jjstateSet[(this.jjnewStateCnt++)] = 223;
/* 2943:     */             }
/* 2944:     */             break;
/* 2945:     */           case 224: 
/* 2946:2408 */             if ((0x7E & l) != 0L)
/* 2947:     */             {
/* 2948:2410 */               if (kind > 46) {
/* 2949:2411 */                 kind = 46;
/* 2950:     */               }
/* 2951:2412 */               jjCheckNAddStates(219, 221);
/* 2952:     */             }
/* 2953:2413 */             break;
/* 2954:     */           case 225: 
/* 2955:2415 */             if ((0x7E & l) != 0L)
/* 2956:     */             {
/* 2957:2417 */               if (kind > 46) {
/* 2958:2418 */                 kind = 46;
/* 2959:     */               }
/* 2960:2419 */               jjCheckNAddStates(222, 224);
/* 2961:     */             }
/* 2962:2420 */             break;
/* 2963:     */           case 226: 
/* 2964:2422 */             if ((0x7E & l) != 0L)
/* 2965:     */             {
/* 2966:2424 */               if (kind > 46) {
/* 2967:2425 */                 kind = 46;
/* 2968:     */               }
/* 2969:2426 */               jjCheckNAddStates(225, 227);
/* 2970:     */             }
/* 2971:2427 */             break;
/* 2972:     */           case 229: 
/* 2973:2429 */             if ((0x7E & l) != 0L)
/* 2974:     */             {
/* 2975:2431 */               if (kind > 46) {
/* 2976:2432 */                 kind = 46;
/* 2977:     */               }
/* 2978:2433 */               jjCheckNAddTwoStates(198, 203);
/* 2979:     */             }
/* 2980:2434 */             break;
/* 2981:     */           case 256: 
/* 2982:2436 */             if (((0x2000 & l) != 0L) && (kind > 32)) {
/* 2983:2437 */               kind = 32;
/* 2984:     */             }
/* 2985:     */             break;
/* 2986:     */           case 257: 
/* 2987:2440 */             if ((0x20 & l) != 0L) {
/* 2988:2441 */               this.jjstateSet[(this.jjnewStateCnt++)] = 256;
/* 2989:     */             }
/* 2990:     */             break;
/* 2991:     */           case 259: 
/* 2992:2444 */             if (((0x1000000 & l) != 0L) && (kind > 33)) {
/* 2993:2445 */               kind = 33;
/* 2994:     */             }
/* 2995:     */             break;
/* 2996:     */           case 260: 
/* 2997:2448 */             if ((0x20 & l) != 0L) {
/* 2998:2449 */               this.jjstateSet[(this.jjnewStateCnt++)] = 259;
/* 2999:     */             }
/* 3000:     */             break;
/* 3001:     */           case 262: 
/* 3002:2452 */             if (((0x1000000 & l) != 0L) && (kind > 34)) {
/* 3003:2453 */               kind = 34;
/* 3004:     */             }
/* 3005:     */             break;
/* 3006:     */           case 263: 
/* 3007:2456 */             if ((0x10000 & l) != 0L) {
/* 3008:2457 */               this.jjstateSet[(this.jjnewStateCnt++)] = 262;
/* 3009:     */             }
/* 3010:     */             break;
/* 3011:     */           case 265: 
/* 3012:2460 */             if (((0x2000 & l) != 0L) && (kind > 35)) {
/* 3013:2461 */               kind = 35;
/* 3014:     */             }
/* 3015:     */             break;
/* 3016:     */           case 266: 
/* 3017:2464 */             if ((0x8 & l) != 0L) {
/* 3018:2465 */               this.jjstateSet[(this.jjnewStateCnt++)] = 265;
/* 3019:     */             }
/* 3020:     */             break;
/* 3021:     */           case 268: 
/* 3022:2468 */             if (((0x2000 & l) != 0L) && (kind > 36)) {
/* 3023:2469 */               kind = 36;
/* 3024:     */             }
/* 3025:     */             break;
/* 3026:     */           case 269: 
/* 3027:2472 */             if ((0x2000 & l) != 0L) {
/* 3028:2473 */               this.jjstateSet[(this.jjnewStateCnt++)] = 268;
/* 3029:     */             }
/* 3030:     */             break;
/* 3031:     */           case 271: 
/* 3032:2476 */             if (((0x4000 & l) != 0L) && (kind > 37)) {
/* 3033:2477 */               kind = 37;
/* 3034:     */             }
/* 3035:     */             break;
/* 3036:     */           case 272: 
/* 3037:2480 */             if ((0x200 & l) != 0L) {
/* 3038:2481 */               this.jjstateSet[(this.jjnewStateCnt++)] = 271;
/* 3039:     */             }
/* 3040:     */             break;
/* 3041:     */           case 274: 
/* 3042:2484 */             if (((0x100000 & l) != 0L) && (kind > 38)) {
/* 3043:2485 */               kind = 38;
/* 3044:     */             }
/* 3045:     */             break;
/* 3046:     */           case 275: 
/* 3047:2488 */             if ((0x10000 & l) != 0L) {
/* 3048:2489 */               this.jjstateSet[(this.jjnewStateCnt++)] = 274;
/* 3049:     */             }
/* 3050:     */             break;
/* 3051:     */           case 277: 
/* 3052:2492 */             if (((0x8 & l) != 0L) && (kind > 39)) {
/* 3053:2493 */               kind = 39;
/* 3054:     */             }
/* 3055:     */             break;
/* 3056:     */           case 278: 
/* 3057:2496 */             if ((0x10000 & l) != 0L) {
/* 3058:2497 */               this.jjstateSet[(this.jjnewStateCnt++)] = 277;
/* 3059:     */             }
/* 3060:     */             break;
/* 3061:     */           }
/* 3062:2501 */         } while (i != startsAt);
/* 3063:     */       }
/* 3064:     */       else
/* 3065:     */       {
/* 3066:2505 */         int i2 = (this.curChar & 0xFF) >> '\006';
/* 3067:2506 */         long l2 = 1L << (this.curChar & 0x3F);
/* 3068:     */         do
/* 3069:     */         {
/* 3070:2509 */           switch (this.jjstateSet[(--i)])
/* 3071:     */           {
/* 3072:     */           case 2: 
/* 3073:     */           case 4: 
/* 3074:     */           case 318: 
/* 3075:2514 */             if ((jjbitVec2[i2] & l2) != 0L)
/* 3076:     */             {
/* 3077:2516 */               if (kind > 3) {
/* 3078:2517 */                 kind = 3;
/* 3079:     */               }
/* 3080:2518 */               jjCheckNAddTwoStates(2, 3);
/* 3081:     */             }
/* 3082:2519 */             break;
/* 3083:     */           case 1: 
/* 3084:2521 */             if ((jjbitVec2[i2] & l2) != 0L)
/* 3085:     */             {
/* 3086:2523 */               if (kind > 3) {
/* 3087:2524 */                 kind = 3;
/* 3088:     */               }
/* 3089:2525 */               jjCheckNAddTwoStates(2, 3);
/* 3090:     */             }
/* 3091:2526 */             break;
/* 3092:     */           case 89: 
/* 3093:     */           case 92: 
/* 3094:2529 */             if ((jjbitVec2[i2] & l2) != 0L)
/* 3095:     */             {
/* 3096:2531 */               if (kind > 31) {
/* 3097:2532 */                 kind = 31;
/* 3098:     */               }
/* 3099:2533 */               jjCheckNAddTwoStates(90, 91);
/* 3100:     */             }
/* 3101:2534 */             break;
/* 3102:     */           case 90: 
/* 3103:     */           case 319: 
/* 3104:2537 */             if ((jjbitVec2[i2] & l2) != 0L)
/* 3105:     */             {
/* 3106:2539 */               if (kind > 31) {
/* 3107:2540 */                 kind = 31;
/* 3108:     */               }
/* 3109:2541 */               jjCheckNAddTwoStates(90, 91);
/* 3110:     */             }
/* 3111:2542 */             break;
/* 3112:     */           case 19: 
/* 3113:     */           case 21: 
/* 3114:2545 */             if ((jjbitVec2[i2] & l2) != 0L)
/* 3115:     */             {
/* 3116:2547 */               if (kind > 9) {
/* 3117:2548 */                 kind = 9;
/* 3118:     */               }
/* 3119:2549 */               jjCheckNAddTwoStates(19, 20);
/* 3120:     */             }
/* 3121:2550 */             break;
/* 3122:     */           case 36: 
/* 3123:     */           case 42: 
/* 3124:2553 */             if ((jjbitVec2[i2] & l2) != 0L) {
/* 3125:2554 */               jjCheckNAddStates(56, 58);
/* 3126:     */             }
/* 3127:     */             break;
/* 3128:     */           case 57: 
/* 3129:     */           case 63: 
/* 3130:2558 */             if ((jjbitVec2[i2] & l2) != 0L) {
/* 3131:2559 */               jjCheckNAddStates(53, 55);
/* 3132:     */             }
/* 3133:     */             break;
/* 3134:     */           case 134: 
/* 3135:     */           case 138: 
/* 3136:2563 */             if ((jjbitVec2[i2] & l2) != 0L) {
/* 3137:2564 */               jjCheckNAddStates(146, 149);
/* 3138:     */             }
/* 3139:     */             break;
/* 3140:     */           case 153: 
/* 3141:     */           case 159: 
/* 3142:2568 */             if ((jjbitVec2[i2] & l2) != 0L) {
/* 3143:2569 */               jjCheckNAddStates(163, 165);
/* 3144:     */             }
/* 3145:     */             break;
/* 3146:     */           case 174: 
/* 3147:     */           case 180: 
/* 3148:2573 */             if ((jjbitVec2[i2] & l2) != 0L) {
/* 3149:2574 */               jjCheckNAddStates(179, 181);
/* 3150:     */             }
/* 3151:     */             break;
/* 3152:     */           }
/* 3153:2578 */         } while (i != startsAt);
/* 3154:     */       }
/* 3155:2580 */       if (kind != 2147483647)
/* 3156:     */       {
/* 3157:2582 */         this.jjmatchedKind = kind;
/* 3158:2583 */         this.jjmatchedPos = curPos;
/* 3159:2584 */         kind = 2147483647;
/* 3160:     */       }
/* 3161:2586 */       curPos++;
/* 3162:2587 */       if ((i = this.jjnewStateCnt) == (startsAt = 317 - (this.jjnewStateCnt = startsAt))) {
/* 3163:2588 */         return curPos;
/* 3164:     */       }
/* 3165:     */       try
/* 3166:     */       {
/* 3167:2589 */         this.curChar = this.input_stream.readChar();
/* 3168:     */       }
/* 3169:     */       catch (IOException e) {}
/* 3170:     */     }
/* 3171:2590 */     return curPos;
/* 3172:     */   }
/* 3173:     */   
/* 3174:     */   private final int jjMoveStringLiteralDfa0_1()
/* 3175:     */   {
/* 3176:2595 */     switch (this.curChar)
/* 3177:     */     {
/* 3178:     */     case '*': 
/* 3179:2598 */       return jjMoveStringLiteralDfa1_1(1L);
/* 3180:     */     }
/* 3181:2600 */     return 1;
/* 3182:     */   }
/* 3183:     */   
/* 3184:     */   private final int jjMoveStringLiteralDfa1_1(long active1)
/* 3185:     */   {
/* 3186:     */     try
/* 3187:     */     {
/* 3188:2605 */       this.curChar = this.input_stream.readChar();
/* 3189:     */     }
/* 3190:     */     catch (IOException e)
/* 3191:     */     {
/* 3192:2607 */       return 1;
/* 3193:     */     }
/* 3194:2609 */     switch (this.curChar)
/* 3195:     */     {
/* 3196:     */     case '/': 
/* 3197:2612 */       if ((active1 & 1L) != 0L) {
/* 3198:2613 */         return jjStopAtPos(1, 64);
/* 3199:     */       }
/* 3200:     */       break;
/* 3201:     */     default: 
/* 3202:2616 */       return 2;
/* 3203:     */     }
/* 3204:2618 */     return 2;
/* 3205:     */   }
/* 3206:     */   
/* 3207:2620 */   static final int[] jjnextStates = { 284, 285, 286, 257, 287, 288, 289, 260, 290, 291, 292, 263, 293, 294, 295, 266, 296, 297, 298, 269, 299, 300, 301, 272, 302, 303, 304, 275, 305, 306, 307, 278, 308, 309, 310, 280, 311, 312, 313, 314, 315, 316, 255, 258, 261, 264, 267, 270, 273, 276, 279, 281, 282, 57, 58, 59, 36, 37, 38, 2, 6, 8, 9, 11, 14, 7, 3, 2, 7, 3, 19, 23, 25, 26, 28, 31, 24, 20, 19, 24, 20, 36, 44, 46, 47, 49, 52, 45, 37, 38, 36, 45, 37, 38, 57, 65, 67, 68, 70, 73, 66, 58, 59, 57, 66, 58, 59, 90, 94, 96, 97, 99, 102, 95, 91, 90, 95, 91, 108, 109, 110, 112, 115, 95, 90, 91, 95, 90, 91, 121, 122, 123, 125, 128, 7, 2, 3, 7, 2, 3, 134, 152, 173, 136, 137, 194, 134, 135, 136, 137, 134, 140, 142, 143, 145, 148, 136, 137, 141, 134, 136, 137, 141, 153, 154, 155, 153, 161, 163, 164, 166, 169, 162, 154, 155, 153, 162, 154, 155, 174, 175, 176, 174, 182, 184, 185, 187, 190, 183, 175, 176, 174, 183, 175, 176, 134, 152, 173, 135, 136, 137, 194, 198, 199, 243, 200, 214, 215, 217, 220, 201, 198, 224, 236, 203, 204, 205, 207, 210, 198, 225, 232, 198, 226, 230, 198, 228, 229, 227, 233, 235, 227, 237, 239, 242, 247, 250, 252, 253, 227, 89, 106, 196, 197, 21, 22, 39, 41, 42, 43, 60, 62, 63, 64, 138, 139, 156, 158, 159, 160, 177, 179, 180, 181 };
/* 3208:2639 */   public static final String[] jjstrLiteralImages = { "", null, null, null, null, null, null, null, null, null, "{", "}", ",", ".", ";", ":", "*", "/", "+", "-", "=", ">", "[", "]", null, ")", null, "<!--", "-->", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null };
/* 3209:2646 */   public static final String[] lexStateNames = { "DEFAULT", "COMMENT" };
/* 3210:2650 */   public static final int[] jjnewLexState = { -1, -1, 1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, -1, -1 };
/* 3211:2655 */   static final long[] jjtoToken = { 114349209288699L, 4L };
/* 3212:2658 */   static final long[] jjtoSkip = { 0L, 1L };
/* 3213:2661 */   static final long[] jjtoMore = { 4L, 2L };
/* 3214:     */   protected CharStream input_stream;
/* 3215:2665 */   private final int[] jjrounds = new int[317];
/* 3216:2666 */   private final int[] jjstateSet = new int[634];
/* 3217:     */   StringBuffer image;
/* 3218:     */   int jjimageLen;
/* 3219:     */   int lengthOfMatch;
/* 3220:     */   protected char curChar;
/* 3221:     */   
/* 3222:     */   public SACParserCSS1TokenManager(CharStream stream)
/* 3223:     */   {
/* 3224:2672 */     this.input_stream = stream;
/* 3225:     */   }
/* 3226:     */   
/* 3227:     */   public SACParserCSS1TokenManager(CharStream stream, int lexState)
/* 3228:     */   {
/* 3229:2675 */     this(stream);
/* 3230:2676 */     SwitchTo(lexState);
/* 3231:     */   }
/* 3232:     */   
/* 3233:     */   public void ReInit(CharStream stream)
/* 3234:     */   {
/* 3235:2680 */     this.jjmatchedPos = (this.jjnewStateCnt = 0);
/* 3236:2681 */     this.curLexState = this.defaultLexState;
/* 3237:2682 */     this.input_stream = stream;
/* 3238:2683 */     ReInitRounds();
/* 3239:     */   }
/* 3240:     */   
/* 3241:     */   private final void ReInitRounds()
/* 3242:     */   {
/* 3243:2688 */     this.jjround = -2147483647;
/* 3244:2689 */     for (int i = 317; i-- > 0;) {
/* 3245:2690 */       this.jjrounds[i] = -2147483648;
/* 3246:     */     }
/* 3247:     */   }
/* 3248:     */   
/* 3249:     */   public void ReInit(CharStream stream, int lexState)
/* 3250:     */   {
/* 3251:2694 */     ReInit(stream);
/* 3252:2695 */     SwitchTo(lexState);
/* 3253:     */   }
/* 3254:     */   
/* 3255:     */   public void SwitchTo(int lexState)
/* 3256:     */   {
/* 3257:2699 */     if ((lexState >= 2) || (lexState < 0)) {
/* 3258:2700 */       throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", 2);
/* 3259:     */     }
/* 3260:2702 */     this.curLexState = lexState;
/* 3261:     */   }
/* 3262:     */   
/* 3263:     */   protected Token jjFillToken()
/* 3264:     */   {
/* 3265:2707 */     Token t = Token.newToken(this.jjmatchedKind);
/* 3266:2708 */     t.kind = this.jjmatchedKind;
/* 3267:2709 */     String im = jjstrLiteralImages[this.jjmatchedKind];
/* 3268:2710 */     t.image = (im == null ? this.input_stream.GetImage() : im);
/* 3269:2711 */     t.beginLine = this.input_stream.getBeginLine();
/* 3270:2712 */     t.beginColumn = this.input_stream.getBeginColumn();
/* 3271:2713 */     t.endLine = this.input_stream.getEndLine();
/* 3272:2714 */     t.endColumn = this.input_stream.getEndColumn();
/* 3273:2715 */     return t;
/* 3274:     */   }
/* 3275:     */   
/* 3276:2718 */   int curLexState = 0;
/* 3277:2719 */   int defaultLexState = 0;
/* 3278:     */   int jjnewStateCnt;
/* 3279:     */   int jjround;
/* 3280:     */   int jjmatchedPos;
/* 3281:     */   int jjmatchedKind;
/* 3282:     */   
/* 3283:     */   public Token getNextToken()
/* 3284:     */   {
/* 3285:2728 */     Token specialToken = null;
/* 3286:     */     
/* 3287:2730 */     int curPos = 0;
/* 3288:     */     try
/* 3289:     */     {
/* 3290:2737 */       this.curChar = this.input_stream.BeginToken();
/* 3291:     */     }
/* 3292:     */     catch (IOException e)
/* 3293:     */     {
/* 3294:2741 */       this.jjmatchedKind = 0;
/* 3295:2742 */       return jjFillToken();
/* 3296:     */     }
/* 3297:2745 */     this.image = null;
/* 3298:2746 */     this.jjimageLen = 0;
/* 3299:     */     for (;;)
/* 3300:     */     {
/* 3301:2750 */       switch (this.curLexState)
/* 3302:     */       {
/* 3303:     */       case 0: 
/* 3304:2753 */         this.jjmatchedKind = 2147483647;
/* 3305:2754 */         this.jjmatchedPos = 0;
/* 3306:2755 */         curPos = jjMoveStringLiteralDfa0_0();
/* 3307:2756 */         if ((this.jjmatchedPos == 0) && (this.jjmatchedKind > 66)) {
/* 3308:2758 */           this.jjmatchedKind = 66;
/* 3309:     */         }
/* 3310:     */         break;
/* 3311:     */       case 1: 
/* 3312:2762 */         this.jjmatchedKind = 2147483647;
/* 3313:2763 */         this.jjmatchedPos = 0;
/* 3314:2764 */         curPos = jjMoveStringLiteralDfa0_1();
/* 3315:2765 */         if ((this.jjmatchedPos == 0) && (this.jjmatchedKind > 65)) {
/* 3316:2767 */           this.jjmatchedKind = 65;
/* 3317:     */         }
/* 3318:     */         break;
/* 3319:     */       }
/* 3320:2771 */       if (this.jjmatchedKind != 2147483647)
/* 3321:     */       {
/* 3322:2773 */         if (this.jjmatchedPos + 1 < curPos) {
/* 3323:2774 */           this.input_stream.backup(curPos - this.jjmatchedPos - 1);
/* 3324:     */         }
/* 3325:2775 */         if ((jjtoToken[(this.jjmatchedKind >> 6)] & 1L << (this.jjmatchedKind & 0x3F)) != 0L)
/* 3326:     */         {
/* 3327:2777 */           Token matchedToken = jjFillToken();
/* 3328:2778 */           TokenLexicalActions(matchedToken);
/* 3329:2779 */           if (jjnewLexState[this.jjmatchedKind] != -1) {
/* 3330:2780 */             this.curLexState = jjnewLexState[this.jjmatchedKind];
/* 3331:     */           }
/* 3332:2781 */           return matchedToken;
/* 3333:     */         }
/* 3334:2783 */         if ((jjtoSkip[(this.jjmatchedKind >> 6)] & 1L << (this.jjmatchedKind & 0x3F)) != 0L)
/* 3335:     */         {
/* 3336:2785 */           if (jjnewLexState[this.jjmatchedKind] == -1) {
/* 3337:     */             break;
/* 3338:     */           }
/* 3339:2786 */           this.curLexState = jjnewLexState[this.jjmatchedKind]; break;
/* 3340:     */         }
/* 3341:2789 */         this.jjimageLen += this.jjmatchedPos + 1;
/* 3342:2790 */         if (jjnewLexState[this.jjmatchedKind] != -1) {
/* 3343:2791 */           this.curLexState = jjnewLexState[this.jjmatchedKind];
/* 3344:     */         }
/* 3345:2792 */         curPos = 0;
/* 3346:2793 */         this.jjmatchedKind = 2147483647;
/* 3347:     */         try
/* 3348:     */         {
/* 3349:2795 */           this.curChar = this.input_stream.readChar();
/* 3350:     */         }
/* 3351:     */         catch (IOException e1) {}
/* 3352:     */       }
/* 3353:     */     }
/* 3354:2800 */     int error_line = this.input_stream.getEndLine();
/* 3355:2801 */     int error_column = this.input_stream.getEndColumn();
/* 3356:2802 */     String error_after = null;
/* 3357:2803 */     boolean EOFSeen = false;
/* 3358:     */     try
/* 3359:     */     {
/* 3360:2804 */       this.input_stream.readChar();this.input_stream.backup(1);
/* 3361:     */     }
/* 3362:     */     catch (IOException e1)
/* 3363:     */     {
/* 3364:2806 */       EOFSeen = true;
/* 3365:2807 */       error_after = curPos <= 1 ? "" : this.input_stream.GetImage();
/* 3366:2808 */       if ((this.curChar == '\n') || (this.curChar == '\r'))
/* 3367:     */       {
/* 3368:2809 */         error_line++;
/* 3369:2810 */         error_column = 0;
/* 3370:     */       }
/* 3371:     */       else
/* 3372:     */       {
/* 3373:2813 */         error_column++;
/* 3374:     */       }
/* 3375:     */     }
/* 3376:2815 */     if (!EOFSeen)
/* 3377:     */     {
/* 3378:2816 */       this.input_stream.backup(1);
/* 3379:2817 */       error_after = curPos <= 1 ? "" : this.input_stream.GetImage();
/* 3380:     */     }
/* 3381:2819 */     throw new TokenMgrError(EOFSeen, this.curLexState, error_line, error_column, error_after, this.curChar, 0);
/* 3382:     */   }
/* 3383:     */   
/* 3384:     */   void TokenLexicalActions(Token matchedToken)
/* 3385:     */   {
/* 3386:2826 */     switch (this.jjmatchedKind)
/* 3387:     */     {
/* 3388:     */     case 4: 
/* 3389:2829 */       if (this.image == null) {
/* 3390:2830 */         this.image = new StringBuffer();
/* 3391:     */       }
/* 3392:2831 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 3393:2832 */       matchedToken.image = trimBy(this.image, 1, 0);
/* 3394:2833 */       break;
/* 3395:     */     case 5: 
/* 3396:2835 */       if (this.image == null) {
/* 3397:2836 */         this.image = new StringBuffer();
/* 3398:     */       }
/* 3399:2837 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 3400:2838 */       matchedToken.image = trimBy(this.image, 1, 0);
/* 3401:2839 */       break;
/* 3402:     */     case 6: 
/* 3403:2841 */       if (this.image == null) {
/* 3404:2842 */         this.image = new StringBuffer();
/* 3405:     */       }
/* 3406:2843 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 3407:2844 */       matchedToken.image = trimBy(this.image, 1, 0);
/* 3408:2845 */       break;
/* 3409:     */     case 7: 
/* 3410:2847 */       if (this.image == null) {
/* 3411:2848 */         this.image = new StringBuffer();
/* 3412:     */       }
/* 3413:2849 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 3414:2850 */       matchedToken.image = trimBy(this.image, 1, 0);
/* 3415:2851 */       break;
/* 3416:     */     case 8: 
/* 3417:2853 */       if (this.image == null) {
/* 3418:2854 */         this.image = new StringBuffer();
/* 3419:     */       }
/* 3420:2855 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 3421:2856 */       matchedToken.image = trimBy(this.image, 1, 0);
/* 3422:2857 */       break;
/* 3423:     */     case 24: 
/* 3424:2859 */       if (this.image == null) {
/* 3425:2860 */         this.image = new StringBuffer();
/* 3426:     */       }
/* 3427:2861 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 3428:2862 */       matchedToken.image = trimBy(this.image, 1, 1);
/* 3429:2863 */       break;
/* 3430:     */     case 26: 
/* 3431:2865 */       if (this.image == null) {
/* 3432:2866 */         this.image = new StringBuffer();
/* 3433:     */       }
/* 3434:2867 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 3435:2868 */       matchedToken.image = trimUrl(this.image);
/* 3436:2869 */       break;
/* 3437:     */     case 32: 
/* 3438:2871 */       if (this.image == null) {
/* 3439:2872 */         this.image = new StringBuffer();
/* 3440:     */       }
/* 3441:2873 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 3442:2874 */       matchedToken.image = trimBy(this.image, 0, 2);
/* 3443:2875 */       break;
/* 3444:     */     case 33: 
/* 3445:2877 */       if (this.image == null) {
/* 3446:2878 */         this.image = new StringBuffer();
/* 3447:     */       }
/* 3448:2879 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 3449:2880 */       matchedToken.image = trimBy(this.image, 0, 2);
/* 3450:2881 */       break;
/* 3451:     */     case 34: 
/* 3452:2883 */       if (this.image == null) {
/* 3453:2884 */         this.image = new StringBuffer();
/* 3454:     */       }
/* 3455:2885 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 3456:2886 */       matchedToken.image = trimBy(this.image, 0, 2);
/* 3457:2887 */       break;
/* 3458:     */     case 35: 
/* 3459:2889 */       if (this.image == null) {
/* 3460:2890 */         this.image = new StringBuffer();
/* 3461:     */       }
/* 3462:2891 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 3463:2892 */       matchedToken.image = trimBy(this.image, 0, 2);
/* 3464:2893 */       break;
/* 3465:     */     case 36: 
/* 3466:2895 */       if (this.image == null) {
/* 3467:2896 */         this.image = new StringBuffer();
/* 3468:     */       }
/* 3469:2897 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 3470:2898 */       matchedToken.image = trimBy(this.image, 0, 2);
/* 3471:2899 */       break;
/* 3472:     */     case 37: 
/* 3473:2901 */       if (this.image == null) {
/* 3474:2902 */         this.image = new StringBuffer();
/* 3475:     */       }
/* 3476:2903 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 3477:2904 */       matchedToken.image = trimBy(this.image, 0, 2);
/* 3478:2905 */       break;
/* 3479:     */     case 38: 
/* 3480:2907 */       if (this.image == null) {
/* 3481:2908 */         this.image = new StringBuffer();
/* 3482:     */       }
/* 3483:2909 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 3484:2910 */       matchedToken.image = trimBy(this.image, 0, 2);
/* 3485:2911 */       break;
/* 3486:     */     case 39: 
/* 3487:2913 */       if (this.image == null) {
/* 3488:2914 */         this.image = new StringBuffer();
/* 3489:     */       }
/* 3490:2915 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 3491:2916 */       matchedToken.image = trimBy(this.image, 0, 2);
/* 3492:2917 */       break;
/* 3493:     */     case 40: 
/* 3494:2919 */       if (this.image == null) {
/* 3495:2920 */         this.image = new StringBuffer();
/* 3496:     */       }
/* 3497:2921 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 3498:2922 */       matchedToken.image = trimBy(this.image, 0, 1);
/* 3499:2923 */       break;
/* 3500:     */     case 66: 
/* 3501:2925 */       if (this.image == null) {
/* 3502:2926 */         this.image = new StringBuffer();
/* 3503:     */       }
/* 3504:2927 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 3505:2928 */       if (!this._quiet) {
/* 3506:2929 */         System.err.println("Illegal character : " + this.image.toString());
/* 3507:     */       }
/* 3508:     */       break;
/* 3509:     */     }
/* 3510:     */   }
/* 3511:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.steadystate.css.parser.SACParserCSS1TokenManager
 * JD-Core Version:    0.7.0.1
 */