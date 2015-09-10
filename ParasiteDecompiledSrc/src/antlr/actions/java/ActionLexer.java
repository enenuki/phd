/*    1:     */ package antlr.actions.java;
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
/*   54:  79 */     this.antlrTool = paramTool;
/*   55:     */   }
/*   56:     */   
/*   57:     */   public void reportError(RecognitionException paramRecognitionException)
/*   58:     */   {
/*   59:  84 */     this.antlrTool.error("Syntax error in action: " + paramRecognitionException, getFilename(), getLine(), getColumn());
/*   60:     */   }
/*   61:     */   
/*   62:     */   public void reportError(String paramString)
/*   63:     */   {
/*   64:  89 */     this.antlrTool.error(paramString, getFilename(), getLine(), getColumn());
/*   65:     */   }
/*   66:     */   
/*   67:     */   public void reportWarning(String paramString)
/*   68:     */   {
/*   69:  94 */     if (getFilename() == null) {
/*   70:  95 */       this.antlrTool.warning(paramString);
/*   71:     */     } else {
/*   72:  98 */       this.antlrTool.warning(paramString, getFilename(), getLine(), getColumn());
/*   73:     */     }
/*   74:     */   }
/*   75:     */   
/*   76:     */   public ActionLexer(InputStream paramInputStream)
/*   77:     */   {
/*   78: 102 */     this(new ByteBuffer(paramInputStream));
/*   79:     */   }
/*   80:     */   
/*   81:     */   public ActionLexer(Reader paramReader)
/*   82:     */   {
/*   83: 105 */     this(new CharBuffer(paramReader));
/*   84:     */   }
/*   85:     */   
/*   86:     */   public ActionLexer(InputBuffer paramInputBuffer)
/*   87:     */   {
/*   88: 108 */     this(new LexerSharedInputState(paramInputBuffer));
/*   89:     */   }
/*   90:     */   
/*   91:     */   public ActionLexer(LexerSharedInputState paramLexerSharedInputState)
/*   92:     */   {
/*   93: 111 */     super(paramLexerSharedInputState);
/*   94: 112 */     this.caseSensitiveLiterals = true;
/*   95: 113 */     setCaseSensitive(true);
/*   96: 114 */     this.literals = new Hashtable();
/*   97:     */   }
/*   98:     */   
/*   99:     */   public Token nextToken()
/*  100:     */     throws TokenStreamException
/*  101:     */   {
/*  102: 118 */     Token localToken = null;
/*  103:     */     for (;;)
/*  104:     */     {
/*  105: 121 */       Object localObject = null;
/*  106: 122 */       int i = 0;
/*  107: 123 */       resetText();
/*  108:     */       try
/*  109:     */       {
/*  110: 126 */         if ((LA(1) >= '\003') && (LA(1) <= 'ÿ'))
/*  111:     */         {
/*  112: 127 */           mACTION(true);
/*  113: 128 */           localToken = this._returnToken;
/*  114:     */         }
/*  115: 131 */         else if (LA(1) == 65535)
/*  116:     */         {
/*  117: 131 */           uponEOF();this._returnToken = makeToken(1);
/*  118:     */         }
/*  119:     */         else
/*  120:     */         {
/*  121: 132 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  122:     */         }
/*  123: 135 */         if (this._returnToken == null) {
/*  124:     */           continue;
/*  125:     */         }
/*  126: 136 */         i = this._returnToken.getType();
/*  127: 137 */         this._returnToken.setType(i);
/*  128: 138 */         return this._returnToken;
/*  129:     */       }
/*  130:     */       catch (RecognitionException localRecognitionException)
/*  131:     */       {
/*  132: 141 */         throw new TokenStreamRecognitionException(localRecognitionException);
/*  133:     */       }
/*  134:     */       catch (CharStreamException localCharStreamException)
/*  135:     */       {
/*  136: 145 */         if ((localCharStreamException instanceof CharStreamIOException)) {
/*  137: 146 */           throw new TokenStreamIOException(((CharStreamIOException)localCharStreamException).io);
/*  138:     */         }
/*  139: 149 */         throw new TokenStreamException(localCharStreamException.getMessage());
/*  140:     */       }
/*  141:     */     }
/*  142:     */   }
/*  143:     */   
/*  144:     */   public final void mACTION(boolean paramBoolean)
/*  145:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  146:     */   {
/*  147: 156 */     Token localToken = null;int j = this.text.length();
/*  148: 157 */     int i = 4;
/*  149:     */     
/*  150:     */ 
/*  151:     */ 
/*  152: 161 */     int k = 0;
/*  153:     */     for (;;)
/*  154:     */     {
/*  155: 164 */       switch (LA(1))
/*  156:     */       {
/*  157:     */       case '#': 
/*  158: 167 */         mAST_ITEM(false);
/*  159: 168 */         break;
/*  160:     */       case '$': 
/*  161: 172 */         mTEXT_ITEM(false);
/*  162: 173 */         break;
/*  163:     */       default: 
/*  164: 176 */         if (_tokenSet_0.member(LA(1)))
/*  165:     */         {
/*  166: 177 */           mSTUFF(false);
/*  167:     */         }
/*  168:     */         else
/*  169:     */         {
/*  170: 180 */           if (k >= 1) {
/*  171:     */             break label126;
/*  172:     */           }
/*  173: 180 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  174:     */         }
/*  175:     */         break;
/*  176:     */       }
/*  177: 183 */       k++;
/*  178:     */     }
/*  179:     */     label126:
/*  180: 186 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/*  181:     */     {
/*  182: 187 */       localToken = makeToken(i);
/*  183: 188 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  184:     */     }
/*  185: 190 */     this._returnToken = localToken;
/*  186:     */   }
/*  187:     */   
/*  188:     */   protected final void mSTUFF(boolean paramBoolean)
/*  189:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  190:     */   {
/*  191: 194 */     Token localToken = null;int j = this.text.length();
/*  192: 195 */     int i = 5;
/*  193: 198 */     switch (LA(1))
/*  194:     */     {
/*  195:     */     case '"': 
/*  196: 201 */       mSTRING(false);
/*  197: 202 */       break;
/*  198:     */     case '\'': 
/*  199: 206 */       mCHAR(false);
/*  200: 207 */       break;
/*  201:     */     case '\n': 
/*  202: 211 */       match('\n');
/*  203: 212 */       newline();
/*  204: 213 */       break;
/*  205:     */     default: 
/*  206: 216 */       if ((LA(1) == '/') && ((LA(2) == '*') || (LA(2) == '/')))
/*  207:     */       {
/*  208: 217 */         mCOMMENT(false);
/*  209:     */       }
/*  210: 219 */       else if ((LA(1) == '\r') && (LA(2) == '\n'))
/*  211:     */       {
/*  212: 220 */         match("\r\n");
/*  213: 221 */         newline();
/*  214:     */       }
/*  215: 223 */       else if ((LA(1) == '/') && (_tokenSet_1.member(LA(2))))
/*  216:     */       {
/*  217: 224 */         match('/');
/*  218:     */         
/*  219: 226 */         match(_tokenSet_1);
/*  220:     */       }
/*  221: 229 */       else if (LA(1) == '\r')
/*  222:     */       {
/*  223: 230 */         match('\r');
/*  224: 231 */         newline();
/*  225:     */       }
/*  226: 233 */       else if (_tokenSet_2.member(LA(1)))
/*  227:     */       {
/*  228: 235 */         match(_tokenSet_2);
/*  229:     */       }
/*  230:     */       else
/*  231:     */       {
/*  232: 239 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  233:     */       }
/*  234:     */       break;
/*  235:     */     }
/*  236: 242 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/*  237:     */     {
/*  238: 243 */       localToken = makeToken(i);
/*  239: 244 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  240:     */     }
/*  241: 246 */     this._returnToken = localToken;
/*  242:     */   }
/*  243:     */   
/*  244:     */   protected final void mAST_ITEM(boolean paramBoolean)
/*  245:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  246:     */   {
/*  247: 250 */     Token localToken1 = null;int j = this.text.length();
/*  248: 251 */     int i = 6;
/*  249:     */     
/*  250: 253 */     Token localToken2 = null;
/*  251: 254 */     Token localToken3 = null;
/*  252: 255 */     Token localToken4 = null;
/*  253:     */     int k;
/*  254: 257 */     if ((LA(1) == '#') && (LA(2) == '('))
/*  255:     */     {
/*  256: 258 */       k = this.text.length();
/*  257: 259 */       match('#');
/*  258: 260 */       this.text.setLength(k);
/*  259: 261 */       mTREE(true);
/*  260: 262 */       localToken2 = this._returnToken;
/*  261:     */     }
/*  262:     */     else
/*  263:     */     {
/*  264:     */       String str1;
/*  265: 264 */       if ((LA(1) == '#') && (_tokenSet_3.member(LA(2))))
/*  266:     */       {
/*  267: 265 */         k = this.text.length();
/*  268: 266 */         match('#');
/*  269: 267 */         this.text.setLength(k);
/*  270: 268 */         mID(true);
/*  271: 269 */         localToken3 = this._returnToken;
/*  272:     */         
/*  273: 271 */         str1 = localToken3.getText();
/*  274: 272 */         String str2 = this.generator.mapTreeId(str1, this.transInfo);
/*  275: 273 */         if (str2 != null)
/*  276:     */         {
/*  277: 274 */           this.text.setLength(j);this.text.append(str2);
/*  278:     */         }
/*  279: 278 */         if (_tokenSet_4.member(LA(1))) {
/*  280: 279 */           mWS(false);
/*  281:     */         }
/*  282: 286 */         if (LA(1) == '=') {
/*  283: 287 */           mVAR_ASSIGN(false);
/*  284:     */         }
/*  285:     */       }
/*  286: 294 */       else if ((LA(1) == '#') && (LA(2) == '['))
/*  287:     */       {
/*  288: 295 */         k = this.text.length();
/*  289: 296 */         match('#');
/*  290: 297 */         this.text.setLength(k);
/*  291: 298 */         mAST_CONSTRUCTOR(true);
/*  292: 299 */         localToken4 = this._returnToken;
/*  293:     */       }
/*  294: 301 */       else if ((LA(1) == '#') && (LA(2) == '#'))
/*  295:     */       {
/*  296: 302 */         match("##");
/*  297:     */         
/*  298: 304 */         str1 = this.currentRule.getRuleName() + "_AST";this.text.setLength(j);this.text.append(str1);
/*  299: 305 */         if (this.transInfo != null) {
/*  300: 306 */           this.transInfo.refRuleRoot = str1;
/*  301:     */         }
/*  302: 310 */         if (_tokenSet_4.member(LA(1))) {
/*  303: 311 */           mWS(false);
/*  304:     */         }
/*  305: 318 */         if (LA(1) == '=') {
/*  306: 319 */           mVAR_ASSIGN(false);
/*  307:     */         }
/*  308:     */       }
/*  309:     */       else
/*  310:     */       {
/*  311: 327 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  312:     */       }
/*  313:     */     }
/*  314: 330 */     if ((paramBoolean) && (localToken1 == null) && (i != -1))
/*  315:     */     {
/*  316: 331 */       localToken1 = makeToken(i);
/*  317: 332 */       localToken1.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  318:     */     }
/*  319: 334 */     this._returnToken = localToken1;
/*  320:     */   }
/*  321:     */   
/*  322:     */   protected final void mTEXT_ITEM(boolean paramBoolean)
/*  323:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  324:     */   {
/*  325: 338 */     Token localToken1 = null;int j = this.text.length();
/*  326: 339 */     int i = 7;
/*  327:     */     
/*  328: 341 */     Token localToken2 = null;
/*  329: 342 */     Token localToken3 = null;
/*  330: 343 */     Token localToken4 = null;
/*  331: 344 */     Token localToken5 = null;
/*  332: 345 */     Token localToken6 = null;
/*  333: 346 */     Token localToken7 = null;
/*  334:     */     String str1;
/*  335:     */     String str2;
/*  336: 348 */     if ((LA(1) == '$') && (LA(2) == 'F') && (LA(3) == 'O'))
/*  337:     */     {
/*  338: 349 */       match("$FOLLOW");
/*  339: 351 */       if ((_tokenSet_5.member(LA(1))) && (_tokenSet_6.member(LA(2))) && (LA(3) >= '\003') && (LA(3) <= 'ÿ'))
/*  340:     */       {
/*  341: 353 */         switch (LA(1))
/*  342:     */         {
/*  343:     */         case '\t': 
/*  344:     */         case '\n': 
/*  345:     */         case '\r': 
/*  346:     */         case ' ': 
/*  347: 356 */           mWS(false);
/*  348: 357 */           break;
/*  349:     */         case '(': 
/*  350:     */           break;
/*  351:     */         default: 
/*  352: 365 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  353:     */         }
/*  354: 369 */         match('(');
/*  355: 370 */         mTEXT_ARG(true);
/*  356: 371 */         localToken6 = this._returnToken;
/*  357: 372 */         match(')');
/*  358:     */       }
/*  359: 379 */       str1 = this.currentRule.getRuleName();
/*  360: 380 */       if (localToken6 != null) {
/*  361: 381 */         str1 = localToken6.getText();
/*  362:     */       }
/*  363: 383 */       str2 = this.generator.getFOLLOWBitSet(str1, 1);
/*  364: 385 */       if (str2 == null)
/*  365:     */       {
/*  366: 386 */         reportError("$FOLLOW(" + str1 + ")" + ": unknown rule or bad lookahead computation");
/*  367:     */       }
/*  368:     */       else
/*  369:     */       {
/*  370: 390 */         this.text.setLength(j);this.text.append(str2);
/*  371:     */       }
/*  372:     */     }
/*  373: 394 */     else if ((LA(1) == '$') && (LA(2) == 'F') && (LA(3) == 'I'))
/*  374:     */     {
/*  375: 395 */       match("$FIRST");
/*  376: 397 */       if ((_tokenSet_5.member(LA(1))) && (_tokenSet_6.member(LA(2))) && (LA(3) >= '\003') && (LA(3) <= 'ÿ'))
/*  377:     */       {
/*  378: 399 */         switch (LA(1))
/*  379:     */         {
/*  380:     */         case '\t': 
/*  381:     */         case '\n': 
/*  382:     */         case '\r': 
/*  383:     */         case ' ': 
/*  384: 402 */           mWS(false);
/*  385: 403 */           break;
/*  386:     */         case '(': 
/*  387:     */           break;
/*  388:     */         default: 
/*  389: 411 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  390:     */         }
/*  391: 415 */         match('(');
/*  392: 416 */         mTEXT_ARG(true);
/*  393: 417 */         localToken7 = this._returnToken;
/*  394: 418 */         match(')');
/*  395:     */       }
/*  396: 425 */       str1 = this.currentRule.getRuleName();
/*  397: 426 */       if (localToken7 != null) {
/*  398: 427 */         str1 = localToken7.getText();
/*  399:     */       }
/*  400: 429 */       str2 = this.generator.getFIRSTBitSet(str1, 1);
/*  401: 431 */       if (str2 == null)
/*  402:     */       {
/*  403: 432 */         reportError("$FIRST(" + str1 + ")" + ": unknown rule or bad lookahead computation");
/*  404:     */       }
/*  405:     */       else
/*  406:     */       {
/*  407: 436 */         this.text.setLength(j);this.text.append(str2);
/*  408:     */       }
/*  409:     */     }
/*  410: 440 */     else if ((LA(1) == '$') && (LA(2) == 'a'))
/*  411:     */     {
/*  412: 441 */       match("$append");
/*  413: 443 */       switch (LA(1))
/*  414:     */       {
/*  415:     */       case '\t': 
/*  416:     */       case '\n': 
/*  417:     */       case '\r': 
/*  418:     */       case ' ': 
/*  419: 446 */         mWS(false);
/*  420: 447 */         break;
/*  421:     */       case '(': 
/*  422:     */         break;
/*  423:     */       default: 
/*  424: 455 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  425:     */       }
/*  426: 459 */       match('(');
/*  427: 460 */       mTEXT_ARG(true);
/*  428: 461 */       localToken2 = this._returnToken;
/*  429: 462 */       match(')');
/*  430:     */       
/*  431: 464 */       str1 = "text.append(" + localToken2.getText() + ")";
/*  432: 465 */       this.text.setLength(j);this.text.append(str1);
/*  433:     */     }
/*  434: 468 */     else if ((LA(1) == '$') && (LA(2) == 's'))
/*  435:     */     {
/*  436: 469 */       match("$set");
/*  437: 471 */       if ((LA(1) == 'T') && (LA(2) == 'e'))
/*  438:     */       {
/*  439: 472 */         match("Text");
/*  440: 474 */         switch (LA(1))
/*  441:     */         {
/*  442:     */         case '\t': 
/*  443:     */         case '\n': 
/*  444:     */         case '\r': 
/*  445:     */         case ' ': 
/*  446: 477 */           mWS(false);
/*  447: 478 */           break;
/*  448:     */         case '(': 
/*  449:     */           break;
/*  450:     */         default: 
/*  451: 486 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  452:     */         }
/*  453: 490 */         match('(');
/*  454: 491 */         mTEXT_ARG(true);
/*  455: 492 */         localToken3 = this._returnToken;
/*  456: 493 */         match(')');
/*  457:     */         
/*  458:     */ 
/*  459: 496 */         str1 = "text.setLength(_begin); text.append(" + localToken3.getText() + ")";
/*  460: 497 */         this.text.setLength(j);this.text.append(str1);
/*  461:     */       }
/*  462: 500 */       else if ((LA(1) == 'T') && (LA(2) == 'o'))
/*  463:     */       {
/*  464: 501 */         match("Token");
/*  465: 503 */         switch (LA(1))
/*  466:     */         {
/*  467:     */         case '\t': 
/*  468:     */         case '\n': 
/*  469:     */         case '\r': 
/*  470:     */         case ' ': 
/*  471: 506 */           mWS(false);
/*  472: 507 */           break;
/*  473:     */         case '(': 
/*  474:     */           break;
/*  475:     */         default: 
/*  476: 515 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  477:     */         }
/*  478: 519 */         match('(');
/*  479: 520 */         mTEXT_ARG(true);
/*  480: 521 */         localToken4 = this._returnToken;
/*  481: 522 */         match(')');
/*  482:     */         
/*  483: 524 */         str1 = "_token = " + localToken4.getText();
/*  484: 525 */         this.text.setLength(j);this.text.append(str1);
/*  485:     */       }
/*  486: 528 */       else if ((LA(1) == 'T') && (LA(2) == 'y'))
/*  487:     */       {
/*  488: 529 */         match("Type");
/*  489: 531 */         switch (LA(1))
/*  490:     */         {
/*  491:     */         case '\t': 
/*  492:     */         case '\n': 
/*  493:     */         case '\r': 
/*  494:     */         case ' ': 
/*  495: 534 */           mWS(false);
/*  496: 535 */           break;
/*  497:     */         case '(': 
/*  498:     */           break;
/*  499:     */         default: 
/*  500: 543 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  501:     */         }
/*  502: 547 */         match('(');
/*  503: 548 */         mTEXT_ARG(true);
/*  504: 549 */         localToken5 = this._returnToken;
/*  505: 550 */         match(')');
/*  506:     */         
/*  507: 552 */         str1 = "_ttype = " + localToken5.getText();
/*  508: 553 */         this.text.setLength(j);this.text.append(str1);
/*  509:     */       }
/*  510:     */       else
/*  511:     */       {
/*  512: 557 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  513:     */       }
/*  514:     */     }
/*  515: 562 */     else if ((LA(1) == '$') && (LA(2) == 'g'))
/*  516:     */     {
/*  517: 563 */       match("$getText");
/*  518:     */       
/*  519: 565 */       this.text.setLength(j);this.text.append("new String(text.getBuffer(),_begin,text.length()-_begin)");
/*  520:     */     }
/*  521:     */     else
/*  522:     */     {
/*  523: 569 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  524:     */     }
/*  525: 572 */     if ((paramBoolean) && (localToken1 == null) && (i != -1))
/*  526:     */     {
/*  527: 573 */       localToken1 = makeToken(i);
/*  528: 574 */       localToken1.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  529:     */     }
/*  530: 576 */     this._returnToken = localToken1;
/*  531:     */   }
/*  532:     */   
/*  533:     */   protected final void mCOMMENT(boolean paramBoolean)
/*  534:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  535:     */   {
/*  536: 580 */     Token localToken = null;int j = this.text.length();
/*  537: 581 */     int i = 19;
/*  538: 584 */     if ((LA(1) == '/') && (LA(2) == '/')) {
/*  539: 585 */       mSL_COMMENT(false);
/*  540: 587 */     } else if ((LA(1) == '/') && (LA(2) == '*')) {
/*  541: 588 */       mML_COMMENT(false);
/*  542:     */     } else {
/*  543: 591 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  544:     */     }
/*  545: 594 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/*  546:     */     {
/*  547: 595 */       localToken = makeToken(i);
/*  548: 596 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  549:     */     }
/*  550: 598 */     this._returnToken = localToken;
/*  551:     */   }
/*  552:     */   
/*  553:     */   protected final void mSTRING(boolean paramBoolean)
/*  554:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  555:     */   {
/*  556: 602 */     Token localToken = null;int j = this.text.length();
/*  557: 603 */     int i = 23;
/*  558:     */     
/*  559:     */ 
/*  560: 606 */     match('"');
/*  561:     */     for (;;)
/*  562:     */     {
/*  563: 610 */       if (LA(1) == '\\')
/*  564:     */       {
/*  565: 611 */         mESC(false);
/*  566:     */       }
/*  567:     */       else
/*  568:     */       {
/*  569: 613 */         if (!_tokenSet_7.member(LA(1))) {
/*  570:     */           break;
/*  571:     */         }
/*  572: 614 */         matchNot('"');
/*  573:     */       }
/*  574:     */     }
/*  575: 622 */     match('"');
/*  576: 623 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/*  577:     */     {
/*  578: 624 */       localToken = makeToken(i);
/*  579: 625 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  580:     */     }
/*  581: 627 */     this._returnToken = localToken;
/*  582:     */   }
/*  583:     */   
/*  584:     */   protected final void mCHAR(boolean paramBoolean)
/*  585:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  586:     */   {
/*  587: 631 */     Token localToken = null;int j = this.text.length();
/*  588: 632 */     int i = 22;
/*  589:     */     
/*  590:     */ 
/*  591: 635 */     match('\'');
/*  592: 637 */     if (LA(1) == '\\') {
/*  593: 638 */       mESC(false);
/*  594: 640 */     } else if (_tokenSet_8.member(LA(1))) {
/*  595: 641 */       matchNot('\'');
/*  596:     */     } else {
/*  597: 644 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  598:     */     }
/*  599: 648 */     match('\'');
/*  600: 649 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/*  601:     */     {
/*  602: 650 */       localToken = makeToken(i);
/*  603: 651 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  604:     */     }
/*  605: 653 */     this._returnToken = localToken;
/*  606:     */   }
/*  607:     */   
/*  608:     */   protected final void mTREE(boolean paramBoolean)
/*  609:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  610:     */   {
/*  611: 657 */     Token localToken1 = null;int j = this.text.length();
/*  612: 658 */     int i = 8;
/*  613:     */     
/*  614: 660 */     Token localToken2 = null;
/*  615: 661 */     Token localToken3 = null;
/*  616:     */     
/*  617: 663 */     StringBuffer localStringBuffer = new StringBuffer();
/*  618: 664 */     int m = 0;
/*  619: 665 */     Vector localVector = new Vector(10);
/*  620:     */     
/*  621:     */ 
/*  622: 668 */     int k = this.text.length();
/*  623: 669 */     match('(');
/*  624: 670 */     this.text.setLength(k);
/*  625: 672 */     switch (LA(1))
/*  626:     */     {
/*  627:     */     case '\t': 
/*  628:     */     case '\n': 
/*  629:     */     case '\r': 
/*  630:     */     case ' ': 
/*  631: 675 */       k = this.text.length();
/*  632: 676 */       mWS(false);
/*  633: 677 */       this.text.setLength(k);
/*  634: 678 */       break;
/*  635:     */     case '"': 
/*  636:     */     case '#': 
/*  637:     */     case '(': 
/*  638:     */     case 'A': 
/*  639:     */     case 'B': 
/*  640:     */     case 'C': 
/*  641:     */     case 'D': 
/*  642:     */     case 'E': 
/*  643:     */     case 'F': 
/*  644:     */     case 'G': 
/*  645:     */     case 'H': 
/*  646:     */     case 'I': 
/*  647:     */     case 'J': 
/*  648:     */     case 'K': 
/*  649:     */     case 'L': 
/*  650:     */     case 'M': 
/*  651:     */     case 'N': 
/*  652:     */     case 'O': 
/*  653:     */     case 'P': 
/*  654:     */     case 'Q': 
/*  655:     */     case 'R': 
/*  656:     */     case 'S': 
/*  657:     */     case 'T': 
/*  658:     */     case 'U': 
/*  659:     */     case 'V': 
/*  660:     */     case 'W': 
/*  661:     */     case 'X': 
/*  662:     */     case 'Y': 
/*  663:     */     case 'Z': 
/*  664:     */     case '[': 
/*  665:     */     case '_': 
/*  666:     */     case 'a': 
/*  667:     */     case 'b': 
/*  668:     */     case 'c': 
/*  669:     */     case 'd': 
/*  670:     */     case 'e': 
/*  671:     */     case 'f': 
/*  672:     */     case 'g': 
/*  673:     */     case 'h': 
/*  674:     */     case 'i': 
/*  675:     */     case 'j': 
/*  676:     */     case 'k': 
/*  677:     */     case 'l': 
/*  678:     */     case 'm': 
/*  679:     */     case 'n': 
/*  680:     */     case 'o': 
/*  681:     */     case 'p': 
/*  682:     */     case 'q': 
/*  683:     */     case 'r': 
/*  684:     */     case 's': 
/*  685:     */     case 't': 
/*  686:     */     case 'u': 
/*  687:     */     case 'v': 
/*  688:     */     case 'w': 
/*  689:     */     case 'x': 
/*  690:     */     case 'y': 
/*  691:     */     case 'z': 
/*  692:     */       break;
/*  693:     */     case '\013': 
/*  694:     */     case '\f': 
/*  695:     */     case '\016': 
/*  696:     */     case '\017': 
/*  697:     */     case '\020': 
/*  698:     */     case '\021': 
/*  699:     */     case '\022': 
/*  700:     */     case '\023': 
/*  701:     */     case '\024': 
/*  702:     */     case '\025': 
/*  703:     */     case '\026': 
/*  704:     */     case '\027': 
/*  705:     */     case '\030': 
/*  706:     */     case '\031': 
/*  707:     */     case '\032': 
/*  708:     */     case '\033': 
/*  709:     */     case '\034': 
/*  710:     */     case '\035': 
/*  711:     */     case '\036': 
/*  712:     */     case '\037': 
/*  713:     */     case '!': 
/*  714:     */     case '$': 
/*  715:     */     case '%': 
/*  716:     */     case '&': 
/*  717:     */     case '\'': 
/*  718:     */     case ')': 
/*  719:     */     case '*': 
/*  720:     */     case '+': 
/*  721:     */     case ',': 
/*  722:     */     case '-': 
/*  723:     */     case '.': 
/*  724:     */     case '/': 
/*  725:     */     case '0': 
/*  726:     */     case '1': 
/*  727:     */     case '2': 
/*  728:     */     case '3': 
/*  729:     */     case '4': 
/*  730:     */     case '5': 
/*  731:     */     case '6': 
/*  732:     */     case '7': 
/*  733:     */     case '8': 
/*  734:     */     case '9': 
/*  735:     */     case ':': 
/*  736:     */     case ';': 
/*  737:     */     case '<': 
/*  738:     */     case '=': 
/*  739:     */     case '>': 
/*  740:     */     case '?': 
/*  741:     */     case '@': 
/*  742:     */     case '\\': 
/*  743:     */     case ']': 
/*  744:     */     case '^': 
/*  745:     */     case '`': 
/*  746:     */     default: 
/*  747: 700 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  748:     */     }
/*  749: 704 */     k = this.text.length();
/*  750: 705 */     mTREE_ELEMENT(true);
/*  751: 706 */     this.text.setLength(k);
/*  752: 707 */     localToken2 = this._returnToken;
/*  753: 708 */     localVector.appendElement(localToken2.getText());
/*  754: 710 */     switch (LA(1))
/*  755:     */     {
/*  756:     */     case '\t': 
/*  757:     */     case '\n': 
/*  758:     */     case '\r': 
/*  759:     */     case ' ': 
/*  760: 713 */       k = this.text.length();
/*  761: 714 */       mWS(false);
/*  762: 715 */       this.text.setLength(k);
/*  763: 716 */       break;
/*  764:     */     case ')': 
/*  765:     */     case ',': 
/*  766:     */       break;
/*  767:     */     default: 
/*  768: 724 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  769:     */     }
/*  770: 731 */     while (LA(1) == ',')
/*  771:     */     {
/*  772: 732 */       k = this.text.length();
/*  773: 733 */       match(',');
/*  774: 734 */       this.text.setLength(k);
/*  775: 736 */       switch (LA(1))
/*  776:     */       {
/*  777:     */       case '\t': 
/*  778:     */       case '\n': 
/*  779:     */       case '\r': 
/*  780:     */       case ' ': 
/*  781: 739 */         k = this.text.length();
/*  782: 740 */         mWS(false);
/*  783: 741 */         this.text.setLength(k);
/*  784: 742 */         break;
/*  785:     */       case '"': 
/*  786:     */       case '#': 
/*  787:     */       case '(': 
/*  788:     */       case 'A': 
/*  789:     */       case 'B': 
/*  790:     */       case 'C': 
/*  791:     */       case 'D': 
/*  792:     */       case 'E': 
/*  793:     */       case 'F': 
/*  794:     */       case 'G': 
/*  795:     */       case 'H': 
/*  796:     */       case 'I': 
/*  797:     */       case 'J': 
/*  798:     */       case 'K': 
/*  799:     */       case 'L': 
/*  800:     */       case 'M': 
/*  801:     */       case 'N': 
/*  802:     */       case 'O': 
/*  803:     */       case 'P': 
/*  804:     */       case 'Q': 
/*  805:     */       case 'R': 
/*  806:     */       case 'S': 
/*  807:     */       case 'T': 
/*  808:     */       case 'U': 
/*  809:     */       case 'V': 
/*  810:     */       case 'W': 
/*  811:     */       case 'X': 
/*  812:     */       case 'Y': 
/*  813:     */       case 'Z': 
/*  814:     */       case '[': 
/*  815:     */       case '_': 
/*  816:     */       case 'a': 
/*  817:     */       case 'b': 
/*  818:     */       case 'c': 
/*  819:     */       case 'd': 
/*  820:     */       case 'e': 
/*  821:     */       case 'f': 
/*  822:     */       case 'g': 
/*  823:     */       case 'h': 
/*  824:     */       case 'i': 
/*  825:     */       case 'j': 
/*  826:     */       case 'k': 
/*  827:     */       case 'l': 
/*  828:     */       case 'm': 
/*  829:     */       case 'n': 
/*  830:     */       case 'o': 
/*  831:     */       case 'p': 
/*  832:     */       case 'q': 
/*  833:     */       case 'r': 
/*  834:     */       case 's': 
/*  835:     */       case 't': 
/*  836:     */       case 'u': 
/*  837:     */       case 'v': 
/*  838:     */       case 'w': 
/*  839:     */       case 'x': 
/*  840:     */       case 'y': 
/*  841:     */       case 'z': 
/*  842:     */         break;
/*  843:     */       case '\013': 
/*  844:     */       case '\f': 
/*  845:     */       case '\016': 
/*  846:     */       case '\017': 
/*  847:     */       case '\020': 
/*  848:     */       case '\021': 
/*  849:     */       case '\022': 
/*  850:     */       case '\023': 
/*  851:     */       case '\024': 
/*  852:     */       case '\025': 
/*  853:     */       case '\026': 
/*  854:     */       case '\027': 
/*  855:     */       case '\030': 
/*  856:     */       case '\031': 
/*  857:     */       case '\032': 
/*  858:     */       case '\033': 
/*  859:     */       case '\034': 
/*  860:     */       case '\035': 
/*  861:     */       case '\036': 
/*  862:     */       case '\037': 
/*  863:     */       case '!': 
/*  864:     */       case '$': 
/*  865:     */       case '%': 
/*  866:     */       case '&': 
/*  867:     */       case '\'': 
/*  868:     */       case ')': 
/*  869:     */       case '*': 
/*  870:     */       case '+': 
/*  871:     */       case ',': 
/*  872:     */       case '-': 
/*  873:     */       case '.': 
/*  874:     */       case '/': 
/*  875:     */       case '0': 
/*  876:     */       case '1': 
/*  877:     */       case '2': 
/*  878:     */       case '3': 
/*  879:     */       case '4': 
/*  880:     */       case '5': 
/*  881:     */       case '6': 
/*  882:     */       case '7': 
/*  883:     */       case '8': 
/*  884:     */       case '9': 
/*  885:     */       case ':': 
/*  886:     */       case ';': 
/*  887:     */       case '<': 
/*  888:     */       case '=': 
/*  889:     */       case '>': 
/*  890:     */       case '?': 
/*  891:     */       case '@': 
/*  892:     */       case '\\': 
/*  893:     */       case ']': 
/*  894:     */       case '^': 
/*  895:     */       case '`': 
/*  896:     */       default: 
/*  897: 764 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  898:     */       }
/*  899: 768 */       k = this.text.length();
/*  900: 769 */       mTREE_ELEMENT(true);
/*  901: 770 */       this.text.setLength(k);
/*  902: 771 */       localToken3 = this._returnToken;
/*  903: 772 */       localVector.appendElement(localToken3.getText());
/*  904: 774 */       switch (LA(1))
/*  905:     */       {
/*  906:     */       case '\t': 
/*  907:     */       case '\n': 
/*  908:     */       case '\r': 
/*  909:     */       case ' ': 
/*  910: 777 */         k = this.text.length();
/*  911: 778 */         mWS(false);
/*  912: 779 */         this.text.setLength(k);
/*  913: 780 */         break;
/*  914:     */       case ')': 
/*  915:     */       case ',': 
/*  916:     */         break;
/*  917:     */       default: 
/*  918: 788 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  919:     */       }
/*  920:     */     }
/*  921: 799 */     this.text.setLength(j);this.text.append(this.generator.getASTCreateString(localVector));
/*  922: 800 */     k = this.text.length();
/*  923: 801 */     match(')');
/*  924: 802 */     this.text.setLength(k);
/*  925: 803 */     if ((paramBoolean) && (localToken1 == null) && (i != -1))
/*  926:     */     {
/*  927: 804 */       localToken1 = makeToken(i);
/*  928: 805 */       localToken1.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  929:     */     }
/*  930: 807 */     this._returnToken = localToken1;
/*  931:     */   }
/*  932:     */   
/*  933:     */   protected final void mID(boolean paramBoolean)
/*  934:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  935:     */   {
/*  936: 811 */     Token localToken = null;int j = this.text.length();
/*  937: 812 */     int i = 17;
/*  938: 816 */     switch (LA(1))
/*  939:     */     {
/*  940:     */     case 'a': 
/*  941:     */     case 'b': 
/*  942:     */     case 'c': 
/*  943:     */     case 'd': 
/*  944:     */     case 'e': 
/*  945:     */     case 'f': 
/*  946:     */     case 'g': 
/*  947:     */     case 'h': 
/*  948:     */     case 'i': 
/*  949:     */     case 'j': 
/*  950:     */     case 'k': 
/*  951:     */     case 'l': 
/*  952:     */     case 'm': 
/*  953:     */     case 'n': 
/*  954:     */     case 'o': 
/*  955:     */     case 'p': 
/*  956:     */     case 'q': 
/*  957:     */     case 'r': 
/*  958:     */     case 's': 
/*  959:     */     case 't': 
/*  960:     */     case 'u': 
/*  961:     */     case 'v': 
/*  962:     */     case 'w': 
/*  963:     */     case 'x': 
/*  964:     */     case 'y': 
/*  965:     */     case 'z': 
/*  966: 825 */       matchRange('a', 'z');
/*  967: 826 */       break;
/*  968:     */     case 'A': 
/*  969:     */     case 'B': 
/*  970:     */     case 'C': 
/*  971:     */     case 'D': 
/*  972:     */     case 'E': 
/*  973:     */     case 'F': 
/*  974:     */     case 'G': 
/*  975:     */     case 'H': 
/*  976:     */     case 'I': 
/*  977:     */     case 'J': 
/*  978:     */     case 'K': 
/*  979:     */     case 'L': 
/*  980:     */     case 'M': 
/*  981:     */     case 'N': 
/*  982:     */     case 'O': 
/*  983:     */     case 'P': 
/*  984:     */     case 'Q': 
/*  985:     */     case 'R': 
/*  986:     */     case 'S': 
/*  987:     */     case 'T': 
/*  988:     */     case 'U': 
/*  989:     */     case 'V': 
/*  990:     */     case 'W': 
/*  991:     */     case 'X': 
/*  992:     */     case 'Y': 
/*  993:     */     case 'Z': 
/*  994: 836 */       matchRange('A', 'Z');
/*  995: 837 */       break;
/*  996:     */     case '_': 
/*  997: 841 */       match('_');
/*  998: 842 */       break;
/*  999:     */     case '[': 
/* 1000:     */     case '\\': 
/* 1001:     */     case ']': 
/* 1002:     */     case '^': 
/* 1003:     */     case '`': 
/* 1004:     */     default: 
/* 1005: 846 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1006:     */     }
/* 1007: 853 */     while (_tokenSet_9.member(LA(1))) {
/* 1008: 855 */       switch (LA(1))
/* 1009:     */       {
/* 1010:     */       case 'a': 
/* 1011:     */       case 'b': 
/* 1012:     */       case 'c': 
/* 1013:     */       case 'd': 
/* 1014:     */       case 'e': 
/* 1015:     */       case 'f': 
/* 1016:     */       case 'g': 
/* 1017:     */       case 'h': 
/* 1018:     */       case 'i': 
/* 1019:     */       case 'j': 
/* 1020:     */       case 'k': 
/* 1021:     */       case 'l': 
/* 1022:     */       case 'm': 
/* 1023:     */       case 'n': 
/* 1024:     */       case 'o': 
/* 1025:     */       case 'p': 
/* 1026:     */       case 'q': 
/* 1027:     */       case 'r': 
/* 1028:     */       case 's': 
/* 1029:     */       case 't': 
/* 1030:     */       case 'u': 
/* 1031:     */       case 'v': 
/* 1032:     */       case 'w': 
/* 1033:     */       case 'x': 
/* 1034:     */       case 'y': 
/* 1035:     */       case 'z': 
/* 1036: 864 */         matchRange('a', 'z');
/* 1037: 865 */         break;
/* 1038:     */       case 'A': 
/* 1039:     */       case 'B': 
/* 1040:     */       case 'C': 
/* 1041:     */       case 'D': 
/* 1042:     */       case 'E': 
/* 1043:     */       case 'F': 
/* 1044:     */       case 'G': 
/* 1045:     */       case 'H': 
/* 1046:     */       case 'I': 
/* 1047:     */       case 'J': 
/* 1048:     */       case 'K': 
/* 1049:     */       case 'L': 
/* 1050:     */       case 'M': 
/* 1051:     */       case 'N': 
/* 1052:     */       case 'O': 
/* 1053:     */       case 'P': 
/* 1054:     */       case 'Q': 
/* 1055:     */       case 'R': 
/* 1056:     */       case 'S': 
/* 1057:     */       case 'T': 
/* 1058:     */       case 'U': 
/* 1059:     */       case 'V': 
/* 1060:     */       case 'W': 
/* 1061:     */       case 'X': 
/* 1062:     */       case 'Y': 
/* 1063:     */       case 'Z': 
/* 1064: 875 */         matchRange('A', 'Z');
/* 1065: 876 */         break;
/* 1066:     */       case '0': 
/* 1067:     */       case '1': 
/* 1068:     */       case '2': 
/* 1069:     */       case '3': 
/* 1070:     */       case '4': 
/* 1071:     */       case '5': 
/* 1072:     */       case '6': 
/* 1073:     */       case '7': 
/* 1074:     */       case '8': 
/* 1075:     */       case '9': 
/* 1076: 882 */         matchRange('0', '9');
/* 1077: 883 */         break;
/* 1078:     */       case '_': 
/* 1079: 887 */         match('_');
/* 1080: 888 */         break;
/* 1081:     */       case ':': 
/* 1082:     */       case ';': 
/* 1083:     */       case '<': 
/* 1084:     */       case '=': 
/* 1085:     */       case '>': 
/* 1086:     */       case '?': 
/* 1087:     */       case '@': 
/* 1088:     */       case '[': 
/* 1089:     */       case '\\': 
/* 1090:     */       case ']': 
/* 1091:     */       case '^': 
/* 1092:     */       case '`': 
/* 1093:     */       default: 
/* 1094: 892 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1095:     */       }
/* 1096:     */     }
/* 1097: 903 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 1098:     */     {
/* 1099: 904 */       localToken = makeToken(i);
/* 1100: 905 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 1101:     */     }
/* 1102: 907 */     this._returnToken = localToken;
/* 1103:     */   }
/* 1104:     */   
/* 1105:     */   protected final void mWS(boolean paramBoolean)
/* 1106:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 1107:     */   {
/* 1108: 911 */     Token localToken = null;int j = this.text.length();
/* 1109: 912 */     int i = 28;
/* 1110:     */     
/* 1111:     */ 
/* 1112:     */ 
/* 1113: 916 */     int k = 0;
/* 1114:     */     for (;;)
/* 1115:     */     {
/* 1116: 919 */       if ((LA(1) == '\r') && (LA(2) == '\n'))
/* 1117:     */       {
/* 1118: 920 */         match('\r');
/* 1119: 921 */         match('\n');
/* 1120: 922 */         newline();
/* 1121:     */       }
/* 1122: 924 */       else if (LA(1) == ' ')
/* 1123:     */       {
/* 1124: 925 */         match(' ');
/* 1125:     */       }
/* 1126: 927 */       else if (LA(1) == '\t')
/* 1127:     */       {
/* 1128: 928 */         match('\t');
/* 1129:     */       }
/* 1130: 930 */       else if (LA(1) == '\r')
/* 1131:     */       {
/* 1132: 931 */         match('\r');
/* 1133: 932 */         newline();
/* 1134:     */       }
/* 1135: 934 */       else if (LA(1) == '\n')
/* 1136:     */       {
/* 1137: 935 */         match('\n');
/* 1138: 936 */         newline();
/* 1139:     */       }
/* 1140:     */       else
/* 1141:     */       {
/* 1142: 939 */         if (k >= 1) {
/* 1143:     */           break;
/* 1144:     */         }
/* 1145: 939 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1146:     */       }
/* 1147: 942 */       k++;
/* 1148:     */     }
/* 1149: 945 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 1150:     */     {
/* 1151: 946 */       localToken = makeToken(i);
/* 1152: 947 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 1153:     */     }
/* 1154: 949 */     this._returnToken = localToken;
/* 1155:     */   }
/* 1156:     */   
/* 1157:     */   protected final void mVAR_ASSIGN(boolean paramBoolean)
/* 1158:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 1159:     */   {
/* 1160: 953 */     Token localToken = null;int j = this.text.length();
/* 1161: 954 */     int i = 18;
/* 1162:     */     
/* 1163:     */ 
/* 1164: 957 */     match('=');
/* 1165: 961 */     if ((LA(1) != '=') && (this.transInfo != null) && (this.transInfo.refRuleRoot != null)) {
/* 1166: 962 */       this.transInfo.assignToRoot = true;
/* 1167:     */     }
/* 1168: 965 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 1169:     */     {
/* 1170: 966 */       localToken = makeToken(i);
/* 1171: 967 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 1172:     */     }
/* 1173: 969 */     this._returnToken = localToken;
/* 1174:     */   }
/* 1175:     */   
/* 1176:     */   protected final void mAST_CONSTRUCTOR(boolean paramBoolean)
/* 1177:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 1178:     */   {
/* 1179: 973 */     Token localToken1 = null;int j = this.text.length();
/* 1180: 974 */     int i = 10;
/* 1181:     */     
/* 1182: 976 */     Token localToken2 = null;
/* 1183: 977 */     Token localToken3 = null;
/* 1184: 978 */     Token localToken4 = null;
/* 1185:     */     
/* 1186: 980 */     int k = this.text.length();
/* 1187: 981 */     match('[');
/* 1188: 982 */     this.text.setLength(k);
/* 1189: 984 */     switch (LA(1))
/* 1190:     */     {
/* 1191:     */     case '\t': 
/* 1192:     */     case '\n': 
/* 1193:     */     case '\r': 
/* 1194:     */     case ' ': 
/* 1195: 987 */       k = this.text.length();
/* 1196: 988 */       mWS(false);
/* 1197: 989 */       this.text.setLength(k);
/* 1198: 990 */       break;
/* 1199:     */     case '"': 
/* 1200:     */     case '#': 
/* 1201:     */     case '(': 
/* 1202:     */     case '0': 
/* 1203:     */     case '1': 
/* 1204:     */     case '2': 
/* 1205:     */     case '3': 
/* 1206:     */     case '4': 
/* 1207:     */     case '5': 
/* 1208:     */     case '6': 
/* 1209:     */     case '7': 
/* 1210:     */     case '8': 
/* 1211:     */     case '9': 
/* 1212:     */     case 'A': 
/* 1213:     */     case 'B': 
/* 1214:     */     case 'C': 
/* 1215:     */     case 'D': 
/* 1216:     */     case 'E': 
/* 1217:     */     case 'F': 
/* 1218:     */     case 'G': 
/* 1219:     */     case 'H': 
/* 1220:     */     case 'I': 
/* 1221:     */     case 'J': 
/* 1222:     */     case 'K': 
/* 1223:     */     case 'L': 
/* 1224:     */     case 'M': 
/* 1225:     */     case 'N': 
/* 1226:     */     case 'O': 
/* 1227:     */     case 'P': 
/* 1228:     */     case 'Q': 
/* 1229:     */     case 'R': 
/* 1230:     */     case 'S': 
/* 1231:     */     case 'T': 
/* 1232:     */     case 'U': 
/* 1233:     */     case 'V': 
/* 1234:     */     case 'W': 
/* 1235:     */     case 'X': 
/* 1236:     */     case 'Y': 
/* 1237:     */     case 'Z': 
/* 1238:     */     case '[': 
/* 1239:     */     case '_': 
/* 1240:     */     case 'a': 
/* 1241:     */     case 'b': 
/* 1242:     */     case 'c': 
/* 1243:     */     case 'd': 
/* 1244:     */     case 'e': 
/* 1245:     */     case 'f': 
/* 1246:     */     case 'g': 
/* 1247:     */     case 'h': 
/* 1248:     */     case 'i': 
/* 1249:     */     case 'j': 
/* 1250:     */     case 'k': 
/* 1251:     */     case 'l': 
/* 1252:     */     case 'm': 
/* 1253:     */     case 'n': 
/* 1254:     */     case 'o': 
/* 1255:     */     case 'p': 
/* 1256:     */     case 'q': 
/* 1257:     */     case 'r': 
/* 1258:     */     case 's': 
/* 1259:     */     case 't': 
/* 1260:     */     case 'u': 
/* 1261:     */     case 'v': 
/* 1262:     */     case 'w': 
/* 1263:     */     case 'x': 
/* 1264:     */     case 'y': 
/* 1265:     */     case 'z': 
/* 1266:     */       break;
/* 1267:     */     case '\013': 
/* 1268:     */     case '\f': 
/* 1269:     */     case '\016': 
/* 1270:     */     case '\017': 
/* 1271:     */     case '\020': 
/* 1272:     */     case '\021': 
/* 1273:     */     case '\022': 
/* 1274:     */     case '\023': 
/* 1275:     */     case '\024': 
/* 1276:     */     case '\025': 
/* 1277:     */     case '\026': 
/* 1278:     */     case '\027': 
/* 1279:     */     case '\030': 
/* 1280:     */     case '\031': 
/* 1281:     */     case '\032': 
/* 1282:     */     case '\033': 
/* 1283:     */     case '\034': 
/* 1284:     */     case '\035': 
/* 1285:     */     case '\036': 
/* 1286:     */     case '\037': 
/* 1287:     */     case '!': 
/* 1288:     */     case '$': 
/* 1289:     */     case '%': 
/* 1290:     */     case '&': 
/* 1291:     */     case '\'': 
/* 1292:     */     case ')': 
/* 1293:     */     case '*': 
/* 1294:     */     case '+': 
/* 1295:     */     case ',': 
/* 1296:     */     case '-': 
/* 1297:     */     case '.': 
/* 1298:     */     case '/': 
/* 1299:     */     case ':': 
/* 1300:     */     case ';': 
/* 1301:     */     case '<': 
/* 1302:     */     case '=': 
/* 1303:     */     case '>': 
/* 1304:     */     case '?': 
/* 1305:     */     case '@': 
/* 1306:     */     case '\\': 
/* 1307:     */     case ']': 
/* 1308:     */     case '^': 
/* 1309:     */     case '`': 
/* 1310:     */     default: 
/* 1311:1014 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1312:     */     }
/* 1313:1018 */     k = this.text.length();
/* 1314:1019 */     mAST_CTOR_ELEMENT(true);
/* 1315:1020 */     this.text.setLength(k);
/* 1316:1021 */     localToken2 = this._returnToken;
/* 1317:1023 */     switch (LA(1))
/* 1318:     */     {
/* 1319:     */     case '\t': 
/* 1320:     */     case '\n': 
/* 1321:     */     case '\r': 
/* 1322:     */     case ' ': 
/* 1323:1026 */       k = this.text.length();
/* 1324:1027 */       mWS(false);
/* 1325:1028 */       this.text.setLength(k);
/* 1326:1029 */       break;
/* 1327:     */     case ',': 
/* 1328:     */     case ']': 
/* 1329:     */       break;
/* 1330:     */     default: 
/* 1331:1037 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1332:     */     }
/* 1333:1042 */     if ((LA(1) == ',') && (_tokenSet_10.member(LA(2))) && (LA(3) >= '\003') && (LA(3) <= 'ÿ'))
/* 1334:     */     {
/* 1335:1043 */       k = this.text.length();
/* 1336:1044 */       match(',');
/* 1337:1045 */       this.text.setLength(k);
/* 1338:1047 */       switch (LA(1))
/* 1339:     */       {
/* 1340:     */       case '\t': 
/* 1341:     */       case '\n': 
/* 1342:     */       case '\r': 
/* 1343:     */       case ' ': 
/* 1344:1050 */         k = this.text.length();
/* 1345:1051 */         mWS(false);
/* 1346:1052 */         this.text.setLength(k);
/* 1347:1053 */         break;
/* 1348:     */       case '"': 
/* 1349:     */       case '#': 
/* 1350:     */       case '(': 
/* 1351:     */       case '0': 
/* 1352:     */       case '1': 
/* 1353:     */       case '2': 
/* 1354:     */       case '3': 
/* 1355:     */       case '4': 
/* 1356:     */       case '5': 
/* 1357:     */       case '6': 
/* 1358:     */       case '7': 
/* 1359:     */       case '8': 
/* 1360:     */       case '9': 
/* 1361:     */       case 'A': 
/* 1362:     */       case 'B': 
/* 1363:     */       case 'C': 
/* 1364:     */       case 'D': 
/* 1365:     */       case 'E': 
/* 1366:     */       case 'F': 
/* 1367:     */       case 'G': 
/* 1368:     */       case 'H': 
/* 1369:     */       case 'I': 
/* 1370:     */       case 'J': 
/* 1371:     */       case 'K': 
/* 1372:     */       case 'L': 
/* 1373:     */       case 'M': 
/* 1374:     */       case 'N': 
/* 1375:     */       case 'O': 
/* 1376:     */       case 'P': 
/* 1377:     */       case 'Q': 
/* 1378:     */       case 'R': 
/* 1379:     */       case 'S': 
/* 1380:     */       case 'T': 
/* 1381:     */       case 'U': 
/* 1382:     */       case 'V': 
/* 1383:     */       case 'W': 
/* 1384:     */       case 'X': 
/* 1385:     */       case 'Y': 
/* 1386:     */       case 'Z': 
/* 1387:     */       case '[': 
/* 1388:     */       case '_': 
/* 1389:     */       case 'a': 
/* 1390:     */       case 'b': 
/* 1391:     */       case 'c': 
/* 1392:     */       case 'd': 
/* 1393:     */       case 'e': 
/* 1394:     */       case 'f': 
/* 1395:     */       case 'g': 
/* 1396:     */       case 'h': 
/* 1397:     */       case 'i': 
/* 1398:     */       case 'j': 
/* 1399:     */       case 'k': 
/* 1400:     */       case 'l': 
/* 1401:     */       case 'm': 
/* 1402:     */       case 'n': 
/* 1403:     */       case 'o': 
/* 1404:     */       case 'p': 
/* 1405:     */       case 'q': 
/* 1406:     */       case 'r': 
/* 1407:     */       case 's': 
/* 1408:     */       case 't': 
/* 1409:     */       case 'u': 
/* 1410:     */       case 'v': 
/* 1411:     */       case 'w': 
/* 1412:     */       case 'x': 
/* 1413:     */       case 'y': 
/* 1414:     */       case 'z': 
/* 1415:     */         break;
/* 1416:     */       case '\013': 
/* 1417:     */       case '\f': 
/* 1418:     */       case '\016': 
/* 1419:     */       case '\017': 
/* 1420:     */       case '\020': 
/* 1421:     */       case '\021': 
/* 1422:     */       case '\022': 
/* 1423:     */       case '\023': 
/* 1424:     */       case '\024': 
/* 1425:     */       case '\025': 
/* 1426:     */       case '\026': 
/* 1427:     */       case '\027': 
/* 1428:     */       case '\030': 
/* 1429:     */       case '\031': 
/* 1430:     */       case '\032': 
/* 1431:     */       case '\033': 
/* 1432:     */       case '\034': 
/* 1433:     */       case '\035': 
/* 1434:     */       case '\036': 
/* 1435:     */       case '\037': 
/* 1436:     */       case '!': 
/* 1437:     */       case '$': 
/* 1438:     */       case '%': 
/* 1439:     */       case '&': 
/* 1440:     */       case '\'': 
/* 1441:     */       case ')': 
/* 1442:     */       case '*': 
/* 1443:     */       case '+': 
/* 1444:     */       case ',': 
/* 1445:     */       case '-': 
/* 1446:     */       case '.': 
/* 1447:     */       case '/': 
/* 1448:     */       case ':': 
/* 1449:     */       case ';': 
/* 1450:     */       case '<': 
/* 1451:     */       case '=': 
/* 1452:     */       case '>': 
/* 1453:     */       case '?': 
/* 1454:     */       case '@': 
/* 1455:     */       case '\\': 
/* 1456:     */       case ']': 
/* 1457:     */       case '^': 
/* 1458:     */       case '`': 
/* 1459:     */       default: 
/* 1460:1077 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1461:     */       }
/* 1462:1081 */       k = this.text.length();
/* 1463:1082 */       mAST_CTOR_ELEMENT(true);
/* 1464:1083 */       this.text.setLength(k);
/* 1465:1084 */       localToken3 = this._returnToken;
/* 1466:     */     }
/* 1467:1086 */     switch (LA(1))
/* 1468:     */     {
/* 1469:     */     case '\t': 
/* 1470:     */     case '\n': 
/* 1471:     */     case '\r': 
/* 1472:     */     case ' ': 
/* 1473:1089 */       k = this.text.length();
/* 1474:1090 */       mWS(false);
/* 1475:1091 */       this.text.setLength(k);
/* 1476:1092 */       break;
/* 1477:     */     case ',': 
/* 1478:     */     case ']': 
/* 1479:     */       break;
/* 1480:     */     default: 
/* 1481:1100 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1482:1105 */       if ((LA(1) != ',') && (LA(1) != ']')) {
/* 1483:1108 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1484:     */       }
/* 1485:     */       break;
/* 1486:     */     }
/* 1487:1113 */     switch (LA(1))
/* 1488:     */     {
/* 1489:     */     case ',': 
/* 1490:1116 */       k = this.text.length();
/* 1491:1117 */       match(',');
/* 1492:1118 */       this.text.setLength(k);
/* 1493:1120 */       switch (LA(1))
/* 1494:     */       {
/* 1495:     */       case '\t': 
/* 1496:     */       case '\n': 
/* 1497:     */       case '\r': 
/* 1498:     */       case ' ': 
/* 1499:1123 */         k = this.text.length();
/* 1500:1124 */         mWS(false);
/* 1501:1125 */         this.text.setLength(k);
/* 1502:1126 */         break;
/* 1503:     */       case '"': 
/* 1504:     */       case '#': 
/* 1505:     */       case '(': 
/* 1506:     */       case '0': 
/* 1507:     */       case '1': 
/* 1508:     */       case '2': 
/* 1509:     */       case '3': 
/* 1510:     */       case '4': 
/* 1511:     */       case '5': 
/* 1512:     */       case '6': 
/* 1513:     */       case '7': 
/* 1514:     */       case '8': 
/* 1515:     */       case '9': 
/* 1516:     */       case 'A': 
/* 1517:     */       case 'B': 
/* 1518:     */       case 'C': 
/* 1519:     */       case 'D': 
/* 1520:     */       case 'E': 
/* 1521:     */       case 'F': 
/* 1522:     */       case 'G': 
/* 1523:     */       case 'H': 
/* 1524:     */       case 'I': 
/* 1525:     */       case 'J': 
/* 1526:     */       case 'K': 
/* 1527:     */       case 'L': 
/* 1528:     */       case 'M': 
/* 1529:     */       case 'N': 
/* 1530:     */       case 'O': 
/* 1531:     */       case 'P': 
/* 1532:     */       case 'Q': 
/* 1533:     */       case 'R': 
/* 1534:     */       case 'S': 
/* 1535:     */       case 'T': 
/* 1536:     */       case 'U': 
/* 1537:     */       case 'V': 
/* 1538:     */       case 'W': 
/* 1539:     */       case 'X': 
/* 1540:     */       case 'Y': 
/* 1541:     */       case 'Z': 
/* 1542:     */       case '[': 
/* 1543:     */       case '_': 
/* 1544:     */       case 'a': 
/* 1545:     */       case 'b': 
/* 1546:     */       case 'c': 
/* 1547:     */       case 'd': 
/* 1548:     */       case 'e': 
/* 1549:     */       case 'f': 
/* 1550:     */       case 'g': 
/* 1551:     */       case 'h': 
/* 1552:     */       case 'i': 
/* 1553:     */       case 'j': 
/* 1554:     */       case 'k': 
/* 1555:     */       case 'l': 
/* 1556:     */       case 'm': 
/* 1557:     */       case 'n': 
/* 1558:     */       case 'o': 
/* 1559:     */       case 'p': 
/* 1560:     */       case 'q': 
/* 1561:     */       case 'r': 
/* 1562:     */       case 's': 
/* 1563:     */       case 't': 
/* 1564:     */       case 'u': 
/* 1565:     */       case 'v': 
/* 1566:     */       case 'w': 
/* 1567:     */       case 'x': 
/* 1568:     */       case 'y': 
/* 1569:     */       case 'z': 
/* 1570:     */         break;
/* 1571:     */       case '\013': 
/* 1572:     */       case '\f': 
/* 1573:     */       case '\016': 
/* 1574:     */       case '\017': 
/* 1575:     */       case '\020': 
/* 1576:     */       case '\021': 
/* 1577:     */       case '\022': 
/* 1578:     */       case '\023': 
/* 1579:     */       case '\024': 
/* 1580:     */       case '\025': 
/* 1581:     */       case '\026': 
/* 1582:     */       case '\027': 
/* 1583:     */       case '\030': 
/* 1584:     */       case '\031': 
/* 1585:     */       case '\032': 
/* 1586:     */       case '\033': 
/* 1587:     */       case '\034': 
/* 1588:     */       case '\035': 
/* 1589:     */       case '\036': 
/* 1590:     */       case '\037': 
/* 1591:     */       case '!': 
/* 1592:     */       case '$': 
/* 1593:     */       case '%': 
/* 1594:     */       case '&': 
/* 1595:     */       case '\'': 
/* 1596:     */       case ')': 
/* 1597:     */       case '*': 
/* 1598:     */       case '+': 
/* 1599:     */       case ',': 
/* 1600:     */       case '-': 
/* 1601:     */       case '.': 
/* 1602:     */       case '/': 
/* 1603:     */       case ':': 
/* 1604:     */       case ';': 
/* 1605:     */       case '<': 
/* 1606:     */       case '=': 
/* 1607:     */       case '>': 
/* 1608:     */       case '?': 
/* 1609:     */       case '@': 
/* 1610:     */       case '\\': 
/* 1611:     */       case ']': 
/* 1612:     */       case '^': 
/* 1613:     */       case '`': 
/* 1614:     */       default: 
/* 1615:1150 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1616:     */       }
/* 1617:1154 */       k = this.text.length();
/* 1618:1155 */       mAST_CTOR_ELEMENT(true);
/* 1619:1156 */       this.text.setLength(k);
/* 1620:1157 */       localToken4 = this._returnToken;
/* 1621:1159 */       switch (LA(1))
/* 1622:     */       {
/* 1623:     */       case '\t': 
/* 1624:     */       case '\n': 
/* 1625:     */       case '\r': 
/* 1626:     */       case ' ': 
/* 1627:1162 */         k = this.text.length();
/* 1628:1163 */         mWS(false);
/* 1629:1164 */         this.text.setLength(k);
/* 1630:1165 */         break;
/* 1631:     */       case ']': 
/* 1632:     */         break;
/* 1633:     */       default: 
/* 1634:1173 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1635:     */       }
/* 1636:     */       break;
/* 1637:     */     case ']': 
/* 1638:     */       break;
/* 1639:     */     default: 
/* 1640:1185 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1641:     */     }
/* 1642:1189 */     k = this.text.length();
/* 1643:1190 */     match(']');
/* 1644:1191 */     this.text.setLength(k);
/* 1645:     */     
/* 1646:1193 */     String str = localToken2.getText();
/* 1647:1194 */     if (localToken3 != null) {
/* 1648:1195 */       str = str + "," + localToken3.getText();
/* 1649:     */     }
/* 1650:1197 */     if (localToken4 != null) {
/* 1651:1198 */       str = str + "," + localToken4.getText();
/* 1652:     */     }
/* 1653:1200 */     this.text.setLength(j);this.text.append(this.generator.getASTCreateString(null, str));
/* 1654:1202 */     if ((paramBoolean) && (localToken1 == null) && (i != -1))
/* 1655:     */     {
/* 1656:1203 */       localToken1 = makeToken(i);
/* 1657:1204 */       localToken1.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 1658:     */     }
/* 1659:1206 */     this._returnToken = localToken1;
/* 1660:     */   }
/* 1661:     */   
/* 1662:     */   protected final void mTEXT_ARG(boolean paramBoolean)
/* 1663:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 1664:     */   {
/* 1665:1210 */     Token localToken = null;int j = this.text.length();
/* 1666:1211 */     int i = 13;
/* 1667:1215 */     switch (LA(1))
/* 1668:     */     {
/* 1669:     */     case '\t': 
/* 1670:     */     case '\n': 
/* 1671:     */     case '\r': 
/* 1672:     */     case ' ': 
/* 1673:1218 */       mWS(false);
/* 1674:1219 */       break;
/* 1675:     */     case '"': 
/* 1676:     */     case '$': 
/* 1677:     */     case '\'': 
/* 1678:     */     case '+': 
/* 1679:     */     case '0': 
/* 1680:     */     case '1': 
/* 1681:     */     case '2': 
/* 1682:     */     case '3': 
/* 1683:     */     case '4': 
/* 1684:     */     case '5': 
/* 1685:     */     case '6': 
/* 1686:     */     case '7': 
/* 1687:     */     case '8': 
/* 1688:     */     case '9': 
/* 1689:     */     case 'A': 
/* 1690:     */     case 'B': 
/* 1691:     */     case 'C': 
/* 1692:     */     case 'D': 
/* 1693:     */     case 'E': 
/* 1694:     */     case 'F': 
/* 1695:     */     case 'G': 
/* 1696:     */     case 'H': 
/* 1697:     */     case 'I': 
/* 1698:     */     case 'J': 
/* 1699:     */     case 'K': 
/* 1700:     */     case 'L': 
/* 1701:     */     case 'M': 
/* 1702:     */     case 'N': 
/* 1703:     */     case 'O': 
/* 1704:     */     case 'P': 
/* 1705:     */     case 'Q': 
/* 1706:     */     case 'R': 
/* 1707:     */     case 'S': 
/* 1708:     */     case 'T': 
/* 1709:     */     case 'U': 
/* 1710:     */     case 'V': 
/* 1711:     */     case 'W': 
/* 1712:     */     case 'X': 
/* 1713:     */     case 'Y': 
/* 1714:     */     case 'Z': 
/* 1715:     */     case '_': 
/* 1716:     */     case 'a': 
/* 1717:     */     case 'b': 
/* 1718:     */     case 'c': 
/* 1719:     */     case 'd': 
/* 1720:     */     case 'e': 
/* 1721:     */     case 'f': 
/* 1722:     */     case 'g': 
/* 1723:     */     case 'h': 
/* 1724:     */     case 'i': 
/* 1725:     */     case 'j': 
/* 1726:     */     case 'k': 
/* 1727:     */     case 'l': 
/* 1728:     */     case 'm': 
/* 1729:     */     case 'n': 
/* 1730:     */     case 'o': 
/* 1731:     */     case 'p': 
/* 1732:     */     case 'q': 
/* 1733:     */     case 'r': 
/* 1734:     */     case 's': 
/* 1735:     */     case 't': 
/* 1736:     */     case 'u': 
/* 1737:     */     case 'v': 
/* 1738:     */     case 'w': 
/* 1739:     */     case 'x': 
/* 1740:     */     case 'y': 
/* 1741:     */     case 'z': 
/* 1742:     */       break;
/* 1743:     */     case '\013': 
/* 1744:     */     case '\f': 
/* 1745:     */     case '\016': 
/* 1746:     */     case '\017': 
/* 1747:     */     case '\020': 
/* 1748:     */     case '\021': 
/* 1749:     */     case '\022': 
/* 1750:     */     case '\023': 
/* 1751:     */     case '\024': 
/* 1752:     */     case '\025': 
/* 1753:     */     case '\026': 
/* 1754:     */     case '\027': 
/* 1755:     */     case '\030': 
/* 1756:     */     case '\031': 
/* 1757:     */     case '\032': 
/* 1758:     */     case '\033': 
/* 1759:     */     case '\034': 
/* 1760:     */     case '\035': 
/* 1761:     */     case '\036': 
/* 1762:     */     case '\037': 
/* 1763:     */     case '!': 
/* 1764:     */     case '#': 
/* 1765:     */     case '%': 
/* 1766:     */     case '&': 
/* 1767:     */     case '(': 
/* 1768:     */     case ')': 
/* 1769:     */     case '*': 
/* 1770:     */     case ',': 
/* 1771:     */     case '-': 
/* 1772:     */     case '.': 
/* 1773:     */     case '/': 
/* 1774:     */     case ':': 
/* 1775:     */     case ';': 
/* 1776:     */     case '<': 
/* 1777:     */     case '=': 
/* 1778:     */     case '>': 
/* 1779:     */     case '?': 
/* 1780:     */     case '@': 
/* 1781:     */     case '[': 
/* 1782:     */     case '\\': 
/* 1783:     */     case ']': 
/* 1784:     */     case '^': 
/* 1785:     */     case '`': 
/* 1786:     */     default: 
/* 1787:1243 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1788:     */     }
/* 1789:1248 */     int k = 0;
/* 1790:     */     for (;;)
/* 1791:     */     {
/* 1792:1251 */       if ((_tokenSet_11.member(LA(1))) && (LA(2) >= '\003') && (LA(2) <= 'ÿ'))
/* 1793:     */       {
/* 1794:1252 */         mTEXT_ARG_ELEMENT(false);
/* 1795:1254 */         if ((_tokenSet_4.member(LA(1))) && (_tokenSet_12.member(LA(2)))) {
/* 1796:1255 */           mWS(false);
/* 1797:1257 */         } else if (!_tokenSet_12.member(LA(1))) {
/* 1798:1260 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1799:     */         }
/* 1800:     */       }
/* 1801:     */       else
/* 1802:     */       {
/* 1803:1266 */         if (k >= 1) {
/* 1804:     */           break;
/* 1805:     */         }
/* 1806:1266 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1807:     */       }
/* 1808:1269 */       k++;
/* 1809:     */     }
/* 1810:1272 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 1811:     */     {
/* 1812:1273 */       localToken = makeToken(i);
/* 1813:1274 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 1814:     */     }
/* 1815:1276 */     this._returnToken = localToken;
/* 1816:     */   }
/* 1817:     */   
/* 1818:     */   protected final void mTREE_ELEMENT(boolean paramBoolean)
/* 1819:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 1820:     */   {
/* 1821:1280 */     Token localToken1 = null;int j = this.text.length();
/* 1822:1281 */     int i = 9;
/* 1823:     */     
/* 1824:1283 */     Token localToken2 = null;
/* 1825:1286 */     switch (LA(1))
/* 1826:     */     {
/* 1827:     */     case '(': 
/* 1828:1289 */       mTREE(false);
/* 1829:1290 */       break;
/* 1830:     */     case '[': 
/* 1831:1294 */       mAST_CONSTRUCTOR(false);
/* 1832:1295 */       break;
/* 1833:     */     case 'A': 
/* 1834:     */     case 'B': 
/* 1835:     */     case 'C': 
/* 1836:     */     case 'D': 
/* 1837:     */     case 'E': 
/* 1838:     */     case 'F': 
/* 1839:     */     case 'G': 
/* 1840:     */     case 'H': 
/* 1841:     */     case 'I': 
/* 1842:     */     case 'J': 
/* 1843:     */     case 'K': 
/* 1844:     */     case 'L': 
/* 1845:     */     case 'M': 
/* 1846:     */     case 'N': 
/* 1847:     */     case 'O': 
/* 1848:     */     case 'P': 
/* 1849:     */     case 'Q': 
/* 1850:     */     case 'R': 
/* 1851:     */     case 'S': 
/* 1852:     */     case 'T': 
/* 1853:     */     case 'U': 
/* 1854:     */     case 'V': 
/* 1855:     */     case 'W': 
/* 1856:     */     case 'X': 
/* 1857:     */     case 'Y': 
/* 1858:     */     case 'Z': 
/* 1859:     */     case '_': 
/* 1860:     */     case 'a': 
/* 1861:     */     case 'b': 
/* 1862:     */     case 'c': 
/* 1863:     */     case 'd': 
/* 1864:     */     case 'e': 
/* 1865:     */     case 'f': 
/* 1866:     */     case 'g': 
/* 1867:     */     case 'h': 
/* 1868:     */     case 'i': 
/* 1869:     */     case 'j': 
/* 1870:     */     case 'k': 
/* 1871:     */     case 'l': 
/* 1872:     */     case 'm': 
/* 1873:     */     case 'n': 
/* 1874:     */     case 'o': 
/* 1875:     */     case 'p': 
/* 1876:     */     case 'q': 
/* 1877:     */     case 'r': 
/* 1878:     */     case 's': 
/* 1879:     */     case 't': 
/* 1880:     */     case 'u': 
/* 1881:     */     case 'v': 
/* 1882:     */     case 'w': 
/* 1883:     */     case 'x': 
/* 1884:     */     case 'y': 
/* 1885:     */     case 'z': 
/* 1886:1312 */       mID_ELEMENT(false);
/* 1887:1313 */       break;
/* 1888:     */     case '"': 
/* 1889:1317 */       mSTRING(false);
/* 1890:1318 */       break;
/* 1891:     */     case '#': 
/* 1892:     */     case '$': 
/* 1893:     */     case '%': 
/* 1894:     */     case '&': 
/* 1895:     */     case '\'': 
/* 1896:     */     case ')': 
/* 1897:     */     case '*': 
/* 1898:     */     case '+': 
/* 1899:     */     case ',': 
/* 1900:     */     case '-': 
/* 1901:     */     case '.': 
/* 1902:     */     case '/': 
/* 1903:     */     case '0': 
/* 1904:     */     case '1': 
/* 1905:     */     case '2': 
/* 1906:     */     case '3': 
/* 1907:     */     case '4': 
/* 1908:     */     case '5': 
/* 1909:     */     case '6': 
/* 1910:     */     case '7': 
/* 1911:     */     case '8': 
/* 1912:     */     case '9': 
/* 1913:     */     case ':': 
/* 1914:     */     case ';': 
/* 1915:     */     case '<': 
/* 1916:     */     case '=': 
/* 1917:     */     case '>': 
/* 1918:     */     case '?': 
/* 1919:     */     case '@': 
/* 1920:     */     case '\\': 
/* 1921:     */     case ']': 
/* 1922:     */     case '^': 
/* 1923:     */     case '`': 
/* 1924:     */     default: 
/* 1925:     */       int k;
/* 1926:1321 */       if ((LA(1) == '#') && (LA(2) == '('))
/* 1927:     */       {
/* 1928:1322 */         k = this.text.length();
/* 1929:1323 */         match('#');
/* 1930:1324 */         this.text.setLength(k);
/* 1931:1325 */         mTREE(false);
/* 1932:     */       }
/* 1933:1327 */       else if ((LA(1) == '#') && (LA(2) == '['))
/* 1934:     */       {
/* 1935:1328 */         k = this.text.length();
/* 1936:1329 */         match('#');
/* 1937:1330 */         this.text.setLength(k);
/* 1938:1331 */         mAST_CONSTRUCTOR(false);
/* 1939:     */       }
/* 1940:     */       else
/* 1941:     */       {
/* 1942:     */         String str;
/* 1943:1333 */         if ((LA(1) == '#') && (_tokenSet_3.member(LA(2))))
/* 1944:     */         {
/* 1945:1334 */           k = this.text.length();
/* 1946:1335 */           match('#');
/* 1947:1336 */           this.text.setLength(k);
/* 1948:1337 */           boolean bool = mID_ELEMENT(true);
/* 1949:1338 */           localToken2 = this._returnToken;
/* 1950:1340 */           if (!bool)
/* 1951:     */           {
/* 1952:1342 */             str = this.generator.mapTreeId(localToken2.getText(), null);
/* 1953:1343 */             this.text.setLength(j);this.text.append(str);
/* 1954:     */           }
/* 1955:     */         }
/* 1956:1347 */         else if ((LA(1) == '#') && (LA(2) == '#'))
/* 1957:     */         {
/* 1958:1348 */           match("##");
/* 1959:1349 */           str = this.currentRule.getRuleName() + "_AST";this.text.setLength(j);this.text.append(str);
/* 1960:     */         }
/* 1961:     */         else
/* 1962:     */         {
/* 1963:1352 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1964:     */         }
/* 1965:     */       }
/* 1966:     */       break;
/* 1967:     */     }
/* 1968:1355 */     if ((paramBoolean) && (localToken1 == null) && (i != -1))
/* 1969:     */     {
/* 1970:1356 */       localToken1 = makeToken(i);
/* 1971:1357 */       localToken1.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 1972:     */     }
/* 1973:1359 */     this._returnToken = localToken1;
/* 1974:     */   }
/* 1975:     */   
/* 1976:     */   protected final boolean mID_ELEMENT(boolean paramBoolean)
/* 1977:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 1978:     */   {
/* 1979:1366 */     boolean bool = false;
/* 1980:1367 */     Token localToken1 = null;int j = this.text.length();
/* 1981:1368 */     int i = 12;
/* 1982:     */     
/* 1983:1370 */     Token localToken2 = null;
/* 1984:     */     
/* 1985:1372 */     mID(true);
/* 1986:1373 */     localToken2 = this._returnToken;
/* 1987:     */     int k;
/* 1988:1375 */     if ((_tokenSet_4.member(LA(1))) && (_tokenSet_13.member(LA(2))))
/* 1989:     */     {
/* 1990:1376 */       k = this.text.length();
/* 1991:1377 */       mWS(false);
/* 1992:1378 */       this.text.setLength(k);
/* 1993:     */     }
/* 1994:1380 */     else if (!_tokenSet_13.member(LA(1)))
/* 1995:     */     {
/* 1996:1383 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1997:     */     }
/* 1998:1388 */     switch (LA(1))
/* 1999:     */     {
/* 2000:     */     case '(': 
/* 2001:1391 */       match('(');
/* 2002:1393 */       if ((_tokenSet_4.member(LA(1))) && (_tokenSet_14.member(LA(2))) && (LA(3) >= '\003') && (LA(3) <= 'ÿ'))
/* 2003:     */       {
/* 2004:1394 */         k = this.text.length();
/* 2005:1395 */         mWS(false);
/* 2006:1396 */         this.text.setLength(k);
/* 2007:     */       }
/* 2008:1398 */       else if ((!_tokenSet_14.member(LA(1))) || (LA(2) < '\003') || (LA(2) > 'ÿ'))
/* 2009:     */       {
/* 2010:1401 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2011:     */       }
/* 2012:1406 */       switch (LA(1))
/* 2013:     */       {
/* 2014:     */       case '"': 
/* 2015:     */       case '#': 
/* 2016:     */       case '\'': 
/* 2017:     */       case '(': 
/* 2018:     */       case '0': 
/* 2019:     */       case '1': 
/* 2020:     */       case '2': 
/* 2021:     */       case '3': 
/* 2022:     */       case '4': 
/* 2023:     */       case '5': 
/* 2024:     */       case '6': 
/* 2025:     */       case '7': 
/* 2026:     */       case '8': 
/* 2027:     */       case '9': 
/* 2028:     */       case 'A': 
/* 2029:     */       case 'B': 
/* 2030:     */       case 'C': 
/* 2031:     */       case 'D': 
/* 2032:     */       case 'E': 
/* 2033:     */       case 'F': 
/* 2034:     */       case 'G': 
/* 2035:     */       case 'H': 
/* 2036:     */       case 'I': 
/* 2037:     */       case 'J': 
/* 2038:     */       case 'K': 
/* 2039:     */       case 'L': 
/* 2040:     */       case 'M': 
/* 2041:     */       case 'N': 
/* 2042:     */       case 'O': 
/* 2043:     */       case 'P': 
/* 2044:     */       case 'Q': 
/* 2045:     */       case 'R': 
/* 2046:     */       case 'S': 
/* 2047:     */       case 'T': 
/* 2048:     */       case 'U': 
/* 2049:     */       case 'V': 
/* 2050:     */       case 'W': 
/* 2051:     */       case 'X': 
/* 2052:     */       case 'Y': 
/* 2053:     */       case 'Z': 
/* 2054:     */       case '[': 
/* 2055:     */       case '_': 
/* 2056:     */       case 'a': 
/* 2057:     */       case 'b': 
/* 2058:     */       case 'c': 
/* 2059:     */       case 'd': 
/* 2060:     */       case 'e': 
/* 2061:     */       case 'f': 
/* 2062:     */       case 'g': 
/* 2063:     */       case 'h': 
/* 2064:     */       case 'i': 
/* 2065:     */       case 'j': 
/* 2066:     */       case 'k': 
/* 2067:     */       case 'l': 
/* 2068:     */       case 'm': 
/* 2069:     */       case 'n': 
/* 2070:     */       case 'o': 
/* 2071:     */       case 'p': 
/* 2072:     */       case 'q': 
/* 2073:     */       case 'r': 
/* 2074:     */       case 's': 
/* 2075:     */       case 't': 
/* 2076:     */       case 'u': 
/* 2077:     */       case 'v': 
/* 2078:     */       case 'w': 
/* 2079:     */       case 'x': 
/* 2080:     */       case 'y': 
/* 2081:     */       case 'z': 
/* 2082:1425 */         mARG(false);
/* 2083:     */       }
/* 2084:1429 */       while (LA(1) == ',')
/* 2085:     */       {
/* 2086:1430 */         match(',');
/* 2087:1432 */         switch (LA(1))
/* 2088:     */         {
/* 2089:     */         case '\t': 
/* 2090:     */         case '\n': 
/* 2091:     */         case '\r': 
/* 2092:     */         case ' ': 
/* 2093:1435 */           k = this.text.length();
/* 2094:1436 */           mWS(false);
/* 2095:1437 */           this.text.setLength(k);
/* 2096:1438 */           break;
/* 2097:     */         case '"': 
/* 2098:     */         case '#': 
/* 2099:     */         case '\'': 
/* 2100:     */         case '(': 
/* 2101:     */         case '0': 
/* 2102:     */         case '1': 
/* 2103:     */         case '2': 
/* 2104:     */         case '3': 
/* 2105:     */         case '4': 
/* 2106:     */         case '5': 
/* 2107:     */         case '6': 
/* 2108:     */         case '7': 
/* 2109:     */         case '8': 
/* 2110:     */         case '9': 
/* 2111:     */         case 'A': 
/* 2112:     */         case 'B': 
/* 2113:     */         case 'C': 
/* 2114:     */         case 'D': 
/* 2115:     */         case 'E': 
/* 2116:     */         case 'F': 
/* 2117:     */         case 'G': 
/* 2118:     */         case 'H': 
/* 2119:     */         case 'I': 
/* 2120:     */         case 'J': 
/* 2121:     */         case 'K': 
/* 2122:     */         case 'L': 
/* 2123:     */         case 'M': 
/* 2124:     */         case 'N': 
/* 2125:     */         case 'O': 
/* 2126:     */         case 'P': 
/* 2127:     */         case 'Q': 
/* 2128:     */         case 'R': 
/* 2129:     */         case 'S': 
/* 2130:     */         case 'T': 
/* 2131:     */         case 'U': 
/* 2132:     */         case 'V': 
/* 2133:     */         case 'W': 
/* 2134:     */         case 'X': 
/* 2135:     */         case 'Y': 
/* 2136:     */         case 'Z': 
/* 2137:     */         case '[': 
/* 2138:     */         case '_': 
/* 2139:     */         case 'a': 
/* 2140:     */         case 'b': 
/* 2141:     */         case 'c': 
/* 2142:     */         case 'd': 
/* 2143:     */         case 'e': 
/* 2144:     */         case 'f': 
/* 2145:     */         case 'g': 
/* 2146:     */         case 'h': 
/* 2147:     */         case 'i': 
/* 2148:     */         case 'j': 
/* 2149:     */         case 'k': 
/* 2150:     */         case 'l': 
/* 2151:     */         case 'm': 
/* 2152:     */         case 'n': 
/* 2153:     */         case 'o': 
/* 2154:     */         case 'p': 
/* 2155:     */         case 'q': 
/* 2156:     */         case 'r': 
/* 2157:     */         case 's': 
/* 2158:     */         case 't': 
/* 2159:     */         case 'u': 
/* 2160:     */         case 'v': 
/* 2161:     */         case 'w': 
/* 2162:     */         case 'x': 
/* 2163:     */         case 'y': 
/* 2164:     */         case 'z': 
/* 2165:     */           break;
/* 2166:     */         case '\013': 
/* 2167:     */         case '\f': 
/* 2168:     */         case '\016': 
/* 2169:     */         case '\017': 
/* 2170:     */         case '\020': 
/* 2171:     */         case '\021': 
/* 2172:     */         case '\022': 
/* 2173:     */         case '\023': 
/* 2174:     */         case '\024': 
/* 2175:     */         case '\025': 
/* 2176:     */         case '\026': 
/* 2177:     */         case '\027': 
/* 2178:     */         case '\030': 
/* 2179:     */         case '\031': 
/* 2180:     */         case '\032': 
/* 2181:     */         case '\033': 
/* 2182:     */         case '\034': 
/* 2183:     */         case '\035': 
/* 2184:     */         case '\036': 
/* 2185:     */         case '\037': 
/* 2186:     */         case '!': 
/* 2187:     */         case '$': 
/* 2188:     */         case '%': 
/* 2189:     */         case '&': 
/* 2190:     */         case ')': 
/* 2191:     */         case '*': 
/* 2192:     */         case '+': 
/* 2193:     */         case ',': 
/* 2194:     */         case '-': 
/* 2195:     */         case '.': 
/* 2196:     */         case '/': 
/* 2197:     */         case ':': 
/* 2198:     */         case ';': 
/* 2199:     */         case '<': 
/* 2200:     */         case '=': 
/* 2201:     */         case '>': 
/* 2202:     */         case '?': 
/* 2203:     */         case '@': 
/* 2204:     */         case '\\': 
/* 2205:     */         case ']': 
/* 2206:     */         case '^': 
/* 2207:     */         case '`': 
/* 2208:     */         default: 
/* 2209:1462 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2210:     */         }
/* 2211:1466 */         mARG(false); continue;
/* 2212:     */         
/* 2213:     */ 
/* 2214:     */ 
/* 2215:     */ 
/* 2216:     */ 
/* 2217:     */ 
/* 2218:     */ 
/* 2219:     */ 
/* 2220:     */ 
/* 2221:     */ 
/* 2222:     */ 
/* 2223:     */ 
/* 2224:1479 */         break;
/* 2225:     */         
/* 2226:     */ 
/* 2227:     */ 
/* 2228:1483 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2229:     */       }
/* 2230:1488 */       switch (LA(1))
/* 2231:     */       {
/* 2232:     */       case '\t': 
/* 2233:     */       case '\n': 
/* 2234:     */       case '\r': 
/* 2235:     */       case ' ': 
/* 2236:1491 */         k = this.text.length();
/* 2237:1492 */         mWS(false);
/* 2238:1493 */         this.text.setLength(k);
/* 2239:1494 */         break;
/* 2240:     */       case ')': 
/* 2241:     */         break;
/* 2242:     */       default: 
/* 2243:1502 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2244:     */       }
/* 2245:1506 */       match(')');
/* 2246:1507 */       break;
/* 2247:     */     case '[': 
/* 2248:1512 */       int m = 0;
/* 2249:     */       for (;;)
/* 2250:     */       {
/* 2251:1515 */         if (LA(1) == '[')
/* 2252:     */         {
/* 2253:1516 */           match('[');
/* 2254:1518 */           switch (LA(1))
/* 2255:     */           {
/* 2256:     */           case '\t': 
/* 2257:     */           case '\n': 
/* 2258:     */           case '\r': 
/* 2259:     */           case ' ': 
/* 2260:1521 */             k = this.text.length();
/* 2261:1522 */             mWS(false);
/* 2262:1523 */             this.text.setLength(k);
/* 2263:1524 */             break;
/* 2264:     */           case '"': 
/* 2265:     */           case '#': 
/* 2266:     */           case '\'': 
/* 2267:     */           case '(': 
/* 2268:     */           case '0': 
/* 2269:     */           case '1': 
/* 2270:     */           case '2': 
/* 2271:     */           case '3': 
/* 2272:     */           case '4': 
/* 2273:     */           case '5': 
/* 2274:     */           case '6': 
/* 2275:     */           case '7': 
/* 2276:     */           case '8': 
/* 2277:     */           case '9': 
/* 2278:     */           case 'A': 
/* 2279:     */           case 'B': 
/* 2280:     */           case 'C': 
/* 2281:     */           case 'D': 
/* 2282:     */           case 'E': 
/* 2283:     */           case 'F': 
/* 2284:     */           case 'G': 
/* 2285:     */           case 'H': 
/* 2286:     */           case 'I': 
/* 2287:     */           case 'J': 
/* 2288:     */           case 'K': 
/* 2289:     */           case 'L': 
/* 2290:     */           case 'M': 
/* 2291:     */           case 'N': 
/* 2292:     */           case 'O': 
/* 2293:     */           case 'P': 
/* 2294:     */           case 'Q': 
/* 2295:     */           case 'R': 
/* 2296:     */           case 'S': 
/* 2297:     */           case 'T': 
/* 2298:     */           case 'U': 
/* 2299:     */           case 'V': 
/* 2300:     */           case 'W': 
/* 2301:     */           case 'X': 
/* 2302:     */           case 'Y': 
/* 2303:     */           case 'Z': 
/* 2304:     */           case '[': 
/* 2305:     */           case '_': 
/* 2306:     */           case 'a': 
/* 2307:     */           case 'b': 
/* 2308:     */           case 'c': 
/* 2309:     */           case 'd': 
/* 2310:     */           case 'e': 
/* 2311:     */           case 'f': 
/* 2312:     */           case 'g': 
/* 2313:     */           case 'h': 
/* 2314:     */           case 'i': 
/* 2315:     */           case 'j': 
/* 2316:     */           case 'k': 
/* 2317:     */           case 'l': 
/* 2318:     */           case 'm': 
/* 2319:     */           case 'n': 
/* 2320:     */           case 'o': 
/* 2321:     */           case 'p': 
/* 2322:     */           case 'q': 
/* 2323:     */           case 'r': 
/* 2324:     */           case 's': 
/* 2325:     */           case 't': 
/* 2326:     */           case 'u': 
/* 2327:     */           case 'v': 
/* 2328:     */           case 'w': 
/* 2329:     */           case 'x': 
/* 2330:     */           case 'y': 
/* 2331:     */           case 'z': 
/* 2332:     */             break;
/* 2333:     */           case '\013': 
/* 2334:     */           case '\f': 
/* 2335:     */           case '\016': 
/* 2336:     */           case '\017': 
/* 2337:     */           case '\020': 
/* 2338:     */           case '\021': 
/* 2339:     */           case '\022': 
/* 2340:     */           case '\023': 
/* 2341:     */           case '\024': 
/* 2342:     */           case '\025': 
/* 2343:     */           case '\026': 
/* 2344:     */           case '\027': 
/* 2345:     */           case '\030': 
/* 2346:     */           case '\031': 
/* 2347:     */           case '\032': 
/* 2348:     */           case '\033': 
/* 2349:     */           case '\034': 
/* 2350:     */           case '\035': 
/* 2351:     */           case '\036': 
/* 2352:     */           case '\037': 
/* 2353:     */           case '!': 
/* 2354:     */           case '$': 
/* 2355:     */           case '%': 
/* 2356:     */           case '&': 
/* 2357:     */           case ')': 
/* 2358:     */           case '*': 
/* 2359:     */           case '+': 
/* 2360:     */           case ',': 
/* 2361:     */           case '-': 
/* 2362:     */           case '.': 
/* 2363:     */           case '/': 
/* 2364:     */           case ':': 
/* 2365:     */           case ';': 
/* 2366:     */           case '<': 
/* 2367:     */           case '=': 
/* 2368:     */           case '>': 
/* 2369:     */           case '?': 
/* 2370:     */           case '@': 
/* 2371:     */           case '\\': 
/* 2372:     */           case ']': 
/* 2373:     */           case '^': 
/* 2374:     */           case '`': 
/* 2375:     */           default: 
/* 2376:1548 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2377:     */           }
/* 2378:1552 */           mARG(false);
/* 2379:1554 */           switch (LA(1))
/* 2380:     */           {
/* 2381:     */           case '\t': 
/* 2382:     */           case '\n': 
/* 2383:     */           case '\r': 
/* 2384:     */           case ' ': 
/* 2385:1557 */             k = this.text.length();
/* 2386:1558 */             mWS(false);
/* 2387:1559 */             this.text.setLength(k);
/* 2388:1560 */             break;
/* 2389:     */           case ']': 
/* 2390:     */             break;
/* 2391:     */           default: 
/* 2392:1568 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2393:     */           }
/* 2394:1572 */           match(']');
/* 2395:     */         }
/* 2396:     */         else
/* 2397:     */         {
/* 2398:1575 */           if (m >= 1) {
/* 2399:     */             break;
/* 2400:     */           }
/* 2401:1575 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2402:     */         }
/* 2403:1578 */         m++;
/* 2404:     */       }
/* 2405:     */     case '.': 
/* 2406:1585 */       match('.');
/* 2407:1586 */       mID_ELEMENT(false);
/* 2408:1587 */       break;
/* 2409:     */     case '\t': 
/* 2410:     */     case '\n': 
/* 2411:     */     case '\r': 
/* 2412:     */     case ' ': 
/* 2413:     */     case ')': 
/* 2414:     */     case '*': 
/* 2415:     */     case '+': 
/* 2416:     */     case ',': 
/* 2417:     */     case '-': 
/* 2418:     */     case '/': 
/* 2419:     */     case '=': 
/* 2420:     */     case ']': 
/* 2421:1594 */       bool = true;
/* 2422:1595 */       String str = this.generator.mapTreeId(localToken2.getText(), this.transInfo);
/* 2423:1596 */       this.text.setLength(j);this.text.append(str);
/* 2424:1599 */       if ((_tokenSet_15.member(LA(1))) && (_tokenSet_16.member(LA(2))) && (this.transInfo != null) && (this.transInfo.refRuleRoot != null))
/* 2425:     */       {
/* 2426:1601 */         switch (LA(1))
/* 2427:     */         {
/* 2428:     */         case '\t': 
/* 2429:     */         case '\n': 
/* 2430:     */         case '\r': 
/* 2431:     */         case ' ': 
/* 2432:1604 */           mWS(false);
/* 2433:1605 */           break;
/* 2434:     */         case '=': 
/* 2435:     */           break;
/* 2436:     */         default: 
/* 2437:1613 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2438:     */         }
/* 2439:1617 */         mVAR_ASSIGN(false);
/* 2440:     */       }
/* 2441:1619 */       else if (!_tokenSet_17.member(LA(1)))
/* 2442:     */       {
/* 2443:1622 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2444:     */       }
/* 2445:     */       break;
/* 2446:     */     default: 
/* 2447:1630 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2448:     */     }
/* 2449:1634 */     if ((paramBoolean) && (localToken1 == null) && (i != -1))
/* 2450:     */     {
/* 2451:1635 */       localToken1 = makeToken(i);
/* 2452:1636 */       localToken1.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 2453:     */     }
/* 2454:1638 */     this._returnToken = localToken1;
/* 2455:1639 */     return bool;
/* 2456:     */   }
/* 2457:     */   
/* 2458:     */   protected final void mAST_CTOR_ELEMENT(boolean paramBoolean)
/* 2459:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 2460:     */   {
/* 2461:1646 */     Token localToken = null;int j = this.text.length();
/* 2462:1647 */     int i = 11;
/* 2463:1650 */     if ((LA(1) == '"') && (LA(2) >= '\003') && (LA(2) <= 'ÿ') && (LA(3) >= '\003') && (LA(3) <= 'ÿ')) {
/* 2464:1651 */       mSTRING(false);
/* 2465:1653 */     } else if ((_tokenSet_18.member(LA(1))) && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 2466:1654 */       mTREE_ELEMENT(false);
/* 2467:1656 */     } else if ((LA(1) >= '0') && (LA(1) <= '9')) {
/* 2468:1657 */       mINT(false);
/* 2469:     */     } else {
/* 2470:1660 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2471:     */     }
/* 2472:1663 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 2473:     */     {
/* 2474:1664 */       localToken = makeToken(i);
/* 2475:1665 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 2476:     */     }
/* 2477:1667 */     this._returnToken = localToken;
/* 2478:     */   }
/* 2479:     */   
/* 2480:     */   protected final void mINT(boolean paramBoolean)
/* 2481:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 2482:     */   {
/* 2483:1671 */     Token localToken = null;int j = this.text.length();
/* 2484:1672 */     int i = 26;
/* 2485:     */     
/* 2486:     */ 
/* 2487:     */ 
/* 2488:1676 */     int k = 0;
/* 2489:     */     for (;;)
/* 2490:     */     {
/* 2491:1679 */       if ((LA(1) >= '0') && (LA(1) <= '9'))
/* 2492:     */       {
/* 2493:1680 */         mDIGIT(false);
/* 2494:     */       }
/* 2495:     */       else
/* 2496:     */       {
/* 2497:1683 */         if (k >= 1) {
/* 2498:     */           break;
/* 2499:     */         }
/* 2500:1683 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2501:     */       }
/* 2502:1686 */       k++;
/* 2503:     */     }
/* 2504:1689 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 2505:     */     {
/* 2506:1690 */       localToken = makeToken(i);
/* 2507:1691 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 2508:     */     }
/* 2509:1693 */     this._returnToken = localToken;
/* 2510:     */   }
/* 2511:     */   
/* 2512:     */   protected final void mARG(boolean paramBoolean)
/* 2513:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 2514:     */   {
/* 2515:1697 */     Token localToken = null;int j = this.text.length();
/* 2516:1698 */     int i = 16;
/* 2517:1702 */     switch (LA(1))
/* 2518:     */     {
/* 2519:     */     case '\'': 
/* 2520:1705 */       mCHAR(false);
/* 2521:1706 */       break;
/* 2522:     */     case '0': 
/* 2523:     */     case '1': 
/* 2524:     */     case '2': 
/* 2525:     */     case '3': 
/* 2526:     */     case '4': 
/* 2527:     */     case '5': 
/* 2528:     */     case '6': 
/* 2529:     */     case '7': 
/* 2530:     */     case '8': 
/* 2531:     */     case '9': 
/* 2532:1712 */       mINT_OR_FLOAT(false);
/* 2533:1713 */       break;
/* 2534:     */     case '(': 
/* 2535:     */     case ')': 
/* 2536:     */     case '*': 
/* 2537:     */     case '+': 
/* 2538:     */     case ',': 
/* 2539:     */     case '-': 
/* 2540:     */     case '.': 
/* 2541:     */     case '/': 
/* 2542:     */     default: 
/* 2543:1716 */       if ((_tokenSet_18.member(LA(1))) && (LA(2) >= '\003') && (LA(2) <= 'ÿ') && (LA(3) >= '\003') && (LA(3) <= 'ÿ')) {
/* 2544:1717 */         mTREE_ELEMENT(false);
/* 2545:1719 */       } else if ((LA(1) == '"') && (LA(2) >= '\003') && (LA(2) <= 'ÿ') && (LA(3) >= '\003') && (LA(3) <= 'ÿ')) {
/* 2546:1720 */         mSTRING(false);
/* 2547:     */       } else {
/* 2548:1723 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2549:     */       }
/* 2550:     */       break;
/* 2551:     */     }
/* 2552:1730 */     while ((_tokenSet_19.member(LA(1))) && (_tokenSet_20.member(LA(2))) && (LA(3) >= '\003') && (LA(3) <= 'ÿ'))
/* 2553:     */     {
/* 2554:1732 */       switch (LA(1))
/* 2555:     */       {
/* 2556:     */       case '\t': 
/* 2557:     */       case '\n': 
/* 2558:     */       case '\r': 
/* 2559:     */       case ' ': 
/* 2560:1735 */         mWS(false);
/* 2561:1736 */         break;
/* 2562:     */       case '*': 
/* 2563:     */       case '+': 
/* 2564:     */       case '-': 
/* 2565:     */       case '/': 
/* 2566:     */         break;
/* 2567:     */       default: 
/* 2568:1744 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2569:     */       }
/* 2570:1749 */       switch (LA(1))
/* 2571:     */       {
/* 2572:     */       case '+': 
/* 2573:1752 */         match('+');
/* 2574:1753 */         break;
/* 2575:     */       case '-': 
/* 2576:1757 */         match('-');
/* 2577:1758 */         break;
/* 2578:     */       case '*': 
/* 2579:1762 */         match('*');
/* 2580:1763 */         break;
/* 2581:     */       case '/': 
/* 2582:1767 */         match('/');
/* 2583:1768 */         break;
/* 2584:     */       case ',': 
/* 2585:     */       case '.': 
/* 2586:     */       default: 
/* 2587:1772 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2588:     */       }
/* 2589:1777 */       switch (LA(1))
/* 2590:     */       {
/* 2591:     */       case '\t': 
/* 2592:     */       case '\n': 
/* 2593:     */       case '\r': 
/* 2594:     */       case ' ': 
/* 2595:1780 */         mWS(false);
/* 2596:1781 */         break;
/* 2597:     */       case '"': 
/* 2598:     */       case '#': 
/* 2599:     */       case '\'': 
/* 2600:     */       case '(': 
/* 2601:     */       case '0': 
/* 2602:     */       case '1': 
/* 2603:     */       case '2': 
/* 2604:     */       case '3': 
/* 2605:     */       case '4': 
/* 2606:     */       case '5': 
/* 2607:     */       case '6': 
/* 2608:     */       case '7': 
/* 2609:     */       case '8': 
/* 2610:     */       case '9': 
/* 2611:     */       case 'A': 
/* 2612:     */       case 'B': 
/* 2613:     */       case 'C': 
/* 2614:     */       case 'D': 
/* 2615:     */       case 'E': 
/* 2616:     */       case 'F': 
/* 2617:     */       case 'G': 
/* 2618:     */       case 'H': 
/* 2619:     */       case 'I': 
/* 2620:     */       case 'J': 
/* 2621:     */       case 'K': 
/* 2622:     */       case 'L': 
/* 2623:     */       case 'M': 
/* 2624:     */       case 'N': 
/* 2625:     */       case 'O': 
/* 2626:     */       case 'P': 
/* 2627:     */       case 'Q': 
/* 2628:     */       case 'R': 
/* 2629:     */       case 'S': 
/* 2630:     */       case 'T': 
/* 2631:     */       case 'U': 
/* 2632:     */       case 'V': 
/* 2633:     */       case 'W': 
/* 2634:     */       case 'X': 
/* 2635:     */       case 'Y': 
/* 2636:     */       case 'Z': 
/* 2637:     */       case '[': 
/* 2638:     */       case '_': 
/* 2639:     */       case 'a': 
/* 2640:     */       case 'b': 
/* 2641:     */       case 'c': 
/* 2642:     */       case 'd': 
/* 2643:     */       case 'e': 
/* 2644:     */       case 'f': 
/* 2645:     */       case 'g': 
/* 2646:     */       case 'h': 
/* 2647:     */       case 'i': 
/* 2648:     */       case 'j': 
/* 2649:     */       case 'k': 
/* 2650:     */       case 'l': 
/* 2651:     */       case 'm': 
/* 2652:     */       case 'n': 
/* 2653:     */       case 'o': 
/* 2654:     */       case 'p': 
/* 2655:     */       case 'q': 
/* 2656:     */       case 'r': 
/* 2657:     */       case 's': 
/* 2658:     */       case 't': 
/* 2659:     */       case 'u': 
/* 2660:     */       case 'v': 
/* 2661:     */       case 'w': 
/* 2662:     */       case 'x': 
/* 2663:     */       case 'y': 
/* 2664:     */       case 'z': 
/* 2665:     */         break;
/* 2666:     */       case '\013': 
/* 2667:     */       case '\f': 
/* 2668:     */       case '\016': 
/* 2669:     */       case '\017': 
/* 2670:     */       case '\020': 
/* 2671:     */       case '\021': 
/* 2672:     */       case '\022': 
/* 2673:     */       case '\023': 
/* 2674:     */       case '\024': 
/* 2675:     */       case '\025': 
/* 2676:     */       case '\026': 
/* 2677:     */       case '\027': 
/* 2678:     */       case '\030': 
/* 2679:     */       case '\031': 
/* 2680:     */       case '\032': 
/* 2681:     */       case '\033': 
/* 2682:     */       case '\034': 
/* 2683:     */       case '\035': 
/* 2684:     */       case '\036': 
/* 2685:     */       case '\037': 
/* 2686:     */       case '!': 
/* 2687:     */       case '$': 
/* 2688:     */       case '%': 
/* 2689:     */       case '&': 
/* 2690:     */       case ')': 
/* 2691:     */       case '*': 
/* 2692:     */       case '+': 
/* 2693:     */       case ',': 
/* 2694:     */       case '-': 
/* 2695:     */       case '.': 
/* 2696:     */       case '/': 
/* 2697:     */       case ':': 
/* 2698:     */       case ';': 
/* 2699:     */       case '<': 
/* 2700:     */       case '=': 
/* 2701:     */       case '>': 
/* 2702:     */       case '?': 
/* 2703:     */       case '@': 
/* 2704:     */       case '\\': 
/* 2705:     */       case ']': 
/* 2706:     */       case '^': 
/* 2707:     */       case '`': 
/* 2708:     */       default: 
/* 2709:1805 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2710:     */       }
/* 2711:1809 */       mARG(false);
/* 2712:     */     }
/* 2713:1817 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 2714:     */     {
/* 2715:1818 */       localToken = makeToken(i);
/* 2716:1819 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 2717:     */     }
/* 2718:1821 */     this._returnToken = localToken;
/* 2719:     */   }
/* 2720:     */   
/* 2721:     */   protected final void mTEXT_ARG_ELEMENT(boolean paramBoolean)
/* 2722:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 2723:     */   {
/* 2724:1825 */     Token localToken = null;int j = this.text.length();
/* 2725:1826 */     int i = 14;
/* 2726:1829 */     switch (LA(1))
/* 2727:     */     {
/* 2728:     */     case 'A': 
/* 2729:     */     case 'B': 
/* 2730:     */     case 'C': 
/* 2731:     */     case 'D': 
/* 2732:     */     case 'E': 
/* 2733:     */     case 'F': 
/* 2734:     */     case 'G': 
/* 2735:     */     case 'H': 
/* 2736:     */     case 'I': 
/* 2737:     */     case 'J': 
/* 2738:     */     case 'K': 
/* 2739:     */     case 'L': 
/* 2740:     */     case 'M': 
/* 2741:     */     case 'N': 
/* 2742:     */     case 'O': 
/* 2743:     */     case 'P': 
/* 2744:     */     case 'Q': 
/* 2745:     */     case 'R': 
/* 2746:     */     case 'S': 
/* 2747:     */     case 'T': 
/* 2748:     */     case 'U': 
/* 2749:     */     case 'V': 
/* 2750:     */     case 'W': 
/* 2751:     */     case 'X': 
/* 2752:     */     case 'Y': 
/* 2753:     */     case 'Z': 
/* 2754:     */     case '_': 
/* 2755:     */     case 'a': 
/* 2756:     */     case 'b': 
/* 2757:     */     case 'c': 
/* 2758:     */     case 'd': 
/* 2759:     */     case 'e': 
/* 2760:     */     case 'f': 
/* 2761:     */     case 'g': 
/* 2762:     */     case 'h': 
/* 2763:     */     case 'i': 
/* 2764:     */     case 'j': 
/* 2765:     */     case 'k': 
/* 2766:     */     case 'l': 
/* 2767:     */     case 'm': 
/* 2768:     */     case 'n': 
/* 2769:     */     case 'o': 
/* 2770:     */     case 'p': 
/* 2771:     */     case 'q': 
/* 2772:     */     case 'r': 
/* 2773:     */     case 's': 
/* 2774:     */     case 't': 
/* 2775:     */     case 'u': 
/* 2776:     */     case 'v': 
/* 2777:     */     case 'w': 
/* 2778:     */     case 'x': 
/* 2779:     */     case 'y': 
/* 2780:     */     case 'z': 
/* 2781:1845 */       mTEXT_ARG_ID_ELEMENT(false);
/* 2782:1846 */       break;
/* 2783:     */     case '"': 
/* 2784:1850 */       mSTRING(false);
/* 2785:1851 */       break;
/* 2786:     */     case '\'': 
/* 2787:1855 */       mCHAR(false);
/* 2788:1856 */       break;
/* 2789:     */     case '0': 
/* 2790:     */     case '1': 
/* 2791:     */     case '2': 
/* 2792:     */     case '3': 
/* 2793:     */     case '4': 
/* 2794:     */     case '5': 
/* 2795:     */     case '6': 
/* 2796:     */     case '7': 
/* 2797:     */     case '8': 
/* 2798:     */     case '9': 
/* 2799:1862 */       mINT_OR_FLOAT(false);
/* 2800:1863 */       break;
/* 2801:     */     case '$': 
/* 2802:1867 */       mTEXT_ITEM(false);
/* 2803:1868 */       break;
/* 2804:     */     case '+': 
/* 2805:1872 */       match('+');
/* 2806:1873 */       break;
/* 2807:     */     case '#': 
/* 2808:     */     case '%': 
/* 2809:     */     case '&': 
/* 2810:     */     case '(': 
/* 2811:     */     case ')': 
/* 2812:     */     case '*': 
/* 2813:     */     case ',': 
/* 2814:     */     case '-': 
/* 2815:     */     case '.': 
/* 2816:     */     case '/': 
/* 2817:     */     case ':': 
/* 2818:     */     case ';': 
/* 2819:     */     case '<': 
/* 2820:     */     case '=': 
/* 2821:     */     case '>': 
/* 2822:     */     case '?': 
/* 2823:     */     case '@': 
/* 2824:     */     case '[': 
/* 2825:     */     case '\\': 
/* 2826:     */     case ']': 
/* 2827:     */     case '^': 
/* 2828:     */     case '`': 
/* 2829:     */     default: 
/* 2830:1877 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2831:     */     }
/* 2832:1880 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 2833:     */     {
/* 2834:1881 */       localToken = makeToken(i);
/* 2835:1882 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 2836:     */     }
/* 2837:1884 */     this._returnToken = localToken;
/* 2838:     */   }
/* 2839:     */   
/* 2840:     */   protected final void mTEXT_ARG_ID_ELEMENT(boolean paramBoolean)
/* 2841:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 2842:     */   {
/* 2843:1888 */     Token localToken1 = null;int j = this.text.length();
/* 2844:1889 */     int i = 15;
/* 2845:     */     
/* 2846:1891 */     Token localToken2 = null;
/* 2847:     */     
/* 2848:1893 */     mID(true);
/* 2849:1894 */     localToken2 = this._returnToken;
/* 2850:     */     int k;
/* 2851:1896 */     if ((_tokenSet_4.member(LA(1))) && (_tokenSet_21.member(LA(2))))
/* 2852:     */     {
/* 2853:1897 */       k = this.text.length();
/* 2854:1898 */       mWS(false);
/* 2855:1899 */       this.text.setLength(k);
/* 2856:     */     }
/* 2857:1901 */     else if (!_tokenSet_21.member(LA(1)))
/* 2858:     */     {
/* 2859:1904 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2860:     */     }
/* 2861:1909 */     switch (LA(1))
/* 2862:     */     {
/* 2863:     */     case '(': 
/* 2864:1912 */       match('(');
/* 2865:1914 */       if ((_tokenSet_4.member(LA(1))) && (_tokenSet_22.member(LA(2))) && (LA(3) >= '\003') && (LA(3) <= 'ÿ'))
/* 2866:     */       {
/* 2867:1915 */         k = this.text.length();
/* 2868:1916 */         mWS(false);
/* 2869:1917 */         this.text.setLength(k);
/* 2870:     */       }
/* 2871:1919 */       else if ((!_tokenSet_22.member(LA(1))) || (LA(2) < '\003') || (LA(2) > 'ÿ'))
/* 2872:     */       {
/* 2873:1922 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2874:     */       }
/* 2875:1929 */       if ((_tokenSet_23.member(LA(1))) && (LA(2) >= '\003') && (LA(2) <= 'ÿ') && (LA(3) >= '\003') && (LA(3) <= 'ÿ'))
/* 2876:     */       {
/* 2877:1930 */         mTEXT_ARG(false);
/* 2878:1934 */         while (LA(1) == ',')
/* 2879:     */         {
/* 2880:1935 */           match(',');
/* 2881:1936 */           mTEXT_ARG(false);
/* 2882:     */         }
/* 2883:     */       }
/* 2884:1952 */       switch (LA(1))
/* 2885:     */       {
/* 2886:     */       case '\t': 
/* 2887:     */       case '\n': 
/* 2888:     */       case '\r': 
/* 2889:     */       case ' ': 
/* 2890:1955 */         k = this.text.length();
/* 2891:1956 */         mWS(false);
/* 2892:1957 */         this.text.setLength(k);
/* 2893:1958 */         break;
/* 2894:     */       case ')': 
/* 2895:     */         break;
/* 2896:     */       default: 
/* 2897:1966 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2898:     */       }
/* 2899:1970 */       match(')');
/* 2900:1971 */       break;
/* 2901:     */     case '[': 
/* 2902:1976 */       int m = 0;
/* 2903:     */       for (;;)
/* 2904:     */       {
/* 2905:1979 */         if (LA(1) == '[')
/* 2906:     */         {
/* 2907:1980 */           match('[');
/* 2908:1982 */           if ((_tokenSet_4.member(LA(1))) && (_tokenSet_23.member(LA(2))) && (LA(3) >= '\003') && (LA(3) <= 'ÿ'))
/* 2909:     */           {
/* 2910:1983 */             k = this.text.length();
/* 2911:1984 */             mWS(false);
/* 2912:1985 */             this.text.setLength(k);
/* 2913:     */           }
/* 2914:1987 */           else if ((!_tokenSet_23.member(LA(1))) || (LA(2) < '\003') || (LA(2) > 'ÿ') || (LA(3) < '\003') || (LA(3) > 'ÿ'))
/* 2915:     */           {
/* 2916:1990 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2917:     */           }
/* 2918:1994 */           mTEXT_ARG(false);
/* 2919:1996 */           switch (LA(1))
/* 2920:     */           {
/* 2921:     */           case '\t': 
/* 2922:     */           case '\n': 
/* 2923:     */           case '\r': 
/* 2924:     */           case ' ': 
/* 2925:1999 */             k = this.text.length();
/* 2926:2000 */             mWS(false);
/* 2927:2001 */             this.text.setLength(k);
/* 2928:2002 */             break;
/* 2929:     */           case ']': 
/* 2930:     */             break;
/* 2931:     */           default: 
/* 2932:2010 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2933:     */           }
/* 2934:2014 */           match(']');
/* 2935:     */         }
/* 2936:     */         else
/* 2937:     */         {
/* 2938:2017 */           if (m >= 1) {
/* 2939:     */             break;
/* 2940:     */           }
/* 2941:2017 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2942:     */         }
/* 2943:2020 */         m++;
/* 2944:     */       }
/* 2945:     */     case '.': 
/* 2946:2027 */       match('.');
/* 2947:2028 */       mTEXT_ARG_ID_ELEMENT(false);
/* 2948:2029 */       break;
/* 2949:     */     case '\t': 
/* 2950:     */     case '\n': 
/* 2951:     */     case '\r': 
/* 2952:     */     case ' ': 
/* 2953:     */     case '"': 
/* 2954:     */     case '$': 
/* 2955:     */     case '\'': 
/* 2956:     */     case ')': 
/* 2957:     */     case '+': 
/* 2958:     */     case ',': 
/* 2959:     */     case '0': 
/* 2960:     */     case '1': 
/* 2961:     */     case '2': 
/* 2962:     */     case '3': 
/* 2963:     */     case '4': 
/* 2964:     */     case '5': 
/* 2965:     */     case '6': 
/* 2966:     */     case '7': 
/* 2967:     */     case '8': 
/* 2968:     */     case '9': 
/* 2969:     */     case 'A': 
/* 2970:     */     case 'B': 
/* 2971:     */     case 'C': 
/* 2972:     */     case 'D': 
/* 2973:     */     case 'E': 
/* 2974:     */     case 'F': 
/* 2975:     */     case 'G': 
/* 2976:     */     case 'H': 
/* 2977:     */     case 'I': 
/* 2978:     */     case 'J': 
/* 2979:     */     case 'K': 
/* 2980:     */     case 'L': 
/* 2981:     */     case 'M': 
/* 2982:     */     case 'N': 
/* 2983:     */     case 'O': 
/* 2984:     */     case 'P': 
/* 2985:     */     case 'Q': 
/* 2986:     */     case 'R': 
/* 2987:     */     case 'S': 
/* 2988:     */     case 'T': 
/* 2989:     */     case 'U': 
/* 2990:     */     case 'V': 
/* 2991:     */     case 'W': 
/* 2992:     */     case 'X': 
/* 2993:     */     case 'Y': 
/* 2994:     */     case 'Z': 
/* 2995:     */     case ']': 
/* 2996:     */     case '_': 
/* 2997:     */     case 'a': 
/* 2998:     */     case 'b': 
/* 2999:     */     case 'c': 
/* 3000:     */     case 'd': 
/* 3001:     */     case 'e': 
/* 3002:     */     case 'f': 
/* 3003:     */     case 'g': 
/* 3004:     */     case 'h': 
/* 3005:     */     case 'i': 
/* 3006:     */     case 'j': 
/* 3007:     */     case 'k': 
/* 3008:     */     case 'l': 
/* 3009:     */     case 'm': 
/* 3010:     */     case 'n': 
/* 3011:     */     case 'o': 
/* 3012:     */     case 'p': 
/* 3013:     */     case 'q': 
/* 3014:     */     case 'r': 
/* 3015:     */     case 's': 
/* 3016:     */     case 't': 
/* 3017:     */     case 'u': 
/* 3018:     */     case 'v': 
/* 3019:     */     case 'w': 
/* 3020:     */     case 'x': 
/* 3021:     */     case 'y': 
/* 3022:     */     case 'z': 
/* 3023:     */       break;
/* 3024:     */     case '\013': 
/* 3025:     */     case '\f': 
/* 3026:     */     case '\016': 
/* 3027:     */     case '\017': 
/* 3028:     */     case '\020': 
/* 3029:     */     case '\021': 
/* 3030:     */     case '\022': 
/* 3031:     */     case '\023': 
/* 3032:     */     case '\024': 
/* 3033:     */     case '\025': 
/* 3034:     */     case '\026': 
/* 3035:     */     case '\027': 
/* 3036:     */     case '\030': 
/* 3037:     */     case '\031': 
/* 3038:     */     case '\032': 
/* 3039:     */     case '\033': 
/* 3040:     */     case '\034': 
/* 3041:     */     case '\035': 
/* 3042:     */     case '\036': 
/* 3043:     */     case '\037': 
/* 3044:     */     case '!': 
/* 3045:     */     case '#': 
/* 3046:     */     case '%': 
/* 3047:     */     case '&': 
/* 3048:     */     case '*': 
/* 3049:     */     case '-': 
/* 3050:     */     case '/': 
/* 3051:     */     case ':': 
/* 3052:     */     case ';': 
/* 3053:     */     case '<': 
/* 3054:     */     case '=': 
/* 3055:     */     case '>': 
/* 3056:     */     case '?': 
/* 3057:     */     case '@': 
/* 3058:     */     case '\\': 
/* 3059:     */     case '^': 
/* 3060:     */     case '`': 
/* 3061:     */     default: 
/* 3062:2055 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 3063:     */     }
/* 3064:2059 */     if ((paramBoolean) && (localToken1 == null) && (i != -1))
/* 3065:     */     {
/* 3066:2060 */       localToken1 = makeToken(i);
/* 3067:2061 */       localToken1.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 3068:     */     }
/* 3069:2063 */     this._returnToken = localToken1;
/* 3070:     */   }
/* 3071:     */   
/* 3072:     */   protected final void mINT_OR_FLOAT(boolean paramBoolean)
/* 3073:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 3074:     */   {
/* 3075:2067 */     Token localToken = null;int j = this.text.length();
/* 3076:2068 */     int i = 27;
/* 3077:     */     
/* 3078:     */ 
/* 3079:     */ 
/* 3080:2072 */     int k = 0;
/* 3081:     */     for (;;)
/* 3082:     */     {
/* 3083:2075 */       if ((LA(1) >= '0') && (LA(1) <= '9') && (_tokenSet_24.member(LA(2))))
/* 3084:     */       {
/* 3085:2076 */         mDIGIT(false);
/* 3086:     */       }
/* 3087:     */       else
/* 3088:     */       {
/* 3089:2079 */         if (k >= 1) {
/* 3090:     */           break;
/* 3091:     */         }
/* 3092:2079 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 3093:     */       }
/* 3094:2082 */       k++;
/* 3095:     */     }
/* 3096:2086 */     if ((LA(1) == 'L') && (_tokenSet_25.member(LA(2))))
/* 3097:     */     {
/* 3098:2087 */       match('L');
/* 3099:     */     }
/* 3100:2089 */     else if ((LA(1) == 'l') && (_tokenSet_25.member(LA(2))))
/* 3101:     */     {
/* 3102:2090 */       match('l');
/* 3103:     */     }
/* 3104:     */     else
/* 3105:     */     {
/* 3106:2092 */       if (LA(1) == '.')
/* 3107:     */       {
/* 3108:2093 */         match('.');
/* 3109:2097 */         while ((LA(1) >= '0') && (LA(1) <= '9') && (_tokenSet_25.member(LA(2)))) {
/* 3110:2098 */           mDIGIT(false);
/* 3111:     */         }
/* 3112:     */       }
/* 3113:2107 */       if (!_tokenSet_25.member(LA(1))) {
/* 3114:2110 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 3115:     */       }
/* 3116:     */     }
/* 3117:2114 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 3118:     */     {
/* 3119:2115 */       localToken = makeToken(i);
/* 3120:2116 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 3121:     */     }
/* 3122:2118 */     this._returnToken = localToken;
/* 3123:     */   }
/* 3124:     */   
/* 3125:     */   protected final void mSL_COMMENT(boolean paramBoolean)
/* 3126:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 3127:     */   {
/* 3128:2122 */     Token localToken = null;int j = this.text.length();
/* 3129:2123 */     int i = 20;
/* 3130:     */     
/* 3131:     */ 
/* 3132:2126 */     match("//");
/* 3133:2131 */     while ((LA(1) != '\n') && (LA(1) != '\r') && 
/* 3134:2132 */       (LA(1) >= '\003') && (LA(1) <= 'ÿ') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 3135:2133 */       matchNot(65535);
/* 3136:     */     }
/* 3137:2142 */     if ((LA(1) == '\r') && (LA(2) == '\n')) {
/* 3138:2143 */       match("\r\n");
/* 3139:2145 */     } else if (LA(1) == '\n') {
/* 3140:2146 */       match('\n');
/* 3141:2148 */     } else if (LA(1) == '\r') {
/* 3142:2149 */       match('\r');
/* 3143:     */     } else {
/* 3144:2152 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 3145:     */     }
/* 3146:2156 */     newline();
/* 3147:2157 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 3148:     */     {
/* 3149:2158 */       localToken = makeToken(i);
/* 3150:2159 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 3151:     */     }
/* 3152:2161 */     this._returnToken = localToken;
/* 3153:     */   }
/* 3154:     */   
/* 3155:     */   protected final void mML_COMMENT(boolean paramBoolean)
/* 3156:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 3157:     */   {
/* 3158:2165 */     Token localToken = null;int j = this.text.length();
/* 3159:2166 */     int i = 21;
/* 3160:     */     
/* 3161:     */ 
/* 3162:2169 */     match("/*");
/* 3163:2174 */     while ((LA(1) != '*') || (LA(2) != '/')) {
/* 3164:2175 */       if ((LA(1) == '\r') && (LA(2) == '\n') && (LA(3) >= '\003') && (LA(3) <= 'ÿ'))
/* 3165:     */       {
/* 3166:2176 */         match('\r');
/* 3167:2177 */         match('\n');
/* 3168:2178 */         newline();
/* 3169:     */       }
/* 3170:2180 */       else if ((LA(1) == '\r') && (LA(2) >= '\003') && (LA(2) <= 'ÿ') && (LA(3) >= '\003') && (LA(3) <= 'ÿ'))
/* 3171:     */       {
/* 3172:2181 */         match('\r');
/* 3173:2182 */         newline();
/* 3174:     */       }
/* 3175:2184 */       else if ((LA(1) == '\n') && (LA(2) >= '\003') && (LA(2) <= 'ÿ') && (LA(3) >= '\003') && (LA(3) <= 'ÿ'))
/* 3176:     */       {
/* 3177:2185 */         match('\n');
/* 3178:2186 */         newline();
/* 3179:     */       }
/* 3180:     */       else
/* 3181:     */       {
/* 3182:2188 */         if ((LA(1) < '\003') || (LA(1) > 'ÿ') || (LA(2) < '\003') || (LA(2) > 'ÿ') || (LA(3) < '\003') || (LA(3) > 'ÿ')) {
/* 3183:     */           break;
/* 3184:     */         }
/* 3185:2189 */         matchNot(65535);
/* 3186:     */       }
/* 3187:     */     }
/* 3188:2197 */     match("*/");
/* 3189:2198 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 3190:     */     {
/* 3191:2199 */       localToken = makeToken(i);
/* 3192:2200 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 3193:     */     }
/* 3194:2202 */     this._returnToken = localToken;
/* 3195:     */   }
/* 3196:     */   
/* 3197:     */   protected final void mESC(boolean paramBoolean)
/* 3198:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 3199:     */   {
/* 3200:2206 */     Token localToken = null;int j = this.text.length();
/* 3201:2207 */     int i = 24;
/* 3202:     */     
/* 3203:     */ 
/* 3204:2210 */     match('\\');
/* 3205:2212 */     switch (LA(1))
/* 3206:     */     {
/* 3207:     */     case 'n': 
/* 3208:2215 */       match('n');
/* 3209:2216 */       break;
/* 3210:     */     case 'r': 
/* 3211:2220 */       match('r');
/* 3212:2221 */       break;
/* 3213:     */     case 't': 
/* 3214:2225 */       match('t');
/* 3215:2226 */       break;
/* 3216:     */     case 'b': 
/* 3217:2230 */       match('b');
/* 3218:2231 */       break;
/* 3219:     */     case 'f': 
/* 3220:2235 */       match('f');
/* 3221:2236 */       break;
/* 3222:     */     case '"': 
/* 3223:2240 */       match('"');
/* 3224:2241 */       break;
/* 3225:     */     case '\'': 
/* 3226:2245 */       match('\'');
/* 3227:2246 */       break;
/* 3228:     */     case '\\': 
/* 3229:2250 */       match('\\');
/* 3230:2251 */       break;
/* 3231:     */     case '0': 
/* 3232:     */     case '1': 
/* 3233:     */     case '2': 
/* 3234:     */     case '3': 
/* 3235:2256 */       matchRange('0', '3');
/* 3236:2259 */       if ((LA(1) >= '0') && (LA(1) <= '9') && (LA(2) >= '\003') && (LA(2) <= 'ÿ'))
/* 3237:     */       {
/* 3238:2260 */         mDIGIT(false);
/* 3239:2262 */         if ((LA(1) >= '0') && (LA(1) <= '9') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 3240:2263 */           mDIGIT(false);
/* 3241:2265 */         } else if ((LA(1) < '\003') || (LA(1) > 'ÿ')) {
/* 3242:2268 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 3243:     */         }
/* 3244:     */       }
/* 3245:2273 */       else if ((LA(1) < '\003') || (LA(1) > 'ÿ'))
/* 3246:     */       {
/* 3247:2276 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 3248:     */       }
/* 3249:     */       break;
/* 3250:     */     case '4': 
/* 3251:     */     case '5': 
/* 3252:     */     case '6': 
/* 3253:     */     case '7': 
/* 3254:2285 */       matchRange('4', '7');
/* 3255:2288 */       if ((LA(1) >= '0') && (LA(1) <= '9') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 3256:2289 */         mDIGIT(false);
/* 3257:2291 */       } else if ((LA(1) < '\003') || (LA(1) > 'ÿ')) {
/* 3258:2294 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 3259:     */       }
/* 3260:     */       break;
/* 3261:     */     default: 
/* 3262:2302 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 3263:     */     }
/* 3264:2306 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 3265:     */     {
/* 3266:2307 */       localToken = makeToken(i);
/* 3267:2308 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 3268:     */     }
/* 3269:2310 */     this._returnToken = localToken;
/* 3270:     */   }
/* 3271:     */   
/* 3272:     */   protected final void mDIGIT(boolean paramBoolean)
/* 3273:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 3274:     */   {
/* 3275:2314 */     Token localToken = null;int j = this.text.length();
/* 3276:2315 */     int i = 25;
/* 3277:     */     
/* 3278:     */ 
/* 3279:2318 */     matchRange('0', '9');
/* 3280:2319 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 3281:     */     {
/* 3282:2320 */       localToken = makeToken(i);
/* 3283:2321 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 3284:     */     }
/* 3285:2323 */     this._returnToken = localToken;
/* 3286:     */   }
/* 3287:     */   
/* 3288:     */   private static final long[] mk_tokenSet_0()
/* 3289:     */   {
/* 3290:2328 */     long[] arrayOfLong = new long[8];
/* 3291:2329 */     arrayOfLong[0] = -103079215112L;
/* 3292:2330 */     for (int i = 1; i <= 3; i++) {
/* 3293:2330 */       arrayOfLong[i] = -1L;
/* 3294:     */     }
/* 3295:2331 */     return arrayOfLong;
/* 3296:     */   }
/* 3297:     */   
/* 3298:2333 */   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
/* 3299:     */   
/* 3300:     */   private static final long[] mk_tokenSet_1()
/* 3301:     */   {
/* 3302:2335 */     long[] arrayOfLong = new long[8];
/* 3303:2336 */     arrayOfLong[0] = -145135534866440L;
/* 3304:2337 */     for (int i = 1; i <= 3; i++) {
/* 3305:2337 */       arrayOfLong[i] = -1L;
/* 3306:     */     }
/* 3307:2338 */     return arrayOfLong;
/* 3308:     */   }
/* 3309:     */   
/* 3310:2340 */   public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
/* 3311:     */   
/* 3312:     */   private static final long[] mk_tokenSet_2()
/* 3313:     */   {
/* 3314:2342 */     long[] arrayOfLong = new long[8];
/* 3315:2343 */     arrayOfLong[0] = -141407503262728L;
/* 3316:2344 */     for (int i = 1; i <= 3; i++) {
/* 3317:2344 */       arrayOfLong[i] = -1L;
/* 3318:     */     }
/* 3319:2345 */     return arrayOfLong;
/* 3320:     */   }
/* 3321:     */   
/* 3322:2347 */   public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
/* 3323:     */   
/* 3324:     */   private static final long[] mk_tokenSet_3()
/* 3325:     */   {
/* 3326:2349 */     long[] arrayOfLong = { 0L, 576460745995190270L, 0L, 0L, 0L };
/* 3327:2350 */     return arrayOfLong;
/* 3328:     */   }
/* 3329:     */   
/* 3330:2352 */   public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
/* 3331:     */   
/* 3332:     */   private static final long[] mk_tokenSet_4()
/* 3333:     */   {
/* 3334:2354 */     long[] arrayOfLong = { 4294977024L, 0L, 0L, 0L, 0L };
/* 3335:2355 */     return arrayOfLong;
/* 3336:     */   }
/* 3337:     */   
/* 3338:2357 */   public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
/* 3339:     */   
/* 3340:     */   private static final long[] mk_tokenSet_5()
/* 3341:     */   {
/* 3342:2359 */     long[] arrayOfLong = { 1103806604800L, 0L, 0L, 0L, 0L };
/* 3343:2360 */     return arrayOfLong;
/* 3344:     */   }
/* 3345:     */   
/* 3346:2362 */   public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
/* 3347:     */   
/* 3348:     */   private static final long[] mk_tokenSet_6()
/* 3349:     */   {
/* 3350:2364 */     long[] arrayOfLong = { 287959436729787904L, 576460745995190270L, 0L, 0L, 0L };
/* 3351:2365 */     return arrayOfLong;
/* 3352:     */   }
/* 3353:     */   
/* 3354:2367 */   public static final BitSet _tokenSet_6 = new BitSet(mk_tokenSet_6());
/* 3355:     */   
/* 3356:     */   private static final long[] mk_tokenSet_7()
/* 3357:     */   {
/* 3358:2369 */     long[] arrayOfLong = new long[8];
/* 3359:2370 */     arrayOfLong[0] = -17179869192L;
/* 3360:2371 */     arrayOfLong[1] = -268435457L;
/* 3361:2372 */     for (int i = 2; i <= 3; i++) {
/* 3362:2372 */       arrayOfLong[i] = -1L;
/* 3363:     */     }
/* 3364:2373 */     return arrayOfLong;
/* 3365:     */   }
/* 3366:     */   
/* 3367:2375 */   public static final BitSet _tokenSet_7 = new BitSet(mk_tokenSet_7());
/* 3368:     */   
/* 3369:     */   private static final long[] mk_tokenSet_8()
/* 3370:     */   {
/* 3371:2377 */     long[] arrayOfLong = new long[8];
/* 3372:2378 */     arrayOfLong[0] = -549755813896L;
/* 3373:2379 */     arrayOfLong[1] = -268435457L;
/* 3374:2380 */     for (int i = 2; i <= 3; i++) {
/* 3375:2380 */       arrayOfLong[i] = -1L;
/* 3376:     */     }
/* 3377:2381 */     return arrayOfLong;
/* 3378:     */   }
/* 3379:     */   
/* 3380:2383 */   public static final BitSet _tokenSet_8 = new BitSet(mk_tokenSet_8());
/* 3381:     */   
/* 3382:     */   private static final long[] mk_tokenSet_9()
/* 3383:     */   {
/* 3384:2385 */     long[] arrayOfLong = { 287948901175001088L, 576460745995190270L, 0L, 0L, 0L };
/* 3385:2386 */     return arrayOfLong;
/* 3386:     */   }
/* 3387:     */   
/* 3388:2388 */   public static final BitSet _tokenSet_9 = new BitSet(mk_tokenSet_9());
/* 3389:     */   
/* 3390:     */   private static final long[] mk_tokenSet_10()
/* 3391:     */   {
/* 3392:2390 */     long[] arrayOfLong = { 287950056521213440L, 576460746129407998L, 0L, 0L, 0L };
/* 3393:2391 */     return arrayOfLong;
/* 3394:     */   }
/* 3395:     */   
/* 3396:2393 */   public static final BitSet _tokenSet_10 = new BitSet(mk_tokenSet_10());
/* 3397:     */   
/* 3398:     */   private static final long[] mk_tokenSet_11()
/* 3399:     */   {
/* 3400:2395 */     long[] arrayOfLong = { 287958332923183104L, 576460745995190270L, 0L, 0L, 0L };
/* 3401:2396 */     return arrayOfLong;
/* 3402:     */   }
/* 3403:     */   
/* 3404:2398 */   public static final BitSet _tokenSet_11 = new BitSet(mk_tokenSet_11());
/* 3405:     */   
/* 3406:     */   private static final long[] mk_tokenSet_12()
/* 3407:     */   {
/* 3408:2400 */     long[] arrayOfLong = { 287978128427460096L, 576460746532061182L, 0L, 0L, 0L };
/* 3409:2401 */     return arrayOfLong;
/* 3410:     */   }
/* 3411:     */   
/* 3412:2403 */   public static final BitSet _tokenSet_12 = new BitSet(mk_tokenSet_12());
/* 3413:     */   
/* 3414:     */   private static final long[] mk_tokenSet_13()
/* 3415:     */   {
/* 3416:2405 */     long[] arrayOfLong = { 2306123388973753856L, 671088640L, 0L, 0L, 0L };
/* 3417:2406 */     return arrayOfLong;
/* 3418:     */   }
/* 3419:     */   
/* 3420:2408 */   public static final BitSet _tokenSet_13 = new BitSet(mk_tokenSet_13());
/* 3421:     */   
/* 3422:     */   private static final long[] mk_tokenSet_14()
/* 3423:     */   {
/* 3424:2410 */     long[] arrayOfLong = { 287952805300282880L, 576460746129407998L, 0L, 0L, 0L };
/* 3425:2411 */     return arrayOfLong;
/* 3426:     */   }
/* 3427:     */   
/* 3428:2413 */   public static final BitSet _tokenSet_14 = new BitSet(mk_tokenSet_14());
/* 3429:     */   
/* 3430:     */   private static final long[] mk_tokenSet_15()
/* 3431:     */   {
/* 3432:2415 */     long[] arrayOfLong = { 2305843013508670976L, 0L, 0L, 0L, 0L };
/* 3433:2416 */     return arrayOfLong;
/* 3434:     */   }
/* 3435:     */   
/* 3436:2418 */   public static final BitSet _tokenSet_15 = new BitSet(mk_tokenSet_15());
/* 3437:     */   
/* 3438:     */   private static final long[] mk_tokenSet_16()
/* 3439:     */   {
/* 3440:2420 */     long[] arrayOfLong = { 2306051920717948416L, 536870912L, 0L, 0L, 0L };
/* 3441:2421 */     return arrayOfLong;
/* 3442:     */   }
/* 3443:     */   
/* 3444:2423 */   public static final BitSet _tokenSet_16 = new BitSet(mk_tokenSet_16());
/* 3445:     */   
/* 3446:     */   private static final long[] mk_tokenSet_17()
/* 3447:     */   {
/* 3448:2425 */     long[] arrayOfLong = { 208911504254464L, 536870912L, 0L, 0L, 0L };
/* 3449:2426 */     return arrayOfLong;
/* 3450:     */   }
/* 3451:     */   
/* 3452:2428 */   public static final BitSet _tokenSet_17 = new BitSet(mk_tokenSet_17());
/* 3453:     */   
/* 3454:     */   private static final long[] mk_tokenSet_18()
/* 3455:     */   {
/* 3456:2430 */     long[] arrayOfLong = { 1151051235328L, 576460746129407998L, 0L, 0L, 0L };
/* 3457:2431 */     return arrayOfLong;
/* 3458:     */   }
/* 3459:     */   
/* 3460:2433 */   public static final BitSet _tokenSet_18 = new BitSet(mk_tokenSet_18());
/* 3461:     */   
/* 3462:     */   private static final long[] mk_tokenSet_19()
/* 3463:     */   {
/* 3464:2435 */     long[] arrayOfLong = { 189120294954496L, 0L, 0L, 0L, 0L };
/* 3465:2436 */     return arrayOfLong;
/* 3466:     */   }
/* 3467:     */   
/* 3468:2438 */   public static final BitSet _tokenSet_19 = new BitSet(mk_tokenSet_19());
/* 3469:     */   
/* 3470:     */   private static final long[] mk_tokenSet_20()
/* 3471:     */   {
/* 3472:2440 */     long[] arrayOfLong = { 288139722277004800L, 576460746129407998L, 0L, 0L, 0L };
/* 3473:2441 */     return arrayOfLong;
/* 3474:     */   }
/* 3475:     */   
/* 3476:2443 */   public static final BitSet _tokenSet_20 = new BitSet(mk_tokenSet_20());
/* 3477:     */   
/* 3478:     */   private static final long[] mk_tokenSet_21()
/* 3479:     */   {
/* 3480:2445 */     long[] arrayOfLong = { 288049596683265536L, 576460746666278910L, 0L, 0L, 0L };
/* 3481:2446 */     return arrayOfLong;
/* 3482:     */   }
/* 3483:     */   
/* 3484:2448 */   public static final BitSet _tokenSet_21 = new BitSet(mk_tokenSet_21());
/* 3485:     */   
/* 3486:     */   private static final long[] mk_tokenSet_22()
/* 3487:     */   {
/* 3488:2450 */     long[] arrayOfLong = { 287960536241415680L, 576460745995190270L, 0L, 0L, 0L };
/* 3489:2451 */     return arrayOfLong;
/* 3490:     */   }
/* 3491:     */   
/* 3492:2453 */   public static final BitSet _tokenSet_22 = new BitSet(mk_tokenSet_22());
/* 3493:     */   
/* 3494:     */   private static final long[] mk_tokenSet_23()
/* 3495:     */   {
/* 3496:2455 */     long[] arrayOfLong = { 287958337218160128L, 576460745995190270L, 0L, 0L, 0L };
/* 3497:2456 */     return arrayOfLong;
/* 3498:     */   }
/* 3499:     */   
/* 3500:2458 */   public static final BitSet _tokenSet_23 = new BitSet(mk_tokenSet_23());
/* 3501:     */   
/* 3502:     */   private static final long[] mk_tokenSet_24()
/* 3503:     */   {
/* 3504:2460 */     long[] arrayOfLong = { 288228817078593024L, 576460746532061182L, 0L, 0L, 0L };
/* 3505:2461 */     return arrayOfLong;
/* 3506:     */   }
/* 3507:     */   
/* 3508:2463 */   public static final BitSet _tokenSet_24 = new BitSet(mk_tokenSet_24());
/* 3509:     */   
/* 3510:     */   private static final long[] mk_tokenSet_25()
/* 3511:     */   {
/* 3512:2465 */     long[] arrayOfLong = { 288158448334415360L, 576460746532061182L, 0L, 0L, 0L };
/* 3513:2466 */     return arrayOfLong;
/* 3514:     */   }
/* 3515:     */   
/* 3516:2468 */   public static final BitSet _tokenSet_25 = new BitSet(mk_tokenSet_25());
/* 3517:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.actions.java.ActionLexer
 * JD-Core Version:    0.7.0.1
 */