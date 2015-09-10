/*   1:    */ package org.hibernate.sql.ordering.antlr;
/*   2:    */ 
/*   3:    */ import antlr.ANTLRHashString;
/*   4:    */ import antlr.ANTLRStringBuffer;
/*   5:    */ import antlr.ByteBuffer;
/*   6:    */ import antlr.CharBuffer;
/*   7:    */ import antlr.CharScanner;
/*   8:    */ import antlr.CharStreamException;
/*   9:    */ import antlr.CharStreamIOException;
/*  10:    */ import antlr.InputBuffer;
/*  11:    */ import antlr.LexerSharedInputState;
/*  12:    */ import antlr.NoViableAltForCharException;
/*  13:    */ import antlr.RecognitionException;
/*  14:    */ import antlr.Token;
/*  15:    */ import antlr.TokenStream;
/*  16:    */ import antlr.TokenStreamException;
/*  17:    */ import antlr.TokenStreamIOException;
/*  18:    */ import antlr.TokenStreamRecognitionException;
/*  19:    */ import antlr.collections.impl.BitSet;
/*  20:    */ import java.io.InputStream;
/*  21:    */ import java.io.Reader;
/*  22:    */ import java.util.Hashtable;
/*  23:    */ 
/*  24:    */ public class GeneratedOrderByLexer
/*  25:    */   extends CharScanner
/*  26:    */   implements OrderByTemplateTokenTypes, TokenStream
/*  27:    */ {
/*  28:    */   public GeneratedOrderByLexer(InputStream in)
/*  29:    */   {
/*  30: 62 */     this(new ByteBuffer(in));
/*  31:    */   }
/*  32:    */   
/*  33:    */   public GeneratedOrderByLexer(Reader in)
/*  34:    */   {
/*  35: 65 */     this(new CharBuffer(in));
/*  36:    */   }
/*  37:    */   
/*  38:    */   public GeneratedOrderByLexer(InputBuffer ib)
/*  39:    */   {
/*  40: 68 */     this(new LexerSharedInputState(ib));
/*  41:    */   }
/*  42:    */   
/*  43:    */   public GeneratedOrderByLexer(LexerSharedInputState state)
/*  44:    */   {
/*  45: 71 */     super(state);
/*  46: 72 */     this.caseSensitiveLiterals = false;
/*  47: 73 */     setCaseSensitive(false);
/*  48: 74 */     this.literals = new Hashtable();
/*  49: 75 */     this.literals.put(new ANTLRHashString("asc", this), new Integer(13));
/*  50: 76 */     this.literals.put(new ANTLRHashString("ascending", this), new Integer(25));
/*  51: 77 */     this.literals.put(new ANTLRHashString("collate", this), new Integer(12));
/*  52: 78 */     this.literals.put(new ANTLRHashString("descending", this), new Integer(26));
/*  53: 79 */     this.literals.put(new ANTLRHashString("desc", this), new Integer(14));
/*  54:    */   }
/*  55:    */   
/*  56:    */   public Token nextToken()
/*  57:    */     throws TokenStreamException
/*  58:    */   {
/*  59: 83 */     Token theRetToken = null;
/*  60:    */     for (;;)
/*  61:    */     {
/*  62: 86 */       Token _token = null;
/*  63: 87 */       int _ttype = 0;
/*  64: 88 */       resetText();
/*  65:    */       try
/*  66:    */       {
/*  67: 91 */         switch (LA(1))
/*  68:    */         {
/*  69:    */         case '(': 
/*  70: 94 */           mOPEN_PAREN(true);
/*  71: 95 */           theRetToken = this._returnToken;
/*  72: 96 */           break;
/*  73:    */         case ')': 
/*  74:100 */           mCLOSE_PAREN(true);
/*  75:101 */           theRetToken = this._returnToken;
/*  76:102 */           break;
/*  77:    */         case ',': 
/*  78:106 */           mCOMMA(true);
/*  79:107 */           theRetToken = this._returnToken;
/*  80:108 */           break;
/*  81:    */         case '`': 
/*  82:112 */           mHARD_QUOTE(true);
/*  83:113 */           theRetToken = this._returnToken;
/*  84:114 */           break;
/*  85:    */         case '\'': 
/*  86:118 */           mQUOTED_STRING(true);
/*  87:119 */           theRetToken = this._returnToken;
/*  88:120 */           break;
/*  89:    */         case '.': 
/*  90:    */         case '0': 
/*  91:    */         case '1': 
/*  92:    */         case '2': 
/*  93:    */         case '3': 
/*  94:    */         case '4': 
/*  95:    */         case '5': 
/*  96:    */         case '6': 
/*  97:    */         case '7': 
/*  98:    */         case '8': 
/*  99:    */         case '9': 
/* 100:126 */           mNUM_INT(true);
/* 101:127 */           theRetToken = this._returnToken;
/* 102:128 */           break;
/* 103:    */         case '\t': 
/* 104:    */         case '\n': 
/* 105:    */         case '\r': 
/* 106:    */         case ' ': 
/* 107:132 */           mWS(true);
/* 108:133 */           theRetToken = this._returnToken;
/* 109:134 */           break;
/* 110:    */         case '\013': 
/* 111:    */         case '\f': 
/* 112:    */         case '\016': 
/* 113:    */         case '\017': 
/* 114:    */         case '\020': 
/* 115:    */         case '\021': 
/* 116:    */         case '\022': 
/* 117:    */         case '\023': 
/* 118:    */         case '\024': 
/* 119:    */         case '\025': 
/* 120:    */         case '\026': 
/* 121:    */         case '\027': 
/* 122:    */         case '\030': 
/* 123:    */         case '\031': 
/* 124:    */         case '\032': 
/* 125:    */         case '\033': 
/* 126:    */         case '\034': 
/* 127:    */         case '\035': 
/* 128:    */         case '\036': 
/* 129:    */         case '\037': 
/* 130:    */         case '!': 
/* 131:    */         case '"': 
/* 132:    */         case '#': 
/* 133:    */         case '$': 
/* 134:    */         case '%': 
/* 135:    */         case '&': 
/* 136:    */         case '*': 
/* 137:    */         case '+': 
/* 138:    */         case '-': 
/* 139:    */         case '/': 
/* 140:    */         case ':': 
/* 141:    */         case ';': 
/* 142:    */         case '<': 
/* 143:    */         case '=': 
/* 144:    */         case '>': 
/* 145:    */         case '?': 
/* 146:    */         case '@': 
/* 147:    */         case 'A': 
/* 148:    */         case 'B': 
/* 149:    */         case 'C': 
/* 150:    */         case 'D': 
/* 151:    */         case 'E': 
/* 152:    */         case 'F': 
/* 153:    */         case 'G': 
/* 154:    */         case 'H': 
/* 155:    */         case 'I': 
/* 156:    */         case 'J': 
/* 157:    */         case 'K': 
/* 158:    */         case 'L': 
/* 159:    */         case 'M': 
/* 160:    */         case 'N': 
/* 161:    */         case 'O': 
/* 162:    */         case 'P': 
/* 163:    */         case 'Q': 
/* 164:    */         case 'R': 
/* 165:    */         case 'S': 
/* 166:    */         case 'T': 
/* 167:    */         case 'U': 
/* 168:    */         case 'V': 
/* 169:    */         case 'W': 
/* 170:    */         case 'X': 
/* 171:    */         case 'Y': 
/* 172:    */         case 'Z': 
/* 173:    */         case '[': 
/* 174:    */         case '\\': 
/* 175:    */         case ']': 
/* 176:    */         case '^': 
/* 177:    */         case '_': 
/* 178:    */         default: 
/* 179:137 */           if (_tokenSet_0.member(LA(1)))
/* 180:    */           {
/* 181:138 */             mIDENT(true);
/* 182:139 */             theRetToken = this._returnToken;
/* 183:    */           }
/* 184:142 */           else if (LA(1) == 65535)
/* 185:    */           {
/* 186:142 */             uponEOF();this._returnToken = makeToken(1);
/* 187:    */           }
/* 188:    */           else
/* 189:    */           {
/* 190:143 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 191:    */           }
/* 192:    */           break;
/* 193:    */         }
/* 194:146 */         if (this._returnToken == null) {
/* 195:    */           continue;
/* 196:    */         }
/* 197:147 */         _ttype = this._returnToken.getType();
/* 198:148 */         this._returnToken.setType(_ttype);
/* 199:149 */         return this._returnToken;
/* 200:    */       }
/* 201:    */       catch (RecognitionException e)
/* 202:    */       {
/* 203:152 */         throw new TokenStreamRecognitionException(e);
/* 204:    */       }
/* 205:    */       catch (CharStreamException cse)
/* 206:    */       {
/* 207:156 */         if ((cse instanceof CharStreamIOException)) {
/* 208:157 */           throw new TokenStreamIOException(((CharStreamIOException)cse).io);
/* 209:    */         }
/* 210:160 */         throw new TokenStreamException(cse.getMessage());
/* 211:    */       }
/* 212:    */     }
/* 213:    */   }
/* 214:    */   
/* 215:    */   public final void mOPEN_PAREN(boolean _createToken)
/* 216:    */     throws RecognitionException, CharStreamException, TokenStreamException
/* 217:    */   {
/* 218:167 */     Token _token = null;int _begin = this.text.length();
/* 219:168 */     int _ttype = 18;
/* 220:    */     
/* 221:    */ 
/* 222:171 */     match('(');
/* 223:172 */     if ((_createToken) && (_token == null) && (_ttype != -1))
/* 224:    */     {
/* 225:173 */       _token = makeToken(_ttype);
/* 226:174 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/* 227:    */     }
/* 228:176 */     this._returnToken = _token;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public final void mCLOSE_PAREN(boolean _createToken)
/* 232:    */     throws RecognitionException, CharStreamException, TokenStreamException
/* 233:    */   {
/* 234:180 */     Token _token = null;int _begin = this.text.length();
/* 235:181 */     int _ttype = 19;
/* 236:    */     
/* 237:    */ 
/* 238:184 */     match(')');
/* 239:185 */     if ((_createToken) && (_token == null) && (_ttype != -1))
/* 240:    */     {
/* 241:186 */       _token = makeToken(_ttype);
/* 242:187 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/* 243:    */     }
/* 244:189 */     this._returnToken = _token;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public final void mCOMMA(boolean _createToken)
/* 248:    */     throws RecognitionException, CharStreamException, TokenStreamException
/* 249:    */   {
/* 250:193 */     Token _token = null;int _begin = this.text.length();
/* 251:194 */     int _ttype = 15;
/* 252:    */     
/* 253:    */ 
/* 254:197 */     match(',');
/* 255:198 */     if ((_createToken) && (_token == null) && (_ttype != -1))
/* 256:    */     {
/* 257:199 */       _token = makeToken(_ttype);
/* 258:200 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/* 259:    */     }
/* 260:202 */     this._returnToken = _token;
/* 261:    */   }
/* 262:    */   
/* 263:    */   public final void mHARD_QUOTE(boolean _createToken)
/* 264:    */     throws RecognitionException, CharStreamException, TokenStreamException
/* 265:    */   {
/* 266:206 */     Token _token = null;int _begin = this.text.length();
/* 267:207 */     int _ttype = 16;
/* 268:    */     
/* 269:    */ 
/* 270:210 */     match('`');
/* 271:211 */     if ((_createToken) && (_token == null) && (_ttype != -1))
/* 272:    */     {
/* 273:212 */       _token = makeToken(_ttype);
/* 274:213 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/* 275:    */     }
/* 276:215 */     this._returnToken = _token;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public final void mIDENT(boolean _createToken)
/* 280:    */     throws RecognitionException, CharStreamException, TokenStreamException
/* 281:    */   {
/* 282:219 */     Token _token = null;int _begin = this.text.length();
/* 283:220 */     int _ttype = 17;
/* 284:    */     
/* 285:    */ 
/* 286:223 */     mID_START_LETTER(false);
/* 287:227 */     while (_tokenSet_1.member(LA(1))) {
/* 288:228 */       mID_LETTER(false);
/* 289:    */     }
/* 290:236 */     _ttype = testLiteralsTable(_ttype);
/* 291:237 */     if ((_createToken) && (_token == null) && (_ttype != -1))
/* 292:    */     {
/* 293:238 */       _token = makeToken(_ttype);
/* 294:239 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/* 295:    */     }
/* 296:241 */     this._returnToken = _token;
/* 297:    */   }
/* 298:    */   
/* 299:    */   protected final void mID_START_LETTER(boolean _createToken)
/* 300:    */     throws RecognitionException, CharStreamException, TokenStreamException
/* 301:    */   {
/* 302:245 */     Token _token = null;int _begin = this.text.length();
/* 303:246 */     int _ttype = 27;
/* 304:249 */     switch (LA(1))
/* 305:    */     {
/* 306:    */     case '_': 
/* 307:252 */       match('_');
/* 308:253 */       break;
/* 309:    */     case '$': 
/* 310:257 */       match('$');
/* 311:258 */       break;
/* 312:    */     case 'a': 
/* 313:    */     case 'b': 
/* 314:    */     case 'c': 
/* 315:    */     case 'd': 
/* 316:    */     case 'e': 
/* 317:    */     case 'f': 
/* 318:    */     case 'g': 
/* 319:    */     case 'h': 
/* 320:    */     case 'i': 
/* 321:    */     case 'j': 
/* 322:    */     case 'k': 
/* 323:    */     case 'l': 
/* 324:    */     case 'm': 
/* 325:    */     case 'n': 
/* 326:    */     case 'o': 
/* 327:    */     case 'p': 
/* 328:    */     case 'q': 
/* 329:    */     case 'r': 
/* 330:    */     case 's': 
/* 331:    */     case 't': 
/* 332:    */     case 'u': 
/* 333:    */     case 'v': 
/* 334:    */     case 'w': 
/* 335:    */     case 'x': 
/* 336:    */     case 'y': 
/* 337:    */     case 'z': 
/* 338:268 */       matchRange('a', 'z');
/* 339:269 */       break;
/* 340:    */     case '%': 
/* 341:    */     case '&': 
/* 342:    */     case '\'': 
/* 343:    */     case '(': 
/* 344:    */     case ')': 
/* 345:    */     case '*': 
/* 346:    */     case '+': 
/* 347:    */     case ',': 
/* 348:    */     case '-': 
/* 349:    */     case '.': 
/* 350:    */     case '/': 
/* 351:    */     case '0': 
/* 352:    */     case '1': 
/* 353:    */     case '2': 
/* 354:    */     case '3': 
/* 355:    */     case '4': 
/* 356:    */     case '5': 
/* 357:    */     case '6': 
/* 358:    */     case '7': 
/* 359:    */     case '8': 
/* 360:    */     case '9': 
/* 361:    */     case ':': 
/* 362:    */     case ';': 
/* 363:    */     case '<': 
/* 364:    */     case '=': 
/* 365:    */     case '>': 
/* 366:    */     case '?': 
/* 367:    */     case '@': 
/* 368:    */     case 'A': 
/* 369:    */     case 'B': 
/* 370:    */     case 'C': 
/* 371:    */     case 'D': 
/* 372:    */     case 'E': 
/* 373:    */     case 'F': 
/* 374:    */     case 'G': 
/* 375:    */     case 'H': 
/* 376:    */     case 'I': 
/* 377:    */     case 'J': 
/* 378:    */     case 'K': 
/* 379:    */     case 'L': 
/* 380:    */     case 'M': 
/* 381:    */     case 'N': 
/* 382:    */     case 'O': 
/* 383:    */     case 'P': 
/* 384:    */     case 'Q': 
/* 385:    */     case 'R': 
/* 386:    */     case 'S': 
/* 387:    */     case 'T': 
/* 388:    */     case 'U': 
/* 389:    */     case 'V': 
/* 390:    */     case 'W': 
/* 391:    */     case 'X': 
/* 392:    */     case 'Y': 
/* 393:    */     case 'Z': 
/* 394:    */     case '[': 
/* 395:    */     case '\\': 
/* 396:    */     case ']': 
/* 397:    */     case '^': 
/* 398:    */     case '`': 
/* 399:    */     default: 
/* 400:272 */       if ((LA(1) >= '') && (LA(1) <= 65534)) {
/* 401:273 */         matchRange('', 65534);
/* 402:    */       } else {
/* 403:276 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 404:    */       }
/* 405:    */       break;
/* 406:    */     }
/* 407:279 */     if ((_createToken) && (_token == null) && (_ttype != -1))
/* 408:    */     {
/* 409:280 */       _token = makeToken(_ttype);
/* 410:281 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/* 411:    */     }
/* 412:283 */     this._returnToken = _token;
/* 413:    */   }
/* 414:    */   
/* 415:    */   protected final void mID_LETTER(boolean _createToken)
/* 416:    */     throws RecognitionException, CharStreamException, TokenStreamException
/* 417:    */   {
/* 418:287 */     Token _token = null;int _begin = this.text.length();
/* 419:288 */     int _ttype = 28;
/* 420:291 */     if (_tokenSet_0.member(LA(1))) {
/* 421:292 */       mID_START_LETTER(false);
/* 422:294 */     } else if ((LA(1) >= '0') && (LA(1) <= '9')) {
/* 423:295 */       matchRange('0', '9');
/* 424:    */     } else {
/* 425:298 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 426:    */     }
/* 427:301 */     if ((_createToken) && (_token == null) && (_ttype != -1))
/* 428:    */     {
/* 429:302 */       _token = makeToken(_ttype);
/* 430:303 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/* 431:    */     }
/* 432:305 */     this._returnToken = _token;
/* 433:    */   }
/* 434:    */   
/* 435:    */   public final void mQUOTED_STRING(boolean _createToken)
/* 436:    */     throws RecognitionException, CharStreamException, TokenStreamException
/* 437:    */   {
/* 438:309 */     Token _token = null;int _begin = this.text.length();
/* 439:310 */     int _ttype = 24;
/* 440:    */     
/* 441:    */ 
/* 442:313 */     match('\'');
/* 443:    */     for (;;)
/* 444:    */     {
/* 445:317 */       boolean synPredMatched44 = false;
/* 446:318 */       if ((LA(1) == '\'') && (LA(2) == '\''))
/* 447:    */       {
/* 448:319 */         int _m44 = mark();
/* 449:320 */         synPredMatched44 = true;
/* 450:321 */         this.inputState.guessing += 1;
/* 451:    */         try
/* 452:    */         {
/* 453:324 */           mESCqs(false);
/* 454:    */         }
/* 455:    */         catch (RecognitionException pe)
/* 456:    */         {
/* 457:328 */           synPredMatched44 = false;
/* 458:    */         }
/* 459:330 */         rewind(_m44);
/* 460:331 */         this.inputState.guessing -= 1;
/* 461:    */       }
/* 462:333 */       if (synPredMatched44)
/* 463:    */       {
/* 464:334 */         mESCqs(false);
/* 465:    */       }
/* 466:    */       else
/* 467:    */       {
/* 468:336 */         if (!_tokenSet_2.member(LA(1))) {
/* 469:    */           break;
/* 470:    */         }
/* 471:337 */         matchNot('\'');
/* 472:    */       }
/* 473:    */     }
/* 474:345 */     match('\'');
/* 475:346 */     if ((_createToken) && (_token == null) && (_ttype != -1))
/* 476:    */     {
/* 477:347 */       _token = makeToken(_ttype);
/* 478:348 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/* 479:    */     }
/* 480:350 */     this._returnToken = _token;
/* 481:    */   }
/* 482:    */   
/* 483:    */   protected final void mESCqs(boolean _createToken)
/* 484:    */     throws RecognitionException, CharStreamException, TokenStreamException
/* 485:    */   {
/* 486:354 */     Token _token = null;int _begin = this.text.length();
/* 487:355 */     int _ttype = 29;
/* 488:    */     
/* 489:    */ 
/* 490:358 */     match('\'');
/* 491:359 */     match('\'');
/* 492:360 */     if ((_createToken) && (_token == null) && (_ttype != -1))
/* 493:    */     {
/* 494:361 */       _token = makeToken(_ttype);
/* 495:362 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/* 496:    */     }
/* 497:364 */     this._returnToken = _token;
/* 498:    */   }
/* 499:    */   
/* 500:    */   public final void mNUM_INT(boolean _createToken)
/* 501:    */     throws RecognitionException, CharStreamException, TokenStreamException
/* 502:    */   {
/* 503:368 */     Token _token = null;int _begin = this.text.length();
/* 504:369 */     int _ttype = 22;
/* 505:    */     
/* 506:371 */     Token f1 = null;
/* 507:372 */     Token f2 = null;
/* 508:373 */     Token f3 = null;
/* 509:374 */     Token f4 = null;
/* 510:375 */     boolean isDecimal = false;Token t = null;
/* 511:377 */     switch (LA(1))
/* 512:    */     {
/* 513:    */     case '.': 
/* 514:380 */       match('.');
/* 515:381 */       if (this.inputState.guessing == 0) {
/* 516:382 */         _ttype = 9;
/* 517:    */       }
/* 518:385 */       if ((LA(1) >= '0') && (LA(1) <= '9'))
/* 519:    */       {
/* 520:387 */         int _cnt50 = 0;
/* 521:    */         for (;;)
/* 522:    */         {
/* 523:390 */           if ((LA(1) >= '0') && (LA(1) <= '9'))
/* 524:    */           {
/* 525:391 */             matchRange('0', '9');
/* 526:    */           }
/* 527:    */           else
/* 528:    */           {
/* 529:394 */             if (_cnt50 >= 1) {
/* 530:    */               break;
/* 531:    */             }
/* 532:394 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 533:    */           }
/* 534:397 */           _cnt50++;
/* 535:    */         }
/* 536:401 */         if (LA(1) == 'e') {
/* 537:402 */           mEXPONENT(false);
/* 538:    */         }
/* 539:409 */         if ((LA(1) == 'd') || (LA(1) == 'f'))
/* 540:    */         {
/* 541:410 */           mFLOAT_SUFFIX(true);
/* 542:411 */           f1 = this._returnToken;
/* 543:412 */           if (this.inputState.guessing == 0) {
/* 544:413 */             t = f1;
/* 545:    */           }
/* 546:    */         }
/* 547:420 */         if (this.inputState.guessing == 0) {
/* 548:422 */           if ((t != null) && (t.getText().toUpperCase().indexOf('F') >= 0)) {
/* 549:424 */             _ttype = 21;
/* 550:    */           } else {
/* 551:428 */             _ttype = 20;
/* 552:    */           }
/* 553:    */         }
/* 554:    */       }
/* 555:    */       break;
/* 556:    */     case '0': 
/* 557:    */     case '1': 
/* 558:    */     case '2': 
/* 559:    */     case '3': 
/* 560:    */     case '4': 
/* 561:    */     case '5': 
/* 562:    */     case '6': 
/* 563:    */     case '7': 
/* 564:    */     case '8': 
/* 565:    */     case '9': 
/* 566:444 */       switch (LA(1))
/* 567:    */       {
/* 568:    */       case '0': 
/* 569:447 */         match('0');
/* 570:448 */         if (this.inputState.guessing == 0) {
/* 571:449 */           isDecimal = true;
/* 572:    */         }
/* 573:452 */         switch (LA(1))
/* 574:    */         {
/* 575:    */         case 'x': 
/* 576:456 */           match('x');
/* 577:    */           
/* 578:    */ 
/* 579:459 */           int _cnt57 = 0;
/* 580:    */           for (;;)
/* 581:    */           {
/* 582:462 */             if (_tokenSet_3.member(LA(1)))
/* 583:    */             {
/* 584:463 */               mHEX_DIGIT(false);
/* 585:    */             }
/* 586:    */             else
/* 587:    */             {
/* 588:466 */               if (_cnt57 >= 1) {
/* 589:    */                 break;
/* 590:    */               }
/* 591:466 */               throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 592:    */             }
/* 593:469 */             _cnt57++;
/* 594:    */           }
/* 595:472 */           break;
/* 596:    */         case '0': 
/* 597:    */         case '1': 
/* 598:    */         case '2': 
/* 599:    */         case '3': 
/* 600:    */         case '4': 
/* 601:    */         case '5': 
/* 602:    */         case '6': 
/* 603:    */         case '7': 
/* 604:478 */           int _cnt59 = 0;
/* 605:    */           for (;;)
/* 606:    */           {
/* 607:481 */             if ((LA(1) >= '0') && (LA(1) <= '7'))
/* 608:    */             {
/* 609:482 */               matchRange('0', '7');
/* 610:    */             }
/* 611:    */             else
/* 612:    */             {
/* 613:485 */               if (_cnt59 >= 1) {
/* 614:    */                 break;
/* 615:    */               }
/* 616:485 */               throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 617:    */             }
/* 618:488 */             _cnt59++;
/* 619:    */           }
/* 620:    */         }
/* 621:498 */         break;
/* 622:    */       case '1': 
/* 623:    */       case '2': 
/* 624:    */       case '3': 
/* 625:    */       case '4': 
/* 626:    */       case '5': 
/* 627:    */       case '6': 
/* 628:    */       case '7': 
/* 629:    */       case '8': 
/* 630:    */       case '9': 
/* 631:505 */         matchRange('1', '9');
/* 632:510 */         while ((LA(1) >= '0') && (LA(1) <= '9')) {
/* 633:511 */           matchRange('0', '9');
/* 634:    */         }
/* 635:519 */         if (this.inputState.guessing == 0) {
/* 636:520 */           isDecimal = true;
/* 637:    */         }
/* 638:    */         break;
/* 639:    */       default: 
/* 640:526 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 641:    */       }
/* 642:531 */       if (LA(1) == 'l')
/* 643:    */       {
/* 644:533 */         match('l');
/* 645:535 */         if (this.inputState.guessing == 0) {
/* 646:536 */           _ttype = 23;
/* 647:    */         }
/* 648:    */       }
/* 649:539 */       else if ((_tokenSet_4.member(LA(1))) && (isDecimal))
/* 650:    */       {
/* 651:541 */         switch (LA(1))
/* 652:    */         {
/* 653:    */         case '.': 
/* 654:544 */           match('.');
/* 655:548 */           while ((LA(1) >= '0') && (LA(1) <= '9')) {
/* 656:549 */             matchRange('0', '9');
/* 657:    */           }
/* 658:558 */           if (LA(1) == 'e') {
/* 659:559 */             mEXPONENT(false);
/* 660:    */           }
/* 661:566 */           if ((LA(1) == 'd') || (LA(1) == 'f'))
/* 662:    */           {
/* 663:567 */             mFLOAT_SUFFIX(true);
/* 664:568 */             f2 = this._returnToken;
/* 665:569 */             if (this.inputState.guessing == 0) {
/* 666:570 */               t = f2;
/* 667:    */             }
/* 668:    */           }
/* 669:    */           break;
/* 670:    */         case 'e': 
/* 671:581 */           mEXPONENT(false);
/* 672:583 */           if ((LA(1) == 'd') || (LA(1) == 'f'))
/* 673:    */           {
/* 674:584 */             mFLOAT_SUFFIX(true);
/* 675:585 */             f3 = this._returnToken;
/* 676:586 */             if (this.inputState.guessing == 0) {
/* 677:587 */               t = f3;
/* 678:    */             }
/* 679:    */           }
/* 680:    */           break;
/* 681:    */         case 'd': 
/* 682:    */         case 'f': 
/* 683:598 */           mFLOAT_SUFFIX(true);
/* 684:599 */           f4 = this._returnToken;
/* 685:600 */           if (this.inputState.guessing == 0) {
/* 686:601 */             t = f4;
/* 687:    */           }
/* 688:    */           break;
/* 689:    */         default: 
/* 690:607 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 691:    */         }
/* 692:611 */         if (this.inputState.guessing == 0) {
/* 693:613 */           if ((t != null) && (t.getText().toUpperCase().indexOf('F') >= 0)) {
/* 694:615 */             _ttype = 21;
/* 695:    */           } else {
/* 696:619 */             _ttype = 20;
/* 697:    */           }
/* 698:    */         }
/* 699:    */       }
/* 700:    */       break;
/* 701:    */     case '/': 
/* 702:    */     default: 
/* 703:632 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 704:    */     }
/* 705:635 */     if ((_createToken) && (_token == null) && (_ttype != -1))
/* 706:    */     {
/* 707:636 */       _token = makeToken(_ttype);
/* 708:637 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/* 709:    */     }
/* 710:639 */     this._returnToken = _token;
/* 711:    */   }
/* 712:    */   
/* 713:    */   protected final void mEXPONENT(boolean _createToken)
/* 714:    */     throws RecognitionException, CharStreamException, TokenStreamException
/* 715:    */   {
/* 716:643 */     Token _token = null;int _begin = this.text.length();
/* 717:644 */     int _ttype = 31;
/* 718:    */     
/* 719:    */ 
/* 720:    */ 
/* 721:648 */     match('e');
/* 722:651 */     switch (LA(1))
/* 723:    */     {
/* 724:    */     case '+': 
/* 725:654 */       match('+');
/* 726:655 */       break;
/* 727:    */     case '-': 
/* 728:659 */       match('-');
/* 729:660 */       break;
/* 730:    */     case '0': 
/* 731:    */     case '1': 
/* 732:    */     case '2': 
/* 733:    */     case '3': 
/* 734:    */     case '4': 
/* 735:    */     case '5': 
/* 736:    */     case '6': 
/* 737:    */     case '7': 
/* 738:    */     case '8': 
/* 739:    */     case '9': 
/* 740:    */       break;
/* 741:    */     case ',': 
/* 742:    */     case '.': 
/* 743:    */     case '/': 
/* 744:    */     default: 
/* 745:670 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 746:    */     }
/* 747:675 */     int _cnt77 = 0;
/* 748:    */     for (;;)
/* 749:    */     {
/* 750:678 */       if ((LA(1) >= '0') && (LA(1) <= '9'))
/* 751:    */       {
/* 752:679 */         matchRange('0', '9');
/* 753:    */       }
/* 754:    */       else
/* 755:    */       {
/* 756:682 */         if (_cnt77 >= 1) {
/* 757:    */           break;
/* 758:    */         }
/* 759:682 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 760:    */       }
/* 761:685 */       _cnt77++;
/* 762:    */     }
/* 763:688 */     if ((_createToken) && (_token == null) && (_ttype != -1))
/* 764:    */     {
/* 765:689 */       _token = makeToken(_ttype);
/* 766:690 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/* 767:    */     }
/* 768:692 */     this._returnToken = _token;
/* 769:    */   }
/* 770:    */   
/* 771:    */   protected final void mFLOAT_SUFFIX(boolean _createToken)
/* 772:    */     throws RecognitionException, CharStreamException, TokenStreamException
/* 773:    */   {
/* 774:696 */     Token _token = null;int _begin = this.text.length();
/* 775:697 */     int _ttype = 32;
/* 776:700 */     switch (LA(1))
/* 777:    */     {
/* 778:    */     case 'f': 
/* 779:703 */       match('f');
/* 780:704 */       break;
/* 781:    */     case 'd': 
/* 782:708 */       match('d');
/* 783:709 */       break;
/* 784:    */     default: 
/* 785:713 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 786:    */     }
/* 787:716 */     if ((_createToken) && (_token == null) && (_ttype != -1))
/* 788:    */     {
/* 789:717 */       _token = makeToken(_ttype);
/* 790:718 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/* 791:    */     }
/* 792:720 */     this._returnToken = _token;
/* 793:    */   }
/* 794:    */   
/* 795:    */   protected final void mHEX_DIGIT(boolean _createToken)
/* 796:    */     throws RecognitionException, CharStreamException, TokenStreamException
/* 797:    */   {
/* 798:724 */     Token _token = null;int _begin = this.text.length();
/* 799:725 */     int _ttype = 30;
/* 800:729 */     switch (LA(1))
/* 801:    */     {
/* 802:    */     case '0': 
/* 803:    */     case '1': 
/* 804:    */     case '2': 
/* 805:    */     case '3': 
/* 806:    */     case '4': 
/* 807:    */     case '5': 
/* 808:    */     case '6': 
/* 809:    */     case '7': 
/* 810:    */     case '8': 
/* 811:    */     case '9': 
/* 812:734 */       matchRange('0', '9');
/* 813:735 */       break;
/* 814:    */     case 'a': 
/* 815:    */     case 'b': 
/* 816:    */     case 'c': 
/* 817:    */     case 'd': 
/* 818:    */     case 'e': 
/* 819:    */     case 'f': 
/* 820:740 */       matchRange('a', 'f');
/* 821:741 */       break;
/* 822:    */     case ':': 
/* 823:    */     case ';': 
/* 824:    */     case '<': 
/* 825:    */     case '=': 
/* 826:    */     case '>': 
/* 827:    */     case '?': 
/* 828:    */     case '@': 
/* 829:    */     case 'A': 
/* 830:    */     case 'B': 
/* 831:    */     case 'C': 
/* 832:    */     case 'D': 
/* 833:    */     case 'E': 
/* 834:    */     case 'F': 
/* 835:    */     case 'G': 
/* 836:    */     case 'H': 
/* 837:    */     case 'I': 
/* 838:    */     case 'J': 
/* 839:    */     case 'K': 
/* 840:    */     case 'L': 
/* 841:    */     case 'M': 
/* 842:    */     case 'N': 
/* 843:    */     case 'O': 
/* 844:    */     case 'P': 
/* 845:    */     case 'Q': 
/* 846:    */     case 'R': 
/* 847:    */     case 'S': 
/* 848:    */     case 'T': 
/* 849:    */     case 'U': 
/* 850:    */     case 'V': 
/* 851:    */     case 'W': 
/* 852:    */     case 'X': 
/* 853:    */     case 'Y': 
/* 854:    */     case 'Z': 
/* 855:    */     case '[': 
/* 856:    */     case '\\': 
/* 857:    */     case ']': 
/* 858:    */     case '^': 
/* 859:    */     case '_': 
/* 860:    */     case '`': 
/* 861:    */     default: 
/* 862:745 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 863:    */     }
/* 864:749 */     if ((_createToken) && (_token == null) && (_ttype != -1))
/* 865:    */     {
/* 866:750 */       _token = makeToken(_ttype);
/* 867:751 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/* 868:    */     }
/* 869:753 */     this._returnToken = _token;
/* 870:    */   }
/* 871:    */   
/* 872:    */   public final void mWS(boolean _createToken)
/* 873:    */     throws RecognitionException, CharStreamException, TokenStreamException
/* 874:    */   {
/* 875:757 */     Token _token = null;int _begin = this.text.length();
/* 876:758 */     int _ttype = 33;
/* 877:762 */     switch (LA(1))
/* 878:    */     {
/* 879:    */     case ' ': 
/* 880:765 */       match(' ');
/* 881:766 */       break;
/* 882:    */     case '\t': 
/* 883:770 */       match('\t');
/* 884:771 */       break;
/* 885:    */     case '\n': 
/* 886:775 */       match('\n');
/* 887:776 */       if (this.inputState.guessing == 0) {
/* 888:777 */         newline();
/* 889:    */       }
/* 890:    */       break;
/* 891:    */     default: 
/* 892:782 */       if ((LA(1) == '\r') && (LA(2) == '\n'))
/* 893:    */       {
/* 894:783 */         match('\r');
/* 895:784 */         match('\n');
/* 896:785 */         if (this.inputState.guessing == 0) {
/* 897:786 */           newline();
/* 898:    */         }
/* 899:    */       }
/* 900:789 */       else if (LA(1) == '\r')
/* 901:    */       {
/* 902:790 */         match('\r');
/* 903:791 */         if (this.inputState.guessing == 0) {
/* 904:792 */           newline();
/* 905:    */         }
/* 906:    */       }
/* 907:    */       else
/* 908:    */       {
/* 909:796 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 910:    */       }
/* 911:    */       break;
/* 912:    */     }
/* 913:800 */     if (this.inputState.guessing == 0) {
/* 914:801 */       _ttype = -1;
/* 915:    */     }
/* 916:803 */     if ((_createToken) && (_token == null) && (_ttype != -1))
/* 917:    */     {
/* 918:804 */       _token = makeToken(_ttype);
/* 919:805 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/* 920:    */     }
/* 921:807 */     this._returnToken = _token;
/* 922:    */   }
/* 923:    */   
/* 924:    */   private static final long[] mk_tokenSet_0()
/* 925:    */   {
/* 926:812 */     long[] data = new long[3072];
/* 927:813 */     data[0] = 68719476736L;
/* 928:814 */     data[1] = 576460745860972544L;
/* 929:815 */     for (int i = 2; i <= 1022; i++) {
/* 930:815 */       data[i] = -1L;
/* 931:    */     }
/* 932:816 */     data[1023] = 9223372036854775807L;
/* 933:817 */     return data;
/* 934:    */   }
/* 935:    */   
/* 936:819 */   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
/* 937:    */   
/* 938:    */   private static final long[] mk_tokenSet_1()
/* 939:    */   {
/* 940:821 */     long[] data = new long[3072];
/* 941:822 */     data[0] = 287948969894477824L;
/* 942:823 */     data[1] = 576460745860972544L;
/* 943:824 */     for (int i = 2; i <= 1022; i++) {
/* 944:824 */       data[i] = -1L;
/* 945:    */     }
/* 946:825 */     data[1023] = 9223372036854775807L;
/* 947:826 */     return data;
/* 948:    */   }
/* 949:    */   
/* 950:828 */   public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
/* 951:    */   
/* 952:    */   private static final long[] mk_tokenSet_2()
/* 953:    */   {
/* 954:830 */     long[] data = new long[2048];
/* 955:831 */     data[0] = -549755813889L;
/* 956:832 */     for (int i = 1; i <= 1022; i++) {
/* 957:832 */       data[i] = -1L;
/* 958:    */     }
/* 959:833 */     data[1023] = 9223372036854775807L;
/* 960:834 */     return data;
/* 961:    */   }
/* 962:    */   
/* 963:836 */   public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
/* 964:    */   
/* 965:    */   private static final long[] mk_tokenSet_3()
/* 966:    */   {
/* 967:838 */     long[] data = new long[1025];
/* 968:839 */     data[0] = 287948901175001088L;
/* 969:840 */     data[1] = 541165879296L;
/* 970:841 */     return data;
/* 971:    */   }
/* 972:    */   
/* 973:843 */   public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
/* 974:    */   
/* 975:    */   private static final long[] mk_tokenSet_4()
/* 976:    */   {
/* 977:845 */     long[] data = new long[1025];
/* 978:846 */     data[0] = 70368744177664L;
/* 979:847 */     data[1] = 481036337152L;
/* 980:848 */     return data;
/* 981:    */   }
/* 982:    */   
/* 983:850 */   public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
/* 984:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.sql.ordering.antlr.GeneratedOrderByLexer
 * JD-Core Version:    0.7.0.1
 */