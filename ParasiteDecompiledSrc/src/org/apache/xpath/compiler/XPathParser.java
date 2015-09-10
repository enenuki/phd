/*    1:     */ package org.apache.xpath.compiler;
/*    2:     */ 
/*    3:     */ import java.io.PrintStream;
/*    4:     */ import javax.xml.transform.ErrorListener;
/*    5:     */ import javax.xml.transform.SourceLocator;
/*    6:     */ import javax.xml.transform.TransformerException;
/*    7:     */ import org.apache.xml.utils.ObjectVector;
/*    8:     */ import org.apache.xml.utils.PrefixResolver;
/*    9:     */ import org.apache.xpath.XPathException;
/*   10:     */ import org.apache.xpath.XPathProcessorException;
/*   11:     */ import org.apache.xpath.domapi.XPathStylesheetDOM3Exception;
/*   12:     */ import org.apache.xpath.objects.XNumber;
/*   13:     */ import org.apache.xpath.objects.XString;
/*   14:     */ import org.apache.xpath.res.XPATHMessages;
/*   15:     */ 
/*   16:     */ public class XPathParser
/*   17:     */ {
/*   18:     */   public static final String CONTINUE_AFTER_FATAL_ERROR = "CONTINUE_AFTER_FATAL_ERROR";
/*   19:     */   private OpMap m_ops;
/*   20:     */   transient String m_token;
/*   21:  61 */   transient char m_tokenChar = '\000';
/*   22:  66 */   int m_queueMark = 0;
/*   23:     */   protected static final int FILTER_MATCH_FAILED = 0;
/*   24:     */   protected static final int FILTER_MATCH_PRIMARY = 1;
/*   25:     */   protected static final int FILTER_MATCH_PREDICATES = 2;
/*   26:     */   PrefixResolver m_namespaceContext;
/*   27:     */   private ErrorListener m_errorListener;
/*   28:     */   SourceLocator m_sourceLocator;
/*   29:     */   private FunctionTable m_functionTable;
/*   30:     */   
/*   31:     */   public XPathParser(ErrorListener errorListener, SourceLocator sourceLocator)
/*   32:     */   {
/*   33:  80 */     this.m_errorListener = errorListener;
/*   34:  81 */     this.m_sourceLocator = sourceLocator;
/*   35:     */   }
/*   36:     */   
/*   37:     */   public void initXPath(Compiler compiler, String expression, PrefixResolver namespaceContext)
/*   38:     */     throws TransformerException
/*   39:     */   {
/*   40: 106 */     this.m_ops = compiler;
/*   41: 107 */     this.m_namespaceContext = namespaceContext;
/*   42: 108 */     this.m_functionTable = compiler.getFunctionTable();
/*   43:     */     
/*   44: 110 */     Lexer lexer = new Lexer(compiler, namespaceContext, this);
/*   45:     */     
/*   46: 112 */     lexer.tokenize(expression);
/*   47:     */     
/*   48: 114 */     this.m_ops.setOp(0, 1);
/*   49: 115 */     this.m_ops.setOp(1, 2);
/*   50:     */     try
/*   51:     */     {
/*   52: 128 */       nextToken();
/*   53: 129 */       Expr();
/*   54: 131 */       if (null != this.m_token)
/*   55:     */       {
/*   56: 133 */         String extraTokens = "";
/*   57: 135 */         while (null != this.m_token)
/*   58:     */         {
/*   59: 137 */           extraTokens = extraTokens + "'" + this.m_token + "'";
/*   60:     */           
/*   61: 139 */           nextToken();
/*   62: 141 */           if (null != this.m_token) {
/*   63: 142 */             extraTokens = extraTokens + ", ";
/*   64:     */           }
/*   65:     */         }
/*   66: 145 */         error("ER_EXTRA_ILLEGAL_TOKENS", new Object[] { extraTokens });
/*   67:     */       }
/*   68:     */     }
/*   69:     */     catch (XPathProcessorException e)
/*   70:     */     {
/*   71: 152 */       if ("CONTINUE_AFTER_FATAL_ERROR".equals(e.getMessage())) {
/*   72: 157 */         initXPath(compiler, "/..", namespaceContext);
/*   73:     */       } else {
/*   74: 160 */         throw e;
/*   75:     */       }
/*   76:     */     }
/*   77: 163 */     compiler.shrink();
/*   78:     */   }
/*   79:     */   
/*   80:     */   public void initMatchPattern(Compiler compiler, String expression, PrefixResolver namespaceContext)
/*   81:     */     throws TransformerException
/*   82:     */   {
/*   83: 182 */     this.m_ops = compiler;
/*   84: 183 */     this.m_namespaceContext = namespaceContext;
/*   85: 184 */     this.m_functionTable = compiler.getFunctionTable();
/*   86:     */     
/*   87: 186 */     Lexer lexer = new Lexer(compiler, namespaceContext, this);
/*   88:     */     
/*   89: 188 */     lexer.tokenize(expression);
/*   90:     */     
/*   91: 190 */     this.m_ops.setOp(0, 30);
/*   92: 191 */     this.m_ops.setOp(1, 2);
/*   93:     */     
/*   94: 193 */     nextToken();
/*   95: 194 */     Pattern();
/*   96: 196 */     if (null != this.m_token)
/*   97:     */     {
/*   98: 198 */       String extraTokens = "";
/*   99: 200 */       while (null != this.m_token)
/*  100:     */       {
/*  101: 202 */         extraTokens = extraTokens + "'" + this.m_token + "'";
/*  102:     */         
/*  103: 204 */         nextToken();
/*  104: 206 */         if (null != this.m_token) {
/*  105: 207 */           extraTokens = extraTokens + ", ";
/*  106:     */         }
/*  107:     */       }
/*  108: 210 */       error("ER_EXTRA_ILLEGAL_TOKENS", new Object[] { extraTokens });
/*  109:     */     }
/*  110: 215 */     this.m_ops.setOp(this.m_ops.getOp(1), -1);
/*  111: 216 */     this.m_ops.setOp(1, this.m_ops.getOp(1) + 1);
/*  112:     */     
/*  113: 218 */     this.m_ops.shrink();
/*  114:     */   }
/*  115:     */   
/*  116:     */   public void setErrorHandler(ErrorListener handler)
/*  117:     */   {
/*  118: 241 */     this.m_errorListener = handler;
/*  119:     */   }
/*  120:     */   
/*  121:     */   public ErrorListener getErrorListener()
/*  122:     */   {
/*  123: 251 */     return this.m_errorListener;
/*  124:     */   }
/*  125:     */   
/*  126:     */   final boolean tokenIs(String s)
/*  127:     */   {
/*  128: 264 */     return s == null ? true : this.m_token != null ? this.m_token.equals(s) : false;
/*  129:     */   }
/*  130:     */   
/*  131:     */   final boolean tokenIs(char c)
/*  132:     */   {
/*  133: 277 */     return this.m_tokenChar == c;
/*  134:     */   }
/*  135:     */   
/*  136:     */   final boolean lookahead(char c, int n)
/*  137:     */   {
/*  138: 293 */     int pos = this.m_queueMark + n;
/*  139:     */     boolean b;
/*  140: 296 */     if ((pos <= this.m_ops.getTokenQueueSize()) && (pos > 0) && (this.m_ops.getTokenQueueSize() != 0))
/*  141:     */     {
/*  142: 299 */       String tok = (String)this.m_ops.m_tokenQueue.elementAt(pos - 1);
/*  143:     */       
/*  144: 301 */       b = tok.charAt(0) == c;
/*  145:     */     }
/*  146:     */     else
/*  147:     */     {
/*  148: 305 */       b = false;
/*  149:     */     }
/*  150: 308 */     return b;
/*  151:     */   }
/*  152:     */   
/*  153:     */   private final boolean lookbehind(char c, int n)
/*  154:     */   {
/*  155: 329 */     int lookBehindPos = this.m_queueMark - (n + 1);
/*  156:     */     boolean isToken;
/*  157: 331 */     if (lookBehindPos >= 0)
/*  158:     */     {
/*  159: 333 */       String lookbehind = (String)this.m_ops.m_tokenQueue.elementAt(lookBehindPos);
/*  160: 335 */       if (lookbehind.length() == 1)
/*  161:     */       {
/*  162: 337 */         char c0 = lookbehind == null ? '|' : lookbehind.charAt(0);
/*  163:     */         
/*  164: 339 */         isToken = c0 != '|';
/*  165:     */       }
/*  166:     */       else
/*  167:     */       {
/*  168: 343 */         isToken = false;
/*  169:     */       }
/*  170:     */     }
/*  171:     */     else
/*  172:     */     {
/*  173: 348 */       isToken = false;
/*  174:     */     }
/*  175: 351 */     return isToken;
/*  176:     */   }
/*  177:     */   
/*  178:     */   private final boolean lookbehindHasToken(int n)
/*  179:     */   {
/*  180:     */     boolean hasToken;
/*  181: 371 */     if (this.m_queueMark - n > 0)
/*  182:     */     {
/*  183: 373 */       String lookbehind = (String)this.m_ops.m_tokenQueue.elementAt(this.m_queueMark - (n - 1));
/*  184: 374 */       char c0 = lookbehind == null ? '|' : lookbehind.charAt(0);
/*  185:     */       
/*  186: 376 */       hasToken = c0 != '|';
/*  187:     */     }
/*  188:     */     else
/*  189:     */     {
/*  190: 380 */       hasToken = false;
/*  191:     */     }
/*  192: 383 */     return hasToken;
/*  193:     */   }
/*  194:     */   
/*  195:     */   private final boolean lookahead(String s, int n)
/*  196:     */   {
/*  197:     */     boolean isToken;
/*  198: 402 */     if (this.m_queueMark + n <= this.m_ops.getTokenQueueSize())
/*  199:     */     {
/*  200: 404 */       String lookahead = (String)this.m_ops.m_tokenQueue.elementAt(this.m_queueMark + (n - 1));
/*  201:     */       
/*  202: 406 */       isToken = s == null ? true : lookahead != null ? lookahead.equals(s) : false;
/*  203:     */     }
/*  204:     */     else
/*  205:     */     {
/*  206: 410 */       isToken = null == s;
/*  207:     */     }
/*  208: 413 */     return isToken;
/*  209:     */   }
/*  210:     */   
/*  211:     */   private final void nextToken()
/*  212:     */   {
/*  213: 423 */     if (this.m_queueMark < this.m_ops.getTokenQueueSize())
/*  214:     */     {
/*  215: 425 */       this.m_token = ((String)this.m_ops.m_tokenQueue.elementAt(this.m_queueMark++));
/*  216: 426 */       this.m_tokenChar = this.m_token.charAt(0);
/*  217:     */     }
/*  218:     */     else
/*  219:     */     {
/*  220: 430 */       this.m_token = null;
/*  221: 431 */       this.m_tokenChar = '\000';
/*  222:     */     }
/*  223:     */   }
/*  224:     */   
/*  225:     */   private final String getTokenRelative(int i)
/*  226:     */   {
/*  227: 447 */     int relative = this.m_queueMark + i;
/*  228:     */     String tok;
/*  229: 449 */     if ((relative > 0) && (relative < this.m_ops.getTokenQueueSize())) {
/*  230: 451 */       tok = (String)this.m_ops.m_tokenQueue.elementAt(relative);
/*  231:     */     } else {
/*  232: 455 */       tok = null;
/*  233:     */     }
/*  234: 458 */     return tok;
/*  235:     */   }
/*  236:     */   
/*  237:     */   private final void prevToken()
/*  238:     */   {
/*  239: 468 */     if (this.m_queueMark > 0)
/*  240:     */     {
/*  241: 470 */       this.m_queueMark -= 1;
/*  242:     */       
/*  243: 472 */       this.m_token = ((String)this.m_ops.m_tokenQueue.elementAt(this.m_queueMark));
/*  244: 473 */       this.m_tokenChar = this.m_token.charAt(0);
/*  245:     */     }
/*  246:     */     else
/*  247:     */     {
/*  248: 477 */       this.m_token = null;
/*  249: 478 */       this.m_tokenChar = '\000';
/*  250:     */     }
/*  251:     */   }
/*  252:     */   
/*  253:     */   private final void consumeExpected(String expected)
/*  254:     */     throws TransformerException
/*  255:     */   {
/*  256: 494 */     if (tokenIs(expected))
/*  257:     */     {
/*  258: 496 */       nextToken();
/*  259:     */     }
/*  260:     */     else
/*  261:     */     {
/*  262: 500 */       error("ER_EXPECTED_BUT_FOUND", new Object[] { expected, this.m_token });
/*  263:     */       
/*  264:     */ 
/*  265:     */ 
/*  266:     */ 
/*  267:     */ 
/*  268: 506 */       throw new XPathProcessorException("CONTINUE_AFTER_FATAL_ERROR");
/*  269:     */     }
/*  270:     */   }
/*  271:     */   
/*  272:     */   private final void consumeExpected(char expected)
/*  273:     */     throws TransformerException
/*  274:     */   {
/*  275: 522 */     if (tokenIs(expected))
/*  276:     */     {
/*  277: 524 */       nextToken();
/*  278:     */     }
/*  279:     */     else
/*  280:     */     {
/*  281: 528 */       error("ER_EXPECTED_BUT_FOUND", new Object[] { String.valueOf(expected), this.m_token });
/*  282:     */       
/*  283:     */ 
/*  284:     */ 
/*  285:     */ 
/*  286:     */ 
/*  287:     */ 
/*  288: 535 */       throw new XPathProcessorException("CONTINUE_AFTER_FATAL_ERROR");
/*  289:     */     }
/*  290:     */   }
/*  291:     */   
/*  292:     */   void warn(String msg, Object[] args)
/*  293:     */     throws TransformerException
/*  294:     */   {
/*  295: 554 */     String fmsg = XPATHMessages.createXPATHWarning(msg, args);
/*  296: 555 */     ErrorListener ehandler = getErrorListener();
/*  297: 557 */     if (null != ehandler) {
/*  298: 560 */       ehandler.warning(new TransformerException(fmsg, this.m_sourceLocator));
/*  299:     */     } else {
/*  300: 565 */       System.err.println(fmsg);
/*  301:     */     }
/*  302:     */   }
/*  303:     */   
/*  304:     */   private void assertion(boolean b, String msg)
/*  305:     */   {
/*  306: 581 */     if (!b)
/*  307:     */     {
/*  308: 583 */       String fMsg = XPATHMessages.createXPATHMessage("ER_INCORRECT_PROGRAMMER_ASSERTION", new Object[] { msg });
/*  309:     */       
/*  310:     */ 
/*  311:     */ 
/*  312: 587 */       throw new RuntimeException(fMsg);
/*  313:     */     }
/*  314:     */   }
/*  315:     */   
/*  316:     */   void error(String msg, Object[] args)
/*  317:     */     throws TransformerException
/*  318:     */   {
/*  319: 607 */     String fmsg = XPATHMessages.createXPATHMessage(msg, args);
/*  320: 608 */     ErrorListener ehandler = getErrorListener();
/*  321:     */     
/*  322: 610 */     TransformerException te = new TransformerException(fmsg, this.m_sourceLocator);
/*  323: 611 */     if (null != ehandler) {
/*  324: 614 */       ehandler.fatalError(te);
/*  325:     */     } else {
/*  326: 619 */       throw te;
/*  327:     */     }
/*  328:     */   }
/*  329:     */   
/*  330:     */   void errorForDOM3(String msg, Object[] args)
/*  331:     */     throws TransformerException
/*  332:     */   {
/*  333: 652 */     String fmsg = XPATHMessages.createXPATHMessage(msg, args);
/*  334: 653 */     ErrorListener ehandler = getErrorListener();
/*  335:     */     
/*  336: 655 */     TransformerException te = new XPathStylesheetDOM3Exception(fmsg, this.m_sourceLocator);
/*  337: 656 */     if (null != ehandler) {
/*  338: 659 */       ehandler.fatalError(te);
/*  339:     */     } else {
/*  340: 664 */       throw te;
/*  341:     */     }
/*  342:     */   }
/*  343:     */   
/*  344:     */   protected String dumpRemainingTokenQueue()
/*  345:     */   {
/*  346: 677 */     int q = this.m_queueMark;
/*  347:     */     String returnMsg;
/*  348: 680 */     if (q < this.m_ops.getTokenQueueSize())
/*  349:     */     {
/*  350: 682 */       String msg = "\n Remaining tokens: (";
/*  351: 684 */       while (q < this.m_ops.getTokenQueueSize())
/*  352:     */       {
/*  353: 686 */         String t = (String)this.m_ops.m_tokenQueue.elementAt(q++);
/*  354:     */         
/*  355: 688 */         msg = msg + " '" + t + "'";
/*  356:     */       }
/*  357: 691 */       returnMsg = msg + ")";
/*  358:     */     }
/*  359:     */     else
/*  360:     */     {
/*  361: 695 */       returnMsg = "";
/*  362:     */     }
/*  363: 698 */     return returnMsg;
/*  364:     */   }
/*  365:     */   
/*  366:     */   final int getFunctionToken(String key)
/*  367:     */   {
/*  368:     */     int tok;
/*  369:     */     try
/*  370:     */     {
/*  371: 721 */       Object id = Keywords.lookupNodeTest(key);
/*  372: 722 */       if (null == id) {
/*  373: 722 */         id = this.m_functionTable.getFunctionID(key);
/*  374:     */       }
/*  375: 723 */       tok = ((Integer)id).intValue();
/*  376:     */     }
/*  377:     */     catch (NullPointerException npe)
/*  378:     */     {
/*  379: 727 */       tok = -1;
/*  380:     */     }
/*  381:     */     catch (ClassCastException cce)
/*  382:     */     {
/*  383: 731 */       tok = -1;
/*  384:     */     }
/*  385: 734 */     return tok;
/*  386:     */   }
/*  387:     */   
/*  388:     */   void insertOp(int pos, int length, int op)
/*  389:     */   {
/*  390: 749 */     int totalLen = this.m_ops.getOp(1);
/*  391: 751 */     for (int i = totalLen - 1; i >= pos; i--) {
/*  392: 753 */       this.m_ops.setOp(i + length, this.m_ops.getOp(i));
/*  393:     */     }
/*  394: 756 */     this.m_ops.setOp(pos, op);
/*  395: 757 */     this.m_ops.setOp(1, totalLen + length);
/*  396:     */   }
/*  397:     */   
/*  398:     */   void appendOp(int length, int op)
/*  399:     */   {
/*  400: 771 */     int totalLen = this.m_ops.getOp(1);
/*  401:     */     
/*  402: 773 */     this.m_ops.setOp(totalLen, op);
/*  403: 774 */     this.m_ops.setOp(totalLen + 1, length);
/*  404: 775 */     this.m_ops.setOp(1, totalLen + length);
/*  405:     */   }
/*  406:     */   
/*  407:     */   protected void Expr()
/*  408:     */     throws TransformerException
/*  409:     */   {
/*  410: 790 */     OrExpr();
/*  411:     */   }
/*  412:     */   
/*  413:     */   protected void OrExpr()
/*  414:     */     throws TransformerException
/*  415:     */   {
/*  416: 805 */     int opPos = this.m_ops.getOp(1);
/*  417:     */     
/*  418: 807 */     AndExpr();
/*  419: 809 */     if ((null != this.m_token) && (tokenIs("or")))
/*  420:     */     {
/*  421: 811 */       nextToken();
/*  422: 812 */       insertOp(opPos, 2, 2);
/*  423: 813 */       OrExpr();
/*  424:     */       
/*  425: 815 */       this.m_ops.setOp(opPos + 1, this.m_ops.getOp(1) - opPos);
/*  426:     */     }
/*  427:     */   }
/*  428:     */   
/*  429:     */   protected void AndExpr()
/*  430:     */     throws TransformerException
/*  431:     */   {
/*  432: 832 */     int opPos = this.m_ops.getOp(1);
/*  433:     */     
/*  434: 834 */     EqualityExpr(-1);
/*  435: 836 */     if ((null != this.m_token) && (tokenIs("and")))
/*  436:     */     {
/*  437: 838 */       nextToken();
/*  438: 839 */       insertOp(opPos, 2, 3);
/*  439: 840 */       AndExpr();
/*  440:     */       
/*  441: 842 */       this.m_ops.setOp(opPos + 1, this.m_ops.getOp(1) - opPos);
/*  442:     */     }
/*  443:     */   }
/*  444:     */   
/*  445:     */   protected int EqualityExpr(int addPos)
/*  446:     */     throws TransformerException
/*  447:     */   {
/*  448: 865 */     int opPos = this.m_ops.getOp(1);
/*  449: 867 */     if (-1 == addPos) {
/*  450: 868 */       addPos = opPos;
/*  451:     */     }
/*  452: 870 */     RelationalExpr(-1);
/*  453: 872 */     if (null != this.m_token) {
/*  454: 874 */       if ((tokenIs('!')) && (lookahead('=', 1)))
/*  455:     */       {
/*  456: 876 */         nextToken();
/*  457: 877 */         nextToken();
/*  458: 878 */         insertOp(addPos, 2, 4);
/*  459:     */         
/*  460: 880 */         int opPlusLeftHandLen = this.m_ops.getOp(1) - addPos;
/*  461:     */         
/*  462: 882 */         addPos = EqualityExpr(addPos);
/*  463: 883 */         this.m_ops.setOp(addPos + 1, this.m_ops.getOp(addPos + opPlusLeftHandLen + 1) + opPlusLeftHandLen);
/*  464:     */         
/*  465: 885 */         addPos += 2;
/*  466:     */       }
/*  467: 887 */       else if (tokenIs('='))
/*  468:     */       {
/*  469: 889 */         nextToken();
/*  470: 890 */         insertOp(addPos, 2, 5);
/*  471:     */         
/*  472: 892 */         int opPlusLeftHandLen = this.m_ops.getOp(1) - addPos;
/*  473:     */         
/*  474: 894 */         addPos = EqualityExpr(addPos);
/*  475: 895 */         this.m_ops.setOp(addPos + 1, this.m_ops.getOp(addPos + opPlusLeftHandLen + 1) + opPlusLeftHandLen);
/*  476:     */         
/*  477: 897 */         addPos += 2;
/*  478:     */       }
/*  479:     */     }
/*  480: 901 */     return addPos;
/*  481:     */   }
/*  482:     */   
/*  483:     */   protected int RelationalExpr(int addPos)
/*  484:     */     throws TransformerException
/*  485:     */   {
/*  486: 925 */     int opPos = this.m_ops.getOp(1);
/*  487: 927 */     if (-1 == addPos) {
/*  488: 928 */       addPos = opPos;
/*  489:     */     }
/*  490: 930 */     AdditiveExpr(-1);
/*  491: 932 */     if (null != this.m_token) {
/*  492: 934 */       if (tokenIs('<'))
/*  493:     */       {
/*  494: 936 */         nextToken();
/*  495: 938 */         if (tokenIs('='))
/*  496:     */         {
/*  497: 940 */           nextToken();
/*  498: 941 */           insertOp(addPos, 2, 6);
/*  499:     */         }
/*  500:     */         else
/*  501:     */         {
/*  502: 945 */           insertOp(addPos, 2, 7);
/*  503:     */         }
/*  504: 948 */         int opPlusLeftHandLen = this.m_ops.getOp(1) - addPos;
/*  505:     */         
/*  506: 950 */         addPos = RelationalExpr(addPos);
/*  507: 951 */         this.m_ops.setOp(addPos + 1, this.m_ops.getOp(addPos + opPlusLeftHandLen + 1) + opPlusLeftHandLen);
/*  508:     */         
/*  509: 953 */         addPos += 2;
/*  510:     */       }
/*  511: 955 */       else if (tokenIs('>'))
/*  512:     */       {
/*  513: 957 */         nextToken();
/*  514: 959 */         if (tokenIs('='))
/*  515:     */         {
/*  516: 961 */           nextToken();
/*  517: 962 */           insertOp(addPos, 2, 8);
/*  518:     */         }
/*  519:     */         else
/*  520:     */         {
/*  521: 966 */           insertOp(addPos, 2, 9);
/*  522:     */         }
/*  523: 969 */         int opPlusLeftHandLen = this.m_ops.getOp(1) - addPos;
/*  524:     */         
/*  525: 971 */         addPos = RelationalExpr(addPos);
/*  526: 972 */         this.m_ops.setOp(addPos + 1, this.m_ops.getOp(addPos + opPlusLeftHandLen + 1) + opPlusLeftHandLen);
/*  527:     */         
/*  528: 974 */         addPos += 2;
/*  529:     */       }
/*  530:     */     }
/*  531: 978 */     return addPos;
/*  532:     */   }
/*  533:     */   
/*  534:     */   protected int AdditiveExpr(int addPos)
/*  535:     */     throws TransformerException
/*  536:     */   {
/*  537:1000 */     int opPos = this.m_ops.getOp(1);
/*  538:1002 */     if (-1 == addPos) {
/*  539:1003 */       addPos = opPos;
/*  540:     */     }
/*  541:1005 */     MultiplicativeExpr(-1);
/*  542:1007 */     if (null != this.m_token) {
/*  543:1009 */       if (tokenIs('+'))
/*  544:     */       {
/*  545:1011 */         nextToken();
/*  546:1012 */         insertOp(addPos, 2, 10);
/*  547:     */         
/*  548:1014 */         int opPlusLeftHandLen = this.m_ops.getOp(1) - addPos;
/*  549:     */         
/*  550:1016 */         addPos = AdditiveExpr(addPos);
/*  551:1017 */         this.m_ops.setOp(addPos + 1, this.m_ops.getOp(addPos + opPlusLeftHandLen + 1) + opPlusLeftHandLen);
/*  552:     */         
/*  553:1019 */         addPos += 2;
/*  554:     */       }
/*  555:1021 */       else if (tokenIs('-'))
/*  556:     */       {
/*  557:1023 */         nextToken();
/*  558:1024 */         insertOp(addPos, 2, 11);
/*  559:     */         
/*  560:1026 */         int opPlusLeftHandLen = this.m_ops.getOp(1) - addPos;
/*  561:     */         
/*  562:1028 */         addPos = AdditiveExpr(addPos);
/*  563:1029 */         this.m_ops.setOp(addPos + 1, this.m_ops.getOp(addPos + opPlusLeftHandLen + 1) + opPlusLeftHandLen);
/*  564:     */         
/*  565:1031 */         addPos += 2;
/*  566:     */       }
/*  567:     */     }
/*  568:1035 */     return addPos;
/*  569:     */   }
/*  570:     */   
/*  571:     */   protected int MultiplicativeExpr(int addPos)
/*  572:     */     throws TransformerException
/*  573:     */   {
/*  574:1058 */     int opPos = this.m_ops.getOp(1);
/*  575:1060 */     if (-1 == addPos) {
/*  576:1061 */       addPos = opPos;
/*  577:     */     }
/*  578:1063 */     UnaryExpr();
/*  579:1065 */     if (null != this.m_token) {
/*  580:1067 */       if (tokenIs('*'))
/*  581:     */       {
/*  582:1069 */         nextToken();
/*  583:1070 */         insertOp(addPos, 2, 12);
/*  584:     */         
/*  585:1072 */         int opPlusLeftHandLen = this.m_ops.getOp(1) - addPos;
/*  586:     */         
/*  587:1074 */         addPos = MultiplicativeExpr(addPos);
/*  588:1075 */         this.m_ops.setOp(addPos + 1, this.m_ops.getOp(addPos + opPlusLeftHandLen + 1) + opPlusLeftHandLen);
/*  589:     */         
/*  590:1077 */         addPos += 2;
/*  591:     */       }
/*  592:1079 */       else if (tokenIs("div"))
/*  593:     */       {
/*  594:1081 */         nextToken();
/*  595:1082 */         insertOp(addPos, 2, 13);
/*  596:     */         
/*  597:1084 */         int opPlusLeftHandLen = this.m_ops.getOp(1) - addPos;
/*  598:     */         
/*  599:1086 */         addPos = MultiplicativeExpr(addPos);
/*  600:1087 */         this.m_ops.setOp(addPos + 1, this.m_ops.getOp(addPos + opPlusLeftHandLen + 1) + opPlusLeftHandLen);
/*  601:     */         
/*  602:1089 */         addPos += 2;
/*  603:     */       }
/*  604:1091 */       else if (tokenIs("mod"))
/*  605:     */       {
/*  606:1093 */         nextToken();
/*  607:1094 */         insertOp(addPos, 2, 14);
/*  608:     */         
/*  609:1096 */         int opPlusLeftHandLen = this.m_ops.getOp(1) - addPos;
/*  610:     */         
/*  611:1098 */         addPos = MultiplicativeExpr(addPos);
/*  612:1099 */         this.m_ops.setOp(addPos + 1, this.m_ops.getOp(addPos + opPlusLeftHandLen + 1) + opPlusLeftHandLen);
/*  613:     */         
/*  614:1101 */         addPos += 2;
/*  615:     */       }
/*  616:1103 */       else if (tokenIs("quo"))
/*  617:     */       {
/*  618:1105 */         nextToken();
/*  619:1106 */         insertOp(addPos, 2, 15);
/*  620:     */         
/*  621:1108 */         int opPlusLeftHandLen = this.m_ops.getOp(1) - addPos;
/*  622:     */         
/*  623:1110 */         addPos = MultiplicativeExpr(addPos);
/*  624:1111 */         this.m_ops.setOp(addPos + 1, this.m_ops.getOp(addPos + opPlusLeftHandLen + 1) + opPlusLeftHandLen);
/*  625:     */         
/*  626:1113 */         addPos += 2;
/*  627:     */       }
/*  628:     */     }
/*  629:1117 */     return addPos;
/*  630:     */   }
/*  631:     */   
/*  632:     */   protected void UnaryExpr()
/*  633:     */     throws TransformerException
/*  634:     */   {
/*  635:1131 */     int opPos = this.m_ops.getOp(1);
/*  636:1132 */     boolean isNeg = false;
/*  637:1134 */     if (this.m_tokenChar == '-')
/*  638:     */     {
/*  639:1136 */       nextToken();
/*  640:1137 */       appendOp(2, 16);
/*  641:     */       
/*  642:1139 */       isNeg = true;
/*  643:     */     }
/*  644:1142 */     UnionExpr();
/*  645:1144 */     if (isNeg) {
/*  646:1145 */       this.m_ops.setOp(opPos + 1, this.m_ops.getOp(1) - opPos);
/*  647:     */     }
/*  648:     */   }
/*  649:     */   
/*  650:     */   protected void StringExpr()
/*  651:     */     throws TransformerException
/*  652:     */   {
/*  653:1159 */     int opPos = this.m_ops.getOp(1);
/*  654:     */     
/*  655:1161 */     appendOp(2, 17);
/*  656:1162 */     Expr();
/*  657:     */     
/*  658:1164 */     this.m_ops.setOp(opPos + 1, this.m_ops.getOp(1) - opPos);
/*  659:     */   }
/*  660:     */   
/*  661:     */   protected void BooleanExpr()
/*  662:     */     throws TransformerException
/*  663:     */   {
/*  664:1179 */     int opPos = this.m_ops.getOp(1);
/*  665:     */     
/*  666:1181 */     appendOp(2, 18);
/*  667:1182 */     Expr();
/*  668:     */     
/*  669:1184 */     int opLen = this.m_ops.getOp(1) - opPos;
/*  670:1186 */     if (opLen == 2) {
/*  671:1188 */       error("ER_BOOLEAN_ARG_NO_LONGER_OPTIONAL", null);
/*  672:     */     }
/*  673:1191 */     this.m_ops.setOp(opPos + 1, opLen);
/*  674:     */   }
/*  675:     */   
/*  676:     */   protected void NumberExpr()
/*  677:     */     throws TransformerException
/*  678:     */   {
/*  679:1205 */     int opPos = this.m_ops.getOp(1);
/*  680:     */     
/*  681:1207 */     appendOp(2, 19);
/*  682:1208 */     Expr();
/*  683:     */     
/*  684:1210 */     this.m_ops.setOp(opPos + 1, this.m_ops.getOp(1) - opPos);
/*  685:     */   }
/*  686:     */   
/*  687:     */   protected void UnionExpr()
/*  688:     */     throws TransformerException
/*  689:     */   {
/*  690:1230 */     int opPos = this.m_ops.getOp(1);
/*  691:1231 */     boolean continueOrLoop = true;
/*  692:1232 */     boolean foundUnion = false;
/*  693:     */     do
/*  694:     */     {
/*  695:1236 */       PathExpr();
/*  696:1238 */       if (!tokenIs('|')) {
/*  697:     */         break;
/*  698:     */       }
/*  699:1240 */       if (false == foundUnion)
/*  700:     */       {
/*  701:1242 */         foundUnion = true;
/*  702:     */         
/*  703:1244 */         insertOp(opPos, 2, 20);
/*  704:     */       }
/*  705:1247 */       nextToken();
/*  706:1256 */     } while (continueOrLoop);
/*  707:1258 */     this.m_ops.setOp(opPos + 1, this.m_ops.getOp(1) - opPos);
/*  708:     */   }
/*  709:     */   
/*  710:     */   protected void PathExpr()
/*  711:     */     throws TransformerException
/*  712:     */   {
/*  713:1276 */     int opPos = this.m_ops.getOp(1);
/*  714:     */     
/*  715:1278 */     int filterExprMatch = FilterExpr();
/*  716:1280 */     if (filterExprMatch != 0)
/*  717:     */     {
/*  718:1284 */       boolean locationPathStarted = filterExprMatch == 2;
/*  719:1286 */       if (tokenIs('/'))
/*  720:     */       {
/*  721:1288 */         nextToken();
/*  722:1290 */         if (!locationPathStarted)
/*  723:     */         {
/*  724:1293 */           insertOp(opPos, 2, 28);
/*  725:     */           
/*  726:1295 */           locationPathStarted = true;
/*  727:     */         }
/*  728:1298 */         if (!RelativeLocationPath()) {
/*  729:1301 */           error("ER_EXPECTED_REL_LOC_PATH", null);
/*  730:     */         }
/*  731:     */       }
/*  732:1307 */       if (locationPathStarted)
/*  733:     */       {
/*  734:1309 */         this.m_ops.setOp(this.m_ops.getOp(1), -1);
/*  735:1310 */         this.m_ops.setOp(1, this.m_ops.getOp(1) + 1);
/*  736:1311 */         this.m_ops.setOp(opPos + 1, this.m_ops.getOp(1) - opPos);
/*  737:     */       }
/*  738:     */     }
/*  739:     */     else
/*  740:     */     {
/*  741:1317 */       LocationPath();
/*  742:     */     }
/*  743:     */   }
/*  744:     */   
/*  745:     */   protected int FilterExpr()
/*  746:     */     throws TransformerException
/*  747:     */   {
/*  748:1341 */     int opPos = this.m_ops.getOp(1);
/*  749:     */     int filterMatch;
/*  750:1345 */     if (PrimaryExpr())
/*  751:     */     {
/*  752:1347 */       if (tokenIs('['))
/*  753:     */       {
/*  754:1351 */         insertOp(opPos, 2, 28);
/*  755:1353 */         while (tokenIs('[')) {
/*  756:1355 */           Predicate();
/*  757:     */         }
/*  758:1358 */         filterMatch = 2;
/*  759:     */       }
/*  760:     */       else
/*  761:     */       {
/*  762:1362 */         filterMatch = 1;
/*  763:     */       }
/*  764:     */     }
/*  765:     */     else {
/*  766:1367 */       filterMatch = 0;
/*  767:     */     }
/*  768:1370 */     return filterMatch;
/*  769:     */   }
/*  770:     */   
/*  771:     */   protected boolean PrimaryExpr()
/*  772:     */     throws TransformerException
/*  773:     */   {
/*  774:1398 */     int opPos = this.m_ops.getOp(1);
/*  775:     */     boolean matchFound;
/*  776:1400 */     if ((this.m_tokenChar == '\'') || (this.m_tokenChar == '"'))
/*  777:     */     {
/*  778:1402 */       appendOp(2, 21);
/*  779:1403 */       Literal();
/*  780:     */       
/*  781:1405 */       this.m_ops.setOp(opPos + 1, this.m_ops.getOp(1) - opPos);
/*  782:     */       
/*  783:     */ 
/*  784:1408 */       matchFound = true;
/*  785:     */     }
/*  786:1410 */     else if (this.m_tokenChar == '$')
/*  787:     */     {
/*  788:1412 */       nextToken();
/*  789:1413 */       appendOp(2, 22);
/*  790:1414 */       QName();
/*  791:     */       
/*  792:1416 */       this.m_ops.setOp(opPos + 1, this.m_ops.getOp(1) - opPos);
/*  793:     */       
/*  794:     */ 
/*  795:1419 */       matchFound = true;
/*  796:     */     }
/*  797:1421 */     else if (this.m_tokenChar == '(')
/*  798:     */     {
/*  799:1423 */       nextToken();
/*  800:1424 */       appendOp(2, 23);
/*  801:1425 */       Expr();
/*  802:1426 */       consumeExpected(')');
/*  803:     */       
/*  804:1428 */       this.m_ops.setOp(opPos + 1, this.m_ops.getOp(1) - opPos);
/*  805:     */       
/*  806:     */ 
/*  807:1431 */       matchFound = true;
/*  808:     */     }
/*  809:1433 */     else if ((null != this.m_token) && ((('.' == this.m_tokenChar) && (this.m_token.length() > 1) && (Character.isDigit(this.m_token.charAt(1)))) || (Character.isDigit(this.m_tokenChar))))
/*  810:     */     {
/*  811:1436 */       appendOp(2, 27);
/*  812:1437 */       Number();
/*  813:     */       
/*  814:1439 */       this.m_ops.setOp(opPos + 1, this.m_ops.getOp(1) - opPos);
/*  815:     */       
/*  816:     */ 
/*  817:1442 */       matchFound = true;
/*  818:     */     }
/*  819:1444 */     else if ((lookahead('(', 1)) || ((lookahead(':', 1)) && (lookahead('(', 3))))
/*  820:     */     {
/*  821:1446 */       matchFound = FunctionCall();
/*  822:     */     }
/*  823:     */     else
/*  824:     */     {
/*  825:1450 */       matchFound = false;
/*  826:     */     }
/*  827:1453 */     return matchFound;
/*  828:     */   }
/*  829:     */   
/*  830:     */   protected void Argument()
/*  831:     */     throws TransformerException
/*  832:     */   {
/*  833:1466 */     int opPos = this.m_ops.getOp(1);
/*  834:     */     
/*  835:1468 */     appendOp(2, 26);
/*  836:1469 */     Expr();
/*  837:     */     
/*  838:1471 */     this.m_ops.setOp(opPos + 1, this.m_ops.getOp(1) - opPos);
/*  839:     */   }
/*  840:     */   
/*  841:     */   protected boolean FunctionCall()
/*  842:     */     throws TransformerException
/*  843:     */   {
/*  844:1486 */     int opPos = this.m_ops.getOp(1);
/*  845:1488 */     if (lookahead(':', 1))
/*  846:     */     {
/*  847:1490 */       appendOp(4, 24);
/*  848:     */       
/*  849:1492 */       this.m_ops.setOp(opPos + 1 + 1, this.m_queueMark - 1);
/*  850:     */       
/*  851:1494 */       nextToken();
/*  852:1495 */       consumeExpected(':');
/*  853:     */       
/*  854:1497 */       this.m_ops.setOp(opPos + 1 + 2, this.m_queueMark - 1);
/*  855:     */       
/*  856:1499 */       nextToken();
/*  857:     */     }
/*  858:     */     else
/*  859:     */     {
/*  860:1503 */       int funcTok = getFunctionToken(this.m_token);
/*  861:1505 */       if (-1 == funcTok) {
/*  862:1507 */         error("ER_COULDNOT_FIND_FUNCTION", new Object[] { this.m_token });
/*  863:     */       }
/*  864:1511 */       switch (funcTok)
/*  865:     */       {
/*  866:     */       case 1030: 
/*  867:     */       case 1031: 
/*  868:     */       case 1032: 
/*  869:     */       case 1033: 
/*  870:1518 */         return false;
/*  871:     */       }
/*  872:1520 */       appendOp(3, 25);
/*  873:     */       
/*  874:1522 */       this.m_ops.setOp(opPos + 1 + 1, funcTok);
/*  875:     */       
/*  876:     */ 
/*  877:1525 */       nextToken();
/*  878:     */     }
/*  879:1528 */     consumeExpected('(');
/*  880:1530 */     while ((!tokenIs(')')) && (this.m_token != null))
/*  881:     */     {
/*  882:1532 */       if (tokenIs(',')) {
/*  883:1534 */         error("ER_FOUND_COMMA_BUT_NO_PRECEDING_ARG", null);
/*  884:     */       }
/*  885:1537 */       Argument();
/*  886:1539 */       if (!tokenIs(')'))
/*  887:     */       {
/*  888:1541 */         consumeExpected(',');
/*  889:1543 */         if (tokenIs(')')) {
/*  890:1545 */           error("ER_FOUND_COMMA_BUT_NO_FOLLOWING_ARG", null);
/*  891:     */         }
/*  892:     */       }
/*  893:     */     }
/*  894:1551 */     consumeExpected(')');
/*  895:     */     
/*  896:     */ 
/*  897:1554 */     this.m_ops.setOp(this.m_ops.getOp(1), -1);
/*  898:1555 */     this.m_ops.setOp(1, this.m_ops.getOp(1) + 1);
/*  899:1556 */     this.m_ops.setOp(opPos + 1, this.m_ops.getOp(1) - opPos);
/*  900:     */     
/*  901:     */ 
/*  902:1559 */     return true;
/*  903:     */   }
/*  904:     */   
/*  905:     */   protected void LocationPath()
/*  906:     */     throws TransformerException
/*  907:     */   {
/*  908:1575 */     int opPos = this.m_ops.getOp(1);
/*  909:     */     
/*  910:     */ 
/*  911:1578 */     appendOp(2, 28);
/*  912:     */     
/*  913:1580 */     boolean seenSlash = tokenIs('/');
/*  914:1582 */     if (seenSlash)
/*  915:     */     {
/*  916:1584 */       appendOp(4, 50);
/*  917:     */       
/*  918:     */ 
/*  919:1587 */       this.m_ops.setOp(this.m_ops.getOp(1) - 2, 4);
/*  920:1588 */       this.m_ops.setOp(this.m_ops.getOp(1) - 1, 35);
/*  921:     */       
/*  922:1590 */       nextToken();
/*  923:     */     }
/*  924:1591 */     else if (this.m_token == null)
/*  925:     */     {
/*  926:1592 */       error("ER_EXPECTED_LOC_PATH_AT_END_EXPR", null);
/*  927:     */     }
/*  928:1595 */     if (this.m_token != null) {
/*  929:1597 */       if ((!RelativeLocationPath()) && (!seenSlash)) {
/*  930:1601 */         error("ER_EXPECTED_LOC_PATH", new Object[] { this.m_token });
/*  931:     */       }
/*  932:     */     }
/*  933:1607 */     this.m_ops.setOp(this.m_ops.getOp(1), -1);
/*  934:1608 */     this.m_ops.setOp(1, this.m_ops.getOp(1) + 1);
/*  935:1609 */     this.m_ops.setOp(opPos + 1, this.m_ops.getOp(1) - opPos);
/*  936:     */   }
/*  937:     */   
/*  938:     */   protected boolean RelativeLocationPath()
/*  939:     */     throws TransformerException
/*  940:     */   {
/*  941:1626 */     if (!Step()) {
/*  942:1628 */       return false;
/*  943:     */     }
/*  944:1631 */     while (tokenIs('/'))
/*  945:     */     {
/*  946:1633 */       nextToken();
/*  947:1635 */       if (!Step()) {
/*  948:1639 */         error("ER_EXPECTED_LOC_STEP", null);
/*  949:     */       }
/*  950:     */     }
/*  951:1643 */     return true;
/*  952:     */   }
/*  953:     */   
/*  954:     */   protected boolean Step()
/*  955:     */     throws TransformerException
/*  956:     */   {
/*  957:1657 */     int opPos = this.m_ops.getOp(1);
/*  958:     */     
/*  959:1659 */     boolean doubleSlash = tokenIs('/');
/*  960:1664 */     if (doubleSlash)
/*  961:     */     {
/*  962:1666 */       nextToken();
/*  963:     */       
/*  964:1668 */       appendOp(2, 42);
/*  965:     */       
/*  966:     */ 
/*  967:     */ 
/*  968:     */ 
/*  969:     */ 
/*  970:     */ 
/*  971:     */ 
/*  972:1676 */       this.m_ops.setOp(1, this.m_ops.getOp(1) + 1);
/*  973:1677 */       this.m_ops.setOp(this.m_ops.getOp(1), 1033);
/*  974:1678 */       this.m_ops.setOp(1, this.m_ops.getOp(1) + 1);
/*  975:     */       
/*  976:     */ 
/*  977:1681 */       this.m_ops.setOp(opPos + 1 + 1, this.m_ops.getOp(1) - opPos);
/*  978:     */       
/*  979:     */ 
/*  980:     */ 
/*  981:1685 */       this.m_ops.setOp(opPos + 1, this.m_ops.getOp(1) - opPos);
/*  982:     */       
/*  983:     */ 
/*  984:1688 */       opPos = this.m_ops.getOp(1);
/*  985:     */     }
/*  986:1691 */     if (tokenIs("."))
/*  987:     */     {
/*  988:1693 */       nextToken();
/*  989:1695 */       if (tokenIs('[')) {
/*  990:1697 */         error("ER_PREDICATE_ILLEGAL_SYNTAX", null);
/*  991:     */       }
/*  992:1700 */       appendOp(4, 48);
/*  993:     */       
/*  994:     */ 
/*  995:1703 */       this.m_ops.setOp(this.m_ops.getOp(1) - 2, 4);
/*  996:1704 */       this.m_ops.setOp(this.m_ops.getOp(1) - 1, 1033);
/*  997:     */     }
/*  998:1706 */     else if (tokenIs(".."))
/*  999:     */     {
/* 1000:1708 */       nextToken();
/* 1001:1709 */       appendOp(4, 45);
/* 1002:     */       
/* 1003:     */ 
/* 1004:1712 */       this.m_ops.setOp(this.m_ops.getOp(1) - 2, 4);
/* 1005:1713 */       this.m_ops.setOp(this.m_ops.getOp(1) - 1, 1033);
/* 1006:     */     }
/* 1007:1719 */     else if ((tokenIs('*')) || (tokenIs('@')) || (tokenIs('_')) || ((this.m_token != null) && (Character.isLetter(this.m_token.charAt(0)))))
/* 1008:     */     {
/* 1009:1722 */       Basis();
/* 1010:1724 */       while (tokenIs('[')) {
/* 1011:1726 */         Predicate();
/* 1012:     */       }
/* 1013:1730 */       this.m_ops.setOp(opPos + 1, this.m_ops.getOp(1) - opPos);
/* 1014:     */     }
/* 1015:     */     else
/* 1016:     */     {
/* 1017:1736 */       if (doubleSlash) {
/* 1018:1739 */         error("ER_EXPECTED_LOC_STEP", null);
/* 1019:     */       }
/* 1020:1742 */       return false;
/* 1021:     */     }
/* 1022:1745 */     return true;
/* 1023:     */   }
/* 1024:     */   
/* 1025:     */   protected void Basis()
/* 1026:     */     throws TransformerException
/* 1027:     */   {
/* 1028:1758 */     int opPos = this.m_ops.getOp(1);
/* 1029:     */     int axesType;
/* 1030:1762 */     if (lookahead("::", 1))
/* 1031:     */     {
/* 1032:1764 */       axesType = AxisName();
/* 1033:     */       
/* 1034:1766 */       nextToken();
/* 1035:1767 */       nextToken();
/* 1036:     */     }
/* 1037:1769 */     else if (tokenIs('@'))
/* 1038:     */     {
/* 1039:1771 */       axesType = 39;
/* 1040:     */       
/* 1041:1773 */       appendOp(2, axesType);
/* 1042:1774 */       nextToken();
/* 1043:     */     }
/* 1044:     */     else
/* 1045:     */     {
/* 1046:1778 */       axesType = 40;
/* 1047:     */       
/* 1048:1780 */       appendOp(2, axesType);
/* 1049:     */     }
/* 1050:1784 */     this.m_ops.setOp(1, this.m_ops.getOp(1) + 1);
/* 1051:     */     
/* 1052:1786 */     NodeTest(axesType);
/* 1053:     */     
/* 1054:     */ 
/* 1055:1789 */     this.m_ops.setOp(opPos + 1 + 1, this.m_ops.getOp(1) - opPos);
/* 1056:     */   }
/* 1057:     */   
/* 1058:     */   protected int AxisName()
/* 1059:     */     throws TransformerException
/* 1060:     */   {
/* 1061:1805 */     Object val = Keywords.getAxisName(this.m_token);
/* 1062:1807 */     if (null == val) {
/* 1063:1809 */       error("ER_ILLEGAL_AXIS_NAME", new Object[] { this.m_token });
/* 1064:     */     }
/* 1065:1813 */     int axesType = ((Integer)val).intValue();
/* 1066:     */     
/* 1067:1815 */     appendOp(2, axesType);
/* 1068:     */     
/* 1069:1817 */     return axesType;
/* 1070:     */   }
/* 1071:     */   
/* 1072:     */   protected void NodeTest(int axesType)
/* 1073:     */     throws TransformerException
/* 1074:     */   {
/* 1075:1833 */     if (lookahead('(', 1))
/* 1076:     */     {
/* 1077:1835 */       Object nodeTestOp = Keywords.getNodeType(this.m_token);
/* 1078:1837 */       if (null == nodeTestOp)
/* 1079:     */       {
/* 1080:1839 */         error("ER_UNKNOWN_NODETYPE", new Object[] { this.m_token });
/* 1081:     */       }
/* 1082:     */       else
/* 1083:     */       {
/* 1084:1844 */         nextToken();
/* 1085:     */         
/* 1086:1846 */         int nt = ((Integer)nodeTestOp).intValue();
/* 1087:     */         
/* 1088:1848 */         this.m_ops.setOp(this.m_ops.getOp(1), nt);
/* 1089:1849 */         this.m_ops.setOp(1, this.m_ops.getOp(1) + 1);
/* 1090:     */         
/* 1091:1851 */         consumeExpected('(');
/* 1092:1853 */         if (1032 == nt) {
/* 1093:1855 */           if (!tokenIs(')')) {
/* 1094:1857 */             Literal();
/* 1095:     */           }
/* 1096:     */         }
/* 1097:1861 */         consumeExpected(')');
/* 1098:     */       }
/* 1099:     */     }
/* 1100:     */     else
/* 1101:     */     {
/* 1102:1868 */       this.m_ops.setOp(this.m_ops.getOp(1), 34);
/* 1103:1869 */       this.m_ops.setOp(1, this.m_ops.getOp(1) + 1);
/* 1104:1871 */       if (lookahead(':', 1))
/* 1105:     */       {
/* 1106:1873 */         if (tokenIs('*'))
/* 1107:     */         {
/* 1108:1875 */           this.m_ops.setOp(this.m_ops.getOp(1), -3);
/* 1109:     */         }
/* 1110:     */         else
/* 1111:     */         {
/* 1112:1879 */           this.m_ops.setOp(this.m_ops.getOp(1), this.m_queueMark - 1);
/* 1113:1883 */           if ((!Character.isLetter(this.m_tokenChar)) && (!tokenIs('_'))) {
/* 1114:1886 */             error("ER_EXPECTED_NODE_TEST", null);
/* 1115:     */           }
/* 1116:     */         }
/* 1117:1890 */         nextToken();
/* 1118:1891 */         consumeExpected(':');
/* 1119:     */       }
/* 1120:     */       else
/* 1121:     */       {
/* 1122:1895 */         this.m_ops.setOp(this.m_ops.getOp(1), -2);
/* 1123:     */       }
/* 1124:1898 */       this.m_ops.setOp(1, this.m_ops.getOp(1) + 1);
/* 1125:1900 */       if (tokenIs('*'))
/* 1126:     */       {
/* 1127:1902 */         this.m_ops.setOp(this.m_ops.getOp(1), -3);
/* 1128:     */       }
/* 1129:     */       else
/* 1130:     */       {
/* 1131:1906 */         this.m_ops.setOp(this.m_ops.getOp(1), this.m_queueMark - 1);
/* 1132:1910 */         if ((!Character.isLetter(this.m_tokenChar)) && (!tokenIs('_'))) {
/* 1133:1913 */           error("ER_EXPECTED_NODE_TEST", null);
/* 1134:     */         }
/* 1135:     */       }
/* 1136:1917 */       this.m_ops.setOp(1, this.m_ops.getOp(1) + 1);
/* 1137:     */       
/* 1138:1919 */       nextToken();
/* 1139:     */     }
/* 1140:     */   }
/* 1141:     */   
/* 1142:     */   protected void Predicate()
/* 1143:     */     throws TransformerException
/* 1144:     */   {
/* 1145:1933 */     if (tokenIs('['))
/* 1146:     */     {
/* 1147:1935 */       nextToken();
/* 1148:1936 */       PredicateExpr();
/* 1149:1937 */       consumeExpected(']');
/* 1150:     */     }
/* 1151:     */   }
/* 1152:     */   
/* 1153:     */   protected void PredicateExpr()
/* 1154:     */     throws TransformerException
/* 1155:     */   {
/* 1156:1951 */     int opPos = this.m_ops.getOp(1);
/* 1157:     */     
/* 1158:1953 */     appendOp(2, 29);
/* 1159:1954 */     Expr();
/* 1160:     */     
/* 1161:     */ 
/* 1162:1957 */     this.m_ops.setOp(this.m_ops.getOp(1), -1);
/* 1163:1958 */     this.m_ops.setOp(1, this.m_ops.getOp(1) + 1);
/* 1164:1959 */     this.m_ops.setOp(opPos + 1, this.m_ops.getOp(1) - opPos);
/* 1165:     */   }
/* 1166:     */   
/* 1167:     */   protected void QName()
/* 1168:     */     throws TransformerException
/* 1169:     */   {
/* 1170:1973 */     if (lookahead(':', 1))
/* 1171:     */     {
/* 1172:1975 */       this.m_ops.setOp(this.m_ops.getOp(1), this.m_queueMark - 1);
/* 1173:1976 */       this.m_ops.setOp(1, this.m_ops.getOp(1) + 1);
/* 1174:     */       
/* 1175:1978 */       nextToken();
/* 1176:1979 */       consumeExpected(':');
/* 1177:     */     }
/* 1178:     */     else
/* 1179:     */     {
/* 1180:1983 */       this.m_ops.setOp(this.m_ops.getOp(1), -2);
/* 1181:1984 */       this.m_ops.setOp(1, this.m_ops.getOp(1) + 1);
/* 1182:     */     }
/* 1183:1988 */     this.m_ops.setOp(this.m_ops.getOp(1), this.m_queueMark - 1);
/* 1184:1989 */     this.m_ops.setOp(1, this.m_ops.getOp(1) + 1);
/* 1185:     */     
/* 1186:1991 */     nextToken();
/* 1187:     */   }
/* 1188:     */   
/* 1189:     */   protected void NCName()
/* 1190:     */   {
/* 1191:2001 */     this.m_ops.setOp(this.m_ops.getOp(1), this.m_queueMark - 1);
/* 1192:2002 */     this.m_ops.setOp(1, this.m_ops.getOp(1) + 1);
/* 1193:     */     
/* 1194:2004 */     nextToken();
/* 1195:     */   }
/* 1196:     */   
/* 1197:     */   protected void Literal()
/* 1198:     */     throws TransformerException
/* 1199:     */   {
/* 1200:2020 */     int last = this.m_token.length() - 1;
/* 1201:2021 */     char c0 = this.m_tokenChar;
/* 1202:2022 */     char cX = this.m_token.charAt(last);
/* 1203:2024 */     if (((c0 == '"') && (cX == '"')) || ((c0 == '\'') && (cX == '\'')))
/* 1204:     */     {
/* 1205:2029 */       int tokenQueuePos = this.m_queueMark - 1;
/* 1206:     */       
/* 1207:2031 */       this.m_ops.m_tokenQueue.setElementAt(null, tokenQueuePos);
/* 1208:     */       
/* 1209:2033 */       Object obj = new XString(this.m_token.substring(1, last));
/* 1210:     */       
/* 1211:2035 */       this.m_ops.m_tokenQueue.setElementAt(obj, tokenQueuePos);
/* 1212:     */       
/* 1213:     */ 
/* 1214:2038 */       this.m_ops.setOp(this.m_ops.getOp(1), tokenQueuePos);
/* 1215:2039 */       this.m_ops.setOp(1, this.m_ops.getOp(1) + 1);
/* 1216:     */       
/* 1217:2041 */       nextToken();
/* 1218:     */     }
/* 1219:     */     else
/* 1220:     */     {
/* 1221:2045 */       error("ER_PATTERN_LITERAL_NEEDS_BE_QUOTED", new Object[] { this.m_token });
/* 1222:     */     }
/* 1223:     */   }
/* 1224:     */   
/* 1225:     */   protected void Number()
/* 1226:     */     throws TransformerException
/* 1227:     */   {
/* 1228:2060 */     if (null != this.m_token)
/* 1229:     */     {
/* 1230:     */       double num;
/* 1231:     */       try
/* 1232:     */       {
/* 1233:2070 */         if ((this.m_token.indexOf('e') > -1) || (this.m_token.indexOf('E') > -1)) {
/* 1234:2071 */           throw new NumberFormatException();
/* 1235:     */         }
/* 1236:2072 */         num = Double.valueOf(this.m_token).doubleValue();
/* 1237:     */       }
/* 1238:     */       catch (NumberFormatException nfe)
/* 1239:     */       {
/* 1240:2076 */         num = 0.0D;
/* 1241:     */         
/* 1242:2078 */         error("ER_COULDNOT_BE_FORMATTED_TO_NUMBER", new Object[] { this.m_token });
/* 1243:     */       }
/* 1244:2082 */       this.m_ops.m_tokenQueue.setElementAt(new XNumber(num), this.m_queueMark - 1);
/* 1245:2083 */       this.m_ops.setOp(this.m_ops.getOp(1), this.m_queueMark - 1);
/* 1246:2084 */       this.m_ops.setOp(1, this.m_ops.getOp(1) + 1);
/* 1247:     */       
/* 1248:2086 */       nextToken();
/* 1249:     */     }
/* 1250:     */   }
/* 1251:     */   
/* 1252:     */   protected void Pattern()
/* 1253:     */     throws TransformerException
/* 1254:     */   {
/* 1255:     */     for (;;)
/* 1256:     */     {
/* 1257:2105 */       LocationPathPattern();
/* 1258:2107 */       if (!tokenIs('|')) {
/* 1259:     */         break;
/* 1260:     */       }
/* 1261:2109 */       nextToken();
/* 1262:     */     }
/* 1263:     */   }
/* 1264:     */   
/* 1265:     */   protected void LocationPathPattern()
/* 1266:     */     throws TransformerException
/* 1267:     */   {
/* 1268:2131 */     int opPos = this.m_ops.getOp(1);
/* 1269:     */     
/* 1270:2133 */     int RELATIVE_PATH_NOT_PERMITTED = 0;
/* 1271:2134 */     int RELATIVE_PATH_PERMITTED = 1;
/* 1272:2135 */     int RELATIVE_PATH_REQUIRED = 2;
/* 1273:     */     
/* 1274:2137 */     int relativePathStatus = 0;
/* 1275:     */     
/* 1276:2139 */     appendOp(2, 31);
/* 1277:2141 */     if ((lookahead('(', 1)) && ((tokenIs("id")) || (tokenIs("key"))))
/* 1278:     */     {
/* 1279:2145 */       IdKeyPattern();
/* 1280:2147 */       if (tokenIs('/'))
/* 1281:     */       {
/* 1282:2149 */         nextToken();
/* 1283:2151 */         if (tokenIs('/'))
/* 1284:     */         {
/* 1285:2153 */           appendOp(4, 52);
/* 1286:     */           
/* 1287:2155 */           nextToken();
/* 1288:     */         }
/* 1289:     */         else
/* 1290:     */         {
/* 1291:2159 */           appendOp(4, 53);
/* 1292:     */         }
/* 1293:2163 */         this.m_ops.setOp(this.m_ops.getOp(1) - 2, 4);
/* 1294:2164 */         this.m_ops.setOp(this.m_ops.getOp(1) - 1, 1034);
/* 1295:     */         
/* 1296:2166 */         relativePathStatus = 2;
/* 1297:     */       }
/* 1298:     */     }
/* 1299:2169 */     else if (tokenIs('/'))
/* 1300:     */     {
/* 1301:2171 */       if (lookahead('/', 1))
/* 1302:     */       {
/* 1303:2173 */         appendOp(4, 52);
/* 1304:     */         
/* 1305:     */ 
/* 1306:     */ 
/* 1307:     */ 
/* 1308:     */ 
/* 1309:2179 */         nextToken();
/* 1310:     */         
/* 1311:2181 */         relativePathStatus = 2;
/* 1312:     */       }
/* 1313:     */       else
/* 1314:     */       {
/* 1315:2185 */         appendOp(4, 50);
/* 1316:     */         
/* 1317:2187 */         relativePathStatus = 1;
/* 1318:     */       }
/* 1319:2192 */       this.m_ops.setOp(this.m_ops.getOp(1) - 2, 4);
/* 1320:2193 */       this.m_ops.setOp(this.m_ops.getOp(1) - 1, 35);
/* 1321:     */       
/* 1322:2195 */       nextToken();
/* 1323:     */     }
/* 1324:     */     else
/* 1325:     */     {
/* 1326:2199 */       relativePathStatus = 2;
/* 1327:     */     }
/* 1328:2202 */     if (relativePathStatus != 0) {
/* 1329:2204 */       if ((!tokenIs('|')) && (null != this.m_token)) {
/* 1330:2206 */         RelativePathPattern();
/* 1331:2208 */       } else if (relativePathStatus == 2) {
/* 1332:2211 */         error("ER_EXPECTED_REL_PATH_PATTERN", null);
/* 1333:     */       }
/* 1334:     */     }
/* 1335:2216 */     this.m_ops.setOp(this.m_ops.getOp(1), -1);
/* 1336:2217 */     this.m_ops.setOp(1, this.m_ops.getOp(1) + 1);
/* 1337:2218 */     this.m_ops.setOp(opPos + 1, this.m_ops.getOp(1) - opPos);
/* 1338:     */   }
/* 1339:     */   
/* 1340:     */   protected void IdKeyPattern()
/* 1341:     */     throws TransformerException
/* 1342:     */   {
/* 1343:2233 */     FunctionCall();
/* 1344:     */   }
/* 1345:     */   
/* 1346:     */   protected void RelativePathPattern()
/* 1347:     */     throws TransformerException
/* 1348:     */   {
/* 1349:2250 */     boolean trailingSlashConsumed = StepPattern(false);
/* 1350:2252 */     while (tokenIs('/'))
/* 1351:     */     {
/* 1352:2254 */       nextToken();
/* 1353:     */       
/* 1354:     */ 
/* 1355:     */ 
/* 1356:     */ 
/* 1357:2259 */       trailingSlashConsumed = StepPattern(!trailingSlashConsumed);
/* 1358:     */     }
/* 1359:     */   }
/* 1360:     */   
/* 1361:     */   protected boolean StepPattern(boolean isLeadingSlashPermitted)
/* 1362:     */     throws TransformerException
/* 1363:     */   {
/* 1364:2277 */     return AbbreviatedNodeTestStep(isLeadingSlashPermitted);
/* 1365:     */   }
/* 1366:     */   
/* 1367:     */   protected boolean AbbreviatedNodeTestStep(boolean isLeadingSlashPermitted)
/* 1368:     */     throws TransformerException
/* 1369:     */   {
/* 1370:2295 */     int opPos = this.m_ops.getOp(1);
/* 1371:     */     
/* 1372:     */ 
/* 1373:     */ 
/* 1374:2299 */     int matchTypePos = -1;
/* 1375:     */     int axesType;
/* 1376:2301 */     if (tokenIs('@'))
/* 1377:     */     {
/* 1378:2303 */       axesType = 51;
/* 1379:     */       
/* 1380:2305 */       appendOp(2, axesType);
/* 1381:2306 */       nextToken();
/* 1382:     */     }
/* 1383:2308 */     else if (lookahead("::", 1))
/* 1384:     */     {
/* 1385:2310 */       if (tokenIs("attribute"))
/* 1386:     */       {
/* 1387:2312 */         axesType = 51;
/* 1388:     */         
/* 1389:2314 */         appendOp(2, axesType);
/* 1390:     */       }
/* 1391:2316 */       else if (tokenIs("child"))
/* 1392:     */       {
/* 1393:2318 */         matchTypePos = this.m_ops.getOp(1);
/* 1394:2319 */         axesType = 53;
/* 1395:     */         
/* 1396:2321 */         appendOp(2, axesType);
/* 1397:     */       }
/* 1398:     */       else
/* 1399:     */       {
/* 1400:2325 */         axesType = -1;
/* 1401:     */         
/* 1402:2327 */         error("ER_AXES_NOT_ALLOWED", new Object[] { this.m_token });
/* 1403:     */       }
/* 1404:2331 */       nextToken();
/* 1405:2332 */       nextToken();
/* 1406:     */     }
/* 1407:2334 */     else if (tokenIs('/'))
/* 1408:     */     {
/* 1409:2336 */       if (!isLeadingSlashPermitted) {
/* 1410:2339 */         error("ER_EXPECTED_STEP_PATTERN", null);
/* 1411:     */       }
/* 1412:2341 */       axesType = 52;
/* 1413:     */       
/* 1414:2343 */       appendOp(2, axesType);
/* 1415:2344 */       nextToken();
/* 1416:     */     }
/* 1417:     */     else
/* 1418:     */     {
/* 1419:2348 */       matchTypePos = this.m_ops.getOp(1);
/* 1420:2349 */       axesType = 53;
/* 1421:     */       
/* 1422:2351 */       appendOp(2, axesType);
/* 1423:     */     }
/* 1424:2355 */     this.m_ops.setOp(1, this.m_ops.getOp(1) + 1);
/* 1425:     */     
/* 1426:2357 */     NodeTest(axesType);
/* 1427:     */     
/* 1428:     */ 
/* 1429:2360 */     this.m_ops.setOp(opPos + 1 + 1, this.m_ops.getOp(1) - opPos);
/* 1430:2363 */     while (tokenIs('[')) {
/* 1431:2365 */       Predicate();
/* 1432:     */     }
/* 1433:     */     boolean trailingSlashConsumed;
/* 1434:2382 */     if ((matchTypePos > -1) && (tokenIs('/')) && (lookahead('/', 1)))
/* 1435:     */     {
/* 1436:2384 */       this.m_ops.setOp(matchTypePos, 52);
/* 1437:     */       
/* 1438:2386 */       nextToken();
/* 1439:     */       
/* 1440:2388 */       trailingSlashConsumed = true;
/* 1441:     */     }
/* 1442:     */     else
/* 1443:     */     {
/* 1444:2392 */       trailingSlashConsumed = false;
/* 1445:     */     }
/* 1446:2396 */     this.m_ops.setOp(opPos + 1, this.m_ops.getOp(1) - opPos);
/* 1447:     */     
/* 1448:     */ 
/* 1449:2399 */     return trailingSlashConsumed;
/* 1450:     */   }
/* 1451:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.compiler.XPathParser
 * JD-Core Version:    0.7.0.1
 */