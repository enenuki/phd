/*    1:     */ package org.apache.xalan.xsltc.runtime;
/*    2:     */ 
/*    3:     */ import java.io.PrintStream;
/*    4:     */ import java.text.DecimalFormat;
/*    5:     */ import java.text.FieldPosition;
/*    6:     */ import java.text.MessageFormat;
/*    7:     */ import java.text.NumberFormat;
/*    8:     */ import java.util.Locale;
/*    9:     */ import java.util.ResourceBundle;
/*   10:     */ import javax.xml.parsers.ParserConfigurationException;
/*   11:     */ import javax.xml.transform.dom.DOMSource;
/*   12:     */ import org.apache.xalan.xsltc.DOM;
/*   13:     */ import org.apache.xalan.xsltc.Translet;
/*   14:     */ import org.apache.xalan.xsltc.dom.AbsoluteIterator;
/*   15:     */ import org.apache.xalan.xsltc.dom.DOMAdapter;
/*   16:     */ import org.apache.xalan.xsltc.dom.MultiDOM;
/*   17:     */ import org.apache.xalan.xsltc.dom.SingletonIterator;
/*   18:     */ import org.apache.xalan.xsltc.dom.StepIterator;
/*   19:     */ import org.apache.xml.dtm.DTMAxisIterator;
/*   20:     */ import org.apache.xml.dtm.DTMManager;
/*   21:     */ import org.apache.xml.dtm.ref.DTMDefaultBase;
/*   22:     */ import org.apache.xml.serializer.ExtendedContentHandler;
/*   23:     */ import org.apache.xml.serializer.SerializationHandler;
/*   24:     */ import org.apache.xml.utils.XML11Char;
/*   25:     */ import org.w3c.dom.DOMException;
/*   26:     */ import org.w3c.dom.Document;
/*   27:     */ import org.w3c.dom.Element;
/*   28:     */ import org.w3c.dom.NamedNodeMap;
/*   29:     */ import org.w3c.dom.NodeList;
/*   30:     */ import org.xml.sax.ContentHandler;
/*   31:     */ import org.xml.sax.SAXException;
/*   32:     */ 
/*   33:     */ public final class BasisLibrary
/*   34:     */ {
/*   35:     */   private static final String EMPTYSTRING = "";
/*   36:     */   private static final int DOUBLE_FRACTION_DIGITS = 340;
/*   37:     */   private static final double lowerBounds = 0.001D;
/*   38:     */   private static final double upperBounds = 10000000.0D;
/*   39:     */   private static DecimalFormat defaultFormatter;
/*   40:     */   
/*   41:     */   public static int countF(DTMAxisIterator iterator)
/*   42:     */   {
/*   43:  65 */     return iterator.getLast();
/*   44:     */   }
/*   45:     */   
/*   46:     */   /**
/*   47:     */    * @deprecated
/*   48:     */    */
/*   49:     */   public static int positionF(DTMAxisIterator iterator)
/*   50:     */   {
/*   51:  74 */     return iterator.isReverse() ? iterator.getLast() - iterator.getPosition() + 1 : iterator.getPosition();
/*   52:     */   }
/*   53:     */   
/*   54:     */   public static double sumF(DTMAxisIterator iterator, DOM dom)
/*   55:     */   {
/*   56:     */     try
/*   57:     */     {
/*   58:  85 */       double result = 0.0D;
/*   59:     */       int node;
/*   60:  87 */       while ((node = iterator.next()) != -1)
/*   61:     */       {
/*   62:     */         int i;
/*   63:  88 */         result += Double.parseDouble(dom.getStringValueX(i));
/*   64:     */       }
/*   65:  90 */       return result;
/*   66:     */     }
/*   67:     */     catch (NumberFormatException e) {}
/*   68:  93 */     return (0.0D / 0.0D);
/*   69:     */   }
/*   70:     */   
/*   71:     */   public static String stringF(int node, DOM dom)
/*   72:     */   {
/*   73: 101 */     return dom.getStringValueX(node);
/*   74:     */   }
/*   75:     */   
/*   76:     */   public static String stringF(Object obj, DOM dom)
/*   77:     */   {
/*   78: 108 */     if ((obj instanceof DTMAxisIterator)) {
/*   79: 109 */       return dom.getStringValueX(((DTMAxisIterator)obj).reset().next());
/*   80:     */     }
/*   81: 111 */     if ((obj instanceof Node)) {
/*   82: 112 */       return dom.getStringValueX(((Node)obj).node);
/*   83:     */     }
/*   84: 114 */     if ((obj instanceof DOM)) {
/*   85: 115 */       return ((DOM)obj).getStringValue();
/*   86:     */     }
/*   87: 118 */     return obj.toString();
/*   88:     */   }
/*   89:     */   
/*   90:     */   public static String stringF(Object obj, int node, DOM dom)
/*   91:     */   {
/*   92: 126 */     if ((obj instanceof DTMAxisIterator)) {
/*   93: 127 */       return dom.getStringValueX(((DTMAxisIterator)obj).reset().next());
/*   94:     */     }
/*   95: 129 */     if ((obj instanceof Node)) {
/*   96: 130 */       return dom.getStringValueX(((Node)obj).node);
/*   97:     */     }
/*   98: 132 */     if ((obj instanceof DOM)) {
/*   99: 136 */       return ((DOM)obj).getStringValue();
/*  100:     */     }
/*  101: 138 */     if ((obj instanceof Double))
/*  102:     */     {
/*  103: 139 */       Double d = (Double)obj;
/*  104: 140 */       String result = d.toString();
/*  105: 141 */       int length = result.length();
/*  106: 142 */       if ((result.charAt(length - 2) == '.') && (result.charAt(length - 1) == '0')) {
/*  107: 144 */         return result.substring(0, length - 2);
/*  108:     */       }
/*  109: 146 */       return result;
/*  110:     */     }
/*  111: 149 */     if (obj != null) {
/*  112: 150 */       return obj.toString();
/*  113:     */     }
/*  114: 152 */     return stringF(node, dom);
/*  115:     */   }
/*  116:     */   
/*  117:     */   public static double numberF(int node, DOM dom)
/*  118:     */   {
/*  119: 160 */     return stringToReal(dom.getStringValueX(node));
/*  120:     */   }
/*  121:     */   
/*  122:     */   public static double numberF(Object obj, DOM dom)
/*  123:     */   {
/*  124: 167 */     if ((obj instanceof Double)) {
/*  125: 168 */       return ((Double)obj).doubleValue();
/*  126:     */     }
/*  127: 170 */     if ((obj instanceof Integer)) {
/*  128: 171 */       return ((Integer)obj).doubleValue();
/*  129:     */     }
/*  130: 173 */     if ((obj instanceof Boolean)) {
/*  131: 174 */       return ((Boolean)obj).booleanValue() ? 1.0D : 0.0D;
/*  132:     */     }
/*  133: 176 */     if ((obj instanceof String)) {
/*  134: 177 */       return stringToReal((String)obj);
/*  135:     */     }
/*  136: 179 */     if ((obj instanceof DTMAxisIterator))
/*  137:     */     {
/*  138: 180 */       DTMAxisIterator iter = (DTMAxisIterator)obj;
/*  139: 181 */       return stringToReal(dom.getStringValueX(iter.reset().next()));
/*  140:     */     }
/*  141: 183 */     if ((obj instanceof Node)) {
/*  142: 184 */       return stringToReal(dom.getStringValueX(((Node)obj).node));
/*  143:     */     }
/*  144: 186 */     if ((obj instanceof DOM)) {
/*  145: 187 */       return stringToReal(((DOM)obj).getStringValue());
/*  146:     */     }
/*  147: 190 */     String className = obj.getClass().getName();
/*  148: 191 */     runTimeError("INVALID_ARGUMENT_ERR", className, "number()");
/*  149: 192 */     return 0.0D;
/*  150:     */   }
/*  151:     */   
/*  152:     */   public static double roundF(double d)
/*  153:     */   {
/*  154: 200 */     return Double.isNaN(d) ? (0.0D / 0.0D) : d == 0.0D ? d : (d < -0.5D) || (d > 0.0D) ? Math.floor(d + 0.5D) : -0.0D;
/*  155:     */   }
/*  156:     */   
/*  157:     */   public static boolean booleanF(Object obj)
/*  158:     */   {
/*  159: 208 */     if ((obj instanceof Double))
/*  160:     */     {
/*  161: 209 */       double temp = ((Double)obj).doubleValue();
/*  162: 210 */       return (temp != 0.0D) && (!Double.isNaN(temp));
/*  163:     */     }
/*  164: 212 */     if ((obj instanceof Integer)) {
/*  165: 213 */       return ((Integer)obj).doubleValue() != 0.0D;
/*  166:     */     }
/*  167: 215 */     if ((obj instanceof Boolean)) {
/*  168: 216 */       return ((Boolean)obj).booleanValue();
/*  169:     */     }
/*  170: 218 */     if ((obj instanceof String)) {
/*  171: 219 */       return !((String)obj).equals("");
/*  172:     */     }
/*  173: 221 */     if ((obj instanceof DTMAxisIterator))
/*  174:     */     {
/*  175: 222 */       DTMAxisIterator iter = (DTMAxisIterator)obj;
/*  176: 223 */       return iter.reset().next() != -1;
/*  177:     */     }
/*  178: 225 */     if ((obj instanceof Node)) {
/*  179: 226 */       return true;
/*  180:     */     }
/*  181: 228 */     if ((obj instanceof DOM))
/*  182:     */     {
/*  183: 229 */       String temp = ((DOM)obj).getStringValue();
/*  184: 230 */       return !temp.equals("");
/*  185:     */     }
/*  186: 233 */     String className = obj.getClass().getName();
/*  187: 234 */     runTimeError("INVALID_ARGUMENT_ERR", className, "boolean()");
/*  188:     */     
/*  189: 236 */     return false;
/*  190:     */   }
/*  191:     */   
/*  192:     */   public static String substringF(String value, double start)
/*  193:     */   {
/*  194:     */     try
/*  195:     */     {
/*  196: 245 */       int strlen = value.length();
/*  197: 246 */       int istart = (int)Math.round(start) - 1;
/*  198: 248 */       if (Double.isNaN(start)) {
/*  199: 248 */         return "";
/*  200:     */       }
/*  201: 249 */       if (istart > strlen) {
/*  202: 249 */         return "";
/*  203:     */       }
/*  204: 250 */       if (istart < 1) {
/*  205: 250 */         istart = 0;
/*  206:     */       }
/*  207: 252 */       return value.substring(istart);
/*  208:     */     }
/*  209:     */     catch (IndexOutOfBoundsException e)
/*  210:     */     {
/*  211: 255 */       runTimeError("RUN_TIME_INTERNAL_ERR", "substring()");
/*  212:     */     }
/*  213: 256 */     return null;
/*  214:     */   }
/*  215:     */   
/*  216:     */   public static String substringF(String value, double start, double length)
/*  217:     */   {
/*  218:     */     try
/*  219:     */     {
/*  220: 266 */       int strlen = value.length();
/*  221: 267 */       int istart = (int)Math.round(start) - 1;
/*  222: 268 */       int isum = istart + (int)Math.round(length);
/*  223: 270 */       if (Double.isInfinite(length)) {
/*  224: 270 */         isum = 2147483647;
/*  225:     */       }
/*  226: 272 */       if ((Double.isNaN(start)) || (Double.isNaN(length))) {
/*  227: 273 */         return "";
/*  228:     */       }
/*  229: 274 */       if (Double.isInfinite(start)) {
/*  230: 274 */         return "";
/*  231:     */       }
/*  232: 275 */       if (istart > strlen) {
/*  233: 275 */         return "";
/*  234:     */       }
/*  235: 276 */       if (isum < 0) {
/*  236: 276 */         return "";
/*  237:     */       }
/*  238: 277 */       if (istart < 0) {
/*  239: 277 */         istart = 0;
/*  240:     */       }
/*  241: 279 */       if (isum > strlen) {
/*  242: 280 */         return value.substring(istart);
/*  243:     */       }
/*  244: 282 */       return value.substring(istart, isum);
/*  245:     */     }
/*  246:     */     catch (IndexOutOfBoundsException e)
/*  247:     */     {
/*  248: 285 */       runTimeError("RUN_TIME_INTERNAL_ERR", "substring()");
/*  249:     */     }
/*  250: 286 */     return null;
/*  251:     */   }
/*  252:     */   
/*  253:     */   public static String substring_afterF(String value, String substring)
/*  254:     */   {
/*  255: 294 */     int index = value.indexOf(substring);
/*  256: 295 */     if (index >= 0) {
/*  257: 296 */       return value.substring(index + substring.length());
/*  258:     */     }
/*  259: 298 */     return "";
/*  260:     */   }
/*  261:     */   
/*  262:     */   public static String substring_beforeF(String value, String substring)
/*  263:     */   {
/*  264: 305 */     int index = value.indexOf(substring);
/*  265: 306 */     if (index >= 0) {
/*  266: 307 */       return value.substring(0, index);
/*  267:     */     }
/*  268: 309 */     return "";
/*  269:     */   }
/*  270:     */   
/*  271:     */   public static String translateF(String value, String from, String to)
/*  272:     */   {
/*  273: 316 */     int tol = to.length();
/*  274: 317 */     int froml = from.length();
/*  275: 318 */     int valuel = value.length();
/*  276:     */     
/*  277: 320 */     StringBuffer result = new StringBuffer();
/*  278: 321 */     for (int i = 0; i < valuel; i++)
/*  279:     */     {
/*  280: 322 */       char ch = value.charAt(i);
/*  281: 323 */       for (int j = 0; j < froml; j++) {
/*  282: 324 */         if (ch == from.charAt(j))
/*  283:     */         {
/*  284: 325 */           if (j >= tol) {
/*  285:     */             break;
/*  286:     */           }
/*  287: 326 */           result.append(to.charAt(j)); break;
/*  288:     */         }
/*  289:     */       }
/*  290: 330 */       if (j == froml) {
/*  291: 331 */         result.append(ch);
/*  292:     */       }
/*  293:     */     }
/*  294: 333 */     return result.toString();
/*  295:     */   }
/*  296:     */   
/*  297:     */   public static String normalize_spaceF(int node, DOM dom)
/*  298:     */   {
/*  299: 340 */     return normalize_spaceF(dom.getStringValueX(node));
/*  300:     */   }
/*  301:     */   
/*  302:     */   public static String normalize_spaceF(String value)
/*  303:     */   {
/*  304: 347 */     int i = 0;int n = value.length();
/*  305: 348 */     StringBuffer result = new StringBuffer();
/*  306:     */     do
/*  307:     */     {
/*  308: 351 */       i++;
/*  309: 350 */       if (i >= n) {
/*  310:     */         break;
/*  311:     */       }
/*  312: 350 */     } while (isWhiteSpace(value.charAt(i)));
/*  313:     */     for (;;)
/*  314:     */     {
/*  315: 355 */       result.append(value.charAt(i++));
/*  316: 354 */       while ((i >= n) || (isWhiteSpace(value.charAt(i))))
/*  317:     */       {
/*  318: 357 */         if (i == n) {
/*  319:     */           break label114;
/*  320:     */         }
/*  321: 359 */         while ((i < n) && (isWhiteSpace(value.charAt(i)))) {
/*  322: 360 */           i++;
/*  323:     */         }
/*  324: 362 */         if (i < n) {
/*  325: 363 */           result.append(' ');
/*  326:     */         }
/*  327:     */       }
/*  328:     */     }
/*  329:     */     label114:
/*  330: 365 */     return result.toString();
/*  331:     */   }
/*  332:     */   
/*  333:     */   public static String generate_idF(int node)
/*  334:     */   {
/*  335: 372 */     if (node > 0) {
/*  336: 374 */       return "N" + node;
/*  337:     */     }
/*  338: 377 */     return "";
/*  339:     */   }
/*  340:     */   
/*  341:     */   public static String getLocalName(String value)
/*  342:     */   {
/*  343: 384 */     int idx = value.lastIndexOf(':');
/*  344: 385 */     if (idx >= 0) {
/*  345: 385 */       value = value.substring(idx + 1);
/*  346:     */     }
/*  347: 386 */     idx = value.lastIndexOf('@');
/*  348: 387 */     if (idx >= 0) {
/*  349: 387 */       value = value.substring(idx + 1);
/*  350:     */     }
/*  351: 388 */     return value;
/*  352:     */   }
/*  353:     */   
/*  354:     */   public static void unresolved_externalF(String name)
/*  355:     */   {
/*  356: 401 */     runTimeError("EXTERNAL_FUNC_ERR", name);
/*  357:     */   }
/*  358:     */   
/*  359:     */   public static void unallowed_extension_functionF(String name)
/*  360:     */   {
/*  361: 409 */     runTimeError("UNALLOWED_EXTENSION_FUNCTION_ERR", name);
/*  362:     */   }
/*  363:     */   
/*  364:     */   public static void unallowed_extension_elementF(String name)
/*  365:     */   {
/*  366: 417 */     runTimeError("UNALLOWED_EXTENSION_ELEMENT_ERR", name);
/*  367:     */   }
/*  368:     */   
/*  369:     */   public static void unsupported_ElementF(String qname, boolean isExtension)
/*  370:     */   {
/*  371: 428 */     if (isExtension) {
/*  372: 429 */       runTimeError("UNSUPPORTED_EXT_ERR", qname);
/*  373:     */     } else {
/*  374: 431 */       runTimeError("UNSUPPORTED_XSL_ERR", qname);
/*  375:     */     }
/*  376:     */   }
/*  377:     */   
/*  378:     */   public static String namespace_uriF(DTMAxisIterator iter, DOM dom)
/*  379:     */   {
/*  380: 438 */     return namespace_uriF(iter.next(), dom);
/*  381:     */   }
/*  382:     */   
/*  383:     */   public static String system_propertyF(String name)
/*  384:     */   {
/*  385: 445 */     if (name.equals("xsl:version")) {
/*  386: 446 */       return "1.0";
/*  387:     */     }
/*  388: 447 */     if (name.equals("xsl:vendor")) {
/*  389: 448 */       return "Apache Software Foundation (Xalan XSLTC)";
/*  390:     */     }
/*  391: 449 */     if (name.equals("xsl:vendor-url")) {
/*  392: 450 */       return "http://xml.apache.org/xalan-j";
/*  393:     */     }
/*  394: 452 */     runTimeError("INVALID_ARGUMENT_ERR", name, "system-property()");
/*  395: 453 */     return "";
/*  396:     */   }
/*  397:     */   
/*  398:     */   public static String namespace_uriF(int node, DOM dom)
/*  399:     */   {
/*  400: 460 */     String value = dom.getNodeName(node);
/*  401: 461 */     int colon = value.lastIndexOf(':');
/*  402: 462 */     if (colon >= 0) {
/*  403: 463 */       return value.substring(0, colon);
/*  404:     */     }
/*  405: 465 */     return "";
/*  406:     */   }
/*  407:     */   
/*  408:     */   public static String objectTypeF(Object obj)
/*  409:     */   {
/*  410: 475 */     if ((obj instanceof String)) {
/*  411: 476 */       return "string";
/*  412:     */     }
/*  413: 477 */     if ((obj instanceof Boolean)) {
/*  414: 478 */       return "boolean";
/*  415:     */     }
/*  416: 479 */     if ((obj instanceof Number)) {
/*  417: 480 */       return "number";
/*  418:     */     }
/*  419: 481 */     if ((obj instanceof DOM)) {
/*  420: 482 */       return "RTF";
/*  421:     */     }
/*  422: 483 */     if ((obj instanceof DTMAxisIterator)) {
/*  423: 484 */       return "node-set";
/*  424:     */     }
/*  425: 486 */     return "unknown";
/*  426:     */   }
/*  427:     */   
/*  428:     */   public static DTMAxisIterator nodesetF(Object obj)
/*  429:     */   {
/*  430: 493 */     if ((obj instanceof DOM))
/*  431:     */     {
/*  432: 495 */       DOM dom = (DOM)obj;
/*  433: 496 */       return new SingletonIterator(dom.getDocument(), true);
/*  434:     */     }
/*  435: 498 */     if ((obj instanceof DTMAxisIterator)) {
/*  436: 499 */       return (DTMAxisIterator)obj;
/*  437:     */     }
/*  438: 502 */     String className = obj.getClass().getName();
/*  439: 503 */     runTimeError("DATA_CONVERSION_ERR", "node-set", className);
/*  440: 504 */     return null;
/*  441:     */   }
/*  442:     */   
/*  443:     */   private static boolean isWhiteSpace(char ch)
/*  444:     */   {
/*  445: 511 */     return (ch == ' ') || (ch == '\t') || (ch == '\n') || (ch == '\r');
/*  446:     */   }
/*  447:     */   
/*  448:     */   private static boolean compareStrings(String lstring, String rstring, int op, DOM dom)
/*  449:     */   {
/*  450: 516 */     switch (op)
/*  451:     */     {
/*  452:     */     case 0: 
/*  453: 518 */       return lstring.equals(rstring);
/*  454:     */     case 1: 
/*  455: 521 */       return !lstring.equals(rstring);
/*  456:     */     case 2: 
/*  457: 524 */       return numberF(lstring, dom) > numberF(rstring, dom);
/*  458:     */     case 3: 
/*  459: 527 */       return numberF(lstring, dom) < numberF(rstring, dom);
/*  460:     */     case 4: 
/*  461: 530 */       return numberF(lstring, dom) >= numberF(rstring, dom);
/*  462:     */     case 5: 
/*  463: 533 */       return numberF(lstring, dom) <= numberF(rstring, dom);
/*  464:     */     }
/*  465: 536 */     runTimeError("RUN_TIME_INTERNAL_ERR", "compare()");
/*  466: 537 */     return false;
/*  467:     */   }
/*  468:     */   
/*  469:     */   public static boolean compare(DTMAxisIterator left, DTMAxisIterator right, int op, DOM dom)
/*  470:     */   {
/*  471: 547 */     left.reset();
/*  472:     */     int rnode;
/*  473:     */     int lnode;
/*  474: 549 */     for (; (lnode = left.next()) != -1; (rnode = right.next()) != -1)
/*  475:     */     {
/*  476:     */       int i;
/*  477: 550 */       String lvalue = dom.getStringValueX(i);
/*  478:     */       
/*  479:     */ 
/*  480: 553 */       right.reset();
/*  481: 554 */       continue;
/*  482:     */       int j;
/*  483: 556 */       if (i == j)
/*  484:     */       {
/*  485: 557 */         if (op == 0) {
/*  486: 558 */           return true;
/*  487:     */         }
/*  488: 559 */         if (op == 1) {}
/*  489:     */       }
/*  490: 563 */       else if (compareStrings(lvalue, dom.getStringValueX(j), op, dom))
/*  491:     */       {
/*  492: 565 */         return true;
/*  493:     */       }
/*  494:     */     }
/*  495: 569 */     return false;
/*  496:     */   }
/*  497:     */   
/*  498:     */   public static boolean compare(int node, DTMAxisIterator iterator, int op, DOM dom)
/*  499:     */   {
/*  500:     */     int rnode;
/*  501:     */     String value;
/*  502: 579 */     switch (op)
/*  503:     */     {
/*  504:     */     case 0: 
/*  505: 581 */       rnode = iterator.next();
/*  506: 582 */       if (rnode != -1)
/*  507:     */       {
/*  508: 583 */         value = dom.getStringValueX(node);
/*  509:     */         do
/*  510:     */         {
/*  511: 585 */           if ((node == rnode) || (value.equals(dom.getStringValueX(rnode)))) {
/*  512: 587 */             return true;
/*  513:     */           }
/*  514: 589 */         } while ((rnode = iterator.next()) != -1);
/*  515:     */       }
/*  516: 589 */       break;
/*  517:     */     case 1: 
/*  518: 593 */       rnode = iterator.next();
/*  519: 594 */       if (rnode != -1)
/*  520:     */       {
/*  521: 595 */         value = dom.getStringValueX(node);
/*  522:     */         do
/*  523:     */         {
/*  524: 597 */           if ((node != rnode) && (!value.equals(dom.getStringValueX(rnode)))) {
/*  525: 599 */             return true;
/*  526:     */           }
/*  527: 601 */         } while ((rnode = iterator.next()) != -1);
/*  528:     */       }
/*  529: 601 */       break;
/*  530:     */     case 3: 
/*  531: 606 */       while ((rnode = iterator.next()) != -1) {
/*  532: 607 */         if (rnode > node) {
/*  533: 607 */           return true;
/*  534:     */         }
/*  535:     */       }
/*  536: 609 */       break;
/*  537:     */     case 2: 
/*  538: 612 */       while ((rnode = iterator.next()) != -1) {
/*  539: 613 */         if (rnode < node) {
/*  540: 613 */           return true;
/*  541:     */         }
/*  542:     */       }
/*  543:     */     }
/*  544: 617 */     return false;
/*  545:     */   }
/*  546:     */   
/*  547:     */   public static boolean compare(DTMAxisIterator left, double rnumber, int op, DOM dom)
/*  548:     */   {
/*  549:     */     int node;
/*  550: 628 */     switch (op)
/*  551:     */     {
/*  552:     */     case 0: 
/*  553: 630 */       while ((node = left.next()) != -1)
/*  554:     */       {
/*  555:     */         int i;
/*  556: 631 */         if (numberF(dom.getStringValueX(i), dom) == rnumber) {
/*  557: 632 */           return true;
/*  558:     */         }
/*  559:     */       }
/*  560: 634 */       break;
/*  561:     */     case 1: 
/*  562: 637 */       while ((node = left.next()) != -1) {
/*  563: 638 */         if (numberF(dom.getStringValueX(node), dom) != rnumber) {
/*  564: 639 */           return true;
/*  565:     */         }
/*  566:     */       }
/*  567: 641 */       break;
/*  568:     */     case 2: 
/*  569: 644 */       while ((node = left.next()) != -1) {
/*  570: 645 */         if (numberF(dom.getStringValueX(node), dom) > rnumber) {
/*  571: 646 */           return true;
/*  572:     */         }
/*  573:     */       }
/*  574: 648 */       break;
/*  575:     */     case 3: 
/*  576: 651 */       while ((node = left.next()) != -1) {
/*  577: 652 */         if (numberF(dom.getStringValueX(node), dom) < rnumber) {
/*  578: 653 */           return true;
/*  579:     */         }
/*  580:     */       }
/*  581: 655 */       break;
/*  582:     */     case 4: 
/*  583: 658 */       while ((node = left.next()) != -1) {
/*  584: 659 */         if (numberF(dom.getStringValueX(node), dom) >= rnumber) {
/*  585: 660 */           return true;
/*  586:     */         }
/*  587:     */       }
/*  588: 662 */       break;
/*  589:     */     case 5: 
/*  590: 665 */       while ((node = left.next()) != -1) {
/*  591: 666 */         if (numberF(dom.getStringValueX(node), dom) <= rnumber) {
/*  592: 667 */           return true;
/*  593:     */         }
/*  594:     */       }
/*  595: 669 */       break;
/*  596:     */     default: 
/*  597: 672 */       runTimeError("RUN_TIME_INTERNAL_ERR", "compare()");
/*  598:     */     }
/*  599: 675 */     return false;
/*  600:     */   }
/*  601:     */   
/*  602:     */   public static boolean compare(DTMAxisIterator left, String rstring, int op, DOM dom)
/*  603:     */   {
/*  604:     */     int node;
/*  605: 685 */     while ((node = left.next()) != -1)
/*  606:     */     {
/*  607:     */       int i;
/*  608: 686 */       if (compareStrings(dom.getStringValueX(i), rstring, op, dom)) {
/*  609: 687 */         return true;
/*  610:     */       }
/*  611:     */     }
/*  612: 690 */     return false;
/*  613:     */   }
/*  614:     */   
/*  615:     */   public static boolean compare(Object left, Object right, int op, DOM dom)
/*  616:     */   {
/*  617: 697 */     boolean result = false;
/*  618: 698 */     boolean hasSimpleArgs = (hasSimpleType(left)) && (hasSimpleType(right));
/*  619: 700 */     if ((op != 0) && (op != 1))
/*  620:     */     {
/*  621: 702 */       if (((left instanceof Node)) || ((right instanceof Node)))
/*  622:     */       {
/*  623: 703 */         if ((left instanceof Boolean))
/*  624:     */         {
/*  625: 704 */           right = new Boolean(booleanF(right));
/*  626: 705 */           hasSimpleArgs = true;
/*  627:     */         }
/*  628: 707 */         if ((right instanceof Boolean))
/*  629:     */         {
/*  630: 708 */           left = new Boolean(booleanF(left));
/*  631: 709 */           hasSimpleArgs = true;
/*  632:     */         }
/*  633:     */       }
/*  634: 713 */       if (hasSimpleArgs)
/*  635:     */       {
/*  636: 714 */         switch (op)
/*  637:     */         {
/*  638:     */         case 2: 
/*  639: 716 */           return numberF(left, dom) > numberF(right, dom);
/*  640:     */         case 3: 
/*  641: 719 */           return numberF(left, dom) < numberF(right, dom);
/*  642:     */         case 4: 
/*  643: 722 */           return numberF(left, dom) >= numberF(right, dom);
/*  644:     */         case 5: 
/*  645: 725 */           return numberF(left, dom) <= numberF(right, dom);
/*  646:     */         }
/*  647: 728 */         runTimeError("RUN_TIME_INTERNAL_ERR", "compare()");
/*  648:     */       }
/*  649:     */     }
/*  650: 734 */     if (hasSimpleArgs)
/*  651:     */     {
/*  652: 735 */       if (((left instanceof Boolean)) || ((right instanceof Boolean))) {
/*  653: 736 */         result = booleanF(left) == booleanF(right);
/*  654: 738 */       } else if (((left instanceof Double)) || ((right instanceof Double)) || ((left instanceof Integer)) || ((right instanceof Integer))) {
/*  655: 740 */         result = numberF(left, dom) == numberF(right, dom);
/*  656:     */       } else {
/*  657: 743 */         result = stringF(left, dom).equals(stringF(right, dom));
/*  658:     */       }
/*  659: 746 */       if (op == 1) {
/*  660: 747 */         result = !result;
/*  661:     */       }
/*  662:     */     }
/*  663:     */     else
/*  664:     */     {
/*  665: 751 */       if ((left instanceof Node)) {
/*  666: 752 */         left = new SingletonIterator(((Node)left).node);
/*  667:     */       }
/*  668: 754 */       if ((right instanceof Node)) {
/*  669: 755 */         right = new SingletonIterator(((Node)right).node);
/*  670:     */       }
/*  671: 758 */       if ((hasSimpleType(left)) || (((left instanceof DOM)) && ((right instanceof DTMAxisIterator))))
/*  672:     */       {
/*  673: 761 */         Object temp = right;right = left;left = temp;
/*  674: 762 */         op = Operators.swapOp(op);
/*  675:     */       }
/*  676: 765 */       if ((left instanceof DOM))
/*  677:     */       {
/*  678: 766 */         if ((right instanceof Boolean))
/*  679:     */         {
/*  680: 767 */           result = ((Boolean)right).booleanValue();
/*  681: 768 */           return result == (op == 0);
/*  682:     */         }
/*  683: 771 */         String sleft = ((DOM)left).getStringValue();
/*  684: 773 */         if ((right instanceof Number)) {
/*  685: 774 */           result = ((Number)right).doubleValue() == stringToReal(sleft);
/*  686: 777 */         } else if ((right instanceof String)) {
/*  687: 778 */           result = sleft.equals((String)right);
/*  688: 780 */         } else if ((right instanceof DOM)) {
/*  689: 781 */           result = sleft.equals(((DOM)right).getStringValue());
/*  690:     */         }
/*  691: 784 */         if (op == 1) {
/*  692: 785 */           result = !result;
/*  693:     */         }
/*  694: 787 */         return result;
/*  695:     */       }
/*  696: 792 */       DTMAxisIterator iter = ((DTMAxisIterator)left).reset();
/*  697: 794 */       if ((right instanceof DTMAxisIterator))
/*  698:     */       {
/*  699: 795 */         result = compare(iter, (DTMAxisIterator)right, op, dom);
/*  700:     */       }
/*  701: 797 */       else if ((right instanceof String))
/*  702:     */       {
/*  703: 798 */         result = compare(iter, (String)right, op, dom);
/*  704:     */       }
/*  705: 800 */       else if ((right instanceof Number))
/*  706:     */       {
/*  707: 801 */         double temp = ((Number)right).doubleValue();
/*  708: 802 */         result = compare(iter, temp, op, dom);
/*  709:     */       }
/*  710: 804 */       else if ((right instanceof Boolean))
/*  711:     */       {
/*  712: 805 */         boolean temp = ((Boolean)right).booleanValue();
/*  713: 806 */         result = (iter.reset().next() != -1) == temp;
/*  714:     */       }
/*  715: 808 */       else if ((right instanceof DOM))
/*  716:     */       {
/*  717: 809 */         result = compare(iter, ((DOM)right).getStringValue(), op, dom);
/*  718:     */       }
/*  719:     */       else
/*  720:     */       {
/*  721: 812 */         if (right == null) {
/*  722: 813 */           return false;
/*  723:     */         }
/*  724: 816 */         String className = right.getClass().getName();
/*  725: 817 */         runTimeError("INVALID_ARGUMENT_ERR", className, "compare()");
/*  726:     */       }
/*  727:     */     }
/*  728: 820 */     return result;
/*  729:     */   }
/*  730:     */   
/*  731:     */   public static boolean testLanguage(String testLang, DOM dom, int node)
/*  732:     */   {
/*  733: 828 */     String nodeLang = dom.getLanguage(node);
/*  734: 829 */     if (nodeLang == null) {
/*  735: 830 */       return false;
/*  736:     */     }
/*  737: 832 */     nodeLang = nodeLang.toLowerCase();
/*  738:     */     
/*  739:     */ 
/*  740: 835 */     testLang = testLang.toLowerCase();
/*  741: 836 */     if (testLang.length() == 2) {
/*  742: 837 */       return nodeLang.startsWith(testLang);
/*  743:     */     }
/*  744: 840 */     return nodeLang.equals(testLang);
/*  745:     */   }
/*  746:     */   
/*  747:     */   private static boolean hasSimpleType(Object obj)
/*  748:     */   {
/*  749: 845 */     return ((obj instanceof Boolean)) || ((obj instanceof Double)) || ((obj instanceof Integer)) || ((obj instanceof String)) || ((obj instanceof Node)) || ((obj instanceof DOM));
/*  750:     */   }
/*  751:     */   
/*  752:     */   public static double stringToReal(String s)
/*  753:     */   {
/*  754:     */     try
/*  755:     */     {
/*  756: 855 */       return Double.valueOf(s).doubleValue();
/*  757:     */     }
/*  758:     */     catch (NumberFormatException e) {}
/*  759: 858 */     return (0.0D / 0.0D);
/*  760:     */   }
/*  761:     */   
/*  762:     */   public static int stringToInt(String s)
/*  763:     */   {
/*  764:     */     try
/*  765:     */     {
/*  766: 867 */       return Integer.parseInt(s);
/*  767:     */     }
/*  768:     */     catch (NumberFormatException e) {}
/*  769: 870 */     return -1;
/*  770:     */   }
/*  771:     */   
/*  772: 878 */   private static String defaultPattern = "";
/*  773:     */   private static FieldPosition _fieldPosition;
/*  774:     */   private static char[] _characterArray;
/*  775:     */   private static int prefixIndex;
/*  776:     */   public static final String RUN_TIME_INTERNAL_ERR = "RUN_TIME_INTERNAL_ERR";
/*  777:     */   public static final String RUN_TIME_COPY_ERR = "RUN_TIME_COPY_ERR";
/*  778:     */   public static final String DATA_CONVERSION_ERR = "DATA_CONVERSION_ERR";
/*  779:     */   public static final String EXTERNAL_FUNC_ERR = "EXTERNAL_FUNC_ERR";
/*  780:     */   public static final String EQUALITY_EXPR_ERR = "EQUALITY_EXPR_ERR";
/*  781:     */   public static final String INVALID_ARGUMENT_ERR = "INVALID_ARGUMENT_ERR";
/*  782:     */   public static final String FORMAT_NUMBER_ERR = "FORMAT_NUMBER_ERR";
/*  783:     */   public static final String ITERATOR_CLONE_ERR = "ITERATOR_CLONE_ERR";
/*  784:     */   public static final String AXIS_SUPPORT_ERR = "AXIS_SUPPORT_ERR";
/*  785:     */   public static final String TYPED_AXIS_SUPPORT_ERR = "TYPED_AXIS_SUPPORT_ERR";
/*  786:     */   public static final String STRAY_ATTRIBUTE_ERR = "STRAY_ATTRIBUTE_ERR";
/*  787:     */   public static final String STRAY_NAMESPACE_ERR = "STRAY_NAMESPACE_ERR";
/*  788:     */   public static final String NAMESPACE_PREFIX_ERR = "NAMESPACE_PREFIX_ERR";
/*  789:     */   public static final String DOM_ADAPTER_INIT_ERR = "DOM_ADAPTER_INIT_ERR";
/*  790:     */   public static final String PARSER_DTD_SUPPORT_ERR = "PARSER_DTD_SUPPORT_ERR";
/*  791:     */   public static final String NAMESPACES_SUPPORT_ERR = "NAMESPACES_SUPPORT_ERR";
/*  792:     */   public static final String CANT_RESOLVE_RELATIVE_URI_ERR = "CANT_RESOLVE_RELATIVE_URI_ERR";
/*  793:     */   public static final String UNSUPPORTED_XSL_ERR = "UNSUPPORTED_XSL_ERR";
/*  794:     */   public static final String UNSUPPORTED_EXT_ERR = "UNSUPPORTED_EXT_ERR";
/*  795:     */   public static final String UNKNOWN_TRANSLET_VERSION_ERR = "UNKNOWN_TRANSLET_VERSION_ERR";
/*  796:     */   public static final String INVALID_QNAME_ERR = "INVALID_QNAME_ERR";
/*  797:     */   public static final String INVALID_NCNAME_ERR = "INVALID_NCNAME_ERR";
/*  798:     */   public static final String UNALLOWED_EXTENSION_FUNCTION_ERR = "UNALLOWED_EXTENSION_FUNCTION_ERR";
/*  799:     */   public static final String UNALLOWED_EXTENSION_ELEMENT_ERR = "UNALLOWED_EXTENSION_ELEMENT_ERR";
/*  800:     */   private static ResourceBundle m_bundle;
/*  801:     */   public static final String ERROR_MESSAGES_KEY = "error-messages";
/*  802:     */   
/*  803:     */   public static String realToString(double d)
/*  804:     */   {
/*  805: 897 */     double m = Math.abs(d);
/*  806: 898 */     if ((m >= 0.001D) && (m < 10000000.0D))
/*  807:     */     {
/*  808: 899 */       String result = Double.toString(d);
/*  809: 900 */       int length = result.length();
/*  810: 902 */       if ((result.charAt(length - 2) == '.') && (result.charAt(length - 1) == '0')) {
/*  811: 904 */         return result.substring(0, length - 2);
/*  812:     */       }
/*  813: 906 */       return result;
/*  814:     */     }
/*  815: 909 */     if ((Double.isNaN(d)) || (Double.isInfinite(d))) {
/*  816: 910 */       return Double.toString(d);
/*  817:     */     }
/*  818: 911 */     return formatNumber(d, defaultPattern, defaultFormatter);
/*  819:     */   }
/*  820:     */   
/*  821:     */   public static int realToInt(double d)
/*  822:     */   {
/*  823: 919 */     return (int)d;
/*  824:     */   }
/*  825:     */   
/*  826:     */   public static String formatNumber(double number, String pattern, DecimalFormat formatter)
/*  827:     */   {
/*  828: 932 */     if (formatter == null) {
/*  829: 933 */       formatter = defaultFormatter;
/*  830:     */     }
/*  831:     */     try
/*  832:     */     {
/*  833: 936 */       StringBuffer result = new StringBuffer();
/*  834: 937 */       if (pattern != defaultPattern) {
/*  835: 938 */         formatter.applyLocalizedPattern(pattern);
/*  836:     */       }
/*  837: 940 */       formatter.format(number, result, _fieldPosition);
/*  838: 941 */       return result.toString();
/*  839:     */     }
/*  840:     */     catch (IllegalArgumentException e)
/*  841:     */     {
/*  842: 944 */       runTimeError("FORMAT_NUMBER_ERR", Double.toString(number), pattern);
/*  843:     */     }
/*  844: 945 */     return "";
/*  845:     */   }
/*  846:     */   
/*  847:     */   public static DTMAxisIterator referenceToNodeSet(Object obj)
/*  848:     */   {
/*  849: 955 */     if ((obj instanceof Node)) {
/*  850: 956 */       return new SingletonIterator(((Node)obj).node);
/*  851:     */     }
/*  852: 959 */     if ((obj instanceof DTMAxisIterator)) {
/*  853: 960 */       return ((DTMAxisIterator)obj).cloneIterator().reset();
/*  854:     */     }
/*  855: 963 */     String className = obj.getClass().getName();
/*  856: 964 */     runTimeError("DATA_CONVERSION_ERR", className, "node-set");
/*  857: 965 */     return null;
/*  858:     */   }
/*  859:     */   
/*  860:     */   public static NodeList referenceToNodeList(Object obj, DOM dom)
/*  861:     */   {
/*  862: 973 */     if (((obj instanceof Node)) || ((obj instanceof DTMAxisIterator)))
/*  863:     */     {
/*  864: 974 */       DTMAxisIterator iter = referenceToNodeSet(obj);
/*  865: 975 */       return dom.makeNodeList(iter);
/*  866:     */     }
/*  867: 977 */     if ((obj instanceof DOM))
/*  868:     */     {
/*  869: 978 */       dom = (DOM)obj;
/*  870: 979 */       return dom.makeNodeList(0);
/*  871:     */     }
/*  872: 982 */     String className = obj.getClass().getName();
/*  873: 983 */     runTimeError("DATA_CONVERSION_ERR", className, "org.w3c.dom.NodeList");
/*  874:     */     
/*  875: 985 */     return null;
/*  876:     */   }
/*  877:     */   
/*  878:     */   public static org.w3c.dom.Node referenceToNode(Object obj, DOM dom)
/*  879:     */   {
/*  880: 993 */     if (((obj instanceof Node)) || ((obj instanceof DTMAxisIterator)))
/*  881:     */     {
/*  882: 994 */       DTMAxisIterator iter = referenceToNodeSet(obj);
/*  883: 995 */       return dom.makeNode(iter);
/*  884:     */     }
/*  885: 997 */     if ((obj instanceof DOM))
/*  886:     */     {
/*  887: 998 */       dom = (DOM)obj;
/*  888: 999 */       DTMAxisIterator iter = dom.getChildren(0);
/*  889:1000 */       return dom.makeNode(iter);
/*  890:     */     }
/*  891:1003 */     String className = obj.getClass().getName();
/*  892:1004 */     runTimeError("DATA_CONVERSION_ERR", className, "org.w3c.dom.Node");
/*  893:1005 */     return null;
/*  894:     */   }
/*  895:     */   
/*  896:     */   public static long referenceToLong(Object obj)
/*  897:     */   {
/*  898:1013 */     if ((obj instanceof Number)) {
/*  899:1014 */       return ((Number)obj).longValue();
/*  900:     */     }
/*  901:1017 */     String className = obj.getClass().getName();
/*  902:1018 */     runTimeError("DATA_CONVERSION_ERR", className, Long.TYPE);
/*  903:1019 */     return 0L;
/*  904:     */   }
/*  905:     */   
/*  906:     */   public static double referenceToDouble(Object obj)
/*  907:     */   {
/*  908:1027 */     if ((obj instanceof Number)) {
/*  909:1028 */       return ((Number)obj).doubleValue();
/*  910:     */     }
/*  911:1031 */     String className = obj.getClass().getName();
/*  912:1032 */     runTimeError("DATA_CONVERSION_ERR", className, Double.TYPE);
/*  913:1033 */     return 0.0D;
/*  914:     */   }
/*  915:     */   
/*  916:     */   public static boolean referenceToBoolean(Object obj)
/*  917:     */   {
/*  918:1041 */     if ((obj instanceof Boolean)) {
/*  919:1042 */       return ((Boolean)obj).booleanValue();
/*  920:     */     }
/*  921:1045 */     String className = obj.getClass().getName();
/*  922:1046 */     runTimeError("DATA_CONVERSION_ERR", className, Boolean.TYPE);
/*  923:1047 */     return false;
/*  924:     */   }
/*  925:     */   
/*  926:     */   public static String referenceToString(Object obj, DOM dom)
/*  927:     */   {
/*  928:1055 */     if ((obj instanceof String)) {
/*  929:1056 */       return (String)obj;
/*  930:     */     }
/*  931:1058 */     if ((obj instanceof DTMAxisIterator)) {
/*  932:1059 */       return dom.getStringValueX(((DTMAxisIterator)obj).reset().next());
/*  933:     */     }
/*  934:1061 */     if ((obj instanceof Node)) {
/*  935:1062 */       return dom.getStringValueX(((Node)obj).node);
/*  936:     */     }
/*  937:1064 */     if ((obj instanceof DOM)) {
/*  938:1065 */       return ((DOM)obj).getStringValue();
/*  939:     */     }
/*  940:1068 */     String className = obj.getClass().getName();
/*  941:1069 */     runTimeError("DATA_CONVERSION_ERR", className, String.class);
/*  942:1070 */     return null;
/*  943:     */   }
/*  944:     */   
/*  945:     */   public static DTMAxisIterator node2Iterator(org.w3c.dom.Node node, Translet translet, DOM dom)
/*  946:     */   {
/*  947:1080 */     org.w3c.dom.Node inNode = node;
/*  948:     */     
/*  949:     */ 
/*  950:1083 */     NodeList nodelist = new NodeList()
/*  951:     */     {
/*  952:     */       private final org.w3c.dom.Node val$inNode;
/*  953:     */       
/*  954:     */       public int getLength()
/*  955:     */       {
/*  956:1085 */         return 1;
/*  957:     */       }
/*  958:     */       
/*  959:     */       public org.w3c.dom.Node item(int index)
/*  960:     */       {
/*  961:1089 */         if (index == 0) {
/*  962:1090 */           return this.val$inNode;
/*  963:     */         }
/*  964:1092 */         return null;
/*  965:     */       }
/*  966:1095 */     };
/*  967:1096 */     return nodeList2Iterator(nodelist, translet, dom);
/*  968:     */   }
/*  969:     */   
/*  970:     */   private static void copyNodes(NodeList nodeList, Document doc, org.w3c.dom.Node parent)
/*  971:     */   {
/*  972:1105 */     int size = nodeList.getLength();
/*  973:1108 */     for (int i = 0; i < size; i++)
/*  974:     */     {
/*  975:1110 */       org.w3c.dom.Node curr = nodeList.item(i);
/*  976:1111 */       int nodeType = curr.getNodeType();
/*  977:1112 */       String value = null;
/*  978:     */       try
/*  979:     */       {
/*  980:1114 */         value = curr.getNodeValue();
/*  981:     */       }
/*  982:     */       catch (DOMException ex)
/*  983:     */       {
/*  984:1116 */         runTimeError("RUN_TIME_INTERNAL_ERR", ex.getMessage());
/*  985:1117 */         return;
/*  986:     */       }
/*  987:1120 */       String nodeName = curr.getNodeName();
/*  988:1121 */       org.w3c.dom.Node newNode = null;
/*  989:1122 */       switch (nodeType)
/*  990:     */       {
/*  991:     */       case 2: 
/*  992:1124 */         newNode = doc.createAttributeNS(curr.getNamespaceURI(), nodeName);
/*  993:     */         
/*  994:1126 */         break;
/*  995:     */       case 4: 
/*  996:1128 */         newNode = doc.createCDATASection(value);
/*  997:1129 */         break;
/*  998:     */       case 8: 
/*  999:1131 */         newNode = doc.createComment(value);
/* 1000:1132 */         break;
/* 1001:     */       case 11: 
/* 1002:1134 */         newNode = doc.createDocumentFragment();
/* 1003:1135 */         break;
/* 1004:     */       case 9: 
/* 1005:1137 */         newNode = doc.createElementNS(null, "__document__");
/* 1006:1138 */         copyNodes(curr.getChildNodes(), doc, newNode);
/* 1007:1139 */         break;
/* 1008:     */       case 10: 
/* 1009:     */         break;
/* 1010:     */       case 1: 
/* 1011:1146 */         Element element = doc.createElementNS(curr.getNamespaceURI(), nodeName);
/* 1012:1148 */         if (curr.hasAttributes())
/* 1013:     */         {
/* 1014:1150 */           NamedNodeMap attributes = curr.getAttributes();
/* 1015:1151 */           for (int k = 0; k < attributes.getLength(); k++)
/* 1016:     */           {
/* 1017:1152 */             org.w3c.dom.Node attr = attributes.item(k);
/* 1018:1153 */             element.setAttributeNS(attr.getNamespaceURI(), attr.getNodeName(), attr.getNodeValue());
/* 1019:     */           }
/* 1020:     */         }
/* 1021:1157 */         copyNodes(curr.getChildNodes(), doc, element);
/* 1022:1158 */         newNode = element;
/* 1023:1159 */         break;
/* 1024:     */       case 6: 
/* 1025:     */         break;
/* 1026:     */       case 5: 
/* 1027:1164 */         newNode = doc.createEntityReference(nodeName);
/* 1028:1165 */         break;
/* 1029:     */       case 12: 
/* 1030:     */         break;
/* 1031:     */       case 7: 
/* 1032:1170 */         newNode = doc.createProcessingInstruction(nodeName, value);
/* 1033:     */         
/* 1034:1172 */         break;
/* 1035:     */       case 3: 
/* 1036:1174 */         newNode = doc.createTextNode(value);
/* 1037:     */       }
/* 1038:     */       try
/* 1039:     */       {
/* 1040:1178 */         parent.appendChild(newNode);
/* 1041:     */       }
/* 1042:     */       catch (DOMException e)
/* 1043:     */       {
/* 1044:1180 */         runTimeError("RUN_TIME_INTERNAL_ERR", e.getMessage());
/* 1045:1181 */         return;
/* 1046:     */       }
/* 1047:     */     }
/* 1048:     */   }
/* 1049:     */   
/* 1050:     */   public static DTMAxisIterator nodeList2Iterator(NodeList nodeList, Translet translet, DOM dom)
/* 1051:     */   {
/* 1052:1195 */     Document doc = null;
/* 1053:     */     try
/* 1054:     */     {
/* 1055:1197 */       doc = ((AbstractTranslet)translet).newDocument("", "__top__");
/* 1056:     */     }
/* 1057:     */     catch (ParserConfigurationException e)
/* 1058:     */     {
/* 1059:1200 */       runTimeError("RUN_TIME_INTERNAL_ERR", e.getMessage());
/* 1060:1201 */       return null;
/* 1061:     */     }
/* 1062:1205 */     copyNodes(nodeList, doc, doc.getDocumentElement());
/* 1063:1208 */     if ((dom instanceof MultiDOM))
/* 1064:     */     {
/* 1065:1209 */       MultiDOM multiDOM = (MultiDOM)dom;
/* 1066:     */       
/* 1067:1211 */       DTMDefaultBase dtm = (DTMDefaultBase)((DOMAdapter)multiDOM.getMain()).getDOMImpl();
/* 1068:1212 */       DTMManager dtmManager = dtm.getManager();
/* 1069:     */       
/* 1070:1214 */       DOM idom = (DOM)dtmManager.getDTM(new DOMSource(doc), false, null, true, false);
/* 1071:     */       
/* 1072:     */ 
/* 1073:1217 */       DOMAdapter domAdapter = new DOMAdapter(idom, translet.getNamesArray(), translet.getUrisArray(), translet.getTypesArray(), translet.getNamespaceArray());
/* 1074:     */       
/* 1075:     */ 
/* 1076:     */ 
/* 1077:     */ 
/* 1078:1222 */       multiDOM.addDOMAdapter(domAdapter);
/* 1079:     */       
/* 1080:1224 */       DTMAxisIterator iter1 = idom.getAxisIterator(3);
/* 1081:1225 */       DTMAxisIterator iter2 = idom.getAxisIterator(3);
/* 1082:1226 */       DTMAxisIterator iter = new AbsoluteIterator(new StepIterator(iter1, iter2));
/* 1083:     */       
/* 1084:     */ 
/* 1085:1229 */       iter.setStartNode(0);
/* 1086:1230 */       return iter;
/* 1087:     */     }
/* 1088:1233 */     runTimeError("RUN_TIME_INTERNAL_ERR", "nodeList2Iterator()");
/* 1089:1234 */     return null;
/* 1090:     */   }
/* 1091:     */   
/* 1092:     */   public static DOM referenceToResultTree(Object obj)
/* 1093:     */   {
/* 1094:     */     try
/* 1095:     */     {
/* 1096:1243 */       return (DOM)obj;
/* 1097:     */     }
/* 1098:     */     catch (IllegalArgumentException e)
/* 1099:     */     {
/* 1100:1246 */       String className = obj.getClass().getName();
/* 1101:1247 */       runTimeError("DATA_CONVERSION_ERR", "reference", className);
/* 1102:     */     }
/* 1103:1248 */     return null;
/* 1104:     */   }
/* 1105:     */   
/* 1106:     */   public static DTMAxisIterator getSingleNode(DTMAxisIterator iterator)
/* 1107:     */   {
/* 1108:1257 */     int node = iterator.next();
/* 1109:1258 */     return new SingletonIterator(node);
/* 1110:     */   }
/* 1111:     */   
/* 1112:     */   public static void copy(Object obj, SerializationHandler handler, int node, DOM dom)
/* 1113:     */   {
/* 1114:     */     try
/* 1115:     */     {
/* 1116:1271 */       if ((obj instanceof DTMAxisIterator))
/* 1117:     */       {
/* 1118:1273 */         DTMAxisIterator iter = (DTMAxisIterator)obj;
/* 1119:1274 */         dom.copy(iter.reset(), handler);
/* 1120:     */       }
/* 1121:1276 */       else if ((obj instanceof Node))
/* 1122:     */       {
/* 1123:1277 */         dom.copy(((Node)obj).node, handler);
/* 1124:     */       }
/* 1125:1279 */       else if ((obj instanceof DOM))
/* 1126:     */       {
/* 1127:1281 */         DOM newDom = (DOM)obj;
/* 1128:1282 */         newDom.copy(newDom.getDocument(), handler);
/* 1129:     */       }
/* 1130:     */       else
/* 1131:     */       {
/* 1132:1285 */         String string = obj.toString();
/* 1133:1286 */         int length = string.length();
/* 1134:1287 */         if (length > _characterArray.length) {
/* 1135:1288 */           _characterArray = new char[length];
/* 1136:     */         }
/* 1137:1289 */         string.getChars(0, length, _characterArray, 0);
/* 1138:1290 */         handler.characters(_characterArray, 0, length);
/* 1139:     */       }
/* 1140:     */     }
/* 1141:     */     catch (SAXException e)
/* 1142:     */     {
/* 1143:1294 */       runTimeError("RUN_TIME_COPY_ERR");
/* 1144:     */     }
/* 1145:     */   }
/* 1146:     */   
/* 1147:     */   public static void checkAttribQName(String name)
/* 1148:     */   {
/* 1149:1303 */     int firstOccur = name.indexOf(":");
/* 1150:1304 */     int lastOccur = name.lastIndexOf(":");
/* 1151:1305 */     String localName = name.substring(lastOccur + 1);
/* 1152:1307 */     if (firstOccur > 0)
/* 1153:     */     {
/* 1154:1308 */       String newPrefix = name.substring(0, firstOccur);
/* 1155:1310 */       if (firstOccur != lastOccur)
/* 1156:     */       {
/* 1157:1311 */         String oriPrefix = name.substring(firstOccur + 1, lastOccur);
/* 1158:1312 */         if (!XML11Char.isXML11ValidNCName(oriPrefix)) {
/* 1159:1314 */           runTimeError("INVALID_QNAME_ERR", oriPrefix + ":" + localName);
/* 1160:     */         }
/* 1161:     */       }
/* 1162:1319 */       if (!XML11Char.isXML11ValidNCName(newPrefix)) {
/* 1163:1320 */         runTimeError("INVALID_QNAME_ERR", newPrefix + ":" + localName);
/* 1164:     */       }
/* 1165:     */     }
/* 1166:1325 */     if ((!XML11Char.isXML11ValidNCName(localName)) || (localName.equals("xmlns"))) {
/* 1167:1326 */       runTimeError("INVALID_QNAME_ERR", localName);
/* 1168:     */     }
/* 1169:     */   }
/* 1170:     */   
/* 1171:     */   public static void checkNCName(String name)
/* 1172:     */   {
/* 1173:1335 */     if (!XML11Char.isXML11ValidNCName(name)) {
/* 1174:1336 */       runTimeError("INVALID_NCNAME_ERR", name);
/* 1175:     */     }
/* 1176:     */   }
/* 1177:     */   
/* 1178:     */   public static void checkQName(String name)
/* 1179:     */   {
/* 1180:1345 */     if (!XML11Char.isXML11ValidQName(name)) {
/* 1181:1346 */       runTimeError("INVALID_QNAME_ERR", name);
/* 1182:     */     }
/* 1183:     */   }
/* 1184:     */   
/* 1185:     */   public static String startXslElement(String qname, String namespace, SerializationHandler handler, DOM dom, int node)
/* 1186:     */   {
/* 1187:     */     try
/* 1188:     */     {
/* 1189:1359 */       int index = qname.indexOf(':');
/* 1190:     */       String prefix;
/* 1191:1361 */       if (index > 0)
/* 1192:     */       {
/* 1193:1362 */         prefix = qname.substring(0, index);
/* 1194:1365 */         if ((namespace == null) || (namespace.length() == 0)) {
/* 1195:1366 */           runTimeError("NAMESPACE_PREFIX_ERR", prefix);
/* 1196:     */         }
/* 1197:1369 */         handler.startElement(namespace, qname.substring(index + 1), qname);
/* 1198:     */         
/* 1199:1371 */         handler.namespaceAfterStartElement(prefix, namespace);
/* 1200:     */       }
/* 1201:1375 */       else if ((namespace != null) && (namespace.length() > 0))
/* 1202:     */       {
/* 1203:1376 */         prefix = generatePrefix();
/* 1204:1377 */         qname = prefix + ':' + qname;
/* 1205:1378 */         handler.startElement(namespace, qname, qname);
/* 1206:1379 */         handler.namespaceAfterStartElement(prefix, namespace);
/* 1207:     */       }
/* 1208:     */       else
/* 1209:     */       {
/* 1210:1382 */         handler.startElement(null, null, qname);
/* 1211:     */       }
/* 1212:     */     }
/* 1213:     */     catch (SAXException e)
/* 1214:     */     {
/* 1215:1387 */       throw new RuntimeException(e.getMessage());
/* 1216:     */     }
/* 1217:1390 */     return qname;
/* 1218:     */   }
/* 1219:     */   
/* 1220:     */   public static String lookupStylesheetQNameNamespace(String lexicalQName, int stylesheetNodeID, int[] ancestorNodeIDs, int[] prefixURIsIndex, String[] prefixURIPairs, boolean ignoreDefault)
/* 1221:     */   {
/* 1222:1427 */     String prefix = getPrefix(lexicalQName);
/* 1223:1428 */     String uri = "";
/* 1224:1430 */     if ((prefix == null) && (!ignoreDefault)) {
/* 1225:1431 */       prefix = "";
/* 1226:     */     }
/* 1227:1434 */     if (prefix != null) {
/* 1228:1437 */       for (int currentNodeID = stylesheetNodeID; currentNodeID >= 0; currentNodeID = ancestorNodeIDs[currentNodeID])
/* 1229:     */       {
/* 1230:1446 */         int prefixStartIdx = prefixURIsIndex[currentNodeID];
/* 1231:1447 */         int prefixLimitIdx = currentNodeID + 1 < prefixURIsIndex.length ? prefixURIsIndex[(currentNodeID + 1)] : prefixURIPairs.length;
/* 1232:     */         
/* 1233:     */ 
/* 1234:     */ 
/* 1235:1451 */         int prefixIdx = prefixStartIdx;
/* 1236:1452 */         for (; prefixIdx < prefixLimitIdx; prefixIdx += 2) {
/* 1237:1455 */           if (prefix.equals(prefixURIPairs[prefixIdx]))
/* 1238:     */           {
/* 1239:1456 */             uri = prefixURIPairs[(prefixIdx + 1)];
/* 1240:1457 */             break;
/* 1241:     */           }
/* 1242:     */         }
/* 1243:     */       }
/* 1244:     */     }
/* 1245:1463 */     return uri;
/* 1246:     */   }
/* 1247:     */   
/* 1248:     */   public static String expandStylesheetQNameRef(String lexicalQName, int stylesheetNodeID, int[] ancestorNodeIDs, int[] prefixURIsIndex, String[] prefixURIPairs, boolean ignoreDefault)
/* 1249:     */   {
/* 1250:1502 */     String prefix = getPrefix(lexicalQName);
/* 1251:1503 */     String localName = prefix != null ? lexicalQName.substring(prefix.length() + 1) : lexicalQName;
/* 1252:     */     
/* 1253:     */ 
/* 1254:1506 */     String uri = lookupStylesheetQNameNamespace(lexicalQName, stylesheetNodeID, ancestorNodeIDs, prefixURIsIndex, prefixURIPairs, ignoreDefault);
/* 1255:1514 */     if ((prefix != null) && (prefix.length() != 0) && ((uri == null) || (uri.length() == 0))) {
/* 1256:1516 */       runTimeError("NAMESPACE_PREFIX_ERR", prefix);
/* 1257:     */     }
/* 1258:     */     String expandedQName;
/* 1259:1519 */     if (uri.length() == 0) {
/* 1260:1520 */       expandedQName = localName;
/* 1261:     */     } else {
/* 1262:1522 */       expandedQName = uri + ':' + localName;
/* 1263:     */     }
/* 1264:1525 */     return expandedQName;
/* 1265:     */   }
/* 1266:     */   
/* 1267:     */   public static String getPrefix(String qname)
/* 1268:     */   {
/* 1269:1532 */     int index = qname.indexOf(':');
/* 1270:1533 */     return index > 0 ? qname.substring(0, index) : null;
/* 1271:     */   }
/* 1272:     */   
/* 1273:     */   public static String generatePrefix()
/* 1274:     */   {
/* 1275:1541 */     return "ns" + prefixIndex++;
/* 1276:     */   }
/* 1277:     */   
/* 1278:     */   static
/* 1279:     */   {
/* 1280: 881 */     NumberFormat f = NumberFormat.getInstance(Locale.getDefault());
/* 1281: 882 */     defaultFormatter = (f instanceof DecimalFormat) ? (DecimalFormat)f : new DecimalFormat();
/* 1282:     */     
/* 1283:     */ 
/* 1284:     */ 
/* 1285: 886 */     defaultFormatter.setMaximumFractionDigits(340);
/* 1286: 887 */     defaultFormatter.setMinimumFractionDigits(0);
/* 1287: 888 */     defaultFormatter.setMinimumIntegerDigits(1);
/* 1288: 889 */     defaultFormatter.setGroupingUsed(false);
/* 1289:     */     
/* 1290:     */ 
/* 1291:     */ 
/* 1292:     */ 
/* 1293:     */ 
/* 1294:     */ 
/* 1295:     */ 
/* 1296:     */ 
/* 1297:     */ 
/* 1298:     */ 
/* 1299:     */ 
/* 1300:     */ 
/* 1301:     */ 
/* 1302:     */ 
/* 1303:     */ 
/* 1304:     */ 
/* 1305:     */ 
/* 1306:     */ 
/* 1307:     */ 
/* 1308:     */ 
/* 1309:     */ 
/* 1310:     */ 
/* 1311:     */ 
/* 1312:     */ 
/* 1313:     */ 
/* 1314:     */ 
/* 1315:     */ 
/* 1316:     */ 
/* 1317:     */ 
/* 1318:     */ 
/* 1319:     */ 
/* 1320:     */ 
/* 1321:     */ 
/* 1322:     */ 
/* 1323:     */ 
/* 1324:     */ 
/* 1325:     */ 
/* 1326: 927 */     _fieldPosition = new FieldPosition(0);
/* 1327:     */     
/* 1328:     */ 
/* 1329:     */ 
/* 1330:     */ 
/* 1331:     */ 
/* 1332:     */ 
/* 1333:     */ 
/* 1334:     */ 
/* 1335:     */ 
/* 1336:     */ 
/* 1337:     */ 
/* 1338:     */ 
/* 1339:     */ 
/* 1340:     */ 
/* 1341:     */ 
/* 1342:     */ 
/* 1343:     */ 
/* 1344:     */ 
/* 1345:     */ 
/* 1346:     */ 
/* 1347:     */ 
/* 1348:     */ 
/* 1349:     */ 
/* 1350:     */ 
/* 1351:     */ 
/* 1352:     */ 
/* 1353:     */ 
/* 1354:     */ 
/* 1355:     */ 
/* 1356:     */ 
/* 1357:     */ 
/* 1358:     */ 
/* 1359:     */ 
/* 1360:     */ 
/* 1361:     */ 
/* 1362:     */ 
/* 1363:     */ 
/* 1364:     */ 
/* 1365:     */ 
/* 1366:     */ 
/* 1367:     */ 
/* 1368:     */ 
/* 1369:     */ 
/* 1370:     */ 
/* 1371:     */ 
/* 1372:     */ 
/* 1373:     */ 
/* 1374:     */ 
/* 1375:     */ 
/* 1376:     */ 
/* 1377:     */ 
/* 1378:     */ 
/* 1379:     */ 
/* 1380:     */ 
/* 1381:     */ 
/* 1382:     */ 
/* 1383:     */ 
/* 1384:     */ 
/* 1385:     */ 
/* 1386:     */ 
/* 1387:     */ 
/* 1388:     */ 
/* 1389:     */ 
/* 1390:     */ 
/* 1391:     */ 
/* 1392:     */ 
/* 1393:     */ 
/* 1394:     */ 
/* 1395:     */ 
/* 1396:     */ 
/* 1397:     */ 
/* 1398:     */ 
/* 1399:     */ 
/* 1400:     */ 
/* 1401:     */ 
/* 1402:     */ 
/* 1403:     */ 
/* 1404:     */ 
/* 1405:     */ 
/* 1406:     */ 
/* 1407:     */ 
/* 1408:     */ 
/* 1409:     */ 
/* 1410:     */ 
/* 1411:     */ 
/* 1412:     */ 
/* 1413:     */ 
/* 1414:     */ 
/* 1415:     */ 
/* 1416:     */ 
/* 1417:     */ 
/* 1418:     */ 
/* 1419:     */ 
/* 1420:     */ 
/* 1421:     */ 
/* 1422:     */ 
/* 1423:     */ 
/* 1424:     */ 
/* 1425:     */ 
/* 1426:     */ 
/* 1427:     */ 
/* 1428:     */ 
/* 1429:     */ 
/* 1430:     */ 
/* 1431:     */ 
/* 1432:     */ 
/* 1433:     */ 
/* 1434:     */ 
/* 1435:     */ 
/* 1436:     */ 
/* 1437:     */ 
/* 1438:     */ 
/* 1439:     */ 
/* 1440:     */ 
/* 1441:     */ 
/* 1442:     */ 
/* 1443:     */ 
/* 1444:     */ 
/* 1445:     */ 
/* 1446:     */ 
/* 1447:     */ 
/* 1448:     */ 
/* 1449:     */ 
/* 1450:     */ 
/* 1451:     */ 
/* 1452:     */ 
/* 1453:     */ 
/* 1454:     */ 
/* 1455:     */ 
/* 1456:     */ 
/* 1457:     */ 
/* 1458:     */ 
/* 1459:     */ 
/* 1460:     */ 
/* 1461:     */ 
/* 1462:     */ 
/* 1463:     */ 
/* 1464:     */ 
/* 1465:     */ 
/* 1466:     */ 
/* 1467:     */ 
/* 1468:     */ 
/* 1469:     */ 
/* 1470:     */ 
/* 1471:     */ 
/* 1472:     */ 
/* 1473:     */ 
/* 1474:     */ 
/* 1475:     */ 
/* 1476:     */ 
/* 1477:     */ 
/* 1478:     */ 
/* 1479:     */ 
/* 1480:     */ 
/* 1481:     */ 
/* 1482:     */ 
/* 1483:     */ 
/* 1484:     */ 
/* 1485:     */ 
/* 1486:     */ 
/* 1487:     */ 
/* 1488:     */ 
/* 1489:     */ 
/* 1490:     */ 
/* 1491:     */ 
/* 1492:     */ 
/* 1493:     */ 
/* 1494:     */ 
/* 1495:     */ 
/* 1496:     */ 
/* 1497:     */ 
/* 1498:     */ 
/* 1499:     */ 
/* 1500:     */ 
/* 1501:     */ 
/* 1502:     */ 
/* 1503:     */ 
/* 1504:     */ 
/* 1505:     */ 
/* 1506:     */ 
/* 1507:     */ 
/* 1508:     */ 
/* 1509:     */ 
/* 1510:     */ 
/* 1511:     */ 
/* 1512:     */ 
/* 1513:     */ 
/* 1514:     */ 
/* 1515:     */ 
/* 1516:     */ 
/* 1517:     */ 
/* 1518:     */ 
/* 1519:     */ 
/* 1520:     */ 
/* 1521:     */ 
/* 1522:     */ 
/* 1523:     */ 
/* 1524:     */ 
/* 1525:     */ 
/* 1526:     */ 
/* 1527:     */ 
/* 1528:     */ 
/* 1529:     */ 
/* 1530:     */ 
/* 1531:     */ 
/* 1532:     */ 
/* 1533:     */ 
/* 1534:     */ 
/* 1535:     */ 
/* 1536:     */ 
/* 1537:     */ 
/* 1538:     */ 
/* 1539:     */ 
/* 1540:     */ 
/* 1541:     */ 
/* 1542:     */ 
/* 1543:     */ 
/* 1544:     */ 
/* 1545:     */ 
/* 1546:     */ 
/* 1547:     */ 
/* 1548:     */ 
/* 1549:     */ 
/* 1550:     */ 
/* 1551:     */ 
/* 1552:     */ 
/* 1553:     */ 
/* 1554:     */ 
/* 1555:     */ 
/* 1556:     */ 
/* 1557:     */ 
/* 1558:     */ 
/* 1559:     */ 
/* 1560:     */ 
/* 1561:     */ 
/* 1562:     */ 
/* 1563:     */ 
/* 1564:     */ 
/* 1565:     */ 
/* 1566:     */ 
/* 1567:     */ 
/* 1568:     */ 
/* 1569:     */ 
/* 1570:     */ 
/* 1571:     */ 
/* 1572:     */ 
/* 1573:     */ 
/* 1574:     */ 
/* 1575:     */ 
/* 1576:     */ 
/* 1577:     */ 
/* 1578:     */ 
/* 1579:     */ 
/* 1580:     */ 
/* 1581:     */ 
/* 1582:     */ 
/* 1583:     */ 
/* 1584:     */ 
/* 1585:     */ 
/* 1586:     */ 
/* 1587:     */ 
/* 1588:     */ 
/* 1589:     */ 
/* 1590:     */ 
/* 1591:     */ 
/* 1592:     */ 
/* 1593:     */ 
/* 1594:     */ 
/* 1595:     */ 
/* 1596:     */ 
/* 1597:     */ 
/* 1598:     */ 
/* 1599:     */ 
/* 1600:     */ 
/* 1601:     */ 
/* 1602:     */ 
/* 1603:     */ 
/* 1604:     */ 
/* 1605:     */ 
/* 1606:     */ 
/* 1607:     */ 
/* 1608:     */ 
/* 1609:     */ 
/* 1610:     */ 
/* 1611:     */ 
/* 1612:     */ 
/* 1613:     */ 
/* 1614:     */ 
/* 1615:     */ 
/* 1616:     */ 
/* 1617:     */ 
/* 1618:     */ 
/* 1619:     */ 
/* 1620:     */ 
/* 1621:     */ 
/* 1622:     */ 
/* 1623:     */ 
/* 1624:     */ 
/* 1625:     */ 
/* 1626:     */ 
/* 1627:     */ 
/* 1628:     */ 
/* 1629:     */ 
/* 1630:     */ 
/* 1631:     */ 
/* 1632:     */ 
/* 1633:     */ 
/* 1634:     */ 
/* 1635:     */ 
/* 1636:     */ 
/* 1637:     */ 
/* 1638:     */ 
/* 1639:     */ 
/* 1640:     */ 
/* 1641:     */ 
/* 1642:     */ 
/* 1643:     */ 
/* 1644:     */ 
/* 1645:     */ 
/* 1646:     */ 
/* 1647:     */ 
/* 1648:     */ 
/* 1649:     */ 
/* 1650:     */ 
/* 1651:     */ 
/* 1652:     */ 
/* 1653:     */ 
/* 1654:     */ 
/* 1655:     */ 
/* 1656:     */ 
/* 1657:     */ 
/* 1658:     */ 
/* 1659:     */ 
/* 1660:     */ 
/* 1661:     */ 
/* 1662:     */ 
/* 1663:1264 */     _characterArray = new char[32];
/* 1664:     */     
/* 1665:     */ 
/* 1666:     */ 
/* 1667:     */ 
/* 1668:     */ 
/* 1669:     */ 
/* 1670:     */ 
/* 1671:     */ 
/* 1672:     */ 
/* 1673:     */ 
/* 1674:     */ 
/* 1675:     */ 
/* 1676:     */ 
/* 1677:     */ 
/* 1678:     */ 
/* 1679:     */ 
/* 1680:     */ 
/* 1681:     */ 
/* 1682:     */ 
/* 1683:     */ 
/* 1684:     */ 
/* 1685:     */ 
/* 1686:     */ 
/* 1687:     */ 
/* 1688:     */ 
/* 1689:     */ 
/* 1690:     */ 
/* 1691:     */ 
/* 1692:     */ 
/* 1693:     */ 
/* 1694:     */ 
/* 1695:     */ 
/* 1696:     */ 
/* 1697:     */ 
/* 1698:     */ 
/* 1699:     */ 
/* 1700:     */ 
/* 1701:     */ 
/* 1702:     */ 
/* 1703:     */ 
/* 1704:     */ 
/* 1705:     */ 
/* 1706:     */ 
/* 1707:     */ 
/* 1708:     */ 
/* 1709:     */ 
/* 1710:     */ 
/* 1711:     */ 
/* 1712:     */ 
/* 1713:     */ 
/* 1714:     */ 
/* 1715:     */ 
/* 1716:     */ 
/* 1717:     */ 
/* 1718:     */ 
/* 1719:     */ 
/* 1720:     */ 
/* 1721:     */ 
/* 1722:     */ 
/* 1723:     */ 
/* 1724:     */ 
/* 1725:     */ 
/* 1726:     */ 
/* 1727:     */ 
/* 1728:     */ 
/* 1729:     */ 
/* 1730:     */ 
/* 1731:     */ 
/* 1732:     */ 
/* 1733:     */ 
/* 1734:     */ 
/* 1735:     */ 
/* 1736:     */ 
/* 1737:     */ 
/* 1738:     */ 
/* 1739:     */ 
/* 1740:     */ 
/* 1741:     */ 
/* 1742:     */ 
/* 1743:     */ 
/* 1744:     */ 
/* 1745:     */ 
/* 1746:     */ 
/* 1747:     */ 
/* 1748:     */ 
/* 1749:     */ 
/* 1750:     */ 
/* 1751:     */ 
/* 1752:     */ 
/* 1753:     */ 
/* 1754:     */ 
/* 1755:     */ 
/* 1756:     */ 
/* 1757:     */ 
/* 1758:     */ 
/* 1759:     */ 
/* 1760:     */ 
/* 1761:     */ 
/* 1762:     */ 
/* 1763:     */ 
/* 1764:     */ 
/* 1765:     */ 
/* 1766:     */ 
/* 1767:     */ 
/* 1768:     */ 
/* 1769:     */ 
/* 1770:     */ 
/* 1771:     */ 
/* 1772:     */ 
/* 1773:     */ 
/* 1774:     */ 
/* 1775:     */ 
/* 1776:     */ 
/* 1777:     */ 
/* 1778:     */ 
/* 1779:     */ 
/* 1780:     */ 
/* 1781:     */ 
/* 1782:     */ 
/* 1783:     */ 
/* 1784:     */ 
/* 1785:     */ 
/* 1786:     */ 
/* 1787:     */ 
/* 1788:     */ 
/* 1789:     */ 
/* 1790:     */ 
/* 1791:     */ 
/* 1792:     */ 
/* 1793:     */ 
/* 1794:     */ 
/* 1795:     */ 
/* 1796:     */ 
/* 1797:     */ 
/* 1798:     */ 
/* 1799:     */ 
/* 1800:     */ 
/* 1801:     */ 
/* 1802:     */ 
/* 1803:     */ 
/* 1804:     */ 
/* 1805:     */ 
/* 1806:     */ 
/* 1807:     */ 
/* 1808:     */ 
/* 1809:     */ 
/* 1810:     */ 
/* 1811:     */ 
/* 1812:     */ 
/* 1813:     */ 
/* 1814:     */ 
/* 1815:     */ 
/* 1816:     */ 
/* 1817:     */ 
/* 1818:     */ 
/* 1819:     */ 
/* 1820:     */ 
/* 1821:     */ 
/* 1822:     */ 
/* 1823:     */ 
/* 1824:     */ 
/* 1825:     */ 
/* 1826:     */ 
/* 1827:     */ 
/* 1828:     */ 
/* 1829:     */ 
/* 1830:     */ 
/* 1831:     */ 
/* 1832:     */ 
/* 1833:     */ 
/* 1834:     */ 
/* 1835:     */ 
/* 1836:     */ 
/* 1837:     */ 
/* 1838:     */ 
/* 1839:     */ 
/* 1840:     */ 
/* 1841:     */ 
/* 1842:     */ 
/* 1843:     */ 
/* 1844:     */ 
/* 1845:     */ 
/* 1846:     */ 
/* 1847:     */ 
/* 1848:     */ 
/* 1849:     */ 
/* 1850:     */ 
/* 1851:     */ 
/* 1852:     */ 
/* 1853:     */ 
/* 1854:     */ 
/* 1855:     */ 
/* 1856:     */ 
/* 1857:     */ 
/* 1858:     */ 
/* 1859:     */ 
/* 1860:     */ 
/* 1861:     */ 
/* 1862:     */ 
/* 1863:     */ 
/* 1864:     */ 
/* 1865:     */ 
/* 1866:     */ 
/* 1867:     */ 
/* 1868:     */ 
/* 1869:     */ 
/* 1870:     */ 
/* 1871:     */ 
/* 1872:     */ 
/* 1873:     */ 
/* 1874:     */ 
/* 1875:     */ 
/* 1876:     */ 
/* 1877:     */ 
/* 1878:     */ 
/* 1879:     */ 
/* 1880:     */ 
/* 1881:     */ 
/* 1882:     */ 
/* 1883:     */ 
/* 1884:     */ 
/* 1885:     */ 
/* 1886:     */ 
/* 1887:     */ 
/* 1888:     */ 
/* 1889:     */ 
/* 1890:     */ 
/* 1891:     */ 
/* 1892:     */ 
/* 1893:     */ 
/* 1894:     */ 
/* 1895:     */ 
/* 1896:     */ 
/* 1897:     */ 
/* 1898:     */ 
/* 1899:     */ 
/* 1900:     */ 
/* 1901:     */ 
/* 1902:     */ 
/* 1903:     */ 
/* 1904:     */ 
/* 1905:     */ 
/* 1906:     */ 
/* 1907:     */ 
/* 1908:     */ 
/* 1909:     */ 
/* 1910:     */ 
/* 1911:     */ 
/* 1912:     */ 
/* 1913:     */ 
/* 1914:     */ 
/* 1915:     */ 
/* 1916:     */ 
/* 1917:     */ 
/* 1918:     */ 
/* 1919:     */ 
/* 1920:     */ 
/* 1921:     */ 
/* 1922:     */ 
/* 1923:     */ 
/* 1924:     */ 
/* 1925:     */ 
/* 1926:     */ 
/* 1927:     */ 
/* 1928:     */ 
/* 1929:     */ 
/* 1930:     */ 
/* 1931:     */ 
/* 1932:     */ 
/* 1933:     */ 
/* 1934:     */ 
/* 1935:     */ 
/* 1936:     */ 
/* 1937:     */ 
/* 1938:1539 */     prefixIndex = 0;
/* 1939:     */     
/* 1940:     */ 
/* 1941:     */ 
/* 1942:     */ 
/* 1943:     */ 
/* 1944:     */ 
/* 1945:     */ 
/* 1946:     */ 
/* 1947:     */ 
/* 1948:     */ 
/* 1949:     */ 
/* 1950:     */ 
/* 1951:     */ 
/* 1952:     */ 
/* 1953:     */ 
/* 1954:     */ 
/* 1955:     */ 
/* 1956:     */ 
/* 1957:     */ 
/* 1958:     */ 
/* 1959:     */ 
/* 1960:     */ 
/* 1961:     */ 
/* 1962:     */ 
/* 1963:     */ 
/* 1964:     */ 
/* 1965:     */ 
/* 1966:     */ 
/* 1967:     */ 
/* 1968:     */ 
/* 1969:     */ 
/* 1970:     */ 
/* 1971:     */ 
/* 1972:     */ 
/* 1973:     */ 
/* 1974:     */ 
/* 1975:     */ 
/* 1976:     */ 
/* 1977:     */ 
/* 1978:     */ 
/* 1979:     */ 
/* 1980:     */ 
/* 1981:     */ 
/* 1982:     */ 
/* 1983:     */ 
/* 1984:     */ 
/* 1985:     */ 
/* 1986:     */ 
/* 1987:     */ 
/* 1988:     */ 
/* 1989:     */ 
/* 1990:     */ 
/* 1991:     */ 
/* 1992:     */ 
/* 1993:     */ 
/* 1994:1595 */     String resource = "org.apache.xalan.xsltc.runtime.ErrorMessages";
/* 1995:1596 */     m_bundle = ResourceBundle.getBundle(resource);
/* 1996:     */   }
/* 1997:     */   
/* 1998:     */   public static void runTimeError(String code)
/* 1999:     */   {
/* 2000:1603 */     throw new RuntimeException(m_bundle.getString(code));
/* 2001:     */   }
/* 2002:     */   
/* 2003:     */   public static void runTimeError(String code, Object[] args)
/* 2004:     */   {
/* 2005:1607 */     String message = MessageFormat.format(m_bundle.getString(code), args);
/* 2006:     */     
/* 2007:1609 */     throw new RuntimeException(message);
/* 2008:     */   }
/* 2009:     */   
/* 2010:     */   public static void runTimeError(String code, Object arg0)
/* 2011:     */   {
/* 2012:1613 */     runTimeError(code, new Object[] { arg0 });
/* 2013:     */   }
/* 2014:     */   
/* 2015:     */   public static void runTimeError(String code, Object arg0, Object arg1)
/* 2016:     */   {
/* 2017:1617 */     runTimeError(code, new Object[] { arg0, arg1 });
/* 2018:     */   }
/* 2019:     */   
/* 2020:     */   public static void consoleOutput(String msg)
/* 2021:     */   {
/* 2022:1621 */     System.out.println(msg);
/* 2023:     */   }
/* 2024:     */   
/* 2025:     */   public static String replace(String base, char ch, String str)
/* 2026:     */   {
/* 2027:1628 */     return base.indexOf(ch) < 0 ? base : replace(base, String.valueOf(ch), new String[] { str });
/* 2028:     */   }
/* 2029:     */   
/* 2030:     */   public static String replace(String base, String delim, String[] str)
/* 2031:     */   {
/* 2032:1633 */     int len = base.length();
/* 2033:1634 */     StringBuffer result = new StringBuffer();
/* 2034:1636 */     for (int i = 0; i < len; i++)
/* 2035:     */     {
/* 2036:1637 */       char ch = base.charAt(i);
/* 2037:1638 */       int k = delim.indexOf(ch);
/* 2038:1640 */       if (k >= 0) {
/* 2039:1641 */         result.append(str[k]);
/* 2040:     */       } else {
/* 2041:1644 */         result.append(ch);
/* 2042:     */       }
/* 2043:     */     }
/* 2044:1647 */     return result.toString();
/* 2045:     */   }
/* 2046:     */   
/* 2047:     */   public static String mapQNameToJavaName(String base)
/* 2048:     */   {
/* 2049:1661 */     return replace(base, ".-:/{}?#%*", new String[] { "$dot$", "$dash$", "$colon$", "$slash$", "", "$colon$", "$ques$", "$hash$", "$per$", "$aster$" });
/* 2050:     */   }
/* 2051:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.runtime.BasisLibrary
 * JD-Core Version:    0.7.0.1
 */