/*    1:     */ package org.apache.xalan.processor;
/*    2:     */ 
/*    3:     */ import java.lang.reflect.InvocationTargetException;
/*    4:     */ import java.lang.reflect.Method;
/*    5:     */ import java.util.StringTokenizer;
/*    6:     */ import java.util.Vector;
/*    7:     */ import javax.xml.transform.TransformerException;
/*    8:     */ import org.apache.xalan.res.XSLMessages;
/*    9:     */ import org.apache.xalan.templates.AVT;
/*   10:     */ import org.apache.xalan.templates.ElemTemplateElement;
/*   11:     */ import org.apache.xml.utils.QName;
/*   12:     */ import org.apache.xml.utils.StringToIntTable;
/*   13:     */ import org.apache.xml.utils.StringVector;
/*   14:     */ import org.apache.xml.utils.XML11Char;
/*   15:     */ import org.apache.xpath.XPath;
/*   16:     */ import org.xml.sax.SAXException;
/*   17:     */ 
/*   18:     */ public class XSLTAttributeDef
/*   19:     */ {
/*   20:     */   static final int FATAL = 0;
/*   21:     */   static final int ERROR = 1;
/*   22:     */   static final int WARNING = 2;
/*   23:     */   static final int T_CDATA = 1;
/*   24:     */   static final int T_URL = 2;
/*   25:     */   static final int T_AVT = 3;
/*   26:     */   static final int T_PATTERN = 4;
/*   27:     */   static final int T_EXPR = 5;
/*   28:     */   static final int T_CHAR = 6;
/*   29:     */   static final int T_NUMBER = 7;
/*   30:     */   static final int T_YESNO = 8;
/*   31:     */   static final int T_QNAME = 9;
/*   32:     */   static final int T_QNAMES = 10;
/*   33:     */   static final int T_ENUM = 11;
/*   34:     */   static final int T_SIMPLEPATTERNLIST = 12;
/*   35:     */   static final int T_NMTOKEN = 13;
/*   36:     */   static final int T_STRINGLIST = 14;
/*   37:     */   static final int T_PREFIX_URLLIST = 15;
/*   38:     */   static final int T_ENUM_OR_PQNAME = 16;
/*   39:     */   static final int T_NCNAME = 17;
/*   40:     */   static final int T_AVT_QNAME = 18;
/*   41:     */   static final int T_QNAMES_RESOLVE_NULL = 19;
/*   42:     */   static final int T_PREFIXLIST = 20;
/*   43:     */   
/*   44:     */   XSLTAttributeDef(String namespace, String name, int type, boolean required, boolean supportsAVT, int errorType)
/*   45:     */   {
/*   46:  69 */     this.m_namespace = namespace;
/*   47:  70 */     this.m_name = name;
/*   48:  71 */     this.m_type = type;
/*   49:  72 */     this.m_required = required;
/*   50:  73 */     this.m_supportsAVT = supportsAVT;
/*   51:  74 */     this.m_errorType = errorType;
/*   52:     */   }
/*   53:     */   
/*   54:     */   XSLTAttributeDef(String namespace, String name, int type, boolean supportsAVT, int errorType, String defaultVal)
/*   55:     */   {
/*   56:  93 */     this.m_namespace = namespace;
/*   57:  94 */     this.m_name = name;
/*   58:  95 */     this.m_type = type;
/*   59:  96 */     this.m_required = false;
/*   60:  97 */     this.m_supportsAVT = supportsAVT;
/*   61:  98 */     this.m_errorType = errorType;
/*   62:  99 */     this.m_default = defaultVal;
/*   63:     */   }
/*   64:     */   
/*   65:     */   XSLTAttributeDef(String namespace, String name, boolean required, boolean supportsAVT, boolean prefixedQNameValAllowed, int errorType, String k1, int v1, String k2, int v2)
/*   66:     */   {
/*   67: 121 */     this.m_namespace = namespace;
/*   68: 122 */     this.m_name = name;
/*   69: 123 */     this.m_type = (prefixedQNameValAllowed ? 16 : 11);
/*   70: 124 */     this.m_required = required;
/*   71: 125 */     this.m_supportsAVT = supportsAVT;
/*   72: 126 */     this.m_errorType = errorType;
/*   73: 127 */     this.m_enums = new StringToIntTable(2);
/*   74:     */     
/*   75: 129 */     this.m_enums.put(k1, v1);
/*   76: 130 */     this.m_enums.put(k2, v2);
/*   77:     */   }
/*   78:     */   
/*   79:     */   XSLTAttributeDef(String namespace, String name, boolean required, boolean supportsAVT, boolean prefixedQNameValAllowed, int errorType, String k1, int v1, String k2, int v2, String k3, int v3)
/*   80:     */   {
/*   81: 154 */     this.m_namespace = namespace;
/*   82: 155 */     this.m_name = name;
/*   83: 156 */     this.m_type = (prefixedQNameValAllowed ? 16 : 11);
/*   84: 157 */     this.m_required = required;
/*   85: 158 */     this.m_supportsAVT = supportsAVT;
/*   86: 159 */     this.m_errorType = errorType;
/*   87: 160 */     this.m_enums = new StringToIntTable(3);
/*   88:     */     
/*   89: 162 */     this.m_enums.put(k1, v1);
/*   90: 163 */     this.m_enums.put(k2, v2);
/*   91: 164 */     this.m_enums.put(k3, v3);
/*   92:     */   }
/*   93:     */   
/*   94:     */   XSLTAttributeDef(String namespace, String name, boolean required, boolean supportsAVT, boolean prefixedQNameValAllowed, int errorType, String k1, int v1, String k2, int v2, String k3, int v3, String k4, int v4)
/*   95:     */   {
/*   96: 190 */     this.m_namespace = namespace;
/*   97: 191 */     this.m_name = name;
/*   98: 192 */     this.m_type = (prefixedQNameValAllowed ? 16 : 11);
/*   99: 193 */     this.m_required = required;
/*  100: 194 */     this.m_supportsAVT = supportsAVT;
/*  101: 195 */     this.m_errorType = errorType;
/*  102: 196 */     this.m_enums = new StringToIntTable(4);
/*  103:     */     
/*  104: 198 */     this.m_enums.put(k1, v1);
/*  105: 199 */     this.m_enums.put(k2, v2);
/*  106: 200 */     this.m_enums.put(k3, v3);
/*  107: 201 */     this.m_enums.put(k4, v4);
/*  108:     */   }
/*  109:     */   
/*  110: 274 */   static final XSLTAttributeDef m_foreignAttr = new XSLTAttributeDef("*", "*", 1, false, false, 2);
/*  111:     */   static final String S_FOREIGNATTR_SETTER = "setForeignAttr";
/*  112:     */   private String m_namespace;
/*  113:     */   private String m_name;
/*  114:     */   private int m_type;
/*  115:     */   private StringToIntTable m_enums;
/*  116:     */   private String m_default;
/*  117:     */   private boolean m_required;
/*  118:     */   private boolean m_supportsAVT;
/*  119:     */   
/*  120:     */   String getNamespace()
/*  121:     */   {
/*  122: 292 */     return this.m_namespace;
/*  123:     */   }
/*  124:     */   
/*  125:     */   String getName()
/*  126:     */   {
/*  127: 307 */     return this.m_name;
/*  128:     */   }
/*  129:     */   
/*  130:     */   int getType()
/*  131:     */   {
/*  132: 324 */     return this.m_type;
/*  133:     */   }
/*  134:     */   
/*  135:     */   private int getEnum(String key)
/*  136:     */   {
/*  137: 345 */     return this.m_enums.get(key);
/*  138:     */   }
/*  139:     */   
/*  140:     */   private String[] getEnumNames()
/*  141:     */   {
/*  142: 358 */     return this.m_enums.keys();
/*  143:     */   }
/*  144:     */   
/*  145:     */   String getDefault()
/*  146:     */   {
/*  147: 373 */     return this.m_default;
/*  148:     */   }
/*  149:     */   
/*  150:     */   void setDefault(String def)
/*  151:     */   {
/*  152: 383 */     this.m_default = def;
/*  153:     */   }
/*  154:     */   
/*  155:     */   boolean getRequired()
/*  156:     */   {
/*  157: 398 */     return this.m_required;
/*  158:     */   }
/*  159:     */   
/*  160:     */   boolean getSupportsAVT()
/*  161:     */   {
/*  162: 413 */     return this.m_supportsAVT;
/*  163:     */   }
/*  164:     */   
/*  165: 416 */   int m_errorType = 2;
/*  166:     */   
/*  167:     */   int getErrorType()
/*  168:     */   {
/*  169: 425 */     return this.m_errorType;
/*  170:     */   }
/*  171:     */   
/*  172: 431 */   String m_setterString = null;
/*  173:     */   
/*  174:     */   public String getSetterMethodName()
/*  175:     */   {
/*  176: 446 */     if (null == this.m_setterString)
/*  177:     */     {
/*  178: 448 */       if (m_foreignAttr == this) {
/*  179: 450 */         return "setForeignAttr";
/*  180:     */       }
/*  181: 452 */       if (this.m_name.equals("*"))
/*  182:     */       {
/*  183: 454 */         this.m_setterString = "addLiteralResultAttribute";
/*  184:     */         
/*  185: 456 */         return this.m_setterString;
/*  186:     */       }
/*  187: 459 */       StringBuffer outBuf = new StringBuffer();
/*  188:     */       
/*  189: 461 */       outBuf.append("set");
/*  190: 463 */       if ((this.m_namespace != null) && (this.m_namespace.equals("http://www.w3.org/XML/1998/namespace"))) {
/*  191: 466 */         outBuf.append("Xml");
/*  192:     */       }
/*  193: 469 */       int n = this.m_name.length();
/*  194: 471 */       for (int i = 0; i < n; i++)
/*  195:     */       {
/*  196: 473 */         char c = this.m_name.charAt(i);
/*  197: 475 */         if ('-' == c)
/*  198:     */         {
/*  199: 477 */           i++;
/*  200:     */           
/*  201: 479 */           c = this.m_name.charAt(i);
/*  202: 480 */           c = Character.toUpperCase(c);
/*  203:     */         }
/*  204: 482 */         else if (0 == i)
/*  205:     */         {
/*  206: 484 */           c = Character.toUpperCase(c);
/*  207:     */         }
/*  208: 487 */         outBuf.append(c);
/*  209:     */       }
/*  210: 490 */       this.m_setterString = outBuf.toString();
/*  211:     */     }
/*  212: 493 */     return this.m_setterString;
/*  213:     */   }
/*  214:     */   
/*  215:     */   AVT processAVT(StylesheetHandler handler, String uri, String name, String rawName, String value, ElemTemplateElement owner)
/*  216:     */     throws SAXException
/*  217:     */   {
/*  218:     */     try
/*  219:     */     {
/*  220: 520 */       return new AVT(handler, uri, name, rawName, value, owner);
/*  221:     */     }
/*  222:     */     catch (TransformerException te)
/*  223:     */     {
/*  224: 526 */       throw new SAXException(te);
/*  225:     */     }
/*  226:     */   }
/*  227:     */   
/*  228:     */   Object processCDATA(StylesheetHandler handler, String uri, String name, String rawName, String value, ElemTemplateElement owner)
/*  229:     */     throws SAXException
/*  230:     */   {
/*  231: 548 */     if (getSupportsAVT()) {
/*  232:     */       try
/*  233:     */       {
/*  234: 551 */         return new AVT(handler, uri, name, rawName, value, owner);
/*  235:     */       }
/*  236:     */       catch (TransformerException te)
/*  237:     */       {
/*  238: 556 */         throw new SAXException(te);
/*  239:     */       }
/*  240:     */     }
/*  241: 559 */     return value;
/*  242:     */   }
/*  243:     */   
/*  244:     */   Object processCHAR(StylesheetHandler handler, String uri, String name, String rawName, String value, ElemTemplateElement owner)
/*  245:     */     throws SAXException
/*  246:     */   {
/*  247: 581 */     if (getSupportsAVT()) {
/*  248:     */       try
/*  249:     */       {
/*  250: 584 */         AVT avt = new AVT(handler, uri, name, rawName, value, owner);
/*  251: 587 */         if ((avt.isSimple()) && (value.length() != 1))
/*  252:     */         {
/*  253: 588 */           handleError(handler, "INVALID_TCHAR", new Object[] { name, value }, null);
/*  254: 589 */           return null;
/*  255:     */         }
/*  256: 591 */         return avt;
/*  257:     */       }
/*  258:     */       catch (TransformerException te)
/*  259:     */       {
/*  260: 595 */         throw new SAXException(te);
/*  261:     */       }
/*  262:     */     }
/*  263: 598 */     if (value.length() != 1)
/*  264:     */     {
/*  265: 600 */       handleError(handler, "INVALID_TCHAR", new Object[] { name, value }, null);
/*  266: 601 */       return null;
/*  267:     */     }
/*  268: 604 */     return new Character(value.charAt(0));
/*  269:     */   }
/*  270:     */   
/*  271:     */   Object processENUM(StylesheetHandler handler, String uri, String name, String rawName, String value, ElemTemplateElement owner)
/*  272:     */     throws SAXException
/*  273:     */   {
/*  274: 627 */     AVT avt = null;
/*  275: 628 */     if (getSupportsAVT()) {
/*  276:     */       try
/*  277:     */       {
/*  278: 631 */         avt = new AVT(handler, uri, name, rawName, value, owner);
/*  279: 634 */         if (!avt.isSimple()) {
/*  280: 634 */           return avt;
/*  281:     */         }
/*  282:     */       }
/*  283:     */       catch (TransformerException te)
/*  284:     */       {
/*  285: 638 */         throw new SAXException(te);
/*  286:     */       }
/*  287:     */     }
/*  288: 642 */     int retVal = getEnum(value);
/*  289: 644 */     if (retVal == -10000)
/*  290:     */     {
/*  291: 646 */       StringBuffer enumNamesList = getListOfEnums();
/*  292: 647 */       handleError(handler, "INVALID_ENUM", new Object[] { name, value, enumNamesList.toString() }, null);
/*  293: 648 */       return null;
/*  294:     */     }
/*  295: 651 */     if (getSupportsAVT()) {
/*  296: 651 */       return avt;
/*  297:     */     }
/*  298: 652 */     return new Integer(retVal);
/*  299:     */   }
/*  300:     */   
/*  301:     */   Object processENUM_OR_PQNAME(StylesheetHandler handler, String uri, String name, String rawName, String value, ElemTemplateElement owner)
/*  302:     */     throws SAXException
/*  303:     */   {
/*  304: 677 */     Object objToReturn = null;
/*  305: 679 */     if (getSupportsAVT()) {
/*  306:     */       try
/*  307:     */       {
/*  308: 682 */         AVT avt = new AVT(handler, uri, name, rawName, value, owner);
/*  309: 683 */         if (!avt.isSimple()) {
/*  310: 683 */           return avt;
/*  311:     */         }
/*  312: 684 */         objToReturn = avt;
/*  313:     */       }
/*  314:     */       catch (TransformerException te)
/*  315:     */       {
/*  316: 688 */         throw new SAXException(te);
/*  317:     */       }
/*  318:     */     }
/*  319: 693 */     int key = getEnum(value);
/*  320: 695 */     if (key != -10000)
/*  321:     */     {
/*  322: 697 */       if (objToReturn == null) {
/*  323: 697 */         objToReturn = new Integer(key);
/*  324:     */       }
/*  325:     */     }
/*  326:     */     else {
/*  327:     */       try
/*  328:     */       {
/*  329: 705 */         QName qname = new QName(value, handler, true);
/*  330: 706 */         if (objToReturn == null) {
/*  331: 706 */           objToReturn = qname;
/*  332:     */         }
/*  333: 708 */         if (qname.getPrefix() == null)
/*  334:     */         {
/*  335: 709 */           StringBuffer enumNamesList = getListOfEnums();
/*  336:     */           
/*  337: 711 */           enumNamesList.append(" <qname-but-not-ncname>");
/*  338: 712 */           handleError(handler, "INVALID_ENUM", new Object[] { name, value, enumNamesList.toString() }, null);
/*  339: 713 */           return null;
/*  340:     */         }
/*  341:     */       }
/*  342:     */       catch (IllegalArgumentException ie)
/*  343:     */       {
/*  344: 719 */         StringBuffer enumNamesList = getListOfEnums();
/*  345: 720 */         enumNamesList.append(" <qname-but-not-ncname>");
/*  346:     */         
/*  347: 722 */         handleError(handler, "INVALID_ENUM", new Object[] { name, value, enumNamesList.toString() }, ie);
/*  348: 723 */         return null;
/*  349:     */       }
/*  350:     */       catch (RuntimeException re)
/*  351:     */       {
/*  352: 728 */         StringBuffer enumNamesList = getListOfEnums();
/*  353: 729 */         enumNamesList.append(" <qname-but-not-ncname>");
/*  354:     */         
/*  355: 731 */         handleError(handler, "INVALID_ENUM", new Object[] { name, value, enumNamesList.toString() }, re);
/*  356: 732 */         return null;
/*  357:     */       }
/*  358:     */     }
/*  359: 736 */     return objToReturn;
/*  360:     */   }
/*  361:     */   
/*  362:     */   Object processEXPR(StylesheetHandler handler, String uri, String name, String rawName, String value, ElemTemplateElement owner)
/*  363:     */     throws SAXException
/*  364:     */   {
/*  365:     */     try
/*  366:     */     {
/*  367: 763 */       return handler.createXPath(value, owner);
/*  368:     */     }
/*  369:     */     catch (TransformerException te)
/*  370:     */     {
/*  371: 769 */       throw new SAXException(te);
/*  372:     */     }
/*  373:     */   }
/*  374:     */   
/*  375:     */   Object processNMTOKEN(StylesheetHandler handler, String uri, String name, String rawName, String value, ElemTemplateElement owner)
/*  376:     */     throws SAXException
/*  377:     */   {
/*  378: 792 */     if (getSupportsAVT()) {
/*  379:     */       try
/*  380:     */       {
/*  381: 795 */         AVT avt = new AVT(handler, uri, name, rawName, value, owner);
/*  382: 798 */         if ((avt.isSimple()) && (!XML11Char.isXML11ValidNmtoken(value)))
/*  383:     */         {
/*  384: 799 */           handleError(handler, "INVALID_NMTOKEN", new Object[] { name, value }, null);
/*  385: 800 */           return null;
/*  386:     */         }
/*  387: 802 */         return avt;
/*  388:     */       }
/*  389:     */       catch (TransformerException te)
/*  390:     */       {
/*  391: 806 */         throw new SAXException(te);
/*  392:     */       }
/*  393:     */     }
/*  394: 809 */     if (!XML11Char.isXML11ValidNmtoken(value))
/*  395:     */     {
/*  396: 810 */       handleError(handler, "INVALID_NMTOKEN", new Object[] { name, value }, null);
/*  397: 811 */       return null;
/*  398:     */     }
/*  399: 814 */     return value;
/*  400:     */   }
/*  401:     */   
/*  402:     */   Object processPATTERN(StylesheetHandler handler, String uri, String name, String rawName, String value, ElemTemplateElement owner)
/*  403:     */     throws SAXException
/*  404:     */   {
/*  405:     */     try
/*  406:     */     {
/*  407: 841 */       return handler.createMatchPatternXPath(value, owner);
/*  408:     */     }
/*  409:     */     catch (TransformerException te)
/*  410:     */     {
/*  411: 847 */       throw new SAXException(te);
/*  412:     */     }
/*  413:     */   }
/*  414:     */   
/*  415:     */   Object processNUMBER(StylesheetHandler handler, String uri, String name, String rawName, String value, ElemTemplateElement owner)
/*  416:     */     throws SAXException
/*  417:     */   {
/*  418: 874 */     if (getSupportsAVT())
/*  419:     */     {
/*  420: 877 */       AVT avt = null;
/*  421:     */       try
/*  422:     */       {
/*  423: 880 */         avt = new AVT(handler, uri, name, rawName, value, owner);
/*  424: 883 */         if (avt.isSimple()) {
/*  425: 885 */           val = Double.valueOf(value);
/*  426:     */         }
/*  427:     */       }
/*  428:     */       catch (TransformerException te)
/*  429:     */       {
/*  430:     */         Double val;
/*  431: 890 */         throw new SAXException(te);
/*  432:     */       }
/*  433:     */       catch (NumberFormatException nfe)
/*  434:     */       {
/*  435: 894 */         handleError(handler, "INVALID_NUMBER", new Object[] { name, value }, nfe);
/*  436: 895 */         return null;
/*  437:     */       }
/*  438: 897 */       return avt;
/*  439:     */     }
/*  440:     */     try
/*  441:     */     {
/*  442: 904 */       return Double.valueOf(value);
/*  443:     */     }
/*  444:     */     catch (NumberFormatException nfe)
/*  445:     */     {
/*  446: 908 */       handleError(handler, "INVALID_NUMBER", new Object[] { name, value }, nfe);
/*  447:     */     }
/*  448: 909 */     return null;
/*  449:     */   }
/*  450:     */   
/*  451:     */   Object processQNAME(StylesheetHandler handler, String uri, String name, String rawName, String value, ElemTemplateElement owner)
/*  452:     */     throws SAXException
/*  453:     */   {
/*  454:     */     try
/*  455:     */     {
/*  456: 937 */       return new QName(value, handler, true);
/*  457:     */     }
/*  458:     */     catch (IllegalArgumentException ie)
/*  459:     */     {
/*  460: 943 */       handleError(handler, "INVALID_QNAME", new Object[] { name, value }, ie);
/*  461: 944 */       return null;
/*  462:     */     }
/*  463:     */     catch (RuntimeException re)
/*  464:     */     {
/*  465: 948 */       handleError(handler, "INVALID_QNAME", new Object[] { name, value }, re);
/*  466:     */     }
/*  467: 949 */     return null;
/*  468:     */   }
/*  469:     */   
/*  470:     */   Object processAVT_QNAME(StylesheetHandler handler, String uri, String name, String rawName, String value, ElemTemplateElement owner)
/*  471:     */     throws SAXException
/*  472:     */   {
/*  473: 974 */     AVT avt = null;
/*  474:     */     try
/*  475:     */     {
/*  476: 977 */       avt = new AVT(handler, uri, name, rawName, value, owner);
/*  477: 980 */       if (avt.isSimple())
/*  478:     */       {
/*  479: 982 */         int indexOfNSSep = value.indexOf(':');
/*  480: 984 */         if (indexOfNSSep >= 0)
/*  481:     */         {
/*  482: 986 */           String prefix = value.substring(0, indexOfNSSep);
/*  483: 987 */           if (!XML11Char.isXML11ValidNCName(prefix))
/*  484:     */           {
/*  485: 989 */             handleError(handler, "INVALID_QNAME", new Object[] { name, value }, null);
/*  486: 990 */             return null;
/*  487:     */           }
/*  488:     */         }
/*  489: 994 */         String localName = indexOfNSSep < 0 ? value : value.substring(indexOfNSSep + 1);
/*  490: 997 */         if ((localName == null) || (localName.length() == 0) || (!XML11Char.isXML11ValidNCName(localName)))
/*  491:     */         {
/*  492:1000 */           handleError(handler, "INVALID_QNAME", new Object[] { name, value }, null);
/*  493:1001 */           return null;
/*  494:     */         }
/*  495:     */       }
/*  496:     */     }
/*  497:     */     catch (TransformerException te)
/*  498:     */     {
/*  499:1008 */       throw new SAXException(te);
/*  500:     */     }
/*  501:1011 */     return avt;
/*  502:     */   }
/*  503:     */   
/*  504:     */   Object processNCNAME(StylesheetHandler handler, String uri, String name, String rawName, String value, ElemTemplateElement owner)
/*  505:     */     throws SAXException
/*  506:     */   {
/*  507:1035 */     if (getSupportsAVT())
/*  508:     */     {
/*  509:1037 */       AVT avt = null;
/*  510:     */       try
/*  511:     */       {
/*  512:1040 */         avt = new AVT(handler, uri, name, rawName, value, owner);
/*  513:1043 */         if ((avt.isSimple()) && (!XML11Char.isXML11ValidNCName(value)))
/*  514:     */         {
/*  515:1045 */           handleError(handler, "INVALID_NCNAME", new Object[] { name, value }, null);
/*  516:1046 */           return null;
/*  517:     */         }
/*  518:1048 */         return avt;
/*  519:     */       }
/*  520:     */       catch (TransformerException te)
/*  521:     */       {
/*  522:1053 */         throw new SAXException(te);
/*  523:     */       }
/*  524:     */     }
/*  525:1057 */     if (!XML11Char.isXML11ValidNCName(value))
/*  526:     */     {
/*  527:1059 */       handleError(handler, "INVALID_NCNAME", new Object[] { name, value }, null);
/*  528:1060 */       return null;
/*  529:     */     }
/*  530:1062 */     return value;
/*  531:     */   }
/*  532:     */   
/*  533:     */   Vector processQNAMES(StylesheetHandler handler, String uri, String name, String rawName, String value)
/*  534:     */     throws SAXException
/*  535:     */   {
/*  536:1088 */     StringTokenizer tokenizer = new StringTokenizer(value, " \t\n\r\f");
/*  537:1089 */     int nQNames = tokenizer.countTokens();
/*  538:1090 */     Vector qnames = new Vector(nQNames);
/*  539:1092 */     for (int i = 0; i < nQNames; i++) {
/*  540:1095 */       qnames.addElement(new QName(tokenizer.nextToken(), handler));
/*  541:     */     }
/*  542:1098 */     return qnames;
/*  543:     */   }
/*  544:     */   
/*  545:     */   final Vector processQNAMESRNU(StylesheetHandler handler, String uri, String name, String rawName, String value)
/*  546:     */     throws SAXException
/*  547:     */   {
/*  548:1125 */     StringTokenizer tokenizer = new StringTokenizer(value, " \t\n\r\f");
/*  549:1126 */     int nQNames = tokenizer.countTokens();
/*  550:1127 */     Vector qnames = new Vector(nQNames);
/*  551:     */     
/*  552:1129 */     String defaultURI = handler.getNamespaceForPrefix("");
/*  553:1130 */     for (int i = 0; i < nQNames; i++)
/*  554:     */     {
/*  555:1132 */       String tok = tokenizer.nextToken();
/*  556:1133 */       if (tok.indexOf(':') == -1) {
/*  557:1134 */         qnames.addElement(new QName(defaultURI, tok));
/*  558:     */       } else {
/*  559:1136 */         qnames.addElement(new QName(tok, handler));
/*  560:     */       }
/*  561:     */     }
/*  562:1139 */     return qnames;
/*  563:     */   }
/*  564:     */   
/*  565:     */   Vector processSIMPLEPATTERNLIST(StylesheetHandler handler, String uri, String name, String rawName, String value, ElemTemplateElement owner)
/*  566:     */     throws SAXException
/*  567:     */   {
/*  568:     */     try
/*  569:     */     {
/*  570:1166 */       StringTokenizer tokenizer = new StringTokenizer(value, " \t\n\r\f");
/*  571:1167 */       int nPatterns = tokenizer.countTokens();
/*  572:1168 */       Vector patterns = new Vector(nPatterns);
/*  573:1170 */       for (int i = 0; i < nPatterns; i++)
/*  574:     */       {
/*  575:1172 */         XPath pattern = handler.createMatchPatternXPath(tokenizer.nextToken(), owner);
/*  576:     */         
/*  577:     */ 
/*  578:1175 */         patterns.addElement(pattern);
/*  579:     */       }
/*  580:1178 */       return patterns;
/*  581:     */     }
/*  582:     */     catch (TransformerException te)
/*  583:     */     {
/*  584:1182 */       throw new SAXException(te);
/*  585:     */     }
/*  586:     */   }
/*  587:     */   
/*  588:     */   StringVector processSTRINGLIST(StylesheetHandler handler, String uri, String name, String rawName, String value)
/*  589:     */   {
/*  590:1202 */     StringTokenizer tokenizer = new StringTokenizer(value, " \t\n\r\f");
/*  591:1203 */     int nStrings = tokenizer.countTokens();
/*  592:1204 */     StringVector strings = new StringVector(nStrings);
/*  593:1206 */     for (int i = 0; i < nStrings; i++) {
/*  594:1208 */       strings.addElement(tokenizer.nextToken());
/*  595:     */     }
/*  596:1211 */     return strings;
/*  597:     */   }
/*  598:     */   
/*  599:     */   StringVector processPREFIX_URLLIST(StylesheetHandler handler, String uri, String name, String rawName, String value)
/*  600:     */     throws SAXException
/*  601:     */   {
/*  602:1233 */     StringTokenizer tokenizer = new StringTokenizer(value, " \t\n\r\f");
/*  603:1234 */     int nStrings = tokenizer.countTokens();
/*  604:1235 */     StringVector strings = new StringVector(nStrings);
/*  605:1237 */     for (int i = 0; i < nStrings; i++)
/*  606:     */     {
/*  607:1239 */       String prefix = tokenizer.nextToken();
/*  608:1240 */       String url = handler.getNamespaceForPrefix(prefix);
/*  609:1242 */       if (url != null) {
/*  610:1243 */         strings.addElement(url);
/*  611:     */       } else {
/*  612:1245 */         throw new SAXException(XSLMessages.createMessage("ER_CANT_RESOLVE_NSPREFIX", new Object[] { prefix }));
/*  613:     */       }
/*  614:     */     }
/*  615:1249 */     return strings;
/*  616:     */   }
/*  617:     */   
/*  618:     */   StringVector processPREFIX_LIST(StylesheetHandler handler, String uri, String name, String rawName, String value)
/*  619:     */     throws SAXException
/*  620:     */   {
/*  621:1271 */     StringTokenizer tokenizer = new StringTokenizer(value, " \t\n\r\f");
/*  622:1272 */     int nStrings = tokenizer.countTokens();
/*  623:1273 */     StringVector strings = new StringVector(nStrings);
/*  624:1275 */     for (int i = 0; i < nStrings; i++)
/*  625:     */     {
/*  626:1277 */       String prefix = tokenizer.nextToken();
/*  627:1278 */       String url = handler.getNamespaceForPrefix(prefix);
/*  628:1279 */       if ((prefix.equals("#default")) || (url != null)) {
/*  629:1280 */         strings.addElement(prefix);
/*  630:     */       } else {
/*  631:1282 */         throw new SAXException(XSLMessages.createMessage("ER_CANT_RESOLVE_NSPREFIX", new Object[] { prefix }));
/*  632:     */       }
/*  633:     */     }
/*  634:1289 */     return strings;
/*  635:     */   }
/*  636:     */   
/*  637:     */   Object processURL(StylesheetHandler handler, String uri, String name, String rawName, String value, ElemTemplateElement owner)
/*  638:     */     throws SAXException
/*  639:     */   {
/*  640:1313 */     if (getSupportsAVT()) {
/*  641:     */       try
/*  642:     */       {
/*  643:1316 */         return new AVT(handler, uri, name, rawName, value, owner);
/*  644:     */       }
/*  645:     */       catch (TransformerException te)
/*  646:     */       {
/*  647:1328 */         throw new SAXException(te);
/*  648:     */       }
/*  649:     */     }
/*  650:1335 */     return value;
/*  651:     */   }
/*  652:     */   
/*  653:     */   private Boolean processYESNO(StylesheetHandler handler, String uri, String name, String rawName, String value)
/*  654:     */     throws SAXException
/*  655:     */   {
/*  656:1359 */     if ((!value.equals("yes")) && (!value.equals("no")))
/*  657:     */     {
/*  658:1361 */       handleError(handler, "INVALID_BOOLEAN", new Object[] { name, value }, null);
/*  659:1362 */       return null;
/*  660:     */     }
/*  661:1365 */     return new Boolean(value.equals("yes"));
/*  662:     */   }
/*  663:     */   
/*  664:     */   Object processValue(StylesheetHandler handler, String uri, String name, String rawName, String value, ElemTemplateElement owner)
/*  665:     */     throws SAXException
/*  666:     */   {
/*  667:1387 */     int type = getType();
/*  668:1388 */     Object processedValue = null;
/*  669:1390 */     switch (type)
/*  670:     */     {
/*  671:     */     case 3: 
/*  672:1393 */       processedValue = processAVT(handler, uri, name, rawName, value, owner);
/*  673:1394 */       break;
/*  674:     */     case 1: 
/*  675:1396 */       processedValue = processCDATA(handler, uri, name, rawName, value, owner);
/*  676:1397 */       break;
/*  677:     */     case 6: 
/*  678:1399 */       processedValue = processCHAR(handler, uri, name, rawName, value, owner);
/*  679:1400 */       break;
/*  680:     */     case 11: 
/*  681:1402 */       processedValue = processENUM(handler, uri, name, rawName, value, owner);
/*  682:1403 */       break;
/*  683:     */     case 5: 
/*  684:1405 */       processedValue = processEXPR(handler, uri, name, rawName, value, owner);
/*  685:1406 */       break;
/*  686:     */     case 13: 
/*  687:1408 */       processedValue = processNMTOKEN(handler, uri, name, rawName, value, owner);
/*  688:1409 */       break;
/*  689:     */     case 4: 
/*  690:1411 */       processedValue = processPATTERN(handler, uri, name, rawName, value, owner);
/*  691:1412 */       break;
/*  692:     */     case 7: 
/*  693:1414 */       processedValue = processNUMBER(handler, uri, name, rawName, value, owner);
/*  694:1415 */       break;
/*  695:     */     case 9: 
/*  696:1417 */       processedValue = processQNAME(handler, uri, name, rawName, value, owner);
/*  697:1418 */       break;
/*  698:     */     case 10: 
/*  699:1420 */       processedValue = processQNAMES(handler, uri, name, rawName, value);
/*  700:1421 */       break;
/*  701:     */     case 19: 
/*  702:1423 */       processedValue = processQNAMESRNU(handler, uri, name, rawName, value);
/*  703:1424 */       break;
/*  704:     */     case 12: 
/*  705:1426 */       processedValue = processSIMPLEPATTERNLIST(handler, uri, name, rawName, value, owner);
/*  706:     */       
/*  707:1428 */       break;
/*  708:     */     case 2: 
/*  709:1430 */       processedValue = processURL(handler, uri, name, rawName, value, owner);
/*  710:1431 */       break;
/*  711:     */     case 8: 
/*  712:1433 */       processedValue = processYESNO(handler, uri, name, rawName, value);
/*  713:1434 */       break;
/*  714:     */     case 14: 
/*  715:1436 */       processedValue = processSTRINGLIST(handler, uri, name, rawName, value);
/*  716:1437 */       break;
/*  717:     */     case 15: 
/*  718:1439 */       processedValue = processPREFIX_URLLIST(handler, uri, name, rawName, value);
/*  719:     */       
/*  720:1441 */       break;
/*  721:     */     case 16: 
/*  722:1443 */       processedValue = processENUM_OR_PQNAME(handler, uri, name, rawName, value, owner);
/*  723:1444 */       break;
/*  724:     */     case 17: 
/*  725:1446 */       processedValue = processNCNAME(handler, uri, name, rawName, value, owner);
/*  726:1447 */       break;
/*  727:     */     case 18: 
/*  728:1449 */       processedValue = processAVT_QNAME(handler, uri, name, rawName, value, owner);
/*  729:1450 */       break;
/*  730:     */     case 20: 
/*  731:1452 */       processedValue = processPREFIX_LIST(handler, uri, name, rawName, value);
/*  732:     */       
/*  733:1454 */       break;
/*  734:     */     }
/*  735:1459 */     return processedValue;
/*  736:     */   }
/*  737:     */   
/*  738:     */   void setDefAttrValue(StylesheetHandler handler, ElemTemplateElement elem)
/*  739:     */     throws SAXException
/*  740:     */   {
/*  741:1474 */     setAttrValue(handler, getNamespace(), getName(), getName(), getDefault(), elem);
/*  742:     */   }
/*  743:     */   
/*  744:     */   private Class getPrimativeClass(Object obj)
/*  745:     */   {
/*  746:1492 */     if ((obj instanceof XPath)) {
/*  747:1493 */       return XPath.class;
/*  748:     */     }
/*  749:1495 */     Class cl = obj.getClass();
/*  750:1497 */     if (cl == Double.class) {
/*  751:1499 */       cl = Double.TYPE;
/*  752:     */     }
/*  753:1502 */     if (cl == Float.class) {
/*  754:1504 */       cl = Float.TYPE;
/*  755:1506 */     } else if (cl == Boolean.class) {
/*  756:1508 */       cl = Boolean.TYPE;
/*  757:1510 */     } else if (cl == Byte.class) {
/*  758:1512 */       cl = Byte.TYPE;
/*  759:1514 */     } else if (cl == Character.class) {
/*  760:1516 */       cl = Character.TYPE;
/*  761:1518 */     } else if (cl == Short.class) {
/*  762:1520 */       cl = Short.TYPE;
/*  763:1522 */     } else if (cl == Integer.class) {
/*  764:1524 */       cl = Integer.TYPE;
/*  765:1526 */     } else if (cl == Long.class) {
/*  766:1528 */       cl = Long.TYPE;
/*  767:     */     }
/*  768:1531 */     return cl;
/*  769:     */   }
/*  770:     */   
/*  771:     */   private StringBuffer getListOfEnums()
/*  772:     */   {
/*  773:1540 */     StringBuffer enumNamesList = new StringBuffer();
/*  774:1541 */     String[] enumValues = getEnumNames();
/*  775:1543 */     for (int i = 0; i < enumValues.length; i++)
/*  776:     */     {
/*  777:1545 */       if (i > 0) {
/*  778:1547 */         enumNamesList.append(' ');
/*  779:     */       }
/*  780:1549 */       enumNamesList.append(enumValues[i]);
/*  781:     */     }
/*  782:1551 */     return enumNamesList;
/*  783:     */   }
/*  784:     */   
/*  785:     */   boolean setAttrValue(StylesheetHandler handler, String attrUri, String attrLocalName, String attrRawName, String attrValue, ElemTemplateElement elem)
/*  786:     */     throws SAXException
/*  787:     */   {
/*  788:1571 */     if ((attrRawName.equals("xmlns")) || (attrRawName.startsWith("xmlns:"))) {
/*  789:1572 */       return true;
/*  790:     */     }
/*  791:1574 */     String setterString = getSetterMethodName();
/*  792:1578 */     if (null != setterString) {
/*  793:     */       try
/*  794:     */       {
/*  795:     */         Method meth;
/*  796:     */         Object[] args;
/*  797:1585 */         if (setterString.equals("setForeignAttr"))
/*  798:     */         {
/*  799:1588 */           if (attrUri == null) {
/*  800:1588 */             attrUri = "";
/*  801:     */           }
/*  802:1590 */           Class sclass = attrUri.getClass();
/*  803:1591 */           Class[] argTypes = { sclass, sclass, sclass, sclass };
/*  804:     */           
/*  805:     */ 
/*  806:1594 */           meth = elem.getClass().getMethod(setterString, argTypes);
/*  807:     */           
/*  808:1596 */           args = new Object[] { attrUri, attrLocalName, attrRawName, attrValue };
/*  809:     */         }
/*  810:     */         else
/*  811:     */         {
/*  812:1601 */           Object value = processValue(handler, attrUri, attrLocalName, attrRawName, attrValue, elem);
/*  813:1605 */           if (null == value) {
/*  814:1605 */             return false;
/*  815:     */           }
/*  816:1608 */           Class[] argTypes = { getPrimativeClass(value) };
/*  817:     */           try
/*  818:     */           {
/*  819:1612 */             meth = elem.getClass().getMethod(setterString, argTypes);
/*  820:     */           }
/*  821:     */           catch (NoSuchMethodException nsme)
/*  822:     */           {
/*  823:1616 */             Class cl = value.getClass();
/*  824:     */             
/*  825:     */ 
/*  826:1619 */             argTypes[0] = cl;
/*  827:1620 */             meth = elem.getClass().getMethod(setterString, argTypes);
/*  828:     */           }
/*  829:1623 */           args = new Object[] { value };
/*  830:     */         }
/*  831:1626 */         meth.invoke(elem, args);
/*  832:     */       }
/*  833:     */       catch (NoSuchMethodException nsme)
/*  834:     */       {
/*  835:1630 */         if (!setterString.equals("setForeignAttr"))
/*  836:     */         {
/*  837:1632 */           handler.error("ER_FAILED_CALLING_METHOD", new Object[] { setterString }, nsme);
/*  838:1633 */           return false;
/*  839:     */         }
/*  840:     */       }
/*  841:     */       catch (IllegalAccessException iae)
/*  842:     */       {
/*  843:1638 */         handler.error("ER_FAILED_CALLING_METHOD", new Object[] { setterString }, iae);
/*  844:1639 */         return false;
/*  845:     */       }
/*  846:     */       catch (InvocationTargetException nsme)
/*  847:     */       {
/*  848:1643 */         handleError(handler, "WG_ILLEGAL_ATTRIBUTE_VALUE", new Object[] { "name", getName() }, nsme);
/*  849:     */         
/*  850:1645 */         return false;
/*  851:     */       }
/*  852:     */     }
/*  853:1649 */     return true;
/*  854:     */   }
/*  855:     */   
/*  856:     */   private void handleError(StylesheetHandler handler, String msg, Object[] args, Exception exc)
/*  857:     */     throws SAXException
/*  858:     */   {
/*  859:1654 */     switch (getErrorType())
/*  860:     */     {
/*  861:     */     case 0: 
/*  862:     */     case 1: 
/*  863:1658 */       handler.error(msg, args, exc);
/*  864:1659 */       break;
/*  865:     */     case 2: 
/*  866:1661 */       handler.warn(msg, args);
/*  867:     */     }
/*  868:     */   }
/*  869:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.processor.XSLTAttributeDef
 * JD-Core Version:    0.7.0.1
 */