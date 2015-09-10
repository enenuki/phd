/*    1:     */ package antlr;
/*    2:     */ 
/*    3:     */ import antlr.collections.impl.BitSet;
/*    4:     */ import antlr.collections.impl.Vector;
/*    5:     */ import java.io.PrintStream;
/*    6:     */ 
/*    7:     */ public class LLkAnalyzer
/*    8:     */   implements LLkGrammarAnalyzer
/*    9:     */ {
/*   10:  23 */   public boolean DEBUG_ANALYZER = false;
/*   11:     */   private AlternativeBlock currentBlock;
/*   12:  25 */   protected Tool tool = null;
/*   13:  26 */   protected Grammar grammar = null;
/*   14:  28 */   protected boolean lexicalAnalysis = false;
/*   15:  30 */   CharFormatter charFormatter = new JavaCharFormatter();
/*   16:     */   
/*   17:     */   public LLkAnalyzer(Tool paramTool)
/*   18:     */   {
/*   19:  34 */     this.tool = paramTool;
/*   20:     */   }
/*   21:     */   
/*   22:     */   protected boolean altUsesWildcardDefault(Alternative paramAlternative)
/*   23:     */   {
/*   24:  41 */     AlternativeElement localAlternativeElement = paramAlternative.head;
/*   25:  43 */     if (((localAlternativeElement instanceof TreeElement)) && ((((TreeElement)localAlternativeElement).root instanceof WildcardElement))) {
/*   26:  45 */       return true;
/*   27:     */     }
/*   28:  47 */     if (((localAlternativeElement instanceof WildcardElement)) && ((localAlternativeElement.next instanceof BlockEndElement))) {
/*   29:  48 */       return true;
/*   30:     */     }
/*   31:  50 */     return false;
/*   32:     */   }
/*   33:     */   
/*   34:     */   public boolean deterministic(AlternativeBlock paramAlternativeBlock)
/*   35:     */   {
/*   36:  58 */     int i = 1;
/*   37:  59 */     if (this.DEBUG_ANALYZER) {
/*   38:  59 */       System.out.println("deterministic(" + paramAlternativeBlock + ")");
/*   39:     */     }
/*   40:  60 */     boolean bool = true;
/*   41:  61 */     int j = paramAlternativeBlock.alternatives.size();
/*   42:  62 */     AlternativeBlock localAlternativeBlock = this.currentBlock;
/*   43:  63 */     Object localObject1 = null;
/*   44:  64 */     this.currentBlock = paramAlternativeBlock;
/*   45:  67 */     if ((!paramAlternativeBlock.greedy) && (!(paramAlternativeBlock instanceof OneOrMoreBlock)) && (!(paramAlternativeBlock instanceof ZeroOrMoreBlock))) {
/*   46:  68 */       this.tool.warning("Being nongreedy only makes sense for (...)+ and (...)*", this.grammar.getFilename(), paramAlternativeBlock.getLine(), paramAlternativeBlock.getColumn());
/*   47:     */     }
/*   48:  74 */     if (j == 1)
/*   49:     */     {
/*   50:  75 */       AlternativeElement localAlternativeElement = paramAlternativeBlock.getAlternativeAt(0).head;
/*   51:  76 */       this.currentBlock.alti = 0;
/*   52:  77 */       paramAlternativeBlock.getAlternativeAt(0).cache[1] = localAlternativeElement.look(1);
/*   53:  78 */       paramAlternativeBlock.getAlternativeAt(0).lookaheadDepth = 1;
/*   54:  79 */       this.currentBlock = localAlternativeBlock;
/*   55:  80 */       return true;
/*   56:     */     }
/*   57:  84 */     for (int k = 0; k < j - 1; k++)
/*   58:     */     {
/*   59:  85 */       this.currentBlock.alti = k;
/*   60:  86 */       this.currentBlock.analysisAlt = k;
/*   61:  87 */       this.currentBlock.altj = (k + 1);
/*   62:  91 */       for (int m = k + 1; m < j; m++)
/*   63:     */       {
/*   64:  92 */         this.currentBlock.altj = m;
/*   65:  93 */         if (this.DEBUG_ANALYZER) {
/*   66:  93 */           System.out.println("comparing " + k + " against alt " + m);
/*   67:     */         }
/*   68:  94 */         this.currentBlock.analysisAlt = m;
/*   69:  95 */         i = 1;
/*   70:     */         
/*   71:     */ 
/*   72:     */ 
/*   73:  99 */         Lookahead[] arrayOfLookahead = new Lookahead[this.grammar.maxk + 1];
/*   74:     */         int n;
/*   75:     */         do
/*   76:     */         {
/*   77: 102 */           n = 0;
/*   78: 103 */           if (this.DEBUG_ANALYZER) {
/*   79: 103 */             System.out.println("checking depth " + i + "<=" + this.grammar.maxk);
/*   80:     */           }
/*   81: 105 */           localObject2 = getAltLookahead(paramAlternativeBlock, k, i);
/*   82: 106 */           localObject3 = getAltLookahead(paramAlternativeBlock, m, i);
/*   83: 110 */           if (this.DEBUG_ANALYZER) {
/*   84: 110 */             System.out.println("p is " + ((Lookahead)localObject2).toString(",", this.charFormatter, this.grammar));
/*   85:     */           }
/*   86: 111 */           if (this.DEBUG_ANALYZER) {
/*   87: 111 */             System.out.println("q is " + ((Lookahead)localObject3).toString(",", this.charFormatter, this.grammar));
/*   88:     */           }
/*   89: 113 */           arrayOfLookahead[i] = ((Lookahead)localObject2).intersection((Lookahead)localObject3);
/*   90: 114 */           if (this.DEBUG_ANALYZER) {
/*   91: 114 */             System.out.println("intersection at depth " + i + " is " + arrayOfLookahead[i].toString());
/*   92:     */           }
/*   93: 115 */           if (!arrayOfLookahead[i].nil())
/*   94:     */           {
/*   95: 116 */             n = 1;
/*   96: 117 */             i++;
/*   97:     */           }
/*   98: 120 */         } while ((n != 0) && (i <= this.grammar.maxk));
/*   99: 122 */         Object localObject2 = paramAlternativeBlock.getAlternativeAt(k);
/*  100: 123 */         Object localObject3 = paramAlternativeBlock.getAlternativeAt(m);
/*  101: 124 */         if (n != 0)
/*  102:     */         {
/*  103: 125 */           bool = false;
/*  104: 126 */           ((Alternative)localObject2).lookaheadDepth = 2147483647;
/*  105: 127 */           ((Alternative)localObject3).lookaheadDepth = 2147483647;
/*  106: 135 */           if (((Alternative)localObject2).synPred != null)
/*  107:     */           {
/*  108: 136 */             if (this.DEBUG_ANALYZER) {
/*  109: 137 */               System.out.println("alt " + k + " has a syn pred");
/*  110:     */             }
/*  111:     */           }
/*  112: 150 */           else if (((Alternative)localObject2).semPred != null)
/*  113:     */           {
/*  114: 151 */             if (this.DEBUG_ANALYZER) {
/*  115: 152 */               System.out.println("alt " + k + " has a sem pred");
/*  116:     */             }
/*  117:     */           }
/*  118: 160 */           else if (altUsesWildcardDefault((Alternative)localObject3)) {
/*  119: 163 */             localObject1 = localObject3;
/*  120: 170 */           } else if ((paramAlternativeBlock.warnWhenFollowAmbig) || ((!(((Alternative)localObject2).head instanceof BlockEndElement)) && (!(((Alternative)localObject3).head instanceof BlockEndElement)))) {
/*  121: 180 */             if (paramAlternativeBlock.generateAmbigWarnings) {
/*  122: 184 */               if ((!paramAlternativeBlock.greedySet) || (!paramAlternativeBlock.greedy) || (((!(((Alternative)localObject2).head instanceof BlockEndElement)) || ((((Alternative)localObject3).head instanceof BlockEndElement))) && ((!(((Alternative)localObject3).head instanceof BlockEndElement)) || ((((Alternative)localObject2).head instanceof BlockEndElement))))) {
/*  123: 195 */                 this.tool.errorHandler.warnAltAmbiguity(this.grammar, paramAlternativeBlock, this.lexicalAnalysis, this.grammar.maxk, arrayOfLookahead, k, m);
/*  124:     */               }
/*  125:     */             }
/*  126:     */           }
/*  127:     */         }
/*  128:     */         else
/*  129:     */         {
/*  130: 208 */           ((Alternative)localObject2).lookaheadDepth = Math.max(((Alternative)localObject2).lookaheadDepth, i);
/*  131: 209 */           ((Alternative)localObject3).lookaheadDepth = Math.max(((Alternative)localObject3).lookaheadDepth, i);
/*  132:     */         }
/*  133:     */       }
/*  134:     */     }
/*  135: 223 */     this.currentBlock = localAlternativeBlock;
/*  136: 224 */     return bool;
/*  137:     */   }
/*  138:     */   
/*  139:     */   public boolean deterministic(OneOrMoreBlock paramOneOrMoreBlock)
/*  140:     */   {
/*  141: 231 */     if (this.DEBUG_ANALYZER) {
/*  142: 231 */       System.out.println("deterministic(...)+(" + paramOneOrMoreBlock + ")");
/*  143:     */     }
/*  144: 232 */     AlternativeBlock localAlternativeBlock = this.currentBlock;
/*  145: 233 */     this.currentBlock = paramOneOrMoreBlock;
/*  146: 234 */     boolean bool1 = deterministic(paramOneOrMoreBlock);
/*  147:     */     
/*  148:     */ 
/*  149: 237 */     boolean bool2 = deterministicImpliedPath(paramOneOrMoreBlock);
/*  150: 238 */     this.currentBlock = localAlternativeBlock;
/*  151: 239 */     return (bool2) && (bool1);
/*  152:     */   }
/*  153:     */   
/*  154:     */   public boolean deterministic(ZeroOrMoreBlock paramZeroOrMoreBlock)
/*  155:     */   {
/*  156: 246 */     if (this.DEBUG_ANALYZER) {
/*  157: 246 */       System.out.println("deterministic(...)*(" + paramZeroOrMoreBlock + ")");
/*  158:     */     }
/*  159: 247 */     AlternativeBlock localAlternativeBlock = this.currentBlock;
/*  160: 248 */     this.currentBlock = paramZeroOrMoreBlock;
/*  161: 249 */     boolean bool1 = deterministic(paramZeroOrMoreBlock);
/*  162:     */     
/*  163:     */ 
/*  164: 252 */     boolean bool2 = deterministicImpliedPath(paramZeroOrMoreBlock);
/*  165: 253 */     this.currentBlock = localAlternativeBlock;
/*  166: 254 */     return (bool2) && (bool1);
/*  167:     */   }
/*  168:     */   
/*  169:     */   public boolean deterministicImpliedPath(BlockWithImpliedExitPath paramBlockWithImpliedExitPath)
/*  170:     */   {
/*  171: 263 */     boolean bool = true;
/*  172: 264 */     Vector localVector = paramBlockWithImpliedExitPath.getAlternatives();
/*  173: 265 */     int j = localVector.size();
/*  174: 266 */     this.currentBlock.altj = -1;
/*  175: 268 */     if (this.DEBUG_ANALYZER) {
/*  176: 268 */       System.out.println("deterministicImpliedPath");
/*  177:     */     }
/*  178: 269 */     for (int k = 0; k < j; k++)
/*  179:     */     {
/*  180: 270 */       Alternative localAlternative = paramBlockWithImpliedExitPath.getAlternativeAt(k);
/*  181: 272 */       if ((localAlternative.head instanceof BlockEndElement)) {
/*  182: 273 */         this.tool.warning("empty alternative makes no sense in (...)* or (...)+", this.grammar.getFilename(), paramBlockWithImpliedExitPath.getLine(), paramBlockWithImpliedExitPath.getColumn());
/*  183:     */       }
/*  184: 276 */       int i = 1;
/*  185:     */       
/*  186:     */ 
/*  187: 279 */       Lookahead[] arrayOfLookahead = new Lookahead[this.grammar.maxk + 1];
/*  188:     */       int m;
/*  189:     */       Object localObject;
/*  190:     */       do
/*  191:     */       {
/*  192: 282 */         m = 0;
/*  193: 283 */         if (this.DEBUG_ANALYZER) {
/*  194: 283 */           System.out.println("checking depth " + i + "<=" + this.grammar.maxk);
/*  195:     */         }
/*  196: 285 */         Lookahead localLookahead = paramBlockWithImpliedExitPath.next.look(i);
/*  197: 286 */         paramBlockWithImpliedExitPath.exitCache[i] = localLookahead;
/*  198: 287 */         this.currentBlock.alti = k;
/*  199: 288 */         localObject = getAltLookahead(paramBlockWithImpliedExitPath, k, i);
/*  200: 290 */         if (this.DEBUG_ANALYZER) {
/*  201: 290 */           System.out.println("follow is " + localLookahead.toString(",", this.charFormatter, this.grammar));
/*  202:     */         }
/*  203: 291 */         if (this.DEBUG_ANALYZER) {
/*  204: 291 */           System.out.println("p is " + ((Lookahead)localObject).toString(",", this.charFormatter, this.grammar));
/*  205:     */         }
/*  206: 293 */         arrayOfLookahead[i] = localLookahead.intersection((Lookahead)localObject);
/*  207: 294 */         if (this.DEBUG_ANALYZER) {
/*  208: 294 */           System.out.println("intersection at depth " + i + " is " + arrayOfLookahead[i]);
/*  209:     */         }
/*  210: 295 */         if (!arrayOfLookahead[i].nil())
/*  211:     */         {
/*  212: 296 */           m = 1;
/*  213: 297 */           i++;
/*  214:     */         }
/*  215: 300 */       } while ((m != 0) && (i <= this.grammar.maxk));
/*  216: 302 */       if (m != 0)
/*  217:     */       {
/*  218: 303 */         bool = false;
/*  219: 304 */         localAlternative.lookaheadDepth = 2147483647;
/*  220: 305 */         paramBlockWithImpliedExitPath.exitLookaheadDepth = 2147483647;
/*  221: 306 */         localObject = paramBlockWithImpliedExitPath.getAlternativeAt(this.currentBlock.alti);
/*  222: 311 */         if (paramBlockWithImpliedExitPath.warnWhenFollowAmbig) {
/*  223: 317 */           if (paramBlockWithImpliedExitPath.generateAmbigWarnings) {
/*  224: 321 */             if ((paramBlockWithImpliedExitPath.greedy == true) && (paramBlockWithImpliedExitPath.greedySet) && (!(((Alternative)localObject).head instanceof BlockEndElement)))
/*  225:     */             {
/*  226: 323 */               if (this.DEBUG_ANALYZER) {
/*  227: 323 */                 System.out.println("greedy loop");
/*  228:     */               }
/*  229:     */             }
/*  230: 330 */             else if ((!paramBlockWithImpliedExitPath.greedy) && (!(((Alternative)localObject).head instanceof BlockEndElement)))
/*  231:     */             {
/*  232: 332 */               if (this.DEBUG_ANALYZER) {
/*  233: 332 */                 System.out.println("nongreedy loop");
/*  234:     */               }
/*  235: 337 */               if (!lookaheadEquivForApproxAndFullAnalysis(paramBlockWithImpliedExitPath.exitCache, this.grammar.maxk)) {
/*  236: 338 */                 this.tool.warning(new String[] { "nongreedy block may exit incorrectly due", "\tto limitations of linear approximate lookahead (first k-1 sets", "\tin lookahead not singleton)." }, this.grammar.getFilename(), paramBlockWithImpliedExitPath.getLine(), paramBlockWithImpliedExitPath.getColumn());
/*  237:     */               }
/*  238:     */             }
/*  239:     */             else
/*  240:     */             {
/*  241: 348 */               this.tool.errorHandler.warnAltExitAmbiguity(this.grammar, paramBlockWithImpliedExitPath, this.lexicalAnalysis, this.grammar.maxk, arrayOfLookahead, k);
/*  242:     */             }
/*  243:     */           }
/*  244:     */         }
/*  245:     */       }
/*  246:     */       else
/*  247:     */       {
/*  248: 359 */         localAlternative.lookaheadDepth = Math.max(localAlternative.lookaheadDepth, i);
/*  249: 360 */         paramBlockWithImpliedExitPath.exitLookaheadDepth = Math.max(paramBlockWithImpliedExitPath.exitLookaheadDepth, i);
/*  250:     */       }
/*  251:     */     }
/*  252: 363 */     return bool;
/*  253:     */   }
/*  254:     */   
/*  255:     */   public Lookahead FOLLOW(int paramInt, RuleEndElement paramRuleEndElement)
/*  256:     */   {
/*  257: 371 */     RuleBlock localRuleBlock = (RuleBlock)paramRuleEndElement.block;
/*  258:     */     String str;
/*  259: 374 */     if (this.lexicalAnalysis) {
/*  260: 375 */       str = CodeGenerator.encodeLexerRuleName(localRuleBlock.getRuleName());
/*  261:     */     } else {
/*  262: 378 */       str = localRuleBlock.getRuleName();
/*  263:     */     }
/*  264: 381 */     if (this.DEBUG_ANALYZER) {
/*  265: 381 */       System.out.println("FOLLOW(" + paramInt + "," + str + ")");
/*  266:     */     }
/*  267: 384 */     if (paramRuleEndElement.lock[paramInt] != 0)
/*  268:     */     {
/*  269: 385 */       if (this.DEBUG_ANALYZER) {
/*  270: 385 */         System.out.println("FOLLOW cycle to " + str);
/*  271:     */       }
/*  272: 386 */       return new Lookahead(str);
/*  273:     */     }
/*  274: 390 */     if (paramRuleEndElement.cache[paramInt] != null)
/*  275:     */     {
/*  276: 391 */       if (this.DEBUG_ANALYZER) {
/*  277: 392 */         System.out.println("cache entry FOLLOW(" + paramInt + ") for " + str + ": " + paramRuleEndElement.cache[paramInt].toString(",", this.charFormatter, this.grammar));
/*  278:     */       }
/*  279: 395 */       if (paramRuleEndElement.cache[paramInt].cycle == null) {
/*  280: 396 */         return (Lookahead)paramRuleEndElement.cache[paramInt].clone();
/*  281:     */       }
/*  282: 399 */       localObject1 = (RuleSymbol)this.grammar.getSymbol(paramRuleEndElement.cache[paramInt].cycle);
/*  283: 400 */       localObject2 = ((RuleSymbol)localObject1).getBlock().endNode;
/*  284: 403 */       if (localObject2.cache[paramInt] == null) {
/*  285: 405 */         return (Lookahead)paramRuleEndElement.cache[paramInt].clone();
/*  286:     */       }
/*  287: 408 */       if (this.DEBUG_ANALYZER) {
/*  288: 409 */         System.out.println("combining FOLLOW(" + paramInt + ") for " + str + ": from " + paramRuleEndElement.cache[paramInt].toString(",", this.charFormatter, this.grammar) + " with FOLLOW for " + ((RuleBlock)((RuleEndElement)localObject2).block).getRuleName() + ": " + localObject2.cache[paramInt].toString(",", this.charFormatter, this.grammar));
/*  289:     */       }
/*  290: 412 */       if (localObject2.cache[paramInt].cycle == null)
/*  291:     */       {
/*  292: 416 */         paramRuleEndElement.cache[paramInt].combineWith(localObject2.cache[paramInt]);
/*  293: 417 */         paramRuleEndElement.cache[paramInt].cycle = null;
/*  294:     */       }
/*  295:     */       else
/*  296:     */       {
/*  297: 424 */         Lookahead localLookahead1 = FOLLOW(paramInt, (RuleEndElement)localObject2);
/*  298: 425 */         paramRuleEndElement.cache[paramInt].combineWith(localLookahead1);
/*  299:     */         
/*  300: 427 */         paramRuleEndElement.cache[paramInt].cycle = localLookahead1.cycle;
/*  301:     */       }
/*  302: 429 */       if (this.DEBUG_ANALYZER) {
/*  303: 430 */         System.out.println("saving FOLLOW(" + paramInt + ") for " + str + ": from " + paramRuleEndElement.cache[paramInt].toString(",", this.charFormatter, this.grammar));
/*  304:     */       }
/*  305: 434 */       return (Lookahead)paramRuleEndElement.cache[paramInt].clone();
/*  306:     */     }
/*  307: 438 */     paramRuleEndElement.lock[paramInt] = true;
/*  308:     */     
/*  309: 440 */     Object localObject1 = new Lookahead();
/*  310:     */     
/*  311: 442 */     Object localObject2 = (RuleSymbol)this.grammar.getSymbol(str);
/*  312: 445 */     for (int i = 0; i < ((RuleSymbol)localObject2).numReferences(); i++)
/*  313:     */     {
/*  314: 446 */       RuleRefElement localRuleRefElement = ((RuleSymbol)localObject2).getReference(i);
/*  315: 447 */       if (this.DEBUG_ANALYZER) {
/*  316: 447 */         System.out.println("next[" + str + "] is " + localRuleRefElement.next.toString());
/*  317:     */       }
/*  318: 448 */       Lookahead localLookahead2 = localRuleRefElement.next.look(paramInt);
/*  319: 449 */       if (this.DEBUG_ANALYZER) {
/*  320: 449 */         System.out.println("FIRST of next[" + str + "] ptr is " + localLookahead2.toString());
/*  321:     */       }
/*  322: 454 */       if ((localLookahead2.cycle != null) && (localLookahead2.cycle.equals(str))) {
/*  323: 455 */         localLookahead2.cycle = null;
/*  324:     */       }
/*  325: 458 */       ((Lookahead)localObject1).combineWith(localLookahead2);
/*  326: 459 */       if (this.DEBUG_ANALYZER) {
/*  327: 459 */         System.out.println("combined FOLLOW[" + str + "] is " + ((Lookahead)localObject1).toString());
/*  328:     */       }
/*  329:     */     }
/*  330: 462 */     paramRuleEndElement.lock[paramInt] = false;
/*  331: 466 */     if ((((Lookahead)localObject1).fset.nil()) && (((Lookahead)localObject1).cycle == null)) {
/*  332: 467 */       if ((this.grammar instanceof TreeWalkerGrammar)) {
/*  333: 470 */         ((Lookahead)localObject1).fset.add(3);
/*  334: 472 */       } else if ((this.grammar instanceof LexerGrammar)) {
/*  335: 479 */         ((Lookahead)localObject1).setEpsilon();
/*  336:     */       } else {
/*  337: 482 */         ((Lookahead)localObject1).fset.add(1);
/*  338:     */       }
/*  339:     */     }
/*  340: 487 */     if (this.DEBUG_ANALYZER) {
/*  341: 488 */       System.out.println("saving FOLLOW(" + paramInt + ") for " + str + ": " + ((Lookahead)localObject1).toString(",", this.charFormatter, this.grammar));
/*  342:     */     }
/*  343: 490 */     paramRuleEndElement.cache[paramInt] = ((Lookahead)((Lookahead)localObject1).clone());
/*  344:     */     
/*  345: 492 */     return localObject1;
/*  346:     */   }
/*  347:     */   
/*  348:     */   private Lookahead getAltLookahead(AlternativeBlock paramAlternativeBlock, int paramInt1, int paramInt2)
/*  349:     */   {
/*  350: 497 */     Alternative localAlternative = paramAlternativeBlock.getAlternativeAt(paramInt1);
/*  351: 498 */     AlternativeElement localAlternativeElement = localAlternative.head;
/*  352:     */     Lookahead localLookahead;
/*  353: 500 */     if (localAlternative.cache[paramInt2] == null)
/*  354:     */     {
/*  355: 501 */       localLookahead = localAlternativeElement.look(paramInt2);
/*  356: 502 */       localAlternative.cache[paramInt2] = localLookahead;
/*  357:     */     }
/*  358:     */     else
/*  359:     */     {
/*  360: 505 */       localLookahead = localAlternative.cache[paramInt2];
/*  361:     */     }
/*  362: 507 */     return localLookahead;
/*  363:     */   }
/*  364:     */   
/*  365:     */   public Lookahead look(int paramInt, ActionElement paramActionElement)
/*  366:     */   {
/*  367: 512 */     if (this.DEBUG_ANALYZER) {
/*  368: 512 */       System.out.println("lookAction(" + paramInt + "," + paramActionElement + ")");
/*  369:     */     }
/*  370: 513 */     return paramActionElement.next.look(paramInt);
/*  371:     */   }
/*  372:     */   
/*  373:     */   public Lookahead look(int paramInt, AlternativeBlock paramAlternativeBlock)
/*  374:     */   {
/*  375: 518 */     if (this.DEBUG_ANALYZER) {
/*  376: 518 */       System.out.println("lookAltBlk(" + paramInt + "," + paramAlternativeBlock + ")");
/*  377:     */     }
/*  378: 519 */     AlternativeBlock localAlternativeBlock = this.currentBlock;
/*  379: 520 */     this.currentBlock = paramAlternativeBlock;
/*  380: 521 */     Lookahead localLookahead1 = new Lookahead();
/*  381:     */     Object localObject;
/*  382: 522 */     for (int i = 0; i < paramAlternativeBlock.alternatives.size(); i++)
/*  383:     */     {
/*  384: 523 */       if (this.DEBUG_ANALYZER) {
/*  385: 523 */         System.out.println("alt " + i + " of " + paramAlternativeBlock);
/*  386:     */       }
/*  387: 525 */       this.currentBlock.analysisAlt = i;
/*  388: 526 */       localObject = paramAlternativeBlock.getAlternativeAt(i);
/*  389: 527 */       AlternativeElement localAlternativeElement = ((Alternative)localObject).head;
/*  390: 528 */       if ((this.DEBUG_ANALYZER) && 
/*  391: 529 */         (((Alternative)localObject).head == ((Alternative)localObject).tail)) {
/*  392: 530 */         System.out.println("alt " + i + " is empty");
/*  393:     */       }
/*  394: 533 */       Lookahead localLookahead2 = localAlternativeElement.look(paramInt);
/*  395: 534 */       localLookahead1.combineWith(localLookahead2);
/*  396:     */     }
/*  397: 536 */     if ((paramInt == 1) && (paramAlternativeBlock.not) && (subruleCanBeInverted(paramAlternativeBlock, this.lexicalAnalysis))) {
/*  398: 538 */       if (this.lexicalAnalysis)
/*  399:     */       {
/*  400: 539 */         BitSet localBitSet = (BitSet)((LexerGrammar)this.grammar).charVocabulary.clone();
/*  401: 540 */         localObject = localLookahead1.fset.toArray();
/*  402: 541 */         for (int j = 0; j < localObject.length; j++) {
/*  403: 542 */           localBitSet.remove(localObject[j]);
/*  404:     */         }
/*  405: 544 */         localLookahead1.fset = localBitSet;
/*  406:     */       }
/*  407:     */       else
/*  408:     */       {
/*  409: 547 */         localLookahead1.fset.notInPlace(4, this.grammar.tokenManager.maxTokenType());
/*  410:     */       }
/*  411:     */     }
/*  412: 550 */     this.currentBlock = localAlternativeBlock;
/*  413: 551 */     return localLookahead1;
/*  414:     */   }
/*  415:     */   
/*  416:     */   public Lookahead look(int paramInt, BlockEndElement paramBlockEndElement)
/*  417:     */   {
/*  418: 567 */     if (this.DEBUG_ANALYZER) {
/*  419: 567 */       System.out.println("lookBlockEnd(" + paramInt + ", " + paramBlockEndElement.block + "); lock is " + paramBlockEndElement.lock[paramInt]);
/*  420:     */     }
/*  421: 568 */     if (paramBlockEndElement.lock[paramInt] != 0) {
/*  422: 573 */       return new Lookahead();
/*  423:     */     }
/*  424:     */     Lookahead localLookahead1;
/*  425: 579 */     if (((paramBlockEndElement.block instanceof ZeroOrMoreBlock)) || ((paramBlockEndElement.block instanceof OneOrMoreBlock)))
/*  426:     */     {
/*  427: 584 */       paramBlockEndElement.lock[paramInt] = true;
/*  428: 585 */       localLookahead1 = look(paramInt, paramBlockEndElement.block);
/*  429: 586 */       paramBlockEndElement.lock[paramInt] = false;
/*  430:     */     }
/*  431:     */     else
/*  432:     */     {
/*  433: 589 */       localLookahead1 = new Lookahead();
/*  434:     */     }
/*  435: 597 */     if ((paramBlockEndElement.block instanceof TreeElement))
/*  436:     */     {
/*  437: 598 */       localLookahead1.combineWith(Lookahead.of(3));
/*  438:     */     }
/*  439: 608 */     else if ((paramBlockEndElement.block instanceof SynPredBlock))
/*  440:     */     {
/*  441: 609 */       localLookahead1.setEpsilon();
/*  442:     */     }
/*  443:     */     else
/*  444:     */     {
/*  445: 614 */       Lookahead localLookahead2 = paramBlockEndElement.block.next.look(paramInt);
/*  446: 615 */       localLookahead1.combineWith(localLookahead2);
/*  447:     */     }
/*  448: 618 */     return localLookahead1;
/*  449:     */   }
/*  450:     */   
/*  451:     */   public Lookahead look(int paramInt, CharLiteralElement paramCharLiteralElement)
/*  452:     */   {
/*  453: 641 */     if (this.DEBUG_ANALYZER) {
/*  454: 641 */       System.out.println("lookCharLiteral(" + paramInt + "," + paramCharLiteralElement + ")");
/*  455:     */     }
/*  456: 643 */     if (paramInt > 1) {
/*  457: 644 */       return paramCharLiteralElement.next.look(paramInt - 1);
/*  458:     */     }
/*  459: 646 */     if (this.lexicalAnalysis)
/*  460:     */     {
/*  461: 647 */       if (paramCharLiteralElement.not)
/*  462:     */       {
/*  463: 648 */         BitSet localBitSet = (BitSet)((LexerGrammar)this.grammar).charVocabulary.clone();
/*  464: 649 */         if (this.DEBUG_ANALYZER) {
/*  465: 649 */           System.out.println("charVocab is " + localBitSet.toString());
/*  466:     */         }
/*  467: 651 */         removeCompetingPredictionSets(localBitSet, paramCharLiteralElement);
/*  468: 652 */         if (this.DEBUG_ANALYZER) {
/*  469: 652 */           System.out.println("charVocab after removal of prior alt lookahead " + localBitSet.toString());
/*  470:     */         }
/*  471: 654 */         localBitSet.clear(paramCharLiteralElement.getType());
/*  472: 655 */         return new Lookahead(localBitSet);
/*  473:     */       }
/*  474: 658 */       return Lookahead.of(paramCharLiteralElement.getType());
/*  475:     */     }
/*  476: 663 */     this.tool.panic("Character literal reference found in parser");
/*  477:     */     
/*  478: 665 */     return Lookahead.of(paramCharLiteralElement.getType());
/*  479:     */   }
/*  480:     */   
/*  481:     */   public Lookahead look(int paramInt, CharRangeElement paramCharRangeElement)
/*  482:     */   {
/*  483: 670 */     if (this.DEBUG_ANALYZER) {
/*  484: 670 */       System.out.println("lookCharRange(" + paramInt + "," + paramCharRangeElement + ")");
/*  485:     */     }
/*  486: 672 */     if (paramInt > 1) {
/*  487: 673 */       return paramCharRangeElement.next.look(paramInt - 1);
/*  488:     */     }
/*  489: 675 */     BitSet localBitSet = BitSet.of(paramCharRangeElement.begin);
/*  490: 676 */     for (int i = paramCharRangeElement.begin + '\001'; i <= paramCharRangeElement.end; i++) {
/*  491: 677 */       localBitSet.add(i);
/*  492:     */     }
/*  493: 679 */     return new Lookahead(localBitSet);
/*  494:     */   }
/*  495:     */   
/*  496:     */   public Lookahead look(int paramInt, GrammarAtom paramGrammarAtom)
/*  497:     */   {
/*  498: 683 */     if (this.DEBUG_ANALYZER) {
/*  499: 683 */       System.out.println("look(" + paramInt + "," + paramGrammarAtom + "[" + paramGrammarAtom.getType() + "])");
/*  500:     */     }
/*  501: 685 */     if (this.lexicalAnalysis) {
/*  502: 687 */       this.tool.panic("token reference found in lexer");
/*  503:     */     }
/*  504: 690 */     if (paramInt > 1) {
/*  505: 691 */       return paramGrammarAtom.next.look(paramInt - 1);
/*  506:     */     }
/*  507: 693 */     Lookahead localLookahead = Lookahead.of(paramGrammarAtom.getType());
/*  508: 694 */     if (paramGrammarAtom.not)
/*  509:     */     {
/*  510: 696 */       int i = this.grammar.tokenManager.maxTokenType();
/*  511: 697 */       localLookahead.fset.notInPlace(4, i);
/*  512:     */       
/*  513: 699 */       removeCompetingPredictionSets(localLookahead.fset, paramGrammarAtom);
/*  514:     */     }
/*  515: 701 */     return localLookahead;
/*  516:     */   }
/*  517:     */   
/*  518:     */   public Lookahead look(int paramInt, OneOrMoreBlock paramOneOrMoreBlock)
/*  519:     */   {
/*  520: 709 */     if (this.DEBUG_ANALYZER) {
/*  521: 709 */       System.out.println("look+" + paramInt + "," + paramOneOrMoreBlock + ")");
/*  522:     */     }
/*  523: 710 */     Lookahead localLookahead = look(paramInt, paramOneOrMoreBlock);
/*  524: 711 */     return localLookahead;
/*  525:     */   }
/*  526:     */   
/*  527:     */   public Lookahead look(int paramInt, RuleBlock paramRuleBlock)
/*  528:     */   {
/*  529: 720 */     if (this.DEBUG_ANALYZER) {
/*  530: 720 */       System.out.println("lookRuleBlk(" + paramInt + "," + paramRuleBlock + ")");
/*  531:     */     }
/*  532: 721 */     Lookahead localLookahead = look(paramInt, paramRuleBlock);
/*  533: 722 */     return localLookahead;
/*  534:     */   }
/*  535:     */   
/*  536:     */   public Lookahead look(int paramInt, RuleEndElement paramRuleEndElement)
/*  537:     */   {
/*  538: 752 */     if (this.DEBUG_ANALYZER) {
/*  539: 753 */       System.out.println("lookRuleBlockEnd(" + paramInt + "); noFOLLOW=" + paramRuleEndElement.noFOLLOW + "; lock is " + paramRuleEndElement.lock[paramInt]);
/*  540:     */     }
/*  541: 755 */     if (paramRuleEndElement.noFOLLOW)
/*  542:     */     {
/*  543: 756 */       localLookahead = new Lookahead();
/*  544: 757 */       localLookahead.setEpsilon();
/*  545: 758 */       localLookahead.epsilonDepth = BitSet.of(paramInt);
/*  546: 759 */       return localLookahead;
/*  547:     */     }
/*  548: 761 */     Lookahead localLookahead = FOLLOW(paramInt, paramRuleEndElement);
/*  549: 762 */     return localLookahead;
/*  550:     */   }
/*  551:     */   
/*  552:     */   public Lookahead look(int paramInt, RuleRefElement paramRuleRefElement)
/*  553:     */   {
/*  554: 782 */     if (this.DEBUG_ANALYZER) {
/*  555: 782 */       System.out.println("lookRuleRef(" + paramInt + "," + paramRuleRefElement + ")");
/*  556:     */     }
/*  557: 783 */     RuleSymbol localRuleSymbol = (RuleSymbol)this.grammar.getSymbol(paramRuleRefElement.targetRule);
/*  558: 784 */     if ((localRuleSymbol == null) || (!localRuleSymbol.defined))
/*  559:     */     {
/*  560: 785 */       this.tool.error("no definition of rule " + paramRuleRefElement.targetRule, this.grammar.getFilename(), paramRuleRefElement.getLine(), paramRuleRefElement.getColumn());
/*  561: 786 */       return new Lookahead();
/*  562:     */     }
/*  563: 788 */     RuleBlock localRuleBlock = localRuleSymbol.getBlock();
/*  564: 789 */     RuleEndElement localRuleEndElement = localRuleBlock.endNode;
/*  565: 790 */     boolean bool = localRuleEndElement.noFOLLOW;
/*  566: 791 */     localRuleEndElement.noFOLLOW = true;
/*  567:     */     
/*  568: 793 */     Lookahead localLookahead1 = look(paramInt, paramRuleRefElement.targetRule);
/*  569: 794 */     if (this.DEBUG_ANALYZER) {
/*  570: 794 */       System.out.println("back from rule ref to " + paramRuleRefElement.targetRule);
/*  571:     */     }
/*  572: 796 */     localRuleEndElement.noFOLLOW = bool;
/*  573: 799 */     if (localLookahead1.cycle != null) {
/*  574: 800 */       this.tool.error("infinite recursion to rule " + localLookahead1.cycle + " from rule " + paramRuleRefElement.enclosingRuleName, this.grammar.getFilename(), paramRuleRefElement.getLine(), paramRuleRefElement.getColumn());
/*  575:     */     }
/*  576: 805 */     if (localLookahead1.containsEpsilon())
/*  577:     */     {
/*  578: 806 */       if (this.DEBUG_ANALYZER) {
/*  579: 807 */         System.out.println("rule ref to " + paramRuleRefElement.targetRule + " has eps, depth: " + localLookahead1.epsilonDepth);
/*  580:     */       }
/*  581: 811 */       localLookahead1.resetEpsilon();
/*  582:     */       
/*  583:     */ 
/*  584:     */ 
/*  585: 815 */       int[] arrayOfInt = localLookahead1.epsilonDepth.toArray();
/*  586: 816 */       localLookahead1.epsilonDepth = null;
/*  587: 817 */       for (int i = 0; i < arrayOfInt.length; i++)
/*  588:     */       {
/*  589: 818 */         int j = paramInt - (paramInt - arrayOfInt[i]);
/*  590: 819 */         Lookahead localLookahead2 = paramRuleRefElement.next.look(j);
/*  591: 820 */         localLookahead1.combineWith(localLookahead2);
/*  592:     */       }
/*  593:     */     }
/*  594: 826 */     return localLookahead1;
/*  595:     */   }
/*  596:     */   
/*  597:     */   public Lookahead look(int paramInt, StringLiteralElement paramStringLiteralElement)
/*  598:     */   {
/*  599: 830 */     if (this.DEBUG_ANALYZER) {
/*  600: 830 */       System.out.println("lookStringLiteral(" + paramInt + "," + paramStringLiteralElement + ")");
/*  601:     */     }
/*  602: 831 */     if (this.lexicalAnalysis)
/*  603:     */     {
/*  604: 833 */       if (paramInt > paramStringLiteralElement.processedAtomText.length()) {
/*  605: 834 */         return paramStringLiteralElement.next.look(paramInt - paramStringLiteralElement.processedAtomText.length());
/*  606:     */       }
/*  607: 838 */       return Lookahead.of(paramStringLiteralElement.processedAtomText.charAt(paramInt - 1));
/*  608:     */     }
/*  609: 843 */     if (paramInt > 1) {
/*  610: 844 */       return paramStringLiteralElement.next.look(paramInt - 1);
/*  611:     */     }
/*  612: 846 */     Lookahead localLookahead = Lookahead.of(paramStringLiteralElement.getType());
/*  613: 847 */     if (paramStringLiteralElement.not)
/*  614:     */     {
/*  615: 849 */       int i = this.grammar.tokenManager.maxTokenType();
/*  616: 850 */       localLookahead.fset.notInPlace(4, i);
/*  617:     */     }
/*  618: 852 */     return localLookahead;
/*  619:     */   }
/*  620:     */   
/*  621:     */   public Lookahead look(int paramInt, SynPredBlock paramSynPredBlock)
/*  622:     */   {
/*  623: 863 */     if (this.DEBUG_ANALYZER) {
/*  624: 863 */       System.out.println("look=>(" + paramInt + "," + paramSynPredBlock + ")");
/*  625:     */     }
/*  626: 864 */     return paramSynPredBlock.next.look(paramInt);
/*  627:     */   }
/*  628:     */   
/*  629:     */   public Lookahead look(int paramInt, TokenRangeElement paramTokenRangeElement)
/*  630:     */   {
/*  631: 868 */     if (this.DEBUG_ANALYZER) {
/*  632: 868 */       System.out.println("lookTokenRange(" + paramInt + "," + paramTokenRangeElement + ")");
/*  633:     */     }
/*  634: 870 */     if (paramInt > 1) {
/*  635: 871 */       return paramTokenRangeElement.next.look(paramInt - 1);
/*  636:     */     }
/*  637: 873 */     BitSet localBitSet = BitSet.of(paramTokenRangeElement.begin);
/*  638: 874 */     for (int i = paramTokenRangeElement.begin + 1; i <= paramTokenRangeElement.end; i++) {
/*  639: 875 */       localBitSet.add(i);
/*  640:     */     }
/*  641: 877 */     return new Lookahead(localBitSet);
/*  642:     */   }
/*  643:     */   
/*  644:     */   public Lookahead look(int paramInt, TreeElement paramTreeElement)
/*  645:     */   {
/*  646: 881 */     if (this.DEBUG_ANALYZER) {
/*  647: 882 */       System.out.println("look(" + paramInt + "," + paramTreeElement.root + "[" + paramTreeElement.root.getType() + "])");
/*  648:     */     }
/*  649: 883 */     if (paramInt > 1) {
/*  650: 884 */       return paramTreeElement.next.look(paramInt - 1);
/*  651:     */     }
/*  652: 886 */     Lookahead localLookahead = null;
/*  653: 887 */     if ((paramTreeElement.root instanceof WildcardElement))
/*  654:     */     {
/*  655: 888 */       localLookahead = paramTreeElement.root.look(1);
/*  656:     */     }
/*  657:     */     else
/*  658:     */     {
/*  659: 891 */       localLookahead = Lookahead.of(paramTreeElement.root.getType());
/*  660: 892 */       if (paramTreeElement.root.not)
/*  661:     */       {
/*  662: 894 */         int i = this.grammar.tokenManager.maxTokenType();
/*  663: 895 */         localLookahead.fset.notInPlace(4, i);
/*  664:     */       }
/*  665:     */     }
/*  666: 898 */     return localLookahead;
/*  667:     */   }
/*  668:     */   
/*  669:     */   public Lookahead look(int paramInt, WildcardElement paramWildcardElement)
/*  670:     */   {
/*  671: 902 */     if (this.DEBUG_ANALYZER) {
/*  672: 902 */       System.out.println("look(" + paramInt + "," + paramWildcardElement + ")");
/*  673:     */     }
/*  674: 905 */     if (paramInt > 1) {
/*  675: 906 */       return paramWildcardElement.next.look(paramInt - 1);
/*  676:     */     }
/*  677:     */     BitSet localBitSet;
/*  678: 910 */     if (this.lexicalAnalysis)
/*  679:     */     {
/*  680: 912 */       localBitSet = (BitSet)((LexerGrammar)this.grammar).charVocabulary.clone();
/*  681:     */     }
/*  682:     */     else
/*  683:     */     {
/*  684: 915 */       localBitSet = new BitSet(1);
/*  685:     */       
/*  686: 917 */       int i = this.grammar.tokenManager.maxTokenType();
/*  687: 918 */       localBitSet.notInPlace(4, i);
/*  688: 919 */       if (this.DEBUG_ANALYZER) {
/*  689: 919 */         System.out.println("look(" + paramInt + "," + paramWildcardElement + ") after not: " + localBitSet);
/*  690:     */       }
/*  691:     */     }
/*  692: 925 */     return new Lookahead(localBitSet);
/*  693:     */   }
/*  694:     */   
/*  695:     */   public Lookahead look(int paramInt, ZeroOrMoreBlock paramZeroOrMoreBlock)
/*  696:     */   {
/*  697: 932 */     if (this.DEBUG_ANALYZER) {
/*  698: 932 */       System.out.println("look*(" + paramInt + "," + paramZeroOrMoreBlock + ")");
/*  699:     */     }
/*  700: 933 */     Lookahead localLookahead1 = look(paramInt, paramZeroOrMoreBlock);
/*  701: 934 */     Lookahead localLookahead2 = paramZeroOrMoreBlock.next.look(paramInt);
/*  702: 935 */     localLookahead1.combineWith(localLookahead2);
/*  703: 936 */     return localLookahead1;
/*  704:     */   }
/*  705:     */   
/*  706:     */   public Lookahead look(int paramInt, String paramString)
/*  707:     */   {
/*  708: 949 */     if (this.DEBUG_ANALYZER) {
/*  709: 949 */       System.out.println("lookRuleName(" + paramInt + "," + paramString + ")");
/*  710:     */     }
/*  711: 950 */     RuleSymbol localRuleSymbol = (RuleSymbol)this.grammar.getSymbol(paramString);
/*  712: 951 */     RuleBlock localRuleBlock = localRuleSymbol.getBlock();
/*  713: 953 */     if (localRuleBlock.lock[paramInt] != 0)
/*  714:     */     {
/*  715: 954 */       if (this.DEBUG_ANALYZER) {
/*  716: 955 */         System.out.println("infinite recursion to rule " + localRuleBlock.getRuleName());
/*  717:     */       }
/*  718: 956 */       return new Lookahead(paramString);
/*  719:     */     }
/*  720: 960 */     if (localRuleBlock.cache[paramInt] != null)
/*  721:     */     {
/*  722: 961 */       if (this.DEBUG_ANALYZER) {
/*  723: 962 */         System.out.println("found depth " + paramInt + " result in FIRST " + paramString + " cache: " + localRuleBlock.cache[paramInt].toString(",", this.charFormatter, this.grammar));
/*  724:     */       }
/*  725: 965 */       return (Lookahead)localRuleBlock.cache[paramInt].clone();
/*  726:     */     }
/*  727: 968 */     localRuleBlock.lock[paramInt] = true;
/*  728: 969 */     Lookahead localLookahead = look(paramInt, localRuleBlock);
/*  729: 970 */     localRuleBlock.lock[paramInt] = false;
/*  730:     */     
/*  731:     */ 
/*  732: 973 */     localRuleBlock.cache[paramInt] = ((Lookahead)localLookahead.clone());
/*  733: 974 */     if (this.DEBUG_ANALYZER) {
/*  734: 975 */       System.out.println("saving depth " + paramInt + " result in FIRST " + paramString + " cache: " + localRuleBlock.cache[paramInt].toString(",", this.charFormatter, this.grammar));
/*  735:     */     }
/*  736: 978 */     return localLookahead;
/*  737:     */   }
/*  738:     */   
/*  739:     */   public static boolean lookaheadEquivForApproxAndFullAnalysis(Lookahead[] paramArrayOfLookahead, int paramInt)
/*  740:     */   {
/*  741: 986 */     for (int i = 1; i <= paramInt - 1; i++)
/*  742:     */     {
/*  743: 987 */       BitSet localBitSet = paramArrayOfLookahead[i].fset;
/*  744: 988 */       if (localBitSet.degree() > 1) {
/*  745: 989 */         return false;
/*  746:     */       }
/*  747:     */     }
/*  748: 992 */     return true;
/*  749:     */   }
/*  750:     */   
/*  751:     */   private void removeCompetingPredictionSets(BitSet paramBitSet, AlternativeElement paramAlternativeElement)
/*  752:     */   {
/*  753:1005 */     AlternativeElement localAlternativeElement1 = this.currentBlock.getAlternativeAt(this.currentBlock.analysisAlt).head;
/*  754:1007 */     if ((localAlternativeElement1 instanceof TreeElement))
/*  755:     */     {
/*  756:1008 */       if (((TreeElement)localAlternativeElement1).root == paramAlternativeElement) {}
/*  757:     */     }
/*  758:1012 */     else if (paramAlternativeElement != localAlternativeElement1) {
/*  759:1013 */       return;
/*  760:     */     }
/*  761:1015 */     for (int i = 0; i < this.currentBlock.analysisAlt; i++)
/*  762:     */     {
/*  763:1016 */       AlternativeElement localAlternativeElement2 = this.currentBlock.getAlternativeAt(i).head;
/*  764:1017 */       paramBitSet.subtractInPlace(localAlternativeElement2.look(1).fset);
/*  765:     */     }
/*  766:     */   }
/*  767:     */   
/*  768:     */   private void removeCompetingPredictionSetsFromWildcard(Lookahead[] paramArrayOfLookahead, AlternativeElement paramAlternativeElement, int paramInt)
/*  769:     */   {
/*  770:1029 */     for (int i = 1; i <= paramInt; i++) {
/*  771:1030 */       for (int j = 0; j < this.currentBlock.analysisAlt; j++)
/*  772:     */       {
/*  773:1031 */         AlternativeElement localAlternativeElement = this.currentBlock.getAlternativeAt(j).head;
/*  774:1032 */         paramArrayOfLookahead[i].fset.subtractInPlace(localAlternativeElement.look(i).fset);
/*  775:     */       }
/*  776:     */     }
/*  777:     */   }
/*  778:     */   
/*  779:     */   private void reset()
/*  780:     */   {
/*  781:1039 */     this.grammar = null;
/*  782:1040 */     this.DEBUG_ANALYZER = false;
/*  783:1041 */     this.currentBlock = null;
/*  784:1042 */     this.lexicalAnalysis = false;
/*  785:     */   }
/*  786:     */   
/*  787:     */   public void setGrammar(Grammar paramGrammar)
/*  788:     */   {
/*  789:1047 */     if (this.grammar != null) {
/*  790:1048 */       reset();
/*  791:     */     }
/*  792:1050 */     this.grammar = paramGrammar;
/*  793:     */     
/*  794:     */ 
/*  795:1053 */     this.lexicalAnalysis = (this.grammar instanceof LexerGrammar);
/*  796:1054 */     this.DEBUG_ANALYZER = this.grammar.analyzerDebug;
/*  797:     */   }
/*  798:     */   
/*  799:     */   public boolean subruleCanBeInverted(AlternativeBlock paramAlternativeBlock, boolean paramBoolean)
/*  800:     */   {
/*  801:1058 */     if (((paramAlternativeBlock instanceof ZeroOrMoreBlock)) || ((paramAlternativeBlock instanceof OneOrMoreBlock)) || ((paramAlternativeBlock instanceof SynPredBlock))) {
/*  802:1063 */       return false;
/*  803:     */     }
/*  804:1066 */     if (paramAlternativeBlock.alternatives.size() == 0) {
/*  805:1067 */       return false;
/*  806:     */     }
/*  807:1071 */     for (int i = 0; i < paramAlternativeBlock.alternatives.size(); i++)
/*  808:     */     {
/*  809:1072 */       Alternative localAlternative = paramAlternativeBlock.getAlternativeAt(i);
/*  810:1074 */       if ((localAlternative.synPred != null) || (localAlternative.semPred != null) || (localAlternative.exceptionSpec != null)) {
/*  811:1075 */         return false;
/*  812:     */       }
/*  813:1078 */       AlternativeElement localAlternativeElement = localAlternative.head;
/*  814:1079 */       if (((!(localAlternativeElement instanceof CharLiteralElement)) && (!(localAlternativeElement instanceof TokenRefElement)) && (!(localAlternativeElement instanceof CharRangeElement)) && (!(localAlternativeElement instanceof TokenRangeElement)) && ((!(localAlternativeElement instanceof StringLiteralElement)) || (paramBoolean))) || (!(localAlternativeElement.next instanceof BlockEndElement)) || (localAlternativeElement.getAutoGenType() != 1)) {
/*  815:1090 */         return false;
/*  816:     */       }
/*  817:     */     }
/*  818:1093 */     return true;
/*  819:     */   }
/*  820:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.LLkAnalyzer
 * JD-Core Version:    0.7.0.1
 */