/*    1:     */ package org.apache.xalan.xsltc.compiler;
/*    2:     */ 
/*    3:     */ import java.io.File;
/*    4:     */ import java.io.IOException;
/*    5:     */ import java.io.PrintStream;
/*    6:     */ import java.io.StringReader;
/*    7:     */ import java.util.Dictionary;
/*    8:     */ import java.util.Enumeration;
/*    9:     */ import java.util.Hashtable;
/*   10:     */ import java.util.Properties;
/*   11:     */ import java.util.Stack;
/*   12:     */ import java.util.StringTokenizer;
/*   13:     */ import java.util.Vector;
/*   14:     */ import java_cup.runtime.Symbol;
/*   15:     */ import java_cup.runtime.lr_parser;
/*   16:     */ import javax.xml.parsers.ParserConfigurationException;
/*   17:     */ import javax.xml.parsers.SAXParser;
/*   18:     */ import javax.xml.parsers.SAXParserFactory;
/*   19:     */ import org.apache.xalan.xsltc.compiler.util.ErrorMsg;
/*   20:     */ import org.apache.xalan.xsltc.compiler.util.MethodType;
/*   21:     */ import org.apache.xalan.xsltc.compiler.util.Type;
/*   22:     */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*   23:     */ import org.apache.xalan.xsltc.runtime.AttributeList;
/*   24:     */ import org.xml.sax.Attributes;
/*   25:     */ import org.xml.sax.ContentHandler;
/*   26:     */ import org.xml.sax.InputSource;
/*   27:     */ import org.xml.sax.Locator;
/*   28:     */ import org.xml.sax.SAXException;
/*   29:     */ import org.xml.sax.SAXParseException;
/*   30:     */ import org.xml.sax.XMLReader;
/*   31:     */ 
/*   32:     */ public class Parser
/*   33:     */   implements Constants, ContentHandler
/*   34:     */ {
/*   35:     */   private static final String XSL = "xsl";
/*   36:     */   private static final String TRANSLET = "translet";
/*   37:  66 */   private Locator _locator = null;
/*   38:     */   private XSLTC _xsltc;
/*   39:     */   private XPathParser _xpathParser;
/*   40:     */   private Vector _errors;
/*   41:     */   private Vector _warnings;
/*   42:     */   private Hashtable _instructionClasses;
/*   43:     */   private Hashtable _instructionAttrs;
/*   44:     */   private Hashtable _qNames;
/*   45:     */   private Hashtable _namespaces;
/*   46:     */   private QName _useAttributeSets;
/*   47:     */   private QName _excludeResultPrefixes;
/*   48:     */   private QName _extensionElementPrefixes;
/*   49:     */   private Hashtable _variableScope;
/*   50:     */   private Stylesheet _currentStylesheet;
/*   51:     */   private SymbolTable _symbolTable;
/*   52:     */   private Output _output;
/*   53:     */   private Template _template;
/*   54:     */   private boolean _rootNamespaceDef;
/*   55:     */   private SyntaxTreeNode _root;
/*   56:     */   private String _target;
/*   57:     */   private int _currentImportPrecedence;
/*   58:     */   
/*   59:     */   public Parser(XSLTC xsltc)
/*   60:     */   {
/*   61:  95 */     this._xsltc = xsltc;
/*   62:     */   }
/*   63:     */   
/*   64:     */   public void init()
/*   65:     */   {
/*   66:  99 */     this._qNames = new Hashtable(512);
/*   67: 100 */     this._namespaces = new Hashtable();
/*   68: 101 */     this._instructionClasses = new Hashtable();
/*   69: 102 */     this._instructionAttrs = new Hashtable();
/*   70: 103 */     this._variableScope = new Hashtable();
/*   71: 104 */     this._template = null;
/*   72: 105 */     this._errors = new Vector();
/*   73: 106 */     this._warnings = new Vector();
/*   74: 107 */     this._symbolTable = new SymbolTable();
/*   75: 108 */     this._xpathParser = new XPathParser(this);
/*   76: 109 */     this._currentStylesheet = null;
/*   77: 110 */     this._output = null;
/*   78: 111 */     this._root = null;
/*   79: 112 */     this._rootNamespaceDef = false;
/*   80: 113 */     this._currentImportPrecedence = 1;
/*   81:     */     
/*   82: 115 */     initStdClasses();
/*   83: 116 */     initInstructionAttrs();
/*   84: 117 */     initExtClasses();
/*   85: 118 */     initSymbolTable();
/*   86:     */     
/*   87: 120 */     this._useAttributeSets = getQName("http://www.w3.org/1999/XSL/Transform", "xsl", "use-attribute-sets");
/*   88:     */     
/*   89: 122 */     this._excludeResultPrefixes = getQName("http://www.w3.org/1999/XSL/Transform", "xsl", "exclude-result-prefixes");
/*   90:     */     
/*   91: 124 */     this._extensionElementPrefixes = getQName("http://www.w3.org/1999/XSL/Transform", "xsl", "extension-element-prefixes");
/*   92:     */   }
/*   93:     */   
/*   94:     */   public void setOutput(Output output)
/*   95:     */   {
/*   96: 129 */     if (this._output != null)
/*   97:     */     {
/*   98: 130 */       if (this._output.getImportPrecedence() <= output.getImportPrecedence())
/*   99:     */       {
/*  100: 131 */         String cdata = this._output.getCdata();
/*  101: 132 */         output.mergeOutput(this._output);
/*  102: 133 */         this._output.disable();
/*  103: 134 */         this._output = output;
/*  104:     */       }
/*  105:     */       else
/*  106:     */       {
/*  107: 137 */         output.disable();
/*  108:     */       }
/*  109:     */     }
/*  110:     */     else {
/*  111: 141 */       this._output = output;
/*  112:     */     }
/*  113:     */   }
/*  114:     */   
/*  115:     */   public Output getOutput()
/*  116:     */   {
/*  117: 146 */     return this._output;
/*  118:     */   }
/*  119:     */   
/*  120:     */   public Properties getOutputProperties()
/*  121:     */   {
/*  122: 150 */     return getTopLevelStylesheet().getOutputProperties();
/*  123:     */   }
/*  124:     */   
/*  125:     */   public void addVariable(Variable var)
/*  126:     */   {
/*  127: 154 */     addVariableOrParam(var);
/*  128:     */   }
/*  129:     */   
/*  130:     */   public void addParameter(Param param)
/*  131:     */   {
/*  132: 158 */     addVariableOrParam(param);
/*  133:     */   }
/*  134:     */   
/*  135:     */   private void addVariableOrParam(VariableBase var)
/*  136:     */   {
/*  137: 162 */     Object existing = this._variableScope.get(var.getName());
/*  138: 163 */     if (existing != null)
/*  139:     */     {
/*  140: 164 */       if ((existing instanceof Stack))
/*  141:     */       {
/*  142: 165 */         Stack stack = (Stack)existing;
/*  143: 166 */         stack.push(var);
/*  144:     */       }
/*  145: 168 */       else if ((existing instanceof VariableBase))
/*  146:     */       {
/*  147: 169 */         Stack stack = new Stack();
/*  148: 170 */         stack.push(existing);
/*  149: 171 */         stack.push(var);
/*  150: 172 */         this._variableScope.put(var.getName(), stack);
/*  151:     */       }
/*  152:     */     }
/*  153:     */     else {
/*  154: 176 */       this._variableScope.put(var.getName(), var);
/*  155:     */     }
/*  156:     */   }
/*  157:     */   
/*  158:     */   public void removeVariable(QName name)
/*  159:     */   {
/*  160: 181 */     Object existing = this._variableScope.get(name);
/*  161: 182 */     if ((existing instanceof Stack))
/*  162:     */     {
/*  163: 183 */       Stack stack = (Stack)existing;
/*  164: 184 */       if (!stack.isEmpty()) {
/*  165: 184 */         stack.pop();
/*  166:     */       }
/*  167: 185 */       if (!stack.isEmpty()) {
/*  168: 185 */         return;
/*  169:     */       }
/*  170:     */     }
/*  171: 187 */     this._variableScope.remove(name);
/*  172:     */   }
/*  173:     */   
/*  174:     */   public VariableBase lookupVariable(QName name)
/*  175:     */   {
/*  176: 191 */     Object existing = this._variableScope.get(name);
/*  177: 192 */     if ((existing instanceof VariableBase)) {
/*  178: 193 */       return (VariableBase)existing;
/*  179:     */     }
/*  180: 195 */     if ((existing instanceof Stack))
/*  181:     */     {
/*  182: 196 */       Stack stack = (Stack)existing;
/*  183: 197 */       return (VariableBase)stack.peek();
/*  184:     */     }
/*  185: 199 */     return null;
/*  186:     */   }
/*  187:     */   
/*  188:     */   public void setXSLTC(XSLTC xsltc)
/*  189:     */   {
/*  190: 203 */     this._xsltc = xsltc;
/*  191:     */   }
/*  192:     */   
/*  193:     */   public XSLTC getXSLTC()
/*  194:     */   {
/*  195: 207 */     return this._xsltc;
/*  196:     */   }
/*  197:     */   
/*  198:     */   public int getCurrentImportPrecedence()
/*  199:     */   {
/*  200: 211 */     return this._currentImportPrecedence;
/*  201:     */   }
/*  202:     */   
/*  203:     */   public int getNextImportPrecedence()
/*  204:     */   {
/*  205: 215 */     return ++this._currentImportPrecedence;
/*  206:     */   }
/*  207:     */   
/*  208:     */   public void setCurrentStylesheet(Stylesheet stylesheet)
/*  209:     */   {
/*  210: 219 */     this._currentStylesheet = stylesheet;
/*  211:     */   }
/*  212:     */   
/*  213:     */   public Stylesheet getCurrentStylesheet()
/*  214:     */   {
/*  215: 223 */     return this._currentStylesheet;
/*  216:     */   }
/*  217:     */   
/*  218:     */   public Stylesheet getTopLevelStylesheet()
/*  219:     */   {
/*  220: 227 */     return this._xsltc.getStylesheet();
/*  221:     */   }
/*  222:     */   
/*  223:     */   public QName getQNameSafe(String stringRep)
/*  224:     */   {
/*  225: 232 */     int colon = stringRep.lastIndexOf(':');
/*  226: 233 */     if (colon != -1)
/*  227:     */     {
/*  228: 234 */       String prefix = stringRep.substring(0, colon);
/*  229: 235 */       String localname = stringRep.substring(colon + 1);
/*  230: 236 */       String namespace = null;
/*  231: 239 */       if (!prefix.equals("xmlns"))
/*  232:     */       {
/*  233: 240 */         namespace = this._symbolTable.lookupNamespace(prefix);
/*  234: 241 */         if (namespace == null) {
/*  235: 241 */           namespace = "";
/*  236:     */         }
/*  237:     */       }
/*  238: 243 */       return getQName(namespace, prefix, localname);
/*  239:     */     }
/*  240: 246 */     String uri = stringRep.equals("xmlns") ? null : this._symbolTable.lookupNamespace("");
/*  241:     */     
/*  242: 248 */     return getQName(uri, null, stringRep);
/*  243:     */   }
/*  244:     */   
/*  245:     */   public QName getQName(String stringRep)
/*  246:     */   {
/*  247: 253 */     return getQName(stringRep, true, false);
/*  248:     */   }
/*  249:     */   
/*  250:     */   public QName getQNameIgnoreDefaultNs(String stringRep)
/*  251:     */   {
/*  252: 257 */     return getQName(stringRep, true, true);
/*  253:     */   }
/*  254:     */   
/*  255:     */   public QName getQName(String stringRep, boolean reportError)
/*  256:     */   {
/*  257: 261 */     return getQName(stringRep, reportError, false);
/*  258:     */   }
/*  259:     */   
/*  260:     */   private QName getQName(String stringRep, boolean reportError, boolean ignoreDefaultNs)
/*  261:     */   {
/*  262: 268 */     int colon = stringRep.lastIndexOf(':');
/*  263: 269 */     if (colon != -1)
/*  264:     */     {
/*  265: 270 */       String prefix = stringRep.substring(0, colon);
/*  266: 271 */       String localname = stringRep.substring(colon + 1);
/*  267: 272 */       String namespace = null;
/*  268: 275 */       if (!prefix.equals("xmlns"))
/*  269:     */       {
/*  270: 276 */         namespace = this._symbolTable.lookupNamespace(prefix);
/*  271: 277 */         if ((namespace == null) && (reportError))
/*  272:     */         {
/*  273: 278 */           int line = getLineNumber();
/*  274: 279 */           ErrorMsg err = new ErrorMsg("NAMESPACE_UNDEF_ERR", line, prefix);
/*  275:     */           
/*  276: 281 */           reportError(3, err);
/*  277:     */         }
/*  278:     */       }
/*  279: 284 */       return getQName(namespace, prefix, localname);
/*  280:     */     }
/*  281: 287 */     if (stringRep.equals("xmlns")) {
/*  282: 288 */       ignoreDefaultNs = true;
/*  283:     */     }
/*  284: 290 */     String defURI = ignoreDefaultNs ? null : this._symbolTable.lookupNamespace("");
/*  285:     */     
/*  286: 292 */     return getQName(defURI, null, stringRep);
/*  287:     */   }
/*  288:     */   
/*  289:     */   public QName getQName(String namespace, String prefix, String localname)
/*  290:     */   {
/*  291: 297 */     if ((namespace == null) || (namespace.equals("")))
/*  292:     */     {
/*  293: 298 */       QName name = (QName)this._qNames.get(localname);
/*  294: 299 */       if (name == null)
/*  295:     */       {
/*  296: 300 */         name = new QName(null, prefix, localname);
/*  297: 301 */         this._qNames.put(localname, name);
/*  298:     */       }
/*  299: 303 */       return name;
/*  300:     */     }
/*  301: 306 */     Dictionary space = (Dictionary)this._namespaces.get(namespace);
/*  302: 307 */     if (space == null)
/*  303:     */     {
/*  304: 308 */       QName name = new QName(namespace, prefix, localname);
/*  305: 309 */       this._namespaces.put(namespace, space = new Hashtable());
/*  306: 310 */       space.put(localname, name);
/*  307: 311 */       return name;
/*  308:     */     }
/*  309: 314 */     QName name = (QName)space.get(localname);
/*  310: 315 */     if (name == null)
/*  311:     */     {
/*  312: 316 */       name = new QName(namespace, prefix, localname);
/*  313: 317 */       space.put(localname, name);
/*  314:     */     }
/*  315: 319 */     return name;
/*  316:     */   }
/*  317:     */   
/*  318:     */   public QName getQName(String scope, String name)
/*  319:     */   {
/*  320: 325 */     return getQName(scope + name);
/*  321:     */   }
/*  322:     */   
/*  323:     */   public QName getQName(QName scope, QName name)
/*  324:     */   {
/*  325: 329 */     return getQName(scope.toString() + name.toString());
/*  326:     */   }
/*  327:     */   
/*  328:     */   public QName getUseAttributeSets()
/*  329:     */   {
/*  330: 333 */     return this._useAttributeSets;
/*  331:     */   }
/*  332:     */   
/*  333:     */   public QName getExtensionElementPrefixes()
/*  334:     */   {
/*  335: 337 */     return this._extensionElementPrefixes;
/*  336:     */   }
/*  337:     */   
/*  338:     */   public QName getExcludeResultPrefixes()
/*  339:     */   {
/*  340: 341 */     return this._excludeResultPrefixes;
/*  341:     */   }
/*  342:     */   
/*  343:     */   public Stylesheet makeStylesheet(SyntaxTreeNode element)
/*  344:     */     throws CompilerException
/*  345:     */   {
/*  346:     */     try
/*  347:     */     {
/*  348:     */       Stylesheet stylesheet;
/*  349: 354 */       if ((element instanceof Stylesheet))
/*  350:     */       {
/*  351: 355 */         stylesheet = (Stylesheet)element;
/*  352:     */       }
/*  353:     */       else
/*  354:     */       {
/*  355: 358 */         stylesheet = new Stylesheet();
/*  356: 359 */         stylesheet.setSimplified();
/*  357: 360 */         stylesheet.addElement(element);
/*  358: 361 */         stylesheet.setAttributes((AttributeList)element.getAttributes());
/*  359: 364 */         if (element.lookupNamespace("") == null) {
/*  360: 365 */           element.addPrefixMapping("", "");
/*  361:     */         }
/*  362:     */       }
/*  363: 368 */       stylesheet.setParser(this);
/*  364: 369 */       return stylesheet;
/*  365:     */     }
/*  366:     */     catch (ClassCastException e)
/*  367:     */     {
/*  368: 372 */       ErrorMsg err = new ErrorMsg("NOT_STYLESHEET_ERR", element);
/*  369: 373 */       throw new CompilerException(err.toString());
/*  370:     */     }
/*  371:     */   }
/*  372:     */   
/*  373:     */   public void createAST(Stylesheet stylesheet)
/*  374:     */   {
/*  375:     */     try
/*  376:     */     {
/*  377: 382 */       if (stylesheet != null)
/*  378:     */       {
/*  379: 383 */         stylesheet.parseContents(this);
/*  380: 384 */         int precedence = stylesheet.getImportPrecedence();
/*  381: 385 */         Enumeration elements = stylesheet.elements();
/*  382: 386 */         while (elements.hasMoreElements())
/*  383:     */         {
/*  384: 387 */           Object child = elements.nextElement();
/*  385: 388 */           if ((child instanceof Text))
/*  386:     */           {
/*  387: 389 */             int l = getLineNumber();
/*  388: 390 */             ErrorMsg err = new ErrorMsg("ILLEGAL_TEXT_NODE_ERR", l, null);
/*  389:     */             
/*  390: 392 */             reportError(3, err);
/*  391:     */           }
/*  392:     */         }
/*  393: 395 */         if (!errorsFound()) {
/*  394: 396 */           stylesheet.typeCheck(this._symbolTable);
/*  395:     */         }
/*  396:     */       }
/*  397:     */     }
/*  398:     */     catch (TypeCheckError e)
/*  399:     */     {
/*  400: 401 */       reportError(3, new ErrorMsg(e));
/*  401:     */     }
/*  402:     */   }
/*  403:     */   
/*  404:     */   public SyntaxTreeNode parse(XMLReader reader, InputSource input)
/*  405:     */   {
/*  406:     */     try
/*  407:     */     {
/*  408: 414 */       reader.setContentHandler(this);
/*  409: 415 */       reader.parse(input);
/*  410:     */       
/*  411: 417 */       return getStylesheet(this._root);
/*  412:     */     }
/*  413:     */     catch (IOException e)
/*  414:     */     {
/*  415: 420 */       if (this._xsltc.debug()) {
/*  416: 420 */         e.printStackTrace();
/*  417:     */       }
/*  418: 421 */       reportError(3, new ErrorMsg(e));
/*  419:     */     }
/*  420:     */     catch (SAXException e)
/*  421:     */     {
/*  422: 424 */       Throwable ex = e.getException();
/*  423: 425 */       if (this._xsltc.debug())
/*  424:     */       {
/*  425: 426 */         e.printStackTrace();
/*  426: 427 */         if (ex != null) {
/*  427: 427 */           ex.printStackTrace();
/*  428:     */         }
/*  429:     */       }
/*  430: 429 */       reportError(3, new ErrorMsg(e));
/*  431:     */     }
/*  432:     */     catch (CompilerException e)
/*  433:     */     {
/*  434: 432 */       if (this._xsltc.debug()) {
/*  435: 432 */         e.printStackTrace();
/*  436:     */       }
/*  437: 433 */       reportError(3, new ErrorMsg(e));
/*  438:     */     }
/*  439:     */     catch (Exception e)
/*  440:     */     {
/*  441: 436 */       if (this._xsltc.debug()) {
/*  442: 436 */         e.printStackTrace();
/*  443:     */       }
/*  444: 437 */       reportError(3, new ErrorMsg(e));
/*  445:     */     }
/*  446: 439 */     return null;
/*  447:     */   }
/*  448:     */   
/*  449:     */   public SyntaxTreeNode parse(InputSource input)
/*  450:     */   {
/*  451:     */     try
/*  452:     */     {
/*  453: 450 */       SAXParserFactory factory = SAXParserFactory.newInstance();
/*  454: 452 */       if (this._xsltc.isSecureProcessing()) {
/*  455:     */         try
/*  456:     */         {
/*  457: 454 */           factory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
/*  458:     */         }
/*  459:     */         catch (SAXException e) {}
/*  460:     */       }
/*  461:     */       try
/*  462:     */       {
/*  463: 460 */         factory.setFeature("http://xml.org/sax/features/namespaces", true);
/*  464:     */       }
/*  465:     */       catch (Exception e)
/*  466:     */       {
/*  467: 463 */         factory.setNamespaceAware(true);
/*  468:     */       }
/*  469: 465 */       SAXParser parser = factory.newSAXParser();
/*  470: 466 */       XMLReader reader = parser.getXMLReader();
/*  471: 467 */       return parse(reader, input);
/*  472:     */     }
/*  473:     */     catch (ParserConfigurationException e)
/*  474:     */     {
/*  475: 470 */       ErrorMsg err = new ErrorMsg("SAX_PARSER_CONFIG_ERR");
/*  476: 471 */       reportError(3, err);
/*  477:     */     }
/*  478:     */     catch (SAXParseException e)
/*  479:     */     {
/*  480: 474 */       reportError(3, new ErrorMsg(e.getMessage(), e.getLineNumber()));
/*  481:     */     }
/*  482:     */     catch (SAXException e)
/*  483:     */     {
/*  484: 477 */       reportError(3, new ErrorMsg(e.getMessage()));
/*  485:     */     }
/*  486: 479 */     return null;
/*  487:     */   }
/*  488:     */   
/*  489:     */   public SyntaxTreeNode getDocumentRoot()
/*  490:     */   {
/*  491: 483 */     return this._root;
/*  492:     */   }
/*  493:     */   
/*  494: 486 */   private String _PImedia = null;
/*  495: 487 */   private String _PItitle = null;
/*  496: 488 */   private String _PIcharset = null;
/*  497:     */   
/*  498:     */   protected void setPIParameters(String media, String title, String charset)
/*  499:     */   {
/*  500: 500 */     this._PImedia = media;
/*  501: 501 */     this._PItitle = title;
/*  502: 502 */     this._PIcharset = charset;
/*  503:     */   }
/*  504:     */   
/*  505:     */   private SyntaxTreeNode getStylesheet(SyntaxTreeNode root)
/*  506:     */     throws CompilerException
/*  507:     */   {
/*  508: 519 */     if (this._target == null)
/*  509:     */     {
/*  510: 520 */       if (!this._rootNamespaceDef)
/*  511:     */       {
/*  512: 521 */         ErrorMsg msg = new ErrorMsg("MISSING_XSLT_URI_ERR");
/*  513: 522 */         throw new CompilerException(msg.toString());
/*  514:     */       }
/*  515: 524 */       return root;
/*  516:     */     }
/*  517: 528 */     if (this._target.charAt(0) == '#')
/*  518:     */     {
/*  519: 529 */       SyntaxTreeNode element = findStylesheet(root, this._target.substring(1));
/*  520: 530 */       if (element == null)
/*  521:     */       {
/*  522: 531 */         ErrorMsg msg = new ErrorMsg("MISSING_XSLT_TARGET_ERR", this._target, root);
/*  523:     */         
/*  524: 533 */         throw new CompilerException(msg.toString());
/*  525:     */       }
/*  526: 535 */       return element;
/*  527:     */     }
/*  528: 538 */     return loadExternalStylesheet(this._target);
/*  529:     */   }
/*  530:     */   
/*  531:     */   private SyntaxTreeNode findStylesheet(SyntaxTreeNode root, String href)
/*  532:     */   {
/*  533: 549 */     if (root == null) {
/*  534: 549 */       return null;
/*  535:     */     }
/*  536: 551 */     if ((root instanceof Stylesheet))
/*  537:     */     {
/*  538: 552 */       String id = root.getAttribute("id");
/*  539: 553 */       if (id.equals(href)) {
/*  540: 553 */         return root;
/*  541:     */       }
/*  542:     */     }
/*  543: 555 */     Vector children = root.getContents();
/*  544: 556 */     if (children != null)
/*  545:     */     {
/*  546: 557 */       int count = children.size();
/*  547: 558 */       for (int i = 0; i < count; i++)
/*  548:     */       {
/*  549: 559 */         SyntaxTreeNode child = (SyntaxTreeNode)children.elementAt(i);
/*  550: 560 */         SyntaxTreeNode node = findStylesheet(child, href);
/*  551: 561 */         if (node != null) {
/*  552: 561 */           return node;
/*  553:     */         }
/*  554:     */       }
/*  555:     */     }
/*  556: 564 */     return null;
/*  557:     */   }
/*  558:     */   
/*  559:     */   private SyntaxTreeNode loadExternalStylesheet(String location)
/*  560:     */     throws CompilerException
/*  561:     */   {
/*  562:     */     InputSource source;
/*  563: 576 */     if (new File(location).exists()) {
/*  564: 577 */       source = new InputSource("file:" + location);
/*  565:     */     } else {
/*  566: 579 */       source = new InputSource(location);
/*  567:     */     }
/*  568: 581 */     SyntaxTreeNode external = parse(source);
/*  569: 582 */     return external;
/*  570:     */   }
/*  571:     */   
/*  572:     */   private void initAttrTable(String elementName, String[] attrs)
/*  573:     */   {
/*  574: 586 */     this._instructionAttrs.put(getQName("http://www.w3.org/1999/XSL/Transform", "xsl", elementName), attrs);
/*  575:     */   }
/*  576:     */   
/*  577:     */   private void initInstructionAttrs()
/*  578:     */   {
/*  579: 591 */     initAttrTable("template", new String[] { "match", "name", "priority", "mode" });
/*  580:     */     
/*  581: 593 */     initAttrTable("stylesheet", new String[] { "id", "version", "extension-element-prefixes", "exclude-result-prefixes" });
/*  582:     */     
/*  583:     */ 
/*  584: 596 */     initAttrTable("transform", new String[] { "id", "version", "extension-element-prefixes", "exclude-result-prefixes" });
/*  585:     */     
/*  586:     */ 
/*  587: 599 */     initAttrTable("text", new String[] { "disable-output-escaping" });
/*  588: 600 */     initAttrTable("if", new String[] { "test" });
/*  589: 601 */     initAttrTable("choose", new String[0]);
/*  590: 602 */     initAttrTable("when", new String[] { "test" });
/*  591: 603 */     initAttrTable("otherwise", new String[0]);
/*  592: 604 */     initAttrTable("for-each", new String[] { "select" });
/*  593: 605 */     initAttrTable("message", new String[] { "terminate" });
/*  594: 606 */     initAttrTable("number", new String[] { "level", "count", "from", "value", "format", "lang", "letter-value", "grouping-separator", "grouping-size" });
/*  595:     */     
/*  596:     */ 
/*  597: 609 */     initAttrTable("comment", new String[0]);
/*  598: 610 */     initAttrTable("copy", new String[] { "use-attribute-sets" });
/*  599: 611 */     initAttrTable("copy-of", new String[] { "select" });
/*  600: 612 */     initAttrTable("param", new String[] { "name", "select" });
/*  601: 613 */     initAttrTable("with-param", new String[] { "name", "select" });
/*  602: 614 */     initAttrTable("variable", new String[] { "name", "select" });
/*  603: 615 */     initAttrTable("output", new String[] { "method", "version", "encoding", "omit-xml-declaration", "standalone", "doctype-public", "doctype-system", "cdata-section-elements", "indent", "media-type" });
/*  604:     */     
/*  605:     */ 
/*  606:     */ 
/*  607:     */ 
/*  608: 620 */     initAttrTable("sort", new String[] { "select", "order", "case-order", "lang", "data-type" });
/*  609:     */     
/*  610: 622 */     initAttrTable("key", new String[] { "name", "match", "use" });
/*  611: 623 */     initAttrTable("fallback", new String[0]);
/*  612: 624 */     initAttrTable("attribute", new String[] { "name", "namespace" });
/*  613: 625 */     initAttrTable("attribute-set", new String[] { "name", "use-attribute-sets" });
/*  614:     */     
/*  615: 627 */     initAttrTable("value-of", new String[] { "select", "disable-output-escaping" });
/*  616:     */     
/*  617: 629 */     initAttrTable("element", new String[] { "name", "namespace", "use-attribute-sets" });
/*  618:     */     
/*  619: 631 */     initAttrTable("call-template", new String[] { "name" });
/*  620: 632 */     initAttrTable("apply-templates", new String[] { "select", "mode" });
/*  621: 633 */     initAttrTable("apply-imports", new String[0]);
/*  622: 634 */     initAttrTable("decimal-format", new String[] { "name", "decimal-separator", "grouping-separator", "infinity", "minus-sign", "NaN", "percent", "per-mille", "zero-digit", "digit", "pattern-separator" });
/*  623:     */     
/*  624:     */ 
/*  625:     */ 
/*  626: 638 */     initAttrTable("import", new String[] { "href" });
/*  627: 639 */     initAttrTable("include", new String[] { "href" });
/*  628: 640 */     initAttrTable("strip-space", new String[] { "elements" });
/*  629: 641 */     initAttrTable("preserve-space", new String[] { "elements" });
/*  630: 642 */     initAttrTable("processing-instruction", new String[] { "name" });
/*  631: 643 */     initAttrTable("namespace-alias", new String[] { "stylesheet-prefix", "result-prefix" });
/*  632:     */   }
/*  633:     */   
/*  634:     */   private void initStdClasses()
/*  635:     */   {
/*  636: 654 */     initStdClass("template", "Template");
/*  637: 655 */     initStdClass("stylesheet", "Stylesheet");
/*  638: 656 */     initStdClass("transform", "Stylesheet");
/*  639: 657 */     initStdClass("text", "Text");
/*  640: 658 */     initStdClass("if", "If");
/*  641: 659 */     initStdClass("choose", "Choose");
/*  642: 660 */     initStdClass("when", "When");
/*  643: 661 */     initStdClass("otherwise", "Otherwise");
/*  644: 662 */     initStdClass("for-each", "ForEach");
/*  645: 663 */     initStdClass("message", "Message");
/*  646: 664 */     initStdClass("number", "Number");
/*  647: 665 */     initStdClass("comment", "Comment");
/*  648: 666 */     initStdClass("copy", "Copy");
/*  649: 667 */     initStdClass("copy-of", "CopyOf");
/*  650: 668 */     initStdClass("param", "Param");
/*  651: 669 */     initStdClass("with-param", "WithParam");
/*  652: 670 */     initStdClass("variable", "Variable");
/*  653: 671 */     initStdClass("output", "Output");
/*  654: 672 */     initStdClass("sort", "Sort");
/*  655: 673 */     initStdClass("key", "Key");
/*  656: 674 */     initStdClass("fallback", "Fallback");
/*  657: 675 */     initStdClass("attribute", "XslAttribute");
/*  658: 676 */     initStdClass("attribute-set", "AttributeSet");
/*  659: 677 */     initStdClass("value-of", "ValueOf");
/*  660: 678 */     initStdClass("element", "XslElement");
/*  661: 679 */     initStdClass("call-template", "CallTemplate");
/*  662: 680 */     initStdClass("apply-templates", "ApplyTemplates");
/*  663: 681 */     initStdClass("apply-imports", "ApplyImports");
/*  664: 682 */     initStdClass("decimal-format", "DecimalFormatting");
/*  665: 683 */     initStdClass("import", "Import");
/*  666: 684 */     initStdClass("include", "Include");
/*  667: 685 */     initStdClass("strip-space", "Whitespace");
/*  668: 686 */     initStdClass("preserve-space", "Whitespace");
/*  669: 687 */     initStdClass("processing-instruction", "ProcessingInstruction");
/*  670: 688 */     initStdClass("namespace-alias", "NamespaceAlias");
/*  671:     */   }
/*  672:     */   
/*  673:     */   private void initStdClass(String elementName, String className)
/*  674:     */   {
/*  675: 692 */     this._instructionClasses.put(getQName("http://www.w3.org/1999/XSL/Transform", "xsl", elementName), "org.apache.xalan.xsltc.compiler." + className);
/*  676:     */   }
/*  677:     */   
/*  678:     */   public boolean elementSupported(String namespace, String localName)
/*  679:     */   {
/*  680: 697 */     return this._instructionClasses.get(getQName(namespace, "xsl", localName)) != null;
/*  681:     */   }
/*  682:     */   
/*  683:     */   public boolean functionSupported(String fname)
/*  684:     */   {
/*  685: 701 */     return this._symbolTable.lookupPrimop(fname) != null;
/*  686:     */   }
/*  687:     */   
/*  688:     */   private void initExtClasses()
/*  689:     */   {
/*  690: 705 */     initExtClass("output", "TransletOutput");
/*  691: 706 */     initExtClass("http://xml.apache.org/xalan/redirect", "write", "TransletOutput");
/*  692:     */   }
/*  693:     */   
/*  694:     */   private void initExtClass(String elementName, String className)
/*  695:     */   {
/*  696: 710 */     this._instructionClasses.put(getQName("http://xml.apache.org/xalan/xsltc", "translet", elementName), "org.apache.xalan.xsltc.compiler." + className);
/*  697:     */   }
/*  698:     */   
/*  699:     */   private void initExtClass(String namespace, String elementName, String className)
/*  700:     */   {
/*  701: 715 */     this._instructionClasses.put(getQName(namespace, "translet", elementName), "org.apache.xalan.xsltc.compiler." + className);
/*  702:     */   }
/*  703:     */   
/*  704:     */   private void initSymbolTable()
/*  705:     */   {
/*  706: 723 */     MethodType I_V = new MethodType(Type.Int, Type.Void);
/*  707: 724 */     MethodType I_R = new MethodType(Type.Int, Type.Real);
/*  708: 725 */     MethodType I_S = new MethodType(Type.Int, Type.String);
/*  709: 726 */     MethodType I_D = new MethodType(Type.Int, Type.NodeSet);
/*  710: 727 */     MethodType R_I = new MethodType(Type.Real, Type.Int);
/*  711: 728 */     MethodType R_V = new MethodType(Type.Real, Type.Void);
/*  712: 729 */     MethodType R_R = new MethodType(Type.Real, Type.Real);
/*  713: 730 */     MethodType R_D = new MethodType(Type.Real, Type.NodeSet);
/*  714: 731 */     MethodType R_O = new MethodType(Type.Real, Type.Reference);
/*  715: 732 */     MethodType I_I = new MethodType(Type.Int, Type.Int);
/*  716: 733 */     MethodType D_O = new MethodType(Type.NodeSet, Type.Reference);
/*  717: 734 */     MethodType D_V = new MethodType(Type.NodeSet, Type.Void);
/*  718: 735 */     MethodType D_S = new MethodType(Type.NodeSet, Type.String);
/*  719: 736 */     MethodType D_D = new MethodType(Type.NodeSet, Type.NodeSet);
/*  720: 737 */     MethodType A_V = new MethodType(Type.Node, Type.Void);
/*  721: 738 */     MethodType S_V = new MethodType(Type.String, Type.Void);
/*  722: 739 */     MethodType S_S = new MethodType(Type.String, Type.String);
/*  723: 740 */     MethodType S_A = new MethodType(Type.String, Type.Node);
/*  724: 741 */     MethodType S_D = new MethodType(Type.String, Type.NodeSet);
/*  725: 742 */     MethodType S_O = new MethodType(Type.String, Type.Reference);
/*  726: 743 */     MethodType B_O = new MethodType(Type.Boolean, Type.Reference);
/*  727: 744 */     MethodType B_V = new MethodType(Type.Boolean, Type.Void);
/*  728: 745 */     MethodType B_B = new MethodType(Type.Boolean, Type.Boolean);
/*  729: 746 */     MethodType B_S = new MethodType(Type.Boolean, Type.String);
/*  730: 747 */     MethodType D_X = new MethodType(Type.NodeSet, Type.Object);
/*  731: 748 */     MethodType R_RR = new MethodType(Type.Real, Type.Real, Type.Real);
/*  732: 749 */     MethodType I_II = new MethodType(Type.Int, Type.Int, Type.Int);
/*  733: 750 */     MethodType B_RR = new MethodType(Type.Boolean, Type.Real, Type.Real);
/*  734: 751 */     MethodType B_II = new MethodType(Type.Boolean, Type.Int, Type.Int);
/*  735: 752 */     MethodType S_SS = new MethodType(Type.String, Type.String, Type.String);
/*  736: 753 */     MethodType S_DS = new MethodType(Type.String, Type.Real, Type.String);
/*  737: 754 */     MethodType S_SR = new MethodType(Type.String, Type.String, Type.Real);
/*  738: 755 */     MethodType O_SO = new MethodType(Type.Reference, Type.String, Type.Reference);
/*  739:     */     
/*  740: 757 */     MethodType D_SS = new MethodType(Type.NodeSet, Type.String, Type.String);
/*  741:     */     
/*  742: 759 */     MethodType D_SD = new MethodType(Type.NodeSet, Type.String, Type.NodeSet);
/*  743:     */     
/*  744: 761 */     MethodType B_BB = new MethodType(Type.Boolean, Type.Boolean, Type.Boolean);
/*  745:     */     
/*  746: 763 */     MethodType B_SS = new MethodType(Type.Boolean, Type.String, Type.String);
/*  747:     */     
/*  748: 765 */     MethodType S_SD = new MethodType(Type.String, Type.String, Type.NodeSet);
/*  749:     */     
/*  750: 767 */     MethodType S_DSS = new MethodType(Type.String, Type.Real, Type.String, Type.String);
/*  751:     */     
/*  752: 769 */     MethodType S_SRR = new MethodType(Type.String, Type.String, Type.Real, Type.Real);
/*  753:     */     
/*  754: 771 */     MethodType S_SSS = new MethodType(Type.String, Type.String, Type.String, Type.String);
/*  755:     */     
/*  756:     */ 
/*  757:     */ 
/*  758:     */ 
/*  759:     */ 
/*  760:     */ 
/*  761:     */ 
/*  762:     */ 
/*  763:     */ 
/*  764:     */ 
/*  765: 782 */     this._symbolTable.addPrimop("current", A_V);
/*  766: 783 */     this._symbolTable.addPrimop("last", I_V);
/*  767: 784 */     this._symbolTable.addPrimop("position", I_V);
/*  768: 785 */     this._symbolTable.addPrimop("true", B_V);
/*  769: 786 */     this._symbolTable.addPrimop("false", B_V);
/*  770: 787 */     this._symbolTable.addPrimop("not", B_B);
/*  771: 788 */     this._symbolTable.addPrimop("name", S_V);
/*  772: 789 */     this._symbolTable.addPrimop("name", S_A);
/*  773: 790 */     this._symbolTable.addPrimop("generate-id", S_V);
/*  774: 791 */     this._symbolTable.addPrimop("generate-id", S_A);
/*  775: 792 */     this._symbolTable.addPrimop("ceiling", R_R);
/*  776: 793 */     this._symbolTable.addPrimop("floor", R_R);
/*  777: 794 */     this._symbolTable.addPrimop("round", R_R);
/*  778: 795 */     this._symbolTable.addPrimop("contains", B_SS);
/*  779: 796 */     this._symbolTable.addPrimop("number", R_O);
/*  780: 797 */     this._symbolTable.addPrimop("number", R_V);
/*  781: 798 */     this._symbolTable.addPrimop("boolean", B_O);
/*  782: 799 */     this._symbolTable.addPrimop("string", S_O);
/*  783: 800 */     this._symbolTable.addPrimop("string", S_V);
/*  784: 801 */     this._symbolTable.addPrimop("translate", S_SSS);
/*  785: 802 */     this._symbolTable.addPrimop("string-length", I_V);
/*  786: 803 */     this._symbolTable.addPrimop("string-length", I_S);
/*  787: 804 */     this._symbolTable.addPrimop("starts-with", B_SS);
/*  788: 805 */     this._symbolTable.addPrimop("format-number", S_DS);
/*  789: 806 */     this._symbolTable.addPrimop("format-number", S_DSS);
/*  790: 807 */     this._symbolTable.addPrimop("unparsed-entity-uri", S_S);
/*  791: 808 */     this._symbolTable.addPrimop("key", D_SS);
/*  792: 809 */     this._symbolTable.addPrimop("key", D_SD);
/*  793: 810 */     this._symbolTable.addPrimop("id", D_S);
/*  794: 811 */     this._symbolTable.addPrimop("id", D_D);
/*  795: 812 */     this._symbolTable.addPrimop("namespace-uri", S_V);
/*  796: 813 */     this._symbolTable.addPrimop("function-available", B_S);
/*  797: 814 */     this._symbolTable.addPrimop("element-available", B_S);
/*  798: 815 */     this._symbolTable.addPrimop("document", D_S);
/*  799: 816 */     this._symbolTable.addPrimop("document", D_V);
/*  800:     */     
/*  801:     */ 
/*  802: 819 */     this._symbolTable.addPrimop("count", I_D);
/*  803: 820 */     this._symbolTable.addPrimop("sum", R_D);
/*  804: 821 */     this._symbolTable.addPrimop("local-name", S_V);
/*  805: 822 */     this._symbolTable.addPrimop("local-name", S_D);
/*  806: 823 */     this._symbolTable.addPrimop("namespace-uri", S_V);
/*  807: 824 */     this._symbolTable.addPrimop("namespace-uri", S_D);
/*  808: 825 */     this._symbolTable.addPrimop("substring", S_SR);
/*  809: 826 */     this._symbolTable.addPrimop("substring", S_SRR);
/*  810: 827 */     this._symbolTable.addPrimop("substring-after", S_SS);
/*  811: 828 */     this._symbolTable.addPrimop("substring-before", S_SS);
/*  812: 829 */     this._symbolTable.addPrimop("normalize-space", S_V);
/*  813: 830 */     this._symbolTable.addPrimop("normalize-space", S_S);
/*  814: 831 */     this._symbolTable.addPrimop("system-property", S_S);
/*  815:     */     
/*  816:     */ 
/*  817: 834 */     this._symbolTable.addPrimop("nodeset", D_O);
/*  818: 835 */     this._symbolTable.addPrimop("objectType", S_O);
/*  819: 836 */     this._symbolTable.addPrimop("cast", O_SO);
/*  820:     */     
/*  821:     */ 
/*  822: 839 */     this._symbolTable.addPrimop("+", R_RR);
/*  823: 840 */     this._symbolTable.addPrimop("-", R_RR);
/*  824: 841 */     this._symbolTable.addPrimop("*", R_RR);
/*  825: 842 */     this._symbolTable.addPrimop("/", R_RR);
/*  826: 843 */     this._symbolTable.addPrimop("%", R_RR);
/*  827:     */     
/*  828:     */ 
/*  829:     */ 
/*  830: 847 */     this._symbolTable.addPrimop("+", I_II);
/*  831: 848 */     this._symbolTable.addPrimop("-", I_II);
/*  832: 849 */     this._symbolTable.addPrimop("*", I_II);
/*  833:     */     
/*  834:     */ 
/*  835: 852 */     this._symbolTable.addPrimop("<", B_RR);
/*  836: 853 */     this._symbolTable.addPrimop("<=", B_RR);
/*  837: 854 */     this._symbolTable.addPrimop(">", B_RR);
/*  838: 855 */     this._symbolTable.addPrimop(">=", B_RR);
/*  839:     */     
/*  840:     */ 
/*  841: 858 */     this._symbolTable.addPrimop("<", B_II);
/*  842: 859 */     this._symbolTable.addPrimop("<=", B_II);
/*  843: 860 */     this._symbolTable.addPrimop(">", B_II);
/*  844: 861 */     this._symbolTable.addPrimop(">=", B_II);
/*  845:     */     
/*  846:     */ 
/*  847: 864 */     this._symbolTable.addPrimop("<", B_BB);
/*  848: 865 */     this._symbolTable.addPrimop("<=", B_BB);
/*  849: 866 */     this._symbolTable.addPrimop(">", B_BB);
/*  850: 867 */     this._symbolTable.addPrimop(">=", B_BB);
/*  851:     */     
/*  852:     */ 
/*  853: 870 */     this._symbolTable.addPrimop("or", B_BB);
/*  854: 871 */     this._symbolTable.addPrimop("and", B_BB);
/*  855:     */     
/*  856:     */ 
/*  857: 874 */     this._symbolTable.addPrimop("u-", R_R);
/*  858: 875 */     this._symbolTable.addPrimop("u-", I_I);
/*  859:     */   }
/*  860:     */   
/*  861:     */   public SymbolTable getSymbolTable()
/*  862:     */   {
/*  863: 879 */     return this._symbolTable;
/*  864:     */   }
/*  865:     */   
/*  866:     */   public Template getTemplate()
/*  867:     */   {
/*  868: 883 */     return this._template;
/*  869:     */   }
/*  870:     */   
/*  871:     */   public void setTemplate(Template template)
/*  872:     */   {
/*  873: 887 */     this._template = template;
/*  874:     */   }
/*  875:     */   
/*  876: 890 */   private int _templateIndex = 0;
/*  877:     */   
/*  878:     */   public int getTemplateIndex()
/*  879:     */   {
/*  880: 893 */     return this._templateIndex++;
/*  881:     */   }
/*  882:     */   
/*  883: 908 */   private boolean versionIsOne = true;
/*  884:     */   
/*  885:     */   public SyntaxTreeNode makeInstance(String uri, String prefix, String local, Attributes attributes)
/*  886:     */   {
/*  887: 913 */     SyntaxTreeNode node = null;
/*  888: 914 */     QName qname = getQName(uri, prefix, local);
/*  889: 915 */     String className = (String)this._instructionClasses.get(qname);
/*  890: 917 */     if (className != null)
/*  891:     */     {
/*  892:     */       try
/*  893:     */       {
/*  894: 919 */         Class clazz = ObjectFactory.findProviderClass(className, ObjectFactory.findClassLoader(), true);
/*  895:     */         
/*  896: 921 */         node = (SyntaxTreeNode)clazz.newInstance();
/*  897: 922 */         node.setQName(qname);
/*  898: 923 */         node.setParser(this);
/*  899: 924 */         if (this._locator != null) {
/*  900: 925 */           node.setLineNumber(getLineNumber());
/*  901:     */         }
/*  902: 927 */         if ((node instanceof Stylesheet)) {
/*  903: 928 */           this._xsltc.setStylesheet((Stylesheet)node);
/*  904:     */         }
/*  905: 930 */         checkForSuperfluousAttributes(node, attributes);
/*  906:     */       }
/*  907:     */       catch (ClassNotFoundException e)
/*  908:     */       {
/*  909: 933 */         ErrorMsg err = new ErrorMsg("CLASS_NOT_FOUND_ERR", node);
/*  910: 934 */         reportError(3, err);
/*  911:     */       }
/*  912:     */       catch (Exception e)
/*  913:     */       {
/*  914: 937 */         ErrorMsg err = new ErrorMsg("INTERNAL_ERR", e.getMessage(), node);
/*  915:     */         
/*  916: 939 */         reportError(2, err);
/*  917:     */       }
/*  918:     */     }
/*  919:     */     else
/*  920:     */     {
/*  921: 943 */       if (uri != null) {
/*  922: 945 */         if (uri.equals("http://www.w3.org/1999/XSL/Transform"))
/*  923:     */         {
/*  924: 946 */           node = new UnsupportedElement(uri, prefix, local, false);
/*  925: 947 */           UnsupportedElement element = (UnsupportedElement)node;
/*  926: 948 */           ErrorMsg msg = new ErrorMsg("UNSUPPORTED_XSL_ERR", getLineNumber(), local);
/*  927:     */           
/*  928: 950 */           element.setErrorMessage(msg);
/*  929: 951 */           if (this.versionIsOne) {
/*  930: 952 */             reportError(1, msg);
/*  931:     */           }
/*  932:     */         }
/*  933: 956 */         else if (uri.equals("http://xml.apache.org/xalan/xsltc"))
/*  934:     */         {
/*  935: 957 */           node = new UnsupportedElement(uri, prefix, local, true);
/*  936: 958 */           UnsupportedElement element = (UnsupportedElement)node;
/*  937: 959 */           ErrorMsg msg = new ErrorMsg("UNSUPPORTED_EXT_ERR", getLineNumber(), local);
/*  938:     */           
/*  939: 961 */           element.setErrorMessage(msg);
/*  940:     */         }
/*  941:     */         else
/*  942:     */         {
/*  943: 965 */           Stylesheet sheet = this._xsltc.getStylesheet();
/*  944: 966 */           if ((sheet != null) && (sheet.isExtension(uri)) && 
/*  945: 967 */             (sheet != (SyntaxTreeNode)this._parentStack.peek()))
/*  946:     */           {
/*  947: 968 */             node = new UnsupportedElement(uri, prefix, local, true);
/*  948: 969 */             UnsupportedElement elem = (UnsupportedElement)node;
/*  949: 970 */             ErrorMsg msg = new ErrorMsg("UNSUPPORTED_EXT_ERR", getLineNumber(), prefix + ":" + local);
/*  950:     */             
/*  951:     */ 
/*  952:     */ 
/*  953: 974 */             elem.setErrorMessage(msg);
/*  954:     */           }
/*  955:     */         }
/*  956:     */       }
/*  957: 979 */       if (node == null)
/*  958:     */       {
/*  959: 980 */         node = new LiteralElement();
/*  960: 981 */         node.setLineNumber(getLineNumber());
/*  961:     */       }
/*  962:     */     }
/*  963: 984 */     if ((node != null) && ((node instanceof LiteralElement))) {
/*  964: 985 */       ((LiteralElement)node).setQName(qname);
/*  965:     */     }
/*  966: 987 */     return node;
/*  967:     */   }
/*  968:     */   
/*  969:     */   private void checkForSuperfluousAttributes(SyntaxTreeNode node, Attributes attrs)
/*  970:     */   {
/*  971: 997 */     QName qname = node.getQName();
/*  972: 998 */     boolean isStylesheet = node instanceof Stylesheet;
/*  973: 999 */     String[] legal = (String[])this._instructionAttrs.get(qname);
/*  974:1000 */     if ((this.versionIsOne) && (legal != null))
/*  975:     */     {
/*  976:1002 */       int n = attrs.getLength();
/*  977:1004 */       for (int i = 0; i < n; i++)
/*  978:     */       {
/*  979:1005 */         String attrQName = attrs.getQName(i);
/*  980:1007 */         if ((isStylesheet) && (attrQName.equals("version"))) {
/*  981:1008 */           this.versionIsOne = attrs.getValue(i).equals("1.0");
/*  982:     */         }
/*  983:1012 */         if ((!attrQName.startsWith("xml")) && (attrQName.indexOf(':') <= 0))
/*  984:     */         {
/*  985:1015 */           for (int j = 0; j < legal.length; j++) {
/*  986:1016 */             if (attrQName.equalsIgnoreCase(legal[j])) {
/*  987:     */               break;
/*  988:     */             }
/*  989:     */           }
/*  990:1020 */           if (j == legal.length)
/*  991:     */           {
/*  992:1021 */             ErrorMsg err = new ErrorMsg("ILLEGAL_ATTRIBUTE_ERR", attrQName, node);
/*  993:     */             
/*  994:     */ 
/*  995:     */ 
/*  996:1025 */             err.setWarningError(true);
/*  997:1026 */             reportError(4, err);
/*  998:     */           }
/*  999:     */         }
/* 1000:     */       }
/* 1001:     */     }
/* 1002:     */   }
/* 1003:     */   
/* 1004:     */   public Expression parseExpression(SyntaxTreeNode parent, String exp)
/* 1005:     */   {
/* 1006:1039 */     return (Expression)parseTopLevel(parent, "<EXPRESSION>" + exp, null);
/* 1007:     */   }
/* 1008:     */   
/* 1009:     */   public Expression parseExpression(SyntaxTreeNode parent, String attr, String def)
/* 1010:     */   {
/* 1011:1051 */     String exp = parent.getAttribute(attr);
/* 1012:1053 */     if ((exp.length() == 0) && (def != null)) {
/* 1013:1053 */       exp = def;
/* 1014:     */     }
/* 1015:1055 */     return (Expression)parseTopLevel(parent, "<EXPRESSION>" + exp, exp);
/* 1016:     */   }
/* 1017:     */   
/* 1018:     */   public Pattern parsePattern(SyntaxTreeNode parent, String pattern)
/* 1019:     */   {
/* 1020:1064 */     return (Pattern)parseTopLevel(parent, "<PATTERN>" + pattern, pattern);
/* 1021:     */   }
/* 1022:     */   
/* 1023:     */   public Pattern parsePattern(SyntaxTreeNode parent, String attr, String def)
/* 1024:     */   {
/* 1025:1076 */     String pattern = parent.getAttribute(attr);
/* 1026:1078 */     if ((pattern.length() == 0) && (def != null)) {
/* 1027:1078 */       pattern = def;
/* 1028:     */     }
/* 1029:1080 */     return (Pattern)parseTopLevel(parent, "<PATTERN>" + pattern, pattern);
/* 1030:     */   }
/* 1031:     */   
/* 1032:     */   private SyntaxTreeNode parseTopLevel(SyntaxTreeNode parent, String text, String expression)
/* 1033:     */   {
/* 1034:1089 */     int line = getLineNumber();
/* 1035:     */     try
/* 1036:     */     {
/* 1037:1092 */       this._xpathParser.setScanner(new XPathLexer(new StringReader(text)));
/* 1038:1093 */       Symbol result = this._xpathParser.parse(expression, line);
/* 1039:1094 */       if (result != null)
/* 1040:     */       {
/* 1041:1095 */         SyntaxTreeNode node = (SyntaxTreeNode)result.value;
/* 1042:1096 */         if (node != null)
/* 1043:     */         {
/* 1044:1097 */           node.setParser(this);
/* 1045:1098 */           node.setParent(parent);
/* 1046:1099 */           node.setLineNumber(line);
/* 1047:     */           
/* 1048:1101 */           return node;
/* 1049:     */         }
/* 1050:     */       }
/* 1051:1104 */       reportError(3, new ErrorMsg("XPATH_PARSER_ERR", expression, parent));
/* 1052:     */     }
/* 1053:     */     catch (Exception e)
/* 1054:     */     {
/* 1055:1108 */       if (this._xsltc.debug()) {
/* 1056:1108 */         e.printStackTrace();
/* 1057:     */       }
/* 1058:1109 */       reportError(3, new ErrorMsg("XPATH_PARSER_ERR", expression, parent));
/* 1059:     */     }
/* 1060:1114 */     SyntaxTreeNode.Dummy.setParser(this);
/* 1061:1115 */     return SyntaxTreeNode.Dummy;
/* 1062:     */   }
/* 1063:     */   
/* 1064:     */   public boolean errorsFound()
/* 1065:     */   {
/* 1066:1124 */     return this._errors.size() > 0;
/* 1067:     */   }
/* 1068:     */   
/* 1069:     */   public void printErrors()
/* 1070:     */   {
/* 1071:1131 */     int size = this._errors.size();
/* 1072:1132 */     if (size > 0)
/* 1073:     */     {
/* 1074:1133 */       System.err.println(new ErrorMsg("COMPILER_ERROR_KEY"));
/* 1075:1134 */       for (int i = 0; i < size; i++) {
/* 1076:1135 */         System.err.println("  " + this._errors.elementAt(i));
/* 1077:     */       }
/* 1078:     */     }
/* 1079:     */   }
/* 1080:     */   
/* 1081:     */   public void printWarnings()
/* 1082:     */   {
/* 1083:1144 */     int size = this._warnings.size();
/* 1084:1145 */     if (size > 0)
/* 1085:     */     {
/* 1086:1146 */       System.err.println(new ErrorMsg("COMPILER_WARNING_KEY"));
/* 1087:1147 */       for (int i = 0; i < size; i++) {
/* 1088:1148 */         System.err.println("  " + this._warnings.elementAt(i));
/* 1089:     */       }
/* 1090:     */     }
/* 1091:     */   }
/* 1092:     */   
/* 1093:     */   public void reportError(int category, ErrorMsg error)
/* 1094:     */   {
/* 1095:1157 */     switch (category)
/* 1096:     */     {
/* 1097:     */     case 0: 
/* 1098:1161 */       this._errors.addElement(error);
/* 1099:1162 */       break;
/* 1100:     */     case 1: 
/* 1101:1166 */       this._errors.addElement(error);
/* 1102:1167 */       break;
/* 1103:     */     case 2: 
/* 1104:1171 */       this._errors.addElement(error);
/* 1105:1172 */       break;
/* 1106:     */     case 3: 
/* 1107:1176 */       this._errors.addElement(error);
/* 1108:1177 */       break;
/* 1109:     */     case 4: 
/* 1110:1181 */       this._warnings.addElement(error);
/* 1111:     */     }
/* 1112:     */   }
/* 1113:     */   
/* 1114:     */   public Vector getErrors()
/* 1115:     */   {
/* 1116:1187 */     return this._errors;
/* 1117:     */   }
/* 1118:     */   
/* 1119:     */   public Vector getWarnings()
/* 1120:     */   {
/* 1121:1191 */     return this._warnings;
/* 1122:     */   }
/* 1123:     */   
/* 1124:1196 */   private Stack _parentStack = null;
/* 1125:1197 */   private Hashtable _prefixMapping = null;
/* 1126:     */   
/* 1127:     */   public void startDocument()
/* 1128:     */   {
/* 1129:1203 */     this._root = null;
/* 1130:1204 */     this._target = null;
/* 1131:1205 */     this._prefixMapping = null;
/* 1132:1206 */     this._parentStack = new Stack();
/* 1133:     */   }
/* 1134:     */   
/* 1135:     */   public void endDocument() {}
/* 1136:     */   
/* 1137:     */   public void startPrefixMapping(String prefix, String uri)
/* 1138:     */   {
/* 1139:1220 */     if (this._prefixMapping == null) {
/* 1140:1221 */       this._prefixMapping = new Hashtable();
/* 1141:     */     }
/* 1142:1223 */     this._prefixMapping.put(prefix, uri);
/* 1143:     */   }
/* 1144:     */   
/* 1145:     */   public void endPrefixMapping(String prefix) {}
/* 1146:     */   
/* 1147:     */   public void startElement(String uri, String localname, String qname, Attributes attributes)
/* 1148:     */     throws SAXException
/* 1149:     */   {
/* 1150:1240 */     int col = qname.lastIndexOf(':');
/* 1151:1241 */     String prefix = col == -1 ? null : qname.substring(0, col);
/* 1152:     */     
/* 1153:1243 */     SyntaxTreeNode element = makeInstance(uri, prefix, localname, attributes);
/* 1154:1245 */     if (element == null)
/* 1155:     */     {
/* 1156:1246 */       ErrorMsg err = new ErrorMsg("ELEMENT_PARSE_ERR", prefix + ':' + localname);
/* 1157:     */       
/* 1158:1248 */       throw new SAXException(err.toString());
/* 1159:     */     }
/* 1160:1253 */     if (this._root == null)
/* 1161:     */     {
/* 1162:1254 */       if ((this._prefixMapping == null) || (!this._prefixMapping.containsValue("http://www.w3.org/1999/XSL/Transform"))) {
/* 1163:1256 */         this._rootNamespaceDef = false;
/* 1164:     */       } else {
/* 1165:1258 */         this._rootNamespaceDef = true;
/* 1166:     */       }
/* 1167:1259 */       this._root = element;
/* 1168:     */     }
/* 1169:     */     else
/* 1170:     */     {
/* 1171:1262 */       SyntaxTreeNode parent = (SyntaxTreeNode)this._parentStack.peek();
/* 1172:1263 */       parent.addElement(element);
/* 1173:1264 */       element.setParent(parent);
/* 1174:     */     }
/* 1175:1266 */     element.setAttributes(new AttributeList(attributes));
/* 1176:1267 */     element.setPrefixMapping(this._prefixMapping);
/* 1177:1269 */     if ((element instanceof Stylesheet))
/* 1178:     */     {
/* 1179:1273 */       getSymbolTable().setCurrentNode(element);
/* 1180:1274 */       ((Stylesheet)element).excludeExtensionPrefixes(this);
/* 1181:     */     }
/* 1182:1277 */     this._prefixMapping = null;
/* 1183:1278 */     this._parentStack.push(element);
/* 1184:     */   }
/* 1185:     */   
/* 1186:     */   public void endElement(String uri, String localname, String qname)
/* 1187:     */   {
/* 1188:1285 */     this._parentStack.pop();
/* 1189:     */   }
/* 1190:     */   
/* 1191:     */   public void characters(char[] ch, int start, int length)
/* 1192:     */   {
/* 1193:1292 */     String string = new String(ch, start, length);
/* 1194:1293 */     SyntaxTreeNode parent = (SyntaxTreeNode)this._parentStack.peek();
/* 1195:1295 */     if (string.length() == 0) {
/* 1196:1295 */       return;
/* 1197:     */     }
/* 1198:1299 */     if ((parent instanceof Text))
/* 1199:     */     {
/* 1200:1300 */       ((Text)parent).setText(string);
/* 1201:1301 */       return;
/* 1202:     */     }
/* 1203:1305 */     if ((parent instanceof Stylesheet)) {
/* 1204:1305 */       return;
/* 1205:     */     }
/* 1206:1307 */     SyntaxTreeNode bro = parent.lastChild();
/* 1207:1308 */     if ((bro != null) && ((bro instanceof Text)))
/* 1208:     */     {
/* 1209:1309 */       Text text = (Text)bro;
/* 1210:1310 */       if ((!text.isTextElement()) && (
/* 1211:1311 */         (length > 1) || (ch[0] < 'Ā')))
/* 1212:     */       {
/* 1213:1312 */         text.setText(string);
/* 1214:1313 */         return;
/* 1215:     */       }
/* 1216:     */     }
/* 1217:1319 */     parent.addElement(new Text(string));
/* 1218:     */   }
/* 1219:     */   
/* 1220:     */   private String getTokenValue(String token)
/* 1221:     */   {
/* 1222:1323 */     int start = token.indexOf('"');
/* 1223:1324 */     int stop = token.lastIndexOf('"');
/* 1224:1325 */     return token.substring(start + 1, stop);
/* 1225:     */   }
/* 1226:     */   
/* 1227:     */   public void processingInstruction(String name, String value)
/* 1228:     */   {
/* 1229:1334 */     if ((this._target == null) && (name.equals("xml-stylesheet")))
/* 1230:     */     {
/* 1231:1336 */       String href = null;
/* 1232:1337 */       String media = null;
/* 1233:1338 */       String title = null;
/* 1234:1339 */       String charset = null;
/* 1235:     */       
/* 1236:     */ 
/* 1237:1342 */       StringTokenizer tokens = new StringTokenizer(value);
/* 1238:1343 */       while (tokens.hasMoreElements())
/* 1239:     */       {
/* 1240:1344 */         String token = (String)tokens.nextElement();
/* 1241:1345 */         if (token.startsWith("href")) {
/* 1242:1346 */           href = getTokenValue(token);
/* 1243:1347 */         } else if (token.startsWith("media")) {
/* 1244:1348 */           media = getTokenValue(token);
/* 1245:1349 */         } else if (token.startsWith("title")) {
/* 1246:1350 */           title = getTokenValue(token);
/* 1247:1351 */         } else if (token.startsWith("charset")) {
/* 1248:1352 */           charset = getTokenValue(token);
/* 1249:     */         }
/* 1250:     */       }
/* 1251:1357 */       if (((this._PImedia == null) || (this._PImedia.equals(media))) && ((this._PItitle == null) || (this._PImedia.equals(title))) && ((this._PIcharset == null) || (this._PImedia.equals(charset)))) {
/* 1252:1360 */         this._target = href;
/* 1253:     */       }
/* 1254:     */     }
/* 1255:     */   }
/* 1256:     */   
/* 1257:     */   public void ignorableWhitespace(char[] ch, int start, int length) {}
/* 1258:     */   
/* 1259:     */   public void skippedEntity(String name) {}
/* 1260:     */   
/* 1261:     */   public void setDocumentLocator(Locator locator)
/* 1262:     */   {
/* 1263:1380 */     this._locator = locator;
/* 1264:     */   }
/* 1265:     */   
/* 1266:     */   private int getLineNumber()
/* 1267:     */   {
/* 1268:1388 */     int line = 0;
/* 1269:1389 */     if (this._locator != null) {
/* 1270:1390 */       line = this._locator.getLineNumber();
/* 1271:     */     }
/* 1272:1391 */     return line;
/* 1273:     */   }
/* 1274:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.Parser
 * JD-Core Version:    0.7.0.1
 */