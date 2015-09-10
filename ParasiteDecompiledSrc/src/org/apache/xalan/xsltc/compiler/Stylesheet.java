/*    1:     */ package org.apache.xalan.xsltc.compiler;
/*    2:     */ 
/*    3:     */ import java.util.Enumeration;
/*    4:     */ import java.util.Hashtable;
/*    5:     */ import java.util.Iterator;
/*    6:     */ import java.util.Properties;
/*    7:     */ import java.util.StringTokenizer;
/*    8:     */ import java.util.Vector;
/*    9:     */ import org.apache.bcel.generic.ANEWARRAY;
/*   10:     */ import org.apache.bcel.generic.ClassGen;
/*   11:     */ import org.apache.bcel.generic.ConstantPoolGen;
/*   12:     */ import org.apache.bcel.generic.FieldGen;
/*   13:     */ import org.apache.bcel.generic.GETFIELD;
/*   14:     */ import org.apache.bcel.generic.GETSTATIC;
/*   15:     */ import org.apache.bcel.generic.INVOKEINTERFACE;
/*   16:     */ import org.apache.bcel.generic.INVOKESPECIAL;
/*   17:     */ import org.apache.bcel.generic.INVOKEVIRTUAL;
/*   18:     */ import org.apache.bcel.generic.ISTORE;
/*   19:     */ import org.apache.bcel.generic.InstructionConstants;
/*   20:     */ import org.apache.bcel.generic.InstructionHandle;
/*   21:     */ import org.apache.bcel.generic.InstructionList;
/*   22:     */ import org.apache.bcel.generic.LocalVariableGen;
/*   23:     */ import org.apache.bcel.generic.MethodGen;
/*   24:     */ import org.apache.bcel.generic.NEW;
/*   25:     */ import org.apache.bcel.generic.NEWARRAY;
/*   26:     */ import org.apache.bcel.generic.PUSH;
/*   27:     */ import org.apache.bcel.generic.PUTFIELD;
/*   28:     */ import org.apache.bcel.generic.PUTSTATIC;
/*   29:     */ import org.apache.bcel.generic.TargetLostException;
/*   30:     */ import org.apache.bcel.util.InstructionFinder;
/*   31:     */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*   32:     */ import org.apache.xalan.xsltc.compiler.util.ErrorMsg;
/*   33:     */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*   34:     */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*   35:     */ import org.apache.xalan.xsltc.compiler.util.Util;
/*   36:     */ import org.apache.xml.utils.SystemIDResolver;
/*   37:     */ 
/*   38:     */ public final class Stylesheet
/*   39:     */   extends SyntaxTreeNode
/*   40:     */ {
/*   41:     */   private String _version;
/*   42:     */   private QName _name;
/*   43:     */   private String _systemId;
/*   44:     */   private Stylesheet _parentStylesheet;
/*   45:  86 */   private Vector _globals = new Vector();
/*   46:  91 */   private Boolean _hasLocalParams = null;
/*   47:     */   private String _className;
/*   48: 101 */   private final Vector _templates = new Vector();
/*   49: 107 */   private Vector _allValidTemplates = null;
/*   50: 109 */   private Vector _elementsWithNamespacesUsedDynamically = null;
/*   51: 114 */   private int _nextModeSerial = 1;
/*   52: 119 */   private final Hashtable _modes = new Hashtable();
/*   53:     */   private Mode _defaultMode;
/*   54: 129 */   private final Hashtable _extensions = new Hashtable();
/*   55: 135 */   public Stylesheet _importedFrom = null;
/*   56: 141 */   public Stylesheet _includedFrom = null;
/*   57: 146 */   private Vector _includedStylesheets = null;
/*   58: 151 */   private int _importPrecedence = 1;
/*   59: 157 */   private int _minimumDescendantPrecedence = -1;
/*   60: 162 */   private Hashtable _keys = new Hashtable();
/*   61: 168 */   private SourceLoader _loader = null;
/*   62: 173 */   private boolean _numberFormattingUsed = false;
/*   63: 179 */   private boolean _simplified = false;
/*   64: 184 */   private boolean _multiDocument = false;
/*   65: 189 */   private boolean _callsNodeset = false;
/*   66: 194 */   private boolean _hasIdCall = false;
/*   67: 200 */   private boolean _templateInlining = false;
/*   68: 205 */   private Output _lastOutputElement = null;
/*   69: 210 */   private Properties _outputProperties = null;
/*   70: 216 */   private int _outputMethod = 0;
/*   71:     */   public static final int UNKNOWN_OUTPUT = 0;
/*   72:     */   public static final int XML_OUTPUT = 1;
/*   73:     */   public static final int HTML_OUTPUT = 2;
/*   74:     */   public static final int TEXT_OUTPUT = 3;
/*   75:     */   
/*   76:     */   public int getOutputMethod()
/*   77:     */   {
/*   78: 228 */     return this._outputMethod;
/*   79:     */   }
/*   80:     */   
/*   81:     */   private void checkOutputMethod()
/*   82:     */   {
/*   83: 235 */     if (this._lastOutputElement != null)
/*   84:     */     {
/*   85: 236 */       String method = this._lastOutputElement.getOutputMethod();
/*   86: 237 */       if (method != null) {
/*   87: 238 */         if (method.equals("xml")) {
/*   88: 239 */           this._outputMethod = 1;
/*   89: 240 */         } else if (method.equals("html")) {
/*   90: 241 */           this._outputMethod = 2;
/*   91: 242 */         } else if (method.equals("text")) {
/*   92: 243 */           this._outputMethod = 3;
/*   93:     */         }
/*   94:     */       }
/*   95:     */     }
/*   96:     */   }
/*   97:     */   
/*   98:     */   public boolean getTemplateInlining()
/*   99:     */   {
/*  100: 249 */     return this._templateInlining;
/*  101:     */   }
/*  102:     */   
/*  103:     */   public void setTemplateInlining(boolean flag)
/*  104:     */   {
/*  105: 253 */     this._templateInlining = flag;
/*  106:     */   }
/*  107:     */   
/*  108:     */   public boolean isSimplified()
/*  109:     */   {
/*  110: 257 */     return this._simplified;
/*  111:     */   }
/*  112:     */   
/*  113:     */   public void setSimplified()
/*  114:     */   {
/*  115: 261 */     this._simplified = true;
/*  116:     */   }
/*  117:     */   
/*  118:     */   public void setHasIdCall(boolean flag)
/*  119:     */   {
/*  120: 265 */     this._hasIdCall = flag;
/*  121:     */   }
/*  122:     */   
/*  123:     */   public void setOutputProperty(String key, String value)
/*  124:     */   {
/*  125: 269 */     if (this._outputProperties == null) {
/*  126: 270 */       this._outputProperties = new Properties();
/*  127:     */     }
/*  128: 272 */     this._outputProperties.setProperty(key, value);
/*  129:     */   }
/*  130:     */   
/*  131:     */   public void setOutputProperties(Properties props)
/*  132:     */   {
/*  133: 276 */     this._outputProperties = props;
/*  134:     */   }
/*  135:     */   
/*  136:     */   public Properties getOutputProperties()
/*  137:     */   {
/*  138: 280 */     return this._outputProperties;
/*  139:     */   }
/*  140:     */   
/*  141:     */   public Output getLastOutputElement()
/*  142:     */   {
/*  143: 284 */     return this._lastOutputElement;
/*  144:     */   }
/*  145:     */   
/*  146:     */   public void setMultiDocument(boolean flag)
/*  147:     */   {
/*  148: 288 */     this._multiDocument = flag;
/*  149:     */   }
/*  150:     */   
/*  151:     */   public boolean isMultiDocument()
/*  152:     */   {
/*  153: 292 */     return this._multiDocument;
/*  154:     */   }
/*  155:     */   
/*  156:     */   public void setCallsNodeset(boolean flag)
/*  157:     */   {
/*  158: 296 */     if (flag) {
/*  159: 296 */       setMultiDocument(flag);
/*  160:     */     }
/*  161: 297 */     this._callsNodeset = flag;
/*  162:     */   }
/*  163:     */   
/*  164:     */   public boolean callsNodeset()
/*  165:     */   {
/*  166: 301 */     return this._callsNodeset;
/*  167:     */   }
/*  168:     */   
/*  169:     */   public void numberFormattingUsed()
/*  170:     */   {
/*  171: 305 */     this._numberFormattingUsed = true;
/*  172:     */     
/*  173:     */ 
/*  174:     */ 
/*  175:     */ 
/*  176:     */ 
/*  177:     */ 
/*  178: 312 */     Stylesheet parent = getParentStylesheet();
/*  179: 313 */     if (null != parent) {
/*  180: 313 */       parent.numberFormattingUsed();
/*  181:     */     }
/*  182:     */   }
/*  183:     */   
/*  184:     */   public void setImportPrecedence(int precedence)
/*  185:     */   {
/*  186: 318 */     this._importPrecedence = precedence;
/*  187:     */     
/*  188:     */ 
/*  189: 321 */     Enumeration elements = elements();
/*  190: 322 */     while (elements.hasMoreElements())
/*  191:     */     {
/*  192: 323 */       SyntaxTreeNode child = (SyntaxTreeNode)elements.nextElement();
/*  193: 324 */       if ((child instanceof Include))
/*  194:     */       {
/*  195: 325 */         Stylesheet included = ((Include)child).getIncludedStylesheet();
/*  196: 326 */         if ((included != null) && (included._includedFrom == this)) {
/*  197: 327 */           included.setImportPrecedence(precedence);
/*  198:     */         }
/*  199:     */       }
/*  200:     */     }
/*  201: 333 */     if (this._importedFrom != null)
/*  202:     */     {
/*  203: 334 */       if (this._importedFrom.getImportPrecedence() < precedence)
/*  204:     */       {
/*  205: 335 */         Parser parser = getParser();
/*  206: 336 */         int nextPrecedence = parser.getNextImportPrecedence();
/*  207: 337 */         this._importedFrom.setImportPrecedence(nextPrecedence);
/*  208:     */       }
/*  209:     */     }
/*  210: 341 */     else if ((this._includedFrom != null) && 
/*  211: 342 */       (this._includedFrom.getImportPrecedence() != precedence)) {
/*  212: 343 */       this._includedFrom.setImportPrecedence(precedence);
/*  213:     */     }
/*  214:     */   }
/*  215:     */   
/*  216:     */   public int getImportPrecedence()
/*  217:     */   {
/*  218: 348 */     return this._importPrecedence;
/*  219:     */   }
/*  220:     */   
/*  221:     */   public int getMinimumDescendantPrecedence()
/*  222:     */   {
/*  223: 357 */     if (this._minimumDescendantPrecedence == -1)
/*  224:     */     {
/*  225: 359 */       int min = getImportPrecedence();
/*  226:     */       
/*  227:     */ 
/*  228: 362 */       int inclImpCount = this._includedStylesheets != null ? this._includedStylesheets.size() : 0;
/*  229: 366 */       for (int i = 0; i < inclImpCount; i++)
/*  230:     */       {
/*  231: 367 */         int prec = ((Stylesheet)this._includedStylesheets.elementAt(i)).getMinimumDescendantPrecedence();
/*  232: 370 */         if (prec < min) {
/*  233: 371 */           min = prec;
/*  234:     */         }
/*  235:     */       }
/*  236: 375 */       this._minimumDescendantPrecedence = min;
/*  237:     */     }
/*  238: 377 */     return this._minimumDescendantPrecedence;
/*  239:     */   }
/*  240:     */   
/*  241:     */   public boolean checkForLoop(String systemId)
/*  242:     */   {
/*  243: 382 */     if ((this._systemId != null) && (this._systemId.equals(systemId))) {
/*  244: 383 */       return true;
/*  245:     */     }
/*  246: 386 */     if (this._parentStylesheet != null) {
/*  247: 387 */       return this._parentStylesheet.checkForLoop(systemId);
/*  248:     */     }
/*  249: 389 */     return false;
/*  250:     */   }
/*  251:     */   
/*  252:     */   public void setParser(Parser parser)
/*  253:     */   {
/*  254: 393 */     super.setParser(parser);
/*  255: 394 */     this._name = makeStylesheetName("__stylesheet_");
/*  256:     */   }
/*  257:     */   
/*  258:     */   public void setParentStylesheet(Stylesheet parent)
/*  259:     */   {
/*  260: 398 */     this._parentStylesheet = parent;
/*  261:     */   }
/*  262:     */   
/*  263:     */   public Stylesheet getParentStylesheet()
/*  264:     */   {
/*  265: 402 */     return this._parentStylesheet;
/*  266:     */   }
/*  267:     */   
/*  268:     */   public void setImportingStylesheet(Stylesheet parent)
/*  269:     */   {
/*  270: 406 */     this._importedFrom = parent;
/*  271: 407 */     parent.addIncludedStylesheet(this);
/*  272:     */   }
/*  273:     */   
/*  274:     */   public void setIncludingStylesheet(Stylesheet parent)
/*  275:     */   {
/*  276: 411 */     this._includedFrom = parent;
/*  277: 412 */     parent.addIncludedStylesheet(this);
/*  278:     */   }
/*  279:     */   
/*  280:     */   public void addIncludedStylesheet(Stylesheet child)
/*  281:     */   {
/*  282: 416 */     if (this._includedStylesheets == null) {
/*  283: 417 */       this._includedStylesheets = new Vector();
/*  284:     */     }
/*  285: 419 */     this._includedStylesheets.addElement(child);
/*  286:     */   }
/*  287:     */   
/*  288:     */   public void setSystemId(String systemId)
/*  289:     */   {
/*  290: 423 */     if (systemId != null) {
/*  291: 424 */       this._systemId = SystemIDResolver.getAbsoluteURI(systemId);
/*  292:     */     }
/*  293:     */   }
/*  294:     */   
/*  295:     */   public String getSystemId()
/*  296:     */   {
/*  297: 429 */     return this._systemId;
/*  298:     */   }
/*  299:     */   
/*  300:     */   public void setSourceLoader(SourceLoader loader)
/*  301:     */   {
/*  302: 433 */     this._loader = loader;
/*  303:     */   }
/*  304:     */   
/*  305:     */   public SourceLoader getSourceLoader()
/*  306:     */   {
/*  307: 437 */     return this._loader;
/*  308:     */   }
/*  309:     */   
/*  310:     */   private QName makeStylesheetName(String prefix)
/*  311:     */   {
/*  312: 441 */     return getParser().getQName(prefix + getXSLTC().nextStylesheetSerial());
/*  313:     */   }
/*  314:     */   
/*  315:     */   public boolean hasGlobals()
/*  316:     */   {
/*  317: 448 */     return this._globals.size() > 0;
/*  318:     */   }
/*  319:     */   
/*  320:     */   public boolean hasLocalParams()
/*  321:     */   {
/*  322: 457 */     if (this._hasLocalParams == null)
/*  323:     */     {
/*  324: 458 */       Vector templates = getAllValidTemplates();
/*  325: 459 */       int n = templates.size();
/*  326: 460 */       for (int i = 0; i < n; i++)
/*  327:     */       {
/*  328: 461 */         Template template = (Template)templates.elementAt(i);
/*  329: 462 */         if (template.hasParams())
/*  330:     */         {
/*  331: 463 */           this._hasLocalParams = Boolean.TRUE;
/*  332: 464 */           return true;
/*  333:     */         }
/*  334:     */       }
/*  335: 467 */       this._hasLocalParams = Boolean.FALSE;
/*  336: 468 */       return false;
/*  337:     */     }
/*  338: 471 */     return this._hasLocalParams.booleanValue();
/*  339:     */   }
/*  340:     */   
/*  341:     */   protected void addPrefixMapping(String prefix, String uri)
/*  342:     */   {
/*  343: 481 */     if ((prefix.equals("")) && (uri.equals("http://www.w3.org/1999/xhtml"))) {
/*  344: 481 */       return;
/*  345:     */     }
/*  346: 482 */     super.addPrefixMapping(prefix, uri);
/*  347:     */   }
/*  348:     */   
/*  349:     */   private void extensionURI(String prefixes, SymbolTable stable)
/*  350:     */   {
/*  351: 489 */     if (prefixes != null)
/*  352:     */     {
/*  353: 490 */       StringTokenizer tokens = new StringTokenizer(prefixes);
/*  354: 491 */       while (tokens.hasMoreTokens())
/*  355:     */       {
/*  356: 492 */         String prefix = tokens.nextToken();
/*  357: 493 */         String uri = lookupNamespace(prefix);
/*  358: 494 */         if (uri != null) {
/*  359: 495 */           this._extensions.put(uri, prefix);
/*  360:     */         }
/*  361:     */       }
/*  362:     */     }
/*  363:     */   }
/*  364:     */   
/*  365:     */   public boolean isExtension(String uri)
/*  366:     */   {
/*  367: 502 */     return this._extensions.get(uri) != null;
/*  368:     */   }
/*  369:     */   
/*  370:     */   public void excludeExtensionPrefixes(Parser parser)
/*  371:     */   {
/*  372: 506 */     SymbolTable stable = parser.getSymbolTable();
/*  373: 507 */     String excludePrefixes = getAttribute("exclude-result-prefixes");
/*  374: 508 */     String extensionPrefixes = getAttribute("extension-element-prefixes");
/*  375:     */     
/*  376:     */ 
/*  377: 511 */     stable.excludeURI("http://www.w3.org/1999/XSL/Transform");
/*  378: 512 */     stable.excludeNamespaces(excludePrefixes);
/*  379: 513 */     stable.excludeNamespaces(extensionPrefixes);
/*  380: 514 */     extensionURI(extensionPrefixes, stable);
/*  381:     */   }
/*  382:     */   
/*  383:     */   public void parseContents(Parser parser)
/*  384:     */   {
/*  385: 523 */     SymbolTable stable = parser.getSymbolTable();
/*  386:     */     
/*  387:     */ 
/*  388:     */ 
/*  389:     */ 
/*  390:     */ 
/*  391:     */ 
/*  392:     */ 
/*  393:     */ 
/*  394:     */ 
/*  395:     */ 
/*  396:     */ 
/*  397:     */ 
/*  398:     */ 
/*  399: 537 */     addPrefixMapping("xml", "http://www.w3.org/XML/1998/namespace");
/*  400:     */     
/*  401:     */ 
/*  402: 540 */     Stylesheet sheet = stable.addStylesheet(this._name, this);
/*  403: 541 */     if (sheet != null)
/*  404:     */     {
/*  405: 543 */       ErrorMsg err = new ErrorMsg("MULTIPLE_STYLESHEET_ERR", this);
/*  406: 544 */       parser.reportError(3, err);
/*  407:     */     }
/*  408: 552 */     if (this._simplified)
/*  409:     */     {
/*  410: 553 */       stable.excludeURI("http://www.w3.org/1999/XSL/Transform");
/*  411: 554 */       Template template = new Template();
/*  412: 555 */       template.parseSimplified(this, parser);
/*  413:     */     }
/*  414:     */     else
/*  415:     */     {
/*  416: 559 */       parseOwnChildren(parser);
/*  417:     */     }
/*  418:     */   }
/*  419:     */   
/*  420:     */   public final void parseOwnChildren(Parser parser)
/*  421:     */   {
/*  422: 567 */     Vector contents = getContents();
/*  423: 568 */     int count = contents.size();
/*  424: 572 */     for (int i = 0; i < count; i++)
/*  425:     */     {
/*  426: 573 */       SyntaxTreeNode child = (SyntaxTreeNode)contents.elementAt(i);
/*  427: 574 */       if (((child instanceof VariableBase)) || ((child instanceof NamespaceAlias)))
/*  428:     */       {
/*  429: 576 */         parser.getSymbolTable().setCurrentNode(child);
/*  430: 577 */         child.parseContents(parser);
/*  431:     */       }
/*  432:     */     }
/*  433: 582 */     for (int i = 0; i < count; i++)
/*  434:     */     {
/*  435: 583 */       SyntaxTreeNode child = (SyntaxTreeNode)contents.elementAt(i);
/*  436: 584 */       if ((!(child instanceof VariableBase)) && (!(child instanceof NamespaceAlias)))
/*  437:     */       {
/*  438: 586 */         parser.getSymbolTable().setCurrentNode(child);
/*  439: 587 */         child.parseContents(parser);
/*  440:     */       }
/*  441: 592 */       if ((!this._templateInlining) && ((child instanceof Template)))
/*  442:     */       {
/*  443: 593 */         Template template = (Template)child;
/*  444: 594 */         String name = "template$dot$" + template.getPosition();
/*  445: 595 */         template.setName(parser.getQName(name));
/*  446:     */       }
/*  447:     */     }
/*  448:     */   }
/*  449:     */   
/*  450:     */   public void processModes()
/*  451:     */   {
/*  452: 601 */     if (this._defaultMode == null) {
/*  453: 602 */       this._defaultMode = new Mode(null, this, "");
/*  454:     */     }
/*  455: 603 */     this._defaultMode.processPatterns(this._keys);
/*  456: 604 */     Enumeration modes = this._modes.elements();
/*  457: 605 */     while (modes.hasMoreElements())
/*  458:     */     {
/*  459: 606 */       Mode mode = (Mode)modes.nextElement();
/*  460: 607 */       mode.processPatterns(this._keys);
/*  461:     */     }
/*  462:     */   }
/*  463:     */   
/*  464:     */   private void compileModes(ClassGenerator classGen)
/*  465:     */   {
/*  466: 612 */     this._defaultMode.compileApplyTemplates(classGen);
/*  467: 613 */     Enumeration modes = this._modes.elements();
/*  468: 614 */     while (modes.hasMoreElements())
/*  469:     */     {
/*  470: 615 */       Mode mode = (Mode)modes.nextElement();
/*  471: 616 */       mode.compileApplyTemplates(classGen);
/*  472:     */     }
/*  473:     */   }
/*  474:     */   
/*  475:     */   public Mode getMode(QName modeName)
/*  476:     */   {
/*  477: 621 */     if (modeName == null)
/*  478:     */     {
/*  479: 622 */       if (this._defaultMode == null) {
/*  480: 623 */         this._defaultMode = new Mode(null, this, "");
/*  481:     */       }
/*  482: 625 */       return this._defaultMode;
/*  483:     */     }
/*  484: 628 */     Mode mode = (Mode)this._modes.get(modeName);
/*  485: 629 */     if (mode == null)
/*  486:     */     {
/*  487: 630 */       String suffix = Integer.toString(this._nextModeSerial++);
/*  488: 631 */       this._modes.put(modeName, mode = new Mode(modeName, this, suffix));
/*  489:     */     }
/*  490: 633 */     return mode;
/*  491:     */   }
/*  492:     */   
/*  493:     */   public org.apache.xalan.xsltc.compiler.util.Type typeCheck(SymbolTable stable)
/*  494:     */     throws TypeCheckError
/*  495:     */   {
/*  496: 641 */     int count = this._globals.size();
/*  497: 642 */     for (int i = 0; i < count; i++)
/*  498:     */     {
/*  499: 643 */       VariableBase var = (VariableBase)this._globals.elementAt(i);
/*  500: 644 */       var.typeCheck(stable);
/*  501:     */     }
/*  502: 646 */     return typeCheckContents(stable);
/*  503:     */   }
/*  504:     */   
/*  505:     */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/*  506:     */   {
/*  507: 653 */     translate();
/*  508:     */   }
/*  509:     */   
/*  510:     */   private void addDOMField(ClassGenerator classGen)
/*  511:     */   {
/*  512: 657 */     FieldGen fgen = new FieldGen(1, Util.getJCRefType("Lorg/apache/xalan/xsltc/DOM;"), "_dom", classGen.getConstantPool());
/*  513:     */     
/*  514:     */ 
/*  515:     */ 
/*  516: 661 */     classGen.addField(fgen.getField());
/*  517:     */   }
/*  518:     */   
/*  519:     */   private void addStaticField(ClassGenerator classGen, String type, String name)
/*  520:     */   {
/*  521: 670 */     FieldGen fgen = new FieldGen(12, Util.getJCRefType(type), name, classGen.getConstantPool());
/*  522:     */     
/*  523:     */ 
/*  524:     */ 
/*  525: 674 */     classGen.addField(fgen.getField());
/*  526:     */   }
/*  527:     */   
/*  528:     */   public void translate()
/*  529:     */   {
/*  530: 682 */     this._className = getXSLTC().getClassName();
/*  531:     */     
/*  532:     */ 
/*  533: 685 */     ClassGenerator classGen = new ClassGenerator(this._className, "org.apache.xalan.xsltc.runtime.AbstractTranslet", "", 33, null, this);
/*  534:     */     
/*  535:     */ 
/*  536:     */ 
/*  537:     */ 
/*  538:     */ 
/*  539:     */ 
/*  540: 692 */     addDOMField(classGen);
/*  541:     */     
/*  542:     */ 
/*  543:     */ 
/*  544: 696 */     compileTransform(classGen);
/*  545:     */     
/*  546:     */ 
/*  547: 699 */     Enumeration elements = elements();
/*  548: 700 */     while (elements.hasMoreElements())
/*  549:     */     {
/*  550: 701 */       Object element = elements.nextElement();
/*  551: 703 */       if ((element instanceof Template))
/*  552:     */       {
/*  553: 705 */         Template template = (Template)element;
/*  554:     */         
/*  555: 707 */         getMode(template.getModeName()).addTemplate(template);
/*  556:     */       }
/*  557: 710 */       else if ((element instanceof AttributeSet))
/*  558:     */       {
/*  559: 711 */         ((AttributeSet)element).translate(classGen, null);
/*  560:     */       }
/*  561: 713 */       else if ((element instanceof Output))
/*  562:     */       {
/*  563: 715 */         Output output = (Output)element;
/*  564: 716 */         if (output.enabled()) {
/*  565: 716 */           this._lastOutputElement = output;
/*  566:     */         }
/*  567:     */       }
/*  568:     */     }
/*  569: 725 */     checkOutputMethod();
/*  570: 726 */     processModes();
/*  571: 727 */     compileModes(classGen);
/*  572: 728 */     compileStaticInitializer(classGen);
/*  573: 729 */     compileConstructor(classGen, this._lastOutputElement);
/*  574: 731 */     if (!getParser().errorsFound()) {
/*  575: 732 */       getXSLTC().dumpClass(classGen.getJavaClass());
/*  576:     */     }
/*  577:     */   }
/*  578:     */   
/*  579:     */   private void compileStaticInitializer(ClassGenerator classGen)
/*  580:     */   {
/*  581: 792 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*  582: 793 */     InstructionList il = new InstructionList();
/*  583:     */     
/*  584: 795 */     MethodGenerator staticConst = new MethodGenerator(9, org.apache.bcel.generic.Type.VOID, null, null, "<clinit>", this._className, il, cpg);
/*  585:     */     
/*  586:     */ 
/*  587:     */ 
/*  588:     */ 
/*  589:     */ 
/*  590: 801 */     addStaticField(classGen, "[Ljava/lang/String;", "_sNamesArray");
/*  591: 802 */     addStaticField(classGen, "[Ljava/lang/String;", "_sUrisArray");
/*  592: 803 */     addStaticField(classGen, "[I", "_sTypesArray");
/*  593: 804 */     addStaticField(classGen, "[Ljava/lang/String;", "_sNamespaceArray");
/*  594:     */     
/*  595:     */ 
/*  596: 807 */     int charDataFieldCount = getXSLTC().getCharacterDataCount();
/*  597: 808 */     for (int i = 0; i < charDataFieldCount; i++) {
/*  598: 809 */       addStaticField(classGen, "[C", "_scharData" + i);
/*  599:     */     }
/*  600: 814 */     Vector namesIndex = getXSLTC().getNamesIndex();
/*  601: 815 */     int size = namesIndex.size();
/*  602: 816 */     String[] namesArray = new String[size];
/*  603: 817 */     String[] urisArray = new String[size];
/*  604: 818 */     int[] typesArray = new int[size];
/*  605: 821 */     for (int i = 0; i < size; i++)
/*  606:     */     {
/*  607: 822 */       String encodedName = (String)namesIndex.elementAt(i);
/*  608:     */       int index;
/*  609: 823 */       if ((index = encodedName.lastIndexOf(':')) > -1) {
/*  610: 824 */         urisArray[i] = encodedName.substring(0, index);
/*  611:     */       }
/*  612: 827 */       index += 1;
/*  613: 828 */       if (encodedName.charAt(index) == '@')
/*  614:     */       {
/*  615: 829 */         typesArray[i] = 2;
/*  616: 830 */         index++;
/*  617:     */       }
/*  618: 831 */       else if (encodedName.charAt(index) == '?')
/*  619:     */       {
/*  620: 832 */         typesArray[i] = 13;
/*  621: 833 */         index++;
/*  622:     */       }
/*  623:     */       else
/*  624:     */       {
/*  625: 835 */         typesArray[i] = 1;
/*  626:     */       }
/*  627: 838 */       if (index == 0) {
/*  628: 839 */         namesArray[i] = encodedName;
/*  629:     */       } else {
/*  630: 842 */         namesArray[i] = encodedName.substring(index);
/*  631:     */       }
/*  632:     */     }
/*  633: 846 */     staticConst.markChunkStart();
/*  634: 847 */     il.append(new PUSH(cpg, size));
/*  635: 848 */     il.append(new ANEWARRAY(cpg.addClass("java.lang.String")));
/*  636: 849 */     int namesArrayRef = cpg.addFieldref(this._className, "_sNamesArray", "[Ljava/lang/String;");
/*  637:     */     
/*  638:     */ 
/*  639: 852 */     il.append(new PUTSTATIC(namesArrayRef));
/*  640: 853 */     staticConst.markChunkEnd();
/*  641: 855 */     for (int i = 0; i < size; i++)
/*  642:     */     {
/*  643: 856 */       String name = namesArray[i];
/*  644: 857 */       staticConst.markChunkStart();
/*  645: 858 */       il.append(new GETSTATIC(namesArrayRef));
/*  646: 859 */       il.append(new PUSH(cpg, i));
/*  647: 860 */       il.append(new PUSH(cpg, name));
/*  648: 861 */       il.append(InstructionConstants.AASTORE);
/*  649: 862 */       staticConst.markChunkEnd();
/*  650:     */     }
/*  651: 865 */     staticConst.markChunkStart();
/*  652: 866 */     il.append(new PUSH(cpg, size));
/*  653: 867 */     il.append(new ANEWARRAY(cpg.addClass("java.lang.String")));
/*  654: 868 */     int urisArrayRef = cpg.addFieldref(this._className, "_sUrisArray", "[Ljava/lang/String;");
/*  655:     */     
/*  656:     */ 
/*  657: 871 */     il.append(new PUTSTATIC(urisArrayRef));
/*  658: 872 */     staticConst.markChunkEnd();
/*  659: 874 */     for (int i = 0; i < size; i++)
/*  660:     */     {
/*  661: 875 */       String uri = urisArray[i];
/*  662: 876 */       staticConst.markChunkStart();
/*  663: 877 */       il.append(new GETSTATIC(urisArrayRef));
/*  664: 878 */       il.append(new PUSH(cpg, i));
/*  665: 879 */       il.append(new PUSH(cpg, uri));
/*  666: 880 */       il.append(InstructionConstants.AASTORE);
/*  667: 881 */       staticConst.markChunkEnd();
/*  668:     */     }
/*  669: 884 */     staticConst.markChunkStart();
/*  670: 885 */     il.append(new PUSH(cpg, size));
/*  671: 886 */     il.append(new NEWARRAY(org.apache.bcel.generic.Type.INT));
/*  672: 887 */     int typesArrayRef = cpg.addFieldref(this._className, "_sTypesArray", "[I");
/*  673:     */     
/*  674:     */ 
/*  675: 890 */     il.append(new PUTSTATIC(typesArrayRef));
/*  676: 891 */     staticConst.markChunkEnd();
/*  677: 893 */     for (int i = 0; i < size; i++)
/*  678:     */     {
/*  679: 894 */       int nodeType = typesArray[i];
/*  680: 895 */       staticConst.markChunkStart();
/*  681: 896 */       il.append(new GETSTATIC(typesArrayRef));
/*  682: 897 */       il.append(new PUSH(cpg, i));
/*  683: 898 */       il.append(new PUSH(cpg, nodeType));
/*  684: 899 */       il.append(InstructionConstants.IASTORE);
/*  685: 900 */       staticConst.markChunkEnd();
/*  686:     */     }
/*  687: 904 */     Vector namespaces = getXSLTC().getNamespaceIndex();
/*  688: 905 */     staticConst.markChunkStart();
/*  689: 906 */     il.append(new PUSH(cpg, namespaces.size()));
/*  690: 907 */     il.append(new ANEWARRAY(cpg.addClass("java.lang.String")));
/*  691: 908 */     int namespaceArrayRef = cpg.addFieldref(this._className, "_sNamespaceArray", "[Ljava/lang/String;");
/*  692:     */     
/*  693:     */ 
/*  694: 911 */     il.append(new PUTSTATIC(namespaceArrayRef));
/*  695: 912 */     staticConst.markChunkEnd();
/*  696: 914 */     for (int i = 0; i < namespaces.size(); i++)
/*  697:     */     {
/*  698: 915 */       String ns = (String)namespaces.elementAt(i);
/*  699: 916 */       staticConst.markChunkStart();
/*  700: 917 */       il.append(new GETSTATIC(namespaceArrayRef));
/*  701: 918 */       il.append(new PUSH(cpg, i));
/*  702: 919 */       il.append(new PUSH(cpg, ns));
/*  703: 920 */       il.append(InstructionConstants.AASTORE);
/*  704: 921 */       staticConst.markChunkEnd();
/*  705:     */     }
/*  706: 925 */     Vector namespaceAncestors = getXSLTC().getNSAncestorPointers();
/*  707: 926 */     if ((namespaceAncestors != null) && (namespaceAncestors.size() != 0))
/*  708:     */     {
/*  709: 927 */       addStaticField(classGen, "[I", "_sNamespaceAncestorsArray");
/*  710:     */       
/*  711: 929 */       staticConst.markChunkStart();
/*  712: 930 */       il.append(new PUSH(cpg, namespaceAncestors.size()));
/*  713: 931 */       il.append(new NEWARRAY(org.apache.bcel.generic.Type.INT));
/*  714: 932 */       int namespaceAncestorsArrayRef = cpg.addFieldref(this._className, "_sNamespaceAncestorsArray", "[I");
/*  715:     */       
/*  716:     */ 
/*  717: 935 */       il.append(new PUTSTATIC(namespaceAncestorsArrayRef));
/*  718: 936 */       staticConst.markChunkEnd();
/*  719: 937 */       for (int i = 0; i < namespaceAncestors.size(); i++)
/*  720:     */       {
/*  721: 938 */         int ancestor = ((Integer)namespaceAncestors.get(i)).intValue();
/*  722: 939 */         staticConst.markChunkStart();
/*  723: 940 */         il.append(new GETSTATIC(namespaceAncestorsArrayRef));
/*  724: 941 */         il.append(new PUSH(cpg, i));
/*  725: 942 */         il.append(new PUSH(cpg, ancestor));
/*  726: 943 */         il.append(InstructionConstants.IASTORE);
/*  727: 944 */         staticConst.markChunkEnd();
/*  728:     */       }
/*  729:     */     }
/*  730: 949 */     Vector prefixURIPairsIdx = getXSLTC().getPrefixURIPairsIdx();
/*  731: 950 */     if ((prefixURIPairsIdx != null) && (prefixURIPairsIdx.size() != 0))
/*  732:     */     {
/*  733: 951 */       addStaticField(classGen, "[I", "_sPrefixURIsIdxArray");
/*  734:     */       
/*  735: 953 */       staticConst.markChunkStart();
/*  736: 954 */       il.append(new PUSH(cpg, prefixURIPairsIdx.size()));
/*  737: 955 */       il.append(new NEWARRAY(org.apache.bcel.generic.Type.INT));
/*  738: 956 */       int prefixURIPairsIdxArrayRef = cpg.addFieldref(this._className, "_sPrefixURIsIdxArray", "[I");
/*  739:     */       
/*  740:     */ 
/*  741:     */ 
/*  742: 960 */       il.append(new PUTSTATIC(prefixURIPairsIdxArrayRef));
/*  743: 961 */       staticConst.markChunkEnd();
/*  744: 962 */       for (int i = 0; i < prefixURIPairsIdx.size(); i++)
/*  745:     */       {
/*  746: 963 */         int idx = ((Integer)prefixURIPairsIdx.get(i)).intValue();
/*  747: 964 */         staticConst.markChunkStart();
/*  748: 965 */         il.append(new GETSTATIC(prefixURIPairsIdxArrayRef));
/*  749: 966 */         il.append(new PUSH(cpg, i));
/*  750: 967 */         il.append(new PUSH(cpg, idx));
/*  751: 968 */         il.append(InstructionConstants.IASTORE);
/*  752: 969 */         staticConst.markChunkEnd();
/*  753:     */       }
/*  754:     */     }
/*  755: 975 */     Vector prefixURIPairs = getXSLTC().getPrefixURIPairs();
/*  756: 976 */     if ((prefixURIPairs != null) && (prefixURIPairs.size() != 0))
/*  757:     */     {
/*  758: 977 */       addStaticField(classGen, "[Ljava/lang/String;", "_sPrefixURIPairsArray");
/*  759:     */       
/*  760:     */ 
/*  761: 980 */       staticConst.markChunkStart();
/*  762: 981 */       il.append(new PUSH(cpg, prefixURIPairs.size()));
/*  763: 982 */       il.append(new ANEWARRAY(cpg.addClass("java.lang.String")));
/*  764: 983 */       int prefixURIPairsRef = cpg.addFieldref(this._className, "_sPrefixURIPairsArray", "[Ljava/lang/String;");
/*  765:     */       
/*  766:     */ 
/*  767:     */ 
/*  768: 987 */       il.append(new PUTSTATIC(prefixURIPairsRef));
/*  769: 988 */       staticConst.markChunkEnd();
/*  770: 989 */       for (int i = 0; i < prefixURIPairs.size(); i++)
/*  771:     */       {
/*  772: 990 */         String prefixOrURI = (String)prefixURIPairs.get(i);
/*  773: 991 */         staticConst.markChunkStart();
/*  774: 992 */         il.append(new GETSTATIC(prefixURIPairsRef));
/*  775: 993 */         il.append(new PUSH(cpg, i));
/*  776: 994 */         il.append(new PUSH(cpg, prefixOrURI));
/*  777: 995 */         il.append(InstructionConstants.AASTORE);
/*  778: 996 */         staticConst.markChunkEnd();
/*  779:     */       }
/*  780:     */     }
/*  781:1001 */     int charDataCount = getXSLTC().getCharacterDataCount();
/*  782:1002 */     int toCharArray = cpg.addMethodref("java.lang.String", "toCharArray", "()[C");
/*  783:1003 */     for (int i = 0; i < charDataCount; i++)
/*  784:     */     {
/*  785:1004 */       staticConst.markChunkStart();
/*  786:1005 */       il.append(new PUSH(cpg, getXSLTC().getCharacterData(i)));
/*  787:1006 */       il.append(new INVOKEVIRTUAL(toCharArray));
/*  788:1007 */       il.append(new PUTSTATIC(cpg.addFieldref(this._className, "_scharData" + i, "[C")));
/*  789:     */       
/*  790:     */ 
/*  791:1010 */       staticConst.markChunkEnd();
/*  792:     */     }
/*  793:1013 */     il.append(InstructionConstants.RETURN);
/*  794:     */     
/*  795:1015 */     classGen.addMethod(staticConst);
/*  796:     */   }
/*  797:     */   
/*  798:     */   private void compileConstructor(ClassGenerator classGen, Output output)
/*  799:     */   {
/*  800:1024 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*  801:1025 */     InstructionList il = new InstructionList();
/*  802:     */     
/*  803:1027 */     MethodGenerator constructor = new MethodGenerator(1, org.apache.bcel.generic.Type.VOID, null, null, "<init>", this._className, il, cpg);
/*  804:     */     
/*  805:     */ 
/*  806:     */ 
/*  807:     */ 
/*  808:     */ 
/*  809:     */ 
/*  810:1034 */     il.append(classGen.loadTranslet());
/*  811:1035 */     il.append(new INVOKESPECIAL(cpg.addMethodref("org.apache.xalan.xsltc.runtime.AbstractTranslet", "<init>", "()V")));
/*  812:     */     
/*  813:     */ 
/*  814:1038 */     constructor.markChunkStart();
/*  815:1039 */     il.append(classGen.loadTranslet());
/*  816:1040 */     il.append(new GETSTATIC(cpg.addFieldref(this._className, "_sNamesArray", "[Ljava/lang/String;")));
/*  817:     */     
/*  818:     */ 
/*  819:1043 */     il.append(new PUTFIELD(cpg.addFieldref("org.apache.xalan.xsltc.runtime.AbstractTranslet", "namesArray", "[Ljava/lang/String;")));
/*  820:     */     
/*  821:     */ 
/*  822:1046 */     constructor.markChunkEnd();
/*  823:     */     
/*  824:1048 */     constructor.markChunkStart();
/*  825:1049 */     il.append(classGen.loadTranslet());
/*  826:1050 */     il.append(new GETSTATIC(cpg.addFieldref(this._className, "_sUrisArray", "[Ljava/lang/String;")));
/*  827:     */     
/*  828:     */ 
/*  829:1053 */     il.append(new PUTFIELD(cpg.addFieldref("org.apache.xalan.xsltc.runtime.AbstractTranslet", "urisArray", "[Ljava/lang/String;")));
/*  830:     */     
/*  831:     */ 
/*  832:1056 */     constructor.markChunkEnd();
/*  833:     */     
/*  834:1058 */     constructor.markChunkStart();
/*  835:1059 */     il.append(classGen.loadTranslet());
/*  836:1060 */     il.append(new GETSTATIC(cpg.addFieldref(this._className, "_sTypesArray", "[I")));
/*  837:     */     
/*  838:     */ 
/*  839:1063 */     il.append(new PUTFIELD(cpg.addFieldref("org.apache.xalan.xsltc.runtime.AbstractTranslet", "typesArray", "[I")));
/*  840:     */     
/*  841:     */ 
/*  842:1066 */     constructor.markChunkEnd();
/*  843:     */     
/*  844:1068 */     constructor.markChunkStart();
/*  845:1069 */     il.append(classGen.loadTranslet());
/*  846:1070 */     il.append(new GETSTATIC(cpg.addFieldref(this._className, "_sNamespaceArray", "[Ljava/lang/String;")));
/*  847:     */     
/*  848:     */ 
/*  849:1073 */     il.append(new PUTFIELD(cpg.addFieldref("org.apache.xalan.xsltc.runtime.AbstractTranslet", "namespaceArray", "[Ljava/lang/String;")));
/*  850:     */     
/*  851:     */ 
/*  852:1076 */     constructor.markChunkEnd();
/*  853:     */     
/*  854:1078 */     constructor.markChunkStart();
/*  855:1079 */     il.append(classGen.loadTranslet());
/*  856:1080 */     il.append(new PUSH(cpg, 101));
/*  857:1081 */     il.append(new PUTFIELD(cpg.addFieldref("org.apache.xalan.xsltc.runtime.AbstractTranslet", "transletVersion", "I")));
/*  858:     */     
/*  859:     */ 
/*  860:1084 */     constructor.markChunkEnd();
/*  861:1086 */     if (this._hasIdCall)
/*  862:     */     {
/*  863:1087 */       constructor.markChunkStart();
/*  864:1088 */       il.append(classGen.loadTranslet());
/*  865:1089 */       il.append(new PUSH(cpg, Boolean.TRUE));
/*  866:1090 */       il.append(new PUTFIELD(cpg.addFieldref("org.apache.xalan.xsltc.runtime.AbstractTranslet", "_hasIdCall", "Z")));
/*  867:     */       
/*  868:     */ 
/*  869:1093 */       constructor.markChunkEnd();
/*  870:     */     }
/*  871:1097 */     if (output != null)
/*  872:     */     {
/*  873:1099 */       constructor.markChunkStart();
/*  874:1100 */       output.translate(classGen, constructor);
/*  875:1101 */       constructor.markChunkEnd();
/*  876:     */     }
/*  877:1106 */     if (this._numberFormattingUsed)
/*  878:     */     {
/*  879:1107 */       constructor.markChunkStart();
/*  880:1108 */       DecimalFormatting.translateDefaultDFS(classGen, constructor);
/*  881:1109 */       constructor.markChunkEnd();
/*  882:     */     }
/*  883:1112 */     il.append(InstructionConstants.RETURN);
/*  884:     */     
/*  885:1114 */     classGen.addMethod(constructor);
/*  886:     */   }
/*  887:     */   
/*  888:     */   private String compileTopLevel(ClassGenerator classGen)
/*  889:     */   {
/*  890:1130 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*  891:     */     
/*  892:1132 */     org.apache.bcel.generic.Type[] argTypes = { Util.getJCRefType("Lorg/apache/xalan/xsltc/DOM;"), Util.getJCRefType("Lorg/apache/xml/dtm/DTMAxisIterator;"), Util.getJCRefType(Constants.TRANSLET_OUTPUT_SIG) };
/*  893:     */     
/*  894:     */ 
/*  895:     */ 
/*  896:     */ 
/*  897:     */ 
/*  898:1138 */     String[] argNames = { "document", "iterator", "handler" };
/*  899:     */     
/*  900:     */ 
/*  901:     */ 
/*  902:1142 */     InstructionList il = new InstructionList();
/*  903:     */     
/*  904:1144 */     MethodGenerator toplevel = new MethodGenerator(1, org.apache.bcel.generic.Type.VOID, argTypes, argNames, "topLevel", this._className, il, classGen.getConstantPool());
/*  905:     */     
/*  906:     */ 
/*  907:     */ 
/*  908:     */ 
/*  909:     */ 
/*  910:     */ 
/*  911:1151 */     toplevel.addException("org.apache.xalan.xsltc.TransletException");
/*  912:     */     
/*  913:     */ 
/*  914:1154 */     LocalVariableGen current = toplevel.addLocalVariable("current", org.apache.bcel.generic.Type.INT, null, null);
/*  915:     */     
/*  916:     */ 
/*  917:     */ 
/*  918:     */ 
/*  919:1159 */     int setFilter = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "setFilter", "(Lorg/apache/xalan/xsltc/StripFilter;)V");
/*  920:     */     
/*  921:     */ 
/*  922:     */ 
/*  923:1163 */     int gitr = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "getIterator", "()Lorg/apache/xml/dtm/DTMAxisIterator;");
/*  924:     */     
/*  925:     */ 
/*  926:1166 */     il.append(toplevel.loadDOM());
/*  927:1167 */     il.append(new INVOKEINTERFACE(gitr, 1));
/*  928:1168 */     il.append(toplevel.nextNode());
/*  929:1169 */     current.setStart(il.append(new ISTORE(current.getIndex())));
/*  930:     */     
/*  931:     */ 
/*  932:1172 */     Vector varDepElements = new Vector(this._globals);
/*  933:1173 */     Enumeration elements = elements();
/*  934:1174 */     while (elements.hasMoreElements())
/*  935:     */     {
/*  936:1175 */       Object element = elements.nextElement();
/*  937:1176 */       if ((element instanceof Key)) {
/*  938:1177 */         varDepElements.add(element);
/*  939:     */       }
/*  940:     */     }
/*  941:1182 */     varDepElements = resolveDependencies(varDepElements);
/*  942:     */     
/*  943:     */ 
/*  944:1185 */     int count = varDepElements.size();
/*  945:1186 */     for (int i = 0; i < count; i++)
/*  946:     */     {
/*  947:1187 */       TopLevelElement tle = (TopLevelElement)varDepElements.elementAt(i);
/*  948:1188 */       tle.translate(classGen, toplevel);
/*  949:1189 */       if ((tle instanceof Key))
/*  950:     */       {
/*  951:1190 */         Key key = (Key)tle;
/*  952:1191 */         this._keys.put(key.getName(), key);
/*  953:     */       }
/*  954:     */     }
/*  955:1196 */     Vector whitespaceRules = new Vector();
/*  956:1197 */     elements = elements();
/*  957:1198 */     while (elements.hasMoreElements())
/*  958:     */     {
/*  959:1199 */       Object element = elements.nextElement();
/*  960:1201 */       if ((element instanceof DecimalFormatting)) {
/*  961:1202 */         ((DecimalFormatting)element).translate(classGen, toplevel);
/*  962:1205 */       } else if ((element instanceof Whitespace)) {
/*  963:1206 */         whitespaceRules.addAll(((Whitespace)element).getRules());
/*  964:     */       }
/*  965:     */     }
/*  966:1211 */     if (whitespaceRules.size() > 0) {
/*  967:1212 */       Whitespace.translateRules(whitespaceRules, classGen);
/*  968:     */     }
/*  969:1215 */     if (classGen.containsMethod("stripSpace", "(Lorg/apache/xalan/xsltc/DOM;II)Z") != null)
/*  970:     */     {
/*  971:1216 */       il.append(toplevel.loadDOM());
/*  972:1217 */       il.append(classGen.loadTranslet());
/*  973:1218 */       il.append(new INVOKEINTERFACE(setFilter, 2));
/*  974:     */     }
/*  975:1221 */     il.append(InstructionConstants.RETURN);
/*  976:     */     
/*  977:     */ 
/*  978:1224 */     classGen.addMethod(toplevel);
/*  979:     */     
/*  980:1226 */     return "(Lorg/apache/xalan/xsltc/DOM;Lorg/apache/xml/dtm/DTMAxisIterator;" + Constants.TRANSLET_OUTPUT_SIG + ")V";
/*  981:     */   }
/*  982:     */   
/*  983:     */   private Vector resolveDependencies(Vector input)
/*  984:     */   {
/*  985:1250 */     Vector result = new Vector();
/*  986:1251 */     while (input.size() > 0)
/*  987:     */     {
/*  988:1252 */       boolean changed = false;
/*  989:1253 */       for (int i = 0; i < input.size();)
/*  990:     */       {
/*  991:1254 */         TopLevelElement vde = (TopLevelElement)input.elementAt(i);
/*  992:1255 */         Vector dep = vde.getDependencies();
/*  993:1256 */         if ((dep == null) || (result.containsAll(dep)))
/*  994:     */         {
/*  995:1257 */           result.addElement(vde);
/*  996:1258 */           input.remove(i);
/*  997:1259 */           changed = true;
/*  998:     */         }
/*  999:     */         else
/* 1000:     */         {
/* 1001:1262 */           i++;
/* 1002:     */         }
/* 1003:     */       }
/* 1004:1267 */       if (!changed)
/* 1005:     */       {
/* 1006:1268 */         ErrorMsg err = new ErrorMsg("CIRCULAR_VARIABLE_ERR", input.toString(), this);
/* 1007:     */         
/* 1008:1270 */         getParser().reportError(3, err);
/* 1009:1271 */         return result;
/* 1010:     */       }
/* 1011:     */     }
/* 1012:1283 */     return result;
/* 1013:     */   }
/* 1014:     */   
/* 1015:     */   private String compileBuildKeys(ClassGenerator classGen)
/* 1016:     */   {
/* 1017:1293 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 1018:     */     
/* 1019:1295 */     org.apache.bcel.generic.Type[] argTypes = { Util.getJCRefType("Lorg/apache/xalan/xsltc/DOM;"), Util.getJCRefType("Lorg/apache/xml/dtm/DTMAxisIterator;"), Util.getJCRefType(Constants.TRANSLET_OUTPUT_SIG), org.apache.bcel.generic.Type.INT };
/* 1020:     */     
/* 1021:     */ 
/* 1022:     */ 
/* 1023:     */ 
/* 1024:     */ 
/* 1025:     */ 
/* 1026:1302 */     String[] argNames = { "document", "iterator", "handler", "current" };
/* 1027:     */     
/* 1028:     */ 
/* 1029:     */ 
/* 1030:1306 */     InstructionList il = new InstructionList();
/* 1031:     */     
/* 1032:1308 */     MethodGenerator buildKeys = new MethodGenerator(1, org.apache.bcel.generic.Type.VOID, argTypes, argNames, "buildKeys", this._className, il, classGen.getConstantPool());
/* 1033:     */     
/* 1034:     */ 
/* 1035:     */ 
/* 1036:     */ 
/* 1037:     */ 
/* 1038:     */ 
/* 1039:1315 */     buildKeys.addException("org.apache.xalan.xsltc.TransletException");
/* 1040:     */     
/* 1041:1317 */     Enumeration elements = elements();
/* 1042:1318 */     while (elements.hasMoreElements())
/* 1043:     */     {
/* 1044:1320 */       Object element = elements.nextElement();
/* 1045:1321 */       if ((element instanceof Key))
/* 1046:     */       {
/* 1047:1322 */         Key key = (Key)element;
/* 1048:1323 */         key.translate(classGen, buildKeys);
/* 1049:1324 */         this._keys.put(key.getName(), key);
/* 1050:     */       }
/* 1051:     */     }
/* 1052:1328 */     il.append(InstructionConstants.RETURN);
/* 1053:     */     
/* 1054:     */ 
/* 1055:1331 */     classGen.addMethod(buildKeys);
/* 1056:     */     
/* 1057:1333 */     return "(Lorg/apache/xalan/xsltc/DOM;Lorg/apache/xml/dtm/DTMAxisIterator;" + Constants.TRANSLET_OUTPUT_SIG + "I)V";
/* 1058:     */   }
/* 1059:     */   
/* 1060:     */   private void compileTransform(ClassGenerator classGen)
/* 1061:     */   {
/* 1062:1342 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 1063:     */     
/* 1064:     */ 
/* 1065:     */ 
/* 1066:     */ 
/* 1067:     */ 
/* 1068:1348 */     org.apache.bcel.generic.Type[] argTypes = new org.apache.bcel.generic.Type[3];
/* 1069:     */     
/* 1070:1350 */     argTypes[0] = Util.getJCRefType("Lorg/apache/xalan/xsltc/DOM;");
/* 1071:1351 */     argTypes[1] = Util.getJCRefType("Lorg/apache/xml/dtm/DTMAxisIterator;");
/* 1072:1352 */     argTypes[2] = Util.getJCRefType(Constants.TRANSLET_OUTPUT_SIG);
/* 1073:     */     
/* 1074:1354 */     String[] argNames = new String[3];
/* 1075:1355 */     argNames[0] = "document";
/* 1076:1356 */     argNames[1] = "iterator";
/* 1077:1357 */     argNames[2] = "handler";
/* 1078:     */     
/* 1079:1359 */     InstructionList il = new InstructionList();
/* 1080:1360 */     MethodGenerator transf = new MethodGenerator(1, org.apache.bcel.generic.Type.VOID, argTypes, argNames, "transform", this._className, il, classGen.getConstantPool());
/* 1081:     */     
/* 1082:     */ 
/* 1083:     */ 
/* 1084:     */ 
/* 1085:     */ 
/* 1086:     */ 
/* 1087:     */ 
/* 1088:1368 */     transf.addException("org.apache.xalan.xsltc.TransletException");
/* 1089:     */     
/* 1090:     */ 
/* 1091:1371 */     LocalVariableGen current = transf.addLocalVariable("current", org.apache.bcel.generic.Type.INT, null, null);
/* 1092:     */     
/* 1093:     */ 
/* 1094:     */ 
/* 1095:1375 */     String applyTemplatesSig = classGen.getApplyTemplatesSig();
/* 1096:1376 */     int applyTemplates = cpg.addMethodref(getClassName(), "applyTemplates", applyTemplatesSig);
/* 1097:     */     
/* 1098:     */ 
/* 1099:1379 */     int domField = cpg.addFieldref(getClassName(), "_dom", "Lorg/apache/xalan/xsltc/DOM;");
/* 1100:     */     
/* 1101:     */ 
/* 1102:     */ 
/* 1103:     */ 
/* 1104:1384 */     il.append(classGen.loadTranslet());
/* 1105:1387 */     if (isMultiDocument())
/* 1106:     */     {
/* 1107:1388 */       il.append(new NEW(cpg.addClass("org.apache.xalan.xsltc.dom.MultiDOM")));
/* 1108:1389 */       il.append(InstructionConstants.DUP);
/* 1109:     */     }
/* 1110:1392 */     il.append(classGen.loadTranslet());
/* 1111:1393 */     il.append(transf.loadDOM());
/* 1112:1394 */     il.append(new INVOKEVIRTUAL(cpg.addMethodref("org.apache.xalan.xsltc.runtime.AbstractTranslet", "makeDOMAdapter", "(Lorg/apache/xalan/xsltc/DOM;)Lorg/apache/xalan/xsltc/dom/DOMAdapter;")));
/* 1113:1400 */     if (isMultiDocument())
/* 1114:     */     {
/* 1115:1401 */       int init = cpg.addMethodref("org.apache.xalan.xsltc.dom.MultiDOM", "<init>", "(Lorg/apache/xalan/xsltc/DOM;)V");
/* 1116:     */       
/* 1117:     */ 
/* 1118:1404 */       il.append(new INVOKESPECIAL(init));
/* 1119:     */     }
/* 1120:1409 */     il.append(new PUTFIELD(domField));
/* 1121:     */     
/* 1122:     */ 
/* 1123:1412 */     int gitr = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "getIterator", "()Lorg/apache/xml/dtm/DTMAxisIterator;");
/* 1124:     */     
/* 1125:     */ 
/* 1126:1415 */     il.append(transf.loadDOM());
/* 1127:1416 */     il.append(new INVOKEINTERFACE(gitr, 1));
/* 1128:1417 */     il.append(transf.nextNode());
/* 1129:1418 */     current.setStart(il.append(new ISTORE(current.getIndex())));
/* 1130:     */     
/* 1131:     */ 
/* 1132:1421 */     il.append(classGen.loadTranslet());
/* 1133:1422 */     il.append(transf.loadHandler());
/* 1134:1423 */     int index = cpg.addMethodref("org.apache.xalan.xsltc.runtime.AbstractTranslet", "transferOutputSettings", "(" + Constants.OUTPUT_HANDLER_SIG + ")V");
/* 1135:     */     
/* 1136:     */ 
/* 1137:1426 */     il.append(new INVOKEVIRTUAL(index));
/* 1138:     */     
/* 1139:     */ 
/* 1140:     */ 
/* 1141:     */ 
/* 1142:     */ 
/* 1143:     */ 
/* 1144:     */ 
/* 1145:1434 */     String keySig = compileBuildKeys(classGen);
/* 1146:1435 */     int keyIdx = cpg.addMethodref(getClassName(), "buildKeys", keySig);
/* 1147:     */     
/* 1148:     */ 
/* 1149:     */ 
/* 1150:1439 */     Enumeration toplevel = elements();
/* 1151:1440 */     if ((this._globals.size() > 0) || (toplevel.hasMoreElements()))
/* 1152:     */     {
/* 1153:1442 */       String topLevelSig = compileTopLevel(classGen);
/* 1154:     */       
/* 1155:1444 */       int topLevelIdx = cpg.addMethodref(getClassName(), "topLevel", topLevelSig);
/* 1156:     */       
/* 1157:     */ 
/* 1158:     */ 
/* 1159:1448 */       il.append(classGen.loadTranslet());
/* 1160:1449 */       il.append(classGen.loadTranslet());
/* 1161:1450 */       il.append(new GETFIELD(domField));
/* 1162:1451 */       il.append(transf.loadIterator());
/* 1163:1452 */       il.append(transf.loadHandler());
/* 1164:1453 */       il.append(new INVOKEVIRTUAL(topLevelIdx));
/* 1165:     */     }
/* 1166:1457 */     il.append(transf.loadHandler());
/* 1167:1458 */     il.append(transf.startDocument());
/* 1168:     */     
/* 1169:     */ 
/* 1170:1461 */     il.append(classGen.loadTranslet());
/* 1171:     */     
/* 1172:1463 */     il.append(classGen.loadTranslet());
/* 1173:1464 */     il.append(new GETFIELD(domField));
/* 1174:     */     
/* 1175:1466 */     il.append(transf.loadIterator());
/* 1176:1467 */     il.append(transf.loadHandler());
/* 1177:1468 */     il.append(new INVOKEVIRTUAL(applyTemplates));
/* 1178:     */     
/* 1179:1470 */     il.append(transf.loadHandler());
/* 1180:1471 */     il.append(transf.endDocument());
/* 1181:     */     
/* 1182:1473 */     il.append(InstructionConstants.RETURN);
/* 1183:     */     
/* 1184:     */ 
/* 1185:1476 */     classGen.addMethod(transf);
/* 1186:     */   }
/* 1187:     */   
/* 1188:     */   private void peepHoleOptimization(MethodGenerator methodGen)
/* 1189:     */   {
/* 1190:1483 */     String pattern = "`aload'`pop'`instruction'";
/* 1191:1484 */     InstructionList il = methodGen.getInstructionList();
/* 1192:1485 */     InstructionFinder find = new InstructionFinder(il);
/* 1193:1486 */     for (Iterator iter = find.search("`aload'`pop'`instruction'"); iter.hasNext();)
/* 1194:     */     {
/* 1195:1487 */       InstructionHandle[] match = (InstructionHandle[])iter.next();
/* 1196:     */       try
/* 1197:     */       {
/* 1198:1489 */         il.delete(match[0], match[1]);
/* 1199:     */       }
/* 1200:     */       catch (TargetLostException e) {}
/* 1201:     */     }
/* 1202:     */   }
/* 1203:     */   
/* 1204:     */   public int addParam(Param param)
/* 1205:     */   {
/* 1206:1498 */     this._globals.addElement(param);
/* 1207:1499 */     return this._globals.size() - 1;
/* 1208:     */   }
/* 1209:     */   
/* 1210:     */   public int addVariable(Variable global)
/* 1211:     */   {
/* 1212:1503 */     this._globals.addElement(global);
/* 1213:1504 */     return this._globals.size() - 1;
/* 1214:     */   }
/* 1215:     */   
/* 1216:     */   public void display(int indent)
/* 1217:     */   {
/* 1218:1508 */     indent(indent);
/* 1219:1509 */     Util.println("Stylesheet");
/* 1220:1510 */     displayContents(indent + 4);
/* 1221:     */   }
/* 1222:     */   
/* 1223:     */   public String getNamespace(String prefix)
/* 1224:     */   {
/* 1225:1515 */     return lookupNamespace(prefix);
/* 1226:     */   }
/* 1227:     */   
/* 1228:     */   public String getClassName()
/* 1229:     */   {
/* 1230:1519 */     return this._className;
/* 1231:     */   }
/* 1232:     */   
/* 1233:     */   public Vector getTemplates()
/* 1234:     */   {
/* 1235:1523 */     return this._templates;
/* 1236:     */   }
/* 1237:     */   
/* 1238:     */   public Vector getAllValidTemplates()
/* 1239:     */   {
/* 1240:1528 */     if (this._includedStylesheets == null) {
/* 1241:1529 */       return this._templates;
/* 1242:     */     }
/* 1243:1533 */     if (this._allValidTemplates == null)
/* 1244:     */     {
/* 1245:1534 */       Vector templates = new Vector();
/* 1246:1535 */       int size = this._includedStylesheets.size();
/* 1247:1536 */       for (int i = 0; i < size; i++)
/* 1248:     */       {
/* 1249:1537 */         Stylesheet included = (Stylesheet)this._includedStylesheets.elementAt(i);
/* 1250:1538 */         templates.addAll(included.getAllValidTemplates());
/* 1251:     */       }
/* 1252:1540 */       templates.addAll(this._templates);
/* 1253:1543 */       if (this._parentStylesheet != null) {
/* 1254:1544 */         return templates;
/* 1255:     */       }
/* 1256:1546 */       this._allValidTemplates = templates;
/* 1257:     */     }
/* 1258:1549 */     return this._allValidTemplates;
/* 1259:     */   }
/* 1260:     */   
/* 1261:     */   protected void addTemplate(Template template)
/* 1262:     */   {
/* 1263:1553 */     this._templates.addElement(template);
/* 1264:     */   }
/* 1265:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.Stylesheet
 * JD-Core Version:    0.7.0.1
 */