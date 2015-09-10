/*    1:     */ package antlr.actions.python;
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
/*   35:  63 */   protected int lineOffset = 0;
/*   36:     */   private Tool antlrTool;
/*   37:     */   ActionTransInfo transInfo;
/*   38:     */   
/*   39:     */   public ActionLexer(String paramString, RuleBlock paramRuleBlock, CodeGenerator paramCodeGenerator, ActionTransInfo paramActionTransInfo)
/*   40:     */   {
/*   41:  73 */     this(new StringReader(paramString));
/*   42:  74 */     this.currentRule = paramRuleBlock;
/*   43:  75 */     this.generator = paramCodeGenerator;
/*   44:  76 */     this.transInfo = paramActionTransInfo;
/*   45:     */   }
/*   46:     */   
/*   47:     */   public void setLineOffset(int paramInt)
/*   48:     */   {
/*   49:  81 */     setLine(paramInt);
/*   50:     */   }
/*   51:     */   
/*   52:     */   public void setTool(Tool paramTool)
/*   53:     */   {
/*   54:  85 */     this.antlrTool = paramTool;
/*   55:     */   }
/*   56:     */   
/*   57:     */   public void reportError(RecognitionException paramRecognitionException)
/*   58:     */   {
/*   59:  90 */     this.antlrTool.error("Syntax error in action: " + paramRecognitionException, getFilename(), getLine(), getColumn());
/*   60:     */   }
/*   61:     */   
/*   62:     */   public void reportError(String paramString)
/*   63:     */   {
/*   64:  97 */     this.antlrTool.error(paramString, getFilename(), getLine(), getColumn());
/*   65:     */   }
/*   66:     */   
/*   67:     */   public void reportWarning(String paramString)
/*   68:     */   {
/*   69: 102 */     if (getFilename() == null) {
/*   70: 103 */       this.antlrTool.warning(paramString);
/*   71:     */     } else {
/*   72: 106 */       this.antlrTool.warning(paramString, getFilename(), getLine(), getColumn());
/*   73:     */     }
/*   74:     */   }
/*   75:     */   
/*   76:     */   public ActionLexer(InputStream paramInputStream)
/*   77:     */   {
/*   78: 110 */     this(new ByteBuffer(paramInputStream));
/*   79:     */   }
/*   80:     */   
/*   81:     */   public ActionLexer(Reader paramReader)
/*   82:     */   {
/*   83: 113 */     this(new CharBuffer(paramReader));
/*   84:     */   }
/*   85:     */   
/*   86:     */   public ActionLexer(InputBuffer paramInputBuffer)
/*   87:     */   {
/*   88: 116 */     this(new LexerSharedInputState(paramInputBuffer));
/*   89:     */   }
/*   90:     */   
/*   91:     */   public ActionLexer(LexerSharedInputState paramLexerSharedInputState)
/*   92:     */   {
/*   93: 119 */     super(paramLexerSharedInputState);
/*   94: 120 */     this.caseSensitiveLiterals = true;
/*   95: 121 */     setCaseSensitive(true);
/*   96: 122 */     this.literals = new Hashtable();
/*   97:     */   }
/*   98:     */   
/*   99:     */   public Token nextToken()
/*  100:     */     throws TokenStreamException
/*  101:     */   {
/*  102: 126 */     Token localToken = null;
/*  103:     */     for (;;)
/*  104:     */     {
/*  105: 129 */       Object localObject = null;
/*  106: 130 */       int i = 0;
/*  107: 131 */       resetText();
/*  108:     */       try
/*  109:     */       {
/*  110: 134 */         if ((LA(1) >= '\003') && (LA(1) <= 'ÿ'))
/*  111:     */         {
/*  112: 135 */           mACTION(true);
/*  113: 136 */           localToken = this._returnToken;
/*  114:     */         }
/*  115: 139 */         else if (LA(1) == 65535)
/*  116:     */         {
/*  117: 139 */           uponEOF();this._returnToken = makeToken(1);
/*  118:     */         }
/*  119:     */         else
/*  120:     */         {
/*  121: 140 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  122:     */         }
/*  123: 143 */         if (this._returnToken == null) {
/*  124:     */           continue;
/*  125:     */         }
/*  126: 144 */         i = this._returnToken.getType();
/*  127: 145 */         this._returnToken.setType(i);
/*  128: 146 */         return this._returnToken;
/*  129:     */       }
/*  130:     */       catch (RecognitionException localRecognitionException)
/*  131:     */       {
/*  132: 149 */         throw new TokenStreamRecognitionException(localRecognitionException);
/*  133:     */       }
/*  134:     */       catch (CharStreamException localCharStreamException)
/*  135:     */       {
/*  136: 153 */         if ((localCharStreamException instanceof CharStreamIOException)) {
/*  137: 154 */           throw new TokenStreamIOException(((CharStreamIOException)localCharStreamException).io);
/*  138:     */         }
/*  139: 157 */         throw new TokenStreamException(localCharStreamException.getMessage());
/*  140:     */       }
/*  141:     */     }
/*  142:     */   }
/*  143:     */   
/*  144:     */   public final void mACTION(boolean paramBoolean)
/*  145:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  146:     */   {
/*  147: 164 */     Token localToken = null;int j = this.text.length();
/*  148: 165 */     int i = 4;
/*  149:     */     
/*  150:     */ 
/*  151:     */ 
/*  152: 169 */     int k = 0;
/*  153:     */     for (;;)
/*  154:     */     {
/*  155: 172 */       switch (LA(1))
/*  156:     */       {
/*  157:     */       case '#': 
/*  158: 175 */         mAST_ITEM(false);
/*  159: 176 */         break;
/*  160:     */       case '$': 
/*  161: 180 */         mTEXT_ITEM(false);
/*  162: 181 */         break;
/*  163:     */       default: 
/*  164: 184 */         if (_tokenSet_0.member(LA(1)))
/*  165:     */         {
/*  166: 185 */           mSTUFF(false);
/*  167:     */         }
/*  168:     */         else
/*  169:     */         {
/*  170: 188 */           if (k >= 1) {
/*  171:     */             break label126;
/*  172:     */           }
/*  173: 188 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  174:     */         }
/*  175:     */         break;
/*  176:     */       }
/*  177: 191 */       k++;
/*  178:     */     }
/*  179:     */     label126:
/*  180: 194 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/*  181:     */     {
/*  182: 195 */       localToken = makeToken(i);
/*  183: 196 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  184:     */     }
/*  185: 198 */     this._returnToken = localToken;
/*  186:     */   }
/*  187:     */   
/*  188:     */   protected final void mSTUFF(boolean paramBoolean)
/*  189:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  190:     */   {
/*  191: 202 */     Token localToken = null;int j = this.text.length();
/*  192: 203 */     int i = 5;
/*  193: 206 */     switch (LA(1))
/*  194:     */     {
/*  195:     */     case '"': 
/*  196: 209 */       mSTRING(false);
/*  197: 210 */       break;
/*  198:     */     case '\'': 
/*  199: 214 */       mCHAR(false);
/*  200: 215 */       break;
/*  201:     */     case '\n': 
/*  202: 219 */       match('\n');
/*  203: 220 */       newline();
/*  204: 221 */       break;
/*  205:     */     default: 
/*  206: 224 */       if ((LA(1) == '/') && ((LA(2) == '*') || (LA(2) == '/')))
/*  207:     */       {
/*  208: 225 */         mCOMMENT(false);
/*  209:     */       }
/*  210: 227 */       else if ((LA(1) == '\r') && (LA(2) == '\n'))
/*  211:     */       {
/*  212: 228 */         match("\r\n");
/*  213: 229 */         newline();
/*  214:     */       }
/*  215: 231 */       else if ((LA(1) == '/') && (_tokenSet_1.member(LA(2))))
/*  216:     */       {
/*  217: 232 */         match('/');
/*  218:     */         
/*  219: 234 */         match(_tokenSet_1);
/*  220:     */       }
/*  221: 237 */       else if (LA(1) == '\r')
/*  222:     */       {
/*  223: 238 */         match('\r');
/*  224: 239 */         newline();
/*  225:     */       }
/*  226: 241 */       else if (_tokenSet_2.member(LA(1)))
/*  227:     */       {
/*  228: 243 */         match(_tokenSet_2);
/*  229:     */       }
/*  230:     */       else
/*  231:     */       {
/*  232: 247 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  233:     */       }
/*  234:     */       break;
/*  235:     */     }
/*  236: 250 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/*  237:     */     {
/*  238: 251 */       localToken = makeToken(i);
/*  239: 252 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  240:     */     }
/*  241: 254 */     this._returnToken = localToken;
/*  242:     */   }
/*  243:     */   
/*  244:     */   protected final void mAST_ITEM(boolean paramBoolean)
/*  245:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  246:     */   {
/*  247: 258 */     Token localToken1 = null;int j = this.text.length();
/*  248: 259 */     int i = 6;
/*  249:     */     
/*  250: 261 */     Token localToken2 = null;
/*  251: 262 */     Token localToken3 = null;
/*  252: 263 */     Token localToken4 = null;
/*  253:     */     int k;
/*  254: 265 */     if ((LA(1) == '#') && (LA(2) == '('))
/*  255:     */     {
/*  256: 266 */       k = this.text.length();
/*  257: 267 */       match('#');
/*  258: 268 */       this.text.setLength(k);
/*  259: 269 */       mTREE(true);
/*  260: 270 */       localToken2 = this._returnToken;
/*  261:     */     }
/*  262:     */     else
/*  263:     */     {
/*  264:     */       String str1;
/*  265: 272 */       if ((LA(1) == '#') && (_tokenSet_3.member(LA(2))))
/*  266:     */       {
/*  267: 273 */         k = this.text.length();
/*  268: 274 */         match('#');
/*  269: 275 */         this.text.setLength(k);
/*  270: 276 */         mID(true);
/*  271: 277 */         localToken3 = this._returnToken;
/*  272:     */         
/*  273: 279 */         str1 = localToken3.getText();
/*  274: 280 */         String str2 = this.generator.mapTreeId(str1, this.transInfo);
/*  275: 281 */         if (str2 != null)
/*  276:     */         {
/*  277: 282 */           this.text.setLength(j);this.text.append(str2);
/*  278:     */         }
/*  279: 286 */         if (_tokenSet_4.member(LA(1))) {
/*  280: 287 */           mWS(false);
/*  281:     */         }
/*  282: 294 */         if (LA(1) == '=') {
/*  283: 295 */           mVAR_ASSIGN(false);
/*  284:     */         }
/*  285:     */       }
/*  286: 302 */       else if ((LA(1) == '#') && (LA(2) == '['))
/*  287:     */       {
/*  288: 303 */         k = this.text.length();
/*  289: 304 */         match('#');
/*  290: 305 */         this.text.setLength(k);
/*  291: 306 */         mAST_CONSTRUCTOR(true);
/*  292: 307 */         localToken4 = this._returnToken;
/*  293:     */       }
/*  294: 309 */       else if ((LA(1) == '#') && (LA(2) == '#'))
/*  295:     */       {
/*  296: 310 */         match("##");
/*  297:     */         
/*  298: 312 */         str1 = this.currentRule.getRuleName() + "_AST";this.text.setLength(j);this.text.append(str1);
/*  299: 313 */         if (this.transInfo != null) {
/*  300: 314 */           this.transInfo.refRuleRoot = str1;
/*  301:     */         }
/*  302: 318 */         if (_tokenSet_4.member(LA(1))) {
/*  303: 319 */           mWS(false);
/*  304:     */         }
/*  305: 326 */         if (LA(1) == '=') {
/*  306: 327 */           mVAR_ASSIGN(false);
/*  307:     */         }
/*  308:     */       }
/*  309:     */       else
/*  310:     */       {
/*  311: 335 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  312:     */       }
/*  313:     */     }
/*  314: 338 */     if ((paramBoolean) && (localToken1 == null) && (i != -1))
/*  315:     */     {
/*  316: 339 */       localToken1 = makeToken(i);
/*  317: 340 */       localToken1.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  318:     */     }
/*  319: 342 */     this._returnToken = localToken1;
/*  320:     */   }
/*  321:     */   
/*  322:     */   protected final void mTEXT_ITEM(boolean paramBoolean)
/*  323:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  324:     */   {
/*  325: 346 */     Token localToken1 = null;int j = this.text.length();
/*  326: 347 */     int i = 7;
/*  327:     */     
/*  328: 349 */     Token localToken2 = null;
/*  329: 350 */     Token localToken3 = null;
/*  330: 351 */     Token localToken4 = null;
/*  331: 352 */     Token localToken5 = null;
/*  332: 353 */     Token localToken6 = null;
/*  333: 354 */     Token localToken7 = null;
/*  334:     */     String str1;
/*  335: 356 */     if ((LA(1) == '$') && (LA(2) == 's') && (LA(3) == 'e'))
/*  336:     */     {
/*  337: 357 */       match("$set");
/*  338: 359 */       if ((LA(1) == 'T') && (LA(2) == 'e'))
/*  339:     */       {
/*  340: 360 */         match("Text");
/*  341: 362 */         switch (LA(1))
/*  342:     */         {
/*  343:     */         case '\t': 
/*  344:     */         case '\n': 
/*  345:     */         case '\r': 
/*  346:     */         case ' ': 
/*  347: 365 */           mWS(false);
/*  348: 366 */           break;
/*  349:     */         case '(': 
/*  350:     */           break;
/*  351:     */         default: 
/*  352: 374 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  353:     */         }
/*  354: 378 */         match('(');
/*  355: 379 */         mTEXT_ARG(true);
/*  356: 380 */         localToken3 = this._returnToken;
/*  357: 381 */         match(')');
/*  358:     */         
/*  359:     */ 
/*  360: 384 */         str1 = "self.text.setLength(_begin) ; self.text.append(" + localToken3.getText() + ")";
/*  361: 385 */         this.text.setLength(j);this.text.append(str1);
/*  362:     */       }
/*  363: 388 */       else if ((LA(1) == 'T') && (LA(2) == 'o'))
/*  364:     */       {
/*  365: 389 */         match("Token");
/*  366: 391 */         switch (LA(1))
/*  367:     */         {
/*  368:     */         case '\t': 
/*  369:     */         case '\n': 
/*  370:     */         case '\r': 
/*  371:     */         case ' ': 
/*  372: 394 */           mWS(false);
/*  373: 395 */           break;
/*  374:     */         case '(': 
/*  375:     */           break;
/*  376:     */         default: 
/*  377: 403 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  378:     */         }
/*  379: 407 */         match('(');
/*  380: 408 */         mTEXT_ARG(true);
/*  381: 409 */         localToken4 = this._returnToken;
/*  382: 410 */         match(')');
/*  383:     */         
/*  384: 412 */         str1 = "_token = " + localToken4.getText();
/*  385: 413 */         this.text.setLength(j);this.text.append(str1);
/*  386:     */       }
/*  387: 416 */       else if ((LA(1) == 'T') && (LA(2) == 'y'))
/*  388:     */       {
/*  389: 417 */         match("Type");
/*  390: 419 */         switch (LA(1))
/*  391:     */         {
/*  392:     */         case '\t': 
/*  393:     */         case '\n': 
/*  394:     */         case '\r': 
/*  395:     */         case ' ': 
/*  396: 422 */           mWS(false);
/*  397: 423 */           break;
/*  398:     */         case '(': 
/*  399:     */           break;
/*  400:     */         default: 
/*  401: 431 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  402:     */         }
/*  403: 435 */         match('(');
/*  404: 436 */         mTEXT_ARG(true);
/*  405: 437 */         localToken5 = this._returnToken;
/*  406: 438 */         match(')');
/*  407:     */         
/*  408: 440 */         str1 = "_ttype = " + localToken5.getText();
/*  409: 441 */         this.text.setLength(j);this.text.append(str1);
/*  410:     */       }
/*  411:     */       else
/*  412:     */       {
/*  413: 445 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  414:     */       }
/*  415:     */     }
/*  416:     */     else
/*  417:     */     {
/*  418:     */       String str2;
/*  419: 450 */       if ((LA(1) == '$') && (LA(2) == 'F') && (LA(3) == 'O'))
/*  420:     */       {
/*  421: 451 */         match("$FOLLOW");
/*  422: 453 */         if ((_tokenSet_5.member(LA(1))) && (_tokenSet_6.member(LA(2))) && (LA(3) >= '\003') && (LA(3) <= 'ÿ'))
/*  423:     */         {
/*  424: 455 */           switch (LA(1))
/*  425:     */           {
/*  426:     */           case '\t': 
/*  427:     */           case '\n': 
/*  428:     */           case '\r': 
/*  429:     */           case ' ': 
/*  430: 458 */             mWS(false);
/*  431: 459 */             break;
/*  432:     */           case '(': 
/*  433:     */             break;
/*  434:     */           default: 
/*  435: 467 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  436:     */           }
/*  437: 471 */           match('(');
/*  438: 472 */           mTEXT_ARG(true);
/*  439: 473 */           localToken6 = this._returnToken;
/*  440: 474 */           match(')');
/*  441:     */         }
/*  442: 481 */         str1 = this.currentRule.getRuleName();
/*  443: 482 */         if (localToken6 != null) {
/*  444: 483 */           str1 = localToken6.getText();
/*  445:     */         }
/*  446: 485 */         str2 = this.generator.getFOLLOWBitSet(str1, 1);
/*  447: 486 */         if (str2 == null)
/*  448:     */         {
/*  449: 487 */           reportError("$FOLLOW(" + str1 + ")" + ": unknown rule or bad lookahead computation");
/*  450:     */         }
/*  451:     */         else
/*  452:     */         {
/*  453: 491 */           this.text.setLength(j);this.text.append(str2);
/*  454:     */         }
/*  455:     */       }
/*  456: 495 */       else if ((LA(1) == '$') && (LA(2) == 'F') && (LA(3) == 'I'))
/*  457:     */       {
/*  458: 496 */         match("$FIRST");
/*  459: 498 */         if ((_tokenSet_5.member(LA(1))) && (_tokenSet_6.member(LA(2))) && (LA(3) >= '\003') && (LA(3) <= 'ÿ'))
/*  460:     */         {
/*  461: 500 */           switch (LA(1))
/*  462:     */           {
/*  463:     */           case '\t': 
/*  464:     */           case '\n': 
/*  465:     */           case '\r': 
/*  466:     */           case ' ': 
/*  467: 503 */             mWS(false);
/*  468: 504 */             break;
/*  469:     */           case '(': 
/*  470:     */             break;
/*  471:     */           default: 
/*  472: 512 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  473:     */           }
/*  474: 516 */           match('(');
/*  475: 517 */           mTEXT_ARG(true);
/*  476: 518 */           localToken7 = this._returnToken;
/*  477: 519 */           match(')');
/*  478:     */         }
/*  479: 526 */         str1 = this.currentRule.getRuleName();
/*  480: 527 */         if (localToken7 != null) {
/*  481: 528 */           str1 = localToken7.getText();
/*  482:     */         }
/*  483: 530 */         str2 = this.generator.getFIRSTBitSet(str1, 1);
/*  484: 531 */         if (str2 == null)
/*  485:     */         {
/*  486: 532 */           reportError("$FIRST(" + str1 + ")" + ": unknown rule or bad lookahead computation");
/*  487:     */         }
/*  488:     */         else
/*  489:     */         {
/*  490: 536 */           this.text.setLength(j);this.text.append(str2);
/*  491:     */         }
/*  492:     */       }
/*  493: 540 */       else if ((LA(1) == '$') && (LA(2) == 's') && (LA(3) == 'k'))
/*  494:     */       {
/*  495: 541 */         match("$skip");
/*  496:     */         
/*  497: 543 */         this.text.setLength(j);this.text.append("_ttype = SKIP");
/*  498:     */       }
/*  499: 546 */       else if ((LA(1) == '$') && (LA(2) == 'a'))
/*  500:     */       {
/*  501: 547 */         match("$append");
/*  502: 549 */         switch (LA(1))
/*  503:     */         {
/*  504:     */         case '\t': 
/*  505:     */         case '\n': 
/*  506:     */         case '\r': 
/*  507:     */         case ' ': 
/*  508: 552 */           mWS(false);
/*  509: 553 */           break;
/*  510:     */         case '(': 
/*  511:     */           break;
/*  512:     */         default: 
/*  513: 561 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  514:     */         }
/*  515: 565 */         match('(');
/*  516: 566 */         mTEXT_ARG(true);
/*  517: 567 */         localToken2 = this._returnToken;
/*  518: 568 */         match(')');
/*  519:     */         
/*  520: 570 */         str1 = "self.text.append(" + localToken2.getText() + ")";
/*  521: 571 */         this.text.setLength(j);this.text.append(str1);
/*  522:     */       }
/*  523: 574 */       else if ((LA(1) == '$') && (LA(2) == 'g'))
/*  524:     */       {
/*  525: 575 */         match("$getText");
/*  526:     */         
/*  527: 577 */         this.text.setLength(j);this.text.append("self.text.getString(_begin)");
/*  528:     */       }
/*  529: 580 */       else if ((LA(1) == '$') && (LA(2) == 'n'))
/*  530:     */       {
/*  531: 582 */         if ((LA(1) == '$') && (LA(2) == 'n') && (LA(3) == 'l')) {
/*  532: 583 */           match("$nl");
/*  533: 585 */         } else if ((LA(1) == '$') && (LA(2) == 'n') && (LA(3) == 'e')) {
/*  534: 586 */           match("$newline");
/*  535:     */         } else {
/*  536: 589 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  537:     */         }
/*  538: 594 */         this.text.setLength(j);this.text.append("self.newline()");
/*  539:     */       }
/*  540:     */       else
/*  541:     */       {
/*  542: 598 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  543:     */       }
/*  544:     */     }
/*  545: 601 */     if ((paramBoolean) && (localToken1 == null) && (i != -1))
/*  546:     */     {
/*  547: 602 */       localToken1 = makeToken(i);
/*  548: 603 */       localToken1.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  549:     */     }
/*  550: 605 */     this._returnToken = localToken1;
/*  551:     */   }
/*  552:     */   
/*  553:     */   protected final void mCOMMENT(boolean paramBoolean)
/*  554:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  555:     */   {
/*  556: 609 */     Token localToken = null;int j = this.text.length();
/*  557: 610 */     int i = 19;
/*  558: 614 */     if ((LA(1) == '/') && (LA(2) == '/')) {
/*  559: 615 */       mSL_COMMENT(false);
/*  560: 617 */     } else if ((LA(1) == '/') && (LA(2) == '*')) {
/*  561: 618 */       mML_COMMENT(false);
/*  562:     */     } else {
/*  563: 621 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  564:     */     }
/*  565: 627 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/*  566:     */     {
/*  567: 628 */       localToken = makeToken(i);
/*  568: 629 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  569:     */     }
/*  570: 631 */     this._returnToken = localToken;
/*  571:     */   }
/*  572:     */   
/*  573:     */   protected final void mSTRING(boolean paramBoolean)
/*  574:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  575:     */   {
/*  576: 635 */     Token localToken = null;int j = this.text.length();
/*  577: 636 */     int i = 24;
/*  578:     */     
/*  579:     */ 
/*  580: 639 */     match('"');
/*  581:     */     for (;;)
/*  582:     */     {
/*  583: 643 */       if (LA(1) == '\\')
/*  584:     */       {
/*  585: 644 */         mESC(false);
/*  586:     */       }
/*  587:     */       else
/*  588:     */       {
/*  589: 646 */         if (!_tokenSet_7.member(LA(1))) {
/*  590:     */           break;
/*  591:     */         }
/*  592: 647 */         matchNot('"');
/*  593:     */       }
/*  594:     */     }
/*  595: 655 */     match('"');
/*  596: 656 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/*  597:     */     {
/*  598: 657 */       localToken = makeToken(i);
/*  599: 658 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  600:     */     }
/*  601: 660 */     this._returnToken = localToken;
/*  602:     */   }
/*  603:     */   
/*  604:     */   protected final void mCHAR(boolean paramBoolean)
/*  605:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  606:     */   {
/*  607: 664 */     Token localToken = null;int j = this.text.length();
/*  608: 665 */     int i = 23;
/*  609:     */     
/*  610:     */ 
/*  611: 668 */     match('\'');
/*  612: 670 */     if (LA(1) == '\\') {
/*  613: 671 */       mESC(false);
/*  614: 673 */     } else if (_tokenSet_8.member(LA(1))) {
/*  615: 674 */       matchNot('\'');
/*  616:     */     } else {
/*  617: 677 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  618:     */     }
/*  619: 681 */     match('\'');
/*  620: 682 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/*  621:     */     {
/*  622: 683 */       localToken = makeToken(i);
/*  623: 684 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  624:     */     }
/*  625: 686 */     this._returnToken = localToken;
/*  626:     */   }
/*  627:     */   
/*  628:     */   protected final void mTREE(boolean paramBoolean)
/*  629:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  630:     */   {
/*  631: 690 */     Token localToken1 = null;int j = this.text.length();
/*  632: 691 */     int i = 8;
/*  633:     */     
/*  634: 693 */     Token localToken2 = null;
/*  635: 694 */     Token localToken3 = null;
/*  636:     */     
/*  637: 696 */     StringBuffer localStringBuffer = new StringBuffer();
/*  638: 697 */     int m = 0;
/*  639: 698 */     Vector localVector = new Vector(10);
/*  640:     */     
/*  641:     */ 
/*  642: 701 */     int k = this.text.length();
/*  643: 702 */     match('(');
/*  644: 703 */     this.text.setLength(k);
/*  645: 705 */     switch (LA(1))
/*  646:     */     {
/*  647:     */     case '\t': 
/*  648:     */     case '\n': 
/*  649:     */     case '\r': 
/*  650:     */     case ' ': 
/*  651: 708 */       k = this.text.length();
/*  652: 709 */       mWS(false);
/*  653: 710 */       this.text.setLength(k);
/*  654: 711 */       break;
/*  655:     */     case '"': 
/*  656:     */     case '#': 
/*  657:     */     case '(': 
/*  658:     */     case 'A': 
/*  659:     */     case 'B': 
/*  660:     */     case 'C': 
/*  661:     */     case 'D': 
/*  662:     */     case 'E': 
/*  663:     */     case 'F': 
/*  664:     */     case 'G': 
/*  665:     */     case 'H': 
/*  666:     */     case 'I': 
/*  667:     */     case 'J': 
/*  668:     */     case 'K': 
/*  669:     */     case 'L': 
/*  670:     */     case 'M': 
/*  671:     */     case 'N': 
/*  672:     */     case 'O': 
/*  673:     */     case 'P': 
/*  674:     */     case 'Q': 
/*  675:     */     case 'R': 
/*  676:     */     case 'S': 
/*  677:     */     case 'T': 
/*  678:     */     case 'U': 
/*  679:     */     case 'V': 
/*  680:     */     case 'W': 
/*  681:     */     case 'X': 
/*  682:     */     case 'Y': 
/*  683:     */     case 'Z': 
/*  684:     */     case '[': 
/*  685:     */     case '_': 
/*  686:     */     case 'a': 
/*  687:     */     case 'b': 
/*  688:     */     case 'c': 
/*  689:     */     case 'd': 
/*  690:     */     case 'e': 
/*  691:     */     case 'f': 
/*  692:     */     case 'g': 
/*  693:     */     case 'h': 
/*  694:     */     case 'i': 
/*  695:     */     case 'j': 
/*  696:     */     case 'k': 
/*  697:     */     case 'l': 
/*  698:     */     case 'm': 
/*  699:     */     case 'n': 
/*  700:     */     case 'o': 
/*  701:     */     case 'p': 
/*  702:     */     case 'q': 
/*  703:     */     case 'r': 
/*  704:     */     case 's': 
/*  705:     */     case 't': 
/*  706:     */     case 'u': 
/*  707:     */     case 'v': 
/*  708:     */     case 'w': 
/*  709:     */     case 'x': 
/*  710:     */     case 'y': 
/*  711:     */     case 'z': 
/*  712:     */       break;
/*  713:     */     case '\013': 
/*  714:     */     case '\f': 
/*  715:     */     case '\016': 
/*  716:     */     case '\017': 
/*  717:     */     case '\020': 
/*  718:     */     case '\021': 
/*  719:     */     case '\022': 
/*  720:     */     case '\023': 
/*  721:     */     case '\024': 
/*  722:     */     case '\025': 
/*  723:     */     case '\026': 
/*  724:     */     case '\027': 
/*  725:     */     case '\030': 
/*  726:     */     case '\031': 
/*  727:     */     case '\032': 
/*  728:     */     case '\033': 
/*  729:     */     case '\034': 
/*  730:     */     case '\035': 
/*  731:     */     case '\036': 
/*  732:     */     case '\037': 
/*  733:     */     case '!': 
/*  734:     */     case '$': 
/*  735:     */     case '%': 
/*  736:     */     case '&': 
/*  737:     */     case '\'': 
/*  738:     */     case ')': 
/*  739:     */     case '*': 
/*  740:     */     case '+': 
/*  741:     */     case ',': 
/*  742:     */     case '-': 
/*  743:     */     case '.': 
/*  744:     */     case '/': 
/*  745:     */     case '0': 
/*  746:     */     case '1': 
/*  747:     */     case '2': 
/*  748:     */     case '3': 
/*  749:     */     case '4': 
/*  750:     */     case '5': 
/*  751:     */     case '6': 
/*  752:     */     case '7': 
/*  753:     */     case '8': 
/*  754:     */     case '9': 
/*  755:     */     case ':': 
/*  756:     */     case ';': 
/*  757:     */     case '<': 
/*  758:     */     case '=': 
/*  759:     */     case '>': 
/*  760:     */     case '?': 
/*  761:     */     case '@': 
/*  762:     */     case '\\': 
/*  763:     */     case ']': 
/*  764:     */     case '^': 
/*  765:     */     case '`': 
/*  766:     */     default: 
/*  767: 733 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  768:     */     }
/*  769: 737 */     k = this.text.length();
/*  770: 738 */     mTREE_ELEMENT(true);
/*  771: 739 */     this.text.setLength(k);
/*  772: 740 */     localToken2 = this._returnToken;
/*  773: 741 */     localVector.appendElement(localToken2.getText());
/*  774: 743 */     switch (LA(1))
/*  775:     */     {
/*  776:     */     case '\t': 
/*  777:     */     case '\n': 
/*  778:     */     case '\r': 
/*  779:     */     case ' ': 
/*  780: 746 */       k = this.text.length();
/*  781: 747 */       mWS(false);
/*  782: 748 */       this.text.setLength(k);
/*  783: 749 */       break;
/*  784:     */     case ')': 
/*  785:     */     case ',': 
/*  786:     */       break;
/*  787:     */     default: 
/*  788: 757 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  789:     */     }
/*  790: 764 */     while (LA(1) == ',')
/*  791:     */     {
/*  792: 765 */       k = this.text.length();
/*  793: 766 */       match(',');
/*  794: 767 */       this.text.setLength(k);
/*  795: 769 */       switch (LA(1))
/*  796:     */       {
/*  797:     */       case '\t': 
/*  798:     */       case '\n': 
/*  799:     */       case '\r': 
/*  800:     */       case ' ': 
/*  801: 772 */         k = this.text.length();
/*  802: 773 */         mWS(false);
/*  803: 774 */         this.text.setLength(k);
/*  804: 775 */         break;
/*  805:     */       case '"': 
/*  806:     */       case '#': 
/*  807:     */       case '(': 
/*  808:     */       case 'A': 
/*  809:     */       case 'B': 
/*  810:     */       case 'C': 
/*  811:     */       case 'D': 
/*  812:     */       case 'E': 
/*  813:     */       case 'F': 
/*  814:     */       case 'G': 
/*  815:     */       case 'H': 
/*  816:     */       case 'I': 
/*  817:     */       case 'J': 
/*  818:     */       case 'K': 
/*  819:     */       case 'L': 
/*  820:     */       case 'M': 
/*  821:     */       case 'N': 
/*  822:     */       case 'O': 
/*  823:     */       case 'P': 
/*  824:     */       case 'Q': 
/*  825:     */       case 'R': 
/*  826:     */       case 'S': 
/*  827:     */       case 'T': 
/*  828:     */       case 'U': 
/*  829:     */       case 'V': 
/*  830:     */       case 'W': 
/*  831:     */       case 'X': 
/*  832:     */       case 'Y': 
/*  833:     */       case 'Z': 
/*  834:     */       case '[': 
/*  835:     */       case '_': 
/*  836:     */       case 'a': 
/*  837:     */       case 'b': 
/*  838:     */       case 'c': 
/*  839:     */       case 'd': 
/*  840:     */       case 'e': 
/*  841:     */       case 'f': 
/*  842:     */       case 'g': 
/*  843:     */       case 'h': 
/*  844:     */       case 'i': 
/*  845:     */       case 'j': 
/*  846:     */       case 'k': 
/*  847:     */       case 'l': 
/*  848:     */       case 'm': 
/*  849:     */       case 'n': 
/*  850:     */       case 'o': 
/*  851:     */       case 'p': 
/*  852:     */       case 'q': 
/*  853:     */       case 'r': 
/*  854:     */       case 's': 
/*  855:     */       case 't': 
/*  856:     */       case 'u': 
/*  857:     */       case 'v': 
/*  858:     */       case 'w': 
/*  859:     */       case 'x': 
/*  860:     */       case 'y': 
/*  861:     */       case 'z': 
/*  862:     */         break;
/*  863:     */       case '\013': 
/*  864:     */       case '\f': 
/*  865:     */       case '\016': 
/*  866:     */       case '\017': 
/*  867:     */       case '\020': 
/*  868:     */       case '\021': 
/*  869:     */       case '\022': 
/*  870:     */       case '\023': 
/*  871:     */       case '\024': 
/*  872:     */       case '\025': 
/*  873:     */       case '\026': 
/*  874:     */       case '\027': 
/*  875:     */       case '\030': 
/*  876:     */       case '\031': 
/*  877:     */       case '\032': 
/*  878:     */       case '\033': 
/*  879:     */       case '\034': 
/*  880:     */       case '\035': 
/*  881:     */       case '\036': 
/*  882:     */       case '\037': 
/*  883:     */       case '!': 
/*  884:     */       case '$': 
/*  885:     */       case '%': 
/*  886:     */       case '&': 
/*  887:     */       case '\'': 
/*  888:     */       case ')': 
/*  889:     */       case '*': 
/*  890:     */       case '+': 
/*  891:     */       case ',': 
/*  892:     */       case '-': 
/*  893:     */       case '.': 
/*  894:     */       case '/': 
/*  895:     */       case '0': 
/*  896:     */       case '1': 
/*  897:     */       case '2': 
/*  898:     */       case '3': 
/*  899:     */       case '4': 
/*  900:     */       case '5': 
/*  901:     */       case '6': 
/*  902:     */       case '7': 
/*  903:     */       case '8': 
/*  904:     */       case '9': 
/*  905:     */       case ':': 
/*  906:     */       case ';': 
/*  907:     */       case '<': 
/*  908:     */       case '=': 
/*  909:     */       case '>': 
/*  910:     */       case '?': 
/*  911:     */       case '@': 
/*  912:     */       case '\\': 
/*  913:     */       case ']': 
/*  914:     */       case '^': 
/*  915:     */       case '`': 
/*  916:     */       default: 
/*  917: 797 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  918:     */       }
/*  919: 801 */       k = this.text.length();
/*  920: 802 */       mTREE_ELEMENT(true);
/*  921: 803 */       this.text.setLength(k);
/*  922: 804 */       localToken3 = this._returnToken;
/*  923: 805 */       localVector.appendElement(localToken3.getText());
/*  924: 807 */       switch (LA(1))
/*  925:     */       {
/*  926:     */       case '\t': 
/*  927:     */       case '\n': 
/*  928:     */       case '\r': 
/*  929:     */       case ' ': 
/*  930: 810 */         k = this.text.length();
/*  931: 811 */         mWS(false);
/*  932: 812 */         this.text.setLength(k);
/*  933: 813 */         break;
/*  934:     */       case ')': 
/*  935:     */       case ',': 
/*  936:     */         break;
/*  937:     */       default: 
/*  938: 821 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  939:     */       }
/*  940:     */     }
/*  941: 832 */     this.text.setLength(j);this.text.append(this.generator.getASTCreateString(localVector));
/*  942: 833 */     k = this.text.length();
/*  943: 834 */     match(')');
/*  944: 835 */     this.text.setLength(k);
/*  945: 836 */     if ((paramBoolean) && (localToken1 == null) && (i != -1))
/*  946:     */     {
/*  947: 837 */       localToken1 = makeToken(i);
/*  948: 838 */       localToken1.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*  949:     */     }
/*  950: 840 */     this._returnToken = localToken1;
/*  951:     */   }
/*  952:     */   
/*  953:     */   protected final void mID(boolean paramBoolean)
/*  954:     */     throws RecognitionException, CharStreamException, TokenStreamException
/*  955:     */   {
/*  956: 844 */     Token localToken = null;int j = this.text.length();
/*  957: 845 */     int i = 17;
/*  958: 849 */     switch (LA(1))
/*  959:     */     {
/*  960:     */     case 'a': 
/*  961:     */     case 'b': 
/*  962:     */     case 'c': 
/*  963:     */     case 'd': 
/*  964:     */     case 'e': 
/*  965:     */     case 'f': 
/*  966:     */     case 'g': 
/*  967:     */     case 'h': 
/*  968:     */     case 'i': 
/*  969:     */     case 'j': 
/*  970:     */     case 'k': 
/*  971:     */     case 'l': 
/*  972:     */     case 'm': 
/*  973:     */     case 'n': 
/*  974:     */     case 'o': 
/*  975:     */     case 'p': 
/*  976:     */     case 'q': 
/*  977:     */     case 'r': 
/*  978:     */     case 's': 
/*  979:     */     case 't': 
/*  980:     */     case 'u': 
/*  981:     */     case 'v': 
/*  982:     */     case 'w': 
/*  983:     */     case 'x': 
/*  984:     */     case 'y': 
/*  985:     */     case 'z': 
/*  986: 858 */       matchRange('a', 'z');
/*  987: 859 */       break;
/*  988:     */     case 'A': 
/*  989:     */     case 'B': 
/*  990:     */     case 'C': 
/*  991:     */     case 'D': 
/*  992:     */     case 'E': 
/*  993:     */     case 'F': 
/*  994:     */     case 'G': 
/*  995:     */     case 'H': 
/*  996:     */     case 'I': 
/*  997:     */     case 'J': 
/*  998:     */     case 'K': 
/*  999:     */     case 'L': 
/* 1000:     */     case 'M': 
/* 1001:     */     case 'N': 
/* 1002:     */     case 'O': 
/* 1003:     */     case 'P': 
/* 1004:     */     case 'Q': 
/* 1005:     */     case 'R': 
/* 1006:     */     case 'S': 
/* 1007:     */     case 'T': 
/* 1008:     */     case 'U': 
/* 1009:     */     case 'V': 
/* 1010:     */     case 'W': 
/* 1011:     */     case 'X': 
/* 1012:     */     case 'Y': 
/* 1013:     */     case 'Z': 
/* 1014: 869 */       matchRange('A', 'Z');
/* 1015: 870 */       break;
/* 1016:     */     case '_': 
/* 1017: 874 */       match('_');
/* 1018: 875 */       break;
/* 1019:     */     case '[': 
/* 1020:     */     case '\\': 
/* 1021:     */     case ']': 
/* 1022:     */     case '^': 
/* 1023:     */     case '`': 
/* 1024:     */     default: 
/* 1025: 879 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1026:     */     }
/* 1027: 886 */     while (_tokenSet_9.member(LA(1))) {
/* 1028: 888 */       switch (LA(1))
/* 1029:     */       {
/* 1030:     */       case 'a': 
/* 1031:     */       case 'b': 
/* 1032:     */       case 'c': 
/* 1033:     */       case 'd': 
/* 1034:     */       case 'e': 
/* 1035:     */       case 'f': 
/* 1036:     */       case 'g': 
/* 1037:     */       case 'h': 
/* 1038:     */       case 'i': 
/* 1039:     */       case 'j': 
/* 1040:     */       case 'k': 
/* 1041:     */       case 'l': 
/* 1042:     */       case 'm': 
/* 1043:     */       case 'n': 
/* 1044:     */       case 'o': 
/* 1045:     */       case 'p': 
/* 1046:     */       case 'q': 
/* 1047:     */       case 'r': 
/* 1048:     */       case 's': 
/* 1049:     */       case 't': 
/* 1050:     */       case 'u': 
/* 1051:     */       case 'v': 
/* 1052:     */       case 'w': 
/* 1053:     */       case 'x': 
/* 1054:     */       case 'y': 
/* 1055:     */       case 'z': 
/* 1056: 897 */         matchRange('a', 'z');
/* 1057: 898 */         break;
/* 1058:     */       case 'A': 
/* 1059:     */       case 'B': 
/* 1060:     */       case 'C': 
/* 1061:     */       case 'D': 
/* 1062:     */       case 'E': 
/* 1063:     */       case 'F': 
/* 1064:     */       case 'G': 
/* 1065:     */       case 'H': 
/* 1066:     */       case 'I': 
/* 1067:     */       case 'J': 
/* 1068:     */       case 'K': 
/* 1069:     */       case 'L': 
/* 1070:     */       case 'M': 
/* 1071:     */       case 'N': 
/* 1072:     */       case 'O': 
/* 1073:     */       case 'P': 
/* 1074:     */       case 'Q': 
/* 1075:     */       case 'R': 
/* 1076:     */       case 'S': 
/* 1077:     */       case 'T': 
/* 1078:     */       case 'U': 
/* 1079:     */       case 'V': 
/* 1080:     */       case 'W': 
/* 1081:     */       case 'X': 
/* 1082:     */       case 'Y': 
/* 1083:     */       case 'Z': 
/* 1084: 908 */         matchRange('A', 'Z');
/* 1085: 909 */         break;
/* 1086:     */       case '0': 
/* 1087:     */       case '1': 
/* 1088:     */       case '2': 
/* 1089:     */       case '3': 
/* 1090:     */       case '4': 
/* 1091:     */       case '5': 
/* 1092:     */       case '6': 
/* 1093:     */       case '7': 
/* 1094:     */       case '8': 
/* 1095:     */       case '9': 
/* 1096: 915 */         matchRange('0', '9');
/* 1097: 916 */         break;
/* 1098:     */       case '_': 
/* 1099: 920 */         match('_');
/* 1100: 921 */         break;
/* 1101:     */       case ':': 
/* 1102:     */       case ';': 
/* 1103:     */       case '<': 
/* 1104:     */       case '=': 
/* 1105:     */       case '>': 
/* 1106:     */       case '?': 
/* 1107:     */       case '@': 
/* 1108:     */       case '[': 
/* 1109:     */       case '\\': 
/* 1110:     */       case ']': 
/* 1111:     */       case '^': 
/* 1112:     */       case '`': 
/* 1113:     */       default: 
/* 1114: 925 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1115:     */       }
/* 1116:     */     }
/* 1117: 936 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 1118:     */     {
/* 1119: 937 */       localToken = makeToken(i);
/* 1120: 938 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 1121:     */     }
/* 1122: 940 */     this._returnToken = localToken;
/* 1123:     */   }
/* 1124:     */   
/* 1125:     */   protected final void mWS(boolean paramBoolean)
/* 1126:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 1127:     */   {
/* 1128: 944 */     Token localToken = null;int j = this.text.length();
/* 1129: 945 */     int i = 29;
/* 1130:     */     
/* 1131:     */ 
/* 1132:     */ 
/* 1133: 949 */     int k = 0;
/* 1134:     */     for (;;)
/* 1135:     */     {
/* 1136: 952 */       if ((LA(1) == '\r') && (LA(2) == '\n'))
/* 1137:     */       {
/* 1138: 953 */         match('\r');
/* 1139: 954 */         match('\n');
/* 1140: 955 */         newline();
/* 1141:     */       }
/* 1142: 957 */       else if (LA(1) == ' ')
/* 1143:     */       {
/* 1144: 958 */         match(' ');
/* 1145:     */       }
/* 1146: 960 */       else if (LA(1) == '\t')
/* 1147:     */       {
/* 1148: 961 */         match('\t');
/* 1149:     */       }
/* 1150: 963 */       else if (LA(1) == '\r')
/* 1151:     */       {
/* 1152: 964 */         match('\r');
/* 1153: 965 */         newline();
/* 1154:     */       }
/* 1155: 967 */       else if (LA(1) == '\n')
/* 1156:     */       {
/* 1157: 968 */         match('\n');
/* 1158: 969 */         newline();
/* 1159:     */       }
/* 1160:     */       else
/* 1161:     */       {
/* 1162: 972 */         if (k >= 1) {
/* 1163:     */           break;
/* 1164:     */         }
/* 1165: 972 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1166:     */       }
/* 1167: 975 */       k++;
/* 1168:     */     }
/* 1169: 978 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 1170:     */     {
/* 1171: 979 */       localToken = makeToken(i);
/* 1172: 980 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 1173:     */     }
/* 1174: 982 */     this._returnToken = localToken;
/* 1175:     */   }
/* 1176:     */   
/* 1177:     */   protected final void mVAR_ASSIGN(boolean paramBoolean)
/* 1178:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 1179:     */   {
/* 1180: 986 */     Token localToken = null;int j = this.text.length();
/* 1181: 987 */     int i = 18;
/* 1182:     */     
/* 1183:     */ 
/* 1184: 990 */     match('=');
/* 1185: 994 */     if ((LA(1) != '=') && (this.transInfo != null) && (this.transInfo.refRuleRoot != null)) {
/* 1186: 995 */       this.transInfo.assignToRoot = true;
/* 1187:     */     }
/* 1188: 998 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 1189:     */     {
/* 1190: 999 */       localToken = makeToken(i);
/* 1191:1000 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 1192:     */     }
/* 1193:1002 */     this._returnToken = localToken;
/* 1194:     */   }
/* 1195:     */   
/* 1196:     */   protected final void mAST_CONSTRUCTOR(boolean paramBoolean)
/* 1197:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 1198:     */   {
/* 1199:1006 */     Token localToken1 = null;int j = this.text.length();
/* 1200:1007 */     int i = 10;
/* 1201:     */     
/* 1202:1009 */     Token localToken2 = null;
/* 1203:1010 */     Token localToken3 = null;
/* 1204:1011 */     Token localToken4 = null;
/* 1205:     */     
/* 1206:1013 */     int k = this.text.length();
/* 1207:1014 */     match('[');
/* 1208:1015 */     this.text.setLength(k);
/* 1209:1017 */     switch (LA(1))
/* 1210:     */     {
/* 1211:     */     case '\t': 
/* 1212:     */     case '\n': 
/* 1213:     */     case '\r': 
/* 1214:     */     case ' ': 
/* 1215:1020 */       k = this.text.length();
/* 1216:1021 */       mWS(false);
/* 1217:1022 */       this.text.setLength(k);
/* 1218:1023 */       break;
/* 1219:     */     case '"': 
/* 1220:     */     case '#': 
/* 1221:     */     case '(': 
/* 1222:     */     case '0': 
/* 1223:     */     case '1': 
/* 1224:     */     case '2': 
/* 1225:     */     case '3': 
/* 1226:     */     case '4': 
/* 1227:     */     case '5': 
/* 1228:     */     case '6': 
/* 1229:     */     case '7': 
/* 1230:     */     case '8': 
/* 1231:     */     case '9': 
/* 1232:     */     case 'A': 
/* 1233:     */     case 'B': 
/* 1234:     */     case 'C': 
/* 1235:     */     case 'D': 
/* 1236:     */     case 'E': 
/* 1237:     */     case 'F': 
/* 1238:     */     case 'G': 
/* 1239:     */     case 'H': 
/* 1240:     */     case 'I': 
/* 1241:     */     case 'J': 
/* 1242:     */     case 'K': 
/* 1243:     */     case 'L': 
/* 1244:     */     case 'M': 
/* 1245:     */     case 'N': 
/* 1246:     */     case 'O': 
/* 1247:     */     case 'P': 
/* 1248:     */     case 'Q': 
/* 1249:     */     case 'R': 
/* 1250:     */     case 'S': 
/* 1251:     */     case 'T': 
/* 1252:     */     case 'U': 
/* 1253:     */     case 'V': 
/* 1254:     */     case 'W': 
/* 1255:     */     case 'X': 
/* 1256:     */     case 'Y': 
/* 1257:     */     case 'Z': 
/* 1258:     */     case '[': 
/* 1259:     */     case '_': 
/* 1260:     */     case 'a': 
/* 1261:     */     case 'b': 
/* 1262:     */     case 'c': 
/* 1263:     */     case 'd': 
/* 1264:     */     case 'e': 
/* 1265:     */     case 'f': 
/* 1266:     */     case 'g': 
/* 1267:     */     case 'h': 
/* 1268:     */     case 'i': 
/* 1269:     */     case 'j': 
/* 1270:     */     case 'k': 
/* 1271:     */     case 'l': 
/* 1272:     */     case 'm': 
/* 1273:     */     case 'n': 
/* 1274:     */     case 'o': 
/* 1275:     */     case 'p': 
/* 1276:     */     case 'q': 
/* 1277:     */     case 'r': 
/* 1278:     */     case 's': 
/* 1279:     */     case 't': 
/* 1280:     */     case 'u': 
/* 1281:     */     case 'v': 
/* 1282:     */     case 'w': 
/* 1283:     */     case 'x': 
/* 1284:     */     case 'y': 
/* 1285:     */     case 'z': 
/* 1286:     */       break;
/* 1287:     */     case '\013': 
/* 1288:     */     case '\f': 
/* 1289:     */     case '\016': 
/* 1290:     */     case '\017': 
/* 1291:     */     case '\020': 
/* 1292:     */     case '\021': 
/* 1293:     */     case '\022': 
/* 1294:     */     case '\023': 
/* 1295:     */     case '\024': 
/* 1296:     */     case '\025': 
/* 1297:     */     case '\026': 
/* 1298:     */     case '\027': 
/* 1299:     */     case '\030': 
/* 1300:     */     case '\031': 
/* 1301:     */     case '\032': 
/* 1302:     */     case '\033': 
/* 1303:     */     case '\034': 
/* 1304:     */     case '\035': 
/* 1305:     */     case '\036': 
/* 1306:     */     case '\037': 
/* 1307:     */     case '!': 
/* 1308:     */     case '$': 
/* 1309:     */     case '%': 
/* 1310:     */     case '&': 
/* 1311:     */     case '\'': 
/* 1312:     */     case ')': 
/* 1313:     */     case '*': 
/* 1314:     */     case '+': 
/* 1315:     */     case ',': 
/* 1316:     */     case '-': 
/* 1317:     */     case '.': 
/* 1318:     */     case '/': 
/* 1319:     */     case ':': 
/* 1320:     */     case ';': 
/* 1321:     */     case '<': 
/* 1322:     */     case '=': 
/* 1323:     */     case '>': 
/* 1324:     */     case '?': 
/* 1325:     */     case '@': 
/* 1326:     */     case '\\': 
/* 1327:     */     case ']': 
/* 1328:     */     case '^': 
/* 1329:     */     case '`': 
/* 1330:     */     default: 
/* 1331:1047 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1332:     */     }
/* 1333:1051 */     k = this.text.length();
/* 1334:1052 */     mAST_CTOR_ELEMENT(true);
/* 1335:1053 */     this.text.setLength(k);
/* 1336:1054 */     localToken2 = this._returnToken;
/* 1337:1056 */     switch (LA(1))
/* 1338:     */     {
/* 1339:     */     case '\t': 
/* 1340:     */     case '\n': 
/* 1341:     */     case '\r': 
/* 1342:     */     case ' ': 
/* 1343:1059 */       k = this.text.length();
/* 1344:1060 */       mWS(false);
/* 1345:1061 */       this.text.setLength(k);
/* 1346:1062 */       break;
/* 1347:     */     case ',': 
/* 1348:     */     case ']': 
/* 1349:     */       break;
/* 1350:     */     default: 
/* 1351:1070 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1352:     */     }
/* 1353:1075 */     if ((LA(1) == ',') && (_tokenSet_10.member(LA(2))) && (LA(3) >= '\003') && (LA(3) <= 'ÿ'))
/* 1354:     */     {
/* 1355:1076 */       k = this.text.length();
/* 1356:1077 */       match(',');
/* 1357:1078 */       this.text.setLength(k);
/* 1358:1080 */       switch (LA(1))
/* 1359:     */       {
/* 1360:     */       case '\t': 
/* 1361:     */       case '\n': 
/* 1362:     */       case '\r': 
/* 1363:     */       case ' ': 
/* 1364:1083 */         k = this.text.length();
/* 1365:1084 */         mWS(false);
/* 1366:1085 */         this.text.setLength(k);
/* 1367:1086 */         break;
/* 1368:     */       case '"': 
/* 1369:     */       case '#': 
/* 1370:     */       case '(': 
/* 1371:     */       case '0': 
/* 1372:     */       case '1': 
/* 1373:     */       case '2': 
/* 1374:     */       case '3': 
/* 1375:     */       case '4': 
/* 1376:     */       case '5': 
/* 1377:     */       case '6': 
/* 1378:     */       case '7': 
/* 1379:     */       case '8': 
/* 1380:     */       case '9': 
/* 1381:     */       case 'A': 
/* 1382:     */       case 'B': 
/* 1383:     */       case 'C': 
/* 1384:     */       case 'D': 
/* 1385:     */       case 'E': 
/* 1386:     */       case 'F': 
/* 1387:     */       case 'G': 
/* 1388:     */       case 'H': 
/* 1389:     */       case 'I': 
/* 1390:     */       case 'J': 
/* 1391:     */       case 'K': 
/* 1392:     */       case 'L': 
/* 1393:     */       case 'M': 
/* 1394:     */       case 'N': 
/* 1395:     */       case 'O': 
/* 1396:     */       case 'P': 
/* 1397:     */       case 'Q': 
/* 1398:     */       case 'R': 
/* 1399:     */       case 'S': 
/* 1400:     */       case 'T': 
/* 1401:     */       case 'U': 
/* 1402:     */       case 'V': 
/* 1403:     */       case 'W': 
/* 1404:     */       case 'X': 
/* 1405:     */       case 'Y': 
/* 1406:     */       case 'Z': 
/* 1407:     */       case '[': 
/* 1408:     */       case '_': 
/* 1409:     */       case 'a': 
/* 1410:     */       case 'b': 
/* 1411:     */       case 'c': 
/* 1412:     */       case 'd': 
/* 1413:     */       case 'e': 
/* 1414:     */       case 'f': 
/* 1415:     */       case 'g': 
/* 1416:     */       case 'h': 
/* 1417:     */       case 'i': 
/* 1418:     */       case 'j': 
/* 1419:     */       case 'k': 
/* 1420:     */       case 'l': 
/* 1421:     */       case 'm': 
/* 1422:     */       case 'n': 
/* 1423:     */       case 'o': 
/* 1424:     */       case 'p': 
/* 1425:     */       case 'q': 
/* 1426:     */       case 'r': 
/* 1427:     */       case 's': 
/* 1428:     */       case 't': 
/* 1429:     */       case 'u': 
/* 1430:     */       case 'v': 
/* 1431:     */       case 'w': 
/* 1432:     */       case 'x': 
/* 1433:     */       case 'y': 
/* 1434:     */       case 'z': 
/* 1435:     */         break;
/* 1436:     */       case '\013': 
/* 1437:     */       case '\f': 
/* 1438:     */       case '\016': 
/* 1439:     */       case '\017': 
/* 1440:     */       case '\020': 
/* 1441:     */       case '\021': 
/* 1442:     */       case '\022': 
/* 1443:     */       case '\023': 
/* 1444:     */       case '\024': 
/* 1445:     */       case '\025': 
/* 1446:     */       case '\026': 
/* 1447:     */       case '\027': 
/* 1448:     */       case '\030': 
/* 1449:     */       case '\031': 
/* 1450:     */       case '\032': 
/* 1451:     */       case '\033': 
/* 1452:     */       case '\034': 
/* 1453:     */       case '\035': 
/* 1454:     */       case '\036': 
/* 1455:     */       case '\037': 
/* 1456:     */       case '!': 
/* 1457:     */       case '$': 
/* 1458:     */       case '%': 
/* 1459:     */       case '&': 
/* 1460:     */       case '\'': 
/* 1461:     */       case ')': 
/* 1462:     */       case '*': 
/* 1463:     */       case '+': 
/* 1464:     */       case ',': 
/* 1465:     */       case '-': 
/* 1466:     */       case '.': 
/* 1467:     */       case '/': 
/* 1468:     */       case ':': 
/* 1469:     */       case ';': 
/* 1470:     */       case '<': 
/* 1471:     */       case '=': 
/* 1472:     */       case '>': 
/* 1473:     */       case '?': 
/* 1474:     */       case '@': 
/* 1475:     */       case '\\': 
/* 1476:     */       case ']': 
/* 1477:     */       case '^': 
/* 1478:     */       case '`': 
/* 1479:     */       default: 
/* 1480:1110 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1481:     */       }
/* 1482:1114 */       k = this.text.length();
/* 1483:1115 */       mAST_CTOR_ELEMENT(true);
/* 1484:1116 */       this.text.setLength(k);
/* 1485:1117 */       localToken3 = this._returnToken;
/* 1486:     */     }
/* 1487:1119 */     switch (LA(1))
/* 1488:     */     {
/* 1489:     */     case '\t': 
/* 1490:     */     case '\n': 
/* 1491:     */     case '\r': 
/* 1492:     */     case ' ': 
/* 1493:1122 */       k = this.text.length();
/* 1494:1123 */       mWS(false);
/* 1495:1124 */       this.text.setLength(k);
/* 1496:1125 */       break;
/* 1497:     */     case ',': 
/* 1498:     */     case ']': 
/* 1499:     */       break;
/* 1500:     */     default: 
/* 1501:1133 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1502:1138 */       if ((LA(1) != ',') && (LA(1) != ']')) {
/* 1503:1141 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1504:     */       }
/* 1505:     */       break;
/* 1506:     */     }
/* 1507:1146 */     switch (LA(1))
/* 1508:     */     {
/* 1509:     */     case ',': 
/* 1510:1149 */       k = this.text.length();
/* 1511:1150 */       match(',');
/* 1512:1151 */       this.text.setLength(k);
/* 1513:1153 */       switch (LA(1))
/* 1514:     */       {
/* 1515:     */       case '\t': 
/* 1516:     */       case '\n': 
/* 1517:     */       case '\r': 
/* 1518:     */       case ' ': 
/* 1519:1156 */         k = this.text.length();
/* 1520:1157 */         mWS(false);
/* 1521:1158 */         this.text.setLength(k);
/* 1522:1159 */         break;
/* 1523:     */       case '"': 
/* 1524:     */       case '#': 
/* 1525:     */       case '(': 
/* 1526:     */       case '0': 
/* 1527:     */       case '1': 
/* 1528:     */       case '2': 
/* 1529:     */       case '3': 
/* 1530:     */       case '4': 
/* 1531:     */       case '5': 
/* 1532:     */       case '6': 
/* 1533:     */       case '7': 
/* 1534:     */       case '8': 
/* 1535:     */       case '9': 
/* 1536:     */       case 'A': 
/* 1537:     */       case 'B': 
/* 1538:     */       case 'C': 
/* 1539:     */       case 'D': 
/* 1540:     */       case 'E': 
/* 1541:     */       case 'F': 
/* 1542:     */       case 'G': 
/* 1543:     */       case 'H': 
/* 1544:     */       case 'I': 
/* 1545:     */       case 'J': 
/* 1546:     */       case 'K': 
/* 1547:     */       case 'L': 
/* 1548:     */       case 'M': 
/* 1549:     */       case 'N': 
/* 1550:     */       case 'O': 
/* 1551:     */       case 'P': 
/* 1552:     */       case 'Q': 
/* 1553:     */       case 'R': 
/* 1554:     */       case 'S': 
/* 1555:     */       case 'T': 
/* 1556:     */       case 'U': 
/* 1557:     */       case 'V': 
/* 1558:     */       case 'W': 
/* 1559:     */       case 'X': 
/* 1560:     */       case 'Y': 
/* 1561:     */       case 'Z': 
/* 1562:     */       case '[': 
/* 1563:     */       case '_': 
/* 1564:     */       case 'a': 
/* 1565:     */       case 'b': 
/* 1566:     */       case 'c': 
/* 1567:     */       case 'd': 
/* 1568:     */       case 'e': 
/* 1569:     */       case 'f': 
/* 1570:     */       case 'g': 
/* 1571:     */       case 'h': 
/* 1572:     */       case 'i': 
/* 1573:     */       case 'j': 
/* 1574:     */       case 'k': 
/* 1575:     */       case 'l': 
/* 1576:     */       case 'm': 
/* 1577:     */       case 'n': 
/* 1578:     */       case 'o': 
/* 1579:     */       case 'p': 
/* 1580:     */       case 'q': 
/* 1581:     */       case 'r': 
/* 1582:     */       case 's': 
/* 1583:     */       case 't': 
/* 1584:     */       case 'u': 
/* 1585:     */       case 'v': 
/* 1586:     */       case 'w': 
/* 1587:     */       case 'x': 
/* 1588:     */       case 'y': 
/* 1589:     */       case 'z': 
/* 1590:     */         break;
/* 1591:     */       case '\013': 
/* 1592:     */       case '\f': 
/* 1593:     */       case '\016': 
/* 1594:     */       case '\017': 
/* 1595:     */       case '\020': 
/* 1596:     */       case '\021': 
/* 1597:     */       case '\022': 
/* 1598:     */       case '\023': 
/* 1599:     */       case '\024': 
/* 1600:     */       case '\025': 
/* 1601:     */       case '\026': 
/* 1602:     */       case '\027': 
/* 1603:     */       case '\030': 
/* 1604:     */       case '\031': 
/* 1605:     */       case '\032': 
/* 1606:     */       case '\033': 
/* 1607:     */       case '\034': 
/* 1608:     */       case '\035': 
/* 1609:     */       case '\036': 
/* 1610:     */       case '\037': 
/* 1611:     */       case '!': 
/* 1612:     */       case '$': 
/* 1613:     */       case '%': 
/* 1614:     */       case '&': 
/* 1615:     */       case '\'': 
/* 1616:     */       case ')': 
/* 1617:     */       case '*': 
/* 1618:     */       case '+': 
/* 1619:     */       case ',': 
/* 1620:     */       case '-': 
/* 1621:     */       case '.': 
/* 1622:     */       case '/': 
/* 1623:     */       case ':': 
/* 1624:     */       case ';': 
/* 1625:     */       case '<': 
/* 1626:     */       case '=': 
/* 1627:     */       case '>': 
/* 1628:     */       case '?': 
/* 1629:     */       case '@': 
/* 1630:     */       case '\\': 
/* 1631:     */       case ']': 
/* 1632:     */       case '^': 
/* 1633:     */       case '`': 
/* 1634:     */       default: 
/* 1635:1183 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1636:     */       }
/* 1637:1187 */       k = this.text.length();
/* 1638:1188 */       mAST_CTOR_ELEMENT(true);
/* 1639:1189 */       this.text.setLength(k);
/* 1640:1190 */       localToken4 = this._returnToken;
/* 1641:1192 */       switch (LA(1))
/* 1642:     */       {
/* 1643:     */       case '\t': 
/* 1644:     */       case '\n': 
/* 1645:     */       case '\r': 
/* 1646:     */       case ' ': 
/* 1647:1195 */         k = this.text.length();
/* 1648:1196 */         mWS(false);
/* 1649:1197 */         this.text.setLength(k);
/* 1650:1198 */         break;
/* 1651:     */       case ']': 
/* 1652:     */         break;
/* 1653:     */       default: 
/* 1654:1206 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1655:     */       }
/* 1656:     */       break;
/* 1657:     */     case ']': 
/* 1658:     */       break;
/* 1659:     */     default: 
/* 1660:1218 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1661:     */     }
/* 1662:1222 */     k = this.text.length();
/* 1663:1223 */     match(']');
/* 1664:1224 */     this.text.setLength(k);
/* 1665:     */     
/* 1666:1226 */     String str = localToken2.getText();
/* 1667:1227 */     if (localToken3 != null) {
/* 1668:1228 */       str = str + "," + localToken3.getText();
/* 1669:     */     }
/* 1670:1230 */     if (localToken4 != null) {
/* 1671:1231 */       str = str + "," + localToken4.getText();
/* 1672:     */     }
/* 1673:1233 */     this.text.setLength(j);this.text.append(this.generator.getASTCreateString(null, str));
/* 1674:1235 */     if ((paramBoolean) && (localToken1 == null) && (i != -1))
/* 1675:     */     {
/* 1676:1236 */       localToken1 = makeToken(i);
/* 1677:1237 */       localToken1.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 1678:     */     }
/* 1679:1239 */     this._returnToken = localToken1;
/* 1680:     */   }
/* 1681:     */   
/* 1682:     */   protected final void mTEXT_ARG(boolean paramBoolean)
/* 1683:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 1684:     */   {
/* 1685:1243 */     Token localToken = null;int j = this.text.length();
/* 1686:1244 */     int i = 13;
/* 1687:1248 */     switch (LA(1))
/* 1688:     */     {
/* 1689:     */     case '\t': 
/* 1690:     */     case '\n': 
/* 1691:     */     case '\r': 
/* 1692:     */     case ' ': 
/* 1693:1251 */       mWS(false);
/* 1694:1252 */       break;
/* 1695:     */     case '"': 
/* 1696:     */     case '$': 
/* 1697:     */     case '\'': 
/* 1698:     */     case '+': 
/* 1699:     */     case '0': 
/* 1700:     */     case '1': 
/* 1701:     */     case '2': 
/* 1702:     */     case '3': 
/* 1703:     */     case '4': 
/* 1704:     */     case '5': 
/* 1705:     */     case '6': 
/* 1706:     */     case '7': 
/* 1707:     */     case '8': 
/* 1708:     */     case '9': 
/* 1709:     */     case 'A': 
/* 1710:     */     case 'B': 
/* 1711:     */     case 'C': 
/* 1712:     */     case 'D': 
/* 1713:     */     case 'E': 
/* 1714:     */     case 'F': 
/* 1715:     */     case 'G': 
/* 1716:     */     case 'H': 
/* 1717:     */     case 'I': 
/* 1718:     */     case 'J': 
/* 1719:     */     case 'K': 
/* 1720:     */     case 'L': 
/* 1721:     */     case 'M': 
/* 1722:     */     case 'N': 
/* 1723:     */     case 'O': 
/* 1724:     */     case 'P': 
/* 1725:     */     case 'Q': 
/* 1726:     */     case 'R': 
/* 1727:     */     case 'S': 
/* 1728:     */     case 'T': 
/* 1729:     */     case 'U': 
/* 1730:     */     case 'V': 
/* 1731:     */     case 'W': 
/* 1732:     */     case 'X': 
/* 1733:     */     case 'Y': 
/* 1734:     */     case 'Z': 
/* 1735:     */     case '_': 
/* 1736:     */     case 'a': 
/* 1737:     */     case 'b': 
/* 1738:     */     case 'c': 
/* 1739:     */     case 'd': 
/* 1740:     */     case 'e': 
/* 1741:     */     case 'f': 
/* 1742:     */     case 'g': 
/* 1743:     */     case 'h': 
/* 1744:     */     case 'i': 
/* 1745:     */     case 'j': 
/* 1746:     */     case 'k': 
/* 1747:     */     case 'l': 
/* 1748:     */     case 'm': 
/* 1749:     */     case 'n': 
/* 1750:     */     case 'o': 
/* 1751:     */     case 'p': 
/* 1752:     */     case 'q': 
/* 1753:     */     case 'r': 
/* 1754:     */     case 's': 
/* 1755:     */     case 't': 
/* 1756:     */     case 'u': 
/* 1757:     */     case 'v': 
/* 1758:     */     case 'w': 
/* 1759:     */     case 'x': 
/* 1760:     */     case 'y': 
/* 1761:     */     case 'z': 
/* 1762:     */       break;
/* 1763:     */     case '\013': 
/* 1764:     */     case '\f': 
/* 1765:     */     case '\016': 
/* 1766:     */     case '\017': 
/* 1767:     */     case '\020': 
/* 1768:     */     case '\021': 
/* 1769:     */     case '\022': 
/* 1770:     */     case '\023': 
/* 1771:     */     case '\024': 
/* 1772:     */     case '\025': 
/* 1773:     */     case '\026': 
/* 1774:     */     case '\027': 
/* 1775:     */     case '\030': 
/* 1776:     */     case '\031': 
/* 1777:     */     case '\032': 
/* 1778:     */     case '\033': 
/* 1779:     */     case '\034': 
/* 1780:     */     case '\035': 
/* 1781:     */     case '\036': 
/* 1782:     */     case '\037': 
/* 1783:     */     case '!': 
/* 1784:     */     case '#': 
/* 1785:     */     case '%': 
/* 1786:     */     case '&': 
/* 1787:     */     case '(': 
/* 1788:     */     case ')': 
/* 1789:     */     case '*': 
/* 1790:     */     case ',': 
/* 1791:     */     case '-': 
/* 1792:     */     case '.': 
/* 1793:     */     case '/': 
/* 1794:     */     case ':': 
/* 1795:     */     case ';': 
/* 1796:     */     case '<': 
/* 1797:     */     case '=': 
/* 1798:     */     case '>': 
/* 1799:     */     case '?': 
/* 1800:     */     case '@': 
/* 1801:     */     case '[': 
/* 1802:     */     case '\\': 
/* 1803:     */     case ']': 
/* 1804:     */     case '^': 
/* 1805:     */     case '`': 
/* 1806:     */     default: 
/* 1807:1276 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1808:     */     }
/* 1809:1281 */     int k = 0;
/* 1810:     */     for (;;)
/* 1811:     */     {
/* 1812:1284 */       if ((_tokenSet_11.member(LA(1))) && (LA(2) >= '\003') && (LA(2) <= 'ÿ'))
/* 1813:     */       {
/* 1814:1285 */         mTEXT_ARG_ELEMENT(false);
/* 1815:1287 */         if ((_tokenSet_4.member(LA(1))) && (_tokenSet_12.member(LA(2)))) {
/* 1816:1288 */           mWS(false);
/* 1817:1290 */         } else if (!_tokenSet_12.member(LA(1))) {
/* 1818:1293 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1819:     */         }
/* 1820:     */       }
/* 1821:     */       else
/* 1822:     */       {
/* 1823:1299 */         if (k >= 1) {
/* 1824:     */           break;
/* 1825:     */         }
/* 1826:1299 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1827:     */       }
/* 1828:1302 */       k++;
/* 1829:     */     }
/* 1830:1305 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 1831:     */     {
/* 1832:1306 */       localToken = makeToken(i);
/* 1833:1307 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 1834:     */     }
/* 1835:1309 */     this._returnToken = localToken;
/* 1836:     */   }
/* 1837:     */   
/* 1838:     */   protected final void mTREE_ELEMENT(boolean paramBoolean)
/* 1839:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 1840:     */   {
/* 1841:1313 */     Token localToken1 = null;int j = this.text.length();
/* 1842:1314 */     int i = 9;
/* 1843:     */     
/* 1844:1316 */     Token localToken2 = null;
/* 1845:1319 */     switch (LA(1))
/* 1846:     */     {
/* 1847:     */     case '(': 
/* 1848:1322 */       mTREE(false);
/* 1849:1323 */       break;
/* 1850:     */     case '[': 
/* 1851:1327 */       mAST_CONSTRUCTOR(false);
/* 1852:1328 */       break;
/* 1853:     */     case 'A': 
/* 1854:     */     case 'B': 
/* 1855:     */     case 'C': 
/* 1856:     */     case 'D': 
/* 1857:     */     case 'E': 
/* 1858:     */     case 'F': 
/* 1859:     */     case 'G': 
/* 1860:     */     case 'H': 
/* 1861:     */     case 'I': 
/* 1862:     */     case 'J': 
/* 1863:     */     case 'K': 
/* 1864:     */     case 'L': 
/* 1865:     */     case 'M': 
/* 1866:     */     case 'N': 
/* 1867:     */     case 'O': 
/* 1868:     */     case 'P': 
/* 1869:     */     case 'Q': 
/* 1870:     */     case 'R': 
/* 1871:     */     case 'S': 
/* 1872:     */     case 'T': 
/* 1873:     */     case 'U': 
/* 1874:     */     case 'V': 
/* 1875:     */     case 'W': 
/* 1876:     */     case 'X': 
/* 1877:     */     case 'Y': 
/* 1878:     */     case 'Z': 
/* 1879:     */     case '_': 
/* 1880:     */     case 'a': 
/* 1881:     */     case 'b': 
/* 1882:     */     case 'c': 
/* 1883:     */     case 'd': 
/* 1884:     */     case 'e': 
/* 1885:     */     case 'f': 
/* 1886:     */     case 'g': 
/* 1887:     */     case 'h': 
/* 1888:     */     case 'i': 
/* 1889:     */     case 'j': 
/* 1890:     */     case 'k': 
/* 1891:     */     case 'l': 
/* 1892:     */     case 'm': 
/* 1893:     */     case 'n': 
/* 1894:     */     case 'o': 
/* 1895:     */     case 'p': 
/* 1896:     */     case 'q': 
/* 1897:     */     case 'r': 
/* 1898:     */     case 's': 
/* 1899:     */     case 't': 
/* 1900:     */     case 'u': 
/* 1901:     */     case 'v': 
/* 1902:     */     case 'w': 
/* 1903:     */     case 'x': 
/* 1904:     */     case 'y': 
/* 1905:     */     case 'z': 
/* 1906:1345 */       mID_ELEMENT(false);
/* 1907:1346 */       break;
/* 1908:     */     case '"': 
/* 1909:1350 */       mSTRING(false);
/* 1910:1351 */       break;
/* 1911:     */     case '#': 
/* 1912:     */     case '$': 
/* 1913:     */     case '%': 
/* 1914:     */     case '&': 
/* 1915:     */     case '\'': 
/* 1916:     */     case ')': 
/* 1917:     */     case '*': 
/* 1918:     */     case '+': 
/* 1919:     */     case ',': 
/* 1920:     */     case '-': 
/* 1921:     */     case '.': 
/* 1922:     */     case '/': 
/* 1923:     */     case '0': 
/* 1924:     */     case '1': 
/* 1925:     */     case '2': 
/* 1926:     */     case '3': 
/* 1927:     */     case '4': 
/* 1928:     */     case '5': 
/* 1929:     */     case '6': 
/* 1930:     */     case '7': 
/* 1931:     */     case '8': 
/* 1932:     */     case '9': 
/* 1933:     */     case ':': 
/* 1934:     */     case ';': 
/* 1935:     */     case '<': 
/* 1936:     */     case '=': 
/* 1937:     */     case '>': 
/* 1938:     */     case '?': 
/* 1939:     */     case '@': 
/* 1940:     */     case '\\': 
/* 1941:     */     case ']': 
/* 1942:     */     case '^': 
/* 1943:     */     case '`': 
/* 1944:     */     default: 
/* 1945:     */       int k;
/* 1946:1354 */       if ((LA(1) == '#') && (LA(2) == '('))
/* 1947:     */       {
/* 1948:1355 */         k = this.text.length();
/* 1949:1356 */         match('#');
/* 1950:1357 */         this.text.setLength(k);
/* 1951:1358 */         mTREE(false);
/* 1952:     */       }
/* 1953:1360 */       else if ((LA(1) == '#') && (LA(2) == '['))
/* 1954:     */       {
/* 1955:1361 */         k = this.text.length();
/* 1956:1362 */         match('#');
/* 1957:1363 */         this.text.setLength(k);
/* 1958:1364 */         mAST_CONSTRUCTOR(false);
/* 1959:     */       }
/* 1960:     */       else
/* 1961:     */       {
/* 1962:     */         String str;
/* 1963:1366 */         if ((LA(1) == '#') && (_tokenSet_3.member(LA(2))))
/* 1964:     */         {
/* 1965:1367 */           k = this.text.length();
/* 1966:1368 */           match('#');
/* 1967:1369 */           this.text.setLength(k);
/* 1968:1370 */           boolean bool = mID_ELEMENT(true);
/* 1969:1371 */           localToken2 = this._returnToken;
/* 1970:1373 */           if (!bool)
/* 1971:     */           {
/* 1972:1375 */             str = this.generator.mapTreeId(localToken2.getText(), null);
/* 1973:1376 */             this.text.setLength(j);this.text.append(str);
/* 1974:     */           }
/* 1975:     */         }
/* 1976:1380 */         else if ((LA(1) == '#') && (LA(2) == '#'))
/* 1977:     */         {
/* 1978:1381 */           match("##");
/* 1979:1382 */           str = this.currentRule.getRuleName() + "_AST";this.text.setLength(j);this.text.append(str);
/* 1980:     */         }
/* 1981:     */         else
/* 1982:     */         {
/* 1983:1385 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 1984:     */         }
/* 1985:     */       }
/* 1986:     */       break;
/* 1987:     */     }
/* 1988:1388 */     if ((paramBoolean) && (localToken1 == null) && (i != -1))
/* 1989:     */     {
/* 1990:1389 */       localToken1 = makeToken(i);
/* 1991:1390 */       localToken1.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 1992:     */     }
/* 1993:1392 */     this._returnToken = localToken1;
/* 1994:     */   }
/* 1995:     */   
/* 1996:     */   protected final boolean mID_ELEMENT(boolean paramBoolean)
/* 1997:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 1998:     */   {
/* 1999:1399 */     boolean bool = false;
/* 2000:1400 */     Token localToken1 = null;int j = this.text.length();
/* 2001:1401 */     int i = 12;
/* 2002:     */     
/* 2003:1403 */     Token localToken2 = null;
/* 2004:     */     
/* 2005:1405 */     mID(true);
/* 2006:1406 */     localToken2 = this._returnToken;
/* 2007:     */     int k;
/* 2008:1408 */     if ((_tokenSet_4.member(LA(1))) && (_tokenSet_13.member(LA(2))))
/* 2009:     */     {
/* 2010:1409 */       k = this.text.length();
/* 2011:1410 */       mWS(false);
/* 2012:1411 */       this.text.setLength(k);
/* 2013:     */     }
/* 2014:1413 */     else if (!_tokenSet_13.member(LA(1)))
/* 2015:     */     {
/* 2016:1416 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2017:     */     }
/* 2018:1421 */     switch (LA(1))
/* 2019:     */     {
/* 2020:     */     case '(': 
/* 2021:1424 */       match('(');
/* 2022:1426 */       if ((_tokenSet_4.member(LA(1))) && (_tokenSet_14.member(LA(2))) && (LA(3) >= '\003') && (LA(3) <= 'ÿ'))
/* 2023:     */       {
/* 2024:1427 */         k = this.text.length();
/* 2025:1428 */         mWS(false);
/* 2026:1429 */         this.text.setLength(k);
/* 2027:     */       }
/* 2028:1431 */       else if ((!_tokenSet_14.member(LA(1))) || (LA(2) < '\003') || (LA(2) > 'ÿ'))
/* 2029:     */       {
/* 2030:1434 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2031:     */       }
/* 2032:1439 */       switch (LA(1))
/* 2033:     */       {
/* 2034:     */       case '"': 
/* 2035:     */       case '#': 
/* 2036:     */       case '\'': 
/* 2037:     */       case '(': 
/* 2038:     */       case '0': 
/* 2039:     */       case '1': 
/* 2040:     */       case '2': 
/* 2041:     */       case '3': 
/* 2042:     */       case '4': 
/* 2043:     */       case '5': 
/* 2044:     */       case '6': 
/* 2045:     */       case '7': 
/* 2046:     */       case '8': 
/* 2047:     */       case '9': 
/* 2048:     */       case 'A': 
/* 2049:     */       case 'B': 
/* 2050:     */       case 'C': 
/* 2051:     */       case 'D': 
/* 2052:     */       case 'E': 
/* 2053:     */       case 'F': 
/* 2054:     */       case 'G': 
/* 2055:     */       case 'H': 
/* 2056:     */       case 'I': 
/* 2057:     */       case 'J': 
/* 2058:     */       case 'K': 
/* 2059:     */       case 'L': 
/* 2060:     */       case 'M': 
/* 2061:     */       case 'N': 
/* 2062:     */       case 'O': 
/* 2063:     */       case 'P': 
/* 2064:     */       case 'Q': 
/* 2065:     */       case 'R': 
/* 2066:     */       case 'S': 
/* 2067:     */       case 'T': 
/* 2068:     */       case 'U': 
/* 2069:     */       case 'V': 
/* 2070:     */       case 'W': 
/* 2071:     */       case 'X': 
/* 2072:     */       case 'Y': 
/* 2073:     */       case 'Z': 
/* 2074:     */       case '[': 
/* 2075:     */       case '_': 
/* 2076:     */       case 'a': 
/* 2077:     */       case 'b': 
/* 2078:     */       case 'c': 
/* 2079:     */       case 'd': 
/* 2080:     */       case 'e': 
/* 2081:     */       case 'f': 
/* 2082:     */       case 'g': 
/* 2083:     */       case 'h': 
/* 2084:     */       case 'i': 
/* 2085:     */       case 'j': 
/* 2086:     */       case 'k': 
/* 2087:     */       case 'l': 
/* 2088:     */       case 'm': 
/* 2089:     */       case 'n': 
/* 2090:     */       case 'o': 
/* 2091:     */       case 'p': 
/* 2092:     */       case 'q': 
/* 2093:     */       case 'r': 
/* 2094:     */       case 's': 
/* 2095:     */       case 't': 
/* 2096:     */       case 'u': 
/* 2097:     */       case 'v': 
/* 2098:     */       case 'w': 
/* 2099:     */       case 'x': 
/* 2100:     */       case 'y': 
/* 2101:     */       case 'z': 
/* 2102:1458 */         mARG(false);
/* 2103:     */       }
/* 2104:1462 */       while (LA(1) == ',')
/* 2105:     */       {
/* 2106:1463 */         match(',');
/* 2107:1465 */         switch (LA(1))
/* 2108:     */         {
/* 2109:     */         case '\t': 
/* 2110:     */         case '\n': 
/* 2111:     */         case '\r': 
/* 2112:     */         case ' ': 
/* 2113:1468 */           k = this.text.length();
/* 2114:1469 */           mWS(false);
/* 2115:1470 */           this.text.setLength(k);
/* 2116:1471 */           break;
/* 2117:     */         case '"': 
/* 2118:     */         case '#': 
/* 2119:     */         case '\'': 
/* 2120:     */         case '(': 
/* 2121:     */         case '0': 
/* 2122:     */         case '1': 
/* 2123:     */         case '2': 
/* 2124:     */         case '3': 
/* 2125:     */         case '4': 
/* 2126:     */         case '5': 
/* 2127:     */         case '6': 
/* 2128:     */         case '7': 
/* 2129:     */         case '8': 
/* 2130:     */         case '9': 
/* 2131:     */         case 'A': 
/* 2132:     */         case 'B': 
/* 2133:     */         case 'C': 
/* 2134:     */         case 'D': 
/* 2135:     */         case 'E': 
/* 2136:     */         case 'F': 
/* 2137:     */         case 'G': 
/* 2138:     */         case 'H': 
/* 2139:     */         case 'I': 
/* 2140:     */         case 'J': 
/* 2141:     */         case 'K': 
/* 2142:     */         case 'L': 
/* 2143:     */         case 'M': 
/* 2144:     */         case 'N': 
/* 2145:     */         case 'O': 
/* 2146:     */         case 'P': 
/* 2147:     */         case 'Q': 
/* 2148:     */         case 'R': 
/* 2149:     */         case 'S': 
/* 2150:     */         case 'T': 
/* 2151:     */         case 'U': 
/* 2152:     */         case 'V': 
/* 2153:     */         case 'W': 
/* 2154:     */         case 'X': 
/* 2155:     */         case 'Y': 
/* 2156:     */         case 'Z': 
/* 2157:     */         case '[': 
/* 2158:     */         case '_': 
/* 2159:     */         case 'a': 
/* 2160:     */         case 'b': 
/* 2161:     */         case 'c': 
/* 2162:     */         case 'd': 
/* 2163:     */         case 'e': 
/* 2164:     */         case 'f': 
/* 2165:     */         case 'g': 
/* 2166:     */         case 'h': 
/* 2167:     */         case 'i': 
/* 2168:     */         case 'j': 
/* 2169:     */         case 'k': 
/* 2170:     */         case 'l': 
/* 2171:     */         case 'm': 
/* 2172:     */         case 'n': 
/* 2173:     */         case 'o': 
/* 2174:     */         case 'p': 
/* 2175:     */         case 'q': 
/* 2176:     */         case 'r': 
/* 2177:     */         case 's': 
/* 2178:     */         case 't': 
/* 2179:     */         case 'u': 
/* 2180:     */         case 'v': 
/* 2181:     */         case 'w': 
/* 2182:     */         case 'x': 
/* 2183:     */         case 'y': 
/* 2184:     */         case 'z': 
/* 2185:     */           break;
/* 2186:     */         case '\013': 
/* 2187:     */         case '\f': 
/* 2188:     */         case '\016': 
/* 2189:     */         case '\017': 
/* 2190:     */         case '\020': 
/* 2191:     */         case '\021': 
/* 2192:     */         case '\022': 
/* 2193:     */         case '\023': 
/* 2194:     */         case '\024': 
/* 2195:     */         case '\025': 
/* 2196:     */         case '\026': 
/* 2197:     */         case '\027': 
/* 2198:     */         case '\030': 
/* 2199:     */         case '\031': 
/* 2200:     */         case '\032': 
/* 2201:     */         case '\033': 
/* 2202:     */         case '\034': 
/* 2203:     */         case '\035': 
/* 2204:     */         case '\036': 
/* 2205:     */         case '\037': 
/* 2206:     */         case '!': 
/* 2207:     */         case '$': 
/* 2208:     */         case '%': 
/* 2209:     */         case '&': 
/* 2210:     */         case ')': 
/* 2211:     */         case '*': 
/* 2212:     */         case '+': 
/* 2213:     */         case ',': 
/* 2214:     */         case '-': 
/* 2215:     */         case '.': 
/* 2216:     */         case '/': 
/* 2217:     */         case ':': 
/* 2218:     */         case ';': 
/* 2219:     */         case '<': 
/* 2220:     */         case '=': 
/* 2221:     */         case '>': 
/* 2222:     */         case '?': 
/* 2223:     */         case '@': 
/* 2224:     */         case '\\': 
/* 2225:     */         case ']': 
/* 2226:     */         case '^': 
/* 2227:     */         case '`': 
/* 2228:     */         default: 
/* 2229:1495 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2230:     */         }
/* 2231:1499 */         mARG(false); continue;
/* 2232:     */         
/* 2233:     */ 
/* 2234:     */ 
/* 2235:     */ 
/* 2236:     */ 
/* 2237:     */ 
/* 2238:     */ 
/* 2239:     */ 
/* 2240:     */ 
/* 2241:     */ 
/* 2242:     */ 
/* 2243:     */ 
/* 2244:1512 */         break;
/* 2245:     */         
/* 2246:     */ 
/* 2247:     */ 
/* 2248:1516 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2249:     */       }
/* 2250:1521 */       switch (LA(1))
/* 2251:     */       {
/* 2252:     */       case '\t': 
/* 2253:     */       case '\n': 
/* 2254:     */       case '\r': 
/* 2255:     */       case ' ': 
/* 2256:1524 */         k = this.text.length();
/* 2257:1525 */         mWS(false);
/* 2258:1526 */         this.text.setLength(k);
/* 2259:1527 */         break;
/* 2260:     */       case ')': 
/* 2261:     */         break;
/* 2262:     */       default: 
/* 2263:1535 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2264:     */       }
/* 2265:1539 */       match(')');
/* 2266:1540 */       break;
/* 2267:     */     case '[': 
/* 2268:1545 */       int m = 0;
/* 2269:     */       for (;;)
/* 2270:     */       {
/* 2271:1548 */         if (LA(1) == '[')
/* 2272:     */         {
/* 2273:1549 */           match('[');
/* 2274:1551 */           switch (LA(1))
/* 2275:     */           {
/* 2276:     */           case '\t': 
/* 2277:     */           case '\n': 
/* 2278:     */           case '\r': 
/* 2279:     */           case ' ': 
/* 2280:1554 */             k = this.text.length();
/* 2281:1555 */             mWS(false);
/* 2282:1556 */             this.text.setLength(k);
/* 2283:1557 */             break;
/* 2284:     */           case '"': 
/* 2285:     */           case '#': 
/* 2286:     */           case '\'': 
/* 2287:     */           case '(': 
/* 2288:     */           case '0': 
/* 2289:     */           case '1': 
/* 2290:     */           case '2': 
/* 2291:     */           case '3': 
/* 2292:     */           case '4': 
/* 2293:     */           case '5': 
/* 2294:     */           case '6': 
/* 2295:     */           case '7': 
/* 2296:     */           case '8': 
/* 2297:     */           case '9': 
/* 2298:     */           case 'A': 
/* 2299:     */           case 'B': 
/* 2300:     */           case 'C': 
/* 2301:     */           case 'D': 
/* 2302:     */           case 'E': 
/* 2303:     */           case 'F': 
/* 2304:     */           case 'G': 
/* 2305:     */           case 'H': 
/* 2306:     */           case 'I': 
/* 2307:     */           case 'J': 
/* 2308:     */           case 'K': 
/* 2309:     */           case 'L': 
/* 2310:     */           case 'M': 
/* 2311:     */           case 'N': 
/* 2312:     */           case 'O': 
/* 2313:     */           case 'P': 
/* 2314:     */           case 'Q': 
/* 2315:     */           case 'R': 
/* 2316:     */           case 'S': 
/* 2317:     */           case 'T': 
/* 2318:     */           case 'U': 
/* 2319:     */           case 'V': 
/* 2320:     */           case 'W': 
/* 2321:     */           case 'X': 
/* 2322:     */           case 'Y': 
/* 2323:     */           case 'Z': 
/* 2324:     */           case '[': 
/* 2325:     */           case '_': 
/* 2326:     */           case 'a': 
/* 2327:     */           case 'b': 
/* 2328:     */           case 'c': 
/* 2329:     */           case 'd': 
/* 2330:     */           case 'e': 
/* 2331:     */           case 'f': 
/* 2332:     */           case 'g': 
/* 2333:     */           case 'h': 
/* 2334:     */           case 'i': 
/* 2335:     */           case 'j': 
/* 2336:     */           case 'k': 
/* 2337:     */           case 'l': 
/* 2338:     */           case 'm': 
/* 2339:     */           case 'n': 
/* 2340:     */           case 'o': 
/* 2341:     */           case 'p': 
/* 2342:     */           case 'q': 
/* 2343:     */           case 'r': 
/* 2344:     */           case 's': 
/* 2345:     */           case 't': 
/* 2346:     */           case 'u': 
/* 2347:     */           case 'v': 
/* 2348:     */           case 'w': 
/* 2349:     */           case 'x': 
/* 2350:     */           case 'y': 
/* 2351:     */           case 'z': 
/* 2352:     */             break;
/* 2353:     */           case '\013': 
/* 2354:     */           case '\f': 
/* 2355:     */           case '\016': 
/* 2356:     */           case '\017': 
/* 2357:     */           case '\020': 
/* 2358:     */           case '\021': 
/* 2359:     */           case '\022': 
/* 2360:     */           case '\023': 
/* 2361:     */           case '\024': 
/* 2362:     */           case '\025': 
/* 2363:     */           case '\026': 
/* 2364:     */           case '\027': 
/* 2365:     */           case '\030': 
/* 2366:     */           case '\031': 
/* 2367:     */           case '\032': 
/* 2368:     */           case '\033': 
/* 2369:     */           case '\034': 
/* 2370:     */           case '\035': 
/* 2371:     */           case '\036': 
/* 2372:     */           case '\037': 
/* 2373:     */           case '!': 
/* 2374:     */           case '$': 
/* 2375:     */           case '%': 
/* 2376:     */           case '&': 
/* 2377:     */           case ')': 
/* 2378:     */           case '*': 
/* 2379:     */           case '+': 
/* 2380:     */           case ',': 
/* 2381:     */           case '-': 
/* 2382:     */           case '.': 
/* 2383:     */           case '/': 
/* 2384:     */           case ':': 
/* 2385:     */           case ';': 
/* 2386:     */           case '<': 
/* 2387:     */           case '=': 
/* 2388:     */           case '>': 
/* 2389:     */           case '?': 
/* 2390:     */           case '@': 
/* 2391:     */           case '\\': 
/* 2392:     */           case ']': 
/* 2393:     */           case '^': 
/* 2394:     */           case '`': 
/* 2395:     */           default: 
/* 2396:1581 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2397:     */           }
/* 2398:1585 */           mARG(false);
/* 2399:1587 */           switch (LA(1))
/* 2400:     */           {
/* 2401:     */           case '\t': 
/* 2402:     */           case '\n': 
/* 2403:     */           case '\r': 
/* 2404:     */           case ' ': 
/* 2405:1590 */             k = this.text.length();
/* 2406:1591 */             mWS(false);
/* 2407:1592 */             this.text.setLength(k);
/* 2408:1593 */             break;
/* 2409:     */           case ']': 
/* 2410:     */             break;
/* 2411:     */           default: 
/* 2412:1601 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2413:     */           }
/* 2414:1605 */           match(']');
/* 2415:     */         }
/* 2416:     */         else
/* 2417:     */         {
/* 2418:1608 */           if (m >= 1) {
/* 2419:     */             break;
/* 2420:     */           }
/* 2421:1608 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2422:     */         }
/* 2423:1611 */         m++;
/* 2424:     */       }
/* 2425:     */     case '.': 
/* 2426:1618 */       match('.');
/* 2427:1619 */       mID_ELEMENT(false);
/* 2428:1620 */       break;
/* 2429:     */     case '\t': 
/* 2430:     */     case '\n': 
/* 2431:     */     case '\r': 
/* 2432:     */     case ' ': 
/* 2433:     */     case ')': 
/* 2434:     */     case '*': 
/* 2435:     */     case '+': 
/* 2436:     */     case ',': 
/* 2437:     */     case '-': 
/* 2438:     */     case '/': 
/* 2439:     */     case '=': 
/* 2440:     */     case ']': 
/* 2441:1627 */       bool = true;
/* 2442:1628 */       String str = this.generator.mapTreeId(localToken2.getText(), this.transInfo);
/* 2443:1629 */       this.text.setLength(j);this.text.append(str);
/* 2444:1632 */       if ((_tokenSet_15.member(LA(1))) && (_tokenSet_16.member(LA(2))) && (this.transInfo != null) && (this.transInfo.refRuleRoot != null))
/* 2445:     */       {
/* 2446:1634 */         switch (LA(1))
/* 2447:     */         {
/* 2448:     */         case '\t': 
/* 2449:     */         case '\n': 
/* 2450:     */         case '\r': 
/* 2451:     */         case ' ': 
/* 2452:1637 */           mWS(false);
/* 2453:1638 */           break;
/* 2454:     */         case '=': 
/* 2455:     */           break;
/* 2456:     */         default: 
/* 2457:1646 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2458:     */         }
/* 2459:1650 */         mVAR_ASSIGN(false);
/* 2460:     */       }
/* 2461:1652 */       else if (!_tokenSet_17.member(LA(1)))
/* 2462:     */       {
/* 2463:1655 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2464:     */       }
/* 2465:     */       break;
/* 2466:     */     default: 
/* 2467:1663 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2468:     */     }
/* 2469:1667 */     if ((paramBoolean) && (localToken1 == null) && (i != -1))
/* 2470:     */     {
/* 2471:1668 */       localToken1 = makeToken(i);
/* 2472:1669 */       localToken1.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 2473:     */     }
/* 2474:1671 */     this._returnToken = localToken1;
/* 2475:1672 */     return bool;
/* 2476:     */   }
/* 2477:     */   
/* 2478:     */   protected final void mAST_CTOR_ELEMENT(boolean paramBoolean)
/* 2479:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 2480:     */   {
/* 2481:1679 */     Token localToken = null;int j = this.text.length();
/* 2482:1680 */     int i = 11;
/* 2483:1683 */     if ((LA(1) == '"') && (LA(2) >= '\003') && (LA(2) <= 'ÿ') && (LA(3) >= '\003') && (LA(3) <= 'ÿ')) {
/* 2484:1684 */       mSTRING(false);
/* 2485:1686 */     } else if ((_tokenSet_18.member(LA(1))) && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 2486:1687 */       mTREE_ELEMENT(false);
/* 2487:1689 */     } else if ((LA(1) >= '0') && (LA(1) <= '9')) {
/* 2488:1690 */       mINT(false);
/* 2489:     */     } else {
/* 2490:1693 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2491:     */     }
/* 2492:1696 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 2493:     */     {
/* 2494:1697 */       localToken = makeToken(i);
/* 2495:1698 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 2496:     */     }
/* 2497:1700 */     this._returnToken = localToken;
/* 2498:     */   }
/* 2499:     */   
/* 2500:     */   protected final void mINT(boolean paramBoolean)
/* 2501:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 2502:     */   {
/* 2503:1704 */     Token localToken = null;int j = this.text.length();
/* 2504:1705 */     int i = 27;
/* 2505:     */     
/* 2506:     */ 
/* 2507:     */ 
/* 2508:1709 */     int k = 0;
/* 2509:     */     for (;;)
/* 2510:     */     {
/* 2511:1712 */       if ((LA(1) >= '0') && (LA(1) <= '9'))
/* 2512:     */       {
/* 2513:1713 */         mDIGIT(false);
/* 2514:     */       }
/* 2515:     */       else
/* 2516:     */       {
/* 2517:1716 */         if (k >= 1) {
/* 2518:     */           break;
/* 2519:     */         }
/* 2520:1716 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2521:     */       }
/* 2522:1719 */       k++;
/* 2523:     */     }
/* 2524:1722 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 2525:     */     {
/* 2526:1723 */       localToken = makeToken(i);
/* 2527:1724 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 2528:     */     }
/* 2529:1726 */     this._returnToken = localToken;
/* 2530:     */   }
/* 2531:     */   
/* 2532:     */   protected final void mARG(boolean paramBoolean)
/* 2533:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 2534:     */   {
/* 2535:1730 */     Token localToken = null;int j = this.text.length();
/* 2536:1731 */     int i = 16;
/* 2537:1735 */     switch (LA(1))
/* 2538:     */     {
/* 2539:     */     case '\'': 
/* 2540:1738 */       mCHAR(false);
/* 2541:1739 */       break;
/* 2542:     */     case '0': 
/* 2543:     */     case '1': 
/* 2544:     */     case '2': 
/* 2545:     */     case '3': 
/* 2546:     */     case '4': 
/* 2547:     */     case '5': 
/* 2548:     */     case '6': 
/* 2549:     */     case '7': 
/* 2550:     */     case '8': 
/* 2551:     */     case '9': 
/* 2552:1745 */       mINT_OR_FLOAT(false);
/* 2553:1746 */       break;
/* 2554:     */     case '(': 
/* 2555:     */     case ')': 
/* 2556:     */     case '*': 
/* 2557:     */     case '+': 
/* 2558:     */     case ',': 
/* 2559:     */     case '-': 
/* 2560:     */     case '.': 
/* 2561:     */     case '/': 
/* 2562:     */     default: 
/* 2563:1749 */       if ((_tokenSet_18.member(LA(1))) && (LA(2) >= '\003') && (LA(2) <= 'ÿ') && (LA(3) >= '\003') && (LA(3) <= 'ÿ')) {
/* 2564:1750 */         mTREE_ELEMENT(false);
/* 2565:1752 */       } else if ((LA(1) == '"') && (LA(2) >= '\003') && (LA(2) <= 'ÿ') && (LA(3) >= '\003') && (LA(3) <= 'ÿ')) {
/* 2566:1753 */         mSTRING(false);
/* 2567:     */       } else {
/* 2568:1756 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2569:     */       }
/* 2570:     */       break;
/* 2571:     */     }
/* 2572:1763 */     while ((_tokenSet_19.member(LA(1))) && (_tokenSet_20.member(LA(2))) && (LA(3) >= '\003') && (LA(3) <= 'ÿ'))
/* 2573:     */     {
/* 2574:1765 */       switch (LA(1))
/* 2575:     */       {
/* 2576:     */       case '\t': 
/* 2577:     */       case '\n': 
/* 2578:     */       case '\r': 
/* 2579:     */       case ' ': 
/* 2580:1768 */         mWS(false);
/* 2581:1769 */         break;
/* 2582:     */       case '*': 
/* 2583:     */       case '+': 
/* 2584:     */       case '-': 
/* 2585:     */       case '/': 
/* 2586:     */         break;
/* 2587:     */       default: 
/* 2588:1777 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2589:     */       }
/* 2590:1782 */       switch (LA(1))
/* 2591:     */       {
/* 2592:     */       case '+': 
/* 2593:1785 */         match('+');
/* 2594:1786 */         break;
/* 2595:     */       case '-': 
/* 2596:1790 */         match('-');
/* 2597:1791 */         break;
/* 2598:     */       case '*': 
/* 2599:1795 */         match('*');
/* 2600:1796 */         break;
/* 2601:     */       case '/': 
/* 2602:1800 */         match('/');
/* 2603:1801 */         break;
/* 2604:     */       case ',': 
/* 2605:     */       case '.': 
/* 2606:     */       default: 
/* 2607:1805 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2608:     */       }
/* 2609:1810 */       switch (LA(1))
/* 2610:     */       {
/* 2611:     */       case '\t': 
/* 2612:     */       case '\n': 
/* 2613:     */       case '\r': 
/* 2614:     */       case ' ': 
/* 2615:1813 */         mWS(false);
/* 2616:1814 */         break;
/* 2617:     */       case '"': 
/* 2618:     */       case '#': 
/* 2619:     */       case '\'': 
/* 2620:     */       case '(': 
/* 2621:     */       case '0': 
/* 2622:     */       case '1': 
/* 2623:     */       case '2': 
/* 2624:     */       case '3': 
/* 2625:     */       case '4': 
/* 2626:     */       case '5': 
/* 2627:     */       case '6': 
/* 2628:     */       case '7': 
/* 2629:     */       case '8': 
/* 2630:     */       case '9': 
/* 2631:     */       case 'A': 
/* 2632:     */       case 'B': 
/* 2633:     */       case 'C': 
/* 2634:     */       case 'D': 
/* 2635:     */       case 'E': 
/* 2636:     */       case 'F': 
/* 2637:     */       case 'G': 
/* 2638:     */       case 'H': 
/* 2639:     */       case 'I': 
/* 2640:     */       case 'J': 
/* 2641:     */       case 'K': 
/* 2642:     */       case 'L': 
/* 2643:     */       case 'M': 
/* 2644:     */       case 'N': 
/* 2645:     */       case 'O': 
/* 2646:     */       case 'P': 
/* 2647:     */       case 'Q': 
/* 2648:     */       case 'R': 
/* 2649:     */       case 'S': 
/* 2650:     */       case 'T': 
/* 2651:     */       case 'U': 
/* 2652:     */       case 'V': 
/* 2653:     */       case 'W': 
/* 2654:     */       case 'X': 
/* 2655:     */       case 'Y': 
/* 2656:     */       case 'Z': 
/* 2657:     */       case '[': 
/* 2658:     */       case '_': 
/* 2659:     */       case 'a': 
/* 2660:     */       case 'b': 
/* 2661:     */       case 'c': 
/* 2662:     */       case 'd': 
/* 2663:     */       case 'e': 
/* 2664:     */       case 'f': 
/* 2665:     */       case 'g': 
/* 2666:     */       case 'h': 
/* 2667:     */       case 'i': 
/* 2668:     */       case 'j': 
/* 2669:     */       case 'k': 
/* 2670:     */       case 'l': 
/* 2671:     */       case 'm': 
/* 2672:     */       case 'n': 
/* 2673:     */       case 'o': 
/* 2674:     */       case 'p': 
/* 2675:     */       case 'q': 
/* 2676:     */       case 'r': 
/* 2677:     */       case 's': 
/* 2678:     */       case 't': 
/* 2679:     */       case 'u': 
/* 2680:     */       case 'v': 
/* 2681:     */       case 'w': 
/* 2682:     */       case 'x': 
/* 2683:     */       case 'y': 
/* 2684:     */       case 'z': 
/* 2685:     */         break;
/* 2686:     */       case '\013': 
/* 2687:     */       case '\f': 
/* 2688:     */       case '\016': 
/* 2689:     */       case '\017': 
/* 2690:     */       case '\020': 
/* 2691:     */       case '\021': 
/* 2692:     */       case '\022': 
/* 2693:     */       case '\023': 
/* 2694:     */       case '\024': 
/* 2695:     */       case '\025': 
/* 2696:     */       case '\026': 
/* 2697:     */       case '\027': 
/* 2698:     */       case '\030': 
/* 2699:     */       case '\031': 
/* 2700:     */       case '\032': 
/* 2701:     */       case '\033': 
/* 2702:     */       case '\034': 
/* 2703:     */       case '\035': 
/* 2704:     */       case '\036': 
/* 2705:     */       case '\037': 
/* 2706:     */       case '!': 
/* 2707:     */       case '$': 
/* 2708:     */       case '%': 
/* 2709:     */       case '&': 
/* 2710:     */       case ')': 
/* 2711:     */       case '*': 
/* 2712:     */       case '+': 
/* 2713:     */       case ',': 
/* 2714:     */       case '-': 
/* 2715:     */       case '.': 
/* 2716:     */       case '/': 
/* 2717:     */       case ':': 
/* 2718:     */       case ';': 
/* 2719:     */       case '<': 
/* 2720:     */       case '=': 
/* 2721:     */       case '>': 
/* 2722:     */       case '?': 
/* 2723:     */       case '@': 
/* 2724:     */       case '\\': 
/* 2725:     */       case ']': 
/* 2726:     */       case '^': 
/* 2727:     */       case '`': 
/* 2728:     */       default: 
/* 2729:1838 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2730:     */       }
/* 2731:1842 */       mARG(false);
/* 2732:     */     }
/* 2733:1850 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 2734:     */     {
/* 2735:1851 */       localToken = makeToken(i);
/* 2736:1852 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 2737:     */     }
/* 2738:1854 */     this._returnToken = localToken;
/* 2739:     */   }
/* 2740:     */   
/* 2741:     */   protected final void mTEXT_ARG_ELEMENT(boolean paramBoolean)
/* 2742:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 2743:     */   {
/* 2744:1858 */     Token localToken = null;int j = this.text.length();
/* 2745:1859 */     int i = 14;
/* 2746:1862 */     switch (LA(1))
/* 2747:     */     {
/* 2748:     */     case 'A': 
/* 2749:     */     case 'B': 
/* 2750:     */     case 'C': 
/* 2751:     */     case 'D': 
/* 2752:     */     case 'E': 
/* 2753:     */     case 'F': 
/* 2754:     */     case 'G': 
/* 2755:     */     case 'H': 
/* 2756:     */     case 'I': 
/* 2757:     */     case 'J': 
/* 2758:     */     case 'K': 
/* 2759:     */     case 'L': 
/* 2760:     */     case 'M': 
/* 2761:     */     case 'N': 
/* 2762:     */     case 'O': 
/* 2763:     */     case 'P': 
/* 2764:     */     case 'Q': 
/* 2765:     */     case 'R': 
/* 2766:     */     case 'S': 
/* 2767:     */     case 'T': 
/* 2768:     */     case 'U': 
/* 2769:     */     case 'V': 
/* 2770:     */     case 'W': 
/* 2771:     */     case 'X': 
/* 2772:     */     case 'Y': 
/* 2773:     */     case 'Z': 
/* 2774:     */     case '_': 
/* 2775:     */     case 'a': 
/* 2776:     */     case 'b': 
/* 2777:     */     case 'c': 
/* 2778:     */     case 'd': 
/* 2779:     */     case 'e': 
/* 2780:     */     case 'f': 
/* 2781:     */     case 'g': 
/* 2782:     */     case 'h': 
/* 2783:     */     case 'i': 
/* 2784:     */     case 'j': 
/* 2785:     */     case 'k': 
/* 2786:     */     case 'l': 
/* 2787:     */     case 'm': 
/* 2788:     */     case 'n': 
/* 2789:     */     case 'o': 
/* 2790:     */     case 'p': 
/* 2791:     */     case 'q': 
/* 2792:     */     case 'r': 
/* 2793:     */     case 's': 
/* 2794:     */     case 't': 
/* 2795:     */     case 'u': 
/* 2796:     */     case 'v': 
/* 2797:     */     case 'w': 
/* 2798:     */     case 'x': 
/* 2799:     */     case 'y': 
/* 2800:     */     case 'z': 
/* 2801:1878 */       mTEXT_ARG_ID_ELEMENT(false);
/* 2802:1879 */       break;
/* 2803:     */     case '"': 
/* 2804:1883 */       mSTRING(false);
/* 2805:1884 */       break;
/* 2806:     */     case '\'': 
/* 2807:1888 */       mCHAR(false);
/* 2808:1889 */       break;
/* 2809:     */     case '0': 
/* 2810:     */     case '1': 
/* 2811:     */     case '2': 
/* 2812:     */     case '3': 
/* 2813:     */     case '4': 
/* 2814:     */     case '5': 
/* 2815:     */     case '6': 
/* 2816:     */     case '7': 
/* 2817:     */     case '8': 
/* 2818:     */     case '9': 
/* 2819:1895 */       mINT_OR_FLOAT(false);
/* 2820:1896 */       break;
/* 2821:     */     case '$': 
/* 2822:1900 */       mTEXT_ITEM(false);
/* 2823:1901 */       break;
/* 2824:     */     case '+': 
/* 2825:1905 */       match('+');
/* 2826:1906 */       break;
/* 2827:     */     case '#': 
/* 2828:     */     case '%': 
/* 2829:     */     case '&': 
/* 2830:     */     case '(': 
/* 2831:     */     case ')': 
/* 2832:     */     case '*': 
/* 2833:     */     case ',': 
/* 2834:     */     case '-': 
/* 2835:     */     case '.': 
/* 2836:     */     case '/': 
/* 2837:     */     case ':': 
/* 2838:     */     case ';': 
/* 2839:     */     case '<': 
/* 2840:     */     case '=': 
/* 2841:     */     case '>': 
/* 2842:     */     case '?': 
/* 2843:     */     case '@': 
/* 2844:     */     case '[': 
/* 2845:     */     case '\\': 
/* 2846:     */     case ']': 
/* 2847:     */     case '^': 
/* 2848:     */     case '`': 
/* 2849:     */     default: 
/* 2850:1910 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2851:     */     }
/* 2852:1913 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 2853:     */     {
/* 2854:1914 */       localToken = makeToken(i);
/* 2855:1915 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 2856:     */     }
/* 2857:1917 */     this._returnToken = localToken;
/* 2858:     */   }
/* 2859:     */   
/* 2860:     */   protected final void mTEXT_ARG_ID_ELEMENT(boolean paramBoolean)
/* 2861:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 2862:     */   {
/* 2863:1921 */     Token localToken1 = null;int j = this.text.length();
/* 2864:1922 */     int i = 15;
/* 2865:     */     
/* 2866:1924 */     Token localToken2 = null;
/* 2867:     */     
/* 2868:1926 */     mID(true);
/* 2869:1927 */     localToken2 = this._returnToken;
/* 2870:     */     int k;
/* 2871:1929 */     if ((_tokenSet_4.member(LA(1))) && (_tokenSet_21.member(LA(2))))
/* 2872:     */     {
/* 2873:1930 */       k = this.text.length();
/* 2874:1931 */       mWS(false);
/* 2875:1932 */       this.text.setLength(k);
/* 2876:     */     }
/* 2877:1934 */     else if (!_tokenSet_21.member(LA(1)))
/* 2878:     */     {
/* 2879:1937 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2880:     */     }
/* 2881:1942 */     switch (LA(1))
/* 2882:     */     {
/* 2883:     */     case '(': 
/* 2884:1945 */       match('(');
/* 2885:1947 */       if ((_tokenSet_4.member(LA(1))) && (_tokenSet_22.member(LA(2))) && (LA(3) >= '\003') && (LA(3) <= 'ÿ'))
/* 2886:     */       {
/* 2887:1948 */         k = this.text.length();
/* 2888:1949 */         mWS(false);
/* 2889:1950 */         this.text.setLength(k);
/* 2890:     */       }
/* 2891:1952 */       else if ((!_tokenSet_22.member(LA(1))) || (LA(2) < '\003') || (LA(2) > 'ÿ'))
/* 2892:     */       {
/* 2893:1955 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2894:     */       }
/* 2895:1962 */       if ((_tokenSet_23.member(LA(1))) && (LA(2) >= '\003') && (LA(2) <= 'ÿ') && (LA(3) >= '\003') && (LA(3) <= 'ÿ'))
/* 2896:     */       {
/* 2897:1963 */         mTEXT_ARG(false);
/* 2898:1967 */         while (LA(1) == ',')
/* 2899:     */         {
/* 2900:1968 */           match(',');
/* 2901:1969 */           mTEXT_ARG(false);
/* 2902:     */         }
/* 2903:     */       }
/* 2904:1985 */       switch (LA(1))
/* 2905:     */       {
/* 2906:     */       case '\t': 
/* 2907:     */       case '\n': 
/* 2908:     */       case '\r': 
/* 2909:     */       case ' ': 
/* 2910:1988 */         k = this.text.length();
/* 2911:1989 */         mWS(false);
/* 2912:1990 */         this.text.setLength(k);
/* 2913:1991 */         break;
/* 2914:     */       case ')': 
/* 2915:     */         break;
/* 2916:     */       default: 
/* 2917:1999 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2918:     */       }
/* 2919:2003 */       match(')');
/* 2920:2004 */       break;
/* 2921:     */     case '[': 
/* 2922:2009 */       int m = 0;
/* 2923:     */       for (;;)
/* 2924:     */       {
/* 2925:2012 */         if (LA(1) == '[')
/* 2926:     */         {
/* 2927:2013 */           match('[');
/* 2928:2015 */           if ((_tokenSet_4.member(LA(1))) && (_tokenSet_23.member(LA(2))) && (LA(3) >= '\003') && (LA(3) <= 'ÿ'))
/* 2929:     */           {
/* 2930:2016 */             k = this.text.length();
/* 2931:2017 */             mWS(false);
/* 2932:2018 */             this.text.setLength(k);
/* 2933:     */           }
/* 2934:2020 */           else if ((!_tokenSet_23.member(LA(1))) || (LA(2) < '\003') || (LA(2) > 'ÿ') || (LA(3) < '\003') || (LA(3) > 'ÿ'))
/* 2935:     */           {
/* 2936:2023 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2937:     */           }
/* 2938:2027 */           mTEXT_ARG(false);
/* 2939:2029 */           switch (LA(1))
/* 2940:     */           {
/* 2941:     */           case '\t': 
/* 2942:     */           case '\n': 
/* 2943:     */           case '\r': 
/* 2944:     */           case ' ': 
/* 2945:2032 */             k = this.text.length();
/* 2946:2033 */             mWS(false);
/* 2947:2034 */             this.text.setLength(k);
/* 2948:2035 */             break;
/* 2949:     */           case ']': 
/* 2950:     */             break;
/* 2951:     */           default: 
/* 2952:2043 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2953:     */           }
/* 2954:2047 */           match(']');
/* 2955:     */         }
/* 2956:     */         else
/* 2957:     */         {
/* 2958:2050 */           if (m >= 1) {
/* 2959:     */             break;
/* 2960:     */           }
/* 2961:2050 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 2962:     */         }
/* 2963:2053 */         m++;
/* 2964:     */       }
/* 2965:     */     case '.': 
/* 2966:2060 */       match('.');
/* 2967:2061 */       mTEXT_ARG_ID_ELEMENT(false);
/* 2968:2062 */       break;
/* 2969:     */     case '\t': 
/* 2970:     */     case '\n': 
/* 2971:     */     case '\r': 
/* 2972:     */     case ' ': 
/* 2973:     */     case '"': 
/* 2974:     */     case '$': 
/* 2975:     */     case '\'': 
/* 2976:     */     case ')': 
/* 2977:     */     case '+': 
/* 2978:     */     case ',': 
/* 2979:     */     case '0': 
/* 2980:     */     case '1': 
/* 2981:     */     case '2': 
/* 2982:     */     case '3': 
/* 2983:     */     case '4': 
/* 2984:     */     case '5': 
/* 2985:     */     case '6': 
/* 2986:     */     case '7': 
/* 2987:     */     case '8': 
/* 2988:     */     case '9': 
/* 2989:     */     case 'A': 
/* 2990:     */     case 'B': 
/* 2991:     */     case 'C': 
/* 2992:     */     case 'D': 
/* 2993:     */     case 'E': 
/* 2994:     */     case 'F': 
/* 2995:     */     case 'G': 
/* 2996:     */     case 'H': 
/* 2997:     */     case 'I': 
/* 2998:     */     case 'J': 
/* 2999:     */     case 'K': 
/* 3000:     */     case 'L': 
/* 3001:     */     case 'M': 
/* 3002:     */     case 'N': 
/* 3003:     */     case 'O': 
/* 3004:     */     case 'P': 
/* 3005:     */     case 'Q': 
/* 3006:     */     case 'R': 
/* 3007:     */     case 'S': 
/* 3008:     */     case 'T': 
/* 3009:     */     case 'U': 
/* 3010:     */     case 'V': 
/* 3011:     */     case 'W': 
/* 3012:     */     case 'X': 
/* 3013:     */     case 'Y': 
/* 3014:     */     case 'Z': 
/* 3015:     */     case ']': 
/* 3016:     */     case '_': 
/* 3017:     */     case 'a': 
/* 3018:     */     case 'b': 
/* 3019:     */     case 'c': 
/* 3020:     */     case 'd': 
/* 3021:     */     case 'e': 
/* 3022:     */     case 'f': 
/* 3023:     */     case 'g': 
/* 3024:     */     case 'h': 
/* 3025:     */     case 'i': 
/* 3026:     */     case 'j': 
/* 3027:     */     case 'k': 
/* 3028:     */     case 'l': 
/* 3029:     */     case 'm': 
/* 3030:     */     case 'n': 
/* 3031:     */     case 'o': 
/* 3032:     */     case 'p': 
/* 3033:     */     case 'q': 
/* 3034:     */     case 'r': 
/* 3035:     */     case 's': 
/* 3036:     */     case 't': 
/* 3037:     */     case 'u': 
/* 3038:     */     case 'v': 
/* 3039:     */     case 'w': 
/* 3040:     */     case 'x': 
/* 3041:     */     case 'y': 
/* 3042:     */     case 'z': 
/* 3043:     */       break;
/* 3044:     */     case '\013': 
/* 3045:     */     case '\f': 
/* 3046:     */     case '\016': 
/* 3047:     */     case '\017': 
/* 3048:     */     case '\020': 
/* 3049:     */     case '\021': 
/* 3050:     */     case '\022': 
/* 3051:     */     case '\023': 
/* 3052:     */     case '\024': 
/* 3053:     */     case '\025': 
/* 3054:     */     case '\026': 
/* 3055:     */     case '\027': 
/* 3056:     */     case '\030': 
/* 3057:     */     case '\031': 
/* 3058:     */     case '\032': 
/* 3059:     */     case '\033': 
/* 3060:     */     case '\034': 
/* 3061:     */     case '\035': 
/* 3062:     */     case '\036': 
/* 3063:     */     case '\037': 
/* 3064:     */     case '!': 
/* 3065:     */     case '#': 
/* 3066:     */     case '%': 
/* 3067:     */     case '&': 
/* 3068:     */     case '*': 
/* 3069:     */     case '-': 
/* 3070:     */     case '/': 
/* 3071:     */     case ':': 
/* 3072:     */     case ';': 
/* 3073:     */     case '<': 
/* 3074:     */     case '=': 
/* 3075:     */     case '>': 
/* 3076:     */     case '?': 
/* 3077:     */     case '@': 
/* 3078:     */     case '\\': 
/* 3079:     */     case '^': 
/* 3080:     */     case '`': 
/* 3081:     */     default: 
/* 3082:2088 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 3083:     */     }
/* 3084:2092 */     if ((paramBoolean) && (localToken1 == null) && (i != -1))
/* 3085:     */     {
/* 3086:2093 */       localToken1 = makeToken(i);
/* 3087:2094 */       localToken1.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 3088:     */     }
/* 3089:2096 */     this._returnToken = localToken1;
/* 3090:     */   }
/* 3091:     */   
/* 3092:     */   protected final void mINT_OR_FLOAT(boolean paramBoolean)
/* 3093:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 3094:     */   {
/* 3095:2100 */     Token localToken = null;int j = this.text.length();
/* 3096:2101 */     int i = 28;
/* 3097:     */     
/* 3098:     */ 
/* 3099:     */ 
/* 3100:2105 */     int k = 0;
/* 3101:     */     for (;;)
/* 3102:     */     {
/* 3103:2108 */       if ((LA(1) >= '0') && (LA(1) <= '9') && (_tokenSet_24.member(LA(2))))
/* 3104:     */       {
/* 3105:2109 */         mDIGIT(false);
/* 3106:     */       }
/* 3107:     */       else
/* 3108:     */       {
/* 3109:2112 */         if (k >= 1) {
/* 3110:     */           break;
/* 3111:     */         }
/* 3112:2112 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 3113:     */       }
/* 3114:2115 */       k++;
/* 3115:     */     }
/* 3116:2119 */     if ((LA(1) == 'L') && (_tokenSet_25.member(LA(2))))
/* 3117:     */     {
/* 3118:2120 */       match('L');
/* 3119:     */     }
/* 3120:2122 */     else if ((LA(1) == 'l') && (_tokenSet_25.member(LA(2))))
/* 3121:     */     {
/* 3122:2123 */       match('l');
/* 3123:     */     }
/* 3124:     */     else
/* 3125:     */     {
/* 3126:2125 */       if (LA(1) == '.')
/* 3127:     */       {
/* 3128:2126 */         match('.');
/* 3129:2130 */         while ((LA(1) >= '0') && (LA(1) <= '9') && (_tokenSet_25.member(LA(2)))) {
/* 3130:2131 */           mDIGIT(false);
/* 3131:     */         }
/* 3132:     */       }
/* 3133:2140 */       if (!_tokenSet_25.member(LA(1))) {
/* 3134:2143 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 3135:     */       }
/* 3136:     */     }
/* 3137:2147 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 3138:     */     {
/* 3139:2148 */       localToken = makeToken(i);
/* 3140:2149 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 3141:     */     }
/* 3142:2151 */     this._returnToken = localToken;
/* 3143:     */   }
/* 3144:     */   
/* 3145:     */   protected final void mSL_COMMENT(boolean paramBoolean)
/* 3146:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 3147:     */   {
/* 3148:2155 */     Token localToken = null;int j = this.text.length();
/* 3149:2156 */     int i = 20;
/* 3150:     */     
/* 3151:     */ 
/* 3152:2159 */     match("//");
/* 3153:     */     
/* 3154:     */ 
/* 3155:2162 */     this.text.setLength(j);this.text.append("#");
/* 3156:2168 */     while ((LA(1) != '\n') && (LA(1) != '\r') && 
/* 3157:2169 */       (LA(1) >= '\003') && (LA(1) <= 'ÿ') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 3158:2170 */       matchNot(65535);
/* 3159:     */     }
/* 3160:2182 */     if ((LA(1) == '\r') && (LA(2) == '\n')) {
/* 3161:2183 */       match("\r\n");
/* 3162:2185 */     } else if (LA(1) == '\n') {
/* 3163:2186 */       match('\n');
/* 3164:2188 */     } else if (LA(1) == '\r') {
/* 3165:2189 */       match('\r');
/* 3166:     */     } else {
/* 3167:2192 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 3168:     */     }
/* 3169:2197 */     newline();
/* 3170:2199 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 3171:     */     {
/* 3172:2200 */       localToken = makeToken(i);
/* 3173:2201 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 3174:     */     }
/* 3175:2203 */     this._returnToken = localToken;
/* 3176:     */   }
/* 3177:     */   
/* 3178:     */   protected final void mML_COMMENT(boolean paramBoolean)
/* 3179:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 3180:     */   {
/* 3181:2207 */     Token localToken = null;int j = this.text.length();
/* 3182:2208 */     int i = 22;
/* 3183:     */     
/* 3184:     */ 
/* 3185:2211 */     match("/*");
/* 3186:     */     
/* 3187:     */ 
/* 3188:2214 */     this.text.setLength(j);this.text.append("#");
/* 3189:2220 */     while ((LA(1) != '*') || (LA(2) != '/')) {
/* 3190:2221 */       if ((LA(1) == '\r') && (LA(2) == '\n') && (LA(3) >= '\003') && (LA(3) <= 'ÿ'))
/* 3191:     */       {
/* 3192:2222 */         match('\r');
/* 3193:2223 */         match('\n');
/* 3194:2224 */         k = this.text.length();
/* 3195:2225 */         mIGNWS(false);
/* 3196:2226 */         this.text.setLength(k);
/* 3197:     */         
/* 3198:2228 */         newline();
/* 3199:2229 */         this.text.append("# ");
/* 3200:     */       }
/* 3201:2232 */       else if ((LA(1) == '\r') && (LA(2) >= '\003') && (LA(2) <= 'ÿ') && (LA(3) >= '\003') && (LA(3) <= 'ÿ'))
/* 3202:     */       {
/* 3203:2233 */         match('\r');
/* 3204:2234 */         k = this.text.length();
/* 3205:2235 */         mIGNWS(false);
/* 3206:2236 */         this.text.setLength(k);
/* 3207:     */         
/* 3208:2238 */         newline();
/* 3209:2239 */         this.text.append("# ");
/* 3210:     */       }
/* 3211:2242 */       else if ((LA(1) == '\n') && (LA(2) >= '\003') && (LA(2) <= 'ÿ') && (LA(3) >= '\003') && (LA(3) <= 'ÿ'))
/* 3212:     */       {
/* 3213:2243 */         match('\n');
/* 3214:2244 */         k = this.text.length();
/* 3215:2245 */         mIGNWS(false);
/* 3216:2246 */         this.text.setLength(k);
/* 3217:     */         
/* 3218:2248 */         newline();
/* 3219:2249 */         this.text.append("# ");
/* 3220:     */       }
/* 3221:     */       else
/* 3222:     */       {
/* 3223:2252 */         if ((LA(1) < '\003') || (LA(1) > 'ÿ') || (LA(2) < '\003') || (LA(2) > 'ÿ') || (LA(3) < '\003') || (LA(3) > 'ÿ')) {
/* 3224:     */           break;
/* 3225:     */         }
/* 3226:2253 */         matchNot(65535);
/* 3227:     */       }
/* 3228:     */     }
/* 3229:2264 */     this.text.append("\n");
/* 3230:     */     
/* 3231:2266 */     int k = this.text.length();
/* 3232:2267 */     match("*/");
/* 3233:2268 */     this.text.setLength(k);
/* 3234:2269 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 3235:     */     {
/* 3236:2270 */       localToken = makeToken(i);
/* 3237:2271 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 3238:     */     }
/* 3239:2273 */     this._returnToken = localToken;
/* 3240:     */   }
/* 3241:     */   
/* 3242:     */   protected final void mIGNWS(boolean paramBoolean)
/* 3243:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 3244:     */   {
/* 3245:2277 */     Token localToken = null;int j = this.text.length();
/* 3246:2278 */     int i = 21;
/* 3247:     */     for (;;)
/* 3248:     */     {
/* 3249:2284 */       if ((LA(1) == ' ') && (LA(2) >= '\003') && (LA(2) <= 'ÿ') && (LA(3) >= '\003') && (LA(3) <= 'ÿ'))
/* 3250:     */       {
/* 3251:2285 */         match(' ');
/* 3252:     */       }
/* 3253:     */       else
/* 3254:     */       {
/* 3255:2287 */         if ((LA(1) != '\t') || (LA(2) < '\003') || (LA(2) > 'ÿ') || (LA(3) < '\003') || (LA(3) > 'ÿ')) {
/* 3256:     */           break;
/* 3257:     */         }
/* 3258:2288 */         match('\t');
/* 3259:     */       }
/* 3260:     */     }
/* 3261:2296 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 3262:     */     {
/* 3263:2297 */       localToken = makeToken(i);
/* 3264:2298 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 3265:     */     }
/* 3266:2300 */     this._returnToken = localToken;
/* 3267:     */   }
/* 3268:     */   
/* 3269:     */   protected final void mESC(boolean paramBoolean)
/* 3270:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 3271:     */   {
/* 3272:2304 */     Token localToken = null;int j = this.text.length();
/* 3273:2305 */     int i = 25;
/* 3274:     */     
/* 3275:     */ 
/* 3276:2308 */     match('\\');
/* 3277:2310 */     switch (LA(1))
/* 3278:     */     {
/* 3279:     */     case 'n': 
/* 3280:2313 */       match('n');
/* 3281:2314 */       break;
/* 3282:     */     case 'r': 
/* 3283:2318 */       match('r');
/* 3284:2319 */       break;
/* 3285:     */     case 't': 
/* 3286:2323 */       match('t');
/* 3287:2324 */       break;
/* 3288:     */     case 'b': 
/* 3289:2328 */       match('b');
/* 3290:2329 */       break;
/* 3291:     */     case 'f': 
/* 3292:2333 */       match('f');
/* 3293:2334 */       break;
/* 3294:     */     case '"': 
/* 3295:2338 */       match('"');
/* 3296:2339 */       break;
/* 3297:     */     case '\'': 
/* 3298:2343 */       match('\'');
/* 3299:2344 */       break;
/* 3300:     */     case '\\': 
/* 3301:2348 */       match('\\');
/* 3302:2349 */       break;
/* 3303:     */     case '0': 
/* 3304:     */     case '1': 
/* 3305:     */     case '2': 
/* 3306:     */     case '3': 
/* 3307:2354 */       matchRange('0', '3');
/* 3308:2357 */       if ((LA(1) >= '0') && (LA(1) <= '9') && (LA(2) >= '\003') && (LA(2) <= 'ÿ'))
/* 3309:     */       {
/* 3310:2358 */         mDIGIT(false);
/* 3311:2360 */         if ((LA(1) >= '0') && (LA(1) <= '9') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 3312:2361 */           mDIGIT(false);
/* 3313:2363 */         } else if ((LA(1) < '\003') || (LA(1) > 'ÿ')) {
/* 3314:2366 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 3315:     */         }
/* 3316:     */       }
/* 3317:2371 */       else if ((LA(1) < '\003') || (LA(1) > 'ÿ'))
/* 3318:     */       {
/* 3319:2374 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 3320:     */       }
/* 3321:     */       break;
/* 3322:     */     case '4': 
/* 3323:     */     case '5': 
/* 3324:     */     case '6': 
/* 3325:     */     case '7': 
/* 3326:2383 */       matchRange('4', '7');
/* 3327:2386 */       if ((LA(1) >= '0') && (LA(1) <= '9') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 3328:2387 */         mDIGIT(false);
/* 3329:2389 */       } else if ((LA(1) < '\003') || (LA(1) > 'ÿ')) {
/* 3330:2392 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 3331:     */       }
/* 3332:     */       break;
/* 3333:     */     default: 
/* 3334:2400 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 3335:     */     }
/* 3336:2404 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 3337:     */     {
/* 3338:2405 */       localToken = makeToken(i);
/* 3339:2406 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 3340:     */     }
/* 3341:2408 */     this._returnToken = localToken;
/* 3342:     */   }
/* 3343:     */   
/* 3344:     */   protected final void mDIGIT(boolean paramBoolean)
/* 3345:     */     throws RecognitionException, CharStreamException, TokenStreamException
/* 3346:     */   {
/* 3347:2412 */     Token localToken = null;int j = this.text.length();
/* 3348:2413 */     int i = 26;
/* 3349:     */     
/* 3350:     */ 
/* 3351:2416 */     matchRange('0', '9');
/* 3352:2417 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 3353:     */     {
/* 3354:2418 */       localToken = makeToken(i);
/* 3355:2419 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 3356:     */     }
/* 3357:2421 */     this._returnToken = localToken;
/* 3358:     */   }
/* 3359:     */   
/* 3360:     */   private static final long[] mk_tokenSet_0()
/* 3361:     */   {
/* 3362:2426 */     long[] arrayOfLong = new long[8];
/* 3363:2427 */     arrayOfLong[0] = -103079215112L;
/* 3364:2428 */     for (int i = 1; i <= 3; i++) {
/* 3365:2428 */       arrayOfLong[i] = -1L;
/* 3366:     */     }
/* 3367:2429 */     return arrayOfLong;
/* 3368:     */   }
/* 3369:     */   
/* 3370:2431 */   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
/* 3371:     */   
/* 3372:     */   private static final long[] mk_tokenSet_1()
/* 3373:     */   {
/* 3374:2433 */     long[] arrayOfLong = new long[8];
/* 3375:2434 */     arrayOfLong[0] = -145135534866440L;
/* 3376:2435 */     for (int i = 1; i <= 3; i++) {
/* 3377:2435 */       arrayOfLong[i] = -1L;
/* 3378:     */     }
/* 3379:2436 */     return arrayOfLong;
/* 3380:     */   }
/* 3381:     */   
/* 3382:2438 */   public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
/* 3383:     */   
/* 3384:     */   private static final long[] mk_tokenSet_2()
/* 3385:     */   {
/* 3386:2440 */     long[] arrayOfLong = new long[8];
/* 3387:2441 */     arrayOfLong[0] = -141407503262728L;
/* 3388:2442 */     for (int i = 1; i <= 3; i++) {
/* 3389:2442 */       arrayOfLong[i] = -1L;
/* 3390:     */     }
/* 3391:2443 */     return arrayOfLong;
/* 3392:     */   }
/* 3393:     */   
/* 3394:2445 */   public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
/* 3395:     */   
/* 3396:     */   private static final long[] mk_tokenSet_3()
/* 3397:     */   {
/* 3398:2447 */     long[] arrayOfLong = { 0L, 576460745995190270L, 0L, 0L, 0L };
/* 3399:2448 */     return arrayOfLong;
/* 3400:     */   }
/* 3401:     */   
/* 3402:2450 */   public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
/* 3403:     */   
/* 3404:     */   private static final long[] mk_tokenSet_4()
/* 3405:     */   {
/* 3406:2452 */     long[] arrayOfLong = { 4294977024L, 0L, 0L, 0L, 0L };
/* 3407:2453 */     return arrayOfLong;
/* 3408:     */   }
/* 3409:     */   
/* 3410:2455 */   public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
/* 3411:     */   
/* 3412:     */   private static final long[] mk_tokenSet_5()
/* 3413:     */   {
/* 3414:2457 */     long[] arrayOfLong = { 1103806604800L, 0L, 0L, 0L, 0L };
/* 3415:2458 */     return arrayOfLong;
/* 3416:     */   }
/* 3417:     */   
/* 3418:2460 */   public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
/* 3419:     */   
/* 3420:     */   private static final long[] mk_tokenSet_6()
/* 3421:     */   {
/* 3422:2462 */     long[] arrayOfLong = { 287959436729787904L, 576460745995190270L, 0L, 0L, 0L };
/* 3423:2463 */     return arrayOfLong;
/* 3424:     */   }
/* 3425:     */   
/* 3426:2465 */   public static final BitSet _tokenSet_6 = new BitSet(mk_tokenSet_6());
/* 3427:     */   
/* 3428:     */   private static final long[] mk_tokenSet_7()
/* 3429:     */   {
/* 3430:2467 */     long[] arrayOfLong = new long[8];
/* 3431:2468 */     arrayOfLong[0] = -17179869192L;
/* 3432:2469 */     arrayOfLong[1] = -268435457L;
/* 3433:2470 */     for (int i = 2; i <= 3; i++) {
/* 3434:2470 */       arrayOfLong[i] = -1L;
/* 3435:     */     }
/* 3436:2471 */     return arrayOfLong;
/* 3437:     */   }
/* 3438:     */   
/* 3439:2473 */   public static final BitSet _tokenSet_7 = new BitSet(mk_tokenSet_7());
/* 3440:     */   
/* 3441:     */   private static final long[] mk_tokenSet_8()
/* 3442:     */   {
/* 3443:2475 */     long[] arrayOfLong = new long[8];
/* 3444:2476 */     arrayOfLong[0] = -549755813896L;
/* 3445:2477 */     arrayOfLong[1] = -268435457L;
/* 3446:2478 */     for (int i = 2; i <= 3; i++) {
/* 3447:2478 */       arrayOfLong[i] = -1L;
/* 3448:     */     }
/* 3449:2479 */     return arrayOfLong;
/* 3450:     */   }
/* 3451:     */   
/* 3452:2481 */   public static final BitSet _tokenSet_8 = new BitSet(mk_tokenSet_8());
/* 3453:     */   
/* 3454:     */   private static final long[] mk_tokenSet_9()
/* 3455:     */   {
/* 3456:2483 */     long[] arrayOfLong = { 287948901175001088L, 576460745995190270L, 0L, 0L, 0L };
/* 3457:2484 */     return arrayOfLong;
/* 3458:     */   }
/* 3459:     */   
/* 3460:2486 */   public static final BitSet _tokenSet_9 = new BitSet(mk_tokenSet_9());
/* 3461:     */   
/* 3462:     */   private static final long[] mk_tokenSet_10()
/* 3463:     */   {
/* 3464:2488 */     long[] arrayOfLong = { 287950056521213440L, 576460746129407998L, 0L, 0L, 0L };
/* 3465:2489 */     return arrayOfLong;
/* 3466:     */   }
/* 3467:     */   
/* 3468:2491 */   public static final BitSet _tokenSet_10 = new BitSet(mk_tokenSet_10());
/* 3469:     */   
/* 3470:     */   private static final long[] mk_tokenSet_11()
/* 3471:     */   {
/* 3472:2493 */     long[] arrayOfLong = { 287958332923183104L, 576460745995190270L, 0L, 0L, 0L };
/* 3473:2494 */     return arrayOfLong;
/* 3474:     */   }
/* 3475:     */   
/* 3476:2496 */   public static final BitSet _tokenSet_11 = new BitSet(mk_tokenSet_11());
/* 3477:     */   
/* 3478:     */   private static final long[] mk_tokenSet_12()
/* 3479:     */   {
/* 3480:2498 */     long[] arrayOfLong = { 287978128427460096L, 576460746532061182L, 0L, 0L, 0L };
/* 3481:2499 */     return arrayOfLong;
/* 3482:     */   }
/* 3483:     */   
/* 3484:2501 */   public static final BitSet _tokenSet_12 = new BitSet(mk_tokenSet_12());
/* 3485:     */   
/* 3486:     */   private static final long[] mk_tokenSet_13()
/* 3487:     */   {
/* 3488:2503 */     long[] arrayOfLong = { 2306123388973753856L, 671088640L, 0L, 0L, 0L };
/* 3489:2504 */     return arrayOfLong;
/* 3490:     */   }
/* 3491:     */   
/* 3492:2506 */   public static final BitSet _tokenSet_13 = new BitSet(mk_tokenSet_13());
/* 3493:     */   
/* 3494:     */   private static final long[] mk_tokenSet_14()
/* 3495:     */   {
/* 3496:2508 */     long[] arrayOfLong = { 287952805300282880L, 576460746129407998L, 0L, 0L, 0L };
/* 3497:2509 */     return arrayOfLong;
/* 3498:     */   }
/* 3499:     */   
/* 3500:2511 */   public static final BitSet _tokenSet_14 = new BitSet(mk_tokenSet_14());
/* 3501:     */   
/* 3502:     */   private static final long[] mk_tokenSet_15()
/* 3503:     */   {
/* 3504:2513 */     long[] arrayOfLong = { 2305843013508670976L, 0L, 0L, 0L, 0L };
/* 3505:2514 */     return arrayOfLong;
/* 3506:     */   }
/* 3507:     */   
/* 3508:2516 */   public static final BitSet _tokenSet_15 = new BitSet(mk_tokenSet_15());
/* 3509:     */   
/* 3510:     */   private static final long[] mk_tokenSet_16()
/* 3511:     */   {
/* 3512:2518 */     long[] arrayOfLong = { 2306051920717948416L, 536870912L, 0L, 0L, 0L };
/* 3513:2519 */     return arrayOfLong;
/* 3514:     */   }
/* 3515:     */   
/* 3516:2521 */   public static final BitSet _tokenSet_16 = new BitSet(mk_tokenSet_16());
/* 3517:     */   
/* 3518:     */   private static final long[] mk_tokenSet_17()
/* 3519:     */   {
/* 3520:2523 */     long[] arrayOfLong = { 208911504254464L, 536870912L, 0L, 0L, 0L };
/* 3521:2524 */     return arrayOfLong;
/* 3522:     */   }
/* 3523:     */   
/* 3524:2526 */   public static final BitSet _tokenSet_17 = new BitSet(mk_tokenSet_17());
/* 3525:     */   
/* 3526:     */   private static final long[] mk_tokenSet_18()
/* 3527:     */   {
/* 3528:2528 */     long[] arrayOfLong = { 1151051235328L, 576460746129407998L, 0L, 0L, 0L };
/* 3529:2529 */     return arrayOfLong;
/* 3530:     */   }
/* 3531:     */   
/* 3532:2531 */   public static final BitSet _tokenSet_18 = new BitSet(mk_tokenSet_18());
/* 3533:     */   
/* 3534:     */   private static final long[] mk_tokenSet_19()
/* 3535:     */   {
/* 3536:2533 */     long[] arrayOfLong = { 189120294954496L, 0L, 0L, 0L, 0L };
/* 3537:2534 */     return arrayOfLong;
/* 3538:     */   }
/* 3539:     */   
/* 3540:2536 */   public static final BitSet _tokenSet_19 = new BitSet(mk_tokenSet_19());
/* 3541:     */   
/* 3542:     */   private static final long[] mk_tokenSet_20()
/* 3543:     */   {
/* 3544:2538 */     long[] arrayOfLong = { 288139722277004800L, 576460746129407998L, 0L, 0L, 0L };
/* 3545:2539 */     return arrayOfLong;
/* 3546:     */   }
/* 3547:     */   
/* 3548:2541 */   public static final BitSet _tokenSet_20 = new BitSet(mk_tokenSet_20());
/* 3549:     */   
/* 3550:     */   private static final long[] mk_tokenSet_21()
/* 3551:     */   {
/* 3552:2543 */     long[] arrayOfLong = { 288049596683265536L, 576460746666278910L, 0L, 0L, 0L };
/* 3553:2544 */     return arrayOfLong;
/* 3554:     */   }
/* 3555:     */   
/* 3556:2546 */   public static final BitSet _tokenSet_21 = new BitSet(mk_tokenSet_21());
/* 3557:     */   
/* 3558:     */   private static final long[] mk_tokenSet_22()
/* 3559:     */   {
/* 3560:2548 */     long[] arrayOfLong = { 287960536241415680L, 576460745995190270L, 0L, 0L, 0L };
/* 3561:2549 */     return arrayOfLong;
/* 3562:     */   }
/* 3563:     */   
/* 3564:2551 */   public static final BitSet _tokenSet_22 = new BitSet(mk_tokenSet_22());
/* 3565:     */   
/* 3566:     */   private static final long[] mk_tokenSet_23()
/* 3567:     */   {
/* 3568:2553 */     long[] arrayOfLong = { 287958337218160128L, 576460745995190270L, 0L, 0L, 0L };
/* 3569:2554 */     return arrayOfLong;
/* 3570:     */   }
/* 3571:     */   
/* 3572:2556 */   public static final BitSet _tokenSet_23 = new BitSet(mk_tokenSet_23());
/* 3573:     */   
/* 3574:     */   private static final long[] mk_tokenSet_24()
/* 3575:     */   {
/* 3576:2558 */     long[] arrayOfLong = { 288228817078593024L, 576460746532061182L, 0L, 0L, 0L };
/* 3577:2559 */     return arrayOfLong;
/* 3578:     */   }
/* 3579:     */   
/* 3580:2561 */   public static final BitSet _tokenSet_24 = new BitSet(mk_tokenSet_24());
/* 3581:     */   
/* 3582:     */   private static final long[] mk_tokenSet_25()
/* 3583:     */   {
/* 3584:2563 */     long[] arrayOfLong = { 288158448334415360L, 576460746532061182L, 0L, 0L, 0L };
/* 3585:2564 */     return arrayOfLong;
/* 3586:     */   }
/* 3587:     */   
/* 3588:2566 */   public static final BitSet _tokenSet_25 = new BitSet(mk_tokenSet_25());
/* 3589:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.actions.python.ActionLexer
 * JD-Core Version:    0.7.0.1
 */