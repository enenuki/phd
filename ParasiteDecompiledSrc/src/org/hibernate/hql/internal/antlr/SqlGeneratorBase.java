/*    1:     */ package org.hibernate.hql.internal.antlr;
/*    2:     */ 
/*    3:     */ import antlr.MismatchedTokenException;
/*    4:     */ import antlr.NoViableAltException;
/*    5:     */ import antlr.RecognitionException;
/*    6:     */ import antlr.TreeParser;
/*    7:     */ import antlr.TreeParserSharedInputState;
/*    8:     */ import antlr.collections.AST;
/*    9:     */ import antlr.collections.impl.BitSet;
/*   10:     */ 
/*   11:     */ public class SqlGeneratorBase
/*   12:     */   extends TreeParser
/*   13:     */   implements SqlTokenTypes
/*   14:     */ {
/*   15:  31 */   private StringBuffer buf = new StringBuffer();
/*   16:     */   
/*   17:     */   protected void out(String s)
/*   18:     */   {
/*   19:  34 */     this.buf.append(s);
/*   20:     */   }
/*   21:     */   
/*   22:     */   protected int getLastChar()
/*   23:     */   {
/*   24:  41 */     int len = this.buf.length();
/*   25:  42 */     if (len == 0) {
/*   26:  43 */       return -1;
/*   27:     */     }
/*   28:  45 */     return this.buf.charAt(len - 1);
/*   29:     */   }
/*   30:     */   
/*   31:     */   protected void optionalSpace() {}
/*   32:     */   
/*   33:     */   protected void out(AST n)
/*   34:     */   {
/*   35:  56 */     out(n.getText());
/*   36:     */   }
/*   37:     */   
/*   38:     */   protected void separator(AST n, String sep)
/*   39:     */   {
/*   40:  60 */     if (n.getNextSibling() != null) {
/*   41:  61 */       out(sep);
/*   42:     */     }
/*   43:     */   }
/*   44:     */   
/*   45:     */   protected boolean hasText(AST a)
/*   46:     */   {
/*   47:  65 */     String t = a.getText();
/*   48:  66 */     return (t != null) && (t.length() > 0);
/*   49:     */   }
/*   50:     */   
/*   51:     */   protected void fromFragmentSeparator(AST a) {}
/*   52:     */   
/*   53:     */   protected void nestedFromFragment(AST d, AST parent) {}
/*   54:     */   
/*   55:     */   protected StringBuffer getStringBuffer()
/*   56:     */   {
/*   57:  78 */     return this.buf;
/*   58:     */   }
/*   59:     */   
/*   60:     */   protected void nyi(AST n)
/*   61:     */   {
/*   62:  82 */     throw new UnsupportedOperationException("Unsupported node: " + n);
/*   63:     */   }
/*   64:     */   
/*   65:     */   protected void beginFunctionTemplate(AST m, AST i)
/*   66:     */   {
/*   67:  87 */     out(i);
/*   68:  88 */     out("(");
/*   69:     */   }
/*   70:     */   
/*   71:     */   protected void endFunctionTemplate(AST m)
/*   72:     */   {
/*   73:  92 */     out(")");
/*   74:     */   }
/*   75:     */   
/*   76:     */   protected void commaBetweenParameters(String comma)
/*   77:     */   {
/*   78:  96 */     out(comma);
/*   79:     */   }
/*   80:     */   
/*   81:     */   public SqlGeneratorBase()
/*   82:     */   {
/*   83:  99 */     this.tokenNames = _tokenNames;
/*   84:     */   }
/*   85:     */   
/*   86:     */   public final void statement(AST _t)
/*   87:     */     throws RecognitionException
/*   88:     */   {
/*   89: 104 */     AST statement_AST_in = _t == ASTNULL ? null : _t;
/*   90:     */     try
/*   91:     */     {
/*   92: 107 */       if (_t == null) {
/*   93: 107 */         _t = ASTNULL;
/*   94:     */       }
/*   95: 108 */       switch (_t.getType())
/*   96:     */       {
/*   97:     */       case 45: 
/*   98: 111 */         selectStatement(_t);
/*   99: 112 */         _t = this._retTree;
/*  100: 113 */         break;
/*  101:     */       case 51: 
/*  102: 117 */         updateStatement(_t);
/*  103: 118 */         _t = this._retTree;
/*  104: 119 */         break;
/*  105:     */       case 13: 
/*  106: 123 */         deleteStatement(_t);
/*  107: 124 */         _t = this._retTree;
/*  108: 125 */         break;
/*  109:     */       case 29: 
/*  110: 129 */         insertStatement(_t);
/*  111: 130 */         _t = this._retTree;
/*  112: 131 */         break;
/*  113:     */       default: 
/*  114: 135 */         throw new NoViableAltException(_t);
/*  115:     */       }
/*  116:     */     }
/*  117:     */     catch (RecognitionException ex)
/*  118:     */     {
/*  119: 140 */       if (this.inputState.guessing == 0)
/*  120:     */       {
/*  121: 141 */         reportError(ex);
/*  122: 142 */         if (_t != null) {
/*  123: 142 */           _t = _t.getNextSibling();
/*  124:     */         }
/*  125:     */       }
/*  126:     */       else
/*  127:     */       {
/*  128: 144 */         throw ex;
/*  129:     */       }
/*  130:     */     }
/*  131: 147 */     this._retTree = _t;
/*  132:     */   }
/*  133:     */   
/*  134:     */   public final void selectStatement(AST _t)
/*  135:     */     throws RecognitionException
/*  136:     */   {
/*  137: 152 */     AST selectStatement_AST_in = _t == ASTNULL ? null : _t;
/*  138:     */     try
/*  139:     */     {
/*  140: 155 */       AST __t3 = _t;
/*  141: 156 */       AST tmp1_AST_in = _t;
/*  142: 157 */       match(_t, 45);
/*  143: 158 */       _t = _t.getFirstChild();
/*  144: 159 */       if (this.inputState.guessing == 0) {
/*  145: 160 */         out("select ");
/*  146:     */       }
/*  147: 162 */       selectClause(_t);
/*  148: 163 */       _t = this._retTree;
/*  149: 164 */       from(_t);
/*  150: 165 */       _t = this._retTree;
/*  151: 167 */       if (_t == null) {
/*  152: 167 */         _t = ASTNULL;
/*  153:     */       }
/*  154: 168 */       switch (_t.getType())
/*  155:     */       {
/*  156:     */       case 53: 
/*  157: 171 */         AST __t5 = _t;
/*  158: 172 */         AST tmp2_AST_in = _t;
/*  159: 173 */         match(_t, 53);
/*  160: 174 */         _t = _t.getFirstChild();
/*  161: 175 */         if (this.inputState.guessing == 0) {
/*  162: 176 */           out(" where ");
/*  163:     */         }
/*  164: 178 */         whereExpr(_t);
/*  165: 179 */         _t = this._retTree;
/*  166: 180 */         _t = __t5;
/*  167: 181 */         _t = _t.getNextSibling();
/*  168: 182 */         break;
/*  169:     */       case 3: 
/*  170:     */       case 24: 
/*  171:     */       case 41: 
/*  172:     */         break;
/*  173:     */       default: 
/*  174: 192 */         throw new NoViableAltException(_t);
/*  175:     */       }
/*  176: 197 */       if (_t == null) {
/*  177: 197 */         _t = ASTNULL;
/*  178:     */       }
/*  179: 198 */       switch (_t.getType())
/*  180:     */       {
/*  181:     */       case 24: 
/*  182: 201 */         AST __t7 = _t;
/*  183: 202 */         AST tmp3_AST_in = _t;
/*  184: 203 */         match(_t, 24);
/*  185: 204 */         _t = _t.getFirstChild();
/*  186: 205 */         if (this.inputState.guessing == 0) {
/*  187: 206 */           out(" group by ");
/*  188:     */         }
/*  189: 208 */         groupExprs(_t);
/*  190: 209 */         _t = this._retTree;
/*  191: 211 */         if (_t == null) {
/*  192: 211 */           _t = ASTNULL;
/*  193:     */         }
/*  194: 212 */         switch (_t.getType())
/*  195:     */         {
/*  196:     */         case 25: 
/*  197: 215 */           AST __t9 = _t;
/*  198: 216 */           AST tmp4_AST_in = _t;
/*  199: 217 */           match(_t, 25);
/*  200: 218 */           _t = _t.getFirstChild();
/*  201: 219 */           if (this.inputState.guessing == 0) {
/*  202: 220 */             out(" having ");
/*  203:     */           }
/*  204: 222 */           booleanExpr(_t, false);
/*  205: 223 */           _t = this._retTree;
/*  206: 224 */           _t = __t9;
/*  207: 225 */           _t = _t.getNextSibling();
/*  208: 226 */           break;
/*  209:     */         case 3: 
/*  210:     */           break;
/*  211:     */         default: 
/*  212: 234 */           throw new NoViableAltException(_t);
/*  213:     */         }
/*  214: 238 */         _t = __t7;
/*  215: 239 */         _t = _t.getNextSibling();
/*  216: 240 */         break;
/*  217:     */       case 3: 
/*  218:     */       case 41: 
/*  219:     */         break;
/*  220:     */       default: 
/*  221: 249 */         throw new NoViableAltException(_t);
/*  222:     */       }
/*  223: 254 */       if (_t == null) {
/*  224: 254 */         _t = ASTNULL;
/*  225:     */       }
/*  226: 255 */       switch (_t.getType())
/*  227:     */       {
/*  228:     */       case 41: 
/*  229: 258 */         AST __t11 = _t;
/*  230: 259 */         AST tmp5_AST_in = _t;
/*  231: 260 */         match(_t, 41);
/*  232: 261 */         _t = _t.getFirstChild();
/*  233: 262 */         if (this.inputState.guessing == 0) {
/*  234: 263 */           out(" order by ");
/*  235:     */         }
/*  236: 265 */         orderExprs(_t);
/*  237: 266 */         _t = this._retTree;
/*  238: 267 */         _t = __t11;
/*  239: 268 */         _t = _t.getNextSibling();
/*  240: 269 */         break;
/*  241:     */       case 3: 
/*  242:     */         break;
/*  243:     */       default: 
/*  244: 277 */         throw new NoViableAltException(_t);
/*  245:     */       }
/*  246: 281 */       _t = __t3;
/*  247: 282 */       _t = _t.getNextSibling();
/*  248:     */     }
/*  249:     */     catch (RecognitionException ex)
/*  250:     */     {
/*  251: 285 */       if (this.inputState.guessing == 0)
/*  252:     */       {
/*  253: 286 */         reportError(ex);
/*  254: 287 */         if (_t != null) {
/*  255: 287 */           _t = _t.getNextSibling();
/*  256:     */         }
/*  257:     */       }
/*  258:     */       else
/*  259:     */       {
/*  260: 289 */         throw ex;
/*  261:     */       }
/*  262:     */     }
/*  263: 292 */     this._retTree = _t;
/*  264:     */   }
/*  265:     */   
/*  266:     */   public final void updateStatement(AST _t)
/*  267:     */     throws RecognitionException
/*  268:     */   {
/*  269: 297 */     AST updateStatement_AST_in = _t == ASTNULL ? null : _t;
/*  270:     */     try
/*  271:     */     {
/*  272: 300 */       AST __t13 = _t;
/*  273: 301 */       AST tmp6_AST_in = _t;
/*  274: 302 */       match(_t, 51);
/*  275: 303 */       _t = _t.getFirstChild();
/*  276: 304 */       if (this.inputState.guessing == 0) {
/*  277: 305 */         out("update ");
/*  278:     */       }
/*  279: 307 */       AST __t14 = _t;
/*  280: 308 */       AST tmp7_AST_in = _t;
/*  281: 309 */       match(_t, 22);
/*  282: 310 */       _t = _t.getFirstChild();
/*  283: 311 */       fromTable(_t);
/*  284: 312 */       _t = this._retTree;
/*  285: 313 */       _t = __t14;
/*  286: 314 */       _t = _t.getNextSibling();
/*  287: 315 */       setClause(_t);
/*  288: 316 */       _t = this._retTree;
/*  289: 318 */       if (_t == null) {
/*  290: 318 */         _t = ASTNULL;
/*  291:     */       }
/*  292: 319 */       switch (_t.getType())
/*  293:     */       {
/*  294:     */       case 53: 
/*  295: 322 */         whereClause(_t);
/*  296: 323 */         _t = this._retTree;
/*  297: 324 */         break;
/*  298:     */       case 3: 
/*  299:     */         break;
/*  300:     */       default: 
/*  301: 332 */         throw new NoViableAltException(_t);
/*  302:     */       }
/*  303: 336 */       _t = __t13;
/*  304: 337 */       _t = _t.getNextSibling();
/*  305:     */     }
/*  306:     */     catch (RecognitionException ex)
/*  307:     */     {
/*  308: 340 */       if (this.inputState.guessing == 0)
/*  309:     */       {
/*  310: 341 */         reportError(ex);
/*  311: 342 */         if (_t != null) {
/*  312: 342 */           _t = _t.getNextSibling();
/*  313:     */         }
/*  314:     */       }
/*  315:     */       else
/*  316:     */       {
/*  317: 344 */         throw ex;
/*  318:     */       }
/*  319:     */     }
/*  320: 347 */     this._retTree = _t;
/*  321:     */   }
/*  322:     */   
/*  323:     */   public final void deleteStatement(AST _t)
/*  324:     */     throws RecognitionException
/*  325:     */   {
/*  326: 352 */     AST deleteStatement_AST_in = _t == ASTNULL ? null : _t;
/*  327:     */     try
/*  328:     */     {
/*  329: 355 */       AST __t17 = _t;
/*  330: 356 */       AST tmp8_AST_in = _t;
/*  331: 357 */       match(_t, 13);
/*  332: 358 */       _t = _t.getFirstChild();
/*  333: 359 */       if (this.inputState.guessing == 0) {
/*  334: 360 */         out("delete");
/*  335:     */       }
/*  336: 362 */       from(_t);
/*  337: 363 */       _t = this._retTree;
/*  338: 365 */       if (_t == null) {
/*  339: 365 */         _t = ASTNULL;
/*  340:     */       }
/*  341: 366 */       switch (_t.getType())
/*  342:     */       {
/*  343:     */       case 53: 
/*  344: 369 */         whereClause(_t);
/*  345: 370 */         _t = this._retTree;
/*  346: 371 */         break;
/*  347:     */       case 3: 
/*  348:     */         break;
/*  349:     */       default: 
/*  350: 379 */         throw new NoViableAltException(_t);
/*  351:     */       }
/*  352: 383 */       _t = __t17;
/*  353: 384 */       _t = _t.getNextSibling();
/*  354:     */     }
/*  355:     */     catch (RecognitionException ex)
/*  356:     */     {
/*  357: 387 */       if (this.inputState.guessing == 0)
/*  358:     */       {
/*  359: 388 */         reportError(ex);
/*  360: 389 */         if (_t != null) {
/*  361: 389 */           _t = _t.getNextSibling();
/*  362:     */         }
/*  363:     */       }
/*  364:     */       else
/*  365:     */       {
/*  366: 391 */         throw ex;
/*  367:     */       }
/*  368:     */     }
/*  369: 394 */     this._retTree = _t;
/*  370:     */   }
/*  371:     */   
/*  372:     */   public final void insertStatement(AST _t)
/*  373:     */     throws RecognitionException
/*  374:     */   {
/*  375: 399 */     AST insertStatement_AST_in = _t == ASTNULL ? null : _t;
/*  376: 400 */     AST i = null;
/*  377:     */     try
/*  378:     */     {
/*  379: 403 */       AST __t20 = _t;
/*  380: 404 */       AST tmp9_AST_in = _t;
/*  381: 405 */       match(_t, 29);
/*  382: 406 */       _t = _t.getFirstChild();
/*  383: 407 */       if (this.inputState.guessing == 0) {
/*  384: 408 */         out("insert ");
/*  385:     */       }
/*  386: 410 */       i = _t;
/*  387: 411 */       match(_t, 30);
/*  388: 412 */       _t = _t.getNextSibling();
/*  389: 413 */       if (this.inputState.guessing == 0)
/*  390:     */       {
/*  391: 414 */         out(i);out(" ");
/*  392:     */       }
/*  393: 416 */       selectStatement(_t);
/*  394: 417 */       _t = this._retTree;
/*  395: 418 */       _t = __t20;
/*  396: 419 */       _t = _t.getNextSibling();
/*  397:     */     }
/*  398:     */     catch (RecognitionException ex)
/*  399:     */     {
/*  400: 422 */       if (this.inputState.guessing == 0)
/*  401:     */       {
/*  402: 423 */         reportError(ex);
/*  403: 424 */         if (_t != null) {
/*  404: 424 */           _t = _t.getNextSibling();
/*  405:     */         }
/*  406:     */       }
/*  407:     */       else
/*  408:     */       {
/*  409: 426 */         throw ex;
/*  410:     */       }
/*  411:     */     }
/*  412: 429 */     this._retTree = _t;
/*  413:     */   }
/*  414:     */   
/*  415:     */   public final void selectClause(AST _t)
/*  416:     */     throws RecognitionException
/*  417:     */   {
/*  418: 434 */     AST selectClause_AST_in = _t == ASTNULL ? null : _t;
/*  419:     */     try
/*  420:     */     {
/*  421: 437 */       AST __t48 = _t;
/*  422: 438 */       AST tmp10_AST_in = _t;
/*  423: 439 */       match(_t, 137);
/*  424: 440 */       _t = _t.getFirstChild();
/*  425: 442 */       if (_t == null) {
/*  426: 442 */         _t = ASTNULL;
/*  427:     */       }
/*  428: 443 */       switch (_t.getType())
/*  429:     */       {
/*  430:     */       case 4: 
/*  431:     */       case 16: 
/*  432: 447 */         distinctOrAll(_t);
/*  433: 448 */         _t = this._retTree;
/*  434: 449 */         break;
/*  435:     */       case 12: 
/*  436:     */       case 15: 
/*  437:     */       case 20: 
/*  438:     */       case 45: 
/*  439:     */       case 49: 
/*  440:     */       case 54: 
/*  441:     */       case 68: 
/*  442:     */       case 69: 
/*  443:     */       case 70: 
/*  444:     */       case 71: 
/*  445:     */       case 73: 
/*  446:     */       case 74: 
/*  447:     */       case 81: 
/*  448:     */       case 90: 
/*  449:     */       case 94: 
/*  450:     */       case 95: 
/*  451:     */       case 96: 
/*  452:     */       case 97: 
/*  453:     */       case 98: 
/*  454:     */       case 99: 
/*  455:     */       case 100: 
/*  456:     */       case 115: 
/*  457:     */       case 116: 
/*  458:     */       case 117: 
/*  459:     */       case 118: 
/*  460:     */       case 119: 
/*  461:     */       case 123: 
/*  462:     */       case 124: 
/*  463:     */       case 125: 
/*  464:     */       case 126: 
/*  465:     */       case 140: 
/*  466:     */       case 142: 
/*  467:     */       case 144: 
/*  468:     */       case 151: 
/*  469:     */         break;
/*  470:     */       case 5: 
/*  471:     */       case 6: 
/*  472:     */       case 7: 
/*  473:     */       case 8: 
/*  474:     */       case 9: 
/*  475:     */       case 10: 
/*  476:     */       case 11: 
/*  477:     */       case 13: 
/*  478:     */       case 14: 
/*  479:     */       case 17: 
/*  480:     */       case 18: 
/*  481:     */       case 19: 
/*  482:     */       case 21: 
/*  483:     */       case 22: 
/*  484:     */       case 23: 
/*  485:     */       case 24: 
/*  486:     */       case 25: 
/*  487:     */       case 26: 
/*  488:     */       case 27: 
/*  489:     */       case 28: 
/*  490:     */       case 29: 
/*  491:     */       case 30: 
/*  492:     */       case 31: 
/*  493:     */       case 32: 
/*  494:     */       case 33: 
/*  495:     */       case 34: 
/*  496:     */       case 35: 
/*  497:     */       case 36: 
/*  498:     */       case 37: 
/*  499:     */       case 38: 
/*  500:     */       case 39: 
/*  501:     */       case 40: 
/*  502:     */       case 41: 
/*  503:     */       case 42: 
/*  504:     */       case 43: 
/*  505:     */       case 44: 
/*  506:     */       case 46: 
/*  507:     */       case 47: 
/*  508:     */       case 48: 
/*  509:     */       case 50: 
/*  510:     */       case 51: 
/*  511:     */       case 52: 
/*  512:     */       case 53: 
/*  513:     */       case 55: 
/*  514:     */       case 56: 
/*  515:     */       case 57: 
/*  516:     */       case 58: 
/*  517:     */       case 59: 
/*  518:     */       case 60: 
/*  519:     */       case 61: 
/*  520:     */       case 62: 
/*  521:     */       case 63: 
/*  522:     */       case 64: 
/*  523:     */       case 65: 
/*  524:     */       case 66: 
/*  525:     */       case 67: 
/*  526:     */       case 72: 
/*  527:     */       case 75: 
/*  528:     */       case 76: 
/*  529:     */       case 77: 
/*  530:     */       case 78: 
/*  531:     */       case 79: 
/*  532:     */       case 80: 
/*  533:     */       case 82: 
/*  534:     */       case 83: 
/*  535:     */       case 84: 
/*  536:     */       case 85: 
/*  537:     */       case 86: 
/*  538:     */       case 87: 
/*  539:     */       case 88: 
/*  540:     */       case 89: 
/*  541:     */       case 91: 
/*  542:     */       case 92: 
/*  543:     */       case 93: 
/*  544:     */       case 101: 
/*  545:     */       case 102: 
/*  546:     */       case 103: 
/*  547:     */       case 104: 
/*  548:     */       case 105: 
/*  549:     */       case 106: 
/*  550:     */       case 107: 
/*  551:     */       case 108: 
/*  552:     */       case 109: 
/*  553:     */       case 110: 
/*  554:     */       case 111: 
/*  555:     */       case 112: 
/*  556:     */       case 113: 
/*  557:     */       case 114: 
/*  558:     */       case 120: 
/*  559:     */       case 121: 
/*  560:     */       case 122: 
/*  561:     */       case 127: 
/*  562:     */       case 128: 
/*  563:     */       case 129: 
/*  564:     */       case 130: 
/*  565:     */       case 131: 
/*  566:     */       case 132: 
/*  567:     */       case 133: 
/*  568:     */       case 134: 
/*  569:     */       case 135: 
/*  570:     */       case 136: 
/*  571:     */       case 137: 
/*  572:     */       case 138: 
/*  573:     */       case 139: 
/*  574:     */       case 141: 
/*  575:     */       case 143: 
/*  576:     */       case 145: 
/*  577:     */       case 146: 
/*  578:     */       case 147: 
/*  579:     */       case 148: 
/*  580:     */       case 149: 
/*  581:     */       case 150: 
/*  582:     */       default: 
/*  583: 490 */         throw new NoViableAltException(_t);
/*  584:     */       }
/*  585: 495 */       int _cnt51 = 0;
/*  586:     */       for (;;)
/*  587:     */       {
/*  588: 498 */         if (_t == null) {
/*  589: 498 */           _t = ASTNULL;
/*  590:     */         }
/*  591: 499 */         if (_tokenSet_0.member(_t.getType()))
/*  592:     */         {
/*  593: 500 */           selectColumn(_t);
/*  594: 501 */           _t = this._retTree;
/*  595:     */         }
/*  596:     */         else
/*  597:     */         {
/*  598: 504 */           if (_cnt51 >= 1) {
/*  599:     */             break;
/*  600:     */           }
/*  601: 504 */           throw new NoViableAltException(_t);
/*  602:     */         }
/*  603: 507 */         _cnt51++;
/*  604:     */       }
/*  605: 510 */       _t = __t48;
/*  606: 511 */       _t = _t.getNextSibling();
/*  607:     */     }
/*  608:     */     catch (RecognitionException ex)
/*  609:     */     {
/*  610: 514 */       if (this.inputState.guessing == 0)
/*  611:     */       {
/*  612: 515 */         reportError(ex);
/*  613: 516 */         if (_t != null) {
/*  614: 516 */           _t = _t.getNextSibling();
/*  615:     */         }
/*  616:     */       }
/*  617:     */       else
/*  618:     */       {
/*  619: 518 */         throw ex;
/*  620:     */       }
/*  621:     */     }
/*  622: 521 */     this._retTree = _t;
/*  623:     */   }
/*  624:     */   
/*  625:     */   public final void from(AST _t)
/*  626:     */     throws RecognitionException
/*  627:     */   {
/*  628: 526 */     AST from_AST_in = _t == ASTNULL ? null : _t;
/*  629: 527 */     AST f = null;
/*  630:     */     try
/*  631:     */     {
/*  632: 530 */       AST __t67 = _t;
/*  633: 531 */       f = _t == ASTNULL ? null : _t;
/*  634: 532 */       match(_t, 22);
/*  635: 533 */       _t = _t.getFirstChild();
/*  636: 534 */       if (this.inputState.guessing == 0) {
/*  637: 535 */         out(" from ");
/*  638:     */       }
/*  639:     */       for (;;)
/*  640:     */       {
/*  641: 540 */         if (_t == null) {
/*  642: 540 */           _t = ASTNULL;
/*  643:     */         }
/*  644: 541 */         if ((_t.getType() != 134) && (_t.getType() != 136)) {
/*  645:     */           break;
/*  646:     */         }
/*  647: 542 */         fromTable(_t);
/*  648: 543 */         _t = this._retTree;
/*  649:     */       }
/*  650: 551 */       _t = __t67;
/*  651: 552 */       _t = _t.getNextSibling();
/*  652:     */     }
/*  653:     */     catch (RecognitionException ex)
/*  654:     */     {
/*  655: 555 */       if (this.inputState.guessing == 0)
/*  656:     */       {
/*  657: 556 */         reportError(ex);
/*  658: 557 */         if (_t != null) {
/*  659: 557 */           _t = _t.getNextSibling();
/*  660:     */         }
/*  661:     */       }
/*  662:     */       else
/*  663:     */       {
/*  664: 559 */         throw ex;
/*  665:     */       }
/*  666:     */     }
/*  667: 562 */     this._retTree = _t;
/*  668:     */   }
/*  669:     */   
/*  670:     */   public final void whereExpr(AST _t)
/*  671:     */     throws RecognitionException
/*  672:     */   {
/*  673: 567 */     AST whereExpr_AST_in = _t == ASTNULL ? null : _t;
/*  674:     */     try
/*  675:     */     {
/*  676: 570 */       if (_t == null) {
/*  677: 570 */         _t = ASTNULL;
/*  678:     */       }
/*  679: 571 */       switch (_t.getType())
/*  680:     */       {
/*  681:     */       case 146: 
/*  682: 574 */         filters(_t);
/*  683: 575 */         _t = this._retTree;
/*  684: 577 */         if (_t == null) {
/*  685: 577 */           _t = ASTNULL;
/*  686:     */         }
/*  687: 578 */         switch (_t.getType())
/*  688:     */         {
/*  689:     */         case 145: 
/*  690: 581 */           if (this.inputState.guessing == 0) {
/*  691: 582 */             out(" and ");
/*  692:     */           }
/*  693: 584 */           thetaJoins(_t);
/*  694: 585 */           _t = this._retTree;
/*  695: 586 */           break;
/*  696:     */         case 3: 
/*  697:     */         case 6: 
/*  698:     */         case 10: 
/*  699:     */         case 19: 
/*  700:     */         case 26: 
/*  701:     */         case 34: 
/*  702:     */         case 38: 
/*  703:     */         case 40: 
/*  704:     */         case 79: 
/*  705:     */         case 80: 
/*  706:     */         case 82: 
/*  707:     */         case 83: 
/*  708:     */         case 84: 
/*  709:     */         case 102: 
/*  710:     */         case 108: 
/*  711:     */         case 110: 
/*  712:     */         case 111: 
/*  713:     */         case 112: 
/*  714:     */         case 113: 
/*  715:     */         case 142: 
/*  716:     */           break;
/*  717:     */         default: 
/*  718: 613 */           throw new NoViableAltException(_t);
/*  719:     */         }
/*  720: 618 */         if (_t == null) {
/*  721: 618 */           _t = ASTNULL;
/*  722:     */         }
/*  723: 619 */         switch (_t.getType())
/*  724:     */         {
/*  725:     */         case 6: 
/*  726:     */         case 10: 
/*  727:     */         case 19: 
/*  728:     */         case 26: 
/*  729:     */         case 34: 
/*  730:     */         case 38: 
/*  731:     */         case 40: 
/*  732:     */         case 79: 
/*  733:     */         case 80: 
/*  734:     */         case 82: 
/*  735:     */         case 83: 
/*  736:     */         case 84: 
/*  737:     */         case 102: 
/*  738:     */         case 108: 
/*  739:     */         case 110: 
/*  740:     */         case 111: 
/*  741:     */         case 112: 
/*  742:     */         case 113: 
/*  743:     */         case 142: 
/*  744: 640 */           if (this.inputState.guessing == 0) {
/*  745: 641 */             out(" and ");
/*  746:     */           }
/*  747: 643 */           booleanExpr(_t, true);
/*  748: 644 */           _t = this._retTree;
/*  749: 645 */           break;
/*  750:     */         case 3: 
/*  751:     */           break;
/*  752:     */         default: 
/*  753: 653 */           throw new NoViableAltException(_t);
/*  754:     */         }
/*  755:     */         break;
/*  756:     */       case 145: 
/*  757: 661 */         thetaJoins(_t);
/*  758: 662 */         _t = this._retTree;
/*  759: 664 */         if (_t == null) {
/*  760: 664 */           _t = ASTNULL;
/*  761:     */         }
/*  762: 665 */         switch (_t.getType())
/*  763:     */         {
/*  764:     */         case 6: 
/*  765:     */         case 10: 
/*  766:     */         case 19: 
/*  767:     */         case 26: 
/*  768:     */         case 34: 
/*  769:     */         case 38: 
/*  770:     */         case 40: 
/*  771:     */         case 79: 
/*  772:     */         case 80: 
/*  773:     */         case 82: 
/*  774:     */         case 83: 
/*  775:     */         case 84: 
/*  776:     */         case 102: 
/*  777:     */         case 108: 
/*  778:     */         case 110: 
/*  779:     */         case 111: 
/*  780:     */         case 112: 
/*  781:     */         case 113: 
/*  782:     */         case 142: 
/*  783: 686 */           if (this.inputState.guessing == 0) {
/*  784: 687 */             out(" and ");
/*  785:     */           }
/*  786: 689 */           booleanExpr(_t, true);
/*  787: 690 */           _t = this._retTree;
/*  788: 691 */           break;
/*  789:     */         case 3: 
/*  790:     */           break;
/*  791:     */         default: 
/*  792: 699 */           throw new NoViableAltException(_t);
/*  793:     */         }
/*  794:     */         break;
/*  795:     */       case 6: 
/*  796:     */       case 10: 
/*  797:     */       case 19: 
/*  798:     */       case 26: 
/*  799:     */       case 34: 
/*  800:     */       case 38: 
/*  801:     */       case 40: 
/*  802:     */       case 79: 
/*  803:     */       case 80: 
/*  804:     */       case 82: 
/*  805:     */       case 83: 
/*  806:     */       case 84: 
/*  807:     */       case 102: 
/*  808:     */       case 108: 
/*  809:     */       case 110: 
/*  810:     */       case 111: 
/*  811:     */       case 112: 
/*  812:     */       case 113: 
/*  813:     */       case 142: 
/*  814: 725 */         booleanExpr(_t, false);
/*  815: 726 */         _t = this._retTree;
/*  816: 727 */         break;
/*  817:     */       default: 
/*  818: 731 */         throw new NoViableAltException(_t);
/*  819:     */       }
/*  820:     */     }
/*  821:     */     catch (RecognitionException ex)
/*  822:     */     {
/*  823: 736 */       if (this.inputState.guessing == 0)
/*  824:     */       {
/*  825: 737 */         reportError(ex);
/*  826: 738 */         if (_t != null) {
/*  827: 738 */           _t = _t.getNextSibling();
/*  828:     */         }
/*  829:     */       }
/*  830:     */       else
/*  831:     */       {
/*  832: 740 */         throw ex;
/*  833:     */       }
/*  834:     */     }
/*  835: 743 */     this._retTree = _t;
/*  836:     */   }
/*  837:     */   
/*  838:     */   public final void groupExprs(AST _t)
/*  839:     */     throws RecognitionException
/*  840:     */   {
/*  841: 748 */     AST groupExprs_AST_in = _t == ASTNULL ? null : _t;
/*  842:     */     try
/*  843:     */     {
/*  844: 751 */       expr(_t);
/*  845: 752 */       _t = this._retTree;
/*  846: 754 */       if (_t == null) {
/*  847: 754 */         _t = ASTNULL;
/*  848:     */       }
/*  849: 755 */       switch (_t.getType())
/*  850:     */       {
/*  851:     */       case 4: 
/*  852:     */       case 5: 
/*  853:     */       case 12: 
/*  854:     */       case 15: 
/*  855:     */       case 20: 
/*  856:     */       case 39: 
/*  857:     */       case 45: 
/*  858:     */       case 47: 
/*  859:     */       case 49: 
/*  860:     */       case 54: 
/*  861:     */       case 71: 
/*  862:     */       case 74: 
/*  863:     */       case 78: 
/*  864:     */       case 81: 
/*  865:     */       case 90: 
/*  866:     */       case 92: 
/*  867:     */       case 94: 
/*  868:     */       case 95: 
/*  869:     */       case 96: 
/*  870:     */       case 97: 
/*  871:     */       case 98: 
/*  872:     */       case 99: 
/*  873:     */       case 100: 
/*  874:     */       case 115: 
/*  875:     */       case 116: 
/*  876:     */       case 117: 
/*  877:     */       case 118: 
/*  878:     */       case 119: 
/*  879:     */       case 123: 
/*  880:     */       case 124: 
/*  881:     */       case 125: 
/*  882:     */       case 126: 
/*  883:     */       case 140: 
/*  884:     */       case 142: 
/*  885:     */       case 148: 
/*  886:     */       case 150: 
/*  887: 793 */         if (this.inputState.guessing == 0) {
/*  888: 794 */           out(" , ");
/*  889:     */         }
/*  890: 796 */         groupExprs(_t);
/*  891: 797 */         _t = this._retTree;
/*  892: 798 */         break;
/*  893:     */       case 3: 
/*  894:     */       case 25: 
/*  895:     */         break;
/*  896:     */       case 6: 
/*  897:     */       case 7: 
/*  898:     */       case 8: 
/*  899:     */       case 9: 
/*  900:     */       case 10: 
/*  901:     */       case 11: 
/*  902:     */       case 13: 
/*  903:     */       case 14: 
/*  904:     */       case 16: 
/*  905:     */       case 17: 
/*  906:     */       case 18: 
/*  907:     */       case 19: 
/*  908:     */       case 21: 
/*  909:     */       case 22: 
/*  910:     */       case 23: 
/*  911:     */       case 24: 
/*  912:     */       case 26: 
/*  913:     */       case 27: 
/*  914:     */       case 28: 
/*  915:     */       case 29: 
/*  916:     */       case 30: 
/*  917:     */       case 31: 
/*  918:     */       case 32: 
/*  919:     */       case 33: 
/*  920:     */       case 34: 
/*  921:     */       case 35: 
/*  922:     */       case 36: 
/*  923:     */       case 37: 
/*  924:     */       case 38: 
/*  925:     */       case 40: 
/*  926:     */       case 41: 
/*  927:     */       case 42: 
/*  928:     */       case 43: 
/*  929:     */       case 44: 
/*  930:     */       case 46: 
/*  931:     */       case 48: 
/*  932:     */       case 50: 
/*  933:     */       case 51: 
/*  934:     */       case 52: 
/*  935:     */       case 53: 
/*  936:     */       case 55: 
/*  937:     */       case 56: 
/*  938:     */       case 57: 
/*  939:     */       case 58: 
/*  940:     */       case 59: 
/*  941:     */       case 60: 
/*  942:     */       case 61: 
/*  943:     */       case 62: 
/*  944:     */       case 63: 
/*  945:     */       case 64: 
/*  946:     */       case 65: 
/*  947:     */       case 66: 
/*  948:     */       case 67: 
/*  949:     */       case 68: 
/*  950:     */       case 69: 
/*  951:     */       case 70: 
/*  952:     */       case 72: 
/*  953:     */       case 73: 
/*  954:     */       case 75: 
/*  955:     */       case 76: 
/*  956:     */       case 77: 
/*  957:     */       case 79: 
/*  958:     */       case 80: 
/*  959:     */       case 82: 
/*  960:     */       case 83: 
/*  961:     */       case 84: 
/*  962:     */       case 85: 
/*  963:     */       case 86: 
/*  964:     */       case 87: 
/*  965:     */       case 88: 
/*  966:     */       case 89: 
/*  967:     */       case 91: 
/*  968:     */       case 93: 
/*  969:     */       case 101: 
/*  970:     */       case 102: 
/*  971:     */       case 103: 
/*  972:     */       case 104: 
/*  973:     */       case 105: 
/*  974:     */       case 106: 
/*  975:     */       case 107: 
/*  976:     */       case 108: 
/*  977:     */       case 109: 
/*  978:     */       case 110: 
/*  979:     */       case 111: 
/*  980:     */       case 112: 
/*  981:     */       case 113: 
/*  982:     */       case 114: 
/*  983:     */       case 120: 
/*  984:     */       case 121: 
/*  985:     */       case 122: 
/*  986:     */       case 127: 
/*  987:     */       case 128: 
/*  988:     */       case 129: 
/*  989:     */       case 130: 
/*  990:     */       case 131: 
/*  991:     */       case 132: 
/*  992:     */       case 133: 
/*  993:     */       case 134: 
/*  994:     */       case 135: 
/*  995:     */       case 136: 
/*  996:     */       case 137: 
/*  997:     */       case 138: 
/*  998:     */       case 139: 
/*  999:     */       case 141: 
/* 1000:     */       case 143: 
/* 1001:     */       case 144: 
/* 1002:     */       case 145: 
/* 1003:     */       case 146: 
/* 1004:     */       case 147: 
/* 1005:     */       case 149: 
/* 1006:     */       default: 
/* 1007: 807 */         throw new NoViableAltException(_t);
/* 1008:     */       }
/* 1009:     */     }
/* 1010:     */     catch (RecognitionException ex)
/* 1011:     */     {
/* 1012: 813 */       if (this.inputState.guessing == 0)
/* 1013:     */       {
/* 1014: 814 */         reportError(ex);
/* 1015: 815 */         if (_t != null) {
/* 1016: 815 */           _t = _t.getNextSibling();
/* 1017:     */         }
/* 1018:     */       }
/* 1019:     */       else
/* 1020:     */       {
/* 1021: 817 */         throw ex;
/* 1022:     */       }
/* 1023:     */     }
/* 1024: 820 */     this._retTree = _t;
/* 1025:     */   }
/* 1026:     */   
/* 1027:     */   public final void booleanExpr(AST _t, boolean parens)
/* 1028:     */     throws RecognitionException
/* 1029:     */   {
/* 1030: 827 */     AST booleanExpr_AST_in = _t == ASTNULL ? null : _t;
/* 1031: 828 */     AST st = null;
/* 1032:     */     try
/* 1033:     */     {
/* 1034: 831 */       if (_t == null) {
/* 1035: 831 */         _t = ASTNULL;
/* 1036:     */       }
/* 1037: 832 */       switch (_t.getType())
/* 1038:     */       {
/* 1039:     */       case 6: 
/* 1040:     */       case 38: 
/* 1041:     */       case 40: 
/* 1042: 837 */         booleanOp(_t, parens);
/* 1043: 838 */         _t = this._retTree;
/* 1044: 839 */         break;
/* 1045:     */       case 10: 
/* 1046:     */       case 19: 
/* 1047:     */       case 26: 
/* 1048:     */       case 34: 
/* 1049:     */       case 79: 
/* 1050:     */       case 80: 
/* 1051:     */       case 82: 
/* 1052:     */       case 83: 
/* 1053:     */       case 84: 
/* 1054:     */       case 102: 
/* 1055:     */       case 108: 
/* 1056:     */       case 110: 
/* 1057:     */       case 111: 
/* 1058:     */       case 112: 
/* 1059:     */       case 113: 
/* 1060: 857 */         comparisonExpr(_t, parens);
/* 1061: 858 */         _t = this._retTree;
/* 1062: 859 */         break;
/* 1063:     */       case 142: 
/* 1064: 863 */         st = _t;
/* 1065: 864 */         match(_t, 142);
/* 1066: 865 */         _t = _t.getNextSibling();
/* 1067: 866 */         if (this.inputState.guessing == 0) {
/* 1068: 867 */           out(st);
/* 1069:     */         }
/* 1070:     */         break;
/* 1071:     */       default: 
/* 1072: 873 */         throw new NoViableAltException(_t);
/* 1073:     */       }
/* 1074:     */     }
/* 1075:     */     catch (RecognitionException ex)
/* 1076:     */     {
/* 1077: 878 */       if (this.inputState.guessing == 0)
/* 1078:     */       {
/* 1079: 879 */         reportError(ex);
/* 1080: 880 */         if (_t != null) {
/* 1081: 880 */           _t = _t.getNextSibling();
/* 1082:     */         }
/* 1083:     */       }
/* 1084:     */       else
/* 1085:     */       {
/* 1086: 882 */         throw ex;
/* 1087:     */       }
/* 1088:     */     }
/* 1089: 885 */     this._retTree = _t;
/* 1090:     */   }
/* 1091:     */   
/* 1092:     */   public final void orderExprs(AST _t)
/* 1093:     */     throws RecognitionException
/* 1094:     */   {
/* 1095: 890 */     AST orderExprs_AST_in = _t == ASTNULL ? null : _t;
/* 1096: 891 */     AST dir = null;
/* 1097:     */     try
/* 1098:     */     {
/* 1099: 895 */       expr(_t);
/* 1100: 896 */       _t = this._retTree;
/* 1101: 899 */       if (_t == null) {
/* 1102: 899 */         _t = ASTNULL;
/* 1103:     */       }
/* 1104: 900 */       switch (_t.getType())
/* 1105:     */       {
/* 1106:     */       case 8: 
/* 1107:     */       case 14: 
/* 1108: 904 */         dir = _t == ASTNULL ? null : _t;
/* 1109: 905 */         orderDirection(_t);
/* 1110: 906 */         _t = this._retTree;
/* 1111: 907 */         if (this.inputState.guessing == 0)
/* 1112:     */         {
/* 1113: 908 */           out(" ");out(dir);
/* 1114:     */         }
/* 1115:     */         break;
/* 1116:     */       case 3: 
/* 1117:     */       case 4: 
/* 1118:     */       case 5: 
/* 1119:     */       case 12: 
/* 1120:     */       case 15: 
/* 1121:     */       case 20: 
/* 1122:     */       case 39: 
/* 1123:     */       case 45: 
/* 1124:     */       case 47: 
/* 1125:     */       case 49: 
/* 1126:     */       case 54: 
/* 1127:     */       case 71: 
/* 1128:     */       case 74: 
/* 1129:     */       case 78: 
/* 1130:     */       case 81: 
/* 1131:     */       case 90: 
/* 1132:     */       case 92: 
/* 1133:     */       case 94: 
/* 1134:     */       case 95: 
/* 1135:     */       case 96: 
/* 1136:     */       case 97: 
/* 1137:     */       case 98: 
/* 1138:     */       case 99: 
/* 1139:     */       case 100: 
/* 1140:     */       case 115: 
/* 1141:     */       case 116: 
/* 1142:     */       case 117: 
/* 1143:     */       case 118: 
/* 1144:     */       case 119: 
/* 1145:     */       case 123: 
/* 1146:     */       case 124: 
/* 1147:     */       case 125: 
/* 1148:     */       case 126: 
/* 1149:     */       case 140: 
/* 1150:     */       case 142: 
/* 1151:     */       case 148: 
/* 1152:     */       case 150: 
/* 1153:     */         break;
/* 1154:     */       case 6: 
/* 1155:     */       case 7: 
/* 1156:     */       case 9: 
/* 1157:     */       case 10: 
/* 1158:     */       case 11: 
/* 1159:     */       case 13: 
/* 1160:     */       case 16: 
/* 1161:     */       case 17: 
/* 1162:     */       case 18: 
/* 1163:     */       case 19: 
/* 1164:     */       case 21: 
/* 1165:     */       case 22: 
/* 1166:     */       case 23: 
/* 1167:     */       case 24: 
/* 1168:     */       case 25: 
/* 1169:     */       case 26: 
/* 1170:     */       case 27: 
/* 1171:     */       case 28: 
/* 1172:     */       case 29: 
/* 1173:     */       case 30: 
/* 1174:     */       case 31: 
/* 1175:     */       case 32: 
/* 1176:     */       case 33: 
/* 1177:     */       case 34: 
/* 1178:     */       case 35: 
/* 1179:     */       case 36: 
/* 1180:     */       case 37: 
/* 1181:     */       case 38: 
/* 1182:     */       case 40: 
/* 1183:     */       case 41: 
/* 1184:     */       case 42: 
/* 1185:     */       case 43: 
/* 1186:     */       case 44: 
/* 1187:     */       case 46: 
/* 1188:     */       case 48: 
/* 1189:     */       case 50: 
/* 1190:     */       case 51: 
/* 1191:     */       case 52: 
/* 1192:     */       case 53: 
/* 1193:     */       case 55: 
/* 1194:     */       case 56: 
/* 1195:     */       case 57: 
/* 1196:     */       case 58: 
/* 1197:     */       case 59: 
/* 1198:     */       case 60: 
/* 1199:     */       case 61: 
/* 1200:     */       case 62: 
/* 1201:     */       case 63: 
/* 1202:     */       case 64: 
/* 1203:     */       case 65: 
/* 1204:     */       case 66: 
/* 1205:     */       case 67: 
/* 1206:     */       case 68: 
/* 1207:     */       case 69: 
/* 1208:     */       case 70: 
/* 1209:     */       case 72: 
/* 1210:     */       case 73: 
/* 1211:     */       case 75: 
/* 1212:     */       case 76: 
/* 1213:     */       case 77: 
/* 1214:     */       case 79: 
/* 1215:     */       case 80: 
/* 1216:     */       case 82: 
/* 1217:     */       case 83: 
/* 1218:     */       case 84: 
/* 1219:     */       case 85: 
/* 1220:     */       case 86: 
/* 1221:     */       case 87: 
/* 1222:     */       case 88: 
/* 1223:     */       case 89: 
/* 1224:     */       case 91: 
/* 1225:     */       case 93: 
/* 1226:     */       case 101: 
/* 1227:     */       case 102: 
/* 1228:     */       case 103: 
/* 1229:     */       case 104: 
/* 1230:     */       case 105: 
/* 1231:     */       case 106: 
/* 1232:     */       case 107: 
/* 1233:     */       case 108: 
/* 1234:     */       case 109: 
/* 1235:     */       case 110: 
/* 1236:     */       case 111: 
/* 1237:     */       case 112: 
/* 1238:     */       case 113: 
/* 1239:     */       case 114: 
/* 1240:     */       case 120: 
/* 1241:     */       case 121: 
/* 1242:     */       case 122: 
/* 1243:     */       case 127: 
/* 1244:     */       case 128: 
/* 1245:     */       case 129: 
/* 1246:     */       case 130: 
/* 1247:     */       case 131: 
/* 1248:     */       case 132: 
/* 1249:     */       case 133: 
/* 1250:     */       case 134: 
/* 1251:     */       case 135: 
/* 1252:     */       case 136: 
/* 1253:     */       case 137: 
/* 1254:     */       case 138: 
/* 1255:     */       case 139: 
/* 1256:     */       case 141: 
/* 1257:     */       case 143: 
/* 1258:     */       case 144: 
/* 1259:     */       case 145: 
/* 1260:     */       case 146: 
/* 1261:     */       case 147: 
/* 1262:     */       case 149: 
/* 1263:     */       default: 
/* 1264: 954 */         throw new NoViableAltException(_t);
/* 1265:     */       }
/* 1266: 959 */       if (_t == null) {
/* 1267: 959 */         _t = ASTNULL;
/* 1268:     */       }
/* 1269: 960 */       switch (_t.getType())
/* 1270:     */       {
/* 1271:     */       case 4: 
/* 1272:     */       case 5: 
/* 1273:     */       case 12: 
/* 1274:     */       case 15: 
/* 1275:     */       case 20: 
/* 1276:     */       case 39: 
/* 1277:     */       case 45: 
/* 1278:     */       case 47: 
/* 1279:     */       case 49: 
/* 1280:     */       case 54: 
/* 1281:     */       case 71: 
/* 1282:     */       case 74: 
/* 1283:     */       case 78: 
/* 1284:     */       case 81: 
/* 1285:     */       case 90: 
/* 1286:     */       case 92: 
/* 1287:     */       case 94: 
/* 1288:     */       case 95: 
/* 1289:     */       case 96: 
/* 1290:     */       case 97: 
/* 1291:     */       case 98: 
/* 1292:     */       case 99: 
/* 1293:     */       case 100: 
/* 1294:     */       case 115: 
/* 1295:     */       case 116: 
/* 1296:     */       case 117: 
/* 1297:     */       case 118: 
/* 1298:     */       case 119: 
/* 1299:     */       case 123: 
/* 1300:     */       case 124: 
/* 1301:     */       case 125: 
/* 1302:     */       case 126: 
/* 1303:     */       case 140: 
/* 1304:     */       case 142: 
/* 1305:     */       case 148: 
/* 1306:     */       case 150: 
/* 1307: 998 */         if (this.inputState.guessing == 0) {
/* 1308: 999 */           out(", ");
/* 1309:     */         }
/* 1310:1001 */         orderExprs(_t);
/* 1311:1002 */         _t = this._retTree;
/* 1312:1003 */         break;
/* 1313:     */       case 3: 
/* 1314:     */         break;
/* 1315:     */       case 6: 
/* 1316:     */       case 7: 
/* 1317:     */       case 8: 
/* 1318:     */       case 9: 
/* 1319:     */       case 10: 
/* 1320:     */       case 11: 
/* 1321:     */       case 13: 
/* 1322:     */       case 14: 
/* 1323:     */       case 16: 
/* 1324:     */       case 17: 
/* 1325:     */       case 18: 
/* 1326:     */       case 19: 
/* 1327:     */       case 21: 
/* 1328:     */       case 22: 
/* 1329:     */       case 23: 
/* 1330:     */       case 24: 
/* 1331:     */       case 25: 
/* 1332:     */       case 26: 
/* 1333:     */       case 27: 
/* 1334:     */       case 28: 
/* 1335:     */       case 29: 
/* 1336:     */       case 30: 
/* 1337:     */       case 31: 
/* 1338:     */       case 32: 
/* 1339:     */       case 33: 
/* 1340:     */       case 34: 
/* 1341:     */       case 35: 
/* 1342:     */       case 36: 
/* 1343:     */       case 37: 
/* 1344:     */       case 38: 
/* 1345:     */       case 40: 
/* 1346:     */       case 41: 
/* 1347:     */       case 42: 
/* 1348:     */       case 43: 
/* 1349:     */       case 44: 
/* 1350:     */       case 46: 
/* 1351:     */       case 48: 
/* 1352:     */       case 50: 
/* 1353:     */       case 51: 
/* 1354:     */       case 52: 
/* 1355:     */       case 53: 
/* 1356:     */       case 55: 
/* 1357:     */       case 56: 
/* 1358:     */       case 57: 
/* 1359:     */       case 58: 
/* 1360:     */       case 59: 
/* 1361:     */       case 60: 
/* 1362:     */       case 61: 
/* 1363:     */       case 62: 
/* 1364:     */       case 63: 
/* 1365:     */       case 64: 
/* 1366:     */       case 65: 
/* 1367:     */       case 66: 
/* 1368:     */       case 67: 
/* 1369:     */       case 68: 
/* 1370:     */       case 69: 
/* 1371:     */       case 70: 
/* 1372:     */       case 72: 
/* 1373:     */       case 73: 
/* 1374:     */       case 75: 
/* 1375:     */       case 76: 
/* 1376:     */       case 77: 
/* 1377:     */       case 79: 
/* 1378:     */       case 80: 
/* 1379:     */       case 82: 
/* 1380:     */       case 83: 
/* 1381:     */       case 84: 
/* 1382:     */       case 85: 
/* 1383:     */       case 86: 
/* 1384:     */       case 87: 
/* 1385:     */       case 88: 
/* 1386:     */       case 89: 
/* 1387:     */       case 91: 
/* 1388:     */       case 93: 
/* 1389:     */       case 101: 
/* 1390:     */       case 102: 
/* 1391:     */       case 103: 
/* 1392:     */       case 104: 
/* 1393:     */       case 105: 
/* 1394:     */       case 106: 
/* 1395:     */       case 107: 
/* 1396:     */       case 108: 
/* 1397:     */       case 109: 
/* 1398:     */       case 110: 
/* 1399:     */       case 111: 
/* 1400:     */       case 112: 
/* 1401:     */       case 113: 
/* 1402:     */       case 114: 
/* 1403:     */       case 120: 
/* 1404:     */       case 121: 
/* 1405:     */       case 122: 
/* 1406:     */       case 127: 
/* 1407:     */       case 128: 
/* 1408:     */       case 129: 
/* 1409:     */       case 130: 
/* 1410:     */       case 131: 
/* 1411:     */       case 132: 
/* 1412:     */       case 133: 
/* 1413:     */       case 134: 
/* 1414:     */       case 135: 
/* 1415:     */       case 136: 
/* 1416:     */       case 137: 
/* 1417:     */       case 138: 
/* 1418:     */       case 139: 
/* 1419:     */       case 141: 
/* 1420:     */       case 143: 
/* 1421:     */       case 144: 
/* 1422:     */       case 145: 
/* 1423:     */       case 146: 
/* 1424:     */       case 147: 
/* 1425:     */       case 149: 
/* 1426:     */       default: 
/* 1427:1011 */         throw new NoViableAltException(_t);
/* 1428:     */       }
/* 1429:     */     }
/* 1430:     */     catch (RecognitionException ex)
/* 1431:     */     {
/* 1432:1017 */       if (this.inputState.guessing == 0)
/* 1433:     */       {
/* 1434:1018 */         reportError(ex);
/* 1435:1019 */         if (_t != null) {
/* 1436:1019 */           _t = _t.getNextSibling();
/* 1437:     */         }
/* 1438:     */       }
/* 1439:     */       else
/* 1440:     */       {
/* 1441:1021 */         throw ex;
/* 1442:     */       }
/* 1443:     */     }
/* 1444:1024 */     this._retTree = _t;
/* 1445:     */   }
/* 1446:     */   
/* 1447:     */   public final void fromTable(AST _t)
/* 1448:     */     throws RecognitionException
/* 1449:     */   {
/* 1450:1029 */     AST fromTable_AST_in = _t == ASTNULL ? null : _t;
/* 1451:1030 */     AST a = null;
/* 1452:1031 */     AST b = null;
/* 1453:     */     try
/* 1454:     */     {
/* 1455:1034 */       if (_t == null) {
/* 1456:1034 */         _t = ASTNULL;
/* 1457:     */       }
/* 1458:1035 */       switch (_t.getType())
/* 1459:     */       {
/* 1460:     */       case 134: 
/* 1461:1038 */         AST __t71 = _t;
/* 1462:1039 */         a = _t == ASTNULL ? null : _t;
/* 1463:1040 */         match(_t, 134);
/* 1464:1041 */         _t = _t.getFirstChild();
/* 1465:1042 */         if (this.inputState.guessing == 0) {
/* 1466:1043 */           out(a);
/* 1467:     */         }
/* 1468:     */         for (;;)
/* 1469:     */         {
/* 1470:1048 */           if (_t == null) {
/* 1471:1048 */             _t = ASTNULL;
/* 1472:     */           }
/* 1473:1049 */           if ((_t.getType() != 134) && (_t.getType() != 136)) {
/* 1474:     */             break;
/* 1475:     */           }
/* 1476:1050 */           tableJoin(_t, a);
/* 1477:1051 */           _t = this._retTree;
/* 1478:     */         }
/* 1479:1059 */         if (this.inputState.guessing == 0) {
/* 1480:1060 */           fromFragmentSeparator(a);
/* 1481:     */         }
/* 1482:1062 */         _t = __t71;
/* 1483:1063 */         _t = _t.getNextSibling();
/* 1484:1064 */         break;
/* 1485:     */       case 136: 
/* 1486:1068 */         AST __t74 = _t;
/* 1487:1069 */         b = _t == ASTNULL ? null : _t;
/* 1488:1070 */         match(_t, 136);
/* 1489:1071 */         _t = _t.getFirstChild();
/* 1490:1072 */         if (this.inputState.guessing == 0) {
/* 1491:1073 */           out(b);
/* 1492:     */         }
/* 1493:     */         for (;;)
/* 1494:     */         {
/* 1495:1078 */           if (_t == null) {
/* 1496:1078 */             _t = ASTNULL;
/* 1497:     */           }
/* 1498:1079 */           if ((_t.getType() != 134) && (_t.getType() != 136)) {
/* 1499:     */             break;
/* 1500:     */           }
/* 1501:1080 */           tableJoin(_t, b);
/* 1502:1081 */           _t = this._retTree;
/* 1503:     */         }
/* 1504:1089 */         if (this.inputState.guessing == 0) {
/* 1505:1090 */           fromFragmentSeparator(b);
/* 1506:     */         }
/* 1507:1092 */         _t = __t74;
/* 1508:1093 */         _t = _t.getNextSibling();
/* 1509:1094 */         break;
/* 1510:     */       default: 
/* 1511:1098 */         throw new NoViableAltException(_t);
/* 1512:     */       }
/* 1513:     */     }
/* 1514:     */     catch (RecognitionException ex)
/* 1515:     */     {
/* 1516:1103 */       if (this.inputState.guessing == 0)
/* 1517:     */       {
/* 1518:1104 */         reportError(ex);
/* 1519:1105 */         if (_t != null) {
/* 1520:1105 */           _t = _t.getNextSibling();
/* 1521:     */         }
/* 1522:     */       }
/* 1523:     */       else
/* 1524:     */       {
/* 1525:1107 */         throw ex;
/* 1526:     */       }
/* 1527:     */     }
/* 1528:1110 */     this._retTree = _t;
/* 1529:     */   }
/* 1530:     */   
/* 1531:     */   public final void setClause(AST _t)
/* 1532:     */     throws RecognitionException
/* 1533:     */   {
/* 1534:1115 */     AST setClause_AST_in = _t == ASTNULL ? null : _t;
/* 1535:     */     try
/* 1536:     */     {
/* 1537:1118 */       AST __t22 = _t;
/* 1538:1119 */       AST tmp11_AST_in = _t;
/* 1539:1120 */       match(_t, 46);
/* 1540:1121 */       _t = _t.getFirstChild();
/* 1541:1122 */       if (this.inputState.guessing == 0) {
/* 1542:1123 */         out(" set ");
/* 1543:     */       }
/* 1544:1125 */       comparisonExpr(_t, false);
/* 1545:1126 */       _t = this._retTree;
/* 1546:     */       for (;;)
/* 1547:     */       {
/* 1548:1130 */         if (_t == null) {
/* 1549:1130 */           _t = ASTNULL;
/* 1550:     */         }
/* 1551:1131 */         if (!_tokenSet_1.member(_t.getType())) {
/* 1552:     */           break;
/* 1553:     */         }
/* 1554:1132 */         if (this.inputState.guessing == 0) {
/* 1555:1133 */           out(", ");
/* 1556:     */         }
/* 1557:1135 */         comparisonExpr(_t, false);
/* 1558:1136 */         _t = this._retTree;
/* 1559:     */       }
/* 1560:1144 */       _t = __t22;
/* 1561:1145 */       _t = _t.getNextSibling();
/* 1562:     */     }
/* 1563:     */     catch (RecognitionException ex)
/* 1564:     */     {
/* 1565:1148 */       if (this.inputState.guessing == 0)
/* 1566:     */       {
/* 1567:1149 */         reportError(ex);
/* 1568:1150 */         if (_t != null) {
/* 1569:1150 */           _t = _t.getNextSibling();
/* 1570:     */         }
/* 1571:     */       }
/* 1572:     */       else
/* 1573:     */       {
/* 1574:1152 */         throw ex;
/* 1575:     */       }
/* 1576:     */     }
/* 1577:1155 */     this._retTree = _t;
/* 1578:     */   }
/* 1579:     */   
/* 1580:     */   public final void whereClause(AST _t)
/* 1581:     */     throws RecognitionException
/* 1582:     */   {
/* 1583:1160 */     AST whereClause_AST_in = _t == ASTNULL ? null : _t;
/* 1584:     */     try
/* 1585:     */     {
/* 1586:1163 */       AST __t26 = _t;
/* 1587:1164 */       AST tmp12_AST_in = _t;
/* 1588:1165 */       match(_t, 53);
/* 1589:1166 */       _t = _t.getFirstChild();
/* 1590:1167 */       if (this.inputState.guessing == 0) {
/* 1591:1168 */         out(" where ");
/* 1592:     */       }
/* 1593:1170 */       whereClauseExpr(_t);
/* 1594:1171 */       _t = this._retTree;
/* 1595:1172 */       _t = __t26;
/* 1596:1173 */       _t = _t.getNextSibling();
/* 1597:     */     }
/* 1598:     */     catch (RecognitionException ex)
/* 1599:     */     {
/* 1600:1176 */       if (this.inputState.guessing == 0)
/* 1601:     */       {
/* 1602:1177 */         reportError(ex);
/* 1603:1178 */         if (_t != null) {
/* 1604:1178 */           _t = _t.getNextSibling();
/* 1605:     */         }
/* 1606:     */       }
/* 1607:     */       else
/* 1608:     */       {
/* 1609:1180 */         throw ex;
/* 1610:     */       }
/* 1611:     */     }
/* 1612:1183 */     this._retTree = _t;
/* 1613:     */   }
/* 1614:     */   
/* 1615:     */   public final void comparisonExpr(AST _t, boolean parens)
/* 1616:     */     throws RecognitionException
/* 1617:     */   {
/* 1618:1190 */     AST comparisonExpr_AST_in = _t == ASTNULL ? null : _t;
/* 1619:     */     try
/* 1620:     */     {
/* 1621:1193 */       if (_t == null) {
/* 1622:1193 */         _t = ASTNULL;
/* 1623:     */       }
/* 1624:1194 */       switch (_t.getType())
/* 1625:     */       {
/* 1626:     */       case 102: 
/* 1627:     */       case 108: 
/* 1628:     */       case 110: 
/* 1629:     */       case 111: 
/* 1630:     */       case 112: 
/* 1631:     */       case 113: 
/* 1632:1202 */         binaryComparisonExpression(_t);
/* 1633:1203 */         _t = this._retTree;
/* 1634:1204 */         break;
/* 1635:     */       case 10: 
/* 1636:     */       case 19: 
/* 1637:     */       case 26: 
/* 1638:     */       case 34: 
/* 1639:     */       case 79: 
/* 1640:     */       case 80: 
/* 1641:     */       case 82: 
/* 1642:     */       case 83: 
/* 1643:     */       case 84: 
/* 1644:1216 */         if ((this.inputState.guessing == 0) && 
/* 1645:1217 */           (parens)) {
/* 1646:1217 */           out("(");
/* 1647:     */         }
/* 1648:1219 */         exoticComparisonExpression(_t);
/* 1649:1220 */         _t = this._retTree;
/* 1650:1221 */         if ((this.inputState.guessing == 0) && 
/* 1651:1222 */           (parens)) {
/* 1652:1222 */           out(")");
/* 1653:     */         }
/* 1654:     */         break;
/* 1655:     */       default: 
/* 1656:1228 */         throw new NoViableAltException(_t);
/* 1657:     */       }
/* 1658:     */     }
/* 1659:     */     catch (RecognitionException ex)
/* 1660:     */     {
/* 1661:1233 */       if (this.inputState.guessing == 0)
/* 1662:     */       {
/* 1663:1234 */         reportError(ex);
/* 1664:1235 */         if (_t != null) {
/* 1665:1235 */           _t = _t.getNextSibling();
/* 1666:     */         }
/* 1667:     */       }
/* 1668:     */       else
/* 1669:     */       {
/* 1670:1237 */         throw ex;
/* 1671:     */       }
/* 1672:     */     }
/* 1673:1240 */     this._retTree = _t;
/* 1674:     */   }
/* 1675:     */   
/* 1676:     */   public final void whereClauseExpr(AST _t)
/* 1677:     */     throws RecognitionException
/* 1678:     */   {
/* 1679:1245 */     AST whereClauseExpr_AST_in = _t == ASTNULL ? null : _t;
/* 1680:     */     try
/* 1681:     */     {
/* 1682:1248 */       boolean synPredMatched29 = false;
/* 1683:1249 */       if (_t == null) {
/* 1684:1249 */         _t = ASTNULL;
/* 1685:     */       }
/* 1686:1250 */       if (_t.getType() == 142)
/* 1687:     */       {
/* 1688:1251 */         AST __t29 = _t;
/* 1689:1252 */         synPredMatched29 = true;
/* 1690:1253 */         this.inputState.guessing += 1;
/* 1691:     */         try
/* 1692:     */         {
/* 1693:1256 */           AST tmp13_AST_in = _t;
/* 1694:1257 */           match(_t, 142);
/* 1695:1258 */           _t = _t.getNextSibling();
/* 1696:     */         }
/* 1697:     */         catch (RecognitionException pe)
/* 1698:     */         {
/* 1699:1262 */           synPredMatched29 = false;
/* 1700:     */         }
/* 1701:1264 */         _t = __t29;
/* 1702:1265 */         this.inputState.guessing -= 1;
/* 1703:     */       }
/* 1704:1267 */       if (synPredMatched29)
/* 1705:     */       {
/* 1706:1268 */         conditionList(_t);
/* 1707:1269 */         _t = this._retTree;
/* 1708:     */       }
/* 1709:1271 */       else if (_tokenSet_2.member(_t.getType()))
/* 1710:     */       {
/* 1711:1272 */         booleanExpr(_t, false);
/* 1712:1273 */         _t = this._retTree;
/* 1713:     */       }
/* 1714:     */       else
/* 1715:     */       {
/* 1716:1276 */         throw new NoViableAltException(_t);
/* 1717:     */       }
/* 1718:     */     }
/* 1719:     */     catch (RecognitionException ex)
/* 1720:     */     {
/* 1721:1281 */       if (this.inputState.guessing == 0)
/* 1722:     */       {
/* 1723:1282 */         reportError(ex);
/* 1724:1283 */         if (_t != null) {
/* 1725:1283 */           _t = _t.getNextSibling();
/* 1726:     */         }
/* 1727:     */       }
/* 1728:     */       else
/* 1729:     */       {
/* 1730:1285 */         throw ex;
/* 1731:     */       }
/* 1732:     */     }
/* 1733:1288 */     this._retTree = _t;
/* 1734:     */   }
/* 1735:     */   
/* 1736:     */   public final void conditionList(AST _t)
/* 1737:     */     throws RecognitionException
/* 1738:     */   {
/* 1739:1293 */     AST conditionList_AST_in = _t == ASTNULL ? null : _t;
/* 1740:     */     try
/* 1741:     */     {
/* 1742:1296 */       sqlToken(_t);
/* 1743:1297 */       _t = this._retTree;
/* 1744:1299 */       if (_t == null) {
/* 1745:1299 */         _t = ASTNULL;
/* 1746:     */       }
/* 1747:1300 */       switch (_t.getType())
/* 1748:     */       {
/* 1749:     */       case 142: 
/* 1750:1303 */         if (this.inputState.guessing == 0) {
/* 1751:1304 */           out(" and ");
/* 1752:     */         }
/* 1753:1306 */         conditionList(_t);
/* 1754:1307 */         _t = this._retTree;
/* 1755:1308 */         break;
/* 1756:     */       case 3: 
/* 1757:     */         break;
/* 1758:     */       default: 
/* 1759:1316 */         throw new NoViableAltException(_t);
/* 1760:     */       }
/* 1761:     */     }
/* 1762:     */     catch (RecognitionException ex)
/* 1763:     */     {
/* 1764:1322 */       if (this.inputState.guessing == 0)
/* 1765:     */       {
/* 1766:1323 */         reportError(ex);
/* 1767:1324 */         if (_t != null) {
/* 1768:1324 */           _t = _t.getNextSibling();
/* 1769:     */         }
/* 1770:     */       }
/* 1771:     */       else
/* 1772:     */       {
/* 1773:1326 */         throw ex;
/* 1774:     */       }
/* 1775:     */     }
/* 1776:1329 */     this._retTree = _t;
/* 1777:     */   }
/* 1778:     */   
/* 1779:     */   public final void expr(AST _t)
/* 1780:     */     throws RecognitionException
/* 1781:     */   {
/* 1782:1334 */     AST expr_AST_in = _t == ASTNULL ? null : _t;
/* 1783:     */     try
/* 1784:     */     {
/* 1785:1337 */       if (_t == null) {
/* 1786:1337 */         _t = ASTNULL;
/* 1787:     */       }
/* 1788:1338 */       switch (_t.getType())
/* 1789:     */       {
/* 1790:     */       case 12: 
/* 1791:     */       case 15: 
/* 1792:     */       case 20: 
/* 1793:     */       case 39: 
/* 1794:     */       case 49: 
/* 1795:     */       case 54: 
/* 1796:     */       case 71: 
/* 1797:     */       case 74: 
/* 1798:     */       case 78: 
/* 1799:     */       case 81: 
/* 1800:     */       case 90: 
/* 1801:     */       case 94: 
/* 1802:     */       case 95: 
/* 1803:     */       case 96: 
/* 1804:     */       case 97: 
/* 1805:     */       case 98: 
/* 1806:     */       case 99: 
/* 1807:     */       case 100: 
/* 1808:     */       case 115: 
/* 1809:     */       case 116: 
/* 1810:     */       case 117: 
/* 1811:     */       case 118: 
/* 1812:     */       case 119: 
/* 1813:     */       case 123: 
/* 1814:     */       case 124: 
/* 1815:     */       case 125: 
/* 1816:     */       case 126: 
/* 1817:     */       case 140: 
/* 1818:     */       case 142: 
/* 1819:     */       case 148: 
/* 1820:     */       case 150: 
/* 1821:1371 */         simpleExpr(_t);
/* 1822:1372 */         _t = this._retTree;
/* 1823:1373 */         break;
/* 1824:     */       case 92: 
/* 1825:1377 */         tupleExpr(_t);
/* 1826:1378 */         _t = this._retTree;
/* 1827:1379 */         break;
/* 1828:     */       case 45: 
/* 1829:1383 */         parenSelect(_t);
/* 1830:1384 */         _t = this._retTree;
/* 1831:1385 */         break;
/* 1832:     */       case 5: 
/* 1833:1389 */         AST __t118 = _t;
/* 1834:1390 */         AST tmp14_AST_in = _t;
/* 1835:1391 */         match(_t, 5);
/* 1836:1392 */         _t = _t.getFirstChild();
/* 1837:1393 */         if (this.inputState.guessing == 0) {
/* 1838:1394 */           out("any ");
/* 1839:     */         }
/* 1840:1396 */         quantified(_t);
/* 1841:1397 */         _t = this._retTree;
/* 1842:1398 */         _t = __t118;
/* 1843:1399 */         _t = _t.getNextSibling();
/* 1844:1400 */         break;
/* 1845:     */       case 4: 
/* 1846:1404 */         AST __t119 = _t;
/* 1847:1405 */         AST tmp15_AST_in = _t;
/* 1848:1406 */         match(_t, 4);
/* 1849:1407 */         _t = _t.getFirstChild();
/* 1850:1408 */         if (this.inputState.guessing == 0) {
/* 1851:1409 */           out("all ");
/* 1852:     */         }
/* 1853:1411 */         quantified(_t);
/* 1854:1412 */         _t = this._retTree;
/* 1855:1413 */         _t = __t119;
/* 1856:1414 */         _t = _t.getNextSibling();
/* 1857:1415 */         break;
/* 1858:     */       case 47: 
/* 1859:1419 */         AST __t120 = _t;
/* 1860:1420 */         AST tmp16_AST_in = _t;
/* 1861:1421 */         match(_t, 47);
/* 1862:1422 */         _t = _t.getFirstChild();
/* 1863:1423 */         if (this.inputState.guessing == 0) {
/* 1864:1424 */           out("some ");
/* 1865:     */         }
/* 1866:1426 */         quantified(_t);
/* 1867:1427 */         _t = this._retTree;
/* 1868:1428 */         _t = __t120;
/* 1869:1429 */         _t = _t.getNextSibling();
/* 1870:1430 */         break;
/* 1871:     */       case 6: 
/* 1872:     */       case 7: 
/* 1873:     */       case 8: 
/* 1874:     */       case 9: 
/* 1875:     */       case 10: 
/* 1876:     */       case 11: 
/* 1877:     */       case 13: 
/* 1878:     */       case 14: 
/* 1879:     */       case 16: 
/* 1880:     */       case 17: 
/* 1881:     */       case 18: 
/* 1882:     */       case 19: 
/* 1883:     */       case 21: 
/* 1884:     */       case 22: 
/* 1885:     */       case 23: 
/* 1886:     */       case 24: 
/* 1887:     */       case 25: 
/* 1888:     */       case 26: 
/* 1889:     */       case 27: 
/* 1890:     */       case 28: 
/* 1891:     */       case 29: 
/* 1892:     */       case 30: 
/* 1893:     */       case 31: 
/* 1894:     */       case 32: 
/* 1895:     */       case 33: 
/* 1896:     */       case 34: 
/* 1897:     */       case 35: 
/* 1898:     */       case 36: 
/* 1899:     */       case 37: 
/* 1900:     */       case 38: 
/* 1901:     */       case 40: 
/* 1902:     */       case 41: 
/* 1903:     */       case 42: 
/* 1904:     */       case 43: 
/* 1905:     */       case 44: 
/* 1906:     */       case 46: 
/* 1907:     */       case 48: 
/* 1908:     */       case 50: 
/* 1909:     */       case 51: 
/* 1910:     */       case 52: 
/* 1911:     */       case 53: 
/* 1912:     */       case 55: 
/* 1913:     */       case 56: 
/* 1914:     */       case 57: 
/* 1915:     */       case 58: 
/* 1916:     */       case 59: 
/* 1917:     */       case 60: 
/* 1918:     */       case 61: 
/* 1919:     */       case 62: 
/* 1920:     */       case 63: 
/* 1921:     */       case 64: 
/* 1922:     */       case 65: 
/* 1923:     */       case 66: 
/* 1924:     */       case 67: 
/* 1925:     */       case 68: 
/* 1926:     */       case 69: 
/* 1927:     */       case 70: 
/* 1928:     */       case 72: 
/* 1929:     */       case 73: 
/* 1930:     */       case 75: 
/* 1931:     */       case 76: 
/* 1932:     */       case 77: 
/* 1933:     */       case 79: 
/* 1934:     */       case 80: 
/* 1935:     */       case 82: 
/* 1936:     */       case 83: 
/* 1937:     */       case 84: 
/* 1938:     */       case 85: 
/* 1939:     */       case 86: 
/* 1940:     */       case 87: 
/* 1941:     */       case 88: 
/* 1942:     */       case 89: 
/* 1943:     */       case 91: 
/* 1944:     */       case 93: 
/* 1945:     */       case 101: 
/* 1946:     */       case 102: 
/* 1947:     */       case 103: 
/* 1948:     */       case 104: 
/* 1949:     */       case 105: 
/* 1950:     */       case 106: 
/* 1951:     */       case 107: 
/* 1952:     */       case 108: 
/* 1953:     */       case 109: 
/* 1954:     */       case 110: 
/* 1955:     */       case 111: 
/* 1956:     */       case 112: 
/* 1957:     */       case 113: 
/* 1958:     */       case 114: 
/* 1959:     */       case 120: 
/* 1960:     */       case 121: 
/* 1961:     */       case 122: 
/* 1962:     */       case 127: 
/* 1963:     */       case 128: 
/* 1964:     */       case 129: 
/* 1965:     */       case 130: 
/* 1966:     */       case 131: 
/* 1967:     */       case 132: 
/* 1968:     */       case 133: 
/* 1969:     */       case 134: 
/* 1970:     */       case 135: 
/* 1971:     */       case 136: 
/* 1972:     */       case 137: 
/* 1973:     */       case 138: 
/* 1974:     */       case 139: 
/* 1975:     */       case 141: 
/* 1976:     */       case 143: 
/* 1977:     */       case 144: 
/* 1978:     */       case 145: 
/* 1979:     */       case 146: 
/* 1980:     */       case 147: 
/* 1981:     */       case 149: 
/* 1982:     */       default: 
/* 1983:1434 */         throw new NoViableAltException(_t);
/* 1984:     */       }
/* 1985:     */     }
/* 1986:     */     catch (RecognitionException ex)
/* 1987:     */     {
/* 1988:1439 */       if (this.inputState.guessing == 0)
/* 1989:     */       {
/* 1990:1440 */         reportError(ex);
/* 1991:1441 */         if (_t != null) {
/* 1992:1441 */           _t = _t.getNextSibling();
/* 1993:     */         }
/* 1994:     */       }
/* 1995:     */       else
/* 1996:     */       {
/* 1997:1443 */         throw ex;
/* 1998:     */       }
/* 1999:     */     }
/* 2000:1446 */     this._retTree = _t;
/* 2001:     */   }
/* 2002:     */   
/* 2003:     */   public final void orderDirection(AST _t)
/* 2004:     */     throws RecognitionException
/* 2005:     */   {
/* 2006:1451 */     AST orderDirection_AST_in = _t == ASTNULL ? null : _t;
/* 2007:     */     try
/* 2008:     */     {
/* 2009:1454 */       if (_t == null) {
/* 2010:1454 */         _t = ASTNULL;
/* 2011:     */       }
/* 2012:1455 */       switch (_t.getType())
/* 2013:     */       {
/* 2014:     */       case 8: 
/* 2015:1458 */         AST tmp17_AST_in = _t;
/* 2016:1459 */         match(_t, 8);
/* 2017:1460 */         _t = _t.getNextSibling();
/* 2018:1461 */         break;
/* 2019:     */       case 14: 
/* 2020:1465 */         AST tmp18_AST_in = _t;
/* 2021:1466 */         match(_t, 14);
/* 2022:1467 */         _t = _t.getNextSibling();
/* 2023:1468 */         break;
/* 2024:     */       default: 
/* 2025:1472 */         throw new NoViableAltException(_t);
/* 2026:     */       }
/* 2027:     */     }
/* 2028:     */     catch (RecognitionException ex)
/* 2029:     */     {
/* 2030:1477 */       if (this.inputState.guessing == 0)
/* 2031:     */       {
/* 2032:1478 */         reportError(ex);
/* 2033:1479 */         if (_t != null) {
/* 2034:1479 */           _t = _t.getNextSibling();
/* 2035:     */         }
/* 2036:     */       }
/* 2037:     */       else
/* 2038:     */       {
/* 2039:1481 */         throw ex;
/* 2040:     */       }
/* 2041:     */     }
/* 2042:1484 */     this._retTree = _t;
/* 2043:     */   }
/* 2044:     */   
/* 2045:     */   public final void filters(AST _t)
/* 2046:     */     throws RecognitionException
/* 2047:     */   {
/* 2048:1489 */     AST filters_AST_in = _t == ASTNULL ? null : _t;
/* 2049:     */     try
/* 2050:     */     {
/* 2051:1492 */       AST __t42 = _t;
/* 2052:1493 */       AST tmp19_AST_in = _t;
/* 2053:1494 */       match(_t, 146);
/* 2054:1495 */       _t = _t.getFirstChild();
/* 2055:1496 */       conditionList(_t);
/* 2056:1497 */       _t = this._retTree;
/* 2057:1498 */       _t = __t42;
/* 2058:1499 */       _t = _t.getNextSibling();
/* 2059:     */     }
/* 2060:     */     catch (RecognitionException ex)
/* 2061:     */     {
/* 2062:1502 */       if (this.inputState.guessing == 0)
/* 2063:     */       {
/* 2064:1503 */         reportError(ex);
/* 2065:1504 */         if (_t != null) {
/* 2066:1504 */           _t = _t.getNextSibling();
/* 2067:     */         }
/* 2068:     */       }
/* 2069:     */       else
/* 2070:     */       {
/* 2071:1506 */         throw ex;
/* 2072:     */       }
/* 2073:     */     }
/* 2074:1509 */     this._retTree = _t;
/* 2075:     */   }
/* 2076:     */   
/* 2077:     */   public final void thetaJoins(AST _t)
/* 2078:     */     throws RecognitionException
/* 2079:     */   {
/* 2080:1514 */     AST thetaJoins_AST_in = _t == ASTNULL ? null : _t;
/* 2081:     */     try
/* 2082:     */     {
/* 2083:1517 */       AST __t44 = _t;
/* 2084:1518 */       AST tmp20_AST_in = _t;
/* 2085:1519 */       match(_t, 145);
/* 2086:1520 */       _t = _t.getFirstChild();
/* 2087:1521 */       conditionList(_t);
/* 2088:1522 */       _t = this._retTree;
/* 2089:1523 */       _t = __t44;
/* 2090:1524 */       _t = _t.getNextSibling();
/* 2091:     */     }
/* 2092:     */     catch (RecognitionException ex)
/* 2093:     */     {
/* 2094:1527 */       if (this.inputState.guessing == 0)
/* 2095:     */       {
/* 2096:1528 */         reportError(ex);
/* 2097:1529 */         if (_t != null) {
/* 2098:1529 */           _t = _t.getNextSibling();
/* 2099:     */         }
/* 2100:     */       }
/* 2101:     */       else
/* 2102:     */       {
/* 2103:1531 */         throw ex;
/* 2104:     */       }
/* 2105:     */     }
/* 2106:1534 */     this._retTree = _t;
/* 2107:     */   }
/* 2108:     */   
/* 2109:     */   public final void sqlToken(AST _t)
/* 2110:     */     throws RecognitionException
/* 2111:     */   {
/* 2112:1539 */     AST sqlToken_AST_in = _t == ASTNULL ? null : _t;
/* 2113:1540 */     AST t = null;
/* 2114:     */     try
/* 2115:     */     {
/* 2116:1543 */       t = _t;
/* 2117:1544 */       match(_t, 142);
/* 2118:1545 */       _t = _t.getNextSibling();
/* 2119:1546 */       if (this.inputState.guessing == 0) {
/* 2120:1547 */         out(t);
/* 2121:     */       }
/* 2122:     */     }
/* 2123:     */     catch (RecognitionException ex)
/* 2124:     */     {
/* 2125:1551 */       if (this.inputState.guessing == 0)
/* 2126:     */       {
/* 2127:1552 */         reportError(ex);
/* 2128:1553 */         if (_t != null) {
/* 2129:1553 */           _t = _t.getNextSibling();
/* 2130:     */         }
/* 2131:     */       }
/* 2132:     */       else
/* 2133:     */       {
/* 2134:1555 */         throw ex;
/* 2135:     */       }
/* 2136:     */     }
/* 2137:1558 */     this._retTree = _t;
/* 2138:     */   }
/* 2139:     */   
/* 2140:     */   public final void distinctOrAll(AST _t)
/* 2141:     */     throws RecognitionException
/* 2142:     */   {
/* 2143:1563 */     AST distinctOrAll_AST_in = _t == ASTNULL ? null : _t;
/* 2144:     */     try
/* 2145:     */     {
/* 2146:1566 */       if (_t == null) {
/* 2147:1566 */         _t = ASTNULL;
/* 2148:     */       }
/* 2149:1567 */       switch (_t.getType())
/* 2150:     */       {
/* 2151:     */       case 16: 
/* 2152:1570 */         AST tmp21_AST_in = _t;
/* 2153:1571 */         match(_t, 16);
/* 2154:1572 */         _t = _t.getNextSibling();
/* 2155:1573 */         if (this.inputState.guessing == 0) {
/* 2156:1574 */           out("distinct ");
/* 2157:     */         }
/* 2158:     */         break;
/* 2159:     */       case 4: 
/* 2160:1580 */         AST tmp22_AST_in = _t;
/* 2161:1581 */         match(_t, 4);
/* 2162:1582 */         _t = _t.getNextSibling();
/* 2163:1583 */         if (this.inputState.guessing == 0) {
/* 2164:1584 */           out("all ");
/* 2165:     */         }
/* 2166:     */         break;
/* 2167:     */       default: 
/* 2168:1590 */         throw new NoViableAltException(_t);
/* 2169:     */       }
/* 2170:     */     }
/* 2171:     */     catch (RecognitionException ex)
/* 2172:     */     {
/* 2173:1595 */       if (this.inputState.guessing == 0)
/* 2174:     */       {
/* 2175:1596 */         reportError(ex);
/* 2176:1597 */         if (_t != null) {
/* 2177:1597 */           _t = _t.getNextSibling();
/* 2178:     */         }
/* 2179:     */       }
/* 2180:     */       else
/* 2181:     */       {
/* 2182:1599 */         throw ex;
/* 2183:     */       }
/* 2184:     */     }
/* 2185:1602 */     this._retTree = _t;
/* 2186:     */   }
/* 2187:     */   
/* 2188:     */   public final void selectColumn(AST _t)
/* 2189:     */     throws RecognitionException
/* 2190:     */   {
/* 2191:1607 */     AST selectColumn_AST_in = _t == ASTNULL ? null : _t;
/* 2192:1608 */     AST p = null;
/* 2193:1609 */     AST sc = null;
/* 2194:     */     try
/* 2195:     */     {
/* 2196:1612 */       p = _t == ASTNULL ? null : _t;
/* 2197:1613 */       selectExpr(_t);
/* 2198:1614 */       _t = this._retTree;
/* 2199:1616 */       if (_t == null) {
/* 2200:1616 */         _t = ASTNULL;
/* 2201:     */       }
/* 2202:1617 */       switch (_t.getType())
/* 2203:     */       {
/* 2204:     */       case 143: 
/* 2205:1620 */         sc = _t;
/* 2206:1621 */         match(_t, 143);
/* 2207:1622 */         _t = _t.getNextSibling();
/* 2208:1623 */         if (this.inputState.guessing == 0) {
/* 2209:1624 */           out(sc);
/* 2210:     */         }
/* 2211:     */         break;
/* 2212:     */       case 3: 
/* 2213:     */       case 12: 
/* 2214:     */       case 15: 
/* 2215:     */       case 20: 
/* 2216:     */       case 45: 
/* 2217:     */       case 49: 
/* 2218:     */       case 54: 
/* 2219:     */       case 68: 
/* 2220:     */       case 69: 
/* 2221:     */       case 70: 
/* 2222:     */       case 71: 
/* 2223:     */       case 73: 
/* 2224:     */       case 74: 
/* 2225:     */       case 81: 
/* 2226:     */       case 90: 
/* 2227:     */       case 94: 
/* 2228:     */       case 95: 
/* 2229:     */       case 96: 
/* 2230:     */       case 97: 
/* 2231:     */       case 98: 
/* 2232:     */       case 99: 
/* 2233:     */       case 100: 
/* 2234:     */       case 115: 
/* 2235:     */       case 116: 
/* 2236:     */       case 117: 
/* 2237:     */       case 118: 
/* 2238:     */       case 119: 
/* 2239:     */       case 123: 
/* 2240:     */       case 124: 
/* 2241:     */       case 125: 
/* 2242:     */       case 126: 
/* 2243:     */       case 140: 
/* 2244:     */       case 142: 
/* 2245:     */       case 144: 
/* 2246:     */       case 151: 
/* 2247:     */         break;
/* 2248:     */       case 4: 
/* 2249:     */       case 5: 
/* 2250:     */       case 6: 
/* 2251:     */       case 7: 
/* 2252:     */       case 8: 
/* 2253:     */       case 9: 
/* 2254:     */       case 10: 
/* 2255:     */       case 11: 
/* 2256:     */       case 13: 
/* 2257:     */       case 14: 
/* 2258:     */       case 16: 
/* 2259:     */       case 17: 
/* 2260:     */       case 18: 
/* 2261:     */       case 19: 
/* 2262:     */       case 21: 
/* 2263:     */       case 22: 
/* 2264:     */       case 23: 
/* 2265:     */       case 24: 
/* 2266:     */       case 25: 
/* 2267:     */       case 26: 
/* 2268:     */       case 27: 
/* 2269:     */       case 28: 
/* 2270:     */       case 29: 
/* 2271:     */       case 30: 
/* 2272:     */       case 31: 
/* 2273:     */       case 32: 
/* 2274:     */       case 33: 
/* 2275:     */       case 34: 
/* 2276:     */       case 35: 
/* 2277:     */       case 36: 
/* 2278:     */       case 37: 
/* 2279:     */       case 38: 
/* 2280:     */       case 39: 
/* 2281:     */       case 40: 
/* 2282:     */       case 41: 
/* 2283:     */       case 42: 
/* 2284:     */       case 43: 
/* 2285:     */       case 44: 
/* 2286:     */       case 46: 
/* 2287:     */       case 47: 
/* 2288:     */       case 48: 
/* 2289:     */       case 50: 
/* 2290:     */       case 51: 
/* 2291:     */       case 52: 
/* 2292:     */       case 53: 
/* 2293:     */       case 55: 
/* 2294:     */       case 56: 
/* 2295:     */       case 57: 
/* 2296:     */       case 58: 
/* 2297:     */       case 59: 
/* 2298:     */       case 60: 
/* 2299:     */       case 61: 
/* 2300:     */       case 62: 
/* 2301:     */       case 63: 
/* 2302:     */       case 64: 
/* 2303:     */       case 65: 
/* 2304:     */       case 66: 
/* 2305:     */       case 67: 
/* 2306:     */       case 72: 
/* 2307:     */       case 75: 
/* 2308:     */       case 76: 
/* 2309:     */       case 77: 
/* 2310:     */       case 78: 
/* 2311:     */       case 79: 
/* 2312:     */       case 80: 
/* 2313:     */       case 82: 
/* 2314:     */       case 83: 
/* 2315:     */       case 84: 
/* 2316:     */       case 85: 
/* 2317:     */       case 86: 
/* 2318:     */       case 87: 
/* 2319:     */       case 88: 
/* 2320:     */       case 89: 
/* 2321:     */       case 91: 
/* 2322:     */       case 92: 
/* 2323:     */       case 93: 
/* 2324:     */       case 101: 
/* 2325:     */       case 102: 
/* 2326:     */       case 103: 
/* 2327:     */       case 104: 
/* 2328:     */       case 105: 
/* 2329:     */       case 106: 
/* 2330:     */       case 107: 
/* 2331:     */       case 108: 
/* 2332:     */       case 109: 
/* 2333:     */       case 110: 
/* 2334:     */       case 111: 
/* 2335:     */       case 112: 
/* 2336:     */       case 113: 
/* 2337:     */       case 114: 
/* 2338:     */       case 120: 
/* 2339:     */       case 121: 
/* 2340:     */       case 122: 
/* 2341:     */       case 127: 
/* 2342:     */       case 128: 
/* 2343:     */       case 129: 
/* 2344:     */       case 130: 
/* 2345:     */       case 131: 
/* 2346:     */       case 132: 
/* 2347:     */       case 133: 
/* 2348:     */       case 134: 
/* 2349:     */       case 135: 
/* 2350:     */       case 136: 
/* 2351:     */       case 137: 
/* 2352:     */       case 138: 
/* 2353:     */       case 139: 
/* 2354:     */       case 141: 
/* 2355:     */       case 145: 
/* 2356:     */       case 146: 
/* 2357:     */       case 147: 
/* 2358:     */       case 148: 
/* 2359:     */       case 149: 
/* 2360:     */       case 150: 
/* 2361:     */       default: 
/* 2362:1668 */         throw new NoViableAltException(_t);
/* 2363:     */       }
/* 2364:1672 */       if (this.inputState.guessing == 0) {
/* 2365:1673 */         separator(sc != null ? sc : p, ", ");
/* 2366:     */       }
/* 2367:     */     }
/* 2368:     */     catch (RecognitionException ex)
/* 2369:     */     {
/* 2370:1677 */       if (this.inputState.guessing == 0)
/* 2371:     */       {
/* 2372:1678 */         reportError(ex);
/* 2373:1679 */         if (_t != null) {
/* 2374:1679 */           _t = _t.getNextSibling();
/* 2375:     */         }
/* 2376:     */       }
/* 2377:     */       else
/* 2378:     */       {
/* 2379:1681 */         throw ex;
/* 2380:     */       }
/* 2381:     */     }
/* 2382:1684 */     this._retTree = _t;
/* 2383:     */   }
/* 2384:     */   
/* 2385:     */   public final void selectExpr(AST _t)
/* 2386:     */     throws RecognitionException
/* 2387:     */   {
/* 2388:1689 */     AST selectExpr_AST_in = _t == ASTNULL ? null : _t;
/* 2389:1690 */     AST e = null;
/* 2390:1691 */     AST mcr = null;
/* 2391:1692 */     AST c = null;
/* 2392:1693 */     AST param = null;
/* 2393:1694 */     AST sn = null;
/* 2394:     */     try
/* 2395:     */     {
/* 2396:1697 */       if (_t == null) {
/* 2397:1697 */         _t = ASTNULL;
/* 2398:     */       }
/* 2399:1698 */       switch (_t.getType())
/* 2400:     */       {
/* 2401:     */       case 15: 
/* 2402:     */       case 140: 
/* 2403:     */       case 142: 
/* 2404:     */       case 144: 
/* 2405:1704 */         e = _t == ASTNULL ? null : _t;
/* 2406:1705 */         selectAtom(_t);
/* 2407:1706 */         _t = this._retTree;
/* 2408:1707 */         if (this.inputState.guessing == 0) {
/* 2409:1708 */           out(e);
/* 2410:     */         }
/* 2411:     */         break;
/* 2412:     */       case 68: 
/* 2413:     */       case 69: 
/* 2414:     */       case 70: 
/* 2415:1716 */         mcr = _t == ASTNULL ? null : _t;
/* 2416:1717 */         mapComponentReference(_t);
/* 2417:1718 */         _t = this._retTree;
/* 2418:1719 */         if (this.inputState.guessing == 0) {
/* 2419:1720 */           out(mcr);
/* 2420:     */         }
/* 2421:     */         break;
/* 2422:     */       case 12: 
/* 2423:1726 */         count(_t);
/* 2424:1727 */         _t = this._retTree;
/* 2425:1728 */         break;
/* 2426:     */       case 73: 
/* 2427:1732 */         AST __t55 = _t;
/* 2428:1733 */         AST tmp23_AST_in = _t;
/* 2429:1734 */         match(_t, 73);
/* 2430:1735 */         _t = _t.getFirstChild();
/* 2431:1737 */         if (_t == null) {
/* 2432:1737 */           _t = ASTNULL;
/* 2433:     */         }
/* 2434:1738 */         switch (_t.getType())
/* 2435:     */         {
/* 2436:     */         case 15: 
/* 2437:1741 */           AST tmp24_AST_in = _t;
/* 2438:1742 */           match(_t, 15);
/* 2439:1743 */           _t = _t.getNextSibling();
/* 2440:1744 */           break;
/* 2441:     */         case 126: 
/* 2442:1748 */           AST tmp25_AST_in = _t;
/* 2443:1749 */           match(_t, 126);
/* 2444:1750 */           _t = _t.getNextSibling();
/* 2445:1751 */           break;
/* 2446:     */         default: 
/* 2447:1755 */           throw new NoViableAltException(_t);
/* 2448:     */         }
/* 2449:1760 */         int _cnt58 = 0;
/* 2450:     */         for (;;)
/* 2451:     */         {
/* 2452:1763 */           if (_t == null) {
/* 2453:1763 */             _t = ASTNULL;
/* 2454:     */           }
/* 2455:1764 */           if (_tokenSet_0.member(_t.getType()))
/* 2456:     */           {
/* 2457:1765 */             selectColumn(_t);
/* 2458:1766 */             _t = this._retTree;
/* 2459:     */           }
/* 2460:     */           else
/* 2461:     */           {
/* 2462:1769 */             if (_cnt58 >= 1) {
/* 2463:     */               break;
/* 2464:     */             }
/* 2465:1769 */             throw new NoViableAltException(_t);
/* 2466:     */           }
/* 2467:1772 */           _cnt58++;
/* 2468:     */         }
/* 2469:1775 */         _t = __t55;
/* 2470:1776 */         _t = _t.getNextSibling();
/* 2471:1777 */         break;
/* 2472:     */       case 81: 
/* 2473:1781 */         methodCall(_t);
/* 2474:1782 */         _t = this._retTree;
/* 2475:1783 */         break;
/* 2476:     */       case 71: 
/* 2477:1787 */         aggregate(_t);
/* 2478:1788 */         _t = this._retTree;
/* 2479:1789 */         break;
/* 2480:     */       case 20: 
/* 2481:     */       case 49: 
/* 2482:     */       case 94: 
/* 2483:     */       case 95: 
/* 2484:     */       case 96: 
/* 2485:     */       case 97: 
/* 2486:     */       case 98: 
/* 2487:     */       case 99: 
/* 2488:     */       case 100: 
/* 2489:     */       case 124: 
/* 2490:     */       case 125: 
/* 2491:     */       case 126: 
/* 2492:1804 */         c = _t == ASTNULL ? null : _t;
/* 2493:1805 */         constant(_t);
/* 2494:1806 */         _t = this._retTree;
/* 2495:1807 */         if (this.inputState.guessing == 0) {
/* 2496:1808 */           out(c);
/* 2497:     */         }
/* 2498:     */         break;
/* 2499:     */       case 54: 
/* 2500:     */       case 74: 
/* 2501:     */       case 90: 
/* 2502:     */       case 115: 
/* 2503:     */       case 116: 
/* 2504:     */       case 117: 
/* 2505:     */       case 118: 
/* 2506:     */       case 119: 
/* 2507:1821 */         arithmeticExpr(_t);
/* 2508:1822 */         _t = this._retTree;
/* 2509:1823 */         break;
/* 2510:     */       case 123: 
/* 2511:1827 */         param = _t;
/* 2512:1828 */         match(_t, 123);
/* 2513:1829 */         _t = _t.getNextSibling();
/* 2514:1830 */         if (this.inputState.guessing == 0) {
/* 2515:1831 */           out(param);
/* 2516:     */         }
/* 2517:     */         break;
/* 2518:     */       case 151: 
/* 2519:1837 */         sn = _t;
/* 2520:1838 */         match(_t, 151);
/* 2521:1839 */         _t = _t.getNextSibling();
/* 2522:1840 */         if (this.inputState.guessing == 0) {
/* 2523:1841 */           out(sn);
/* 2524:     */         }
/* 2525:     */         break;
/* 2526:     */       case 45: 
/* 2527:1847 */         if (this.inputState.guessing == 0) {
/* 2528:1848 */           out("(");
/* 2529:     */         }
/* 2530:1850 */         selectStatement(_t);
/* 2531:1851 */         _t = this._retTree;
/* 2532:1852 */         if (this.inputState.guessing == 0) {
/* 2533:1853 */           out(")");
/* 2534:     */         }
/* 2535:     */         break;
/* 2536:     */       case 13: 
/* 2537:     */       case 14: 
/* 2538:     */       case 16: 
/* 2539:     */       case 17: 
/* 2540:     */       case 18: 
/* 2541:     */       case 19: 
/* 2542:     */       case 21: 
/* 2543:     */       case 22: 
/* 2544:     */       case 23: 
/* 2545:     */       case 24: 
/* 2546:     */       case 25: 
/* 2547:     */       case 26: 
/* 2548:     */       case 27: 
/* 2549:     */       case 28: 
/* 2550:     */       case 29: 
/* 2551:     */       case 30: 
/* 2552:     */       case 31: 
/* 2553:     */       case 32: 
/* 2554:     */       case 33: 
/* 2555:     */       case 34: 
/* 2556:     */       case 35: 
/* 2557:     */       case 36: 
/* 2558:     */       case 37: 
/* 2559:     */       case 38: 
/* 2560:     */       case 39: 
/* 2561:     */       case 40: 
/* 2562:     */       case 41: 
/* 2563:     */       case 42: 
/* 2564:     */       case 43: 
/* 2565:     */       case 44: 
/* 2566:     */       case 46: 
/* 2567:     */       case 47: 
/* 2568:     */       case 48: 
/* 2569:     */       case 50: 
/* 2570:     */       case 51: 
/* 2571:     */       case 52: 
/* 2572:     */       case 53: 
/* 2573:     */       case 55: 
/* 2574:     */       case 56: 
/* 2575:     */       case 57: 
/* 2576:     */       case 58: 
/* 2577:     */       case 59: 
/* 2578:     */       case 60: 
/* 2579:     */       case 61: 
/* 2580:     */       case 62: 
/* 2581:     */       case 63: 
/* 2582:     */       case 64: 
/* 2583:     */       case 65: 
/* 2584:     */       case 66: 
/* 2585:     */       case 67: 
/* 2586:     */       case 72: 
/* 2587:     */       case 75: 
/* 2588:     */       case 76: 
/* 2589:     */       case 77: 
/* 2590:     */       case 78: 
/* 2591:     */       case 79: 
/* 2592:     */       case 80: 
/* 2593:     */       case 82: 
/* 2594:     */       case 83: 
/* 2595:     */       case 84: 
/* 2596:     */       case 85: 
/* 2597:     */       case 86: 
/* 2598:     */       case 87: 
/* 2599:     */       case 88: 
/* 2600:     */       case 89: 
/* 2601:     */       case 91: 
/* 2602:     */       case 92: 
/* 2603:     */       case 93: 
/* 2604:     */       case 101: 
/* 2605:     */       case 102: 
/* 2606:     */       case 103: 
/* 2607:     */       case 104: 
/* 2608:     */       case 105: 
/* 2609:     */       case 106: 
/* 2610:     */       case 107: 
/* 2611:     */       case 108: 
/* 2612:     */       case 109: 
/* 2613:     */       case 110: 
/* 2614:     */       case 111: 
/* 2615:     */       case 112: 
/* 2616:     */       case 113: 
/* 2617:     */       case 114: 
/* 2618:     */       case 120: 
/* 2619:     */       case 121: 
/* 2620:     */       case 122: 
/* 2621:     */       case 127: 
/* 2622:     */       case 128: 
/* 2623:     */       case 129: 
/* 2624:     */       case 130: 
/* 2625:     */       case 131: 
/* 2626:     */       case 132: 
/* 2627:     */       case 133: 
/* 2628:     */       case 134: 
/* 2629:     */       case 135: 
/* 2630:     */       case 136: 
/* 2631:     */       case 137: 
/* 2632:     */       case 138: 
/* 2633:     */       case 139: 
/* 2634:     */       case 141: 
/* 2635:     */       case 143: 
/* 2636:     */       case 145: 
/* 2637:     */       case 146: 
/* 2638:     */       case 147: 
/* 2639:     */       case 148: 
/* 2640:     */       case 149: 
/* 2641:     */       case 150: 
/* 2642:     */       default: 
/* 2643:1859 */         throw new NoViableAltException(_t);
/* 2644:     */       }
/* 2645:     */     }
/* 2646:     */     catch (RecognitionException ex)
/* 2647:     */     {
/* 2648:1864 */       if (this.inputState.guessing == 0)
/* 2649:     */       {
/* 2650:1865 */         reportError(ex);
/* 2651:1866 */         if (_t != null) {
/* 2652:1866 */           _t = _t.getNextSibling();
/* 2653:     */         }
/* 2654:     */       }
/* 2655:     */       else
/* 2656:     */       {
/* 2657:1868 */         throw ex;
/* 2658:     */       }
/* 2659:     */     }
/* 2660:1871 */     this._retTree = _t;
/* 2661:     */   }
/* 2662:     */   
/* 2663:     */   public final void selectAtom(AST _t)
/* 2664:     */     throws RecognitionException
/* 2665:     */   {
/* 2666:1876 */     AST selectAtom_AST_in = _t == ASTNULL ? null : _t;
/* 2667:     */     try
/* 2668:     */     {
/* 2669:1879 */       if (_t == null) {
/* 2670:1879 */         _t = ASTNULL;
/* 2671:     */       }
/* 2672:1880 */       switch (_t.getType())
/* 2673:     */       {
/* 2674:     */       case 15: 
/* 2675:1883 */         AST tmp26_AST_in = _t;
/* 2676:1884 */         match(_t, 15);
/* 2677:1885 */         _t = _t.getNextSibling();
/* 2678:1886 */         break;
/* 2679:     */       case 142: 
/* 2680:1890 */         AST tmp27_AST_in = _t;
/* 2681:1891 */         match(_t, 142);
/* 2682:1892 */         _t = _t.getNextSibling();
/* 2683:1893 */         break;
/* 2684:     */       case 140: 
/* 2685:1897 */         AST tmp28_AST_in = _t;
/* 2686:1898 */         match(_t, 140);
/* 2687:1899 */         _t = _t.getNextSibling();
/* 2688:1900 */         break;
/* 2689:     */       case 144: 
/* 2690:1904 */         AST tmp29_AST_in = _t;
/* 2691:1905 */         match(_t, 144);
/* 2692:1906 */         _t = _t.getNextSibling();
/* 2693:1907 */         break;
/* 2694:     */       default: 
/* 2695:1911 */         throw new NoViableAltException(_t);
/* 2696:     */       }
/* 2697:     */     }
/* 2698:     */     catch (RecognitionException ex)
/* 2699:     */     {
/* 2700:1916 */       if (this.inputState.guessing == 0)
/* 2701:     */       {
/* 2702:1917 */         reportError(ex);
/* 2703:1918 */         if (_t != null) {
/* 2704:1918 */           _t = _t.getNextSibling();
/* 2705:     */         }
/* 2706:     */       }
/* 2707:     */       else
/* 2708:     */       {
/* 2709:1920 */         throw ex;
/* 2710:     */       }
/* 2711:     */     }
/* 2712:1923 */     this._retTree = _t;
/* 2713:     */   }
/* 2714:     */   
/* 2715:     */   public final void mapComponentReference(AST _t)
/* 2716:     */     throws RecognitionException
/* 2717:     */   {
/* 2718:1928 */     AST mapComponentReference_AST_in = _t == ASTNULL ? null : _t;
/* 2719:     */     try
/* 2720:     */     {
/* 2721:1931 */       if (_t == null) {
/* 2722:1931 */         _t = ASTNULL;
/* 2723:     */       }
/* 2724:1932 */       switch (_t.getType())
/* 2725:     */       {
/* 2726:     */       case 68: 
/* 2727:1935 */         AST tmp30_AST_in = _t;
/* 2728:1936 */         match(_t, 68);
/* 2729:1937 */         _t = _t.getNextSibling();
/* 2730:1938 */         break;
/* 2731:     */       case 69: 
/* 2732:1942 */         AST tmp31_AST_in = _t;
/* 2733:1943 */         match(_t, 69);
/* 2734:1944 */         _t = _t.getNextSibling();
/* 2735:1945 */         break;
/* 2736:     */       case 70: 
/* 2737:1949 */         AST tmp32_AST_in = _t;
/* 2738:1950 */         match(_t, 70);
/* 2739:1951 */         _t = _t.getNextSibling();
/* 2740:1952 */         break;
/* 2741:     */       default: 
/* 2742:1956 */         throw new NoViableAltException(_t);
/* 2743:     */       }
/* 2744:     */     }
/* 2745:     */     catch (RecognitionException ex)
/* 2746:     */     {
/* 2747:1961 */       if (this.inputState.guessing == 0)
/* 2748:     */       {
/* 2749:1962 */         reportError(ex);
/* 2750:1963 */         if (_t != null) {
/* 2751:1963 */           _t = _t.getNextSibling();
/* 2752:     */         }
/* 2753:     */       }
/* 2754:     */       else
/* 2755:     */       {
/* 2756:1965 */         throw ex;
/* 2757:     */       }
/* 2758:     */     }
/* 2759:1968 */     this._retTree = _t;
/* 2760:     */   }
/* 2761:     */   
/* 2762:     */   public final void count(AST _t)
/* 2763:     */     throws RecognitionException
/* 2764:     */   {
/* 2765:1973 */     AST count_AST_in = _t == ASTNULL ? null : _t;
/* 2766:     */     try
/* 2767:     */     {
/* 2768:1976 */       AST __t60 = _t;
/* 2769:1977 */       AST tmp33_AST_in = _t;
/* 2770:1978 */       match(_t, 12);
/* 2771:1979 */       _t = _t.getFirstChild();
/* 2772:1980 */       if (this.inputState.guessing == 0) {
/* 2773:1981 */         out("count(");
/* 2774:     */       }
/* 2775:1984 */       if (_t == null) {
/* 2776:1984 */         _t = ASTNULL;
/* 2777:     */       }
/* 2778:1985 */       switch (_t.getType())
/* 2779:     */       {
/* 2780:     */       case 4: 
/* 2781:     */       case 16: 
/* 2782:1989 */         distinctOrAll(_t);
/* 2783:1990 */         _t = this._retTree;
/* 2784:1991 */         break;
/* 2785:     */       case 12: 
/* 2786:     */       case 15: 
/* 2787:     */       case 20: 
/* 2788:     */       case 39: 
/* 2789:     */       case 49: 
/* 2790:     */       case 54: 
/* 2791:     */       case 71: 
/* 2792:     */       case 74: 
/* 2793:     */       case 78: 
/* 2794:     */       case 81: 
/* 2795:     */       case 88: 
/* 2796:     */       case 90: 
/* 2797:     */       case 94: 
/* 2798:     */       case 95: 
/* 2799:     */       case 96: 
/* 2800:     */       case 97: 
/* 2801:     */       case 98: 
/* 2802:     */       case 99: 
/* 2803:     */       case 100: 
/* 2804:     */       case 115: 
/* 2805:     */       case 116: 
/* 2806:     */       case 117: 
/* 2807:     */       case 118: 
/* 2808:     */       case 119: 
/* 2809:     */       case 123: 
/* 2810:     */       case 124: 
/* 2811:     */       case 125: 
/* 2812:     */       case 126: 
/* 2813:     */       case 140: 
/* 2814:     */       case 142: 
/* 2815:     */       case 148: 
/* 2816:     */       case 150: 
/* 2817:     */         break;
/* 2818:     */       case 5: 
/* 2819:     */       case 6: 
/* 2820:     */       case 7: 
/* 2821:     */       case 8: 
/* 2822:     */       case 9: 
/* 2823:     */       case 10: 
/* 2824:     */       case 11: 
/* 2825:     */       case 13: 
/* 2826:     */       case 14: 
/* 2827:     */       case 17: 
/* 2828:     */       case 18: 
/* 2829:     */       case 19: 
/* 2830:     */       case 21: 
/* 2831:     */       case 22: 
/* 2832:     */       case 23: 
/* 2833:     */       case 24: 
/* 2834:     */       case 25: 
/* 2835:     */       case 26: 
/* 2836:     */       case 27: 
/* 2837:     */       case 28: 
/* 2838:     */       case 29: 
/* 2839:     */       case 30: 
/* 2840:     */       case 31: 
/* 2841:     */       case 32: 
/* 2842:     */       case 33: 
/* 2843:     */       case 34: 
/* 2844:     */       case 35: 
/* 2845:     */       case 36: 
/* 2846:     */       case 37: 
/* 2847:     */       case 38: 
/* 2848:     */       case 40: 
/* 2849:     */       case 41: 
/* 2850:     */       case 42: 
/* 2851:     */       case 43: 
/* 2852:     */       case 44: 
/* 2853:     */       case 45: 
/* 2854:     */       case 46: 
/* 2855:     */       case 47: 
/* 2856:     */       case 48: 
/* 2857:     */       case 50: 
/* 2858:     */       case 51: 
/* 2859:     */       case 52: 
/* 2860:     */       case 53: 
/* 2861:     */       case 55: 
/* 2862:     */       case 56: 
/* 2863:     */       case 57: 
/* 2864:     */       case 58: 
/* 2865:     */       case 59: 
/* 2866:     */       case 60: 
/* 2867:     */       case 61: 
/* 2868:     */       case 62: 
/* 2869:     */       case 63: 
/* 2870:     */       case 64: 
/* 2871:     */       case 65: 
/* 2872:     */       case 66: 
/* 2873:     */       case 67: 
/* 2874:     */       case 68: 
/* 2875:     */       case 69: 
/* 2876:     */       case 70: 
/* 2877:     */       case 72: 
/* 2878:     */       case 73: 
/* 2879:     */       case 75: 
/* 2880:     */       case 76: 
/* 2881:     */       case 77: 
/* 2882:     */       case 79: 
/* 2883:     */       case 80: 
/* 2884:     */       case 82: 
/* 2885:     */       case 83: 
/* 2886:     */       case 84: 
/* 2887:     */       case 85: 
/* 2888:     */       case 86: 
/* 2889:     */       case 87: 
/* 2890:     */       case 89: 
/* 2891:     */       case 91: 
/* 2892:     */       case 92: 
/* 2893:     */       case 93: 
/* 2894:     */       case 101: 
/* 2895:     */       case 102: 
/* 2896:     */       case 103: 
/* 2897:     */       case 104: 
/* 2898:     */       case 105: 
/* 2899:     */       case 106: 
/* 2900:     */       case 107: 
/* 2901:     */       case 108: 
/* 2902:     */       case 109: 
/* 2903:     */       case 110: 
/* 2904:     */       case 111: 
/* 2905:     */       case 112: 
/* 2906:     */       case 113: 
/* 2907:     */       case 114: 
/* 2908:     */       case 120: 
/* 2909:     */       case 121: 
/* 2910:     */       case 122: 
/* 2911:     */       case 127: 
/* 2912:     */       case 128: 
/* 2913:     */       case 129: 
/* 2914:     */       case 130: 
/* 2915:     */       case 131: 
/* 2916:     */       case 132: 
/* 2917:     */       case 133: 
/* 2918:     */       case 134: 
/* 2919:     */       case 135: 
/* 2920:     */       case 136: 
/* 2921:     */       case 137: 
/* 2922:     */       case 138: 
/* 2923:     */       case 139: 
/* 2924:     */       case 141: 
/* 2925:     */       case 143: 
/* 2926:     */       case 144: 
/* 2927:     */       case 145: 
/* 2928:     */       case 146: 
/* 2929:     */       case 147: 
/* 2930:     */       case 149: 
/* 2931:     */       default: 
/* 2932:2030 */         throw new NoViableAltException(_t);
/* 2933:     */       }
/* 2934:2034 */       countExpr(_t);
/* 2935:2035 */       _t = this._retTree;
/* 2936:2036 */       if (this.inputState.guessing == 0) {
/* 2937:2037 */         out(")");
/* 2938:     */       }
/* 2939:2039 */       _t = __t60;
/* 2940:2040 */       _t = _t.getNextSibling();
/* 2941:     */     }
/* 2942:     */     catch (RecognitionException ex)
/* 2943:     */     {
/* 2944:2043 */       if (this.inputState.guessing == 0)
/* 2945:     */       {
/* 2946:2044 */         reportError(ex);
/* 2947:2045 */         if (_t != null) {
/* 2948:2045 */           _t = _t.getNextSibling();
/* 2949:     */         }
/* 2950:     */       }
/* 2951:     */       else
/* 2952:     */       {
/* 2953:2047 */         throw ex;
/* 2954:     */       }
/* 2955:     */     }
/* 2956:2050 */     this._retTree = _t;
/* 2957:     */   }
/* 2958:     */   
/* 2959:     */   public final void methodCall(AST _t)
/* 2960:     */     throws RecognitionException
/* 2961:     */   {
/* 2962:2055 */     AST methodCall_AST_in = _t == ASTNULL ? null : _t;
/* 2963:2056 */     AST m = null;
/* 2964:2057 */     AST i = null;
/* 2965:     */     try
/* 2966:     */     {
/* 2967:2060 */       AST __t161 = _t;
/* 2968:2061 */       m = _t == ASTNULL ? null : _t;
/* 2969:2062 */       match(_t, 81);
/* 2970:2063 */       _t = _t.getFirstChild();
/* 2971:2064 */       i = _t;
/* 2972:2065 */       match(_t, 147);
/* 2973:2066 */       _t = _t.getNextSibling();
/* 2974:2067 */       if (this.inputState.guessing == 0) {
/* 2975:2068 */         beginFunctionTemplate(m, i);
/* 2976:     */       }
/* 2977:2071 */       if (_t == null) {
/* 2978:2071 */         _t = ASTNULL;
/* 2979:     */       }
/* 2980:2072 */       switch (_t.getType())
/* 2981:     */       {
/* 2982:     */       case 75: 
/* 2983:2075 */         AST __t163 = _t;
/* 2984:2076 */         AST tmp34_AST_in = _t;
/* 2985:2077 */         match(_t, 75);
/* 2986:2078 */         _t = _t.getFirstChild();
/* 2987:2080 */         if (_t == null) {
/* 2988:2080 */           _t = ASTNULL;
/* 2989:     */         }
/* 2990:2081 */         switch (_t.getType())
/* 2991:     */         {
/* 2992:     */         case 4: 
/* 2993:     */         case 5: 
/* 2994:     */         case 12: 
/* 2995:     */         case 15: 
/* 2996:     */         case 20: 
/* 2997:     */         case 39: 
/* 2998:     */         case 45: 
/* 2999:     */         case 47: 
/* 3000:     */         case 49: 
/* 3001:     */         case 54: 
/* 3002:     */         case 71: 
/* 3003:     */         case 74: 
/* 3004:     */         case 78: 
/* 3005:     */         case 81: 
/* 3006:     */         case 90: 
/* 3007:     */         case 92: 
/* 3008:     */         case 94: 
/* 3009:     */         case 95: 
/* 3010:     */         case 96: 
/* 3011:     */         case 97: 
/* 3012:     */         case 98: 
/* 3013:     */         case 99: 
/* 3014:     */         case 100: 
/* 3015:     */         case 115: 
/* 3016:     */         case 116: 
/* 3017:     */         case 117: 
/* 3018:     */         case 118: 
/* 3019:     */         case 119: 
/* 3020:     */         case 123: 
/* 3021:     */         case 124: 
/* 3022:     */         case 125: 
/* 3023:     */         case 126: 
/* 3024:     */         case 140: 
/* 3025:     */         case 142: 
/* 3026:     */         case 148: 
/* 3027:     */         case 150: 
/* 3028:2119 */           arguments(_t);
/* 3029:2120 */           _t = this._retTree;
/* 3030:2121 */           break;
/* 3031:     */         case 3: 
/* 3032:     */           break;
/* 3033:     */         case 6: 
/* 3034:     */         case 7: 
/* 3035:     */         case 8: 
/* 3036:     */         case 9: 
/* 3037:     */         case 10: 
/* 3038:     */         case 11: 
/* 3039:     */         case 13: 
/* 3040:     */         case 14: 
/* 3041:     */         case 16: 
/* 3042:     */         case 17: 
/* 3043:     */         case 18: 
/* 3044:     */         case 19: 
/* 3045:     */         case 21: 
/* 3046:     */         case 22: 
/* 3047:     */         case 23: 
/* 3048:     */         case 24: 
/* 3049:     */         case 25: 
/* 3050:     */         case 26: 
/* 3051:     */         case 27: 
/* 3052:     */         case 28: 
/* 3053:     */         case 29: 
/* 3054:     */         case 30: 
/* 3055:     */         case 31: 
/* 3056:     */         case 32: 
/* 3057:     */         case 33: 
/* 3058:     */         case 34: 
/* 3059:     */         case 35: 
/* 3060:     */         case 36: 
/* 3061:     */         case 37: 
/* 3062:     */         case 38: 
/* 3063:     */         case 40: 
/* 3064:     */         case 41: 
/* 3065:     */         case 42: 
/* 3066:     */         case 43: 
/* 3067:     */         case 44: 
/* 3068:     */         case 46: 
/* 3069:     */         case 48: 
/* 3070:     */         case 50: 
/* 3071:     */         case 51: 
/* 3072:     */         case 52: 
/* 3073:     */         case 53: 
/* 3074:     */         case 55: 
/* 3075:     */         case 56: 
/* 3076:     */         case 57: 
/* 3077:     */         case 58: 
/* 3078:     */         case 59: 
/* 3079:     */         case 60: 
/* 3080:     */         case 61: 
/* 3081:     */         case 62: 
/* 3082:     */         case 63: 
/* 3083:     */         case 64: 
/* 3084:     */         case 65: 
/* 3085:     */         case 66: 
/* 3086:     */         case 67: 
/* 3087:     */         case 68: 
/* 3088:     */         case 69: 
/* 3089:     */         case 70: 
/* 3090:     */         case 72: 
/* 3091:     */         case 73: 
/* 3092:     */         case 75: 
/* 3093:     */         case 76: 
/* 3094:     */         case 77: 
/* 3095:     */         case 79: 
/* 3096:     */         case 80: 
/* 3097:     */         case 82: 
/* 3098:     */         case 83: 
/* 3099:     */         case 84: 
/* 3100:     */         case 85: 
/* 3101:     */         case 86: 
/* 3102:     */         case 87: 
/* 3103:     */         case 88: 
/* 3104:     */         case 89: 
/* 3105:     */         case 91: 
/* 3106:     */         case 93: 
/* 3107:     */         case 101: 
/* 3108:     */         case 102: 
/* 3109:     */         case 103: 
/* 3110:     */         case 104: 
/* 3111:     */         case 105: 
/* 3112:     */         case 106: 
/* 3113:     */         case 107: 
/* 3114:     */         case 108: 
/* 3115:     */         case 109: 
/* 3116:     */         case 110: 
/* 3117:     */         case 111: 
/* 3118:     */         case 112: 
/* 3119:     */         case 113: 
/* 3120:     */         case 114: 
/* 3121:     */         case 120: 
/* 3122:     */         case 121: 
/* 3123:     */         case 122: 
/* 3124:     */         case 127: 
/* 3125:     */         case 128: 
/* 3126:     */         case 129: 
/* 3127:     */         case 130: 
/* 3128:     */         case 131: 
/* 3129:     */         case 132: 
/* 3130:     */         case 133: 
/* 3131:     */         case 134: 
/* 3132:     */         case 135: 
/* 3133:     */         case 136: 
/* 3134:     */         case 137: 
/* 3135:     */         case 138: 
/* 3136:     */         case 139: 
/* 3137:     */         case 141: 
/* 3138:     */         case 143: 
/* 3139:     */         case 144: 
/* 3140:     */         case 145: 
/* 3141:     */         case 146: 
/* 3142:     */         case 147: 
/* 3143:     */         case 149: 
/* 3144:     */         default: 
/* 3145:2129 */           throw new NoViableAltException(_t);
/* 3146:     */         }
/* 3147:2133 */         _t = __t163;
/* 3148:2134 */         _t = _t.getNextSibling();
/* 3149:2135 */         break;
/* 3150:     */       case 3: 
/* 3151:     */         break;
/* 3152:     */       default: 
/* 3153:2143 */         throw new NoViableAltException(_t);
/* 3154:     */       }
/* 3155:2147 */       if (this.inputState.guessing == 0) {
/* 3156:2148 */         endFunctionTemplate(m);
/* 3157:     */       }
/* 3158:2150 */       _t = __t161;
/* 3159:2151 */       _t = _t.getNextSibling();
/* 3160:     */     }
/* 3161:     */     catch (RecognitionException ex)
/* 3162:     */     {
/* 3163:2154 */       if (this.inputState.guessing == 0)
/* 3164:     */       {
/* 3165:2155 */         reportError(ex);
/* 3166:2156 */         if (_t != null) {
/* 3167:2156 */           _t = _t.getNextSibling();
/* 3168:     */         }
/* 3169:     */       }
/* 3170:     */       else
/* 3171:     */       {
/* 3172:2158 */         throw ex;
/* 3173:     */       }
/* 3174:     */     }
/* 3175:2161 */     this._retTree = _t;
/* 3176:     */   }
/* 3177:     */   
/* 3178:     */   public final void aggregate(AST _t)
/* 3179:     */     throws RecognitionException
/* 3180:     */   {
/* 3181:2166 */     AST aggregate_AST_in = _t == ASTNULL ? null : _t;
/* 3182:2167 */     AST a = null;
/* 3183:     */     try
/* 3184:     */     {
/* 3185:2170 */       AST __t159 = _t;
/* 3186:2171 */       a = _t == ASTNULL ? null : _t;
/* 3187:2172 */       match(_t, 71);
/* 3188:2173 */       _t = _t.getFirstChild();
/* 3189:2174 */       if (this.inputState.guessing == 0) {
/* 3190:2175 */         beginFunctionTemplate(a, a);
/* 3191:     */       }
/* 3192:2177 */       expr(_t);
/* 3193:2178 */       _t = this._retTree;
/* 3194:2179 */       if (this.inputState.guessing == 0) {
/* 3195:2180 */         endFunctionTemplate(a);
/* 3196:     */       }
/* 3197:2182 */       _t = __t159;
/* 3198:2183 */       _t = _t.getNextSibling();
/* 3199:     */     }
/* 3200:     */     catch (RecognitionException ex)
/* 3201:     */     {
/* 3202:2186 */       if (this.inputState.guessing == 0)
/* 3203:     */       {
/* 3204:2187 */         reportError(ex);
/* 3205:2188 */         if (_t != null) {
/* 3206:2188 */           _t = _t.getNextSibling();
/* 3207:     */         }
/* 3208:     */       }
/* 3209:     */       else
/* 3210:     */       {
/* 3211:2190 */         throw ex;
/* 3212:     */       }
/* 3213:     */     }
/* 3214:2193 */     this._retTree = _t;
/* 3215:     */   }
/* 3216:     */   
/* 3217:     */   public final void constant(AST _t)
/* 3218:     */     throws RecognitionException
/* 3219:     */   {
/* 3220:2198 */     AST constant_AST_in = _t == ASTNULL ? null : _t;
/* 3221:     */     try
/* 3222:     */     {
/* 3223:2201 */       if (_t == null) {
/* 3224:2201 */         _t = ASTNULL;
/* 3225:     */       }
/* 3226:2202 */       switch (_t.getType())
/* 3227:     */       {
/* 3228:     */       case 95: 
/* 3229:2205 */         AST tmp35_AST_in = _t;
/* 3230:2206 */         match(_t, 95);
/* 3231:2207 */         _t = _t.getNextSibling();
/* 3232:2208 */         break;
/* 3233:     */       case 96: 
/* 3234:2212 */         AST tmp36_AST_in = _t;
/* 3235:2213 */         match(_t, 96);
/* 3236:2214 */         _t = _t.getNextSibling();
/* 3237:2215 */         break;
/* 3238:     */       case 124: 
/* 3239:2219 */         AST tmp37_AST_in = _t;
/* 3240:2220 */         match(_t, 124);
/* 3241:2221 */         _t = _t.getNextSibling();
/* 3242:2222 */         break;
/* 3243:     */       case 97: 
/* 3244:2226 */         AST tmp38_AST_in = _t;
/* 3245:2227 */         match(_t, 97);
/* 3246:2228 */         _t = _t.getNextSibling();
/* 3247:2229 */         break;
/* 3248:     */       case 98: 
/* 3249:2233 */         AST tmp39_AST_in = _t;
/* 3250:2234 */         match(_t, 98);
/* 3251:2235 */         _t = _t.getNextSibling();
/* 3252:2236 */         break;
/* 3253:     */       case 99: 
/* 3254:2240 */         AST tmp40_AST_in = _t;
/* 3255:2241 */         match(_t, 99);
/* 3256:2242 */         _t = _t.getNextSibling();
/* 3257:2243 */         break;
/* 3258:     */       case 125: 
/* 3259:2247 */         AST tmp41_AST_in = _t;
/* 3260:2248 */         match(_t, 125);
/* 3261:2249 */         _t = _t.getNextSibling();
/* 3262:2250 */         break;
/* 3263:     */       case 94: 
/* 3264:2254 */         AST tmp42_AST_in = _t;
/* 3265:2255 */         match(_t, 94);
/* 3266:2256 */         _t = _t.getNextSibling();
/* 3267:2257 */         break;
/* 3268:     */       case 100: 
/* 3269:2261 */         AST tmp43_AST_in = _t;
/* 3270:2262 */         match(_t, 100);
/* 3271:2263 */         _t = _t.getNextSibling();
/* 3272:2264 */         break;
/* 3273:     */       case 49: 
/* 3274:2268 */         AST tmp44_AST_in = _t;
/* 3275:2269 */         match(_t, 49);
/* 3276:2270 */         _t = _t.getNextSibling();
/* 3277:2271 */         break;
/* 3278:     */       case 20: 
/* 3279:2275 */         AST tmp45_AST_in = _t;
/* 3280:2276 */         match(_t, 20);
/* 3281:2277 */         _t = _t.getNextSibling();
/* 3282:2278 */         break;
/* 3283:     */       case 126: 
/* 3284:2282 */         AST tmp46_AST_in = _t;
/* 3285:2283 */         match(_t, 126);
/* 3286:2284 */         _t = _t.getNextSibling();
/* 3287:2285 */         break;
/* 3288:     */       default: 
/* 3289:2289 */         throw new NoViableAltException(_t);
/* 3290:     */       }
/* 3291:     */     }
/* 3292:     */     catch (RecognitionException ex)
/* 3293:     */     {
/* 3294:2294 */       if (this.inputState.guessing == 0)
/* 3295:     */       {
/* 3296:2295 */         reportError(ex);
/* 3297:2296 */         if (_t != null) {
/* 3298:2296 */           _t = _t.getNextSibling();
/* 3299:     */         }
/* 3300:     */       }
/* 3301:     */       else
/* 3302:     */       {
/* 3303:2298 */         throw ex;
/* 3304:     */       }
/* 3305:     */     }
/* 3306:2301 */     this._retTree = _t;
/* 3307:     */   }
/* 3308:     */   
/* 3309:     */   public final void arithmeticExpr(AST _t)
/* 3310:     */     throws RecognitionException
/* 3311:     */   {
/* 3312:2306 */     AST arithmeticExpr_AST_in = _t == ASTNULL ? null : _t;
/* 3313:     */     try
/* 3314:     */     {
/* 3315:2309 */       if (_t == null) {
/* 3316:2309 */         _t = ASTNULL;
/* 3317:     */       }
/* 3318:2310 */       switch (_t.getType())
/* 3319:     */       {
/* 3320:     */       case 115: 
/* 3321:     */       case 116: 
/* 3322:2314 */         additiveExpr(_t);
/* 3323:2315 */         _t = this._retTree;
/* 3324:2316 */         break;
/* 3325:     */       case 117: 
/* 3326:     */       case 118: 
/* 3327:     */       case 119: 
/* 3328:2322 */         multiplicativeExpr(_t);
/* 3329:2323 */         _t = this._retTree;
/* 3330:2324 */         break;
/* 3331:     */       case 90: 
/* 3332:2328 */         AST __t131 = _t;
/* 3333:2329 */         AST tmp47_AST_in = _t;
/* 3334:2330 */         match(_t, 90);
/* 3335:2331 */         _t = _t.getFirstChild();
/* 3336:2332 */         if (this.inputState.guessing == 0) {
/* 3337:2333 */           out("-");
/* 3338:     */         }
/* 3339:2335 */         nestedExprAfterMinusDiv(_t);
/* 3340:2336 */         _t = this._retTree;
/* 3341:2337 */         _t = __t131;
/* 3342:2338 */         _t = _t.getNextSibling();
/* 3343:2339 */         break;
/* 3344:     */       case 54: 
/* 3345:     */       case 74: 
/* 3346:2344 */         caseExpr(_t);
/* 3347:2345 */         _t = this._retTree;
/* 3348:2346 */         break;
/* 3349:     */       default: 
/* 3350:2350 */         throw new NoViableAltException(_t);
/* 3351:     */       }
/* 3352:     */     }
/* 3353:     */     catch (RecognitionException ex)
/* 3354:     */     {
/* 3355:2355 */       if (this.inputState.guessing == 0)
/* 3356:     */       {
/* 3357:2356 */         reportError(ex);
/* 3358:2357 */         if (_t != null) {
/* 3359:2357 */           _t = _t.getNextSibling();
/* 3360:     */         }
/* 3361:     */       }
/* 3362:     */       else
/* 3363:     */       {
/* 3364:2359 */         throw ex;
/* 3365:     */       }
/* 3366:     */     }
/* 3367:2362 */     this._retTree = _t;
/* 3368:     */   }
/* 3369:     */   
/* 3370:     */   public final void countExpr(AST _t)
/* 3371:     */     throws RecognitionException
/* 3372:     */   {
/* 3373:2367 */     AST countExpr_AST_in = _t == ASTNULL ? null : _t;
/* 3374:     */     try
/* 3375:     */     {
/* 3376:2370 */       if (_t == null) {
/* 3377:2370 */         _t = ASTNULL;
/* 3378:     */       }
/* 3379:2371 */       switch (_t.getType())
/* 3380:     */       {
/* 3381:     */       case 88: 
/* 3382:2374 */         AST tmp48_AST_in = _t;
/* 3383:2375 */         match(_t, 88);
/* 3384:2376 */         _t = _t.getNextSibling();
/* 3385:2377 */         if (this.inputState.guessing == 0) {
/* 3386:2378 */           out("*");
/* 3387:     */         }
/* 3388:     */         break;
/* 3389:     */       case 12: 
/* 3390:     */       case 15: 
/* 3391:     */       case 20: 
/* 3392:     */       case 39: 
/* 3393:     */       case 49: 
/* 3394:     */       case 54: 
/* 3395:     */       case 71: 
/* 3396:     */       case 74: 
/* 3397:     */       case 78: 
/* 3398:     */       case 81: 
/* 3399:     */       case 90: 
/* 3400:     */       case 94: 
/* 3401:     */       case 95: 
/* 3402:     */       case 96: 
/* 3403:     */       case 97: 
/* 3404:     */       case 98: 
/* 3405:     */       case 99: 
/* 3406:     */       case 100: 
/* 3407:     */       case 115: 
/* 3408:     */       case 116: 
/* 3409:     */       case 117: 
/* 3410:     */       case 118: 
/* 3411:     */       case 119: 
/* 3412:     */       case 123: 
/* 3413:     */       case 124: 
/* 3414:     */       case 125: 
/* 3415:     */       case 126: 
/* 3416:     */       case 140: 
/* 3417:     */       case 142: 
/* 3418:     */       case 148: 
/* 3419:     */       case 150: 
/* 3420:2414 */         simpleExpr(_t);
/* 3421:2415 */         _t = this._retTree;
/* 3422:2416 */         break;
/* 3423:     */       case 13: 
/* 3424:     */       case 14: 
/* 3425:     */       case 16: 
/* 3426:     */       case 17: 
/* 3427:     */       case 18: 
/* 3428:     */       case 19: 
/* 3429:     */       case 21: 
/* 3430:     */       case 22: 
/* 3431:     */       case 23: 
/* 3432:     */       case 24: 
/* 3433:     */       case 25: 
/* 3434:     */       case 26: 
/* 3435:     */       case 27: 
/* 3436:     */       case 28: 
/* 3437:     */       case 29: 
/* 3438:     */       case 30: 
/* 3439:     */       case 31: 
/* 3440:     */       case 32: 
/* 3441:     */       case 33: 
/* 3442:     */       case 34: 
/* 3443:     */       case 35: 
/* 3444:     */       case 36: 
/* 3445:     */       case 37: 
/* 3446:     */       case 38: 
/* 3447:     */       case 40: 
/* 3448:     */       case 41: 
/* 3449:     */       case 42: 
/* 3450:     */       case 43: 
/* 3451:     */       case 44: 
/* 3452:     */       case 45: 
/* 3453:     */       case 46: 
/* 3454:     */       case 47: 
/* 3455:     */       case 48: 
/* 3456:     */       case 50: 
/* 3457:     */       case 51: 
/* 3458:     */       case 52: 
/* 3459:     */       case 53: 
/* 3460:     */       case 55: 
/* 3461:     */       case 56: 
/* 3462:     */       case 57: 
/* 3463:     */       case 58: 
/* 3464:     */       case 59: 
/* 3465:     */       case 60: 
/* 3466:     */       case 61: 
/* 3467:     */       case 62: 
/* 3468:     */       case 63: 
/* 3469:     */       case 64: 
/* 3470:     */       case 65: 
/* 3471:     */       case 66: 
/* 3472:     */       case 67: 
/* 3473:     */       case 68: 
/* 3474:     */       case 69: 
/* 3475:     */       case 70: 
/* 3476:     */       case 72: 
/* 3477:     */       case 73: 
/* 3478:     */       case 75: 
/* 3479:     */       case 76: 
/* 3480:     */       case 77: 
/* 3481:     */       case 79: 
/* 3482:     */       case 80: 
/* 3483:     */       case 82: 
/* 3484:     */       case 83: 
/* 3485:     */       case 84: 
/* 3486:     */       case 85: 
/* 3487:     */       case 86: 
/* 3488:     */       case 87: 
/* 3489:     */       case 89: 
/* 3490:     */       case 91: 
/* 3491:     */       case 92: 
/* 3492:     */       case 93: 
/* 3493:     */       case 101: 
/* 3494:     */       case 102: 
/* 3495:     */       case 103: 
/* 3496:     */       case 104: 
/* 3497:     */       case 105: 
/* 3498:     */       case 106: 
/* 3499:     */       case 107: 
/* 3500:     */       case 108: 
/* 3501:     */       case 109: 
/* 3502:     */       case 110: 
/* 3503:     */       case 111: 
/* 3504:     */       case 112: 
/* 3505:     */       case 113: 
/* 3506:     */       case 114: 
/* 3507:     */       case 120: 
/* 3508:     */       case 121: 
/* 3509:     */       case 122: 
/* 3510:     */       case 127: 
/* 3511:     */       case 128: 
/* 3512:     */       case 129: 
/* 3513:     */       case 130: 
/* 3514:     */       case 131: 
/* 3515:     */       case 132: 
/* 3516:     */       case 133: 
/* 3517:     */       case 134: 
/* 3518:     */       case 135: 
/* 3519:     */       case 136: 
/* 3520:     */       case 137: 
/* 3521:     */       case 138: 
/* 3522:     */       case 139: 
/* 3523:     */       case 141: 
/* 3524:     */       case 143: 
/* 3525:     */       case 144: 
/* 3526:     */       case 145: 
/* 3527:     */       case 146: 
/* 3528:     */       case 147: 
/* 3529:     */       case 149: 
/* 3530:     */       default: 
/* 3531:2420 */         throw new NoViableAltException(_t);
/* 3532:     */       }
/* 3533:     */     }
/* 3534:     */     catch (RecognitionException ex)
/* 3535:     */     {
/* 3536:2425 */       if (this.inputState.guessing == 0)
/* 3537:     */       {
/* 3538:2426 */         reportError(ex);
/* 3539:2427 */         if (_t != null) {
/* 3540:2427 */           _t = _t.getNextSibling();
/* 3541:     */         }
/* 3542:     */       }
/* 3543:     */       else
/* 3544:     */       {
/* 3545:2429 */         throw ex;
/* 3546:     */       }
/* 3547:     */     }
/* 3548:2432 */     this._retTree = _t;
/* 3549:     */   }
/* 3550:     */   
/* 3551:     */   public final void simpleExpr(AST _t)
/* 3552:     */     throws RecognitionException
/* 3553:     */   {
/* 3554:2437 */     AST simpleExpr_AST_in = _t == ASTNULL ? null : _t;
/* 3555:2438 */     AST c = null;
/* 3556:     */     try
/* 3557:     */     {
/* 3558:2441 */       if (_t == null) {
/* 3559:2441 */         _t = ASTNULL;
/* 3560:     */       }
/* 3561:2442 */       switch (_t.getType())
/* 3562:     */       {
/* 3563:     */       case 20: 
/* 3564:     */       case 49: 
/* 3565:     */       case 94: 
/* 3566:     */       case 95: 
/* 3567:     */       case 96: 
/* 3568:     */       case 97: 
/* 3569:     */       case 98: 
/* 3570:     */       case 99: 
/* 3571:     */       case 100: 
/* 3572:     */       case 124: 
/* 3573:     */       case 125: 
/* 3574:     */       case 126: 
/* 3575:2456 */         c = _t == ASTNULL ? null : _t;
/* 3576:2457 */         constant(_t);
/* 3577:2458 */         _t = this._retTree;
/* 3578:2459 */         if (this.inputState.guessing == 0) {
/* 3579:2460 */           out(c);
/* 3580:     */         }
/* 3581:     */         break;
/* 3582:     */       case 39: 
/* 3583:2466 */         AST tmp49_AST_in = _t;
/* 3584:2467 */         match(_t, 39);
/* 3585:2468 */         _t = _t.getNextSibling();
/* 3586:2469 */         if (this.inputState.guessing == 0) {
/* 3587:2470 */           out("null");
/* 3588:     */         }
/* 3589:     */         break;
/* 3590:     */       case 15: 
/* 3591:     */       case 78: 
/* 3592:     */       case 140: 
/* 3593:     */       case 150: 
/* 3594:2479 */         addrExpr(_t);
/* 3595:2480 */         _t = this._retTree;
/* 3596:2481 */         break;
/* 3597:     */       case 142: 
/* 3598:2485 */         sqlToken(_t);
/* 3599:2486 */         _t = this._retTree;
/* 3600:2487 */         break;
/* 3601:     */       case 71: 
/* 3602:2491 */         aggregate(_t);
/* 3603:2492 */         _t = this._retTree;
/* 3604:2493 */         break;
/* 3605:     */       case 81: 
/* 3606:2497 */         methodCall(_t);
/* 3607:2498 */         _t = this._retTree;
/* 3608:2499 */         break;
/* 3609:     */       case 12: 
/* 3610:2503 */         count(_t);
/* 3611:2504 */         _t = this._retTree;
/* 3612:2505 */         break;
/* 3613:     */       case 123: 
/* 3614:     */       case 148: 
/* 3615:2510 */         parameter(_t);
/* 3616:2511 */         _t = this._retTree;
/* 3617:2512 */         break;
/* 3618:     */       case 54: 
/* 3619:     */       case 74: 
/* 3620:     */       case 90: 
/* 3621:     */       case 115: 
/* 3622:     */       case 116: 
/* 3623:     */       case 117: 
/* 3624:     */       case 118: 
/* 3625:     */       case 119: 
/* 3626:2523 */         arithmeticExpr(_t);
/* 3627:2524 */         _t = this._retTree;
/* 3628:2525 */         break;
/* 3629:     */       case 13: 
/* 3630:     */       case 14: 
/* 3631:     */       case 16: 
/* 3632:     */       case 17: 
/* 3633:     */       case 18: 
/* 3634:     */       case 19: 
/* 3635:     */       case 21: 
/* 3636:     */       case 22: 
/* 3637:     */       case 23: 
/* 3638:     */       case 24: 
/* 3639:     */       case 25: 
/* 3640:     */       case 26: 
/* 3641:     */       case 27: 
/* 3642:     */       case 28: 
/* 3643:     */       case 29: 
/* 3644:     */       case 30: 
/* 3645:     */       case 31: 
/* 3646:     */       case 32: 
/* 3647:     */       case 33: 
/* 3648:     */       case 34: 
/* 3649:     */       case 35: 
/* 3650:     */       case 36: 
/* 3651:     */       case 37: 
/* 3652:     */       case 38: 
/* 3653:     */       case 40: 
/* 3654:     */       case 41: 
/* 3655:     */       case 42: 
/* 3656:     */       case 43: 
/* 3657:     */       case 44: 
/* 3658:     */       case 45: 
/* 3659:     */       case 46: 
/* 3660:     */       case 47: 
/* 3661:     */       case 48: 
/* 3662:     */       case 50: 
/* 3663:     */       case 51: 
/* 3664:     */       case 52: 
/* 3665:     */       case 53: 
/* 3666:     */       case 55: 
/* 3667:     */       case 56: 
/* 3668:     */       case 57: 
/* 3669:     */       case 58: 
/* 3670:     */       case 59: 
/* 3671:     */       case 60: 
/* 3672:     */       case 61: 
/* 3673:     */       case 62: 
/* 3674:     */       case 63: 
/* 3675:     */       case 64: 
/* 3676:     */       case 65: 
/* 3677:     */       case 66: 
/* 3678:     */       case 67: 
/* 3679:     */       case 68: 
/* 3680:     */       case 69: 
/* 3681:     */       case 70: 
/* 3682:     */       case 72: 
/* 3683:     */       case 73: 
/* 3684:     */       case 75: 
/* 3685:     */       case 76: 
/* 3686:     */       case 77: 
/* 3687:     */       case 79: 
/* 3688:     */       case 80: 
/* 3689:     */       case 82: 
/* 3690:     */       case 83: 
/* 3691:     */       case 84: 
/* 3692:     */       case 85: 
/* 3693:     */       case 86: 
/* 3694:     */       case 87: 
/* 3695:     */       case 88: 
/* 3696:     */       case 89: 
/* 3697:     */       case 91: 
/* 3698:     */       case 92: 
/* 3699:     */       case 93: 
/* 3700:     */       case 101: 
/* 3701:     */       case 102: 
/* 3702:     */       case 103: 
/* 3703:     */       case 104: 
/* 3704:     */       case 105: 
/* 3705:     */       case 106: 
/* 3706:     */       case 107: 
/* 3707:     */       case 108: 
/* 3708:     */       case 109: 
/* 3709:     */       case 110: 
/* 3710:     */       case 111: 
/* 3711:     */       case 112: 
/* 3712:     */       case 113: 
/* 3713:     */       case 114: 
/* 3714:     */       case 120: 
/* 3715:     */       case 121: 
/* 3716:     */       case 122: 
/* 3717:     */       case 127: 
/* 3718:     */       case 128: 
/* 3719:     */       case 129: 
/* 3720:     */       case 130: 
/* 3721:     */       case 131: 
/* 3722:     */       case 132: 
/* 3723:     */       case 133: 
/* 3724:     */       case 134: 
/* 3725:     */       case 135: 
/* 3726:     */       case 136: 
/* 3727:     */       case 137: 
/* 3728:     */       case 138: 
/* 3729:     */       case 139: 
/* 3730:     */       case 141: 
/* 3731:     */       case 143: 
/* 3732:     */       case 144: 
/* 3733:     */       case 145: 
/* 3734:     */       case 146: 
/* 3735:     */       case 147: 
/* 3736:     */       case 149: 
/* 3737:     */       default: 
/* 3738:2529 */         throw new NoViableAltException(_t);
/* 3739:     */       }
/* 3740:     */     }
/* 3741:     */     catch (RecognitionException ex)
/* 3742:     */     {
/* 3743:2534 */       if (this.inputState.guessing == 0)
/* 3744:     */       {
/* 3745:2535 */         reportError(ex);
/* 3746:2536 */         if (_t != null) {
/* 3747:2536 */           _t = _t.getNextSibling();
/* 3748:     */         }
/* 3749:     */       }
/* 3750:     */       else
/* 3751:     */       {
/* 3752:2538 */         throw ex;
/* 3753:     */       }
/* 3754:     */     }
/* 3755:2541 */     this._retTree = _t;
/* 3756:     */   }
/* 3757:     */   
/* 3758:     */   public final void tableJoin(AST _t, AST parent)
/* 3759:     */     throws RecognitionException
/* 3760:     */   {
/* 3761:2548 */     AST tableJoin_AST_in = _t == ASTNULL ? null : _t;
/* 3762:2549 */     AST c = null;
/* 3763:2550 */     AST d = null;
/* 3764:     */     try
/* 3765:     */     {
/* 3766:2553 */       if (_t == null) {
/* 3767:2553 */         _t = ASTNULL;
/* 3768:     */       }
/* 3769:2554 */       switch (_t.getType())
/* 3770:     */       {
/* 3771:     */       case 136: 
/* 3772:2557 */         AST __t78 = _t;
/* 3773:2558 */         c = _t == ASTNULL ? null : _t;
/* 3774:2559 */         match(_t, 136);
/* 3775:2560 */         _t = _t.getFirstChild();
/* 3776:2561 */         if (this.inputState.guessing == 0)
/* 3777:     */         {
/* 3778:2562 */           out(" ");out(c);
/* 3779:     */         }
/* 3780:     */         for (;;)
/* 3781:     */         {
/* 3782:2567 */           if (_t == null) {
/* 3783:2567 */             _t = ASTNULL;
/* 3784:     */           }
/* 3785:2568 */           if ((_t.getType() != 134) && (_t.getType() != 136)) {
/* 3786:     */             break;
/* 3787:     */           }
/* 3788:2569 */           tableJoin(_t, c);
/* 3789:2570 */           _t = this._retTree;
/* 3790:     */         }
/* 3791:2578 */         _t = __t78;
/* 3792:2579 */         _t = _t.getNextSibling();
/* 3793:2580 */         break;
/* 3794:     */       case 134: 
/* 3795:2584 */         AST __t81 = _t;
/* 3796:2585 */         d = _t == ASTNULL ? null : _t;
/* 3797:2586 */         match(_t, 134);
/* 3798:2587 */         _t = _t.getFirstChild();
/* 3799:2588 */         if (this.inputState.guessing == 0) {
/* 3800:2589 */           nestedFromFragment(d, parent);
/* 3801:     */         }
/* 3802:     */         for (;;)
/* 3803:     */         {
/* 3804:2594 */           if (_t == null) {
/* 3805:2594 */             _t = ASTNULL;
/* 3806:     */           }
/* 3807:2595 */           if ((_t.getType() != 134) && (_t.getType() != 136)) {
/* 3808:     */             break;
/* 3809:     */           }
/* 3810:2596 */           tableJoin(_t, d);
/* 3811:2597 */           _t = this._retTree;
/* 3812:     */         }
/* 3813:2605 */         _t = __t81;
/* 3814:2606 */         _t = _t.getNextSibling();
/* 3815:2607 */         break;
/* 3816:     */       default: 
/* 3817:2611 */         throw new NoViableAltException(_t);
/* 3818:     */       }
/* 3819:     */     }
/* 3820:     */     catch (RecognitionException ex)
/* 3821:     */     {
/* 3822:2616 */       if (this.inputState.guessing == 0)
/* 3823:     */       {
/* 3824:2617 */         reportError(ex);
/* 3825:2618 */         if (_t != null) {
/* 3826:2618 */           _t = _t.getNextSibling();
/* 3827:     */         }
/* 3828:     */       }
/* 3829:     */       else
/* 3830:     */       {
/* 3831:2620 */         throw ex;
/* 3832:     */       }
/* 3833:     */     }
/* 3834:2623 */     this._retTree = _t;
/* 3835:     */   }
/* 3836:     */   
/* 3837:     */   public final void booleanOp(AST _t, boolean parens)
/* 3838:     */     throws RecognitionException
/* 3839:     */   {
/* 3840:2630 */     AST booleanOp_AST_in = _t == ASTNULL ? null : _t;
/* 3841:     */     try
/* 3842:     */     {
/* 3843:2633 */       if (_t == null) {
/* 3844:2633 */         _t = ASTNULL;
/* 3845:     */       }
/* 3846:2634 */       switch (_t.getType())
/* 3847:     */       {
/* 3848:     */       case 6: 
/* 3849:2637 */         AST __t85 = _t;
/* 3850:2638 */         AST tmp50_AST_in = _t;
/* 3851:2639 */         match(_t, 6);
/* 3852:2640 */         _t = _t.getFirstChild();
/* 3853:2641 */         booleanExpr(_t, true);
/* 3854:2642 */         _t = this._retTree;
/* 3855:2643 */         if (this.inputState.guessing == 0) {
/* 3856:2644 */           out(" and ");
/* 3857:     */         }
/* 3858:2646 */         booleanExpr(_t, true);
/* 3859:2647 */         _t = this._retTree;
/* 3860:2648 */         _t = __t85;
/* 3861:2649 */         _t = _t.getNextSibling();
/* 3862:2650 */         break;
/* 3863:     */       case 40: 
/* 3864:2654 */         AST __t86 = _t;
/* 3865:2655 */         AST tmp51_AST_in = _t;
/* 3866:2656 */         match(_t, 40);
/* 3867:2657 */         _t = _t.getFirstChild();
/* 3868:2658 */         if ((this.inputState.guessing == 0) && 
/* 3869:2659 */           (parens)) {
/* 3870:2659 */           out("(");
/* 3871:     */         }
/* 3872:2661 */         booleanExpr(_t, false);
/* 3873:2662 */         _t = this._retTree;
/* 3874:2663 */         if (this.inputState.guessing == 0) {
/* 3875:2664 */           out(" or ");
/* 3876:     */         }
/* 3877:2666 */         booleanExpr(_t, false);
/* 3878:2667 */         _t = this._retTree;
/* 3879:2668 */         if ((this.inputState.guessing == 0) && 
/* 3880:2669 */           (parens)) {
/* 3881:2669 */           out(")");
/* 3882:     */         }
/* 3883:2671 */         _t = __t86;
/* 3884:2672 */         _t = _t.getNextSibling();
/* 3885:2673 */         break;
/* 3886:     */       case 38: 
/* 3887:2677 */         AST __t87 = _t;
/* 3888:2678 */         AST tmp52_AST_in = _t;
/* 3889:2679 */         match(_t, 38);
/* 3890:2680 */         _t = _t.getFirstChild();
/* 3891:2681 */         if (this.inputState.guessing == 0) {
/* 3892:2682 */           out(" not (");
/* 3893:     */         }
/* 3894:2684 */         booleanExpr(_t, false);
/* 3895:2685 */         _t = this._retTree;
/* 3896:2686 */         if (this.inputState.guessing == 0) {
/* 3897:2687 */           out(")");
/* 3898:     */         }
/* 3899:2689 */         _t = __t87;
/* 3900:2690 */         _t = _t.getNextSibling();
/* 3901:2691 */         break;
/* 3902:     */       default: 
/* 3903:2695 */         throw new NoViableAltException(_t);
/* 3904:     */       }
/* 3905:     */     }
/* 3906:     */     catch (RecognitionException ex)
/* 3907:     */     {
/* 3908:2700 */       if (this.inputState.guessing == 0)
/* 3909:     */       {
/* 3910:2701 */         reportError(ex);
/* 3911:2702 */         if (_t != null) {
/* 3912:2702 */           _t = _t.getNextSibling();
/* 3913:     */         }
/* 3914:     */       }
/* 3915:     */       else
/* 3916:     */       {
/* 3917:2704 */         throw ex;
/* 3918:     */       }
/* 3919:     */     }
/* 3920:2707 */     this._retTree = _t;
/* 3921:     */   }
/* 3922:     */   
/* 3923:     */   public final void binaryComparisonExpression(AST _t)
/* 3924:     */     throws RecognitionException
/* 3925:     */   {
/* 3926:2712 */     AST binaryComparisonExpression_AST_in = _t == ASTNULL ? null : _t;
/* 3927:     */     try
/* 3928:     */     {
/* 3929:2715 */       if (_t == null) {
/* 3930:2715 */         _t = ASTNULL;
/* 3931:     */       }
/* 3932:2716 */       switch (_t.getType())
/* 3933:     */       {
/* 3934:     */       case 102: 
/* 3935:2719 */         AST __t91 = _t;
/* 3936:2720 */         AST tmp53_AST_in = _t;
/* 3937:2721 */         match(_t, 102);
/* 3938:2722 */         _t = _t.getFirstChild();
/* 3939:2723 */         expr(_t);
/* 3940:2724 */         _t = this._retTree;
/* 3941:2725 */         if (this.inputState.guessing == 0) {
/* 3942:2726 */           out("=");
/* 3943:     */         }
/* 3944:2728 */         expr(_t);
/* 3945:2729 */         _t = this._retTree;
/* 3946:2730 */         _t = __t91;
/* 3947:2731 */         _t = _t.getNextSibling();
/* 3948:2732 */         break;
/* 3949:     */       case 108: 
/* 3950:2736 */         AST __t92 = _t;
/* 3951:2737 */         AST tmp54_AST_in = _t;
/* 3952:2738 */         match(_t, 108);
/* 3953:2739 */         _t = _t.getFirstChild();
/* 3954:2740 */         expr(_t);
/* 3955:2741 */         _t = this._retTree;
/* 3956:2742 */         if (this.inputState.guessing == 0) {
/* 3957:2743 */           out("<>");
/* 3958:     */         }
/* 3959:2745 */         expr(_t);
/* 3960:2746 */         _t = this._retTree;
/* 3961:2747 */         _t = __t92;
/* 3962:2748 */         _t = _t.getNextSibling();
/* 3963:2749 */         break;
/* 3964:     */       case 111: 
/* 3965:2753 */         AST __t93 = _t;
/* 3966:2754 */         AST tmp55_AST_in = _t;
/* 3967:2755 */         match(_t, 111);
/* 3968:2756 */         _t = _t.getFirstChild();
/* 3969:2757 */         expr(_t);
/* 3970:2758 */         _t = this._retTree;
/* 3971:2759 */         if (this.inputState.guessing == 0) {
/* 3972:2760 */           out(">");
/* 3973:     */         }
/* 3974:2762 */         expr(_t);
/* 3975:2763 */         _t = this._retTree;
/* 3976:2764 */         _t = __t93;
/* 3977:2765 */         _t = _t.getNextSibling();
/* 3978:2766 */         break;
/* 3979:     */       case 113: 
/* 3980:2770 */         AST __t94 = _t;
/* 3981:2771 */         AST tmp56_AST_in = _t;
/* 3982:2772 */         match(_t, 113);
/* 3983:2773 */         _t = _t.getFirstChild();
/* 3984:2774 */         expr(_t);
/* 3985:2775 */         _t = this._retTree;
/* 3986:2776 */         if (this.inputState.guessing == 0) {
/* 3987:2777 */           out(">=");
/* 3988:     */         }
/* 3989:2779 */         expr(_t);
/* 3990:2780 */         _t = this._retTree;
/* 3991:2781 */         _t = __t94;
/* 3992:2782 */         _t = _t.getNextSibling();
/* 3993:2783 */         break;
/* 3994:     */       case 110: 
/* 3995:2787 */         AST __t95 = _t;
/* 3996:2788 */         AST tmp57_AST_in = _t;
/* 3997:2789 */         match(_t, 110);
/* 3998:2790 */         _t = _t.getFirstChild();
/* 3999:2791 */         expr(_t);
/* 4000:2792 */         _t = this._retTree;
/* 4001:2793 */         if (this.inputState.guessing == 0) {
/* 4002:2794 */           out("<");
/* 4003:     */         }
/* 4004:2796 */         expr(_t);
/* 4005:2797 */         _t = this._retTree;
/* 4006:2798 */         _t = __t95;
/* 4007:2799 */         _t = _t.getNextSibling();
/* 4008:2800 */         break;
/* 4009:     */       case 112: 
/* 4010:2804 */         AST __t96 = _t;
/* 4011:2805 */         AST tmp58_AST_in = _t;
/* 4012:2806 */         match(_t, 112);
/* 4013:2807 */         _t = _t.getFirstChild();
/* 4014:2808 */         expr(_t);
/* 4015:2809 */         _t = this._retTree;
/* 4016:2810 */         if (this.inputState.guessing == 0) {
/* 4017:2811 */           out("<=");
/* 4018:     */         }
/* 4019:2813 */         expr(_t);
/* 4020:2814 */         _t = this._retTree;
/* 4021:2815 */         _t = __t96;
/* 4022:2816 */         _t = _t.getNextSibling();
/* 4023:2817 */         break;
/* 4024:     */       case 103: 
/* 4025:     */       case 104: 
/* 4026:     */       case 105: 
/* 4027:     */       case 106: 
/* 4028:     */       case 107: 
/* 4029:     */       case 109: 
/* 4030:     */       default: 
/* 4031:2821 */         throw new NoViableAltException(_t);
/* 4032:     */       }
/* 4033:     */     }
/* 4034:     */     catch (RecognitionException ex)
/* 4035:     */     {
/* 4036:2826 */       if (this.inputState.guessing == 0)
/* 4037:     */       {
/* 4038:2827 */         reportError(ex);
/* 4039:2828 */         if (_t != null) {
/* 4040:2828 */           _t = _t.getNextSibling();
/* 4041:     */         }
/* 4042:     */       }
/* 4043:     */       else
/* 4044:     */       {
/* 4045:2830 */         throw ex;
/* 4046:     */       }
/* 4047:     */     }
/* 4048:2833 */     this._retTree = _t;
/* 4049:     */   }
/* 4050:     */   
/* 4051:     */   public final void exoticComparisonExpression(AST _t)
/* 4052:     */     throws RecognitionException
/* 4053:     */   {
/* 4054:2838 */     AST exoticComparisonExpression_AST_in = _t == ASTNULL ? null : _t;
/* 4055:     */     try
/* 4056:     */     {
/* 4057:2841 */       if (_t == null) {
/* 4058:2841 */         _t = ASTNULL;
/* 4059:     */       }
/* 4060:2842 */       switch (_t.getType())
/* 4061:     */       {
/* 4062:     */       case 34: 
/* 4063:2845 */         AST __t98 = _t;
/* 4064:2846 */         AST tmp59_AST_in = _t;
/* 4065:2847 */         match(_t, 34);
/* 4066:2848 */         _t = _t.getFirstChild();
/* 4067:2849 */         expr(_t);
/* 4068:2850 */         _t = this._retTree;
/* 4069:2851 */         if (this.inputState.guessing == 0) {
/* 4070:2852 */           out(" like ");
/* 4071:     */         }
/* 4072:2854 */         expr(_t);
/* 4073:2855 */         _t = this._retTree;
/* 4074:2856 */         likeEscape(_t);
/* 4075:2857 */         _t = this._retTree;
/* 4076:2858 */         _t = __t98;
/* 4077:2859 */         _t = _t.getNextSibling();
/* 4078:2860 */         break;
/* 4079:     */       case 84: 
/* 4080:2864 */         AST __t99 = _t;
/* 4081:2865 */         AST tmp60_AST_in = _t;
/* 4082:2866 */         match(_t, 84);
/* 4083:2867 */         _t = _t.getFirstChild();
/* 4084:2868 */         expr(_t);
/* 4085:2869 */         _t = this._retTree;
/* 4086:2870 */         if (this.inputState.guessing == 0) {
/* 4087:2871 */           out(" not like ");
/* 4088:     */         }
/* 4089:2873 */         expr(_t);
/* 4090:2874 */         _t = this._retTree;
/* 4091:2875 */         likeEscape(_t);
/* 4092:2876 */         _t = this._retTree;
/* 4093:2877 */         _t = __t99;
/* 4094:2878 */         _t = _t.getNextSibling();
/* 4095:2879 */         break;
/* 4096:     */       case 10: 
/* 4097:2883 */         AST __t100 = _t;
/* 4098:2884 */         AST tmp61_AST_in = _t;
/* 4099:2885 */         match(_t, 10);
/* 4100:2886 */         _t = _t.getFirstChild();
/* 4101:2887 */         expr(_t);
/* 4102:2888 */         _t = this._retTree;
/* 4103:2889 */         if (this.inputState.guessing == 0) {
/* 4104:2890 */           out(" between ");
/* 4105:     */         }
/* 4106:2892 */         expr(_t);
/* 4107:2893 */         _t = this._retTree;
/* 4108:2894 */         if (this.inputState.guessing == 0) {
/* 4109:2895 */           out(" and ");
/* 4110:     */         }
/* 4111:2897 */         expr(_t);
/* 4112:2898 */         _t = this._retTree;
/* 4113:2899 */         _t = __t100;
/* 4114:2900 */         _t = _t.getNextSibling();
/* 4115:2901 */         break;
/* 4116:     */       case 82: 
/* 4117:2905 */         AST __t101 = _t;
/* 4118:2906 */         AST tmp62_AST_in = _t;
/* 4119:2907 */         match(_t, 82);
/* 4120:2908 */         _t = _t.getFirstChild();
/* 4121:2909 */         expr(_t);
/* 4122:2910 */         _t = this._retTree;
/* 4123:2911 */         if (this.inputState.guessing == 0) {
/* 4124:2912 */           out(" not between ");
/* 4125:     */         }
/* 4126:2914 */         expr(_t);
/* 4127:2915 */         _t = this._retTree;
/* 4128:2916 */         if (this.inputState.guessing == 0) {
/* 4129:2917 */           out(" and ");
/* 4130:     */         }
/* 4131:2919 */         expr(_t);
/* 4132:2920 */         _t = this._retTree;
/* 4133:2921 */         _t = __t101;
/* 4134:2922 */         _t = _t.getNextSibling();
/* 4135:2923 */         break;
/* 4136:     */       case 26: 
/* 4137:2927 */         AST __t102 = _t;
/* 4138:2928 */         AST tmp63_AST_in = _t;
/* 4139:2929 */         match(_t, 26);
/* 4140:2930 */         _t = _t.getFirstChild();
/* 4141:2931 */         expr(_t);
/* 4142:2932 */         _t = this._retTree;
/* 4143:2933 */         if (this.inputState.guessing == 0) {
/* 4144:2934 */           out(" in");
/* 4145:     */         }
/* 4146:2936 */         inList(_t);
/* 4147:2937 */         _t = this._retTree;
/* 4148:2938 */         _t = __t102;
/* 4149:2939 */         _t = _t.getNextSibling();
/* 4150:2940 */         break;
/* 4151:     */       case 83: 
/* 4152:2944 */         AST __t103 = _t;
/* 4153:2945 */         AST tmp64_AST_in = _t;
/* 4154:2946 */         match(_t, 83);
/* 4155:2947 */         _t = _t.getFirstChild();
/* 4156:2948 */         expr(_t);
/* 4157:2949 */         _t = this._retTree;
/* 4158:2950 */         if (this.inputState.guessing == 0) {
/* 4159:2951 */           out(" not in ");
/* 4160:     */         }
/* 4161:2953 */         inList(_t);
/* 4162:2954 */         _t = this._retTree;
/* 4163:2955 */         _t = __t103;
/* 4164:2956 */         _t = _t.getNextSibling();
/* 4165:2957 */         break;
/* 4166:     */       case 19: 
/* 4167:2961 */         AST __t104 = _t;
/* 4168:2962 */         AST tmp65_AST_in = _t;
/* 4169:2963 */         match(_t, 19);
/* 4170:2964 */         _t = _t.getFirstChild();
/* 4171:2965 */         if (this.inputState.guessing == 0)
/* 4172:     */         {
/* 4173:2966 */           optionalSpace();out("exists ");
/* 4174:     */         }
/* 4175:2968 */         quantified(_t);
/* 4176:2969 */         _t = this._retTree;
/* 4177:2970 */         _t = __t104;
/* 4178:2971 */         _t = _t.getNextSibling();
/* 4179:2972 */         break;
/* 4180:     */       case 80: 
/* 4181:2976 */         AST __t105 = _t;
/* 4182:2977 */         AST tmp66_AST_in = _t;
/* 4183:2978 */         match(_t, 80);
/* 4184:2979 */         _t = _t.getFirstChild();
/* 4185:2980 */         expr(_t);
/* 4186:2981 */         _t = this._retTree;
/* 4187:2982 */         _t = __t105;
/* 4188:2983 */         _t = _t.getNextSibling();
/* 4189:2984 */         if (this.inputState.guessing == 0) {
/* 4190:2985 */           out(" is null");
/* 4191:     */         }
/* 4192:     */         break;
/* 4193:     */       case 79: 
/* 4194:2991 */         AST __t106 = _t;
/* 4195:2992 */         AST tmp67_AST_in = _t;
/* 4196:2993 */         match(_t, 79);
/* 4197:2994 */         _t = _t.getFirstChild();
/* 4198:2995 */         expr(_t);
/* 4199:2996 */         _t = this._retTree;
/* 4200:2997 */         _t = __t106;
/* 4201:2998 */         _t = _t.getNextSibling();
/* 4202:2999 */         if (this.inputState.guessing == 0) {
/* 4203:3000 */           out(" is not null");
/* 4204:     */         }
/* 4205:     */         break;
/* 4206:     */       default: 
/* 4207:3006 */         throw new NoViableAltException(_t);
/* 4208:     */       }
/* 4209:     */     }
/* 4210:     */     catch (RecognitionException ex)
/* 4211:     */     {
/* 4212:3011 */       if (this.inputState.guessing == 0)
/* 4213:     */       {
/* 4214:3012 */         reportError(ex);
/* 4215:3013 */         if (_t != null) {
/* 4216:3013 */           _t = _t.getNextSibling();
/* 4217:     */         }
/* 4218:     */       }
/* 4219:     */       else
/* 4220:     */       {
/* 4221:3015 */         throw ex;
/* 4222:     */       }
/* 4223:     */     }
/* 4224:3018 */     this._retTree = _t;
/* 4225:     */   }
/* 4226:     */   
/* 4227:     */   public final void likeEscape(AST _t)
/* 4228:     */     throws RecognitionException
/* 4229:     */   {
/* 4230:3023 */     AST likeEscape_AST_in = _t == ASTNULL ? null : _t;
/* 4231:     */     try
/* 4232:     */     {
/* 4233:3027 */       if (_t == null) {
/* 4234:3027 */         _t = ASTNULL;
/* 4235:     */       }
/* 4236:3028 */       switch (_t.getType())
/* 4237:     */       {
/* 4238:     */       case 18: 
/* 4239:3031 */         AST __t109 = _t;
/* 4240:3032 */         AST tmp68_AST_in = _t;
/* 4241:3033 */         match(_t, 18);
/* 4242:3034 */         _t = _t.getFirstChild();
/* 4243:3035 */         if (this.inputState.guessing == 0) {
/* 4244:3036 */           out(" escape ");
/* 4245:     */         }
/* 4246:3038 */         expr(_t);
/* 4247:3039 */         _t = this._retTree;
/* 4248:3040 */         _t = __t109;
/* 4249:3041 */         _t = _t.getNextSibling();
/* 4250:3042 */         break;
/* 4251:     */       case 3: 
/* 4252:     */         break;
/* 4253:     */       default: 
/* 4254:3050 */         throw new NoViableAltException(_t);
/* 4255:     */       }
/* 4256:     */     }
/* 4257:     */     catch (RecognitionException ex)
/* 4258:     */     {
/* 4259:3056 */       if (this.inputState.guessing == 0)
/* 4260:     */       {
/* 4261:3057 */         reportError(ex);
/* 4262:3058 */         if (_t != null) {
/* 4263:3058 */           _t = _t.getNextSibling();
/* 4264:     */         }
/* 4265:     */       }
/* 4266:     */       else
/* 4267:     */       {
/* 4268:3060 */         throw ex;
/* 4269:     */       }
/* 4270:     */     }
/* 4271:3063 */     this._retTree = _t;
/* 4272:     */   }
/* 4273:     */   
/* 4274:     */   public final void inList(AST _t)
/* 4275:     */     throws RecognitionException
/* 4276:     */   {
/* 4277:3068 */     AST inList_AST_in = _t == ASTNULL ? null : _t;
/* 4278:     */     try
/* 4279:     */     {
/* 4280:3071 */       AST __t111 = _t;
/* 4281:3072 */       AST tmp69_AST_in = _t;
/* 4282:3073 */       match(_t, 77);
/* 4283:3074 */       _t = _t.getFirstChild();
/* 4284:3075 */       if (this.inputState.guessing == 0) {
/* 4285:3076 */         out(" ");
/* 4286:     */       }
/* 4287:3079 */       if (_t == null) {
/* 4288:3079 */         _t = ASTNULL;
/* 4289:     */       }
/* 4290:3080 */       switch (_t.getType())
/* 4291:     */       {
/* 4292:     */       case 45: 
/* 4293:3083 */         parenSelect(_t);
/* 4294:3084 */         _t = this._retTree;
/* 4295:3085 */         break;
/* 4296:     */       case 3: 
/* 4297:     */       case 12: 
/* 4298:     */       case 15: 
/* 4299:     */       case 20: 
/* 4300:     */       case 39: 
/* 4301:     */       case 49: 
/* 4302:     */       case 54: 
/* 4303:     */       case 71: 
/* 4304:     */       case 74: 
/* 4305:     */       case 78: 
/* 4306:     */       case 81: 
/* 4307:     */       case 90: 
/* 4308:     */       case 92: 
/* 4309:     */       case 94: 
/* 4310:     */       case 95: 
/* 4311:     */       case 96: 
/* 4312:     */       case 97: 
/* 4313:     */       case 98: 
/* 4314:     */       case 99: 
/* 4315:     */       case 100: 
/* 4316:     */       case 115: 
/* 4317:     */       case 116: 
/* 4318:     */       case 117: 
/* 4319:     */       case 118: 
/* 4320:     */       case 119: 
/* 4321:     */       case 123: 
/* 4322:     */       case 124: 
/* 4323:     */       case 125: 
/* 4324:     */       case 126: 
/* 4325:     */       case 140: 
/* 4326:     */       case 142: 
/* 4327:     */       case 148: 
/* 4328:     */       case 150: 
/* 4329:3121 */         simpleExprList(_t);
/* 4330:3122 */         _t = this._retTree;
/* 4331:3123 */         break;
/* 4332:     */       case 4: 
/* 4333:     */       case 5: 
/* 4334:     */       case 6: 
/* 4335:     */       case 7: 
/* 4336:     */       case 8: 
/* 4337:     */       case 9: 
/* 4338:     */       case 10: 
/* 4339:     */       case 11: 
/* 4340:     */       case 13: 
/* 4341:     */       case 14: 
/* 4342:     */       case 16: 
/* 4343:     */       case 17: 
/* 4344:     */       case 18: 
/* 4345:     */       case 19: 
/* 4346:     */       case 21: 
/* 4347:     */       case 22: 
/* 4348:     */       case 23: 
/* 4349:     */       case 24: 
/* 4350:     */       case 25: 
/* 4351:     */       case 26: 
/* 4352:     */       case 27: 
/* 4353:     */       case 28: 
/* 4354:     */       case 29: 
/* 4355:     */       case 30: 
/* 4356:     */       case 31: 
/* 4357:     */       case 32: 
/* 4358:     */       case 33: 
/* 4359:     */       case 34: 
/* 4360:     */       case 35: 
/* 4361:     */       case 36: 
/* 4362:     */       case 37: 
/* 4363:     */       case 38: 
/* 4364:     */       case 40: 
/* 4365:     */       case 41: 
/* 4366:     */       case 42: 
/* 4367:     */       case 43: 
/* 4368:     */       case 44: 
/* 4369:     */       case 46: 
/* 4370:     */       case 47: 
/* 4371:     */       case 48: 
/* 4372:     */       case 50: 
/* 4373:     */       case 51: 
/* 4374:     */       case 52: 
/* 4375:     */       case 53: 
/* 4376:     */       case 55: 
/* 4377:     */       case 56: 
/* 4378:     */       case 57: 
/* 4379:     */       case 58: 
/* 4380:     */       case 59: 
/* 4381:     */       case 60: 
/* 4382:     */       case 61: 
/* 4383:     */       case 62: 
/* 4384:     */       case 63: 
/* 4385:     */       case 64: 
/* 4386:     */       case 65: 
/* 4387:     */       case 66: 
/* 4388:     */       case 67: 
/* 4389:     */       case 68: 
/* 4390:     */       case 69: 
/* 4391:     */       case 70: 
/* 4392:     */       case 72: 
/* 4393:     */       case 73: 
/* 4394:     */       case 75: 
/* 4395:     */       case 76: 
/* 4396:     */       case 77: 
/* 4397:     */       case 79: 
/* 4398:     */       case 80: 
/* 4399:     */       case 82: 
/* 4400:     */       case 83: 
/* 4401:     */       case 84: 
/* 4402:     */       case 85: 
/* 4403:     */       case 86: 
/* 4404:     */       case 87: 
/* 4405:     */       case 88: 
/* 4406:     */       case 89: 
/* 4407:     */       case 91: 
/* 4408:     */       case 93: 
/* 4409:     */       case 101: 
/* 4410:     */       case 102: 
/* 4411:     */       case 103: 
/* 4412:     */       case 104: 
/* 4413:     */       case 105: 
/* 4414:     */       case 106: 
/* 4415:     */       case 107: 
/* 4416:     */       case 108: 
/* 4417:     */       case 109: 
/* 4418:     */       case 110: 
/* 4419:     */       case 111: 
/* 4420:     */       case 112: 
/* 4421:     */       case 113: 
/* 4422:     */       case 114: 
/* 4423:     */       case 120: 
/* 4424:     */       case 121: 
/* 4425:     */       case 122: 
/* 4426:     */       case 127: 
/* 4427:     */       case 128: 
/* 4428:     */       case 129: 
/* 4429:     */       case 130: 
/* 4430:     */       case 131: 
/* 4431:     */       case 132: 
/* 4432:     */       case 133: 
/* 4433:     */       case 134: 
/* 4434:     */       case 135: 
/* 4435:     */       case 136: 
/* 4436:     */       case 137: 
/* 4437:     */       case 138: 
/* 4438:     */       case 139: 
/* 4439:     */       case 141: 
/* 4440:     */       case 143: 
/* 4441:     */       case 144: 
/* 4442:     */       case 145: 
/* 4443:     */       case 146: 
/* 4444:     */       case 147: 
/* 4445:     */       case 149: 
/* 4446:     */       default: 
/* 4447:3127 */         throw new NoViableAltException(_t);
/* 4448:     */       }
/* 4449:3131 */       _t = __t111;
/* 4450:3132 */       _t = _t.getNextSibling();
/* 4451:     */     }
/* 4452:     */     catch (RecognitionException ex)
/* 4453:     */     {
/* 4454:3135 */       if (this.inputState.guessing == 0)
/* 4455:     */       {
/* 4456:3136 */         reportError(ex);
/* 4457:3137 */         if (_t != null) {
/* 4458:3137 */           _t = _t.getNextSibling();
/* 4459:     */         }
/* 4460:     */       }
/* 4461:     */       else
/* 4462:     */       {
/* 4463:3139 */         throw ex;
/* 4464:     */       }
/* 4465:     */     }
/* 4466:3142 */     this._retTree = _t;
/* 4467:     */   }
/* 4468:     */   
/* 4469:     */   public final void quantified(AST _t)
/* 4470:     */     throws RecognitionException
/* 4471:     */   {
/* 4472:3147 */     AST quantified_AST_in = _t == ASTNULL ? null : _t;
/* 4473:     */     try
/* 4474:     */     {
/* 4475:3150 */       if (this.inputState.guessing == 0) {
/* 4476:3151 */         out("(");
/* 4477:     */       }
/* 4478:3154 */       if (_t == null) {
/* 4479:3154 */         _t = ASTNULL;
/* 4480:     */       }
/* 4481:3155 */       switch (_t.getType())
/* 4482:     */       {
/* 4483:     */       case 142: 
/* 4484:3158 */         sqlToken(_t);
/* 4485:3159 */         _t = this._retTree;
/* 4486:3160 */         break;
/* 4487:     */       case 45: 
/* 4488:3164 */         selectStatement(_t);
/* 4489:3165 */         _t = this._retTree;
/* 4490:3166 */         break;
/* 4491:     */       default: 
/* 4492:3170 */         throw new NoViableAltException(_t);
/* 4493:     */       }
/* 4494:3174 */       if (this.inputState.guessing == 0) {
/* 4495:3175 */         out(")");
/* 4496:     */       }
/* 4497:     */     }
/* 4498:     */     catch (RecognitionException ex)
/* 4499:     */     {
/* 4500:3179 */       if (this.inputState.guessing == 0)
/* 4501:     */       {
/* 4502:3180 */         reportError(ex);
/* 4503:3181 */         if (_t != null) {
/* 4504:3181 */           _t = _t.getNextSibling();
/* 4505:     */         }
/* 4506:     */       }
/* 4507:     */       else
/* 4508:     */       {
/* 4509:3183 */         throw ex;
/* 4510:     */       }
/* 4511:     */     }
/* 4512:3186 */     this._retTree = _t;
/* 4513:     */   }
/* 4514:     */   
/* 4515:     */   public final void parenSelect(AST _t)
/* 4516:     */     throws RecognitionException
/* 4517:     */   {
/* 4518:3191 */     AST parenSelect_AST_in = _t == ASTNULL ? null : _t;
/* 4519:     */     try
/* 4520:     */     {
/* 4521:3194 */       if (this.inputState.guessing == 0) {
/* 4522:3195 */         out("(");
/* 4523:     */       }
/* 4524:3197 */       selectStatement(_t);
/* 4525:3198 */       _t = this._retTree;
/* 4526:3199 */       if (this.inputState.guessing == 0) {
/* 4527:3200 */         out(")");
/* 4528:     */       }
/* 4529:     */     }
/* 4530:     */     catch (RecognitionException ex)
/* 4531:     */     {
/* 4532:3204 */       if (this.inputState.guessing == 0)
/* 4533:     */       {
/* 4534:3205 */         reportError(ex);
/* 4535:3206 */         if (_t != null) {
/* 4536:3206 */           _t = _t.getNextSibling();
/* 4537:     */         }
/* 4538:     */       }
/* 4539:     */       else
/* 4540:     */       {
/* 4541:3208 */         throw ex;
/* 4542:     */       }
/* 4543:     */     }
/* 4544:3211 */     this._retTree = _t;
/* 4545:     */   }
/* 4546:     */   
/* 4547:     */   public final void simpleExprList(AST _t)
/* 4548:     */     throws RecognitionException
/* 4549:     */   {
/* 4550:3216 */     AST simpleExprList_AST_in = _t == ASTNULL ? null : _t;
/* 4551:3217 */     AST e = null;
/* 4552:     */     try
/* 4553:     */     {
/* 4554:3220 */       if (this.inputState.guessing == 0) {
/* 4555:3221 */         out("(");
/* 4556:     */       }
/* 4557:     */       for (;;)
/* 4558:     */       {
/* 4559:3226 */         if (_t == null) {
/* 4560:3226 */           _t = ASTNULL;
/* 4561:     */         }
/* 4562:3227 */         if (!_tokenSet_3.member(_t.getType())) {
/* 4563:     */           break;
/* 4564:     */         }
/* 4565:3228 */         e = _t == ASTNULL ? null : _t;
/* 4566:3229 */         simpleOrTupleExpr(_t);
/* 4567:3230 */         _t = this._retTree;
/* 4568:3231 */         if (this.inputState.guessing == 0) {
/* 4569:3232 */           separator(e, " , ");
/* 4570:     */         }
/* 4571:     */       }
/* 4572:3241 */       if (this.inputState.guessing == 0) {
/* 4573:3242 */         out(")");
/* 4574:     */       }
/* 4575:     */     }
/* 4576:     */     catch (RecognitionException ex)
/* 4577:     */     {
/* 4578:3246 */       if (this.inputState.guessing == 0)
/* 4579:     */       {
/* 4580:3247 */         reportError(ex);
/* 4581:3248 */         if (_t != null) {
/* 4582:3248 */           _t = _t.getNextSibling();
/* 4583:     */         }
/* 4584:     */       }
/* 4585:     */       else
/* 4586:     */       {
/* 4587:3250 */         throw ex;
/* 4588:     */       }
/* 4589:     */     }
/* 4590:3253 */     this._retTree = _t;
/* 4591:     */   }
/* 4592:     */   
/* 4593:     */   public final void simpleOrTupleExpr(AST _t)
/* 4594:     */     throws RecognitionException
/* 4595:     */   {
/* 4596:3258 */     AST simpleOrTupleExpr_AST_in = _t == ASTNULL ? null : _t;
/* 4597:     */     try
/* 4598:     */     {
/* 4599:3261 */       if (_t == null) {
/* 4600:3261 */         _t = ASTNULL;
/* 4601:     */       }
/* 4602:3262 */       switch (_t.getType())
/* 4603:     */       {
/* 4604:     */       case 12: 
/* 4605:     */       case 15: 
/* 4606:     */       case 20: 
/* 4607:     */       case 39: 
/* 4608:     */       case 49: 
/* 4609:     */       case 54: 
/* 4610:     */       case 71: 
/* 4611:     */       case 74: 
/* 4612:     */       case 78: 
/* 4613:     */       case 81: 
/* 4614:     */       case 90: 
/* 4615:     */       case 94: 
/* 4616:     */       case 95: 
/* 4617:     */       case 96: 
/* 4618:     */       case 97: 
/* 4619:     */       case 98: 
/* 4620:     */       case 99: 
/* 4621:     */       case 100: 
/* 4622:     */       case 115: 
/* 4623:     */       case 116: 
/* 4624:     */       case 117: 
/* 4625:     */       case 118: 
/* 4626:     */       case 119: 
/* 4627:     */       case 123: 
/* 4628:     */       case 124: 
/* 4629:     */       case 125: 
/* 4630:     */       case 126: 
/* 4631:     */       case 140: 
/* 4632:     */       case 142: 
/* 4633:     */       case 148: 
/* 4634:     */       case 150: 
/* 4635:3295 */         simpleExpr(_t);
/* 4636:3296 */         _t = this._retTree;
/* 4637:3297 */         break;
/* 4638:     */       case 92: 
/* 4639:3301 */         tupleExpr(_t);
/* 4640:3302 */         _t = this._retTree;
/* 4641:3303 */         break;
/* 4642:     */       case 13: 
/* 4643:     */       case 14: 
/* 4644:     */       case 16: 
/* 4645:     */       case 17: 
/* 4646:     */       case 18: 
/* 4647:     */       case 19: 
/* 4648:     */       case 21: 
/* 4649:     */       case 22: 
/* 4650:     */       case 23: 
/* 4651:     */       case 24: 
/* 4652:     */       case 25: 
/* 4653:     */       case 26: 
/* 4654:     */       case 27: 
/* 4655:     */       case 28: 
/* 4656:     */       case 29: 
/* 4657:     */       case 30: 
/* 4658:     */       case 31: 
/* 4659:     */       case 32: 
/* 4660:     */       case 33: 
/* 4661:     */       case 34: 
/* 4662:     */       case 35: 
/* 4663:     */       case 36: 
/* 4664:     */       case 37: 
/* 4665:     */       case 38: 
/* 4666:     */       case 40: 
/* 4667:     */       case 41: 
/* 4668:     */       case 42: 
/* 4669:     */       case 43: 
/* 4670:     */       case 44: 
/* 4671:     */       case 45: 
/* 4672:     */       case 46: 
/* 4673:     */       case 47: 
/* 4674:     */       case 48: 
/* 4675:     */       case 50: 
/* 4676:     */       case 51: 
/* 4677:     */       case 52: 
/* 4678:     */       case 53: 
/* 4679:     */       case 55: 
/* 4680:     */       case 56: 
/* 4681:     */       case 57: 
/* 4682:     */       case 58: 
/* 4683:     */       case 59: 
/* 4684:     */       case 60: 
/* 4685:     */       case 61: 
/* 4686:     */       case 62: 
/* 4687:     */       case 63: 
/* 4688:     */       case 64: 
/* 4689:     */       case 65: 
/* 4690:     */       case 66: 
/* 4691:     */       case 67: 
/* 4692:     */       case 68: 
/* 4693:     */       case 69: 
/* 4694:     */       case 70: 
/* 4695:     */       case 72: 
/* 4696:     */       case 73: 
/* 4697:     */       case 75: 
/* 4698:     */       case 76: 
/* 4699:     */       case 77: 
/* 4700:     */       case 79: 
/* 4701:     */       case 80: 
/* 4702:     */       case 82: 
/* 4703:     */       case 83: 
/* 4704:     */       case 84: 
/* 4705:     */       case 85: 
/* 4706:     */       case 86: 
/* 4707:     */       case 87: 
/* 4708:     */       case 88: 
/* 4709:     */       case 89: 
/* 4710:     */       case 91: 
/* 4711:     */       case 93: 
/* 4712:     */       case 101: 
/* 4713:     */       case 102: 
/* 4714:     */       case 103: 
/* 4715:     */       case 104: 
/* 4716:     */       case 105: 
/* 4717:     */       case 106: 
/* 4718:     */       case 107: 
/* 4719:     */       case 108: 
/* 4720:     */       case 109: 
/* 4721:     */       case 110: 
/* 4722:     */       case 111: 
/* 4723:     */       case 112: 
/* 4724:     */       case 113: 
/* 4725:     */       case 114: 
/* 4726:     */       case 120: 
/* 4727:     */       case 121: 
/* 4728:     */       case 122: 
/* 4729:     */       case 127: 
/* 4730:     */       case 128: 
/* 4731:     */       case 129: 
/* 4732:     */       case 130: 
/* 4733:     */       case 131: 
/* 4734:     */       case 132: 
/* 4735:     */       case 133: 
/* 4736:     */       case 134: 
/* 4737:     */       case 135: 
/* 4738:     */       case 136: 
/* 4739:     */       case 137: 
/* 4740:     */       case 138: 
/* 4741:     */       case 139: 
/* 4742:     */       case 141: 
/* 4743:     */       case 143: 
/* 4744:     */       case 144: 
/* 4745:     */       case 145: 
/* 4746:     */       case 146: 
/* 4747:     */       case 147: 
/* 4748:     */       case 149: 
/* 4749:     */       default: 
/* 4750:3307 */         throw new NoViableAltException(_t);
/* 4751:     */       }
/* 4752:     */     }
/* 4753:     */     catch (RecognitionException ex)
/* 4754:     */     {
/* 4755:3312 */       if (this.inputState.guessing == 0)
/* 4756:     */       {
/* 4757:3313 */         reportError(ex);
/* 4758:3314 */         if (_t != null) {
/* 4759:3314 */           _t = _t.getNextSibling();
/* 4760:     */         }
/* 4761:     */       }
/* 4762:     */       else
/* 4763:     */       {
/* 4764:3316 */         throw ex;
/* 4765:     */       }
/* 4766:     */     }
/* 4767:3319 */     this._retTree = _t;
/* 4768:     */   }
/* 4769:     */   
/* 4770:     */   public final void tupleExpr(AST _t)
/* 4771:     */     throws RecognitionException
/* 4772:     */   {
/* 4773:3324 */     AST tupleExpr_AST_in = _t == ASTNULL ? null : _t;
/* 4774:3325 */     AST e = null;
/* 4775:     */     try
/* 4776:     */     {
/* 4777:3328 */       AST __t122 = _t;
/* 4778:3329 */       AST tmp70_AST_in = _t;
/* 4779:3330 */       match(_t, 92);
/* 4780:3331 */       _t = _t.getFirstChild();
/* 4781:3332 */       if (this.inputState.guessing == 0) {
/* 4782:3333 */         out("(");
/* 4783:     */       }
/* 4784:     */       for (;;)
/* 4785:     */       {
/* 4786:3338 */         if (_t == null) {
/* 4787:3338 */           _t = ASTNULL;
/* 4788:     */         }
/* 4789:3339 */         if (!_tokenSet_4.member(_t.getType())) {
/* 4790:     */           break;
/* 4791:     */         }
/* 4792:3340 */         e = _t == ASTNULL ? null : _t;
/* 4793:3341 */         expr(_t);
/* 4794:3342 */         _t = this._retTree;
/* 4795:3343 */         if (this.inputState.guessing == 0) {
/* 4796:3344 */           separator(e, " , ");
/* 4797:     */         }
/* 4798:     */       }
/* 4799:3353 */       if (this.inputState.guessing == 0) {
/* 4800:3354 */         out(")");
/* 4801:     */       }
/* 4802:3356 */       _t = __t122;
/* 4803:3357 */       _t = _t.getNextSibling();
/* 4804:     */     }
/* 4805:     */     catch (RecognitionException ex)
/* 4806:     */     {
/* 4807:3360 */       if (this.inputState.guessing == 0)
/* 4808:     */       {
/* 4809:3361 */         reportError(ex);
/* 4810:3362 */         if (_t != null) {
/* 4811:3362 */           _t = _t.getNextSibling();
/* 4812:     */         }
/* 4813:     */       }
/* 4814:     */       else
/* 4815:     */       {
/* 4816:3364 */         throw ex;
/* 4817:     */       }
/* 4818:     */     }
/* 4819:3367 */     this._retTree = _t;
/* 4820:     */   }
/* 4821:     */   
/* 4822:     */   public final void addrExpr(AST _t)
/* 4823:     */     throws RecognitionException
/* 4824:     */   {
/* 4825:3372 */     AST addrExpr_AST_in = _t == ASTNULL ? null : _t;
/* 4826:3373 */     AST r = null;
/* 4827:3374 */     AST i = null;
/* 4828:3375 */     AST j = null;
/* 4829:3376 */     AST v = null;
/* 4830:     */     try
/* 4831:     */     {
/* 4832:3379 */       if (_t == null) {
/* 4833:3379 */         _t = ASTNULL;
/* 4834:     */       }
/* 4835:3380 */       switch (_t.getType())
/* 4836:     */       {
/* 4837:     */       case 15: 
/* 4838:3383 */         AST __t170 = _t;
/* 4839:3384 */         r = _t == ASTNULL ? null : _t;
/* 4840:3385 */         match(_t, 15);
/* 4841:3386 */         _t = _t.getFirstChild();
/* 4842:3387 */         AST tmp71_AST_in = _t;
/* 4843:3388 */         if (_t == null) {
/* 4844:3388 */           throw new MismatchedTokenException();
/* 4845:     */         }
/* 4846:3389 */         _t = _t.getNextSibling();
/* 4847:3390 */         AST tmp72_AST_in = _t;
/* 4848:3391 */         if (_t == null) {
/* 4849:3391 */           throw new MismatchedTokenException();
/* 4850:     */         }
/* 4851:3392 */         _t = _t.getNextSibling();
/* 4852:3393 */         _t = __t170;
/* 4853:3394 */         _t = _t.getNextSibling();
/* 4854:3395 */         if (this.inputState.guessing == 0) {
/* 4855:3396 */           out(r);
/* 4856:     */         }
/* 4857:     */         break;
/* 4858:     */       case 140: 
/* 4859:3402 */         i = _t;
/* 4860:3403 */         match(_t, 140);
/* 4861:3404 */         _t = _t.getNextSibling();
/* 4862:3405 */         if (this.inputState.guessing == 0) {
/* 4863:3406 */           out(i);
/* 4864:     */         }
/* 4865:     */         break;
/* 4866:     */       case 78: 
/* 4867:3412 */         j = _t;
/* 4868:3413 */         match(_t, 78);
/* 4869:3414 */         _t = _t.getNextSibling();
/* 4870:3415 */         if (this.inputState.guessing == 0) {
/* 4871:3416 */           out(j);
/* 4872:     */         }
/* 4873:     */         break;
/* 4874:     */       case 150: 
/* 4875:3422 */         v = _t;
/* 4876:3423 */         match(_t, 150);
/* 4877:3424 */         _t = _t.getNextSibling();
/* 4878:3425 */         if (this.inputState.guessing == 0) {
/* 4879:3426 */           out(v);
/* 4880:     */         }
/* 4881:     */         break;
/* 4882:     */       default: 
/* 4883:3432 */         throw new NoViableAltException(_t);
/* 4884:     */       }
/* 4885:     */     }
/* 4886:     */     catch (RecognitionException ex)
/* 4887:     */     {
/* 4888:3437 */       if (this.inputState.guessing == 0)
/* 4889:     */       {
/* 4890:3438 */         reportError(ex);
/* 4891:3439 */         if (_t != null) {
/* 4892:3439 */           _t = _t.getNextSibling();
/* 4893:     */         }
/* 4894:     */       }
/* 4895:     */       else
/* 4896:     */       {
/* 4897:3441 */         throw ex;
/* 4898:     */       }
/* 4899:     */     }
/* 4900:3444 */     this._retTree = _t;
/* 4901:     */   }
/* 4902:     */   
/* 4903:     */   public final void parameter(AST _t)
/* 4904:     */     throws RecognitionException
/* 4905:     */   {
/* 4906:3449 */     AST parameter_AST_in = _t == ASTNULL ? null : _t;
/* 4907:3450 */     AST n = null;
/* 4908:3451 */     AST p = null;
/* 4909:     */     try
/* 4910:     */     {
/* 4911:3454 */       if (_t == null) {
/* 4912:3454 */         _t = ASTNULL;
/* 4913:     */       }
/* 4914:3455 */       switch (_t.getType())
/* 4915:     */       {
/* 4916:     */       case 148: 
/* 4917:3458 */         n = _t;
/* 4918:3459 */         match(_t, 148);
/* 4919:3460 */         _t = _t.getNextSibling();
/* 4920:3461 */         if (this.inputState.guessing == 0) {
/* 4921:3462 */           out(n);
/* 4922:     */         }
/* 4923:     */         break;
/* 4924:     */       case 123: 
/* 4925:3468 */         p = _t;
/* 4926:3469 */         match(_t, 123);
/* 4927:3470 */         _t = _t.getNextSibling();
/* 4928:3471 */         if (this.inputState.guessing == 0) {
/* 4929:3472 */           out(p);
/* 4930:     */         }
/* 4931:     */         break;
/* 4932:     */       default: 
/* 4933:3478 */         throw new NoViableAltException(_t);
/* 4934:     */       }
/* 4935:     */     }
/* 4936:     */     catch (RecognitionException ex)
/* 4937:     */     {
/* 4938:3483 */       if (this.inputState.guessing == 0)
/* 4939:     */       {
/* 4940:3484 */         reportError(ex);
/* 4941:3485 */         if (_t != null) {
/* 4942:3485 */           _t = _t.getNextSibling();
/* 4943:     */         }
/* 4944:     */       }
/* 4945:     */       else
/* 4946:     */       {
/* 4947:3487 */         throw ex;
/* 4948:     */       }
/* 4949:     */     }
/* 4950:3490 */     this._retTree = _t;
/* 4951:     */   }
/* 4952:     */   
/* 4953:     */   public final void additiveExpr(AST _t)
/* 4954:     */     throws RecognitionException
/* 4955:     */   {
/* 4956:3495 */     AST additiveExpr_AST_in = _t == ASTNULL ? null : _t;
/* 4957:     */     try
/* 4958:     */     {
/* 4959:3498 */       if (_t == null) {
/* 4960:3498 */         _t = ASTNULL;
/* 4961:     */       }
/* 4962:3499 */       switch (_t.getType())
/* 4963:     */       {
/* 4964:     */       case 115: 
/* 4965:3502 */         AST __t133 = _t;
/* 4966:3503 */         AST tmp73_AST_in = _t;
/* 4967:3504 */         match(_t, 115);
/* 4968:3505 */         _t = _t.getFirstChild();
/* 4969:3506 */         expr(_t);
/* 4970:3507 */         _t = this._retTree;
/* 4971:3508 */         if (this.inputState.guessing == 0) {
/* 4972:3509 */           out("+");
/* 4973:     */         }
/* 4974:3511 */         expr(_t);
/* 4975:3512 */         _t = this._retTree;
/* 4976:3513 */         _t = __t133;
/* 4977:3514 */         _t = _t.getNextSibling();
/* 4978:3515 */         break;
/* 4979:     */       case 116: 
/* 4980:3519 */         AST __t134 = _t;
/* 4981:3520 */         AST tmp74_AST_in = _t;
/* 4982:3521 */         match(_t, 116);
/* 4983:3522 */         _t = _t.getFirstChild();
/* 4984:3523 */         expr(_t);
/* 4985:3524 */         _t = this._retTree;
/* 4986:3525 */         if (this.inputState.guessing == 0) {
/* 4987:3526 */           out("-");
/* 4988:     */         }
/* 4989:3528 */         nestedExprAfterMinusDiv(_t);
/* 4990:3529 */         _t = this._retTree;
/* 4991:3530 */         _t = __t134;
/* 4992:3531 */         _t = _t.getNextSibling();
/* 4993:3532 */         break;
/* 4994:     */       default: 
/* 4995:3536 */         throw new NoViableAltException(_t);
/* 4996:     */       }
/* 4997:     */     }
/* 4998:     */     catch (RecognitionException ex)
/* 4999:     */     {
/* 5000:3541 */       if (this.inputState.guessing == 0)
/* 5001:     */       {
/* 5002:3542 */         reportError(ex);
/* 5003:3543 */         if (_t != null) {
/* 5004:3543 */           _t = _t.getNextSibling();
/* 5005:     */         }
/* 5006:     */       }
/* 5007:     */       else
/* 5008:     */       {
/* 5009:3545 */         throw ex;
/* 5010:     */       }
/* 5011:     */     }
/* 5012:3548 */     this._retTree = _t;
/* 5013:     */   }
/* 5014:     */   
/* 5015:     */   public final void multiplicativeExpr(AST _t)
/* 5016:     */     throws RecognitionException
/* 5017:     */   {
/* 5018:3553 */     AST multiplicativeExpr_AST_in = _t == ASTNULL ? null : _t;
/* 5019:     */     try
/* 5020:     */     {
/* 5021:3556 */       if (_t == null) {
/* 5022:3556 */         _t = ASTNULL;
/* 5023:     */       }
/* 5024:3557 */       switch (_t.getType())
/* 5025:     */       {
/* 5026:     */       case 117: 
/* 5027:3560 */         AST __t136 = _t;
/* 5028:3561 */         AST tmp75_AST_in = _t;
/* 5029:3562 */         match(_t, 117);
/* 5030:3563 */         _t = _t.getFirstChild();
/* 5031:3564 */         nestedExpr(_t);
/* 5032:3565 */         _t = this._retTree;
/* 5033:3566 */         if (this.inputState.guessing == 0) {
/* 5034:3567 */           out("*");
/* 5035:     */         }
/* 5036:3569 */         nestedExpr(_t);
/* 5037:3570 */         _t = this._retTree;
/* 5038:3571 */         _t = __t136;
/* 5039:3572 */         _t = _t.getNextSibling();
/* 5040:3573 */         break;
/* 5041:     */       case 118: 
/* 5042:3577 */         AST __t137 = _t;
/* 5043:3578 */         AST tmp76_AST_in = _t;
/* 5044:3579 */         match(_t, 118);
/* 5045:3580 */         _t = _t.getFirstChild();
/* 5046:3581 */         nestedExpr(_t);
/* 5047:3582 */         _t = this._retTree;
/* 5048:3583 */         if (this.inputState.guessing == 0) {
/* 5049:3584 */           out("/");
/* 5050:     */         }
/* 5051:3586 */         nestedExprAfterMinusDiv(_t);
/* 5052:3587 */         _t = this._retTree;
/* 5053:3588 */         _t = __t137;
/* 5054:3589 */         _t = _t.getNextSibling();
/* 5055:3590 */         break;
/* 5056:     */       case 119: 
/* 5057:3594 */         AST __t138 = _t;
/* 5058:3595 */         AST tmp77_AST_in = _t;
/* 5059:3596 */         match(_t, 119);
/* 5060:3597 */         _t = _t.getFirstChild();
/* 5061:3598 */         nestedExpr(_t);
/* 5062:3599 */         _t = this._retTree;
/* 5063:3600 */         if (this.inputState.guessing == 0) {
/* 5064:3601 */           out(" % ");
/* 5065:     */         }
/* 5066:3603 */         nestedExprAfterMinusDiv(_t);
/* 5067:3604 */         _t = this._retTree;
/* 5068:3605 */         _t = __t138;
/* 5069:3606 */         _t = _t.getNextSibling();
/* 5070:3607 */         break;
/* 5071:     */       default: 
/* 5072:3611 */         throw new NoViableAltException(_t);
/* 5073:     */       }
/* 5074:     */     }
/* 5075:     */     catch (RecognitionException ex)
/* 5076:     */     {
/* 5077:3616 */       if (this.inputState.guessing == 0)
/* 5078:     */       {
/* 5079:3617 */         reportError(ex);
/* 5080:3618 */         if (_t != null) {
/* 5081:3618 */           _t = _t.getNextSibling();
/* 5082:     */         }
/* 5083:     */       }
/* 5084:     */       else
/* 5085:     */       {
/* 5086:3620 */         throw ex;
/* 5087:     */       }
/* 5088:     */     }
/* 5089:3623 */     this._retTree = _t;
/* 5090:     */   }
/* 5091:     */   
/* 5092:     */   public final void nestedExprAfterMinusDiv(AST _t)
/* 5093:     */     throws RecognitionException
/* 5094:     */   {
/* 5095:3628 */     AST nestedExprAfterMinusDiv_AST_in = _t == ASTNULL ? null : _t;
/* 5096:     */     try
/* 5097:     */     {
/* 5098:3631 */       boolean synPredMatched144 = false;
/* 5099:3632 */       if (_t == null) {
/* 5100:3632 */         _t = ASTNULL;
/* 5101:     */       }
/* 5102:3633 */       if (_tokenSet_5.member(_t.getType()))
/* 5103:     */       {
/* 5104:3634 */         AST __t144 = _t;
/* 5105:3635 */         synPredMatched144 = true;
/* 5106:3636 */         this.inputState.guessing += 1;
/* 5107:     */         try
/* 5108:     */         {
/* 5109:3639 */           arithmeticExpr(_t);
/* 5110:3640 */           _t = this._retTree;
/* 5111:     */         }
/* 5112:     */         catch (RecognitionException pe)
/* 5113:     */         {
/* 5114:3644 */           synPredMatched144 = false;
/* 5115:     */         }
/* 5116:3646 */         _t = __t144;
/* 5117:3647 */         this.inputState.guessing -= 1;
/* 5118:     */       }
/* 5119:3649 */       if (synPredMatched144)
/* 5120:     */       {
/* 5121:3650 */         if (this.inputState.guessing == 0) {
/* 5122:3651 */           out("(");
/* 5123:     */         }
/* 5124:3653 */         arithmeticExpr(_t);
/* 5125:3654 */         _t = this._retTree;
/* 5126:3655 */         if (this.inputState.guessing == 0) {
/* 5127:3656 */           out(")");
/* 5128:     */         }
/* 5129:     */       }
/* 5130:3659 */       else if (_tokenSet_4.member(_t.getType()))
/* 5131:     */       {
/* 5132:3660 */         expr(_t);
/* 5133:3661 */         _t = this._retTree;
/* 5134:     */       }
/* 5135:     */       else
/* 5136:     */       {
/* 5137:3664 */         throw new NoViableAltException(_t);
/* 5138:     */       }
/* 5139:     */     }
/* 5140:     */     catch (RecognitionException ex)
/* 5141:     */     {
/* 5142:3669 */       if (this.inputState.guessing == 0)
/* 5143:     */       {
/* 5144:3670 */         reportError(ex);
/* 5145:3671 */         if (_t != null) {
/* 5146:3671 */           _t = _t.getNextSibling();
/* 5147:     */         }
/* 5148:     */       }
/* 5149:     */       else
/* 5150:     */       {
/* 5151:3673 */         throw ex;
/* 5152:     */       }
/* 5153:     */     }
/* 5154:3676 */     this._retTree = _t;
/* 5155:     */   }
/* 5156:     */   
/* 5157:     */   public final void caseExpr(AST _t)
/* 5158:     */     throws RecognitionException
/* 5159:     */   {
/* 5160:3681 */     AST caseExpr_AST_in = _t == ASTNULL ? null : _t;
/* 5161:     */     try
/* 5162:     */     {
/* 5163:3684 */       if (_t == null) {
/* 5164:3684 */         _t = ASTNULL;
/* 5165:     */       }
/* 5166:3685 */       switch (_t.getType())
/* 5167:     */       {
/* 5168:     */       case 54: 
/* 5169:3688 */         AST __t146 = _t;
/* 5170:3689 */         AST tmp78_AST_in = _t;
/* 5171:3690 */         match(_t, 54);
/* 5172:3691 */         _t = _t.getFirstChild();
/* 5173:3692 */         if (this.inputState.guessing == 0) {
/* 5174:3693 */           out("case");
/* 5175:     */         }
/* 5176:3696 */         int _cnt149 = 0;
/* 5177:     */         for (;;)
/* 5178:     */         {
/* 5179:3699 */           if (_t == null) {
/* 5180:3699 */             _t = ASTNULL;
/* 5181:     */           }
/* 5182:3700 */           if (_t.getType() == 58)
/* 5183:     */           {
/* 5184:3701 */             AST __t148 = _t;
/* 5185:3702 */             AST tmp79_AST_in = _t;
/* 5186:3703 */             match(_t, 58);
/* 5187:3704 */             _t = _t.getFirstChild();
/* 5188:3705 */             if (this.inputState.guessing == 0) {
/* 5189:3706 */               out(" when ");
/* 5190:     */             }
/* 5191:3708 */             booleanExpr(_t, false);
/* 5192:3709 */             _t = this._retTree;
/* 5193:3710 */             if (this.inputState.guessing == 0) {
/* 5194:3711 */               out(" then ");
/* 5195:     */             }
/* 5196:3713 */             expr(_t);
/* 5197:3714 */             _t = this._retTree;
/* 5198:3715 */             _t = __t148;
/* 5199:3716 */             _t = _t.getNextSibling();
/* 5200:     */           }
/* 5201:     */           else
/* 5202:     */           {
/* 5203:3719 */             if (_cnt149 >= 1) {
/* 5204:     */               break;
/* 5205:     */             }
/* 5206:3719 */             throw new NoViableAltException(_t);
/* 5207:     */           }
/* 5208:3722 */           _cnt149++;
/* 5209:     */         }
/* 5210:3726 */         if (_t == null) {
/* 5211:3726 */           _t = ASTNULL;
/* 5212:     */         }
/* 5213:3727 */         switch (_t.getType())
/* 5214:     */         {
/* 5215:     */         case 56: 
/* 5216:3730 */           AST __t151 = _t;
/* 5217:3731 */           AST tmp80_AST_in = _t;
/* 5218:3732 */           match(_t, 56);
/* 5219:3733 */           _t = _t.getFirstChild();
/* 5220:3734 */           if (this.inputState.guessing == 0) {
/* 5221:3735 */             out(" else ");
/* 5222:     */           }
/* 5223:3737 */           expr(_t);
/* 5224:3738 */           _t = this._retTree;
/* 5225:3739 */           _t = __t151;
/* 5226:3740 */           _t = _t.getNextSibling();
/* 5227:3741 */           break;
/* 5228:     */         case 3: 
/* 5229:     */           break;
/* 5230:     */         default: 
/* 5231:3749 */           throw new NoViableAltException(_t);
/* 5232:     */         }
/* 5233:3753 */         if (this.inputState.guessing == 0) {
/* 5234:3754 */           out(" end");
/* 5235:     */         }
/* 5236:3756 */         _t = __t146;
/* 5237:3757 */         _t = _t.getNextSibling();
/* 5238:3758 */         break;
/* 5239:     */       case 74: 
/* 5240:3762 */         AST __t152 = _t;
/* 5241:3763 */         AST tmp81_AST_in = _t;
/* 5242:3764 */         match(_t, 74);
/* 5243:3765 */         _t = _t.getFirstChild();
/* 5244:3766 */         if (this.inputState.guessing == 0) {
/* 5245:3767 */           out("case ");
/* 5246:     */         }
/* 5247:3769 */         expr(_t);
/* 5248:3770 */         _t = this._retTree;
/* 5249:     */         
/* 5250:3772 */         int _cnt155 = 0;
/* 5251:     */         for (;;)
/* 5252:     */         {
/* 5253:3775 */           if (_t == null) {
/* 5254:3775 */             _t = ASTNULL;
/* 5255:     */           }
/* 5256:3776 */           if (_t.getType() == 58)
/* 5257:     */           {
/* 5258:3777 */             AST __t154 = _t;
/* 5259:3778 */             AST tmp82_AST_in = _t;
/* 5260:3779 */             match(_t, 58);
/* 5261:3780 */             _t = _t.getFirstChild();
/* 5262:3781 */             if (this.inputState.guessing == 0) {
/* 5263:3782 */               out(" when ");
/* 5264:     */             }
/* 5265:3784 */             expr(_t);
/* 5266:3785 */             _t = this._retTree;
/* 5267:3786 */             if (this.inputState.guessing == 0) {
/* 5268:3787 */               out(" then ");
/* 5269:     */             }
/* 5270:3789 */             expr(_t);
/* 5271:3790 */             _t = this._retTree;
/* 5272:3791 */             _t = __t154;
/* 5273:3792 */             _t = _t.getNextSibling();
/* 5274:     */           }
/* 5275:     */           else
/* 5276:     */           {
/* 5277:3795 */             if (_cnt155 >= 1) {
/* 5278:     */               break;
/* 5279:     */             }
/* 5280:3795 */             throw new NoViableAltException(_t);
/* 5281:     */           }
/* 5282:3798 */           _cnt155++;
/* 5283:     */         }
/* 5284:3802 */         if (_t == null) {
/* 5285:3802 */           _t = ASTNULL;
/* 5286:     */         }
/* 5287:3803 */         switch (_t.getType())
/* 5288:     */         {
/* 5289:     */         case 56: 
/* 5290:3806 */           AST __t157 = _t;
/* 5291:3807 */           AST tmp83_AST_in = _t;
/* 5292:3808 */           match(_t, 56);
/* 5293:3809 */           _t = _t.getFirstChild();
/* 5294:3810 */           if (this.inputState.guessing == 0) {
/* 5295:3811 */             out(" else ");
/* 5296:     */           }
/* 5297:3813 */           expr(_t);
/* 5298:3814 */           _t = this._retTree;
/* 5299:3815 */           _t = __t157;
/* 5300:3816 */           _t = _t.getNextSibling();
/* 5301:3817 */           break;
/* 5302:     */         case 3: 
/* 5303:     */           break;
/* 5304:     */         default: 
/* 5305:3825 */           throw new NoViableAltException(_t);
/* 5306:     */         }
/* 5307:3829 */         if (this.inputState.guessing == 0) {
/* 5308:3830 */           out(" end");
/* 5309:     */         }
/* 5310:3832 */         _t = __t152;
/* 5311:3833 */         _t = _t.getNextSibling();
/* 5312:3834 */         break;
/* 5313:     */       default: 
/* 5314:3838 */         throw new NoViableAltException(_t);
/* 5315:     */       }
/* 5316:     */     }
/* 5317:     */     catch (RecognitionException ex)
/* 5318:     */     {
/* 5319:3843 */       if (this.inputState.guessing == 0)
/* 5320:     */       {
/* 5321:3844 */         reportError(ex);
/* 5322:3845 */         if (_t != null) {
/* 5323:3845 */           _t = _t.getNextSibling();
/* 5324:     */         }
/* 5325:     */       }
/* 5326:     */       else
/* 5327:     */       {
/* 5328:3847 */         throw ex;
/* 5329:     */       }
/* 5330:     */     }
/* 5331:3850 */     this._retTree = _t;
/* 5332:     */   }
/* 5333:     */   
/* 5334:     */   public final void nestedExpr(AST _t)
/* 5335:     */     throws RecognitionException
/* 5336:     */   {
/* 5337:3855 */     AST nestedExpr_AST_in = _t == ASTNULL ? null : _t;
/* 5338:     */     try
/* 5339:     */     {
/* 5340:3858 */       boolean synPredMatched141 = false;
/* 5341:3859 */       if (_t == null) {
/* 5342:3859 */         _t = ASTNULL;
/* 5343:     */       }
/* 5344:3860 */       if ((_t.getType() == 115) || (_t.getType() == 116))
/* 5345:     */       {
/* 5346:3861 */         AST __t141 = _t;
/* 5347:3862 */         synPredMatched141 = true;
/* 5348:3863 */         this.inputState.guessing += 1;
/* 5349:     */         try
/* 5350:     */         {
/* 5351:3866 */           additiveExpr(_t);
/* 5352:3867 */           _t = this._retTree;
/* 5353:     */         }
/* 5354:     */         catch (RecognitionException pe)
/* 5355:     */         {
/* 5356:3871 */           synPredMatched141 = false;
/* 5357:     */         }
/* 5358:3873 */         _t = __t141;
/* 5359:3874 */         this.inputState.guessing -= 1;
/* 5360:     */       }
/* 5361:3876 */       if (synPredMatched141)
/* 5362:     */       {
/* 5363:3877 */         if (this.inputState.guessing == 0) {
/* 5364:3878 */           out("(");
/* 5365:     */         }
/* 5366:3880 */         additiveExpr(_t);
/* 5367:3881 */         _t = this._retTree;
/* 5368:3882 */         if (this.inputState.guessing == 0) {
/* 5369:3883 */           out(")");
/* 5370:     */         }
/* 5371:     */       }
/* 5372:3886 */       else if (_tokenSet_4.member(_t.getType()))
/* 5373:     */       {
/* 5374:3887 */         expr(_t);
/* 5375:3888 */         _t = this._retTree;
/* 5376:     */       }
/* 5377:     */       else
/* 5378:     */       {
/* 5379:3891 */         throw new NoViableAltException(_t);
/* 5380:     */       }
/* 5381:     */     }
/* 5382:     */     catch (RecognitionException ex)
/* 5383:     */     {
/* 5384:3896 */       if (this.inputState.guessing == 0)
/* 5385:     */       {
/* 5386:3897 */         reportError(ex);
/* 5387:3898 */         if (_t != null) {
/* 5388:3898 */           _t = _t.getNextSibling();
/* 5389:     */         }
/* 5390:     */       }
/* 5391:     */       else
/* 5392:     */       {
/* 5393:3900 */         throw ex;
/* 5394:     */       }
/* 5395:     */     }
/* 5396:3903 */     this._retTree = _t;
/* 5397:     */   }
/* 5398:     */   
/* 5399:     */   public final void arguments(AST _t)
/* 5400:     */     throws RecognitionException
/* 5401:     */   {
/* 5402:3908 */     AST arguments_AST_in = _t == ASTNULL ? null : _t;
/* 5403:     */     try
/* 5404:     */     {
/* 5405:3911 */       expr(_t);
/* 5406:3912 */       _t = this._retTree;
/* 5407:     */       for (;;)
/* 5408:     */       {
/* 5409:3916 */         if (_t == null) {
/* 5410:3916 */           _t = ASTNULL;
/* 5411:     */         }
/* 5412:3917 */         if (!_tokenSet_4.member(_t.getType())) {
/* 5413:     */           break;
/* 5414:     */         }
/* 5415:3918 */         if (this.inputState.guessing == 0) {
/* 5416:3919 */           commaBetweenParameters(", ");
/* 5417:     */         }
/* 5418:3921 */         expr(_t);
/* 5419:3922 */         _t = this._retTree;
/* 5420:     */       }
/* 5421:     */     }
/* 5422:     */     catch (RecognitionException ex)
/* 5423:     */     {
/* 5424:3932 */       if (this.inputState.guessing == 0)
/* 5425:     */       {
/* 5426:3933 */         reportError(ex);
/* 5427:3934 */         if (_t != null) {
/* 5428:3934 */           _t = _t.getNextSibling();
/* 5429:     */         }
/* 5430:     */       }
/* 5431:     */       else
/* 5432:     */       {
/* 5433:3936 */         throw ex;
/* 5434:     */       }
/* 5435:     */     }
/* 5436:3939 */     this._retTree = _t;
/* 5437:     */   }
/* 5438:     */   
/* 5439:3943 */   public static final String[] _tokenNames = { "<0>", "EOF", "<2>", "NULL_TREE_LOOKAHEAD", "\"all\"", "\"any\"", "\"and\"", "\"as\"", "\"asc\"", "\"avg\"", "\"between\"", "\"class\"", "\"count\"", "\"delete\"", "\"desc\"", "DOT", "\"distinct\"", "\"elements\"", "\"escape\"", "\"exists\"", "\"false\"", "\"fetch\"", "\"from\"", "\"full\"", "\"group\"", "\"having\"", "\"in\"", "\"indices\"", "\"inner\"", "\"insert\"", "\"into\"", "\"is\"", "\"join\"", "\"left\"", "\"like\"", "\"max\"", "\"min\"", "\"new\"", "\"not\"", "\"null\"", "\"or\"", "\"order\"", "\"outer\"", "\"properties\"", "\"right\"", "\"select\"", "\"set\"", "\"some\"", "\"sum\"", "\"true\"", "\"union\"", "\"update\"", "\"versioned\"", "\"where\"", "\"case\"", "\"end\"", "\"else\"", "\"then\"", "\"when\"", "\"on\"", "\"with\"", "\"both\"", "\"empty\"", "\"leading\"", "\"member\"", "\"object\"", "\"of\"", "\"trailing\"", "KEY", "VALUE", "ENTRY", "AGGREGATE", "ALIAS", "CONSTRUCTOR", "CASE2", "EXPR_LIST", "FILTER_ENTITY", "IN_LIST", "INDEX_OP", "IS_NOT_NULL", "IS_NULL", "METHOD_CALL", "NOT_BETWEEN", "NOT_IN", "NOT_LIKE", "ORDER_ELEMENT", "QUERY", "RANGE", "ROW_STAR", "SELECT_FROM", "UNARY_MINUS", "UNARY_PLUS", "VECTOR_EXPR", "WEIRD_IDENT", "CONSTANT", "NUM_DOUBLE", "NUM_FLOAT", "NUM_LONG", "NUM_BIG_INTEGER", "NUM_BIG_DECIMAL", "JAVA_CONSTANT", "COMMA", "EQ", "OPEN", "CLOSE", "\"by\"", "\"ascending\"", "\"descending\"", "NE", "SQL_NE", "LT", "GT", "LE", "GE", "CONCAT", "PLUS", "MINUS", "STAR", "DIV", "MOD", "OPEN_BRACKET", "CLOSE_BRACKET", "COLON", "PARAM", "NUM_INT", "QUOTED_STRING", "IDENT", "ID_START_LETTER", "ID_LETTER", "ESCqs", "WS", "HEX_DIGIT", "EXPONENT", "FLOAT_SUFFIX", "FROM_FRAGMENT", "IMPLIED_FROM", "JOIN_FRAGMENT", "SELECT_CLAUSE", "LEFT_OUTER", "RIGHT_OUTER", "ALIAS_REF", "PROPERTY_REF", "SQL_TOKEN", "SELECT_COLUMNS", "SELECT_EXPR", "THETA_JOINS", "FILTERS", "METHOD_NAME", "NAMED_PARAM", "BOGUS", "RESULT_VARIABLE_REF", "SQL_NODE" };
/* 5440:     */   
/* 5441:     */   private static final long[] mk_tokenSet_0()
/* 5442:     */   {
/* 5443:4099 */     long[] data = { 18612532836077568L, 8716717215208048368L, 8474624L, 0L, 0L, 0L };
/* 5444:4100 */     return data;
/* 5445:     */   }
/* 5446:     */   
/* 5447:4102 */   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
/* 5448:     */   
/* 5449:     */   private static final long[] mk_tokenSet_1()
/* 5450:     */   {
/* 5451:4104 */     long[] data = { 17247503360L, 1073398228549632L, 0L, 0L };
/* 5452:4105 */     return data;
/* 5453:     */   }
/* 5454:     */   
/* 5455:4107 */   public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
/* 5456:     */   
/* 5457:     */   private static final long[] mk_tokenSet_2()
/* 5458:     */   {
/* 5459:4109 */     long[] data = { 1391637038144L, 1073398228549632L, 16384L, 0L, 0L, 0L };
/* 5460:4110 */     return data;
/* 5461:     */   }
/* 5462:     */   
/* 5463:4112 */   public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
/* 5464:     */   
/* 5465:     */   private static final long[] mk_tokenSet_3()
/* 5466:     */   {
/* 5467:4114 */     long[] data = { 18577898219802624L, 8716717215476499584L, 5263360L, 0L, 0L, 0L };
/* 5468:4115 */     return data;
/* 5469:     */   }
/* 5470:     */   
/* 5471:4117 */   public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
/* 5472:     */   
/* 5473:     */   private static final long[] mk_tokenSet_4()
/* 5474:     */   {
/* 5475:4119 */     long[] data = { 18753820080246832L, 8716717215476499584L, 5263360L, 0L, 0L, 0L };
/* 5476:4120 */     return data;
/* 5477:     */   }
/* 5478:     */   
/* 5479:4122 */   public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
/* 5480:     */   
/* 5481:     */   private static final long[] mk_tokenSet_5()
/* 5482:     */   {
/* 5483:4124 */     long[] data = { 18014398509481984L, 69805794291352576L, 0L, 0L };
/* 5484:4125 */     return data;
/* 5485:     */   }
/* 5486:     */   
/* 5487:4127 */   public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
/* 5488:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.antlr.SqlGeneratorBase
 * JD-Core Version:    0.7.0.1
 */