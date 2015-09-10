/*    1:     */ package java_cup.runtime;
/*    2:     */ 
/*    3:     */ import java.io.PrintStream;
/*    4:     */ import java.util.Stack;
/*    5:     */ import java.util.Vector;
/*    6:     */ 
/*    7:     */ public abstract class lr_parser
/*    8:     */ {
/*    9:     */   protected static final int _error_sync_size = 3;
/*   10:     */   
/*   11:     */   public lr_parser(Scanner paramScanner)
/*   12:     */   {
/*   13: 129 */     this();
/*   14: 130 */     setScanner(paramScanner);
/*   15:     */   }
/*   16:     */   
/*   17:     */   protected int error_sync_size()
/*   18:     */   {
/*   19: 147 */     return 3;
/*   20:     */   }
/*   21:     */   
/*   22: 222 */   protected boolean _done_parsing = false;
/*   23:     */   protected int tos;
/*   24:     */   protected Symbol cur_token;
/*   25:     */   
/*   26:     */   public void done_parsing()
/*   27:     */   {
/*   28: 232 */     this._done_parsing = true;
/*   29:     */   }
/*   30:     */   
/*   31: 251 */   protected Stack stack = new Stack();
/*   32:     */   protected short[][] production_tab;
/*   33:     */   protected short[][] action_tab;
/*   34:     */   protected short[][] reduce_tab;
/*   35:     */   private Scanner _scanner;
/*   36:     */   protected Symbol[] lookahead;
/*   37:     */   protected int lookahead_pos;
/*   38:     */   
/*   39:     */   public void setScanner(Scanner paramScanner)
/*   40:     */   {
/*   41: 278 */     this._scanner = paramScanner;
/*   42:     */   }
/*   43:     */   
/*   44:     */   public Scanner getScanner()
/*   45:     */   {
/*   46: 283 */     return this._scanner;
/*   47:     */   }
/*   48:     */   
/*   49:     */   public Symbol scan()
/*   50:     */     throws Exception
/*   51:     */   {
/*   52: 335 */     Symbol localSymbol = getScanner().next_token();
/*   53: 336 */     return localSymbol != null ? localSymbol : new Symbol(EOF_sym());
/*   54:     */   }
/*   55:     */   
/*   56:     */   public void report_fatal_error(String paramString, Object paramObject)
/*   57:     */     throws Exception
/*   58:     */   {
/*   59: 355 */     done_parsing();
/*   60:     */     
/*   61:     */ 
/*   62: 358 */     report_error(paramString, paramObject);
/*   63:     */     
/*   64:     */ 
/*   65: 361 */     throw new Exception("Can't recover from previous error(s)");
/*   66:     */   }
/*   67:     */   
/*   68:     */   public void report_error(String paramString, Object paramObject)
/*   69:     */   {
/*   70: 377 */     System.err.print(paramString);
/*   71: 378 */     if ((paramObject instanceof Symbol))
/*   72:     */     {
/*   73: 379 */       if (((Symbol)paramObject).left != -1) {
/*   74: 380 */         System.err.println(" at character " + ((Symbol)paramObject).left + 
/*   75: 381 */           " of input");
/*   76:     */       } else {
/*   77: 382 */         System.err.println("");
/*   78:     */       }
/*   79:     */     }
/*   80:     */     else {
/*   81: 383 */       System.err.println("");
/*   82:     */     }
/*   83:     */   }
/*   84:     */   
/*   85:     */   public void syntax_error(Symbol paramSymbol)
/*   86:     */   {
/*   87: 396 */     report_error("Syntax error", paramSymbol);
/*   88:     */   }
/*   89:     */   
/*   90:     */   public void unrecovered_syntax_error(Symbol paramSymbol)
/*   91:     */     throws Exception
/*   92:     */   {
/*   93: 409 */     report_fatal_error("Couldn't repair and continue parse", paramSymbol);
/*   94:     */   }
/*   95:     */   
/*   96:     */   protected final short get_action(int paramInt1, int paramInt2)
/*   97:     */   {
/*   98: 428 */     short[] arrayOfShort = this.action_tab[paramInt1];
/*   99:     */     int m;
/*  100: 431 */     if (arrayOfShort.length < 20)
/*  101:     */     {
/*  102: 432 */       for (m = 0; m < arrayOfShort.length; m++)
/*  103:     */       {
/*  104: 435 */         int i = arrayOfShort[(m++)];
/*  105: 436 */         if ((i == paramInt2) || (i == -1)) {
/*  106: 439 */           return arrayOfShort[m];
/*  107:     */         }
/*  108:     */       }
/*  109:     */     }
/*  110:     */     else
/*  111:     */     {
/*  112: 445 */       int j = 0;
/*  113: 446 */       int k = (arrayOfShort.length - 1) / 2 - 1;
/*  114: 447 */       while (j <= k)
/*  115:     */       {
/*  116: 449 */         m = (j + k) / 2;
/*  117: 450 */         if (paramInt2 == arrayOfShort[(m * 2)]) {
/*  118: 451 */           return arrayOfShort[(m * 2 + 1)];
/*  119:     */         }
/*  120: 452 */         if (paramInt2 > arrayOfShort[(m * 2)]) {
/*  121: 453 */           j = m + 1;
/*  122:     */         } else {
/*  123: 455 */           k = m - 1;
/*  124:     */         }
/*  125:     */       }
/*  126: 459 */       return arrayOfShort[(arrayOfShort.length - 1)];
/*  127:     */     }
/*  128: 464 */     return 0;
/*  129:     */   }
/*  130:     */   
/*  131:     */   protected final short get_reduce(int paramInt1, int paramInt2)
/*  132:     */   {
/*  133: 482 */     short[] arrayOfShort = this.reduce_tab[paramInt1];
/*  134: 485 */     if (arrayOfShort == null) {
/*  135: 486 */       return -1;
/*  136:     */     }
/*  137: 488 */     for (int j = 0; j < arrayOfShort.length; j++)
/*  138:     */     {
/*  139: 491 */       int i = arrayOfShort[(j++)];
/*  140: 492 */       if ((i == paramInt2) || (i == -1)) {
/*  141: 495 */         return arrayOfShort[j];
/*  142:     */       }
/*  143:     */     }
/*  144: 499 */     return -1;
/*  145:     */   }
/*  146:     */   
/*  147:     */   public Symbol parse()
/*  148:     */     throws Exception
/*  149:     */   {
/*  150: 516 */     Symbol localSymbol = null;
/*  151:     */     
/*  152:     */ 
/*  153:     */ 
/*  154:     */ 
/*  155:     */ 
/*  156:     */ 
/*  157: 523 */     this.production_tab = production_table();
/*  158: 524 */     this.action_tab = action_table();
/*  159: 525 */     this.reduce_tab = reduce_table();
/*  160:     */     
/*  161:     */ 
/*  162: 528 */     init_actions();
/*  163:     */     
/*  164:     */ 
/*  165: 531 */     user_init();
/*  166:     */     
/*  167:     */ 
/*  168: 534 */     this.cur_token = scan();
/*  169:     */     
/*  170:     */ 
/*  171: 537 */     this.stack.removeAllElements();
/*  172: 538 */     this.stack.push(new Symbol(0, start_state()));
/*  173: 539 */     this.tos = 0;
/*  174: 542 */     for (this._done_parsing = false; !this._done_parsing;)
/*  175:     */     {
/*  176: 545 */       if (this.cur_token.used_by_parser) {
/*  177: 546 */         throw new Error("Symbol recycling detected (fix your scanner).");
/*  178:     */       }
/*  179: 551 */       int i = get_action(((Symbol)this.stack.peek()).parse_state, this.cur_token.sym);
/*  180: 554 */       if (i > 0)
/*  181:     */       {
/*  182: 557 */         this.cur_token.parse_state = (i - 1);
/*  183: 558 */         this.cur_token.used_by_parser = true;
/*  184: 559 */         this.stack.push(this.cur_token);
/*  185: 560 */         this.tos += 1;
/*  186:     */         
/*  187:     */ 
/*  188: 563 */         this.cur_token = scan();
/*  189:     */       }
/*  190: 566 */       else if (i < 0)
/*  191:     */       {
/*  192: 569 */         localSymbol = do_action(-i - 1, this, this.stack, this.tos);
/*  193:     */         
/*  194:     */ 
/*  195: 572 */         int k = this.production_tab[(-i - 1)][0];
/*  196: 573 */         int j = this.production_tab[(-i - 1)][1];
/*  197: 576 */         for (int m = 0; m < j; m++)
/*  198:     */         {
/*  199: 578 */           this.stack.pop();
/*  200: 579 */           this.tos -= 1;
/*  201:     */         }
/*  202: 583 */         i = get_reduce(((Symbol)this.stack.peek()).parse_state, k);
/*  203:     */         
/*  204:     */ 
/*  205: 586 */         localSymbol.parse_state = i;
/*  206: 587 */         localSymbol.used_by_parser = true;
/*  207: 588 */         this.stack.push(localSymbol);
/*  208: 589 */         this.tos += 1;
/*  209:     */       }
/*  210: 592 */       else if (i == 0)
/*  211:     */       {
/*  212: 595 */         syntax_error(this.cur_token);
/*  213: 598 */         if (!error_recovery(false))
/*  214:     */         {
/*  215: 601 */           unrecovered_syntax_error(this.cur_token);
/*  216:     */           
/*  217:     */ 
/*  218: 604 */           done_parsing();
/*  219:     */         }
/*  220:     */         else
/*  221:     */         {
/*  222: 606 */           localSymbol = (Symbol)this.stack.peek();
/*  223:     */         }
/*  224:     */       }
/*  225:     */     }
/*  226: 610 */     return localSymbol;
/*  227:     */   }
/*  228:     */   
/*  229:     */   public void debug_message(String paramString)
/*  230:     */   {
/*  231: 622 */     System.err.println(paramString);
/*  232:     */   }
/*  233:     */   
/*  234:     */   public void dump_stack()
/*  235:     */   {
/*  236: 630 */     if (this.stack == null)
/*  237:     */     {
/*  238: 632 */       debug_message("# Stack dump requested, but stack is null");
/*  239: 633 */       return;
/*  240:     */     }
/*  241: 636 */     debug_message("============ Parse Stack Dump ============");
/*  242: 639 */     for (int i = 0; i < this.stack.size(); i++) {
/*  243: 641 */       debug_message("Symbol: " + ((Symbol)this.stack.elementAt(i)).sym + 
/*  244: 642 */         " State: " + ((Symbol)this.stack.elementAt(i)).parse_state);
/*  245:     */     }
/*  246: 644 */     debug_message("==========================================");
/*  247:     */   }
/*  248:     */   
/*  249:     */   public void debug_reduce(int paramInt1, int paramInt2, int paramInt3)
/*  250:     */   {
/*  251: 657 */     debug_message("# Reduce with prod #" + paramInt1 + " [NT=" + paramInt2 + 
/*  252: 658 */       ", " + "SZ=" + paramInt3 + "]");
/*  253:     */   }
/*  254:     */   
/*  255:     */   public void debug_shift(Symbol paramSymbol)
/*  256:     */   {
/*  257: 669 */     debug_message("# Shift under term #" + paramSymbol.sym + 
/*  258: 670 */       " to state #" + paramSymbol.parse_state);
/*  259:     */   }
/*  260:     */   
/*  261:     */   public void debug_stack()
/*  262:     */   {
/*  263: 678 */     StringBuffer localStringBuffer = new StringBuffer("## STACK:");
/*  264: 679 */     for (int i = 0; i < this.stack.size(); i++)
/*  265:     */     {
/*  266: 680 */       Symbol localSymbol = (Symbol)this.stack.elementAt(i);
/*  267: 681 */       localStringBuffer.append(" <state " + localSymbol.parse_state + ", sym " + localSymbol.sym + ">");
/*  268: 682 */       if ((i % 3 == 2) || (i == this.stack.size() - 1))
/*  269:     */       {
/*  270: 683 */         debug_message(localStringBuffer.toString());
/*  271: 684 */         localStringBuffer = new StringBuffer("         ");
/*  272:     */       }
/*  273:     */     }
/*  274:     */   }
/*  275:     */   
/*  276:     */   public Symbol debug_parse()
/*  277:     */     throws Exception
/*  278:     */   {
/*  279: 703 */     Symbol localSymbol = null;
/*  280:     */     
/*  281:     */ 
/*  282:     */ 
/*  283:     */ 
/*  284:     */ 
/*  285: 709 */     this.production_tab = production_table();
/*  286: 710 */     this.action_tab = action_table();
/*  287: 711 */     this.reduce_tab = reduce_table();
/*  288:     */     
/*  289: 713 */     debug_message("# Initializing parser");
/*  290:     */     
/*  291:     */ 
/*  292: 716 */     init_actions();
/*  293:     */     
/*  294:     */ 
/*  295: 719 */     user_init();
/*  296:     */     
/*  297:     */ 
/*  298: 722 */     this.cur_token = scan();
/*  299:     */     
/*  300: 724 */     debug_message("# Current Symbol is #" + this.cur_token.sym);
/*  301:     */     
/*  302:     */ 
/*  303: 727 */     this.stack.removeAllElements();
/*  304: 728 */     this.stack.push(new Symbol(0, start_state()));
/*  305: 729 */     this.tos = 0;
/*  306: 732 */     for (this._done_parsing = false; !this._done_parsing;)
/*  307:     */     {
/*  308: 735 */       if (this.cur_token.used_by_parser) {
/*  309: 736 */         throw new Error("Symbol recycling detected (fix your scanner).");
/*  310:     */       }
/*  311: 742 */       int i = get_action(((Symbol)this.stack.peek()).parse_state, this.cur_token.sym);
/*  312: 745 */       if (i > 0)
/*  313:     */       {
/*  314: 748 */         this.cur_token.parse_state = (i - 1);
/*  315: 749 */         this.cur_token.used_by_parser = true;
/*  316: 750 */         debug_shift(this.cur_token);
/*  317: 751 */         this.stack.push(this.cur_token);
/*  318: 752 */         this.tos += 1;
/*  319:     */         
/*  320:     */ 
/*  321: 755 */         this.cur_token = scan();
/*  322: 756 */         debug_message("# Current token is " + this.cur_token);
/*  323:     */       }
/*  324: 759 */       else if (i < 0)
/*  325:     */       {
/*  326: 762 */         localSymbol = do_action(-i - 1, this, this.stack, this.tos);
/*  327:     */         
/*  328:     */ 
/*  329: 765 */         int k = this.production_tab[(-i - 1)][0];
/*  330: 766 */         int j = this.production_tab[(-i - 1)][1];
/*  331:     */         
/*  332: 768 */         debug_reduce(-i - 1, k, j);
/*  333: 771 */         for (int m = 0; m < j; m++)
/*  334:     */         {
/*  335: 773 */           this.stack.pop();
/*  336: 774 */           this.tos -= 1;
/*  337:     */         }
/*  338: 778 */         i = get_reduce(((Symbol)this.stack.peek()).parse_state, k);
/*  339: 779 */         debug_message("# Reduce rule: top state " + 
/*  340: 780 */           ((Symbol)this.stack.peek()).parse_state + 
/*  341: 781 */           ", lhs sym " + k + " -> state " + i);
/*  342:     */         
/*  343:     */ 
/*  344: 784 */         localSymbol.parse_state = i;
/*  345: 785 */         localSymbol.used_by_parser = true;
/*  346: 786 */         this.stack.push(localSymbol);
/*  347: 787 */         this.tos += 1;
/*  348:     */         
/*  349: 789 */         debug_message("# Goto state #" + i);
/*  350:     */       }
/*  351: 792 */       else if (i == 0)
/*  352:     */       {
/*  353: 795 */         syntax_error(this.cur_token);
/*  354: 798 */         if (!error_recovery(true))
/*  355:     */         {
/*  356: 801 */           unrecovered_syntax_error(this.cur_token);
/*  357:     */           
/*  358:     */ 
/*  359: 804 */           done_parsing();
/*  360:     */         }
/*  361:     */         else
/*  362:     */         {
/*  363: 806 */           localSymbol = (Symbol)this.stack.peek();
/*  364:     */         }
/*  365:     */       }
/*  366:     */     }
/*  367: 810 */     return localSymbol;
/*  368:     */   }
/*  369:     */   
/*  370:     */   protected boolean error_recovery(boolean paramBoolean)
/*  371:     */     throws Exception
/*  372:     */   {
/*  373: 842 */     if (paramBoolean) {
/*  374: 842 */       debug_message("# Attempting error recovery");
/*  375:     */     }
/*  376: 846 */     if (!find_recovery_config(paramBoolean))
/*  377:     */     {
/*  378: 848 */       if (paramBoolean) {
/*  379: 848 */         debug_message("# Error recovery fails");
/*  380:     */       }
/*  381: 849 */       return false;
/*  382:     */     }
/*  383: 853 */     read_lookahead();
/*  384:     */     for (;;)
/*  385:     */     {
/*  386: 859 */       if (paramBoolean) {
/*  387: 859 */         debug_message("# Trying to parse ahead");
/*  388:     */       }
/*  389: 860 */       if (try_parse_ahead(paramBoolean)) {
/*  390:     */         break;
/*  391:     */       }
/*  392: 866 */       if (this.lookahead[0].sym == EOF_sym())
/*  393:     */       {
/*  394: 868 */         if (paramBoolean) {
/*  395: 868 */           debug_message("# Error recovery fails at EOF");
/*  396:     */         }
/*  397: 869 */         return false;
/*  398:     */       }
/*  399: 878 */       if (paramBoolean) {
/*  400: 879 */         debug_message("# Consuming Symbol #" + this.lookahead[0].sym);
/*  401:     */       }
/*  402: 880 */       restart_lookahead();
/*  403:     */     }
/*  404: 884 */     if (paramBoolean) {
/*  405: 884 */       debug_message("# Parse-ahead ok, going back to normal parse");
/*  406:     */     }
/*  407: 887 */     parse_lookahead(paramBoolean);
/*  408:     */     
/*  409:     */ 
/*  410: 890 */     return true;
/*  411:     */   }
/*  412:     */   
/*  413:     */   protected boolean shift_under_error()
/*  414:     */   {
/*  415: 901 */     return get_action(((Symbol)this.stack.peek()).parse_state, error_sym()) > 0;
/*  416:     */   }
/*  417:     */   
/*  418:     */   protected boolean find_recovery_config(boolean paramBoolean)
/*  419:     */   {
/*  420: 918 */     if (paramBoolean) {
/*  421: 918 */       debug_message("# Finding recovery state on stack");
/*  422:     */     }
/*  423: 921 */     int j = ((Symbol)this.stack.peek()).right;
/*  424: 922 */     int k = ((Symbol)this.stack.peek()).left;
/*  425: 925 */     while (!shift_under_error())
/*  426:     */     {
/*  427: 928 */       if (paramBoolean) {
/*  428: 929 */         debug_message("# Pop stack by one, state was # " + 
/*  429: 930 */           ((Symbol)this.stack.peek()).parse_state);
/*  430:     */       }
/*  431: 931 */       k = ((Symbol)this.stack.pop()).left;
/*  432: 932 */       this.tos -= 1;
/*  433: 935 */       if (this.stack.empty())
/*  434:     */       {
/*  435: 937 */         if (paramBoolean) {
/*  436: 937 */           debug_message("# No recovery state found on stack");
/*  437:     */         }
/*  438: 938 */         return false;
/*  439:     */       }
/*  440:     */     }
/*  441: 943 */     int i = get_action(((Symbol)this.stack.peek()).parse_state, error_sym());
/*  442: 944 */     if (paramBoolean)
/*  443:     */     {
/*  444: 946 */       debug_message("# Recover state found (#" + 
/*  445: 947 */         ((Symbol)this.stack.peek()).parse_state + ")");
/*  446: 948 */       debug_message("# Shifting on error to state #" + (i - 1));
/*  447:     */     }
/*  448: 952 */     Symbol localSymbol = new Symbol(error_sym(), k, j);
/*  449: 953 */     localSymbol.parse_state = (i - 1);
/*  450: 954 */     localSymbol.used_by_parser = true;
/*  451: 955 */     this.stack.push(localSymbol);
/*  452: 956 */     this.tos += 1;
/*  453:     */     
/*  454: 958 */     return true;
/*  455:     */   }
/*  456:     */   
/*  457:     */   protected void read_lookahead()
/*  458:     */     throws Exception
/*  459:     */   {
/*  460: 977 */     this.lookahead = new Symbol[error_sync_size()];
/*  461: 980 */     for (int i = 0; i < error_sync_size(); i++)
/*  462:     */     {
/*  463: 982 */       this.lookahead[i] = this.cur_token;
/*  464: 983 */       this.cur_token = scan();
/*  465:     */     }
/*  466: 987 */     this.lookahead_pos = 0;
/*  467:     */   }
/*  468:     */   
/*  469:     */   protected Symbol cur_err_token()
/*  470:     */   {
/*  471: 993 */     return this.lookahead[this.lookahead_pos];
/*  472:     */   }
/*  473:     */   
/*  474:     */   protected boolean advance_lookahead()
/*  475:     */   {
/*  476:1003 */     this.lookahead_pos += 1;
/*  477:     */     
/*  478:     */ 
/*  479:1006 */     return this.lookahead_pos < error_sync_size();
/*  480:     */   }
/*  481:     */   
/*  482:     */   protected void restart_lookahead()
/*  483:     */     throws Exception
/*  484:     */   {
/*  485:1017 */     for (int i = 1; i < error_sync_size(); i++) {
/*  486:1018 */       this.lookahead[(i - 1)] = this.lookahead[i];
/*  487:     */     }
/*  488:1025 */     this.lookahead[(error_sync_size() - 1)] = this.cur_token;
/*  489:1026 */     this.cur_token = scan();
/*  490:     */     
/*  491:     */ 
/*  492:1029 */     this.lookahead_pos = 0;
/*  493:     */   }
/*  494:     */   
/*  495:     */   protected boolean try_parse_ahead(boolean paramBoolean)
/*  496:     */     throws Exception
/*  497:     */   {
/*  498:1050 */     virtual_parse_stack localvirtual_parse_stack = new virtual_parse_stack(this.stack);
/*  499:     */     for (;;)
/*  500:     */     {
/*  501:1056 */       int i = get_action(localvirtual_parse_stack.top(), cur_err_token().sym);
/*  502:1059 */       if (i == 0) {
/*  503:1059 */         return false;
/*  504:     */       }
/*  505:1062 */       if (i > 0)
/*  506:     */       {
/*  507:1065 */         localvirtual_parse_stack.push(i - 1);
/*  508:1067 */         if (paramBoolean) {
/*  509:1067 */           debug_message("# Parse-ahead shifts Symbol #" + 
/*  510:1068 */             cur_err_token().sym + " into state #" + (i - 1));
/*  511:     */         }
/*  512:1071 */         if (!advance_lookahead()) {
/*  513:1071 */           return true;
/*  514:     */         }
/*  515:     */       }
/*  516:     */       else
/*  517:     */       {
/*  518:1077 */         if (-i - 1 == start_production())
/*  519:     */         {
/*  520:1079 */           if (paramBoolean) {
/*  521:1079 */             debug_message("# Parse-ahead accepts");
/*  522:     */           }
/*  523:1080 */           return true;
/*  524:     */         }
/*  525:1084 */         int j = this.production_tab[(-i - 1)][0];
/*  526:1085 */         int k = this.production_tab[(-i - 1)][1];
/*  527:1088 */         for (int m = 0; m < k; m++) {
/*  528:1089 */           localvirtual_parse_stack.pop();
/*  529:     */         }
/*  530:1091 */         if (paramBoolean) {
/*  531:1092 */           debug_message("# Parse-ahead reduces: handle size = " + 
/*  532:1093 */             k + " lhs = #" + j + " from state #" + localvirtual_parse_stack.top());
/*  533:     */         }
/*  534:1096 */         localvirtual_parse_stack.push(get_reduce(localvirtual_parse_stack.top(), j));
/*  535:1097 */         if (paramBoolean) {
/*  536:1098 */           debug_message("# Goto state #" + localvirtual_parse_stack.top());
/*  537:     */         }
/*  538:     */       }
/*  539:     */     }
/*  540:     */   }
/*  541:     */   
/*  542:     */   protected void parse_lookahead(boolean paramBoolean)
/*  543:     */     throws Exception
/*  544:     */   {
/*  545:1121 */     Symbol localSymbol = null;
/*  546:     */     
/*  547:     */ 
/*  548:     */ 
/*  549:     */ 
/*  550:     */ 
/*  551:1127 */     this.lookahead_pos = 0;
/*  552:1129 */     if (paramBoolean)
/*  553:     */     {
/*  554:1131 */       debug_message("# Reparsing saved input with actions");
/*  555:1132 */       debug_message("# Current Symbol is #" + cur_err_token().sym);
/*  556:1133 */       debug_message("# Current state is #" + 
/*  557:1134 */         ((Symbol)this.stack.peek()).parse_state);
/*  558:     */     }
/*  559:1138 */     while (!this._done_parsing)
/*  560:     */     {
/*  561:1143 */       int i = 
/*  562:1144 */         get_action(((Symbol)this.stack.peek()).parse_state, cur_err_token().sym);
/*  563:1147 */       if (i > 0)
/*  564:     */       {
/*  565:1150 */         cur_err_token().parse_state = (i - 1);
/*  566:1151 */         cur_err_token().used_by_parser = true;
/*  567:1152 */         if (paramBoolean) {
/*  568:1152 */           debug_shift(cur_err_token());
/*  569:     */         }
/*  570:1153 */         this.stack.push(cur_err_token());
/*  571:1154 */         this.tos += 1;
/*  572:1157 */         if (!advance_lookahead())
/*  573:     */         {
/*  574:1159 */           if (paramBoolean) {
/*  575:1159 */             debug_message("# Completed reparse");
/*  576:     */           }
/*  577:1168 */           return;
/*  578:     */         }
/*  579:1171 */         if (paramBoolean) {
/*  580:1172 */           debug_message("# Current Symbol is #" + cur_err_token().sym);
/*  581:     */         }
/*  582:     */       }
/*  583:1175 */       else if (i < 0)
/*  584:     */       {
/*  585:1178 */         localSymbol = do_action(-i - 1, this, this.stack, this.tos);
/*  586:     */         
/*  587:     */ 
/*  588:1181 */         int k = this.production_tab[(-i - 1)][0];
/*  589:1182 */         int j = this.production_tab[(-i - 1)][1];
/*  590:1184 */         if (paramBoolean) {
/*  591:1184 */           debug_reduce(-i - 1, k, j);
/*  592:     */         }
/*  593:1187 */         for (int m = 0; m < j; m++)
/*  594:     */         {
/*  595:1189 */           this.stack.pop();
/*  596:1190 */           this.tos -= 1;
/*  597:     */         }
/*  598:1194 */         i = get_reduce(((Symbol)this.stack.peek()).parse_state, k);
/*  599:     */         
/*  600:     */ 
/*  601:1197 */         localSymbol.parse_state = i;
/*  602:1198 */         localSymbol.used_by_parser = true;
/*  603:1199 */         this.stack.push(localSymbol);
/*  604:1200 */         this.tos += 1;
/*  605:1202 */         if (paramBoolean) {
/*  606:1202 */           debug_message("# Goto state #" + i);
/*  607:     */         }
/*  608:     */       }
/*  609:1207 */       else if (i == 0)
/*  610:     */       {
/*  611:1209 */         report_fatal_error("Syntax error", localSymbol); return;
/*  612:     */       }
/*  613:     */     }
/*  614:     */   }
/*  615:     */   
/*  616:     */   protected static short[][] unpackFromStrings(String[] paramArrayOfString)
/*  617:     */   {
/*  618:1223 */     StringBuffer localStringBuffer = new StringBuffer(paramArrayOfString[0]);
/*  619:1224 */     for (int i = 1; i < paramArrayOfString.length; i++) {
/*  620:1225 */       localStringBuffer.append(paramArrayOfString[i]);
/*  621:     */     }
/*  622:1226 */     int j = 0;
/*  623:1227 */     int k = localStringBuffer.charAt(j) << '\020' | localStringBuffer.charAt(j + 1);j += 2;
/*  624:1228 */     short[][] arrayOfShort = new short[k][];
/*  625:1229 */     for (int m = 0; m < k; m++)
/*  626:     */     {
/*  627:1230 */       int n = localStringBuffer.charAt(j) << '\020' | localStringBuffer.charAt(j + 1);j += 2;
/*  628:1231 */       arrayOfShort[m] = new short[n];
/*  629:1232 */       for (int i1 = 0; i1 < n; i1++) {
/*  630:1233 */         arrayOfShort[m][i1] = ((short)(localStringBuffer.charAt(j++) - '\002'));
/*  631:     */       }
/*  632:     */     }
/*  633:1235 */     return arrayOfShort;
/*  634:     */   }
/*  635:     */   
/*  636:     */   public lr_parser() {}
/*  637:     */   
/*  638:     */   public abstract int EOF_sym();
/*  639:     */   
/*  640:     */   public abstract short[][] action_table();
/*  641:     */   
/*  642:     */   public abstract Symbol do_action(int paramInt1, lr_parser paramlr_parser, Stack paramStack, int paramInt2)
/*  643:     */     throws Exception;
/*  644:     */   
/*  645:     */   public abstract int error_sym();
/*  646:     */   
/*  647:     */   protected abstract void init_actions()
/*  648:     */     throws Exception;
/*  649:     */   
/*  650:     */   public abstract short[][] production_table();
/*  651:     */   
/*  652:     */   public abstract short[][] reduce_table();
/*  653:     */   
/*  654:     */   public abstract int start_production();
/*  655:     */   
/*  656:     */   public abstract int start_state();
/*  657:     */   
/*  658:     */   public void user_init()
/*  659:     */     throws Exception
/*  660:     */   {}
/*  661:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     java_cup.runtime.lr_parser
 * JD-Core Version:    0.7.0.1
 */