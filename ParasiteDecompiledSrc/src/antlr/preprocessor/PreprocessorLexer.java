/*    1:     */ package antlr.preprocessor;
/*    2:     */ 
/*    3:     */ import antlr.ANTLRHashString;
/*    4:     */ import antlr.ANTLRStringBuffer;
/*    5:     */ import antlr.ByteBuffer;
/*    6:     */ import antlr.CharBuffer;
/*    7:     */ import antlr.CharScanner;
/*    8:     */ import antlr.CharStreamException;
/*    9:     */ import antlr.CharStreamIOException;
/*   10:     */ import antlr.InputBuffer;
/*   11:     */ import antlr.LexerSharedInputState;
/*   12:     */ import antlr.NoViableAltForCharException;
/*   13:     */ import antlr.RecognitionException;
/*   14:     */ import antlr.Token;
/*   15:     */ import antlr.TokenStream;
/*   16:     */ import antlr.TokenStreamException;
/*   17:     */ import antlr.TokenStreamIOException;
/*   18:     */ import antlr.TokenStreamRecognitionException;
/*   19:     */ import antlr.collections.impl.BitSet;
/*   20:     */ import java.io.InputStream;
/*   21:     */ import java.io.Reader;
/*   22:     */ import java.util.Hashtable;
/*   23:     */ 
/*   24:     */ public class PreprocessorLexer
/*   25:     */   extends CharScanner
/*   26:     */   implements PreprocessorTokenTypes, TokenStream
/*   27:     */ {
/*   28:     */   public PreprocessorLexer(InputStream paramInputStream)
/*   29:     */   {
/*   30:  32 */     this(new ByteBuffer(paramInputStream));
/*   31:     */   }
/*   32:     */   
/*   33:     */   public PreprocessorLexer(Reader paramReader)
/*   34:     */   {
/*   35:  35 */     this(new CharBuffer(paramReader));
/*   36:     */   }
/*   37:     */   
/*   38:     */   public PreprocessorLexer(InputBuffer paramInputBuffer)
/*   39:     */   {
/*   40:  38 */     this(new LexerSharedInputState(paramInputBuffer));
/*   41:     */   }
/*   42:     */   
/*   43:     */   public PreprocessorLexer(LexerSharedInputState paramLexerSharedInputState)
/*   44:     */   {
/*   45:  41 */     super(paramLexerSharedInputState);
/*   46:  42 */     this.caseSensitiveLiterals = true;
/*   47:  43 */     setCaseSensitive(true);
/*   48:  44 */     this.literals = new Hashtable();
/*   49:  45 */     this.literals.put(new ANTLRHashString("public", this), new Integer(18));
/*   50:  46 */     this.literals.put(new ANTLRHashString("class", this), new Integer(8));
/*   51:  47 */     this.literals.put(new ANTLRHashString("throws", this), new Integer(23));
/*   52:  48 */     this.literals.put(new ANTLRHashString("catch", this), new Integer(26));
/*   53:  49 */     this.literals.put(new ANTLRHashString("private", this), new Integer(17));
/*   54:  50 */     this.literals.put(new ANTLRHashString("extends", this), new Integer(10));
/*   55:  51 */     this.literals.put(new ANTLRHashString("protected", this), new Integer(16));
/*   56:  52 */     this.literals.put(new ANTLRHashString("returns", this), new Integer(21));
/*   57:  53 */     this.literals.put(new ANTLRHashString("tokens", this), new Integer(4));
/*   58:  54 */     this.literals.put(new ANTLRHashString("exception", this), new Integer(25));
/*   59:     */   }
/*   60:     */   
/*   61:     */   public Token nextToken()
/*   62:     */     throws TokenStreamException
/*   63:     */   {
/*   64:  58 */     Token localToken = null;
/*   65:     */     for (;;)
/*   66:     */     {
/*   67:  61 */       Object localObject = null;
/*   68:  62 */       int i = 0;
/*   69:  63 */       resetText();
/*   70:     */       try
/*   71:     */       {
/*   72:  66 */         switch (LA(1))
/*   73:     */         {
/*   74:     */         case ':': 
/*   75:  69 */           mRULE_BLOCK(true);
/*   76:  70 */           localToken = this._returnToken;
/*   77:  71 */           break;
/*   78:     */         case '\t': 
/*   79:     */         case '\n': 
/*   80:     */         case '\r': 
/*   81:     */         case ' ': 
/*   82:  75 */           mWS(true);
/*   83:  76 */           localToken = this._returnToken;
/*   84:  77 */           break;
/*   85:     */         case '/': 
/*   86:  81 */           mCOMMENT(true);
/*   87:  82 */           localToken = this._returnToken;
/*   88:  83 */           break;
/*   89:     */         case '{': 
/*   90:  87 */           mACTION(true);
/*   91:  88 */           localToken = this._returnToken;
/*   92:  89 */           break;
/*   93:     */         case '"': 
/*   94:  93 */           mSTRING_LITERAL(true);
/*   95:  94 */           localToken = this._returnToken;
/*   96:  95 */           break;
/*   97:     */         case '\'': 
/*   98:  99 */           mCHAR_LITERAL(true);
/*   99: 100 */           localToken = this._returnToken;
/*  100: 101 */           break;
/*  101:     */         case '!': 
/*  102: 105 */           mBANG(true);
/*  103: 106 */           localToken = this._returnToken;
/*  104: 107 */           break;
/*  105:     */         case ';': 
/*  106: 111 */           mSEMI(true);
/*  107: 112 */           localToken = this._returnToken;
/*  108: 113 */           break;
/*  109:     */         case ',': 
/*  110: 117 */           mCOMMA(true);
/*  111: 118 */           localToken = this._returnToken;
/*  112: 119 */           break;
/*  113:     */         case '}': 
/*  114: 123 */           mRCURLY(true);
/*  115: 124 */           localToken = this._returnToken;
/*  116: 125 */           break;
/*  117:     */         case ')': 
/*  118: 129 */           mRPAREN(true);
/*  119: 130 */           localToken = this._returnToken;
/*  120: 131 */           break;
/*  121:     */         case 'A': 
/*  122:     */         case 'B': 
/*  123:     */         case 'C': 
/*  124:     */         case 'D': 
/*  125:     */         case 'E': 
/*  126:     */         case 'F': 
/*  127:     */         case 'G': 
/*  128:     */         case 'H': 
/*  129:     */         case 'I': 
/*  130:     */         case 'J': 
/*  131:     */         case 'K': 
/*  132:     */         case 'L': 
/*  133:     */         case 'M': 
/*  134:     */         case 'N': 
/*  135:     */         case 'O': 
/*  136:     */         case 'P': 
/*  137:     */         case 'Q': 
/*  138:     */         case 'R': 
/*  139:     */         case 'S': 
/*  140:     */         case 'T': 
/*  141:     */         case 'U': 
/*  142:     */         case 'V': 
/*  143:     */         case 'W': 
/*  144:     */         case 'X': 
/*  145:     */         case 'Y': 
/*  146:     */         case 'Z': 
/*  147:     */         case '_': 
/*  148:     */         case 'a': 
/*  149:     */         case 'b': 
/*  150:     */         case 'c': 
/*  151:     */         case 'd': 
/*  152:     */         case 'e': 
/*  153:     */         case 'f': 
/*  154:     */         case 'g': 
/*  155:     */         case 'h': 
/*  156:     */         case 'i': 
/*  157:     */         case 'j': 
/*  158:     */         case 'k': 
/*  159:     */         case 'l': 
/*  160:     */         case 'm': 
/*  161:     */         case 'n': 
/*  162:     */         case 'o': 
/*  163:     */         case 'p': 
/*  164:     */         case 'q': 
/*  165:     */         case 'r': 
/*  166:     */         case 's': 
/*  167:     */         case 't': 
/*  168:     */         case 'u': 
/*  169:     */         case 'v': 
/*  170:     */         case 'w': 
/*  171:     */         case 'x': 
/*  172:     */         case 'y': 
/*  173:     */         case 'z': 
/*  174: 148 */           mID_OR_KEYWORD(true);
/*  175: 149 */           localToken = this._returnToken;
/*  176: 150 */           break;
/*  177:     */         case '=': 
/*  178: 154 */           mASSIGN_RHS(true);
/*  179: 155 */           localToken = this._returnToken;
/*  180: 156 */           break;
/*  181:     */         case '[': 
/*  182: 160 */           mARG_ACTION(true);
/*  183: 161 */           localToken = this._returnToken;
/*  184: 162 */           break;
/*  185:     */         case '\013': 
/*  186:     */         case '\f': 
/*  187:     */         case '\016': 
/*  188:     */         case '\017': 
/*  189:     */         case '\020': 
/*  190:     */         case '\021': 
/*  191:     */         case '\022': 
/*  192:     */         case '\023': 
/*  193:     */         case '\024': 
/*  194:     */         case '\025': 
/*  195:     */         case '\026': 
/*  196:     */         case '\027': 
/*  197:     */         case '\030': 
/*  198:     */         case '\031': 
/*  199:     */         case '\032': 
/*  200:     */         case '\033': 
/*  201:     */         case '\034': 
/*  202:     */         case '\035': 
/*  203:     */         case '\036': 
/*  204:     */         case '\037': 
/*  205:     */         case '#': 
/*  206:     */         case '$': 
/*  207:     */         case '%': 
/*  208:     */         case '&': 
/*  209:     */         case '(': 
/*  210:     */         case '*': 
/*  211:     */         case '+': 
/*  212:     */         case '-': 
/*  213:     */         case '.': 
/*  214:     */         case '0': 
/*  215:     */         case '1': 
/*  216:     */         case '2': 
/*  217:     */         case '3': 
/*  218:     */         case '4': 
/*  219:     */         case '5': 
/*  220:     */         case '6': 
/*  221:     */         case '7': 
/*  222:     */         case '8': 
/*  223:     */         case '9': 
/*  224:     */         case '<': 
/*  225:     */         case '>': 
/*  226:     */         case '?': 
/*  227:     */         case '@': 
/*  228:     */         case '\\': 
/*  229:     */         case ']': 
/*  230:     */         case '^': 
/*  231:     */         case '`': 
/*  232:     */         case '|': 
/*  233:     */         default: 
/*  234: 165 */           if ((LA(1) == '(') && (_tokenSet_0.member(LA(2))))
/*  235:     */           {
/*  236: 166 */             mSUBRULE_BLOCK(true);
/*  237: 167 */             localToken = this._returnToken;
/*  238:     */           }
/*  239: 169 */           else if (LA(1) == '(')
/*  240:     */           {
/*  241: 170 */             mLPAREN(true);
/*  242: 171 */             localToken = this._returnToken;
/*  243:     */           }
/*  244: 174 */           else if (LA(1) == 65535)
/*  245:     */           {
/*  246: 174 */             uponEOF();this._returnToken = makeToken(1);
/*  247:     */           }
/*  248:     */           else
/*  249:     */           {
/*  250: 175 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  251:     */           }
/*  252:     */           break;
/*  253:     */         }
/*  254: 178 */         if (this._returnToken == null) {
/*  255:     */           continue;
/*  256:     */         }
/*  257: 179 */         i = this._returnToken.getType();
/*  258: 180 */         i = testLiteralsTable(i);
/*  259: 181 */         this._returnToken.setType(i);
/*  260: 182 */         return this._returnToken;
/*  261:     */       }
/*  262:     */       catch (RecognitionException localRecognitionException)
/*  263:     */       {
/*  264: 185 */         throw new TokenStreamRecognitionException(localRecognitionException);
/*  265:     */       }
/*  266:     */       catch (CharStreamException localCharStreamException)
/*  267:     */       {
/*  268: 189 */         if ((localCharStreamException instanceof CharStreamIOException)) {
/*  269: 190 */           throw new TokenStreamIOException(((CharStreamIOException)localCharStreamException).io);
/*  270:     */         }
/*  271: 193 */         throw new TokenStreamException(localCharStreamException.getMessage());
/*  272:     */       }
/*  273:     */     }
/*  274:     */   }
/*  275:     */   
/*  276:     */   public final void mRULE_BLOCK(boolean paramBoolean)
/*  277:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  278:     */   {
/*  279: 200 */     Token localToken = null;int j = this.text.length();
/*  280: 201 */     int i = 22;
/*  281:     */     
/*  282:     */ 
/*  283: 204 */     match(':');
/*  284:     */     int k;
/*  285: 206 */     if ((_tokenSet_1.member(LA(1))) && (_tokenSet_2.member(LA(2))))
/*  286:     */     {
/*  287: 207 */       k = this.text.length();
/*  288: 208 */       mWS(false);
/*  289: 209 */       this.text.setLength(k);
/*  290:     */     }
/*  291: 211 */     else if (!_tokenSet_2.member(LA(1)))
/*  292:     */     {
/*  293: 214 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  294:     */     }
/*  295: 218 */     mALT(false);
/*  296: 220 */     switch (LA(1))
/*  297:     */     {
/*  298:     */     case '\t': 
/*  299:     */     case '\n': 
/*  300:     */     case '\r': 
/*  301:     */     case ' ': 
/*  302: 223 */       k = this.text.length();
/*  303: 224 */       mWS(false);
/*  304: 225 */       this.text.setLength(k);
/*  305: 226 */       break;
/*  306:     */     case ';': 
/*  307:     */     case '|': 
/*  308:     */       break;
/*  309:     */     default: 
/*  310: 234 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  311:     */     }
/*  312: 241 */     while (LA(1) == '|')
/*  313:     */     {
/*  314: 242 */       match('|');
/*  315: 244 */       if ((_tokenSet_1.member(LA(1))) && (_tokenSet_2.member(LA(2))))
/*  316:     */       {
/*  317: 245 */         k = this.text.length();
/*  318: 246 */         mWS(false);
/*  319: 247 */         this.text.setLength(k);
/*  320:     */       }
/*  321: 249 */       else if (!_tokenSet_2.member(LA(1)))
/*  322:     */       {
/*  323: 252 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  324:     */       }
/*  325: 256 */       mALT(false);
/*  326: 258 */       switch (LA(1))
/*  327:     */       {
/*  328:     */       case '\t': 
/*  329:     */       case '\n': 
/*  330:     */       case '\r': 
/*  331:     */       case ' ': 
/*  332: 261 */         k = this.text.length();
/*  333: 262 */         mWS(false);
/*  334: 263 */         this.text.setLength(k);
/*  335: 264 */         break;
/*  336:     */       case ';': 
/*  337:     */       case '|': 
/*  338:     */         break;
/*  339:     */       default: 
/*  340: 272 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  341:     */       }
/*  342:     */     }
/*  343: 283 */     match(';');
/*  344: 284 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/*  345:     */     {
/*  346: 285 */       localToken = makeToken(i);
/*  347: 286 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  348:     */     }
/*  349: 288 */     this._returnToken = localToken;
/*  350:     */   }
/*  351:     */   
/*  352:     */   public final void mWS(boolean paramBoolean)
/*  353:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  354:     */   {
/*  355: 292 */     Token localToken = null;int j = this.text.length();
/*  356: 293 */     int i = 33;
/*  357:     */     
/*  358:     */ 
/*  359:     */ 
/*  360: 297 */     int k = 0;
/*  361:     */     for (;;)
/*  362:     */     {
/*  363: 300 */       if (LA(1) == ' ')
/*  364:     */       {
/*  365: 301 */         match(' ');
/*  366:     */       }
/*  367: 303 */       else if (LA(1) == '\t')
/*  368:     */       {
/*  369: 304 */         match('\t');
/*  370:     */       }
/*  371: 306 */       else if ((LA(1) == '\n') || (LA(1) == '\r'))
/*  372:     */       {
/*  373: 307 */         mNEWLINE(false);
/*  374:     */       }
/*  375:     */       else
/*  376:     */       {
/*  377: 310 */         if (k >= 1) {
/*  378:     */           break;
/*  379:     */         }
/*  380: 310 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  381:     */       }
/*  382: 313 */       k++;
/*  383:     */     }
/*  384: 316 */     i = -1;
/*  385: 317 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/*  386:     */     {
/*  387: 318 */       localToken = makeToken(i);
/*  388: 319 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  389:     */     }
/*  390: 321 */     this._returnToken = localToken;
/*  391:     */   }
/*  392:     */   
/*  393:     */   protected final void mALT(boolean paramBoolean)
/*  394:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  395:     */   {
/*  396: 325 */     Token localToken = null;int j = this.text.length();
/*  397: 326 */     int i = 27;
/*  398: 332 */     while ((_tokenSet_3.member(LA(1))) && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/*  399: 333 */       mELEMENT(false);
/*  400:     */     }
/*  401: 341 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/*  402:     */     {
/*  403: 342 */       localToken = makeToken(i);
/*  404: 343 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  405:     */     }
/*  406: 345 */     this._returnToken = localToken;
/*  407:     */   }
/*  408:     */   
/*  409:     */   public final void mSUBRULE_BLOCK(boolean paramBoolean)
/*  410:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  411:     */   {
/*  412: 349 */     Token localToken = null;int j = this.text.length();
/*  413: 350 */     int i = 6;
/*  414:     */     
/*  415:     */ 
/*  416: 353 */     match('(');
/*  417: 355 */     if ((_tokenSet_1.member(LA(1))) && (_tokenSet_0.member(LA(2)))) {
/*  418: 356 */       mWS(false);
/*  419: 358 */     } else if (!_tokenSet_0.member(LA(1))) {
/*  420: 361 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  421:     */     }
/*  422: 365 */     mALT(false);
/*  423: 369 */     while ((_tokenSet_4.member(LA(1))) && (_tokenSet_0.member(LA(2))))
/*  424:     */     {
/*  425: 371 */       switch (LA(1))
/*  426:     */       {
/*  427:     */       case '\t': 
/*  428:     */       case '\n': 
/*  429:     */       case '\r': 
/*  430:     */       case ' ': 
/*  431: 374 */         mWS(false);
/*  432: 375 */         break;
/*  433:     */       case '|': 
/*  434:     */         break;
/*  435:     */       default: 
/*  436: 383 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  437:     */       }
/*  438: 387 */       match('|');
/*  439: 389 */       if ((_tokenSet_1.member(LA(1))) && (_tokenSet_0.member(LA(2)))) {
/*  440: 390 */         mWS(false);
/*  441: 392 */       } else if (!_tokenSet_0.member(LA(1))) {
/*  442: 395 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  443:     */       }
/*  444: 399 */       mALT(false);
/*  445:     */     }
/*  446: 408 */     switch (LA(1))
/*  447:     */     {
/*  448:     */     case '\t': 
/*  449:     */     case '\n': 
/*  450:     */     case '\r': 
/*  451:     */     case ' ': 
/*  452: 411 */       mWS(false);
/*  453: 412 */       break;
/*  454:     */     case ')': 
/*  455:     */       break;
/*  456:     */     default: 
/*  457: 420 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  458:     */     }
/*  459: 424 */     match(')');
/*  460: 426 */     if ((LA(1) == '=') && (LA(2) == '>')) {
/*  461: 427 */       match("=>");
/*  462: 429 */     } else if (LA(1) == '*') {
/*  463: 430 */       match('*');
/*  464: 432 */     } else if (LA(1) == '+') {
/*  465: 433 */       match('+');
/*  466: 435 */     } else if (LA(1) == '?') {
/*  467: 436 */       match('?');
/*  468:     */     }
/*  469: 442 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/*  470:     */     {
/*  471: 443 */       localToken = makeToken(i);
/*  472: 444 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  473:     */     }
/*  474: 446 */     this._returnToken = localToken;
/*  475:     */   }
/*  476:     */   
/*  477:     */   protected final void mELEMENT(boolean paramBoolean)
/*  478:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  479:     */   {
/*  480: 450 */     Token localToken = null;int j = this.text.length();
/*  481: 451 */     int i = 28;
/*  482: 454 */     switch (LA(1))
/*  483:     */     {
/*  484:     */     case '/': 
/*  485: 457 */       mCOMMENT(false);
/*  486: 458 */       break;
/*  487:     */     case '{': 
/*  488: 462 */       mACTION(false);
/*  489: 463 */       break;
/*  490:     */     case '"': 
/*  491: 467 */       mSTRING_LITERAL(false);
/*  492: 468 */       break;
/*  493:     */     case '\'': 
/*  494: 472 */       mCHAR_LITERAL(false);
/*  495: 473 */       break;
/*  496:     */     case '(': 
/*  497: 477 */       mSUBRULE_BLOCK(false);
/*  498: 478 */       break;
/*  499:     */     case '\n': 
/*  500:     */     case '\r': 
/*  501: 482 */       mNEWLINE(false);
/*  502: 483 */       break;
/*  503:     */     default: 
/*  504: 486 */       if (_tokenSet_5.member(LA(1))) {
/*  505: 488 */         match(_tokenSet_5);
/*  506:     */       } else {
/*  507: 492 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  508:     */       }
/*  509:     */       break;
/*  510:     */     }
/*  511: 495 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/*  512:     */     {
/*  513: 496 */       localToken = makeToken(i);
/*  514: 497 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  515:     */     }
/*  516: 499 */     this._returnToken = localToken;
/*  517:     */   }
/*  518:     */   
/*  519:     */   public final void mCOMMENT(boolean paramBoolean)
/*  520:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  521:     */   {
/*  522: 503 */     Token localToken = null;int j = this.text.length();
/*  523: 504 */     int i = 35;
/*  524: 508 */     if ((LA(1) == '/') && (LA(2) == '/')) {
/*  525: 509 */       mSL_COMMENT(false);
/*  526: 511 */     } else if ((LA(1) == '/') && (LA(2) == '*')) {
/*  527: 512 */       mML_COMMENT(false);
/*  528:     */     } else {
/*  529: 515 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  530:     */     }
/*  531: 519 */     i = -1;
/*  532: 520 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/*  533:     */     {
/*  534: 521 */       localToken = makeToken(i);
/*  535: 522 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  536:     */     }
/*  537: 524 */     this._returnToken = localToken;
/*  538:     */   }
/*  539:     */   
/*  540:     */   public final void mACTION(boolean paramBoolean)
/*  541:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  542:     */   {
/*  543: 528 */     Token localToken = null;int j = this.text.length();
/*  544: 529 */     int i = 7;
/*  545:     */     
/*  546:     */ 
/*  547: 532 */     match('{');
/*  548: 537 */     while (LA(1) != '}') {
/*  549: 538 */       if (((LA(1) == '\n') || (LA(1) == '\r')) && (LA(2) >= '\003') && (LA(2) <= 'ÿ'))
/*  550:     */       {
/*  551: 539 */         mNEWLINE(false);
/*  552:     */       }
/*  553: 541 */       else if ((LA(1) == '{') && (LA(2) >= '\003') && (LA(2) <= 'ÿ'))
/*  554:     */       {
/*  555: 542 */         mACTION(false);
/*  556:     */       }
/*  557: 544 */       else if ((LA(1) == '\'') && (_tokenSet_6.member(LA(2))))
/*  558:     */       {
/*  559: 545 */         mCHAR_LITERAL(false);
/*  560:     */       }
/*  561: 547 */       else if ((LA(1) == '/') && ((LA(2) == '*') || (LA(2) == '/')))
/*  562:     */       {
/*  563: 548 */         mCOMMENT(false);
/*  564:     */       }
/*  565: 550 */       else if ((LA(1) == '"') && (LA(2) >= '\003') && (LA(2) <= 'ÿ'))
/*  566:     */       {
/*  567: 551 */         mSTRING_LITERAL(false);
/*  568:     */       }
/*  569:     */       else
/*  570:     */       {
/*  571: 553 */         if ((LA(1) < '\003') || (LA(1) > 'ÿ') || (LA(2) < '\003') || (LA(2) > 'ÿ')) {
/*  572:     */           break;
/*  573:     */         }
/*  574: 554 */         matchNot(65535);
/*  575:     */       }
/*  576:     */     }
/*  577: 562 */     match('}');
/*  578: 563 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/*  579:     */     {
/*  580: 564 */       localToken = makeToken(i);
/*  581: 565 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  582:     */     }
/*  583: 567 */     this._returnToken = localToken;
/*  584:     */   }
/*  585:     */   
/*  586:     */   public final void mSTRING_LITERAL(boolean paramBoolean)
/*  587:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  588:     */   {
/*  589: 571 */     Token localToken = null;int j = this.text.length();
/*  590: 572 */     int i = 39;
/*  591:     */     
/*  592:     */ 
/*  593: 575 */     match('"');
/*  594:     */     for (;;)
/*  595:     */     {
/*  596: 579 */       if (LA(1) == '\\')
/*  597:     */       {
/*  598: 580 */         mESC(false);
/*  599:     */       }
/*  600:     */       else
/*  601:     */       {
/*  602: 582 */         if (!_tokenSet_7.member(LA(1))) {
/*  603:     */           break;
/*  604:     */         }
/*  605: 583 */         matchNot('"');
/*  606:     */       }
/*  607:     */     }
/*  608: 591 */     match('"');
/*  609: 592 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/*  610:     */     {
/*  611: 593 */       localToken = makeToken(i);
/*  612: 594 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  613:     */     }
/*  614: 596 */     this._returnToken = localToken;
/*  615:     */   }
/*  616:     */   
/*  617:     */   public final void mCHAR_LITERAL(boolean paramBoolean)
/*  618:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  619:     */   {
/*  620: 600 */     Token localToken = null;int j = this.text.length();
/*  621: 601 */     int i = 38;
/*  622:     */     
/*  623:     */ 
/*  624: 604 */     match('\'');
/*  625: 606 */     if (LA(1) == '\\') {
/*  626: 607 */       mESC(false);
/*  627: 609 */     } else if (_tokenSet_8.member(LA(1))) {
/*  628: 610 */       matchNot('\'');
/*  629:     */     } else {
/*  630: 613 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  631:     */     }
/*  632: 617 */     match('\'');
/*  633: 618 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/*  634:     */     {
/*  635: 619 */       localToken = makeToken(i);
/*  636: 620 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  637:     */     }
/*  638: 622 */     this._returnToken = localToken;
/*  639:     */   }
/*  640:     */   
/*  641:     */   protected final void mNEWLINE(boolean paramBoolean)
/*  642:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  643:     */   {
/*  644: 626 */     Token localToken = null;int j = this.text.length();
/*  645: 627 */     int i = 34;
/*  646: 631 */     if ((LA(1) == '\r') && (LA(2) == '\n'))
/*  647:     */     {
/*  648: 632 */       match('\r');
/*  649: 633 */       match('\n');
/*  650: 634 */       newline();
/*  651:     */     }
/*  652: 636 */     else if (LA(1) == '\r')
/*  653:     */     {
/*  654: 637 */       match('\r');
/*  655: 638 */       newline();
/*  656:     */     }
/*  657: 640 */     else if (LA(1) == '\n')
/*  658:     */     {
/*  659: 641 */       match('\n');
/*  660: 642 */       newline();
/*  661:     */     }
/*  662:     */     else
/*  663:     */     {
/*  664: 645 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  665:     */     }
/*  666: 649 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/*  667:     */     {
/*  668: 650 */       localToken = makeToken(i);
/*  669: 651 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  670:     */     }
/*  671: 653 */     this._returnToken = localToken;
/*  672:     */   }
/*  673:     */   
/*  674:     */   public final void mBANG(boolean paramBoolean)
/*  675:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  676:     */   {
/*  677: 657 */     Token localToken = null;int j = this.text.length();
/*  678: 658 */     int i = 19;
/*  679:     */     
/*  680:     */ 
/*  681: 661 */     match('!');
/*  682: 662 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/*  683:     */     {
/*  684: 663 */       localToken = makeToken(i);
/*  685: 664 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  686:     */     }
/*  687: 666 */     this._returnToken = localToken;
/*  688:     */   }
/*  689:     */   
/*  690:     */   public final void mSEMI(boolean paramBoolean)
/*  691:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  692:     */   {
/*  693: 670 */     Token localToken = null;int j = this.text.length();
/*  694: 671 */     int i = 11;
/*  695:     */     
/*  696:     */ 
/*  697: 674 */     match(';');
/*  698: 675 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/*  699:     */     {
/*  700: 676 */       localToken = makeToken(i);
/*  701: 677 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  702:     */     }
/*  703: 679 */     this._returnToken = localToken;
/*  704:     */   }
/*  705:     */   
/*  706:     */   public final void mCOMMA(boolean paramBoolean)
/*  707:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  708:     */   {
/*  709: 683 */     Token localToken = null;int j = this.text.length();
/*  710: 684 */     int i = 24;
/*  711:     */     
/*  712:     */ 
/*  713: 687 */     match(',');
/*  714: 688 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/*  715:     */     {
/*  716: 689 */       localToken = makeToken(i);
/*  717: 690 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  718:     */     }
/*  719: 692 */     this._returnToken = localToken;
/*  720:     */   }
/*  721:     */   
/*  722:     */   public final void mRCURLY(boolean paramBoolean)
/*  723:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  724:     */   {
/*  725: 696 */     Token localToken = null;int j = this.text.length();
/*  726: 697 */     int i = 15;
/*  727:     */     
/*  728:     */ 
/*  729: 700 */     match('}');
/*  730: 701 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/*  731:     */     {
/*  732: 702 */       localToken = makeToken(i);
/*  733: 703 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  734:     */     }
/*  735: 705 */     this._returnToken = localToken;
/*  736:     */   }
/*  737:     */   
/*  738:     */   public final void mLPAREN(boolean paramBoolean)
/*  739:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  740:     */   {
/*  741: 709 */     Token localToken = null;int j = this.text.length();
/*  742: 710 */     int i = 29;
/*  743:     */     
/*  744:     */ 
/*  745: 713 */     match('(');
/*  746: 714 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/*  747:     */     {
/*  748: 715 */       localToken = makeToken(i);
/*  749: 716 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  750:     */     }
/*  751: 718 */     this._returnToken = localToken;
/*  752:     */   }
/*  753:     */   
/*  754:     */   public final void mRPAREN(boolean paramBoolean)
/*  755:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  756:     */   {
/*  757: 722 */     Token localToken = null;int j = this.text.length();
/*  758: 723 */     int i = 30;
/*  759:     */     
/*  760:     */ 
/*  761: 726 */     match(')');
/*  762: 727 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/*  763:     */     {
/*  764: 728 */       localToken = makeToken(i);
/*  765: 729 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  766:     */     }
/*  767: 731 */     this._returnToken = localToken;
/*  768:     */   }
/*  769:     */   
/*  770:     */   public final void mID_OR_KEYWORD(boolean paramBoolean)
/*  771:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  772:     */   {
/*  773: 742 */     Token localToken1 = null;int j = this.text.length();
/*  774: 743 */     int i = 31;
/*  775:     */     
/*  776: 745 */     Token localToken2 = null;
/*  777:     */     
/*  778: 747 */     mID(true);
/*  779: 748 */     localToken2 = this._returnToken;
/*  780: 749 */     i = localToken2.getType();
/*  781: 751 */     if ((_tokenSet_9.member(LA(1))) && (LA(2) >= '\003') && (LA(2) <= 'ÿ') && (localToken2.getText().equals("header")))
/*  782:     */     {
/*  783: 753 */       if ((_tokenSet_1.member(LA(1))) && (_tokenSet_9.member(LA(2)))) {
/*  784: 754 */         mWS(false);
/*  785: 756 */       } else if ((!_tokenSet_9.member(LA(1))) || (LA(2) < '\003') || (LA(2) > 'ÿ')) {
/*  786: 759 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  787:     */       }
/*  788: 764 */       switch (LA(1))
/*  789:     */       {
/*  790:     */       case '"': 
/*  791: 767 */         mSTRING_LITERAL(false);
/*  792: 768 */         break;
/*  793:     */       case '\t': 
/*  794:     */       case '\n': 
/*  795:     */       case '\r': 
/*  796:     */       case ' ': 
/*  797:     */       case '/': 
/*  798:     */       case '{': 
/*  799:     */         break;
/*  800:     */       default: 
/*  801: 777 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  802:     */       }
/*  803:     */       for (;;)
/*  804:     */       {
/*  805: 784 */         switch (LA(1))
/*  806:     */         {
/*  807:     */         case '\t': 
/*  808:     */         case '\n': 
/*  809:     */         case '\r': 
/*  810:     */         case ' ': 
/*  811: 787 */           mWS(false);
/*  812: 788 */           break;
/*  813:     */         case '/': 
/*  814: 792 */           mCOMMENT(false);
/*  815:     */         }
/*  816:     */       }
/*  817: 802 */       mACTION(false);
/*  818: 803 */       i = 5;
/*  819:     */     }
/*  820: 805 */     else if ((_tokenSet_10.member(LA(1))) && (LA(2) >= '\003') && (LA(2) <= 'ÿ') && (localToken2.getText().equals("tokens")))
/*  821:     */     {
/*  822:     */       for (;;)
/*  823:     */       {
/*  824: 809 */         switch (LA(1))
/*  825:     */         {
/*  826:     */         case '\t': 
/*  827:     */         case '\n': 
/*  828:     */         case '\r': 
/*  829:     */         case ' ': 
/*  830: 812 */           mWS(false);
/*  831: 813 */           break;
/*  832:     */         case '/': 
/*  833: 817 */           mCOMMENT(false);
/*  834:     */         }
/*  835:     */       }
/*  836: 827 */       mCURLY_BLOCK_SCARF(false);
/*  837: 828 */       i = 12;
/*  838:     */     }
/*  839: 830 */     else if ((_tokenSet_10.member(LA(1))) && (localToken2.getText().equals("options")))
/*  840:     */     {
/*  841:     */       for (;;)
/*  842:     */       {
/*  843: 834 */         switch (LA(1))
/*  844:     */         {
/*  845:     */         case '\t': 
/*  846:     */         case '\n': 
/*  847:     */         case '\r': 
/*  848:     */         case ' ': 
/*  849: 837 */           mWS(false);
/*  850: 838 */           break;
/*  851:     */         case '/': 
/*  852: 842 */           mCOMMENT(false);
/*  853:     */         }
/*  854:     */       }
/*  855: 852 */       match('{');
/*  856: 853 */       i = 13;
/*  857:     */     }
/*  858: 859 */     if ((paramBoolean) && (localToken1 == null) && (i != -1))
/*  859:     */     {
/*  860: 860 */       localToken1 = makeToken(i);
/*  861: 861 */       localToken1.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  862:     */     }
/*  863: 863 */     this._returnToken = localToken1;
/*  864:     */   }
/*  865:     */   
/*  866:     */   protected final void mID(boolean paramBoolean)
/*  867:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  868:     */   {
/*  869: 867 */     Token localToken = null;int j = this.text.length();
/*  870: 868 */     int i = 9;
/*  871: 872 */     switch (LA(1))
/*  872:     */     {
/*  873:     */     case 'a': 
/*  874:     */     case 'b': 
/*  875:     */     case 'c': 
/*  876:     */     case 'd': 
/*  877:     */     case 'e': 
/*  878:     */     case 'f': 
/*  879:     */     case 'g': 
/*  880:     */     case 'h': 
/*  881:     */     case 'i': 
/*  882:     */     case 'j': 
/*  883:     */     case 'k': 
/*  884:     */     case 'l': 
/*  885:     */     case 'm': 
/*  886:     */     case 'n': 
/*  887:     */     case 'o': 
/*  888:     */     case 'p': 
/*  889:     */     case 'q': 
/*  890:     */     case 'r': 
/*  891:     */     case 's': 
/*  892:     */     case 't': 
/*  893:     */     case 'u': 
/*  894:     */     case 'v': 
/*  895:     */     case 'w': 
/*  896:     */     case 'x': 
/*  897:     */     case 'y': 
/*  898:     */     case 'z': 
/*  899: 881 */       matchRange('a', 'z');
/*  900: 882 */       break;
/*  901:     */     case 'A': 
/*  902:     */     case 'B': 
/*  903:     */     case 'C': 
/*  904:     */     case 'D': 
/*  905:     */     case 'E': 
/*  906:     */     case 'F': 
/*  907:     */     case 'G': 
/*  908:     */     case 'H': 
/*  909:     */     case 'I': 
/*  910:     */     case 'J': 
/*  911:     */     case 'K': 
/*  912:     */     case 'L': 
/*  913:     */     case 'M': 
/*  914:     */     case 'N': 
/*  915:     */     case 'O': 
/*  916:     */     case 'P': 
/*  917:     */     case 'Q': 
/*  918:     */     case 'R': 
/*  919:     */     case 'S': 
/*  920:     */     case 'T': 
/*  921:     */     case 'U': 
/*  922:     */     case 'V': 
/*  923:     */     case 'W': 
/*  924:     */     case 'X': 
/*  925:     */     case 'Y': 
/*  926:     */     case 'Z': 
/*  927: 892 */       matchRange('A', 'Z');
/*  928: 893 */       break;
/*  929:     */     case '_': 
/*  930: 897 */       match('_');
/*  931: 898 */       break;
/*  932:     */     case '[': 
/*  933:     */     case '\\': 
/*  934:     */     case ']': 
/*  935:     */     case '^': 
/*  936:     */     case '`': 
/*  937:     */     default: 
/*  938: 902 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  939:     */     }
/*  940:     */     for (;;)
/*  941:     */     {
/*  942: 909 */       switch (LA(1))
/*  943:     */       {
/*  944:     */       case 'a': 
/*  945:     */       case 'b': 
/*  946:     */       case 'c': 
/*  947:     */       case 'd': 
/*  948:     */       case 'e': 
/*  949:     */       case 'f': 
/*  950:     */       case 'g': 
/*  951:     */       case 'h': 
/*  952:     */       case 'i': 
/*  953:     */       case 'j': 
/*  954:     */       case 'k': 
/*  955:     */       case 'l': 
/*  956:     */       case 'm': 
/*  957:     */       case 'n': 
/*  958:     */       case 'o': 
/*  959:     */       case 'p': 
/*  960:     */       case 'q': 
/*  961:     */       case 'r': 
/*  962:     */       case 's': 
/*  963:     */       case 't': 
/*  964:     */       case 'u': 
/*  965:     */       case 'v': 
/*  966:     */       case 'w': 
/*  967:     */       case 'x': 
/*  968:     */       case 'y': 
/*  969:     */       case 'z': 
/*  970: 918 */         matchRange('a', 'z');
/*  971: 919 */         break;
/*  972:     */       case 'A': 
/*  973:     */       case 'B': 
/*  974:     */       case 'C': 
/*  975:     */       case 'D': 
/*  976:     */       case 'E': 
/*  977:     */       case 'F': 
/*  978:     */       case 'G': 
/*  979:     */       case 'H': 
/*  980:     */       case 'I': 
/*  981:     */       case 'J': 
/*  982:     */       case 'K': 
/*  983:     */       case 'L': 
/*  984:     */       case 'M': 
/*  985:     */       case 'N': 
/*  986:     */       case 'O': 
/*  987:     */       case 'P': 
/*  988:     */       case 'Q': 
/*  989:     */       case 'R': 
/*  990:     */       case 'S': 
/*  991:     */       case 'T': 
/*  992:     */       case 'U': 
/*  993:     */       case 'V': 
/*  994:     */       case 'W': 
/*  995:     */       case 'X': 
/*  996:     */       case 'Y': 
/*  997:     */       case 'Z': 
/*  998: 929 */         matchRange('A', 'Z');
/*  999: 930 */         break;
/* 1000:     */       case '_': 
/* 1001: 934 */         match('_');
/* 1002: 935 */         break;
/* 1003:     */       case '0': 
/* 1004:     */       case '1': 
/* 1005:     */       case '2': 
/* 1006:     */       case '3': 
/* 1007:     */       case '4': 
/* 1008:     */       case '5': 
/* 1009:     */       case '6': 
/* 1010:     */       case '7': 
/* 1011:     */       case '8': 
/* 1012:     */       case '9': 
/* 1013: 941 */         matchRange('0', '9');
/* 1014:     */       }
/* 1015:     */     }
/* 1016: 951 */     i = testLiteralsTable(new String(this.text.getBuffer(), j, this.text.length() - j), i);
/* 1017: 952 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 1018:     */     {
/* 1019: 953 */       localToken = makeToken(i);
/* 1020: 954 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 1021:     */     }
/* 1022: 956 */     this._returnToken = localToken;
/* 1023:     */   }
/* 1024:     */   
/* 1025:     */   protected final void mCURLY_BLOCK_SCARF(boolean paramBoolean)
/* 1026:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 1027:     */   {
/* 1028: 960 */     Token localToken = null;int j = this.text.length();
/* 1029: 961 */     int i = 32;
/* 1030:     */     
/* 1031:     */ 
/* 1032: 964 */     match('{');
/* 1033: 969 */     while (LA(1) != '}') {
/* 1034: 970 */       if (((LA(1) == '\n') || (LA(1) == '\r')) && (LA(2) >= '\003') && (LA(2) <= 'ÿ'))
/* 1035:     */       {
/* 1036: 971 */         mNEWLINE(false);
/* 1037:     */       }
/* 1038: 973 */       else if ((LA(1) == '"') && (LA(2) >= '\003') && (LA(2) <= 'ÿ'))
/* 1039:     */       {
/* 1040: 974 */         mSTRING_LITERAL(false);
/* 1041:     */       }
/* 1042: 976 */       else if ((LA(1) == '\'') && (_tokenSet_6.member(LA(2))))
/* 1043:     */       {
/* 1044: 977 */         mCHAR_LITERAL(false);
/* 1045:     */       }
/* 1046: 979 */       else if ((LA(1) == '/') && ((LA(2) == '*') || (LA(2) == '/')))
/* 1047:     */       {
/* 1048: 980 */         mCOMMENT(false);
/* 1049:     */       }
/* 1050:     */       else
/* 1051:     */       {
/* 1052: 982 */         if ((LA(1) < '\003') || (LA(1) > 'ÿ') || (LA(2) < '\003') || (LA(2) > 'ÿ')) {
/* 1053:     */           break;
/* 1054:     */         }
/* 1055: 983 */         matchNot(65535);
/* 1056:     */       }
/* 1057:     */     }
/* 1058: 991 */     match('}');
/* 1059: 992 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 1060:     */     {
/* 1061: 993 */       localToken = makeToken(i);
/* 1062: 994 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 1063:     */     }
/* 1064: 996 */     this._returnToken = localToken;
/* 1065:     */   }
/* 1066:     */   
/* 1067:     */   public final void mASSIGN_RHS(boolean paramBoolean)
/* 1068:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 1069:     */   {
/* 1070:1000 */     Token localToken = null;int j = this.text.length();
/* 1071:1001 */     int i = 14;
/* 1072:     */     
/* 1073:     */ 
/* 1074:1004 */     int k = this.text.length();
/* 1075:1005 */     match('=');
/* 1076:1006 */     this.text.setLength(k);
/* 1077:1011 */     while (LA(1) != ';') {
/* 1078:1012 */       if ((LA(1) == '"') && (LA(2) >= '\003') && (LA(2) <= 'ÿ'))
/* 1079:     */       {
/* 1080:1013 */         mSTRING_LITERAL(false);
/* 1081:     */       }
/* 1082:1015 */       else if ((LA(1) == '\'') && (_tokenSet_6.member(LA(2))))
/* 1083:     */       {
/* 1084:1016 */         mCHAR_LITERAL(false);
/* 1085:     */       }
/* 1086:1018 */       else if (((LA(1) == '\n') || (LA(1) == '\r')) && (LA(2) >= '\003') && (LA(2) <= 'ÿ'))
/* 1087:     */       {
/* 1088:1019 */         mNEWLINE(false);
/* 1089:     */       }
/* 1090:     */       else
/* 1091:     */       {
/* 1092:1021 */         if ((LA(1) < '\003') || (LA(1) > 'ÿ') || (LA(2) < '\003') || (LA(2) > 'ÿ')) {
/* 1093:     */           break;
/* 1094:     */         }
/* 1095:1022 */         matchNot(65535);
/* 1096:     */       }
/* 1097:     */     }
/* 1098:1030 */     match(';');
/* 1099:1031 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 1100:     */     {
/* 1101:1032 */       localToken = makeToken(i);
/* 1102:1033 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 1103:     */     }
/* 1104:1035 */     this._returnToken = localToken;
/* 1105:     */   }
/* 1106:     */   
/* 1107:     */   protected final void mSL_COMMENT(boolean paramBoolean)
/* 1108:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 1109:     */   {
/* 1110:1039 */     Token localToken = null;int j = this.text.length();
/* 1111:1040 */     int i = 36;
/* 1112:     */     
/* 1113:     */ 
/* 1114:1043 */     match("//");
/* 1115:1048 */     while ((LA(1) != '\n') && (LA(1) != '\r') && 
/* 1116:1049 */       (LA(1) >= '\003') && (LA(1) <= 'ÿ') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 1117:1050 */       matchNot(65535);
/* 1118:     */     }
/* 1119:1058 */     mNEWLINE(false);
/* 1120:1059 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 1121:     */     {
/* 1122:1060 */       localToken = makeToken(i);
/* 1123:1061 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 1124:     */     }
/* 1125:1063 */     this._returnToken = localToken;
/* 1126:     */   }
/* 1127:     */   
/* 1128:     */   protected final void mML_COMMENT(boolean paramBoolean)
/* 1129:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 1130:     */   {
/* 1131:1067 */     Token localToken = null;int j = this.text.length();
/* 1132:1068 */     int i = 37;
/* 1133:     */     
/* 1134:     */ 
/* 1135:1071 */     match("/*");
/* 1136:1076 */     while ((LA(1) != '*') || (LA(2) != '/')) {
/* 1137:1077 */       if (((LA(1) == '\n') || (LA(1) == '\r')) && (LA(2) >= '\003') && (LA(2) <= 'ÿ'))
/* 1138:     */       {
/* 1139:1078 */         mNEWLINE(false);
/* 1140:     */       }
/* 1141:     */       else
/* 1142:     */       {
/* 1143:1080 */         if ((LA(1) < '\003') || (LA(1) > 'ÿ') || (LA(2) < '\003') || (LA(2) > 'ÿ')) {
/* 1144:     */           break;
/* 1145:     */         }
/* 1146:1081 */         matchNot(65535);
/* 1147:     */       }
/* 1148:     */     }
/* 1149:1089 */     match("*/");
/* 1150:1090 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 1151:     */     {
/* 1152:1091 */       localToken = makeToken(i);
/* 1153:1092 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 1154:     */     }
/* 1155:1094 */     this._returnToken = localToken;
/* 1156:     */   }
/* 1157:     */   
/* 1158:     */   protected final void mESC(boolean paramBoolean)
/* 1159:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 1160:     */   {
/* 1161:1098 */     Token localToken = null;int j = this.text.length();
/* 1162:1099 */     int i = 40;
/* 1163:     */     
/* 1164:     */ 
/* 1165:1102 */     match('\\');
/* 1166:1104 */     switch (LA(1))
/* 1167:     */     {
/* 1168:     */     case 'n': 
/* 1169:1107 */       match('n');
/* 1170:1108 */       break;
/* 1171:     */     case 'r': 
/* 1172:1112 */       match('r');
/* 1173:1113 */       break;
/* 1174:     */     case 't': 
/* 1175:1117 */       match('t');
/* 1176:1118 */       break;
/* 1177:     */     case 'b': 
/* 1178:1122 */       match('b');
/* 1179:1123 */       break;
/* 1180:     */     case 'f': 
/* 1181:1127 */       match('f');
/* 1182:1128 */       break;
/* 1183:     */     case 'w': 
/* 1184:1132 */       match('w');
/* 1185:1133 */       break;
/* 1186:     */     case 'a': 
/* 1187:1137 */       match('a');
/* 1188:1138 */       break;
/* 1189:     */     case '"': 
/* 1190:1142 */       match('"');
/* 1191:1143 */       break;
/* 1192:     */     case '\'': 
/* 1193:1147 */       match('\'');
/* 1194:1148 */       break;
/* 1195:     */     case '\\': 
/* 1196:1152 */       match('\\');
/* 1197:1153 */       break;
/* 1198:     */     case '0': 
/* 1199:     */     case '1': 
/* 1200:     */     case '2': 
/* 1201:     */     case '3': 
/* 1202:1158 */       matchRange('0', '3');
/* 1203:1161 */       if ((LA(1) >= '0') && (LA(1) <= '9') && (LA(2) >= '\003') && (LA(2) <= 'ÿ'))
/* 1204:     */       {
/* 1205:1162 */         mDIGIT(false);
/* 1206:1164 */         if ((LA(1) >= '0') && (LA(1) <= '9') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 1207:1165 */           mDIGIT(false);
/* 1208:1167 */         } else if ((LA(1) < '\003') || (LA(1) > 'ÿ')) {
/* 1209:1170 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1210:     */         }
/* 1211:     */       }
/* 1212:1175 */       else if ((LA(1) < '\003') || (LA(1) > 'ÿ'))
/* 1213:     */       {
/* 1214:1178 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1215:     */       }
/* 1216:     */       break;
/* 1217:     */     case '4': 
/* 1218:     */     case '5': 
/* 1219:     */     case '6': 
/* 1220:     */     case '7': 
/* 1221:1187 */       matchRange('4', '7');
/* 1222:1190 */       if ((LA(1) >= '0') && (LA(1) <= '9') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 1223:1191 */         mDIGIT(false);
/* 1224:1193 */       } else if ((LA(1) < '\003') || (LA(1) > 'ÿ')) {
/* 1225:1196 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1226:     */       }
/* 1227:     */       break;
/* 1228:     */     case 'u': 
/* 1229:1204 */       match('u');
/* 1230:1205 */       mXDIGIT(false);
/* 1231:1206 */       mXDIGIT(false);
/* 1232:1207 */       mXDIGIT(false);
/* 1233:1208 */       mXDIGIT(false);
/* 1234:1209 */       break;
/* 1235:     */     default: 
/* 1236:1213 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1237:     */     }
/* 1238:1217 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 1239:     */     {
/* 1240:1218 */       localToken = makeToken(i);
/* 1241:1219 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 1242:     */     }
/* 1243:1221 */     this._returnToken = localToken;
/* 1244:     */   }
/* 1245:     */   
/* 1246:     */   protected final void mDIGIT(boolean paramBoolean)
/* 1247:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 1248:     */   {
/* 1249:1225 */     Token localToken = null;int j = this.text.length();
/* 1250:1226 */     int i = 41;
/* 1251:     */     
/* 1252:     */ 
/* 1253:1229 */     matchRange('0', '9');
/* 1254:1230 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 1255:     */     {
/* 1256:1231 */       localToken = makeToken(i);
/* 1257:1232 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 1258:     */     }
/* 1259:1234 */     this._returnToken = localToken;
/* 1260:     */   }
/* 1261:     */   
/* 1262:     */   protected final void mXDIGIT(boolean paramBoolean)
/* 1263:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 1264:     */   {
/* 1265:1238 */     Token localToken = null;int j = this.text.length();
/* 1266:1239 */     int i = 42;
/* 1267:1242 */     switch (LA(1))
/* 1268:     */     {
/* 1269:     */     case '0': 
/* 1270:     */     case '1': 
/* 1271:     */     case '2': 
/* 1272:     */     case '3': 
/* 1273:     */     case '4': 
/* 1274:     */     case '5': 
/* 1275:     */     case '6': 
/* 1276:     */     case '7': 
/* 1277:     */     case '8': 
/* 1278:     */     case '9': 
/* 1279:1247 */       matchRange('0', '9');
/* 1280:1248 */       break;
/* 1281:     */     case 'a': 
/* 1282:     */     case 'b': 
/* 1283:     */     case 'c': 
/* 1284:     */     case 'd': 
/* 1285:     */     case 'e': 
/* 1286:     */     case 'f': 
/* 1287:1253 */       matchRange('a', 'f');
/* 1288:1254 */       break;
/* 1289:     */     case 'A': 
/* 1290:     */     case 'B': 
/* 1291:     */     case 'C': 
/* 1292:     */     case 'D': 
/* 1293:     */     case 'E': 
/* 1294:     */     case 'F': 
/* 1295:1259 */       matchRange('A', 'F');
/* 1296:1260 */       break;
/* 1297:     */     case ':': 
/* 1298:     */     case ';': 
/* 1299:     */     case '<': 
/* 1300:     */     case '=': 
/* 1301:     */     case '>': 
/* 1302:     */     case '?': 
/* 1303:     */     case '@': 
/* 1304:     */     case 'G': 
/* 1305:     */     case 'H': 
/* 1306:     */     case 'I': 
/* 1307:     */     case 'J': 
/* 1308:     */     case 'K': 
/* 1309:     */     case 'L': 
/* 1310:     */     case 'M': 
/* 1311:     */     case 'N': 
/* 1312:     */     case 'O': 
/* 1313:     */     case 'P': 
/* 1314:     */     case 'Q': 
/* 1315:     */     case 'R': 
/* 1316:     */     case 'S': 
/* 1317:     */     case 'T': 
/* 1318:     */     case 'U': 
/* 1319:     */     case 'V': 
/* 1320:     */     case 'W': 
/* 1321:     */     case 'X': 
/* 1322:     */     case 'Y': 
/* 1323:     */     case 'Z': 
/* 1324:     */     case '[': 
/* 1325:     */     case '\\': 
/* 1326:     */     case ']': 
/* 1327:     */     case '^': 
/* 1328:     */     case '_': 
/* 1329:     */     case '`': 
/* 1330:     */     default: 
/* 1331:1264 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1332:     */     }
/* 1333:1267 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 1334:     */     {
/* 1335:1268 */       localToken = makeToken(i);
/* 1336:1269 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 1337:     */     }
/* 1338:1271 */     this._returnToken = localToken;
/* 1339:     */   }
/* 1340:     */   
/* 1341:     */   public final void mARG_ACTION(boolean paramBoolean)
/* 1342:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 1343:     */   {
/* 1344:1275 */     Token localToken = null;int j = this.text.length();
/* 1345:1276 */     int i = 20;
/* 1346:     */     
/* 1347:     */ 
/* 1348:1279 */     match('[');
/* 1349:1284 */     while (LA(1) != ']') {
/* 1350:1285 */       if ((LA(1) == '[') && (LA(2) >= '\003') && (LA(2) <= 'ÿ'))
/* 1351:     */       {
/* 1352:1286 */         mARG_ACTION(false);
/* 1353:     */       }
/* 1354:1288 */       else if (((LA(1) == '\n') || (LA(1) == '\r')) && (LA(2) >= '\003') && (LA(2) <= 'ÿ'))
/* 1355:     */       {
/* 1356:1289 */         mNEWLINE(false);
/* 1357:     */       }
/* 1358:1291 */       else if ((LA(1) == '\'') && (_tokenSet_6.member(LA(2))))
/* 1359:     */       {
/* 1360:1292 */         mCHAR_LITERAL(false);
/* 1361:     */       }
/* 1362:1294 */       else if ((LA(1) == '"') && (LA(2) >= '\003') && (LA(2) <= 'ÿ'))
/* 1363:     */       {
/* 1364:1295 */         mSTRING_LITERAL(false);
/* 1365:     */       }
/* 1366:     */       else
/* 1367:     */       {
/* 1368:1297 */         if ((LA(1) < '\003') || (LA(1) > 'ÿ') || (LA(2) < '\003') || (LA(2) > 'ÿ')) {
/* 1369:     */           break;
/* 1370:     */         }
/* 1371:1298 */         matchNot(65535);
/* 1372:     */       }
/* 1373:     */     }
/* 1374:1306 */     match(']');
/* 1375:1307 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 1376:     */     {
/* 1377:1308 */       localToken = makeToken(i);
/* 1378:1309 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 1379:     */     }
/* 1380:1311 */     this._returnToken = localToken;
/* 1381:     */   }
/* 1382:     */   
/* 1383:     */   private static final long[] mk_tokenSet_0()
/* 1384:     */   {
/* 1385:1316 */     long[] arrayOfLong = new long[8];
/* 1386:1317 */     arrayOfLong[0] = -576460752303423496L;
/* 1387:1318 */     for (int i = 1; i <= 3; i++) {
/* 1388:1318 */       arrayOfLong[i] = -1L;
/* 1389:     */     }
/* 1390:1319 */     return arrayOfLong;
/* 1391:     */   }
/* 1392:     */   
/* 1393:1321 */   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
/* 1394:     */   
/* 1395:     */   private static final long[] mk_tokenSet_1()
/* 1396:     */   {
/* 1397:1323 */     long[] arrayOfLong = { 4294977024L, 0L, 0L, 0L, 0L };
/* 1398:1324 */     return arrayOfLong;
/* 1399:     */   }
/* 1400:     */   
/* 1401:1326 */   public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
/* 1402:     */   
/* 1403:     */   private static final long[] mk_tokenSet_2()
/* 1404:     */   {
/* 1405:1328 */     long[] arrayOfLong = new long[8];
/* 1406:1329 */     arrayOfLong[0] = -2199023255560L;
/* 1407:1330 */     for (int i = 1; i <= 3; i++) {
/* 1408:1330 */       arrayOfLong[i] = -1L;
/* 1409:     */     }
/* 1410:1331 */     return arrayOfLong;
/* 1411:     */   }
/* 1412:     */   
/* 1413:1333 */   public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
/* 1414:     */   
/* 1415:     */   private static final long[] mk_tokenSet_3()
/* 1416:     */   {
/* 1417:1335 */     long[] arrayOfLong = new long[8];
/* 1418:1336 */     arrayOfLong[0] = -576462951326679048L;
/* 1419:1337 */     for (int i = 1; i <= 3; i++) {
/* 1420:1337 */       arrayOfLong[i] = -1L;
/* 1421:     */     }
/* 1422:1338 */     return arrayOfLong;
/* 1423:     */   }
/* 1424:     */   
/* 1425:1340 */   public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
/* 1426:     */   
/* 1427:     */   private static final long[] mk_tokenSet_4()
/* 1428:     */   {
/* 1429:1342 */     long[] arrayOfLong = { 4294977024L, 1152921504606846976L, 0L, 0L, 0L };
/* 1430:1343 */     return arrayOfLong;
/* 1431:     */   }
/* 1432:     */   
/* 1433:1345 */   public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
/* 1434:     */   
/* 1435:     */   private static final long[] mk_tokenSet_5()
/* 1436:     */   {
/* 1437:1347 */     long[] arrayOfLong = new long[8];
/* 1438:1348 */     arrayOfLong[0] = -576605355262354440L;
/* 1439:1349 */     arrayOfLong[1] = -576460752303423489L;
/* 1440:1350 */     for (int i = 2; i <= 3; i++) {
/* 1441:1350 */       arrayOfLong[i] = -1L;
/* 1442:     */     }
/* 1443:1351 */     return arrayOfLong;
/* 1444:     */   }
/* 1445:     */   
/* 1446:1353 */   public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
/* 1447:     */   
/* 1448:     */   private static final long[] mk_tokenSet_6()
/* 1449:     */   {
/* 1450:1355 */     long[] arrayOfLong = new long[8];
/* 1451:1356 */     arrayOfLong[0] = -549755813896L;
/* 1452:1357 */     for (int i = 1; i <= 3; i++) {
/* 1453:1357 */       arrayOfLong[i] = -1L;
/* 1454:     */     }
/* 1455:1358 */     return arrayOfLong;
/* 1456:     */   }
/* 1457:     */   
/* 1458:1360 */   public static final BitSet _tokenSet_6 = new BitSet(mk_tokenSet_6());
/* 1459:     */   
/* 1460:     */   private static final long[] mk_tokenSet_7()
/* 1461:     */   {
/* 1462:1362 */     long[] arrayOfLong = new long[8];
/* 1463:1363 */     arrayOfLong[0] = -17179869192L;
/* 1464:1364 */     arrayOfLong[1] = -268435457L;
/* 1465:1365 */     for (int i = 2; i <= 3; i++) {
/* 1466:1365 */       arrayOfLong[i] = -1L;
/* 1467:     */     }
/* 1468:1366 */     return arrayOfLong;
/* 1469:     */   }
/* 1470:     */   
/* 1471:1368 */   public static final BitSet _tokenSet_7 = new BitSet(mk_tokenSet_7());
/* 1472:     */   
/* 1473:     */   private static final long[] mk_tokenSet_8()
/* 1474:     */   {
/* 1475:1370 */     long[] arrayOfLong = new long[8];
/* 1476:1371 */     arrayOfLong[0] = -549755813896L;
/* 1477:1372 */     arrayOfLong[1] = -268435457L;
/* 1478:1373 */     for (int i = 2; i <= 3; i++) {
/* 1479:1373 */       arrayOfLong[i] = -1L;
/* 1480:     */     }
/* 1481:1374 */     return arrayOfLong;
/* 1482:     */   }
/* 1483:     */   
/* 1484:1376 */   public static final BitSet _tokenSet_8 = new BitSet(mk_tokenSet_8());
/* 1485:     */   
/* 1486:     */   private static final long[] mk_tokenSet_9()
/* 1487:     */   {
/* 1488:1378 */     long[] arrayOfLong = { 140758963201536L, 576460752303423488L, 0L, 0L, 0L };
/* 1489:1379 */     return arrayOfLong;
/* 1490:     */   }
/* 1491:     */   
/* 1492:1381 */   public static final BitSet _tokenSet_9 = new BitSet(mk_tokenSet_9());
/* 1493:     */   
/* 1494:     */   private static final long[] mk_tokenSet_10()
/* 1495:     */   {
/* 1496:1383 */     long[] arrayOfLong = { 140741783332352L, 576460752303423488L, 0L, 0L, 0L };
/* 1497:1384 */     return arrayOfLong;
/* 1498:     */   }
/* 1499:     */   
/* 1500:1386 */   public static final BitSet _tokenSet_10 = new BitSet(mk_tokenSet_10());
/* 1501:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.preprocessor.PreprocessorLexer
 * JD-Core Version:    0.7.0.1
 */