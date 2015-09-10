/*    1:     */ package antlr.actions.cpp;
/*    2:     */ 
/*    3:     */ import antlr.ANTLRStringBuffer;
/*    4:     */ import antlr.ActionTransInfo;
/*    5:     */ import antlr.ByteBuffer;
/*    6:     */ import antlr.CharBuffer;
/*    7:     */ import antlr.CharScanner;
/*    8:     */ import antlr.CharStreamException;
/*    9:     */ import antlr.CharStreamIOException;
/*   10:     */ import antlr.CodeGenerator;
/*   11:     */ import antlr.InputBuffer;
/*   12:     */ import antlr.LexerSharedInputState;
/*   13:     */ import antlr.NoViableAltForCharException;
/*   14:     */ import antlr.RecognitionException;
/*   15:     */ import antlr.RuleBlock;
/*   16:     */ import antlr.Token;
/*   17:     */ import antlr.TokenStream;
/*   18:     */ import antlr.TokenStreamException;
/*   19:     */ import antlr.TokenStreamIOException;
/*   20:     */ import antlr.TokenStreamRecognitionException;
/*   21:     */ import antlr.Tool;
/*   22:     */ import antlr.collections.impl.BitSet;
/*   23:     */ import antlr.collections.impl.Vector;
/*   24:     */ import java.io.InputStream;
/*   25:     */ import java.io.Reader;
/*   26:     */ import java.io.StringReader;
/*   27:     */ import java.util.Hashtable;
/*   28:     */ 
/*   29:     */ public class ActionLexer
/*   30:     */   extends CharScanner
/*   31:     */   implements ActionLexerTokenTypes, TokenStream
/*   32:     */ {
/*   33:     */   protected RuleBlock currentRule;
/*   34:     */   protected CodeGenerator generator;
/*   35:  59 */   protected int lineOffset = 0;
/*   36:     */   private Tool antlrTool;
/*   37:     */   ActionTransInfo transInfo;
/*   38:     */   
/*   39:     */   public ActionLexer(String paramString, RuleBlock paramRuleBlock, CodeGenerator paramCodeGenerator, ActionTransInfo paramActionTransInfo)
/*   40:     */   {
/*   41:  67 */     this(new StringReader(paramString));
/*   42:  68 */     this.currentRule = paramRuleBlock;
/*   43:  69 */     this.generator = paramCodeGenerator;
/*   44:  70 */     this.transInfo = paramActionTransInfo;
/*   45:     */   }
/*   46:     */   
/*   47:     */   public void setLineOffset(int paramInt)
/*   48:     */   {
/*   49:  75 */     setLine(paramInt);
/*   50:     */   }
/*   51:     */   
/*   52:     */   public void setTool(Tool paramTool)
/*   53:     */   {
/*   54:  80 */     this.antlrTool = paramTool;
/*   55:     */   }
/*   56:     */   
/*   57:     */   public void reportError(RecognitionException paramRecognitionException)
/*   58:     */   {
/*   59:  85 */     this.antlrTool.error("Syntax error in action: " + paramRecognitionException, getFilename(), getLine(), getColumn());
/*   60:     */   }
/*   61:     */   
/*   62:     */   public void reportError(String paramString)
/*   63:     */   {
/*   64:  90 */     this.antlrTool.error(paramString, getFilename(), getLine(), getColumn());
/*   65:     */   }
/*   66:     */   
/*   67:     */   public void reportWarning(String paramString)
/*   68:     */   {
/*   69:  95 */     if (getFilename() == null) {
/*   70:  96 */       this.antlrTool.warning(paramString);
/*   71:     */     } else {
/*   72:  98 */       this.antlrTool.warning(paramString, getFilename(), getLine(), getColumn());
/*   73:     */     }
/*   74:     */   }
/*   75:     */   
/*   76:     */   public ActionLexer(InputStream paramInputStream)
/*   77:     */   {
/*   78: 101 */     this(new ByteBuffer(paramInputStream));
/*   79:     */   }
/*   80:     */   
/*   81:     */   public ActionLexer(Reader paramReader)
/*   82:     */   {
/*   83: 104 */     this(new CharBuffer(paramReader));
/*   84:     */   }
/*   85:     */   
/*   86:     */   public ActionLexer(InputBuffer paramInputBuffer)
/*   87:     */   {
/*   88: 107 */     this(new LexerSharedInputState(paramInputBuffer));
/*   89:     */   }
/*   90:     */   
/*   91:     */   public ActionLexer(LexerSharedInputState paramLexerSharedInputState)
/*   92:     */   {
/*   93: 110 */     super(paramLexerSharedInputState);
/*   94: 111 */     this.caseSensitiveLiterals = true;
/*   95: 112 */     setCaseSensitive(true);
/*   96: 113 */     this.literals = new Hashtable();
/*   97:     */   }
/*   98:     */   
/*   99:     */   public Token nextToken()
/*  100:     */     throws TokenStreamException
/*  101:     */   {
/*  102: 117 */     Token localToken = null;
/*  103:     */     for (;;)
/*  104:     */     {
/*  105: 120 */       Object localObject = null;
/*  106: 121 */       int i = 0;
/*  107: 122 */       resetText();
/*  108:     */       try
/*  109:     */       {
/*  110: 125 */         if ((LA(1) >= '\003') && (LA(1) <= 'ÿ'))
/*  111:     */         {
/*  112: 126 */           mACTION(true);
/*  113: 127 */           localToken = this._returnToken;
/*  114:     */         }
/*  115: 130 */         else if (LA(1) == 65535)
/*  116:     */         {
/*  117: 130 */           uponEOF();this._returnToken = makeToken(1);
/*  118:     */         }
/*  119:     */         else
/*  120:     */         {
/*  121: 131 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  122:     */         }
/*  123: 134 */         if (this._returnToken == null) {
/*  124:     */           continue;
/*  125:     */         }
/*  126: 135 */         i = this._returnToken.getType();
/*  127: 136 */         this._returnToken.setType(i);
/*  128: 137 */         return this._returnToken;
/*  129:     */       }
/*  130:     */       catch (RecognitionException localRecognitionException)
/*  131:     */       {
/*  132: 140 */         throw new TokenStreamRecognitionException(localRecognitionException);
/*  133:     */       }
/*  134:     */       catch (CharStreamException localCharStreamException)
/*  135:     */       {
/*  136: 144 */         if ((localCharStreamException instanceof CharStreamIOException)) {
/*  137: 145 */           throw new TokenStreamIOException(((CharStreamIOException)localCharStreamException).io);
/*  138:     */         }
/*  139: 148 */         throw new TokenStreamException(localCharStreamException.getMessage());
/*  140:     */       }
/*  141:     */     }
/*  142:     */   }
/*  143:     */   
/*  144:     */   public final void mACTION(boolean paramBoolean)
/*  145:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  146:     */   {
/*  147: 155 */     Token localToken = null;int j = this.text.length();
/*  148: 156 */     int i = 4;
/*  149:     */     
/*  150:     */ 
/*  151:     */ 
/*  152: 160 */     int k = 0;
/*  153:     */     for (;;)
/*  154:     */     {
/*  155: 163 */       switch (LA(1))
/*  156:     */       {
/*  157:     */       case '#': 
/*  158: 166 */         mAST_ITEM(false);
/*  159: 167 */         break;
/*  160:     */       case '$': 
/*  161: 171 */         mTEXT_ITEM(false);
/*  162: 172 */         break;
/*  163:     */       default: 
/*  164: 175 */         if (_tokenSet_0.member(LA(1)))
/*  165:     */         {
/*  166: 176 */           mSTUFF(false);
/*  167:     */         }
/*  168:     */         else
/*  169:     */         {
/*  170: 179 */           if (k >= 1) {
/*  171:     */             break label126;
/*  172:     */           }
/*  173: 179 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  174:     */         }
/*  175:     */         break;
/*  176:     */       }
/*  177: 182 */       k++;
/*  178:     */     }
/*  179:     */     label126:
/*  180: 185 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/*  181:     */     {
/*  182: 186 */       localToken = makeToken(i);
/*  183: 187 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  184:     */     }
/*  185: 189 */     this._returnToken = localToken;
/*  186:     */   }
/*  187:     */   
/*  188:     */   protected final void mSTUFF(boolean paramBoolean)
/*  189:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  190:     */   {
/*  191: 196 */     Token localToken = null;int j = this.text.length();
/*  192: 197 */     int i = 5;
/*  193: 200 */     switch (LA(1))
/*  194:     */     {
/*  195:     */     case '"': 
/*  196: 203 */       mSTRING(false);
/*  197: 204 */       break;
/*  198:     */     case '\'': 
/*  199: 208 */       mCHAR(false);
/*  200: 209 */       break;
/*  201:     */     case '\n': 
/*  202: 213 */       match('\n');
/*  203: 214 */       newline();
/*  204: 215 */       break;
/*  205:     */     default: 
/*  206: 218 */       if ((LA(1) == '/') && ((LA(2) == '*') || (LA(2) == '/')))
/*  207:     */       {
/*  208: 219 */         mCOMMENT(false);
/*  209:     */       }
/*  210: 221 */       else if ((LA(1) == '\r') && (LA(2) == '\n'))
/*  211:     */       {
/*  212: 222 */         match("\r\n");
/*  213: 223 */         newline();
/*  214:     */       }
/*  215: 225 */       else if ((LA(1) == '\\') && (LA(2) == '#'))
/*  216:     */       {
/*  217: 226 */         match('\\');
/*  218: 227 */         match('#');
/*  219: 228 */         this.text.setLength(j);this.text.append("#");
/*  220:     */       }
/*  221: 230 */       else if ((LA(1) == '/') && (_tokenSet_1.member(LA(2))))
/*  222:     */       {
/*  223: 231 */         match('/');
/*  224:     */         
/*  225: 233 */         match(_tokenSet_1);
/*  226:     */       }
/*  227: 236 */       else if (LA(1) == '\r')
/*  228:     */       {
/*  229: 237 */         match('\r');
/*  230: 238 */         newline();
/*  231:     */       }
/*  232: 240 */       else if (_tokenSet_2.member(LA(1)))
/*  233:     */       {
/*  234: 242 */         match(_tokenSet_2);
/*  235:     */       }
/*  236:     */       else
/*  237:     */       {
/*  238: 246 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  239:     */       }
/*  240:     */       break;
/*  241:     */     }
/*  242: 249 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/*  243:     */     {
/*  244: 250 */       localToken = makeToken(i);
/*  245: 251 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  246:     */     }
/*  247: 253 */     this._returnToken = localToken;
/*  248:     */   }
/*  249:     */   
/*  250:     */   protected final void mAST_ITEM(boolean paramBoolean)
/*  251:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  252:     */   {
/*  253: 257 */     Token localToken1 = null;int j = this.text.length();
/*  254: 258 */     int i = 6;
/*  255:     */     
/*  256: 260 */     Token localToken2 = null;
/*  257: 261 */     Token localToken3 = null;
/*  258: 262 */     Token localToken4 = null;
/*  259:     */     int k;
/*  260: 264 */     if ((LA(1) == '#') && (LA(2) == '('))
/*  261:     */     {
/*  262: 265 */       k = this.text.length();
/*  263: 266 */       match('#');
/*  264: 267 */       this.text.setLength(k);
/*  265: 268 */       mTREE(true);
/*  266: 269 */       localToken2 = this._returnToken;
/*  267:     */     }
/*  268:     */     else
/*  269:     */     {
/*  270:     */       String str1;
/*  271: 271 */       if ((LA(1) == '#') && (_tokenSet_3.member(LA(2))))
/*  272:     */       {
/*  273: 272 */         k = this.text.length();
/*  274: 273 */         match('#');
/*  275: 274 */         this.text.setLength(k);
/*  276: 276 */         switch (LA(1))
/*  277:     */         {
/*  278:     */         case '\t': 
/*  279:     */         case '\n': 
/*  280:     */         case '\r': 
/*  281:     */         case ' ': 
/*  282: 279 */           mWS(false);
/*  283: 280 */           break;
/*  284:     */         case ':': 
/*  285:     */         case 'A': 
/*  286:     */         case 'B': 
/*  287:     */         case 'C': 
/*  288:     */         case 'D': 
/*  289:     */         case 'E': 
/*  290:     */         case 'F': 
/*  291:     */         case 'G': 
/*  292:     */         case 'H': 
/*  293:     */         case 'I': 
/*  294:     */         case 'J': 
/*  295:     */         case 'K': 
/*  296:     */         case 'L': 
/*  297:     */         case 'M': 
/*  298:     */         case 'N': 
/*  299:     */         case 'O': 
/*  300:     */         case 'P': 
/*  301:     */         case 'Q': 
/*  302:     */         case 'R': 
/*  303:     */         case 'S': 
/*  304:     */         case 'T': 
/*  305:     */         case 'U': 
/*  306:     */         case 'V': 
/*  307:     */         case 'W': 
/*  308:     */         case 'X': 
/*  309:     */         case 'Y': 
/*  310:     */         case 'Z': 
/*  311:     */         case '_': 
/*  312:     */         case 'a': 
/*  313:     */         case 'b': 
/*  314:     */         case 'c': 
/*  315:     */         case 'd': 
/*  316:     */         case 'e': 
/*  317:     */         case 'f': 
/*  318:     */         case 'g': 
/*  319:     */         case 'h': 
/*  320:     */         case 'i': 
/*  321:     */         case 'j': 
/*  322:     */         case 'k': 
/*  323:     */         case 'l': 
/*  324:     */         case 'm': 
/*  325:     */         case 'n': 
/*  326:     */         case 'o': 
/*  327:     */         case 'p': 
/*  328:     */         case 'q': 
/*  329:     */         case 'r': 
/*  330:     */         case 's': 
/*  331:     */         case 't': 
/*  332:     */         case 'u': 
/*  333:     */         case 'v': 
/*  334:     */         case 'w': 
/*  335:     */         case 'x': 
/*  336:     */         case 'y': 
/*  337:     */         case 'z': 
/*  338:     */           break;
/*  339:     */         case '\013': 
/*  340:     */         case '\f': 
/*  341:     */         case '\016': 
/*  342:     */         case '\017': 
/*  343:     */         case '\020': 
/*  344:     */         case '\021': 
/*  345:     */         case '\022': 
/*  346:     */         case '\023': 
/*  347:     */         case '\024': 
/*  348:     */         case '\025': 
/*  349:     */         case '\026': 
/*  350:     */         case '\027': 
/*  351:     */         case '\030': 
/*  352:     */         case '\031': 
/*  353:     */         case '\032': 
/*  354:     */         case '\033': 
/*  355:     */         case '\034': 
/*  356:     */         case '\035': 
/*  357:     */         case '\036': 
/*  358:     */         case '\037': 
/*  359:     */         case '!': 
/*  360:     */         case '"': 
/*  361:     */         case '#': 
/*  362:     */         case '$': 
/*  363:     */         case '%': 
/*  364:     */         case '&': 
/*  365:     */         case '\'': 
/*  366:     */         case '(': 
/*  367:     */         case ')': 
/*  368:     */         case '*': 
/*  369:     */         case '+': 
/*  370:     */         case ',': 
/*  371:     */         case '-': 
/*  372:     */         case '.': 
/*  373:     */         case '/': 
/*  374:     */         case '0': 
/*  375:     */         case '1': 
/*  376:     */         case '2': 
/*  377:     */         case '3': 
/*  378:     */         case '4': 
/*  379:     */         case '5': 
/*  380:     */         case '6': 
/*  381:     */         case '7': 
/*  382:     */         case '8': 
/*  383:     */         case '9': 
/*  384:     */         case ';': 
/*  385:     */         case '<': 
/*  386:     */         case '=': 
/*  387:     */         case '>': 
/*  388:     */         case '?': 
/*  389:     */         case '@': 
/*  390:     */         case '[': 
/*  391:     */         case '\\': 
/*  392:     */         case ']': 
/*  393:     */         case '^': 
/*  394:     */         case '`': 
/*  395:     */         default: 
/*  396: 301 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  397:     */         }
/*  398: 305 */         mID(true);
/*  399: 306 */         localToken3 = this._returnToken;
/*  400:     */         
/*  401: 308 */         str1 = localToken3.getText();
/*  402: 309 */         String str2 = this.generator.mapTreeId(localToken3.getText(), this.transInfo);
/*  403: 312 */         if ((str2 != null) && (!str1.equals(str2)))
/*  404:     */         {
/*  405: 314 */           this.text.setLength(j);this.text.append(str2);
/*  406:     */         }
/*  407: 318 */         else if ((str1.equals("if")) || (str1.equals("define")) || (str1.equals("ifdef")) || (str1.equals("ifndef")) || (str1.equals("else")) || (str1.equals("elif")) || (str1.equals("endif")) || (str1.equals("warning")) || (str1.equals("error")) || (str1.equals("ident")) || (str1.equals("pragma")) || (str1.equals("include")))
/*  408:     */         {
/*  409: 331 */           this.text.setLength(j);this.text.append("#" + str1);
/*  410:     */         }
/*  411: 336 */         if (_tokenSet_4.member(LA(1))) {
/*  412: 337 */           mWS(false);
/*  413:     */         }
/*  414: 344 */         if (LA(1) == '=') {
/*  415: 345 */           mVAR_ASSIGN(false);
/*  416:     */         }
/*  417:     */       }
/*  418: 352 */       else if ((LA(1) == '#') && (LA(2) == '['))
/*  419:     */       {
/*  420: 353 */         k = this.text.length();
/*  421: 354 */         match('#');
/*  422: 355 */         this.text.setLength(k);
/*  423: 356 */         mAST_CONSTRUCTOR(true);
/*  424: 357 */         localToken4 = this._returnToken;
/*  425:     */       }
/*  426: 359 */       else if ((LA(1) == '#') && (LA(2) == '#'))
/*  427:     */       {
/*  428: 360 */         match("##");
/*  429: 362 */         if (this.currentRule != null)
/*  430:     */         {
/*  431: 364 */           str1 = this.currentRule.getRuleName() + "_AST";
/*  432: 365 */           this.text.setLength(j);this.text.append(str1);
/*  433: 367 */           if (this.transInfo != null) {
/*  434: 368 */             this.transInfo.refRuleRoot = str1;
/*  435:     */           }
/*  436:     */         }
/*  437:     */         else
/*  438:     */         {
/*  439: 373 */           reportWarning("\"##\" not valid in this context");
/*  440: 374 */           this.text.setLength(j);this.text.append("##");
/*  441:     */         }
/*  442: 378 */         if (_tokenSet_4.member(LA(1))) {
/*  443: 379 */           mWS(false);
/*  444:     */         }
/*  445: 386 */         if (LA(1) == '=') {
/*  446: 387 */           mVAR_ASSIGN(false);
/*  447:     */         }
/*  448:     */       }
/*  449:     */       else
/*  450:     */       {
/*  451: 395 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  452:     */       }
/*  453:     */     }
/*  454: 398 */     if ((paramBoolean) && (localToken1 == null) && (i != -1))
/*  455:     */     {
/*  456: 399 */       localToken1 = makeToken(i);
/*  457: 400 */       localToken1.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  458:     */     }
/*  459: 402 */     this._returnToken = localToken1;
/*  460:     */   }
/*  461:     */   
/*  462:     */   protected final void mTEXT_ITEM(boolean paramBoolean)
/*  463:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  464:     */   {
/*  465: 406 */     Token localToken1 = null;int j = this.text.length();
/*  466: 407 */     int i = 7;
/*  467:     */     
/*  468: 409 */     Token localToken2 = null;
/*  469: 410 */     Token localToken3 = null;
/*  470: 411 */     Token localToken4 = null;
/*  471: 412 */     Token localToken5 = null;
/*  472: 413 */     Token localToken6 = null;
/*  473: 414 */     Token localToken7 = null;
/*  474:     */     String str1;
/*  475:     */     String str2;
/*  476: 416 */     if ((LA(1) == '$') && (LA(2) == 'F') && (LA(3) == 'O'))
/*  477:     */     {
/*  478: 417 */       match("$FOLLOW");
/*  479: 419 */       if ((_tokenSet_5.member(LA(1))) && (_tokenSet_6.member(LA(2))) && (LA(3) >= '\003') && (LA(3) <= 'ÿ'))
/*  480:     */       {
/*  481: 421 */         switch (LA(1))
/*  482:     */         {
/*  483:     */         case '\t': 
/*  484:     */         case '\n': 
/*  485:     */         case '\r': 
/*  486:     */         case ' ': 
/*  487: 424 */           mWS(false);
/*  488: 425 */           break;
/*  489:     */         case '(': 
/*  490:     */           break;
/*  491:     */         default: 
/*  492: 433 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  493:     */         }
/*  494: 437 */         match('(');
/*  495: 438 */         mTEXT_ARG(true);
/*  496: 439 */         localToken6 = this._returnToken;
/*  497: 440 */         match(')');
/*  498:     */       }
/*  499: 447 */       str1 = this.currentRule.getRuleName();
/*  500: 448 */       if (localToken6 != null) {
/*  501: 449 */         str1 = localToken6.getText();
/*  502:     */       }
/*  503: 451 */       str2 = this.generator.getFOLLOWBitSet(str1, 1);
/*  504: 453 */       if (str2 == null)
/*  505:     */       {
/*  506: 454 */         reportError("$FOLLOW(" + str1 + ")" + ": unknown rule or bad lookahead computation");
/*  507:     */       }
/*  508:     */       else
/*  509:     */       {
/*  510: 458 */         this.text.setLength(j);this.text.append(str2);
/*  511:     */       }
/*  512:     */     }
/*  513: 462 */     else if ((LA(1) == '$') && (LA(2) == 'F') && (LA(3) == 'I'))
/*  514:     */     {
/*  515: 463 */       match("$FIRST");
/*  516: 465 */       if ((_tokenSet_5.member(LA(1))) && (_tokenSet_6.member(LA(2))) && (LA(3) >= '\003') && (LA(3) <= 'ÿ'))
/*  517:     */       {
/*  518: 467 */         switch (LA(1))
/*  519:     */         {
/*  520:     */         case '\t': 
/*  521:     */         case '\n': 
/*  522:     */         case '\r': 
/*  523:     */         case ' ': 
/*  524: 470 */           mWS(false);
/*  525: 471 */           break;
/*  526:     */         case '(': 
/*  527:     */           break;
/*  528:     */         default: 
/*  529: 479 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  530:     */         }
/*  531: 483 */         match('(');
/*  532: 484 */         mTEXT_ARG(true);
/*  533: 485 */         localToken7 = this._returnToken;
/*  534: 486 */         match(')');
/*  535:     */       }
/*  536: 493 */       str1 = this.currentRule.getRuleName();
/*  537: 494 */       if (localToken7 != null) {
/*  538: 495 */         str1 = localToken7.getText();
/*  539:     */       }
/*  540: 497 */       str2 = this.generator.getFIRSTBitSet(str1, 1);
/*  541: 499 */       if (str2 == null)
/*  542:     */       {
/*  543: 500 */         reportError("$FIRST(" + str1 + ")" + ": unknown rule or bad lookahead computation");
/*  544:     */       }
/*  545:     */       else
/*  546:     */       {
/*  547: 504 */         this.text.setLength(j);this.text.append(str2);
/*  548:     */       }
/*  549:     */     }
/*  550: 508 */     else if ((LA(1) == '$') && (LA(2) == 'a'))
/*  551:     */     {
/*  552: 509 */       match("$append");
/*  553: 511 */       switch (LA(1))
/*  554:     */       {
/*  555:     */       case '\t': 
/*  556:     */       case '\n': 
/*  557:     */       case '\r': 
/*  558:     */       case ' ': 
/*  559: 514 */         mWS(false);
/*  560: 515 */         break;
/*  561:     */       case '(': 
/*  562:     */         break;
/*  563:     */       default: 
/*  564: 523 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  565:     */       }
/*  566: 527 */       match('(');
/*  567: 528 */       mTEXT_ARG(true);
/*  568: 529 */       localToken2 = this._returnToken;
/*  569: 530 */       match(')');
/*  570:     */       
/*  571: 532 */       str1 = "text += " + localToken2.getText();
/*  572: 533 */       this.text.setLength(j);this.text.append(str1);
/*  573:     */     }
/*  574: 536 */     else if ((LA(1) == '$') && (LA(2) == 's'))
/*  575:     */     {
/*  576: 537 */       match("$set");
/*  577: 539 */       if ((LA(1) == 'T') && (LA(2) == 'e'))
/*  578:     */       {
/*  579: 540 */         match("Text");
/*  580: 542 */         switch (LA(1))
/*  581:     */         {
/*  582:     */         case '\t': 
/*  583:     */         case '\n': 
/*  584:     */         case '\r': 
/*  585:     */         case ' ': 
/*  586: 545 */           mWS(false);
/*  587: 546 */           break;
/*  588:     */         case '(': 
/*  589:     */           break;
/*  590:     */         default: 
/*  591: 554 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  592:     */         }
/*  593: 558 */         match('(');
/*  594: 559 */         mTEXT_ARG(true);
/*  595: 560 */         localToken3 = this._returnToken;
/*  596: 561 */         match(')');
/*  597:     */         
/*  598:     */ 
/*  599: 564 */         str1 = "{ text.erase(_begin); text += " + localToken3.getText() + "; }";
/*  600: 565 */         this.text.setLength(j);this.text.append(str1);
/*  601:     */       }
/*  602: 568 */       else if ((LA(1) == 'T') && (LA(2) == 'o'))
/*  603:     */       {
/*  604: 569 */         match("Token");
/*  605: 571 */         switch (LA(1))
/*  606:     */         {
/*  607:     */         case '\t': 
/*  608:     */         case '\n': 
/*  609:     */         case '\r': 
/*  610:     */         case ' ': 
/*  611: 574 */           mWS(false);
/*  612: 575 */           break;
/*  613:     */         case '(': 
/*  614:     */           break;
/*  615:     */         default: 
/*  616: 583 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  617:     */         }
/*  618: 587 */         match('(');
/*  619: 588 */         mTEXT_ARG(true);
/*  620: 589 */         localToken4 = this._returnToken;
/*  621: 590 */         match(')');
/*  622:     */         
/*  623: 592 */         str1 = "_token = " + localToken4.getText();
/*  624: 593 */         this.text.setLength(j);this.text.append(str1);
/*  625:     */       }
/*  626: 596 */       else if ((LA(1) == 'T') && (LA(2) == 'y'))
/*  627:     */       {
/*  628: 597 */         match("Type");
/*  629: 599 */         switch (LA(1))
/*  630:     */         {
/*  631:     */         case '\t': 
/*  632:     */         case '\n': 
/*  633:     */         case '\r': 
/*  634:     */         case ' ': 
/*  635: 602 */           mWS(false);
/*  636: 603 */           break;
/*  637:     */         case '(': 
/*  638:     */           break;
/*  639:     */         default: 
/*  640: 611 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  641:     */         }
/*  642: 615 */         match('(');
/*  643: 616 */         mTEXT_ARG(true);
/*  644: 617 */         localToken5 = this._returnToken;
/*  645: 618 */         match(')');
/*  646:     */         
/*  647: 620 */         str1 = "_ttype = " + localToken5.getText();
/*  648: 621 */         this.text.setLength(j);this.text.append(str1);
/*  649:     */       }
/*  650:     */       else
/*  651:     */       {
/*  652: 625 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  653:     */       }
/*  654:     */     }
/*  655: 630 */     else if ((LA(1) == '$') && (LA(2) == 'g'))
/*  656:     */     {
/*  657: 631 */       match("$getText");
/*  658:     */       
/*  659: 633 */       this.text.setLength(j);this.text.append("text.substr(_begin,text.length()-_begin)");
/*  660:     */     }
/*  661:     */     else
/*  662:     */     {
/*  663: 637 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  664:     */     }
/*  665: 640 */     if ((paramBoolean) && (localToken1 == null) && (i != -1))
/*  666:     */     {
/*  667: 641 */       localToken1 = makeToken(i);
/*  668: 642 */       localToken1.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  669:     */     }
/*  670: 644 */     this._returnToken = localToken1;
/*  671:     */   }
/*  672:     */   
/*  673:     */   protected final void mCOMMENT(boolean paramBoolean)
/*  674:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  675:     */   {
/*  676: 648 */     Token localToken = null;int j = this.text.length();
/*  677: 649 */     int i = 19;
/*  678: 652 */     if ((LA(1) == '/') && (LA(2) == '/')) {
/*  679: 653 */       mSL_COMMENT(false);
/*  680: 655 */     } else if ((LA(1) == '/') && (LA(2) == '*')) {
/*  681: 656 */       mML_COMMENT(false);
/*  682:     */     } else {
/*  683: 659 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  684:     */     }
/*  685: 662 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/*  686:     */     {
/*  687: 663 */       localToken = makeToken(i);
/*  688: 664 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  689:     */     }
/*  690: 666 */     this._returnToken = localToken;
/*  691:     */   }
/*  692:     */   
/*  693:     */   protected final void mSTRING(boolean paramBoolean)
/*  694:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  695:     */   {
/*  696: 670 */     Token localToken = null;int j = this.text.length();
/*  697: 671 */     int i = 23;
/*  698:     */     
/*  699:     */ 
/*  700: 674 */     match('"');
/*  701:     */     for (;;)
/*  702:     */     {
/*  703: 678 */       if (LA(1) == '\\')
/*  704:     */       {
/*  705: 679 */         mESC(false);
/*  706:     */       }
/*  707:     */       else
/*  708:     */       {
/*  709: 681 */         if (!_tokenSet_7.member(LA(1))) {
/*  710:     */           break;
/*  711:     */         }
/*  712: 682 */         matchNot('"');
/*  713:     */       }
/*  714:     */     }
/*  715: 690 */     match('"');
/*  716: 691 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/*  717:     */     {
/*  718: 692 */       localToken = makeToken(i);
/*  719: 693 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  720:     */     }
/*  721: 695 */     this._returnToken = localToken;
/*  722:     */   }
/*  723:     */   
/*  724:     */   protected final void mCHAR(boolean paramBoolean)
/*  725:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  726:     */   {
/*  727: 699 */     Token localToken = null;int j = this.text.length();
/*  728: 700 */     int i = 22;
/*  729:     */     
/*  730:     */ 
/*  731: 703 */     match('\'');
/*  732: 705 */     if (LA(1) == '\\') {
/*  733: 706 */       mESC(false);
/*  734: 708 */     } else if (_tokenSet_8.member(LA(1))) {
/*  735: 709 */       matchNot('\'');
/*  736:     */     } else {
/*  737: 712 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  738:     */     }
/*  739: 716 */     match('\'');
/*  740: 717 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/*  741:     */     {
/*  742: 718 */       localToken = makeToken(i);
/*  743: 719 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  744:     */     }
/*  745: 721 */     this._returnToken = localToken;
/*  746:     */   }
/*  747:     */   
/*  748:     */   protected final void mTREE(boolean paramBoolean)
/*  749:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  750:     */   {
/*  751: 725 */     Token localToken1 = null;int j = this.text.length();
/*  752: 726 */     int i = 8;
/*  753:     */     
/*  754: 728 */     Token localToken2 = null;
/*  755: 729 */     Token localToken3 = null;
/*  756:     */     
/*  757: 731 */     StringBuffer localStringBuffer = new StringBuffer();
/*  758: 732 */     int m = 0;
/*  759: 733 */     Vector localVector = new Vector(10);
/*  760:     */     
/*  761:     */ 
/*  762: 736 */     int k = this.text.length();
/*  763: 737 */     match('(');
/*  764: 738 */     this.text.setLength(k);
/*  765: 740 */     switch (LA(1))
/*  766:     */     {
/*  767:     */     case '\t': 
/*  768:     */     case '\n': 
/*  769:     */     case '\r': 
/*  770:     */     case ' ': 
/*  771: 743 */       k = this.text.length();
/*  772: 744 */       mWS(false);
/*  773: 745 */       this.text.setLength(k);
/*  774: 746 */       break;
/*  775:     */     case '"': 
/*  776:     */     case '#': 
/*  777:     */     case '(': 
/*  778:     */     case ':': 
/*  779:     */     case 'A': 
/*  780:     */     case 'B': 
/*  781:     */     case 'C': 
/*  782:     */     case 'D': 
/*  783:     */     case 'E': 
/*  784:     */     case 'F': 
/*  785:     */     case 'G': 
/*  786:     */     case 'H': 
/*  787:     */     case 'I': 
/*  788:     */     case 'J': 
/*  789:     */     case 'K': 
/*  790:     */     case 'L': 
/*  791:     */     case 'M': 
/*  792:     */     case 'N': 
/*  793:     */     case 'O': 
/*  794:     */     case 'P': 
/*  795:     */     case 'Q': 
/*  796:     */     case 'R': 
/*  797:     */     case 'S': 
/*  798:     */     case 'T': 
/*  799:     */     case 'U': 
/*  800:     */     case 'V': 
/*  801:     */     case 'W': 
/*  802:     */     case 'X': 
/*  803:     */     case 'Y': 
/*  804:     */     case 'Z': 
/*  805:     */     case '[': 
/*  806:     */     case '_': 
/*  807:     */     case 'a': 
/*  808:     */     case 'b': 
/*  809:     */     case 'c': 
/*  810:     */     case 'd': 
/*  811:     */     case 'e': 
/*  812:     */     case 'f': 
/*  813:     */     case 'g': 
/*  814:     */     case 'h': 
/*  815:     */     case 'i': 
/*  816:     */     case 'j': 
/*  817:     */     case 'k': 
/*  818:     */     case 'l': 
/*  819:     */     case 'm': 
/*  820:     */     case 'n': 
/*  821:     */     case 'o': 
/*  822:     */     case 'p': 
/*  823:     */     case 'q': 
/*  824:     */     case 'r': 
/*  825:     */     case 's': 
/*  826:     */     case 't': 
/*  827:     */     case 'u': 
/*  828:     */     case 'v': 
/*  829:     */     case 'w': 
/*  830:     */     case 'x': 
/*  831:     */     case 'y': 
/*  832:     */     case 'z': 
/*  833:     */       break;
/*  834:     */     case '\013': 
/*  835:     */     case '\f': 
/*  836:     */     case '\016': 
/*  837:     */     case '\017': 
/*  838:     */     case '\020': 
/*  839:     */     case '\021': 
/*  840:     */     case '\022': 
/*  841:     */     case '\023': 
/*  842:     */     case '\024': 
/*  843:     */     case '\025': 
/*  844:     */     case '\026': 
/*  845:     */     case '\027': 
/*  846:     */     case '\030': 
/*  847:     */     case '\031': 
/*  848:     */     case '\032': 
/*  849:     */     case '\033': 
/*  850:     */     case '\034': 
/*  851:     */     case '\035': 
/*  852:     */     case '\036': 
/*  853:     */     case '\037': 
/*  854:     */     case '!': 
/*  855:     */     case '$': 
/*  856:     */     case '%': 
/*  857:     */     case '&': 
/*  858:     */     case '\'': 
/*  859:     */     case ')': 
/*  860:     */     case '*': 
/*  861:     */     case '+': 
/*  862:     */     case ',': 
/*  863:     */     case '-': 
/*  864:     */     case '.': 
/*  865:     */     case '/': 
/*  866:     */     case '0': 
/*  867:     */     case '1': 
/*  868:     */     case '2': 
/*  869:     */     case '3': 
/*  870:     */     case '4': 
/*  871:     */     case '5': 
/*  872:     */     case '6': 
/*  873:     */     case '7': 
/*  874:     */     case '8': 
/*  875:     */     case '9': 
/*  876:     */     case ';': 
/*  877:     */     case '<': 
/*  878:     */     case '=': 
/*  879:     */     case '>': 
/*  880:     */     case '?': 
/*  881:     */     case '@': 
/*  882:     */     case '\\': 
/*  883:     */     case ']': 
/*  884:     */     case '^': 
/*  885:     */     case '`': 
/*  886:     */     default: 
/*  887: 768 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  888:     */     }
/*  889: 772 */     k = this.text.length();
/*  890: 773 */     mTREE_ELEMENT(true);
/*  891: 774 */     this.text.setLength(k);
/*  892: 775 */     localToken2 = this._returnToken;
/*  893:     */     
/*  894: 777 */     localVector.appendElement(this.generator.processStringForASTConstructor(localToken2.getText()));
/*  895: 782 */     switch (LA(1))
/*  896:     */     {
/*  897:     */     case '\t': 
/*  898:     */     case '\n': 
/*  899:     */     case '\r': 
/*  900:     */     case ' ': 
/*  901: 785 */       k = this.text.length();
/*  902: 786 */       mWS(false);
/*  903: 787 */       this.text.setLength(k);
/*  904: 788 */       break;
/*  905:     */     case ')': 
/*  906:     */     case ',': 
/*  907:     */       break;
/*  908:     */     default: 
/*  909: 796 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  910:     */     }
/*  911: 803 */     while (LA(1) == ',')
/*  912:     */     {
/*  913: 804 */       k = this.text.length();
/*  914: 805 */       match(',');
/*  915: 806 */       this.text.setLength(k);
/*  916: 808 */       switch (LA(1))
/*  917:     */       {
/*  918:     */       case '\t': 
/*  919:     */       case '\n': 
/*  920:     */       case '\r': 
/*  921:     */       case ' ': 
/*  922: 811 */         k = this.text.length();
/*  923: 812 */         mWS(false);
/*  924: 813 */         this.text.setLength(k);
/*  925: 814 */         break;
/*  926:     */       case '"': 
/*  927:     */       case '#': 
/*  928:     */       case '(': 
/*  929:     */       case ':': 
/*  930:     */       case 'A': 
/*  931:     */       case 'B': 
/*  932:     */       case 'C': 
/*  933:     */       case 'D': 
/*  934:     */       case 'E': 
/*  935:     */       case 'F': 
/*  936:     */       case 'G': 
/*  937:     */       case 'H': 
/*  938:     */       case 'I': 
/*  939:     */       case 'J': 
/*  940:     */       case 'K': 
/*  941:     */       case 'L': 
/*  942:     */       case 'M': 
/*  943:     */       case 'N': 
/*  944:     */       case 'O': 
/*  945:     */       case 'P': 
/*  946:     */       case 'Q': 
/*  947:     */       case 'R': 
/*  948:     */       case 'S': 
/*  949:     */       case 'T': 
/*  950:     */       case 'U': 
/*  951:     */       case 'V': 
/*  952:     */       case 'W': 
/*  953:     */       case 'X': 
/*  954:     */       case 'Y': 
/*  955:     */       case 'Z': 
/*  956:     */       case '[': 
/*  957:     */       case '_': 
/*  958:     */       case 'a': 
/*  959:     */       case 'b': 
/*  960:     */       case 'c': 
/*  961:     */       case 'd': 
/*  962:     */       case 'e': 
/*  963:     */       case 'f': 
/*  964:     */       case 'g': 
/*  965:     */       case 'h': 
/*  966:     */       case 'i': 
/*  967:     */       case 'j': 
/*  968:     */       case 'k': 
/*  969:     */       case 'l': 
/*  970:     */       case 'm': 
/*  971:     */       case 'n': 
/*  972:     */       case 'o': 
/*  973:     */       case 'p': 
/*  974:     */       case 'q': 
/*  975:     */       case 'r': 
/*  976:     */       case 's': 
/*  977:     */       case 't': 
/*  978:     */       case 'u': 
/*  979:     */       case 'v': 
/*  980:     */       case 'w': 
/*  981:     */       case 'x': 
/*  982:     */       case 'y': 
/*  983:     */       case 'z': 
/*  984:     */         break;
/*  985:     */       case '\013': 
/*  986:     */       case '\f': 
/*  987:     */       case '\016': 
/*  988:     */       case '\017': 
/*  989:     */       case '\020': 
/*  990:     */       case '\021': 
/*  991:     */       case '\022': 
/*  992:     */       case '\023': 
/*  993:     */       case '\024': 
/*  994:     */       case '\025': 
/*  995:     */       case '\026': 
/*  996:     */       case '\027': 
/*  997:     */       case '\030': 
/*  998:     */       case '\031': 
/*  999:     */       case '\032': 
/* 1000:     */       case '\033': 
/* 1001:     */       case '\034': 
/* 1002:     */       case '\035': 
/* 1003:     */       case '\036': 
/* 1004:     */       case '\037': 
/* 1005:     */       case '!': 
/* 1006:     */       case '$': 
/* 1007:     */       case '%': 
/* 1008:     */       case '&': 
/* 1009:     */       case '\'': 
/* 1010:     */       case ')': 
/* 1011:     */       case '*': 
/* 1012:     */       case '+': 
/* 1013:     */       case ',': 
/* 1014:     */       case '-': 
/* 1015:     */       case '.': 
/* 1016:     */       case '/': 
/* 1017:     */       case '0': 
/* 1018:     */       case '1': 
/* 1019:     */       case '2': 
/* 1020:     */       case '3': 
/* 1021:     */       case '4': 
/* 1022:     */       case '5': 
/* 1023:     */       case '6': 
/* 1024:     */       case '7': 
/* 1025:     */       case '8': 
/* 1026:     */       case '9': 
/* 1027:     */       case ';': 
/* 1028:     */       case '<': 
/* 1029:     */       case '=': 
/* 1030:     */       case '>': 
/* 1031:     */       case '?': 
/* 1032:     */       case '@': 
/* 1033:     */       case '\\': 
/* 1034:     */       case ']': 
/* 1035:     */       case '^': 
/* 1036:     */       case '`': 
/* 1037:     */       default: 
/* 1038: 836 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1039:     */       }
/* 1040: 840 */       k = this.text.length();
/* 1041: 841 */       mTREE_ELEMENT(true);
/* 1042: 842 */       this.text.setLength(k);
/* 1043: 843 */       localToken3 = this._returnToken;
/* 1044:     */       
/* 1045: 845 */       localVector.appendElement(this.generator.processStringForASTConstructor(localToken3.getText()));
/* 1046: 850 */       switch (LA(1))
/* 1047:     */       {
/* 1048:     */       case '\t': 
/* 1049:     */       case '\n': 
/* 1050:     */       case '\r': 
/* 1051:     */       case ' ': 
/* 1052: 853 */         k = this.text.length();
/* 1053: 854 */         mWS(false);
/* 1054: 855 */         this.text.setLength(k);
/* 1055: 856 */         break;
/* 1056:     */       case ')': 
/* 1057:     */       case ',': 
/* 1058:     */         break;
/* 1059:     */       default: 
/* 1060: 864 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1061:     */       }
/* 1062:     */     }
/* 1063: 875 */     this.text.setLength(j);this.text.append(this.generator.getASTCreateString(localVector));
/* 1064: 876 */     k = this.text.length();
/* 1065: 877 */     match(')');
/* 1066: 878 */     this.text.setLength(k);
/* 1067: 879 */     if ((paramBoolean) && (localToken1 == null) && (i != -1))
/* 1068:     */     {
/* 1069: 880 */       localToken1 = makeToken(i);
/* 1070: 881 */       localToken1.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 1071:     */     }
/* 1072: 883 */     this._returnToken = localToken1;
/* 1073:     */   }
/* 1074:     */   
/* 1075:     */   protected final void mWS(boolean paramBoolean)
/* 1076:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 1077:     */   {
/* 1078: 887 */     Token localToken = null;int j = this.text.length();
/* 1079: 888 */     int i = 28;
/* 1080:     */     
/* 1081:     */ 
/* 1082:     */ 
/* 1083: 892 */     int k = 0;
/* 1084:     */     for (;;)
/* 1085:     */     {
/* 1086: 895 */       if ((LA(1) == '\r') && (LA(2) == '\n'))
/* 1087:     */       {
/* 1088: 896 */         match('\r');
/* 1089: 897 */         match('\n');
/* 1090: 898 */         newline();
/* 1091:     */       }
/* 1092: 900 */       else if (LA(1) == ' ')
/* 1093:     */       {
/* 1094: 901 */         match(' ');
/* 1095:     */       }
/* 1096: 903 */       else if (LA(1) == '\t')
/* 1097:     */       {
/* 1098: 904 */         match('\t');
/* 1099:     */       }
/* 1100: 906 */       else if (LA(1) == '\r')
/* 1101:     */       {
/* 1102: 907 */         match('\r');
/* 1103: 908 */         newline();
/* 1104:     */       }
/* 1105: 910 */       else if (LA(1) == '\n')
/* 1106:     */       {
/* 1107: 911 */         match('\n');
/* 1108: 912 */         newline();
/* 1109:     */       }
/* 1110:     */       else
/* 1111:     */       {
/* 1112: 915 */         if (k >= 1) {
/* 1113:     */           break;
/* 1114:     */         }
/* 1115: 915 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1116:     */       }
/* 1117: 918 */       k++;
/* 1118:     */     }
/* 1119: 921 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 1120:     */     {
/* 1121: 922 */       localToken = makeToken(i);
/* 1122: 923 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 1123:     */     }
/* 1124: 925 */     this._returnToken = localToken;
/* 1125:     */   }
/* 1126:     */   
/* 1127:     */   protected final void mID(boolean paramBoolean)
/* 1128:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 1129:     */   {
/* 1130: 929 */     Token localToken = null;int j = this.text.length();
/* 1131: 930 */     int i = 17;
/* 1132: 934 */     switch (LA(1))
/* 1133:     */     {
/* 1134:     */     case 'a': 
/* 1135:     */     case 'b': 
/* 1136:     */     case 'c': 
/* 1137:     */     case 'd': 
/* 1138:     */     case 'e': 
/* 1139:     */     case 'f': 
/* 1140:     */     case 'g': 
/* 1141:     */     case 'h': 
/* 1142:     */     case 'i': 
/* 1143:     */     case 'j': 
/* 1144:     */     case 'k': 
/* 1145:     */     case 'l': 
/* 1146:     */     case 'm': 
/* 1147:     */     case 'n': 
/* 1148:     */     case 'o': 
/* 1149:     */     case 'p': 
/* 1150:     */     case 'q': 
/* 1151:     */     case 'r': 
/* 1152:     */     case 's': 
/* 1153:     */     case 't': 
/* 1154:     */     case 'u': 
/* 1155:     */     case 'v': 
/* 1156:     */     case 'w': 
/* 1157:     */     case 'x': 
/* 1158:     */     case 'y': 
/* 1159:     */     case 'z': 
/* 1160: 943 */       matchRange('a', 'z');
/* 1161: 944 */       break;
/* 1162:     */     case 'A': 
/* 1163:     */     case 'B': 
/* 1164:     */     case 'C': 
/* 1165:     */     case 'D': 
/* 1166:     */     case 'E': 
/* 1167:     */     case 'F': 
/* 1168:     */     case 'G': 
/* 1169:     */     case 'H': 
/* 1170:     */     case 'I': 
/* 1171:     */     case 'J': 
/* 1172:     */     case 'K': 
/* 1173:     */     case 'L': 
/* 1174:     */     case 'M': 
/* 1175:     */     case 'N': 
/* 1176:     */     case 'O': 
/* 1177:     */     case 'P': 
/* 1178:     */     case 'Q': 
/* 1179:     */     case 'R': 
/* 1180:     */     case 'S': 
/* 1181:     */     case 'T': 
/* 1182:     */     case 'U': 
/* 1183:     */     case 'V': 
/* 1184:     */     case 'W': 
/* 1185:     */     case 'X': 
/* 1186:     */     case 'Y': 
/* 1187:     */     case 'Z': 
/* 1188: 954 */       matchRange('A', 'Z');
/* 1189: 955 */       break;
/* 1190:     */     case '_': 
/* 1191: 959 */       match('_');
/* 1192: 960 */       break;
/* 1193:     */     case ':': 
/* 1194: 964 */       match("::");
/* 1195: 965 */       break;
/* 1196:     */     case ';': 
/* 1197:     */     case '<': 
/* 1198:     */     case '=': 
/* 1199:     */     case '>': 
/* 1200:     */     case '?': 
/* 1201:     */     case '@': 
/* 1202:     */     case '[': 
/* 1203:     */     case '\\': 
/* 1204:     */     case ']': 
/* 1205:     */     case '^': 
/* 1206:     */     case '`': 
/* 1207:     */     default: 
/* 1208: 969 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1209:     */     }
/* 1210: 976 */     while (_tokenSet_9.member(LA(1))) {
/* 1211: 978 */       switch (LA(1))
/* 1212:     */       {
/* 1213:     */       case 'a': 
/* 1214:     */       case 'b': 
/* 1215:     */       case 'c': 
/* 1216:     */       case 'd': 
/* 1217:     */       case 'e': 
/* 1218:     */       case 'f': 
/* 1219:     */       case 'g': 
/* 1220:     */       case 'h': 
/* 1221:     */       case 'i': 
/* 1222:     */       case 'j': 
/* 1223:     */       case 'k': 
/* 1224:     */       case 'l': 
/* 1225:     */       case 'm': 
/* 1226:     */       case 'n': 
/* 1227:     */       case 'o': 
/* 1228:     */       case 'p': 
/* 1229:     */       case 'q': 
/* 1230:     */       case 'r': 
/* 1231:     */       case 's': 
/* 1232:     */       case 't': 
/* 1233:     */       case 'u': 
/* 1234:     */       case 'v': 
/* 1235:     */       case 'w': 
/* 1236:     */       case 'x': 
/* 1237:     */       case 'y': 
/* 1238:     */       case 'z': 
/* 1239: 987 */         matchRange('a', 'z');
/* 1240: 988 */         break;
/* 1241:     */       case 'A': 
/* 1242:     */       case 'B': 
/* 1243:     */       case 'C': 
/* 1244:     */       case 'D': 
/* 1245:     */       case 'E': 
/* 1246:     */       case 'F': 
/* 1247:     */       case 'G': 
/* 1248:     */       case 'H': 
/* 1249:     */       case 'I': 
/* 1250:     */       case 'J': 
/* 1251:     */       case 'K': 
/* 1252:     */       case 'L': 
/* 1253:     */       case 'M': 
/* 1254:     */       case 'N': 
/* 1255:     */       case 'O': 
/* 1256:     */       case 'P': 
/* 1257:     */       case 'Q': 
/* 1258:     */       case 'R': 
/* 1259:     */       case 'S': 
/* 1260:     */       case 'T': 
/* 1261:     */       case 'U': 
/* 1262:     */       case 'V': 
/* 1263:     */       case 'W': 
/* 1264:     */       case 'X': 
/* 1265:     */       case 'Y': 
/* 1266:     */       case 'Z': 
/* 1267: 998 */         matchRange('A', 'Z');
/* 1268: 999 */         break;
/* 1269:     */       case '0': 
/* 1270:     */       case '1': 
/* 1271:     */       case '2': 
/* 1272:     */       case '3': 
/* 1273:     */       case '4': 
/* 1274:     */       case '5': 
/* 1275:     */       case '6': 
/* 1276:     */       case '7': 
/* 1277:     */       case '8': 
/* 1278:     */       case '9': 
/* 1279:1005 */         matchRange('0', '9');
/* 1280:1006 */         break;
/* 1281:     */       case '_': 
/* 1282:1010 */         match('_');
/* 1283:1011 */         break;
/* 1284:     */       case ':': 
/* 1285:1015 */         match("::");
/* 1286:1016 */         break;
/* 1287:     */       case ';': 
/* 1288:     */       case '<': 
/* 1289:     */       case '=': 
/* 1290:     */       case '>': 
/* 1291:     */       case '?': 
/* 1292:     */       case '@': 
/* 1293:     */       case '[': 
/* 1294:     */       case '\\': 
/* 1295:     */       case ']': 
/* 1296:     */       case '^': 
/* 1297:     */       case '`': 
/* 1298:     */       default: 
/* 1299:1020 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1300:     */       }
/* 1301:     */     }
/* 1302:1031 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 1303:     */     {
/* 1304:1032 */       localToken = makeToken(i);
/* 1305:1033 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 1306:     */     }
/* 1307:1035 */     this._returnToken = localToken;
/* 1308:     */   }
/* 1309:     */   
/* 1310:     */   protected final void mVAR_ASSIGN(boolean paramBoolean)
/* 1311:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 1312:     */   {
/* 1313:1039 */     Token localToken = null;int j = this.text.length();
/* 1314:1040 */     int i = 18;
/* 1315:     */     
/* 1316:     */ 
/* 1317:1043 */     match('=');
/* 1318:1047 */     if ((LA(1) != '=') && (this.transInfo != null) && (this.transInfo.refRuleRoot != null)) {
/* 1319:1048 */       this.transInfo.assignToRoot = true;
/* 1320:     */     }
/* 1321:1051 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 1322:     */     {
/* 1323:1052 */       localToken = makeToken(i);
/* 1324:1053 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 1325:     */     }
/* 1326:1055 */     this._returnToken = localToken;
/* 1327:     */   }
/* 1328:     */   
/* 1329:     */   protected final void mAST_CONSTRUCTOR(boolean paramBoolean)
/* 1330:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 1331:     */   {
/* 1332:1059 */     Token localToken1 = null;int j = this.text.length();
/* 1333:1060 */     int i = 10;
/* 1334:     */     
/* 1335:1062 */     Token localToken2 = null;
/* 1336:1063 */     Token localToken3 = null;
/* 1337:     */     
/* 1338:1065 */     int k = this.text.length();
/* 1339:1066 */     match('[');
/* 1340:1067 */     this.text.setLength(k);
/* 1341:1069 */     switch (LA(1))
/* 1342:     */     {
/* 1343:     */     case '\t': 
/* 1344:     */     case '\n': 
/* 1345:     */     case '\r': 
/* 1346:     */     case ' ': 
/* 1347:1072 */       k = this.text.length();
/* 1348:1073 */       mWS(false);
/* 1349:1074 */       this.text.setLength(k);
/* 1350:1075 */       break;
/* 1351:     */     case '"': 
/* 1352:     */     case '#': 
/* 1353:     */     case '(': 
/* 1354:     */     case '0': 
/* 1355:     */     case '1': 
/* 1356:     */     case '2': 
/* 1357:     */     case '3': 
/* 1358:     */     case '4': 
/* 1359:     */     case '5': 
/* 1360:     */     case '6': 
/* 1361:     */     case '7': 
/* 1362:     */     case '8': 
/* 1363:     */     case '9': 
/* 1364:     */     case ':': 
/* 1365:     */     case 'A': 
/* 1366:     */     case 'B': 
/* 1367:     */     case 'C': 
/* 1368:     */     case 'D': 
/* 1369:     */     case 'E': 
/* 1370:     */     case 'F': 
/* 1371:     */     case 'G': 
/* 1372:     */     case 'H': 
/* 1373:     */     case 'I': 
/* 1374:     */     case 'J': 
/* 1375:     */     case 'K': 
/* 1376:     */     case 'L': 
/* 1377:     */     case 'M': 
/* 1378:     */     case 'N': 
/* 1379:     */     case 'O': 
/* 1380:     */     case 'P': 
/* 1381:     */     case 'Q': 
/* 1382:     */     case 'R': 
/* 1383:     */     case 'S': 
/* 1384:     */     case 'T': 
/* 1385:     */     case 'U': 
/* 1386:     */     case 'V': 
/* 1387:     */     case 'W': 
/* 1388:     */     case 'X': 
/* 1389:     */     case 'Y': 
/* 1390:     */     case 'Z': 
/* 1391:     */     case '[': 
/* 1392:     */     case '_': 
/* 1393:     */     case 'a': 
/* 1394:     */     case 'b': 
/* 1395:     */     case 'c': 
/* 1396:     */     case 'd': 
/* 1397:     */     case 'e': 
/* 1398:     */     case 'f': 
/* 1399:     */     case 'g': 
/* 1400:     */     case 'h': 
/* 1401:     */     case 'i': 
/* 1402:     */     case 'j': 
/* 1403:     */     case 'k': 
/* 1404:     */     case 'l': 
/* 1405:     */     case 'm': 
/* 1406:     */     case 'n': 
/* 1407:     */     case 'o': 
/* 1408:     */     case 'p': 
/* 1409:     */     case 'q': 
/* 1410:     */     case 'r': 
/* 1411:     */     case 's': 
/* 1412:     */     case 't': 
/* 1413:     */     case 'u': 
/* 1414:     */     case 'v': 
/* 1415:     */     case 'w': 
/* 1416:     */     case 'x': 
/* 1417:     */     case 'y': 
/* 1418:     */     case 'z': 
/* 1419:     */       break;
/* 1420:     */     case '\013': 
/* 1421:     */     case '\f': 
/* 1422:     */     case '\016': 
/* 1423:     */     case '\017': 
/* 1424:     */     case '\020': 
/* 1425:     */     case '\021': 
/* 1426:     */     case '\022': 
/* 1427:     */     case '\023': 
/* 1428:     */     case '\024': 
/* 1429:     */     case '\025': 
/* 1430:     */     case '\026': 
/* 1431:     */     case '\027': 
/* 1432:     */     case '\030': 
/* 1433:     */     case '\031': 
/* 1434:     */     case '\032': 
/* 1435:     */     case '\033': 
/* 1436:     */     case '\034': 
/* 1437:     */     case '\035': 
/* 1438:     */     case '\036': 
/* 1439:     */     case '\037': 
/* 1440:     */     case '!': 
/* 1441:     */     case '$': 
/* 1442:     */     case '%': 
/* 1443:     */     case '&': 
/* 1444:     */     case '\'': 
/* 1445:     */     case ')': 
/* 1446:     */     case '*': 
/* 1447:     */     case '+': 
/* 1448:     */     case ',': 
/* 1449:     */     case '-': 
/* 1450:     */     case '.': 
/* 1451:     */     case '/': 
/* 1452:     */     case ';': 
/* 1453:     */     case '<': 
/* 1454:     */     case '=': 
/* 1455:     */     case '>': 
/* 1456:     */     case '?': 
/* 1457:     */     case '@': 
/* 1458:     */     case '\\': 
/* 1459:     */     case ']': 
/* 1460:     */     case '^': 
/* 1461:     */     case '`': 
/* 1462:     */     default: 
/* 1463:1099 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1464:     */     }
/* 1465:1103 */     k = this.text.length();
/* 1466:1104 */     mAST_CTOR_ELEMENT(true);
/* 1467:1105 */     this.text.setLength(k);
/* 1468:1106 */     localToken2 = this._returnToken;
/* 1469:1108 */     switch (LA(1))
/* 1470:     */     {
/* 1471:     */     case '\t': 
/* 1472:     */     case '\n': 
/* 1473:     */     case '\r': 
/* 1474:     */     case ' ': 
/* 1475:1111 */       k = this.text.length();
/* 1476:1112 */       mWS(false);
/* 1477:1113 */       this.text.setLength(k);
/* 1478:1114 */       break;
/* 1479:     */     case ',': 
/* 1480:     */     case ']': 
/* 1481:     */       break;
/* 1482:     */     default: 
/* 1483:1122 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1484:     */     }
/* 1485:1127 */     switch (LA(1))
/* 1486:     */     {
/* 1487:     */     case ',': 
/* 1488:1130 */       k = this.text.length();
/* 1489:1131 */       match(',');
/* 1490:1132 */       this.text.setLength(k);
/* 1491:1134 */       switch (LA(1))
/* 1492:     */       {
/* 1493:     */       case '\t': 
/* 1494:     */       case '\n': 
/* 1495:     */       case '\r': 
/* 1496:     */       case ' ': 
/* 1497:1137 */         k = this.text.length();
/* 1498:1138 */         mWS(false);
/* 1499:1139 */         this.text.setLength(k);
/* 1500:1140 */         break;
/* 1501:     */       case '"': 
/* 1502:     */       case '#': 
/* 1503:     */       case '(': 
/* 1504:     */       case '0': 
/* 1505:     */       case '1': 
/* 1506:     */       case '2': 
/* 1507:     */       case '3': 
/* 1508:     */       case '4': 
/* 1509:     */       case '5': 
/* 1510:     */       case '6': 
/* 1511:     */       case '7': 
/* 1512:     */       case '8': 
/* 1513:     */       case '9': 
/* 1514:     */       case ':': 
/* 1515:     */       case 'A': 
/* 1516:     */       case 'B': 
/* 1517:     */       case 'C': 
/* 1518:     */       case 'D': 
/* 1519:     */       case 'E': 
/* 1520:     */       case 'F': 
/* 1521:     */       case 'G': 
/* 1522:     */       case 'H': 
/* 1523:     */       case 'I': 
/* 1524:     */       case 'J': 
/* 1525:     */       case 'K': 
/* 1526:     */       case 'L': 
/* 1527:     */       case 'M': 
/* 1528:     */       case 'N': 
/* 1529:     */       case 'O': 
/* 1530:     */       case 'P': 
/* 1531:     */       case 'Q': 
/* 1532:     */       case 'R': 
/* 1533:     */       case 'S': 
/* 1534:     */       case 'T': 
/* 1535:     */       case 'U': 
/* 1536:     */       case 'V': 
/* 1537:     */       case 'W': 
/* 1538:     */       case 'X': 
/* 1539:     */       case 'Y': 
/* 1540:     */       case 'Z': 
/* 1541:     */       case '[': 
/* 1542:     */       case '_': 
/* 1543:     */       case 'a': 
/* 1544:     */       case 'b': 
/* 1545:     */       case 'c': 
/* 1546:     */       case 'd': 
/* 1547:     */       case 'e': 
/* 1548:     */       case 'f': 
/* 1549:     */       case 'g': 
/* 1550:     */       case 'h': 
/* 1551:     */       case 'i': 
/* 1552:     */       case 'j': 
/* 1553:     */       case 'k': 
/* 1554:     */       case 'l': 
/* 1555:     */       case 'm': 
/* 1556:     */       case 'n': 
/* 1557:     */       case 'o': 
/* 1558:     */       case 'p': 
/* 1559:     */       case 'q': 
/* 1560:     */       case 'r': 
/* 1561:     */       case 's': 
/* 1562:     */       case 't': 
/* 1563:     */       case 'u': 
/* 1564:     */       case 'v': 
/* 1565:     */       case 'w': 
/* 1566:     */       case 'x': 
/* 1567:     */       case 'y': 
/* 1568:     */       case 'z': 
/* 1569:     */         break;
/* 1570:     */       case '\013': 
/* 1571:     */       case '\f': 
/* 1572:     */       case '\016': 
/* 1573:     */       case '\017': 
/* 1574:     */       case '\020': 
/* 1575:     */       case '\021': 
/* 1576:     */       case '\022': 
/* 1577:     */       case '\023': 
/* 1578:     */       case '\024': 
/* 1579:     */       case '\025': 
/* 1580:     */       case '\026': 
/* 1581:     */       case '\027': 
/* 1582:     */       case '\030': 
/* 1583:     */       case '\031': 
/* 1584:     */       case '\032': 
/* 1585:     */       case '\033': 
/* 1586:     */       case '\034': 
/* 1587:     */       case '\035': 
/* 1588:     */       case '\036': 
/* 1589:     */       case '\037': 
/* 1590:     */       case '!': 
/* 1591:     */       case '$': 
/* 1592:     */       case '%': 
/* 1593:     */       case '&': 
/* 1594:     */       case '\'': 
/* 1595:     */       case ')': 
/* 1596:     */       case '*': 
/* 1597:     */       case '+': 
/* 1598:     */       case ',': 
/* 1599:     */       case '-': 
/* 1600:     */       case '.': 
/* 1601:     */       case '/': 
/* 1602:     */       case ';': 
/* 1603:     */       case '<': 
/* 1604:     */       case '=': 
/* 1605:     */       case '>': 
/* 1606:     */       case '?': 
/* 1607:     */       case '@': 
/* 1608:     */       case '\\': 
/* 1609:     */       case ']': 
/* 1610:     */       case '^': 
/* 1611:     */       case '`': 
/* 1612:     */       default: 
/* 1613:1164 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1614:     */       }
/* 1615:1168 */       k = this.text.length();
/* 1616:1169 */       mAST_CTOR_ELEMENT(true);
/* 1617:1170 */       this.text.setLength(k);
/* 1618:1171 */       localToken3 = this._returnToken;
/* 1619:1173 */       switch (LA(1))
/* 1620:     */       {
/* 1621:     */       case '\t': 
/* 1622:     */       case '\n': 
/* 1623:     */       case '\r': 
/* 1624:     */       case ' ': 
/* 1625:1176 */         k = this.text.length();
/* 1626:1177 */         mWS(false);
/* 1627:1178 */         this.text.setLength(k);
/* 1628:1179 */         break;
/* 1629:     */       case ']': 
/* 1630:     */         break;
/* 1631:     */       default: 
/* 1632:1187 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1633:     */       }
/* 1634:     */       break;
/* 1635:     */     case ']': 
/* 1636:     */       break;
/* 1637:     */     default: 
/* 1638:1199 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1639:     */     }
/* 1640:1203 */     k = this.text.length();
/* 1641:1204 */     match(']');
/* 1642:1205 */     this.text.setLength(k);
/* 1643:     */     
/* 1644:     */ 
/* 1645:     */ 
/* 1646:1209 */     String str = this.generator.processStringForASTConstructor(localToken2.getText());
/* 1647:1213 */     if (localToken3 != null) {
/* 1648:1214 */       str = str + "," + localToken3.getText();
/* 1649:     */     }
/* 1650:1216 */     this.text.setLength(j);this.text.append(this.generator.getASTCreateString(null, str));
/* 1651:1218 */     if ((paramBoolean) && (localToken1 == null) && (i != -1))
/* 1652:     */     {
/* 1653:1219 */       localToken1 = makeToken(i);
/* 1654:1220 */       localToken1.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 1655:     */     }
/* 1656:1222 */     this._returnToken = localToken1;
/* 1657:     */   }
/* 1658:     */   
/* 1659:     */   protected final void mTEXT_ARG(boolean paramBoolean)
/* 1660:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 1661:     */   {
/* 1662:1226 */     Token localToken = null;int j = this.text.length();
/* 1663:1227 */     int i = 13;
/* 1664:1231 */     switch (LA(1))
/* 1665:     */     {
/* 1666:     */     case '\t': 
/* 1667:     */     case '\n': 
/* 1668:     */     case '\r': 
/* 1669:     */     case ' ': 
/* 1670:1234 */       mWS(false);
/* 1671:1235 */       break;
/* 1672:     */     case '"': 
/* 1673:     */     case '$': 
/* 1674:     */     case '\'': 
/* 1675:     */     case '+': 
/* 1676:     */     case '0': 
/* 1677:     */     case '1': 
/* 1678:     */     case '2': 
/* 1679:     */     case '3': 
/* 1680:     */     case '4': 
/* 1681:     */     case '5': 
/* 1682:     */     case '6': 
/* 1683:     */     case '7': 
/* 1684:     */     case '8': 
/* 1685:     */     case '9': 
/* 1686:     */     case ':': 
/* 1687:     */     case 'A': 
/* 1688:     */     case 'B': 
/* 1689:     */     case 'C': 
/* 1690:     */     case 'D': 
/* 1691:     */     case 'E': 
/* 1692:     */     case 'F': 
/* 1693:     */     case 'G': 
/* 1694:     */     case 'H': 
/* 1695:     */     case 'I': 
/* 1696:     */     case 'J': 
/* 1697:     */     case 'K': 
/* 1698:     */     case 'L': 
/* 1699:     */     case 'M': 
/* 1700:     */     case 'N': 
/* 1701:     */     case 'O': 
/* 1702:     */     case 'P': 
/* 1703:     */     case 'Q': 
/* 1704:     */     case 'R': 
/* 1705:     */     case 'S': 
/* 1706:     */     case 'T': 
/* 1707:     */     case 'U': 
/* 1708:     */     case 'V': 
/* 1709:     */     case 'W': 
/* 1710:     */     case 'X': 
/* 1711:     */     case 'Y': 
/* 1712:     */     case 'Z': 
/* 1713:     */     case '_': 
/* 1714:     */     case 'a': 
/* 1715:     */     case 'b': 
/* 1716:     */     case 'c': 
/* 1717:     */     case 'd': 
/* 1718:     */     case 'e': 
/* 1719:     */     case 'f': 
/* 1720:     */     case 'g': 
/* 1721:     */     case 'h': 
/* 1722:     */     case 'i': 
/* 1723:     */     case 'j': 
/* 1724:     */     case 'k': 
/* 1725:     */     case 'l': 
/* 1726:     */     case 'm': 
/* 1727:     */     case 'n': 
/* 1728:     */     case 'o': 
/* 1729:     */     case 'p': 
/* 1730:     */     case 'q': 
/* 1731:     */     case 'r': 
/* 1732:     */     case 's': 
/* 1733:     */     case 't': 
/* 1734:     */     case 'u': 
/* 1735:     */     case 'v': 
/* 1736:     */     case 'w': 
/* 1737:     */     case 'x': 
/* 1738:     */     case 'y': 
/* 1739:     */     case 'z': 
/* 1740:     */       break;
/* 1741:     */     case '\013': 
/* 1742:     */     case '\f': 
/* 1743:     */     case '\016': 
/* 1744:     */     case '\017': 
/* 1745:     */     case '\020': 
/* 1746:     */     case '\021': 
/* 1747:     */     case '\022': 
/* 1748:     */     case '\023': 
/* 1749:     */     case '\024': 
/* 1750:     */     case '\025': 
/* 1751:     */     case '\026': 
/* 1752:     */     case '\027': 
/* 1753:     */     case '\030': 
/* 1754:     */     case '\031': 
/* 1755:     */     case '\032': 
/* 1756:     */     case '\033': 
/* 1757:     */     case '\034': 
/* 1758:     */     case '\035': 
/* 1759:     */     case '\036': 
/* 1760:     */     case '\037': 
/* 1761:     */     case '!': 
/* 1762:     */     case '#': 
/* 1763:     */     case '%': 
/* 1764:     */     case '&': 
/* 1765:     */     case '(': 
/* 1766:     */     case ')': 
/* 1767:     */     case '*': 
/* 1768:     */     case ',': 
/* 1769:     */     case '-': 
/* 1770:     */     case '.': 
/* 1771:     */     case '/': 
/* 1772:     */     case ';': 
/* 1773:     */     case '<': 
/* 1774:     */     case '=': 
/* 1775:     */     case '>': 
/* 1776:     */     case '?': 
/* 1777:     */     case '@': 
/* 1778:     */     case '[': 
/* 1779:     */     case '\\': 
/* 1780:     */     case ']': 
/* 1781:     */     case '^': 
/* 1782:     */     case '`': 
/* 1783:     */     default: 
/* 1784:1259 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1785:     */     }
/* 1786:1264 */     int k = 0;
/* 1787:     */     for (;;)
/* 1788:     */     {
/* 1789:1267 */       if ((_tokenSet_10.member(LA(1))) && (LA(2) >= '\003') && (LA(2) <= 'ÿ'))
/* 1790:     */       {
/* 1791:1268 */         mTEXT_ARG_ELEMENT(false);
/* 1792:1270 */         if ((_tokenSet_4.member(LA(1))) && (_tokenSet_11.member(LA(2)))) {
/* 1793:1271 */           mWS(false);
/* 1794:1273 */         } else if (!_tokenSet_11.member(LA(1))) {
/* 1795:1276 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1796:     */         }
/* 1797:     */       }
/* 1798:     */       else
/* 1799:     */       {
/* 1800:1282 */         if (k >= 1) {
/* 1801:     */           break;
/* 1802:     */         }
/* 1803:1282 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1804:     */       }
/* 1805:1285 */       k++;
/* 1806:     */     }
/* 1807:1288 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 1808:     */     {
/* 1809:1289 */       localToken = makeToken(i);
/* 1810:1290 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 1811:     */     }
/* 1812:1292 */     this._returnToken = localToken;
/* 1813:     */   }
/* 1814:     */   
/* 1815:     */   protected final void mTREE_ELEMENT(boolean paramBoolean)
/* 1816:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 1817:     */   {
/* 1818:1296 */     Token localToken1 = null;int j = this.text.length();
/* 1819:1297 */     int i = 9;
/* 1820:     */     
/* 1821:1299 */     Token localToken2 = null;
/* 1822:1302 */     switch (LA(1))
/* 1823:     */     {
/* 1824:     */     case '(': 
/* 1825:1305 */       mTREE(false);
/* 1826:1306 */       break;
/* 1827:     */     case '[': 
/* 1828:1310 */       mAST_CONSTRUCTOR(false);
/* 1829:1311 */       break;
/* 1830:     */     case ':': 
/* 1831:     */     case 'A': 
/* 1832:     */     case 'B': 
/* 1833:     */     case 'C': 
/* 1834:     */     case 'D': 
/* 1835:     */     case 'E': 
/* 1836:     */     case 'F': 
/* 1837:     */     case 'G': 
/* 1838:     */     case 'H': 
/* 1839:     */     case 'I': 
/* 1840:     */     case 'J': 
/* 1841:     */     case 'K': 
/* 1842:     */     case 'L': 
/* 1843:     */     case 'M': 
/* 1844:     */     case 'N': 
/* 1845:     */     case 'O': 
/* 1846:     */     case 'P': 
/* 1847:     */     case 'Q': 
/* 1848:     */     case 'R': 
/* 1849:     */     case 'S': 
/* 1850:     */     case 'T': 
/* 1851:     */     case 'U': 
/* 1852:     */     case 'V': 
/* 1853:     */     case 'W': 
/* 1854:     */     case 'X': 
/* 1855:     */     case 'Y': 
/* 1856:     */     case 'Z': 
/* 1857:     */     case '_': 
/* 1858:     */     case 'a': 
/* 1859:     */     case 'b': 
/* 1860:     */     case 'c': 
/* 1861:     */     case 'd': 
/* 1862:     */     case 'e': 
/* 1863:     */     case 'f': 
/* 1864:     */     case 'g': 
/* 1865:     */     case 'h': 
/* 1866:     */     case 'i': 
/* 1867:     */     case 'j': 
/* 1868:     */     case 'k': 
/* 1869:     */     case 'l': 
/* 1870:     */     case 'm': 
/* 1871:     */     case 'n': 
/* 1872:     */     case 'o': 
/* 1873:     */     case 'p': 
/* 1874:     */     case 'q': 
/* 1875:     */     case 'r': 
/* 1876:     */     case 's': 
/* 1877:     */     case 't': 
/* 1878:     */     case 'u': 
/* 1879:     */     case 'v': 
/* 1880:     */     case 'w': 
/* 1881:     */     case 'x': 
/* 1882:     */     case 'y': 
/* 1883:     */     case 'z': 
/* 1884:1328 */       mID_ELEMENT(false);
/* 1885:1329 */       break;
/* 1886:     */     case '"': 
/* 1887:1333 */       mSTRING(false);
/* 1888:1334 */       break;
/* 1889:     */     case '#': 
/* 1890:     */     case '$': 
/* 1891:     */     case '%': 
/* 1892:     */     case '&': 
/* 1893:     */     case '\'': 
/* 1894:     */     case ')': 
/* 1895:     */     case '*': 
/* 1896:     */     case '+': 
/* 1897:     */     case ',': 
/* 1898:     */     case '-': 
/* 1899:     */     case '.': 
/* 1900:     */     case '/': 
/* 1901:     */     case '0': 
/* 1902:     */     case '1': 
/* 1903:     */     case '2': 
/* 1904:     */     case '3': 
/* 1905:     */     case '4': 
/* 1906:     */     case '5': 
/* 1907:     */     case '6': 
/* 1908:     */     case '7': 
/* 1909:     */     case '8': 
/* 1910:     */     case '9': 
/* 1911:     */     case ';': 
/* 1912:     */     case '<': 
/* 1913:     */     case '=': 
/* 1914:     */     case '>': 
/* 1915:     */     case '?': 
/* 1916:     */     case '@': 
/* 1917:     */     case '\\': 
/* 1918:     */     case ']': 
/* 1919:     */     case '^': 
/* 1920:     */     case '`': 
/* 1921:     */     default: 
/* 1922:     */       int k;
/* 1923:1337 */       if ((LA(1) == '#') && (LA(2) == '('))
/* 1924:     */       {
/* 1925:1338 */         k = this.text.length();
/* 1926:1339 */         match('#');
/* 1927:1340 */         this.text.setLength(k);
/* 1928:1341 */         mTREE(false);
/* 1929:     */       }
/* 1930:1343 */       else if ((LA(1) == '#') && (LA(2) == '['))
/* 1931:     */       {
/* 1932:1344 */         k = this.text.length();
/* 1933:1345 */         match('#');
/* 1934:1346 */         this.text.setLength(k);
/* 1935:1347 */         mAST_CONSTRUCTOR(false);
/* 1936:     */       }
/* 1937:     */       else
/* 1938:     */       {
/* 1939:     */         String str;
/* 1940:1349 */         if ((LA(1) == '#') && (_tokenSet_12.member(LA(2))))
/* 1941:     */         {
/* 1942:1350 */           k = this.text.length();
/* 1943:1351 */           match('#');
/* 1944:1352 */           this.text.setLength(k);
/* 1945:1353 */           boolean bool = mID_ELEMENT(true);
/* 1946:1354 */           localToken2 = this._returnToken;
/* 1947:1356 */           if (!bool)
/* 1948:     */           {
/* 1949:1358 */             str = this.generator.mapTreeId(localToken2.getText(), null);
/* 1950:1360 */             if (str != null)
/* 1951:     */             {
/* 1952:1361 */               this.text.setLength(j);this.text.append(str);
/* 1953:     */             }
/* 1954:     */           }
/* 1955:     */         }
/* 1956:1366 */         else if ((LA(1) == '#') && (LA(2) == '#'))
/* 1957:     */         {
/* 1958:1367 */           match("##");
/* 1959:1369 */           if (this.currentRule != null)
/* 1960:     */           {
/* 1961:1371 */             str = this.currentRule.getRuleName() + "_AST";
/* 1962:1372 */             this.text.setLength(j);this.text.append(str);
/* 1963:     */           }
/* 1964:     */           else
/* 1965:     */           {
/* 1966:1376 */             reportError("\"##\" not valid in this context");
/* 1967:1377 */             this.text.setLength(j);this.text.append("##");
/* 1968:     */           }
/* 1969:     */         }
/* 1970:     */         else
/* 1971:     */         {
/* 1972:1382 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1973:     */         }
/* 1974:     */       }
/* 1975:     */       break;
/* 1976:     */     }
/* 1977:1385 */     if ((paramBoolean) && (localToken1 == null) && (i != -1))
/* 1978:     */     {
/* 1979:1386 */       localToken1 = makeToken(i);
/* 1980:1387 */       localToken1.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 1981:     */     }
/* 1982:1389 */     this._returnToken = localToken1;
/* 1983:     */   }
/* 1984:     */   
/* 1985:     */   protected final boolean mID_ELEMENT(boolean paramBoolean)
/* 1986:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 1987:     */   {
/* 1988:1396 */     boolean bool = false;
/* 1989:1397 */     Token localToken1 = null;int j = this.text.length();
/* 1990:1398 */     int i = 12;
/* 1991:     */     
/* 1992:1400 */     Token localToken2 = null;
/* 1993:     */     
/* 1994:1402 */     mID(true);
/* 1995:1403 */     localToken2 = this._returnToken;
/* 1996:     */     int k;
/* 1997:1405 */     if ((_tokenSet_4.member(LA(1))) && (_tokenSet_13.member(LA(2))))
/* 1998:     */     {
/* 1999:1406 */       k = this.text.length();
/* 2000:1407 */       mWS(false);
/* 2001:1408 */       this.text.setLength(k);
/* 2002:     */     }
/* 2003:1410 */     else if (!_tokenSet_13.member(LA(1)))
/* 2004:     */     {
/* 2005:1413 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2006:     */     }
/* 2007:1418 */     switch (LA(1))
/* 2008:     */     {
/* 2009:     */     case '(': 
/* 2010:     */     case '<': 
/* 2011:1422 */       switch (LA(1))
/* 2012:     */       {
/* 2013:     */       case '<': 
/* 2014:1425 */         match('<');
/* 2015:1429 */         while (_tokenSet_14.member(LA(1))) {
/* 2016:1430 */           matchNot('>');
/* 2017:     */         }
/* 2018:1438 */         match('>');
/* 2019:1439 */         break;
/* 2020:     */       case '(': 
/* 2021:     */         break;
/* 2022:     */       default: 
/* 2023:1447 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2024:     */       }
/* 2025:1451 */       match('(');
/* 2026:1453 */       if ((_tokenSet_4.member(LA(1))) && (_tokenSet_15.member(LA(2))) && (LA(3) >= '\003') && (LA(3) <= 'ÿ'))
/* 2027:     */       {
/* 2028:1454 */         k = this.text.length();
/* 2029:1455 */         mWS(false);
/* 2030:1456 */         this.text.setLength(k);
/* 2031:     */       }
/* 2032:1458 */       else if ((!_tokenSet_15.member(LA(1))) || (LA(2) < '\003') || (LA(2) > 'ÿ'))
/* 2033:     */       {
/* 2034:1461 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2035:     */       }
/* 2036:1466 */       switch (LA(1))
/* 2037:     */       {
/* 2038:     */       case '"': 
/* 2039:     */       case '#': 
/* 2040:     */       case '\'': 
/* 2041:     */       case '(': 
/* 2042:     */       case '0': 
/* 2043:     */       case '1': 
/* 2044:     */       case '2': 
/* 2045:     */       case '3': 
/* 2046:     */       case '4': 
/* 2047:     */       case '5': 
/* 2048:     */       case '6': 
/* 2049:     */       case '7': 
/* 2050:     */       case '8': 
/* 2051:     */       case '9': 
/* 2052:     */       case ':': 
/* 2053:     */       case 'A': 
/* 2054:     */       case 'B': 
/* 2055:     */       case 'C': 
/* 2056:     */       case 'D': 
/* 2057:     */       case 'E': 
/* 2058:     */       case 'F': 
/* 2059:     */       case 'G': 
/* 2060:     */       case 'H': 
/* 2061:     */       case 'I': 
/* 2062:     */       case 'J': 
/* 2063:     */       case 'K': 
/* 2064:     */       case 'L': 
/* 2065:     */       case 'M': 
/* 2066:     */       case 'N': 
/* 2067:     */       case 'O': 
/* 2068:     */       case 'P': 
/* 2069:     */       case 'Q': 
/* 2070:     */       case 'R': 
/* 2071:     */       case 'S': 
/* 2072:     */       case 'T': 
/* 2073:     */       case 'U': 
/* 2074:     */       case 'V': 
/* 2075:     */       case 'W': 
/* 2076:     */       case 'X': 
/* 2077:     */       case 'Y': 
/* 2078:     */       case 'Z': 
/* 2079:     */       case '[': 
/* 2080:     */       case '_': 
/* 2081:     */       case 'a': 
/* 2082:     */       case 'b': 
/* 2083:     */       case 'c': 
/* 2084:     */       case 'd': 
/* 2085:     */       case 'e': 
/* 2086:     */       case 'f': 
/* 2087:     */       case 'g': 
/* 2088:     */       case 'h': 
/* 2089:     */       case 'i': 
/* 2090:     */       case 'j': 
/* 2091:     */       case 'k': 
/* 2092:     */       case 'l': 
/* 2093:     */       case 'm': 
/* 2094:     */       case 'n': 
/* 2095:     */       case 'o': 
/* 2096:     */       case 'p': 
/* 2097:     */       case 'q': 
/* 2098:     */       case 'r': 
/* 2099:     */       case 's': 
/* 2100:     */       case 't': 
/* 2101:     */       case 'u': 
/* 2102:     */       case 'v': 
/* 2103:     */       case 'w': 
/* 2104:     */       case 'x': 
/* 2105:     */       case 'y': 
/* 2106:     */       case 'z': 
/* 2107:1486 */         mARG(false);
/* 2108:     */       }
/* 2109:1490 */       while (LA(1) == ',')
/* 2110:     */       {
/* 2111:1491 */         match(',');
/* 2112:1493 */         switch (LA(1))
/* 2113:     */         {
/* 2114:     */         case '\t': 
/* 2115:     */         case '\n': 
/* 2116:     */         case '\r': 
/* 2117:     */         case ' ': 
/* 2118:1496 */           k = this.text.length();
/* 2119:1497 */           mWS(false);
/* 2120:1498 */           this.text.setLength(k);
/* 2121:1499 */           break;
/* 2122:     */         case '"': 
/* 2123:     */         case '#': 
/* 2124:     */         case '\'': 
/* 2125:     */         case '(': 
/* 2126:     */         case '0': 
/* 2127:     */         case '1': 
/* 2128:     */         case '2': 
/* 2129:     */         case '3': 
/* 2130:     */         case '4': 
/* 2131:     */         case '5': 
/* 2132:     */         case '6': 
/* 2133:     */         case '7': 
/* 2134:     */         case '8': 
/* 2135:     */         case '9': 
/* 2136:     */         case ':': 
/* 2137:     */         case 'A': 
/* 2138:     */         case 'B': 
/* 2139:     */         case 'C': 
/* 2140:     */         case 'D': 
/* 2141:     */         case 'E': 
/* 2142:     */         case 'F': 
/* 2143:     */         case 'G': 
/* 2144:     */         case 'H': 
/* 2145:     */         case 'I': 
/* 2146:     */         case 'J': 
/* 2147:     */         case 'K': 
/* 2148:     */         case 'L': 
/* 2149:     */         case 'M': 
/* 2150:     */         case 'N': 
/* 2151:     */         case 'O': 
/* 2152:     */         case 'P': 
/* 2153:     */         case 'Q': 
/* 2154:     */         case 'R': 
/* 2155:     */         case 'S': 
/* 2156:     */         case 'T': 
/* 2157:     */         case 'U': 
/* 2158:     */         case 'V': 
/* 2159:     */         case 'W': 
/* 2160:     */         case 'X': 
/* 2161:     */         case 'Y': 
/* 2162:     */         case 'Z': 
/* 2163:     */         case '[': 
/* 2164:     */         case '_': 
/* 2165:     */         case 'a': 
/* 2166:     */         case 'b': 
/* 2167:     */         case 'c': 
/* 2168:     */         case 'd': 
/* 2169:     */         case 'e': 
/* 2170:     */         case 'f': 
/* 2171:     */         case 'g': 
/* 2172:     */         case 'h': 
/* 2173:     */         case 'i': 
/* 2174:     */         case 'j': 
/* 2175:     */         case 'k': 
/* 2176:     */         case 'l': 
/* 2177:     */         case 'm': 
/* 2178:     */         case 'n': 
/* 2179:     */         case 'o': 
/* 2180:     */         case 'p': 
/* 2181:     */         case 'q': 
/* 2182:     */         case 'r': 
/* 2183:     */         case 's': 
/* 2184:     */         case 't': 
/* 2185:     */         case 'u': 
/* 2186:     */         case 'v': 
/* 2187:     */         case 'w': 
/* 2188:     */         case 'x': 
/* 2189:     */         case 'y': 
/* 2190:     */         case 'z': 
/* 2191:     */           break;
/* 2192:     */         case '\013': 
/* 2193:     */         case '\f': 
/* 2194:     */         case '\016': 
/* 2195:     */         case '\017': 
/* 2196:     */         case '\020': 
/* 2197:     */         case '\021': 
/* 2198:     */         case '\022': 
/* 2199:     */         case '\023': 
/* 2200:     */         case '\024': 
/* 2201:     */         case '\025': 
/* 2202:     */         case '\026': 
/* 2203:     */         case '\027': 
/* 2204:     */         case '\030': 
/* 2205:     */         case '\031': 
/* 2206:     */         case '\032': 
/* 2207:     */         case '\033': 
/* 2208:     */         case '\034': 
/* 2209:     */         case '\035': 
/* 2210:     */         case '\036': 
/* 2211:     */         case '\037': 
/* 2212:     */         case '!': 
/* 2213:     */         case '$': 
/* 2214:     */         case '%': 
/* 2215:     */         case '&': 
/* 2216:     */         case ')': 
/* 2217:     */         case '*': 
/* 2218:     */         case '+': 
/* 2219:     */         case ',': 
/* 2220:     */         case '-': 
/* 2221:     */         case '.': 
/* 2222:     */         case '/': 
/* 2223:     */         case ';': 
/* 2224:     */         case '<': 
/* 2225:     */         case '=': 
/* 2226:     */         case '>': 
/* 2227:     */         case '?': 
/* 2228:     */         case '@': 
/* 2229:     */         case '\\': 
/* 2230:     */         case ']': 
/* 2231:     */         case '^': 
/* 2232:     */         case '`': 
/* 2233:     */         default: 
/* 2234:1524 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2235:     */         }
/* 2236:1528 */         mARG(false); continue;
/* 2237:     */         
/* 2238:     */ 
/* 2239:     */ 
/* 2240:     */ 
/* 2241:     */ 
/* 2242:     */ 
/* 2243:     */ 
/* 2244:     */ 
/* 2245:     */ 
/* 2246:     */ 
/* 2247:     */ 
/* 2248:     */ 
/* 2249:1541 */         break;
/* 2250:     */         
/* 2251:     */ 
/* 2252:     */ 
/* 2253:1545 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2254:     */       }
/* 2255:1550 */       switch (LA(1))
/* 2256:     */       {
/* 2257:     */       case '\t': 
/* 2258:     */       case '\n': 
/* 2259:     */       case '\r': 
/* 2260:     */       case ' ': 
/* 2261:1553 */         k = this.text.length();
/* 2262:1554 */         mWS(false);
/* 2263:1555 */         this.text.setLength(k);
/* 2264:1556 */         break;
/* 2265:     */       case ')': 
/* 2266:     */         break;
/* 2267:     */       default: 
/* 2268:1564 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2269:     */       }
/* 2270:1568 */       match(')');
/* 2271:1569 */       break;
/* 2272:     */     case '[': 
/* 2273:1574 */       int m = 0;
/* 2274:     */       for (;;)
/* 2275:     */       {
/* 2276:1577 */         if (LA(1) == '[')
/* 2277:     */         {
/* 2278:1578 */           match('[');
/* 2279:1580 */           switch (LA(1))
/* 2280:     */           {
/* 2281:     */           case '\t': 
/* 2282:     */           case '\n': 
/* 2283:     */           case '\r': 
/* 2284:     */           case ' ': 
/* 2285:1583 */             k = this.text.length();
/* 2286:1584 */             mWS(false);
/* 2287:1585 */             this.text.setLength(k);
/* 2288:1586 */             break;
/* 2289:     */           case '"': 
/* 2290:     */           case '#': 
/* 2291:     */           case '\'': 
/* 2292:     */           case '(': 
/* 2293:     */           case '0': 
/* 2294:     */           case '1': 
/* 2295:     */           case '2': 
/* 2296:     */           case '3': 
/* 2297:     */           case '4': 
/* 2298:     */           case '5': 
/* 2299:     */           case '6': 
/* 2300:     */           case '7': 
/* 2301:     */           case '8': 
/* 2302:     */           case '9': 
/* 2303:     */           case ':': 
/* 2304:     */           case 'A': 
/* 2305:     */           case 'B': 
/* 2306:     */           case 'C': 
/* 2307:     */           case 'D': 
/* 2308:     */           case 'E': 
/* 2309:     */           case 'F': 
/* 2310:     */           case 'G': 
/* 2311:     */           case 'H': 
/* 2312:     */           case 'I': 
/* 2313:     */           case 'J': 
/* 2314:     */           case 'K': 
/* 2315:     */           case 'L': 
/* 2316:     */           case 'M': 
/* 2317:     */           case 'N': 
/* 2318:     */           case 'O': 
/* 2319:     */           case 'P': 
/* 2320:     */           case 'Q': 
/* 2321:     */           case 'R': 
/* 2322:     */           case 'S': 
/* 2323:     */           case 'T': 
/* 2324:     */           case 'U': 
/* 2325:     */           case 'V': 
/* 2326:     */           case 'W': 
/* 2327:     */           case 'X': 
/* 2328:     */           case 'Y': 
/* 2329:     */           case 'Z': 
/* 2330:     */           case '[': 
/* 2331:     */           case '_': 
/* 2332:     */           case 'a': 
/* 2333:     */           case 'b': 
/* 2334:     */           case 'c': 
/* 2335:     */           case 'd': 
/* 2336:     */           case 'e': 
/* 2337:     */           case 'f': 
/* 2338:     */           case 'g': 
/* 2339:     */           case 'h': 
/* 2340:     */           case 'i': 
/* 2341:     */           case 'j': 
/* 2342:     */           case 'k': 
/* 2343:     */           case 'l': 
/* 2344:     */           case 'm': 
/* 2345:     */           case 'n': 
/* 2346:     */           case 'o': 
/* 2347:     */           case 'p': 
/* 2348:     */           case 'q': 
/* 2349:     */           case 'r': 
/* 2350:     */           case 's': 
/* 2351:     */           case 't': 
/* 2352:     */           case 'u': 
/* 2353:     */           case 'v': 
/* 2354:     */           case 'w': 
/* 2355:     */           case 'x': 
/* 2356:     */           case 'y': 
/* 2357:     */           case 'z': 
/* 2358:     */             break;
/* 2359:     */           case '\013': 
/* 2360:     */           case '\f': 
/* 2361:     */           case '\016': 
/* 2362:     */           case '\017': 
/* 2363:     */           case '\020': 
/* 2364:     */           case '\021': 
/* 2365:     */           case '\022': 
/* 2366:     */           case '\023': 
/* 2367:     */           case '\024': 
/* 2368:     */           case '\025': 
/* 2369:     */           case '\026': 
/* 2370:     */           case '\027': 
/* 2371:     */           case '\030': 
/* 2372:     */           case '\031': 
/* 2373:     */           case '\032': 
/* 2374:     */           case '\033': 
/* 2375:     */           case '\034': 
/* 2376:     */           case '\035': 
/* 2377:     */           case '\036': 
/* 2378:     */           case '\037': 
/* 2379:     */           case '!': 
/* 2380:     */           case '$': 
/* 2381:     */           case '%': 
/* 2382:     */           case '&': 
/* 2383:     */           case ')': 
/* 2384:     */           case '*': 
/* 2385:     */           case '+': 
/* 2386:     */           case ',': 
/* 2387:     */           case '-': 
/* 2388:     */           case '.': 
/* 2389:     */           case '/': 
/* 2390:     */           case ';': 
/* 2391:     */           case '<': 
/* 2392:     */           case '=': 
/* 2393:     */           case '>': 
/* 2394:     */           case '?': 
/* 2395:     */           case '@': 
/* 2396:     */           case '\\': 
/* 2397:     */           case ']': 
/* 2398:     */           case '^': 
/* 2399:     */           case '`': 
/* 2400:     */           default: 
/* 2401:1611 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2402:     */           }
/* 2403:1615 */           mARG(false);
/* 2404:1617 */           switch (LA(1))
/* 2405:     */           {
/* 2406:     */           case '\t': 
/* 2407:     */           case '\n': 
/* 2408:     */           case '\r': 
/* 2409:     */           case ' ': 
/* 2410:1620 */             k = this.text.length();
/* 2411:1621 */             mWS(false);
/* 2412:1622 */             this.text.setLength(k);
/* 2413:1623 */             break;
/* 2414:     */           case ']': 
/* 2415:     */             break;
/* 2416:     */           default: 
/* 2417:1631 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2418:     */           }
/* 2419:1635 */           match(']');
/* 2420:     */         }
/* 2421:     */         else
/* 2422:     */         {
/* 2423:1638 */           if (m >= 1) {
/* 2424:     */             break;
/* 2425:     */           }
/* 2426:1638 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2427:     */         }
/* 2428:1641 */         m++;
/* 2429:     */       }
/* 2430:     */     case '.': 
/* 2431:1648 */       match('.');
/* 2432:1649 */       mID_ELEMENT(false);
/* 2433:1650 */       break;
/* 2434:     */     case ':': 
/* 2435:1654 */       match("::");
/* 2436:1655 */       mID_ELEMENT(false);
/* 2437:1656 */       break;
/* 2438:     */     default: 
/* 2439:1659 */       if ((LA(1) == '-') && (LA(2) == '>') && (_tokenSet_12.member(LA(3))))
/* 2440:     */       {
/* 2441:1660 */         match("->");
/* 2442:1661 */         mID_ELEMENT(false);
/* 2443:     */       }
/* 2444:1663 */       else if (_tokenSet_16.member(LA(1)))
/* 2445:     */       {
/* 2446:1665 */         bool = true;
/* 2447:1666 */         String str = this.generator.mapTreeId(localToken2.getText(), this.transInfo);
/* 2448:1668 */         if (str != null)
/* 2449:     */         {
/* 2450:1669 */           this.text.setLength(j);this.text.append(str);
/* 2451:     */         }
/* 2452:1673 */         if ((_tokenSet_17.member(LA(1))) && (_tokenSet_16.member(LA(2))) && (this.transInfo != null) && (this.transInfo.refRuleRoot != null))
/* 2453:     */         {
/* 2454:1675 */           switch (LA(1))
/* 2455:     */           {
/* 2456:     */           case '\t': 
/* 2457:     */           case '\n': 
/* 2458:     */           case '\r': 
/* 2459:     */           case ' ': 
/* 2460:1678 */             mWS(false);
/* 2461:1679 */             break;
/* 2462:     */           case '=': 
/* 2463:     */             break;
/* 2464:     */           default: 
/* 2465:1687 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2466:     */           }
/* 2467:1691 */           mVAR_ASSIGN(false);
/* 2468:     */         }
/* 2469:1693 */         else if (!_tokenSet_18.member(LA(1)))
/* 2470:     */         {
/* 2471:1696 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2472:     */         }
/* 2473:     */       }
/* 2474:     */       else
/* 2475:     */       {
/* 2476:1702 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2477:     */       }
/* 2478:     */       break;
/* 2479:     */     }
/* 2480:1706 */     if ((paramBoolean) && (localToken1 == null) && (i != -1))
/* 2481:     */     {
/* 2482:1707 */       localToken1 = makeToken(i);
/* 2483:1708 */       localToken1.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 2484:     */     }
/* 2485:1710 */     this._returnToken = localToken1;
/* 2486:1711 */     return bool;
/* 2487:     */   }
/* 2488:     */   
/* 2489:     */   protected final void mAST_CTOR_ELEMENT(boolean paramBoolean)
/* 2490:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 2491:     */   {
/* 2492:1718 */     Token localToken = null;int j = this.text.length();
/* 2493:1719 */     int i = 11;
/* 2494:1722 */     if ((LA(1) == '"') && (LA(2) >= '\003') && (LA(2) <= 'ÿ') && (LA(3) >= '\003') && (LA(3) <= 'ÿ')) {
/* 2495:1723 */       mSTRING(false);
/* 2496:1725 */     } else if ((_tokenSet_19.member(LA(1))) && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 2497:1726 */       mTREE_ELEMENT(false);
/* 2498:1728 */     } else if ((LA(1) >= '0') && (LA(1) <= '9')) {
/* 2499:1729 */       mINT(false);
/* 2500:     */     } else {
/* 2501:1732 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2502:     */     }
/* 2503:1735 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 2504:     */     {
/* 2505:1736 */       localToken = makeToken(i);
/* 2506:1737 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 2507:     */     }
/* 2508:1739 */     this._returnToken = localToken;
/* 2509:     */   }
/* 2510:     */   
/* 2511:     */   protected final void mINT(boolean paramBoolean)
/* 2512:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 2513:     */   {
/* 2514:1743 */     Token localToken = null;int j = this.text.length();
/* 2515:1744 */     int i = 26;
/* 2516:     */     
/* 2517:     */ 
/* 2518:     */ 
/* 2519:1748 */     int k = 0;
/* 2520:     */     for (;;)
/* 2521:     */     {
/* 2522:1751 */       if ((LA(1) >= '0') && (LA(1) <= '9'))
/* 2523:     */       {
/* 2524:1752 */         mDIGIT(false);
/* 2525:     */       }
/* 2526:     */       else
/* 2527:     */       {
/* 2528:1755 */         if (k >= 1) {
/* 2529:     */           break;
/* 2530:     */         }
/* 2531:1755 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2532:     */       }
/* 2533:1758 */       k++;
/* 2534:     */     }
/* 2535:1761 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 2536:     */     {
/* 2537:1762 */       localToken = makeToken(i);
/* 2538:1763 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 2539:     */     }
/* 2540:1765 */     this._returnToken = localToken;
/* 2541:     */   }
/* 2542:     */   
/* 2543:     */   protected final void mARG(boolean paramBoolean)
/* 2544:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 2545:     */   {
/* 2546:1769 */     Token localToken = null;int j = this.text.length();
/* 2547:1770 */     int i = 16;
/* 2548:1774 */     switch (LA(1))
/* 2549:     */     {
/* 2550:     */     case '\'': 
/* 2551:1777 */       mCHAR(false);
/* 2552:1778 */       break;
/* 2553:     */     case '0': 
/* 2554:     */     case '1': 
/* 2555:     */     case '2': 
/* 2556:     */     case '3': 
/* 2557:     */     case '4': 
/* 2558:     */     case '5': 
/* 2559:     */     case '6': 
/* 2560:     */     case '7': 
/* 2561:     */     case '8': 
/* 2562:     */     case '9': 
/* 2563:1784 */       mINT_OR_FLOAT(false);
/* 2564:1785 */       break;
/* 2565:     */     case '(': 
/* 2566:     */     case ')': 
/* 2567:     */     case '*': 
/* 2568:     */     case '+': 
/* 2569:     */     case ',': 
/* 2570:     */     case '-': 
/* 2571:     */     case '.': 
/* 2572:     */     case '/': 
/* 2573:     */     default: 
/* 2574:1788 */       if ((_tokenSet_19.member(LA(1))) && (LA(2) >= '\003') && (LA(2) <= 'ÿ') && (LA(3) >= '\003') && (LA(3) <= 'ÿ')) {
/* 2575:1789 */         mTREE_ELEMENT(false);
/* 2576:1791 */       } else if ((LA(1) == '"') && (LA(2) >= '\003') && (LA(2) <= 'ÿ') && (LA(3) >= '\003') && (LA(3) <= 'ÿ')) {
/* 2577:1792 */         mSTRING(false);
/* 2578:     */       } else {
/* 2579:1795 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2580:     */       }
/* 2581:     */       break;
/* 2582:     */     }
/* 2583:1802 */     while ((_tokenSet_20.member(LA(1))) && (_tokenSet_21.member(LA(2))) && (LA(3) >= '\003') && (LA(3) <= 'ÿ'))
/* 2584:     */     {
/* 2585:1804 */       switch (LA(1))
/* 2586:     */       {
/* 2587:     */       case '\t': 
/* 2588:     */       case '\n': 
/* 2589:     */       case '\r': 
/* 2590:     */       case ' ': 
/* 2591:1807 */         mWS(false);
/* 2592:1808 */         break;
/* 2593:     */       case '*': 
/* 2594:     */       case '+': 
/* 2595:     */       case '-': 
/* 2596:     */       case '/': 
/* 2597:     */         break;
/* 2598:     */       default: 
/* 2599:1816 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2600:     */       }
/* 2601:1821 */       switch (LA(1))
/* 2602:     */       {
/* 2603:     */       case '+': 
/* 2604:1824 */         match('+');
/* 2605:1825 */         break;
/* 2606:     */       case '-': 
/* 2607:1829 */         match('-');
/* 2608:1830 */         break;
/* 2609:     */       case '*': 
/* 2610:1834 */         match('*');
/* 2611:1835 */         break;
/* 2612:     */       case '/': 
/* 2613:1839 */         match('/');
/* 2614:1840 */         break;
/* 2615:     */       case ',': 
/* 2616:     */       case '.': 
/* 2617:     */       default: 
/* 2618:1844 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2619:     */       }
/* 2620:1849 */       switch (LA(1))
/* 2621:     */       {
/* 2622:     */       case '\t': 
/* 2623:     */       case '\n': 
/* 2624:     */       case '\r': 
/* 2625:     */       case ' ': 
/* 2626:1852 */         mWS(false);
/* 2627:1853 */         break;
/* 2628:     */       case '"': 
/* 2629:     */       case '#': 
/* 2630:     */       case '\'': 
/* 2631:     */       case '(': 
/* 2632:     */       case '0': 
/* 2633:     */       case '1': 
/* 2634:     */       case '2': 
/* 2635:     */       case '3': 
/* 2636:     */       case '4': 
/* 2637:     */       case '5': 
/* 2638:     */       case '6': 
/* 2639:     */       case '7': 
/* 2640:     */       case '8': 
/* 2641:     */       case '9': 
/* 2642:     */       case ':': 
/* 2643:     */       case 'A': 
/* 2644:     */       case 'B': 
/* 2645:     */       case 'C': 
/* 2646:     */       case 'D': 
/* 2647:     */       case 'E': 
/* 2648:     */       case 'F': 
/* 2649:     */       case 'G': 
/* 2650:     */       case 'H': 
/* 2651:     */       case 'I': 
/* 2652:     */       case 'J': 
/* 2653:     */       case 'K': 
/* 2654:     */       case 'L': 
/* 2655:     */       case 'M': 
/* 2656:     */       case 'N': 
/* 2657:     */       case 'O': 
/* 2658:     */       case 'P': 
/* 2659:     */       case 'Q': 
/* 2660:     */       case 'R': 
/* 2661:     */       case 'S': 
/* 2662:     */       case 'T': 
/* 2663:     */       case 'U': 
/* 2664:     */       case 'V': 
/* 2665:     */       case 'W': 
/* 2666:     */       case 'X': 
/* 2667:     */       case 'Y': 
/* 2668:     */       case 'Z': 
/* 2669:     */       case '[': 
/* 2670:     */       case '_': 
/* 2671:     */       case 'a': 
/* 2672:     */       case 'b': 
/* 2673:     */       case 'c': 
/* 2674:     */       case 'd': 
/* 2675:     */       case 'e': 
/* 2676:     */       case 'f': 
/* 2677:     */       case 'g': 
/* 2678:     */       case 'h': 
/* 2679:     */       case 'i': 
/* 2680:     */       case 'j': 
/* 2681:     */       case 'k': 
/* 2682:     */       case 'l': 
/* 2683:     */       case 'm': 
/* 2684:     */       case 'n': 
/* 2685:     */       case 'o': 
/* 2686:     */       case 'p': 
/* 2687:     */       case 'q': 
/* 2688:     */       case 'r': 
/* 2689:     */       case 's': 
/* 2690:     */       case 't': 
/* 2691:     */       case 'u': 
/* 2692:     */       case 'v': 
/* 2693:     */       case 'w': 
/* 2694:     */       case 'x': 
/* 2695:     */       case 'y': 
/* 2696:     */       case 'z': 
/* 2697:     */         break;
/* 2698:     */       case '\013': 
/* 2699:     */       case '\f': 
/* 2700:     */       case '\016': 
/* 2701:     */       case '\017': 
/* 2702:     */       case '\020': 
/* 2703:     */       case '\021': 
/* 2704:     */       case '\022': 
/* 2705:     */       case '\023': 
/* 2706:     */       case '\024': 
/* 2707:     */       case '\025': 
/* 2708:     */       case '\026': 
/* 2709:     */       case '\027': 
/* 2710:     */       case '\030': 
/* 2711:     */       case '\031': 
/* 2712:     */       case '\032': 
/* 2713:     */       case '\033': 
/* 2714:     */       case '\034': 
/* 2715:     */       case '\035': 
/* 2716:     */       case '\036': 
/* 2717:     */       case '\037': 
/* 2718:     */       case '!': 
/* 2719:     */       case '$': 
/* 2720:     */       case '%': 
/* 2721:     */       case '&': 
/* 2722:     */       case ')': 
/* 2723:     */       case '*': 
/* 2724:     */       case '+': 
/* 2725:     */       case ',': 
/* 2726:     */       case '-': 
/* 2727:     */       case '.': 
/* 2728:     */       case '/': 
/* 2729:     */       case ';': 
/* 2730:     */       case '<': 
/* 2731:     */       case '=': 
/* 2732:     */       case '>': 
/* 2733:     */       case '?': 
/* 2734:     */       case '@': 
/* 2735:     */       case '\\': 
/* 2736:     */       case ']': 
/* 2737:     */       case '^': 
/* 2738:     */       case '`': 
/* 2739:     */       default: 
/* 2740:1878 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2741:     */       }
/* 2742:1882 */       mARG(false);
/* 2743:     */     }
/* 2744:1890 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 2745:     */     {
/* 2746:1891 */       localToken = makeToken(i);
/* 2747:1892 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 2748:     */     }
/* 2749:1894 */     this._returnToken = localToken;
/* 2750:     */   }
/* 2751:     */   
/* 2752:     */   protected final void mTEXT_ARG_ELEMENT(boolean paramBoolean)
/* 2753:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 2754:     */   {
/* 2755:1898 */     Token localToken = null;int j = this.text.length();
/* 2756:1899 */     int i = 14;
/* 2757:1902 */     switch (LA(1))
/* 2758:     */     {
/* 2759:     */     case ':': 
/* 2760:     */     case 'A': 
/* 2761:     */     case 'B': 
/* 2762:     */     case 'C': 
/* 2763:     */     case 'D': 
/* 2764:     */     case 'E': 
/* 2765:     */     case 'F': 
/* 2766:     */     case 'G': 
/* 2767:     */     case 'H': 
/* 2768:     */     case 'I': 
/* 2769:     */     case 'J': 
/* 2770:     */     case 'K': 
/* 2771:     */     case 'L': 
/* 2772:     */     case 'M': 
/* 2773:     */     case 'N': 
/* 2774:     */     case 'O': 
/* 2775:     */     case 'P': 
/* 2776:     */     case 'Q': 
/* 2777:     */     case 'R': 
/* 2778:     */     case 'S': 
/* 2779:     */     case 'T': 
/* 2780:     */     case 'U': 
/* 2781:     */     case 'V': 
/* 2782:     */     case 'W': 
/* 2783:     */     case 'X': 
/* 2784:     */     case 'Y': 
/* 2785:     */     case 'Z': 
/* 2786:     */     case '_': 
/* 2787:     */     case 'a': 
/* 2788:     */     case 'b': 
/* 2789:     */     case 'c': 
/* 2790:     */     case 'd': 
/* 2791:     */     case 'e': 
/* 2792:     */     case 'f': 
/* 2793:     */     case 'g': 
/* 2794:     */     case 'h': 
/* 2795:     */     case 'i': 
/* 2796:     */     case 'j': 
/* 2797:     */     case 'k': 
/* 2798:     */     case 'l': 
/* 2799:     */     case 'm': 
/* 2800:     */     case 'n': 
/* 2801:     */     case 'o': 
/* 2802:     */     case 'p': 
/* 2803:     */     case 'q': 
/* 2804:     */     case 'r': 
/* 2805:     */     case 's': 
/* 2806:     */     case 't': 
/* 2807:     */     case 'u': 
/* 2808:     */     case 'v': 
/* 2809:     */     case 'w': 
/* 2810:     */     case 'x': 
/* 2811:     */     case 'y': 
/* 2812:     */     case 'z': 
/* 2813:1918 */       mTEXT_ARG_ID_ELEMENT(false);
/* 2814:1919 */       break;
/* 2815:     */     case '"': 
/* 2816:1923 */       mSTRING(false);
/* 2817:1924 */       break;
/* 2818:     */     case '\'': 
/* 2819:1928 */       mCHAR(false);
/* 2820:1929 */       break;
/* 2821:     */     case '0': 
/* 2822:     */     case '1': 
/* 2823:     */     case '2': 
/* 2824:     */     case '3': 
/* 2825:     */     case '4': 
/* 2826:     */     case '5': 
/* 2827:     */     case '6': 
/* 2828:     */     case '7': 
/* 2829:     */     case '8': 
/* 2830:     */     case '9': 
/* 2831:1935 */       mINT_OR_FLOAT(false);
/* 2832:1936 */       break;
/* 2833:     */     case '$': 
/* 2834:1940 */       mTEXT_ITEM(false);
/* 2835:1941 */       break;
/* 2836:     */     case '+': 
/* 2837:1945 */       match('+');
/* 2838:1946 */       break;
/* 2839:     */     case '#': 
/* 2840:     */     case '%': 
/* 2841:     */     case '&': 
/* 2842:     */     case '(': 
/* 2843:     */     case ')': 
/* 2844:     */     case '*': 
/* 2845:     */     case ',': 
/* 2846:     */     case '-': 
/* 2847:     */     case '.': 
/* 2848:     */     case '/': 
/* 2849:     */     case ';': 
/* 2850:     */     case '<': 
/* 2851:     */     case '=': 
/* 2852:     */     case '>': 
/* 2853:     */     case '?': 
/* 2854:     */     case '@': 
/* 2855:     */     case '[': 
/* 2856:     */     case '\\': 
/* 2857:     */     case ']': 
/* 2858:     */     case '^': 
/* 2859:     */     case '`': 
/* 2860:     */     default: 
/* 2861:1950 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2862:     */     }
/* 2863:1953 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 2864:     */     {
/* 2865:1954 */       localToken = makeToken(i);
/* 2866:1955 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 2867:     */     }
/* 2868:1957 */     this._returnToken = localToken;
/* 2869:     */   }
/* 2870:     */   
/* 2871:     */   protected final void mTEXT_ARG_ID_ELEMENT(boolean paramBoolean)
/* 2872:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 2873:     */   {
/* 2874:1961 */     Token localToken1 = null;int j = this.text.length();
/* 2875:1962 */     int i = 15;
/* 2876:     */     
/* 2877:1964 */     Token localToken2 = null;
/* 2878:     */     
/* 2879:1966 */     mID(true);
/* 2880:1967 */     localToken2 = this._returnToken;
/* 2881:     */     int k;
/* 2882:1969 */     if ((_tokenSet_4.member(LA(1))) && (_tokenSet_22.member(LA(2))))
/* 2883:     */     {
/* 2884:1970 */       k = this.text.length();
/* 2885:1971 */       mWS(false);
/* 2886:1972 */       this.text.setLength(k);
/* 2887:     */     }
/* 2888:1974 */     else if (!_tokenSet_22.member(LA(1)))
/* 2889:     */     {
/* 2890:1977 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2891:     */     }
/* 2892:1982 */     switch (LA(1))
/* 2893:     */     {
/* 2894:     */     case '(': 
/* 2895:1985 */       match('(');
/* 2896:1987 */       if ((_tokenSet_4.member(LA(1))) && (_tokenSet_23.member(LA(2))) && (LA(3) >= '\003') && (LA(3) <= 'ÿ'))
/* 2897:     */       {
/* 2898:1988 */         k = this.text.length();
/* 2899:1989 */         mWS(false);
/* 2900:1990 */         this.text.setLength(k);
/* 2901:     */       }
/* 2902:1992 */       else if ((!_tokenSet_23.member(LA(1))) || (LA(2) < '\003') || (LA(2) > 'ÿ'))
/* 2903:     */       {
/* 2904:1995 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2905:     */       }
/* 2906:2002 */       if ((_tokenSet_24.member(LA(1))) && (LA(2) >= '\003') && (LA(2) <= 'ÿ') && (LA(3) >= '\003') && (LA(3) <= 'ÿ'))
/* 2907:     */       {
/* 2908:2003 */         mTEXT_ARG(false);
/* 2909:2007 */         while (LA(1) == ',')
/* 2910:     */         {
/* 2911:2008 */           match(',');
/* 2912:2009 */           mTEXT_ARG(false);
/* 2913:     */         }
/* 2914:     */       }
/* 2915:2025 */       switch (LA(1))
/* 2916:     */       {
/* 2917:     */       case '\t': 
/* 2918:     */       case '\n': 
/* 2919:     */       case '\r': 
/* 2920:     */       case ' ': 
/* 2921:2028 */         k = this.text.length();
/* 2922:2029 */         mWS(false);
/* 2923:2030 */         this.text.setLength(k);
/* 2924:2031 */         break;
/* 2925:     */       case ')': 
/* 2926:     */         break;
/* 2927:     */       default: 
/* 2928:2039 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2929:     */       }
/* 2930:2043 */       match(')');
/* 2931:2044 */       break;
/* 2932:     */     case '[': 
/* 2933:2049 */       int m = 0;
/* 2934:     */       for (;;)
/* 2935:     */       {
/* 2936:2052 */         if (LA(1) == '[')
/* 2937:     */         {
/* 2938:2053 */           match('[');
/* 2939:2055 */           if ((_tokenSet_4.member(LA(1))) && (_tokenSet_24.member(LA(2))) && (LA(3) >= '\003') && (LA(3) <= 'ÿ'))
/* 2940:     */           {
/* 2941:2056 */             k = this.text.length();
/* 2942:2057 */             mWS(false);
/* 2943:2058 */             this.text.setLength(k);
/* 2944:     */           }
/* 2945:2060 */           else if ((!_tokenSet_24.member(LA(1))) || (LA(2) < '\003') || (LA(2) > 'ÿ') || (LA(3) < '\003') || (LA(3) > 'ÿ'))
/* 2946:     */           {
/* 2947:2063 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2948:     */           }
/* 2949:2067 */           mTEXT_ARG(false);
/* 2950:2069 */           switch (LA(1))
/* 2951:     */           {
/* 2952:     */           case '\t': 
/* 2953:     */           case '\n': 
/* 2954:     */           case '\r': 
/* 2955:     */           case ' ': 
/* 2956:2072 */             k = this.text.length();
/* 2957:2073 */             mWS(false);
/* 2958:2074 */             this.text.setLength(k);
/* 2959:2075 */             break;
/* 2960:     */           case ']': 
/* 2961:     */             break;
/* 2962:     */           default: 
/* 2963:2083 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2964:     */           }
/* 2965:2087 */           match(']');
/* 2966:     */         }
/* 2967:     */         else
/* 2968:     */         {
/* 2969:2090 */           if (m >= 1) {
/* 2970:     */             break;
/* 2971:     */           }
/* 2972:2090 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2973:     */         }
/* 2974:2093 */         m++;
/* 2975:     */       }
/* 2976:     */     case '.': 
/* 2977:2100 */       match('.');
/* 2978:2101 */       mTEXT_ARG_ID_ELEMENT(false);
/* 2979:2102 */       break;
/* 2980:     */     case '-': 
/* 2981:2106 */       match("->");
/* 2982:2107 */       mTEXT_ARG_ID_ELEMENT(false);
/* 2983:2108 */       break;
/* 2984:     */     default: 
/* 2985:2111 */       if ((LA(1) == ':') && (LA(2) == ':') && (_tokenSet_12.member(LA(3))))
/* 2986:     */       {
/* 2987:2112 */         match("::");
/* 2988:2113 */         mTEXT_ARG_ID_ELEMENT(false);
/* 2989:     */       }
/* 2990:2115 */       else if (!_tokenSet_11.member(LA(1)))
/* 2991:     */       {
/* 2992:2118 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2993:     */       }
/* 2994:     */       break;
/* 2995:     */     }
/* 2996:2122 */     if ((paramBoolean) && (localToken1 == null) && (i != -1))
/* 2997:     */     {
/* 2998:2123 */       localToken1 = makeToken(i);
/* 2999:2124 */       localToken1.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 3000:     */     }
/* 3001:2126 */     this._returnToken = localToken1;
/* 3002:     */   }
/* 3003:     */   
/* 3004:     */   protected final void mINT_OR_FLOAT(boolean paramBoolean)
/* 3005:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 3006:     */   {
/* 3007:2130 */     Token localToken = null;int j = this.text.length();
/* 3008:2131 */     int i = 27;
/* 3009:     */     
/* 3010:     */ 
/* 3011:     */ 
/* 3012:2135 */     int k = 0;
/* 3013:     */     for (;;)
/* 3014:     */     {
/* 3015:2138 */       if ((LA(1) >= '0') && (LA(1) <= '9') && (_tokenSet_25.member(LA(2))))
/* 3016:     */       {
/* 3017:2139 */         mDIGIT(false);
/* 3018:     */       }
/* 3019:     */       else
/* 3020:     */       {
/* 3021:2142 */         if (k >= 1) {
/* 3022:     */           break;
/* 3023:     */         }
/* 3024:2142 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 3025:     */       }
/* 3026:2145 */       k++;
/* 3027:     */     }
/* 3028:2149 */     if ((LA(1) == 'L') && (_tokenSet_26.member(LA(2))))
/* 3029:     */     {
/* 3030:2150 */       match('L');
/* 3031:     */     }
/* 3032:2152 */     else if ((LA(1) == 'l') && (_tokenSet_26.member(LA(2))))
/* 3033:     */     {
/* 3034:2153 */       match('l');
/* 3035:     */     }
/* 3036:     */     else
/* 3037:     */     {
/* 3038:2155 */       if (LA(1) == '.')
/* 3039:     */       {
/* 3040:2156 */         match('.');
/* 3041:2160 */         while ((LA(1) >= '0') && (LA(1) <= '9') && (_tokenSet_26.member(LA(2)))) {
/* 3042:2161 */           mDIGIT(false);
/* 3043:     */         }
/* 3044:     */       }
/* 3045:2170 */       if (!_tokenSet_26.member(LA(1))) {
/* 3046:2173 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 3047:     */       }
/* 3048:     */     }
/* 3049:2177 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 3050:     */     {
/* 3051:2178 */       localToken = makeToken(i);
/* 3052:2179 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 3053:     */     }
/* 3054:2181 */     this._returnToken = localToken;
/* 3055:     */   }
/* 3056:     */   
/* 3057:     */   protected final void mSL_COMMENT(boolean paramBoolean)
/* 3058:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 3059:     */   {
/* 3060:2185 */     Token localToken = null;int j = this.text.length();
/* 3061:2186 */     int i = 20;
/* 3062:     */     
/* 3063:     */ 
/* 3064:2189 */     match("//");
/* 3065:2194 */     while ((LA(1) != '\n') && (LA(1) != '\r') && 
/* 3066:2195 */       (LA(1) >= '\003') && (LA(1) <= 'ÿ') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 3067:2196 */       matchNot(65535);
/* 3068:     */     }
/* 3069:2205 */     if ((LA(1) == '\r') && (LA(2) == '\n')) {
/* 3070:2206 */       match("\r\n");
/* 3071:2208 */     } else if (LA(1) == '\n') {
/* 3072:2209 */       match('\n');
/* 3073:2211 */     } else if (LA(1) == '\r') {
/* 3074:2212 */       match('\r');
/* 3075:     */     } else {
/* 3076:2215 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 3077:     */     }
/* 3078:2219 */     newline();
/* 3079:2220 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 3080:     */     {
/* 3081:2221 */       localToken = makeToken(i);
/* 3082:2222 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 3083:     */     }
/* 3084:2224 */     this._returnToken = localToken;
/* 3085:     */   }
/* 3086:     */   
/* 3087:     */   protected final void mML_COMMENT(boolean paramBoolean)
/* 3088:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 3089:     */   {
/* 3090:2228 */     Token localToken = null;int j = this.text.length();
/* 3091:2229 */     int i = 21;
/* 3092:     */     
/* 3093:     */ 
/* 3094:2232 */     match("/*");
/* 3095:2237 */     while ((LA(1) != '*') || (LA(2) != '/')) {
/* 3096:2238 */       if ((LA(1) == '\r') && (LA(2) == '\n') && (LA(3) >= '\003') && (LA(3) <= 'ÿ'))
/* 3097:     */       {
/* 3098:2239 */         match('\r');
/* 3099:2240 */         match('\n');
/* 3100:2241 */         newline();
/* 3101:     */       }
/* 3102:2243 */       else if ((LA(1) == '\r') && (LA(2) >= '\003') && (LA(2) <= 'ÿ') && (LA(3) >= '\003') && (LA(3) <= 'ÿ'))
/* 3103:     */       {
/* 3104:2244 */         match('\r');
/* 3105:2245 */         newline();
/* 3106:     */       }
/* 3107:2247 */       else if ((LA(1) == '\n') && (LA(2) >= '\003') && (LA(2) <= 'ÿ') && (LA(3) >= '\003') && (LA(3) <= 'ÿ'))
/* 3108:     */       {
/* 3109:2248 */         match('\n');
/* 3110:2249 */         newline();
/* 3111:     */       }
/* 3112:     */       else
/* 3113:     */       {
/* 3114:2251 */         if ((LA(1) < '\003') || (LA(1) > 'ÿ') || (LA(2) < '\003') || (LA(2) > 'ÿ') || (LA(3) < '\003') || (LA(3) > 'ÿ')) {
/* 3115:     */           break;
/* 3116:     */         }
/* 3117:2252 */         matchNot(65535);
/* 3118:     */       }
/* 3119:     */     }
/* 3120:2260 */     match("*/");
/* 3121:2261 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 3122:     */     {
/* 3123:2262 */       localToken = makeToken(i);
/* 3124:2263 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 3125:     */     }
/* 3126:2265 */     this._returnToken = localToken;
/* 3127:     */   }
/* 3128:     */   
/* 3129:     */   protected final void mESC(boolean paramBoolean)
/* 3130:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 3131:     */   {
/* 3132:2269 */     Token localToken = null;int j = this.text.length();
/* 3133:2270 */     int i = 24;
/* 3134:     */     
/* 3135:     */ 
/* 3136:2273 */     match('\\');
/* 3137:2275 */     switch (LA(1))
/* 3138:     */     {
/* 3139:     */     case 'n': 
/* 3140:2278 */       match('n');
/* 3141:2279 */       break;
/* 3142:     */     case 'r': 
/* 3143:2283 */       match('r');
/* 3144:2284 */       break;
/* 3145:     */     case 't': 
/* 3146:2288 */       match('t');
/* 3147:2289 */       break;
/* 3148:     */     case 'v': 
/* 3149:2293 */       match('v');
/* 3150:2294 */       break;
/* 3151:     */     case 'b': 
/* 3152:2298 */       match('b');
/* 3153:2299 */       break;
/* 3154:     */     case 'f': 
/* 3155:2303 */       match('f');
/* 3156:2304 */       break;
/* 3157:     */     case '"': 
/* 3158:2308 */       match('"');
/* 3159:2309 */       break;
/* 3160:     */     case '\'': 
/* 3161:2313 */       match('\'');
/* 3162:2314 */       break;
/* 3163:     */     case '\\': 
/* 3164:2318 */       match('\\');
/* 3165:2319 */       break;
/* 3166:     */     case '0': 
/* 3167:     */     case '1': 
/* 3168:     */     case '2': 
/* 3169:     */     case '3': 
/* 3170:2324 */       matchRange('0', '3');
/* 3171:2327 */       if ((LA(1) >= '0') && (LA(1) <= '9') && (LA(2) >= '\003') && (LA(2) <= 'ÿ'))
/* 3172:     */       {
/* 3173:2328 */         mDIGIT(false);
/* 3174:2330 */         if ((LA(1) >= '0') && (LA(1) <= '9') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 3175:2331 */           mDIGIT(false);
/* 3176:2333 */         } else if ((LA(1) < '\003') || (LA(1) > 'ÿ')) {
/* 3177:2336 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 3178:     */         }
/* 3179:     */       }
/* 3180:2341 */       else if ((LA(1) < '\003') || (LA(1) > 'ÿ'))
/* 3181:     */       {
/* 3182:2344 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 3183:     */       }
/* 3184:     */       break;
/* 3185:     */     case '4': 
/* 3186:     */     case '5': 
/* 3187:     */     case '6': 
/* 3188:     */     case '7': 
/* 3189:2353 */       matchRange('4', '7');
/* 3190:2356 */       if ((LA(1) >= '0') && (LA(1) <= '9') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 3191:2357 */         mDIGIT(false);
/* 3192:2359 */       } else if ((LA(1) < '\003') || (LA(1) > 'ÿ')) {
/* 3193:2362 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 3194:     */       }
/* 3195:     */       break;
/* 3196:     */     default: 
/* 3197:2370 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 3198:     */     }
/* 3199:2374 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 3200:     */     {
/* 3201:2375 */       localToken = makeToken(i);
/* 3202:2376 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 3203:     */     }
/* 3204:2378 */     this._returnToken = localToken;
/* 3205:     */   }
/* 3206:     */   
/* 3207:     */   protected final void mDIGIT(boolean paramBoolean)
/* 3208:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 3209:     */   {
/* 3210:2382 */     Token localToken = null;int j = this.text.length();
/* 3211:2383 */     int i = 25;
/* 3212:     */     
/* 3213:     */ 
/* 3214:2386 */     matchRange('0', '9');
/* 3215:2387 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 3216:     */     {
/* 3217:2388 */       localToken = makeToken(i);
/* 3218:2389 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 3219:     */     }
/* 3220:2391 */     this._returnToken = localToken;
/* 3221:     */   }
/* 3222:     */   
/* 3223:     */   private static final long[] mk_tokenSet_0()
/* 3224:     */   {
/* 3225:2396 */     long[] arrayOfLong = new long[8];
/* 3226:2397 */     arrayOfLong[0] = -103079215112L;
/* 3227:2398 */     for (int i = 1; i <= 3; i++) {
/* 3228:2398 */       arrayOfLong[i] = -1L;
/* 3229:     */     }
/* 3230:2399 */     return arrayOfLong;
/* 3231:     */   }
/* 3232:     */   
/* 3233:2401 */   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
/* 3234:     */   
/* 3235:     */   private static final long[] mk_tokenSet_1()
/* 3236:     */   {
/* 3237:2403 */     long[] arrayOfLong = new long[8];
/* 3238:2404 */     arrayOfLong[0] = -145135534866440L;
/* 3239:2405 */     for (int i = 1; i <= 3; i++) {
/* 3240:2405 */       arrayOfLong[i] = -1L;
/* 3241:     */     }
/* 3242:2406 */     return arrayOfLong;
/* 3243:     */   }
/* 3244:     */   
/* 3245:2408 */   public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
/* 3246:     */   
/* 3247:     */   private static final long[] mk_tokenSet_2()
/* 3248:     */   {
/* 3249:2410 */     long[] arrayOfLong = new long[8];
/* 3250:2411 */     arrayOfLong[0] = -141407503262728L;
/* 3251:2412 */     for (int i = 1; i <= 3; i++) {
/* 3252:2412 */       arrayOfLong[i] = -1L;
/* 3253:     */     }
/* 3254:2413 */     return arrayOfLong;
/* 3255:     */   }
/* 3256:     */   
/* 3257:2415 */   public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
/* 3258:     */   
/* 3259:     */   private static final long[] mk_tokenSet_3()
/* 3260:     */   {
/* 3261:2417 */     long[] arrayOfLong = { 288230380446688768L, 576460745995190270L, 0L, 0L, 0L };
/* 3262:2418 */     return arrayOfLong;
/* 3263:     */   }
/* 3264:     */   
/* 3265:2420 */   public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
/* 3266:     */   
/* 3267:     */   private static final long[] mk_tokenSet_4()
/* 3268:     */   {
/* 3269:2422 */     long[] arrayOfLong = { 4294977024L, 0L, 0L, 0L, 0L };
/* 3270:2423 */     return arrayOfLong;
/* 3271:     */   }
/* 3272:     */   
/* 3273:2425 */   public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
/* 3274:     */   
/* 3275:     */   private static final long[] mk_tokenSet_5()
/* 3276:     */   {
/* 3277:2427 */     long[] arrayOfLong = { 1103806604800L, 0L, 0L, 0L, 0L };
/* 3278:2428 */     return arrayOfLong;
/* 3279:     */   }
/* 3280:     */   
/* 3281:2430 */   public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
/* 3282:     */   
/* 3283:     */   private static final long[] mk_tokenSet_6()
/* 3284:     */   {
/* 3285:2432 */     long[] arrayOfLong = { 576189812881499648L, 576460745995190270L, 0L, 0L, 0L };
/* 3286:2433 */     return arrayOfLong;
/* 3287:     */   }
/* 3288:     */   
/* 3289:2435 */   public static final BitSet _tokenSet_6 = new BitSet(mk_tokenSet_6());
/* 3290:     */   
/* 3291:     */   private static final long[] mk_tokenSet_7()
/* 3292:     */   {
/* 3293:2437 */     long[] arrayOfLong = new long[8];
/* 3294:2438 */     arrayOfLong[0] = -17179869192L;
/* 3295:2439 */     arrayOfLong[1] = -268435457L;
/* 3296:2440 */     for (int i = 2; i <= 3; i++) {
/* 3297:2440 */       arrayOfLong[i] = -1L;
/* 3298:     */     }
/* 3299:2441 */     return arrayOfLong;
/* 3300:     */   }
/* 3301:     */   
/* 3302:2443 */   public static final BitSet _tokenSet_7 = new BitSet(mk_tokenSet_7());
/* 3303:     */   
/* 3304:     */   private static final long[] mk_tokenSet_8()
/* 3305:     */   {
/* 3306:2445 */     long[] arrayOfLong = new long[8];
/* 3307:2446 */     arrayOfLong[0] = -549755813896L;
/* 3308:2447 */     arrayOfLong[1] = -268435457L;
/* 3309:2448 */     for (int i = 2; i <= 3; i++) {
/* 3310:2448 */       arrayOfLong[i] = -1L;
/* 3311:     */     }
/* 3312:2449 */     return arrayOfLong;
/* 3313:     */   }
/* 3314:     */   
/* 3315:2451 */   public static final BitSet _tokenSet_8 = new BitSet(mk_tokenSet_8());
/* 3316:     */   
/* 3317:     */   private static final long[] mk_tokenSet_9()
/* 3318:     */   {
/* 3319:2453 */     long[] arrayOfLong = { 576179277326712832L, 576460745995190270L, 0L, 0L, 0L };
/* 3320:2454 */     return arrayOfLong;
/* 3321:     */   }
/* 3322:     */   
/* 3323:2456 */   public static final BitSet _tokenSet_9 = new BitSet(mk_tokenSet_9());
/* 3324:     */   
/* 3325:     */   private static final long[] mk_tokenSet_10()
/* 3326:     */   {
/* 3327:2458 */     long[] arrayOfLong = { 576188709074894848L, 576460745995190270L, 0L, 0L, 0L };
/* 3328:2459 */     return arrayOfLong;
/* 3329:     */   }
/* 3330:     */   
/* 3331:2461 */   public static final BitSet _tokenSet_10 = new BitSet(mk_tokenSet_10());
/* 3332:     */   
/* 3333:     */   private static final long[] mk_tokenSet_11()
/* 3334:     */   {
/* 3335:2463 */     long[] arrayOfLong = { 576208504579171840L, 576460746532061182L, 0L, 0L, 0L };
/* 3336:2464 */     return arrayOfLong;
/* 3337:     */   }
/* 3338:     */   
/* 3339:2466 */   public static final BitSet _tokenSet_11 = new BitSet(mk_tokenSet_11());
/* 3340:     */   
/* 3341:     */   private static final long[] mk_tokenSet_12()
/* 3342:     */   {
/* 3343:2468 */     long[] arrayOfLong = { 288230376151711744L, 576460745995190270L, 0L, 0L, 0L };
/* 3344:2469 */     return arrayOfLong;
/* 3345:     */   }
/* 3346:     */   
/* 3347:2471 */   public static final BitSet _tokenSet_12 = new BitSet(mk_tokenSet_12());
/* 3348:     */   
/* 3349:     */   private static final long[] mk_tokenSet_13()
/* 3350:     */   {
/* 3351:2473 */     long[] arrayOfLong = { 3747275269732312576L, 671088640L, 0L, 0L, 0L };
/* 3352:2474 */     return arrayOfLong;
/* 3353:     */   }
/* 3354:     */   
/* 3355:2476 */   public static final BitSet _tokenSet_13 = new BitSet(mk_tokenSet_13());
/* 3356:     */   
/* 3357:     */   private static final long[] mk_tokenSet_14()
/* 3358:     */   {
/* 3359:2478 */     long[] arrayOfLong = new long[8];
/* 3360:2479 */     arrayOfLong[0] = -4611686018427387912L;
/* 3361:2480 */     for (int i = 1; i <= 3; i++) {
/* 3362:2480 */       arrayOfLong[i] = -1L;
/* 3363:     */     }
/* 3364:2481 */     return arrayOfLong;
/* 3365:     */   }
/* 3366:     */   
/* 3367:2483 */   public static final BitSet _tokenSet_14 = new BitSet(mk_tokenSet_14());
/* 3368:     */   
/* 3369:     */   private static final long[] mk_tokenSet_15()
/* 3370:     */   {
/* 3371:2485 */     long[] arrayOfLong = { 576183181451994624L, 576460746129407998L, 0L, 0L, 0L };
/* 3372:2486 */     return arrayOfLong;
/* 3373:     */   }
/* 3374:     */   
/* 3375:2488 */   public static final BitSet _tokenSet_15 = new BitSet(mk_tokenSet_15());
/* 3376:     */   
/* 3377:     */   private static final long[] mk_tokenSet_16()
/* 3378:     */   {
/* 3379:2490 */     long[] arrayOfLong = { 2306051920717948416L, 536870912L, 0L, 0L, 0L };
/* 3380:2491 */     return arrayOfLong;
/* 3381:     */   }
/* 3382:     */   
/* 3383:2493 */   public static final BitSet _tokenSet_16 = new BitSet(mk_tokenSet_16());
/* 3384:     */   
/* 3385:     */   private static final long[] mk_tokenSet_17()
/* 3386:     */   {
/* 3387:2495 */     long[] arrayOfLong = { 2305843013508670976L, 0L, 0L, 0L, 0L };
/* 3388:2496 */     return arrayOfLong;
/* 3389:     */   }
/* 3390:     */   
/* 3391:2498 */   public static final BitSet _tokenSet_17 = new BitSet(mk_tokenSet_17());
/* 3392:     */   
/* 3393:     */   private static final long[] mk_tokenSet_18()
/* 3394:     */   {
/* 3395:2500 */     long[] arrayOfLong = { 208911504254464L, 536870912L, 0L, 0L, 0L };
/* 3396:2501 */     return arrayOfLong;
/* 3397:     */   }
/* 3398:     */   
/* 3399:2503 */   public static final BitSet _tokenSet_18 = new BitSet(mk_tokenSet_18());
/* 3400:     */   
/* 3401:     */   private static final long[] mk_tokenSet_19()
/* 3402:     */   {
/* 3403:2505 */     long[] arrayOfLong = { 288231527202947072L, 576460746129407998L, 0L, 0L, 0L };
/* 3404:2506 */     return arrayOfLong;
/* 3405:     */   }
/* 3406:     */   
/* 3407:2508 */   public static final BitSet _tokenSet_19 = new BitSet(mk_tokenSet_19());
/* 3408:     */   
/* 3409:     */   private static final long[] mk_tokenSet_20()
/* 3410:     */   {
/* 3411:2510 */     long[] arrayOfLong = { 189120294954496L, 0L, 0L, 0L, 0L };
/* 3412:2511 */     return arrayOfLong;
/* 3413:     */   }
/* 3414:     */   
/* 3415:2513 */   public static final BitSet _tokenSet_20 = new BitSet(mk_tokenSet_20());
/* 3416:     */   
/* 3417:     */   private static final long[] mk_tokenSet_21()
/* 3418:     */   {
/* 3419:2515 */     long[] arrayOfLong = { 576370098428716544L, 576460746129407998L, 0L, 0L, 0L };
/* 3420:2516 */     return arrayOfLong;
/* 3421:     */   }
/* 3422:     */   
/* 3423:2518 */   public static final BitSet _tokenSet_21 = new BitSet(mk_tokenSet_21());
/* 3424:     */   
/* 3425:     */   private static final long[] mk_tokenSet_22()
/* 3426:     */   {
/* 3427:2520 */     long[] arrayOfLong = { 576315157207066112L, 576460746666278910L, 0L, 0L, 0L };
/* 3428:2521 */     return arrayOfLong;
/* 3429:     */   }
/* 3430:     */   
/* 3431:2523 */   public static final BitSet _tokenSet_22 = new BitSet(mk_tokenSet_22());
/* 3432:     */   
/* 3433:     */   private static final long[] mk_tokenSet_23()
/* 3434:     */   {
/* 3435:2525 */     long[] arrayOfLong = { 576190912393127424L, 576460745995190270L, 0L, 0L, 0L };
/* 3436:2526 */     return arrayOfLong;
/* 3437:     */   }
/* 3438:     */   
/* 3439:2528 */   public static final BitSet _tokenSet_23 = new BitSet(mk_tokenSet_23());
/* 3440:     */   
/* 3441:     */   private static final long[] mk_tokenSet_24()
/* 3442:     */   {
/* 3443:2530 */     long[] arrayOfLong = { 576188713369871872L, 576460745995190270L, 0L, 0L, 0L };
/* 3444:2531 */     return arrayOfLong;
/* 3445:     */   }
/* 3446:     */   
/* 3447:2533 */   public static final BitSet _tokenSet_24 = new BitSet(mk_tokenSet_24());
/* 3448:     */   
/* 3449:     */   private static final long[] mk_tokenSet_25()
/* 3450:     */   {
/* 3451:2535 */     long[] arrayOfLong = { 576459193230304768L, 576460746532061182L, 0L, 0L, 0L };
/* 3452:2536 */     return arrayOfLong;
/* 3453:     */   }
/* 3454:     */   
/* 3455:2538 */   public static final BitSet _tokenSet_25 = new BitSet(mk_tokenSet_25());
/* 3456:     */   
/* 3457:     */   private static final long[] mk_tokenSet_26()
/* 3458:     */   {
/* 3459:2540 */     long[] arrayOfLong = { 576388824486127104L, 576460746532061182L, 0L, 0L, 0L };
/* 3460:2541 */     return arrayOfLong;
/* 3461:     */   }
/* 3462:     */   
/* 3463:2543 */   public static final BitSet _tokenSet_26 = new BitSet(mk_tokenSet_26());
/* 3464:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.actions.cpp.ActionLexer
 * JD-Core Version:    0.7.0.1
 */