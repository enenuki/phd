/*   1:    */ package antlr;
/*   2:    */ 
/*   3:    */ import antlr.collections.impl.BitSet;
/*   4:    */ import antlr.collections.impl.Vector;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.io.PrintWriter;
/*   7:    */ import java.util.Enumeration;
/*   8:    */ import java.util.Hashtable;
/*   9:    */ 
/*  10:    */ public class DocBookCodeGenerator
/*  11:    */   extends CodeGenerator
/*  12:    */ {
/*  13: 25 */   protected int syntacticPredLevel = 0;
/*  14: 28 */   protected boolean doingLexRules = false;
/*  15:    */   protected boolean firstElementInAlt;
/*  16: 32 */   protected AlternativeElement prevAltElem = null;
/*  17:    */   
/*  18:    */   public DocBookCodeGenerator()
/*  19:    */   {
/*  20: 40 */     this.charFormatter = new JavaCharFormatter();
/*  21:    */   }
/*  22:    */   
/*  23:    */   static String HTMLEncode(String paramString)
/*  24:    */   {
/*  25: 48 */     StringBuffer localStringBuffer = new StringBuffer();
/*  26:    */     
/*  27: 50 */     int i = 0;
/*  28: 50 */     for (int j = paramString.length(); i < j; i++)
/*  29:    */     {
/*  30: 51 */       char c = paramString.charAt(i);
/*  31: 52 */       if (c == '&') {
/*  32: 53 */         localStringBuffer.append("&amp;");
/*  33: 54 */       } else if (c == '"') {
/*  34: 55 */         localStringBuffer.append("&quot;");
/*  35: 56 */       } else if (c == '\'') {
/*  36: 57 */         localStringBuffer.append("&#039;");
/*  37: 58 */       } else if (c == '<') {
/*  38: 59 */         localStringBuffer.append("&lt;");
/*  39: 60 */       } else if (c == '>') {
/*  40: 61 */         localStringBuffer.append("&gt;");
/*  41:    */       } else {
/*  42: 63 */         localStringBuffer.append(c);
/*  43:    */       }
/*  44:    */     }
/*  45: 65 */     return localStringBuffer.toString();
/*  46:    */   }
/*  47:    */   
/*  48:    */   static String QuoteForId(String paramString)
/*  49:    */   {
/*  50: 73 */     StringBuffer localStringBuffer = new StringBuffer();
/*  51:    */     
/*  52: 75 */     int i = 0;
/*  53: 75 */     for (int j = paramString.length(); i < j; i++)
/*  54:    */     {
/*  55: 76 */       char c = paramString.charAt(i);
/*  56: 77 */       if (c == '_') {
/*  57: 78 */         localStringBuffer.append(".");
/*  58:    */       } else {
/*  59: 80 */         localStringBuffer.append(c);
/*  60:    */       }
/*  61:    */     }
/*  62: 82 */     return localStringBuffer.toString();
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void gen()
/*  66:    */   {
/*  67:    */     try
/*  68:    */     {
/*  69: 89 */       Enumeration localEnumeration = this.behavior.grammars.elements();
/*  70: 90 */       while (localEnumeration.hasMoreElements())
/*  71:    */       {
/*  72: 91 */         Grammar localGrammar = (Grammar)localEnumeration.nextElement();
/*  73:    */         
/*  74:    */ 
/*  75:    */ 
/*  76:    */ 
/*  77:    */ 
/*  78:    */ 
/*  79: 98 */         localGrammar.setCodeGenerator(this);
/*  80:    */         
/*  81:    */ 
/*  82:101 */         localGrammar.generate();
/*  83:103 */         if (this.antlrTool.hasError()) {
/*  84:104 */           this.antlrTool.fatalError("Exiting due to errors.");
/*  85:    */         }
/*  86:    */       }
/*  87:    */     }
/*  88:    */     catch (IOException localIOException)
/*  89:    */     {
/*  90:111 */       this.antlrTool.reportException(localIOException, null);
/*  91:    */     }
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void gen(ActionElement paramActionElement) {}
/*  95:    */   
/*  96:    */   public void gen(AlternativeBlock paramAlternativeBlock)
/*  97:    */   {
/*  98:126 */     genGenericBlock(paramAlternativeBlock, "");
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void gen(BlockEndElement paramBlockEndElement) {}
/* 102:    */   
/* 103:    */   public void gen(CharLiteralElement paramCharLiteralElement)
/* 104:    */   {
/* 105:142 */     if (paramCharLiteralElement.not) {
/* 106:143 */       _print("~");
/* 107:    */     }
/* 108:145 */     _print(HTMLEncode(paramCharLiteralElement.atomText) + " ");
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void gen(CharRangeElement paramCharRangeElement)
/* 112:    */   {
/* 113:152 */     print(paramCharRangeElement.beginText + ".." + paramCharRangeElement.endText + " ");
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void gen(LexerGrammar paramLexerGrammar)
/* 117:    */     throws IOException
/* 118:    */   {
/* 119:157 */     setGrammar(paramLexerGrammar);
/* 120:158 */     this.antlrTool.reportProgress("Generating " + this.grammar.getClassName() + ".sgml");
/* 121:159 */     this.currentOutput = this.antlrTool.openOutputFile(this.grammar.getClassName() + ".sgml");
/* 122:    */     
/* 123:161 */     this.tabs = 0;
/* 124:162 */     this.doingLexRules = true;
/* 125:    */     
/* 126:    */ 
/* 127:165 */     genHeader();
/* 128:    */     
/* 129:    */ 
/* 130:    */ 
/* 131:    */ 
/* 132:    */ 
/* 133:    */ 
/* 134:172 */     println("");
/* 135:175 */     if (this.grammar.comment != null) {
/* 136:176 */       _println(HTMLEncode(this.grammar.comment));
/* 137:    */     }
/* 138:179 */     println("<para>Definition of lexer " + this.grammar.getClassName() + ", which is a subclass of " + this.grammar.getSuperClass() + ".</para>");
/* 139:    */     
/* 140:    */ 
/* 141:    */ 
/* 142:    */ 
/* 143:    */ 
/* 144:    */ 
/* 145:    */ 
/* 146:    */ 
/* 147:    */ 
/* 148:    */ 
/* 149:    */ 
/* 150:    */ 
/* 151:    */ 
/* 152:    */ 
/* 153:    */ 
/* 154:    */ 
/* 155:    */ 
/* 156:    */ 
/* 157:    */ 
/* 158:    */ 
/* 159:    */ 
/* 160:    */ 
/* 161:    */ 
/* 162:    */ 
/* 163:    */ 
/* 164:    */ 
/* 165:    */ 
/* 166:    */ 
/* 167:    */ 
/* 168:    */ 
/* 169:    */ 
/* 170:    */ 
/* 171:212 */     genNextToken();
/* 172:    */     
/* 173:    */ 
/* 174:    */ 
/* 175:216 */     Enumeration localEnumeration = this.grammar.rules.elements();
/* 176:217 */     while (localEnumeration.hasMoreElements())
/* 177:    */     {
/* 178:218 */       RuleSymbol localRuleSymbol = (RuleSymbol)localEnumeration.nextElement();
/* 179:219 */       if (!localRuleSymbol.id.equals("mnextToken")) {
/* 180:220 */         genRule(localRuleSymbol);
/* 181:    */       }
/* 182:    */     }
/* 183:225 */     this.currentOutput.close();
/* 184:226 */     this.currentOutput = null;
/* 185:227 */     this.doingLexRules = false;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public void gen(OneOrMoreBlock paramOneOrMoreBlock)
/* 189:    */   {
/* 190:234 */     genGenericBlock(paramOneOrMoreBlock, "+");
/* 191:    */   }
/* 192:    */   
/* 193:    */   public void gen(ParserGrammar paramParserGrammar)
/* 194:    */     throws IOException
/* 195:    */   {
/* 196:239 */     setGrammar(paramParserGrammar);
/* 197:    */     
/* 198:241 */     this.antlrTool.reportProgress("Generating " + this.grammar.getClassName() + ".sgml");
/* 199:242 */     this.currentOutput = this.antlrTool.openOutputFile(this.grammar.getClassName() + ".sgml");
/* 200:    */     
/* 201:244 */     this.tabs = 0;
/* 202:    */     
/* 203:    */ 
/* 204:247 */     genHeader();
/* 205:    */     
/* 206:    */ 
/* 207:250 */     println("");
/* 208:253 */     if (this.grammar.comment != null) {
/* 209:254 */       _println(HTMLEncode(this.grammar.comment));
/* 210:    */     }
/* 211:257 */     println("<para>Definition of parser " + this.grammar.getClassName() + ", which is a subclass of " + this.grammar.getSuperClass() + ".</para>");
/* 212:    */     
/* 213:    */ 
/* 214:260 */     Enumeration localEnumeration = this.grammar.rules.elements();
/* 215:261 */     while (localEnumeration.hasMoreElements())
/* 216:    */     {
/* 217:262 */       println("");
/* 218:    */       
/* 219:264 */       GrammarSymbol localGrammarSymbol = (GrammarSymbol)localEnumeration.nextElement();
/* 220:266 */       if ((localGrammarSymbol instanceof RuleSymbol)) {
/* 221:267 */         genRule((RuleSymbol)localGrammarSymbol);
/* 222:    */       }
/* 223:    */     }
/* 224:270 */     this.tabs -= 1;
/* 225:271 */     println("");
/* 226:    */     
/* 227:273 */     genTail();
/* 228:    */     
/* 229:    */ 
/* 230:276 */     this.currentOutput.close();
/* 231:277 */     this.currentOutput = null;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public void gen(RuleRefElement paramRuleRefElement)
/* 235:    */   {
/* 236:284 */     RuleSymbol localRuleSymbol = (RuleSymbol)this.grammar.getSymbol(paramRuleRefElement.targetRule);
/* 237:    */     
/* 238:    */ 
/* 239:287 */     _print("<link linkend=\"" + QuoteForId(paramRuleRefElement.targetRule) + "\">");
/* 240:288 */     _print(paramRuleRefElement.targetRule);
/* 241:289 */     _print("</link>");
/* 242:    */     
/* 243:    */ 
/* 244:    */ 
/* 245:    */ 
/* 246:294 */     _print(" ");
/* 247:    */   }
/* 248:    */   
/* 249:    */   public void gen(StringLiteralElement paramStringLiteralElement)
/* 250:    */   {
/* 251:301 */     if (paramStringLiteralElement.not) {
/* 252:302 */       _print("~");
/* 253:    */     }
/* 254:304 */     _print(HTMLEncode(paramStringLiteralElement.atomText));
/* 255:305 */     _print(" ");
/* 256:    */   }
/* 257:    */   
/* 258:    */   public void gen(TokenRangeElement paramTokenRangeElement)
/* 259:    */   {
/* 260:312 */     print(paramTokenRangeElement.beginText + ".." + paramTokenRangeElement.endText + " ");
/* 261:    */   }
/* 262:    */   
/* 263:    */   public void gen(TokenRefElement paramTokenRefElement)
/* 264:    */   {
/* 265:319 */     if (paramTokenRefElement.not) {
/* 266:320 */       _print("~");
/* 267:    */     }
/* 268:322 */     _print(paramTokenRefElement.atomText);
/* 269:323 */     _print(" ");
/* 270:    */   }
/* 271:    */   
/* 272:    */   public void gen(TreeElement paramTreeElement)
/* 273:    */   {
/* 274:327 */     print(paramTreeElement + " ");
/* 275:    */   }
/* 276:    */   
/* 277:    */   public void gen(TreeWalkerGrammar paramTreeWalkerGrammar)
/* 278:    */     throws IOException
/* 279:    */   {
/* 280:332 */     setGrammar(paramTreeWalkerGrammar);
/* 281:    */     
/* 282:334 */     this.antlrTool.reportProgress("Generating " + this.grammar.getClassName() + ".sgml");
/* 283:335 */     this.currentOutput = this.antlrTool.openOutputFile(this.grammar.getClassName() + ".sgml");
/* 284:    */     
/* 285:    */ 
/* 286:338 */     this.tabs = 0;
/* 287:    */     
/* 288:    */ 
/* 289:341 */     genHeader();
/* 290:    */     
/* 291:    */ 
/* 292:344 */     println("");
/* 293:    */     
/* 294:    */ 
/* 295:    */ 
/* 296:    */ 
/* 297:    */ 
/* 298:    */ 
/* 299:    */ 
/* 300:    */ 
/* 301:353 */     println("");
/* 302:356 */     if (this.grammar.comment != null) {
/* 303:357 */       _println(HTMLEncode(this.grammar.comment));
/* 304:    */     }
/* 305:360 */     println("<para>Definition of tree parser " + this.grammar.getClassName() + ", which is a subclass of " + this.grammar.getSuperClass() + ".</para>");
/* 306:    */     
/* 307:    */ 
/* 308:    */ 
/* 309:    */ 
/* 310:    */ 
/* 311:    */ 
/* 312:    */ 
/* 313:    */ 
/* 314:    */ 
/* 315:    */ 
/* 316:    */ 
/* 317:372 */     println("");
/* 318:    */     
/* 319:374 */     this.tabs += 1;
/* 320:    */     
/* 321:    */ 
/* 322:377 */     Enumeration localEnumeration = this.grammar.rules.elements();
/* 323:378 */     while (localEnumeration.hasMoreElements())
/* 324:    */     {
/* 325:379 */       println("");
/* 326:    */       
/* 327:381 */       GrammarSymbol localGrammarSymbol = (GrammarSymbol)localEnumeration.nextElement();
/* 328:383 */       if ((localGrammarSymbol instanceof RuleSymbol)) {
/* 329:384 */         genRule((RuleSymbol)localGrammarSymbol);
/* 330:    */       }
/* 331:    */     }
/* 332:387 */     this.tabs -= 1;
/* 333:388 */     println("");
/* 334:    */     
/* 335:    */ 
/* 336:    */ 
/* 337:    */ 
/* 338:    */ 
/* 339:    */ 
/* 340:395 */     this.currentOutput.close();
/* 341:396 */     this.currentOutput = null;
/* 342:    */   }
/* 343:    */   
/* 344:    */   public void gen(WildcardElement paramWildcardElement)
/* 345:    */   {
/* 346:406 */     _print(". ");
/* 347:    */   }
/* 348:    */   
/* 349:    */   public void gen(ZeroOrMoreBlock paramZeroOrMoreBlock)
/* 350:    */   {
/* 351:413 */     genGenericBlock(paramZeroOrMoreBlock, "*");
/* 352:    */   }
/* 353:    */   
/* 354:    */   protected void genAlt(Alternative paramAlternative)
/* 355:    */   {
/* 356:417 */     if (paramAlternative.getTreeSpecifier() != null) {
/* 357:418 */       _print(paramAlternative.getTreeSpecifier().getText());
/* 358:    */     }
/* 359:420 */     this.prevAltElem = null;
/* 360:421 */     for (AlternativeElement localAlternativeElement = paramAlternative.head; !(localAlternativeElement instanceof BlockEndElement); localAlternativeElement = localAlternativeElement.next)
/* 361:    */     {
/* 362:424 */       localAlternativeElement.generate();
/* 363:425 */       this.firstElementInAlt = false;
/* 364:426 */       this.prevAltElem = localAlternativeElement;
/* 365:    */     }
/* 366:    */   }
/* 367:    */   
/* 368:    */   public void genCommonBlock(AlternativeBlock paramAlternativeBlock)
/* 369:    */   {
/* 370:447 */     if (paramAlternativeBlock.alternatives.size() > 1) {
/* 371:448 */       println("<itemizedlist mark=\"none\">");
/* 372:    */     }
/* 373:449 */     for (int i = 0; i < paramAlternativeBlock.alternatives.size(); i++)
/* 374:    */     {
/* 375:450 */       Alternative localAlternative = paramAlternativeBlock.getAlternativeAt(i);
/* 376:451 */       AlternativeElement localAlternativeElement = localAlternative.head;
/* 377:453 */       if (paramAlternativeBlock.alternatives.size() > 1) {
/* 378:454 */         print("<listitem><para>");
/* 379:    */       }
/* 380:457 */       if ((i > 0) && (paramAlternativeBlock.alternatives.size() > 1)) {
/* 381:458 */         _print("| ");
/* 382:    */       }
/* 383:463 */       boolean bool = this.firstElementInAlt;
/* 384:464 */       this.firstElementInAlt = true;
/* 385:465 */       this.tabs += 1;
/* 386:    */       
/* 387:467 */       genAlt(localAlternative);
/* 388:468 */       this.tabs -= 1;
/* 389:469 */       this.firstElementInAlt = bool;
/* 390:470 */       if (paramAlternativeBlock.alternatives.size() > 1) {
/* 391:471 */         _println("</para></listitem>");
/* 392:    */       }
/* 393:    */     }
/* 394:473 */     if (paramAlternativeBlock.alternatives.size() > 1) {
/* 395:474 */       println("</itemizedlist>");
/* 396:    */     }
/* 397:    */   }
/* 398:    */   
/* 399:    */   public void genFollowSetForRuleBlock(RuleBlock paramRuleBlock)
/* 400:    */   {
/* 401:482 */     Lookahead localLookahead = this.grammar.theLLkAnalyzer.FOLLOW(1, paramRuleBlock.endNode);
/* 402:483 */     printSet(this.grammar.maxk, 1, localLookahead);
/* 403:    */   }
/* 404:    */   
/* 405:    */   protected void genGenericBlock(AlternativeBlock paramAlternativeBlock, String paramString)
/* 406:    */   {
/* 407:487 */     if (paramAlternativeBlock.alternatives.size() > 1)
/* 408:    */     {
/* 409:489 */       _println("");
/* 410:490 */       if (!this.firstElementInAlt) {
/* 411:496 */         _println("(");
/* 412:    */       } else {
/* 413:506 */         _print("(");
/* 414:    */       }
/* 415:    */     }
/* 416:    */     else
/* 417:    */     {
/* 418:510 */       _print("( ");
/* 419:    */     }
/* 420:514 */     genCommonBlock(paramAlternativeBlock);
/* 421:515 */     if (paramAlternativeBlock.alternatives.size() > 1)
/* 422:    */     {
/* 423:516 */       _println("");
/* 424:517 */       print(")" + paramString + " ");
/* 425:519 */       if (!(paramAlternativeBlock.next instanceof BlockEndElement))
/* 426:    */       {
/* 427:520 */         _println("");
/* 428:521 */         print("");
/* 429:    */       }
/* 430:    */     }
/* 431:    */     else
/* 432:    */     {
/* 433:525 */       _print(")" + paramString + " ");
/* 434:    */     }
/* 435:    */   }
/* 436:    */   
/* 437:    */   protected void genHeader()
/* 438:    */   {
/* 439:531 */     println("<?xml version=\"1.0\" standalone=\"no\"?>");
/* 440:532 */     println("<!DOCTYPE book PUBLIC \"-//OASIS//DTD DocBook V3.1//EN\">");
/* 441:533 */     println("<book lang=\"en\">");
/* 442:534 */     println("<bookinfo>");
/* 443:535 */     println("<title>Grammar " + this.grammar.getClassName() + "</title>");
/* 444:536 */     println("  <author>");
/* 445:537 */     println("    <firstname></firstname>");
/* 446:538 */     println("    <othername></othername>");
/* 447:539 */     println("    <surname></surname>");
/* 448:540 */     println("    <affiliation>");
/* 449:541 */     println("     <address>");
/* 450:542 */     println("     <email></email>");
/* 451:543 */     println("     </address>");
/* 452:544 */     println("    </affiliation>");
/* 453:545 */     println("  </author>");
/* 454:546 */     println("  <othercredit>");
/* 455:547 */     println("    <contrib>");
/* 456:548 */     println("    Generated by <ulink url=\"http://www.ANTLR.org/\">ANTLR</ulink>" + Tool.version);
/* 457:549 */     println("    from " + this.antlrTool.grammarFile);
/* 458:550 */     println("    </contrib>");
/* 459:551 */     println("  </othercredit>");
/* 460:552 */     println("  <pubdate></pubdate>");
/* 461:553 */     println("  <abstract>");
/* 462:554 */     println("  <para>");
/* 463:555 */     println("  </para>");
/* 464:556 */     println("  </abstract>");
/* 465:557 */     println("</bookinfo>");
/* 466:558 */     println("<chapter>");
/* 467:559 */     println("<title></title>");
/* 468:    */   }
/* 469:    */   
/* 470:    */   protected void genLookaheadSetForAlt(Alternative paramAlternative)
/* 471:    */   {
/* 472:564 */     if ((this.doingLexRules) && (paramAlternative.cache[1].containsEpsilon()))
/* 473:    */     {
/* 474:565 */       println("MATCHES ALL");
/* 475:566 */       return;
/* 476:    */     }
/* 477:568 */     int i = paramAlternative.lookaheadDepth;
/* 478:569 */     if (i == 2147483647) {
/* 479:572 */       i = this.grammar.maxk;
/* 480:    */     }
/* 481:574 */     for (int j = 1; j <= i; j++)
/* 482:    */     {
/* 483:575 */       Lookahead localLookahead = paramAlternative.cache[j];
/* 484:576 */       printSet(i, j, localLookahead);
/* 485:    */     }
/* 486:    */   }
/* 487:    */   
/* 488:    */   public void genLookaheadSetForBlock(AlternativeBlock paramAlternativeBlock)
/* 489:    */   {
/* 490:586 */     int i = 0;
/* 491:    */     Object localObject;
/* 492:587 */     for (int j = 0; j < paramAlternativeBlock.alternatives.size(); j++)
/* 493:    */     {
/* 494:588 */       localObject = paramAlternativeBlock.getAlternativeAt(j);
/* 495:589 */       if (((Alternative)localObject).lookaheadDepth == 2147483647)
/* 496:    */       {
/* 497:590 */         i = this.grammar.maxk;
/* 498:591 */         break;
/* 499:    */       }
/* 500:593 */       if (i < ((Alternative)localObject).lookaheadDepth) {
/* 501:594 */         i = ((Alternative)localObject).lookaheadDepth;
/* 502:    */       }
/* 503:    */     }
/* 504:598 */     for (j = 1; j <= i; j++)
/* 505:    */     {
/* 506:599 */       localObject = this.grammar.theLLkAnalyzer.look(j, paramAlternativeBlock);
/* 507:600 */       printSet(i, j, (Lookahead)localObject);
/* 508:    */     }
/* 509:    */   }
/* 510:    */   
/* 511:    */   public void genNextToken()
/* 512:    */   {
/* 513:609 */     println("");
/* 514:610 */     println("/** Lexer nextToken rule:");
/* 515:611 */     println(" *  The lexer nextToken rule is synthesized from all of the user-defined");
/* 516:612 */     println(" *  lexer rules.  It logically consists of one big alternative block with");
/* 517:613 */     println(" *  each user-defined rule being an alternative.");
/* 518:614 */     println(" */");
/* 519:    */     
/* 520:    */ 
/* 521:    */ 
/* 522:618 */     RuleBlock localRuleBlock = MakeGrammar.createNextTokenRule(this.grammar, this.grammar.rules, "nextToken");
/* 523:    */     
/* 524:    */ 
/* 525:621 */     RuleSymbol localRuleSymbol = new RuleSymbol("mnextToken");
/* 526:622 */     localRuleSymbol.setDefined();
/* 527:623 */     localRuleSymbol.setBlock(localRuleBlock);
/* 528:624 */     localRuleSymbol.access = "private";
/* 529:625 */     this.grammar.define(localRuleSymbol);
/* 530:    */     
/* 531:    */ 
/* 532:    */ 
/* 533:    */ 
/* 534:    */ 
/* 535:    */ 
/* 536:    */ 
/* 537:    */ 
/* 538:    */ 
/* 539:    */ 
/* 540:    */ 
/* 541:    */ 
/* 542:638 */     genCommonBlock(localRuleBlock);
/* 543:    */   }
/* 544:    */   
/* 545:    */   public void genRule(RuleSymbol paramRuleSymbol)
/* 546:    */   {
/* 547:645 */     if ((paramRuleSymbol == null) || (!paramRuleSymbol.isDefined())) {
/* 548:645 */       return;
/* 549:    */     }
/* 550:646 */     println("");
/* 551:648 */     if ((paramRuleSymbol.access.length() != 0) && 
/* 552:649 */       (!paramRuleSymbol.access.equals("public"))) {
/* 553:650 */       _print("<para>" + paramRuleSymbol.access + " </para>");
/* 554:    */     }
/* 555:654 */     println("<section id=\"" + QuoteForId(paramRuleSymbol.getId()) + "\">");
/* 556:655 */     println("<title>" + paramRuleSymbol.getId() + "</title>");
/* 557:656 */     if (paramRuleSymbol.comment != null) {
/* 558:657 */       _println("<para>" + HTMLEncode(paramRuleSymbol.comment) + "</para>");
/* 559:    */     }
/* 560:659 */     println("<para>");
/* 561:    */     
/* 562:    */ 
/* 563:662 */     RuleBlock localRuleBlock = paramRuleSymbol.getBlock();
/* 564:    */     
/* 565:    */ 
/* 566:    */ 
/* 567:    */ 
/* 568:    */ 
/* 569:    */ 
/* 570:    */ 
/* 571:    */ 
/* 572:    */ 
/* 573:    */ 
/* 574:    */ 
/* 575:674 */     _println("");
/* 576:675 */     print(paramRuleSymbol.getId() + ":\t");
/* 577:676 */     this.tabs += 1;
/* 578:    */     
/* 579:    */ 
/* 580:    */ 
/* 581:    */ 
/* 582:    */ 
/* 583:682 */     genCommonBlock(localRuleBlock);
/* 584:    */     
/* 585:684 */     _println("");
/* 586:    */     
/* 587:686 */     this.tabs -= 1;
/* 588:687 */     _println("</para>");
/* 589:688 */     _println("</section><!-- section \"" + paramRuleSymbol.getId() + "\" -->");
/* 590:    */   }
/* 591:    */   
/* 592:    */   protected void genSynPred(SynPredBlock paramSynPredBlock) {}
/* 593:    */   
/* 594:    */   public void genTail()
/* 595:    */   {
/* 596:700 */     println("</chapter>");
/* 597:701 */     println("</book>");
/* 598:    */   }
/* 599:    */   
/* 600:    */   protected void genTokenTypes(TokenManager paramTokenManager)
/* 601:    */     throws IOException
/* 602:    */   {
/* 603:707 */     this.antlrTool.reportProgress("Generating " + paramTokenManager.getName() + TokenTypesFileSuffix + TokenTypesFileExt);
/* 604:708 */     this.currentOutput = this.antlrTool.openOutputFile(paramTokenManager.getName() + TokenTypesFileSuffix + TokenTypesFileExt);
/* 605:    */     
/* 606:710 */     this.tabs = 0;
/* 607:    */     
/* 608:    */ 
/* 609:713 */     genHeader();
/* 610:    */     
/* 611:    */ 
/* 612:    */ 
/* 613:717 */     println("");
/* 614:718 */     println("*** Tokens used by the parser");
/* 615:719 */     println("This is a list of the token numeric values and the corresponding");
/* 616:720 */     println("token identifiers.  Some tokens are literals, and because of that");
/* 617:721 */     println("they have no identifiers.  Literals are double-quoted.");
/* 618:722 */     this.tabs += 1;
/* 619:    */     
/* 620:    */ 
/* 621:725 */     Vector localVector = paramTokenManager.getVocabulary();
/* 622:726 */     for (int i = 4; i < localVector.size(); i++)
/* 623:    */     {
/* 624:727 */       String str = (String)localVector.elementAt(i);
/* 625:728 */       if (str != null) {
/* 626:729 */         println(str + " = " + i);
/* 627:    */       }
/* 628:    */     }
/* 629:734 */     this.tabs -= 1;
/* 630:735 */     println("*** End of tokens used by the parser");
/* 631:    */     
/* 632:    */ 
/* 633:738 */     this.currentOutput.close();
/* 634:739 */     this.currentOutput = null;
/* 635:    */   }
/* 636:    */   
/* 637:    */   protected String processActionForSpecialSymbols(String paramString, int paramInt, RuleBlock paramRuleBlock, ActionTransInfo paramActionTransInfo)
/* 638:    */   {
/* 639:747 */     return paramString;
/* 640:    */   }
/* 641:    */   
/* 642:    */   public String getASTCreateString(Vector paramVector)
/* 643:    */   {
/* 644:754 */     return null;
/* 645:    */   }
/* 646:    */   
/* 647:    */   public String getASTCreateString(GrammarAtom paramGrammarAtom, String paramString)
/* 648:    */   {
/* 649:761 */     return null;
/* 650:    */   }
/* 651:    */   
/* 652:    */   public String mapTreeId(String paramString, ActionTransInfo paramActionTransInfo)
/* 653:    */   {
/* 654:771 */     return paramString;
/* 655:    */   }
/* 656:    */   
/* 657:    */   public void printSet(int paramInt1, int paramInt2, Lookahead paramLookahead)
/* 658:    */   {
/* 659:780 */     int i = 5;
/* 660:    */     
/* 661:782 */     int[] arrayOfInt = paramLookahead.fset.toArray();
/* 662:784 */     if (paramInt1 != 1) {
/* 663:785 */       print("k==" + paramInt2 + ": {");
/* 664:    */     } else {
/* 665:788 */       print("{ ");
/* 666:    */     }
/* 667:790 */     if (arrayOfInt.length > i)
/* 668:    */     {
/* 669:791 */       _println("");
/* 670:792 */       this.tabs += 1;
/* 671:793 */       print("");
/* 672:    */     }
/* 673:796 */     int j = 0;
/* 674:797 */     for (int k = 0; k < arrayOfInt.length; k++)
/* 675:    */     {
/* 676:798 */       j++;
/* 677:799 */       if (j > i)
/* 678:    */       {
/* 679:800 */         _println("");
/* 680:801 */         print("");
/* 681:802 */         j = 0;
/* 682:    */       }
/* 683:804 */       if (this.doingLexRules) {
/* 684:805 */         _print(this.charFormatter.literalChar(arrayOfInt[k]));
/* 685:    */       } else {
/* 686:808 */         _print((String)this.grammar.tokenManager.getVocabulary().elementAt(arrayOfInt[k]));
/* 687:    */       }
/* 688:810 */       if (k != arrayOfInt.length - 1) {
/* 689:811 */         _print(", ");
/* 690:    */       }
/* 691:    */     }
/* 692:815 */     if (arrayOfInt.length > i)
/* 693:    */     {
/* 694:816 */       _println("");
/* 695:817 */       this.tabs -= 1;
/* 696:818 */       print("");
/* 697:    */     }
/* 698:820 */     _println(" }");
/* 699:    */   }
/* 700:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.DocBookCodeGenerator
 * JD-Core Version:    0.7.0.1
 */