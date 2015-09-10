/*    1:     */ package com.steadystate.css.parser;
/*    2:     */ 
/*    3:     */ import java.util.Enumeration;
/*    4:     */ import java.util.Vector;
/*    5:     */ import org.w3c.css.sac.CSSParseException;
/*    6:     */ import org.w3c.css.sac.Condition;
/*    7:     */ import org.w3c.css.sac.ConditionFactory;
/*    8:     */ import org.w3c.css.sac.ErrorHandler;
/*    9:     */ import org.w3c.css.sac.LexicalUnit;
/*   10:     */ import org.w3c.css.sac.Parser;
/*   11:     */ import org.w3c.css.sac.Selector;
/*   12:     */ import org.w3c.css.sac.SelectorFactory;
/*   13:     */ import org.w3c.css.sac.SelectorList;
/*   14:     */ import org.w3c.css.sac.SimpleSelector;
/*   15:     */ 
/*   16:     */ public class SACParserCSS21
/*   17:     */   extends AbstractSACParser
/*   18:     */   implements Parser, SACParserCSS21Constants
/*   19:     */ {
/*   20:  18 */   private boolean _quiet = true;
/*   21:     */   public SACParserCSS21TokenManager token_source;
/*   22:     */   public Token token;
/*   23:     */   public Token jj_nt;
/*   24:     */   private int jj_ntk;
/*   25:     */   private Token jj_scanpos;
/*   26:     */   private Token jj_lastpos;
/*   27:     */   private int jj_la;
/*   28:     */   
/*   29:     */   public SACParserCSS21()
/*   30:     */   {
/*   31:  21 */     this((CharStream)null);
/*   32:     */   }
/*   33:     */   
/*   34:     */   public String getParserVersion()
/*   35:     */   {
/*   36:  25 */     return "http://www.w3.org/TR/CSS21/";
/*   37:     */   }
/*   38:     */   
/*   39:     */   protected String getGrammarUri()
/*   40:     */   {
/*   41:  30 */     return "http://www.w3.org/TR/CSS21/grammar.html";
/*   42:     */   }
/*   43:     */   
/*   44:     */   protected Token getToken()
/*   45:     */   {
/*   46:  35 */     return this.token;
/*   47:     */   }
/*   48:     */   
/*   49:     */   public final void styleSheet()
/*   50:     */     throws ParseException
/*   51:     */   {
/*   52:     */     try
/*   53:     */     {
/*   54:  47 */       handleStartDocument();
/*   55:  48 */       styleSheetRuleList();
/*   56:  49 */       jj_consume_token(0);
/*   57:     */     }
/*   58:     */     finally
/*   59:     */     {
/*   60:  51 */       handleEndDocument();
/*   61:     */     }
/*   62:     */   }
/*   63:     */   
/*   64:     */   public final void styleSheetRuleList()
/*   65:     */     throws ParseException
/*   66:     */   {
/*   67:  56 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*   68:     */     {
/*   69:     */     case 32: 
/*   70:  58 */       charsetRule();
/*   71:  59 */       break;
/*   72:     */     default: 
/*   73:  61 */       this.jj_la1[0] = this.jj_gen;
/*   74:     */     }
/*   75:     */     for (;;)
/*   76:     */     {
/*   77:  66 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*   78:     */       {
/*   79:     */       case 1: 
/*   80:     */       case 25: 
/*   81:     */       case 26: 
/*   82:     */         break;
/*   83:     */       default: 
/*   84:  73 */         this.jj_la1[1] = this.jj_gen;
/*   85:  74 */         break;
/*   86:     */       }
/*   87:  76 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*   88:     */       {
/*   89:     */       case 1: 
/*   90:  78 */         jj_consume_token(1);
/*   91:  79 */         break;
/*   92:     */       case 25: 
/*   93:  81 */         jj_consume_token(25);
/*   94:  82 */         break;
/*   95:     */       case 26: 
/*   96:  84 */         jj_consume_token(26);
/*   97:     */       }
/*   98:     */     }
/*   99:  87 */     this.jj_la1[2] = this.jj_gen;
/*  100:  88 */     jj_consume_token(-1);
/*  101:  89 */     throw new ParseException();
/*  102:  94 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  103:     */     {
/*  104:     */     case 29: 
/*  105:     */       break;
/*  106:     */     default: 
/*  107:  99 */       this.jj_la1[3] = this.jj_gen;
/*  108: 100 */       break;
/*  109:     */     }
/*  110: 102 */     importRule();
/*  111:     */     for (;;)
/*  112:     */     {
/*  113: 105 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  114:     */       {
/*  115:     */       case 1: 
/*  116:     */       case 25: 
/*  117:     */       case 26: 
/*  118:     */         break;
/*  119:     */       default: 
/*  120: 112 */         this.jj_la1[4] = this.jj_gen;
/*  121: 113 */         break;
/*  122:     */       }
/*  123: 115 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  124:     */       {
/*  125:     */       case 1: 
/*  126: 117 */         jj_consume_token(1);
/*  127: 118 */         break;
/*  128:     */       case 25: 
/*  129: 120 */         jj_consume_token(25);
/*  130: 121 */         break;
/*  131:     */       case 26: 
/*  132: 123 */         jj_consume_token(26);
/*  133:     */       }
/*  134:     */     }
/*  135: 126 */     this.jj_la1[5] = this.jj_gen;
/*  136: 127 */     jj_consume_token(-1);
/*  137: 128 */     throw new ParseException();
/*  138: 134 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  139:     */     {
/*  140:     */     case 9: 
/*  141:     */     case 11: 
/*  142:     */     case 12: 
/*  143:     */     case 17: 
/*  144:     */     case 20: 
/*  145:     */     case 30: 
/*  146:     */     case 31: 
/*  147:     */     case 56: 
/*  148:     */       break;
/*  149:     */     default: 
/*  150: 146 */       this.jj_la1[6] = this.jj_gen;
/*  151: 147 */       break;
/*  152:     */     }
/*  153: 149 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  154:     */     {
/*  155:     */     case 9: 
/*  156:     */     case 11: 
/*  157:     */     case 12: 
/*  158:     */     case 17: 
/*  159:     */     case 20: 
/*  160:     */     case 56: 
/*  161: 156 */       styleRule();
/*  162: 157 */       break;
/*  163:     */     case 31: 
/*  164: 159 */       mediaRule();
/*  165: 160 */       break;
/*  166:     */     case 30: 
/*  167: 162 */       pageRule();
/*  168: 163 */       break;
/*  169:     */     default: 
/*  170: 165 */       this.jj_la1[7] = this.jj_gen;
/*  171: 166 */       jj_consume_token(-1);
/*  172: 167 */       throw new ParseException();
/*  173:     */     }
/*  174:     */     for (;;)
/*  175:     */     {
/*  176: 171 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  177:     */       {
/*  178:     */       case 1: 
/*  179:     */       case 25: 
/*  180:     */       case 26: 
/*  181:     */         break;
/*  182:     */       default: 
/*  183: 178 */         this.jj_la1[8] = this.jj_gen;
/*  184: 179 */         break;
/*  185:     */       }
/*  186: 181 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  187:     */       {
/*  188:     */       case 1: 
/*  189: 183 */         jj_consume_token(1);
/*  190: 184 */         break;
/*  191:     */       case 25: 
/*  192: 186 */         jj_consume_token(25);
/*  193: 187 */         break;
/*  194:     */       case 26: 
/*  195: 189 */         jj_consume_token(26);
/*  196:     */       }
/*  197:     */     }
/*  198: 192 */     this.jj_la1[9] = this.jj_gen;
/*  199: 193 */     jj_consume_token(-1);
/*  200: 194 */     throw new ParseException();
/*  201: 200 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  202:     */     {
/*  203:     */     case 9: 
/*  204:     */     case 11: 
/*  205:     */     case 12: 
/*  206:     */     case 17: 
/*  207:     */     case 20: 
/*  208:     */     case 29: 
/*  209:     */     case 30: 
/*  210:     */     case 31: 
/*  211:     */     case 56: 
/*  212:     */       break;
/*  213:     */     default: 
/*  214: 213 */       this.jj_la1[10] = this.jj_gen;
/*  215: 214 */       break;
/*  216:     */     }
/*  217: 216 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  218:     */     {
/*  219:     */     case 9: 
/*  220:     */     case 11: 
/*  221:     */     case 12: 
/*  222:     */     case 17: 
/*  223:     */     case 20: 
/*  224:     */     case 56: 
/*  225: 223 */       styleRule();
/*  226: 224 */       break;
/*  227:     */     case 31: 
/*  228: 226 */       mediaRule();
/*  229: 227 */       break;
/*  230:     */     case 30: 
/*  231: 229 */       pageRule();
/*  232: 230 */       break;
/*  233:     */     case 29: 
/*  234: 232 */       importRuleIgnored();
/*  235: 233 */       break;
/*  236:     */     default: 
/*  237: 235 */       this.jj_la1[11] = this.jj_gen;
/*  238: 236 */       jj_consume_token(-1);
/*  239: 237 */       throw new ParseException();
/*  240:     */     }
/*  241:     */     for (;;)
/*  242:     */     {
/*  243: 241 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  244:     */       {
/*  245:     */       case 1: 
/*  246:     */       case 25: 
/*  247:     */       case 26: 
/*  248:     */         break;
/*  249:     */       default: 
/*  250: 248 */         this.jj_la1[12] = this.jj_gen;
/*  251: 249 */         break;
/*  252:     */       }
/*  253: 251 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  254:     */       {
/*  255:     */       case 1: 
/*  256: 253 */         jj_consume_token(1);
/*  257: 254 */         break;
/*  258:     */       case 25: 
/*  259: 256 */         jj_consume_token(25);
/*  260: 257 */         break;
/*  261:     */       case 26: 
/*  262: 259 */         jj_consume_token(26);
/*  263:     */       }
/*  264:     */     }
/*  265: 262 */     this.jj_la1[13] = this.jj_gen;
/*  266: 263 */     jj_consume_token(-1);
/*  267: 264 */     throw new ParseException();
/*  268:     */   }
/*  269:     */   
/*  270:     */   public final void styleSheetRuleSingle()
/*  271:     */     throws ParseException
/*  272:     */   {
/*  273: 274 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  274:     */     {
/*  275:     */     case 32: 
/*  276: 276 */       charsetRule();
/*  277: 277 */       break;
/*  278:     */     case 29: 
/*  279: 279 */       importRule();
/*  280: 280 */       break;
/*  281:     */     case 9: 
/*  282:     */     case 11: 
/*  283:     */     case 12: 
/*  284:     */     case 17: 
/*  285:     */     case 20: 
/*  286:     */     case 56: 
/*  287: 287 */       styleRule();
/*  288: 288 */       break;
/*  289:     */     case 31: 
/*  290: 290 */       mediaRule();
/*  291: 291 */       break;
/*  292:     */     case 30: 
/*  293: 293 */       pageRule();
/*  294: 294 */       break;
/*  295:     */     case 33: 
/*  296: 296 */       unknownRule();
/*  297: 297 */       break;
/*  298:     */     default: 
/*  299: 299 */       this.jj_la1[14] = this.jj_gen;
/*  300: 300 */       jj_consume_token(-1);
/*  301: 301 */       throw new ParseException();
/*  302:     */     }
/*  303:     */   }
/*  304:     */   
/*  305:     */   public final void charsetRule()
/*  306:     */     throws ParseException
/*  307:     */   {
/*  308:     */     try
/*  309:     */     {
/*  310: 308 */       jj_consume_token(32);
/*  311: 309 */       Token t = jj_consume_token(21);
/*  312: 310 */       jj_consume_token(10);
/*  313: 311 */       handleCharset(t.toString());
/*  314:     */     }
/*  315:     */     catch (ParseException e)
/*  316:     */     {
/*  317: 313 */       getErrorHandler().error(toCSSParseException("invalidCharsetRule", e));
/*  318:     */     }
/*  319:     */   }
/*  320:     */   
/*  321:     */   public final void unknownRule()
/*  322:     */     throws ParseException
/*  323:     */   {
/*  324:     */     try
/*  325:     */     {
/*  326: 322 */       Token t = jj_consume_token(33);
/*  327: 323 */       String s = skip();
/*  328: 324 */       handleIgnorableAtRule(s);
/*  329:     */     }
/*  330:     */     catch (ParseException e)
/*  331:     */     {
/*  332: 326 */       getErrorHandler().error(toCSSParseException("invalidUnknownRule", e));
/*  333:     */     }
/*  334:     */   }
/*  335:     */   
/*  336:     */   public final void importRule()
/*  337:     */     throws ParseException
/*  338:     */   {
/*  339: 340 */     SACMediaListImpl ml = new SACMediaListImpl();
/*  340:     */     try
/*  341:     */     {
/*  342: 342 */       jj_consume_token(29);
/*  343:     */       for (;;)
/*  344:     */       {
/*  345: 345 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  346:     */         {
/*  347:     */         case 1: 
/*  348:     */           break;
/*  349:     */         default: 
/*  350: 350 */           this.jj_la1[15] = this.jj_gen;
/*  351: 351 */           break;
/*  352:     */         }
/*  353: 353 */         jj_consume_token(1);
/*  354:     */       }
/*  355:     */       Token t;
/*  356: 355 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  357:     */       {
/*  358:     */       case 21: 
/*  359: 357 */         t = jj_consume_token(21);
/*  360: 358 */         break;
/*  361:     */       case 24: 
/*  362: 360 */         t = jj_consume_token(24);
/*  363: 361 */         break;
/*  364:     */       default: 
/*  365: 363 */         this.jj_la1[16] = this.jj_gen;
/*  366: 364 */         jj_consume_token(-1);
/*  367: 365 */         throw new ParseException();
/*  368:     */       }
/*  369:     */       for (;;)
/*  370:     */       {
/*  371: 369 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  372:     */         {
/*  373:     */         case 1: 
/*  374:     */           break;
/*  375:     */         default: 
/*  376: 374 */           this.jj_la1[17] = this.jj_gen;
/*  377: 375 */           break;
/*  378:     */         }
/*  379: 377 */         jj_consume_token(1);
/*  380:     */       }
/*  381: 379 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  382:     */       {
/*  383:     */       case 56: 
/*  384: 381 */         mediaList(ml);
/*  385: 382 */         break;
/*  386:     */       default: 
/*  387: 384 */         this.jj_la1[18] = this.jj_gen;
/*  388:     */       }
/*  389: 387 */       jj_consume_token(10);
/*  390: 388 */       handleImportStyle(unescape(t.image), ml, null);
/*  391:     */     }
/*  392:     */     catch (CSSParseException e)
/*  393:     */     {
/*  394: 390 */       getErrorHandler().error(e);
/*  395: 391 */       error_skipAtRule();
/*  396:     */     }
/*  397:     */     catch (ParseException e)
/*  398:     */     {
/*  399: 393 */       getErrorHandler().error(toCSSParseException("invalidImportRule", e));
/*  400:     */       
/*  401: 395 */       error_skipAtRule();
/*  402:     */     }
/*  403:     */   }
/*  404:     */   
/*  405:     */   public final void importRuleIgnored()
/*  406:     */     throws ParseException
/*  407:     */   {
/*  408: 401 */     SACMediaListImpl ml = new SACMediaListImpl();
/*  409: 402 */     ParseException e = generateParseException();
/*  410:     */     try
/*  411:     */     {
/*  412: 404 */       jj_consume_token(29);
/*  413:     */       for (;;)
/*  414:     */       {
/*  415: 407 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  416:     */         {
/*  417:     */         case 1: 
/*  418:     */           break;
/*  419:     */         default: 
/*  420: 412 */           this.jj_la1[19] = this.jj_gen;
/*  421: 413 */           break;
/*  422:     */         }
/*  423: 415 */         jj_consume_token(1);
/*  424:     */       }
/*  425:     */       Token t;
/*  426: 417 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  427:     */       {
/*  428:     */       case 21: 
/*  429: 419 */         t = jj_consume_token(21);
/*  430: 420 */         break;
/*  431:     */       case 24: 
/*  432: 422 */         t = jj_consume_token(24);
/*  433: 423 */         break;
/*  434:     */       default: 
/*  435: 425 */         this.jj_la1[20] = this.jj_gen;
/*  436: 426 */         jj_consume_token(-1);
/*  437: 427 */         throw new ParseException();
/*  438:     */       }
/*  439:     */       for (;;)
/*  440:     */       {
/*  441: 431 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  442:     */         {
/*  443:     */         case 1: 
/*  444:     */           break;
/*  445:     */         default: 
/*  446: 436 */           this.jj_la1[21] = this.jj_gen;
/*  447: 437 */           break;
/*  448:     */         }
/*  449: 439 */         jj_consume_token(1);
/*  450:     */       }
/*  451: 441 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  452:     */       {
/*  453:     */       case 56: 
/*  454: 443 */         mediaList(ml);
/*  455: 444 */         break;
/*  456:     */       default: 
/*  457: 446 */         this.jj_la1[22] = this.jj_gen;
/*  458:     */       }
/*  459: 449 */       jj_consume_token(10);
/*  460:     */     }
/*  461:     */     finally
/*  462:     */     {
/*  463: 451 */       getErrorHandler().error(toCSSParseException("invalidImportRuleIgnored", e));
/*  464:     */     }
/*  465:     */   }
/*  466:     */   
/*  467:     */   public final void mediaRule()
/*  468:     */     throws ParseException
/*  469:     */   {
/*  470: 461 */     boolean start = false;
/*  471: 462 */     SACMediaListImpl ml = new SACMediaListImpl();
/*  472:     */     try
/*  473:     */     {
/*  474: 464 */       jj_consume_token(31);
/*  475:     */       for (;;)
/*  476:     */       {
/*  477: 467 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  478:     */         {
/*  479:     */         case 1: 
/*  480:     */           break;
/*  481:     */         default: 
/*  482: 472 */           this.jj_la1[23] = this.jj_gen;
/*  483: 473 */           break;
/*  484:     */         }
/*  485: 475 */         jj_consume_token(1);
/*  486:     */       }
/*  487: 477 */       mediaList(ml);
/*  488: 478 */       start = true;
/*  489: 479 */       handleStartMedia(ml);
/*  490: 480 */       jj_consume_token(6);
/*  491:     */       for (;;)
/*  492:     */       {
/*  493: 483 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  494:     */         {
/*  495:     */         case 1: 
/*  496:     */           break;
/*  497:     */         default: 
/*  498: 488 */           this.jj_la1[24] = this.jj_gen;
/*  499: 489 */           break;
/*  500:     */         }
/*  501: 491 */         jj_consume_token(1);
/*  502:     */       }
/*  503: 493 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  504:     */       {
/*  505:     */       case 9: 
/*  506:     */       case 11: 
/*  507:     */       case 12: 
/*  508:     */       case 17: 
/*  509:     */       case 20: 
/*  510:     */       case 30: 
/*  511:     */       case 33: 
/*  512:     */       case 56: 
/*  513: 502 */         mediaRuleList();
/*  514: 503 */         break;
/*  515:     */       default: 
/*  516: 505 */         this.jj_la1[25] = this.jj_gen;
/*  517:     */       }
/*  518: 508 */       jj_consume_token(7);
/*  519:     */     }
/*  520:     */     catch (CSSParseException e)
/*  521:     */     {
/*  522: 510 */       getErrorHandler().error(e);
/*  523: 511 */       error_skipblock();
/*  524:     */     }
/*  525:     */     catch (ParseException e)
/*  526:     */     {
/*  527: 513 */       CSSParseException cpe = toCSSParseException("invalidMediaRule", e);
/*  528: 514 */       getErrorHandler().error(cpe);
/*  529: 515 */       getErrorHandler().warning(createSkipWarning("ignoringRule", cpe));
/*  530: 516 */       error_skipblock();
/*  531:     */     }
/*  532:     */     finally
/*  533:     */     {
/*  534: 518 */       if (start) {
/*  535: 519 */         handleEndMedia(ml);
/*  536:     */       }
/*  537:     */     }
/*  538:     */   }
/*  539:     */   
/*  540:     */   public final void mediaList(SACMediaListImpl ml)
/*  541:     */     throws ParseException
/*  542:     */   {
/*  543:     */     try
/*  544:     */     {
/*  545: 527 */       String s = medium();
/*  546:     */       for (;;)
/*  547:     */       {
/*  548: 530 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  549:     */         {
/*  550:     */         case 8: 
/*  551:     */           break;
/*  552:     */         default: 
/*  553: 535 */           this.jj_la1[26] = this.jj_gen;
/*  554: 536 */           break;
/*  555:     */         }
/*  556: 538 */         jj_consume_token(8);
/*  557:     */         for (;;)
/*  558:     */         {
/*  559: 541 */           switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  560:     */           {
/*  561:     */           case 1: 
/*  562:     */             break;
/*  563:     */           default: 
/*  564: 546 */             this.jj_la1[27] = this.jj_gen;
/*  565: 547 */             break;
/*  566:     */           }
/*  567: 549 */           jj_consume_token(1);
/*  568:     */         }
/*  569: 551 */         ml.add(s);
/*  570: 552 */         s = medium();
/*  571:     */       }
/*  572: 554 */       ml.add(s);
/*  573:     */     }
/*  574:     */     catch (ParseException e)
/*  575:     */     {
/*  576: 556 */       throw toCSSParseException("invalidMediaList", e);
/*  577:     */     }
/*  578:     */   }
/*  579:     */   
/*  580:     */   public final void mediaRuleList()
/*  581:     */     throws ParseException
/*  582:     */   {
/*  583:     */     for (;;)
/*  584:     */     {
/*  585: 563 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  586:     */       {
/*  587:     */       case 9: 
/*  588:     */       case 11: 
/*  589:     */       case 12: 
/*  590:     */       case 17: 
/*  591:     */       case 20: 
/*  592:     */       case 56: 
/*  593: 570 */         styleRule();
/*  594: 571 */         break;
/*  595:     */       case 30: 
/*  596: 573 */         pageRule();
/*  597: 574 */         break;
/*  598:     */       case 33: 
/*  599: 576 */         unknownRule();
/*  600: 577 */         break;
/*  601:     */       default: 
/*  602: 579 */         this.jj_la1[28] = this.jj_gen;
/*  603: 580 */         jj_consume_token(-1);
/*  604: 581 */         throw new ParseException();
/*  605:     */       }
/*  606:     */       for (;;)
/*  607:     */       {
/*  608: 585 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  609:     */         {
/*  610:     */         case 1: 
/*  611:     */           break;
/*  612:     */         default: 
/*  613: 590 */           this.jj_la1[29] = this.jj_gen;
/*  614: 591 */           break;
/*  615:     */         }
/*  616: 593 */         jj_consume_token(1);
/*  617:     */       }
/*  618: 595 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  619:     */       {
/*  620:     */       }
/*  621:     */     }
/*  622: 607 */     this.jj_la1[30] = this.jj_gen;
/*  623:     */   }
/*  624:     */   
/*  625:     */   public final void mediaRuleSingle()
/*  626:     */     throws ParseException
/*  627:     */   {
/*  628: 614 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  629:     */     {
/*  630:     */     case 9: 
/*  631:     */     case 11: 
/*  632:     */     case 12: 
/*  633:     */     case 17: 
/*  634:     */     case 20: 
/*  635:     */     case 56: 
/*  636: 621 */       styleRule();
/*  637: 622 */       break;
/*  638:     */     case 30: 
/*  639: 624 */       pageRule();
/*  640: 625 */       break;
/*  641:     */     case 33: 
/*  642: 627 */       unknownRule();
/*  643: 628 */       break;
/*  644:     */     default: 
/*  645: 630 */       this.jj_la1[31] = this.jj_gen;
/*  646: 631 */       jj_consume_token(-1);
/*  647: 632 */       throw new ParseException();
/*  648:     */     }
/*  649:     */   }
/*  650:     */   
/*  651:     */   public final String medium()
/*  652:     */     throws ParseException
/*  653:     */   {
/*  654: 643 */     Token t = jj_consume_token(56);
/*  655:     */     for (;;)
/*  656:     */     {
/*  657: 646 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  658:     */       {
/*  659:     */       case 1: 
/*  660:     */         break;
/*  661:     */       default: 
/*  662: 651 */         this.jj_la1[32] = this.jj_gen;
/*  663: 652 */         break;
/*  664:     */       }
/*  665: 654 */       jj_consume_token(1);
/*  666:     */     }
/*  667: 656 */     return t.image;
/*  668:     */   }
/*  669:     */   
/*  670:     */   public final void pageRule()
/*  671:     */     throws ParseException
/*  672:     */   {
/*  673: 667 */     Token t = null;
/*  674: 668 */     String s = null;
/*  675: 669 */     boolean start = false;
/*  676:     */     try
/*  677:     */     {
/*  678: 671 */       jj_consume_token(30);
/*  679:     */       for (;;)
/*  680:     */       {
/*  681: 674 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  682:     */         {
/*  683:     */         case 1: 
/*  684:     */           break;
/*  685:     */         default: 
/*  686: 679 */           this.jj_la1[33] = this.jj_gen;
/*  687: 680 */           break;
/*  688:     */         }
/*  689: 682 */         jj_consume_token(1);
/*  690:     */       }
/*  691: 684 */       if (jj_2_1(2))
/*  692:     */       {
/*  693: 685 */         s = pseudoPage();
/*  694:     */         for (;;)
/*  695:     */         {
/*  696: 688 */           switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  697:     */           {
/*  698:     */           case 1: 
/*  699:     */             break;
/*  700:     */           default: 
/*  701: 693 */             this.jj_la1[34] = this.jj_gen;
/*  702: 694 */             break;
/*  703:     */           }
/*  704: 696 */           jj_consume_token(1);
/*  705:     */         }
/*  706:     */       }
/*  707: 701 */       jj_consume_token(6);
/*  708:     */       for (;;)
/*  709:     */       {
/*  710: 704 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  711:     */         {
/*  712:     */         case 1: 
/*  713:     */           break;
/*  714:     */         default: 
/*  715: 709 */           this.jj_la1[35] = this.jj_gen;
/*  716: 710 */           break;
/*  717:     */         }
/*  718: 712 */         jj_consume_token(1);
/*  719:     */       }
/*  720: 714 */       start = true;
/*  721: 715 */       handleStartPage(t != null ? unescape(t.image) : null, s);
/*  722: 716 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  723:     */       {
/*  724:     */       case 56: 
/*  725: 718 */         declaration();
/*  726: 719 */         break;
/*  727:     */       default: 
/*  728: 721 */         this.jj_la1[36] = this.jj_gen;
/*  729:     */       }
/*  730:     */       for (;;)
/*  731:     */       {
/*  732: 726 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  733:     */         {
/*  734:     */         case 10: 
/*  735:     */           break;
/*  736:     */         default: 
/*  737: 731 */           this.jj_la1[37] = this.jj_gen;
/*  738: 732 */           break;
/*  739:     */         }
/*  740: 734 */         jj_consume_token(10);
/*  741:     */         for (;;)
/*  742:     */         {
/*  743: 737 */           switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  744:     */           {
/*  745:     */           case 1: 
/*  746:     */             break;
/*  747:     */           default: 
/*  748: 742 */             this.jj_la1[38] = this.jj_gen;
/*  749: 743 */             break;
/*  750:     */           }
/*  751: 745 */           jj_consume_token(1);
/*  752:     */         }
/*  753: 747 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  754:     */         {
/*  755:     */         case 56: 
/*  756: 749 */           declaration();
/*  757: 750 */           break;
/*  758:     */         default: 
/*  759: 752 */           this.jj_la1[39] = this.jj_gen;
/*  760:     */         }
/*  761:     */       }
/*  762: 756 */       jj_consume_token(7);
/*  763:     */     }
/*  764:     */     catch (ParseException e)
/*  765:     */     {
/*  766: 758 */       throw toCSSParseException("invalidPageRule", e);
/*  767:     */     }
/*  768:     */     finally
/*  769:     */     {
/*  770: 760 */       if (start) {
/*  771: 761 */         handleEndPage(t != null ? unescape(t.image) : null, s);
/*  772:     */       }
/*  773:     */     }
/*  774:     */   }
/*  775:     */   
/*  776:     */   public final String pseudoPage()
/*  777:     */     throws ParseException
/*  778:     */   {
/*  779: 773 */     jj_consume_token(11);
/*  780: 774 */     Token t = jj_consume_token(56);
/*  781: 775 */     return t.image;
/*  782:     */   }
/*  783:     */   
/*  784:     */   public final LexicalUnit operator(LexicalUnit prev)
/*  785:     */     throws ParseException
/*  786:     */   {
/*  787:     */     Token t;
/*  788: 786 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  789:     */     {
/*  790:     */     case 13: 
/*  791: 788 */       t = jj_consume_token(13);
/*  792:     */       for (;;)
/*  793:     */       {
/*  794: 791 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  795:     */         {
/*  796:     */         case 1: 
/*  797:     */           break;
/*  798:     */         default: 
/*  799: 796 */           this.jj_la1[40] = this.jj_gen;
/*  800: 797 */           break;
/*  801:     */         }
/*  802: 799 */         jj_consume_token(1);
/*  803:     */       }
/*  804: 801 */       return new LexicalUnitImpl(prev, (short)4);
/*  805:     */     case 8: 
/*  806: 804 */       t = jj_consume_token(8);
/*  807:     */       for (;;)
/*  808:     */       {
/*  809: 807 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  810:     */         {
/*  811:     */         case 1: 
/*  812:     */           break;
/*  813:     */         default: 
/*  814: 812 */           this.jj_la1[41] = this.jj_gen;
/*  815: 813 */           break;
/*  816:     */         }
/*  817: 815 */         jj_consume_token(1);
/*  818:     */       }
/*  819: 817 */       return new LexicalUnitImpl(prev, (short)0);
/*  820:     */     }
/*  821: 820 */     this.jj_la1[42] = this.jj_gen;
/*  822: 821 */     jj_consume_token(-1);
/*  823: 822 */     throw new ParseException();
/*  824:     */   }
/*  825:     */   
/*  826:     */   public final char combinator()
/*  827:     */     throws ParseException
/*  828:     */   {
/*  829: 835 */     char c = ' ';
/*  830: 836 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  831:     */     {
/*  832:     */     case 14: 
/*  833: 838 */       jj_consume_token(14);
/*  834: 839 */       c = '+';
/*  835:     */       for (;;)
/*  836:     */       {
/*  837: 842 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  838:     */         {
/*  839:     */         case 1: 
/*  840:     */           break;
/*  841:     */         default: 
/*  842: 847 */           this.jj_la1[43] = this.jj_gen;
/*  843: 848 */           break;
/*  844:     */         }
/*  845: 850 */         jj_consume_token(1);
/*  846:     */       }
/*  847:     */     case 19: 
/*  848: 854 */       jj_consume_token(19);
/*  849: 855 */       c = '>';
/*  850:     */       for (;;)
/*  851:     */       {
/*  852: 858 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  853:     */         {
/*  854:     */         case 1: 
/*  855:     */           break;
/*  856:     */         default: 
/*  857: 863 */           this.jj_la1[44] = this.jj_gen;
/*  858: 864 */           break;
/*  859:     */         }
/*  860: 866 */         jj_consume_token(1);
/*  861:     */       }
/*  862:     */     case 1: 
/*  863: 870 */       jj_consume_token(1);
/*  864: 871 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  865:     */       {
/*  866:     */       case 14: 
/*  867:     */       case 19: 
/*  868: 874 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  869:     */         {
/*  870:     */         case 14: 
/*  871: 876 */           jj_consume_token(14);
/*  872: 877 */           c = '+';
/*  873: 878 */           break;
/*  874:     */         case 19: 
/*  875: 880 */           jj_consume_token(19);
/*  876: 881 */           c = '>';
/*  877: 882 */           break;
/*  878:     */         default: 
/*  879: 884 */           this.jj_la1[45] = this.jj_gen;
/*  880: 885 */           jj_consume_token(-1);
/*  881: 886 */           throw new ParseException();
/*  882:     */         }
/*  883:     */         for (;;)
/*  884:     */         {
/*  885: 890 */           switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  886:     */           {
/*  887:     */           case 1: 
/*  888:     */             break;
/*  889:     */           default: 
/*  890: 895 */             this.jj_la1[46] = this.jj_gen;
/*  891: 896 */             break;
/*  892:     */           }
/*  893: 898 */           jj_consume_token(1);
/*  894:     */         }
/*  895:     */       }
/*  896: 902 */       this.jj_la1[47] = this.jj_gen;
/*  897:     */       
/*  898:     */ 
/*  899: 905 */       break;
/*  900:     */     default: 
/*  901: 907 */       this.jj_la1[48] = this.jj_gen;
/*  902: 908 */       jj_consume_token(-1);
/*  903: 909 */       throw new ParseException();
/*  904:     */     }
/*  905: 911 */     return c;
/*  906:     */   }
/*  907:     */   
/*  908:     */   public final char unaryOperator()
/*  909:     */     throws ParseException
/*  910:     */   {
/*  911: 921 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  912:     */     {
/*  913:     */     case 15: 
/*  914: 923 */       jj_consume_token(15);
/*  915: 924 */       return '-';
/*  916:     */     case 14: 
/*  917: 927 */       jj_consume_token(14);
/*  918: 928 */       return '+';
/*  919:     */     }
/*  920: 931 */     this.jj_la1[49] = this.jj_gen;
/*  921: 932 */     jj_consume_token(-1);
/*  922: 933 */     throw new ParseException();
/*  923:     */   }
/*  924:     */   
/*  925:     */   public final String property()
/*  926:     */     throws ParseException
/*  927:     */   {
/*  928: 945 */     Token t = jj_consume_token(56);
/*  929:     */     for (;;)
/*  930:     */     {
/*  931: 948 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  932:     */       {
/*  933:     */       case 1: 
/*  934:     */         break;
/*  935:     */       default: 
/*  936: 953 */         this.jj_la1[50] = this.jj_gen;
/*  937: 954 */         break;
/*  938:     */       }
/*  939: 956 */       jj_consume_token(1);
/*  940:     */     }
/*  941: 958 */     return unescape(t.image);
/*  942:     */   }
/*  943:     */   
/*  944:     */   public final void styleRule()
/*  945:     */     throws ParseException
/*  946:     */   {
/*  947: 969 */     SelectorList selList = null;
/*  948: 970 */     boolean start = false;
/*  949:     */     try
/*  950:     */     {
/*  951: 972 */       selList = selectorList();
/*  952: 973 */       jj_consume_token(6);
/*  953:     */       for (;;)
/*  954:     */       {
/*  955: 976 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  956:     */         {
/*  957:     */         case 1: 
/*  958:     */           break;
/*  959:     */         default: 
/*  960: 981 */           this.jj_la1[51] = this.jj_gen;
/*  961: 982 */           break;
/*  962:     */         }
/*  963: 984 */         jj_consume_token(1);
/*  964:     */       }
/*  965: 986 */       start = true;
/*  966: 987 */       handleStartSelector(selList);
/*  967: 988 */       styleDeclaration();
/*  968: 989 */       jj_consume_token(7);
/*  969:     */     }
/*  970:     */     catch (CSSParseException e)
/*  971:     */     {
/*  972: 991 */       getErrorHandler().error(e);
/*  973: 992 */       getErrorHandler().warning(createSkipWarning("ignoringRule", e));
/*  974: 993 */       error_skipblock();
/*  975:     */     }
/*  976:     */     catch (ParseException e)
/*  977:     */     {
/*  978: 995 */       CSSParseException cpe = toCSSParseException("invalidStyleRule", e);
/*  979: 996 */       getErrorHandler().error(cpe);
/*  980: 997 */       getErrorHandler().warning(createSkipWarning("ignoringFollowingDeclarations", cpe));
/*  981: 998 */       error_skipblock();
/*  982:     */     }
/*  983:     */     finally
/*  984:     */     {
/*  985:1000 */       if (start) {
/*  986:1001 */         handleEndSelector(selList);
/*  987:     */       }
/*  988:     */     }
/*  989:     */   }
/*  990:     */   
/*  991:     */   public final SelectorList selectorList()
/*  992:     */     throws ParseException
/*  993:     */   {
/*  994:1007 */     SelectorListImpl selList = new SelectorListImpl();
/*  995:     */     
/*  996:1009 */     Selector sel = selector();
/*  997:     */     for (;;)
/*  998:     */     {
/*  999:1012 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1000:     */       {
/* 1001:     */       case 8: 
/* 1002:     */         break;
/* 1003:     */       default: 
/* 1004:1017 */         this.jj_la1[52] = this.jj_gen;
/* 1005:1018 */         break;
/* 1006:     */       }
/* 1007:1020 */       jj_consume_token(8);
/* 1008:     */       for (;;)
/* 1009:     */       {
/* 1010:1023 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1011:     */         {
/* 1012:     */         case 1: 
/* 1013:     */           break;
/* 1014:     */         default: 
/* 1015:1028 */           this.jj_la1[53] = this.jj_gen;
/* 1016:1029 */           break;
/* 1017:     */         }
/* 1018:1031 */         jj_consume_token(1);
/* 1019:     */       }
/* 1020:1033 */       selList.add(sel);
/* 1021:1034 */       sel = selector();
/* 1022:     */     }
/* 1023:1036 */     selList.add(sel);
/* 1024:1037 */     return selList;
/* 1025:     */   }
/* 1026:     */   
/* 1027:     */   public final Selector selector()
/* 1028:     */     throws ParseException
/* 1029:     */   {
/* 1030:     */     try
/* 1031:     */     {
/* 1032:1050 */       Selector sel = simpleSelector(null, ' ');
/* 1033:1053 */       while (jj_2_2(2))
/* 1034:     */       {
/* 1035:1058 */         char comb = combinator();
/* 1036:1059 */         sel = simpleSelector(sel, comb);
/* 1037:     */       }
/* 1038:     */       for (;;)
/* 1039:     */       {
/* 1040:1063 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1041:     */         {
/* 1042:     */         case 1: 
/* 1043:     */           break;
/* 1044:     */         default: 
/* 1045:1068 */           this.jj_la1[54] = this.jj_gen;
/* 1046:1069 */           break;
/* 1047:     */         }
/* 1048:1071 */         jj_consume_token(1);
/* 1049:     */       }
/* 1050:1073 */       return sel;
/* 1051:     */     }
/* 1052:     */     catch (ParseException e)
/* 1053:     */     {
/* 1054:1075 */       throw toCSSParseException("invalidSelector", e);
/* 1055:     */     }
/* 1056:     */   }
/* 1057:     */   
/* 1058:     */   public final Selector simpleSelector(Selector sel, char comb)
/* 1059:     */     throws ParseException
/* 1060:     */   {
/* 1061:1089 */     SimpleSelector simpleSel = null;
/* 1062:1090 */     Condition c = null;
/* 1063:     */     try
/* 1064:     */     {
/* 1065:1092 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1066:     */       {
/* 1067:     */       case 12: 
/* 1068:     */       case 56: 
/* 1069:1095 */         simpleSel = elementName();
/* 1070:     */         for (;;)
/* 1071:     */         {
/* 1072:1098 */           switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1073:     */           {
/* 1074:     */           case 9: 
/* 1075:     */           case 11: 
/* 1076:     */           case 17: 
/* 1077:     */           case 20: 
/* 1078:     */             break;
/* 1079:     */           default: 
/* 1080:1106 */             this.jj_la1[55] = this.jj_gen;
/* 1081:1107 */             break;
/* 1082:     */           }
/* 1083:1109 */           switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1084:     */           {
/* 1085:     */           case 20: 
/* 1086:1111 */             c = hash(c);
/* 1087:1112 */             break;
/* 1088:     */           case 9: 
/* 1089:1114 */             c = _class(c);
/* 1090:1115 */             break;
/* 1091:     */           case 17: 
/* 1092:1117 */             c = attrib(c);
/* 1093:1118 */             break;
/* 1094:     */           case 11: 
/* 1095:1120 */             c = pseudo(c);
/* 1096:     */           }
/* 1097:     */         }
/* 1098:1123 */         this.jj_la1[56] = this.jj_gen;
/* 1099:1124 */         jj_consume_token(-1);
/* 1100:1125 */         throw new ParseException();
/* 1101:     */       case 9: 
/* 1102:     */       case 11: 
/* 1103:     */       case 17: 
/* 1104:     */       case 20: 
/* 1105:1133 */         simpleSel = getSelectorFactory().createElementSelector(null, null);
/* 1106:     */         for (;;)
/* 1107:     */         {
/* 1108:1136 */           switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1109:     */           {
/* 1110:     */           case 20: 
/* 1111:1138 */             c = hash(c);
/* 1112:1139 */             break;
/* 1113:     */           case 9: 
/* 1114:1141 */             c = _class(c);
/* 1115:1142 */             break;
/* 1116:     */           case 17: 
/* 1117:1144 */             c = attrib(c);
/* 1118:1145 */             break;
/* 1119:     */           case 11: 
/* 1120:1147 */             c = pseudo(c);
/* 1121:1148 */             break;
/* 1122:     */           default: 
/* 1123:1150 */             this.jj_la1[57] = this.jj_gen;
/* 1124:1151 */             jj_consume_token(-1);
/* 1125:1152 */             throw new ParseException();
/* 1126:     */           }
/* 1127:1154 */           switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1128:     */           {
/* 1129:     */           }
/* 1130:     */         }
/* 1131:1162 */         this.jj_la1[58] = this.jj_gen;
/* 1132:1163 */         break;
/* 1133:     */       default: 
/* 1134:1168 */         this.jj_la1[59] = this.jj_gen;
/* 1135:1169 */         jj_consume_token(-1);
/* 1136:1170 */         throw new ParseException();
/* 1137:     */       }
/* 1138:1172 */       if (c != null) {
/* 1139:1173 */         simpleSel = getSelectorFactory().createConditionalSelector(simpleSel, c);
/* 1140:     */       }
/* 1141:1176 */       if (sel != null) {
/* 1142:1177 */         switch (comb)
/* 1143:     */         {
/* 1144:     */         case ' ': 
/* 1145:1179 */           sel = getSelectorFactory().createDescendantSelector(sel, simpleSel);
/* 1146:1180 */           break;
/* 1147:     */         case '+': 
/* 1148:1182 */           sel = getSelectorFactory().createDirectAdjacentSelector(sel.getSelectorType(), sel, simpleSel);
/* 1149:1183 */           break;
/* 1150:     */         case '>': 
/* 1151:1185 */           sel = getSelectorFactory().createChildSelector(sel, simpleSel);
/* 1152:     */         }
/* 1153:     */       }
/* 1154:1189 */       return simpleSel;
/* 1155:     */     }
/* 1156:     */     catch (ParseException e)
/* 1157:     */     {
/* 1158:1194 */       throw toCSSParseException("invalidSimpleSelector", e);
/* 1159:     */     }
/* 1160:     */   }
/* 1161:     */   
/* 1162:     */   public final Condition _class(Condition pred)
/* 1163:     */     throws ParseException
/* 1164:     */   {
/* 1165:     */     try
/* 1166:     */     {
/* 1167:1207 */       jj_consume_token(9);
/* 1168:1208 */       Token t = jj_consume_token(56);
/* 1169:1209 */       Condition c = getConditionFactory().createClassCondition(null, t.image);
/* 1170:1210 */       return pred == null ? c : getConditionFactory().createAndCondition(pred, c);
/* 1171:     */     }
/* 1172:     */     catch (ParseException e)
/* 1173:     */     {
/* 1174:1212 */       throw toCSSParseException("invalidClassSelector", e);
/* 1175:     */     }
/* 1176:     */   }
/* 1177:     */   
/* 1178:     */   public final SimpleSelector elementName()
/* 1179:     */     throws ParseException
/* 1180:     */   {
/* 1181:     */     try
/* 1182:     */     {
/* 1183:1225 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1184:     */       {
/* 1185:     */       case 56: 
/* 1186:1227 */         Token t = jj_consume_token(56);
/* 1187:1228 */         return getSelectorFactory().createElementSelector(null, unescape(t.image));
/* 1188:     */       case 12: 
/* 1189:1231 */         jj_consume_token(12);
/* 1190:1232 */         return getSelectorFactory().createElementSelector(null, null);
/* 1191:     */       }
/* 1192:1235 */       this.jj_la1[60] = this.jj_gen;
/* 1193:1236 */       jj_consume_token(-1);
/* 1194:1237 */       throw new ParseException();
/* 1195:     */     }
/* 1196:     */     catch (ParseException e)
/* 1197:     */     {
/* 1198:1240 */       throw toCSSParseException("invalidElementName", e);
/* 1199:     */     }
/* 1200:     */   }
/* 1201:     */   
/* 1202:     */   public final Condition attrib(Condition pred)
/* 1203:     */     throws ParseException
/* 1204:     */   {
/* 1205:1253 */     String name = null;
/* 1206:1254 */     String value = null;
/* 1207:1255 */     int type = 0;
/* 1208:     */     try
/* 1209:     */     {
/* 1210:1257 */       jj_consume_token(17);
/* 1211:     */       for (;;)
/* 1212:     */       {
/* 1213:1260 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1214:     */         {
/* 1215:     */         case 1: 
/* 1216:     */           break;
/* 1217:     */         default: 
/* 1218:1265 */           this.jj_la1[61] = this.jj_gen;
/* 1219:1266 */           break;
/* 1220:     */         }
/* 1221:1268 */         jj_consume_token(1);
/* 1222:     */       }
/* 1223:1270 */       Token t = jj_consume_token(56);
/* 1224:1271 */       name = unescape(t.image);
/* 1225:     */       for (;;)
/* 1226:     */       {
/* 1227:1274 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1228:     */         {
/* 1229:     */         case 1: 
/* 1230:     */           break;
/* 1231:     */         default: 
/* 1232:1279 */           this.jj_la1[62] = this.jj_gen;
/* 1233:1280 */           break;
/* 1234:     */         }
/* 1235:1282 */         jj_consume_token(1);
/* 1236:     */       }
/* 1237:1284 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1238:     */       {
/* 1239:     */       case 16: 
/* 1240:     */       case 27: 
/* 1241:     */       case 28: 
/* 1242:1288 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1243:     */         {
/* 1244:     */         case 16: 
/* 1245:1290 */           jj_consume_token(16);
/* 1246:1291 */           type = 1;
/* 1247:1292 */           break;
/* 1248:     */         case 27: 
/* 1249:1294 */           jj_consume_token(27);
/* 1250:1295 */           type = 2;
/* 1251:1296 */           break;
/* 1252:     */         case 28: 
/* 1253:1298 */           jj_consume_token(28);
/* 1254:1299 */           type = 3;
/* 1255:1300 */           break;
/* 1256:     */         default: 
/* 1257:1302 */           this.jj_la1[63] = this.jj_gen;
/* 1258:1303 */           jj_consume_token(-1);
/* 1259:1304 */           throw new ParseException();
/* 1260:     */         }
/* 1261:     */         for (;;)
/* 1262:     */         {
/* 1263:1308 */           switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1264:     */           {
/* 1265:     */           case 1: 
/* 1266:     */             break;
/* 1267:     */           default: 
/* 1268:1313 */             this.jj_la1[64] = this.jj_gen;
/* 1269:1314 */             break;
/* 1270:     */           }
/* 1271:1316 */           jj_consume_token(1);
/* 1272:     */         }
/* 1273:1318 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1274:     */         {
/* 1275:     */         case 56: 
/* 1276:1320 */           t = jj_consume_token(56);
/* 1277:1321 */           value = t.image;
/* 1278:1322 */           break;
/* 1279:     */         case 21: 
/* 1280:1324 */           t = jj_consume_token(21);
/* 1281:1325 */           value = unescape(t.image);
/* 1282:1326 */           break;
/* 1283:     */         default: 
/* 1284:1328 */           this.jj_la1[65] = this.jj_gen;
/* 1285:1329 */           jj_consume_token(-1);
/* 1286:1330 */           throw new ParseException();
/* 1287:     */         }
/* 1288:     */         for (;;)
/* 1289:     */         {
/* 1290:1334 */           switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1291:     */           {
/* 1292:     */           case 1: 
/* 1293:     */             break;
/* 1294:     */           default: 
/* 1295:1339 */             this.jj_la1[66] = this.jj_gen;
/* 1296:1340 */             break;
/* 1297:     */           }
/* 1298:1342 */           jj_consume_token(1);
/* 1299:     */         }
/* 1300:     */       }
/* 1301:1346 */       this.jj_la1[67] = this.jj_gen;
/* 1302:     */       
/* 1303:     */ 
/* 1304:1349 */       jj_consume_token(18);
/* 1305:1350 */       Condition c = null;
/* 1306:1351 */       switch (type)
/* 1307:     */       {
/* 1308:     */       case 0: 
/* 1309:1353 */         c = getConditionFactory().createAttributeCondition(name, null, false, null);
/* 1310:1354 */         break;
/* 1311:     */       case 1: 
/* 1312:1356 */         c = getConditionFactory().createAttributeCondition(name, null, false, value);
/* 1313:1357 */         break;
/* 1314:     */       case 2: 
/* 1315:1359 */         c = getConditionFactory().createOneOfAttributeCondition(name, null, false, value);
/* 1316:1360 */         break;
/* 1317:     */       case 3: 
/* 1318:1362 */         c = getConditionFactory().createBeginHyphenAttributeCondition(name, null, false, value);
/* 1319:     */       }
/* 1320:1365 */       return pred == null ? c : getConditionFactory().createAndCondition(pred, c);
/* 1321:     */     }
/* 1322:     */     catch (ParseException e)
/* 1323:     */     {
/* 1324:1367 */       throw toCSSParseException("invalidAttrib", e);
/* 1325:     */     }
/* 1326:     */   }
/* 1327:     */   
/* 1328:     */   public final Condition pseudo(Condition pred)
/* 1329:     */     throws ParseException
/* 1330:     */   {
/* 1331:1381 */     String arg = "";
/* 1332:     */     try
/* 1333:     */     {
/* 1334:1383 */       jj_consume_token(11);
/* 1335:     */       Token t;
/* 1336:     */       Condition c;
/* 1337:1384 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1338:     */       {
/* 1339:     */       case 56: 
/* 1340:1386 */         t = jj_consume_token(56);
/* 1341:     */         
/* 1342:     */ 
/* 1343:1389 */         String s = t.image;
/* 1344:1390 */         c = getConditionFactory().createPseudoClassCondition(null, s);
/* 1345:1391 */         return pred == null ? c : getConditionFactory().createAndCondition(pred, c);
/* 1346:     */       case 55: 
/* 1347:1396 */         t = jj_consume_token(55);
/* 1348:1397 */         String function = unescape(t.image);
/* 1349:     */         for (;;)
/* 1350:     */         {
/* 1351:1400 */           switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1352:     */           {
/* 1353:     */           case 1: 
/* 1354:     */             break;
/* 1355:     */           default: 
/* 1356:1405 */             this.jj_la1[68] = this.jj_gen;
/* 1357:1406 */             break;
/* 1358:     */           }
/* 1359:1408 */           jj_consume_token(1);
/* 1360:     */         }
/* 1361:1410 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1362:     */         {
/* 1363:     */         case 56: 
/* 1364:1412 */           t = jj_consume_token(56);
/* 1365:1413 */           arg = unescape(t.image);
/* 1366:1414 */           break;
/* 1367:     */         default: 
/* 1368:1416 */           this.jj_la1[69] = this.jj_gen;
/* 1369:     */         }
/* 1370:     */         for (;;)
/* 1371:     */         {
/* 1372:1421 */           switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1373:     */           {
/* 1374:     */           case 1: 
/* 1375:     */             break;
/* 1376:     */           default: 
/* 1377:1426 */             this.jj_la1[70] = this.jj_gen;
/* 1378:1427 */             break;
/* 1379:     */           }
/* 1380:1429 */           jj_consume_token(1);
/* 1381:     */         }
/* 1382:1431 */         jj_consume_token(22);
/* 1383:1432 */         if (function.equalsIgnoreCase("lang("))
/* 1384:     */         {
/* 1385:1433 */           c = getConditionFactory().createLangCondition(unescape(arg));
/* 1386:1434 */           return pred == null ? c : getConditionFactory().createAndCondition(pred, c);
/* 1387:     */         }
/* 1388:1438 */         Condition c = getConditionFactory().createPseudoClassCondition(null, function + arg + ")");
/* 1389:1439 */         return pred == null ? c : getConditionFactory().createAndCondition(pred, c);
/* 1390:     */       }
/* 1391:1453 */       this.jj_la1[71] = this.jj_gen;
/* 1392:1454 */       jj_consume_token(-1);
/* 1393:1455 */       throw new ParseException();
/* 1394:     */     }
/* 1395:     */     catch (ParseException e)
/* 1396:     */     {
/* 1397:1458 */       throw toCSSParseException("invalidPseudo", e);
/* 1398:     */     }
/* 1399:     */   }
/* 1400:     */   
/* 1401:     */   public final Condition hash(Condition pred)
/* 1402:     */     throws ParseException
/* 1403:     */   {
/* 1404:     */     try
/* 1405:     */     {
/* 1406:1466 */       Token t = jj_consume_token(20);
/* 1407:1467 */       Condition c = getConditionFactory().createIdCondition(t.image.substring(1));
/* 1408:1468 */       return pred == null ? c : getConditionFactory().createAndCondition(pred, c);
/* 1409:     */     }
/* 1410:     */     catch (ParseException e)
/* 1411:     */     {
/* 1412:1470 */       throw toCSSParseException("invalidHash", e);
/* 1413:     */     }
/* 1414:     */   }
/* 1415:     */   
/* 1416:     */   public final void styleDeclaration()
/* 1417:     */     throws ParseException
/* 1418:     */   {
/* 1419:     */     try
/* 1420:     */     {
/* 1421:1477 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1422:     */       {
/* 1423:     */       case 56: 
/* 1424:1479 */         declaration();
/* 1425:1480 */         break;
/* 1426:     */       default: 
/* 1427:1482 */         this.jj_la1[72] = this.jj_gen;
/* 1428:     */       }
/* 1429:     */       for (;;)
/* 1430:     */       {
/* 1431:1487 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1432:     */         {
/* 1433:     */         case 10: 
/* 1434:     */           break;
/* 1435:     */         default: 
/* 1436:1492 */           this.jj_la1[73] = this.jj_gen;
/* 1437:1493 */           break;
/* 1438:     */         }
/* 1439:1495 */         jj_consume_token(10);
/* 1440:     */         for (;;)
/* 1441:     */         {
/* 1442:1498 */           switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1443:     */           {
/* 1444:     */           case 1: 
/* 1445:     */             break;
/* 1446:     */           default: 
/* 1447:1503 */             this.jj_la1[74] = this.jj_gen;
/* 1448:1504 */             break;
/* 1449:     */           }
/* 1450:1506 */           jj_consume_token(1);
/* 1451:     */         }
/* 1452:1508 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1453:     */         {
/* 1454:     */         case 56: 
/* 1455:1510 */           declaration();
/* 1456:1511 */           break;
/* 1457:     */         default: 
/* 1458:1513 */           this.jj_la1[75] = this.jj_gen;
/* 1459:     */         }
/* 1460:     */       }
/* 1461:     */     }
/* 1462:     */     catch (ParseException ex)
/* 1463:     */     {
/* 1464:1518 */       getErrorHandler().error(toCSSParseException("invalidDeclaration", ex));
/* 1465:1519 */       error_skipdecl();
/* 1466:     */     }
/* 1467:     */   }
/* 1468:     */   
/* 1469:     */   public final void declaration()
/* 1470:     */     throws ParseException
/* 1471:     */   {
/* 1472:1532 */     boolean priority = false;
/* 1473:     */     try
/* 1474:     */     {
/* 1475:1534 */       String p = property();
/* 1476:1535 */       jj_consume_token(11);
/* 1477:     */       for (;;)
/* 1478:     */       {
/* 1479:1538 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1480:     */         {
/* 1481:     */         case 1: 
/* 1482:     */           break;
/* 1483:     */         default: 
/* 1484:1543 */           this.jj_la1[76] = this.jj_gen;
/* 1485:1544 */           break;
/* 1486:     */         }
/* 1487:1546 */         jj_consume_token(1);
/* 1488:     */       }
/* 1489:1548 */       LexicalUnit e = expr();
/* 1490:1549 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1491:     */       {
/* 1492:     */       case 35: 
/* 1493:1551 */         priority = prio();
/* 1494:1552 */         break;
/* 1495:     */       default: 
/* 1496:1554 */         this.jj_la1[77] = this.jj_gen;
/* 1497:     */       }
/* 1498:1557 */       handleProperty(p, e, priority);
/* 1499:     */     }
/* 1500:     */     catch (CSSParseException ex)
/* 1501:     */     {
/* 1502:1559 */       getErrorHandler().error(ex);
/* 1503:1560 */       error_skipdecl();
/* 1504:     */     }
/* 1505:     */     catch (ParseException ex)
/* 1506:     */     {
/* 1507:1562 */       getErrorHandler().error(toCSSParseException("invalidDeclaration", ex));
/* 1508:1563 */       error_skipdecl();
/* 1509:     */     }
/* 1510:     */   }
/* 1511:     */   
/* 1512:     */   public final boolean prio()
/* 1513:     */     throws ParseException
/* 1514:     */   {
/* 1515:1573 */     jj_consume_token(35);
/* 1516:     */     for (;;)
/* 1517:     */     {
/* 1518:1576 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1519:     */       {
/* 1520:     */       case 1: 
/* 1521:     */         break;
/* 1522:     */       default: 
/* 1523:1581 */         this.jj_la1[78] = this.jj_gen;
/* 1524:1582 */         break;
/* 1525:     */       }
/* 1526:1584 */       jj_consume_token(1);
/* 1527:     */     }
/* 1528:1586 */     jj_consume_token(34);
/* 1529:     */     for (;;)
/* 1530:     */     {
/* 1531:1589 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1532:     */       {
/* 1533:     */       case 1: 
/* 1534:     */         break;
/* 1535:     */       default: 
/* 1536:1594 */         this.jj_la1[79] = this.jj_gen;
/* 1537:1595 */         break;
/* 1538:     */       }
/* 1539:1597 */       jj_consume_token(1);
/* 1540:     */     }
/* 1541:1599 */     return true;
/* 1542:     */   }
/* 1543:     */   
/* 1544:     */   public final LexicalUnit expr()
/* 1545:     */     throws ParseException
/* 1546:     */   {
/* 1547:     */     try
/* 1548:     */     {
/* 1549:1614 */       LexicalUnit head = term(null);
/* 1550:1615 */       LexicalUnit body = head;
/* 1551:     */       for (;;)
/* 1552:     */       {
/* 1553:1618 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1554:     */         {
/* 1555:     */         case 8: 
/* 1556:     */         case 13: 
/* 1557:     */         case 14: 
/* 1558:     */         case 15: 
/* 1559:     */         case 20: 
/* 1560:     */         case 21: 
/* 1561:     */         case 24: 
/* 1562:     */         case 36: 
/* 1563:     */         case 37: 
/* 1564:     */         case 38: 
/* 1565:     */         case 39: 
/* 1566:     */         case 40: 
/* 1567:     */         case 41: 
/* 1568:     */         case 42: 
/* 1569:     */         case 43: 
/* 1570:     */         case 44: 
/* 1571:     */         case 45: 
/* 1572:     */         case 46: 
/* 1573:     */         case 47: 
/* 1574:     */         case 48: 
/* 1575:     */         case 49: 
/* 1576:     */         case 50: 
/* 1577:     */         case 51: 
/* 1578:     */         case 52: 
/* 1579:     */         case 53: 
/* 1580:     */         case 54: 
/* 1581:     */         case 55: 
/* 1582:     */         case 56: 
/* 1583:     */           break;
/* 1584:     */         case 9: 
/* 1585:     */         case 10: 
/* 1586:     */         case 11: 
/* 1587:     */         case 12: 
/* 1588:     */         case 16: 
/* 1589:     */         case 17: 
/* 1590:     */         case 18: 
/* 1591:     */         case 19: 
/* 1592:     */         case 22: 
/* 1593:     */         case 23: 
/* 1594:     */         case 25: 
/* 1595:     */         case 26: 
/* 1596:     */         case 27: 
/* 1597:     */         case 28: 
/* 1598:     */         case 29: 
/* 1599:     */         case 30: 
/* 1600:     */         case 31: 
/* 1601:     */         case 32: 
/* 1602:     */         case 33: 
/* 1603:     */         case 34: 
/* 1604:     */         case 35: 
/* 1605:     */         default: 
/* 1606:1650 */           this.jj_la1[80] = this.jj_gen;
/* 1607:1651 */           break;
/* 1608:     */         }
/* 1609:1653 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1610:     */         {
/* 1611:     */         case 8: 
/* 1612:     */         case 13: 
/* 1613:1656 */           body = operator(body);
/* 1614:1657 */           break;
/* 1615:     */         default: 
/* 1616:1659 */           this.jj_la1[81] = this.jj_gen;
/* 1617:     */         }
/* 1618:1662 */         body = term(body);
/* 1619:     */       }
/* 1620:1664 */       return head;
/* 1621:     */     }
/* 1622:     */     catch (ParseException ex)
/* 1623:     */     {
/* 1624:1666 */       throw toCSSParseException("invalidExpr", ex);
/* 1625:     */     }
/* 1626:     */   }
/* 1627:     */   
/* 1628:     */   public final LexicalUnit term(LexicalUnit prev)
/* 1629:     */     throws ParseException
/* 1630:     */   {
/* 1631:1681 */     char op = ' ';
/* 1632:     */     
/* 1633:1683 */     LexicalUnit value = null;
/* 1634:1684 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1635:     */     {
/* 1636:     */     case 14: 
/* 1637:     */     case 15: 
/* 1638:1687 */       op = unaryOperator();
/* 1639:1688 */       break;
/* 1640:     */     default: 
/* 1641:1690 */       this.jj_la1[82] = this.jj_gen;
/* 1642:     */     }
/* 1643:     */     Token t;
/* 1644:1693 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1645:     */     {
/* 1646:     */     case 37: 
/* 1647:     */     case 38: 
/* 1648:     */     case 39: 
/* 1649:     */     case 40: 
/* 1650:     */     case 41: 
/* 1651:     */     case 42: 
/* 1652:     */     case 43: 
/* 1653:     */     case 44: 
/* 1654:     */     case 45: 
/* 1655:     */     case 46: 
/* 1656:     */     case 47: 
/* 1657:     */     case 48: 
/* 1658:     */     case 49: 
/* 1659:     */     case 50: 
/* 1660:     */     case 51: 
/* 1661:     */     case 53: 
/* 1662:     */     case 54: 
/* 1663:     */     case 55: 
/* 1664:1712 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1665:     */       {
/* 1666:     */       case 54: 
/* 1667:1714 */         t = jj_consume_token(54);
/* 1668:     */         try
/* 1669:     */         {
/* 1670:1717 */           value = LexicalUnitImpl.createNumber(prev, intValue(op, t.image));
/* 1671:     */         }
/* 1672:     */         catch (NumberFormatException e)
/* 1673:     */         {
/* 1674:1721 */           value = LexicalUnitImpl.createNumber(prev, floatValue(op, t.image));
/* 1675:     */         }
/* 1676:     */       case 53: 
/* 1677:1725 */         t = jj_consume_token(53);
/* 1678:1726 */         value = LexicalUnitImpl.createPercentage(prev, floatValue(op, t.image));
/* 1679:1727 */         break;
/* 1680:     */       case 39: 
/* 1681:1729 */         t = jj_consume_token(39);
/* 1682:1730 */         value = LexicalUnitImpl.createPixel(prev, floatValue(op, t.image));
/* 1683:1731 */         break;
/* 1684:     */       case 40: 
/* 1685:1733 */         t = jj_consume_token(40);
/* 1686:1734 */         value = LexicalUnitImpl.createCentimeter(prev, floatValue(op, t.image));
/* 1687:1735 */         break;
/* 1688:     */       case 41: 
/* 1689:1737 */         t = jj_consume_token(41);
/* 1690:1738 */         value = LexicalUnitImpl.createMillimeter(prev, floatValue(op, t.image));
/* 1691:1739 */         break;
/* 1692:     */       case 42: 
/* 1693:1741 */         t = jj_consume_token(42);
/* 1694:1742 */         value = LexicalUnitImpl.createInch(prev, floatValue(op, t.image));
/* 1695:1743 */         break;
/* 1696:     */       case 43: 
/* 1697:1745 */         t = jj_consume_token(43);
/* 1698:1746 */         value = LexicalUnitImpl.createPoint(prev, floatValue(op, t.image));
/* 1699:1747 */         break;
/* 1700:     */       case 44: 
/* 1701:1749 */         t = jj_consume_token(44);
/* 1702:1750 */         value = LexicalUnitImpl.createPica(prev, floatValue(op, t.image));
/* 1703:1751 */         break;
/* 1704:     */       case 37: 
/* 1705:1753 */         t = jj_consume_token(37);
/* 1706:1754 */         value = LexicalUnitImpl.createEm(prev, floatValue(op, t.image));
/* 1707:1755 */         break;
/* 1708:     */       case 38: 
/* 1709:1757 */         t = jj_consume_token(38);
/* 1710:1758 */         value = LexicalUnitImpl.createEx(prev, floatValue(op, t.image));
/* 1711:1759 */         break;
/* 1712:     */       case 45: 
/* 1713:1761 */         t = jj_consume_token(45);
/* 1714:1762 */         value = LexicalUnitImpl.createDegree(prev, floatValue(op, t.image));
/* 1715:1763 */         break;
/* 1716:     */       case 46: 
/* 1717:1765 */         t = jj_consume_token(46);
/* 1718:1766 */         value = LexicalUnitImpl.createRadian(prev, floatValue(op, t.image));
/* 1719:1767 */         break;
/* 1720:     */       case 47: 
/* 1721:1769 */         t = jj_consume_token(47);
/* 1722:1770 */         value = LexicalUnitImpl.createGradian(prev, floatValue(op, t.image));
/* 1723:1771 */         break;
/* 1724:     */       case 48: 
/* 1725:1773 */         t = jj_consume_token(48);
/* 1726:1774 */         value = LexicalUnitImpl.createMillisecond(prev, floatValue(op, t.image));
/* 1727:1775 */         break;
/* 1728:     */       case 49: 
/* 1729:1777 */         t = jj_consume_token(49);
/* 1730:1778 */         value = LexicalUnitImpl.createSecond(prev, floatValue(op, t.image));
/* 1731:1779 */         break;
/* 1732:     */       case 50: 
/* 1733:1781 */         t = jj_consume_token(50);
/* 1734:1782 */         value = LexicalUnitImpl.createHertz(prev, floatValue(op, t.image));
/* 1735:1783 */         break;
/* 1736:     */       case 51: 
/* 1737:1785 */         t = jj_consume_token(51);
/* 1738:1786 */         value = LexicalUnitImpl.createKiloHertz(prev, floatValue(op, t.image));
/* 1739:1787 */         break;
/* 1740:     */       case 55: 
/* 1741:1789 */         value = function(prev);
/* 1742:1790 */         break;
/* 1743:     */       case 52: 
/* 1744:     */       default: 
/* 1745:1792 */         this.jj_la1[83] = this.jj_gen;
/* 1746:1793 */         jj_consume_token(-1);
/* 1747:1794 */         throw new ParseException();
/* 1748:     */       }
/* 1749:     */       break;
/* 1750:     */     case 21: 
/* 1751:1798 */       t = jj_consume_token(21);
/* 1752:1799 */       value = new LexicalUnitImpl(prev, (short)36, unescape(t.image));
/* 1753:1800 */       break;
/* 1754:     */     case 56: 
/* 1755:1802 */       t = jj_consume_token(56);
/* 1756:1803 */       value = new LexicalUnitImpl(prev, (short)35, t.image);
/* 1757:1804 */       break;
/* 1758:     */     case 24: 
/* 1759:1806 */       t = jj_consume_token(24);
/* 1760:1807 */       value = new LexicalUnitImpl(prev, (short)24, t.image);
/* 1761:1808 */       break;
/* 1762:     */     case 20: 
/* 1763:1810 */       value = hexcolor(prev);
/* 1764:1811 */       break;
/* 1765:     */     case 52: 
/* 1766:1813 */       t = jj_consume_token(52);
/* 1767:1814 */       int n = getLastNumPos(t.image);
/* 1768:1815 */       value = LexicalUnitImpl.createDimension(prev, floatValue(op, t.image.substring(0, n + 1)), t.image.substring(n + 1));
/* 1769:     */       
/* 1770:     */ 
/* 1771:     */ 
/* 1772:1819 */       break;
/* 1773:     */     case 36: 
/* 1774:1821 */       t = jj_consume_token(36);
/* 1775:1822 */       value = new LexicalUnitImpl(prev, (short)12, t.image);
/* 1776:1823 */       break;
/* 1777:     */     case 22: 
/* 1778:     */     case 23: 
/* 1779:     */     case 25: 
/* 1780:     */     case 26: 
/* 1781:     */     case 27: 
/* 1782:     */     case 28: 
/* 1783:     */     case 29: 
/* 1784:     */     case 30: 
/* 1785:     */     case 31: 
/* 1786:     */     case 32: 
/* 1787:     */     case 33: 
/* 1788:     */     case 34: 
/* 1789:     */     case 35: 
/* 1790:     */     default: 
/* 1791:1825 */       this.jj_la1[84] = this.jj_gen;
/* 1792:1826 */       jj_consume_token(-1);
/* 1793:1827 */       throw new ParseException();
/* 1794:     */     }
/* 1795:     */     for (;;)
/* 1796:     */     {
/* 1797:1831 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1798:     */       {
/* 1799:     */       case 1: 
/* 1800:     */         break;
/* 1801:     */       default: 
/* 1802:1836 */         this.jj_la1[85] = this.jj_gen;
/* 1803:1837 */         break;
/* 1804:     */       }
/* 1805:1839 */       jj_consume_token(1);
/* 1806:     */     }
/* 1807:1841 */     if ((value instanceof LexicalUnitImpl)) {
/* 1808:1843 */       ((LexicalUnitImpl)value).setLocator(getLocator());
/* 1809:     */     }
/* 1810:1845 */     return value;
/* 1811:     */   }
/* 1812:     */   
/* 1813:     */   public final LexicalUnit function(LexicalUnit prev)
/* 1814:     */     throws ParseException
/* 1815:     */   {
/* 1816:1857 */     Token t = jj_consume_token(55);
/* 1817:     */     for (;;)
/* 1818:     */     {
/* 1819:1860 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1820:     */       {
/* 1821:     */       case 1: 
/* 1822:     */         break;
/* 1823:     */       default: 
/* 1824:1865 */         this.jj_la1[86] = this.jj_gen;
/* 1825:1866 */         break;
/* 1826:     */       }
/* 1827:1868 */       jj_consume_token(1);
/* 1828:     */     }
/* 1829:1870 */     LexicalUnit params = expr();
/* 1830:1871 */     jj_consume_token(22);
/* 1831:1872 */     return functionInternal(prev, t, params);
/* 1832:     */   }
/* 1833:     */   
/* 1834:     */   public final LexicalUnit hexcolor(LexicalUnit prev)
/* 1835:     */     throws ParseException
/* 1836:     */   {
/* 1837:1904 */     Token t = jj_consume_token(20);
/* 1838:1905 */     return hexcolorInternal(prev, t);
/* 1839:     */   }
/* 1840:     */   
/* 1841:     */   void skipSelector()
/* 1842:     */     throws ParseException
/* 1843:     */   {
/* 1844:1910 */     Token t = getToken(1);
/* 1845:1911 */     while ((t.kind != 8) && (t.kind != 10) && (t.kind != 6) && (t.kind != 0))
/* 1846:     */     {
/* 1847:1912 */       getNextToken();
/* 1848:1913 */       t = getToken(1);
/* 1849:     */     }
/* 1850:     */   }
/* 1851:     */   
/* 1852:     */   String skip()
/* 1853:     */     throws ParseException
/* 1854:     */   {
/* 1855:1918 */     StringBuffer sb = new StringBuffer();
/* 1856:1919 */     int nesting = 0;
/* 1857:1920 */     Token t = getToken(0);
/* 1858:1921 */     if (t.image != null) {
/* 1859:1922 */       sb.append(t.image);
/* 1860:     */     }
/* 1861:     */     do
/* 1862:     */     {
/* 1863:1925 */       t = getNextToken();
/* 1864:1926 */       if (t.kind == 0) {
/* 1865:     */         break;
/* 1866:     */       }
/* 1867:1928 */       sb.append(t.image);
/* 1868:1929 */       if (t.kind == 6) {
/* 1869:1930 */         nesting++;
/* 1870:1931 */       } else if (t.kind == 7) {
/* 1871:1932 */         nesting--;
/* 1872:     */       } else {
/* 1873:1933 */         if ((t.kind == 10) && (nesting <= 0)) {
/* 1874:     */           break;
/* 1875:     */         }
/* 1876:     */       }
/* 1877:1936 */     } while ((t.kind != 7) || (nesting > 0));
/* 1878:1938 */     return sb.toString();
/* 1879:     */   }
/* 1880:     */   
/* 1881:     */   void error_skipblock()
/* 1882:     */     throws ParseException
/* 1883:     */   {
/* 1884:1943 */     int nesting = 0;
/* 1885:     */     Token t;
/* 1886:     */     do
/* 1887:     */     {
/* 1888:1946 */       t = getNextToken();
/* 1889:1947 */       if (t.kind == 6) {
/* 1890:1949 */         nesting++;
/* 1891:1951 */       } else if (t.kind == 7) {
/* 1892:1953 */         nesting--;
/* 1893:     */       } else {
/* 1894:1955 */         if (t.kind == 0) {
/* 1895:     */           break;
/* 1896:     */         }
/* 1897:     */       }
/* 1898:1960 */     } while ((t.kind != 7) || (nesting > 0));
/* 1899:     */   }
/* 1900:     */   
/* 1901:     */   void error_skipdecl()
/* 1902:     */     throws ParseException
/* 1903:     */   {
/* 1904:1964 */     int nesting = 0;
/* 1905:1965 */     Token t = getToken(1);
/* 1906:1966 */     if (t.kind == 6)
/* 1907:     */     {
/* 1908:1968 */       error_skipblock();
/* 1909:     */     }
/* 1910:     */     else
/* 1911:     */     {
/* 1912:1972 */       Token oldToken = t;
/* 1913:1973 */       while ((t.kind != 10) && (t.kind != 7) && (t.kind != 0))
/* 1914:     */       {
/* 1915:1975 */         oldToken = t;
/* 1916:1976 */         t = getNextToken();
/* 1917:     */       }
/* 1918:1978 */       if (t.kind == 7) {
/* 1919:1980 */         this.token = oldToken;
/* 1920:     */       }
/* 1921:     */     }
/* 1922:     */   }
/* 1923:     */   
/* 1924:     */   void error_skipAtRule()
/* 1925:     */     throws ParseException
/* 1926:     */   {
/* 1927:1986 */     Token t = null;
/* 1928:     */     do
/* 1929:     */     {
/* 1930:1989 */       t = getNextToken();
/* 1931:1991 */     } while (t.kind != 10);
/* 1932:     */   }
/* 1933:     */   
/* 1934:     */   private final boolean jj_2_1(int xla)
/* 1935:     */   {
/* 1936:1995 */     this.jj_la = xla;this.jj_lastpos = (this.jj_scanpos = this.token);
/* 1937:     */     try
/* 1938:     */     {
/* 1939:1996 */       return !jj_3_1();
/* 1940:     */     }
/* 1941:     */     catch (LookaheadSuccess ls)
/* 1942:     */     {
/* 1943:1997 */       return true;
/* 1944:     */     }
/* 1945:     */     finally
/* 1946:     */     {
/* 1947:1998 */       jj_save(0, xla);
/* 1948:     */     }
/* 1949:     */   }
/* 1950:     */   
/* 1951:     */   private final boolean jj_2_2(int xla)
/* 1952:     */   {
/* 1953:2002 */     this.jj_la = xla;this.jj_lastpos = (this.jj_scanpos = this.token);
/* 1954:     */     try
/* 1955:     */     {
/* 1956:2003 */       return !jj_3_2();
/* 1957:     */     }
/* 1958:     */     catch (LookaheadSuccess ls)
/* 1959:     */     {
/* 1960:2004 */       return true;
/* 1961:     */     }
/* 1962:     */     finally
/* 1963:     */     {
/* 1964:2005 */       jj_save(1, xla);
/* 1965:     */     }
/* 1966:     */   }
/* 1967:     */   
/* 1968:     */   private final boolean jj_3_1()
/* 1969:     */   {
/* 1970:2009 */     if (jj_3R_51()) {
/* 1971:2009 */       return true;
/* 1972:     */     }
/* 1973:2010 */     return false;
/* 1974:     */   }
/* 1975:     */   
/* 1976:     */   private final boolean jj_3R_53()
/* 1977:     */   {
/* 1978:2015 */     Token xsp = this.jj_scanpos;
/* 1979:2016 */     if (jj_3R_57())
/* 1980:     */     {
/* 1981:2017 */       this.jj_scanpos = xsp;
/* 1982:2018 */       if (jj_3R_58()) {
/* 1983:2018 */         return true;
/* 1984:     */       }
/* 1985:     */     }
/* 1986:2020 */     return false;
/* 1987:     */   }
/* 1988:     */   
/* 1989:     */   private final boolean jj_3R_65()
/* 1990:     */   {
/* 1991:2024 */     if (jj_scan_token(12)) {
/* 1992:2024 */       return true;
/* 1993:     */     }
/* 1994:2025 */     return false;
/* 1995:     */   }
/* 1996:     */   
/* 1997:     */   private final boolean jj_3R_63()
/* 1998:     */   {
/* 1999:2029 */     if (jj_scan_token(19)) {
/* 2000:2029 */       return true;
/* 2001:     */     }
/* 2002:2030 */     return false;
/* 2003:     */   }
/* 2004:     */   
/* 2005:     */   private final boolean jj_3R_64()
/* 2006:     */   {
/* 2007:2034 */     if (jj_scan_token(56)) {
/* 2008:2034 */       return true;
/* 2009:     */     }
/* 2010:2035 */     return false;
/* 2011:     */   }
/* 2012:     */   
/* 2013:     */   private final boolean jj_3R_70()
/* 2014:     */   {
/* 2015:2039 */     if (jj_scan_token(20)) {
/* 2016:2039 */       return true;
/* 2017:     */     }
/* 2018:2040 */     return false;
/* 2019:     */   }
/* 2020:     */   
/* 2021:     */   private final boolean jj_3R_60()
/* 2022:     */   {
/* 2023:2045 */     Token xsp = this.jj_scanpos;
/* 2024:2046 */     if (jj_3R_64())
/* 2025:     */     {
/* 2026:2047 */       this.jj_scanpos = xsp;
/* 2027:2048 */       if (jj_3R_65()) {
/* 2028:2048 */         return true;
/* 2029:     */       }
/* 2030:     */     }
/* 2031:2050 */     return false;
/* 2032:     */   }
/* 2033:     */   
/* 2034:     */   private final boolean jj_3_2()
/* 2035:     */   {
/* 2036:2054 */     if (jj_3R_52()) {
/* 2037:2054 */       return true;
/* 2038:     */     }
/* 2039:2055 */     if (jj_3R_53()) {
/* 2040:2055 */       return true;
/* 2041:     */     }
/* 2042:2056 */     return false;
/* 2043:     */   }
/* 2044:     */   
/* 2045:     */   private final boolean jj_3R_73()
/* 2046:     */   {
/* 2047:2060 */     if (jj_scan_token(11)) {
/* 2048:2060 */       return true;
/* 2049:     */     }
/* 2050:2061 */     return false;
/* 2051:     */   }
/* 2052:     */   
/* 2053:     */   private final boolean jj_3R_69()
/* 2054:     */   {
/* 2055:2065 */     if (jj_3R_73()) {
/* 2056:2065 */       return true;
/* 2057:     */     }
/* 2058:2066 */     return false;
/* 2059:     */   }
/* 2060:     */   
/* 2061:     */   private final boolean jj_3R_51()
/* 2062:     */   {
/* 2063:2070 */     if (jj_scan_token(11)) {
/* 2064:2070 */       return true;
/* 2065:     */     }
/* 2066:2071 */     if (jj_scan_token(56)) {
/* 2067:2071 */       return true;
/* 2068:     */     }
/* 2069:2072 */     return false;
/* 2070:     */   }
/* 2071:     */   
/* 2072:     */   private final boolean jj_3R_68()
/* 2073:     */   {
/* 2074:2076 */     if (jj_3R_72()) {
/* 2075:2076 */       return true;
/* 2076:     */     }
/* 2077:2077 */     return false;
/* 2078:     */   }
/* 2079:     */   
/* 2080:     */   private final boolean jj_3R_67()
/* 2081:     */   {
/* 2082:2081 */     if (jj_3R_71()) {
/* 2083:2081 */       return true;
/* 2084:     */     }
/* 2085:2082 */     return false;
/* 2086:     */   }
/* 2087:     */   
/* 2088:     */   private final boolean jj_3R_66()
/* 2089:     */   {
/* 2090:2086 */     if (jj_3R_70()) {
/* 2091:2086 */       return true;
/* 2092:     */     }
/* 2093:2087 */     return false;
/* 2094:     */   }
/* 2095:     */   
/* 2096:     */   private final boolean jj_3R_61()
/* 2097:     */   {
/* 2098:2092 */     Token xsp = this.jj_scanpos;
/* 2099:2093 */     if (jj_3R_66())
/* 2100:     */     {
/* 2101:2094 */       this.jj_scanpos = xsp;
/* 2102:2095 */       if (jj_3R_67())
/* 2103:     */       {
/* 2104:2096 */         this.jj_scanpos = xsp;
/* 2105:2097 */         if (jj_3R_68())
/* 2106:     */         {
/* 2107:2098 */           this.jj_scanpos = xsp;
/* 2108:2099 */           if (jj_3R_69()) {
/* 2109:2099 */             return true;
/* 2110:     */           }
/* 2111:     */         }
/* 2112:     */       }
/* 2113:     */     }
/* 2114:2103 */     return false;
/* 2115:     */   }
/* 2116:     */   
/* 2117:     */   private final boolean jj_3R_62()
/* 2118:     */   {
/* 2119:2107 */     if (jj_scan_token(14)) {
/* 2120:2107 */       return true;
/* 2121:     */     }
/* 2122:2108 */     return false;
/* 2123:     */   }
/* 2124:     */   
/* 2125:     */   private final boolean jj_3R_59()
/* 2126:     */   {
/* 2127:2113 */     Token xsp = this.jj_scanpos;
/* 2128:2114 */     if (jj_3R_62())
/* 2129:     */     {
/* 2130:2115 */       this.jj_scanpos = xsp;
/* 2131:2116 */       if (jj_3R_63()) {
/* 2132:2116 */         return true;
/* 2133:     */       }
/* 2134:     */     }
/* 2135:2118 */     return false;
/* 2136:     */   }
/* 2137:     */   
/* 2138:     */   private final boolean jj_3R_58()
/* 2139:     */   {
/* 2140:2123 */     if (jj_3R_61()) {
/* 2141:2123 */       return true;
/* 2142:     */     }
/* 2143:     */     Token xsp;
/* 2144:     */     do
/* 2145:     */     {
/* 2146:2125 */       xsp = this.jj_scanpos;
/* 2147:2126 */     } while (!jj_3R_61());
/* 2148:2126 */     this.jj_scanpos = xsp;
/* 2149:     */     
/* 2150:2128 */     return false;
/* 2151:     */   }
/* 2152:     */   
/* 2153:     */   private final boolean jj_3R_72()
/* 2154:     */   {
/* 2155:2132 */     if (jj_scan_token(17)) {
/* 2156:2132 */       return true;
/* 2157:     */     }
/* 2158:2133 */     return false;
/* 2159:     */   }
/* 2160:     */   
/* 2161:     */   private final boolean jj_3R_56()
/* 2162:     */   {
/* 2163:2137 */     if (jj_scan_token(1)) {
/* 2164:2137 */       return true;
/* 2165:     */     }
/* 2166:2139 */     Token xsp = this.jj_scanpos;
/* 2167:2140 */     if (jj_3R_59()) {
/* 2168:2140 */       this.jj_scanpos = xsp;
/* 2169:     */     }
/* 2170:2141 */     return false;
/* 2171:     */   }
/* 2172:     */   
/* 2173:     */   private final boolean jj_3R_55()
/* 2174:     */   {
/* 2175:2145 */     if (jj_scan_token(19)) {
/* 2176:2145 */       return true;
/* 2177:     */     }
/* 2178:     */     Token xsp;
/* 2179:     */     do
/* 2180:     */     {
/* 2181:2148 */       xsp = this.jj_scanpos;
/* 2182:2149 */     } while (!jj_scan_token(1));
/* 2183:2149 */     this.jj_scanpos = xsp;
/* 2184:     */     
/* 2185:2151 */     return false;
/* 2186:     */   }
/* 2187:     */   
/* 2188:     */   private final boolean jj_3R_71()
/* 2189:     */   {
/* 2190:2155 */     if (jj_scan_token(9)) {
/* 2191:2155 */       return true;
/* 2192:     */     }
/* 2193:2156 */     return false;
/* 2194:     */   }
/* 2195:     */   
/* 2196:     */   private final boolean jj_3R_54()
/* 2197:     */   {
/* 2198:2160 */     if (jj_scan_token(14)) {
/* 2199:2160 */       return true;
/* 2200:     */     }
/* 2201:     */     Token xsp;
/* 2202:     */     do
/* 2203:     */     {
/* 2204:2163 */       xsp = this.jj_scanpos;
/* 2205:2164 */     } while (!jj_scan_token(1));
/* 2206:2164 */     this.jj_scanpos = xsp;
/* 2207:     */     
/* 2208:2166 */     return false;
/* 2209:     */   }
/* 2210:     */   
/* 2211:     */   private final boolean jj_3R_52()
/* 2212:     */   {
/* 2213:2171 */     Token xsp = this.jj_scanpos;
/* 2214:2172 */     if (jj_3R_54())
/* 2215:     */     {
/* 2216:2173 */       this.jj_scanpos = xsp;
/* 2217:2174 */       if (jj_3R_55())
/* 2218:     */       {
/* 2219:2175 */         this.jj_scanpos = xsp;
/* 2220:2176 */         if (jj_3R_56()) {
/* 2221:2176 */           return true;
/* 2222:     */         }
/* 2223:     */       }
/* 2224:     */     }
/* 2225:2179 */     return false;
/* 2226:     */   }
/* 2227:     */   
/* 2228:     */   private final boolean jj_3R_57()
/* 2229:     */   {
/* 2230:2183 */     if (jj_3R_60()) {
/* 2231:2183 */       return true;
/* 2232:     */     }
/* 2233:2184 */     return false;
/* 2234:     */   }
/* 2235:     */   
/* 2236:2192 */   public boolean lookingAhead = false;
/* 2237:     */   private boolean jj_semLA;
/* 2238:     */   private int jj_gen;
/* 2239:2195 */   private final int[] jj_la1 = new int[87];
/* 2240:     */   private static int[] jj_la1_0;
/* 2241:     */   private static int[] jj_la1_1;
/* 2242:     */   private static int[] jj_la1_2;
/* 2243:     */   
/* 2244:     */   static
/* 2245:     */   {
/* 2246:2200 */     jj_la1_0();
/* 2247:2201 */     jj_la1_1();
/* 2248:2202 */     jj_la1_2();
/* 2249:     */   }
/* 2250:     */   
/* 2251:     */   private static void jj_la1_0()
/* 2252:     */   {
/* 2253:2205 */     jj_la1_0 = new int[] { 0, 100663298, 100663298, 536870912, 100663298, 100663298, -1072555520, -1072555520, 100663298, 100663298, -535684608, -535684608, 100663298, 100663298, -535684608, 2, 18874368, 2, 0, 2, 18874368, 2, 0, 2, 2, 1074928128, 256, 2, 1074928128, 2, 1074928128, 1074928128, 2, 2, 2, 2, 0, 1024, 2, 0, 2, 2, 8448, 2, 2, 540672, 2, 540672, 540674, 49152, 2, 2, 256, 2, 2, 1182208, 1182208, 1182208, 1182208, 1186304, 4096, 2, 2, 402718720, 2, 2097152, 2, 402718720, 2, 0, 2, 0, 0, 1024, 2, 0, 2, 0, 2, 2, 19980544, 8448, 49152, 0, 19922944, 2, 2 };
/* 2254:     */   }
/* 2255:     */   
/* 2256:     */   private static void jj_la1_1()
/* 2257:     */   {
/* 2258:2208 */     jj_la1_1 = new int[] { 1, 0, 0, 0, 0, 0, 16777216, 16777216, 0, 0, 16777216, 16777216, 0, 0, 16777219, 0, 0, 0, 16777216, 0, 0, 0, 16777216, 0, 0, 16777218, 0, 0, 16777218, 0, 16777218, 16777218, 0, 0, 0, 0, 16777216, 0, 0, 16777216, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 16777216, 16777216, 0, 0, 0, 0, 16777216, 0, 0, 0, 16777216, 0, 25165824, 16777216, 0, 0, 16777216, 0, 8, 0, 0, 33554416, 0, 0, 15728608, 33554416, 0, 0 };
/* 2259:     */   }
/* 2260:     */   
/* 2261:     */   private static void jj_la1_2()
/* 2262:     */   {
/* 2263:2211 */     jj_la1_2 = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
/* 2264:     */   }
/* 2265:     */   
/* 2266:2213 */   private final JJCalls[] jj_2_rtns = new JJCalls[2];
/* 2267:2214 */   private boolean jj_rescan = false;
/* 2268:2215 */   private int jj_gc = 0;
/* 2269:     */   
/* 2270:     */   public SACParserCSS21(CharStream stream)
/* 2271:     */   {
/* 2272:2218 */     this.token_source = new SACParserCSS21TokenManager(stream);
/* 2273:2219 */     this.token = new Token();
/* 2274:2220 */     this.jj_ntk = -1;
/* 2275:2221 */     this.jj_gen = 0;
/* 2276:2222 */     for (int i = 0; i < 87; i++) {
/* 2277:2222 */       this.jj_la1[i] = -1;
/* 2278:     */     }
/* 2279:2223 */     for (int i = 0; i < this.jj_2_rtns.length; i++) {
/* 2280:2223 */       this.jj_2_rtns[i] = new JJCalls();
/* 2281:     */     }
/* 2282:     */   }
/* 2283:     */   
/* 2284:     */   public void ReInit(CharStream stream)
/* 2285:     */   {
/* 2286:2227 */     this.token_source.ReInit(stream);
/* 2287:2228 */     this.token = new Token();
/* 2288:2229 */     this.jj_ntk = -1;
/* 2289:2230 */     this.jj_gen = 0;
/* 2290:2231 */     for (int i = 0; i < 87; i++) {
/* 2291:2231 */       this.jj_la1[i] = -1;
/* 2292:     */     }
/* 2293:2232 */     for (int i = 0; i < this.jj_2_rtns.length; i++) {
/* 2294:2232 */       this.jj_2_rtns[i] = new JJCalls();
/* 2295:     */     }
/* 2296:     */   }
/* 2297:     */   
/* 2298:     */   public SACParserCSS21(SACParserCSS21TokenManager tm)
/* 2299:     */   {
/* 2300:2236 */     this.token_source = tm;
/* 2301:2237 */     this.token = new Token();
/* 2302:2238 */     this.jj_ntk = -1;
/* 2303:2239 */     this.jj_gen = 0;
/* 2304:2240 */     for (int i = 0; i < 87; i++) {
/* 2305:2240 */       this.jj_la1[i] = -1;
/* 2306:     */     }
/* 2307:2241 */     for (int i = 0; i < this.jj_2_rtns.length; i++) {
/* 2308:2241 */       this.jj_2_rtns[i] = new JJCalls();
/* 2309:     */     }
/* 2310:     */   }
/* 2311:     */   
/* 2312:     */   public void ReInit(SACParserCSS21TokenManager tm)
/* 2313:     */   {
/* 2314:2245 */     this.token_source = tm;
/* 2315:2246 */     this.token = new Token();
/* 2316:2247 */     this.jj_ntk = -1;
/* 2317:2248 */     this.jj_gen = 0;
/* 2318:2249 */     for (int i = 0; i < 87; i++) {
/* 2319:2249 */       this.jj_la1[i] = -1;
/* 2320:     */     }
/* 2321:2250 */     for (int i = 0; i < this.jj_2_rtns.length; i++) {
/* 2322:2250 */       this.jj_2_rtns[i] = new JJCalls();
/* 2323:     */     }
/* 2324:     */   }
/* 2325:     */   
/* 2326:     */   private final Token jj_consume_token(int kind)
/* 2327:     */     throws ParseException
/* 2328:     */   {
/* 2329:     */     Token oldToken;
/* 2330:2255 */     if ((oldToken = this.token).next != null) {
/* 2331:2255 */       this.token = this.token.next;
/* 2332:     */     } else {
/* 2333:2256 */       this.token = (this.token.next = this.token_source.getNextToken());
/* 2334:     */     }
/* 2335:2257 */     this.jj_ntk = -1;
/* 2336:2258 */     if (this.token.kind == kind)
/* 2337:     */     {
/* 2338:2259 */       this.jj_gen += 1;
/* 2339:2260 */       if (++this.jj_gc > 100)
/* 2340:     */       {
/* 2341:2261 */         this.jj_gc = 0;
/* 2342:2262 */         for (int i = 0; i < this.jj_2_rtns.length; i++)
/* 2343:     */         {
/* 2344:2263 */           JJCalls c = this.jj_2_rtns[i];
/* 2345:2264 */           while (c != null)
/* 2346:     */           {
/* 2347:2265 */             if (c.gen < this.jj_gen) {
/* 2348:2265 */               c.first = null;
/* 2349:     */             }
/* 2350:2266 */             c = c.next;
/* 2351:     */           }
/* 2352:     */         }
/* 2353:     */       }
/* 2354:2270 */       return this.token;
/* 2355:     */     }
/* 2356:2272 */     this.token = oldToken;
/* 2357:2273 */     this.jj_kind = kind;
/* 2358:2274 */     throw generateParseException();
/* 2359:     */   }
/* 2360:     */   
/* 2361:2278 */   private final LookaheadSuccess jj_ls = new LookaheadSuccess(null);
/* 2362:     */   
/* 2363:     */   private final boolean jj_scan_token(int kind)
/* 2364:     */   {
/* 2365:2280 */     if (this.jj_scanpos == this.jj_lastpos)
/* 2366:     */     {
/* 2367:2281 */       this.jj_la -= 1;
/* 2368:2282 */       if (this.jj_scanpos.next == null) {
/* 2369:2283 */         this.jj_lastpos = (this.jj_scanpos = this.jj_scanpos.next = this.token_source.getNextToken());
/* 2370:     */       } else {
/* 2371:2285 */         this.jj_lastpos = (this.jj_scanpos = this.jj_scanpos.next);
/* 2372:     */       }
/* 2373:     */     }
/* 2374:     */     else
/* 2375:     */     {
/* 2376:2288 */       this.jj_scanpos = this.jj_scanpos.next;
/* 2377:     */     }
/* 2378:2290 */     if (this.jj_rescan)
/* 2379:     */     {
/* 2380:2291 */       int i = 0;
/* 2381:2291 */       for (Token tok = this.token; (tok != null) && (tok != this.jj_scanpos); tok = tok.next) {
/* 2382:2292 */         i++;
/* 2383:     */       }
/* 2384:2293 */       if (tok != null) {
/* 2385:2293 */         jj_add_error_token(kind, i);
/* 2386:     */       }
/* 2387:     */     }
/* 2388:2295 */     if (this.jj_scanpos.kind != kind) {
/* 2389:2295 */       return true;
/* 2390:     */     }
/* 2391:2296 */     if ((this.jj_la == 0) && (this.jj_scanpos == this.jj_lastpos)) {
/* 2392:2296 */       throw this.jj_ls;
/* 2393:     */     }
/* 2394:2297 */     return false;
/* 2395:     */   }
/* 2396:     */   
/* 2397:     */   public final Token getNextToken()
/* 2398:     */   {
/* 2399:2301 */     if (this.token.next != null) {
/* 2400:2301 */       this.token = this.token.next;
/* 2401:     */     } else {
/* 2402:2302 */       this.token = (this.token.next = this.token_source.getNextToken());
/* 2403:     */     }
/* 2404:2303 */     this.jj_ntk = -1;
/* 2405:2304 */     this.jj_gen += 1;
/* 2406:2305 */     return this.token;
/* 2407:     */   }
/* 2408:     */   
/* 2409:     */   public final Token getToken(int index)
/* 2410:     */   {
/* 2411:2309 */     Token t = this.lookingAhead ? this.jj_scanpos : this.token;
/* 2412:2310 */     for (int i = 0; i < index; i++) {
/* 2413:2311 */       if (t.next != null) {
/* 2414:2311 */         t = t.next;
/* 2415:     */       } else {
/* 2416:2312 */         t = t.next = this.token_source.getNextToken();
/* 2417:     */       }
/* 2418:     */     }
/* 2419:2314 */     return t;
/* 2420:     */   }
/* 2421:     */   
/* 2422:     */   private final int jj_ntk()
/* 2423:     */   {
/* 2424:2318 */     if ((this.jj_nt = this.token.next) == null) {
/* 2425:2319 */       return this.jj_ntk = (this.token.next = this.token_source.getNextToken()).kind;
/* 2426:     */     }
/* 2427:2321 */     return this.jj_ntk = this.jj_nt.kind;
/* 2428:     */   }
/* 2429:     */   
/* 2430:2324 */   private Vector jj_expentries = new Vector();
/* 2431:     */   private int[] jj_expentry;
/* 2432:2326 */   private int jj_kind = -1;
/* 2433:2327 */   private int[] jj_lasttokens = new int[100];
/* 2434:     */   private int jj_endpos;
/* 2435:     */   
/* 2436:     */   private void jj_add_error_token(int kind, int pos)
/* 2437:     */   {
/* 2438:2331 */     if (pos >= 100) {
/* 2439:2331 */       return;
/* 2440:     */     }
/* 2441:2332 */     if (pos == this.jj_endpos + 1)
/* 2442:     */     {
/* 2443:2333 */       this.jj_lasttokens[(this.jj_endpos++)] = kind;
/* 2444:     */     }
/* 2445:2334 */     else if (this.jj_endpos != 0)
/* 2446:     */     {
/* 2447:2335 */       this.jj_expentry = new int[this.jj_endpos];
/* 2448:2336 */       for (int i = 0; i < this.jj_endpos; i++) {
/* 2449:2337 */         this.jj_expentry[i] = this.jj_lasttokens[i];
/* 2450:     */       }
/* 2451:2339 */       boolean exists = false;
/* 2452:2340 */       for (Enumeration e = this.jj_expentries.elements(); e.hasMoreElements();)
/* 2453:     */       {
/* 2454:2341 */         int[] oldentry = (int[])e.nextElement();
/* 2455:2342 */         if (oldentry.length == this.jj_expentry.length)
/* 2456:     */         {
/* 2457:2343 */           exists = true;
/* 2458:2344 */           for (int i = 0; i < this.jj_expentry.length; i++) {
/* 2459:2345 */             if (oldentry[i] != this.jj_expentry[i])
/* 2460:     */             {
/* 2461:2346 */               exists = false;
/* 2462:2347 */               break;
/* 2463:     */             }
/* 2464:     */           }
/* 2465:2350 */           if (exists) {
/* 2466:     */             break;
/* 2467:     */           }
/* 2468:     */         }
/* 2469:     */       }
/* 2470:2353 */       if (!exists) {
/* 2471:2353 */         this.jj_expentries.addElement(this.jj_expentry);
/* 2472:     */       }
/* 2473:2354 */       if (pos != 0)
/* 2474:     */       {
/* 2475:2354 */         int tmp205_204 = pos;this.jj_endpos = tmp205_204;this.jj_lasttokens[(tmp205_204 - 1)] = kind;
/* 2476:     */       }
/* 2477:     */     }
/* 2478:     */   }
/* 2479:     */   
/* 2480:     */   public ParseException generateParseException()
/* 2481:     */   {
/* 2482:2359 */     this.jj_expentries.removeAllElements();
/* 2483:2360 */     boolean[] la1tokens = new boolean[92];
/* 2484:2361 */     for (int i = 0; i < 92; i++) {
/* 2485:2362 */       la1tokens[i] = false;
/* 2486:     */     }
/* 2487:2364 */     if (this.jj_kind >= 0)
/* 2488:     */     {
/* 2489:2365 */       la1tokens[this.jj_kind] = true;
/* 2490:2366 */       this.jj_kind = -1;
/* 2491:     */     }
/* 2492:2368 */     for (int i = 0; i < 87; i++) {
/* 2493:2369 */       if (this.jj_la1[i] == this.jj_gen) {
/* 2494:2370 */         for (int j = 0; j < 32; j++)
/* 2495:     */         {
/* 2496:2371 */           if ((jj_la1_0[i] & 1 << j) != 0) {
/* 2497:2372 */             la1tokens[j] = true;
/* 2498:     */           }
/* 2499:2374 */           if ((jj_la1_1[i] & 1 << j) != 0) {
/* 2500:2375 */             la1tokens[(32 + j)] = true;
/* 2501:     */           }
/* 2502:2377 */           if ((jj_la1_2[i] & 1 << j) != 0) {
/* 2503:2378 */             la1tokens[(64 + j)] = true;
/* 2504:     */           }
/* 2505:     */         }
/* 2506:     */       }
/* 2507:     */     }
/* 2508:2383 */     for (int i = 0; i < 92; i++) {
/* 2509:2384 */       if (la1tokens[i] != 0)
/* 2510:     */       {
/* 2511:2385 */         this.jj_expentry = new int[1];
/* 2512:2386 */         this.jj_expentry[0] = i;
/* 2513:2387 */         this.jj_expentries.addElement(this.jj_expentry);
/* 2514:     */       }
/* 2515:     */     }
/* 2516:2390 */     this.jj_endpos = 0;
/* 2517:2391 */     jj_rescan_token();
/* 2518:2392 */     jj_add_error_token(0, 0);
/* 2519:2393 */     int[][] exptokseq = new int[this.jj_expentries.size()][];
/* 2520:2394 */     for (int i = 0; i < this.jj_expentries.size(); i++) {
/* 2521:2395 */       exptokseq[i] = ((int[])(int[])this.jj_expentries.elementAt(i));
/* 2522:     */     }
/* 2523:2397 */     return new ParseException(this.token, exptokseq, tokenImage);
/* 2524:     */   }
/* 2525:     */   
/* 2526:     */   private final void jj_rescan_token()
/* 2527:     */   {
/* 2528:2407 */     this.jj_rescan = true;
/* 2529:2408 */     for (int i = 0; i < 2; i++) {
/* 2530:     */       try
/* 2531:     */       {
/* 2532:2410 */         JJCalls p = this.jj_2_rtns[i];
/* 2533:     */         do
/* 2534:     */         {
/* 2535:2412 */           if (p.gen > this.jj_gen)
/* 2536:     */           {
/* 2537:2413 */             this.jj_la = p.arg;this.jj_lastpos = (this.jj_scanpos = p.first);
/* 2538:2414 */             switch (i)
/* 2539:     */             {
/* 2540:     */             case 0: 
/* 2541:2415 */               jj_3_1(); break;
/* 2542:     */             case 1: 
/* 2543:2416 */               jj_3_2();
/* 2544:     */             }
/* 2545:     */           }
/* 2546:2419 */           p = p.next;
/* 2547:2420 */         } while (p != null);
/* 2548:     */       }
/* 2549:     */       catch (LookaheadSuccess ls) {}
/* 2550:     */     }
/* 2551:2423 */     this.jj_rescan = false;
/* 2552:     */   }
/* 2553:     */   
/* 2554:     */   private final void jj_save(int index, int xla)
/* 2555:     */   {
/* 2556:2427 */     JJCalls p = this.jj_2_rtns[index];
/* 2557:2428 */     while (p.gen > this.jj_gen)
/* 2558:     */     {
/* 2559:2429 */       if (p.next == null)
/* 2560:     */       {
/* 2561:2429 */         p = p.next = new JJCalls(); break;
/* 2562:     */       }
/* 2563:2430 */       p = p.next;
/* 2564:     */     }
/* 2565:2432 */     p.gen = (this.jj_gen + xla - this.jj_la);p.first = this.token;p.arg = xla;
/* 2566:     */   }
/* 2567:     */   
/* 2568:     */   public final void enable_tracing() {}
/* 2569:     */   
/* 2570:     */   public final void disable_tracing() {}
/* 2571:     */   
/* 2572:     */   static final class JJCalls
/* 2573:     */   {
/* 2574:     */     int gen;
/* 2575:     */     Token first;
/* 2576:     */     int arg;
/* 2577:     */     JJCalls next;
/* 2578:     */   }
/* 2579:     */   
/* 2580:     */   private static final class LookaheadSuccess
/* 2581:     */     extends Error
/* 2582:     */   {}
/* 2583:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.steadystate.css.parser.SACParserCSS21
 * JD-Core Version:    0.7.0.1
 */