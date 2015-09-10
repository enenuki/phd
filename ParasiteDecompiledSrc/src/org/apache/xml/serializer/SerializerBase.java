/*    1:     */ package org.apache.xml.serializer;
/*    2:     */ 
/*    3:     */ import java.io.IOException;
/*    4:     */ import java.io.OutputStream;
/*    5:     */ import java.io.Writer;
/*    6:     */ import java.util.HashMap;
/*    7:     */ import java.util.Hashtable;
/*    8:     */ import java.util.Properties;
/*    9:     */ import java.util.Set;
/*   10:     */ import java.util.Vector;
/*   11:     */ import javax.xml.transform.SourceLocator;
/*   12:     */ import javax.xml.transform.Transformer;
/*   13:     */ import org.apache.xml.serializer.dom3.DOM3SerializerImpl;
/*   14:     */ import org.apache.xml.serializer.utils.Messages;
/*   15:     */ import org.apache.xml.serializer.utils.Utils;
/*   16:     */ import org.w3c.dom.Node;
/*   17:     */ import org.xml.sax.Attributes;
/*   18:     */ import org.xml.sax.ContentHandler;
/*   19:     */ import org.xml.sax.Locator;
/*   20:     */ import org.xml.sax.SAXException;
/*   21:     */ import org.xml.sax.SAXParseException;
/*   22:     */ import org.xml.sax.helpers.AttributesImpl;
/*   23:     */ 
/*   24:     */ public abstract class SerializerBase
/*   25:     */   implements SerializationHandler, SerializerConstants
/*   26:     */ {
/*   27:     */   public static final String PKG_NAME;
/*   28:     */   public static final String PKG_PATH;
/*   29:     */   
/*   30:     */   static
/*   31:     */   {
/*   32:  70 */     String fullyQualifiedName = SerializerBase.class.getName();
/*   33:  71 */     int lastDot = fullyQualifiedName.lastIndexOf('.');
/*   34:  72 */     if (lastDot < 0) {
/*   35:  73 */       PKG_NAME = "";
/*   36:     */     } else {
/*   37:  75 */       PKG_NAME = fullyQualifiedName.substring(0, lastDot);
/*   38:     */     }
/*   39:  78 */     StringBuffer sb = new StringBuffer();
/*   40:  79 */     for (int i = 0; i < PKG_NAME.length(); i++)
/*   41:     */     {
/*   42:  80 */       char ch = PKG_NAME.charAt(i);
/*   43:  81 */       if (ch == '.') {
/*   44:  82 */         sb.append('/');
/*   45:     */       } else {
/*   46:  84 */         sb.append(ch);
/*   47:     */       }
/*   48:     */     }
/*   49:  86 */     PKG_PATH = sb.toString();
/*   50:     */   }
/*   51:     */   
/*   52:     */   protected void fireEndElem(String name)
/*   53:     */     throws SAXException
/*   54:     */   {
/*   55:  98 */     if (this.m_tracer != null)
/*   56:     */     {
/*   57: 100 */       flushMyWriter();
/*   58: 101 */       this.m_tracer.fireGenerateEvent(4, name, (Attributes)null);
/*   59:     */     }
/*   60:     */   }
/*   61:     */   
/*   62:     */   protected void fireCharEvent(char[] chars, int start, int length)
/*   63:     */     throws SAXException
/*   64:     */   {
/*   65: 114 */     if (this.m_tracer != null)
/*   66:     */     {
/*   67: 116 */       flushMyWriter();
/*   68: 117 */       this.m_tracer.fireGenerateEvent(5, chars, start, length);
/*   69:     */     }
/*   70:     */   }
/*   71:     */   
/*   72: 124 */   protected boolean m_needToCallStartDocument = true;
/*   73: 129 */   protected boolean m_cdataTagOpen = false;
/*   74: 136 */   protected AttributesImplSerializer m_attributes = new AttributesImplSerializer();
/*   75: 141 */   protected boolean m_inEntityRef = false;
/*   76: 144 */   protected boolean m_inExternalDTD = false;
/*   77:     */   protected String m_doctypeSystem;
/*   78:     */   protected String m_doctypePublic;
/*   79: 160 */   boolean m_needToOutputDocTypeDecl = true;
/*   80: 165 */   protected boolean m_shouldNotWriteXMLHeader = false;
/*   81:     */   private String m_standalone;
/*   82: 175 */   protected boolean m_standaloneWasSpecified = false;
/*   83: 180 */   protected boolean m_doIndent = false;
/*   84: 184 */   protected int m_indentAmount = 0;
/*   85: 189 */   protected String m_version = null;
/*   86:     */   protected String m_mediatype;
/*   87:     */   private Transformer m_transformer;
/*   88:     */   protected NamespaceMappings m_prefixMap;
/*   89:     */   protected SerializerTrace m_tracer;
/*   90:     */   protected SourceLocator m_sourceLocator;
/*   91: 223 */   protected Writer m_writer = null;
/*   92: 231 */   protected ElemContext m_elemContext = new ElemContext();
/*   93: 239 */   protected char[] m_charsBuff = new char[60];
/*   94: 247 */   protected char[] m_attrBuff = new char[30];
/*   95:     */   
/*   96:     */   public void comment(String data)
/*   97:     */     throws SAXException
/*   98:     */   {
/*   99: 256 */     this.m_docIsEmpty = false;
/*  100:     */     
/*  101: 258 */     int length = data.length();
/*  102: 259 */     if (length > this.m_charsBuff.length) {
/*  103: 261 */       this.m_charsBuff = new char[length * 2 + 1];
/*  104:     */     }
/*  105: 263 */     data.getChars(0, length, this.m_charsBuff, 0);
/*  106: 264 */     comment(this.m_charsBuff, 0, length);
/*  107:     */   }
/*  108:     */   
/*  109:     */   protected String patchName(String qname)
/*  110:     */   {
/*  111: 281 */     int lastColon = qname.lastIndexOf(':');
/*  112: 283 */     if (lastColon > 0)
/*  113:     */     {
/*  114: 284 */       int firstColon = qname.indexOf(':');
/*  115: 285 */       String prefix = qname.substring(0, firstColon);
/*  116: 286 */       String localName = qname.substring(lastColon + 1);
/*  117:     */       
/*  118:     */ 
/*  119: 289 */       String uri = this.m_prefixMap.lookupNamespace(prefix);
/*  120: 290 */       if ((uri != null) && (uri.length() == 0)) {
/*  121: 291 */         return localName;
/*  122:     */       }
/*  123: 293 */       if (firstColon != lastColon) {
/*  124: 294 */         return prefix + ':' + localName;
/*  125:     */       }
/*  126:     */     }
/*  127: 297 */     return qname;
/*  128:     */   }
/*  129:     */   
/*  130:     */   protected static String getLocalName(String qname)
/*  131:     */   {
/*  132: 308 */     int col = qname.lastIndexOf(':');
/*  133: 309 */     return col > 0 ? qname.substring(col + 1) : qname;
/*  134:     */   }
/*  135:     */   
/*  136:     */   public void addAttribute(String uri, String localName, String rawName, String type, String value, boolean XSLAttribute)
/*  137:     */     throws SAXException
/*  138:     */   {
/*  139: 372 */     if (this.m_elemContext.m_startTagOpen) {
/*  140: 374 */       addAttributeAlways(uri, localName, rawName, type, value, XSLAttribute);
/*  141:     */     }
/*  142:     */   }
/*  143:     */   
/*  144:     */   public boolean addAttributeAlways(String uri, String localName, String rawName, String type, String value, boolean XSLAttribute)
/*  145:     */   {
/*  146:     */     int index;
/*  147: 412 */     if ((localName == null) || (uri == null) || (uri.length() == 0)) {
/*  148: 413 */       index = this.m_attributes.getIndex(rawName);
/*  149:     */     } else {
/*  150: 415 */       index = this.m_attributes.getIndex(uri, localName);
/*  151:     */     }
/*  152:     */     boolean was_added;
/*  153: 417 */     if (index >= 0)
/*  154:     */     {
/*  155: 423 */       this.m_attributes.setValue(index, value);
/*  156: 424 */       was_added = false;
/*  157:     */     }
/*  158:     */     else
/*  159:     */     {
/*  160: 429 */       this.m_attributes.addAttribute(uri, localName, rawName, type, value);
/*  161: 430 */       was_added = true;
/*  162:     */     }
/*  163: 432 */     return was_added;
/*  164:     */   }
/*  165:     */   
/*  166:     */   public void addAttribute(String name, String value)
/*  167:     */   {
/*  168: 446 */     if (this.m_elemContext.m_startTagOpen)
/*  169:     */     {
/*  170: 448 */       String patchedName = patchName(name);
/*  171: 449 */       String localName = getLocalName(patchedName);
/*  172: 450 */       String uri = getNamespaceURI(patchedName, false);
/*  173:     */       
/*  174: 452 */       addAttributeAlways(uri, localName, patchedName, "CDATA", value, false);
/*  175:     */     }
/*  176:     */   }
/*  177:     */   
/*  178:     */   public void addXSLAttribute(String name, String value, String uri)
/*  179:     */   {
/*  180: 466 */     if (this.m_elemContext.m_startTagOpen)
/*  181:     */     {
/*  182: 468 */       String patchedName = patchName(name);
/*  183: 469 */       String localName = getLocalName(patchedName);
/*  184:     */       
/*  185: 471 */       addAttributeAlways(uri, localName, patchedName, "CDATA", value, true);
/*  186:     */     }
/*  187:     */   }
/*  188:     */   
/*  189:     */   public void addAttributes(Attributes atts)
/*  190:     */     throws SAXException
/*  191:     */   {
/*  192: 484 */     int nAtts = atts.getLength();
/*  193: 486 */     for (int i = 0; i < nAtts; i++)
/*  194:     */     {
/*  195: 488 */       String uri = atts.getURI(i);
/*  196: 490 */       if (null == uri) {
/*  197: 491 */         uri = "";
/*  198:     */       }
/*  199: 493 */       addAttributeAlways(uri, atts.getLocalName(i), atts.getQName(i), atts.getType(i), atts.getValue(i), false);
/*  200:     */     }
/*  201:     */   }
/*  202:     */   
/*  203:     */   public ContentHandler asContentHandler()
/*  204:     */     throws IOException
/*  205:     */   {
/*  206: 515 */     return this;
/*  207:     */   }
/*  208:     */   
/*  209:     */   public void endEntity(String name)
/*  210:     */     throws SAXException
/*  211:     */   {
/*  212: 527 */     if (name.equals("[dtd]")) {
/*  213: 528 */       this.m_inExternalDTD = false;
/*  214:     */     }
/*  215: 529 */     this.m_inEntityRef = false;
/*  216: 531 */     if (this.m_tracer != null) {
/*  217: 532 */       fireEndEntity(name);
/*  218:     */     }
/*  219:     */   }
/*  220:     */   
/*  221:     */   public String getEncoding()
/*  222:     */   {
/*  223: 561 */     return getOutputProperty("encoding");
/*  224:     */   }
/*  225:     */   
/*  226:     */   public void setEncoding(String encoding)
/*  227:     */   {
/*  228: 570 */     setOutputProperty("encoding", encoding);
/*  229:     */   }
/*  230:     */   
/*  231:     */   public void setOmitXMLDeclaration(boolean b)
/*  232:     */   {
/*  233: 580 */     String val = b ? "yes" : "no";
/*  234: 581 */     setOutputProperty("omit-xml-declaration", val);
/*  235:     */   }
/*  236:     */   
/*  237:     */   public boolean getOmitXMLDeclaration()
/*  238:     */   {
/*  239: 591 */     return this.m_shouldNotWriteXMLHeader;
/*  240:     */   }
/*  241:     */   
/*  242:     */   public String getDoctypePublic()
/*  243:     */   {
/*  244: 603 */     return this.m_doctypePublic;
/*  245:     */   }
/*  246:     */   
/*  247:     */   public void setDoctypePublic(String doctypePublic)
/*  248:     */   {
/*  249: 612 */     setOutputProperty("doctype-public", doctypePublic);
/*  250:     */   }
/*  251:     */   
/*  252:     */   public String getDoctypeSystem()
/*  253:     */   {
/*  254: 625 */     return this.m_doctypeSystem;
/*  255:     */   }
/*  256:     */   
/*  257:     */   public void setDoctypeSystem(String doctypeSystem)
/*  258:     */   {
/*  259: 634 */     setOutputProperty("doctype-system", doctypeSystem);
/*  260:     */   }
/*  261:     */   
/*  262:     */   public void setDoctype(String doctypeSystem, String doctypePublic)
/*  263:     */   {
/*  264: 645 */     setOutputProperty("doctype-system", doctypeSystem);
/*  265: 646 */     setOutputProperty("doctype-public", doctypePublic);
/*  266:     */   }
/*  267:     */   
/*  268:     */   public void setStandalone(String standalone)
/*  269:     */   {
/*  270: 658 */     setOutputProperty("standalone", standalone);
/*  271:     */   }
/*  272:     */   
/*  273:     */   protected void setStandaloneInternal(String standalone)
/*  274:     */   {
/*  275: 667 */     if ("yes".equals(standalone)) {
/*  276: 668 */       this.m_standalone = "yes";
/*  277:     */     } else {
/*  278: 670 */       this.m_standalone = "no";
/*  279:     */     }
/*  280:     */   }
/*  281:     */   
/*  282:     */   public String getStandalone()
/*  283:     */   {
/*  284: 682 */     return this.m_standalone;
/*  285:     */   }
/*  286:     */   
/*  287:     */   public boolean getIndent()
/*  288:     */   {
/*  289: 691 */     return this.m_doIndent;
/*  290:     */   }
/*  291:     */   
/*  292:     */   public String getMediaType()
/*  293:     */   {
/*  294: 701 */     return this.m_mediatype;
/*  295:     */   }
/*  296:     */   
/*  297:     */   public String getVersion()
/*  298:     */   {
/*  299: 710 */     return this.m_version;
/*  300:     */   }
/*  301:     */   
/*  302:     */   public void setVersion(String version)
/*  303:     */   {
/*  304: 720 */     setOutputProperty("version", version);
/*  305:     */   }
/*  306:     */   
/*  307:     */   public void setMediaType(String mediaType)
/*  308:     */   {
/*  309: 732 */     setOutputProperty("media-type", mediaType);
/*  310:     */   }
/*  311:     */   
/*  312:     */   public int getIndentAmount()
/*  313:     */   {
/*  314: 740 */     return this.m_indentAmount;
/*  315:     */   }
/*  316:     */   
/*  317:     */   public void setIndentAmount(int m_indentAmount)
/*  318:     */   {
/*  319: 749 */     this.m_indentAmount = m_indentAmount;
/*  320:     */   }
/*  321:     */   
/*  322:     */   public void setIndent(boolean doIndent)
/*  323:     */   {
/*  324: 761 */     String val = doIndent ? "yes" : "no";
/*  325: 762 */     setOutputProperty("indent", val);
/*  326:     */   }
/*  327:     */   
/*  328:     */   public DOMSerializer asDOMSerializer()
/*  329:     */     throws IOException
/*  330:     */   {
/*  331: 794 */     return this;
/*  332:     */   }
/*  333:     */   
/*  334:     */   private static final boolean subPartMatch(String p, String t)
/*  335:     */   {
/*  336: 807 */     return (p == t) || ((null != p) && (p.equals(t)));
/*  337:     */   }
/*  338:     */   
/*  339:     */   protected static final String getPrefixPart(String qname)
/*  340:     */   {
/*  341: 821 */     int col = qname.indexOf(':');
/*  342: 822 */     return col > 0 ? qname.substring(0, col) : null;
/*  343:     */   }
/*  344:     */   
/*  345:     */   public NamespaceMappings getNamespaceMappings()
/*  346:     */   {
/*  347: 833 */     return this.m_prefixMap;
/*  348:     */   }
/*  349:     */   
/*  350:     */   public String getPrefix(String namespaceURI)
/*  351:     */   {
/*  352: 844 */     String prefix = this.m_prefixMap.lookupPrefix(namespaceURI);
/*  353: 845 */     return prefix;
/*  354:     */   }
/*  355:     */   
/*  356:     */   public String getNamespaceURI(String qname, boolean isElement)
/*  357:     */   {
/*  358: 858 */     String uri = "";
/*  359: 859 */     int col = qname.lastIndexOf(':');
/*  360: 860 */     String prefix = col > 0 ? qname.substring(0, col) : "";
/*  361: 862 */     if ((!"".equals(prefix)) || (isElement)) {
/*  362: 864 */       if (this.m_prefixMap != null)
/*  363:     */       {
/*  364: 866 */         uri = this.m_prefixMap.lookupNamespace(prefix);
/*  365: 867 */         if ((uri == null) && (!prefix.equals("xmlns"))) {
/*  366: 869 */           throw new RuntimeException(Utils.messages.createMessage("ER_NAMESPACE_PREFIX", new Object[] { qname.substring(0, col) }));
/*  367:     */         }
/*  368:     */       }
/*  369:     */     }
/*  370: 876 */     return uri;
/*  371:     */   }
/*  372:     */   
/*  373:     */   public String getNamespaceURIFromPrefix(String prefix)
/*  374:     */   {
/*  375: 888 */     String uri = null;
/*  376: 889 */     if (this.m_prefixMap != null) {
/*  377: 890 */       uri = this.m_prefixMap.lookupNamespace(prefix);
/*  378:     */     }
/*  379: 891 */     return uri;
/*  380:     */   }
/*  381:     */   
/*  382:     */   public void entityReference(String name)
/*  383:     */     throws SAXException
/*  384:     */   {
/*  385: 904 */     flushPending();
/*  386:     */     
/*  387: 906 */     startEntity(name);
/*  388: 907 */     endEntity(name);
/*  389: 909 */     if (this.m_tracer != null) {
/*  390: 910 */       fireEntityReference(name);
/*  391:     */     }
/*  392:     */   }
/*  393:     */   
/*  394:     */   public void setTransformer(Transformer t)
/*  395:     */   {
/*  396: 920 */     this.m_transformer = t;
/*  397: 925 */     if (((this.m_transformer instanceof SerializerTrace)) && (((SerializerTrace)this.m_transformer).hasTraceListeners())) {
/*  398: 927 */       this.m_tracer = ((SerializerTrace)this.m_transformer);
/*  399:     */     } else {
/*  400: 929 */       this.m_tracer = null;
/*  401:     */     }
/*  402:     */   }
/*  403:     */   
/*  404:     */   public Transformer getTransformer()
/*  405:     */   {
/*  406: 939 */     return this.m_transformer;
/*  407:     */   }
/*  408:     */   
/*  409:     */   public void characters(Node node)
/*  410:     */     throws SAXException
/*  411:     */   {
/*  412: 951 */     flushPending();
/*  413: 952 */     String data = node.getNodeValue();
/*  414: 953 */     if (data != null)
/*  415:     */     {
/*  416: 955 */       int length = data.length();
/*  417: 956 */       if (length > this.m_charsBuff.length) {
/*  418: 958 */         this.m_charsBuff = new char[length * 2 + 1];
/*  419:     */       }
/*  420: 960 */       data.getChars(0, length, this.m_charsBuff, 0);
/*  421: 961 */       characters(this.m_charsBuff, 0, length);
/*  422:     */     }
/*  423:     */   }
/*  424:     */   
/*  425:     */   public void fatalError(SAXParseException exc)
/*  426:     */     throws SAXException
/*  427:     */   {
/*  428: 977 */     this.m_elemContext.m_startTagOpen = false;
/*  429:     */   }
/*  430:     */   
/*  431:     */   protected void fireStartEntity(String name)
/*  432:     */     throws SAXException
/*  433:     */   {
/*  434: 995 */     if (this.m_tracer != null)
/*  435:     */     {
/*  436: 997 */       flushMyWriter();
/*  437: 998 */       this.m_tracer.fireGenerateEvent(9, name);
/*  438:     */     }
/*  439:     */   }
/*  440:     */   
/*  441:     */   private void flushMyWriter()
/*  442:     */   {
/*  443:1027 */     if (this.m_writer != null) {
/*  444:     */       try
/*  445:     */       {
/*  446:1031 */         this.m_writer.flush();
/*  447:     */       }
/*  448:     */       catch (IOException ioe) {}
/*  449:     */     }
/*  450:     */   }
/*  451:     */   
/*  452:     */   protected void fireCDATAEvent(char[] chars, int start, int length)
/*  453:     */     throws SAXException
/*  454:     */   {
/*  455:1048 */     if (this.m_tracer != null)
/*  456:     */     {
/*  457:1050 */       flushMyWriter();
/*  458:1051 */       this.m_tracer.fireGenerateEvent(10, chars, start, length);
/*  459:     */     }
/*  460:     */   }
/*  461:     */   
/*  462:     */   protected void fireCommentEvent(char[] chars, int start, int length)
/*  463:     */     throws SAXException
/*  464:     */   {
/*  465:1064 */     if (this.m_tracer != null)
/*  466:     */     {
/*  467:1066 */       flushMyWriter();
/*  468:1067 */       this.m_tracer.fireGenerateEvent(8, new String(chars, start, length));
/*  469:     */     }
/*  470:     */   }
/*  471:     */   
/*  472:     */   public void fireEndEntity(String name)
/*  473:     */     throws SAXException
/*  474:     */   {
/*  475:1079 */     if (this.m_tracer != null) {
/*  476:1080 */       flushMyWriter();
/*  477:     */     }
/*  478:     */   }
/*  479:     */   
/*  480:     */   protected void fireStartDoc()
/*  481:     */     throws SAXException
/*  482:     */   {
/*  483:1090 */     if (this.m_tracer != null)
/*  484:     */     {
/*  485:1092 */       flushMyWriter();
/*  486:1093 */       this.m_tracer.fireGenerateEvent(1);
/*  487:     */     }
/*  488:     */   }
/*  489:     */   
/*  490:     */   protected void fireEndDoc()
/*  491:     */     throws SAXException
/*  492:     */   {
/*  493:1104 */     if (this.m_tracer != null)
/*  494:     */     {
/*  495:1106 */       flushMyWriter();
/*  496:1107 */       this.m_tracer.fireGenerateEvent(2);
/*  497:     */     }
/*  498:     */   }
/*  499:     */   
/*  500:     */   protected void fireStartElem(String elemName)
/*  501:     */     throws SAXException
/*  502:     */   {
/*  503:1121 */     if (this.m_tracer != null)
/*  504:     */     {
/*  505:1123 */       flushMyWriter();
/*  506:1124 */       this.m_tracer.fireGenerateEvent(3, elemName, this.m_attributes);
/*  507:     */     }
/*  508:     */   }
/*  509:     */   
/*  510:     */   protected void fireEscapingEvent(String name, String data)
/*  511:     */     throws SAXException
/*  512:     */   {
/*  513:1150 */     if (this.m_tracer != null)
/*  514:     */     {
/*  515:1152 */       flushMyWriter();
/*  516:1153 */       this.m_tracer.fireGenerateEvent(7, name, data);
/*  517:     */     }
/*  518:     */   }
/*  519:     */   
/*  520:     */   protected void fireEntityReference(String name)
/*  521:     */     throws SAXException
/*  522:     */   {
/*  523:1165 */     if (this.m_tracer != null)
/*  524:     */     {
/*  525:1167 */       flushMyWriter();
/*  526:1168 */       this.m_tracer.fireGenerateEvent(9, name, (Attributes)null);
/*  527:     */     }
/*  528:     */   }
/*  529:     */   
/*  530:     */   public void startDocument()
/*  531:     */     throws SAXException
/*  532:     */   {
/*  533:1190 */     startDocumentInternal();
/*  534:1191 */     this.m_needToCallStartDocument = false;
/*  535:     */   }
/*  536:     */   
/*  537:     */   protected void startDocumentInternal()
/*  538:     */     throws SAXException
/*  539:     */   {
/*  540:1213 */     if (this.m_tracer != null) {
/*  541:1214 */       fireStartDoc();
/*  542:     */     }
/*  543:     */   }
/*  544:     */   
/*  545:     */   public void setSourceLocator(SourceLocator locator)
/*  546:     */   {
/*  547:1225 */     this.m_sourceLocator = locator;
/*  548:     */   }
/*  549:     */   
/*  550:     */   public void setNamespaceMappings(NamespaceMappings mappings)
/*  551:     */   {
/*  552:1236 */     this.m_prefixMap = mappings;
/*  553:     */   }
/*  554:     */   
/*  555:     */   public boolean reset()
/*  556:     */   {
/*  557:1241 */     resetSerializerBase();
/*  558:1242 */     return true;
/*  559:     */   }
/*  560:     */   
/*  561:     */   private void resetSerializerBase()
/*  562:     */   {
/*  563:1251 */     this.m_attributes.clear();
/*  564:1252 */     this.m_CdataElems = null;
/*  565:1253 */     this.m_cdataTagOpen = false;
/*  566:1254 */     this.m_docIsEmpty = true;
/*  567:1255 */     this.m_doctypePublic = null;
/*  568:1256 */     this.m_doctypeSystem = null;
/*  569:1257 */     this.m_doIndent = false;
/*  570:1258 */     this.m_elemContext = new ElemContext();
/*  571:1259 */     this.m_indentAmount = 0;
/*  572:1260 */     this.m_inEntityRef = false;
/*  573:1261 */     this.m_inExternalDTD = false;
/*  574:1262 */     this.m_mediatype = null;
/*  575:1263 */     this.m_needToCallStartDocument = true;
/*  576:1264 */     this.m_needToOutputDocTypeDecl = false;
/*  577:1265 */     if (this.m_OutputProps != null) {
/*  578:1266 */       this.m_OutputProps.clear();
/*  579:     */     }
/*  580:1267 */     if (this.m_OutputPropsDefault != null) {
/*  581:1268 */       this.m_OutputPropsDefault.clear();
/*  582:     */     }
/*  583:1269 */     if (this.m_prefixMap != null) {
/*  584:1270 */       this.m_prefixMap.reset();
/*  585:     */     }
/*  586:1271 */     this.m_shouldNotWriteXMLHeader = false;
/*  587:1272 */     this.m_sourceLocator = null;
/*  588:1273 */     this.m_standalone = null;
/*  589:1274 */     this.m_standaloneWasSpecified = false;
/*  590:1275 */     this.m_StringOfCDATASections = null;
/*  591:1276 */     this.m_tracer = null;
/*  592:1277 */     this.m_transformer = null;
/*  593:1278 */     this.m_version = null;
/*  594:     */   }
/*  595:     */   
/*  596:     */   final boolean inTemporaryOutputState()
/*  597:     */   {
/*  598:1297 */     return getEncoding() == null;
/*  599:     */   }
/*  600:     */   
/*  601:     */   public void addAttribute(String uri, String localName, String rawName, String type, String value)
/*  602:     */     throws SAXException
/*  603:     */   {
/*  604:1308 */     if (this.m_elemContext.m_startTagOpen) {
/*  605:1310 */       addAttributeAlways(uri, localName, rawName, type, value, false);
/*  606:     */     }
/*  607:     */   }
/*  608:     */   
/*  609:1353 */   protected String m_StringOfCDATASections = null;
/*  610:1355 */   boolean m_docIsEmpty = true;
/*  611:     */   
/*  612:     */   void initCdataElems(String s)
/*  613:     */   {
/*  614:1358 */     if (s != null)
/*  615:     */     {
/*  616:1360 */       int max = s.length();
/*  617:     */       
/*  618:     */ 
/*  619:1363 */       boolean inCurly = false;
/*  620:     */       
/*  621:     */ 
/*  622:1366 */       boolean foundURI = false;
/*  623:     */       
/*  624:1368 */       StringBuffer buf = new StringBuffer();
/*  625:1369 */       String uri = null;
/*  626:1370 */       String localName = null;
/*  627:1377 */       for (int i = 0; i < max; i++)
/*  628:     */       {
/*  629:1380 */         char c = s.charAt(i);
/*  630:1382 */         if (Character.isWhitespace(c))
/*  631:     */         {
/*  632:1384 */           if (!inCurly)
/*  633:     */           {
/*  634:1386 */             if (buf.length() > 0)
/*  635:     */             {
/*  636:1388 */               localName = buf.toString();
/*  637:1389 */               if (!foundURI) {
/*  638:1390 */                 uri = "";
/*  639:     */               }
/*  640:1391 */               addCDATAElement(uri, localName);
/*  641:1392 */               buf.setLength(0);
/*  642:1393 */               foundURI = false;
/*  643:     */             }
/*  644:     */           }
/*  645:     */           else {
/*  646:1398 */             buf.append(c);
/*  647:     */           }
/*  648:     */         }
/*  649:1400 */         else if ('{' == c)
/*  650:     */         {
/*  651:1401 */           inCurly = true;
/*  652:     */         }
/*  653:1402 */         else if ('}' == c)
/*  654:     */         {
/*  655:1405 */           foundURI = true;
/*  656:1406 */           uri = buf.toString();
/*  657:1407 */           buf.setLength(0);
/*  658:1408 */           inCurly = false;
/*  659:     */         }
/*  660:     */         else
/*  661:     */         {
/*  662:1413 */           buf.append(c);
/*  663:     */         }
/*  664:     */       }
/*  665:1418 */       if (buf.length() > 0)
/*  666:     */       {
/*  667:1421 */         localName = buf.toString();
/*  668:1422 */         if (!foundURI) {
/*  669:1423 */           uri = "";
/*  670:     */         }
/*  671:1424 */         addCDATAElement(uri, localName);
/*  672:     */       }
/*  673:     */     }
/*  674:     */   }
/*  675:     */   
/*  676:1428 */   protected Hashtable m_CdataElems = null;
/*  677:     */   private HashMap m_OutputProps;
/*  678:     */   private HashMap m_OutputPropsDefault;
/*  679:     */   
/*  680:     */   private void addCDATAElement(String uri, String localName)
/*  681:     */   {
/*  682:1431 */     if (this.m_CdataElems == null) {
/*  683:1432 */       this.m_CdataElems = new Hashtable();
/*  684:     */     }
/*  685:1435 */     Hashtable h = (Hashtable)this.m_CdataElems.get(localName);
/*  686:1436 */     if (h == null)
/*  687:     */     {
/*  688:1437 */       h = new Hashtable();
/*  689:1438 */       this.m_CdataElems.put(localName, h);
/*  690:     */     }
/*  691:1440 */     h.put(uri, uri);
/*  692:     */   }
/*  693:     */   
/*  694:     */   public boolean documentIsEmpty()
/*  695:     */   {
/*  696:1454 */     return (this.m_docIsEmpty) && (this.m_elemContext.m_currentElemDepth == 0);
/*  697:     */   }
/*  698:     */   
/*  699:     */   protected boolean isCdataSection()
/*  700:     */   {
/*  701:1473 */     boolean b = false;
/*  702:1475 */     if (null != this.m_StringOfCDATASections)
/*  703:     */     {
/*  704:1477 */       if (this.m_elemContext.m_elementLocalName == null)
/*  705:     */       {
/*  706:1479 */         String localName = getLocalName(this.m_elemContext.m_elementName);
/*  707:1480 */         this.m_elemContext.m_elementLocalName = localName;
/*  708:     */       }
/*  709:1483 */       if (this.m_elemContext.m_elementURI == null) {
/*  710:1485 */         this.m_elemContext.m_elementURI = getElementURI();
/*  711:1487 */       } else if (this.m_elemContext.m_elementURI.length() == 0) {
/*  712:1488 */         if (this.m_elemContext.m_elementName == null) {
/*  713:1489 */           this.m_elemContext.m_elementName = this.m_elemContext.m_elementLocalName;
/*  714:1492 */         } else if (this.m_elemContext.m_elementLocalName.length() < this.m_elemContext.m_elementName.length()) {
/*  715:1495 */           this.m_elemContext.m_elementURI = getElementURI();
/*  716:     */         }
/*  717:     */       }
/*  718:1499 */       Hashtable h = (Hashtable)this.m_CdataElems.get(this.m_elemContext.m_elementLocalName);
/*  719:1500 */       if (h != null)
/*  720:     */       {
/*  721:1502 */         Object obj = h.get(this.m_elemContext.m_elementURI);
/*  722:1503 */         if (obj != null) {
/*  723:1504 */           b = true;
/*  724:     */         }
/*  725:     */       }
/*  726:     */     }
/*  727:1508 */     return b;
/*  728:     */   }
/*  729:     */   
/*  730:     */   private String getElementURI()
/*  731:     */   {
/*  732:1520 */     String uri = null;
/*  733:     */     
/*  734:     */ 
/*  735:     */ 
/*  736:     */ 
/*  737:     */ 
/*  738:1526 */     String prefix = getPrefixPart(this.m_elemContext.m_elementName);
/*  739:1528 */     if (prefix == null) {
/*  740:1530 */       uri = this.m_prefixMap.lookupNamespace("");
/*  741:     */     } else {
/*  742:1532 */       uri = this.m_prefixMap.lookupNamespace(prefix);
/*  743:     */     }
/*  744:1534 */     if (uri == null) {
/*  745:1539 */       uri = "";
/*  746:     */     }
/*  747:1542 */     return uri;
/*  748:     */   }
/*  749:     */   
/*  750:     */   public String getOutputProperty(String name)
/*  751:     */   {
/*  752:1552 */     String val = getOutputPropertyNonDefault(name);
/*  753:1554 */     if (val == null) {
/*  754:1555 */       val = getOutputPropertyDefault(name);
/*  755:     */     }
/*  756:1556 */     return val;
/*  757:     */   }
/*  758:     */   
/*  759:     */   public String getOutputPropertyNonDefault(String name)
/*  760:     */   {
/*  761:1569 */     return getProp(name, false);
/*  762:     */   }
/*  763:     */   
/*  764:     */   public Object asDOM3Serializer()
/*  765:     */     throws IOException
/*  766:     */   {
/*  767:1584 */     return new DOM3SerializerImpl(this);
/*  768:     */   }
/*  769:     */   
/*  770:     */   public String getOutputPropertyDefault(String name)
/*  771:     */   {
/*  772:1592 */     return getProp(name, true);
/*  773:     */   }
/*  774:     */   
/*  775:     */   public void setOutputProperty(String name, String val)
/*  776:     */   {
/*  777:1601 */     setProp(name, val, false);
/*  778:     */   }
/*  779:     */   
/*  780:     */   public void setOutputPropertyDefault(String name, String val)
/*  781:     */   {
/*  782:1610 */     setProp(name, val, true);
/*  783:     */   }
/*  784:     */   
/*  785:     */   Set getOutputPropDefaultKeys()
/*  786:     */   {
/*  787:1628 */     return this.m_OutputPropsDefault.keySet();
/*  788:     */   }
/*  789:     */   
/*  790:     */   Set getOutputPropKeys()
/*  791:     */   {
/*  792:1631 */     return this.m_OutputProps.keySet();
/*  793:     */   }
/*  794:     */   
/*  795:     */   private String getProp(String name, boolean defaultVal)
/*  796:     */   {
/*  797:1635 */     if (this.m_OutputProps == null)
/*  798:     */     {
/*  799:1636 */       this.m_OutputProps = new HashMap();
/*  800:1637 */       this.m_OutputPropsDefault = new HashMap();
/*  801:     */     }
/*  802:     */     String val;
/*  803:1641 */     if (defaultVal) {
/*  804:1642 */       val = (String)this.m_OutputPropsDefault.get(name);
/*  805:     */     } else {
/*  806:1644 */       val = (String)this.m_OutputProps.get(name);
/*  807:     */     }
/*  808:1646 */     return val;
/*  809:     */   }
/*  810:     */   
/*  811:     */   void setProp(String name, String val, boolean defaultVal)
/*  812:     */   {
/*  813:1657 */     if (this.m_OutputProps == null)
/*  814:     */     {
/*  815:1658 */       this.m_OutputProps = new HashMap();
/*  816:1659 */       this.m_OutputPropsDefault = new HashMap();
/*  817:     */     }
/*  818:1662 */     if (defaultVal)
/*  819:     */     {
/*  820:1663 */       this.m_OutputPropsDefault.put(name, val);
/*  821:     */     }
/*  822:1665 */     else if (("cdata-section-elements".equals(name)) && (val != null))
/*  823:     */     {
/*  824:1666 */       initCdataElems(val);
/*  825:1667 */       String oldVal = (String)this.m_OutputProps.get(name);
/*  826:     */       String newVal;
/*  827:1669 */       if (oldVal == null) {
/*  828:1670 */         newVal = oldVal + ' ' + val;
/*  829:     */       } else {
/*  830:1672 */         newVal = val;
/*  831:     */       }
/*  832:1673 */       this.m_OutputProps.put(name, newVal);
/*  833:     */     }
/*  834:     */     else
/*  835:     */     {
/*  836:1676 */       this.m_OutputProps.put(name, val);
/*  837:     */     }
/*  838:     */   }
/*  839:     */   
/*  840:     */   static char getFirstCharLocName(String name)
/*  841:     */   {
/*  842:1690 */     int i = name.indexOf('}');
/*  843:     */     char first;
/*  844:1691 */     if (i < 0) {
/*  845:1692 */       first = name.charAt(0);
/*  846:     */     } else {
/*  847:1694 */       first = name.charAt(i + 1);
/*  848:     */     }
/*  849:1695 */     return first;
/*  850:     */   }
/*  851:     */   
/*  852:     */   public void setDocumentLocator(Locator locator) {}
/*  853:     */   
/*  854:     */   public void close() {}
/*  855:     */   
/*  856:     */   protected void initCDATA() {}
/*  857:     */   
/*  858:     */   public void namespaceAfterStartElement(String uri, String prefix)
/*  859:     */     throws SAXException
/*  860:     */   {}
/*  861:     */   
/*  862:     */   public void error(SAXParseException exc)
/*  863:     */     throws SAXException
/*  864:     */   {}
/*  865:     */   
/*  866:     */   public void warning(SAXParseException exc)
/*  867:     */     throws SAXException
/*  868:     */   {}
/*  869:     */   
/*  870:     */   public void notationDecl(String arg0, String arg1, String arg2)
/*  871:     */     throws SAXException
/*  872:     */   {}
/*  873:     */   
/*  874:     */   public void unparsedEntityDecl(String arg0, String arg1, String arg2, String arg3)
/*  875:     */     throws SAXException
/*  876:     */   {}
/*  877:     */   
/*  878:     */   public void setDTDEntityExpansion(boolean expand) {}
/*  879:     */   
/*  880:     */   public abstract void flushPending()
/*  881:     */     throws SAXException;
/*  882:     */   
/*  883:     */   public abstract boolean setEscaping(boolean paramBoolean)
/*  884:     */     throws SAXException;
/*  885:     */   
/*  886:     */   public abstract void serialize(Node paramNode)
/*  887:     */     throws IOException;
/*  888:     */   
/*  889:     */   public abstract void setContentHandler(ContentHandler paramContentHandler);
/*  890:     */   
/*  891:     */   public abstract void addUniqueAttribute(String paramString1, String paramString2, int paramInt)
/*  892:     */     throws SAXException;
/*  893:     */   
/*  894:     */   public abstract boolean startPrefixMapping(String paramString1, String paramString2, boolean paramBoolean)
/*  895:     */     throws SAXException;
/*  896:     */   
/*  897:     */   public abstract void startElement(String paramString)
/*  898:     */     throws SAXException;
/*  899:     */   
/*  900:     */   public abstract void startElement(String paramString1, String paramString2, String paramString3)
/*  901:     */     throws SAXException;
/*  902:     */   
/*  903:     */   public abstract void endElement(String paramString)
/*  904:     */     throws SAXException;
/*  905:     */   
/*  906:     */   public abstract void characters(String paramString)
/*  907:     */     throws SAXException;
/*  908:     */   
/*  909:     */   public abstract void skippedEntity(String paramString)
/*  910:     */     throws SAXException;
/*  911:     */   
/*  912:     */   public abstract void processingInstruction(String paramString1, String paramString2)
/*  913:     */     throws SAXException;
/*  914:     */   
/*  915:     */   public abstract void ignorableWhitespace(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*  916:     */     throws SAXException;
/*  917:     */   
/*  918:     */   public abstract void characters(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*  919:     */     throws SAXException;
/*  920:     */   
/*  921:     */   public abstract void endElement(String paramString1, String paramString2, String paramString3)
/*  922:     */     throws SAXException;
/*  923:     */   
/*  924:     */   public abstract void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
/*  925:     */     throws SAXException;
/*  926:     */   
/*  927:     */   public abstract void endPrefixMapping(String paramString)
/*  928:     */     throws SAXException;
/*  929:     */   
/*  930:     */   public abstract void startPrefixMapping(String paramString1, String paramString2)
/*  931:     */     throws SAXException;
/*  932:     */   
/*  933:     */   public abstract void endDocument()
/*  934:     */     throws SAXException;
/*  935:     */   
/*  936:     */   public abstract void comment(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*  937:     */     throws SAXException;
/*  938:     */   
/*  939:     */   public abstract void endCDATA()
/*  940:     */     throws SAXException;
/*  941:     */   
/*  942:     */   public abstract void startCDATA()
/*  943:     */     throws SAXException;
/*  944:     */   
/*  945:     */   public abstract void startEntity(String paramString)
/*  946:     */     throws SAXException;
/*  947:     */   
/*  948:     */   public abstract void endDTD()
/*  949:     */     throws SAXException;
/*  950:     */   
/*  951:     */   public abstract void startDTD(String paramString1, String paramString2, String paramString3)
/*  952:     */     throws SAXException;
/*  953:     */   
/*  954:     */   public abstract void setCdataSectionElements(Vector paramVector);
/*  955:     */   
/*  956:     */   public abstract void externalEntityDecl(String paramString1, String paramString2, String paramString3)
/*  957:     */     throws SAXException;
/*  958:     */   
/*  959:     */   public abstract void internalEntityDecl(String paramString1, String paramString2)
/*  960:     */     throws SAXException;
/*  961:     */   
/*  962:     */   public abstract void attributeDecl(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
/*  963:     */     throws SAXException;
/*  964:     */   
/*  965:     */   public abstract void elementDecl(String paramString1, String paramString2)
/*  966:     */     throws SAXException;
/*  967:     */   
/*  968:     */   public abstract Properties getOutputFormat();
/*  969:     */   
/*  970:     */   public abstract void setOutputFormat(Properties paramProperties);
/*  971:     */   
/*  972:     */   public abstract Writer getWriter();
/*  973:     */   
/*  974:     */   public abstract void setWriter(Writer paramWriter);
/*  975:     */   
/*  976:     */   public abstract OutputStream getOutputStream();
/*  977:     */   
/*  978:     */   public abstract void setOutputStream(OutputStream paramOutputStream);
/*  979:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.serializer.SerializerBase
 * JD-Core Version:    0.7.0.1
 */