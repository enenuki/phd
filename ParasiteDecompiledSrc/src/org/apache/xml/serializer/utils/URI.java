/*    1:     */ package org.apache.xml.serializer.utils;
/*    2:     */ 
/*    3:     */ import java.io.IOException;
/*    4:     */ 
/*    5:     */ final class URI
/*    6:     */ {
/*    7:     */   private static final String RESERVED_CHARACTERS = ";/?:@&=+$,";
/*    8:     */   private static final String MARK_CHARACTERS = "-_.!~*'() ";
/*    9:     */   private static final String SCHEME_CHARACTERS = "+-.";
/*   10:     */   private static final String USERINFO_CHARACTERS = ";:&=+$,";
/*   11:     */   public URI() {}
/*   12:     */   
/*   13:     */   public static class MalformedURIException
/*   14:     */     extends IOException
/*   15:     */   {
/*   16:     */     public MalformedURIException() {}
/*   17:     */     
/*   18:     */     public MalformedURIException(String p_msg)
/*   19:     */     {
/*   20:  93 */       super();
/*   21:     */     }
/*   22:     */   }
/*   23:     */   
/*   24: 117 */   private String m_scheme = null;
/*   25: 121 */   private String m_userinfo = null;
/*   26: 125 */   private String m_host = null;
/*   27: 129 */   private int m_port = -1;
/*   28: 133 */   private String m_path = null;
/*   29: 140 */   private String m_queryString = null;
/*   30: 144 */   private String m_fragment = null;
/*   31: 147 */   private static boolean DEBUG = false;
/*   32:     */   
/*   33:     */   public URI(URI p_other)
/*   34:     */   {
/*   35: 162 */     initialize(p_other);
/*   36:     */   }
/*   37:     */   
/*   38:     */   public URI(String p_uriSpec)
/*   39:     */     throws URI.MalformedURIException
/*   40:     */   {
/*   41: 182 */     this((URI)null, p_uriSpec);
/*   42:     */   }
/*   43:     */   
/*   44:     */   public URI(URI p_base, String p_uriSpec)
/*   45:     */     throws URI.MalformedURIException
/*   46:     */   {
/*   47: 199 */     initialize(p_base, p_uriSpec);
/*   48:     */   }
/*   49:     */   
/*   50:     */   public URI(String p_scheme, String p_schemeSpecificPart)
/*   51:     */     throws URI.MalformedURIException
/*   52:     */   {
/*   53: 218 */     if ((p_scheme == null) || (p_scheme.trim().length() == 0)) {
/*   54: 220 */       throw new MalformedURIException("Cannot construct URI with null/empty scheme!");
/*   55:     */     }
/*   56: 224 */     if ((p_schemeSpecificPart == null) || (p_schemeSpecificPart.trim().length() == 0)) {
/*   57: 227 */       throw new MalformedURIException("Cannot construct URI with null/empty scheme-specific part!");
/*   58:     */     }
/*   59: 231 */     setScheme(p_scheme);
/*   60: 232 */     setPath(p_schemeSpecificPart);
/*   61:     */   }
/*   62:     */   
/*   63:     */   public URI(String p_scheme, String p_host, String p_path, String p_queryString, String p_fragment)
/*   64:     */     throws URI.MalformedURIException
/*   65:     */   {
/*   66: 259 */     this(p_scheme, null, p_host, -1, p_path, p_queryString, p_fragment);
/*   67:     */   }
/*   68:     */   
/*   69:     */   public URI(String p_scheme, String p_userinfo, String p_host, int p_port, String p_path, String p_queryString, String p_fragment)
/*   70:     */     throws URI.MalformedURIException
/*   71:     */   {
/*   72: 291 */     if ((p_scheme == null) || (p_scheme.trim().length() == 0)) {
/*   73: 293 */       throw new MalformedURIException(Utils.messages.createMessage("ER_SCHEME_REQUIRED", null));
/*   74:     */     }
/*   75: 296 */     if (p_host == null)
/*   76:     */     {
/*   77: 298 */       if (p_userinfo != null) {
/*   78: 300 */         throw new MalformedURIException(Utils.messages.createMessage("ER_NO_USERINFO_IF_NO_HOST", null));
/*   79:     */       }
/*   80: 304 */       if (p_port != -1) {
/*   81: 306 */         throw new MalformedURIException(Utils.messages.createMessage("ER_NO_PORT_IF_NO_HOST", null));
/*   82:     */       }
/*   83:     */     }
/*   84: 311 */     if (p_path != null)
/*   85:     */     {
/*   86: 313 */       if ((p_path.indexOf('?') != -1) && (p_queryString != null)) {
/*   87: 315 */         throw new MalformedURIException(Utils.messages.createMessage("ER_NO_QUERY_STRING_IN_PATH", null));
/*   88:     */       }
/*   89: 319 */       if ((p_path.indexOf('#') != -1) && (p_fragment != null)) {
/*   90: 321 */         throw new MalformedURIException(Utils.messages.createMessage("ER_NO_FRAGMENT_STRING_IN_PATH", null));
/*   91:     */       }
/*   92:     */     }
/*   93: 326 */     setScheme(p_scheme);
/*   94: 327 */     setHost(p_host);
/*   95: 328 */     setPort(p_port);
/*   96: 329 */     setUserinfo(p_userinfo);
/*   97: 330 */     setPath(p_path);
/*   98: 331 */     setQueryString(p_queryString);
/*   99: 332 */     setFragment(p_fragment);
/*  100:     */   }
/*  101:     */   
/*  102:     */   private void initialize(URI p_other)
/*  103:     */   {
/*  104: 343 */     this.m_scheme = p_other.getScheme();
/*  105: 344 */     this.m_userinfo = p_other.getUserinfo();
/*  106: 345 */     this.m_host = p_other.getHost();
/*  107: 346 */     this.m_port = p_other.getPort();
/*  108: 347 */     this.m_path = p_other.getPath();
/*  109: 348 */     this.m_queryString = p_other.getQueryString();
/*  110: 349 */     this.m_fragment = p_other.getFragment();
/*  111:     */   }
/*  112:     */   
/*  113:     */   private void initialize(URI p_base, String p_uriSpec)
/*  114:     */     throws URI.MalformedURIException
/*  115:     */   {
/*  116: 372 */     if ((p_base == null) && ((p_uriSpec == null) || (p_uriSpec.trim().length() == 0))) {
/*  117: 375 */       throw new MalformedURIException(Utils.messages.createMessage("ER_CANNOT_INIT_URI_EMPTY_PARMS", null));
/*  118:     */     }
/*  119: 380 */     if ((p_uriSpec == null) || (p_uriSpec.trim().length() == 0))
/*  120:     */     {
/*  121: 382 */       initialize(p_base);
/*  122:     */       
/*  123: 384 */       return;
/*  124:     */     }
/*  125: 387 */     String uriSpec = p_uriSpec.trim();
/*  126: 388 */     int uriSpecLen = uriSpec.length();
/*  127: 389 */     int index = 0;
/*  128:     */     
/*  129:     */ 
/*  130: 392 */     int colonIndex = uriSpec.indexOf(':');
/*  131: 393 */     if (colonIndex < 0)
/*  132:     */     {
/*  133: 395 */       if (p_base == null) {
/*  134: 397 */         throw new MalformedURIException(Utils.messages.createMessage("ER_NO_SCHEME_IN_URI", new Object[] { uriSpec }));
/*  135:     */       }
/*  136:     */     }
/*  137:     */     else
/*  138:     */     {
/*  139: 402 */       initializeScheme(uriSpec);
/*  140: 403 */       uriSpec = uriSpec.substring(colonIndex + 1);
/*  141: 404 */       uriSpecLen = uriSpec.length();
/*  142:     */     }
/*  143: 408 */     if (uriSpec.startsWith("//"))
/*  144:     */     {
/*  145: 410 */       index += 2;
/*  146:     */       
/*  147: 412 */       int startPos = index;
/*  148:     */       
/*  149:     */ 
/*  150: 415 */       char testChar = '\000';
/*  151: 417 */       while (index < uriSpecLen)
/*  152:     */       {
/*  153: 419 */         testChar = uriSpec.charAt(index);
/*  154: 421 */         if ((testChar == '/') || (testChar == '?') || (testChar == '#')) {
/*  155:     */           break;
/*  156:     */         }
/*  157: 426 */         index++;
/*  158:     */       }
/*  159: 431 */       if (index > startPos) {
/*  160: 433 */         initializeAuthority(uriSpec.substring(startPos, index));
/*  161:     */       } else {
/*  162: 437 */         this.m_host = "";
/*  163:     */       }
/*  164:     */     }
/*  165: 441 */     initializePath(uriSpec.substring(index));
/*  166: 448 */     if (p_base != null)
/*  167:     */     {
/*  168: 458 */       if ((this.m_path.length() == 0) && (this.m_scheme == null) && (this.m_host == null))
/*  169:     */       {
/*  170: 460 */         this.m_scheme = p_base.getScheme();
/*  171: 461 */         this.m_userinfo = p_base.getUserinfo();
/*  172: 462 */         this.m_host = p_base.getHost();
/*  173: 463 */         this.m_port = p_base.getPort();
/*  174: 464 */         this.m_path = p_base.getPath();
/*  175: 466 */         if (this.m_queryString == null) {
/*  176: 468 */           this.m_queryString = p_base.getQueryString();
/*  177:     */         }
/*  178: 471 */         return;
/*  179:     */       }
/*  180: 476 */       if (this.m_scheme == null) {
/*  181: 478 */         this.m_scheme = p_base.getScheme();
/*  182:     */       }
/*  183: 483 */       if (this.m_host == null)
/*  184:     */       {
/*  185: 485 */         this.m_userinfo = p_base.getUserinfo();
/*  186: 486 */         this.m_host = p_base.getHost();
/*  187: 487 */         this.m_port = p_base.getPort();
/*  188:     */       }
/*  189:     */       else
/*  190:     */       {
/*  191: 491 */         return;
/*  192:     */       }
/*  193: 495 */       if ((this.m_path.length() > 0) && (this.m_path.startsWith("/"))) {
/*  194: 497 */         return;
/*  195:     */       }
/*  196: 502 */       String path = new String();
/*  197: 503 */       String basePath = p_base.getPath();
/*  198: 506 */       if (basePath != null)
/*  199:     */       {
/*  200: 508 */         int lastSlash = basePath.lastIndexOf('/');
/*  201: 510 */         if (lastSlash != -1) {
/*  202: 512 */           path = basePath.substring(0, lastSlash + 1);
/*  203:     */         }
/*  204:     */       }
/*  205: 517 */       path = path.concat(this.m_path);
/*  206:     */       
/*  207:     */ 
/*  208: 520 */       index = -1;
/*  209: 522 */       while ((index = path.indexOf("/./")) != -1) {
/*  210: 524 */         path = path.substring(0, index + 1).concat(path.substring(index + 3));
/*  211:     */       }
/*  212: 528 */       if (path.endsWith("/.")) {
/*  213: 530 */         path = path.substring(0, path.length() - 1);
/*  214:     */       }
/*  215: 535 */       index = -1;
/*  216:     */       
/*  217: 537 */       int segIndex = -1;
/*  218: 538 */       String tempString = null;
/*  219: 540 */       while ((index = path.indexOf("/../")) > 0)
/*  220:     */       {
/*  221: 542 */         tempString = path.substring(0, path.indexOf("/../"));
/*  222: 543 */         segIndex = tempString.lastIndexOf('/');
/*  223: 545 */         if (segIndex != -1) {
/*  224: 547 */           if (!tempString.substring(segIndex++).equals("..")) {
/*  225: 549 */             path = path.substring(0, segIndex).concat(path.substring(index + 4));
/*  226:     */           }
/*  227:     */         }
/*  228:     */       }
/*  229: 557 */       if (path.endsWith("/.."))
/*  230:     */       {
/*  231: 559 */         tempString = path.substring(0, path.length() - 3);
/*  232: 560 */         segIndex = tempString.lastIndexOf('/');
/*  233: 562 */         if (segIndex != -1) {
/*  234: 564 */           path = path.substring(0, segIndex + 1);
/*  235:     */         }
/*  236:     */       }
/*  237: 568 */       this.m_path = path;
/*  238:     */     }
/*  239:     */   }
/*  240:     */   
/*  241:     */   private void initializeScheme(String p_uriSpec)
/*  242:     */     throws URI.MalformedURIException
/*  243:     */   {
/*  244: 583 */     int uriSpecLen = p_uriSpec.length();
/*  245: 584 */     int index = 0;
/*  246: 585 */     String scheme = null;
/*  247: 586 */     char testChar = '\000';
/*  248: 588 */     while (index < uriSpecLen)
/*  249:     */     {
/*  250: 590 */       testChar = p_uriSpec.charAt(index);
/*  251: 592 */       if ((testChar == ':') || (testChar == '/') || (testChar == '?') || (testChar == '#')) {
/*  252:     */         break;
/*  253:     */       }
/*  254: 598 */       index++;
/*  255:     */     }
/*  256: 601 */     scheme = p_uriSpec.substring(0, index);
/*  257: 603 */     if (scheme.length() == 0) {
/*  258: 605 */       throw new MalformedURIException(Utils.messages.createMessage("ER_NO_SCHEME_INURI", null));
/*  259:     */     }
/*  260: 609 */     setScheme(scheme);
/*  261:     */   }
/*  262:     */   
/*  263:     */   private void initializeAuthority(String p_uriSpec)
/*  264:     */     throws URI.MalformedURIException
/*  265:     */   {
/*  266: 625 */     int index = 0;
/*  267: 626 */     int start = 0;
/*  268: 627 */     int end = p_uriSpec.length();
/*  269: 628 */     char testChar = '\000';
/*  270: 629 */     String userinfo = null;
/*  271: 632 */     if (p_uriSpec.indexOf('@', start) != -1)
/*  272:     */     {
/*  273: 634 */       while (index < end)
/*  274:     */       {
/*  275: 636 */         testChar = p_uriSpec.charAt(index);
/*  276: 638 */         if (testChar == '@') {
/*  277:     */           break;
/*  278:     */         }
/*  279: 643 */         index++;
/*  280:     */       }
/*  281: 646 */       userinfo = p_uriSpec.substring(start, index);
/*  282:     */       
/*  283: 648 */       index++;
/*  284:     */     }
/*  285: 652 */     String host = null;
/*  286:     */     
/*  287: 654 */     start = index;
/*  288: 656 */     while (index < end)
/*  289:     */     {
/*  290: 658 */       testChar = p_uriSpec.charAt(index);
/*  291: 660 */       if (testChar == ':') {
/*  292:     */         break;
/*  293:     */       }
/*  294: 665 */       index++;
/*  295:     */     }
/*  296: 668 */     host = p_uriSpec.substring(start, index);
/*  297:     */     
/*  298: 670 */     int port = -1;
/*  299: 672 */     if (host.length() > 0) {
/*  300: 676 */       if (testChar == ':')
/*  301:     */       {
/*  302: 678 */         index++;
/*  303:     */         
/*  304: 680 */         start = index;
/*  305: 682 */         while (index < end) {
/*  306: 684 */           index++;
/*  307:     */         }
/*  308: 687 */         String portStr = p_uriSpec.substring(start, index);
/*  309: 689 */         if (portStr.length() > 0)
/*  310:     */         {
/*  311: 691 */           for (int i = 0; i < portStr.length(); i++) {
/*  312: 693 */             if (!isDigit(portStr.charAt(i))) {
/*  313: 695 */               throw new MalformedURIException(portStr + " is invalid. Port should only contain digits!");
/*  314:     */             }
/*  315:     */           }
/*  316:     */           try
/*  317:     */           {
/*  318: 702 */             port = Integer.parseInt(portStr);
/*  319:     */           }
/*  320:     */           catch (NumberFormatException nfe) {}
/*  321:     */         }
/*  322:     */       }
/*  323:     */     }
/*  324: 713 */     setHost(host);
/*  325: 714 */     setPort(port);
/*  326: 715 */     setUserinfo(userinfo);
/*  327:     */   }
/*  328:     */   
/*  329:     */   private void initializePath(String p_uriSpec)
/*  330:     */     throws URI.MalformedURIException
/*  331:     */   {
/*  332: 728 */     if (p_uriSpec == null) {
/*  333: 730 */       throw new MalformedURIException("Cannot initialize path from null string!");
/*  334:     */     }
/*  335: 734 */     int index = 0;
/*  336: 735 */     int start = 0;
/*  337: 736 */     int end = p_uriSpec.length();
/*  338: 737 */     char testChar = '\000';
/*  339: 740 */     while (index < end)
/*  340:     */     {
/*  341: 742 */       testChar = p_uriSpec.charAt(index);
/*  342: 744 */       if ((testChar == '?') || (testChar == '#')) {
/*  343:     */         break;
/*  344:     */       }
/*  345: 750 */       if (testChar == '%')
/*  346:     */       {
/*  347: 752 */         if ((index + 2 >= end) || (!isHex(p_uriSpec.charAt(index + 1))) || (!isHex(p_uriSpec.charAt(index + 2)))) {
/*  348: 755 */           throw new MalformedURIException(Utils.messages.createMessage("ER_PATH_CONTAINS_INVALID_ESCAPE_SEQUENCE", null));
/*  349:     */         }
/*  350:     */       }
/*  351: 759 */       else if ((!isReservedCharacter(testChar)) && (!isUnreservedCharacter(testChar))) {
/*  352: 762 */         if ('\\' != testChar) {
/*  353: 763 */           throw new MalformedURIException(Utils.messages.createMessage("ER_PATH_INVALID_CHAR", new Object[] { String.valueOf(testChar) }));
/*  354:     */         }
/*  355:     */       }
/*  356: 767 */       index++;
/*  357:     */     }
/*  358: 770 */     this.m_path = p_uriSpec.substring(start, index);
/*  359: 773 */     if (testChar == '?')
/*  360:     */     {
/*  361: 775 */       index++;
/*  362:     */       
/*  363: 777 */       start = index;
/*  364: 779 */       while (index < end)
/*  365:     */       {
/*  366: 781 */         testChar = p_uriSpec.charAt(index);
/*  367: 783 */         if (testChar == '#') {
/*  368:     */           break;
/*  369:     */         }
/*  370: 788 */         if (testChar == '%')
/*  371:     */         {
/*  372: 790 */           if ((index + 2 >= end) || (!isHex(p_uriSpec.charAt(index + 1))) || (!isHex(p_uriSpec.charAt(index + 2)))) {
/*  373: 793 */             throw new MalformedURIException("Query string contains invalid escape sequence!");
/*  374:     */           }
/*  375:     */         }
/*  376: 797 */         else if ((!isReservedCharacter(testChar)) && (!isUnreservedCharacter(testChar))) {
/*  377: 800 */           throw new MalformedURIException("Query string contains invalid character:" + testChar);
/*  378:     */         }
/*  379: 804 */         index++;
/*  380:     */       }
/*  381: 807 */       this.m_queryString = p_uriSpec.substring(start, index);
/*  382:     */     }
/*  383: 811 */     if (testChar == '#')
/*  384:     */     {
/*  385: 813 */       index++;
/*  386:     */       
/*  387: 815 */       start = index;
/*  388: 817 */       while (index < end)
/*  389:     */       {
/*  390: 819 */         testChar = p_uriSpec.charAt(index);
/*  391: 821 */         if (testChar == '%')
/*  392:     */         {
/*  393: 823 */           if ((index + 2 >= end) || (!isHex(p_uriSpec.charAt(index + 1))) || (!isHex(p_uriSpec.charAt(index + 2)))) {
/*  394: 826 */             throw new MalformedURIException("Fragment contains invalid escape sequence!");
/*  395:     */           }
/*  396:     */         }
/*  397: 830 */         else if ((!isReservedCharacter(testChar)) && (!isUnreservedCharacter(testChar))) {
/*  398: 833 */           throw new MalformedURIException("Fragment contains invalid character:" + testChar);
/*  399:     */         }
/*  400: 837 */         index++;
/*  401:     */       }
/*  402: 840 */       this.m_fragment = p_uriSpec.substring(start, index);
/*  403:     */     }
/*  404:     */   }
/*  405:     */   
/*  406:     */   public String getScheme()
/*  407:     */   {
/*  408: 851 */     return this.m_scheme;
/*  409:     */   }
/*  410:     */   
/*  411:     */   public String getSchemeSpecificPart()
/*  412:     */   {
/*  413: 863 */     StringBuffer schemespec = new StringBuffer();
/*  414: 865 */     if ((this.m_userinfo != null) || (this.m_host != null) || (this.m_port != -1)) {
/*  415: 867 */       schemespec.append("//");
/*  416:     */     }
/*  417: 870 */     if (this.m_userinfo != null)
/*  418:     */     {
/*  419: 872 */       schemespec.append(this.m_userinfo);
/*  420: 873 */       schemespec.append('@');
/*  421:     */     }
/*  422: 876 */     if (this.m_host != null) {
/*  423: 878 */       schemespec.append(this.m_host);
/*  424:     */     }
/*  425: 881 */     if (this.m_port != -1)
/*  426:     */     {
/*  427: 883 */       schemespec.append(':');
/*  428: 884 */       schemespec.append(this.m_port);
/*  429:     */     }
/*  430: 887 */     if (this.m_path != null) {
/*  431: 889 */       schemespec.append(this.m_path);
/*  432:     */     }
/*  433: 892 */     if (this.m_queryString != null)
/*  434:     */     {
/*  435: 894 */       schemespec.append('?');
/*  436: 895 */       schemespec.append(this.m_queryString);
/*  437:     */     }
/*  438: 898 */     if (this.m_fragment != null)
/*  439:     */     {
/*  440: 900 */       schemespec.append('#');
/*  441: 901 */       schemespec.append(this.m_fragment);
/*  442:     */     }
/*  443: 904 */     return schemespec.toString();
/*  444:     */   }
/*  445:     */   
/*  446:     */   public String getUserinfo()
/*  447:     */   {
/*  448: 914 */     return this.m_userinfo;
/*  449:     */   }
/*  450:     */   
/*  451:     */   public String getHost()
/*  452:     */   {
/*  453: 924 */     return this.m_host;
/*  454:     */   }
/*  455:     */   
/*  456:     */   public int getPort()
/*  457:     */   {
/*  458: 934 */     return this.m_port;
/*  459:     */   }
/*  460:     */   
/*  461:     */   public String getPath(boolean p_includeQueryString, boolean p_includeFragment)
/*  462:     */   {
/*  463: 955 */     StringBuffer pathString = new StringBuffer(this.m_path);
/*  464: 957 */     if ((p_includeQueryString) && (this.m_queryString != null))
/*  465:     */     {
/*  466: 959 */       pathString.append('?');
/*  467: 960 */       pathString.append(this.m_queryString);
/*  468:     */     }
/*  469: 963 */     if ((p_includeFragment) && (this.m_fragment != null))
/*  470:     */     {
/*  471: 965 */       pathString.append('#');
/*  472: 966 */       pathString.append(this.m_fragment);
/*  473:     */     }
/*  474: 969 */     return pathString.toString();
/*  475:     */   }
/*  476:     */   
/*  477:     */   public String getPath()
/*  478:     */   {
/*  479: 980 */     return this.m_path;
/*  480:     */   }
/*  481:     */   
/*  482:     */   public String getQueryString()
/*  483:     */   {
/*  484: 992 */     return this.m_queryString;
/*  485:     */   }
/*  486:     */   
/*  487:     */   public String getFragment()
/*  488:     */   {
/*  489:1004 */     return this.m_fragment;
/*  490:     */   }
/*  491:     */   
/*  492:     */   public void setScheme(String p_scheme)
/*  493:     */     throws URI.MalformedURIException
/*  494:     */   {
/*  495:1019 */     if (p_scheme == null) {
/*  496:1021 */       throw new MalformedURIException(Utils.messages.createMessage("ER_SCHEME_FROM_NULL_STRING", null));
/*  497:     */     }
/*  498:1024 */     if (!isConformantSchemeName(p_scheme)) {
/*  499:1026 */       throw new MalformedURIException(Utils.messages.createMessage("ER_SCHEME_NOT_CONFORMANT", null));
/*  500:     */     }
/*  501:1029 */     this.m_scheme = p_scheme.toLowerCase();
/*  502:     */   }
/*  503:     */   
/*  504:     */   public void setUserinfo(String p_userinfo)
/*  505:     */     throws URI.MalformedURIException
/*  506:     */   {
/*  507:1044 */     if (p_userinfo == null)
/*  508:     */     {
/*  509:1046 */       this.m_userinfo = null;
/*  510:     */     }
/*  511:     */     else
/*  512:     */     {
/*  513:1050 */       if (this.m_host == null) {
/*  514:1052 */         throw new MalformedURIException("Userinfo cannot be set when host is null!");
/*  515:     */       }
/*  516:1058 */       int index = 0;
/*  517:1059 */       int end = p_userinfo.length();
/*  518:1060 */       char testChar = '\000';
/*  519:1062 */       while (index < end)
/*  520:     */       {
/*  521:1064 */         testChar = p_userinfo.charAt(index);
/*  522:1066 */         if (testChar == '%')
/*  523:     */         {
/*  524:1068 */           if ((index + 2 >= end) || (!isHex(p_userinfo.charAt(index + 1))) || (!isHex(p_userinfo.charAt(index + 2)))) {
/*  525:1071 */             throw new MalformedURIException("Userinfo contains invalid escape sequence!");
/*  526:     */           }
/*  527:     */         }
/*  528:1075 */         else if ((!isUnreservedCharacter(testChar)) && (";:&=+$,".indexOf(testChar) == -1)) {
/*  529:1078 */           throw new MalformedURIException("Userinfo contains invalid character:" + testChar);
/*  530:     */         }
/*  531:1082 */         index++;
/*  532:     */       }
/*  533:     */     }
/*  534:1086 */     this.m_userinfo = p_userinfo;
/*  535:     */   }
/*  536:     */   
/*  537:     */   public void setHost(String p_host)
/*  538:     */     throws URI.MalformedURIException
/*  539:     */   {
/*  540:1101 */     if ((p_host == null) || (p_host.trim().length() == 0))
/*  541:     */     {
/*  542:1103 */       this.m_host = p_host;
/*  543:1104 */       this.m_userinfo = null;
/*  544:1105 */       this.m_port = -1;
/*  545:     */     }
/*  546:1107 */     else if (!isWellFormedAddress(p_host))
/*  547:     */     {
/*  548:1109 */       throw new MalformedURIException(Utils.messages.createMessage("ER_HOST_ADDRESS_NOT_WELLFORMED", null));
/*  549:     */     }
/*  550:1112 */     this.m_host = p_host;
/*  551:     */   }
/*  552:     */   
/*  553:     */   public void setPort(int p_port)
/*  554:     */     throws URI.MalformedURIException
/*  555:     */   {
/*  556:1129 */     if ((p_port >= 0) && (p_port <= 65535))
/*  557:     */     {
/*  558:1131 */       if (this.m_host == null) {
/*  559:1133 */         throw new MalformedURIException(Utils.messages.createMessage("ER_PORT_WHEN_HOST_NULL", null));
/*  560:     */       }
/*  561:     */     }
/*  562:1137 */     else if (p_port != -1) {
/*  563:1139 */       throw new MalformedURIException(Utils.messages.createMessage("ER_INVALID_PORT", null));
/*  564:     */     }
/*  565:1142 */     this.m_port = p_port;
/*  566:     */   }
/*  567:     */   
/*  568:     */   public void setPath(String p_path)
/*  569:     */     throws URI.MalformedURIException
/*  570:     */   {
/*  571:1162 */     if (p_path == null)
/*  572:     */     {
/*  573:1164 */       this.m_path = null;
/*  574:1165 */       this.m_queryString = null;
/*  575:1166 */       this.m_fragment = null;
/*  576:     */     }
/*  577:     */     else
/*  578:     */     {
/*  579:1170 */       initializePath(p_path);
/*  580:     */     }
/*  581:     */   }
/*  582:     */   
/*  583:     */   public void appendPath(String p_addToPath)
/*  584:     */     throws URI.MalformedURIException
/*  585:     */   {
/*  586:1190 */     if ((p_addToPath == null) || (p_addToPath.trim().length() == 0)) {
/*  587:1192 */       return;
/*  588:     */     }
/*  589:1195 */     if (!isURIString(p_addToPath)) {
/*  590:1197 */       throw new MalformedURIException(Utils.messages.createMessage("ER_PATH_INVALID_CHAR", new Object[] { p_addToPath }));
/*  591:     */     }
/*  592:1200 */     if ((this.m_path == null) || (this.m_path.trim().length() == 0))
/*  593:     */     {
/*  594:1202 */       if (p_addToPath.startsWith("/")) {
/*  595:1204 */         this.m_path = p_addToPath;
/*  596:     */       } else {
/*  597:1208 */         this.m_path = ("/" + p_addToPath);
/*  598:     */       }
/*  599:     */     }
/*  600:1211 */     else if (this.m_path.endsWith("/"))
/*  601:     */     {
/*  602:1213 */       if (p_addToPath.startsWith("/")) {
/*  603:1215 */         this.m_path = this.m_path.concat(p_addToPath.substring(1));
/*  604:     */       } else {
/*  605:1219 */         this.m_path = this.m_path.concat(p_addToPath);
/*  606:     */       }
/*  607:     */     }
/*  608:1224 */     else if (p_addToPath.startsWith("/")) {
/*  609:1226 */       this.m_path = this.m_path.concat(p_addToPath);
/*  610:     */     } else {
/*  611:1230 */       this.m_path = this.m_path.concat("/" + p_addToPath);
/*  612:     */     }
/*  613:     */   }
/*  614:     */   
/*  615:     */   public void setQueryString(String p_queryString)
/*  616:     */     throws URI.MalformedURIException
/*  617:     */   {
/*  618:1250 */     if (p_queryString == null)
/*  619:     */     {
/*  620:1252 */       this.m_queryString = null;
/*  621:     */     }
/*  622:     */     else
/*  623:     */     {
/*  624:1254 */       if (!isGenericURI()) {
/*  625:1256 */         throw new MalformedURIException("Query string can only be set for a generic URI!");
/*  626:     */       }
/*  627:1259 */       if (getPath() == null) {
/*  628:1261 */         throw new MalformedURIException("Query string cannot be set when path is null!");
/*  629:     */       }
/*  630:1264 */       if (!isURIString(p_queryString)) {
/*  631:1266 */         throw new MalformedURIException("Query string contains invalid character!");
/*  632:     */       }
/*  633:1271 */       this.m_queryString = p_queryString;
/*  634:     */     }
/*  635:     */   }
/*  636:     */   
/*  637:     */   public void setFragment(String p_fragment)
/*  638:     */     throws URI.MalformedURIException
/*  639:     */   {
/*  640:1289 */     if (p_fragment == null)
/*  641:     */     {
/*  642:1291 */       this.m_fragment = null;
/*  643:     */     }
/*  644:     */     else
/*  645:     */     {
/*  646:1293 */       if (!isGenericURI()) {
/*  647:1295 */         throw new MalformedURIException(Utils.messages.createMessage("ER_FRAG_FOR_GENERIC_URI", null));
/*  648:     */       }
/*  649:1298 */       if (getPath() == null) {
/*  650:1300 */         throw new MalformedURIException(Utils.messages.createMessage("ER_FRAG_WHEN_PATH_NULL", null));
/*  651:     */       }
/*  652:1303 */       if (!isURIString(p_fragment)) {
/*  653:1305 */         throw new MalformedURIException(Utils.messages.createMessage("ER_FRAG_INVALID_CHAR", null));
/*  654:     */       }
/*  655:1309 */       this.m_fragment = p_fragment;
/*  656:     */     }
/*  657:     */   }
/*  658:     */   
/*  659:     */   public boolean equals(Object p_test)
/*  660:     */   {
/*  661:1324 */     if ((p_test instanceof URI))
/*  662:     */     {
/*  663:1326 */       URI testURI = (URI)p_test;
/*  664:1328 */       if (((this.m_scheme == null) && (testURI.m_scheme == null)) || ((this.m_scheme != null) && (testURI.m_scheme != null) && (this.m_scheme.equals(testURI.m_scheme)) && (((this.m_userinfo == null) && (testURI.m_userinfo == null)) || ((this.m_userinfo != null) && (testURI.m_userinfo != null) && (this.m_userinfo.equals(testURI.m_userinfo)) && (((this.m_host == null) && (testURI.m_host == null)) || ((this.m_host != null) && (testURI.m_host != null) && (this.m_host.equals(testURI.m_host)) && (this.m_port == testURI.m_port) && (((this.m_path == null) && (testURI.m_path == null)) || ((this.m_path != null) && (testURI.m_path != null) && (this.m_path.equals(testURI.m_path)) && (((this.m_queryString == null) && (testURI.m_queryString == null)) || ((this.m_queryString != null) && (testURI.m_queryString != null) && (this.m_queryString.equals(testURI.m_queryString)) && (((this.m_fragment == null) && (testURI.m_fragment == null)) || ((this.m_fragment != null) && (testURI.m_fragment != null) && (this.m_fragment.equals(testURI.m_fragment)))))))))))))) {
/*  665:1336 */         return true;
/*  666:     */       }
/*  667:     */     }
/*  668:1340 */     return false;
/*  669:     */   }
/*  670:     */   
/*  671:     */   public String toString()
/*  672:     */   {
/*  673:1351 */     StringBuffer uriSpecString = new StringBuffer();
/*  674:1353 */     if (this.m_scheme != null)
/*  675:     */     {
/*  676:1355 */       uriSpecString.append(this.m_scheme);
/*  677:1356 */       uriSpecString.append(':');
/*  678:     */     }
/*  679:1359 */     uriSpecString.append(getSchemeSpecificPart());
/*  680:     */     
/*  681:1361 */     return uriSpecString.toString();
/*  682:     */   }
/*  683:     */   
/*  684:     */   public boolean isGenericURI()
/*  685:     */   {
/*  686:1376 */     return this.m_host != null;
/*  687:     */   }
/*  688:     */   
/*  689:     */   public static boolean isConformantSchemeName(String p_scheme)
/*  690:     */   {
/*  691:1391 */     if ((p_scheme == null) || (p_scheme.trim().length() == 0)) {
/*  692:1393 */       return false;
/*  693:     */     }
/*  694:1396 */     if (!isAlpha(p_scheme.charAt(0))) {
/*  695:1398 */       return false;
/*  696:     */     }
/*  697:1403 */     for (int i = 1; i < p_scheme.length(); i++)
/*  698:     */     {
/*  699:1405 */       char testChar = p_scheme.charAt(i);
/*  700:1407 */       if ((!isAlphanum(testChar)) && ("+-.".indexOf(testChar) == -1)) {
/*  701:1409 */         return false;
/*  702:     */       }
/*  703:     */     }
/*  704:1413 */     return true;
/*  705:     */   }
/*  706:     */   
/*  707:     */   public static boolean isWellFormedAddress(String p_address)
/*  708:     */   {
/*  709:1432 */     if (p_address == null) {
/*  710:1434 */       return false;
/*  711:     */     }
/*  712:1437 */     String address = p_address.trim();
/*  713:1438 */     int addrLength = address.length();
/*  714:1440 */     if ((addrLength == 0) || (addrLength > 255)) {
/*  715:1442 */       return false;
/*  716:     */     }
/*  717:1445 */     if ((address.startsWith(".")) || (address.startsWith("-"))) {
/*  718:1447 */       return false;
/*  719:     */     }
/*  720:1453 */     int index = address.lastIndexOf('.');
/*  721:1455 */     if (address.endsWith(".")) {
/*  722:1457 */       index = address.substring(0, index).lastIndexOf('.');
/*  723:     */     }
/*  724:1460 */     if ((index + 1 < addrLength) && (isDigit(p_address.charAt(index + 1))))
/*  725:     */     {
/*  726:1463 */       int numDots = 0;
/*  727:1468 */       for (int i = 0; i < addrLength; i++)
/*  728:     */       {
/*  729:1470 */         char testChar = address.charAt(i);
/*  730:1472 */         if (testChar == '.')
/*  731:     */         {
/*  732:1474 */           if ((!isDigit(address.charAt(i - 1))) || ((i + 1 < addrLength) && (!isDigit(address.charAt(i + 1))))) {
/*  733:1477 */             return false;
/*  734:     */           }
/*  735:1480 */           numDots++;
/*  736:     */         }
/*  737:1482 */         else if (!isDigit(testChar))
/*  738:     */         {
/*  739:1484 */           return false;
/*  740:     */         }
/*  741:     */       }
/*  742:1488 */       if (numDots != 3) {
/*  743:1490 */         return false;
/*  744:     */       }
/*  745:     */     }
/*  746:     */     else
/*  747:     */     {
/*  748:1500 */       for (int i = 0; i < addrLength; i++)
/*  749:     */       {
/*  750:1502 */         char testChar = address.charAt(i);
/*  751:1504 */         if (testChar == '.')
/*  752:     */         {
/*  753:1506 */           if (!isAlphanum(address.charAt(i - 1))) {
/*  754:1508 */             return false;
/*  755:     */           }
/*  756:1511 */           if ((i + 1 < addrLength) && (!isAlphanum(address.charAt(i + 1)))) {
/*  757:1513 */             return false;
/*  758:     */           }
/*  759:     */         }
/*  760:1516 */         else if ((!isAlphanum(testChar)) && (testChar != '-'))
/*  761:     */         {
/*  762:1518 */           return false;
/*  763:     */         }
/*  764:     */       }
/*  765:     */     }
/*  766:1523 */     return true;
/*  767:     */   }
/*  768:     */   
/*  769:     */   private static boolean isDigit(char p_char)
/*  770:     */   {
/*  771:1535 */     return (p_char >= '0') && (p_char <= '9');
/*  772:     */   }
/*  773:     */   
/*  774:     */   private static boolean isHex(char p_char)
/*  775:     */   {
/*  776:1548 */     return (isDigit(p_char)) || ((p_char >= 'a') && (p_char <= 'f')) || ((p_char >= 'A') && (p_char <= 'F'));
/*  777:     */   }
/*  778:     */   
/*  779:     */   private static boolean isAlpha(char p_char)
/*  780:     */   {
/*  781:1561 */     return ((p_char >= 'a') && (p_char <= 'z')) || ((p_char >= 'A') && (p_char <= 'Z'));
/*  782:     */   }
/*  783:     */   
/*  784:     */   private static boolean isAlphanum(char p_char)
/*  785:     */   {
/*  786:1574 */     return (isAlpha(p_char)) || (isDigit(p_char));
/*  787:     */   }
/*  788:     */   
/*  789:     */   private static boolean isReservedCharacter(char p_char)
/*  790:     */   {
/*  791:1587 */     return ";/?:@&=+$,".indexOf(p_char) != -1;
/*  792:     */   }
/*  793:     */   
/*  794:     */   private static boolean isUnreservedCharacter(char p_char)
/*  795:     */   {
/*  796:1599 */     return (isAlphanum(p_char)) || ("-_.!~*'() ".indexOf(p_char) != -1);
/*  797:     */   }
/*  798:     */   
/*  799:     */   private static boolean isURIString(String p_uric)
/*  800:     */   {
/*  801:1614 */     if (p_uric == null) {
/*  802:1616 */       return false;
/*  803:     */     }
/*  804:1619 */     int end = p_uric.length();
/*  805:1620 */     char testChar = '\000';
/*  806:1622 */     for (int i = 0; i < end; i++)
/*  807:     */     {
/*  808:1624 */       testChar = p_uric.charAt(i);
/*  809:1626 */       if (testChar == '%')
/*  810:     */       {
/*  811:1628 */         if ((i + 2 >= end) || (!isHex(p_uric.charAt(i + 1))) || (!isHex(p_uric.charAt(i + 2)))) {
/*  812:1631 */           return false;
/*  813:     */         }
/*  814:1635 */         i += 2;
/*  815:     */       }
/*  816:1641 */       else if ((!isReservedCharacter(testChar)) && (!isUnreservedCharacter(testChar)))
/*  817:     */       {
/*  818:1647 */         return false;
/*  819:     */       }
/*  820:     */     }
/*  821:1651 */     return true;
/*  822:     */   }
/*  823:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.serializer.utils.URI
 * JD-Core Version:    0.7.0.1
 */