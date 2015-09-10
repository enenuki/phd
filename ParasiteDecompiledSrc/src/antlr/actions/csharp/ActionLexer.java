/*    1:     */ package antlr.actions.csharp;
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
/*  284:     */         case 'A': 
/*  285:     */         case 'B': 
/*  286:     */         case 'C': 
/*  287:     */         case 'D': 
/*  288:     */         case 'E': 
/*  289:     */         case 'F': 
/*  290:     */         case 'G': 
/*  291:     */         case 'H': 
/*  292:     */         case 'I': 
/*  293:     */         case 'J': 
/*  294:     */         case 'K': 
/*  295:     */         case 'L': 
/*  296:     */         case 'M': 
/*  297:     */         case 'N': 
/*  298:     */         case 'O': 
/*  299:     */         case 'P': 
/*  300:     */         case 'Q': 
/*  301:     */         case 'R': 
/*  302:     */         case 'S': 
/*  303:     */         case 'T': 
/*  304:     */         case 'U': 
/*  305:     */         case 'V': 
/*  306:     */         case 'W': 
/*  307:     */         case 'X': 
/*  308:     */         case 'Y': 
/*  309:     */         case 'Z': 
/*  310:     */         case '_': 
/*  311:     */         case 'a': 
/*  312:     */         case 'b': 
/*  313:     */         case 'c': 
/*  314:     */         case 'd': 
/*  315:     */         case 'e': 
/*  316:     */         case 'f': 
/*  317:     */         case 'g': 
/*  318:     */         case 'h': 
/*  319:     */         case 'i': 
/*  320:     */         case 'j': 
/*  321:     */         case 'k': 
/*  322:     */         case 'l': 
/*  323:     */         case 'm': 
/*  324:     */         case 'n': 
/*  325:     */         case 'o': 
/*  326:     */         case 'p': 
/*  327:     */         case 'q': 
/*  328:     */         case 'r': 
/*  329:     */         case 's': 
/*  330:     */         case 't': 
/*  331:     */         case 'u': 
/*  332:     */         case 'v': 
/*  333:     */         case 'w': 
/*  334:     */         case 'x': 
/*  335:     */         case 'y': 
/*  336:     */         case 'z': 
/*  337:     */           break;
/*  338:     */         case '\013': 
/*  339:     */         case '\f': 
/*  340:     */         case '\016': 
/*  341:     */         case '\017': 
/*  342:     */         case '\020': 
/*  343:     */         case '\021': 
/*  344:     */         case '\022': 
/*  345:     */         case '\023': 
/*  346:     */         case '\024': 
/*  347:     */         case '\025': 
/*  348:     */         case '\026': 
/*  349:     */         case '\027': 
/*  350:     */         case '\030': 
/*  351:     */         case '\031': 
/*  352:     */         case '\032': 
/*  353:     */         case '\033': 
/*  354:     */         case '\034': 
/*  355:     */         case '\035': 
/*  356:     */         case '\036': 
/*  357:     */         case '\037': 
/*  358:     */         case '!': 
/*  359:     */         case '"': 
/*  360:     */         case '#': 
/*  361:     */         case '$': 
/*  362:     */         case '%': 
/*  363:     */         case '&': 
/*  364:     */         case '\'': 
/*  365:     */         case '(': 
/*  366:     */         case ')': 
/*  367:     */         case '*': 
/*  368:     */         case '+': 
/*  369:     */         case ',': 
/*  370:     */         case '-': 
/*  371:     */         case '.': 
/*  372:     */         case '/': 
/*  373:     */         case '0': 
/*  374:     */         case '1': 
/*  375:     */         case '2': 
/*  376:     */         case '3': 
/*  377:     */         case '4': 
/*  378:     */         case '5': 
/*  379:     */         case '6': 
/*  380:     */         case '7': 
/*  381:     */         case '8': 
/*  382:     */         case '9': 
/*  383:     */         case ':': 
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
/*  407: 318 */         else if ((str1.equals("define")) || (str1.equals("undef")) || (str1.equals("if")) || (str1.equals("elif")) || (str1.equals("else")) || (str1.equals("endif")) || (str1.equals("line")) || (str1.equals("error")) || (str1.equals("warning")) || (str1.equals("region")) || (str1.equals("endregion")))
/*  408:     */         {
/*  409: 330 */           this.text.setLength(j);this.text.append("#" + str1);
/*  410:     */         }
/*  411: 335 */         if (_tokenSet_4.member(LA(1))) {
/*  412: 336 */           mWS(false);
/*  413:     */         }
/*  414: 343 */         if (LA(1) == '=') {
/*  415: 344 */           mVAR_ASSIGN(false);
/*  416:     */         }
/*  417:     */       }
/*  418: 351 */       else if ((LA(1) == '#') && (LA(2) == '['))
/*  419:     */       {
/*  420: 352 */         k = this.text.length();
/*  421: 353 */         match('#');
/*  422: 354 */         this.text.setLength(k);
/*  423: 355 */         mAST_CONSTRUCTOR(true);
/*  424: 356 */         localToken4 = this._returnToken;
/*  425:     */       }
/*  426: 358 */       else if ((LA(1) == '#') && (LA(2) == '#'))
/*  427:     */       {
/*  428: 359 */         match("##");
/*  429: 361 */         if (this.currentRule != null)
/*  430:     */         {
/*  431: 363 */           str1 = this.currentRule.getRuleName() + "_AST";
/*  432: 364 */           this.text.setLength(j);this.text.append(str1);
/*  433: 366 */           if (this.transInfo != null) {
/*  434: 367 */             this.transInfo.refRuleRoot = str1;
/*  435:     */           }
/*  436:     */         }
/*  437:     */         else
/*  438:     */         {
/*  439: 372 */           reportWarning("\"##\" not valid in this context");
/*  440: 373 */           this.text.setLength(j);this.text.append("##");
/*  441:     */         }
/*  442: 377 */         if (_tokenSet_4.member(LA(1))) {
/*  443: 378 */           mWS(false);
/*  444:     */         }
/*  445: 385 */         if (LA(1) == '=') {
/*  446: 386 */           mVAR_ASSIGN(false);
/*  447:     */         }
/*  448:     */       }
/*  449:     */       else
/*  450:     */       {
/*  451: 394 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  452:     */       }
/*  453:     */     }
/*  454: 397 */     if ((paramBoolean) && (localToken1 == null) && (i != -1))
/*  455:     */     {
/*  456: 398 */       localToken1 = makeToken(i);
/*  457: 399 */       localToken1.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  458:     */     }
/*  459: 401 */     this._returnToken = localToken1;
/*  460:     */   }
/*  461:     */   
/*  462:     */   protected final void mTEXT_ITEM(boolean paramBoolean)
/*  463:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  464:     */   {
/*  465: 405 */     Token localToken1 = null;int j = this.text.length();
/*  466: 406 */     int i = 7;
/*  467:     */     
/*  468: 408 */     Token localToken2 = null;
/*  469: 409 */     Token localToken3 = null;
/*  470: 410 */     Token localToken4 = null;
/*  471: 411 */     Token localToken5 = null;
/*  472: 412 */     Token localToken6 = null;
/*  473: 413 */     Token localToken7 = null;
/*  474:     */     String str1;
/*  475:     */     String str2;
/*  476: 415 */     if ((LA(1) == '$') && (LA(2) == 'F') && (LA(3) == 'O'))
/*  477:     */     {
/*  478: 416 */       match("$FOLLOW");
/*  479: 418 */       if ((_tokenSet_5.member(LA(1))) && (_tokenSet_6.member(LA(2))) && (LA(3) >= '\003') && (LA(3) <= 'ÿ'))
/*  480:     */       {
/*  481: 420 */         switch (LA(1))
/*  482:     */         {
/*  483:     */         case '\t': 
/*  484:     */         case '\n': 
/*  485:     */         case '\r': 
/*  486:     */         case ' ': 
/*  487: 423 */           mWS(false);
/*  488: 424 */           break;
/*  489:     */         case '(': 
/*  490:     */           break;
/*  491:     */         default: 
/*  492: 432 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  493:     */         }
/*  494: 436 */         match('(');
/*  495: 437 */         mTEXT_ARG(true);
/*  496: 438 */         localToken6 = this._returnToken;
/*  497: 439 */         match(')');
/*  498:     */       }
/*  499: 446 */       str1 = this.currentRule.getRuleName();
/*  500: 447 */       if (localToken6 != null) {
/*  501: 448 */         str1 = localToken6.getText();
/*  502:     */       }
/*  503: 450 */       str2 = this.generator.getFOLLOWBitSet(str1, 1);
/*  504: 452 */       if (str2 == null)
/*  505:     */       {
/*  506: 453 */         reportError("$FOLLOW(" + str1 + ")" + ": unknown rule or bad lookahead computation");
/*  507:     */       }
/*  508:     */       else
/*  509:     */       {
/*  510: 457 */         this.text.setLength(j);this.text.append(str2);
/*  511:     */       }
/*  512:     */     }
/*  513: 461 */     else if ((LA(1) == '$') && (LA(2) == 'F') && (LA(3) == 'I'))
/*  514:     */     {
/*  515: 462 */       match("$FIRST");
/*  516: 464 */       if ((_tokenSet_5.member(LA(1))) && (_tokenSet_6.member(LA(2))) && (LA(3) >= '\003') && (LA(3) <= 'ÿ'))
/*  517:     */       {
/*  518: 466 */         switch (LA(1))
/*  519:     */         {
/*  520:     */         case '\t': 
/*  521:     */         case '\n': 
/*  522:     */         case '\r': 
/*  523:     */         case ' ': 
/*  524: 469 */           mWS(false);
/*  525: 470 */           break;
/*  526:     */         case '(': 
/*  527:     */           break;
/*  528:     */         default: 
/*  529: 478 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  530:     */         }
/*  531: 482 */         match('(');
/*  532: 483 */         mTEXT_ARG(true);
/*  533: 484 */         localToken7 = this._returnToken;
/*  534: 485 */         match(')');
/*  535:     */       }
/*  536: 492 */       str1 = this.currentRule.getRuleName();
/*  537: 493 */       if (localToken7 != null) {
/*  538: 494 */         str1 = localToken7.getText();
/*  539:     */       }
/*  540: 496 */       str2 = this.generator.getFIRSTBitSet(str1, 1);
/*  541: 498 */       if (str2 == null)
/*  542:     */       {
/*  543: 499 */         reportError("$FIRST(" + str1 + ")" + ": unknown rule or bad lookahead computation");
/*  544:     */       }
/*  545:     */       else
/*  546:     */       {
/*  547: 503 */         this.text.setLength(j);this.text.append(str2);
/*  548:     */       }
/*  549:     */     }
/*  550: 507 */     else if ((LA(1) == '$') && (LA(2) == 'a'))
/*  551:     */     {
/*  552: 508 */       match("$append");
/*  553: 510 */       switch (LA(1))
/*  554:     */       {
/*  555:     */       case '\t': 
/*  556:     */       case '\n': 
/*  557:     */       case '\r': 
/*  558:     */       case ' ': 
/*  559: 513 */         mWS(false);
/*  560: 514 */         break;
/*  561:     */       case '(': 
/*  562:     */         break;
/*  563:     */       default: 
/*  564: 522 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  565:     */       }
/*  566: 526 */       match('(');
/*  567: 527 */       mTEXT_ARG(true);
/*  568: 528 */       localToken2 = this._returnToken;
/*  569: 529 */       match(')');
/*  570:     */       
/*  571: 531 */       str1 = "text.Append(" + localToken2.getText() + ")";
/*  572: 532 */       this.text.setLength(j);this.text.append(str1);
/*  573:     */     }
/*  574: 535 */     else if ((LA(1) == '$') && (LA(2) == 's'))
/*  575:     */     {
/*  576: 536 */       match("$set");
/*  577: 538 */       if ((LA(1) == 'T') && (LA(2) == 'e'))
/*  578:     */       {
/*  579: 539 */         match("Text");
/*  580: 541 */         switch (LA(1))
/*  581:     */         {
/*  582:     */         case '\t': 
/*  583:     */         case '\n': 
/*  584:     */         case '\r': 
/*  585:     */         case ' ': 
/*  586: 544 */           mWS(false);
/*  587: 545 */           break;
/*  588:     */         case '(': 
/*  589:     */           break;
/*  590:     */         default: 
/*  591: 553 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  592:     */         }
/*  593: 557 */         match('(');
/*  594: 558 */         mTEXT_ARG(true);
/*  595: 559 */         localToken3 = this._returnToken;
/*  596: 560 */         match(')');
/*  597:     */         
/*  598:     */ 
/*  599: 563 */         str1 = "text.Length = _begin; text.Append(" + localToken3.getText() + ")";
/*  600: 564 */         this.text.setLength(j);this.text.append(str1);
/*  601:     */       }
/*  602: 567 */       else if ((LA(1) == 'T') && (LA(2) == 'o'))
/*  603:     */       {
/*  604: 568 */         match("Token");
/*  605: 570 */         switch (LA(1))
/*  606:     */         {
/*  607:     */         case '\t': 
/*  608:     */         case '\n': 
/*  609:     */         case '\r': 
/*  610:     */         case ' ': 
/*  611: 573 */           mWS(false);
/*  612: 574 */           break;
/*  613:     */         case '(': 
/*  614:     */           break;
/*  615:     */         default: 
/*  616: 582 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  617:     */         }
/*  618: 586 */         match('(');
/*  619: 587 */         mTEXT_ARG(true);
/*  620: 588 */         localToken4 = this._returnToken;
/*  621: 589 */         match(')');
/*  622:     */         
/*  623: 591 */         str1 = "_token = " + localToken4.getText();
/*  624: 592 */         this.text.setLength(j);this.text.append(str1);
/*  625:     */       }
/*  626: 595 */       else if ((LA(1) == 'T') && (LA(2) == 'y'))
/*  627:     */       {
/*  628: 596 */         match("Type");
/*  629: 598 */         switch (LA(1))
/*  630:     */         {
/*  631:     */         case '\t': 
/*  632:     */         case '\n': 
/*  633:     */         case '\r': 
/*  634:     */         case ' ': 
/*  635: 601 */           mWS(false);
/*  636: 602 */           break;
/*  637:     */         case '(': 
/*  638:     */           break;
/*  639:     */         default: 
/*  640: 610 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  641:     */         }
/*  642: 614 */         match('(');
/*  643: 615 */         mTEXT_ARG(true);
/*  644: 616 */         localToken5 = this._returnToken;
/*  645: 617 */         match(')');
/*  646:     */         
/*  647: 619 */         str1 = "_ttype = " + localToken5.getText();
/*  648: 620 */         this.text.setLength(j);this.text.append(str1);
/*  649:     */       }
/*  650:     */       else
/*  651:     */       {
/*  652: 624 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  653:     */       }
/*  654:     */     }
/*  655: 629 */     else if ((LA(1) == '$') && (LA(2) == 'g'))
/*  656:     */     {
/*  657: 630 */       match("$getText");
/*  658:     */       
/*  659: 632 */       this.text.setLength(j);this.text.append("text.ToString(_begin, text.Length-_begin)");
/*  660:     */     }
/*  661:     */     else
/*  662:     */     {
/*  663: 636 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  664:     */     }
/*  665: 639 */     if ((paramBoolean) && (localToken1 == null) && (i != -1))
/*  666:     */     {
/*  667: 640 */       localToken1 = makeToken(i);
/*  668: 641 */       localToken1.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  669:     */     }
/*  670: 643 */     this._returnToken = localToken1;
/*  671:     */   }
/*  672:     */   
/*  673:     */   protected final void mCOMMENT(boolean paramBoolean)
/*  674:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  675:     */   {
/*  676: 647 */     Token localToken = null;int j = this.text.length();
/*  677: 648 */     int i = 19;
/*  678: 651 */     if ((LA(1) == '/') && (LA(2) == '/')) {
/*  679: 652 */       mSL_COMMENT(false);
/*  680: 654 */     } else if ((LA(1) == '/') && (LA(2) == '*')) {
/*  681: 655 */       mML_COMMENT(false);
/*  682:     */     } else {
/*  683: 658 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  684:     */     }
/*  685: 661 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/*  686:     */     {
/*  687: 662 */       localToken = makeToken(i);
/*  688: 663 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  689:     */     }
/*  690: 665 */     this._returnToken = localToken;
/*  691:     */   }
/*  692:     */   
/*  693:     */   protected final void mSTRING(boolean paramBoolean)
/*  694:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  695:     */   {
/*  696: 669 */     Token localToken = null;int j = this.text.length();
/*  697: 670 */     int i = 23;
/*  698:     */     
/*  699:     */ 
/*  700: 673 */     match('"');
/*  701:     */     for (;;)
/*  702:     */     {
/*  703: 677 */       if (LA(1) == '\\')
/*  704:     */       {
/*  705: 678 */         mESC(false);
/*  706:     */       }
/*  707:     */       else
/*  708:     */       {
/*  709: 680 */         if (!_tokenSet_7.member(LA(1))) {
/*  710:     */           break;
/*  711:     */         }
/*  712: 681 */         matchNot('"');
/*  713:     */       }
/*  714:     */     }
/*  715: 689 */     match('"');
/*  716: 690 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/*  717:     */     {
/*  718: 691 */       localToken = makeToken(i);
/*  719: 692 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  720:     */     }
/*  721: 694 */     this._returnToken = localToken;
/*  722:     */   }
/*  723:     */   
/*  724:     */   protected final void mCHAR(boolean paramBoolean)
/*  725:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  726:     */   {
/*  727: 698 */     Token localToken = null;int j = this.text.length();
/*  728: 699 */     int i = 22;
/*  729:     */     
/*  730:     */ 
/*  731: 702 */     match('\'');
/*  732: 704 */     if (LA(1) == '\\') {
/*  733: 705 */       mESC(false);
/*  734: 707 */     } else if (_tokenSet_8.member(LA(1))) {
/*  735: 708 */       matchNot('\'');
/*  736:     */     } else {
/*  737: 711 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  738:     */     }
/*  739: 715 */     match('\'');
/*  740: 716 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/*  741:     */     {
/*  742: 717 */       localToken = makeToken(i);
/*  743: 718 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  744:     */     }
/*  745: 720 */     this._returnToken = localToken;
/*  746:     */   }
/*  747:     */   
/*  748:     */   protected final void mTREE(boolean paramBoolean)
/*  749:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  750:     */   {
/*  751: 724 */     Token localToken1 = null;int j = this.text.length();
/*  752: 725 */     int i = 8;
/*  753:     */     
/*  754: 727 */     Token localToken2 = null;
/*  755: 728 */     Token localToken3 = null;
/*  756:     */     
/*  757: 730 */     StringBuffer localStringBuffer = new StringBuffer();
/*  758: 731 */     int m = 0;
/*  759: 732 */     Vector localVector = new Vector(10);
/*  760:     */     
/*  761:     */ 
/*  762: 735 */     int k = this.text.length();
/*  763: 736 */     match('(');
/*  764: 737 */     this.text.setLength(k);
/*  765: 739 */     switch (LA(1))
/*  766:     */     {
/*  767:     */     case '\t': 
/*  768:     */     case '\n': 
/*  769:     */     case '\r': 
/*  770:     */     case ' ': 
/*  771: 742 */       k = this.text.length();
/*  772: 743 */       mWS(false);
/*  773: 744 */       this.text.setLength(k);
/*  774: 745 */       break;
/*  775:     */     case '"': 
/*  776:     */     case '#': 
/*  777:     */     case '(': 
/*  778:     */     case 'A': 
/*  779:     */     case 'B': 
/*  780:     */     case 'C': 
/*  781:     */     case 'D': 
/*  782:     */     case 'E': 
/*  783:     */     case 'F': 
/*  784:     */     case 'G': 
/*  785:     */     case 'H': 
/*  786:     */     case 'I': 
/*  787:     */     case 'J': 
/*  788:     */     case 'K': 
/*  789:     */     case 'L': 
/*  790:     */     case 'M': 
/*  791:     */     case 'N': 
/*  792:     */     case 'O': 
/*  793:     */     case 'P': 
/*  794:     */     case 'Q': 
/*  795:     */     case 'R': 
/*  796:     */     case 'S': 
/*  797:     */     case 'T': 
/*  798:     */     case 'U': 
/*  799:     */     case 'V': 
/*  800:     */     case 'W': 
/*  801:     */     case 'X': 
/*  802:     */     case 'Y': 
/*  803:     */     case 'Z': 
/*  804:     */     case '[': 
/*  805:     */     case '_': 
/*  806:     */     case 'a': 
/*  807:     */     case 'b': 
/*  808:     */     case 'c': 
/*  809:     */     case 'd': 
/*  810:     */     case 'e': 
/*  811:     */     case 'f': 
/*  812:     */     case 'g': 
/*  813:     */     case 'h': 
/*  814:     */     case 'i': 
/*  815:     */     case 'j': 
/*  816:     */     case 'k': 
/*  817:     */     case 'l': 
/*  818:     */     case 'm': 
/*  819:     */     case 'n': 
/*  820:     */     case 'o': 
/*  821:     */     case 'p': 
/*  822:     */     case 'q': 
/*  823:     */     case 'r': 
/*  824:     */     case 's': 
/*  825:     */     case 't': 
/*  826:     */     case 'u': 
/*  827:     */     case 'v': 
/*  828:     */     case 'w': 
/*  829:     */     case 'x': 
/*  830:     */     case 'y': 
/*  831:     */     case 'z': 
/*  832:     */       break;
/*  833:     */     case '\013': 
/*  834:     */     case '\f': 
/*  835:     */     case '\016': 
/*  836:     */     case '\017': 
/*  837:     */     case '\020': 
/*  838:     */     case '\021': 
/*  839:     */     case '\022': 
/*  840:     */     case '\023': 
/*  841:     */     case '\024': 
/*  842:     */     case '\025': 
/*  843:     */     case '\026': 
/*  844:     */     case '\027': 
/*  845:     */     case '\030': 
/*  846:     */     case '\031': 
/*  847:     */     case '\032': 
/*  848:     */     case '\033': 
/*  849:     */     case '\034': 
/*  850:     */     case '\035': 
/*  851:     */     case '\036': 
/*  852:     */     case '\037': 
/*  853:     */     case '!': 
/*  854:     */     case '$': 
/*  855:     */     case '%': 
/*  856:     */     case '&': 
/*  857:     */     case '\'': 
/*  858:     */     case ')': 
/*  859:     */     case '*': 
/*  860:     */     case '+': 
/*  861:     */     case ',': 
/*  862:     */     case '-': 
/*  863:     */     case '.': 
/*  864:     */     case '/': 
/*  865:     */     case '0': 
/*  866:     */     case '1': 
/*  867:     */     case '2': 
/*  868:     */     case '3': 
/*  869:     */     case '4': 
/*  870:     */     case '5': 
/*  871:     */     case '6': 
/*  872:     */     case '7': 
/*  873:     */     case '8': 
/*  874:     */     case '9': 
/*  875:     */     case ':': 
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
/*  887: 767 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  888:     */     }
/*  889: 771 */     k = this.text.length();
/*  890: 772 */     mTREE_ELEMENT(true);
/*  891: 773 */     this.text.setLength(k);
/*  892: 774 */     localToken2 = this._returnToken;
/*  893:     */     
/*  894: 776 */     localVector.appendElement(this.generator.processStringForASTConstructor(localToken2.getText()));
/*  895: 781 */     switch (LA(1))
/*  896:     */     {
/*  897:     */     case '\t': 
/*  898:     */     case '\n': 
/*  899:     */     case '\r': 
/*  900:     */     case ' ': 
/*  901: 784 */       k = this.text.length();
/*  902: 785 */       mWS(false);
/*  903: 786 */       this.text.setLength(k);
/*  904: 787 */       break;
/*  905:     */     case ')': 
/*  906:     */     case ',': 
/*  907:     */       break;
/*  908:     */     default: 
/*  909: 795 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  910:     */     }
/*  911: 802 */     while (LA(1) == ',')
/*  912:     */     {
/*  913: 803 */       k = this.text.length();
/*  914: 804 */       match(',');
/*  915: 805 */       this.text.setLength(k);
/*  916: 807 */       switch (LA(1))
/*  917:     */       {
/*  918:     */       case '\t': 
/*  919:     */       case '\n': 
/*  920:     */       case '\r': 
/*  921:     */       case ' ': 
/*  922: 810 */         k = this.text.length();
/*  923: 811 */         mWS(false);
/*  924: 812 */         this.text.setLength(k);
/*  925: 813 */         break;
/*  926:     */       case '"': 
/*  927:     */       case '#': 
/*  928:     */       case '(': 
/*  929:     */       case 'A': 
/*  930:     */       case 'B': 
/*  931:     */       case 'C': 
/*  932:     */       case 'D': 
/*  933:     */       case 'E': 
/*  934:     */       case 'F': 
/*  935:     */       case 'G': 
/*  936:     */       case 'H': 
/*  937:     */       case 'I': 
/*  938:     */       case 'J': 
/*  939:     */       case 'K': 
/*  940:     */       case 'L': 
/*  941:     */       case 'M': 
/*  942:     */       case 'N': 
/*  943:     */       case 'O': 
/*  944:     */       case 'P': 
/*  945:     */       case 'Q': 
/*  946:     */       case 'R': 
/*  947:     */       case 'S': 
/*  948:     */       case 'T': 
/*  949:     */       case 'U': 
/*  950:     */       case 'V': 
/*  951:     */       case 'W': 
/*  952:     */       case 'X': 
/*  953:     */       case 'Y': 
/*  954:     */       case 'Z': 
/*  955:     */       case '[': 
/*  956:     */       case '_': 
/*  957:     */       case 'a': 
/*  958:     */       case 'b': 
/*  959:     */       case 'c': 
/*  960:     */       case 'd': 
/*  961:     */       case 'e': 
/*  962:     */       case 'f': 
/*  963:     */       case 'g': 
/*  964:     */       case 'h': 
/*  965:     */       case 'i': 
/*  966:     */       case 'j': 
/*  967:     */       case 'k': 
/*  968:     */       case 'l': 
/*  969:     */       case 'm': 
/*  970:     */       case 'n': 
/*  971:     */       case 'o': 
/*  972:     */       case 'p': 
/*  973:     */       case 'q': 
/*  974:     */       case 'r': 
/*  975:     */       case 's': 
/*  976:     */       case 't': 
/*  977:     */       case 'u': 
/*  978:     */       case 'v': 
/*  979:     */       case 'w': 
/*  980:     */       case 'x': 
/*  981:     */       case 'y': 
/*  982:     */       case 'z': 
/*  983:     */         break;
/*  984:     */       case '\013': 
/*  985:     */       case '\f': 
/*  986:     */       case '\016': 
/*  987:     */       case '\017': 
/*  988:     */       case '\020': 
/*  989:     */       case '\021': 
/*  990:     */       case '\022': 
/*  991:     */       case '\023': 
/*  992:     */       case '\024': 
/*  993:     */       case '\025': 
/*  994:     */       case '\026': 
/*  995:     */       case '\027': 
/*  996:     */       case '\030': 
/*  997:     */       case '\031': 
/*  998:     */       case '\032': 
/*  999:     */       case '\033': 
/* 1000:     */       case '\034': 
/* 1001:     */       case '\035': 
/* 1002:     */       case '\036': 
/* 1003:     */       case '\037': 
/* 1004:     */       case '!': 
/* 1005:     */       case '$': 
/* 1006:     */       case '%': 
/* 1007:     */       case '&': 
/* 1008:     */       case '\'': 
/* 1009:     */       case ')': 
/* 1010:     */       case '*': 
/* 1011:     */       case '+': 
/* 1012:     */       case ',': 
/* 1013:     */       case '-': 
/* 1014:     */       case '.': 
/* 1015:     */       case '/': 
/* 1016:     */       case '0': 
/* 1017:     */       case '1': 
/* 1018:     */       case '2': 
/* 1019:     */       case '3': 
/* 1020:     */       case '4': 
/* 1021:     */       case '5': 
/* 1022:     */       case '6': 
/* 1023:     */       case '7': 
/* 1024:     */       case '8': 
/* 1025:     */       case '9': 
/* 1026:     */       case ':': 
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
/* 1038: 835 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1039:     */       }
/* 1040: 839 */       k = this.text.length();
/* 1041: 840 */       mTREE_ELEMENT(true);
/* 1042: 841 */       this.text.setLength(k);
/* 1043: 842 */       localToken3 = this._returnToken;
/* 1044:     */       
/* 1045: 844 */       localVector.appendElement(this.generator.processStringForASTConstructor(localToken3.getText()));
/* 1046: 849 */       switch (LA(1))
/* 1047:     */       {
/* 1048:     */       case '\t': 
/* 1049:     */       case '\n': 
/* 1050:     */       case '\r': 
/* 1051:     */       case ' ': 
/* 1052: 852 */         k = this.text.length();
/* 1053: 853 */         mWS(false);
/* 1054: 854 */         this.text.setLength(k);
/* 1055: 855 */         break;
/* 1056:     */       case ')': 
/* 1057:     */       case ',': 
/* 1058:     */         break;
/* 1059:     */       default: 
/* 1060: 863 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1061:     */       }
/* 1062:     */     }
/* 1063: 874 */     this.text.setLength(j);this.text.append(this.generator.getASTCreateString(localVector));
/* 1064: 875 */     k = this.text.length();
/* 1065: 876 */     match(')');
/* 1066: 877 */     this.text.setLength(k);
/* 1067: 878 */     if ((paramBoolean) && (localToken1 == null) && (i != -1))
/* 1068:     */     {
/* 1069: 879 */       localToken1 = makeToken(i);
/* 1070: 880 */       localToken1.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 1071:     */     }
/* 1072: 882 */     this._returnToken = localToken1;
/* 1073:     */   }
/* 1074:     */   
/* 1075:     */   protected final void mWS(boolean paramBoolean)
/* 1076:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 1077:     */   {
/* 1078: 886 */     Token localToken = null;int j = this.text.length();
/* 1079: 887 */     int i = 28;
/* 1080:     */     
/* 1081:     */ 
/* 1082:     */ 
/* 1083: 891 */     int k = 0;
/* 1084:     */     for (;;)
/* 1085:     */     {
/* 1086: 894 */       if ((LA(1) == '\r') && (LA(2) == '\n'))
/* 1087:     */       {
/* 1088: 895 */         match('\r');
/* 1089: 896 */         match('\n');
/* 1090: 897 */         newline();
/* 1091:     */       }
/* 1092: 899 */       else if (LA(1) == ' ')
/* 1093:     */       {
/* 1094: 900 */         match(' ');
/* 1095:     */       }
/* 1096: 902 */       else if (LA(1) == '\t')
/* 1097:     */       {
/* 1098: 903 */         match('\t');
/* 1099:     */       }
/* 1100: 905 */       else if (LA(1) == '\r')
/* 1101:     */       {
/* 1102: 906 */         match('\r');
/* 1103: 907 */         newline();
/* 1104:     */       }
/* 1105: 909 */       else if (LA(1) == '\n')
/* 1106:     */       {
/* 1107: 910 */         match('\n');
/* 1108: 911 */         newline();
/* 1109:     */       }
/* 1110:     */       else
/* 1111:     */       {
/* 1112: 914 */         if (k >= 1) {
/* 1113:     */           break;
/* 1114:     */         }
/* 1115: 914 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1116:     */       }
/* 1117: 917 */       k++;
/* 1118:     */     }
/* 1119: 920 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 1120:     */     {
/* 1121: 921 */       localToken = makeToken(i);
/* 1122: 922 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 1123:     */     }
/* 1124: 924 */     this._returnToken = localToken;
/* 1125:     */   }
/* 1126:     */   
/* 1127:     */   protected final void mID(boolean paramBoolean)
/* 1128:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 1129:     */   {
/* 1130: 928 */     Token localToken = null;int j = this.text.length();
/* 1131: 929 */     int i = 17;
/* 1132: 933 */     switch (LA(1))
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
/* 1160: 942 */       matchRange('a', 'z');
/* 1161: 943 */       break;
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
/* 1188: 953 */       matchRange('A', 'Z');
/* 1189: 954 */       break;
/* 1190:     */     case '_': 
/* 1191: 958 */       match('_');
/* 1192: 959 */       break;
/* 1193:     */     case '[': 
/* 1194:     */     case '\\': 
/* 1195:     */     case ']': 
/* 1196:     */     case '^': 
/* 1197:     */     case '`': 
/* 1198:     */     default: 
/* 1199: 963 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1200:     */     }
/* 1201: 970 */     while (_tokenSet_9.member(LA(1))) {
/* 1202: 972 */       switch (LA(1))
/* 1203:     */       {
/* 1204:     */       case 'a': 
/* 1205:     */       case 'b': 
/* 1206:     */       case 'c': 
/* 1207:     */       case 'd': 
/* 1208:     */       case 'e': 
/* 1209:     */       case 'f': 
/* 1210:     */       case 'g': 
/* 1211:     */       case 'h': 
/* 1212:     */       case 'i': 
/* 1213:     */       case 'j': 
/* 1214:     */       case 'k': 
/* 1215:     */       case 'l': 
/* 1216:     */       case 'm': 
/* 1217:     */       case 'n': 
/* 1218:     */       case 'o': 
/* 1219:     */       case 'p': 
/* 1220:     */       case 'q': 
/* 1221:     */       case 'r': 
/* 1222:     */       case 's': 
/* 1223:     */       case 't': 
/* 1224:     */       case 'u': 
/* 1225:     */       case 'v': 
/* 1226:     */       case 'w': 
/* 1227:     */       case 'x': 
/* 1228:     */       case 'y': 
/* 1229:     */       case 'z': 
/* 1230: 981 */         matchRange('a', 'z');
/* 1231: 982 */         break;
/* 1232:     */       case 'A': 
/* 1233:     */       case 'B': 
/* 1234:     */       case 'C': 
/* 1235:     */       case 'D': 
/* 1236:     */       case 'E': 
/* 1237:     */       case 'F': 
/* 1238:     */       case 'G': 
/* 1239:     */       case 'H': 
/* 1240:     */       case 'I': 
/* 1241:     */       case 'J': 
/* 1242:     */       case 'K': 
/* 1243:     */       case 'L': 
/* 1244:     */       case 'M': 
/* 1245:     */       case 'N': 
/* 1246:     */       case 'O': 
/* 1247:     */       case 'P': 
/* 1248:     */       case 'Q': 
/* 1249:     */       case 'R': 
/* 1250:     */       case 'S': 
/* 1251:     */       case 'T': 
/* 1252:     */       case 'U': 
/* 1253:     */       case 'V': 
/* 1254:     */       case 'W': 
/* 1255:     */       case 'X': 
/* 1256:     */       case 'Y': 
/* 1257:     */       case 'Z': 
/* 1258: 992 */         matchRange('A', 'Z');
/* 1259: 993 */         break;
/* 1260:     */       case '0': 
/* 1261:     */       case '1': 
/* 1262:     */       case '2': 
/* 1263:     */       case '3': 
/* 1264:     */       case '4': 
/* 1265:     */       case '5': 
/* 1266:     */       case '6': 
/* 1267:     */       case '7': 
/* 1268:     */       case '8': 
/* 1269:     */       case '9': 
/* 1270: 999 */         matchRange('0', '9');
/* 1271:1000 */         break;
/* 1272:     */       case '_': 
/* 1273:1004 */         match('_');
/* 1274:1005 */         break;
/* 1275:     */       case ':': 
/* 1276:     */       case ';': 
/* 1277:     */       case '<': 
/* 1278:     */       case '=': 
/* 1279:     */       case '>': 
/* 1280:     */       case '?': 
/* 1281:     */       case '@': 
/* 1282:     */       case '[': 
/* 1283:     */       case '\\': 
/* 1284:     */       case ']': 
/* 1285:     */       case '^': 
/* 1286:     */       case '`': 
/* 1287:     */       default: 
/* 1288:1009 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1289:     */       }
/* 1290:     */     }
/* 1291:1020 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 1292:     */     {
/* 1293:1021 */       localToken = makeToken(i);
/* 1294:1022 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 1295:     */     }
/* 1296:1024 */     this._returnToken = localToken;
/* 1297:     */   }
/* 1298:     */   
/* 1299:     */   protected final void mVAR_ASSIGN(boolean paramBoolean)
/* 1300:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 1301:     */   {
/* 1302:1028 */     Token localToken = null;int j = this.text.length();
/* 1303:1029 */     int i = 18;
/* 1304:     */     
/* 1305:     */ 
/* 1306:1032 */     match('=');
/* 1307:1036 */     if ((LA(1) != '=') && (this.transInfo != null) && (this.transInfo.refRuleRoot != null)) {
/* 1308:1037 */       this.transInfo.assignToRoot = true;
/* 1309:     */     }
/* 1310:1040 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 1311:     */     {
/* 1312:1041 */       localToken = makeToken(i);
/* 1313:1042 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 1314:     */     }
/* 1315:1044 */     this._returnToken = localToken;
/* 1316:     */   }
/* 1317:     */   
/* 1318:     */   protected final void mAST_CONSTRUCTOR(boolean paramBoolean)
/* 1319:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 1320:     */   {
/* 1321:1048 */     Token localToken1 = null;int j = this.text.length();
/* 1322:1049 */     int i = 10;
/* 1323:     */     
/* 1324:1051 */     Token localToken2 = null;
/* 1325:1052 */     Token localToken3 = null;
/* 1326:1053 */     Token localToken4 = null;
/* 1327:     */     
/* 1328:1055 */     int k = this.text.length();
/* 1329:1056 */     match('[');
/* 1330:1057 */     this.text.setLength(k);
/* 1331:1059 */     switch (LA(1))
/* 1332:     */     {
/* 1333:     */     case '\t': 
/* 1334:     */     case '\n': 
/* 1335:     */     case '\r': 
/* 1336:     */     case ' ': 
/* 1337:1062 */       k = this.text.length();
/* 1338:1063 */       mWS(false);
/* 1339:1064 */       this.text.setLength(k);
/* 1340:1065 */       break;
/* 1341:     */     case '"': 
/* 1342:     */     case '#': 
/* 1343:     */     case '(': 
/* 1344:     */     case '0': 
/* 1345:     */     case '1': 
/* 1346:     */     case '2': 
/* 1347:     */     case '3': 
/* 1348:     */     case '4': 
/* 1349:     */     case '5': 
/* 1350:     */     case '6': 
/* 1351:     */     case '7': 
/* 1352:     */     case '8': 
/* 1353:     */     case '9': 
/* 1354:     */     case 'A': 
/* 1355:     */     case 'B': 
/* 1356:     */     case 'C': 
/* 1357:     */     case 'D': 
/* 1358:     */     case 'E': 
/* 1359:     */     case 'F': 
/* 1360:     */     case 'G': 
/* 1361:     */     case 'H': 
/* 1362:     */     case 'I': 
/* 1363:     */     case 'J': 
/* 1364:     */     case 'K': 
/* 1365:     */     case 'L': 
/* 1366:     */     case 'M': 
/* 1367:     */     case 'N': 
/* 1368:     */     case 'O': 
/* 1369:     */     case 'P': 
/* 1370:     */     case 'Q': 
/* 1371:     */     case 'R': 
/* 1372:     */     case 'S': 
/* 1373:     */     case 'T': 
/* 1374:     */     case 'U': 
/* 1375:     */     case 'V': 
/* 1376:     */     case 'W': 
/* 1377:     */     case 'X': 
/* 1378:     */     case 'Y': 
/* 1379:     */     case 'Z': 
/* 1380:     */     case '[': 
/* 1381:     */     case '_': 
/* 1382:     */     case 'a': 
/* 1383:     */     case 'b': 
/* 1384:     */     case 'c': 
/* 1385:     */     case 'd': 
/* 1386:     */     case 'e': 
/* 1387:     */     case 'f': 
/* 1388:     */     case 'g': 
/* 1389:     */     case 'h': 
/* 1390:     */     case 'i': 
/* 1391:     */     case 'j': 
/* 1392:     */     case 'k': 
/* 1393:     */     case 'l': 
/* 1394:     */     case 'm': 
/* 1395:     */     case 'n': 
/* 1396:     */     case 'o': 
/* 1397:     */     case 'p': 
/* 1398:     */     case 'q': 
/* 1399:     */     case 'r': 
/* 1400:     */     case 's': 
/* 1401:     */     case 't': 
/* 1402:     */     case 'u': 
/* 1403:     */     case 'v': 
/* 1404:     */     case 'w': 
/* 1405:     */     case 'x': 
/* 1406:     */     case 'y': 
/* 1407:     */     case 'z': 
/* 1408:     */       break;
/* 1409:     */     case '\013': 
/* 1410:     */     case '\f': 
/* 1411:     */     case '\016': 
/* 1412:     */     case '\017': 
/* 1413:     */     case '\020': 
/* 1414:     */     case '\021': 
/* 1415:     */     case '\022': 
/* 1416:     */     case '\023': 
/* 1417:     */     case '\024': 
/* 1418:     */     case '\025': 
/* 1419:     */     case '\026': 
/* 1420:     */     case '\027': 
/* 1421:     */     case '\030': 
/* 1422:     */     case '\031': 
/* 1423:     */     case '\032': 
/* 1424:     */     case '\033': 
/* 1425:     */     case '\034': 
/* 1426:     */     case '\035': 
/* 1427:     */     case '\036': 
/* 1428:     */     case '\037': 
/* 1429:     */     case '!': 
/* 1430:     */     case '$': 
/* 1431:     */     case '%': 
/* 1432:     */     case '&': 
/* 1433:     */     case '\'': 
/* 1434:     */     case ')': 
/* 1435:     */     case '*': 
/* 1436:     */     case '+': 
/* 1437:     */     case ',': 
/* 1438:     */     case '-': 
/* 1439:     */     case '.': 
/* 1440:     */     case '/': 
/* 1441:     */     case ':': 
/* 1442:     */     case ';': 
/* 1443:     */     case '<': 
/* 1444:     */     case '=': 
/* 1445:     */     case '>': 
/* 1446:     */     case '?': 
/* 1447:     */     case '@': 
/* 1448:     */     case '\\': 
/* 1449:     */     case ']': 
/* 1450:     */     case '^': 
/* 1451:     */     case '`': 
/* 1452:     */     default: 
/* 1453:1089 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1454:     */     }
/* 1455:1093 */     k = this.text.length();
/* 1456:1094 */     mAST_CTOR_ELEMENT(true);
/* 1457:1095 */     this.text.setLength(k);
/* 1458:1096 */     localToken2 = this._returnToken;
/* 1459:1098 */     switch (LA(1))
/* 1460:     */     {
/* 1461:     */     case '\t': 
/* 1462:     */     case '\n': 
/* 1463:     */     case '\r': 
/* 1464:     */     case ' ': 
/* 1465:1101 */       k = this.text.length();
/* 1466:1102 */       mWS(false);
/* 1467:1103 */       this.text.setLength(k);
/* 1468:1104 */       break;
/* 1469:     */     case ',': 
/* 1470:     */     case ']': 
/* 1471:     */       break;
/* 1472:     */     default: 
/* 1473:1112 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1474:     */     }
/* 1475:1117 */     if ((LA(1) == ',') && (_tokenSet_10.member(LA(2))) && (LA(3) >= '\003') && (LA(3) <= 'ÿ'))
/* 1476:     */     {
/* 1477:1118 */       k = this.text.length();
/* 1478:1119 */       match(',');
/* 1479:1120 */       this.text.setLength(k);
/* 1480:1122 */       switch (LA(1))
/* 1481:     */       {
/* 1482:     */       case '\t': 
/* 1483:     */       case '\n': 
/* 1484:     */       case '\r': 
/* 1485:     */       case ' ': 
/* 1486:1125 */         k = this.text.length();
/* 1487:1126 */         mWS(false);
/* 1488:1127 */         this.text.setLength(k);
/* 1489:1128 */         break;
/* 1490:     */       case '"': 
/* 1491:     */       case '#': 
/* 1492:     */       case '(': 
/* 1493:     */       case '0': 
/* 1494:     */       case '1': 
/* 1495:     */       case '2': 
/* 1496:     */       case '3': 
/* 1497:     */       case '4': 
/* 1498:     */       case '5': 
/* 1499:     */       case '6': 
/* 1500:     */       case '7': 
/* 1501:     */       case '8': 
/* 1502:     */       case '9': 
/* 1503:     */       case 'A': 
/* 1504:     */       case 'B': 
/* 1505:     */       case 'C': 
/* 1506:     */       case 'D': 
/* 1507:     */       case 'E': 
/* 1508:     */       case 'F': 
/* 1509:     */       case 'G': 
/* 1510:     */       case 'H': 
/* 1511:     */       case 'I': 
/* 1512:     */       case 'J': 
/* 1513:     */       case 'K': 
/* 1514:     */       case 'L': 
/* 1515:     */       case 'M': 
/* 1516:     */       case 'N': 
/* 1517:     */       case 'O': 
/* 1518:     */       case 'P': 
/* 1519:     */       case 'Q': 
/* 1520:     */       case 'R': 
/* 1521:     */       case 'S': 
/* 1522:     */       case 'T': 
/* 1523:     */       case 'U': 
/* 1524:     */       case 'V': 
/* 1525:     */       case 'W': 
/* 1526:     */       case 'X': 
/* 1527:     */       case 'Y': 
/* 1528:     */       case 'Z': 
/* 1529:     */       case '[': 
/* 1530:     */       case '_': 
/* 1531:     */       case 'a': 
/* 1532:     */       case 'b': 
/* 1533:     */       case 'c': 
/* 1534:     */       case 'd': 
/* 1535:     */       case 'e': 
/* 1536:     */       case 'f': 
/* 1537:     */       case 'g': 
/* 1538:     */       case 'h': 
/* 1539:     */       case 'i': 
/* 1540:     */       case 'j': 
/* 1541:     */       case 'k': 
/* 1542:     */       case 'l': 
/* 1543:     */       case 'm': 
/* 1544:     */       case 'n': 
/* 1545:     */       case 'o': 
/* 1546:     */       case 'p': 
/* 1547:     */       case 'q': 
/* 1548:     */       case 'r': 
/* 1549:     */       case 's': 
/* 1550:     */       case 't': 
/* 1551:     */       case 'u': 
/* 1552:     */       case 'v': 
/* 1553:     */       case 'w': 
/* 1554:     */       case 'x': 
/* 1555:     */       case 'y': 
/* 1556:     */       case 'z': 
/* 1557:     */         break;
/* 1558:     */       case '\013': 
/* 1559:     */       case '\f': 
/* 1560:     */       case '\016': 
/* 1561:     */       case '\017': 
/* 1562:     */       case '\020': 
/* 1563:     */       case '\021': 
/* 1564:     */       case '\022': 
/* 1565:     */       case '\023': 
/* 1566:     */       case '\024': 
/* 1567:     */       case '\025': 
/* 1568:     */       case '\026': 
/* 1569:     */       case '\027': 
/* 1570:     */       case '\030': 
/* 1571:     */       case '\031': 
/* 1572:     */       case '\032': 
/* 1573:     */       case '\033': 
/* 1574:     */       case '\034': 
/* 1575:     */       case '\035': 
/* 1576:     */       case '\036': 
/* 1577:     */       case '\037': 
/* 1578:     */       case '!': 
/* 1579:     */       case '$': 
/* 1580:     */       case '%': 
/* 1581:     */       case '&': 
/* 1582:     */       case '\'': 
/* 1583:     */       case ')': 
/* 1584:     */       case '*': 
/* 1585:     */       case '+': 
/* 1586:     */       case ',': 
/* 1587:     */       case '-': 
/* 1588:     */       case '.': 
/* 1589:     */       case '/': 
/* 1590:     */       case ':': 
/* 1591:     */       case ';': 
/* 1592:     */       case '<': 
/* 1593:     */       case '=': 
/* 1594:     */       case '>': 
/* 1595:     */       case '?': 
/* 1596:     */       case '@': 
/* 1597:     */       case '\\': 
/* 1598:     */       case ']': 
/* 1599:     */       case '^': 
/* 1600:     */       case '`': 
/* 1601:     */       default: 
/* 1602:1152 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1603:     */       }
/* 1604:1156 */       k = this.text.length();
/* 1605:1157 */       mAST_CTOR_ELEMENT(true);
/* 1606:1158 */       this.text.setLength(k);
/* 1607:1159 */       localToken3 = this._returnToken;
/* 1608:     */     }
/* 1609:1161 */     switch (LA(1))
/* 1610:     */     {
/* 1611:     */     case '\t': 
/* 1612:     */     case '\n': 
/* 1613:     */     case '\r': 
/* 1614:     */     case ' ': 
/* 1615:1164 */       k = this.text.length();
/* 1616:1165 */       mWS(false);
/* 1617:1166 */       this.text.setLength(k);
/* 1618:1167 */       break;
/* 1619:     */     case ',': 
/* 1620:     */     case ']': 
/* 1621:     */       break;
/* 1622:     */     default: 
/* 1623:1175 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1624:1180 */       if ((LA(1) != ',') && (LA(1) != ']')) {
/* 1625:1183 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1626:     */       }
/* 1627:     */       break;
/* 1628:     */     }
/* 1629:1188 */     switch (LA(1))
/* 1630:     */     {
/* 1631:     */     case ',': 
/* 1632:1191 */       k = this.text.length();
/* 1633:1192 */       match(',');
/* 1634:1193 */       this.text.setLength(k);
/* 1635:1195 */       switch (LA(1))
/* 1636:     */       {
/* 1637:     */       case '\t': 
/* 1638:     */       case '\n': 
/* 1639:     */       case '\r': 
/* 1640:     */       case ' ': 
/* 1641:1198 */         k = this.text.length();
/* 1642:1199 */         mWS(false);
/* 1643:1200 */         this.text.setLength(k);
/* 1644:1201 */         break;
/* 1645:     */       case '"': 
/* 1646:     */       case '#': 
/* 1647:     */       case '(': 
/* 1648:     */       case '0': 
/* 1649:     */       case '1': 
/* 1650:     */       case '2': 
/* 1651:     */       case '3': 
/* 1652:     */       case '4': 
/* 1653:     */       case '5': 
/* 1654:     */       case '6': 
/* 1655:     */       case '7': 
/* 1656:     */       case '8': 
/* 1657:     */       case '9': 
/* 1658:     */       case 'A': 
/* 1659:     */       case 'B': 
/* 1660:     */       case 'C': 
/* 1661:     */       case 'D': 
/* 1662:     */       case 'E': 
/* 1663:     */       case 'F': 
/* 1664:     */       case 'G': 
/* 1665:     */       case 'H': 
/* 1666:     */       case 'I': 
/* 1667:     */       case 'J': 
/* 1668:     */       case 'K': 
/* 1669:     */       case 'L': 
/* 1670:     */       case 'M': 
/* 1671:     */       case 'N': 
/* 1672:     */       case 'O': 
/* 1673:     */       case 'P': 
/* 1674:     */       case 'Q': 
/* 1675:     */       case 'R': 
/* 1676:     */       case 'S': 
/* 1677:     */       case 'T': 
/* 1678:     */       case 'U': 
/* 1679:     */       case 'V': 
/* 1680:     */       case 'W': 
/* 1681:     */       case 'X': 
/* 1682:     */       case 'Y': 
/* 1683:     */       case 'Z': 
/* 1684:     */       case '[': 
/* 1685:     */       case '_': 
/* 1686:     */       case 'a': 
/* 1687:     */       case 'b': 
/* 1688:     */       case 'c': 
/* 1689:     */       case 'd': 
/* 1690:     */       case 'e': 
/* 1691:     */       case 'f': 
/* 1692:     */       case 'g': 
/* 1693:     */       case 'h': 
/* 1694:     */       case 'i': 
/* 1695:     */       case 'j': 
/* 1696:     */       case 'k': 
/* 1697:     */       case 'l': 
/* 1698:     */       case 'm': 
/* 1699:     */       case 'n': 
/* 1700:     */       case 'o': 
/* 1701:     */       case 'p': 
/* 1702:     */       case 'q': 
/* 1703:     */       case 'r': 
/* 1704:     */       case 's': 
/* 1705:     */       case 't': 
/* 1706:     */       case 'u': 
/* 1707:     */       case 'v': 
/* 1708:     */       case 'w': 
/* 1709:     */       case 'x': 
/* 1710:     */       case 'y': 
/* 1711:     */       case 'z': 
/* 1712:     */         break;
/* 1713:     */       case '\013': 
/* 1714:     */       case '\f': 
/* 1715:     */       case '\016': 
/* 1716:     */       case '\017': 
/* 1717:     */       case '\020': 
/* 1718:     */       case '\021': 
/* 1719:     */       case '\022': 
/* 1720:     */       case '\023': 
/* 1721:     */       case '\024': 
/* 1722:     */       case '\025': 
/* 1723:     */       case '\026': 
/* 1724:     */       case '\027': 
/* 1725:     */       case '\030': 
/* 1726:     */       case '\031': 
/* 1727:     */       case '\032': 
/* 1728:     */       case '\033': 
/* 1729:     */       case '\034': 
/* 1730:     */       case '\035': 
/* 1731:     */       case '\036': 
/* 1732:     */       case '\037': 
/* 1733:     */       case '!': 
/* 1734:     */       case '$': 
/* 1735:     */       case '%': 
/* 1736:     */       case '&': 
/* 1737:     */       case '\'': 
/* 1738:     */       case ')': 
/* 1739:     */       case '*': 
/* 1740:     */       case '+': 
/* 1741:     */       case ',': 
/* 1742:     */       case '-': 
/* 1743:     */       case '.': 
/* 1744:     */       case '/': 
/* 1745:     */       case ':': 
/* 1746:     */       case ';': 
/* 1747:     */       case '<': 
/* 1748:     */       case '=': 
/* 1749:     */       case '>': 
/* 1750:     */       case '?': 
/* 1751:     */       case '@': 
/* 1752:     */       case '\\': 
/* 1753:     */       case ']': 
/* 1754:     */       case '^': 
/* 1755:     */       case '`': 
/* 1756:     */       default: 
/* 1757:1225 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1758:     */       }
/* 1759:1229 */       k = this.text.length();
/* 1760:1230 */       mAST_CTOR_ELEMENT(true);
/* 1761:1231 */       this.text.setLength(k);
/* 1762:1232 */       localToken4 = this._returnToken;
/* 1763:1234 */       switch (LA(1))
/* 1764:     */       {
/* 1765:     */       case '\t': 
/* 1766:     */       case '\n': 
/* 1767:     */       case '\r': 
/* 1768:     */       case ' ': 
/* 1769:1237 */         k = this.text.length();
/* 1770:1238 */         mWS(false);
/* 1771:1239 */         this.text.setLength(k);
/* 1772:1240 */         break;
/* 1773:     */       case ']': 
/* 1774:     */         break;
/* 1775:     */       default: 
/* 1776:1248 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1777:     */       }
/* 1778:     */       break;
/* 1779:     */     case ']': 
/* 1780:     */       break;
/* 1781:     */     default: 
/* 1782:1260 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1783:     */     }
/* 1784:1264 */     k = this.text.length();
/* 1785:1265 */     match(']');
/* 1786:1266 */     this.text.setLength(k);
/* 1787:     */     
/* 1788:1268 */     String str = this.generator.processStringForASTConstructor(localToken2.getText());
/* 1789:1272 */     if (localToken3 != null) {
/* 1790:1273 */       str = str + "," + localToken3.getText();
/* 1791:     */     }
/* 1792:1274 */     if (localToken4 != null) {
/* 1793:1275 */       str = str + "," + localToken4.getText();
/* 1794:     */     }
/* 1795:1277 */     this.text.setLength(j);this.text.append(this.generator.getASTCreateString(null, str));
/* 1796:1279 */     if ((paramBoolean) && (localToken1 == null) && (i != -1))
/* 1797:     */     {
/* 1798:1280 */       localToken1 = makeToken(i);
/* 1799:1281 */       localToken1.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 1800:     */     }
/* 1801:1283 */     this._returnToken = localToken1;
/* 1802:     */   }
/* 1803:     */   
/* 1804:     */   protected final void mTEXT_ARG(boolean paramBoolean)
/* 1805:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 1806:     */   {
/* 1807:1287 */     Token localToken = null;int j = this.text.length();
/* 1808:1288 */     int i = 13;
/* 1809:1292 */     switch (LA(1))
/* 1810:     */     {
/* 1811:     */     case '\t': 
/* 1812:     */     case '\n': 
/* 1813:     */     case '\r': 
/* 1814:     */     case ' ': 
/* 1815:1295 */       mWS(false);
/* 1816:1296 */       break;
/* 1817:     */     case '"': 
/* 1818:     */     case '$': 
/* 1819:     */     case '\'': 
/* 1820:     */     case '+': 
/* 1821:     */     case '0': 
/* 1822:     */     case '1': 
/* 1823:     */     case '2': 
/* 1824:     */     case '3': 
/* 1825:     */     case '4': 
/* 1826:     */     case '5': 
/* 1827:     */     case '6': 
/* 1828:     */     case '7': 
/* 1829:     */     case '8': 
/* 1830:     */     case '9': 
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
/* 1884:     */       break;
/* 1885:     */     case '\013': 
/* 1886:     */     case '\f': 
/* 1887:     */     case '\016': 
/* 1888:     */     case '\017': 
/* 1889:     */     case '\020': 
/* 1890:     */     case '\021': 
/* 1891:     */     case '\022': 
/* 1892:     */     case '\023': 
/* 1893:     */     case '\024': 
/* 1894:     */     case '\025': 
/* 1895:     */     case '\026': 
/* 1896:     */     case '\027': 
/* 1897:     */     case '\030': 
/* 1898:     */     case '\031': 
/* 1899:     */     case '\032': 
/* 1900:     */     case '\033': 
/* 1901:     */     case '\034': 
/* 1902:     */     case '\035': 
/* 1903:     */     case '\036': 
/* 1904:     */     case '\037': 
/* 1905:     */     case '!': 
/* 1906:     */     case '#': 
/* 1907:     */     case '%': 
/* 1908:     */     case '&': 
/* 1909:     */     case '(': 
/* 1910:     */     case ')': 
/* 1911:     */     case '*': 
/* 1912:     */     case ',': 
/* 1913:     */     case '-': 
/* 1914:     */     case '.': 
/* 1915:     */     case '/': 
/* 1916:     */     case ':': 
/* 1917:     */     case ';': 
/* 1918:     */     case '<': 
/* 1919:     */     case '=': 
/* 1920:     */     case '>': 
/* 1921:     */     case '?': 
/* 1922:     */     case '@': 
/* 1923:     */     case '[': 
/* 1924:     */     case '\\': 
/* 1925:     */     case ']': 
/* 1926:     */     case '^': 
/* 1927:     */     case '`': 
/* 1928:     */     default: 
/* 1929:1320 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1930:     */     }
/* 1931:1325 */     int k = 0;
/* 1932:     */     for (;;)
/* 1933:     */     {
/* 1934:1328 */       if ((_tokenSet_11.member(LA(1))) && (LA(2) >= '\003') && (LA(2) <= 'ÿ'))
/* 1935:     */       {
/* 1936:1329 */         mTEXT_ARG_ELEMENT(false);
/* 1937:1331 */         if ((_tokenSet_4.member(LA(1))) && (_tokenSet_12.member(LA(2)))) {
/* 1938:1332 */           mWS(false);
/* 1939:1334 */         } else if (!_tokenSet_12.member(LA(1))) {
/* 1940:1337 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1941:     */         }
/* 1942:     */       }
/* 1943:     */       else
/* 1944:     */       {
/* 1945:1343 */         if (k >= 1) {
/* 1946:     */           break;
/* 1947:     */         }
/* 1948:1343 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1949:     */       }
/* 1950:1346 */       k++;
/* 1951:     */     }
/* 1952:1349 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 1953:     */     {
/* 1954:1350 */       localToken = makeToken(i);
/* 1955:1351 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 1956:     */     }
/* 1957:1353 */     this._returnToken = localToken;
/* 1958:     */   }
/* 1959:     */   
/* 1960:     */   protected final void mTREE_ELEMENT(boolean paramBoolean)
/* 1961:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 1962:     */   {
/* 1963:1357 */     Token localToken1 = null;int j = this.text.length();
/* 1964:1358 */     int i = 9;
/* 1965:     */     
/* 1966:1360 */     Token localToken2 = null;
/* 1967:1363 */     switch (LA(1))
/* 1968:     */     {
/* 1969:     */     case '(': 
/* 1970:1366 */       mTREE(false);
/* 1971:1367 */       break;
/* 1972:     */     case '[': 
/* 1973:1371 */       mAST_CONSTRUCTOR(false);
/* 1974:1372 */       break;
/* 1975:     */     case 'A': 
/* 1976:     */     case 'B': 
/* 1977:     */     case 'C': 
/* 1978:     */     case 'D': 
/* 1979:     */     case 'E': 
/* 1980:     */     case 'F': 
/* 1981:     */     case 'G': 
/* 1982:     */     case 'H': 
/* 1983:     */     case 'I': 
/* 1984:     */     case 'J': 
/* 1985:     */     case 'K': 
/* 1986:     */     case 'L': 
/* 1987:     */     case 'M': 
/* 1988:     */     case 'N': 
/* 1989:     */     case 'O': 
/* 1990:     */     case 'P': 
/* 1991:     */     case 'Q': 
/* 1992:     */     case 'R': 
/* 1993:     */     case 'S': 
/* 1994:     */     case 'T': 
/* 1995:     */     case 'U': 
/* 1996:     */     case 'V': 
/* 1997:     */     case 'W': 
/* 1998:     */     case 'X': 
/* 1999:     */     case 'Y': 
/* 2000:     */     case 'Z': 
/* 2001:     */     case '_': 
/* 2002:     */     case 'a': 
/* 2003:     */     case 'b': 
/* 2004:     */     case 'c': 
/* 2005:     */     case 'd': 
/* 2006:     */     case 'e': 
/* 2007:     */     case 'f': 
/* 2008:     */     case 'g': 
/* 2009:     */     case 'h': 
/* 2010:     */     case 'i': 
/* 2011:     */     case 'j': 
/* 2012:     */     case 'k': 
/* 2013:     */     case 'l': 
/* 2014:     */     case 'm': 
/* 2015:     */     case 'n': 
/* 2016:     */     case 'o': 
/* 2017:     */     case 'p': 
/* 2018:     */     case 'q': 
/* 2019:     */     case 'r': 
/* 2020:     */     case 's': 
/* 2021:     */     case 't': 
/* 2022:     */     case 'u': 
/* 2023:     */     case 'v': 
/* 2024:     */     case 'w': 
/* 2025:     */     case 'x': 
/* 2026:     */     case 'y': 
/* 2027:     */     case 'z': 
/* 2028:1389 */       mID_ELEMENT(false);
/* 2029:1390 */       break;
/* 2030:     */     case '"': 
/* 2031:1394 */       mSTRING(false);
/* 2032:1395 */       break;
/* 2033:     */     case '#': 
/* 2034:     */     case '$': 
/* 2035:     */     case '%': 
/* 2036:     */     case '&': 
/* 2037:     */     case '\'': 
/* 2038:     */     case ')': 
/* 2039:     */     case '*': 
/* 2040:     */     case '+': 
/* 2041:     */     case ',': 
/* 2042:     */     case '-': 
/* 2043:     */     case '.': 
/* 2044:     */     case '/': 
/* 2045:     */     case '0': 
/* 2046:     */     case '1': 
/* 2047:     */     case '2': 
/* 2048:     */     case '3': 
/* 2049:     */     case '4': 
/* 2050:     */     case '5': 
/* 2051:     */     case '6': 
/* 2052:     */     case '7': 
/* 2053:     */     case '8': 
/* 2054:     */     case '9': 
/* 2055:     */     case ':': 
/* 2056:     */     case ';': 
/* 2057:     */     case '<': 
/* 2058:     */     case '=': 
/* 2059:     */     case '>': 
/* 2060:     */     case '?': 
/* 2061:     */     case '@': 
/* 2062:     */     case '\\': 
/* 2063:     */     case ']': 
/* 2064:     */     case '^': 
/* 2065:     */     case '`': 
/* 2066:     */     default: 
/* 2067:     */       int k;
/* 2068:1398 */       if ((LA(1) == '#') && (LA(2) == '('))
/* 2069:     */       {
/* 2070:1399 */         k = this.text.length();
/* 2071:1400 */         match('#');
/* 2072:1401 */         this.text.setLength(k);
/* 2073:1402 */         mTREE(false);
/* 2074:     */       }
/* 2075:1404 */       else if ((LA(1) == '#') && (LA(2) == '['))
/* 2076:     */       {
/* 2077:1405 */         k = this.text.length();
/* 2078:1406 */         match('#');
/* 2079:1407 */         this.text.setLength(k);
/* 2080:1408 */         mAST_CONSTRUCTOR(false);
/* 2081:     */       }
/* 2082:     */       else
/* 2083:     */       {
/* 2084:     */         String str;
/* 2085:1410 */         if ((LA(1) == '#') && (_tokenSet_13.member(LA(2))))
/* 2086:     */         {
/* 2087:1411 */           k = this.text.length();
/* 2088:1412 */           match('#');
/* 2089:1413 */           this.text.setLength(k);
/* 2090:1414 */           boolean bool = mID_ELEMENT(true);
/* 2091:1415 */           localToken2 = this._returnToken;
/* 2092:1417 */           if (!bool)
/* 2093:     */           {
/* 2094:1419 */             str = this.generator.mapTreeId(localToken2.getText(), null);
/* 2095:1420 */             if (str != null)
/* 2096:     */             {
/* 2097:1421 */               this.text.setLength(j);this.text.append(str);
/* 2098:     */             }
/* 2099:     */           }
/* 2100:     */         }
/* 2101:1426 */         else if ((LA(1) == '#') && (LA(2) == '#'))
/* 2102:     */         {
/* 2103:1427 */           match("##");
/* 2104:1429 */           if (this.currentRule != null)
/* 2105:     */           {
/* 2106:1431 */             str = this.currentRule.getRuleName() + "_AST";
/* 2107:1432 */             this.text.setLength(j);this.text.append(str);
/* 2108:     */           }
/* 2109:     */           else
/* 2110:     */           {
/* 2111:1436 */             reportError("\"##\" not valid in this context");
/* 2112:1437 */             this.text.setLength(j);this.text.append("##");
/* 2113:     */           }
/* 2114:     */         }
/* 2115:     */         else
/* 2116:     */         {
/* 2117:1442 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2118:     */         }
/* 2119:     */       }
/* 2120:     */       break;
/* 2121:     */     }
/* 2122:1445 */     if ((paramBoolean) && (localToken1 == null) && (i != -1))
/* 2123:     */     {
/* 2124:1446 */       localToken1 = makeToken(i);
/* 2125:1447 */       localToken1.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 2126:     */     }
/* 2127:1449 */     this._returnToken = localToken1;
/* 2128:     */   }
/* 2129:     */   
/* 2130:     */   protected final boolean mID_ELEMENT(boolean paramBoolean)
/* 2131:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 2132:     */   {
/* 2133:1456 */     boolean bool = false;
/* 2134:1457 */     Token localToken1 = null;int j = this.text.length();
/* 2135:1458 */     int i = 12;
/* 2136:     */     
/* 2137:1460 */     Token localToken2 = null;
/* 2138:     */     
/* 2139:1462 */     mID(true);
/* 2140:1463 */     localToken2 = this._returnToken;
/* 2141:     */     int k;
/* 2142:1465 */     if ((_tokenSet_4.member(LA(1))) && (_tokenSet_14.member(LA(2))))
/* 2143:     */     {
/* 2144:1466 */       k = this.text.length();
/* 2145:1467 */       mWS(false);
/* 2146:1468 */       this.text.setLength(k);
/* 2147:     */     }
/* 2148:1470 */     else if (!_tokenSet_14.member(LA(1)))
/* 2149:     */     {
/* 2150:1473 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2151:     */     }
/* 2152:1478 */     switch (LA(1))
/* 2153:     */     {
/* 2154:     */     case '(': 
/* 2155:1481 */       match('(');
/* 2156:1483 */       if ((_tokenSet_4.member(LA(1))) && (_tokenSet_15.member(LA(2))) && (LA(3) >= '\003') && (LA(3) <= 'ÿ'))
/* 2157:     */       {
/* 2158:1484 */         k = this.text.length();
/* 2159:1485 */         mWS(false);
/* 2160:1486 */         this.text.setLength(k);
/* 2161:     */       }
/* 2162:1488 */       else if ((!_tokenSet_15.member(LA(1))) || (LA(2) < '\003') || (LA(2) > 'ÿ'))
/* 2163:     */       {
/* 2164:1491 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2165:     */       }
/* 2166:1496 */       switch (LA(1))
/* 2167:     */       {
/* 2168:     */       case '"': 
/* 2169:     */       case '#': 
/* 2170:     */       case '\'': 
/* 2171:     */       case '(': 
/* 2172:     */       case '0': 
/* 2173:     */       case '1': 
/* 2174:     */       case '2': 
/* 2175:     */       case '3': 
/* 2176:     */       case '4': 
/* 2177:     */       case '5': 
/* 2178:     */       case '6': 
/* 2179:     */       case '7': 
/* 2180:     */       case '8': 
/* 2181:     */       case '9': 
/* 2182:     */       case 'A': 
/* 2183:     */       case 'B': 
/* 2184:     */       case 'C': 
/* 2185:     */       case 'D': 
/* 2186:     */       case 'E': 
/* 2187:     */       case 'F': 
/* 2188:     */       case 'G': 
/* 2189:     */       case 'H': 
/* 2190:     */       case 'I': 
/* 2191:     */       case 'J': 
/* 2192:     */       case 'K': 
/* 2193:     */       case 'L': 
/* 2194:     */       case 'M': 
/* 2195:     */       case 'N': 
/* 2196:     */       case 'O': 
/* 2197:     */       case 'P': 
/* 2198:     */       case 'Q': 
/* 2199:     */       case 'R': 
/* 2200:     */       case 'S': 
/* 2201:     */       case 'T': 
/* 2202:     */       case 'U': 
/* 2203:     */       case 'V': 
/* 2204:     */       case 'W': 
/* 2205:     */       case 'X': 
/* 2206:     */       case 'Y': 
/* 2207:     */       case 'Z': 
/* 2208:     */       case '[': 
/* 2209:     */       case '_': 
/* 2210:     */       case 'a': 
/* 2211:     */       case 'b': 
/* 2212:     */       case 'c': 
/* 2213:     */       case 'd': 
/* 2214:     */       case 'e': 
/* 2215:     */       case 'f': 
/* 2216:     */       case 'g': 
/* 2217:     */       case 'h': 
/* 2218:     */       case 'i': 
/* 2219:     */       case 'j': 
/* 2220:     */       case 'k': 
/* 2221:     */       case 'l': 
/* 2222:     */       case 'm': 
/* 2223:     */       case 'n': 
/* 2224:     */       case 'o': 
/* 2225:     */       case 'p': 
/* 2226:     */       case 'q': 
/* 2227:     */       case 'r': 
/* 2228:     */       case 's': 
/* 2229:     */       case 't': 
/* 2230:     */       case 'u': 
/* 2231:     */       case 'v': 
/* 2232:     */       case 'w': 
/* 2233:     */       case 'x': 
/* 2234:     */       case 'y': 
/* 2235:     */       case 'z': 
/* 2236:1515 */         mARG(false);
/* 2237:     */       }
/* 2238:1519 */       while (LA(1) == ',')
/* 2239:     */       {
/* 2240:1520 */         match(',');
/* 2241:1522 */         switch (LA(1))
/* 2242:     */         {
/* 2243:     */         case '\t': 
/* 2244:     */         case '\n': 
/* 2245:     */         case '\r': 
/* 2246:     */         case ' ': 
/* 2247:1525 */           k = this.text.length();
/* 2248:1526 */           mWS(false);
/* 2249:1527 */           this.text.setLength(k);
/* 2250:1528 */           break;
/* 2251:     */         case '"': 
/* 2252:     */         case '#': 
/* 2253:     */         case '\'': 
/* 2254:     */         case '(': 
/* 2255:     */         case '0': 
/* 2256:     */         case '1': 
/* 2257:     */         case '2': 
/* 2258:     */         case '3': 
/* 2259:     */         case '4': 
/* 2260:     */         case '5': 
/* 2261:     */         case '6': 
/* 2262:     */         case '7': 
/* 2263:     */         case '8': 
/* 2264:     */         case '9': 
/* 2265:     */         case 'A': 
/* 2266:     */         case 'B': 
/* 2267:     */         case 'C': 
/* 2268:     */         case 'D': 
/* 2269:     */         case 'E': 
/* 2270:     */         case 'F': 
/* 2271:     */         case 'G': 
/* 2272:     */         case 'H': 
/* 2273:     */         case 'I': 
/* 2274:     */         case 'J': 
/* 2275:     */         case 'K': 
/* 2276:     */         case 'L': 
/* 2277:     */         case 'M': 
/* 2278:     */         case 'N': 
/* 2279:     */         case 'O': 
/* 2280:     */         case 'P': 
/* 2281:     */         case 'Q': 
/* 2282:     */         case 'R': 
/* 2283:     */         case 'S': 
/* 2284:     */         case 'T': 
/* 2285:     */         case 'U': 
/* 2286:     */         case 'V': 
/* 2287:     */         case 'W': 
/* 2288:     */         case 'X': 
/* 2289:     */         case 'Y': 
/* 2290:     */         case 'Z': 
/* 2291:     */         case '[': 
/* 2292:     */         case '_': 
/* 2293:     */         case 'a': 
/* 2294:     */         case 'b': 
/* 2295:     */         case 'c': 
/* 2296:     */         case 'd': 
/* 2297:     */         case 'e': 
/* 2298:     */         case 'f': 
/* 2299:     */         case 'g': 
/* 2300:     */         case 'h': 
/* 2301:     */         case 'i': 
/* 2302:     */         case 'j': 
/* 2303:     */         case 'k': 
/* 2304:     */         case 'l': 
/* 2305:     */         case 'm': 
/* 2306:     */         case 'n': 
/* 2307:     */         case 'o': 
/* 2308:     */         case 'p': 
/* 2309:     */         case 'q': 
/* 2310:     */         case 'r': 
/* 2311:     */         case 's': 
/* 2312:     */         case 't': 
/* 2313:     */         case 'u': 
/* 2314:     */         case 'v': 
/* 2315:     */         case 'w': 
/* 2316:     */         case 'x': 
/* 2317:     */         case 'y': 
/* 2318:     */         case 'z': 
/* 2319:     */           break;
/* 2320:     */         case '\013': 
/* 2321:     */         case '\f': 
/* 2322:     */         case '\016': 
/* 2323:     */         case '\017': 
/* 2324:     */         case '\020': 
/* 2325:     */         case '\021': 
/* 2326:     */         case '\022': 
/* 2327:     */         case '\023': 
/* 2328:     */         case '\024': 
/* 2329:     */         case '\025': 
/* 2330:     */         case '\026': 
/* 2331:     */         case '\027': 
/* 2332:     */         case '\030': 
/* 2333:     */         case '\031': 
/* 2334:     */         case '\032': 
/* 2335:     */         case '\033': 
/* 2336:     */         case '\034': 
/* 2337:     */         case '\035': 
/* 2338:     */         case '\036': 
/* 2339:     */         case '\037': 
/* 2340:     */         case '!': 
/* 2341:     */         case '$': 
/* 2342:     */         case '%': 
/* 2343:     */         case '&': 
/* 2344:     */         case ')': 
/* 2345:     */         case '*': 
/* 2346:     */         case '+': 
/* 2347:     */         case ',': 
/* 2348:     */         case '-': 
/* 2349:     */         case '.': 
/* 2350:     */         case '/': 
/* 2351:     */         case ':': 
/* 2352:     */         case ';': 
/* 2353:     */         case '<': 
/* 2354:     */         case '=': 
/* 2355:     */         case '>': 
/* 2356:     */         case '?': 
/* 2357:     */         case '@': 
/* 2358:     */         case '\\': 
/* 2359:     */         case ']': 
/* 2360:     */         case '^': 
/* 2361:     */         case '`': 
/* 2362:     */         default: 
/* 2363:1552 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2364:     */         }
/* 2365:1556 */         mARG(false); continue;
/* 2366:     */         
/* 2367:     */ 
/* 2368:     */ 
/* 2369:     */ 
/* 2370:     */ 
/* 2371:     */ 
/* 2372:     */ 
/* 2373:     */ 
/* 2374:     */ 
/* 2375:     */ 
/* 2376:     */ 
/* 2377:     */ 
/* 2378:1569 */         break;
/* 2379:     */         
/* 2380:     */ 
/* 2381:     */ 
/* 2382:1573 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2383:     */       }
/* 2384:1578 */       switch (LA(1))
/* 2385:     */       {
/* 2386:     */       case '\t': 
/* 2387:     */       case '\n': 
/* 2388:     */       case '\r': 
/* 2389:     */       case ' ': 
/* 2390:1581 */         k = this.text.length();
/* 2391:1582 */         mWS(false);
/* 2392:1583 */         this.text.setLength(k);
/* 2393:1584 */         break;
/* 2394:     */       case ')': 
/* 2395:     */         break;
/* 2396:     */       default: 
/* 2397:1592 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2398:     */       }
/* 2399:1596 */       match(')');
/* 2400:1597 */       break;
/* 2401:     */     case '[': 
/* 2402:1602 */       int m = 0;
/* 2403:     */       for (;;)
/* 2404:     */       {
/* 2405:1605 */         if (LA(1) == '[')
/* 2406:     */         {
/* 2407:1606 */           match('[');
/* 2408:1608 */           switch (LA(1))
/* 2409:     */           {
/* 2410:     */           case '\t': 
/* 2411:     */           case '\n': 
/* 2412:     */           case '\r': 
/* 2413:     */           case ' ': 
/* 2414:1611 */             k = this.text.length();
/* 2415:1612 */             mWS(false);
/* 2416:1613 */             this.text.setLength(k);
/* 2417:1614 */             break;
/* 2418:     */           case '"': 
/* 2419:     */           case '#': 
/* 2420:     */           case '\'': 
/* 2421:     */           case '(': 
/* 2422:     */           case '0': 
/* 2423:     */           case '1': 
/* 2424:     */           case '2': 
/* 2425:     */           case '3': 
/* 2426:     */           case '4': 
/* 2427:     */           case '5': 
/* 2428:     */           case '6': 
/* 2429:     */           case '7': 
/* 2430:     */           case '8': 
/* 2431:     */           case '9': 
/* 2432:     */           case 'A': 
/* 2433:     */           case 'B': 
/* 2434:     */           case 'C': 
/* 2435:     */           case 'D': 
/* 2436:     */           case 'E': 
/* 2437:     */           case 'F': 
/* 2438:     */           case 'G': 
/* 2439:     */           case 'H': 
/* 2440:     */           case 'I': 
/* 2441:     */           case 'J': 
/* 2442:     */           case 'K': 
/* 2443:     */           case 'L': 
/* 2444:     */           case 'M': 
/* 2445:     */           case 'N': 
/* 2446:     */           case 'O': 
/* 2447:     */           case 'P': 
/* 2448:     */           case 'Q': 
/* 2449:     */           case 'R': 
/* 2450:     */           case 'S': 
/* 2451:     */           case 'T': 
/* 2452:     */           case 'U': 
/* 2453:     */           case 'V': 
/* 2454:     */           case 'W': 
/* 2455:     */           case 'X': 
/* 2456:     */           case 'Y': 
/* 2457:     */           case 'Z': 
/* 2458:     */           case '[': 
/* 2459:     */           case '_': 
/* 2460:     */           case 'a': 
/* 2461:     */           case 'b': 
/* 2462:     */           case 'c': 
/* 2463:     */           case 'd': 
/* 2464:     */           case 'e': 
/* 2465:     */           case 'f': 
/* 2466:     */           case 'g': 
/* 2467:     */           case 'h': 
/* 2468:     */           case 'i': 
/* 2469:     */           case 'j': 
/* 2470:     */           case 'k': 
/* 2471:     */           case 'l': 
/* 2472:     */           case 'm': 
/* 2473:     */           case 'n': 
/* 2474:     */           case 'o': 
/* 2475:     */           case 'p': 
/* 2476:     */           case 'q': 
/* 2477:     */           case 'r': 
/* 2478:     */           case 's': 
/* 2479:     */           case 't': 
/* 2480:     */           case 'u': 
/* 2481:     */           case 'v': 
/* 2482:     */           case 'w': 
/* 2483:     */           case 'x': 
/* 2484:     */           case 'y': 
/* 2485:     */           case 'z': 
/* 2486:     */             break;
/* 2487:     */           case '\013': 
/* 2488:     */           case '\f': 
/* 2489:     */           case '\016': 
/* 2490:     */           case '\017': 
/* 2491:     */           case '\020': 
/* 2492:     */           case '\021': 
/* 2493:     */           case '\022': 
/* 2494:     */           case '\023': 
/* 2495:     */           case '\024': 
/* 2496:     */           case '\025': 
/* 2497:     */           case '\026': 
/* 2498:     */           case '\027': 
/* 2499:     */           case '\030': 
/* 2500:     */           case '\031': 
/* 2501:     */           case '\032': 
/* 2502:     */           case '\033': 
/* 2503:     */           case '\034': 
/* 2504:     */           case '\035': 
/* 2505:     */           case '\036': 
/* 2506:     */           case '\037': 
/* 2507:     */           case '!': 
/* 2508:     */           case '$': 
/* 2509:     */           case '%': 
/* 2510:     */           case '&': 
/* 2511:     */           case ')': 
/* 2512:     */           case '*': 
/* 2513:     */           case '+': 
/* 2514:     */           case ',': 
/* 2515:     */           case '-': 
/* 2516:     */           case '.': 
/* 2517:     */           case '/': 
/* 2518:     */           case ':': 
/* 2519:     */           case ';': 
/* 2520:     */           case '<': 
/* 2521:     */           case '=': 
/* 2522:     */           case '>': 
/* 2523:     */           case '?': 
/* 2524:     */           case '@': 
/* 2525:     */           case '\\': 
/* 2526:     */           case ']': 
/* 2527:     */           case '^': 
/* 2528:     */           case '`': 
/* 2529:     */           default: 
/* 2530:1638 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2531:     */           }
/* 2532:1642 */           mARG(false);
/* 2533:1644 */           switch (LA(1))
/* 2534:     */           {
/* 2535:     */           case '\t': 
/* 2536:     */           case '\n': 
/* 2537:     */           case '\r': 
/* 2538:     */           case ' ': 
/* 2539:1647 */             k = this.text.length();
/* 2540:1648 */             mWS(false);
/* 2541:1649 */             this.text.setLength(k);
/* 2542:1650 */             break;
/* 2543:     */           case ']': 
/* 2544:     */             break;
/* 2545:     */           default: 
/* 2546:1658 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2547:     */           }
/* 2548:1662 */           match(']');
/* 2549:     */         }
/* 2550:     */         else
/* 2551:     */         {
/* 2552:1665 */           if (m >= 1) {
/* 2553:     */             break;
/* 2554:     */           }
/* 2555:1665 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2556:     */         }
/* 2557:1668 */         m++;
/* 2558:     */       }
/* 2559:     */     case '.': 
/* 2560:1675 */       match('.');
/* 2561:1676 */       mID_ELEMENT(false);
/* 2562:1677 */       break;
/* 2563:     */     default: 
/* 2564:1680 */       if ((LA(1) == '-') && (LA(2) == '>') && (_tokenSet_13.member(LA(3))))
/* 2565:     */       {
/* 2566:1681 */         match("->");
/* 2567:1682 */         mID_ELEMENT(false);
/* 2568:     */       }
/* 2569:1684 */       else if (_tokenSet_16.member(LA(1)))
/* 2570:     */       {
/* 2571:1686 */         bool = true;
/* 2572:1687 */         String str = this.generator.mapTreeId(localToken2.getText(), this.transInfo);
/* 2573:1689 */         if (str != null)
/* 2574:     */         {
/* 2575:1690 */           this.text.setLength(j);this.text.append(str);
/* 2576:     */         }
/* 2577:1694 */         if ((_tokenSet_17.member(LA(1))) && (_tokenSet_16.member(LA(2))) && (this.transInfo != null) && (this.transInfo.refRuleRoot != null))
/* 2578:     */         {
/* 2579:1696 */           switch (LA(1))
/* 2580:     */           {
/* 2581:     */           case '\t': 
/* 2582:     */           case '\n': 
/* 2583:     */           case '\r': 
/* 2584:     */           case ' ': 
/* 2585:1699 */             mWS(false);
/* 2586:1700 */             break;
/* 2587:     */           case '=': 
/* 2588:     */             break;
/* 2589:     */           default: 
/* 2590:1708 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2591:     */           }
/* 2592:1712 */           mVAR_ASSIGN(false);
/* 2593:     */         }
/* 2594:1714 */         else if (!_tokenSet_18.member(LA(1)))
/* 2595:     */         {
/* 2596:1717 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2597:     */         }
/* 2598:     */       }
/* 2599:     */       else
/* 2600:     */       {
/* 2601:1723 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2602:     */       }
/* 2603:     */       break;
/* 2604:     */     }
/* 2605:1727 */     if ((paramBoolean) && (localToken1 == null) && (i != -1))
/* 2606:     */     {
/* 2607:1728 */       localToken1 = makeToken(i);
/* 2608:1729 */       localToken1.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 2609:     */     }
/* 2610:1731 */     this._returnToken = localToken1;
/* 2611:1732 */     return bool;
/* 2612:     */   }
/* 2613:     */   
/* 2614:     */   protected final void mAST_CTOR_ELEMENT(boolean paramBoolean)
/* 2615:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 2616:     */   {
/* 2617:1739 */     Token localToken = null;int j = this.text.length();
/* 2618:1740 */     int i = 11;
/* 2619:1743 */     if ((LA(1) == '"') && (LA(2) >= '\003') && (LA(2) <= 'ÿ') && (LA(3) >= '\003') && (LA(3) <= 'ÿ')) {
/* 2620:1744 */       mSTRING(false);
/* 2621:1746 */     } else if ((_tokenSet_19.member(LA(1))) && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 2622:1747 */       mTREE_ELEMENT(false);
/* 2623:1749 */     } else if ((LA(1) >= '0') && (LA(1) <= '9')) {
/* 2624:1750 */       mINT(false);
/* 2625:     */     } else {
/* 2626:1753 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2627:     */     }
/* 2628:1756 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 2629:     */     {
/* 2630:1757 */       localToken = makeToken(i);
/* 2631:1758 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 2632:     */     }
/* 2633:1760 */     this._returnToken = localToken;
/* 2634:     */   }
/* 2635:     */   
/* 2636:     */   protected final void mINT(boolean paramBoolean)
/* 2637:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 2638:     */   {
/* 2639:1764 */     Token localToken = null;int j = this.text.length();
/* 2640:1765 */     int i = 26;
/* 2641:     */     
/* 2642:     */ 
/* 2643:     */ 
/* 2644:1769 */     int k = 0;
/* 2645:     */     for (;;)
/* 2646:     */     {
/* 2647:1772 */       if ((LA(1) >= '0') && (LA(1) <= '9'))
/* 2648:     */       {
/* 2649:1773 */         mDIGIT(false);
/* 2650:     */       }
/* 2651:     */       else
/* 2652:     */       {
/* 2653:1776 */         if (k >= 1) {
/* 2654:     */           break;
/* 2655:     */         }
/* 2656:1776 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2657:     */       }
/* 2658:1779 */       k++;
/* 2659:     */     }
/* 2660:1782 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 2661:     */     {
/* 2662:1783 */       localToken = makeToken(i);
/* 2663:1784 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 2664:     */     }
/* 2665:1786 */     this._returnToken = localToken;
/* 2666:     */   }
/* 2667:     */   
/* 2668:     */   protected final void mARG(boolean paramBoolean)
/* 2669:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 2670:     */   {
/* 2671:1790 */     Token localToken = null;int j = this.text.length();
/* 2672:1791 */     int i = 16;
/* 2673:1795 */     switch (LA(1))
/* 2674:     */     {
/* 2675:     */     case '\'': 
/* 2676:1798 */       mCHAR(false);
/* 2677:1799 */       break;
/* 2678:     */     case '0': 
/* 2679:     */     case '1': 
/* 2680:     */     case '2': 
/* 2681:     */     case '3': 
/* 2682:     */     case '4': 
/* 2683:     */     case '5': 
/* 2684:     */     case '6': 
/* 2685:     */     case '7': 
/* 2686:     */     case '8': 
/* 2687:     */     case '9': 
/* 2688:1805 */       mINT_OR_FLOAT(false);
/* 2689:1806 */       break;
/* 2690:     */     case '(': 
/* 2691:     */     case ')': 
/* 2692:     */     case '*': 
/* 2693:     */     case '+': 
/* 2694:     */     case ',': 
/* 2695:     */     case '-': 
/* 2696:     */     case '.': 
/* 2697:     */     case '/': 
/* 2698:     */     default: 
/* 2699:1809 */       if ((_tokenSet_19.member(LA(1))) && (LA(2) >= '\003') && (LA(2) <= 'ÿ') && (LA(3) >= '\003') && (LA(3) <= 'ÿ')) {
/* 2700:1810 */         mTREE_ELEMENT(false);
/* 2701:1812 */       } else if ((LA(1) == '"') && (LA(2) >= '\003') && (LA(2) <= 'ÿ') && (LA(3) >= '\003') && (LA(3) <= 'ÿ')) {
/* 2702:1813 */         mSTRING(false);
/* 2703:     */       } else {
/* 2704:1816 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2705:     */       }
/* 2706:     */       break;
/* 2707:     */     }
/* 2708:1823 */     while ((_tokenSet_20.member(LA(1))) && (_tokenSet_21.member(LA(2))) && (LA(3) >= '\003') && (LA(3) <= 'ÿ'))
/* 2709:     */     {
/* 2710:1825 */       switch (LA(1))
/* 2711:     */       {
/* 2712:     */       case '\t': 
/* 2713:     */       case '\n': 
/* 2714:     */       case '\r': 
/* 2715:     */       case ' ': 
/* 2716:1828 */         mWS(false);
/* 2717:1829 */         break;
/* 2718:     */       case '*': 
/* 2719:     */       case '+': 
/* 2720:     */       case '-': 
/* 2721:     */       case '/': 
/* 2722:     */         break;
/* 2723:     */       default: 
/* 2724:1837 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2725:     */       }
/* 2726:1842 */       switch (LA(1))
/* 2727:     */       {
/* 2728:     */       case '+': 
/* 2729:1845 */         match('+');
/* 2730:1846 */         break;
/* 2731:     */       case '-': 
/* 2732:1850 */         match('-');
/* 2733:1851 */         break;
/* 2734:     */       case '*': 
/* 2735:1855 */         match('*');
/* 2736:1856 */         break;
/* 2737:     */       case '/': 
/* 2738:1860 */         match('/');
/* 2739:1861 */         break;
/* 2740:     */       case ',': 
/* 2741:     */       case '.': 
/* 2742:     */       default: 
/* 2743:1865 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2744:     */       }
/* 2745:1870 */       switch (LA(1))
/* 2746:     */       {
/* 2747:     */       case '\t': 
/* 2748:     */       case '\n': 
/* 2749:     */       case '\r': 
/* 2750:     */       case ' ': 
/* 2751:1873 */         mWS(false);
/* 2752:1874 */         break;
/* 2753:     */       case '"': 
/* 2754:     */       case '#': 
/* 2755:     */       case '\'': 
/* 2756:     */       case '(': 
/* 2757:     */       case '0': 
/* 2758:     */       case '1': 
/* 2759:     */       case '2': 
/* 2760:     */       case '3': 
/* 2761:     */       case '4': 
/* 2762:     */       case '5': 
/* 2763:     */       case '6': 
/* 2764:     */       case '7': 
/* 2765:     */       case '8': 
/* 2766:     */       case '9': 
/* 2767:     */       case 'A': 
/* 2768:     */       case 'B': 
/* 2769:     */       case 'C': 
/* 2770:     */       case 'D': 
/* 2771:     */       case 'E': 
/* 2772:     */       case 'F': 
/* 2773:     */       case 'G': 
/* 2774:     */       case 'H': 
/* 2775:     */       case 'I': 
/* 2776:     */       case 'J': 
/* 2777:     */       case 'K': 
/* 2778:     */       case 'L': 
/* 2779:     */       case 'M': 
/* 2780:     */       case 'N': 
/* 2781:     */       case 'O': 
/* 2782:     */       case 'P': 
/* 2783:     */       case 'Q': 
/* 2784:     */       case 'R': 
/* 2785:     */       case 'S': 
/* 2786:     */       case 'T': 
/* 2787:     */       case 'U': 
/* 2788:     */       case 'V': 
/* 2789:     */       case 'W': 
/* 2790:     */       case 'X': 
/* 2791:     */       case 'Y': 
/* 2792:     */       case 'Z': 
/* 2793:     */       case '[': 
/* 2794:     */       case '_': 
/* 2795:     */       case 'a': 
/* 2796:     */       case 'b': 
/* 2797:     */       case 'c': 
/* 2798:     */       case 'd': 
/* 2799:     */       case 'e': 
/* 2800:     */       case 'f': 
/* 2801:     */       case 'g': 
/* 2802:     */       case 'h': 
/* 2803:     */       case 'i': 
/* 2804:     */       case 'j': 
/* 2805:     */       case 'k': 
/* 2806:     */       case 'l': 
/* 2807:     */       case 'm': 
/* 2808:     */       case 'n': 
/* 2809:     */       case 'o': 
/* 2810:     */       case 'p': 
/* 2811:     */       case 'q': 
/* 2812:     */       case 'r': 
/* 2813:     */       case 's': 
/* 2814:     */       case 't': 
/* 2815:     */       case 'u': 
/* 2816:     */       case 'v': 
/* 2817:     */       case 'w': 
/* 2818:     */       case 'x': 
/* 2819:     */       case 'y': 
/* 2820:     */       case 'z': 
/* 2821:     */         break;
/* 2822:     */       case '\013': 
/* 2823:     */       case '\f': 
/* 2824:     */       case '\016': 
/* 2825:     */       case '\017': 
/* 2826:     */       case '\020': 
/* 2827:     */       case '\021': 
/* 2828:     */       case '\022': 
/* 2829:     */       case '\023': 
/* 2830:     */       case '\024': 
/* 2831:     */       case '\025': 
/* 2832:     */       case '\026': 
/* 2833:     */       case '\027': 
/* 2834:     */       case '\030': 
/* 2835:     */       case '\031': 
/* 2836:     */       case '\032': 
/* 2837:     */       case '\033': 
/* 2838:     */       case '\034': 
/* 2839:     */       case '\035': 
/* 2840:     */       case '\036': 
/* 2841:     */       case '\037': 
/* 2842:     */       case '!': 
/* 2843:     */       case '$': 
/* 2844:     */       case '%': 
/* 2845:     */       case '&': 
/* 2846:     */       case ')': 
/* 2847:     */       case '*': 
/* 2848:     */       case '+': 
/* 2849:     */       case ',': 
/* 2850:     */       case '-': 
/* 2851:     */       case '.': 
/* 2852:     */       case '/': 
/* 2853:     */       case ':': 
/* 2854:     */       case ';': 
/* 2855:     */       case '<': 
/* 2856:     */       case '=': 
/* 2857:     */       case '>': 
/* 2858:     */       case '?': 
/* 2859:     */       case '@': 
/* 2860:     */       case '\\': 
/* 2861:     */       case ']': 
/* 2862:     */       case '^': 
/* 2863:     */       case '`': 
/* 2864:     */       default: 
/* 2865:1898 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2866:     */       }
/* 2867:1902 */       mARG(false);
/* 2868:     */     }
/* 2869:1910 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 2870:     */     {
/* 2871:1911 */       localToken = makeToken(i);
/* 2872:1912 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 2873:     */     }
/* 2874:1914 */     this._returnToken = localToken;
/* 2875:     */   }
/* 2876:     */   
/* 2877:     */   protected final void mTEXT_ARG_ELEMENT(boolean paramBoolean)
/* 2878:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 2879:     */   {
/* 2880:1918 */     Token localToken = null;int j = this.text.length();
/* 2881:1919 */     int i = 14;
/* 2882:1922 */     switch (LA(1))
/* 2883:     */     {
/* 2884:     */     case 'A': 
/* 2885:     */     case 'B': 
/* 2886:     */     case 'C': 
/* 2887:     */     case 'D': 
/* 2888:     */     case 'E': 
/* 2889:     */     case 'F': 
/* 2890:     */     case 'G': 
/* 2891:     */     case 'H': 
/* 2892:     */     case 'I': 
/* 2893:     */     case 'J': 
/* 2894:     */     case 'K': 
/* 2895:     */     case 'L': 
/* 2896:     */     case 'M': 
/* 2897:     */     case 'N': 
/* 2898:     */     case 'O': 
/* 2899:     */     case 'P': 
/* 2900:     */     case 'Q': 
/* 2901:     */     case 'R': 
/* 2902:     */     case 'S': 
/* 2903:     */     case 'T': 
/* 2904:     */     case 'U': 
/* 2905:     */     case 'V': 
/* 2906:     */     case 'W': 
/* 2907:     */     case 'X': 
/* 2908:     */     case 'Y': 
/* 2909:     */     case 'Z': 
/* 2910:     */     case '_': 
/* 2911:     */     case 'a': 
/* 2912:     */     case 'b': 
/* 2913:     */     case 'c': 
/* 2914:     */     case 'd': 
/* 2915:     */     case 'e': 
/* 2916:     */     case 'f': 
/* 2917:     */     case 'g': 
/* 2918:     */     case 'h': 
/* 2919:     */     case 'i': 
/* 2920:     */     case 'j': 
/* 2921:     */     case 'k': 
/* 2922:     */     case 'l': 
/* 2923:     */     case 'm': 
/* 2924:     */     case 'n': 
/* 2925:     */     case 'o': 
/* 2926:     */     case 'p': 
/* 2927:     */     case 'q': 
/* 2928:     */     case 'r': 
/* 2929:     */     case 's': 
/* 2930:     */     case 't': 
/* 2931:     */     case 'u': 
/* 2932:     */     case 'v': 
/* 2933:     */     case 'w': 
/* 2934:     */     case 'x': 
/* 2935:     */     case 'y': 
/* 2936:     */     case 'z': 
/* 2937:1938 */       mTEXT_ARG_ID_ELEMENT(false);
/* 2938:1939 */       break;
/* 2939:     */     case '"': 
/* 2940:1943 */       mSTRING(false);
/* 2941:1944 */       break;
/* 2942:     */     case '\'': 
/* 2943:1948 */       mCHAR(false);
/* 2944:1949 */       break;
/* 2945:     */     case '0': 
/* 2946:     */     case '1': 
/* 2947:     */     case '2': 
/* 2948:     */     case '3': 
/* 2949:     */     case '4': 
/* 2950:     */     case '5': 
/* 2951:     */     case '6': 
/* 2952:     */     case '7': 
/* 2953:     */     case '8': 
/* 2954:     */     case '9': 
/* 2955:1955 */       mINT_OR_FLOAT(false);
/* 2956:1956 */       break;
/* 2957:     */     case '$': 
/* 2958:1960 */       mTEXT_ITEM(false);
/* 2959:1961 */       break;
/* 2960:     */     case '+': 
/* 2961:1965 */       match('+');
/* 2962:1966 */       break;
/* 2963:     */     case '#': 
/* 2964:     */     case '%': 
/* 2965:     */     case '&': 
/* 2966:     */     case '(': 
/* 2967:     */     case ')': 
/* 2968:     */     case '*': 
/* 2969:     */     case ',': 
/* 2970:     */     case '-': 
/* 2971:     */     case '.': 
/* 2972:     */     case '/': 
/* 2973:     */     case ':': 
/* 2974:     */     case ';': 
/* 2975:     */     case '<': 
/* 2976:     */     case '=': 
/* 2977:     */     case '>': 
/* 2978:     */     case '?': 
/* 2979:     */     case '@': 
/* 2980:     */     case '[': 
/* 2981:     */     case '\\': 
/* 2982:     */     case ']': 
/* 2983:     */     case '^': 
/* 2984:     */     case '`': 
/* 2985:     */     default: 
/* 2986:1970 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2987:     */     }
/* 2988:1973 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 2989:     */     {
/* 2990:1974 */       localToken = makeToken(i);
/* 2991:1975 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 2992:     */     }
/* 2993:1977 */     this._returnToken = localToken;
/* 2994:     */   }
/* 2995:     */   
/* 2996:     */   protected final void mTEXT_ARG_ID_ELEMENT(boolean paramBoolean)
/* 2997:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 2998:     */   {
/* 2999:1981 */     Token localToken1 = null;int j = this.text.length();
/* 3000:1982 */     int i = 15;
/* 3001:     */     
/* 3002:1984 */     Token localToken2 = null;
/* 3003:     */     
/* 3004:1986 */     mID(true);
/* 3005:1987 */     localToken2 = this._returnToken;
/* 3006:     */     int k;
/* 3007:1989 */     if ((_tokenSet_4.member(LA(1))) && (_tokenSet_22.member(LA(2))))
/* 3008:     */     {
/* 3009:1990 */       k = this.text.length();
/* 3010:1991 */       mWS(false);
/* 3011:1992 */       this.text.setLength(k);
/* 3012:     */     }
/* 3013:1994 */     else if (!_tokenSet_22.member(LA(1)))
/* 3014:     */     {
/* 3015:1997 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 3016:     */     }
/* 3017:2002 */     switch (LA(1))
/* 3018:     */     {
/* 3019:     */     case '(': 
/* 3020:2005 */       match('(');
/* 3021:2007 */       if ((_tokenSet_4.member(LA(1))) && (_tokenSet_23.member(LA(2))) && (LA(3) >= '\003') && (LA(3) <= 'ÿ'))
/* 3022:     */       {
/* 3023:2008 */         k = this.text.length();
/* 3024:2009 */         mWS(false);
/* 3025:2010 */         this.text.setLength(k);
/* 3026:     */       }
/* 3027:2012 */       else if ((!_tokenSet_23.member(LA(1))) || (LA(2) < '\003') || (LA(2) > 'ÿ'))
/* 3028:     */       {
/* 3029:2015 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 3030:     */       }
/* 3031:2022 */       if ((_tokenSet_24.member(LA(1))) && (LA(2) >= '\003') && (LA(2) <= 'ÿ') && (LA(3) >= '\003') && (LA(3) <= 'ÿ'))
/* 3032:     */       {
/* 3033:2023 */         mTEXT_ARG(false);
/* 3034:2027 */         while (LA(1) == ',')
/* 3035:     */         {
/* 3036:2028 */           match(',');
/* 3037:2029 */           mTEXT_ARG(false);
/* 3038:     */         }
/* 3039:     */       }
/* 3040:2045 */       switch (LA(1))
/* 3041:     */       {
/* 3042:     */       case '\t': 
/* 3043:     */       case '\n': 
/* 3044:     */       case '\r': 
/* 3045:     */       case ' ': 
/* 3046:2048 */         k = this.text.length();
/* 3047:2049 */         mWS(false);
/* 3048:2050 */         this.text.setLength(k);
/* 3049:2051 */         break;
/* 3050:     */       case ')': 
/* 3051:     */         break;
/* 3052:     */       default: 
/* 3053:2059 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 3054:     */       }
/* 3055:2063 */       match(')');
/* 3056:2064 */       break;
/* 3057:     */     case '[': 
/* 3058:2069 */       int m = 0;
/* 3059:     */       for (;;)
/* 3060:     */       {
/* 3061:2072 */         if (LA(1) == '[')
/* 3062:     */         {
/* 3063:2073 */           match('[');
/* 3064:2075 */           if ((_tokenSet_4.member(LA(1))) && (_tokenSet_24.member(LA(2))) && (LA(3) >= '\003') && (LA(3) <= 'ÿ'))
/* 3065:     */           {
/* 3066:2076 */             k = this.text.length();
/* 3067:2077 */             mWS(false);
/* 3068:2078 */             this.text.setLength(k);
/* 3069:     */           }
/* 3070:2080 */           else if ((!_tokenSet_24.member(LA(1))) || (LA(2) < '\003') || (LA(2) > 'ÿ') || (LA(3) < '\003') || (LA(3) > 'ÿ'))
/* 3071:     */           {
/* 3072:2083 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 3073:     */           }
/* 3074:2087 */           mTEXT_ARG(false);
/* 3075:2089 */           switch (LA(1))
/* 3076:     */           {
/* 3077:     */           case '\t': 
/* 3078:     */           case '\n': 
/* 3079:     */           case '\r': 
/* 3080:     */           case ' ': 
/* 3081:2092 */             k = this.text.length();
/* 3082:2093 */             mWS(false);
/* 3083:2094 */             this.text.setLength(k);
/* 3084:2095 */             break;
/* 3085:     */           case ']': 
/* 3086:     */             break;
/* 3087:     */           default: 
/* 3088:2103 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 3089:     */           }
/* 3090:2107 */           match(']');
/* 3091:     */         }
/* 3092:     */         else
/* 3093:     */         {
/* 3094:2110 */           if (m >= 1) {
/* 3095:     */             break;
/* 3096:     */           }
/* 3097:2110 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 3098:     */         }
/* 3099:2113 */         m++;
/* 3100:     */       }
/* 3101:     */     case '.': 
/* 3102:2120 */       match('.');
/* 3103:2121 */       mTEXT_ARG_ID_ELEMENT(false);
/* 3104:2122 */       break;
/* 3105:     */     case '-': 
/* 3106:2126 */       match("->");
/* 3107:2127 */       mTEXT_ARG_ID_ELEMENT(false);
/* 3108:2128 */       break;
/* 3109:     */     case '\t': 
/* 3110:     */     case '\n': 
/* 3111:     */     case '\r': 
/* 3112:     */     case ' ': 
/* 3113:     */     case '"': 
/* 3114:     */     case '$': 
/* 3115:     */     case '\'': 
/* 3116:     */     case ')': 
/* 3117:     */     case '+': 
/* 3118:     */     case ',': 
/* 3119:     */     case '0': 
/* 3120:     */     case '1': 
/* 3121:     */     case '2': 
/* 3122:     */     case '3': 
/* 3123:     */     case '4': 
/* 3124:     */     case '5': 
/* 3125:     */     case '6': 
/* 3126:     */     case '7': 
/* 3127:     */     case '8': 
/* 3128:     */     case '9': 
/* 3129:     */     case 'A': 
/* 3130:     */     case 'B': 
/* 3131:     */     case 'C': 
/* 3132:     */     case 'D': 
/* 3133:     */     case 'E': 
/* 3134:     */     case 'F': 
/* 3135:     */     case 'G': 
/* 3136:     */     case 'H': 
/* 3137:     */     case 'I': 
/* 3138:     */     case 'J': 
/* 3139:     */     case 'K': 
/* 3140:     */     case 'L': 
/* 3141:     */     case 'M': 
/* 3142:     */     case 'N': 
/* 3143:     */     case 'O': 
/* 3144:     */     case 'P': 
/* 3145:     */     case 'Q': 
/* 3146:     */     case 'R': 
/* 3147:     */     case 'S': 
/* 3148:     */     case 'T': 
/* 3149:     */     case 'U': 
/* 3150:     */     case 'V': 
/* 3151:     */     case 'W': 
/* 3152:     */     case 'X': 
/* 3153:     */     case 'Y': 
/* 3154:     */     case 'Z': 
/* 3155:     */     case ']': 
/* 3156:     */     case '_': 
/* 3157:     */     case 'a': 
/* 3158:     */     case 'b': 
/* 3159:     */     case 'c': 
/* 3160:     */     case 'd': 
/* 3161:     */     case 'e': 
/* 3162:     */     case 'f': 
/* 3163:     */     case 'g': 
/* 3164:     */     case 'h': 
/* 3165:     */     case 'i': 
/* 3166:     */     case 'j': 
/* 3167:     */     case 'k': 
/* 3168:     */     case 'l': 
/* 3169:     */     case 'm': 
/* 3170:     */     case 'n': 
/* 3171:     */     case 'o': 
/* 3172:     */     case 'p': 
/* 3173:     */     case 'q': 
/* 3174:     */     case 'r': 
/* 3175:     */     case 's': 
/* 3176:     */     case 't': 
/* 3177:     */     case 'u': 
/* 3178:     */     case 'v': 
/* 3179:     */     case 'w': 
/* 3180:     */     case 'x': 
/* 3181:     */     case 'y': 
/* 3182:     */     case 'z': 
/* 3183:     */       break;
/* 3184:     */     case '\013': 
/* 3185:     */     case '\f': 
/* 3186:     */     case '\016': 
/* 3187:     */     case '\017': 
/* 3188:     */     case '\020': 
/* 3189:     */     case '\021': 
/* 3190:     */     case '\022': 
/* 3191:     */     case '\023': 
/* 3192:     */     case '\024': 
/* 3193:     */     case '\025': 
/* 3194:     */     case '\026': 
/* 3195:     */     case '\027': 
/* 3196:     */     case '\030': 
/* 3197:     */     case '\031': 
/* 3198:     */     case '\032': 
/* 3199:     */     case '\033': 
/* 3200:     */     case '\034': 
/* 3201:     */     case '\035': 
/* 3202:     */     case '\036': 
/* 3203:     */     case '\037': 
/* 3204:     */     case '!': 
/* 3205:     */     case '#': 
/* 3206:     */     case '%': 
/* 3207:     */     case '&': 
/* 3208:     */     case '*': 
/* 3209:     */     case '/': 
/* 3210:     */     case ':': 
/* 3211:     */     case ';': 
/* 3212:     */     case '<': 
/* 3213:     */     case '=': 
/* 3214:     */     case '>': 
/* 3215:     */     case '?': 
/* 3216:     */     case '@': 
/* 3217:     */     case '\\': 
/* 3218:     */     case '^': 
/* 3219:     */     case '`': 
/* 3220:     */     default: 
/* 3221:2154 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 3222:     */     }
/* 3223:2158 */     if ((paramBoolean) && (localToken1 == null) && (i != -1))
/* 3224:     */     {
/* 3225:2159 */       localToken1 = makeToken(i);
/* 3226:2160 */       localToken1.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 3227:     */     }
/* 3228:2162 */     this._returnToken = localToken1;
/* 3229:     */   }
/* 3230:     */   
/* 3231:     */   protected final void mINT_OR_FLOAT(boolean paramBoolean)
/* 3232:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 3233:     */   {
/* 3234:2166 */     Token localToken = null;int j = this.text.length();
/* 3235:2167 */     int i = 27;
/* 3236:     */     
/* 3237:     */ 
/* 3238:     */ 
/* 3239:2171 */     int k = 0;
/* 3240:     */     for (;;)
/* 3241:     */     {
/* 3242:2174 */       if ((LA(1) >= '0') && (LA(1) <= '9') && (_tokenSet_25.member(LA(2))))
/* 3243:     */       {
/* 3244:2175 */         mDIGIT(false);
/* 3245:     */       }
/* 3246:     */       else
/* 3247:     */       {
/* 3248:2178 */         if (k >= 1) {
/* 3249:     */           break;
/* 3250:     */         }
/* 3251:2178 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 3252:     */       }
/* 3253:2181 */       k++;
/* 3254:     */     }
/* 3255:2185 */     if ((LA(1) == 'L') && (_tokenSet_26.member(LA(2))))
/* 3256:     */     {
/* 3257:2186 */       match('L');
/* 3258:     */     }
/* 3259:2188 */     else if ((LA(1) == 'l') && (_tokenSet_26.member(LA(2))))
/* 3260:     */     {
/* 3261:2189 */       match('l');
/* 3262:     */     }
/* 3263:     */     else
/* 3264:     */     {
/* 3265:2191 */       if (LA(1) == '.')
/* 3266:     */       {
/* 3267:2192 */         match('.');
/* 3268:2196 */         while ((LA(1) >= '0') && (LA(1) <= '9') && (_tokenSet_26.member(LA(2)))) {
/* 3269:2197 */           mDIGIT(false);
/* 3270:     */         }
/* 3271:     */       }
/* 3272:2206 */       if (!_tokenSet_26.member(LA(1))) {
/* 3273:2209 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 3274:     */       }
/* 3275:     */     }
/* 3276:2213 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 3277:     */     {
/* 3278:2214 */       localToken = makeToken(i);
/* 3279:2215 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 3280:     */     }
/* 3281:2217 */     this._returnToken = localToken;
/* 3282:     */   }
/* 3283:     */   
/* 3284:     */   protected final void mSL_COMMENT(boolean paramBoolean)
/* 3285:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 3286:     */   {
/* 3287:2221 */     Token localToken = null;int j = this.text.length();
/* 3288:2222 */     int i = 20;
/* 3289:     */     
/* 3290:     */ 
/* 3291:2225 */     match("//");
/* 3292:2230 */     while ((LA(1) != '\n') && (LA(1) != '\r') && 
/* 3293:2231 */       (LA(1) >= '\003') && (LA(1) <= 'ÿ') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 3294:2232 */       matchNot(65535);
/* 3295:     */     }
/* 3296:2241 */     if ((LA(1) == '\r') && (LA(2) == '\n')) {
/* 3297:2242 */       match("\r\n");
/* 3298:2244 */     } else if (LA(1) == '\n') {
/* 3299:2245 */       match('\n');
/* 3300:2247 */     } else if (LA(1) == '\r') {
/* 3301:2248 */       match('\r');
/* 3302:     */     } else {
/* 3303:2251 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 3304:     */     }
/* 3305:2255 */     newline();
/* 3306:2256 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 3307:     */     {
/* 3308:2257 */       localToken = makeToken(i);
/* 3309:2258 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 3310:     */     }
/* 3311:2260 */     this._returnToken = localToken;
/* 3312:     */   }
/* 3313:     */   
/* 3314:     */   protected final void mML_COMMENT(boolean paramBoolean)
/* 3315:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 3316:     */   {
/* 3317:2264 */     Token localToken = null;int j = this.text.length();
/* 3318:2265 */     int i = 21;
/* 3319:     */     
/* 3320:     */ 
/* 3321:2268 */     match("/*");
/* 3322:2273 */     while ((LA(1) != '*') || (LA(2) != '/')) {
/* 3323:2274 */       if ((LA(1) == '\r') && (LA(2) == '\n') && (LA(3) >= '\003') && (LA(3) <= 'ÿ'))
/* 3324:     */       {
/* 3325:2275 */         match('\r');
/* 3326:2276 */         match('\n');
/* 3327:2277 */         newline();
/* 3328:     */       }
/* 3329:2279 */       else if ((LA(1) == '\r') && (LA(2) >= '\003') && (LA(2) <= 'ÿ') && (LA(3) >= '\003') && (LA(3) <= 'ÿ'))
/* 3330:     */       {
/* 3331:2280 */         match('\r');
/* 3332:2281 */         newline();
/* 3333:     */       }
/* 3334:2283 */       else if ((LA(1) == '\n') && (LA(2) >= '\003') && (LA(2) <= 'ÿ') && (LA(3) >= '\003') && (LA(3) <= 'ÿ'))
/* 3335:     */       {
/* 3336:2284 */         match('\n');
/* 3337:2285 */         newline();
/* 3338:     */       }
/* 3339:     */       else
/* 3340:     */       {
/* 3341:2287 */         if ((LA(1) < '\003') || (LA(1) > 'ÿ') || (LA(2) < '\003') || (LA(2) > 'ÿ') || (LA(3) < '\003') || (LA(3) > 'ÿ')) {
/* 3342:     */           break;
/* 3343:     */         }
/* 3344:2288 */         matchNot(65535);
/* 3345:     */       }
/* 3346:     */     }
/* 3347:2296 */     match("*/");
/* 3348:2297 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 3349:     */     {
/* 3350:2298 */       localToken = makeToken(i);
/* 3351:2299 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 3352:     */     }
/* 3353:2301 */     this._returnToken = localToken;
/* 3354:     */   }
/* 3355:     */   
/* 3356:     */   protected final void mESC(boolean paramBoolean)
/* 3357:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 3358:     */   {
/* 3359:2305 */     Token localToken = null;int j = this.text.length();
/* 3360:2306 */     int i = 24;
/* 3361:     */     
/* 3362:     */ 
/* 3363:2309 */     match('\\');
/* 3364:2311 */     switch (LA(1))
/* 3365:     */     {
/* 3366:     */     case 'n': 
/* 3367:2314 */       match('n');
/* 3368:2315 */       break;
/* 3369:     */     case 'r': 
/* 3370:2319 */       match('r');
/* 3371:2320 */       break;
/* 3372:     */     case 't': 
/* 3373:2324 */       match('t');
/* 3374:2325 */       break;
/* 3375:     */     case 'b': 
/* 3376:2329 */       match('b');
/* 3377:2330 */       break;
/* 3378:     */     case 'f': 
/* 3379:2334 */       match('f');
/* 3380:2335 */       break;
/* 3381:     */     case '"': 
/* 3382:2339 */       match('"');
/* 3383:2340 */       break;
/* 3384:     */     case '\'': 
/* 3385:2344 */       match('\'');
/* 3386:2345 */       break;
/* 3387:     */     case '\\': 
/* 3388:2349 */       match('\\');
/* 3389:2350 */       break;
/* 3390:     */     case '0': 
/* 3391:     */     case '1': 
/* 3392:     */     case '2': 
/* 3393:     */     case '3': 
/* 3394:2355 */       matchRange('0', '3');
/* 3395:2358 */       if ((LA(1) >= '0') && (LA(1) <= '9') && (LA(2) >= '\003') && (LA(2) <= 'ÿ'))
/* 3396:     */       {
/* 3397:2359 */         mDIGIT(false);
/* 3398:2361 */         if ((LA(1) >= '0') && (LA(1) <= '9') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 3399:2362 */           mDIGIT(false);
/* 3400:2364 */         } else if ((LA(1) < '\003') || (LA(1) > 'ÿ')) {
/* 3401:2367 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 3402:     */         }
/* 3403:     */       }
/* 3404:2372 */       else if ((LA(1) < '\003') || (LA(1) > 'ÿ'))
/* 3405:     */       {
/* 3406:2375 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 3407:     */       }
/* 3408:     */       break;
/* 3409:     */     case '4': 
/* 3410:     */     case '5': 
/* 3411:     */     case '6': 
/* 3412:     */     case '7': 
/* 3413:2384 */       matchRange('4', '7');
/* 3414:2387 */       if ((LA(1) >= '0') && (LA(1) <= '9') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 3415:2388 */         mDIGIT(false);
/* 3416:2390 */       } else if ((LA(1) < '\003') || (LA(1) > 'ÿ')) {
/* 3417:2393 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 3418:     */       }
/* 3419:     */       break;
/* 3420:     */     default: 
/* 3421:2401 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 3422:     */     }
/* 3423:2405 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 3424:     */     {
/* 3425:2406 */       localToken = makeToken(i);
/* 3426:2407 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 3427:     */     }
/* 3428:2409 */     this._returnToken = localToken;
/* 3429:     */   }
/* 3430:     */   
/* 3431:     */   protected final void mDIGIT(boolean paramBoolean)
/* 3432:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 3433:     */   {
/* 3434:2413 */     Token localToken = null;int j = this.text.length();
/* 3435:2414 */     int i = 25;
/* 3436:     */     
/* 3437:     */ 
/* 3438:2417 */     matchRange('0', '9');
/* 3439:2418 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 3440:     */     {
/* 3441:2419 */       localToken = makeToken(i);
/* 3442:2420 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 3443:     */     }
/* 3444:2422 */     this._returnToken = localToken;
/* 3445:     */   }
/* 3446:     */   
/* 3447:     */   private static final long[] mk_tokenSet_0()
/* 3448:     */   {
/* 3449:2427 */     long[] arrayOfLong = new long[8];
/* 3450:2428 */     arrayOfLong[0] = -103079215112L;
/* 3451:2429 */     for (int i = 1; i <= 3; i++) {
/* 3452:2429 */       arrayOfLong[i] = -1L;
/* 3453:     */     }
/* 3454:2430 */     return arrayOfLong;
/* 3455:     */   }
/* 3456:     */   
/* 3457:2432 */   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
/* 3458:     */   
/* 3459:     */   private static final long[] mk_tokenSet_1()
/* 3460:     */   {
/* 3461:2434 */     long[] arrayOfLong = new long[8];
/* 3462:2435 */     arrayOfLong[0] = -145135534866440L;
/* 3463:2436 */     for (int i = 1; i <= 3; i++) {
/* 3464:2436 */       arrayOfLong[i] = -1L;
/* 3465:     */     }
/* 3466:2437 */     return arrayOfLong;
/* 3467:     */   }
/* 3468:     */   
/* 3469:2439 */   public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
/* 3470:     */   
/* 3471:     */   private static final long[] mk_tokenSet_2()
/* 3472:     */   {
/* 3473:2441 */     long[] arrayOfLong = new long[8];
/* 3474:2442 */     arrayOfLong[0] = -141407503262728L;
/* 3475:2443 */     for (int i = 1; i <= 3; i++) {
/* 3476:2443 */       arrayOfLong[i] = -1L;
/* 3477:     */     }
/* 3478:2444 */     return arrayOfLong;
/* 3479:     */   }
/* 3480:     */   
/* 3481:2446 */   public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
/* 3482:     */   
/* 3483:     */   private static final long[] mk_tokenSet_3()
/* 3484:     */   {
/* 3485:2448 */     long[] arrayOfLong = { 4294977024L, 576460745995190270L, 0L, 0L, 0L };
/* 3486:2449 */     return arrayOfLong;
/* 3487:     */   }
/* 3488:     */   
/* 3489:2451 */   public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
/* 3490:     */   
/* 3491:     */   private static final long[] mk_tokenSet_4()
/* 3492:     */   {
/* 3493:2453 */     long[] arrayOfLong = { 4294977024L, 0L, 0L, 0L, 0L };
/* 3494:2454 */     return arrayOfLong;
/* 3495:     */   }
/* 3496:     */   
/* 3497:2456 */   public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
/* 3498:     */   
/* 3499:     */   private static final long[] mk_tokenSet_5()
/* 3500:     */   {
/* 3501:2458 */     long[] arrayOfLong = { 1103806604800L, 0L, 0L, 0L, 0L };
/* 3502:2459 */     return arrayOfLong;
/* 3503:     */   }
/* 3504:     */   
/* 3505:2461 */   public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
/* 3506:     */   
/* 3507:     */   private static final long[] mk_tokenSet_6()
/* 3508:     */   {
/* 3509:2463 */     long[] arrayOfLong = { 287959436729787904L, 576460745995190270L, 0L, 0L, 0L };
/* 3510:2464 */     return arrayOfLong;
/* 3511:     */   }
/* 3512:     */   
/* 3513:2466 */   public static final BitSet _tokenSet_6 = new BitSet(mk_tokenSet_6());
/* 3514:     */   
/* 3515:     */   private static final long[] mk_tokenSet_7()
/* 3516:     */   {
/* 3517:2468 */     long[] arrayOfLong = new long[8];
/* 3518:2469 */     arrayOfLong[0] = -17179869192L;
/* 3519:2470 */     arrayOfLong[1] = -268435457L;
/* 3520:2471 */     for (int i = 2; i <= 3; i++) {
/* 3521:2471 */       arrayOfLong[i] = -1L;
/* 3522:     */     }
/* 3523:2472 */     return arrayOfLong;
/* 3524:     */   }
/* 3525:     */   
/* 3526:2474 */   public static final BitSet _tokenSet_7 = new BitSet(mk_tokenSet_7());
/* 3527:     */   
/* 3528:     */   private static final long[] mk_tokenSet_8()
/* 3529:     */   {
/* 3530:2476 */     long[] arrayOfLong = new long[8];
/* 3531:2477 */     arrayOfLong[0] = -549755813896L;
/* 3532:2478 */     arrayOfLong[1] = -268435457L;
/* 3533:2479 */     for (int i = 2; i <= 3; i++) {
/* 3534:2479 */       arrayOfLong[i] = -1L;
/* 3535:     */     }
/* 3536:2480 */     return arrayOfLong;
/* 3537:     */   }
/* 3538:     */   
/* 3539:2482 */   public static final BitSet _tokenSet_8 = new BitSet(mk_tokenSet_8());
/* 3540:     */   
/* 3541:     */   private static final long[] mk_tokenSet_9()
/* 3542:     */   {
/* 3543:2484 */     long[] arrayOfLong = { 287948901175001088L, 576460745995190270L, 0L, 0L, 0L };
/* 3544:2485 */     return arrayOfLong;
/* 3545:     */   }
/* 3546:     */   
/* 3547:2487 */   public static final BitSet _tokenSet_9 = new BitSet(mk_tokenSet_9());
/* 3548:     */   
/* 3549:     */   private static final long[] mk_tokenSet_10()
/* 3550:     */   {
/* 3551:2489 */     long[] arrayOfLong = { 287950056521213440L, 576460746129407998L, 0L, 0L, 0L };
/* 3552:2490 */     return arrayOfLong;
/* 3553:     */   }
/* 3554:     */   
/* 3555:2492 */   public static final BitSet _tokenSet_10 = new BitSet(mk_tokenSet_10());
/* 3556:     */   
/* 3557:     */   private static final long[] mk_tokenSet_11()
/* 3558:     */   {
/* 3559:2494 */     long[] arrayOfLong = { 287958332923183104L, 576460745995190270L, 0L, 0L, 0L };
/* 3560:2495 */     return arrayOfLong;
/* 3561:     */   }
/* 3562:     */   
/* 3563:2497 */   public static final BitSet _tokenSet_11 = new BitSet(mk_tokenSet_11());
/* 3564:     */   
/* 3565:     */   private static final long[] mk_tokenSet_12()
/* 3566:     */   {
/* 3567:2499 */     long[] arrayOfLong = { 287978128427460096L, 576460746532061182L, 0L, 0L, 0L };
/* 3568:2500 */     return arrayOfLong;
/* 3569:     */   }
/* 3570:     */   
/* 3571:2502 */   public static final BitSet _tokenSet_12 = new BitSet(mk_tokenSet_12());
/* 3572:     */   
/* 3573:     */   private static final long[] mk_tokenSet_13()
/* 3574:     */   {
/* 3575:2504 */     long[] arrayOfLong = { 0L, 576460745995190270L, 0L, 0L, 0L };
/* 3576:2505 */     return arrayOfLong;
/* 3577:     */   }
/* 3578:     */   
/* 3579:2507 */   public static final BitSet _tokenSet_13 = new BitSet(mk_tokenSet_13());
/* 3580:     */   
/* 3581:     */   private static final long[] mk_tokenSet_14()
/* 3582:     */   {
/* 3583:2509 */     long[] arrayOfLong = { 2306123388973753856L, 671088640L, 0L, 0L, 0L };
/* 3584:2510 */     return arrayOfLong;
/* 3585:     */   }
/* 3586:     */   
/* 3587:2512 */   public static final BitSet _tokenSet_14 = new BitSet(mk_tokenSet_14());
/* 3588:     */   
/* 3589:     */   private static final long[] mk_tokenSet_15()
/* 3590:     */   {
/* 3591:2514 */     long[] arrayOfLong = { 287952805300282880L, 576460746129407998L, 0L, 0L, 0L };
/* 3592:2515 */     return arrayOfLong;
/* 3593:     */   }
/* 3594:     */   
/* 3595:2517 */   public static final BitSet _tokenSet_15 = new BitSet(mk_tokenSet_15());
/* 3596:     */   
/* 3597:     */   private static final long[] mk_tokenSet_16()
/* 3598:     */   {
/* 3599:2519 */     long[] arrayOfLong = { 2306051920717948416L, 536870912L, 0L, 0L, 0L };
/* 3600:2520 */     return arrayOfLong;
/* 3601:     */   }
/* 3602:     */   
/* 3603:2522 */   public static final BitSet _tokenSet_16 = new BitSet(mk_tokenSet_16());
/* 3604:     */   
/* 3605:     */   private static final long[] mk_tokenSet_17()
/* 3606:     */   {
/* 3607:2524 */     long[] arrayOfLong = { 2305843013508670976L, 0L, 0L, 0L, 0L };
/* 3608:2525 */     return arrayOfLong;
/* 3609:     */   }
/* 3610:     */   
/* 3611:2527 */   public static final BitSet _tokenSet_17 = new BitSet(mk_tokenSet_17());
/* 3612:     */   
/* 3613:     */   private static final long[] mk_tokenSet_18()
/* 3614:     */   {
/* 3615:2529 */     long[] arrayOfLong = { 208911504254464L, 536870912L, 0L, 0L, 0L };
/* 3616:2530 */     return arrayOfLong;
/* 3617:     */   }
/* 3618:     */   
/* 3619:2532 */   public static final BitSet _tokenSet_18 = new BitSet(mk_tokenSet_18());
/* 3620:     */   
/* 3621:     */   private static final long[] mk_tokenSet_19()
/* 3622:     */   {
/* 3623:2534 */     long[] arrayOfLong = { 1151051235328L, 576460746129407998L, 0L, 0L, 0L };
/* 3624:2535 */     return arrayOfLong;
/* 3625:     */   }
/* 3626:     */   
/* 3627:2537 */   public static final BitSet _tokenSet_19 = new BitSet(mk_tokenSet_19());
/* 3628:     */   
/* 3629:     */   private static final long[] mk_tokenSet_20()
/* 3630:     */   {
/* 3631:2539 */     long[] arrayOfLong = { 189120294954496L, 0L, 0L, 0L, 0L };
/* 3632:2540 */     return arrayOfLong;
/* 3633:     */   }
/* 3634:     */   
/* 3635:2542 */   public static final BitSet _tokenSet_20 = new BitSet(mk_tokenSet_20());
/* 3636:     */   
/* 3637:     */   private static final long[] mk_tokenSet_21()
/* 3638:     */   {
/* 3639:2544 */     long[] arrayOfLong = { 288139722277004800L, 576460746129407998L, 0L, 0L, 0L };
/* 3640:2545 */     return arrayOfLong;
/* 3641:     */   }
/* 3642:     */   
/* 3643:2547 */   public static final BitSet _tokenSet_21 = new BitSet(mk_tokenSet_21());
/* 3644:     */   
/* 3645:     */   private static final long[] mk_tokenSet_22()
/* 3646:     */   {
/* 3647:2549 */     long[] arrayOfLong = { 288084781055354368L, 576460746666278910L, 0L, 0L, 0L };
/* 3648:2550 */     return arrayOfLong;
/* 3649:     */   }
/* 3650:     */   
/* 3651:2552 */   public static final BitSet _tokenSet_22 = new BitSet(mk_tokenSet_22());
/* 3652:     */   
/* 3653:     */   private static final long[] mk_tokenSet_23()
/* 3654:     */   {
/* 3655:2554 */     long[] arrayOfLong = { 287960536241415680L, 576460745995190270L, 0L, 0L, 0L };
/* 3656:2555 */     return arrayOfLong;
/* 3657:     */   }
/* 3658:     */   
/* 3659:2557 */   public static final BitSet _tokenSet_23 = new BitSet(mk_tokenSet_23());
/* 3660:     */   
/* 3661:     */   private static final long[] mk_tokenSet_24()
/* 3662:     */   {
/* 3663:2559 */     long[] arrayOfLong = { 287958337218160128L, 576460745995190270L, 0L, 0L, 0L };
/* 3664:2560 */     return arrayOfLong;
/* 3665:     */   }
/* 3666:     */   
/* 3667:2562 */   public static final BitSet _tokenSet_24 = new BitSet(mk_tokenSet_24());
/* 3668:     */   
/* 3669:     */   private static final long[] mk_tokenSet_25()
/* 3670:     */   {
/* 3671:2564 */     long[] arrayOfLong = { 288228817078593024L, 576460746532061182L, 0L, 0L, 0L };
/* 3672:2565 */     return arrayOfLong;
/* 3673:     */   }
/* 3674:     */   
/* 3675:2567 */   public static final BitSet _tokenSet_25 = new BitSet(mk_tokenSet_25());
/* 3676:     */   
/* 3677:     */   private static final long[] mk_tokenSet_26()
/* 3678:     */   {
/* 3679:2569 */     long[] arrayOfLong = { 288158448334415360L, 576460746532061182L, 0L, 0L, 0L };
/* 3680:2570 */     return arrayOfLong;
/* 3681:     */   }
/* 3682:     */   
/* 3683:2572 */   public static final BitSet _tokenSet_26 = new BitSet(mk_tokenSet_26());
/* 3684:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.actions.csharp.ActionLexer
 * JD-Core Version:    0.7.0.1
 */