/*    1:     */ package org.apache.xalan.templates;
/*    2:     */ 
/*    3:     */ import java.util.ArrayList;
/*    4:     */ import java.util.Iterator;
/*    5:     */ import java.util.List;
/*    6:     */ import java.util.Vector;
/*    7:     */ import javax.xml.transform.TransformerException;
/*    8:     */ import org.apache.xalan.res.XSLMessages;
/*    9:     */ import org.apache.xalan.trace.TraceManager;
/*   10:     */ import org.apache.xalan.transformer.TransformerImpl;
/*   11:     */ import org.apache.xml.serializer.ExtendedContentHandler;
/*   12:     */ import org.apache.xml.serializer.SerializationHandler;
/*   13:     */ import org.apache.xml.utils.StringVector;
/*   14:     */ import org.apache.xpath.XPathContext;
/*   15:     */ import org.w3c.dom.Attr;
/*   16:     */ import org.w3c.dom.DOMException;
/*   17:     */ import org.w3c.dom.Document;
/*   18:     */ import org.w3c.dom.Element;
/*   19:     */ import org.w3c.dom.NamedNodeMap;
/*   20:     */ import org.w3c.dom.Node;
/*   21:     */ import org.w3c.dom.NodeList;
/*   22:     */ import org.w3c.dom.TypeInfo;
/*   23:     */ import org.w3c.dom.UserDataHandler;
/*   24:     */ import org.xml.sax.ContentHandler;
/*   25:     */ import org.xml.sax.SAXException;
/*   26:     */ 
/*   27:     */ public class ElemLiteralResult
/*   28:     */   extends ElemUse
/*   29:     */ {
/*   30:     */   static final long serialVersionUID = -8703409074421657260L;
/*   31:     */   private static final String EMPTYSTRING = "";
/*   32:  64 */   private boolean isLiteralResultAsStylesheet = false;
/*   33:     */   
/*   34:     */   public void setIsLiteralResultAsStylesheet(boolean b)
/*   35:     */   {
/*   36:  76 */     this.isLiteralResultAsStylesheet = b;
/*   37:     */   }
/*   38:     */   
/*   39:     */   public boolean getIsLiteralResultAsStylesheet()
/*   40:     */   {
/*   41:  89 */     return this.isLiteralResultAsStylesheet;
/*   42:     */   }
/*   43:     */   
/*   44:     */   public void compose(StylesheetRoot sroot)
/*   45:     */     throws TransformerException
/*   46:     */   {
/*   47: 100 */     super.compose(sroot);
/*   48: 101 */     StylesheetRoot.ComposeState cstate = sroot.getComposeState();
/*   49: 102 */     Vector vnames = cstate.getVariableNames();
/*   50: 103 */     if (null != this.m_avts)
/*   51:     */     {
/*   52: 105 */       int nAttrs = this.m_avts.size();
/*   53: 107 */       for (int i = nAttrs - 1; i >= 0; i--)
/*   54:     */       {
/*   55: 109 */         AVT avt = (AVT)this.m_avts.get(i);
/*   56: 110 */         avt.fixupVariables(vnames, cstate.getGlobalsSize());
/*   57:     */       }
/*   58:     */     }
/*   59:     */   }
/*   60:     */   
/*   61: 121 */   private List m_avts = null;
/*   62: 125 */   private List m_xslAttr = null;
/*   63:     */   private String m_namespace;
/*   64:     */   private String m_localName;
/*   65:     */   private String m_rawName;
/*   66:     */   private StringVector m_ExtensionElementURIs;
/*   67:     */   private String m_version;
/*   68:     */   private StringVector m_excludeResultPrefixes;
/*   69:     */   
/*   70:     */   public void addLiteralResultAttribute(AVT avt)
/*   71:     */   {
/*   72: 135 */     if (null == this.m_avts) {
/*   73: 136 */       this.m_avts = new ArrayList();
/*   74:     */     }
/*   75: 138 */     this.m_avts.add(avt);
/*   76:     */   }
/*   77:     */   
/*   78:     */   public void addLiteralResultAttribute(String att)
/*   79:     */   {
/*   80: 149 */     if (null == this.m_xslAttr) {
/*   81: 150 */       this.m_xslAttr = new ArrayList();
/*   82:     */     }
/*   83: 152 */     this.m_xslAttr.add(att);
/*   84:     */   }
/*   85:     */   
/*   86:     */   public void setXmlSpace(AVT avt)
/*   87:     */   {
/*   88: 169 */     addLiteralResultAttribute(avt);
/*   89: 170 */     String val = avt.getSimpleString();
/*   90: 171 */     if (val.equals("default")) {
/*   91: 173 */       super.setXmlSpace(2);
/*   92: 175 */     } else if (val.equals("preserve")) {
/*   93: 177 */       super.setXmlSpace(1);
/*   94:     */     }
/*   95:     */   }
/*   96:     */   
/*   97:     */   public AVT getLiteralResultAttributeNS(String namespaceURI, String localName)
/*   98:     */   {
/*   99: 193 */     if (null != this.m_avts)
/*  100:     */     {
/*  101: 195 */       int nAttrs = this.m_avts.size();
/*  102: 197 */       for (int i = nAttrs - 1; i >= 0; i--)
/*  103:     */       {
/*  104: 199 */         AVT avt = (AVT)this.m_avts.get(i);
/*  105: 201 */         if ((avt.getName().equals(localName)) && (avt.getURI().equals(namespaceURI))) {
/*  106: 204 */           return avt;
/*  107:     */         }
/*  108:     */       }
/*  109:     */     }
/*  110: 209 */     return null;
/*  111:     */   }
/*  112:     */   
/*  113:     */   public String getAttributeNS(String namespaceURI, String localName)
/*  114:     */   {
/*  115: 224 */     AVT avt = getLiteralResultAttributeNS(namespaceURI, localName);
/*  116: 226 */     if (null != avt) {
/*  117: 228 */       return avt.getSimpleString();
/*  118:     */     }
/*  119: 231 */     return "";
/*  120:     */   }
/*  121:     */   
/*  122:     */   public AVT getLiteralResultAttribute(String name)
/*  123:     */   {
/*  124: 245 */     if (null != this.m_avts)
/*  125:     */     {
/*  126: 247 */       int nAttrs = this.m_avts.size();
/*  127: 248 */       String namespace = null;
/*  128: 249 */       for (int i = nAttrs - 1; i >= 0; i--)
/*  129:     */       {
/*  130: 251 */         AVT avt = (AVT)this.m_avts.get(i);
/*  131: 252 */         namespace = avt.getURI();
/*  132: 254 */         if (((namespace != null) && (!namespace.equals("")) && ((namespace + ":" + avt.getName()).equals(name))) || (((namespace == null) || (namespace.equals(""))) && (avt.getRawName().equals(name)))) {
/*  133: 258 */           return avt;
/*  134:     */         }
/*  135:     */       }
/*  136:     */     }
/*  137: 263 */     return null;
/*  138:     */   }
/*  139:     */   
/*  140:     */   public String getAttribute(String rawName)
/*  141:     */   {
/*  142: 278 */     AVT avt = getLiteralResultAttribute(rawName);
/*  143: 280 */     if (null != avt) {
/*  144: 282 */       return avt.getSimpleString();
/*  145:     */     }
/*  146: 285 */     return "";
/*  147:     */   }
/*  148:     */   
/*  149:     */   public boolean containsExcludeResultPrefix(String prefix, String uri)
/*  150:     */   {
/*  151: 301 */     if ((uri == null) || ((null == this.m_excludeResultPrefixes) && (null == this.m_ExtensionElementURIs))) {
/*  152: 305 */       return super.containsExcludeResultPrefix(prefix, uri);
/*  153:     */     }
/*  154: 307 */     if (prefix.length() == 0) {
/*  155: 308 */       prefix = "#default";
/*  156:     */     }
/*  157: 312 */     if (this.m_excludeResultPrefixes != null) {
/*  158: 313 */       for (int i = 0; i < this.m_excludeResultPrefixes.size(); i++) {
/*  159: 315 */         if (uri.equals(getNamespaceForPrefix(this.m_excludeResultPrefixes.elementAt(i)))) {
/*  160: 316 */           return true;
/*  161:     */         }
/*  162:     */       }
/*  163:     */     }
/*  164: 320 */     if ((this.m_ExtensionElementURIs != null) && (this.m_ExtensionElementURIs.contains(uri))) {
/*  165: 321 */       return true;
/*  166:     */     }
/*  167: 323 */     return super.containsExcludeResultPrefix(prefix, uri);
/*  168:     */   }
/*  169:     */   
/*  170:     */   public void resolvePrefixTables()
/*  171:     */     throws TransformerException
/*  172:     */   {
/*  173: 335 */     super.resolvePrefixTables();
/*  174:     */     
/*  175: 337 */     StylesheetRoot stylesheet = getStylesheetRoot();
/*  176: 339 */     if ((null != this.m_namespace) && (this.m_namespace.length() > 0))
/*  177:     */     {
/*  178: 341 */       NamespaceAlias nsa = stylesheet.getNamespaceAliasComposed(this.m_namespace);
/*  179: 343 */       if (null != nsa)
/*  180:     */       {
/*  181: 345 */         this.m_namespace = nsa.getResultNamespace();
/*  182:     */         
/*  183:     */ 
/*  184: 348 */         String resultPrefix = nsa.getStylesheetPrefix();
/*  185: 350 */         if ((null != resultPrefix) && (resultPrefix.length() > 0)) {
/*  186: 351 */           this.m_rawName = (resultPrefix + ":" + this.m_localName);
/*  187:     */         } else {
/*  188: 353 */           this.m_rawName = this.m_localName;
/*  189:     */         }
/*  190:     */       }
/*  191:     */     }
/*  192: 357 */     if (null != this.m_avts)
/*  193:     */     {
/*  194: 359 */       int n = this.m_avts.size();
/*  195: 361 */       for (int i = 0; i < n; i++)
/*  196:     */       {
/*  197: 363 */         AVT avt = (AVT)this.m_avts.get(i);
/*  198:     */         
/*  199:     */ 
/*  200: 366 */         String ns = avt.getURI();
/*  201: 368 */         if ((null != ns) && (ns.length() > 0))
/*  202:     */         {
/*  203: 370 */           NamespaceAlias nsa = stylesheet.getNamespaceAliasComposed(this.m_namespace);
/*  204: 373 */           if (null != nsa)
/*  205:     */           {
/*  206: 375 */             String namespace = nsa.getResultNamespace();
/*  207:     */             
/*  208:     */ 
/*  209: 378 */             String resultPrefix = nsa.getStylesheetPrefix();
/*  210: 379 */             String rawName = avt.getName();
/*  211: 381 */             if ((null != resultPrefix) && (resultPrefix.length() > 0)) {
/*  212: 382 */               rawName = resultPrefix + ":" + rawName;
/*  213:     */             }
/*  214: 384 */             avt.setURI(namespace);
/*  215: 385 */             avt.setRawName(rawName);
/*  216:     */           }
/*  217:     */         }
/*  218:     */       }
/*  219:     */     }
/*  220:     */   }
/*  221:     */   
/*  222:     */   boolean needToCheckExclude()
/*  223:     */   {
/*  224: 402 */     if ((null == this.m_excludeResultPrefixes) && (null == getPrefixTable()) && (this.m_ExtensionElementURIs == null)) {
/*  225: 405 */       return false;
/*  226:     */     }
/*  227: 410 */     if (null == getPrefixTable()) {
/*  228: 411 */       setPrefixTable(new ArrayList());
/*  229:     */     }
/*  230: 413 */     return true;
/*  231:     */   }
/*  232:     */   
/*  233:     */   public void setNamespace(String ns)
/*  234:     */   {
/*  235: 434 */     if (null == ns) {
/*  236: 435 */       ns = "";
/*  237:     */     }
/*  238: 436 */     this.m_namespace = ns;
/*  239:     */   }
/*  240:     */   
/*  241:     */   public String getNamespace()
/*  242:     */   {
/*  243: 450 */     return this.m_namespace;
/*  244:     */   }
/*  245:     */   
/*  246:     */   public void setLocalName(String localName)
/*  247:     */   {
/*  248: 467 */     this.m_localName = localName;
/*  249:     */   }
/*  250:     */   
/*  251:     */   public String getLocalName()
/*  252:     */   {
/*  253: 481 */     return this.m_localName;
/*  254:     */   }
/*  255:     */   
/*  256:     */   public void setRawName(String rawName)
/*  257:     */   {
/*  258: 498 */     this.m_rawName = rawName;
/*  259:     */   }
/*  260:     */   
/*  261:     */   public String getRawName()
/*  262:     */   {
/*  263: 509 */     return this.m_rawName;
/*  264:     */   }
/*  265:     */   
/*  266:     */   public String getPrefix()
/*  267:     */   {
/*  268: 519 */     int len = this.m_rawName.length() - this.m_localName.length() - 1;
/*  269: 520 */     return len > 0 ? this.m_rawName.substring(0, len) : "";
/*  270:     */   }
/*  271:     */   
/*  272:     */   public void setExtensionElementPrefixes(StringVector v)
/*  273:     */   {
/*  274: 540 */     this.m_ExtensionElementURIs = v;
/*  275:     */   }
/*  276:     */   
/*  277:     */   public NamedNodeMap getAttributes()
/*  278:     */   {
/*  279: 550 */     return new LiteralElementAttributes();
/*  280:     */   }
/*  281:     */   
/*  282:     */   public class LiteralElementAttributes
/*  283:     */     implements NamedNodeMap
/*  284:     */   {
/*  285: 554 */     private int m_count = -1;
/*  286:     */     
/*  287:     */     public LiteralElementAttributes() {}
/*  288:     */     
/*  289:     */     public int getLength()
/*  290:     */     {
/*  291: 571 */       if (this.m_count == -1) {
/*  292: 573 */         if (null != ElemLiteralResult.this.m_avts) {
/*  293: 573 */           this.m_count = ElemLiteralResult.this.m_avts.size();
/*  294:     */         } else {
/*  295: 574 */           this.m_count = 0;
/*  296:     */         }
/*  297:     */       }
/*  298: 576 */       return this.m_count;
/*  299:     */     }
/*  300:     */     
/*  301:     */     public Node getNamedItem(String name)
/*  302:     */     {
/*  303: 588 */       if (getLength() == 0) {
/*  304: 588 */         return null;
/*  305:     */       }
/*  306: 589 */       String uri = null;
/*  307: 590 */       String localName = name;
/*  308: 591 */       int index = name.indexOf(":");
/*  309: 592 */       if (-1 != index)
/*  310:     */       {
/*  311: 593 */         uri = name.substring(0, index);
/*  312: 594 */         localName = name.substring(index + 1);
/*  313:     */       }
/*  314: 596 */       Node retNode = null;
/*  315: 597 */       Iterator eum = ElemLiteralResult.this.m_avts.iterator();
/*  316: 598 */       while (eum.hasNext())
/*  317:     */       {
/*  318: 599 */         AVT avt = (AVT)eum.next();
/*  319: 600 */         if (localName.equals(avt.getName()))
/*  320:     */         {
/*  321: 602 */           String nsURI = avt.getURI();
/*  322: 603 */           if (((uri == null) && (nsURI == null)) || ((uri != null) && (uri.equals(nsURI))))
/*  323:     */           {
/*  324: 606 */             retNode = new ElemLiteralResult.Attribute(ElemLiteralResult.this, avt, ElemLiteralResult.this);
/*  325: 607 */             break;
/*  326:     */           }
/*  327:     */         }
/*  328:     */       }
/*  329: 611 */       return retNode;
/*  330:     */     }
/*  331:     */     
/*  332:     */     public Node getNamedItemNS(String namespaceURI, String localName)
/*  333:     */     {
/*  334: 625 */       if (getLength() == 0) {
/*  335: 625 */         return null;
/*  336:     */       }
/*  337: 626 */       Node retNode = null;
/*  338: 627 */       Iterator eum = ElemLiteralResult.this.m_avts.iterator();
/*  339: 628 */       while (eum.hasNext())
/*  340:     */       {
/*  341: 630 */         AVT avt = (AVT)eum.next();
/*  342: 631 */         if (localName.equals(avt.getName()))
/*  343:     */         {
/*  344: 633 */           String nsURI = avt.getURI();
/*  345: 634 */           if (((namespaceURI == null) && (nsURI == null)) || ((namespaceURI != null) && (namespaceURI.equals(nsURI))))
/*  346:     */           {
/*  347: 637 */             retNode = new ElemLiteralResult.Attribute(ElemLiteralResult.this, avt, ElemLiteralResult.this);
/*  348: 638 */             break;
/*  349:     */           }
/*  350:     */         }
/*  351:     */       }
/*  352: 642 */       return retNode;
/*  353:     */     }
/*  354:     */     
/*  355:     */     public Node item(int i)
/*  356:     */     {
/*  357: 655 */       if ((getLength() == 0) || (i >= ElemLiteralResult.this.m_avts.size())) {
/*  358: 655 */         return null;
/*  359:     */       }
/*  360: 656 */       return new ElemLiteralResult.Attribute(ElemLiteralResult.this, (AVT)ElemLiteralResult.this.m_avts.get(i), ElemLiteralResult.this);
/*  361:     */     }
/*  362:     */     
/*  363:     */     public Node removeNamedItem(String name)
/*  364:     */       throws DOMException
/*  365:     */     {
/*  366: 673 */       ElemLiteralResult.this.throwDOMException((short)7, "NO_MODIFICATION_ALLOWED_ERR");
/*  367:     */       
/*  368: 675 */       return null;
/*  369:     */     }
/*  370:     */     
/*  371:     */     public Node removeNamedItemNS(String namespaceURI, String localName)
/*  372:     */       throws DOMException
/*  373:     */     {
/*  374: 692 */       ElemLiteralResult.this.throwDOMException((short)7, "NO_MODIFICATION_ALLOWED_ERR");
/*  375:     */       
/*  376: 694 */       return null;
/*  377:     */     }
/*  378:     */     
/*  379:     */     public Node setNamedItem(Node arg)
/*  380:     */       throws DOMException
/*  381:     */     {
/*  382: 709 */       ElemLiteralResult.this.throwDOMException((short)7, "NO_MODIFICATION_ALLOWED_ERR");
/*  383:     */       
/*  384: 711 */       return null;
/*  385:     */     }
/*  386:     */     
/*  387:     */     public Node setNamedItemNS(Node arg)
/*  388:     */       throws DOMException
/*  389:     */     {
/*  390: 726 */       ElemLiteralResult.this.throwDOMException((short)7, "NO_MODIFICATION_ALLOWED_ERR");
/*  391:     */       
/*  392: 728 */       return null;
/*  393:     */     }
/*  394:     */   }
/*  395:     */   
/*  396:     */   public class Attribute
/*  397:     */     implements Attr
/*  398:     */   {
/*  399:     */     private AVT m_attribute;
/*  400: 734 */     private Element m_owner = null;
/*  401:     */     
/*  402:     */     public Attribute(AVT avt, Element elem)
/*  403:     */     {
/*  404: 740 */       this.m_attribute = avt;
/*  405: 741 */       this.m_owner = elem;
/*  406:     */     }
/*  407:     */     
/*  408:     */     public Node appendChild(Node newChild)
/*  409:     */       throws DOMException
/*  410:     */     {
/*  411: 755 */       ElemLiteralResult.this.throwDOMException((short)7, "NO_MODIFICATION_ALLOWED_ERR");
/*  412:     */       
/*  413: 757 */       return null;
/*  414:     */     }
/*  415:     */     
/*  416:     */     public Node cloneNode(boolean deep)
/*  417:     */     {
/*  418: 770 */       return new Attribute(ElemLiteralResult.this, this.m_attribute, this.m_owner);
/*  419:     */     }
/*  420:     */     
/*  421:     */     public NamedNodeMap getAttributes()
/*  422:     */     {
/*  423: 780 */       return null;
/*  424:     */     }
/*  425:     */     
/*  426:     */     public NodeList getChildNodes()
/*  427:     */     {
/*  428: 790 */       return new ElemLiteralResult.1(this);
/*  429:     */     }
/*  430:     */     
/*  431:     */     public Node getFirstChild()
/*  432:     */     {
/*  433: 807 */       return null;
/*  434:     */     }
/*  435:     */     
/*  436:     */     public Node getLastChild()
/*  437:     */     {
/*  438: 817 */       return null;
/*  439:     */     }
/*  440:     */     
/*  441:     */     public String getLocalName()
/*  442:     */     {
/*  443: 827 */       return this.m_attribute.getName();
/*  444:     */     }
/*  445:     */     
/*  446:     */     public String getNamespaceURI()
/*  447:     */     {
/*  448: 838 */       String uri = this.m_attribute.getURI();
/*  449: 839 */       return uri.equals("") ? null : uri;
/*  450:     */     }
/*  451:     */     
/*  452:     */     public Node getNextSibling()
/*  453:     */     {
/*  454: 849 */       return null;
/*  455:     */     }
/*  456:     */     
/*  457:     */     public String getNodeName()
/*  458:     */     {
/*  459: 859 */       String uri = this.m_attribute.getURI();
/*  460: 860 */       String localName = getLocalName();
/*  461: 861 */       return uri + ":" + localName;
/*  462:     */     }
/*  463:     */     
/*  464:     */     public short getNodeType()
/*  465:     */     {
/*  466: 871 */       return 2;
/*  467:     */     }
/*  468:     */     
/*  469:     */     public String getNodeValue()
/*  470:     */       throws DOMException
/*  471:     */     {
/*  472: 883 */       return this.m_attribute.getSimpleString();
/*  473:     */     }
/*  474:     */     
/*  475:     */     public Document getOwnerDocument()
/*  476:     */     {
/*  477: 893 */       return this.m_owner.getOwnerDocument();
/*  478:     */     }
/*  479:     */     
/*  480:     */     public Node getParentNode()
/*  481:     */     {
/*  482: 903 */       return this.m_owner;
/*  483:     */     }
/*  484:     */     
/*  485:     */     public String getPrefix()
/*  486:     */     {
/*  487: 914 */       String uri = this.m_attribute.getURI();
/*  488: 915 */       String rawName = this.m_attribute.getRawName();
/*  489: 916 */       return uri.equals("") ? null : rawName.substring(0, rawName.indexOf(":"));
/*  490:     */     }
/*  491:     */     
/*  492:     */     public Node getPreviousSibling()
/*  493:     */     {
/*  494: 927 */       return null;
/*  495:     */     }
/*  496:     */     
/*  497:     */     public boolean hasAttributes()
/*  498:     */     {
/*  499: 937 */       return false;
/*  500:     */     }
/*  501:     */     
/*  502:     */     public boolean hasChildNodes()
/*  503:     */     {
/*  504: 947 */       return false;
/*  505:     */     }
/*  506:     */     
/*  507:     */     public Node insertBefore(Node newChild, Node refChild)
/*  508:     */       throws DOMException
/*  509:     */     {
/*  510: 963 */       ElemLiteralResult.this.throwDOMException((short)7, "NO_MODIFICATION_ALLOWED_ERR");
/*  511:     */       
/*  512: 965 */       return null;
/*  513:     */     }
/*  514:     */     
/*  515:     */     public boolean isSupported(String feature, String version)
/*  516:     */     {
/*  517: 976 */       return false;
/*  518:     */     }
/*  519:     */     
/*  520:     */     public void normalize() {}
/*  521:     */     
/*  522:     */     public Node removeChild(Node oldChild)
/*  523:     */       throws DOMException
/*  524:     */     {
/*  525: 993 */       ElemLiteralResult.this.throwDOMException((short)7, "NO_MODIFICATION_ALLOWED_ERR");
/*  526:     */       
/*  527: 995 */       return null;
/*  528:     */     }
/*  529:     */     
/*  530:     */     public Node replaceChild(Node newChild, Node oldChild)
/*  531:     */       throws DOMException
/*  532:     */     {
/*  533:1010 */       ElemLiteralResult.this.throwDOMException((short)7, "NO_MODIFICATION_ALLOWED_ERR");
/*  534:     */       
/*  535:1012 */       return null;
/*  536:     */     }
/*  537:     */     
/*  538:     */     public void setNodeValue(String nodeValue)
/*  539:     */       throws DOMException
/*  540:     */     {
/*  541:1024 */       ElemLiteralResult.this.throwDOMException((short)7, "NO_MODIFICATION_ALLOWED_ERR");
/*  542:     */     }
/*  543:     */     
/*  544:     */     public void setPrefix(String prefix)
/*  545:     */       throws DOMException
/*  546:     */     {
/*  547:1037 */       ElemLiteralResult.this.throwDOMException((short)7, "NO_MODIFICATION_ALLOWED_ERR");
/*  548:     */     }
/*  549:     */     
/*  550:     */     public String getName()
/*  551:     */     {
/*  552:1046 */       return this.m_attribute.getName();
/*  553:     */     }
/*  554:     */     
/*  555:     */     public String getValue()
/*  556:     */     {
/*  557:1054 */       return this.m_attribute.getSimpleString();
/*  558:     */     }
/*  559:     */     
/*  560:     */     public Element getOwnerElement()
/*  561:     */     {
/*  562:1063 */       return this.m_owner;
/*  563:     */     }
/*  564:     */     
/*  565:     */     public boolean getSpecified()
/*  566:     */     {
/*  567:1071 */       return true;
/*  568:     */     }
/*  569:     */     
/*  570:     */     public void setValue(String value)
/*  571:     */       throws DOMException
/*  572:     */     {
/*  573:1083 */       ElemLiteralResult.this.throwDOMException((short)7, "NO_MODIFICATION_ALLOWED_ERR");
/*  574:     */     }
/*  575:     */     
/*  576:     */     public TypeInfo getSchemaTypeInfo()
/*  577:     */     {
/*  578:1087 */       return null;
/*  579:     */     }
/*  580:     */     
/*  581:     */     public boolean isId()
/*  582:     */     {
/*  583:1089 */       return false;
/*  584:     */     }
/*  585:     */     
/*  586:     */     public Object setUserData(String key, Object data, UserDataHandler handler)
/*  587:     */     {
/*  588:1094 */       return getOwnerDocument().setUserData(key, data, handler);
/*  589:     */     }
/*  590:     */     
/*  591:     */     public Object getUserData(String key)
/*  592:     */     {
/*  593:1098 */       return getOwnerDocument().getUserData(key);
/*  594:     */     }
/*  595:     */     
/*  596:     */     public Object getFeature(String feature, String version)
/*  597:     */     {
/*  598:1102 */       return isSupported(feature, version) ? this : null;
/*  599:     */     }
/*  600:     */     
/*  601:     */     public boolean isEqualNode(Node arg)
/*  602:     */     {
/*  603:1106 */       return arg == this;
/*  604:     */     }
/*  605:     */     
/*  606:     */     public String lookupNamespaceURI(String specifiedPrefix)
/*  607:     */     {
/*  608:1110 */       return null;
/*  609:     */     }
/*  610:     */     
/*  611:     */     public boolean isDefaultNamespace(String namespaceURI)
/*  612:     */     {
/*  613:1114 */       return false;
/*  614:     */     }
/*  615:     */     
/*  616:     */     public String lookupPrefix(String namespaceURI)
/*  617:     */     {
/*  618:1118 */       return null;
/*  619:     */     }
/*  620:     */     
/*  621:     */     public boolean isSameNode(Node other)
/*  622:     */     {
/*  623:1123 */       return this == other;
/*  624:     */     }
/*  625:     */     
/*  626:     */     public void setTextContent(String textContent)
/*  627:     */       throws DOMException
/*  628:     */     {
/*  629:1128 */       setNodeValue(textContent);
/*  630:     */     }
/*  631:     */     
/*  632:     */     public String getTextContent()
/*  633:     */       throws DOMException
/*  634:     */     {
/*  635:1132 */       return getNodeValue();
/*  636:     */     }
/*  637:     */     
/*  638:     */     public short compareDocumentPosition(Node other)
/*  639:     */       throws DOMException
/*  640:     */     {
/*  641:1136 */       return 0;
/*  642:     */     }
/*  643:     */     
/*  644:     */     public String getBaseURI()
/*  645:     */     {
/*  646:1140 */       return null;
/*  647:     */     }
/*  648:     */   }
/*  649:     */   
/*  650:     */   public String getExtensionElementPrefix(int i)
/*  651:     */     throws ArrayIndexOutOfBoundsException
/*  652:     */   {
/*  653:1158 */     if (null == this.m_ExtensionElementURIs) {
/*  654:1159 */       throw new ArrayIndexOutOfBoundsException();
/*  655:     */     }
/*  656:1161 */     return this.m_ExtensionElementURIs.elementAt(i);
/*  657:     */   }
/*  658:     */   
/*  659:     */   public int getExtensionElementPrefixCount()
/*  660:     */   {
/*  661:1172 */     return null != this.m_ExtensionElementURIs ? this.m_ExtensionElementURIs.size() : 0;
/*  662:     */   }
/*  663:     */   
/*  664:     */   public boolean containsExtensionElementURI(String uri)
/*  665:     */   {
/*  666:1187 */     if (null == this.m_ExtensionElementURIs) {
/*  667:1188 */       return false;
/*  668:     */     }
/*  669:1190 */     return this.m_ExtensionElementURIs.contains(uri);
/*  670:     */   }
/*  671:     */   
/*  672:     */   public int getXSLToken()
/*  673:     */   {
/*  674:1201 */     return 77;
/*  675:     */   }
/*  676:     */   
/*  677:     */   public String getNodeName()
/*  678:     */   {
/*  679:1213 */     return this.m_rawName;
/*  680:     */   }
/*  681:     */   
/*  682:     */   public void setVersion(String v)
/*  683:     */   {
/*  684:1230 */     this.m_version = v;
/*  685:     */   }
/*  686:     */   
/*  687:     */   public String getVersion()
/*  688:     */   {
/*  689:1241 */     return this.m_version;
/*  690:     */   }
/*  691:     */   
/*  692:     */   public void setExcludeResultPrefixes(StringVector v)
/*  693:     */   {
/*  694:1264 */     this.m_excludeResultPrefixes = v;
/*  695:     */   }
/*  696:     */   
/*  697:     */   private boolean excludeResultNSDecl(String prefix, String uri)
/*  698:     */     throws TransformerException
/*  699:     */   {
/*  700:1282 */     if (null != this.m_excludeResultPrefixes) {
/*  701:1284 */       return containsExcludeResultPrefix(prefix, uri);
/*  702:     */     }
/*  703:1287 */     return false;
/*  704:     */   }
/*  705:     */   
/*  706:     */   public void execute(TransformerImpl transformer)
/*  707:     */     throws TransformerException
/*  708:     */   {
/*  709:1303 */     SerializationHandler rhandler = transformer.getSerializationHandler();
/*  710:     */     try
/*  711:     */     {
/*  712:1307 */       if (transformer.getDebug())
/*  713:     */       {
/*  714:1310 */         rhandler.flushPending();
/*  715:1311 */         transformer.getTraceManager().fireTraceEvent(this);
/*  716:     */       }
/*  717:1317 */       rhandler.startPrefixMapping(getPrefix(), getNamespace());
/*  718:     */       
/*  719:     */ 
/*  720:1320 */       executeNSDecls(transformer);
/*  721:1321 */       rhandler.startElement(getNamespace(), getLocalName(), getRawName());
/*  722:     */     }
/*  723:     */     catch (SAXException se)
/*  724:     */     {
/*  725:1325 */       throw new TransformerException(se);
/*  726:     */     }
/*  727:1335 */     TransformerException tException = null;
/*  728:     */     try
/*  729:     */     {
/*  730:1340 */       super.execute(transformer);
/*  731:1344 */       if (null != this.m_avts)
/*  732:     */       {
/*  733:1346 */         int nAttrs = this.m_avts.size();
/*  734:1348 */         for (int i = nAttrs - 1; i >= 0; i--)
/*  735:     */         {
/*  736:1350 */           AVT avt = (AVT)this.m_avts.get(i);
/*  737:1351 */           XPathContext xctxt = transformer.getXPathContext();
/*  738:1352 */           int sourceNode = xctxt.getCurrentNode();
/*  739:1353 */           String stringedValue = avt.evaluate(xctxt, sourceNode, this);
/*  740:1356 */           if (null != stringedValue) {
/*  741:1364 */             rhandler.addAttribute(avt.getURI(), avt.getName(), avt.getRawName(), "CDATA", stringedValue, false);
/*  742:     */           }
/*  743:     */         }
/*  744:     */       }
/*  745:1376 */       transformer.executeChildTemplates(this, true);
/*  746:     */     }
/*  747:     */     catch (TransformerException te)
/*  748:     */     {
/*  749:1381 */       tException = te;
/*  750:     */     }
/*  751:     */     catch (SAXException se)
/*  752:     */     {
/*  753:1385 */       tException = new TransformerException(se);
/*  754:     */     }
/*  755:     */     try
/*  756:     */     {
/*  757:1395 */       if (transformer.getDebug()) {
/*  758:1399 */         transformer.getTraceManager().fireTraceEndEvent(this);
/*  759:     */       }
/*  760:1401 */       rhandler.endElement(getNamespace(), getLocalName(), getRawName());
/*  761:     */     }
/*  762:     */     catch (SAXException se)
/*  763:     */     {
/*  764:1409 */       if (tException != null) {
/*  765:1410 */         throw tException;
/*  766:     */       }
/*  767:1412 */       throw new TransformerException(se);
/*  768:     */     }
/*  769:1418 */     if (tException != null) {
/*  770:1419 */       throw tException;
/*  771:     */     }
/*  772:1421 */     unexecuteNSDecls(transformer);
/*  773:     */     try
/*  774:     */     {
/*  775:1426 */       rhandler.endPrefixMapping(getPrefix());
/*  776:     */     }
/*  777:     */     catch (SAXException se)
/*  778:     */     {
/*  779:1430 */       throw new TransformerException(se);
/*  780:     */     }
/*  781:     */   }
/*  782:     */   
/*  783:     */   public Iterator enumerateLiteralResultAttributes()
/*  784:     */   {
/*  785:1443 */     return null == this.m_avts ? null : this.m_avts.iterator();
/*  786:     */   }
/*  787:     */   
/*  788:     */   protected boolean accept(XSLTVisitor visitor)
/*  789:     */   {
/*  790:1455 */     return visitor.visitLiteralResultElement(this);
/*  791:     */   }
/*  792:     */   
/*  793:     */   protected void callChildVisitors(XSLTVisitor visitor, boolean callAttrs)
/*  794:     */   {
/*  795:1464 */     if ((callAttrs) && (null != this.m_avts))
/*  796:     */     {
/*  797:1466 */       int nAttrs = this.m_avts.size();
/*  798:1468 */       for (int i = nAttrs - 1; i >= 0; i--)
/*  799:     */       {
/*  800:1470 */         AVT avt = (AVT)this.m_avts.get(i);
/*  801:1471 */         avt.callVisitors(visitor);
/*  802:     */       }
/*  803:     */     }
/*  804:1474 */     super.callChildVisitors(visitor, callAttrs);
/*  805:     */   }
/*  806:     */   
/*  807:     */   public void throwDOMException(short code, String msg)
/*  808:     */   {
/*  809:1485 */     String themsg = XSLMessages.createMessage(msg, null);
/*  810:     */     
/*  811:1487 */     throw new DOMException(code, themsg);
/*  812:     */   }
/*  813:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.templates.ElemLiteralResult
 * JD-Core Version:    0.7.0.1
 */