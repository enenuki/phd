/*   1:    */ package antlr;
/*   2:    */ 
/*   3:    */ import antlr.collections.Stack;
/*   4:    */ import antlr.collections.impl.LList;
/*   5:    */ import antlr.collections.impl.Vector;
/*   6:    */ 
/*   7:    */ public class MakeGrammar
/*   8:    */   extends DefineGrammarSymbols
/*   9:    */ {
/*  10: 16 */   protected Stack blocks = new LList();
/*  11:    */   protected RuleRefElement lastRuleRef;
/*  12:    */   protected RuleEndElement ruleEnd;
/*  13:    */   protected RuleBlock ruleBlock;
/*  14: 21 */   protected int nested = 0;
/*  15: 22 */   protected boolean grammarError = false;
/*  16: 24 */   ExceptionSpec currentExceptionSpec = null;
/*  17:    */   
/*  18:    */   public MakeGrammar(Tool paramTool, String[] paramArrayOfString, LLkAnalyzer paramLLkAnalyzer)
/*  19:    */   {
/*  20: 27 */     super(paramTool, paramArrayOfString, paramLLkAnalyzer);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public void abortGrammar()
/*  24:    */   {
/*  25: 32 */     String str = "unknown grammar";
/*  26: 33 */     if (this.grammar != null) {
/*  27: 34 */       str = this.grammar.getClassName();
/*  28:    */     }
/*  29: 36 */     this.tool.error("aborting grammar '" + str + "' due to errors");
/*  30: 37 */     super.abortGrammar();
/*  31:    */   }
/*  32:    */   
/*  33:    */   protected void addElementToCurrentAlt(AlternativeElement paramAlternativeElement)
/*  34:    */   {
/*  35: 41 */     paramAlternativeElement.enclosingRuleName = this.ruleBlock.ruleName;
/*  36: 42 */     context().addAlternativeElement(paramAlternativeElement);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void beginAlt(boolean paramBoolean)
/*  40:    */   {
/*  41: 46 */     super.beginAlt(paramBoolean);
/*  42: 47 */     Alternative localAlternative = new Alternative();
/*  43: 48 */     localAlternative.setAutoGen(paramBoolean);
/*  44: 49 */     context().block.addAlternative(localAlternative);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void beginChildList()
/*  48:    */   {
/*  49: 53 */     super.beginChildList();
/*  50: 54 */     context().block.addAlternative(new Alternative());
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void beginExceptionGroup()
/*  54:    */   {
/*  55: 59 */     super.beginExceptionGroup();
/*  56: 60 */     if (!(context().block instanceof RuleBlock)) {
/*  57: 61 */       this.tool.panic("beginExceptionGroup called outside of rule block");
/*  58:    */     }
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void beginExceptionSpec(Token paramToken)
/*  62:    */   {
/*  63: 68 */     if (paramToken != null) {
/*  64: 69 */       paramToken.setText(StringUtils.stripFront(StringUtils.stripBack(paramToken.getText(), " \n\r\t"), " \n\r\t"));
/*  65:    */     }
/*  66: 71 */     super.beginExceptionSpec(paramToken);
/*  67:    */     
/*  68:    */ 
/*  69: 74 */     this.currentExceptionSpec = new ExceptionSpec(paramToken);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void beginSubRule(Token paramToken1, Token paramToken2, boolean paramBoolean)
/*  73:    */   {
/*  74: 78 */     super.beginSubRule(paramToken1, paramToken2, paramBoolean);
/*  75:    */     
/*  76:    */ 
/*  77:    */ 
/*  78: 82 */     this.blocks.push(new BlockContext());
/*  79: 83 */     context().block = new AlternativeBlock(this.grammar, paramToken2, paramBoolean);
/*  80: 84 */     context().altNum = 0;
/*  81: 85 */     this.nested += 1;
/*  82:    */     
/*  83:    */ 
/*  84: 88 */     context().blockEnd = new BlockEndElement(this.grammar);
/*  85:    */     
/*  86: 90 */     context().blockEnd.block = context().block;
/*  87: 91 */     labelElement(context().block, paramToken1);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void beginTree(Token paramToken)
/*  91:    */     throws SemanticException
/*  92:    */   {
/*  93: 95 */     if (!(this.grammar instanceof TreeWalkerGrammar))
/*  94:    */     {
/*  95: 96 */       this.tool.error("Trees only allowed in TreeParser", this.grammar.getFilename(), paramToken.getLine(), paramToken.getColumn());
/*  96: 97 */       throw new SemanticException("Trees only allowed in TreeParser");
/*  97:    */     }
/*  98: 99 */     super.beginTree(paramToken);
/*  99:100 */     this.blocks.push(new TreeBlockContext());
/* 100:101 */     context().block = new TreeElement(this.grammar, paramToken);
/* 101:102 */     context().altNum = 0;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public BlockContext context()
/* 105:    */   {
/* 106:106 */     if (this.blocks.height() == 0) {
/* 107:107 */       return null;
/* 108:    */     }
/* 109:110 */     return (BlockContext)this.blocks.top();
/* 110:    */   }
/* 111:    */   
/* 112:    */   public static RuleBlock createNextTokenRule(Grammar paramGrammar, Vector paramVector, String paramString)
/* 113:    */   {
/* 114:123 */     RuleBlock localRuleBlock1 = new RuleBlock(paramGrammar, paramString);
/* 115:124 */     localRuleBlock1.setDefaultErrorHandler(paramGrammar.getDefaultErrorHandler());
/* 116:125 */     RuleEndElement localRuleEndElement = new RuleEndElement(paramGrammar);
/* 117:126 */     localRuleBlock1.setEndElement(localRuleEndElement);
/* 118:127 */     localRuleEndElement.block = localRuleBlock1;
/* 119:129 */     for (int i = 0; i < paramVector.size(); i++)
/* 120:    */     {
/* 121:130 */       RuleSymbol localRuleSymbol = (RuleSymbol)paramVector.elementAt(i);
/* 122:131 */       if (!localRuleSymbol.isDefined())
/* 123:    */       {
/* 124:132 */         paramGrammar.antlrTool.error("Lexer rule " + localRuleSymbol.id.substring(1) + " is not defined");
/* 125:    */       }
/* 126:135 */       else if (localRuleSymbol.access.equals("public"))
/* 127:    */       {
/* 128:136 */         Alternative localAlternative = new Alternative();
/* 129:137 */         RuleBlock localRuleBlock2 = localRuleSymbol.getBlock();
/* 130:138 */         Vector localVector = localRuleBlock2.getAlternatives();
/* 131:141 */         if ((localVector != null) && (localVector.size() == 1))
/* 132:    */         {
/* 133:142 */           localObject = (Alternative)localVector.elementAt(0);
/* 134:143 */           if (((Alternative)localObject).semPred != null) {
/* 135:145 */             localAlternative.semPred = ((Alternative)localObject).semPred;
/* 136:    */           }
/* 137:    */         }
/* 138:154 */         Object localObject = new RuleRefElement(paramGrammar, new CommonToken(41, localRuleSymbol.getId()), 1);
/* 139:    */         
/* 140:    */ 
/* 141:    */ 
/* 142:158 */         ((RuleRefElement)localObject).setLabel("theRetToken");
/* 143:159 */         ((RuleRefElement)localObject).enclosingRuleName = "nextToken";
/* 144:160 */         ((RuleRefElement)localObject).next = localRuleEndElement;
/* 145:161 */         localAlternative.addElement((AlternativeElement)localObject);
/* 146:162 */         localAlternative.setAutoGen(true);
/* 147:163 */         localRuleBlock1.addAlternative(localAlternative);
/* 148:164 */         localRuleSymbol.addReference((RuleRefElement)localObject);
/* 149:    */       }
/* 150:    */     }
/* 151:169 */     localRuleBlock1.setAutoGen(true);
/* 152:170 */     localRuleBlock1.prepareForAnalysis();
/* 153:    */     
/* 154:172 */     return localRuleBlock1;
/* 155:    */   }
/* 156:    */   
/* 157:    */   private AlternativeBlock createOptionalRuleRef(String paramString, Token paramToken)
/* 158:    */   {
/* 159:178 */     AlternativeBlock localAlternativeBlock = new AlternativeBlock(this.grammar, paramToken, false);
/* 160:    */     
/* 161:    */ 
/* 162:181 */     String str = CodeGenerator.encodeLexerRuleName(paramString);
/* 163:182 */     if (!this.grammar.isDefined(str)) {
/* 164:183 */       this.grammar.define(new RuleSymbol(str));
/* 165:    */     }
/* 166:188 */     CommonToken localCommonToken = new CommonToken(24, paramString);
/* 167:189 */     localCommonToken.setLine(paramToken.getLine());
/* 168:190 */     localCommonToken.setLine(paramToken.getColumn());
/* 169:191 */     RuleRefElement localRuleRefElement = new RuleRefElement(this.grammar, localCommonToken, 1);
/* 170:    */     
/* 171:    */ 
/* 172:194 */     localRuleRefElement.enclosingRuleName = this.ruleBlock.ruleName;
/* 173:    */     
/* 174:    */ 
/* 175:197 */     BlockEndElement localBlockEndElement = new BlockEndElement(this.grammar);
/* 176:198 */     localBlockEndElement.block = localAlternativeBlock;
/* 177:    */     
/* 178:    */ 
/* 179:201 */     Alternative localAlternative1 = new Alternative(localRuleRefElement);
/* 180:202 */     localAlternative1.addElement(localBlockEndElement);
/* 181:    */     
/* 182:    */ 
/* 183:205 */     localAlternativeBlock.addAlternative(localAlternative1);
/* 184:    */     
/* 185:    */ 
/* 186:208 */     Alternative localAlternative2 = new Alternative();
/* 187:209 */     localAlternative2.addElement(localBlockEndElement);
/* 188:    */     
/* 189:211 */     localAlternativeBlock.addAlternative(localAlternative2);
/* 190:    */     
/* 191:213 */     localAlternativeBlock.prepareForAnalysis();
/* 192:214 */     return localAlternativeBlock;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void defineRuleName(Token paramToken, String paramString1, boolean paramBoolean, String paramString2)
/* 196:    */     throws SemanticException
/* 197:    */   {
/* 198:223 */     if (paramToken.type == 24)
/* 199:    */     {
/* 200:224 */       if (!(this.grammar instanceof LexerGrammar))
/* 201:    */       {
/* 202:225 */         this.tool.error("Lexical rule " + paramToken.getText() + " defined outside of lexer", this.grammar.getFilename(), paramToken.getLine(), paramToken.getColumn());
/* 203:    */         
/* 204:    */ 
/* 205:228 */         paramToken.setText(paramToken.getText().toLowerCase());
/* 206:    */       }
/* 207:    */     }
/* 208:232 */     else if ((this.grammar instanceof LexerGrammar))
/* 209:    */     {
/* 210:233 */       this.tool.error("Lexical rule names must be upper case, '" + paramToken.getText() + "' is not", this.grammar.getFilename(), paramToken.getLine(), paramToken.getColumn());
/* 211:    */       
/* 212:    */ 
/* 213:236 */       paramToken.setText(paramToken.getText().toUpperCase());
/* 214:    */     }
/* 215:240 */     super.defineRuleName(paramToken, paramString1, paramBoolean, paramString2);
/* 216:241 */     String str = paramToken.getText();
/* 217:243 */     if (paramToken.type == 24) {
/* 218:244 */       str = CodeGenerator.encodeLexerRuleName(str);
/* 219:    */     }
/* 220:246 */     RuleSymbol localRuleSymbol = (RuleSymbol)this.grammar.getSymbol(str);
/* 221:247 */     RuleBlock localRuleBlock = new RuleBlock(this.grammar, paramToken.getText(), paramToken.getLine(), paramBoolean);
/* 222:    */     
/* 223:    */ 
/* 224:250 */     localRuleBlock.setDefaultErrorHandler(this.grammar.getDefaultErrorHandler());
/* 225:    */     
/* 226:252 */     this.ruleBlock = localRuleBlock;
/* 227:253 */     this.blocks.push(new BlockContext());
/* 228:254 */     context().block = localRuleBlock;
/* 229:255 */     localRuleSymbol.setBlock(localRuleBlock);
/* 230:256 */     this.ruleEnd = new RuleEndElement(this.grammar);
/* 231:257 */     localRuleBlock.setEndElement(this.ruleEnd);
/* 232:258 */     this.nested = 0;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void endAlt()
/* 236:    */   {
/* 237:262 */     super.endAlt();
/* 238:263 */     if (this.nested == 0) {
/* 239:264 */       addElementToCurrentAlt(this.ruleEnd);
/* 240:    */     } else {
/* 241:267 */       addElementToCurrentAlt(context().blockEnd);
/* 242:    */     }
/* 243:269 */     context().altNum += 1;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public void endChildList()
/* 247:    */   {
/* 248:273 */     super.endChildList();
/* 249:    */     
/* 250:    */ 
/* 251:    */ 
/* 252:    */ 
/* 253:278 */     BlockEndElement localBlockEndElement = new BlockEndElement(this.grammar);
/* 254:279 */     localBlockEndElement.block = context().block;
/* 255:280 */     addElementToCurrentAlt(localBlockEndElement);
/* 256:    */   }
/* 257:    */   
/* 258:    */   public void endExceptionGroup()
/* 259:    */   {
/* 260:284 */     super.endExceptionGroup();
/* 261:    */   }
/* 262:    */   
/* 263:    */   public void endExceptionSpec()
/* 264:    */   {
/* 265:288 */     super.endExceptionSpec();
/* 266:289 */     if (this.currentExceptionSpec == null) {
/* 267:290 */       this.tool.panic("exception processing internal error -- no active exception spec");
/* 268:    */     }
/* 269:292 */     if ((context().block instanceof RuleBlock)) {
/* 270:294 */       ((RuleBlock)context().block).addExceptionSpec(this.currentExceptionSpec);
/* 271:298 */     } else if (context().currentAlt().exceptionSpec != null) {
/* 272:299 */       this.tool.error("Alternative already has an exception specification", this.grammar.getFilename(), context().block.getLine(), context().block.getColumn());
/* 273:    */     } else {
/* 274:302 */       context().currentAlt().exceptionSpec = this.currentExceptionSpec;
/* 275:    */     }
/* 276:305 */     this.currentExceptionSpec = null;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public void endGrammar()
/* 280:    */   {
/* 281:310 */     if (this.grammarError) {
/* 282:311 */       abortGrammar();
/* 283:    */     } else {
/* 284:314 */       super.endGrammar();
/* 285:    */     }
/* 286:    */   }
/* 287:    */   
/* 288:    */   public void endRule(String paramString)
/* 289:    */   {
/* 290:319 */     super.endRule(paramString);
/* 291:320 */     BlockContext localBlockContext = (BlockContext)this.blocks.pop();
/* 292:    */     
/* 293:322 */     this.ruleEnd.block = localBlockContext.block;
/* 294:323 */     this.ruleEnd.block.prepareForAnalysis();
/* 295:    */   }
/* 296:    */   
/* 297:    */   public void endSubRule()
/* 298:    */   {
/* 299:328 */     super.endSubRule();
/* 300:329 */     this.nested -= 1;
/* 301:    */     
/* 302:331 */     BlockContext localBlockContext = (BlockContext)this.blocks.pop();
/* 303:332 */     AlternativeBlock localAlternativeBlock = localBlockContext.block;
/* 304:    */     Object localObject;
/* 305:336 */     if ((localAlternativeBlock.not) && (!(localAlternativeBlock instanceof SynPredBlock)) && (!(localAlternativeBlock instanceof ZeroOrMoreBlock)) && (!(localAlternativeBlock instanceof OneOrMoreBlock))) {
/* 306:342 */       if (!this.analyzer.subruleCanBeInverted(localAlternativeBlock, this.grammar instanceof LexerGrammar))
/* 307:    */       {
/* 308:343 */         localObject = System.getProperty("line.separator");
/* 309:344 */         this.tool.error("This subrule cannot be inverted.  Only subrules of the form:" + (String)localObject + "    (T1|T2|T3...) or" + (String)localObject + "    ('c1'|'c2'|'c3'...)" + (String)localObject + "may be inverted (ranges are also allowed).", this.grammar.getFilename(), localAlternativeBlock.getLine(), localAlternativeBlock.getColumn());
/* 310:    */       }
/* 311:    */     }
/* 312:356 */     if ((localAlternativeBlock instanceof SynPredBlock))
/* 313:    */     {
/* 314:359 */       localObject = (SynPredBlock)localAlternativeBlock;
/* 315:360 */       context().block.hasASynPred = true;
/* 316:361 */       context().currentAlt().synPred = ((SynPredBlock)localObject);
/* 317:362 */       this.grammar.hasSyntacticPredicate = true;
/* 318:363 */       ((SynPredBlock)localObject).removeTrackingOfRuleRefs(this.grammar);
/* 319:    */     }
/* 320:    */     else
/* 321:    */     {
/* 322:366 */       addElementToCurrentAlt(localAlternativeBlock);
/* 323:    */     }
/* 324:368 */     localBlockContext.blockEnd.block.prepareForAnalysis();
/* 325:    */   }
/* 326:    */   
/* 327:    */   public void endTree()
/* 328:    */   {
/* 329:372 */     super.endTree();
/* 330:373 */     BlockContext localBlockContext = (BlockContext)this.blocks.pop();
/* 331:374 */     addElementToCurrentAlt(localBlockContext.block);
/* 332:    */   }
/* 333:    */   
/* 334:    */   public void hasError()
/* 335:    */   {
/* 336:379 */     this.grammarError = true;
/* 337:    */   }
/* 338:    */   
/* 339:    */   private void labelElement(AlternativeElement paramAlternativeElement, Token paramToken)
/* 340:    */   {
/* 341:383 */     if (paramToken != null)
/* 342:    */     {
/* 343:385 */       for (int i = 0; i < this.ruleBlock.labeledElements.size(); i++)
/* 344:    */       {
/* 345:386 */         AlternativeElement localAlternativeElement = (AlternativeElement)this.ruleBlock.labeledElements.elementAt(i);
/* 346:387 */         String str = localAlternativeElement.getLabel();
/* 347:388 */         if ((str != null) && (str.equals(paramToken.getText())))
/* 348:    */         {
/* 349:389 */           this.tool.error("Label '" + paramToken.getText() + "' has already been defined", this.grammar.getFilename(), paramToken.getLine(), paramToken.getColumn());
/* 350:390 */           return;
/* 351:    */         }
/* 352:    */       }
/* 353:394 */       paramAlternativeElement.setLabel(paramToken.getText());
/* 354:395 */       this.ruleBlock.labeledElements.appendElement(paramAlternativeElement);
/* 355:    */     }
/* 356:    */   }
/* 357:    */   
/* 358:    */   public void noAutoGenSubRule()
/* 359:    */   {
/* 360:400 */     context().block.setAutoGen(false);
/* 361:    */   }
/* 362:    */   
/* 363:    */   public void oneOrMoreSubRule()
/* 364:    */   {
/* 365:404 */     if (context().block.not) {
/* 366:405 */       this.tool.error("'~' cannot be applied to (...)* subrule", this.grammar.getFilename(), context().block.getLine(), context().block.getColumn());
/* 367:    */     }
/* 368:410 */     OneOrMoreBlock localOneOrMoreBlock = new OneOrMoreBlock(this.grammar);
/* 369:411 */     setBlock(localOneOrMoreBlock, context().block);
/* 370:412 */     BlockContext localBlockContext = (BlockContext)this.blocks.pop();
/* 371:413 */     this.blocks.push(new BlockContext());
/* 372:414 */     context().block = localOneOrMoreBlock;
/* 373:415 */     context().blockEnd = localBlockContext.blockEnd;
/* 374:416 */     context().blockEnd.block = localOneOrMoreBlock;
/* 375:    */   }
/* 376:    */   
/* 377:    */   public void optionalSubRule()
/* 378:    */   {
/* 379:420 */     if (context().block.not) {
/* 380:421 */       this.tool.error("'~' cannot be applied to (...)? subrule", this.grammar.getFilename(), context().block.getLine(), context().block.getColumn());
/* 381:    */     }
/* 382:425 */     beginAlt(false);
/* 383:426 */     endAlt();
/* 384:    */   }
/* 385:    */   
/* 386:    */   public void refAction(Token paramToken)
/* 387:    */   {
/* 388:430 */     super.refAction(paramToken);
/* 389:431 */     context().block.hasAnAction = true;
/* 390:432 */     addElementToCurrentAlt(new ActionElement(this.grammar, paramToken));
/* 391:    */   }
/* 392:    */   
/* 393:    */   public void setUserExceptions(String paramString)
/* 394:    */   {
/* 395:436 */     ((RuleBlock)context().block).throwsSpec = paramString;
/* 396:    */   }
/* 397:    */   
/* 398:    */   public void refArgAction(Token paramToken)
/* 399:    */   {
/* 400:441 */     ((RuleBlock)context().block).argAction = paramToken.getText();
/* 401:    */   }
/* 402:    */   
/* 403:    */   public void refCharLiteral(Token paramToken1, Token paramToken2, boolean paramBoolean1, int paramInt, boolean paramBoolean2)
/* 404:    */   {
/* 405:445 */     if (!(this.grammar instanceof LexerGrammar))
/* 406:    */     {
/* 407:446 */       this.tool.error("Character literal only valid in lexer", this.grammar.getFilename(), paramToken1.getLine(), paramToken1.getColumn());
/* 408:447 */       return;
/* 409:    */     }
/* 410:449 */     super.refCharLiteral(paramToken1, paramToken2, paramBoolean1, paramInt, paramBoolean2);
/* 411:450 */     CharLiteralElement localCharLiteralElement = new CharLiteralElement((LexerGrammar)this.grammar, paramToken1, paramBoolean1, paramInt);
/* 412:453 */     if ((!((LexerGrammar)this.grammar).caseSensitive) && (localCharLiteralElement.getType() < 128) && (Character.toLowerCase((char)localCharLiteralElement.getType()) != (char)localCharLiteralElement.getType())) {
/* 413:457 */       this.tool.warning("Character literal must be lowercase when caseSensitive=false", this.grammar.getFilename(), paramToken1.getLine(), paramToken1.getColumn());
/* 414:    */     }
/* 415:460 */     addElementToCurrentAlt(localCharLiteralElement);
/* 416:461 */     labelElement(localCharLiteralElement, paramToken2);
/* 417:    */     
/* 418:    */ 
/* 419:464 */     String str = this.ruleBlock.getIgnoreRule();
/* 420:465 */     if ((!paramBoolean2) && (str != null)) {
/* 421:466 */       addElementToCurrentAlt(createOptionalRuleRef(str, paramToken1));
/* 422:    */     }
/* 423:    */   }
/* 424:    */   
/* 425:    */   public void refCharRange(Token paramToken1, Token paramToken2, Token paramToken3, int paramInt, boolean paramBoolean)
/* 426:    */   {
/* 427:471 */     if (!(this.grammar instanceof LexerGrammar))
/* 428:    */     {
/* 429:472 */       this.tool.error("Character range only valid in lexer", this.grammar.getFilename(), paramToken1.getLine(), paramToken1.getColumn());
/* 430:473 */       return;
/* 431:    */     }
/* 432:475 */     int i = ANTLRLexer.tokenTypeForCharLiteral(paramToken1.getText());
/* 433:476 */     int j = ANTLRLexer.tokenTypeForCharLiteral(paramToken2.getText());
/* 434:477 */     if (j < i)
/* 435:    */     {
/* 436:478 */       this.tool.error("Malformed range.", this.grammar.getFilename(), paramToken1.getLine(), paramToken1.getColumn());
/* 437:479 */       return;
/* 438:    */     }
/* 439:483 */     if (!((LexerGrammar)this.grammar).caseSensitive)
/* 440:    */     {
/* 441:484 */       if ((i < 128) && (Character.toLowerCase((char)i) != (char)i)) {
/* 442:485 */         this.tool.warning("Character literal must be lowercase when caseSensitive=false", this.grammar.getFilename(), paramToken1.getLine(), paramToken1.getColumn());
/* 443:    */       }
/* 444:487 */       if ((j < 128) && (Character.toLowerCase((char)j) != (char)j)) {
/* 445:488 */         this.tool.warning("Character literal must be lowercase when caseSensitive=false", this.grammar.getFilename(), paramToken2.getLine(), paramToken2.getColumn());
/* 446:    */       }
/* 447:    */     }
/* 448:492 */     super.refCharRange(paramToken1, paramToken2, paramToken3, paramInt, paramBoolean);
/* 449:493 */     CharRangeElement localCharRangeElement = new CharRangeElement((LexerGrammar)this.grammar, paramToken1, paramToken2, paramInt);
/* 450:494 */     addElementToCurrentAlt(localCharRangeElement);
/* 451:495 */     labelElement(localCharRangeElement, paramToken3);
/* 452:    */     
/* 453:    */ 
/* 454:498 */     String str = this.ruleBlock.getIgnoreRule();
/* 455:499 */     if ((!paramBoolean) && (str != null)) {
/* 456:500 */       addElementToCurrentAlt(createOptionalRuleRef(str, paramToken1));
/* 457:    */     }
/* 458:    */   }
/* 459:    */   
/* 460:    */   public void refTokensSpecElementOption(Token paramToken1, Token paramToken2, Token paramToken3)
/* 461:    */   {
/* 462:511 */     TokenSymbol localTokenSymbol = this.grammar.tokenManager.getTokenSymbol(paramToken1.getText());
/* 463:513 */     if (localTokenSymbol == null) {
/* 464:514 */       this.tool.panic("cannot find " + paramToken1.getText() + "in tokens {...}");
/* 465:    */     }
/* 466:516 */     if (paramToken2.getText().equals("AST")) {
/* 467:517 */       localTokenSymbol.setASTNodeType(paramToken3.getText());
/* 468:    */     } else {
/* 469:520 */       this.grammar.antlrTool.error("invalid tokens {...} element option:" + paramToken2.getText(), this.grammar.getFilename(), paramToken2.getLine(), paramToken2.getColumn());
/* 470:    */     }
/* 471:    */   }
/* 472:    */   
/* 473:    */   public void refElementOption(Token paramToken1, Token paramToken2)
/* 474:    */   {
/* 475:532 */     AlternativeElement localAlternativeElement = context().currentElement();
/* 476:533 */     if (((localAlternativeElement instanceof StringLiteralElement)) || ((localAlternativeElement instanceof TokenRefElement)) || ((localAlternativeElement instanceof WildcardElement))) {
/* 477:536 */       ((GrammarAtom)localAlternativeElement).setOption(paramToken1, paramToken2);
/* 478:    */     } else {
/* 479:539 */       this.tool.error("cannot use element option (" + paramToken1.getText() + ") for this kind of element", this.grammar.getFilename(), paramToken1.getLine(), paramToken1.getColumn());
/* 480:    */     }
/* 481:    */   }
/* 482:    */   
/* 483:    */   public void refExceptionHandler(Token paramToken1, Token paramToken2)
/* 484:    */   {
/* 485:547 */     super.refExceptionHandler(paramToken1, paramToken2);
/* 486:548 */     if (this.currentExceptionSpec == null) {
/* 487:549 */       this.tool.panic("exception handler processing internal error");
/* 488:    */     }
/* 489:551 */     this.currentExceptionSpec.addHandler(new ExceptionHandler(paramToken1, paramToken2));
/* 490:    */   }
/* 491:    */   
/* 492:    */   public void refInitAction(Token paramToken)
/* 493:    */   {
/* 494:555 */     super.refAction(paramToken);
/* 495:556 */     context().block.setInitAction(paramToken.getText());
/* 496:    */   }
/* 497:    */   
/* 498:    */   public void refMemberAction(Token paramToken)
/* 499:    */   {
/* 500:560 */     this.grammar.classMemberAction = paramToken;
/* 501:    */   }
/* 502:    */   
/* 503:    */   public void refPreambleAction(Token paramToken)
/* 504:    */   {
/* 505:564 */     super.refPreambleAction(paramToken);
/* 506:    */   }
/* 507:    */   
/* 508:    */   public void refReturnAction(Token paramToken)
/* 509:    */   {
/* 510:569 */     if ((this.grammar instanceof LexerGrammar))
/* 511:    */     {
/* 512:570 */       String str = CodeGenerator.encodeLexerRuleName(((RuleBlock)context().block).getRuleName());
/* 513:571 */       RuleSymbol localRuleSymbol = (RuleSymbol)this.grammar.getSymbol(str);
/* 514:572 */       if (localRuleSymbol.access.equals("public"))
/* 515:    */       {
/* 516:573 */         this.tool.warning("public Lexical rules cannot specify return type", this.grammar.getFilename(), paramToken.getLine(), paramToken.getColumn());
/* 517:574 */         return;
/* 518:    */       }
/* 519:    */     }
/* 520:577 */     ((RuleBlock)context().block).returnAction = paramToken.getText();
/* 521:    */   }
/* 522:    */   
/* 523:    */   public void refRule(Token paramToken1, Token paramToken2, Token paramToken3, Token paramToken4, int paramInt)
/* 524:    */   {
/* 525:586 */     if ((this.grammar instanceof LexerGrammar))
/* 526:    */     {
/* 527:588 */       if (paramToken2.type != 24)
/* 528:    */       {
/* 529:589 */         this.tool.error("Parser rule " + paramToken2.getText() + " referenced in lexer");
/* 530:590 */         return;
/* 531:    */       }
/* 532:592 */       if (paramInt == 2) {
/* 533:593 */         this.tool.error("AST specification ^ not allowed in lexer", this.grammar.getFilename(), paramToken2.getLine(), paramToken2.getColumn());
/* 534:    */       }
/* 535:    */     }
/* 536:597 */     super.refRule(paramToken1, paramToken2, paramToken3, paramToken4, paramInt);
/* 537:598 */     this.lastRuleRef = new RuleRefElement(this.grammar, paramToken2, paramInt);
/* 538:599 */     if (paramToken4 != null) {
/* 539:600 */       this.lastRuleRef.setArgs(paramToken4.getText());
/* 540:    */     }
/* 541:602 */     if (paramToken1 != null) {
/* 542:603 */       this.lastRuleRef.setIdAssign(paramToken1.getText());
/* 543:    */     }
/* 544:605 */     addElementToCurrentAlt(this.lastRuleRef);
/* 545:    */     
/* 546:607 */     String str = paramToken2.getText();
/* 547:609 */     if (paramToken2.type == 24) {
/* 548:610 */       str = CodeGenerator.encodeLexerRuleName(str);
/* 549:    */     }
/* 550:613 */     RuleSymbol localRuleSymbol = (RuleSymbol)this.grammar.getSymbol(str);
/* 551:614 */     localRuleSymbol.addReference(this.lastRuleRef);
/* 552:615 */     labelElement(this.lastRuleRef, paramToken3);
/* 553:    */   }
/* 554:    */   
/* 555:    */   public void refSemPred(Token paramToken)
/* 556:    */   {
/* 557:620 */     super.refSemPred(paramToken);
/* 558:622 */     if (context().currentAlt().atStart())
/* 559:    */     {
/* 560:623 */       context().currentAlt().semPred = paramToken.getText();
/* 561:    */     }
/* 562:    */     else
/* 563:    */     {
/* 564:626 */       ActionElement localActionElement = new ActionElement(this.grammar, paramToken);
/* 565:627 */       localActionElement.isSemPred = true;
/* 566:628 */       addElementToCurrentAlt(localActionElement);
/* 567:    */     }
/* 568:    */   }
/* 569:    */   
/* 570:    */   public void refStringLiteral(Token paramToken1, Token paramToken2, int paramInt, boolean paramBoolean)
/* 571:    */   {
/* 572:634 */     super.refStringLiteral(paramToken1, paramToken2, paramInt, paramBoolean);
/* 573:635 */     if (((this.grammar instanceof TreeWalkerGrammar)) && (paramInt == 2)) {
/* 574:636 */       this.tool.error("^ not allowed in here for tree-walker", this.grammar.getFilename(), paramToken1.getLine(), paramToken1.getColumn());
/* 575:    */     }
/* 576:638 */     StringLiteralElement localStringLiteralElement = new StringLiteralElement(this.grammar, paramToken1, paramInt);
/* 577:641 */     if (((this.grammar instanceof LexerGrammar)) && (!((LexerGrammar)this.grammar).caseSensitive)) {
/* 578:642 */       for (int i = 1; i < paramToken1.getText().length() - 1; i++)
/* 579:    */       {
/* 580:643 */         char c = paramToken1.getText().charAt(i);
/* 581:644 */         if ((c < '') && (Character.toLowerCase(c) != c))
/* 582:    */         {
/* 583:645 */           this.tool.warning("Characters of string literal must be lowercase when caseSensitive=false", this.grammar.getFilename(), paramToken1.getLine(), paramToken1.getColumn());
/* 584:646 */           break;
/* 585:    */         }
/* 586:    */       }
/* 587:    */     }
/* 588:651 */     addElementToCurrentAlt(localStringLiteralElement);
/* 589:652 */     labelElement(localStringLiteralElement, paramToken2);
/* 590:    */     
/* 591:    */ 
/* 592:655 */     String str = this.ruleBlock.getIgnoreRule();
/* 593:656 */     if ((!paramBoolean) && (str != null)) {
/* 594:657 */       addElementToCurrentAlt(createOptionalRuleRef(str, paramToken1));
/* 595:    */     }
/* 596:    */   }
/* 597:    */   
/* 598:    */   public void refToken(Token paramToken1, Token paramToken2, Token paramToken3, Token paramToken4, boolean paramBoolean1, int paramInt, boolean paramBoolean2)
/* 599:    */   {
/* 600:    */     Object localObject;
/* 601:663 */     if ((this.grammar instanceof LexerGrammar))
/* 602:    */     {
/* 603:665 */       if (paramInt == 2) {
/* 604:666 */         this.tool.error("AST specification ^ not allowed in lexer", this.grammar.getFilename(), paramToken2.getLine(), paramToken2.getColumn());
/* 605:    */       }
/* 606:668 */       if (paramBoolean1) {
/* 607:669 */         this.tool.error("~TOKEN is not allowed in lexer", this.grammar.getFilename(), paramToken2.getLine(), paramToken2.getColumn());
/* 608:    */       }
/* 609:671 */       refRule(paramToken1, paramToken2, paramToken3, paramToken4, paramInt);
/* 610:    */       
/* 611:    */ 
/* 612:674 */       localObject = this.ruleBlock.getIgnoreRule();
/* 613:675 */       if ((!paramBoolean2) && (localObject != null)) {
/* 614:676 */         addElementToCurrentAlt(createOptionalRuleRef((String)localObject, paramToken2));
/* 615:    */       }
/* 616:    */     }
/* 617:    */     else
/* 618:    */     {
/* 619:681 */       if (paramToken1 != null) {
/* 620:682 */         this.tool.error("Assignment from token reference only allowed in lexer", this.grammar.getFilename(), paramToken1.getLine(), paramToken1.getColumn());
/* 621:    */       }
/* 622:684 */       if (paramToken4 != null) {
/* 623:685 */         this.tool.error("Token reference arguments only allowed in lexer", this.grammar.getFilename(), paramToken4.getLine(), paramToken4.getColumn());
/* 624:    */       }
/* 625:687 */       super.refToken(paramToken1, paramToken2, paramToken3, paramToken4, paramBoolean1, paramInt, paramBoolean2);
/* 626:688 */       localObject = new TokenRefElement(this.grammar, paramToken2, paramBoolean1, paramInt);
/* 627:689 */       addElementToCurrentAlt((AlternativeElement)localObject);
/* 628:690 */       labelElement((AlternativeElement)localObject, paramToken3);
/* 629:    */     }
/* 630:    */   }
/* 631:    */   
/* 632:    */   public void refTokenRange(Token paramToken1, Token paramToken2, Token paramToken3, int paramInt, boolean paramBoolean)
/* 633:    */   {
/* 634:695 */     if ((this.grammar instanceof LexerGrammar))
/* 635:    */     {
/* 636:696 */       this.tool.error("Token range not allowed in lexer", this.grammar.getFilename(), paramToken1.getLine(), paramToken1.getColumn());
/* 637:697 */       return;
/* 638:    */     }
/* 639:699 */     super.refTokenRange(paramToken1, paramToken2, paramToken3, paramInt, paramBoolean);
/* 640:700 */     TokenRangeElement localTokenRangeElement = new TokenRangeElement(this.grammar, paramToken1, paramToken2, paramInt);
/* 641:701 */     if (localTokenRangeElement.end < localTokenRangeElement.begin)
/* 642:    */     {
/* 643:702 */       this.tool.error("Malformed range.", this.grammar.getFilename(), paramToken1.getLine(), paramToken1.getColumn());
/* 644:703 */       return;
/* 645:    */     }
/* 646:705 */     addElementToCurrentAlt(localTokenRangeElement);
/* 647:706 */     labelElement(localTokenRangeElement, paramToken3);
/* 648:    */   }
/* 649:    */   
/* 650:    */   public void refTreeSpecifier(Token paramToken)
/* 651:    */   {
/* 652:710 */     context().currentAlt().treeSpecifier = paramToken;
/* 653:    */   }
/* 654:    */   
/* 655:    */   public void refWildcard(Token paramToken1, Token paramToken2, int paramInt)
/* 656:    */   {
/* 657:714 */     super.refWildcard(paramToken1, paramToken2, paramInt);
/* 658:715 */     WildcardElement localWildcardElement = new WildcardElement(this.grammar, paramToken1, paramInt);
/* 659:716 */     addElementToCurrentAlt(localWildcardElement);
/* 660:717 */     labelElement(localWildcardElement, paramToken2);
/* 661:    */   }
/* 662:    */   
/* 663:    */   public void reset()
/* 664:    */   {
/* 665:722 */     super.reset();
/* 666:723 */     this.blocks = new LList();
/* 667:724 */     this.lastRuleRef = null;
/* 668:725 */     this.ruleEnd = null;
/* 669:726 */     this.ruleBlock = null;
/* 670:727 */     this.nested = 0;
/* 671:728 */     this.currentExceptionSpec = null;
/* 672:729 */     this.grammarError = false;
/* 673:    */   }
/* 674:    */   
/* 675:    */   public void setArgOfRuleRef(Token paramToken)
/* 676:    */   {
/* 677:733 */     super.setArgOfRuleRef(paramToken);
/* 678:734 */     this.lastRuleRef.setArgs(paramToken.getText());
/* 679:    */   }
/* 680:    */   
/* 681:    */   public static void setBlock(AlternativeBlock paramAlternativeBlock1, AlternativeBlock paramAlternativeBlock2)
/* 682:    */   {
/* 683:738 */     paramAlternativeBlock1.setAlternatives(paramAlternativeBlock2.getAlternatives());
/* 684:739 */     paramAlternativeBlock1.initAction = paramAlternativeBlock2.initAction;
/* 685:    */     
/* 686:741 */     paramAlternativeBlock1.label = paramAlternativeBlock2.label;
/* 687:742 */     paramAlternativeBlock1.hasASynPred = paramAlternativeBlock2.hasASynPred;
/* 688:743 */     paramAlternativeBlock1.hasAnAction = paramAlternativeBlock2.hasAnAction;
/* 689:744 */     paramAlternativeBlock1.warnWhenFollowAmbig = paramAlternativeBlock2.warnWhenFollowAmbig;
/* 690:745 */     paramAlternativeBlock1.generateAmbigWarnings = paramAlternativeBlock2.generateAmbigWarnings;
/* 691:746 */     paramAlternativeBlock1.line = paramAlternativeBlock2.line;
/* 692:747 */     paramAlternativeBlock1.greedy = paramAlternativeBlock2.greedy;
/* 693:748 */     paramAlternativeBlock1.greedySet = paramAlternativeBlock2.greedySet;
/* 694:    */   }
/* 695:    */   
/* 696:    */   public void setRuleOption(Token paramToken1, Token paramToken2)
/* 697:    */   {
/* 698:753 */     this.ruleBlock.setOption(paramToken1, paramToken2);
/* 699:    */   }
/* 700:    */   
/* 701:    */   public void setSubruleOption(Token paramToken1, Token paramToken2)
/* 702:    */   {
/* 703:757 */     context().block.setOption(paramToken1, paramToken2);
/* 704:    */   }
/* 705:    */   
/* 706:    */   public void synPred()
/* 707:    */   {
/* 708:761 */     if (context().block.not) {
/* 709:762 */       this.tool.error("'~' cannot be applied to syntactic predicate", this.grammar.getFilename(), context().block.getLine(), context().block.getColumn());
/* 710:    */     }
/* 711:767 */     SynPredBlock localSynPredBlock = new SynPredBlock(this.grammar);
/* 712:768 */     setBlock(localSynPredBlock, context().block);
/* 713:769 */     BlockContext localBlockContext = (BlockContext)this.blocks.pop();
/* 714:770 */     this.blocks.push(new BlockContext());
/* 715:771 */     context().block = localSynPredBlock;
/* 716:772 */     context().blockEnd = localBlockContext.blockEnd;
/* 717:773 */     context().blockEnd.block = localSynPredBlock;
/* 718:    */   }
/* 719:    */   
/* 720:    */   public void zeroOrMoreSubRule()
/* 721:    */   {
/* 722:777 */     if (context().block.not) {
/* 723:778 */       this.tool.error("'~' cannot be applied to (...)+ subrule", this.grammar.getFilename(), context().block.getLine(), context().block.getColumn());
/* 724:    */     }
/* 725:783 */     ZeroOrMoreBlock localZeroOrMoreBlock = new ZeroOrMoreBlock(this.grammar);
/* 726:784 */     setBlock(localZeroOrMoreBlock, context().block);
/* 727:785 */     BlockContext localBlockContext = (BlockContext)this.blocks.pop();
/* 728:786 */     this.blocks.push(new BlockContext());
/* 729:787 */     context().block = localZeroOrMoreBlock;
/* 730:788 */     context().blockEnd = localBlockContext.blockEnd;
/* 731:789 */     context().blockEnd.block = localZeroOrMoreBlock;
/* 732:    */   }
/* 733:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.MakeGrammar
 * JD-Core Version:    0.7.0.1
 */