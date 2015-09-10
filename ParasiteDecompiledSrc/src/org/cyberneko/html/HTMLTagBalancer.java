/*    1:     */ package org.cyberneko.html;
/*    2:     */ 
/*    3:     */ import java.util.ArrayList;
/*    4:     */ import java.util.List;
/*    5:     */ import org.apache.xerces.util.XMLAttributesImpl;
/*    6:     */ import org.apache.xerces.xni.Augmentations;
/*    7:     */ import org.apache.xerces.xni.NamespaceContext;
/*    8:     */ import org.apache.xerces.xni.QName;
/*    9:     */ import org.apache.xerces.xni.XMLAttributes;
/*   10:     */ import org.apache.xerces.xni.XMLDocumentHandler;
/*   11:     */ import org.apache.xerces.xni.XMLLocator;
/*   12:     */ import org.apache.xerces.xni.XMLResourceIdentifier;
/*   13:     */ import org.apache.xerces.xni.XMLString;
/*   14:     */ import org.apache.xerces.xni.XNIException;
/*   15:     */ import org.apache.xerces.xni.parser.XMLComponentManager;
/*   16:     */ import org.apache.xerces.xni.parser.XMLConfigurationException;
/*   17:     */ import org.apache.xerces.xni.parser.XMLDocumentFilter;
/*   18:     */ import org.apache.xerces.xni.parser.XMLDocumentSource;
/*   19:     */ import org.cyberneko.html.xercesbridge.XercesBridge;
/*   20:     */ 
/*   21:     */ public class HTMLTagBalancer
/*   22:     */   implements XMLDocumentFilter, HTMLComponent
/*   23:     */ {
/*   24:     */   protected static final String NAMESPACES = "http://xml.org/sax/features/namespaces";
/*   25:     */   protected static final String AUGMENTATIONS = "http://cyberneko.org/html/features/augmentations";
/*   26:     */   protected static final String REPORT_ERRORS = "http://cyberneko.org/html/features/report-errors";
/*   27:     */   protected static final String DOCUMENT_FRAGMENT_DEPRECATED = "http://cyberneko.org/html/features/document-fragment";
/*   28:     */   protected static final String DOCUMENT_FRAGMENT = "http://cyberneko.org/html/features/balance-tags/document-fragment";
/*   29:     */   protected static final String IGNORE_OUTSIDE_CONTENT = "http://cyberneko.org/html/features/balance-tags/ignore-outside-content";
/*   30: 101 */   private static final String[] RECOGNIZED_FEATURES = { "http://xml.org/sax/features/namespaces", "http://cyberneko.org/html/features/augmentations", "http://cyberneko.org/html/features/report-errors", "http://cyberneko.org/html/features/document-fragment", "http://cyberneko.org/html/features/balance-tags/document-fragment", "http://cyberneko.org/html/features/balance-tags/ignore-outside-content" };
/*   31: 111 */   private static final Boolean[] RECOGNIZED_FEATURES_DEFAULTS = { null, null, null, null, Boolean.FALSE, Boolean.FALSE };
/*   32:     */   protected static final String NAMES_ELEMS = "http://cyberneko.org/html/properties/names/elems";
/*   33:     */   protected static final String NAMES_ATTRS = "http://cyberneko.org/html/properties/names/attrs";
/*   34:     */   protected static final String ERROR_REPORTER = "http://cyberneko.org/html/properties/error-reporter";
/*   35:     */   public static final String FRAGMENT_CONTEXT_STACK = "http://cyberneko.org/html/properties/balance-tags/fragment-context-stack";
/*   36: 138 */   private static final String[] RECOGNIZED_PROPERTIES = { "http://cyberneko.org/html/properties/names/elems", "http://cyberneko.org/html/properties/names/attrs", "http://cyberneko.org/html/properties/error-reporter", "http://cyberneko.org/html/properties/balance-tags/fragment-context-stack" };
/*   37: 146 */   private static final Object[] RECOGNIZED_PROPERTIES_DEFAULTS = { null, null, null, null };
/*   38:     */   protected static final short NAMES_NO_CHANGE = 0;
/*   39:     */   protected static final short NAMES_MATCH = 0;
/*   40:     */   protected static final short NAMES_UPPERCASE = 1;
/*   41:     */   protected static final short NAMES_LOWERCASE = 2;
/*   42: 170 */   protected static final HTMLEventInfo SYNTHESIZED_ITEM = new HTMLEventInfo.SynthesizedItem();
/*   43:     */   protected boolean fNamespaces;
/*   44:     */   protected boolean fAugmentations;
/*   45:     */   protected boolean fReportErrors;
/*   46:     */   protected boolean fDocumentFragment;
/*   47:     */   protected boolean fIgnoreOutsideContent;
/*   48:     */   protected boolean fAllowSelfclosingIframe;
/*   49:     */   protected short fNamesElems;
/*   50:     */   protected short fNamesAttrs;
/*   51:     */   protected HTMLErrorReporter fErrorReporter;
/*   52:     */   protected XMLDocumentSource fDocumentSource;
/*   53:     */   protected XMLDocumentHandler fDocumentHandler;
/*   54:     */   protected final InfoStack fElementStack;
/*   55:     */   protected final InfoStack fInlineStack;
/*   56:     */   protected boolean fSeenAnything;
/*   57:     */   protected boolean fSeenDoctype;
/*   58:     */   protected boolean fSeenRootElement;
/*   59:     */   protected boolean fSeenRootElementEnd;
/*   60:     */   protected boolean fSeenHeadElement;
/*   61:     */   protected boolean fSeenBodyElement;
/*   62:     */   protected boolean fOpenedForm;
/*   63:     */   private final QName fQName;
/*   64:     */   private final XMLAttributes fEmptyAttrs;
/*   65:     */   private final HTMLAugmentations fInfosetAugs;
/*   66:     */   protected HTMLTagBalancingListener tagBalancingListener;
/*   67:     */   private LostText lostText_;
/*   68:     */   private boolean forcedStartElement_;
/*   69:     */   private boolean forcedEndElement_;
/*   70:     */   private QName[] fragmentContextStack_;
/*   71:     */   private int fragmentContextStackSize_;
/*   72:     */   private List endElementsBuffer_;
/*   73:     */   
/*   74:     */   public HTMLTagBalancer()
/*   75:     */   {
/*   76: 219 */     this.fElementStack = new InfoStack();
/*   77:     */     
/*   78:     */ 
/*   79: 222 */     this.fInlineStack = new InfoStack();
/*   80:     */     
/*   81:     */ 
/*   82:     */ 
/*   83:     */ 
/*   84:     */ 
/*   85:     */ 
/*   86:     */ 
/*   87:     */ 
/*   88:     */ 
/*   89:     */ 
/*   90:     */ 
/*   91:     */ 
/*   92:     */ 
/*   93:     */ 
/*   94:     */ 
/*   95:     */ 
/*   96:     */ 
/*   97:     */ 
/*   98:     */ 
/*   99:     */ 
/*  100:     */ 
/*  101:     */ 
/*  102:     */ 
/*  103:     */ 
/*  104:     */ 
/*  105:     */ 
/*  106:     */ 
/*  107:     */ 
/*  108:     */ 
/*  109:     */ 
/*  110:     */ 
/*  111: 254 */     this.fQName = new QName();
/*  112:     */     
/*  113:     */ 
/*  114: 257 */     this.fEmptyAttrs = new XMLAttributesImpl();
/*  115:     */     
/*  116:     */ 
/*  117: 260 */     this.fInfosetAugs = new HTMLAugmentations();
/*  118:     */     
/*  119:     */ 
/*  120: 263 */     this.lostText_ = new LostText();
/*  121:     */     
/*  122: 265 */     this.forcedStartElement_ = false;
/*  123: 266 */     this.forcedEndElement_ = false;
/*  124:     */     
/*  125:     */ 
/*  126:     */ 
/*  127:     */ 
/*  128: 271 */     this.fragmentContextStack_ = null;
/*  129: 272 */     this.fragmentContextStackSize_ = 0;
/*  130:     */     
/*  131: 274 */     this.endElementsBuffer_ = new ArrayList();
/*  132:     */   }
/*  133:     */   
/*  134:     */   public Boolean getFeatureDefault(String featureId)
/*  135:     */   {
/*  136: 282 */     int length = RECOGNIZED_FEATURES != null ? RECOGNIZED_FEATURES.length : 0;
/*  137: 283 */     for (int i = 0; i < length; i++) {
/*  138: 284 */       if (RECOGNIZED_FEATURES[i].equals(featureId)) {
/*  139: 285 */         return RECOGNIZED_FEATURES_DEFAULTS[i];
/*  140:     */       }
/*  141:     */     }
/*  142: 288 */     return null;
/*  143:     */   }
/*  144:     */   
/*  145:     */   public Object getPropertyDefault(String propertyId)
/*  146:     */   {
/*  147: 293 */     int length = RECOGNIZED_PROPERTIES != null ? RECOGNIZED_PROPERTIES.length : 0;
/*  148: 294 */     for (int i = 0; i < length; i++) {
/*  149: 295 */       if (RECOGNIZED_PROPERTIES[i].equals(propertyId)) {
/*  150: 296 */         return RECOGNIZED_PROPERTIES_DEFAULTS[i];
/*  151:     */       }
/*  152:     */     }
/*  153: 299 */     return null;
/*  154:     */   }
/*  155:     */   
/*  156:     */   public String[] getRecognizedFeatures()
/*  157:     */   {
/*  158: 308 */     return RECOGNIZED_FEATURES;
/*  159:     */   }
/*  160:     */   
/*  161:     */   public String[] getRecognizedProperties()
/*  162:     */   {
/*  163: 313 */     return RECOGNIZED_PROPERTIES;
/*  164:     */   }
/*  165:     */   
/*  166:     */   public void reset(XMLComponentManager manager)
/*  167:     */     throws XMLConfigurationException
/*  168:     */   {
/*  169: 321 */     this.fNamespaces = manager.getFeature("http://xml.org/sax/features/namespaces");
/*  170: 322 */     this.fAugmentations = manager.getFeature("http://cyberneko.org/html/features/augmentations");
/*  171: 323 */     this.fReportErrors = manager.getFeature("http://cyberneko.org/html/features/report-errors");
/*  172: 324 */     this.fDocumentFragment = ((manager.getFeature("http://cyberneko.org/html/features/balance-tags/document-fragment")) || (manager.getFeature("http://cyberneko.org/html/features/document-fragment")));
/*  173:     */     
/*  174: 326 */     this.fIgnoreOutsideContent = manager.getFeature("http://cyberneko.org/html/features/balance-tags/ignore-outside-content");
/*  175: 327 */     this.fAllowSelfclosingIframe = manager.getFeature("http://cyberneko.org/html/features/scanner/allow-selfclosing-iframe");
/*  176:     */     
/*  177:     */ 
/*  178: 330 */     this.fNamesElems = getNamesValue(String.valueOf(manager.getProperty("http://cyberneko.org/html/properties/names/elems")));
/*  179: 331 */     this.fNamesAttrs = getNamesValue(String.valueOf(manager.getProperty("http://cyberneko.org/html/properties/names/attrs")));
/*  180: 332 */     this.fErrorReporter = ((HTMLErrorReporter)manager.getProperty("http://cyberneko.org/html/properties/error-reporter"));
/*  181:     */     
/*  182: 334 */     this.fragmentContextStack_ = ((QName[])manager.getProperty("http://cyberneko.org/html/properties/balance-tags/fragment-context-stack"));
/*  183:     */   }
/*  184:     */   
/*  185:     */   public void setFeature(String featureId, boolean state)
/*  186:     */     throws XMLConfigurationException
/*  187:     */   {
/*  188: 342 */     if (featureId.equals("http://cyberneko.org/html/features/augmentations"))
/*  189:     */     {
/*  190: 343 */       this.fAugmentations = state;
/*  191: 344 */       return;
/*  192:     */     }
/*  193: 346 */     if (featureId.equals("http://cyberneko.org/html/features/report-errors"))
/*  194:     */     {
/*  195: 347 */       this.fReportErrors = state;
/*  196: 348 */       return;
/*  197:     */     }
/*  198: 350 */     if (featureId.equals("http://cyberneko.org/html/features/balance-tags/ignore-outside-content"))
/*  199:     */     {
/*  200: 351 */       this.fIgnoreOutsideContent = state;
/*  201: 352 */       return;
/*  202:     */     }
/*  203:     */   }
/*  204:     */   
/*  205:     */   public void setProperty(String propertyId, Object value)
/*  206:     */     throws XMLConfigurationException
/*  207:     */   {
/*  208: 361 */     if (propertyId.equals("http://cyberneko.org/html/properties/names/elems"))
/*  209:     */     {
/*  210: 362 */       this.fNamesElems = getNamesValue(String.valueOf(value));
/*  211: 363 */       return;
/*  212:     */     }
/*  213: 366 */     if (propertyId.equals("http://cyberneko.org/html/properties/names/attrs"))
/*  214:     */     {
/*  215: 367 */       this.fNamesAttrs = getNamesValue(String.valueOf(value));
/*  216: 368 */       return;
/*  217:     */     }
/*  218:     */   }
/*  219:     */   
/*  220:     */   public void setDocumentHandler(XMLDocumentHandler handler)
/*  221:     */   {
/*  222: 379 */     this.fDocumentHandler = handler;
/*  223:     */   }
/*  224:     */   
/*  225:     */   public XMLDocumentHandler getDocumentHandler()
/*  226:     */   {
/*  227: 386 */     return this.fDocumentHandler;
/*  228:     */   }
/*  229:     */   
/*  230:     */   public void startDocument(XMLLocator locator, String encoding, NamespaceContext nscontext, Augmentations augs)
/*  231:     */     throws XNIException
/*  232:     */   {
/*  233: 401 */     this.fElementStack.top = 0;
/*  234: 402 */     if (this.fragmentContextStack_ != null)
/*  235:     */     {
/*  236: 403 */       this.fragmentContextStackSize_ = this.fragmentContextStack_.length;
/*  237: 404 */       for (int i = 0; i < this.fragmentContextStack_.length; i++)
/*  238:     */       {
/*  239: 405 */         QName name = this.fragmentContextStack_[i];
/*  240: 406 */         HTMLElements.Element elt = HTMLElements.getElement(name.localpart);
/*  241: 407 */         this.fElementStack.push(new Info(elt, name));
/*  242:     */       }
/*  243:     */     }
/*  244:     */     else
/*  245:     */     {
/*  246: 412 */       this.fragmentContextStackSize_ = 0;
/*  247:     */     }
/*  248: 414 */     this.fSeenAnything = false;
/*  249: 415 */     this.fSeenDoctype = false;
/*  250: 416 */     this.fSeenRootElement = false;
/*  251: 417 */     this.fSeenRootElementEnd = false;
/*  252: 418 */     this.fSeenHeadElement = false;
/*  253: 419 */     this.fSeenBodyElement = false;
/*  254: 423 */     if (this.fDocumentHandler != null) {
/*  255: 424 */       XercesBridge.getInstance().XMLDocumentHandler_startDocument(this.fDocumentHandler, locator, encoding, nscontext, augs);
/*  256:     */     }
/*  257:     */   }
/*  258:     */   
/*  259:     */   public void xmlDecl(String version, String encoding, String standalone, Augmentations augs)
/*  260:     */     throws XNIException
/*  261:     */   {
/*  262: 434 */     if ((!this.fSeenAnything) && (this.fDocumentHandler != null)) {
/*  263: 435 */       this.fDocumentHandler.xmlDecl(version, encoding, standalone, augs);
/*  264:     */     }
/*  265:     */   }
/*  266:     */   
/*  267:     */   public void doctypeDecl(String rootElementName, String publicId, String systemId, Augmentations augs)
/*  268:     */     throws XNIException
/*  269:     */   {
/*  270: 442 */     this.fSeenAnything = true;
/*  271: 443 */     if (this.fReportErrors) {
/*  272: 444 */       if (this.fSeenRootElement) {
/*  273: 445 */         this.fErrorReporter.reportError("HTML2010", null);
/*  274: 447 */       } else if (this.fSeenDoctype) {
/*  275: 448 */         this.fErrorReporter.reportError("HTML2011", null);
/*  276:     */       }
/*  277:     */     }
/*  278: 451 */     if ((!this.fSeenRootElement) && (!this.fSeenDoctype))
/*  279:     */     {
/*  280: 452 */       this.fSeenDoctype = true;
/*  281: 453 */       if (this.fDocumentHandler != null) {
/*  282: 454 */         this.fDocumentHandler.doctypeDecl(rootElementName, publicId, systemId, augs);
/*  283:     */       }
/*  284:     */     }
/*  285:     */   }
/*  286:     */   
/*  287:     */   public void endDocument(Augmentations augs)
/*  288:     */     throws XNIException
/*  289:     */   {
/*  290: 463 */     this.fIgnoreOutsideContent = true;
/*  291: 464 */     consumeBufferedEndElements();
/*  292: 467 */     if ((!this.fSeenRootElement) && (!this.fDocumentFragment))
/*  293:     */     {
/*  294: 468 */       if (this.fReportErrors) {
/*  295: 469 */         this.fErrorReporter.reportError("HTML2000", null);
/*  296:     */       }
/*  297: 471 */       if (this.fDocumentHandler != null)
/*  298:     */       {
/*  299: 472 */         this.fSeenRootElementEnd = false;
/*  300: 473 */         forceStartBody();
/*  301: 474 */         String body = modifyName("body", this.fNamesElems);
/*  302: 475 */         this.fQName.setValues(null, body, body, null);
/*  303: 476 */         callEndElement(this.fQName, synthesizedAugs());
/*  304:     */         
/*  305: 478 */         String ename = modifyName("html", this.fNamesElems);
/*  306: 479 */         this.fQName.setValues(null, ename, ename, null);
/*  307: 480 */         callEndElement(this.fQName, synthesizedAugs());
/*  308:     */       }
/*  309:     */     }
/*  310:     */     else
/*  311:     */     {
/*  312: 486 */       int length = this.fElementStack.top - this.fragmentContextStackSize_;
/*  313: 487 */       for (int i = 0; i < length; i++)
/*  314:     */       {
/*  315: 488 */         Info info = this.fElementStack.pop();
/*  316: 489 */         if (this.fReportErrors)
/*  317:     */         {
/*  318: 490 */           String ename = info.qname.rawname;
/*  319: 491 */           this.fErrorReporter.reportWarning("HTML2001", new Object[] { ename });
/*  320:     */         }
/*  321: 493 */         if (this.fDocumentHandler != null) {
/*  322: 494 */           callEndElement(info.qname, synthesizedAugs());
/*  323:     */         }
/*  324:     */       }
/*  325:     */     }
/*  326: 500 */     if (this.fDocumentHandler != null) {
/*  327: 501 */       this.fDocumentHandler.endDocument(augs);
/*  328:     */     }
/*  329:     */   }
/*  330:     */   
/*  331:     */   private void consumeBufferedEndElements()
/*  332:     */   {
/*  333: 511 */     List toConsume = new ArrayList(this.endElementsBuffer_);
/*  334: 512 */     this.endElementsBuffer_.clear();
/*  335: 513 */     for (int i = 0; i < toConsume.size(); i++)
/*  336:     */     {
/*  337: 514 */       ElementEntry entry = (ElementEntry)toConsume.get(i);
/*  338: 515 */       this.forcedEndElement_ = true;
/*  339: 516 */       endElement(entry.name_, entry.augs_);
/*  340:     */     }
/*  341: 518 */     this.endElementsBuffer_.clear();
/*  342:     */   }
/*  343:     */   
/*  344:     */   public void comment(XMLString text, Augmentations augs)
/*  345:     */     throws XNIException
/*  346:     */   {
/*  347: 523 */     this.fSeenAnything = true;
/*  348: 524 */     consumeEarlyTextIfNeeded();
/*  349: 525 */     if (this.fDocumentHandler != null) {
/*  350: 526 */       this.fDocumentHandler.comment(text, augs);
/*  351:     */     }
/*  352:     */   }
/*  353:     */   
/*  354:     */   private void consumeEarlyTextIfNeeded()
/*  355:     */   {
/*  356: 531 */     if (!this.lostText_.isEmpty())
/*  357:     */     {
/*  358: 532 */       if (!this.fSeenBodyElement) {
/*  359: 533 */         forceStartBody();
/*  360:     */       }
/*  361: 535 */       this.lostText_.refeed(this);
/*  362:     */     }
/*  363:     */   }
/*  364:     */   
/*  365:     */   public void processingInstruction(String target, XMLString data, Augmentations augs)
/*  366:     */     throws XNIException
/*  367:     */   {
/*  368: 542 */     this.fSeenAnything = true;
/*  369: 543 */     consumeEarlyTextIfNeeded();
/*  370: 544 */     if (this.fDocumentHandler != null) {
/*  371: 545 */       this.fDocumentHandler.processingInstruction(target, data, augs);
/*  372:     */     }
/*  373:     */   }
/*  374:     */   
/*  375:     */   public void startElement(QName elem, XMLAttributes attrs, Augmentations augs)
/*  376:     */     throws XNIException
/*  377:     */   {
/*  378: 552 */     this.fSeenAnything = true;
/*  379:     */     
/*  380: 554 */     boolean isForcedCreation = this.forcedStartElement_;
/*  381: 555 */     this.forcedStartElement_ = false;
/*  382: 558 */     if (this.fSeenRootElementEnd)
/*  383:     */     {
/*  384: 559 */       notifyDiscardedStartElement(elem, attrs, augs);
/*  385: 560 */       return;
/*  386:     */     }
/*  387: 564 */     HTMLElements.Element element = getElement(elem);
/*  388: 565 */     short elementCode = element.code;
/*  389: 568 */     if ((isForcedCreation) && ((elementCode == 101) || (elementCode == 91))) {
/*  390: 569 */       return;
/*  391:     */     }
/*  392: 573 */     if ((this.fSeenRootElement) && (elementCode == 46))
/*  393:     */     {
/*  394: 574 */       notifyDiscardedStartElement(elem, attrs, augs);
/*  395: 575 */       return;
/*  396:     */     }
/*  397: 577 */     if (elementCode == 44)
/*  398:     */     {
/*  399: 578 */       if (this.fSeenHeadElement)
/*  400:     */       {
/*  401: 579 */         notifyDiscardedStartElement(elem, attrs, augs);
/*  402: 580 */         return;
/*  403:     */       }
/*  404: 582 */       this.fSeenHeadElement = true;
/*  405:     */     }
/*  406: 584 */     else if (elementCode == 37)
/*  407:     */     {
/*  408: 585 */       consumeBufferedEndElements();
/*  409:     */     }
/*  410: 587 */     else if (elementCode == 14)
/*  411:     */     {
/*  412: 589 */       if (!this.fSeenHeadElement)
/*  413:     */       {
/*  414: 590 */         QName head = createQName("head");
/*  415: 591 */         forceStartElement(head, null, synthesizedAugs());
/*  416: 592 */         endElement(head, synthesizedAugs());
/*  417:     */       }
/*  418: 594 */       consumeBufferedEndElements();
/*  419: 596 */       if (this.fSeenBodyElement)
/*  420:     */       {
/*  421: 597 */         notifyDiscardedStartElement(elem, attrs, augs);
/*  422: 598 */         return;
/*  423:     */       }
/*  424: 600 */       this.fSeenBodyElement = true;
/*  425:     */     }
/*  426: 602 */     else if (elementCode == 35)
/*  427:     */     {
/*  428: 603 */       if (this.fOpenedForm)
/*  429:     */       {
/*  430: 604 */         notifyDiscardedStartElement(elem, attrs, augs);
/*  431: 605 */         return;
/*  432:     */       }
/*  433: 607 */       this.fOpenedForm = true;
/*  434:     */     }
/*  435: 609 */     else if (elementCode == 117)
/*  436:     */     {
/*  437: 610 */       consumeBufferedEndElements();
/*  438:     */     }
/*  439: 614 */     if (element.parent != null)
/*  440:     */     {
/*  441: 615 */       HTMLElements.Element preferedParent = element.parent[0];
/*  442: 616 */       if ((!this.fDocumentFragment) || ((preferedParent.code != 44) && (preferedParent.code != 14))) {
/*  443: 619 */         if ((!this.fSeenRootElement) && (!this.fDocumentFragment))
/*  444:     */         {
/*  445: 620 */           String pname = preferedParent.name;
/*  446: 621 */           pname = modifyName(pname, this.fNamesElems);
/*  447: 622 */           if (this.fReportErrors)
/*  448:     */           {
/*  449: 623 */             String ename = elem.rawname;
/*  450: 624 */             this.fErrorReporter.reportWarning("HTML2002", new Object[] { ename, pname });
/*  451:     */           }
/*  452: 626 */           QName qname = new QName(null, pname, pname, null);
/*  453: 627 */           boolean parentCreated = forceStartElement(qname, null, synthesizedAugs());
/*  454: 628 */           if (!parentCreated)
/*  455:     */           {
/*  456: 629 */             if (!isForcedCreation) {
/*  457: 630 */               notifyDiscardedStartElement(elem, attrs, augs);
/*  458:     */             }
/*  459: 632 */             return;
/*  460:     */           }
/*  461:     */         }
/*  462: 636 */         else if ((preferedParent.code != 44) || ((!this.fSeenBodyElement) && (!this.fDocumentFragment)))
/*  463:     */         {
/*  464: 637 */           int depth = getParentDepth(element.parent, element.bounds);
/*  465: 638 */           if (depth == -1)
/*  466:     */           {
/*  467: 639 */             String pname = modifyName(preferedParent.name, this.fNamesElems);
/*  468: 640 */             QName qname = new QName(null, pname, pname, null);
/*  469: 641 */             if (this.fReportErrors)
/*  470:     */             {
/*  471: 642 */               String ename = elem.rawname;
/*  472: 643 */               this.fErrorReporter.reportWarning("HTML2004", new Object[] { ename, pname });
/*  473:     */             }
/*  474: 645 */             boolean parentCreated = forceStartElement(qname, null, synthesizedAugs());
/*  475: 646 */             if (!parentCreated)
/*  476:     */             {
/*  477: 647 */               if (!isForcedCreation) {
/*  478: 648 */                 notifyDiscardedStartElement(elem, attrs, augs);
/*  479:     */               }
/*  480: 650 */               return;
/*  481:     */             }
/*  482:     */           }
/*  483:     */         }
/*  484:     */       }
/*  485:     */     }
/*  486: 658 */     int depth = 0;
/*  487: 659 */     if (element.flags == 0)
/*  488:     */     {
/*  489: 660 */       int length = this.fElementStack.top;
/*  490: 661 */       this.fInlineStack.top = 0;
/*  491: 662 */       for (int i = length - 1; i >= 0; i--)
/*  492:     */       {
/*  493: 663 */         Info info = this.fElementStack.data[i];
/*  494: 664 */         if (!info.element.isInline()) {
/*  495:     */           break;
/*  496:     */         }
/*  497: 667 */         this.fInlineStack.push(info);
/*  498: 668 */         endElement(info.qname, synthesizedAugs());
/*  499:     */       }
/*  500: 670 */       depth = this.fInlineStack.top;
/*  501:     */     }
/*  502: 676 */     if (((this.fElementStack.top > 1) && (this.fElementStack.peek().element.code == 90)) || ((this.fElementStack.top > 2) && (this.fElementStack.data[(this.fElementStack.top - 2)].element.code == 44)))
/*  503:     */     {
/*  504: 679 */       Info info = this.fElementStack.pop();
/*  505: 680 */       if (this.fDocumentHandler != null) {
/*  506: 681 */         callEndElement(info.qname, synthesizedAugs());
/*  507:     */       }
/*  508:     */     }
/*  509: 684 */     if (element.closes != null)
/*  510:     */     {
/*  511: 685 */       int length = this.fElementStack.top;
/*  512: 686 */       for (int i = length - 1; i >= 0; i--)
/*  513:     */       {
/*  514: 687 */         Info info = this.fElementStack.data[i];
/*  515: 690 */         if (element.closes(info.element.code))
/*  516:     */         {
/*  517: 691 */           if (this.fReportErrors)
/*  518:     */           {
/*  519: 692 */             String ename = elem.rawname;
/*  520: 693 */             String iname = info.qname.rawname;
/*  521: 694 */             this.fErrorReporter.reportWarning("HTML2005", new Object[] { ename, iname });
/*  522:     */           }
/*  523: 696 */           for (int j = length - 1; j >= i; j--)
/*  524:     */           {
/*  525: 697 */             info = this.fElementStack.pop();
/*  526: 698 */             if (this.fDocumentHandler != null) {
/*  527: 700 */               callEndElement(info.qname, synthesizedAugs());
/*  528:     */             }
/*  529:     */           }
/*  530: 703 */           length = i;
/*  531:     */         }
/*  532:     */         else
/*  533:     */         {
/*  534: 708 */           if ((info.element.isBlock()) || (element.isParent(info.element))) {
/*  535:     */             break;
/*  536:     */           }
/*  537:     */         }
/*  538:     */       }
/*  539:     */     }
/*  540: 715 */     else if (elementCode == 101)
/*  541:     */     {
/*  542: 716 */       for (int i = this.fElementStack.top - 1; i >= 0; i--)
/*  543:     */       {
/*  544: 717 */         Info info = this.fElementStack.data[i];
/*  545: 718 */         if (!info.element.isInline()) {
/*  546:     */           break;
/*  547:     */         }
/*  548: 721 */         endElement(info.qname, synthesizedAugs());
/*  549:     */       }
/*  550:     */     }
/*  551: 726 */     this.fSeenRootElement = true;
/*  552: 727 */     if ((element != null) && (element.isEmpty()))
/*  553:     */     {
/*  554: 728 */       if (attrs == null) {
/*  555: 729 */         attrs = emptyAttributes();
/*  556:     */       }
/*  557: 731 */       if (this.fDocumentHandler != null) {
/*  558: 732 */         this.fDocumentHandler.emptyElement(elem, attrs, augs);
/*  559:     */       }
/*  560:     */     }
/*  561:     */     else
/*  562:     */     {
/*  563: 736 */       boolean inline = (element != null) && (element.isInline());
/*  564: 737 */       this.fElementStack.push(new Info(element, elem, inline ? attrs : null));
/*  565: 738 */       if (attrs == null) {
/*  566: 739 */         attrs = emptyAttributes();
/*  567:     */       }
/*  568: 741 */       if (this.fDocumentHandler != null) {
/*  569: 742 */         callStartElement(elem, attrs, augs);
/*  570:     */       }
/*  571:     */     }
/*  572: 747 */     for (int i = 0; i < depth; i++)
/*  573:     */     {
/*  574: 748 */       Info info = this.fInlineStack.pop();
/*  575: 749 */       forceStartElement(info.qname, info.attributes, synthesizedAugs());
/*  576:     */     }
/*  577: 752 */     if (elementCode == 14) {
/*  578: 753 */       this.lostText_.refeed(this);
/*  579:     */     }
/*  580:     */   }
/*  581:     */   
/*  582:     */   private boolean forceStartElement(QName elem, XMLAttributes attrs, Augmentations augs)
/*  583:     */     throws XNIException
/*  584:     */   {
/*  585: 765 */     this.forcedStartElement_ = true;
/*  586: 766 */     startElement(elem, attrs, augs);
/*  587:     */     
/*  588: 768 */     return (this.fElementStack.top > 0) && (elem.equals(this.fElementStack.peek().qname));
/*  589:     */   }
/*  590:     */   
/*  591:     */   private QName createQName(String tagName)
/*  592:     */   {
/*  593: 772 */     tagName = modifyName(tagName, this.fNamesElems);
/*  594: 773 */     return new QName(null, tagName, tagName, "http://www.w3.org/1999/xhtml");
/*  595:     */   }
/*  596:     */   
/*  597:     */   public void emptyElement(QName element, XMLAttributes attrs, Augmentations augs)
/*  598:     */     throws XNIException
/*  599:     */   {
/*  600: 779 */     startElement(element, attrs, augs);
/*  601:     */     
/*  602: 781 */     HTMLElements.Element elem = getElement(element);
/*  603: 782 */     if ((elem.isEmpty()) || (elem.code == 117) || ((elem.code == 48) && (this.fAllowSelfclosingIframe))) {
/*  604: 785 */       endElement(element, augs);
/*  605:     */     }
/*  606:     */   }
/*  607:     */   
/*  608:     */   public void startGeneralEntity(String name, XMLResourceIdentifier id, String encoding, Augmentations augs)
/*  609:     */     throws XNIException
/*  610:     */   {
/*  611: 794 */     this.fSeenAnything = true;
/*  612: 797 */     if (this.fSeenRootElementEnd) {
/*  613: 798 */       return;
/*  614:     */     }
/*  615: 802 */     if (!this.fDocumentFragment)
/*  616:     */     {
/*  617: 803 */       boolean insertBody = !this.fSeenRootElement;
/*  618: 804 */       if (!insertBody)
/*  619:     */       {
/*  620: 805 */         Info info = this.fElementStack.peek();
/*  621: 806 */         if ((info.element.code == 44) || (info.element.code == 46))
/*  622:     */         {
/*  623: 808 */           String hname = modifyName("head", this.fNamesElems);
/*  624: 809 */           String bname = modifyName("body", this.fNamesElems);
/*  625: 810 */           if (this.fReportErrors) {
/*  626: 811 */             this.fErrorReporter.reportWarning("HTML2009", new Object[] { hname, bname });
/*  627:     */           }
/*  628: 813 */           this.fQName.setValues(null, hname, hname, null);
/*  629: 814 */           endElement(this.fQName, synthesizedAugs());
/*  630: 815 */           insertBody = true;
/*  631:     */         }
/*  632:     */       }
/*  633: 818 */       if (insertBody) {
/*  634: 819 */         forceStartBody();
/*  635:     */       }
/*  636:     */     }
/*  637: 824 */     if (this.fDocumentHandler != null) {
/*  638: 825 */       this.fDocumentHandler.startGeneralEntity(name, id, encoding, augs);
/*  639:     */     }
/*  640:     */   }
/*  641:     */   
/*  642:     */   private void forceStartBody()
/*  643:     */   {
/*  644: 834 */     QName body = createQName("body");
/*  645: 835 */     if (this.fReportErrors) {
/*  646: 836 */       this.fErrorReporter.reportWarning("HTML2006", new Object[] { body.localpart });
/*  647:     */     }
/*  648: 838 */     forceStartElement(body, null, synthesizedAugs());
/*  649:     */   }
/*  650:     */   
/*  651:     */   public void textDecl(String version, String encoding, Augmentations augs)
/*  652:     */     throws XNIException
/*  653:     */   {
/*  654: 844 */     this.fSeenAnything = true;
/*  655: 847 */     if (this.fSeenRootElementEnd) {
/*  656: 848 */       return;
/*  657:     */     }
/*  658: 852 */     if (this.fDocumentHandler != null) {
/*  659: 853 */       this.fDocumentHandler.textDecl(version, encoding, augs);
/*  660:     */     }
/*  661:     */   }
/*  662:     */   
/*  663:     */   public void endGeneralEntity(String name, Augmentations augs)
/*  664:     */     throws XNIException
/*  665:     */   {
/*  666: 862 */     if (this.fSeenRootElementEnd) {
/*  667: 863 */       return;
/*  668:     */     }
/*  669: 867 */     if (this.fDocumentHandler != null) {
/*  670: 868 */       this.fDocumentHandler.endGeneralEntity(name, augs);
/*  671:     */     }
/*  672:     */   }
/*  673:     */   
/*  674:     */   public void startCDATA(Augmentations augs)
/*  675:     */     throws XNIException
/*  676:     */   {
/*  677: 875 */     this.fSeenAnything = true;
/*  678:     */     
/*  679: 877 */     consumeEarlyTextIfNeeded();
/*  680: 880 */     if (this.fSeenRootElementEnd) {
/*  681: 881 */       return;
/*  682:     */     }
/*  683: 885 */     if (this.fDocumentHandler != null) {
/*  684: 886 */       this.fDocumentHandler.startCDATA(augs);
/*  685:     */     }
/*  686:     */   }
/*  687:     */   
/*  688:     */   public void endCDATA(Augmentations augs)
/*  689:     */     throws XNIException
/*  690:     */   {
/*  691: 895 */     if (this.fSeenRootElementEnd) {
/*  692: 896 */       return;
/*  693:     */     }
/*  694: 900 */     if (this.fDocumentHandler != null) {
/*  695: 901 */       this.fDocumentHandler.endCDATA(augs);
/*  696:     */     }
/*  697:     */   }
/*  698:     */   
/*  699:     */   public void characters(XMLString text, Augmentations augs)
/*  700:     */     throws XNIException
/*  701:     */   {
/*  702: 909 */     if (this.fSeenRootElementEnd) {
/*  703: 910 */       return;
/*  704:     */     }
/*  705: 913 */     if ((this.fElementStack.top == 0) && (!this.fDocumentFragment))
/*  706:     */     {
/*  707: 915 */       this.lostText_.add(text, augs);
/*  708: 916 */       return;
/*  709:     */     }
/*  710: 920 */     boolean whitespace = true;
/*  711: 921 */     for (int i = 0; i < text.length; i++) {
/*  712: 922 */       if (!Character.isWhitespace(text.ch[(text.offset + i)]))
/*  713:     */       {
/*  714: 923 */         whitespace = false;
/*  715: 924 */         break;
/*  716:     */       }
/*  717:     */     }
/*  718: 928 */     if (!this.fDocumentFragment)
/*  719:     */     {
/*  720: 930 */       if (!this.fSeenRootElement)
/*  721:     */       {
/*  722: 931 */         if (whitespace) {
/*  723: 932 */           return;
/*  724:     */         }
/*  725: 934 */         forceStartBody();
/*  726:     */       }
/*  727: 937 */       if ((whitespace) && ((this.fElementStack.top < 2) || (this.endElementsBuffer_.size() == 1))) {
/*  728: 939 */         return;
/*  729:     */       }
/*  730: 946 */       if (!whitespace)
/*  731:     */       {
/*  732: 947 */         Info info = this.fElementStack.peek();
/*  733: 948 */         if ((info.element.code == 44) || (info.element.code == 46))
/*  734:     */         {
/*  735: 950 */           String hname = modifyName("head", this.fNamesElems);
/*  736: 951 */           String bname = modifyName("body", this.fNamesElems);
/*  737: 952 */           if (this.fReportErrors) {
/*  738: 953 */             this.fErrorReporter.reportWarning("HTML2009", new Object[] { hname, bname });
/*  739:     */           }
/*  740: 955 */           forceStartBody();
/*  741:     */         }
/*  742:     */       }
/*  743:     */     }
/*  744: 961 */     if (this.fDocumentHandler != null) {
/*  745: 962 */       this.fDocumentHandler.characters(text, augs);
/*  746:     */     }
/*  747:     */   }
/*  748:     */   
/*  749:     */   public void ignorableWhitespace(XMLString text, Augmentations augs)
/*  750:     */     throws XNIException
/*  751:     */   {
/*  752: 970 */     characters(text, augs);
/*  753:     */   }
/*  754:     */   
/*  755:     */   public void endElement(QName element, Augmentations augs)
/*  756:     */     throws XNIException
/*  757:     */   {
/*  758: 975 */     boolean forcedEndElement = this.forcedEndElement_;
/*  759: 977 */     if (this.fSeenRootElementEnd)
/*  760:     */     {
/*  761: 978 */       notifyDiscardedEndElement(element, augs);
/*  762: 979 */       return;
/*  763:     */     }
/*  764: 983 */     HTMLElements.Element elem = getElement(element);
/*  765: 986 */     if ((!this.fIgnoreOutsideContent) && ((elem.code == 14) || (elem.code == 46)))
/*  766:     */     {
/*  767: 988 */       this.endElementsBuffer_.add(new ElementEntry(element, augs));
/*  768: 989 */       return;
/*  769:     */     }
/*  770: 993 */     if (elem.code == 46)
/*  771:     */     {
/*  772: 994 */       this.fSeenRootElementEnd = true;
/*  773:     */     }
/*  774: 996 */     else if (elem.code == 35)
/*  775:     */     {
/*  776: 997 */       this.fOpenedForm = false;
/*  777:     */     }
/*  778: 999 */     else if ((elem.code == 44) && (!forcedEndElement))
/*  779:     */     {
/*  780:1001 */       this.endElementsBuffer_.add(new ElementEntry(element, augs));
/*  781:1002 */       return;
/*  782:     */     }
/*  783:1006 */     int depth = getElementDepth(elem);
/*  784:1007 */     if (depth == -1)
/*  785:     */     {
/*  786:1008 */       if (elem.code == 77)
/*  787:     */       {
/*  788:1009 */         forceStartElement(element, emptyAttributes(), synthesizedAugs());
/*  789:1010 */         endElement(element, augs);
/*  790:     */       }
/*  791:1012 */       else if (!elem.isEmpty())
/*  792:     */       {
/*  793:1013 */         notifyDiscardedEndElement(element, augs);
/*  794:     */       }
/*  795:1015 */       return;
/*  796:     */     }
/*  797:1019 */     if ((depth > 1) && (elem.isInline()))
/*  798:     */     {
/*  799:1020 */       int size = this.fElementStack.top;
/*  800:1021 */       this.fInlineStack.top = 0;
/*  801:1022 */       for (int i = 0; i < depth - 1; i++)
/*  802:     */       {
/*  803:1023 */         Info info = this.fElementStack.data[(size - i - 1)];
/*  804:1024 */         HTMLElements.Element pelem = info.element;
/*  805:1025 */         if ((pelem.isInline()) || (pelem.code == 34)) {
/*  806:1029 */           this.fInlineStack.push(info);
/*  807:     */         }
/*  808:     */       }
/*  809:     */     }
/*  810:1035 */     for (int i = 0; i < depth; i++)
/*  811:     */     {
/*  812:1036 */       Info info = this.fElementStack.pop();
/*  813:1037 */       if ((this.fReportErrors) && (i < depth - 1))
/*  814:     */       {
/*  815:1038 */         String ename = modifyName(element.rawname, this.fNamesElems);
/*  816:1039 */         String iname = info.qname.rawname;
/*  817:1040 */         this.fErrorReporter.reportWarning("HTML2007", new Object[] { ename, iname });
/*  818:     */       }
/*  819:1042 */       if (this.fDocumentHandler != null) {
/*  820:1044 */         callEndElement(info.qname, i < depth - 1 ? synthesizedAugs() : augs);
/*  821:     */       }
/*  822:     */     }
/*  823:1049 */     if (depth > 1)
/*  824:     */     {
/*  825:1050 */       int size = this.fInlineStack.top;
/*  826:1051 */       for (int i = 0; i < size; i++)
/*  827:     */       {
/*  828:1052 */         Info info = this.fInlineStack.pop();
/*  829:1053 */         XMLAttributes attributes = info.attributes;
/*  830:1054 */         if (this.fReportErrors)
/*  831:     */         {
/*  832:1055 */           String iname = info.qname.rawname;
/*  833:1056 */           this.fErrorReporter.reportWarning("HTML2008", new Object[] { iname });
/*  834:     */         }
/*  835:1058 */         forceStartElement(info.qname, attributes, synthesizedAugs());
/*  836:     */       }
/*  837:     */     }
/*  838:     */   }
/*  839:     */   
/*  840:     */   public void setDocumentSource(XMLDocumentSource source)
/*  841:     */   {
/*  842:1068 */     this.fDocumentSource = source;
/*  843:     */   }
/*  844:     */   
/*  845:     */   public XMLDocumentSource getDocumentSource()
/*  846:     */   {
/*  847:1073 */     return this.fDocumentSource;
/*  848:     */   }
/*  849:     */   
/*  850:     */   public void startDocument(XMLLocator locator, String encoding, Augmentations augs)
/*  851:     */     throws XNIException
/*  852:     */   {
/*  853:1081 */     startDocument(locator, encoding, null, augs);
/*  854:     */   }
/*  855:     */   
/*  856:     */   public void startPrefixMapping(String prefix, String uri, Augmentations augs)
/*  857:     */     throws XNIException
/*  858:     */   {
/*  859:1089 */     if (this.fSeenRootElementEnd) {
/*  860:1090 */       return;
/*  861:     */     }
/*  862:1094 */     if (this.fDocumentHandler != null) {
/*  863:1095 */       XercesBridge.getInstance().XMLDocumentHandler_startPrefixMapping(this.fDocumentHandler, prefix, uri, augs);
/*  864:     */     }
/*  865:     */   }
/*  866:     */   
/*  867:     */   public void endPrefixMapping(String prefix, Augmentations augs)
/*  868:     */     throws XNIException
/*  869:     */   {
/*  870:1105 */     if (this.fSeenRootElementEnd) {
/*  871:1106 */       return;
/*  872:     */     }
/*  873:1110 */     if (this.fDocumentHandler != null) {
/*  874:1111 */       XercesBridge.getInstance().XMLDocumentHandler_endPrefixMapping(this.fDocumentHandler, prefix, augs);
/*  875:     */     }
/*  876:     */   }
/*  877:     */   
/*  878:     */   protected HTMLElements.Element getElement(QName elementName)
/*  879:     */   {
/*  880:1122 */     String name = elementName.rawname;
/*  881:1123 */     if ((this.fNamespaces) && ("http://www.w3.org/1999/xhtml".equals(elementName.uri)))
/*  882:     */     {
/*  883:1124 */       int index = name.indexOf(':');
/*  884:1125 */       if (index != -1) {
/*  885:1126 */         name = name.substring(index + 1);
/*  886:     */       }
/*  887:     */     }
/*  888:1129 */     return HTMLElements.getElement(name);
/*  889:     */   }
/*  890:     */   
/*  891:     */   protected final void callStartElement(QName element, XMLAttributes attrs, Augmentations augs)
/*  892:     */     throws XNIException
/*  893:     */   {
/*  894:1136 */     this.fDocumentHandler.startElement(element, attrs, augs);
/*  895:     */   }
/*  896:     */   
/*  897:     */   protected final void callEndElement(QName element, Augmentations augs)
/*  898:     */     throws XNIException
/*  899:     */   {
/*  900:1142 */     this.fDocumentHandler.endElement(element, augs);
/*  901:     */   }
/*  902:     */   
/*  903:     */   protected final int getElementDepth(HTMLElements.Element element)
/*  904:     */   {
/*  905:1152 */     boolean container = element.isContainer();
/*  906:1153 */     short elementCode = element.code;
/*  907:1154 */     boolean tableBodyOrHtml = (elementCode == 101) || (elementCode == 14) || (elementCode == 46);
/*  908:     */     
/*  909:1156 */     int depth = -1;
/*  910:1157 */     for (int i = this.fElementStack.top - 1; i >= this.fragmentContextStackSize_; i--)
/*  911:     */     {
/*  912:1158 */       Info info = this.fElementStack.data[i];
/*  913:1159 */       if (info.element.code == element.code)
/*  914:     */       {
/*  915:1160 */         depth = this.fElementStack.top - i;
/*  916:1161 */         break;
/*  917:     */       }
/*  918:1163 */       if ((!container) && (info.element.isBlock())) {
/*  919:     */         break;
/*  920:     */       }
/*  921:1166 */       if ((info.element.code == 101) && (!tableBodyOrHtml)) {
/*  922:1167 */         return -1;
/*  923:     */       }
/*  924:     */     }
/*  925:1170 */     return depth;
/*  926:     */   }
/*  927:     */   
/*  928:     */   protected int getParentDepth(HTMLElements.Element[] parents, short bounds)
/*  929:     */   {
/*  930:1180 */     if (parents != null) {
/*  931:1181 */       for (int i = this.fElementStack.top - 1; i >= 0; i--)
/*  932:     */       {
/*  933:1182 */         Info info = this.fElementStack.data[i];
/*  934:1183 */         if (info.element.code == bounds) {
/*  935:     */           break;
/*  936:     */         }
/*  937:1186 */         for (int j = 0; j < parents.length; j++) {
/*  938:1187 */           if (info.element.code == parents[j].code) {
/*  939:1188 */             return this.fElementStack.top - i;
/*  940:     */           }
/*  941:     */         }
/*  942:     */       }
/*  943:     */     }
/*  944:1193 */     return -1;
/*  945:     */   }
/*  946:     */   
/*  947:     */   protected final XMLAttributes emptyAttributes()
/*  948:     */   {
/*  949:1198 */     this.fEmptyAttrs.removeAllAttributes();
/*  950:1199 */     return this.fEmptyAttrs;
/*  951:     */   }
/*  952:     */   
/*  953:     */   protected final Augmentations synthesizedAugs()
/*  954:     */   {
/*  955:1204 */     HTMLAugmentations augs = null;
/*  956:1205 */     if (this.fAugmentations)
/*  957:     */     {
/*  958:1206 */       augs = this.fInfosetAugs;
/*  959:1207 */       augs.removeAllItems();
/*  960:1208 */       augs.putItem("http://cyberneko.org/html/features/augmentations", SYNTHESIZED_ITEM);
/*  961:     */     }
/*  962:1210 */     return augs;
/*  963:     */   }
/*  964:     */   
/*  965:     */   protected static final String modifyName(String name, short mode)
/*  966:     */   {
/*  967:1219 */     switch (mode)
/*  968:     */     {
/*  969:     */     case 1: 
/*  970:1220 */       return name.toUpperCase();
/*  971:     */     case 2: 
/*  972:1221 */       return name.toLowerCase();
/*  973:     */     }
/*  974:1223 */     return name;
/*  975:     */   }
/*  976:     */   
/*  977:     */   protected static final short getNamesValue(String value)
/*  978:     */   {
/*  979:1234 */     if (value.equals("lower")) {
/*  980:1235 */       return 2;
/*  981:     */     }
/*  982:1237 */     if (value.equals("upper")) {
/*  983:1238 */       return 1;
/*  984:     */     }
/*  985:1240 */     return 0;
/*  986:     */   }
/*  987:     */   
/*  988:     */   public static class Info
/*  989:     */   {
/*  990:     */     public HTMLElements.Element element;
/*  991:     */     public QName qname;
/*  992:     */     public XMLAttributes attributes;
/*  993:     */     
/*  994:     */     public Info(HTMLElements.Element element, QName qname)
/*  995:     */     {
/*  996:1290 */       this(element, qname, null);
/*  997:     */     }
/*  998:     */     
/*  999:     */     public Info(HTMLElements.Element element, QName qname, XMLAttributes attributes)
/* 1000:     */     {
/* 1001:1304 */       this.element = element;
/* 1002:1305 */       this.qname = new QName(qname);
/* 1003:1306 */       if (attributes != null)
/* 1004:     */       {
/* 1005:1307 */         int length = attributes.getLength();
/* 1006:1308 */         if (length > 0)
/* 1007:     */         {
/* 1008:1309 */           QName aqname = new QName();
/* 1009:1310 */           XMLAttributes newattrs = new XMLAttributesImpl();
/* 1010:1311 */           for (int i = 0; i < length; i++)
/* 1011:     */           {
/* 1012:1312 */             attributes.getName(i, aqname);
/* 1013:1313 */             String type = attributes.getType(i);
/* 1014:1314 */             String value = attributes.getValue(i);
/* 1015:1315 */             String nonNormalizedValue = attributes.getNonNormalizedValue(i);
/* 1016:1316 */             boolean specified = attributes.isSpecified(i);
/* 1017:1317 */             newattrs.addAttribute(aqname, type, value);
/* 1018:1318 */             newattrs.setNonNormalizedValue(i, nonNormalizedValue);
/* 1019:1319 */             newattrs.setSpecified(i, specified);
/* 1020:     */           }
/* 1021:1321 */           this.attributes = newattrs;
/* 1022:     */         }
/* 1023:     */       }
/* 1024:     */     }
/* 1025:     */     
/* 1026:     */     public String toString()
/* 1027:     */     {
/* 1028:1330 */       return super.toString() + this.qname;
/* 1029:     */     }
/* 1030:     */   }
/* 1031:     */   
/* 1032:     */   public static class InfoStack
/* 1033:     */   {
/* 1034:     */     public int top;
/* 1035:1345 */     public HTMLTagBalancer.Info[] data = new HTMLTagBalancer.Info[10];
/* 1036:     */     
/* 1037:     */     public void push(HTMLTagBalancer.Info info)
/* 1038:     */     {
/* 1039:1353 */       if (this.top == this.data.length)
/* 1040:     */       {
/* 1041:1354 */         HTMLTagBalancer.Info[] newarray = new HTMLTagBalancer.Info[this.top + 10];
/* 1042:1355 */         System.arraycopy(this.data, 0, newarray, 0, this.top);
/* 1043:1356 */         this.data = newarray;
/* 1044:     */       }
/* 1045:1358 */       this.data[(this.top++)] = info;
/* 1046:     */     }
/* 1047:     */     
/* 1048:     */     public HTMLTagBalancer.Info peek()
/* 1049:     */     {
/* 1050:1363 */       return this.data[(this.top - 1)];
/* 1051:     */     }
/* 1052:     */     
/* 1053:     */     public HTMLTagBalancer.Info pop()
/* 1054:     */     {
/* 1055:1368 */       return this.data[(--this.top)];
/* 1056:     */     }
/* 1057:     */     
/* 1058:     */     public String toString()
/* 1059:     */     {
/* 1060:1375 */       StringBuffer sb = new StringBuffer("InfoStack(");
/* 1061:1376 */       for (int i = this.top - 1; i >= 0; i--)
/* 1062:     */       {
/* 1063:1377 */         sb.append(this.data[i]);
/* 1064:1378 */         if (i != 0) {
/* 1065:1379 */           sb.append(", ");
/* 1066:     */         }
/* 1067:     */       }
/* 1068:1381 */       sb.append(")");
/* 1069:1382 */       return sb.toString();
/* 1070:     */     }
/* 1071:     */   }
/* 1072:     */   
/* 1073:     */   void setTagBalancingListener(HTMLTagBalancingListener tagBalancingListener)
/* 1074:     */   {
/* 1075:1389 */     this.tagBalancingListener = tagBalancingListener;
/* 1076:     */   }
/* 1077:     */   
/* 1078:     */   private void notifyDiscardedStartElement(QName elem, XMLAttributes attrs, Augmentations augs)
/* 1079:     */   {
/* 1080:1397 */     if (this.tagBalancingListener != null) {
/* 1081:1398 */       this.tagBalancingListener.ignoredStartElement(elem, attrs, augs);
/* 1082:     */     }
/* 1083:     */   }
/* 1084:     */   
/* 1085:     */   private void notifyDiscardedEndElement(QName element, Augmentations augs)
/* 1086:     */   {
/* 1087:1405 */     if (this.tagBalancingListener != null) {
/* 1088:1406 */       this.tagBalancingListener.ignoredEndElement(element, augs);
/* 1089:     */     }
/* 1090:     */   }
/* 1091:     */   
/* 1092:     */   static class ElementEntry
/* 1093:     */   {
/* 1094:     */     private final QName name_;
/* 1095:     */     private final Augmentations augs_;
/* 1096:     */     
/* 1097:     */     ElementEntry(QName element, Augmentations augs)
/* 1098:     */     {
/* 1099:1416 */       this.name_ = new QName(element);
/* 1100:1417 */       this.augs_ = (augs == null ? null : new HTMLAugmentations(augs));
/* 1101:     */     }
/* 1102:     */   }
/* 1103:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.cyberneko.html.HTMLTagBalancer
 * JD-Core Version:    0.7.0.1
 */