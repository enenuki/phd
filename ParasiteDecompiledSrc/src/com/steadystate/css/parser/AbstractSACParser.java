/*   1:    */ package com.steadystate.css.parser;
/*   2:    */ 
/*   3:    */ import com.steadystate.css.parser.selectors.ConditionFactoryImpl;
/*   4:    */ import com.steadystate.css.parser.selectors.SelectorFactoryImpl;
/*   5:    */ import com.steadystate.css.sac.DocumentHandlerExt;
/*   6:    */ import com.steadystate.css.sac.TestCSSParseException;
/*   7:    */ import java.io.IOException;
/*   8:    */ import java.io.InputStreamReader;
/*   9:    */ import java.net.URL;
/*  10:    */ import java.text.MessageFormat;
/*  11:    */ import java.util.Locale;
/*  12:    */ import java.util.MissingResourceException;
/*  13:    */ import java.util.ResourceBundle;
/*  14:    */ import org.w3c.css.sac.CSSParseException;
/*  15:    */ import org.w3c.css.sac.ConditionFactory;
/*  16:    */ import org.w3c.css.sac.DocumentHandler;
/*  17:    */ import org.w3c.css.sac.ErrorHandler;
/*  18:    */ import org.w3c.css.sac.InputSource;
/*  19:    */ import org.w3c.css.sac.LexicalUnit;
/*  20:    */ import org.w3c.css.sac.Locator;
/*  21:    */ import org.w3c.css.sac.Parser;
/*  22:    */ import org.w3c.css.sac.SACMediaList;
/*  23:    */ import org.w3c.css.sac.Selector;
/*  24:    */ import org.w3c.css.sac.SelectorFactory;
/*  25:    */ import org.w3c.css.sac.SelectorList;
/*  26:    */ 
/*  27:    */ abstract class AbstractSACParser
/*  28:    */   implements Parser
/*  29:    */ {
/*  30: 62 */   private DocumentHandlerExt documentHandler = null;
/*  31: 63 */   private ErrorHandler errorHandler = null;
/*  32: 64 */   private InputSource source = null;
/*  33: 65 */   private Locale locale = null;
/*  34: 66 */   private SelectorFactory selectorFactory = null;
/*  35: 67 */   private ConditionFactory conditionFactory = null;
/*  36:    */   private ResourceBundle sacParserMessages;
/*  37:    */   
/*  38:    */   protected abstract Token getToken();
/*  39:    */   
/*  40:    */   protected DocumentHandlerExt getDocumentHandler()
/*  41:    */   {
/*  42: 73 */     if (this.documentHandler == null) {
/*  43: 75 */       setDocumentHandler(new HandlerBase());
/*  44:    */     }
/*  45: 77 */     return this.documentHandler;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void setDocumentHandler(DocumentHandler handler)
/*  49:    */   {
/*  50: 82 */     if ((handler instanceof DocumentHandlerExt)) {
/*  51: 84 */       this.documentHandler = ((DocumentHandlerExt)handler);
/*  52:    */     }
/*  53:    */   }
/*  54:    */   
/*  55:    */   protected ErrorHandler getErrorHandler()
/*  56:    */   {
/*  57: 90 */     if (this.errorHandler == null) {
/*  58: 92 */       setErrorHandler(new HandlerBase());
/*  59:    */     }
/*  60: 94 */     return this.errorHandler;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void setErrorHandler(ErrorHandler eh)
/*  64:    */   {
/*  65: 99 */     this.errorHandler = eh;
/*  66:    */   }
/*  67:    */   
/*  68:    */   protected InputSource getInputSource()
/*  69:    */   {
/*  70:104 */     return this.source;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setLocale(Locale locale)
/*  74:    */   {
/*  75:109 */     if (this.locale != locale) {
/*  76:111 */       this.sacParserMessages = null;
/*  77:    */     }
/*  78:113 */     this.locale = locale;
/*  79:    */   }
/*  80:    */   
/*  81:    */   protected Locale getLocale()
/*  82:    */   {
/*  83:118 */     if (this.locale == null) {
/*  84:120 */       setLocale(Locale.getDefault());
/*  85:    */     }
/*  86:122 */     return this.locale;
/*  87:    */   }
/*  88:    */   
/*  89:    */   protected SelectorFactory getSelectorFactory()
/*  90:    */   {
/*  91:127 */     if (this.selectorFactory == null) {
/*  92:129 */       this.selectorFactory = new SelectorFactoryImpl();
/*  93:    */     }
/*  94:131 */     return this.selectorFactory;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void setSelectorFactory(SelectorFactory selectorFactory)
/*  98:    */   {
/*  99:136 */     this.selectorFactory = selectorFactory;
/* 100:    */   }
/* 101:    */   
/* 102:    */   protected ConditionFactory getConditionFactory()
/* 103:    */   {
/* 104:141 */     if (this.conditionFactory == null) {
/* 105:143 */       this.conditionFactory = new ConditionFactoryImpl();
/* 106:    */     }
/* 107:145 */     return this.conditionFactory;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void setConditionFactory(ConditionFactory conditionFactory)
/* 111:    */   {
/* 112:150 */     this.conditionFactory = conditionFactory;
/* 113:    */   }
/* 114:    */   
/* 115:    */   protected ResourceBundle getSACParserMessages()
/* 116:    */   {
/* 117:155 */     if (this.sacParserMessages == null) {
/* 118:157 */       this.sacParserMessages = ResourceBundle.getBundle("com.steadystate.css.parser.SACParserMessages", getLocale());
/* 119:    */     }
/* 120:161 */     return this.sacParserMessages;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public Locator getLocator()
/* 124:    */   {
/* 125:166 */     return new LocatorImpl(getInputSource().getURI(), getToken() == null ? 0 : getToken().beginLine, getToken() == null ? 0 : getToken().beginColumn);
/* 126:    */   }
/* 127:    */   
/* 128:    */   protected String add_escapes(String str)
/* 129:    */   {
/* 130:173 */     StringBuilder retval = new StringBuilder();
/* 131:175 */     for (int i = 0; i < str.length(); i++) {
/* 132:177 */       switch (str.charAt(i))
/* 133:    */       {
/* 134:    */       case '\000': 
/* 135:    */         break;
/* 136:    */       case '\b': 
/* 137:182 */         retval.append("\\b");
/* 138:183 */         break;
/* 139:    */       case '\t': 
/* 140:185 */         retval.append("\\t");
/* 141:186 */         break;
/* 142:    */       case '\n': 
/* 143:188 */         retval.append("\\n");
/* 144:189 */         break;
/* 145:    */       case '\f': 
/* 146:191 */         retval.append("\\f");
/* 147:192 */         break;
/* 148:    */       case '\r': 
/* 149:194 */         retval.append("\\r");
/* 150:195 */         break;
/* 151:    */       case '"': 
/* 152:197 */         retval.append("\\\"");
/* 153:198 */         break;
/* 154:    */       case '\'': 
/* 155:200 */         retval.append("\\'");
/* 156:201 */         break;
/* 157:    */       case '\\': 
/* 158:203 */         retval.append("\\\\");
/* 159:204 */         break;
/* 160:    */       default: 
/* 161:    */         char ch;
/* 162:206 */         if (((ch = str.charAt(i)) < ' ') || (ch > '~'))
/* 163:    */         {
/* 164:208 */           String s = "0000" + Integer.toString(ch, 16);
/* 165:209 */           retval.append("\\u" + s.substring(s.length() - 4, s.length()));
/* 166:    */         }
/* 167:    */         else
/* 168:    */         {
/* 169:214 */           retval.append(ch);
/* 170:    */         }
/* 171:    */         break;
/* 172:    */       }
/* 173:    */     }
/* 174:219 */     return retval.toString();
/* 175:    */   }
/* 176:    */   
/* 177:    */   protected CSSParseException toCSSParseException(String key, ParseException e)
/* 178:    */   {
/* 179:224 */     String messagePattern1 = getSACParserMessages().getString("invalidExpectingOne");
/* 180:    */     
/* 181:226 */     String messagePattern2 = getSACParserMessages().getString("invalidExpectingMore");
/* 182:    */     
/* 183:228 */     int maxSize = 0;
/* 184:229 */     StringBuilder expected = new StringBuilder();
/* 185:230 */     for (int i = 0; i < e.expectedTokenSequences.length; i++)
/* 186:    */     {
/* 187:232 */       if (maxSize < e.expectedTokenSequences[i].length) {
/* 188:234 */         maxSize = e.expectedTokenSequences[i].length;
/* 189:    */       }
/* 190:236 */       for (int j = 0; j < e.expectedTokenSequences[i].length; j++) {
/* 191:238 */         expected.append(e.tokenImage[e.expectedTokenSequences[i][j]]);
/* 192:    */       }
/* 193:241 */       if (i < e.expectedTokenSequences.length - 1) {
/* 194:243 */         expected.append(", ");
/* 195:    */       }
/* 196:    */     }
/* 197:246 */     StringBuilder invalid = new StringBuilder();
/* 198:247 */     Token tok = e.currentToken.next;
/* 199:248 */     for (int i = 0; i < maxSize; i++)
/* 200:    */     {
/* 201:250 */       if (i != 0) {
/* 202:252 */         invalid.append(" ");
/* 203:    */       }
/* 204:254 */       if (tok.kind == 0)
/* 205:    */       {
/* 206:256 */         invalid.append(e.tokenImage[0]);
/* 207:257 */         break;
/* 208:    */       }
/* 209:259 */       invalid.append(add_escapes(tok.image));
/* 210:260 */       tok = tok.next;
/* 211:    */     }
/* 212:262 */     String s = null;
/* 213:    */     try
/* 214:    */     {
/* 215:265 */       s = getSACParserMessages().getString(key);
/* 216:    */     }
/* 217:    */     catch (MissingResourceException ex)
/* 218:    */     {
/* 219:269 */       s = key;
/* 220:    */     }
/* 221:271 */     StringBuffer message = new StringBuffer(s);
/* 222:272 */     message.append(' ');
/* 223:273 */     if (e.expectedTokenSequences.length == 1) {
/* 224:275 */       message.append(MessageFormat.format(messagePattern1, new Object[] { invalid, expected }));
/* 225:    */     } else {
/* 226:280 */       message.append(MessageFormat.format(messagePattern2, new Object[] { invalid, expected }));
/* 227:    */     }
/* 228:283 */     return new TestCSSParseException(message.toString(), getInputSource().getURI(), e.currentToken.next.beginLine, e.currentToken.next.beginColumn, getGrammarUri());
/* 229:    */   }
/* 230:    */   
/* 231:    */   protected CSSParseException toCSSParseException(TokenMgrError e)
/* 232:    */   {
/* 233:290 */     String messagePattern = getSACParserMessages().getString("tokenMgrError");
/* 234:    */     
/* 235:292 */     return new TestCSSParseException(messagePattern, getInputSource().getURI(), 1, 1, getGrammarUri());
/* 236:    */   }
/* 237:    */   
/* 238:    */   protected CSSParseException createSkipWarning(String key, CSSParseException e)
/* 239:    */   {
/* 240:298 */     String s = null;
/* 241:    */     try
/* 242:    */     {
/* 243:301 */       s = getSACParserMessages().getString(key);
/* 244:    */     }
/* 245:    */     catch (MissingResourceException ex)
/* 246:    */     {
/* 247:305 */       s = key;
/* 248:    */     }
/* 249:307 */     return new TestCSSParseException(s, e.getURI(), e.getLineNumber(), e.getColumnNumber(), getGrammarUri());
/* 250:    */   }
/* 251:    */   
/* 252:    */   public void parseStyleSheet(InputSource source)
/* 253:    */     throws IOException
/* 254:    */   {
/* 255:314 */     this.source = source;
/* 256:315 */     ReInit(getCharStream(source));
/* 257:    */     try
/* 258:    */     {
/* 259:318 */       styleSheet();
/* 260:    */     }
/* 261:    */     catch (ParseException e)
/* 262:    */     {
/* 263:322 */       getErrorHandler().error(toCSSParseException("invalidStyleSheet", e));
/* 264:    */     }
/* 265:    */     catch (TokenMgrError e)
/* 266:    */     {
/* 267:327 */       getErrorHandler().error(toCSSParseException(e));
/* 268:    */     }
/* 269:    */     catch (CSSParseException e)
/* 270:    */     {
/* 271:332 */       getErrorHandler().error(e);
/* 272:    */     }
/* 273:    */   }
/* 274:    */   
/* 275:    */   public void parseStyleSheet(String uri)
/* 276:    */     throws IOException
/* 277:    */   {
/* 278:338 */     parseStyleSheet(new InputSource(uri));
/* 279:    */   }
/* 280:    */   
/* 281:    */   public void parseStyleDeclaration(InputSource source)
/* 282:    */     throws IOException
/* 283:    */   {
/* 284:344 */     this.source = source;
/* 285:345 */     ReInit(getCharStream(source));
/* 286:    */     try
/* 287:    */     {
/* 288:348 */       styleDeclaration();
/* 289:    */     }
/* 290:    */     catch (ParseException e)
/* 291:    */     {
/* 292:352 */       getErrorHandler().error(toCSSParseException("invalidStyleDeclaration", e));
/* 293:    */     }
/* 294:    */   }
/* 295:    */   
/* 296:    */   public void parseRule(InputSource source)
/* 297:    */     throws IOException
/* 298:    */   {
/* 299:359 */     this.source = source;
/* 300:360 */     ReInit(getCharStream(source));
/* 301:    */     try
/* 302:    */     {
/* 303:363 */       styleSheetRuleSingle();
/* 304:    */     }
/* 305:    */     catch (ParseException e)
/* 306:    */     {
/* 307:367 */       getErrorHandler().error(toCSSParseException("invalidRule", e));
/* 308:    */     }
/* 309:    */   }
/* 310:    */   
/* 311:    */   public SelectorList parseSelectors(InputSource source)
/* 312:    */     throws IOException
/* 313:    */   {
/* 314:375 */     this.source = source;
/* 315:376 */     ReInit(getCharStream(source));
/* 316:377 */     SelectorList sl = null;
/* 317:    */     try
/* 318:    */     {
/* 319:380 */       sl = selectorList();
/* 320:    */     }
/* 321:    */     catch (ParseException e)
/* 322:    */     {
/* 323:384 */       getErrorHandler().error(toCSSParseException("invalidSelectorList", e));
/* 324:    */     }
/* 325:387 */     return sl;
/* 326:    */   }
/* 327:    */   
/* 328:    */   public LexicalUnit parsePropertyValue(InputSource source)
/* 329:    */     throws IOException
/* 330:    */   {
/* 331:393 */     this.source = source;
/* 332:394 */     ReInit(getCharStream(source));
/* 333:395 */     LexicalUnit lu = null;
/* 334:    */     try
/* 335:    */     {
/* 336:398 */       lu = expr();
/* 337:    */     }
/* 338:    */     catch (ParseException e)
/* 339:    */     {
/* 340:402 */       getErrorHandler().error(toCSSParseException("invalidExpr", e));
/* 341:    */     }
/* 342:405 */     return lu;
/* 343:    */   }
/* 344:    */   
/* 345:    */   public boolean parsePriority(InputSource source)
/* 346:    */     throws IOException
/* 347:    */   {
/* 348:411 */     this.source = source;
/* 349:412 */     ReInit(getCharStream(source));
/* 350:413 */     boolean b = false;
/* 351:    */     try
/* 352:    */     {
/* 353:416 */       b = prio();
/* 354:    */     }
/* 355:    */     catch (ParseException e)
/* 356:    */     {
/* 357:420 */       getErrorHandler().error(toCSSParseException("invalidPrio", e));
/* 358:    */     }
/* 359:423 */     return b;
/* 360:    */   }
/* 361:    */   
/* 362:    */   public SACMediaList parseMedia(InputSource source)
/* 363:    */     throws IOException
/* 364:    */   {
/* 365:428 */     this.source = source;
/* 366:429 */     ReInit(getCharStream(source));
/* 367:430 */     SACMediaListImpl ml = new SACMediaListImpl();
/* 368:    */     try
/* 369:    */     {
/* 370:433 */       mediaList(ml);
/* 371:    */     }
/* 372:    */     catch (ParseException e)
/* 373:    */     {
/* 374:437 */       getErrorHandler().error(toCSSParseException("invalidMediaList", e));
/* 375:    */     }
/* 376:    */     catch (CSSParseException e)
/* 377:    */     {
/* 378:442 */       getErrorHandler().error(e);
/* 379:    */     }
/* 380:444 */     return ml;
/* 381:    */   }
/* 382:    */   
/* 383:    */   private CharStream getCharStream(InputSource source)
/* 384:    */     throws IOException
/* 385:    */   {
/* 386:450 */     if (source.getCharacterStream() != null) {
/* 387:452 */       return new ASCII_CharStream(source.getCharacterStream(), 1, 1);
/* 388:    */     }
/* 389:455 */     if (source.getByteStream() != null) {
/* 390:457 */       return new ASCII_CharStream(new InputStreamReader(source.getByteStream()), 1, 1);
/* 391:    */     }
/* 392:460 */     if (source.getURI() != null) {
/* 393:462 */       return new ASCII_CharStream(new InputStreamReader(new URL(source.getURI()).openStream()), 1, 1);
/* 394:    */     }
/* 395:465 */     return null;
/* 396:    */   }
/* 397:    */   
/* 398:    */   public abstract String getParserVersion();
/* 399:    */   
/* 400:    */   protected abstract String getGrammarUri();
/* 401:    */   
/* 402:    */   protected abstract void ReInit(CharStream paramCharStream);
/* 403:    */   
/* 404:    */   protected abstract void styleSheet()
/* 405:    */     throws CSSParseException, ParseException;
/* 406:    */   
/* 407:    */   protected abstract void styleDeclaration()
/* 408:    */     throws ParseException;
/* 409:    */   
/* 410:    */   protected abstract void styleSheetRuleSingle()
/* 411:    */     throws ParseException;
/* 412:    */   
/* 413:    */   protected abstract SelectorList selectorList()
/* 414:    */     throws ParseException;
/* 415:    */   
/* 416:    */   protected abstract LexicalUnit expr()
/* 417:    */     throws ParseException;
/* 418:    */   
/* 419:    */   protected abstract boolean prio()
/* 420:    */     throws ParseException;
/* 421:    */   
/* 422:    */   protected abstract void mediaList(SACMediaListImpl paramSACMediaListImpl)
/* 423:    */     throws ParseException;
/* 424:    */   
/* 425:    */   protected void handleStartDocument()
/* 426:    */   {
/* 427:481 */     getDocumentHandler().startDocument(getInputSource());
/* 428:    */   }
/* 429:    */   
/* 430:    */   protected void handleEndDocument()
/* 431:    */   {
/* 432:486 */     getDocumentHandler().endDocument(getInputSource());
/* 433:    */   }
/* 434:    */   
/* 435:    */   protected void handleIgnorableAtRule(String s)
/* 436:    */   {
/* 437:491 */     getDocumentHandler().ignorableAtRule(s);
/* 438:    */   }
/* 439:    */   
/* 440:    */   protected void handleCharset(String characterEncoding)
/* 441:    */   {
/* 442:496 */     getDocumentHandler().charset(characterEncoding);
/* 443:    */   }
/* 444:    */   
/* 445:    */   protected void handleImportStyle(String uri, SACMediaList media, String defaultNamespaceURI)
/* 446:    */   {
/* 447:502 */     getDocumentHandler().importStyle(uri, media, defaultNamespaceURI);
/* 448:    */   }
/* 449:    */   
/* 450:    */   protected void handleStartMedia(SACMediaList media)
/* 451:    */   {
/* 452:507 */     getDocumentHandler().startMedia(media);
/* 453:    */   }
/* 454:    */   
/* 455:    */   protected void handleMedium(String medium) {}
/* 456:    */   
/* 457:    */   protected void handleEndMedia(SACMediaList media)
/* 458:    */   {
/* 459:516 */     getDocumentHandler().endMedia(media);
/* 460:    */   }
/* 461:    */   
/* 462:    */   protected void handleStartPage(String name, String pseudo_page)
/* 463:    */   {
/* 464:521 */     getDocumentHandler().startPage(name, pseudo_page);
/* 465:    */   }
/* 466:    */   
/* 467:    */   protected void handleEndPage(String name, String pseudo_page)
/* 468:    */   {
/* 469:526 */     getDocumentHandler().endPage(name, pseudo_page);
/* 470:    */   }
/* 471:    */   
/* 472:    */   protected void handleStartFontFace()
/* 473:    */   {
/* 474:531 */     getDocumentHandler().startFontFace();
/* 475:    */   }
/* 476:    */   
/* 477:    */   protected void handleEndFontFace()
/* 478:    */   {
/* 479:536 */     getDocumentHandler().endFontFace();
/* 480:    */   }
/* 481:    */   
/* 482:    */   protected void handleSelector(Selector selector) {}
/* 483:    */   
/* 484:    */   protected void handleStartSelector(SelectorList selectors)
/* 485:    */   {
/* 486:545 */     getDocumentHandler().startSelector(selectors);
/* 487:    */   }
/* 488:    */   
/* 489:    */   protected void handleEndSelector(SelectorList selectors)
/* 490:    */   {
/* 491:550 */     getDocumentHandler().endSelector(selectors);
/* 492:    */   }
/* 493:    */   
/* 494:    */   protected void handleProperty(String name, LexicalUnit value, boolean important)
/* 495:    */   {
/* 496:556 */     getDocumentHandler().property(name, value, important);
/* 497:    */   }
/* 498:    */   
/* 499:    */   protected LexicalUnit functionInternal(LexicalUnit prev, Token t, LexicalUnit params)
/* 500:    */   {
/* 501:562 */     if (t.image.equalsIgnoreCase("counter(")) {
/* 502:563 */       return LexicalUnitImpl.createCounter(prev, params);
/* 503:    */     }
/* 504:564 */     if (t.image.equalsIgnoreCase("counters(")) {
/* 505:565 */       return LexicalUnitImpl.createCounters(prev, params);
/* 506:    */     }
/* 507:566 */     if (t.image.equalsIgnoreCase("attr(")) {
/* 508:567 */       return LexicalUnitImpl.createAttr(prev, params);
/* 509:    */     }
/* 510:568 */     if (t.image.equalsIgnoreCase("rect(")) {
/* 511:569 */       return LexicalUnitImpl.createRect(prev, params);
/* 512:    */     }
/* 513:571 */     return LexicalUnitImpl.createFunction(prev, t.image.substring(0, t.image.length() - 1), params);
/* 514:    */   }
/* 515:    */   
/* 516:    */   protected LexicalUnit hexcolorInternal(LexicalUnit prev, Token t)
/* 517:    */   {
/* 518:580 */     int i = 1;
/* 519:581 */     int r = 0;
/* 520:582 */     int g = 0;
/* 521:583 */     int b = 0;
/* 522:584 */     int len = t.image.length() - 1;
/* 523:585 */     String pattern = getSACParserMessages().getString("invalidColor");
/* 524:    */     try
/* 525:    */     {
/* 526:588 */       if (len == 3)
/* 527:    */       {
/* 528:589 */         r = Integer.parseInt(t.image.substring(i + 0, i + 1), 16);
/* 529:590 */         g = Integer.parseInt(t.image.substring(i + 1, i + 2), 16);
/* 530:591 */         b = Integer.parseInt(t.image.substring(i + 2, i + 3), 16);
/* 531:592 */         r = r << 4 | r;
/* 532:593 */         g = g << 4 | g;
/* 533:594 */         b = b << 4 | b;
/* 534:    */       }
/* 535:595 */       else if (len == 6)
/* 536:    */       {
/* 537:596 */         r = Integer.parseInt(t.image.substring(i + 0, i + 2), 16);
/* 538:597 */         g = Integer.parseInt(t.image.substring(i + 2, i + 4), 16);
/* 539:598 */         b = Integer.parseInt(t.image.substring(i + 4, i + 6), 16);
/* 540:    */       }
/* 541:    */       else
/* 542:    */       {
/* 543:600 */         throw new TestCSSParseException(MessageFormat.format(pattern, new Object[] { t }), getInputSource().getURI(), t.beginLine, t.beginColumn, getGrammarUri());
/* 544:    */       }
/* 545:607 */       LexicalUnit lr = LexicalUnitImpl.createNumber(null, r);
/* 546:608 */       LexicalUnit lc1 = LexicalUnitImpl.createComma(lr);
/* 547:609 */       LexicalUnit lg = LexicalUnitImpl.createNumber(lc1, g);
/* 548:610 */       LexicalUnit lc2 = LexicalUnitImpl.createComma(lg);
/* 549:611 */       LexicalUnit lb = LexicalUnitImpl.createNumber(lc2, b);
/* 550:    */       
/* 551:613 */       return LexicalUnitImpl.createRgbColor(prev, lr);
/* 552:    */     }
/* 553:    */     catch (NumberFormatException ex)
/* 554:    */     {
/* 555:617 */       throw new TestCSSParseException(MessageFormat.format(pattern, new Object[] { t }), getInputSource().getURI(), t.beginLine, t.beginColumn, ex, getGrammarUri());
/* 556:    */     }
/* 557:    */   }
/* 558:    */   
/* 559:    */   int intValue(char op, String s)
/* 560:    */   {
/* 561:626 */     return (op == '-' ? -1 : 1) * Integer.parseInt(s);
/* 562:    */   }
/* 563:    */   
/* 564:    */   float floatValue(char op, String s)
/* 565:    */   {
/* 566:630 */     return (op == '-' ? -1 : 1) * Float.parseFloat(s);
/* 567:    */   }
/* 568:    */   
/* 569:    */   int getLastNumPos(String s)
/* 570:    */   {
/* 571:635 */     for (int i = 0; i < s.length(); i++) {
/* 572:636 */       if (Character.isLetter(s.charAt(i))) {
/* 573:    */         break;
/* 574:    */       }
/* 575:    */     }
/* 576:640 */     return i - 1;
/* 577:    */   }
/* 578:    */   
/* 579:    */   String unescape(String s)
/* 580:    */   {
/* 581:652 */     int len = s.length();
/* 582:653 */     StringBuffer buf = new StringBuffer(len);
/* 583:654 */     int index = 0;
/* 584:656 */     while (index < len)
/* 585:    */     {
/* 586:657 */       char c = s.charAt(index);
/* 587:658 */       if (c == '\\')
/* 588:    */       {
/* 589:659 */         index++;
/* 590:659 */         if (index < len)
/* 591:    */         {
/* 592:660 */           c = s.charAt(index);
/* 593:661 */           switch (c)
/* 594:    */           {
/* 595:    */           case '0': 
/* 596:    */           case '1': 
/* 597:    */           case '2': 
/* 598:    */           case '3': 
/* 599:    */           case '4': 
/* 600:    */           case '5': 
/* 601:    */           case '6': 
/* 602:    */           case '7': 
/* 603:    */           case '8': 
/* 604:    */           case '9': 
/* 605:    */           case 'A': 
/* 606:    */           case 'B': 
/* 607:    */           case 'C': 
/* 608:    */           case 'D': 
/* 609:    */           case 'E': 
/* 610:    */           case 'F': 
/* 611:    */           case 'a': 
/* 612:    */           case 'b': 
/* 613:    */           case 'c': 
/* 614:    */           case 'd': 
/* 615:    */           case 'e': 
/* 616:    */           case 'f': 
/* 617:666 */             int numValue = Character.digit(c, 16);
/* 618:667 */             int count = 0;
/* 619:668 */             int p = 16;
/* 620:670 */             while ((index + 1 < len) && (count < 6))
/* 621:    */             {
/* 622:671 */               c = s.charAt(index + 1);
/* 623:673 */               if (Character.digit(c, 16) != -1)
/* 624:    */               {
/* 625:674 */                 numValue = numValue * 16 + Character.digit(c, 16);
/* 626:675 */                 p *= 16;
/* 627:676 */                 index++;
/* 628:    */               }
/* 629:678 */               else if (Character.isWhitespace(c))
/* 630:    */               {
/* 631:680 */                 index++;
/* 632:    */               }
/* 633:    */             }
/* 634:685 */             buf.append((char)numValue);
/* 635:686 */             break;
/* 636:    */           case '\n': 
/* 637:    */           case '\f': 
/* 638:    */             break;
/* 639:    */           case '\r': 
/* 640:691 */             if ((index + 1 < len) && 
/* 641:692 */               (s.charAt(index + 1) == '\n')) {
/* 642:693 */               index++;
/* 643:    */             }
/* 644:    */             break;
/* 645:    */           case '\013': 
/* 646:    */           case '\016': 
/* 647:    */           case '\017': 
/* 648:    */           case '\020': 
/* 649:    */           case '\021': 
/* 650:    */           case '\022': 
/* 651:    */           case '\023': 
/* 652:    */           case '\024': 
/* 653:    */           case '\025': 
/* 654:    */           case '\026': 
/* 655:    */           case '\027': 
/* 656:    */           case '\030': 
/* 657:    */           case '\031': 
/* 658:    */           case '\032': 
/* 659:    */           case '\033': 
/* 660:    */           case '\034': 
/* 661:    */           case '\035': 
/* 662:    */           case '\036': 
/* 663:    */           case '\037': 
/* 664:    */           case ' ': 
/* 665:    */           case '!': 
/* 666:    */           case '"': 
/* 667:    */           case '#': 
/* 668:    */           case '$': 
/* 669:    */           case '%': 
/* 670:    */           case '&': 
/* 671:    */           case '\'': 
/* 672:    */           case '(': 
/* 673:    */           case ')': 
/* 674:    */           case '*': 
/* 675:    */           case '+': 
/* 676:    */           case ',': 
/* 677:    */           case '-': 
/* 678:    */           case '.': 
/* 679:    */           case '/': 
/* 680:    */           case ':': 
/* 681:    */           case ';': 
/* 682:    */           case '<': 
/* 683:    */           case '=': 
/* 684:    */           case '>': 
/* 685:    */           case '?': 
/* 686:    */           case '@': 
/* 687:    */           case 'G': 
/* 688:    */           case 'H': 
/* 689:    */           case 'I': 
/* 690:    */           case 'J': 
/* 691:    */           case 'K': 
/* 692:    */           case 'L': 
/* 693:    */           case 'M': 
/* 694:    */           case 'N': 
/* 695:    */           case 'O': 
/* 696:    */           case 'P': 
/* 697:    */           case 'Q': 
/* 698:    */           case 'R': 
/* 699:    */           case 'S': 
/* 700:    */           case 'T': 
/* 701:    */           case 'U': 
/* 702:    */           case 'V': 
/* 703:    */           case 'W': 
/* 704:    */           case 'X': 
/* 705:    */           case 'Y': 
/* 706:    */           case 'Z': 
/* 707:    */           case '[': 
/* 708:    */           case '\\': 
/* 709:    */           case ']': 
/* 710:    */           case '^': 
/* 711:    */           case '_': 
/* 712:    */           case '`': 
/* 713:    */           default: 
/* 714:698 */             buf.append(c);
/* 715:    */           }
/* 716:    */         }
/* 717:    */         else
/* 718:    */         {
/* 719:701 */           throw new TestCSSParseException("invalid string " + s, getLocator(), getGrammarUri());
/* 720:    */         }
/* 721:    */       }
/* 722:    */       else
/* 723:    */       {
/* 724:705 */         buf.append(c);
/* 725:    */       }
/* 726:707 */       index++;
/* 727:    */     }
/* 728:710 */     return buf.toString();
/* 729:    */   }
/* 730:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.steadystate.css.parser.AbstractSACParser
 * JD-Core Version:    0.7.0.1
 */