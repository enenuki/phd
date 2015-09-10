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
/*   16:     */ public class SACParserCSSmobileOKBasic1
/*   17:     */   extends AbstractSACParser
/*   18:     */   implements Parser, SACParserCSSmobileOKBasic1Constants
/*   19:     */ {
/*   20:  18 */   private boolean _quiet = true;
/*   21:     */   public SACParserCSSmobileOKBasic1TokenManager token_source;
/*   22:     */   public Token token;
/*   23:     */   public Token jj_nt;
/*   24:     */   private int jj_ntk;
/*   25:     */   private Token jj_scanpos;
/*   26:     */   private Token jj_lastpos;
/*   27:     */   private int jj_la;
/*   28:     */   
/*   29:     */   public SACParserCSSmobileOKBasic1()
/*   30:     */   {
/*   31:  21 */     this((CharStream)null);
/*   32:     */   }
/*   33:     */   
/*   34:     */   public String getParserVersion()
/*   35:     */   {
/*   36:  25 */     return "http://www.w3.org/TR/mobileOK-basic10-tests/#validity";
/*   37:     */   }
/*   38:     */   
/*   39:     */   protected String getGrammarUri()
/*   40:     */   {
/*   41:  30 */     return "CSSgrammarMobileOKBasic1.0.txt";
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
/*   54:  46 */       handleStartDocument();
/*   55:  47 */       styleSheetRuleList();
/*   56:  48 */       jj_consume_token(0);
/*   57:     */     }
/*   58:     */     finally
/*   59:     */     {
/*   60:  50 */       handleEndDocument();
/*   61:     */     }
/*   62:     */   }
/*   63:     */   
/*   64:     */   public final void styleSheetRuleList()
/*   65:     */     throws ParseException
/*   66:     */   {
/*   67:     */     for (;;)
/*   68:     */     {
/*   69:  59 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*   70:     */       {
/*   71:     */       case 1: 
/*   72:     */       case 27: 
/*   73:     */       case 28: 
/*   74:     */         break;
/*   75:     */       default: 
/*   76:  66 */         this.jj_la1[0] = this.jj_gen;
/*   77:  67 */         break;
/*   78:     */       }
/*   79:  69 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*   80:     */       {
/*   81:     */       case 1: 
/*   82:  71 */         jj_consume_token(1);
/*   83:  72 */         break;
/*   84:     */       case 27: 
/*   85:  74 */         jj_consume_token(27);
/*   86:  75 */         break;
/*   87:     */       case 28: 
/*   88:  77 */         jj_consume_token(28);
/*   89:     */       }
/*   90:     */     }
/*   91:  80 */     this.jj_la1[1] = this.jj_gen;
/*   92:  81 */     jj_consume_token(-1);
/*   93:  82 */     throw new ParseException();
/*   94:  87 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*   95:     */     {
/*   96:     */     case 29: 
/*   97:     */       break;
/*   98:     */     default: 
/*   99:  92 */       this.jj_la1[2] = this.jj_gen;
/*  100:  93 */       break;
/*  101:     */     }
/*  102:  95 */     importRule();
/*  103:     */     for (;;)
/*  104:     */     {
/*  105:  98 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  106:     */       {
/*  107:     */       case 1: 
/*  108:     */       case 27: 
/*  109:     */       case 28: 
/*  110:     */         break;
/*  111:     */       default: 
/*  112: 105 */         this.jj_la1[3] = this.jj_gen;
/*  113: 106 */         break;
/*  114:     */       }
/*  115: 108 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  116:     */       {
/*  117:     */       case 1: 
/*  118: 110 */         jj_consume_token(1);
/*  119: 111 */         break;
/*  120:     */       case 27: 
/*  121: 113 */         jj_consume_token(27);
/*  122: 114 */         break;
/*  123:     */       case 28: 
/*  124: 116 */         jj_consume_token(28);
/*  125:     */       }
/*  126:     */     }
/*  127: 119 */     this.jj_la1[4] = this.jj_gen;
/*  128: 120 */     jj_consume_token(-1);
/*  129: 121 */     throw new ParseException();
/*  130: 127 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  131:     */     {
/*  132:     */     case 3: 
/*  133:     */     case 4: 
/*  134:     */     case 5: 
/*  135:     */     case 6: 
/*  136:     */     case 7: 
/*  137:     */     case 8: 
/*  138:     */     case 9: 
/*  139:     */     case 13: 
/*  140:     */     case 16: 
/*  141:     */     case 30: 
/*  142:     */     case 32: 
/*  143:     */       break;
/*  144:     */     case 10: 
/*  145:     */     case 11: 
/*  146:     */     case 12: 
/*  147:     */     case 14: 
/*  148:     */     case 15: 
/*  149:     */     case 17: 
/*  150:     */     case 18: 
/*  151:     */     case 19: 
/*  152:     */     case 20: 
/*  153:     */     case 21: 
/*  154:     */     case 22: 
/*  155:     */     case 23: 
/*  156:     */     case 24: 
/*  157:     */     case 25: 
/*  158:     */     case 26: 
/*  159:     */     case 27: 
/*  160:     */     case 28: 
/*  161:     */     case 29: 
/*  162:     */     case 31: 
/*  163:     */     default: 
/*  164: 142 */       this.jj_la1[5] = this.jj_gen;
/*  165: 143 */       break;
/*  166:     */     }
/*  167: 145 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  168:     */     {
/*  169:     */     case 3: 
/*  170:     */     case 4: 
/*  171:     */     case 5: 
/*  172:     */     case 6: 
/*  173:     */     case 7: 
/*  174:     */     case 8: 
/*  175:     */     case 9: 
/*  176:     */     case 13: 
/*  177:     */     case 16: 
/*  178: 155 */       styleRule();
/*  179: 156 */       break;
/*  180:     */     case 30: 
/*  181: 158 */       mediaRule();
/*  182: 159 */       break;
/*  183:     */     case 32: 
/*  184: 161 */       unknownRule();
/*  185: 162 */       break;
/*  186:     */     case 10: 
/*  187:     */     case 11: 
/*  188:     */     case 12: 
/*  189:     */     case 14: 
/*  190:     */     case 15: 
/*  191:     */     case 17: 
/*  192:     */     case 18: 
/*  193:     */     case 19: 
/*  194:     */     case 20: 
/*  195:     */     case 21: 
/*  196:     */     case 22: 
/*  197:     */     case 23: 
/*  198:     */     case 24: 
/*  199:     */     case 25: 
/*  200:     */     case 26: 
/*  201:     */     case 27: 
/*  202:     */     case 28: 
/*  203:     */     case 29: 
/*  204:     */     case 31: 
/*  205:     */     default: 
/*  206: 164 */       this.jj_la1[6] = this.jj_gen;
/*  207: 165 */       jj_consume_token(-1);
/*  208: 166 */       throw new ParseException();
/*  209:     */     }
/*  210:     */     for (;;)
/*  211:     */     {
/*  212: 170 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  213:     */       {
/*  214:     */       case 1: 
/*  215:     */       case 27: 
/*  216:     */       case 28: 
/*  217:     */         break;
/*  218:     */       default: 
/*  219: 177 */         this.jj_la1[7] = this.jj_gen;
/*  220: 178 */         break;
/*  221:     */       }
/*  222: 180 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  223:     */       {
/*  224:     */       case 1: 
/*  225: 182 */         jj_consume_token(1);
/*  226: 183 */         break;
/*  227:     */       case 27: 
/*  228: 185 */         jj_consume_token(27);
/*  229: 186 */         break;
/*  230:     */       case 28: 
/*  231: 188 */         jj_consume_token(28);
/*  232:     */       }
/*  233:     */     }
/*  234: 191 */     this.jj_la1[8] = this.jj_gen;
/*  235: 192 */     jj_consume_token(-1);
/*  236: 193 */     throw new ParseException();
/*  237: 199 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  238:     */     {
/*  239:     */     case 3: 
/*  240:     */     case 4: 
/*  241:     */     case 5: 
/*  242:     */     case 6: 
/*  243:     */     case 7: 
/*  244:     */     case 8: 
/*  245:     */     case 9: 
/*  246:     */     case 13: 
/*  247:     */     case 16: 
/*  248:     */     case 29: 
/*  249:     */     case 30: 
/*  250:     */     case 32: 
/*  251:     */       break;
/*  252:     */     case 10: 
/*  253:     */     case 11: 
/*  254:     */     case 12: 
/*  255:     */     case 14: 
/*  256:     */     case 15: 
/*  257:     */     case 17: 
/*  258:     */     case 18: 
/*  259:     */     case 19: 
/*  260:     */     case 20: 
/*  261:     */     case 21: 
/*  262:     */     case 22: 
/*  263:     */     case 23: 
/*  264:     */     case 24: 
/*  265:     */     case 25: 
/*  266:     */     case 26: 
/*  267:     */     case 27: 
/*  268:     */     case 28: 
/*  269:     */     case 31: 
/*  270:     */     default: 
/*  271: 215 */       this.jj_la1[9] = this.jj_gen;
/*  272: 216 */       break;
/*  273:     */     }
/*  274: 218 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  275:     */     {
/*  276:     */     case 3: 
/*  277:     */     case 4: 
/*  278:     */     case 5: 
/*  279:     */     case 6: 
/*  280:     */     case 7: 
/*  281:     */     case 8: 
/*  282:     */     case 9: 
/*  283:     */     case 13: 
/*  284:     */     case 16: 
/*  285: 228 */       styleRule();
/*  286: 229 */       break;
/*  287:     */     case 30: 
/*  288: 231 */       mediaRule();
/*  289: 232 */       break;
/*  290:     */     case 29: 
/*  291: 234 */       importRuleIgnored();
/*  292: 235 */       break;
/*  293:     */     case 32: 
/*  294: 237 */       unknownRule();
/*  295: 238 */       break;
/*  296:     */     case 10: 
/*  297:     */     case 11: 
/*  298:     */     case 12: 
/*  299:     */     case 14: 
/*  300:     */     case 15: 
/*  301:     */     case 17: 
/*  302:     */     case 18: 
/*  303:     */     case 19: 
/*  304:     */     case 20: 
/*  305:     */     case 21: 
/*  306:     */     case 22: 
/*  307:     */     case 23: 
/*  308:     */     case 24: 
/*  309:     */     case 25: 
/*  310:     */     case 26: 
/*  311:     */     case 27: 
/*  312:     */     case 28: 
/*  313:     */     case 31: 
/*  314:     */     default: 
/*  315: 240 */       this.jj_la1[10] = this.jj_gen;
/*  316: 241 */       jj_consume_token(-1);
/*  317: 242 */       throw new ParseException();
/*  318:     */     }
/*  319:     */     for (;;)
/*  320:     */     {
/*  321: 246 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  322:     */       {
/*  323:     */       case 1: 
/*  324:     */       case 27: 
/*  325:     */       case 28: 
/*  326:     */         break;
/*  327:     */       default: 
/*  328: 253 */         this.jj_la1[11] = this.jj_gen;
/*  329: 254 */         break;
/*  330:     */       }
/*  331: 256 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  332:     */       {
/*  333:     */       case 1: 
/*  334: 258 */         jj_consume_token(1);
/*  335: 259 */         break;
/*  336:     */       case 27: 
/*  337: 261 */         jj_consume_token(27);
/*  338: 262 */         break;
/*  339:     */       case 28: 
/*  340: 264 */         jj_consume_token(28);
/*  341:     */       }
/*  342:     */     }
/*  343: 267 */     this.jj_la1[12] = this.jj_gen;
/*  344: 268 */     jj_consume_token(-1);
/*  345: 269 */     throw new ParseException();
/*  346:     */   }
/*  347:     */   
/*  348:     */   public final void styleSheetRuleSingle()
/*  349:     */     throws ParseException
/*  350:     */   {
/*  351: 279 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  352:     */     {
/*  353:     */     case 29: 
/*  354: 281 */       importRule();
/*  355: 282 */       break;
/*  356:     */     case 3: 
/*  357:     */     case 4: 
/*  358:     */     case 5: 
/*  359:     */     case 6: 
/*  360:     */     case 7: 
/*  361:     */     case 8: 
/*  362:     */     case 9: 
/*  363:     */     case 13: 
/*  364:     */     case 16: 
/*  365: 292 */       styleRule();
/*  366: 293 */       break;
/*  367:     */     case 30: 
/*  368: 295 */       mediaRule();
/*  369: 296 */       break;
/*  370:     */     case 32: 
/*  371: 298 */       unknownRule();
/*  372: 299 */       break;
/*  373:     */     case 10: 
/*  374:     */     case 11: 
/*  375:     */     case 12: 
/*  376:     */     case 14: 
/*  377:     */     case 15: 
/*  378:     */     case 17: 
/*  379:     */     case 18: 
/*  380:     */     case 19: 
/*  381:     */     case 20: 
/*  382:     */     case 21: 
/*  383:     */     case 22: 
/*  384:     */     case 23: 
/*  385:     */     case 24: 
/*  386:     */     case 25: 
/*  387:     */     case 26: 
/*  388:     */     case 27: 
/*  389:     */     case 28: 
/*  390:     */     case 31: 
/*  391:     */     default: 
/*  392: 301 */       this.jj_la1[13] = this.jj_gen;
/*  393: 302 */       jj_consume_token(-1);
/*  394: 303 */       throw new ParseException();
/*  395:     */     }
/*  396:     */   }
/*  397:     */   
/*  398:     */   public final void unknownRule()
/*  399:     */     throws ParseException
/*  400:     */   {
/*  401:     */     try
/*  402:     */     {
/*  403: 311 */       Token t = jj_consume_token(32);
/*  404: 312 */       String s = skip();
/*  405: 313 */       handleIgnorableAtRule(s);
/*  406:     */     }
/*  407:     */     catch (ParseException e)
/*  408:     */     {
/*  409: 315 */       getErrorHandler().error(toCSSParseException("invalidUnknownRule", e));
/*  410:     */     }
/*  411:     */   }
/*  412:     */   
/*  413:     */   public final void importRule()
/*  414:     */     throws ParseException
/*  415:     */   {
/*  416: 328 */     SACMediaListImpl ml = new SACMediaListImpl();
/*  417:     */     try
/*  418:     */     {
/*  419: 330 */       jj_consume_token(29);
/*  420:     */       for (;;)
/*  421:     */       {
/*  422: 333 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  423:     */         {
/*  424:     */         case 1: 
/*  425:     */           break;
/*  426:     */         default: 
/*  427: 338 */           this.jj_la1[14] = this.jj_gen;
/*  428: 339 */           break;
/*  429:     */         }
/*  430: 341 */         jj_consume_token(1);
/*  431:     */       }
/*  432:     */       Token t;
/*  433: 343 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  434:     */       {
/*  435:     */       case 24: 
/*  436: 345 */         t = jj_consume_token(24);
/*  437: 346 */         break;
/*  438:     */       case 26: 
/*  439: 348 */         t = jj_consume_token(26);
/*  440: 349 */         break;
/*  441:     */       default: 
/*  442: 351 */         this.jj_la1[15] = this.jj_gen;
/*  443: 352 */         jj_consume_token(-1);
/*  444: 353 */         throw new ParseException();
/*  445:     */       }
/*  446:     */       for (;;)
/*  447:     */       {
/*  448: 357 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  449:     */         {
/*  450:     */         case 1: 
/*  451:     */           break;
/*  452:     */         default: 
/*  453: 362 */           this.jj_la1[16] = this.jj_gen;
/*  454: 363 */           break;
/*  455:     */         }
/*  456: 365 */         jj_consume_token(1);
/*  457:     */       }
/*  458: 367 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  459:     */       {
/*  460:     */       case 3: 
/*  461: 369 */         mediaList(ml);
/*  462: 370 */         break;
/*  463:     */       default: 
/*  464: 372 */         this.jj_la1[17] = this.jj_gen;
/*  465:     */       }
/*  466: 375 */       jj_consume_token(14);
/*  467: 376 */       handleImportStyle(unescape(t.image), new SACMediaListImpl(), null);
/*  468:     */     }
/*  469:     */     catch (CSSParseException e)
/*  470:     */     {
/*  471: 378 */       getErrorHandler().error(e);
/*  472: 379 */       error_skipAtRule();
/*  473:     */     }
/*  474:     */     catch (ParseException e)
/*  475:     */     {
/*  476: 381 */       getErrorHandler().error(toCSSParseException("invalidImportRule", e));
/*  477:     */       
/*  478: 383 */       error_skipAtRule();
/*  479:     */     }
/*  480:     */   }
/*  481:     */   
/*  482:     */   public final void importRuleIgnored()
/*  483:     */     throws ParseException
/*  484:     */   {
/*  485: 389 */     SACMediaListImpl ml = new SACMediaListImpl();
/*  486: 390 */     ParseException e = generateParseException();
/*  487:     */     try
/*  488:     */     {
/*  489: 392 */       jj_consume_token(29);
/*  490:     */       for (;;)
/*  491:     */       {
/*  492: 395 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  493:     */         {
/*  494:     */         case 1: 
/*  495:     */           break;
/*  496:     */         default: 
/*  497: 400 */           this.jj_la1[18] = this.jj_gen;
/*  498: 401 */           break;
/*  499:     */         }
/*  500: 403 */         jj_consume_token(1);
/*  501:     */       }
/*  502:     */       Token t;
/*  503: 405 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  504:     */       {
/*  505:     */       case 24: 
/*  506: 407 */         t = jj_consume_token(24);
/*  507: 408 */         break;
/*  508:     */       case 26: 
/*  509: 410 */         t = jj_consume_token(26);
/*  510: 411 */         break;
/*  511:     */       default: 
/*  512: 413 */         this.jj_la1[19] = this.jj_gen;
/*  513: 414 */         jj_consume_token(-1);
/*  514: 415 */         throw new ParseException();
/*  515:     */       }
/*  516:     */       for (;;)
/*  517:     */       {
/*  518: 419 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  519:     */         {
/*  520:     */         case 1: 
/*  521:     */           break;
/*  522:     */         default: 
/*  523: 424 */           this.jj_la1[20] = this.jj_gen;
/*  524: 425 */           break;
/*  525:     */         }
/*  526: 427 */         jj_consume_token(1);
/*  527:     */       }
/*  528: 429 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  529:     */       {
/*  530:     */       case 3: 
/*  531: 431 */         mediaList(ml);
/*  532: 432 */         break;
/*  533:     */       default: 
/*  534: 434 */         this.jj_la1[21] = this.jj_gen;
/*  535:     */       }
/*  536: 437 */       jj_consume_token(14);
/*  537:     */     }
/*  538:     */     finally
/*  539:     */     {
/*  540: 439 */       getErrorHandler().error(toCSSParseException("invalidImportRuleIgnored", e));
/*  541:     */     }
/*  542:     */   }
/*  543:     */   
/*  544:     */   public final void mediaRule()
/*  545:     */     throws ParseException
/*  546:     */   {
/*  547: 449 */     boolean start = false;
/*  548: 450 */     SACMediaListImpl ml = new SACMediaListImpl();
/*  549:     */     try
/*  550:     */     {
/*  551: 452 */       jj_consume_token(30);
/*  552:     */       for (;;)
/*  553:     */       {
/*  554: 455 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  555:     */         {
/*  556:     */         case 1: 
/*  557:     */           break;
/*  558:     */         default: 
/*  559: 460 */           this.jj_la1[22] = this.jj_gen;
/*  560: 461 */           break;
/*  561:     */         }
/*  562: 463 */         jj_consume_token(1);
/*  563:     */       }
/*  564: 465 */       mediaList(ml);
/*  565: 466 */       start = true;
/*  566: 467 */       handleStartMedia(ml);
/*  567: 468 */       jj_consume_token(10);
/*  568:     */       for (;;)
/*  569:     */       {
/*  570: 471 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  571:     */         {
/*  572:     */         case 1: 
/*  573:     */           break;
/*  574:     */         default: 
/*  575: 476 */           this.jj_la1[23] = this.jj_gen;
/*  576: 477 */           break;
/*  577:     */         }
/*  578: 479 */         jj_consume_token(1);
/*  579:     */       }
/*  580: 481 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  581:     */       {
/*  582:     */       case 3: 
/*  583:     */       case 4: 
/*  584:     */       case 5: 
/*  585:     */       case 6: 
/*  586:     */       case 7: 
/*  587:     */       case 8: 
/*  588:     */       case 9: 
/*  589:     */       case 13: 
/*  590:     */       case 16: 
/*  591:     */       case 32: 
/*  592: 492 */         mediaRuleList();
/*  593: 493 */         break;
/*  594:     */       case 10: 
/*  595:     */       case 11: 
/*  596:     */       case 12: 
/*  597:     */       case 14: 
/*  598:     */       case 15: 
/*  599:     */       case 17: 
/*  600:     */       case 18: 
/*  601:     */       case 19: 
/*  602:     */       case 20: 
/*  603:     */       case 21: 
/*  604:     */       case 22: 
/*  605:     */       case 23: 
/*  606:     */       case 24: 
/*  607:     */       case 25: 
/*  608:     */       case 26: 
/*  609:     */       case 27: 
/*  610:     */       case 28: 
/*  611:     */       case 29: 
/*  612:     */       case 30: 
/*  613:     */       case 31: 
/*  614:     */       default: 
/*  615: 495 */         this.jj_la1[24] = this.jj_gen;
/*  616:     */       }
/*  617: 498 */       jj_consume_token(11);
/*  618:     */     }
/*  619:     */     catch (CSSParseException e)
/*  620:     */     {
/*  621: 500 */       getErrorHandler().error(e);
/*  622: 501 */       error_skipblock();
/*  623:     */     }
/*  624:     */     catch (ParseException e)
/*  625:     */     {
/*  626: 503 */       CSSParseException cpe = toCSSParseException("invalidMediaRule", e);
/*  627: 504 */       getErrorHandler().error(cpe);
/*  628: 505 */       getErrorHandler().warning(createSkipWarning("ignoringRule", cpe));
/*  629: 506 */       error_skipblock();
/*  630:     */     }
/*  631:     */     finally
/*  632:     */     {
/*  633: 508 */       if (start) {
/*  634: 509 */         handleEndMedia(ml);
/*  635:     */       }
/*  636:     */     }
/*  637:     */   }
/*  638:     */   
/*  639:     */   public final void mediaList(SACMediaListImpl ml)
/*  640:     */     throws ParseException
/*  641:     */   {
/*  642:     */     try
/*  643:     */     {
/*  644: 517 */       String s = medium();
/*  645:     */       for (;;)
/*  646:     */       {
/*  647: 520 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  648:     */         {
/*  649:     */         case 12: 
/*  650:     */           break;
/*  651:     */         default: 
/*  652: 525 */           this.jj_la1[25] = this.jj_gen;
/*  653: 526 */           break;
/*  654:     */         }
/*  655: 528 */         jj_consume_token(12);
/*  656:     */         for (;;)
/*  657:     */         {
/*  658: 531 */           switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  659:     */           {
/*  660:     */           case 1: 
/*  661:     */             break;
/*  662:     */           default: 
/*  663: 536 */             this.jj_la1[26] = this.jj_gen;
/*  664: 537 */             break;
/*  665:     */           }
/*  666: 539 */           jj_consume_token(1);
/*  667:     */         }
/*  668: 541 */         ml.add(s);
/*  669: 542 */         s = medium();
/*  670:     */       }
/*  671: 544 */       ml.add(s);
/*  672:     */     }
/*  673:     */     catch (ParseException e)
/*  674:     */     {
/*  675: 546 */       throw toCSSParseException("invalidMediaList", e);
/*  676:     */     }
/*  677:     */   }
/*  678:     */   
/*  679:     */   public final void mediaRuleList()
/*  680:     */     throws ParseException
/*  681:     */   {
/*  682:     */     for (;;)
/*  683:     */     {
/*  684: 553 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  685:     */       {
/*  686:     */       case 3: 
/*  687:     */       case 4: 
/*  688:     */       case 5: 
/*  689:     */       case 6: 
/*  690:     */       case 7: 
/*  691:     */       case 8: 
/*  692:     */       case 9: 
/*  693:     */       case 13: 
/*  694:     */       case 16: 
/*  695: 563 */         styleRule();
/*  696: 564 */         break;
/*  697:     */       case 32: 
/*  698: 566 */         unknownRule();
/*  699: 567 */         break;
/*  700:     */       case 10: 
/*  701:     */       case 11: 
/*  702:     */       case 12: 
/*  703:     */       case 14: 
/*  704:     */       case 15: 
/*  705:     */       case 17: 
/*  706:     */       case 18: 
/*  707:     */       case 19: 
/*  708:     */       case 20: 
/*  709:     */       case 21: 
/*  710:     */       case 22: 
/*  711:     */       case 23: 
/*  712:     */       case 24: 
/*  713:     */       case 25: 
/*  714:     */       case 26: 
/*  715:     */       case 27: 
/*  716:     */       case 28: 
/*  717:     */       case 29: 
/*  718:     */       case 30: 
/*  719:     */       case 31: 
/*  720:     */       default: 
/*  721: 569 */         this.jj_la1[27] = this.jj_gen;
/*  722: 570 */         jj_consume_token(-1);
/*  723: 571 */         throw new ParseException();
/*  724:     */       }
/*  725:     */       for (;;)
/*  726:     */       {
/*  727: 575 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  728:     */         {
/*  729:     */         case 1: 
/*  730:     */           break;
/*  731:     */         default: 
/*  732: 580 */           this.jj_la1[28] = this.jj_gen;
/*  733: 581 */           break;
/*  734:     */         }
/*  735: 583 */         jj_consume_token(1);
/*  736:     */       }
/*  737: 585 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  738:     */       {
/*  739:     */       }
/*  740:     */     }
/*  741: 599 */     this.jj_la1[29] = this.jj_gen;
/*  742:     */   }
/*  743:     */   
/*  744:     */   public final void mediaRuleSingle()
/*  745:     */     throws ParseException
/*  746:     */   {
/*  747: 606 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  748:     */     {
/*  749:     */     case 3: 
/*  750:     */     case 4: 
/*  751:     */     case 5: 
/*  752:     */     case 6: 
/*  753:     */     case 7: 
/*  754:     */     case 8: 
/*  755:     */     case 9: 
/*  756:     */     case 13: 
/*  757:     */     case 16: 
/*  758: 616 */       styleRule();
/*  759: 617 */       break;
/*  760:     */     case 32: 
/*  761: 619 */       unknownRule();
/*  762: 620 */       break;
/*  763:     */     case 10: 
/*  764:     */     case 11: 
/*  765:     */     case 12: 
/*  766:     */     case 14: 
/*  767:     */     case 15: 
/*  768:     */     case 17: 
/*  769:     */     case 18: 
/*  770:     */     case 19: 
/*  771:     */     case 20: 
/*  772:     */     case 21: 
/*  773:     */     case 22: 
/*  774:     */     case 23: 
/*  775:     */     case 24: 
/*  776:     */     case 25: 
/*  777:     */     case 26: 
/*  778:     */     case 27: 
/*  779:     */     case 28: 
/*  780:     */     case 29: 
/*  781:     */     case 30: 
/*  782:     */     case 31: 
/*  783:     */     default: 
/*  784: 622 */       this.jj_la1[30] = this.jj_gen;
/*  785: 623 */       jj_consume_token(-1);
/*  786: 624 */       throw new ParseException();
/*  787:     */     }
/*  788:     */   }
/*  789:     */   
/*  790:     */   public final String medium()
/*  791:     */     throws ParseException
/*  792:     */   {
/*  793: 635 */     Token t = jj_consume_token(3);
/*  794:     */     for (;;)
/*  795:     */     {
/*  796: 638 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  797:     */       {
/*  798:     */       case 1: 
/*  799:     */         break;
/*  800:     */       default: 
/*  801: 643 */         this.jj_la1[31] = this.jj_gen;
/*  802: 644 */         break;
/*  803:     */       }
/*  804: 646 */       jj_consume_token(1);
/*  805:     */     }
/*  806: 648 */     handleMedium(t.image);
/*  807: 649 */     return t.image;
/*  808:     */   }
/*  809:     */   
/*  810:     */   public final LexicalUnit operator(LexicalUnit prev)
/*  811:     */     throws ParseException
/*  812:     */   {
/*  813:     */     Token t;
/*  814: 660 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  815:     */     {
/*  816:     */     case 17: 
/*  817: 662 */       t = jj_consume_token(17);
/*  818:     */       for (;;)
/*  819:     */       {
/*  820: 665 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  821:     */         {
/*  822:     */         case 1: 
/*  823:     */           break;
/*  824:     */         default: 
/*  825: 670 */           this.jj_la1[32] = this.jj_gen;
/*  826: 671 */           break;
/*  827:     */         }
/*  828: 673 */         jj_consume_token(1);
/*  829:     */       }
/*  830: 675 */       return new LexicalUnitImpl(prev, (short)4);
/*  831:     */     case 12: 
/*  832: 678 */       t = jj_consume_token(12);
/*  833:     */       for (;;)
/*  834:     */       {
/*  835: 681 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  836:     */         {
/*  837:     */         case 1: 
/*  838:     */           break;
/*  839:     */         default: 
/*  840: 686 */           this.jj_la1[33] = this.jj_gen;
/*  841: 687 */           break;
/*  842:     */         }
/*  843: 689 */         jj_consume_token(1);
/*  844:     */       }
/*  845: 691 */       return new LexicalUnitImpl(prev, (short)0);
/*  846:     */     }
/*  847: 694 */     this.jj_la1[34] = this.jj_gen;
/*  848: 695 */     jj_consume_token(-1);
/*  849: 696 */     throw new ParseException();
/*  850:     */   }
/*  851:     */   
/*  852:     */   public final char unaryOperator()
/*  853:     */     throws ParseException
/*  854:     */   {
/*  855: 707 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  856:     */     {
/*  857:     */     case 19: 
/*  858: 709 */       jj_consume_token(19);
/*  859: 710 */       return '-';
/*  860:     */     case 18: 
/*  861: 713 */       jj_consume_token(18);
/*  862: 714 */       return '+';
/*  863:     */     }
/*  864: 717 */     this.jj_la1[35] = this.jj_gen;
/*  865: 718 */     jj_consume_token(-1);
/*  866: 719 */     throw new ParseException();
/*  867:     */   }
/*  868:     */   
/*  869:     */   public final String property()
/*  870:     */     throws ParseException
/*  871:     */   {
/*  872: 731 */     Token t = jj_consume_token(3);
/*  873:     */     for (;;)
/*  874:     */     {
/*  875: 734 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  876:     */       {
/*  877:     */       case 1: 
/*  878:     */         break;
/*  879:     */       default: 
/*  880: 739 */         this.jj_la1[36] = this.jj_gen;
/*  881: 740 */         break;
/*  882:     */       }
/*  883: 742 */       jj_consume_token(1);
/*  884:     */     }
/*  885: 744 */     return unescape(t.image);
/*  886:     */   }
/*  887:     */   
/*  888:     */   public final void styleRule()
/*  889:     */     throws ParseException
/*  890:     */   {
/*  891: 755 */     SelectorList selList = null;
/*  892: 756 */     boolean start = false;
/*  893: 757 */     boolean noError = true;
/*  894:     */     try
/*  895:     */     {
/*  896: 759 */       selList = selectorList();
/*  897: 760 */       jj_consume_token(10);
/*  898:     */       for (;;)
/*  899:     */       {
/*  900: 763 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  901:     */         {
/*  902:     */         case 1: 
/*  903:     */           break;
/*  904:     */         default: 
/*  905: 768 */           this.jj_la1[37] = this.jj_gen;
/*  906: 769 */           break;
/*  907:     */         }
/*  908: 771 */         jj_consume_token(1);
/*  909:     */       }
/*  910: 773 */       start = true;
/*  911: 774 */       handleStartSelector(selList);
/*  912: 775 */       declaration();
/*  913:     */       for (;;)
/*  914:     */       {
/*  915: 778 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  916:     */         {
/*  917:     */         case 14: 
/*  918:     */           break;
/*  919:     */         default: 
/*  920: 783 */           this.jj_la1[38] = this.jj_gen;
/*  921: 784 */           break;
/*  922:     */         }
/*  923: 786 */         jj_consume_token(14);
/*  924:     */         for (;;)
/*  925:     */         {
/*  926: 789 */           switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  927:     */           {
/*  928:     */           case 1: 
/*  929:     */             break;
/*  930:     */           default: 
/*  931: 794 */             this.jj_la1[39] = this.jj_gen;
/*  932: 795 */             break;
/*  933:     */           }
/*  934: 797 */           jj_consume_token(1);
/*  935:     */         }
/*  936: 799 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  937:     */         {
/*  938:     */         case 3: 
/*  939: 801 */           declaration();
/*  940: 802 */           break;
/*  941:     */         default: 
/*  942: 804 */           this.jj_la1[40] = this.jj_gen;
/*  943:     */         }
/*  944:     */       }
/*  945: 808 */       jj_consume_token(11);
/*  946:     */     }
/*  947:     */     catch (CSSParseException e)
/*  948:     */     {
/*  949: 810 */       getErrorHandler().error(e);
/*  950: 811 */       noError = false;
/*  951: 812 */       error_skipblock();
/*  952:     */     }
/*  953:     */     catch (ParseException e)
/*  954:     */     {
/*  955: 814 */       getErrorHandler().error(toCSSParseException("invalidStyleRule", e));
/*  956: 815 */       noError = false;
/*  957: 816 */       error_skipblock();
/*  958:     */     }
/*  959:     */     finally
/*  960:     */     {
/*  961: 818 */       if (start) {
/*  962: 819 */         handleEndSelector(selList);
/*  963:     */       }
/*  964:     */     }
/*  965:     */   }
/*  966:     */   
/*  967:     */   public final SelectorList selectorList()
/*  968:     */     throws ParseException
/*  969:     */   {
/*  970: 825 */     SelectorListImpl selList = new SelectorListImpl();
/*  971:     */     
/*  972: 827 */     Selector sel = selector();
/*  973:     */     for (;;)
/*  974:     */     {
/*  975: 830 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  976:     */       {
/*  977:     */       case 12: 
/*  978:     */         break;
/*  979:     */       default: 
/*  980: 835 */         this.jj_la1[41] = this.jj_gen;
/*  981: 836 */         break;
/*  982:     */       }
/*  983: 838 */       jj_consume_token(12);
/*  984:     */       for (;;)
/*  985:     */       {
/*  986: 841 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  987:     */         {
/*  988:     */         case 1: 
/*  989:     */           break;
/*  990:     */         default: 
/*  991: 846 */           this.jj_la1[42] = this.jj_gen;
/*  992: 847 */           break;
/*  993:     */         }
/*  994: 849 */         jj_consume_token(1);
/*  995:     */       }
/*  996: 851 */       selList.add(sel);
/*  997: 852 */       sel = selector();
/*  998:     */     }
/*  999:     */     for (;;)
/* 1000:     */     {
/* 1001: 856 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1002:     */       {
/* 1003:     */       case 1: 
/* 1004:     */         break;
/* 1005:     */       default: 
/* 1006: 861 */         this.jj_la1[43] = this.jj_gen;
/* 1007: 862 */         break;
/* 1008:     */       }
/* 1009: 864 */       jj_consume_token(1);
/* 1010:     */     }
/* 1011: 866 */     selList.add(sel);
/* 1012: 867 */     return selList;
/* 1013:     */   }
/* 1014:     */   
/* 1015:     */   public final Selector selector()
/* 1016:     */     throws ParseException
/* 1017:     */   {
/* 1018:     */     try
/* 1019:     */     {
/* 1020: 879 */       Selector sel = simpleSelector(null, ' ');
/* 1021: 882 */       while (jj_2_1(2))
/* 1022:     */       {
/* 1023: 887 */         jj_consume_token(1);
/* 1024: 888 */         sel = simpleSelector(sel, ' ');
/* 1025:     */       }
/* 1026: 890 */       handleSelector(sel);
/* 1027: 891 */       return sel;
/* 1028:     */     }
/* 1029:     */     catch (ParseException e)
/* 1030:     */     {
/* 1031: 893 */       throw toCSSParseException("invalidSelector", e);
/* 1032:     */     }
/* 1033:     */   }
/* 1034:     */   
/* 1035:     */   public final Selector simpleSelector(Selector sel, char comb)
/* 1036:     */     throws ParseException
/* 1037:     */   {
/* 1038: 908 */     SimpleSelector simpleSel = null;
/* 1039: 909 */     Condition c = null;
/* 1040:     */     try
/* 1041:     */     {
/* 1042: 911 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1043:     */       {
/* 1044:     */       case 3: 
/* 1045:     */       case 16: 
/* 1046: 914 */         simpleSel = elementName();
/* 1047: 915 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1048:     */         {
/* 1049:     */         case 9: 
/* 1050: 917 */           c = hash(c);
/* 1051: 918 */           break;
/* 1052:     */         default: 
/* 1053: 920 */           this.jj_la1[44] = this.jj_gen;
/* 1054:     */         }
/* 1055: 923 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1056:     */         {
/* 1057:     */         case 13: 
/* 1058: 925 */           c = _class(c);
/* 1059: 926 */           break;
/* 1060:     */         default: 
/* 1061: 928 */           this.jj_la1[45] = this.jj_gen;
/* 1062:     */         }
/* 1063: 931 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1064:     */         {
/* 1065:     */         case 4: 
/* 1066:     */         case 5: 
/* 1067:     */         case 6: 
/* 1068: 935 */           c = pseudoClass(c);
/* 1069: 936 */           break;
/* 1070:     */         default: 
/* 1071: 938 */           this.jj_la1[46] = this.jj_gen;
/* 1072:     */         }
/* 1073: 941 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1074:     */         {
/* 1075:     */         case 7: 
/* 1076:     */         case 8: 
/* 1077: 944 */           c = pseudoElement(c);
/* 1078: 945 */           break;
/* 1079:     */         default: 
/* 1080: 947 */           this.jj_la1[47] = this.jj_gen;
/* 1081:     */         }
/* 1082: 950 */         break;
/* 1083:     */       case 9: 
/* 1084: 952 */         simpleSel = getSelectorFactory().createElementSelector(null, null);
/* 1085: 953 */         c = hash(c);
/* 1086: 954 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1087:     */         {
/* 1088:     */         case 13: 
/* 1089: 956 */           c = _class(c);
/* 1090: 957 */           break;
/* 1091:     */         default: 
/* 1092: 959 */           this.jj_la1[48] = this.jj_gen;
/* 1093:     */         }
/* 1094: 962 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1095:     */         {
/* 1096:     */         case 4: 
/* 1097:     */         case 5: 
/* 1098:     */         case 6: 
/* 1099: 966 */           c = pseudoClass(c);
/* 1100: 967 */           break;
/* 1101:     */         default: 
/* 1102: 969 */           this.jj_la1[49] = this.jj_gen;
/* 1103:     */         }
/* 1104: 972 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1105:     */         {
/* 1106:     */         case 7: 
/* 1107:     */         case 8: 
/* 1108: 975 */           c = pseudoElement(c);
/* 1109: 976 */           break;
/* 1110:     */         default: 
/* 1111: 978 */           this.jj_la1[50] = this.jj_gen;
/* 1112:     */         }
/* 1113: 981 */         break;
/* 1114:     */       case 13: 
/* 1115: 983 */         simpleSel = getSelectorFactory().createElementSelector(null, null);
/* 1116: 984 */         c = _class(c);
/* 1117: 985 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1118:     */         {
/* 1119:     */         case 4: 
/* 1120:     */         case 5: 
/* 1121:     */         case 6: 
/* 1122: 989 */           c = pseudoClass(c);
/* 1123: 990 */           break;
/* 1124:     */         default: 
/* 1125: 992 */           this.jj_la1[51] = this.jj_gen;
/* 1126:     */         }
/* 1127: 995 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1128:     */         {
/* 1129:     */         case 7: 
/* 1130:     */         case 8: 
/* 1131: 998 */           c = pseudoElement(c);
/* 1132: 999 */           break;
/* 1133:     */         default: 
/* 1134:1001 */           this.jj_la1[52] = this.jj_gen;
/* 1135:     */         }
/* 1136:1004 */         break;
/* 1137:     */       case 4: 
/* 1138:     */       case 5: 
/* 1139:     */       case 6: 
/* 1140:1008 */         simpleSel = getSelectorFactory().createElementSelector(null, null);
/* 1141:1009 */         c = pseudoClass(c);
/* 1142:1010 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1143:     */         {
/* 1144:     */         case 7: 
/* 1145:     */         case 8: 
/* 1146:1013 */           c = pseudoElement(c);
/* 1147:1014 */           break;
/* 1148:     */         default: 
/* 1149:1016 */           this.jj_la1[53] = this.jj_gen;
/* 1150:     */         }
/* 1151:1019 */         break;
/* 1152:     */       case 7: 
/* 1153:     */       case 8: 
/* 1154:1022 */         simpleSel = getSelectorFactory().createElementSelector(null, null);
/* 1155:1023 */         c = pseudoElement(c);
/* 1156:1024 */         break;
/* 1157:     */       case 10: 
/* 1158:     */       case 11: 
/* 1159:     */       case 12: 
/* 1160:     */       case 14: 
/* 1161:     */       case 15: 
/* 1162:     */       default: 
/* 1163:1026 */         this.jj_la1[54] = this.jj_gen;
/* 1164:1027 */         jj_consume_token(-1);
/* 1165:1028 */         throw new ParseException();
/* 1166:     */       }
/* 1167:1030 */       if (c != null) {
/* 1168:1031 */         simpleSel = getSelectorFactory().createConditionalSelector(simpleSel, c);
/* 1169:     */       }
/* 1170:1034 */       if (sel != null) {
/* 1171:1035 */         switch (comb)
/* 1172:     */         {
/* 1173:     */         case ' ': 
/* 1174:1037 */           sel = getSelectorFactory().createDescendantSelector(sel, simpleSel);
/* 1175:     */         }
/* 1176:     */       }
/* 1177:1041 */       return simpleSel;
/* 1178:     */     }
/* 1179:     */     catch (ParseException e)
/* 1180:     */     {
/* 1181:1046 */       throw toCSSParseException("invalidSimpleSelector", e);
/* 1182:     */     }
/* 1183:     */   }
/* 1184:     */   
/* 1185:     */   public final Condition _class(Condition pred)
/* 1186:     */     throws ParseException
/* 1187:     */   {
/* 1188:     */     try
/* 1189:     */     {
/* 1190:1059 */       jj_consume_token(13);
/* 1191:1060 */       Token t = jj_consume_token(3);
/* 1192:1061 */       Condition c = getConditionFactory().createClassCondition(null, t.image);
/* 1193:1062 */       return pred == null ? c : getConditionFactory().createAndCondition(pred, c);
/* 1194:     */     }
/* 1195:     */     catch (ParseException e)
/* 1196:     */     {
/* 1197:1064 */       throw toCSSParseException("invalidClassSelector", e);
/* 1198:     */     }
/* 1199:     */   }
/* 1200:     */   
/* 1201:     */   public final SimpleSelector elementName()
/* 1202:     */     throws ParseException
/* 1203:     */   {
/* 1204:     */     try
/* 1205:     */     {
/* 1206:1077 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1207:     */       {
/* 1208:     */       case 3: 
/* 1209:1079 */         Token t = jj_consume_token(3);
/* 1210:1080 */         return getSelectorFactory().createElementSelector(null, unescape(t.image));
/* 1211:     */       case 16: 
/* 1212:1083 */         jj_consume_token(16);
/* 1213:1084 */         return getSelectorFactory().createElementSelector(null, null);
/* 1214:     */       }
/* 1215:1087 */       this.jj_la1[55] = this.jj_gen;
/* 1216:1088 */       jj_consume_token(-1);
/* 1217:1089 */       throw new ParseException();
/* 1218:     */     }
/* 1219:     */     catch (ParseException e)
/* 1220:     */     {
/* 1221:1092 */       throw toCSSParseException("invalidElementName", e);
/* 1222:     */     }
/* 1223:     */   }
/* 1224:     */   
/* 1225:     */   public final Condition pseudoClass(Condition pred)
/* 1226:     */     throws ParseException
/* 1227:     */   {
/* 1228:     */     try
/* 1229:     */     {
/* 1230:     */       Token t;
/* 1231:1106 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1232:     */       {
/* 1233:     */       case 4: 
/* 1234:1108 */         t = jj_consume_token(4);
/* 1235:1109 */         break;
/* 1236:     */       case 5: 
/* 1237:1111 */         t = jj_consume_token(5);
/* 1238:1112 */         break;
/* 1239:     */       case 6: 
/* 1240:1114 */         t = jj_consume_token(6);
/* 1241:1115 */         break;
/* 1242:     */       default: 
/* 1243:1117 */         this.jj_la1[56] = this.jj_gen;
/* 1244:1118 */         jj_consume_token(-1);
/* 1245:1119 */         throw new ParseException();
/* 1246:     */       }
/* 1247:1123 */       String s = t.image;
/* 1248:1124 */       Condition c = getConditionFactory().createPseudoClassCondition(null, s);
/* 1249:1125 */       return pred == null ? c : getConditionFactory().createAndCondition(pred, c);
/* 1250:     */     }
/* 1251:     */     catch (ParseException e)
/* 1252:     */     {
/* 1253:1129 */       throw toCSSParseException("invalidPseudoClass", e);
/* 1254:     */     }
/* 1255:     */   }
/* 1256:     */   
/* 1257:     */   public final Condition pseudoElement(Condition pred)
/* 1258:     */     throws ParseException
/* 1259:     */   {
/* 1260:     */     try
/* 1261:     */     {
/* 1262:     */       Token t;
/* 1263:1143 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1264:     */       {
/* 1265:     */       case 8: 
/* 1266:1145 */         t = jj_consume_token(8);
/* 1267:1146 */         break;
/* 1268:     */       case 7: 
/* 1269:1148 */         t = jj_consume_token(7);
/* 1270:     */         
/* 1271:1150 */         String s = t.image;
/* 1272:     */         
/* 1273:1152 */         Condition c = getConditionFactory().createPseudoClassCondition(null, s);
/* 1274:1153 */         return pred == null ? c : getConditionFactory().createAndCondition(pred, c);
/* 1275:     */       default: 
/* 1276:1158 */         this.jj_la1[57] = this.jj_gen;
/* 1277:1159 */         jj_consume_token(-1);
/* 1278:1160 */         throw new ParseException();
/* 1279:     */       }
/* 1280:     */     }
/* 1281:     */     catch (ParseException e)
/* 1282:     */     {
/* 1283:1163 */       throw toCSSParseException("invalidPseudoElement", e);
/* 1284:     */     }
/* 1285:1165 */     throw new Error("Missing return statement in function");
/* 1286:     */   }
/* 1287:     */   
/* 1288:     */   public final Condition hash(Condition pred)
/* 1289:     */     throws ParseException
/* 1290:     */   {
/* 1291:     */     try
/* 1292:     */     {
/* 1293:1171 */       Token t = jj_consume_token(9);
/* 1294:1172 */       Condition c = getConditionFactory().createIdCondition(t.image.substring(1));
/* 1295:1173 */       return pred == null ? c : getConditionFactory().createAndCondition(pred, c);
/* 1296:     */     }
/* 1297:     */     catch (ParseException e)
/* 1298:     */     {
/* 1299:1175 */       throw toCSSParseException("invalidHash", e);
/* 1300:     */     }
/* 1301:     */   }
/* 1302:     */   
/* 1303:     */   public final void styleDeclaration()
/* 1304:     */     throws ParseException
/* 1305:     */   {
/* 1306:1181 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1307:     */     {
/* 1308:     */     case 3: 
/* 1309:1183 */       declaration();
/* 1310:1184 */       break;
/* 1311:     */     default: 
/* 1312:1186 */       this.jj_la1[58] = this.jj_gen;
/* 1313:     */     }
/* 1314:     */     for (;;)
/* 1315:     */     {
/* 1316:1191 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1317:     */       {
/* 1318:     */       case 14: 
/* 1319:     */         break;
/* 1320:     */       default: 
/* 1321:1196 */         this.jj_la1[59] = this.jj_gen;
/* 1322:1197 */         break;
/* 1323:     */       }
/* 1324:1199 */       jj_consume_token(14);
/* 1325:     */       for (;;)
/* 1326:     */       {
/* 1327:1202 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1328:     */         {
/* 1329:     */         case 1: 
/* 1330:     */           break;
/* 1331:     */         default: 
/* 1332:1207 */           this.jj_la1[60] = this.jj_gen;
/* 1333:1208 */           break;
/* 1334:     */         }
/* 1335:1210 */         jj_consume_token(1);
/* 1336:     */       }
/* 1337:1212 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1338:     */       {
/* 1339:     */       case 3: 
/* 1340:1214 */         declaration();
/* 1341:1215 */         break;
/* 1342:     */       default: 
/* 1343:1217 */         this.jj_la1[61] = this.jj_gen;
/* 1344:     */       }
/* 1345:     */     }
/* 1346:     */   }
/* 1347:     */   
/* 1348:     */   public final void declaration()
/* 1349:     */     throws ParseException
/* 1350:     */   {
/* 1351:1232 */     boolean priority = false;
/* 1352:     */     try
/* 1353:     */     {
/* 1354:1234 */       String p = property();
/* 1355:1235 */       jj_consume_token(15);
/* 1356:     */       for (;;)
/* 1357:     */       {
/* 1358:1238 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1359:     */         {
/* 1360:     */         case 1: 
/* 1361:     */           break;
/* 1362:     */         default: 
/* 1363:1243 */           this.jj_la1[62] = this.jj_gen;
/* 1364:1244 */           break;
/* 1365:     */         }
/* 1366:1246 */         jj_consume_token(1);
/* 1367:     */       }
/* 1368:1248 */       LexicalUnit e = expr();
/* 1369:1249 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1370:     */       {
/* 1371:     */       case 31: 
/* 1372:1251 */         priority = prio();
/* 1373:1252 */         break;
/* 1374:     */       default: 
/* 1375:1254 */         this.jj_la1[63] = this.jj_gen;
/* 1376:     */       }
/* 1377:1257 */       handleProperty(p, e, priority);
/* 1378:     */     }
/* 1379:     */     catch (CSSParseException ex)
/* 1380:     */     {
/* 1381:1259 */       getErrorHandler().error(ex);
/* 1382:1260 */       error_skipdecl();
/* 1383:     */     }
/* 1384:     */     catch (ParseException ex)
/* 1385:     */     {
/* 1386:1262 */       getErrorHandler().error(toCSSParseException("invalidDeclaration", ex));
/* 1387:1263 */       error_skipdecl();
/* 1388:     */     }
/* 1389:     */   }
/* 1390:     */   
/* 1391:     */   public final boolean prio()
/* 1392:     */     throws ParseException
/* 1393:     */   {
/* 1394:1273 */     jj_consume_token(31);
/* 1395:     */     for (;;)
/* 1396:     */     {
/* 1397:1276 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1398:     */       {
/* 1399:     */       case 1: 
/* 1400:     */         break;
/* 1401:     */       default: 
/* 1402:1281 */         this.jj_la1[64] = this.jj_gen;
/* 1403:1282 */         break;
/* 1404:     */       }
/* 1405:1284 */       jj_consume_token(1);
/* 1406:     */     }
/* 1407:1286 */     return true;
/* 1408:     */   }
/* 1409:     */   
/* 1410:     */   public final LexicalUnit expr()
/* 1411:     */     throws ParseException
/* 1412:     */   {
/* 1413:     */     try
/* 1414:     */     {
/* 1415:1301 */       LexicalUnit head = term(null);
/* 1416:1302 */       LexicalUnit body = head;
/* 1417:     */       for (;;)
/* 1418:     */       {
/* 1419:1305 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1420:     */         {
/* 1421:     */         case 3: 
/* 1422:     */         case 9: 
/* 1423:     */         case 12: 
/* 1424:     */         case 17: 
/* 1425:     */         case 18: 
/* 1426:     */         case 19: 
/* 1427:     */         case 24: 
/* 1428:     */         case 26: 
/* 1429:     */         case 33: 
/* 1430:     */         case 34: 
/* 1431:     */         case 35: 
/* 1432:     */         case 36: 
/* 1433:     */         case 37: 
/* 1434:     */         case 38: 
/* 1435:     */         case 39: 
/* 1436:     */         case 40: 
/* 1437:     */         case 41: 
/* 1438:     */         case 42: 
/* 1439:     */         case 43: 
/* 1440:     */         case 47: 
/* 1441:     */           break;
/* 1442:     */         case 4: 
/* 1443:     */         case 5: 
/* 1444:     */         case 6: 
/* 1445:     */         case 7: 
/* 1446:     */         case 8: 
/* 1447:     */         case 10: 
/* 1448:     */         case 11: 
/* 1449:     */         case 13: 
/* 1450:     */         case 14: 
/* 1451:     */         case 15: 
/* 1452:     */         case 16: 
/* 1453:     */         case 20: 
/* 1454:     */         case 21: 
/* 1455:     */         case 22: 
/* 1456:     */         case 23: 
/* 1457:     */         case 25: 
/* 1458:     */         case 27: 
/* 1459:     */         case 28: 
/* 1460:     */         case 29: 
/* 1461:     */         case 30: 
/* 1462:     */         case 31: 
/* 1463:     */         case 32: 
/* 1464:     */         case 44: 
/* 1465:     */         case 45: 
/* 1466:     */         case 46: 
/* 1467:     */         default: 
/* 1468:1329 */           this.jj_la1[65] = this.jj_gen;
/* 1469:1330 */           break;
/* 1470:     */         }
/* 1471:1332 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1472:     */         {
/* 1473:     */         case 12: 
/* 1474:     */         case 17: 
/* 1475:1335 */           body = operator(body);
/* 1476:1336 */           break;
/* 1477:     */         default: 
/* 1478:1338 */           this.jj_la1[66] = this.jj_gen;
/* 1479:     */         }
/* 1480:1341 */         body = term(body);
/* 1481:     */       }
/* 1482:1343 */       return head;
/* 1483:     */     }
/* 1484:     */     catch (ParseException ex)
/* 1485:     */     {
/* 1486:1345 */       throw toCSSParseException("invalidExpr", ex);
/* 1487:     */     }
/* 1488:     */   }
/* 1489:     */   
/* 1490:     */   public final LexicalUnit term(LexicalUnit prev)
/* 1491:     */     throws ParseException
/* 1492:     */   {
/* 1493:1359 */     char op = ' ';
/* 1494:     */     
/* 1495:1361 */     LexicalUnit value = null;
/* 1496:1362 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1497:     */     {
/* 1498:     */     case 18: 
/* 1499:     */     case 19: 
/* 1500:1365 */       op = unaryOperator();
/* 1501:1366 */       break;
/* 1502:     */     default: 
/* 1503:1368 */       this.jj_la1[67] = this.jj_gen;
/* 1504:     */     }
/* 1505:     */     Token t;
/* 1506:1371 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1507:     */     {
/* 1508:     */     case 33: 
/* 1509:     */     case 34: 
/* 1510:     */     case 35: 
/* 1511:     */     case 36: 
/* 1512:     */     case 37: 
/* 1513:     */     case 38: 
/* 1514:     */     case 39: 
/* 1515:     */     case 40: 
/* 1516:     */     case 41: 
/* 1517:     */     case 42: 
/* 1518:1382 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1519:     */       {
/* 1520:     */       case 42: 
/* 1521:1384 */         t = jj_consume_token(42);
/* 1522:     */         try
/* 1523:     */         {
/* 1524:1387 */           value = LexicalUnitImpl.createNumber(prev, intValue(op, t.image));
/* 1525:     */         }
/* 1526:     */         catch (NumberFormatException e)
/* 1527:     */         {
/* 1528:1391 */           value = LexicalUnitImpl.createNumber(prev, floatValue(op, t.image));
/* 1529:     */         }
/* 1530:     */       case 41: 
/* 1531:1395 */         t = jj_consume_token(41);
/* 1532:1396 */         value = LexicalUnitImpl.createPercentage(prev, floatValue(op, t.image));
/* 1533:1397 */         break;
/* 1534:     */       case 35: 
/* 1535:1399 */         t = jj_consume_token(35);
/* 1536:1400 */         value = LexicalUnitImpl.createPixel(prev, floatValue(op, t.image));
/* 1537:1401 */         break;
/* 1538:     */       case 36: 
/* 1539:1403 */         t = jj_consume_token(36);
/* 1540:1404 */         value = LexicalUnitImpl.createCentimeter(prev, floatValue(op, t.image));
/* 1541:1405 */         break;
/* 1542:     */       case 37: 
/* 1543:1407 */         t = jj_consume_token(37);
/* 1544:1408 */         value = LexicalUnitImpl.createMillimeter(prev, floatValue(op, t.image));
/* 1545:1409 */         break;
/* 1546:     */       case 38: 
/* 1547:1411 */         t = jj_consume_token(38);
/* 1548:1412 */         value = LexicalUnitImpl.createInch(prev, floatValue(op, t.image));
/* 1549:1413 */         break;
/* 1550:     */       case 39: 
/* 1551:1415 */         t = jj_consume_token(39);
/* 1552:1416 */         value = LexicalUnitImpl.createPoint(prev, floatValue(op, t.image));
/* 1553:1417 */         break;
/* 1554:     */       case 40: 
/* 1555:1419 */         t = jj_consume_token(40);
/* 1556:1420 */         value = LexicalUnitImpl.createPica(prev, floatValue(op, t.image));
/* 1557:1421 */         break;
/* 1558:     */       case 33: 
/* 1559:1423 */         t = jj_consume_token(33);
/* 1560:1424 */         value = LexicalUnitImpl.createEm(prev, floatValue(op, t.image));
/* 1561:1425 */         break;
/* 1562:     */       case 34: 
/* 1563:1427 */         t = jj_consume_token(34);
/* 1564:1428 */         value = LexicalUnitImpl.createEx(prev, floatValue(op, t.image));
/* 1565:1429 */         break;
/* 1566:     */       default: 
/* 1567:1431 */         this.jj_la1[68] = this.jj_gen;
/* 1568:1432 */         jj_consume_token(-1);
/* 1569:1433 */         throw new ParseException();
/* 1570:     */       }
/* 1571:     */       break;
/* 1572:     */     case 24: 
/* 1573:1437 */       t = jj_consume_token(24);
/* 1574:1438 */       value = new LexicalUnitImpl(prev, (short)36, t.image);
/* 1575:1439 */       break;
/* 1576:     */     case 3: 
/* 1577:1441 */       t = jj_consume_token(3);
/* 1578:1442 */       value = new LexicalUnitImpl(prev, (short)35, t.image);
/* 1579:1443 */       break;
/* 1580:     */     case 26: 
/* 1581:1445 */       t = jj_consume_token(26);
/* 1582:1446 */       value = new LexicalUnitImpl(prev, (short)24, t.image);
/* 1583:1447 */       break;
/* 1584:     */     case 47: 
/* 1585:1449 */       t = jj_consume_token(47);
/* 1586:1450 */       value = new LexicalUnitImpl(prev, (short)39, t.image);
/* 1587:1451 */       break;
/* 1588:     */     case 43: 
/* 1589:1453 */       value = rgb(prev);
/* 1590:1454 */       break;
/* 1591:     */     case 9: 
/* 1592:1456 */       value = hexcolor(prev);
/* 1593:1457 */       break;
/* 1594:     */     case 4: 
/* 1595:     */     case 5: 
/* 1596:     */     case 6: 
/* 1597:     */     case 7: 
/* 1598:     */     case 8: 
/* 1599:     */     case 10: 
/* 1600:     */     case 11: 
/* 1601:     */     case 12: 
/* 1602:     */     case 13: 
/* 1603:     */     case 14: 
/* 1604:     */     case 15: 
/* 1605:     */     case 16: 
/* 1606:     */     case 17: 
/* 1607:     */     case 18: 
/* 1608:     */     case 19: 
/* 1609:     */     case 20: 
/* 1610:     */     case 21: 
/* 1611:     */     case 22: 
/* 1612:     */     case 23: 
/* 1613:     */     case 25: 
/* 1614:     */     case 27: 
/* 1615:     */     case 28: 
/* 1616:     */     case 29: 
/* 1617:     */     case 30: 
/* 1618:     */     case 31: 
/* 1619:     */     case 32: 
/* 1620:     */     case 44: 
/* 1621:     */     case 45: 
/* 1622:     */     case 46: 
/* 1623:     */     default: 
/* 1624:1459 */       this.jj_la1[69] = this.jj_gen;
/* 1625:1460 */       jj_consume_token(-1);
/* 1626:1461 */       throw new ParseException();
/* 1627:     */     }
/* 1628:     */     for (;;)
/* 1629:     */     {
/* 1630:1465 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1631:     */       {
/* 1632:     */       case 1: 
/* 1633:     */         break;
/* 1634:     */       default: 
/* 1635:1470 */         this.jj_la1[70] = this.jj_gen;
/* 1636:1471 */         break;
/* 1637:     */       }
/* 1638:1473 */       jj_consume_token(1);
/* 1639:     */     }
/* 1640:1475 */     if ((value instanceof LexicalUnitImpl)) {
/* 1641:1477 */       ((LexicalUnitImpl)value).setLocator(getLocator());
/* 1642:     */     }
/* 1643:1479 */     return value;
/* 1644:     */   }
/* 1645:     */   
/* 1646:     */   public final LexicalUnit rgb(LexicalUnit prev)
/* 1647:     */     throws ParseException
/* 1648:     */   {
/* 1649:1491 */     Token t = jj_consume_token(43);
/* 1650:     */     for (;;)
/* 1651:     */     {
/* 1652:1494 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1653:     */       {
/* 1654:     */       case 1: 
/* 1655:     */         break;
/* 1656:     */       default: 
/* 1657:1499 */         this.jj_la1[71] = this.jj_gen;
/* 1658:1500 */         break;
/* 1659:     */       }
/* 1660:1502 */       jj_consume_token(1);
/* 1661:     */     }
/* 1662:1504 */     LexicalUnit params = expr();
/* 1663:1505 */     jj_consume_token(25);
/* 1664:1506 */     return LexicalUnitImpl.createRgbColor(prev, params);
/* 1665:     */   }
/* 1666:     */   
/* 1667:     */   public final LexicalUnit hexcolor(LexicalUnit prev)
/* 1668:     */     throws ParseException
/* 1669:     */   {
/* 1670:1517 */     Token t = jj_consume_token(9);
/* 1671:1518 */     return hexcolorInternal(prev, t);
/* 1672:     */   }
/* 1673:     */   
/* 1674:     */   void skipSelector()
/* 1675:     */     throws ParseException
/* 1676:     */   {
/* 1677:1523 */     Token t = getToken(1);
/* 1678:1524 */     while ((t.kind != 12) && (t.kind != 14) && (t.kind != 10) && (t.kind != 0))
/* 1679:     */     {
/* 1680:1525 */       getNextToken();
/* 1681:1526 */       t = getToken(1);
/* 1682:     */     }
/* 1683:     */   }
/* 1684:     */   
/* 1685:     */   String skip()
/* 1686:     */     throws ParseException
/* 1687:     */   {
/* 1688:1531 */     StringBuffer sb = new StringBuffer();
/* 1689:1532 */     int nesting = 0;
/* 1690:1533 */     Token t = getToken(0);
/* 1691:1534 */     if (t.image != null) {
/* 1692:1535 */       sb.append(t.image);
/* 1693:     */     }
/* 1694:     */     do
/* 1695:     */     {
/* 1696:1538 */       t = getNextToken();
/* 1697:1539 */       if (t.kind == 0) {
/* 1698:     */         break;
/* 1699:     */       }
/* 1700:1541 */       sb.append(t.image);
/* 1701:1542 */       if (t.kind == 10) {
/* 1702:1543 */         nesting++;
/* 1703:1544 */       } else if (t.kind == 11) {
/* 1704:1545 */         nesting--;
/* 1705:     */       } else {
/* 1706:1546 */         if ((t.kind == 14) && (nesting <= 0)) {
/* 1707:     */           break;
/* 1708:     */         }
/* 1709:     */       }
/* 1710:1549 */     } while ((t.kind != 11) || (nesting > 0));
/* 1711:1551 */     return sb.toString();
/* 1712:     */   }
/* 1713:     */   
/* 1714:     */   void error_skipblock()
/* 1715:     */     throws ParseException
/* 1716:     */   {
/* 1717:1556 */     int nesting = 0;
/* 1718:     */     Token t;
/* 1719:     */     do
/* 1720:     */     {
/* 1721:1559 */       t = getNextToken();
/* 1722:1560 */       if (t.kind == 10) {
/* 1723:1562 */         nesting++;
/* 1724:1564 */       } else if (t.kind == 11) {
/* 1725:1566 */         nesting--;
/* 1726:     */       } else {
/* 1727:1568 */         if (t.kind == 0) {
/* 1728:     */           break;
/* 1729:     */         }
/* 1730:     */       }
/* 1731:1573 */     } while ((t.kind != 11) || (nesting > 0));
/* 1732:     */   }
/* 1733:     */   
/* 1734:     */   void error_skipdecl()
/* 1735:     */     throws ParseException
/* 1736:     */   {
/* 1737:1577 */     int nesting = 0;
/* 1738:1578 */     Token t = getToken(1);
/* 1739:1579 */     if (t.kind == 10)
/* 1740:     */     {
/* 1741:1581 */       error_skipblock();
/* 1742:     */     }
/* 1743:     */     else
/* 1744:     */     {
/* 1745:1585 */       Token oldToken = t;
/* 1746:1586 */       while ((t.kind != 14) && (t.kind != 11) && (t.kind != 0))
/* 1747:     */       {
/* 1748:1588 */         oldToken = t;
/* 1749:1589 */         t = getNextToken();
/* 1750:     */       }
/* 1751:1591 */       if (t.kind == 11) {
/* 1752:1593 */         this.token = oldToken;
/* 1753:     */       }
/* 1754:     */     }
/* 1755:     */   }
/* 1756:     */   
/* 1757:     */   void error_skipAtRule()
/* 1758:     */     throws ParseException
/* 1759:     */   {
/* 1760:1599 */     Token t = null;
/* 1761:     */     do
/* 1762:     */     {
/* 1763:1602 */       t = getNextToken();
/* 1764:1604 */     } while (t.kind != 14);
/* 1765:     */   }
/* 1766:     */   
/* 1767:     */   private final boolean jj_2_1(int xla)
/* 1768:     */   {
/* 1769:1608 */     this.jj_la = xla;this.jj_lastpos = (this.jj_scanpos = this.token);
/* 1770:     */     try
/* 1771:     */     {
/* 1772:1609 */       return !jj_3_1();
/* 1773:     */     }
/* 1774:     */     catch (LookaheadSuccess ls)
/* 1775:     */     {
/* 1776:1610 */       return true;
/* 1777:     */     }
/* 1778:     */     finally
/* 1779:     */     {
/* 1780:1611 */       jj_save(0, xla);
/* 1781:     */     }
/* 1782:     */   }
/* 1783:     */   
/* 1784:     */   private final boolean jj_3R_44()
/* 1785:     */   {
/* 1786:1615 */     if (jj_scan_token(13)) {
/* 1787:1615 */       return true;
/* 1788:     */     }
/* 1789:1616 */     return false;
/* 1790:     */   }
/* 1791:     */   
/* 1792:     */   private final boolean jj_3_1()
/* 1793:     */   {
/* 1794:1620 */     if (jj_scan_token(1)) {
/* 1795:1620 */       return true;
/* 1796:     */     }
/* 1797:1621 */     if (jj_3R_36()) {
/* 1798:1621 */       return true;
/* 1799:     */     }
/* 1800:1622 */     return false;
/* 1801:     */   }
/* 1802:     */   
/* 1803:     */   private final boolean jj_3R_39()
/* 1804:     */   {
/* 1805:1626 */     if (jj_3R_44()) {
/* 1806:1626 */       return true;
/* 1807:     */     }
/* 1808:1627 */     return false;
/* 1809:     */   }
/* 1810:     */   
/* 1811:     */   private final boolean jj_3R_43()
/* 1812:     */   {
/* 1813:1631 */     if (jj_scan_token(9)) {
/* 1814:1631 */       return true;
/* 1815:     */     }
/* 1816:1632 */     return false;
/* 1817:     */   }
/* 1818:     */   
/* 1819:     */   private final boolean jj_3R_36()
/* 1820:     */   {
/* 1821:1637 */     Token xsp = this.jj_scanpos;
/* 1822:1638 */     if (jj_3R_37())
/* 1823:     */     {
/* 1824:1639 */       this.jj_scanpos = xsp;
/* 1825:1640 */       if (jj_3R_38())
/* 1826:     */       {
/* 1827:1641 */         this.jj_scanpos = xsp;
/* 1828:1642 */         if (jj_3R_39())
/* 1829:     */         {
/* 1830:1643 */           this.jj_scanpos = xsp;
/* 1831:1644 */           if (jj_3R_40())
/* 1832:     */           {
/* 1833:1645 */             this.jj_scanpos = xsp;
/* 1834:1646 */             if (jj_3R_41()) {
/* 1835:1646 */               return true;
/* 1836:     */             }
/* 1837:     */           }
/* 1838:     */         }
/* 1839:     */       }
/* 1840:     */     }
/* 1841:1651 */     return false;
/* 1842:     */   }
/* 1843:     */   
/* 1844:     */   private final boolean jj_3R_46()
/* 1845:     */   {
/* 1846:1656 */     Token xsp = this.jj_scanpos;
/* 1847:1657 */     if (jj_scan_token(8))
/* 1848:     */     {
/* 1849:1658 */       this.jj_scanpos = xsp;
/* 1850:1659 */       if (jj_3R_49()) {
/* 1851:1659 */         return true;
/* 1852:     */       }
/* 1853:     */     }
/* 1854:1661 */     return false;
/* 1855:     */   }
/* 1856:     */   
/* 1857:     */   private final boolean jj_3R_41()
/* 1858:     */   {
/* 1859:1665 */     if (jj_3R_46()) {
/* 1860:1665 */       return true;
/* 1861:     */     }
/* 1862:1666 */     return false;
/* 1863:     */   }
/* 1864:     */   
/* 1865:     */   private final boolean jj_3R_38()
/* 1866:     */   {
/* 1867:1670 */     if (jj_3R_43()) {
/* 1868:1670 */       return true;
/* 1869:     */     }
/* 1870:1671 */     return false;
/* 1871:     */   }
/* 1872:     */   
/* 1873:     */   private final boolean jj_3R_48()
/* 1874:     */   {
/* 1875:1675 */     if (jj_scan_token(16)) {
/* 1876:1675 */       return true;
/* 1877:     */     }
/* 1878:1676 */     return false;
/* 1879:     */   }
/* 1880:     */   
/* 1881:     */   private final boolean jj_3R_47()
/* 1882:     */   {
/* 1883:1680 */     if (jj_scan_token(3)) {
/* 1884:1680 */       return true;
/* 1885:     */     }
/* 1886:1681 */     return false;
/* 1887:     */   }
/* 1888:     */   
/* 1889:     */   private final boolean jj_3R_40()
/* 1890:     */   {
/* 1891:1685 */     if (jj_3R_45()) {
/* 1892:1685 */       return true;
/* 1893:     */     }
/* 1894:1686 */     return false;
/* 1895:     */   }
/* 1896:     */   
/* 1897:     */   private final boolean jj_3R_37()
/* 1898:     */   {
/* 1899:1690 */     if (jj_3R_42()) {
/* 1900:1690 */       return true;
/* 1901:     */     }
/* 1902:1691 */     return false;
/* 1903:     */   }
/* 1904:     */   
/* 1905:     */   private final boolean jj_3R_49()
/* 1906:     */   {
/* 1907:1695 */     if (jj_scan_token(7)) {
/* 1908:1695 */       return true;
/* 1909:     */     }
/* 1910:1696 */     return false;
/* 1911:     */   }
/* 1912:     */   
/* 1913:     */   private final boolean jj_3R_45()
/* 1914:     */   {
/* 1915:1701 */     Token xsp = this.jj_scanpos;
/* 1916:1702 */     if (jj_scan_token(4))
/* 1917:     */     {
/* 1918:1703 */       this.jj_scanpos = xsp;
/* 1919:1704 */       if (jj_scan_token(5))
/* 1920:     */       {
/* 1921:1705 */         this.jj_scanpos = xsp;
/* 1922:1706 */         if (jj_scan_token(6)) {
/* 1923:1706 */           return true;
/* 1924:     */         }
/* 1925:     */       }
/* 1926:     */     }
/* 1927:1709 */     return false;
/* 1928:     */   }
/* 1929:     */   
/* 1930:     */   private final boolean jj_3R_42()
/* 1931:     */   {
/* 1932:1714 */     Token xsp = this.jj_scanpos;
/* 1933:1715 */     if (jj_3R_47())
/* 1934:     */     {
/* 1935:1716 */       this.jj_scanpos = xsp;
/* 1936:1717 */       if (jj_3R_48()) {
/* 1937:1717 */         return true;
/* 1938:     */       }
/* 1939:     */     }
/* 1940:1719 */     return false;
/* 1941:     */   }
/* 1942:     */   
/* 1943:1727 */   public boolean lookingAhead = false;
/* 1944:     */   private boolean jj_semLA;
/* 1945:     */   private int jj_gen;
/* 1946:1730 */   private final int[] jj_la1 = new int[72];
/* 1947:     */   private static int[] jj_la1_0;
/* 1948:     */   private static int[] jj_la1_1;
/* 1949:     */   private static int[] jj_la1_2;
/* 1950:     */   
/* 1951:     */   static
/* 1952:     */   {
/* 1953:1735 */     jj_la1_0();
/* 1954:1736 */     jj_la1_1();
/* 1955:1737 */     jj_la1_2();
/* 1956:     */   }
/* 1957:     */   
/* 1958:     */   private static void jj_la1_0()
/* 1959:     */   {
/* 1960:1740 */     jj_la1_0 = new int[] { 402653186, 402653186, 536870912, 402653186, 402653186, 1073816568, 1073816568, 402653186, 402653186, 1610687480, 1610687480, 402653186, 402653186, 1610687480, 2, 83886080, 2, 8, 2, 83886080, 2, 8, 2, 2, 74744, 4096, 2, 74744, 2, 74744, 74744, 2, 2, 2, 135168, 786432, 2, 2, 16384, 2, 8, 4096, 2, 2, 512, 8192, 112, 384, 8192, 112, 384, 112, 384, 384, 74744, 65544, 112, 384, 8, 16384, 2, 8, 2, -2147483648, 2, 84808200, 135168, 786432, 0, 83886600, 2, 2 };
/* 1961:     */   }
/* 1962:     */   
/* 1963:     */   private static void jj_la1_1()
/* 1964:     */   {
/* 1965:1743 */     jj_la1_1 = new int[] { 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 36862, 0, 0, 2046, 36862, 0, 0 };
/* 1966:     */   }
/* 1967:     */   
/* 1968:     */   private static void jj_la1_2()
/* 1969:     */   {
/* 1970:1746 */     jj_la1_2 = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
/* 1971:     */   }
/* 1972:     */   
/* 1973:1748 */   private final JJCalls[] jj_2_rtns = new JJCalls[1];
/* 1974:1749 */   private boolean jj_rescan = false;
/* 1975:1750 */   private int jj_gc = 0;
/* 1976:     */   
/* 1977:     */   public SACParserCSSmobileOKBasic1(CharStream stream)
/* 1978:     */   {
/* 1979:1753 */     this.token_source = new SACParserCSSmobileOKBasic1TokenManager(stream);
/* 1980:1754 */     this.token = new Token();
/* 1981:1755 */     this.jj_ntk = -1;
/* 1982:1756 */     this.jj_gen = 0;
/* 1983:1757 */     for (int i = 0; i < 72; i++) {
/* 1984:1757 */       this.jj_la1[i] = -1;
/* 1985:     */     }
/* 1986:1758 */     for (int i = 0; i < this.jj_2_rtns.length; i++) {
/* 1987:1758 */       this.jj_2_rtns[i] = new JJCalls();
/* 1988:     */     }
/* 1989:     */   }
/* 1990:     */   
/* 1991:     */   public void ReInit(CharStream stream)
/* 1992:     */   {
/* 1993:1762 */     this.token_source.ReInit(stream);
/* 1994:1763 */     this.token = new Token();
/* 1995:1764 */     this.jj_ntk = -1;
/* 1996:1765 */     this.jj_gen = 0;
/* 1997:1766 */     for (int i = 0; i < 72; i++) {
/* 1998:1766 */       this.jj_la1[i] = -1;
/* 1999:     */     }
/* 2000:1767 */     for (int i = 0; i < this.jj_2_rtns.length; i++) {
/* 2001:1767 */       this.jj_2_rtns[i] = new JJCalls();
/* 2002:     */     }
/* 2003:     */   }
/* 2004:     */   
/* 2005:     */   public SACParserCSSmobileOKBasic1(SACParserCSSmobileOKBasic1TokenManager tm)
/* 2006:     */   {
/* 2007:1771 */     this.token_source = tm;
/* 2008:1772 */     this.token = new Token();
/* 2009:1773 */     this.jj_ntk = -1;
/* 2010:1774 */     this.jj_gen = 0;
/* 2011:1775 */     for (int i = 0; i < 72; i++) {
/* 2012:1775 */       this.jj_la1[i] = -1;
/* 2013:     */     }
/* 2014:1776 */     for (int i = 0; i < this.jj_2_rtns.length; i++) {
/* 2015:1776 */       this.jj_2_rtns[i] = new JJCalls();
/* 2016:     */     }
/* 2017:     */   }
/* 2018:     */   
/* 2019:     */   public void ReInit(SACParserCSSmobileOKBasic1TokenManager tm)
/* 2020:     */   {
/* 2021:1780 */     this.token_source = tm;
/* 2022:1781 */     this.token = new Token();
/* 2023:1782 */     this.jj_ntk = -1;
/* 2024:1783 */     this.jj_gen = 0;
/* 2025:1784 */     for (int i = 0; i < 72; i++) {
/* 2026:1784 */       this.jj_la1[i] = -1;
/* 2027:     */     }
/* 2028:1785 */     for (int i = 0; i < this.jj_2_rtns.length; i++) {
/* 2029:1785 */       this.jj_2_rtns[i] = new JJCalls();
/* 2030:     */     }
/* 2031:     */   }
/* 2032:     */   
/* 2033:     */   private final Token jj_consume_token(int kind)
/* 2034:     */     throws ParseException
/* 2035:     */   {
/* 2036:     */     Token oldToken;
/* 2037:1790 */     if ((oldToken = this.token).next != null) {
/* 2038:1790 */       this.token = this.token.next;
/* 2039:     */     } else {
/* 2040:1791 */       this.token = (this.token.next = this.token_source.getNextToken());
/* 2041:     */     }
/* 2042:1792 */     this.jj_ntk = -1;
/* 2043:1793 */     if (this.token.kind == kind)
/* 2044:     */     {
/* 2045:1794 */       this.jj_gen += 1;
/* 2046:1795 */       if (++this.jj_gc > 100)
/* 2047:     */       {
/* 2048:1796 */         this.jj_gc = 0;
/* 2049:1797 */         for (int i = 0; i < this.jj_2_rtns.length; i++)
/* 2050:     */         {
/* 2051:1798 */           JJCalls c = this.jj_2_rtns[i];
/* 2052:1799 */           while (c != null)
/* 2053:     */           {
/* 2054:1800 */             if (c.gen < this.jj_gen) {
/* 2055:1800 */               c.first = null;
/* 2056:     */             }
/* 2057:1801 */             c = c.next;
/* 2058:     */           }
/* 2059:     */         }
/* 2060:     */       }
/* 2061:1805 */       return this.token;
/* 2062:     */     }
/* 2063:1807 */     this.token = oldToken;
/* 2064:1808 */     this.jj_kind = kind;
/* 2065:1809 */     throw generateParseException();
/* 2066:     */   }
/* 2067:     */   
/* 2068:1813 */   private final LookaheadSuccess jj_ls = new LookaheadSuccess(null);
/* 2069:     */   
/* 2070:     */   private final boolean jj_scan_token(int kind)
/* 2071:     */   {
/* 2072:1815 */     if (this.jj_scanpos == this.jj_lastpos)
/* 2073:     */     {
/* 2074:1816 */       this.jj_la -= 1;
/* 2075:1817 */       if (this.jj_scanpos.next == null) {
/* 2076:1818 */         this.jj_lastpos = (this.jj_scanpos = this.jj_scanpos.next = this.token_source.getNextToken());
/* 2077:     */       } else {
/* 2078:1820 */         this.jj_lastpos = (this.jj_scanpos = this.jj_scanpos.next);
/* 2079:     */       }
/* 2080:     */     }
/* 2081:     */     else
/* 2082:     */     {
/* 2083:1823 */       this.jj_scanpos = this.jj_scanpos.next;
/* 2084:     */     }
/* 2085:1825 */     if (this.jj_rescan)
/* 2086:     */     {
/* 2087:1826 */       int i = 0;
/* 2088:1826 */       for (Token tok = this.token; (tok != null) && (tok != this.jj_scanpos); tok = tok.next) {
/* 2089:1827 */         i++;
/* 2090:     */       }
/* 2091:1828 */       if (tok != null) {
/* 2092:1828 */         jj_add_error_token(kind, i);
/* 2093:     */       }
/* 2094:     */     }
/* 2095:1830 */     if (this.jj_scanpos.kind != kind) {
/* 2096:1830 */       return true;
/* 2097:     */     }
/* 2098:1831 */     if ((this.jj_la == 0) && (this.jj_scanpos == this.jj_lastpos)) {
/* 2099:1831 */       throw this.jj_ls;
/* 2100:     */     }
/* 2101:1832 */     return false;
/* 2102:     */   }
/* 2103:     */   
/* 2104:     */   public final Token getNextToken()
/* 2105:     */   {
/* 2106:1836 */     if (this.token.next != null) {
/* 2107:1836 */       this.token = this.token.next;
/* 2108:     */     } else {
/* 2109:1837 */       this.token = (this.token.next = this.token_source.getNextToken());
/* 2110:     */     }
/* 2111:1838 */     this.jj_ntk = -1;
/* 2112:1839 */     this.jj_gen += 1;
/* 2113:1840 */     return this.token;
/* 2114:     */   }
/* 2115:     */   
/* 2116:     */   public final Token getToken(int index)
/* 2117:     */   {
/* 2118:1844 */     Token t = this.lookingAhead ? this.jj_scanpos : this.token;
/* 2119:1845 */     for (int i = 0; i < index; i++) {
/* 2120:1846 */       if (t.next != null) {
/* 2121:1846 */         t = t.next;
/* 2122:     */       } else {
/* 2123:1847 */         t = t.next = this.token_source.getNextToken();
/* 2124:     */       }
/* 2125:     */     }
/* 2126:1849 */     return t;
/* 2127:     */   }
/* 2128:     */   
/* 2129:     */   private final int jj_ntk()
/* 2130:     */   {
/* 2131:1853 */     if ((this.jj_nt = this.token.next) == null) {
/* 2132:1854 */       return this.jj_ntk = (this.token.next = this.token_source.getNextToken()).kind;
/* 2133:     */     }
/* 2134:1856 */     return this.jj_ntk = this.jj_nt.kind;
/* 2135:     */   }
/* 2136:     */   
/* 2137:1859 */   private Vector jj_expentries = new Vector();
/* 2138:     */   private int[] jj_expentry;
/* 2139:1861 */   private int jj_kind = -1;
/* 2140:1862 */   private int[] jj_lasttokens = new int[100];
/* 2141:     */   private int jj_endpos;
/* 2142:     */   
/* 2143:     */   private void jj_add_error_token(int kind, int pos)
/* 2144:     */   {
/* 2145:1866 */     if (pos >= 100) {
/* 2146:1866 */       return;
/* 2147:     */     }
/* 2148:1867 */     if (pos == this.jj_endpos + 1)
/* 2149:     */     {
/* 2150:1868 */       this.jj_lasttokens[(this.jj_endpos++)] = kind;
/* 2151:     */     }
/* 2152:1869 */     else if (this.jj_endpos != 0)
/* 2153:     */     {
/* 2154:1870 */       this.jj_expentry = new int[this.jj_endpos];
/* 2155:1871 */       for (int i = 0; i < this.jj_endpos; i++) {
/* 2156:1872 */         this.jj_expentry[i] = this.jj_lasttokens[i];
/* 2157:     */       }
/* 2158:1874 */       boolean exists = false;
/* 2159:1875 */       for (Enumeration e = this.jj_expentries.elements(); e.hasMoreElements();)
/* 2160:     */       {
/* 2161:1876 */         int[] oldentry = (int[])e.nextElement();
/* 2162:1877 */         if (oldentry.length == this.jj_expentry.length)
/* 2163:     */         {
/* 2164:1878 */           exists = true;
/* 2165:1879 */           for (int i = 0; i < this.jj_expentry.length; i++) {
/* 2166:1880 */             if (oldentry[i] != this.jj_expentry[i])
/* 2167:     */             {
/* 2168:1881 */               exists = false;
/* 2169:1882 */               break;
/* 2170:     */             }
/* 2171:     */           }
/* 2172:1885 */           if (exists) {
/* 2173:     */             break;
/* 2174:     */           }
/* 2175:     */         }
/* 2176:     */       }
/* 2177:1888 */       if (!exists) {
/* 2178:1888 */         this.jj_expentries.addElement(this.jj_expentry);
/* 2179:     */       }
/* 2180:1889 */       if (pos != 0)
/* 2181:     */       {
/* 2182:1889 */         int tmp205_204 = pos;this.jj_endpos = tmp205_204;this.jj_lasttokens[(tmp205_204 - 1)] = kind;
/* 2183:     */       }
/* 2184:     */     }
/* 2185:     */   }
/* 2186:     */   
/* 2187:     */   public ParseException generateParseException()
/* 2188:     */   {
/* 2189:1894 */     this.jj_expentries.removeAllElements();
/* 2190:1895 */     boolean[] la1tokens = new boolean[68];
/* 2191:1896 */     for (int i = 0; i < 68; i++) {
/* 2192:1897 */       la1tokens[i] = false;
/* 2193:     */     }
/* 2194:1899 */     if (this.jj_kind >= 0)
/* 2195:     */     {
/* 2196:1900 */       la1tokens[this.jj_kind] = true;
/* 2197:1901 */       this.jj_kind = -1;
/* 2198:     */     }
/* 2199:1903 */     for (int i = 0; i < 72; i++) {
/* 2200:1904 */       if (this.jj_la1[i] == this.jj_gen) {
/* 2201:1905 */         for (int j = 0; j < 32; j++)
/* 2202:     */         {
/* 2203:1906 */           if ((jj_la1_0[i] & 1 << j) != 0) {
/* 2204:1907 */             la1tokens[j] = true;
/* 2205:     */           }
/* 2206:1909 */           if ((jj_la1_1[i] & 1 << j) != 0) {
/* 2207:1910 */             la1tokens[(32 + j)] = true;
/* 2208:     */           }
/* 2209:1912 */           if ((jj_la1_2[i] & 1 << j) != 0) {
/* 2210:1913 */             la1tokens[(64 + j)] = true;
/* 2211:     */           }
/* 2212:     */         }
/* 2213:     */       }
/* 2214:     */     }
/* 2215:1918 */     for (int i = 0; i < 68; i++) {
/* 2216:1919 */       if (la1tokens[i] != 0)
/* 2217:     */       {
/* 2218:1920 */         this.jj_expentry = new int[1];
/* 2219:1921 */         this.jj_expentry[0] = i;
/* 2220:1922 */         this.jj_expentries.addElement(this.jj_expentry);
/* 2221:     */       }
/* 2222:     */     }
/* 2223:1925 */     this.jj_endpos = 0;
/* 2224:1926 */     jj_rescan_token();
/* 2225:1927 */     jj_add_error_token(0, 0);
/* 2226:1928 */     int[][] exptokseq = new int[this.jj_expentries.size()][];
/* 2227:1929 */     for (int i = 0; i < this.jj_expentries.size(); i++) {
/* 2228:1930 */       exptokseq[i] = ((int[])(int[])this.jj_expentries.elementAt(i));
/* 2229:     */     }
/* 2230:1932 */     return new ParseException(this.token, exptokseq, tokenImage);
/* 2231:     */   }
/* 2232:     */   
/* 2233:     */   private final void jj_rescan_token()
/* 2234:     */   {
/* 2235:1942 */     this.jj_rescan = true;
/* 2236:1943 */     for (int i = 0; i < 1; i++) {
/* 2237:     */       try
/* 2238:     */       {
/* 2239:1945 */         JJCalls p = this.jj_2_rtns[i];
/* 2240:     */         do
/* 2241:     */         {
/* 2242:1947 */           if (p.gen > this.jj_gen)
/* 2243:     */           {
/* 2244:1948 */             this.jj_la = p.arg;this.jj_lastpos = (this.jj_scanpos = p.first);
/* 2245:1949 */             switch (i)
/* 2246:     */             {
/* 2247:     */             case 0: 
/* 2248:1950 */               jj_3_1();
/* 2249:     */             }
/* 2250:     */           }
/* 2251:1953 */           p = p.next;
/* 2252:1954 */         } while (p != null);
/* 2253:     */       }
/* 2254:     */       catch (LookaheadSuccess ls) {}
/* 2255:     */     }
/* 2256:1957 */     this.jj_rescan = false;
/* 2257:     */   }
/* 2258:     */   
/* 2259:     */   private final void jj_save(int index, int xla)
/* 2260:     */   {
/* 2261:1961 */     JJCalls p = this.jj_2_rtns[index];
/* 2262:1962 */     while (p.gen > this.jj_gen)
/* 2263:     */     {
/* 2264:1963 */       if (p.next == null)
/* 2265:     */       {
/* 2266:1963 */         p = p.next = new JJCalls(); break;
/* 2267:     */       }
/* 2268:1964 */       p = p.next;
/* 2269:     */     }
/* 2270:1966 */     p.gen = (this.jj_gen + xla - this.jj_la);p.first = this.token;p.arg = xla;
/* 2271:     */   }
/* 2272:     */   
/* 2273:     */   public final void enable_tracing() {}
/* 2274:     */   
/* 2275:     */   public final void disable_tracing() {}
/* 2276:     */   
/* 2277:     */   static final class JJCalls
/* 2278:     */   {
/* 2279:     */     int gen;
/* 2280:     */     Token first;
/* 2281:     */     int arg;
/* 2282:     */     JJCalls next;
/* 2283:     */   }
/* 2284:     */   
/* 2285:     */   private static final class LookaheadSuccess
/* 2286:     */     extends Error
/* 2287:     */   {}
/* 2288:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.steadystate.css.parser.SACParserCSSmobileOKBasic1
 * JD-Core Version:    0.7.0.1
 */