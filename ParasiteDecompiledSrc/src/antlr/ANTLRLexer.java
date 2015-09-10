/*    1:     */ package antlr;
/*    2:     */ 
/*    3:     */ import antlr.collections.impl.BitSet;
/*    4:     */ import java.io.InputStream;
/*    5:     */ import java.io.Reader;
/*    6:     */ import java.util.Hashtable;
/*    7:     */ 
/*    8:     */ public class ANTLRLexer
/*    9:     */   extends CharScanner
/*   10:     */   implements ANTLRTokenTypes, TokenStream
/*   11:     */ {
/*   12:     */   public static int escapeCharValue(String paramString)
/*   13:     */   {
/*   14:  35 */     if (paramString.charAt(1) != '\\') {
/*   15:  35 */       return 0;
/*   16:     */     }
/*   17:  36 */     switch (paramString.charAt(2))
/*   18:     */     {
/*   19:     */     case 'b': 
/*   20:  37 */       return 8;
/*   21:     */     case 'r': 
/*   22:  38 */       return 13;
/*   23:     */     case 't': 
/*   24:  39 */       return 9;
/*   25:     */     case 'n': 
/*   26:  40 */       return 10;
/*   27:     */     case 'f': 
/*   28:  41 */       return 12;
/*   29:     */     case '"': 
/*   30:  42 */       return 34;
/*   31:     */     case '\'': 
/*   32:  43 */       return 39;
/*   33:     */     case '\\': 
/*   34:  44 */       return 92;
/*   35:     */     case 'u': 
/*   36:  48 */       if (paramString.length() != 8) {
/*   37:  49 */         return 0;
/*   38:     */       }
/*   39:  52 */       return Character.digit(paramString.charAt(3), 16) * 16 * 16 * 16 + Character.digit(paramString.charAt(4), 16) * 16 * 16 + Character.digit(paramString.charAt(5), 16) * 16 + Character.digit(paramString.charAt(6), 16);
/*   40:     */     case '0': 
/*   41:     */     case '1': 
/*   42:     */     case '2': 
/*   43:     */     case '3': 
/*   44:  63 */       if ((paramString.length() > 5) && (Character.isDigit(paramString.charAt(4)))) {
/*   45:  64 */         return (paramString.charAt(2) - '0') * 8 * 8 + (paramString.charAt(3) - '0') * 8 + (paramString.charAt(4) - '0');
/*   46:     */       }
/*   47:  66 */       if ((paramString.length() > 4) && (Character.isDigit(paramString.charAt(3)))) {
/*   48:  67 */         return (paramString.charAt(2) - '0') * 8 + (paramString.charAt(3) - '0');
/*   49:     */       }
/*   50:  69 */       return paramString.charAt(2) - '0';
/*   51:     */     case '4': 
/*   52:     */     case '5': 
/*   53:     */     case '6': 
/*   54:     */     case '7': 
/*   55:  75 */       if ((paramString.length() > 4) && (Character.isDigit(paramString.charAt(3)))) {
/*   56:  76 */         return (paramString.charAt(2) - '0') * 8 + (paramString.charAt(3) - '0');
/*   57:     */       }
/*   58:  78 */       return paramString.charAt(2) - '0';
/*   59:     */     }
/*   60:  81 */     return 0;
/*   61:     */   }
/*   62:     */   
/*   63:     */   public static int tokenTypeForCharLiteral(String paramString)
/*   64:     */   {
/*   65:  86 */     if (paramString.length() > 3) {
/*   66:  87 */       return escapeCharValue(paramString);
/*   67:     */     }
/*   68:  90 */     return paramString.charAt(1);
/*   69:     */   }
/*   70:     */   
/*   71:     */   public ANTLRLexer(InputStream paramInputStream)
/*   72:     */   {
/*   73:  94 */     this(new ByteBuffer(paramInputStream));
/*   74:     */   }
/*   75:     */   
/*   76:     */   public ANTLRLexer(Reader paramReader)
/*   77:     */   {
/*   78:  97 */     this(new CharBuffer(paramReader));
/*   79:     */   }
/*   80:     */   
/*   81:     */   public ANTLRLexer(InputBuffer paramInputBuffer)
/*   82:     */   {
/*   83: 100 */     this(new LexerSharedInputState(paramInputBuffer));
/*   84:     */   }
/*   85:     */   
/*   86:     */   public ANTLRLexer(LexerSharedInputState paramLexerSharedInputState)
/*   87:     */   {
/*   88: 103 */     super(paramLexerSharedInputState);
/*   89: 104 */     this.caseSensitiveLiterals = true;
/*   90: 105 */     setCaseSensitive(true);
/*   91: 106 */     this.literals = new Hashtable();
/*   92: 107 */     this.literals.put(new ANTLRHashString("public", this), new Integer(31));
/*   93: 108 */     this.literals.put(new ANTLRHashString("class", this), new Integer(10));
/*   94: 109 */     this.literals.put(new ANTLRHashString("header", this), new Integer(5));
/*   95: 110 */     this.literals.put(new ANTLRHashString("throws", this), new Integer(37));
/*   96: 111 */     this.literals.put(new ANTLRHashString("lexclass", this), new Integer(9));
/*   97: 112 */     this.literals.put(new ANTLRHashString("catch", this), new Integer(40));
/*   98: 113 */     this.literals.put(new ANTLRHashString("private", this), new Integer(32));
/*   99: 114 */     this.literals.put(new ANTLRHashString("options", this), new Integer(51));
/*  100: 115 */     this.literals.put(new ANTLRHashString("extends", this), new Integer(11));
/*  101: 116 */     this.literals.put(new ANTLRHashString("protected", this), new Integer(30));
/*  102: 117 */     this.literals.put(new ANTLRHashString("TreeParser", this), new Integer(13));
/*  103: 118 */     this.literals.put(new ANTLRHashString("Parser", this), new Integer(29));
/*  104: 119 */     this.literals.put(new ANTLRHashString("Lexer", this), new Integer(12));
/*  105: 120 */     this.literals.put(new ANTLRHashString("returns", this), new Integer(35));
/*  106: 121 */     this.literals.put(new ANTLRHashString("charVocabulary", this), new Integer(18));
/*  107: 122 */     this.literals.put(new ANTLRHashString("tokens", this), new Integer(4));
/*  108: 123 */     this.literals.put(new ANTLRHashString("exception", this), new Integer(39));
/*  109:     */   }
/*  110:     */   
/*  111:     */   public Token nextToken()
/*  112:     */     throws TokenStreamException
/*  113:     */   {
/*  114: 127 */     Token localToken = null;
/*  115:     */     for (;;)
/*  116:     */     {
/*  117: 130 */       Object localObject = null;
/*  118: 131 */       int i = 0;
/*  119: 132 */       resetText();
/*  120:     */       try
/*  121:     */       {
/*  122: 135 */         switch (LA(1))
/*  123:     */         {
/*  124:     */         case '\t': 
/*  125:     */         case '\n': 
/*  126:     */         case '\r': 
/*  127:     */         case ' ': 
/*  128: 138 */           mWS(true);
/*  129: 139 */           localToken = this._returnToken;
/*  130: 140 */           break;
/*  131:     */         case '/': 
/*  132: 144 */           mCOMMENT(true);
/*  133: 145 */           localToken = this._returnToken;
/*  134: 146 */           break;
/*  135:     */         case '<': 
/*  136: 150 */           mOPEN_ELEMENT_OPTION(true);
/*  137: 151 */           localToken = this._returnToken;
/*  138: 152 */           break;
/*  139:     */         case '>': 
/*  140: 156 */           mCLOSE_ELEMENT_OPTION(true);
/*  141: 157 */           localToken = this._returnToken;
/*  142: 158 */           break;
/*  143:     */         case ',': 
/*  144: 162 */           mCOMMA(true);
/*  145: 163 */           localToken = this._returnToken;
/*  146: 164 */           break;
/*  147:     */         case '?': 
/*  148: 168 */           mQUESTION(true);
/*  149: 169 */           localToken = this._returnToken;
/*  150: 170 */           break;
/*  151:     */         case '#': 
/*  152: 174 */           mTREE_BEGIN(true);
/*  153: 175 */           localToken = this._returnToken;
/*  154: 176 */           break;
/*  155:     */         case '(': 
/*  156: 180 */           mLPAREN(true);
/*  157: 181 */           localToken = this._returnToken;
/*  158: 182 */           break;
/*  159:     */         case ')': 
/*  160: 186 */           mRPAREN(true);
/*  161: 187 */           localToken = this._returnToken;
/*  162: 188 */           break;
/*  163:     */         case ':': 
/*  164: 192 */           mCOLON(true);
/*  165: 193 */           localToken = this._returnToken;
/*  166: 194 */           break;
/*  167:     */         case '*': 
/*  168: 198 */           mSTAR(true);
/*  169: 199 */           localToken = this._returnToken;
/*  170: 200 */           break;
/*  171:     */         case '+': 
/*  172: 204 */           mPLUS(true);
/*  173: 205 */           localToken = this._returnToken;
/*  174: 206 */           break;
/*  175:     */         case ';': 
/*  176: 210 */           mSEMI(true);
/*  177: 211 */           localToken = this._returnToken;
/*  178: 212 */           break;
/*  179:     */         case '^': 
/*  180: 216 */           mCARET(true);
/*  181: 217 */           localToken = this._returnToken;
/*  182: 218 */           break;
/*  183:     */         case '!': 
/*  184: 222 */           mBANG(true);
/*  185: 223 */           localToken = this._returnToken;
/*  186: 224 */           break;
/*  187:     */         case '|': 
/*  188: 228 */           mOR(true);
/*  189: 229 */           localToken = this._returnToken;
/*  190: 230 */           break;
/*  191:     */         case '~': 
/*  192: 234 */           mNOT_OP(true);
/*  193: 235 */           localToken = this._returnToken;
/*  194: 236 */           break;
/*  195:     */         case '}': 
/*  196: 240 */           mRCURLY(true);
/*  197: 241 */           localToken = this._returnToken;
/*  198: 242 */           break;
/*  199:     */         case '\'': 
/*  200: 246 */           mCHAR_LITERAL(true);
/*  201: 247 */           localToken = this._returnToken;
/*  202: 248 */           break;
/*  203:     */         case '"': 
/*  204: 252 */           mSTRING_LITERAL(true);
/*  205: 253 */           localToken = this._returnToken;
/*  206: 254 */           break;
/*  207:     */         case '0': 
/*  208:     */         case '1': 
/*  209:     */         case '2': 
/*  210:     */         case '3': 
/*  211:     */         case '4': 
/*  212:     */         case '5': 
/*  213:     */         case '6': 
/*  214:     */         case '7': 
/*  215:     */         case '8': 
/*  216:     */         case '9': 
/*  217: 260 */           mINT(true);
/*  218: 261 */           localToken = this._returnToken;
/*  219: 262 */           break;
/*  220:     */         case '[': 
/*  221: 266 */           mARG_ACTION(true);
/*  222: 267 */           localToken = this._returnToken;
/*  223: 268 */           break;
/*  224:     */         case '{': 
/*  225: 272 */           mACTION(true);
/*  226: 273 */           localToken = this._returnToken;
/*  227: 274 */           break;
/*  228:     */         case 'A': 
/*  229:     */         case 'B': 
/*  230:     */         case 'C': 
/*  231:     */         case 'D': 
/*  232:     */         case 'E': 
/*  233:     */         case 'F': 
/*  234:     */         case 'G': 
/*  235:     */         case 'H': 
/*  236:     */         case 'I': 
/*  237:     */         case 'J': 
/*  238:     */         case 'K': 
/*  239:     */         case 'L': 
/*  240:     */         case 'M': 
/*  241:     */         case 'N': 
/*  242:     */         case 'O': 
/*  243:     */         case 'P': 
/*  244:     */         case 'Q': 
/*  245:     */         case 'R': 
/*  246:     */         case 'S': 
/*  247:     */         case 'T': 
/*  248:     */         case 'U': 
/*  249:     */         case 'V': 
/*  250:     */         case 'W': 
/*  251:     */         case 'X': 
/*  252:     */         case 'Y': 
/*  253:     */         case 'Z': 
/*  254: 284 */           mTOKEN_REF(true);
/*  255: 285 */           localToken = this._returnToken;
/*  256: 286 */           break;
/*  257:     */         case 'a': 
/*  258:     */         case 'b': 
/*  259:     */         case 'c': 
/*  260:     */         case 'd': 
/*  261:     */         case 'e': 
/*  262:     */         case 'f': 
/*  263:     */         case 'g': 
/*  264:     */         case 'h': 
/*  265:     */         case 'i': 
/*  266:     */         case 'j': 
/*  267:     */         case 'k': 
/*  268:     */         case 'l': 
/*  269:     */         case 'm': 
/*  270:     */         case 'n': 
/*  271:     */         case 'o': 
/*  272:     */         case 'p': 
/*  273:     */         case 'q': 
/*  274:     */         case 'r': 
/*  275:     */         case 's': 
/*  276:     */         case 't': 
/*  277:     */         case 'u': 
/*  278:     */         case 'v': 
/*  279:     */         case 'w': 
/*  280:     */         case 'x': 
/*  281:     */         case 'y': 
/*  282:     */         case 'z': 
/*  283: 296 */           mRULE_REF(true);
/*  284: 297 */           localToken = this._returnToken;
/*  285: 298 */           break;
/*  286:     */         case '\013': 
/*  287:     */         case '\f': 
/*  288:     */         case '\016': 
/*  289:     */         case '\017': 
/*  290:     */         case '\020': 
/*  291:     */         case '\021': 
/*  292:     */         case '\022': 
/*  293:     */         case '\023': 
/*  294:     */         case '\024': 
/*  295:     */         case '\025': 
/*  296:     */         case '\026': 
/*  297:     */         case '\027': 
/*  298:     */         case '\030': 
/*  299:     */         case '\031': 
/*  300:     */         case '\032': 
/*  301:     */         case '\033': 
/*  302:     */         case '\034': 
/*  303:     */         case '\035': 
/*  304:     */         case '\036': 
/*  305:     */         case '\037': 
/*  306:     */         case '$': 
/*  307:     */         case '%': 
/*  308:     */         case '&': 
/*  309:     */         case '-': 
/*  310:     */         case '.': 
/*  311:     */         case '=': 
/*  312:     */         case '@': 
/*  313:     */         case '\\': 
/*  314:     */         case ']': 
/*  315:     */         case '_': 
/*  316:     */         case '`': 
/*  317:     */         default: 
/*  318: 301 */           if ((LA(1) == '=') && (LA(2) == '>'))
/*  319:     */           {
/*  320: 302 */             mIMPLIES(true);
/*  321: 303 */             localToken = this._returnToken;
/*  322:     */           }
/*  323: 305 */           else if ((LA(1) == '.') && (LA(2) == '.'))
/*  324:     */           {
/*  325: 306 */             mRANGE(true);
/*  326: 307 */             localToken = this._returnToken;
/*  327:     */           }
/*  328: 309 */           else if (LA(1) == '=')
/*  329:     */           {
/*  330: 310 */             mASSIGN(true);
/*  331: 311 */             localToken = this._returnToken;
/*  332:     */           }
/*  333: 313 */           else if (LA(1) == '.')
/*  334:     */           {
/*  335: 314 */             mWILDCARD(true);
/*  336: 315 */             localToken = this._returnToken;
/*  337:     */           }
/*  338: 318 */           else if (LA(1) == 65535)
/*  339:     */           {
/*  340: 318 */             uponEOF();this._returnToken = makeToken(1);
/*  341:     */           }
/*  342:     */           else
/*  343:     */           {
/*  344: 319 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  345:     */           }
/*  346:     */           break;
/*  347:     */         }
/*  348: 322 */         if (this._returnToken == null) {
/*  349:     */           continue;
/*  350:     */         }
/*  351: 323 */         i = this._returnToken.getType();
/*  352: 324 */         this._returnToken.setType(i);
/*  353: 325 */         return this._returnToken;
/*  354:     */       }
/*  355:     */       catch (RecognitionException localRecognitionException)
/*  356:     */       {
/*  357: 328 */         throw new TokenStreamRecognitionException(localRecognitionException);
/*  358:     */       }
/*  359:     */       catch (CharStreamException localCharStreamException)
/*  360:     */       {
/*  361: 332 */         if ((localCharStreamException instanceof CharStreamIOException)) {
/*  362: 333 */           throw new TokenStreamIOException(((CharStreamIOException)localCharStreamException).io);
/*  363:     */         }
/*  364: 336 */         throw new TokenStreamException(localCharStreamException.getMessage());
/*  365:     */       }
/*  366:     */     }
/*  367:     */   }
/*  368:     */   
/*  369:     */   public final void mWS(boolean paramBoolean)
/*  370:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  371:     */   {
/*  372: 343 */     Token localToken = null;int j = this.text.length();
/*  373: 344 */     int i = 52;
/*  374: 348 */     switch (LA(1))
/*  375:     */     {
/*  376:     */     case ' ': 
/*  377: 351 */       match(' ');
/*  378: 352 */       break;
/*  379:     */     case '\t': 
/*  380: 356 */       match('\t');
/*  381: 357 */       break;
/*  382:     */     case '\n': 
/*  383: 361 */       match('\n');
/*  384: 362 */       newline();
/*  385: 363 */       break;
/*  386:     */     default: 
/*  387: 366 */       if ((LA(1) == '\r') && (LA(2) == '\n'))
/*  388:     */       {
/*  389: 367 */         match('\r');
/*  390: 368 */         match('\n');
/*  391: 369 */         newline();
/*  392:     */       }
/*  393: 371 */       else if (LA(1) == '\r')
/*  394:     */       {
/*  395: 372 */         match('\r');
/*  396: 373 */         newline();
/*  397:     */       }
/*  398:     */       else
/*  399:     */       {
/*  400: 376 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  401:     */       }
/*  402:     */       break;
/*  403:     */     }
/*  404: 380 */     i = -1;
/*  405: 381 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/*  406:     */     {
/*  407: 382 */       localToken = makeToken(i);
/*  408: 383 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  409:     */     }
/*  410: 385 */     this._returnToken = localToken;
/*  411:     */   }
/*  412:     */   
/*  413:     */   public final void mCOMMENT(boolean paramBoolean)
/*  414:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  415:     */   {
/*  416: 389 */     Token localToken1 = null;int j = this.text.length();
/*  417: 390 */     int i = 53;
/*  418:     */     
/*  419: 392 */     Token localToken2 = null;
/*  420: 395 */     if ((LA(1) == '/') && (LA(2) == '/'))
/*  421:     */     {
/*  422: 396 */       mSL_COMMENT(false);
/*  423:     */     }
/*  424: 398 */     else if ((LA(1) == '/') && (LA(2) == '*'))
/*  425:     */     {
/*  426: 399 */       mML_COMMENT(true);
/*  427: 400 */       localToken2 = this._returnToken;
/*  428: 401 */       i = localToken2.getType();
/*  429:     */     }
/*  430:     */     else
/*  431:     */     {
/*  432: 404 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  433:     */     }
/*  434: 408 */     if (i != 8) {
/*  435: 408 */       i = -1;
/*  436:     */     }
/*  437: 409 */     if ((paramBoolean) && (localToken1 == null) && (i != -1))
/*  438:     */     {
/*  439: 410 */       localToken1 = makeToken(i);
/*  440: 411 */       localToken1.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  441:     */     }
/*  442: 413 */     this._returnToken = localToken1;
/*  443:     */   }
/*  444:     */   
/*  445:     */   protected final void mSL_COMMENT(boolean paramBoolean)
/*  446:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  447:     */   {
/*  448: 417 */     Token localToken = null;int j = this.text.length();
/*  449: 418 */     int i = 54;
/*  450:     */     
/*  451:     */ 
/*  452: 421 */     match("//");
/*  453: 425 */     while (_tokenSet_0.member(LA(1))) {
/*  454: 427 */       match(_tokenSet_0);
/*  455:     */     }
/*  456: 437 */     if ((LA(1) == '\r') && (LA(2) == '\n'))
/*  457:     */     {
/*  458: 438 */       match('\r');
/*  459: 439 */       match('\n');
/*  460:     */     }
/*  461: 441 */     else if (LA(1) == '\r')
/*  462:     */     {
/*  463: 442 */       match('\r');
/*  464:     */     }
/*  465: 444 */     else if (LA(1) == '\n')
/*  466:     */     {
/*  467: 445 */       match('\n');
/*  468:     */     }
/*  469:     */     else
/*  470:     */     {
/*  471: 448 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  472:     */     }
/*  473: 452 */     newline();
/*  474: 453 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/*  475:     */     {
/*  476: 454 */       localToken = makeToken(i);
/*  477: 455 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  478:     */     }
/*  479: 457 */     this._returnToken = localToken;
/*  480:     */   }
/*  481:     */   
/*  482:     */   protected final void mML_COMMENT(boolean paramBoolean)
/*  483:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  484:     */   {
/*  485: 461 */     Token localToken = null;int j = this.text.length();
/*  486: 462 */     int i = 55;
/*  487:     */     
/*  488:     */ 
/*  489: 465 */     match("/*");
/*  490: 467 */     if ((LA(1) == '*') && (LA(2) >= '\003') && (LA(2) <= 'ÿ') && (LA(2) != '/'))
/*  491:     */     {
/*  492: 468 */       match('*');
/*  493: 469 */       i = 8;
/*  494:     */     }
/*  495: 471 */     else if ((LA(1) < '\003') || (LA(1) > 'ÿ') || (LA(2) < '\003') || (LA(2) > 'ÿ'))
/*  496:     */     {
/*  497: 474 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  498:     */     }
/*  499: 482 */     while ((LA(1) != '*') || (LA(2) != '/')) {
/*  500: 483 */       if ((LA(1) == '\r') && (LA(2) == '\n'))
/*  501:     */       {
/*  502: 484 */         match('\r');
/*  503: 485 */         match('\n');
/*  504: 486 */         newline();
/*  505:     */       }
/*  506: 488 */       else if ((LA(1) == '\r') && (LA(2) >= '\003') && (LA(2) <= 'ÿ'))
/*  507:     */       {
/*  508: 489 */         match('\r');
/*  509: 490 */         newline();
/*  510:     */       }
/*  511: 492 */       else if ((_tokenSet_0.member(LA(1))) && (LA(2) >= '\003') && (LA(2) <= 'ÿ'))
/*  512:     */       {
/*  513: 494 */         match(_tokenSet_0);
/*  514:     */       }
/*  515:     */       else
/*  516:     */       {
/*  517: 497 */         if (LA(1) != '\n') {
/*  518:     */           break;
/*  519:     */         }
/*  520: 498 */         match('\n');
/*  521: 499 */         newline();
/*  522:     */       }
/*  523:     */     }
/*  524: 507 */     match("*/");
/*  525: 508 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/*  526:     */     {
/*  527: 509 */       localToken = makeToken(i);
/*  528: 510 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  529:     */     }
/*  530: 512 */     this._returnToken = localToken;
/*  531:     */   }
/*  532:     */   
/*  533:     */   public final void mOPEN_ELEMENT_OPTION(boolean paramBoolean)
/*  534:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  535:     */   {
/*  536: 516 */     Token localToken = null;int j = this.text.length();
/*  537: 517 */     int i = 25;
/*  538:     */     
/*  539:     */ 
/*  540: 520 */     match('<');
/*  541: 521 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/*  542:     */     {
/*  543: 522 */       localToken = makeToken(i);
/*  544: 523 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  545:     */     }
/*  546: 525 */     this._returnToken = localToken;
/*  547:     */   }
/*  548:     */   
/*  549:     */   public final void mCLOSE_ELEMENT_OPTION(boolean paramBoolean)
/*  550:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  551:     */   {
/*  552: 529 */     Token localToken = null;int j = this.text.length();
/*  553: 530 */     int i = 26;
/*  554:     */     
/*  555:     */ 
/*  556: 533 */     match('>');
/*  557: 534 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/*  558:     */     {
/*  559: 535 */       localToken = makeToken(i);
/*  560: 536 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  561:     */     }
/*  562: 538 */     this._returnToken = localToken;
/*  563:     */   }
/*  564:     */   
/*  565:     */   public final void mCOMMA(boolean paramBoolean)
/*  566:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  567:     */   {
/*  568: 542 */     Token localToken = null;int j = this.text.length();
/*  569: 543 */     int i = 38;
/*  570:     */     
/*  571:     */ 
/*  572: 546 */     match(',');
/*  573: 547 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/*  574:     */     {
/*  575: 548 */       localToken = makeToken(i);
/*  576: 549 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  577:     */     }
/*  578: 551 */     this._returnToken = localToken;
/*  579:     */   }
/*  580:     */   
/*  581:     */   public final void mQUESTION(boolean paramBoolean)
/*  582:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  583:     */   {
/*  584: 555 */     Token localToken = null;int j = this.text.length();
/*  585: 556 */     int i = 45;
/*  586:     */     
/*  587:     */ 
/*  588: 559 */     match('?');
/*  589: 560 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/*  590:     */     {
/*  591: 561 */       localToken = makeToken(i);
/*  592: 562 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  593:     */     }
/*  594: 564 */     this._returnToken = localToken;
/*  595:     */   }
/*  596:     */   
/*  597:     */   public final void mTREE_BEGIN(boolean paramBoolean)
/*  598:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  599:     */   {
/*  600: 568 */     Token localToken = null;int j = this.text.length();
/*  601: 569 */     int i = 44;
/*  602:     */     
/*  603:     */ 
/*  604: 572 */     match("#(");
/*  605: 573 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/*  606:     */     {
/*  607: 574 */       localToken = makeToken(i);
/*  608: 575 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  609:     */     }
/*  610: 577 */     this._returnToken = localToken;
/*  611:     */   }
/*  612:     */   
/*  613:     */   public final void mLPAREN(boolean paramBoolean)
/*  614:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  615:     */   {
/*  616: 581 */     Token localToken = null;int j = this.text.length();
/*  617: 582 */     int i = 27;
/*  618:     */     
/*  619:     */ 
/*  620: 585 */     match('(');
/*  621: 586 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/*  622:     */     {
/*  623: 587 */       localToken = makeToken(i);
/*  624: 588 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  625:     */     }
/*  626: 590 */     this._returnToken = localToken;
/*  627:     */   }
/*  628:     */   
/*  629:     */   public final void mRPAREN(boolean paramBoolean)
/*  630:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  631:     */   {
/*  632: 594 */     Token localToken = null;int j = this.text.length();
/*  633: 595 */     int i = 28;
/*  634:     */     
/*  635:     */ 
/*  636: 598 */     match(')');
/*  637: 599 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/*  638:     */     {
/*  639: 600 */       localToken = makeToken(i);
/*  640: 601 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  641:     */     }
/*  642: 603 */     this._returnToken = localToken;
/*  643:     */   }
/*  644:     */   
/*  645:     */   public final void mCOLON(boolean paramBoolean)
/*  646:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  647:     */   {
/*  648: 607 */     Token localToken = null;int j = this.text.length();
/*  649: 608 */     int i = 36;
/*  650:     */     
/*  651:     */ 
/*  652: 611 */     match(':');
/*  653: 612 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/*  654:     */     {
/*  655: 613 */       localToken = makeToken(i);
/*  656: 614 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  657:     */     }
/*  658: 616 */     this._returnToken = localToken;
/*  659:     */   }
/*  660:     */   
/*  661:     */   public final void mSTAR(boolean paramBoolean)
/*  662:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  663:     */   {
/*  664: 620 */     Token localToken = null;int j = this.text.length();
/*  665: 621 */     int i = 46;
/*  666:     */     
/*  667:     */ 
/*  668: 624 */     match('*');
/*  669: 625 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/*  670:     */     {
/*  671: 626 */       localToken = makeToken(i);
/*  672: 627 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  673:     */     }
/*  674: 629 */     this._returnToken = localToken;
/*  675:     */   }
/*  676:     */   
/*  677:     */   public final void mPLUS(boolean paramBoolean)
/*  678:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  679:     */   {
/*  680: 633 */     Token localToken = null;int j = this.text.length();
/*  681: 634 */     int i = 47;
/*  682:     */     
/*  683:     */ 
/*  684: 637 */     match('+');
/*  685: 638 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/*  686:     */     {
/*  687: 639 */       localToken = makeToken(i);
/*  688: 640 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  689:     */     }
/*  690: 642 */     this._returnToken = localToken;
/*  691:     */   }
/*  692:     */   
/*  693:     */   public final void mASSIGN(boolean paramBoolean)
/*  694:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  695:     */   {
/*  696: 646 */     Token localToken = null;int j = this.text.length();
/*  697: 647 */     int i = 15;
/*  698:     */     
/*  699:     */ 
/*  700: 650 */     match('=');
/*  701: 651 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/*  702:     */     {
/*  703: 652 */       localToken = makeToken(i);
/*  704: 653 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  705:     */     }
/*  706: 655 */     this._returnToken = localToken;
/*  707:     */   }
/*  708:     */   
/*  709:     */   public final void mIMPLIES(boolean paramBoolean)
/*  710:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  711:     */   {
/*  712: 659 */     Token localToken = null;int j = this.text.length();
/*  713: 660 */     int i = 48;
/*  714:     */     
/*  715:     */ 
/*  716: 663 */     match("=>");
/*  717: 664 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/*  718:     */     {
/*  719: 665 */       localToken = makeToken(i);
/*  720: 666 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  721:     */     }
/*  722: 668 */     this._returnToken = localToken;
/*  723:     */   }
/*  724:     */   
/*  725:     */   public final void mSEMI(boolean paramBoolean)
/*  726:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  727:     */   {
/*  728: 672 */     Token localToken = null;int j = this.text.length();
/*  729: 673 */     int i = 16;
/*  730:     */     
/*  731:     */ 
/*  732: 676 */     match(';');
/*  733: 677 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/*  734:     */     {
/*  735: 678 */       localToken = makeToken(i);
/*  736: 679 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  737:     */     }
/*  738: 681 */     this._returnToken = localToken;
/*  739:     */   }
/*  740:     */   
/*  741:     */   public final void mCARET(boolean paramBoolean)
/*  742:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  743:     */   {
/*  744: 685 */     Token localToken = null;int j = this.text.length();
/*  745: 686 */     int i = 49;
/*  746:     */     
/*  747:     */ 
/*  748: 689 */     match('^');
/*  749: 690 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/*  750:     */     {
/*  751: 691 */       localToken = makeToken(i);
/*  752: 692 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  753:     */     }
/*  754: 694 */     this._returnToken = localToken;
/*  755:     */   }
/*  756:     */   
/*  757:     */   public final void mBANG(boolean paramBoolean)
/*  758:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  759:     */   {
/*  760: 698 */     Token localToken = null;int j = this.text.length();
/*  761: 699 */     int i = 33;
/*  762:     */     
/*  763:     */ 
/*  764: 702 */     match('!');
/*  765: 703 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/*  766:     */     {
/*  767: 704 */       localToken = makeToken(i);
/*  768: 705 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  769:     */     }
/*  770: 707 */     this._returnToken = localToken;
/*  771:     */   }
/*  772:     */   
/*  773:     */   public final void mOR(boolean paramBoolean)
/*  774:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  775:     */   {
/*  776: 711 */     Token localToken = null;int j = this.text.length();
/*  777: 712 */     int i = 21;
/*  778:     */     
/*  779:     */ 
/*  780: 715 */     match('|');
/*  781: 716 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/*  782:     */     {
/*  783: 717 */       localToken = makeToken(i);
/*  784: 718 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  785:     */     }
/*  786: 720 */     this._returnToken = localToken;
/*  787:     */   }
/*  788:     */   
/*  789:     */   public final void mWILDCARD(boolean paramBoolean)
/*  790:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  791:     */   {
/*  792: 724 */     Token localToken = null;int j = this.text.length();
/*  793: 725 */     int i = 50;
/*  794:     */     
/*  795:     */ 
/*  796: 728 */     match('.');
/*  797: 729 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/*  798:     */     {
/*  799: 730 */       localToken = makeToken(i);
/*  800: 731 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  801:     */     }
/*  802: 733 */     this._returnToken = localToken;
/*  803:     */   }
/*  804:     */   
/*  805:     */   public final void mRANGE(boolean paramBoolean)
/*  806:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  807:     */   {
/*  808: 737 */     Token localToken = null;int j = this.text.length();
/*  809: 738 */     int i = 22;
/*  810:     */     
/*  811:     */ 
/*  812: 741 */     match("..");
/*  813: 742 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/*  814:     */     {
/*  815: 743 */       localToken = makeToken(i);
/*  816: 744 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  817:     */     }
/*  818: 746 */     this._returnToken = localToken;
/*  819:     */   }
/*  820:     */   
/*  821:     */   public final void mNOT_OP(boolean paramBoolean)
/*  822:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  823:     */   {
/*  824: 750 */     Token localToken = null;int j = this.text.length();
/*  825: 751 */     int i = 42;
/*  826:     */     
/*  827:     */ 
/*  828: 754 */     match('~');
/*  829: 755 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/*  830:     */     {
/*  831: 756 */       localToken = makeToken(i);
/*  832: 757 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  833:     */     }
/*  834: 759 */     this._returnToken = localToken;
/*  835:     */   }
/*  836:     */   
/*  837:     */   public final void mRCURLY(boolean paramBoolean)
/*  838:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  839:     */   {
/*  840: 763 */     Token localToken = null;int j = this.text.length();
/*  841: 764 */     int i = 17;
/*  842:     */     
/*  843:     */ 
/*  844: 767 */     match('}');
/*  845: 768 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/*  846:     */     {
/*  847: 769 */       localToken = makeToken(i);
/*  848: 770 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  849:     */     }
/*  850: 772 */     this._returnToken = localToken;
/*  851:     */   }
/*  852:     */   
/*  853:     */   public final void mCHAR_LITERAL(boolean paramBoolean)
/*  854:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  855:     */   {
/*  856: 776 */     Token localToken = null;int j = this.text.length();
/*  857: 777 */     int i = 19;
/*  858:     */     
/*  859:     */ 
/*  860: 780 */     match('\'');
/*  861: 782 */     if (LA(1) == '\\') {
/*  862: 783 */       mESC(false);
/*  863: 785 */     } else if (_tokenSet_1.member(LA(1))) {
/*  864: 786 */       matchNot('\'');
/*  865:     */     } else {
/*  866: 789 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  867:     */     }
/*  868: 793 */     match('\'');
/*  869: 794 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/*  870:     */     {
/*  871: 795 */       localToken = makeToken(i);
/*  872: 796 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  873:     */     }
/*  874: 798 */     this._returnToken = localToken;
/*  875:     */   }
/*  876:     */   
/*  877:     */   protected final void mESC(boolean paramBoolean)
/*  878:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  879:     */   {
/*  880: 802 */     Token localToken = null;int j = this.text.length();
/*  881: 803 */     int i = 56;
/*  882:     */     
/*  883:     */ 
/*  884: 806 */     match('\\');
/*  885: 808 */     switch (LA(1))
/*  886:     */     {
/*  887:     */     case 'n': 
/*  888: 811 */       match('n');
/*  889: 812 */       break;
/*  890:     */     case 'r': 
/*  891: 816 */       match('r');
/*  892: 817 */       break;
/*  893:     */     case 't': 
/*  894: 821 */       match('t');
/*  895: 822 */       break;
/*  896:     */     case 'b': 
/*  897: 826 */       match('b');
/*  898: 827 */       break;
/*  899:     */     case 'f': 
/*  900: 831 */       match('f');
/*  901: 832 */       break;
/*  902:     */     case 'w': 
/*  903: 836 */       match('w');
/*  904: 837 */       break;
/*  905:     */     case 'a': 
/*  906: 841 */       match('a');
/*  907: 842 */       break;
/*  908:     */     case '"': 
/*  909: 846 */       match('"');
/*  910: 847 */       break;
/*  911:     */     case '\'': 
/*  912: 851 */       match('\'');
/*  913: 852 */       break;
/*  914:     */     case '\\': 
/*  915: 856 */       match('\\');
/*  916: 857 */       break;
/*  917:     */     case '0': 
/*  918:     */     case '1': 
/*  919:     */     case '2': 
/*  920:     */     case '3': 
/*  921: 862 */       matchRange('0', '3');
/*  922: 865 */       if ((LA(1) >= '0') && (LA(1) <= '7') && (LA(2) >= '\003') && (LA(2) <= 'ÿ'))
/*  923:     */       {
/*  924: 866 */         matchRange('0', '7');
/*  925: 868 */         if ((LA(1) >= '0') && (LA(1) <= '7') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/*  926: 869 */           matchRange('0', '7');
/*  927: 871 */         } else if ((LA(1) < '\003') || (LA(1) > 'ÿ')) {
/*  928: 874 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  929:     */         }
/*  930:     */       }
/*  931: 879 */       else if ((LA(1) < '\003') || (LA(1) > 'ÿ'))
/*  932:     */       {
/*  933: 882 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  934:     */       }
/*  935:     */       break;
/*  936:     */     case '4': 
/*  937:     */     case '5': 
/*  938:     */     case '6': 
/*  939:     */     case '7': 
/*  940: 891 */       matchRange('4', '7');
/*  941: 894 */       if ((LA(1) >= '0') && (LA(1) <= '7') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/*  942: 895 */         matchRange('0', '7');
/*  943: 897 */       } else if ((LA(1) < '\003') || (LA(1) > 'ÿ')) {
/*  944: 900 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  945:     */       }
/*  946:     */       break;
/*  947:     */     case 'u': 
/*  948: 908 */       match('u');
/*  949: 909 */       mXDIGIT(false);
/*  950: 910 */       mXDIGIT(false);
/*  951: 911 */       mXDIGIT(false);
/*  952: 912 */       mXDIGIT(false);
/*  953: 913 */       break;
/*  954:     */     default: 
/*  955: 917 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  956:     */     }
/*  957: 921 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/*  958:     */     {
/*  959: 922 */       localToken = makeToken(i);
/*  960: 923 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  961:     */     }
/*  962: 925 */     this._returnToken = localToken;
/*  963:     */   }
/*  964:     */   
/*  965:     */   public final void mSTRING_LITERAL(boolean paramBoolean)
/*  966:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  967:     */   {
/*  968: 929 */     Token localToken = null;int j = this.text.length();
/*  969: 930 */     int i = 6;
/*  970:     */     
/*  971:     */ 
/*  972: 933 */     match('"');
/*  973:     */     for (;;)
/*  974:     */     {
/*  975: 937 */       if (LA(1) == '\\')
/*  976:     */       {
/*  977: 938 */         mESC(false);
/*  978:     */       }
/*  979:     */       else
/*  980:     */       {
/*  981: 940 */         if (!_tokenSet_2.member(LA(1))) {
/*  982:     */           break;
/*  983:     */         }
/*  984: 941 */         matchNot('"');
/*  985:     */       }
/*  986:     */     }
/*  987: 949 */     match('"');
/*  988: 950 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/*  989:     */     {
/*  990: 951 */       localToken = makeToken(i);
/*  991: 952 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  992:     */     }
/*  993: 954 */     this._returnToken = localToken;
/*  994:     */   }
/*  995:     */   
/*  996:     */   protected final void mXDIGIT(boolean paramBoolean)
/*  997:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  998:     */   {
/*  999: 958 */     Token localToken = null;int j = this.text.length();
/* 1000: 959 */     int i = 58;
/* 1001: 962 */     switch (LA(1))
/* 1002:     */     {
/* 1003:     */     case '0': 
/* 1004:     */     case '1': 
/* 1005:     */     case '2': 
/* 1006:     */     case '3': 
/* 1007:     */     case '4': 
/* 1008:     */     case '5': 
/* 1009:     */     case '6': 
/* 1010:     */     case '7': 
/* 1011:     */     case '8': 
/* 1012:     */     case '9': 
/* 1013: 967 */       matchRange('0', '9');
/* 1014: 968 */       break;
/* 1015:     */     case 'a': 
/* 1016:     */     case 'b': 
/* 1017:     */     case 'c': 
/* 1018:     */     case 'd': 
/* 1019:     */     case 'e': 
/* 1020:     */     case 'f': 
/* 1021: 973 */       matchRange('a', 'f');
/* 1022: 974 */       break;
/* 1023:     */     case 'A': 
/* 1024:     */     case 'B': 
/* 1025:     */     case 'C': 
/* 1026:     */     case 'D': 
/* 1027:     */     case 'E': 
/* 1028:     */     case 'F': 
/* 1029: 979 */       matchRange('A', 'F');
/* 1030: 980 */       break;
/* 1031:     */     case ':': 
/* 1032:     */     case ';': 
/* 1033:     */     case '<': 
/* 1034:     */     case '=': 
/* 1035:     */     case '>': 
/* 1036:     */     case '?': 
/* 1037:     */     case '@': 
/* 1038:     */     case 'G': 
/* 1039:     */     case 'H': 
/* 1040:     */     case 'I': 
/* 1041:     */     case 'J': 
/* 1042:     */     case 'K': 
/* 1043:     */     case 'L': 
/* 1044:     */     case 'M': 
/* 1045:     */     case 'N': 
/* 1046:     */     case 'O': 
/* 1047:     */     case 'P': 
/* 1048:     */     case 'Q': 
/* 1049:     */     case 'R': 
/* 1050:     */     case 'S': 
/* 1051:     */     case 'T': 
/* 1052:     */     case 'U': 
/* 1053:     */     case 'V': 
/* 1054:     */     case 'W': 
/* 1055:     */     case 'X': 
/* 1056:     */     case 'Y': 
/* 1057:     */     case 'Z': 
/* 1058:     */     case '[': 
/* 1059:     */     case '\\': 
/* 1060:     */     case ']': 
/* 1061:     */     case '^': 
/* 1062:     */     case '_': 
/* 1063:     */     case '`': 
/* 1064:     */     default: 
/* 1065: 984 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1066:     */     }
/* 1067: 987 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 1068:     */     {
/* 1069: 988 */       localToken = makeToken(i);
/* 1070: 989 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 1071:     */     }
/* 1072: 991 */     this._returnToken = localToken;
/* 1073:     */   }
/* 1074:     */   
/* 1075:     */   protected final void mDIGIT(boolean paramBoolean)
/* 1076:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 1077:     */   {
/* 1078: 995 */     Token localToken = null;int j = this.text.length();
/* 1079: 996 */     int i = 57;
/* 1080:     */     
/* 1081:     */ 
/* 1082: 999 */     matchRange('0', '9');
/* 1083:1000 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 1084:     */     {
/* 1085:1001 */       localToken = makeToken(i);
/* 1086:1002 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 1087:     */     }
/* 1088:1004 */     this._returnToken = localToken;
/* 1089:     */   }
/* 1090:     */   
/* 1091:     */   public final void mINT(boolean paramBoolean)
/* 1092:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 1093:     */   {
/* 1094:1008 */     Token localToken = null;int j = this.text.length();
/* 1095:1009 */     int i = 20;
/* 1096:     */     
/* 1097:     */ 
/* 1098:     */ 
/* 1099:1013 */     int k = 0;
/* 1100:     */     for (;;)
/* 1101:     */     {
/* 1102:1016 */       if ((LA(1) >= '0') && (LA(1) <= '9'))
/* 1103:     */       {
/* 1104:1017 */         matchRange('0', '9');
/* 1105:     */       }
/* 1106:     */       else
/* 1107:     */       {
/* 1108:1020 */         if (k >= 1) {
/* 1109:     */           break;
/* 1110:     */         }
/* 1111:1020 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1112:     */       }
/* 1113:1023 */       k++;
/* 1114:     */     }
/* 1115:1026 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 1116:     */     {
/* 1117:1027 */       localToken = makeToken(i);
/* 1118:1028 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 1119:     */     }
/* 1120:1030 */     this._returnToken = localToken;
/* 1121:     */   }
/* 1122:     */   
/* 1123:     */   public final void mARG_ACTION(boolean paramBoolean)
/* 1124:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 1125:     */   {
/* 1126:1034 */     Token localToken = null;int j = this.text.length();
/* 1127:1035 */     int i = 34;
/* 1128:     */     
/* 1129:     */ 
/* 1130:1038 */     mNESTED_ARG_ACTION(false);
/* 1131:1039 */     setText(StringUtils.stripFrontBack(getText(), "[", "]"));
/* 1132:1040 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 1133:     */     {
/* 1134:1041 */       localToken = makeToken(i);
/* 1135:1042 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 1136:     */     }
/* 1137:1044 */     this._returnToken = localToken;
/* 1138:     */   }
/* 1139:     */   
/* 1140:     */   protected final void mNESTED_ARG_ACTION(boolean paramBoolean)
/* 1141:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 1142:     */   {
/* 1143:1048 */     Token localToken = null;int j = this.text.length();
/* 1144:1049 */     int i = 59;
/* 1145:     */     
/* 1146:     */ 
/* 1147:1052 */     match('[');
/* 1148:     */     for (;;)
/* 1149:     */     {
/* 1150:1056 */       switch (LA(1))
/* 1151:     */       {
/* 1152:     */       case '[': 
/* 1153:1059 */         mNESTED_ARG_ACTION(false);
/* 1154:1060 */         break;
/* 1155:     */       case '\n': 
/* 1156:1064 */         match('\n');
/* 1157:1065 */         newline();
/* 1158:1066 */         break;
/* 1159:     */       case '\'': 
/* 1160:1070 */         mCHAR_LITERAL(false);
/* 1161:1071 */         break;
/* 1162:     */       case '"': 
/* 1163:1075 */         mSTRING_LITERAL(false);
/* 1164:1076 */         break;
/* 1165:     */       default: 
/* 1166:1079 */         if ((LA(1) == '\r') && (LA(2) == '\n'))
/* 1167:     */         {
/* 1168:1080 */           match('\r');
/* 1169:1081 */           match('\n');
/* 1170:1082 */           newline();
/* 1171:     */         }
/* 1172:1084 */         else if ((LA(1) == '\r') && (LA(2) >= '\003') && (LA(2) <= 'ÿ'))
/* 1173:     */         {
/* 1174:1085 */           match('\r');
/* 1175:1086 */           newline();
/* 1176:     */         }
/* 1177:     */         else
/* 1178:     */         {
/* 1179:1088 */           if (!_tokenSet_3.member(LA(1))) {
/* 1180:     */             break label210;
/* 1181:     */           }
/* 1182:1089 */           matchNot(']');
/* 1183:     */         }
/* 1184:     */         break;
/* 1185:     */       }
/* 1186:     */     }
/* 1187:     */     label210:
/* 1188:1097 */     match(']');
/* 1189:1098 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 1190:     */     {
/* 1191:1099 */       localToken = makeToken(i);
/* 1192:1100 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 1193:     */     }
/* 1194:1102 */     this._returnToken = localToken;
/* 1195:     */   }
/* 1196:     */   
/* 1197:     */   public final void mACTION(boolean paramBoolean)
/* 1198:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 1199:     */   {
/* 1200:1106 */     Object localObject = null;int j = this.text.length();
/* 1201:1107 */     int i = 7;
/* 1202:     */     
/* 1203:1109 */     int k = getLine();int m = getColumn();
/* 1204:     */     
/* 1205:1111 */     mNESTED_ACTION(false);
/* 1206:1113 */     if (LA(1) == '?')
/* 1207:     */     {
/* 1208:1114 */       match('?');
/* 1209:1115 */       i = 43;
/* 1210:     */     }
/* 1211:1122 */     if (i == 7) {
/* 1212:1123 */       setText(StringUtils.stripFrontBack(getText(), "{", "}"));
/* 1213:     */     } else {
/* 1214:1126 */       setText(StringUtils.stripFrontBack(getText(), "{", "}?"));
/* 1215:     */     }
/* 1216:1128 */     CommonToken localCommonToken = new CommonToken(i, new String(this.text.getBuffer(), j, this.text.length() - j));
/* 1217:1129 */     localCommonToken.setLine(k);
/* 1218:1130 */     localCommonToken.setColumn(m);
/* 1219:1131 */     localObject = localCommonToken;
/* 1220:1133 */     if ((paramBoolean) && (localObject == null) && (i != -1))
/* 1221:     */     {
/* 1222:1134 */       localObject = makeToken(i);
/* 1223:1135 */       ((Token)localObject).setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 1224:     */     }
/* 1225:1137 */     this._returnToken = ((Token)localObject);
/* 1226:     */   }
/* 1227:     */   
/* 1228:     */   protected final void mNESTED_ACTION(boolean paramBoolean)
/* 1229:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 1230:     */   {
/* 1231:1141 */     Token localToken = null;int j = this.text.length();
/* 1232:1142 */     int i = 60;
/* 1233:     */     
/* 1234:     */ 
/* 1235:1145 */     match('{');
/* 1236:1150 */     while (LA(1) != '}') {
/* 1237:1151 */       if (((LA(1) == '\n') || (LA(1) == '\r')) && (LA(2) >= '\003') && (LA(2) <= 'ÿ'))
/* 1238:     */       {
/* 1239:1153 */         if ((LA(1) == '\r') && (LA(2) == '\n'))
/* 1240:     */         {
/* 1241:1154 */           match('\r');
/* 1242:1155 */           match('\n');
/* 1243:1156 */           newline();
/* 1244:     */         }
/* 1245:1158 */         else if ((LA(1) == '\r') && (LA(2) >= '\003') && (LA(2) <= 'ÿ'))
/* 1246:     */         {
/* 1247:1159 */           match('\r');
/* 1248:1160 */           newline();
/* 1249:     */         }
/* 1250:1162 */         else if (LA(1) == '\n')
/* 1251:     */         {
/* 1252:1163 */           match('\n');
/* 1253:1164 */           newline();
/* 1254:     */         }
/* 1255:     */         else
/* 1256:     */         {
/* 1257:1167 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1258:     */         }
/* 1259:     */       }
/* 1260:1172 */       else if ((LA(1) == '{') && (LA(2) >= '\003') && (LA(2) <= 'ÿ'))
/* 1261:     */       {
/* 1262:1173 */         mNESTED_ACTION(false);
/* 1263:     */       }
/* 1264:1175 */       else if ((LA(1) == '\'') && (_tokenSet_4.member(LA(2))))
/* 1265:     */       {
/* 1266:1176 */         mCHAR_LITERAL(false);
/* 1267:     */       }
/* 1268:1178 */       else if ((LA(1) == '/') && ((LA(2) == '*') || (LA(2) == '/')))
/* 1269:     */       {
/* 1270:1179 */         mCOMMENT(false);
/* 1271:     */       }
/* 1272:1181 */       else if ((LA(1) == '"') && (LA(2) >= '\003') && (LA(2) <= 'ÿ'))
/* 1273:     */       {
/* 1274:1182 */         mSTRING_LITERAL(false);
/* 1275:     */       }
/* 1276:     */       else
/* 1277:     */       {
/* 1278:1184 */         if ((LA(1) < '\003') || (LA(1) > 'ÿ') || (LA(2) < '\003') || (LA(2) > 'ÿ')) {
/* 1279:     */           break;
/* 1280:     */         }
/* 1281:1185 */         matchNot(65535);
/* 1282:     */       }
/* 1283:     */     }
/* 1284:1193 */     match('}');
/* 1285:1194 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 1286:     */     {
/* 1287:1195 */       localToken = makeToken(i);
/* 1288:1196 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 1289:     */     }
/* 1290:1198 */     this._returnToken = localToken;
/* 1291:     */   }
/* 1292:     */   
/* 1293:     */   public final void mTOKEN_REF(boolean paramBoolean)
/* 1294:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 1295:     */   {
/* 1296:1202 */     Token localToken = null;int j = this.text.length();
/* 1297:1203 */     int i = 24;
/* 1298:     */     
/* 1299:     */ 
/* 1300:1206 */     matchRange('A', 'Z');
/* 1301:     */     for (;;)
/* 1302:     */     {
/* 1303:1210 */       switch (LA(1))
/* 1304:     */       {
/* 1305:     */       case 'a': 
/* 1306:     */       case 'b': 
/* 1307:     */       case 'c': 
/* 1308:     */       case 'd': 
/* 1309:     */       case 'e': 
/* 1310:     */       case 'f': 
/* 1311:     */       case 'g': 
/* 1312:     */       case 'h': 
/* 1313:     */       case 'i': 
/* 1314:     */       case 'j': 
/* 1315:     */       case 'k': 
/* 1316:     */       case 'l': 
/* 1317:     */       case 'm': 
/* 1318:     */       case 'n': 
/* 1319:     */       case 'o': 
/* 1320:     */       case 'p': 
/* 1321:     */       case 'q': 
/* 1322:     */       case 'r': 
/* 1323:     */       case 's': 
/* 1324:     */       case 't': 
/* 1325:     */       case 'u': 
/* 1326:     */       case 'v': 
/* 1327:     */       case 'w': 
/* 1328:     */       case 'x': 
/* 1329:     */       case 'y': 
/* 1330:     */       case 'z': 
/* 1331:1219 */         matchRange('a', 'z');
/* 1332:1220 */         break;
/* 1333:     */       case 'A': 
/* 1334:     */       case 'B': 
/* 1335:     */       case 'C': 
/* 1336:     */       case 'D': 
/* 1337:     */       case 'E': 
/* 1338:     */       case 'F': 
/* 1339:     */       case 'G': 
/* 1340:     */       case 'H': 
/* 1341:     */       case 'I': 
/* 1342:     */       case 'J': 
/* 1343:     */       case 'K': 
/* 1344:     */       case 'L': 
/* 1345:     */       case 'M': 
/* 1346:     */       case 'N': 
/* 1347:     */       case 'O': 
/* 1348:     */       case 'P': 
/* 1349:     */       case 'Q': 
/* 1350:     */       case 'R': 
/* 1351:     */       case 'S': 
/* 1352:     */       case 'T': 
/* 1353:     */       case 'U': 
/* 1354:     */       case 'V': 
/* 1355:     */       case 'W': 
/* 1356:     */       case 'X': 
/* 1357:     */       case 'Y': 
/* 1358:     */       case 'Z': 
/* 1359:1230 */         matchRange('A', 'Z');
/* 1360:1231 */         break;
/* 1361:     */       case '_': 
/* 1362:1235 */         match('_');
/* 1363:1236 */         break;
/* 1364:     */       case '0': 
/* 1365:     */       case '1': 
/* 1366:     */       case '2': 
/* 1367:     */       case '3': 
/* 1368:     */       case '4': 
/* 1369:     */       case '5': 
/* 1370:     */       case '6': 
/* 1371:     */       case '7': 
/* 1372:     */       case '8': 
/* 1373:     */       case '9': 
/* 1374:1242 */         matchRange('0', '9');
/* 1375:     */       }
/* 1376:     */     }
/* 1377:1252 */     i = testLiteralsTable(i);
/* 1378:1253 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 1379:     */     {
/* 1380:1254 */       localToken = makeToken(i);
/* 1381:1255 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 1382:     */     }
/* 1383:1257 */     this._returnToken = localToken;
/* 1384:     */   }
/* 1385:     */   
/* 1386:     */   public final void mRULE_REF(boolean paramBoolean)
/* 1387:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 1388:     */   {
/* 1389:1261 */     Token localToken = null;int j = this.text.length();
/* 1390:1262 */     int i = 41;
/* 1391:     */     
/* 1392:     */ 
/* 1393:1265 */     int k = 0;
/* 1394:     */     
/* 1395:     */ 
/* 1396:1268 */     k = mINTERNAL_RULE_REF(false);
/* 1397:1269 */     i = k;
/* 1398:1271 */     if (k == 51)
/* 1399:     */     {
/* 1400:1272 */       mWS_LOOP(false);
/* 1401:1274 */       if (LA(1) == '{')
/* 1402:     */       {
/* 1403:1275 */         match('{');
/* 1404:1276 */         i = 14;
/* 1405:     */       }
/* 1406:     */     }
/* 1407:1283 */     else if (k == 4)
/* 1408:     */     {
/* 1409:1284 */       mWS_LOOP(false);
/* 1410:1286 */       if (LA(1) == '{')
/* 1411:     */       {
/* 1412:1287 */         match('{');
/* 1413:1288 */         i = 23;
/* 1414:     */       }
/* 1415:     */     }
/* 1416:1299 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 1417:     */     {
/* 1418:1300 */       localToken = makeToken(i);
/* 1419:1301 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 1420:     */     }
/* 1421:1303 */     this._returnToken = localToken;
/* 1422:     */   }
/* 1423:     */   
/* 1424:     */   protected final int mINTERNAL_RULE_REF(boolean paramBoolean)
/* 1425:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 1426:     */   {
/* 1427:1308 */     Token localToken = null;int k = this.text.length();
/* 1428:1309 */     int j = 62;
/* 1429:     */     
/* 1430:     */ 
/* 1431:1312 */     int i = 41;
/* 1432:     */     
/* 1433:     */ 
/* 1434:1315 */     matchRange('a', 'z');
/* 1435:     */     for (;;)
/* 1436:     */     {
/* 1437:1319 */       switch (LA(1))
/* 1438:     */       {
/* 1439:     */       case 'a': 
/* 1440:     */       case 'b': 
/* 1441:     */       case 'c': 
/* 1442:     */       case 'd': 
/* 1443:     */       case 'e': 
/* 1444:     */       case 'f': 
/* 1445:     */       case 'g': 
/* 1446:     */       case 'h': 
/* 1447:     */       case 'i': 
/* 1448:     */       case 'j': 
/* 1449:     */       case 'k': 
/* 1450:     */       case 'l': 
/* 1451:     */       case 'm': 
/* 1452:     */       case 'n': 
/* 1453:     */       case 'o': 
/* 1454:     */       case 'p': 
/* 1455:     */       case 'q': 
/* 1456:     */       case 'r': 
/* 1457:     */       case 's': 
/* 1458:     */       case 't': 
/* 1459:     */       case 'u': 
/* 1460:     */       case 'v': 
/* 1461:     */       case 'w': 
/* 1462:     */       case 'x': 
/* 1463:     */       case 'y': 
/* 1464:     */       case 'z': 
/* 1465:1328 */         matchRange('a', 'z');
/* 1466:1329 */         break;
/* 1467:     */       case 'A': 
/* 1468:     */       case 'B': 
/* 1469:     */       case 'C': 
/* 1470:     */       case 'D': 
/* 1471:     */       case 'E': 
/* 1472:     */       case 'F': 
/* 1473:     */       case 'G': 
/* 1474:     */       case 'H': 
/* 1475:     */       case 'I': 
/* 1476:     */       case 'J': 
/* 1477:     */       case 'K': 
/* 1478:     */       case 'L': 
/* 1479:     */       case 'M': 
/* 1480:     */       case 'N': 
/* 1481:     */       case 'O': 
/* 1482:     */       case 'P': 
/* 1483:     */       case 'Q': 
/* 1484:     */       case 'R': 
/* 1485:     */       case 'S': 
/* 1486:     */       case 'T': 
/* 1487:     */       case 'U': 
/* 1488:     */       case 'V': 
/* 1489:     */       case 'W': 
/* 1490:     */       case 'X': 
/* 1491:     */       case 'Y': 
/* 1492:     */       case 'Z': 
/* 1493:1339 */         matchRange('A', 'Z');
/* 1494:1340 */         break;
/* 1495:     */       case '_': 
/* 1496:1344 */         match('_');
/* 1497:1345 */         break;
/* 1498:     */       case '0': 
/* 1499:     */       case '1': 
/* 1500:     */       case '2': 
/* 1501:     */       case '3': 
/* 1502:     */       case '4': 
/* 1503:     */       case '5': 
/* 1504:     */       case '6': 
/* 1505:     */       case '7': 
/* 1506:     */       case '8': 
/* 1507:     */       case '9': 
/* 1508:1351 */         matchRange('0', '9');
/* 1509:     */       }
/* 1510:     */     }
/* 1511:1361 */     i = testLiteralsTable(i);
/* 1512:1362 */     if ((paramBoolean) && (localToken == null) && (j != -1))
/* 1513:     */     {
/* 1514:1363 */       localToken = makeToken(j);
/* 1515:1364 */       localToken.setText(new String(this.text.getBuffer(), k, this.text.length() - k));
/* 1516:     */     }
/* 1517:1366 */     this._returnToken = localToken;
/* 1518:1367 */     return i;
/* 1519:     */   }
/* 1520:     */   
/* 1521:     */   protected final void mWS_LOOP(boolean paramBoolean)
/* 1522:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 1523:     */   {
/* 1524:1371 */     Token localToken = null;int j = this.text.length();
/* 1525:1372 */     int i = 61;
/* 1526:     */     for (;;)
/* 1527:     */     {
/* 1528:1378 */       switch (LA(1))
/* 1529:     */       {
/* 1530:     */       case '\t': 
/* 1531:     */       case '\n': 
/* 1532:     */       case '\r': 
/* 1533:     */       case ' ': 
/* 1534:1381 */         mWS(false);
/* 1535:1382 */         break;
/* 1536:     */       case '/': 
/* 1537:1386 */         mCOMMENT(false);
/* 1538:     */       }
/* 1539:     */     }
/* 1540:1396 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 1541:     */     {
/* 1542:1397 */       localToken = makeToken(i);
/* 1543:1398 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 1544:     */     }
/* 1545:1400 */     this._returnToken = localToken;
/* 1546:     */   }
/* 1547:     */   
/* 1548:     */   protected final void mWS_OPT(boolean paramBoolean)
/* 1549:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 1550:     */   {
/* 1551:1404 */     Token localToken = null;int j = this.text.length();
/* 1552:1405 */     int i = 63;
/* 1553:1409 */     if (_tokenSet_5.member(LA(1))) {
/* 1554:1410 */       mWS(false);
/* 1555:     */     }
/* 1556:1416 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 1557:     */     {
/* 1558:1417 */       localToken = makeToken(i);
/* 1559:1418 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 1560:     */     }
/* 1561:1420 */     this._returnToken = localToken;
/* 1562:     */   }
/* 1563:     */   
/* 1564:     */   private static final long[] mk_tokenSet_0()
/* 1565:     */   {
/* 1566:1425 */     long[] arrayOfLong = new long[8];
/* 1567:1426 */     arrayOfLong[0] = -9224L;
/* 1568:1427 */     for (int i = 1; i <= 3; i++) {
/* 1569:1427 */       arrayOfLong[i] = -1L;
/* 1570:     */     }
/* 1571:1428 */     return arrayOfLong;
/* 1572:     */   }
/* 1573:     */   
/* 1574:1430 */   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
/* 1575:     */   
/* 1576:     */   private static final long[] mk_tokenSet_1()
/* 1577:     */   {
/* 1578:1432 */     long[] arrayOfLong = new long[8];
/* 1579:1433 */     arrayOfLong[0] = -549755813896L;
/* 1580:1434 */     arrayOfLong[1] = -268435457L;
/* 1581:1435 */     for (int i = 2; i <= 3; i++) {
/* 1582:1435 */       arrayOfLong[i] = -1L;
/* 1583:     */     }
/* 1584:1436 */     return arrayOfLong;
/* 1585:     */   }
/* 1586:     */   
/* 1587:1438 */   public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
/* 1588:     */   
/* 1589:     */   private static final long[] mk_tokenSet_2()
/* 1590:     */   {
/* 1591:1440 */     long[] arrayOfLong = new long[8];
/* 1592:1441 */     arrayOfLong[0] = -17179869192L;
/* 1593:1442 */     arrayOfLong[1] = -268435457L;
/* 1594:1443 */     for (int i = 2; i <= 3; i++) {
/* 1595:1443 */       arrayOfLong[i] = -1L;
/* 1596:     */     }
/* 1597:1444 */     return arrayOfLong;
/* 1598:     */   }
/* 1599:     */   
/* 1600:1446 */   public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
/* 1601:     */   
/* 1602:     */   private static final long[] mk_tokenSet_3()
/* 1603:     */   {
/* 1604:1448 */     long[] arrayOfLong = new long[8];
/* 1605:1449 */     arrayOfLong[0] = -566935692296L;
/* 1606:1450 */     arrayOfLong[1] = -671088641L;
/* 1607:1451 */     for (int i = 2; i <= 3; i++) {
/* 1608:1451 */       arrayOfLong[i] = -1L;
/* 1609:     */     }
/* 1610:1452 */     return arrayOfLong;
/* 1611:     */   }
/* 1612:     */   
/* 1613:1454 */   public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
/* 1614:     */   
/* 1615:     */   private static final long[] mk_tokenSet_4()
/* 1616:     */   {
/* 1617:1456 */     long[] arrayOfLong = new long[8];
/* 1618:1457 */     arrayOfLong[0] = -549755813896L;
/* 1619:1458 */     for (int i = 1; i <= 3; i++) {
/* 1620:1458 */       arrayOfLong[i] = -1L;
/* 1621:     */     }
/* 1622:1459 */     return arrayOfLong;
/* 1623:     */   }
/* 1624:     */   
/* 1625:1461 */   public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
/* 1626:     */   
/* 1627:     */   private static final long[] mk_tokenSet_5()
/* 1628:     */   {
/* 1629:1463 */     long[] arrayOfLong = { 4294977024L, 0L, 0L, 0L, 0L };
/* 1630:1464 */     return arrayOfLong;
/* 1631:     */   }
/* 1632:     */   
/* 1633:1466 */   public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
/* 1634:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.ANTLRLexer
 * JD-Core Version:    0.7.0.1
 */