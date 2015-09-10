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
/*   16:     */ public class SACParserCSS2
/*   17:     */   extends AbstractSACParser
/*   18:     */   implements Parser, SACParserCSS2Constants
/*   19:     */ {
/*   20:  18 */   private boolean _quiet = true;
/*   21:     */   public SACParserCSS2TokenManager token_source;
/*   22:     */   public Token token;
/*   23:     */   public Token jj_nt;
/*   24:     */   private int jj_ntk;
/*   25:     */   private Token jj_scanpos;
/*   26:     */   private Token jj_lastpos;
/*   27:     */   private int jj_la;
/*   28:     */   
/*   29:     */   public SACParserCSS2()
/*   30:     */   {
/*   31:  21 */     this((CharStream)null);
/*   32:     */   }
/*   33:     */   
/*   34:     */   public String getParserVersion()
/*   35:     */   {
/*   36:  25 */     return "http://www.w3.org/TR/REC-CSS2/";
/*   37:     */   }
/*   38:     */   
/*   39:     */   protected String getGrammarUri()
/*   40:     */   {
/*   41:  30 */     return "http://www.w3.org/TR/REC-CSS2/grammar.html";
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
/*   80:     */       case 24: 
/*   81:     */       case 25: 
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
/*   92:     */       case 24: 
/*   93:  81 */         jj_consume_token(24);
/*   94:  82 */         break;
/*   95:     */       case 25: 
/*   96:  84 */         jj_consume_token(25);
/*   97:     */       }
/*   98:     */     }
/*   99:  87 */     this.jj_la1[2] = this.jj_gen;
/*  100:  88 */     jj_consume_token(-1);
/*  101:  89 */     throw new ParseException();
/*  102:  94 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  103:     */     {
/*  104:     */     case 28: 
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
/*  116:     */       case 24: 
/*  117:     */       case 25: 
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
/*  128:     */       case 24: 
/*  129: 120 */         jj_consume_token(24);
/*  130: 121 */         break;
/*  131:     */       case 25: 
/*  132: 123 */         jj_consume_token(25);
/*  133:     */       }
/*  134:     */     }
/*  135: 126 */     this.jj_la1[5] = this.jj_gen;
/*  136: 127 */     jj_consume_token(-1);
/*  137: 128 */     throw new ParseException();
/*  138: 134 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  139:     */     {
/*  140:     */     case 8: 
/*  141:     */     case 10: 
/*  142:     */     case 11: 
/*  143:     */     case 17: 
/*  144:     */     case 19: 
/*  145:     */     case 29: 
/*  146:     */     case 30: 
/*  147:     */     case 31: 
/*  148:     */     case 33: 
/*  149:     */     case 56: 
/*  150:     */       break;
/*  151:     */     default: 
/*  152: 148 */       this.jj_la1[6] = this.jj_gen;
/*  153: 149 */       break;
/*  154:     */     }
/*  155: 151 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  156:     */     {
/*  157:     */     case 8: 
/*  158:     */     case 10: 
/*  159:     */     case 11: 
/*  160:     */     case 17: 
/*  161:     */     case 19: 
/*  162:     */     case 56: 
/*  163: 158 */       styleRule();
/*  164: 159 */       break;
/*  165:     */     case 30: 
/*  166: 161 */       mediaRule();
/*  167: 162 */       break;
/*  168:     */     case 29: 
/*  169: 164 */       pageRule();
/*  170: 165 */       break;
/*  171:     */     case 31: 
/*  172: 167 */       fontFaceRule();
/*  173: 168 */       break;
/*  174:     */     case 33: 
/*  175: 170 */       unknownRule();
/*  176: 171 */       break;
/*  177:     */     default: 
/*  178: 173 */       this.jj_la1[7] = this.jj_gen;
/*  179: 174 */       jj_consume_token(-1);
/*  180: 175 */       throw new ParseException();
/*  181:     */     }
/*  182:     */     for (;;)
/*  183:     */     {
/*  184: 179 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  185:     */       {
/*  186:     */       case 1: 
/*  187:     */       case 24: 
/*  188:     */       case 25: 
/*  189:     */         break;
/*  190:     */       default: 
/*  191: 186 */         this.jj_la1[8] = this.jj_gen;
/*  192: 187 */         break;
/*  193:     */       }
/*  194: 189 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  195:     */       {
/*  196:     */       case 1: 
/*  197: 191 */         jj_consume_token(1);
/*  198: 192 */         break;
/*  199:     */       case 24: 
/*  200: 194 */         jj_consume_token(24);
/*  201: 195 */         break;
/*  202:     */       case 25: 
/*  203: 197 */         jj_consume_token(25);
/*  204:     */       }
/*  205:     */     }
/*  206: 200 */     this.jj_la1[9] = this.jj_gen;
/*  207: 201 */     jj_consume_token(-1);
/*  208: 202 */     throw new ParseException();
/*  209: 208 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  210:     */     {
/*  211:     */     case 8: 
/*  212:     */     case 10: 
/*  213:     */     case 11: 
/*  214:     */     case 17: 
/*  215:     */     case 19: 
/*  216:     */     case 28: 
/*  217:     */     case 29: 
/*  218:     */     case 30: 
/*  219:     */     case 31: 
/*  220:     */     case 33: 
/*  221:     */     case 56: 
/*  222:     */       break;
/*  223:     */     default: 
/*  224: 223 */       this.jj_la1[10] = this.jj_gen;
/*  225: 224 */       break;
/*  226:     */     }
/*  227: 226 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  228:     */     {
/*  229:     */     case 8: 
/*  230:     */     case 10: 
/*  231:     */     case 11: 
/*  232:     */     case 17: 
/*  233:     */     case 19: 
/*  234:     */     case 56: 
/*  235: 233 */       styleRule();
/*  236: 234 */       break;
/*  237:     */     case 30: 
/*  238: 236 */       mediaRule();
/*  239: 237 */       break;
/*  240:     */     case 29: 
/*  241: 239 */       pageRule();
/*  242: 240 */       break;
/*  243:     */     case 31: 
/*  244: 242 */       fontFaceRule();
/*  245: 243 */       break;
/*  246:     */     case 28: 
/*  247: 245 */       importRuleIgnored();
/*  248: 246 */       break;
/*  249:     */     case 33: 
/*  250: 248 */       unknownRule();
/*  251: 249 */       break;
/*  252:     */     default: 
/*  253: 251 */       this.jj_la1[11] = this.jj_gen;
/*  254: 252 */       jj_consume_token(-1);
/*  255: 253 */       throw new ParseException();
/*  256:     */     }
/*  257:     */     for (;;)
/*  258:     */     {
/*  259: 257 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  260:     */       {
/*  261:     */       case 1: 
/*  262:     */       case 24: 
/*  263:     */       case 25: 
/*  264:     */         break;
/*  265:     */       default: 
/*  266: 264 */         this.jj_la1[12] = this.jj_gen;
/*  267: 265 */         break;
/*  268:     */       }
/*  269: 267 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  270:     */       {
/*  271:     */       case 1: 
/*  272: 269 */         jj_consume_token(1);
/*  273: 270 */         break;
/*  274:     */       case 24: 
/*  275: 272 */         jj_consume_token(24);
/*  276: 273 */         break;
/*  277:     */       case 25: 
/*  278: 275 */         jj_consume_token(25);
/*  279:     */       }
/*  280:     */     }
/*  281: 278 */     this.jj_la1[13] = this.jj_gen;
/*  282: 279 */     jj_consume_token(-1);
/*  283: 280 */     throw new ParseException();
/*  284:     */   }
/*  285:     */   
/*  286:     */   public final void styleSheetRuleSingle()
/*  287:     */     throws ParseException
/*  288:     */   {
/*  289: 290 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  290:     */     {
/*  291:     */     case 32: 
/*  292: 292 */       charsetRule();
/*  293: 293 */       break;
/*  294:     */     case 28: 
/*  295: 295 */       importRule();
/*  296: 296 */       break;
/*  297:     */     case 8: 
/*  298:     */     case 10: 
/*  299:     */     case 11: 
/*  300:     */     case 17: 
/*  301:     */     case 19: 
/*  302:     */     case 56: 
/*  303: 303 */       styleRule();
/*  304: 304 */       break;
/*  305:     */     case 30: 
/*  306: 306 */       mediaRule();
/*  307: 307 */       break;
/*  308:     */     case 29: 
/*  309: 309 */       pageRule();
/*  310: 310 */       break;
/*  311:     */     case 31: 
/*  312: 312 */       fontFaceRule();
/*  313: 313 */       break;
/*  314:     */     case 33: 
/*  315: 315 */       unknownRule();
/*  316: 316 */       break;
/*  317:     */     case 9: 
/*  318:     */     case 12: 
/*  319:     */     case 13: 
/*  320:     */     case 14: 
/*  321:     */     case 15: 
/*  322:     */     case 16: 
/*  323:     */     case 18: 
/*  324:     */     case 20: 
/*  325:     */     case 21: 
/*  326:     */     case 22: 
/*  327:     */     case 23: 
/*  328:     */     case 24: 
/*  329:     */     case 25: 
/*  330:     */     case 26: 
/*  331:     */     case 27: 
/*  332:     */     case 34: 
/*  333:     */     case 35: 
/*  334:     */     case 36: 
/*  335:     */     case 37: 
/*  336:     */     case 38: 
/*  337:     */     case 39: 
/*  338:     */     case 40: 
/*  339:     */     case 41: 
/*  340:     */     case 42: 
/*  341:     */     case 43: 
/*  342:     */     case 44: 
/*  343:     */     case 45: 
/*  344:     */     case 46: 
/*  345:     */     case 47: 
/*  346:     */     case 48: 
/*  347:     */     case 49: 
/*  348:     */     case 50: 
/*  349:     */     case 51: 
/*  350:     */     case 52: 
/*  351:     */     case 53: 
/*  352:     */     case 54: 
/*  353:     */     case 55: 
/*  354:     */     default: 
/*  355: 318 */       this.jj_la1[14] = this.jj_gen;
/*  356: 319 */       jj_consume_token(-1);
/*  357: 320 */       throw new ParseException();
/*  358:     */     }
/*  359:     */   }
/*  360:     */   
/*  361:     */   public final void charsetRule()
/*  362:     */     throws ParseException
/*  363:     */   {
/*  364:     */     try
/*  365:     */     {
/*  366: 327 */       jj_consume_token(32);
/*  367:     */       for (;;)
/*  368:     */       {
/*  369: 330 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  370:     */         {
/*  371:     */         case 1: 
/*  372:     */           break;
/*  373:     */         default: 
/*  374: 335 */           this.jj_la1[15] = this.jj_gen;
/*  375: 336 */           break;
/*  376:     */         }
/*  377: 338 */         jj_consume_token(1);
/*  378:     */       }
/*  379: 340 */       Token t = jj_consume_token(20);
/*  380:     */       for (;;)
/*  381:     */       {
/*  382: 343 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  383:     */         {
/*  384:     */         case 1: 
/*  385:     */           break;
/*  386:     */         default: 
/*  387: 348 */           this.jj_la1[16] = this.jj_gen;
/*  388: 349 */           break;
/*  389:     */         }
/*  390: 351 */         jj_consume_token(1);
/*  391:     */       }
/*  392: 353 */       jj_consume_token(9);
/*  393: 354 */       handleCharset(t.toString());
/*  394:     */     }
/*  395:     */     catch (ParseException e)
/*  396:     */     {
/*  397: 356 */       getErrorHandler().error(toCSSParseException("invalidCharsetRule", e));
/*  398:     */     }
/*  399:     */   }
/*  400:     */   
/*  401:     */   public final void unknownRule()
/*  402:     */     throws ParseException
/*  403:     */   {
/*  404:     */     try
/*  405:     */     {
/*  406: 365 */       Token t = jj_consume_token(33);
/*  407: 366 */       String s = skip();
/*  408: 367 */       handleIgnorableAtRule(s);
/*  409:     */     }
/*  410:     */     catch (ParseException e)
/*  411:     */     {
/*  412: 369 */       getErrorHandler().error(toCSSParseException("invalidUnknownRule", e));
/*  413:     */     }
/*  414:     */   }
/*  415:     */   
/*  416:     */   public final void importRule()
/*  417:     */     throws ParseException
/*  418:     */   {
/*  419: 383 */     SACMediaListImpl ml = new SACMediaListImpl();
/*  420:     */     try
/*  421:     */     {
/*  422: 385 */       jj_consume_token(28);
/*  423:     */       for (;;)
/*  424:     */       {
/*  425: 388 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  426:     */         {
/*  427:     */         case 1: 
/*  428:     */           break;
/*  429:     */         default: 
/*  430: 393 */           this.jj_la1[17] = this.jj_gen;
/*  431: 394 */           break;
/*  432:     */         }
/*  433: 396 */         jj_consume_token(1);
/*  434:     */       }
/*  435:     */       Token t;
/*  436: 398 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  437:     */       {
/*  438:     */       case 20: 
/*  439: 400 */         t = jj_consume_token(20);
/*  440: 401 */         break;
/*  441:     */       case 23: 
/*  442: 403 */         t = jj_consume_token(23);
/*  443: 404 */         break;
/*  444:     */       default: 
/*  445: 406 */         this.jj_la1[18] = this.jj_gen;
/*  446: 407 */         jj_consume_token(-1);
/*  447: 408 */         throw new ParseException();
/*  448:     */       }
/*  449:     */       for (;;)
/*  450:     */       {
/*  451: 412 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  452:     */         {
/*  453:     */         case 1: 
/*  454:     */           break;
/*  455:     */         default: 
/*  456: 417 */           this.jj_la1[19] = this.jj_gen;
/*  457: 418 */           break;
/*  458:     */         }
/*  459: 420 */         jj_consume_token(1);
/*  460:     */       }
/*  461: 422 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  462:     */       {
/*  463:     */       case 56: 
/*  464: 424 */         mediaList(ml);
/*  465: 425 */         break;
/*  466:     */       default: 
/*  467: 427 */         this.jj_la1[20] = this.jj_gen;
/*  468:     */       }
/*  469: 430 */       jj_consume_token(9);
/*  470: 431 */       handleImportStyle(unescape(t.image), ml, null);
/*  471:     */     }
/*  472:     */     catch (CSSParseException e)
/*  473:     */     {
/*  474: 433 */       getErrorHandler().error(e);
/*  475: 434 */       error_skipAtRule();
/*  476:     */     }
/*  477:     */     catch (ParseException e)
/*  478:     */     {
/*  479: 436 */       getErrorHandler().error(toCSSParseException("invalidImportRule", e));
/*  480:     */       
/*  481: 438 */       error_skipAtRule();
/*  482:     */     }
/*  483:     */   }
/*  484:     */   
/*  485:     */   public final void importRuleIgnored()
/*  486:     */     throws ParseException
/*  487:     */   {
/*  488: 444 */     SACMediaListImpl ml = new SACMediaListImpl();
/*  489: 445 */     ParseException e = generateParseException();
/*  490:     */     try
/*  491:     */     {
/*  492: 447 */       jj_consume_token(28);
/*  493:     */       for (;;)
/*  494:     */       {
/*  495: 450 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  496:     */         {
/*  497:     */         case 1: 
/*  498:     */           break;
/*  499:     */         default: 
/*  500: 455 */           this.jj_la1[21] = this.jj_gen;
/*  501: 456 */           break;
/*  502:     */         }
/*  503: 458 */         jj_consume_token(1);
/*  504:     */       }
/*  505:     */       Token t;
/*  506: 460 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  507:     */       {
/*  508:     */       case 20: 
/*  509: 462 */         t = jj_consume_token(20);
/*  510: 463 */         break;
/*  511:     */       case 23: 
/*  512: 465 */         t = jj_consume_token(23);
/*  513: 466 */         break;
/*  514:     */       default: 
/*  515: 468 */         this.jj_la1[22] = this.jj_gen;
/*  516: 469 */         jj_consume_token(-1);
/*  517: 470 */         throw new ParseException();
/*  518:     */       }
/*  519:     */       for (;;)
/*  520:     */       {
/*  521: 474 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  522:     */         {
/*  523:     */         case 1: 
/*  524:     */           break;
/*  525:     */         default: 
/*  526: 479 */           this.jj_la1[23] = this.jj_gen;
/*  527: 480 */           break;
/*  528:     */         }
/*  529: 482 */         jj_consume_token(1);
/*  530:     */       }
/*  531: 484 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  532:     */       {
/*  533:     */       case 56: 
/*  534: 486 */         mediaList(ml);
/*  535: 487 */         break;
/*  536:     */       default: 
/*  537: 489 */         this.jj_la1[24] = this.jj_gen;
/*  538:     */       }
/*  539: 492 */       jj_consume_token(9);
/*  540:     */     }
/*  541:     */     finally
/*  542:     */     {
/*  543: 494 */       getErrorHandler().error(toCSSParseException("invalidImportRuleIgnored", e));
/*  544:     */     }
/*  545:     */   }
/*  546:     */   
/*  547:     */   public final void mediaRule()
/*  548:     */     throws ParseException
/*  549:     */   {
/*  550: 504 */     boolean start = false;
/*  551: 505 */     SACMediaListImpl ml = new SACMediaListImpl();
/*  552:     */     try
/*  553:     */     {
/*  554: 507 */       jj_consume_token(30);
/*  555:     */       for (;;)
/*  556:     */       {
/*  557: 510 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  558:     */         {
/*  559:     */         case 1: 
/*  560:     */           break;
/*  561:     */         default: 
/*  562: 515 */           this.jj_la1[25] = this.jj_gen;
/*  563: 516 */           break;
/*  564:     */         }
/*  565: 518 */         jj_consume_token(1);
/*  566:     */       }
/*  567: 520 */       mediaList(ml);
/*  568: 521 */       start = true;
/*  569: 522 */       handleStartMedia(ml);
/*  570: 523 */       jj_consume_token(5);
/*  571:     */       for (;;)
/*  572:     */       {
/*  573: 526 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  574:     */         {
/*  575:     */         case 1: 
/*  576:     */           break;
/*  577:     */         default: 
/*  578: 531 */           this.jj_la1[26] = this.jj_gen;
/*  579: 532 */           break;
/*  580:     */         }
/*  581: 534 */         jj_consume_token(1);
/*  582:     */       }
/*  583: 536 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  584:     */       {
/*  585:     */       case 8: 
/*  586:     */       case 10: 
/*  587:     */       case 11: 
/*  588:     */       case 17: 
/*  589:     */       case 19: 
/*  590:     */       case 29: 
/*  591:     */       case 33: 
/*  592:     */       case 56: 
/*  593: 545 */         mediaRuleList();
/*  594: 546 */         break;
/*  595:     */       default: 
/*  596: 548 */         this.jj_la1[27] = this.jj_gen;
/*  597:     */       }
/*  598: 551 */       jj_consume_token(6);
/*  599:     */     }
/*  600:     */     catch (CSSParseException e)
/*  601:     */     {
/*  602: 553 */       getErrorHandler().error(e);
/*  603: 554 */       error_skipblock();
/*  604:     */     }
/*  605:     */     catch (ParseException e)
/*  606:     */     {
/*  607: 556 */       CSSParseException cpe = toCSSParseException("invalidMediaRule", e);
/*  608: 557 */       getErrorHandler().error(cpe);
/*  609: 558 */       getErrorHandler().warning(createSkipWarning("ignoringRule", cpe));
/*  610: 559 */       error_skipblock();
/*  611:     */     }
/*  612:     */     finally
/*  613:     */     {
/*  614: 561 */       if (start) {
/*  615: 562 */         handleEndMedia(ml);
/*  616:     */       }
/*  617:     */     }
/*  618:     */   }
/*  619:     */   
/*  620:     */   public final void mediaList(SACMediaListImpl ml)
/*  621:     */     throws ParseException
/*  622:     */   {
/*  623:     */     try
/*  624:     */     {
/*  625: 570 */       String s = medium();
/*  626:     */       for (;;)
/*  627:     */       {
/*  628: 573 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  629:     */         {
/*  630:     */         case 7: 
/*  631:     */           break;
/*  632:     */         default: 
/*  633: 578 */           this.jj_la1[28] = this.jj_gen;
/*  634: 579 */           break;
/*  635:     */         }
/*  636: 581 */         jj_consume_token(7);
/*  637:     */         for (;;)
/*  638:     */         {
/*  639: 584 */           switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  640:     */           {
/*  641:     */           case 1: 
/*  642:     */             break;
/*  643:     */           default: 
/*  644: 589 */             this.jj_la1[29] = this.jj_gen;
/*  645: 590 */             break;
/*  646:     */           }
/*  647: 592 */           jj_consume_token(1);
/*  648:     */         }
/*  649: 594 */         ml.add(s);
/*  650: 595 */         s = medium();
/*  651:     */       }
/*  652: 597 */       ml.add(s);
/*  653:     */     }
/*  654:     */     catch (ParseException e)
/*  655:     */     {
/*  656: 599 */       throw toCSSParseException("invalidMediaList", e);
/*  657:     */     }
/*  658:     */   }
/*  659:     */   
/*  660:     */   public final void mediaRuleList()
/*  661:     */     throws ParseException
/*  662:     */   {
/*  663:     */     for (;;)
/*  664:     */     {
/*  665: 606 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  666:     */       {
/*  667:     */       case 8: 
/*  668:     */       case 10: 
/*  669:     */       case 11: 
/*  670:     */       case 17: 
/*  671:     */       case 19: 
/*  672:     */       case 56: 
/*  673: 613 */         styleRule();
/*  674: 614 */         break;
/*  675:     */       case 29: 
/*  676: 616 */         pageRule();
/*  677: 617 */         break;
/*  678:     */       case 33: 
/*  679: 619 */         unknownRule();
/*  680: 620 */         break;
/*  681:     */       default: 
/*  682: 622 */         this.jj_la1[30] = this.jj_gen;
/*  683: 623 */         jj_consume_token(-1);
/*  684: 624 */         throw new ParseException();
/*  685:     */       }
/*  686:     */       for (;;)
/*  687:     */       {
/*  688: 628 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  689:     */         {
/*  690:     */         case 1: 
/*  691:     */           break;
/*  692:     */         default: 
/*  693: 633 */           this.jj_la1[31] = this.jj_gen;
/*  694: 634 */           break;
/*  695:     */         }
/*  696: 636 */         jj_consume_token(1);
/*  697:     */       }
/*  698: 638 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  699:     */       {
/*  700:     */       }
/*  701:     */     }
/*  702: 650 */     this.jj_la1[32] = this.jj_gen;
/*  703:     */   }
/*  704:     */   
/*  705:     */   public final void mediaRuleSingle()
/*  706:     */     throws ParseException
/*  707:     */   {
/*  708: 657 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  709:     */     {
/*  710:     */     case 8: 
/*  711:     */     case 10: 
/*  712:     */     case 11: 
/*  713:     */     case 17: 
/*  714:     */     case 19: 
/*  715:     */     case 56: 
/*  716: 664 */       styleRule();
/*  717: 665 */       break;
/*  718:     */     case 29: 
/*  719: 667 */       pageRule();
/*  720: 668 */       break;
/*  721:     */     case 33: 
/*  722: 670 */       unknownRule();
/*  723: 671 */       break;
/*  724:     */     default: 
/*  725: 673 */       this.jj_la1[33] = this.jj_gen;
/*  726: 674 */       jj_consume_token(-1);
/*  727: 675 */       throw new ParseException();
/*  728:     */     }
/*  729:     */   }
/*  730:     */   
/*  731:     */   public final String medium()
/*  732:     */     throws ParseException
/*  733:     */   {
/*  734: 686 */     Token t = jj_consume_token(56);
/*  735:     */     for (;;)
/*  736:     */     {
/*  737: 689 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  738:     */       {
/*  739:     */       case 1: 
/*  740:     */         break;
/*  741:     */       default: 
/*  742: 694 */         this.jj_la1[34] = this.jj_gen;
/*  743: 695 */         break;
/*  744:     */       }
/*  745: 697 */       jj_consume_token(1);
/*  746:     */     }
/*  747: 699 */     handleMedium(t.image);
/*  748: 700 */     return t.image;
/*  749:     */   }
/*  750:     */   
/*  751:     */   public final void pageRule()
/*  752:     */     throws ParseException
/*  753:     */   {
/*  754: 711 */     Token t = null;
/*  755: 712 */     String s = null;
/*  756: 713 */     boolean start = false;
/*  757:     */     try
/*  758:     */     {
/*  759: 715 */       jj_consume_token(29);
/*  760:     */       for (;;)
/*  761:     */       {
/*  762: 718 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  763:     */         {
/*  764:     */         case 1: 
/*  765:     */           break;
/*  766:     */         default: 
/*  767: 723 */           this.jj_la1[35] = this.jj_gen;
/*  768: 724 */           break;
/*  769:     */         }
/*  770: 726 */         jj_consume_token(1);
/*  771:     */       }
/*  772: 728 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  773:     */       {
/*  774:     */       case 10: 
/*  775:     */       case 56: 
/*  776: 731 */         if (jj_2_1(2))
/*  777:     */         {
/*  778: 732 */           t = jj_consume_token(56);
/*  779: 733 */           s = pseudoPage();
/*  780:     */           for (;;)
/*  781:     */           {
/*  782: 736 */             switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  783:     */             {
/*  784:     */             case 1: 
/*  785:     */               break;
/*  786:     */             default: 
/*  787: 741 */               this.jj_la1[36] = this.jj_gen;
/*  788: 742 */               break;
/*  789:     */             }
/*  790: 744 */             jj_consume_token(1);
/*  791:     */           }
/*  792:     */         }
/*  793: 747 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  794:     */         {
/*  795:     */         case 56: 
/*  796: 749 */           t = jj_consume_token(56);
/*  797:     */           for (;;)
/*  798:     */           {
/*  799: 752 */             switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  800:     */             {
/*  801:     */             case 1: 
/*  802:     */               break;
/*  803:     */             default: 
/*  804: 757 */               this.jj_la1[37] = this.jj_gen;
/*  805: 758 */               break;
/*  806:     */             }
/*  807: 760 */             jj_consume_token(1);
/*  808:     */           }
/*  809:     */         case 10: 
/*  810: 764 */           s = pseudoPage();
/*  811:     */           for (;;)
/*  812:     */           {
/*  813: 767 */             switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  814:     */             {
/*  815:     */             case 1: 
/*  816:     */               break;
/*  817:     */             default: 
/*  818: 772 */               this.jj_la1[38] = this.jj_gen;
/*  819: 773 */               break;
/*  820:     */             }
/*  821: 775 */             jj_consume_token(1);
/*  822:     */           }
/*  823:     */         }
/*  824: 779 */         this.jj_la1[39] = this.jj_gen;
/*  825: 780 */         jj_consume_token(-1);
/*  826: 781 */         throw new ParseException();
/*  827:     */       }
/*  828: 786 */       this.jj_la1[40] = this.jj_gen;
/*  829:     */       
/*  830:     */ 
/*  831: 789 */       jj_consume_token(5);
/*  832:     */       for (;;)
/*  833:     */       {
/*  834: 792 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  835:     */         {
/*  836:     */         case 1: 
/*  837:     */           break;
/*  838:     */         default: 
/*  839: 797 */           this.jj_la1[41] = this.jj_gen;
/*  840: 798 */           break;
/*  841:     */         }
/*  842: 800 */         jj_consume_token(1);
/*  843:     */       }
/*  844: 802 */       start = true;
/*  845: 803 */       handleStartPage(t != null ? unescape(t.image) : null, s);
/*  846: 804 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  847:     */       {
/*  848:     */       case 56: 
/*  849: 806 */         declaration();
/*  850: 807 */         break;
/*  851:     */       default: 
/*  852: 809 */         this.jj_la1[42] = this.jj_gen;
/*  853:     */       }
/*  854:     */       for (;;)
/*  855:     */       {
/*  856: 814 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  857:     */         {
/*  858:     */         case 9: 
/*  859:     */           break;
/*  860:     */         default: 
/*  861: 819 */           this.jj_la1[43] = this.jj_gen;
/*  862: 820 */           break;
/*  863:     */         }
/*  864: 822 */         jj_consume_token(9);
/*  865:     */         for (;;)
/*  866:     */         {
/*  867: 825 */           switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  868:     */           {
/*  869:     */           case 1: 
/*  870:     */             break;
/*  871:     */           default: 
/*  872: 830 */             this.jj_la1[44] = this.jj_gen;
/*  873: 831 */             break;
/*  874:     */           }
/*  875: 833 */           jj_consume_token(1);
/*  876:     */         }
/*  877: 835 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  878:     */         {
/*  879:     */         case 56: 
/*  880: 837 */           declaration();
/*  881: 838 */           break;
/*  882:     */         default: 
/*  883: 840 */           this.jj_la1[45] = this.jj_gen;
/*  884:     */         }
/*  885:     */       }
/*  886: 844 */       jj_consume_token(6);
/*  887:     */     }
/*  888:     */     catch (ParseException e)
/*  889:     */     {
/*  890: 846 */       throw toCSSParseException("invalidPageRule", e);
/*  891:     */     }
/*  892:     */     finally
/*  893:     */     {
/*  894: 848 */       if (start) {
/*  895: 849 */         handleEndPage(t != null ? unescape(t.image) : null, s);
/*  896:     */       }
/*  897:     */     }
/*  898:     */   }
/*  899:     */   
/*  900:     */   public final String pseudoPage()
/*  901:     */     throws ParseException
/*  902:     */   {
/*  903: 861 */     jj_consume_token(10);
/*  904: 862 */     Token t = jj_consume_token(56);
/*  905: 863 */     return t.image;
/*  906:     */   }
/*  907:     */   
/*  908:     */   public final void fontFaceRule()
/*  909:     */     throws ParseException
/*  910:     */   {
/*  911: 874 */     boolean start = false;
/*  912:     */     try
/*  913:     */     {
/*  914: 876 */       jj_consume_token(31);
/*  915:     */       for (;;)
/*  916:     */       {
/*  917: 879 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  918:     */         {
/*  919:     */         case 1: 
/*  920:     */           break;
/*  921:     */         default: 
/*  922: 884 */           this.jj_la1[46] = this.jj_gen;
/*  923: 885 */           break;
/*  924:     */         }
/*  925: 887 */         jj_consume_token(1);
/*  926:     */       }
/*  927: 889 */       jj_consume_token(5);
/*  928:     */       for (;;)
/*  929:     */       {
/*  930: 892 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  931:     */         {
/*  932:     */         case 1: 
/*  933:     */           break;
/*  934:     */         default: 
/*  935: 897 */           this.jj_la1[47] = this.jj_gen;
/*  936: 898 */           break;
/*  937:     */         }
/*  938: 900 */         jj_consume_token(1);
/*  939:     */       }
/*  940: 902 */       start = true;handleStartFontFace();
/*  941: 903 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  942:     */       {
/*  943:     */       case 56: 
/*  944: 905 */         declaration();
/*  945: 906 */         break;
/*  946:     */       default: 
/*  947: 908 */         this.jj_la1[48] = this.jj_gen;
/*  948:     */       }
/*  949:     */       for (;;)
/*  950:     */       {
/*  951: 913 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  952:     */         {
/*  953:     */         case 9: 
/*  954:     */           break;
/*  955:     */         default: 
/*  956: 918 */           this.jj_la1[49] = this.jj_gen;
/*  957: 919 */           break;
/*  958:     */         }
/*  959: 921 */         jj_consume_token(9);
/*  960:     */         for (;;)
/*  961:     */         {
/*  962: 924 */           switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  963:     */           {
/*  964:     */           case 1: 
/*  965:     */             break;
/*  966:     */           default: 
/*  967: 929 */             this.jj_la1[50] = this.jj_gen;
/*  968: 930 */             break;
/*  969:     */           }
/*  970: 932 */           jj_consume_token(1);
/*  971:     */         }
/*  972: 934 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  973:     */         {
/*  974:     */         case 56: 
/*  975: 936 */           declaration();
/*  976: 937 */           break;
/*  977:     */         default: 
/*  978: 939 */           this.jj_la1[51] = this.jj_gen;
/*  979:     */         }
/*  980:     */       }
/*  981: 943 */       jj_consume_token(6);
/*  982:     */     }
/*  983:     */     catch (ParseException e)
/*  984:     */     {
/*  985: 945 */       throw toCSSParseException("invalidFontFaceRule", e);
/*  986:     */     }
/*  987:     */     finally
/*  988:     */     {
/*  989: 947 */       if (start) {
/*  990: 948 */         handleEndFontFace();
/*  991:     */       }
/*  992:     */     }
/*  993:     */   }
/*  994:     */   
/*  995:     */   public final LexicalUnit operator(LexicalUnit prev)
/*  996:     */     throws ParseException
/*  997:     */   {
/*  998:     */     Token t;
/*  999: 960 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1000:     */     {
/* 1001:     */     case 12: 
/* 1002: 962 */       t = jj_consume_token(12);
/* 1003:     */       for (;;)
/* 1004:     */       {
/* 1005: 965 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1006:     */         {
/* 1007:     */         case 1: 
/* 1008:     */           break;
/* 1009:     */         default: 
/* 1010: 970 */           this.jj_la1[52] = this.jj_gen;
/* 1011: 971 */           break;
/* 1012:     */         }
/* 1013: 973 */         jj_consume_token(1);
/* 1014:     */       }
/* 1015: 975 */       return new LexicalUnitImpl(prev, (short)4);
/* 1016:     */     case 7: 
/* 1017: 978 */       t = jj_consume_token(7);
/* 1018:     */       for (;;)
/* 1019:     */       {
/* 1020: 981 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1021:     */         {
/* 1022:     */         case 1: 
/* 1023:     */           break;
/* 1024:     */         default: 
/* 1025: 986 */           this.jj_la1[53] = this.jj_gen;
/* 1026: 987 */           break;
/* 1027:     */         }
/* 1028: 989 */         jj_consume_token(1);
/* 1029:     */       }
/* 1030: 991 */       return new LexicalUnitImpl(prev, (short)0);
/* 1031:     */     }
/* 1032: 994 */     this.jj_la1[54] = this.jj_gen;
/* 1033: 995 */     jj_consume_token(-1);
/* 1034: 996 */     throw new ParseException();
/* 1035:     */   }
/* 1036:     */   
/* 1037:     */   public final char combinator()
/* 1038:     */     throws ParseException
/* 1039:     */   {
/* 1040:1007 */     char c = ' ';
/* 1041:1008 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1042:     */     {
/* 1043:     */     case 13: 
/* 1044:1010 */       jj_consume_token(13);
/* 1045:1011 */       c = '+';
/* 1046:     */       for (;;)
/* 1047:     */       {
/* 1048:1014 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1049:     */         {
/* 1050:     */         case 1: 
/* 1051:     */           break;
/* 1052:     */         default: 
/* 1053:1019 */           this.jj_la1[55] = this.jj_gen;
/* 1054:1020 */           break;
/* 1055:     */         }
/* 1056:1022 */         jj_consume_token(1);
/* 1057:     */       }
/* 1058:     */     case 16: 
/* 1059:1026 */       jj_consume_token(16);
/* 1060:1027 */       c = '>';
/* 1061:     */       for (;;)
/* 1062:     */       {
/* 1063:1030 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1064:     */         {
/* 1065:     */         case 1: 
/* 1066:     */           break;
/* 1067:     */         default: 
/* 1068:1035 */           this.jj_la1[56] = this.jj_gen;
/* 1069:1036 */           break;
/* 1070:     */         }
/* 1071:1038 */         jj_consume_token(1);
/* 1072:     */       }
/* 1073:     */     case 1: 
/* 1074:1042 */       jj_consume_token(1);
/* 1075:1043 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1076:     */       {
/* 1077:     */       case 13: 
/* 1078:     */       case 16: 
/* 1079:1046 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1080:     */         {
/* 1081:     */         case 13: 
/* 1082:1048 */           jj_consume_token(13);
/* 1083:1049 */           c = '+';
/* 1084:1050 */           break;
/* 1085:     */         case 16: 
/* 1086:1052 */           jj_consume_token(16);
/* 1087:1053 */           c = '>';
/* 1088:1054 */           break;
/* 1089:     */         default: 
/* 1090:1056 */           this.jj_la1[57] = this.jj_gen;
/* 1091:1057 */           jj_consume_token(-1);
/* 1092:1058 */           throw new ParseException();
/* 1093:     */         }
/* 1094:     */         for (;;)
/* 1095:     */         {
/* 1096:1062 */           switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1097:     */           {
/* 1098:     */           case 1: 
/* 1099:     */             break;
/* 1100:     */           default: 
/* 1101:1067 */             this.jj_la1[58] = this.jj_gen;
/* 1102:1068 */             break;
/* 1103:     */           }
/* 1104:1070 */           jj_consume_token(1);
/* 1105:     */         }
/* 1106:     */       }
/* 1107:1074 */       this.jj_la1[59] = this.jj_gen;
/* 1108:     */       
/* 1109:     */ 
/* 1110:1077 */       break;
/* 1111:     */     default: 
/* 1112:1079 */       this.jj_la1[60] = this.jj_gen;
/* 1113:1080 */       jj_consume_token(-1);
/* 1114:1081 */       throw new ParseException();
/* 1115:     */     }
/* 1116:1083 */     return c;
/* 1117:     */   }
/* 1118:     */   
/* 1119:     */   public final char unaryOperator()
/* 1120:     */     throws ParseException
/* 1121:     */   {
/* 1122:1093 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1123:     */     {
/* 1124:     */     case 14: 
/* 1125:1095 */       jj_consume_token(14);
/* 1126:1096 */       return '-';
/* 1127:     */     case 13: 
/* 1128:1099 */       jj_consume_token(13);
/* 1129:1100 */       return '+';
/* 1130:     */     }
/* 1131:1103 */     this.jj_la1[61] = this.jj_gen;
/* 1132:1104 */     jj_consume_token(-1);
/* 1133:1105 */     throw new ParseException();
/* 1134:     */   }
/* 1135:     */   
/* 1136:     */   public final String property()
/* 1137:     */     throws ParseException
/* 1138:     */   {
/* 1139:1117 */     Token t = jj_consume_token(56);
/* 1140:     */     for (;;)
/* 1141:     */     {
/* 1142:1120 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1143:     */       {
/* 1144:     */       case 1: 
/* 1145:     */         break;
/* 1146:     */       default: 
/* 1147:1125 */         this.jj_la1[62] = this.jj_gen;
/* 1148:1126 */         break;
/* 1149:     */       }
/* 1150:1128 */       jj_consume_token(1);
/* 1151:     */     }
/* 1152:1130 */     return unescape(t.image);
/* 1153:     */   }
/* 1154:     */   
/* 1155:     */   public final void styleRule()
/* 1156:     */     throws ParseException
/* 1157:     */   {
/* 1158:1141 */     SelectorList selList = null;
/* 1159:1142 */     boolean start = false;
/* 1160:     */     try
/* 1161:     */     {
/* 1162:1144 */       selList = selectorList();
/* 1163:1145 */       jj_consume_token(5);
/* 1164:     */       for (;;)
/* 1165:     */       {
/* 1166:1148 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1167:     */         {
/* 1168:     */         case 1: 
/* 1169:     */           break;
/* 1170:     */         default: 
/* 1171:1153 */           this.jj_la1[63] = this.jj_gen;
/* 1172:1154 */           break;
/* 1173:     */         }
/* 1174:1156 */         jj_consume_token(1);
/* 1175:     */       }
/* 1176:1158 */       start = true;
/* 1177:1159 */       handleStartSelector(selList);
/* 1178:1160 */       styleDeclaration();
/* 1179:1161 */       jj_consume_token(6);
/* 1180:     */     }
/* 1181:     */     catch (CSSParseException e)
/* 1182:     */     {
/* 1183:1163 */       getErrorHandler().error(e);
/* 1184:1164 */       getErrorHandler().warning(createSkipWarning("ignoringRule", e));
/* 1185:1165 */       error_skipblock();
/* 1186:     */     }
/* 1187:     */     catch (ParseException e)
/* 1188:     */     {
/* 1189:1167 */       CSSParseException cpe = toCSSParseException("invalidStyleRule", e);
/* 1190:1168 */       getErrorHandler().error(cpe);
/* 1191:1169 */       getErrorHandler().warning(createSkipWarning("ignoringFollowingDeclarations", cpe));
/* 1192:1170 */       error_skipblock();
/* 1193:     */     }
/* 1194:     */     finally
/* 1195:     */     {
/* 1196:1172 */       if (start) {
/* 1197:1173 */         handleEndSelector(selList);
/* 1198:     */       }
/* 1199:     */     }
/* 1200:     */   }
/* 1201:     */   
/* 1202:     */   public final SelectorList selectorList()
/* 1203:     */     throws ParseException
/* 1204:     */   {
/* 1205:1179 */     SelectorListImpl selList = new SelectorListImpl();
/* 1206:     */     
/* 1207:1181 */     Selector sel = selector();
/* 1208:     */     for (;;)
/* 1209:     */     {
/* 1210:1184 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1211:     */       {
/* 1212:     */       case 7: 
/* 1213:     */         break;
/* 1214:     */       default: 
/* 1215:1189 */         this.jj_la1[64] = this.jj_gen;
/* 1216:1190 */         break;
/* 1217:     */       }
/* 1218:1192 */       jj_consume_token(7);
/* 1219:     */       for (;;)
/* 1220:     */       {
/* 1221:1195 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1222:     */         {
/* 1223:     */         case 1: 
/* 1224:     */           break;
/* 1225:     */         default: 
/* 1226:1200 */           this.jj_la1[65] = this.jj_gen;
/* 1227:1201 */           break;
/* 1228:     */         }
/* 1229:1203 */         jj_consume_token(1);
/* 1230:     */       }
/* 1231:1205 */       selList.add(sel);
/* 1232:1206 */       sel = selector();
/* 1233:     */     }
/* 1234:1208 */     selList.add(sel);
/* 1235:1209 */     return selList;
/* 1236:     */   }
/* 1237:     */   
/* 1238:     */   public final Selector selector()
/* 1239:     */     throws ParseException
/* 1240:     */   {
/* 1241:     */     try
/* 1242:     */     {
/* 1243:1222 */       Selector sel = simpleSelector(null, ' ');
/* 1244:1225 */       while (jj_2_2(2))
/* 1245:     */       {
/* 1246:1230 */         char comb = combinator();
/* 1247:1231 */         sel = simpleSelector(sel, comb);
/* 1248:     */       }
/* 1249:     */       for (;;)
/* 1250:     */       {
/* 1251:1235 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1252:     */         {
/* 1253:     */         case 1: 
/* 1254:     */           break;
/* 1255:     */         default: 
/* 1256:1240 */           this.jj_la1[66] = this.jj_gen;
/* 1257:1241 */           break;
/* 1258:     */         }
/* 1259:1243 */         jj_consume_token(1);
/* 1260:     */       }
/* 1261:1245 */       handleSelector(sel);
/* 1262:1246 */       return sel;
/* 1263:     */     }
/* 1264:     */     catch (ParseException e)
/* 1265:     */     {
/* 1266:1248 */       throw toCSSParseException("invalidSelector", e);
/* 1267:     */     }
/* 1268:     */   }
/* 1269:     */   
/* 1270:     */   public final Selector simpleSelector(Selector sel, char comb)
/* 1271:     */     throws ParseException
/* 1272:     */   {
/* 1273:1261 */     SimpleSelector simpleSel = null;
/* 1274:1262 */     Condition c = null;
/* 1275:     */     try
/* 1276:     */     {
/* 1277:1264 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1278:     */       {
/* 1279:     */       case 11: 
/* 1280:     */       case 56: 
/* 1281:1267 */         simpleSel = elementName();
/* 1282:     */         for (;;)
/* 1283:     */         {
/* 1284:1270 */           switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1285:     */           {
/* 1286:     */           case 8: 
/* 1287:     */           case 10: 
/* 1288:     */           case 17: 
/* 1289:     */           case 19: 
/* 1290:     */             break;
/* 1291:     */           default: 
/* 1292:1278 */             this.jj_la1[67] = this.jj_gen;
/* 1293:1279 */             break;
/* 1294:     */           }
/* 1295:1281 */           switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1296:     */           {
/* 1297:     */           case 19: 
/* 1298:1283 */             c = hash(c);
/* 1299:1284 */             break;
/* 1300:     */           case 8: 
/* 1301:1286 */             c = _class(c);
/* 1302:1287 */             break;
/* 1303:     */           case 17: 
/* 1304:1289 */             c = attrib(c);
/* 1305:1290 */             break;
/* 1306:     */           case 10: 
/* 1307:1292 */             c = pseudo(c);
/* 1308:     */           }
/* 1309:     */         }
/* 1310:1295 */         this.jj_la1[68] = this.jj_gen;
/* 1311:1296 */         jj_consume_token(-1);
/* 1312:1297 */         throw new ParseException();
/* 1313:     */       case 8: 
/* 1314:     */       case 10: 
/* 1315:     */       case 17: 
/* 1316:     */       case 19: 
/* 1317:1305 */         simpleSel = getSelectorFactory().createElementSelector(null, null);
/* 1318:     */         for (;;)
/* 1319:     */         {
/* 1320:1308 */           switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1321:     */           {
/* 1322:     */           case 19: 
/* 1323:1310 */             c = hash(c);
/* 1324:1311 */             break;
/* 1325:     */           case 8: 
/* 1326:1313 */             c = _class(c);
/* 1327:1314 */             break;
/* 1328:     */           case 17: 
/* 1329:1316 */             c = attrib(c);
/* 1330:1317 */             break;
/* 1331:     */           case 10: 
/* 1332:1319 */             c = pseudo(c);
/* 1333:1320 */             break;
/* 1334:     */           default: 
/* 1335:1322 */             this.jj_la1[69] = this.jj_gen;
/* 1336:1323 */             jj_consume_token(-1);
/* 1337:1324 */             throw new ParseException();
/* 1338:     */           }
/* 1339:1326 */           switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1340:     */           {
/* 1341:     */           }
/* 1342:     */         }
/* 1343:1334 */         this.jj_la1[70] = this.jj_gen;
/* 1344:1335 */         break;
/* 1345:     */       default: 
/* 1346:1340 */         this.jj_la1[71] = this.jj_gen;
/* 1347:1341 */         jj_consume_token(-1);
/* 1348:1342 */         throw new ParseException();
/* 1349:     */       }
/* 1350:1344 */       if (c != null) {
/* 1351:1345 */         simpleSel = getSelectorFactory().createConditionalSelector(simpleSel, c);
/* 1352:     */       }
/* 1353:1348 */       if (sel != null) {
/* 1354:1349 */         switch (comb)
/* 1355:     */         {
/* 1356:     */         case ' ': 
/* 1357:1351 */           sel = getSelectorFactory().createDescendantSelector(sel, simpleSel);
/* 1358:1352 */           break;
/* 1359:     */         case '+': 
/* 1360:1354 */           sel = getSelectorFactory().createDirectAdjacentSelector(sel.getSelectorType(), sel, simpleSel);
/* 1361:1355 */           break;
/* 1362:     */         case '>': 
/* 1363:1357 */           sel = getSelectorFactory().createChildSelector(sel, simpleSel);
/* 1364:     */         }
/* 1365:     */       }
/* 1366:1361 */       return simpleSel;
/* 1367:     */     }
/* 1368:     */     catch (ParseException e)
/* 1369:     */     {
/* 1370:1366 */       throw toCSSParseException("invalidSimpleSelector", e);
/* 1371:     */     }
/* 1372:     */   }
/* 1373:     */   
/* 1374:     */   public final Condition _class(Condition pred)
/* 1375:     */     throws ParseException
/* 1376:     */   {
/* 1377:     */     try
/* 1378:     */     {
/* 1379:1379 */       jj_consume_token(8);
/* 1380:1380 */       Token t = jj_consume_token(56);
/* 1381:1381 */       Condition c = getConditionFactory().createClassCondition(null, t.image);
/* 1382:1382 */       return pred == null ? c : getConditionFactory().createAndCondition(pred, c);
/* 1383:     */     }
/* 1384:     */     catch (ParseException e)
/* 1385:     */     {
/* 1386:1384 */       throw toCSSParseException("invalidClassSelector", e);
/* 1387:     */     }
/* 1388:     */   }
/* 1389:     */   
/* 1390:     */   public final SimpleSelector elementName()
/* 1391:     */     throws ParseException
/* 1392:     */   {
/* 1393:     */     try
/* 1394:     */     {
/* 1395:1397 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1396:     */       {
/* 1397:     */       case 56: 
/* 1398:1399 */         Token t = jj_consume_token(56);
/* 1399:1400 */         return getSelectorFactory().createElementSelector(null, unescape(t.image));
/* 1400:     */       case 11: 
/* 1401:1403 */         jj_consume_token(11);
/* 1402:1404 */         return getSelectorFactory().createElementSelector(null, null);
/* 1403:     */       }
/* 1404:1407 */       this.jj_la1[72] = this.jj_gen;
/* 1405:1408 */       jj_consume_token(-1);
/* 1406:1409 */       throw new ParseException();
/* 1407:     */     }
/* 1408:     */     catch (ParseException e)
/* 1409:     */     {
/* 1410:1412 */       throw toCSSParseException("invalidElementName", e);
/* 1411:     */     }
/* 1412:     */   }
/* 1413:     */   
/* 1414:     */   public final Condition attrib(Condition pred)
/* 1415:     */     throws ParseException
/* 1416:     */   {
/* 1417:1425 */     String name = null;
/* 1418:1426 */     String value = null;
/* 1419:1427 */     int type = 0;
/* 1420:     */     try
/* 1421:     */     {
/* 1422:1429 */       jj_consume_token(17);
/* 1423:     */       for (;;)
/* 1424:     */       {
/* 1425:1432 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1426:     */         {
/* 1427:     */         case 1: 
/* 1428:     */           break;
/* 1429:     */         default: 
/* 1430:1437 */           this.jj_la1[73] = this.jj_gen;
/* 1431:1438 */           break;
/* 1432:     */         }
/* 1433:1440 */         jj_consume_token(1);
/* 1434:     */       }
/* 1435:1442 */       Token t = jj_consume_token(56);
/* 1436:1443 */       name = unescape(t.image);
/* 1437:     */       for (;;)
/* 1438:     */       {
/* 1439:1446 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1440:     */         {
/* 1441:     */         case 1: 
/* 1442:     */           break;
/* 1443:     */         default: 
/* 1444:1451 */           this.jj_la1[74] = this.jj_gen;
/* 1445:1452 */           break;
/* 1446:     */         }
/* 1447:1454 */         jj_consume_token(1);
/* 1448:     */       }
/* 1449:1456 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1450:     */       {
/* 1451:     */       case 15: 
/* 1452:     */       case 26: 
/* 1453:     */       case 27: 
/* 1454:1460 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1455:     */         {
/* 1456:     */         case 15: 
/* 1457:1462 */           jj_consume_token(15);
/* 1458:1463 */           type = 1;
/* 1459:1464 */           break;
/* 1460:     */         case 26: 
/* 1461:1466 */           jj_consume_token(26);
/* 1462:1467 */           type = 2;
/* 1463:1468 */           break;
/* 1464:     */         case 27: 
/* 1465:1470 */           jj_consume_token(27);
/* 1466:1471 */           type = 3;
/* 1467:1472 */           break;
/* 1468:     */         default: 
/* 1469:1474 */           this.jj_la1[75] = this.jj_gen;
/* 1470:1475 */           jj_consume_token(-1);
/* 1471:1476 */           throw new ParseException();
/* 1472:     */         }
/* 1473:     */         for (;;)
/* 1474:     */         {
/* 1475:1480 */           switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1476:     */           {
/* 1477:     */           case 1: 
/* 1478:     */             break;
/* 1479:     */           default: 
/* 1480:1485 */             this.jj_la1[76] = this.jj_gen;
/* 1481:1486 */             break;
/* 1482:     */           }
/* 1483:1488 */           jj_consume_token(1);
/* 1484:     */         }
/* 1485:1490 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1486:     */         {
/* 1487:     */         case 56: 
/* 1488:1492 */           t = jj_consume_token(56);
/* 1489:1493 */           value = t.image;
/* 1490:1494 */           break;
/* 1491:     */         case 20: 
/* 1492:1496 */           t = jj_consume_token(20);
/* 1493:1497 */           value = unescape(t.image);
/* 1494:1498 */           break;
/* 1495:     */         default: 
/* 1496:1500 */           this.jj_la1[77] = this.jj_gen;
/* 1497:1501 */           jj_consume_token(-1);
/* 1498:1502 */           throw new ParseException();
/* 1499:     */         }
/* 1500:     */         for (;;)
/* 1501:     */         {
/* 1502:1506 */           switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1503:     */           {
/* 1504:     */           case 1: 
/* 1505:     */             break;
/* 1506:     */           default: 
/* 1507:1511 */             this.jj_la1[78] = this.jj_gen;
/* 1508:1512 */             break;
/* 1509:     */           }
/* 1510:1514 */           jj_consume_token(1);
/* 1511:     */         }
/* 1512:     */       }
/* 1513:1518 */       this.jj_la1[79] = this.jj_gen;
/* 1514:     */       
/* 1515:     */ 
/* 1516:1521 */       jj_consume_token(18);
/* 1517:1522 */       Condition c = null;
/* 1518:1523 */       switch (type)
/* 1519:     */       {
/* 1520:     */       case 0: 
/* 1521:1525 */         c = getConditionFactory().createAttributeCondition(name, null, false, null);
/* 1522:1526 */         break;
/* 1523:     */       case 1: 
/* 1524:1528 */         c = getConditionFactory().createAttributeCondition(name, null, false, value);
/* 1525:1529 */         break;
/* 1526:     */       case 2: 
/* 1527:1531 */         c = getConditionFactory().createOneOfAttributeCondition(name, null, false, value);
/* 1528:1532 */         break;
/* 1529:     */       case 3: 
/* 1530:1534 */         c = getConditionFactory().createBeginHyphenAttributeCondition(name, null, false, value);
/* 1531:     */       }
/* 1532:1537 */       return pred == null ? c : getConditionFactory().createAndCondition(pred, c);
/* 1533:     */     }
/* 1534:     */     catch (ParseException e)
/* 1535:     */     {
/* 1536:1539 */       throw toCSSParseException("invalidAttrib", e);
/* 1537:     */     }
/* 1538:     */   }
/* 1539:     */   
/* 1540:     */   public final Condition pseudo(Condition pred)
/* 1541:     */     throws ParseException
/* 1542:     */   {
/* 1543:     */     try
/* 1544:     */     {
/* 1545:1555 */       jj_consume_token(10);
/* 1546:     */       Token t;
/* 1547:     */       Condition c;
/* 1548:1556 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1549:     */       {
/* 1550:     */       case 56: 
/* 1551:1558 */         t = jj_consume_token(56);
/* 1552:     */         
/* 1553:     */ 
/* 1554:1561 */         String s = t.image;
/* 1555:1562 */         c = getConditionFactory().createPseudoClassCondition(null, s);
/* 1556:1563 */         return pred == null ? c : getConditionFactory().createAndCondition(pred, c);
/* 1557:     */       case 55: 
/* 1558:1568 */         t = jj_consume_token(55);
/* 1559:1569 */         String function = unescape(t.image);
/* 1560:     */         for (;;)
/* 1561:     */         {
/* 1562:1572 */           switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1563:     */           {
/* 1564:     */           case 1: 
/* 1565:     */             break;
/* 1566:     */           default: 
/* 1567:1577 */             this.jj_la1[80] = this.jj_gen;
/* 1568:1578 */             break;
/* 1569:     */           }
/* 1570:1580 */           jj_consume_token(1);
/* 1571:     */         }
/* 1572:1582 */         t = jj_consume_token(56);
/* 1573:1583 */         String arg = unescape(t.image);
/* 1574:     */         for (;;)
/* 1575:     */         {
/* 1576:1586 */           switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1577:     */           {
/* 1578:     */           case 1: 
/* 1579:     */             break;
/* 1580:     */           default: 
/* 1581:1591 */             this.jj_la1[81] = this.jj_gen;
/* 1582:1592 */             break;
/* 1583:     */           }
/* 1584:1594 */           jj_consume_token(1);
/* 1585:     */         }
/* 1586:1596 */         jj_consume_token(21);
/* 1587:1597 */         if (function.equalsIgnoreCase("lang("))
/* 1588:     */         {
/* 1589:1598 */           c = getConditionFactory().createLangCondition(unescape(arg));
/* 1590:1599 */           return pred == null ? c : getConditionFactory().createAndCondition(pred, c);
/* 1591:     */         }
/* 1592:1603 */         Condition c = getConditionFactory().createPseudoClassCondition(null, function + arg + ")");
/* 1593:1604 */         return pred == null ? c : getConditionFactory().createAndCondition(pred, c);
/* 1594:     */       }
/* 1595:1618 */       this.jj_la1[82] = this.jj_gen;
/* 1596:1619 */       jj_consume_token(-1);
/* 1597:1620 */       throw new ParseException();
/* 1598:     */     }
/* 1599:     */     catch (ParseException e)
/* 1600:     */     {
/* 1601:1623 */       throw toCSSParseException("invalidPseudo", e);
/* 1602:     */     }
/* 1603:     */   }
/* 1604:     */   
/* 1605:     */   public final Condition hash(Condition pred)
/* 1606:     */     throws ParseException
/* 1607:     */   {
/* 1608:     */     try
/* 1609:     */     {
/* 1610:1631 */       Token t = jj_consume_token(19);
/* 1611:1632 */       Condition c = getConditionFactory().createIdCondition(t.image.substring(1));
/* 1612:1633 */       return pred == null ? c : getConditionFactory().createAndCondition(pred, c);
/* 1613:     */     }
/* 1614:     */     catch (ParseException e)
/* 1615:     */     {
/* 1616:1635 */       throw toCSSParseException("invalidHash", e);
/* 1617:     */     }
/* 1618:     */   }
/* 1619:     */   
/* 1620:     */   public final void styleDeclaration()
/* 1621:     */     throws ParseException
/* 1622:     */   {
/* 1623:     */     try
/* 1624:     */     {
/* 1625:1642 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1626:     */       {
/* 1627:     */       case 56: 
/* 1628:1644 */         declaration();
/* 1629:1645 */         break;
/* 1630:     */       default: 
/* 1631:1647 */         this.jj_la1[83] = this.jj_gen;
/* 1632:     */       }
/* 1633:     */       for (;;)
/* 1634:     */       {
/* 1635:1652 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1636:     */         {
/* 1637:     */         case 9: 
/* 1638:     */           break;
/* 1639:     */         default: 
/* 1640:1657 */           this.jj_la1[84] = this.jj_gen;
/* 1641:1658 */           break;
/* 1642:     */         }
/* 1643:1660 */         jj_consume_token(9);
/* 1644:     */         for (;;)
/* 1645:     */         {
/* 1646:1663 */           switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1647:     */           {
/* 1648:     */           case 1: 
/* 1649:     */             break;
/* 1650:     */           default: 
/* 1651:1668 */             this.jj_la1[85] = this.jj_gen;
/* 1652:1669 */             break;
/* 1653:     */           }
/* 1654:1671 */           jj_consume_token(1);
/* 1655:     */         }
/* 1656:1673 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1657:     */         {
/* 1658:     */         case 56: 
/* 1659:1675 */           declaration();
/* 1660:1676 */           break;
/* 1661:     */         default: 
/* 1662:1678 */           this.jj_la1[86] = this.jj_gen;
/* 1663:     */         }
/* 1664:     */       }
/* 1665:     */     }
/* 1666:     */     catch (ParseException ex)
/* 1667:     */     {
/* 1668:1683 */       getErrorHandler().error(toCSSParseException("invalidDeclaration", ex));
/* 1669:1684 */       error_skipdecl();
/* 1670:     */     }
/* 1671:     */   }
/* 1672:     */   
/* 1673:     */   public final void declaration()
/* 1674:     */     throws ParseException
/* 1675:     */   {
/* 1676:1697 */     boolean priority = false;
/* 1677:     */     try
/* 1678:     */     {
/* 1679:1699 */       String p = property();
/* 1680:1700 */       jj_consume_token(10);
/* 1681:     */       for (;;)
/* 1682:     */       {
/* 1683:1703 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1684:     */         {
/* 1685:     */         case 1: 
/* 1686:     */           break;
/* 1687:     */         default: 
/* 1688:1708 */           this.jj_la1[87] = this.jj_gen;
/* 1689:1709 */           break;
/* 1690:     */         }
/* 1691:1711 */         jj_consume_token(1);
/* 1692:     */       }
/* 1693:1713 */       LexicalUnit e = expr();
/* 1694:1714 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1695:     */       {
/* 1696:     */       case 34: 
/* 1697:1716 */         priority = prio();
/* 1698:1717 */         break;
/* 1699:     */       default: 
/* 1700:1719 */         this.jj_la1[88] = this.jj_gen;
/* 1701:     */       }
/* 1702:1722 */       handleProperty(p, e, priority);
/* 1703:     */     }
/* 1704:     */     catch (CSSParseException ex)
/* 1705:     */     {
/* 1706:1724 */       getErrorHandler().error(ex);
/* 1707:1725 */       error_skipdecl();
/* 1708:     */     }
/* 1709:     */     catch (ParseException ex)
/* 1710:     */     {
/* 1711:1727 */       getErrorHandler().error(toCSSParseException("invalidDeclaration", ex));
/* 1712:1728 */       error_skipdecl();
/* 1713:     */     }
/* 1714:     */   }
/* 1715:     */   
/* 1716:     */   public final boolean prio()
/* 1717:     */     throws ParseException
/* 1718:     */   {
/* 1719:1738 */     jj_consume_token(34);
/* 1720:     */     for (;;)
/* 1721:     */     {
/* 1722:1741 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1723:     */       {
/* 1724:     */       case 1: 
/* 1725:     */         break;
/* 1726:     */       default: 
/* 1727:1746 */         this.jj_la1[89] = this.jj_gen;
/* 1728:1747 */         break;
/* 1729:     */       }
/* 1730:1749 */       jj_consume_token(1);
/* 1731:     */     }
/* 1732:1751 */     return true;
/* 1733:     */   }
/* 1734:     */   
/* 1735:     */   public final LexicalUnit expr()
/* 1736:     */     throws ParseException
/* 1737:     */   {
/* 1738:     */     try
/* 1739:     */     {
/* 1740:1766 */       LexicalUnit head = term(null);
/* 1741:1767 */       LexicalUnit body = head;
/* 1742:     */       for (;;)
/* 1743:     */       {
/* 1744:1770 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1745:     */         {
/* 1746:     */         case 7: 
/* 1747:     */         case 12: 
/* 1748:     */         case 13: 
/* 1749:     */         case 14: 
/* 1750:     */         case 19: 
/* 1751:     */         case 20: 
/* 1752:     */         case 23: 
/* 1753:     */         case 35: 
/* 1754:     */         case 36: 
/* 1755:     */         case 37: 
/* 1756:     */         case 38: 
/* 1757:     */         case 39: 
/* 1758:     */         case 40: 
/* 1759:     */         case 41: 
/* 1760:     */         case 42: 
/* 1761:     */         case 43: 
/* 1762:     */         case 44: 
/* 1763:     */         case 45: 
/* 1764:     */         case 46: 
/* 1765:     */         case 47: 
/* 1766:     */         case 48: 
/* 1767:     */         case 49: 
/* 1768:     */         case 50: 
/* 1769:     */         case 51: 
/* 1770:     */         case 52: 
/* 1771:     */         case 53: 
/* 1772:     */         case 54: 
/* 1773:     */         case 55: 
/* 1774:     */         case 56: 
/* 1775:     */         case 59: 
/* 1776:     */           break;
/* 1777:     */         case 8: 
/* 1778:     */         case 9: 
/* 1779:     */         case 10: 
/* 1780:     */         case 11: 
/* 1781:     */         case 15: 
/* 1782:     */         case 16: 
/* 1783:     */         case 17: 
/* 1784:     */         case 18: 
/* 1785:     */         case 21: 
/* 1786:     */         case 22: 
/* 1787:     */         case 24: 
/* 1788:     */         case 25: 
/* 1789:     */         case 26: 
/* 1790:     */         case 27: 
/* 1791:     */         case 28: 
/* 1792:     */         case 29: 
/* 1793:     */         case 30: 
/* 1794:     */         case 31: 
/* 1795:     */         case 32: 
/* 1796:     */         case 33: 
/* 1797:     */         case 34: 
/* 1798:     */         case 57: 
/* 1799:     */         case 58: 
/* 1800:     */         default: 
/* 1801:1804 */           this.jj_la1[90] = this.jj_gen;
/* 1802:1805 */           break;
/* 1803:     */         }
/* 1804:1807 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1805:     */         {
/* 1806:     */         case 7: 
/* 1807:     */         case 12: 
/* 1808:1810 */           body = operator(body);
/* 1809:1811 */           break;
/* 1810:     */         default: 
/* 1811:1813 */           this.jj_la1[91] = this.jj_gen;
/* 1812:     */         }
/* 1813:1816 */         body = term(body);
/* 1814:     */       }
/* 1815:1818 */       return head;
/* 1816:     */     }
/* 1817:     */     catch (ParseException ex)
/* 1818:     */     {
/* 1819:1820 */       throw toCSSParseException("invalidExpr", ex);
/* 1820:     */     }
/* 1821:     */   }
/* 1822:     */   
/* 1823:     */   public final LexicalUnit term(LexicalUnit prev)
/* 1824:     */     throws ParseException
/* 1825:     */   {
/* 1826:1835 */     char op = ' ';
/* 1827:     */     
/* 1828:1837 */     LexicalUnit value = null;
/* 1829:1838 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1830:     */     {
/* 1831:     */     case 13: 
/* 1832:     */     case 14: 
/* 1833:1841 */       op = unaryOperator();
/* 1834:1842 */       break;
/* 1835:     */     default: 
/* 1836:1844 */       this.jj_la1[92] = this.jj_gen;
/* 1837:     */     }
/* 1838:     */     Token t;
/* 1839:1847 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1840:     */     {
/* 1841:     */     case 36: 
/* 1842:     */     case 37: 
/* 1843:     */     case 38: 
/* 1844:     */     case 39: 
/* 1845:     */     case 40: 
/* 1846:     */     case 41: 
/* 1847:     */     case 42: 
/* 1848:     */     case 43: 
/* 1849:     */     case 44: 
/* 1850:     */     case 45: 
/* 1851:     */     case 46: 
/* 1852:     */     case 47: 
/* 1853:     */     case 48: 
/* 1854:     */     case 49: 
/* 1855:     */     case 50: 
/* 1856:     */     case 52: 
/* 1857:     */     case 53: 
/* 1858:     */     case 55: 
/* 1859:1866 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1860:     */       {
/* 1861:     */       case 53: 
/* 1862:1868 */         t = jj_consume_token(53);
/* 1863:     */         try
/* 1864:     */         {
/* 1865:1871 */           value = LexicalUnitImpl.createNumber(prev, intValue(op, t.image));
/* 1866:     */         }
/* 1867:     */         catch (NumberFormatException e)
/* 1868:     */         {
/* 1869:1875 */           value = LexicalUnitImpl.createNumber(prev, floatValue(op, t.image));
/* 1870:     */         }
/* 1871:     */       case 52: 
/* 1872:1879 */         t = jj_consume_token(52);
/* 1873:1880 */         value = LexicalUnitImpl.createPercentage(prev, floatValue(op, t.image));
/* 1874:1881 */         break;
/* 1875:     */       case 38: 
/* 1876:1883 */         t = jj_consume_token(38);
/* 1877:1884 */         value = LexicalUnitImpl.createPixel(prev, floatValue(op, t.image));
/* 1878:1885 */         break;
/* 1879:     */       case 39: 
/* 1880:1887 */         t = jj_consume_token(39);
/* 1881:1888 */         value = LexicalUnitImpl.createCentimeter(prev, floatValue(op, t.image));
/* 1882:1889 */         break;
/* 1883:     */       case 40: 
/* 1884:1891 */         t = jj_consume_token(40);
/* 1885:1892 */         value = LexicalUnitImpl.createMillimeter(prev, floatValue(op, t.image));
/* 1886:1893 */         break;
/* 1887:     */       case 41: 
/* 1888:1895 */         t = jj_consume_token(41);
/* 1889:1896 */         value = LexicalUnitImpl.createInch(prev, floatValue(op, t.image));
/* 1890:1897 */         break;
/* 1891:     */       case 42: 
/* 1892:1899 */         t = jj_consume_token(42);
/* 1893:1900 */         value = LexicalUnitImpl.createPoint(prev, floatValue(op, t.image));
/* 1894:1901 */         break;
/* 1895:     */       case 43: 
/* 1896:1903 */         t = jj_consume_token(43);
/* 1897:1904 */         value = LexicalUnitImpl.createPica(prev, floatValue(op, t.image));
/* 1898:1905 */         break;
/* 1899:     */       case 36: 
/* 1900:1907 */         t = jj_consume_token(36);
/* 1901:1908 */         value = LexicalUnitImpl.createEm(prev, floatValue(op, t.image));
/* 1902:1909 */         break;
/* 1903:     */       case 37: 
/* 1904:1911 */         t = jj_consume_token(37);
/* 1905:1912 */         value = LexicalUnitImpl.createEx(prev, floatValue(op, t.image));
/* 1906:1913 */         break;
/* 1907:     */       case 44: 
/* 1908:1915 */         t = jj_consume_token(44);
/* 1909:1916 */         value = LexicalUnitImpl.createDegree(prev, floatValue(op, t.image));
/* 1910:1917 */         break;
/* 1911:     */       case 45: 
/* 1912:1919 */         t = jj_consume_token(45);
/* 1913:1920 */         value = LexicalUnitImpl.createRadian(prev, floatValue(op, t.image));
/* 1914:1921 */         break;
/* 1915:     */       case 46: 
/* 1916:1923 */         t = jj_consume_token(46);
/* 1917:1924 */         value = LexicalUnitImpl.createGradian(prev, floatValue(op, t.image));
/* 1918:1925 */         break;
/* 1919:     */       case 47: 
/* 1920:1927 */         t = jj_consume_token(47);
/* 1921:1928 */         value = LexicalUnitImpl.createMillisecond(prev, floatValue(op, t.image));
/* 1922:1929 */         break;
/* 1923:     */       case 48: 
/* 1924:1931 */         t = jj_consume_token(48);
/* 1925:1932 */         value = LexicalUnitImpl.createSecond(prev, floatValue(op, t.image));
/* 1926:1933 */         break;
/* 1927:     */       case 49: 
/* 1928:1935 */         t = jj_consume_token(49);
/* 1929:1936 */         value = LexicalUnitImpl.createHertz(prev, floatValue(op, t.image));
/* 1930:1937 */         break;
/* 1931:     */       case 50: 
/* 1932:1939 */         t = jj_consume_token(50);
/* 1933:1940 */         value = LexicalUnitImpl.createKiloHertz(prev, floatValue(op, t.image));
/* 1934:1941 */         break;
/* 1935:     */       case 55: 
/* 1936:1943 */         value = function(prev);
/* 1937:1944 */         break;
/* 1938:     */       case 51: 
/* 1939:     */       case 54: 
/* 1940:     */       default: 
/* 1941:1946 */         this.jj_la1[93] = this.jj_gen;
/* 1942:1947 */         jj_consume_token(-1);
/* 1943:1948 */         throw new ParseException();
/* 1944:     */       }
/* 1945:     */       break;
/* 1946:     */     case 20: 
/* 1947:1952 */       t = jj_consume_token(20);
/* 1948:1953 */       value = new LexicalUnitImpl(prev, (short)36, unescape(t.image));
/* 1949:1954 */       break;
/* 1950:     */     case 56: 
/* 1951:1956 */       t = jj_consume_token(56);
/* 1952:1957 */       value = new LexicalUnitImpl(prev, (short)35, t.image);
/* 1953:1958 */       break;
/* 1954:     */     case 23: 
/* 1955:1960 */       t = jj_consume_token(23);
/* 1956:1961 */       value = new LexicalUnitImpl(prev, (short)24, t.image);
/* 1957:1962 */       break;
/* 1958:     */     case 59: 
/* 1959:1964 */       t = jj_consume_token(59);
/* 1960:1965 */       value = new LexicalUnitImpl(prev, (short)39, t.image);
/* 1961:1966 */       break;
/* 1962:     */     case 54: 
/* 1963:1968 */       value = rgb(prev);
/* 1964:1969 */       break;
/* 1965:     */     case 19: 
/* 1966:1971 */       value = hexcolor(prev);
/* 1967:1972 */       break;
/* 1968:     */     case 51: 
/* 1969:1974 */       t = jj_consume_token(51);
/* 1970:1975 */       int n = getLastNumPos(t.image);
/* 1971:1976 */       value = LexicalUnitImpl.createDimension(prev, floatValue(op, t.image.substring(0, n + 1)), t.image.substring(n + 1));
/* 1972:     */       
/* 1973:     */ 
/* 1974:     */ 
/* 1975:1980 */       break;
/* 1976:     */     case 35: 
/* 1977:1982 */       t = jj_consume_token(35);
/* 1978:1983 */       value = new LexicalUnitImpl(prev, (short)12, t.image);
/* 1979:1984 */       break;
/* 1980:     */     case 21: 
/* 1981:     */     case 22: 
/* 1982:     */     case 24: 
/* 1983:     */     case 25: 
/* 1984:     */     case 26: 
/* 1985:     */     case 27: 
/* 1986:     */     case 28: 
/* 1987:     */     case 29: 
/* 1988:     */     case 30: 
/* 1989:     */     case 31: 
/* 1990:     */     case 32: 
/* 1991:     */     case 33: 
/* 1992:     */     case 34: 
/* 1993:     */     case 57: 
/* 1994:     */     case 58: 
/* 1995:     */     default: 
/* 1996:1986 */       this.jj_la1[94] = this.jj_gen;
/* 1997:1987 */       jj_consume_token(-1);
/* 1998:1988 */       throw new ParseException();
/* 1999:     */     }
/* 2000:     */     for (;;)
/* 2001:     */     {
/* 2002:1992 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 2003:     */       {
/* 2004:     */       case 1: 
/* 2005:     */         break;
/* 2006:     */       default: 
/* 2007:1997 */         this.jj_la1[95] = this.jj_gen;
/* 2008:1998 */         break;
/* 2009:     */       }
/* 2010:2000 */       jj_consume_token(1);
/* 2011:     */     }
/* 2012:2002 */     if ((value instanceof LexicalUnitImpl)) {
/* 2013:2004 */       ((LexicalUnitImpl)value).setLocator(getLocator());
/* 2014:     */     }
/* 2015:2006 */     return value;
/* 2016:     */   }
/* 2017:     */   
/* 2018:     */   public final LexicalUnit function(LexicalUnit prev)
/* 2019:     */     throws ParseException
/* 2020:     */   {
/* 2021:2018 */     Token t = jj_consume_token(55);
/* 2022:     */     for (;;)
/* 2023:     */     {
/* 2024:2021 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 2025:     */       {
/* 2026:     */       case 1: 
/* 2027:     */         break;
/* 2028:     */       default: 
/* 2029:2026 */         this.jj_la1[96] = this.jj_gen;
/* 2030:2027 */         break;
/* 2031:     */       }
/* 2032:2029 */       jj_consume_token(1);
/* 2033:     */     }
/* 2034:2031 */     LexicalUnit params = expr();
/* 2035:2032 */     jj_consume_token(21);
/* 2036:2033 */     return functionInternal(prev, t, params);
/* 2037:     */   }
/* 2038:     */   
/* 2039:     */   public final LexicalUnit rgb(LexicalUnit prev)
/* 2040:     */     throws ParseException
/* 2041:     */   {
/* 2042:2045 */     Token t = jj_consume_token(54);
/* 2043:     */     for (;;)
/* 2044:     */     {
/* 2045:2048 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 2046:     */       {
/* 2047:     */       case 1: 
/* 2048:     */         break;
/* 2049:     */       default: 
/* 2050:2053 */         this.jj_la1[97] = this.jj_gen;
/* 2051:2054 */         break;
/* 2052:     */       }
/* 2053:2056 */       jj_consume_token(1);
/* 2054:     */     }
/* 2055:2058 */     LexicalUnit params = expr();
/* 2056:2059 */     jj_consume_token(21);
/* 2057:2060 */     return LexicalUnitImpl.createRgbColor(prev, params);
/* 2058:     */   }
/* 2059:     */   
/* 2060:     */   public final LexicalUnit hexcolor(LexicalUnit prev)
/* 2061:     */     throws ParseException
/* 2062:     */   {
/* 2063:2071 */     Token t = jj_consume_token(19);
/* 2064:2072 */     return hexcolorInternal(prev, t);
/* 2065:     */   }
/* 2066:     */   
/* 2067:     */   void skipSelector()
/* 2068:     */     throws ParseException
/* 2069:     */   {
/* 2070:2077 */     Token t = getToken(1);
/* 2071:2078 */     while ((t.kind != 7) && (t.kind != 9) && (t.kind != 5) && (t.kind != 0))
/* 2072:     */     {
/* 2073:2079 */       getNextToken();
/* 2074:2080 */       t = getToken(1);
/* 2075:     */     }
/* 2076:     */   }
/* 2077:     */   
/* 2078:     */   String skip()
/* 2079:     */     throws ParseException
/* 2080:     */   {
/* 2081:2085 */     StringBuffer sb = new StringBuffer();
/* 2082:2086 */     int nesting = 0;
/* 2083:2087 */     Token t = getToken(0);
/* 2084:2088 */     if (t.image != null) {
/* 2085:2089 */       sb.append(t.image);
/* 2086:     */     }
/* 2087:     */     do
/* 2088:     */     {
/* 2089:2092 */       t = getNextToken();
/* 2090:2093 */       if (t.kind == 0) {
/* 2091:     */         break;
/* 2092:     */       }
/* 2093:2095 */       sb.append(t.image);
/* 2094:2096 */       if (t.kind == 5) {
/* 2095:2097 */         nesting++;
/* 2096:2098 */       } else if (t.kind == 6) {
/* 2097:2099 */         nesting--;
/* 2098:     */       } else {
/* 2099:2100 */         if ((t.kind == 9) && (nesting <= 0)) {
/* 2100:     */           break;
/* 2101:     */         }
/* 2102:     */       }
/* 2103:2103 */     } while ((t.kind != 6) || (nesting > 0));
/* 2104:2105 */     return sb.toString();
/* 2105:     */   }
/* 2106:     */   
/* 2107:     */   void error_skipblock()
/* 2108:     */     throws ParseException
/* 2109:     */   {
/* 2110:2110 */     int nesting = 0;
/* 2111:     */     Token t;
/* 2112:     */     do
/* 2113:     */     {
/* 2114:2113 */       t = getNextToken();
/* 2115:2114 */       if (t.kind == 5) {
/* 2116:2116 */         nesting++;
/* 2117:2118 */       } else if (t.kind == 6) {
/* 2118:2120 */         nesting--;
/* 2119:     */       } else {
/* 2120:2122 */         if (t.kind == 0) {
/* 2121:     */           break;
/* 2122:     */         }
/* 2123:     */       }
/* 2124:2127 */     } while ((t.kind != 6) || (nesting > 0));
/* 2125:     */   }
/* 2126:     */   
/* 2127:     */   void error_skipdecl()
/* 2128:     */     throws ParseException
/* 2129:     */   {
/* 2130:2131 */     int nesting = 0;
/* 2131:2132 */     Token t = getToken(1);
/* 2132:2133 */     if (t.kind == 5)
/* 2133:     */     {
/* 2134:2135 */       error_skipblock();
/* 2135:     */     }
/* 2136:     */     else
/* 2137:     */     {
/* 2138:2139 */       Token oldToken = t;
/* 2139:2140 */       while ((t.kind != 9) && (t.kind != 6) && (t.kind != 0))
/* 2140:     */       {
/* 2141:2142 */         oldToken = t;
/* 2142:2143 */         t = getNextToken();
/* 2143:     */       }
/* 2144:2145 */       if (t.kind == 6) {
/* 2145:2147 */         this.token = oldToken;
/* 2146:     */       }
/* 2147:     */     }
/* 2148:     */   }
/* 2149:     */   
/* 2150:     */   void error_skipAtRule()
/* 2151:     */     throws ParseException
/* 2152:     */   {
/* 2153:2153 */     Token t = null;
/* 2154:     */     do
/* 2155:     */     {
/* 2156:2156 */       t = getNextToken();
/* 2157:2158 */     } while (t.kind != 9);
/* 2158:     */   }
/* 2159:     */   
/* 2160:     */   private final boolean jj_2_1(int xla)
/* 2161:     */   {
/* 2162:2162 */     this.jj_la = xla;this.jj_lastpos = (this.jj_scanpos = this.token);
/* 2163:     */     try
/* 2164:     */     {
/* 2165:2163 */       return !jj_3_1();
/* 2166:     */     }
/* 2167:     */     catch (LookaheadSuccess ls)
/* 2168:     */     {
/* 2169:2164 */       return true;
/* 2170:     */     }
/* 2171:     */     finally
/* 2172:     */     {
/* 2173:2165 */       jj_save(0, xla);
/* 2174:     */     }
/* 2175:     */   }
/* 2176:     */   
/* 2177:     */   private final boolean jj_2_2(int xla)
/* 2178:     */   {
/* 2179:2169 */     this.jj_la = xla;this.jj_lastpos = (this.jj_scanpos = this.token);
/* 2180:     */     try
/* 2181:     */     {
/* 2182:2170 */       return !jj_3_2();
/* 2183:     */     }
/* 2184:     */     catch (LookaheadSuccess ls)
/* 2185:     */     {
/* 2186:2171 */       return true;
/* 2187:     */     }
/* 2188:     */     finally
/* 2189:     */     {
/* 2190:2172 */       jj_save(1, xla);
/* 2191:     */     }
/* 2192:     */   }
/* 2193:     */   
/* 2194:     */   private final boolean jj_3_2()
/* 2195:     */   {
/* 2196:2176 */     if (jj_3R_60()) {
/* 2197:2176 */       return true;
/* 2198:     */     }
/* 2199:2177 */     if (jj_3R_61()) {
/* 2200:2177 */       return true;
/* 2201:     */     }
/* 2202:2178 */     return false;
/* 2203:     */   }
/* 2204:     */   
/* 2205:     */   private final boolean jj_3R_81()
/* 2206:     */   {
/* 2207:2182 */     if (jj_scan_token(10)) {
/* 2208:2182 */       return true;
/* 2209:     */     }
/* 2210:2183 */     return false;
/* 2211:     */   }
/* 2212:     */   
/* 2213:     */   private final boolean jj_3R_77()
/* 2214:     */   {
/* 2215:2187 */     if (jj_3R_81()) {
/* 2216:2187 */       return true;
/* 2217:     */     }
/* 2218:2188 */     return false;
/* 2219:     */   }
/* 2220:     */   
/* 2221:     */   private final boolean jj_3R_76()
/* 2222:     */   {
/* 2223:2192 */     if (jj_3R_80()) {
/* 2224:2192 */       return true;
/* 2225:     */     }
/* 2226:2193 */     return false;
/* 2227:     */   }
/* 2228:     */   
/* 2229:     */   private final boolean jj_3R_75()
/* 2230:     */   {
/* 2231:2197 */     if (jj_3R_79()) {
/* 2232:2197 */       return true;
/* 2233:     */     }
/* 2234:2198 */     return false;
/* 2235:     */   }
/* 2236:     */   
/* 2237:     */   private final boolean jj_3R_74()
/* 2238:     */   {
/* 2239:2202 */     if (jj_3R_78()) {
/* 2240:2202 */       return true;
/* 2241:     */     }
/* 2242:2203 */     return false;
/* 2243:     */   }
/* 2244:     */   
/* 2245:     */   private final boolean jj_3R_69()
/* 2246:     */   {
/* 2247:2208 */     Token xsp = this.jj_scanpos;
/* 2248:2209 */     if (jj_3R_74())
/* 2249:     */     {
/* 2250:2210 */       this.jj_scanpos = xsp;
/* 2251:2211 */       if (jj_3R_75())
/* 2252:     */       {
/* 2253:2212 */         this.jj_scanpos = xsp;
/* 2254:2213 */         if (jj_3R_76())
/* 2255:     */         {
/* 2256:2214 */           this.jj_scanpos = xsp;
/* 2257:2215 */           if (jj_3R_77()) {
/* 2258:2215 */             return true;
/* 2259:     */           }
/* 2260:     */         }
/* 2261:     */       }
/* 2262:     */     }
/* 2263:2219 */     return false;
/* 2264:     */   }
/* 2265:     */   
/* 2266:     */   private final boolean jj_3R_70()
/* 2267:     */   {
/* 2268:2223 */     if (jj_scan_token(13)) {
/* 2269:2223 */       return true;
/* 2270:     */     }
/* 2271:2224 */     return false;
/* 2272:     */   }
/* 2273:     */   
/* 2274:     */   private final boolean jj_3R_67()
/* 2275:     */   {
/* 2276:2229 */     Token xsp = this.jj_scanpos;
/* 2277:2230 */     if (jj_3R_70())
/* 2278:     */     {
/* 2279:2231 */       this.jj_scanpos = xsp;
/* 2280:2232 */       if (jj_3R_71()) {
/* 2281:2232 */         return true;
/* 2282:     */       }
/* 2283:     */     }
/* 2284:2234 */     return false;
/* 2285:     */   }
/* 2286:     */   
/* 2287:     */   private final boolean jj_3_1()
/* 2288:     */   {
/* 2289:2238 */     if (jj_scan_token(56)) {
/* 2290:2238 */       return true;
/* 2291:     */     }
/* 2292:2239 */     if (jj_3R_59()) {
/* 2293:2239 */       return true;
/* 2294:     */     }
/* 2295:2240 */     return false;
/* 2296:     */   }
/* 2297:     */   
/* 2298:     */   private final boolean jj_3R_66()
/* 2299:     */   {
/* 2300:2245 */     if (jj_3R_69()) {
/* 2301:2245 */       return true;
/* 2302:     */     }
/* 2303:     */     Token xsp;
/* 2304:     */     do
/* 2305:     */     {
/* 2306:2247 */       xsp = this.jj_scanpos;
/* 2307:2248 */     } while (!jj_3R_69());
/* 2308:2248 */     this.jj_scanpos = xsp;
/* 2309:     */     
/* 2310:2250 */     return false;
/* 2311:     */   }
/* 2312:     */   
/* 2313:     */   private final boolean jj_3R_80()
/* 2314:     */   {
/* 2315:2254 */     if (jj_scan_token(17)) {
/* 2316:2254 */       return true;
/* 2317:     */     }
/* 2318:2255 */     return false;
/* 2319:     */   }
/* 2320:     */   
/* 2321:     */   private final boolean jj_3R_64()
/* 2322:     */   {
/* 2323:2259 */     if (jj_scan_token(1)) {
/* 2324:2259 */       return true;
/* 2325:     */     }
/* 2326:2261 */     Token xsp = this.jj_scanpos;
/* 2327:2262 */     if (jj_3R_67()) {
/* 2328:2262 */       this.jj_scanpos = xsp;
/* 2329:     */     }
/* 2330:2263 */     return false;
/* 2331:     */   }
/* 2332:     */   
/* 2333:     */   private final boolean jj_3R_63()
/* 2334:     */   {
/* 2335:2267 */     if (jj_scan_token(16)) {
/* 2336:2267 */       return true;
/* 2337:     */     }
/* 2338:     */     Token xsp;
/* 2339:     */     do
/* 2340:     */     {
/* 2341:2270 */       xsp = this.jj_scanpos;
/* 2342:2271 */     } while (!jj_scan_token(1));
/* 2343:2271 */     this.jj_scanpos = xsp;
/* 2344:     */     
/* 2345:2273 */     return false;
/* 2346:     */   }
/* 2347:     */   
/* 2348:     */   private final boolean jj_3R_79()
/* 2349:     */   {
/* 2350:2277 */     if (jj_scan_token(8)) {
/* 2351:2277 */       return true;
/* 2352:     */     }
/* 2353:2278 */     return false;
/* 2354:     */   }
/* 2355:     */   
/* 2356:     */   private final boolean jj_3R_62()
/* 2357:     */   {
/* 2358:2282 */     if (jj_scan_token(13)) {
/* 2359:2282 */       return true;
/* 2360:     */     }
/* 2361:     */     Token xsp;
/* 2362:     */     do
/* 2363:     */     {
/* 2364:2285 */       xsp = this.jj_scanpos;
/* 2365:2286 */     } while (!jj_scan_token(1));
/* 2366:2286 */     this.jj_scanpos = xsp;
/* 2367:     */     
/* 2368:2288 */     return false;
/* 2369:     */   }
/* 2370:     */   
/* 2371:     */   private final boolean jj_3R_60()
/* 2372:     */   {
/* 2373:2293 */     Token xsp = this.jj_scanpos;
/* 2374:2294 */     if (jj_3R_62())
/* 2375:     */     {
/* 2376:2295 */       this.jj_scanpos = xsp;
/* 2377:2296 */       if (jj_3R_63())
/* 2378:     */       {
/* 2379:2297 */         this.jj_scanpos = xsp;
/* 2380:2298 */         if (jj_3R_64()) {
/* 2381:2298 */           return true;
/* 2382:     */         }
/* 2383:     */       }
/* 2384:     */     }
/* 2385:2301 */     return false;
/* 2386:     */   }
/* 2387:     */   
/* 2388:     */   private final boolean jj_3R_65()
/* 2389:     */   {
/* 2390:2305 */     if (jj_3R_68()) {
/* 2391:2305 */       return true;
/* 2392:     */     }
/* 2393:2306 */     return false;
/* 2394:     */   }
/* 2395:     */   
/* 2396:     */   private final boolean jj_3R_59()
/* 2397:     */   {
/* 2398:2310 */     if (jj_scan_token(10)) {
/* 2399:2310 */       return true;
/* 2400:     */     }
/* 2401:2311 */     return false;
/* 2402:     */   }
/* 2403:     */   
/* 2404:     */   private final boolean jj_3R_61()
/* 2405:     */   {
/* 2406:2316 */     Token xsp = this.jj_scanpos;
/* 2407:2317 */     if (jj_3R_65())
/* 2408:     */     {
/* 2409:2318 */       this.jj_scanpos = xsp;
/* 2410:2319 */       if (jj_3R_66()) {
/* 2411:2319 */         return true;
/* 2412:     */       }
/* 2413:     */     }
/* 2414:2321 */     return false;
/* 2415:     */   }
/* 2416:     */   
/* 2417:     */   private final boolean jj_3R_73()
/* 2418:     */   {
/* 2419:2325 */     if (jj_scan_token(11)) {
/* 2420:2325 */       return true;
/* 2421:     */     }
/* 2422:2326 */     return false;
/* 2423:     */   }
/* 2424:     */   
/* 2425:     */   private final boolean jj_3R_72()
/* 2426:     */   {
/* 2427:2330 */     if (jj_scan_token(56)) {
/* 2428:2330 */       return true;
/* 2429:     */     }
/* 2430:2331 */     return false;
/* 2431:     */   }
/* 2432:     */   
/* 2433:     */   private final boolean jj_3R_71()
/* 2434:     */   {
/* 2435:2335 */     if (jj_scan_token(16)) {
/* 2436:2335 */       return true;
/* 2437:     */     }
/* 2438:2336 */     return false;
/* 2439:     */   }
/* 2440:     */   
/* 2441:     */   private final boolean jj_3R_68()
/* 2442:     */   {
/* 2443:2341 */     Token xsp = this.jj_scanpos;
/* 2444:2342 */     if (jj_3R_72())
/* 2445:     */     {
/* 2446:2343 */       this.jj_scanpos = xsp;
/* 2447:2344 */       if (jj_3R_73()) {
/* 2448:2344 */         return true;
/* 2449:     */       }
/* 2450:     */     }
/* 2451:2346 */     return false;
/* 2452:     */   }
/* 2453:     */   
/* 2454:     */   private final boolean jj_3R_78()
/* 2455:     */   {
/* 2456:2350 */     if (jj_scan_token(19)) {
/* 2457:2350 */       return true;
/* 2458:     */     }
/* 2459:2351 */     return false;
/* 2460:     */   }
/* 2461:     */   
/* 2462:2359 */   public boolean lookingAhead = false;
/* 2463:     */   private boolean jj_semLA;
/* 2464:     */   private int jj_gen;
/* 2465:2362 */   private final int[] jj_la1 = new int[98];
/* 2466:     */   private static int[] jj_la1_0;
/* 2467:     */   private static int[] jj_la1_1;
/* 2468:     */   private static int[] jj_la1_2;
/* 2469:     */   
/* 2470:     */   static
/* 2471:     */   {
/* 2472:2367 */     jj_la1_0();
/* 2473:2368 */     jj_la1_1();
/* 2474:2369 */     jj_la1_2();
/* 2475:     */   }
/* 2476:     */   
/* 2477:     */   private static void jj_la1_0()
/* 2478:     */   {
/* 2479:2372 */     jj_la1_0 = new int[] { 0, 50331650, 50331650, 268435456, 50331650, 50331650, -536212224, -536212224, 50331650, 50331650, -267776768, -267776768, 50331650, 50331650, -267776768, 2, 2, 2, 9437184, 2, 0, 2, 9437184, 2, 0, 2, 2, 537529600, 128, 2, 537529600, 2, 537529600, 537529600, 2, 2, 2, 2, 2, 1024, 1024, 2, 0, 512, 2, 0, 2, 2, 0, 512, 2, 0, 2, 2, 4224, 2, 2, 73728, 2, 73728, 73730, 24576, 2, 2, 128, 2, 2, 656640, 656640, 656640, 656640, 658688, 2048, 2, 2, 201359360, 2, 1048576, 2, 201359360, 2, 2, 0, 0, 512, 2, 0, 2, 0, 2, 9990272, 4224, 24576, 0, 9961472, 2, 2, 2 };
/* 2480:     */   }
/* 2481:     */   
/* 2482:     */   private static void jj_la1_1()
/* 2483:     */   {
/* 2484:2375 */     jj_la1_1 = new int[] { 1, 0, 0, 0, 0, 0, 16777218, 16777218, 0, 0, 16777218, 16777218, 0, 0, 16777219, 0, 0, 0, 0, 0, 16777216, 0, 0, 0, 16777216, 0, 0, 16777218, 0, 0, 16777218, 0, 16777218, 16777218, 0, 0, 0, 0, 0, 16777216, 16777216, 0, 16777216, 0, 0, 16777216, 0, 0, 16777216, 0, 0, 16777216, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 16777216, 16777216, 0, 0, 0, 0, 16777216, 0, 0, 0, 0, 25165824, 16777216, 0, 0, 16777216, 0, 4, 0, 167772152, 0, 0, 12058608, 167772152, 0, 0, 0 };
/* 2485:     */   }
/* 2486:     */   
/* 2487:     */   private static void jj_la1_2()
/* 2488:     */   {
/* 2489:2378 */     jj_la1_2 = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
/* 2490:     */   }
/* 2491:     */   
/* 2492:2380 */   private final JJCalls[] jj_2_rtns = new JJCalls[2];
/* 2493:2381 */   private boolean jj_rescan = false;
/* 2494:2382 */   private int jj_gc = 0;
/* 2495:     */   
/* 2496:     */   public SACParserCSS2(CharStream stream)
/* 2497:     */   {
/* 2498:2385 */     this.token_source = new SACParserCSS2TokenManager(stream);
/* 2499:2386 */     this.token = new Token();
/* 2500:2387 */     this.jj_ntk = -1;
/* 2501:2388 */     this.jj_gen = 0;
/* 2502:2389 */     for (int i = 0; i < 98; i++) {
/* 2503:2389 */       this.jj_la1[i] = -1;
/* 2504:     */     }
/* 2505:2390 */     for (int i = 0; i < this.jj_2_rtns.length; i++) {
/* 2506:2390 */       this.jj_2_rtns[i] = new JJCalls();
/* 2507:     */     }
/* 2508:     */   }
/* 2509:     */   
/* 2510:     */   public void ReInit(CharStream stream)
/* 2511:     */   {
/* 2512:2394 */     this.token_source.ReInit(stream);
/* 2513:2395 */     this.token = new Token();
/* 2514:2396 */     this.jj_ntk = -1;
/* 2515:2397 */     this.jj_gen = 0;
/* 2516:2398 */     for (int i = 0; i < 98; i++) {
/* 2517:2398 */       this.jj_la1[i] = -1;
/* 2518:     */     }
/* 2519:2399 */     for (int i = 0; i < this.jj_2_rtns.length; i++) {
/* 2520:2399 */       this.jj_2_rtns[i] = new JJCalls();
/* 2521:     */     }
/* 2522:     */   }
/* 2523:     */   
/* 2524:     */   public SACParserCSS2(SACParserCSS2TokenManager tm)
/* 2525:     */   {
/* 2526:2403 */     this.token_source = tm;
/* 2527:2404 */     this.token = new Token();
/* 2528:2405 */     this.jj_ntk = -1;
/* 2529:2406 */     this.jj_gen = 0;
/* 2530:2407 */     for (int i = 0; i < 98; i++) {
/* 2531:2407 */       this.jj_la1[i] = -1;
/* 2532:     */     }
/* 2533:2408 */     for (int i = 0; i < this.jj_2_rtns.length; i++) {
/* 2534:2408 */       this.jj_2_rtns[i] = new JJCalls();
/* 2535:     */     }
/* 2536:     */   }
/* 2537:     */   
/* 2538:     */   public void ReInit(SACParserCSS2TokenManager tm)
/* 2539:     */   {
/* 2540:2412 */     this.token_source = tm;
/* 2541:2413 */     this.token = new Token();
/* 2542:2414 */     this.jj_ntk = -1;
/* 2543:2415 */     this.jj_gen = 0;
/* 2544:2416 */     for (int i = 0; i < 98; i++) {
/* 2545:2416 */       this.jj_la1[i] = -1;
/* 2546:     */     }
/* 2547:2417 */     for (int i = 0; i < this.jj_2_rtns.length; i++) {
/* 2548:2417 */       this.jj_2_rtns[i] = new JJCalls();
/* 2549:     */     }
/* 2550:     */   }
/* 2551:     */   
/* 2552:     */   private final Token jj_consume_token(int kind)
/* 2553:     */     throws ParseException
/* 2554:     */   {
/* 2555:     */     Token oldToken;
/* 2556:2422 */     if ((oldToken = this.token).next != null) {
/* 2557:2422 */       this.token = this.token.next;
/* 2558:     */     } else {
/* 2559:2423 */       this.token = (this.token.next = this.token_source.getNextToken());
/* 2560:     */     }
/* 2561:2424 */     this.jj_ntk = -1;
/* 2562:2425 */     if (this.token.kind == kind)
/* 2563:     */     {
/* 2564:2426 */       this.jj_gen += 1;
/* 2565:2427 */       if (++this.jj_gc > 100)
/* 2566:     */       {
/* 2567:2428 */         this.jj_gc = 0;
/* 2568:2429 */         for (int i = 0; i < this.jj_2_rtns.length; i++)
/* 2569:     */         {
/* 2570:2430 */           JJCalls c = this.jj_2_rtns[i];
/* 2571:2431 */           while (c != null)
/* 2572:     */           {
/* 2573:2432 */             if (c.gen < this.jj_gen) {
/* 2574:2432 */               c.first = null;
/* 2575:     */             }
/* 2576:2433 */             c = c.next;
/* 2577:     */           }
/* 2578:     */         }
/* 2579:     */       }
/* 2580:2437 */       return this.token;
/* 2581:     */     }
/* 2582:2439 */     this.token = oldToken;
/* 2583:2440 */     this.jj_kind = kind;
/* 2584:2441 */     throw generateParseException();
/* 2585:     */   }
/* 2586:     */   
/* 2587:2445 */   private final LookaheadSuccess jj_ls = new LookaheadSuccess(null);
/* 2588:     */   
/* 2589:     */   private final boolean jj_scan_token(int kind)
/* 2590:     */   {
/* 2591:2447 */     if (this.jj_scanpos == this.jj_lastpos)
/* 2592:     */     {
/* 2593:2448 */       this.jj_la -= 1;
/* 2594:2449 */       if (this.jj_scanpos.next == null) {
/* 2595:2450 */         this.jj_lastpos = (this.jj_scanpos = this.jj_scanpos.next = this.token_source.getNextToken());
/* 2596:     */       } else {
/* 2597:2452 */         this.jj_lastpos = (this.jj_scanpos = this.jj_scanpos.next);
/* 2598:     */       }
/* 2599:     */     }
/* 2600:     */     else
/* 2601:     */     {
/* 2602:2455 */       this.jj_scanpos = this.jj_scanpos.next;
/* 2603:     */     }
/* 2604:2457 */     if (this.jj_rescan)
/* 2605:     */     {
/* 2606:2458 */       int i = 0;
/* 2607:2458 */       for (Token tok = this.token; (tok != null) && (tok != this.jj_scanpos); tok = tok.next) {
/* 2608:2459 */         i++;
/* 2609:     */       }
/* 2610:2460 */       if (tok != null) {
/* 2611:2460 */         jj_add_error_token(kind, i);
/* 2612:     */       }
/* 2613:     */     }
/* 2614:2462 */     if (this.jj_scanpos.kind != kind) {
/* 2615:2462 */       return true;
/* 2616:     */     }
/* 2617:2463 */     if ((this.jj_la == 0) && (this.jj_scanpos == this.jj_lastpos)) {
/* 2618:2463 */       throw this.jj_ls;
/* 2619:     */     }
/* 2620:2464 */     return false;
/* 2621:     */   }
/* 2622:     */   
/* 2623:     */   public final Token getNextToken()
/* 2624:     */   {
/* 2625:2468 */     if (this.token.next != null) {
/* 2626:2468 */       this.token = this.token.next;
/* 2627:     */     } else {
/* 2628:2469 */       this.token = (this.token.next = this.token_source.getNextToken());
/* 2629:     */     }
/* 2630:2470 */     this.jj_ntk = -1;
/* 2631:2471 */     this.jj_gen += 1;
/* 2632:2472 */     return this.token;
/* 2633:     */   }
/* 2634:     */   
/* 2635:     */   public final Token getToken(int index)
/* 2636:     */   {
/* 2637:2476 */     Token t = this.lookingAhead ? this.jj_scanpos : this.token;
/* 2638:2477 */     for (int i = 0; i < index; i++) {
/* 2639:2478 */       if (t.next != null) {
/* 2640:2478 */         t = t.next;
/* 2641:     */       } else {
/* 2642:2479 */         t = t.next = this.token_source.getNextToken();
/* 2643:     */       }
/* 2644:     */     }
/* 2645:2481 */     return t;
/* 2646:     */   }
/* 2647:     */   
/* 2648:     */   private final int jj_ntk()
/* 2649:     */   {
/* 2650:2485 */     if ((this.jj_nt = this.token.next) == null) {
/* 2651:2486 */       return this.jj_ntk = (this.token.next = this.token_source.getNextToken()).kind;
/* 2652:     */     }
/* 2653:2488 */     return this.jj_ntk = this.jj_nt.kind;
/* 2654:     */   }
/* 2655:     */   
/* 2656:2491 */   private Vector jj_expentries = new Vector();
/* 2657:     */   private int[] jj_expentry;
/* 2658:2493 */   private int jj_kind = -1;
/* 2659:2494 */   private int[] jj_lasttokens = new int[100];
/* 2660:     */   private int jj_endpos;
/* 2661:     */   
/* 2662:     */   private void jj_add_error_token(int kind, int pos)
/* 2663:     */   {
/* 2664:2498 */     if (pos >= 100) {
/* 2665:2498 */       return;
/* 2666:     */     }
/* 2667:2499 */     if (pos == this.jj_endpos + 1)
/* 2668:     */     {
/* 2669:2500 */       this.jj_lasttokens[(this.jj_endpos++)] = kind;
/* 2670:     */     }
/* 2671:2501 */     else if (this.jj_endpos != 0)
/* 2672:     */     {
/* 2673:2502 */       this.jj_expentry = new int[this.jj_endpos];
/* 2674:2503 */       for (int i = 0; i < this.jj_endpos; i++) {
/* 2675:2504 */         this.jj_expentry[i] = this.jj_lasttokens[i];
/* 2676:     */       }
/* 2677:2506 */       boolean exists = false;
/* 2678:2507 */       for (Enumeration e = this.jj_expentries.elements(); e.hasMoreElements();)
/* 2679:     */       {
/* 2680:2508 */         int[] oldentry = (int[])e.nextElement();
/* 2681:2509 */         if (oldentry.length == this.jj_expentry.length)
/* 2682:     */         {
/* 2683:2510 */           exists = true;
/* 2684:2511 */           for (int i = 0; i < this.jj_expentry.length; i++) {
/* 2685:2512 */             if (oldentry[i] != this.jj_expentry[i])
/* 2686:     */             {
/* 2687:2513 */               exists = false;
/* 2688:2514 */               break;
/* 2689:     */             }
/* 2690:     */           }
/* 2691:2517 */           if (exists) {
/* 2692:     */             break;
/* 2693:     */           }
/* 2694:     */         }
/* 2695:     */       }
/* 2696:2520 */       if (!exists) {
/* 2697:2520 */         this.jj_expentries.addElement(this.jj_expentry);
/* 2698:     */       }
/* 2699:2521 */       if (pos != 0)
/* 2700:     */       {
/* 2701:2521 */         int tmp205_204 = pos;this.jj_endpos = tmp205_204;this.jj_lasttokens[(tmp205_204 - 1)] = kind;
/* 2702:     */       }
/* 2703:     */     }
/* 2704:     */   }
/* 2705:     */   
/* 2706:     */   public ParseException generateParseException()
/* 2707:     */   {
/* 2708:2526 */     this.jj_expentries.removeAllElements();
/* 2709:2527 */     boolean[] la1tokens = new boolean[78];
/* 2710:2528 */     for (int i = 0; i < 78; i++) {
/* 2711:2529 */       la1tokens[i] = false;
/* 2712:     */     }
/* 2713:2531 */     if (this.jj_kind >= 0)
/* 2714:     */     {
/* 2715:2532 */       la1tokens[this.jj_kind] = true;
/* 2716:2533 */       this.jj_kind = -1;
/* 2717:     */     }
/* 2718:2535 */     for (int i = 0; i < 98; i++) {
/* 2719:2536 */       if (this.jj_la1[i] == this.jj_gen) {
/* 2720:2537 */         for (int j = 0; j < 32; j++)
/* 2721:     */         {
/* 2722:2538 */           if ((jj_la1_0[i] & 1 << j) != 0) {
/* 2723:2539 */             la1tokens[j] = true;
/* 2724:     */           }
/* 2725:2541 */           if ((jj_la1_1[i] & 1 << j) != 0) {
/* 2726:2542 */             la1tokens[(32 + j)] = true;
/* 2727:     */           }
/* 2728:2544 */           if ((jj_la1_2[i] & 1 << j) != 0) {
/* 2729:2545 */             la1tokens[(64 + j)] = true;
/* 2730:     */           }
/* 2731:     */         }
/* 2732:     */       }
/* 2733:     */     }
/* 2734:2550 */     for (int i = 0; i < 78; i++) {
/* 2735:2551 */       if (la1tokens[i] != 0)
/* 2736:     */       {
/* 2737:2552 */         this.jj_expentry = new int[1];
/* 2738:2553 */         this.jj_expentry[0] = i;
/* 2739:2554 */         this.jj_expentries.addElement(this.jj_expentry);
/* 2740:     */       }
/* 2741:     */     }
/* 2742:2557 */     this.jj_endpos = 0;
/* 2743:2558 */     jj_rescan_token();
/* 2744:2559 */     jj_add_error_token(0, 0);
/* 2745:2560 */     int[][] exptokseq = new int[this.jj_expentries.size()][];
/* 2746:2561 */     for (int i = 0; i < this.jj_expentries.size(); i++) {
/* 2747:2562 */       exptokseq[i] = ((int[])(int[])this.jj_expentries.elementAt(i));
/* 2748:     */     }
/* 2749:2564 */     return new ParseException(this.token, exptokseq, tokenImage);
/* 2750:     */   }
/* 2751:     */   
/* 2752:     */   private final void jj_rescan_token()
/* 2753:     */   {
/* 2754:2574 */     this.jj_rescan = true;
/* 2755:2575 */     for (int i = 0; i < 2; i++) {
/* 2756:     */       try
/* 2757:     */       {
/* 2758:2577 */         JJCalls p = this.jj_2_rtns[i];
/* 2759:     */         do
/* 2760:     */         {
/* 2761:2579 */           if (p.gen > this.jj_gen)
/* 2762:     */           {
/* 2763:2580 */             this.jj_la = p.arg;this.jj_lastpos = (this.jj_scanpos = p.first);
/* 2764:2581 */             switch (i)
/* 2765:     */             {
/* 2766:     */             case 0: 
/* 2767:2582 */               jj_3_1(); break;
/* 2768:     */             case 1: 
/* 2769:2583 */               jj_3_2();
/* 2770:     */             }
/* 2771:     */           }
/* 2772:2586 */           p = p.next;
/* 2773:2587 */         } while (p != null);
/* 2774:     */       }
/* 2775:     */       catch (LookaheadSuccess ls) {}
/* 2776:     */     }
/* 2777:2590 */     this.jj_rescan = false;
/* 2778:     */   }
/* 2779:     */   
/* 2780:     */   private final void jj_save(int index, int xla)
/* 2781:     */   {
/* 2782:2594 */     JJCalls p = this.jj_2_rtns[index];
/* 2783:2595 */     while (p.gen > this.jj_gen)
/* 2784:     */     {
/* 2785:2596 */       if (p.next == null)
/* 2786:     */       {
/* 2787:2596 */         p = p.next = new JJCalls(); break;
/* 2788:     */       }
/* 2789:2597 */       p = p.next;
/* 2790:     */     }
/* 2791:2599 */     p.gen = (this.jj_gen + xla - this.jj_la);p.first = this.token;p.arg = xla;
/* 2792:     */   }
/* 2793:     */   
/* 2794:     */   public final void enable_tracing() {}
/* 2795:     */   
/* 2796:     */   public final void disable_tracing() {}
/* 2797:     */   
/* 2798:     */   static final class JJCalls
/* 2799:     */   {
/* 2800:     */     int gen;
/* 2801:     */     Token first;
/* 2802:     */     int arg;
/* 2803:     */     JJCalls next;
/* 2804:     */   }
/* 2805:     */   
/* 2806:     */   private static final class LookaheadSuccess
/* 2807:     */     extends Error
/* 2808:     */   {}
/* 2809:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.steadystate.css.parser.SACParserCSS2
 * JD-Core Version:    0.7.0.1
 */