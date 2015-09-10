/*    1:     */ package org.apache.xalan.xslt;
/*    2:     */ 
/*    3:     */ import java.io.File;
/*    4:     */ import java.io.FileWriter;
/*    5:     */ import java.io.PrintStream;
/*    6:     */ import java.io.PrintWriter;
/*    7:     */ import java.lang.reflect.Field;
/*    8:     */ import java.lang.reflect.Method;
/*    9:     */ import java.util.Enumeration;
/*   10:     */ import java.util.Hashtable;
/*   11:     */ import java.util.StringTokenizer;
/*   12:     */ import java.util.Vector;
/*   13:     */ import org.w3c.dom.Document;
/*   14:     */ import org.w3c.dom.Element;
/*   15:     */ import org.w3c.dom.Node;
/*   16:     */ import org.xml.sax.Attributes;
/*   17:     */ 
/*   18:     */ public class EnvironmentCheck
/*   19:     */ {
/*   20:     */   public static final String ERROR = "ERROR.";
/*   21:     */   public static final String WARNING = "WARNING.";
/*   22:     */   public static final String ERROR_FOUND = "At least one error was found!";
/*   23:     */   public static final String VERSION = "version.";
/*   24:     */   public static final String FOUNDCLASSES = "foundclasses.";
/*   25:     */   public static final String CLASS_PRESENT = "present-unknown-version";
/*   26:     */   public static final String CLASS_NOTPRESENT = "not-present";
/*   27:     */   
/*   28:     */   public static void main(String[] args)
/*   29:     */   {
/*   30: 104 */     PrintWriter sendOutputTo = new PrintWriter(System.out, true);
/*   31: 107 */     for (int i = 0; i < args.length; i++) {
/*   32: 109 */       if ("-out".equalsIgnoreCase(args[i]))
/*   33:     */       {
/*   34: 111 */         i++;
/*   35: 113 */         if (i < args.length) {
/*   36:     */           try
/*   37:     */           {
/*   38: 117 */             sendOutputTo = new PrintWriter(new FileWriter(args[i], true));
/*   39:     */           }
/*   40:     */           catch (Exception e)
/*   41:     */           {
/*   42: 121 */             System.err.println("# WARNING: -out " + args[i] + " threw " + e.toString());
/*   43:     */           }
/*   44:     */         } else {
/*   45: 127 */           System.err.println("# WARNING: -out argument should have a filename, output sent to console");
/*   46:     */         }
/*   47:     */       }
/*   48:     */     }
/*   49: 133 */     EnvironmentCheck app = new EnvironmentCheck();
/*   50: 134 */     app.checkEnvironment(sendOutputTo);
/*   51:     */   }
/*   52:     */   
/*   53:     */   public boolean checkEnvironment(PrintWriter pw)
/*   54:     */   {
/*   55: 165 */     if (null != pw) {
/*   56: 166 */       this.outWriter = pw;
/*   57:     */     }
/*   58: 169 */     Hashtable hash = getEnvironmentHash();
/*   59:     */     
/*   60:     */ 
/*   61: 172 */     boolean environmentHasErrors = writeEnvironmentReport(hash);
/*   62: 174 */     if (environmentHasErrors)
/*   63:     */     {
/*   64: 178 */       logMsg("# WARNING: Potential problems found in your environment!");
/*   65: 179 */       logMsg("#    Check any 'ERROR' items above against the Xalan FAQs");
/*   66: 180 */       logMsg("#    to correct potential problems with your classes/jars");
/*   67: 181 */       logMsg("#    http://xml.apache.org/xalan-j/faq.html");
/*   68: 182 */       if (null != this.outWriter) {
/*   69: 183 */         this.outWriter.flush();
/*   70:     */       }
/*   71: 184 */       return false;
/*   72:     */     }
/*   73: 188 */     logMsg("# YAHOO! Your environment seems to be OK.");
/*   74: 189 */     if (null != this.outWriter) {
/*   75: 190 */       this.outWriter.flush();
/*   76:     */     }
/*   77: 191 */     return true;
/*   78:     */   }
/*   79:     */   
/*   80:     */   public Hashtable getEnvironmentHash()
/*   81:     */   {
/*   82: 218 */     Hashtable hash = new Hashtable();
/*   83:     */     
/*   84:     */ 
/*   85:     */ 
/*   86:     */ 
/*   87: 223 */     checkJAXPVersion(hash);
/*   88: 224 */     checkProcessorVersion(hash);
/*   89: 225 */     checkParserVersion(hash);
/*   90: 226 */     checkAntVersion(hash);
/*   91: 227 */     checkDOMVersion(hash);
/*   92: 228 */     checkSAXVersion(hash);
/*   93: 229 */     checkSystemProperties(hash);
/*   94:     */     
/*   95: 231 */     return hash;
/*   96:     */   }
/*   97:     */   
/*   98:     */   protected boolean writeEnvironmentReport(Hashtable h)
/*   99:     */   {
/*  100: 251 */     if (null == h)
/*  101:     */     {
/*  102: 253 */       logMsg("# ERROR: writeEnvironmentReport called with null Hashtable");
/*  103: 254 */       return false;
/*  104:     */     }
/*  105: 257 */     boolean errors = false;
/*  106:     */     
/*  107: 259 */     logMsg("#---- BEGIN writeEnvironmentReport($Revision$): Useful stuff found: ----");
/*  108:     */     
/*  109:     */ 
/*  110:     */ 
/*  111: 263 */     Enumeration keys = h.keys();
/*  112: 264 */     while (keys.hasMoreElements())
/*  113:     */     {
/*  114: 268 */       Object key = keys.nextElement();
/*  115: 269 */       String keyStr = (String)key;
/*  116:     */       try
/*  117:     */       {
/*  118: 273 */         if (keyStr.startsWith("foundclasses."))
/*  119:     */         {
/*  120: 275 */           Vector v = (Vector)h.get(keyStr);
/*  121: 276 */           errors |= logFoundJars(v, keyStr);
/*  122:     */         }
/*  123:     */         else
/*  124:     */         {
/*  125: 285 */           if (keyStr.startsWith("ERROR.")) {
/*  126: 287 */             errors = true;
/*  127:     */           }
/*  128: 289 */           logMsg(keyStr + "=" + h.get(keyStr));
/*  129:     */         }
/*  130:     */       }
/*  131:     */       catch (Exception e)
/*  132:     */       {
/*  133: 294 */         logMsg("Reading-" + key + "= threw: " + e.toString());
/*  134:     */       }
/*  135:     */     }
/*  136: 298 */     logMsg("#----- END writeEnvironmentReport: Useful properties found: -----");
/*  137:     */     
/*  138:     */ 
/*  139: 301 */     return errors;
/*  140:     */   }
/*  141:     */   
/*  142: 326 */   public String[] jarNames = { "xalan.jar", "xalansamples.jar", "xalanj1compat.jar", "xalanservlet.jar", "serializer.jar", "xerces.jar", "xercesImpl.jar", "testxsl.jar", "crimson.jar", "lotusxsl.jar", "jaxp.jar", "parser.jar", "dom.jar", "sax.jar", "xml.jar", "xml-apis.jar", "xsltc.jar" };
/*  143:     */   
/*  144:     */   protected boolean logFoundJars(Vector v, String desc)
/*  145:     */   {
/*  146: 356 */     if ((null == v) || (v.size() < 1)) {
/*  147: 357 */       return false;
/*  148:     */     }
/*  149: 359 */     boolean errors = false;
/*  150:     */     
/*  151: 361 */     logMsg("#---- BEGIN Listing XML-related jars in: " + desc + " ----");
/*  152: 363 */     for (int i = 0; i < v.size(); i++)
/*  153:     */     {
/*  154: 365 */       Hashtable subhash = (Hashtable)v.elementAt(i);
/*  155:     */       
/*  156: 367 */       Enumeration keys = subhash.keys();
/*  157: 368 */       while (keys.hasMoreElements())
/*  158:     */       {
/*  159: 372 */         Object key = keys.nextElement();
/*  160: 373 */         String keyStr = (String)key;
/*  161:     */         try
/*  162:     */         {
/*  163: 376 */           if (keyStr.startsWith("ERROR.")) {
/*  164: 378 */             errors = true;
/*  165:     */           }
/*  166: 380 */           logMsg(keyStr + "=" + subhash.get(keyStr));
/*  167:     */         }
/*  168:     */         catch (Exception e)
/*  169:     */         {
/*  170: 385 */           errors = true;
/*  171: 386 */           logMsg("Reading-" + key + "= threw: " + e.toString());
/*  172:     */         }
/*  173:     */       }
/*  174:     */     }
/*  175: 391 */     logMsg("#----- END Listing XML-related jars in: " + desc + " -----");
/*  176:     */     
/*  177: 393 */     return errors;
/*  178:     */   }
/*  179:     */   
/*  180:     */   public void appendEnvironmentReport(Node container, Document factory, Hashtable h)
/*  181:     */   {
/*  182: 411 */     if ((null == container) || (null == factory)) {
/*  183: 413 */       return;
/*  184:     */     }
/*  185:     */     try
/*  186:     */     {
/*  187: 418 */       Element envCheckNode = factory.createElement("EnvironmentCheck");
/*  188: 419 */       envCheckNode.setAttribute("version", "$Revision$");
/*  189: 420 */       container.appendChild(envCheckNode);
/*  190: 422 */       if (null == h)
/*  191:     */       {
/*  192: 424 */         Element statusNode = factory.createElement("status");
/*  193: 425 */         statusNode.setAttribute("result", "ERROR");
/*  194: 426 */         statusNode.appendChild(factory.createTextNode("appendEnvironmentReport called with null Hashtable!"));
/*  195: 427 */         envCheckNode.appendChild(statusNode);
/*  196: 428 */         return;
/*  197:     */       }
/*  198: 431 */       boolean errors = false;
/*  199:     */       
/*  200: 433 */       Element hashNode = factory.createElement("environment");
/*  201: 434 */       envCheckNode.appendChild(hashNode);
/*  202:     */       
/*  203: 436 */       Enumeration keys = h.keys();
/*  204: 437 */       while (keys.hasMoreElements())
/*  205:     */       {
/*  206: 441 */         Object key = keys.nextElement();
/*  207: 442 */         String keyStr = (String)key;
/*  208:     */         try
/*  209:     */         {
/*  210: 446 */           if (keyStr.startsWith("foundclasses."))
/*  211:     */           {
/*  212: 448 */             Vector v = (Vector)h.get(keyStr);
/*  213:     */             
/*  214: 450 */             errors |= appendFoundJars(hashNode, factory, v, keyStr);
/*  215:     */           }
/*  216:     */           else
/*  217:     */           {
/*  218: 459 */             if (keyStr.startsWith("ERROR.")) {
/*  219: 461 */               errors = true;
/*  220:     */             }
/*  221: 463 */             Element node = factory.createElement("item");
/*  222: 464 */             node.setAttribute("key", keyStr);
/*  223: 465 */             node.appendChild(factory.createTextNode((String)h.get(keyStr)));
/*  224: 466 */             hashNode.appendChild(node);
/*  225:     */           }
/*  226:     */         }
/*  227:     */         catch (Exception e)
/*  228:     */         {
/*  229: 471 */           errors = true;
/*  230: 472 */           Element node = factory.createElement("item");
/*  231: 473 */           node.setAttribute("key", keyStr);
/*  232: 474 */           node.appendChild(factory.createTextNode("ERROR. Reading " + key + " threw: " + e.toString()));
/*  233: 475 */           hashNode.appendChild(node);
/*  234:     */         }
/*  235:     */       }
/*  236: 479 */       Element statusNode = factory.createElement("status");
/*  237: 480 */       statusNode.setAttribute("result", errors ? "ERROR" : "OK");
/*  238: 481 */       envCheckNode.appendChild(statusNode);
/*  239:     */     }
/*  240:     */     catch (Exception e2)
/*  241:     */     {
/*  242: 485 */       System.err.println("appendEnvironmentReport threw: " + e2.toString());
/*  243: 486 */       e2.printStackTrace();
/*  244:     */     }
/*  245:     */   }
/*  246:     */   
/*  247:     */   protected boolean appendFoundJars(Node container, Document factory, Vector v, String desc)
/*  248:     */   {
/*  249: 509 */     if ((null == v) || (v.size() < 1)) {
/*  250: 510 */       return false;
/*  251:     */     }
/*  252: 512 */     boolean errors = false;
/*  253: 514 */     for (int i = 0; i < v.size(); i++)
/*  254:     */     {
/*  255: 516 */       Hashtable subhash = (Hashtable)v.elementAt(i);
/*  256:     */       
/*  257: 518 */       Enumeration keys = subhash.keys();
/*  258: 519 */       while (keys.hasMoreElements())
/*  259:     */       {
/*  260: 523 */         Object key = keys.nextElement();
/*  261:     */         try
/*  262:     */         {
/*  263: 526 */           String keyStr = (String)key;
/*  264: 527 */           if (keyStr.startsWith("ERROR.")) {
/*  265: 529 */             errors = true;
/*  266:     */           }
/*  267: 531 */           Element node = factory.createElement("foundJar");
/*  268: 532 */           node.setAttribute("name", keyStr.substring(0, keyStr.indexOf("-")));
/*  269: 533 */           node.setAttribute("desc", keyStr.substring(keyStr.indexOf("-") + 1));
/*  270: 534 */           node.appendChild(factory.createTextNode((String)subhash.get(keyStr)));
/*  271: 535 */           container.appendChild(node);
/*  272:     */         }
/*  273:     */         catch (Exception e)
/*  274:     */         {
/*  275: 539 */           errors = true;
/*  276: 540 */           Element node = factory.createElement("foundJar");
/*  277: 541 */           node.appendChild(factory.createTextNode("ERROR. Reading " + key + " threw: " + e.toString()));
/*  278: 542 */           container.appendChild(node);
/*  279:     */         }
/*  280:     */       }
/*  281:     */     }
/*  282: 546 */     return errors;
/*  283:     */   }
/*  284:     */   
/*  285:     */   protected void checkSystemProperties(Hashtable h)
/*  286:     */   {
/*  287: 565 */     if (null == h) {
/*  288: 566 */       h = new Hashtable();
/*  289:     */     }
/*  290:     */     try
/*  291:     */     {
/*  292: 571 */       String javaVersion = System.getProperty("java.version");
/*  293:     */       
/*  294: 573 */       h.put("java.version", javaVersion);
/*  295:     */     }
/*  296:     */     catch (SecurityException se)
/*  297:     */     {
/*  298: 579 */       h.put("java.version", "WARNING: SecurityException thrown accessing system version properties");
/*  299:     */     }
/*  300:     */     try
/*  301:     */     {
/*  302: 590 */       String cp = System.getProperty("java.class.path");
/*  303:     */       
/*  304: 592 */       h.put("java.class.path", cp);
/*  305:     */       
/*  306: 594 */       Vector classpathJars = checkPathForJars(cp, this.jarNames);
/*  307: 596 */       if (null != classpathJars) {
/*  308: 597 */         h.put("foundclasses.java.class.path", classpathJars);
/*  309:     */       }
/*  310: 600 */       String othercp = System.getProperty("sun.boot.class.path");
/*  311: 602 */       if (null != othercp)
/*  312:     */       {
/*  313: 604 */         h.put("sun.boot.class.path", othercp);
/*  314:     */         
/*  315: 606 */         classpathJars = checkPathForJars(othercp, this.jarNames);
/*  316: 608 */         if (null != classpathJars) {
/*  317: 609 */           h.put("foundclasses.sun.boot.class.path", classpathJars);
/*  318:     */         }
/*  319:     */       }
/*  320: 614 */       othercp = System.getProperty("java.ext.dirs");
/*  321: 616 */       if (null != othercp)
/*  322:     */       {
/*  323: 618 */         h.put("java.ext.dirs", othercp);
/*  324:     */         
/*  325: 620 */         classpathJars = checkPathForJars(othercp, this.jarNames);
/*  326: 622 */         if (null != classpathJars) {
/*  327: 623 */           h.put("foundclasses.java.ext.dirs", classpathJars);
/*  328:     */         }
/*  329:     */       }
/*  330:     */     }
/*  331:     */     catch (SecurityException se2)
/*  332:     */     {
/*  333: 633 */       h.put("java.class.path", "WARNING: SecurityException thrown accessing system classpath properties");
/*  334:     */     }
/*  335:     */   }
/*  336:     */   
/*  337:     */   protected Vector checkPathForJars(String cp, String[] jars)
/*  338:     */   {
/*  339: 659 */     if ((null == cp) || (null == jars) || (0 == cp.length()) || (0 == jars.length)) {
/*  340: 661 */       return null;
/*  341:     */     }
/*  342: 663 */     Vector v = new Vector();
/*  343: 664 */     StringTokenizer st = new StringTokenizer(cp, File.pathSeparator);
/*  344:     */     int i;
/*  345: 666 */     for (; st.hasMoreTokens(); i < jars.length)
/*  346:     */     {
/*  347: 670 */       String filename = st.nextToken();
/*  348:     */       
/*  349: 672 */       i = 0; continue;
/*  350: 674 */       if (filename.indexOf(jars[i]) > -1)
/*  351:     */       {
/*  352: 676 */         File f = new File(filename);
/*  353: 678 */         if (f.exists())
/*  354:     */         {
/*  355:     */           try
/*  356:     */           {
/*  357: 685 */             Hashtable h = new Hashtable(2);
/*  358:     */             
/*  359: 687 */             h.put(jars[i] + "-path", f.getAbsolutePath());
/*  360: 694 */             if (!"xalan.jar".equalsIgnoreCase(jars[i])) {
/*  361: 695 */               h.put(jars[i] + "-apparent.version", getApparentVersion(jars[i], f.length()));
/*  362:     */             }
/*  363: 698 */             v.addElement(h);
/*  364:     */           }
/*  365:     */           catch (Exception e) {}
/*  366:     */         }
/*  367:     */         else
/*  368:     */         {
/*  369: 708 */           Hashtable h = new Hashtable(2);
/*  370:     */           
/*  371: 710 */           h.put(jars[i] + "-path", "WARNING. Classpath entry: " + filename + " does not exist");
/*  372:     */           
/*  373: 712 */           h.put(jars[i] + "-apparent.version", "not-present");
/*  374: 713 */           v.addElement(h);
/*  375:     */         }
/*  376:     */       }
/*  377: 672 */       i++;
/*  378:     */     }
/*  379: 719 */     return v;
/*  380:     */   }
/*  381:     */   
/*  382:     */   protected String getApparentVersion(String jarName, long jarSize)
/*  383:     */   {
/*  384: 745 */     String foundSize = (String)jarVersions.get(new Long(jarSize));
/*  385: 747 */     if ((null != foundSize) && (foundSize.startsWith(jarName))) {
/*  386: 749 */       return foundSize;
/*  387:     */     }
/*  388: 753 */     if (("xerces.jar".equalsIgnoreCase(jarName)) || ("xercesImpl.jar".equalsIgnoreCase(jarName))) {
/*  389: 761 */       return jarName + " " + "WARNING." + "present-unknown-version";
/*  390:     */     }
/*  391: 767 */     return jarName + " " + "present-unknown-version";
/*  392:     */   }
/*  393:     */   
/*  394:     */   protected void checkJAXPVersion(Hashtable h)
/*  395:     */   {
/*  396: 784 */     if (null == h) {
/*  397: 785 */       h = new Hashtable();
/*  398:     */     }
/*  399: 787 */     Class[] noArgs = new Class[0];
/*  400: 788 */     Class clazz = null;
/*  401:     */     try
/*  402:     */     {
/*  403: 792 */       String JAXP1_CLASS = "javax.xml.parsers.DocumentBuilder";
/*  404: 793 */       String JAXP11_METHOD = "getDOMImplementation";
/*  405:     */       
/*  406: 795 */       clazz = ObjectFactory.findProviderClass("javax.xml.parsers.DocumentBuilder", ObjectFactory.findClassLoader(), true);
/*  407:     */       
/*  408:     */ 
/*  409: 798 */       Method method = clazz.getMethod("getDOMImplementation", noArgs);
/*  410:     */       
/*  411:     */ 
/*  412: 801 */       h.put("version.JAXP", "1.1 or higher");
/*  413:     */     }
/*  414:     */     catch (Exception e)
/*  415:     */     {
/*  416: 805 */       if (null != clazz)
/*  417:     */       {
/*  418: 810 */         h.put("ERROR.version.JAXP", "1.0.1");
/*  419: 811 */         h.put("ERROR.", "At least one error was found!");
/*  420:     */       }
/*  421:     */       else
/*  422:     */       {
/*  423: 818 */         h.put("ERROR.version.JAXP", "not-present");
/*  424: 819 */         h.put("ERROR.", "At least one error was found!");
/*  425:     */       }
/*  426:     */     }
/*  427:     */   }
/*  428:     */   
/*  429:     */   protected void checkProcessorVersion(Hashtable h)
/*  430:     */   {
/*  431: 834 */     if (null == h) {
/*  432: 835 */       h = new Hashtable();
/*  433:     */     }
/*  434:     */     try
/*  435:     */     {
/*  436: 839 */       String XALAN1_VERSION_CLASS = "org.apache.xalan.xslt.XSLProcessorVersion";
/*  437:     */       
/*  438:     */ 
/*  439: 842 */       Class clazz = ObjectFactory.findProviderClass("org.apache.xalan.xslt.XSLProcessorVersion", ObjectFactory.findClassLoader(), true);
/*  440:     */       
/*  441:     */ 
/*  442:     */ 
/*  443: 846 */       StringBuffer buf = new StringBuffer();
/*  444: 847 */       Field f = clazz.getField("PRODUCT");
/*  445:     */       
/*  446: 849 */       buf.append(f.get(null));
/*  447: 850 */       buf.append(';');
/*  448:     */       
/*  449: 852 */       f = clazz.getField("LANGUAGE");
/*  450:     */       
/*  451: 854 */       buf.append(f.get(null));
/*  452: 855 */       buf.append(';');
/*  453:     */       
/*  454: 857 */       f = clazz.getField("S_VERSION");
/*  455:     */       
/*  456: 859 */       buf.append(f.get(null));
/*  457: 860 */       buf.append(';');
/*  458: 861 */       h.put("version.xalan1", buf.toString());
/*  459:     */     }
/*  460:     */     catch (Exception e1)
/*  461:     */     {
/*  462: 865 */       h.put("version.xalan1", "not-present");
/*  463:     */     }
/*  464:     */     try
/*  465:     */     {
/*  466: 872 */       String XALAN2_VERSION_CLASS = "org.apache.xalan.processor.XSLProcessorVersion";
/*  467:     */       
/*  468:     */ 
/*  469: 875 */       Class clazz = ObjectFactory.findProviderClass("org.apache.xalan.processor.XSLProcessorVersion", ObjectFactory.findClassLoader(), true);
/*  470:     */       
/*  471:     */ 
/*  472:     */ 
/*  473: 879 */       StringBuffer buf = new StringBuffer();
/*  474: 880 */       Field f = clazz.getField("S_VERSION");
/*  475: 881 */       buf.append(f.get(null));
/*  476:     */       
/*  477: 883 */       h.put("version.xalan2x", buf.toString());
/*  478:     */     }
/*  479:     */     catch (Exception e2)
/*  480:     */     {
/*  481: 887 */       h.put("version.xalan2x", "not-present");
/*  482:     */     }
/*  483:     */     try
/*  484:     */     {
/*  485: 892 */       String XALAN2_2_VERSION_CLASS = "org.apache.xalan.Version";
/*  486:     */       
/*  487: 894 */       String XALAN2_2_VERSION_METHOD = "getVersion";
/*  488: 895 */       Class[] noArgs = new Class[0];
/*  489:     */       
/*  490: 897 */       Class clazz = ObjectFactory.findProviderClass("org.apache.xalan.Version", ObjectFactory.findClassLoader(), true);
/*  491:     */       
/*  492:     */ 
/*  493: 900 */       Method method = clazz.getMethod("getVersion", noArgs);
/*  494: 901 */       Object returnValue = method.invoke(null, new Object[0]);
/*  495:     */       
/*  496: 903 */       h.put("version.xalan2_2", (String)returnValue);
/*  497:     */     }
/*  498:     */     catch (Exception e2)
/*  499:     */     {
/*  500: 907 */       h.put("version.xalan2_2", "not-present");
/*  501:     */     }
/*  502:     */   }
/*  503:     */   
/*  504:     */   protected void checkParserVersion(Hashtable h)
/*  505:     */   {
/*  506: 923 */     if (null == h) {
/*  507: 924 */       h = new Hashtable();
/*  508:     */     }
/*  509:     */     try
/*  510:     */     {
/*  511: 928 */       String XERCES1_VERSION_CLASS = "org.apache.xerces.framework.Version";
/*  512:     */       
/*  513: 930 */       Class clazz = ObjectFactory.findProviderClass("org.apache.xerces.framework.Version", ObjectFactory.findClassLoader(), true);
/*  514:     */       
/*  515:     */ 
/*  516:     */ 
/*  517: 934 */       Field f = clazz.getField("fVersion");
/*  518: 935 */       String parserVersion = (String)f.get(null);
/*  519:     */       
/*  520: 937 */       h.put("version.xerces1", parserVersion);
/*  521:     */     }
/*  522:     */     catch (Exception e)
/*  523:     */     {
/*  524: 941 */       h.put("version.xerces1", "not-present");
/*  525:     */     }
/*  526:     */     try
/*  527:     */     {
/*  528: 947 */       String XERCES2_VERSION_CLASS = "org.apache.xerces.impl.Version";
/*  529:     */       
/*  530: 949 */       Class clazz = ObjectFactory.findProviderClass("org.apache.xerces.impl.Version", ObjectFactory.findClassLoader(), true);
/*  531:     */       
/*  532:     */ 
/*  533:     */ 
/*  534: 953 */       Field f = clazz.getField("fVersion");
/*  535: 954 */       String parserVersion = (String)f.get(null);
/*  536:     */       
/*  537: 956 */       h.put("version.xerces2", parserVersion);
/*  538:     */     }
/*  539:     */     catch (Exception e)
/*  540:     */     {
/*  541: 960 */       h.put("version.xerces2", "not-present");
/*  542:     */     }
/*  543:     */     try
/*  544:     */     {
/*  545: 965 */       String CRIMSON_CLASS = "org.apache.crimson.parser.Parser2";
/*  546:     */       
/*  547: 967 */       Class clazz = ObjectFactory.findProviderClass("org.apache.crimson.parser.Parser2", ObjectFactory.findClassLoader(), true);
/*  548:     */       
/*  549:     */ 
/*  550:     */ 
/*  551: 971 */       h.put("version.crimson", "present-unknown-version");
/*  552:     */     }
/*  553:     */     catch (Exception e)
/*  554:     */     {
/*  555: 975 */       h.put("version.crimson", "not-present");
/*  556:     */     }
/*  557:     */   }
/*  558:     */   
/*  559:     */   protected void checkAntVersion(Hashtable h)
/*  560:     */   {
/*  561: 987 */     if (null == h) {
/*  562: 988 */       h = new Hashtable();
/*  563:     */     }
/*  564:     */     try
/*  565:     */     {
/*  566: 992 */       String ANT_VERSION_CLASS = "org.apache.tools.ant.Main";
/*  567: 993 */       String ANT_VERSION_METHOD = "getAntVersion";
/*  568: 994 */       Class[] noArgs = new Class[0];
/*  569:     */       
/*  570: 996 */       Class clazz = ObjectFactory.findProviderClass("org.apache.tools.ant.Main", ObjectFactory.findClassLoader(), true);
/*  571:     */       
/*  572:     */ 
/*  573: 999 */       Method method = clazz.getMethod("getAntVersion", noArgs);
/*  574:1000 */       Object returnValue = method.invoke(null, new Object[0]);
/*  575:     */       
/*  576:1002 */       h.put("version.ant", (String)returnValue);
/*  577:     */     }
/*  578:     */     catch (Exception e)
/*  579:     */     {
/*  580:1006 */       h.put("version.ant", "not-present");
/*  581:     */     }
/*  582:     */   }
/*  583:     */   
/*  584:     */   protected void checkDOMVersion(Hashtable h)
/*  585:     */   {
/*  586:1022 */     if (null == h) {
/*  587:1023 */       h = new Hashtable();
/*  588:     */     }
/*  589:1025 */     String DOM_LEVEL2_CLASS = "org.w3c.dom.Document";
/*  590:1026 */     String DOM_LEVEL2_METHOD = "createElementNS";
/*  591:1027 */     String DOM_LEVEL2WD_CLASS = "org.w3c.dom.Node";
/*  592:1028 */     String DOM_LEVEL2WD_METHOD = "supported";
/*  593:1029 */     String DOM_LEVEL2FD_CLASS = "org.w3c.dom.Node";
/*  594:1030 */     String DOM_LEVEL2FD_METHOD = "isSupported";
/*  595:1031 */     Class[] twoStringArgs = { String.class, String.class };
/*  596:     */     try
/*  597:     */     {
/*  598:1036 */       Class clazz = ObjectFactory.findProviderClass("org.w3c.dom.Document", ObjectFactory.findClassLoader(), true);
/*  599:     */       
/*  600:     */ 
/*  601:1039 */       Method method = clazz.getMethod("createElementNS", twoStringArgs);
/*  602:     */       
/*  603:     */ 
/*  604:     */ 
/*  605:1043 */       h.put("version.DOM", "2.0");
/*  606:     */       try
/*  607:     */       {
/*  608:1049 */         clazz = ObjectFactory.findProviderClass("org.w3c.dom.Node", ObjectFactory.findClassLoader(), true);
/*  609:     */         
/*  610:     */ 
/*  611:1052 */         method = clazz.getMethod("supported", twoStringArgs);
/*  612:     */         
/*  613:1054 */         h.put("ERROR.version.DOM.draftlevel", "2.0wd");
/*  614:1055 */         h.put("ERROR.", "At least one error was found!");
/*  615:     */       }
/*  616:     */       catch (Exception e2)
/*  617:     */       {
/*  618:     */         try
/*  619:     */         {
/*  620:1062 */           clazz = ObjectFactory.findProviderClass("org.w3c.dom.Node", ObjectFactory.findClassLoader(), true);
/*  621:     */           
/*  622:     */ 
/*  623:1065 */           method = clazz.getMethod("isSupported", twoStringArgs);
/*  624:     */           
/*  625:1067 */           h.put("version.DOM.draftlevel", "2.0fd");
/*  626:     */         }
/*  627:     */         catch (Exception e3)
/*  628:     */         {
/*  629:1071 */           h.put("ERROR.version.DOM.draftlevel", "2.0unknown");
/*  630:1072 */           h.put("ERROR.", "At least one error was found!");
/*  631:     */         }
/*  632:     */       }
/*  633:     */     }
/*  634:     */     catch (Exception e)
/*  635:     */     {
/*  636:1078 */       h.put("ERROR.version.DOM", "ERROR attempting to load DOM level 2 class: " + e.toString());
/*  637:     */       
/*  638:1080 */       h.put("ERROR.", "At least one error was found!");
/*  639:     */     }
/*  640:     */   }
/*  641:     */   
/*  642:     */   protected void checkSAXVersion(Hashtable h)
/*  643:     */   {
/*  644:1100 */     if (null == h) {
/*  645:1101 */       h = new Hashtable();
/*  646:     */     }
/*  647:1103 */     String SAX_VERSION1_CLASS = "org.xml.sax.Parser";
/*  648:1104 */     String SAX_VERSION1_METHOD = "parse";
/*  649:1105 */     String SAX_VERSION2_CLASS = "org.xml.sax.XMLReader";
/*  650:1106 */     String SAX_VERSION2_METHOD = "parse";
/*  651:1107 */     String SAX_VERSION2BETA_CLASSNF = "org.xml.sax.helpers.AttributesImpl";
/*  652:1108 */     String SAX_VERSION2BETA_METHODNF = "setAttributes";
/*  653:1109 */     Class[] oneStringArg = { String.class };
/*  654:     */     
/*  655:1111 */     Class[] attributesArg = { Attributes.class };
/*  656:     */     try
/*  657:     */     {
/*  658:1117 */       Class clazz = ObjectFactory.findProviderClass("org.xml.sax.helpers.AttributesImpl", ObjectFactory.findClassLoader(), true);
/*  659:     */       
/*  660:     */ 
/*  661:1120 */       Method method = clazz.getMethod("setAttributes", attributesArg);
/*  662:     */       
/*  663:     */ 
/*  664:     */ 
/*  665:1124 */       h.put("version.SAX", "2.0");
/*  666:     */     }
/*  667:     */     catch (Exception e)
/*  668:     */     {
/*  669:1129 */       h.put("ERROR.version.SAX", "ERROR attempting to load SAX version 2 class: " + e.toString());
/*  670:     */       
/*  671:1131 */       h.put("ERROR.", "At least one error was found!");
/*  672:     */       try
/*  673:     */       {
/*  674:1135 */         Class clazz = ObjectFactory.findProviderClass("org.xml.sax.XMLReader", ObjectFactory.findClassLoader(), true);
/*  675:     */         
/*  676:     */ 
/*  677:1138 */         Method method = clazz.getMethod("parse", oneStringArg);
/*  678:     */         
/*  679:     */ 
/*  680:     */ 
/*  681:     */ 
/*  682:1143 */         h.put("version.SAX-backlevel", "2.0beta2-or-earlier");
/*  683:     */       }
/*  684:     */       catch (Exception e2)
/*  685:     */       {
/*  686:1148 */         h.put("ERROR.version.SAX", "ERROR attempting to load SAX version 2 class: " + e.toString());
/*  687:     */         
/*  688:1150 */         h.put("ERROR.", "At least one error was found!");
/*  689:     */         try
/*  690:     */         {
/*  691:1154 */           Class clazz = ObjectFactory.findProviderClass("org.xml.sax.Parser", ObjectFactory.findClassLoader(), true);
/*  692:     */           
/*  693:     */ 
/*  694:1157 */           Method method = clazz.getMethod("parse", oneStringArg);
/*  695:     */           
/*  696:     */ 
/*  697:     */ 
/*  698:     */ 
/*  699:1162 */           h.put("version.SAX-backlevel", "1.0");
/*  700:     */         }
/*  701:     */         catch (Exception e3)
/*  702:     */         {
/*  703:1168 */           h.put("ERROR.version.SAX-backlevel", "ERROR attempting to load SAX version 1 class: " + e3.toString());
/*  704:     */         }
/*  705:     */       }
/*  706:     */     }
/*  707:     */   }
/*  708:     */   
/*  709:1184 */   private static Hashtable jarVersions = new Hashtable();
/*  710:     */   
/*  711:     */   static
/*  712:     */   {
/*  713:1195 */     jarVersions.put(new Long(857192L), "xalan.jar from xalan-j_1_1");
/*  714:1196 */     jarVersions.put(new Long(440237L), "xalan.jar from xalan-j_1_2");
/*  715:1197 */     jarVersions.put(new Long(436094L), "xalan.jar from xalan-j_1_2_1");
/*  716:1198 */     jarVersions.put(new Long(426249L), "xalan.jar from xalan-j_1_2_2");
/*  717:1199 */     jarVersions.put(new Long(702536L), "xalan.jar from xalan-j_2_0_0");
/*  718:1200 */     jarVersions.put(new Long(720930L), "xalan.jar from xalan-j_2_0_1");
/*  719:1201 */     jarVersions.put(new Long(732330L), "xalan.jar from xalan-j_2_1_0");
/*  720:1202 */     jarVersions.put(new Long(872241L), "xalan.jar from xalan-j_2_2_D10");
/*  721:1203 */     jarVersions.put(new Long(882739L), "xalan.jar from xalan-j_2_2_D11");
/*  722:1204 */     jarVersions.put(new Long(923866L), "xalan.jar from xalan-j_2_2_0");
/*  723:1205 */     jarVersions.put(new Long(905872L), "xalan.jar from xalan-j_2_3_D1");
/*  724:1206 */     jarVersions.put(new Long(906122L), "xalan.jar from xalan-j_2_3_0");
/*  725:1207 */     jarVersions.put(new Long(906248L), "xalan.jar from xalan-j_2_3_1");
/*  726:1208 */     jarVersions.put(new Long(983377L), "xalan.jar from xalan-j_2_4_D1");
/*  727:1209 */     jarVersions.put(new Long(997276L), "xalan.jar from xalan-j_2_4_0");
/*  728:1210 */     jarVersions.put(new Long(1031036L), "xalan.jar from xalan-j_2_4_1");
/*  729:     */     
/*  730:     */ 
/*  731:1213 */     jarVersions.put(new Long(596540L), "xsltc.jar from xalan-j_2_2_0");
/*  732:1214 */     jarVersions.put(new Long(590247L), "xsltc.jar from xalan-j_2_3_D1");
/*  733:1215 */     jarVersions.put(new Long(589914L), "xsltc.jar from xalan-j_2_3_0");
/*  734:1216 */     jarVersions.put(new Long(589915L), "xsltc.jar from xalan-j_2_3_1");
/*  735:1217 */     jarVersions.put(new Long(1306667L), "xsltc.jar from xalan-j_2_4_D1");
/*  736:1218 */     jarVersions.put(new Long(1328227L), "xsltc.jar from xalan-j_2_4_0");
/*  737:1219 */     jarVersions.put(new Long(1344009L), "xsltc.jar from xalan-j_2_4_1");
/*  738:1220 */     jarVersions.put(new Long(1348361L), "xsltc.jar from xalan-j_2_5_D1");
/*  739:     */     
/*  740:     */ 
/*  741:1223 */     jarVersions.put(new Long(1268634L), "xsltc.jar-bundled from xalan-j_2_3_0");
/*  742:     */     
/*  743:1225 */     jarVersions.put(new Long(100196L), "xml-apis.jar from xalan-j_2_2_0 or xalan-j_2_3_D1");
/*  744:1226 */     jarVersions.put(new Long(108484L), "xml-apis.jar from xalan-j_2_3_0, or xalan-j_2_3_1 from xml-commons-1.0.b2");
/*  745:1227 */     jarVersions.put(new Long(109049L), "xml-apis.jar from xalan-j_2_4_0 from xml-commons RIVERCOURT1 branch");
/*  746:1228 */     jarVersions.put(new Long(113749L), "xml-apis.jar from xalan-j_2_4_1 from factoryfinder-build of xml-commons RIVERCOURT1");
/*  747:1229 */     jarVersions.put(new Long(124704L), "xml-apis.jar from tck-jaxp-1_2_0 branch of xml-commons");
/*  748:1230 */     jarVersions.put(new Long(124724L), "xml-apis.jar from tck-jaxp-1_2_0 branch of xml-commons, tag: xml-commons-external_1_2_01");
/*  749:1231 */     jarVersions.put(new Long(194205L), "xml-apis.jar from head branch of xml-commons, tag: xml-commons-external_1_3_02");
/*  750:     */     
/*  751:     */ 
/*  752:     */ 
/*  753:1235 */     jarVersions.put(new Long(424490L), "xalan.jar from Xerces Tools releases - ERROR:DO NOT USE!");
/*  754:     */     
/*  755:1237 */     jarVersions.put(new Long(1591855L), "xerces.jar from xalan-j_1_1 from xerces-1...");
/*  756:1238 */     jarVersions.put(new Long(1498679L), "xerces.jar from xalan-j_1_2 from xerces-1_2_0.bin");
/*  757:1239 */     jarVersions.put(new Long(1484896L), "xerces.jar from xalan-j_1_2_1 from xerces-1_2_1.bin");
/*  758:1240 */     jarVersions.put(new Long(804460L), "xerces.jar from xalan-j_1_2_2 from xerces-1_2_2.bin");
/*  759:1241 */     jarVersions.put(new Long(1499244L), "xerces.jar from xalan-j_2_0_0 from xerces-1_2_3.bin");
/*  760:1242 */     jarVersions.put(new Long(1605266L), "xerces.jar from xalan-j_2_0_1 from xerces-1_3_0.bin");
/*  761:1243 */     jarVersions.put(new Long(904030L), "xerces.jar from xalan-j_2_1_0 from xerces-1_4.bin");
/*  762:1244 */     jarVersions.put(new Long(904030L), "xerces.jar from xerces-1_4_0.bin");
/*  763:1245 */     jarVersions.put(new Long(1802885L), "xerces.jar from xerces-1_4_2.bin");
/*  764:1246 */     jarVersions.put(new Long(1734594L), "xerces.jar from Xerces-J-bin.2.0.0.beta3");
/*  765:1247 */     jarVersions.put(new Long(1808883L), "xerces.jar from xalan-j_2_2_D10,D11,D12 or xerces-1_4_3.bin");
/*  766:1248 */     jarVersions.put(new Long(1812019L), "xerces.jar from xalan-j_2_2_0");
/*  767:1249 */     jarVersions.put(new Long(1720292L), "xercesImpl.jar from xalan-j_2_3_D1");
/*  768:1250 */     jarVersions.put(new Long(1730053L), "xercesImpl.jar from xalan-j_2_3_0 or xalan-j_2_3_1 from xerces-2_0_0");
/*  769:1251 */     jarVersions.put(new Long(1728861L), "xercesImpl.jar from xalan-j_2_4_D1 from xerces-2_0_1");
/*  770:1252 */     jarVersions.put(new Long(972027L), "xercesImpl.jar from xalan-j_2_4_0 from xerces-2_1");
/*  771:1253 */     jarVersions.put(new Long(831587L), "xercesImpl.jar from xalan-j_2_4_1 from xerces-2_2");
/*  772:1254 */     jarVersions.put(new Long(891817L), "xercesImpl.jar from xalan-j_2_5_D1 from xerces-2_3");
/*  773:1255 */     jarVersions.put(new Long(895924L), "xercesImpl.jar from xerces-2_4");
/*  774:1256 */     jarVersions.put(new Long(1010806L), "xercesImpl.jar from Xerces-J-bin.2.6.2");
/*  775:1257 */     jarVersions.put(new Long(1203860L), "xercesImpl.jar from Xerces-J-bin.2.7.1");
/*  776:     */     
/*  777:1259 */     jarVersions.put(new Long(37485L), "xalanj1compat.jar from xalan-j_2_0_0");
/*  778:1260 */     jarVersions.put(new Long(38100L), "xalanj1compat.jar from xalan-j_2_0_1");
/*  779:     */     
/*  780:1262 */     jarVersions.put(new Long(18779L), "xalanservlet.jar from xalan-j_2_0_0");
/*  781:1263 */     jarVersions.put(new Long(21453L), "xalanservlet.jar from xalan-j_2_0_1");
/*  782:1264 */     jarVersions.put(new Long(24826L), "xalanservlet.jar from xalan-j_2_3_1 or xalan-j_2_4_1");
/*  783:1265 */     jarVersions.put(new Long(24831L), "xalanservlet.jar from xalan-j_2_4_1");
/*  784:     */     
/*  785:     */ 
/*  786:     */ 
/*  787:1269 */     jarVersions.put(new Long(5618L), "jaxp.jar from jaxp1.0.1");
/*  788:1270 */     jarVersions.put(new Long(136133L), "parser.jar from jaxp1.0.1");
/*  789:1271 */     jarVersions.put(new Long(28404L), "jaxp.jar from jaxp-1.1");
/*  790:1272 */     jarVersions.put(new Long(187162L), "crimson.jar from jaxp-1.1");
/*  791:1273 */     jarVersions.put(new Long(801714L), "xalan.jar from jaxp-1.1");
/*  792:1274 */     jarVersions.put(new Long(196399L), "crimson.jar from crimson-1.1.1");
/*  793:1275 */     jarVersions.put(new Long(33323L), "jaxp.jar from crimson-1.1.1 or jakarta-ant-1.4.1b1");
/*  794:1276 */     jarVersions.put(new Long(152717L), "crimson.jar from crimson-1.1.2beta2");
/*  795:1277 */     jarVersions.put(new Long(88143L), "xml-apis.jar from crimson-1.1.2beta2");
/*  796:1278 */     jarVersions.put(new Long(206384L), "crimson.jar from crimson-1.1.3 or jakarta-ant-1.4.1b1");
/*  797:     */     
/*  798:     */ 
/*  799:1281 */     jarVersions.put(new Long(136198L), "parser.jar from jakarta-ant-1.3 or 1.2");
/*  800:1282 */     jarVersions.put(new Long(5537L), "jaxp.jar from jakarta-ant-1.3 or 1.2");
/*  801:     */   }
/*  802:     */   
/*  803:1286 */   protected PrintWriter outWriter = new PrintWriter(System.out, true);
/*  804:     */   
/*  805:     */   protected void logMsg(String s)
/*  806:     */   {
/*  807:1294 */     this.outWriter.println(s);
/*  808:     */   }
/*  809:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xslt.EnvironmentCheck
 * JD-Core Version:    0.7.0.1
 */