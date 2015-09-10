/*    1:     */ package org.hibernate.hql.internal.antlr;
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
/*   24:     */ public class HqlBaseLexer
/*   25:     */   extends CharScanner
/*   26:     */   implements HqlTokenTypes, TokenStream
/*   27:     */ {
/*   28:     */   protected void setPossibleID(boolean possibleID) {}
/*   29:     */   
/*   30:     */   public HqlBaseLexer(InputStream in)
/*   31:     */   {
/*   32:  45 */     this(new ByteBuffer(in));
/*   33:     */   }
/*   34:     */   
/*   35:     */   public HqlBaseLexer(Reader in)
/*   36:     */   {
/*   37:  48 */     this(new CharBuffer(in));
/*   38:     */   }
/*   39:     */   
/*   40:     */   public HqlBaseLexer(InputBuffer ib)
/*   41:     */   {
/*   42:  51 */     this(new LexerSharedInputState(ib));
/*   43:     */   }
/*   44:     */   
/*   45:     */   public HqlBaseLexer(LexerSharedInputState state)
/*   46:     */   {
/*   47:  54 */     super(state);
/*   48:  55 */     this.caseSensitiveLiterals = false;
/*   49:  56 */     setCaseSensitive(false);
/*   50:  57 */     this.literals = new Hashtable();
/*   51:  58 */     this.literals.put(new ANTLRHashString("between", this), new Integer(10));
/*   52:  59 */     this.literals.put(new ANTLRHashString("case", this), new Integer(54));
/*   53:  60 */     this.literals.put(new ANTLRHashString("delete", this), new Integer(13));
/*   54:  61 */     this.literals.put(new ANTLRHashString("new", this), new Integer(37));
/*   55:  62 */     this.literals.put(new ANTLRHashString("end", this), new Integer(55));
/*   56:  63 */     this.literals.put(new ANTLRHashString("object", this), new Integer(65));
/*   57:  64 */     this.literals.put(new ANTLRHashString("insert", this), new Integer(29));
/*   58:  65 */     this.literals.put(new ANTLRHashString("distinct", this), new Integer(16));
/*   59:  66 */     this.literals.put(new ANTLRHashString("where", this), new Integer(53));
/*   60:  67 */     this.literals.put(new ANTLRHashString("trailing", this), new Integer(67));
/*   61:  68 */     this.literals.put(new ANTLRHashString("then", this), new Integer(57));
/*   62:  69 */     this.literals.put(new ANTLRHashString("select", this), new Integer(45));
/*   63:  70 */     this.literals.put(new ANTLRHashString("and", this), new Integer(6));
/*   64:  71 */     this.literals.put(new ANTLRHashString("outer", this), new Integer(42));
/*   65:  72 */     this.literals.put(new ANTLRHashString("not", this), new Integer(38));
/*   66:  73 */     this.literals.put(new ANTLRHashString("fetch", this), new Integer(21));
/*   67:  74 */     this.literals.put(new ANTLRHashString("from", this), new Integer(22));
/*   68:  75 */     this.literals.put(new ANTLRHashString("null", this), new Integer(39));
/*   69:  76 */     this.literals.put(new ANTLRHashString("count", this), new Integer(12));
/*   70:  77 */     this.literals.put(new ANTLRHashString("like", this), new Integer(34));
/*   71:  78 */     this.literals.put(new ANTLRHashString("when", this), new Integer(58));
/*   72:  79 */     this.literals.put(new ANTLRHashString("class", this), new Integer(11));
/*   73:  80 */     this.literals.put(new ANTLRHashString("inner", this), new Integer(28));
/*   74:  81 */     this.literals.put(new ANTLRHashString("leading", this), new Integer(63));
/*   75:  82 */     this.literals.put(new ANTLRHashString("with", this), new Integer(60));
/*   76:  83 */     this.literals.put(new ANTLRHashString("set", this), new Integer(46));
/*   77:  84 */     this.literals.put(new ANTLRHashString("escape", this), new Integer(18));
/*   78:  85 */     this.literals.put(new ANTLRHashString("join", this), new Integer(32));
/*   79:  86 */     this.literals.put(new ANTLRHashString("elements", this), new Integer(17));
/*   80:  87 */     this.literals.put(new ANTLRHashString("of", this), new Integer(66));
/*   81:  88 */     this.literals.put(new ANTLRHashString("is", this), new Integer(31));
/*   82:  89 */     this.literals.put(new ANTLRHashString("member", this), new Integer(64));
/*   83:  90 */     this.literals.put(new ANTLRHashString("or", this), new Integer(40));
/*   84:  91 */     this.literals.put(new ANTLRHashString("any", this), new Integer(5));
/*   85:  92 */     this.literals.put(new ANTLRHashString("full", this), new Integer(23));
/*   86:  93 */     this.literals.put(new ANTLRHashString("min", this), new Integer(36));
/*   87:  94 */     this.literals.put(new ANTLRHashString("as", this), new Integer(7));
/*   88:  95 */     this.literals.put(new ANTLRHashString("by", this), new Integer(105));
/*   89:  96 */     this.literals.put(new ANTLRHashString("all", this), new Integer(4));
/*   90:  97 */     this.literals.put(new ANTLRHashString("union", this), new Integer(50));
/*   91:  98 */     this.literals.put(new ANTLRHashString("order", this), new Integer(41));
/*   92:  99 */     this.literals.put(new ANTLRHashString("both", this), new Integer(61));
/*   93: 100 */     this.literals.put(new ANTLRHashString("some", this), new Integer(47));
/*   94: 101 */     this.literals.put(new ANTLRHashString("properties", this), new Integer(43));
/*   95: 102 */     this.literals.put(new ANTLRHashString("ascending", this), new Integer(106));
/*   96: 103 */     this.literals.put(new ANTLRHashString("descending", this), new Integer(107));
/*   97: 104 */     this.literals.put(new ANTLRHashString("false", this), new Integer(20));
/*   98: 105 */     this.literals.put(new ANTLRHashString("exists", this), new Integer(19));
/*   99: 106 */     this.literals.put(new ANTLRHashString("asc", this), new Integer(8));
/*  100: 107 */     this.literals.put(new ANTLRHashString("left", this), new Integer(33));
/*  101: 108 */     this.literals.put(new ANTLRHashString("desc", this), new Integer(14));
/*  102: 109 */     this.literals.put(new ANTLRHashString("max", this), new Integer(35));
/*  103: 110 */     this.literals.put(new ANTLRHashString("empty", this), new Integer(62));
/*  104: 111 */     this.literals.put(new ANTLRHashString("sum", this), new Integer(48));
/*  105: 112 */     this.literals.put(new ANTLRHashString("on", this), new Integer(59));
/*  106: 113 */     this.literals.put(new ANTLRHashString("into", this), new Integer(30));
/*  107: 114 */     this.literals.put(new ANTLRHashString("else", this), new Integer(56));
/*  108: 115 */     this.literals.put(new ANTLRHashString("right", this), new Integer(44));
/*  109: 116 */     this.literals.put(new ANTLRHashString("versioned", this), new Integer(52));
/*  110: 117 */     this.literals.put(new ANTLRHashString("in", this), new Integer(26));
/*  111: 118 */     this.literals.put(new ANTLRHashString("avg", this), new Integer(9));
/*  112: 119 */     this.literals.put(new ANTLRHashString("update", this), new Integer(51));
/*  113: 120 */     this.literals.put(new ANTLRHashString("true", this), new Integer(49));
/*  114: 121 */     this.literals.put(new ANTLRHashString("group", this), new Integer(24));
/*  115: 122 */     this.literals.put(new ANTLRHashString("having", this), new Integer(25));
/*  116: 123 */     this.literals.put(new ANTLRHashString("indices", this), new Integer(27));
/*  117:     */   }
/*  118:     */   
/*  119:     */   public Token nextToken()
/*  120:     */     throws TokenStreamException
/*  121:     */   {
/*  122: 127 */     Token theRetToken = null;
/*  123:     */     for (;;)
/*  124:     */     {
/*  125: 130 */       Token _token = null;
/*  126: 131 */       int _ttype = 0;
/*  127: 132 */       resetText();
/*  128:     */       try
/*  129:     */       {
/*  130: 135 */         switch (LA(1))
/*  131:     */         {
/*  132:     */         case '=': 
/*  133: 138 */           mEQ(true);
/*  134: 139 */           theRetToken = this._returnToken;
/*  135: 140 */           break;
/*  136:     */         case '!': 
/*  137:     */         case '^': 
/*  138: 144 */           mNE(true);
/*  139: 145 */           theRetToken = this._returnToken;
/*  140: 146 */           break;
/*  141:     */         case ',': 
/*  142: 150 */           mCOMMA(true);
/*  143: 151 */           theRetToken = this._returnToken;
/*  144: 152 */           break;
/*  145:     */         case '(': 
/*  146: 156 */           mOPEN(true);
/*  147: 157 */           theRetToken = this._returnToken;
/*  148: 158 */           break;
/*  149:     */         case ')': 
/*  150: 162 */           mCLOSE(true);
/*  151: 163 */           theRetToken = this._returnToken;
/*  152: 164 */           break;
/*  153:     */         case '[': 
/*  154: 168 */           mOPEN_BRACKET(true);
/*  155: 169 */           theRetToken = this._returnToken;
/*  156: 170 */           break;
/*  157:     */         case ']': 
/*  158: 174 */           mCLOSE_BRACKET(true);
/*  159: 175 */           theRetToken = this._returnToken;
/*  160: 176 */           break;
/*  161:     */         case '|': 
/*  162: 180 */           mCONCAT(true);
/*  163: 181 */           theRetToken = this._returnToken;
/*  164: 182 */           break;
/*  165:     */         case '+': 
/*  166: 186 */           mPLUS(true);
/*  167: 187 */           theRetToken = this._returnToken;
/*  168: 188 */           break;
/*  169:     */         case '-': 
/*  170: 192 */           mMINUS(true);
/*  171: 193 */           theRetToken = this._returnToken;
/*  172: 194 */           break;
/*  173:     */         case '*': 
/*  174: 198 */           mSTAR(true);
/*  175: 199 */           theRetToken = this._returnToken;
/*  176: 200 */           break;
/*  177:     */         case '/': 
/*  178: 204 */           mDIV(true);
/*  179: 205 */           theRetToken = this._returnToken;
/*  180: 206 */           break;
/*  181:     */         case '%': 
/*  182: 210 */           mMOD(true);
/*  183: 211 */           theRetToken = this._returnToken;
/*  184: 212 */           break;
/*  185:     */         case ':': 
/*  186: 216 */           mCOLON(true);
/*  187: 217 */           theRetToken = this._returnToken;
/*  188: 218 */           break;
/*  189:     */         case '?': 
/*  190: 222 */           mPARAM(true);
/*  191: 223 */           theRetToken = this._returnToken;
/*  192: 224 */           break;
/*  193:     */         case '\'': 
/*  194: 228 */           mQUOTED_STRING(true);
/*  195: 229 */           theRetToken = this._returnToken;
/*  196: 230 */           break;
/*  197:     */         case '\t': 
/*  198:     */         case '\n': 
/*  199:     */         case '\r': 
/*  200:     */         case ' ': 
/*  201: 234 */           mWS(true);
/*  202: 235 */           theRetToken = this._returnToken;
/*  203: 236 */           break;
/*  204:     */         case '.': 
/*  205:     */         case '0': 
/*  206:     */         case '1': 
/*  207:     */         case '2': 
/*  208:     */         case '3': 
/*  209:     */         case '4': 
/*  210:     */         case '5': 
/*  211:     */         case '6': 
/*  212:     */         case '7': 
/*  213:     */         case '8': 
/*  214:     */         case '9': 
/*  215: 242 */           mNUM_INT(true);
/*  216: 243 */           theRetToken = this._returnToken;
/*  217: 244 */           break;
/*  218:     */         case '\013': 
/*  219:     */         case '\f': 
/*  220:     */         case '\016': 
/*  221:     */         case '\017': 
/*  222:     */         case '\020': 
/*  223:     */         case '\021': 
/*  224:     */         case '\022': 
/*  225:     */         case '\023': 
/*  226:     */         case '\024': 
/*  227:     */         case '\025': 
/*  228:     */         case '\026': 
/*  229:     */         case '\027': 
/*  230:     */         case '\030': 
/*  231:     */         case '\031': 
/*  232:     */         case '\032': 
/*  233:     */         case '\033': 
/*  234:     */         case '\034': 
/*  235:     */         case '\035': 
/*  236:     */         case '\036': 
/*  237:     */         case '\037': 
/*  238:     */         case '"': 
/*  239:     */         case '#': 
/*  240:     */         case '$': 
/*  241:     */         case '&': 
/*  242:     */         case ';': 
/*  243:     */         case '<': 
/*  244:     */         case '>': 
/*  245:     */         case '@': 
/*  246:     */         case 'A': 
/*  247:     */         case 'B': 
/*  248:     */         case 'C': 
/*  249:     */         case 'D': 
/*  250:     */         case 'E': 
/*  251:     */         case 'F': 
/*  252:     */         case 'G': 
/*  253:     */         case 'H': 
/*  254:     */         case 'I': 
/*  255:     */         case 'J': 
/*  256:     */         case 'K': 
/*  257:     */         case 'L': 
/*  258:     */         case 'M': 
/*  259:     */         case 'N': 
/*  260:     */         case 'O': 
/*  261:     */         case 'P': 
/*  262:     */         case 'Q': 
/*  263:     */         case 'R': 
/*  264:     */         case 'S': 
/*  265:     */         case 'T': 
/*  266:     */         case 'U': 
/*  267:     */         case 'V': 
/*  268:     */         case 'W': 
/*  269:     */         case 'X': 
/*  270:     */         case 'Y': 
/*  271:     */         case 'Z': 
/*  272:     */         case '\\': 
/*  273:     */         case '_': 
/*  274:     */         case '`': 
/*  275:     */         case 'a': 
/*  276:     */         case 'b': 
/*  277:     */         case 'c': 
/*  278:     */         case 'd': 
/*  279:     */         case 'e': 
/*  280:     */         case 'f': 
/*  281:     */         case 'g': 
/*  282:     */         case 'h': 
/*  283:     */         case 'i': 
/*  284:     */         case 'j': 
/*  285:     */         case 'k': 
/*  286:     */         case 'l': 
/*  287:     */         case 'm': 
/*  288:     */         case 'n': 
/*  289:     */         case 'o': 
/*  290:     */         case 'p': 
/*  291:     */         case 'q': 
/*  292:     */         case 'r': 
/*  293:     */         case 's': 
/*  294:     */         case 't': 
/*  295:     */         case 'u': 
/*  296:     */         case 'v': 
/*  297:     */         case 'w': 
/*  298:     */         case 'x': 
/*  299:     */         case 'y': 
/*  300:     */         case 'z': 
/*  301:     */         case '{': 
/*  302:     */         default: 
/*  303: 247 */           if ((LA(1) == '<') && (LA(2) == '>'))
/*  304:     */           {
/*  305: 248 */             mSQL_NE(true);
/*  306: 249 */             theRetToken = this._returnToken;
/*  307:     */           }
/*  308: 251 */           else if ((LA(1) == '<') && (LA(2) == '='))
/*  309:     */           {
/*  310: 252 */             mLE(true);
/*  311: 253 */             theRetToken = this._returnToken;
/*  312:     */           }
/*  313: 255 */           else if ((LA(1) == '>') && (LA(2) == '='))
/*  314:     */           {
/*  315: 256 */             mGE(true);
/*  316: 257 */             theRetToken = this._returnToken;
/*  317:     */           }
/*  318: 259 */           else if (LA(1) == '<')
/*  319:     */           {
/*  320: 260 */             mLT(true);
/*  321: 261 */             theRetToken = this._returnToken;
/*  322:     */           }
/*  323: 263 */           else if (LA(1) == '>')
/*  324:     */           {
/*  325: 264 */             mGT(true);
/*  326: 265 */             theRetToken = this._returnToken;
/*  327:     */           }
/*  328: 267 */           else if (_tokenSet_0.member(LA(1)))
/*  329:     */           {
/*  330: 268 */             mIDENT(true);
/*  331: 269 */             theRetToken = this._returnToken;
/*  332:     */           }
/*  333: 272 */           else if (LA(1) == 65535)
/*  334:     */           {
/*  335: 272 */             uponEOF();this._returnToken = makeToken(1);
/*  336:     */           }
/*  337:     */           else
/*  338:     */           {
/*  339: 273 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  340:     */           }
/*  341:     */           break;
/*  342:     */         }
/*  343: 276 */         if (this._returnToken == null) {
/*  344:     */           continue;
/*  345:     */         }
/*  346: 277 */         _ttype = this._returnToken.getType();
/*  347: 278 */         this._returnToken.setType(_ttype);
/*  348: 279 */         return this._returnToken;
/*  349:     */       }
/*  350:     */       catch (RecognitionException e)
/*  351:     */       {
/*  352: 282 */         throw new TokenStreamRecognitionException(e);
/*  353:     */       }
/*  354:     */       catch (CharStreamException cse)
/*  355:     */       {
/*  356: 286 */         if ((cse instanceof CharStreamIOException)) {
/*  357: 287 */           throw new TokenStreamIOException(((CharStreamIOException)cse).io);
/*  358:     */         }
/*  359: 290 */         throw new TokenStreamException(cse.getMessage());
/*  360:     */       }
/*  361:     */     }
/*  362:     */   }
/*  363:     */   
/*  364:     */   public final void mEQ(boolean _createToken)
/*  365:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  366:     */   {
/*  367: 297 */     Token _token = null;int _begin = this.text.length();
/*  368: 298 */     int _ttype = 102;
/*  369:     */     
/*  370:     */ 
/*  371: 301 */     match('=');
/*  372: 302 */     if ((_createToken) && (_token == null) && (_ttype != -1))
/*  373:     */     {
/*  374: 303 */       _token = makeToken(_ttype);
/*  375: 304 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*  376:     */     }
/*  377: 306 */     this._returnToken = _token;
/*  378:     */   }
/*  379:     */   
/*  380:     */   public final void mLT(boolean _createToken)
/*  381:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  382:     */   {
/*  383: 310 */     Token _token = null;int _begin = this.text.length();
/*  384: 311 */     int _ttype = 110;
/*  385:     */     
/*  386:     */ 
/*  387: 314 */     match('<');
/*  388: 315 */     if ((_createToken) && (_token == null) && (_ttype != -1))
/*  389:     */     {
/*  390: 316 */       _token = makeToken(_ttype);
/*  391: 317 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*  392:     */     }
/*  393: 319 */     this._returnToken = _token;
/*  394:     */   }
/*  395:     */   
/*  396:     */   public final void mGT(boolean _createToken)
/*  397:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  398:     */   {
/*  399: 323 */     Token _token = null;int _begin = this.text.length();
/*  400: 324 */     int _ttype = 111;
/*  401:     */     
/*  402:     */ 
/*  403: 327 */     match('>');
/*  404: 328 */     if ((_createToken) && (_token == null) && (_ttype != -1))
/*  405:     */     {
/*  406: 329 */       _token = makeToken(_ttype);
/*  407: 330 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*  408:     */     }
/*  409: 332 */     this._returnToken = _token;
/*  410:     */   }
/*  411:     */   
/*  412:     */   public final void mSQL_NE(boolean _createToken)
/*  413:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  414:     */   {
/*  415: 336 */     Token _token = null;int _begin = this.text.length();
/*  416: 337 */     int _ttype = 109;
/*  417:     */     
/*  418:     */ 
/*  419: 340 */     match("<>");
/*  420: 341 */     if ((_createToken) && (_token == null) && (_ttype != -1))
/*  421:     */     {
/*  422: 342 */       _token = makeToken(_ttype);
/*  423: 343 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*  424:     */     }
/*  425: 345 */     this._returnToken = _token;
/*  426:     */   }
/*  427:     */   
/*  428:     */   public final void mNE(boolean _createToken)
/*  429:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  430:     */   {
/*  431: 349 */     Token _token = null;int _begin = this.text.length();
/*  432: 350 */     int _ttype = 108;
/*  433: 353 */     switch (LA(1))
/*  434:     */     {
/*  435:     */     case '!': 
/*  436: 356 */       match("!=");
/*  437: 357 */       break;
/*  438:     */     case '^': 
/*  439: 361 */       match("^=");
/*  440: 362 */       break;
/*  441:     */     default: 
/*  442: 366 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  443:     */     }
/*  444: 369 */     if ((_createToken) && (_token == null) && (_ttype != -1))
/*  445:     */     {
/*  446: 370 */       _token = makeToken(_ttype);
/*  447: 371 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*  448:     */     }
/*  449: 373 */     this._returnToken = _token;
/*  450:     */   }
/*  451:     */   
/*  452:     */   public final void mLE(boolean _createToken)
/*  453:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  454:     */   {
/*  455: 377 */     Token _token = null;int _begin = this.text.length();
/*  456: 378 */     int _ttype = 112;
/*  457:     */     
/*  458:     */ 
/*  459: 381 */     match("<=");
/*  460: 382 */     if ((_createToken) && (_token == null) && (_ttype != -1))
/*  461:     */     {
/*  462: 383 */       _token = makeToken(_ttype);
/*  463: 384 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*  464:     */     }
/*  465: 386 */     this._returnToken = _token;
/*  466:     */   }
/*  467:     */   
/*  468:     */   public final void mGE(boolean _createToken)
/*  469:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  470:     */   {
/*  471: 390 */     Token _token = null;int _begin = this.text.length();
/*  472: 391 */     int _ttype = 113;
/*  473:     */     
/*  474:     */ 
/*  475: 394 */     match(">=");
/*  476: 395 */     if ((_createToken) && (_token == null) && (_ttype != -1))
/*  477:     */     {
/*  478: 396 */       _token = makeToken(_ttype);
/*  479: 397 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*  480:     */     }
/*  481: 399 */     this._returnToken = _token;
/*  482:     */   }
/*  483:     */   
/*  484:     */   public final void mCOMMA(boolean _createToken)
/*  485:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  486:     */   {
/*  487: 403 */     Token _token = null;int _begin = this.text.length();
/*  488: 404 */     int _ttype = 101;
/*  489:     */     
/*  490:     */ 
/*  491: 407 */     match(',');
/*  492: 408 */     if ((_createToken) && (_token == null) && (_ttype != -1))
/*  493:     */     {
/*  494: 409 */       _token = makeToken(_ttype);
/*  495: 410 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*  496:     */     }
/*  497: 412 */     this._returnToken = _token;
/*  498:     */   }
/*  499:     */   
/*  500:     */   public final void mOPEN(boolean _createToken)
/*  501:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  502:     */   {
/*  503: 416 */     Token _token = null;int _begin = this.text.length();
/*  504: 417 */     int _ttype = 103;
/*  505:     */     
/*  506:     */ 
/*  507: 420 */     match('(');
/*  508: 421 */     if ((_createToken) && (_token == null) && (_ttype != -1))
/*  509:     */     {
/*  510: 422 */       _token = makeToken(_ttype);
/*  511: 423 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*  512:     */     }
/*  513: 425 */     this._returnToken = _token;
/*  514:     */   }
/*  515:     */   
/*  516:     */   public final void mCLOSE(boolean _createToken)
/*  517:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  518:     */   {
/*  519: 429 */     Token _token = null;int _begin = this.text.length();
/*  520: 430 */     int _ttype = 104;
/*  521:     */     
/*  522:     */ 
/*  523: 433 */     match(')');
/*  524: 434 */     if ((_createToken) && (_token == null) && (_ttype != -1))
/*  525:     */     {
/*  526: 435 */       _token = makeToken(_ttype);
/*  527: 436 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*  528:     */     }
/*  529: 438 */     this._returnToken = _token;
/*  530:     */   }
/*  531:     */   
/*  532:     */   public final void mOPEN_BRACKET(boolean _createToken)
/*  533:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  534:     */   {
/*  535: 442 */     Token _token = null;int _begin = this.text.length();
/*  536: 443 */     int _ttype = 120;
/*  537:     */     
/*  538:     */ 
/*  539: 446 */     match('[');
/*  540: 447 */     if ((_createToken) && (_token == null) && (_ttype != -1))
/*  541:     */     {
/*  542: 448 */       _token = makeToken(_ttype);
/*  543: 449 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*  544:     */     }
/*  545: 451 */     this._returnToken = _token;
/*  546:     */   }
/*  547:     */   
/*  548:     */   public final void mCLOSE_BRACKET(boolean _createToken)
/*  549:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  550:     */   {
/*  551: 455 */     Token _token = null;int _begin = this.text.length();
/*  552: 456 */     int _ttype = 121;
/*  553:     */     
/*  554:     */ 
/*  555: 459 */     match(']');
/*  556: 460 */     if ((_createToken) && (_token == null) && (_ttype != -1))
/*  557:     */     {
/*  558: 461 */       _token = makeToken(_ttype);
/*  559: 462 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*  560:     */     }
/*  561: 464 */     this._returnToken = _token;
/*  562:     */   }
/*  563:     */   
/*  564:     */   public final void mCONCAT(boolean _createToken)
/*  565:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  566:     */   {
/*  567: 468 */     Token _token = null;int _begin = this.text.length();
/*  568: 469 */     int _ttype = 114;
/*  569:     */     
/*  570:     */ 
/*  571: 472 */     match("||");
/*  572: 473 */     if ((_createToken) && (_token == null) && (_ttype != -1))
/*  573:     */     {
/*  574: 474 */       _token = makeToken(_ttype);
/*  575: 475 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*  576:     */     }
/*  577: 477 */     this._returnToken = _token;
/*  578:     */   }
/*  579:     */   
/*  580:     */   public final void mPLUS(boolean _createToken)
/*  581:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  582:     */   {
/*  583: 481 */     Token _token = null;int _begin = this.text.length();
/*  584: 482 */     int _ttype = 115;
/*  585:     */     
/*  586:     */ 
/*  587: 485 */     match('+');
/*  588: 486 */     if ((_createToken) && (_token == null) && (_ttype != -1))
/*  589:     */     {
/*  590: 487 */       _token = makeToken(_ttype);
/*  591: 488 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*  592:     */     }
/*  593: 490 */     this._returnToken = _token;
/*  594:     */   }
/*  595:     */   
/*  596:     */   public final void mMINUS(boolean _createToken)
/*  597:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  598:     */   {
/*  599: 494 */     Token _token = null;int _begin = this.text.length();
/*  600: 495 */     int _ttype = 116;
/*  601:     */     
/*  602:     */ 
/*  603: 498 */     match('-');
/*  604: 499 */     if ((_createToken) && (_token == null) && (_ttype != -1))
/*  605:     */     {
/*  606: 500 */       _token = makeToken(_ttype);
/*  607: 501 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*  608:     */     }
/*  609: 503 */     this._returnToken = _token;
/*  610:     */   }
/*  611:     */   
/*  612:     */   public final void mSTAR(boolean _createToken)
/*  613:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  614:     */   {
/*  615: 507 */     Token _token = null;int _begin = this.text.length();
/*  616: 508 */     int _ttype = 117;
/*  617:     */     
/*  618:     */ 
/*  619: 511 */     match('*');
/*  620: 512 */     if ((_createToken) && (_token == null) && (_ttype != -1))
/*  621:     */     {
/*  622: 513 */       _token = makeToken(_ttype);
/*  623: 514 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*  624:     */     }
/*  625: 516 */     this._returnToken = _token;
/*  626:     */   }
/*  627:     */   
/*  628:     */   public final void mDIV(boolean _createToken)
/*  629:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  630:     */   {
/*  631: 520 */     Token _token = null;int _begin = this.text.length();
/*  632: 521 */     int _ttype = 118;
/*  633:     */     
/*  634:     */ 
/*  635: 524 */     match('/');
/*  636: 525 */     if ((_createToken) && (_token == null) && (_ttype != -1))
/*  637:     */     {
/*  638: 526 */       _token = makeToken(_ttype);
/*  639: 527 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*  640:     */     }
/*  641: 529 */     this._returnToken = _token;
/*  642:     */   }
/*  643:     */   
/*  644:     */   public final void mMOD(boolean _createToken)
/*  645:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  646:     */   {
/*  647: 533 */     Token _token = null;int _begin = this.text.length();
/*  648: 534 */     int _ttype = 119;
/*  649:     */     
/*  650:     */ 
/*  651: 537 */     match('%');
/*  652: 538 */     if ((_createToken) && (_token == null) && (_ttype != -1))
/*  653:     */     {
/*  654: 539 */       _token = makeToken(_ttype);
/*  655: 540 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*  656:     */     }
/*  657: 542 */     this._returnToken = _token;
/*  658:     */   }
/*  659:     */   
/*  660:     */   public final void mCOLON(boolean _createToken)
/*  661:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  662:     */   {
/*  663: 546 */     Token _token = null;int _begin = this.text.length();
/*  664: 547 */     int _ttype = 122;
/*  665:     */     
/*  666:     */ 
/*  667: 550 */     match(':');
/*  668: 551 */     if ((_createToken) && (_token == null) && (_ttype != -1))
/*  669:     */     {
/*  670: 552 */       _token = makeToken(_ttype);
/*  671: 553 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*  672:     */     }
/*  673: 555 */     this._returnToken = _token;
/*  674:     */   }
/*  675:     */   
/*  676:     */   public final void mPARAM(boolean _createToken)
/*  677:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  678:     */   {
/*  679: 559 */     Token _token = null;int _begin = this.text.length();
/*  680: 560 */     int _ttype = 123;
/*  681:     */     
/*  682:     */ 
/*  683: 563 */     match('?');
/*  684: 564 */     if ((_createToken) && (_token == null) && (_ttype != -1))
/*  685:     */     {
/*  686: 565 */       _token = makeToken(_ttype);
/*  687: 566 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*  688:     */     }
/*  689: 568 */     this._returnToken = _token;
/*  690:     */   }
/*  691:     */   
/*  692:     */   public final void mIDENT(boolean _createToken)
/*  693:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  694:     */   {
/*  695: 572 */     Token _token = null;int _begin = this.text.length();
/*  696: 573 */     int _ttype = 126;
/*  697:     */     
/*  698:     */ 
/*  699: 576 */     mID_START_LETTER(false);
/*  700: 580 */     while (_tokenSet_1.member(LA(1))) {
/*  701: 581 */       mID_LETTER(false);
/*  702:     */     }
/*  703: 589 */     if (this.inputState.guessing == 0) {
/*  704: 592 */       setPossibleID(true);
/*  705:     */     }
/*  706: 595 */     _ttype = testLiteralsTable(_ttype);
/*  707: 596 */     if ((_createToken) && (_token == null) && (_ttype != -1))
/*  708:     */     {
/*  709: 597 */       _token = makeToken(_ttype);
/*  710: 598 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*  711:     */     }
/*  712: 600 */     this._returnToken = _token;
/*  713:     */   }
/*  714:     */   
/*  715:     */   protected final void mID_START_LETTER(boolean _createToken)
/*  716:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  717:     */   {
/*  718: 604 */     Token _token = null;int _begin = this.text.length();
/*  719: 605 */     int _ttype = 127;
/*  720: 608 */     switch (LA(1))
/*  721:     */     {
/*  722:     */     case '_': 
/*  723: 611 */       match('_');
/*  724: 612 */       break;
/*  725:     */     case '$': 
/*  726: 616 */       match('$');
/*  727: 617 */       break;
/*  728:     */     case 'a': 
/*  729:     */     case 'b': 
/*  730:     */     case 'c': 
/*  731:     */     case 'd': 
/*  732:     */     case 'e': 
/*  733:     */     case 'f': 
/*  734:     */     case 'g': 
/*  735:     */     case 'h': 
/*  736:     */     case 'i': 
/*  737:     */     case 'j': 
/*  738:     */     case 'k': 
/*  739:     */     case 'l': 
/*  740:     */     case 'm': 
/*  741:     */     case 'n': 
/*  742:     */     case 'o': 
/*  743:     */     case 'p': 
/*  744:     */     case 'q': 
/*  745:     */     case 'r': 
/*  746:     */     case 's': 
/*  747:     */     case 't': 
/*  748:     */     case 'u': 
/*  749:     */     case 'v': 
/*  750:     */     case 'w': 
/*  751:     */     case 'x': 
/*  752:     */     case 'y': 
/*  753:     */     case 'z': 
/*  754: 627 */       matchRange('a', 'z');
/*  755: 628 */       break;
/*  756:     */     case '%': 
/*  757:     */     case '&': 
/*  758:     */     case '\'': 
/*  759:     */     case '(': 
/*  760:     */     case ')': 
/*  761:     */     case '*': 
/*  762:     */     case '+': 
/*  763:     */     case ',': 
/*  764:     */     case '-': 
/*  765:     */     case '.': 
/*  766:     */     case '/': 
/*  767:     */     case '0': 
/*  768:     */     case '1': 
/*  769:     */     case '2': 
/*  770:     */     case '3': 
/*  771:     */     case '4': 
/*  772:     */     case '5': 
/*  773:     */     case '6': 
/*  774:     */     case '7': 
/*  775:     */     case '8': 
/*  776:     */     case '9': 
/*  777:     */     case ':': 
/*  778:     */     case ';': 
/*  779:     */     case '<': 
/*  780:     */     case '=': 
/*  781:     */     case '>': 
/*  782:     */     case '?': 
/*  783:     */     case '@': 
/*  784:     */     case 'A': 
/*  785:     */     case 'B': 
/*  786:     */     case 'C': 
/*  787:     */     case 'D': 
/*  788:     */     case 'E': 
/*  789:     */     case 'F': 
/*  790:     */     case 'G': 
/*  791:     */     case 'H': 
/*  792:     */     case 'I': 
/*  793:     */     case 'J': 
/*  794:     */     case 'K': 
/*  795:     */     case 'L': 
/*  796:     */     case 'M': 
/*  797:     */     case 'N': 
/*  798:     */     case 'O': 
/*  799:     */     case 'P': 
/*  800:     */     case 'Q': 
/*  801:     */     case 'R': 
/*  802:     */     case 'S': 
/*  803:     */     case 'T': 
/*  804:     */     case 'U': 
/*  805:     */     case 'V': 
/*  806:     */     case 'W': 
/*  807:     */     case 'X': 
/*  808:     */     case 'Y': 
/*  809:     */     case 'Z': 
/*  810:     */     case '[': 
/*  811:     */     case '\\': 
/*  812:     */     case ']': 
/*  813:     */     case '^': 
/*  814:     */     case '`': 
/*  815:     */     default: 
/*  816: 631 */       if ((LA(1) >= '') && (LA(1) <= 65534)) {
/*  817: 632 */         matchRange('', 65534);
/*  818:     */       } else {
/*  819: 635 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  820:     */       }
/*  821:     */       break;
/*  822:     */     }
/*  823: 638 */     if ((_createToken) && (_token == null) && (_ttype != -1))
/*  824:     */     {
/*  825: 639 */       _token = makeToken(_ttype);
/*  826: 640 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*  827:     */     }
/*  828: 642 */     this._returnToken = _token;
/*  829:     */   }
/*  830:     */   
/*  831:     */   protected final void mID_LETTER(boolean _createToken)
/*  832:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  833:     */   {
/*  834: 646 */     Token _token = null;int _begin = this.text.length();
/*  835: 647 */     int _ttype = 128;
/*  836: 650 */     if (_tokenSet_0.member(LA(1))) {
/*  837: 651 */       mID_START_LETTER(false);
/*  838: 653 */     } else if ((LA(1) >= '0') && (LA(1) <= '9')) {
/*  839: 654 */       matchRange('0', '9');
/*  840:     */     } else {
/*  841: 657 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  842:     */     }
/*  843: 660 */     if ((_createToken) && (_token == null) && (_ttype != -1))
/*  844:     */     {
/*  845: 661 */       _token = makeToken(_ttype);
/*  846: 662 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*  847:     */     }
/*  848: 664 */     this._returnToken = _token;
/*  849:     */   }
/*  850:     */   
/*  851:     */   public final void mQUOTED_STRING(boolean _createToken)
/*  852:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  853:     */   {
/*  854: 668 */     Token _token = null;int _begin = this.text.length();
/*  855: 669 */     int _ttype = 125;
/*  856:     */     
/*  857:     */ 
/*  858: 672 */     match('\'');
/*  859:     */     for (;;)
/*  860:     */     {
/*  861: 676 */       boolean synPredMatched221 = false;
/*  862: 677 */       if ((LA(1) == '\'') && (LA(2) == '\''))
/*  863:     */       {
/*  864: 678 */         int _m221 = mark();
/*  865: 679 */         synPredMatched221 = true;
/*  866: 680 */         this.inputState.guessing += 1;
/*  867:     */         try
/*  868:     */         {
/*  869: 683 */           mESCqs(false);
/*  870:     */         }
/*  871:     */         catch (RecognitionException pe)
/*  872:     */         {
/*  873: 687 */           synPredMatched221 = false;
/*  874:     */         }
/*  875: 689 */         rewind(_m221);
/*  876: 690 */         this.inputState.guessing -= 1;
/*  877:     */       }
/*  878: 692 */       if (synPredMatched221)
/*  879:     */       {
/*  880: 693 */         mESCqs(false);
/*  881:     */       }
/*  882:     */       else
/*  883:     */       {
/*  884: 695 */         if (!_tokenSet_2.member(LA(1))) {
/*  885:     */           break;
/*  886:     */         }
/*  887: 696 */         matchNot('\'');
/*  888:     */       }
/*  889:     */     }
/*  890: 704 */     match('\'');
/*  891: 705 */     if ((_createToken) && (_token == null) && (_ttype != -1))
/*  892:     */     {
/*  893: 706 */       _token = makeToken(_ttype);
/*  894: 707 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*  895:     */     }
/*  896: 709 */     this._returnToken = _token;
/*  897:     */   }
/*  898:     */   
/*  899:     */   protected final void mESCqs(boolean _createToken)
/*  900:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  901:     */   {
/*  902: 713 */     Token _token = null;int _begin = this.text.length();
/*  903: 714 */     int _ttype = 129;
/*  904:     */     
/*  905:     */ 
/*  906: 717 */     match('\'');
/*  907: 718 */     match('\'');
/*  908: 719 */     if ((_createToken) && (_token == null) && (_ttype != -1))
/*  909:     */     {
/*  910: 720 */       _token = makeToken(_ttype);
/*  911: 721 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*  912:     */     }
/*  913: 723 */     this._returnToken = _token;
/*  914:     */   }
/*  915:     */   
/*  916:     */   public final void mWS(boolean _createToken)
/*  917:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  918:     */   {
/*  919: 727 */     Token _token = null;int _begin = this.text.length();
/*  920: 728 */     int _ttype = 130;
/*  921: 732 */     switch (LA(1))
/*  922:     */     {
/*  923:     */     case ' ': 
/*  924: 735 */       match(' ');
/*  925: 736 */       break;
/*  926:     */     case '\t': 
/*  927: 740 */       match('\t');
/*  928: 741 */       break;
/*  929:     */     case '\n': 
/*  930: 745 */       match('\n');
/*  931: 746 */       if (this.inputState.guessing == 0) {
/*  932: 747 */         newline();
/*  933:     */       }
/*  934:     */       break;
/*  935:     */     default: 
/*  936: 752 */       if ((LA(1) == '\r') && (LA(2) == '\n'))
/*  937:     */       {
/*  938: 753 */         match('\r');
/*  939: 754 */         match('\n');
/*  940: 755 */         if (this.inputState.guessing == 0) {
/*  941: 756 */           newline();
/*  942:     */         }
/*  943:     */       }
/*  944: 759 */       else if (LA(1) == '\r')
/*  945:     */       {
/*  946: 760 */         match('\r');
/*  947: 761 */         if (this.inputState.guessing == 0) {
/*  948: 762 */           newline();
/*  949:     */         }
/*  950:     */       }
/*  951:     */       else
/*  952:     */       {
/*  953: 766 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  954:     */       }
/*  955:     */       break;
/*  956:     */     }
/*  957: 770 */     if (this.inputState.guessing == 0) {
/*  958: 771 */       _ttype = -1;
/*  959:     */     }
/*  960: 773 */     if ((_createToken) && (_token == null) && (_ttype != -1))
/*  961:     */     {
/*  962: 774 */       _token = makeToken(_ttype);
/*  963: 775 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*  964:     */     }
/*  965: 777 */     this._returnToken = _token;
/*  966:     */   }
/*  967:     */   
/*  968:     */   public final void mNUM_INT(boolean _createToken)
/*  969:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  970:     */   {
/*  971: 781 */     Token _token = null;int _begin = this.text.length();
/*  972: 782 */     int _ttype = 124;
/*  973:     */     
/*  974: 784 */     Token f1 = null;
/*  975: 785 */     Token f2 = null;
/*  976: 786 */     Token f3 = null;
/*  977: 787 */     Token f4 = null;
/*  978: 788 */     boolean isDecimal = false;Token t = null;
/*  979: 790 */     switch (LA(1))
/*  980:     */     {
/*  981:     */     case '.': 
/*  982: 793 */       match('.');
/*  983: 794 */       if (this.inputState.guessing == 0) {
/*  984: 795 */         _ttype = 15;
/*  985:     */       }
/*  986: 798 */       if ((LA(1) >= '0') && (LA(1) <= '9'))
/*  987:     */       {
/*  988: 800 */         int _cnt229 = 0;
/*  989:     */         for (;;)
/*  990:     */         {
/*  991: 803 */           if ((LA(1) >= '0') && (LA(1) <= '9'))
/*  992:     */           {
/*  993: 804 */             matchRange('0', '9');
/*  994:     */           }
/*  995:     */           else
/*  996:     */           {
/*  997: 807 */             if (_cnt229 >= 1) {
/*  998:     */               break;
/*  999:     */             }
/* 1000: 807 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1001:     */           }
/* 1002: 810 */           _cnt229++;
/* 1003:     */         }
/* 1004: 814 */         if (LA(1) == 'e') {
/* 1005: 815 */           mEXPONENT(false);
/* 1006:     */         }
/* 1007: 822 */         if ((LA(1) == 'b') || (LA(1) == 'd') || (LA(1) == 'f'))
/* 1008:     */         {
/* 1009: 823 */           mFLOAT_SUFFIX(true);
/* 1010: 824 */           f1 = this._returnToken;
/* 1011: 825 */           if (this.inputState.guessing == 0) {
/* 1012: 826 */             t = f1;
/* 1013:     */           }
/* 1014:     */         }
/* 1015: 833 */         if (this.inputState.guessing == 0) {
/* 1016: 835 */           if ((t != null) && (t.getText().toUpperCase().indexOf("BD") >= 0)) {
/* 1017: 836 */             _ttype = 99;
/* 1018: 838 */           } else if ((t != null) && (t.getText().toUpperCase().indexOf('F') >= 0)) {
/* 1019: 839 */             _ttype = 96;
/* 1020:     */           } else {
/* 1021: 842 */             _ttype = 95;
/* 1022:     */           }
/* 1023:     */         }
/* 1024:     */       }
/* 1025:     */       break;
/* 1026:     */     case '0': 
/* 1027:     */     case '1': 
/* 1028:     */     case '2': 
/* 1029:     */     case '3': 
/* 1030:     */     case '4': 
/* 1031:     */     case '5': 
/* 1032:     */     case '6': 
/* 1033:     */     case '7': 
/* 1034:     */     case '8': 
/* 1035:     */     case '9': 
/* 1036: 858 */       switch (LA(1))
/* 1037:     */       {
/* 1038:     */       case '0': 
/* 1039: 861 */         match('0');
/* 1040: 862 */         if (this.inputState.guessing == 0) {
/* 1041: 863 */           isDecimal = true;
/* 1042:     */         }
/* 1043: 866 */         switch (LA(1))
/* 1044:     */         {
/* 1045:     */         case 'x': 
/* 1046: 870 */           match('x');
/* 1047:     */           
/* 1048:     */ 
/* 1049: 873 */           int _cnt236 = 0;
/* 1050:     */           for (;;)
/* 1051:     */           {
/* 1052: 876 */             if (_tokenSet_3.member(LA(1)))
/* 1053:     */             {
/* 1054: 877 */               mHEX_DIGIT(false);
/* 1055:     */             }
/* 1056:     */             else
/* 1057:     */             {
/* 1058: 880 */               if (_cnt236 >= 1) {
/* 1059:     */                 break;
/* 1060:     */               }
/* 1061: 880 */               throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1062:     */             }
/* 1063: 883 */             _cnt236++;
/* 1064:     */           }
/* 1065: 886 */           break;
/* 1066:     */         case '0': 
/* 1067:     */         case '1': 
/* 1068:     */         case '2': 
/* 1069:     */         case '3': 
/* 1070:     */         case '4': 
/* 1071:     */         case '5': 
/* 1072:     */         case '6': 
/* 1073:     */         case '7': 
/* 1074: 892 */           int _cnt238 = 0;
/* 1075:     */           for (;;)
/* 1076:     */           {
/* 1077: 895 */             if ((LA(1) >= '0') && (LA(1) <= '7'))
/* 1078:     */             {
/* 1079: 896 */               matchRange('0', '7');
/* 1080:     */             }
/* 1081:     */             else
/* 1082:     */             {
/* 1083: 899 */               if (_cnt238 >= 1) {
/* 1084:     */                 break;
/* 1085:     */               }
/* 1086: 899 */               throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1087:     */             }
/* 1088: 902 */             _cnt238++;
/* 1089:     */           }
/* 1090:     */         }
/* 1091: 912 */         break;
/* 1092:     */       case '1': 
/* 1093:     */       case '2': 
/* 1094:     */       case '3': 
/* 1095:     */       case '4': 
/* 1096:     */       case '5': 
/* 1097:     */       case '6': 
/* 1098:     */       case '7': 
/* 1099:     */       case '8': 
/* 1100:     */       case '9': 
/* 1101: 919 */         matchRange('1', '9');
/* 1102: 924 */         while ((LA(1) >= '0') && (LA(1) <= '9')) {
/* 1103: 925 */           matchRange('0', '9');
/* 1104:     */         }
/* 1105: 933 */         if (this.inputState.guessing == 0) {
/* 1106: 934 */           isDecimal = true;
/* 1107:     */         }
/* 1108:     */         break;
/* 1109:     */       default: 
/* 1110: 940 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1111:     */       }
/* 1112: 945 */       if ((LA(1) == 'b') && (LA(2) == 'i'))
/* 1113:     */       {
/* 1114: 947 */         match('b');
/* 1115: 948 */         match('i');
/* 1116: 950 */         if (this.inputState.guessing == 0) {
/* 1117: 951 */           _ttype = 98;
/* 1118:     */         }
/* 1119:     */       }
/* 1120: 954 */       else if (LA(1) == 'l')
/* 1121:     */       {
/* 1122: 956 */         match('l');
/* 1123: 958 */         if (this.inputState.guessing == 0) {
/* 1124: 959 */           _ttype = 97;
/* 1125:     */         }
/* 1126:     */       }
/* 1127: 962 */       else if ((_tokenSet_4.member(LA(1))) && (isDecimal))
/* 1128:     */       {
/* 1129: 964 */         switch (LA(1))
/* 1130:     */         {
/* 1131:     */         case '.': 
/* 1132: 967 */           match('.');
/* 1133: 971 */           while ((LA(1) >= '0') && (LA(1) <= '9')) {
/* 1134: 972 */             matchRange('0', '9');
/* 1135:     */           }
/* 1136: 981 */           if (LA(1) == 'e') {
/* 1137: 982 */             mEXPONENT(false);
/* 1138:     */           }
/* 1139: 989 */           if ((LA(1) == 'b') || (LA(1) == 'd') || (LA(1) == 'f'))
/* 1140:     */           {
/* 1141: 990 */             mFLOAT_SUFFIX(true);
/* 1142: 991 */             f2 = this._returnToken;
/* 1143: 992 */             if (this.inputState.guessing == 0) {
/* 1144: 993 */               t = f2;
/* 1145:     */             }
/* 1146:     */           }
/* 1147:     */           break;
/* 1148:     */         case 'e': 
/* 1149:1004 */           mEXPONENT(false);
/* 1150:1006 */           if ((LA(1) == 'b') || (LA(1) == 'd') || (LA(1) == 'f'))
/* 1151:     */           {
/* 1152:1007 */             mFLOAT_SUFFIX(true);
/* 1153:1008 */             f3 = this._returnToken;
/* 1154:1009 */             if (this.inputState.guessing == 0) {
/* 1155:1010 */               t = f3;
/* 1156:     */             }
/* 1157:     */           }
/* 1158:     */           break;
/* 1159:     */         case 'b': 
/* 1160:     */         case 'd': 
/* 1161:     */         case 'f': 
/* 1162:1021 */           mFLOAT_SUFFIX(true);
/* 1163:1022 */           f4 = this._returnToken;
/* 1164:1023 */           if (this.inputState.guessing == 0) {
/* 1165:1024 */             t = f4;
/* 1166:     */           }
/* 1167:     */           break;
/* 1168:     */         default: 
/* 1169:1030 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1170:     */         }
/* 1171:1034 */         if (this.inputState.guessing == 0) {
/* 1172:1036 */           if ((t != null) && (t.getText().toUpperCase().indexOf("BD") >= 0)) {
/* 1173:1037 */             _ttype = 99;
/* 1174:1039 */           } else if ((t != null) && (t.getText().toUpperCase().indexOf('F') >= 0)) {
/* 1175:1040 */             _ttype = 96;
/* 1176:     */           } else {
/* 1177:1043 */             _ttype = 95;
/* 1178:     */           }
/* 1179:     */         }
/* 1180:     */       }
/* 1181:     */       break;
/* 1182:     */     case '/': 
/* 1183:     */     default: 
/* 1184:1056 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1185:     */     }
/* 1186:1059 */     if ((_createToken) && (_token == null) && (_ttype != -1))
/* 1187:     */     {
/* 1188:1060 */       _token = makeToken(_ttype);
/* 1189:1061 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/* 1190:     */     }
/* 1191:1063 */     this._returnToken = _token;
/* 1192:     */   }
/* 1193:     */   
/* 1194:     */   protected final void mEXPONENT(boolean _createToken)
/* 1195:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 1196:     */   {
/* 1197:1067 */     Token _token = null;int _begin = this.text.length();
/* 1198:1068 */     int _ttype = 132;
/* 1199:     */     
/* 1200:     */ 
/* 1201:     */ 
/* 1202:1072 */     match('e');
/* 1203:1075 */     switch (LA(1))
/* 1204:     */     {
/* 1205:     */     case '+': 
/* 1206:1078 */       match('+');
/* 1207:1079 */       break;
/* 1208:     */     case '-': 
/* 1209:1083 */       match('-');
/* 1210:1084 */       break;
/* 1211:     */     case '0': 
/* 1212:     */     case '1': 
/* 1213:     */     case '2': 
/* 1214:     */     case '3': 
/* 1215:     */     case '4': 
/* 1216:     */     case '5': 
/* 1217:     */     case '6': 
/* 1218:     */     case '7': 
/* 1219:     */     case '8': 
/* 1220:     */     case '9': 
/* 1221:     */       break;
/* 1222:     */     case ',': 
/* 1223:     */     case '.': 
/* 1224:     */     case '/': 
/* 1225:     */     default: 
/* 1226:1094 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1227:     */     }
/* 1228:1099 */     int _cnt257 = 0;
/* 1229:     */     for (;;)
/* 1230:     */     {
/* 1231:1102 */       if ((LA(1) >= '0') && (LA(1) <= '9'))
/* 1232:     */       {
/* 1233:1103 */         matchRange('0', '9');
/* 1234:     */       }
/* 1235:     */       else
/* 1236:     */       {
/* 1237:1106 */         if (_cnt257 >= 1) {
/* 1238:     */           break;
/* 1239:     */         }
/* 1240:1106 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1241:     */       }
/* 1242:1109 */       _cnt257++;
/* 1243:     */     }
/* 1244:1112 */     if ((_createToken) && (_token == null) && (_ttype != -1))
/* 1245:     */     {
/* 1246:1113 */       _token = makeToken(_ttype);
/* 1247:1114 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/* 1248:     */     }
/* 1249:1116 */     this._returnToken = _token;
/* 1250:     */   }
/* 1251:     */   
/* 1252:     */   protected final void mFLOAT_SUFFIX(boolean _createToken)
/* 1253:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 1254:     */   {
/* 1255:1120 */     Token _token = null;int _begin = this.text.length();
/* 1256:1121 */     int _ttype = 133;
/* 1257:1124 */     switch (LA(1))
/* 1258:     */     {
/* 1259:     */     case 'f': 
/* 1260:1127 */       match('f');
/* 1261:1128 */       break;
/* 1262:     */     case 'd': 
/* 1263:1132 */       match('d');
/* 1264:1133 */       break;
/* 1265:     */     case 'b': 
/* 1266:1137 */       match('b');
/* 1267:1138 */       match('d');
/* 1268:1139 */       break;
/* 1269:     */     case 'c': 
/* 1270:     */     case 'e': 
/* 1271:     */     default: 
/* 1272:1143 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1273:     */     }
/* 1274:1146 */     if ((_createToken) && (_token == null) && (_ttype != -1))
/* 1275:     */     {
/* 1276:1147 */       _token = makeToken(_ttype);
/* 1277:1148 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/* 1278:     */     }
/* 1279:1150 */     this._returnToken = _token;
/* 1280:     */   }
/* 1281:     */   
/* 1282:     */   protected final void mHEX_DIGIT(boolean _createToken)
/* 1283:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 1284:     */   {
/* 1285:1154 */     Token _token = null;int _begin = this.text.length();
/* 1286:1155 */     int _ttype = 131;
/* 1287:1159 */     switch (LA(1))
/* 1288:     */     {
/* 1289:     */     case '0': 
/* 1290:     */     case '1': 
/* 1291:     */     case '2': 
/* 1292:     */     case '3': 
/* 1293:     */     case '4': 
/* 1294:     */     case '5': 
/* 1295:     */     case '6': 
/* 1296:     */     case '7': 
/* 1297:     */     case '8': 
/* 1298:     */     case '9': 
/* 1299:1164 */       matchRange('0', '9');
/* 1300:1165 */       break;
/* 1301:     */     case 'a': 
/* 1302:     */     case 'b': 
/* 1303:     */     case 'c': 
/* 1304:     */     case 'd': 
/* 1305:     */     case 'e': 
/* 1306:     */     case 'f': 
/* 1307:1170 */       matchRange('a', 'f');
/* 1308:1171 */       break;
/* 1309:     */     case ':': 
/* 1310:     */     case ';': 
/* 1311:     */     case '<': 
/* 1312:     */     case '=': 
/* 1313:     */     case '>': 
/* 1314:     */     case '?': 
/* 1315:     */     case '@': 
/* 1316:     */     case 'A': 
/* 1317:     */     case 'B': 
/* 1318:     */     case 'C': 
/* 1319:     */     case 'D': 
/* 1320:     */     case 'E': 
/* 1321:     */     case 'F': 
/* 1322:     */     case 'G': 
/* 1323:     */     case 'H': 
/* 1324:     */     case 'I': 
/* 1325:     */     case 'J': 
/* 1326:     */     case 'K': 
/* 1327:     */     case 'L': 
/* 1328:     */     case 'M': 
/* 1329:     */     case 'N': 
/* 1330:     */     case 'O': 
/* 1331:     */     case 'P': 
/* 1332:     */     case 'Q': 
/* 1333:     */     case 'R': 
/* 1334:     */     case 'S': 
/* 1335:     */     case 'T': 
/* 1336:     */     case 'U': 
/* 1337:     */     case 'V': 
/* 1338:     */     case 'W': 
/* 1339:     */     case 'X': 
/* 1340:     */     case 'Y': 
/* 1341:     */     case 'Z': 
/* 1342:     */     case '[': 
/* 1343:     */     case '\\': 
/* 1344:     */     case ']': 
/* 1345:     */     case '^': 
/* 1346:     */     case '_': 
/* 1347:     */     case '`': 
/* 1348:     */     default: 
/* 1349:1175 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1350:     */     }
/* 1351:1179 */     if ((_createToken) && (_token == null) && (_ttype != -1))
/* 1352:     */     {
/* 1353:1180 */       _token = makeToken(_ttype);
/* 1354:1181 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/* 1355:     */     }
/* 1356:1183 */     this._returnToken = _token;
/* 1357:     */   }
/* 1358:     */   
/* 1359:     */   private static final long[] mk_tokenSet_0()
/* 1360:     */   {
/* 1361:1188 */     long[] data = new long[3072];
/* 1362:1189 */     data[0] = 68719476736L;
/* 1363:1190 */     data[1] = 576460745860972544L;
/* 1364:1191 */     for (int i = 2; i <= 1022; i++) {
/* 1365:1191 */       data[i] = -1L;
/* 1366:     */     }
/* 1367:1192 */     data[1023] = 9223372036854775807L;
/* 1368:1193 */     return data;
/* 1369:     */   }
/* 1370:     */   
/* 1371:1195 */   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
/* 1372:     */   
/* 1373:     */   private static final long[] mk_tokenSet_1()
/* 1374:     */   {
/* 1375:1197 */     long[] data = new long[3072];
/* 1376:1198 */     data[0] = 287948969894477824L;
/* 1377:1199 */     data[1] = 576460745860972544L;
/* 1378:1200 */     for (int i = 2; i <= 1022; i++) {
/* 1379:1200 */       data[i] = -1L;
/* 1380:     */     }
/* 1381:1201 */     data[1023] = 9223372036854775807L;
/* 1382:1202 */     return data;
/* 1383:     */   }
/* 1384:     */   
/* 1385:1204 */   public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
/* 1386:     */   
/* 1387:     */   private static final long[] mk_tokenSet_2()
/* 1388:     */   {
/* 1389:1206 */     long[] data = new long[2048];
/* 1390:1207 */     data[0] = -549755813889L;
/* 1391:1208 */     for (int i = 1; i <= 1022; i++) {
/* 1392:1208 */       data[i] = -1L;
/* 1393:     */     }
/* 1394:1209 */     data[1023] = 9223372036854775807L;
/* 1395:1210 */     return data;
/* 1396:     */   }
/* 1397:     */   
/* 1398:1212 */   public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
/* 1399:     */   
/* 1400:     */   private static final long[] mk_tokenSet_3()
/* 1401:     */   {
/* 1402:1214 */     long[] data = new long[1025];
/* 1403:1215 */     data[0] = 287948901175001088L;
/* 1404:1216 */     data[1] = 541165879296L;
/* 1405:1217 */     return data;
/* 1406:     */   }
/* 1407:     */   
/* 1408:1219 */   public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
/* 1409:     */   
/* 1410:     */   private static final long[] mk_tokenSet_4()
/* 1411:     */   {
/* 1412:1221 */     long[] data = new long[1025];
/* 1413:1222 */     data[0] = 70368744177664L;
/* 1414:1223 */     data[1] = 498216206336L;
/* 1415:1224 */     return data;
/* 1416:     */   }
/* 1417:     */   
/* 1418:1226 */   public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
/* 1419:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.antlr.HqlBaseLexer
 * JD-Core Version:    0.7.0.1
 */