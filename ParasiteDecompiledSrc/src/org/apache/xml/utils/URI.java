/*    1:     */ package org.apache.xml.utils;
/*    2:     */ 
/*    3:     */ import java.io.IOException;
/*    4:     */ import java.io.Serializable;
/*    5:     */ import org.apache.xml.res.XMLMessages;
/*    6:     */ 
/*    7:     */ public class URI
/*    8:     */   implements Serializable
/*    9:     */ {
/*   10:     */   static final long serialVersionUID = 7096266377907081897L;
/*   11:     */   private static final String RESERVED_CHARACTERS = ";/?:@&=+$,";
/*   12:     */   private static final String MARK_CHARACTERS = "-_.!~*'() ";
/*   13:     */   private static final String SCHEME_CHARACTERS = "+-.";
/*   14:     */   private static final String USERINFO_CHARACTERS = ";:&=+$,";
/*   15:     */   public URI() {}
/*   16:     */   
/*   17:     */   public static class MalformedURIException
/*   18:     */     extends IOException
/*   19:     */   {
/*   20:     */     public MalformedURIException() {}
/*   21:     */     
/*   22:     */     public MalformedURIException(String p_msg)
/*   23:     */     {
/*   24:  90 */       super();
/*   25:     */     }
/*   26:     */   }
/*   27:     */   
/*   28: 114 */   private String m_scheme = null;
/*   29: 118 */   private String m_userinfo = null;
/*   30: 122 */   private String m_host = null;
/*   31: 126 */   private int m_port = -1;
/*   32: 130 */   private String m_path = null;
/*   33: 137 */   private String m_queryString = null;
/*   34: 141 */   private String m_fragment = null;
/*   35: 144 */   private static boolean DEBUG = false;
/*   36:     */   
/*   37:     */   public URI(URI p_other)
/*   38:     */   {
/*   39: 159 */     initialize(p_other);
/*   40:     */   }
/*   41:     */   
/*   42:     */   public URI(String p_uriSpec)
/*   43:     */     throws URI.MalformedURIException
/*   44:     */   {
/*   45: 179 */     this((URI)null, p_uriSpec);
/*   46:     */   }
/*   47:     */   
/*   48:     */   public URI(URI p_base, String p_uriSpec)
/*   49:     */     throws URI.MalformedURIException
/*   50:     */   {
/*   51: 196 */     initialize(p_base, p_uriSpec);
/*   52:     */   }
/*   53:     */   
/*   54:     */   public URI(String p_scheme, String p_schemeSpecificPart)
/*   55:     */     throws URI.MalformedURIException
/*   56:     */   {
/*   57: 215 */     if ((p_scheme == null) || (p_scheme.trim().length() == 0)) {
/*   58: 217 */       throw new MalformedURIException("Cannot construct URI with null/empty scheme!");
/*   59:     */     }
/*   60: 221 */     if ((p_schemeSpecificPart == null) || (p_schemeSpecificPart.trim().length() == 0)) {
/*   61: 224 */       throw new MalformedURIException("Cannot construct URI with null/empty scheme-specific part!");
/*   62:     */     }
/*   63: 228 */     setScheme(p_scheme);
/*   64: 229 */     setPath(p_schemeSpecificPart);
/*   65:     */   }
/*   66:     */   
/*   67:     */   public URI(String p_scheme, String p_host, String p_path, String p_queryString, String p_fragment)
/*   68:     */     throws URI.MalformedURIException
/*   69:     */   {
/*   70: 256 */     this(p_scheme, null, p_host, -1, p_path, p_queryString, p_fragment);
/*   71:     */   }
/*   72:     */   
/*   73:     */   public URI(String p_scheme, String p_userinfo, String p_host, int p_port, String p_path, String p_queryString, String p_fragment)
/*   74:     */     throws URI.MalformedURIException
/*   75:     */   {
/*   76: 288 */     if ((p_scheme == null) || (p_scheme.trim().length() == 0)) {
/*   77: 290 */       throw new MalformedURIException(XMLMessages.createXMLMessage("ER_SCHEME_REQUIRED", null));
/*   78:     */     }
/*   79: 293 */     if (p_host == null)
/*   80:     */     {
/*   81: 295 */       if (p_userinfo != null) {
/*   82: 297 */         throw new MalformedURIException(XMLMessages.createXMLMessage("ER_NO_USERINFO_IF_NO_HOST", null));
/*   83:     */       }
/*   84: 301 */       if (p_port != -1) {
/*   85: 303 */         throw new MalformedURIException(XMLMessages.createXMLMessage("ER_NO_PORT_IF_NO_HOST", null));
/*   86:     */       }
/*   87:     */     }
/*   88: 308 */     if (p_path != null)
/*   89:     */     {
/*   90: 310 */       if ((p_path.indexOf('?') != -1) && (p_queryString != null)) {
/*   91: 312 */         throw new MalformedURIException(XMLMessages.createXMLMessage("ER_NO_QUERY_STRING_IN_PATH", null));
/*   92:     */       }
/*   93: 316 */       if ((p_path.indexOf('#') != -1) && (p_fragment != null)) {
/*   94: 318 */         throw new MalformedURIException(XMLMessages.createXMLMessage("ER_NO_FRAGMENT_STRING_IN_PATH", null));
/*   95:     */       }
/*   96:     */     }
/*   97: 323 */     setScheme(p_scheme);
/*   98: 324 */     setHost(p_host);
/*   99: 325 */     setPort(p_port);
/*  100: 326 */     setUserinfo(p_userinfo);
/*  101: 327 */     setPath(p_path);
/*  102: 328 */     setQueryString(p_queryString);
/*  103: 329 */     setFragment(p_fragment);
/*  104:     */   }
/*  105:     */   
/*  106:     */   private void initialize(URI p_other)
/*  107:     */   {
/*  108: 340 */     this.m_scheme = p_other.getScheme();
/*  109: 341 */     this.m_userinfo = p_other.getUserinfo();
/*  110: 342 */     this.m_host = p_other.getHost();
/*  111: 343 */     this.m_port = p_other.getPort();
/*  112: 344 */     this.m_path = p_other.getPath();
/*  113: 345 */     this.m_queryString = p_other.getQueryString();
/*  114: 346 */     this.m_fragment = p_other.getFragment();
/*  115:     */   }
/*  116:     */   
/*  117:     */   private void initialize(URI p_base, String p_uriSpec)
/*  118:     */     throws URI.MalformedURIException
/*  119:     */   {
/*  120: 369 */     if ((p_base == null) && ((p_uriSpec == null) || (p_uriSpec.trim().length() == 0))) {
/*  121: 372 */       throw new MalformedURIException(XMLMessages.createXMLMessage("ER_CANNOT_INIT_URI_EMPTY_PARMS", null));
/*  122:     */     }
/*  123: 377 */     if ((p_uriSpec == null) || (p_uriSpec.trim().length() == 0))
/*  124:     */     {
/*  125: 379 */       initialize(p_base);
/*  126:     */       
/*  127: 381 */       return;
/*  128:     */     }
/*  129: 384 */     String uriSpec = p_uriSpec.trim();
/*  130: 385 */     int uriSpecLen = uriSpec.length();
/*  131: 386 */     int index = 0;
/*  132:     */     
/*  133:     */ 
/*  134: 389 */     int colonIndex = uriSpec.indexOf(':');
/*  135: 390 */     if (colonIndex < 0)
/*  136:     */     {
/*  137: 392 */       if (p_base == null) {
/*  138: 394 */         throw new MalformedURIException(XMLMessages.createXMLMessage("ER_NO_SCHEME_IN_URI", new Object[] { uriSpec }));
/*  139:     */       }
/*  140:     */     }
/*  141:     */     else
/*  142:     */     {
/*  143: 399 */       initializeScheme(uriSpec);
/*  144: 400 */       uriSpec = uriSpec.substring(colonIndex + 1);
/*  145: 402 */       if ((this.m_scheme != null) && (p_base != null)) {
/*  146: 418 */         if ((uriSpec.startsWith("/")) || (!this.m_scheme.equals(p_base.m_scheme)) || (!p_base.getSchemeSpecificPart().startsWith("/"))) {
/*  147: 420 */           p_base = null;
/*  148:     */         }
/*  149:     */       }
/*  150: 424 */       uriSpecLen = uriSpec.length();
/*  151:     */     }
/*  152: 428 */     if (uriSpec.startsWith("//"))
/*  153:     */     {
/*  154: 430 */       index += 2;
/*  155:     */       
/*  156: 432 */       int startPos = index;
/*  157:     */       
/*  158:     */ 
/*  159: 435 */       char testChar = '\000';
/*  160: 437 */       while (index < uriSpecLen)
/*  161:     */       {
/*  162: 439 */         testChar = uriSpec.charAt(index);
/*  163: 441 */         if ((testChar == '/') || (testChar == '?') || (testChar == '#')) {
/*  164:     */           break;
/*  165:     */         }
/*  166: 446 */         index++;
/*  167:     */       }
/*  168: 451 */       if (index > startPos) {
/*  169: 453 */         initializeAuthority(uriSpec.substring(startPos, index));
/*  170:     */       } else {
/*  171: 457 */         this.m_host = "";
/*  172:     */       }
/*  173:     */     }
/*  174: 461 */     initializePath(uriSpec.substring(index));
/*  175: 468 */     if (p_base != null)
/*  176:     */     {
/*  177: 478 */       if ((this.m_path.length() == 0) && (this.m_scheme == null) && (this.m_host == null))
/*  178:     */       {
/*  179: 480 */         this.m_scheme = p_base.getScheme();
/*  180: 481 */         this.m_userinfo = p_base.getUserinfo();
/*  181: 482 */         this.m_host = p_base.getHost();
/*  182: 483 */         this.m_port = p_base.getPort();
/*  183: 484 */         this.m_path = p_base.getPath();
/*  184: 486 */         if (this.m_queryString == null) {
/*  185: 488 */           this.m_queryString = p_base.getQueryString();
/*  186:     */         }
/*  187: 491 */         return;
/*  188:     */       }
/*  189: 496 */       if (this.m_scheme == null) {
/*  190: 498 */         this.m_scheme = p_base.getScheme();
/*  191:     */       }
/*  192: 503 */       if (this.m_host == null)
/*  193:     */       {
/*  194: 505 */         this.m_userinfo = p_base.getUserinfo();
/*  195: 506 */         this.m_host = p_base.getHost();
/*  196: 507 */         this.m_port = p_base.getPort();
/*  197:     */       }
/*  198:     */       else
/*  199:     */       {
/*  200: 511 */         return;
/*  201:     */       }
/*  202: 515 */       if ((this.m_path.length() > 0) && (this.m_path.startsWith("/"))) {
/*  203: 517 */         return;
/*  204:     */       }
/*  205: 522 */       String path = new String();
/*  206: 523 */       String basePath = p_base.getPath();
/*  207: 526 */       if (basePath != null)
/*  208:     */       {
/*  209: 528 */         int lastSlash = basePath.lastIndexOf('/');
/*  210: 530 */         if (lastSlash != -1) {
/*  211: 532 */           path = basePath.substring(0, lastSlash + 1);
/*  212:     */         }
/*  213:     */       }
/*  214: 537 */       path = path.concat(this.m_path);
/*  215:     */       
/*  216:     */ 
/*  217: 540 */       index = -1;
/*  218: 542 */       while ((index = path.indexOf("/./")) != -1) {
/*  219: 544 */         path = path.substring(0, index + 1).concat(path.substring(index + 3));
/*  220:     */       }
/*  221: 548 */       if (path.endsWith("/.")) {
/*  222: 550 */         path = path.substring(0, path.length() - 1);
/*  223:     */       }
/*  224: 555 */       index = -1;
/*  225:     */       
/*  226: 557 */       int segIndex = -1;
/*  227: 558 */       String tempString = null;
/*  228: 560 */       while ((index = path.indexOf("/../")) > 0)
/*  229:     */       {
/*  230: 562 */         tempString = path.substring(0, path.indexOf("/../"));
/*  231: 563 */         segIndex = tempString.lastIndexOf('/');
/*  232: 565 */         if (segIndex != -1) {
/*  233: 567 */           if (!tempString.substring(segIndex++).equals("..")) {
/*  234: 569 */             path = path.substring(0, segIndex).concat(path.substring(index + 4));
/*  235:     */           }
/*  236:     */         }
/*  237:     */       }
/*  238: 577 */       if (path.endsWith("/.."))
/*  239:     */       {
/*  240: 579 */         tempString = path.substring(0, path.length() - 3);
/*  241: 580 */         segIndex = tempString.lastIndexOf('/');
/*  242: 582 */         if (segIndex != -1) {
/*  243: 584 */           path = path.substring(0, segIndex + 1);
/*  244:     */         }
/*  245:     */       }
/*  246: 588 */       this.m_path = path;
/*  247:     */     }
/*  248:     */   }
/*  249:     */   
/*  250:     */   private void initializeScheme(String p_uriSpec)
/*  251:     */     throws URI.MalformedURIException
/*  252:     */   {
/*  253: 603 */     int uriSpecLen = p_uriSpec.length();
/*  254: 604 */     int index = 0;
/*  255: 605 */     String scheme = null;
/*  256: 606 */     char testChar = '\000';
/*  257: 608 */     while (index < uriSpecLen)
/*  258:     */     {
/*  259: 610 */       testChar = p_uriSpec.charAt(index);
/*  260: 612 */       if ((testChar == ':') || (testChar == '/') || (testChar == '?') || (testChar == '#')) {
/*  261:     */         break;
/*  262:     */       }
/*  263: 618 */       index++;
/*  264:     */     }
/*  265: 621 */     scheme = p_uriSpec.substring(0, index);
/*  266: 623 */     if (scheme.length() == 0) {
/*  267: 625 */       throw new MalformedURIException(XMLMessages.createXMLMessage("ER_NO_SCHEME_INURI", null));
/*  268:     */     }
/*  269: 629 */     setScheme(scheme);
/*  270:     */   }
/*  271:     */   
/*  272:     */   private void initializeAuthority(String p_uriSpec)
/*  273:     */     throws URI.MalformedURIException
/*  274:     */   {
/*  275: 645 */     int index = 0;
/*  276: 646 */     int start = 0;
/*  277: 647 */     int end = p_uriSpec.length();
/*  278: 648 */     char testChar = '\000';
/*  279: 649 */     String userinfo = null;
/*  280: 652 */     if (p_uriSpec.indexOf('@', start) != -1)
/*  281:     */     {
/*  282: 654 */       while (index < end)
/*  283:     */       {
/*  284: 656 */         testChar = p_uriSpec.charAt(index);
/*  285: 658 */         if (testChar == '@') {
/*  286:     */           break;
/*  287:     */         }
/*  288: 663 */         index++;
/*  289:     */       }
/*  290: 666 */       userinfo = p_uriSpec.substring(start, index);
/*  291:     */       
/*  292: 668 */       index++;
/*  293:     */     }
/*  294: 672 */     String host = null;
/*  295:     */     
/*  296: 674 */     start = index;
/*  297: 676 */     while (index < end)
/*  298:     */     {
/*  299: 678 */       testChar = p_uriSpec.charAt(index);
/*  300: 680 */       if (testChar == ':') {
/*  301:     */         break;
/*  302:     */       }
/*  303: 685 */       index++;
/*  304:     */     }
/*  305: 688 */     host = p_uriSpec.substring(start, index);
/*  306:     */     
/*  307: 690 */     int port = -1;
/*  308: 692 */     if (host.length() > 0) {
/*  309: 696 */       if (testChar == ':')
/*  310:     */       {
/*  311: 698 */         index++;
/*  312:     */         
/*  313: 700 */         start = index;
/*  314: 702 */         while (index < end) {
/*  315: 704 */           index++;
/*  316:     */         }
/*  317: 707 */         String portStr = p_uriSpec.substring(start, index);
/*  318: 709 */         if (portStr.length() > 0)
/*  319:     */         {
/*  320: 711 */           for (int i = 0; i < portStr.length(); i++) {
/*  321: 713 */             if (!isDigit(portStr.charAt(i))) {
/*  322: 715 */               throw new MalformedURIException(portStr + " is invalid. Port should only contain digits!");
/*  323:     */             }
/*  324:     */           }
/*  325:     */           try
/*  326:     */           {
/*  327: 722 */             port = Integer.parseInt(portStr);
/*  328:     */           }
/*  329:     */           catch (NumberFormatException nfe) {}
/*  330:     */         }
/*  331:     */       }
/*  332:     */     }
/*  333: 733 */     setHost(host);
/*  334: 734 */     setPort(port);
/*  335: 735 */     setUserinfo(userinfo);
/*  336:     */   }
/*  337:     */   
/*  338:     */   private void initializePath(String p_uriSpec)
/*  339:     */     throws URI.MalformedURIException
/*  340:     */   {
/*  341: 748 */     if (p_uriSpec == null) {
/*  342: 750 */       throw new MalformedURIException("Cannot initialize path from null string!");
/*  343:     */     }
/*  344: 754 */     int index = 0;
/*  345: 755 */     int start = 0;
/*  346: 756 */     int end = p_uriSpec.length();
/*  347: 757 */     char testChar = '\000';
/*  348: 760 */     while (index < end)
/*  349:     */     {
/*  350: 762 */       testChar = p_uriSpec.charAt(index);
/*  351: 764 */       if ((testChar == '?') || (testChar == '#')) {
/*  352:     */         break;
/*  353:     */       }
/*  354: 770 */       if (testChar == '%')
/*  355:     */       {
/*  356: 772 */         if ((index + 2 >= end) || (!isHex(p_uriSpec.charAt(index + 1))) || (!isHex(p_uriSpec.charAt(index + 2)))) {
/*  357: 775 */           throw new MalformedURIException(XMLMessages.createXMLMessage("ER_PATH_CONTAINS_INVALID_ESCAPE_SEQUENCE", null));
/*  358:     */         }
/*  359:     */       }
/*  360: 779 */       else if ((!isReservedCharacter(testChar)) && (!isUnreservedCharacter(testChar))) {
/*  361: 782 */         if ('\\' != testChar) {
/*  362: 783 */           throw new MalformedURIException(XMLMessages.createXMLMessage("ER_PATH_INVALID_CHAR", new Object[] { String.valueOf(testChar) }));
/*  363:     */         }
/*  364:     */       }
/*  365: 787 */       index++;
/*  366:     */     }
/*  367: 790 */     this.m_path = p_uriSpec.substring(start, index);
/*  368: 793 */     if (testChar == '?')
/*  369:     */     {
/*  370: 795 */       index++;
/*  371:     */       
/*  372: 797 */       start = index;
/*  373: 799 */       while (index < end)
/*  374:     */       {
/*  375: 801 */         testChar = p_uriSpec.charAt(index);
/*  376: 803 */         if (testChar == '#') {
/*  377:     */           break;
/*  378:     */         }
/*  379: 808 */         if (testChar == '%')
/*  380:     */         {
/*  381: 810 */           if ((index + 2 >= end) || (!isHex(p_uriSpec.charAt(index + 1))) || (!isHex(p_uriSpec.charAt(index + 2)))) {
/*  382: 813 */             throw new MalformedURIException("Query string contains invalid escape sequence!");
/*  383:     */           }
/*  384:     */         }
/*  385: 817 */         else if ((!isReservedCharacter(testChar)) && (!isUnreservedCharacter(testChar))) {
/*  386: 820 */           throw new MalformedURIException("Query string contains invalid character:" + testChar);
/*  387:     */         }
/*  388: 824 */         index++;
/*  389:     */       }
/*  390: 827 */       this.m_queryString = p_uriSpec.substring(start, index);
/*  391:     */     }
/*  392: 831 */     if (testChar == '#')
/*  393:     */     {
/*  394: 833 */       index++;
/*  395:     */       
/*  396: 835 */       start = index;
/*  397: 837 */       while (index < end)
/*  398:     */       {
/*  399: 839 */         testChar = p_uriSpec.charAt(index);
/*  400: 841 */         if (testChar == '%')
/*  401:     */         {
/*  402: 843 */           if ((index + 2 >= end) || (!isHex(p_uriSpec.charAt(index + 1))) || (!isHex(p_uriSpec.charAt(index + 2)))) {
/*  403: 846 */             throw new MalformedURIException("Fragment contains invalid escape sequence!");
/*  404:     */           }
/*  405:     */         }
/*  406: 850 */         else if ((!isReservedCharacter(testChar)) && (!isUnreservedCharacter(testChar))) {
/*  407: 853 */           throw new MalformedURIException("Fragment contains invalid character:" + testChar);
/*  408:     */         }
/*  409: 857 */         index++;
/*  410:     */       }
/*  411: 860 */       this.m_fragment = p_uriSpec.substring(start, index);
/*  412:     */     }
/*  413:     */   }
/*  414:     */   
/*  415:     */   public String getScheme()
/*  416:     */   {
/*  417: 871 */     return this.m_scheme;
/*  418:     */   }
/*  419:     */   
/*  420:     */   public String getSchemeSpecificPart()
/*  421:     */   {
/*  422: 883 */     StringBuffer schemespec = new StringBuffer();
/*  423: 885 */     if ((this.m_userinfo != null) || (this.m_host != null) || (this.m_port != -1)) {
/*  424: 887 */       schemespec.append("//");
/*  425:     */     }
/*  426: 890 */     if (this.m_userinfo != null)
/*  427:     */     {
/*  428: 892 */       schemespec.append(this.m_userinfo);
/*  429: 893 */       schemespec.append('@');
/*  430:     */     }
/*  431: 896 */     if (this.m_host != null) {
/*  432: 898 */       schemespec.append(this.m_host);
/*  433:     */     }
/*  434: 901 */     if (this.m_port != -1)
/*  435:     */     {
/*  436: 903 */       schemespec.append(':');
/*  437: 904 */       schemespec.append(this.m_port);
/*  438:     */     }
/*  439: 907 */     if (this.m_path != null) {
/*  440: 909 */       schemespec.append(this.m_path);
/*  441:     */     }
/*  442: 912 */     if (this.m_queryString != null)
/*  443:     */     {
/*  444: 914 */       schemespec.append('?');
/*  445: 915 */       schemespec.append(this.m_queryString);
/*  446:     */     }
/*  447: 918 */     if (this.m_fragment != null)
/*  448:     */     {
/*  449: 920 */       schemespec.append('#');
/*  450: 921 */       schemespec.append(this.m_fragment);
/*  451:     */     }
/*  452: 924 */     return schemespec.toString();
/*  453:     */   }
/*  454:     */   
/*  455:     */   public String getUserinfo()
/*  456:     */   {
/*  457: 934 */     return this.m_userinfo;
/*  458:     */   }
/*  459:     */   
/*  460:     */   public String getHost()
/*  461:     */   {
/*  462: 944 */     return this.m_host;
/*  463:     */   }
/*  464:     */   
/*  465:     */   public int getPort()
/*  466:     */   {
/*  467: 954 */     return this.m_port;
/*  468:     */   }
/*  469:     */   
/*  470:     */   public String getPath(boolean p_includeQueryString, boolean p_includeFragment)
/*  471:     */   {
/*  472: 975 */     StringBuffer pathString = new StringBuffer(this.m_path);
/*  473: 977 */     if ((p_includeQueryString) && (this.m_queryString != null))
/*  474:     */     {
/*  475: 979 */       pathString.append('?');
/*  476: 980 */       pathString.append(this.m_queryString);
/*  477:     */     }
/*  478: 983 */     if ((p_includeFragment) && (this.m_fragment != null))
/*  479:     */     {
/*  480: 985 */       pathString.append('#');
/*  481: 986 */       pathString.append(this.m_fragment);
/*  482:     */     }
/*  483: 989 */     return pathString.toString();
/*  484:     */   }
/*  485:     */   
/*  486:     */   public String getPath()
/*  487:     */   {
/*  488:1000 */     return this.m_path;
/*  489:     */   }
/*  490:     */   
/*  491:     */   public String getQueryString()
/*  492:     */   {
/*  493:1012 */     return this.m_queryString;
/*  494:     */   }
/*  495:     */   
/*  496:     */   public String getFragment()
/*  497:     */   {
/*  498:1024 */     return this.m_fragment;
/*  499:     */   }
/*  500:     */   
/*  501:     */   public void setScheme(String p_scheme)
/*  502:     */     throws URI.MalformedURIException
/*  503:     */   {
/*  504:1039 */     if (p_scheme == null) {
/*  505:1041 */       throw new MalformedURIException(XMLMessages.createXMLMessage("ER_SCHEME_FROM_NULL_STRING", null));
/*  506:     */     }
/*  507:1044 */     if (!isConformantSchemeName(p_scheme)) {
/*  508:1046 */       throw new MalformedURIException(XMLMessages.createXMLMessage("ER_SCHEME_NOT_CONFORMANT", null));
/*  509:     */     }
/*  510:1049 */     this.m_scheme = p_scheme.toLowerCase();
/*  511:     */   }
/*  512:     */   
/*  513:     */   public void setUserinfo(String p_userinfo)
/*  514:     */     throws URI.MalformedURIException
/*  515:     */   {
/*  516:1064 */     if (p_userinfo == null)
/*  517:     */     {
/*  518:1066 */       this.m_userinfo = null;
/*  519:     */     }
/*  520:     */     else
/*  521:     */     {
/*  522:1070 */       if (this.m_host == null) {
/*  523:1072 */         throw new MalformedURIException("Userinfo cannot be set when host is null!");
/*  524:     */       }
/*  525:1078 */       int index = 0;
/*  526:1079 */       int end = p_userinfo.length();
/*  527:1080 */       char testChar = '\000';
/*  528:1082 */       while (index < end)
/*  529:     */       {
/*  530:1084 */         testChar = p_userinfo.charAt(index);
/*  531:1086 */         if (testChar == '%')
/*  532:     */         {
/*  533:1088 */           if ((index + 2 >= end) || (!isHex(p_userinfo.charAt(index + 1))) || (!isHex(p_userinfo.charAt(index + 2)))) {
/*  534:1091 */             throw new MalformedURIException("Userinfo contains invalid escape sequence!");
/*  535:     */           }
/*  536:     */         }
/*  537:1095 */         else if ((!isUnreservedCharacter(testChar)) && (";:&=+$,".indexOf(testChar) == -1)) {
/*  538:1098 */           throw new MalformedURIException("Userinfo contains invalid character:" + testChar);
/*  539:     */         }
/*  540:1102 */         index++;
/*  541:     */       }
/*  542:     */     }
/*  543:1106 */     this.m_userinfo = p_userinfo;
/*  544:     */   }
/*  545:     */   
/*  546:     */   public void setHost(String p_host)
/*  547:     */     throws URI.MalformedURIException
/*  548:     */   {
/*  549:1121 */     if ((p_host == null) || (p_host.trim().length() == 0))
/*  550:     */     {
/*  551:1123 */       this.m_host = p_host;
/*  552:1124 */       this.m_userinfo = null;
/*  553:1125 */       this.m_port = -1;
/*  554:     */     }
/*  555:1127 */     else if (!isWellFormedAddress(p_host))
/*  556:     */     {
/*  557:1129 */       throw new MalformedURIException(XMLMessages.createXMLMessage("ER_HOST_ADDRESS_NOT_WELLFORMED", null));
/*  558:     */     }
/*  559:1132 */     this.m_host = p_host;
/*  560:     */   }
/*  561:     */   
/*  562:     */   public void setPort(int p_port)
/*  563:     */     throws URI.MalformedURIException
/*  564:     */   {
/*  565:1149 */     if ((p_port >= 0) && (p_port <= 65535))
/*  566:     */     {
/*  567:1151 */       if (this.m_host == null) {
/*  568:1153 */         throw new MalformedURIException(XMLMessages.createXMLMessage("ER_PORT_WHEN_HOST_NULL", null));
/*  569:     */       }
/*  570:     */     }
/*  571:1157 */     else if (p_port != -1) {
/*  572:1159 */       throw new MalformedURIException(XMLMessages.createXMLMessage("ER_INVALID_PORT", null));
/*  573:     */     }
/*  574:1162 */     this.m_port = p_port;
/*  575:     */   }
/*  576:     */   
/*  577:     */   public void setPath(String p_path)
/*  578:     */     throws URI.MalformedURIException
/*  579:     */   {
/*  580:1182 */     if (p_path == null)
/*  581:     */     {
/*  582:1184 */       this.m_path = null;
/*  583:1185 */       this.m_queryString = null;
/*  584:1186 */       this.m_fragment = null;
/*  585:     */     }
/*  586:     */     else
/*  587:     */     {
/*  588:1190 */       initializePath(p_path);
/*  589:     */     }
/*  590:     */   }
/*  591:     */   
/*  592:     */   public void appendPath(String p_addToPath)
/*  593:     */     throws URI.MalformedURIException
/*  594:     */   {
/*  595:1210 */     if ((p_addToPath == null) || (p_addToPath.trim().length() == 0)) {
/*  596:1212 */       return;
/*  597:     */     }
/*  598:1215 */     if (!isURIString(p_addToPath)) {
/*  599:1217 */       throw new MalformedURIException(XMLMessages.createXMLMessage("ER_PATH_INVALID_CHAR", new Object[] { p_addToPath }));
/*  600:     */     }
/*  601:1220 */     if ((this.m_path == null) || (this.m_path.trim().length() == 0))
/*  602:     */     {
/*  603:1222 */       if (p_addToPath.startsWith("/")) {
/*  604:1224 */         this.m_path = p_addToPath;
/*  605:     */       } else {
/*  606:1228 */         this.m_path = ("/" + p_addToPath);
/*  607:     */       }
/*  608:     */     }
/*  609:1231 */     else if (this.m_path.endsWith("/"))
/*  610:     */     {
/*  611:1233 */       if (p_addToPath.startsWith("/")) {
/*  612:1235 */         this.m_path = this.m_path.concat(p_addToPath.substring(1));
/*  613:     */       } else {
/*  614:1239 */         this.m_path = this.m_path.concat(p_addToPath);
/*  615:     */       }
/*  616:     */     }
/*  617:1244 */     else if (p_addToPath.startsWith("/")) {
/*  618:1246 */       this.m_path = this.m_path.concat(p_addToPath);
/*  619:     */     } else {
/*  620:1250 */       this.m_path = this.m_path.concat("/" + p_addToPath);
/*  621:     */     }
/*  622:     */   }
/*  623:     */   
/*  624:     */   public void setQueryString(String p_queryString)
/*  625:     */     throws URI.MalformedURIException
/*  626:     */   {
/*  627:1270 */     if (p_queryString == null)
/*  628:     */     {
/*  629:1272 */       this.m_queryString = null;
/*  630:     */     }
/*  631:     */     else
/*  632:     */     {
/*  633:1274 */       if (!isGenericURI()) {
/*  634:1276 */         throw new MalformedURIException("Query string can only be set for a generic URI!");
/*  635:     */       }
/*  636:1279 */       if (getPath() == null) {
/*  637:1281 */         throw new MalformedURIException("Query string cannot be set when path is null!");
/*  638:     */       }
/*  639:1284 */       if (!isURIString(p_queryString)) {
/*  640:1286 */         throw new MalformedURIException("Query string contains invalid character!");
/*  641:     */       }
/*  642:1291 */       this.m_queryString = p_queryString;
/*  643:     */     }
/*  644:     */   }
/*  645:     */   
/*  646:     */   public void setFragment(String p_fragment)
/*  647:     */     throws URI.MalformedURIException
/*  648:     */   {
/*  649:1309 */     if (p_fragment == null)
/*  650:     */     {
/*  651:1311 */       this.m_fragment = null;
/*  652:     */     }
/*  653:     */     else
/*  654:     */     {
/*  655:1313 */       if (!isGenericURI()) {
/*  656:1315 */         throw new MalformedURIException(XMLMessages.createXMLMessage("ER_FRAG_FOR_GENERIC_URI", null));
/*  657:     */       }
/*  658:1318 */       if (getPath() == null) {
/*  659:1320 */         throw new MalformedURIException(XMLMessages.createXMLMessage("ER_FRAG_WHEN_PATH_NULL", null));
/*  660:     */       }
/*  661:1323 */       if (!isURIString(p_fragment)) {
/*  662:1325 */         throw new MalformedURIException(XMLMessages.createXMLMessage("ER_FRAG_INVALID_CHAR", null));
/*  663:     */       }
/*  664:1329 */       this.m_fragment = p_fragment;
/*  665:     */     }
/*  666:     */   }
/*  667:     */   
/*  668:     */   public boolean equals(Object p_test)
/*  669:     */   {
/*  670:1344 */     if ((p_test instanceof URI))
/*  671:     */     {
/*  672:1346 */       URI testURI = (URI)p_test;
/*  673:1348 */       if (((this.m_scheme == null) && (testURI.m_scheme == null)) || ((this.m_scheme != null) && (testURI.m_scheme != null) && (this.m_scheme.equals(testURI.m_scheme)) && (((this.m_userinfo == null) && (testURI.m_userinfo == null)) || ((this.m_userinfo != null) && (testURI.m_userinfo != null) && (this.m_userinfo.equals(testURI.m_userinfo)) && (((this.m_host == null) && (testURI.m_host == null)) || ((this.m_host != null) && (testURI.m_host != null) && (this.m_host.equals(testURI.m_host)) && (this.m_port == testURI.m_port) && (((this.m_path == null) && (testURI.m_path == null)) || ((this.m_path != null) && (testURI.m_path != null) && (this.m_path.equals(testURI.m_path)) && (((this.m_queryString == null) && (testURI.m_queryString == null)) || ((this.m_queryString != null) && (testURI.m_queryString != null) && (this.m_queryString.equals(testURI.m_queryString)) && (((this.m_fragment == null) && (testURI.m_fragment == null)) || ((this.m_fragment != null) && (testURI.m_fragment != null) && (this.m_fragment.equals(testURI.m_fragment)))))))))))))) {
/*  674:1356 */         return true;
/*  675:     */       }
/*  676:     */     }
/*  677:1360 */     return false;
/*  678:     */   }
/*  679:     */   
/*  680:     */   public String toString()
/*  681:     */   {
/*  682:1371 */     StringBuffer uriSpecString = new StringBuffer();
/*  683:1373 */     if (this.m_scheme != null)
/*  684:     */     {
/*  685:1375 */       uriSpecString.append(this.m_scheme);
/*  686:1376 */       uriSpecString.append(':');
/*  687:     */     }
/*  688:1379 */     uriSpecString.append(getSchemeSpecificPart());
/*  689:     */     
/*  690:1381 */     return uriSpecString.toString();
/*  691:     */   }
/*  692:     */   
/*  693:     */   public boolean isGenericURI()
/*  694:     */   {
/*  695:1396 */     return this.m_host != null;
/*  696:     */   }
/*  697:     */   
/*  698:     */   public static boolean isConformantSchemeName(String p_scheme)
/*  699:     */   {
/*  700:1411 */     if ((p_scheme == null) || (p_scheme.trim().length() == 0)) {
/*  701:1413 */       return false;
/*  702:     */     }
/*  703:1416 */     if (!isAlpha(p_scheme.charAt(0))) {
/*  704:1418 */       return false;
/*  705:     */     }
/*  706:1423 */     for (int i = 1; i < p_scheme.length(); i++)
/*  707:     */     {
/*  708:1425 */       char testChar = p_scheme.charAt(i);
/*  709:1427 */       if ((!isAlphanum(testChar)) && ("+-.".indexOf(testChar) == -1)) {
/*  710:1429 */         return false;
/*  711:     */       }
/*  712:     */     }
/*  713:1433 */     return true;
/*  714:     */   }
/*  715:     */   
/*  716:     */   public static boolean isWellFormedAddress(String p_address)
/*  717:     */   {
/*  718:1452 */     if (p_address == null) {
/*  719:1454 */       return false;
/*  720:     */     }
/*  721:1457 */     String address = p_address.trim();
/*  722:1458 */     int addrLength = address.length();
/*  723:1460 */     if ((addrLength == 0) || (addrLength > 255)) {
/*  724:1462 */       return false;
/*  725:     */     }
/*  726:1465 */     if ((address.startsWith(".")) || (address.startsWith("-"))) {
/*  727:1467 */       return false;
/*  728:     */     }
/*  729:1473 */     int index = address.lastIndexOf('.');
/*  730:1475 */     if (address.endsWith(".")) {
/*  731:1477 */       index = address.substring(0, index).lastIndexOf('.');
/*  732:     */     }
/*  733:1480 */     if ((index + 1 < addrLength) && (isDigit(p_address.charAt(index + 1))))
/*  734:     */     {
/*  735:1483 */       int numDots = 0;
/*  736:1488 */       for (int i = 0; i < addrLength; i++)
/*  737:     */       {
/*  738:1490 */         char testChar = address.charAt(i);
/*  739:1492 */         if (testChar == '.')
/*  740:     */         {
/*  741:1494 */           if ((!isDigit(address.charAt(i - 1))) || ((i + 1 < addrLength) && (!isDigit(address.charAt(i + 1))))) {
/*  742:1497 */             return false;
/*  743:     */           }
/*  744:1500 */           numDots++;
/*  745:     */         }
/*  746:1502 */         else if (!isDigit(testChar))
/*  747:     */         {
/*  748:1504 */           return false;
/*  749:     */         }
/*  750:     */       }
/*  751:1508 */       if (numDots != 3) {
/*  752:1510 */         return false;
/*  753:     */       }
/*  754:     */     }
/*  755:     */     else
/*  756:     */     {
/*  757:1520 */       for (int i = 0; i < addrLength; i++)
/*  758:     */       {
/*  759:1522 */         char testChar = address.charAt(i);
/*  760:1524 */         if (testChar == '.')
/*  761:     */         {
/*  762:1526 */           if (!isAlphanum(address.charAt(i - 1))) {
/*  763:1528 */             return false;
/*  764:     */           }
/*  765:1531 */           if ((i + 1 < addrLength) && (!isAlphanum(address.charAt(i + 1)))) {
/*  766:1533 */             return false;
/*  767:     */           }
/*  768:     */         }
/*  769:1536 */         else if ((!isAlphanum(testChar)) && (testChar != '-'))
/*  770:     */         {
/*  771:1538 */           return false;
/*  772:     */         }
/*  773:     */       }
/*  774:     */     }
/*  775:1543 */     return true;
/*  776:     */   }
/*  777:     */   
/*  778:     */   private static boolean isDigit(char p_char)
/*  779:     */   {
/*  780:1555 */     return (p_char >= '0') && (p_char <= '9');
/*  781:     */   }
/*  782:     */   
/*  783:     */   private static boolean isHex(char p_char)
/*  784:     */   {
/*  785:1568 */     return (isDigit(p_char)) || ((p_char >= 'a') && (p_char <= 'f')) || ((p_char >= 'A') && (p_char <= 'F'));
/*  786:     */   }
/*  787:     */   
/*  788:     */   private static boolean isAlpha(char p_char)
/*  789:     */   {
/*  790:1581 */     return ((p_char >= 'a') && (p_char <= 'z')) || ((p_char >= 'A') && (p_char <= 'Z'));
/*  791:     */   }
/*  792:     */   
/*  793:     */   private static boolean isAlphanum(char p_char)
/*  794:     */   {
/*  795:1594 */     return (isAlpha(p_char)) || (isDigit(p_char));
/*  796:     */   }
/*  797:     */   
/*  798:     */   private static boolean isReservedCharacter(char p_char)
/*  799:     */   {
/*  800:1607 */     return ";/?:@&=+$,".indexOf(p_char) != -1;
/*  801:     */   }
/*  802:     */   
/*  803:     */   private static boolean isUnreservedCharacter(char p_char)
/*  804:     */   {
/*  805:1619 */     return (isAlphanum(p_char)) || ("-_.!~*'() ".indexOf(p_char) != -1);
/*  806:     */   }
/*  807:     */   
/*  808:     */   private static boolean isURIString(String p_uric)
/*  809:     */   {
/*  810:1634 */     if (p_uric == null) {
/*  811:1636 */       return false;
/*  812:     */     }
/*  813:1639 */     int end = p_uric.length();
/*  814:1640 */     char testChar = '\000';
/*  815:1642 */     for (int i = 0; i < end; i++)
/*  816:     */     {
/*  817:1644 */       testChar = p_uric.charAt(i);
/*  818:1646 */       if (testChar == '%')
/*  819:     */       {
/*  820:1648 */         if ((i + 2 >= end) || (!isHex(p_uric.charAt(i + 1))) || (!isHex(p_uric.charAt(i + 2)))) {
/*  821:1651 */           return false;
/*  822:     */         }
/*  823:1655 */         i += 2;
/*  824:     */       }
/*  825:1661 */       else if ((!isReservedCharacter(testChar)) && (!isUnreservedCharacter(testChar)))
/*  826:     */       {
/*  827:1667 */         return false;
/*  828:     */       }
/*  829:     */     }
/*  830:1671 */     return true;
/*  831:     */   }
/*  832:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.utils.URI
 * JD-Core Version:    0.7.0.1
 */