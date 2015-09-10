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
/*   16:     */ public class SACParserCSS1
/*   17:     */   extends AbstractSACParser
/*   18:     */   implements Parser, SACParserCSS1Constants
/*   19:     */ {
/*   20:  18 */   private boolean _quiet = true;
/*   21:     */   public SACParserCSS1TokenManager token_source;
/*   22:     */   public Token token;
/*   23:     */   public Token jj_nt;
/*   24:     */   private int jj_ntk;
/*   25:     */   private Token jj_scanpos;
/*   26:     */   private Token jj_lastpos;
/*   27:     */   private int jj_la;
/*   28:     */   
/*   29:     */   public SACParserCSS1()
/*   30:     */   {
/*   31:  21 */     this((CharStream)null);
/*   32:     */   }
/*   33:     */   
/*   34:     */   public String getParserVersion()
/*   35:     */   {
/*   36:  25 */     return "http://www.w3.org/TR/REC-CSS1";
/*   37:     */   }
/*   38:     */   
/*   39:     */   protected String getGrammarUri()
/*   40:     */   {
/*   41:  30 */     return "http://www.w3.org/TR/REC-CSS1#appendix-b";
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
/*   54:  50 */       handleStartDocument();
/*   55:  51 */       styleSheetRuleList();
/*   56:  52 */       jj_consume_token(0);
/*   57:     */     }
/*   58:     */     finally
/*   59:     */     {
/*   60:  54 */       handleEndDocument();
/*   61:     */     }
/*   62:     */   }
/*   63:     */   
/*   64:     */   public final void styleSheetRuleList()
/*   65:     */     throws ParseException
/*   66:     */   {
/*   67:     */     for (;;)
/*   68:     */     {
/*   69:  63 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*   70:     */       {
/*   71:     */       case 1: 
/*   72:     */       case 27: 
/*   73:     */       case 28: 
/*   74:     */         break;
/*   75:     */       default: 
/*   76:  70 */         this.jj_la1[0] = this.jj_gen;
/*   77:  71 */         break;
/*   78:     */       }
/*   79:  73 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*   80:     */       {
/*   81:     */       case 1: 
/*   82:  75 */         jj_consume_token(1);
/*   83:  76 */         break;
/*   84:     */       case 27: 
/*   85:  78 */         jj_consume_token(27);
/*   86:  79 */         break;
/*   87:     */       case 28: 
/*   88:  81 */         jj_consume_token(28);
/*   89:     */       }
/*   90:     */     }
/*   91:  84 */     this.jj_la1[1] = this.jj_gen;
/*   92:  85 */     jj_consume_token(-1);
/*   93:  86 */     throw new ParseException();
/*   94:  91 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*   95:     */     {
/*   96:     */     case 29: 
/*   97:     */       break;
/*   98:     */     default: 
/*   99:  96 */       this.jj_la1[2] = this.jj_gen;
/*  100:  97 */       break;
/*  101:     */     }
/*  102:  99 */     importRule();
/*  103:     */     for (;;)
/*  104:     */     {
/*  105: 102 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  106:     */       {
/*  107:     */       case 1: 
/*  108:     */       case 27: 
/*  109:     */       case 28: 
/*  110:     */         break;
/*  111:     */       default: 
/*  112: 109 */         this.jj_la1[3] = this.jj_gen;
/*  113: 110 */         break;
/*  114:     */       }
/*  115: 112 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  116:     */       {
/*  117:     */       case 1: 
/*  118: 114 */         jj_consume_token(1);
/*  119: 115 */         break;
/*  120:     */       case 27: 
/*  121: 117 */         jj_consume_token(27);
/*  122: 118 */         break;
/*  123:     */       case 28: 
/*  124: 120 */         jj_consume_token(28);
/*  125:     */       }
/*  126:     */     }
/*  127: 123 */     this.jj_la1[4] = this.jj_gen;
/*  128: 124 */     jj_consume_token(-1);
/*  129: 125 */     throw new ParseException();
/*  130: 131 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
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
/*  141:     */     case 31: 
/*  142:     */       break;
/*  143:     */     case 10: 
/*  144:     */     case 11: 
/*  145:     */     case 12: 
/*  146:     */     case 14: 
/*  147:     */     case 15: 
/*  148:     */     case 17: 
/*  149:     */     case 18: 
/*  150:     */     case 19: 
/*  151:     */     case 20: 
/*  152:     */     case 21: 
/*  153:     */     case 22: 
/*  154:     */     case 23: 
/*  155:     */     case 24: 
/*  156:     */     case 25: 
/*  157:     */     case 26: 
/*  158:     */     case 27: 
/*  159:     */     case 28: 
/*  160:     */     case 29: 
/*  161:     */     case 30: 
/*  162:     */     default: 
/*  163: 145 */       this.jj_la1[5] = this.jj_gen;
/*  164: 146 */       break;
/*  165:     */     }
/*  166: 148 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  167:     */     {
/*  168:     */     case 3: 
/*  169:     */     case 4: 
/*  170:     */     case 5: 
/*  171:     */     case 6: 
/*  172:     */     case 7: 
/*  173:     */     case 8: 
/*  174:     */     case 9: 
/*  175:     */     case 13: 
/*  176:     */     case 16: 
/*  177: 158 */       styleRule();
/*  178: 159 */       break;
/*  179:     */     case 31: 
/*  180: 161 */       unknownRule();
/*  181: 162 */       break;
/*  182:     */     case 10: 
/*  183:     */     case 11: 
/*  184:     */     case 12: 
/*  185:     */     case 14: 
/*  186:     */     case 15: 
/*  187:     */     case 17: 
/*  188:     */     case 18: 
/*  189:     */     case 19: 
/*  190:     */     case 20: 
/*  191:     */     case 21: 
/*  192:     */     case 22: 
/*  193:     */     case 23: 
/*  194:     */     case 24: 
/*  195:     */     case 25: 
/*  196:     */     case 26: 
/*  197:     */     case 27: 
/*  198:     */     case 28: 
/*  199:     */     case 29: 
/*  200:     */     case 30: 
/*  201:     */     default: 
/*  202: 164 */       this.jj_la1[6] = this.jj_gen;
/*  203: 165 */       jj_consume_token(-1);
/*  204: 166 */       throw new ParseException();
/*  205:     */     }
/*  206:     */     for (;;)
/*  207:     */     {
/*  208: 170 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  209:     */       {
/*  210:     */       case 1: 
/*  211:     */       case 27: 
/*  212:     */       case 28: 
/*  213:     */         break;
/*  214:     */       default: 
/*  215: 177 */         this.jj_la1[7] = this.jj_gen;
/*  216: 178 */         break;
/*  217:     */       }
/*  218: 180 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  219:     */       {
/*  220:     */       case 1: 
/*  221: 182 */         jj_consume_token(1);
/*  222: 183 */         break;
/*  223:     */       case 27: 
/*  224: 185 */         jj_consume_token(27);
/*  225: 186 */         break;
/*  226:     */       case 28: 
/*  227: 188 */         jj_consume_token(28);
/*  228:     */       }
/*  229:     */     }
/*  230: 191 */     this.jj_la1[8] = this.jj_gen;
/*  231: 192 */     jj_consume_token(-1);
/*  232: 193 */     throw new ParseException();
/*  233: 199 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  234:     */     {
/*  235:     */     case 3: 
/*  236:     */     case 4: 
/*  237:     */     case 5: 
/*  238:     */     case 6: 
/*  239:     */     case 7: 
/*  240:     */     case 8: 
/*  241:     */     case 9: 
/*  242:     */     case 13: 
/*  243:     */     case 16: 
/*  244:     */     case 29: 
/*  245:     */     case 31: 
/*  246:     */       break;
/*  247:     */     case 10: 
/*  248:     */     case 11: 
/*  249:     */     case 12: 
/*  250:     */     case 14: 
/*  251:     */     case 15: 
/*  252:     */     case 17: 
/*  253:     */     case 18: 
/*  254:     */     case 19: 
/*  255:     */     case 20: 
/*  256:     */     case 21: 
/*  257:     */     case 22: 
/*  258:     */     case 23: 
/*  259:     */     case 24: 
/*  260:     */     case 25: 
/*  261:     */     case 26: 
/*  262:     */     case 27: 
/*  263:     */     case 28: 
/*  264:     */     case 30: 
/*  265:     */     default: 
/*  266: 214 */       this.jj_la1[9] = this.jj_gen;
/*  267: 215 */       break;
/*  268:     */     }
/*  269: 217 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  270:     */     {
/*  271:     */     case 3: 
/*  272:     */     case 4: 
/*  273:     */     case 5: 
/*  274:     */     case 6: 
/*  275:     */     case 7: 
/*  276:     */     case 8: 
/*  277:     */     case 9: 
/*  278:     */     case 13: 
/*  279:     */     case 16: 
/*  280: 227 */       styleRule();
/*  281: 228 */       break;
/*  282:     */     case 29: 
/*  283: 230 */       importRuleIgnored();
/*  284: 231 */       break;
/*  285:     */     case 31: 
/*  286: 233 */       unknownRule();
/*  287: 234 */       break;
/*  288:     */     case 10: 
/*  289:     */     case 11: 
/*  290:     */     case 12: 
/*  291:     */     case 14: 
/*  292:     */     case 15: 
/*  293:     */     case 17: 
/*  294:     */     case 18: 
/*  295:     */     case 19: 
/*  296:     */     case 20: 
/*  297:     */     case 21: 
/*  298:     */     case 22: 
/*  299:     */     case 23: 
/*  300:     */     case 24: 
/*  301:     */     case 25: 
/*  302:     */     case 26: 
/*  303:     */     case 27: 
/*  304:     */     case 28: 
/*  305:     */     case 30: 
/*  306:     */     default: 
/*  307: 236 */       this.jj_la1[10] = this.jj_gen;
/*  308: 237 */       jj_consume_token(-1);
/*  309: 238 */       throw new ParseException();
/*  310:     */     }
/*  311:     */     for (;;)
/*  312:     */     {
/*  313: 242 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  314:     */       {
/*  315:     */       case 1: 
/*  316:     */       case 27: 
/*  317:     */       case 28: 
/*  318:     */         break;
/*  319:     */       default: 
/*  320: 249 */         this.jj_la1[11] = this.jj_gen;
/*  321: 250 */         break;
/*  322:     */       }
/*  323: 252 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  324:     */       {
/*  325:     */       case 1: 
/*  326: 254 */         jj_consume_token(1);
/*  327: 255 */         break;
/*  328:     */       case 27: 
/*  329: 257 */         jj_consume_token(27);
/*  330: 258 */         break;
/*  331:     */       case 28: 
/*  332: 260 */         jj_consume_token(28);
/*  333:     */       }
/*  334:     */     }
/*  335: 263 */     this.jj_la1[12] = this.jj_gen;
/*  336: 264 */     jj_consume_token(-1);
/*  337: 265 */     throw new ParseException();
/*  338:     */   }
/*  339:     */   
/*  340:     */   public final void styleSheetRuleSingle()
/*  341:     */     throws ParseException
/*  342:     */   {
/*  343: 275 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  344:     */     {
/*  345:     */     case 29: 
/*  346: 277 */       importRule();
/*  347: 278 */       break;
/*  348:     */     case 3: 
/*  349:     */     case 4: 
/*  350:     */     case 5: 
/*  351:     */     case 6: 
/*  352:     */     case 7: 
/*  353:     */     case 8: 
/*  354:     */     case 9: 
/*  355:     */     case 13: 
/*  356:     */     case 16: 
/*  357: 288 */       styleRule();
/*  358: 289 */       break;
/*  359:     */     case 31: 
/*  360: 291 */       unknownRule();
/*  361: 292 */       break;
/*  362:     */     case 10: 
/*  363:     */     case 11: 
/*  364:     */     case 12: 
/*  365:     */     case 14: 
/*  366:     */     case 15: 
/*  367:     */     case 17: 
/*  368:     */     case 18: 
/*  369:     */     case 19: 
/*  370:     */     case 20: 
/*  371:     */     case 21: 
/*  372:     */     case 22: 
/*  373:     */     case 23: 
/*  374:     */     case 24: 
/*  375:     */     case 25: 
/*  376:     */     case 26: 
/*  377:     */     case 27: 
/*  378:     */     case 28: 
/*  379:     */     case 30: 
/*  380:     */     default: 
/*  381: 294 */       this.jj_la1[13] = this.jj_gen;
/*  382: 295 */       jj_consume_token(-1);
/*  383: 296 */       throw new ParseException();
/*  384:     */     }
/*  385:     */   }
/*  386:     */   
/*  387:     */   public final void unknownRule()
/*  388:     */     throws ParseException
/*  389:     */   {
/*  390:     */     try
/*  391:     */     {
/*  392: 304 */       Token t = jj_consume_token(31);
/*  393: 305 */       String s = skip();
/*  394: 306 */       handleIgnorableAtRule(s);
/*  395:     */     }
/*  396:     */     catch (ParseException e)
/*  397:     */     {
/*  398: 308 */       getErrorHandler().error(toCSSParseException("invalidUnknownRule", e));
/*  399:     */     }
/*  400:     */   }
/*  401:     */   
/*  402:     */   public final void importRule()
/*  403:     */     throws ParseException
/*  404:     */   {
/*  405:     */     try
/*  406:     */     {
/*  407: 322 */       jj_consume_token(29);
/*  408:     */       for (;;)
/*  409:     */       {
/*  410: 325 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  411:     */         {
/*  412:     */         case 1: 
/*  413:     */           break;
/*  414:     */         default: 
/*  415: 330 */           this.jj_la1[14] = this.jj_gen;
/*  416: 331 */           break;
/*  417:     */         }
/*  418: 333 */         jj_consume_token(1);
/*  419:     */       }
/*  420:     */       Token t;
/*  421: 335 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  422:     */       {
/*  423:     */       case 24: 
/*  424: 337 */         t = jj_consume_token(24);
/*  425: 338 */         break;
/*  426:     */       case 26: 
/*  427: 340 */         t = jj_consume_token(26);
/*  428: 341 */         break;
/*  429:     */       default: 
/*  430: 343 */         this.jj_la1[15] = this.jj_gen;
/*  431: 344 */         jj_consume_token(-1);
/*  432: 345 */         throw new ParseException();
/*  433:     */       }
/*  434:     */       for (;;)
/*  435:     */       {
/*  436: 349 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  437:     */         {
/*  438:     */         case 1: 
/*  439:     */           break;
/*  440:     */         default: 
/*  441: 354 */           this.jj_la1[16] = this.jj_gen;
/*  442: 355 */           break;
/*  443:     */         }
/*  444: 357 */         jj_consume_token(1);
/*  445:     */       }
/*  446: 359 */       jj_consume_token(14);
/*  447: 360 */       handleImportStyle(unescape(t.image), new SACMediaListImpl(), null);
/*  448:     */     }
/*  449:     */     catch (CSSParseException e)
/*  450:     */     {
/*  451: 362 */       getErrorHandler().error(e);
/*  452: 363 */       error_skipAtRule();
/*  453:     */     }
/*  454:     */     catch (ParseException e)
/*  455:     */     {
/*  456: 365 */       getErrorHandler().error(toCSSParseException("invalidImportRule", e));
/*  457:     */       
/*  458: 367 */       error_skipAtRule();
/*  459:     */     }
/*  460:     */   }
/*  461:     */   
/*  462:     */   public final void importRuleIgnored()
/*  463:     */     throws ParseException
/*  464:     */   {
/*  465: 373 */     ParseException e = generateParseException();
/*  466:     */     try
/*  467:     */     {
/*  468: 375 */       jj_consume_token(29);
/*  469:     */       for (;;)
/*  470:     */       {
/*  471: 378 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  472:     */         {
/*  473:     */         case 1: 
/*  474:     */           break;
/*  475:     */         default: 
/*  476: 383 */           this.jj_la1[17] = this.jj_gen;
/*  477: 384 */           break;
/*  478:     */         }
/*  479: 386 */         jj_consume_token(1);
/*  480:     */       }
/*  481:     */       Token t;
/*  482: 388 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  483:     */       {
/*  484:     */       case 24: 
/*  485: 390 */         t = jj_consume_token(24);
/*  486: 391 */         break;
/*  487:     */       case 26: 
/*  488: 393 */         t = jj_consume_token(26);
/*  489: 394 */         break;
/*  490:     */       default: 
/*  491: 396 */         this.jj_la1[18] = this.jj_gen;
/*  492: 397 */         jj_consume_token(-1);
/*  493: 398 */         throw new ParseException();
/*  494:     */       }
/*  495:     */       for (;;)
/*  496:     */       {
/*  497: 402 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  498:     */         {
/*  499:     */         case 1: 
/*  500:     */           break;
/*  501:     */         default: 
/*  502: 407 */           this.jj_la1[19] = this.jj_gen;
/*  503: 408 */           break;
/*  504:     */         }
/*  505: 410 */         jj_consume_token(1);
/*  506:     */       }
/*  507: 412 */       jj_consume_token(14);
/*  508:     */     }
/*  509:     */     finally
/*  510:     */     {
/*  511: 414 */       getErrorHandler().error(toCSSParseException("invalidImportRuleIgnored", e));
/*  512:     */     }
/*  513:     */   }
/*  514:     */   
/*  515:     */   public final String medium()
/*  516:     */     throws ParseException
/*  517:     */   {
/*  518: 425 */     Token t = jj_consume_token(3);
/*  519:     */     for (;;)
/*  520:     */     {
/*  521: 428 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  522:     */       {
/*  523:     */       case 1: 
/*  524:     */         break;
/*  525:     */       default: 
/*  526: 433 */         this.jj_la1[20] = this.jj_gen;
/*  527: 434 */         break;
/*  528:     */       }
/*  529: 436 */       jj_consume_token(1);
/*  530:     */     }
/*  531: 438 */     handleMedium(t.image);
/*  532: 439 */     return t.image;
/*  533:     */   }
/*  534:     */   
/*  535:     */   public final LexicalUnit operator(LexicalUnit prev)
/*  536:     */     throws ParseException
/*  537:     */   {
/*  538:     */     Token t;
/*  539: 450 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  540:     */     {
/*  541:     */     case 17: 
/*  542: 452 */       t = jj_consume_token(17);
/*  543:     */       for (;;)
/*  544:     */       {
/*  545: 455 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  546:     */         {
/*  547:     */         case 1: 
/*  548:     */           break;
/*  549:     */         default: 
/*  550: 460 */           this.jj_la1[21] = this.jj_gen;
/*  551: 461 */           break;
/*  552:     */         }
/*  553: 463 */         jj_consume_token(1);
/*  554:     */       }
/*  555: 465 */       return new LexicalUnitImpl(prev, (short)4);
/*  556:     */     case 12: 
/*  557: 468 */       t = jj_consume_token(12);
/*  558:     */       for (;;)
/*  559:     */       {
/*  560: 471 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  561:     */         {
/*  562:     */         case 1: 
/*  563:     */           break;
/*  564:     */         default: 
/*  565: 476 */           this.jj_la1[22] = this.jj_gen;
/*  566: 477 */           break;
/*  567:     */         }
/*  568: 479 */         jj_consume_token(1);
/*  569:     */       }
/*  570: 481 */       return new LexicalUnitImpl(prev, (short)0);
/*  571:     */     }
/*  572: 484 */     this.jj_la1[23] = this.jj_gen;
/*  573: 485 */     jj_consume_token(-1);
/*  574: 486 */     throw new ParseException();
/*  575:     */   }
/*  576:     */   
/*  577:     */   public final char unaryOperator()
/*  578:     */     throws ParseException
/*  579:     */   {
/*  580: 497 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  581:     */     {
/*  582:     */     case 19: 
/*  583: 499 */       jj_consume_token(19);
/*  584: 500 */       return '-';
/*  585:     */     case 18: 
/*  586: 503 */       jj_consume_token(18);
/*  587: 504 */       return '+';
/*  588:     */     }
/*  589: 507 */     this.jj_la1[24] = this.jj_gen;
/*  590: 508 */     jj_consume_token(-1);
/*  591: 509 */     throw new ParseException();
/*  592:     */   }
/*  593:     */   
/*  594:     */   public final String property()
/*  595:     */     throws ParseException
/*  596:     */   {
/*  597: 521 */     Token t = jj_consume_token(3);
/*  598:     */     for (;;)
/*  599:     */     {
/*  600: 524 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  601:     */       {
/*  602:     */       case 1: 
/*  603:     */         break;
/*  604:     */       default: 
/*  605: 529 */         this.jj_la1[25] = this.jj_gen;
/*  606: 530 */         break;
/*  607:     */       }
/*  608: 532 */       jj_consume_token(1);
/*  609:     */     }
/*  610: 534 */     return unescape(t.image);
/*  611:     */   }
/*  612:     */   
/*  613:     */   public final void styleRule()
/*  614:     */     throws ParseException
/*  615:     */   {
/*  616: 545 */     SelectorList selList = null;
/*  617: 546 */     boolean start = false;
/*  618: 547 */     boolean noError = true;
/*  619:     */     try
/*  620:     */     {
/*  621: 549 */       selList = selectorList();
/*  622: 550 */       jj_consume_token(10);
/*  623:     */       for (;;)
/*  624:     */       {
/*  625: 553 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  626:     */         {
/*  627:     */         case 1: 
/*  628:     */           break;
/*  629:     */         default: 
/*  630: 558 */           this.jj_la1[26] = this.jj_gen;
/*  631: 559 */           break;
/*  632:     */         }
/*  633: 561 */         jj_consume_token(1);
/*  634:     */       }
/*  635: 563 */       start = true;
/*  636: 564 */       handleStartSelector(selList);
/*  637: 565 */       declaration();
/*  638:     */       for (;;)
/*  639:     */       {
/*  640: 568 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  641:     */         {
/*  642:     */         case 14: 
/*  643:     */           break;
/*  644:     */         default: 
/*  645: 573 */           this.jj_la1[27] = this.jj_gen;
/*  646: 574 */           break;
/*  647:     */         }
/*  648: 576 */         jj_consume_token(14);
/*  649:     */         for (;;)
/*  650:     */         {
/*  651: 579 */           switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  652:     */           {
/*  653:     */           case 1: 
/*  654:     */             break;
/*  655:     */           default: 
/*  656: 584 */             this.jj_la1[28] = this.jj_gen;
/*  657: 585 */             break;
/*  658:     */           }
/*  659: 587 */           jj_consume_token(1);
/*  660:     */         }
/*  661: 589 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  662:     */         {
/*  663:     */         case 3: 
/*  664: 591 */           declaration();
/*  665: 592 */           break;
/*  666:     */         default: 
/*  667: 594 */           this.jj_la1[29] = this.jj_gen;
/*  668:     */         }
/*  669:     */       }
/*  670: 598 */       jj_consume_token(11);
/*  671:     */     }
/*  672:     */     catch (CSSParseException e)
/*  673:     */     {
/*  674: 600 */       getErrorHandler().error(e);
/*  675: 601 */       noError = false;
/*  676: 602 */       error_skipblock();
/*  677:     */     }
/*  678:     */     catch (ParseException e)
/*  679:     */     {
/*  680: 604 */       getErrorHandler().error(toCSSParseException("invalidStyleRule", e));
/*  681: 605 */       noError = false;
/*  682: 606 */       error_skipblock();
/*  683:     */     }
/*  684:     */     finally
/*  685:     */     {
/*  686: 608 */       if (start) {
/*  687: 609 */         handleEndSelector(selList);
/*  688:     */       }
/*  689:     */     }
/*  690:     */   }
/*  691:     */   
/*  692:     */   public final SelectorList selectorList()
/*  693:     */     throws ParseException
/*  694:     */   {
/*  695: 615 */     SelectorListImpl selList = new SelectorListImpl();
/*  696:     */     
/*  697: 617 */     Selector sel = selector();
/*  698:     */     for (;;)
/*  699:     */     {
/*  700: 620 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  701:     */       {
/*  702:     */       case 12: 
/*  703:     */         break;
/*  704:     */       default: 
/*  705: 625 */         this.jj_la1[30] = this.jj_gen;
/*  706: 626 */         break;
/*  707:     */       }
/*  708: 628 */       jj_consume_token(12);
/*  709:     */       for (;;)
/*  710:     */       {
/*  711: 631 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  712:     */         {
/*  713:     */         case 1: 
/*  714:     */           break;
/*  715:     */         default: 
/*  716: 636 */           this.jj_la1[31] = this.jj_gen;
/*  717: 637 */           break;
/*  718:     */         }
/*  719: 639 */         jj_consume_token(1);
/*  720:     */       }
/*  721: 641 */       selList.add(sel);
/*  722: 642 */       sel = selector();
/*  723:     */     }
/*  724:     */     for (;;)
/*  725:     */     {
/*  726: 646 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  727:     */       {
/*  728:     */       case 1: 
/*  729:     */         break;
/*  730:     */       default: 
/*  731: 651 */         this.jj_la1[32] = this.jj_gen;
/*  732: 652 */         break;
/*  733:     */       }
/*  734: 654 */       jj_consume_token(1);
/*  735:     */     }
/*  736: 656 */     selList.add(sel);
/*  737: 657 */     return selList;
/*  738:     */   }
/*  739:     */   
/*  740:     */   public final Selector selector()
/*  741:     */     throws ParseException
/*  742:     */   {
/*  743:     */     try
/*  744:     */     {
/*  745: 669 */       Selector sel = simpleSelector(null, ' ');
/*  746: 672 */       while (jj_2_1(2))
/*  747:     */       {
/*  748: 677 */         jj_consume_token(1);
/*  749: 678 */         sel = simpleSelector(sel, ' ');
/*  750:     */       }
/*  751: 680 */       handleSelector(sel);
/*  752: 681 */       return sel;
/*  753:     */     }
/*  754:     */     catch (ParseException e)
/*  755:     */     {
/*  756: 683 */       throw toCSSParseException("invalidSelector", e);
/*  757:     */     }
/*  758:     */   }
/*  759:     */   
/*  760:     */   public final Selector simpleSelector(Selector sel, char comb)
/*  761:     */     throws ParseException
/*  762:     */   {
/*  763: 698 */     SimpleSelector simpleSel = null;
/*  764: 699 */     Condition c = null;
/*  765:     */     try
/*  766:     */     {
/*  767: 701 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  768:     */       {
/*  769:     */       case 3: 
/*  770:     */       case 16: 
/*  771: 704 */         simpleSel = elementName();
/*  772: 705 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  773:     */         {
/*  774:     */         case 9: 
/*  775: 707 */           c = hash(c);
/*  776: 708 */           break;
/*  777:     */         default: 
/*  778: 710 */           this.jj_la1[33] = this.jj_gen;
/*  779:     */         }
/*  780: 713 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  781:     */         {
/*  782:     */         case 13: 
/*  783: 715 */           c = _class(c);
/*  784: 716 */           break;
/*  785:     */         default: 
/*  786: 718 */           this.jj_la1[34] = this.jj_gen;
/*  787:     */         }
/*  788: 721 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  789:     */         {
/*  790:     */         case 4: 
/*  791:     */         case 5: 
/*  792:     */         case 6: 
/*  793: 725 */           c = pseudoClass(c);
/*  794: 726 */           break;
/*  795:     */         default: 
/*  796: 728 */           this.jj_la1[35] = this.jj_gen;
/*  797:     */         }
/*  798: 731 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  799:     */         {
/*  800:     */         case 7: 
/*  801:     */         case 8: 
/*  802: 734 */           c = pseudoElement(c);
/*  803: 735 */           break;
/*  804:     */         default: 
/*  805: 737 */           this.jj_la1[36] = this.jj_gen;
/*  806:     */         }
/*  807: 740 */         break;
/*  808:     */       case 9: 
/*  809: 742 */         simpleSel = getSelectorFactory().createElementSelector(null, null);
/*  810: 743 */         c = hash(c);
/*  811: 744 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  812:     */         {
/*  813:     */         case 13: 
/*  814: 746 */           c = _class(c);
/*  815: 747 */           break;
/*  816:     */         default: 
/*  817: 749 */           this.jj_la1[37] = this.jj_gen;
/*  818:     */         }
/*  819: 752 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  820:     */         {
/*  821:     */         case 4: 
/*  822:     */         case 5: 
/*  823:     */         case 6: 
/*  824: 756 */           c = pseudoClass(c);
/*  825: 757 */           break;
/*  826:     */         default: 
/*  827: 759 */           this.jj_la1[38] = this.jj_gen;
/*  828:     */         }
/*  829: 762 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  830:     */         {
/*  831:     */         case 7: 
/*  832:     */         case 8: 
/*  833: 765 */           c = pseudoElement(c);
/*  834: 766 */           break;
/*  835:     */         default: 
/*  836: 768 */           this.jj_la1[39] = this.jj_gen;
/*  837:     */         }
/*  838: 771 */         break;
/*  839:     */       case 13: 
/*  840: 773 */         simpleSel = getSelectorFactory().createElementSelector(null, null);
/*  841: 774 */         c = _class(c);
/*  842: 775 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  843:     */         {
/*  844:     */         case 4: 
/*  845:     */         case 5: 
/*  846:     */         case 6: 
/*  847: 779 */           c = pseudoClass(c);
/*  848: 780 */           break;
/*  849:     */         default: 
/*  850: 782 */           this.jj_la1[40] = this.jj_gen;
/*  851:     */         }
/*  852: 785 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  853:     */         {
/*  854:     */         case 7: 
/*  855:     */         case 8: 
/*  856: 788 */           c = pseudoElement(c);
/*  857: 789 */           break;
/*  858:     */         default: 
/*  859: 791 */           this.jj_la1[41] = this.jj_gen;
/*  860:     */         }
/*  861: 794 */         break;
/*  862:     */       case 4: 
/*  863:     */       case 5: 
/*  864:     */       case 6: 
/*  865: 798 */         simpleSel = getSelectorFactory().createElementSelector(null, null);
/*  866: 799 */         c = pseudoClass(c);
/*  867: 800 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  868:     */         {
/*  869:     */         case 7: 
/*  870:     */         case 8: 
/*  871: 803 */           c = pseudoElement(c);
/*  872: 804 */           break;
/*  873:     */         default: 
/*  874: 806 */           this.jj_la1[42] = this.jj_gen;
/*  875:     */         }
/*  876: 809 */         break;
/*  877:     */       case 7: 
/*  878:     */       case 8: 
/*  879: 812 */         simpleSel = getSelectorFactory().createElementSelector(null, null);
/*  880: 813 */         c = pseudoElement(c);
/*  881: 814 */         break;
/*  882:     */       case 10: 
/*  883:     */       case 11: 
/*  884:     */       case 12: 
/*  885:     */       case 14: 
/*  886:     */       case 15: 
/*  887:     */       default: 
/*  888: 816 */         this.jj_la1[43] = this.jj_gen;
/*  889: 817 */         jj_consume_token(-1);
/*  890: 818 */         throw new ParseException();
/*  891:     */       }
/*  892: 820 */       if (c != null) {
/*  893: 821 */         simpleSel = getSelectorFactory().createConditionalSelector(simpleSel, c);
/*  894:     */       }
/*  895: 824 */       if (sel != null) {
/*  896: 825 */         switch (comb)
/*  897:     */         {
/*  898:     */         case ' ': 
/*  899: 827 */           sel = getSelectorFactory().createDescendantSelector(sel, simpleSel);
/*  900:     */         }
/*  901:     */       }
/*  902: 831 */       return simpleSel;
/*  903:     */     }
/*  904:     */     catch (ParseException e)
/*  905:     */     {
/*  906: 836 */       throw toCSSParseException("invalidSimpleSelector", e);
/*  907:     */     }
/*  908:     */   }
/*  909:     */   
/*  910:     */   public final Condition _class(Condition pred)
/*  911:     */     throws ParseException
/*  912:     */   {
/*  913:     */     try
/*  914:     */     {
/*  915: 849 */       jj_consume_token(13);
/*  916: 850 */       Token t = jj_consume_token(3);
/*  917: 851 */       Condition c = getConditionFactory().createClassCondition(null, t.image);
/*  918: 852 */       return pred == null ? c : getConditionFactory().createAndCondition(pred, c);
/*  919:     */     }
/*  920:     */     catch (ParseException e)
/*  921:     */     {
/*  922: 854 */       throw toCSSParseException("invalidClassSelector", e);
/*  923:     */     }
/*  924:     */   }
/*  925:     */   
/*  926:     */   public final SimpleSelector elementName()
/*  927:     */     throws ParseException
/*  928:     */   {
/*  929:     */     try
/*  930:     */     {
/*  931: 867 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  932:     */       {
/*  933:     */       case 3: 
/*  934: 869 */         Token t = jj_consume_token(3);
/*  935: 870 */         return getSelectorFactory().createElementSelector(null, unescape(t.image));
/*  936:     */       case 16: 
/*  937: 873 */         jj_consume_token(16);
/*  938: 874 */         return getSelectorFactory().createElementSelector(null, null);
/*  939:     */       }
/*  940: 877 */       this.jj_la1[44] = this.jj_gen;
/*  941: 878 */       jj_consume_token(-1);
/*  942: 879 */       throw new ParseException();
/*  943:     */     }
/*  944:     */     catch (ParseException e)
/*  945:     */     {
/*  946: 882 */       throw toCSSParseException("invalidElementName", e);
/*  947:     */     }
/*  948:     */   }
/*  949:     */   
/*  950:     */   public final Condition pseudoClass(Condition pred)
/*  951:     */     throws ParseException
/*  952:     */   {
/*  953:     */     try
/*  954:     */     {
/*  955:     */       Token t;
/*  956: 896 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  957:     */       {
/*  958:     */       case 4: 
/*  959: 898 */         t = jj_consume_token(4);
/*  960: 899 */         break;
/*  961:     */       case 5: 
/*  962: 901 */         t = jj_consume_token(5);
/*  963: 902 */         break;
/*  964:     */       case 6: 
/*  965: 904 */         t = jj_consume_token(6);
/*  966: 905 */         break;
/*  967:     */       default: 
/*  968: 907 */         this.jj_la1[45] = this.jj_gen;
/*  969: 908 */         jj_consume_token(-1);
/*  970: 909 */         throw new ParseException();
/*  971:     */       }
/*  972: 913 */       String s = t.image;
/*  973: 914 */       Condition c = getConditionFactory().createPseudoClassCondition(null, s);
/*  974: 915 */       return pred == null ? c : getConditionFactory().createAndCondition(pred, c);
/*  975:     */     }
/*  976:     */     catch (ParseException e)
/*  977:     */     {
/*  978: 919 */       throw toCSSParseException("invalidPseudoClass", e);
/*  979:     */     }
/*  980:     */   }
/*  981:     */   
/*  982:     */   public final Condition pseudoElement(Condition pred)
/*  983:     */     throws ParseException
/*  984:     */   {
/*  985:     */     try
/*  986:     */     {
/*  987:     */       Token t;
/*  988: 933 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  989:     */       {
/*  990:     */       case 8: 
/*  991: 935 */         t = jj_consume_token(8);
/*  992: 936 */         break;
/*  993:     */       case 7: 
/*  994: 938 */         t = jj_consume_token(7);
/*  995:     */         
/*  996: 940 */         String s = t.image;
/*  997:     */         
/*  998: 942 */         Condition c = getConditionFactory().createPseudoClassCondition(null, s);
/*  999: 943 */         return pred == null ? c : getConditionFactory().createAndCondition(pred, c);
/* 1000:     */       default: 
/* 1001: 948 */         this.jj_la1[46] = this.jj_gen;
/* 1002: 949 */         jj_consume_token(-1);
/* 1003: 950 */         throw new ParseException();
/* 1004:     */       }
/* 1005:     */     }
/* 1006:     */     catch (ParseException e)
/* 1007:     */     {
/* 1008: 953 */       throw toCSSParseException("invalidPseudoElement", e);
/* 1009:     */     }
/* 1010: 955 */     throw new Error("Missing return statement in function");
/* 1011:     */   }
/* 1012:     */   
/* 1013:     */   public final Condition hash(Condition pred)
/* 1014:     */     throws ParseException
/* 1015:     */   {
/* 1016:     */     try
/* 1017:     */     {
/* 1018: 961 */       Token t = jj_consume_token(9);
/* 1019: 962 */       Condition c = getConditionFactory().createIdCondition(t.image.substring(1));
/* 1020: 963 */       return pred == null ? c : getConditionFactory().createAndCondition(pred, c);
/* 1021:     */     }
/* 1022:     */     catch (ParseException e)
/* 1023:     */     {
/* 1024: 965 */       throw toCSSParseException("invalidHash", e);
/* 1025:     */     }
/* 1026:     */   }
/* 1027:     */   
/* 1028:     */   public final void styleDeclaration()
/* 1029:     */     throws ParseException
/* 1030:     */   {
/* 1031: 971 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1032:     */     {
/* 1033:     */     case 3: 
/* 1034: 973 */       declaration();
/* 1035: 974 */       break;
/* 1036:     */     default: 
/* 1037: 976 */       this.jj_la1[47] = this.jj_gen;
/* 1038:     */     }
/* 1039:     */     for (;;)
/* 1040:     */     {
/* 1041: 981 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1042:     */       {
/* 1043:     */       case 14: 
/* 1044:     */         break;
/* 1045:     */       default: 
/* 1046: 986 */         this.jj_la1[48] = this.jj_gen;
/* 1047: 987 */         break;
/* 1048:     */       }
/* 1049: 989 */       jj_consume_token(14);
/* 1050:     */       for (;;)
/* 1051:     */       {
/* 1052: 992 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1053:     */         {
/* 1054:     */         case 1: 
/* 1055:     */           break;
/* 1056:     */         default: 
/* 1057: 997 */           this.jj_la1[49] = this.jj_gen;
/* 1058: 998 */           break;
/* 1059:     */         }
/* 1060:1000 */         jj_consume_token(1);
/* 1061:     */       }
/* 1062:1002 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1063:     */       {
/* 1064:     */       case 3: 
/* 1065:1004 */         declaration();
/* 1066:1005 */         break;
/* 1067:     */       default: 
/* 1068:1007 */         this.jj_la1[50] = this.jj_gen;
/* 1069:     */       }
/* 1070:     */     }
/* 1071:     */   }
/* 1072:     */   
/* 1073:     */   public final void declaration()
/* 1074:     */     throws ParseException
/* 1075:     */   {
/* 1076:1022 */     boolean priority = false;
/* 1077:     */     try
/* 1078:     */     {
/* 1079:1024 */       String p = property();
/* 1080:1025 */       jj_consume_token(15);
/* 1081:     */       for (;;)
/* 1082:     */       {
/* 1083:1028 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1084:     */         {
/* 1085:     */         case 1: 
/* 1086:     */           break;
/* 1087:     */         default: 
/* 1088:1033 */           this.jj_la1[51] = this.jj_gen;
/* 1089:1034 */           break;
/* 1090:     */         }
/* 1091:1036 */         jj_consume_token(1);
/* 1092:     */       }
/* 1093:1038 */       LexicalUnit e = expr();
/* 1094:1039 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1095:     */       {
/* 1096:     */       case 30: 
/* 1097:1041 */         priority = prio();
/* 1098:1042 */         break;
/* 1099:     */       default: 
/* 1100:1044 */         this.jj_la1[52] = this.jj_gen;
/* 1101:     */       }
/* 1102:1047 */       handleProperty(p, e, priority);
/* 1103:     */     }
/* 1104:     */     catch (CSSParseException ex)
/* 1105:     */     {
/* 1106:1049 */       getErrorHandler().error(ex);
/* 1107:1050 */       error_skipdecl();
/* 1108:     */     }
/* 1109:     */     catch (ParseException ex)
/* 1110:     */     {
/* 1111:1052 */       getErrorHandler().error(toCSSParseException("invalidDeclaration", ex));
/* 1112:1053 */       error_skipdecl();
/* 1113:     */     }
/* 1114:     */   }
/* 1115:     */   
/* 1116:     */   public final boolean prio()
/* 1117:     */     throws ParseException
/* 1118:     */   {
/* 1119:1063 */     jj_consume_token(30);
/* 1120:     */     for (;;)
/* 1121:     */     {
/* 1122:1066 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1123:     */       {
/* 1124:     */       case 1: 
/* 1125:     */         break;
/* 1126:     */       default: 
/* 1127:1071 */         this.jj_la1[53] = this.jj_gen;
/* 1128:1072 */         break;
/* 1129:     */       }
/* 1130:1074 */       jj_consume_token(1);
/* 1131:     */     }
/* 1132:1076 */     return true;
/* 1133:     */   }
/* 1134:     */   
/* 1135:     */   public final LexicalUnit expr()
/* 1136:     */     throws ParseException
/* 1137:     */   {
/* 1138:     */     try
/* 1139:     */     {
/* 1140:1091 */       LexicalUnit head = term(null);
/* 1141:1092 */       LexicalUnit body = head;
/* 1142:     */       for (;;)
/* 1143:     */       {
/* 1144:1095 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1145:     */         {
/* 1146:     */         case 3: 
/* 1147:     */         case 9: 
/* 1148:     */         case 12: 
/* 1149:     */         case 17: 
/* 1150:     */         case 18: 
/* 1151:     */         case 19: 
/* 1152:     */         case 24: 
/* 1153:     */         case 26: 
/* 1154:     */         case 32: 
/* 1155:     */         case 33: 
/* 1156:     */         case 34: 
/* 1157:     */         case 35: 
/* 1158:     */         case 36: 
/* 1159:     */         case 37: 
/* 1160:     */         case 38: 
/* 1161:     */         case 39: 
/* 1162:     */         case 40: 
/* 1163:     */         case 41: 
/* 1164:     */         case 42: 
/* 1165:     */         case 46: 
/* 1166:     */           break;
/* 1167:     */         case 4: 
/* 1168:     */         case 5: 
/* 1169:     */         case 6: 
/* 1170:     */         case 7: 
/* 1171:     */         case 8: 
/* 1172:     */         case 10: 
/* 1173:     */         case 11: 
/* 1174:     */         case 13: 
/* 1175:     */         case 14: 
/* 1176:     */         case 15: 
/* 1177:     */         case 16: 
/* 1178:     */         case 20: 
/* 1179:     */         case 21: 
/* 1180:     */         case 22: 
/* 1181:     */         case 23: 
/* 1182:     */         case 25: 
/* 1183:     */         case 27: 
/* 1184:     */         case 28: 
/* 1185:     */         case 29: 
/* 1186:     */         case 30: 
/* 1187:     */         case 31: 
/* 1188:     */         case 43: 
/* 1189:     */         case 44: 
/* 1190:     */         case 45: 
/* 1191:     */         default: 
/* 1192:1119 */           this.jj_la1[54] = this.jj_gen;
/* 1193:1120 */           break;
/* 1194:     */         }
/* 1195:1122 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1196:     */         {
/* 1197:     */         case 12: 
/* 1198:     */         case 17: 
/* 1199:1125 */           body = operator(body);
/* 1200:1126 */           break;
/* 1201:     */         default: 
/* 1202:1128 */           this.jj_la1[55] = this.jj_gen;
/* 1203:     */         }
/* 1204:1131 */         body = term(body);
/* 1205:     */       }
/* 1206:1133 */       return head;
/* 1207:     */     }
/* 1208:     */     catch (ParseException ex)
/* 1209:     */     {
/* 1210:1135 */       throw toCSSParseException("invalidExpr", ex);
/* 1211:     */     }
/* 1212:     */   }
/* 1213:     */   
/* 1214:     */   public final LexicalUnit term(LexicalUnit prev)
/* 1215:     */     throws ParseException
/* 1216:     */   {
/* 1217:1149 */     char op = ' ';
/* 1218:     */     
/* 1219:1151 */     LexicalUnit value = null;
/* 1220:1152 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1221:     */     {
/* 1222:     */     case 18: 
/* 1223:     */     case 19: 
/* 1224:1155 */       op = unaryOperator();
/* 1225:1156 */       break;
/* 1226:     */     default: 
/* 1227:1158 */       this.jj_la1[56] = this.jj_gen;
/* 1228:     */     }
/* 1229:     */     Token t;
/* 1230:1161 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1231:     */     {
/* 1232:     */     case 32: 
/* 1233:     */     case 33: 
/* 1234:     */     case 34: 
/* 1235:     */     case 35: 
/* 1236:     */     case 36: 
/* 1237:     */     case 37: 
/* 1238:     */     case 38: 
/* 1239:     */     case 39: 
/* 1240:     */     case 40: 
/* 1241:     */     case 41: 
/* 1242:1172 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1243:     */       {
/* 1244:     */       case 41: 
/* 1245:1174 */         t = jj_consume_token(41);
/* 1246:     */         try
/* 1247:     */         {
/* 1248:1177 */           value = LexicalUnitImpl.createNumber(prev, intValue(op, t.image));
/* 1249:     */         }
/* 1250:     */         catch (NumberFormatException e)
/* 1251:     */         {
/* 1252:1181 */           value = LexicalUnitImpl.createNumber(prev, floatValue(op, t.image));
/* 1253:     */         }
/* 1254:     */       case 40: 
/* 1255:1185 */         t = jj_consume_token(40);
/* 1256:1186 */         value = LexicalUnitImpl.createPercentage(prev, floatValue(op, t.image));
/* 1257:1187 */         break;
/* 1258:     */       case 34: 
/* 1259:1189 */         t = jj_consume_token(34);
/* 1260:1190 */         value = LexicalUnitImpl.createPixel(prev, floatValue(op, t.image));
/* 1261:1191 */         break;
/* 1262:     */       case 35: 
/* 1263:1193 */         t = jj_consume_token(35);
/* 1264:1194 */         value = LexicalUnitImpl.createCentimeter(prev, floatValue(op, t.image));
/* 1265:1195 */         break;
/* 1266:     */       case 36: 
/* 1267:1197 */         t = jj_consume_token(36);
/* 1268:1198 */         value = LexicalUnitImpl.createMillimeter(prev, floatValue(op, t.image));
/* 1269:1199 */         break;
/* 1270:     */       case 37: 
/* 1271:1201 */         t = jj_consume_token(37);
/* 1272:1202 */         value = LexicalUnitImpl.createInch(prev, floatValue(op, t.image));
/* 1273:1203 */         break;
/* 1274:     */       case 38: 
/* 1275:1205 */         t = jj_consume_token(38);
/* 1276:1206 */         value = LexicalUnitImpl.createPoint(prev, floatValue(op, t.image));
/* 1277:1207 */         break;
/* 1278:     */       case 39: 
/* 1279:1209 */         t = jj_consume_token(39);
/* 1280:1210 */         value = LexicalUnitImpl.createPica(prev, floatValue(op, t.image));
/* 1281:1211 */         break;
/* 1282:     */       case 32: 
/* 1283:1213 */         t = jj_consume_token(32);
/* 1284:1214 */         value = LexicalUnitImpl.createEm(prev, floatValue(op, t.image));
/* 1285:1215 */         break;
/* 1286:     */       case 33: 
/* 1287:1217 */         t = jj_consume_token(33);
/* 1288:1218 */         value = LexicalUnitImpl.createEx(prev, floatValue(op, t.image));
/* 1289:1219 */         break;
/* 1290:     */       default: 
/* 1291:1221 */         this.jj_la1[57] = this.jj_gen;
/* 1292:1222 */         jj_consume_token(-1);
/* 1293:1223 */         throw new ParseException();
/* 1294:     */       }
/* 1295:     */       break;
/* 1296:     */     case 24: 
/* 1297:1227 */       t = jj_consume_token(24);
/* 1298:1228 */       value = new LexicalUnitImpl(prev, (short)36, t.image);
/* 1299:1229 */       break;
/* 1300:     */     case 3: 
/* 1301:1231 */       t = jj_consume_token(3);
/* 1302:1232 */       value = new LexicalUnitImpl(prev, (short)35, t.image);
/* 1303:1233 */       break;
/* 1304:     */     case 26: 
/* 1305:1235 */       t = jj_consume_token(26);
/* 1306:1236 */       value = new LexicalUnitImpl(prev, (short)24, t.image);
/* 1307:1237 */       break;
/* 1308:     */     case 46: 
/* 1309:1239 */       t = jj_consume_token(46);
/* 1310:1240 */       value = new LexicalUnitImpl(prev, (short)39, t.image);
/* 1311:1241 */       break;
/* 1312:     */     case 42: 
/* 1313:1243 */       value = rgb(prev);
/* 1314:1244 */       break;
/* 1315:     */     case 9: 
/* 1316:1246 */       value = hexcolor(prev);
/* 1317:1247 */       break;
/* 1318:     */     case 4: 
/* 1319:     */     case 5: 
/* 1320:     */     case 6: 
/* 1321:     */     case 7: 
/* 1322:     */     case 8: 
/* 1323:     */     case 10: 
/* 1324:     */     case 11: 
/* 1325:     */     case 12: 
/* 1326:     */     case 13: 
/* 1327:     */     case 14: 
/* 1328:     */     case 15: 
/* 1329:     */     case 16: 
/* 1330:     */     case 17: 
/* 1331:     */     case 18: 
/* 1332:     */     case 19: 
/* 1333:     */     case 20: 
/* 1334:     */     case 21: 
/* 1335:     */     case 22: 
/* 1336:     */     case 23: 
/* 1337:     */     case 25: 
/* 1338:     */     case 27: 
/* 1339:     */     case 28: 
/* 1340:     */     case 29: 
/* 1341:     */     case 30: 
/* 1342:     */     case 31: 
/* 1343:     */     case 43: 
/* 1344:     */     case 44: 
/* 1345:     */     case 45: 
/* 1346:     */     default: 
/* 1347:1249 */       this.jj_la1[58] = this.jj_gen;
/* 1348:1250 */       jj_consume_token(-1);
/* 1349:1251 */       throw new ParseException();
/* 1350:     */     }
/* 1351:     */     for (;;)
/* 1352:     */     {
/* 1353:1255 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1354:     */       {
/* 1355:     */       case 1: 
/* 1356:     */         break;
/* 1357:     */       default: 
/* 1358:1260 */         this.jj_la1[59] = this.jj_gen;
/* 1359:1261 */         break;
/* 1360:     */       }
/* 1361:1263 */       jj_consume_token(1);
/* 1362:     */     }
/* 1363:1265 */     if ((value instanceof LexicalUnitImpl)) {
/* 1364:1267 */       ((LexicalUnitImpl)value).setLocator(getLocator());
/* 1365:     */     }
/* 1366:1269 */     return value;
/* 1367:     */   }
/* 1368:     */   
/* 1369:     */   public final LexicalUnit rgb(LexicalUnit prev)
/* 1370:     */     throws ParseException
/* 1371:     */   {
/* 1372:1281 */     Token t = jj_consume_token(42);
/* 1373:     */     for (;;)
/* 1374:     */     {
/* 1375:1284 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 1376:     */       {
/* 1377:     */       case 1: 
/* 1378:     */         break;
/* 1379:     */       default: 
/* 1380:1289 */         this.jj_la1[60] = this.jj_gen;
/* 1381:1290 */         break;
/* 1382:     */       }
/* 1383:1292 */       jj_consume_token(1);
/* 1384:     */     }
/* 1385:1294 */     LexicalUnit params = expr();
/* 1386:1295 */     jj_consume_token(25);
/* 1387:1296 */     return LexicalUnitImpl.createRgbColor(prev, params);
/* 1388:     */   }
/* 1389:     */   
/* 1390:     */   public final LexicalUnit hexcolor(LexicalUnit prev)
/* 1391:     */     throws ParseException
/* 1392:     */   {
/* 1393:1307 */     Token t = jj_consume_token(9);
/* 1394:1308 */     return hexcolorInternal(prev, t);
/* 1395:     */   }
/* 1396:     */   
/* 1397:     */   void skipSelector()
/* 1398:     */     throws ParseException
/* 1399:     */   {
/* 1400:1313 */     Token t = getToken(1);
/* 1401:1314 */     while ((t.kind != 12) && (t.kind != 14) && (t.kind != 10) && (t.kind != 0))
/* 1402:     */     {
/* 1403:1315 */       getNextToken();
/* 1404:1316 */       t = getToken(1);
/* 1405:     */     }
/* 1406:     */   }
/* 1407:     */   
/* 1408:     */   String skip()
/* 1409:     */     throws ParseException
/* 1410:     */   {
/* 1411:1321 */     StringBuffer sb = new StringBuffer();
/* 1412:1322 */     int nesting = 0;
/* 1413:1323 */     Token t = getToken(0);
/* 1414:1324 */     if (t.image != null) {
/* 1415:1325 */       sb.append(t.image);
/* 1416:     */     }
/* 1417:     */     do
/* 1418:     */     {
/* 1419:1328 */       t = getNextToken();
/* 1420:1329 */       if (t.kind == 0) {
/* 1421:     */         break;
/* 1422:     */       }
/* 1423:1331 */       sb.append(t.image);
/* 1424:1332 */       if (t.kind == 10) {
/* 1425:1333 */         nesting++;
/* 1426:1334 */       } else if (t.kind == 11) {
/* 1427:1335 */         nesting--;
/* 1428:     */       } else {
/* 1429:1336 */         if ((t.kind == 14) && (nesting <= 0)) {
/* 1430:     */           break;
/* 1431:     */         }
/* 1432:     */       }
/* 1433:1339 */     } while ((t.kind != 11) || (nesting > 0));
/* 1434:1341 */     return sb.toString();
/* 1435:     */   }
/* 1436:     */   
/* 1437:     */   void error_skipblock()
/* 1438:     */     throws ParseException
/* 1439:     */   {
/* 1440:1346 */     int nesting = 0;
/* 1441:     */     Token t;
/* 1442:     */     do
/* 1443:     */     {
/* 1444:1349 */       t = getNextToken();
/* 1445:1350 */       if (t.kind == 10) {
/* 1446:1352 */         nesting++;
/* 1447:1354 */       } else if (t.kind == 11) {
/* 1448:1356 */         nesting--;
/* 1449:     */       } else {
/* 1450:1358 */         if (t.kind == 0) {
/* 1451:     */           break;
/* 1452:     */         }
/* 1453:     */       }
/* 1454:1363 */     } while ((t.kind != 11) || (nesting > 0));
/* 1455:     */   }
/* 1456:     */   
/* 1457:     */   void error_skipdecl()
/* 1458:     */     throws ParseException
/* 1459:     */   {
/* 1460:1367 */     int nesting = 0;
/* 1461:1368 */     Token t = getToken(1);
/* 1462:1369 */     if (t.kind == 10)
/* 1463:     */     {
/* 1464:1371 */       error_skipblock();
/* 1465:     */     }
/* 1466:     */     else
/* 1467:     */     {
/* 1468:1375 */       Token oldToken = t;
/* 1469:1376 */       while ((t.kind != 14) && (t.kind != 11) && (t.kind != 0))
/* 1470:     */       {
/* 1471:1378 */         oldToken = t;
/* 1472:1379 */         t = getNextToken();
/* 1473:     */       }
/* 1474:1381 */       if (t.kind == 11) {
/* 1475:1383 */         this.token = oldToken;
/* 1476:     */       }
/* 1477:     */     }
/* 1478:     */   }
/* 1479:     */   
/* 1480:     */   void error_skipAtRule()
/* 1481:     */     throws ParseException
/* 1482:     */   {
/* 1483:1389 */     Token t = null;
/* 1484:     */     do
/* 1485:     */     {
/* 1486:1392 */       t = getNextToken();
/* 1487:1394 */     } while (t.kind != 14);
/* 1488:     */   }
/* 1489:     */   
/* 1490:     */   private final boolean jj_2_1(int xla)
/* 1491:     */   {
/* 1492:1398 */     this.jj_la = xla;this.jj_lastpos = (this.jj_scanpos = this.token);
/* 1493:     */     try
/* 1494:     */     {
/* 1495:1399 */       return !jj_3_1();
/* 1496:     */     }
/* 1497:     */     catch (LookaheadSuccess ls)
/* 1498:     */     {
/* 1499:1400 */       return true;
/* 1500:     */     }
/* 1501:     */     finally
/* 1502:     */     {
/* 1503:1401 */       jj_save(0, xla);
/* 1504:     */     }
/* 1505:     */   }
/* 1506:     */   
/* 1507:     */   private final boolean jj_3R_43()
/* 1508:     */   {
/* 1509:1405 */     if (jj_scan_token(7)) {
/* 1510:1405 */       return true;
/* 1511:     */     }
/* 1512:1406 */     return false;
/* 1513:     */   }
/* 1514:     */   
/* 1515:     */   private final boolean jj_3R_39()
/* 1516:     */   {
/* 1517:1411 */     Token xsp = this.jj_scanpos;
/* 1518:1412 */     if (jj_scan_token(4))
/* 1519:     */     {
/* 1520:1413 */       this.jj_scanpos = xsp;
/* 1521:1414 */       if (jj_scan_token(5))
/* 1522:     */       {
/* 1523:1415 */         this.jj_scanpos = xsp;
/* 1524:1416 */         if (jj_scan_token(6)) {
/* 1525:1416 */           return true;
/* 1526:     */         }
/* 1527:     */       }
/* 1528:     */     }
/* 1529:1419 */     return false;
/* 1530:     */   }
/* 1531:     */   
/* 1532:     */   private final boolean jj_3R_36()
/* 1533:     */   {
/* 1534:1424 */     Token xsp = this.jj_scanpos;
/* 1535:1425 */     if (jj_3R_41())
/* 1536:     */     {
/* 1537:1426 */       this.jj_scanpos = xsp;
/* 1538:1427 */       if (jj_3R_42()) {
/* 1539:1427 */         return true;
/* 1540:     */       }
/* 1541:     */     }
/* 1542:1429 */     return false;
/* 1543:     */   }
/* 1544:     */   
/* 1545:     */   private final boolean jj_3R_38()
/* 1546:     */   {
/* 1547:1433 */     if (jj_scan_token(13)) {
/* 1548:1433 */       return true;
/* 1549:     */     }
/* 1550:1434 */     return false;
/* 1551:     */   }
/* 1552:     */   
/* 1553:     */   private final boolean jj_3_1()
/* 1554:     */   {
/* 1555:1438 */     if (jj_scan_token(1)) {
/* 1556:1438 */       return true;
/* 1557:     */     }
/* 1558:1439 */     if (jj_3R_30()) {
/* 1559:1439 */       return true;
/* 1560:     */     }
/* 1561:1440 */     return false;
/* 1562:     */   }
/* 1563:     */   
/* 1564:     */   private final boolean jj_3R_33()
/* 1565:     */   {
/* 1566:1444 */     if (jj_3R_38()) {
/* 1567:1444 */       return true;
/* 1568:     */     }
/* 1569:1445 */     return false;
/* 1570:     */   }
/* 1571:     */   
/* 1572:     */   private final boolean jj_3R_37()
/* 1573:     */   {
/* 1574:1449 */     if (jj_scan_token(9)) {
/* 1575:1449 */       return true;
/* 1576:     */     }
/* 1577:1450 */     return false;
/* 1578:     */   }
/* 1579:     */   
/* 1580:     */   private final boolean jj_3R_30()
/* 1581:     */   {
/* 1582:1455 */     Token xsp = this.jj_scanpos;
/* 1583:1456 */     if (jj_3R_31())
/* 1584:     */     {
/* 1585:1457 */       this.jj_scanpos = xsp;
/* 1586:1458 */       if (jj_3R_32())
/* 1587:     */       {
/* 1588:1459 */         this.jj_scanpos = xsp;
/* 1589:1460 */         if (jj_3R_33())
/* 1590:     */         {
/* 1591:1461 */           this.jj_scanpos = xsp;
/* 1592:1462 */           if (jj_3R_34())
/* 1593:     */           {
/* 1594:1463 */             this.jj_scanpos = xsp;
/* 1595:1464 */             if (jj_3R_35()) {
/* 1596:1464 */               return true;
/* 1597:     */             }
/* 1598:     */           }
/* 1599:     */         }
/* 1600:     */       }
/* 1601:     */     }
/* 1602:1469 */     return false;
/* 1603:     */   }
/* 1604:     */   
/* 1605:     */   private final boolean jj_3R_40()
/* 1606:     */   {
/* 1607:1474 */     Token xsp = this.jj_scanpos;
/* 1608:1475 */     if (jj_scan_token(8))
/* 1609:     */     {
/* 1610:1476 */       this.jj_scanpos = xsp;
/* 1611:1477 */       if (jj_3R_43()) {
/* 1612:1477 */         return true;
/* 1613:     */       }
/* 1614:     */     }
/* 1615:1479 */     return false;
/* 1616:     */   }
/* 1617:     */   
/* 1618:     */   private final boolean jj_3R_35()
/* 1619:     */   {
/* 1620:1483 */     if (jj_3R_40()) {
/* 1621:1483 */       return true;
/* 1622:     */     }
/* 1623:1484 */     return false;
/* 1624:     */   }
/* 1625:     */   
/* 1626:     */   private final boolean jj_3R_32()
/* 1627:     */   {
/* 1628:1488 */     if (jj_3R_37()) {
/* 1629:1488 */       return true;
/* 1630:     */     }
/* 1631:1489 */     return false;
/* 1632:     */   }
/* 1633:     */   
/* 1634:     */   private final boolean jj_3R_42()
/* 1635:     */   {
/* 1636:1493 */     if (jj_scan_token(16)) {
/* 1637:1493 */       return true;
/* 1638:     */     }
/* 1639:1494 */     return false;
/* 1640:     */   }
/* 1641:     */   
/* 1642:     */   private final boolean jj_3R_41()
/* 1643:     */   {
/* 1644:1498 */     if (jj_scan_token(3)) {
/* 1645:1498 */       return true;
/* 1646:     */     }
/* 1647:1499 */     return false;
/* 1648:     */   }
/* 1649:     */   
/* 1650:     */   private final boolean jj_3R_34()
/* 1651:     */   {
/* 1652:1503 */     if (jj_3R_39()) {
/* 1653:1503 */       return true;
/* 1654:     */     }
/* 1655:1504 */     return false;
/* 1656:     */   }
/* 1657:     */   
/* 1658:     */   private final boolean jj_3R_31()
/* 1659:     */   {
/* 1660:1508 */     if (jj_3R_36()) {
/* 1661:1508 */       return true;
/* 1662:     */     }
/* 1663:1509 */     return false;
/* 1664:     */   }
/* 1665:     */   
/* 1666:1517 */   public boolean lookingAhead = false;
/* 1667:     */   private boolean jj_semLA;
/* 1668:     */   private int jj_gen;
/* 1669:1520 */   private final int[] jj_la1 = new int[61];
/* 1670:     */   private static int[] jj_la1_0;
/* 1671:     */   private static int[] jj_la1_1;
/* 1672:     */   private static int[] jj_la1_2;
/* 1673:     */   
/* 1674:     */   static
/* 1675:     */   {
/* 1676:1525 */     jj_la1_0();
/* 1677:1526 */     jj_la1_1();
/* 1678:1527 */     jj_la1_2();
/* 1679:     */   }
/* 1680:     */   
/* 1681:     */   private static void jj_la1_0()
/* 1682:     */   {
/* 1683:1530 */     jj_la1_0 = new int[] { 402653186, 402653186, 536870912, 402653186, 402653186, -2147408904, -2147408904, 402653186, 402653186, -1610537992, -1610537992, 402653186, 402653186, -1610537992, 2, 83886080, 2, 2, 83886080, 2, 2, 2, 2, 135168, 786432, 2, 2, 16384, 2, 8, 4096, 2, 2, 512, 8192, 112, 384, 8192, 112, 384, 112, 384, 384, 74744, 65544, 112, 384, 8, 16384, 2, 8, 2, 1073741824, 2, 84808200, 135168, 786432, 0, 83886600, 2, 2 };
/* 1684:     */   }
/* 1685:     */   
/* 1686:     */   private static void jj_la1_1()
/* 1687:     */   {
/* 1688:1533 */     jj_la1_1 = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 18431, 0, 0, 1023, 18431, 0, 0 };
/* 1689:     */   }
/* 1690:     */   
/* 1691:     */   private static void jj_la1_2()
/* 1692:     */   {
/* 1693:1536 */     jj_la1_2 = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
/* 1694:     */   }
/* 1695:     */   
/* 1696:1538 */   private final JJCalls[] jj_2_rtns = new JJCalls[1];
/* 1697:1539 */   private boolean jj_rescan = false;
/* 1698:1540 */   private int jj_gc = 0;
/* 1699:     */   
/* 1700:     */   public SACParserCSS1(CharStream stream)
/* 1701:     */   {
/* 1702:1543 */     this.token_source = new SACParserCSS1TokenManager(stream);
/* 1703:1544 */     this.token = new Token();
/* 1704:1545 */     this.jj_ntk = -1;
/* 1705:1546 */     this.jj_gen = 0;
/* 1706:1547 */     for (int i = 0; i < 61; i++) {
/* 1707:1547 */       this.jj_la1[i] = -1;
/* 1708:     */     }
/* 1709:1548 */     for (int i = 0; i < this.jj_2_rtns.length; i++) {
/* 1710:1548 */       this.jj_2_rtns[i] = new JJCalls();
/* 1711:     */     }
/* 1712:     */   }
/* 1713:     */   
/* 1714:     */   public void ReInit(CharStream stream)
/* 1715:     */   {
/* 1716:1552 */     this.token_source.ReInit(stream);
/* 1717:1553 */     this.token = new Token();
/* 1718:1554 */     this.jj_ntk = -1;
/* 1719:1555 */     this.jj_gen = 0;
/* 1720:1556 */     for (int i = 0; i < 61; i++) {
/* 1721:1556 */       this.jj_la1[i] = -1;
/* 1722:     */     }
/* 1723:1557 */     for (int i = 0; i < this.jj_2_rtns.length; i++) {
/* 1724:1557 */       this.jj_2_rtns[i] = new JJCalls();
/* 1725:     */     }
/* 1726:     */   }
/* 1727:     */   
/* 1728:     */   public SACParserCSS1(SACParserCSS1TokenManager tm)
/* 1729:     */   {
/* 1730:1561 */     this.token_source = tm;
/* 1731:1562 */     this.token = new Token();
/* 1732:1563 */     this.jj_ntk = -1;
/* 1733:1564 */     this.jj_gen = 0;
/* 1734:1565 */     for (int i = 0; i < 61; i++) {
/* 1735:1565 */       this.jj_la1[i] = -1;
/* 1736:     */     }
/* 1737:1566 */     for (int i = 0; i < this.jj_2_rtns.length; i++) {
/* 1738:1566 */       this.jj_2_rtns[i] = new JJCalls();
/* 1739:     */     }
/* 1740:     */   }
/* 1741:     */   
/* 1742:     */   public void ReInit(SACParserCSS1TokenManager tm)
/* 1743:     */   {
/* 1744:1570 */     this.token_source = tm;
/* 1745:1571 */     this.token = new Token();
/* 1746:1572 */     this.jj_ntk = -1;
/* 1747:1573 */     this.jj_gen = 0;
/* 1748:1574 */     for (int i = 0; i < 61; i++) {
/* 1749:1574 */       this.jj_la1[i] = -1;
/* 1750:     */     }
/* 1751:1575 */     for (int i = 0; i < this.jj_2_rtns.length; i++) {
/* 1752:1575 */       this.jj_2_rtns[i] = new JJCalls();
/* 1753:     */     }
/* 1754:     */   }
/* 1755:     */   
/* 1756:     */   private final Token jj_consume_token(int kind)
/* 1757:     */     throws ParseException
/* 1758:     */   {
/* 1759:     */     Token oldToken;
/* 1760:1580 */     if ((oldToken = this.token).next != null) {
/* 1761:1580 */       this.token = this.token.next;
/* 1762:     */     } else {
/* 1763:1581 */       this.token = (this.token.next = this.token_source.getNextToken());
/* 1764:     */     }
/* 1765:1582 */     this.jj_ntk = -1;
/* 1766:1583 */     if (this.token.kind == kind)
/* 1767:     */     {
/* 1768:1584 */       this.jj_gen += 1;
/* 1769:1585 */       if (++this.jj_gc > 100)
/* 1770:     */       {
/* 1771:1586 */         this.jj_gc = 0;
/* 1772:1587 */         for (int i = 0; i < this.jj_2_rtns.length; i++)
/* 1773:     */         {
/* 1774:1588 */           JJCalls c = this.jj_2_rtns[i];
/* 1775:1589 */           while (c != null)
/* 1776:     */           {
/* 1777:1590 */             if (c.gen < this.jj_gen) {
/* 1778:1590 */               c.first = null;
/* 1779:     */             }
/* 1780:1591 */             c = c.next;
/* 1781:     */           }
/* 1782:     */         }
/* 1783:     */       }
/* 1784:1595 */       return this.token;
/* 1785:     */     }
/* 1786:1597 */     this.token = oldToken;
/* 1787:1598 */     this.jj_kind = kind;
/* 1788:1599 */     throw generateParseException();
/* 1789:     */   }
/* 1790:     */   
/* 1791:1603 */   private final LookaheadSuccess jj_ls = new LookaheadSuccess(null);
/* 1792:     */   
/* 1793:     */   private final boolean jj_scan_token(int kind)
/* 1794:     */   {
/* 1795:1605 */     if (this.jj_scanpos == this.jj_lastpos)
/* 1796:     */     {
/* 1797:1606 */       this.jj_la -= 1;
/* 1798:1607 */       if (this.jj_scanpos.next == null) {
/* 1799:1608 */         this.jj_lastpos = (this.jj_scanpos = this.jj_scanpos.next = this.token_source.getNextToken());
/* 1800:     */       } else {
/* 1801:1610 */         this.jj_lastpos = (this.jj_scanpos = this.jj_scanpos.next);
/* 1802:     */       }
/* 1803:     */     }
/* 1804:     */     else
/* 1805:     */     {
/* 1806:1613 */       this.jj_scanpos = this.jj_scanpos.next;
/* 1807:     */     }
/* 1808:1615 */     if (this.jj_rescan)
/* 1809:     */     {
/* 1810:1616 */       int i = 0;
/* 1811:1616 */       for (Token tok = this.token; (tok != null) && (tok != this.jj_scanpos); tok = tok.next) {
/* 1812:1617 */         i++;
/* 1813:     */       }
/* 1814:1618 */       if (tok != null) {
/* 1815:1618 */         jj_add_error_token(kind, i);
/* 1816:     */       }
/* 1817:     */     }
/* 1818:1620 */     if (this.jj_scanpos.kind != kind) {
/* 1819:1620 */       return true;
/* 1820:     */     }
/* 1821:1621 */     if ((this.jj_la == 0) && (this.jj_scanpos == this.jj_lastpos)) {
/* 1822:1621 */       throw this.jj_ls;
/* 1823:     */     }
/* 1824:1622 */     return false;
/* 1825:     */   }
/* 1826:     */   
/* 1827:     */   public final Token getNextToken()
/* 1828:     */   {
/* 1829:1626 */     if (this.token.next != null) {
/* 1830:1626 */       this.token = this.token.next;
/* 1831:     */     } else {
/* 1832:1627 */       this.token = (this.token.next = this.token_source.getNextToken());
/* 1833:     */     }
/* 1834:1628 */     this.jj_ntk = -1;
/* 1835:1629 */     this.jj_gen += 1;
/* 1836:1630 */     return this.token;
/* 1837:     */   }
/* 1838:     */   
/* 1839:     */   public final Token getToken(int index)
/* 1840:     */   {
/* 1841:1634 */     Token t = this.lookingAhead ? this.jj_scanpos : this.token;
/* 1842:1635 */     for (int i = 0; i < index; i++) {
/* 1843:1636 */       if (t.next != null) {
/* 1844:1636 */         t = t.next;
/* 1845:     */       } else {
/* 1846:1637 */         t = t.next = this.token_source.getNextToken();
/* 1847:     */       }
/* 1848:     */     }
/* 1849:1639 */     return t;
/* 1850:     */   }
/* 1851:     */   
/* 1852:     */   private final int jj_ntk()
/* 1853:     */   {
/* 1854:1643 */     if ((this.jj_nt = this.token.next) == null) {
/* 1855:1644 */       return this.jj_ntk = (this.token.next = this.token_source.getNextToken()).kind;
/* 1856:     */     }
/* 1857:1646 */     return this.jj_ntk = this.jj_nt.kind;
/* 1858:     */   }
/* 1859:     */   
/* 1860:1649 */   private Vector jj_expentries = new Vector();
/* 1861:     */   private int[] jj_expentry;
/* 1862:1651 */   private int jj_kind = -1;
/* 1863:1652 */   private int[] jj_lasttokens = new int[100];
/* 1864:     */   private int jj_endpos;
/* 1865:     */   
/* 1866:     */   private void jj_add_error_token(int kind, int pos)
/* 1867:     */   {
/* 1868:1656 */     if (pos >= 100) {
/* 1869:1656 */       return;
/* 1870:     */     }
/* 1871:1657 */     if (pos == this.jj_endpos + 1)
/* 1872:     */     {
/* 1873:1658 */       this.jj_lasttokens[(this.jj_endpos++)] = kind;
/* 1874:     */     }
/* 1875:1659 */     else if (this.jj_endpos != 0)
/* 1876:     */     {
/* 1877:1660 */       this.jj_expentry = new int[this.jj_endpos];
/* 1878:1661 */       for (int i = 0; i < this.jj_endpos; i++) {
/* 1879:1662 */         this.jj_expentry[i] = this.jj_lasttokens[i];
/* 1880:     */       }
/* 1881:1664 */       boolean exists = false;
/* 1882:1665 */       for (Enumeration e = this.jj_expentries.elements(); e.hasMoreElements();)
/* 1883:     */       {
/* 1884:1666 */         int[] oldentry = (int[])e.nextElement();
/* 1885:1667 */         if (oldentry.length == this.jj_expentry.length)
/* 1886:     */         {
/* 1887:1668 */           exists = true;
/* 1888:1669 */           for (int i = 0; i < this.jj_expentry.length; i++) {
/* 1889:1670 */             if (oldentry[i] != this.jj_expentry[i])
/* 1890:     */             {
/* 1891:1671 */               exists = false;
/* 1892:1672 */               break;
/* 1893:     */             }
/* 1894:     */           }
/* 1895:1675 */           if (exists) {
/* 1896:     */             break;
/* 1897:     */           }
/* 1898:     */         }
/* 1899:     */       }
/* 1900:1678 */       if (!exists) {
/* 1901:1678 */         this.jj_expentries.addElement(this.jj_expentry);
/* 1902:     */       }
/* 1903:1679 */       if (pos != 0)
/* 1904:     */       {
/* 1905:1679 */         int tmp205_204 = pos;this.jj_endpos = tmp205_204;this.jj_lasttokens[(tmp205_204 - 1)] = kind;
/* 1906:     */       }
/* 1907:     */     }
/* 1908:     */   }
/* 1909:     */   
/* 1910:     */   public ParseException generateParseException()
/* 1911:     */   {
/* 1912:1684 */     this.jj_expentries.removeAllElements();
/* 1913:1685 */     boolean[] la1tokens = new boolean[67];
/* 1914:1686 */     for (int i = 0; i < 67; i++) {
/* 1915:1687 */       la1tokens[i] = false;
/* 1916:     */     }
/* 1917:1689 */     if (this.jj_kind >= 0)
/* 1918:     */     {
/* 1919:1690 */       la1tokens[this.jj_kind] = true;
/* 1920:1691 */       this.jj_kind = -1;
/* 1921:     */     }
/* 1922:1693 */     for (int i = 0; i < 61; i++) {
/* 1923:1694 */       if (this.jj_la1[i] == this.jj_gen) {
/* 1924:1695 */         for (int j = 0; j < 32; j++)
/* 1925:     */         {
/* 1926:1696 */           if ((jj_la1_0[i] & 1 << j) != 0) {
/* 1927:1697 */             la1tokens[j] = true;
/* 1928:     */           }
/* 1929:1699 */           if ((jj_la1_1[i] & 1 << j) != 0) {
/* 1930:1700 */             la1tokens[(32 + j)] = true;
/* 1931:     */           }
/* 1932:1702 */           if ((jj_la1_2[i] & 1 << j) != 0) {
/* 1933:1703 */             la1tokens[(64 + j)] = true;
/* 1934:     */           }
/* 1935:     */         }
/* 1936:     */       }
/* 1937:     */     }
/* 1938:1708 */     for (int i = 0; i < 67; i++) {
/* 1939:1709 */       if (la1tokens[i] != 0)
/* 1940:     */       {
/* 1941:1710 */         this.jj_expentry = new int[1];
/* 1942:1711 */         this.jj_expentry[0] = i;
/* 1943:1712 */         this.jj_expentries.addElement(this.jj_expentry);
/* 1944:     */       }
/* 1945:     */     }
/* 1946:1715 */     this.jj_endpos = 0;
/* 1947:1716 */     jj_rescan_token();
/* 1948:1717 */     jj_add_error_token(0, 0);
/* 1949:1718 */     int[][] exptokseq = new int[this.jj_expentries.size()][];
/* 1950:1719 */     for (int i = 0; i < this.jj_expentries.size(); i++) {
/* 1951:1720 */       exptokseq[i] = ((int[])(int[])this.jj_expentries.elementAt(i));
/* 1952:     */     }
/* 1953:1722 */     return new ParseException(this.token, exptokseq, tokenImage);
/* 1954:     */   }
/* 1955:     */   
/* 1956:     */   private final void jj_rescan_token()
/* 1957:     */   {
/* 1958:1732 */     this.jj_rescan = true;
/* 1959:1733 */     for (int i = 0; i < 1; i++) {
/* 1960:     */       try
/* 1961:     */       {
/* 1962:1735 */         JJCalls p = this.jj_2_rtns[i];
/* 1963:     */         do
/* 1964:     */         {
/* 1965:1737 */           if (p.gen > this.jj_gen)
/* 1966:     */           {
/* 1967:1738 */             this.jj_la = p.arg;this.jj_lastpos = (this.jj_scanpos = p.first);
/* 1968:1739 */             switch (i)
/* 1969:     */             {
/* 1970:     */             case 0: 
/* 1971:1740 */               jj_3_1();
/* 1972:     */             }
/* 1973:     */           }
/* 1974:1743 */           p = p.next;
/* 1975:1744 */         } while (p != null);
/* 1976:     */       }
/* 1977:     */       catch (LookaheadSuccess ls) {}
/* 1978:     */     }
/* 1979:1747 */     this.jj_rescan = false;
/* 1980:     */   }
/* 1981:     */   
/* 1982:     */   private final void jj_save(int index, int xla)
/* 1983:     */   {
/* 1984:1751 */     JJCalls p = this.jj_2_rtns[index];
/* 1985:1752 */     while (p.gen > this.jj_gen)
/* 1986:     */     {
/* 1987:1753 */       if (p.next == null)
/* 1988:     */       {
/* 1989:1753 */         p = p.next = new JJCalls(); break;
/* 1990:     */       }
/* 1991:1754 */       p = p.next;
/* 1992:     */     }
/* 1993:1756 */     p.gen = (this.jj_gen + xla - this.jj_la);p.first = this.token;p.arg = xla;
/* 1994:     */   }
/* 1995:     */   
/* 1996:     */   public void mediaList(SACMediaListImpl ml) {}
/* 1997:     */   
/* 1998:     */   public final void enable_tracing() {}
/* 1999:     */   
/* 2000:     */   public final void disable_tracing() {}
/* 2001:     */   
/* 2002:     */   static final class JJCalls
/* 2003:     */   {
/* 2004:     */     int gen;
/* 2005:     */     Token first;
/* 2006:     */     int arg;
/* 2007:     */     JJCalls next;
/* 2008:     */   }
/* 2009:     */   
/* 2010:     */   private static final class LookaheadSuccess
/* 2011:     */     extends Error
/* 2012:     */   {}
/* 2013:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.steadystate.css.parser.SACParserCSS1
 * JD-Core Version:    0.7.0.1
 */