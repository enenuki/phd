/*   1:    */ package antlr;
/*   2:    */ 
/*   3:    */ import antlr.collections.impl.BitSet;
/*   4:    */ import antlr.collections.impl.Vector;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.io.PrintWriter;
/*   7:    */ import java.util.Enumeration;
/*   8:    */ import java.util.Hashtable;
/*   9:    */ 
/*  10:    */ public class HTMLCodeGenerator
/*  11:    */   extends CodeGenerator
/*  12:    */ {
/*  13: 22 */   protected int syntacticPredLevel = 0;
/*  14: 25 */   protected boolean doingLexRules = false;
/*  15:    */   protected boolean firstElementInAlt;
/*  16: 29 */   protected AlternativeElement prevAltElem = null;
/*  17:    */   
/*  18:    */   public HTMLCodeGenerator()
/*  19:    */   {
/*  20: 37 */     this.charFormatter = new JavaCharFormatter();
/*  21:    */   }
/*  22:    */   
/*  23:    */   static String HTMLEncode(String paramString)
/*  24:    */   {
/*  25: 45 */     StringBuffer localStringBuffer = new StringBuffer();
/*  26:    */     
/*  27: 47 */     int i = 0;
/*  28: 47 */     for (int j = paramString.length(); i < j; i++)
/*  29:    */     {
/*  30: 48 */       char c = paramString.charAt(i);
/*  31: 49 */       if (c == '&') {
/*  32: 50 */         localStringBuffer.append("&amp;");
/*  33: 51 */       } else if (c == '"') {
/*  34: 52 */         localStringBuffer.append("&quot;");
/*  35: 53 */       } else if (c == '\'') {
/*  36: 54 */         localStringBuffer.append("&#039;");
/*  37: 55 */       } else if (c == '<') {
/*  38: 56 */         localStringBuffer.append("&lt;");
/*  39: 57 */       } else if (c == '>') {
/*  40: 58 */         localStringBuffer.append("&gt;");
/*  41:    */       } else {
/*  42: 60 */         localStringBuffer.append(c);
/*  43:    */       }
/*  44:    */     }
/*  45: 62 */     return localStringBuffer.toString();
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void gen()
/*  49:    */   {
/*  50:    */     try
/*  51:    */     {
/*  52: 69 */       Enumeration localEnumeration = this.behavior.grammars.elements();
/*  53: 70 */       while (localEnumeration.hasMoreElements())
/*  54:    */       {
/*  55: 71 */         Grammar localGrammar = (Grammar)localEnumeration.nextElement();
/*  56:    */         
/*  57:    */ 
/*  58:    */ 
/*  59:    */ 
/*  60:    */ 
/*  61:    */ 
/*  62: 78 */         localGrammar.setCodeGenerator(this);
/*  63:    */         
/*  64:    */ 
/*  65: 81 */         localGrammar.generate();
/*  66: 83 */         if (this.antlrTool.hasError()) {
/*  67: 84 */           this.antlrTool.fatalError("Exiting due to errors.");
/*  68:    */         }
/*  69:    */       }
/*  70:    */     }
/*  71:    */     catch (IOException localIOException)
/*  72:    */     {
/*  73: 91 */       this.antlrTool.reportException(localIOException, null);
/*  74:    */     }
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void gen(ActionElement paramActionElement) {}
/*  78:    */   
/*  79:    */   public void gen(AlternativeBlock paramAlternativeBlock)
/*  80:    */   {
/*  81:106 */     genGenericBlock(paramAlternativeBlock, "");
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void gen(BlockEndElement paramBlockEndElement) {}
/*  85:    */   
/*  86:    */   public void gen(CharLiteralElement paramCharLiteralElement)
/*  87:    */   {
/*  88:122 */     if (paramCharLiteralElement.not) {
/*  89:123 */       _print("~");
/*  90:    */     }
/*  91:125 */     _print(HTMLEncode(paramCharLiteralElement.atomText) + " ");
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void gen(CharRangeElement paramCharRangeElement)
/*  95:    */   {
/*  96:132 */     print(paramCharRangeElement.beginText + ".." + paramCharRangeElement.endText + " ");
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void gen(LexerGrammar paramLexerGrammar)
/* 100:    */     throws IOException
/* 101:    */   {
/* 102:137 */     setGrammar(paramLexerGrammar);
/* 103:138 */     this.antlrTool.reportProgress("Generating " + this.grammar.getClassName() + ".html");
/* 104:139 */     this.currentOutput = this.antlrTool.openOutputFile(this.grammar.getClassName() + ".html");
/* 105:    */     
/* 106:    */ 
/* 107:142 */     this.tabs = 0;
/* 108:143 */     this.doingLexRules = true;
/* 109:    */     
/* 110:    */ 
/* 111:146 */     genHeader();
/* 112:    */     
/* 113:    */ 
/* 114:    */ 
/* 115:    */ 
/* 116:    */ 
/* 117:    */ 
/* 118:153 */     println("");
/* 119:156 */     if (this.grammar.comment != null) {
/* 120:157 */       _println(HTMLEncode(this.grammar.comment));
/* 121:    */     }
/* 122:160 */     println("Definition of lexer " + this.grammar.getClassName() + ", which is a subclass of " + this.grammar.getSuperClass() + ".");
/* 123:    */     
/* 124:    */ 
/* 125:    */ 
/* 126:    */ 
/* 127:    */ 
/* 128:    */ 
/* 129:    */ 
/* 130:    */ 
/* 131:    */ 
/* 132:    */ 
/* 133:    */ 
/* 134:    */ 
/* 135:    */ 
/* 136:    */ 
/* 137:    */ 
/* 138:    */ 
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
/* 155:193 */     genNextToken();
/* 156:    */     
/* 157:    */ 
/* 158:    */ 
/* 159:197 */     Enumeration localEnumeration = this.grammar.rules.elements();
/* 160:198 */     while (localEnumeration.hasMoreElements())
/* 161:    */     {
/* 162:199 */       RuleSymbol localRuleSymbol = (RuleSymbol)localEnumeration.nextElement();
/* 163:200 */       if (!localRuleSymbol.id.equals("mnextToken")) {
/* 164:201 */         genRule(localRuleSymbol);
/* 165:    */       }
/* 166:    */     }
/* 167:206 */     this.currentOutput.close();
/* 168:207 */     this.currentOutput = null;
/* 169:208 */     this.doingLexRules = false;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void gen(OneOrMoreBlock paramOneOrMoreBlock)
/* 173:    */   {
/* 174:215 */     genGenericBlock(paramOneOrMoreBlock, "+");
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void gen(ParserGrammar paramParserGrammar)
/* 178:    */     throws IOException
/* 179:    */   {
/* 180:220 */     setGrammar(paramParserGrammar);
/* 181:    */     
/* 182:222 */     this.antlrTool.reportProgress("Generating " + this.grammar.getClassName() + ".html");
/* 183:223 */     this.currentOutput = this.antlrTool.openOutputFile(this.grammar.getClassName() + ".html");
/* 184:    */     
/* 185:225 */     this.tabs = 0;
/* 186:    */     
/* 187:    */ 
/* 188:228 */     genHeader();
/* 189:    */     
/* 190:    */ 
/* 191:231 */     println("");
/* 192:234 */     if (this.grammar.comment != null) {
/* 193:235 */       _println(HTMLEncode(this.grammar.comment));
/* 194:    */     }
/* 195:238 */     println("Definition of parser " + this.grammar.getClassName() + ", which is a subclass of " + this.grammar.getSuperClass() + ".");
/* 196:    */     
/* 197:    */ 
/* 198:241 */     Enumeration localEnumeration = this.grammar.rules.elements();
/* 199:242 */     while (localEnumeration.hasMoreElements())
/* 200:    */     {
/* 201:243 */       println("");
/* 202:    */       
/* 203:245 */       GrammarSymbol localGrammarSymbol = (GrammarSymbol)localEnumeration.nextElement();
/* 204:247 */       if ((localGrammarSymbol instanceof RuleSymbol)) {
/* 205:248 */         genRule((RuleSymbol)localGrammarSymbol);
/* 206:    */       }
/* 207:    */     }
/* 208:251 */     this.tabs -= 1;
/* 209:252 */     println("");
/* 210:    */     
/* 211:254 */     genTail();
/* 212:    */     
/* 213:    */ 
/* 214:257 */     this.currentOutput.close();
/* 215:258 */     this.currentOutput = null;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public void gen(RuleRefElement paramRuleRefElement)
/* 219:    */   {
/* 220:265 */     RuleSymbol localRuleSymbol = (RuleSymbol)this.grammar.getSymbol(paramRuleRefElement.targetRule);
/* 221:    */     
/* 222:    */ 
/* 223:268 */     _print("<a href=\"" + this.grammar.getClassName() + ".html#" + paramRuleRefElement.targetRule + "\">");
/* 224:269 */     _print(paramRuleRefElement.targetRule);
/* 225:270 */     _print("</a>");
/* 226:    */     
/* 227:    */ 
/* 228:    */ 
/* 229:    */ 
/* 230:275 */     _print(" ");
/* 231:    */   }
/* 232:    */   
/* 233:    */   public void gen(StringLiteralElement paramStringLiteralElement)
/* 234:    */   {
/* 235:282 */     if (paramStringLiteralElement.not) {
/* 236:283 */       _print("~");
/* 237:    */     }
/* 238:285 */     _print(HTMLEncode(paramStringLiteralElement.atomText));
/* 239:286 */     _print(" ");
/* 240:    */   }
/* 241:    */   
/* 242:    */   public void gen(TokenRangeElement paramTokenRangeElement)
/* 243:    */   {
/* 244:293 */     print(paramTokenRangeElement.beginText + ".." + paramTokenRangeElement.endText + " ");
/* 245:    */   }
/* 246:    */   
/* 247:    */   public void gen(TokenRefElement paramTokenRefElement)
/* 248:    */   {
/* 249:300 */     if (paramTokenRefElement.not) {
/* 250:301 */       _print("~");
/* 251:    */     }
/* 252:303 */     _print(paramTokenRefElement.atomText);
/* 253:304 */     _print(" ");
/* 254:    */   }
/* 255:    */   
/* 256:    */   public void gen(TreeElement paramTreeElement)
/* 257:    */   {
/* 258:308 */     print(paramTreeElement + " ");
/* 259:    */   }
/* 260:    */   
/* 261:    */   public void gen(TreeWalkerGrammar paramTreeWalkerGrammar)
/* 262:    */     throws IOException
/* 263:    */   {
/* 264:313 */     setGrammar(paramTreeWalkerGrammar);
/* 265:    */     
/* 266:315 */     this.antlrTool.reportProgress("Generating " + this.grammar.getClassName() + ".html");
/* 267:316 */     this.currentOutput = this.antlrTool.openOutputFile(this.grammar.getClassName() + ".html");
/* 268:    */     
/* 269:    */ 
/* 270:319 */     this.tabs = 0;
/* 271:    */     
/* 272:    */ 
/* 273:322 */     genHeader();
/* 274:    */     
/* 275:    */ 
/* 276:325 */     println("");
/* 277:    */     
/* 278:    */ 
/* 279:    */ 
/* 280:    */ 
/* 281:    */ 
/* 282:    */ 
/* 283:    */ 
/* 284:    */ 
/* 285:334 */     println("");
/* 286:337 */     if (this.grammar.comment != null) {
/* 287:338 */       _println(HTMLEncode(this.grammar.comment));
/* 288:    */     }
/* 289:341 */     println("Definition of tree parser " + this.grammar.getClassName() + ", which is a subclass of " + this.grammar.getSuperClass() + ".");
/* 290:    */     
/* 291:    */ 
/* 292:    */ 
/* 293:    */ 
/* 294:    */ 
/* 295:    */ 
/* 296:    */ 
/* 297:    */ 
/* 298:    */ 
/* 299:    */ 
/* 300:    */ 
/* 301:353 */     println("");
/* 302:    */     
/* 303:355 */     this.tabs += 1;
/* 304:    */     
/* 305:    */ 
/* 306:358 */     Enumeration localEnumeration = this.grammar.rules.elements();
/* 307:359 */     while (localEnumeration.hasMoreElements())
/* 308:    */     {
/* 309:360 */       println("");
/* 310:    */       
/* 311:362 */       GrammarSymbol localGrammarSymbol = (GrammarSymbol)localEnumeration.nextElement();
/* 312:364 */       if ((localGrammarSymbol instanceof RuleSymbol)) {
/* 313:365 */         genRule((RuleSymbol)localGrammarSymbol);
/* 314:    */       }
/* 315:    */     }
/* 316:368 */     this.tabs -= 1;
/* 317:369 */     println("");
/* 318:    */     
/* 319:    */ 
/* 320:    */ 
/* 321:    */ 
/* 322:    */ 
/* 323:    */ 
/* 324:376 */     this.currentOutput.close();
/* 325:377 */     this.currentOutput = null;
/* 326:    */   }
/* 327:    */   
/* 328:    */   public void gen(WildcardElement paramWildcardElement)
/* 329:    */   {
/* 330:387 */     _print(". ");
/* 331:    */   }
/* 332:    */   
/* 333:    */   public void gen(ZeroOrMoreBlock paramZeroOrMoreBlock)
/* 334:    */   {
/* 335:394 */     genGenericBlock(paramZeroOrMoreBlock, "*");
/* 336:    */   }
/* 337:    */   
/* 338:    */   protected void genAlt(Alternative paramAlternative)
/* 339:    */   {
/* 340:398 */     if (paramAlternative.getTreeSpecifier() != null) {
/* 341:399 */       _print(paramAlternative.getTreeSpecifier().getText());
/* 342:    */     }
/* 343:401 */     this.prevAltElem = null;
/* 344:402 */     for (AlternativeElement localAlternativeElement = paramAlternative.head; !(localAlternativeElement instanceof BlockEndElement); localAlternativeElement = localAlternativeElement.next)
/* 345:    */     {
/* 346:405 */       localAlternativeElement.generate();
/* 347:406 */       this.firstElementInAlt = false;
/* 348:407 */       this.prevAltElem = localAlternativeElement;
/* 349:    */     }
/* 350:    */   }
/* 351:    */   
/* 352:    */   public void genCommonBlock(AlternativeBlock paramAlternativeBlock)
/* 353:    */   {
/* 354:428 */     for (int i = 0; i < paramAlternativeBlock.alternatives.size(); i++)
/* 355:    */     {
/* 356:429 */       Alternative localAlternative = paramAlternativeBlock.getAlternativeAt(i);
/* 357:430 */       AlternativeElement localAlternativeElement = localAlternative.head;
/* 358:433 */       if ((i > 0) && (paramAlternativeBlock.alternatives.size() > 1))
/* 359:    */       {
/* 360:434 */         _println("");
/* 361:435 */         print("|\t");
/* 362:    */       }
/* 363:440 */       boolean bool = this.firstElementInAlt;
/* 364:441 */       this.firstElementInAlt = true;
/* 365:442 */       this.tabs += 1;
/* 366:    */       
/* 367:    */ 
/* 368:    */ 
/* 369:    */ 
/* 370:    */ 
/* 371:    */ 
/* 372:    */ 
/* 373:    */ 
/* 374:    */ 
/* 375:    */ 
/* 376:    */ 
/* 377:    */ 
/* 378:    */ 
/* 379:456 */       genAlt(localAlternative);
/* 380:457 */       this.tabs -= 1;
/* 381:458 */       this.firstElementInAlt = bool;
/* 382:    */     }
/* 383:    */   }
/* 384:    */   
/* 385:    */   public void genFollowSetForRuleBlock(RuleBlock paramRuleBlock)
/* 386:    */   {
/* 387:467 */     Lookahead localLookahead = this.grammar.theLLkAnalyzer.FOLLOW(1, paramRuleBlock.endNode);
/* 388:468 */     printSet(this.grammar.maxk, 1, localLookahead);
/* 389:    */   }
/* 390:    */   
/* 391:    */   protected void genGenericBlock(AlternativeBlock paramAlternativeBlock, String paramString)
/* 392:    */   {
/* 393:472 */     if (paramAlternativeBlock.alternatives.size() > 1)
/* 394:    */     {
/* 395:474 */       if (!this.firstElementInAlt)
/* 396:    */       {
/* 397:476 */         if ((this.prevAltElem == null) || (!(this.prevAltElem instanceof AlternativeBlock)) || (((AlternativeBlock)this.prevAltElem).alternatives.size() == 1))
/* 398:    */         {
/* 399:479 */           _println("");
/* 400:480 */           print("(\t");
/* 401:    */         }
/* 402:    */         else
/* 403:    */         {
/* 404:483 */           _print("(\t");
/* 405:    */         }
/* 406:    */       }
/* 407:    */       else {
/* 408:489 */         _print("(\t");
/* 409:    */       }
/* 410:    */     }
/* 411:    */     else {
/* 412:493 */       _print("( ");
/* 413:    */     }
/* 414:497 */     genCommonBlock(paramAlternativeBlock);
/* 415:498 */     if (paramAlternativeBlock.alternatives.size() > 1)
/* 416:    */     {
/* 417:499 */       _println("");
/* 418:500 */       print(")" + paramString + " ");
/* 419:502 */       if (!(paramAlternativeBlock.next instanceof BlockEndElement))
/* 420:    */       {
/* 421:503 */         _println("");
/* 422:504 */         print("");
/* 423:    */       }
/* 424:    */     }
/* 425:    */     else
/* 426:    */     {
/* 427:508 */       _print(")" + paramString + " ");
/* 428:    */     }
/* 429:    */   }
/* 430:    */   
/* 431:    */   protected void genHeader()
/* 432:    */   {
/* 433:514 */     println("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
/* 434:515 */     println("<HTML>");
/* 435:516 */     println("<HEAD>");
/* 436:517 */     println("<TITLE>Grammar " + this.antlrTool.grammarFile + "</TITLE>");
/* 437:518 */     println("</HEAD>");
/* 438:519 */     println("<BODY>");
/* 439:520 */     println("<table summary=\"\" border=\"1\" cellpadding=\"5\">");
/* 440:521 */     println("<tr>");
/* 441:522 */     println("<td>");
/* 442:523 */     println("<font size=\"+2\">Grammar " + this.grammar.getClassName() + "</font><br>");
/* 443:524 */     println("<a href=\"http://www.ANTLR.org\">ANTLR</a>-generated HTML file from " + this.antlrTool.grammarFile);
/* 444:525 */     println("<p>");
/* 445:526 */     println("Terence Parr, <a href=\"http://www.magelang.com\">MageLang Institute</a>");
/* 446:527 */     println("<br>ANTLR Version " + Tool.version + "; 1989-2005");
/* 447:528 */     println("</td>");
/* 448:529 */     println("</tr>");
/* 449:530 */     println("</table>");
/* 450:531 */     println("<PRE>");
/* 451:    */   }
/* 452:    */   
/* 453:    */   protected void genLookaheadSetForAlt(Alternative paramAlternative)
/* 454:    */   {
/* 455:540 */     if ((this.doingLexRules) && (paramAlternative.cache[1].containsEpsilon()))
/* 456:    */     {
/* 457:541 */       println("MATCHES ALL");
/* 458:542 */       return;
/* 459:    */     }
/* 460:544 */     int i = paramAlternative.lookaheadDepth;
/* 461:545 */     if (i == 2147483647) {
/* 462:548 */       i = this.grammar.maxk;
/* 463:    */     }
/* 464:550 */     for (int j = 1; j <= i; j++)
/* 465:    */     {
/* 466:551 */       Lookahead localLookahead = paramAlternative.cache[j];
/* 467:552 */       printSet(i, j, localLookahead);
/* 468:    */     }
/* 469:    */   }
/* 470:    */   
/* 471:    */   public void genLookaheadSetForBlock(AlternativeBlock paramAlternativeBlock)
/* 472:    */   {
/* 473:562 */     int i = 0;
/* 474:    */     Object localObject;
/* 475:563 */     for (int j = 0; j < paramAlternativeBlock.alternatives.size(); j++)
/* 476:    */     {
/* 477:564 */       localObject = paramAlternativeBlock.getAlternativeAt(j);
/* 478:565 */       if (((Alternative)localObject).lookaheadDepth == 2147483647)
/* 479:    */       {
/* 480:566 */         i = this.grammar.maxk;
/* 481:567 */         break;
/* 482:    */       }
/* 483:569 */       if (i < ((Alternative)localObject).lookaheadDepth) {
/* 484:570 */         i = ((Alternative)localObject).lookaheadDepth;
/* 485:    */       }
/* 486:    */     }
/* 487:574 */     for (j = 1; j <= i; j++)
/* 488:    */     {
/* 489:575 */       localObject = this.grammar.theLLkAnalyzer.look(j, paramAlternativeBlock);
/* 490:576 */       printSet(i, j, (Lookahead)localObject);
/* 491:    */     }
/* 492:    */   }
/* 493:    */   
/* 494:    */   public void genNextToken()
/* 495:    */   {
/* 496:585 */     println("");
/* 497:586 */     println("/** Lexer nextToken rule:");
/* 498:587 */     println(" *  The lexer nextToken rule is synthesized from all of the user-defined");
/* 499:588 */     println(" *  lexer rules.  It logically consists of one big alternative block with");
/* 500:589 */     println(" *  each user-defined rule being an alternative.");
/* 501:590 */     println(" */");
/* 502:    */     
/* 503:    */ 
/* 504:    */ 
/* 505:594 */     RuleBlock localRuleBlock = MakeGrammar.createNextTokenRule(this.grammar, this.grammar.rules, "nextToken");
/* 506:    */     
/* 507:    */ 
/* 508:597 */     RuleSymbol localRuleSymbol = new RuleSymbol("mnextToken");
/* 509:598 */     localRuleSymbol.setDefined();
/* 510:599 */     localRuleSymbol.setBlock(localRuleBlock);
/* 511:600 */     localRuleSymbol.access = "private";
/* 512:601 */     this.grammar.define(localRuleSymbol);
/* 513:    */     
/* 514:    */ 
/* 515:    */ 
/* 516:    */ 
/* 517:    */ 
/* 518:    */ 
/* 519:    */ 
/* 520:    */ 
/* 521:    */ 
/* 522:    */ 
/* 523:    */ 
/* 524:    */ 
/* 525:614 */     genCommonBlock(localRuleBlock);
/* 526:    */   }
/* 527:    */   
/* 528:    */   public void genRule(RuleSymbol paramRuleSymbol)
/* 529:    */   {
/* 530:621 */     if ((paramRuleSymbol == null) || (!paramRuleSymbol.isDefined())) {
/* 531:621 */       return;
/* 532:    */     }
/* 533:622 */     println("");
/* 534:623 */     if (paramRuleSymbol.comment != null) {
/* 535:624 */       _println(HTMLEncode(paramRuleSymbol.comment));
/* 536:    */     }
/* 537:626 */     if ((paramRuleSymbol.access.length() != 0) && 
/* 538:627 */       (!paramRuleSymbol.access.equals("public"))) {
/* 539:628 */       _print(paramRuleSymbol.access + " ");
/* 540:    */     }
/* 541:631 */     _print("<a name=\"" + paramRuleSymbol.getId() + "\">");
/* 542:632 */     _print(paramRuleSymbol.getId());
/* 543:633 */     _print("</a>");
/* 544:    */     
/* 545:    */ 
/* 546:636 */     RuleBlock localRuleBlock = paramRuleSymbol.getBlock();
/* 547:    */     
/* 548:    */ 
/* 549:    */ 
/* 550:    */ 
/* 551:    */ 
/* 552:    */ 
/* 553:    */ 
/* 554:    */ 
/* 555:    */ 
/* 556:    */ 
/* 557:    */ 
/* 558:648 */     _println("");
/* 559:649 */     this.tabs += 1;
/* 560:650 */     print(":\t");
/* 561:    */     
/* 562:    */ 
/* 563:    */ 
/* 564:    */ 
/* 565:    */ 
/* 566:656 */     genCommonBlock(localRuleBlock);
/* 567:    */     
/* 568:658 */     _println("");
/* 569:659 */     println(";");
/* 570:660 */     this.tabs -= 1;
/* 571:    */   }
/* 572:    */   
/* 573:    */   protected void genSynPred(SynPredBlock paramSynPredBlock)
/* 574:    */   {
/* 575:668 */     this.syntacticPredLevel += 1;
/* 576:669 */     genGenericBlock(paramSynPredBlock, " =>");
/* 577:670 */     this.syntacticPredLevel -= 1;
/* 578:    */   }
/* 579:    */   
/* 580:    */   public void genTail()
/* 581:    */   {
/* 582:674 */     println("</PRE>");
/* 583:675 */     println("</BODY>");
/* 584:676 */     println("</HTML>");
/* 585:    */   }
/* 586:    */   
/* 587:    */   protected void genTokenTypes(TokenManager paramTokenManager)
/* 588:    */     throws IOException
/* 589:    */   {
/* 590:682 */     this.antlrTool.reportProgress("Generating " + paramTokenManager.getName() + TokenTypesFileSuffix + TokenTypesFileExt);
/* 591:683 */     this.currentOutput = this.antlrTool.openOutputFile(paramTokenManager.getName() + TokenTypesFileSuffix + TokenTypesFileExt);
/* 592:    */     
/* 593:685 */     this.tabs = 0;
/* 594:    */     
/* 595:    */ 
/* 596:688 */     genHeader();
/* 597:    */     
/* 598:    */ 
/* 599:    */ 
/* 600:692 */     println("");
/* 601:693 */     println("*** Tokens used by the parser");
/* 602:694 */     println("This is a list of the token numeric values and the corresponding");
/* 603:695 */     println("token identifiers.  Some tokens are literals, and because of that");
/* 604:696 */     println("they have no identifiers.  Literals are double-quoted.");
/* 605:697 */     this.tabs += 1;
/* 606:    */     
/* 607:    */ 
/* 608:700 */     Vector localVector = paramTokenManager.getVocabulary();
/* 609:701 */     for (int i = 4; i < localVector.size(); i++)
/* 610:    */     {
/* 611:702 */       String str = (String)localVector.elementAt(i);
/* 612:703 */       if (str != null) {
/* 613:704 */         println(str + " = " + i);
/* 614:    */       }
/* 615:    */     }
/* 616:709 */     this.tabs -= 1;
/* 617:710 */     println("*** End of tokens used by the parser");
/* 618:    */     
/* 619:    */ 
/* 620:713 */     this.currentOutput.close();
/* 621:714 */     this.currentOutput = null;
/* 622:    */   }
/* 623:    */   
/* 624:    */   public String getASTCreateString(Vector paramVector)
/* 625:    */   {
/* 626:721 */     return null;
/* 627:    */   }
/* 628:    */   
/* 629:    */   public String getASTCreateString(GrammarAtom paramGrammarAtom, String paramString)
/* 630:    */   {
/* 631:728 */     return null;
/* 632:    */   }
/* 633:    */   
/* 634:    */   public String mapTreeId(String paramString, ActionTransInfo paramActionTransInfo)
/* 635:    */   {
/* 636:738 */     return paramString;
/* 637:    */   }
/* 638:    */   
/* 639:    */   protected String processActionForSpecialSymbols(String paramString, int paramInt, RuleBlock paramRuleBlock, ActionTransInfo paramActionTransInfo)
/* 640:    */   {
/* 641:746 */     return paramString;
/* 642:    */   }
/* 643:    */   
/* 644:    */   public void printSet(int paramInt1, int paramInt2, Lookahead paramLookahead)
/* 645:    */   {
/* 646:755 */     int i = 5;
/* 647:    */     
/* 648:757 */     int[] arrayOfInt = paramLookahead.fset.toArray();
/* 649:759 */     if (paramInt1 != 1) {
/* 650:760 */       print("k==" + paramInt2 + ": {");
/* 651:    */     } else {
/* 652:763 */       print("{ ");
/* 653:    */     }
/* 654:765 */     if (arrayOfInt.length > i)
/* 655:    */     {
/* 656:766 */       _println("");
/* 657:767 */       this.tabs += 1;
/* 658:768 */       print("");
/* 659:    */     }
/* 660:771 */     int j = 0;
/* 661:772 */     for (int k = 0; k < arrayOfInt.length; k++)
/* 662:    */     {
/* 663:773 */       j++;
/* 664:774 */       if (j > i)
/* 665:    */       {
/* 666:775 */         _println("");
/* 667:776 */         print("");
/* 668:777 */         j = 0;
/* 669:    */       }
/* 670:779 */       if (this.doingLexRules) {
/* 671:780 */         _print(this.charFormatter.literalChar(arrayOfInt[k]));
/* 672:    */       } else {
/* 673:783 */         _print((String)this.grammar.tokenManager.getVocabulary().elementAt(arrayOfInt[k]));
/* 674:    */       }
/* 675:785 */       if (k != arrayOfInt.length - 1) {
/* 676:786 */         _print(", ");
/* 677:    */       }
/* 678:    */     }
/* 679:790 */     if (arrayOfInt.length > i)
/* 680:    */     {
/* 681:791 */       _println("");
/* 682:792 */       this.tabs -= 1;
/* 683:793 */       print("");
/* 684:    */     }
/* 685:795 */     _println(" }");
/* 686:    */   }
/* 687:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.HTMLCodeGenerator
 * JD-Core Version:    0.7.0.1
 */