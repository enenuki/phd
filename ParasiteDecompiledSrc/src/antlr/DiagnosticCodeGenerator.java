/*   1:    */ package antlr;
/*   2:    */ 
/*   3:    */ import antlr.collections.impl.BitSet;
/*   4:    */ import antlr.collections.impl.Vector;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.io.PrintWriter;
/*   7:    */ import java.util.Enumeration;
/*   8:    */ import java.util.Hashtable;
/*   9:    */ 
/*  10:    */ public class DiagnosticCodeGenerator
/*  11:    */   extends CodeGenerator
/*  12:    */ {
/*  13: 22 */   protected int syntacticPredLevel = 0;
/*  14: 25 */   protected boolean doingLexRules = false;
/*  15:    */   
/*  16:    */   public DiagnosticCodeGenerator()
/*  17:    */   {
/*  18: 33 */     this.charFormatter = new JavaCharFormatter();
/*  19:    */   }
/*  20:    */   
/*  21:    */   public void gen()
/*  22:    */   {
/*  23:    */     try
/*  24:    */     {
/*  25: 42 */       Enumeration localEnumeration = this.behavior.grammars.elements();
/*  26: 43 */       while (localEnumeration.hasMoreElements())
/*  27:    */       {
/*  28: 44 */         localObject = (Grammar)localEnumeration.nextElement();
/*  29:    */         
/*  30:    */ 
/*  31: 47 */         ((Grammar)localObject).setGrammarAnalyzer(this.analyzer);
/*  32: 48 */         ((Grammar)localObject).setCodeGenerator(this);
/*  33: 49 */         this.analyzer.setGrammar((Grammar)localObject);
/*  34:    */         
/*  35:    */ 
/*  36: 52 */         ((Grammar)localObject).generate();
/*  37: 54 */         if (this.antlrTool.hasError()) {
/*  38: 55 */           this.antlrTool.panic("Exiting due to errors.");
/*  39:    */         }
/*  40:    */       }
/*  41: 61 */       Object localObject = this.behavior.tokenManagers.elements();
/*  42: 62 */       while (((Enumeration)localObject).hasMoreElements())
/*  43:    */       {
/*  44: 63 */         TokenManager localTokenManager = (TokenManager)((Enumeration)localObject).nextElement();
/*  45: 64 */         if (!localTokenManager.isReadOnly()) {
/*  46: 66 */           genTokenTypes(localTokenManager);
/*  47:    */         }
/*  48:    */       }
/*  49:    */     }
/*  50:    */     catch (IOException localIOException)
/*  51:    */     {
/*  52: 71 */       this.antlrTool.reportException(localIOException, null);
/*  53:    */     }
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void gen(ActionElement paramActionElement)
/*  57:    */   {
/*  58: 79 */     if (!paramActionElement.isSemPred)
/*  59:    */     {
/*  60: 83 */       print("ACTION: ");
/*  61: 84 */       _printAction(paramActionElement.actionText);
/*  62:    */     }
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void gen(AlternativeBlock paramAlternativeBlock)
/*  66:    */   {
/*  67: 92 */     println("Start of alternative block.");
/*  68: 93 */     this.tabs += 1;
/*  69: 94 */     genBlockPreamble(paramAlternativeBlock);
/*  70:    */     
/*  71: 96 */     boolean bool = this.grammar.theLLkAnalyzer.deterministic(paramAlternativeBlock);
/*  72: 97 */     if (!bool) {
/*  73: 98 */       println("Warning: This alternative block is non-deterministic");
/*  74:    */     }
/*  75:100 */     genCommonBlock(paramAlternativeBlock);
/*  76:101 */     this.tabs -= 1;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void gen(BlockEndElement paramBlockEndElement) {}
/*  80:    */   
/*  81:    */   public void gen(CharLiteralElement paramCharLiteralElement)
/*  82:    */   {
/*  83:117 */     print("Match character ");
/*  84:118 */     if (paramCharLiteralElement.not) {
/*  85:119 */       _print("NOT ");
/*  86:    */     }
/*  87:121 */     _print(paramCharLiteralElement.atomText);
/*  88:122 */     if (paramCharLiteralElement.label != null) {
/*  89:123 */       _print(", label=" + paramCharLiteralElement.label);
/*  90:    */     }
/*  91:125 */     _println("");
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void gen(CharRangeElement paramCharRangeElement)
/*  95:    */   {
/*  96:132 */     print("Match character range: " + paramCharRangeElement.beginText + ".." + paramCharRangeElement.endText);
/*  97:133 */     if (paramCharRangeElement.label != null) {
/*  98:134 */       _print(", label = " + paramCharRangeElement.label);
/*  99:    */     }
/* 100:136 */     _println("");
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void gen(LexerGrammar paramLexerGrammar)
/* 104:    */     throws IOException
/* 105:    */   {
/* 106:141 */     setGrammar(paramLexerGrammar);
/* 107:142 */     this.antlrTool.reportProgress("Generating " + this.grammar.getClassName() + TokenTypesFileExt);
/* 108:143 */     this.currentOutput = this.antlrTool.openOutputFile(this.grammar.getClassName() + TokenTypesFileExt);
/* 109:    */     
/* 110:    */ 
/* 111:146 */     this.tabs = 0;
/* 112:147 */     this.doingLexRules = true;
/* 113:    */     
/* 114:    */ 
/* 115:150 */     genHeader();
/* 116:    */     
/* 117:    */ 
/* 118:153 */     println("");
/* 119:154 */     println("*** Lexer Preamble Action.");
/* 120:155 */     println("This action will appear before the declaration of your lexer class:");
/* 121:156 */     this.tabs += 1;
/* 122:157 */     println(this.grammar.preambleAction.getText());
/* 123:158 */     this.tabs -= 1;
/* 124:159 */     println("*** End of Lexer Preamble Action");
/* 125:    */     
/* 126:    */ 
/* 127:162 */     println("");
/* 128:163 */     println("*** Your lexer class is called '" + this.grammar.getClassName() + "' and is a subclass of '" + this.grammar.getSuperClass() + "'.");
/* 129:    */     
/* 130:    */ 
/* 131:166 */     println("");
/* 132:167 */     println("*** User-defined lexer  class members:");
/* 133:168 */     println("These are the member declarations that you defined for your class:");
/* 134:169 */     this.tabs += 1;
/* 135:170 */     printAction(this.grammar.classMemberAction.getText());
/* 136:171 */     this.tabs -= 1;
/* 137:172 */     println("*** End of user-defined lexer class members");
/* 138:    */     
/* 139:    */ 
/* 140:175 */     println("");
/* 141:176 */     println("*** String literals used in the parser");
/* 142:177 */     println("The following string literals were used in the parser.");
/* 143:178 */     println("An actual code generator would arrange to place these literals");
/* 144:179 */     println("into a table in the generated lexer, so that actions in the");
/* 145:180 */     println("generated lexer could match token text against the literals.");
/* 146:181 */     println("String literals used in the lexer are not listed here, as they");
/* 147:182 */     println("are incorporated into the mainstream lexer processing.");
/* 148:183 */     this.tabs += 1;
/* 149:    */     
/* 150:185 */     Enumeration localEnumeration = this.grammar.getSymbols();
/* 151:    */     Object localObject;
/* 152:186 */     while (localEnumeration.hasMoreElements())
/* 153:    */     {
/* 154:187 */       localObject = (GrammarSymbol)localEnumeration.nextElement();
/* 155:189 */       if ((localObject instanceof StringLiteralSymbol))
/* 156:    */       {
/* 157:190 */         StringLiteralSymbol localStringLiteralSymbol = (StringLiteralSymbol)localObject;
/* 158:191 */         println(localStringLiteralSymbol.getId() + " = " + localStringLiteralSymbol.getTokenType());
/* 159:    */       }
/* 160:    */     }
/* 161:194 */     this.tabs -= 1;
/* 162:195 */     println("*** End of string literals used by the parser");
/* 163:    */     
/* 164:    */ 
/* 165:    */ 
/* 166:    */ 
/* 167:200 */     genNextToken();
/* 168:    */     
/* 169:    */ 
/* 170:203 */     println("");
/* 171:204 */     println("*** User-defined Lexer rules:");
/* 172:205 */     this.tabs += 1;
/* 173:    */     
/* 174:207 */     localEnumeration = this.grammar.rules.elements();
/* 175:208 */     while (localEnumeration.hasMoreElements())
/* 176:    */     {
/* 177:209 */       localObject = (RuleSymbol)localEnumeration.nextElement();
/* 178:210 */       if (!((RuleSymbol)localObject).id.equals("mnextToken")) {
/* 179:211 */         genRule((RuleSymbol)localObject);
/* 180:    */       }
/* 181:    */     }
/* 182:215 */     this.tabs -= 1;
/* 183:216 */     println("");
/* 184:217 */     println("*** End User-defined Lexer rules:");
/* 185:    */     
/* 186:    */ 
/* 187:220 */     this.currentOutput.close();
/* 188:221 */     this.currentOutput = null;
/* 189:222 */     this.doingLexRules = false;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void gen(OneOrMoreBlock paramOneOrMoreBlock)
/* 193:    */   {
/* 194:229 */     println("Start ONE-OR-MORE (...)+ block:");
/* 195:230 */     this.tabs += 1;
/* 196:231 */     genBlockPreamble(paramOneOrMoreBlock);
/* 197:232 */     boolean bool = this.grammar.theLLkAnalyzer.deterministic(paramOneOrMoreBlock);
/* 198:233 */     if (!bool) {
/* 199:234 */       println("Warning: This one-or-more block is non-deterministic");
/* 200:    */     }
/* 201:236 */     genCommonBlock(paramOneOrMoreBlock);
/* 202:237 */     this.tabs -= 1;
/* 203:238 */     println("End ONE-OR-MORE block.");
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void gen(ParserGrammar paramParserGrammar)
/* 207:    */     throws IOException
/* 208:    */   {
/* 209:243 */     setGrammar(paramParserGrammar);
/* 210:    */     
/* 211:245 */     this.antlrTool.reportProgress("Generating " + this.grammar.getClassName() + TokenTypesFileExt);
/* 212:246 */     this.currentOutput = this.antlrTool.openOutputFile(this.grammar.getClassName() + TokenTypesFileExt);
/* 213:    */     
/* 214:    */ 
/* 215:249 */     this.tabs = 0;
/* 216:    */     
/* 217:    */ 
/* 218:252 */     genHeader();
/* 219:    */     
/* 220:    */ 
/* 221:255 */     println("");
/* 222:256 */     println("*** Parser Preamble Action.");
/* 223:257 */     println("This action will appear before the declaration of your parser class:");
/* 224:258 */     this.tabs += 1;
/* 225:259 */     println(this.grammar.preambleAction.getText());
/* 226:260 */     this.tabs -= 1;
/* 227:261 */     println("*** End of Parser Preamble Action");
/* 228:    */     
/* 229:    */ 
/* 230:264 */     println("");
/* 231:265 */     println("*** Your parser class is called '" + this.grammar.getClassName() + "' and is a subclass of '" + this.grammar.getSuperClass() + "'.");
/* 232:    */     
/* 233:    */ 
/* 234:268 */     println("");
/* 235:269 */     println("*** User-defined parser class members:");
/* 236:270 */     println("These are the member declarations that you defined for your class:");
/* 237:271 */     this.tabs += 1;
/* 238:272 */     printAction(this.grammar.classMemberAction.getText());
/* 239:273 */     this.tabs -= 1;
/* 240:274 */     println("*** End of user-defined parser class members");
/* 241:    */     
/* 242:    */ 
/* 243:277 */     println("");
/* 244:278 */     println("*** Parser rules:");
/* 245:279 */     this.tabs += 1;
/* 246:    */     
/* 247:    */ 
/* 248:282 */     Enumeration localEnumeration = this.grammar.rules.elements();
/* 249:283 */     while (localEnumeration.hasMoreElements())
/* 250:    */     {
/* 251:284 */       println("");
/* 252:    */       
/* 253:286 */       GrammarSymbol localGrammarSymbol = (GrammarSymbol)localEnumeration.nextElement();
/* 254:288 */       if ((localGrammarSymbol instanceof RuleSymbol)) {
/* 255:289 */         genRule((RuleSymbol)localGrammarSymbol);
/* 256:    */       }
/* 257:    */     }
/* 258:292 */     this.tabs -= 1;
/* 259:293 */     println("");
/* 260:294 */     println("*** End of parser rules");
/* 261:    */     
/* 262:296 */     println("");
/* 263:297 */     println("*** End of parser");
/* 264:    */     
/* 265:    */ 
/* 266:300 */     this.currentOutput.close();
/* 267:301 */     this.currentOutput = null;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public void gen(RuleRefElement paramRuleRefElement)
/* 271:    */   {
/* 272:308 */     RuleSymbol localRuleSymbol = (RuleSymbol)this.grammar.getSymbol(paramRuleRefElement.targetRule);
/* 273:    */     
/* 274:    */ 
/* 275:311 */     print("Rule Reference: " + paramRuleRefElement.targetRule);
/* 276:312 */     if (paramRuleRefElement.idAssign != null) {
/* 277:313 */       _print(", assigned to '" + paramRuleRefElement.idAssign + "'");
/* 278:    */     }
/* 279:315 */     if (paramRuleRefElement.args != null) {
/* 280:316 */       _print(", arguments = " + paramRuleRefElement.args);
/* 281:    */     }
/* 282:318 */     _println("");
/* 283:321 */     if ((localRuleSymbol == null) || (!localRuleSymbol.isDefined()))
/* 284:    */     {
/* 285:322 */       println("Rule '" + paramRuleRefElement.targetRule + "' is referenced, but that rule is not defined.");
/* 286:323 */       println("\tPerhaps the rule is misspelled, or you forgot to define it.");
/* 287:324 */       return;
/* 288:    */     }
/* 289:326 */     if (!(localRuleSymbol instanceof RuleSymbol))
/* 290:    */     {
/* 291:328 */       println("Rule '" + paramRuleRefElement.targetRule + "' is referenced, but that is not a grammar rule.");
/* 292:329 */       return;
/* 293:    */     }
/* 294:331 */     if (paramRuleRefElement.idAssign != null)
/* 295:    */     {
/* 296:333 */       if (localRuleSymbol.block.returnAction == null) {
/* 297:334 */         println("Error: You assigned from Rule '" + paramRuleRefElement.targetRule + "', but that rule has no return type.");
/* 298:    */       }
/* 299:    */     }
/* 300:339 */     else if ((!(this.grammar instanceof LexerGrammar)) && (this.syntacticPredLevel == 0) && (localRuleSymbol.block.returnAction != null)) {
/* 301:340 */       println("Warning: Rule '" + paramRuleRefElement.targetRule + "' returns a value");
/* 302:    */     }
/* 303:343 */     if ((paramRuleRefElement.args != null) && (localRuleSymbol.block.argAction == null)) {
/* 304:344 */       println("Error: Rule '" + paramRuleRefElement.targetRule + "' accepts no arguments.");
/* 305:    */     }
/* 306:    */   }
/* 307:    */   
/* 308:    */   public void gen(StringLiteralElement paramStringLiteralElement)
/* 309:    */   {
/* 310:352 */     print("Match string literal ");
/* 311:353 */     _print(paramStringLiteralElement.atomText);
/* 312:354 */     if (paramStringLiteralElement.label != null) {
/* 313:355 */       _print(", label=" + paramStringLiteralElement.label);
/* 314:    */     }
/* 315:357 */     _println("");
/* 316:    */   }
/* 317:    */   
/* 318:    */   public void gen(TokenRangeElement paramTokenRangeElement)
/* 319:    */   {
/* 320:364 */     print("Match token range: " + paramTokenRangeElement.beginText + ".." + paramTokenRangeElement.endText);
/* 321:365 */     if (paramTokenRangeElement.label != null) {
/* 322:366 */       _print(", label = " + paramTokenRangeElement.label);
/* 323:    */     }
/* 324:368 */     _println("");
/* 325:    */   }
/* 326:    */   
/* 327:    */   public void gen(TokenRefElement paramTokenRefElement)
/* 328:    */   {
/* 329:375 */     print("Match token ");
/* 330:376 */     if (paramTokenRefElement.not) {
/* 331:377 */       _print("NOT ");
/* 332:    */     }
/* 333:379 */     _print(paramTokenRefElement.atomText);
/* 334:380 */     if (paramTokenRefElement.label != null) {
/* 335:381 */       _print(", label=" + paramTokenRefElement.label);
/* 336:    */     }
/* 337:383 */     _println("");
/* 338:    */   }
/* 339:    */   
/* 340:    */   public void gen(TreeElement paramTreeElement)
/* 341:    */   {
/* 342:387 */     print("Tree reference: " + paramTreeElement);
/* 343:    */   }
/* 344:    */   
/* 345:    */   public void gen(TreeWalkerGrammar paramTreeWalkerGrammar)
/* 346:    */     throws IOException
/* 347:    */   {
/* 348:392 */     setGrammar(paramTreeWalkerGrammar);
/* 349:    */     
/* 350:394 */     this.antlrTool.reportProgress("Generating " + this.grammar.getClassName() + TokenTypesFileExt);
/* 351:395 */     this.currentOutput = this.antlrTool.openOutputFile(this.grammar.getClassName() + TokenTypesFileExt);
/* 352:    */     
/* 353:    */ 
/* 354:398 */     this.tabs = 0;
/* 355:    */     
/* 356:    */ 
/* 357:401 */     genHeader();
/* 358:    */     
/* 359:    */ 
/* 360:404 */     println("");
/* 361:405 */     println("*** Tree-walker Preamble Action.");
/* 362:406 */     println("This action will appear before the declaration of your tree-walker class:");
/* 363:407 */     this.tabs += 1;
/* 364:408 */     println(this.grammar.preambleAction.getText());
/* 365:409 */     this.tabs -= 1;
/* 366:410 */     println("*** End of tree-walker Preamble Action");
/* 367:    */     
/* 368:    */ 
/* 369:413 */     println("");
/* 370:414 */     println("*** Your tree-walker class is called '" + this.grammar.getClassName() + "' and is a subclass of '" + this.grammar.getSuperClass() + "'.");
/* 371:    */     
/* 372:    */ 
/* 373:417 */     println("");
/* 374:418 */     println("*** User-defined tree-walker class members:");
/* 375:419 */     println("These are the member declarations that you defined for your class:");
/* 376:420 */     this.tabs += 1;
/* 377:421 */     printAction(this.grammar.classMemberAction.getText());
/* 378:422 */     this.tabs -= 1;
/* 379:423 */     println("*** End of user-defined tree-walker class members");
/* 380:    */     
/* 381:    */ 
/* 382:426 */     println("");
/* 383:427 */     println("*** tree-walker rules:");
/* 384:428 */     this.tabs += 1;
/* 385:    */     
/* 386:    */ 
/* 387:431 */     Enumeration localEnumeration = this.grammar.rules.elements();
/* 388:432 */     while (localEnumeration.hasMoreElements())
/* 389:    */     {
/* 390:433 */       println("");
/* 391:    */       
/* 392:435 */       GrammarSymbol localGrammarSymbol = (GrammarSymbol)localEnumeration.nextElement();
/* 393:437 */       if ((localGrammarSymbol instanceof RuleSymbol)) {
/* 394:438 */         genRule((RuleSymbol)localGrammarSymbol);
/* 395:    */       }
/* 396:    */     }
/* 397:441 */     this.tabs -= 1;
/* 398:442 */     println("");
/* 399:443 */     println("*** End of tree-walker rules");
/* 400:    */     
/* 401:445 */     println("");
/* 402:446 */     println("*** End of tree-walker");
/* 403:    */     
/* 404:    */ 
/* 405:449 */     this.currentOutput.close();
/* 406:450 */     this.currentOutput = null;
/* 407:    */   }
/* 408:    */   
/* 409:    */   public void gen(WildcardElement paramWildcardElement)
/* 410:    */   {
/* 411:455 */     print("Match wildcard");
/* 412:456 */     if (paramWildcardElement.getLabel() != null) {
/* 413:457 */       _print(", label = " + paramWildcardElement.getLabel());
/* 414:    */     }
/* 415:459 */     _println("");
/* 416:    */   }
/* 417:    */   
/* 418:    */   public void gen(ZeroOrMoreBlock paramZeroOrMoreBlock)
/* 419:    */   {
/* 420:466 */     println("Start ZERO-OR-MORE (...)+ block:");
/* 421:467 */     this.tabs += 1;
/* 422:468 */     genBlockPreamble(paramZeroOrMoreBlock);
/* 423:469 */     boolean bool = this.grammar.theLLkAnalyzer.deterministic(paramZeroOrMoreBlock);
/* 424:470 */     if (!bool) {
/* 425:471 */       println("Warning: This zero-or-more block is non-deterministic");
/* 426:    */     }
/* 427:473 */     genCommonBlock(paramZeroOrMoreBlock);
/* 428:474 */     this.tabs -= 1;
/* 429:475 */     println("End ZERO-OR-MORE block.");
/* 430:    */   }
/* 431:    */   
/* 432:    */   protected void genAlt(Alternative paramAlternative)
/* 433:    */   {
/* 434:480 */     for (AlternativeElement localAlternativeElement = paramAlternative.head; !(localAlternativeElement instanceof BlockEndElement); localAlternativeElement = localAlternativeElement.next) {
/* 435:484 */       localAlternativeElement.generate();
/* 436:    */     }
/* 437:486 */     if (paramAlternative.getTreeSpecifier() != null) {
/* 438:487 */       println("AST will be built as: " + paramAlternative.getTreeSpecifier().getText());
/* 439:    */     }
/* 440:    */   }
/* 441:    */   
/* 442:    */   protected void genBlockPreamble(AlternativeBlock paramAlternativeBlock)
/* 443:    */   {
/* 444:498 */     if (paramAlternativeBlock.initAction != null) {
/* 445:499 */       printAction("Init action: " + paramAlternativeBlock.initAction);
/* 446:    */     }
/* 447:    */   }
/* 448:    */   
/* 449:    */   public void genCommonBlock(AlternativeBlock paramAlternativeBlock)
/* 450:    */   {
/* 451:509 */     int i = paramAlternativeBlock.alternatives.size() == 1 ? 1 : 0;
/* 452:    */     
/* 453:511 */     println("Start of an alternative block.");
/* 454:512 */     this.tabs += 1;
/* 455:513 */     println("The lookahead set for this block is:");
/* 456:514 */     this.tabs += 1;
/* 457:515 */     genLookaheadSetForBlock(paramAlternativeBlock);
/* 458:516 */     this.tabs -= 1;
/* 459:518 */     if (i != 0)
/* 460:    */     {
/* 461:519 */       println("This block has a single alternative");
/* 462:520 */       if (paramAlternativeBlock.getAlternativeAt(0).synPred != null)
/* 463:    */       {
/* 464:522 */         println("Warning: you specified a syntactic predicate for this alternative,");
/* 465:523 */         println("and it is the only alternative of a block and will be ignored.");
/* 466:    */       }
/* 467:    */     }
/* 468:    */     else
/* 469:    */     {
/* 470:527 */       println("This block has multiple alternatives:");
/* 471:528 */       this.tabs += 1;
/* 472:    */     }
/* 473:531 */     for (int j = 0; j < paramAlternativeBlock.alternatives.size(); j++)
/* 474:    */     {
/* 475:532 */       Alternative localAlternative = paramAlternativeBlock.getAlternativeAt(j);
/* 476:533 */       AlternativeElement localAlternativeElement = localAlternative.head;
/* 477:    */       
/* 478:    */ 
/* 479:536 */       println("");
/* 480:537 */       if (j != 0) {
/* 481:538 */         print("Otherwise, ");
/* 482:    */       } else {
/* 483:541 */         print("");
/* 484:    */       }
/* 485:543 */       _println("Alternate(" + (j + 1) + ") will be taken IF:");
/* 486:544 */       println("The lookahead set: ");
/* 487:545 */       this.tabs += 1;
/* 488:546 */       genLookaheadSetForAlt(localAlternative);
/* 489:547 */       this.tabs -= 1;
/* 490:548 */       if ((localAlternative.semPred != null) || (localAlternative.synPred != null)) {
/* 491:549 */         print("is matched, AND ");
/* 492:    */       } else {
/* 493:552 */         println("is matched.");
/* 494:    */       }
/* 495:556 */       if (localAlternative.semPred != null)
/* 496:    */       {
/* 497:557 */         _println("the semantic predicate:");
/* 498:558 */         this.tabs += 1;
/* 499:559 */         println(localAlternative.semPred);
/* 500:560 */         if (localAlternative.synPred != null) {
/* 501:561 */           print("is true, AND ");
/* 502:    */         } else {
/* 503:564 */           println("is true.");
/* 504:    */         }
/* 505:    */       }
/* 506:569 */       if (localAlternative.synPred != null)
/* 507:    */       {
/* 508:570 */         _println("the syntactic predicate:");
/* 509:571 */         this.tabs += 1;
/* 510:572 */         genSynPred(localAlternative.synPred);
/* 511:573 */         this.tabs -= 1;
/* 512:574 */         println("is matched.");
/* 513:    */       }
/* 514:578 */       genAlt(localAlternative);
/* 515:    */     }
/* 516:580 */     println("");
/* 517:581 */     println("OTHERWISE, a NoViableAlt exception will be thrown");
/* 518:582 */     println("");
/* 519:584 */     if (i == 0)
/* 520:    */     {
/* 521:585 */       this.tabs -= 1;
/* 522:586 */       println("End of alternatives");
/* 523:    */     }
/* 524:588 */     this.tabs -= 1;
/* 525:589 */     println("End of alternative block.");
/* 526:    */   }
/* 527:    */   
/* 528:    */   public void genFollowSetForRuleBlock(RuleBlock paramRuleBlock)
/* 529:    */   {
/* 530:597 */     Lookahead localLookahead = this.grammar.theLLkAnalyzer.FOLLOW(1, paramRuleBlock.endNode);
/* 531:598 */     printSet(this.grammar.maxk, 1, localLookahead);
/* 532:    */   }
/* 533:    */   
/* 534:    */   protected void genHeader()
/* 535:    */   {
/* 536:603 */     println("ANTLR-generated file resulting from grammar " + this.antlrTool.grammarFile);
/* 537:604 */     println("Diagnostic output");
/* 538:605 */     println("");
/* 539:606 */     println("Terence Parr, MageLang Institute");
/* 540:607 */     println("with John Lilley, Empathy Software");
/* 541:608 */     println("ANTLR Version " + Tool.version + "; 1989-2005");
/* 542:609 */     println("");
/* 543:610 */     println("*** Header Action.");
/* 544:611 */     println("This action will appear at the top of all generated files.");
/* 545:612 */     this.tabs += 1;
/* 546:613 */     printAction(this.behavior.getHeaderAction(""));
/* 547:614 */     this.tabs -= 1;
/* 548:615 */     println("*** End of Header Action");
/* 549:616 */     println("");
/* 550:    */   }
/* 551:    */   
/* 552:    */   protected void genLookaheadSetForAlt(Alternative paramAlternative)
/* 553:    */   {
/* 554:621 */     if ((this.doingLexRules) && (paramAlternative.cache[1].containsEpsilon()))
/* 555:    */     {
/* 556:622 */       println("MATCHES ALL");
/* 557:623 */       return;
/* 558:    */     }
/* 559:625 */     int i = paramAlternative.lookaheadDepth;
/* 560:626 */     if (i == 2147483647) {
/* 561:629 */       i = this.grammar.maxk;
/* 562:    */     }
/* 563:631 */     for (int j = 1; j <= i; j++)
/* 564:    */     {
/* 565:632 */       Lookahead localLookahead = paramAlternative.cache[j];
/* 566:633 */       printSet(i, j, localLookahead);
/* 567:    */     }
/* 568:    */   }
/* 569:    */   
/* 570:    */   public void genLookaheadSetForBlock(AlternativeBlock paramAlternativeBlock)
/* 571:    */   {
/* 572:643 */     int i = 0;
/* 573:    */     Object localObject;
/* 574:644 */     for (int j = 0; j < paramAlternativeBlock.alternatives.size(); j++)
/* 575:    */     {
/* 576:645 */       localObject = paramAlternativeBlock.getAlternativeAt(j);
/* 577:646 */       if (((Alternative)localObject).lookaheadDepth == 2147483647)
/* 578:    */       {
/* 579:647 */         i = this.grammar.maxk;
/* 580:648 */         break;
/* 581:    */       }
/* 582:650 */       if (i < ((Alternative)localObject).lookaheadDepth) {
/* 583:651 */         i = ((Alternative)localObject).lookaheadDepth;
/* 584:    */       }
/* 585:    */     }
/* 586:655 */     for (j = 1; j <= i; j++)
/* 587:    */     {
/* 588:656 */       localObject = this.grammar.theLLkAnalyzer.look(j, paramAlternativeBlock);
/* 589:657 */       printSet(i, j, (Lookahead)localObject);
/* 590:    */     }
/* 591:    */   }
/* 592:    */   
/* 593:    */   public void genNextToken()
/* 594:    */   {
/* 595:666 */     println("");
/* 596:667 */     println("*** Lexer nextToken rule:");
/* 597:668 */     println("The lexer nextToken rule is synthesized from all of the user-defined");
/* 598:669 */     println("lexer rules.  It logically consists of one big alternative block with");
/* 599:670 */     println("each user-defined rule being an alternative.");
/* 600:671 */     println("");
/* 601:    */     
/* 602:    */ 
/* 603:    */ 
/* 604:675 */     RuleBlock localRuleBlock = MakeGrammar.createNextTokenRule(this.grammar, this.grammar.rules, "nextToken");
/* 605:    */     
/* 606:    */ 
/* 607:678 */     RuleSymbol localRuleSymbol = new RuleSymbol("mnextToken");
/* 608:679 */     localRuleSymbol.setDefined();
/* 609:680 */     localRuleSymbol.setBlock(localRuleBlock);
/* 610:681 */     localRuleSymbol.access = "private";
/* 611:682 */     this.grammar.define(localRuleSymbol);
/* 612:685 */     if (!this.grammar.theLLkAnalyzer.deterministic(localRuleBlock))
/* 613:    */     {
/* 614:686 */       println("The grammar analyzer has determined that the synthesized");
/* 615:687 */       println("nextToken rule is non-deterministic (i.e., it has ambiguities)");
/* 616:688 */       println("This means that there is some overlap of the character");
/* 617:689 */       println("lookahead for two or more of your lexer rules.");
/* 618:    */     }
/* 619:692 */     genCommonBlock(localRuleBlock);
/* 620:    */     
/* 621:694 */     println("*** End of nextToken lexer rule.");
/* 622:    */   }
/* 623:    */   
/* 624:    */   public void genRule(RuleSymbol paramRuleSymbol)
/* 625:    */   {
/* 626:701 */     println("");
/* 627:702 */     String str = this.doingLexRules ? "Lexer" : "Parser";
/* 628:703 */     println("*** " + str + " Rule: " + paramRuleSymbol.getId());
/* 629:704 */     if (!paramRuleSymbol.isDefined())
/* 630:    */     {
/* 631:705 */       println("This rule is undefined.");
/* 632:706 */       println("This means that the rule was referenced somewhere in the grammar,");
/* 633:707 */       println("but a definition for the rule was not encountered.");
/* 634:708 */       println("It is also possible that syntax errors during the parse of");
/* 635:709 */       println("your grammar file prevented correct processing of the rule.");
/* 636:710 */       println("*** End " + str + " Rule: " + paramRuleSymbol.getId());
/* 637:711 */       return;
/* 638:    */     }
/* 639:713 */     this.tabs += 1;
/* 640:715 */     if (paramRuleSymbol.access.length() != 0) {
/* 641:716 */       println("Access: " + paramRuleSymbol.access);
/* 642:    */     }
/* 643:720 */     RuleBlock localRuleBlock = paramRuleSymbol.getBlock();
/* 644:723 */     if (localRuleBlock.returnAction != null)
/* 645:    */     {
/* 646:724 */       println("Return value(s): " + localRuleBlock.returnAction);
/* 647:725 */       if (this.doingLexRules)
/* 648:    */       {
/* 649:726 */         println("Error: you specified return value(s) for a lexical rule.");
/* 650:727 */         println("\tLexical rules have an implicit return type of 'int'.");
/* 651:    */       }
/* 652:    */     }
/* 653:731 */     else if (this.doingLexRules)
/* 654:    */     {
/* 655:732 */       println("Return value: lexical rule returns an implicit token type");
/* 656:    */     }
/* 657:    */     else
/* 658:    */     {
/* 659:735 */       println("Return value: none");
/* 660:    */     }
/* 661:740 */     if (localRuleBlock.argAction != null) {
/* 662:741 */       println("Arguments: " + localRuleBlock.argAction);
/* 663:    */     }
/* 664:745 */     genBlockPreamble(localRuleBlock);
/* 665:    */     
/* 666:    */ 
/* 667:748 */     boolean bool = this.grammar.theLLkAnalyzer.deterministic(localRuleBlock);
/* 668:749 */     if (!bool) {
/* 669:750 */       println("Error: This rule is non-deterministic");
/* 670:    */     }
/* 671:754 */     genCommonBlock(localRuleBlock);
/* 672:    */     
/* 673:    */ 
/* 674:757 */     ExceptionSpec localExceptionSpec = localRuleBlock.findExceptionSpec("");
/* 675:760 */     if (localExceptionSpec != null)
/* 676:    */     {
/* 677:761 */       println("You specified error-handler(s) for this rule:");
/* 678:762 */       this.tabs += 1;
/* 679:763 */       for (int i = 0; i < localExceptionSpec.handlers.size(); i++)
/* 680:    */       {
/* 681:764 */         if (i != 0) {
/* 682:765 */           println("");
/* 683:    */         }
/* 684:768 */         ExceptionHandler localExceptionHandler = (ExceptionHandler)localExceptionSpec.handlers.elementAt(i);
/* 685:769 */         println("Error-handler(" + (i + 1) + ") catches [" + localExceptionHandler.exceptionTypeAndName.getText() + "] and executes:");
/* 686:770 */         printAction(localExceptionHandler.action.getText());
/* 687:    */       }
/* 688:772 */       this.tabs -= 1;
/* 689:773 */       println("End error-handlers.");
/* 690:    */     }
/* 691:775 */     else if (!this.doingLexRules)
/* 692:    */     {
/* 693:776 */       println("Default error-handling will be generated, which catches all");
/* 694:777 */       println("parser exceptions and consumes tokens until the follow-set is seen.");
/* 695:    */     }
/* 696:782 */     if (!this.doingLexRules)
/* 697:    */     {
/* 698:783 */       println("The follow set for this rule is:");
/* 699:784 */       this.tabs += 1;
/* 700:785 */       genFollowSetForRuleBlock(localRuleBlock);
/* 701:786 */       this.tabs -= 1;
/* 702:    */     }
/* 703:789 */     this.tabs -= 1;
/* 704:790 */     println("*** End " + str + " Rule: " + paramRuleSymbol.getId());
/* 705:    */   }
/* 706:    */   
/* 707:    */   protected void genSynPred(SynPredBlock paramSynPredBlock)
/* 708:    */   {
/* 709:798 */     this.syntacticPredLevel += 1;
/* 710:799 */     gen(paramSynPredBlock);
/* 711:800 */     this.syntacticPredLevel -= 1;
/* 712:    */   }
/* 713:    */   
/* 714:    */   protected void genTokenTypes(TokenManager paramTokenManager)
/* 715:    */     throws IOException
/* 716:    */   {
/* 717:806 */     this.antlrTool.reportProgress("Generating " + paramTokenManager.getName() + TokenTypesFileSuffix + TokenTypesFileExt);
/* 718:807 */     this.currentOutput = this.antlrTool.openOutputFile(paramTokenManager.getName() + TokenTypesFileSuffix + TokenTypesFileExt);
/* 719:    */     
/* 720:809 */     this.tabs = 0;
/* 721:    */     
/* 722:    */ 
/* 723:812 */     genHeader();
/* 724:    */     
/* 725:    */ 
/* 726:    */ 
/* 727:816 */     println("");
/* 728:817 */     println("*** Tokens used by the parser");
/* 729:818 */     println("This is a list of the token numeric values and the corresponding");
/* 730:819 */     println("token identifiers.  Some tokens are literals, and because of that");
/* 731:820 */     println("they have no identifiers.  Literals are double-quoted.");
/* 732:821 */     this.tabs += 1;
/* 733:    */     
/* 734:    */ 
/* 735:824 */     Vector localVector = paramTokenManager.getVocabulary();
/* 736:825 */     for (int i = 4; i < localVector.size(); i++)
/* 737:    */     {
/* 738:826 */       String str = (String)localVector.elementAt(i);
/* 739:827 */       if (str != null) {
/* 740:828 */         println(str + " = " + i);
/* 741:    */       }
/* 742:    */     }
/* 743:833 */     this.tabs -= 1;
/* 744:834 */     println("*** End of tokens used by the parser");
/* 745:    */     
/* 746:    */ 
/* 747:837 */     this.currentOutput.close();
/* 748:838 */     this.currentOutput = null;
/* 749:    */   }
/* 750:    */   
/* 751:    */   public String getASTCreateString(Vector paramVector)
/* 752:    */   {
/* 753:845 */     return "***Create an AST from a vector here***" + System.getProperty("line.separator");
/* 754:    */   }
/* 755:    */   
/* 756:    */   public String getASTCreateString(GrammarAtom paramGrammarAtom, String paramString)
/* 757:    */   {
/* 758:852 */     return "[" + paramString + "]";
/* 759:    */   }
/* 760:    */   
/* 761:    */   protected String processActionForSpecialSymbols(String paramString, int paramInt, RuleBlock paramRuleBlock, ActionTransInfo paramActionTransInfo)
/* 762:    */   {
/* 763:860 */     return paramString;
/* 764:    */   }
/* 765:    */   
/* 766:    */   public String mapTreeId(String paramString, ActionTransInfo paramActionTransInfo)
/* 767:    */   {
/* 768:870 */     return paramString;
/* 769:    */   }
/* 770:    */   
/* 771:    */   public void printSet(int paramInt1, int paramInt2, Lookahead paramLookahead)
/* 772:    */   {
/* 773:879 */     int i = 5;
/* 774:    */     
/* 775:881 */     int[] arrayOfInt = paramLookahead.fset.toArray();
/* 776:883 */     if (paramInt1 != 1) {
/* 777:884 */       print("k==" + paramInt2 + ": {");
/* 778:    */     } else {
/* 779:887 */       print("{ ");
/* 780:    */     }
/* 781:889 */     if (arrayOfInt.length > i)
/* 782:    */     {
/* 783:890 */       _println("");
/* 784:891 */       this.tabs += 1;
/* 785:892 */       print("");
/* 786:    */     }
/* 787:895 */     int j = 0;
/* 788:896 */     for (int k = 0; k < arrayOfInt.length; k++)
/* 789:    */     {
/* 790:897 */       j++;
/* 791:898 */       if (j > i)
/* 792:    */       {
/* 793:899 */         _println("");
/* 794:900 */         print("");
/* 795:901 */         j = 0;
/* 796:    */       }
/* 797:903 */       if (this.doingLexRules) {
/* 798:904 */         _print(this.charFormatter.literalChar(arrayOfInt[k]));
/* 799:    */       } else {
/* 800:907 */         _print((String)this.grammar.tokenManager.getVocabulary().elementAt(arrayOfInt[k]));
/* 801:    */       }
/* 802:909 */       if (k != arrayOfInt.length - 1) {
/* 803:910 */         _print(", ");
/* 804:    */       }
/* 805:    */     }
/* 806:914 */     if (arrayOfInt.length > i)
/* 807:    */     {
/* 808:915 */       _println("");
/* 809:916 */       this.tabs -= 1;
/* 810:917 */       print("");
/* 811:    */     }
/* 812:919 */     _println(" }");
/* 813:    */   }
/* 814:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.DiagnosticCodeGenerator
 * JD-Core Version:    0.7.0.1
 */